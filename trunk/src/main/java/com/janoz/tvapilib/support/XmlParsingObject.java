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
package com.janoz.tvapilib.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public abstract class XmlParsingObject {
	
	protected void parse(AbstractSaxParser parser,InputStream inputStream) {
		try {
			InputSource input = new InputSource(inputStream);
			XMLReader reader = XMLReaderFactory.createXMLReader(); 
			reader.setContentHandler(parser);
			reader.parse(input);
		} catch (SAXException e) {
			throw new TvException("Error parsing XML data.",e);
		} catch (IOException e) {
			throw new TvException("IOError while parsing XML data.",e);
		}
	}
	
	protected InputStream openStream(String url) {
		try {
			return new URL(url).openStream();
		} catch (IOException e) {
			throw new TvException("Unable to open XML data");
		}
	}
	
	protected InputStream openZippedStream(String url) {
		return new ZipInputStream(openStream(url));
	}
}
