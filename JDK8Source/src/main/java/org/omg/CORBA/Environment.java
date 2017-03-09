/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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
 * A container (holder) for an exception that is used in <code>Request</code>
 * operations to make exceptions available to the client.  An
 * <code>Environment</code> object is created with the <code>ORB</code>
 * method <code>create_environment</code>.
 *
 * <p>
 *  用于在<code>请求</code>操作中用于向客户端提供例外的异常的容器(持有者)。
 * 使用<code> ORB </code>方法<code> create_environment </code>创建<code> Environment </code>对象。
 * 
 * 
 * @since   JDK1.2
 */

public abstract class Environment {

    /**
     * Retrieves the exception in this <code>Environment</code> object.
     *
     * <p>
     *  在此<code> Environment </code>对象中检索异常。
     * 
     * 
     * @return                  the exception in this <code>Environment</code> object
     */

    public abstract java.lang.Exception exception();

    /**
     * Inserts the given exception into this <code>Environment</code> object.
     *
     * <p>
     *  将给定的异常插入到此<code> Environment </code>对象中。
     * 
     * 
     * @param except            the exception to be set
     */

    public abstract void exception(java.lang.Exception except);

    /**
     * Clears this <code>Environment</code> object of its exception.
     * <p>
     *  清除此异常的<code> Environment </code>对象。
     */

    public abstract void clear();

}
