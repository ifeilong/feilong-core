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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

/**
 * The Class GetPropertyValueListBeanTest.
 */
public class GetPropertyValueListBeanTest{

    /**
     * Map.
     */
    @Test
    public void testGetPropertyValueListMap(){
        List<Map<String, String>> list = newArrayList();
        list.add(toMap("key", "value1"));
        list.add(toMap("key", "value2"));
        list.add(toMap("key", "value3"));

        List<String> resultList = CollectionsUtil.getPropertyValueList(list, "(key)");
        assertThat(resultList, contains("value1", "value2", "value3"));
    }

    /**
     * 集合.
     */
    @Test
    public void testGetPropertyValueListList(){
        List<List<String>> list = newArrayList();
        list.add(toList("小明", "18"));
        list.add(toList("小宏", "18"));
        list.add(toList("小振", "18"));

        List<String> resultList = CollectionsUtil.getPropertyValueList(list, "[0]");
        assertThat(resultList, contains("小明", "小宏", "小振"));
    }

    //---------------------------------------------------------------
    /**
     * 数组
     */
    @Test
    public void testGetPropertyValueListArray(){
        List<String[]> list = newArrayList();
        list.add(toArray("三国", "水浒"));
        list.add(toArray("西游", "金瓶梅"));

        List<String> resultList = CollectionsUtil.getPropertyValueList(list, "[0]");
        assertThat(resultList, contains("三国", "西游"));
    }

    //
    //    /**
    //     * 级联对象.
    //     */
    //    @Test
    //    public void testGetPropertyValueListCascade(){
    //        List<Integer> list = CollectionsUtil.getPropertyValueList(userList, "userInfo.age");
    //        assertThat(list, contains(28, null));
    //    }

}
