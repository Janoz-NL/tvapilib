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
package com.janoz.tvapilib.thetvdb.impl.parsers;

import java.util.List;

import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class BannerParser {

	private Fanart fanart;
	private UrlSupplier urlSupplier;
	private String bannerType1;
	private String bannerType2;
	private Integer season;
	
	private String language;
	
	public BannerParser(UrlSupplier urlSupplier) {
		this.urlSupplier = urlSupplier;
	}
	
	public void reset() {
		fanart = new Fanart();
		bannerType1 = null;
		bannerType2 = null;
		language = null;
		season = null;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("id".equals(stack.get(0))) {
				fanart.setId(Integer.parseInt(content));
			} else if ("bannerpath".equals(stack.get(0))) {
				fanart.setUrl(urlSupplier.getImageUrl(content));
			} else if ("bannertype".equals(stack.get(0))) {
				bannerType1 = content;
			} else if ("bannertype2".equals(stack.get(0))) {
				bannerType2 = content;
			} else if ("language".equals(stack.get(0))) {
				  language = content;
			} else if ("thumbnailpath".equals(stack.get(0))) {
				fanart.setThumbUrl(urlSupplier.getImageUrl(content));
			} else if ("season".equals(stack.get(0))) {
				season = Integer.valueOf(content);
//			} else if ("colors".equals(stack.get(0))) {
//				//<Colors>|226,168,148|21,1,2|130,0,0|</Colors>
//			} else if ("rating".equals(stack.get(0))) {
//				//<Rating>9.1667</Rating>
//			} else if ("ratingcount".equals(stack.get(0))) {
//				//<RatingCount>6</RatingCount>
//			} else if ("seriesname".equals(stack.get(0))) {
//				//<SeriesName>false</SeriesName>
//			} else if ("vignettepath".equals(stack.get(0))) {
//				//<VignettePath
			} 
		}
	}
	
	public void storeFanart(Show show) {
		fanart.setType(FanartType.getTvDbType(bannerType1, bannerType2));
		if (language==null || "en".equals(language)) {
			//Only English for now
			switch (fanart.getType()) {
			case BANNER_BLANK:
			case BANNER_GRAPHICAL:
			case BANNER_TEXT:
				show.addBanner(fanart);
				break;
			case FANART:
				show.addFanart(fanart);
				break;
			case POSTER:
				show.addPoster(fanart);
				break;
			case SEASON_BANNER:
				if (season == null || season == 0) {
					show.addAllSeasonBanner(fanart);
				} else {
					show.getSeason(season).addBanner(fanart);
				}
				break;
			case SEASON_POSTER:
				if (season == null || season == 0) {
					show.addAllSeasonPoster(fanart);
				} else {
					show.getSeason(season).addPoster(fanart);
				}
				break;
			}
		}
	}

}
