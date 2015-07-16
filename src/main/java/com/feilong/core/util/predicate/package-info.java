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
/**
 *自定义的  Predicate,常用在 {@link com.feilong.core.util.CollectionsUtil#select(java.util.Collection, org.apache.commons.collections4.Predicate)},{@link com.feilong.core.util.CollectionsUtil#groupCount(java.util.Collection, org.apache.commons.collections4.Predicate, String)} 等地方.
 * @author feilong 
 * @version 1.2.0 2015年4月27日 下午1:47:11 
 * @since 1.2.0
 @see org.apache.commons.collections4.PredicateUtils
 @see org.apache.commons.collections4.functors.AllPredicate
 @see org.apache.commons.collections4.functors.AndPredicate
 @see org.apache.commons.collections4.functors.AnyPredicate
 @see org.apache.commons.collections4.functors.EqualPredicate
 @see org.apache.commons.collections4.functors.ExceptionPredicate
 @see org.apache.commons.collections4.functors.FalsePredicate
 @see org.apache.commons.collections4.functors.IdentityPredicate
 @see org.apache.commons.collections4.functors.InstanceofPredicate
 @see org.apache.commons.collections4.functors.InvokerTransformer
 @see org.apache.commons.collections4.functors.NonePredicate
 @see org.apache.commons.collections4.functors.NotNullPredicate
 @see org.apache.commons.collections4.functors.NotPredicate
 @see org.apache.commons.collections4.functors.NullIsExceptionPredicate
 @see org.apache.commons.collections4.functors.NullIsFalsePredicate
 @see org.apache.commons.collections4.functors.NullIsTruePredicate
 @see org.apache.commons.collections4.functors.NullPredicate
 @see org.apache.commons.collections4.functors.OnePredicate
 @see org.apache.commons.collections4.functors.OrPredicate
 @see org.apache.commons.collections4.functors.TransformedPredicate
 @see org.apache.commons.collections4.functors.TransformerPredicate
 @see org.apache.commons.collections4.functors.TruePredicate
 @see org.apache.commons.collections4.functors.UniquePredicate
 */
package com.feilong.core.util.predicate;