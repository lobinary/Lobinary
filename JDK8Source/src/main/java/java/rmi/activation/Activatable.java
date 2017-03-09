/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.activation;

import java.rmi.MarshalledObject;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.UnknownGroupException;
import java.rmi.activation.UnknownObjectException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RemoteServer;
import sun.rmi.server.ActivatableServerRef;

/**
 * The <code>Activatable</code> class provides support for remote
 * objects that require persistent access over time and that
 * can be activated by the system.
 *
 * <p>For the constructors and static <code>exportObject</code> methods,
 * the stub for a remote object being exported is obtained as described in
 * {@link java.rmi.server.UnicastRemoteObject}.
 *
 * <p>An attempt to serialize explicitly an instance of this class will
 * fail.
 *
 * <p>
 *  <code> Activatable </code>类提供对需要随着时间的持续访问并且可以由系统激活的远程对象的支持。
 * 
 *  <p>对于构造函数和static <code> exportObject </code>方法,要导出的远程对象的存根是如{@link java.rmi.server.UnicastRemoteObject}
 * 中所述获取的。
 * 
 *  <p>尝试显式序列化此类的实例将失败。
 * 
 * 
 * @author      Ann Wollrath
 * @since       1.2
 * @serial      exclude
 */
public abstract class Activatable extends RemoteServer {

    private ActivationID id;
    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = -3120617863591563455L;

    /**
     * Constructs an activatable remote object by registering
     * an activation descriptor (with the specified location, data, and
     * restart mode) for this object, and exporting the object with the
     * specified port.
     *
     * <p><strong>Note:</strong> Using the <code>Activatable</code>
     * constructors that both register and export an activatable remote
     * object is strongly discouraged because the actions of registering
     * and exporting the remote object are <i>not</i> guaranteed to be
     * atomic.  Instead, an application should register an activation
     * descriptor and export a remote object separately, so that exceptions
     * can be handled properly.
     *
     * <p>This method invokes the {@link
     * #exportObject(Remote,String,MarshalledObject,boolean,int)
     * exportObject} method with this object, and the specified location,
     * data, restart mode, and port.  Subsequent calls to {@link #getID}
     * will return the activation identifier returned from the call to
     * <code>exportObject</code>.
     *
     * <p>
     *  通过注册此对象的激活描述符(具有指定的位置,数据和重新启动模式)并导出具有指定端口的对象,构造可激活的远程对象。
     * 
     *  <p> <strong>注意</strong>：强烈建议您不要使用<code>可激活</code>构造函数注册和导出可激活的远程对象,因为注册和导出远程对象的操作不是<i> </i>保证是原子的。
     * 相反,应用程序应该注册激活描述符并单独导出远程对象,以便正确处理异常。
     * 
     *  <p>此方法使用此对象以及指定的位置,数据,重新启动模式和端口调用{@link #exportObject(Remote,String,MarshalledObject,boolean,int)exportObject}
     * 方法。
     * 对{@link #getID}的后续调用将返回从调用<code> exportObject </code>返回的激活标识符。
     * 
     * 
     * @param location the location for classes for this object
     * @param data the object's initialization data
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @exception ActivationException if object registration fails.
     * @exception RemoteException if either of the following fails:
     * a) registering the object with the activation system or b) exporting
     * the object to the RMI runtime.
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation.
     * @since 1.2
     **/
    protected Activatable(String location,
                          MarshalledObject<?> data,
                          boolean restart,
                          int port)
        throws ActivationException, RemoteException
    {
        super();
        id = exportObject(this, location, data, restart, port);
    }

