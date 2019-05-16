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

import static com.feilong.core.util.MapUtil.newTreeMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

public class NewTreeMapTest{

    @Test
    public void test(){
        Map<String, String> newTreeMap = MapUtil.newTreeMap();
        newTreeMap.put("name", "feilong");
        newTreeMap.put("age", "18");
        newTreeMap.put("address", "shanghai");

        assertThat(newTreeMap.size(), is(3));

        assertThat(
                        newTreeMap,
                        allOf(//
                                        hasEntry("name", "feilong"),
                                        hasEntry("age", "18"),
                                        hasEntry("address", "shanghai")));
    }

    @Test
    public void testnewTreeMap2333(){
        Map<String, String> newTreeMap = newTreeMap();
        newTreeMap.put("name", "feilong");
        newTreeMap.put("age", "18");
        newTreeMap.put("address", "shanghai");
        assertThat(newTreeMap.size(), is(3));

        Map<String, String> newTreeMap1 = newTreeMap(newTreeMap);
        assertThat(
                        newTreeMap1,
                        allOf(//
                                        hasEntry("name", "feilong"),
                                        hasEntry("age", "18"),
                                        hasEntry("address", "shanghai")));
    }

    @Test(expected = NullPointerException.class)
    public void testnewTreeMapTest(){
        newTreeMap(null);
    }

}
