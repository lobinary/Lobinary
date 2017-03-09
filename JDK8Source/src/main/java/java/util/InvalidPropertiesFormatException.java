/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.io.NotSerializableException;
import java.io.IOException;

/**
 * Thrown to indicate that an operation could not complete because
 * the input did not conform to the appropriate XML document type
 * for a collection of properties, as per the {@link Properties}
 * specification.<p>
 *
 * Note, that although InvalidPropertiesFormatException inherits Serializable
 * interface from Exception, it is not intended to be Serializable. Appropriate
 * serialization methods are implemented to throw NotSerializableException.
 *
 * <p>
 *  由于根据{@link属性}规范,输入不符合属性集合的相应XML文档类型,因此表示无法完成操作。<p>
 * 
 *  注意,虽然InvalidPropertiesFormatException从Exception继承Serializable接口,但它不是要可序列化。
 * 适当的序列化方法实现抛出NotSerializableException。
 * 
 * 
 * @see     Properties
 * @since   1.5
 * @serial exclude
 */

public class InvalidPropertiesFormatException extends IOException {

    private static final long serialVersionUID = 7763056076009360219L;

    /**
     * Constructs an InvalidPropertiesFormatException with the specified
     * cause.
     *
     * <p>
     *  构造具有指定原因的InvalidPropertiesFormatException。
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).
     */
    public InvalidPropertiesFormatException(Throwable cause) {
        super(cause==null ? null : cause.toString());
        this.initCause(cause);
    }

   /**
    * Constructs an InvalidPropertiesFormatException with the specified
    * detail message.
    *
    * <p>
    *  使用指定的详细消息构造InvalidPropertiesFormatException。
    * 
    * 
    * @param   message   the detail message. The detail message is saved for
    *          later retrieval by the {@link Throwable#getMessage()} method.
    */
    public InvalidPropertiesFormatException(String message) {
        super(message);
    }

    /**
     * Throws NotSerializableException, since InvalidPropertiesFormatException
     * objects are not intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为InvalidPropertiesFormatException对象不是可序列化的。
     * 
     */
    private void writeObject(java.io.ObjectOutputStream out)
        throws NotSerializableException
    {
        throw new NotSerializableException("Not serializable.");
    }

    /**
     * Throws NotSerializableException, since InvalidPropertiesFormatException
     * objects are not intended to be serializable.
     * <p>
     *  抛出NotSerializableException,因为InvalidPropertiesFormatException对象不是可序列化的。
     */
    private void readObject(java.io.ObjectInputStream in)
        throws NotSerializableException
    {
        throw new NotSerializableException("Not serializable.");
    }

}
