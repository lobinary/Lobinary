/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * file and determines that the file is malformed or otherwise cannot
 * be interpreted as a class file.
 *
 * <p>
 *  Java虚拟机尝试读取类文件并确定该文件格式不正确或无法解释为类文件时抛出。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public
class ClassFormatError extends LinkageError {
    private static final long serialVersionUID = -8420114879011949195L;

    /**
     * Constructs a <code>ClassFormatError</code> with no detail message.
     * <p>
     *  构造一个没有详细消息的<code> ClassFormatError </code>。
     * 
     */
    public ClassFormatError() {
        super();
    }

    /**
     * Constructs a <code>ClassFormatError</code> with the specified
     * detail message.
     *
     * <p>
     *  使用指定的详细消息构造<code> ClassFormatError </code>。
     * 
     * @param   s   the detail message.
     */
    public ClassFormatError(String s) {
        super(s);
    }
}
