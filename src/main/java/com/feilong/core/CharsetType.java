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
package com.feilong.core;

import java.nio.charset.StandardCharsets;

/**
 * 常用字符编码.
 * 
 * <p>
 * 建议不要自己硬编码,直接调用这里预声明的常量.
 * </p>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.CharEncoding
 * @see org.apache.commons.lang3.CharEncoding#isSupported(String)
 * @see java.nio.charset.Charset#availableCharsets()
 * @see java.nio.charset.Charset#isSupported(String)
 * @see java.nio.charset.Charset#defaultCharset()
 * @since 1.4.0
 */
public final class CharsetType{

    /**
     * <code>UTF-8</code>,Eight-bit (Unicode Transformation Format).
     *
     * <p>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     * 
     * <h3>Unicode编码</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 正如前面前面所提到的一样,世界存在这么多国家,也存在着多种编码风格,像中文的GB232、GBK、GB18030,这样乱搞一套,虽然在本地运行没有问题,但是一旦出现在网络上,由于互不兼容,访问则会出现乱码.
     * </p>
     * 
     * <p>
     * 为了解决这个问题,伟大的Unicode编码腾空出世.
     * Unicode编码的作用就是能够使计算机实现夸平台、跨语言的文本转换和处理.它几乎包含了世界上所有的符号,并且每个符号都是独一无二的.在它的编码世界里,每一个数字代表一个符号,每一个符号代表了一个数字,不存在二义性.
     * </p>
     * 
     * <p>
     * Unicode编码又称统一码、万国码、单一码,它是业界的一种标准,是为了解决传统的字符编码方案的局限而产生的,它为每种语言中的每个字符设定了统一并且唯一的二进制编码,以满足跨语言、跨平台进行文本转换、处理的要求.<br>
     * 同时Unicode是字符集,它存在很多几种实现方式如: UTF-8、UTF-16.
     * </p>
     * </blockquote>
     * 
     * <h3>UTF-8:</h3>
     * 
     * <blockquote>
     * <p>
     * 互联网的普及,强烈要求出现一种统一的编码方式.UTF-8就是在互联网上使用最广的一种unicode的实现方式.其他实现方式还包括UTF-16和UTF-32,不过在互联网上基本不用.<br>
     * <span style="color:red">重复一遍:UTF-8是Unicode的实现方式之一.</span> <br>
     * UTF-8最大的一个特点,就是它是一种变长的编码方式.它可以使用1~4个字节表示一个符号,根据不同的符号而变化字节长度. 
     * </p>
     * 
     * UTF-8的编码规则很简单,只有两条: 
     * 
     * <ul>
     * <li>1)对于单字节的符号,字节的第一位设为0,后面7位为这个符号的unicode码.因此对于英语字母,UTF-8编码和ASCII码是相同的. </li>
     * <li>2)对于n字节的符号({@code n>1}),第一个字节的前n位都设为1,第n+1位设为0,后面字节的前两位一律设为10.剩下的没有提及的二进制位,全部为这个符号的unicode码.</li>
     * </ul>
     * </blockquote>
     * 
     * @see org.apache.commons.lang3.CharEncoding#UTF_8
     * @see java.nio.charset.StandardCharsets#UTF_8
     */
    public static final String UTF8       = StandardCharsets.UTF_8.name();

    //---------------------------------------------------------------

    /**
     * <code>{@value}</code>,<span style="color:red">只能表示简体中文</span> .
     * 
     * <p style="color:red">
     * if you want to use {@link #GB2312},may be use {@link #GB18030} is better
     * </p>
     * 
     * <p>
     * ASCII码的提出,有效的解决了西文文字的信息化问题,但对于汉字字符却完全不适用。<br>
     * 为了满足国内在计算机中使用汉字的需要,中国国家标准总局发布了一系列的汉字字符集国家标准编码,统称为<b>GB码</b>,或<b>国标码</b>。<br>
     * 其中最有影响的是于1980年发布的《信息交换用汉字编码字符集 基本集》,标准号为GB2312-1980,因其使用非常普遍,也常被通称为国标码。<br>
     * </p>
     * 
     * <p>
     * 共收录6763个汉字,其中一级汉字3755个,二级汉字3008个,还收录了拉丁字母、希腊字母、日文等682个全角字符.<br>
     * 由于GB2312的出现,它基本上解决了我们日常的需要,它所收录的汉子已经覆盖了中国大陆99.75%的使用平率. <br>
     * 但是我国文化博大精深,对于人名、古汉语等方面出现的罕用字,GB2312还是不能处理,于是后面的GBK和GB18030汉字字符集出现了.
     * </p>
     */
    public static final String GB2312     = "GB2312";

