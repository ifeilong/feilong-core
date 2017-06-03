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
package com.feilong.core.bean.convertutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * The Class ConvertUtilToMapCollectionTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapCollectionTest{

    //******com.feilong.core.bean.ConvertUtil.toMap(Collection<SimpleEntry<String, String>>)**********************
    /**
     * Test to map3.
     */
    @Test
    public void testToMap3(){
        Map<String, String> map = toMap(toList(//
                        Pair.of("张飞", "丈八蛇矛"),
                        Pair.of("关羽", "青龙偃月刀"),
                        Pair.of("赵云", "龙胆枪"),
                        Pair.of("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map 33.
     */
    @Test
    public void testToMap33(){
        Map<String, String> map = toMap(toList(
                        new SimpleEntry<>("张飞", "丈八蛇矛"),
                        new SimpleEntry<>("关羽", "青龙偃月刀"),
                        new SimpleEntry<>("赵云", "龙胆枪"),
                        new SimpleEntry<>("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map null collection.
     */
    @Test
    public void testToMapNullCollection(){
        assertEquals(emptyMap(), toMap((List<SimpleEntry<String, String>>) null));
    }

    /**
     * Test to map collection null element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToMapCollectionNullElement(){
        toMap(toList(new SimpleEntry<>("张飞", "丈八蛇矛"), null, new SimpleEntry<>("赵云", "龙胆枪"), new SimpleEntry<>("刘备", "双股剑")));
    }
}
