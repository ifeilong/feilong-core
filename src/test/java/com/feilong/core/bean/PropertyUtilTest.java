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
package com.feilong.core.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.test.User;

/**
 *
 * @author feilong
 * @version 1.2.2 2015年7月14日 下午10:07:59
 * @since 1.2.2
 */
public class PropertyUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtilTest.class);

    /**
     * Describe.
     */
    @Test
    public void describe(){
        User a = new User();
        a.setId(5L);
        Date now = new Date();
        a.setDate(now);

        List list = new ArrayList();
        list.add(a);

        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe(a)));
        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe(new BigDecimal(5L))));
        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe("123456")));
        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe(list)));
        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe(new HashMap())));
        LOGGER.info("map:{}", JsonUtil.format(PropertyUtil.describe(User.class)));
    }
}
