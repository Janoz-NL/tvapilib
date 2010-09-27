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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.janoz.tvapilib.support.XmlParsingObject;


/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class UrlSupplier extends XmlParsingObject {

	private String apiKey;
	private static Random rand = new Random();
	private Map<MirrorType, List<String>> mirrors;

	/**
	 * @param apiKey TheTVDB api-key.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public UrlSupplier(String apiKey) throws ParserConfigurationException,
			SAXException, IOException {
		this.apiKey = apiKey;
		mirrors = new HashMap<MirrorType, List<String>>();
		for (MirrorType type : MirrorType.values()) {
			mirrors.put(type, new ArrayList<String>());
		}
		Node node = fetchFeed("http://thetvdb.com/api/" + apiKey
				+ "/mirrors.xml");
		for (Node mirrorNode : new ChildIterator(getChildNodeByName(node,
				"mirrors"))) {
			if ("mirror".equalsIgnoreCase(mirrorNode.getNodeName())) {
				procesMirror(mirrorNode);
			}
		}
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

	private void procesMirror(Node mirror) {
		String url = null;
		int typemask = 0;
		for (Node node : new ChildIterator(mirror)) {
			if ("typemask".equalsIgnoreCase(node.getNodeName())) {
				typemask = Integer.parseInt(node.getTextContent());
			} else if ("mirrorpath".equalsIgnoreCase(node.getNodeName())) {
				url = node.getTextContent();
			}
		}
		for (MirrorType type : MirrorType.values()) {
			if (type.matches(typemask)) {
				mirrors.get(type).add(url);
			}
		}

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

	private enum MirrorType {
		XML(1), BANNER(2), ZIP(4);

		int mask;

		MirrorType(int mask) {
			this.mask = mask;
		}

		public boolean matches(int i) {
			return 0 != (i & mask);
		}
	}
}
