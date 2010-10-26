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
package com.janoz.tvapilib.bierdopje.impl;

import java.util.List;

import com.janoz.tvapilib.bierdopje.Bierdopje;
import com.janoz.tvapilib.bierdopje.impl.parser.ResponseParser;
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
	public List<Subtitle> getAllSubsFor(int theTvDbId, int season, int episode) {
		Show show = new Show();
		show.setTheTvDbId(theTvDbId);
		Episode ep = new Episode();
		ep.setSeason(show.getSeason(season));
		ep.setEpisode(episode);
		return getAllSubsFor(ep);
	}

	@Override
	public List<Subtitle> getAllSubsFor(Episode episode) {
		ResponseParser parser = new ResponseParser(episode);
		parse(parser,openStream(constructGetAllSubsForUrl(episode)));
		return parser.getResult();
	}
	
	String constructGetAllSubsForUrl(Episode episode) {
		StringBuilder sb = new StringBuilder("http://api.bierdopje.com/")
				.append(apiKey).append("/GetAllSubsFor/")
				.append(episode.getSeason().getShow().getTheTvDbId()).append("/")
				.append(episode.getSeason().getSeason()).append("/")
				.append(episode.getEpisode()).append("/nl/true");
		return sb.toString();
	}
}
