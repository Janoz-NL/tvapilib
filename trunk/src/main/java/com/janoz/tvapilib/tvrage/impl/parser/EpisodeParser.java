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

import com.janoz.tvapilib.model.Episode;

public class EpisodeParser {
	
//	private static final Log LOG = LogFactory.getLog(EpisodeParser.class);

	private Episode episode;
	private String episodeString;
	
	public void reset() {
		this.episode = new Episode();
		this.episodeString = null;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seasonnum".equals(stack.get(0))) {
				episode.setEpisode(Integer.parseInt(content));
			} else if ("title".equals(stack.get(0))) {
				episode.setTitle(content);
			} else if ("airdate".equals(stack.get(0))) {
				episode.setAired(parseDate(content));
			} else if ("number".equals(stack.get(0))) {
				episodeString = content;
			}
		}
	}

	public Episode getEpisode(){
		return episode;
	}
	
	public String getEpisodeString() {
		return episodeString;
	}
	
	public Integer getEpisodeFormEpisodeString() {
		if (episodeString == null) {
			return null;
		}
		return Integer.valueOf(episodeString.split("x")[1]);
	}

	public Integer getSeasonFormEpisodeString() {
		if (episodeString == null) {
			return null;
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
