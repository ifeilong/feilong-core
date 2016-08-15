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
package com.feilong.tools.jsonlib;

import java.util.Map;

import net.sf.json.processors.JsonValueProcessor;

/**
 * 格式化成json的一些配置.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see net.sf.json.JsonConfig
 * @since 1.2.2
 */
public class JsonFormatConfig{

    /** 排除属性名称的数组. */
    private String[]                        excludes;

    /** 包含属性名称的数组. */
    private String[]                        includes;

    /**
     * 指定属性名称使用的处理器.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User("feilong1", 24);
     * user.setPassword("123456");
     * user.setMoney(ConvertUtil.toBigDecimal("99999999.00"));
     * 
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <String, JsonValueProcessor>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
     * jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, jsonFormatConfig));
     * </pre>
     * 
     * 将会输出:
     * 
     * <pre class="code">
     * {
     *   "password": "******",
     *   "age": 24,
     *   "name": "feilong1",
     *   "money": "99999999.00"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @see net.sf.json.processors.JsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.BigDecimalJsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor
     */
    private Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap;

    //***************************************************************************

    /**
     * The Constructor.
     */
    public JsonFormatConfig(){
    }

    /**
     * The Constructor.
     *
     * @param propertyNameAndJsonValueProcessorMap
     *            the property name and json value processor map
     */
    public JsonFormatConfig(Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap){
        this.propertyNameAndJsonValueProcessorMap = propertyNameAndJsonValueProcessorMap;
    }

    /**
     * The Constructor.
     *
     * @param excludes
     *            the excludes
     * @param includes
     *            the includes
     */
    public JsonFormatConfig(String[] excludes, String[] includes){
        this.excludes = excludes;
        this.includes = includes;
    }

    //***************************************************************************

    /**
     * 获得 排除属性名称的数组.
     *
     * @return the 排除属性名称的数组
     */
    public String[] getExcludes(){
        return excludes;
    }

    /**
     * 设置 排除属性名称的数组.
     *
     * @param excludes
     *            the new 排除属性名称的数组
     * @since 1.8.5 change to Varargs
     */
    public void setExcludes(String...excludes){
        this.excludes = excludes;
    }

    /**
     * 获得 包含属性名称的数组.
     *
     * @return the 包含属性名称的数组
     */
    public String[] getIncludes(){
        return includes;
    }

    /**
     * 设置 包含属性名称的数组.
     *
     * @param includes
     *            the new 包含属性名称的数组
     * @since 1.8.5 change to Varargs
     */
    public void setIncludes(String...includes){
        this.includes = includes;
    }

    /**
     * 指定属性名称使用的处理器.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User("feilong1", 24);
     * user.setPassword("123456");
     * user.setMoney(ConvertUtil.toBigDecimal("99999999.00"));
     * 
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <String, JsonValueProcessor>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
     * jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, jsonFormatConfig));
     * </pre>
     * 
     * 将会输出:
     * 
     * <pre class="code">
     * {
     *   "password": "******",
     *   "age": 24,
     *   "name": "feilong1",
     *   "money": "99999999.00"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @return the propertyNameAndJsonValueProcessorMap
     * @see net.sf.json.processors.JsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.BigDecimalJsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor
     */
    public Map<String, JsonValueProcessor> getPropertyNameAndJsonValueProcessorMap(){
        return propertyNameAndJsonValueProcessorMap;
    }

    /**
     * 指定属性名称使用的处理器.
     *
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User("feilong1", 24);
     * user.setPassword("123456");
     * user.setMoney(ConvertUtil.toBigDecimal("99999999.00"));
     * 
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <String, JsonValueProcessor>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JsonFormatConfig jsonFormatConfig = new JsonFormatConfig();
     * jsonFormatConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, jsonFormatConfig));
     * </pre>
     * 
     * 将会输出:
     * 
     * <pre class="code">
     * {
     *   "password": "******",
     *   "age": 24,
     *   "name": "feilong1",
     *   "money": "99999999.00"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param propertyNameAndJsonValueProcessorMap
     *            the propertyNameAndJsonValueProcessorMap to set
     * @see net.sf.json.processors.JsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.BigDecimalJsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor
     */
    public void setPropertyNameAndJsonValueProcessorMap(Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap){
        this.propertyNameAndJsonValueProcessorMap = propertyNameAndJsonValueProcessorMap;
    }

}