    /**
     * <code>{@value}</code>(Chinese Internal Code Specification),全称《汉字内码扩展规范》,<span style="color:green">1995年12月1日制订</span>
     * ,K为扩展的汉语拼音中"扩"字的声母.<span style="color:green">可以表示简体中文和繁体中文</span>.
     * 
     * <p style="color:red">
     * if you want to use {@link #GBK},may be use {@link #GB18030} is better
     * </p>
     * 
     * <p>
     * GBK编码标准兼容GB2312,共收录<span style="color:green">汉字21003个、符号883个,并提供1894个造字码位</span>,简、繁体字融于一库.
     * </p>
     */
    public static final String GBK        = "GBK";

    /**
     * <code>{@value}</code> 国家标准GB18030-2000《信息交换用汉字编码字符集基本集的扩充》 .
     * 
     * <p>
     * 是继GB2312-1980和GB13000-1993之后最重要的汉字编码标准,是我国计算机系统必须遵循的基础性标准之一.<br>
     * 目前,GB18030有两个版本:GB18030-2000和GB18030-2005.
     * </p>
     * 
     * <p>
     * 其中GB18030-2000仅规定了常用<span style="color:green">非汉字符号和27533个汉字(包括部首、部件等)的编码</span>,<br>
     * 而GB18030-2005是全文强制性标准,市场上销售的产品必须符合,是<span style="color:green">GB18030-2000的基础上增加了42711个汉字和多种我国少数民族文字的编码</span>.
     * </p>
     * 
     * <p>
     * <span style="color:red">GB18030-2000是GBK的取代版本</span> ,它的主要特点是在GBK基础上增加了CJK统一汉字扩充A的汉字.<br>
     * GB18030-2005的主要特点是在GB18030-2000基础上增加了CJK统一汉字扩充B的汉字.
     * </p>
     * 
     * <h3>GB18030主要有以下特点:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>与UTF-8相同,采用多字节编码,每个字可以由1个、2个或4个字节组成.</li>
     * <li>编码空间庞大,最多可定义161万个字符.</li>
     * <li>支持中国国内少数民族的文字,不需要动用造字区.</li>
     * <li>汉字收录范围包含繁体汉字以及日韩汉字</li>
     * </ul>
     * </blockquote>
     */
    public static final String GB18030    = "GB18030";

    //---------------------------------------------------------------

    /**
     * <code>ISO-8859-1</code>,ISO/IEC 8859-1,又称Latin-1或"西欧语言",是国际标准化组织内ISO/IEC 8859的第一个8位字符集.
     * 
     * <p>
     * ISO Latin Alphabet #1, also known as ISO-LATIN-1.<br>
     * Every implementation of the Java platform is required to support this character encoding.
     * </p>
     * 
     * <p>
     * 它以ASCII为基础,在空置的0xA0-0xFF的范围内,加入192个字母及符号,藉以供使用变音符号的拉丁字母语言使用.<br>
     * 此字符集支援部分于欧洲使用的语言,包括阿尔巴尼亚语、巴斯克语、布列塔尼语、加泰罗尼亚语、丹麦语、荷兰语、法罗语、弗里西语、加利西亚语、德语、格陵兰语、冰岛语、爱尔兰盖尔语、意大利语、拉丁语、卢森堡语、挪威语、葡萄牙语、里托罗曼斯语、苏格兰盖尔语、西班牙语及瑞典语.
     * </p>
     * 
     * <p>
     * 英语虽然没有重音字母,但仍会标明为ISO 8859-1编码.<br>
     * 除此之外,欧洲以外的部分语言,如南非荷兰语、斯瓦希里语、印尼语及马来语、菲律宾他加洛语等也可使用ISO 8859-1编码<br>
     * 法语及芬兰语本来也使用ISO 8859-1来表示.但因它没有法语使用的 œ、Œ、 Ÿ 三个字母及芬兰语使用的 Š、š、Ž、ž ,故<span style="color:red">于1998年被ISO/IEC 8859-15所取代</span>.(ISO
     * 8859-15同时加入了欧元符号)
     * </p>
     * 
     * @see org.apache.commons.lang3.CharEncoding#ISO_8859_1
     * @see java.nio.charset.StandardCharsets#ISO_8859_1
     */
    public static final String ISO_8859_1 = StandardCharsets.ISO_8859_1.name();

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private CharsetType(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}