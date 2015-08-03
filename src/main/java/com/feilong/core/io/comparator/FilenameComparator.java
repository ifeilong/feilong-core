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
package com.feilong.core.io.comparator;

import java.util.Comparator;

import com.feilong.core.util.RegexUtil;

/**
 * The Class FilenameComparator.
 *
 * @author feilong
 * @version 1.0.9 2015年1月14日 下午6:38:37
 * @since 1.0.9
 * @deprecated 待重构成通用
 * @see org.apache.commons.io.comparator.NameFileComparator
 */
@Deprecated
public class FilenameComparator implements Comparator<String>{

    /** 文件名称的正则表达式,主要方便提取数字,比如 ".*ppt-coreContent(\\d*).png". */
    private final String regexPattern;

    /**
     * The Constructor.
     *
     * @param regexPattern
     *            文件名称的正则表达式,主要方便提取数字,比如 ".*ppt-coreContent(\\d*).png"
     */
    public FilenameComparator(String regexPattern){
        this.regexPattern = regexPattern;
    }

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
