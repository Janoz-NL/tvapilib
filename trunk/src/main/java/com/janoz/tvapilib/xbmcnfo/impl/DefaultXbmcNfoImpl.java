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
