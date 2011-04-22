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
package com.janoz.tvapilib.thetvdb.impl;

import com.janoz.tvapilib.model.impl.DefaultModelFactory;
import com.janoz.tvapilib.model.impl.Episode;
import com.janoz.tvapilib.model.impl.Season;
import com.janoz.tvapilib.model.impl.Show;
import com.janoz.tvapilib.thetvdb.impl.support.UrlSupplier;

public class DefaultTheTVDBImpl extends TheTVDBImpl<Show, Season, Episode> {

	public DefaultTheTVDBImpl(String apiKey) {
		super(DefaultModelFactory.INSTANCE, apiKey);
	}
	
	private DefaultTheTVDBImpl(UrlSupplier urlSupplier){
		super(DefaultModelFactory.INSTANCE, urlSupplier);
	}

}
