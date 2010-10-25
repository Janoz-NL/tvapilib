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
		assertEquals(1,epCap.getValue().getSeason().getShow().getTheTvDbId());
		assertEquals(2,epCap.getValue().getSeason().getSeason());
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
		assertEquals(1,epCap.getValue().getSeason().getShow().getTheTvDbId());
		assertEquals(2,epCap.getValue().getSeason().getSeason());
		assertEquals(3,epCap.getValue().getEpisode());
		assertEquals(4,result.size());
		for (Subtitle subtitle : result) {
			assertSame(epCap.getValue(),subtitle.getEpisode());
			assertNotNull(subtitle.getDownloadUrl());
			assertNotNull(subtitle.getFileTitle());
		}
	}
}
