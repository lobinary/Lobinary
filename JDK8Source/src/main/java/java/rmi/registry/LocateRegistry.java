/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.registry;

import java.rmi.RemoteException;
import java.rmi.server.ObjID;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import sun.rmi.registry.RegistryImpl;
import sun.rmi.server.UnicastRef2;
import sun.rmi.server.UnicastRef;
import sun.rmi.server.Util;
import sun.rmi.transport.LiveRef;
import sun.rmi.transport.tcp.TCPEndpoint;

/**
 * <code>LocateRegistry</code> is used to obtain a reference to a bootstrap
 * remote object registry on a particular host (including the local host), or
 * to create a remote object registry that accepts calls on a specific port.
 *
 * <p> Note that a <code>getRegistry</code> call does not actually make a
 * connection to the remote host.  It simply creates a local reference to
 * the remote registry and will succeed even if no registry is running on
 * the remote host.  Therefore, a subsequent method invocation to a remote
 * registry returned as a result of this method may fail.
 *
 * <p>
 *  <code> LocateRegistry </code>用于获取对特定主机(包括本地主机)上的引导远程对象注册表的引用,或者创建接受特定端口上的调用的远程对象注册表。
 * 
 *  <p>请注意,<code> getRegistry </code>调用实际上不会建立到远程主机的连接。它只是创建一个本地引用远程注册表,并将成功,即使没有注册表在远程主机上运行。
 * 因此,作为此方法的结果返回的对远程注册表的后续方法调用可能会失败。
 * 
 * 
 * @author  Ann Wollrath
 * @author  Peter Jones
 * @since   JDK1.1
 * @see     java.rmi.registry.Registry
 */
public final class LocateRegistry {

    /**
     * Private constructor to disable public construction.
     * <p>
     *  私人建设者禁止公共建设。
     * 
     */
    private LocateRegistry() {}

    /**
     * Returns a reference to the the remote object <code>Registry</code> for
     * the local host on the default registry port of 1099.
     *
     * <p>
     *  返回对默认注册表端口为1099的本地主机的远程对象<code> Registry </code>的引用。
     * 
     * 
     * @return reference (a stub) to the remote object registry
     * @exception RemoteException if the reference could not be created
     * @since JDK1.1
     */
    public static Registry getRegistry()
        throws RemoteException
    {
        return getRegistry(null, Registry.REGISTRY_PORT);
    }

    /**
     * Returns a reference to the the remote object <code>Registry</code> for
     * the local host on the specified <code>port</code>.
     *
     * <p>
     *  返回对指定<code> port </code>上的本地主机的远程对象<code> Registry </code>的引用。
     * 
     * 
     * @param port port on which the registry accepts requests
     * @return reference (a stub) to the remote object registry
     * @exception RemoteException if the reference could not be created
     * @since JDK1.1
     */
    public static Registry getRegistry(int port)
        throws RemoteException
    {
        return getRegistry(null, port);
    }

    /**
     * Returns a reference to the remote object <code>Registry</code> on the
     * specified <code>host</code> on the default registry port of 1099.  If
     * <code>host</code> is <code>null</code>, the local host is used.
     *
     * <p>
     *  在默认注册表端口1099上返回对指定的<code> host </code>上远程对象<code>注册表</code>的引用。
     * 如果<code> host </code>为<code> null <代码>,则使用本地主机。
     * 
     * 
     * @param host host for the remote registry
     * @return reference (a stub) to the remote object registry
     * @exception RemoteException if the reference could not be created
     * @since JDK1.1
     */
    public static Registry getRegistry(String host)
        throws RemoteException
    {
        return getRegistry(host, Registry.REGISTRY_PORT);
    }

    /**
     * Returns a reference to the remote object <code>Registry</code> on the
     * specified <code>host</code> and <code>port</code>. If <code>host</code>
     * is <code>null</code>, the local host is used.
     *
     * <p>
     *  返回对指定的<code> host </code>和<code> port </code>上的远程对象<code> Registry </code>的引用。
     * 如果<code> host </code>是<code> null </code>,则使用本地主机。
     * 
     * 
     * @param host host for the remote registry
     * @param port port on which the registry accepts requests
     * @return reference (a stub) to the remote object registry
     * @exception RemoteException if the reference could not be created
     * @since JDK1.1
     */
    public static Registry getRegistry(String host, int port)
        throws RemoteException
    {
        return getRegistry(host, port, null);
    }

