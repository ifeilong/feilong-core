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
package com.feilong.core.net.urlutiltest;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URLUtil;

/**
 * The Class URLUtilGetUnionUrlTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetUnionUrlTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUnionUrlTest.class);

    private final URL           url    = URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing/");

    /**
     * Test get union url 1.
     */
    @Test
    public void testGetUnionUrl1(){
        LOGGER.debug(URLUtil.getUnionUrl(URLUtil.toURL("E:\\test"), "sanguo"));
    }

    //

    @Test
    public void testGetUnionUrl1222(){
        LOGGER.debug(URLUtil.getUnionUrl(URLUtil.toURL("Ea:\\test"), "sanguo"));
    }

    /**
     * Test get union url2.
     */
    @Test
    public void testGetUnionUrl2(){
        assertEquals("http://www.exiaoshuo.com/jinyiyexing/1173348/", URLUtil.getUnionUrl(url, "/jinyiyexing/1173348/"));
        assertEquals("http://www.exiaoshuo.com/jinyiyexing/jinyiyexing/1173348/", URLUtil.getUnionUrl(url, "jinyiyexing/1173348/"));
        assertEquals("http://www.exiaoshuo.com/jinyiyexing/1173348/", URLUtil.getUnionUrl(url, "1173348/"));
        assertEquals("http://www.exiaoshuo.com/jinyiyexing/1173348", URLUtil.getUnionUrl(url, "1173348"));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testGetUnionUrlTestNull(){
        URLUtil.getUnionUrl(url, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUnionUrlTestEmpty(){
        URLUtil.getUnionUrl(url, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUnionUrlTestBlank(){
        URLUtil.getUnionUrl(url, " ");
    }

}
