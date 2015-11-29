/*******************************************************************************
 * Copyright (c) 2010 Gijs de Vries aka Janoz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gijs de Vries aka Janoz - initial API and implementation
 ******************************************************************************/
package com.janoz.tvapilib.thetvdb.impl;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Art;
import com.janoz.tvapilib.model.impl.Episode;
import com.janoz.tvapilib.model.impl.Show;

public class TheTVDBTest {


	UrlSupplier urlSupplierMock;
	DefaultTheTVDBImpl subject;
	
	@Before
	public void setup() throws Exception {
		//construct an instance of the subject with a mocked UrlSupplier
		urlSupplierMock = createMock(UrlSupplier.class);
		Constructor<DefaultTheTVDBImpl> constructor =
			DefaultTheTVDBImpl.class.getDeclaredConstructor(UrlSupplier.class);
		constructor.setAccessible(true);
		subject = constructor.newInstance(urlSupplierMock);
	}
	
	@Test
	public void testSearch() {
		expect(urlSupplierMock.getShowSearchUrl(eq("nikita"))).andReturn(getResource("GetSeries.nikita.xml"));
		replay(urlSupplierMock);

		List<Show> shows = subject.searchShows("nikita");
		verify(urlSupplierMock);
		assertShow164301(shows.get(0), false);
		
		assertEquals("La Femme Nikita",shows.get(1).getTitle());
		assertEquals(2,shows.size());
	}

	@Test
	public void testEpisodeByShow() throws Exception {
		expect(urlSupplierMock.getBaseEpisodeUrl(eq(164301), eq(1), eq(3))).andReturn(getResource("164301_1_3.xml"));
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2738381.jpg"))).andReturn(
				"http://aDomain/ep_1_3.jpg");
		replay(urlSupplierMock);
		Show show = new Show();
		show.setTheTvDbId(164301);
		Episode episode = subject.getEpisode(show, 1, 3);
		verify(urlSupplierMock);
		assertEpisode164301_1_3(show, episode);
	}

	@Test
	public void testEpisodeByShowId() throws Exception {
		expect(urlSupplierMock.getBaseEpisodeUrl(eq(164301), eq(1), eq(3))).andReturn(getResource("164301_1_3.xml"));
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2738381.jpg"))).andReturn(
				"http://aDomain/ep_1_3.jpg");
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(164301, 1, 3);
		verify(urlSupplierMock);
		assertEpisode164301_1_3(null, episode);
	}
	
	@Test
	public void testShow() throws Exception {
		expect(urlSupplierMock.getBaseShowUrl(eq(164301))).andReturn(getResource("164301.xml"));
		replay(urlSupplierMock);

		Show show = subject.getShow(164301);
		verify(urlSupplierMock);
		assertShow164301(show, true);
		assertShow164301Extra(show);

		
		
	}
	
	@Test
	public void testFullShow() throws Exception {
		expect(urlSupplierMock.getFullShowUrl(eq(164301))).andReturn(getResource("164301_all.xml"));
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2415991.jpg"))).andReturn(
			"http://aDomain/ep_1_1.jpg");
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2733071.jpg"))).andReturn(
			"http://aDomain/ep_1_2.jpg");
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2738381.jpg"))).andReturn(
			"http://aDomain/ep_1_3.jpg");
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2756421.jpg"))).andReturn(
			"http://aDomain/ep_1_5.jpg");		
		replay(urlSupplierMock);
		
		Show show = subject.getShowWithEpisodes(164301);
		verify(urlSupplierMock);
		assertShow164301(show, true);
		assertShow164301Extra(show);

		assertEpisode164301_1_3(show, show.getSeason(1).getEpisode(3));
		assertTrue(show.getSeason(1).hasEpisode(9));
		assertFalse(show.getSeason(1).hasEpisode(10));
		assertEquals(9,show.getSeason(1).getNrOfEpisodes());
	}
	
	@Test
	public void testBanners() {
		expect(urlSupplierMock.getBannerUrl(164301)).andReturn(getResource("164301_banners.xml"));
		expect(urlSupplierMock.getImageUrl((String)anyObject())).andAnswer(new IAnswer<String>() {
			        @Override
			        public String answer() throws Throwable {
			            return "http://anUrl/" + (String)getCurrentArguments()[0]; 
			        }
			    }
			).anyTimes();
		
		
		
		replay(urlSupplierMock);
		Show show = new Show();
		show.setTheTvDbId(164301);
		subject.addFanart(show);
		verify(urlSupplierMock);
		
		assertContains(show.getArts(),
				587461,535361,495351,495371,541941,541951,495381,
				550631,541931,598121,550801,490141,482301,585071);
		assertEquals(0,show.getSeason(0).getArts().size());
		assertContains(show.getSeason(1).getArts(),600161);
	}

	@Test
	public void testEpisodeWithoutThumb() throws Exception {
		expect(urlSupplierMock.getBaseEpisodeUrl(eq(164301), eq(1), eq(3))).andReturn(getResource("164301_1_3_nothumb.xml"));
		replay(urlSupplierMock);
		Show show = new Show();
		show.setTheTvDbId(164301);
		Episode episode = subject.getEpisode(show, 1, 3);
		verify(urlSupplierMock);
		assertEquals(0,episode.getArts().size());
	}




	private void assertContains(Set<Art> artSet, int... ids) {
		assertEquals(ids.length, artSet.size());
		for (Art f : artSet) {
			assertTrue("id " + f.getId() + " not found.",inArray(ids,f.getId()));
		}
	}
	
	private boolean inArray(int[] haystack, int needle) {
		for (int i : haystack) {
			if (i == needle) {
				return true;
			}
		}
		return false;
	}
	
	private String getResource(String filename) {
		return this.getClass().getClassLoader().getResource("responses/thetvdb/"+filename).toString();
	}


	
	private void assertEpisode164301_1_3(Show show, Episode episode) {
		if (show == null) {
			assertEquals(Integer.valueOf(164301),episode.getSeason().getShow().getTheTvDbId());
		} else {
			assertSame(show,episode.getSeason().getShow());
		}
		assertEquals(2738381,episode.getTheTvDbId());
		assertEquals(1,episode.getSeason().getSeason());
		assertEquals(3,episode.getEpisode());
		assertEquals("Kill Jill",episode.getTitle());
		assertEquals(new GregorianCalendar(2010,Calendar.SEPTEMBER,23).getTime(),episode.getAired());
		assertEquals("A very long description about Jill..",episode.getDescription());
		assertEquals("http://aDomain/ep_1_3.jpg",episode.getArts().iterator().next().getUrl());
		assertEquals(Double.valueOf(7.8),episode.getRating());
	}
	
	private void assertShow164301(Show show, boolean testExtra) {
		assertEquals(Integer.valueOf(164301),show.getTheTvDbId());
		assertEquals("tt1592154", show.getImdbId());
		assertEquals("Nikita",show.getTitle());
		assertEquals("A very long description about Nikita and division.",show.getDescription());
		if (testExtra) {
			assertEquals(Double.valueOf(9.0),show.getRating());
			assertTrue(show.getGenres().contains("Action"));
			assertTrue(show.getGenres().contains("Adventure"));
			assertTrue(show.getGenres().contains("Drama"));
		}
	}
	private void assertShow164301Extra(Show show) {
		assertEquals("The CW", show.getNetwork());
	}
	
	

}
