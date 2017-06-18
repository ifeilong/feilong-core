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
package com.feilong.core.lang;

import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.SortUtil.sortMapByKeyAsc;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.ConvertUtil;

/**
 * {@link java.lang.System}工具类.
 * 
 * <h3>常用Properties:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>file.encoding <br>
 * {@link org.apache.commons.lang3.SystemUtils#FILE_ENCODING}</td>
 * <td>utf-8</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>file.separator <br>
 * {@link org.apache.commons.lang3.SystemUtils#FILE_SEPARATOR}</td>
 * <td>\\</td>
 * <tr valign="top">
 * <td>java.home <br>
 * {@link org.apache.commons.lang3.SystemUtils#JAVA_HOME}</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\jre</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.io.tmpdir <br>
 * {@link org.apache.commons.lang3.SystemUtils#JAVA_IO_TMPDIR}</td>
 * <td>C:\\Users\\feilong\\AppData\\Local\\Temp\\</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.version <br>
 * {@link org.apache.commons.lang3.SystemUtils#JAVA_VERSION}</td>
 * <td>1.6.0_37</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>line.separator <br>
 * {@link System#lineSeparator()}</td>
 * <td>\r\n</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>user.dir <br>
 * {@link org.apache.commons.lang3.SystemUtils#USER_DIR}</td>
 * <td>E:\\Workspaces\\feilong\\feilong-platform\\commons\\feilong-core</td>
 * </tr>
 * <tr valign="top">
 * <td>user.home<br>
 * {@link org.apache.commons.lang3.SystemUtils#USER_HOME}</td>
 * <td>C:\\Users\\feilong</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * 
 * <h3>其他Properties:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>awt.toolkit</td>
 * <td>sun.awt.windows.WToolkit</td>
 * </tr>
 * <tr valign="top">
 * <td>file.encoding.pkg</td>
 * <td>sun.io</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>java.awt.graphicsenv</td>
 * <td>sun.awt.Win32GraphicsEnvironment</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.awt.printerjob</td>
 * <td>sun.awt.windows.WPrinterJob</td>
 * </tr>
 * <tr valign="top">
 * <td>java.class.path</td>
 * <td>E:\\Workspaces\\feilong\\feilong-platform\\commons\\feilong-core\\target\\test-classes;E:\\Workspaces\\feilong\\feilong-platform\\
 * commons\\feilong-core\\target\\classes;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\org\\slf4j\\slf4j-api\\1.7.6\\slf4j-api-1.7.6.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\net\\sf\\json-lib\\json-lib\\2.4\\json-lib-2.4-jdk15.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\net\\sf\\ezmorph\\ezmorph\\1.0.6\\ezmorph-1.0.6.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\commons-lang\\commons-lang\\2.6\\commons-lang-2.6.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\org\\apache\\commons\\commons-lang3\\3.3.2\\commons-lang3-3.3.2.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\commons-beanutils\\commons-beanutils\\1.9.1\\commons-beanutils-1.9.1.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\commons-logging\\commons-logging\\1.1.1\\commons-logging-1.1.1.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\commons-collections\\commons-collections\\3.2.1\\commons-collections-3.2.1.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\xom\\xom\\1.2.5\\xom-1.2.5.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\xml-apis\\xml-apis\\1.3.03\\xml-apis-1.3.03.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\xerces\\xercesImpl\\2.8.0\\xercesImpl-2.8.0.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\xalan\\xalan\\2.7.0\\xalan-2.7.0.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\junit\\junit\\4.10\\junit-4.10.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\org\\hamcrest\\hamcrest-core\\1.1\\hamcrest-core-1.1.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\org\\easymock\\easymock\\3.1\\easymock-3.1.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\cglib\\cglib-nodep\\2.2.2\\cglib-nodep-2.2.2.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository
 * \\org\\objenesis\\objenesis\\1.2\\objenesis-1.2.jar;E:\\Workspaces\\feilong\\else\\feilong-common-test\\target\\classes;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\org\\slf4j\\slf4j-log4j12\\1.7.6\\slf4j-log4j12-1.7.6.jar;D:\\FeiLong
 * Soft\\Essential\\Development\\repository\\log4j\\log4j\\1.2.17\\log4j-1.2.17.jar;/D:/FeiLong
 * Soft/Essential/Development/eclipse-jee-kepler-SR2-win32/configuration/org.eclipse.osgi/bundles/378/1/.cp/;/D:/FeiLong
 * Soft/Essential/Development/eclipse-jee-kepler-SR2-win32/configuration/org.eclipse.osgi/bundles/377/1/.cp/</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.class.version</td>
 * <td>50.0</td>
 * </tr>
 * <tr valign="top">
 * <td>java.endorsed.dirs</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\endorsed</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.ext.dirs</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\ext;C:\\Windows\\Sun\\Java\\lib\\ext</td>
 * </tr>
 * 
 * 
 * <tr valign="top">
 * <td>java.library.path</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\bin;C:\\Windows\\Sun\\Java\\bin;C:\\Windows\\system32;C:\\Windows;D:/Program
 * Files/Java/jre6/bin/client;D:/Program Files/Java/jre6/bin;D:/Program Files/Java/jre6/lib/i386;C:\\Program Files (x86)\\NVIDIA
 * Corporation\\PhysX\\Common;C:\\Program Files (x86)\\Common
 * Files\\NetSarang;C:\\Windows\\system32;C:\\Windows;C:\\Windows\\System32\\Wbem;
 * C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x86;C:\\Program Files
 * (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x64;C:\\Program Files (x86)\\ATI Technologies\\ATI.ACE\\Core-Static;C:\\Program
 * Files\\TortoiseSVN\\bin;D:\\Program Files\\Java\\jdk1.6.0_37\\bin;D:\\FeiLong Soft;D:\\FeiLong Soft\\Essential\\run;D:\\FeiLong
 * Soft\\Essential\\Development\\apache-ant-1.9.3\\bin;D:\\FeiLong Soft\\Essential\\Development\\apache-maven-3.2.1\\bin;D:\\FeiLong
 * Soft\\Datebase\\postgresql\\postgresql-9.2.4-1-windows-x64-binaries\\bin;;D:\\FeiLong
 * Soft\\Essential\\Development\\eclipse-jee-kepler-SR2-win32;;.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.runtime.name</td>
 * <td>Java(TM) SE Runtime Environment</td>
 * </tr>
 * <tr valign="top">
 * <td>java.runtime.version</td>
 * <td>1.6.0_37-b06</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.specification.name</td>
 * <td>Java Platform API Specification</td>
 * </tr>
 * <tr valign="top">
 * <td>java.specification.vendor</td>
 * <td>Sun Microsystems Inc.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.specification.version</td>
 * <td>1.6</td>
 * </tr>
 * <tr valign="top">
 * <td>java.vendor</td>
 * <td>Sun Microsystems Inc.</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.vendor.url</td>
 * <td>http://java.sun.com/</td>
 * </tr>
 * <tr valign="top">
 * <td>java.vendor.url.bug</td>
 * <td>http://java.sun.com/cgi-bin/bugreport.cgi</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>java.vm.info</td>
 * <td>mixed mode</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.vm.name</td>
 * <td>Java HotSpot(TM) Client VM</td>
 * </tr>
 * <tr valign="top">
 * <td>java.vm.specification.name</td>
 * <td>Java Virtual Machine Specification</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.vm.specification.vendor</td>
 * <td>Sun Microsystems Inc.</td>
 * </tr>
 * <tr valign="top">
 * <td>java.vm.specification.version</td>
 * <td>1.0</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>java.vm.vendor</td>
 * <td>Sun Microsystems Inc.</td>
 * </tr>
 * <tr valign="top">
 * <td>java.vm.version</td>
 * <td>20.12-b01</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>os.arch</td>
 * <td>x86</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>os.name</td>
 * <td>Windows 7</td>
 * </tr>
 * <tr valign="top">
 * <td>os.version</td>
 * <td>6.1</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>path.separator</td>
 * <td>;</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.arch.data.model</td>
 * <td>32</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.boot.class.path</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\resources.jar;D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\rt.jar;D:\\Program
 * Files\\Java\\jdk1.6.0_37\\jre\\lib\\sunrsasign.jar;D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\jsse.jar;D:\\Program
 * Files\\Java\\jdk1.6.0_37\\jre\\lib\\jce.jar;D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\lib\\charsets.jar;D:\\Program
 * Files\\Java\\jdk1.6.0_37\\jre\\lib\\modules\\jdk.boot.jar;D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\classes</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.boot.library.path</td>
 * <td>D:\\Program Files\\Java\\jdk1.6.0_37\\jre\\bin</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.cpu.endian</td>
 * <td>little</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.cpu.isalist</td>
 * <td>pentium_pro+mmx pentium_pro pentium+mmx pentium i486 i386 i86</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.desktop</td>
 * <td>windows</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.io.unicode.encoding</td>
 * <td>UnicodeLittle</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.java.command</td>
 * <td>org.eclipse.jdt.internal.junit.runner.RemoteTestRunner -version 3 -port 60401 -testLoaderClass
 * org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader -loaderpluginname org.eclipse.jdt.junit4.runtime -test
 * com.feilong.core.lang.SystemUtilTest:getPropertiesMapForLog</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.java.launcher</td>
 * <td>SUN_STANDARD</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.jnu.encoding</td>
 * <td>GBK</td>
 * </tr>
 * <tr valign="top">
 * <td>sun.management.compiler</td>
 * <td>HotSpot Client Compiler</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>sun.os.patch.level</td>
 * <td>Service Pack 1</td>
 * </tr>
 * <tr valign="top">
 * <td>user.country</td>
 * <td>CN</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>user.language</td>
 * <td>zh</td>
 * </tr>
 * <tr valign="top">
 * <td>user.name</td>
 * <td>feilong</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>user.timezone</td>
 * <td>Asia/Bangkok</td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.lang.System
 * @see org.apache.commons.lang3.SystemUtils
 * @see org.apache.commons.lang3.SystemUtils#USER_HOME
 * @see org.apache.commons.lang3.SystemUtils#FILE_ENCODING
 * @see org.apache.commons.lang3.SystemUtils#FILE_SEPARATOR
 * @see org.apache.commons.lang3.SystemUtils#JAVA_IO_TMPDIR
 * 
 * @see "org.springframework.util.SystemPropertyUtils"
 * @since 1.0.7
 */
