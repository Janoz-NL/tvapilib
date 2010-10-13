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
