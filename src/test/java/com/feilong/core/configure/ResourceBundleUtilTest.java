/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.configure;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class ResourceBundleUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-6-25 15:21:15
 */
public class ResourceBundleUtilTest{

    /** The Constant LOGGER. */
    private static final Logger  LOGGER         = LoggerFactory.getLogger(ResourceBundleUtilTest.class);

    /** The base name. */
    private String               baseName       = "messages/feilong-core-test";

    /** The resource bundle. */
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);

    /**
     * Gets the value.
     * 
     */
    @Test
    // "/WEB-INF/classes/feilong.user.properties"
    public final void testGetValue(){
        String aString = ResourceBundleUtil.getValue(baseName, "config_test_array");
        LOGGER.debug(aString);
    }

    /**
     * Gets the value with arguments.
     * 
     */
    @Test
    public final void testGetValueWithArguments(){
        String aString = ResourceBundleUtil.getValueWithArguments(resourceBundle, "test", "2", "22");
        LOGGER.debug(aString);
    }

    /**
     * Read properties as array.
     */
    @Test
    public final void readPropertiesAsArray(){
        String[] strings = ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",", String.class);
        LOGGER.info(JsonUtil.format(strings));
    }

    /**
     * Read prefix as map.
     */
    @Test
    public final void readPrefixAsMap(){

        Locale locale = Locale.CHINA;
        Map<String, String> map = ResourceBundleUtil.readPrefixAsMap(baseName, "FileType", "\\.", locale);
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Read all properties to map.
     */
    @Test
    public final void readAllPropertiesToMap(){
        Locale locale = Locale.CHINA;
        baseName = "messages/feilong-core-message";
        Map<String, String> map = ResourceBundleUtil.readAllPropertiesToMap(baseName, locale);
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Read all properties to map.
     * 
     */
    @Test
    public final void testGetValue1(){
        Locale locale = Locale.ENGLISH;
        baseName = "messages/feilong-core-message";
        LOGGER.info(ResourceBundleUtil.getValue(baseName, "config_date_hour", locale));
    }
}
