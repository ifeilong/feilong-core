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
package com.feilong.tools.formatter;

import java.util.Date;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.NumberUtil;
import com.feilong.store.order.OrderLine;
import com.feilong.tools.formatter.entity.BeanFormatterConfig;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.formatDuration;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SimpleBeanFormattterTest2{

    /** The Constant log. */
    private static final Logger            LOGGER              = LoggerFactory.getLogger(SimpleBeanFormattterTest2.class);

    private static Iterable<OrderLine>     ITERABLE_DATA       = null;

    private BeanFormatterConfig<OrderLine> beanFormatterConfig = null;

    //********************************************************************
    @Test
    public void testFormatToSimpleTable2(){
    }

    @Test
    public void testFormatToSimpleTable3(){
        beanFormatterConfig = new BeanFormatterConfig<>(OrderLine.class);
    }

    @Test
    public void testFormatToSimpleTable5(){
        beanFormatterConfig = new BeanFormatterConfig<>(OrderLine.class);
        beanFormatterConfig.setIncludePropertyNames("id", "orderId", "MSRP", "salePrice", "discountPrice", "count", "subtotal");
    }

    @Test
    public void testFormatToSimpleTable6(){
        beanFormatterConfig = new BeanFormatterConfig<>(OrderLine.class);
        //beanFormatterConfig.setIncludePropertyNames("id", "orderId", "MSRP", "salePrice", "discountPrice", "count", "subtotal");
        beanFormatterConfig.setSorts("id", "orderId");
    }

    @Test
    public void testFormatToSimpleTable7(){
        beanFormatterConfig = new BeanFormatterConfig<>(OrderLine.class);
        beanFormatterConfig.setIncludePropertyNames("id", "orderId", "MSRP", "salePrice", "discountPrice", "count", "subtotal");
        beanFormatterConfig.setSorts("id", "orderId");
    }

    //********************************************************************
    @BeforeClass
    public static void beforeClass(){
        OrderLine orderLine1 = new OrderLine();
        orderLine1.setId(10450L);
        orderLine1.setOrderId(888L);
        orderLine1.setItemId(65L);
        orderLine1.setSkuId(745L);

        orderLine1.setMSRP(toBigDecimal(1000));
        orderLine1.setSalePrice(toBigDecimal(800));
        orderLine1.setCount(5);
        orderLine1.setDiscountPrice(toBigDecimal(100));
        orderLine1.setSubtotal(
                        NumberUtil.getMultiplyValue(orderLine1.getSalePrice(), orderLine1.getCount(), 2)
                                        .subtract(orderLine1.getDiscountPrice()));

        //-----------------------------------------------------------------
        OrderLine orderLine2 = new OrderLine();
        orderLine2.setId(12356L);
        orderLine2.setOrderId(888L);
        orderLine2.setItemId(65L);
        orderLine2.setSkuId(745L);

        orderLine2.setMSRP(toBigDecimal(1000));
        orderLine2.setSalePrice(toBigDecimal(800));
        orderLine2.setCount(5);
        orderLine2.setDiscountPrice(toBigDecimal(100));
        orderLine2.setSubtotal(
                        NumberUtil.getMultiplyValue(orderLine2.getSalePrice(), orderLine2.getCount(), 2)
                                        .subtract(orderLine2.getDiscountPrice()));

        ITERABLE_DATA = toList(orderLine1, orderLine2);
    }

    @After
    public void after(){
        Date beginDate = new Date();
        LOGGER.debug(FormatterUtil.formatToSimpleTable(ITERABLE_DATA, beanFormatterConfig));
        LOGGER.info("use time:{}", formatDuration(beginDate));
    }
}
