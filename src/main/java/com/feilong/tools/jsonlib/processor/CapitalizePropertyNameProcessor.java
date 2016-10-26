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

import org.apache.commons.lang3.StringUtils;

import net.sf.json.processors.PropertyNameProcessor;

/**
 * 将指定类型下面所有属性名字首字母变大写的处理器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see <a href="https://github.com/venusdrogon/feilong-core/issues/505">json format 需要支持修改key的名字</a>
 * @since 1.9.3
 */
public class CapitalizePropertyNameProcessor implements PropertyNameProcessor{

    /** Singleton predicate instance. */
    public static final PropertyNameProcessor INSTANCE = new CapitalizePropertyNameProcessor();

    /**
     * Instantiates a new capitalize property name processor.
     */
    private CapitalizePropertyNameProcessor(){
    }

    //**********************************************************************************************************

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.PropertyNameProcessor#processPropertyName(java.lang.Class, java.lang.String)
     */
    @Override
    public String processPropertyName(@SuppressWarnings("rawtypes") Class beanClass,String currentPropertyName){
        return StringUtils.capitalize(currentPropertyName);
    }
}
