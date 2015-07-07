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
package com.feilong.core.tools.jackson2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 基于 com.fasterxml.jackson.core 的 json 工具类.
 * 
 * <h3>依赖于下面的jar:</h3>
 * 
 * <blockquote>
 * 
 * <pre>
 * {@code
 * <dependency>
 *     <groupId>com.fasterxml.jackson.core</groupId>
 *     <artifactId>jackson-databind</artifactId>
 * </dependency>
 * <dependency>
 *     <groupId>com.fasterxml.jackson.core</groupId>
 *     <artifactId>jackson-annotations</artifactId>
 * </dependency>
 * <dependency>
 *     <groupId>com.fasterxml.jackson.core</groupId>
 *     <artifactId>jackson-core</artifactId>
 * </dependency>
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * @author feilong
 * @version 1.2.2 2015年7月8日 上午1:20:48
 * @since 1.2.2
 */
public class JsonUtil{

    /** The Constant LOGGER. */
    private static final Logger       LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper;
    static{
        objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static <T> T toObject(String json,Class<T> clazz){
        try{
            return objectMapper.readValue(json, clazz);
        }catch (JsonParseException e){
            LOGGER.error(e.getMessage(), e);
        }catch (JsonMappingException e){
            LOGGER.error(e.getMessage(), e);
        }catch (IOException e){
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String toJson(T entity){
        try{
            return objectMapper.writeValueAsString(entity);
        }catch (JsonGenerationException e){
            LOGGER.error(e.getMessage(), e);
        }catch (JsonMappingException e){
            LOGGER.error(e.getMessage(), e);
        }catch (IOException e){
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toCollection(String json,TypeReference<T> typeReference){
        try{
            return objectMapper.readValue(json, typeReference);
        }catch (JsonParseException e){
            LOGGER.error(e.getMessage(), e);
        }catch (JsonMappingException e){
            LOGGER.error(e.getMessage(), e);
        }catch (IOException e){
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Write value as string.
     *
     * @param value
     *            the value
     * @return the string
     * @throws IOException
     *             the IO exception
     * @throws JsonGenerationException
     *             the json generation exception
     * @throws JsonMappingException
     *             the json mapping exception
     * @throws JsonProcessingException
     *             the json processing exception
     * @since 1.2.2
     */
    public static String writeValueAsString(Object value) throws IOException,JsonGenerationException,JsonMappingException,
                    JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        // 仅输出一行json字符串
        //objectMapper.writeValue(System.out, value);

        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // 将字符串美化成多行
        //ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String writeValueAsString = objectMapper.writeValueAsString(value);
        return writeValueAsString;
    }
}
