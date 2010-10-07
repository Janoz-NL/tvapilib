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

import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractSaxParser extends DefaultHandler {
	
	private LinkedList<String> stack;

	@Override
	public void startDocument() throws SAXException {
		stack = new LinkedList<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		stack.add(localName.toLowerCase());
		handleTagStart(stack, attributes);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String content = new String(ch,start,length).trim();
		if (content.length() > 0) {
			handleContent(stack, content);
		}
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		handleTagEnd(stack);
		if (!localName.toLowerCase().equals(stack.removeLast())) {
			throw new SAXException("Unexpected endtag '"+localName+"'");
		}

	}
	@Override
	public void endDocument() throws SAXException {
		if (!stack.isEmpty()) {
			throw new SAXException("Unexpected end of document.");
		}

	}


	public abstract void handleTagStart(LinkedList<String> stack, Attributes attributes );
	public abstract void handleContent(LinkedList<String> stack, String content);
	public abstract void handleTagEnd(LinkedList<String> stack);
	
	protected boolean stackEquals(String... nodeNames) {
		if (nodeNames.length != stack.size()) {
			return false;
		}
		int i=0;
		for (String node:stack) {
			if (!node.equals(nodeNames[i])) {
				return false;
			}
			i++;
		}
		return true;
	}

}
