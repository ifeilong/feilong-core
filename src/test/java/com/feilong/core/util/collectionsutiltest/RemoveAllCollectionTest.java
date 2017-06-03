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

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilRemoveAllCollectionTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class RemoveAllCollectionTest{

    /**
     * Test remove all collection.
     */
    @Test
    public void testRemoveAllCollection(){
        List<String> list = toList("xinge", "feilong1", "feilong2", "feilong2");
        List<String> removeList = CollectionsUtil.removeAll(list, toList("feilong2", "feilong1"));

        assertThat(removeList, contains("xinge"));
        assertThat(list, contains("xinge", "feilong1", "feilong2", "feilong2"));
    }

    /**
     * Test remove all collection null object collection.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllCollectionNullObjectCollection(){
        CollectionsUtil.removeAll(null, toList("feilong2"));
    }

    /**
     * Test remove all collection null remove collection.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllCollectionNullRemoveCollection(){
        CollectionsUtil.removeAll(toList("feilong2"), null);
    }
}
