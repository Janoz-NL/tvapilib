package com.janoz.tvapilib.lockstockmods.impl.parsers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class LogoParser extends AbstractSaxParser {

	List<Fanart> result = new ArrayList<Fanart>();

	@Override
	public void handleTagStart(LinkedList<String> stack, Attributes attributes) {
		if (stack.size()==2
			&& "logos".equals(stack.get(0)) 
			&& "logo".equals(stack.get(1))) {

			Fanart fanart = new Fanart();
			fanart.setType(FanartType.LOGO);
			fanart.setUrl(attributes.getValue("", "url"));
			result.add(fanart);
		}
	}

	@Override
	public void handleContent(LinkedList<String> stack, String content) {
		// Do Nothing
		
	}

	@Override
	public void handleTagEnd(LinkedList<String> stack) {
		// Do Nothing
		
	}
	
	public List<Fanart> getResult() {
		return result;
	}

}
