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
package com.feilong.core.lang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class ClassUtilTest.
 *
 * @author feilong
 * @version 1.0.8 2014年7月21日 下午3:01:52
 * @since 1.0.8
 */
public class ClassUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtilTest.class);

    /**
     * Test to class.
     */
    @Test
    public void testToClass(){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("" + JsonUtil.format(ClassUtil.toClass("a", "a")));
            LOGGER.info("" + JsonUtil.format(ClassUtil.toClass(1, true)));
        }
    }

    /**
     * Test is interface.
     */
    @Test
    public void testIsInterface(){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("" + ClassUtil.isInterface(this.getClass()));
            LOGGER.info("" + ClassUtil.isInterface(DatePattern.class));
        }
    }

    /**
     * Test get class info map for LOGGER.
     */
    @Test
    public void testGetClassInfoMapForLog(){
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(ClassUtil.getClassInfoMapForLog(this.getClass())));
            LOGGER.debug(JsonUtil.format(ClassUtil.getClassInfoMapForLog(DatePattern.class)));
        }
    }
}
