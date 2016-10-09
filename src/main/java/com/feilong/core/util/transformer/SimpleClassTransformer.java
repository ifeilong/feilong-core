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

import com.feilong.core.bean.ConvertUtil;

import static com.feilong.core.bean.ConvertUtil.convert;

/**
 * 简单的将对象转成指定 <code>targetType</code> 类型的转换器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            原来的类型
 * @param <V>
 *            转成的结果类型
 * @see ConvertUtil#convert(Object, Class)
 * @since 1.9.2
 */
public class SimpleClassTransformer<T, V> implements Transformer<T, V>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 809439581555072949L;

    /** 需要被转成什么目标类型. */
    private final Class<V>    targetType;

    /**
     * Instantiates a new convert transformer.
     * 
     * <p>
     * 如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param targetType
     *            需要被转成什么目标类型
     */
    public SimpleClassTransformer(Class<V> targetType){
        Validate.notNull(targetType, "targetType can't be null!");
        this.targetType = targetType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
     */
    @Override
    public V transform(final T input){
        return convert(input, targetType);
    }
}