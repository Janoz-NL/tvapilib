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
