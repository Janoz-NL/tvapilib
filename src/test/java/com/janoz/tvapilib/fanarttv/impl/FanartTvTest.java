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
package com.janoz.tvapilib.fanarttv.impl;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.fanarttv.impl.DefaultFanartTvImpl;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.model.impl.Show;
public class FanartTvTest {
	
	DefaultFanartTvImpl subject;
	
	@Before
	public void setup() {
		subject = createMockBuilder(DefaultFanartTvImpl.class)
			.addMockedMethod("getUrl").withConstructor().createMock();
		
	}
	
	@Test
	public void addLogoTest() throws Exception {
		expect(subject.getUrl(eq(153021))).andReturn(
				this.getClass().getClassLoader().getResource("responses/fanart.tv/153021.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setTheTvDbId(153021);
		subject.addFanart(show);
		verify(subject);
		Fanart f = show.getClearlogos().get(0);
		assertEquals("http://fanart.tv/fanart/153021/clearlogo/TheWalkingDead-153021.png",f.getUrl());
		assertEquals(FanartType.CLEARLOGO, f.getType());
		assertEquals(4, show.getClearlogos().size());
		f = show.getCleararts().get(0);
		assertEquals("http://fanart.tv/fanart/153021/clearart/T_153021 (2).png",f.getUrl());
		assertEquals(FanartType.CLEARART, f.getType());
		assertEquals(5, show.getCleararts().size());
		f = show.getThumbs().get(0);
		assertEquals("http://fanart.tv/fanart/153021/tvthumb/T_153021.jpg",f.getUrl());
		assertEquals(FanartType.THUMB, f.getType());
		assertEquals(4, show.getThumbs().size());
	}

	@Test
	public void addLogoUnknownTest() throws Exception {
		expect(subject.getUrl(eq(1))).andReturn(
				this.getClass().getClassLoader().getResource("responses/fanart.tv/1.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setTheTvDbId(1);
		subject.addFanart(show);
		verify(subject);
		assertTrue("ShouldBeUntouched",show.getClearlogos().isEmpty());
		assertTrue("ShouldBeUntouched",show.getCleararts().isEmpty());
		assertTrue("ShouldBeUntouched",show.getThumbs().isEmpty());
	}

}
