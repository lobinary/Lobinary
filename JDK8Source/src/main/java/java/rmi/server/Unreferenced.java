/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1998, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A remote object implementation should implement the
 * <code>Unreferenced</code> interface to receive notification when there are
 * no more clients that reference that remote object.
 *
 * <p>
 *  远程对象实现应实现<code> Unreferenced </code>接口,以便在没有更多客户端引用该远程对象时接收通知。
 * 
 * 
 * @author  Ann Wollrath
 * @author  Roger Riggs
 * @since   JDK1.1
 */
public interface Unreferenced {
    /**
     * Called by the RMI runtime sometime after the runtime determines that
     * the reference list, the list of clients referencing the remote object,
     * becomes empty.
     * <p>
     *  由RMI运行时在运行时间后调用,确定引用列表(引用远程对象的客户机列表)变为空。
     * 
     * @since JDK1.1
     */
    public void unreferenced();
}
