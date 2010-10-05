package com.janoz.tvapilib.model;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Season implements Comparable<Season>{

	private int season;
	private Show show;
	private SortedSet<Episode> episodes = new TreeSet<Episode>();
	private List<Fanart> poster;
	private List<Fanart> banners;

	
	
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Season other = (Season) obj;
		if (season != other.season)
			return false;
		if (show == null) {
			if (other.show != null)
				return false;
		} else if (!show.equals(other.show))
			return false;
		return true;
	}
	
	
	
}
