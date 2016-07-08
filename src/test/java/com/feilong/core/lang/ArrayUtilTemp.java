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

import static com.feilong.core.date.DateExtensionUtil.getIntervalForView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.MapUtil;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ArrayUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0
 */
public class ArrayUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayUtilTemp.class);

    /**
     * 一筐鸡蛋：
     * 1个1个拿,正好拿完。
     * 2个2个拿,还剩1个。
     * 3个3个拿,正好拿完。
     * 4个4个拿,还剩1个。
     * 5个5个拿,还剩1个
     * 6个6个拿,还剩3个。
     * 7个7个拿,正好拿完。
     * 8个8个拿,还剩1个。
     * 9个9个拿,正好拿完。
     * 
     * 问筐里有多少鸡蛋？.
     *
     * @since 1.5.5
     */
    @Test
    public void testArrayUtilTest2(){
        Date beginDate = new Date();

        int j = 1;
        int z = 7 * 9;

        int total = 10000;
        List<Integer> list = new ArrayList<Integer>(total / z / 5);
        for (int i = z; i < total; i = z * j){
            //LOGGER.debug("i:{},j={}", i, j);
            if (i % 5 == 1 && i % 4 == 1 && i % 6 == 3 && i % 8 == 1){
                list.add(i);
            }
            ++j;
        }
        LOGGER.debug("loop count j=[{}],use time:[{}],\nlist:{}", j, getIntervalForView(beginDate, new Date()), list);
    }

    /**
     * TestArrayUtilTest.
     */
    @Test
    public void testGroup(){
        Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };
        Map<Integer, List<Integer>> group = group(array);
        LOGGER.debug(JsonUtil.format(group));

    }

    /**
     * TestArrayUtilTest.
     */
    @Test
    public void testGroup1(){
        String[] array1 = { "关羽", "feilong", "关羽", "基友团", "关羽" };

        Map<String, List<String>> group1 = group(array1);
        LOGGER.debug(JsonUtil.format(group1));

        Collection<List<String>> values1 = group1.values();
        LOGGER.debug(JsonUtil.format(values1));
    }

    /**
     * Test group object.
     */
    @Test
    public void testGroupObject(){
        User[] users = {
                         new User("张三", 18),
                         new User("李四", 28),
                         new User("王五", 38),
                         new User("陈二", 18),
                         new User("孔六", 28),
                         new User("飞飞", 58) };

        Map<Integer, List<User>> group = group(users, "age");
        LOGGER.debug(JsonUtil.format(group));
    }

    /**
     * 将<code>array</code> 分组.
     * 
     * <p>
     * 返回的 {@link LinkedHashMap} ,key是分组中的值,value是分组值的列表;key的顺序是依照 <code>array</code>元素不同值的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Integer[] array = { 1, 1, 1, 2, 2, 3, 4, 5, 5, 6, 7, 8, 8 };
     * Map{@code <Integer, List<Integer>>} group = ArrayUtil.group(array);
     * LOGGER.debug(JsonUtil.format(group));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {
         "1":         [
             1,
             1,
             1
         ],
         "2":         [
             2,
             2
         ],
         "3": [3],
         "4": [4],
         "5":         [
             5,
             5
         ],
         "6": [6],
         "7": [7],
         "8":         [
             8,
             8
         ]
     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param array
     *            the array
     * @return 如果 <code>array</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @since 1.0.8
     */
    public static <T> Map<T, List<T>> group(T[] array){
        if (null == array){
            return Collections.emptyMap();
        }
        Map<T, List<T>> map = MapUtil.newLinkedHashMap(array.length);
        for (T t : array){
            MapUtil.putMultiValue(map, t, t);
        }
        return map;
    }

    /**
     * 将对象数组 <code>array</code>,按照指定属性的值 <code>propertyName</code> 进行分组.
     * 
     * <p>
     * 返回的map是 {@link LinkedHashMap},key是指定属性名称的值,value是指定名称值所属对象list,顺序是依照属性名称值顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User[] users = {
     *                  new User("张三", 18),
     *                  new User("李四", 28),
     *                  new User("王五", 38),
     *                  new User("陈二", 18),
     *                  new User("孔六", 28),
     *                  new User("飞飞", 58) };
     * 
     * Map{@code <Integer, List<User>>} group = ArrayUtil.group(users, "age");
     * LOGGER.debug(JsonUtil.format(group));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {
         "18": [{
                 "age": 18,
                 "name": "张三"
             },{
                 "age": 18,
                 "name": "陈二"
             }
         ],
         "28": [{
                 "age": 28,
                 "name": "李四"
             },{
                 "age": 28,
                 "name": "孔六"
             }
         ],
         "38": [{
             "age": 38,
             "name": "王五"
         }],
         "58": [{
             "age": 58,
             "name": "飞飞"
         }]
     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param array
     *            对象数组
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectArray</code> 是 null或者empty,返回 {@link java.util.Collections#emptyMap()} <br>
     *         如果 <code>propertyName</code>是 null 抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code>是 blank 抛出 {@link IllegalArgumentException}<br>
     * @see com.feilong.core.bean.ConvertUtil#toList(Object...)
     * @see com.feilong.core.util.CollectionsUtil#group(java.util.Collection, String)
     * @since 1.0.8
     */
    public static <O, T> Map<T, List<O>> group(O[] array,String propertyName){
        if (Validator.isNullOrEmpty(array)){
            return Collections.emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        return CollectionsUtil.group(ConvertUtil.toList(array), propertyName);
    }
}
