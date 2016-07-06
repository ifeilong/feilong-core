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

import static com.feilong.core.Validator.isNullOrEmpty;

import java.util.Collection;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;

import com.feilong.core.bean.PropertyUtil;

/**
 * The Class BeanPredicateUtil.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.PredicateUtils
 * @since 1.8.0
 */
public final class BeanPredicateUtil{

    /** Don't let anyone instantiate this class. */
    private BeanPredicateUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Equal predicate.
     *
     * @param <T>
     *            the generic type
     * @param <V>
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValue
     *            the property value
     * @return the predicate
     */
    public static <T, V> Predicate<T> equalPredicate(String propertyName,V propertyValue){
        return new BeanPredicate<T>(propertyName, PredicateUtils.equalPredicate(propertyValue));
    }

    /**
     * Contains predicate.
     * 
     * <p>
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用
     * {@link org.apache.commons.lang3.ArrayUtils#contains(Object[], Object) ArrayUtils.contains} 判断是否在 <code>values</code>数组中.
     * </p>
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
     * @return the predicate
     */
    @SafeVarargs
    public static <T, V> Predicate<T> containsPredicate(final String propertyName,final V...propertyValues){
        return new BeanPredicate<T>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return org.apache.commons.lang3.ArrayUtils.contains(propertyValues, propertyValue);
            }
        });
    }

    /**
     * Contains predicate.
     * 
     * <p>
     * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用{@link java.util.Collection#contains(Object)
     * Collection.contains} 判断是否在<code>values</code>集合中.
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param <V>
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the property value list
     * @return the predicate
     */
    public static <T, V> Predicate<T> containsPredicate(final String propertyName,final Collection<V> propertyValueList){
        return new BeanPredicate<T>(propertyName, new Predicate<V>(){

            @Override
            public boolean evaluate(V propertyValue){
                return isNullOrEmpty(propertyValueList) ? false : propertyValueList.contains(propertyValue);
            }
        });
    }
}
