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
package com.janoz.tvapilib.bierdopje.impl.parser;

import java.util.List;

import com.janoz.tvapilib.model.Subtitle;

public class SubsParser {

	private Subtitle subtitle = null;

	public void reset() {
		this.subtitle = new Subtitle();
	}
	
	public void handleContent(List<String> stack, String content){
		if (stack.size()==1) {
			if ("filename".equals(stack.get(0))) {
				subtitle.setFileTitle(content);
			} else if ("downloadlink".equals(stack.get(0))) {
				subtitle.setDownloadUrl(content);
			}
		}
	}
	
	public Subtitle getResult() {
		return subtitle;
	}

}
