/*******************************************************************************
 * Copyright 2010 Gijs de Vries aka Janoz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.janoz.tvapilib.thetvdb.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;

public class TheTVDBTest {


	UrlSupplier urlSupplierMock;
	TheTVDBImpl subject;
	
	@Before
	public void setup() throws Exception {
		//construct an instance of the subject with a mocked UrlSupplier
		urlSupplierMock = createMock(UrlSupplier.class);
		Constructor<TheTVDBImpl> constructor =
			TheTVDBImpl.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		subject = constructor.newInstance();
		Field field = subject.getClass().getDeclaredField("urlSupplier");
		field.setAccessible(true);
		field.set(subject,urlSupplierMock);
	}
	
	@Test
	public void testEpisodeByShow() throws Exception {
		expect(urlSupplierMock.getBaseEpisodeUrl(eq(164301), eq(1), eq(3))).andReturn(
				new StringBuilder(
						this.getClass().getClassLoader().getResource("responses/thetvdb/164301_1_3.xml").toString()));
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2738381.jpg"))).andReturn(
				"http://aDomain/ep_1_3.jpg");
		replay(urlSupplierMock);
		Show show = new Show();
		show.setId(164301);
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
		
		Show show = subject.getFullShow(164301);
		verify(urlSupplierMock);
		assertShow164301(show);
		assertEpisode164301_1_3(show, show.getSeason(1).getEpisode(3));
		assertNotNull(show.getSeason(1).getEpisode(9));
		assertNull(show.getSeason(1).getEpisode(10));
		assertEquals(9,show.getSeason(1).getNrOfEpisodes());
	}

	private StringBuilder getResource(String filename) {
		return new StringBuilder(
				this.getClass().getClassLoader().getResource("responses/thetvdb/"+filename).toString());
	}


	
	private void assertEpisode164301_1_3(Show show, Episode episode) {
		if (show == null) {
			assertEquals(164301,episode.getSeason().getShow().getId());
		} else {
			assertSame(show,episode.getSeason().getShow());
		}
		assertEquals(2738381,episode.getId());
		assertEquals(1,episode.getSeason().getSeason());
		assertEquals(3,episode.getEpisode());
		assertEquals("Kill Jill",episode.getTitle());
		assertEquals(new GregorianCalendar(2010,Calendar.SEPTEMBER,23).getTime(),episode.getAired());
		assertEquals("A very long description about Jill..",episode.getDescription());
		assertEquals("http://aDomain/ep_1_3.jpg",episode.getThumbUrl());
	}
	
	private void assertShow164301(Show show) {
		assertEquals(164301,show.getId());
		assertEquals("Nikita",show.getTitle());

	}
	
	

}
