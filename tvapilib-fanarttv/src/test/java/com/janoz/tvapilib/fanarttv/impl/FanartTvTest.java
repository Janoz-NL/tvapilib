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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

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
        assertEquals(1, show.getSeason(5).getArts().size());
        assertEquals(0, show.getSeason(4).getArts().size());
        assertEquals(3, show.getSeason(3).getArts().size());
        assertEquals(3, show.getSeason(2).getArts().size());
        assertEquals(3, show.getSeason(1).getArts().size());
        assertEquals(1, show.getSeason(0).getArts().size());
        assertEquals(4, show.getSeason(-1).getArts().size());
        
        assertEquals(15, show.getArts().size());
        
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

    @Test
    public void testSeasonNullpointer() throws Exception{
        expect(subject.getUrl(eq(153021))).andReturn(
                this.getClass().getClassLoader().getResource("responses/fanart.tv/walkingDeadNumberFormat.json").toString());

        replay(subject);
        Show show = new Show();
        show.setTheTvDbId(153021);
        subject.addFanart(show);
        verify(subject);
    }


}
