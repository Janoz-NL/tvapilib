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
package com.janoz.tvapilib.lockstockmods.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.model.Show;
public class LockStockModsTest {
	
	LockStockModsImpl subject;
	
	@Before
	public void setup() {
		subject = createMockBuilder(LockStockModsImpl.class)
			.addMockedMethod("getUrl").withConstructor().createMock();
		
	}
	
	@Test
	public void addLogoTest() throws Exception {
		expect(subject.getUrl(eq(164301))).andReturn(
				this.getClass().getClassLoader().getResource("responses/lockstockmods/164301.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setId(164301);
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
				this.getClass().getClassLoader().getResource("responses/lockstockmods/1.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setId(1);
		subject.addClearLogos(show);
		verify(subject);
		assertTrue("ShouldBeUntouched",show.getLogos().isEmpty());
	}

}
