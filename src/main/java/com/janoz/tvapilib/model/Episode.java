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

import java.util.Date;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Episode {

	private int id;
	private Show show;
	private int episode;
	private int season;
	private String title;
	private Date aired;
	private String description;
	private String thumbUrl;

	/**
	 * @return TheTVDB id of this episode.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id TheTVDB id of this episode.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return The show this episode belongs to.
	 */
	public Show getShow() {
		return show;
	}

	/**
	 * @param show The show this episode belongs to.
	 */
	public void setShow(Show show) {
		this.show = show;
	}

	/**
	 * @return The episode number.
	 */
	public int getEpisode() {
		return episode;
	}

	/**
	 * @param episode The episode number.
	 */
	public void setEpisode(int episode) {
		this.episode = episode;
	}

	/**
	 * @return The season number.
	 */
	public int getSeason() {
		return season;
	}

	/**
	 * @param season The season number.
	 */
	public void setSeason(int season) {
		this.season = season;
	}

	/**
	 * @return The title of this episode.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The Title of this episode.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return The date this episode was first aired.
	 */
	public Date getAired() {
		return aired;
	}

	/**
	 * @param aired The date this episode was first aired.
	 */
	public void setAired(Date aired) {
		this.aired = aired;
	}

	/**
	 * @return The description of this episode.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description of this episode.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return URL to the thumbnail of this episode.
	 */
	public String getThumbUrl() {
		return thumbUrl;
	}

	/**
	 * @param thumbUrl URL to the thumbnail of this episode.
	 */
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

}
