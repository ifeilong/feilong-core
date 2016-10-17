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
package com.feilong.tools.jsonlib;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.Person;

/**
 * The Class JsonUtilToArrayWithClassMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToArrayWithClassMapTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtilToArrayWithClassMapTest.class);

    /**
     * 把一个json数组串转换成实体数组,且数组元素的属性含有另外实例Bean void.
     */
    @Test
    public void toArray3(){
        String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("data", Person.class);

        MyBean[] objArr = JsonUtil.toArray(json, MyBean.class, classMap);
        LOGGER.debug(JsonUtil.format(objArr));
    }
}
