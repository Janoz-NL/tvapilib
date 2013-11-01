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
package com.janoz.tvapilib.fanarttv.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.janoz.tvapilib.fanarttv.FanartTv;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.TvApiException;
import com.janoz.tvapilib.support.XmlParsingObject;

/**
 * @author Gijs de Vries aka Janoz
 *
 * @param <Sh> Show type
 * @param <Se> Season type
 * @param <Ep> Episode type
 *
 */
public class FanartTvImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> implements FanartTv<Sh,Se,Ep> {

    private static final Log LOG = LogFactory.getLog(FanartTvImpl.class);

    private String apiKey;
    
    public FanartTvImpl(String apiKey) {
        this.apiKey = apiKey;
    }
    
	@Override
	public void addFanart(Sh show) {
	    int theTvDbId = show.getTheTvDbId();
	    Reader r = new InputStreamReader(openStream(getUrl(theTvDbId)));
	    JSONObject result = (JSONObject)JSONValue.parse(r);
	    if (result == null) {
	        return;
	    }
	    JSONObject showArt = (JSONObject)result.values().iterator().next();
	    //validate
	    String receivedTvDbId = (String)showArt.get("thetvdb_id");
	    if (!receivedTvDbId.equals(""+theTvDbId)) {
	        throw new TvApiException("Unexpected result. Wrong TVDB id.");
	    }
	    
	    for(String key:(Set<String>)showArt.keySet()) {
	        if ("thetvdb_id".equals(key)) continue;
	        List<JSONObject> artArray = (List<JSONObject>)showArt.get(key);
	        parsArtArray(show, key, artArray);
	    }
	}

	private void parsArtArray(Sh show, String key, List<JSONObject> artArray) {
	    System.out.println(key);
	    for (JSONObject jsonArt : artArray) {
	        if (!"en".equals(jsonArt.get("lang"))) continue;
	        //only english for now
	        Fanart fanart = artConstructor(key);
	        if (fanart == null) continue;
	        fanart.setId(Integer.parseInt((String)jsonArt.get("id")));
	        fanart.setRatingCount(Integer.parseInt((String)jsonArt.get("likes")));
	        if (jsonArt.containsKey("season") && !"all".equals(jsonArt.get("season"))) {
	            fanart.setSeason(Integer.parseInt((String)jsonArt.get("season")));
	        }
	        fanart.setUrl((String)jsonArt.get("url"));
	        fanart.setThumbUrl(fanart.getUrl() + "/preview");
	        show.addFanart(fanart);
            }
	}
	
	private Fanart artConstructor(String key) {
	    Fanart result = new Fanart();
	    if ("clearart".equals(key)){
	        result.setType(FanartType.CLEARART);
	    } else if ("clearlogo".equals(key)){
                result.setType(FanartType.CLEARLOGO);
            } else if ("showbackground".equals(key)){
                result.setType(FanartType.BACKDROP);
            } else if ("tvbanner".equals(key)){
                result.setType(FanartType.BANNER_GRAPHICAL);
            } else if ("hdtvlogo".equals(key)){
                result.setType(FanartType.CLEARLOGO);
            } else if ("tvthumb".equals(key)){
                result.setType(FanartType.THUMB);
            } else if ("hdclearart".equals(key)){
                result.setType(FanartType.CLEARART);
            } else if ("seasonthumb".equals(key)){
                return null; //not supported
                //result.setType(FanartType.THUMB);
            } else {
                return null;
            }
	    return result;
	}

	/*
	 * Package accessible so it can be mocked for test purposes.
	 */
	String getUrl(int theTvDbId) {
		return "http://api.fanart.tv/webservice/series/"+apiKey+"/"+theTvDbId;
	}
	
	       private InputStream openStream(String url) {
	                try {
	                        return new URL(url).openStream();
	                } catch (IOException e) {
	                        LOG.info("Unable to open JSON data.",e);
	                        throw new TvApiException("Unable to open JSON data.",e);
	                }
	        }

}
