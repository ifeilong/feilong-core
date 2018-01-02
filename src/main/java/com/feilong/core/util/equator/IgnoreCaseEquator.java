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
package com.feilong.core.util.equator;

import java.io.Serializable;

import org.apache.commons.collections4.Equator;
import org.apache.commons.lang3.StringUtils;

/**
 * 忽视大小写的实现.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.collections4.functors.DefaultEquator
 * @since 1.10.1
 */
public class IgnoreCaseEquator implements Equator<String>,Serializable{

    /** The Constant serialVersionUID. */
    private static final long             serialVersionUID = 5700113137952086493L;

    /** Static instance. */
    // the static instance works for all types
    public static final IgnoreCaseEquator INSTANCE         = new IgnoreCaseEquator();

    //---------------------------------------------------------------

    /**
     * Restricted constructor.
     */
    private IgnoreCaseEquator(){
        super();
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Equator#equate(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean equate(final String s1,final String s2){
        return StringUtils.equalsIgnoreCase(s1, s2);
    }

    //---------------------------------------------------------------

    /**
     * {@inheritDoc}
     *
     * @return <code>s.hashCode()</code> if <code>s</code> is non-<code>null</code>, else -1.
     */
    @Override
    public int hash(final String s){
        return s == null ? -1 : s.hashCode();
    }

}
