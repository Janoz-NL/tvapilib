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
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.Subtitle;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 * 
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 *
 */
public class BierdopjeImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends XmlParsingObject implements Bierdopje<Sh,Se,Ep> {

	private String apiKey;

	/**
	 * @param apiKey The bierdopje.com api-key.
	 */
	public BierdopjeImpl(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public List<Subtitle> getAllSubsFor(int theTvDbId, int season, int episode) {
		ResponseParser parser = new ResponseParser();
		parse(parser,openStream(constructGetAllSubsForUrl(theTvDbId, season, episode)));
		return parser.getResult();
	}

	@Override
	public List<Subtitle> getAllSubsFor(Ep episode) {
		return getAllSubsFor(episode.getSeason().getShow().getTheTvDbId(),
				episode.getSeason().getSeason(),
				episode.getEpisode());
		
	}

	String constructGetAllSubsForUrl(int theTvDbId, int season, int episode) {
		StringBuilder sb = new StringBuilder("http://api.bierdopje.com/")
				.append(apiKey).append("/GetAllSubsFor/")
				.append(theTvDbId).append("/")
				.append(season).append("/")
				.append(episode).append("/nl/true");
		return sb.toString();
	}
}
