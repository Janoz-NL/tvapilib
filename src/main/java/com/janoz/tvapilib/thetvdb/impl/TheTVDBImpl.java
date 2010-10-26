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
package com.janoz.tvapilib.thetvdb.impl;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.thetvdb.TheTVDB;
import com.janoz.tvapilib.thetvdb.impl.parsers.BannersParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseEpisodeParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseShowParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.FullShowParser;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class TheTVDBImpl extends XmlParsingObject implements TheTVDB {

	private UrlSupplier urlSupplier;
	
	

	/**
	 * @param apiKey TVDB Apikey
	 */
	public TheTVDBImpl(String apiKey) {
		urlSupplier = new UrlSupplier(apiKey);
	}
	
	/*
	 * Constructor so class can be instantiated without initializing UrlSupplier.
	 */
	@SuppressWarnings("unused")
	private TheTVDBImpl(){
		//Added for test purposes..
	}
	
	@Override
	public Show getFullShow(int theTvDbId) {
		// TODO reimplement this using the zip file
		Show show = getShowWithEpisodes(theTvDbId);
		fillFanart(show);
		return show;
	}

	@Override
	public Show getShow(int theTvDbId) {
		String url = urlSupplier.getBaseShowUrl(theTvDbId);
		BaseShowParser parser = new BaseShowParser();
		parse(parser,openStream(url));
		return parser.getResult();
	}
	
	@Override
	public Show getShowWithEpisodes(int theTvDbId) {
		String url = urlSupplier.getFullShowUrl(theTvDbId);
		FullShowParser parser = new FullShowParser(urlSupplier);
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Episode getEpisode(int theTvDbId, int season, int episode) {
		Show show = new Show();
		show.setTheTvDbId(theTvDbId);
		return getEpisode(show, season, episode);
	}

	@Override
	public Episode getEpisode(Show show, int season, int episode) {
		String url = urlSupplier.getBaseEpisodeUrl(show.getTheTvDbId(),
				season, episode);
		BaseEpisodeParser parser = new BaseEpisodeParser(show,urlSupplier);
		parse(parser,openStream(url));
		return parser.getResult();
	}
	
	@Override
	public void fillFanart(Show show) {
		String url = urlSupplier.getBannerUrl(show.getTheTvDbId());
		BannersParser parser = new BannersParser(urlSupplier,show);
		parse(parser,openStream(url));
	}
}
