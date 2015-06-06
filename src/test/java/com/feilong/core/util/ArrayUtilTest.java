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
package com.feilong.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.entity.ToStringConfig;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.core.util.ArrayUtil;
import com.feilong.core.util.ListUtil;
import com.feilong.test.User;

/**
 * The Class ArrayUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-12 上午11:29:02
 * @since 1.0
 */
public class ArrayUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ArrayUtilTest.class);

    /**
     * TestArrayUtilTest.
     */
    @Test
    public void testArrayUtilTest(){
        String[] strs = new String[10];
        log.debug("" + strs.length);
    }

    /**
     * Test array util test22.
     */
    @Test
    public void testArrayUtilTest22(){
        String[] strs = {
                "ppt-coreContent1.png",
                "ppt-coreContent3.png",
                "ppt-coreContent10.png",
                "ppt-coreContent11.png",
                "ppt-coreContent2.png" };
        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(strs));
        }

    }

    /**
     * Test is contain.
     */
    @Test
    public void testIsContain(){
        Assert.assertEquals(true, ArrayUtil.isContain(new Integer[] { 1, 223 }, 1));
        Assert.assertEquals(true, ArrayUtil.isContain(new Long[] { 1L, 223L }, 1L));

        String[] array = new String[] { "1", "223" };
        Assert.assertEquals(false, ArrayUtil.isContain(array, "2"));
    }

    /**
     * TestArrayUtilTest.
     */
    @Test
    public void testGroup(){
        Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };

        Map<Integer, List<Integer>> group = ArrayUtil.group(array);

        log.debug(JsonUtil.format(group));

        Collection<List<Integer>> values = group.values();
        log.debug(JsonUtil.format(values));

        //****************************************************************
        String[] array1 = { "金鑫", "feilong", "金鑫", "基友团", "金鑫" };

        Map<String, List<String>> group1 = ArrayUtil.group(array1);

        log.debug(JsonUtil.format(group1));

        Collection<List<String>> values1 = group1.values();
        log.debug(JsonUtil.format(values1));
    }

    /**
     * Test group object.
     *
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     */
    @Test
    public void testGroupObject() throws IllegalAccessException,InvocationTargetException,NoSuchMethodException{
        User[] users = {
                new User("张三", 18),
                new User("李四", 28),
                new User("王五", 38),
                new User("陈二", 18),
                new User("孔六", 28),
                new User("飞飞", 58) };

        Map<Integer, List<User>> group = ArrayUtil.group(users, "age");

        log.debug(JsonUtil.format(group));

        Collection<List<User>> values = group.values();
        log.debug(JsonUtil.format(values));

    }

    /**
     * Test get by array.
     */
    @Test
    public void testGetElement(){
        if (log.isInfoEnabled()){
            Object array = new String[] { "jinxin", "feilong", "1" };
            int index = 2;
            String element = ArrayUtil.getElement(array, index);
            log.info("" + element);
        }
    }

    /**
     * Test.
     */
    @Test
    public final void test(){
        String[] aStrings = new String[2];
        aStrings[0] = "a";
        aStrings[1] = "b";
        log.info(Arrays.toString(aStrings));
        log.info(ArrayUtils.toString(aStrings));
        String aString = "FACTORY_ID,SHOE_NAME,CHANNEL,PRODUCT_CODE,COLOR_CODE,PAYTYPE,FACTORY_CODE,TRACKING_NO_UPS,MH_ID,DEVICE,SUB_TOTAL_PRICE,TAX_PRICE,DELIVERY_PRICE,TOTAL_PRICE,PAY_DATE,REVENUE_DATE,RETURN_DATE,CANCEL_DATE,SHOP_NAME,CALCEL_CODE";
        log.info(ListUtil.toString(Arrays.asList(aString.split(",")), true));
    }

    /**
     * 数组和list 转换.
     */
    @Test
    public final void arrayAndList(){

        String aString = "FACTORY_ID,SHOE_NAME,CHANNEL,PRODUCT_CODE,COLOR_CODE,PAYTYPE,FACTORY_CODE,TRACKING_NO_UPS,MH_ID,DEVICE,SUB_TOTAL_PRICE,TAX_PRICE,DELIVERY_PRICE,TOTAL_PRICE,PAY_DATE,REVENUE_DATE,RETURN_DATE,CANCEL_DATE,SHOP_NAME,CALCEL_CODE";
        List<String> asList = Arrays.asList(aString.split(","));
        log.info(ListUtil.toString(asList, true));

        String[] array = asList.toArray(new String[0]);

        for (String string : array){
            log.info(string);
        }

        User[] users = { new User(5L), new User(6L) };
        List<User> list = Arrays.asList(users);
        for (User user : list){
            log.info(user.getId() + "");
        }

        User[] usersarray = list.toArray(new User[0]);

        for (User user : usersarray){
            log.info(user.getId() + "");
        }
    }

    /**
     * Convert list to string replace brackets.
     */
    @Test
    public final void convertListToStringReplaceBrackets(){
        String[] array = new String[] { "1", "223" };
        //Use "Arrays.toString(array)" instead.
        log.info(array.toString());
        log.info(Arrays.toString(array));
        log.info(StringUtils.join(array, ","));
    }

    /**
     * To iterator.
     */
    @Test
    public final void toIterator(){
        String[] array = { "1", "223" };
        Iterator<String> iterator = ArrayUtil.toIterator(array);

        while (iterator.hasNext()){
            log.debug(iterator.next());
        }

        int[] arrays = { 1, 2 };
        Iterator<Integer> iterator1 = ArrayUtil.toIterator(arrays);

        //		while (iterator1.hasNext()){
        //			log.debug("" + iterator1.next());
        //		}
        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(iterator1));
        }

    }

    /**
     * To iterator user.
     */
    @Test
    public final void toIteratorUser(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        User[] users = { user1, user2 };

        Iterator<User> iterator = ArrayUtil.toIterator(users);

        while (iterator.hasNext()){
            User user = iterator.next();
            log.debug("{}", user.getId());
        }
    }

    /**
     * To list.
     */
    @Test
    public void toList(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        User[] users = { user1, user2 };

        List<User> list = ArrayUtil.toList(users);
        log.info(JsonUtil.format(list));

    }

    /**
     * To string1.
     */
    @Test
    public void toString1(){
        Object[] arrays = { "222", "1111" };
        ToStringConfig toStringConfig = new ToStringConfig(",");
        log.info(ArrayUtil.toString(arrays, toStringConfig));

        Integer[] array1 = { 2, 1 };
        log.info(ArrayUtil.toString(array1, toStringConfig));

    }
}
