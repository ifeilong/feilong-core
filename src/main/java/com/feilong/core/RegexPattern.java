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

import java.util.regex.Pattern;

/**
 * 常用的正则表达式模式.
 * 
 * <h3>开始结束</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>\</td>
 * <td>将下一个字符标记为一个特殊字符、或一个原义字符、或一个 向后引用、或一个八进制转义符.<br>
 * 例如,'n' 匹配字符 "n".'\n' 匹配一个换行符.序列 '\\' 匹配 "\" 而 "\(" 则匹配 "(".</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>^</td>
 * <td>匹配输入字符串的开始位置.如果设置了 RegExp 对象的 Multiline 属性,^ 也匹配 '\n' 或 '\r' 之后的位置.</td>
 * </tr>
 * <tr valign="top">
 * <td>$</td>
 * <td>匹配输入字符串的结束位置.如果设置了RegExp 对象的 Multiline 属性,$ 也匹配 '\n' 或 '\r' 之前的位置.</td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>次数</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>*</td>
 * <td>匹配前面的子表达式 <b>零次</b> 或 <b>多次</b>.<br>
 * 例如,zo* 能匹配 "z" 以及 "zoo".* 等价于{0,}.</td>
 * </tr>
 * <tr valign="top">
 * <td>+</td>
 * <td>匹配前面的子表达式 <b>一次</b> 或 <b>多次</b>.<br>
 * 例如,'zo+' 能匹配 "zo" 以及 "zoo",但不能匹配 "z".+ 等价于 {1,}.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>?</td>
 * <td>匹配前面的子表达式<b> 零次</b> 或 <b>一次</b>.<br>
 * 例如,"do(es)?" 可以匹配 "do" 或 "does" 中的"do" .? 等价于 {0,1}.</td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>简写</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\d</td>
 * <td>匹配一个数字字符.等价于 [0-9].</td>
 * </tr>
 * <tr valign="top">
 * <td>\D</td>
 * <td>匹配一个非数字字符.等价于 [^0-9].</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\s</td>
 * <td>匹配任何空白字符,包括空格、制表符、换页符等等.等价于 [ \f\n\r\t\v].</td>
 * </tr>
 * <tr valign="top">
 * <td>\S</td>
 * <td>匹配任何非空白字符.等价于 [^ \f\n\r\t\v].</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\w</td>
 * <td>匹配包括下划线的任何单词字符.等价于'[A-Za-z0-9_]'.</td>
 * </tr>
 * <tr valign="top">
 * <td>\W</td>
 * <td>匹配任何非单词字符.等价于 '[^A-Za-z0-9_]'.</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>特殊</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>\n</td>
 * <td>匹配一个换行符.等价于 \x0a 和 \cJ.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\r</td>
 * <td>匹配一个回车符.等价于 \x0d 和 \cM.</td>
 * </tr>
 * <tr valign="top">
 * <td>\t</td>
 * <td>匹配一个制表符.等价于 \x09 和 \cI.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\v</td>
 * <td>匹配一个垂直制表符.等价于 \x0b 和 \cK.</td>
 * </tr>
 * <tr valign="top">
 * <td>\f</td>
 * <td>匹配一个换页符.等价于 \x0c 和 \cL.</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>全部符号解释</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{n}</td>
 * <td>n 是一个非负整数.匹配确定的 n 次.例如,'o{2}' 不能匹配 "Bob" 中的 'o',但是能匹配 "food" 中的两个 o.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{n,}</td>
 * <td>n 是一个非负整数.至少匹配n 次.例如,'o{2,}' 不能匹配 "Bob" 中的 'o',但能匹配 "foooood" 中的所有 o.'o{1,}' 等价于 'o+'.'o{0,}' 则等价于 'o*'.</td>
 * </tr>
 * <tr valign="top">
 * <td>{n,m}</td>
 * <td>m 和 n 均为非负整数,其中 {@code n <= m}.最少匹配 n 次且最多匹配 m 次.例如,"o{1,3}" 将匹配 "fooooood" 中的前三个 o.'o{0,1}' 等价于 'o?'.请注意在逗号和两个数之间不能有空格.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>?</td>
 * <td>当该字符紧跟在任何一个其他限制符 (*, +, ?, {n}, {n,}, {n,m}) 后面时,匹配模式是非贪婪的.非贪婪模式尽可能少的匹配所搜索的字符串,而默认的贪婪模式则尽可能多的匹配所搜索的字符串.例如,对于字符串 "oooo",'o+?' 将匹配单个
 * "o",而 'o+' 将匹配所有 'o'.</td>
 * </tr>
 * <tr valign="top">
 * <td>.</td>
 * <td>匹配除 "\n" 之外的任何单个字符.要匹配包括 '\n' 在内的任何字符,请使用象 '[.\n]' 的模式.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>(pattern)</td>
 * <td>匹配 pattern 并获取这一匹配.所获取的匹配可以从产生的 Matches 集合得到,在VBScript 中使用 SubMatches 集合,在JScript 中则使用 $0…$9 属性.要匹配圆括号字符,请使用 '\(' 或 '\)'.</td>
 * </tr>
 * <tr valign="top">
 * <td>(?:pattern)</td>
 * <td>匹配 pattern 但不获取匹配结果,也就是说这是一个非获取匹配,不进行存储供以后使用.这在使用 "或" 字符 (|) 来组合一个模式的各个部分是很有用.例如, 'industr(?:y|ies) 就是一个比 'industry|industries'
 * 更简略的表达式.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>(?=pattern)</td>
 * <td>正向预查,在任何匹配 pattern 的字符串开始处匹配查找字符串.这是一个非获取匹配,也就是说,该匹配不需要获取供以后使用.例如,'Windows (?=95|98|NT|2000)' 能匹配 "Windows 2000" 中的 "Windows" ,但不能匹配
 * "Windows 3.1" 中的 "Windows".预查不消耗字符,也就是说,在一个匹配发生后,在最后一次匹配之后立即开始下一次匹配的搜索,而不是从包含预查的字符之后开始.</td>
 * </tr>
 * <tr valign="top">
 * <td>(?!pattern)</td>
 * <td>负向预查,在任何不匹配 pattern 的字符串开始处匹配查找字符串.这是一个非获取匹配,也就是说,该匹配不需要获取供以后使用.例如'Windows (?!95|98|NT|2000)' 能匹配 "Windows 3.1" 中的 "Windows",但不能匹配
 * "Windows 2000" 中的 "Windows".预查不消耗字符,也就是说,在一个匹配发生后,在最后一次匹配之后立即开始下一次匹配的搜索,而不是从包含预查的字符之后开始</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>x|y</td>
 * <td>匹配 x 或 y.例如,'z|food' 能匹配 "z" 或 "food".'(z|f)ood' 则匹配 "zood" 或 "food".</td>
 * </tr>
 * <tr valign="top">
 * <td>[xyz]</td>
 * <td>字符集合.匹配所包含的任意一个字符.例如, '[abc]' 可以匹配 "plain" 中的 'a'.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>[^xyz]</td>
 * <td>负值字符集合.匹配未包含的任意字符.例如, '[^abc]' 可以匹配 "plain" 中的'p'.</td>
 * </tr>
 * <tr valign="top">
 * <td>[a-z]</td>
 * <td>字符范围.匹配指定范围内的任意字符.例如,'[a-z]' 可以匹配 'a' 到 'z' 范围内的任意小写字母字符.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>[^a-z]</td>
 * <td>负值字符范围.匹配任何不在指定范围内的任意字符.例如,'[^a-z]' 可以匹配任何不在 'a' 到 'z' 范围内的任意字符.</td>
 * </tr>
 * <tr valign="top">
 * <td>\b</td>
 * <td>匹配一个单词边界,也就是指单词和空格间的位置.例如, 'er\b' 可以匹配"never" 中的 'er',但不能匹配 "verb" 中的 'er'.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>\B</td>
 * <td>匹配非单词边界.'er\B' 能匹配 "verb" 中的 'er',但不能匹配 "never" 中的 'er'.</td>
 * </tr>
 * <tr valign="top">
 * <td>\cx</td>
 * <td>匹配由 x 指明的控制字符.例如, \cM 匹配一个 Control-M 或回车符.x 的值必须为 A-Z 或 a-z 之一.否则,将 c 视为一个原义的 'c' 字符.</td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * <pre class="code">
 * {@code
 * 1.字符类是可选自符的集合,用‘[’封装,比如[Jj],[0-9],[A-Za-z]或[^0-9].这里的-表示范围(Unicode落在两个边界之间的所有字符),^表示求补(指定字符外的所有字符 
 * 2.有许多预定以的字符类,像\d(数字)或\p&#123;Sc&#125;(Unicode货币符号),见表12-8和12-9. 
 * 3大多数字符与它们自身匹配,像上例中的java字符. 
 * 4.符号.匹配任何字符(可能行终止符(line terminators)除外,这依赖于标识设置(flag settings)) 
 * 5.\用作转义符,比如\.匹配一个句点,\\匹配一个反斜杠. 
 * 6.^和$分别匹配行头和行尾 
 * 7.如果X和Y都是正则表达式,则XY表示"X的匹配后面跟着Y的匹配".X|Y表示"任何X或Y的匹配" 
 * 8.可以将量词(quantifier)用到表达式中,X+ 表示X重复1次或多次,X* 表示X重复0次或多次,X? 表示X重复0次或1次 
 * 9.默认地,一个量词总是与使总体成功匹配的最长的可能重复匹配.可以加上后缀？(称为reluctant或stingy 匹配,用以匹配最小的重复数),和+(称为possessive或贪婪匹配,用以即使在总体匹配失败的情况下也匹配最大的重复数)来更改这种属性. 
 * }
 * </pre>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see Pattern
 * @since 1.0.0
 */