    /**
     * Returns a locally created remote reference to the remote object
     * <code>Registry</code> on the specified <code>host</code> and
     * <code>port</code>.  Communication with this remote registry will
     * use the supplied <code>RMIClientSocketFactory</code> <code>csf</code>
     * to create <code>Socket</code> connections to the registry on the
     * remote <code>host</code> and <code>port</code>.
     *
     * <p>
     * 返回指定的<code> host </code>和<code> port </code>上的远程对象<code> Registry </code>的本地创建的远程引用。
     * 与此远程注册表的通信将使用提供的<code> RMIClientSocketFactory </code> <code> csf </code>来创建到远程<code>主机</code>和<code> 
     * port </code>。
     * 返回指定的<code> host </code>和<code> port </code>上的远程对象<code> Registry </code>的本地创建的远程引用。
     * 
     * 
     * @param host host for the remote registry
     * @param port port on which the registry accepts requests
     * @param csf  client-side <code>Socket</code> factory used to
     *      make connections to the registry.  If <code>csf</code>
     *      is null, then the default client-side <code>Socket</code>
     *      factory will be used in the registry stub.
     * @return reference (a stub) to the remote registry
     * @exception RemoteException if the reference could not be created
     * @since 1.2
     */
    public static Registry getRegistry(String host, int port,
                                       RMIClientSocketFactory csf)
        throws RemoteException
    {
        Registry registry = null;

        if (port <= 0)
            port = Registry.REGISTRY_PORT;

        if (host == null || host.length() == 0) {
            // If host is blank (as returned by "file:" URL in 1.0.2 used in
            // java.rmi.Naming), try to convert to real local host name so
            // that the RegistryImpl's checkAccess will not fail.
            try {
                host = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                // If that failed, at least try "" (localhost) anyway...
                host = "";
            }
        }

        /*
         * Create a proxy for the registry with the given host, port, and
         * client socket factory.  If the supplied client socket factory is
         * null, then the ref type is a UnicastRef, otherwise the ref type
         * is a UnicastRef2.  If the property
         * java.rmi.server.ignoreStubClasses is true, then the proxy
         * returned is an instance of a dynamic proxy class that implements
         * the Registry interface; otherwise the proxy returned is an
         * instance of the pregenerated stub class for RegistryImpl.
         * <p>
         *  为给定的主机,端口和客户端套接字工厂创建注册表的代理。如果提供的客户端套接字工厂为null,则引用类型为UnicastRef,否则引用类型为UnicastRef2。
         * 如果属性java.rmi.server.ignoreStubClasses为true,则返回的代理是实现注册表接口的动态代理类的实例;否则返回的代理是RegistryImpl的预生成的存根类的实例。
         * 
         * 
         **/
        LiveRef liveRef =
            new LiveRef(new ObjID(ObjID.REGISTRY_ID),
                        new TCPEndpoint(host, port, csf, null),
                        false);
        RemoteRef ref =
            (csf == null) ? new UnicastRef(liveRef) : new UnicastRef2(liveRef);

        return (Registry) Util.createProxy(RegistryImpl.class, ref, false);
    }

    /**
     * Creates and exports a <code>Registry</code> instance on the local
     * host that accepts requests on the specified <code>port</code>.
     *
     * <p>The <code>Registry</code> instance is exported as if the static
     * {@link UnicastRemoteObject#exportObject(Remote,int)
     * UnicastRemoteObject.exportObject} method is invoked, passing the
     * <code>Registry</code> instance and the specified <code>port</code> as
     * arguments, except that the <code>Registry</code> instance is
     * exported with a well-known object identifier, an {@link ObjID}
     * instance constructed with the value {@link ObjID#REGISTRY_ID}.
     *
     * <p>
     *  在本地主机上创建并导出接受指定<code> port </code>上请求的<code>注册表</code>实例。
     * 
     *  <p> <code>注册表</code>实例被导出,就像调用了静态的{@link UnicastRemoteObject#exportObject(Remote,int)UnicastRemoteObject.exportObject}
     * 方法,传递<code>注册表</code>指定的<code> port </code>作为参数,除了<code>注册表</code>实例使用已知的对象标识符导出,{@link ObjID}实例使用值{@link ObjID #REGISTRY_ID}
     * 。
     * 
     * 
     * @param port the port on which the registry accepts requests
     * @return the registry
     * @exception RemoteException if the registry could not be exported
     * @since JDK1.1
     **/
    public static Registry createRegistry(int port) throws RemoteException {
        return new RegistryImpl(port);
    }

    /**
     * Creates and exports a <code>Registry</code> instance on the local
     * host that uses custom socket factories for communication with that
     * instance.  The registry that is created listens for incoming
     * requests on the given <code>port</code> using a
     * <code>ServerSocket</code> created from the supplied
     * <code>RMIServerSocketFactory</code>.
     *
     * <p>The <code>Registry</code> instance is exported as if
     * the static {@link
     * UnicastRemoteObject#exportObject(Remote,int,RMIClientSocketFactory,RMIServerSocketFactory)
     * UnicastRemoteObject.exportObject} method is invoked, passing the
     * <code>Registry</code> instance, the specified <code>port</code>, the
     * specified <code>RMIClientSocketFactory</code>, and the specified
     * <code>RMIServerSocketFactory</code> as arguments, except that the
     * <code>Registry</code> instance is exported with a well-known object
     * identifier, an {@link ObjID} instance constructed with the value
     * {@link ObjID#REGISTRY_ID}.
     *
     * <p>
     * 在使用自定义套接字工厂与该实例进行通信的本地主机上创建并导出<code>注册表</code>实例。
     * 创建的注册表使用从提供的<code> RMIServerSocketFactory </code>创建的<code> ServerSocket </code>监听给定<code> port </code>
     * 上的传入请求。
     * 在使用自定义套接字工厂与该实例进行通信的本地主机上创建并导出<code>注册表</code>实例。
     * 
     * 
     * @param port port on which the registry accepts requests
     * @param csf  client-side <code>Socket</code> factory used to
     *      make connections to the registry
     * @param ssf  server-side <code>ServerSocket</code> factory
     *      used to accept connections to the registry
     * @return the registry
     * @exception RemoteException if the registry could not be exported
     * @since 1.2
     **/
    public static Registry createRegistry(int port,
                                          RMIClientSocketFactory csf,
                                          RMIServerSocketFactory ssf)
        throws RemoteException
    {
        return new RegistryImpl(port, csf, ssf);
    }
}
