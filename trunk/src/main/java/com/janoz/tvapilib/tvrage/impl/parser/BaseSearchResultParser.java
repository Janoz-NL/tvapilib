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
package com.janoz.tvapilib.tvrage.impl.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class BaseSearchResultParser  extends AbstractSaxParser {

	private static final String SHOW = "show";
	private static final String RESULTS = "results";
	
	private boolean inShow = false;
	private ShowParser showParser = new ShowParser();
	private List<Show> results = new ArrayList<Show>();
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inShow && stackEquals(RESULTS,SHOW)) {
			inShow = true;
			Show show = new Show();
			results.add(show);
			showParser.reset(show);
		}
	}

	@Override
	public void handleContent(String content) {
		if (inShow) {
			showParser.handleContent(this.getStackTail(2), content);
		}
	}

	@Override
	public void handleTagEnd() {
		if (inShow && stackEquals(RESULTS,SHOW)) {
			inShow = false;
		}
	}

	public List<Show> getResults() {
		return results;
	}
}
