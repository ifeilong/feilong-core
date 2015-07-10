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
package com.feilong.core.tools.jsonlib;

import java.util.Map;

import net.sf.json.processors.JsonValueProcessor;

/**
 * The Class JsonFormatConfig.
 *
 * @author feilong
 * @version 1.2.2 2015年7月10日 下午10:24:17
 * @see net.sf.json.JsonConfig
 * @since 1.2.2
 */
public class JsonFormatConfig{

    /** The excludes. */
    private String[]                        excludes;

    /** The includes. */
    private String[]                        includes;

    /** The property name and json value processor map. */
    private Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap;

    /**
     * The Constructor.
     */
    public JsonFormatConfig(){
        super();
    }

    /**
     * 获得 the excludes.
     *
     * @return the excludes
     */
    public String[] getExcludes(){
        return excludes;
    }

    /**
     * 设置 the excludes.
     *
     * @param excludes
     *            the excludes to set
     */
    public void setExcludes(String[] excludes){
        this.excludes = excludes;
    }

    /**
     * 获得 the property name and json value processor map.
     *
     * @return the propertyNameAndJsonValueProcessorMap
     */
    public Map<String, JsonValueProcessor> getPropertyNameAndJsonValueProcessorMap(){
        return propertyNameAndJsonValueProcessorMap;
    }

    /**
     * 设置 the property name and json value processor map.
     *
     * @param propertyNameAndJsonValueProcessorMap
     *            the propertyNameAndJsonValueProcessorMap to set
     */
    public void setPropertyNameAndJsonValueProcessorMap(Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap){
        this.propertyNameAndJsonValueProcessorMap = propertyNameAndJsonValueProcessorMap;
    }

    /**
     * 获得 includes.
     *
     * @return the includes
     */
    public String[] getIncludes(){
        return includes;
    }

    /**
     * 设置 includes.
     *
     * @param includes
     *            the includes to set
     */
    public void setIncludes(String[] includes){
        this.includes = includes;
    }

}
