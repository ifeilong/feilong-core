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
import net.sf.json.processors.PropertyNameProcessor;

/**
 * java格式化成json的一些配置.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see net.sf.json.JsonConfig
 * @since 1.2.2
 * @since 1.9.4 <a href="https://github.com/venusdrogon/feilong-core/issues/511">rename</a>
 */
public class JavaToJsonConfig{

    /** 排除属性名称的数组. */
    private String[]                             excludes;

    /** 包含属性名称的数组. */
    private String[]                             includes;

    /**
     * 指定属性名称使用的值修改处理器.
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
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, javaToJsonConfig));
     * </pre>
     * 
     * 将会输出:
     * 
     * <pre class="code">
     * {
     * "password": "******",
     * "age": 24,
     * "name": "feilong1",
     * "money": "99999999.00"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @see net.sf.json.processors.JsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.BigDecimalJsonValueProcessor
     * @see com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor
     */
    private Map<String, JsonValueProcessor>      propertyNameAndJsonValueProcessorMap;

    /**
     * 转成json的时候,对属性名字做特殊处理的控制器对映关系.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * 我们这边的代码
     * 
     * <pre class="code">
     * public class CrmAddpointCommand implements Serializable{
     * 
     *     <span style="color:green">// 用户编码</span>
     *     private String openId;
     * 
     *     <span style="color:green">// 渠道：Tmall - 天猫 JD - 京东</span>
     *     private String consumptionChannel;
     * 
     *     <span style="color:green">// 淘宝/京东买家账号</span>
     *     private String buyerId;
     * 
     *     <span style="color:green">// 电商订单编号 </span>
     *     private String orderCode;
     * 
     *     <span style="color:green">// setter getter</span>
     * }
     * 
     * </pre>
     * 
     * 符合标准的java代码规范,如果直接使用 {@link com.feilong.tools.jsonlib.JsonUtil#format(Object)}
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand));
     * }
     * 
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "orderCode": "fl123456",
     * "buyerId": "123456",
     * "consumptionChannel": "feilongstore",
     * "openId": "feilong888888ky"
     * }
     * 
     * </pre>
     * 
     * 输出的属性大小写和 crmAddpointCommand 对象里面字段的大小写相同,但是对方接口要求首字符要大写:
     * 
     * <p>
     * <img src="https://cloud.githubusercontent.com/assets/3479472/19713507/434572a8-9b79-11e6-987a-07e572df5bf9.png"/>
     * </p>
     * 
     * 此时,你可以使用
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     //****************************************************************************************
     * 
     *     JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * 
     *     Map{@code <Class<?>, PropertyNameProcessor>} targetClassAndPropertyNameProcessorMap = newHashMap(1);
     *     targetClassAndPropertyNameProcessorMap.put(CrmAddpointCommand.class, CapitalizePropertyNameProcessor.INSTANCE);
     * 
     *     <span style="color:red">javaToJsonConfig.setJsonTargetClassAndPropertyNameProcessorMap(targetClassAndPropertyNameProcessorMap);</span>
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand, javaToJsonConfig));
     * }
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "OrderCode": "fl123456",
     * "BuyerId": "123456",
     * "ConsumptionChannel": "feilongstore",
     * "OpenId": "feilong888888ky"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/505">json format 需要支持修改key的名字</a>
     * @since 1.9.3
     */
    private Map<Class<?>, PropertyNameProcessor> jsonTargetClassAndPropertyNameProcessorMap;

    //***************************************************************************

    /**
     * The Constructor.
     */
    public JavaToJsonConfig(){
    }

    /**
     * The Constructor.
     *
     * @param propertyNameAndJsonValueProcessorMap
     *            the property name and json value processor map
     */
    public JavaToJsonConfig(Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap){
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
    public JavaToJsonConfig(String[] excludes, String[] includes){
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
     * 指定属性名称使用的值修改处理器.
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
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, javaToJsonConfig));
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
     * 指定属性名称使用的值修改处理器.
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
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.info(JsonUtil.format(user, javaToJsonConfig));
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

