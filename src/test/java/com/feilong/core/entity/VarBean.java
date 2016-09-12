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
package com.feilong.core.entity;

import com.feilong.core.bean.Alias;

/**
 * The Class VarBean.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.1
 */
public class VarBean{

    //    with_space_value: a 
    //    !with_space_value= a 
    //
    //    config_test_boolean=true
    //    config_test_int=888
    //
    //    config_test_array=5,8,7,6
    //
    //    FileType.image=Image
    //    FileType.video=Video
    //    FileType.audio=Audio
    //
    //    test=今天 {0}
    /** The with space value. */
    //    test.arguments=my name is {0},age is {1}
    @Alias(name = "with_space_value")
    private String  withSpaceValue;

    /** The b. */
    @Alias(name = "config_test_boolean")
    private boolean b;

    /** The i. */
    @Alias(name = "config_test_int")
    private boolean i;

    /** The longs. */
    @Alias(name = "config_test_array")
    private Long[]  longs;

    /** The image. */
    @Alias(name = "FileType.image")
    private String  image;

    /** The video. */
    @Alias(name = "FileType.video")
    private String  video;

    /** The audio. */
    @Alias(name = "FileType.audio")
    private String  audio;

    /** The arguments. */
    @Alias(name = "test.arguments")
    private String  arguments;

    /**
     * Gets the with space value.
     *
     * @return the withSpaceValue
     */
    public String getWithSpaceValue(){
        return withSpaceValue;
    }

    /**
     * Sets the with space value.
     *
     * @param withSpaceValue
     *            the withSpaceValue to set
     */
    public void setWithSpaceValue(String withSpaceValue){
        this.withSpaceValue = withSpaceValue;
    }

    /**
     * Gets the b.
     *
     * @return the b
     */
    public boolean getB(){
        return b;
    }

    /**
     * Sets the b.
     *
     * @param b
     *            the b to set
     */
    public void setB(boolean b){
        this.b = b;
    }

    /**
     * Gets the i.
     *
     * @return the i
     */
    public boolean getI(){
        return i;
    }

    /**
     * Sets the i.
     *
     * @param i
     *            the i to set
     */
    public void setI(boolean i){
        this.i = i;
    }

    /**
     * Gets the longs.
     *
     * @return the longs
     */
    public Long[] getLongs(){
        return longs;
    }

    /**
     * Sets the longs.
     *
     * @param longs
     *            the longs to set
     */
    public void setLongs(Long[] longs){
        this.longs = longs;
    }

    /**
     * Gets the image.
     *
     * @return the image
     */
    public String getImage(){
        return image;
    }

    /**
     * Sets the image.
     *
     * @param image
     *            the image to set
     */
    public void setImage(String image){
        this.image = image;
    }

    /**
     * Gets the video.
     *
     * @return the video
     */
    public String getVideo(){
        return video;
    }

    /**
     * Sets the video.
     *
     * @param video
     *            the video to set
     */
    public void setVideo(String video){
        this.video = video;
    }

    /**
     * Gets the audio.
     *
     * @return the audio
     */
    public String getAudio(){
        return audio;
    }

    /**
     * Sets the audio.
     *
     * @param audio
     *            the audio to set
     */
    public void setAudio(String audio){
        this.audio = audio;
    }

    /**
     * Gets the arguments.
     *
     * @return the arguments
     */
    public String getArguments(){
        return arguments;
    }

    /**
     * Sets the arguments.
     *
     * @param arguments
     *            the arguments to set
     */
    public void setArguments(String arguments){
        this.arguments = arguments;
    }
}
