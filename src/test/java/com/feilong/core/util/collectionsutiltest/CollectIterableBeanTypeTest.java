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
package com.feilong.core.util.collectionsutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.store.member.Customer;
import com.feilong.store.member.User;

/**
 * The Class CollectIterableBeanTypeTest.
 */
public class CollectIterableBeanTypeTest{

    /**
     * Test collect3.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testCollect3(){
        List<User> list = toList(//
                        new User(23L, "张飞"),
                        new User(24L, "关羽"),
                        new User(25L, "刘备"));
        //
        //        List<Customer> customerList = newArrayList();
        //        for (User user : list){
        //            Customer customer = new Customer();
        //            customer.setId(user.getId());
        //            customer.setName(user.getName());
        //            customerList.add(customer);
        //        }

        List<Customer> customerList = CollectionsUtil.collect(list, Customer.class);

        assertThat(customerList.get(0), allOf(hasProperty("id", is(23L)), hasProperty("name", is("张飞"))));
        assertThat(customerList.get(1), allOf(hasProperty("id", is(24L)), hasProperty("name", is("关羽"))));
        assertThat(customerList.get(2), allOf(hasProperty("id", is(25L)), hasProperty("name", is("刘备"))));
    }

    /**
     * Test collect with null element.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testCollectWithNullElement(){
        List<User> list = toList(//
                        new User(23L, "张飞"),
                        null);

        List<Customer> customerList = CollectionsUtil.collect(list, Customer.class);
        assertThat(customerList.get(0), allOf(hasProperty("id", is(23L)), hasProperty("name", is("张飞"))));
        assertThat(customerList.get(1), is((Customer) null));
    }

    /**
     * Test collect with property name.
     */
    @SuppressWarnings("static-method")
    //---------
    @Test
    public void testCollectWithPropertyName(){
        List<User> list = toList(//
                        new User(23L, "张飞"));

        List<Customer> customerList = CollectionsUtil.collect(list, Customer.class, "name");
        assertThat(customerList.get(0), allOf(hasProperty("id", not(23L)), hasProperty("name", is("张飞"))));
    }

    /**
     * Test collect with property name no from name.
     */
    @SuppressWarnings("static-method")
    //---------
    @Test(expected = BeanOperationException.class)
    public void testCollectWithPropertyNameNoFromName(){
        List<User> list = toList(//
                        new User(23L, "张飞"));

        CollectionsUtil.collect(list, Customer.class, "addresses");
    }

    /**
     * Test collect with property name no to name.
     */
    @SuppressWarnings("static-method")
    @Test(expected = BeanOperationException.class)
    public void testCollectWithPropertyNameNoToName(){
        List<User> list = toList(//
                        new User(23L, "张飞"));

        CollectionsUtil.collect(list, Customer.class, "age");
    }

    //---------

    /**
     * Test collect null iterable.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testCollectNullIterable(){
        assertEquals(null, CollectionsUtil.collect((List<Long>) null, User.class));
    }

    /**
     * Test collect empty iterable.
     */
    @SuppressWarnings("static-method")
    @Test
    public void testCollectEmptyIterable(){
        assertEquals(emptyList(), CollectionsUtil.collect(new ArrayList<Long>(), User.class));
    }

    //********

    /**
     * Test collect null output list bean type.
     */
    @SuppressWarnings("static-method")
    @Test(expected = NullPointerException.class)
    public void testCollectNullOutputListBeanType(){
        CollectionsUtil.collect(new ArrayList<Long>(), (Class) null);
    }

}
