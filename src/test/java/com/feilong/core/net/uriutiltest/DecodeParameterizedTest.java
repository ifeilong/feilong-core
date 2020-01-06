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
package com.feilong.core.net.uriutiltest;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.net.URIUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

public class DecodeParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: URIUtil.decode({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    private static Object[][] build(){
        return new Object[][] {
                                { "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", UTF8, "飞天奔月" },
                                { null, UTF8, EMPTY },
                                { EMPTY, UTF8, EMPTY },
                                { SPACE, UTF8, SPACE },

                                { "+", UTF8, SPACE },

                                { "%24", UTF8, "$" },
                                { "%26", UTF8, "&" },
                                { "%2B", UTF8, "+" },
                                { "%2C", UTF8, "," },
                                { "%3A", UTF8, ":" },
                                { "%3B", UTF8, ";" },
                                { "%3D", UTF8, "=" },
                                { "%3F", UTF8, "?", },
                                { "%40", UTF8, "@", },

                                { "fei+long", UTF8, "fei long", },

                                { "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", null, "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88" },
                                { "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", "", "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88" },
                                { "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", " ", "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88" },
                //
        };
    }

    @Test
    public void test(){
        assertEquals(expectedValue, URIUtil.decode(input1, input2));
    }

}
