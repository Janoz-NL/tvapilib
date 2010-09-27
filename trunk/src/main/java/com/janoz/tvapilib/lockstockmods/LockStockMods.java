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
package com.janoz.tvapilib.lockstockmods;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.janoz.tvapilib.model.Show;

/**
 * @author Gijs de Vries aka Janoz
 *
 * Retrieves ClearLOGOs from http://www.lockstockmods.net/clearart/
 * 
 */
public interface LockStockMods {

	
	/**
	 * 
	 * @param show The show to add the logo to.
	 * @return true if a logo was found and added to the show.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public boolean addClearLogo(Show show) throws ParserConfigurationException, SAXException, IOException;
	
	/**
	 * @param showId TheTVDB ID of the show 
	 * @return URL to the clearlogo or null if non available
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public String getClearLogoURL(int showId) throws ParserConfigurationException, SAXException, IOException;
}
