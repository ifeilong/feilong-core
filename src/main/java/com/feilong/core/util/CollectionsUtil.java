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

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.bean.PropertyValueObtainer;
import com.feilong.core.util.closure.BeanPropertyValueChangeClosure;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.core.util.transformer.BeanTransformer;

/**
 * {@link Collection} 工具类,是 {@link Collections} 的扩展和补充.
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
 * <h3>guava 实用方法:</h3>
 * <blockquote>
 * <ol>
 * <li>com.google.common.collect.Iterables.concat({@code Iterable<? extends Iterable<? extends T>>})</li>
 * </ol>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.util.Collections
 * @see org.apache.commons.collections4.ListUtils
 * @see org.apache.commons.collections4.IterableUtils
 * @see org.apache.commons.collections4.CollectionUtils
 * @see "org.springframework.util.CollectionUtils"
 * @see "com.google.common.collect.Sets"
 * @see "com.google.common.collect.Lists"
 * @see "com.google.common.collect.Queues"
 * @see "com.google.common.collect.Iterators"
 * @see "com.google.common.collect.Iterables"
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

    //---------------------------------------------------------------

    /**
     * 循环将<code>beanIterable</code>每个元素的每个指定属性 <code>propertyName</code>的值改成 <code>propertyValue</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 对于以下购物车全选的代码:
     * 
     * <pre class="code">
     * 
     * <span style="color:green">//找到需要处理的对象list</span>
     * List{@code <ShoppingCartLineCommand>} toDoNeedChangeCheckedCommandList = select(
     *                 needChangeCheckedCommandList,
     *                 toggleCheckStatusShoppingCartLinePredicateBuilder.build(shoppingCartLineCommandList, checkStatus));
     * 
     * <span style="color:green">// 将状态修改成对应状态</span>
     * for (ShoppingCartLineCommand shoppingCartLineCommand : toDoNeedChangeCheckedCommandList){
     *     shoppingCartLineCommand.setSettlementState(1);
     * }
     * 
     * </pre>
     * 
     * <b>此时你还可以:</b>
     * 
     * <pre class="code">
     * <span style="color:green">//找到需要处理的对象list</span>
     * List{@code <ShoppingCartLineCommand>} toDoNeedChangeCheckedCommandList = select(
     *                 needChangeCheckedCommandList,
     *                 toggleCheckStatusShoppingCartLinePredicateBuilder.build(shoppingCartLineCommandList, checkStatus));
     * 
     * <span style="color:green">// 将状态修改成对应状态</span>
     * CollectionsUtil.forEach(toDoNeedChangeCheckedCommandList, "settlementState", 1);
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>beanIterable</code> 是null或者empty,什么都不做</li>
     * <li>如果 <code>beanIterable</code> 中有元素是null,将跳过去</li>
     * </ol>
     * </blockquote>
     * 
     * @param <O>
     *            the element type
     * @param beanIterable
     *            beanIterable
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            指定属性的属性值
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see org.apache.commons.collections4.IterableUtils#forEach(Iterable, org.apache.commons.collections4.Closure)
     * @see BeanPropertyValueChangeClosure
     * @since 1.10.2
     */
    public static <O> void forEach(final Iterable<O> beanIterable,String propertyName,Object propertyValue){
        if (isNotNullOrEmpty(beanIterable)){
            IterableUtils.forEach(beanIterable, new BeanPropertyValueChangeClosure<O>(propertyName, propertyValue));
        }
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
     * List{@code <String>} list = toList("xinge", "feilong1");
     * CollectionsUtil.addAllIgnoreNull(list, null); = false
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
     * private Set{@code <String>} getItemComboIds(List{@code <ShoppingCartLineCommand>} lines){
     *     Set{@code <String>} set = new HashSet{@code <>}();
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
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * 
     * private Set{@code <String>} getItemComboIds(List{@code <ShoppingCartLineCommand>} lines){
     *     if (isNullOrEmpty(lines)){
     *         return Collections.emptySet();
     *     }
     *     Set{@code <String>} set = new HashSet{@code <>}();
     *     for (ShoppingCartLineCommand line : lines){
     *         CollectionsUtil.addAllIgnoreNull(set, line.getComboIds());
     *     }
     *     return set;
     * }
     * </pre>
     * 
     * <p>
     * 重构之后,方法的复杂度会更小,阅读性更高
     * </p>
     * </blockquote>
     * 
     * @param <O>
     *            the type of object the {@link Collection} contains
     * @param objectCollection
     *            the collection to add to, 不能为null
     * @param iterable
     *            the iterable of elements to add
     * @return a boolean 标识 <code>objectCollection</code> 是否改变,如果改变了,返回true.<br>
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
        return null != iterable && CollectionUtils.addAll(objectCollection, iterable);
    }

    /**
     * 添加 <code>element</code>元素到指定的<code>objectCollection</code>,如果 <code>element</code> 是null或者 empty将忽略.
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * 
     * 对于以下代码:
     * 
     * <pre class="code">
     * 
     * List{@code <Object[]>} dataList = new ArrayList{@code <>}();
     * for (T bean : iterable){
     *     Object[] objectArray = toObjectArray(bean, propertyNameList);
     *     if (isNotNullOrEmpty(objectArray)){
     *         dataList.add(objectArray);
     *     }
     * }
     * return dataList;
     * 
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * 
     * List{@code <Object[]>} dataList = new ArrayList{@code <>}();
     * for (T bean : iterable){
     *     addIgnoreNullOrEmpty(dataList, toObjectArray(bean, propertyNameList));
     * }
     * return dataList;
     * </pre>
     * 
     * 重构之后,方法的复杂度会更小,阅读性更高
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param objectCollection
     *            the collection to add to, 不能为null
     * @param element
     *            element to add
     * @return a boolean 标识 <code>objectCollection</code> 是否改变,如果改变了,返回true.<br>
     *         如果 <code>objectCollection</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>element</code> 是null 或者 empty,直接返回false<br>
     *         否则调用<code>objectCollection.add(object)</code>
     * @see org.apache.commons.collections4.CollectionUtils#addIgnoreNull(Collection, Object)
     * @since 1.8.2
     */
    public static <T> boolean addIgnoreNullOrEmpty(final Collection<T> objectCollection,final T element){
        Validate.notNull(objectCollection, "objectCollection can't be null!");
        return isNotNullOrEmpty(element) && objectCollection.add(element);
    }

    //---------------------------------------------------------------

    /**
     * 在<code>list</code>中,查找第一个属性 <code>propertyName</code> 值是指定值 <code>propertyValue</code> 对象的索引位置.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * CollectionsUtil.indexOf(list, "name", "张飞")                          =   0
     * 
     * CollectionsUtil.indexOf(null, "age", 24)                             =   -1
     * CollectionsUtil.indexOf(new ArrayList{@code <User>}(), "age", 24)    =   -1
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>常用于 浏览历史记录,当前的商品id是否在历史记录中第一条位置,如果是,可能就不会操作Cookie,诸如此类的操作</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the generic type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            指定属性的属性值
     * @return 如果 <code>list</code>是null 或者 empty,返回 -1<br>
     *         如果指定属性<code>propertyName</code>的值 <code>propertyValue</code>在 list 查找不到,返回 -1<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see org.apache.commons.collections4.ListUtils#indexOf(List, Predicate)
     * @see BeanPredicateUtil#equalPredicate(String, Object)
     * @since 1.5.5
     */
    public static <O, V> int indexOf(List<O> list,String propertyName,V propertyValue){
        return ListUtils.indexOf(list, BeanPredicateUtil.<O, V> equalPredicate(propertyName, propertyValue));
    }
    //***********************删除****************************************************

    /**
     * 从 <code>objectCollection</code>中删除所有的 <code>removeCollection</code> <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回剩余的集合 <span style="color:red">(原集合对象<code>objectCollection</code>不变)</span>,如果你不想修改 <code>objectCollection</code>的话,不能直接调用
     * <code>collection.removeAll(remove);</code>,这个方法非常有用.</li>
     * <li>底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code>的元素加入到新的list返回.</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中删除 "feilong2","feilong1"元素
     * </p>
     * 
     * <pre class="code">
     * List{@code <String>} list = toList("xinge", "feilong1", "feilong2", "feilong2");
     * List{@code <String>} removeList = CollectionsUtil.removeAll(list, toList("feilong2", "feilong1"));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * ["xinge"]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the collection from which items are removed (in the returned collection)
     * @param removeCollection
     *            the items to be removed from the returned <code>collection</code>
     * @return 从 <code>objectCollection</code>中排除掉 <code>removeCollection</code> 元素的新的 list
     * @throws NullPointerException
     *             如果 <code>objectCollection</code> 是null,或者 <code>removeCollection</code> 是null
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     */
    public static <O> List<O> removeAll(Collection<O> objectCollection,Collection<O> removeCollection){
        Validate.notNull(objectCollection, "objectCollection can't be null!");
        Validate.notNull(removeCollection, "removeCollection can't be null!");
        return ListUtils.removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>objectCollection</code>中删除所有的 <code>null</code>元素 <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ol>
     * <li>返回剩余的集合 <span style="color:red">(原集合对象<code>objectCollection</code>不变)</span>,如果你不想修改 <code>objectCollection</code>的话,不能直接调用
     * <code>collection.removeAll(remove);</code>,这个方法非常有用.</li>
     * <li>底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code>的元素加入到新的list返回.</li>
     * </ol>
     * 
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中删除 null 元素
     * </p>
     * 
     * <pre class="code">
     * List{@code <String>} list = toList("xinge", <span style="color:red">null</span>, "feilong2", <span style=
    "color:red">null</span>, "feilong2");
     * List{@code <String>} removeList = CollectionsUtil.removeAllNull(list);
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * "xinge", "feilong2", "feilong2"
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the collection from which items are removed (in the returned collection)
     * @return 从 <code>objectCollection</code>中排除掉 <code>null</code> 元素的新的 list
     * @throws NullPointerException
     *             如果 <code>objectCollection</code> 是null
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.11.0
     */
    public static <O> List<O> removeAllNull(Collection<O> objectCollection){
        Validate.notNull(objectCollection, "objectCollection can't be null!");
        return remove(objectCollection, toArray((O) null));
    }

    //---------------------------------------------------------------
    /**
     * 从 <code>objectCollection</code>中 删除<code>removeElements</code> <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回剩余的集合 <span style="color:red">(原集合对象不变)</span>,这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.remove(removeElements);</code>.</li>
     * <li>底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElements</code> 的元素加入到新的list返回.</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("xinge");
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.remove(list, "feilong2")));
     * </pre>
     * 
     * <b>返回:</b>
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
     *            the object collection
     * @param removeElements
     *            需要被删除的元素
     * @return a <code>List</code> containing all the elements of <code>c</code> except any elements that also occur in <code>remove</code>.
     * @throws NullPointerException
     *             如果 <code>objectCollection</code> 是null
     * @see #removeAll(Collection, Collection)
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     * @since 1.10.7 change param to Varargs
     */
    @SafeVarargs
    public static <O> List<O> remove(Collection<O> objectCollection,O...removeElements){
        return removeAll(objectCollection, toList(removeElements));
    }

    //---------------------------------------------------------------
    /**
     * 去重,返回没有重复元素的新list <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * list.add("feilong3");
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.removeDuplicate(list)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * ["feilong1","feilong2","feilong3"]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原 <code>objectCollection</code> 是有序的,那么返回的结果参照原 <code>objectCollection</code>元素顺序</li>
     * <li>原 <code>objectCollection</code>不变</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @return 如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则先转换成 {@link LinkedHashSet},再转换成{@link ArrayList}返回
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see com.feilong.core.bean.ConvertUtil#toList(Collection)
     * @see org.apache.commons.collections4.IterableUtils#uniqueIterable(Iterable)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments
     *      </a>
     */
    public static <O> List<O> removeDuplicate(Collection<O> objectCollection){
        return isNullOrEmpty(objectCollection) ? Collections.<O> emptyList() : toList(new LinkedHashSet<O>(objectCollection));
    }

    /**
     * 去重,返回指定属性 propertyName的值没有重复元素的新list <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user1 = new User(1L);
     * User user2 = new User(1L);
     * List{@code <User>} list = toList(user1, user2);
     * 
     * List{@code <User>} removeDuplicate = CollectionsUtil.removeDuplicate(list, "id");
     * 
     * assertThat(removeDuplicate, contains(user1));
     * assertSame(1, removeDuplicate.size());
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原 <code>objectCollection</code> 是有序的,那么返回的结果参照原 <code>objectCollection</code>元素顺序</li>
     * <li>原 <code>objectCollection</code>不变</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo),<br>
     *            如果是null或者empty,那么直接调用 {@link #removeDuplicate(Collection)}<br>
     * @return 如果 <code>propertyNames</code> 是null或者empty,那么直接调用 {@link #removeDuplicate(Collection)}<br>
     *         如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see com.feilong.core.bean.ConvertUtil#toList(Collection)
     * @see org.apache.commons.collections4.IterableUtils#uniqueIterable(Iterable)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments
     *      </a>
     * @since 2.1.0
     */
    public static <O> List<O> removeDuplicate(Collection<O> objectCollection,String propertyName){
        if (isNullOrEmpty(propertyName)){
            return removeDuplicate(objectCollection);
        }
        if (isNullOrEmpty(objectCollection)){
            return Collections.<O> emptyList();
        }

        //---------------------------------------------------------------
        Map<Object, O> map = groupOne(objectCollection, propertyName);
        return toList(map.values());
    }

    /**
     * 去重,返回指定属性 propertyNames 组合的值都不重复元素的新list <span style="color:red">(原集合对象不变)</span>.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user1 = new User(1L);
     * user1.setUserInfo(new UserInfo(15));
     * 
     * User user2 = new User(1L);
     * user2.setUserInfo(new UserInfo(16));
     * 
     * User user3 = new User(1L);
     * user3.setUserInfo(new UserInfo(15));
     * 
     * List{@code <User>} list = toList(user1, user2, user3);
     * 
     * List{@code <User>} removeDuplicate = CollectionsUtil.removeDuplicate(list, "id", "userInfo.age");
     * 
     * assertThat(removeDuplicate, contains(user1, user2));
     * assertSame(2, removeDuplicate.size());
     * </pre>
     * 
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原 <code>objectCollection</code> 是有序的,那么返回的结果参照原 <code>objectCollection</code>元素顺序</li>
     * <li>原 <code>objectCollection</code>不变</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyNames
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo),<br>
     *            如果是null或者empty,那么直接调用 {@link #removeDuplicate(Collection)}<br>
     * @return 如果 <code>propertyNames</code> 是null或者empty,那么直接调用 {@link #removeDuplicate(Collection)}<br>
     *         如果 <code>objectCollection</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see com.feilong.core.bean.ConvertUtil#toList(Collection)
     * @see org.apache.commons.collections4.IterableUtils#uniqueIterable(Iterable)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments
     *      </a>
     * @since 2.1.0
     */
    public static <O> List<O> removeDuplicate(Collection<O> objectCollection,String...propertyNames){
        if (isNullOrEmpty(propertyNames)){
            return removeDuplicate(objectCollection);
        }
        if (isNullOrEmpty(objectCollection)){
            return Collections.<O> emptyList();
        }

        //---------------------------------------------------------------
        //用来识别是否重复
        List<Map<String, Object>> mapList = newArrayList();

        //用来存放返回list
        List<O> returnList = new ArrayList<>(IterableUtils.size(objectCollection));
        for (O o : objectCollection){
            Map<String, Object> propertyNameAndValueMap = PropertyUtil.describe(o, propertyNames);
            boolean isNotExist = !isExist(mapList, propertyNameAndValueMap, propertyNames);
            if (isNotExist){
                returnList.add(o);
                mapList.add(propertyNameAndValueMap);
            }
        }
        return returnList;
    }

    /**
     * 判断<code>mapList</code> 中,是否含有 指定 key-value 的map.
     *
     * @param mapList
     *            the map list
     * @param propertyNameAndValueMap
     *            the property name and value map
     * @param keys
     *            the keys
     * @return 存在,返回true
     * @since 2.1.0
     */
    private static boolean isExist(List<Map<String, Object>> mapList,Map<String, Object> propertyNameAndValueMap,String...keys){
        for (Map<String, Object> map : mapList){
            if (eqauls(map, propertyNameAndValueMap, keys)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个map ,提取每个属性 <code>keys</code> 值, 看看是否一致, 如果有不一致的返回false; 如果都一致那么返回true.
     *
     * @param map
     *            the map
     * @param propertyNameAndValueMap
     *            the property name and value map
     * @param keys
     *            the property names
     * @return true, if successful
     * @since 2.1.0
     */
    private static boolean eqauls(Map<String, Object> map,Map<String, Object> propertyNameAndValueMap,String...keys){
        for (String propertyName : keys){
            if (!Objects.equals(map.get(propertyName), propertyNameAndValueMap.get(propertyName))){
                return false;
            }
        }
        return true;
    }

    //----------------------获得 属性值-----------------------------------------

    /**
     * 循环集合 <code>beanIterable</code>,取到对象指定的属性 <code>propertyName</code>的值,拼成List({@link ArrayList}).
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 获取user list每个元素的id属性值,组成新的list返回
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = toList(//
     *                 new User(2L),
     *                 new User(5L),
     *                 new User(5L));
     * 
     * List{@code <Long>} resultList = CollectionsUtil.getPropertyValueList(list, "id");
     * LOGGER.debug(JsonUtil.format(resultList));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [2,5,5]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>对于参数 <code>propertyName</code>:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 对于以下的数据结构:
     * </p>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">//***************list****************************************</span>
     * List{@code <UserAddress>} userAddresseList = new ArrayList{@code <>}();
     * 
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress("中南海");
     * userAddresseList.add(userAddress);
     * 
     * <span style="color:green">//***************map****************************************</span>
     * Map{@code <String, String>} attrMap = new HashMap{@code <>}();
     * attrMap.put("蜀国", "赵子龙");
     * attrMap.put("魏国", "张文远");
     * attrMap.put("吴国", "甘兴霸");
     * 
     * //---------------------------------------------------------------
     * UserInfo userInfo1 = new UserInfo();
     * userInfo1.setAge(28);
     * 
     * User user1 = new User(2L);
     * user1.setLoves(new String[] { "sanguo1", "xiaoshuo1" });
     * user1.setUserInfo(userInfo1);
     * user1.setAttrMap(attrMap);
     * user1.setUserAddresseList(userAddresseList);
     * 
     * //---------------------------------------------------------------
     * UserInfo userInfo2 = new UserInfo();
     * userInfo2.setAge(null);
     * 
     * User user2 = new User(3L);
     * user2.setLoves(new String[] { "sanguo2", "xiaoshuo2" });
     * user2.setUserInfo(userInfo2);
     * user2.setAttrMap(attrMap);
     * user2.setUserAddresseList(userAddresseList);
     * 
     * List{@code <User>} userList = toList(user1,user2);
     * </pre>
     * 
     * <p>
     * <b>以下情况:</b>
     * </p>
     * 
     * <pre class="code">
     * <span style="color:green">//数组,取userList 每个元素的 loves属性第2个元素的值</span>
     * CollectionsUtil.getPropertyValueList(userList, <b>"loves[1]"</b>)                   =   ["xiaoshuo1","xiaoshuo2"]
    
     * <span style="color:green">//级联对象,取userList 每个元素的 userInfo属性的 age 属性的值</span>
     * CollectionsUtil.getPropertyValueList(userList, <b>"userInfo.age"</b>)               =   [28,null]
     * 
     * <span style="color:green">//Map,取userList 每个元素的 attrMap属性中的key是 "蜀国" 的值</span>
     * CollectionsUtil.getPropertyValueList(userList, <b>"attrMap(蜀国)"</b>)                =   ["赵子龙","赵子龙"]
     * 
     * <span style="color:green">//集合,取userList 每个元素的 userAddresseList属性中的第一个元素</span>
     * CollectionsUtil.getPropertyValueList(userList, <b>"userAddresseList[0]"</b>)        = [{"address": "中南海"},{"address": "中南海"}]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>关于参数 beanIterable</h3>
     * <blockquote>
     * 支持以下类型:
     * <dl>
     * <dt>bean Iterable</dt>
     * <dd>诸如List{@code <User>},Set{@code <User>}等</dd>
     * 
     * <dt>map Iterable</dt>
     * <dd>
     * 比如 {@code List<Map<String, String>>}
     * 
     * 示例:
     * 
     * <pre>
     * List{@code <Map<String, String>>} list = newArrayList();
     * list.add(toMap("key", "value1"));
     * list.add(toMap("key", "value2"));
     * list.add(toMap("key", "value3"));
     * 
     * List{@code <String>} resultList = CollectionsUtil.getPropertyValueList(list, "(key)");
     * assertThat(resultList, contains("value1", "value2", "value3"));
     * </pre>
     * 
     * </dd>
     * 
     * <dt>list Iterable</dt>
     * <dd>
     * 比如 {@code  List<List<String>>}
     * 
     * 示例:
     * 
     * <pre>
     * List{@code <List<String>>} list = newArrayList();
     * list.add(toList("小明", "18"));
     * list.add(toList("小宏", "18"));
     * list.add(toList("小振", "18"));
     * 
     * List{@code <String>} resultList = CollectionsUtil.getPropertyValueList(list, "[0]");
     * assertThat(resultList, contains("小明", "小宏", "小振"));
     * </pre>
     * 
     * </dd>
     * 
     * <dt>数组 Iterable</dt>
     * <dd>
     * 比如 {@code  List<String[]>}
     * 
     * 示例:
     * 
     * <pre>
     * List{@code <String[]>} list = newArrayList();
     * list.add(toArray("三国", "水浒"));
     * list.add(toArray("西游", "金瓶梅"));
     * 
     * List{@code <String>} resultList = CollectionsUtil.getPropertyValueList(list, "[0]");
     * assertThat(resultList, contains("三国", "西游"));
     * </pre>
     * 
     * </dd>
     * </dl>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            返回集合类型 generic type
     * @param <O>
     *            可迭代对象类型 generic type
     * @param beanIterable
     *            支持
     * 
     *            <ul>
     *            <li>bean Iterable,比如List{@code <User>},Set{@code <User>}等</li>
     *            <li>map Iterable,比如{@code List<Map<String, String>>}</li>
     *            <li>list Iterable , 比如 {@code  List<List<String>>}</li>
     *            <li>数组 Iterable ,比如 {@code  List<String[]>}</li>
     *            </ul>
     * 
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果参数 <code>beanIterable</code>是null或者empty,会返回empty ArrayList<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see PropertyValueObtainer#getPropertyValueCollection(Iterable, String, Collection)
     * @since jdk1.5
     */
    public static <T, O> List<T> getPropertyValueList(Iterable<O> beanIterable,String propertyName){
        if (isNullOrEmpty(beanIterable)){//避免null point
            return emptyList();
        }
        return PropertyValueObtainer
                        .getPropertyValueCollection(beanIterable, propertyName, new ArrayList<T>(IterableUtils.size(beanIterable)));
    }

    /**
     * 解析迭代集合 <code>beanIterable</code> ,取到对象指定的属性 <code>propertyName</code>的值,拼成{@link Set}({@link LinkedHashSet}).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的是 {@link LinkedHashSet},顺序是参数 <code>beanIterable</code> 元素的顺序</li>
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
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueSet(list, "id")));
     * </pre>
     * 
     * <b>返回:</b>
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
     * @param beanIterable
     *            支持
     * 
     *            <ul>
     *            <li>bean Iterable,比如List{@code <User>},Set{@code <User>}等</li>
     *            <li>map Iterable,比如{@code List<Map<String, String>>}</li>
     *            <li>list Iterable , 比如 {@code  List<List<String>>}</li>
     *            <li>数组 Iterable ,比如 {@code  List<String[]>}</li>
     *            </ul>
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果参数 <code>beanIterable</code>是null或者empty,会返回empty {@link LinkedHashSet}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see PropertyValueObtainer#getPropertyValueCollection(Iterable, String, Collection)
     * @since 1.0.8
     */
    public static <T, O> Set<T> getPropertyValueSet(Iterable<O> beanIterable,String propertyName){
        if (isNullOrEmpty(beanIterable)){//避免null point
            return emptySet();
        }
        return PropertyValueObtainer
                        .getPropertyValueCollection(beanIterable, propertyName, new LinkedHashSet<T>(IterableUtils.size(beanIterable)));
    }

    //----------------------------getPropertyValueMap-----------------------------------

    /**
     * 循环 <code>beanIterable</code> ,以 <code>keyPropertyName</code>属性值为key, <code>valuePropertyName</code>属性值为value,组成map返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的是 {@link LinkedHashMap},顺序是参数 <code>beanIterable</code> 元素的顺序</li>
     * <li>如果有元素 <code>keyPropertyName</code>属性值相同,那么后面的值会覆盖前面的值</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueMap(list, "name", "age")));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "张飞": 23,
     * "关羽": 24,
     * "刘备": 25
     * }
     * </pre>
     * 
     * <hr>
     * <p>
     * 如果有元素 <code>keyPropertyName</code>属性值相同,那么后面的值会覆盖前面的值
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("张飞", 25));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.getPropertyValueMap(list, "name", "age")));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "张飞": 25,
     * "关羽": 24,
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
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param keyPropertyName
     *            泛型O对象指定的属性名称,取到的值将作为返回的map的key,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param valuePropertyName
     *            泛型O对象指定的属性名称,取到的值将作为返回的map的value,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>keyPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>valuePropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>valuePropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     */
    public static <K, V, O> Map<K, V> getPropertyValueMap(Iterable<O> beanIterable,String keyPropertyName,String valuePropertyName){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }

        //---------------------------------------------------------------
        Validate.notBlank(keyPropertyName, "keyPropertyName can't be null/empty!");
        Validate.notBlank(valuePropertyName, "valuePropertyName can't be null/empty!");

        Map<K, V> map = newLinkedHashMap(IterableUtils.size(beanIterable));
        for (O bean : beanIterable){
            map.put(PropertyUtil.<K> getProperty(bean, keyPropertyName), PropertyUtil.<V> getProperty(bean, valuePropertyName));
        }
        return map;
    }
    //*************************find****************************************************************

    /**
     * 找到 <code>iterable</code>中,第一个 <code>propertyName</code>属性名称值是 <code>propertyValue</code> 的对应元素.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中查找name是 关羽 的User对象
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * list.add(new User("关羽", 50));
     * 
     * LOGGER.info(JsonUtil.format(CollectionsUtil.find(list, "name", "关羽")));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": 24,
     * "name": "关羽"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回第一个匹配对象</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            指定的值
     * @return 如果 <code>iterable</code>是null, 返回null<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>iterable</code>中没有相关元素的属性<code>propertyName</code> 值是<code>propertyValue</code>,返回null
     * @see #find(Iterable, Predicate)
     * @see com.feilong.core.util.predicate.BeanPredicateUtil#equalPredicate(String, Object)
     */
    public static <O, V> O find(Iterable<O> beanIterable,String propertyName,V propertyValue){
        return null == beanIterable ? null : find(beanIterable, BeanPredicateUtil.<O, V> equalPredicate(propertyName, propertyValue));
    }

    /**
     * 找到 <code>iterable</code>中,第一个 <code>propertyName</code>属性名称和值是 <code>propertyValue</code>是 propertyNameAndPropertyValueMap 的对应元素.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中查找name是 关羽,且年龄是24 的User对象
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * list.add(new User("关羽", 50));
     * 
     * Map{@code <String, ?>} map = toMap("name", "关羽", "age", 24);
     * LOGGER.info(JsonUtil.format(CollectionsUtil.find(list, map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": 24,
     * "name": "关羽"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回第一个匹配对象</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyNameAndPropertyValueMap
     *            属性和指定属性值对应的map,其中key是泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>iterable</code>是null, 返回null<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 是empty,抛出{@link IllegalArgumentException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 中有key是null,抛出{@link NullPointerException}<br>
     *         如果 <code>propertyNameAndPropertyValueMap</code> 中有key是blank,抛出{@link IllegalArgumentException}<br>
     *         如果 <code>iterable</code>中没有相关元素的属性<code>propertyName</code> 值是<code>propertyValue</code>,返回null
     * @see #find(Iterable, Predicate)
     * @see com.feilong.core.util.predicate.BeanPredicateUtil#equalPredicate(String, Object)
     * @since 2.1.0
     */
    public static <O> O find(Iterable<O> beanIterable,Map<String, ?> propertyNameAndPropertyValueMap){
        return null == beanIterable ? null : find(beanIterable, BeanPredicateUtil.<O> equalPredicate(propertyNameAndPropertyValueMap));
    }

    /**
     * 迭代查找匹配<code>predicate</code> 的第一个元素并返回.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中查找name是 关羽,并且 age等于30的User对象
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(//
     *                 new User("张飞", 23),
     *                 new User("关羽", 24),
     *                 new User("刘备", 25),
     *                 new User("关羽", 30));
     * 
     * Map{@code <String, Object>} map = new HashMap{@code <>}();
     * map.put("name", "关羽");
     * map.put("age", 30);
     * 
     * Predicate{@code <User>} predicate = BeanPredicateUtil.equalPredicate(map);
     * 
     * User user = CollectionsUtil.find(list, predicate);
     * LOGGER.debug(JsonUtil.format(user));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "age": 30,
     * "name": "关羽"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回第一个匹配对象</li>
     * </ol>
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
     * 循环 <code>beanIterable</code>,获得元素 <code>bean</code>的 <code>propertyName</code>的值,判断是否在<code>propertyValues</code>
     * 数组中;如果在,将该对象存入list中返回.
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <p>
     * 查询的结果的顺序按照原来 <code>beanIterable</code>里面的顺序,和参数 <code>propertyValues</code> 无关,如果你需要结果里面的元素按照指定的<code>propertyValues</code>
     * 顺序排序的话,可以将结果再调用{@link SortUtil#sortListByFixedOrderPropertyValueArray(List, String, Object...)}
     * </p>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * String[] array = { "刘备", "关羽" };
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, "name", array)));
     * 
     * </pre>
     * 
     * <b>返回:</b>
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
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the values
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>propertyValues</code> 是null,返回 {@code new ArrayList<O>}<br>
     * @see BeanPredicateUtil#containsPredicate(String, Object...)
     */
    @SafeVarargs
    public static <O, V> List<O> select(Iterable<O> beanIterable,String propertyName,V...propertyValues){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList()
                        : select(beanIterable, BeanPredicateUtil.<O, V> containsPredicate(propertyName, propertyValues));
    }

    /**
     * 循环 <code>beanIterable</code>,获得元素 <code>bean</code> 的<code>propertyName</code>的值,判断是否在<code>propertyValueList</code>
     * 集合中;如果在,将该对象存入list中返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>查询的结果的顺序按照原来 <code>beanIterable</code>里面的顺序,和参数 <code>propertyValueList</code> 无关,如果你需要结果里面的元素按照指定的
     * <code>propertyValueList</code>顺序排序的话,可以将结果再调用{@link SortUtil#sortListByFixedOrderPropertyValueList(List, String, List)}</li>
     * <li>和该方法正好相反的是 {@link #selectRejected(Iterable, String, Collection)}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 查询 name属性是"张飞"或者是"刘备"的 User list
     * </p>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * List{@code <String>} propertyValueList = new ArrayList{@code <>}();
     * propertyValueList.add("张飞");
     * propertyValueList.add("刘备");
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, "name", propertyValueList)));
     * </pre>
     * 
     * <b>返回:</b>
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
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:
     * </p>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 当前店铺 的物流方式Id set</span>
     * Set{@code <Long>} distributionModeIdSet = new HashSet{@code <>}();
     * for (TemeplateDistributionMode tdCmd : temeplateDistributionModeList){
     *     distributionModeIdSet.add(tdCmd.getDistributionModeId());
     * }
     * 
     * <span style="color:green">// 拿到所有的物流方式 列表</span>
     * List{@code <DistributionCommand>} distributionCommandList = freigthMemoryManager.getDistributionList();
     * 
     * <span style="color:green">// 根据 物流方式ID 找出 支持本商铺的 DistributionCommand</span>
     * List{@code <DistributionCommand>} curShopDistributionCommandList = new ArrayList{@code <>}();
     * 
     * for (Long modeId : distributionModeIdSet){
     *     for (DistributionCommand distributionCmd : distributionCommandList){
     *         if (modeId.equals(distributionCmd.getDistributionModeId())){
     *             curShopDistributionCommandList.add(distributionCmd);
     *         }
     *     }
     * }
     * </pre>
     * 
     * <b>可以重构成:</b>
     * 
     * <pre class="code">
     * <span style="color:green">// 当前店铺 的物流方式Id set</span>
     * Set{@code <Long>} distributionModeIdSet = CollectionsUtil.getPropertyValueSet(temeplateDistributionModeList, "distributionModeId");
     * <span style="color:green">// 拿到所有的物流方式 列表</span>
     * List{@code <DistributionCommand>} distributionCommandList = freigthMemoryManager.getDistributionList();
     * 
     * <span style="color:green">// 根据 物流方式ID 找出 支持本商铺的 DistributionCommand</span>
     * List{@code <DistributionCommand>} curShopDistributionCommandList = CollectionsUtil.select(distributionCommandList, "distributionModeId", distributionModeIdSet);
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         否则调用 {@link #select(Iterable, Predicate)}
     * @see #select(Iterable, Predicate)
     * @see BeanPredicateUtil#containsPredicate(String, Collection)
     * @since 1.5.0
     */
    public static <O, V> List<O> select(Iterable<O> beanIterable,String propertyName,Collection<V> propertyValueList){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList()
                        : select(beanIterable, BeanPredicateUtil.<O, V> containsPredicate(propertyName, propertyValueList));
    }

    /**
     * 按照指定的 {@link Predicate},返回查询出来的集合.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>和该方法正好相反的是 {@link #selectRejected(Iterable, Predicate)}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 查找等于 1的元素
     * </p>
     * 
     * <pre class="code">
     * List{@code <Long>} list = new ArrayList{@code <>}();
     * list.add(1L);
     * list.add(1L);
     * list.add(2L);
     * list.add(3L);
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, new EqualPredicate{@code <Long>}(1L))));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [1,1]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 查找大于 10的元素
     * </p>
     * 
     * <pre class="code">
     * Comparator{@code <Integer>} comparator = ComparatorUtils.naturalComparator();
     * Predicate{@code <Integer>} predicate = new ComparatorPredicate{@code <Integer>}(10, comparator, Criterion.LESS);
     * 
     * List{@code <Integer>} select = CollectionsUtil.select(toList(1, 5, 10, 30, 55, 88, 1, 12, 3), predicate);
     * LOGGER.debug(JsonUtil.format(select, 0, 0));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [30,55,88,12]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param predicate
     *            接口封装了对输入对象的判断,返回true或者false,可用的实现类有
     *            <ul>
     *            <li>{@link org.apache.commons.collections4.functors.EqualPredicate EqualPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.IdentityPredicate IdentityPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.FalsePredicate FalsePredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.TruePredicate TruePredicate}</li>
     *            <li>....</li>
     *            </ul>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         否则返回 {@link CollectionUtils#select(Iterable, Predicate)}
     * @see org.apache.commons.collections4.CollectionUtils#select(Iterable, Predicate)
     */
    public static <O> List<O> select(Iterable<O> beanIterable,Predicate<O> predicate){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList() : (List<O>) CollectionUtils.select(beanIterable, predicate);
    }

    //***************************selectRejected*********************************************************************

    /**
     * 循环 <code>beanIterable</code>,获得元素 <code>bean</code> 的 <code>propertyName</code> 属性值<span style="color:red">都不在</span>
     * <code>propertyValues</code> 时候的list.
     *
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 查询name 不是刘备 也不是张飞的 User list元素
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * List{@code <User>} selectRejected = CollectionsUtil.selectRejected(list, "name", "刘备", "张飞");
     * LOGGER.info(JsonUtil.format(selectRejected));
     * 
     * </pre>
     * 
     * <b>返回:</b>
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
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the values
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see BeanPredicateUtil#containsPredicate(String, Object...)
     * @see #selectRejected(Iterable, Predicate)
     */
    @SafeVarargs
    public static <O, V> List<O> selectRejected(Iterable<O> beanIterable,String propertyName,V...propertyValues){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList()
                        : selectRejected(beanIterable, BeanPredicateUtil.<O, V> containsPredicate(propertyName, propertyValues));
    }

    /**
     * 循环 <code>beanIterable</code>,获得元素 <code>bean</code> 的 <code>propertyName</code>的值,判断是否不在<code>propertyValueList</code>
     * 集合中;<span style="color:red">如果不在</span>,将该对象存入list中返回.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 查询 name属性是不是"张飞",也不是"刘备"的 User list
     * </p>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("关羽", 24));
     * list.add(new User("刘备", 25));
     * 
     * List{@code <String>} propertyValueList = new ArrayList{@code <>}();
     * propertyValueList.add("张飞");
     * propertyValueList.add("刘备");
     * LOGGER.info(JsonUtil.format(CollectionsUtil.selectRejected(list, "name", propertyValueList)));
     * 
     * </pre>
     * 
     * <b>返回:</b>
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
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>和该方法正好相反的是 {@link #select(Iterable, String, Collection)}</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see BeanPredicateUtil#containsPredicate(String, Collection)
     * @see #selectRejected(Iterable, Predicate)
     * @since 1.5.0
     */
    public static <O, V> List<O> selectRejected(Iterable<O> beanIterable,String propertyName,Collection<V> propertyValueList){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList()
                        : selectRejected(beanIterable, BeanPredicateUtil.<O, V> containsPredicate(propertyName, propertyValueList));
    }

    /**
     * 循环 <code>beanIterable</code>,获得元素 <code>bean</code>,判断是否不匹配<code>predicate</code>,<span style="color:red">如果不匹配</span>
     * ,将该对象存入list中返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>和该方法正好相反的是 {@link #select(Iterable, Predicate)}</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从list中查找不等于1的元素
     * </p>
     * 
     * <pre class="code">
     * List{@code <Long>} list = toList(1L, 1L, 2L, 3L);
     * CollectionsUtil.selectRejected(list, new EqualPredicate{@code <Long>}(1L))
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * 2L, 3L
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param predicate
     *            the predicate
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.collections4.CollectionUtils#selectRejected(Iterable, Predicate)
     * @since 1.4.0
     */
    public static <O> List<O> selectRejected(Iterable<O> beanIterable,Predicate<O> predicate){
        return isNullOrEmpty(beanIterable) ? Collections.<O> emptyList()
                        : (List<O>) CollectionUtils.selectRejected(beanIterable, predicate);
    }

    //---------------------------------------------------------------

    /**
     * 循环 <code>inputIterable</code>,将每个元素使用 <code>transformer</code> 转换成新的对象,返回<b>新的list</b>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("xinge");
     * list.add("feilong1");
     * list.add("feilong2");
     * list.add("feilong2");
     * 
     * Transformer{@code <String, Object>} nullTransformer = TransformerUtils.nullTransformer();
     * List{@code <Object>} collect = CollectionsUtil.collect(list, nullTransformer);
     * LOGGER.info(JsonUtil.format(collect, 0, 0));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [null,null,null,null]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>更多的,使用这个方法来处理两个不同类型的转换:</h3>
     * 
     * <blockquote>
     * <p>
     * 比如购物车功能,有游客购物车<b>CookieShoppingCartLine</b>以及内存购物车对象
     * <b>ShoppingCartLineCommand</b>,两个数据结构部分元素相同,<br>
     * 用户登陆需要把<b>cookie</b>中的购物车转成内存购物车<b>ShoppingCartLineCommand</b> list,这时我们可以先创建<b>ToShoppingCartLineCommandTransformer</b>
     * </p>
     * 
     * <p>
     * 代码示例:
     * </p>
     * 
     * <pre class="code">
     * 
     * class <b>ToShoppingCartLineCommandTransformer</b> implements <b>Transformer</b>{@code <CookieShoppingCartLine, ShoppingCartLineCommand>}{
     * 
     *     private static final String[] COPY_PROPERTY_NAMES = {"skuId","extentionCode","quantity","createTime","settlementState","lineGroup" };
     * 
     *     public ShoppingCartLineCommand <b>transform</b>(CookieShoppingCartLine cookieShoppingCartLine){
     *         <span style="color:green">// 将cookie中的购物车 转换为 shoppingCartLineCommand</span>
     *         ShoppingCartLineCommand shoppingLineCommand = new ShoppingCartLineCommand();
     *         PropertyUtil.copyProperties(shoppingLineCommand, cookieShoppingCartLine, COPY_PROPERTY_NAMES);
     * 
     *         shoppingLineCommand.setId(cookieShoppingCartLine.getId());
     *         shoppingLineCommand.setGift(null == cookieShoppingCartLine.getIsGift() ? false : cookieShoppingCartLine.getIsGift());
     * 
     *         return shoppingLineCommand;
     *     }
     * }
     * 
     * </pre>
     * 
     * <p>
     * 然后调用:
     * </p>
     * 
     * <pre class="code">
     * 
     * public List{@code <ShoppingCartLineCommand>} load(HttpServletRequest request){
     *     <span style="color:green">// 获取cookie中的购物车行集合</span>
     *     List{@code <CookieShoppingCartLine>} cookieShoppingCartLineList = getCookieShoppingCartLines(request);
     *     if (isNullOrEmpty(cookieShoppingCartLineList)){
     *         return null;
     *     }
     * 
     *     return CollectionsUtil.collect(cookieShoppingCartLineList, new ToShoppingCartLineCommandTransformer());
     * }
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
     * @return 如果 <code>inputIterable</code> 是null,返回 null<br>
     *         如果 <code>transformer</code> 是null,返回 empty list
     * @see org.apache.commons.collections4.CollectionUtils#collect(Iterable, Transformer)
     * @see org.apache.commons.collections4.CollectionUtils#transform(Collection, Transformer)
     * @since 1.5.5
     */
    public static <O, T> List<T> collect(final Iterable<O> inputIterable,final Transformer<? super O, ? extends T> transformer){
        return null == inputIterable ? null : (List<T>) CollectionUtils.collect(inputIterable, transformer);
    }

    /**
     * 循环 <code>inputBeanIterable</code>,将每个元素使用转换程成新的 outputListBeanType 类型对象(如有需要只copy传入的<code>includePropertyNames</code>属性)
     * 返回<b>新的list</b>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 已知有以下两个类 User 和 Customer
     * 
     * <pre class="code">
     * 
     * public class User{
     * 
     *     // The id. 
     *     private Long id = 0L;
     * 
     *     //** The name. 
     *     private String name = "feilong";
     * 
     *     //** 年龄. 
     *     private Integer age;
     * 
     *     //setter /getter
     * 
     *     public User(Long id, String name){
     *         this.id = id;
     *         this.name = name;
     *     }
     * 
     * }
     * 
     * </pre>
     * 
     * <pre class="code">
     * 
     * public class Customer{
     * 
     *     //** The id. 
     *     private long id;
     * 
     *     //* The name. 
     *     private String name;
     * 
     *     //setter /getter
     * 
     * }
     * 
     * </pre>
     * 
     * 此时有以下的 List{@code <User>} 需要转换成List{@code <Customer>}
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = toList(//
     *                 new User(23L, "张飞"),
     *                 new User(24L, "关羽"),
     *                 new User(25L, "刘备"));
     * </pre>
     * 
     * <b>以前你需要如此这般写:</b>
     * 
     * <pre class="code">
     * List{@code <Customer>} customerList = new ArrayList{@code <>}();
     * for (User user : list){
     *     Customer customer = new Customer();
     *     customer.setId(user.getId());
     *     customer.setName(user.getName());
     *     customerList.add(customer);
     * }
     * </pre>
     * 
     * <p>
     * 如果属性很多,书写代码很繁琐
     * </p>
     * 
     * <p>
     * 此时你可以这么写:
     * </p>
     * 
     * <pre class="code">
     *  List{@code <Customer>} customerList = CollectionsUtil.collect(list, Customer.class);
     * </pre>
     * 
     * <p>
     * 一行代码搞定集合转换问题
     * </p>
     * 
     * <p>
     * 如果你只想转换id属性,你可以:
     * </p>
     * 
     * <pre class="code">
     *  List{@code <Customer>} customerList = CollectionsUtil.collect(list, Customer.class,"id");
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>outputListBeanType 需要有默认的构造函数</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <I>
     *            the generic type
     * @param inputBeanIterable
     *            输入的input bean Iterable
     * @param outputListBeanType
     *            要转成Bean list的类型.
     * @param includePropertyNames
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo),<br>
     *            如果是null或者empty,那么复制所有的属性<br>
     *            <ol>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>inputBeanIterable</code> bean 没有的属性名字,将会抛出异常</li>
     *            <li>如果传入的<code>includePropertyNames</code>,含有 <code>inputBeanIterable</code>
     *            bean有,但是<code>outputListBeanType</code>没有的属性名字,会抛出异常,see{@link PropertyUtilsBean#setSimpleProperty(Object, String, Object)
     *            copyProperties} Line2078</li>
     *            </ol>
     * @return 如果 <code>inputBeanIterable</code> 是null,返回 null<br>
     *         如果 <code>inputBeanIterable</code> 中有元素是null,那么返回的list对应位置的元素也是null<br>
     *         如果 <code>outputListBeanType</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.10.1
     */
    public static <O, I> List<O> collect(
                    final Iterable<I> inputBeanIterable,
                    final Class<O> outputListBeanType,
                    final String...includePropertyNames){
        Validate.notNull(outputListBeanType, "outListBeanType can't be null!");
        return collect(inputBeanIterable, new BeanTransformer<>(outputListBeanType, includePropertyNames));
    }

    /**
     * 循环 <code>inputIterator</code>,将每个元素使用 <code>transformer</code> 转换成新的对象 返回<b>新的list</b>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 一个简单的将list中的所有元素转成null
     * </p>
     * 
     * <pre class="code">
     * List{@code <String>} list = toList("xinge", "feilong1", "feilong2", "feilong2");
     * 
     * Transformer{@code <String, Object>} nullTransformer = TransformerUtils.nullTransformer();
     * List{@code <Object>} collect = CollectionsUtil.collect(list.iterator(), nullTransformer);
     * LOGGER.info(JsonUtil.format(collect, 0, 0));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [null,null,null,null]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the type of object in the output collection
     * @param <T>
     *            the type of object in the input collection
     * @param inputIterator
     *            the iterator to get the input from
     * @param transformer
     *            the transformer to use, may be null
     * @return 如果 <code>inputIterator</code> 是null,返回 null<br>
     *         如果 <code>transformer</code> 是null,返回 {@code new ArrayList<>}
     * @see org.apache.commons.collections4.CollectionUtils#collect(java.util.Iterator, Transformer)
     * @since 1.5.5
     */
    public static <O, T> List<T> collect(final Iterator<O> inputIterator,final Transformer<? super O, ? extends T> transformer){
        return null == inputIterator ? null : (List<T>) CollectionUtils.collect(inputIterator, transformer);
    }

    //----------------------------group-----------------------------------

    /**
     * 循环 <code>beanIterable</code>,以 元素的 <code>propertyName</code>属性值为key,相同值的元素组成list作为value,封装成map返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>
     * 返回的{@link LinkedHashMap},key是 <code>beanIterable</code>中的元素对象中 <code>propertyName</code>的值,value是<code>beanIterable</code>
     * 中的元素对象;
     * </li>
     * 
     * <li>顺序是 <code>beanIterable</code> <code>propertyName</code>的值顺序,如果需要排序,可自行调用 {@link SortUtil#sortMapByKeyAsc(Map)},
     * {@link SortUtil#sortMapByKeyDesc(Map)}, {@link SortUtil#sortMapByValueAsc(Map)}, {@link SortUtil#sortMapByValueDesc(Map)}或者,
     * {@link SortUtil#sortMap(Map, java.util.Comparator)}</li>
     * 
     * <li>属性<code>propertyName</code>值相同的元素,组成集合 list</li>
     * <li>如果value只需要单值的话,可以调用 {@link #groupOne(Iterable, String)}方法</li>
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = toList(
     *                 new User("张飞", 23),
     *                 new User("刘备", 25),
     *                 new User("刘备", 30));
     * 
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(list, "name");
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "张飞": [ {
            "age": 23,
            "name": "张飞",
        }],
        "刘备": [
            {
                "age": 25,
                "name": "刘备",
            },
            {
                "age": 30,
                "name": "刘备",
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
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #group(Iterable, String, Predicate)
     * @since 1.0.8
     */
    public static <T, O> Map<T, List<O>> group(Iterable<O> beanIterable,String propertyName){
        return group(beanIterable, propertyName, null);
    }

    /**
     * 循环 <code>beanIterable</code>,找到符合条件的 <code>includePredicate</code>的元素,以元素的 <code>propertyName</code>
     * 属性值为key,相同值的元素组成list作为value,封装成map返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>
     * 返回的{@link LinkedHashMap},key是 <code>beanIterable</code>中的元素对象中 <code>propertyName</code>的值,value是<code>beanIterable</code>
     * 中的元素对象;
     * </li>
     * 
     * <li>
     * 顺序是 <code>beanIterable</code> <code>propertyName</code>的值顺序,如果需要排序,可自行调用 {@link SortUtil#sortMapByKeyAsc(Map)},
     * {@link SortUtil#sortMapByKeyDesc(Map)}, {@link SortUtil#sortMapByValueAsc(Map)}, {@link SortUtil#sortMapByValueDesc(Map)}或者,
     * {@link SortUtil#sortMap(Map, java.util.Comparator)}
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将age {@code >} 20的User,按照name 进行 group
     * </p>
     * 
     * <pre class="code">
        List{@code <User>} list = new ArrayList{@code <>}();
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
     * <b>返回:</b>
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
     * <p>
     * 当然,对于上述代码,你还可以优化成:
     * </p>
     * 
     * <pre class="code">
     * Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(list, "name", comparatorPredicate);
     * </pre>
     * 
     * 参见
     * {@link BeanPredicateUtil#comparatorPredicate(String, Comparable, org.apache.commons.collections4.functors.ComparatorPredicate.Criterion)}
     * 
     * </blockquote>
     *
     * @param <T>
     *            注意,此处的T是属性值,Object类型,如果从excel中读取的类型是String,那么不能简简单单的使用Integer来接收,不能强制转换
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param includePredicate
     *            the include predicate
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果没有任何element match <code>includePredicate</code>,返回 empty {@link LinkedHashMap}<br>
     *         如果 <code>includePredicate</code> 是null,那么以所有的元素进行分组
     * @see PropertyUtil#getProperty(Object, String)
     * @see #groupOne(Iterable, String)
     * @since 1.5.5
     */
    public static <T, O> Map<T, List<O>> group(Iterable<O> beanIterable,final String propertyName,Predicate<O> includePredicate){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");

        //---------------------------------------------------------------
        //org.apache.commons.beanutils.BeanToPropertyValueTransformer 但是实现的是 commons-collection3
        return group(beanIterable, includePredicate, new Transformer<O, T>(){

            @Override
            public T transform(O input){
                return PropertyUtil.getProperty(input, propertyName);
            }
        });
    }

    /**
     * 循环 <code>beanIterable</code>,将元素使用<code>keyTransformer</code>转成key,相同值的元素组成list作为value,封装成map返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 返回的{@link LinkedHashMap},key是 <code>beanIterable</code>中的元素 使用<code>keyTransformer</code>转换的值,value是
     * <code>beanIterable</code>中的元素对象(相同key值,组成list);
     * </li>
     * 
     * <li>
     * 返回的{@link LinkedHashMap}顺序,是 <code>beanIterable</code> 元素顺序,如果需要排序,可自行调用 {@link SortUtil#sortMapByKeyAsc(Map)},
     * {@link SortUtil#sortMapByKeyDesc(Map)}, {@link SortUtil#sortMapByValueAsc(Map)}, {@link SortUtil#sortMapByValueDesc(Map)}或者,
     * {@link SortUtil#sortMap(Map, java.util.Comparator)}
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从user list中,提取user的姓名的姓为key,user组成list,返回map
     * </p>
     * 
     * <pre class="code">
     * 
     * User mateng55 = new User("马腾", 55);
     * User machao28 = new User("马超", 28);
     * User madai27 = new User("马岱", 27);
     * User maxiu25 = new User("马休", 25);
     * User zhangfei28 = new User("张飞", 28);
     * User liubei32 = new User("刘备", 32);
     * User guanyu50 = new User("关羽", 50);
     * User guanping32 = new User("关平", 32);
     * User guansuo31 = new User("关索", 31);
     * User guanxing20 = new User("关兴", 18);
     * 
     * <span style="color:green">//---------------------------------------------------------------</span>
     * List{@code <User>} list = toList(mateng55, machao28, madai27, maxiu25, zhangfei28, liubei32, guanyu50, guanping32, guansuo31, guanxing20);
     * 
     * <span style="color:green">//---------------------------------------------------------------</span>
     * 
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(list,new Transformer{@code <User, String>}(){
     * 
     *     &#64;Override
     *     public String transform(User user){
     *         <span style="color:green">//提取名字 的姓</span>
     *         return user.getName().substring(0, 1);
     *     }
     * });
     * 
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "马":[{
                "age": 55,
                "name": "马腾",
            },{
                "age": 28,
                "name": "马超",
            },{
                "age": 27,
                "name": "马岱",
            },{
                "age": 25,
                "name": "马休",
            }
        ],
        "张": [{
            "age": 28,
            "name": "张飞",
        }],
        "刘": [{
            "age": 32,
            "name": "刘备",
        }],
        "关": [{
                "age": 50,
                "name": "关羽",
            },{
                "age": 32,
                "name": "关平",
            },{
                "age": 31,
                "name": "关索",
            },{
                "age": 18,
                "name": "关兴",
            }
        ]
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param keyTransformer
     *            返回的map,key转换器
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyTransformer</code> 是null,抛出 {@link NullPointerException}<br>
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/270">List to Map 实现类似矩阵的逻辑 by ananbeike</a>
     * @since 1.8.8
     */
    public static <T, O> Map<T, List<O>> group(Iterable<O> beanIterable,Transformer<O, T> keyTransformer){
        return group(beanIterable, null, keyTransformer);
    }

    /**
     * 循环 <code>beanIterable</code>,找到符合条件的 <code>includePredicate</code>的元素,将元素使用<code>keyTransformer</code>转成key
     * ,相同值的元素组成list作为value,封装成map返回.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 返回的{@link LinkedHashMap},key是 <code>beanIterable</code>中的元素 使用<code>keyTransformer</code>转换的值,value是
     * <code>beanIterable</code>中的元素对象(相同key值,组成list);
     * </li>
     * 
     * <li>
     * 返回的{@link LinkedHashMap}顺序,是 <code>beanIterable</code> 元素顺序,如果需要排序,可自行调用 {@link SortUtil#sortMapByKeyAsc(Map)},
     * {@link SortUtil#sortMapByKeyDesc(Map)}, {@link SortUtil#sortMapByValueAsc(Map)}, {@link SortUtil#sortMapByValueDesc(Map)}或者,
     * {@link SortUtil#sortMap(Map, java.util.Comparator)}
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 从user list中,提取 年龄 大于20的user,user的姓名的姓为key,user组成list,返回map
     * </p>
     * 
     * <pre class="code">
     * 
     * User mateng55 = new User("马腾", 55);
     * User machao28 = new User("马超", 28);
     * User madai27 = new User("马岱", 27);
     * User maxiu25 = new User("马休", 25);
     * User zhangfei28 = new User("张飞", 28);
     * User liubei32 = new User("刘备", 32);
     * User guanyu50 = new User("关羽", 50);
     * User guanping32 = new User("关平", 32);
     * User guansuo31 = new User("关索", 31);
     * User guanxing20 = new User("关兴", 18);
     * 
     * <span style="color:green">//---------------------------------------------------------------</span>
     * List{@code <User>} list = toList(mateng55, machao28, madai27, maxiu25, zhangfei28, liubei32, guanyu50, guanping32, guansuo31, guanxing20);
     * 
     * <span style="color:green">//---------------------------------------------------------------</span>
     * 
     * Predicate{@code <User>} comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
     * Map{@code <String, List<User>>} map = CollectionsUtil.group(list, comparatorPredicate, new Transformer{@code <User, String>}(){
     * 
     *     &#64;Override
     *     public String transform(User user){
     *         <span style="color:green">//提取名字 的姓</span>
     *         return user.getName().substring(0, 1);
     *     }
     * });
     * 
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
            "马":[{
                    "age": 55,
                    "name": "马腾",
                },{
                    "age": 28,
                    "name": "马超",
                },{
                    "age": 27,
                    "name": "马岱",
                },{
                    "age": 25,
                    "name": "马休"
                }],
            "张": [{
                "age": 28,
                "name": "张飞"
            }],
            "刘": [{
                "age": 32,
                "name": "刘备"
            }],
            "关": [{
                    "age": 50,
                    "name": "关羽"
                },{
                    "age": 32,
                    "name": "关平"
                },{
                    "age": 31,
                    "name": "关索"
                }]
        }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param includePredicate
     *            the include predicate
     * @param keyTransformer
     *            返回的map,key转换器
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyTransformer</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>includePredicate</code> 是null,那么以所有的元素进行分组<br>
     *         如果没有任何element match <code>includePredicate</code>,返回 empty {@link LinkedHashMap}<br>
     * @see org.apache.commons.collections4.Transformer#transform(Object)
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/270">List to Map 实现类似矩阵的逻辑 by ananbeike</a>
     * @since 1.8.8
     */
    public static <T, O> Map<T, List<O>> group(Iterable<O> beanIterable,Predicate<O> includePredicate,Transformer<O, T> keyTransformer){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }
        Validate.notNull(keyTransformer, "keyTransformer can't be null!");
        //---------------------------------------------------------------

        Map<T, List<O>> map = newLinkedHashMap(IterableUtils.size(beanIterable));
        for (O obj : beanIterable){
            if (null != includePredicate && !includePredicate.evaluate(obj)){
                continue;
            }
            MapUtil.putMultiValue(map, keyTransformer.transform(obj), obj);
        }
        return map;
    }

    /**
     * 循环 <code>iterable</code>,以元素的 <code>propertyName</code>属性值为key,元素为value,封装成map返回(map只put第一个匹配的元素,<b>后面出现相同的元素将会忽略</b>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的{@link LinkedHashMap},key是 <code>iterable</code>中的元素对象中 <code>propertyName</code>的值,value是
     * <code>beanIterable</code>中的元素对象;</li>
     * 
     * <li>顺序是 <code>beanIterable</code> <code>propertyName</code>的值 顺序,如果需要排序,可自行调用 {@link SortUtil#sortMapByKeyAsc(Map)},
     * {@link SortUtil#sortMapByKeyDesc(Map)}, {@link SortUtil#sortMapByValueAsc(Map)}, {@link SortUtil#sortMapByValueDesc(Map)}或者,
     * {@link SortUtil#sortMap(Map, java.util.Comparator)}</li>
     * 
     * <li>间接的可以做到基于某个属性值去重的效果</li>
     * <li>如果value需要是集合的话,可以调用 {@link #group(Iterable, String)}方法</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * List{@code <User>} list = new ArrayList{@code <>}();
     * list.add(new User("张飞", 23));
     * list.add(new User("刘备", 25));
     * list.add(new User("刘备", 30));
     * 
     * Map{@code <String, User>} map = CollectionsUtil.groupOne(list, "name");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
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
     * @param beanIterable
     *            bean Iterable,诸如List{@code <User>},Set{@code <User>}等
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>beanIterable</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see #group(Iterable, String)
     * @since 1.0.8
     */
    public static <T, O> Map<T, O> groupOne(Iterable<O> beanIterable,String propertyName){
        if (isNullOrEmpty(beanIterable)){
            return emptyMap();
        }
        Validate.notBlank(propertyName, "propertyName can't be null/empty!");
        //---------------------------------------------------------------
        Map<T, O> map = newLinkedHashMap(IterableUtils.size(beanIterable));
        for (O o : beanIterable){
            T key = PropertyUtil.getProperty(o, propertyName);
            if (!map.containsKey(key)){
                map.put(key, o);
            }else{
                if (LOGGER.isTraceEnabled()){
                    LOGGER.trace("map:[{}] already has the key:[{}],ignore!", map.keySet(), key);
                }
            }
        }
        return map;
    }

    //------------------list---------------------------------------------

    /**
     * 创建 a <i>mutable</i>, empty {@code ArrayList} instance .
     * 
     * @param <E>
     *            the element type
     * @return the array list
     * @since 1.10.7
     */
    public static <E> ArrayList<E> newArrayList(){
        return new ArrayList<>();
    }

    /**
     * 创建 a <i>mutable</i>, empty {@code LinkedList} instance .
     * 
     * @param <E>
     *            the element type
     * @return the linked list
     * @since 1.10.7
     */
    public static <E> LinkedList<E> newLinkedList(){
        return new LinkedList<>();
    }

    /**
     * 创建 a <i>mutable</i>, empty {@code CopyOnWriteArrayList} instance .
     * 
     * @param <E>
     *            the element type
     * @return a new, empty {@code CopyOnWriteArrayList}
     * @since 1.10.7
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(){
        return new CopyOnWriteArrayList<>();
    }

    //--------------------set-------------------------------------------

    /**
     * 创建 a <i>mutable</i>, empty {@code newHashSet} instance .
     * 
     * @param <E>
     *            the element type
     * @return the hash set
     * @since 1.10.7
     */
    public static <E> HashSet<E> newHashSet(){
        return new HashSet<>();
    }

    /**
     * 创建 a <i>mutable</i>, empty {@code LinkedHashSet} instance .
     *
     * @param <E>
     *            the element type
     * @return a new, empty {@code LinkedHashSet}
     * @since 1.10.7
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(){
        return new LinkedHashSet<>();
    }

}