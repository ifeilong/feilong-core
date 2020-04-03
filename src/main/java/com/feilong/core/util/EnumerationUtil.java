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

import java.util.Enumeration;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.EnumerationIterator;

/**
 * {@link Enumeration}工具类.
 * 
 * <p>
 * {@link Enumeration}接口是JDK 1.0时就推出的,是最早的迭代输出接口,最早使用Vector时就是使用{@link Enumeration}接口进行输出的。<br>
 * 虽然{@link Enumeration}是一个旧的类,但是在JDK1.5之后为Enumeration类进行了扩充,增加了泛型的操作应用
 * </p>
 * 
 * <h3>为什么还要继续使用{@link Enumeration}?</h3>
 * 
 * <blockquote>
 * <p>
 * {@link Enumeration}和Iterator接口的功能非常类似,而且{@link Enumeration}接口中方法的名称也比 接口中的方法名称长很多,那为什么还要继续使用{@link Enumeration}呢？
 * </p>
 * <p>
 * 在旧的操作中依然会使用{@link Enumeration}接口。<br>
 * 实际上Java的发展经历了很长的时间,一些比较古老的系统或是类库的方法中(例如,本系列的下一步Web开发中就存在这样的操作方法)还在使用{@link Enumeration}接口,所以掌握其操作也是很有必要的。
 * </p>
 * <p>
 * 而Iterator是JDK1.2才添加的接口,它也是为了HashMap、ArrayList等集合提供遍历接口。<br>
 * Iterator是支持fail-fast机制的:当多个线程对同一个集合的内容进行操作时,就可能会产生fail-fast事件。
 * </p>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.EnumerationUtils
 * @since 1.5.3
 */
public final class EnumerationUtil{

    /** Don't let anyone instantiate this class. */
    private EnumerationUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 判断<code>enumeration</code>枚举里面,是否有指定的元素<code>value</code>.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * EnumerationUtil.contains(null, "a")                                          =   false
     * 
     * EnumerationUtil.contains(toEnumeration(toList("4", "5")), "a")               =   false
     * EnumerationUtil.contains(toEnumeration(toList("4", "5")), "4")               =   true
     * EnumerationUtil.contains(toEnumeration(toList("4", "5", "")), "")            =   true
     * EnumerationUtil.contains(toEnumeration(toList("4", "5", "", null)), null)    =   true
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param enumeration
     *            the enumeration
     * @param value
     *            指定的元素
     * @return 如果 <code>enumeration</code> 是null或者empty,返回 false<br>
     *         否则如果 contains 返回true,<br>
     *         其他返回false
     * @see "org.springframework.util.CollectionUtils#contains(Enumeration, Object)"
     * @see org.apache.commons.collections4.iterators.EnumerationIterator
     * @see org.apache.commons.collections4.IteratorUtils#contains(java.util.Iterator, Object)
     */
    public static <O> boolean contains(Enumeration<O> enumeration,O value){
        return isNotNullOrEmpty(enumeration) && IteratorUtils.contains(new EnumerationIterator<O>(enumeration), value);
    }
}
