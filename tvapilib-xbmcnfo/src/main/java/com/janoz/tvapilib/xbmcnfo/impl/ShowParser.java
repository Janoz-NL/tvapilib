/*******************************************************************************
 * Copyright (c) 2011 Gijs de Vries aka Janoz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gijs de Vries aka Janoz - initial API and implementation
 ******************************************************************************/
package com.janoz.tvapilib.xbmcnfo.impl;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class ShowParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends AbstractSaxParser{

	private static final String ROOTNODE = "tvshow";
	private final Sh show;
	private Integer id = null;
	
	public ShowParser(ModelFactory<Sh,Se,Ep> modelFactory) {
		show = modelFactory.newShow();	
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		//do nothing for now
	}

	@Override
	public void handleContent(String content) {
		if (stackEquals(ROOTNODE,"tvdbid")) {
			show.setTheTvDbId(Integer.valueOf(content));
		} else if (stackEquals(ROOTNODE,"id")) {
			if (content.startsWith("tt")) {
				show.setImdbId(content);
			} else {
				id = Integer.valueOf(content);
			}
		} else if (stackEquals(ROOTNODE,"title")) {
			show.setTitle(content);
		} else if (stackEquals(ROOTNODE,"plot")) {
			show.setDescription(content);
		}
	}

	@Override
	public void handleTagEnd() {
		//do nothing for now		
	}
	
	public Sh getShow() {
		if (show.getTheTvDbId() == null) {
			show.setTheTvDbId(id);
		}
		return show;
	}

}
