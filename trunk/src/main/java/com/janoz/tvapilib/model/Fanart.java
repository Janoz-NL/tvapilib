package com.janoz.tvapilib.model;

public class Fanart {

	private FanartType type;
	private String id;
	private String url;
	private String thumbUrl;
	private float rating;
	private int ratingCount;
	private boolean seriesName;
	private Integer season;
	public FanartType getType() {
		return type;
	}
	public void setType(FanartType type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public int getRatingCount() {
		return ratingCount;
	}
	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}
	public boolean isSeriesName() {
		return seriesName;
	}
	public void setSeriesName(boolean seriesName) {
		this.seriesName = seriesName;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	
	
}
