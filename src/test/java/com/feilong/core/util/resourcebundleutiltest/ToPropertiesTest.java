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

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static com.feilong.core.util.ResourceBundleUtil.toProperties;

/**
 * The Class ResourceBundleUtilToPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToPropertiesTest{

    /**
     * Test to properties null resource bundle.
     */
    @Test(expected = NullPointerException.class)
    public void testToPropertiesNullResourceBundle(){
        toProperties(null);
    }

    /**
     * Test to properties.
     */
    @Test
    public void testToProperties(){
        Properties properties = toProperties(getResourceBundle("messages/memcached"));
        assertThat(toMap(properties), hasKey("memcached.alivecheck"));
    }

    /**
     * Test to properties empty.
     */
    @Test
    public void testToPropertiesEmpty(){
        Properties properties = toProperties(getResourceBundle("messages/empty"));
        assertEquals(emptyMap(), properties);
    }

}
