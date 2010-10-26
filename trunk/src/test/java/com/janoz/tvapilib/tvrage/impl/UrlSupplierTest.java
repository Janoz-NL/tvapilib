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
package com.janoz.tvapilib.tvrage.impl;

import static org.junit.Assert.*;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class UrlSupplierTest {
	private static final String API_KEY = "**API_KEY**";
	UrlSupplier subject;
	
	@Before
	public void setup() {
		subject = new UrlSupplier(API_KEY);
	}
	
	@Test
	public void testShowInfo() {
		assertEquals("http://services.tvrage.com/myfeeds/showinfo.php?key=**API_KEY**&sid=123",subject.getShowInfoUrl(123));
	}

	@Test
	public void testShowInfoPublic() throws Exception {
		clearApiKey();
		assertEquals("http://services.tvrage.com/feeds/showinfo.php?sid=123",subject.getShowInfoUrl(123));
	}

	@Test
	public void testEpsideList() {
		assertEquals("http://services.tvrage.com/myfeeds/episode_list.php?key=**API_KEY**&sid=123",subject.getEpisodeListUrl(123));
	}

	@Test
	public void testEpisodeListPublic() throws Exception {
		clearApiKey();
		assertEquals("http://services.tvrage.com/feeds/episode_list.php?sid=123",subject.getEpisodeListUrl(123));
	}

	@Test
	public void testEpsideInfo() {
		assertEquals("http://services.tvrage.com/myfeeds/episodeinfo.php?key=**API_KEY**&sid=1&ep=2x3",subject.getEpisodeInfoUrl(1, 2, 3));
	}

	@Test
	public void testEpisodeInfoPublic() throws Exception {
		clearApiKey();
		assertEquals("http://services.tvrage.com/feeds/episodeinfo.php?sid=1&ep=2x3",subject.getEpisodeInfoUrl(1, 2, 3));
	}
	
	private void clearApiKey() throws Exception {
		Field field = UrlSupplier.class.getDeclaredField("apiKey");
		field.setAccessible(true);
		field.set(subject, null);
	}
}
