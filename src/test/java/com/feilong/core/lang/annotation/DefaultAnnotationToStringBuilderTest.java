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
package com.feilong.core.lang.annotation;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

import com.feilong.core.bean.Alias;
import com.feilong.core.entity.DangaMemCachedConfig;

/**
 * The Class DefaultAnnotationToStringBuilderTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.4
 */
public class DefaultAnnotationToStringBuilderTest{

    /** The annotation to string builder. */
    private final AnnotationToStringBuilder annotationToStringBuilder = DefaultAnnotationToStringBuilder.instance();

    /**
     * Test.
     */
    @Test
    public void test(){
        assertEquals(EMPTY, annotationToStringBuilder.build(null));
    }

    /**
     * Test 1.
     */
    @Test
    public void test1(){
        Field field = FieldUtils.getDeclaredField(DangaMemCachedConfig.class, "serverList", true);
        Alias alias = field.getAnnotation(Alias.class);

        assertEquals(
                        "@com.feilong.core.bean.Alias(name=memcached.serverlist, sampleValue=172.20.31.23:11211,172.20.31.22:11211)",
                        annotationToStringBuilder.build(alias));
    }

}
