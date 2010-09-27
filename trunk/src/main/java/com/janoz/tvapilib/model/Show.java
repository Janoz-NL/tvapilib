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
	 * @return URL to the fanart of this show
	 */
	public String getFanartUrl() {
		return fanartUrl;
	}

	/**
	 * @param fanartUrl URL to the fanart of this show
	 */
	public void setFanartUrl(String fanartUrl) {
		this.fanartUrl = fanartUrl;
	}

	/**
	 * @return URL to the poster of this show
	 */
	public String getPosterUrl() {
		return posterUrl;
	}

	/**
	 * @param posterUrl URL to the poster of this show
	 */
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	/**
	 * @return URL to the banner of this show
	 */
	public String getBannerUrl() {
		return bannerUrl;
	}

	/**
	 * @param bannerUrl URL to the banner of this show
	 */
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
}
