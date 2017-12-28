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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URLUtil;

/**
 * The Class URLUtilToURLTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToURLTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ToURLTest.class);

    /**
     * Test to URL.
     */
    @Test
    public void testToURL(){
        String spec = "C:\\Users\\feilong\\feilong\\train\\新员工\\warmReminder\\20160704141057.html";
        LOGGER.debug("" + URLUtil.toURL(spec));
    }

    /**
     * Test to URL null.
     */
    @Test(expected = NullPointerException.class)
    public void testToURLNull(){
        URLUtil.toURL(null);
    }

    /**
     * Test to URL empty.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToURLEmpty(){
        URLUtil.toURL("");
    }

    /**
     * Test to URL empty 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToURLEmpty1(){
        URLUtil.toURL(" ");
    }
}
