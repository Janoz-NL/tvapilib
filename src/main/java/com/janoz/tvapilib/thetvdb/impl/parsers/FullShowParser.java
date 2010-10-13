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

import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class FullShowParser extends AbstractSaxParser {

	private boolean inShow = false;
	private boolean inEpisode = false;
	private ShowParser showParser = new ShowParser();
	private EpisodeParser episodeParser = new EpisodeParser();
	private Show result = null;	
	
	public FullShowParser(UrlSupplier urlSupplier) {
		episodeParser.setUrlSupplier(urlSupplier);
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
				result = new Show();
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
	
	public Show getResult() {
		return result;
	}

}
