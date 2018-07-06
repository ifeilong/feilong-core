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
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.feilong.core.util.comparator.SortHelper;

/**
 * The Class ParsePropertyNameAndOrderTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.2
 */
public class ParsePropertyNameAndOrderTest{

    /**
     * Test parse property name and order no.
     */
    @Test
    public void testParsePropertyNameAndOrderNo(){
        assertArrayEquals(toArray("name", null), SortHelper.parsePropertyNameAndOrder("name"));
        assertArrayEquals(toArray("name", null), SortHelper.parsePropertyNameAndOrder("name "));
        assertArrayEquals(toArray("name", null), SortHelper.parsePropertyNameAndOrder(" name "));
    }

    /**
     * Test parse property name and order asc.
     */
    @Test
    public void testParsePropertyNameAndOrderAsc(){
        assertArrayEquals(toArray("name", "asc"), SortHelper.parsePropertyNameAndOrder("name asc"));
        assertArrayEquals(toArray("name", "aSc"), SortHelper.parsePropertyNameAndOrder("name aSc"));
        assertArrayEquals(toArray("name", "aSc"), SortHelper.parsePropertyNameAndOrder("name   aSc"));
    }

    /**
     * Test parse property name and order desc.
     */
    @Test
    public void testParsePropertyNameAndOrderDesc(){
        assertArrayEquals(toArray("name", "desc"), SortHelper.parsePropertyNameAndOrder("name desc"));
        assertArrayEquals(toArray("name", "deSc"), SortHelper.parsePropertyNameAndOrder("name deSc"));
    }
    //------------------------------------------------------------

    /**
     * Test parse property name and order null.
     */
    @Test(expected = NullPointerException.class)
    public void testParsePropertyNameAndOrderNull(){
        SortHelper.parsePropertyNameAndOrder(null);
    }

    /**
     * Test parse property name and order empty.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParsePropertyNameAndOrderEmpty(){
        SortHelper.parsePropertyNameAndOrder("");
    }

    /**
     * Test parse property name and order blank.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testParsePropertyNameAndOrderBlank(){
        SortHelper.parsePropertyNameAndOrder(" ");
    }

    //------------------------------------------------------------

    /**
     * Test parse property name and order array length more than 2.
     */
    //@Test()
    @Test(expected = IllegalArgumentException.class)
    public void testParsePropertyNameAndOrderArrayLengthMoreThan2(){
        SortHelper.parsePropertyNameAndOrder("name desc desc");
    }

    /**
     * Test parse property name and order array length eqals 2 but not asc.
     */
    @Test(expected = IllegalArgumentException.class)
    //@Test
    public void testParsePropertyNameAndOrderArrayLengthEqals2ButNotAsc(){
        SortHelper.parsePropertyNameAndOrder("name des");
    }
}
