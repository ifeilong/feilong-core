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
package com.feilong.core.util.predicate;

import static com.feilong.core.Validator.isNullOrEmpty;

import java.util.Collection;
import java.util.Comparator;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;

import com.feilong.core.bean.PropertyUtil;

/**
 * The Class BeanPredicateUtil.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.PredicateUtils
 * @see com.feilong.core.util.predicate.BeanPredicate
 * @since 1.8.0
 */
public final class BeanPredicateUtil{

    /** Don't let anyone instantiate this class. */
    private BeanPredicateUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Equal predicate.
     * 
     * <p>
     * 用来指定 <code>T</code> 对象的 特定属性 <code>propertyName</code> equals 指定的 propertyValue
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            the property value
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.PredicateUtils#equalPredicate(Object)
     */
    public static <T, V> Predicate<T> equalPredicate(String propertyName,V propertyValue){
        return new BeanPredicate<T>(propertyName, PredicateUtils.equalPredicate(propertyValue));
    }

    /**
     * Contains predicate.
     * 
     * <p>
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用
     * {@link org.apache.commons.lang3.ArrayUtils#contains(Object[], Object) ArrayUtils.contains} 判断是否在 <code>values</code>数组中.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the property values
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.lang3.ArrayUtils#contains(Object[], Object)
     */
    @SafeVarargs
    public static <T, V> Predicate<T> containsPredicate(final String propertyName,final V...propertyValues){
        return new BeanPredicate<T>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return org.apache.commons.lang3.ArrayUtils.contains(propertyValues, propertyValue);
            }
        });
    }

    /**
     * Contains predicate.
     * 
     * <p>
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用{@link java.util.Collection#contains(Object)
     * Collection.contains} 判断是否在<code>values</code>集合中.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the property value list
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.util.Collection#contains(Object)
     */
    public static <T, V> Predicate<T> containsPredicate(final String propertyName,final Collection<V> propertyValueList){
        return new BeanPredicate<T>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return isNullOrEmpty(propertyValueList) ? false : propertyValueList.contains(propertyValue);
            }
        });
    }

    //**************************************************************************************************

    /**
     * Comparator predicate.
     * 
     * <p>
     * 拿<code>valueToCompare</code> 和 提取t对象的属性<code>propertyName</code>的值,进行比较(使用 {@link ComparatorUtils#naturalComparator()} 自然排序比较器)<br>
     * 注意,比较 <code><b>comparator.compare(valueToCompare, propertyValue)</b></code>
     * </p>
     * 
     * <h3>关于 {@link Criterion}:</h3>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * 
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * 
     * <tr valign="top">
     * <td>{@link Criterion#EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) == 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#LESS}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) < 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#GREATER}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) > 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#GREATER_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) >= 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#LESS_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) <= 0</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     * 
     * <h3>通常对于以下代码:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 10),
     *                 new User("张飞", 28),
     *                 new User("刘备", 32),
     *                 new User("刘备", 30),
     *                 new User("刘备", 10));
     * 
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(list, "name", new Predicate{@code <User>}(){
     * 
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getAge() {@code >} 20;
     *     }
     * });
     * 
     * </pre>
     * 
     * 你可以简化成:
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 10),
     *                 new User("张飞", 28),
     *                 new User("刘备", 32),
     *                 new User("刘备", 30),
     *                 new User("刘备", 10));
     * 
     * Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param valueToCompare
     *            the value to compare
     * @param criterion
     *            the criterion
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see ComparatorUtils#naturalComparator()
     * @see #comparatorPredicate(String, Comparable, Comparator, Criterion)
     * @since commons-collections 4
     */
    public static <T, V extends Comparable<? super V>> Predicate<T> comparatorPredicate(
                    String propertyName,
                    V valueToCompare,
                    Criterion criterion){
        return comparatorPredicate(propertyName, valueToCompare, ComparatorUtils.<V> naturalComparator(), criterion);
    }

    /**
     * Comparator predicate.
     * 
     * <p>
     * 拿<code>valueToCompare</code> 和 提取t对象的属性<code>propertyName</code>的值,进行比较(使用 <code>comparator</code> 比较器)<br>
     * 注意,比较 <code><b>comparator.compare(valueToCompare, propertyValue)</b></code>
     * </p>
     * 
     * <h3>关于 {@link Criterion}:</h3>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * 
     * <tr style="background-color:#ccccff">
     * <th align="left">字段</th>
     * <th align="left">说明</th>
     * </tr>
     * 
     * 
     * <tr valign="top">
     * <td>{@link Criterion#EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) == 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#LESS}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) < 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#GREATER}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) > 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#GREATER_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) >= 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#LESS_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) <= 0</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param valueToCompare
     *            the value to compare
     * @param comparator
     *            the comparator
     * @param criterion
     *            the criterion
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.functors.ComparatorPredicate
     * @since commons-collections 4
     */
    public static <T, V extends Comparable<? super V>> Predicate<T> comparatorPredicate(
                    String propertyName,
                    V valueToCompare,
                    Comparator<V> comparator,
                    Criterion criterion){
        return new BeanPredicate<T>(propertyName, new ComparatorPredicate<V>(valueToCompare, comparator, criterion));
    }
}
