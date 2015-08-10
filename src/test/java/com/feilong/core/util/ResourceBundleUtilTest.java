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
package com.feilong.core.util;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;

/**
 * The Class ResourceBundleUtilTest.
 * 
 * @author feilong
 * @version 1.0.7 2014-6-25 15:21:15
 */
public class ResourceBundleUtilTest{

    /** The Constant LOGGER. */
    private static final Logger  LOGGER         = LoggerFactory.getLogger(ResourceBundleUtilTest.class);

    /** The base name. */
    private final String         baseName       = "messages/feilong-core-test";

    /** The resource bundle. */
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName);

    /**
     * Gets the value.
     * 
     */
    @Test
    // "/WEB-INF/classes/feilong.user.properties"
    public final void testGetValue(){
        LOGGER.debug(ResourceBundleUtil.getValue(baseName, "config_test_array"));
    }

    /**
     * Gets the value with arguments.
     * 
     */
    @Test
    public final void testGetValueWithArguments(){
        LOGGER.debug(ResourceBundleUtil.getValueWithArguments(resourceBundle, "test", "2", "22"));
    }

    /**
     * Read properties as array.
     */
    @Test
    public final void readPropertiesAsArray(){
        LOGGER.info(JsonUtil.format(ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",", String.class)));
        LOGGER.info(JsonUtil.format(ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",", Integer.class)));
    }

    /**
     * Read prefix as map.
     */
    @Test
    public final void readPrefixAsMap(){
        Map<String, String> map = ResourceBundleUtil.readPrefixAsMap(baseName, "FileType", "\\.", Locale.CHINA);
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Read all properties to map.
     */
    @Test
    public final void readAllPropertiesToMap(){
        Map<String, String> map = ResourceBundleUtil.readAllPropertiesToMap("messages/feilong-core-message", Locale.CHINA);
        LOGGER.info(JsonUtil.format(map));
    }

    /**
     * Read all properties to map.
     * 
     */
    @Test
    public final void testGetValue1(){
        LOGGER.info(ResourceBundleUtil.getValue("messages/feilong-core-message", "config_date_hour", Locale.ENGLISH));
    }
}
