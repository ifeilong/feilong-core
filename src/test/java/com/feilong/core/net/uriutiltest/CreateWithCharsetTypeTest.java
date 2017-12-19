/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.net.uriutiltest;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.net.URIUtil.encode;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.junit.Test;

import com.feilong.core.net.URIParseException;
import com.feilong.core.net.URIUtil;

/**
 * The Class URIUtilCreateWithCharsetTypeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CreateWithCharsetTypeTest{

    /**
     * Test create.
     */
    @Test
    public void testCreate(){
        String url = "http://127.0.0.1/cmens/t-b-f-a-c-s-f-p-g-e-i-o.htm?a=1&a=2";
        URI uri = URIUtil.create(url, UTF8);
        assertEquals(url, uri.toString());
    }

    /**
     * Test create chinese.
     */
    @Test
    public void testCreateChinese(){
        String url = "http://127.0.0.1/cmens/../t-b-f-a-c-s-f-p-g-e-i-o.htm?a=飞龙&a=2";
        URI uri = URIUtil.create(url, UTF8);
        assertEquals("http://127.0.0.1/cmens/../t-b-f-a-c-s-f-p-g-e-i-o.htm?a=" + encode("飞龙", UTF8) + "&a=2", uri.toString());
    }

    /**
     * Test create null charset type.
     */
    @Test
    public void testCreateNullCharsetType(){
        String url = "http://127.0.0.1/cmens/../t-b-f-a-c-s-f-p-g-e-i-o.htm?a=飞龙&a=2";
        URI uri = URIUtil.create(url, null);
        assertEquals("http://127.0.0.1/cmens/../t-b-f-a-c-s-f-p-g-e-i-o.htm?a=" + "飞龙" + "&a=2", uri.toString());
    }

    /**
     * Test create null uri string.
     */
    @Test(expected = NullPointerException.class)
    public void testCreateNullUriString(){
        URIUtil.create(null, UTF8);
    }

    /**
     * Test create empty uri string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateEmptyUriString(){
        URIUtil.create("", UTF8);
    }

    /**
     * Test create blank uri string.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateBlankUriString(){
        URIUtil.create(" ", UTF8);
    }

    /**
     * Test create URI parse exception.
     */
    @Test(expected = URIParseException.class)
    public void testCreateURIParseException(){
        String url = "http:/127.0.0.-->?1/cmens/t-b-f-a-c-s-f-p-g-e-i-o.htm?a=1&a=2";
        URIUtil.create(url, UTF8);
    }
}
