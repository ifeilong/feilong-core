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
package com.feilong.core.util.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.lang3.Validate;

/**
 * 专注于 bean 属性值的排序.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public final class BeanComparatorUtil{

    /** Don't let anyone instantiate this class. */
    private BeanComparatorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //*************************************************************************************************

    /**
     * Chained comparator.
     *
     * @param <T>
     *            the generic type
     * @param propertyNames
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,该属性对应的value 必须实现 {@link Comparable}接口.
     * @return the comparator
     * @throws NullPointerException
     *             如果 <code>propertyNames</code> 是null,或者有元素是 null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNames</code> 是empty,或者有元素是 blank
     * @see org.apache.commons.collections4.ComparatorUtils#chainedComparator(java.util.Collection)
     */
    public static <T> Comparator<T> chainedComparator(String...propertyNames){
        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");

        List<Comparator<T>> comparators = new ArrayList<>();
        for (String propertyName : propertyNames){
            Validate.notBlank(propertyName, "propertyName can't be blank!");

            Comparator<T> comparator = propertyComparator(propertyName);
            comparators.add(comparator);
        }
        return ComparatorUtils.chainedComparator(comparators);
    }

    /**
     * Property comparator.
     *
     * @param <T>
     *            the generic type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,该属性对应的value 必须实现 {@link Comparable}接口.
     * @return the comparator
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see PropertyComparator#PropertyComparator(String)
     */
    public static <T> Comparator<T> propertyComparator(String propertyName){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new PropertyComparator<>(propertyName);
    }

    /**
     * Property comparator.
     *
     * @param <V>
     *            the value type
     * @param <T>
     *            the generic type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,该属性对应的value 必须实现 {@link Comparable}接口.
     * @param propertyValues
     *            the property values
     * @return the comparator
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null,或者<code>propertyValues</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see PropertyComparator#PropertyComparator(String, Comparator)
     * @see FixedOrderComparator#FixedOrderComparator(Object...)
     */
    @SafeVarargs
    public static <V, T> Comparator<T> propertyComparator(String propertyName,V...propertyValues){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        return new PropertyComparator<>(propertyName, new FixedOrderComparator<>(propertyValues));
    }

    /**
     * Property comparator.
     *
     * @param <V>
     *            the value type
     * @param <T>
     *            the generic type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,该属性对应的value 必须实现 {@link Comparable}接口.
     * @param propertyValues
     *            the property values
     * @return the comparator
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null,或者<code>propertyValues</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see PropertyComparator#PropertyComparator(String, Comparator)
     * @see FixedOrderComparator#FixedOrderComparator(List)
     */
    public static <V, T> Comparator<T> propertyComparator(String propertyName,List<V> propertyValues){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        return new PropertyComparator<>(propertyName, new FixedOrderComparator<>(propertyValues));
    }
}
