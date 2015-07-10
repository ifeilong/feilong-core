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
package com.feilong.core.tools.jsonlib.propertySetStrategy;

import net.sf.json.util.PropertySetStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PropertyStrategyWrapper.<br>
 * Ignore missing properties with Json-Lib <br>
 * 避免出现 Unknown property 'orderIdAndCodeMap' on class 'class com.baozun.trade.web.controller.payment.result.command.PaymentResultEntity' 异常
 * 
 * @see <a
 *      href="http://javaskeleton.blogspot.com/2011/05/ignore-missing-properties-with-json-lib.html">ignore-missing-properties-with-json-lib</a>
 * @see <a href="http://envy2002.iteye.com/blog/1682738">envy2002.iteye.com</a>
 * @see net.sf.json.util.PropertySetStrategy
 * @since 1.0.5
 */
public class PropertyStrategyWrapper extends PropertySetStrategy{

    /** The Constant LOGGER. */
    private static final Logger       LOGGER = LoggerFactory.getLogger(PropertyStrategyWrapper.class);

    /** The original. */
    private final PropertySetStrategy propertySetStrategy;

    /**
     * Instantiates a new property strategy wrapper.
     * 
     * @param propertySetStrategy
     *            the property set strategy
     */
    public PropertyStrategyWrapper(PropertySetStrategy propertySetStrategy){
        this.propertySetStrategy = propertySetStrategy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.util.PropertySetStrategy#setProperty(java.lang.Object, java.lang.String, java.lang.Object)
     */
    @Override
    public void setProperty(Object bean,String key,Object value){
        try{
            propertySetStrategy.setProperty(bean, key, value);
        }catch (Exception e){
            LOGGER.warn(e.getMessage(), e);
        }
    }
}