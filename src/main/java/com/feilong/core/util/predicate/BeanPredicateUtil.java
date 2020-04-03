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

import static com.feilong.core.Validator.isNotNullOrEmpty;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.util.AggregateUtil;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.equator.IgnoreCaseEquator;

/**
 * 专门针对bean,提供的 BeanPredicateUtil.
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

    //---------------------------------------------------------------

    /**
     * 用来指定 <code>T</code> 对象的特定属性 <code>propertyName</code> equals 指定的 <code>propertyValue</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 在list中查找 名字是 关羽,并且 年龄是30 的user
     * </p>
     * 
     * <pre class="code">
     * 
     * User guanyu30 = new User("关羽", 30);
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 guanyu30);
     * 
     * Predicate{@code <User>} predicate = PredicateUtils
     *                 .andPredicate(BeanPredicateUtil.equalPredicate("name", "关羽"), BeanPredicateUtil.equalPredicate("age", 30));
     * 
     * assertEquals(guanyu30, CollectionsUtil.find(list, predicate));
     * 
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
     * @param propertyValue
     *            the property value
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.PredicateUtils#equalPredicate(Object)
     */
    public static <T, V> Predicate<T> equalPredicate(String propertyName,V propertyValue){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new BeanPredicate<>(propertyName, PredicateUtils.equalPredicate(propertyValue));
    }

    /**
     * 用来指定 <code>T</code> 对象的特定属性 <code>propertyName</code> equalsIgnoreCase 指定的 <code>propertyValue</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 在list中查找 name是 zhangfei(忽视大小写) 的user
     * </p>
     * 
     * <pre class="code">
     * User zhangfei1 = new User("zhangfei", 22);
     * User zhangfei2 = new User("zhangFei", 22);
     * User zhangfei3 = new User("Zhangfei", 22);
     * User zhangfeinull = new User((String) null, 22);
     * User guanyu = new User("guanyu", 25);
     * User liubei = new User("liubei", 30);
     * 
     * List{@code <User>} list = toList(zhangfei1, zhangfei2, zhangfei3, zhangfeinull, guanyu, liubei);
     * 
     * List{@code <User>} select = select(list, BeanPredicateUtil.{@code <User>} equalIgnoreCasePredicate("name", "zhangfei"));
     * 
     * assertThat(select, allOf(//
     *                 hasItem(zhangfei1),
     *                 hasItem(zhangfei2),
     *                 hasItem(zhangfei3),
     * 
     *                 not(hasItem(zhangfeinull)),
     *                 not(hasItem(guanyu)),
     *                 not(hasItem(liubei))
     * //
     * ));
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            the property value
     * @return 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.PredicateUtils#equalPredicate(Object)
     * @see com.feilong.core.util.equator.IgnoreCaseEquator#INSTANCE
     * @since 1.10.1
     */
    public static <T> Predicate<T> equalIgnoreCasePredicate(String propertyName,String propertyValue){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new BeanPredicate<>(propertyName, EqualPredicate.equalPredicate(propertyValue, IgnoreCaseEquator.INSTANCE));
    }

    /**
     * 用来判断一个对象指定的属性以及属性值是否相等.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 在list中查找 名字是 关羽,并且 年龄是30 的user
     * </p>
     * 
     * <pre class="code">
     * 
     * User guanyu30 = new User("关羽", 30);
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 guanyu30);
     * 
     * Predicate{@code <User>} predicate = PredicateUtils
     *                 .andPredicate(BeanPredicateUtil.equalPredicate("name", "关羽"), BeanPredicateUtil.equalPredicate("age", 30));
     * 
     * assertEquals(guanyu30, CollectionsUtil.find(list, predicate));
     * </pre>
     * 
     * <p>
     * 此时你可以优化成:
     * </p>
     * 
     * <pre class="code">
     * 
     * User guanyu30 = new User("关羽", 30);
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 guanyu30);
     * 
     * Map{@code <String, Object>} map = ConvertUtil.toMap("name", "关羽", "age", 30);
     * assertEquals(guanyu30, find(list, BeanPredicateUtil.{@code <User>} equalPredicate(map)));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param propertyNameAndPropertyValueMap
     *            属性和指定属性值对应的map,其中key是泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>propertyNameAndPropertyValueMap</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 是empty,抛出{@link IllegalArgumentException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 中有key是null,抛出{@link NullPointerException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 中有key是blank,抛出{@link IllegalArgumentException}<br>
     * @see #equalPredicate(String, Object)
     * @since 1.9.5
     */
    public static <T> Predicate<T> equalPredicate(Map<String, ?> propertyNameAndPropertyValueMap){
        Validate.notEmpty(propertyNameAndPropertyValueMap, "propertyNameAndPropertyValueMap can't be null!");

        @SuppressWarnings("unchecked")
        BeanPredicate<T>[] beanPredicates = ArrayUtil.newArray(BeanPredicate.class, propertyNameAndPropertyValueMap.size());

        int index = 0;
        for (Map.Entry<String, ?> entry : propertyNameAndPropertyValueMap.entrySet()){
            String propertyName = entry.getKey();
            Object propertyValue = entry.getValue();

            Validate.notBlank(propertyName, "propertyName can't be blank!");

            Array.set(beanPredicates, index, equalPredicate(propertyName, propertyValue));
            index++;
        }
        return PredicateUtils.allPredicate(beanPredicates);
    }

    /**
     * 构造属性与一个指定对象 <code>bean</code> 的一组属性的值 <code>propertyNames</code> 都相等的判断器.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 在list中查找 名字是 关羽,并且 年龄是30 的user
     * </p>
     * 
     * <pre class="code">
     * 
     * User guanyu30 = new User("关羽", 30);
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 guanyu30);
     * 
     * Predicate{@code <User>} predicate = PredicateUtils
     *                 .andPredicate(BeanPredicateUtil.equalPredicate("name", "关羽"), BeanPredicateUtil.equalPredicate("age", 30));
     * 
     * assertEquals(guanyu30, CollectionsUtil.find(list, predicate));
     * </pre>
     * 
     * <p>
     * 此时你可以优化成:
     * </p>
     * 
     * <pre class="code">
     * 
     * User guanyu30 = new User("关羽", 30);
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 guanyu30);
     * 
     * assertEquals(guanyu30, find(list, BeanPredicateUtil.equalPredicate(guanyu30, "name", "age")));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            the bean
     * @param propertyNames
     *            如果 <code>propertyNames</code> 是null或者empty 那么取所有属性值.
     * @return 如果 <code>bean</code> 是null,返回 {@link org.apache.commons.collections4.PredicateUtils#nullPredicate()}<br>
     *         否则 调用 {@link com.feilong.core.bean.PropertyUtil#describe(Object, String...)} 提取属性值,再调用 {@link #equalPredicate(Map)}
     * @see #equalPredicate(String, Object)
     * @see com.feilong.core.bean.PropertyUtil#describe(Object, String...)
     * @since 1.10.6
     */
    public static <T> Predicate<T> equalPredicate(T bean,String...propertyNames){
        if (null == bean){
            return PredicateUtils.nullPredicate();
        }
        Map<String, ?> propertyNameAndPropertyValueMap = PropertyUtil.describe(bean, propertyNames);
        return equalPredicate(propertyNameAndPropertyValueMap);
    }

    //---------------------------------------------------------------

    /**
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用
     * {@link org.apache.commons.lang3.ArrayUtils#contains(Object[], Object) ArrayUtils.contains} 判断是否在 <code>values</code>数组中.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
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
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new BeanPredicate<>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return org.apache.commons.lang3.ArrayUtils.contains(propertyValues, propertyValue);
            }
        });
    }

    /**
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用{@link java.util.Collection#contains(Object)
     * Collection.contains} 判断是否在<code>values</code>集合中.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
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
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new BeanPredicate<>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return isNotNullOrEmpty(propertyValueList) && propertyValueList.contains(propertyValue);
            }
        });
    }

    //---------------------------------------------------------------

    /**
     * 判断指定的值 <code>valueToCompare</code> <code>criterion</code> 提取的bean对象属性<code>propertyName</code>的值 .
     * 
     * <p>
     * 注意: 是参数 <code>valueToCompare</code> 和 属性值进行比较 <code>criterion</code>, BeanPredicateUtil.comparatorPredicate("age", 20,
     * Criterion.LESS) 表示提取所有比20 大的结果
     * </p>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>使用 {@link ComparatorUtils#naturalComparator()} 自然排序比较器.</li>
     * <li>比较 <code><b><span style="color:red">comparator.compare(valueToCompare, propertyValue)</span></b></code>.</li>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
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
     * <td>comparator.compare(valueToCompare, propertyValue) {@code <} 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#GREATER}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code >} 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#GREATER_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code >=} 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#LESS_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code <=} 0</td>
     * </tr>
     * 
     * </table>
     * </blockquote>
     * 
     * <h3>通常对于以下代码:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 大于20岁的人分个组
     * </p>
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
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return comparatorPredicate(propertyName, valueToCompare, ComparatorUtils.<V> naturalComparator(), criterion);
    }

    /**
     * 判断指定的值 <code>valueToCompare</code> <code>criterion</code> 提取的bean对象属性<code>propertyName</code>的值 .
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>使用 <code>comparator</code> 比较器</li>
     * <li>比较 <code><b><span style="color:red">comparator.compare(valueToCompare, propertyValue)</span></b></code>.</li>
     * <li>
     * 常用于解析集合,如 {@link CollectionsUtil#select(Iterable, Predicate) select},{@link CollectionsUtil#find(Iterable, Predicate) find},
     * {@link CollectionsUtil#selectRejected(Iterable, Predicate) selectRejected},
     * {@link CollectionsUtil#group(Iterable, String, Predicate) group},
     * {@link AggregateUtil#groupCount(Iterable, String, Predicate) groupCount},
     * {@link AggregateUtil#sum(Iterable, String, Predicate) sum} 等方法.
     * </li>
     * </ol>
     * </blockquote>
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
     * <td>comparator.compare(valueToCompare, propertyValue) {@code <} 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#GREATER}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code >} 0</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@link Criterion#GREATER_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code >=} 0</td>
     * </tr>
     * 
     * <tr valign="top">
     * <td>{@link Criterion#LESS_OR_EQUAL}</td>
     * <td>comparator.compare(valueToCompare, propertyValue) {@code <=} 0</td>
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
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return new BeanPredicate<>(propertyName, new ComparatorPredicate<V>(valueToCompare, comparator, criterion));
    }
}
