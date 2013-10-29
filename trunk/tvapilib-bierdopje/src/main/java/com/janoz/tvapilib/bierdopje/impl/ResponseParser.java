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
package com.janoz.tvapilib.bierdopje.impl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Subtitle;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class ResponseParser extends AbstractSaxParser{

	private boolean inSub = false;
	private SubsParser subsParser = new SubsParser();
	private List<Subtitle> result = new ArrayList<Subtitle>();
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inSub && stackEquals("bierdopje","response","results","result")) {
			subsParser.reset();
			inSub = true;
		}
	}

	@Override
	public void handleContent(String content) {
		if (inSub) {
			subsParser.handleContent(getStackTail(4), content);
		}
	}

	@Override
	public void handleTagEnd() {
		if (inSub && stackEquals("bierdopje","response","results","result")) {
			Subtitle sub = subsParser.getResult();
			result.add(sub);
			subsParser.reset();
			inSub = true;
		}
	}
	
	public List<Subtitle> getResult() {
		return result;
	}
	

}
