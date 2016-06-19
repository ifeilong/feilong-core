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

import java.util.Collection;

import org.apache.commons.collections4.Predicate;

import com.feilong.core.Validator;
import com.feilong.core.bean.PropertyUtil;

/**
 * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值,使用{@link java.util.Collection#contains(Object)
 * Collection.contains} 判断是否在<code>values</code>集合中.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
 * @since 1.5.0
 */
public class CollectionContainsPredicate<T> implements Predicate<T>{

    /**
     * 泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     * <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>.
     */
    private final String        propertyName;

    /** The value. */
    private final Collection<?> propertyValueList;

    /**
     * The Constructor.
     *
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValueList
     *            the values
     */
    public CollectionContainsPredicate(String propertyName, Collection<?> propertyValueList){
        this.propertyValueList = propertyValueList;
        this.propertyName = propertyName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(T object){
        return Validator.isNullOrEmpty(propertyValueList) ? false
                        : propertyValueList.contains(PropertyUtil.getProperty(object, propertyName));
    }
}