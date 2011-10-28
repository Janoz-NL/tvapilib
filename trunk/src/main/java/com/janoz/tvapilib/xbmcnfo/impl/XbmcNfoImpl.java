package com.janoz.tvapilib.xbmcnfo.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	public Ep loadEpisode(File nfoFile) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
