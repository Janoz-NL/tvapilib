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
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseShowParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends AbstractSaxParser {

	private static final String EPISODELIST = "episodelist";
	private static final String SEASON = "season";
	private static final String EPISODE = "episode";
	
	private ParseState parseState = null;
	
	private String rootTag = null;
	
	private Sh result = null;
	private Se currentSeason = null;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	private final ShowParser<Sh,Se,Ep> showParser;
	private final EpisodeParser<Sh,Se,Ep> episodeParser;
	
	public BaseShowParser(ModelFactory<Sh,Se,Ep> modelFactory, String rootTag) {
		this.modelFactory = modelFactory;
		this.rootTag = rootTag;
		showParser = new ShowParser<Sh,Se,Ep>();
		episodeParser = new EpisodeParser<Sh,Se,Ep>();
		
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (parseState == ParseState.IN_SEASON && stackEquals(rootTag,EPISODELIST,SEASON,EPISODE)) {
			episodeParser.reset(currentSeason);
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
			result = modelFactory.newShow();
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
			episodeParser.getEpisode();
		}
	}

	public Sh getResult(){
		return result;
	}

	private enum ParseState{
		IN_SHOW,
		IN_EPISODELIST,
		IN_SEASON,
		IN_EPISODE;
	}
}
