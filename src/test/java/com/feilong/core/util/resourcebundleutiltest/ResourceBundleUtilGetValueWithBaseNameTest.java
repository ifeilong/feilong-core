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
package com.feilong.core.util.resourcebundleutiltest;

import java.util.MissingResourceException;

import org.junit.Test;

import static com.feilong.core.util.ResourceBundleUtil.getValue;

/**
 * The Class ResourceBundleUtilGetValueWithBaseNameTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ResourceBundleUtilGetValueWithBaseNameTest{

    /** The base name. */
    private static final String BASE_NAME = "messages/feilong-core-test";

    /**
     * Test get value null basename.
     */
    //**********************************************************************************
    @Test(expected = NullPointerException.class)
    public void testGetValueNullBasename(){
        getValue((String) null, "config_test_array");
    }

    /**
     * Test get value empty basename.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueEmptyBasename(){
        getValue("", "config_test_array");
    }

    /**
     * Test get value blank basename.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueBlankBasename(){
        getValue(" ", "config_test_array");
    }

    /**
     * Test get value null key.
     */
    //**********************************************************************************
    @Test(expected = NullPointerException.class)
    public void testGetValueNullKey(){
        getValue(BASE_NAME, null);
    }

    /**
     * Test get value empty key.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueEmptyKey(){
        getValue(BASE_NAME, "");
    }

    /**
     * Test get value blank key.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetValueBlankKey(){
        getValue(BASE_NAME, " ");
    }

    //**********************************************************************************

    /**
     * Test get value base name not exits.
     */
    @Test(expected = MissingResourceException.class)
    public void testGetValueBaseNameNotExits(){
        getValue("file_wo_bu_cun_zai", "wo_bu_cun_zai");
    }

}
