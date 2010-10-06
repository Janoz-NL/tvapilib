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

import java.util.List;

import com.janoz.tvapilib.model.Show;

public class ShowParser {
	
	private Show show;

	public void reset(Show show) {
		this.show = show;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seriesid".equalsIgnoreCase(stack.get(0))) {
				show.setId(Integer.parseInt(content));
			} else if ("seriesname".equalsIgnoreCase(stack.get(0))) {
				show.setTitle(content);
			} 
		}
	}
	
	public Show getShow() {
		return show;
	}
}
