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
package com.feilong.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toEnumeration;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class EnumerationUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
public class EnumerationUtilTest{

    /**
     * Test contains.
     */
    @Test
    public final void testContains(){
        assertEquals(false, EnumerationUtil.contains(null, "a"));
    }

    /**
     * Test contains 1.
     */
    @Test
    public final void testContains1(){
        assertEquals(false, EnumerationUtil.contains(toEnumeration(toList("4", "5")), "a"));
        assertEquals(true, EnumerationUtil.contains(toEnumeration(toList("4", "5")), "4"));
        assertEquals(true, EnumerationUtil.contains(toEnumeration(toList("4", "5", "")), ""));
        assertEquals(true, EnumerationUtil.contains(toEnumeration(toList("4", "5", "", null)), null));
    }
}
