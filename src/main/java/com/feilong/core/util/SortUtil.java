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
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;
import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.lang3.Validate;

import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.core.util.comparator.ComparatorUtil;
import com.feilong.core.util.comparator.PropertyComparator;

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
 * 由于这些方法是 void类型的,通常我们需要写2至3行代码
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

    //---------------------------------------------------------------
    /**
     * 对 数组 <code>arrays</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * sortArray(toArray(5, 10, 3, 2)  =   [2,3,5,10]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>以前代码需要写成:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * public static String toSalesPropertiesIdsJson(Long...itemPropertiesIdLongs){
     *     Arrays.sort(itemPropertiesIdLongs);
     *     return JsonUtil.format(itemPropertiesIdLongs, 0, 0);
     * }
     * 
     * </pre>
     * 
     * 现在可以重构成:
     * 
     * <pre class="code">
     * 
     * public static String toSalesPropertiesIdsJson(Long...itemPropertiesIdLongs){
     *     return JsonUtil.format(sortArray(itemPropertiesIdLongs), 0, 0);
     * }
     * </pre>
     * 
     * <b>再如:</b>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 得到默认分类,目前是最小的</span>
     * private Long getDefaultCategoryId(Long[] categoriesIds){
     *     Arrays.sort(categoriesIds);
     *     return categoriesIds[0];
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 得到默认分类,目前是最小的</span>
     * private Long getDefaultCategoryId(Long[] categoriesIds){
     *     return sortArray(categoriesIds)[0];
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return 如果 <code>array</code> 是null,返回 empty array<br>
     * @see java.util.Arrays#sort(Object[])
     * @since 1.8.7 change T... to T[]
     */
    public static <T> T[] sortArray(T[] arrays){//此处定义为 T[] 而不是 T...,为了避免 jdk8以下的版本  sort(strs, fixedOrderComparator); 编译不通过
        if (null == arrays){
            return toArray();
        }
        Arrays.sort(arrays);
        return arrays;
    }

    /**
     * 对 数组 <code>arrays</code>使用 <code>comparator</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 对字符串数组先按照长度比较,如果长度相等,那么再按照字母比较
     * </p>
     * 
     * <pre class="code">
     * 
     * String[] arrays = { "almn", "fba", "cba" };
     * 
     * Comparator{@code <String>} comparator = new Comparator{@code <String>}(){
     * 
     *     &#64;Override
     *     public int compare(String s1,String s2){
     *         Integer length = s1.length();
     *         Integer length2 = s2.length();
     * 
     *         <span style="color:green">//先判断长度,长度比较</span>
     *         int compareTo = length.compareTo(length2);
     * 
     *         <span style="color:green">//如果长度相等,那么比较自己本身的顺序</span>
     *         if (0 == compareTo){
     *             compareTo = s1.compareTo(s2);
     *         }
     *         return compareTo;
     *     }
     * };
     * sortArray(arrays, comparator);
     * 
     * assertArrayEquals(toArray("cba", "fba", "almn"), arrays);
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @param comparators
     *            the comparators
     * @return 如果 <code>array</code> 是null,返回 empty array<br>
     *         如果 <code>comparators</code> 是null或者empty,直接返回 <code>arrays</code><br>
     * @see java.util.Arrays#sort(Object[], Comparator)
     * @since 1.8.2 change to varargs parameter comparator
     * @since 1.8.7 change method name
     */
    @SafeVarargs
    public static <T> T[] sortArray(T[] arrays,Comparator<T>...comparators){
        if (null == arrays){
            return toArray();
        }
        if (isNullOrEmpty(comparators)){
            return arrays;
        }
        Arrays.sort(arrays, toComparator(comparators));
        return arrays;
    }

    //---------------------------------------------------------------

    /**
     * 对 集合 <code>list</code> 进行排序.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>{@link java.util.Collections#sort(List) Collections.sort} 底层就是调用的是 {@link java.util.Arrays#sort(Object[]) Arrays.sort}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * sortList(toList(5, 10, 3, 2))       = [2,3,5,10]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @see java.util.Collections#sort(List)
     * @since 1.8.7 change method name
     */
    public static <T extends Comparable<? super T>> List<T> sortList(List<T> list){
        if (null == list){
            return emptyList();
        }
        Collections.sort(list);
        return list;
    }

    //---------------------------------------------------------------
    /**
     * 对 集合 <code>list</code> 按照指定的固定顺序 <code>fixedOrderItems</code> 进行排序.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>默认使用的是 {@link UnknownObjectBehavior#AFTER} ,不在指定固定顺序的元素将排在后面</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * assertThat(
     *                 sortListByFixedOrderArray(toList("张飞", "关羽", "刘备"), toArray("刘备", "张飞", "关羽")), //
     *                 contains("刘备", "张飞", "关羽"));
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * try{
     *     Collections.sort(
     *                     list,
     *                     new FixedOrderComparator<>(
     *                                     StoPropertyConstants.PRPT_ITEM_HYPELAUNCH,
     *                                     StoPropertyConstants.PRPT_ITEM_MIADIDAS_VALUE_EN,
     *                                     StoPropertyConstants.PRPT_ITEM_PRESONALLZATION_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_PERSALES_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_VIP_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_COMINGSOON_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_DISCOUNT_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_NORMAL_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_NOSALE));
     * }catch (Exception e){
     *     LOGGER.error("itemType sort error:{},itemType:{}", e, JsonUtil.format(itemType));
     * }
     * 
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * com.feilong.core.util.SortUtil.sortListByFixedOrderArray(
     *                 list,
     *                 StoPropertyConstants.PRPT_ITEM_HYPELAUNCH,
     *                 StoPropertyConstants.PRPT_ITEM_MIADIDAS_VALUE_EN,
     *                 StoPropertyConstants.PRPT_ITEM_PRESONALLZATION_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_PERSALES_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_VIP_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_COMINGSOON_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_DISCOUNT_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_NORMAL_CODE,
     *                 StoPropertyConstants.PRPT_ITEM_NOSALE);
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param fixedOrderItems
     *            the fixed order items
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>list</code> 是empty,返回 <code>list</code><br>
     *         如果 <code>fixedOrderItems</code> 是null或者是 empty,返回 <code>list</code><br>
     * @see java.util.Collections#sort(List)
     * @since 1.14.3
     */
    @SafeVarargs
    public static <T extends Comparable<? super T>> List<T> sortListByFixedOrderArray(List<T> list,T...fixedOrderItems){
        return sortListByFixedOrderList(list, toList(fixedOrderItems));
    }

    /**
     * 对 集合 <code>list</code> 按照指定的固定顺序 <code>fixedOrderItemList</code> 进行排序.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>默认使用的是 {@link UnknownObjectBehavior#AFTER} ,不在指定固定顺序的元素将排在后面</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * assertThat(
     *                 sortListByFixedOrderList(toList("张飞", "关羽", "刘备"), toList("刘备", "张飞", "关羽")), //
     *                 contains("刘备", "张飞", "关羽"));
     * </pre>
     * 
     * </blockquote>
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * try{
     *     Collections.sort(
     *                     list,
     *                     new FixedOrderComparator<>(
     *                                     StoPropertyConstants.PRPT_ITEM_HYPELAUNCH,
     *                                     StoPropertyConstants.PRPT_ITEM_MIADIDAS_VALUE_EN,
     *                                     StoPropertyConstants.PRPT_ITEM_PRESONALLZATION_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_PERSALES_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_VIP_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_COMINGSOON_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_DISCOUNT_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_NORMAL_CODE,
     *                                     StoPropertyConstants.PRPT_ITEM_NOSALE));
     * }catch (Exception e){
     *     LOGGER.error("itemType sort error:{},itemType:{}", e, JsonUtil.format(itemType));
     * }
     * 
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * com.feilong.core.util.SortUtil.sortListByFixedOrderList(
     *                 list,
     *                 toList(
     *                                 StoPropertyConstants.PRPT_ITEM_HYPELAUNCH,
     *                                 StoPropertyConstants.PRPT_ITEM_MIADIDAS_VALUE_EN,
     *                                 StoPropertyConstants.PRPT_ITEM_PRESONALLZATION_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_PERSALES_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_VIP_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_COMINGSOON_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_DISCOUNT_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_NORMAL_CODE,
     *                                 StoPropertyConstants.PRPT_ITEM_NOSALE));
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param fixedOrderItemList
     *            the fixed order item list
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>list</code> 是empty,返回 <code>list</code><br>
     *         如果 <code>fixedOrderItemList</code> 是null或者是 empty,返回 <code>list</code><br>
     * @see java.util.Collections#sort(List)
     * @see com.feilong.core.util.comparator.ComparatorUtil#buildFixedOrderComparator(List)
     * @since 1.14.3
     */
    public static <T extends Comparable<? super T>> List<T> sortListByFixedOrderList(List<T> list,List<T> fixedOrderItemList){
        if (null == list){
            return emptyList();
        }
        if (isNullOrEmpty(list)){
            return list;
        }
        if (isNullOrEmpty(fixedOrderItemList)){
            return list;
        }
        return sortList(list, ComparatorUtil.buildFixedOrderComparator(fixedOrderItemList));
    }

    //---------------------------------------------------------------

    /**
     * 对集合 <code>list</code>,使用指定的 <code>comparators</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将 user list 按照 id进行排序
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User(12L, 18));
     * list.add(new User(2L, 36));
     * list.add(new User(5L, 22));
     * list.add(new User(1L, 8));
     * 
     * SortUtil.sortList(list, new PropertyComparator{@code <User>}("id"));
     * LOGGER.debug(JsonUtil.format(list));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [
     *  {"id": 1,"age": 8},
     *  {"id": 2,"age": 36},
     *  {"id": 5,"age": 22},
     *  {"id": 12,"age": 18}
     * ]
     * </pre>
     * 
     * <p>
     * 当然对于上述示例,你可以直接调用:
     * </p>
     * 
     * <pre class="code">
     * {@link #sortListByPropertyNamesValue(List, String...) SortUtil.sortListByPropertyNamesValue(list, "id");}
     * </pre>
     * 
     * 
     * <p>
     * <b>我们再来个复杂点的例子:</b> 将 user list 按照 "刘备" 排在 "关羽" 前面 进行排序,如果名字相同再按照 age进行排序
     * </p>
     * 
     * <pre class="code">
     * User guanyu = new User("关羽", 30);
     * 
     * User liubei60 = new User("刘备", 60);
     * User liubei25 = new User("刘备", 25);
     * User liubei30 = new User("刘备", 30);
     * User liubei10 = new User("刘备", 10);
     * 
     * String[] names = { "刘备", "关羽" };
     * List{@code <User>} list = CollectionsUtil.select(toList(liubei60, liubei30, liubei10, guanyu, liubei25), "name", names);
     * sortList(
     *                 list, //
     *                 new PropertyComparator{@code <User>}("name", new FixedOrderComparator{@code <>}(names)),
     *                 new PropertyComparator{@code <User>}("age"));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * assertThat(list, contains(liubei10, liubei25, liubei30, liubei60, guanyu));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param list
     *            the list
     * @param comparators
     *            the comparators
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>comparators</code> 是null或者empty,直接返回 <code>list</code><br>
     *         如果 <code>comparators length ==1</code>,取 comparators[0]做排序; <br>
     *         如果 {@code comparators length > 1},转成 {@link ComparatorUtils#chainedComparator(Comparator...)}排序;
     * @see java.util.Collections#sort(List, Comparator)
     * @since 1.8.2
     * @since 1.8.7 change method name
     */
    @SafeVarargs
    public static <O> List<O> sortList(List<O> list,Comparator<O>...comparators){
        if (null == list){
            return emptyList();
        }

        if (isNullOrEmpty(comparators)){
            return list;
        }
        Collections.sort(list, toComparator(comparators));
        return list;
    }

    /**
     * 如果 <code>comparators length ==1</code>,返回 comparators[0]; 否则返回 {@link ComparatorUtils#chainedComparator(Comparator...)};
     *
     * @param <O>
     *            the generic type
     * @param comparators
     *            the comparators
     * @return the comparator
     * @since 1.8.2
     */
    @SafeVarargs
    private static <O> Comparator<O> toComparator(Comparator<O>...comparators){
        return 1 == comparators.length ? comparators[0] : ComparatorUtils.chainedComparator(comparators);
    }

    //---------------------------------------------------------------

    /**
     * 对集合 <code>list</code>,按照指定属性的值(组合)进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将user list 先按照 id 再按照 age 进行排序
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User(12L, 18));
     * list.add(new User(2L, 36));
     * list.add(new User(2L, 2));
     * list.add(new User(2L, 30));
     * list.add(new User(1L, 8));
     * 
     * SortUtil.sortListByPropertyNamesValue(list, "id", "age");
     * 
     * LOGGER.debug(JsonUtil.formatWithIncludes(list, "id", "age"));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [
     *  {"id": 1,"age": 8},
     *  {"id": 2,"age": 2},
     *  {"id": 2,"age": 30},
     *  {"id": 2,"age": 36},
     *  {"id": 12,"age": 18}
     * ]
     * </pre>
     * 
     * 你还可以
     * 
     * <p>
     * <b>场景:</b> 将user list 先按照 id desc 再按照 age asc 进行排序
     * </p>
     * 
     * <pre class="code">
     * User id12_age18 = new User(12L, 18);
     * User id1_age8 = new User(1L, 8);
     * User id2_age30 = new User(2L, 30);
     * User id2_age2 = new User(2L, 2);
     * User id2_age36 = new User(2L, 36);
     * List{@code <User>} list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);
     * 
     * sortListByPropertyNamesValue(list, "id desc", "age");
     * 
     * assertThat(list, contains(id12_age18, id2_age2, id2_age30, id2_age36, id1_age8));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param list
     *            the list
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
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyNames</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNames</code> 是empty ,或者有 null元素
     * @see BeanComparatorUtil#chainedComparator(String...)
     * @see org.apache.commons.collections4.ComparatorUtils#chainedComparator(java.util.Comparator...)
     * @see #sortList(List, Comparator...)
     * @since 1.8.7 change name
     */
    public static <O> List<O> sortListByPropertyNamesValue(List<O> list,String...propertyNameAndOrders){
        if (null == list){
            return emptyList();
        }
        Validate.notEmpty(propertyNameAndOrders, "propertyNameAndOrders can't be null/empty!");
        Validate.noNullElements(propertyNameAndOrders, "propertyNameAndOrders:[%s] has empty value", propertyNameAndOrders);

        Comparator<O> comparator = BeanComparatorUtil.chainedComparator(propertyNameAndOrders);
        return sortList(list, comparator);
    }

    //---------------------------------------------------------------

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将user list中 "刘备" 排在 "关羽"前面
     * </p>
     * 
     * <pre class="code">
     * User zhangfei = new User("张飞", 23);
     * User guanyu = new User("关羽", 30);
     * User liubei = new User("刘备", 25);
     * List{@code <User>} list = toList(zhangfei, guanyu, liubei);
     * 
     * List{@code <User>} resultList = CollectionsUtil.select(list, "name", "刘备", "关羽");
     * Collections.sort(resultList, new PropertyComparator{@code <User>}("name", new FixedOrderComparator{@code <>}("刘备", "关羽")));
     * </pre>
     * 
     * <p>
     * 此时你可以直接调用:
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} resultList = CollectionsUtil.select(list, "name", "刘备", "关羽");
     * SortUtil.sortListByFixedOrderPropertyValueArray(resultList, "name", "刘备", "关羽"));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * assertThat(resultList, contains(liubei, guanyu));
     * </pre>
     * 
     * </blockquote>
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
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null,或者 <code>propertyValues</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, Object...)
     * @see #sortList(List, Comparator...)
     * @since 1.8.7 change method name
     */
    @SafeVarargs
    public static <O, V> List<O> sortListByFixedOrderPropertyValueArray(List<O> list,String propertyName,V...propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Comparator<O> propertyComparator = BeanComparatorUtil.propertyComparator(propertyName, propertyValues);
        return sortList(list, propertyComparator);
    }

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将user list中 "刘备" 排在 "关羽"前面
     * </p>
     * 
     * <pre class="code">
     * 
     * User zhangfei = new User("张飞", 23);
     * User guanyu = new User("关羽", 30);
     * User liubei = new User("刘备", 25);
     * List{@code <User>} list = toList(zhangfei, guanyu, liubei);
     * 
     * List{@code <User>} returnList = CollectionsUtil.select(list, "name", toList("刘备", "关羽"));
     * returnList = sortListByFixedOrderPropertyValueList(returnList, "name", toList("刘备", "关羽"));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * assertThat(returnList, contains(liubei, guanyu));
     * </pre>
     * 
     * </blockquote>
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
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, List)
     * @see #sortList(List, Comparator...)
     * @since 1.8.7 change method name
     */
    public static <O, V> List<O> sortListByFixedOrderPropertyValueList(List<O> list,String propertyName,List<V> propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Comparator<O> propertyComparator = BeanComparatorUtil.propertyComparator(propertyName, propertyValues);
        return sortList(list, propertyComparator);
    }

    //---------------------------------------------------------------

    /**
     * 按照key asc顺序排序.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * <li>该方法使用了 {@link PropertyComparator},允许 null key,<b>null key排在最前面</b></li>
     * <li>
     * 如果直接使用 {@link java.util.TreeMap#TreeMap(Map)},TreeMap不允许 key是null,如果有key是null,那么将会抛出{@link NullPointerException}<br>
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put(null, 1345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortMapByKeyAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * null: 1345,
     * "a": 123,
     * "b": 8,
     * "c": 345
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see java.util.TreeMap#TreeMap(Map)
     * @since 1.8.0 move from MapUtil
     * @since 1.8.7 change method name
     */
    public static <K, V> Map<K, V> sortMapByKeyAsc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sortMap(map, new PropertyComparator<Map.Entry<K, V>>("key"));
    }

    /**
     * 按照key desc 倒序排序.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * <li>该方法使用了 {@link PropertyComparator},允许 null key,<b>null key排在最后面</b></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put(null, 88);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortMapByKeyDesc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "c": 345,
     * "b": 8,
     * "a": 123,
     * null: 88
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see #sortMap(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     * @since 1.8.7 change method name
     */
    public static <K, V> Map<K, V> sortMapByKeyDesc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sortMap(map, new ReverseComparator<Map.Entry<K, V>>(new PropertyComparator<Map.Entry<K, V>>("key")));
    }

    //---------------------------------------------------------------

    /**
     * 按照key 指定名字顺序排序.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <>}();
     * 
     * map.put("DE", 99);
     * map.put("L", 3428);
     * map.put("O", 13);
     * map.put("UN", 17);
     * map.put("S", 6);
     * 
     * //L-上市,S-暂停,DE-终止上市,UN-未上市
     * Map{@code <String, Integer>} sortByKeyAsc = sortMapByKeyFixOrder(map, "L", "UN", "DE", "S", "O");
     * 
     * LOGGER.debug(JsonUtil.format(sortByKeyAsc));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "L": 3428,
        "UN": 17,
        "S": 6,
        "DE": 99,
        "O": 13
    }
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @param keys
     *            the keys
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keys</code> 是null或者empty,原样返回 <code>map</code><br>
     * @see PropertyComparator#PropertyComparator(String)
     * @see #sortMap(Map, Comparator)
     * @since 1.10.6
     */
    public static <K, V> Map<K, V> sortMapByKeyFixOrder(Map<K, V> map,K...keys){
        if (null == map){
            return emptyMap();
        }
        //---------------------------------------------------------------

        if (isNullOrEmpty(keys)){
            return map;
        }

        //---------------------------------------------------------------
        Comparator<Map.Entry<K, V>> propertyComparator = BeanComparatorUtil.propertyComparator("key", keys);
        return sortMap(map, propertyComparator);
    }

    //------------------------sortMapByValue---------------------------------------

    /**
     * 根据value 来顺序排序(asc).
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * LOGGER.debug(JsonUtil.format(SortUtil.sortMapByValueAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "b": 8,
     * "a": 123,
     * "c": 345
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see #sortMap(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     * @since 1.8.7 change name from sortByValueAsc
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortMapByValueAsc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sortMap(map, new PropertyComparator<Map.Entry<K, V>>("value"));
    }

    /**
     * 根据value 来倒序排序(desc).
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new LinkedHashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortMapByValueDesc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "c": 345,
     * "a": 123,
     * "b": 8
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see #sortMap(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     * @since 1.8.7 change method name from sortByValueDesc
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortMapByValueDesc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sortMap(map, new ReverseComparator<Map.Entry<K, V>>(new PropertyComparator<Map.Entry<K, V>>("value")));
    }

    //---------------------------------------------------------------

    /**
     * 使用 基于 {@link java.util.Map.Entry Entry} 的 <code>mapEntryComparator</code> 来对 <code>map</code>进行排序.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li><span style="color:red">原 <code>map</code> 的顺序不变</span></li>
     * <li>由于是对{@link java.util.Map.Entry Entry}排序的, 既可以按照key来排序,也可以按照value来排序哦</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 比如有以下的map
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <>}();
     * 
     * map.put("a13", 123);
     * map.put("a2", 345);
     * map.put("a8", 8);
     * </pre>
     * 
     * 如果我们只是使用 :
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByKeyAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "a13": 123,
     * "a2": 345,
     * "a8": 8
     * }
     * </pre>
     * 
     * 此时可以看出 a13是以字符串的形式进行比较的,我们可以使用以下的自定义的 Comparator,来达到排序的效果
     * 
     * <pre class="code">
     * PropertyComparator{@code <Entry<String, Integer>>} propertyComparator = new PropertyComparator{@code <Map.Entry<String, Integer>>}(
     *                 "key",
     *                 new RegexGroupNumberComparator("a(\\d*)"));
     * LOGGER.debug(JsonUtil.format(SortUtil.sortMap(map, propertyComparator)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "a2": 345,
     * "a8": 8,
     * "a13": 123
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @param mapEntryComparator
     *            基于 {@link java.util.Map.Entry Entry} 的 {@link Comparator}
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>mapEntryComparator</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.util.Collections#sort(List, Comparator)
     * @since 1.8.0 move from MapUtil
     * @since 1.8.7 change method name from sort
     */
    public static <K, V> Map<K, V> sortMap(Map<K, V> map,Comparator<Map.Entry<K, V>> mapEntryComparator){
        if (null == map){
            return emptyMap();
        }
        Validate.notNull(mapEntryComparator, "mapEntryComparator can't be null!");

        List<Map.Entry<K, V>> mapEntryList = toList(map.entrySet());
        return toMap(sortList(mapEntryList, mapEntryComparator));
    }

}
