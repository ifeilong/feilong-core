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

import com.feilong.core.lang.NumberUtil;

import static com.feilong.core.NumberPattern.TWO_DECIMAL_POINTS;

import net.sf.json.JsonConfig;

/**
 * {@link BigDecimal}数字json value处理器.
 * 
 * <p>
 * 如果不使用这个处理器,对于 BigDecimal格式输出成json,在json to string 字符串的时候,会剔除末尾的0和小数点,会出现循环截断的情况,参见
 * {@link net.sf.json.util.JSONUtils#numberToString(Number)}:
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * User user = new User("feilong1", 24);
 * user.setMoney(toBigDecimal("99999999.00"));
 * 
 * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
 * javaToJsonConfig.setIncludes("name", "age", "money");
 * 
 * LOGGER.debug(JsonUtil.format(user, javaToJsonConfig));
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
 * {
 * "age": 24,
 * "name": "feilong1",
 * "money": 99999999
 * }
 * </pre>
 * 
 * <p>
 * 可以看出,money少了后面的 <code>.00</code>
 * </p>
 * 
 * </blockquote>
 * 
 * <p>
 * 此时,你可以使用该处理器,提前先把结果处理成字符串,这样就避免进行后面toString 的时候将number 转成字符串的过程
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * User user = new User("feilong1", 24);
 * user.setMoney(toBigDecimal("99999999.00"));
 * 
 * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
 * propertyNameAndJsonValueProcessorMap.put("money", <b>new BigDecimalJsonValueProcessor(TWO_DECIMAL_POINTS)</b>);
 * 
 * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
 * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
 * javaToJsonConfig.setIncludes("name", "age", "money");
 * 
 * LOGGER.debug(JsonUtil.format(user, javaToJsonConfig));
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
 * {
 * "age": 24,
 * "name": "feilong1",
 * "money": "99999999.00"
 * }
 * </pre>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public class BigDecimalJsonValueProcessor extends AbstractJsonValueProcessor{

    /**
     * The number pattern(默认两位小数点).
     * 
     * @see com.feilong.core.NumberPattern
     */
    private String numberPattern = TWO_DECIMAL_POINTS;

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