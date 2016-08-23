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

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.SystemUtils.LINE_SEPARATOR;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.ToStringConfig;
import com.feilong.test.AbstractTwoParamsAndOneResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toList;

public class ConvertUtilToStringCollectionToStringConfigParameterizedTest
                extends AbstractTwoParamsAndOneResultParameterizedTest<Collection<?>, ToStringConfig, String>{

    @Parameters(name = "index:{index}")
    public static Iterable<Object[]> data(){

        ToStringConfig toStringConfig = new ToStringConfig(",", false);
        List<String> list = toList("feilong", "", "xinge");
        Object[][] objects = new Object[][] { //
                                              { null, toStringConfig, EMPTY },
                                              { toList(), toStringConfig, EMPTY },

                                              { list, toStringConfig, "feilong,xinge" },
                                              { list, new ToStringConfig("@"), "feilong@@xinge" },
                                              { toList("feilong", "", "xinge", null), new ToStringConfig("@"), "feilong@@xinge@" },
                                              {
                                                toList("飞龙", "小金", "四金", "金金金金"),
                                                new ToStringConfig(LINE_SEPARATOR),
                                                "飞龙" + LINE_SEPARATOR + "小金" + LINE_SEPARATOR + "四金" + LINE_SEPARATOR + "金金金金" },
                //
        };
        return toList(objects);
    }

    @Test
    public void testToString(){
        assertEquals(expectedValue, ConvertUtil.toString(input1, input2));
    }

}
