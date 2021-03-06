/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link HeaderGroup}.
 *
 */
public class TestHeaderGroup {

    @Test
    public void testConstructor() {
        final HeaderGroup headergroup = new HeaderGroup();
        Assert.assertNotNull(headergroup.getAllHeaders());
        Assert.assertEquals(0, headergroup.getAllHeaders().length);
    }

    @Test
    public void testClear() {
        final HeaderGroup headergroup = new HeaderGroup();
        headergroup.addHeader(new BasicHeader("name", "value"));
        Assert.assertEquals(1, headergroup.getAllHeaders().length);
        headergroup.clear();
        Assert.assertEquals(0, headergroup.getAllHeaders().length);
    }

    @Test
    public void testAddRemoveHeader() {
        final HeaderGroup headergroup = new HeaderGroup();
        final Header header = new BasicHeader("name", "value");
        headergroup.addHeader(header);
        headergroup.addHeader(null);
        Assert.assertEquals(1, headergroup.getAllHeaders().length);
        headergroup.removeHeader(header);
        headergroup.removeHeader(null);
        Assert.assertEquals(0, headergroup.getAllHeaders().length);
    }

    @Test
    public void testUpdateHeader() {
        final HeaderGroup headergroup = new HeaderGroup();
        final Header header1 = new BasicHeader("name1", "value1");
        final Header header2 = new BasicHeader("name2", "value2");
        final Header header3 = new BasicHeader("name3", "value3");
        headergroup.addHeader(header1);
        headergroup.addHeader(header2);
        headergroup.addHeader(header3);
        headergroup.updateHeader(new BasicHeader("name2", "newvalue"));
        headergroup.updateHeader(new BasicHeader("name4", "value4"));
        headergroup.updateHeader(null);
        Assert.assertEquals(4, headergroup.getAllHeaders().length);
        Assert.assertEquals("newvalue", headergroup.getFirstHeader("name2").getValue());
    }

    @Test
    public void testSetHeaders() {
        final HeaderGroup headergroup = new HeaderGroup();
        final Header header1 = new BasicHeader("name1", "value1");
        final Header header2 = new BasicHeader("name2", "value2");
        final Header header3 = new BasicHeader("name3", "value3");
        headergroup.addHeader(header1);
        headergroup.setHeaders(new Header[] { header2, header3 });
        Assert.assertEquals(2, headergroup.getAllHeaders().length);
        Assert.assertEquals(0, headergroup.getHeaders("name1").length);
        Assert.assertFalse(headergroup.containsHeader("name1"));
        Assert.assertEquals(1, headergroup.getHeaders("name2").length);
        Assert.assertTrue(headergroup.containsHeader("name2"));
        Assert.assertEquals(1, headergroup.getHeaders("name3").length);
        Assert.assertTrue(headergroup.containsHeader("name3"));
        headergroup.setHeaders(null);
        Assert.assertEquals(0, headergroup.getAllHeaders().length);
    }

    @Test
    public void testFirstLastHeaders() {
        final HeaderGroup headergroup = new HeaderGroup();
        final Header header1 = new BasicHeader("name", "value1");
        final Header header2 = new BasicHeader("name", "value2");
        final Header header3 = new BasicHeader("name", "value3");
        headergroup.setHeaders(new Header[] { header1, header2, header3 });

        Assert.assertNull(headergroup.getFirstHeader("whatever"));
        Assert.assertNull(headergroup.getLastHeader("whatever"));

        Assert.assertEquals("value1", headergroup.getFirstHeader("name").getValue());
        Assert.assertEquals("value3", headergroup.getLastHeader("name").getValue());
    }

    @Test
    public void testCondensedHeader() {
        final HeaderGroup headergroup = new HeaderGroup();
        Assert.assertNull(headergroup.getCondensedHeader("name"));

        final Header header1 = new BasicHeader("name", "value1");
        final Header header2 = new BasicHeader("name", "value2");
        final Header header3 = new BasicHeader("name", "value3");
        headergroup.setHeaders(new Header[] { header1, header2, header3 });

        Assert.assertEquals("value1, value2, value3", headergroup.getCondensedHeader("name").getValue());

        headergroup.setHeaders(new Header[] { header1 });
        Assert.assertEquals(header1, headergroup.getCondensedHeader("name"));
    }

    @Test
    public void testIterator() {
        final HeaderGroup headergroup = new HeaderGroup();
        final HeaderIterator i = headergroup.iterator();
        Assert.assertNotNull(i);
        Assert.assertFalse(i.hasNext());
    }

    @Test
    public void testHeaderRemove() {
        final HeaderGroup headergroup = new HeaderGroup();
        final Header header1 = new BasicHeader("name", "value1");
        final Header header2 = new BasicHeader("name", "value2");
        final Header header3 = new BasicHeader("name", "value3");
        headergroup.setHeaders(new Header[] { header1, header2, header3 });
        final HeaderIterator i = headergroup.iterator();
        Assert.assertNotNull(i);
        Assert.assertTrue(i.hasNext());
        i.next();
        Assert.assertTrue(i.hasNext());
        i.next();
        i.remove();
        Assert.assertEquals(2, headergroup.getAllHeaders().length);
        Assert.assertTrue(i.hasNext());
        i.next();
        i.remove();
        Assert.assertEquals(1, headergroup.getAllHeaders().length);
        Assert.assertFalse(i.hasNext());
    }

    @Test
    public void testCloning() throws Exception {
        final HeaderGroup orig = new HeaderGroup();
        final Header header1 = new BasicHeader("name", "value1");
        final Header header2 = new BasicHeader("name", "value2");
        final Header header3 = new BasicHeader("name", "value3");
        orig.setHeaders(new Header[] { header1, header2, header3 });
        final HeaderGroup clone = (HeaderGroup) orig.clone();
        final Header[] headers1 = orig.getAllHeaders();
        final Header[] headers2 = clone.getAllHeaders();
        Assert.assertNotNull(headers1);
        Assert.assertNotNull(headers2);
        Assert.assertEquals(3, headers2.length);
        Assert.assertEquals(headers1.length, headers2.length);
        for (int i = 0; i < headers1.length; i++) {
            Assert.assertEquals(headers1[i].getName(), headers2[i].getName());
            Assert.assertEquals(headers1[i].getValue(), headers2[i].getValue());
        }
    }

    @Test
    public void testSerialization() throws Exception {
        final HeaderGroup orig = new HeaderGroup();
        final Header header1 = new BasicHeader("name", "value1");
        final Header header2 = new BasicHeader("name", "value2");
        final Header header3 = new BasicHeader("name", "value3");
        orig.setHeaders(new Header[] { header1, header2, header3 });
        final ByteArrayOutputStream outbuffer = new ByteArrayOutputStream();
        final ObjectOutputStream outStream = new ObjectOutputStream(outbuffer);
        outStream.writeObject(orig);
        outStream.close();
        final byte[] raw = outbuffer.toByteArray();
        final ByteArrayInputStream inBuffer = new ByteArrayInputStream(raw);
        final ObjectInputStream inStream = new ObjectInputStream(inBuffer);
        final HeaderGroup clone = (HeaderGroup) inStream.readObject();
        final Header[] headers1 = orig.getAllHeaders();
        final Header[] headers2 = clone.getAllHeaders();
        Assert.assertNotNull(headers1);
        Assert.assertNotNull(headers2);
        Assert.assertEquals(headers1.length, headers2.length);
        for (int i = 0; i < headers1.length; i++) {
            Assert.assertEquals(headers1[i].getName(), headers2[i].getName());
            Assert.assertEquals(headers1[i].getValue(), headers2[i].getValue());
        }
    }

}
