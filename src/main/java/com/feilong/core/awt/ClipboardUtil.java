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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 操作剪切板 {@link java.awt.datatransfer.Clipboard}.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-23 下午02:38:55
 * @since 1.0.0
 */
public final class ClipboardUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ClipboardUtil.class);

    /** Don't let anyone instantiate this class. */
    private ClipboardUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //**************************************************************
    /**
     * 设置剪贴板数据.
     * 
     * @param data
     *            the new clipboard content
     */
    public static final void setClipboardContent(String data){
        Clipboard clipboard = getSystemClipboard();
        Transferable transferable = new StringSelection(data);

        ClipboardOwner clipboardOwner = null;
        clipboard.setContents(transferable, clipboardOwner);

        log.debug("Clipboard setContents over,clipboardOwner[null]");
    }

    //******************************************************************************
    /**
     * 从剪贴板中取数据.
     *
     * @return the clipboard content
     * @throws UnsupportedFlavorException
     *             the unsupported flavor exception
     * @throws IOException
     *             the IO exception
     */
    public static final String getClipboardContent() throws UnsupportedFlavorException,IOException{
        Transferable transferable = getTransferable();
        // 因为原系的剪贴板里有多种信息, 如文字, 图片, 文件等
        // 先判断开始取得的可传输的数据是不是文字, 如果是, 取得这些文字
        DataFlavor dataFlavor = DataFlavor.stringFlavor;

        if (transferable != null && transferable.isDataFlavorSupported(dataFlavor)){
            // 同样, 因为Transferable中的DataFlavor是多种类型的,
            // 所以传入DataFlavor这个参数, 指定要取得哪种类型的Data.
            return (String) transferable.getTransferData(dataFlavor);
        }
        return null;
    }

    /**
     * 获得 clipboard reader.
     *
     * @return the clipboard reader
     * @throws UnsupportedFlavorException
     *             the unsupported flavor exception
     * @throws IOException
     *             the IO exception
     * @since 1.0.8
     */
    public static final Reader getClipboardReader() throws UnsupportedFlavorException,IOException{
        Transferable transferable = getTransferable();

        DataFlavor dataFlavor = DataFlavor.stringFlavor;
        Reader reader = dataFlavor.getReaderForText(transferable);
        return reader;
    }

    //******************************************************************************************

    /**
     * 获得 transferable.
     *
     * @return the transferable
     * @since 1.0.8
     */
    private static Transferable getTransferable(){
        Clipboard clipboard = getSystemClipboard();

        // 取得系统剪贴板里可传输的数据构造的Java对象
        Object requestor = null;
        Transferable transferable = clipboard.getContents(requestor);
        return transferable;
    }

    /**
     * 获取系统 Clipboard 的一个实例，该 Clipboard 可作为本机平台提供的剪贴板工具的接口.<br>
     * 该剪贴板使数据能够在 Java 应用程序和使用本机剪贴板工具的本机应用程序之间传输..
     * 
     * @return Clipboard
     * @see java.awt.Toolkit#getDefaultToolkit()
     * @see java.awt.Toolkit#getSystemClipboard()
     */
    private static final Clipboard getSystemClipboard(){
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = defaultToolkit.getSystemClipboard();
        return clipboard;
    }
}
