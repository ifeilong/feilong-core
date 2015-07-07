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

import com.feilong.core.bean.BeanUtilException;
import com.feilong.core.net.HttpMethodType;

/**
 * The Class EnumUtilTest.
 * 
 * @author feilong
 * @version 1.0.6 2014年5月12日 下午11:00:42
 * @since 1.0.6
 */
public class EnumUtilTest{

    /**
     * Test method for {@link com.feilong.core.lang.EnumUtil#getEnumByPropertyValue(Class, String, Object)}.
     */
    @Test
    public final void testGetEnum(){
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "get"));
    }

    /**
     * Test get http method type.
     */
    @Test
    public final void testGetHttpMethodType(){
        assertEquals(HttpMethodType.POST, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "post"));
        assertEquals(HttpMethodType.POST, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "pOst"));
        assertEquals(HttpMethodType.POST, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "POST"));
        assertEquals(HttpMethodType.POST, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "posT"));
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "get"));
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "gEt"));
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "geT"));
        assertEquals(HttpMethodType.GET, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "GET"));

    }

    /**
     * Test get http method type1.
     */
    @Test(expected = NoSuchFieldException.class)
    public final void testGetHttpMethodType1(){
        assertEquals(null, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", "post111"));
    }

    /**
     * Test get http method type2.
     */
    @Test(expected = BeanUtilException.class)
    public final void testGetHttpMethodType2(){
        assertEquals(null, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", ""));
    }

    /**
     * Test get http method type3.
     */
    @Test(expected = BeanUtilException.class)
    public final void testGetHttpMethodType3(){
        assertEquals(null, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method", null));
    }

    /**
     * Test get http method type4.
     */
    @Test(expected = BeanUtilException.class)
    public final void testGetHttpMethodType4(){
        assertEquals(null, EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodType.class, "method2222", null));
    }
}
