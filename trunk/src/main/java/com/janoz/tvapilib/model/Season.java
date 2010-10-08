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

public class Season implements Comparable<Season> {

	private int season;
	private Show show = null;
	private SortedSet<Episode> episodes = new TreeSet<Episode>();
	private List<Fanart> posters = new ArrayList<Fanart>();
	private List<Fanart> banners = new ArrayList<Fanart>();

	
	
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}
	
	public void addEpisode(Episode episode) {
		episodes.add(episode);
	}
	
	public void addPoster(Fanart fanart){
		posters.add(fanart);
	}
	
	public List<Fanart> getPosters() {
		return Collections.unmodifiableList(posters);
	}
	
	public void addBanner(Fanart fanart){
		banners.add(fanart);
	}
	
	public List<Fanart> getBanners() {
		return Collections.unmodifiableList(banners);
	}
	
	public Episode getEpisode(int episodeNr) {
		Episode dummy = new Episode();
		dummy.setSeason(this);
		dummy.setEpisode(episodeNr);
		SortedSet<Episode> tail = episodes.tailSet(dummy);
		if (!tail.isEmpty() && tail.first().getEpisode() == episodeNr) {
			return tail.first();
		} else {
			return null;
		}
	}
	public int getNrOfEpisodes() {
		return episodes.size();
	}



	@Override
	public int compareTo(Season other) {
		if (this.show.equals(other.show)) {
			return this.season - other.season;
		} else {
			return this.show.getTitle().compareTo(other.show.getTitle()); 
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + season;
		result = prime * result + ((show == null) ? 0 : show.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Season other = (Season) obj;
		if (season != other.season) {
			return false;
		}
		if (show == null) {
			if (other.show != null) {
				return false;
			}
		} else if (!show.equals(other.show)) {
			return false;
		}
		return true;
	}
	
	
	
}
