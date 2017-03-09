/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.UnknownGroupException;
import java.rmi.activation.UnknownObjectException;

/**
 * The <code>ActivationSystem</code> provides a means for registering
 * groups and "activatable" objects to be activated within those groups.
 * The <code>ActivationSystem</code> works closely with the
 * <code>Activator</code>, which activates objects registered via the
 * <code>ActivationSystem</code>, and the <code>ActivationMonitor</code>,
 * which obtains information about active and inactive objects,
 * and inactive groups.
 *
 * <p>
 *  <code> ActivationSystem </code>提供了一种注册组和在这些组中激活的"可激活"对象的方法。
 *  <code> ActivationSystem </code>与<code> Activator </code>紧密配合,激活通过<code> ActivationSystem </code>注册的对
 * 象和<code> ActivationMonitor </code>有关活动和非活动对象以及非活动组的信息。
 *  <code> ActivationSystem </code>提供了一种注册组和在这些组中激活的"可激活"对象的方法。
 * 
 * 
 * @author      Ann Wollrath
 * @see         Activator
 * @see         ActivationMonitor
 * @since       1.2
 */
public interface ActivationSystem extends Remote {

    /** The port to lookup the activation system. */
    public static final int SYSTEM_PORT = 1098;

    /**
     * The <code>registerObject</code> method is used to register an
     * activation descriptor, <code>desc</code>, and obtain an
     * activation identifier for a activatable remote object. The
     * <code>ActivationSystem</code> creates an
     * <code>ActivationID</code> (a activation identifier) for the
     * object specified by the descriptor, <code>desc</code>, and
     * records, in stable storage, the activation descriptor and its
     * associated identifier for later use. When the <code>Activator</code>
     * receives an <code>activate</code> request for a specific identifier, it
     * looks up the activation descriptor (registered previously) for
     * the specified identifier and uses that information to activate
     * the object. <p>
     *
     * <p>
     *  <code> registerObject </code>方法用于注册激活描述符<code> desc </code>,并获得可激活远程对象的激活标识符。
     *  <code> ActivationSystem </code>为由描述符<code> desc </code>指定的对象创建<code> ActivationID </code>(激活标识符),并在稳
     * 定存储中记录激活描述符及其相关标识符供以后使用。
     *  <code> registerObject </code>方法用于注册激活描述符<code> desc </code>,并获得可激活远程对象的激活标识符。
     * 当<code> Activator </code>接收到针对特定标识符的<code> activate </code>请求时,它查找指定标识符的激活描述符(先前注册),并使用该信息激活对象。 <p>。
     * 
     * 
     * @param desc the object's activation descriptor
     * @return the activation id that can be used to activate the object
     * @exception ActivationException if registration fails (e.g., database
     * update failure, etc).
     * @exception UnknownGroupException if group referred to in
     * <code>desc</code> is not registered with this system
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public ActivationID registerObject(ActivationDesc desc)
        throws ActivationException, UnknownGroupException, RemoteException;

    /**
     * Remove the activation id and associated descriptor previously
     * registered with the <code>ActivationSystem</code>; the object
     * can no longer be activated via the object's activation id.
     *
     * <p>
     *  删除先前使用<code> ActivationSystem </code>注册的激活ID和关联描述符;该对象不能再通过对象的激活ID激活。
     * 
     * 
     * @param id the object's activation id (from previous registration)
     * @exception ActivationException if unregister fails (e.g., database
     * update failure, etc).
     * @exception UnknownObjectException if object is unknown (not registered)
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public void unregisterObject(ActivationID id)
        throws ActivationException, UnknownObjectException, RemoteException;

    /**
     * Register the activation group. An activation group must be
     * registered with the <code>ActivationSystem</code> before objects
     * can be registered within that group.
     *
     * <p>
     * 注册激活组。必须在<code> ActivationSystem </code>中注册激活组,才能在该组中注册对象。
     * 
     * 
     * @param desc the group's descriptor
     * @return an identifier for the group
     * @exception ActivationException if group registration fails
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public ActivationGroupID registerGroup(ActivationGroupDesc desc)
        throws ActivationException, RemoteException;

    /**
     * Callback to inform activation system that group is now
     * active. This call is made internally by the
     * <code>ActivationGroup.createGroup</code> method to inform
     * the <code>ActivationSystem</code> that the group is now
     * active.
     *
     * <p>
     *  回调以通知激活系统该组现在是活动的。
     * 此调用由<code> ActivationGroup.createGroup </code>方法内部进行,以通知<code> ActivationSystem </code>该组现在处于活动状态。
     * 
     * 
     * @param id the activation group's identifier
     * @param group the group's instantiator
     * @param incarnation the group's incarnation number
     * @return monitor for activation group
     * @exception UnknownGroupException if group is not registered
     * @exception ActivationException if a group for the specified
     * <code>id</code> is already active and that group is not equal
     * to the specified <code>group</code> or that group has a different
     * <code>incarnation</code> than the specified <code>group</code>
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public ActivationMonitor activeGroup(ActivationGroupID id,
                                         ActivationInstantiator group,
                                         long incarnation)
        throws UnknownGroupException, ActivationException, RemoteException;

    /**
     * Remove the activation group. An activation group makes this call back
     * to inform the activator that the group should be removed (destroyed).
     * If this call completes successfully, objects can no longer be
     * registered or activated within the group. All information of the
     * group and its associated objects is removed from the system.
     *
     * <p>
     *  删除激活组。激活组将该回叫通知激活者该组应被移除(销毁)。如果此调用成功完成,则无法在组中注册或激活对象。将从系统中删除组及其关联对象的所有信息。
     * 
     * 
     * @param id the activation group's identifier
     * @exception ActivationException if unregister fails (e.g., database
     * update failure, etc).
     * @exception UnknownGroupException if group is not registered
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public void unregisterGroup(ActivationGroupID id)
        throws ActivationException, UnknownGroupException, RemoteException;

    /**
     * Shutdown the activation system. Destroys all groups spawned by
     * the activation daemon and exits the activation daemon.
     * <p>
     *  关闭激活系统。销毁由激活守护程序产生的所有组,并退出激活守护程序。
     * 
     * 
     * @exception RemoteException if failed to contact/shutdown the activation
     * daemon
     * @since 1.2
     */
    public void shutdown() throws RemoteException;

