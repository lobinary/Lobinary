/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2002, Oracle and/or its affiliates. All rights reserved.
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
import sun.rmi.server.UnicastServerRef;
import sun.rmi.runtime.Log;

/**
 * The <code>RemoteServer</code> class is the common superclass to server
 * implementations and provides the framework to support a wide range
 * of remote reference semantics.  Specifically, the functions needed
 * to create and export remote objects (i.e. to make them remotely
 * available) are provided abstractly by <code>RemoteServer</code> and
 * concretely by its subclass(es).
 *
 * <p>
 *  <code> RemoteServer </code>类是服务器实现的常用超类,并提供了支持各种远程引用语义的框架。
 * 具体来说,创建和导出远程对象所需的函数(即使它们远程可用)由<code> RemoteServer </code>抽象地提供,具体地由其子类提供。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 */
public abstract class RemoteServer extends RemoteObject
{
    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = -4100238210092549637L;

    /**
     * Constructs a <code>RemoteServer</code>.
     * <p>
     *  构造一个<code> RemoteServer </code>。
     * 
     * 
     * @since JDK1.1
     */
    protected RemoteServer() {
        super();
    }

    /**
     * Constructs a <code>RemoteServer</code> with the given reference type.
     *
     * <p>
     *  构造具有给定引用类型的<code> RemoteServer </code>。
     * 
     * 
     * @param ref the remote reference
     * @since JDK1.1
     */
    protected RemoteServer(RemoteRef ref) {
        super(ref);
    }

    /**
     * Returns a string representation of the client host for the
     * remote method invocation being processed in the current thread.
     *
     * <p>
     *  返回当前线程中正在处理的远程方法调用的客户端主机的字符串表示形式。
     * 
     * 
     * @return  a string representation of the client host
     *
     * @throws  ServerNotActiveException if no remote method invocation
     * is being processed in the current thread
     *
     * @since   JDK1.1
     */
    public static String getClientHost() throws ServerNotActiveException {
        return sun.rmi.transport.tcp.TCPTransport.getClientHost();
    }

    /**
     * Log RMI calls to the output stream <code>out</code>. If
     * <code>out</code> is <code>null</code>, call logging is turned off.
     *
     * <p>If there is a security manager, its
     * <code>checkPermission</code> method will be invoked with a
     * <code>java.util.logging.LoggingPermission("control")</code>
     * permission; this could result in a <code>SecurityException</code>.
     *
     * <p>
     *  日志RMI调用输出流<code> out </code>。如果<code> out </code>是<code> null </code>,则呼叫记录被关闭。
     * 
     *  <p>如果有安全管理器,其<code> checkPermission </code>方法将使用<code> java.util.logging.LoggingPermission("control"
     * )</code>权限调用;这可能导致<code> SecurityException </code>。
     * 
     * @param   out the output stream to which RMI calls should be logged
     * @throws  SecurityException  if there is a security manager and
     *          the invocation of its <code>checkPermission</code> method
     *          fails
     * @see #getLog
     * @since JDK1.1
     */
    public static void setLog(java.io.OutputStream out)
    {
        logNull = (out == null);
        UnicastServerRef.callLog.setOutputStream(out);
    }

    /**
     * Returns stream for the RMI call log.
     * <p>
     * 
     * 
     * @return the call log
     * @see #setLog
     * @since JDK1.1
     */
    public static java.io.PrintStream getLog()
    {
        return (logNull ? null : UnicastServerRef.callLog.getPrintStream());
    }

    // initialize log status
    private static boolean logNull = !UnicastServerRef.logCalls;
}
