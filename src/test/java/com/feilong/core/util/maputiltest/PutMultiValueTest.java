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
package com.feilong.core.util.maputiltest;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;

/**
 * The Class MapUtilPutMultiValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class PutMultiValueTest{

    /**
     * Test put multi value.
     */
    @Test
    public void testPutMultiValue(){
        Map<String, List<String>> mutiMap = newLinkedHashMap(2);
        MapUtil.putMultiValue(mutiMap, "name", "张飞");
        MapUtil.putMultiValue(mutiMap, "name", "关羽");
        MapUtil.putMultiValue(mutiMap, "age", "30");

        assertThat(mutiMap, allOf(hasEntry("name", toList("张飞", "关羽")), hasEntry("age", toList("30"))));
    }

    /**
     * Test put multi value null map.
     */
    @Test(expected = NullPointerException.class)
    public void testPutMultiValueNullMap(){
        MapUtil.putMultiValue(null, "name", "张飞");
    }
}
