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
package com.janoz.tvapilib.model;

public interface IShow<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {

	Se getSeason(int season);
	
	int getTvRageId();
	int getTheTvDbId();

	void setDescription(String description);

	void setTitle(String title);

	void setTheTvDbId(int showId);

	void setTvRageId(int tvRageId);

	void addLogo(Fanart logo);
	void addAllSeasonPoster(Fanart poster);
	void addAllSeasonBanner(Fanart banner);
	void addBanner(Fanart banner);
	void addPoster(Fanart poster);
	void addFanart(Fanart fanart);
	
}
