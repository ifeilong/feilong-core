/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ObjectUtil;

/**
 * {@link List}工具类.
 * 
 * @author 金鑫 2010-3-2 下午03:20:12
 * @since 1.0.0
 */
public final class ListUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ListUtil.class);

    /** Don't let anyone instantiate this class. */
    private ListUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 用于 自定义标签/ 自定义el.
     * 
     * @param collection
     *            一个集合,将会被转成Iterator,可以为逗号隔开的字符串,会被分隔成Iterator.
     * @param value
     *            任意类型的值,最终toString 判断比较.
     * @return true, if successful
     * @see ObjectUtil#toIterator(Object)
     * @see #isContain(Iterator, Object)
     * @deprecated
     */
    @Deprecated
    public static boolean isContainTag(Object collection,Object value){
        Iterator<?> iterator = ObjectUtil.toIterator(collection);
        return isContain(iterator, value);
    }

    /**
     * iterator是否包含某个值.
     * 
     * @param iterator
     *            iterator
     * @param value
     *            value
     * @return iterator是否包含某个值,如果iterator为null/empty,则返回false
     * @see Iterator#hasNext()
     * @see Iterator#next()
     * @deprecated 代码这里不是很严谨 ,需要重构
     */
    @Deprecated
    public static boolean isContain(Iterator<?> iterator,Object value){
        boolean flag = false;
        if (Validator.isNotNullOrEmpty(iterator)){
            Object object = null;
            while (iterator.hasNext()){
                object = iterator.next();
                if (object.toString().equals(value.toString())){
                    flag = true;
                    break;
                }
            }
        }else{
            log.debug("iterator is null/empty");
        }
        return flag;
    }

    /**
     * 从 <code>collection</code>中 删除 所有的 <code>remove</code>. 返回剩余的集合. The cardinality of an
     * element <code>e</code> in the returned collection is the same as the cardinality of <code>e</code> in <code>collection</code> unless
     * <code>remove</code> contains <code>e</code>, in which
     * case the cardinality is zero.这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用 <code>collection.removeAll(remove);</code>.
     *
     * @param <T>
     *            the generic type
     * @param collection
     *            the collection from which items are removed (in the returned collection)
     * @param remove
     *            the items to be removed from the returned <code>collection</code>
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see org.apache.commons.collections.ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 3.2
     * @since 1.0.8
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> removeAll(Collection<T> collection,Collection<T> remove){
        return org.apache.commons.collections.ListUtils.removeAll(collection, remove);
    }

    /**
     * 从 <code>collection</code>中 删除 <code>removeElement</code>. 返回剩余的集合. 这个方法非常有用,如果你不想修改 <code>collection</code>的话,不能调用
     * <code>collection.remove(removeElement);</code>.
     *
     * @param <T>
     *            the generic type
     * @param collection
     *            the collection from which items are removed (in the returned collection)
     * @param removeElement
     *            the remove element
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     *         any elements that also occur in <code>remove</code>.
     * @see org.apache.commons.collections.ListUtils#removeAll(Collection, Collection)
     * @since Commons Collections 3.2
     * @since 1.0.8
     */
    public static <T> List<T> remove(Collection<T> collection,T removeElement){
        Collection<T> remove = new ArrayList<T>();
        remove.add(removeElement);
        return removeAll(collection, remove);
    }

    /**
     * 去重(如果原collection是有序的,那么会保留原collection元素顺序).
     * 
     * @param <T>
     *            the generic type
     * @param collection
     *            the item src list
     * @return if Validator.isNullOrEmpty(collection) 返回null<br>
     *         else 返回的是 {@link ArrayList}
     * @see ArrayList#ArrayList(java.util.Collection)
     * @see LinkedHashSet#LinkedHashSet(Collection)
     * @see <a
     *      href="http://www.oschina.net/code/snippet_117714_2991?p=2#comments">http://www.oschina.net/code/snippet_117714_2991?p=2#comments</a>
     */
    public static <T> List<T> removeDuplicate(Collection<T> collection){
        if (Validator.isNullOrEmpty(collection)){
            return null;
        }
        // 效率问题？contains的本质就是遍历.
        // 在100W的list当中执行0.546秒，而contains，我则没耐心去等了.顺便贴一下在10W下2段代码的运行时间.
        // [foo1] 100000 -> 50487 : 48610 ms.
        // [foo2] 100000 -> 50487 : 47 ms.
        return new ArrayList<T>(new LinkedHashSet<T>(collection));
    }

    /**
     * 获得 list第一个元素.
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            list
     * @return 第一个元素<br>
     *         if (Validator.isNotNullOrEmpty(list),return null
     */
    public static <T> T getFirstItem(List<T> list){
        if (Validator.isNotNullOrEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    // ***********************************************************************************
    /**
     * list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动<br>
     * 如:
     * 
     * <pre>
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toStringReplaceBrackets(testList)-----{@code >}(xinge, feilong)
     * </pre>
     * 
     * @param list
     *            list集合
     * @return list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动
     */
    public static String toStringReplaceBrackets(List<String> list){
        return list.toString().replace('[', '(').replace(']', ')');
    }

    /**
     * list集合转换成字符串,移除[]中括号符号,并去除空格 <br>
     * 如:
     * 
     * <pre>
     * {@code
     * 
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toStringRemoveBrackets(testList)----->xinge,feilong
     * }
     * </pre>
     * 
     * @param list
     *            list集合
     * @return list集合转换成字符串,移除[]中括号符号,并去除空格
     */
    public static String toStringRemoveBrackets(List<String> list){
        String s = list.toString();
        return s.substring(1, s.length() - 1).replaceAll(" ", "");
    }

    /**
     * list集合转换成字符串 如:
     * 
     * <pre>
     * {@code
     * 
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toString(testList,true)----->'xinge','feilong'
     * toString(testList,false)----->xinge,feilong
     * }
     * </pre>
     * 
     * @param list
     *            list集合
     * @param isAddQuotation
     *            是否增加单引号
     * @return list集合转换成字符串
     */
    public static String toString(List<String> list,boolean isAddQuotation){
        String returnValue = toStringRemoveBrackets(list);
        if (isAddQuotation){
            returnValue = "'" + returnValue.replaceAll(",", "','") + "'";
        }
        return returnValue;
    }
}