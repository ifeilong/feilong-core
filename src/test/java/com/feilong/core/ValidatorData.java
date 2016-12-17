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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.feilong.store.member.User;
import com.feilong.test.TestUtil;

import static com.feilong.core.bean.ConvertUtil.toEnumeration;

/**
 * The Class ValidatorData.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
public class ValidatorData{

    /**
     * Builds the data.
     *
     * @return the iterable
     */
    public static Iterable<Object[]> buildData(){
        Object[] nullOrEmptyElement = {
                                        null,
                                        "", //
                                        "   ",

                                        new StringBuffer(),
                                        new StringBuffer(""),
                                        new StringBuffer(" "),
                                        //new StringBuffer(null), //NPE

                                        new StringBuilder(),
                                        new StringBuilder(""),
                                        new StringBuilder(" "),
                                        // new StringBuilder(null),//NPE

                                        new ArrayList<String>(),
                                        new LinkedHashMap<String, String>(),
                                        // **********Array*********************************
                                        new String[] {},
                                        new Integer[][] {},
                                        new User[] {},

                                        new double[] {},
                                        new float[] {},
                                        new long[] {},
                                        new int[] {},
                                        new short[] {},
                                        new char[] {},
                                        new byte[] {},
                                        new boolean[] {},

                                        new ArrayList<String>().iterator(),
                                        toEnumeration(new ArrayList<String>()),
                                        new Iterator<User>(){

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
                                        }

        };

        Object[] notNullOrEmptyElement = {
                                           new Integer[] { 2 }, //
                                           new Long[] { 2L },
                                           new int[] { 1, 2 },
                                           new double[] { 1.2d },
                                           new long[] { 200L },
                                           new float[] { 5.8f },
                                           new boolean[] { true },
                                           new char[] { 'a' },
                                           new short[] { 5 } };
        return TestUtil.toDataList(nullOrEmptyElement, notNullOrEmptyElement);
    }

}
