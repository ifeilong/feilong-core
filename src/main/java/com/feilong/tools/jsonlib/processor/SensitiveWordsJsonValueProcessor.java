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

import com.feilong.tools.jsonlib.JsonUtil;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 过滤敏感信息,最直接的就是像密码这样的内容,不可以直接明文输出在控制台或者日志文件,需要转换成***字眼.
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * 
 * User user = new User("feilong1", 24);
 * user.setPassword("123456");
 * 
 * JsonValueProcessor jsonValueProcessor = new SensitiveWordsJsonValueProcessor();
 * 
 * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
 * propertyNameAndJsonValueProcessorMap.put("password", jsonValueProcessor);
 * propertyNameAndJsonValueProcessorMap.put("age", jsonValueProcessor);
 * 
 * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
 * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
 * javaToJsonConfig.setIncludes("name", "age", "password");
 * 
 * LOGGER.debug(JsonUtil.format(user, javaToJsonConfig));
 * 
 * </pre>
 * 
 * <b>返回:</b>
 * 
 * <pre class="code">
 * {
 * "password": "******",
 * "age": "******",
 * "name": "feilong1"
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * <h3>说明:</h3>
 * 
 * <blockquote>
 * 目前 {@link JsonUtil} 内置对<b>"password"</b>, <b>"key"</b> 两个字眼的属性名字,默认是显示成*******,参见
 * {@link JsonUtil#SENSITIVE_WORDS_PROPERTY_NAMES} 以及 {@link JsonUtil#registerDefaultJsonValueProcessor(JsonConfig)}
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public class SensitiveWordsJsonValueProcessor extends AbstractJsonValueProcessor{

    /** Singleton instance. */
    public static final JsonValueProcessor INSTANCE                = new SensitiveWordsJsonValueProcessor();

    /** The default sensitive words. */
    private static String                  DEFAULT_SENSITIVE_WORDS = "******";

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.tools.jsonlib.processor.AbstractJsonValueProcessor#processValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    protected Object processValue(Object value,JsonConfig jsonConfig){
        return null == value ? null : DEFAULT_SENSITIVE_WORDS;
    }
}