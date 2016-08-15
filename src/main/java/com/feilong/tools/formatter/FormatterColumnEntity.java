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
package com.feilong.tools.formatter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 定制每列的顺序以及标题的名称.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
//实体需要声明成 public的 ,否则反射取不到
public class FormatterColumnEntity{

    /** 属性名字. */
    private String propertyName;

    /**
     * 显示的列的名称.
     * 
     * @see FormatterColumn#name()
     */
    private String name;

    /** 显示顺序. */
    private int    order;

    //***************************************************************************

    /**
     * Instantiates a new formatter column entity.
     *
     * @param name
     *            显示的列的名称
     * @param propertyName
     *            属性名字
     * @param order
     *            显示顺序
     */
    public FormatterColumnEntity(String name, String propertyName, int order){
        super();
        this.name = name;
        this.propertyName = propertyName;
        this.order = order;
    }

    //***************************************************************************

    /**
     * 获得 显示的列的名称.
     *
     * @return the name
     * @see FormatterColumn#name()
     */
    public String getName(){
        return name;
    }

    /**
     * 设置 显示的列的名称.
     *
     * @param name
     *            the name to set
     * @see FormatterColumn#name()
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * 获得 显示顺序.
     *
     * @return the order
     */
    public int getOrder(){
        return order;
    }

    /**
     * 设置 显示顺序.
     *
     * @param order
     *            the order to set
     */
    public void setOrder(int order){
        this.order = order;
    }

    /**
     * 获得 属性名字.
     *
     * @return the propertyName
     */
    public String getPropertyName(){
        return propertyName;
    }

    /**
     * 设置 属性名字.
     *
     * @param propertyName
     *            the propertyName to set
     */
    public void setPropertyName(String propertyName){
        this.propertyName = propertyName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
