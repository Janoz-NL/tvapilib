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
package com.janoz.tvapilib.thetvdb.impl.parsers;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Season;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.TvException;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class EpisodeParser {
	
	private static final Logger LOG = Logger.getLogger(EpisodeParser.class);
	
	Episode episode;
	Show show;
	Season season;
	UrlSupplier urlSupplier;
	boolean done = false;

	public void reset(Show show) {
		this.show = show;
		this.episode = new Episode();
		this.done = false;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("id".equals(stack.get(0))) {
				episode.setId(Integer.parseInt(content));
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

	public Episode getEpisode(){
		if (!done) {
			if (season == null) {
				throw new TvException("Parsing incomplete");
			}
			season.addEpisode(episode);
			episode.setSeason(season);
			done=true;
		}
		return episode;
	}
	
	
	public void setUrlSupplier(UrlSupplier urlSupplier) {
		this.urlSupplier = urlSupplier;
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
