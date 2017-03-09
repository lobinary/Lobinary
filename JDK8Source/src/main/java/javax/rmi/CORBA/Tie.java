/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2001, Oracle and/or its affiliates. All rights reserved.
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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package javax.rmi.CORBA;

import java.rmi.Remote;
import java.util.Hashtable;

import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.ORB;

/**
 * Defines methods which all RMI-IIOP server side ties must implement.
 * <p>
 *  定义所有RMI-IIOP服务器端绑定必须实现的方法。
 * 
 */
public interface Tie extends org.omg.CORBA.portable.InvokeHandler {
    /**
     * Returns an object reference for the target object represented by
     * this tie.
     * <p>
     *  返回此tie所表示的目标对象的对象引用。
     * 
     * 
     * @return an object reference for the target object.
     */
    org.omg.CORBA.Object thisObject();

    /**
     * Deactivates the target object represented by this tie.
     * <p>
     *  停用由此tie所表示的目标对象。
     * 
     */
    void deactivate() throws java.rmi.NoSuchObjectException;

    /**
     * Returns the ORB for this tie.
     * <p>
     *  返回此tie的ORB。
     * 
     * 
     * @return the ORB.
     */
    ORB orb();

    /**
     * Sets the ORB for this tie.
     * <p>
     *  设置此tie的ORB。
     * 
     * 
     * @param orb the ORB.
     */
    void orb(ORB orb);

    /**
     * Called by {@link Util#registerTarget} to set the target
     * for this tie.
     * <p>
     *  由{@link Util#registerTarget}调用以设置此tie的目标。
     * 
     * 
     * @param target the object to use as the target for this tie.
     */
    void setTarget(java.rmi.Remote target);

    /**
     * Returns the target for this tie.
     * <p>
     *  返回此tie的目标。
     * 
     * @return the target.
     */
    java.rmi.Remote getTarget();
}
