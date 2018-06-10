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
package com.feilong.core.lang.numberutiltest;

import static com.feilong.core.NumberPattern.PERCENT_WITH_NOPOINT;

import org.junit.Test;

import com.feilong.core.lang.NumberUtil;

/**
 * The Class NumberUtilGetProgressTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetProgressTest{

    /**
     * Gets the progress null current.
     */
    @Test(expected = NullPointerException.class)
    public void getProgressNullCurrent(){
        NumberUtil.getProgress(null, 5, PERCENT_WITH_NOPOINT);
    }

    /**
     * Gets the progress null total.
     */
    @Test(expected = NullPointerException.class)
    public void getProgressNullTotal(){
        NumberUtil.getProgress(5, null, PERCENT_WITH_NOPOINT);
    }

    /**
     * Gets the progress 3.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getProgress3(){
        NumberUtil.getProgress(-5, 5, PERCENT_WITH_NOPOINT);
    }

    /**
     * Gets the progress 4.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getProgress4(){
        NumberUtil.getProgress(5, -5, PERCENT_WITH_NOPOINT);
    }

    /**
     * Gets the progress 5.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getProgress5(){
        NumberUtil.getProgress(5, 4, PERCENT_WITH_NOPOINT);
    }

    /**
     * Gets the progress null pattern.
     *
     */
    @Test(expected = NullPointerException.class)
    public void getProgressNullPattern(){
        NumberUtil.getProgress(5, 8, null);
    }

    /**
     * Gets the progress empty pattern.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getProgressEmptyPattern(){
        NumberUtil.getProgress(5, 8, "");
    }

    /**
     * Gets the progress empty pattern 1.
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void getProgressEmptyPattern1(){
        NumberUtil.getProgress(5, 8, " ");
    }
}
