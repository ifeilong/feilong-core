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

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static com.feilong.core.util.comparator.SortHelper.isAsc;
import static org.apache.commons.collections4.ComparatorUtils.reversedComparator;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ComparableComparator;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;
import org.apache.commons.lang3.Validate;

/**
 * 专注于 bean 属性值的排序.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.ComparatorUtils
 * @see FixedOrderComparator
 * @since 1.8.0
 */
public final class BeanComparatorUtil{

    /** Don't let anyone instantiate this class. */
    private BeanComparatorUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 按照不同指定属性 <code>propertyNameAndOrders</code> 排序的 {@link Comparator}.
     *
     * @param <T>
     *            the generic type
     * @param propertyNameAndOrders
     *            属性名称和排序因子,
     * 
     *            <p>
     *            格式可以是纯的属性名称, 比如 "name"; 也可以是属性名称+排序因子(以空格分隔),比如 "name desc"
     *            </p>
     * 
     *            <h3>说明:</h3>
     *            <blockquote>
     * 
     *            <dl>
     *            <dt>关于属性名称</dt>
     *            <dd>
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be
     *            modified,参见<a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,<br>
     *            该属性对应的value 必须实现 {@link Comparable}接口.
     *            </dd>
     * 
     *            <dt>关于排序因子</dt>
     *            <dd>
     *            可以没有排序因子<br>
     *            如果有,值可以是asc(顺序),desc(倒序)两种;<br>
     *            如果没有,默认按照asc(顺序)排序;<br>
     *            此外,asc/desc忽略大小写
     *            </dd>
     * 
     *            </dl>
     * 
     *            </blockquote>
     * @return 如果propertyNameAndOrders是单值,那么直接调用 {@link #propertyComparator(String)} 返回
     * @throws NullPointerException
     *             如果 <code>propertyNameAndOrders</code> 是null,<br>
     *             或者有元素是 null;
     * @throws IllegalArgumentException
     *             如果 <code>propertyNameAndOrders</code> 是empty,<br>
     *             或者有元素是 blank
     * @see org.apache.commons.collections4.ComparatorUtils#chainedComparator(java.util.Collection)
     * @since 1.10.2 support propertyNameAndOrder
     */
    public static <T> Comparator<T> chainedComparator(String...propertyNameAndOrders){
        Validate.notEmpty(propertyNameAndOrders, "propertyNameAndOrders can't be null/empty!");

        //如果propertyNameAndOrders是单值,那么直接调用 com.feilong.core.util.comparator.BeanComparatorUtil.propertyComparator(String) 返回
        if (1 == propertyNameAndOrders.length){
            return propertyComparator(propertyNameAndOrders[0]);
        }

        //---------------------------------------------------------------

        List<Comparator<T>> comparators = newArrayList();
        for (String propertyNameAndOrder : propertyNameAndOrders){
            Validate.notBlank(propertyNameAndOrder, "propertyNameAndOrder can't be blank!");

            String[] propertyNameAndOrderArray = SortHelper.parsePropertyNameAndOrder(propertyNameAndOrder);

            //注意:此处不要使用 propertyComparator(propertyName)

            //因为,PropertyComparator 如果属性值相同,会使用其他规则继续比较(为了TreeMap/treeSet), 
            //也就是说,通常而言一次就比较出顺序,后续的propertyNameAndOrders 就没作用了
            Comparator instance = ComparatorUtils.nullHighComparator(ComparableComparator.comparableComparator()); //null排在最后面  

            BeanComparator<T> beanComparator = new BeanComparator<>(propertyNameAndOrderArray[0], instance);
            comparators.add(isAsc(propertyNameAndOrderArray) ? beanComparator : reversedComparator(beanComparator));
        }
        return ComparatorUtils.chainedComparator(comparators);
    }

    //---------------------------------------------------------------

    /**
     * 指定属性 <code>propertyName</code> ,按照自然顺序 排序的 {@link Comparator}.
     *
     * @param <T>
     *            the generic type
     * @param propertyNameAndOrder
     *            属性名称和排序因子,
     * 
     *            <p>
     *            格式可以是纯的属性名称, 比如 "name"; 也可以是属性名称+排序因子(以空格分隔),比如 "name desc"
     *            </p>
     * 
     *            <h3>说明:</h3>
     *            <blockquote>
     * 
     *            <dl>
     *            <dt>关于属性名称</dt>
     *            <dd>
     *            泛型T对象的属性名称,Possibly indexed and/or nested name of the property to be
     *            modified,参见<a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,<br>
     *            该属性对应的value 必须实现 {@link Comparable}接口.
     *            </dd>
     * 
     *            <dt>关于排序因子</dt>
     *            <dd>
     *            可以没有排序因子<br>
     *            如果有,值可以是asc(顺序),desc(倒序)两种;<br>
     *            如果没有,默认按照asc(顺序)排序;<br>
     *            此外,asc/desc忽略大小写
     *            </dd>
     * 
     *            </dl>
     * 
     *            </blockquote>
     * @return {@link PropertyComparator}
     * @throws NullPointerException
     *             如果 <code>propertyNameAndOrder</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNameAndOrder</code> 是blank
     * @see PropertyComparator#PropertyComparator(String)
     */
    public static <T> Comparator<T> propertyComparator(String propertyNameAndOrder){
        Validate.notBlank(propertyNameAndOrder, "propertyNameAndOrder can't be blank!");

        String[] propertyNameAndOrderArray = SortHelper.parsePropertyNameAndOrder(propertyNameAndOrder);

        PropertyComparator<T> propertyComparator = new PropertyComparator<>(propertyNameAndOrderArray[0]);
        return isAsc(propertyNameAndOrderArray) ? propertyComparator : reversedComparator(propertyComparator);
    }

    //---------------------------------------------------------------

    /**
     * 指定属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 排序的 {@link Comparator}.
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
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
    public static <T, V> Comparator<T> propertyComparator(String propertyName,V...propertyValues){
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
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
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
    public static <T, V> Comparator<T> propertyComparator(String propertyName,List<V> propertyValues){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        return propertyComparator(propertyName, propertyValues, UnknownObjectBehavior.AFTER);
    }

    /**
     * 指定属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code>,并且可以指定 <code>unknownObjectBehavior</code> 排序的 {@link Comparator}.
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>,该属性对应的value 必须实现 {@link Comparable}接口.
     * @param propertyValues
     *            the property values
     * @param unknownObjectBehavior
     *            the unknown object behavior
     * @return the comparator
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null,或者<code>propertyValues</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see PropertyComparator#PropertyComparator(String, Comparator)
     * @see FixedOrderComparator#FixedOrderComparator(List)
     * @since 1.8.2
     */
    public static <T, V> Comparator<T> propertyComparator(
                    String propertyName,
                    List<V> propertyValues,
                    UnknownObjectBehavior unknownObjectBehavior){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Validate.notNull(propertyValues, "propertyValues can't be null!");
        FixedOrderComparator<V> fixedOrderComparator = ComparatorUtil.buildFixedOrderComparator(propertyValues, unknownObjectBehavior);
        return new PropertyComparator<>(propertyName, fixedOrderComparator);
    }

}
