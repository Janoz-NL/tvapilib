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
package com.janoz.tvapilib.tvrage.impl;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseEpisodeParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends AbstractSaxParser {
	
	private static final String SHOW = "show";
	private static final String EPISODE = "episode";
	private static final String LAST_EPISODE = "latestepisode";
	private static final String NEXT_EPISODE = "nextepisode";

	private final ShowParser<Sh,Se,Ep> showParser;
	private final EpisodeParser<Sh,Se,Ep> episodeParser;
	
	private ParseState parseState = null;
	
	private Sh show = null;
	private Ep episode = null;
//	private Episode lastEpisode = null;
//	private Episode nextEpisode = null;
	
	public BaseEpisodeParser(Sh show) {
		this.show = show;
		this.showParser = new ShowParser<Sh,Se,Ep>();
		this.episodeParser = new EpisodeParser<Sh,Se,Ep>();
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,EPISODE)) {
			episodeParser.reset(show);
			parseState = ParseState.IN_EPISODE;
		}
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,LAST_EPISODE)) {
			episodeParser.reset(show);
			parseState = ParseState.IN_LAST_EPISODE;
		}
		if (parseState == ParseState.IN_SHOW && stackEquals(SHOW,NEXT_EPISODE)) {
			episodeParser.reset(show);
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
			episode = episodeParser.getEpisode();
		}
		if (parseState == ParseState.IN_LAST_EPISODE && stackEquals(SHOW,EPISODE)) {
			parseState = ParseState.IN_SHOW;
			episodeParser.getEpisode();
		}
		if (parseState == ParseState.IN_NEXT_EPISODE && stackEquals(SHOW,EPISODE)) {
			parseState = ParseState.IN_SHOW;
			episodeParser.getEpisode();
		}
	}
	
	public Ep getResult() {
		return episode;
	}
	
	private enum ParseState {
		IN_SHOW,
		IN_EPISODE,
		IN_LAST_EPISODE,
		IN_NEXT_EPISODE;
	}
}
