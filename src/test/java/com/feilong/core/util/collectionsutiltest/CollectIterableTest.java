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
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.TransformerUtils;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.User;

/**
 * The Class CollectionsUtilCollectIterableTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CollectIterableTest{

    /**
     * Test collect.
     */
    @Test
    public void testCollect(){
        List<String> list = toList("xinge", "feilong1", "feilong2", "feilong2");

        Transformer<String, Object> nullTransformer = TransformerUtils.nullTransformer();
        List<Object> collect = CollectionsUtil.collect(list, nullTransformer);

        Object[] objects = { null, null, null, null };
        assertThat(collect, hasItems(objects));
    }

    /**
     * Test collect null iterable.
     */
    @Test
    public void testCollectNullIterable(){
        assertEquals(null, CollectionsUtil.collect((List<Long>) null, TransformerUtils.stringValueTransformer()));
    }

    /**
     * Test collect empty iterable.
     */
    @Test
    public void testCollectEmptyIterable(){
        assertEquals(emptyList(), CollectionsUtil.collect(new ArrayList<Long>(), TransformerUtils.stringValueTransformer()));
    }

    /**
     * Test collect 2.
     */
    @Test
    public void testCollect2(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        Transformer<User, String> invokerTransformer = TransformerUtils.invokerTransformer("getName");
        assertThat(CollectionsUtil.collect(list, invokerTransformer), hasItems("张飞", "关羽", "刘备"));
    }

    /**
     * Test collect3.
     */
    @Test
    public void testCollect3(){
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25));

        List<String> collect1 = CollectionsUtil.collect(list, TransformerUtils.constantTransformer("jintian"));
        assertThat(collect1, hasItems("jintian", "jintian", "jintian"));
    }

}
