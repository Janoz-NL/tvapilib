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
package com.janoz.tvapilib.bierdopje;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Subtitle;

/**
 * Implementation of the subtitle requesting part of the bierdopje.com API.
 * Other information at Bierdopje is only a mirror of TheTVDB so its easier
 * to use {@link com.janoz.tvapilib.thetvdb.TheTVDB} instead.
 * 
 * 
 * @author Gijs de Vries aka Janoz
 *
 */
public interface Bierdopje {

	/**
	 * @param showId TheTVDB id of the requested show.
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return A list of subtitles for this episode.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	List<Subtitle> getAllSubsFor(int showId, int season, int episode)
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * @param episode The episode for which subtitles are requested
	 * @return A list of subtitles for this episode.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	List<Subtitle> getAllSubsFor(Episode episode)
			throws ParserConfigurationException, SAXException, IOException;

}
