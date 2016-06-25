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

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * The Class AbstractJsonValueProcessor.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.7.3
 */
abstract class AbstractJsonValueProcessor implements JsonValueProcessor{

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object value,JsonConfig jsonConfig){
        return processValue(value, jsonConfig);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key,Object value,JsonConfig jsonConfig){
        return processValue(value, jsonConfig);
    }

    /**
     * Process value.
     *
     * @param value
     *            the value
     * @param jsonConfig
     *            the json config
     * @return the object
     */
    protected abstract Object processValue(Object value,JsonConfig jsonConfig);
}
