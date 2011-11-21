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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;
import com.janoz.tvapilib.model.ModelFactory;
import com.janoz.tvapilib.support.XmlParsingObject;
import com.janoz.tvapilib.xbmcnfo.XbmcNfo;

public class XbmcNfoImpl<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> extends XmlParsingObject implements XbmcNfo<Sh, Se, Ep> {

	private ModelFactory<Sh,Se,Ep> modelFactory;
	
	public XbmcNfoImpl(ModelFactory<Sh,Se,Ep> modelFactory) {
		this.modelFactory = modelFactory;
	}
	
	@Override
	public Sh loadShow(File nfoFile) throws IOException {
		FileInputStream fis = new FileInputStream(nfoFile);
		ShowParser<Sh,Se,Ep> parser = new ShowParser<Sh,Se,Ep>(modelFactory);
		parse(parser, fis);
		return parser.getShow();
	}

	@Override
    public void saveShow(Sh show, File nfoFile) throws IOException {
		if (!nfoFile.createNewFile()) {
			throw new IOException("Unable to create file '" + nfoFile + "'.");
		}
		OutputStreamWriter osw = createUtf8FileWriter(nfoFile);
		osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<tvshow>\n");
		writeTag(osw,"id",show.getTheTvDbId());
		writeTag(osw,"tvdbid",show.getTheTvDbId());
		writeTag(osw,"title",show.getTitle());
		writeTag(osw,"plot",show.getDescription());
		writeTag(osw,"studio",show.getNetwork());
		writeTag(osw,"mpaa",show.getContentRating());
		osw.write("</tvshow>");
		osw.close();
    }
	
	@Override
    public void saveEpisode(Ep episode, File nfoFile) throws IOException {
		if (!nfoFile.createNewFile()) {
			throw new IOException("Unable to create file '" + nfoFile + "'.");
		}
		OutputStreamWriter osw = createUtf8FileWriter(nfoFile);
/*
 <episodedetails>
        <rating>10.00</rating>
        <thumb>http://thetvdb.com/banners/episodes/164981/2528821.jpg</thumb>
        <credits>Writer</credits>
        <director>Mr. Vision</director>
        
        <studio>Production studio or channel</studio>
        <mpaa>MPAA certification</mpaa>
 */
		osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<episodedetails>\n");
		writeTag(osw,"title",episode.getTitle());
		writeTag(osw,"season",episode.getSeason().getSeason());
		writeTag(osw,"episode",episode.getEpisode());
		writeTag(osw,"plot",episode.getDescription());
		writeTag(osw,"aired",episode.getAired());
		osw.write("</episodedetails>");
		osw.close();
    }


	private OutputStreamWriter createUtf8FileWriter(File file) throws IOException {
		return new OutputStreamWriter(new FileOutputStream(file), "UTF8");
	}
	
	private String encode(String input) {
		return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	private void writeTag(Writer w, String tag, Date value) throws IOException {
		if (value == null) {
			writeTag(w, tag,new SimpleDateFormat("yyyy-MM-dd").format(value));
		}
	}

	private void writeTag(Writer w, String tag, Integer value) throws IOException {
		if (value != null) {
			writeTag(w, tag,value.toString());
		}
	}
	
	private void writeTag(Writer w, String tag, String value) throws IOException {
		if (value != null) {
			w.write("\t<");
			w.write(tag);
			w.write(">");
			w.write(encode(value));
			w.write("</");
			w.write(tag);
			w.write(">\n");
		}
	}
	
	@Override
	public Ep loadEpisode(File nfoFile) throws IOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
