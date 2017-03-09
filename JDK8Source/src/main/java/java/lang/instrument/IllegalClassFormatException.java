/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.instrument;

/*
 * Copyright 2003 Wily Technology, Inc.
 * <p>
 *  版权所有2003 Wily Technology,Inc.
 * 
 */

/**
 * Thrown by an implementation of
 * {@link java.lang.instrument.ClassFileTransformer#transform ClassFileTransformer.transform}
 * when its input parameters are invalid.
 * This may occur either because the initial class file bytes were
 * invalid or a previously applied transform corrupted the bytes.
 *
 * <p>
 *  当其输入参数无效时,由{@link java.lang.instrument.ClassFileTransformer#transform ClassFileTransformer.transform}
 * 的实现引发。
 * 这可能由于初始类文件字节无效或者先前应用的变换损坏了字节而发生。
 * 
 * 
 * @see     java.lang.instrument.ClassFileTransformer#transform
 * @since   1.5
 */
public class IllegalClassFormatException extends Exception {
    private static final long serialVersionUID = -3841736710924794009L;

    /**
     * Constructs an <code>IllegalClassFormatException</code> with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的<code> IllegalClassFormatException </code>。
     * 
     */
    public
    IllegalClassFormatException() {
        super();
    }

    /**
     * Constructs an <code>IllegalClassFormatException</code> with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造一个<code> IllegalClassFormatException </code>。
     * 
     * @param   s   the detail message.
     */
    public
    IllegalClassFormatException(String s) {
        super(s);
    }
}
