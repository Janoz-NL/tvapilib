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
package com.janoz.tvapilib.thetvdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.support.AbstractSaxParser;

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
	public void handleTagStart(Attributes attributes) {
		//do Nothing
	}

	@Override
	public void handleContent(String content) {
		if (isStackSize(3)
				&& stackStartsWith("mirrors","mirror")) {
			if ("typemask".equalsIgnoreCase(getNodeName())) {
				typemask = Integer.parseInt(content);
			} else if ("mirrorpath".equalsIgnoreCase(getNodeName())) {
				url = content;
			}
		}		
		
	}

	@Override
	public void handleTagEnd() {
		if (stackEquals("mirrors","mirror")) {
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