public final class RegexPattern{

    /**
     * 大陆的电话号码 <code>{@value}</code>.
     * 
     * <p>
     * 支持 (3-4位)区号(option)+(6-8位)直播号码+(1-6位)分机号(option) 的组合
     * </p>
     * 
     * <h3>有效的:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>86771588</li>
     * <li>021-86771588</li>
     * <li>021-867715</li>
     * <li>86771588-888</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * <h3>无效的:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * "",
     * "   ",
     * "02021-86771588-888", //区号3-4位 太长了
     * "020-86771588888", //直播号码6-8位 太长了
     * "021-86775" //直播号码 需要 6-8位
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>关于分机号(extension number):</h3>
     * 
     * <blockquote>
     * <p>
     * 分机号是相对总机号说的,一般公司或企事业单位为了节省外线电话,会购置一台集团电话或电话交换机,外线或叫直线电话接到交换机上,可以设一部总机,外线的电话全部接入总机,通过总机代转,<br>
     * 一般一个电话交换机能接入外线和分出的分机是由交换机的型号决定,各分机可以设1-6位分机号,各分机之间可通过分机号任打电话,接打外线由电话交换机自动调配线路。
     * </p>
     * 
     * <p>
     * 分机号比较理想的位数为4位,第一位可以设置成部门识别号,其他的可以根据需要设置。
     * 千万别用三位的紧急服务号码来做分机号,如果有人用直线电话误操作的话,会带来不必要的麻烦
     * </p>
     * </blockquote>
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Extension_(telephone)">Extension_(telephone)</a>
     * @see <a href="http://regexlib.com/Search.aspx?k=phone+number&c=7&m=5&ps=20">regexlib</a>
     */
    public static final String TELEPHONE               = "^(\\d{3,4}-)?\\d{6,8}(-\\d{1,6})?$";

