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
package com.janoz.tvapilib.bierdopje.impl.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Subtitle;
import com.janoz.tvapilib.support.AbstractSaxParser;

public class ResponseParser extends AbstractSaxParser{

	private boolean inSub = false;
	private SubsParser subsParser = new SubsParser();
	private Episode episode;
	private List<Subtitle> result = new ArrayList<Subtitle>();

	public ResponseParser(Episode episode) {
		this.episode = episode;
	}
	
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
			sub.setEpisode(episode);
			result.add(sub);
			subsParser.reset();
			inSub = true;
		}
	}
	
	public List<Subtitle> getResult() {
		return result;
	}
	

}