    /**
     * Constructs an activatable remote object by registering
     * an activation descriptor (with the specified location, data, and
     * restart mode) for this object, and exporting the object with the
     * specified port, and specified client and server socket factories.
     *
     * <p><strong>Note:</strong> Using the <code>Activatable</code>
     * constructors that both register and export an activatable remote
     * object is strongly discouraged because the actions of registering
     * and exporting the remote object are <i>not</i> guaranteed to be
     * atomic.  Instead, an application should register an activation
     * descriptor and export a remote object separately, so that exceptions
     * can be handled properly.
     *
     * <p>This method invokes the {@link
     * #exportObject(Remote,String,MarshalledObject,boolean,int,RMIClientSocketFactory,RMIServerSocketFactory)
     * exportObject} method with this object, and the specified location,
     * data, restart mode, port, and client and server socket factories.
     * Subsequent calls to {@link #getID} will return the activation
     * identifier returned from the call to <code>exportObject</code>.
     *
     * <p>
     * 通过注册此对象的激活描述符(具有指定的位置,数据和重新启动模式)并使用指定的端口以及指定的客户端和服务器套接字工厂导出对象,构造可激活的远程对象。
     * 
     *  <p> <strong>注意</strong>：强烈建议您不要使用<code>可激活</code>构造函数注册和导出可激活的远程对象,因为注册和导出远程对象的操作不是<i> </i>保证是原子的。
     * 相反,应用程序应该注册激活描述符并单独导出远程对象,以便正确处理异常。
     * 
     *  <p>此方法使用此对象以及指定的位置,数据,重新启动模式,端口以及客户端和服务器调用{@link #exportObject(Remote,String,MarshalledObject,boolean,int,RMIClientSocketFactory,RMIServerSocketFactory)exportObject}
     * 插座工厂。
     * 对{@link #getID}的后续调用将返回从调用<code> exportObject </code>返回的激活标识符。
     * 
     * 
     * @param location the location for classes for this object
     * @param data the object's initialization data
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @param csf the client-side socket factory for making calls to the
     * remote object
     * @param ssf the server-side socket factory for receiving remote calls
     * @exception ActivationException if object registration fails.
     * @exception RemoteException if either of the following fails:
     * a) registering the object with the activation system or b) exporting
     * the object to the RMI runtime.
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation.
     * @since 1.2
     **/
    protected Activatable(String location,
                          MarshalledObject<?> data,
                          boolean restart,
                          int port,
                          RMIClientSocketFactory csf,
                          RMIServerSocketFactory ssf)
        throws ActivationException, RemoteException
    {
        super();
        id = exportObject(this, location, data, restart, port, csf, ssf);
    }

    /**
     * Constructor used to activate/export the object on a specified
     * port. An "activatable" remote object must have a constructor that
     * takes two arguments: <ul>
     * <li>the object's activation identifier (<code>ActivationID</code>), and
     * <li>the object's initialization data (a <code>MarshalledObject</code>).
     * </ul><p>
     *
     * A concrete subclass of this class must call this constructor when it is
     * <i>activated</i> via the two parameter constructor described above. As
     * a side-effect of construction, the remote object is "exported"
     * to the RMI runtime (on the specified <code>port</code>) and is
     * available to accept incoming calls from clients.
     *
     * <p>
     *  用于在指定端口上激活/导出对象的构造函数。
     * 一个"可激活的"远程对象必须有一个构造函数,它接受两个参数：<ul> <li>对象的激活标识符(<code> ActivationID </code>)和<li>对象的初始化数据(<code> Mars
     * halledObject </code>)。
     *  用于在指定端口上激活/导出对象的构造函数。 </ul> <p>。
     * 
     * 这个类的一个具体子类必须通过上面描述的两个参数构造函数调用这个构造函数,当它被激活</i>时。
     * 作为构造的副作用,远程对象"导出"到RMI运行时(在指定的<code> port </code>),并且可用于接受来自客户端的传入呼叫。
     * 
     * 
     * @param id activation identifier for the object
     * @param port the port number on which the object is exported
     * @exception RemoteException if exporting the object to the RMI
     * runtime fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    protected Activatable(ActivationID id, int port)
        throws RemoteException
    {
        super();
        this.id = id;
        exportObject(this, id, port);
    }

    /**
     * Constructor used to activate/export the object on a specified
     * port. An "activatable" remote object must have a constructor that
     * takes two arguments: <ul>
     * <li>the object's activation identifier (<code>ActivationID</code>), and
     * <li>the object's initialization data (a <code>MarshalledObject</code>).
     * </ul><p>
     *
     * A concrete subclass of this class must call this constructor when it is
     * <i>activated</i> via the two parameter constructor described above. As
     * a side-effect of construction, the remote object is "exported"
     * to the RMI runtime (on the specified <code>port</code>) and is
     * available to accept incoming calls from clients.
     *
     * <p>
     *  用于在指定端口上激活/导出对象的构造函数。
     * 一个"可激活的"远程对象必须有一个构造函数,它接受两个参数：<ul> <li>对象的激活标识符(<code> ActivationID </code>)和<li>对象的初始化数据(<code> Mars
     * halledObject </code>)。
     *  用于在指定端口上激活/导出对象的构造函数。 </ul> <p>。
     * 
     *  这个类的一个具体子类必须通过上面描述的两个参数构造函数调用这个构造函数,当它被激活</i>时。
     * 作为构造的副作用,远程对象"导出"到RMI运行时(在指定的<code> port </code>),并且可用于接受来自客户端的传入呼叫。
     * 
     * 
     * @param id activation identifier for the object
     * @param port the port number on which the object is exported
     * @param csf the client-side socket factory for making calls to the
     * remote object
     * @param ssf the server-side socket factory for receiving remote calls
     * @exception RemoteException if exporting the object to the RMI
     * runtime fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    protected Activatable(ActivationID id, int port,
                          RMIClientSocketFactory csf,
                          RMIServerSocketFactory ssf)
        throws RemoteException
    {
        super();
        this.id = id;
        exportObject(this, id, port, csf, ssf);
    }

    /**
     * Returns the object's activation identifier.  The method is
     * protected so that only subclasses can obtain an object's
     * identifier.
     * <p>
     *  返回对象的激活标识符。该方法受到保护,因此只有子类可以获取对象的标识符。
     * 
     * 
     * @return the object's activation identifier
     * @since 1.2
     */
    protected ActivationID getID() {
        return id;
    }

