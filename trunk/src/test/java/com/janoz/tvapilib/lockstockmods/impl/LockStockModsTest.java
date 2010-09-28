package com.janoz.tvapilib.lockstockmods.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
		assertTrue(subject.addClearLogo(show));
		verify(subject);
		assertEquals("http://www.lockstockmods.net/logos/164301/clearart/Nikita-164301.png",show.getLogoUrl());
	}

	@Test
	public void addLogoUnknownTest() throws Exception {
		expect(subject.getUrl(eq(1))).andReturn(
				this.getClass().getClassLoader().getResource("responses/lockstockmods/1.xml").toString());
		replay(subject);
		Show show = new Show();
		show.setId(1);
		show.setLogoUrl("ShouldBeUntouched");
		assertFalse(subject.addClearLogo(show));
		verify(subject);
		assertEquals("ShouldBeUntouched",show.getLogoUrl());
	}

}
