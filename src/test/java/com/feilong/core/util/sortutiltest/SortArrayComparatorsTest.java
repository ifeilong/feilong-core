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
package com.feilong.core.util.sortutiltest;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.apache.commons.collections4.ComparatorUtils;
import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.util.SortUtil.sortArray;

/**
 * The Class SortUtilSortArrayComparatorsTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortArrayComparatorsTest{

    /**
     * Test sort array comparators test.
     */
    @Test
    public void testSortArrayComparatorsTest(){
        String[] arrays = { "almn", "fba", "cba" };

        Comparator<String> comparator = new Comparator<String>(){

            @Override
            public int compare(String s1,String s2){
                Integer length = s1.length();
                Integer length2 = s2.length();

                //先判断长度,长度比较
                int compareTo = length.compareTo(length2);

                //如果长度相等,那么比较自己本身的顺序
                if (0 == compareTo){
                    compareTo = s1.compareTo(s2);
                }
                return compareTo;
            }
        };
        sortArray(arrays, comparator);

        assertArrayEquals(toArray("cba", "fba", "almn"), arrays);
    }

    /**
     * Test sort array null array.
     */
    @Test
    public void testSortArrayNullArray(){
        String[] arrays = null;
        assertEquals(EMPTY_STRING_ARRAY, sortArray(arrays, ComparatorUtils.<String> naturalComparator()));
    }

    /**
     * Test sort array null comparator.
     */
    @Test
    public void testSortArrayNullComparator(){
        assertEquals(toArray(1, 2, 3), sortArray(toArray(1, 2, 3), null));
    }

    /**
     * Test sort array empty comparator.
     */
    @Test
    public void testSortArrayEmptyComparator(){
        assertEquals(toArray(1, 2, 3), sortArray(toArray(1, 2, 3), ConvertUtil.<Comparator> toArray()));
    }
}
