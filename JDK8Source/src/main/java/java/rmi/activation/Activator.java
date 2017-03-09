/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.activation.UnknownObjectException;

/**
 * The <code>Activator</code> facilitates remote object activation. A
 * "faulting" remote reference calls the activator's
 * <code>activate</code> method to obtain a "live" reference to a
 * "activatable" remote object. Upon receiving a request for activation,
 * the activator looks up the activation descriptor for the activation
 * identifier, <code>id</code>, determines the group in which the
 * object should be activated initiates object re-creation via the
 * group's <code>ActivationInstantiator</code> (via a call to the
 * <code>newInstance</code> method). The activator initiates the
 * execution of activation groups as necessary. For example, if an
 * activation group for a specific group identifier is not already
 * executing, the activator initiates the execution of a VM for the
 * group. <p>
 *
 * The <code>Activator</code> works closely with
 * <code>ActivationSystem</code>, which provides a means for registering
 * groups and objects within those groups, and <code>ActivationMonitor</code>,
 * which recives information about active and inactive objects and inactive
 * groups. <p>
 *
 * The activator is responsible for monitoring and detecting when
 * activation groups fail so that it can remove stale remote references
 * to groups and active object's within those groups.<p>
 *
 * <p>
 *  <code> Activator </code>有助于远程对象激活。 "故障"远程参考调用激活器的<code> activate </code>方法以获得对"可激活"远程对象的"实时"引用。
 * 在接收到激活请求时,激活器查找激活标识符的激活描述符,<code> id </code>,确定其中应激活对象的组通过组的<code> ActivationInstantiator启动对象重新创建</code>
 * (通过调用<code> newInstance </code>方法)。
 *  <code> Activator </code>有助于远程对象激活。 "故障"远程参考调用激活器的<code> activate </code>方法以获得对"可激活"远程对象的"实时"引用。
 * 激活器根据需要启动激活组的执行。例如,如果特定组标识符的激活组尚未执行,则激活器启动该组的VM的执行。 <p>。
 * 
 *  <code> Activator </code>与<code> ActivationSystem </code>密切配合,提供了在这些组中注册组和对象的方法,<code> ActivationMoni
 * tor </code>非活动对象和非活动组。
 *  <p>。
 * 
 *  激活器负责监视和检测激活组何时失败,从而可以删除对这些组中的组和活动对象的过时远程引用。<p>
 * 
 * 
 * @author      Ann Wollrath
 * @see         ActivationInstantiator
 * @see         ActivationGroupDesc
 * @see         ActivationGroupID
 * @since       1.2
 */
public interface Activator extends Remote {
    /**
     * Activate the object associated with the activation identifier,
     * <code>id</code>. If the activator knows the object to be active
     * already, and <code>force</code> is false , the stub with a
     * "live" reference is returned immediately to the caller;
     * otherwise, if the activator does not know that corresponding
     * the remote object is active, the activator uses the activation
     * descriptor information (previously registered) to determine the
     * group (VM) in which the object should be activated. If an
     * <code>ActivationInstantiator</code> corresponding to the
     * object's group descriptor already exists, the activator invokes
     * the activation group's <code>newInstance</code> method passing
     * it the object's id and descriptor. <p>
     *
     * If the activation group for the object's group descriptor does
     * not yet exist, the activator starts an
     * <code>ActivationInstantiator</code> executing (by spawning a
     * child process, for example). When the activator receives the
     * activation group's call back (via the
     * <code>ActivationSystem</code>'s <code>activeGroup</code>
     * method) specifying the activation group's reference, the
     * activator can then invoke that activation instantiator's
     * <code>newInstance</code> method to forward each pending
     * activation request to the activation group and return the
     * result (a marshalled remote object reference, a stub) to the
     * caller.<p>
     *
     * Note that the activator receives a "marshalled" object instead of a
     * Remote object so that the activator does not need to load the
     * code for that object, or participate in distributed garbage
     * collection for that object. If the activator kept a strong
     * reference to the remote object, the activator would then
     * prevent the object from being garbage collected under the
     * normal distributed garbage collection mechanism. <p>
     *
     * <p>
     * 激活与激活标识符<code> id </code>相关联的对象。
     * 如果激活者知道对象已经是活动的,并且<code> force </code>为false,那么具有"实时"引用的存根立即返回到调用者;否则,如果激活器不知道对应的远程对象是活动的,则激活器使用激活描述符
     * 信息(先前注册的)来确定其中应激活对象的组(VM)。
     * 激活与激活标识符<code> id </code>相关联的对象。
     * 如果对应于对象的组描述符的<code> ActivationInstantiator </code>已经存在,激活器调用激活组的<code> newInstance </code>方法传递对象的id和描
     * 述符。
     * 激活与激活标识符<code> id </code>相关联的对象。 <p>。
     * 
     * 
     * @param id the activation identifier for the object being activated
     * @param force if true, the activator contacts the group to obtain
     * the remote object's reference; if false, returning the cached value
     * is allowed.
     * @return the remote object (a stub) in a marshalled form
     * @exception ActivationException if object activation fails
     * @exception UnknownObjectException if object is unknown (not registered)
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public MarshalledObject<? extends Remote> activate(ActivationID id,
                                                       boolean force)
        throws ActivationException, UnknownObjectException, RemoteException;

}
