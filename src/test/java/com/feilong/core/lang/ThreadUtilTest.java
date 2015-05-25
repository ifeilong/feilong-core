/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ThreadUtil;

/**
 * The Class ThreadUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 17, 2011 2:02:13 AM
 */
public class ThreadUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ThreadUtilTest.class);

    /**
     * Test method for {@link com.feilong.core.lang.ThreadUtil#getCurrentMethodName(java.lang.Thread)}.
     */
    @Test
    public void testGetMethodName(){
        Thread currentThread = Thread.currentThread();
        log.info(ThreadUtil.getCurrentMethodName(currentThread));
    }

    /**
     * Test get method name1.
     */
    @Test
    public void testGetMethodName1(){
        log.info("1");
        testGetMethodName2();
    }

    /**
     * Test get method name2.
     */
    @Test
    public void testGetMethodName2(){
        log.info("2");
        testGetMethodName3();
    }

    /**
     * Test get method name3.
     */
    @Test
    public void testGetMethodName3(){
        log.info("3");
        testGetMethodName();
    }
}
