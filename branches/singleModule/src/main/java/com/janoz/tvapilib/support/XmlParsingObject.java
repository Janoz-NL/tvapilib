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
package com.janoz.tvapilib.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public abstract class XmlParsingObject {
	
	private static final Log LOG = LogFactory.getLog(XmlParsingObject.class);
	
	protected void parse(AbstractSaxParser parser,InputStream inputStream) {
		try {
			InputSource input = new InputSource(inputStream);
			XMLReader reader = XMLReaderFactory.createXMLReader(); 
			reader.setContentHandler(parser);
			reader.parse(input);
		} catch (SAXException e) {
			LOG.info("Error parsing XML data.",e);
			throw new TvApiException(e.getMessage(),e);
		} catch (IOException e) {
			LOG.info("IO error while parsing XML data.",e);
			throw new TvApiException("IO error while parsing XML data.",e);
		}
	}
	
	protected InputStream openStream(String url) {
		try {
			return new URL(url).openStream();
		} catch (IOException e) {
			LOG.info("Unable to open XML data.",e);
			throw new TvApiException("Unable to open XML data.",e);
		}
	}
	
	protected InputStream openZippedStream(String url) {
		return new ZipInputStream(openStream(url));
	}
}
