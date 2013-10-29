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

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Subtitle;


public class BierdopjeTest {

	private static final String API_KEY = "**apiKey**";
	DefaultBierdopjeImpl subject;
	
	@Before
	public void setup() {
		subject = createMockBuilder(DefaultBierdopjeImpl.class)
			.addMockedMethod("constructGetAllSubsForUrl").withConstructor(API_KEY).createMock();
	}

	@Test
	public void testGetSubsUrl(){
		//replace mock
		subject = new DefaultBierdopjeImpl(API_KEY);		
		assertEquals("http://api.bierdopje.com/**apiKey**/GetAllSubsFor/1/2/3/nl/true",subject.constructGetAllSubsForUrl(1,2,3));
	}
	
	@Test
	public void testGetAllSubsForNoResult() throws Exception {
		expect(subject.constructGetAllSubsForUrl(eq(1),eq(2),eq(3))).andReturn(
				this.getClass().getClassLoader().getResource("responses/bierdopje/getSubsForNone.xml").toString());
		replay(subject);
		List<Subtitle> result = subject.getAllSubsFor(1,2,3);
		verify(subject);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAllSubsFor() throws Exception {
		expect(subject.constructGetAllSubsForUrl(eq(1),eq(2),eq(3))).andReturn(
				this.getClass().getClassLoader().getResource("responses/bierdopje/getSubsForMany.xml").toString());
		replay(subject);
		List<Subtitle> result = subject.getAllSubsFor(1,2,3);
		verify(subject);
		assertEquals(4,result.size());
		for (Subtitle subtitle : result) {
			assertNotNull(subtitle.getDownloadUrl());
			assertNotNull(subtitle.getFileTitle());
		}
	}
}
