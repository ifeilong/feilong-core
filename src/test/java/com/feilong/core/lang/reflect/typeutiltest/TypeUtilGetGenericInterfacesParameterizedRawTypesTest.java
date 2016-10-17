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
package com.feilong.core.lang.reflect.typeutiltest;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.feilong.core.lang.reflect.TypeUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;

public class TypeUtilGetGenericInterfacesParameterizedRawTypesTest{

    @Test
    public void testGetGenericInterfacesParameterizedRawTypes(){
        Class<?>[] rawTypes = TypeUtil
                        .getGenericInterfacesParameterizedRawTypes(SkuItemRepositoryInterfaceImpl.class, BaseSolrRepository.class);

        assertArrayEquals(toArray(SkuItem.class, Long.class), rawTypes);
    }

    @Test(expected = NullPointerException.class)
    public void testGetGenericInterfacesParameterizedRawTypesNoExtractInterfaceClass(){
        TypeUtil.getGenericInterfacesParameterizedRawTypes(SkuItemRepositoryInterfaceImpl.class, BaseSolrRepository2.class);
    }

    /**
     * 没有实现接口的类
     * 
     * @since 1.9.2
     */
    @Test(expected = NullPointerException.class)
    public void testGetGenericInterfacesParameterizedRawTypesNoInterface(){
        TypeUtil.getGenericInterfacesParameterizedRawTypes(SkuItemRepositoryImplNoParentClass.class, BaseSolrRepository.class);
    }

    @Test(expected = NullPointerException.class)
    public void testGetGenericInterfacesParameterizedRawTypesNullKlass(){
        TypeUtil.getGenericInterfacesParameterizedRawTypes(null, BaseSolrRepository.class);
    }

    @Test(expected = NullPointerException.class)
    public void testGetGenericInterfacesParameterizedRawTypesNullExtractInterfaceClass(){
        TypeUtil.getGenericInterfacesParameterizedRawTypes(SkuItemRepositoryInterfaceImpl.class, null);
    }
}
