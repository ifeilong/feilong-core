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
package com.feilong.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.feilong.core.bean.BeanUtilException;
import com.feilong.core.lang.EnumUtil;

/**
 * The Class HttpMethodTypeTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.6
 */
public class HttpMethodTypeTest{

    /**
     * Name.
     */
    @Test
    public void name(){
        assertEquals(HttpMethodType.GET, HttpMethodType.valueOf("GET"));
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "get"));
        assertEquals(HttpMethodType.GET, HttpMethodType.getByMethodValueIgnoreCase("Get"));
    }

    /**
     * Name2.
     */
    @Test(expected = BeanUtilException.class)
    public void name2(){
        assertNotEquals(HttpMethodType.GET, HttpMethodType.getByMethodValueIgnoreCase("Get1"));
    }

    /**
     * Name1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void name1(){
        assertEquals(HttpMethodType.GET, HttpMethodType.valueOf("GeT"));
    }
}
