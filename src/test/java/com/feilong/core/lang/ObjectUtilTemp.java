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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.iterators.ArrayIterator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ObjectUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ObjectUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtilTemp.class);

    @Test
    public void test(){
        //Object[] objects =  { "a", "b" }; //这个不行  
        Object[] objects = new String[] { "a", "b" };
        String[] strings = (String[]) objects;

        LOGGER.debug(JsonUtil.format(strings));
    }

    @Test
    public void testIsBoolean(){
        assertEquals(false, isBoolean(null));
        assertEquals(true, isBoolean(false));
    }

    /**
     * Checks if is integer.
     */
    @Test
    public void testIsInteger(){
        assertEquals(false, isInteger(null));
        assertEquals(false, isInteger(false));
        assertEquals(true, isInteger(1));
        assertEquals(false, isInteger(5.56));
    }

    /**
     * 判断指定的对象 <code>object</code>是不是 {@link Boolean} 类型数据.
     * 
     * @param object
     *            对象
     * @return 是返回true <br>
     *         如果 <code>object</code> 是null,返回false
     */
    public static boolean isBoolean(Object object){
        return object instanceof Boolean;
    }

    /**
     * 判断指定的对象 <code>object</code>是不是{@link Integer}类型.
     * 
     * @param object
     *            对象
     * @return 是返回true <br>
     *         如果 <code>object</code> 是null,返回false
     */
    public static boolean isInteger(Object object){
        return object instanceof Integer;
    }

    /**
     * To iterator.
     */
    @Test
    public void toIterator(){
        String[] array = { "1", "223" };
        LOGGER.debug(JsonUtil.format(toIterator(array)));

        int[] arrays = { 1, 2 };
        LOGGER.debug(JsonUtil.format(toIterator(arrays)));
        LOGGER.debug(JsonUtil.format(new ArrayIterator(arrays)));
        LOGGER.debug(JsonUtil.format(new ArrayIterator(array)));
        //LOGGER.debug(JsonUtil.format(new ArrayIterator(null)));
    }

    /**
     * 将数组转成转成 {@link java.util.Iterator}.
     * <p>
     * 如果我们幸运的话,它是一个对象数组,我们可以遍历并with no copying<br>
     * 否则,异常 ClassCastException 中 ,Rats -- 它是一个基本类型数组,循环放入arrayList 转成arrayList.iterator()
     * </p>
     * <p>
     * <b>注:</b>{@link Arrays#asList(Object...)} 转的list是 {@link Array} 的内部类 ArrayList,这个类没有实现
     * {@link java.util.AbstractList#add(int, Object)} 这个方法,<br>
     * 如果拿这个list进行add操作,会出现 {@link java.lang.UnsupportedOperationException}
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            数组,可以是 对象数组,或者是 基本类型数组
     * @return 如果 (null == arrays) 返回 null;<br>
     *         否则会先将arrays转成Object[]数组,调用 {@link Arrays#asList(Object...)}转成list,再调用 {@link List#iterator()
     *         t}<br>
     *         对于基本类型的数组,由于不是 Object[],会有类型转换异常,此时先通过 {@link Array#getLength(Object)}取到数组长度,循环调用 {@link Array#get(Object, int)}设置到 list中
     * @see Arrays#asList(Object...)
     * @see Array#getLength(Object)
     * @see Array#get(Object, int)
     * @see List#iterator()
     * @deprecated
     */
    @Deprecated
    @SuppressWarnings({ "unchecked" })
    public static <T> Iterator<T> toIterator(Object arrays){
        if (null == arrays){
            return null;
        }
        List<T> list = null;
        try{
            // 如果我们幸运的话,它是一个对象数组,我们可以遍历并with no copying
            Object[] objArrays = (Object[]) arrays;
            list = (List<T>) ConvertUtil.toList(objArrays);
        }catch (ClassCastException e){
            LOGGER.debug("arrays can not cast to Object[],maybe primitive type,values is:{},{}", arrays, e.getMessage());
            // Rats -- 它是一个基本类型数组
            int length = Array.getLength(arrays);
            list = new ArrayList<T>(length);
            for (int i = 0; i < length; ++i){
                Object object = Array.get(arrays, i);
                list.add((T) object);
            }
        }
        return list.iterator();
    }
}