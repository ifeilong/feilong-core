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
package com.feilong.core.util.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang3.Validate;

import com.feilong.core.util.RegexUtil;

/**
 * 正则表达式分组 number group 比较器.
 * 
 * <h3>背景:</h3>
 * 
 * <blockquote>
 * <p>
 * 如果要比较 "ppt-coreContent2","ppt-coreContent13","ppt-coreContent12",<br>
 * 默认直接使用字符串比较的话, ppt-coreContent<b>13</b> 会排在 ppt-coreContent<b>2</b> 前面
 * </p>
 * 
 * <p>
 * 针对这种情况,你可以使用
 * </p>
 * 
 * <pre class="code">
 * Collections.sort(includedFileUrlList, new RegexGroupNumberComparator(".*ppt-coreContent(\\d*).png"));
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * 默认会提取正则表达式中的 <span style="color:red">第一个group 转成int 类型</span> 进行比较
 * </p>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.apache.commons.io.comparator.NameFileComparator"
 * @see "org.apache.commons.io.comparator.DirectoryFileComparator"
 * @see "org.apache.commons.io.comparator.DefaultFileComparator"
 * @see "org.apache.commons.io.comparator.ExtensionFileComparator"
 * @see "org.apache.commons.io.comparator.PathFileComparator"
 * @see "org.apache.commons.io.comparator.SizeFileComparator"
 * @since 1.4.0
 */
public class RegexGroupNumberComparator implements Comparator<String>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3400735378238717278L;

    /** 文件名称的正则表达式,主要方便提取数字,比如 ".*ppt-coreContent(\\d*).png". */
    private final String      regexPattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new regex group number comparator.
     * 
     * <p style="color:red">
     * 默认会提取正则表达式中的第一个group 转成int 类型进行比较
     * </p>
     * 
     * <p>
     * 比如 要比较 "ppt-coreContent2","ppt-coreContent13","ppt-coreContent12",默认直接使用字符串比较的话, ppt-coreContent13会排在 ppt-coreContent2前面
     * </p>
     * 
     * <p>
     * 针对这种情况,你可以使用
     * </p>
     * 
     * <pre class="code">
     * Collections.sort(includedFileUrlList, new RegexGroupNumberComparator(".*ppt-coreContent(\\d*).png"));
     * </pre>
     * 
     * <p>
     * 如果 <code>regexPattern</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>regexPattern</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * </p>
     * 
     * @param regexPattern
     *            文件名称的正则表达式,主要方便提取数字,比如 ".*ppt-coreContent(\\d*).png"
     */
    public RegexGroupNumberComparator(String regexPattern){
        Validate.notBlank(regexPattern, "regexPattern can't be blank!");
        this.regexPattern = regexPattern;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(String s1,String s2){
        String group1 = RegexUtil.group(regexPattern, s1, 1);
        String group2 = RegexUtil.group(regexPattern, s2, 1);

        Integer parseInt = Integer.parseInt(group1);
        Integer parseInt2 = Integer.parseInt(group2);
        return parseInt.compareTo(parseInt2);
    }
}
