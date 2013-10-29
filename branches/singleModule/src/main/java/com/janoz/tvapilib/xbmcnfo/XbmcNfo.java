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
package com.janoz.tvapilib.xbmcnfo;

import java.io.File;
import java.io.IOException;

import com.janoz.tvapilib.model.IEpisode;
import com.janoz.tvapilib.model.ISeason;
import com.janoz.tvapilib.model.IShow;

public interface XbmcNfo<Sh extends IShow<Sh,Se,Ep>, Se extends ISeason<Sh,Se,Ep>, Ep extends IEpisode<Sh,Se,Ep>> {
	
	/**
	 * @param nfoFile 
	 * 		showinfo.nfo file to read show data from
	 * @return constructed 
	 * 		show object
	 * @throws IOException 
	 * 		when a read error occurred 
	 * 		TODO: Maybe wrap it in a tvapiexception like everywhere? 
	 */
	Sh loadShow(File nfoFile) throws IOException;

	/**
	 * @param show 
	 * 		Show to save
	 * @param nfoFile 
	 * 		file to save show to. If the file exist it will be overwritten, if it 
	 * 		doesn't exist it will be created.
	 * @throws IOException 
	 * 		when a write error occurred 
	 * 		TODO: Maybe wrap it in a tvapiexception like everywhere?  
	 */
	void saveShow(Sh show, File nfoFile) throws IOException;

	/**
	 * @param nfoFile 
	 * 		showinfo.nfo file to read show data from
	 * @return 
	 * 		constructed	show object
	 * @throws IOException 
	 * 		when a read error occurred 
	 * 		TODO: Maybe wrap it in a tvapiexception like everywhere? 
	 */
	Ep loadEpisode(File nfoFile) throws IOException;
	
	/**
	 * @param episode 
	 * 		episode to save
	 * @param nfoFile 
	 * 		file to save episode to. If the file exist it will be overwritten, if it doesn't exist it will be created.
	 * @throws IOException 
	 * 		when a write error occurred 
	 * 		TODO: Maybe wrap it in a tvapiexception like everywhere?
	 */
	void saveEpisode(Ep episode, File nfoFile) throws IOException;
}
