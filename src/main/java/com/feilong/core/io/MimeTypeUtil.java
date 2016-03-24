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

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.feilong.core.util.Validator;

/**
 * 获取文件Mime-Type.
 * 
 * <p>
 * MIME(Multi-Purpose Internet Mail Extensions)
 * </p>
 *
 * @author feilong
 * @version 1.0.8 2014年11月19日 上午1:12:50
 * @see com.feilong.core.io.MimeType
 * @see "org.apache.catalina.startup.Tomcat#DEFAULT_MIME_MAPPINGS"
 * @see "org.apache.http.entity.ContentType"
 * @see <a href="http://stackoverflow.com/questions/4348810/java-library-to-find-the-mime-type-from-file-content/10140531#10140531">java
 *      library to find the mime type from file content</a>
 * @see <a href="http://stackoverflow.com/questions/51438/getting-a-files-mime-type-in-java">Getting A File Mime Type In Java</a>
 * @see <a href="http://en.wikipedia.org/wiki/MIME">MIME Wiki</a>
 * @see <a href="http://tika.apache.org/">也可以使用Apache Tika</a>
 * @since 1.0.8
 */
public final class MimeTypeUtil{

    /** The Constant fileExtensionMap. */
    private static final Map<String, String> FILE_EXTENSION_MAP;

    /** Don't let anyone instantiate this class. */
    private MimeTypeUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    static{
        FILE_EXTENSION_MAP = new HashMap<String, String>();
        for (MimeType mimeType : MimeType.values()){
            FILE_EXTENSION_MAP.put(mimeType.getExtension(), mimeType.getMime());
        }
    }

    /**
     * 获得 content type by file name.
     * 
     * <p>
     * <b>Very incomplete function. As of Java 7, html, pdf and jpeg extensions return the correct mime-type but js and css return null!
     * </b>
     * </p>
     * 
     * <p>
     * I tried Apache Tika but it is huge with tons of dependencies, <br>
     * URLConnection doesn't use the bytes of the files, <br>
     * {@link MimetypesFileTypeMap} also just looks at files names,and I couldn't move to Java 7.
     * </p>
     * 
     * @param fileName
     *            the file name
     * @return the content type by file name
     * @see java.net.URLConnection#getFileNameMap()
     * @see javax.activation.MimetypesFileTypeMap#getContentType(java.io.File)
     * @see java.net.FileNameMap#getContentTypeFor(String)
     * @see org.apache.commons.io.FilenameUtils#getExtension(String)
     * @see java.net.URLConnection#guessContentTypeFromName(String)
     * @see java.net.URLConnection#guessContentTypeFromStream(java.io.InputStream)
     * @see "java.nio.file.Files#probeContentType(java.nio.file.Path)"
     */
    public static String getContentTypeByFileName(String fileName){

        String extension = FilenameUtils.getExtension(fileName);
        if (Validator.isNullOrEmpty(extension)){
            return StringUtils.EMPTY;
        }

        // 1. first use java's build-in utils
        //从数据文件加载文件名映射(一个 mimetable)。它首先尝试加载由 "content.types.user.table" 属性定义的特定于用户的表。如果加载失败,它会尝试加载位于 java 主目录下的 lib/content-types.properties 中的默认内置表。
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(fileName);

        // 2. nothing found -> lookup our in extension map to find types like ".doc" or ".docx"
        if (Validator.isNullOrEmpty(contentType)){
            contentType = FILE_EXTENSION_MAP.get(extension.toLowerCase());
        }

        return contentType;
    }
}