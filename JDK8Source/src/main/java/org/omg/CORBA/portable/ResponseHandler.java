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
package org.omg.CORBA.portable;

/**
This interface is supplied by an ORB to a servant at invocation time and allows
the servant to later retrieve an OutputStream for returning the invocation results.
/* <p>
/*  此接口在调用时由ORB提供给服务方,并允许服务方稍后检索用于返回调用结果的OutputStream。
/* 
*/

public interface ResponseHandler {
    /**
     * Called by the servant during a method invocation. The servant
     * should call this method to create a reply marshal buffer if no
     * exception occurred.
     *
     * <p>
     *  在方法调用期间由服务方调用。如果没有发生异常,服务方应该调用此方法来创建一个回复封送缓冲区。
     * 
     * 
     * @return an OutputStream suitable for marshalling the reply.
     *
     * @see <a href="package-summary.html#unimpl"><code>portable</code>
     * package comments for unimplemented features</a>
     */
    OutputStream createReply();

    /**
     * Called by the servant during a method invocation. The servant
     * should call this method to create a reply marshal buffer if a
     * user exception occurred.
     *
     * <p>
     *  在方法调用期间由服务方调用。如果发生用户异常,服务方应调用此方法来创建应答封送缓冲区。
     * 
     * @return an OutputStream suitable for marshalling the exception
     * ID and the user exception body.
     */
    OutputStream createExceptionReply();
}
