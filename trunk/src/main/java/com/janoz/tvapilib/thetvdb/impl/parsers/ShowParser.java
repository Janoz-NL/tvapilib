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

import java.util.List;

import com.janoz.tvapilib.model.Show;

public class ShowParser {
	
	private Show show = null;

	public void reset(Show show) {
		this.show = show;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seriesid".equals(stack.get(0))) {
				show.setId(Integer.parseInt(content));
			} else if ("seriesname".equals(stack.get(0))) {
				show.setTitle(content);
			} else if ("overview".equals(stack.get(0))) {
				show.setDescription(content);
			}
		}
	}
	
	public Show getShow() {
		return show;
	}
}
