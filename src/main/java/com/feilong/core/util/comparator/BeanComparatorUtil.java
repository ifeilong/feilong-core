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

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;
import org.apache.commons.lang3.Validate;

import static com.feilong.core.bean.ConvertUtil.toList;

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
     * 按照不同指定属性 <code>propertyName</code> 排序的 {@link Comparator}.
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

            //注意此处不要使用 propertyComparator(propertyName)  PropertyComparator
            //因为,上述 PropertyComparator 如果属性值相同 会比较 hashcode值(为了map), 也就是说通常而言会比较出顺序 
            comparators.add(new BeanComparator<T>(propertyName));
        }
        return ComparatorUtils.chainedComparator(comparators);
    }

    //*************************************************************************************************
    /**
     * 指定属性 <code>propertyName</code> ,按照自然顺序 排序的 {@link Comparator}.
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
     * 指定属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 排序的 {@link Comparator}.
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
     * @see #propertyComparator(String, List)
     */
    @SafeVarargs
    public static <V, T> Comparator<T> propertyComparator(String propertyName,V...propertyValues){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        return propertyComparator(propertyName, toList(propertyValues));
    }

    /**
     * 指定属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 排序的 {@link Comparator}.
     * 
     * <p>
     * 调用 {@link #propertyComparator(String, List, UnknownObjectBehavior)},默认 {@link UnknownObjectBehavior#AFTER}
     * </p>
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
     * @see #propertyComparator(String, List, UnknownObjectBehavior)
     */
    public static <V, T> Comparator<T> propertyComparator(String propertyName,List<V> propertyValues){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        return propertyComparator(propertyName, propertyValues, UnknownObjectBehavior.AFTER);
    }

    /**
     * 指定属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code>,并且可以指定 <code>unknownObjectBehavior</code> 排序的 {@link Comparator}.
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
     * @param unknownObjectBehavior
     *            the unknown object behavior
     * @return the comparator
     * 
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null,或者<code>propertyValues</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see PropertyComparator#PropertyComparator(String, Comparator)
     * @see FixedOrderComparator#FixedOrderComparator(List)
     * @since 1.8.2
     */
    public static <V, T> Comparator<T> propertyComparator(
                    String propertyName,
                    List<V> propertyValues,
                    UnknownObjectBehavior unknownObjectBehavior){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        FixedOrderComparator<V> fixedOrderComparator = new FixedOrderComparator<>(propertyValues);
        fixedOrderComparator.setUnknownObjectBehavior(unknownObjectBehavior);
        return new PropertyComparator<>(propertyName, fixedOrderComparator);
    }
}
