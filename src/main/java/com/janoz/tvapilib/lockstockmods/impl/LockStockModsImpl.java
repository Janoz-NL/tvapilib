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

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.janoz.tvapilib.lockstockmods.LockStockMods;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class LockStockModsImpl extends XmlParsingObject implements LockStockMods {

	@Override
	public boolean addClearLogo(Show show) throws ParserConfigurationException, SAXException, IOException {
		String url = getClearLogoURL(show.getId());
		if (url != null) {
			show.setLogoUrl(url);
			return true;
		}
		return false;
	}

	@Override
	public String getClearLogoURL(int showId) throws ParserConfigurationException, SAXException, IOException {
		Document doc = fetchFeed("http://www.lockstockmods.net/logos/getlogo.php?id="+showId);
		Node node = getChildNodeByName(doc, "logos","logo");
		if (node == null) {
			return null;
		} else {
			return node.getAttributes().getNamedItem("url").getTextContent();
		}
	}

}
