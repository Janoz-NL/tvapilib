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

public class BaseShowParser extends AbstractSaxParser {

	private boolean inShow = false;
	private ShowParser showParser = new ShowParser();
	private Show result = null;
	
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inShow && stackEquals("data","series")) {
			result = new Show();
			showParser.reset(result);
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

	public Show getResult() {
		return result;
	}

	

}
