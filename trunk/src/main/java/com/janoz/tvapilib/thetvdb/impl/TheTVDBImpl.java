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

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.thetvdb.TheTVDB;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class TheTVDBImpl extends XmlParsingObject implements TheTVDB {

	private static final Logger LOG = Logger.getLogger(TheTVDBImpl.class);

	private UrlSupplier urlSupplier;
	
	/**
	 * @param apiKey
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public TheTVDBImpl(String apiKey) throws ParserConfigurationException,
			SAXException, IOException {
		urlSupplier = new UrlSupplier(apiKey);
	}

	@Override
	public Show getShow(int showId) throws ParserConfigurationException,
			SAXException, IOException, DOMException, ParseException {
		Document doc = fetchFeed(urlSupplier.getBaseShowUrl(showId).toString());
		return parseShow(getChildNodeByName(doc, "data", "series"));
	}

	@Override
	public Episode getEpisode(int showId, int season, int episode)
			throws ParserConfigurationException, SAXException, IOException,
			DOMException, ParseException {
		Show show = new Show();
		show.setId(showId);
		return getEpisode(show, season, episode);
	}

	@Override
	public Episode getEpisode(Show show, int season, int episode)
			throws ParserConfigurationException, SAXException, IOException,
			DOMException, ParseException {
		Document doc = fetchFeed(urlSupplier.getBaseEpisodeUrl(show.getId(),
				season, episode).toString());
		return parseEpisode(getChildNodeByName(doc, "data", "episode"), show);
	}

	private Show parseShow(Node showNode) throws DOMException {
		Show show = new Show();
		for (Node node : new ChildIterator(showNode)) {
			if ("SeriesID".equalsIgnoreCase(node.getNodeName())) {
				show.setId(Integer.parseInt(node.getTextContent()));
			} else if ("SeriesName".equalsIgnoreCase(node.getNodeName())) {
				show.setTitle(node.getTextContent());
			} else if ("fanart".equalsIgnoreCase(node.getNodeName())) {
				show.setFanartUrl(urlSupplier.getImageUrl(node.getTextContent()));
			} else if ("poster".equalsIgnoreCase(node.getNodeName())) {
				show.setPosterUrl(urlSupplier.getImageUrl(node.getTextContent()));
			} else if ("banner".equalsIgnoreCase(node.getNodeName())) {
				show.setBannerUrl(urlSupplier.getImageUrl(node.getTextContent()));
			}
		}
		return show;
	}

	private Episode parseEpisode(Node episodeNode, Show show)
			throws DOMException {
		Episode episode = new Episode();
		episode.setShow(show);
		for (Node node : new ChildIterator(episodeNode)) {
			if ("id".equalsIgnoreCase(node.getNodeName())) {
				episode.setId(Integer.parseInt(node.getTextContent()));
			} else if ("SeasonNumber".equalsIgnoreCase(node.getNodeName())) {
				episode.setSeason(Integer.parseInt(node.getTextContent()));
			} else if ("EpisodeNumber".equalsIgnoreCase(node.getNodeName())) {
				episode.setEpisode(Integer.parseInt(node.getTextContent()));
			} else if ("EpisodeName".equalsIgnoreCase(node.getNodeName())) {
				episode.setTitle(node.getTextContent());
			} else if ("Overview".equalsIgnoreCase(node.getNodeName())) {
				episode.setDescription(node.getTextContent());
			} else if ("filename".equalsIgnoreCase(node.getNodeName())) {
				episode.setThumbUrl(urlSupplier.getImageUrl(node
						.getTextContent()));
			} else if ("FirstAired".equalsIgnoreCase(node.getNodeName())) {
				episode.setAired(parseDate(node.getTextContent()));
			}
		}
		return episode;
	}

	private Date parseDate(String src) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date(df.parse(src).getTime());
		} catch (ParseException e) {
			LOG.warn("Unable to retreive date from '" + src + "'.", e);
			return null;
		}
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
