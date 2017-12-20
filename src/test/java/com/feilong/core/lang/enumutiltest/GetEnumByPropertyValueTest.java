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
package com.feilong.core.lang.enumutiltest;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.entity.HttpMethodTestType;
import com.feilong.core.lang.EnumUtil;

/**
 * The Class EnumUtilGetEnumByPropertyValueTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetEnumByPropertyValueTest{

    /**
     * Test get http method type null enum class.
     */
    @Test(expected = NullPointerException.class)
    public void testGetHttpMethodTypeNullEnumClass(){
        EnumUtil.getEnumByPropertyValue(null, "method", "aa");
    }

    /**
     * Test get http method type null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetHttpMethodTypeNullPropertyName(){
        EnumUtil.getEnumByPropertyValue(HttpMethodTestType.class, null, "aa");
    }

    /**
     * Test get http method type empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetHttpMethodTypeEmptyPropertyName(){
        EnumUtil.getEnumByPropertyValue(HttpMethodTestType.class, "", "aa");
    }

    /**
     * Test get http method type blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetHttpMethodTypeBlankPropertyName(){
        EnumUtil.getEnumByPropertyValue(HttpMethodTestType.class, " ", "aa");
    }

    /**
     * Test get http method type 4.
     */
    @Test(expected = BeanOperationException.class)
    public void testGetHttpMethodType4(){
        EnumUtil.getEnumByPropertyValue(HttpMethodTestType.class, "method2222", null);
    }
}
