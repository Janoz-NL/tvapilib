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

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Show {

	private int id;
	private String title;
	private String fanartUrl;
	private String posterUrl;
	private String bannerUrl;
	private String logoUrl;

	/**
	 * @return TheTVDB ID of this show.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param showId TheTVDB ID of this show.
	 */
	public void setId(int showId) {
		this.id = showId;
	}

	/**
	 * @return The title of this show.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The title of this show.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return URL to the fanart of this show.
	 */
	public String getFanartUrl() {
		return fanartUrl;
	}

	/**
	 * @param fanartUrl URL to the fanart of this show.
	 */
	public void setFanartUrl(String fanartUrl) {
		this.fanartUrl = fanartUrl;
	}

	/**
	 * @return URL to the poster of this show.
	 */
	public String getPosterUrl() {
		return posterUrl;
	}

	/**
	 * @param posterUrl URL to the poster of this show.
	 */
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	/**
	 * @return URL to the banner of this show.
	 */
	public String getBannerUrl() {
		return bannerUrl;
	}

	/**
	 * @param bannerUrl URL to the banner of this show.
	 */
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	/**
	 * @return The Url to the clearLogo.
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl The Url to the clearLogo.
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	
}
