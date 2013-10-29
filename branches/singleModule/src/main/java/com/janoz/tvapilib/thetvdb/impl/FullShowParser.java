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

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class FullShowParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends AbstractSaxParser {

	private boolean inShow = false;
	private boolean inEpisode = false;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	private ShowParser<Sh,Se,Ep> showParser = new ShowParser<Sh,Se,Ep>();
	private EpisodeParser<Sh,Se,Ep> episodeParser;
	private Sh result = null;	
	
	public FullShowParser(ModelFactory<Sh,Se,Ep> modelFactory, UrlSupplier urlSupplier) {
		this.modelFactory = modelFactory;
		episodeParser = new EpisodeParser<Sh,Se,Ep>(urlSupplier);
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (
				!inShow 
				&& !inEpisode 
				&& isStackSize(2)
				&& stackStartsWith("data")) {
			if ("series".equals(getNodeName())) {
				inShow = true;
				result = modelFactory.newShow();
				showParser.reset(result);
			} else if ("episode".equals(getNodeName())) {
				inEpisode = true;
				episodeParser.reset(result);
			}
		}
	}

	@Override
	public void handleContent(String content) {
		if (inEpisode) {
			episodeParser.handleContent(getStackTail(2), content);
		} else if (inShow) {
			showParser.handleContent(getStackTail(2), content);
		}
	}

	@Override
	public void handleTagEnd() {
		if (isStackSize(2) && stackStartsWith("data")) {
			if (inShow && "series".equals(getNodeName())) {
				inShow = false;
			} else if (inEpisode && "episode".equals(getNodeName())) {
				inEpisode = false;
				episodeParser.getEpisode();
			}
		}
	}
	
	public Sh getResult() {
		return result;
	}

}
