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

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Test;

import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static com.feilong.core.util.ResourceBundleUtil.toMap;

/**
 * The Class ResourceBundleUtilGetResourceBundleWithBaseNameAndLocaleTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetResourceBundleWithBaseNameAndLocaleTest{

    /**
     * Test get resource bundle.
     */
    @Test
    public void testGetResourceBundle(){
        ResourceBundle resourceBundle = getResourceBundle("messages/feilong-archetypes", Locale.ENGLISH);
        Map<String, String> map = toMap(resourceBundle);
        assertThat(map, hasEntry("feilong-archetypes.welcome", "welcome(english)"));
    }

    /**
     * Test get resource bundle null locale.
     */
    @Test
    public void testGetResourceBundleNullLocale(){
        ResourceBundle resourceBundle = getResourceBundle("messages/feilong-archetypes", null);
        Map<String, String> map = toMap(resourceBundle);
        assertThat(map, hasKey("feilong-archetypes.welcome"));
    }

    /**
     * Test get resource bundle null base name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetResourceBundleNullBaseName(){
        getResourceBundle((String) null, Locale.CHINA);
    }

    /**
     * Test get resource bundle empty base name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetResourceBundleEmptyBaseName(){
        getResourceBundle("", Locale.CHINA);
    }

    /**
     * Test get resource bundle blank base name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetResourceBundleBlankBaseName(){
        getResourceBundle(" ", Locale.CHINA);
    }
}
