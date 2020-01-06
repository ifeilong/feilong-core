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
import static com.feilong.core.bean.ToStringConfig.DEFAULT_CONNECTOR;
import static java.lang.System.lineSeparator;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class ToStringCollectionAndConnectorParameterizedTest.
 */
public class ToStringCollectionAndConnectorParameterizedTest
                extends Abstract2ParamsAndResultParameterizedTest<Collection<?>, String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index} ConvertUtil.toString({0},{1})= {2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    /**
     * Builds the.
     *
     * @return the object[][]
     */
    private static Object[][] build(){
        List<String> list = toList("feilong", "", "xinge");

        return new Object[][] {
                                { null, DEFAULT_CONNECTOR, EMPTY },
                                { toList(), DEFAULT_CONNECTOR, EMPTY },

                                //------

                                { list, DEFAULT_CONNECTOR, "feilong,,xinge" },

                                { toList("feilong", "xinge"), DEFAULT_CONNECTOR, "feilong,xinge" },
                                { toList("feilong", "", "xinge", null), DEFAULT_CONNECTOR, "feilong,,xinge," },

                                { toList("feilong", "xinge"), DEFAULT_CONNECTOR, "feilong,xinge" },
                                { toList("feilong", "", "xinge", null), DEFAULT_CONNECTOR, "feilong,,xinge," },

                                { list, "@", "feilong@@xinge" },
                                { toList("feilong", "", "xinge", null), "@", "feilong@@xinge@" },
                                {
                                  toList("飞龙", "小金", "四金", "金金金金"),
                                  lineSeparator(),
                                  "飞龙" + lineSeparator() + "小金" + lineSeparator() + "四金" + lineSeparator() + "金金金金" }, };
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals(expectedValue, ConvertUtil.toString(input1, input2));
    }

}
