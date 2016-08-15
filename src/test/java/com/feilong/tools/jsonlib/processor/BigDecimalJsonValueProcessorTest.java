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
package com.feilong.tools.jsonlib.processor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonFormatConfig;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.NumberPattern.TWO_DECIMAL_POINTS;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;

import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
public class BigDecimalJsonValueProcessorTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(BigDecimalJsonValueProcessorTest.class);

    @Test
    public void testSensitiveWordsJsonValueProcessor(){
        User user = new User("feilong1", 24);
        user.setMoney(toBigDecimal("99999999.00"));

        JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
        jsonFormatConfig.setIncludes("money");

        LOGGER.debug(JsonUtil.format(user, jsonFormatConfig));
    }

    @Test
    public void testSensitiveWordsJsonValueProcessor2(){
        User user = new User("feilong1", 24);
        user.setMoney(toBigDecimal("99999999.00"));

        Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = new HashMap<>();
        propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor(TWO_DECIMAL_POINTS));

        JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
        jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
        jsonFormatConfig.setIncludes("name", "age", "money");

        LOGGER.debug(JsonUtil.format(user, jsonFormatConfig));
    }
}
