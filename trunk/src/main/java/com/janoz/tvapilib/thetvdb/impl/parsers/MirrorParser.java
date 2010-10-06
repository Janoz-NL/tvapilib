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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.support.AbstractSaxParser;
import com.janoz.tvapilib.thetvdb.impl.MirrorType;

public class MirrorParser extends AbstractSaxParser{

	private int typemask = 0;
	private String url = null;
	private Map<MirrorType, List<String>> mirrors;
	
	public MirrorParser() {
		mirrors = new HashMap<MirrorType, List<String>>();
		for (MirrorType type : MirrorType.values()) {
			mirrors.put(type, new ArrayList<String>());
		}
	}
	
	@Override
	public void handleTagStart(LinkedList<String> stack, Attributes attributes) {
		//do Nothing
	}

	@Override
	public void handleContent(LinkedList<String> stack, String content) {
		if (stack.size()==3) {
			if ("mirrors".equals(stack.get(0)) 
					&& "mirror".equals(stack.get(1))) {
				if ("typemask".equalsIgnoreCase(stack.get(2))) {
					typemask = Integer.parseInt(content);
				} else if ("mirrorpath".equalsIgnoreCase(stack.get(2))) {
					url = content;
				}
			}
		}		
		
	}

	@Override
	public void handleTagEnd(LinkedList<String> stack) {
		if (stack.size()==2
			&& "mirrors".equals(stack.get(0)) 
			&& "mirror".equals(stack.get(1))) {
			for (MirrorType type : MirrorType.values()) {
				if (type.matches(typemask)) {
					mirrors.get(type).add(url);
				}
			}
			url = null;
			typemask = 0;
		}
	}
	
	public Map<MirrorType, List<String>> getResult() {
		return mirrors;
	}

}
