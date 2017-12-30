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

import com.feilong.core.NumberPattern;
import com.feilong.core.lang.NumberUtil;

/**
 * 数字转成字符串的转换器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.7
 */
public class NumberToStringTransformer implements Transformer<Number, String>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8811898394834078637L;

    /** 模式,可以使用 {@link NumberPattern}. */
    private final String      pattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new number to string transformer.
     * 
     * <p>
     * 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>pattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     * 
     * @param pattern
     *            模式,可以使用 {@link NumberPattern}
     */
    public NumberToStringTransformer(String pattern){
        Validate.notBlank(pattern, "pattern can't be blank!");
        this.pattern = pattern;
    }

    //---------------------------------------------------------------
    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
     */
    @Override
    public String transform(Number value){
        return null == value ? null : NumberUtil.toString(value, pattern);
    }
}
