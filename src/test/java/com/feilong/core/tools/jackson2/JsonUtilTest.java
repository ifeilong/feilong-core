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
package com.feilong.core.tools.jackson2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.BaseJsonTest;
import com.feilong.test.User;

/**
 *
 * @author feilong
 * @version 1.2.2 2015年7月8日 上午1:26:34
 * @since 1.2.2
 */
public class JsonUtilTest extends BaseJsonTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilTest.class);

    /**
     * Testenclosing_type.
     */
    @Test
    public void testToJson(){
        User userForJsonTest = getUserForJsonTest();
        LOGGER.info("{} ", JsonUtil.toJson(userForJsonTest));
    }
}
