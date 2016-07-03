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

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class SystemUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.7
 */
public class SystemUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtilTest.class);

    /**
     * Nano time.
     */
    @Test
    public void nanoTime(){
        LOGGER.debug("" + System.nanoTime()); //返回最准确的可用系统计时器的当前值,以毫微秒为单位.
        LOGGER.debug("" + System.currentTimeMillis()); //返回以毫秒为单位的当前时间.注意,当返回值的时间单位是毫秒时,值的粒度取决于底层操作系统,并且粒度可能更大.例如,许多操作系统以几十毫秒为单位测量时间. 
    }

    /**
     * Path.
     */
    @Test
    public void path(){
        String path = System.getenv("Path");
        String[] strings = path.split(";");
        Arrays.sort(strings);

        LOGGER.debug(JsonUtil.format(strings));
    }

    /**
     * Test get env map for log.
     */
    @Test
    public void testGetEnvMap(){
        LOGGER.debug(JsonUtil.format(SystemUtil.getEnvMap()));
    }

    /**
     * Test get properties map for log.
     */
    @Test
    public void testGetPropertiesMap(){
        LOGGER.debug(JsonUtil.format(SystemUtil.getPropertiesMap()));
    }
}
