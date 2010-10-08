/*******************************************************************************
 * Copyright 2010 Gijs de Vries aka Janoz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.janoz.tvapilib.lockstockmods.impl.parsers;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class LogoParser extends AbstractSaxParser {

	private List<Fanart> result = new ArrayList<Fanart>();

	@Override
	public void handleTagStart(Attributes attributes) {
		if (stackEquals("logos","logo")) {
			Fanart fanart = new Fanart();
			fanart.setType(FanartType.LOGO);
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
