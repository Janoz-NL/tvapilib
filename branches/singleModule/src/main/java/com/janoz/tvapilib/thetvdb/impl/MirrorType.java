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
package com.janoz.tvapilib.thetvdb.impl;

public enum MirrorType {
	XML(1), BANNER(2), ZIP(4);

	private int mask;

	MirrorType(int mask) {
		this.mask = mask;
	}

	public boolean matches(int i) {
		return 0 != (i & mask);
	}
}
