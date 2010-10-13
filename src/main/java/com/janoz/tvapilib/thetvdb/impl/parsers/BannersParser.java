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
package com.janoz.tvapilib.thetvdb.impl.parsers;

import org.xml.sax.Attributes;

import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.support.AbstractSaxParser;
import com.janoz.tvapilib.thetvdb.impl.UrlSupplier;

public class BannersParser extends AbstractSaxParser {

	private boolean inBanner = false;
	private Show show;
	private BannerParser bannerParser;
	
	public BannersParser(UrlSupplier urlSupplier, Show show) {
		this.show = show;
		this.bannerParser = new BannerParser(urlSupplier);
	}
	
	@Override
	public void handleTagStart(Attributes attributes) {
		if (!inBanner && stackEquals("banners","banner")) {
			bannerParser.reset();
			inBanner = true;
		}
	}

	@Override
	public void handleContent(String content) {
		if (inBanner) {
			bannerParser.handleContent(getStackTail(2), content);
		}
	}

	@Override
	public void handleTagEnd() {
		if (inBanner && stackEquals("banners","banner")) {
			inBanner = false;
			bannerParser.storeFanart(show);
		}
	}
}
