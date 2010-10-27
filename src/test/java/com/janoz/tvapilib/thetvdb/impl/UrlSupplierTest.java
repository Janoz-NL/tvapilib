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
package com.janoz.tvapilib.thetvdb.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class UrlSupplierTest {

	private static final String API_KEY = "**API_KEY**";
	UrlSupplier subject;
	
	@Before
	public void setup() throws Exception {
		//UrlSupplier.class.getDeclaredConstructor().setAccessible(true);
		subject = createMockBuilder(UrlSupplier.class)
			.addMockedMethod("getMirrorUrl").withConstructor().createMock();
	}
	
	@Test
	public void testMirrorInit() throws Exception {
		initWithMirrorConfig("responses/thetvdb/mirrors.xml");
		
		Map<MirrorType, List<String>> mirrors = getMirrorsFrom(subject);
		List<String> xmlMirrors = mirrors.get(MirrorType.XML);
		List<String> bannerMirrors = mirrors.get(MirrorType.BANNER);
		List<String> zipMirrors = mirrors.get(MirrorType.ZIP);
		
		assertTrue(xmlMirrors.contains("http://thetvdb.com"));
		assertTrue(bannerMirrors.contains("http://thetvdb.com"));
		assertTrue(zipMirrors.contains("http://thetvdb.com"));
	}
	
	@Test
	public void testMirrorInitExtended() throws Exception {
		initWithMirrorConfig("responses/thetvdb/bigmirrors.xml");
		
		Map<MirrorType, List<String>> mirrors = getMirrorsFrom(subject);
		List<String> xmlMirrors = mirrors.get(MirrorType.XML);
		List<String> bannerMirrors = mirrors.get(MirrorType.BANNER);
		List<String> zipMirrors = mirrors.get(MirrorType.ZIP);
		
		assertTrue(xmlMirrors.contains("http://all.com"));
		assertTrue(xmlMirrors.contains("http://bannerxml.com"));
		assertTrue(xmlMirrors.contains("http://zipxml.com"));
		assertTrue(xmlMirrors.contains("http://xml.com"));
		assertEquals(4,xmlMirrors.size());
		assertTrue(bannerMirrors.contains("http://all.com"));
		assertTrue(bannerMirrors.contains("http://bannerxml.com"));
		assertTrue(bannerMirrors.contains("http://zipbanner.com"));
		assertTrue(bannerMirrors.contains("http://banner.com"));
		assertEquals(4,bannerMirrors.size());
		assertTrue(zipMirrors.contains("http://all.com"));
		assertTrue(zipMirrors.contains("http://zipbanner.com"));
		assertTrue(zipMirrors.contains("http://zipxml.com"));
		assertTrue(zipMirrors.contains("http://zip.com"));
		assertEquals(4,zipMirrors.size());
	}
	
	@Test
	public void testShowSearchUrl() throws Exception{
		assertEquals("http://www.thetvdb.com/api/GetSeries.php?seriesname=SomeShowName",subject.getShowSearchUrl("SomeShowName"));
	}
	
	@Test
	public void testGetEpisodeUrl() throws Exception{
		initWithMirrorConfig("responses/thetvdb/singlemirrors.xml");
		assertEquals("http://xml.com/api/**API_KEY**/series/1/default/2/3",subject.getBaseEpisodeUrl(1, 2, 3).toString());

	}

	@Test
	public void testGetShowUrl() throws Exception{
		initWithMirrorConfig("responses/thetvdb/singlemirrors.xml");
		assertEquals("http://xml.com/api/**API_KEY**/series/1",subject.getBaseShowUrl(1).toString());
	}

	@Test
	public void testGetImageUrl() throws Exception{
		initWithMirrorConfig("responses/thetvdb/singlemirrors.xml");
		assertEquals("http://banner.com/banners/post",subject.getImageUrl("post"));
	}

	@SuppressWarnings("unchecked")
	private Map<MirrorType, List<String>> getMirrorsFrom(UrlSupplier object) throws Exception {
		Field field = UrlSupplier.class.getDeclaredField("mirrors");
		field.setAccessible(true);
		return (Map<MirrorType, List<String>>) field.get(subject);
	}
	
	private void invokeInitMirrors(UrlSupplier object) throws Exception {
		Method method = UrlSupplier.class.getDeclaredMethod("initMirrors");
		method.setAccessible(true);
		method.invoke(object);		
	}
	
	private void initWithMirrorConfig(String mirrorDataUri) throws Exception {
		expect(subject.getMirrorUrl()).andReturn(
				this.getClass().getClassLoader().getResource(mirrorDataUri).toString());
		replay(subject);
		invokeInitMirrors(subject);
		verify(subject);
		
		Field field = UrlSupplier.class.getDeclaredField("apiKey");
		field.setAccessible(true);
		field.set(subject,API_KEY);
	}
}
