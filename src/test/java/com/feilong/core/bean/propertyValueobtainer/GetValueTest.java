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
package com.feilong.core.bean.propertyValueobtainer;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import com.feilong.core.bean.PropertyValueObtainer;
import com.feilong.store.member.Member;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.12.2
 */
public class GetValueTest{

    @Test
    public void testGetValue() throws IntrospectionException{
        Member member = new Member();
        member.setId(1L);

        assertEquals(1L, PropertyValueObtainer.getValue(member, new PropertyDescriptor("id", Member.class)));
    }

    @Test(expected = NullPointerException.class)
    //@Test
    public void testGetValueError() throws IntrospectionException{
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(StoreLocatorErrorProperty.class);

        //---------------------------------------------------------------
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors){

            PropertyValueObtainer.getValue(new StoreLocatorErrorProperty(), propertyDescriptor);
        }

    }
    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testGetValueNullObj(){
        PropertyValueObtainer.getValue(new Member(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testGetValueNullPropertyDescriptor() throws IntrospectionException{
        PropertyValueObtainer.getValue(null, new PropertyDescriptor("id", Member.class));
    }
}