public final class SystemUtil{

    /** Don't let anyone instantiate this class. */
    private SystemUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 取到 {@link System#getProperty(String)},转成 {@link java.util.TreeMap},以遍输出log的时候,会顺序显示.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(SystemUtil.getPropertiesMap()));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
        "file.encoding": "UTF-8",
        "file.encoding.pkg": "sun.io",
        "file.separator": "\\",
        "java.class.path": "E:\\Workspaces\\feilong\\feilong-core\\target\\test-classes;E:\\Workspaces\\feilong\\feilong-core\\target\\classes;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\apache\\commons\\commons-lang3\\3.4\\commons-lang3-3.4.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\apache\\commons\\commons-collections4\\4.1\\commons-collections4-4.1.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\commons-beanutils\\commons-beanutils\\1.9.2\\commons-beanutils-1.9.2.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\net\\sf\\json-lib\\json-lib\\2.4\\json-lib-2.4-jdk15.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\net\\sf\\ezmorph\\ezmorph\\1.0.6\\ezmorph-1.0.6.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\slf4j\\slf4j-log4j12\\1.7.21\\slf4j-log4j12-1.7.21.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\log4j\\log4j\\1.2.17\\log4j-1.2.17.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\commons-lang\\commons-lang\\2.6\\commons-lang-2.6.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\commons-logging\\commons-logging\\1.2\\commons-logging-1.2.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\commons-collections\\commons-collections\\3.2.2\\commons-collections-3.2.2.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\hamcrest\\hamcrest-library\\1.3\\hamcrest-library-1.3.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\hamcrest\\hamcrest-core\\1.3\\hamcrest-core-1.3.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\xom\\xom\\1.2.5\\xom-1.2.5.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\xalan\\xalan\\2.7.0\\xalan-2.7.0.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\org\\slf4j\\slf4j-api\\1.7.21\\slf4j-api-1.7.21.jar;D:\\FeiLong Soft\\Essential\\Development\\repository\\junit\\junit\\4.12\\junit-4.12.jar;E:\\Workspaces\\feilong\\feilong-resource\\feilong-common-test\\target\\classes;/D:/FeiLong Soft/Essential/Development/eclipse-jee-mars-2-win32/configuration/org.eclipse.osgi/425/0/.cp/;/D:/FeiLong Soft/Essential/Development/eclipse-jee-mars-2-win32/configuration/org.eclipse.osgi/424/0/.cp/",
        "java.class.version": "51.0",
        "java.endorsed.dirs": "D:\\Program Files\\Java\\jdk1.7.0_60\\jre\\lib\\endorsed",
        "java.ext.dirs": "D:\\Program Files\\Java\\jdk1.7.0_60\\jre\\lib\\ext;C:\\Windows\\Sun\\Java\\lib\\ext",
        "java.home": "D:\\Program Files\\Java\\jdk1.7.0_60\\jre",
        "java.io.tmpdir": "C:\\Users\\feilong\\AppData\\Local\\Temp\\",
        "java.library.path": "D:\\Program Files\\Java\\jdk1.7.0_60\\bin;C:\\Windows\\Sun\\Java\\bin;C:\\Windows\\system32;C:\\Windows;D:/Program Files/Java/jre1.8.0_45/bin/client;D:/Program Files/Java/jre1.8.0_45/bin;D:/Program Files/Java/jre1.8.0_45/lib/i386;C:\\ProgramData\\Oracle\\Java\\javapath;D:\\Goto;C:\\Program Files (x86)\\NVIDIA Corporation\\PhysX\\Common;C:\\Program Files (x86)\\Common Files\\NetSarang;C:\\Windows\\system32;C:\\Windows;C:\\Windows\\System32\\Wbem;C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x86;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x64;C:\\Program Files (x86)\\ATI Technologies\\ATI.ACE\\Core-Static;C:\\Program Files\\TortoiseSVN\\bin;C:\\Program Files (x86)\\Skype\\Phone\\;C:\\Program Files\\TortoiseGit\\bin;D:\\Program Files\\Java\\jdk1.8.0_45\\bin;D:\\FeiLong Soft;D:\\FeiLong Soft\\Essential\\run;D:\\FeiLong Soft\\StaticDevelopment\\apache-ant-1.9.3\\bin;D:\\FeiLong Soft\\StaticDevelopment\\apache-maven-3.3.3\\bin;D:\\FeiLong Soft\\Datebase\\postgresql\\postgresql-9.4.0-1-windows-x64-binaries\\bin;C:\\Program Files (x86)\\Git\\bin;C:\\Program Files (x86)\\Git\\libexec\\git-core;D:\\FeiLong Soft\\Essential\\Development\\redis-2.8.19;D:\\FeiLong Soft\\Essential\\Development\\eclipse-jee-mars-2-win32;;.",
        "java.runtime.name": "Java(TM) SE Runtime Environment",
        "java.runtime.version": "1.7.0_60-b19",
        "java.specification.name": "Java Platform API Specification",
        "java.specification.vendor": "Oracle Corporation",
        "java.specification.version": "1.7",
        "java.vendor": "Oracle Corporation",
        "java.vendor.url": "http://java.oracle.com/",
        "java.vendor.url.bug": "http://bugreport.sun.com/bugreport/",
        "java.version": "1.7.0_60",
        "java.vm.info": "mixed mode, sharing",
        "java.vm.name": "Java HotSpot(TM) Client VM",
        "java.vm.specification.name": "Java Virtual Machine Specification",
        "java.vm.specification.vendor": "Oracle Corporation",
        "java.vm.specification.version": "1.7",
        "java.vm.vendor": "Oracle Corporation",
        "java.vm.version": "24.60-b09",
        "line.separator": "\r\n",
        "os.arch": "x86",
        "os.name": "Windows 7",
        "os.version": "6.1",
        "path.separator": ";",
        "sun.desktop": "windows",
        "user.country": "CN",
        "user.dir": "E:\\Workspaces\\feilong\\feilong-core",
        "user.home": "C:\\Users\\feilong",
        "user.language": "zh",
        "user.name": "feilong",
        "user.script": "",
        "user.timezone": "Asia/Shanghai",
        "user.variant": ""
    }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @return the properties map for log
     * @see System#getProperties()
     * @see ConvertUtil#toMap(Properties)
     * @see "org.springframework.core.env.AbstractEnvironment#getSystemProperties()"
     * @since 1.8.0 change name
     */
    public static Map<String, String> getPropertiesMap(){
        return toMap(System.getProperties());
    }

