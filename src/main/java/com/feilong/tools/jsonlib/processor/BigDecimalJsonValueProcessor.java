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
package com.feilong.tools.jsonlib.processor;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.feilong.core.NumberPattern;
import com.feilong.core.lang.NumberUtil;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * The Class BigDecimalJsonValueProcessor.
 *
 * @author feilong
 * @version 1.2.2 2015年7月10日 下午11:16:48
 * @since 1.2.2
 */
public class BigDecimalJsonValueProcessor implements JsonValueProcessor{

    /**
     * The number pattern.
     * 
     * @see com.feilong.core.NumberPattern
     */
    private String numberPattern = NumberPattern.TWO_DECIMAL_POINTS;

    /**
     * The Constructor.
     */
    public BigDecimalJsonValueProcessor(){
        super();
    }

    /**
     * The Constructor.
     *
     * @param numberPattern
     *            the number pattern
     */
    public BigDecimalJsonValueProcessor(String numberPattern){
        super();
        this.numberPattern = numberPattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object value,JsonConfig jsonConfig){
        return process(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key,Object value,JsonConfig jsonConfig){
        return process(value);
    }

    /**
     * Process.
     *
     * @param value
     *            the value
     * @return the object
     */
    private Object process(Object value){
        return value == null ? StringUtils.EMPTY
                        : (value instanceof BigDecimal ? NumberUtil.toString((Number) value, numberPattern) : value);
    }
}