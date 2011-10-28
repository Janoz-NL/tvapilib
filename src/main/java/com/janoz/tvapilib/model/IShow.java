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
	
	Integer getTvRageId();
	Integer getTheTvDbId();
	String getImdbId();

	void setTvRageId(Integer tvRageId);
	void setTheTvDbId(Integer showId);
	void setImdbId(String showId);
	void setDescription(String description);
	void setTitle(String title);

	void addFanart(Fanart fanart);
	
}
