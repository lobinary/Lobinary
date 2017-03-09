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

package java.rmi;

/**
 * A <code>NoSuchObjectException</code> is thrown if an attempt is made to
 * invoke a method on an object that no longer exists in the remote virtual
 * machine.  If a <code>NoSuchObjectException</code> occurs attempting to
 * invoke a method on a remote object, the call may be retransmitted and still
 * preserve RMI's "at most once" call semantics.
 *
 * A <code>NoSuchObjectException</code> is also thrown by the method
 * <code>java.rmi.server.RemoteObject.toStub</code> and by the
 * <code>unexportObject</code> methods of
 * <code>java.rmi.server.UnicastRemoteObject</code> and
 * <code>java.rmi.activation.Activatable</code> and
 *
 * <p>
 *  如果试图调用远程虚拟机中不再存在的对象上的方法,则会抛出<code> NoSuchObjectException </code>。
 * 如果发生<code> NoSuchObjectException </code>尝试调用远程对象上的方法,则可以重新传输调用,并仍保留RMI的"至多一次"调用语义。
 * 
 *  还通过方法<code> java.rmi.server.RemoteObject.toStub </code>以及<code> java.rmi的<code> unexportObject </code>
 * 方法抛出<code> NoSuchObjectException </code> .server.UnicastRemoteObject </code>和<code> java.rmi.activati
 * 
 * @author  Ann Wollrath
 * @since   JDK1.1
 * @see     java.rmi.server.RemoteObject#toStub(Remote)
 * @see     java.rmi.server.UnicastRemoteObject#unexportObject(Remote,boolean)
 * @see     java.rmi.activation.Activatable#unexportObject(Remote,boolean)
 */
public class NoSuchObjectException extends RemoteException {

    /* indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 6619395951570472985L;

    /**
     * Constructs a <code>NoSuchObjectException</code> with the specified
     * detail message.
     *
     * <p>
     * on.Activatable </code>和。
     * 
     * 
     * @param s the detail message
     * @since   JDK1.1
     */
    public NoSuchObjectException(String s) {
        super(s);
    }
}
