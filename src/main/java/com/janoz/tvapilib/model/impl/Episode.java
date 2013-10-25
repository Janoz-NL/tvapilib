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
package com.janoz.tvapilib.model.impl;

import java.util.Date;

import com.janoz.tvapilib.model.IEpisode;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Episode implements Comparable<Episode>, IEpisode<Show,Season,Episode> {

	private Integer theTvDbId;
	private Season season = null;
	private int episode;
	private String title;
	private Date aired = null;
	private String tvRageUrl;
	private String description;
	private String thumbUrl;
	private Double rating;

	public int getTheTvDbId() {
		return theTvDbId;
	}

	@Override
	public void setTheTvDbId(int theTvDbId) {
		this.theTvDbId = theTvDbId;
	}

	@Override
	public int getEpisode() {
		return episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	@Override
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Date getAired() {
		return (aired == null) ? null : (Date)aired.clone();
	}

	@Override
	public void setAired(Date aired) {
		this.aired = (aired == null) ? null : (Date)aired.clone();
	}

	
	public String getTvRageUrl() {
		return tvRageUrl;
	}

	@Override
	public void setTvRageUrl(String tvRageUrl) {
		this.tvRageUrl = tvRageUrl;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	@Override
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	public Double getRating() {
		return rating;
	}

	@Override
	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + episode;
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Episode other = (Episode) obj;
		if (episode != other.episode) {
			return false;
		}
		if (season == null) {
			if (other.season != null) {
				return false;
			}
		} else if (!season.equals(other.season)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Episode other) {
		int i = this.season.compareTo(other.season);
		if (i != 0) {
			return i;
		}
		return this.episode - other.episode; 
	}

}
