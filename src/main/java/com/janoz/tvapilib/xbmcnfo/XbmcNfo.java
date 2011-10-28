package com.janoz.tvapilib.xbmcnfo;

import java.io.File;
import java.io.IOException;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

public interface XbmcNfo<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	
	Sh loadShow(File nfoFile) throws IOException;
	
	Ep loadEpisode(File nfoFile) throws IOException;
}
