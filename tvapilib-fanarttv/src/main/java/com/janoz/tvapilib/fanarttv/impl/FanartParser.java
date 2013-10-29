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
package com.janoz.tvapilib.fanarttv.impl;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class FanartParser extends AbstractSaxParser {

	private List<Fanart> result = new ArrayList<Fanart>();

	@Override
	public void handleTagStart(Attributes attributes) {
		if (stackEquals("fanart","clearlogos","clearlogo")) {
			Fanart fanart = new Fanart();
			fanart.setType(FanartType.CLEARLOGO);
			fanart.setUrl(attributes.getValue("", "url"));
			result.add(fanart);
		} else if (stackEquals("fanart","cleararts","clearart")) {
			Fanart fanart = new Fanart();
			fanart.setType(FanartType.CLEARART);
			fanart.setUrl(attributes.getValue("", "url"));
			result.add(fanart);
		} else if (stackEquals("fanart","tvthumbs","tvthumb")) {
			Fanart fanart = new Fanart();
			fanart.setType(FanartType.THUMB);
			fanart.setUrl(attributes.getValue("", "url"));
			result.add(fanart);
		}
	}

	@Override
	public void handleContent(String content) {
		// Do Nothing
		
	}

	@Override
	public void handleTagEnd() {
		// Do Nothing
		
	}
	
	public List<Fanart> getResult() {
		return result;
	}

}
