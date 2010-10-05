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
package com.janoz.tvapilib.thetvdb;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public interface TheTVDB {

	/**
	 * @param showId TheTVDB id of the requested show.
	 * @return The show or null if the show is not found.
	 */
	Show getShow(int showId);

	/**
	 * @param showId TheTVDB id of the requested show.
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return 
	 * 			The episode or null if the episode is not known. A new Show object 
	 * 			containing only the ID will be injected (ie requesting show.getTitle 
	 * 			will return null)
	 */
	Episode getEpisode(int showId, int season, int episode);

	/**
	 * @param show Show. Only the ID is used, but the show is also injected into the returned episode
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return The episode or null if the episode is not known.
	 */
	Episode getEpisode(Show show, int season, int episode);

}
