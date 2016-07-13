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
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
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
     * 返回:
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
     * @see #populate(Object, Map)
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

}
