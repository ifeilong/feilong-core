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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.net.URIUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.net.URIUtil.encode;

/**
 * The Class URIUtilEncodeUriTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class EncodeUriTest{

    /** The Constant PATH. */
    private static final String PATH = "www.baidu.com";

    /**
     * Test encode uri.
     */
    @Test
    public void testEncodeUri(){
        String uriString = PATH + "?name=金鑫&name=飞龙&age=18";
        assertEquals(
                        Slf4jUtil.format(PATH + "?name={}&name={}&age=18", encode("金鑫", UTF8), encode("飞龙", UTF8)),
                        URIUtil.encodeUri(uriString, UTF8));
    }

    /**
     * Test encode uri 1.
     */
    @Test
    public void testEncodeUri1(){
        String pattern = "mailto:venus@163.com?subject={}&body={}";
        String uriString = Slf4jUtil.format(pattern, "你好", "我是飞天奔月<br>哈哈哈哈");

        String result = URIUtil.encodeUri(uriString, UTF8);
        assertEquals(Slf4jUtil.format(pattern, encode("你好", UTF8), encode("我是飞天奔月<br>哈哈哈哈", UTF8)), result);
    }

    /**
     * Test encode uri null uri.
     */
    @Test(expected = NullPointerException.class)
    public void testEncodeUriNullUri(){
        URIUtil.encodeUri(null, UTF8);
    }

    /**
     * Test encode uri empty uri.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEncodeUriEmptyUri(){
        URIUtil.encodeUri("", UTF8);
    }

    /**
     * Test encode uri blank uri.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEncodeUriBlankUri(){
        URIUtil.encodeUri(" ", UTF8);
    }

    /**
     * Test encode uri null charset type.
     */
    //******************
    @Test
    public void testEncodeUriNullCharsetType(){
        assertEquals(PATH, URIUtil.encodeUri(PATH, null));
    }

    /**
     * Test encode uri empty charset type.
     */
    @Test
    public void testEncodeUriEmptyCharsetType(){
        assertEquals(PATH, URIUtil.encodeUri(PATH, ""));
    }

    /**
     * Test encode uri blank charset type.
     */
    @Test
    public void testEncodeUriBlankCharsetType(){
        assertEquals(PATH, URIUtil.encodeUri(PATH, " "));
    }
}
