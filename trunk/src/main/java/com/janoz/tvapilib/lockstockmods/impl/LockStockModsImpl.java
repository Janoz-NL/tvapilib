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
		for(Fanart logo:getClearLogos(show.getId())){
			show.addLogo(logo);
		}
	}

	@Override
	public List<Fanart> getClearLogos(int showId) {
		LogoParser parser = new LogoParser();
		parse(parser, openStream(getUrl(showId)));
		return parser.getResult();
	}
	
	/*
	 * Package accessible so it can be mocked for test purposes.
	 */
	String getUrl(int showId) {
		return "http://www.lockstockmods.net/logos/getlogo.php?id="+showId;
	}

}
