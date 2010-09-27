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
package com.janoz.tvapilib.model;

/**
 * @author Gijs de Vries aka Janoz
 *
 */
public class Subtitle {

	private Episode episode;
	private String fileTitle;
	private String downloadUrl;

	/**
	 * @return The episode this subtitle belongs to.
	 */
	public Episode getEpisode() {
		return episode;
	}

	/**
	 * @param episode The episode this subtitle belongs to.
	 */
	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	/**
	 * @return The title of the file
	 */
	public String getFileTitle() {
		return fileTitle;
	}

	/**
	 * @param fileTitle The title of the file
	 */
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	/**
	 * @return URL of this subtitle.
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl URL of this subtitle.
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
