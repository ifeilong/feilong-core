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

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.AnnotationUtils;

/**
 * 默认直接调用 {@link org.apache.commons.lang3.AnnotationUtils#toString(Annotation)}.
 * 
 * <h3>格式:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * 
 * 16:26:30 INFO  (ContextRefreshedEventClientCacheListener.java:123) onApplicationEvent() - url And ClientCache info:    {
 *         "/clientcache": "@com.feilong.spring.web.servlet.interceptor.clientcache.ClientCache(value=20)",
 *         "/item/{itemid}": "@com.feilong.spring.web.servlet.interceptor.clientcache.ClientCache(value=300)",
 *         "/noclientcache": "@com.feilong.spring.web.servlet.interceptor.clientcache.ClientCache(value=0)"
 *     }
 * 
 * </pre>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @see org.apache.commons.lang3.AnnotationUtils#toString(Annotation)
 * @since 1.10.4
 */
public class DefaultAnnotationToStringBuilder<T extends Annotation> implements AnnotationToStringBuilder<T>{

    /** Static instance. */
    // the static instance works for all types
    @SuppressWarnings("rawtypes")
    public static final DefaultAnnotationToStringBuilder DEFAULT_ANNOTATION_TO_STRING_BUILDER = new DefaultAnnotationToStringBuilder();

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.spring.web.method.AnnotationToStringBuilder#build(java.lang.annotation.Annotation)
     */
    @Override
    public String build(T annotation){
        if (null == annotation){
            return EMPTY;
        }

        return AnnotationUtils.toString(annotation);
    }

    //---------------------------------------------------------------

    /**
     * Instance.
     *
     * @param <T>
     *            the generic type
     * @return the default annotation to string builder
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Annotation> DefaultAnnotationToStringBuilder<T> instance(){
        return DEFAULT_ANNOTATION_TO_STRING_BUILDER;
    }
}
