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
package com.janoz.tvapilib.lockstockmods.impl;

import java.util.List;

import com.janoz.tvapilib.lockstockmods.LockStockMods;
import com.janoz.tvapilib.lockstockmods.impl.parsers.LogoParser;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class LockStockModsImpl extends XmlParsingObject implements LockStockMods {

	@Override
	public void addClearLogos(Show show) {
		for(Fanart logo:getClearLogos(show.getTheTvDbId())){
			show.addLogo(logo);
		}
	}

	@Override
	public List<Fanart> getClearLogos(int theTvDbId) {
		LogoParser parser = new LogoParser();
		parse(parser, openStream(getUrl(theTvDbId)));
		return parser.getResult();
	}
	
	/*
	 * Package accessible so it can be mocked for test purposes.
	 */
	String getUrl(int theTvDbId) {
		return "http://www.lockstockmods.net/logos/getlogo.php?id="+theTvDbId;
	}

}
