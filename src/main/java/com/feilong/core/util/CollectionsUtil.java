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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.bean.BeanUtilException;
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
 * <h3>{@link <a href="http://stamen.iteye.com/blog/2003458">SET-MAP现代诗一首</a>}</h3>
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
 * <h3>Collections Framework关系图:</h3>
 * 
 * <blockquote>
 * <p>
 * <img src="http://venusdrogon.github.io/feilong-platform/mysource/Collections-Framework.png"/>
 * </p>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Collection}:</h3>
 * <blockquote>
 * <ol>
 * <li>一组对象的集合,一般不直接使用</li>
 * <li>有 {@link Collection#add(Object) add},{@link Collection#size() size} ,{@link Collection#clear() clear},
 * {@link Collection#contains(Object) contains}
 * ,{@link Collection#remove(Object) remove},{@link Collection#removeAll(Collection) removeAll},{@link Collection#retainAll(Collection)
 * retainAll},
 * {@link Collection#toArray() toArray}方法</li>
 * <li>没有get()方法.只能通过iterator()遍历元素</li>
 * </ol>
 * </blockquote>
 * 
 * 
 * <h3>关于 {@link java.util.List}:</h3>
 * <blockquote>
 * <ol>
 * <li>An ordered collection</li>
 * <li>integer index for insert and search.</li>
 * <li>除了继承Collection接口方法外,有自己的方法定义: get(int) indexOf lastIndexOf listIterator set(int) subList(int,int)</li>
 * <li>optional:可空,可重复</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.ArrayList}:</h3>
 * <blockquote>
 * <ol>
 * <li>Resizable-array implementation of the List interface</li>
 * <li>元素可空</li>
 * <li>有自己控制容量(数组大小)的方法</li>
 * </ol>
 * 
 * <h3>扩容:</h3>
 * <blockquote>
 * <ol>
 * <li>see {@link java.util.ArrayList#ensureCapacity(int)},<br>
 * 在jdk1.6里面,int newCapacity = (oldCapacity * 3)/2 + 1 通常是1.5倍<br>
 * 在jdk1.7+里面,代码进行了优化</li>
 * </ol>
 * </blockquote>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.LinkedList}:</h3>
 * <blockquote>
 * <ol>
 * <li>Linked list implementation,双向链表</li>
 * <li>元素可空</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Vector}:</h3>
 * <blockquote>
 * <ol>
 * <li>growable array of objects</li>
 * <li>线程安全的动态数组 synchronized</li>
 * <li>操作基本和ArrayList相同</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Stack}:</h3>
 * <blockquote>
 * <ol>
 * <li>last-in-first-out (LIFO) stack of objects</li>
 * </ol>
 * </blockquote>
 * 
 * 
 * <hr>
 * 
 * 
 * <h3>关于 {@link java.util.Set }:</h3>
 * <blockquote>
 * <ol>
 * <li>A collection contains no duplicate elements</li>
 * <li>Set和Collection拥有一模一样的接口名称</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.HashSet }:</h3>
 * <blockquote>
 * <ol>
 * <li>backed by a HashMap instance.</li>
 * <li>makes no guarantees as to the iteration order of the set; 不保证顺序</li>
 * <li>permits the null element.允许空元素</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.LinkedHashSet }:</h3>
 * <blockquote>
 * <ol>
 * <li>Hash Map and linked list implementation of the Set interface,</li>
 * <li>with predictable iteration order</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.TreeSet }:</h3>
 * <blockquote>
 * <ol>
 * <li>A NavigableSet implementation based on a TreeMap.</li>
 * <li>ordered using their natural ordering, or by a Comparator provided at set creation time</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.EnumSet }:</h3>
 * <blockquote>
 * <ol>
 * <li>A specialized Set implementation for use with enum types.</li>
 * <li>Null elements are not permitted.</li>
 * <li>natural order (the order in which the enum constants are declared.</li>
 * <li>abstract class.</li>
 * <li>以位向量的形式存储,这种存储形式非常紧凑,高效,占用内存很小,运行效率很好.</li>
 * </ol>
 * </blockquote>
 * 
 * <hr>
 * 
 * <h3>关于 {@link java.util.Queue }:</h3>
 * <blockquote>
 * <ol>
 * <li>Queues typically, but do not necessarily,order elements in a FIFO (first-in-first-out) manner</li>
 * </ol>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.2 Sep 2, 2010 8:08:40 PM
 * @version 1.5.0 2016年1月8日 下午2:08:59
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

    //***********************删除****************************************************

    /**
     * 从 <code>collection</code>中 删除 所有的 <code>remove</code>. 返回剩余的集合 <span style="color:red">(原集合对象不变)</span>.
     * 
     * <p>
     * The cardinality of an element <code>e</code> in the returned collection is the same as the cardinality of <code>e</code> in
     * <code>collection</code> unless <code>remove</code> contains <code>e</code>, in which case the cardinality is zero.
     * </p>
     * 
     * <p>
     * 这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用 <code>collection.removeAll(remove);</code>.
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
     * @param remove
     *            the items to be removed from the returned <code>collection</code>
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     */
    public static <O> List<O> removeAll(Collection<O> objectCollection,Collection<O> remove){
        return ListUtils.removeAll(objectCollection, remove);
    }

    /**
     * 从 <code>collection</code>中 删除 所有的 <code>propertyName</code> 值在 <code>values</code>集合中的对象. 返回剩余的集合 <span
     * style="color:red">(原集合对象不变)</span>.
     * 
     * <p>
     * 这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用 <code>collection.removeAll(remove);</code>.
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
     * <pre>
     * 
     * {@code
     * 
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));
    
        List<String> list = new ArrayList<String>();
        list.add("张飞");
        list.add("刘备");
    
        List<User> removeAll = CollectionsUtil.removeAll(objectCollection, "name", list);
        LOGGER.info(JsonUtil.format(removeAll));
     * }
     * 
     * 返回:
     * 
    [    {
        "age": 24,
        "name": "关羽"
    }]
     * 
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param values
     *            the values
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see #select(Collection, String, Collection)
     * @see #removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.5.0
     */
    public static <O, V> List<O> removeAll(Collection<O> objectCollection,String propertyName,Collection<V> values){
        Collection<O> removeCollection = select(objectCollection, propertyName, values);
        return removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>collection</code>中 删除所有的 <code>propertyName</code> 值是 <code>value</code>的对象.返回剩余的集合 <span
     * style="color:red">(原集合对象不变)</span>.
     * 
     * <p>
     * 该方法等同于 {@link #selectRejected(Collection, String, Object)}
     * </p>
     * 
     * <p>
     * 这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用 <code>collection.removeAll(remove);</code>.
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
     * <pre>
     * 
     * {@code
     * 
     *     List<User> objectCollection = new ArrayList<User>();
     *     objectCollection.add(new User("张飞", 23));
     *     objectCollection.add(new User("关羽", 24));
     *     objectCollection.add(new User("刘备", 25));
     * 
     *     List<User> removeAll = CollectionsUtil.removeAll(objectCollection, "name", "刘备");
     *     LOGGER.info(JsonUtil.format(removeAll));
     * }
     * 
     * 返回:
     * 
     * [
                {
            "age": 23,
            "name": "张飞"
        },
                {
            
            "age": 24,
            "name": "关羽"
        }
    ]
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            the value
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see #select(Collection, String, Collection)
     * @see #removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.5.0
     */
    public static <O, V> List<O> removeAll(Collection<O> objectCollection,String propertyName,V value){
        Collection<O> removeCollection = select(objectCollection, propertyName, value);
        return removeAll(objectCollection, removeCollection);
    }

    /**
     * 从 <code>collection</code>中 删除<code>removeElement</code>,返回剩余的集合 <span style="color:red">(原集合对象不变)</span>.
     * 
     * <p>
     * 这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用 <code>collection.remove(removeElement);</code>.
     * </p>
     * 
     * <p>
     * 底层实现是调用的 {@link ListUtils#removeAll(Collection, Collection)},将不是<code>removeElement</code> 的元素加入到新的list返回.
     * </p>
     * 
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
        List<String> list = new ArrayList<String>();
        list.add("xinge");
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");
    
        LOGGER.info(JsonUtil.format(CollectionsUtil.remove(list, "feilong2")));
    }
    
    返回:
    [
        "xinge",
        "feilong1"
    ]
    
    此时,原来的list不变:
    {@code
        LOGGER.info(JsonUtil.format(list));
    }
    
    输出 :
    
    [
        "xinge",
        "feilong1",
        "feilong2",
        "feilong2"
    ]
     * 
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
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 4
     * @since 1.0.8
     */
    public static <O> List<O> remove(Collection<O> objectCollection,O removeElement){
        Collection<O> remove = new ArrayList<O>();
        remove.add(removeElement);
        return removeAll(objectCollection, remove);
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
     * <pre>
    {@code
        List<String> list = new ArrayList<String>();
        list.add("feilong1");
        list.add("feilong2");
        list.add("feilong2");
        list.add("feilong3");
    
        LOGGER.info(JsonUtil.format(CollectionsUtil.removeDuplicate(list)));
    }
    
    返回:
    
    [
        "feilong1",
        "feilong2",
        "feilong3"
    ]
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>效率问题？contains的本质就是遍历.</h3>
     * 
     * <blockquote>
     * <p>
     * 在100W的list当中执行0.546秒,而contains,我则没耐心去等了.顺便贴一下在10W下2段代码的运行时间.<br>
     * [foo1] 100000 -> 50487 : 48610 ms.<br>
     * [foo2] 100000 -> 50487 : 47 ms.<br>
     * </p>
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the item src list
     * @return if Validator.isNullOrEmpty(collection) 返回null<br>
     *         else 返回的是 {@link ArrayList}
     * @see ArrayList#ArrayList(java.util.Collection)
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments
     *      </a>
     */
    public static <O> List<O> removeDuplicate(Collection<O> objectCollection){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }
        return new ArrayList<O>(new LinkedHashSet<O>(objectCollection));
    }

    //*************************获得 属性值 *******************************************************************

    /**
     * 解析对象集合,使用 {@link PropertyUtil#getProperty(Object, String)}取到对象特殊属性,拼成List(ArrayList).
     * 
     * <p>
     * 支持属性级联获取,支付获取数组,集合,map,自定义bean等属性
     * </p>
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * List&lt;User&gt; testList = new ArrayList&lt;User&gt;();
     * 
     * User user;
     * UserInfo userInfo;
     * 
     * //*******************************************************
     * List&lt;UserAddress&gt; userAddresseList = new ArrayList&lt;UserAddress&gt;();
     * UserAddress userAddress = new UserAddress();
     * userAddress.setAddress(&quot;中南海&quot;);
     * userAddresseList.add(userAddress);
     * 
     * //*******************************************************
     * Map&lt;String, String&gt; attrMap = new HashMap&lt;String, String&gt;();
     * attrMap.put(&quot;蜀国&quot;, &quot;赵子龙&quot;);
     * attrMap.put(&quot;魏国&quot;, &quot;张文远&quot;);
     * attrMap.put(&quot;吴国&quot;, &quot;甘兴霸&quot;);
     * 
     * //*******************************************************
     * String[] lovesStrings1 = { &quot;sanguo1&quot;, &quot;xiaoshuo1&quot; };
     * userInfo = new UserInfo();
     * userInfo.setAge(28);
     * 
     * user = new User(2L);
     * user.setLoves(lovesStrings1);
     * user.setUserInfo(userInfo);
     * user.setUserAddresseList(userAddresseList);
     * 
     * user.setAttrMap(attrMap);
     * testList.add(user);
     * 
     * //*****************************************************
     * String[] lovesStrings2 = { &quot;sanguo2&quot;, &quot;xiaoshuo2&quot; };
     * userInfo = new UserInfo();
     * userInfo.setAge(30);
     * 
     * user = new User(3L);
     * user.setLoves(lovesStrings2);
     * user.setUserInfo(userInfo);
     * user.setUserAddresseList(userAddresseList);
     * user.setAttrMap(attrMap);
     * testList.add(user);
     * 
     * //数组
     * List&lt;String&gt; fieldValueList1 = CollectionsUtil.getPropertyValueList(testList, &quot;loves[1]&quot;);
     * LOGGER.info(JsonUtil.format(fieldValueList1));
     * 
     * //级联对象
     * List&lt;Integer&gt; fieldValueList2 = CollectionsUtil.getPropertyValueList(testList, &quot;userInfo.age&quot;);
     * LOGGER.info(JsonUtil.format(fieldValueList2));
     * 
     * //Map
     * List&lt;Integer&gt; attrList = CollectionsUtil.getPropertyValueList(testList, &quot;attrMap(蜀国)&quot;);
     * LOGGER.info(JsonUtil.format(attrList));
     * 
     * //集合
     * List&lt;String&gt; addressList = CollectionsUtil.getPropertyValueList(testList, &quot;userAddresseList[0]&quot;);
     * LOGGER.info(JsonUtil.format(addressList));
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return 解析迭代集合,取到对象特殊属性,拼成List(ArrayList)
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     * @see #getPropertyValueCollection(Collection, String, Collection)
     * @since jdk1.5
     */
    public static <T, O> List<T> getPropertyValueList(Collection<O> objectCollection,String propertyName){
        List<T> list = new ArrayList<T>();
        return getPropertyValueCollection(objectCollection, propertyName, list);
    }

    /**
     * 获得 property value set.
     * 
     * <pre>
     * Example 1:
     * 
     * List<User> testList = new ArrayList<User>();
     * testList.add(new User(2L));
     * testList.add(new User(5L));
     * testList.add(new User(5L));
     * 
     * Set<Long> fieldValueCollection = CollectionsUtil.getPropertyValueSet(testList, "id");
     * LOGGER.info(JsonUtil.format(fieldValueCollection));
     * 
     * 输出:
     * 
     * [
        2,
        5
        ]
     * 
     * </pre>
     * 
     * <p>
     * 以 {@link LinkedHashSet}存储,一定程度上可以按照元素顺序返回
     * </p>
     * 
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return the property value set
     * @see #getPropertyValueCollection(Collection, String, Collection)
     * @since 1.0.8
     */
    public static <T, O> Set<T> getPropertyValueSet(Collection<O> objectCollection,String propertyName){
        Set<T> set = new LinkedHashSet<T>();
        return getPropertyValueCollection(objectCollection, propertyName, set);
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param returnCollection
     *            the return collection
     * @return if Validator.isNullOrEmpty(objectCollection),return <code>returnCollection</code>
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.BeanToPropertyValueTransformer
     * @since 1.0.8
     */
    private static <T, O, K extends Collection<T>> K getPropertyValueCollection(
                    Collection<O> objectCollection,
                    String propertyName,
                    K returnCollection){
        Validate.notNull(returnCollection, "returnCollection can't be null!");

        //避免null point
        if (Validator.isNullOrEmpty(objectCollection)){
            return returnCollection;
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        try{
            for (O bean : objectCollection){
                @SuppressWarnings("unchecked")
                T property = (T) PropertyUtil.getProperty(bean, propertyName);
                returnCollection.add(property);
            }
        }catch (BeanUtilException e){
            LOGGER.error(e.getClass().getName(), e);
        }
        return returnCollection;
    }

    //*************************find****************************************************************

    /**
     * 找到 <code>objectCollection</code>中,第一个 <code>propertyName</code>属性名称 值是 <code>value</code>对应的元素.
     * 
     * <p>
     * 如果 collection or predicate是null, 或者 collection中没有相关元素匹配 predicate,将返回null.
     * </p>
     *
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));
        objectCollection.add(new User("关羽", 24));
    
        LOGGER.info(JsonUtil.format(CollectionsUtil.find(objectCollection, "name", "关羽")));
    }
    
    返回:
    
    [
                {
            "age": 24,
            "name": "关羽"
        }
    ]
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            指定的值
     * @return the first element of the collection which matches the predicate or null if none could be found
     * @see IterableUtils#find(Iterable, Predicate)
     */
    public static <O, V> O find(Collection<O> objectCollection,String propertyName,V value){
        Predicate<O> predicate = new BeanPropertyValueEqualsPredicate<O>(propertyName, value);
        return IterableUtils.find(objectCollection, predicate);
    }

    //**************************select*****************************************************************

    /**
     * 循环遍历 <code>objectCollection</code>,返回 当bean propertyName 属性值 equals 特定value 时候的list.
     *
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
        List<User> objectCollection = new ArrayList<User>();
        objectCollection.add(new User("张飞", 23));
        objectCollection.add(new User("关羽", 24));
        objectCollection.add(new User("刘备", 25));
        objectCollection.add(new User("关羽", 24));
    
        LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", "关羽")));
    }
    
    返回:
    
    [
                {
            "age": 24,
            "name": "关羽"
        },
                {
            "age": 24,
            "name": "关羽"
        }
    ]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            the value
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see CollectionUtils#select(Iterable, Predicate)
     */
    public static <O, V> List<O> select(Collection<O> objectCollection,String propertyName,V value){
        Object[] values = { value };
        return select(objectCollection, propertyName, values);
    }

    /**
     * 调用 {@link ArrayContainsPredicate},获得 <code>propertyName</code>的值,判断是否 在<code>values</code>数组中;如果在,将该对象存入list中返回.
     * 
     * <p>
     * 具体参见 {@link ArrayContainsPredicate}的使用
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            List<User> objectCollection = new ArrayList<User>();
            objectCollection.add(new User("张飞", 23));
            objectCollection.add(new User("关羽", 24));
            objectCollection.add(new User("刘备", 25));
    
            String[] array = }{ "刘备", "关羽" } {@code ;
            LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, "name", array)));
    }
    
    返回:
    
    [
                {
            "age": 24,
            "name": "关羽"
        },
                {
            "age": 25,
            "name": "刘备"
        }
    ]
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param values
     *            the values
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see com.feilong.core.util.predicate.ArrayContainsPredicate#ArrayContainsPredicate(String, Object...)
     */
    @SafeVarargs
    public static <O, V> List<O> select(Collection<O> objectCollection,String propertyName,V...values){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        Predicate<O> predicate = new ArrayContainsPredicate<O>(propertyName, values);
        return select(objectCollection, predicate);
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
     * <pre>
     * List&lt;User&gt; objectCollection = new ArrayList&lt;User&gt;();
     * objectCollection.add(new User(&quot;张飞&quot;, 23));
     * objectCollection.add(new User(&quot;关羽&quot;, 24));
     * objectCollection.add(new User(&quot;刘备&quot;, 25));
     * 
     * List&lt;String&gt; list = new ArrayList&lt;String&gt;();
     * list.add(&quot;张飞&quot;);
     * list.add(&quot;刘备&quot;);
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, &quot;name&quot;, list)));
     * </pre>
     * 
     * <p>
     * 返回 张飞 和 刘备的对象集合
     * </p>
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param values
     *            the values
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see com.feilong.core.util.predicate.CollectionContainsPredicate
     * @since 1.5.0
     */
    public static <O, V> List<O> select(Collection<O> objectCollection,String propertyName,Collection<V> values){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        Predicate<O> predicate = new CollectionContainsPredicate<O>(propertyName, values);
        return select(objectCollection, predicate);
    }

    /**
     * 按照指定的 {@link Predicate},返回查询出来的集合.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            List<Long> list = new ArrayList<Long>();
            list.add(1L);
            list.add(1L);
            list.add(2L);
            list.add(3L);
            LOGGER.info(JsonUtil.format(CollectionsUtil.select(list, new EqualPredicate<Long>(1L))));
    }
    
    返回:
    
    [
        1,
        1
    ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param predicate
     *            接口封装了对输入对象的判断，返回true或者false,可用的实现类有
     *            <ul>
     *            <li>{@link org.apache.commons.collections.functors.EqualPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.IdentityPredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.FalsePredicate}</li>
     *            <li>{@link org.apache.commons.collections4.functors.TruePredicate}</li>
     *            <li>....</li>
     *            </ul>
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see org.apache.commons.collections4.CollectionUtils#select(Iterable, Predicate)
     */
    public static <O> List<O> select(Collection<O> objectCollection,Predicate<O> predicate){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }
        return (List<O>) CollectionUtils.select(objectCollection, predicate);
    }

    //***************************selectRejected*********************************************************************

    /**
     * 循环遍历 <code>objectCollection</code> ,返回 当bean propertyName 属性值不 equals 特定value 时候的list.
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            the value
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see CollectionUtils#selectRejected(Iterable, Predicate)
     */
    public static <O, V> List<O> selectRejected(Collection<O> objectCollection,String propertyName,V value){
        Object[] values = { value };
        return selectRejected(objectCollection, propertyName, values);
    }

    /**
     * 循环遍历 <code>objectCollection</code> ,返回 当bean propertyName 属性值 都不在values 时候的list.
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param values
     *            the values
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see com.feilong.core.util.predicate.ArrayContainsPredicate
     * @see #selectRejected(Collection, Predicate)
     */
    @SafeVarargs
    public static <O, V> List<O> selectRejected(Collection<O> objectCollection,String propertyName,V...values){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        Predicate<O> predicate = new ArrayContainsPredicate<O>(propertyName, values);
        return selectRejected(objectCollection, predicate);
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
     * <pre>
     * List&lt;User&gt; objectCollection = new ArrayList&lt;User&gt;();
     * objectCollection.add(new User(&quot;张飞&quot;, 23));
     * objectCollection.add(new User(&quot;关羽&quot;, 24));
     * objectCollection.add(new User(&quot;刘备&quot;, 25));
     * 
     * List&lt;String&gt; list = new ArrayList&lt;String&gt;();
     * list.add(&quot;张飞&quot;);
     * list.add(&quot;刘备&quot;);
     * LOGGER.info(JsonUtil.format(CollectionsUtil.select(objectCollection, &quot;name&quot;, list)));
     * </pre>
     * 
     * <p>
     * 返回 关羽 的对象集合
     * </p>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param values
     *            the values
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see com.feilong.core.util.predicate.CollectionContainsPredicate
     * @see #selectRejected(Collection , Predicate)
     * @since 1.5.0
     */
    public static <O, V> List<O> selectRejected(Collection<O> objectCollection,String propertyName,Collection<V> values){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        Predicate<O> predicate = new CollectionContainsPredicate<O>(propertyName, values);
        return selectRejected(objectCollection, predicate);
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
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyList()}
     * @see org.apache.commons.collections4.CollectionUtils#selectRejected(Iterable, Predicate)
     * @since 1.4.0
     */
    public static <O> List<O> selectRejected(Collection<O> objectCollection,Predicate<O> predicate){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyList();
        }
        return (List<O>) CollectionUtils.selectRejected(objectCollection, predicate);
    }

    //******************************getPropertyValueMap*********************************************************************
    /**
     * 解析对象集合,以 <code>keyPropertyName</code>属性值为key, <code>valuePropertyName</code>属性值为值,组成map返回.
     * 
     * <p>
     * 注意:返回的是 {@link LinkedHashMap}
     * </p>
     * <br>
     * 使用 {@link PropertyUtil#getProperty(Object, String)}取到对象特殊属性. <br>
     * 支持属性级联获取,支付获取数组,集合,map,自定义bean等属性
     * 
     * <h3>使用示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre>
     * List&lt;User&gt; testList = new ArrayList&lt;User&gt;();
     * testList.add(new User(&quot;张飞&quot;, 23));
     * testList.add(new User(&quot;关羽&quot;, 24));
     * testList.add(new User(&quot;刘备&quot;, 25));
     * 
     * Map&lt;String, Integer&gt; map = CollectionsUtil.getFieldValueMap(testList, &quot;name&quot;, &quot;age&quot;);
     * 
     * 返回 :
     * 
     * "关羽": 24,
     * "张飞": 23,
     * "刘备": 25
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
     *            the key property name
     * @param valuePropertyName
     *            the value property name
     * @return 解析对象集合,以 <code>keyPropertyName</code>属性值为key, <code>valuePropertyName</code>属性值为值,组成map返回<br>
     *         if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyMap()}
     * @see com.feilong.core.bean.BeanUtil#getProperty(Object, String)
     * @see org.apache.commons.beanutils.PropertyUtils#getProperty(Object, String)
     */
    public static <K, V, O> Map<K, V> getPropertyValueMap(Collection<O> objectCollection,String keyPropertyName,String valuePropertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notEmpty(keyPropertyName, "keyPropertyName can't be null/empty!");
        Validate.notEmpty(valuePropertyName, "valuePropertyName can't be null/empty!");

        Map<K, V> map = new LinkedHashMap<K, V>();

        for (O bean : objectCollection){
            @SuppressWarnings("unchecked")
            K key = (K) PropertyUtil.getProperty(bean, keyPropertyName);
            @SuppressWarnings("unchecked")
            V value = (V) PropertyUtil.getProperty(bean, valuePropertyName);

            map.put(key, value);
        }
        return map;
    }

    //*******************************group*********************************************************

    /**
     * Group 对象(如果propertyName 存在相同的值,那么这些值,将会以list的形式 put到map中).
     * 
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
      * 
      * {@code
        List<User> testList = new ArrayList<User>();
        testList.add(new User("张飞", 23));
        testList.add(new User("刘备", 25));
        testList.add(new User("刘备", 25));
    
        Map<String, List<User>> map = CollectionsUtil.group(testList, "name");
        LOGGER.info(JsonUtil.format(map));
      * }
      * 
      * 返回 :
      * 
    {
        "张飞": [        {
            "age": 23,
            "name": "张飞"
        }],
        "刘备":         [
                        {
                "age": 25,
                "name": "刘备"
            },
                        {
                "age": 25,
                "name": "刘备"
            }
        ]
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            注意,此处的T其实是 Object 类型, 需要区别对待,比如从excel中读取的类型是String,那么就不能简简单单的使用Integer来接收, 因为不能强制转换
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if objectCollection isNullOrEmpty ,will return {@link Collections#emptyMap()}; <br>
     *         if propertyName isNullOrEmpty,will throw {@link NullPointerException}
     * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
     * @see com.feilong.core.lang.ArrayUtil#group(Object[], String)
     * @see #groupOne(Collection, String)
     * @since 1.0.8
     */
    public static <T, O> Map<T, List<O>> group(Collection<O> objectCollection,String propertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }
        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        //视需求  可以换成 HashMap 或者TreeMap
        Map<T, List<O>> map = new LinkedHashMap<T, List<O>>(objectCollection.size());

        for (O o : objectCollection){
            T t = PropertyUtil.getProperty(o, propertyName);
            List<O> valueList = map.get(t);
            if (null == valueList){
                valueList = new ArrayList<O>();
            }
            valueList.add(o);
            map.put(t, valueList);
        }
        return map;
    }

    /**
     * 循环 <code>objectCollection</code>,统计 {@code propertyName}出现的次数.
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if objectCollection isNullOrEmpty ,will return {@link Collections#emptyMap()}; <br>
     *         if propertyName isNullOrEmpty,will throw {@link NullPointerException}
     * @see #groupCount(Collection , Predicate, String)
     */
    public static <T, O> Map<T, Integer> groupCount(Collection<O> objectCollection,String propertyName){
        return groupCount(objectCollection, null, propertyName);
    }

    /**
     * 循环 <code>objectCollection</code>,只选择 符合 <code>includePredicate</code>的对象,统计 {@code propertyName}出现的次数.
     *
     * @param <T>
     *            the generic type
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param includePredicate
     *            只选择 符合 <code>includePredicate</code>的对象,如果是null 则统计集合中全部的Object
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if objectCollection isNullOrEmpty ,will return {@link Collections#emptyMap()}; <br>
     *         if propertyName isNullOrEmpty,will throw {@link NullPointerException}
     * @since 1.2.0
     */
    public static <T, O> Map<T, Integer> groupCount(Collection<O> objectCollection,Predicate<O> includePredicate,String propertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        Map<T, Integer> map = new LinkedHashMap<T, Integer>();

        for (O o : objectCollection){
            if (null != includePredicate){
                //跳出循环标记
                boolean continueFlag = !includePredicate.evaluate(o);
                if (continueFlag){
                    continue;
                }
            }

            T t = PropertyUtil.getProperty(o, propertyName);
            Integer count = map.get(t);
            if (null == count){
                count = 0;
            }
            count = count + 1;

            map.put(t, count);
        }
        return map;
    }

    /**
     * Group one(map只put第一个匹配的元素).
     * 
     * <p>
     * 返回的map,key是 <code>objectCollection</code>中的元素对象中 <code>propertyName</code>的值,value是 <code>objectCollection</code>中的元素对象
     * </p>
     * 
     * <p>
     * 间接的可以做到基于某个属性值去重的效果
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
      * 
      * {@code
      *     List<User> testList = new ArrayList<User>();
      *     testList.add(new User("张飞", 23));
      *     testList.add(new User("刘备", 25));
      *     testList.add(new User("刘备", 25));
      * 
      *     Map<String, User> map = CollectionsUtil.groupOne(testList, "name");
      *     LOGGER.info(JsonUtil.format(map));
      * }
      * 
      * 返回 :
      * 
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if objectCollection isNullOrEmpty ,will return {@link Collections#emptyMap()}; <br>
     *         if propertyName isNullOrEmpty,will throw {@link NullPointerException}
     * @see #group(Collection, String)
     * @since 1.0.8
     */
    public static <T, O> Map<T, O> groupOne(Collection<O> objectCollection,String propertyName){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }

        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        //视需求  可以换成 HashMap 或者TreeMap
        Map<T, O> map = new LinkedHashMap<T, O>(objectCollection.size());

        for (O o : objectCollection){
            T key = PropertyUtil.getProperty(o, propertyName);

            if (!map.containsKey(key)){
                map.put(key, o);
            }else{
                if (LOGGER.isDebugEnabled()){
                    LOGGER.debug(
                                    "Abandoned except the first value outside,map:{},containsKey key:[{}],",
                                    JsonUtil.format(map.keySet()),
                                    key);
                }
            }
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
     * <pre>
    {@code
            List<User> list = new ArrayList<User>();
    
            User user1 = new User(2L);
            user1.setAge(18);
            list.add(user1);
    
            User user2 = new User(3L);
            user2.setAge(30);
            list.add(user2);
    
            Map<String, Number> map = CollectionsUtil.avg(list, 2, "id", "age");
    
            LOGGER.info(JsonUtil.format(map));
    }
    
    返回:
    {
        "id": 2.5,
        "age": 24
    }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param scale
     *            平均数值的精度
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyMap()}
     * @see #sum(Collection, String...)
     */
    public static <O> Map<String, Number> avg(Collection<O> objectCollection,int scale,String...propertyNames){
        //总分
        Map<String, Number> sumMap = sum(objectCollection, propertyNames);

        int size = objectCollection.size();
        Map<String, Number> map = new LinkedHashMap<String, Number>(size);//视需求  可以换成 HashMap 或者TreeMap

        for (Map.Entry<String, Number> entry : sumMap.entrySet()){
            String key = entry.getKey();
            Number value = entry.getValue();

            map.put(key, NumberUtil.getDivideValue(ConvertUtil.toBigDecimal(value), size, scale));
        }
        return map;
    }

    /**
     * 算术平均值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            List<User> list = new ArrayList<User>();
            list.add(new User(2L));
            list.add(new User(5L));
            list.add(new User(5L));
    }
    
    返回: 4.00
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param scale
     *            平均数值的精度
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return the map< string, list< o>>
     * @see #sum(Collection, String...)
     * 
     * @since 1.5.0
     */
    public static <O> Number avg(Collection<O> objectCollection,int scale,String propertyName){
        String[] propertyNames = { propertyName };
        return avg(objectCollection, scale, propertyNames).get(propertyName);
    }

    //***********************************sum*************************************************************
    /**
     * 总和,计算集合对象内指定的属性名值的总和.
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            List<User> list = new ArrayList<User>();
    
            User user1 = new User(2L);
            user1.setAge(18);
            list.add(user1);
    
            User user2 = new User(3L);
            user2.setAge(30);
            list.add(user2);
    
            Map<String, Number> map = CollectionsUtil.sum(list, "id", "age");
            LOGGER.info(JsonUtil.format(map));
    }
    * 返回:
    * {
         "id": 5,
         "age": 48
      }
     * 
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
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return if Validator.isNullOrEmpty(objectCollection),return {@link Collections#emptyMap()}
     */
    public static <O> Map<String, Number> sum(Collection<O> objectCollection,String...propertyNames){
        if (Validator.isNullOrEmpty(objectCollection)){
            return Collections.emptyMap();
        }

        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");

        //总分
        Map<String, Number> sumMap = new LinkedHashMap<String, Number>(objectCollection.size());

        for (O o : objectCollection){
            for (String propertyName : propertyNames){
                Number propertyValue = PropertyUtil.getProperty(o, propertyName);

                Number mapPropertyNameValue = sumMap.get(propertyName);
                if (null == mapPropertyNameValue){//如果通过反射某个元素值是null,则使用默认值0 代替
                    mapPropertyNameValue = 0;
                }
                sumMap.put(propertyName, NumberUtil.getAddValue(mapPropertyNameValue, propertyValue));
            }
        }
        return sumMap;
    }

    /**
     * 总和,计算集合对象内指定的属性名值的总和.
     * 
     * <p>
     * 如果通过反射某个元素值是null,则使用默认值0代替,再进行累加
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            List<User> list = new ArrayList<User>();
            list.add(new User(2L));
            list.add(new User(5L));
            list.add(new User(5L));
            
         Number number = CollectionsUtil.sum(list, "id");
         LOGGER.info("" + number);
    }
    
    返回: 12
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * 当你需要写这样的代码的时候,
     * 
     * <pre>
    {@code
    protected Integer getCookieShoppingCartLinesQty(List<CookieShoppingCartLine> cartLineList)} {  
    
    {@code
        Integer qty = 0;
        //获取cookie中的购物车行集合
        if (null != cartLineList && cartLineList.size() > 0)}{  {@code
            for (Iterator iterator = cartLineList.iterator(); iterator.hasNext();)} {  {@code
                CookieShoppingCartLine cookieShoppingCartLine = (CookieShoppingCartLine) iterator.next();
                qty += cookieShoppingCartLine.getQuantity();
            }}
        }
        return qty;
    }
    }
     * </pre>
     * 
     * 你可以写成:
     * 
     * <pre>
     {@code 
     protected Integer getCookieShoppingCartLinesQty(List<CookieShoppingCartLine> cartLineList)}{ {@code 
         return Validator.isNullOrEmpty(cartLineList) ? 0 : CollectionsUtil.sum(cartLineList, "quantity").intValue();
     }}
     }
     * </pre>
     * 
     * @param <O>
     *            the generic type
     * @param objectCollection
     *            the object collection
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @return 如果 objectCollection is null or empty, 那么返回 <code>null</code>
     * @see #sum(Collection, String...)
     * @since 1.5.0
     */
    public static <O> Number sum(Collection<O> objectCollection,String propertyName){
        Validate.notEmpty(propertyName, "propertyName can't be null/empty!");

        String[] propertyNames = { propertyName };
        return sum(objectCollection, propertyNames).get(propertyName);
    }
}