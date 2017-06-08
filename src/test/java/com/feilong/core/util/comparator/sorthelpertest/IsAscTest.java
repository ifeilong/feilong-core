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
package com.feilong.core.util.comparator.sorthelpertest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.feilong.core.util.comparator.SortHelper;

/**
 * The Class IsAscTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public class IsAscTest{

    /**
     * Test property name and order array order desc ignore case.
     */
    @Test
    public void testPropertyNameAndOrderArrayOrderDescIgnoreCase(){
        assertEquals(false, SortHelper.isAsc(toArray("name", "desc")));
        assertEquals(false, SortHelper.isAsc(toArray("name", "dEsc")));
        assertEquals(false, SortHelper.isAsc(toArray("name", "deSc")));
    }

    /**
     * Test property name and order array order asc ignore case.
     */
    @Test
    public void testPropertyNameAndOrderArrayOrderAscIgnoreCase(){
        assertEquals(true, SortHelper.isAsc(toArray("name", "asC")));
        assertEquals(true, SortHelper.isAsc(toArray("name", "AsC")));
        assertEquals(true, SortHelper.isAsc(toArray("name", "ASC")));
    }

    /**
     * Test property name and order array order asc.
     */
    @Test
    public void testPropertyNameAndOrderArrayOrderAsc(){
        assertEquals(true, SortHelper.isAsc(toArray("name", "asc")));
    }

    /**
     * Test property name and order array order null.
     */
    @Test
    public void testPropertyNameAndOrderArrayOrderNull(){
        assertEquals(true, SortHelper.isAsc(toArray("name", null)));
    }

    /**
     * Test property name and order array is null.
     */
    //------
    @Test(expected = NullPointerException.class)
    public void testPropertyNameAndOrderArrayIsNull(){
        SortHelper.isAsc(null);
    }

    /**
     * Test property name and order array is empty.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrderArrayIsEmpty(){
        SortHelper.isAsc(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    /**
     * Test property name and order array length not 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrderArrayLengthNot2(){
        SortHelper.isAsc(toArray("name"));
    }

    //-------

    /**
     * Test property name and order array order value illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrderArrayOrderValueIllegal(){
        SortHelper.isAsc(toArray("name", "aaaa"));
    }

    /**
     * Test property name and order array order value empty illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrderArrayOrderValueEmptyIllegal(){
        SortHelper.isAsc(toArray("name", ""));
    }

    /**
     * Test property name and order array order value blank illegal.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testPropertyNameAndOrderArrayOrderValueBlankIllegal(){
        SortHelper.isAsc(toArray("name", " "));
    }

}
