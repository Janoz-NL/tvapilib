/*******************************************************************************
 * Copyright (c) 2011 Gijs de Vries aka Janoz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gijs de Vries aka Janoz - initial API and implementation
 ******************************************************************************/
package com.janoz.tvapilib.xbmcnfo.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import com.janoz.tvapilib.model.impl.Show;

public class XbmcNfoTest {

	DefaultXbmcNfoImpl subject;
	
	@Before
	public void setup() {
		subject = new DefaultXbmcNfoImpl();
	}
	
	@Test
	public void testLoadShow() throws Exception{
		File showNfo = getResource("tvshow.nfo");
		Show show = subject.loadShow(showNfo);
		assertEquals("ShowId unexpected", Integer.valueOf(153021), show.getTheTvDbId());
	}

	@Test
	public void testLoadShowNoTvDbId() throws Exception{
		File showNfo = getResource("tvshow_notvdbid.nfo");
		Show show = subject.loadShow(showNfo);
		assertEquals("ShowId unexpected", Integer.valueOf(153021), show.getTheTvDbId());
		assertNull("ImdbId unexpected", show.getImdbId());
	}
	@Test
	public void testLoadShowBothId() throws Exception{
		File showNfo = getResource("tvshow_both.nfo");
		Show show = subject.loadShow(showNfo);
		assertEquals("ShowId unexpected", Integer.valueOf(153021), show.getTheTvDbId());
		assertEquals("ImdbId unexpected", "tt1520211", show.getImdbId());
	}
	@Test
	public void testLoadShowOnlyImdbId() throws Exception{
		File showNfo = getResource("tvshow_onlyimdb.nfo");
		Show show = subject.loadShow(showNfo);
		assertNull("ShowId unexpected", show.getTheTvDbId());
		assertEquals("ImdbId unexpected", "tt1520211", show.getImdbId());
	}

	private File getResource(String filename) throws URISyntaxException {
		return new File(this.getClass().getClassLoader().getResource("responses/xbmcnfo/"+filename).toURI());
	}
}
