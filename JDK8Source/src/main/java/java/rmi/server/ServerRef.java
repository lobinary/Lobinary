/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

import java.rmi.*;

/**
 * A ServerRef represents the server-side handle for a remote object
 * implementation.
 *
 * <p>
 *  ServerRef表示远程对象实现的服务器端句柄。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @deprecated No replacement. This interface is unused and is obsolete.
 */
@Deprecated
public interface ServerRef extends RemoteRef {

    /** indicate compatibility with JDK 1.1.x version of class. */
    static final long serialVersionUID = -4557750989390278438L;

    /**
     * Creates a client stub object for the supplied Remote object.
     * If the call completes successfully, the remote object should
     * be able to accept incoming calls from clients.
     * <p>
     *  为提供的Remote对象创建客户端存根对象。如果呼叫成功完成,远程对象应该能够接受来自客户端的传入呼叫。
     * 
     * 
     * @param obj the remote object implementation
     * @param data information necessary to export the object
     * @return the stub for the remote object
     * @exception RemoteException if an exception occurs attempting
     * to export the object (e.g., stub class could not be found)
     * @since JDK1.1
     */
    RemoteStub exportObject(Remote obj, Object data)
        throws RemoteException;

    /**
     * Returns the hostname of the current client.  When called from a
     * thread actively handling a remote method invocation the
     * hostname of the client is returned.
     * <p>
     *  返回当前客户端的主机名。当从主动处理远程方法调用的线程调用时,将返回客户端的主机名。
     * 
     * @return the client's host name
     * @exception ServerNotActiveException if called outside of servicing
     * a remote method invocation
     * @since JDK1.1
     */
    String getClientHost() throws ServerNotActiveException;
}
