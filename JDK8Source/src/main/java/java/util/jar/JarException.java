/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.util.jar;

/**
 * Signals that an error of some sort has occurred while reading from
 * or writing to a JAR file.
 *
 * <p>
 *  表示在读取或写入JAR文件时发生了某种类型的错误。
 * 
 * 
 * @author  David Connelly
 * @since   1.2
 */
public
class JarException extends java.util.zip.ZipException {
    private static final long serialVersionUID = 7159778400963954473L;

    /**
     * Constructs a JarException with no detail message.
     * <p>
     *  构造一个没有详细消息的JarException。
     * 
     */
    public JarException() {
    }

    /**
     * Constructs a JarException with the specified detail message.
     * <p>
     *  构造具有指定详细消息的JarException。
     * 
     * @param s the detail message
     */
    public JarException(String s) {
        super(s);
    }
}
