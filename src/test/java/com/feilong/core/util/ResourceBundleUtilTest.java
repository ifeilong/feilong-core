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

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.StringConverter;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.BeanUtil;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.ResourceBundleUtil.getValue;
import static com.feilong.core.util.ResourceBundleUtil.readToMap;

/**
 * The Class ResourceBundleUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ResourceBundleUtilTest{

    /** The Constant LOGGER. */
    private static final Logger  LOGGER         = LoggerFactory.getLogger(ResourceBundleUtilTest.class);

    /** The base name. */
    private static final String  BASE_NAME      = "messages/feilong-core-test";

    /** The resource bundle. */
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME);

    //************************************************************************************************

    /**
     * TestResourceBundleUtilTest.
     */
    @Test
    public void testResourceBundleUtilTest(){
        Control control = Control.getControl(Control.FORMAT_PROPERTIES);
        //control.needsReload(baseName, locale, format, loader, bundle, loadTime)     ;
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, control);
        LOGGER.debug(JsonUtil.format(toMap(bundle)));
    }

    /**
     * Test get value.
     */
    @Test
    public void testGetValue(){
        assertEquals("5,8,7,6", getValue(BASE_NAME, "config_test_array"));
        assertEquals("5,8,7,6", getValue("messages.feilong-core-test", "config_test_array"));
    }

    /**
     * Test with space value.
     */
    @Test
    public void testWithSpaceValue(){
        assertEquals("a ", getValue(BASE_NAME, "with_space_value"));
    }

    /**
     * Test base name not exits.
     */
    @Test(expected = MissingResourceException.class)
    public void testBaseNameNotExits(){
        assertEquals("", getValue("file_wo_bu_cun_zai", "wo_bu_cun_zai"));
    }

    /**
     * Test null.
     */
    @Test
    public void testNull(){
        assertEquals("", getValue(BASE_NAME, "wo_bu_cun_zai"));
    }

    /**
     * Test empty.
     */
    @Test
    public void testEmpty(){
        assertEquals("", getValue("messages.empty", "wo_bu_cun_zai"));
    }

    /**
     * Gets the value with arguments.
     */
    @Test
    @Ignore
    public void testGetValueWithArguments(){
        assertEquals("今天 2", getValue(resourceBundle, "test", "2", "22"));
    }

    /**
     * Test get value with arguments1.
     */
    @Test
    public void testGetValueWithArguments1(){
        assertEquals("my name is feilong,age is 18", getValue(resourceBundle, "test.arguments", "feilong", "18"));
    }

    /**
     * Read all properties to map.
     */
    @Test
    public void testReadToMap(){
        LOGGER.debug(JsonUtil.format(readToMap(BASE_NAME, Locale.CHINA)));
    }

    /**
     * Test get value1.
     */
    @Test
    public void testGetValue1(){
        LOGGER.debug(getValue(BASE_NAME, "config_date_hour", Locale.ENGLISH));
    }

    //**********************************************************************************************
    /**
     * Test read properties to alias bean.
     */
    @Test
    public void testReadToAliasBean(){
        DangaMemCachedConfig dangaMemCachedConfig = ResourceBundleUtil.readToAliasBean("messages.memcached", DangaMemCachedConfig.class);
        LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
    }

    /**
     * Test read properties to alias bean1.
     */
    @Test
    public void testReadToAliasBean1(){
        ArrayConverter arrayConverter = new ArrayConverter(String[].class, new StringConverter(), 2);
        char[] allowedChars = { ':' };
        arrayConverter.setAllowedChars(allowedChars);

        BeanUtil.register(arrayConverter, String[].class);

        DangaMemCachedConfig dangaMemCachedConfig = ResourceBundleUtil.readToAliasBean("messages.memcached", DangaMemCachedConfig.class);
        LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
    }

    //**********************************************************************************************

    /**
     * Test read properties to alias bean nullpoint.
     */
    @Test(expected = NullPointerException.class)
    public void testReadToAliasBeanNullpoint(){
        ResourceBundleUtil.readToAliasBean(null, DangaMemCachedConfig.class);
    }

    /**
     * Test read properties to alias bean illegal argument exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testReadToAliasBeanIllegalArgumentException(){
        ResourceBundleUtil.readToAliasBean("   ", DangaMemCachedConfig.class);
    }

    /**
     * Test read properties to alias bean nullpoint1.
     */
    @Test(expected = NullPointerException.class)
    public void testReadToAliasBeanNullpoint1(){
        ResourceBundleUtil.readToAliasBean("messages.memcached", null);
    }

    /**
     * Test read properties to alias bean nullpoint4.
     */
    @Test(expected = MissingResourceException.class)
    public void testReadToAliasBeanNullpoint4(){
        ResourceBundleUtil.readToAliasBean("messages.memcached111", DangaMemCachedConfig.class);
    }

}
