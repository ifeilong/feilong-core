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
package com.feilong.core.bean.convertutiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toStrings;
import static org.junit.Assert.assertArrayEquals;

import java.net.URL;

import org.junit.Test;

import com.feilong.core.net.URLUtil;

/**
 * The Class ConvertUtilToStringsTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringsTest{

    /**
     * Test to strings string.
     */
    @Test
    public void testToStringsString(){
        assertArrayEquals(new String[] { "5", "4", "8", "2", "8", "9", "5", "3", "a" }, toStrings("{5,4, 8,2;8 9_5@3`a}"));
    }

    /**
     * Test to strings integers.
     */
    @Test
    public void testToStringsIntegers(){
        assertArrayEquals(new String[] { "1", "2", "5" }, toStrings(new Integer[] { 1, 2, 5 }));
    }

    /**
     * Test to strings UR ls.
     */
    @Test
    public void testToStringsURLs(){
        URL[] urls = {
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing0/"),
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing1/"),
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing2/"),
                       null };

        assertArrayEquals(toArray(
                        "http://www.exiaoshuo.com/jinyiyexing0/",
                        "http://www.exiaoshuo.com/jinyiyexing1/",
                        "http://www.exiaoshuo.com/jinyiyexing2/",
                        null), toStrings(urls));

    }

    /**
     * Test to strings null.
     */
    @Test
    public void testToStringsNull(){
        assertArrayEquals(null, toStrings(null));
    }

    /**
     * Test to strings empty UR ls.
     */
    @Test
    public void testToStringsEmptyURLs(){
        URL[] urls1 = {};
        assertArrayEquals(new String[] {}, toStrings(urls1));
    }

}
