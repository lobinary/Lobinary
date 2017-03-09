/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.parsers;

/**
 * Indicates a serious configuration error.
 *
 * <p>
 *  表示严重的配置错误。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */

public class ParserConfigurationException extends Exception {

    /**
     * Create a new <code>ParserConfigurationException</code> with no
     * detail mesage.
     * <p>
     *  创建一个没有详细信息的新<code> ParserConfigurationException </code>。
     * 
     */

    public ParserConfigurationException() {
        super();
    }

    /**
     * Create a new <code>ParserConfigurationException</code> with
     * the <code>String</code> specified as an error message.
     *
     * <p>
     *  使用指定为错误消息的<code> String </code>创建新的<code> ParserConfigurationException </code>。
     * 
     * @param msg The error message for the exception.
     */

    public ParserConfigurationException(String msg) {
        super(msg);
    }

}
