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

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.util.ArrayUtil;

/**
 * 调用 {@link PropertyUtil#getProperty(Object, String)} 获得 <code>propertyName</code>的值，判断是否 {@link ArrayUtil#isContain(Object[], Object)} 在
 * <code>values</code>数组中.
 *
 * @author feilong
 * @version 1.2.0 2015年4月27日 下午3:12:32
 * @since 1.2.0
 * @see com.feilong.core.bean.PropertyUtil#getProperty(Object, String)
 * @see com.feilong.core.util.ArrayUtil#isContain(Object[], Object)
 */
public class ArrayContainsPredicate implements Predicate{

    /** The value. */
    private final Object[] values;

    /** The property name. */
    private final String   propertyName;

    /**
     * The Constructor.
     *
     * @param propertyName
     *            the property name
     * @param values
     *            the values
     */
    public ArrayContainsPredicate(String propertyName, Object...values){
        this.values = values;
        this.propertyName = propertyName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(Object object){
        Object property = PropertyUtil.getProperty(object, propertyName);
        return ArrayUtil.isContain(values, property);
    }
}