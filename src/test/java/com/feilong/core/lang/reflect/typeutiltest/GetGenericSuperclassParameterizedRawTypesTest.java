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

/**
 * The Class TypeUtilGetGenericSuperclassParameterizedRawTypesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetGenericSuperclassParameterizedRawTypesTest{

    /**
     * Test get generic superclass parameterized raw types.
     */
    @Test
    public void testGetGenericSuperclassParameterizedRawTypes(){
        Class<?>[] genericSuperclassParameterizedRawTypes = TypeUtil.getGenericSuperclassParameterizedRawTypes(SkuItemRepositoryImpl.class);
        assertArrayEquals(toArray(SkuItem.class, Long.class), genericSuperclassParameterizedRawTypes);
    }

    /**
     * Test get generic superclass parameterized raw types 2.
     */
    @Test
    public void testGetGenericSuperclassParameterizedRawTypes2(){
        Class<?>[] genericSuperclassParameterizedRawTypes = TypeUtil
                        .getGenericSuperclassParameterizedRawTypes(SkuItemRepositoryImpl2.class);
        assertArrayEquals(toArray(SkuItem.class, Long.class), genericSuperclassParameterizedRawTypes);
    }

    /**
     * Test get generic superclass parameterized raw types no parameterized type class.
     */
    @Test(expected = NullPointerException.class)
    public void testGetGenericSuperclassParameterizedRawTypesNoParameterizedTypeClass(){
        TypeUtil.getGenericSuperclassParameterizedRawTypes(SkuItemRepositoryImplNoParameterizedTypeClassImpl.class);
    }

    /**
     * Test get generic superclass parameterized raw types no parent class.
     */
    @Test(expected = NullPointerException.class)
    public void testGetGenericSuperclassParameterizedRawTypesNoParentClass(){
        SkuItemRepositoryImplNoParentClass<String> skuItemRepository = new SkuItemRepositoryImplNoParentClass<>();
        TypeUtil.getGenericSuperclassParameterizedRawTypes(skuItemRepository.getClass());
    }

    /**
     * Test get generic superclass parameterized raw types null class.
     */
    @Test(expected = NullPointerException.class)
    public void testGetGenericSuperclassParameterizedRawTypesNullClass(){
        TypeUtil.getGenericSuperclassParameterizedRawTypes(null);
    }
}
