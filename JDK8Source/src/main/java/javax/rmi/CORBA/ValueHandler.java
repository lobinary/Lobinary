/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

package javax.rmi.CORBA;

/**
 * Defines methods which allow serialization of Java objects
 * to and from GIOP streams.
 * <p>
 *  定义允许将Java对象序列化到GIOP流和从GIOP流中序列化的方法。
 * 
 * 
 **/
public interface ValueHandler {

    /**
     * Writes a value to the stream using Java semantics.
     * <p>
     *  使用Java语义将值写入流。
     * 
     * 
     * @param out the stream to write the value to.
     * @param value the value to be written to the stream.
     **/
    void writeValue(org.omg.CORBA.portable.OutputStream out,
                    java.io.Serializable value);

    /**
     * Reads a value from the stream using Java semantics.
     * <p>
     *  使用Java语义从流读取值。
     * 
     * 
     * @param in the stream to read the value from.
     * @param offset the current position in the input stream.
     * @param clz the type of the value to be read in.
     * @param repositoryID the RepositoryId of the value to be read in.
     * @param sender the sending context runtime codebase.
     * @return the value read from the stream.
     **/
    java.io.Serializable readValue(org.omg.CORBA.portable.InputStream in,
                                   int offset,
                                   java.lang.Class clz,
                                   String repositoryID,
                                   org.omg.SendingContext.RunTime sender);

    /**
     * Returns the CORBA RepositoryId for the given Java class.
     * <p>
     *  返回给定Java类的CORBA RepositoryId。
     * 
     * 
     * @param clz a Java class.
     * @return the CORBA RepositoryId for the class.
     **/
    java.lang.String getRMIRepositoryID(java.lang.Class clz);

    /**
     * Indicates whether the given class performs custom or
     * default marshaling.
     * <p>
     *  指示给定类是否执行自定义或默认编组。
     * 
     * 
     * @param clz the class to test for custom marshaling.
     * @return <code>true</code> if the class performs custom marshaling, <code>false</code>
     * if it does not.
     **/
    boolean isCustomMarshaled(java.lang.Class clz);

    /**
     * Returns the CodeBase for this ValueHandler.  This is used by
     * the ORB runtime.  The server sends the service context containing
     * the IOR for this CodeBase on the first GIOP reply.  The client
     * does the same on the first GIOP request.
     * <p>
     *  返回此ValueHandler的CodeBase。这由ORB运行时使用。服务器在第一GIOP答复中发送包含该CodeBase的IOR的服务上下文。客户端在第一个GIOP请求上执行相同操作。
     * 
     * 
     * @return the SendingContext.CodeBase of this ValueHandler.
     **/
    org.omg.SendingContext.RunTime getRunTimeCodeBase();

    /**
     * If the value contains a <code>writeReplace</code> method then the result
     * is returned.  Otherwise, the value itself is returned.
     * <p>
     *  如果值包含<code> writeReplace </code>方法,则返回结果。否则,将返回值本身。
     * 
     * @param value the value to be marshaled.
     * @return the true value to marshal on the wire.
     **/
    java.io.Serializable writeReplace(java.io.Serializable value);

}
