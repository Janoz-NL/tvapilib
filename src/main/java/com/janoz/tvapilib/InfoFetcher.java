/*******************************************************************************
 * Copyright 2010 Gijs de Vries aka Janoz
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.janoz.tvapilib;

import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import com.janoz.tvapilib.bierdopje.Bierdopje;
import com.janoz.tvapilib.bierdopje.impl.BierdopjeImpl;
import com.janoz.tvapilib.lockstockmods.LockStockMods;
import com.janoz.tvapilib.lockstockmods.impl.LockStockModsImpl;
import com.janoz.tvapilib.model.Episode;
import com.janoz.tvapilib.model.Show;
import com.janoz.tvapilib.model.Subtitle;
import com.janoz.tvapilib.thetvdb.TheTVDB;
import com.janoz.tvapilib.thetvdb.impl.TheTVDBImpl;

/**
 * @author Gijs de Vries aka Janoz
 *
 *
 * Simple class to demonstrate usage.
 */
public class InfoFetcher {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Properties keys = new Properties();
		keys.load(new FileReader("apikeys.properties"));

		TheTVDB theTVDB = new TheTVDBImpl(keys.getProperty("thetvdb"));
		Bierdopje bierdopje = new BierdopjeImpl(keys.getProperty("bierdopje"));
		Show show = theTVDB.getShow(164301);
		Episode episode = theTVDB.getEpisode(show, 1, 1);
		List<Subtitle> subtitles = bierdopje.getAllSubsFor(episode);
		System.out.println(episode.getTitle());
		for (Subtitle subtitle : subtitles) {

			System.out.println(subtitle.getDownloadUrl());

		}
		LockStockMods lockStockMods = new LockStockModsImpl();
		System.out.println(lockStockMods.getClearLogoURL(164301));

	}
}
