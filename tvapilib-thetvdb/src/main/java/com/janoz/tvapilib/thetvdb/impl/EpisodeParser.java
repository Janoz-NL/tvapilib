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


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.janoz.tvapilib.model.Art;
import com.janoz.tvapilib.model.Art.Type;
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.TvApiException;

public class EpisodeParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	private static final Log LOG = LogFactory.getLog(EpisodeParser.class);
	
	private Sh show;

	private final UrlSupplier urlSupplier;
	
	//collectedData
	private Se season = null;
	private Integer episode = null;
	private Integer theTvDbId = null;
	private String title = null;
	private String description = null;
	private Art art = null;
	private Date airDate = null;
	private Double rating = null;
	
	public EpisodeParser(UrlSupplier urlSupplier) {
		this.urlSupplier = urlSupplier;
	}

	public void reset(Sh show) {
		this.show = show;
		this.season = null;
		this.episode = null;
		this.theTvDbId = null;
		this.title = null;
		this.description = null;
		this.art = null;
		this.airDate = null;
		this.rating = null;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("id".equals(stack.get(0))) {
				theTvDbId = Integer.valueOf(content);
			} else if ("seasonnumber".equals(stack.get(0))) {
				season = show.getSeason(
						Integer.parseInt(content));
			} else if ("episodenumber".equals(stack.get(0))) {
				episode = Integer.valueOf(content);
			} else if ("episodename".equals(stack.get(0))) {
				title = content;
			} else if ("overview".equals(stack.get(0))) {
				description = content;
			} else if ("filename".equals(stack.get(0))) {
				art = new Art();
				art.setHd(false);
				art.setUrl(urlSupplier.getImageUrl(content));
				art.setType(Type.THUMB);
			} else if ("firstaired".equals(stack.get(0))) {
				airDate = parseDate(content);
			} else if ("rating".equals(stack.get(0))) {
				rating = Double.valueOf(content);
			}
		}
	}

	public Ep getEpisode(){
		if (season == null) {
			LOG.info("Episode never got a season.");
			throw new TvApiException("Episode never got a season.");
		}
		if (episode == null) {
			LOG.info("Episode never got an episode.");
			throw new TvApiException("Episode never got an episode.");
		}
		Ep result = season.getEpisode(episode);
		if (theTvDbId != null) {
			result.setTheTvDbId(theTvDbId);
		}
		if (title != null) {
			result.setTitle(title);
		}
		if (description != null) {
			result.setDescription(description);
		}
		if (airDate != null) {
			result.setAired(airDate);
		}
		if (rating != null) {
			result.setRating(rating);
		}
		result.addArt(art);
		return result;
	}

	private Date parseDate(String src) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date(df.parse(src).getTime());
		} catch (ParseException e) {
			throw new TvApiException("Unable to retreive date from '" + src + "'.", e);
		}
	}
}
