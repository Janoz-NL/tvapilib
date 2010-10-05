package com.janoz.tvapilib.thetvdb.impl.parsers;

import java.util.LinkedList;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class BaseEpisodeParser extends AbstractSaxParser {

	boolean inEpisode = false;
	private EpisodeParser episodeParser = new EpisodeParser();
	private Show show;
	
	
	
	public BaseEpisodeParser(Show show, UrlSupplier urlSupplier) {
		super();
		this.show = show;
		episodeParser.setUrlSupplier(urlSupplier);
	}

	@Override
	public void handleTagStart(LinkedList<String> stack, Attributes attributes) {
		if (!inEpisode && stack.size()==2) {
			if ("data".equals(stack.get(0)) 
					&& "episode".equals(stack.get(1))) {
				episodeParser.reset(show);
				inEpisode = true;
			}
		}
		
	}

	@Override
	public void handleContent(LinkedList<String> stack, String content) {
		if (inEpisode) {
			episodeParser.handleContent(stack.subList(2, stack.size()), content);
		}
	}
	
	@Override
	public void handleTagEnd(LinkedList<String> stack) {
		if (inEpisode && stack.size()==2) {
			if ("data".equals(stack.get(0)) 
					&& "episode".equals(stack.get(1))) {
				inEpisode = false;
			}
		}
	}

	public Episode getResult() {
		return episodeParser.getEpisode();
	}

}
