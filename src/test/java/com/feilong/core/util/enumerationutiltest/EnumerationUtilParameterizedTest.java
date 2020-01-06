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
package com.feilong.core.util.enumerationutiltest;

import static org.junit.Assert.assertEquals;

import java.util.Enumeration;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.util.EnumerationUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toEnumeration;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class EnumerationUtilParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.8
 */
public class EnumerationUtilParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Enumeration<Object>, Object, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:EnumerationUtil.contains({0}, {1})={2}")
    public static Iterable<Object[]> data(){
        return toList(
                        toArray(null, "a", false),
                        toArray(toEnumeration(toList("4", "5")), "a", false),
                        toArray(toEnumeration(toList("4", "5")), "4", true),
                        toArray(toEnumeration(toList("4", "5", "")), "", true),
                        toArray(toEnumeration(toList("4", "5", "", null)), null, true)
        //
        );
    }

    /**
     * Test contains.
     */
    @Test
    public void testContains(){
        assertEquals(expectedValue, EnumerationUtil.contains(input1, input2));
    }
}
