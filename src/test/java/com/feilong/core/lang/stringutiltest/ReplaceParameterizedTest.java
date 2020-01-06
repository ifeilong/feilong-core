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
package com.feilong.core.lang.stringutiltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.StringUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class StringReplaceParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class ReplaceParameterizedTest extends Abstract3ParamsAndResultParameterizedTest<String, String, String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:StringUtil.replace({0}, {1}, {2})={3}")
    public static Iterable<Object[]> data(){
        return toList(
                        toArray("黑色/黄色/蓝色", "/", "_", "黑色_黄色_蓝色"),
                        toArray(null, "/", "_", null),
                        toArray("黑色/黄色/蓝色", "/", null, "黑色/黄色/蓝色"),
                        toArray("黑色/黄色/蓝色", "色", "color", "黑color/黄color/蓝color"),
                        toArray("黑色/黄色/蓝色", null, "_", "黑色/黄色/蓝色"),
                        toArray("SH1265,SH5951", "([a-zA-Z]+[0-9]+)", "'$1'", "SH1265,SH5951"),
                        new Object[] { "黑色/黄色/蓝色", "/", null, "黑色/黄色/蓝色" });

    }

    /**
     * Replace.
     */
    @Test
    public void replace(){
        assertEquals(expectedValue, StringUtil.replace(input1, input2, input3));
    }
}