    /**
     * Register an object descriptor for an activatable remote
     * object so that is can be activated on demand.
     *
     * <p>
     *  为可激活的远程对象注册对象描述符,以便可以根据需要激活。
     * 
     * 
     * @param desc  the object's descriptor
     * @return the stub for the activatable remote object
     * @exception UnknownGroupException if group id in <code>desc</code>
     * is not registered with the activation system
     * @exception ActivationException if activation system is not running
     * @exception RemoteException if remote call fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static Remote register(ActivationDesc desc)
        throws UnknownGroupException, ActivationException, RemoteException
    {
        // register object with activator.
        ActivationID id =
            ActivationGroup.getSystem().registerObject(desc);
        return sun.rmi.server.ActivatableRef.getStub(desc, id);
    }

    /**
     * Informs the system that the object with the corresponding activation
     * <code>id</code> is currently inactive. If the object is currently
     * active, the object is "unexported" from the RMI runtime (only if
     * there are no pending or in-progress calls)
     * so the that it can no longer receive incoming calls. This call
     * informs this VM's ActivationGroup that the object is inactive,
     * that, in turn, informs its ActivationMonitor. If this call
     * completes successfully, a subsequent activate request to the activator
     * will cause the object to reactivate. The operation may still
     * succeed if the object is considered active but has already
     * unexported itself.
     *
     * <p>
     * 通知系统具有相应激活<code> id </code>的对象当前处于非活动状态。如果对象当前处于活动状态,那么该对象将从RMI运行时"取消导出"(仅当没有挂起或进行中的调用时),因此它不能再接收呼入。
     * 此调用通知此VM的ActivationGroup对象不活动,反过来,通知其ActivationMonitor。如果此调用成功完成,则激活程序的后续激活请求将使对象重新激活。
     * 如果对象被认为是活动的,但是已经自己导出,操作仍然可以成功。
     * 
     * 
     * @param id the object's activation identifier
     * @return true if the operation succeeds (the operation will
     * succeed if the object in currently known to be active and is
     * either already unexported or is currently exported and has no
     * pending/executing calls); false is returned if the object has
     * pending/executing calls in which case it cannot be deactivated
     * @exception UnknownObjectException if object is not known (it may
     * already be inactive)
     * @exception ActivationException if group is not active
     * @exception RemoteException if call informing monitor fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static boolean inactive(ActivationID id)
        throws UnknownObjectException, ActivationException, RemoteException
    {
        return ActivationGroup.currentGroup().inactiveObject(id);
    }

    /**
     * Revokes previous registration for the activation descriptor
     * associated with <code>id</code>. An object can no longer be
     * activated via that <code>id</code>.
     *
     * <p>
     *  撤消与<code> id </code>相关联的激活描述符的先前注册。不能再通过<code> id </code>激活对象。
     * 
     * 
     * @param id the object's activation identifier
     * @exception UnknownObjectException if object (<code>id</code>) is unknown
     * @exception ActivationException if activation system is not running
     * @exception RemoteException if remote call to activation system fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static void unregister(ActivationID id)
        throws UnknownObjectException, ActivationException, RemoteException
    {
        ActivationGroup.getSystem().unregisterObject(id);
    }

    /**
     * Registers an activation descriptor (with the specified location,
     * data, and restart mode) for the specified object, and exports that
     * object with the specified port.
     *
     * <p><strong>Note:</strong> Using this method (as well as the
     * <code>Activatable</code> constructors that both register and export
     * an activatable remote object) is strongly discouraged because the
     * actions of registering and exporting the remote object are
     * <i>not</i> guaranteed to be atomic.  Instead, an application should
     * register an activation descriptor and export a remote object
     * separately, so that exceptions can be handled properly.
     *
     * <p>This method invokes the {@link
     * #exportObject(Remote,String,MarshalledObject,boolean,int,RMIClientSocketFactory,RMIServerSocketFactory)
     * exportObject} method with the specified object, location, data,
     * restart mode, and port, and <code>null</code> for both client and
     * server socket factories, and then returns the resulting activation
     * identifier.
     *
     * <p>
     *  注册指定对象的激活描述符(具有指定的位置,数据和重新启动模式),并使用指定的端口导出该对象。
     * 
     *  <p> <strong>注意</strong>：强烈建议不要使用此方法(以及注册和导出可激活远程对象的<code> Activatable </code>构造函数),因为注册和导出远程对象不是</i>
     * 保证是原子的。
     * 相反,应用程序应该注册激活描述符并单独导出远程对象,以便正确处理异常。
     * 
     * <p>此方法调用具有指定对象,位置,数据,重新启动模式和端口的{@link #exportObject(Remote,String,MarshalledObject,boolean,int,RMIClientSocketFactory,RMIServerSocketFactory)exportObject}
     * 方法, </code>用于客户端和服务器套接字工厂,然后返回生成的激活标识符。
     * 
     * 
     * @param obj the object being exported
     * @param location the object's code location
     * @param data the object's bootstrapping data
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @return the activation identifier obtained from registering the
     * descriptor, <code>desc</code>, with the activation system
     * the wrong group
     * @exception ActivationException if activation group is not active
     * @exception RemoteException if object registration or export fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     **/
    public static ActivationID exportObject(Remote obj,
                                            String location,
                                            MarshalledObject<?> data,
                                            boolean restart,
                                            int port)
        throws ActivationException, RemoteException
    {
        return exportObject(obj, location, data, restart, port, null, null);
    }

