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
package com.janoz.tvapilib.tvrage.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.impl.Episode;
import com.janoz.tvapilib.model.impl.Show;
public class TvRageTest {

	UrlSupplier urlSupplierMock;
	DefaultTvRageImpl subject;
	
	@Before
	public void setup() throws Exception {
		//construct an instance of the subject with a mocked UrlSupplier
		urlSupplierMock = createMock(UrlSupplier.class);
		Constructor<DefaultTvRageImpl> constructor =
			DefaultTvRageImpl.class.getDeclaredConstructor(UrlSupplier.class);
		constructor.setAccessible(true);
		subject = constructor.newInstance(urlSupplierMock);
	}
	
	@Test
	public void testSearch() {
		expect(urlSupplierMock.getShowSearchUrl(eq("buffy"))).andReturn(getResource("search_buffy.MF.xml"));
		replay(urlSupplierMock);

		List<Show> shows = subject.searchShows("buffy");
		verify(urlSupplierMock);
		assertBuffy(shows.get(0));
		assertEquals("Buffy the Animated Series",shows.get(1).getTitle());
		assertEquals(2,shows.size());
	}

	@Test
	public void testSearchGreek() {
		expect(urlSupplierMock.getShowSearchUrl(eq("greek"))).andReturn(getResource("search_greek.xml"));
		replay(urlSupplierMock);

		List<Show> shows = subject.searchShows("greek");
		verify(urlSupplierMock);
		assertEquals("My Big Fat Greek Life",shows.get(0).getTitle());
		assertEquals("Jim Henson's The Storyteller: Greek Myths",shows.get(1).getTitle());
		assertEquals("The Greek Variety Show",shows.get(2).getTitle());
		assertEquals("GREEK",shows.get(3).getTitle());
		assertEquals("GRΣΣK UNCOVΣRΣD",shows.get(4).getTitle());
		assertEquals(5,shows.size());
	}

	@Test
	public void testSearchPublic() {
		expect(urlSupplierMock.getShowSearchUrl(eq("buffy"))).andReturn(getResource("search_buffy.xml"));
		replay(urlSupplierMock);

		List<Show> shows = subject.searchShows("buffy");
		verify(urlSupplierMock);
		assertBuffy(shows.get(0));
		assertEquals("Buffy the Animated Series",shows.get(1).getTitle());
		assertEquals(2,shows.size());
	}

	@Test
	public void testShow() {
		expect(urlSupplierMock.getShowInfoUrl(eq(2930))).andReturn(getResource("showinfo_2930.MF.xml"));
		replay(urlSupplierMock);

		Show show = subject.getShow(2930);
		verify(urlSupplierMock);
		assertBuffy(show);
	}
	
	@Test
	public void testShowPublic() {
		expect(urlSupplierMock.getShowInfoUrl(eq(2930))).andReturn(getResource("showinfo_2930.xml"));
		replay(urlSupplierMock);

		Show show = subject.getShow(2930);
		verify(urlSupplierMock);
		assertBuffy(show);
	}

	
	@Test
	public void testEpisodeList() {
		expect(urlSupplierMock.getEpisodeListUrl(eq(2930))).andReturn(getResource("episode_list_2930.MF.xml"));
		replay(urlSupplierMock);

		Show show = subject.getShowWithEpisodes(2930);
		verify(urlSupplierMock);
		assertBuffy(show);
		assertAllBuffyEpisodes(show);
	}
	
	@Test
	public void testEpisodeListPublic() {
		expect(urlSupplierMock.getEpisodeListUrl(eq(2930))).andReturn(getResource("episode_list_2930.xml"));
		replay(urlSupplierMock);

		Show show = subject.getShowWithEpisodes(2930);
		verify(urlSupplierMock);
		assertBuffy(show);
		assertAllBuffyEpisodes(show);
	}
	
	@Test
	public void testEpisodeInfo() {
		expect(urlSupplierMock.getEpisodeInfoUrl(eq(2930), eq(2), eq(4))).andReturn(getResource("episodeinfo_2930_2x04.MF.xml"));
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(2930, 2, 4);
		verify(urlSupplierMock);
		assertBuffyEpisode2x4(episode);
	}
	
	@Test
	public void testEpisodeInfoPublic() {
		expect(urlSupplierMock.getEpisodeInfoUrl(eq(2930), eq(2), eq(4))).andReturn(getResource("episodeinfo_2930_2x04.xml"));
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(2930, 2, 4);
		verify(urlSupplierMock);
		assertBuffyEpisode2x4(episode);
	}
	
