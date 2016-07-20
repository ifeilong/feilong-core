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

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.math.BigDecimal;

import com.feilong.core.NumberPattern;
import com.feilong.core.lang.NumberUtil;

import net.sf.json.JsonConfig;

/**
 * The Class BigDecimalJsonValueProcessor.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public class BigDecimalJsonValueProcessor extends AbstractJsonValueProcessor{

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
    }

    /**
     * The Constructor.
     *
     * @param numberPattern
     *            你可以使用 {@link com.feilong.core.NumberPattern}
     */
    public BigDecimalJsonValueProcessor(String numberPattern){
        this.numberPattern = numberPattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.tools.jsonlib.processor.AbstractJsonValueProcessor#processValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    protected Object processValue(Object value,JsonConfig jsonConfig){
        return value == null ? EMPTY : (value instanceof BigDecimal ? NumberUtil.toString((Number) value, numberPattern) : value);
    }
}