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
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

/**
 * Retrieves ClearLOGOs from http://www.lockstockmods.net/clearart/
 * 
 * @author Gijs de Vries aka Janoz
 *
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 * 
 */
public interface LockStockMods<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {

	
	/**
	 * 
	 * @param show The show to add the logo to.
	 * 
	 */
	void addClearLogos(Sh show);
	
	/**
	 * @param theTvDbId TheTVDB ID of the show 
	 * @return A list of clearlogos.
	 */
	List<Fanart> getClearLogos(int theTvDbId);
}
