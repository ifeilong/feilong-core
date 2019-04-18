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
package com.feilong.core.util.predicate;

import com.feilong.core.util.RegexUtil;

/**
 * 正则表达式类型的 StringPredicate.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see RegexUtil#matches(String, CharSequence)
 * @since 1.13.2
 */
public class RegexStringPredicate implements StringPredicate{

    /** The regex pattern. */
    private final String regexPattern;

    //---------------------------------------------------------------

    /**
     * Instantiates a new regex string predicate.
     *
     * @param regexPattern
     *            the regex pattern
     */
    public RegexStringPredicate(String regexPattern){
        super();
        this.regexPattern = regexPattern;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value){
        return RegexUtil.matches(regexPattern, value);
    }

}
