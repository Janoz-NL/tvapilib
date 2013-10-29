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
package com.janoz.tvapilib.tvrage;

import java.util.List;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

/**
 * @author Gijs de Vries aka Janoz
 *
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 *
 */
public interface TvRage<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	/**
	 * @param name The name of the show to search for.
	 * @return A list of shows.
	 */
	List<Sh> searchShows(String name);
	
	/**
	 * @param tvRageId TvRage id of the requested show.
	 * @return The show or null if the show is not found.
	 */
	Sh getShow(int tvRageId);

	/**
	 * 
	 * @param tvRageId TvRage id of the requested show.
	 * @return The show including all known episodes or null if the show is not found.
	 */
	Sh getShowWithEpisodes(int tvRageId);
	
	/**
	 * @param tvRageId TvRage id of the requested show.
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return 
	 * 			The episode or null if the episode is not known. A new Show object 
	 * 			containing only the ID will be injected (ie requesting show.getTitle 
	 * 			will return null)
	 */
	Ep getEpisode(int tvRageId, int season, int episode);

	/**
	 * @param show Show. Only the ID is used, but the show is also injected into the returned episode
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return The episode or null if the episode is not known.
	 */
	Ep getEpisode(Sh show, int season, int episode);

}
