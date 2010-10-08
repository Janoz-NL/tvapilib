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
package com.janoz.tvapilib.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Show {

	private int id;
	private String title;
//    <Actors>|Maggie Q|Shane West|Lyndsy Fonseca|Aaron Stanford|Melinda Clarke|Xander Berkeley|Ashton Holmes|Tiffany Hines|</Actors>
	//private List<Actor> actors;
//    <ContentRating>TV-14</ContentRating>
//    <FirstAired>2010-09-09</FirstAired>
//    <Genre>|Action and Adventure|Drama|</Genre>
//    <IMDB_ID>tt1592154</IMDB_ID>
//    <Language>en</Language>
//    <Network>The CW</Network>
//    <NetworkID></NetworkID>
//    <Overview>A very long description about Nikita and division.</Overview>
//    <Rating>9.0</Rating>
//    <RatingCount>10</RatingCount>
//    <Runtime>60</Runtime>
//    <SeriesID>164301</SeriesID>
//    <SeriesName>Nikita</SeriesName>
//    <Status>Continuing</Status>
//    <added>2010-05-20 12:28:55</added>
//    <addedBy>235</addedBy>
//    <banner>graphical/164301-g4.jpg</banner>
//    <fanart>fanart/original/164301-5.jpg</fanart>
//    <lastupdated>1285704535</lastupdated>
//    <poster>posters/164301-2.jpg</poster>
//    <zap2it_id></zap2it_id>
	
	private SortedSet<Season> seasons = new TreeSet<Season>();
	
	
	private List<Fanart> fanarts = new ArrayList<Fanart>();
	private List<Fanart> posters = new ArrayList<Fanart>();
	private List<Fanart> banners = new ArrayList<Fanart>();
	private List<Fanart> logos = new ArrayList<Fanart>();
	private List<Fanart> allSeasonPosters = new ArrayList<Fanart>();
	private List<Fanart> allSeasonBanners = new ArrayList<Fanart>();


	public int getId() {
		return id;
	}

	public void setId(int showId) {
		this.id = showId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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
		result = prime * result + id;
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
		if (id != other.id) {
			return false;
		}
		return true;
	}

	public SortedSet<Season> getSeasons() {
		return Collections.unmodifiableSortedSet(seasons);
	}

	public List<Fanart> getFanarts() {
		return Collections.unmodifiableList(fanarts);
	}

	public void addFanart(Fanart fanart) {
		this.fanarts.add(fanart);
	}

	public List<Fanart> getPosters() {
		return Collections.unmodifiableList(posters);
	}

	public void addPoster(Fanart poster) {
		this.posters.add(poster);
	}

	public List<Fanart> getBanners() {
		return Collections.unmodifiableList(banners);
	}

	public void addBanner(Fanart banner) {
		this.banners.add(banner);
	}

	public List<Fanart> getLogos() {
		return Collections.unmodifiableList(logos);
	}

	public void addLogo(Fanart logo) {
		this.logos.add(logo);
	}

	public List<Fanart> getAllSeasonPosters() {
		return Collections.unmodifiableList(allSeasonPosters);
	}

	public void addAllSeasonPoster(Fanart allSeasonPoster) {
		this.allSeasonPosters.add(allSeasonPoster);
	}

	public List<Fanart> getAllSeasonBanners() {
		return Collections.unmodifiableList(allSeasonBanners);
	}

	public void addAllSeasonBanner(Fanart allSeasonBanner) {
		this.allSeasonBanners.add(allSeasonBanner);
	}
	
	
	
}
