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
package com.janoz.tvapilib.thetvdb.impl.parsers;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class BaseEpisodeParser extends AbstractSaxParser {

	private boolean inEpisode = false;
	private EpisodeParser episodeParser = new EpisodeParser();
	private Show show;
	
	
	
	public BaseEpisodeParser(Show show, UrlSupplier urlSupplier) {
		super();
		this.show = show;
		episodeParser.setUrlSupplier(urlSupplier);
	}

	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inEpisode && stackEquals("data","episode")) {
			episodeParser.reset(show);
			inEpisode = true;
		}
	}

	@Override
	public void handleContent(String content) {
		if (inEpisode) {
			episodeParser.handleContent(getStackTail(2), content);
		}
	}
	
	@Override
	public void handleTagEnd() {
		if (inEpisode && stackEquals("data","episode")) {
				inEpisode = false;
		}
	}

	public Episode getResult() {
		return episodeParser.getEpisode();
	}

}
