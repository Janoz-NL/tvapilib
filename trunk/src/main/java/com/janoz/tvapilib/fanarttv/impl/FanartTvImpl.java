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
package com.janoz.tvapilib.fanarttv.impl;

import java.util.List;

import com.janoz.tvapilib.fanarttv.FanartTv;
import com.janoz.tvapilib.fanarttv.impl.parsers.LogoParser;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 *
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 *
 */
public class FanartTvImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends XmlParsingObject implements FanartTv<Sh,Se,Ep> {

	@Override
	public void addClearLogos(Sh show) {
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
		return "http://fanart.tv/api/getart.php?type=clearlogo&id="+theTvDbId;
	}

}
