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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.util.ResourceBundleUtil.getResourceBundle;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.core.entity.VarBean;
import com.feilong.core.util.ResourceBundleUtil;

/**
 * The Class BeanUtilPopulateAliasBeanTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class PopulateAliasBeanTest{

    /**
     * Test populate alias bean.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateAliasBean(){
        Map<String, String> readPropertiesToMap = ResourceBundleUtil.toMap(getResourceBundle("messages.feilong-core-test"));

        VarBean varBean = new VarBean();
        VarBean populateAliasBean = BeanUtil.populateAliasBean(varBean, readPropertiesToMap);

        assertThat(
                        populateAliasBean,
                        allOf(//
                                        hasProperty("arguments", is("my name is {0},age is {1}")),
                                        hasProperty("audio", is("Audio")),
                                        hasProperty("longs", arrayContaining(5L, 8L, 7L, 6L)),
                                        hasProperty("b", is(true))
                        //
                        ));
    }

    /**
     * Test populate alias null bean.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testPopulateAliasNullBean(){
        Map<String, String> readPropertiesToMap = ResourceBundleUtil.toMap(getResourceBundle("messages.feilong-core-test"));
        BeanUtil.populateAliasBean(null, readPropertiesToMap);
    }

    /**
     * Test populate alias null alias and value map.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateAliasNullAliasAndValueMap(){
        VarBean varBean = new VarBean();
        assertEquals(varBean, BeanUtil.populateAliasBean(varBean, null));
    }

    /**
     * Test populate alias empty alias and value map.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testPopulateAliasEmptyAliasAndValueMap(){
        VarBean varBean = new VarBean();
        assertEquals(varBean, BeanUtil.populateAliasBean(varBean, new HashMap<String, String>()));
    }
}
