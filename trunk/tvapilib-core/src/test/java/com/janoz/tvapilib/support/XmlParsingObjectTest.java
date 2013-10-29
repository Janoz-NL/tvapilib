package com.janoz.tvapilib.support;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

public class XmlParsingObjectTest {

	private AbstractSaxParser parserMock;
	private XmlParsingObject subject = new XmlParsingObject() {

		@Override
		public void parse(AbstractSaxParser parser, InputStream inputStream) {
			super.parse(parser, inputStream);
		}

		@Override
		public InputStream openStream(String url) {
			return super.openStream(url);
		}
	};
	
	@Before
	public void setup() {
		parserMock = createMockBuilder(AbstractSaxParser.class).withConstructor().createMock();
	}
	
	@Test
	public void testParse() {
		parserMock.handleTagStart(isA(Attributes.class));
		parserMock.handleTagStart(isA(Attributes.class));
		parserMock.handleContent(eq("content"));
		parserMock.handleTagEnd();
		parserMock.handleTagStart(isA(Attributes.class));
		parserMock.handleContent(eq("content2"));
		parserMock.handleTagEnd();
		parserMock.handleTagEnd();
		
		replay(parserMock);
		subject.parse(parserMock, subject.openStream(getResource("data.xml")));
		verify(parserMock);
	}
	
	@Test
	public void testParseSaxError() {
		try {
			subject.parse(parserMock, subject.openStream(getResource("data_ERR.xml")));
			fail();
		} catch (TvApiException tae) {
			assertEquals(SAXParseException.class, tae.getCause().getClass());
			assertEquals(tae.getMessage(), tae.getCause().getMessage());
			assertNotNull(tae.getMessage());
		}
	}
	
	@Test
	public void testParseIOError() {
		final IOException ioe = new IOException("MSG");
		try {
			subject.parse(parserMock, new InputStream() {
				
				@Override
				public int read() throws IOException {
					throw ioe;
				}
			});
			fail();
		} catch (TvApiException tae) {
			assertSame(ioe, tae.getCause());
			assertEquals("IO error while parsing XML data.", tae.getMessage());
			assertNotNull(tae.getMessage());
		}
	}
	
	
	private String getResource(String filename) {
		return this.getClass().getClassLoader().getResource("testxml/"+filename).toString();
	}
	
}
