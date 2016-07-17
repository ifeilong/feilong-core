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
package com.feilong.core.bean;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class BeanUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class BeanUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtilTemp.class);

    /**
     * Describe.
     */
    @Test
    public void describe(){
        User user = new User();
        user.setId(5L);
        user.setDate(new Date());

        LOGGER.debug(JsonUtil.format(describe(user)));
    }

    /**
     * Describe1.
     */
    @Test
    public void describe1(){
        LOGGER.debug(JsonUtil.format(describe(null)));
    }
    // [start] describe 把Bean的属性值放入到一个Map里面

    /**
     * 返回一个<code>bean</code>中所有的可读属性(read method),并将属性名/属性值放入一个Map中.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * User user = new User();
     * user.setId(5L);
     * user.setDate(new Date());
     * 
     * LOGGER.info(JsonUtil.format(BeanUtil.describe(user)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "id": "5",
     * "date": "Mon Apr 11 00:37:56 CST 2016"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 另外还有一个名为class的属性,属性值是Object的类名,事实上class是java.lang.Object的一个属性.
     * </p>
     * 
     * <p>
     * <span style="color:red">缺陷:<br>
     * 自己手工注册的ConvertUtils.register(dateTimeConverter, java.util.Date.class)不会生效</span><br>
     * 
     * 在赋值的时候,虽然调用了 {@link BeanUtilsBean#getNestedProperty(Object, String)}, 虽然也调用了 ConvertUtilsBean来转换 <br>
     * 但是 {@link ConvertUtilsBean#ConvertUtilsBean()} 默认的构造函数 是使用标准的转换
     * </p>
     *
     * @param bean
     *            Bean whose properties are to be extracted
     * @return 如果 <code>bean</code> 是null,返回 empty HashMap,see {@link BeanUtilsBean#describe(Object)}
     * @throws BeanUtilException
     *             有任何异常,转成{@link BeanUtilException}返回
     * @see org.apache.commons.beanutils.BeanUtils#describe(Object)
     * @see org.apache.commons.beanutils.PropertyUtils#describe(Object)
     * @see PropertyUtil#describe(Object, String...)
     * @see PropertyDescriptor
     * @see BeanUtil#populate(Object, Map)
     */
    public static Map<String, String> describe(Object bean){
        try{
            //Return the entire set of properties for which the specified bean provides a read method.
            return BeanUtils.describe(bean);
        }catch (Exception e){
            throw new BeanUtilException(e);
        }
    }

    // [end]

    /**
     * 创建 dyna bean.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, Class<?>>} typeMap = new HashMap<>();
     * typeMap.put("address", java.util.Map.class);
     * typeMap.put("firstName", String.class);
     * typeMap.put("lastName", String.class);
     * 
     * Map{@code <String, Object>} valueMap = new HashMap<>();
     * valueMap.put("address", new HashMap());
     * valueMap.put("firstName", "Fred");
     * valueMap.put("lastName", "Flintstone");
     * 
     * DynaBean dynaBean = BeanUtil.newDynaBean(typeMap, valueMap);
     * LOGGER.debug(JsonUtil.format(dynaBean));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     *         "lastName": "Flintstone",
     *         "address": {},
     *         "firstName": "Fred"
     *     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param typeMap
     *            属性名称 和类型的 map
     * @param valueMap
     *            属性名称 和 值 的map
     * @return the dyna bean
     * @throws NullPointerException
     *             如果 <code>typeMap </code> 或者 <code>valueMap</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>typeMap.size() != valueMap.size()</code>
     * @since 1.8.1 change name
     */
    public static DynaBean newDynaBean(Map<String, Class<?>> typeMap,Map<String, Object> valueMap){
        Validate.notNull(typeMap, "typeMap can't be null!");
        Validate.notNull(valueMap, "valueMap can't be null!");
        Validate.isTrue(typeMap.size() == valueMap.size(), "typeMap size:[%s] != valueMap size:[%s]", typeMap.size(), valueMap.size());

        //*********************************************************************************
        try{
            DynaClass dynaClass = new BasicDynaClass(null, null, toDynaPropertyArray(typeMap));

            DynaBean dynaBean = dynaClass.newInstance();
            for (Map.Entry<String, Object> entry : valueMap.entrySet()){
                dynaBean.set(entry.getKey(), entry.getValue());
            }
            return dynaBean;
        }catch (IllegalAccessException | InstantiationException e){
            LOGGER.error("", e);
            throw new BeanUtilException(e);
        }
    }

    /**
     * To dyna property array.
     *
     * @param typeMap
     *            the type map
     * @return the dyna property[]
     * @since 1.8.0
     */
    private static DynaProperty[] toDynaPropertyArray(Map<String, Class<?>> typeMap){
        DynaProperty[] dynaPropertys = new DynaProperty[typeMap.size()];
        int i = 0;
        for (Map.Entry<String, Class<?>> entry : typeMap.entrySet()){
            dynaPropertys[i] = new DynaProperty(entry.getKey(), entry.getValue());
            i++;
        }
        return dynaPropertys;
    }

    @Test(expected = NullPointerException.class)
    public void testBasicDynaClass1(){
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("address", new HashMap());
        valueMap.put("firstName", "Fred");
        valueMap.put("lastName", "Flintstone");

        newDynaBean(null, valueMap);
    }

    @Test(expected = NullPointerException.class)
    public void testBasicDynaClass2(){
        Map<String, Class<?>> typeMap = new HashMap<>();
        typeMap.put("address", java.util.Map.class);
        typeMap.put("firstName", String.class);
        typeMap.put("lastName", String.class);

        newDynaBean(typeMap, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBasicDynaClass4(){
        Map<String, Class<?>> typeMap = new HashMap<>();
        typeMap.put("address", java.util.Map.class);
        typeMap.put("firstName", String.class);
        typeMap.put("lastName", String.class);

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("address", new HashMap());
        valueMap.put("firstName", "Fred");

        newDynaBean(typeMap, valueMap);
    }

    @Test
    public void testBasicDynaClass(){
        Map<String, Class<?>> typeMap = new HashMap<>();
        typeMap.put("address", java.util.Map.class);
        typeMap.put("firstName", String.class);
        typeMap.put("lastName", String.class);

        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("address", new HashMap());
        valueMap.put("firstName", "Fred");
        valueMap.put("lastName", "Flintstone");

        DynaBean dynaBean = newDynaBean(typeMap, valueMap);
        LOGGER.debug(JsonUtil.format(dynaBean));
    }

}
