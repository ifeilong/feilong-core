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
package com.feilong.core.tools.jsonlib.processor;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 过滤敏感信息,最直接的就是像密码这样的内容,不可以输出在控制台,需要转换成***字眼.
 *
 * @author feilong
 * @version 1.2.2 2015年7月10日 下午10:54:55
 * @since 1.2.2
 */
public class SensitiveWordsJsonValueProcessor implements JsonValueProcessor{

    /** The default sensitive words. */
    private static String DEFAULT_SENSITIVE_WORDS = "******";

    /**
     * The Constructor.
     */
    public SensitiveWordsJsonValueProcessor(){
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object value,JsonConfig jsonConfig){
        return processValue(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key,Object value,JsonConfig jsonConfig){
        return processValue(value);
    }

    /**
     * Process.
     *
     * @param value
     *            the value
     * @return the object
     */
    private static Object processValue(Object value){
        return null == value ? null : DEFAULT_SENSITIVE_WORDS;
    }
}