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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.TvApiException;

public class EpisodeParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
//	private static final Log LOG = LogFactory.getLog(EpisodeParser.class);

	private Sh show = null;
	private Se season = null;
	private Integer episode = null;
	private String episodeString = null;
	private String title = null;
	private String thumbUrl = null;
	private Date airDate = null;

	
	public void reset(Sh show) {
		reset(show,null);
	}
	public void reset(Se season) {
		reset(null,season);
	}
	
	private void reset(Sh show, Se season) {
		this.show = show;
		this.season = season;
		this.episode = null;
		this.episodeString = null;
		this.title = null;
		this.thumbUrl = null;
		this.airDate = null;
		
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seasonnum".equals(stack.get(0))) {
				episode = Integer.parseInt(content);
			} else if ("title".equals(stack.get(0))) {
				title = content;
			} else if ("airdate".equals(stack.get(0))) {
				airDate = parseDate(content);
			} else if ("number".equals(stack.get(0))) {
				episodeString = content;
			} else if ("screencap".equals(stack.get(0))) {
				thumbUrl = content;
			}
		}
	}

	public Ep getEpisode(){
		if (season == null) {
			season = show.getSeason(getSeasonFormEpisodeString());
			episode = getEpisodeFormEpisodeString();
		}
		if (episode == null) {
			throw new TvApiException("No episode number found.");
		}
		Ep result = season.getEpisode(episode);
		if (title != null) {
			result.setTitle(title);
		}
		if (thumbUrl != null) {
			result.setThumbUrl(thumbUrl);
		}
		if (airDate != null) {
			result.setAired(airDate);
		}		
		return result;
	}
	
	private Integer getEpisodeFormEpisodeString() {
		if (episodeString == null) {
			throw new TvApiException("No episode number found in result.");
		}
		return Integer.valueOf(episodeString.split("x")[1]);
	}

	private Integer getSeasonFormEpisodeString() {
		if (episodeString == null) {
			throw new TvApiException("No season found in result.");
		}
		return Integer.valueOf(episodeString.split("x")[0]);
	}
	
	private Date parseDate(String date){
		int y = Integer.parseInt(date.substring(0,4));
		int m = Integer.parseInt(date.substring(5,7));
		int d = Integer.parseInt(date.substring(8));
		if (m==0) {
			y++;
		}
		if (d==0) {
			m++;
		}
		Calendar cal = new GregorianCalendar(y,m - 1,d);
		return cal.getTime();
	}
}