    /**
     * Set the activation descriptor, <code>desc</code> for the object with
     * the activation identifier, <code>id</code>. The change will take
     * effect upon subsequent activation of the object.
     *
     * <p>
     *  为激活标识为<code> id </code>的对象设置激活描述符<code> desc </code>。更改将在对象的后续激活时生效。
     * 
     * 
     * @param id the activation identifier for the activatable object
     * @param desc the activation descriptor for the activatable object
     * @exception UnknownGroupException the group associated with
     * <code>desc</code> is not a registered group
     * @exception UnknownObjectException the activation <code>id</code>
     * is not registered
     * @exception ActivationException for general failure (e.g., unable
     * to update log)
     * @exception RemoteException if remote call fails
     * @return the previous value of the activation descriptor
     * @see #getActivationDesc
     * @since 1.2
     */
    public ActivationDesc setActivationDesc(ActivationID id,
                                            ActivationDesc desc)
        throws ActivationException, UnknownObjectException,
            UnknownGroupException, RemoteException;

    /**
     * Set the activation group descriptor, <code>desc</code> for the object
     * with the activation group identifier, <code>id</code>. The change will
     * take effect upon subsequent activation of the group.
     *
     * <p>
     *  为激活组标识符<code> id </code>的对象设置激活组描述符<code> desc </code>。更改将在组的后续激活时生效。
     * 
     * 
     * @param id the activation group identifier for the activation group
     * @param desc the activation group descriptor for the activation group
     * @exception UnknownGroupException the group associated with
     * <code>id</code> is not a registered group
     * @exception ActivationException for general failure (e.g., unable
     * to update log)
     * @exception RemoteException if remote call fails
     * @return the previous value of the activation group descriptor
     * @see #getActivationGroupDesc
     * @since 1.2
     */
    public ActivationGroupDesc setActivationGroupDesc(ActivationGroupID id,
                                                      ActivationGroupDesc desc)
       throws ActivationException, UnknownGroupException, RemoteException;

    /**
     * Returns the activation descriptor, for the object with the activation
     * identifier, <code>id</code>.
     *
     * <p>
     *  返回激活标识为<code> id </code>的对象的激活描述符。
     * 
     * 
     * @param id the activation identifier for the activatable object
     * @exception UnknownObjectException if <code>id</code> is not registered
     * @exception ActivationException for general failure
     * @exception RemoteException if remote call fails
     * @return the activation descriptor
     * @see #setActivationDesc
     * @since 1.2
     */
    public ActivationDesc getActivationDesc(ActivationID id)
       throws ActivationException, UnknownObjectException, RemoteException;

    /**
     * Returns the activation group descriptor, for the group
     * with the activation group identifier, <code>id</code>.
     *
     * <p>
     *  返回激活组标识符<code> id </code>的组的激活组描述符。
     * 
     * @param id the activation group identifier for the group
     * @exception UnknownGroupException if <code>id</code> is not registered
     * @exception ActivationException for general failure
     * @exception RemoteException if remote call fails
     * @return the activation group descriptor
     * @see #setActivationGroupDesc
     * @since 1.2
     */
    public ActivationGroupDesc getActivationGroupDesc(ActivationGroupID id)
       throws ActivationException, UnknownGroupException, RemoteException;
}
