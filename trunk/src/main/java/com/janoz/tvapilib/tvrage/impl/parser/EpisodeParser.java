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
import com.janoz.tvapilib.model.ModelFactory;

public class EpisodeParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
//	private static final Log LOG = LogFactory.getLog(EpisodeParser.class);

	private Ep episode = null;
	private String episodeString = null;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	
	public EpisodeParser(ModelFactory<Sh,Se,Ep> modelFactory) {
		this.modelFactory = modelFactory;
	}
	
	public void reset() {
		this.episode = modelFactory.newEpisode();
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

	public Ep getEpisode(){
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
