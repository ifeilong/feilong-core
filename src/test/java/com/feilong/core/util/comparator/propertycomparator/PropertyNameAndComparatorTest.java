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
package com.feilong.core.util.comparator.propertycomparator;

import org.apache.commons.collections4.ComparatorUtils;
import org.junit.Test;

import com.feilong.core.util.comparator.PropertyComparator;

public class PropertyNameAndComparatorTest{

    //----------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testPropertyComparatorNullPropertyNameAndNaturalComparator(){
        new PropertyComparator<>(null, ComparatorUtils.NATURAL_COMPARATOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyComparatorEmptyPropertyNameAndNaturalComparator(){
        new PropertyComparator<>("", ComparatorUtils.NATURAL_COMPARATOR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyComparatorBlankPropertyNameAndNaturalComparator(){
        new PropertyComparator<>("    ", ComparatorUtils.NATURAL_COMPARATOR);
    }
}