    /**
     * Registers an activation descriptor (with the specified location,
     * data, and restart mode) for the specified object, and exports that
     * object with the specified port, and the specified client and server
     * socket factories.
     *
     * <p><strong>Note:</strong> Using this method (as well as the
     * <code>Activatable</code> constructors that both register and export
     * an activatable remote object) is strongly discouraged because the
     * actions of registering and exporting the remote object are
     * <i>not</i> guaranteed to be atomic.  Instead, an application should
     * register an activation descriptor and export a remote object
     * separately, so that exceptions can be handled properly.
     *
     * <p>This method first registers an activation descriptor for the
     * specified object as follows. It obtains the activation system by
     * invoking the method {@link ActivationGroup#getSystem
     * ActivationGroup.getSystem}.  This method then obtains an {@link
     * ActivationID} for the object by invoking the activation system's
     * {@link ActivationSystem#registerObject registerObject} method with
     * an {@link ActivationDesc} constructed with the specified object's
     * class name, and the specified location, data, and restart mode.  If
     * an exception occurs obtaining the activation system or registering
     * the activation descriptor, that exception is thrown to the caller.
     *
     * <p>Next, this method exports the object by invoking the {@link
     * #exportObject(Remote,ActivationID,int,RMIClientSocketFactory,RMIServerSocketFactory)
     * exportObject} method with the specified remote object, the
     * activation identifier obtained from registration, the specified
     * port, and the specified client and server socket factories.  If an
     * exception occurs exporting the object, this method attempts to
     * unregister the activation identifier (obtained from registration) by
     * invoking the activation system's {@link
     * ActivationSystem#unregisterObject unregisterObject} method with the
     * activation identifier.  If an exception occurs unregistering the
     * identifier, that exception is ignored, and the original exception
     * that occurred exporting the object is thrown to the caller.
     *
     * <p>Finally, this method invokes the {@link
     * ActivationGroup#activeObject activeObject} method on the activation
     * group in this VM with the activation identifier and the specified
     * remote object, and returns the activation identifier to the caller.
     *
     * <p>
     *  注册指定对象的激活描述符(具有指定的位置,数据和重新启动模式),并导出具有指定端口的对象以及指定的客户端和服务器套接字工厂。
     * 
     *  <p> <strong>注意</strong>：强烈建议不要使用此方法(以及注册和导出可激活远程对象的<code> Activatable </code>构造函数),因为注册和导出远程对象不是</i>
     * 保证是原子的。
     * 相反,应用程序应该注册激活描述符并单独导出远程对象,以便正确处理异常。
     * 
     * <p>此方法首先注册指定对象的激活描述符,如下所示。它通过调用{@link ActivationGroup#getSystem ActivationGroup.getSystem}方法获取激活系统。
     * 然后,此方法通过使用由指定对象的类名称构造的{@link ActivationDesc}调用激活系统的{@link ActivationSystem#registerObject registerObject}
     * 方法获取对象的{@link ActivationID},以及指定的位置,数据和重启模式。
     * <p>此方法首先注册指定对象的激活描述符,如下所示。它通过调用{@link ActivationGroup#getSystem ActivationGroup.getSystem}方法获取激活系统。
     * 如果在获取激活系统或注册激活描述符时发生异常,则会将该异常抛出给调用者。
     * 
     *  <p>接下来,此方法通过使用指定的远程对象调用{@link #exportObject(Remote,ActivationID,int,RMIClientSocketFactory,RMIServerSocketFactory)exportObject}
     * 方法来导出对象,从注册获得的激活标识,指定的端口,指定的客户端和服务器套接字工厂。
     * 如果导出对象时发生异常,则此方法尝试通过调用激活系统的具有激活标识符的{@link ActivationSystem#unregisterObject unregisterObject}方法来取消注册激
     * 活标识符(从注册获得)。
     * 如果发生异常,注销标识符,则会忽略该异常,并且将导出对象的原始异常抛出给调用者。
     * 
     * <p>最后,此方法使用激活标识符和指定的远程对象在此VM中的激活组上调用{@link ActivationGroup#activeObject activeObject}方法,并将激活标识符返回给调用者
     * 。
     * 
     * 
     * @param obj the object being exported
     * @param location the object's code location
     * @param data the object's bootstrapping data
     * @param restart if true, the object is restarted (reactivated) when
     * either the activator is restarted or the object's activation group
     * is restarted after an unexpected crash; if false, the object is only
     * activated on demand.  Specifying <code>restart</code> to be
     * <code>true</code> does not force an initial immediate activation of
     * a newly registered object;  initial activation is lazy.
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @param csf the client-side socket factory for making calls to the
     * remote object
     * @param ssf the server-side socket factory for receiving remote calls
     * @return the activation identifier obtained from registering the
     * descriptor with the activation system
     * @exception ActivationException if activation group is not active
     * @exception RemoteException if object registration or export fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     **/
    public static ActivationID exportObject(Remote obj,
                                            String location,
                                            MarshalledObject<?> data,
                                            boolean restart,
                                            int port,
                                            RMIClientSocketFactory csf,
                                            RMIServerSocketFactory ssf)
        throws ActivationException, RemoteException
    {
        ActivationDesc desc = new ActivationDesc(obj.getClass().getName(),
                                                 location, data, restart);
        /*
         * Register descriptor.
         * <p>
         *  寄存器描述符。
         * 
         */
        ActivationSystem system =  ActivationGroup.getSystem();
        ActivationID id = system.registerObject(desc);

        /*
         * Export object.
         * <p>
         *  导出对象。
         * 
         */
        try {
            exportObject(obj, id, port, csf, ssf);
        } catch (RemoteException e) {
            /*
             * Attempt to unregister activation descriptor because export
             * failed and register/export should be atomic (see 4323621).
             * <p>
             *  尝试取消注册激活描述符,因为导出失败,并且注册/导出应该是原子的(参见4323621)。
             * 
             */
            try {
                system.unregisterObject(id);
            } catch (Exception ex) {
            }
            /*
             * Report original exception.
             * <p>
             *  报告原始异常。
             * 
             */
            throw e;
        }

        /*
         * This call can't fail (it is a local call, and the only possible
         * exception, thrown if the group is inactive, will not be thrown
         * because the group is not inactive).
         * <p>
         *  此调用不能失败(它是一个本地调用,并且唯一可能的异常,如果组处于非活动状态,则不会抛出,因为组不是非活动的)。
         * 
         */
        ActivationGroup.currentGroup().activeObject(id, obj);

        return id;
    }

