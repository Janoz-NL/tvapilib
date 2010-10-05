/*******************************************************************************
 * Copyright 2010 Gijs de Vries aka Janoz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.janoz.tvapilib.thetvdb.impl;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.helpers.XMLReaderFactory;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Season;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.thetvdb.TheTVDB;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseEpisodeParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseShowParser;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class TheTVDBImpl extends XmlParsingObject implements TheTVDB {

	private static final Logger LOG = Logger.getLogger(TheTVDBImpl.class);

	private UrlSupplier urlSupplier;
	
	/**
	 * @param apiKey
	 */
	public TheTVDBImpl(String apiKey) {
		urlSupplier = new UrlSupplier(apiKey);
	}

	@Override
	public Show getShow(int showId) {
		BaseShowParser parser = new BaseShowParser();
		parse(parser,urlSupplier.getBaseShowUrl(showId).toString());
		return parser.getResult();
	}

	@Override
	public Episode getEpisode(int showId, int season, int episode) {
		Show show = new Show();
		show.setId(showId);
		return getEpisode(show, season, episode);
	}

	@Override
	public Episode getEpisode(Show show, int season, int episode) {
		BaseEpisodeParser parser = new BaseEpisodeParser(show,urlSupplier);
		parse(parser,urlSupplier.getBaseEpisodeUrl(show.getId(),
				season, episode).toString());
		return parser.getResult();
	}
	
	public void getBanners(Show show) {
		System.out.println(urlSupplier.getBaseShowUrl(show.getId()).append("/banner.xml").toString());
	}


	
	/* * * Below is for test purposes. * * */
	
	/*
	 * Constructor so class can be instantiated without initializing UrlSupplier.
	 */
	@SuppressWarnings("unused")
	private TheTVDBImpl(){  //NOSONAR
		//Added for test purposes..
	}
}
