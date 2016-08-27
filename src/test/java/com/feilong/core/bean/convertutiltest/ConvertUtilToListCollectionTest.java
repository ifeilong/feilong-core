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

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toList;

public class ConvertUtilToListCollectionTest{

    @Test
    public void testToList(){
        Set<String> set = new LinkedHashSet<>();
        Collections.addAll(set, "a", "a", "b", "b");

        assertThat(toList(set), contains("a", "b"));
    }

    /**
     * To list3.
     */
    @Test
    public void testToList3(){
        assertThat(toList(toList("a", "a", "b", "b")), contains("a", "a", "b", "b"));
    }

    @Test
    public void testToListNullCollection(){
        assertEquals(emptyList(), toList((Set<String>) null));
    }

    @Test
    public void testToListEmptyCollection(){
        assertEquals(emptyList(), toList(new ArrayList<String>()));
    }
}
