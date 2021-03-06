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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheEntrySerializationException;
import org.apache.http.client.cache.HttpCacheEntrySerializer;

/**
 * {@link HttpCacheEntrySerializer} implementation that uses the default (native)
 * serialization.
 *
 * @see java.io.Serializable
 *
 * @since 4.1
 */
@Contract(threading = ThreadingBehavior.IMMUTABLE)
public class DefaultHttpCacheEntrySerializer implements HttpCacheEntrySerializer {

    private static final List<Pattern> ALLOWED_CLASS_PATTERNS = Collections.unmodifiableList(Arrays.asList(
            Pattern.compile("^(\\[L)?org\\.apache\\.http\\.(.*)"),
            Pattern.compile("^(\\[L)?java\\.util\\.(.*)"),
            Pattern.compile("^(\\[L)?java\\.lang\\.(.*)$"),
            Pattern.compile("^\\[B$")));

    private final List<Pattern> allowedClassPatterns;

    DefaultHttpCacheEntrySerializer(final Pattern... allowedClassPatterns) {
        this.allowedClassPatterns = Collections.unmodifiableList(Arrays.asList(allowedClassPatterns));
    }

    public DefaultHttpCacheEntrySerializer() {
        this.allowedClassPatterns = ALLOWED_CLASS_PATTERNS;
    }

    @Override
    public void writeTo(final HttpCacheEntry cacheEntry, final OutputStream os) throws IOException {
        final ObjectOutputStream oos = new ObjectOutputStream(os);
        try {
            oos.writeObject(cacheEntry);
        } finally {
            oos.close();
        }
    }

    @Override
    public HttpCacheEntry readFrom(final InputStream is) throws IOException {
        final ObjectInputStream ois = new RestrictedObjectInputStream(is, allowedClassPatterns);
        try {
            return (HttpCacheEntry) ois.readObject();
        } catch (final ClassNotFoundException ex) {
            throw new HttpCacheEntrySerializationException("Class not found: " + ex.getMessage(), ex);
        } finally {
            ois.close();
        }
    }

    private static class RestrictedObjectInputStream extends ObjectInputStream {

        private final List<Pattern> allowedClassPatterns;

        private RestrictedObjectInputStream(final InputStream in, final List<Pattern> patterns) throws IOException {
            super(in);
            this.allowedClassPatterns = patterns;
        }

        @Override
        protected Class<?> resolveClass(final ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if (isProhibited(desc)) {
                throw new HttpCacheEntrySerializationException(String.format(
                        "Class %s is not allowed for deserialization", desc.getName()));
            }
            return super.resolveClass(desc);
        }

        private boolean isProhibited(final ObjectStreamClass desc) {
            for (final Pattern pattern : allowedClassPatterns) {
                if (pattern.matcher(desc.getName()).matches()) {
                    return false;
                }
            }
            return true;
        }
    }

}
