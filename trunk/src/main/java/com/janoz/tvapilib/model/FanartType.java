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
			if (type.bannerType1.equals(bannerType1) 
					&& ((type.bannerType2 == null) 
						|| (type.bannerType2.equals(bannerType2)))) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
