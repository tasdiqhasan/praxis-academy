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
package org.apache.http.impl.client.cache;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheEntrySerializationException;
import org.apache.http.client.cache.HttpCacheEntrySerializer;
import org.apache.http.client.cache.Resource;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.Before;
import org.junit.Test;

import com.sun.rowset.JdbcRowSetImpl;

public class TestHttpCacheEntrySerializers {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private HttpCacheEntrySerializer impl;

    @Before
    public void setUp() {
        impl = new DefaultHttpCacheEntrySerializer();
    }

    @Test
    public void canSerializeEntriesWithVariantMaps() throws Exception {
        readWriteVerify(makeCacheEntryWithVariantMap());
    }

    @Test(expected = HttpCacheEntrySerializationException.class)
    public void throwExceptionIfUnsafeDeserialization() throws IOException {
        impl.readFrom(new ByteArrayInputStream(serializeProhibitedObject()));
    }

    @Test(expected = HttpCacheEntrySerializationException.class)
    public void allowClassesToBeDeserialized() throws IOException {
        impl = new DefaultHttpCacheEntrySerializer(
                Pattern.compile("javax.sql.rowset.BaseRowSet"),
                Pattern.compile("com.sun.rowset.JdbcRowSetImpl"));
        readVerify(serializeProhibitedObject());
    }

    @Test(expected = HttpCacheEntrySerializationException.class)
    public void allowClassesToBeDeserializedByRegex() throws IOException {
        impl = new DefaultHttpCacheEntrySerializer(
                Pattern.compile(("^com\\.sun\\.rowset\\.(.*)")),
                Pattern.compile("^javax\\.sql\\.rowset\\.BaseRowSet$"));
        readVerify(serializeProhibitedObject());
    }

    private byte[] serializeProhibitedObject() throws IOException {
        final JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.writeObject(jdbcRowSet);
        } finally {
            oos.close();
        }
        return baos.toByteArray();
    }

    private void readVerify(final byte[] data) throws IOException {
        impl.readFrom(new ByteArrayInputStream(data));
    }

    public void readWriteVerify(final HttpCacheEntry writeEntry) throws IOException {
        // write the entry
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        impl.writeTo(writeEntry, out);

        // read the entry
        final ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        final HttpCacheEntry readEntry = impl.readFrom(in);

        // compare
        assertTrue(areEqual(readEntry, writeEntry));
    }

    private HttpCacheEntry makeCacheEntryWithVariantMap() {
        final Header[] headers = new Header[5];
        for (int i = 0; i < headers.length; i++) {
            headers[i] = new BasicHeader("header" + i, "value" + i);
        }
        final String body = "Lorem ipsum dolor sit amet";

        final ProtocolVersion pvObj = new ProtocolVersion("HTTP", 1, 1);
        final StatusLine slObj = new BasicStatusLine(pvObj, 200, "ok");
        final Map<String,String> variantMap = new HashMap<String,String>();
        variantMap.put("test variant 1","true");
        variantMap.put("test variant 2","true");
        final HttpCacheEntry cacheEntry = new HttpCacheEntry(new Date(), new Date(),
                slObj, headers, new HeapResource(Base64.decodeBase64(body
                        .getBytes(UTF8))), variantMap, HeaderConstants.GET_METHOD);

        return cacheEntry;
    }

    private boolean areEqual(final HttpCacheEntry one, final HttpCacheEntry two) throws IOException {
        // dates are only stored with second precision, so scrub milliseconds
        if (!((one.getRequestDate().getTime() / 1000) == (two.getRequestDate()
                .getTime() / 1000))) {
            return false;
        }
        if (!((one.getResponseDate().getTime() / 1000) == (two
                .getResponseDate().getTime() / 1000))) {
            return false;
        }
        if (!one.getProtocolVersion().equals(two.getProtocolVersion())) {
            return false;
        }

        final byte[] onesByteArray = resourceToBytes(one.getResource());
        final byte[] twosByteArray = resourceToBytes(two.getResource());

        if (!Arrays.equals(onesByteArray,twosByteArray)) {
            return false;
        }

        final Header[] oneHeaders = one.getAllHeaders();
        final Header[] twoHeaders = two.getAllHeaders();
        if (!(oneHeaders.length == twoHeaders.length)) {
            return false;
        }
        for (int i = 0; i < oneHeaders.length; i++) {
            if (!oneHeaders[i].getName().equals(twoHeaders[i].getName())) {
                return false;
            }
            if (!oneHeaders[i].getValue().equals(twoHeaders[i].getValue())) {
                return false;
            }
        }

        return true;
    }

    private byte[] resourceToBytes(final Resource res) throws IOException {
        final InputStream inputStream = res.getInputStream();
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int readBytes;
        final byte[] bytes = new byte[8096];
        while ((readBytes = inputStream.read(bytes)) > 0) {
            outputStream.write(bytes, 0, readBytes);
        }

        final byte[] byteData = outputStream.toByteArray();

        inputStream.close();
        outputStream.close();

        return byteData;
    }
}
