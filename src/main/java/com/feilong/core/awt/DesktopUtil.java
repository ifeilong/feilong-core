/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.awt;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.CharsetType;
import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.log.Slf4jUtil;
import com.feilong.core.net.URIUtil;

/**
 * {@link java.awt.Desktop}允许 Java应用程序启动已在本机桌面上注册的关联应用程序，以及处理 URI 或文件 .
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.0 2011-5-5 下午05:07:45
 * @see java.awt.Desktop
 * @since 1.0.0
 * @since jdk 1.6
 */
public final class DesktopUtil{

    /** The Constant LOGGER. */
    private static final Logger  LOGGER            = LoggerFactory.getLogger(DesktopUtil.class);

    /** 判断当前系统是否支持Java AWT Desktop扩展. */
    private final static boolean DESKTOP_SUPPORTED = Desktop.isDesktopSupported();

    /** Don't let anyone instantiate this class. */
    private DesktopUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 使用系统默认浏览器,打开url.
     *
     * @param url
     *            url地址
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #desktopAction(String, Action)
     */
    public static void browse(String url) throws UncheckedIOException{
        desktopAction(url, Desktop.Action.BROWSE);
    }

    /**
     * 启动关联应用程序来打开文件..
     *
     * @param url
     *            url地址
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #desktopAction(String, Action)
     */
    public static void open(String url) throws UncheckedIOException{
        desktopAction(url, Desktop.Action.OPEN);
    }

    /**
     * 发送邮件.
     *
     * @param mailtoURL
     *            the mail
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see java.awt.Desktop#mail(URI)
     * @see #desktopAction(String, Action)
     */
    public static void mail(String mailtoURL) throws UncheckedIOException{
        desktopAction(mailtoURL, Desktop.Action.MAIL);
    }

    /**
     * 打印.
     *
     * @param url
     *            the url
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @since 1.2.0
     */
    public static void print(String url) throws UncheckedIOException{
        desktopAction(url, Desktop.Action.PRINT);
    }

    /**
     * Edits the.
     *
     * @param url
     *            the url
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @since 1.2.0
     */
    public static void edit(String url) throws UncheckedIOException{
        desktopAction(url, Desktop.Action.EDIT);
    }

    /**
     * Desktop action.
     *
     * @param url
     *            the url
     * @param action
     *            the action
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws RuntimeException
     *             the runtime exception
     * @throws UnsupportedOperationException
     *             the unsupported operation exception
     * @since 1.2.0
     */
    private static void desktopAction(String url,Action action) throws UncheckedIOException,RuntimeException,UnsupportedOperationException{
        if (DESKTOP_SUPPORTED){
            // 获取当前系统桌面扩展
            Desktop desktop = Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (desktop.isSupported(action)){
                try{
                    switch (action) {
                        case MAIL:
                            desktop.mail(URIUtil.create(url, CharsetType.UTF8));
                            break;
                        case BROWSE: // 获取系统默认浏览器打开链接
                            desktop.browse(URIUtil.create(url, CharsetType.UTF8));
                            break;
                        case OPEN: // 启动关联应用程序来打开文件
                            desktop.open(new File(url));
                            break;
                        case EDIT:
                            desktop.edit(new File(url));
                            break;
                        case PRINT:
                            desktop.print(new File(url));
                            break;
                        default:
                            String messagePattern = "{} not support!";
                            throw new UnsupportedOperationException(Slf4jUtil.formatMessage(messagePattern, action));
                    }
                }catch (IOException e){
                    throw new UncheckedIOException(e);
                }
            }
        }else{
            LOGGER.error("don't Support Desktop");
            throw new RuntimeException("don't Support Desktop");
        }
    }
}
