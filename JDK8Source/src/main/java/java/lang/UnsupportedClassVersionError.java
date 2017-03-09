/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown when the Java Virtual Machine attempts to read a class
 * file and determines that the major and minor version numbers
 * in the file are not supported.
 *
 * <p>
 *  Java虚拟机尝试读取类文件并确定不支持文件中的主版本号和次版本号时抛出。
 * 
 * 
 * @since   1.2
 */
public
class UnsupportedClassVersionError extends ClassFormatError {
    private static final long serialVersionUID = -7123279212883497373L;

    /**
     * Constructs a <code>UnsupportedClassVersionError</code>
     * with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> UnsupportedClassVersionError </code>。
     * 
     */
    public UnsupportedClassVersionError() {
        super();
    }

    /**
     * Constructs a <code>UnsupportedClassVersionError</code> with
     * the specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> UnsupportedClassVersionError </code>。
     * 
     * @param   s   the detail message.
     */
    public UnsupportedClassVersionError(String s) {
        super(s);
    }
}
