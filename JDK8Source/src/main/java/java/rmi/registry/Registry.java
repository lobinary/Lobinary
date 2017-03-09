/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * <code>Registry</code> is a remote interface to a simple remote
 * object registry that provides methods for storing and retrieving
 * remote object references bound with arbitrary string names.  The
 * <code>bind</code>, <code>unbind</code>, and <code>rebind</code>
 * methods are used to alter the name bindings in the registry, and
 * the <code>lookup</code> and <code>list</code> methods are used to
 * query the current name bindings.
 *
 * <p>In its typical usage, a <code>Registry</code> enables RMI client
 * bootstrapping: it provides a simple means for a client to obtain an
 * initial reference to a remote object.  Therefore, a registry's
 * remote object implementation is typically exported with a
 * well-known address, such as with a well-known {@link
 * java.rmi.server.ObjID#REGISTRY_ID ObjID} and TCP port number
 * (default is {@link #REGISTRY_PORT 1099}).
 *
 * <p>The {@link LocateRegistry} class provides a programmatic API for
 * constructing a bootstrap reference to a <code>Registry</code> at a
 * remote address (see the static <code>getRegistry</code> methods)
 * and for creating and exporting a <code>Registry</code> in the
 * current VM on a particular local address (see the static
 * <code>createRegistry</code> methods).
 *
 * <p>A <code>Registry</code> implementation may choose to restrict
 * access to some or all of its methods (for example, methods that
 * mutate the registry's bindings may be restricted to calls
 * originating from the local host).  If a <code>Registry</code>
 * method chooses to deny access for a given invocation, its
 * implementation may throw {@link java.rmi.AccessException}, which
 * (because it extends {@link java.rmi.RemoteException}) will be
 * wrapped in a {@link java.rmi.ServerException} when caught by a
 * remote client.
 *
 * <p>The names used for bindings in a <code>Registry</code> are pure
 * strings, not parsed.  A service which stores its remote reference
 * in a <code>Registry</code> may wish to use a package name as a
 * prefix in the name binding to reduce the likelihood of name
 * collisions in the registry.
 *
 * <p>
 *  <code>注册表</code>是一个远程对象注册表的远程接口,提供存储和检索使用任意字符串名称绑定的远程对象引用的方法。
 *  <code> bind </code>,<code> unbind </code>和<code> rebind </code>方法用于更改注册表中的名称绑定,和<code> list </code>方
 * 法用于查询当前名称绑定。
 *  <code>注册表</code>是一个远程对象注册表的远程接口,提供存储和检索使用任意字符串名称绑定的远程对象引用的方法。
 * 
 *  <p>在其典型用法中,<code>注册表</code>启用RMI客户端引导：它为客户端提供了一种获取对远程对象的初始引用的简单方法。
 * 因此,注册表的远程对象实现通常使用公知的地址导出,例如使用众所周知的{@link java.rmi.server.ObjID#REGISTRY_ID ObjID}和TCP端口号(默认值为{@link #REGISTRY_PORT 1099}
 * )。
 *  <p>在其典型用法中,<code>注册表</code>启用RMI客户端引导：它为客户端提供了一种获取对远程对象的初始引用的简单方法。
 * 
 *  <p> {@link LocateRegistry}类提供了一个编程API,用于在远程地址(参见静态<code> getRegistry </code>方法)中构建对<code>注册表</code>的
 * 引导引用,并在特定本地地址上导出当前VM中的<code>注册表</code>(请参阅static <code> createRegistry </code>方法)。
 * 
 * <p> <code>注册表</code>实现可以选择限制对其部分或全部方法的访问(例如,将注册表绑定变异的方法可能限于源自本地主机的调用)。
 * 如果<code> Registry </code>方法选择拒绝给定调用的访问,其实现可能会抛出{@link java.rmi.AccessException},因为它会扩展{@link java.rmi.RemoteException}
 * 被远程客户端捕获时包装在{@link java.rmi.ServerException}中。
 * <p> <code>注册表</code>实现可以选择限制对其部分或全部方法的访问(例如,将注册表绑定变异的方法可能限于源自本地主机的调用)。
 * 
 * 
 * @author      Ann Wollrath
 * @author      Peter Jones
 * @since       JDK1.1
 * @see         LocateRegistry
 */
public interface Registry extends Remote {

    /** Well known port for registry. */
    public static final int REGISTRY_PORT = 1099;

    /**
     * Returns the remote reference bound to the specified
     * <code>name</code> in this registry.
     *
     * <p>
     *  <p>在<code>注册表</code>中用于绑定的名称是纯字符串,不进行解析。
     * 将其远程引用存储在<code> Registry </code>中的服务可能希望在名称绑定中使用包名作为前缀,以减少注册表中名称冲突的可能性。
     * 
     * 
     * @param   name the name for the remote reference to look up
     *
     * @return  a reference to a remote object
     *
     * @throws  NotBoundException if <code>name</code> is not currently bound
     *
     * @throws  RemoteException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation
     *
     * @throws  AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws  NullPointerException if <code>name</code> is <code>null</code>
     */
    public Remote lookup(String name)
        throws RemoteException, NotBoundException, AccessException;

    /**
     * Binds a remote reference to the specified <code>name</code> in
     * this registry.
     *
     * <p>
     *  返回绑定到此注册表中指定的<code> name </code>的远程引用。
     * 
     * 
     * @param   name the name to associate with the remote reference
     * @param   obj a reference to a remote object (usually a stub)
     *
     * @throws  AlreadyBoundException if <code>name</code> is already bound
     *
     * @throws  RemoteException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws  AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws  NullPointerException if <code>name</code> is
     * <code>null</code>, or if <code>obj</code> is <code>null</code>
     */
    public void bind(String name, Remote obj)
        throws RemoteException, AlreadyBoundException, AccessException;

    /**
     * Removes the binding for the specified <code>name</code> in
     * this registry.
     *
     * <p>
     *  将远程引用绑定到此注册表中指定的<code>名称</code>。
     * 
     * 
     * @param   name the name of the binding to remove
     *
     * @throws  NotBoundException if <code>name</code> is not currently bound
     *
     * @throws  RemoteException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws  AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws  NullPointerException if <code>name</code> is <code>null</code>
     */
    public void unbind(String name)
        throws RemoteException, NotBoundException, AccessException;

    /**
     * Replaces the binding for the specified <code>name</code> in
     * this registry with the supplied remote reference.  If there is
     * an existing binding for the specified <code>name</code>, it is
     * discarded.
     *
     * <p>
     *  删除此注册表中指定的<code>名称</code>的绑定。
     * 
     * 
     * @param   name the name to associate with the remote reference
     * @param   obj a reference to a remote object (usually a stub)
     *
     * @throws  RemoteException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation (if
     * originating from a non-local host, for example)
     *
     * @throws  AccessException if this registry is local and it denies
     * the caller access to perform this operation
     *
     * @throws  NullPointerException if <code>name</code> is
     * <code>null</code>, or if <code>obj</code> is <code>null</code>
     */
    public void rebind(String name, Remote obj)
        throws RemoteException, AccessException;

    /**
     * Returns an array of the names bound in this registry.  The
     * array will contain a snapshot of the names bound in this
     * registry at the time of the given invocation of this method.
     *
     * <p>
     *  将此注册表中指定的<code> name </code>的绑定替换为提供的远程引用。如果指定的<code> name </code>存在绑定,它将被丢弃。
     * 
     * 
     * @return  an array of the names bound in this registry
     *
     * @throws  RemoteException if remote communication with the
     * registry failed; if exception is a <code>ServerException</code>
     * containing an <code>AccessException</code>, then the registry
     * denies the caller access to perform this operation
     *
     * @throws  AccessException if this registry is local and it denies
     * the caller access to perform this operation
     */
    public String[] list() throws RemoteException, AccessException;
}
