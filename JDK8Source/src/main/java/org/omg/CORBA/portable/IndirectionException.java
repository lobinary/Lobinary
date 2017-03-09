/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性Copyright IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.CORBA.portable;

import org.omg.CORBA.SystemException;
/**
 * The Indirection exception is a Java specific system exception.
 * It is thrown when the ORB's input stream is called to demarshal
 * a value that is encoded as an indirection that is in the process
 * of being demarshaled. This can occur when the ORB input stream
 * calls the ValueHandler to demarshal an RMI value whose state
 * contains a recursive reference to itself. Because the top-level
 * ValueHandler.read_value() call has not yet returned a value,
 * the ORB input stream's indirection table does not contain an entry
 * for an object with the stream offset specified by the indirection
 * tag. The stream offset is returned in the exception's offset field.
 * <p>
 *  间接异常是Java特定的系统异常。当ORB的输入流被调用以解密被编码为正在被解散的过程中的间接的值时,抛出它。
 * 当ORB输入流调用ValueHandler来解析其状态包含对自身的递归引用的RMI值时,可能会发生这种情况。
 * 因为顶层ValueHandler.read_value()调用尚未返回值,所以ORB输入流的间接表不包含具有由间接标记指定的流偏移的对象的条目。流偏移在异常的偏移字段中返回。
 * 
 * 
 * @see org.omg.CORBA_2_3.portable.InputStream
 * @see org.omg.CORBA_2_3.portable.OutputStream
 */
public class IndirectionException extends SystemException {

    /**
     * Points to the stream's offset.
     * <p>
     *  指向流的偏移量。
     * 
     */
    public int offset;

    /**
     * Creates an IndirectionException with the right offset value.
     * The stream offset is returned in the exception's offset field.
     * This exception is constructed and thrown during reading
     * recursively defined values off of a stream.
     *
     * <p>
     *  创建具有正确偏移值的IndirectionException。流偏移在异常的偏移字段中返回。在从流中读取递归定义的值期间构造和抛出此异常。
     * 
     * @param offset the stream offset where recursion is detected.
     */
    public IndirectionException(int offset){
        super("", 0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
        this.offset = offset;
    }
}
