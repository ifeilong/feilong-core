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
package com.feilong.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class FeiLongVersionTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class FeiLongVersionTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FeiLongVersionTest.class);

    /**
     * Test method for {@link com.feilong.core.FeiLongVersion#getVersion()}.
     */
    @Test
    public void testGetVersion(){
        LOGGER.info(FeiLongVersion.getVersion());
        //LOGGER.info(SpringVersion.getVersion());

        Package pkg = FeiLongVersion.class.getPackage();
        LOGGER.info(JsonUtil.format(pkg));
    }
}
