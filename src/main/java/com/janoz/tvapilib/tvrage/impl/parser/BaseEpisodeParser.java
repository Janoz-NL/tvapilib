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

public class BaseEpisodeParser extends AbstractSaxParser {
	
	private static final String SHOW = "show";
	private static final String EPISODE = "episode";
	private static final String LAST_EPISODE = "latestepisode";
	private static final String NEXT_EPISODE = "nextepisode";

	private ShowParser showParser = new ShowParser();
	private EpisodeParser episodeParser = new EpisodeParser();
	
	private ParseState parseState = null;
	
	private Show show = null;
	private Episode episode = null;
//	private Episode lastEpisode = null;
//	private Episode nextEpisode = null;
	
	public BaseEpisodeParser() {
		this(new Show());
	}
	
	public BaseEpisodeParser(Show show) {
		this.show = show;
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,EPISODE)) {
			episodeParser.reset();
			parseState = ParseState.IN_EPISODE;
		}
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,LAST_EPISODE)) {
			episodeParser.reset();
			parseState = ParseState.IN_LAST_EPISODE;
		}
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,NEXT_EPISODE)) {
			episodeParser.reset();
			parseState = ParseState.IN_NEXT_EPISODE;
		}
		if (parseState == null && stackEquals(SHOW)) {
			show.setTvRageId(Integer.valueOf(attributes.getValue("id")));
			showParser.reset(show);
			parseState = ParseState.IN_SHOW;
		}
	}

	@Override
	public void handleContent(String content) {
		if (parseState == ParseState.IN_SHOW) {
			showParser.handleContent(getStackTail(1), content);
		} 
		if (parseState == ParseState.IN_EPISODE) {
			episodeParser.handleContent(getStackTail(2), content);
		}
		if (parseState == ParseState.IN_LAST_EPISODE) {
			episodeParser.handleContent(getStackTail(2), content);
		}
		if (parseState == ParseState.IN_NEXT_EPISODE) {
			episodeParser.handleContent(getStackTail(2), content);
		}
	}

	@Override
	public void handleTagEnd() {
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW)) {
			parseState = null;
		} 
		if (parseState == ParseState.IN_EPISODE && stackEquals(SHOW,EPISODE)) {
			parseState = ParseState.IN_SHOW;
			episode = getEpisodeFromParser();
		}
		if (parseState == ParseState.IN_LAST_EPISODE && stackEquals(SHOW,EPISODE)) {
			parseState = ParseState.IN_SHOW;
			getEpisodeFromParser();
		}
		if (parseState == ParseState.IN_NEXT_EPISODE && stackEquals(SHOW,EPISODE)) {
			parseState = ParseState.IN_SHOW;
			getEpisodeFromParser();
		}
	}

	private Episode getEpisodeFromParser() {
		Episode ep = episodeParser.getEpisode();
		ep.setEpisode(episodeParser.getEpisodeFormEpisodeString());
		Season season = show.getSeason(episodeParser.getSeasonFormEpisodeString());
		ep.setSeason(season);
		season.addEpisode(ep);
		return ep;
	}

	
	public Episode getResult() {
		return episode;
	}
	
	private enum ParseState {
		IN_SHOW,
		IN_EPISODE,
		IN_LAST_EPISODE,
		IN_NEXT_EPISODE;
	}
}
