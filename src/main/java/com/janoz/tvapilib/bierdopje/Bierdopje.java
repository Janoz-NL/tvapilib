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
package com.janoz.tvapilib.bierdopje;

import java.util.List;

import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Subtitle;

/**
 * Implementation of the subtitle requesting part of the bierdopje.com API.
 * Other information at Bierdopje is only a mirror of TheTVDB so its easier
 * to use {@link com.janoz.tvapilib.thetvdb.TheTVDB} instead.
 * 
 * 
 * @author Gijs de Vries aka Janoz
 *
 */
public interface Bierdopje {

	/**
	 * @param showId TheTVDB id of the requested show.
	 * @param season Season number.
	 * @param episode Episode number.
	 * @return A list of subtitles for this episode.
	 */
	List<Subtitle> getAllSubsFor(int showId, int season, int episode);

	/**
	 * @param episode The episode for which subtitles are requested
	 * @return A list of subtitles for this episode.
	 */
	List<Subtitle> getAllSubsFor(Episode episode);

}
