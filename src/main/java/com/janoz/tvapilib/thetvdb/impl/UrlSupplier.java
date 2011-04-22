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
import java.util.Map;
import java.util.Random;

import com.janoz.tvapilib.support.XmlParsingObject;


/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class UrlSupplier extends XmlParsingObject {

	private static Random rand = new Random();

	private String apiKey;
	private Map<MirrorType, List<String>> mirrors;

	/**
	 * @param apiKey TheTVDB api-key.

	 */
	public UrlSupplier(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * 
	 * @param name Show to search for.
	 * @return Search URL (this method does not require the apikey).
	 */
	public String getShowSearchUrl(String name) {
		return  "http://www.thetvdb.com/api/GetSeries.php?seriesname="+ name;
	}
	/**
	 * @param theTvDbId TheTVDB show ID.
	 * @param season season number.
	 * @param episode episode number.
	 * @return API url for retrieving episode information.
	 */
	public String getBaseEpisodeUrl(int theTvDbId, int season, int episode) {
		return getBaseShowUrlBuilder(theTvDbId)
				.append("/default/").append(season).append("/").append(episode).toString();
	}

	/**
	 * @param theTvDbId TheTVDB show ID
	 * @return API url for retrieving show information. 
	 */
	public String getBaseShowUrl(int theTvDbId) {
		return getBaseShowUrlBuilder(theTvDbId).toString();
	}

	/**
	 * @param theTvDbId TheTVDB show ID
	 * @return API url for retrieving full show information. 
	 */
	public String getFullShowUrl(int theTvDbId) {
		return getBaseShowUrlBuilder(theTvDbId).append("/all/").toString();
	}

	/**
	 * @param filename uri of artwork. 
	 * @return Complete url constructed from filename uri and mirrordata.
	 */
	public String getImageUrl(String filename) {
		return getBannerUrl().append(filename).toString();
	}
	
	/**
	 * @param theTvDbId TheTVDB show ID
	 * @return API url for retrieving banner information. 
	 */
	public String getBannerUrl(int theTvDbId) {
		return getBaseShowUrlBuilder(theTvDbId).append("/banners.xml").toString();
	}

	private StringBuilder getBaseShowUrlBuilder(int theTvDbId) {
		return getXmlUrl().append("/series/").append(theTvDbId);
	}

	
	private void initMirrors() {
		MirrorParser parser = new MirrorParser();
		parse(parser,openStream(getMirrorUrl()));
		mirrors = parser.getResult();
	}
	

	private StringBuilder getXmlUrl() {
		StringBuilder sb = new StringBuilder(getMirror(MirrorType.XML));
		sb.append("/api/");
		sb.append(apiKey);
		return sb;
	}

	private StringBuilder getBannerUrl() {
		StringBuilder sb = new StringBuilder(getMirror(MirrorType.BANNER));
		sb.append("/banners/");
		return sb;
	}

	private String getMirror(MirrorType type) {
		if (mirrors == null) {
			initMirrors();
		}
		List<String> candidates = mirrors.get(type);
		return candidates.get(rand.nextInt(candidates.size()));
	}

	/*
	 * Package accessible so it can be mocked even though t causes a 
	 * major violation in Sonar.
	 */
	String getMirrorUrl() {
		return "http://thetvdb.com/api/"+apiKey+"/mirrors.xml";
	}

}
