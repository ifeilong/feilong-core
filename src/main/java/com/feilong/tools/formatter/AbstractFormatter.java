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
package com.feilong.tools.formatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.CollectionsUtil;

import static com.feilong.core.Validator.isNullOrEmpty;

/**
 * The Class AbstractFormatter.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public abstract class AbstractFormatter implements Formatter{

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.coreextension.formatter.Formatter#format(java.lang.Iterable)
     */
    @Override
    public <T> String format(Iterable<T> iterable){
        return format(iterable, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.coreextension.formatter.Formatter#format(java.lang.Iterable,
     * com.feilong.coreextension.formatter.BeanFormatterConfig)
     */
    @Override
    public <T> String format(Iterable<T> iterable,BeanFormatterConfig<T> beanFormatterConfig){
        if (isNullOrEmpty(iterable)){
            return EMPTY;
        }
        BeanFormatterConfig<T> useBeanCsvConfig = null == beanFormatterConfig ? buildBeanFormatterConfig(iterable) : beanFormatterConfig;
        Validate.notNull(useBeanCsvConfig.getBeanClass(), "beanCsvConfig.getBeanClass() can't be null!");

        List<FormatterColumnEntity> formatterColumnEntityList = PropertyValueMapExtractor.getFormatterColumnEntityList(useBeanCsvConfig);

        String[] columnTitles = ConvertUtil.toStrings(CollectionsUtil.getPropertyValueList(formatterColumnEntityList, "name"));
        List<Object[]> dataList = PropertyValueMapExtractor.buildDataList(iterable, formatterColumnEntityList);
        return format(columnTitles, dataList);
    }

    /**
     * Builds the bean formatter config.
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @return the bean formatter config
     */
    private static <T> BeanFormatterConfig<T> buildBeanFormatterConfig(Iterable<T> iterable){
        T t = IterableUtils.get(iterable, 0);
        Class<T> klass = (Class<T>) t.getClass();
        return new BeanFormatterConfig<T>(klass);
    }
}