    /**
     * 大陆的电话号码(必须要有区号) <code>{@value}</code>.
     * 
     * <p>
     * 支持 (3-4位)区号(must)+(6-8位)直播号码+(1-6位)分机号(option) 的组合
     * </p>
     * 
     * <h3>有效的:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>021-86771588</li>
     * <li>021-867715</li>
     * <li>021-86771588-888</li>
     * </ul>
     * 
     * </blockquote>
     * 
     * <h3>无效的:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * "",
     * "   ",
     * "86771588", //没有区号
     * "02021-86771588-888", //区号3-4位 太长了
     * "86771588-888", //没有区号
     * "020-86771588888", //直播号码6-8位 太长了
     * "021-86775" //直播号码 需要 6-8位
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>区号</h3>
     * 
     * <blockquote>
     * <p>
     * 是指世界各大城市所属行政区域常用电话区划号码,这些号码主要用于国内、国际长途电话接入。<br>
     * 比如,中国大陆国际区号86,北京区号010、广州区号020等。<br>
     * 而在使用国内长途电话时,区号前要加拨0。<br>
     * 值得一提的是,人们往往容易混淆概念,误以为区号本身前面有一个0。也就是说,由于0是唯一的国内长途接入码,经常和后面的区号并列使用,所以形成了习惯。<br>
     * 实际上在境外打电话回境内某城市时,当地国际长途电话接入码加在中国国家号86之后,该城市区号前没有0。比如,成都区号应是28,而非028。
     * </p>
     * </blockquote>
     * 
     * @see <a href="http://baike.baidu.com/view/103379.htm">区号</a>
     * @since 1.7.1
     */
    public static final String TELEPHONE_MUST_AREACODE = "^\\d{3,4}-\\d{6,8}(-\\d{1,6})?$";

