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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.janoz.tvapilib.model.Art;
import com.janoz.tvapilib.model.IShow;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Show implements IShow<Show,Season,Episode> {

	private String imdbId;
	private Integer tvRageId;
	private Integer theTvDbId;
	private String title;
	private String description;;
	//private List<Actor> actors;
	private Set<String> genres = new HashSet<String>();
	//private Language language
	private Double rating;
	//private int ratingCount;
	private String network;
	private String contentRating;
	//private ShowStatus status;
	//private int runtime;
	
	private Set<Art> arts = new HashSet<Art>();

	private SortedSet<Season> seasons = new TreeSet<Season>();
	
	@Override
    public String getImdbId() {
    	return imdbId;
    }
	
	@Override
    public void setImdbId(String imdbId) {
    	this.imdbId = imdbId;
    }

	@Override
	public Integer getTvRageId() {
		return tvRageId;
	}

	@Override
	public void setTvRageId(Integer tvRageId) {
		this.tvRageId = tvRageId;
	}

	@Override
	public Integer getTheTvDbId() {
		return theTvDbId;
	}

	@Override
	public void setTheTvDbId(Integer showId) {
		this.theTvDbId = showId;
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
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getRating() {
		return rating;
	}

	@Override
	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String getNetwork() {
		return network;
	}

	@Override
	public void setNetwork(String network) {
		this.network = network;
	}
	
	@Override
	public String getContentRating() {
		return contentRating;
	}

	@Override
	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	@Override
	public Season getSeason(int season) {
		Season result = new Season();
		result.setShow(this);
		result.setSeason(season);
		if (seasons.contains(result)) {
			result = seasons.tailSet(result).first();
		} else {
			seasons.add(result);
		}
		return result;
	}
	
	@Override
	public void addGenre(String genre) {
		genres.add(genre);
	}
	
	public Set<String> getGenres() {
		return genres;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + theTvDbId;
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
		Show other = (Show) obj;
		if (theTvDbId != other.theTvDbId) {
			return false;
		}
		return true;
	}

	public SortedSet<Season> getSeasons() {
		return Collections.unmodifiableSortedSet(seasons);
	}

	@Override
	public void addArt(Art art) {
		arts.add(art);
	}

	public Set<Art> getArts() {
		return arts;
	}
}
