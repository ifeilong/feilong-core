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
package com.feilong.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.7.2
 */
public class CartesianProductTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(CartesianProductTest.class);

    /**
     * TestCollectionUtilTest.
     */
    @Test
    public void testCollectionUtilTest1(){
        Integer[] v1 = { 1, 2, 3 };
        Integer[] v2 = { 1, 2 };
        Integer[] v3 = { 5 };

        int nIterations = v1.length * v2.length * v3.length;
        for (int i = 0; i < nIterations; i++){
            LOGGER.debug("{} {} {}", v1[i % v1.length], v2[i % v2.length], v3[i % v3.length]);
        }
    }

    /**
     * Test collection util test11.
     */
    @Test
    public void testCollectionUtilTest11(){
        Integer[] v1 = { 1, 2, 3 };
        Integer[] v2 = { 1, 2 };
        Integer[] v3 = { 5 };
        Integer[] v4 = { 4, 8 };

        LOGGER.debug(JsonUtil.format(cartesianProduct(v1, v2, v3, v4), 0, 4));
        LOGGER.debug(JsonUtil.format(cartesianProduct(v1)));

    }

    /**
     * Test collection util test11.
     */
    @Test
    public void testCollectionUtilTest112(){
        LOGGER.debug(
                        JsonUtil.format(
                                        cartesianProduct(
                                                        ConvertUtil.toList(1, 2, 3),
                                                        ConvertUtil.toList(1, 2),
                                                        ConvertUtil.toList(5),
                                                        ConvertUtil.toList(4, 8)),
                                        0,
                                        4));

    }

    /**
     * Cartesian product.
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return the list
     * 
     * @see <a href="http://blog.chinaunix.net/uid-21125022-id-4392818.html">笛卡尔乘积及java算法实现 </a>
     * @see com.google.common.collect.Sets#cartesianProduct(Set<? extends B>...)
     * @see com.google.common.collect.Lists#cartesianProduct(List<? extends B>...)
     * @see <a href="http://stackoverflow.com/questions/1719594/iterative-cartesian-product-in-java/1723050#1723050">Iterative Cartesian
     *      Product in Java</a>
     * @see <a href="http://baike.baidu.com/subview/348542/348542.htm#4_2">程序使用说明</a>
     */
    private static <T> List<List<T>> cartesianProduct(T[]...arrays){
        List<Iterable<T>> list = new ArrayList<>();
        for (T[] array : arrays){
            list.add(ConvertUtil.toList(array));
        }
        return cartesianProduct(list);
    }

    @SafeVarargs
    private static <T> List<List<T>> cartesianProduct(Iterable<T>...iterables){
        List<Iterable<T>> list = ConvertUtil.toList(iterables);
        return cartesianProduct(list);
    }

    private static <T, I extends Iterable<T>> List<List<T>> cartesianProduct(Iterable<I> iterables){
        int length = 1;
        for (Iterable<T> iterable : iterables){
            length *= IterableUtils.size(iterable);
        }

        List<List<T>> returnList = new ArrayList<>();
        for (int i = 0; i < length; i++){
            //***********从不同的数组中取值*************************************
            List<T> list = new ArrayList<T>();
            for (Iterable<T> iterable : iterables){
                list.add(IterableUtils.get(iterable, i % IterableUtils.size(iterable)));
            }
            LOGGER.debug(list.toString());
            //************************************************
            returnList.add(list);
        }

        return returnList;
    }

}
