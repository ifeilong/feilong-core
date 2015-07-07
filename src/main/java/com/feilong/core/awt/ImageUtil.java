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
package com.feilong.core.awt;

import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.FileUtil;
import com.feilong.core.io.ImageType;
import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.tools.json.JsonUtil;

/**
 * 和awt package相关的图片工具类.
 * 
 * <h3>关于图片格式 <code>formatName</code>:</h3>
 * 
 * <blockquote>
 * 
 * 用于 {@link javax.imageio.ImageIO.CanEncodeImageAndFormatFilter#CanEncodeImageAndFormatFilter(ImageTypeSpecifier, String)}
 * <ul>
 * <li> {@link com.sun.imageio.plugins.bmp.BMPImageWriterSpi}</li>
 * <li> {@link com.sun.imageio.plugins.gif.GIFImageWriterSpi}</li>
 * <li> {@link com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi}</li>
 * <li> {@link com.sun.imageio.plugins.png.PNGImageWriterSpi}</li>
 * <li> {@link com.sun.imageio.plugins.wbmp.WBMPImageWriterSpi}</li>
 * </ul>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0 2010-11-30 下午03:24:45
 * @see javax.imageio.ImageIO
 * @since 1.0.0
 */
public final class ImageUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    /** Don't let anyone instantiate this class. */
    private ImageUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Write.
     *
     * @param renderedImage
     *            the rendered image
     * @param outputFilePath
     *            the output file path
     * @param formatName
     *            the format name
     * @see FileUtil#getFileOutputStream(String)
     * @see #write(RenderedImage, OutputStream, String)
     * @since 1.2.0
     */
    public static void write(RenderedImage renderedImage,String outputFilePath,String formatName){
        OutputStream outputStream = FileUtil.getFileOutputStream(outputFilePath);
        write(renderedImage, outputStream, formatName);

        LOGGER.info("write image success:[{}]", outputFilePath);
    }

    /**
     * Write.
     *
     * @param renderedImage
     *            renderedImage
     * @param outputStream
     *            outputStream will close
     * @param formatName
     *            a String containg the informal name of the format {@link ImageType}.
     * @see javax.imageio.ImageIO#write(RenderedImage, String, OutputStream)
     */
    public static void write(RenderedImage renderedImage,OutputStream outputStream,String formatName){
        try{
            ImageIO.write(renderedImage, formatName, outputStream);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 从一个old图片,生成一个新的 new BufferedImage.
     * <p>
     * 该BufferedImage 的Width Height 和原图一样<br>
     * 该BufferedImage操作不会影响原图.
     * </p>
     * 
     * @param oldBufferedImage
     *            oldBufferedImage
     * @return new BufferedImage
     * @see java.awt.image.BufferedImage
     */
    public static BufferedImage getNewBufferedImageFromFile(BufferedImage oldBufferedImage){
        int width = oldBufferedImage.getWidth();
        int height = oldBufferedImage.getHeight();

        BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return newBufferedImage;
    }

    /**
     * 从一个old图片,生成一个新的 new BufferedImage.
     * <p>
     * 该BufferedImage 的Width Height 和原图一样<br>
     * 该BufferedImage操作不会影响原图.
     * </p>
     * 
     * @param imagePath
     *            图片
     * @return new BufferedImage
     * @see #getNewBufferedImageFromFile(BufferedImage)
     * @see java.awt.image.BufferedImage
     */
    public static BufferedImage getNewBufferedImageFromFile(String imagePath){
        BufferedImage oldBufferedImage = getBufferedImage(imagePath);
        return getNewBufferedImageFromFile(oldBufferedImage);
    }

    /**
     * 基于原始图片,获得一个Graphics2D,大小和原图相等.<br>
     * 并 drawImage原始图.
     * 
     * @param newBufferedImage
     *            newBufferedImage
     * @param bufferedImageOld
     *            bufferedImageOld
     * @return the graphics2 d by image
     * @see java.awt.Graphics2D
     * @see java.awt.image.BufferedImage
     */
    public static Graphics2D getGraphics2DByImage(BufferedImage newBufferedImage,BufferedImage bufferedImageOld){
        int width = bufferedImageOld.getWidth();
        int height = bufferedImageOld.getHeight();

        Graphics2D graphics2D = newBufferedImage.createGraphics();
        graphics2D.drawImage(bufferedImageOld, 0, 0, width, height, null);
        return graphics2D;
    }

    /**
     * 获得image/{@link java.awt.image.BufferedImage} 对象.<br>
     * {@link java.awt.image.BufferedImage} 子类描述具有 可访问图像数据缓冲区的 Image.
     *
     * @param imageFilePath
     *            图像路径
     * @return the buffered image
     * @see javax.imageio.ImageIO#read(File)
     * @see #getBufferedImage(File)
     */
    public static BufferedImage getBufferedImage(String imageFilePath){
        File file = new File(imageFilePath);
        return getBufferedImage(file);
    }

    /**
     * 获得image/{@link java.awt.image.BufferedImage} 对象.<br>
     * {@link java.awt.image.BufferedImage} 子类描述具有 可访问图像数据缓冲区的 Image.
     *
     * @param imageFile
     *            the file
     * @return the buffered image
     * @see javax.imageio.ImageIO#read(File)
     * @since 1.2.0
     */
    public static BufferedImage getBufferedImage(File imageFile){
        try{
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("input imageFile's absolutePath is:[{}]", imageFile.getAbsolutePath());
                Map<String, Object> map = getBufferedImageInfoMapForLog(bufferedImage);
                LOGGER.debug("bufferedImage info:{}", JsonUtil.format(map, new String[] { "data", "matrix" }));
            }
            return bufferedImage;
        }catch (IOException e){
            LOGGER.error("", e);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 获得 buffered image info map for LOGGER.
     *
     * @param bufferedImage
     *            the buffered image
     * @return the buffered image info map for log
     * @since 1.2.0
     */
    private static Map<String, Object> getBufferedImageInfoMapForLog(BufferedImage bufferedImage){
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("getWidth()", bufferedImage.getWidth());
        map.put("getHeight()", bufferedImage.getHeight());
        map.put("getPropertyNames()", bufferedImage.getPropertyNames());
        map.put("isAlphaPremultiplied()", bufferedImage.isAlphaPremultiplied());
        map.put("toString()", bufferedImage.toString());
        map.put("getType()", bufferedImage.getType());
        map.put("getMinTileX()", bufferedImage.getMinTileX());
        map.put("getMinTileY()", bufferedImage.getMinTileY());
        map.put("getMinX()", bufferedImage.getMinX());
        map.put("getMinY()", bufferedImage.getMinY());
        map.put("getColorModel()", bufferedImage.getColorModel());
        map.put("getNumXTiles()", bufferedImage.getNumXTiles());
        map.put("getNumYTiles()", bufferedImage.getNumYTiles());
        map.put("getSampleModel()", bufferedImage.getSampleModel());
        map.put("getTileGridXOffset()", bufferedImage.getTileGridXOffset());
        map.put("getTileGridYOffset()", bufferedImage.getTileGridYOffset());
        map.put("getTileHeight()", bufferedImage.getTileHeight());
        map.put("getTileWidth()", bufferedImage.getTileWidth());
        map.put("getTransparency()", bufferedImage.getTransparency());
        map.put("getWritableTileIndices()", bufferedImage.getWritableTileIndices());
        return map;
    }

    /**
     * 是否是cmyk类型.
     *
     * @param imageFilename
     *            文件
     * @return 是否是cmyk类型,是返回true
     * @see java.awt.color.ColorSpace
     * @see java.awt.color.ColorSpace#TYPE_CMYK
     * @see #getBufferedImage(String)
     * @deprecated 未成功验证,暂时不要调用
     */
    @Deprecated
    public static boolean isCMYKType(String imageFilename){
        BufferedImage bufferedImage = getBufferedImage(imageFilename);
        ColorSpace colorSpace = getColorSpace(bufferedImage);
        int colorSpaceType = colorSpace.getType();
        return colorSpaceType == ColorSpace.TYPE_CMYK;
    }

    /**
     * 获得 color space.
     *
     * @param bufferedImage
     *            the buffered image
     * @return the color space
     * @since 1.2.0
     */
    private static ColorSpace getColorSpace(BufferedImage bufferedImage){
        ColorModel colorModel = bufferedImage.getColorModel();
        ColorSpace colorSpace = colorModel.getColorSpace();
        return colorSpace;
    }
}