    /**
     * 取到 {@link System#getenv()},转成 {@link java.util.TreeMap},以遍输出log的时候,会顺序显示.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * LOGGER.debug(JsonUtil.format(SystemUtil.getEnvMap()));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
        "=::": "::\\",
        "ALLUSERSPROFILE": "C:\\ProgramData",
        "ANT_HOME": "D:\\FeiLong Soft\\StaticDevelopment\\apache-ant-1.9.3",
        "APPDATA": "C:\\Users\\feilong\\AppData\\Roaming",
        "APP_ENCRYPTION_PASSWORD_SPEEDO": "speedodev",
        "COMPUTERNAME": "FEILONG-PC",
        "ComSpec": "C:\\Windows\\system32\\cmd.exe",
        "FP_NO_HOST_CHECK": "NO",
        "HOME": "C:\\Users\\feilong",
        "HOMEDRIVE": "C:",
        "HOMEPATH": "\\Users\\feilong",
        "LOCALAPPDATA": "C:\\Users\\feilong\\AppData\\Local",
        "LOGONSERVER": "\\\\FEILONG-PC",
        "NUMBER_OF_PROCESSORS": "4",
        "OS": "Windows_NT",
        "PATHEXT": ".COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC",
        "PROCESSOR_ARCHITECTURE": "x86",
        "PROCESSOR_ARCHITEW6432": "AMD64",
        "PROCESSOR_LEVEL": "6",
        "PROCESSOR_REVISION": "3a09",
        "PSModulePath": "C:\\Windows\\system32\\WindowsPowerShell\\v1.0\\Modules\\",
        "PUBLIC": "C:\\Users\\Public",
        "Path": "D:/Program Files/Java/jre1.8.0_45/bin/client;D:/Program Files/Java/jre1.8.0_45/bin;D:/Program Files/Java/jre1.8.0_45/lib/i386;C:\\ProgramData\\Oracle\\Java\\javapath;D:\\Goto;C:\\Program Files (x86)\\NVIDIA Corporation\\PhysX\\Common;C:\\Program Files (x86)\\Common Files\\NetSarang;C:\\Windows\\system32;C:\\Windows;C:\\Windows\\System32\\Wbem;C:\\Windows\\System32\\WindowsPowerShell\\v1.0\\;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x86;C:\\Program Files (x86)\\Intel\\OpenCL SDK\\2.0\\bin\\x64;C:\\Program Files (x86)\\ATI Technologies\\ATI.ACE\\Core-Static;C:\\Program Files\\TortoiseSVN\\bin;C:\\Program Files (x86)\\Skype\\Phone\\;C:\\Program Files\\TortoiseGit\\bin;D:\\Program Files\\Java\\jdk1.8.0_45\\bin;D:\\FeiLong Soft;D:\\FeiLong Soft\\Essential\\run;D:\\FeiLong Soft\\StaticDevelopment\\apache-ant-1.9.3\\bin;D:\\FeiLong Soft\\StaticDevelopment\\apache-maven-3.3.3\\bin;D:\\FeiLong Soft\\Datebase\\postgresql\\postgresql-9.4.0-1-windows-x64-binaries\\bin;C:\\Program Files (x86)\\Git\\bin;C:\\Program Files (x86)\\Git\\libexec\\git-core;D:\\FeiLong Soft\\Essential\\Development\\redis-2.8.19;D:\\FeiLong Soft\\Essential\\Development\\eclipse-jee-mars-2-win32;",
        "ProgramData": "C:\\ProgramData",
        "ProgramFiles": "C:\\Program Files (x86)",
        "ProgramFiles(x86)": "C:\\Program Files (x86)",
        "SESSIONNAME": "Console",
        "SystemDrive": "C:",
        "SystemRoot": "C:\\Windows",
        "TEMP": "C:\\Users\\feilong\\AppData\\Local\\Temp",
        "TMP": "C:\\Users\\feilong\\AppData\\Local\\Temp",
        "USERDOMAIN": "feilong-PC",
        "USERNAME": "feilong",
        "USERPROFILE": "C:\\Users\\feilong",
        "windir": "C:\\Windows",
        "windows_tracing_flags": "3",
        "windows_tracing_logfile": "C:\\BVTBin\\Tests\\installpackage\\csilogfile.log"
    }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @return the env map for log
     * @see System#getenv()
     * @see "org.springframework.core.env.AbstractEnvironment#getSystemEnvironment()"
     * @since 1.8.0 change name
     */
    public static Map<String, String> getEnvMap(){
        return sortMapByKeyAsc(System.getenv());
    }

    //---------------------------------------------------------------

    /**
     * 循环 <code>map</code> ,设置到系统属性 {@link java.lang.System#setProperty(String, String)}.
     * 
     * <p>
     * 如果 <code>map</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>map</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * </p>
     *
     * @param map
     *            the properties from map
     * @see java.lang.System#setProperty(String, String)
     * @since 1.2.0
     */
    public static void setPropertiesFromMap(Map<String, String> map){
        Validate.notEmpty(map, "map can't be null/empty!");
        for (Map.Entry<String, String> entry : map.entrySet()){
            System.setProperty(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 设置 properties from properties.
     * 
     * <p>
     * 如果 <code>properties</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     *
     * @param properties
     *            the properties from properties
     * @see ConvertUtil#toMap(Properties)
     * @see #setPropertiesFromMap(Map)
     * @see java.lang.System#setProperties(Properties)
     * @since 1.2.0
     */
    public static void setPropertiesFromProperties(Properties properties){
        Validate.notNull(properties, "properties can't be null!");
        setPropertiesFromMap(toMap(properties));
    }
}
