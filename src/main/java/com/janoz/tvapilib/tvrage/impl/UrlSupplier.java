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
package com.janoz.tvapilib.tvrage.impl;

public class UrlSupplier {

	private static final String SHOW_INFO = "showinfo.php";
	private static final String EPISODE_LIST = "episode_list.php";
	private static final String EPISODE_INFO = "episodeinfo.php";
	
	private String apiKey = null;
	
	public UrlSupplier(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getShowInfoUrl(int tvRageId){
		boolean usePublic = apiKey == null;
		return getServicesUrlBuilder(SHOW_INFO, usePublic)
				.append("sid=").append(tvRageId).toString();
		
	}
	
	public String getEpisodeListUrl(int tvRageId){
		boolean usePublic = apiKey == null;
		return getServicesUrlBuilder(EPISODE_LIST, usePublic)
				.append("sid=").append(tvRageId).toString();
	}
	
	public String getEpisodeInfoUrl(int tvRageId, int season, int episode) {
		boolean usePublic = apiKey == null;
		return getServicesUrlBuilder(EPISODE_INFO, usePublic)
				.append("sid=").append(tvRageId)
				.append("&ep=").append(season).append('x').append(episode).toString();
	}
	
	private StringBuilder getServicesUrlBuilder(String command, boolean usePublic) {
		StringBuilder result = new StringBuilder("http://services.tvrage.com/");
		if (!usePublic) {
			result.append("my");
		}
		result.append("feeds/");
		result.append(command);
		result.append("?");
		if (!usePublic) {
			result.append("key=");
			result.append(apiKey);
			result.append("&");
		}
		return result;
	}
}
