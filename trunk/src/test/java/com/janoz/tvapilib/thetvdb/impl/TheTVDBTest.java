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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.Show;

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
		assertShow164301(shows.get(0));
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
		assertShow164301(show);
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
		assertShow164301(show);
		assertEpisode164301_1_3(show, show.getSeason(1).getEpisode(3));
		assertNotNull(show.getSeason(1).getEpisode(9));
		assertNull(show.getSeason(1).getEpisode(10));
		assertEquals(9,show.getSeason(1).getNrOfEpisodes());
	}
	
	@Test
	public void testBanners() {
		expect(urlSupplierMock.getBannerUrl(164301)).andReturn(getResource("164301_banners.xml"));
		expect(urlSupplierMock.getImageUrl((String)anyObject())).andReturn("http://anUrl/").anyTimes();
		
		replay(urlSupplierMock);
		Show show = new Show();
		show.setTheTvDbId(164301);
		subject.fillFanart(show);
		verify(urlSupplierMock);
		
		assertContains(show.getFanarts(),587461,535361,495351,495371);
		assertContains(show.getPosters(),541941,541951,495381);
		assertContains(show.getBanners(),550631,541931,598121,550801,490141,482301,585071);
		assertEquals(0,show.getAllSeasonBanners().size());
		assertEquals(0,show.getAllSeasonPosters().size());
		assertEquals(0,show.getSeason(1).getBanners().size());
		assertContains(show.getSeason(1).getPosters(),600161);
	}

	private void assertContains(List<Fanart> fanartList, int... ids) {
		assertEquals(ids.length, fanartList.size());
		for (Fanart f : fanartList) {
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
			assertEquals(164301,episode.getSeason().getShow().getTheTvDbId());
		} else {
			assertSame(show,episode.getSeason().getShow());
		}
		assertEquals(2738381,episode.getTheTvDbId());
		assertEquals(1,episode.getSeason().getSeason());
		assertEquals(3,episode.getEpisode());
		assertEquals("Kill Jill",episode.getTitle());
		assertEquals(new GregorianCalendar(2010,Calendar.SEPTEMBER,23).getTime(),episode.getAired());
		assertEquals("A very long description about Jill..",episode.getDescription());
		assertEquals("http://aDomain/ep_1_3.jpg",episode.getThumbUrl());
	}
	
	private void assertShow164301(Show show) {
		assertEquals(164301,show.getTheTvDbId());
		assertEquals("Nikita",show.getTitle());
		assertEquals("A very long description about Nikita and division.",show.getDescription());
	}
	
	

}
