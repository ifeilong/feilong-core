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

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import com.feilong.core.io.UncheckedIOException;

/**
 * 截屏操作.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-30 下午01:33:59
 * @see java.awt.Robot
 * @see java.awt.Toolkit
 * @since 1.0.0
 */
public final class ScreenShotUtil{

    /** Don't let anyone instantiate this class. */
    private ScreenShotUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 全屏幕截取.
     * 
     * @param fileName
     *            文件名称
     * @param formatName
     *            图片格式
     * @see #screenshot(String, String, int, int, int, int)
     */
    public static void screenshot(String fileName,String formatName){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        screenshot(fileName, formatName, 0, 0, width, height);
    }

    /**
     * 区域截图.
     *
     * @param fileName
     *            文件名称
     * @param formatName
     *            图片格式
     * @param x
     *            左上角的 X 坐标
     * @param y
     *            左上角的 Y 坐标
     * @param width
     *            高度
     * @param height
     *            宽度
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getRobot()
     * @see com.feilong.core.awt.ImageUtil#write(RenderedImage, String, String)
     */
    public static void screenshot(String fileName,String formatName,int x,int y,int width,int height) throws UncheckedIOException{
        Rectangle rectangle = new Rectangle(x, y, width, height);
        screenshot(fileName, formatName, rectangle);
    }

    /**
     * 区域截图.
     *
     * @param fileName
     *            文件名称
     * @param formatName
     *            图片格式
     * @param rectangle
     *            {@link java.awt.Rectangle}
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @since 1.2.1
     */
    public static void screenshot(String fileName,String formatName,Rectangle rectangle) throws UncheckedIOException{
        Robot robot = getRobot();
        BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
        ImageUtil.write(bufferedImage, fileName, formatName);
    }

    /**
     * 获得 {@link java.awt.Robot}.
     *
     * @return the robot
     * @throws RuntimeException
     *             the runtime exception
     * @see java.awt.Robot
     * @since 1.2.0
     */
    private static Robot getRobot() throws RuntimeException{
        try{
            Robot robot = new Robot();
            return robot;
        }catch (AWTException e){
            throw new RuntimeException(e);
        }
    }
}