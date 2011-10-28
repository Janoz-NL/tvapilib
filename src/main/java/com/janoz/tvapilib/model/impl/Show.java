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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.janoz.tvapilib.model.Fanart;
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
	//private Set<Genre> genres
	//private String imdbId;
	//private Language language
	//private Double rating;
	//private int ratingCount;
	//private String network;
	//private ContentRating contentRating
	//private ShowStatus status;
	//private int runtime;
	
	private SortedSet<Season> seasons = new TreeSet<Season>();
	
	
	private List<Fanart> backdrops = new ArrayList<Fanart>();
	private List<Fanart> posters = new ArrayList<Fanart>();
	private List<Fanart> banners = new ArrayList<Fanart>();
	private List<Fanart> clearlogos = new ArrayList<Fanart>();
	private List<Fanart> cleararts = new ArrayList<Fanart>();
	private List<Fanart> thumbs = new ArrayList<Fanart>();
	private List<Fanart> allSeasonPosters = new ArrayList<Fanart>();
	private List<Fanart> allSeasonBanners = new ArrayList<Fanart>();

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

	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
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
	public void addFanart(Fanart fanart) {
		switch (fanart.getType()) {
		case BACKDROP:
			backdrops.add(fanart);
			break;
		case POSTER:
			posters.add(fanart);
			break;
		case BANNER_BLANK:
		case BANNER_GRAPHICAL:
		case BANNER_TEXT:
			banners.add(fanart);
			break;
		case CLEARART:
			cleararts.add(fanart);
			break;
		case CLEARLOGO:
			clearlogos.add(fanart);
			break;
		case THUMB:
			thumbs.add(fanart);
			break;
		case SEASON_POSTER:
			allSeasonPosters.add(fanart);
			break;
		case SEASON_BANNER:
			allSeasonBanners.add(fanart);
			break;
		case UNKNOWN:
			//do nothing
		}
	}
	public List<Fanart> getBackdrops() {
		return Collections.unmodifiableList(backdrops);
	}

	public List<Fanart> getClearlogos() {
		return Collections.unmodifiableList(clearlogos);
	}

	public List<Fanart> getCleararts() {
		return Collections.unmodifiableList(cleararts);
	}

	public List<Fanart> getThumbs() {
		return Collections.unmodifiableList(thumbs);
	}

	public List<Fanart> getPosters() {
		return Collections.unmodifiableList(posters);
	}

	public List<Fanart> getBanners() {
		return Collections.unmodifiableList(banners);
	}

	public List<Fanart> getAllSeasonPosters() {
		return Collections.unmodifiableList(allSeasonPosters);
	}

	public List<Fanart> getAllSeasonBanners() {
		return Collections.unmodifiableList(allSeasonBanners);
	}
}
