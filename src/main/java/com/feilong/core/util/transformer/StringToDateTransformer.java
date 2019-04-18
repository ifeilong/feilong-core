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

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.date.DateUtil.toDate;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.Validate;

import com.feilong.core.DatePattern;

/**
 * 字符串转成日期的转换器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.13.2
 */
public class StringToDateTransformer implements Transformer<String, Date>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4826259468102686057L;

    /** 模式,可以使用 {@link DatePattern}. */
    private final String      pattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new date to string transformer.
     * 
     * <p>
     * 如果 <code>pattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>pattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     * 
     * @param pattern
     *            模式,可以使用 {@link DatePattern}
     */
    public StringToDateTransformer(String pattern){
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
    public Date transform(String value){
        return isNullOrEmpty(value) ? null : toDate(value, pattern);
    }
}
