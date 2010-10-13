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
import com.janoz.tvapilib.thetvdb.impl.parsers.MirrorParser;


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
		initMirrors();
	}

	/**
	 * @param showId TheTVDB show ID.
	 * @param season season number.
	 * @param episode episode number.
	 * @return API url for retrieving episode information.
	 */
	public String getBaseEpisodeUrl(int showId, int season, int episode) {
		return getBaseShowUrlBuilder(showId)
				.append("/default/").append(season).append("/").append(episode).toString();
	}

	/**
	 * @param showId TheTVDB show ID
	 * @return API url for retrieving show information. 
	 */
	public String getBaseShowUrl(int showId) {
		return getBaseShowUrlBuilder(showId).toString();
	}

	/**
	 * @param showId TheTVDB show ID
	 * @return API url for retrieving full show information. 
	 */
	public String getFullShowUrl(int showId) {
		return getBaseShowUrlBuilder(showId).append("/all/").toString();
	}

	/**
	 * @param filename uri of artwork. 
	 * @return Complete url constructed from filename uri and mirrordata.
	 */
	public String getImageUrl(String filename) {
		return getBannerUrl().append(filename).toString();
	}
	
	/**
	 * @param showId TheTVDB show ID
	 * @return API url for retrieving banner information. 
	 */
	public String getBannerUrl(int showId) {
		return getBaseShowUrlBuilder(showId).append("/banner.xml").toString();
	}

	private StringBuilder getBaseShowUrlBuilder(int showId) {
		return getXmlUrl().append("/series/").append(showId);
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
		List<String> candidates = mirrors.get(type);
		return candidates.get(rand.nextInt(candidates.size()));
	}

	/*
	 * Package accessible so it can be mocked even though t causes a 
	 * major violation in Sonar.
	 */
	String getMirrorUrl() { //NOSONAR
		return "http://thetvdb.com/api/"+apiKey+"/mirrors.xml";
	}
	
	/*
	 * Constructor so class can be instantiated without initializing mirrordata.
	 */
	UrlSupplier(){ //NOSONAR
		//Added for test purposes..
	}
	
}
