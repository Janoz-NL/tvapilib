package com.janoz.tvapilib.support;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;

public class AbstractSaxParserTest {

    private Attributes lastAttributes = null;

    private AbstractSaxParser subject = new AbstractSaxParser() {
        @Override
        public void handleTagStart(Attributes attributes) {
            lastAttributes = attributes;
        }
        
        @Override
        public void handleTagEnd() {
        	//Do nothing
        }
        
        @Override
        public void handleContent(String content) {
        	//Do nothing
        }
    };

    @Before
    public void setup() throws Exception {
        subject.startDocument();
        subject.startElement("", "First", null, null);
        subject.startElement("", "Second", null, null);
        subject.startElement("", "ThiRD", null, null);
    }

    @After
    public void teardown() throws Exception {
        subject.endElement("", "thiRD", "");
        subject.endElement("", "secONd", "");
        subject.endElement("", "fIrSt", "");
        subject.endDocument();
    }

    @Test
    public void testStackSize() throws Exception{
        assertTrue(subject.isStackSize(3));
        assertFalse(subject.isStackSize(4));
    }

    @Test
    public void testStack() throws Exception{
        assertTrue(subject.stackEquals("first","second","third"));
        assertFalse(subject.stackEquals("First","second","third"));
        assertFalse(subject.stackEquals("first","second"));
        assertEquals("third",subject.getNodeName());
        assertTrue(subject.stackStartsWith("first"));
        assertTrue(subject.stackStartsWith("first","second"));
        assertTrue(subject.stackStartsWith("first","second","third"));
        assertFalse(subject.stackStartsWith("first","second","third","fourth"));
        assertFalse(subject.stackStartsWith("second","third"));
    }
    
    @Test
    public void testStackTail() throws Exception{
        
        List<String> tail;
        
        tail = subject.getStackTail(1);
        assertEquals(2,tail.size());
        assertEquals("second",tail.get(0));
        assertEquals("third",tail.get(1));

        tail = subject.getStackTail(2);
        assertEquals(1,tail.size());
        assertEquals("third",tail.get(0));

        tail = subject.getStackTail(3);
        assertTrue(tail.isEmpty());
    }
    
    @Test
    public void testHandleContent() throws Exception{
        Attributes attributes = createMock(Attributes.class);
        subject.startElement(null, "fourth", null, attributes);
        assertSame(attributes, lastAttributes);
        subject.endElement(null, "fourth", null);
    }
}
