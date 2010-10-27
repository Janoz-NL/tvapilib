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

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public interface TvRage {
	
	/**
	 * @param name The name of the show to search for.
	 * @return A list of shows.
	 */
	List<Show> searchShows(String name);
	
	/**
	 * @param tvRageId TvRage id of the requested show.
	 * @return The show or null if the show is not found.
	 */
	Show getShow(int tvRageId);

	/**
	 * 
	 * @param tvRageId TvRage id of the requested show.
	 * @return The show including all known episodes or null if the show is not found.
	 */
	Show getShowWithEpisodes(int tvRageId);
	
	/**
	 * @param tvRageId TvRage id of the requested show.
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return 
	 * 			The episode or null if the episode is not known. A new Show object 
	 * 			containing only the ID will be injected (ie requesting show.getTitle 
	 * 			will return null)
	 */
	Episode getEpisode(int tvRageId, int season, int episode);

	/**
	 * @param show Show. Only the ID is used, but the show is also injected into the returned episode
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return The episode or null if the episode is not known.
	 */
	Episode getEpisode(Show show, int season, int episode);

}
