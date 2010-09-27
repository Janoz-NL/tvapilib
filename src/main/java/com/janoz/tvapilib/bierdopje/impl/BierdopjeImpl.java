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
package com.janoz.tvapilib.bierdopje.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.janoz.tvapilib.bierdopje.Bierdopje;
import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.model.Subtitle;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class BierdopjeImpl extends XmlParsingObject implements Bierdopje {

	private String apiKey;

	/**
	 * @param apiKey The bierdopje.com api-key.
	 */
	public BierdopjeImpl(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public List<Subtitle> getAllSubsFor(int showId, int season, int episode)
			throws ParserConfigurationException, SAXException, IOException {
		Show show = new Show();
		show.setId(showId);
		Episode ep = new Episode();
		ep.setSeason(season);
		ep.setEpisode(episode);
		ep.setShow(show);
		return getAllSubsFor(ep);
	}

	@Override
	public List<Subtitle> getAllSubsFor(Episode episode)
			throws ParserConfigurationException, SAXException, IOException {
		StringBuilder sb = new StringBuilder("http://api.bierdopje.com/")
				.append(apiKey).append("/GetAllSubsFor/")
				.append(episode.getShow().getId()).append("/")
				.append(episode.getSeason()).append("/")
				.append(episode.getEpisode()).append("/nl/true");

		Document doc = fetchFeed(sb.toString());

		Node node = getChildNodeByName(doc.getFirstChild(), "response");
		node = getChildNodeByName(node, "results");
		return parseSubtitles(node, episode);
	}

	private List<Subtitle> parseSubtitles(Node node, Episode episode) {
		List<Subtitle> subtitles = new ArrayList<Subtitle>();
		for (Node result : new ChildIterator(node)) {
			if ("result".equalsIgnoreCase(result.getNodeName())) {
				Subtitle subtitle = new Subtitle();
				subtitle.setEpisode(episode);
				for (Node resultChild : new ChildIterator(result)) {
					if ("filename".equalsIgnoreCase(resultChild.getNodeName())) {
						subtitle.setFileTitle(resultChild.getTextContent());
					} else if ("downloadlink".equalsIgnoreCase(resultChild
							.getNodeName())) {
						subtitle.setDownloadUrl(resultChild.getTextContent());
					}
				}
				subtitles.add(subtitle);
			}
		}
		return subtitles;
	}
}