    /**
     * 转成json的时候,对属性名字做特殊处理的控制器对映关系.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * 我们这边的代码
     * 
     * <pre class="code">
     * public class CrmAddpointCommand implements Serializable{
     * 
     *     <span style="color:green">// 用户编码</span>
     *     private String openId;
     * 
     *     <span style="color:green">// 渠道：Tmall - 天猫 JD - 京东</span>
     *     private String consumptionChannel;
     * 
     *     <span style="color:green">// 淘宝/京东买家账号</span>
     *     private String buyerId;
     * 
     *     <span style="color:green">// 电商订单编号 </span>
     *     private String orderCode;
     * 
     *     <span style="color:green">// setter getter</span>
     * }
     * 
     * </pre>
     * 
     * 符合标准的java代码规范,如果直接使用 {@link com.feilong.tools.jsonlib.JsonUtil#format(Object)}
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand));
     * }
     * 
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "orderCode": "fl123456",
     * "buyerId": "123456",
     * "consumptionChannel": "feilongstore",
     * "openId": "feilong888888ky"
     * }
     * 
     * </pre>
     * 
     * 输出的属性大小写和 crmAddpointCommand 对象里面字段的大小写相同,但是对方接口要求首字符要大写:
     * 
     * <p>
     * <img src="https://cloud.githubusercontent.com/assets/3479472/19713507/434572a8-9b79-11e6-987a-07e572df5bf9.png"/>
     * </p>
     * 
     * 此时,你可以使用
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     //****************************************************************************************
     * 
     *     JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * 
     *     Map{@code <Class<?>, PropertyNameProcessor>} targetClassAndPropertyNameProcessorMap = newHashMap(1);
     *     targetClassAndPropertyNameProcessorMap.put(CrmAddpointCommand.class, CapitalizePropertyNameProcessor.INSTANCE);
     * 
     *     <span style="color:red">javaToJsonConfig.setJsonTargetClassAndPropertyNameProcessorMap(targetClassAndPropertyNameProcessorMap);</span>
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand, javaToJsonConfig));
     * }
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "OrderCode": "fl123456",
     * "BuyerId": "123456",
     * "ConsumptionChannel": "feilongstore",
     * "OpenId": "feilong888888ky"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @return the 转成json的时候,对属性名字做特殊处理的控制器对映关系
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/505">json format 需要支持修改key的名字</a>
     * @since 1.9.3
     */
    public Map<Class<?>, PropertyNameProcessor> getJsonTargetClassAndPropertyNameProcessorMap(){
        return jsonTargetClassAndPropertyNameProcessorMap;
    }

    /**
     * 转成json的时候,对属性名字做特殊处理的控制器对映关系.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * 我们这边的代码
     * 
     * <pre class="code">
     * public class CrmAddpointCommand implements Serializable{
     * 
     *     <span style="color:green">// 用户编码</span>
     *     private String openId;
     * 
     *     <span style="color:green">// 渠道：Tmall - 天猫 JD - 京东</span>
     *     private String consumptionChannel;
     * 
     *     <span style="color:green">// 淘宝/京东买家账号</span>
     *     private String buyerId;
     * 
     *     <span style="color:green">// 电商订单编号 </span>
     *     private String orderCode;
     * 
     *     <span style="color:green">// setter getter</span>
     * }
     * 
     * </pre>
     * 
     * 符合标准的java代码规范,如果直接使用 {@link com.feilong.tools.jsonlib.JsonUtil#format(Object)}
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand));
     * }
     * 
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "orderCode": "fl123456",
     * "buyerId": "123456",
     * "consumptionChannel": "feilongstore",
     * "openId": "feilong888888ky"
     * }
     * 
     * </pre>
     * 
     * 输出的属性大小写和 crmAddpointCommand 对象里面字段的大小写相同,但是对方接口要求首字符要大写:
     * 
     * <p>
     * <img src="https://cloud.githubusercontent.com/assets/3479472/19713507/434572a8-9b79-11e6-987a-07e572df5bf9.png"/>
     * </p>
     * 
     * 此时,你可以使用
     * 
     * <pre class="code">
     * 
     * public void testJsonTest(){
     *     CrmAddpointCommand crmAddpointCommand = new CrmAddpointCommand();
     * 
     *     crmAddpointCommand.setBuyerId("123456");
     *     crmAddpointCommand.setConsumptionChannel("feilongstore");
     *     crmAddpointCommand.setOpenId("feilong888888ky");
     *     crmAddpointCommand.setOrderCode("fl123456");
     * 
     *     //****************************************************************************************
     * 
     *     JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * 
     *     Map{@code <Class<?>, PropertyNameProcessor>} targetClassAndPropertyNameProcessorMap = newHashMap(1);
     *     targetClassAndPropertyNameProcessorMap.put(CrmAddpointCommand.class, CapitalizePropertyNameProcessor.INSTANCE);
     * 
     *     <span style="color:red">javaToJsonConfig.setJsonTargetClassAndPropertyNameProcessorMap(targetClassAndPropertyNameProcessorMap);</span>
     * 
     *     LOGGER.debug(JsonUtil.format(crmAddpointCommand, javaToJsonConfig));
     * }
     * </pre>
     * 
     * <b>输出结果:</b>
     * 
     * <pre class="code">
     * {
     * "OrderCode": "fl123456",
     * "BuyerId": "123456",
     * "ConsumptionChannel": "feilongstore",
     * "OpenId": "feilong888888ky"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param jsonTargetClassAndPropertyNameProcessorMap
     *            the new 转成json的时候,对属性名字做特殊处理的控制器对映关系
     * @see <a href="https://github.com/venusdrogon/feilong-core/issues/505">json format 需要支持修改key的名字</a>
     * @since 1.9.3
     */
    public void setJsonTargetClassAndPropertyNameProcessorMap(
                    Map<Class<?>, PropertyNameProcessor> jsonTargetClassAndPropertyNameProcessorMap){
        this.jsonTargetClassAndPropertyNameProcessorMap = jsonTargetClassAndPropertyNameProcessorMap;
    }

}
