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
				"http://aDomain/anImage.jpg");
		replay(urlSupplierMock);
		Show show = new Show();
		show.setId(164301);
		Episode episode = subject.getEpisode(show, 1, 3);
		verify(urlSupplierMock);
		assertEpisode164301_1_3(show, episode);
	}

	@Test
	public void testEpisodeByShowId() throws Exception {
		expect(urlSupplierMock.getBaseEpisodeUrl(eq(164301), eq(1), eq(3))).andReturn(
				new StringBuilder(
						this.getClass().getClassLoader().getResource("responses/thetvdb/164301_1_3.xml").toString()));
		expect(urlSupplierMock.getImageUrl(eq("episodes/164301/2738381.jpg"))).andReturn(
				"http://aDomain/anImage.jpg");
		replay(urlSupplierMock);

		Episode episode = subject.getEpisode(164301, 1, 3);
		verify(urlSupplierMock);
		assertEpisode164301_1_3(null, episode);
	}
	
	@Test
	public void testShow() throws Exception {
		expect(urlSupplierMock.getBaseShowUrl(eq(164301))).andReturn(
				new StringBuilder(
						this.getClass().getClassLoader().getResource("responses/thetvdb/164301.xml").toString()));
		expect(urlSupplierMock.getImageUrl(eq("graphical/164301-g4.jpg")))
			.andReturn("http://aDomain/banner.jpg");
		expect(urlSupplierMock.getImageUrl(eq("fanart/original/164301-5.jpg")))
			.andReturn("http://aDomain/fanart.jpg");
		expect(urlSupplierMock.getImageUrl(eq("posters/164301-2.jpg")))
			.andReturn("http://aDomain/poster.jpg");
		replay(urlSupplierMock);

		Show show = subject.getShow(164301);
		verify(urlSupplierMock);
		assertShow164301(show);
	}

	private void assertEpisode164301_1_3(Show show, Episode episode) {
		if (show == null) {
			assertEquals(164301,episode.getShow().getId());
		} else {
			assertSame(show,episode.getShow());
		}
		assertEquals(2738381,episode.getId());
		assertEquals(1,episode.getSeason());
		assertEquals(3,episode.getEpisode());
		assertEquals("Kill Jill",episode.getTitle());
		assertEquals(new GregorianCalendar(2010,Calendar.SEPTEMBER,23).getTime(),episode.getAired());
		assertEquals("A very long description about Jill..",episode.getDescription());
		assertEquals("http://aDomain/anImage.jpg",episode.getThumbUrl());
	}
	
	private void assertShow164301(Show show) {
		assertEquals(164301,show.getId());
		assertEquals("Nikita",show.getTitle());
		assertEquals("http://aDomain/fanart.jpg",show.getFanartUrl());
		assertEquals("http://aDomain/poster.jpg",show.getPosterUrl());
		assertEquals("http://aDomain/banner.jpg",show.getBannerUrl());
	}
	
	

}
