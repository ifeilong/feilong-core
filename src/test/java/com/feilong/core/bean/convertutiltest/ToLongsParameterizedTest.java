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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toLongs;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

/**
 * The Class ConvertUtilToLongsParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToLongsParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Object, Long[]>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toLongs({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    /**
     * @return
     * @since 1.10.3
     */
    private static Object[][] build(){
        return new Object[][] {
                                { null, null },

                                { "1,2,3", new Long[] { 1L, 2L, 3L } },
                                { "{1,2,3}", new Long[] { 1L, 2L, 3L } },
                                { "{ 1 ,2,3}", new Long[] { 1L, 2L, 3L } },
                                { "1,2, 3", new Long[] { 1L, 2L, 3L } },
                                { "1,2 , 3", new Long[] { 1L, 2L, 3L } },
                                { new String[] { "1", "2", "3" }, new Long[] { 1L, 2L, 3L } },
                                { toList("1", "2", "3"), new Long[] { 1L, 2L, 3L } },
                                { toList("1", "2", " 3"), new Long[] { 1L, 2L, 3L } },

                                { toArray(true, false, false), new Long[] { 1L, 0L, 0L } },
                                { new String[] { "1", null, "2", "3" }, new Long[] {} }, };
    }

    /**
     * Test to longs.
     */
    @Test
    public void testToLongs(){
        assertArrayEquals(expectedValue, toLongs(input1));
    }

}
