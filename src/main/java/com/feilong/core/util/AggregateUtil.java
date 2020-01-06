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

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static java.math.BigDecimal.ZERO;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.NumberUtil;

/**
 * 专门用来统计数据的工具类.
 * 
 * <p>
 * 类似于sql里面的 统计函数 <a href="http://www.sqlcourse2.com/agg_functions.html">(Aggregate functions)</a>
 * </p>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "java.util.stream.Collectors"
 * @since 1.8.0
 */
//Aggregate Functions
public final class AggregateUtil{

    /** Don't let anyone instantiate this class. */
    private AggregateUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //-------------------------avg--------------------------------------

    /**
     * 算术平均值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 求的User list 里面 id属性 的平均值
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User(2L));
     * list.add(new User(5L));
     * list.add(new User(5L));
     * AggregateUtil.avg(list, "id", 2)
     * </pre>
     * 
     * <b>返回:</b> 4.00
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Iterable, String...)
     */
    public static <O> BigDecimal avg(Iterable<O> beanIterable,String propertyName,int scale){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return isNullOrEmpty(beanIterable) ? null : avg(beanIterable, toArray(propertyName), scale).get(propertyName);
    }

    /**
     * 算术平均值.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的 {@link LinkedHashMap},key是 <code>propertyNames</code>的元素,value是基于这个属性名称获得的值的平均值;key的顺序是依照 <code>propertyNames</code>元素的顺序
     * </li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 求的User list 里面 age 以及id属性 的平均值
     * </p>
     * 
     * <pre class="code">
     * User user1 = new User(2L);
     * user1.setAge(18);
     * 
     * User user2 = new User(3L);
     * user2.setAge(30);
     * 
     * List{@code <User>} list = toList(user1, user2);
     * Map{@code <String, BigDecimal>} map = AggregateUtil.avg(list, ConvertUtil.toArray("id", "age"), 2);
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": 2.5,
     * "age": 24
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param scale
     *            标度,小数的位数,四舍五入,用于 {@link java.math.BigDecimal#setScale(int, RoundingMode)}<br>
     *            如果为零或正数,则标度是小数点后的位数。<br>
     *            如果为负数,则将该数的非标度值乘以 10 的负 scale 次幂 (通常情况用不到负数的情况)
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果<code>propertyNames</code> 是null 抛出 {@link NullPointerException} 异常<br>
     *         如果<code>propertyNames</code> 有元素是null 抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Iterable, String...)
     */
    public static <O> Map<String, BigDecimal> avg(Iterable<O> beanIterable,String[] propertyNames,int scale){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }

        Map<String, BigDecimal> sumMap = sum(beanIterable, propertyNames);//先求和

