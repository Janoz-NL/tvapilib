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
package com.janoz.tvapilib.tvrage.impl;

import com.janoz.tvapilib.model.DefaultModelFactory;
import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Season;
import com.janoz.tvapilib.model.Show;

public class DefaultTvRageImpl extends TvRageImpl<Show,Season,Episode> {

	public DefaultTvRageImpl() {
		super(DefaultModelFactory.INSTANCE);
	}

	public DefaultTvRageImpl(String apiKey) {
		super(DefaultModelFactory.INSTANCE, apiKey);
	}

	private DefaultTvRageImpl(UrlSupplier urlSupplier) {
		super(DefaultModelFactory.INSTANCE, urlSupplier);
	}
}
