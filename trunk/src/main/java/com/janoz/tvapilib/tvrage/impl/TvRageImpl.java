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

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.tvrage.TvRage;
import com.janoz.tvapilib.tvrage.impl.parser.BaseEpisodeParser;
import com.janoz.tvapilib.tvrage.impl.parser.BaseShowParser;

public class TvRageImpl extends XmlParsingObject implements TvRage {

	private UrlSupplier urlSupplier;
	
	public TvRageImpl() {
		this(null);
	}
	
	public TvRageImpl(String apiKey) {
		this.urlSupplier = new UrlSupplier(apiKey);
	}
	
	@Override
	public Show getShow(int tvRageId) {
		String url = urlSupplier.getShowInfoUrl(tvRageId);
		BaseShowParser parser = new BaseShowParser("showinfo");
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Show getShowWithEpisodes(int tvRageId) {
		String url = urlSupplier.getEpisodeListUrl(tvRageId);
		BaseShowParser parser = new BaseShowParser("show");
		parse(parser,openStream(url));
		Show result = parser.getResult();
		if (result != null) {
			result.setTvRageId(tvRageId);
		}
		return result;
	}

	@Override
	public Episode getEpisode(int tvRageId, int season, int episode) {
		String url = urlSupplier.getEpisodeInfoUrl(tvRageId,season,episode);
		BaseEpisodeParser parser = new BaseEpisodeParser();
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Episode getEpisode(Show show, int season, int episode) {
		String url = urlSupplier.getEpisodeInfoUrl(show.getTvRageId(),season,episode);
		BaseEpisodeParser parser = new BaseEpisodeParser(show);
		parse(parser,openStream(url));
		return parser.getResult();
	}

}
