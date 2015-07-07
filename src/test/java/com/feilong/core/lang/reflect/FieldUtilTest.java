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
package com.feilong.core.lang.reflect;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;

/**
 * The Class FieldUtilTest.
 * 
 * @author feilong
 * @version 1.0.7 2014年7月15日 下午1:23:59
 * @since 1.0.7
 */
public class FieldUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtilTest.class);

    /**
     * Test get static property.
     */
    @Test
    public void testGetStaticProperty(){
        LOGGER.info("" + FieldUtil.getStaticProperty("com.feilong.core.io.ImageType", "JPG"));
        LOGGER.info("" + FieldUtil.getStaticProperty("com.feilong.core.io.IOConstants", "GB"));
    }

    /**
     * Creates the payment form.
     * 
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    @Test
    public final void testGetFieldValueMap() throws IllegalArgumentException,IllegalAccessException{
        User user = new User(12L);
        LOGGER.info(JsonUtil.format(FieldUtil.getFieldValueMap(user)));
    }
}
