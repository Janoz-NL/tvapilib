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
package com.janoz.tvapilib.thetvdb.impl;

import java.util.List;

import com.janoz.tvapilib.model.Art;
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

public class BannerParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {

	private final UrlSupplier urlSupplier;
	private Art art = null;
	private String bannerType1 = null;
	private String bannerType2 = null;
	private Integer season = null;
	private boolean text = false;
	
	private String language;
	
	public BannerParser(UrlSupplier urlSupplier) {
		this.urlSupplier = urlSupplier;
	}
	
	public void reset() {
		art = new Art();
		bannerType1 = null;
		bannerType2 = null;
		language = null;
		season = null;
		text = false;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("id".equals(stack.get(0))) {
				art.setId(Integer.parseInt(content));
			} else if ("bannerpath".equals(stack.get(0))) {
				art.setUrl(urlSupplier.getImageUrl(content));
			} else if ("bannertype".equals(stack.get(0))) {
				bannerType1 = content;
			} else if ("bannertype2".equals(stack.get(0))) {
				bannerType2 = content;
			} else if ("language".equals(stack.get(0))) {
				language = content;
			} else if ("thumbnailpath".equals(stack.get(0))) {
				art.setThumbUrl(urlSupplier.getImageUrl(content));
			} else if ("season".equals(stack.get(0))) {
				season = Integer.valueOf(content);
			} else if ("rating".equals(stack.get(0))) {
				art.setRating(Float.parseFloat(content) / 10f);
			} else if ("ratingcount".equals(stack.get(0))) {
				art.setRatingCount(Integer.parseInt(content));
//			} else if ("colors".equals(stack.get(0))) {
//				//<Colors>|226,168,148|21,1,2|130,0,0|</Colors>
//			} else if ("seriesname".equals(stack.get(0))) {
//				//<SeriesName>false</SeriesName>
//			} else if ("vignettepath".equals(stack.get(0))) {
//				//<VignettePath
			} 
		}
	}
	
	public void storeFanart(Sh show) {
		//Only English for now
		if (language==null || "en".equals(language)) {
			TheTvDbFanartType tvdbType = 
				TheTvDbFanartType.getTvDbType(bannerType1, bannerType2);
			art.setType(tvdbType.artType);
			art.setText(text || tvdbType.textDefault);
			if (season == null) {
				show.addArt(art);
			} else {
				show.getSeason(season).addArt(art);
			}
		}
	}

	
	private enum TheTvDbFanartType {
		BACKDROP ("fanart",null, Art.Type.BACKDROP, false ),
		POSTER ("poster", null, Art.Type.POSTER, true),
		BANNER_TEXT ("series", "text", Art.Type.BANNER, true), 
		BANNER_GRAPHICAL ("series", "graphical", Art.Type.BANNER, true),
		BANNER_BLANK ("series", "blank", Art.Type.BANNER, false),
		SEASON_POSTER ("season", "season", Art.Type.POSTER, true),
		SEASON_BANNER ("season", "seasonwide", Art.Type.BANNER, true);

		private final String bannerType1;
		private final String bannerType2;
		private final Art.Type artType;
		private final boolean textDefault;
		
		TheTvDbFanartType(String bannerType1, String bannerType2, Art.Type artType, boolean textDefault) {
			this.bannerType1 = bannerType1;
			this.bannerType2 = bannerType2;
			this.artType = artType;
			this.textDefault = textDefault;
		}
		
		static TheTvDbFanartType getTvDbType(String bannerType1, String bannerType2) {
			for (TheTvDbFanartType type : values()) {
				if (type.bannerType1.equals(bannerType1) 
						&& ((type.bannerType2 == null) 
							|| (type.bannerType2.equals(bannerType2)))) {
					return type;
				}
			}
			return null;
		}

	}
}
