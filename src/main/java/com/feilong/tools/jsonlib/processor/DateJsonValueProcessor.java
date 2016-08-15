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

import java.util.Date;

import com.feilong.core.date.DateUtil;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

import net.sf.json.JsonConfig;

/**
 * 时间转换 日期值处理器实现.
 * 
 * {@link Date}日期json value处理器.
 * 
 * <p>
 * 如果不使用这个处理器,对于 Date格式输出成json,在date to json的时候,会解析Date 的每个字段属性,比如month,year,day等:
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * User user = new User("feilong1", 24);
 * user.setDate(toDate("2016-08-15 13:30:00", COMMON_DATE_AND_TIME));
 * 
 * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
 * jsonFormatConfig.setIncludes("date");
 * 
 * LOGGER.debug(JsonUtil.format(user, jsonFormatConfig));
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
{"date": {"date": 15}}
 * </pre>
 * 
 * <p>
 * 可以看出,这个日期输出并不是我们期望看到的结果
 * </p>
 * 
 * </blockquote>
 * 
 * <p>
 * 此时,你可以使用该处理器,做自定义解析
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * User user = new User("feilong1", 24);
 * user.setDate(toDate("2016-08-15 13:30:00", COMMON_DATE_AND_TIME));
 * 
 * Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = new HashMap<>();
 * propertyNameAndJsonValueProcessorMap.put("date", new DateJsonValueProcessor(COMMON_DATE_AND_TIME));
 * 
 * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
 * jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
 * jsonFormatConfig.setIncludes("date");
 * 
 * LOGGER.debug(JsonUtil.format(user, jsonFormatConfig));
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
{"date": "2016-08-15 13:30:00"}
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * 为了简化操作,{@link JsonUtil#getDefaultJsonConfig()} 内置了 <code>new DateJsonValueProcessor(COMMON_DATE_AND_TIME)</code>
 * ,如果你想输出成其他的日期格式,也可以使用这个类来提前渲染
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * User user = new User("feilong1", 24);
 * user.setDate(toDate("2016-08-15", COMMON_DATE));
 * 
 * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
 * propertyNameAndJsonValueProcessorMap.put("date", new DateJsonValueProcessor(COMMON_DATE));
 * 
 * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
 * jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
 * jsonFormatConfig.setIncludes("date");
 * 
 * LOGGER.debug(JsonUtil.format(user, jsonFormatConfig));
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
{"date": "2016-08-15"}
 * </pre>
 * 
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.5
 */
public class DateJsonValueProcessor extends AbstractJsonValueProcessor{

    /** The date pattern. */
    private String datePattern = COMMON_DATE_AND_TIME;

    /**
     * The Constructor.
     *
     * @param datePattern
     *            你可以使用 {@link com.feilong.core.DatePattern}
     */
    public DateJsonValueProcessor(String datePattern){
        this.datePattern = datePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.tools.jsonlib.processor.AbstractJsonValueProcessor#processValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    protected Object processValue(Object value,JsonConfig jsonConfig){
        return null == value ? null : (value instanceof Date ? DateUtil.toString((Date) value, datePattern) : value.toString());
    }
}