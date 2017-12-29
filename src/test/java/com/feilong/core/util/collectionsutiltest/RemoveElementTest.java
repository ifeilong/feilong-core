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
package com.feilong.core.util.collectionsutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

public class RemoveElementTest{

    /**
     * Test remove.
     */
    @Test
    public void testRemove1(){
        List<String> list = toList("xinge", "feilong1", "feilong2", "feilong3", "feilong2");

        List<String> removeList = CollectionsUtil.remove(list, "feilong2", "feilong3");

        assertThat(removeList, hasItems("xinge", "feilong1"));
        assertThat(list, hasItems("xinge", "feilong1", "feilong2", "feilong3"));
    }

    @Test
    public void testRemove(){

        List<String> list = new ArrayList<String>(){

            private static final long serialVersionUID = -9002323146501447769L;

            {
                add("xinge");
                add("feilong1");
                add("feilong2");
                add("feilong2");
            }
        };

        List<String> removeList = CollectionsUtil.remove(list, "feilong2");
        assertThat(removeList, hasItems("xinge", "feilong1"));
        assertThat(list, hasItems("xinge", "feilong1", "feilong2", "feilong2"));
    }

    /**
     * Test remove element 1.
     */
    @Test
    public void testRemoveElement1(){
        assertThat(
                        CollectionsUtil.remove(toList("xinge", "feilong1", "feilong2", "feilong2"), null),
                        contains("xinge", "feilong1", "feilong2", "feilong2"));
    }

    /**
     * Test remove element.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveElement(){
        CollectionsUtil.remove(null, "刘备");
    }

}
