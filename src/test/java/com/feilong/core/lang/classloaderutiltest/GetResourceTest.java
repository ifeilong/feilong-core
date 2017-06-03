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
package com.feilong.core.lang.classloaderutiltest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.lang.ClassLoaderUtil;

/**
 * The Class ClassLoaderUtilGetResourceTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetResourceTest{

    /**
     * Test get resource.
     */
    @Test
    public void testGetResource(){
        assertThat(ClassLoaderUtil.getResource(""), is(notNullValue()));
        assertThat(ClassLoaderUtil.getResource("com"), is(notNullValue()));
    }

    /**
     * Test get resource not exist.
     */
    @Test
    public void testGetResourceNotExist(){
        assertEquals(null, ClassLoaderUtil.getResource("feilong-core-test.properties"));
    }

    /**
     * Test get resource 3.
     */
    @Test
    public void testGetResource3(){
        assertThat(ClassLoaderUtil.getResource("messages/feilong-core-test.properties"), is(notNullValue()));
    }

    /**
     * Test get resource starts with slash.
     */
    @Test
    public void testGetResourceStartsWithSlash(){
        assertThat(ClassLoaderUtil.getResource("/messages/feilong-core-test.properties"), is(notNullValue()));
    }

    /**
     * Test get resource null resource name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetResourceNullResourceName(){
        ClassLoaderUtil.getResource(null);
    }
}
