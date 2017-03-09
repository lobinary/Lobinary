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
import java.rmi.activation.UnknownGroupException;
import java.rmi.activation.UnknownObjectException;

/**
 * An <code>ActivationMonitor</code> is specific to an
 * <code>ActivationGroup</code> and is obtained when a group is
 * reported active via a call to
 * <code>ActivationSystem.activeGroup</code> (this is done
 * internally). An activation group is responsible for informing its
 * <code>ActivationMonitor</code> when either: its objects become active or
 * inactive, or the group as a whole becomes inactive.
 *
 * <p>
 *  <code> ActivationMonitor </code>特定于<code> ActivationGroup </code>,当通过调用<code> ActivationSystem.activ
 * eGroup </code> 。
 * 激活组负责在以下情况时通知其<code> ActivationMonitor </code>：其对象变为活动或非活动,或者组作为一个整体变为非活动状态。
 * 
 * 
 * @author      Ann Wollrath
 * @see         Activator
 * @see         ActivationSystem
 * @see         ActivationGroup
 * @since       1.2
 */
public interface ActivationMonitor extends Remote {

   /**
     * An activation group calls its monitor's
     * <code>inactiveObject</code> method when an object in its group
     * becomes inactive (deactivates).  An activation group discovers
     * that an object (that it participated in activating) in its VM
     * is no longer active, via calls to the activation group's
     * <code>inactiveObject</code> method. <p>
     *
     * The <code>inactiveObject</code> call informs the
     * <code>ActivationMonitor</code> that the remote object reference
     * it holds for the object with the activation identifier,
     * <code>id</code>, is no longer valid. The monitor considers the
     * reference associated with <code>id</code> as a stale reference.
     * Since the reference is considered stale, a subsequent
     * <code>activate</code> call for the same activation identifier
     * results in re-activating the remote object.<p>
     *
     * <p>
     *  当其组中的对象变为不活动(取消激活)时,激活组调用其监视器的<code> inactiveObject </code>方法。
     * 激活组通过调用激活组的<code> inactiveObject </code>方法发现其VM中的对象(它参与激活)不再活动。 <p>。
     * 
     *  <code> inactiveObject </code>调用通知<code> ActivationMonitor </code>,它对具有激活标识符<code> id </code>的对象的远程对象
     * 引用不再有效。
     * 监视器将与<code> id </code>相关联的引用视为陈旧的引用。由于引用被认为是陈旧的,因此对相同激活标识符的后续<code> activate </code>调用导致重新激活远程对象。
     * <p>。
     * 
     * @param id the object's activation identifier
     * @exception UnknownObjectException if object is unknown
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public void inactiveObject(ActivationID id)
        throws UnknownObjectException, RemoteException;

    /**
     * Informs that an object is now active. An <code>ActivationGroup</code>
     * informs its monitor if an object in its group becomes active by
     * other means than being activated directly (i.e., the object
     * is registered and "activated" itself).
     *
     * <p>
     * 
     * 
     * @param id the active object's id
     * @param obj the marshalled form of the object's stub
     * @exception UnknownObjectException if object is unknown
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public void activeObject(ActivationID id,
                             MarshalledObject<? extends Remote> obj)
        throws UnknownObjectException, RemoteException;

    /**
     * Informs that the group is now inactive. The group will be
     * recreated upon a subsequent request to activate an object
     * within the group. A group becomes inactive when all objects
     * in the group report that they are inactive.
     *
     * <p>
     * 通知对象现在处于活动状态。 <code> ActivationGroup </code>通知其监视器其组中的对象是否通过除直接激活之外的其他方式变为活动状态(即,对象被注册并且"激活"自身)。
     * 
     * 
     * @param id the group's id
     * @param incarnation the group's incarnation number
     * @exception UnknownGroupException if group is unknown
     * @exception RemoteException if remote call fails
     * @since 1.2
     */
    public void inactiveGroup(ActivationGroupID id,
                              long incarnation)
        throws UnknownGroupException, RemoteException;

}
