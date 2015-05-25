/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
/**
 *自定义的  Predicate,常用在 {@link com.feilong.core.util.CollectionsUtil#select(java.util.Collection, org.apache.commons.collections.Predicate)},{@link com.feilong.core.util.CollectionsUtil#groupCount(java.util.Collection, org.apache.commons.collections.Predicate, String)} 等地方.
 * @author <a href="mailto:venusdrogon@163.com">feilong</a> 
 * @version 1.1.2 2015年4月27日 下午1:47:11 
 * @since 1.1.2
 @see org.apache.commons.collections.PredicateUtils
 @see org.apache.commons.collections.functors.AllPredicate
 @see org.apache.commons.collections.functors.AndPredicate
 @see org.apache.commons.collections.functors.AnyPredicate
 @see org.apache.commons.collections.functors.EqualPredicate
 @see org.apache.commons.collections.functors.ExceptionPredicate
 @see org.apache.commons.collections.functors.FalsePredicate
 @see org.apache.commons.collections.functors.IdentityPredicate
 @see org.apache.commons.collections.functors.InstanceofPredicate
 @see org.apache.commons.collections.functors.InvokerTransformer
 @see org.apache.commons.collections.functors.NonePredicate
 @see org.apache.commons.collections.functors.NotNullPredicate
 @see org.apache.commons.collections.functors.NotPredicate
 @see org.apache.commons.collections.functors.NullIsExceptionPredicate
 @see org.apache.commons.collections.functors.NullIsFalsePredicate
 @see org.apache.commons.collections.functors.NullIsTruePredicate
 @see org.apache.commons.collections.functors.NullPredicate
 @see org.apache.commons.collections.functors.OnePredicate
 @see org.apache.commons.collections.functors.OrPredicate
 @see org.apache.commons.collections.functors.TransformedPredicate
 @see org.apache.commons.collections.functors.TransformerPredicate
 @see org.apache.commons.collections.functors.TruePredicate
 @see org.apache.commons.collections.functors.UniquePredicate
 */
package com.feilong.core.util.predicate;