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
package com.feilong.core.util.closure;

import org.apache.commons.collections4.Closure;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.PropertyUtil;

/**
 * {@link org.apache.commons.collections4.Closure} 实现,用来更新指定属性的指定值.
 * <h3>构造函数:</h3>
 * 
 * <blockquote>
 * 
 * BeanPropertyValueChangeClosure 构造函数提供两个参数, 属性名称和指定的属性值
 * 
 * <pre class="code">
 * public BeanPropertyValueChangeClosure(String propertyName, Object propertyValue)
 * </pre>
 * 
 * <b>注意:</b> Possibly indexed and/or nested name of the property to be
 * modified,参见<a href="../../bean/BeanUtil.html#propertyName">propertyName</a>.
 * </blockquote>
 * 
 * <h3>典型的使用示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * // create the closure
 * BeanPropertyValueChangeClosure closure = new BeanPropertyValueChangeClosure("activeEmployee", Boolean.TRUE);
 *
 * // update the Collection
 * CollectionUtils.forAllDo(peopleCollection, closure);
 * </pre>
 * 
 * 上面的示例将会提取 <code>peopleCollection</code> 的每个 person 对象, 并且更新<code>activeEmployee</code> 属性值为<code>true</code>.
 * 
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @see org.apache.commons.beanutils.BeanPropertyValueChangeClosure
 * @see org.apache.commons.collections4.CollectionUtils#forAllDo(Iterable, Closure)
 * @see org.apache.commons.collections4.IterableUtils#forEach(Iterable, Closure)
 * @since 1.10.2
 */
public class BeanPropertyValueChangeClosure<T> implements Closure<T>{

    /**
     * 指定bean对象排序属性名字.
     * 
     * <p>
     * 泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     * <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     * </p>
     */
    private final String propertyName;

    /** 指定的属性值. */
    private final Object propertyValue;

    //---------------------------------------------------------------

    /**
     * Instantiates a new bean property value change closure.
     * 
     * <p>
     * 如果 <code>propertyName</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>propertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param propertyName
     *            指定bean对象排序属性名字.
     *            <p>
     *            泛型T对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../../bean/BeanUtil.html#propertyName">propertyName</a>
     *            </p>
     * @param propertyValue
     *            the value
     */
    public BeanPropertyValueChangeClosure(String propertyName, Object propertyValue){
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Closure#execute(java.lang.Object)
     */
    @Override
    public void execute(T input){
        if (null == input){//如果 input是null,跳过去
            return;
        }

        PropertyUtil.setProperty(input, propertyName, propertyValue);
    }
}
