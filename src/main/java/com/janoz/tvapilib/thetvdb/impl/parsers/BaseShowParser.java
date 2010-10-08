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
