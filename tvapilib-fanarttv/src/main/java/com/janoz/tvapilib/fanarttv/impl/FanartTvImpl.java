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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.janoz.tvapilib.fanarttv.FanartTv;
import com.janoz.tvapilib.model.Art;
import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.support.TvApiException;

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
	    JSONObject showArt = (JSONObject)JSONValue.parse(r);
	    //validate
	    String receivedTvDbId = (String)showArt.get("thetvdb_id");
	    if (!receivedTvDbId.equals(""+theTvDbId)) {
	        throw new TvApiException("Unexpected result. Wrong TVDB id.");
	    }

	    for(String key:(Set<String>)showArt.keySet()) {
            if (artMapping.containsKey(key)) {
                List<JSONObject> artArray = (List<JSONObject>) showArt.get(key);
                parsArtArray(show, key, artArray);
            }
	    }
	}

	private void parsArtArray(Sh show, String key, List<JSONObject> artArray) {
	    for (JSONObject jsonArt : artArray) {
	        if (!"en".equals(jsonArt.get("lang"))) continue;
	        //only english for now
	        Art art = artConstructor(key);
	        art.setId(Integer.parseInt((String)jsonArt.get("id")));
	        art.setRatingCount(Integer.parseInt((String)jsonArt.get("likes")));
	        art.setUrl((String)jsonArt.get("url"));
	        art.setThumbUrl(art.getUrl() + "/preview");
	        if (jsonArt.containsKey("season")) {
	        	String strSeason = (String)jsonArt.get("season");
                int season = "all".equals(strSeason)?-1:"".equals(strSeason)?0:Integer.parseInt(strSeason);
	        	show.getSeason(season).addArt(art);
	        } else {
	            show.addArt(art);
	        }
        }
	}
	
	private Art artConstructor(String key) {
        if (artMapping.containsKey(key)) {
            Art result = new Art();
            result.setHd(key.startsWith("hd"));
            result.setType(artMapping.get(key));
            return result;
        }
        return null;
	}

    private static Map<String, Art.Type> artMapping;
    static {
        artMapping = new HashMap<String, Art.Type>();
        artMapping.put("clearart",Art.Type.CLEARART);
        artMapping.put("clearlogo",Art.Type.CLEARLOGO);
        artMapping.put("showbackground",Art.Type.BACKDROP);
        artMapping.put("tvbanner",Art.Type.BANNER);
        artMapping.put("hdtvlogo",Art.Type.CLEARLOGO);
        artMapping.put("tvthumb",Art.Type.THUMB);
        artMapping.put("hdclearart",Art.Type.CLEARART);
        artMapping.put("seasonthumb",Art.Type.THUMB);
    }


	/*
	 * Package accessible so it can be mocked for test purposes.
	 */
	String getUrl(int theTvDbId) {
		return "http://webservice.fanart.tv/v3/tv/"+theTvDbId+"?api_key="+apiKey;
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
