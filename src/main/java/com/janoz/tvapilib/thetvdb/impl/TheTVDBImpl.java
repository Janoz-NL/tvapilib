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

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.thetvdb.TheTVDB;
import com.janoz.tvapilib.thetvdb.impl.parsers.BannersParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseEpisodeParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.BaseShowParser;
import com.janoz.tvapilib.thetvdb.impl.parsers.FullShowParser;

/**
 * @author Gijs de Vries aka Janoz
 *
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 *
 */
public class TheTVDBImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends XmlParsingObject implements TheTVDB<Sh,Se,Ep> {

	private final UrlSupplier urlSupplier;
	private final ModelFactory<Sh,Se,Ep> modelFactory;

	public TheTVDBImpl(ModelFactory<Sh,Se,Ep> modelFactory, String apiKey) {
		urlSupplier = new UrlSupplier(apiKey);
		this.modelFactory = modelFactory;
	}
	
	//Added for test purposes..
	protected TheTVDBImpl(ModelFactory<Sh,Se,Ep> modelFactory,UrlSupplier urlSupplier){
		this.urlSupplier = urlSupplier;
		this.modelFactory = modelFactory;
	}
	
	@Override
	public List<Sh> searchShows(String name) {
		String url = urlSupplier.getShowSearchUrl(name);
		BaseShowParser<Sh,Se,Ep> parser = new BaseShowParser<Sh,Se,Ep>(modelFactory);
		parse(parser,openStream(url));
		return parser.getResults();
	}

	@Override
	public Sh getFullShow(int theTvDbId) {
		// TODO reimplement this using the zip file
		Sh show = getShowWithEpisodes(theTvDbId);
		fillFanart(show);
		return show;
	}

	@Override
	public Sh getShow(int theTvDbId) {
		String url = urlSupplier.getBaseShowUrl(theTvDbId);
		BaseShowParser<Sh,Se,Ep> parser = new BaseShowParser<Sh,Se,Ep>(modelFactory);
		parse(parser,openStream(url));
		return parser.getResult();
	}
	
	@Override
	public Sh getShowWithEpisodes(int theTvDbId) {
		String url = urlSupplier.getFullShowUrl(theTvDbId);
		FullShowParser<Sh,Se,Ep> parser = new FullShowParser<Sh,Se,Ep>(modelFactory,urlSupplier);
		parse(parser,openStream(url));
		return parser.getResult();
	}

	@Override
	public Ep getEpisode(int theTvDbId, int season, int episode) {
		Sh show = modelFactory.newShow();
		show.setTheTvDbId(theTvDbId);
		return getEpisode(show, season, episode);
	}

	@Override
	public Ep getEpisode(Sh show, int season, int episode) {
		String url = urlSupplier.getBaseEpisodeUrl(show.getTheTvDbId(),
				season, episode);
		BaseEpisodeParser<Sh,Se,Ep> parser = new BaseEpisodeParser<Sh,Se,Ep>(show,urlSupplier);
		parse(parser,openStream(url));
		return parser.getResult();
	}
	
	@Override
	public void fillFanart(Sh show) {
		String url = urlSupplier.getBannerUrl(show.getTheTvDbId());
		BannersParser<Sh,Se,Ep> parser = new BannersParser<Sh,Se,Ep>(urlSupplier,show);
		parse(parser,openStream(url));
	}
}