    /**
     * Export the activatable remote object to the RMI runtime to make
     * the object available to receive incoming calls. The object is
     * exported on an anonymous port, if <code>port</code> is zero. <p>
     *
     * During activation, this <code>exportObject</code> method should
     * be invoked explicitly by an "activatable" object, that does not
     * extend the <code>Activatable</code> class. There is no need for objects
     * that do extend the <code>Activatable</code> class to invoke this
     * method directly because the object is exported during construction.
     *
     * <p>
     *  将可激活的远程对象导出到RMI运行时,以使对象可用于接收传入呼叫。如果<code> port </code>为零,则在匿名端口上导出对象。 <p>
     * 
     *  在激活期间,这个<code> exportObject </code>方法应该由一个"可激活"对象显式调用,不会扩展<code> Activatable </code>类。
     * 没有必要扩展<code> Activatable </code>类的对象直接调用此方法,因为对象在构造期间导出。
     * 
     * 
     * @return the stub for the activatable remote object
     * @param obj the remote object implementation
     * @param id the object's  activation identifier
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @exception RemoteException if object export fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static Remote exportObject(Remote obj,
                                      ActivationID id,
                                      int port)
        throws RemoteException
    {
        return exportObject(obj, new ActivatableServerRef(id, port));
    }

    /**
     * Export the activatable remote object to the RMI runtime to make
     * the object available to receive incoming calls. The object is
     * exported on an anonymous port, if <code>port</code> is zero. <p>
     *
     * During activation, this <code>exportObject</code> method should
     * be invoked explicitly by an "activatable" object, that does not
     * extend the <code>Activatable</code> class. There is no need for objects
     * that do extend the <code>Activatable</code> class to invoke this
     * method directly because the object is exported during construction.
     *
     * <p>
     *  将可激活的远程对象导出到RMI运行时,以使对象可用于接收传入呼叫。如果<code> port </code>为零,则在匿名端口上导出对象。 <p>
     * 
     * 在激活期间,此<code> exportObject </code>方法应由"可激活"对象显式调用,不会扩展<code> Activatable </code>类。
     * 没有必要扩展<code> Activatable </code>类的对象直接调用此方法,因为对象在构造期间导出。
     * 
     * 
     * @return the stub for the activatable remote object
     * @param obj the remote object implementation
     * @param id the object's  activation identifier
     * @param port the port on which the object is exported (an anonymous
     * port is used if port=0)
     * @param csf the client-side socket factory for making calls to the
     * remote object
     * @param ssf the server-side socket factory for receiving remote calls
     * @exception RemoteException if object export fails
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static Remote exportObject(Remote obj,
                                      ActivationID id,
                                      int port,
                                      RMIClientSocketFactory csf,
                                      RMIServerSocketFactory ssf)
        throws RemoteException
    {
        return exportObject(obj, new ActivatableServerRef(id, port, csf, ssf));
    }

    /**
     * Remove the remote object, obj, from the RMI runtime. If
     * successful, the object can no longer accept incoming RMI calls.
     * If the force parameter is true, the object is forcibly unexported
     * even if there are pending calls to the remote object or the
     * remote object still has calls in progress.  If the force
     * parameter is false, the object is only unexported if there are
     * no pending or in progress calls to the object.
     *
     * <p>
     *  从RMI运行时中删除远程对象obj。如果成功,对象不能再接受传入的RMI调用。如果force参数为true,则即使有对远程对象的挂起调用或远程对象仍有呼叫正在进行,对象也会被强制取消导出。
     * 如果force参数为false,那么只有在没有对对象的挂起或进行中调用时,才会取消导出对象。
     * 
     * 
     * @param obj the remote object to be unexported
     * @param force if true, unexports the object even if there are
     * pending or in-progress calls; if false, only unexports the object
     * if there are no pending or in-progress calls
     * @return true if operation is successful, false otherwise
     * @exception NoSuchObjectException if the remote object is not
     * currently exported
     * @exception UnsupportedOperationException if and only if activation is
     * not supported by this implementation
     * @since 1.2
     */
    public static boolean unexportObject(Remote obj, boolean force)
        throws NoSuchObjectException
    {
        return sun.rmi.transport.ObjectTable.unexportObject(obj, force);
    }

    /**
     * Exports the specified object using the specified server ref.
     * <p>
     */
    private static Remote exportObject(Remote obj, ActivatableServerRef sref)
        throws RemoteException
    {
        // if obj extends Activatable, set its ref.
        if (obj instanceof Activatable) {
            ((Activatable) obj).ref = sref;

        }
        return sref.exportObject(obj, null, false);
    }
}
