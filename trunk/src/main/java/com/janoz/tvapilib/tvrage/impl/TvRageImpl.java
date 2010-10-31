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

import java.util.List;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.tvrage.TvRage;
import com.janoz.tvapilib.tvrage.impl.parser.BaseEpisodeParser;
import com.janoz.tvapilib.tvrage.impl.parser.BaseSearchResultParser;
import com.janoz.tvapilib.tvrage.impl.parser.BaseShowParser;

public class TvRageImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends XmlParsingObject implements TvRage<Sh,Se,Ep> {

	private final UrlSupplier urlSupplier;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	
	public TvRageImpl(ModelFactory<Sh,Se,Ep> modelFactory) {
		this(modelFactory,(String)null);
	}
	
	public TvRageImpl(ModelFactory<Sh,Se,Ep> modelFactory,String apiKey) {
		this(modelFactory,new UrlSupplier(apiKey));
	}

	protected TvRageImpl(ModelFactory<Sh,Se,Ep> modelFactory,UrlSupplier urlSupplier) {
		this.modelFactory = modelFactory;
		this.urlSupplier = urlSupplier;
	}

	@Override
	public List<Sh> searchShows(String name) {
		String url = urlSupplier.getShowSearchUrl(name);
		BaseSearchResultParser<Sh,Se,Ep> parser = new BaseSearchResultParser<Sh,Se,Ep>(modelFactory);
		parse(parser,openStream(url));
		return parser.getResults();
		
	}
	
	@Override
	public Sh getShow(int tvRageId) {
		String url = urlSupplier.getShowInfoUrl(tvRageId);
		BaseShowParser<Sh,Se,Ep> parser = new BaseShowParser<Sh,Se,Ep>(modelFactory,"showinfo");
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Sh getShowWithEpisodes(int tvRageId) {
		String url = urlSupplier.getEpisodeListUrl(tvRageId);
		BaseShowParser<Sh,Se,Ep> parser = new BaseShowParser<Sh,Se,Ep>(modelFactory,"show");
		parse(parser,openStream(url));
		Sh result = parser.getResult();
		if (result != null) {
			result.setTvRageId(tvRageId);
		}
		return result;
	}

	@Override
	public Ep getEpisode(int tvRageId, int season, int episode) {
		String url = urlSupplier.getEpisodeInfoUrl(tvRageId,season,episode);
		BaseEpisodeParser<Sh,Se,Ep> parser = new BaseEpisodeParser<Sh,Se,Ep>(modelFactory);
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Ep getEpisode(Sh show, int season, int episode) {
		String url = urlSupplier.getEpisodeInfoUrl(show.getTvRageId(),season,episode);
		BaseEpisodeParser<Sh,Se,Ep> parser = new BaseEpisodeParser<Sh,Se,Ep>(modelFactory,show);
		parse(parser,openStream(url));
		return parser.getResult();
	}

}
