/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.net;

/**
 * A simple interface which provides a mechanism to map
 * between a file name and a MIME type string.
 *
 * <p>
 *  一个简单的接口,它提供了一种在文件名和MIME类型字符串之间映射的机制。
 * 
 * 
 * @author  Steven B. Byrne
 * @since   JDK1.1
 */
public interface FileNameMap {

    /**
     * Gets the MIME type for the specified file name.
     * <p>
     *  获取指定文件名的MIME类型。
     * 
     * @param fileName the specified file name
     * @return a {@code String} indicating the MIME
     * type for the specified file name.
     */
    public String getContentTypeFor(String fileName);
}
