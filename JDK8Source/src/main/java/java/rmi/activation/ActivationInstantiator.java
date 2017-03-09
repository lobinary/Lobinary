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

/**
 * An <code>ActivationInstantiator</code> is responsible for creating
 * instances of "activatable" objects. A concrete subclass of
 * <code>ActivationGroup</code> implements the <code>newInstance</code>
 * method to handle creating objects within the group.
 *
 * <p>
 *  <code> ActivationInstantiator </code>负责创建"可激活"对象的实例。
 *  <code> ActivationGroup </code>的具体子类实现<code> newInstance </code>方法来处理在组内创建对象。
 * 
 * 
 * @author      Ann Wollrath
 * @see         ActivationGroup
 * @since       1.2
 */
public interface ActivationInstantiator extends Remote {

   /**
    * The activator calls an instantiator's <code>newInstance</code>
    * method in order to recreate in that group an object with the
    * activation identifier, <code>id</code>, and descriptor,
    * <code>desc</code>. The instantiator is responsible for: <ul>
    *
    * <li> determining the class for the object using the descriptor's
    * <code>getClassName</code> method,
    *
    * <li> loading the class from the code location obtained from the
    * descriptor (using the <code>getLocation</code> method),
    *
    * <li> creating an instance of the class by invoking the special
    * "activation" constructor of the object's class that takes two
    * arguments: the object's <code>ActivationID</code>, and the
    * <code>MarshalledObject</code> containing object specific
    * initialization data, and
    *
    * <li> returning a MarshalledObject containing the stub for the
    * remote object it created </ul>
    *
    * <p>
    *  激活器调用实例化器的<code> newInstance </code>方法,以便在该组中重新创建具有激活标识符<code> id </code>和描述符<code> desc </code>的对象。
    * 实例化器负责：<ul>。
    * 
    *  <li>使用描述符的<code> getClassName </code>方法确定对象的类,
    * 
    *  <li>从使用<code> getLocation </code>方法获取的代码位置加载类,
    * 
    * @param id the object's activation identifier
    * @param desc the object's descriptor
    * @return a marshalled object containing the serialized
    * representation of remote object's stub
    * @exception ActivationException if object activation fails
    * @exception RemoteException if remote call fails
    * @since 1.2
    */
    public MarshalledObject<? extends Remote> newInstance(ActivationID id,
                                                          ActivationDesc desc)
        throws ActivationException, RemoteException;
}
