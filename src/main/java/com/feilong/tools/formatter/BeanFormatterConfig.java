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

/**
 * The Class BeanFormatterConfig.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @since 1.8.5
 */
public class BeanFormatterConfig<T> {

    //*************************通常二者选其一设置**********************
    /** 排除的属性名字, 会提取所有的属性, 然后剔除 exclude部分. */
    private String[] excludePropertyNames;

    /** 包含的属性名字,会提取所有的属性,然后仅取 include部分. */
    private String[] includePropertyNames;

    //***********************************************************
    /** 显示属性(列 )的顺序. */
    private String[] sorts;

    /** 迭代对象的类型. */
    private Class<T> beanClass;

    //***********************************************************

    /**
     * The Constructor.
     *
     * @param beanClass
     *            the bean class
     */
    public BeanFormatterConfig(Class<T> beanClass){
        super();
        this.beanClass = beanClass;
    }

    //***********************************************************
    /**
     * 获得 排除的属性名字, 会提取所有的属性, 然后剔除 exclude部分.
     *
     * @return the excludePropertyNames
     */
    public String[] getExcludePropertyNames(){
        return excludePropertyNames;
    }

    /**
     * 设置 排除的属性名字, 会提取所有的属性, 然后剔除 exclude部分.
     *
     * @param excludePropertyNames
     *            the excludePropertyNames to set
     */
    public void setExcludePropertyNames(String...excludePropertyNames){
        this.excludePropertyNames = excludePropertyNames;
    }

    /**
     * 获得 包含的属性名字,会提取所有的属性,然后仅取 include部分.
     *
     * @return the includePropertyNames
     */
    public String[] getIncludePropertyNames(){
        return includePropertyNames;
    }

    /**
     * 设置 包含的属性名字,会提取所有的属性,然后仅取 include部分.
     *
     * @param includePropertyNames
     *            the includePropertyNames to set
     */
    public void setIncludePropertyNames(String...includePropertyNames){
        this.includePropertyNames = includePropertyNames;
    }

    /**
     * 获得 迭代对象的类型.
     *
     * @return the 迭代对象的类型
     */
    public Class<T> getBeanClass(){
        return beanClass;
    }

    /**
     * 设置 迭代对象的类型.
     *
     * @param beanClass
     *            the new 迭代对象的类型
     */
    public void setBeanClass(Class<T> beanClass){
        this.beanClass = beanClass;
    }

    /**
     * 获得 显示属性(列 )的顺序.
     *
     * @return the sorts
     */
    public String[] getSorts(){
        return sorts;
    }

    /**
     * 设置 显示属性(列 )的顺序.
     *
     * @param sorts
     *            the sorts to set
     */
    public void setSorts(String...sorts){
        this.sorts = sorts;
    }
}
