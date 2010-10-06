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

import java.util.List;

import com.janoz.tvapilib.model.Fanart;
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
	 * 
	 */
	void addClearLogos(Show show);
	
	/**
	 * @param showId TheTVDB ID of the show 
	 * @return A list of clearlogos.
	 */
	List<Fanart> getClearLogos(int showId);
}
