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

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.ObjectUtils;

import com.feilong.core.bean.PropertyUtil;

/**
 * 调用 {@link com.feilong.core.bean.PropertyUtil#getProperty(Object, String)} 匹配属性值.
 *
 * @author feilong
 * @param <T>
 *            the generic type
 * @see org.apache.commons.beanutils.BeanPredicate
 * @see org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate
 * @since 1.2.0
 */
//XXX 如果{@link org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate}支持泛型,那么这么类将会废弃
public class BeanPropertyValueEqualsPredicate<T> implements Predicate<T>{

    /**
     * 泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     * {@link <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>}.
     */
    private final String propertyName;

    /** The value. */
    private final Object value;

    /**
     * The Constructor.
     *
     * @param propertyName
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param value
     *            the value
     */
    public BeanPropertyValueEqualsPredicate(String propertyName, Object value){
        this.propertyName = propertyName;
        this.value = value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @SuppressWarnings("deprecation")
    @Override
    public boolean evaluate(T object){
        Object property = PropertyUtil.getProperty(object, propertyName);
        return ObjectUtils.equals(property, value);
    }
}