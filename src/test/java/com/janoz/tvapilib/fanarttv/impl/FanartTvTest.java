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
		expect(subject.getUrl(eq(164301))).andReturn(
				this.getClass().getClassLoader().getResource("responses/fanart.tv/164301.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setTheTvDbId(164301);
		subject.addClearLogos(show);
		verify(subject);
		Fanart f = show.getLogos().get(0);
		assertEquals("http://www.lockstockmods.net/logos/164301/clearart/Nikita-164301.png",f.getUrl());
		assertEquals(FanartType.LOGO, f.getType());
		f = show.getLogos().get(1);
		assertEquals("http://www.lockstockmods.net/logos/164301/clearart/Nikita-164301-2.png",f.getUrl());
		assertEquals(FanartType.LOGO, f.getType());
	}

	@Test
	public void addLogoUnknownTest() throws Exception {
		expect(subject.getUrl(eq(1))).andReturn(
				this.getClass().getClassLoader().getResource("responses/fanart.tv/1.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setTheTvDbId(1);
		subject.addClearLogos(show);
		verify(subject);
		assertTrue("ShouldBeUntouched",show.getLogos().isEmpty());
	}

}