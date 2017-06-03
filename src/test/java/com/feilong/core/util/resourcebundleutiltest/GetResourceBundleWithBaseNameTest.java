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

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;

import java.util.ResourceBundle;

import org.junit.Test;

import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static com.feilong.core.util.ResourceBundleUtil.toMap;

/**
 * The Class ResourceBundleUtilGetResourceBundleWithBaseNameTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetResourceBundleWithBaseNameTest{

    /** The base name. */
    private static final String BASE_NAME = "messages/feilong-core-test";

    /**
     * Test get resource bundle.
     */
    @Test
    public void testGetResourceBundle(){
        ResourceBundle resourceBundle = getResourceBundle(BASE_NAME);
        assertThat(toMap(resourceBundle), hasKey("config_test_array"));
    }

    /**
     * Test get resource bundle null base name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetResourceBundleNullBaseName(){
        getResourceBundle((String) null);
    }

    /**
     * Test get resource bundle empty base name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetResourceBundleEmptyBaseName(){
        getResourceBundle("");
    }

    /**
     * Test get resource bundle blank base name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetResourceBundleBlankBaseName(){
        getResourceBundle(" ");
    }

}
