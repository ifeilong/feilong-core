/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;

/**
 * The Class ObjectUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 4, 2013 1:58:05 PM
 */
public class ObjectUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ObjectUtilTest.class);

    /**
     * Name.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void name() throws IOException{
        //		 log.info("Size of Object: " + ObjectUtil.size(new Object()));
        log.info("Size of Calendar: " + ObjectUtil.size(Calendar.getInstance()));
        log.info("Size of HashMap: " + ObjectUtil.size(new HashMap<String, String>()));
    }

    /**
     * Test method for {@link com.feilong.core.lang.ObjectUtil#equalsNotNull(java.lang.Object, java.lang.Object)}.
     */
    @Test
    public final void testEqualsNotNull(){
        assertEquals(false, ObjectUtil.equalsNotNull(1, 2));
        assertEquals(false, ObjectUtil.equalsNotNull(1, null));
        assertEquals(false, ObjectUtil.equalsNotNull(null, 2));
        assertEquals(false, ObjectUtil.equalsNotNull(null, null));

        assertEquals(false, ObjectUtil.equalsNotNull(1, "1"));
        assertEquals(true, ObjectUtil.equalsNotNull(1, 1));
        assertEquals(true, ObjectUtil.equalsNotNull("1", "1"));
    }

    @Test
    public final void testEquals(){
        assertEquals(true, ObjectUtil.equals(1, 1, false));
        assertEquals(true, ObjectUtil.equals(null, null, false));
        assertEquals(true, ObjectUtil.equals("", " ", false));
    }

    /**
     * Assert equals.
     */
    @Test
    public final void assertEquals2(){

        Long a = new Long(1L);
        Long b = new Long(1L);

        log.info((a == b) + "");
        log.info(a.equals(b) + "");

        User user = new User(1L);
        List<User> list = new ArrayList<User>();

        list.add(user);
        list.add(new User(1L));
        list.add(new User(new Long(1L)));
        list.add(new User(new Long(1L)));
        list.add(new User(new Long(1L)));

        for (User user2 : list){
            log.info((user2.getId() == user.getId()) + "");
        }
    }

    /**
     * Test method for {@link com.feilong.core.lang.ObjectUtil#toIterator(java.lang.Object)}.
     */
    @Test
    public final void testToIterator(){

        // *************************逗号分隔的数组********************************
        log.info(StringUtils.center("逗号分隔的数组", 60, "*"));
        Iterator<?> iterator = ObjectUtil.toIterator("1,2");
        printIterator(iterator);

        // ************************map*********************************
        log.info(StringUtils.center("map", 60, "*"));
        Map<String, String> map = new HashMap<String, String>();

        map.put("a", "1");
        map.put("b", "2");

        iterator = ObjectUtil.toIterator(map);
        printIterator(iterator);

        // ***************************array******************************
        log.info(StringUtils.center("array", 60, "*"));
        Object[] array = { "5", 8 };
        iterator = ObjectUtil.toIterator(array);
        printIterator(iterator);
        // ***************************collection******************************
        log.info(StringUtils.center("collection", 60, "*"));
        Collection<String> collection = new ArrayList<String>();
        collection.add("aaaa");
        collection.add("nnnnn");

        iterator = ObjectUtil.toIterator(collection);
        printIterator(iterator);

        // **********************enumeration***********************************
        log.info(StringUtils.center("enumeration", 60, "*"));
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");
        log.debug(JsonUtil.format(ObjectUtil.toIterator(enumeration)));
    }

    /**
     * Prints the iterator.
     * 
     * @param iterator
     *            the iterator
     */
    private void printIterator(Iterator<?> iterator){
        while (iterator.hasNext()){
            Object object = iterator.next();
            log.info(object.toString());
        }
    }

    /**
     * Test method for {@link com.feilong.core.lang.ObjectUtil#toInteger(java.lang.Object)}.
     */
    @Test
    public final void testToInteger(){
        assertEquals(8, ObjectUtil.toInteger(8L).intValue());
        assertEquals(8, ObjectUtil.toInteger("8").intValue());
    }

    /**
     * Test to string object.
     */
    @Test
    public final void testToStringObject(){
        String[] aaaa = { "aa", "aaa" };
        log.info(ObjectUtil.toString(aaaa));
    }

    /**
     * Test method for {@link com.feilong.core.lang.ObjectUtil#toT(java.lang.Object, java.lang.Class)}.
     */
    @Test
    public final void testToT(){
        log.info(ObjectUtil.toT(BigDecimal.ONE, Float.class) + "");
    }

    @Test
    public final void testToBigDecimal(){
        assertEquals(BigDecimal.valueOf(1111), ObjectUtil.toBigDecimal(1111));
        assertEquals(BigDecimal.valueOf(0.1), ObjectUtil.toBigDecimal(0.1));
    }
}