	@Test
	public void testEpisodeInfoUsingShow() {
		Show show = new Show();
		show.setTvRageId(2930);
		
		expect(urlSupplierMock.getEpisodeInfoUrl(eq(2930), eq(2), eq(4))).andReturn(getResource("episodeinfo_2930_2x04.MF.xml"));
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(show, 2, 4);
		verify(urlSupplierMock);
		assertBuffyEpisode2x4(episode);
	}
	
	@Test
	public void testEpisodeInfoUsingShowPublic() {
		Show show = new Show();
		show.setTvRageId(2930);
		
		expect(urlSupplierMock.getEpisodeInfoUrl(eq(2930), eq(2), eq(4))).andReturn(getResource("episodeinfo_2930_2x04.xml"));
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(show, 2, 4);
		verify(urlSupplierMock);
		assertBuffyEpisode2x4(episode);
	}

	private String getResource(String filename) {
		return this.getClass().getClassLoader().getResource("responses/tvrage/"+filename).toString();
	}
	
	private void assertBuffy(Show show) {
		assertEquals("Buffy the Vampire Slayer", show.getTitle());
		assertEquals(Integer.valueOf(2930),show.getTvRageId());
	}
	
	private void assertAllBuffyEpisodes(Show show) {
		assertEquals(1,show.getSeason(0).getNrOfEpisodes());
		assertEquals(12,show.getSeason(1).getNrOfEpisodes());
		assertEquals(22,show.getSeason(2).getNrOfEpisodes());
		assertEquals(22,show.getSeason(3).getNrOfEpisodes());
		assertEquals(22,show.getSeason(4).getNrOfEpisodes());
		assertEquals(22,show.getSeason(5).getNrOfEpisodes());
		assertEquals(22,show.getSeason(6).getNrOfEpisodes());
		assertEquals(22,show.getSeason(7).getNrOfEpisodes());
		assertEquals(0,show.getSeason(8).getNrOfEpisodes());
		
		//sample
		assertBuffyEpisode1x1(show.getSeason(1).getEpisode(1));
		assertBuffyEpisode1x12(show.getSeason(1).getEpisode(12));
		assertBuffyEpisode2x4(show.getSeason(2).getEpisode(4));
		assertBuffyEpisode7x22(show.getSeason(7).getEpisode(22));
	}

	public void assertBuffyEpisode1x1(Episode episode) {
		assertEquals("Welcome to the Hellmouth (1)", episode.getTitle());
		assertEquals(new GregorianCalendar(1997,Calendar.MARCH,10).getTime(), episode.getAired());
		assertEquals(1,episode.getEpisode());
		assertEquals(1,episode.getSeason().getSeason());
		assertEquals("http://www.tvrage.com/Buffy_The_Vampire_Slayer/episodes/28077",episode.getTvRageUrl());
		assertBuffy(episode.getSeason().getShow());
	}
	
	public void assertBuffyEpisode1x12(Episode episode) {
		assertEquals("Prophecy Girl", episode.getTitle());
		assertEquals(new GregorianCalendar(1997,Calendar.JUNE,2).getTime(), episode.getAired());
		assertEquals(12,episode.getEpisode());
		assertEquals(1,episode.getSeason().getSeason());
		assertEquals("http://www.tvrage.com/Buffy_The_Vampire_Slayer/episodes/28088",episode.getTvRageUrl());
		assertBuffy(episode.getSeason().getShow());
	}
	
	public void assertBuffyEpisode2x4(Episode episode) {
		assertEquals("Inca Mummy Girl", episode.getTitle());
		assertEquals(new GregorianCalendar(1997,Calendar.OCTOBER,6).getTime(), episode.getAired());
		assertEquals(4,episode.getEpisode());
		assertEquals(2,episode.getSeason().getSeason());
		assertEquals("http://www.tvrage.com/Buffy_The_Vampire_Slayer/episodes/28092",episode.getTvRageUrl());
		assertBuffy(episode.getSeason().getShow());
	}
	
	public void assertBuffyEpisode7x22(Episode episode) {
		assertEquals("Chosen", episode.getTitle());
		assertEquals(new GregorianCalendar(2003,Calendar.MAY,20).getTime(), episode.getAired());
		assertEquals(22,episode.getEpisode());
		assertEquals(7,episode.getSeason().getSeason());
		assertEquals("http://www.tvrage.com/Buffy_The_Vampire_Slayer/episodes/28220",episode.getTvRageUrl());
		assertBuffy(episode.getSeason().getShow());
	}
	
}
