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
package com.feilong.tools.jsonlib;

import com.feilong.core.CharsetType;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

/**
 * xml功能
 * 
 * @author feilong
 * @version 1.4.0 2015年8月23日 上午12:05:11
 * @since 1.4.0
 */
public class JsonlibXmlUtil{

    // [start]objectToXML

    /**
     * 把json串、数组、集合(collection map)、实体Bean转换成XML.
     * 
     * <p>
     * {@link <a href="http://json-lib.sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html">XMLSerializer API</a>}:
     * {@link <a href="http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html">具体实例请参考</a>}
     * </p>
     * 
     * <p>
     * 需要xom jar
     * </p>
     * 
     * <pre class="code">
     * {@code
     * <groupId>xom</groupId> 
     * <artifactId>xom</artifactId>
     * }
     * </pre>
     * 
     * <p>
     * 缺点:
     * </p>
     * <ul>
     * <li>不能去掉 {@code <?xml version="1.0" encoding="UTF-8"? >}</li>
     * <li>不能格式化输出</li>
     * <li>对于空元素,不能输出 {@code <additionalData></additionalData>} ,只能输出 {@code <additionalData/>}</li>
     * </ul>
     * 
     * @param object
     *            the object
     * @return xml
     * @see #objectToXML(Object, String)
     */
    public static String objectToXML(Object object){
        return objectToXML(object, null);
    }

    /**
     * Object to xml.
     * 
     * <p>
     * 缺点:
     * </p>
     * <ul>
     * <li>不能去掉 {@code <?xml version="1.0" encoding="UTF-8"? >}</li>
     * <li>不能格式化输出</li>
     * <li>对于空元素,不能输出 {@code <additionalData></additionalData>} ,只能输出 {@code <additionalData/>}</li>
     * </ul>
     * 
     * <p>
     * 需要xom jar
     * </p>
     * 
     * <pre class="code">
     * {@code
     * <groupId>xom</groupId> 
     * <artifactId>xom</artifactId>
     * }
     * </pre>
     * 
     * @param object
     *            the object
     * @param encoding
     *            the encoding ,you can use {@link CharsetType}
     * @return the string
     * @see JsonUtil#toJSON(Object)
     * @see net.sf.json.xml.XMLSerializer
     * @see net.sf.json.xml.XMLSerializer#write(JSON)
     * @see net.sf.json.xml.XMLSerializer#write(JSON, String)
     */
    public static String objectToXML(Object object,String encoding){
        JSON json = JsonUtil.toJSON(object);
        XMLSerializer xmlSerializer = new XMLSerializer();
        // xmlSerializer.setRootName("outputPaymentPGW");
        // xmlSerializer.setTypeHintsCompatibility(true);
        // xmlSerializer.setSkipWhitespace(false);
        // xmlSerializer.setTypeHintsEnabled(true);//是否保留元素类型标识,默认true
        // xmlSerializer.setElementName("e");//设置元素标签,默认e
        // xmlSerializer.setArrayName("a");//设置数组标签,默认a
        // xmlSerializer.setObjectName("o");//设置对象标签,默认o

        return xmlSerializer.write(json, encoding);
    }

    // [end]

    // [start]xmlToJSON

    /**
     * XML转成json串.
     * 
     * <p>
     * 需要xom jar
     * </p>
     * 
     * <pre class="code">
     * {@code
     * <groupId>xom</groupId> 
     * <artifactId>xom</artifactId>
     * }
     * </pre>
     * 
     * @param xml
     *            the xml
     * @return String
     * @see net.sf.json.xml.XMLSerializer#read(String)
     */
    public static JSON xmlToJSON(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml);
    }
}
