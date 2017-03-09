/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * <code>RemoteRef</code> represents the handle for a remote object. A
 * <code>RemoteStub</code> uses a remote reference to carry out a
 * remote method invocation to a remote object.
 *
 * <p>
 *  <code> RemoteRef </code>表示远程对象的句柄。 <code> RemoteStub </code>使用远程引用对远程对象执行远程方法调用。
 * 
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @see     java.rmi.server.RemoteStub
 */
public interface RemoteRef extends java.io.Externalizable {

    /** indicate compatibility with JDK 1.1.x version of class. */
    static final long serialVersionUID = 3632638527362204081L;

    /**
     * Initialize the server package prefix: assumes that the
     * implementation of server ref classes (e.g., UnicastRef,
     * UnicastServerRef) are located in the package defined by the
     * prefix.
     * <p>
     *  初始化服务器包前缀：假定服务器ref类(例如,UnicastRef,UnicastServerRef)的实现位于由前缀定义的包中。
     * 
     */
    final static String packagePrefix = "sun.rmi.server";

    /**
     * Invoke a method. This form of delegating method invocation
     * to the reference allows the reference to take care of
     * setting up the connection to the remote host, marshaling
     * some representation for the method and parameters, then
     * communicating the method invocation to the remote host.
     * This method either returns the result of a method invocation
     * on the remote object which resides on the remote host or
     * throws a RemoteException if the call failed or an
     * application-level exception if the remote invocation throws
     * an exception.
     *
     * <p>
     *  调用方法。这种形式的委托方法调用到引用允许引用负责设置到远程主机的连接,编排方法和参数的一些表示,然后将方法调用传送到远程主机。
     * 此方法返回驻留在远程主机上的远程对象上的方法调用的结果,如果调用失败则抛出RemoteException异常,如果远程调用抛出异常,则抛出应用程序级异常。
     * 
     * 
     * @param obj the object that contains the RemoteRef (e.g., the
     *            RemoteStub for the object.
     * @param method the method to be invoked
     * @param params the parameter list
     * @param opnum  a hash that may be used to represent the method
     * @return result of remote method invocation
     * @exception Exception if any exception occurs during remote method
     * invocation
     * @since 1.2
     */
    Object invoke(Remote obj,
                  java.lang.reflect.Method method,
                  Object[] params,
                  long opnum)
        throws Exception;

    /**
     * Creates an appropriate call object for a new remote method
     * invocation on this object.  Passing operation array and index,
     * allows the stubs generator to assign the operation indexes and
     * interpret them. The remote reference may need the operation to
     * encode in the call.
     *
     * <p>
     *  为此对象上的新远程方法调用创建适当的调用对象。传递操作数组和索引,允许存根生成器分配操作索引并解释它们。远程引用可能需要在调用中进行编码的操作。
     * 
     * 
     * @since JDK1.1
     * @deprecated 1.2 style stubs no longer use this method. Instead of
     * using a sequence of method calls on the stub's the remote reference
     * (<code>newCall</code>, <code>invoke</code>, and <code>done</code>), a
     * stub uses a single method, <code>invoke(Remote, Method, Object[],
     * int)</code>, on the remote reference to carry out parameter
     * marshalling, remote method executing and unmarshalling of the return
     * value.
     *
     * @param obj remote stub through which to make call
     * @param op array of stub operations
     * @param opnum operation number
     * @param hash stub/skeleton interface hash
     * @return call object representing remote call
     * @throws RemoteException if failed to initiate new remote call
     * @see #invoke(Remote,java.lang.reflect.Method,Object[],long)
     */
    @Deprecated
    RemoteCall newCall(RemoteObject obj, Operation[] op, int opnum, long hash)
        throws RemoteException;

    /**
     * Executes the remote call.
     *
     * Invoke will raise any "user" exceptions which
     * should pass through and not be caught by the stub.  If any
     * exception is raised during the remote invocation, invoke should
     * take care of cleaning up the connection before raising the
     * "user" or remote exception.
     *
     * <p>
     *  执行远程调用。
     * 
     * Invoke将引发应该通过但不被存根捕获的任何"用户"异常。如果在远程调用期间引发任何异常,调用应该在提高"用户"或远程异常之前清理连接。
     * 
     * 
     * @since JDK1.1
     * @deprecated 1.2 style stubs no longer use this method. Instead of
     * using a sequence of method calls to the remote reference
     * (<code>newCall</code>, <code>invoke</code>, and <code>done</code>), a
     * stub uses a single method, <code>invoke(Remote, Method, Object[],
     * int)</code>, on the remote reference to carry out parameter
     * marshalling, remote method executing and unmarshalling of the return
     * value.
     *
     * @param call object representing remote call
     * @throws Exception if any exception occurs during remote method
     * @see #invoke(Remote,java.lang.reflect.Method,Object[],long)
     */
    @Deprecated
    void invoke(RemoteCall call) throws Exception;

    /**
     * Allows the remote reference to clean up (or reuse) the connection.
     * Done should only be called if the invoke returns successfully
     * (non-exceptionally) to the stub.
     *
     * <p>
     *  允许远程引用清除(或重用)连接。仅当调用成功返回(非例外)到存根时才应调用Done。
     * 
     * 
     * @since JDK1.1
     * @deprecated 1.2 style stubs no longer use this method. Instead of
     * using a sequence of method calls to the remote reference
     * (<code>newCall</code>, <code>invoke</code>, and <code>done</code>), a
     * stub uses a single method, <code>invoke(Remote, Method, Object[],
     * int)</code>, on the remote reference to carry out parameter
     * marshalling, remote method executing and unmarshalling of the return
     * value.
     *
     * @param call object representing remote call
     * @throws RemoteException if remote error occurs during call cleanup
     * @see #invoke(Remote,java.lang.reflect.Method,Object[],long)
     */
    @Deprecated
    void done(RemoteCall call) throws RemoteException;

    /**
     * Returns the class name of the ref type to be serialized onto
     * the stream 'out'.
     * <p>
     *  返回要序列化到流"out"上的引用类型的类名。
     * 
     * 
     * @param out the output stream to which the reference will be serialized
     * @return the class name (without package qualification) of the reference
     * type
     * @since JDK1.1
     */
    String getRefClass(java.io.ObjectOutput out);

    /**
     * Returns a hashcode for a remote object.  Two remote object stubs
     * that refer to the same remote object will have the same hash code
     * (in order to support remote objects as keys in hash tables).
     *
     * <p>
     *  返回远程对象的哈希码。引用同一远程对象的两个远程对象存根将具有相同的哈希码(以支持远程对象作为哈希表中的密钥)。
     * 
     * 
     * @return remote object hashcode
     * @see             java.util.Hashtable
     * @since JDK1.1
     */
    int remoteHashCode();

    /**
     * Compares two remote objects for equality.
     * Returns a boolean that indicates whether this remote object is
     * equivalent to the specified Object. This method is used when a
     * remote object is stored in a hashtable.
     * <p>
     *  比较两个远程对象是否相等。返回一个布尔值,指示此远程对象是否等同于指定的对象。当远程对象存储在散列表中时,将使用此方法。
     * 
     * 
     * @param   obj     the Object to compare with
     * @return  true if these Objects are equal; false otherwise.
     * @see             java.util.Hashtable
     * @since JDK1.1
     */
    boolean remoteEquals(RemoteRef obj);

    /**
     * Returns a String that represents the reference of this remote
     * object.
     * <p>
     *  返回表示此远程对象的引用的字符串。
     * 
     * @return string representing remote object reference
     * @since JDK1.1
     */
    String remoteToString();

}
