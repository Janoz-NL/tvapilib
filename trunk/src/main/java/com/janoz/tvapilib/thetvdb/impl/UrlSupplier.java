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
	public StringBuilder getBaseEpisodeUrl(int showId, int season, int episode) {
		return getBaseShowUrl(showId)
				.append("/default/").append(season).append("/").append(episode);
	}

	/**
	 * @param showId TheTVDB show ID
	 * @return API url for retrieving show information. 
	 */
	public StringBuilder getBaseShowUrl(int showId) {
		return getXmlUrl().append("/series/").append(showId);
	}

	/**
	 * @param filename uri of the show or episode artwork. 
	 * @return Complete url constructed from filename uri and mirrordata.
	 */
	public String getImageUrl(String filename) {
		return getBannerUrl().append(filename).toString();
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
