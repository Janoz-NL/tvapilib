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
package com.janoz.tvapilib.thetvdb.impl.parsers;

import java.util.LinkedList;

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
	public void handleTagStart(LinkedList<String> stack, Attributes attributes) {
		if (
				!inShow 
				&& !inEpisode 
				&& stack.size()==2 
				&&"data".equals(stack.get(0))) {
			if ("series".equals(stack.get(1))) {
				inShow = true;
				result = new Show();
				showParser.reset(result);
			} else if ("episode".equals(stack.get(1))) {
				inEpisode = true;
				episodeParser.reset(result);
			}
		}
	}

	@Override
	public void handleContent(LinkedList<String> stack, String content) {
		if (inEpisode) {
			episodeParser.handleContent(stack.subList(2, stack.size()), content);
		} else if (inShow) {
			showParser.handleContent(stack.subList(2, stack.size()), content);
		}
	}

	@Override
	public void handleTagEnd(LinkedList<String> stack) {
		if (stack.size()==2 && "data".equals(stack.get(0))) {
			if (inShow && "series".equals(stack.get(1))) {
				inShow = false;
			} else if (inEpisode && "episode".equals(stack.get(1))) {
				inEpisode = false;
				episodeParser.getEpisode();
			}
		}
	}
	
	public Show getResult() {
		return result;
	}

}
