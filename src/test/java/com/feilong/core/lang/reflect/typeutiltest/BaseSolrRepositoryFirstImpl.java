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

import java.io.Serializable;

import com.feilong.core.lang.reflect.TypeUtil;

/**
 * The Class BaseSolrRepositoryFirstImpl.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @param <PK>
 *            the generic type
 */
public abstract class BaseSolrRepositoryFirstImpl<T, PK extends Serializable> implements BaseSolrRepository<T, PK>{

    /** 和solr schemal对应的对象类型. */
    protected Class<T> modelClass;

    /**
     * The Constructor.
     */
    @SuppressWarnings("unchecked")
    public BaseSolrRepositoryFirstImpl(){
        Class<?>[] genericClassArray = TypeUtil.getGenericSuperclassParameterizedRawTypes(this.getClass());
        this.modelClass = (Class<T>) genericClassArray[0];
        //TypeUtils.getTypeArguments(this.getClass());
        // LOGGER.debug(JsonUtil.format(ClassUtilTemp.getClassInfoMapForLog(modelClass)));
    }
}