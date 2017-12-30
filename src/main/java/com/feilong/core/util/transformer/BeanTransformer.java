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
package com.feilong.core.util.transformer;

import java.io.Serializable;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.reflect.ConstructorUtil;

/**
 * 将传入的bean转成指定类型 <code>toBeanType</code> 的Bean 的转换器.
 * 
 * <p>
 * 如果只需要转换特定的属性(忽略其他属性),此时可以使用 <code>includePropertyNames</code> 属性;<br>
 * 如果没有传递此属性,那么默认会转换所有的属性
 * </p>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <I>
 *            输入类型
 * @param <O>
 *            输出类型
 * @see org.apache.commons.beanutils.BeanToPropertyValueTransformer
 * @since 1.10.1
 */
public class BeanTransformer<I, O> implements Transformer<I, O>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5873559262715038376L;

    /**
     * 转成Bean的类型.
     */
    private final Class<O>    toBeanType;

    /**
     * 指定转换属性的名字(如果).
     */
    private final String[]    includePropertyNames;

    //---------------------------------------------------------------

    /**
     * Instantiates a new bean transformer.
     * 
     * <p>
     * 如果 <code>toBeanType</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param toBeanType
     *            转成Bean的类型.
     * @param includePropertyNames
     *            包含的属性数组名字数组,(can be nested/indexed/mapped/combo),如果是null或者empty,那么复制所有的属性
     */
    public BeanTransformer(Class<O> toBeanType, String...includePropertyNames){
        Validate.notNull(toBeanType, "toBeanType can't be null!");
        this.toBeanType = toBeanType;
        this.includePropertyNames = includePropertyNames;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
     */
    @Override
    public O transform(I inputBean){
        if (null == inputBean){
            return null;
        }

        O outputBean = ConstructorUtil.newInstance(toBeanType);

        PropertyUtil.copyProperties(outputBean, inputBean, includePropertyNames);
        return outputBean;
    }
}
