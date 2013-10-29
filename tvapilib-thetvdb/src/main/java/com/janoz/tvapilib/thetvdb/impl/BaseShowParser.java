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

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseShowParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends AbstractSaxParser {

	private boolean inShow = false;
	private final ModelFactory<Sh,Se,Ep> modelFactory;
	private ShowParser<Sh,Se,Ep> showParser = new ShowParser<Sh,Se,Ep>();
	private List<Sh> results = new ArrayList<Sh>();
	
	public BaseShowParser(ModelFactory<Sh,Se,Ep> modelFactory) {
		this.modelFactory = modelFactory;
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inShow && stackEquals("data","series")) {
			Sh show = modelFactory.newShow();
			results.add(show);
			showParser.reset(show);
			inShow = true;
		}
	}

	@Override
	public void handleContent(String content) {
		if (inShow) {
			showParser.handleContent(getStackTail(2), content);
		}
	}
	
	@Override
	public void handleTagEnd() {
		if (inShow && stackEquals("data","series")) {
			inShow = false;
		}
	}

	public Sh getResult() {
		return results.size() > 0? results.get(0) : null;
	}

	public List<Sh> getResults() {
		return results;
	}

	

}
