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

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.NumberUtil;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;

/**
 * 专门用来统计数据的.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public final class StatisticsUtil{

    /** Don't let anyone instantiate this class. */
    private StatisticsUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //***********************************avg*************************************************************

    /**
     * 算术平均值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(2L));
     * list.add(new User(5L));
     * list.add(new User(5L));
     * StatisticsUtil.avg(list, "id", 2)
     * </pre>
     * 
     * 返回: 4.00
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param scale
     *            平均数值的精度
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Collection, String...)
     */
    public static <O> BigDecimal avg(Collection<O> objectCollection,String propertyName,int scale){
        return avg(objectCollection, ConvertUtil.toArray(propertyName), scale).get(propertyName);
    }

    /**
     * 算术平均值.
     * 
     * <p>
     * 返回的 {@link LinkedHashMap},key是 <code>propertyNames</code>的元素,value是基于这个属性名称获得的值的平均值;key的顺序是依照 <code>propertyNames</code>元素的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user1 = new User(2L);
     * user1.setAge(18);
     * 
     * User user2 = new User(3L);
     * user2.setAge(30);
     * 
     * List{@code <User>} list = toList(user1, user2);
     * Map{@code <String, BigDecimal>} map = StatisticsUtil.avg(list, ConvertUtil.toArray("id", "age"), 2);
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回:
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
     * @param objectCollection
     *            the object collection
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param scale
     *            平均数值的精度
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果<code>propertyNames</code> 是null 抛出 {@link NullPointerException} 异常<br>
     *         如果<code>propertyNames</code> 有元素 是null 抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Collection, String...)
     */
    public static <O> Map<String, BigDecimal> avg(Collection<O> objectCollection,String[] propertyNames,int scale){
        Map<String, BigDecimal> sumMap = sum(objectCollection, propertyNames);

        int size = objectCollection.size();
        Map<String, BigDecimal> map = MapUtil.newLinkedHashMap(size);
        for (Map.Entry<String, BigDecimal> entry : sumMap.entrySet()){
            map.put(entry.getKey(), NumberUtil.getDivideValue(toBigDecimal(entry.getValue()), size, scale));
        }
        return map;
    }

    //***********************************sum*************************************************************
    /**
     * 总和,计算集合对象<code>objectCollection</code> 内指定的属性名 <code>propertyNames</code> 值的总和.
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user1 = new User(2L);
     * user1.setAge(18);
     * 
     * User user2 = new User(3L);
     * user2.setAge(30);
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(user1);
     * list.add(user2);
     * 
     * Map{@code <String, BigDecimal>} map = StatisticsUtil.sum(list, "id", "age");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回:
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
     * @param objectCollection
     *            the object collection
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果<code>propertyNames</code> 是null 抛出 {@link NullPointerException} 异常<br>
     *         如果<code>propertyNames</code> 有元素 是null 抛出 {@link IllegalArgumentException}<br>
     * @see #sum(Collection, String[], Predicate)
     */
    public static <O> Map<String, BigDecimal> sum(Collection<O> objectCollection,String...propertyNames){
        return sum(objectCollection, propertyNames, null);
    }

    /**
     * 迭代<code>objectCollection</code>,提取符合 <code>includePredicate</code>的元素 的指定 <code>propertyNames</code> 元素的值 ,累计总和.
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
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
     * Map{@code <String, BigDecimal>} map = StatisticsUtil.sum(list, ConvertUtil.toArray("id", "age"), new Predicate{@code <User>}(){
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
     * 返回:
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
     * @param objectCollection
     *            the object collection
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果<code>propertyNames</code> 是null 抛出 {@link NullPointerException} 异常<br>
     *         如果<code>propertyNames</code> 有元素 是null 抛出 {@link IllegalArgumentException}<br>
     */
    public static <O> Map<String, BigDecimal> sum(Collection<O> objectCollection,String[] propertyNames,Predicate<O> includePredicate){
        if (isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.noNullElements(propertyNames, "propertyNames can't be null/empty!");

        Map<String, BigDecimal> sumMap = MapUtil.newLinkedHashMap(objectCollection.size());
        for (O obj : objectCollection){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }

            for (String propertyName : propertyNames){
                //如果通过反射某个元素值是null,则使用默认值0 代替
                BigDecimal addValue = NumberUtil.getAddValue(
                                ObjectUtils.defaultIfNull(sumMap.get(propertyName), 0),
                                ObjectUtils.defaultIfNull(PropertyUtil.<Number> getProperty(obj, propertyName), 0));
                sumMap.put(propertyName, addValue);
            }
        }
        return sumMap;
    }

    /**
     * 总和,计算集合对象<code>objectCollection</code> 内指定的属性名 <code>propertyName</code> 值的总和.
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(2L));
     * list.add(new User(5L));
     * list.add(new User(5L));
     * 
     * LOGGER.info("" + StatisticsUtil.sum(list, "id"));
     * </pre>
     * 
     * 返回: 12
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * 当你需要写这样的代码的时候,
     * 
     * <pre class="code">
     * 
     * protected Integer getCookieShoppingCartLinesQty(List{@code <CookieShoppingCartLine>} cartLineList){
     *     Integer qty = 0;
     *     //获取cookie中的购物车行集合
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
     * 你可以写成:
     * 
     * <pre class="code">
     * 
     * protected Integer getCookieShoppingCartLinesQty(List{@code <CookieShoppingCartLine>} cartLineList){
     *     return isNullOrEmpty(cartLineList) ? 0 : StatisticsUtil.sum(cartLineList, "quantity").intValue();
     * }
     * </pre>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectCollection</code> 是null或者 empty,返回 null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #sum(Collection, String...)
     */
    public static <O> BigDecimal sum(Collection<O> objectCollection,String propertyName){
        return sum(objectCollection, propertyName, null);
    }

    /**
     * 迭代<code>objectCollection</code>,提取 符合 <code>includePredicate</code>的元素 的指定 <code>propertyName</code> 元素的值 ,累计总和..
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(2L));
     * list.add(new User(50L));
     * list.add(new User(50L));
     * 
     * assertEquals(new BigDecimal(100L), StatisticsUtil.sum(list, "id", new Predicate{@code <User>}(){
     * 
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getId() {@code >} 10L;
     *     }
     * }));
     * 
     * </pre>
     * 
     * <p>
     * 当然这段代码,你还可以优化成:
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(2L));
     * list.add(new User(50L));
     * list.add(new User(50L));
     * 
     * Predicate{@code <Long>} predicate = new ComparatorPredicate{@code <Long>}(10L, ComparatorUtils.{@code <Long>} naturalComparator(), Criterion.LESS);
     * BigDecimal sum = StatisticsUtil.sum(list, "id", new BeanPredicate{@code <User>}("id", predicate));
     * assertEquals(new BigDecimal(100L), sum);
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>objectCollection</code> 是null或者 empty,返回 null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #sum(Collection, String[], Predicate)
     */
    public static <O> BigDecimal sum(Collection<O> objectCollection,String propertyName,Predicate<O> includePredicate){
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        return sum(objectCollection, toArray(propertyName), includePredicate).get(propertyName);
    }

    //***********************************************************************************************

    /**
     * 循环 <code>objectCollection</code>,统计 <code>propertyName</code> 的值出现的次数.
     * 
     * <p>
     * 返回的{@link LinkedHashMap},key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 <code>objectCollection</code> <code>propertyName</code>的值的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User("张飞"));
     * list.add(new User("关羽"));
     * list.add(new User("刘备"));
     * list.add(new User("刘备"));
     * 
     * Map{@code <String, Integer>} map = StatisticsUtil.groupCount(list, "name");
     * LOGGER.info(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
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
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectCollection</code> isNullOrEmpty ,返回 {@link Collections#emptyMap()}; <br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #groupCount(Collection , String, Predicate)
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     */
    public static <T, O> Map<T, Integer> groupCount(Collection<O> objectCollection,String propertyName){
        return groupCount(objectCollection, propertyName, null);
    }

    /**
     * 循环 <code>objectCollection</code>,只选择符合 <code>includePredicate</code>的对象,统计 <code>propertyName</code>的值出现的次数.
     * 
     * <p>
     * 返回的{@link LinkedHashMap},key是<code>propertyName</code>对应的值,value是该值出现的次数;<br>
     * 顺序是 objectCollection <code>propertyName</code>的值的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User("张飞", 20));
     * list.add(new User("关羽", 30));
     * list.add(new User("刘备", 40));
     * list.add(new User("赵云", 50));
     * 
     * Map{@code <String, Integer>} map = StatisticsUtil.groupCount(list, "name", new Predicate{@code <User>}(){
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getAge() {@code >} 30;
     *     }
     * });
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
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
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            只选择 符合 <code>includePredicate</code>的对象,如果是null 则统计集合中全部的Object
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see org.apache.commons.collections4.CollectionUtils#getCardinalityMap(Iterable)
     */
    public static <T, O> Map<T, Integer> groupCount(Collection<O> objectCollection,String propertyName,Predicate<O> includePredicate){
        if (isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, Integer> map = new LinkedHashMap<T, Integer>();
        for (O obj : objectCollection){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }
            MapUtil.putSumValue(map, PropertyUtil.<T> getProperty(obj, propertyName), 1);
        }
        return map;
    }

    /**
     * 指定一个<code>map</code>,指定特定的keys,取得其中的 value 最小值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * 
     * map.put("a", 3007);
     * map.put("b", 3001);
     * map.put("c", 3002);
     * map.put("d", 3003);
     * map.put("e", 3004);
     * map.put("f", 3005);
     * map.put("g", -1005);
     * 
     * LOGGER.info("" + MapUtil.getMinValue(map, "a", "b", "d", "g", "m"));
     * </pre>
     * 
     * 返回:
     * -1005
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            指定一个map
     * @param keys
     *            指定特定的key
     * @return 如果 <code>map</code> 是null或者empty,返回 null;<br>
     *         如果 <code>keys</code> 是null或者empty,返回<code>map</code>所有value的最小值<br>
     *         如果循环的 key不在map key里面,则返回的map中忽略该key,并输出warn level log<br>
     *         如果 keys 中的所有的key 都不在 map 中出现 ,那么返回null
     * @see MapUtil#getSubMap(Map, Object...)
     * @see java.util.Collections#min(Collection)
     */
    @SafeVarargs
    public static <K, T extends Number & Comparable<? super T>> T getMinValue(Map<K, T> map,K...keys){
        Map<K, T> subMap = MapUtil.getSubMap(map, keys);
        return isNullOrEmpty(subMap) ? null : Collections.min(subMap.values()); //注意 Number本身 没有实现Comparable接口
    }
}
