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
import com.janoz.tvapilib.model.ISeason;

public class Season implements Comparable<Season>,ISeason<Show,Season,Episode> {

	private int season;
	private Show show = null;
	private SortedSet<Episode> episodes = new TreeSet<Episode>();
	private Set<Art> arts = new HashSet<Art>();

	
	
	@Override
	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	@Override
	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}
	
	@Override
	public Episode getEpisode(int episode) {
		Episode newEp = new Episode();
		newEp.setSeason(this);
		newEp.setEpisode(episode);
		SortedSet<Episode> tail = episodes.tailSet(newEp);
		if (!tail.isEmpty() && tail.first().getEpisode() == episode) {
			return tail.first();
		} else {
			episodes.add(newEp);
			return newEp;
		} 
	}
	
	public SortedSet<Episode> getEpisodes() {
		return Collections.unmodifiableSortedSet(episodes);
	}
	
	@Override
	public void addArt(Art art){
		arts.add(art);
	}
	
	public Set<Art> getArts() {
		return arts;
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

	public boolean hasEpisode(int episode) {
		Episode dummy = new Episode();
		dummy.setSeason(this);
		dummy.setEpisode(episode);
		SortedSet<Episode> tail = episodes.tailSet(dummy);
		return (!tail.isEmpty() && tail.first().getEpisode() == episode);
	}
	
	
	
}
