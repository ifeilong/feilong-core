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

import org.apache.commons.lang3.StringUtils;

/**
 * 判断字符串是否包含指定的 searchCharSequence.
 * 
 * <h3>使用场景:</h3>
 * 
 * <blockquote>
 * <p>
 * XML 中:
 * </p>
 * 
 * <pre class="code">
{@code          
<bean class="com.feilong.context.converter.IfStringToBeanConverter">

    <property name="predicate">
        <bean class="com.feilong.core.util.predicate.ContainsStringPredicate" p:searchCharSequence=
"TRADE_NOT_EXIST" />
    </property>

    <property name="trueStringToBeanConverter">
        <bean class="com.feilong.netpay.alipay.query.AlipayTradeNotExistStringToBeanConverter" />
    </property>

    <property name="falseStringToBeanConverter">
        <bean class="com.feilong.context.converter.XMLMapBuilderStringToBeanConverter">
            <property name="beanClass" value="com.feilong.netpay.alipay.query.AlipaySingleQueryResultCommand" />

            <property name="nameAndValueMapBuilder">
                <bean class="com.feilong.context.converter.builder.XmlNodeNameAndValueMapBuilder" p:xpathExpression=
"/alipay/response/trade/*" />
            </property>

            <property name="beanBuilder">
                <bean class="com.feilong.context.converter.builder.AliasBeanBuilder" />
            </property>
        </bean>
    </property>
</bean>
}
 * </pre>
 * 
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * 
 * @since 1.14.3
 */
public class ContainsStringPredicate implements StringPredicate{

    /** The search char sequence. */
    private CharSequence searchCharSequence;

    //---------------------------------------------------------------

    /**
     * Instantiates a new contains string predicate.
     */
    public ContainsStringPredicate(){
        super();
    }

    /**
     * Instantiates a new contains string predicate.
     *
     * @param searchCharSequence
     *            the search char sequence
     */
    public ContainsStringPredicate(CharSequence searchCharSequence){
        super();
        this.searchCharSequence = searchCharSequence;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.collections4.Predicate#evaluate(java.lang.Object)
     */
    @Override
    public boolean evaluate(String value){
        return StringUtils.contains(value, searchCharSequence);
    }

    //---------------------------------------------------------------
    /**
     * Gets the search char sequence.
     *
     * @return the searchCharSequence
     */
    public CharSequence getSearchCharSequence(){
        return searchCharSequence;
    }

    /**
     * Sets the search char sequence.
     *
     * @param searchCharSequence
     *            the searchCharSequence to set
     */
    public void setSearchCharSequence(CharSequence searchCharSequence){
        this.searchCharSequence = searchCharSequence;
    }

}
