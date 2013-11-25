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
	void setTvRageId(Integer tvRageId);
	Integer getTheTvDbId();
	void setTheTvDbId(Integer showId);
	String getImdbId();
	void setImdbId(String showId);
	String getTitle();
	void setTitle(String title);
	String getDescription();	
	void setDescription(String description);
	String getNetwork();
	void setNetwork(String network);
	String getContentRating();
	void setContentRating(String contentRating);
	void setRating(Double rating);
	void addArt(Art art);
	void addGenre(String genre);


}
