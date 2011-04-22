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

import java.util.List;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

public class ShowParser<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	private Sh show = null;

	public void reset(Sh show) {
		this.show = show;
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("seriesid".equals(stack.get(0))) {
				show.setTheTvDbId(Integer.parseInt(content));
			} else if ("seriesname".equals(stack.get(0))) {
				show.setTitle(content);
			} else if ("overview".equals(stack.get(0))) {
				show.setDescription(content);
			}
		}
	}
	
	public Sh getShow() {
		return show;
	}
}
