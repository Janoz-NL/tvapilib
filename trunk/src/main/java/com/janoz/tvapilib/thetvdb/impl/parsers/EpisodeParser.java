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
package com.janoz.tvapilib.thetvdb.impl.parsers;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.TvException;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class EpisodeParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	private static final Log LOG = LogFactory.getLog(EpisodeParser.class);
	
	private Ep episode;
	private Sh show;
	private Se season;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	private final UrlSupplier urlSupplier;
	private boolean done = false;
	
	public EpisodeParser(ModelFactory<Sh,Se,Ep> modelFactory, UrlSupplier urlSupplier) {
		this.modelFactory = modelFactory;
		this.urlSupplier = urlSupplier;
	}

	public void reset(Sh show) {
		this.show = show;
		this.episode = modelFactory.newEpisode();
		this.done = false;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("id".equals(stack.get(0))) {
				episode.setTheTvDbId(Integer.parseInt(content));
			} else if ("seasonnumber".equals(stack.get(0))) {
				season = show.getSeason(
						Integer.parseInt(content));
			} else if ("episodenumber".equals(stack.get(0))) {
				episode.setEpisode(Integer.parseInt(content));
			} else if ("episodename".equals(stack.get(0))) {
				episode.setTitle(content);
			} else if ("overview".equals(stack.get(0))) {
				episode.setDescription(content);
			} else if ("filename".equals(stack.get(0))) {
				episode.setThumbUrl(urlSupplier.getImageUrl(content));
			} else if ("firstaired".equals(stack.get(0))) {
				episode.setAired(parseDate(content));
			}
		}
	}

	public Ep getEpisode(){
		if (!done) {
			if (season == null) {
				LOG.info("Episode never got a season.");
				throw new TvException("Episode never got a season.");
			}
			episode.setSeason(season);
			season.addEpisode(episode);
			done=true;
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
}
