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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.core.net.URIUtil;
import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

public class HasQueryTest extends Abstract1ParamAndResultParameterizedTest<String, Boolean>{

    @Parameters(name = "index:{index}:URIUtil.hasQueryString({0})={1}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray(null, false),
                        toArray("", false),
                        toArray(" ", false),
                        toArray("www.baidu.com", false),

                        toArray("www.baidu.com?", true),
                        toArray("www.baidu.com?a", true),
                        toArray("www.baidu.com?a=1", true)
        //,
        );
    }

    @Test
    public void test(){
        Boolean result = MethodUtil.invokeStaticMethod(URIUtil.class, "hasQueryString", input1);
        assertEquals(expectedValue, result);
    }

}
