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
package com.feilong.core.bean;

import static java.util.Collections.emptyMap;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.Transformer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.convert;

public class ToMapTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ToMapTemp.class);

    @Test
    public void test(){
        Map<String, String> map = new HashMap<>();
        map.put("1", "2");

        Transformer<String, Integer> transformer = new ConvertTransformer<>(Integer.class);

        Map<Integer, Integer> returnMap = toMap(map, transformer, transformer);
        // 输出测试
        for (Map.Entry<Integer, Integer> entry : returnMap.entrySet()){
            Integer key = entry.getKey();
            Integer value = entry.getValue();

            LOGGER.debug("key:[{}],value:[{}]", key, value);
        }
    }

    /**
     * 将 诸如 Map{@code <String, String>} 类型转成 Map{@code <Integer, Integer>} 类型.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>返回的是 {@link LinkedHashMap},顺序依照入参 inputMap</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 将Map{@code <String, String>} 类型转成 Map{@code <Integer, Integer>} 类型
     * </p>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = new HashMap{@code <>}();
     * map.put("1", "2");
     * 
     * Transformer{@code <String, Integer>} transformer = new ConvertTransformer{@code <>}(Integer.class);
     * 
     * Map{@code <Integer, Integer>} returnMap = toMap(map, transformer, transformer);
     * 
     * // 输出测试
     * for (Map.Entry{@code <Integer, Integer>} entry : returnMap.entrySet()){
     *     Integer key = entry.getKey();
     *     Integer value = entry.getValue();
     *     LOGGER.debug("key:[{}],value:[{}]", key, value);
     * }
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key:[1],value:[2]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param <I>
     *            the generic type
     * @param <J>
     *            the generic type
     * @param inputMap
     *            the input map
     * @param keyTransformer
     *            key 转换器
     * @param valueTransformer
     *            value 转换器
     * @return 如果 <code>inputMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keyTransformer</code> 是null,那么key直接使用<code>inputMap</code>的key<br>
     *         如果 <code>valueTransformer</code> 是null,那么value 直接使用<code>inputMap</code>的 value<br>
     * @since 1.9.2
     */
    @SuppressWarnings("unchecked")
    public final static <K, V, I, J> Map<I, J> toMap(
                    Map<K, V> inputMap,
                    final Transformer<K, I> keyTransformer,
                    final Transformer<V, J> valueTransformer){
        if (isNullOrEmpty(inputMap)){
            return emptyMap();
        }

        Map<I, J> returnMap = new LinkedHashMap<>(inputMap.size());

        for (Map.Entry<K, V> entry : inputMap.entrySet()){
            K key = entry.getKey();
            V value = entry.getValue();

            returnMap.put(
                            null == keyTransformer ? (I) key : keyTransformer.transform(key),
                            null == valueTransformer ? (J) key : valueTransformer.transform(value));
        }
        return returnMap;
    }

    /**
     * 将对象转成指定 targetType 类型的转换器.
     *
     * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
     * @param <T>
     *            the generic type
     * @param <V>
     *            the value type
     * @see com.feilong.core.bean.ConvertUtil#convert(Object, Class)
     * @since 1.9.2
     */
    private static final class ConvertTransformer<T, V> implements Transformer<T, V>,Serializable{

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 809439581555072949L;

        /** The target type. */
        private final Class<V>    targetType;

        /**
         * Instantiates a new convert transformer.
         *
         * @param targetType
         *            the target type
         */
        public ConvertTransformer(Class<V> targetType){
            super();
            this.targetType = targetType;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.apache.commons.collections4.Transformer#transform(java.lang.Object)
         */
        @Override
        public V transform(final T input){
            return convert(input, targetType);
        }
    }
}
