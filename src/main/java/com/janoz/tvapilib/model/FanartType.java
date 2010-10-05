package com.janoz.tvapilib.model;

public enum FanartType {
	FANART ("fanart",null),
	POSTER ("poster", null),
	BANNER_TEXT ("series", "text"), 
	BANNER_GRAPHICAL ("series", "graphical"),
	BANNER_BLANK ("series", "blank"),
	SEASON_POSTER ("season", "season"),
	SEASON_BANNER ("season", "seasonwide"),
	LOGO  ("notATvDbType", "notATvDbType"),
	UNKNOWN (null,null);
	
	private String bannerType1;
	private String bannerType2;
	
	FanartType(String bannerType1, String bannerType2) {
		this.bannerType1 = bannerType1;
		this.bannerType2 = bannerType2;
	}
	
	public static FanartType getTvDbType(String bannerType1, String bannerType2) {
		for (FanartType type : values()) {
			if (type.bannerType1.equals(bannerType1)) {
				if ((type.bannerType2 == null) 
						|| (type.bannerType2.equals(bannerType2))) {
					return type;
				}
			}
		}
		return UNKNOWN;
	}
}
