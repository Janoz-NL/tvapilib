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

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractSaxParser extends DefaultHandler {
	
	private LinkedList<String> stack = null;

	@Override
	public void startDocument() throws SAXException {
		stack = new LinkedList<String>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		stack.add(localName.toLowerCase());
		handleTagStart(attributes);
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String content = new String(ch,start,length).trim();
		if (content.length() > 0) {
			handleContent(content);
		}
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		handleTagEnd();
		if (!localName.equalsIgnoreCase(stack.removeLast())) {
			throw new SAXException("Unexpected endtag '"+localName+"'");
		}

	}
	@Override
	public void endDocument() throws SAXException {
		if (!stack.isEmpty()) {
			throw new SAXException("Unexpected end of document.");
		}

	}


	public abstract void handleTagStart(Attributes attributes );
	public abstract void handleContent(String content);
	public abstract void handleTagEnd();
	
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
	
	protected boolean stackStartsWith(String... nodeNames) {
		if (nodeNames.length > stack.size()) {
			return false;
		}
		int i=0;
		for (String node:stack) {
			if (i == nodeNames.length) {
				return true;
			}
			if (!node.equals(nodeNames[i])) {
				return false;
			}
			i++;
		}
		return true;
	}

	protected List<String> getStackTail(int offset) {
		return stack.subList(offset, stack.size());
	}
	
	protected String getNodeName() {
		return stack.getLast();
	}
	
	protected boolean isStackSize(int i) {
		return i == stack.size();
	}
}
