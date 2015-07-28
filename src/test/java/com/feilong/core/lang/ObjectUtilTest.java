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

import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.test.User;

/**
 * The Class ObjectUtilTest.
 * 
 * @author feilong
 * @version 1.0 Jan 4, 2013 1:58:05 PM
 */
public class ObjectUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtilTest.class);

    /**
     * Name.
     */
    @Test
    public void testSize(){
        //		 LOGGER.info("Size of Object: " + ObjectUtil.size(new Object()));
        LOGGER.info("Size of Calendar: " + ObjectUtil.size(Calendar.getInstance()));
        LOGGER.info("Size of HashMap: " + ObjectUtil.size(new HashMap<String, String>()));
    }

    /**
     * Assert equals.
     */
    @Test
    public final void assertEquals2(){

        Long a = new Long(1L);
        Long b = new Long(1L);

        LOGGER.info((a == b) + "");
        LOGGER.info(a.equals(b) + "");

        User user = new User(1L);
        List<User> list = new ArrayList<User>();

        list.add(user);
        list.add(new User(1L));
        list.add(new User(new Long(1L)));
        list.add(new User(new Long(1L)));
        list.add(new User(new Long(1L)));

        for (User user2 : list){
            LOGGER.info((user2.getId() == user.getId()) + "");
        }
    }

    /**
     * Test method for {@link com.feilong.core.lang.ObjectUtil#toIterator(java.lang.Object)}.
     */
    @Test
    public final void testToIterator(){

        // *************************逗号分隔的数组********************************
        LOGGER.info(StringUtils.center("逗号分隔的数组", 60, "*"));
        Iterator<?> iterator = ObjectUtil.toIterator("1,2");
        printIterator(iterator);

        // ************************map*********************************
        LOGGER.info(StringUtils.center("map", 60, "*"));
        Map<String, String> map = new HashMap<String, String>();

        map.put("a", "1");
        map.put("b", "2");

        iterator = ObjectUtil.toIterator(map);
        printIterator(iterator);

        // ***************************array******************************
        LOGGER.info(StringUtils.center("array", 60, "*"));
        Object[] array = { "5", 8 };
        iterator = ObjectUtil.toIterator(array);
        printIterator(iterator);
        // ***************************collection******************************
        LOGGER.info(StringUtils.center("collection", 60, "*"));
        Collection<String> collection = new ArrayList<String>();
        collection.add("aaaa");
        collection.add("nnnnn");

        iterator = ObjectUtil.toIterator(collection);
        printIterator(iterator);

        // **********************enumeration***********************************
        LOGGER.info(StringUtils.center("enumeration", 60, "*"));
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");
        LOGGER.debug(JsonUtil.format(ObjectUtil.toIterator(enumeration)));
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
            LOGGER.info(object.toString());
        }
    }

}