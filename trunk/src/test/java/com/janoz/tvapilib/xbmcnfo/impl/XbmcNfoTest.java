package com.janoz.tvapilib.xbmcnfo.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Ignore;
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
	}

	private File getResource(String filename) throws URISyntaxException {
		return new File(this.getClass().getClassLoader().getResource("responses/xbmcnfo/"+filename).toURI());
	}
}
