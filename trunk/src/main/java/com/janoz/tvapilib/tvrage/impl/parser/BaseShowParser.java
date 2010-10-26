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
package com.janoz.tvapilib.tvrage.impl.parser;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Season;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseShowParser extends AbstractSaxParser {

	private static final String EPISODELIST = "episodelist";
	private static final String SEASON = "season";
	private static final String EPISODE = "episode";
	
	private ParseState parseState = null;
	
	private String rootTag = null;
	
	private Show result = null;
	private Season currentSeason = null;
	private ShowParser showParser = new ShowParser();
	private EpisodeParser episodeParser = new EpisodeParser();
	
	public BaseShowParser(String rootTag) {
		this.rootTag = rootTag;
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (parseState == ParseState.IN_SEASON && stackEquals(rootTag,EPISODELIST,SEASON,EPISODE)) {
			episodeParser.reset();
			parseState = ParseState.IN_EPISODE;
		}
		if (parseState == ParseState.IN_EPISODELIST && stackEquals(rootTag,EPISODELIST,SEASON)) {
			int season = Integer.valueOf(attributes.getValue("no"));
			currentSeason = result.getSeason(season);
			parseState = ParseState.IN_SEASON;
		}
		if (parseState == ParseState.IN_SHOW && stackEquals(rootTag,EPISODELIST)) {
			parseState = ParseState.IN_EPISODELIST;
		}
		if (parseState == null && stackEquals(rootTag)) {
			result = new Show();
			showParser.reset(result);
			parseState = ParseState.IN_SHOW;
		}
	}

	@Override
	public void handleContent(String content) {
		if (parseState == ParseState.IN_SHOW) {
			showParser.handleContent(getStackTail(1), content);
		} else if (parseState == ParseState.IN_EPISODE) {
			episodeParser.handleContent(getStackTail(4), content);
		}
	}
	
	@Override
	public void handleTagEnd() {
		if (parseState == ParseState.IN_SHOW && stackEquals(rootTag)) {
			parseState = null;
		} 
		if (parseState == ParseState.IN_EPISODELIST && stackEquals(rootTag,EPISODELIST)) {
			parseState = ParseState.IN_SHOW;
		}
		if (parseState == ParseState.IN_SEASON && stackEquals(rootTag,EPISODELIST,SEASON)) {
			parseState = ParseState.IN_EPISODELIST;
		}
		if (parseState == ParseState.IN_EPISODE && stackEquals(rootTag,EPISODELIST,SEASON,EPISODE)) {
			parseState = ParseState.IN_SEASON;
			Episode ep = episodeParser.getEpisode();
			ep.setSeason(currentSeason);
			currentSeason.addEpisode(ep);
		}
	}

	public Show getResult(){
		return result;
	}

	private enum ParseState{
		IN_SHOW,
		IN_EPISODELIST,
		IN_SEASON,
		IN_EPISODE;
	}
}
