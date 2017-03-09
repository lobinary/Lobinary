/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2002, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package com.sun.corba.se.impl.io;

/**
 * Unexpected data appeared in an ObjectInputStream trying to read
 * an Object.
 * This exception occurs when the stream contains primitive data
 * instead of the object expected by readObject.
 * The eof flag in the exception is true to indicate that no more
 * primitive data is available.
 * The count field contains the number of bytes available to read.
 *
 * <p>
 *  意外的数据出现在一个ObjectInputStream试图读取一个对象。当流包含原始数据而不是readObject期望的对象时,会发生此异常。异常中的eof标志为真,表示没有更多的原始数据可用。
 * 计数字段包含可读取的字节数。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public class OptionalDataException extends java.io.IOException {
    /*
     * Create an <code>OptionalDataException</code> with a length.
     * <p>
     *  创建一个带有长度的<code> OptionalDataException </code>。
     * 
     */
    OptionalDataException(int len) {
        eof = false;
        length = len;
    }

    /*
     * Create an <code>OptionalDataException</code> signifing no
     * more primitive data is available.
     * <p>
     *  创建一个<code> OptionalDataException </code>,表示没有更多的原始数据可用。
     * 
     */
    OptionalDataException(boolean end) {
        length = 0;
        eof = end;
    }

    /**
     * The number of bytes of primitive data available to be read
     * in the current buffer.
     * <p>
     *  可用于在当前缓冲区中读取的基本数据的字节数。
     * 
     */
    public int length;

    /**
     * True if there is no more data in the buffered part of the stream.
     * <p>
     *  如果流的缓冲部分中没有更多数据,则为true。
     */
    public boolean eof;
}
