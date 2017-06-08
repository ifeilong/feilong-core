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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.core.util.comparator.PropertyComparator;

/**
 * The Class ChainedComparatorTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public class ChainedComparatorTest{

    /**
     * Test property name and orders.
     */
    @Test
    public void testPropertyNameAndOrders(){
        Comparator<Object> chainedComparator = BeanComparatorUtil.chainedComparator("name");
        assertTrue(ClassUtil.isInstance(chainedComparator, PropertyComparator.class));
    }

    /**
     * Test property name and orders null element.
     */
    //--------
    @Test(expected = NullPointerException.class)
    public void testPropertyNameAndOrdersNullElement(){
        BeanComparatorUtil.chainedComparator(null, "name");
    }

    /**
     * Test property name and orders empty element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrdersEmptyElement(){
        BeanComparatorUtil.chainedComparator("name", "");
    }

    /**
     * Test property name and orders blank element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrdersBlankElement(){
        BeanComparatorUtil.chainedComparator("name", " ");
    }

    /**
     * Test property name and orders null.
     */
    //------
    @Test(expected = NullPointerException.class)
    public void testPropertyNameAndOrdersNull(){
        BeanComparatorUtil.chainedComparator(null);
    }

    /**
     * Test property name and orders null 1.
     */
    @Test(expected = NullPointerException.class)
    public void testPropertyNameAndOrdersNull1(){
        BeanComparatorUtil.chainedComparator(toArray((String) null));
    }

    /**
     * Test property name and orders empty.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrdersEmpty(){
        BeanComparatorUtil.chainedComparator(ConvertUtil.<String> toArray());
    }
}
