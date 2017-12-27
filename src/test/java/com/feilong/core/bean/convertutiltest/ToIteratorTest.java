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

import static com.feilong.core.bean.ConvertUtil.toIterator;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Test;

/**
 * The Class ConvertUtilToIteratorTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToIteratorTest{

    /**
     * Test to iterator enumeration.
     */
    @Test
    public void testToIteratorEnumeration(){
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");

        Iterator<String> iterator = toIterator(enumeration);
        assertThat(IteratorUtils.toList(iterator), contains("this", "is", "a", "test"));
    }

    /**
     * Test to iterator collection.
     */
    @Test
    public void testToIteratorCollection(){
        List<String> list = newArrayList();
        list.add("aaaa");
        list.add("nnnnn");

        Iterator<String> iterator = toIterator(list);
        assertThat(IteratorUtils.toList(iterator), contains("aaaa", "nnnnn"));
    }

    /**
     * Test to iterator string.
     */
    @Test
    public void testToIteratorString(){
        Iterator<String> iterator = toIterator("1,2");
        assertThat(IteratorUtils.toList(iterator), contains("1", "2"));
    }

    /**
     * Test to iterator array.
     */
    @Test
    public void testToIteratorArray(){
        Object[] array = { "5", 8 };

        Iterator<Object> iterator = toIterator(array);
        assertThat(IteratorUtils.toList(iterator), org.hamcrest.Matchers.<Object> contains("5", 8));
    }

    /**
     * Test to iterator primitive array.
     */
    @Test
    public void testToIteratorPrimitiveArray(){
        int[] i2 = { 1, 2 };
        Iterator<Integer> iterator = toIterator(i2);
        assertThat(IteratorUtils.toList(iterator), contains(1, 2));
    }

    /**
     * Test to iterator map.
     */
    @Test
    public void testToIteratorMap(){
        Map<String, String> map = toMap("a", "1", "b", "2");

        Iterator<String> iterator = toIterator(map);
        assertThat(IteratorUtils.toList(iterator), contains("1", "2"));
    }

    /**
     * Test to iterator null.
     */
    @Test
    public void testToIteratorNull(){
        assertEquals(null, toIterator(null));
    }

}
