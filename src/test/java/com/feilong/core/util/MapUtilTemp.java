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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toList;

public class MapUtilTemp{

    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtilTemp.class);

    @Test
    public void testToArrayValueMap1(){
        Map<String, String> singleValueMap = MapUtil.newLinkedHashMap(2);
        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        Map<String, String[]> arrayValueMap = toArrayValueMap1(singleValueMap);

        // java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
        String[] strings = arrayValueMap.get("province");
    }

    public static <K, V> Map<K, V[]> toArrayValueMap1(Map<K, V> singleValueMap){
        Map<K, V[]> arrayValueMap = MapUtil.newLinkedHashMap(singleValueMap.size());//保证顺序和参数singleValueMap顺序相同
        for (Map.Entry<K, V> entry : singleValueMap.entrySet()){
            //V[] array = toArray(entry.getValue());
            V[] array = (V[]) (toList(entry.getValue()).toArray());
            arrayValueMap.put(entry.getKey(), array);//注意此处的Value不要声明成V,否则会变成Object数组
        }
        return arrayValueMap;
    }

    @Test
    public void testToListValueMap(){
        Map<String, String> singleValueMap = MapUtil.newLinkedHashMap(2);
        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        Map<String, List<String>> arrayValueMap = toListValueMap(singleValueMap);
        List<String> strings = arrayValueMap.get("province");

        System.out.println(JsonUtil.format(arrayValueMap));//TODO:remove

    }

    public static <K, V> Map<K, List<V>> toListValueMap(Map<K, V> singleValueMap){
        Map<K, List<V>> arrayValueMap = MapUtil.newLinkedHashMap(singleValueMap.size());//保证顺序和参数singleValueMap顺序相同
        for (Map.Entry<K, V> entry : singleValueMap.entrySet()){
            arrayValueMap.put(entry.getKey(), toList(entry.getValue()));
        }
        return arrayValueMap;
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testMapUtilTest2(){
        LOGGER.debug("{}", Integer.highestOneBit((125 - 1) << 1));

        for (int i = 0, j = 10; i < j; ++i){
            LOGGER.debug("{},{},{}", i, capacity(i), (int) (i / 0.75f) + 1);
        }
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as
     * long as it grows no larger than expectedSize and the load factor is >= its
     * default (0.75).
     *
     * @param expectedSize
     *            the expected size
     * @return the int
     */
    static int capacity(int expectedSize){
        if (expectedSize < 3){
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824){
            // This is the calculation used in JDK8 to resize when a putAll happens; 
            //it seems to be the most conservative calculation we can make.  
            //0.75 is the default load factor.
            return (int) (expectedSize / 0.75F + 1.0F);
        }
        return Integer.MAX_VALUE; // any large value
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testNewHashMap(){
        Map<Object, Object> newHashMap = MapUtil.newHashMap(100);
        assertThat(newHashMap.size(), is(0));
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testNewHashMap1(){
        Map<Integer, Integer> map = MapUtil.newHashMap(100);
        for (int i = 0, j = 100; i < j; ++i){
            map.put(i, i);
        }

        //  Map<Object, Object> newHashMap = MapUtil.newHashMap(map.size());
        Map<Object, Object> newHashMap = new HashMap<>(256);
        newHashMap.putAll(map);
        LOGGER.debug("{}", newHashMap.size());
    }
}
