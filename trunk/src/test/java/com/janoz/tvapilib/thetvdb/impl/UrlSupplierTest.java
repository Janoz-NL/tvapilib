package com.janoz.tvapilib.thetvdb.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.thetvdb.impl.UrlSupplier.MirrorType;

public class UrlSupplierTest {

	UrlSupplier subject;
	
	@Before
	public void setup() throws Exception {
		//UrlSupplier.class.getDeclaredConstructor().setAccessible(true);
		subject = createMockBuilder(UrlSupplier.class)
			.addMockedMethod("getMirrorUrl").withConstructor().createMock();
	}
	
	@Test
	public void testMirrorInit() throws Exception {
		expect(subject.getMirrorUrl()).andReturn(
				this.getClass().getClassLoader().getResource("responses/thetvdb/mirrors.xml").toString());
		replay(subject);
		Method method = UrlSupplier.class.getDeclaredMethod("initMirrors");
		method.setAccessible(true);
		method.invoke(subject);
		verify(subject);
		Field field = UrlSupplier.class.getDeclaredField("mirrors");
		field.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<MirrorType, List<String>> mirrors = 
			(Map<MirrorType, List<String>>) field.get(subject);
		assertEquals("http://thetvdb.com",mirrors.get(MirrorType.XML).get(0));
		assertEquals("http://thetvdb.com",mirrors.get(MirrorType.BANNER).get(0));
		assertEquals("http://thetvdb.com",mirrors.get(MirrorType.ZIP).get(0));
	}
}
