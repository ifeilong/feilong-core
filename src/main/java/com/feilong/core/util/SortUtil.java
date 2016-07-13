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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.feilong.core.util.comparator.BeanComparatorUtil;

/**
 * 专注于排序的工具类.
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>该类的初衷之一,通常如果直接使用jdk里面的sort方法,比如
 * 
 * <pre class="code">
 * {@link java.util.Arrays#sort(Object[])}
 * </pre>
 * 
 * 或者
 * 
 * <pre class="code">
 * {@link java.util.Collections#sort(List)}
 * </pre>
 * 
 * 由于 这些方法是 void类型的,通常我们需要写2至3行代码
 * 
 * </li>
 * <li>第二,该类对bean list的排序更加简单便捷</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public final class SortUtil{

    /** Don't let anyone instantiate this class. */
    private SortUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //*****************************************************************************************
    /**
     * 对 数组 <code>array</code> 进行排序.
     *
     * @param <T>
     *            the generic type
     * @param array
     *            the array
     * @return 如果 <code>array</code> 是null,返回 null<br>
     * @see java.util.Arrays#sort(Object[])
     */
    public static <T> T[] sort(T[] array){
        if (null == array){
            return null;
        }
        Arrays.sort(array);
        return array;
    }

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code> 进行排序.
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @return 如果 <code>list</code> 是null,返回 null<br>
     * @see java.util.Collections#sort(List)
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> list){
        if (null == list){
            return null;
        }
        Collections.sort(list);
        return list;
    }

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code>,指定属性(组合)进行排序.
     *
     * @param <O>
     *            the generic type
     * @param list
     *            the list
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>list</code> 是null,返回 null<br>
     * @throws NullPointerException
     *             如果 <code>propertyNames</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNames</code> 是empty ,或者有 null元素
     * @see BeanComparatorUtil#chainedComparator(String...)
     * @see org.apache.commons.collections4.ComparatorUtils#chainedComparator(java.util.Comparator...)
     */
    public static <O> List<O> sort(List<O> list,String...propertyNames){
        if (null == list){
            return null;
        }
        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");
        Validate.noNullElements(propertyNames, "propertyName:%s has empty value", propertyNames);

        Collections.sort(list, BeanComparatorUtil.chainedComparator(propertyNames));
        return list;
    }

    //*************************************************************************************************

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the property values
     * @return 如果 <code>list</code> 是null,返回 null<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, Object...)
     */
    @SafeVarargs
    public static <O, V> List<O> sortByFixedOrder(List<O> list,String propertyName,V...propertyValues){
        if (null == list){
            return null;
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Collections.sort(list, BeanComparatorUtil.propertyComparator(propertyName, propertyValues));
        return list;
    }

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the property values
     * @return 如果 <code>list</code> 是null,返回 null<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, List)
     */
    public static <O, V> List<O> sortByFixedOrder(List<O> list,String propertyName,List<V> propertyValues){
        if (null == list){
            return null;
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Collections.sort(list, BeanComparatorUtil.propertyComparator(propertyName, propertyValues));
        return list;
    }
}
