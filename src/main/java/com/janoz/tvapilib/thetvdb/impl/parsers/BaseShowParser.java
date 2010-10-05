package com.janoz.tvapilib.thetvdb.impl.parsers;

import java.util.LinkedList;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseShowParser extends AbstractSaxParser {

	boolean inShow = false;
	private ShowParser showParser = new ShowParser();
	private Show result = null;
	
	
	@Override
	public void handleTagStart(LinkedList<String> stack, Attributes attributes) {
		if (!inShow && stack.size()==2) {
			if ("data".equals(stack.get(0)) 
					&& "series".equals(stack.get(1))) {
				result = new Show();
				showParser.reset(result);
				inShow = true;
			}
		}
		
	}

	@Override
	public void handleContent(LinkedList<String> stack, String content) {
		if (inShow) {
			showParser.handleContent(stack.subList(2, stack.size()), content);
		}
	}
	
	@Override
	public void handleTagEnd(LinkedList<String> stack) {
		if (inShow && stack.size()==2) {
			if ("data".equals(stack.get(0)) 
					&& "series".equals(stack.get(1))) {
				inShow = false;
			}
		}
	}

	public Show getResult() {
		return result;
	}

	

}
