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

import static com.feilong.core.Validator.isNotNullOrEmpty;

/**
 * 不是null or empty StringPredicate.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * 
 * @see org.apache.commons.collections4.functors.NullPredicate
 * @see org.apache.commons.collections4.functors.NullIsExceptionPredicate
 * @see org.apache.commons.collections4.functors.NullIsFalsePredicate
 * @see org.apache.commons.collections4.functors.NullIsTruePredicate
 * 
 * @see org.apache.commons.collections4.functors.NonePredicate
 * @see org.apache.commons.collections4.functors.NotNullPredicate
 * 
 * @since 1.13.2
 */
public class NotNullOrEmptyStringPredicate implements StringPredicate{

    /** Static instance. */
    // the static instance works for all types
    public static final StringPredicate INSTANCE = new NotNullOrEmptyStringPredicate();

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value){
        return isNotNullOrEmpty(value);
    }
}
