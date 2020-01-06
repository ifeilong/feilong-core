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

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.ResourceBundle;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.ResourceBundleUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class ResourceBundleUtilGetValueWithResourceBundleParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetValueWithResourceBundleParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<ResourceBundle, String, Object[], String>{

    /** The resource bundle. */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("messages/feilong-core-test");

    /**
     * Test get value.
     */
    @Test
    public void testGetValue(){
        assertEquals(expectedValue, ResourceBundleUtil.getValue(input1, input2, input3));
    }

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:ResourceBundleUtil.getValue(\"{0}\",\"{1}\",\"{2}\")={3}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray(RESOURCE_BUNDLE, "config_test_array", toArray(), "5,8,7,6"),
                        toArray(RESOURCE_BUNDLE, "test.arguments", toArray("feilong", 28), "my name is feilong,age is 28"),
                        toArray(RESOURCE_BUNDLE, "with_space_value", toArray(), "a "),

                        toArray(RESOURCE_BUNDLE, "emptyValue", null, EMPTY),
                        toArray(RESOURCE_BUNDLE, "emptyValue", toArray(), EMPTY),

                        toArray(RESOURCE_BUNDLE, "wo_bu_cun_zai", toArray(), EMPTY),
                        toArray(ResourceBundle.getBundle("messages.empty"), "wo_bu_cun_zai", toArray(), EMPTY)
        //  
        );
    }
}
