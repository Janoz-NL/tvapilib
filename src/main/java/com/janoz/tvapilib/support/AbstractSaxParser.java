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

}
