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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.store.member.UserSameHashCode;
import com.feilong.store.member.UserSameHashCodeWithComparable;

public class PropertyNameTest{

    @Test
    public void testPropertyComparatorWithTreeSetWithComparable(){
        UserSameHashCodeWithComparable userSameHashCodeWithComparable_1_name1 = new UserSameHashCodeWithComparable(1, "name1");
        UserSameHashCodeWithComparable userSameHashCodeWithComparable_5_name5 = new UserSameHashCodeWithComparable(5, "name5_5");
        UserSameHashCodeWithComparable userSameHashCodeWithComparable_5_name6 = new UserSameHashCodeWithComparable(5, "name5_6");
        UserSameHashCodeWithComparable userSameHashCodeWithComparable_5_name1 = new UserSameHashCodeWithComparable(5, "name5_1");
        UserSameHashCodeWithComparable userSameHashCodeWithComparable_5_name2 = new UserSameHashCodeWithComparable(5, "name5_2");

        UserSameHashCodeWithComparable nullPropertyValue = new UserSameHashCodeWithComparable(null, "name2");

        UserSameHashCodeWithComparable nullObject = null;
        //------

        //null  相同 留一个

        Set<UserSameHashCodeWithComparable> set = new TreeSet<>(new PropertyComparator<>("id"));
        set.add(userSameHashCodeWithComparable_5_name5);
        set.add(userSameHashCodeWithComparable_5_name6);

        set.add(nullPropertyValue);
        set.add(nullObject);

        set.add(userSameHashCodeWithComparable_1_name1);
        set.add(userSameHashCodeWithComparable_5_name1);
        set.add(userSameHashCodeWithComparable_5_name2);

        set.add(nullObject);
        set.add(nullPropertyValue);

        //----------------------------------------------------------

        assertEquals(7, set.size());
        assertThat(
                        set,
                        allOf(
                                        contains(
                                                        nullPropertyValue, //如果对应的属性值是null,排在最前面
                                                        userSameHashCodeWithComparable_1_name1, //指定属性的属性值越小对应的对象排在前面,反之排在后面

                                                        //如果对象实现了 Comparable 接口, 那么直接比较
                                                        userSameHashCodeWithComparable_5_name1,
                                                        userSameHashCodeWithComparable_5_name2,
                                                        userSameHashCodeWithComparable_5_name5,
                                                        userSameHashCodeWithComparable_5_name6,

                                                        nullObject)//空元素排在后面
                        ));
    }

    @Test
    public void testPropertyComparatorWithTreeSet(){
        UserSameHashCode userSameHashCode_1_name1 = new UserSameHashCode(1, "name1");
        UserSameHashCode userSameHashCode_5_name5 = new UserSameHashCode(5, "name5_5");
        UserSameHashCode userSameHashCode_5_name6 = new UserSameHashCode(5, "name5_6");
        UserSameHashCode userSameHashCode_5_name1 = new UserSameHashCode(5, "name5_1");
        UserSameHashCode userSameHashCode_5_name2 = new UserSameHashCode(5, "name5_2");

        UserSameHashCode nullPropertyValue = new UserSameHashCode(null, "name2");

        UserSameHashCode nullObject = null;
        //------

        //null  相同 留一个

        Set<UserSameHashCode> set = new TreeSet<>(new PropertyComparator<>("id"));
        set.add(userSameHashCode_5_name5);
        set.add(userSameHashCode_5_name6);

        set.add(nullPropertyValue);
        set.add(nullObject);

        set.add(userSameHashCode_1_name1);
        set.add(userSameHashCode_5_name1);
        set.add(userSameHashCode_5_name2);

        set.add(nullObject);
        set.add(nullPropertyValue);

        //----------------------------------------------------------

        assertEquals(4, set.size());
        assertThat(
                        set,
                        allOf(
                                        contains(
                                                        nullPropertyValue, //如果对应的属性值是null,排在最前面
                                                        userSameHashCode_1_name1, //指定属性的属性值越小对应的对象排在前面,反之排在后面

                                                        //如果两个值相等,那么比较对象本身
                                                        userSameHashCode_5_name5,

                                                        nullObject)//空元素排在后面
                        ));
    }

    //----------------------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testPropertyComparatorNullPropertyName(){
        new PropertyComparator<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyComparatorEmptyPropertyName(){
        new PropertyComparator<>("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPropertyComparatorBlankPropertyName(){
        new PropertyComparator<>("    ");
    }

}