    /**
     * 手机号码 <code>{@value}</code>.
     * 
     * <pre class="code">
     * 移动:1340-1348、135、136、137、138、139、147、150、151、152、157、158、159、182、183、184、187、188　
     * 联通:130、131、132、145、155、156、185、186
     * 电信:133、1349、153、180、181、189、(1349卫通)
     * </pre>
     * 
     * <h3>182段</h3>
     * 
     * <blockquote>
     * <p>
     * 北京移动正式启用182新号段 昨天,记者从北京移动获悉,从今年1月开始,北京移动启动了182新号段.<br>
     * 其中动感地带号段为18210000000到18210369999,数量为37万,<br>
     * 神州行畅听卡号段为18210370000到18210649999,数量为25万,<br>
     * 神州行家园卡号段为18210650000到18210899999,数量为25万.
     * </p>
     * </blockquote>
     * 
     * <h3><a href="http://www.dvbcn.com/2014/09/23-113699.html">176、177、178段</a></h3>
     * 
     * <blockquote>
     * <p>
     * 家工信部为了鼓励民间资本进去电信行业,专门发放电信拍照.然后第三方电信上商又从三大运营商买的号码 <br>
     * 据了解,按照工信部的批复,三大运营商4G新用户均将使用17开头的号段. <br>
     * 中国电信称177号段为中国电信4G专属号段,并在7月15日向在上海、深圳、南京等16个城市实行177号段放号.<br>
     * 中国移动则在今年已使用178号段并对多个城市进行放号.至此也就意味着三大运营商的4G号段已全面商用. <br>
     * </p>
     * </blockquote>
     * 
     * @see <a href="http://liaojuncai.iteye.com/blog/1986310">严格的手机号码正则表达式写法</a>
     */
    public static final String MOBILEPHONE             = "^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";

    //******************************************************************************

    /** 邮政编码 <code>{@value}</code>. */
    public static final String ZIPCODE                 = "^\\d{6}$";

    //******************************************************************************
    /**
     * 两位数小数 <code>{@value}</code>
     * 
     * <p>
     * 可以是200 也可以是200.00 不可以是 200.0
     * </p>
     */
    public static final String DECIMAL_TWO_DIGIT       = "^[0-9]+(.[0-9]{2})?$";

    /**
     * 纯数字 <code>{@value}</code>.
     *
     * @see java.lang.Character#isDigit(char)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isNumeric(CharSequence)}
     */
    @Deprecated
    public static final String NUMBER                  = "^[0-9]*$";

    //**************************************************************************************
    /**
     * 字母和数字 (alpha numeric) <code>{@value}</code>.
     * 
     * @see java.lang.Character#isLetterOrDigit(int)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isAlphanumeric(CharSequence)}
     */
    @Deprecated
    public static final String AN                      = "^[0-9a-zA-Z]+$";

    /**
     * 字母和数字和空格(alpha numeric space)<code>{@value}</code>.
     * 
     * @see java.lang.Character#isLetterOrDigit(int)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isAlphanumericSpace(CharSequence)}
     */
    @Deprecated
    public static final String ANS                     = "^[0-9a-zA-Z ]+$";

    //******************************************************************************
    /**
     * email 的正则表达式 <code>{@value}</code>.
     * 
     * @see "org.apache.commons.validator.routines.EmailValidator"
     * @see <a href="http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/">how-to-validate-email
     *      -address-with-regular-expression</a>
     * @see <a href="https://en.wikipedia.org/wiki/Email_address">Email_address</a>
     * @deprecated 建议使用apache commons-validator<br>
     *             "org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(emailString)"
     *             ,<br>
     *             验证更加完善,分别会对user 和domain再次校验
     */
    @Deprecated
    public static final String EMAIL                   = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    //"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",

    /**
     * IP 的正则表达式 <code>{@value}</code>.
     * 
     * @deprecated 建议使用apache commons-validator <br>
     *             "org.apache.commons.validator.routines.InetAddressValidator.getInstance().isValid(emailString)"
     */
    @Deprecated
    public static final String IP                      = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

    //******************************************************************************

    /**
     * 所有都是字母 <code>{@value}</code>.
     * 
     * @see java.lang.Character#isLetter(char)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isAlpha(CharSequence)}
     */
    @Deprecated
    public static final String LETTER                  = "^[A-Za-z]+$";

    /**
     * 小写字母 <code>{@value}</code>.
     * 
     * @see java.lang.Character#isLowerCase(char)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isAllLowerCase(CharSequence)}
     */
    @Deprecated
    public static final String LETTER_LOWERCASE        = "^[a-z]+$";

    /**
     * 大写字母 <code>{@value}</code>.
     * 
     * @see java.lang.Character#isUpperCase(char)
     * @deprecated 建议使用 {@link org.apache.commons.lang3.StringUtils#isAllUpperCase(CharSequence)}
     */
    @Deprecated
    public static final String LETTER_UPPERCASE        = "^[A-Z]+$";

    /**
     * 网址Url 链接 <code>{@value}</code>.
     * 
     * @deprecated 建议使用apache commons-validator <br>
     *             "org.apache.commons.validator.routines.DomainValidator.getInstance().isValid(emailString)"
     */
    @Deprecated
    public static final String URLLINK                 = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /** Don't let anyone instantiate this class. */
    private RegexPattern(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}