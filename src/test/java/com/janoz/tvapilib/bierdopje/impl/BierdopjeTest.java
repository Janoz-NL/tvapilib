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
package com.janoz.tvapilib.bierdopje.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.List;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Subtitle;


public class BierdopjeTest {

	private static final String API_KEY = "**apiKey**";
	BierdopjeImpl subject;
	
	@Before
	public void setup() {
		subject = createMockBuilder(BierdopjeImpl.class)
			.addMockedMethod("constructGetAllSubsForUrl").withConstructor(API_KEY).createMock();
	}
	
	@Test
	public void testGetAllSubsForNoResult() throws Exception {
		Capture<Episode> epCap = new Capture<Episode>();
		expect(subject.constructGetAllSubsForUrl(capture(epCap))).andReturn(
				this.getClass().getClassLoader().getResource("responses/bierdopje/getSubsForNone.xml").toString());
		replay(subject);
		List<Subtitle> result = subject.getAllSubsFor(1,2,3);
		verify(subject);
		assertEquals(1,epCap.getValue().getShow().getId());
		assertEquals(2,epCap.getValue().getSeason());
		assertEquals(3,epCap.getValue().getEpisode());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAllSubsFor() throws Exception {
		Capture<Episode> epCap = new Capture<Episode>();
		expect(subject.constructGetAllSubsForUrl(capture(epCap))).andReturn(
				this.getClass().getClassLoader().getResource("responses/bierdopje/getSubsForMany.xml").toString());
		replay(subject);
		List<Subtitle> result = subject.getAllSubsFor(1,2,3);
		verify(subject);
		assertEquals(1,epCap.getValue().getShow().getId());
		assertEquals(2,epCap.getValue().getSeason());
		assertEquals(3,epCap.getValue().getEpisode());
		assertEquals(4,result.size());
		for (Subtitle subtitle : result) {
			assertSame(epCap.getValue(),subtitle.getEpisode());
			assertNotNull(subtitle.getDownloadUrl());
			assertNotNull(subtitle.getFileTitle());
		}
	}
}
