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
 * TODO language
 */
public class Subtitle {

	private String fileTitle;
	private String downloadUrl;

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
