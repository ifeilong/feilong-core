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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.core.util.predicate.ArrayContainsPredicate;
import com.feilong.core.util.predicate.BeanPropertyValueEqualsPredicate;
import com.feilong.core.util.predicate.CollectionContainsPredicate;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * {@link Collection} 工具类,是 {@link Collections} 的扩展和补充.<br>
 * 
 * <h3>Collections Framework关系图:</h3>
 * 
 * <blockquote>
 * <p>
 * <img src="http://venusdrogon.github.io/feilong-platform/mysource/Collections-Framework.png"/>
 * </p>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Collection}:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.Collection Collection}</td>
 * <td>
 * <ol>
 * <li>一组对象的集合,一般不直接使用</li>
 * <li>有 {@link Collection#add(Object) add},{@link Collection#size() size},{@link Collection#clear() clear},
 * {@link Collection#contains(Object) contains} ,{@link Collection#remove(Object) remove},{@link Collection#removeAll(Collection) removeAll}
 * ,{@link Collection#retainAll(Collection) retainAll}, {@link Collection#toArray() toArray}方法</li>
 * <li><span style="color:red">没有get()方法.只能通过iterator()遍历元素</span></li>
 * </ol>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.List}:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">interface/class</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.List List}</td>
 * <td>
 * <ol>
 * <li>An ordered collection</li>
 * <li>integer index for insert and search.</li>
 * <li>除了继承Collection接口方法外,有自己的方法定义: get(int) indexOf lastIndexOf listIterator set(int) subList(int,int)</li>
 * <li>optional:可空,可重复</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.ArrayList ArrayList}</td>
 * <td>
 * <ol>
 * <li>Resizable-array implementation of the List interface</li>
 * <li>元素可空</li>
 * <li>有自己控制容量(数组大小)的方法</li>
 * </ol>
 * <p>
 * 扩容:
 * </p>
 * <blockquote>
 * <ol>
 * <li>see {@link java.util.ArrayList#ensureCapacity(int)},<br>
 * 在jdk1.6里面,int newCapacity = (oldCapacity * 3)/2 + 1 通常是1.5倍<br>
 * 在jdk1.7+里面,代码进行了优化</li>
 * </ol>
 * </blockquote>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.LinkedList LinkedList}</td>
 * <td>
 * <ol>
 * <li>Linked list implementation,双向链表</li>
 * <li>元素可空</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.Vector Vector}</td>
 * <td>
 * <ol>
 * <li>growable array of objects</li>
 * <li>线程安全的动态数组 synchronized</li>
 * <li>操作基本和ArrayList相同</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.Stack Stack}</td>
 * <td>
 * <ol>
 * <li>last-in-first-out (LIFO) stack of objects</li>
 * </ol>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <hr>
 * 
 * <h3>关于 {@link Set }:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">interface/class</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.Set Set}</td>
 * <td>
 * <ol>
 * <li>A collection contains no duplicate elements</li>
 * <li>Set和Collection拥有一模一样的接口名称</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.HashSet HashSet}</td>
 * <td>
 * <ol>
 * <li>backed by a HashMap instance.</li>
 * <li>makes no guarantees as to the iteration order of the set; 不保证顺序</li>
 * <li>permits the null element.允许空元素</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.LinkedHashSet LinkedHashSet}</td>
 * <td>
 * <ol>
 * <li>Hash Map and linked list implementation of the Set interface,</li>
 * <li>with predictable iteration order</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.TreeSet TreeSet}</td>
 * <td>
 * <ol>
 * <li>A NavigableSet implementation based on a TreeMap.</li>
 * <li>ordered using their natural ordering, or by a Comparator provided at set creation time</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.EnumSet EnumSet}</td>
 * <td>
 * <ol>
 * <li>A specialized Set implementation for use with enum types.</li>
 * <li>Null elements are not permitted.</li>
 * <li>natural order (the order in which the enum constants are declared.</li>
 * <li>abstract class.</li>
 * <li>以位向量的形式存储,这种存储形式非常紧凑,高效,占用内存很小,运行效率很好.</li>
 * </ol>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <hr>
 * 
 * <h3>关于 {@link java.util.Queue Queue}:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">interface/class</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.Queue Queue}</td>
 * <td>
 * <ol>
 * <li>Queues typically, but do not necessarily,order elements in a FIFO (first-in-first-out) manner</li>
 * </ol>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * <h3><a href="http://stamen.iteye.com/blog/2003458">SET-MAP现代诗一首</a></h3>
 * 
 * <blockquote>
 * <ul>
 * <li>天下人都知道Set,Map不能重复</li>
 * <li>80%人知道hashCode,equals是判断重复的法则 </li>
 * <li>40%人知道Set添加重复元素时,旧元素不会被覆盖</li>
 * <li>20%人知道Map添加重复键时,旧键不会被覆盖,而值会覆盖</li>
 * </ul>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.util.Collections
 * @see org.apache.commons.collections4.ListUtils
 * @see org.apache.commons.collections4.IterableUtils
 * @see org.apache.commons.collections4.CollectionUtils
 * @see "org.springframework.util.CollectionUtils"
 * @since 1.0.2
 * @since jdk1.5
 */
public final class CollectionsUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsUtil.class);

    /** Don't let anyone instantiate this class. */
    private CollectionsUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 添加所有的{@link Iterable}元素到指定的<code>objectCollection</code>,如果 {@code iterable}是null将忽略.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <String>} list = ConvertUtil.toList("xinge", "feilong1");
     * CollectionsUtil.addAllIgnoreNull(list, null); = false
     * </pre>
     * 
     * </blockquote>
     * 
     * 对于以下代码:
     * 
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * private Set{@code <String>} getItemComboIds(List{@code <ShoppingCartLineCommand>} lines){
     *     Set{@code <String>} set = new HashSet{@code <String>}();
     *     if ({@code null != lines && lines.size() > 0}){
     *         for (ShoppingCartLineCommand line : lines){
     *             if (line.getComboIds() != null){
     *                 set.addAll(line.getComboIds());
     *             }
     *         }
     *     }
     *     return set;
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * 
     * private Set{@code <String>} getItemComboIds(List{@code <ShoppingCartLineCommand>} lines){
     *     if (Validator.isNullOrEmpty(lines)){
     *         return Collections.emptySet();
     *     }
     *     Set{@code <String>} set = new HashSet{@code <String>}();
     *     for (ShoppingCartLineCommand line : lines){
     *         CollectionsUtil.addAllIgnoreNull(set, line.getComboIds());
     *     }
     *     return set;
     * }
     * </pre>
     * 
     * 重构之后,方法额复杂度会更小
     * 
     * </blockquote>
     *
     * @param <O>
     *            the type of object the {@link Collection} contains
     * @param objectCollection
     *            the collection to add to, 不能为null
     * @param iterable
     *            the iterable of elements to add
     * @return a boolean 标识 collection 是否改变.<br>
     *         如果 <code>objectCollection</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>iterable</code> 是null,直接返回false<br>
     *         否则调用{@link CollectionUtils#addAll(Collection, Iterable)}
     * @see org.apache.commons.collections4.CollectionUtils#addIgnoreNull(Collection, Object)
     * @see org.apache.commons.collections4.CollectionUtils#addAll(Collection, Iterable)
     * @see org.apache.commons.collections4.CollectionUtils#addAll(Collection, Iterator)
     * @since 1.6.3
     */
    public static <O> boolean addAllIgnoreNull(final Collection<O> objectCollection,final Iterable<? extends O> iterable){
        Validate.notNull(objectCollection, "objectCollection can't be null!");
        return null == iterable ? false : CollectionUtils.addAll(objectCollection, iterable);
    }

    /**
     * 在list中,查找第一个属性 <code>propertyName</code> 值是指定值 <code>value</code>的索引位置.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * assertEquals(0, CollectionsUtil.indexOf(list, "name", "张飞"));
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            the value
     * @return 在list中,查找 第一个 属性 <code>propertyName</code> 值是 指定值 <code>value</code>的 索引位置<br>
     *         如果 list是null 或者 empty,返回 -1<br>
     *         如果指定属性<code>propertyName</code>的值 <code>value</code>在 list 查找不到也返回 -1<br>
     * @see org.apache.commons.collections4.ListUtils#indexOf(List, Predicate)
     * @see com.feilong.core.util.predicate.BeanPropertyValueEqualsPredicate
     * @since 1.5.5
     */
    public static <O, T> int indexOf(List<O> list,String propertyName,T propertyValue){
        return ListUtils.indexOf(list, new BeanPropertyValueEqualsPredicate<O>(propertyName, propertyValue));
    }
    //***********************删除****************************************************

    /**
     * 从 <code>collection</code>中 删除所有的 <code>remove</code>.
     * 
     * <p>
     * The cardinality of an element <code>e</code> in the returned collection is the same as the cardinality of <code>e</code> in
     * <code>collection</code> unless <code>remove</code> contains <code>e</code>, in which case the cardinality is zero.
     * </p>
     * 
     * <p>
     * 返回剩余的集合 <span style="color:red">(原集合对象不变)</span>,这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.removeAll(remove);</code>.
     * </p>
     * 
     * <p>
     * 底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code> 的元素加入到新的list返回.
     * </p>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the collection from which items are removed (in the returned collection)
     * @param removeCollection
     *            the items to be removed from the returned <code>collection</code>
     * @return 如果 <code>objectCollection</code> 是null,抛出 {@link NullPointerException}<br>
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     */
    public static <O> List<O> removeAll(Collection<O> objectCollection,Collection<O> removeCollection){
        Validate.notNull(objectCollection, "objectCollection can't be null!");
        return ListUtils.removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>collection</code>中 删除 所有的 <code>propertyName</code> 值在 <code>values</code>集合中的对象.
     * 
     * <p>
     * 返回剩余的集合 <span style="color:red">(原集合对象不变)</span>,这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.removeAll(remove);</code>.
     * </p>
     * 
     * <p>
     * 底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code> 的元素加入到新的list返回.
     * </p>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * 
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("张飞");
     * list.add("刘备");
     * 
     * List{@code <User>} removeAll = CollectionsUtil.removeAll(objectCollection, "name", list);
     * LOGGER.info(JsonUtil.format(removeAll));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
     * "age": 24,
     * "name": "关羽"
     * }]
     * </pre>
     * 
     * </blockquote>
     * 
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see #select(Collection, String, Collection)
     * @see #removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.5.0
     */
    public static <O, V> List<O> removeAll(Collection<O> objectCollection,String propertyName,Collection<V> propertyValueList){
        Collection<O> removeCollection = select(objectCollection, propertyName, propertyValueList);
        return removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>collection</code>中 删除所有的 <code>propertyName</code> 值是在 <code>propertyValues</code>中的对象.
     * 
     * <p>
     * 该方法等同于 {@link #selectRejected(Collection, String, Object...)}
     * </p>
     * 
     * <p>
     * 返回剩余的集合 <span style="color:red">(原集合对象不变)</span>,这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.removeAll(remove);</code>.
     * </p>
     * 
     * <p>
     * 底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code>的元素加入到新的list返回.
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * 
     * List{@code <User>} removeAll = CollectionsUtil.removeAll(objectCollection, "name", "刘备");
     * LOGGER.info(JsonUtil.format(removeAll));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
            "age": 23,
            "name": "张飞"
        },{
            "age": 24,
            "name": "关羽"
        }]
     * </pre>
     * 
     * <hr>
     * 
     * <pre class="code">
     * List{@code <User>} removeAll = CollectionsUtil.removeAll(objectCollection, "name", "刘备","关羽");
     * LOGGER.info(JsonUtil.format(removeAll));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
     * "age": 23,
     * "name": "张飞"
     * }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the values
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see #select(Collection, String, Object...)
     * @see #removeAll(Collection, Collection)
     * @since 1.6.0
     */
    @SafeVarargs
    public static <O, V> List<O> removeAll(Collection<O> objectCollection,String propertyName,V...propertyValues){
        Collection<O> removeCollection = select(objectCollection, propertyName, propertyValues);
        return removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>collection</code>中 删除<code>removeElement</code>.
     * 
     * <p>
     * 返回剩余的集合 <span style="color:red">(原集合对象不变)</span>,这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.remove(removeElement);</code>.
     * </p>
     * 
     * <p>
     * 底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code> 的元素加入到新的list返回.
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("xinge");
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.remove(list, "feilong2")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * ["xinge","feilong1"]
     * </pre>
     * 
     * 此时,原来的list不变:
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(list));
     * </pre>
     * 
     * 输出 :
     * 
     * <pre class="code">
     * ["xinge","feilong1","feilong2","feilong2"]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the collection from which items are removed (in the returned collection)
     * @param removeElement
     *            the remove element
     * @return a <code>List</code> containing all the elements of <code>c</code> except any elements that also occur in <code>remove</code>.
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     */
    public static <O> List<O> remove(Collection<O> objectCollection,O removeElement){
        return removeAll(objectCollection, ConvertUtil.toList(removeElement));
    }

    /**
     * 去重.
     * 
     * <p>
     * 如果原 <code>collection</code> 是有序的,那么会保留原 <code>collection</code>元素顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * list.add("feilong3");
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.removeDuplicate(list)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * ["feilong1","feilong2","feilong3"]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>效率问题？contains的本质就是遍历.</h3>
     * 
     * <blockquote>
     * <p>
     * 在100W的list当中执行0.546秒,而contains,我则没耐心去等了.顺便贴一下在10W下2段代码的运行时间.<br>
     * [foo1] 100000 {@code ->} 50487 : 48610 ms.<br>
     * [foo2] 100000 {@code ->} 50487 : 47 ms.<br>
     * </p>
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the item src list
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则先转换成 {@link LinkedHashSet},再转换成{@link ArrayList}返回
     * @see ArrayList#ArrayList(java.util.Collection)
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments
     *      </a>
     */
    public static <O> List<O> removeDuplicate(Collection<O> objectCollection){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : new ArrayList<O>(new LinkedHashSet<O>(objectCollection));
    }

    //*************************获得 属性值 *******************************************************************

    /**
     * 解析对象集合 <code>objectCollection</code>,使用 {@link PropertyUtil#getProperty(Object, String)}取到对象指定的属性 <code>propertyName</code>的值,拼成List(
     * {@link ArrayList}).
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <UserAddress>} userAddresseList = new ArrayList{@code <UserAddress>}();
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("中南海");
     * userAddresseList.add(userAddress);
     * 
     * //*******************************************************
     * Map{@code <String, String>} attrMap = new HashMap{@code <String, String>}();
     * attrMap.put("蜀国", "赵子龙");
     * attrMap.put("魏国", "张文远");
     * attrMap.put("吴国", "甘兴霸");
     * 
     * //*******************************************************
     * UserInfo userInfo1 = new UserInfo();
     * userInfo1.setAge(28);
     * 
     * User user1 = new User(2L);
     * user1.setLoves(new String[] { "sanguo1", "xiaoshuo1" });
     * user1.setUserInfo(userInfo1);
     * user1.setAttrMap(attrMap);
     * user1.setUserAddresseList(userAddresseList);
     * 
     * //*****************************************************
     * UserInfo userInfo2 = new UserInfo();
     * userInfo2.setAge(null);
     * 
     * User user2 = new User(3L);
     * user2.setLoves(new String[] { "sanguo2", "xiaoshuo2" });
     * user2.setUserInfo(userInfo2);
     * user2.setAttrMap(attrMap);
     * user2.setUserAddresseList(userAddresseList);
     * 
     * List{@code <User>} userList = new ArrayList{@code <User>}();
     * userList.add(user1);
     * userList.add(user2);
     * </pre>
     * 
     * <p>
     * 以下情况:
     * </p>
     * 
     * <hr>
     * <span style="color:green">//数组</span>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueList(userList, "loves[1]")));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
     * ["xiaoshuo1","xiaoshuo2"]
     * </pre>
     * 
     * <hr>
     * <span style="color:green">//级联对象</span>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueList(userList, "userInfo.age")));
     * </pre>
     * 
     * 结果 :
     * 
     * <pre class="code">
     * [28,null]
     * </pre>
     * 
     * <hr>
     * <span style="color:green">//Map</span>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueList(userList, "attrMap(蜀国)")));
     * </pre>
     * 
     * 结果 :
     * 
     * <pre class="code">
     * ["赵子龙","赵子龙"]
     * </pre>
     * 
     * <hr>
     * <span style="color:green">//集合</span>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueList(userList, "userAddresseList[0]")));
     * </pre>
     * 
     * 结果 :
     * 
     * <pre class="code">
     * [{"address": "中南海"},{"address": "中南海"}]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            返回集合类型 generic type
     * @param <O>
     *            可迭代对象类型 generic type
     * @param objectCollection
     *            任何可以迭代的对象
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 解析迭代集合,取到对象指定的属性 <code>propertyName</code>的值,拼成List(ArrayList),<br>
     *         如果参数 <code>objectCollection</code>是null或者empty,会返回empty ArrayList<br>
     *         如果参数 <code>propertyName</code>是null或者empty,将会出现异常;
     * @see #getPropertyValueCollection(Collection, String, Collection)
     * @since jdk1.5
     */
    public static <T, O> List<T> getPropertyValueList(Collection<O> objectCollection,String propertyName){
        return getPropertyValueCollection(objectCollection, propertyName, new ArrayList<T>());
    }

    /**
     * 解析迭代集合 <code>objectCollection</code> ,取到对象指定的属性 <code>propertyName</code>的值,拼成{@link Set}.
     * 
     * <p>
     * 注意:返回的是 {@link LinkedHashSet},顺序是参数 <code>objectCollection</code> 元素的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} testList = new ArrayList{@code <User>}();
     * testList.add(new User(2L));
     * testList.add(new User(5L));
     * testList.add(new User(5L));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueSet(testList, "id")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [2,5]
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
     * @return 如果参数 <code>objectCollection</code>是null或者empty,会返回empty {@link LinkedHashSet}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see #getPropertyValueCollection(Collection, String, Collection)
     * @since 1.0.8
     */
    public static <T, O> Set<T> getPropertyValueSet(Collection<O> objectCollection,String propertyName){
        return getPropertyValueCollection(objectCollection, propertyName, new LinkedHashSet<T>());
    }

    /**
     * 循环objectCollection,调用 {@link PropertyUtil#getProperty(Object, String)} 获得 propertyName的值,塞到 <code>returnCollection</code> 中返回.
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param <K>
     *            the key type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param returnCollection
     *            the return collection
     * @return 如果 <code>returnCollection</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>objectCollection</code> 是null或者empty,返回 <code>returnCollection</code><br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see PropertyUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.BeanToPropertyValueTransformer
     * @since 1.0.8
     */
    private static <T, O, K extends Collection<T>> K getPropertyValueCollection(
                    Collection<O> objectCollection,
                    String propertyName,
                    K returnCollection){
        Validate.notNull(returnCollection, "returnCollection can't be null!");
        if (Validator.isNullOrEmpty(objectCollection)){//避免null point
            return returnCollection;
        }

        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        for (O bean : objectCollection){
            returnCollection.add(PropertyUtil.<T> getProperty(bean, propertyName));
        }
        return returnCollection;
    }

    //******************************getPropertyValueMap*********************************************************************
    /**
     * 解析对象集合,以 <code>keyPropertyName</code>属性值为key, <code>valuePropertyName</code>属性值为值,组成map返回.
     * 
     * <p>
     * 注意:返回的是 {@link LinkedHashMap},顺序是参数 objectCollection 元素的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} testList = new ArrayList{@code <User>}();
     * testList.add(new User("张飞", 23));
     * testList.add(new User("关羽", 24));
     * testList.add(new User("刘备", 25));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueMap(testList, "name", "age")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "张飞": 23,
     * "关羽": 24,
     * "刘备": 25
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param <O>
     *            可迭代对象类型 generic type
     * @param objectCollection
     *            任何可以迭代的对象
     * @param keyPropertyName
     *            泛型O对象指定的属性名称,取到的值将作为返回的map的key,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param valuePropertyName
     *            泛型O对象指定的属性名称,取到的值将作为返回的map的value,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>keyPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>valuePropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>valuePropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     */
    public static <K, V, O> Map<K, V> getPropertyValueMap(Collection<O> objectCollection,String keyPropertyName,String valuePropertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notBlank(keyPropertyName, "keyPropertyName can't be null/empty!");
        Validate.notBlank(valuePropertyName, "valuePropertyName can't be null/empty!");

        Map<K, V> map = new LinkedHashMap<K, V>();
        for (O bean : objectCollection){
            map.put(PropertyUtil.<K> getProperty(bean, keyPropertyName), PropertyUtil.<V> getProperty(bean, valuePropertyName));
        }
        return map;
    }
    //*************************find****************************************************************

    /**
     * 找到 <code>iterable</code>中,第一个 <code>propertyName</code>属性名称值是 <code>value</code>对应的元素.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * objectCollection.add(new User("关羽", 24));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.find(objectCollection, "name", "关羽")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
     * "age": 24,
     * "name": "关羽"
     * }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param iterable
     *            the iterable
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            指定的值
     * @return 如果 <code>iterable</code>是null, 返回null<br>
     *         如果 <code>iterable</code>中没有相关元素的属性<code>propertyName</code> 值是<code>propertyValue</code>,返回null
     * @see #find(Iterable, Predicate)
     */
    public static <O, V> O find(Iterable<O> iterable,String propertyName,V propertyValue){
        return find(iterable, new BeanPropertyValueEqualsPredicate<O>(propertyName, propertyValue));
    }

    /**
     * 迭代查找匹配predicate 的第一个元素并返回.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * list.add(new User("关羽", 24));
     * 
     * User user = CollectionsUtil.find(
     *                 list,
     *                 PredicateUtils.andPredicate(
     *                                 new BeanPropertyValueEqualsPredicate{@code <User>}("name", "刘备"),
     *                                 new BeanPropertyValueEqualsPredicate{@code <User>}("age", 25)));
     * LOGGER.info(JsonUtil.format(user));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "age": 25,
     * "name": "刘备"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param iterable
     *            the iterable to search, may be null
     * @param predicate
     *            the predicate to use, may not be null
     * @return 如果 <code>predicate</code> 是 null,将抛出{@link NullPointerException} <br>
     *         如果 <code>iterable</code>是null, 返回null<br>
     *         如果 <code>iterable</code>中没有相关元素匹配 <code>predicate</code>,返回null
     * @see IterableUtils#find(Iterable, Predicate)
     * @since 1.5.5
     */
    public static <O> O find(Iterable<O> iterable,Predicate<O> predicate){
        return IterableUtils.find(iterable, predicate);
    }

    //**************************select*****************************************************************

    /**
     * 调用 {@link ArrayContainsPredicate},获得 <code>propertyName</code>的值,判断是否 在<code>values</code>数组中;如果在,将该对象存入list中返回.
     * 
     * <p>
     * 具体参见 {@link ArrayContainsPredicate}的使用
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * 
     * String[] array = { "刘备", "关羽" };
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", array)));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
       [{
               "age": 24,
               "name": "关羽"
           },{
               "age": 25,
               "name": "刘备"
       }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the values
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * @see com.feilong.core.util.predicate.ArrayContainsPredicate#ArrayContainsPredicate(String, Object...)
     */
    @SafeVarargs
    public static <O, V> List<O> select(Collection<O> objectCollection,String propertyName,V...propertyValues){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : select(objectCollection, new ArrayContainsPredicate<O>(propertyName, propertyValues));
    }

    /**
     * 调用 {@link CollectionContainsPredicate},获得 <code>propertyName</code>的值,判断是否 在<code>values</code>集合中;如果在,将该对象存入list中返回.
     * 
     * <p>
     * 具体参见 {@link CollectionContainsPredicate}的使用
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * 
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("张飞");
     * list.add("刘备");
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", list)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
    [{
                "age": 23,
                "name": "张飞"
            },{
                "age": 25,
                "name": "刘备"
     }]
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则调用 {@link #select(Collection, Predicate)}
     * @see com.feilong.core.util.predicate.CollectionContainsPredicate
     * @see #select(Collection, Predicate)
     * @since 1.5.0
     */
    public static <O, V> List<O> select(Collection<O> objectCollection,String propertyName,Collection<V> propertyValueList){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : select(objectCollection, new CollectionContainsPredicate<O>(propertyName, propertyValueList));
    }

    /**
     * 按照指定的 {@link Predicate},返回查询出来的集合.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <Long>} list = new ArrayList{@code <Long>}();
     * list.add(1L);
     * list.add(1L);
     * list.add(2L);
     * list.add(3L);
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, new EqualPredicate{@code <Long>}(1L))));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [1,1]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param predicate
     *            接口封装了对输入对象的判断,返回true或者false,可用的实现类有
     *            <ul>
     *            <li>{@link org.apache.commons.collections4.functors.EqualPredicate EqualPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.IdentityPredicate IdentityPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.FalsePredicate FalsePredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.TruePredicate TruePredicate}</li>
     *            <li>....</li>
     *            </ul>
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则返回 {@link CollectionUtils#select(Iterable, Predicate)}
     * @see org.apache.commons.collections4.CollectionUtils#select(Iterable, Predicate)
     */
    public static <O> List<O> select(Collection<O> objectCollection,Predicate<O> predicate){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : (List<O>) CollectionUtils.select(objectCollection, predicate);
    }

    //***************************selectRejected*********************************************************************

    /**
     * 循环遍历 <code>objectCollection</code> ,返回 当bean <code>propertyName</code> 属性值都不在 <code>propertyValues</code> 时候的list.
     *
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * List{@code <User>} selectRejected = CollectionsUtil.selectRejected(list, "name", "刘备", "张飞");
     * LOGGER.info(JsonUtil.format(selectRejected));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
     * "age": 24,
     * "name": "关羽"
     * }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the values
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see com.feilong.core.util.predicate.ArrayContainsPredicate
     * @see #selectRejected(Collection, Predicate)
     */
    @SafeVarargs
    public static <O, V> List<O> selectRejected(Collection<O> objectCollection,String propertyName,V...propertyValues){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : selectRejected(objectCollection, new ArrayContainsPredicate<O>(propertyName, propertyValues));
    }

    /**
     * 调用 {@link CollectionContainsPredicate},获得 <code>propertyName</code>的值,判断是否 不在<code>values</code>集合中;如果不在,将该对象存入list中返回.
     * 
     * <p>
     * 具体参见 {@link CollectionContainsPredicate}的使用
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} objectCollection = new ArrayList{@code <User>}();
     * objectCollection.add(new User("张飞", 23));
     * objectCollection.add(new User("关羽", 24));
     * objectCollection.add(new User("刘备", 25));
     * 
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("张飞");
     * list.add("刘备");
     * LOGGER.info(JsonUtil.format(CollectionsUtil.selectRejected(objectCollection, "name", list)));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * [{
     * "age": 24,
     * "name": "关羽"
     * }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see com.feilong.core.util.predicate.CollectionContainsPredicate
     * @see #selectRejected(Collection , Predicate)
     * @since 1.5.0
     */
    public static <O, V> List<O> selectRejected(Collection<O> objectCollection,String propertyName,Collection<V> propertyValueList){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : selectRejected(objectCollection, new CollectionContainsPredicate<O>(propertyName, propertyValueList));
    }

    /**
     * Select rejected.
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param predicate
     *            the predicate
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.collections4.CollectionUtils#selectRejected(Iterable, Predicate)
     * @since 1.4.0
     */
    public static <O> List<O> selectRejected(Collection<O> objectCollection,Predicate<O> predicate){
        return Validator.isNullOrEmpty(objectCollection) ? Collections.<O> emptyList()
                        : (List<O>) CollectionUtils.selectRejected(objectCollection, predicate);
    }

    /**
     * 循环 <code>inputIterable</code>,将每个元素使用 <code>transformer</code> 转换成新的对象 返回<b>新的list</b>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <String>} list = new ArrayList{@code <String>}();
     * list.add("xinge");
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * 
     * Transformer{@code <String, Object>} nullTransformer = TransformerUtils.nullTransformer();
     * List{@code <Object>} collect = CollectionsUtil.collect(list, nullTransformer);
     * LOGGER.info("list:{}", JsonUtil.format(collect, 0, 0));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * list:[null,null,null,null]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the type of object in the input collection
     * @param <T>
     *            the type of object in the output collection
     * @param inputIterable
     *            the inputIterable to get the input from
     * @param transformer
     *            the transformer to use, may be null
     * @return 如果 <code>inputIterable</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>transformer</code> 是null,返回 empty list
     * @see org.apache.commons.collections4.CollectionUtils#collect(Iterable, Transformer)
     * @since 1.5.5
     */
    public static <O, T> List<T> collect(final Iterable<O> inputIterable,final Transformer<? super O, ? extends T> transformer){
        return null == inputIterable ? Collections.<T> emptyList() : (List<T>) CollectionUtils.collect(inputIterable, transformer);
    }

    /**
     * 循环 <code>inputIterator</code>,将每个元素使用 <code>transformer</code> 转换成新的对象 返回<b>新的list</b>.
     * 
     * @param <O>
     *            the type of object in the output collection
     * @param <T>
     *            the type of object in the input collection
     * @param inputIterator
     *            the iterator to get the input from
     * @param transformer
     *            the transformer to use, may be null
     * @return 如果 <code>inputIterator</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>transformer</code> 是null,返回 empty list
     * @see org.apache.commons.collections4.CollectionUtils#collect(java.util.Iterator, Transformer)
     * @since 1.5.5
     */
    public static <O, T> List<T> collect(final Iterator<O> inputIterator,final Transformer<? super O, ? extends T> transformer){
        return null == inputIterator ? Collections.<T> emptyList() : (List<T>) CollectionUtils.collect(inputIterator, transformer);
    }
    //*******************************group*********************************************************

    /**
     * Group 对象(如果propertyName 存在相同的值,那么这些值,将会以list的形式 put到map中).
     * 
     * <p>
     * 返回的LinkedHashMap,key是 <code>objectCollection</code>中的元素对象中 <code>propertyName</code>的值,value是 <code>objectCollection</code>中的元素对象;
     * <br>
     * 顺序是 <code>objectCollection</code> <code>propertyName</code>的值 顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} testList = new ArrayList{@code <User>}();
     * testList.add(new User("张飞", 23));
     * testList.add(new User("刘备", 25));
     * testList.add(new User("刘备", 25));
     * 
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(testList, "name");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
     * {
     * "张飞": [{
     * "age": 23,
     * "name": "张飞"
     * }],
     * "刘备": [{
     * "age": 25,
     * "name": "刘备"
     * },{
     * "age": 25,
     * "name": "刘备"
     * }]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            注意,此处的T是属性值,Object类型,如果从excel中读取的类型是String,那么不能简简单单的使用Integer来接收,不能强制转换
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see #group(Collection, String, Predicate)
     * @since 1.0.8
     */
    public static <T, O> Map<T, List<O>> group(Collection<O> objectCollection,String propertyName){
        return group(objectCollection, propertyName, null);
    }

    /**
     * 循环 <code>objectCollection</code>,找到符合条件的 <code>includePredicate</code>的元素,(如果propertyName存在相同的值,那么这些值,将会以list的形式 put到map中).
     * 
     * <p>
     * 返回的{@link LinkedHashMap},key是 <code>objectCollection</code>中的元素对象中 <code>propertyName</code>的值,value是 <code>objectCollection</code>
     * 中的元素对象;
     * <br>
     * 顺序是 <code>objectCollection</code> <code>propertyName</code>的值顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
        List{@code <User>} list = new ArrayList{@code <User>}();
        list.add(new User("张飞", 10));
        list.add(new User("张飞", 28));
        list.add(new User("刘备", 32));
        list.add(new User("刘备", 30));
        list.add(new User("刘备", 10));
    
        Map{@code <String, List<User>>} map = CollectionsUtil.group(list, "name", new Predicate{@code <User>}(){
    
            {@code @Override}
            public boolean evaluate(User user){
                return user.getAge() {@code >} 20;
            }
        });
        LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
    {
        "张飞": [{
            "age": 28,
            "name": "张飞"
        }],
        "刘备": [{
                "age": 32,
                "name": "刘备"
            },{
                "age": 30,
                "name": "刘备"
            }
        ]
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            注意,此处的T是属性值,Object类型,如果从excel中读取的类型是String,那么不能简简单单的使用Integer来接收,不能强制转换
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果没有任何element match <code>includePredicate</code>,返回 empty {@link LinkedHashMap}<br>
     *         如果 <code>includePredicate</code> 是null,那么以所有的元素进行分组
     * @see PropertyUtil#getProperty(Object, String)
     * @see #groupOne(Collection, String)
     * @since 1.5.5
     */
    public static <T, O> Map<T, List<O>> group(Collection<O> objectCollection,String propertyName,Predicate<O> includePredicate){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, List<O>> map = new LinkedHashMap<T, List<O>>(objectCollection.size());
        for (O o : objectCollection){
            if (null != includePredicate && !includePredicate.evaluate(o)){
                continue;
            }
            MapUtil.putMultiValue(map, PropertyUtil.<T> getProperty(o, propertyName), o);
        }
        return map;
    }

    /**
     * Group one(map只put第一个匹配的元素,后面出现相同的元素将会忽略).
     * 
     * <p>
     * 返回的LinkedHashMap,key是 <code>objectCollection</code>中的元素对象中 <code>propertyName</code>的值,value是 <code>objectCollection</code>中的元素对象;
     * <br>
     * 顺序是 <code>objectCollection</code> <code>propertyName</code>的值 顺序
     * </p>
     * 
     * <p>
     * 间接的可以做到基于某个属性值去重的效果
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} testList = new ArrayList{@code <User>}();
     * testList.add(new User("张飞", 23));
     * testList.add(new User("刘备", 25));
     * testList.add(new User("刘备", 25));
     * 
     * Map{@code <String, User>} map = CollectionsUtil.groupOne(testList, "name");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
     * {
        "张飞":         {
            "age": 23,
            "name": "张飞"
        },
        "刘备":         {
            "age": 25,
            "name": "刘备"
        }
    }
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
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #group(Collection, String)
     * @since 1.0.8
     */
    public static <T, O> Map<T, O> groupOne(Collection<O> objectCollection,String propertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }

        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, O> map = new LinkedHashMap<T, O>(objectCollection.size());

        for (O o : objectCollection){
            T key = PropertyUtil.getProperty(o, propertyName);

            if (!map.containsKey(key)){
                map.put(key, o);
            }else{
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug("map:{} already has the key:{},ignore!", JsonUtil.format(map.keySet()), key);
                }
            }
        }
        return map;
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
     * List{@code <User>} testList = new ArrayList{@code <User>}();
     * testList.add(new User("张飞"));
     * testList.add(new User("关羽"));
     * testList.add(new User("刘备"));
     * testList.add(new User("刘备"));
     * 
     * Map{@code <String, Integer>} map = CollectionsUtil.groupCount(testList, "name");
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
     * Map{@code <String, Integer>} map = CollectionsUtil.groupCount(list, "name", new Predicate{@code <User>}(){
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
     * @since 1.6.2
     */
    public static <T, O> Map<T, Integer> groupCount(Collection<O> objectCollection,String propertyName,Predicate<O> includePredicate){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        Map<T, Integer> map = new LinkedHashMap<T, Integer>();
        for (O o : objectCollection){
            if (null != includePredicate && !includePredicate.evaluate(o)){
                continue;
            }
            MapUtil.putSumValue(map, PropertyUtil.<T> getProperty(o, propertyName), 1);
        }
        return map;
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
     * CollectionsUtil.avg(list, "id", 2)
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
     * @since 1.6.2
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
     * List{@code <User>} list = ConvertUtil.toList(user1, user2);
     * Map{@code <String, BigDecimal>} map = CollectionsUtil.avg(list, ConvertUtil.toArray("id", "age"), 2);
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
        Map<String, BigDecimal> map = new LinkedHashMap<String, BigDecimal>(size);
        for (Map.Entry<String, BigDecimal> entry : sumMap.entrySet()){
            map.put(entry.getKey(), NumberUtil.getDivideValue(ConvertUtil.toBigDecimal(entry.getValue()), size, scale));
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
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * 
     * User user1 = new User(2L);
     * user1.setAge(18);
     * list.add(user1);
     * 
     * User user2 = new User(3L);
     * user2.setAge(30);
     * list.add(user2);
     * 
     * Map{@code <String, BigDecimal>} map = CollectionsUtil.sum(list, "id", "age");
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
     * List{@code <User>} list = ConvertUtil.toList(user1, user2, user3);
     * Map{@code <String, BigDecimal>} map = CollectionsUtil.sum(list, ConvertUtil.toArray("id", "age"), new Predicate{@code <User>}(){
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
     * @since 1.6.2
     */
    public static <O> Map<String, BigDecimal> sum(Collection<O> objectCollection,String[] propertyNames,Predicate<O> includePredicate){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.noNullElements(propertyNames, "propertyNames can't be null/empty!");

        Map<String, BigDecimal> sumMap = new LinkedHashMap<String, BigDecimal>(objectCollection.size());
        for (O o : objectCollection){
            if (null != includePredicate && !includePredicate.evaluate(o)){
                continue;
            }

            for (String propertyName : propertyNames){
                Number propertyValue = PropertyUtil.getProperty(o, propertyName);

                BigDecimal mapPropertyNameValue = sumMap.get(propertyName);
                //如果通过反射某个元素值是null,则使用默认值0 代替
                BigDecimal addValue = NumberUtil.getAddValue(null == mapPropertyNameValue ? 0 : mapPropertyNameValue, propertyValue);
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
     * LOGGER.info("" + CollectionsUtil.sum(list, "id"));
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
     *     return Validator.isNullOrEmpty(cartLineList) ? 0 : CollectionsUtil.sum(cartLineList, "quantity").intValue();
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
     * @since 1.5.0
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
     * assertEquals(new BigDecimal(100L), CollectionsUtil.sum(list, "id", new Predicate{@code <User>}(){
     * 
     *     {@code @Override}
     *     public boolean evaluate(User user){
     *         return user.getId() {@code >} 10L;
     *     }
     * }));
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
     * @since 1.5.5
     */
    public static <O> BigDecimal sum(Collection<O> objectCollection,String propertyName,Predicate<O> includePredicate){
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        return sum(objectCollection, ConvertUtil.toArray(propertyName), includePredicate).get(propertyName);
    }
}