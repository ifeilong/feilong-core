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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.bean.BeanOperationException;
import com.feilong.core.bean.BeanUtil;
import com.feilong.store.order.OrderLine;

/**
 * The Class BeanUtilCloneBeanTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CloneBeanTest{

    /**
     * Test clone bean.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testCloneBean(){
        OrderLine orderLine = new OrderLine();
        orderLine.setCount(8);
        orderLine.setSalePrice(toBigDecimal(599));

        OrderLine cloneOrderLine = BeanUtil.cloneBean(orderLine);
        assertThat(cloneOrderLine, allOf(hasProperty("count", is(8)), hasProperty("salePrice", is(toBigDecimal(599)))));
    }

    /**
     * Test clone bean list.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testCloneBeanList(){
        OrderLine orderLine = new OrderLine();
        orderLine.setCount(8);
        orderLine.setSalePrice(toBigDecimal(599));

        List<OrderLine> list = toList(orderLine);

        List<OrderLine> cloneList = BeanUtil.cloneBean(list);
        assertEquals(emptyList(), cloneList);
    }

    //---------------------------------------------------------------

    /**
     * Test clone bean null bean.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testCloneBeanNullBean(){
        BeanUtil.cloneBean(null);
    }

    /**
     * Test clone bean no default constructor bean.
     */
    @Test(expected = BeanOperationException.class)
    public void testCloneBeanNoDefaultConstructorBean(){
        NoDefaultConstructorBean noDefaultConstructorBean = new NoDefaultConstructorBean("feilong");
        BeanUtil.cloneBean(noDefaultConstructorBean);
    }

    /**
     * 没有默认构造函数的bean.
     *
     * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
     * @since 1.9.0
     */
    public class NoDefaultConstructorBean{

        /**
         * Instantiates a new no default constructor bean.
         *
         * @param name
         *            the name
         */
        public NoDefaultConstructorBean(String name){
        }
    }

}
