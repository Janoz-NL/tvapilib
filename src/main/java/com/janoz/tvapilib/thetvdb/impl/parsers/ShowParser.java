package com.janoz.tvapilib.thetvdb.impl.parsers;

import java.util.List;

import org.apache.log4j.Logger;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class ShowParser {
	private static final Logger LOG = Logger.getLogger(ShowParser.class);
	
	Show show;

	public void reset(Show show) {
		this.show = show;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seriesid".equalsIgnoreCase(stack.get(0))) {
				show.setId(Integer.parseInt(content));
			} else if ("seriesname".equalsIgnoreCase(stack.get(0))) {
				show.setTitle(content);
			} 
		}
	}
	
	public Show getShow() {
		return show;
	}
}
