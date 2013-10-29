/*******************************************************************************
 * Copyright (c) 2011 Gijs de Vries aka Janoz.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gijs de Vries aka Janoz - initial API and implementation
 ******************************************************************************/
package com.janoz.tvapilib.xbmcnfo.impl;

import com.janoz.tvapilib.model.impl.DefaultModelFactory;
import com.janoz.tvapilib.model.impl.Episode;
import com.janoz.tvapilib.model.impl.Season;
import com.janoz.tvapilib.model.impl.Show;

public class DefaultXbmcNfoImpl extends XbmcNfoImpl<Show,Season,Episode> {

	public DefaultXbmcNfoImpl() {
		super(DefaultModelFactory.INSTANCE);
	}

}
