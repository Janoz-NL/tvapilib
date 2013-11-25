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

import java.util.Date;

public interface IEpisode<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	Se getSeason();

	int getEpisode();

	void setTheTvDbId(int theTvDbId);
	void setTitle(String title);
	String getTitle();
	void setAired(Date aired);
	Date getAired();
	void setTvRageUrl(String url);
	void setDescription(String description);
	String getDescription();
	void setRating(Double rating);
	void addArt(Art art);

}
