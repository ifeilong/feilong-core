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
 * 图片类型的枚举.
 * 
 * @author feilong
 * @version 1.0.0 2011-5-30 下午01:19:46
 * @version 1.0.5 2014-5-4 00:24
 * @since 1.0.0
 * @see com.feilong.core.io.MimeType
 */
public final class ImageType{

    /**
     * JPG Joint Photograhic Experts Group（联合图像专家组）,JPEG的文件格式一般有两种文件扩展名：.jpg和.jpeg，这两种扩展名的实质是相同的
     */
    public static final String JPG  = MimeType.JPG.getExtension();

    /**
     * JPEG Joint Photograhic Experts Group（联合图像专家组）,JPEG的文件格式一般有两种文件扩展名：.jpg和.jpeg，这两种扩展名的实质是相同的
     */
    public static final String JPEG = MimeType.JPEG.getExtension();

    /**
     * PNG (Portable Network Graphic Format) 流式网络图形格式.
     */
    public static final String PNG  = MimeType.PNG.getExtension();

    /**
     * GIF (Graphics Interchange format)（图形交换格式） .
     */
    public static final String GIF  = MimeType.GIF.getExtension();

    /**
     * BMP Windows 位图.<br>
     * 为了保证照片图像的质量，请使用 PNG 、JPEG、TIFF 文件.<br>
     * BMP文件适用于 Windows 中的墙纸 .
     */
    public static final String BMP  = MimeType.BMP.getExtension();

    /** Don't let anyone instantiate this class. */
    private ImageType(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}