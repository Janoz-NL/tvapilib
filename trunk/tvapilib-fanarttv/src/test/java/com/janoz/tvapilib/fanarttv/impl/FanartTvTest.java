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

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.janoz.tvapilib.fanarttv.impl.DefaultFanartTvImpl;
import com.janoz.tvapilib.model.Fanart;
import com.janoz.tvapilib.model.FanartType;
import com.janoz.tvapilib.model.impl.Show;
import com.janoz.tvapilib.support.TvApiException;
public class FanartTvTest {

    DefaultFanartTvImpl subject;

    @Before
    public void setup() {
        subject = createMockBuilder(DefaultFanartTvImpl.class)
                .addMockedMethod("getUrl").withConstructor("**apikey**").createMock();

    }

    @Test
    public void addArtTest() throws Exception {
        expect(subject.getUrl(eq(164301))).andReturn(
                this.getClass().getClassLoader().getResource("responses/fanart.tv/nikita.json").toString());
        replay(subject);
        Show show = new Show();
        show.setTheTvDbId(164301);
        subject.addFanart(show);
        verify(subject);
        assertEquals(3, show.getBackdrops().size());
        assertEquals(1, show.getBanners().size());
        assertEquals(4, show.getCleararts().size());
        assertEquals(8, show.getClearlogos().size());
        assertEquals(2, show.getThumbs().size());
        
    }

    @Test
    public void addCoruptResponseArtTest() throws Exception {
        expect(subject.getUrl(eq(-1))).andReturn(
                this.getClass().getClassLoader().getResource("responses/fanart.tv/nikita.json").toString());
        replay(subject);
        Show show = new Show();
        show.setTheTvDbId(-1);
        try {
            subject.addFanart(show);
            fail();
        } catch (TvApiException tae) {
            verify(subject);
            assertEquals("Unexpected result. Wrong TVDB id.", tae.getMessage());
        }
    }
    @Test
    public void addNoArtTest() throws Exception {
        expect(subject.getUrl(eq(153021))).andReturn(
                this.getClass().getClassLoader().getResource("responses/fanart.tv/null.json").toString());
        replay(subject);
        Show show = new Show();
        show.setTheTvDbId(153021);
        subject.addFanart(show);
        verify(subject);
    }

}
