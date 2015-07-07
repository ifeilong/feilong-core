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
package com.feilong.core.lang.reflect;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.reflect.res.BaseSolrRepository;
import com.feilong.core.lang.reflect.res.SkuItemRepositoryImpl;
import com.feilong.core.lang.reflect.res.SkuItemRepositoryImpl2;
import com.feilong.core.lang.reflect.res.SkuItemRepositoryImpl3;
import com.feilong.core.lang.reflect.res.SkuItemRepositoryImpl4;
import com.feilong.core.tools.jsonlib.JsonUtil;

/**
 * The Class TypeUtilTest.
 *
 * @author feilong
 * @version 1.1.1 2015年4月15日 下午1:34:51
 * @since 1.1.1
 */
public class TypeUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TypeUtilTest.class);

    /**
     * Test.
     */
    @Test
    public final void test(){
        LOGGER.info("" + new SkuItemRepositoryImpl());
    }

    /**
     * Test2.
     */
    @Test
    public final void test2(){
        LOGGER.info("" + new SkuItemRepositoryImpl2());
    }

    /**
     * Test3.
     */
    @Test
    public final void test3(){
        LOGGER.info("" + new SkuItemRepositoryImpl3());

        Class<?>[] genericInterfacesParameterizedRawTypes = TypeUtil.getGenericInterfacesParameterizedRawTypes(
                        SkuItemRepositoryImpl3.class,
                        BaseSolrRepository.class);

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(genericInterfacesParameterizedRawTypes));
        }
    }

    /**
     * Test4.
     */
    @Test
    public final void test4(){
        SkuItemRepositoryImpl4<String> skuItemRepository = new SkuItemRepositoryImpl4<String>();
        //Class<?> componentType = SkuItemRepositoryImpl4.class.getComponentType();
        Class<?>[] genericInterfacesParameterizedRawTypes = TypeUtil
                        .getGenericSuperclassParameterizedRawTypes(skuItemRepository.getClass());

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(genericInterfacesParameterizedRawTypes));
        }
    }
}