        int size = IterableUtils.size(beanIterable);
        Map<String, BigDecimal> map = newLinkedHashMap(size);
        for (Map.Entry<String, BigDecimal> entry : sumMap.entrySet()){
            map.put(entry.getKey(), NumberUtil.getDivideValue(toBigDecimal(entry.getValue()), size, scale));
        }
        return map;
    }

    //--------------------------sum-------------------------------------

    /**
     * 总和,计算集合对象<code>beanIterable</code> 内指定的属性名 <code>propertyName</code> 值的总和.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果通过反射某个元素值是null,则使用默认值0代替,再进行累加</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User(2L));
     * list.add(new User(5L));
     * list.add(new User(5L));
     * 
     * LOGGER.info("" + AggregateUtil.sum(list, "id"));
     * </pre>
     * 
     * <b>返回:</b> 12
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 当你需要写这样的代码的时候,
     * </p>
     * 
     * <pre class="code">
     * 
     * protected Integer getCookieShoppingCartLinesQty(List{@code <CookieShoppingCartLine>} cartLineList){
     *     Integer qty = 0;
     *     <span style="color:green">//获取cookie中的购物车行集合</span>
     *     if ({@code null != cartLineList && cartLineList.size() > 0}){
     *         for (Iterator iterator = cartLineList.iterator(); iterator.hasNext();){
     *             CookieShoppingCartLine cookieShoppingCartLine = (CookieShoppingCartLine) iterator.next();
     *             qty += cookieShoppingCartLine.getQuantity();
     *         }
     *     }
     *     return qty;
     * }
     * </pre>
     * 
     * <p>
     * 你可以写成:
     * </p>
     * 
     * <pre class="code">
     * 
     * protected Integer getCookieShoppingCartLinesQty(List{@code <CookieShoppingCartLine>} cartLineList){
     *     return isNullOrEmpty(cartLineList) ? 0 : AggregateUtil.sum(cartLineList, "quantity").intValue();
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者 empty,返回 null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #sum(Iterable, String...)
     */
    public static <O> BigDecimal sum(Iterable<O> beanIterable,String propertyName){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        return sum(beanIterable, propertyName, null);
    }

    /**
     * 迭代<code>beanIterable</code>,提取 符合 <code>includePredicate</code>的元素的指定 <code>propertyName</code> 元素的值 ,累计总和.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果通过反射某个元素值是null,则使用默认值0代替,再进行累加</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list(条件是 id {@code >}10),id属性值的总和
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User(2L));
     * list.add(new User(50L));
     * list.add(new User(50L));
     * 
     * AggregateUtil.sum(list, "id", new Predicate{@code <User>}(){
     * 
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getId() {@code >} 10L;
     *     }
     * });
     * 
     * </pre>
     * 
     * <p>
     * <b>返回:</b> new BigDecimal(100L)
     * </p>
     * 
     * <p>
     * 当然这段代码,你还可以优化成:
     * </p>
     * 
     * <pre class="code">
     * Predicate{@code <Long>} predicate = new ComparatorPredicate{@code <Long>}(10L, ComparatorUtils.{@code <Long>} naturalComparator(), Criterion.LESS);
     * BigDecimal sum = AggregateUtil.sum(list, "id", new BeanPredicate{@code <User>}("id", predicate));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>beanIterable</code> 是null或者 empty,返回 null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>includePredicate</code> 是null,那么迭代所有的元素<br>
     *         如果<code>beanIterable</code>没有符合 <code>includePredicate</code>的元素,返回 <code>null</code>
     * @see #sum(Iterable, String[], Predicate)
     */
    public static <O> BigDecimal sum(Iterable<O> beanIterable,String propertyName,Predicate<O> includePredicate){
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        return sum(beanIterable, toArray(propertyName), includePredicate).get(propertyName);
    }

    /**
     * 求和,分别计算集合对象<code>beanIterable</code> 内指定的不同属性名 <code>propertyNames</code> 值的总和.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果通过反射某个元素值是null,则使用默认值0代替,再进行累加</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 在user list 中,分别统计 id属性以及age属性值总和
     * </p>
     * 
     * <pre class="code">
     * User user1 = new User(2L);
     * user1.setAge(18);
     * 
     * User user2 = new User(3L);
     * user2.setAge(30);
     * 
     * Map{@code <String, BigDecimal>} map = AggregateUtil.sum(toList(user1, user2), "id", "age");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": 5,
     * "age": 48
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果<code>propertyNames</code> 是null 抛出 {@link NullPointerException} 异常<br>
     *         如果<code>propertyNames</code> 有元素是null 抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Iterable, String[], Predicate)
     */
    public static <O> Map<String, BigDecimal> sum(Iterable<O> beanIterable,String...propertyNames){
        return sum(beanIterable, propertyNames, null);
    }

    /**
     * 迭代<code>beanIterable</code>,提取符合 <code>includePredicate</code>的元素的指定 <code>propertyNames</code> 元素的值 ,累计总和.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User user1 = new User(10L);
     * user1.setName("刘备");
     * user1.setAge(50);
     * 
     * User user2 = new User(20L);
     * user1.setName("关羽");
     * user2.setAge(50);
     * 
     * User user3 = new User(100L);
     * user3.setName("张飞");
     * user3.setAge(100);
     * 
     * List{@code <User>} list = toList(user1, user2, user3);
     * Map{@code <String, BigDecimal>} map = AggregateUtil.sum(list, ConvertUtil.toArray("id", "age"), new Predicate{@code <User>}(){
     * 
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return !"张飞".equals(user.getName());
     *     }
     * });
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": 30,
     * "age": 100
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果通过反射某个元素值是null,则使用默认值0代替,再进行累加<br>
     *         如果 <code>includePredicate</code> 是null,那么迭代所有的元素<br>
     *         如果<code>beanIterable</code>没有符合 <code>includePredicate</code>的元素,返回 <code>new LinkedHashMap</code>
     * @throws NullPointerException
     *             如果<code>propertyNames</code> 是null
     * @throws IllegalArgumentException
     *             果<code>propertyNames</code> 有元素 是null <br>
     */
    public static <O> Map<String, BigDecimal> sum(Iterable<O> beanIterable,String[] propertyNames,Predicate<O> includePredicate){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }
        Validate.noNullElements(propertyNames, "propertyNames can't be null/empty!");

        Map<String, BigDecimal> sumMap = newLinkedHashMap(IterableUtils.size(beanIterable));
        for (O obj : beanIterable){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }

            for (String propertyName : propertyNames){
                //如果通过反射某个元素值是null,则使用默认值0 代替
                BigDecimal addValue = NumberUtil.getAddValue(
                                defaultIfNull(sumMap.get(propertyName), 0),
                                defaultIfNull(PropertyUtil.<Number> getProperty(obj, propertyName), 0));
                sumMap.put(propertyName, addValue);
            }
        }
        return sumMap;
    }

    //---------------------------------------------------------------

    /**
     * 迭代<code>beanIterable</code>,取元素 <code>keyPropertyName</code> 的值为 key ,累计 <code>sumPropertyName</code> 属性值 为 value,返回 map.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 统计 user list 按照姓名分组, 累加每个人的 age 总和
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 20),
     *                 new User("关羽", 20),
     *                 new User("刘备", 20),
     *                 new User("刘备", 20));
     * 
     * Map{@code <String, BigDecimal>} map = AggregateUtil.groupSum(list, "name", "age");
     * 
     * </pre>
     * 
     * <b>assertThat:</b>
     * 
     * <pre class="code">
     * assertThat(
     *                 map,
     *                 allOf(//
     *                                 hasEntry("刘备", toBigDecimal(40)),
     *                                 hasEntry("张飞", toBigDecimal(20)),
     *                                 hasEntry("关羽", toBigDecimal(20))));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param keyPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param sumPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果通过反射某个元素值是null,则使用默认值0代替,再进行累加<br>
     *         如果 <code>keyPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>keyPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>sumPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>sumPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @since 1.13.2
     */
    public static <O, T> Map<T, BigDecimal> groupSum(Iterable<O> beanIterable,String keyPropertyName,String sumPropertyName){
        return groupSum(beanIterable, keyPropertyName, sumPropertyName, null);
    }

    /**
     * 迭代<code>beanIterable</code>,提取符合 <code>includePredicate</code>的元素,取元素 <code>keyPropertyName</code> 的值为 key ,累计
     * <code>sumPropertyName</code> 属性值 为 value,返回 map.
     * 
     * <p>
     * 统计 user list 所有 age 小于等于30的, 按照姓名分组, 累加每个人的 age 总和
     * </p>
     * 
     * <pre class="code">
     * 
     * Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.GREATER_OR_EQUAL);
     * 
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 20),
     *                 new User("关羽", 20),
     *                 new User("刘备", 20),
     *                 new User("刘备", 50),
     *                 new User("刘备", 20));
     * 
     * Map{@code <String, BigDecimal>} map = AggregateUtil.groupSum(list, "name", "age",comparatorPredicate);
     * 
     * </pre>
     * 
     * <b>assertThat:</b>
     * 
     * <pre class="code">
     * assertThat(
     *                 map,
     *                 allOf(//
     *                                 hasEntry("刘备", toBigDecimal(40)),
     *                                 hasEntry("张飞", toBigDecimal(20)),
     *                                 hasEntry("关羽", toBigDecimal(20))));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param keyPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param sumPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果通过反射某个元素值是null,则使用默认值0代替,再进行累加<br>
     *         如果 <code>includePredicate</code> 是null,那么迭代所有的元素<br>
     *         如果 <code>beanIterable</code>没有符合 <code>includePredicate</code>的元素,返回 <code>new LinkedHashMap</code>
     * 
     *         如果 <code>keyPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>keyPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>sumPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>sumPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @since 1.13.2
     */
    public static <O, T> Map<T, BigDecimal> groupSum(
                    Iterable<O> beanIterable,
                    String keyPropertyName,
                    String sumPropertyName,
                    Predicate<O> includePredicate){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }

        //---------------------------------------------------------------
        Validate.notBlank(keyPropertyName, "keyPropertyName can't be null/empty!");
        Validate.notBlank(sumPropertyName, "sumPropertyName can't be null/empty!");

        Map<T, BigDecimal> map = newLinkedHashMap();
        for (O obj : beanIterable){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }

            T keyPropertyValue = PropertyUtil.<T> getProperty(obj, keyPropertyName);
            BigDecimal value = toBigDecimal(defaultIfNull(PropertyUtil.<Number> getProperty(obj, sumPropertyName), ZERO));
            MapUtil.putSumValue(map, keyPropertyValue, value);
        }
        return map;
    }

    //---------------------------------------------------------------

    /**
     * 循环 <code>beanIterable</code>,统计 <code>propertyName</code> 的值出现的次数.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list,属性名字是name 的值的数量
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞"));
     * list.add(new User("关羽"));
     * list.add(new User("刘备"));
     * list.add(new User("刘备"));
     * 
     * Map{@code <String, Integer>} map = AggregateUtil.groupCount(list, "name");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "张飞": 1,
     * "关羽": 1,
     * "刘备": 2
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #groupCount(Iterable , String, Predicate)
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     */
    public static <O, T> Map<T, Integer> groupCount(Iterable<O> beanIterable,String propertyName){
        return groupCount(beanIterable, propertyName, null);
    }

    /**
     * 循环 <code>beanIterable</code>,只选择符合 <code>includePredicate</code>的对象,统计 <code>propertyName</code>的值出现的次数.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list(条件是 age {@code >} 30 的user),name属性值的数量
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 20));
     * list.add(new User("关羽", 30));
     * list.add(new User("刘备", 40));
     * list.add(new User("赵云", 50));
     * 
     * Map{@code <String, Integer>} map = AggregateUtil.groupCount(list, "name", new Predicate{@code <User>}(){
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getAge() {@code >} 30;
     *     }
     * });
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "刘备": 1,
     * "赵云": 1
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            只选择 符合 <code>includePredicate</code>的对象,如果是null 则统计集合中全部的元素
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>includePredicate</code> 是null,则统计集合中全部的元素<br>
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     */
    public static <O, T> Map<T, Integer> groupCount(Iterable<O> beanIterable,String propertyName,Predicate<O> includePredicate){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, Integer> map = newLinkedHashMap();
        for (O obj : beanIterable){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }
            MapUtil.putSumValue(map, PropertyUtil.<T> getProperty(obj, propertyName), 1);
        }
        return map;
    }

    //---------------------------------------------------------------

    /**
     * 循环 <code>beanIterable</code>,统计不同 <code>propertyNames</code>的不同值出现的次数.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>名字,子map的key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list,属性名字是name 的值的数量 以及age值的数量
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 20),
     *                 new User("关羽", 30),
     *                 new User("赵云", 50),
     *                 new User("刘备", 40),
     *                 new User("刘备", 30),
     *                 new User("赵云", 50));
     * 
     * Map{@code <String, Map<Object, Integer>>} map = AggregateUtil.groupCount(list, toArray("name", "age"));
     * 
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     *     {
     *         "age":         {
     *             "20": 1,
     *             "30": 2,
     *             "50": 2,
     *             "40": 1
     *         },
     *         "name":         {
     *             "张飞": 1,
     *             "关羽": 1,
     *             "赵云": 2,
     *             "刘备": 2
     *         }
     *     }
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyNames</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNames</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     *         如果 循环的<code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 循环的<code>propertyName</code> 是empty或者blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     * @since 1.10.6
     * @since 1.10.7 change param type from {@code String[] propertyNames} to {@code String...propertyNames} <br>
     *        change return type from {@code Map<String, Map<T, Integer>>} to {@code Map<String, Map<Object, Integer>>}
     */
    public static <O> Map<String, Map<Object, Integer>> groupCount(Iterable<O> beanIterable,String...propertyNames){
        return groupCount(beanIterable, propertyNames, (Predicate<O>) null);
    }

    /**
     * 循环 <code>beanIterable</code>,只选择符合 <code>includePredicate</code>的对象,分别统计 <code>propertyNames</code>的值出现的次数.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>名字,子map的key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list(条件是 age {@code >} 30 的user),name属性值的数量以及age 的数量
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 20),
     *                 new User("关羽", 30),
     *                 new User("赵云", 50),
     *                 new User("刘备", 40),
     *                 new User("刘备", 30),
     *                 new User("赵云", 50));
     * 
     * Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
     * Map{@code <String, Map<Object, Integer>>} map = AggregateUtil.groupCount(list, toArray("name", "age"), comparatorPredicate);
     * 
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     *     {
     *         "age":         {
     *             "50": 2,
     *             "40": 1
     *         },
     *         "name":         {
     *             "赵云": 2,
     *             "刘备": 1
     *         }
     *     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            只选择 符合 <code>includePredicate</code>的对象,如果是null 则统计集合中全部的元素
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyNames</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNames</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     *         如果 循环的<code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 循环的<code>propertyName</code> 是empty或者blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>includePredicate</code> 是null,则统计集合中全部的元素<br>
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     * @since 1.10.6
     * @since 1.10.7 change return type from {@code Map<String, Map<T, Integer>>} to {@code Map<String, Map<Object, Integer>>}
     */
    public static <O> Map<String, Map<Object, Integer>> groupCount(
                    Iterable<O> beanIterable,
                    String[] propertyNames,
                    Predicate<O> includePredicate){
        return groupCount(beanIterable, propertyNames, null, includePredicate);
    }

    /**
     * 循环 <code>beanIterable</code>,分别统计 <code>propertyNames</code>的值出现的次数,统计的时候支持使用
     * <code>propertyValueAndTransformerMap</code> 属性值的转换.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>名字,子map的key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list,name属性值的数量以及age 的数量,并且 age以范围区间统计
     * </p>
     * 
     * <pre class="code">
    List{@code <User>} list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("刘备", 32),
                        new User("刘备", 40),
                        new User("赵云", 51),
                        new User("赵云", 50));
    
        Transformer{@code <?, ?>} transformer = new Transformer{@code <Integer, String>}(){
    
            public String transform(Integer input){
                if ({@code input >= 50}){
                    return {@code ">=50"};
                }
                if ({@code input >= 30 && input < 50}){
                    return {@code ">=30&&<50"};
                }
                if ({@code input >= 20 && input < 30}){
                    return {@code ">=20&&<30"};
                }               
                throw new UnsupportedOperationException("value not support!");
            }
    
        };
    
        //---------------------------------------------------------------
        Map{@code <String, Transformer<Object, Object>>} propertyValueAndTransformerMap = toMap("age", (Transformer{@code <Object, Object>}) transformer);
    
        //---------------------------------------------------------------
    
        Map{@code <String, Map<Object, Integer>>} map = AggregateUtil
                        .groupCount(list, toArray("name", "age"), propertyValueAndTransformerMap);
    
        LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "name":         {
            "张飞": 1,
            "关羽": 1,
            "刘备": 2,
            "赵云": 2
        },
        "age":         {
            {@code ">=20&&<30"}: 1,
            {@code ">=30&&<50"}: 3,
            {@code ">=50"}: 2
        }
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueAndTransformerMap
     *            the property name value converter map
     * @return 如果 <code>propertyValueAndTransformerMap</code> 是null或者empty,方法等于 {@link #groupCount(Iterable, String[], Predicate)}<br>
     *         如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyNames</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNames</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     *         如果 循环的<code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 循环的<code>propertyName</code> 是empty或者blank,抛出 {@link IllegalArgumentException}<br>
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     * @since 1.10.7
     */
    public static <O> Map<String, Map<Object, Integer>> groupCount(
                    Iterable<O> beanIterable,
                    String[] propertyNames,
                    Map<String, Transformer<Object, Object>> propertyValueAndTransformerMap){
        return groupCount(beanIterable, propertyNames, propertyValueAndTransformerMap, null);
    }

    /**
     * 循环 <code>beanIterable</code>,只选择符合 <code>includePredicate</code>的对象,分别统计 <code>propertyNames</code>的值出现的次数,统计的时候支持使用
     * <code>propertyValueAndTransformerMap</code> 属性值的转换.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是<code>propertyName</code>名字,子map的key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 统计user list(条件是 age {@code >} 30 的user),name属性值的数量以及age 的数量,并且 age以范围区间统计
     * </p>
     * 
     * <pre class="code">
    List{@code <User>} list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("刘备", 32),
                        new User("刘备", 40),
                        new User("赵云", 51),
                        new User("赵云", 50));
    
        Transformer{@code <?, ?>} transformer = new Transformer{@code <Integer, String>}(){
    
            public String transform(Integer input){
                if ({@code input >= 50}){
                    return {@code ">=50"};
                }
                if ({@code input >= 30 && input < 50}){
                    return {@code ">=30&&<50"};
                }
                throw new UnsupportedOperationException("value not support!");
            }
    
        };
    
        //---------------------------------------------------------------
        Map{@code <String, Transformer<Object, Object>>} propertyValueAndTransformerMap = toMap("age", (Transformer{@code <Object, Object>}) transformer);
    
        Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
    
        //---------------------------------------------------------------
    
        Map{@code <String, Map<Object, Integer>>} map = AggregateUtil
                        .groupCount(list, toArray("name", "age"), propertyValueAndTransformerMap, comparatorPredicate);
    
        LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "name":         {
            "刘备": 2,
            "赵云": 2
        },
        "age":         {
            {@code ">=30&&<50"}: 2,
            {@code ">=50"}: 2
        }
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueAndTransformerMap
     *            the property name value converter map
     * @param includePredicate
     *            只选择 符合 <code>includePredicate</code>的对象,如果是null 则统计集合中全部的元素
     * @return 如果 <code>propertyValueAndTransformerMap</code> 是null或者empty,方法等于 {@link #groupCount(Iterable, String[], Predicate)}<br>
     *         如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyNames</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNames</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     *         如果 循环的<code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 循环的<code>propertyName</code> 是empty或者blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>includePredicate</code> 是null,则统计集合中全部的元素<br>
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     * @since 1.10.7
     */
    public static <O> Map<String, Map<Object, Integer>> groupCount(
                    Iterable<O> beanIterable,
                    String[] propertyNames,
                    Map<String, Transformer<Object, Object>> propertyValueAndTransformerMap,
                    Predicate<O> includePredicate){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }

        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");

        for (String propertyName : propertyNames){
            Validate.notBlank(propertyName, "propertyName can't be blank!");
        }

        //---------------------------------------------------------------

        Map<String, Map<Object, Integer>> resultMap = newLinkedHashMap(propertyNames.length);
        for (O bean : beanIterable){
            if (null != includePredicate && !includePredicate.evaluate(bean)){
                continue;
            }
            handlerForeachPropertyNames(propertyNames, propertyValueAndTransformerMap, resultMap, bean);
        }
        return resultMap;
    }

    //---------------------------------------------------------------

    /**
     * Handler foreach property names.
     *
     * @param <O>
     *            the generic type
     * @param propertyNames
     *            the property names
     * @param propertyNameValueConverterMap
     *            the property name value converter map
     * @param resultMap
     *            the result map
     * @param bean
     *            the bean
     * @since 1.10.7
     */
    private static <O> void handlerForeachPropertyNames(
                    String[] propertyNames,
                    Map<String, Transformer<Object, Object>> propertyNameValueConverterMap,
                    Map<String, Map<Object, Integer>> resultMap,
                    O bean){
        for (String propertyName : propertyNames){

            //取map,如果没有构造一个
            Map<Object, Integer> propertyNameGroupCountMap = defaultIfNull(
                            resultMap.get(propertyName),
                            new LinkedHashMap<Object, Integer>());

            Object value = convertValue(bean, propertyName, propertyNameValueConverterMap);
            MapUtil.putSumValue(propertyNameGroupCountMap, value, 1);

            //---------------------------------------------------------------

            resultMap.put(propertyName, propertyNameGroupCountMap);
        }
    }

    /**
     * Convert value.
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param obj
     *            the obj
     * @param propertyName
     *            the property name
     * @param propertyValueAndTransformerMap
     *            the property name value converter map
     * @return the string
     * @since 1.10.7
     */
    private static <O, T> Object convertValue(
                    O obj,
                    String propertyName,
                    Map<String, Transformer<Object, Object>> propertyValueAndTransformerMap){
        T value = PropertyUtil.<T> getProperty(obj, propertyName);

        if (isNullOrEmpty(propertyValueAndTransformerMap)){
            return value;
        }

        //---------------------------------------------------------------
        Transformer<Object, Object> transformer = propertyValueAndTransformerMap.get(propertyName);
        if (null == transformer){
            return value;
        }

        //---------------------------------------------------------------
        return transformer.transform(value);
    }
}
