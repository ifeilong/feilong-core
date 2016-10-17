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

/**
 * Sku Item for solr.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SkuItem implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8190409138133199386L;

    /** showOrder 显示顺序. */
    private float             showOrder;

    /**
     * 获得 showOrder 显示顺序.
     *
     * @return the showOrder
     */
    public float getShowOrder(){
        return showOrder;
    }

    /**
     * 设置 showOrder 显示顺序.
     *
     * @param showOrder
     *            the showOrder to set
     */
    public void setShowOrder(float showOrder){
        this.showOrder = showOrder;
    }

}
