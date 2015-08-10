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
     * To iterator.
     */
    @Test
    public final void toIterator(){
        String[] array = { "1", "223" };
        LOGGER.debug(JsonUtil.format(toIterator(array)));

        int[] arrays = { 1, 2 };
        LOGGER.debug(JsonUtil.format(toIterator(arrays)));
        LOGGER.debug(JsonUtil.format(new ArrayIterator(arrays)));
        LOGGER.debug(JsonUtil.format(new ArrayIterator(array)));
        LOGGER.debug(JsonUtil.format(new ArrayIterator(null)));
    }

    /**
     * 将数组转成转成 {@link java.util.Iterator}.
     * <p>
     * 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying<br>
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
     * @return if (null == arrays) return null;<br>
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
            // 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying
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