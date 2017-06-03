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

import static com.feilong.core.bean.ConvertUtil.toIterator;
import static com.feilong.core.bean.ConvertUtil.toList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.TransformerUtils;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

/**
 * The Class CollectionsUtilCollectIteratorTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CollectIteratorTest{

    /**
     * Test collect iterator.
     */
    @Test
    public void testCollectIterator(){
        List<String> list = toList("xinge", "feilong1", "feilong2", "feilong2");

        Transformer<String, Object> nullTransformer = TransformerUtils.nullTransformer();
        List<Object> collect = CollectionsUtil.collect(list.iterator(), nullTransformer);

        Object[] objects = { null, null, null, null };
        assertThat(collect, hasItems(objects));
    }

    /**
     * Test collect null iterator.
     */
    @Test
    public void testCollectNullIterator(){
        assertEquals(null, CollectionsUtil.collect((Iterator<Long>) null, TransformerUtils.stringValueTransformer()));
    }

    /**
     * Test collect empty iterator.
     */
    @Test
    public void testCollectEmptyIterator(){
        assertEquals(emptyList(), CollectionsUtil.collect((new ArrayList<Long>()).iterator(), TransformerUtils.stringValueTransformer()));
    }

    /**
     * Test collect null transformer.
     */
    @Test
    public void testCollectNullTransformer(){
        assertEquals(emptyList(), CollectionsUtil.collect(toIterator("1,2,3"), null));
    }

}
