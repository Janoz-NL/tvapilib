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
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public abstract class XmlParsingObject {


	/* SAX IMPLEMENTATION */
	
	protected void parse(AbstractSaxParser parser,String uri) {
		try {
			InputSource input = new InputSource(new URL(uri).openStream());
			XMLReader reader = XMLReaderFactory.createXMLReader(); 
			reader.setContentHandler(parser);
			reader.parse(input);
		} catch (SAXException e) {
			throw new TvException("Error parsing document.",e);
		} catch (IOException e) {
			throw new TvException("Error communicating.",e);
		}
	}
	
	/* DOM IMPLEMENTATION */

	private final Object builderSemaphore = new Object();

	protected Document fetchFeed(String uri) {
		Document result = null;
		try {
			synchronized (builderSemaphore) {
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				factory.setValidating(false);
				DocumentBuilder builder = factory.newDocumentBuilder();
				result = builder.parse(uri);
			}
		} catch (SAXException saxe) {
			throw new TvException("",saxe);
		} catch (IOException ioe) {
			throw new TvException("",ioe);
		} catch (ParserConfigurationException pce) {
			throw new TvException("", pce);
		}
		return result;
	}

	/**
	 * Returns the first node named <code>name</code> or null if it isn't
	 * present.
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	protected Node getChildNodeByName(Node parent, String... name) {
		if (name.length == 0) {
			return parent;
		}
		NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (name[0].equalsIgnoreCase(children.item(i).getNodeName())) {
				return getChildNodeByName(children.item(i),
						Arrays.copyOfRange(name, 1, name.length));
			}
		}
		return null;
	}

	protected static class ChildIterator implements Iterable<Node>, Iterator<Node> {

		private int i = 0;
		private NodeList items;

		public ChildIterator(Node node) {
			items = node.getChildNodes();
		}

		@Override
		public boolean hasNext() {
			return i < items.getLength();
		}

		@Override
		public Node next() {
			i++;
			return items.item(i - 1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterator<Node> iterator() {
			return this;
		}

	}
}
