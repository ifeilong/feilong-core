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

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;

/**
 * The Class ConvertUtilToListEnumerationTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToListEnumerationTest{

    /**
     * To list.
     */
    @Test
    public void testToList(){
        List<String> list = newArrayList();
        Collections.addAll(list, "a", "b");

        Enumeration<String> enumeration = ConvertUtil.toEnumeration(list);
        assertThat(toList(enumeration), contains("a", "b"));
    }

    /**
     * Test to list null enumeration.
     */
    @Test
    public void testToListNullEnumeration(){
        assertEquals(emptyList(), toList((Enumeration<String>) null));
    }

}
