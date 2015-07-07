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

import java.math.BigDecimal;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * The Class DoubleJsonValueProcessor.
 * 
 * @author feilong
 * @version 1.0.7 2014-5-28 13:30:06
 */
public class DoubleJsonValueProcessor implements JsonValueProcessor{

    /**
     * Instantiates a new double json value processor.
     */
    public DoubleJsonValueProcessor(){
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object arg0,JsonConfig arg1){
        return process(arg0);
    }

    /**
     * Process.
     * 
     * @param arg0
     *            the arg0
     * @return the object
     */
    private Object process(Object arg0){
        if (arg0 == null){
            return "";
        }else{
            //对于 double 转成 BigDecimal，推荐使用 BigDecimal.valueOf，不建议使用new BigDecimal(double)，参见 JDK API
            //new BigDecimal(0.1) ====>   0.1000000000000000055511151231257827021181583404541015625
            //BigDecimal.valueOf(0.1) ====>  0.1
            BigDecimal a = BigDecimal.valueOf((Double) arg0);
            return String.valueOf(a);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String arg0,Object arg1,JsonConfig arg2){
        return process(arg1);
    }
}