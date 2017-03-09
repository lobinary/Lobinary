/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * A class that contains user exceptions returned by the server.
 * When the client uses the DII to make an invocation, any user exception
 * returned from the server is enclosed in an <code>Any</code> object contained in the
 * <code>UnknownUserException</code> object. This is available from the
 * <code>Environment</code> object returned by the method <code>Request.env</code>.
 *
 * <p>
 *  包含服务器返回的用户异常的类。
 * 当客户端使用DII进行调用时,从服务器返回的任何用户异常都被包含在<code> UnknownUserException </code>对象中的<code> Any </code>对象中。
 * 这可以从方法<code> Request.env </code>返回的<code> Environment </code>对象中获得。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 * @see Request
 */

public final class UnknownUserException extends UserException {

    /** The <code>Any</code> instance that contains the actual user exception thrown
     *  by the server.
     * <p>
     *  由服务器。
     * 
     * 
     * @serial
     */
    public Any except;

    /**
     * Constructs an <code>UnknownUserException</code> object.
     * <p>
     *  构造一个<code> UnknownUserException </code>对象。
     * 
     */
    public UnknownUserException() {
        super();
    }

    /**
     * Constructs an <code>UnknownUserException</code> object that contains the given
     * <code>Any</code> object.
     *
     * <p>
     *  构造包含给定的<code> Any </code>对象的<code> UnknownUserException </code>对象。
     * 
     * @param a an <code>Any</code> object that contains a user exception returned
     *         by the server
     */
    public UnknownUserException(Any a) {
        super();
        except = a;
    }
}
