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
package com.feilong.core.util.comparator.beancomparatorutiltest;

import org.junit.Test;

import com.feilong.core.util.comparator.BeanComparatorUtil;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public class PropertyComparatorTest{

    //--------
    @Test(expected = NullPointerException.class)
    public void testPropertyNameAndOrdersNull(){
        BeanComparatorUtil.propertyComparator(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrdersEmpty(){
        BeanComparatorUtil.propertyComparator("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrdersBlank(){
        BeanComparatorUtil.propertyComparator(" ");
    }
}
