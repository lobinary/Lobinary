/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.remote.rmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <p>RMI object used to establish connections to an RMI connector.
 * There is one Remote object implementing this interface for each RMI
 * connector.</p>
 *
 * <p>User code does not usually refer to this interface.  It is
 * specified as part of the public API so that different
 * implementations of that API will interoperate.</p>
 *
 * <p>
 *  <p>用于建立与RMI连接器的连接的RMI对象。有一个Remote对象为每个RMI连接器实现此接口。</p>
 * 
 *  <p>用户代码通常不会引用此界面。它被指定为公共API的一部分,以便该API的不同实现将互操作。</p>
 * 
 * 
 * @since 1.5
 */
public interface RMIServer extends Remote {
    /**
     * <p>The version of the RMI Connector Protocol understood by this
     * connector server.  This is a string with the following format:</p>
     *
     * <pre>
     * <em>protocol-version</em> <em>implementation-name</em>
     * </pre>
     *
     * <p>The <code><em>protocol-version</em></code> is a series of
     * two or more non-negative integers separated by periods
     * (<code>.</code>).  An implementation of the version described
     * by this documentation must use the string <code>1.0</code>
     * here.</p>
     *
     * <p>After the protocol version there must be a space, followed
     * by the implementation name.  The format of the implementation
     * name is unspecified.  It is recommended that it include an
     * implementation version number.  An implementation can use an
     * empty string as its implementation name, for example for
     * security reasons.</p>
     *
     * <p>
     *  <p>此连接器服务器了解的RMI连接器协议的版本。这是一个具有以下格式的字符串：</p>
     * 
     * <pre>
     *  <em> protocol-version </em> <em> implementation-name </em>
     * </pre>
     * 
     *  <p> <code> <em> protocol-version </em> </code>是一系列两个或多个非负整数,以句点分隔(<code>。</code>)。
     * 
     * @return a string with the format described here.
     *
     * @exception RemoteException if there is a communication
     * exception during the remote method call.
     */
    public String getVersion() throws RemoteException;

    /**
     * <p>Makes a new connection through this RMI connector.  Each
     * remote client calls this method to obtain a new RMI object
     * representing its connection.</p>
     *
     * <p>
     * 本文档描述的版本的实现必须使用<code> 1.0 </code>字符串。</p>。
     * 
     *  <p>协议版本后面必须有一个空格,后面跟着实现名称。实施名称的格式未指定。建议它包括实现版本号。实施可以使用空字符串作为其实现名称,例如出于安全原因。</p>
     * 
     * 
     * @param credentials this object specifies the user-defined credentials
     * to be passed in to the server in order to authenticate the user before
     * creating the <code>RMIConnection</code>.  Can be null.
     *
     * @return the newly-created connection object.
     *
     * @exception IOException if the new client object cannot be
     * created or exported, or if there is a communication exception
     * during the remote method call.
     *
     * @exception SecurityException if the given credentials do not
     * allow the server to authenticate the caller successfully.
     */
    public RMIConnection newClient(Object credentials) throws IOException;
}
