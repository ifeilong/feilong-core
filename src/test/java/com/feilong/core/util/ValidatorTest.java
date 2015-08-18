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
package com.feilong.core.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.User;

/**
 * The Class ValidatorTest.
 *
 * @author feilong
 * @version 1.0.7 2014年5月22日 下午1:25:51
 * @since 1.0.7
 */
public class ValidatorTest{

    /**
     * Test filter.
     */
    @Test
    public void testFilter(){
        int[] i = {};
        assertEquals(true, Validator.isNullOrEmpty(i));
    }

    /**
     * Name.
     */
    @Test
    public void name(){
        assertEquals(true, new Integer[][] {} instanceof Object[]);
        assertEquals(true, new int[][] {} instanceof Object[]);
    }

    /**
     * Test method for {@link com.feilong.core.util.Validator#isNullOrEmpty(java.lang.Object)}.
     */
    @Test
    public void testIsNullOrEmpty(){
        List<String> list = new ArrayList<String>();

        assertEquals(true, Validator.isNullOrEmpty(""));
        assertEquals(true, Validator.isNullOrEmpty("   "));
        assertEquals(true, Validator.isNullOrEmpty(null));
        assertEquals(true, Validator.isNullOrEmpty(list));
        assertEquals(true, Validator.isNullOrEmpty(new LinkedHashMap<String, String>()));

        // **********Array*********************************
        assertEquals(true, Validator.isNullOrEmpty(new String[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new Integer[] { 2 }));
        assertEquals(true, Validator.isNullOrEmpty(new Integer[][] {}));

        assertEquals(false, Validator.isNullOrEmpty(new Long[] { 2L }));
        assertEquals(true, Validator.isNullOrEmpty(new User[] {}));

        assertEquals(true, Validator.isNullOrEmpty(new int[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new int[] { 1, 2 }));

        assertEquals(true, Validator.isNullOrEmpty(new double[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new double[] { 1.2d }));

        assertEquals(true, Validator.isNullOrEmpty(new long[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new long[] { 200L }));

        assertEquals(true, Validator.isNullOrEmpty(new float[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new float[] { 5.8f }));

        assertEquals(true, Validator.isNullOrEmpty(new boolean[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new boolean[] { true }));

        assertEquals(true, Validator.isNullOrEmpty(new byte[] {}));

        assertEquals(true, Validator.isNullOrEmpty(new char[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new char[] { 'a' }));

        assertEquals(true, Validator.isNullOrEmpty(new short[] {}));
        assertEquals(false, Validator.isNullOrEmpty(new short[] { 5 }));

        assertEquals(true, Validator.isNullOrEmpty(list.iterator()));
        assertEquals(true, Validator.isNullOrEmpty(ConvertUtil.toEnumeration(list)));
        assertEquals(true, Validator.isNullOrEmpty(new Iterator<User>(){

            @Override
            public boolean hasNext(){
                return false;
            }

            @Override
            public User next(){
                return null;
            }

            @Override
            public void remove(){
            }
        }));
    }
}
