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
package com.feilong.core.lang;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
@RunWith(Parameterized.class)
public class StringReplaceParameterizedTest{

    //必须是 public 访问修饰符

    /** The f input. */
    @Parameter(value = 0) // first data value (0) is default
    public String text;

    @Parameter(value = 1)
    public String searchString;

    @Parameter(value = 2)
    public String replacement;

    /** The f expected. */
    @Parameter(value = 3)
    public String expectedValue;

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

    @Test
    public void replace(){
        assertEquals(expectedValue, StringUtil.replace(text, searchString, replacement));
    }
}
