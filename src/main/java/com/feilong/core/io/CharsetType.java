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
package com.feilong.core.io;

/**
 * 常用编码的枚举.
 * <p>
 * 建议不要自己硬编码,直接调用这里预声明的常量.
 * </p>
 * 
 * @author feilong
 * @version 1.0 2012-4-5 下午4:51:08
 * @version 1.0.5 2014-5-4 14:35 改成interface
 * @see org.apache.commons.lang3.CharEncoding
 * @see org.apache.commons.lang3.CharEncoding#isSupported(String)
 * @see java.nio.charset.Charset#availableCharsets()
 * @see java.nio.charset.Charset#isSupported(String)
 * @see java.nio.charset.Charset#defaultCharset()
 * @since 1.0.0
 */
public final class CharsetType{

    /** GBK可以表示简体中文和繁体中文 <code>{@value}</code>. */
    public static final String GBK        = "GBK";

    /** GB2312只能表示简体中文 <code>{@value}</code>. */
    public static final String GB2312     = "GB2312";

    /** <code>{@value}</code>. */
    public static final String GB18030    = "GB18030";

    // *********************************************************************************************
    /** <code>{@value}</code>. */
    public static final String UTF8       = "UTF-8";

    /**
     * <code>{@value}</code>
     * <p>
     * ISO Latin Alphabet #1, also known as ISO-LATIN-1.<br>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     * 　ISO/IEC 8859-1，又称Latin-1或“西欧语言”，是国际标准化组织内ISO/IEC 8859的第一个8位字符集.<br>
     * 它以ASCII为基础，在空置的0xA0-0xFF的范围内，加入192个字母及符号，藉以供使用变音符号的拉丁字母语言使用.<br>
     * 此字符集支援部分于欧洲使用的语言，包括阿尔巴尼亚语、巴斯克语、布列塔尼语、加泰罗尼亚语、丹麦语、荷兰语、法罗语、弗里西语、加利西亚语、德语、格陵兰语、冰岛语、爱尔兰盖尔语、意大利语、拉丁语、卢森堡语、挪威语、葡萄牙语、里托罗曼斯语、苏格兰盖尔语、西班牙语及瑞典语.
     * 　<br>
     * 　英语虽然没有重音字母，但仍会标明为ISO 8859-1编码.除此之外，欧洲以外的部分语言，如南非荷兰语、斯瓦希里语、印尼语及马来语、菲律宾他加洛语等也可使用ISO 8859-1编码. 　　<br>
     * 法语及芬兰语本来也使用ISO 8859-1来表示.但因它没有法语使用的 œ、Œ、 Ÿ 三个字母及芬兰语使用的 Š、š、Ž、ž ，故于1998年被ISO/IEC 8859-15所取代.（ISO 8859-15同时加入了欧元符号）
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /** Don't let anyone instantiate this class. */
    private CharsetType(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}