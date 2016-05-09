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

import java.util.Enumeration;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.iterators.EnumerationIterator;

import com.feilong.core.Validator;

/**
 * {@link Enumeration} 工具类.
 *
 * @author feilong
 * @version 1.5.3 2016年4月18日 上午2:34:38
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

    /**
     * 判断 <code>enumeration</code> 枚举里面,是否有 指定的元素 <code>value</code>.
     * 
     * <h3>代码流程:</h3>
     * <blockquote>
     * <ol>
     * <li><code>if isNullOrEmpty(enumeration) return false</code></li>
     * <li>循环枚举里面的每个元素,调用 {@link org.apache.commons.lang3.ObjectUtils#equals(Object, Object)},如果equals 返回true</li>
     * </ol>
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param enumeration
     *            the enumeration
     * @param value
     *            指定的元素
     * @return true, if contains
     * @see "org.springframework.util.CollectionUtils#contains(Enumeration, Object)"
     * @see org.apache.commons.lang3.ObjectUtils#equals(Object, Object)
     * @see org.apache.commons.collections4.iterators#EnumerationIterator
     * @see org.apache.commons.collections4.IteratorUtils#contains(java.util.Iterator, Object)
     */
    public static <O> boolean contains(Enumeration<O> enumeration,O value){
        if (Validator.isNullOrEmpty(enumeration)){
            return false;
        }
        EnumerationIterator<O> iterator = new EnumerationIterator<O>(enumeration);
        return IteratorUtils.contains(iterator, value);
    }
}
