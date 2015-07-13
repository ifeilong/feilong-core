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

import static org.junit.Assert.assertEquals;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;

import org.junit.Test;

import com.feilong.core.io.FileUtil;

/**
 * The Class ImageUtilTest.
 *
 * @author feilong
 * @version 1.2.0 2015年5月9日 上午12:03:27
 * @since 1.2.0
 */
public class ImageUtilTest{

    /**
     * Creates new RGB images from all the CMYK images passed in on the command line. The new filename generated is, for example
     * "GIF_original_filename.gif".
     * 
     * @param args
     *            the arguments
     * 
     * @author 徐新望 Jul 9, 2010
     */
    public static void main(String[] args){
        String filename = "E:\\workspaces\\project\\cmyk.jpg";
        String format = "gif";
        String rgbFilename = FileUtil.getNewFileName(filename, format);
        cmyk2rgb(filename, rgbFilename, format);
    }

    /**
     * CMYK转RGB If 'filename' is a CMYK file, then convert the image into RGB, store it into a JPEG file, and return the new filename.
     *
     * @param cmykImageFilename
     *            the filename
     * @param outputFileName
     *            the output file name
     */

    //这样就可以完成转化，但是有一个不好的地方就是生成生颜色失真比较严重。从美工那得知他们本来就是不一样的格式，失真比较正常。[此实例参考了国外的文章。] 
    private static void cmyk2rgb(String cmykImageFilename,String outputFileName,String format){

        BufferedImage cmykBufferedImage = ImageUtil.getBufferedImage(cmykImageFilename);

        if (isCMYKType(cmykImageFilename)){

            BufferedImage rgbBufferedImage = new BufferedImage(
                            cmykBufferedImage.getWidth(),
                            cmykBufferedImage.getHeight(),
                            BufferedImage.TYPE_3BYTE_BGR);

            ColorConvertOp colorConvertOp = new ColorConvertOp(null);
            colorConvertOp.filter(cmykBufferedImage, rgbBufferedImage);

            ImageUtil.write(rgbBufferedImage, outputFileName, format);
        }
    }

    /**
     * Test is cmyk type.
     */
    @Test
    public final void testIsCMYKType(){
        //assertEquals(false, ImageUtil.isCMYKType("E:/DataCommon/test/1.png"));
        assertEquals(false, isCMYKType("E:/DataCommon/test/1.jpg"));
    }

    /**
     * Test is cmyk type2.
     */
    @Test
    public final void testIsCMYKType2(){
        assertEquals(true, isCMYKType("E:/DataCommon/test/cmyk.jpg"));
    }

    /**
     * 是否是cmyk类型.
     *
     * @param imageFilename
     *            文件
     * @return 是否是cmyk类型,是返回true
     * @see java.awt.color.ColorSpace
     * @see java.awt.color.ColorSpace#TYPE_CMYK
     * @see ImageUtil#getBufferedImage(String)
     * @deprecated 未成功验证,暂时不要调用
     */
    @Deprecated
    public static boolean isCMYKType(String imageFilename){
        BufferedImage bufferedImage = ImageUtil.getBufferedImage(imageFilename);
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
