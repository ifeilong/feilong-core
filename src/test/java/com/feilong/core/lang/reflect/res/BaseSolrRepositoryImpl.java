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
package com.feilong.core.lang.reflect.res;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ClassUtilTemp;
import com.feilong.core.lang.reflect.TypeUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class BaseSolrRepositoryImpl.
 *
 * @param <T>
 *            the generic type
 * @param <PK>
 *            the generic type
 */
public abstract class BaseSolrRepositoryImpl<T, PK extends Serializable> implements BaseSolrRepository<T, PK>{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSolrRepositoryImpl.class);

    /** 和solr schemal对应的对象类型. */
    protected Class<T>          modelClass;

    /**
     * The Constructor.
     */
    public BaseSolrRepositoryImpl(){
        Class<?>[] genericClassArray = TypeUtil.getGenericSuperclassParameterizedRawTypes(this.getClass());
        this.modelClass = (Class<T>) genericClassArray[0];
        //TypeUtils.getTypeArguments(this.getClass());
        LOGGER.debug(JsonUtil.format(ClassUtilTemp.getClassInfoMapForLog(modelClass)));
    }
}