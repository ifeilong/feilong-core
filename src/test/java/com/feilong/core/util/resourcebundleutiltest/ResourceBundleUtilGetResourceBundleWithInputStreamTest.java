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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.junit.Ignore;
import org.junit.Test;

import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static com.feilong.core.util.ResourceBundleUtil.toMap;

/**
 * The Class ResourceBundleUtilGetResourceBundleWithInputStreamTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ResourceBundleUtilGetResourceBundleWithInputStreamTest{

    /**
     * Test get resource bundle null input stream.
     */
    @Test(expected = NullPointerException.class)
    public void testGetResourceBundleNullInputStream(){
        getResourceBundle((InputStream) null);
    }

    /**
     * Test get resource bundle.
     *
     * @throws FileNotFoundException
     *             the file not found exception
     */
    @Test()
    @Ignore
    public void testGetResourceBundle() throws FileNotFoundException{
        FileInputStream inputStream = new FileInputStream("E:\\DataCommon\\Files\\Java\\config\\mail-read.properties");
        ResourceBundle resourceBundle = getResourceBundle(inputStream);
        assertThat(toMap(resourceBundle), hasKey("incoming.pop.hostname"));
    }

}
