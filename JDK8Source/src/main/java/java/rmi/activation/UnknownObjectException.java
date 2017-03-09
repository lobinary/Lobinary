/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An <code>UnknownObjectException</code> is thrown by methods of classes and
 * interfaces in the <code>java.rmi.activation</code> package when the
 * <code>ActivationID</code> parameter to the method is determined to be
 * invalid.  An <code>ActivationID</code> is invalid if it is not currently
 * known by the <code>ActivationSystem</code>.  An <code>ActivationID</code>
 * is obtained by the <code>ActivationSystem.registerObject</code> method.
 * An <code>ActivationID</code> is also obtained during the
 * <code>Activatable.register</code> call.
 *
 * <p>
 *  当确定方法的<code> ActivationID </code>参数为<code>时,<code> java.rmi.activation </code>包中的类和接口的方法抛出<code> Unk
 * nownObjectException </code>无效。
 * 如果<code> ActivationSystem </code>当前未知,则<code> ActivationID </code>无效。
 * 通过<code> ActivationSystem.registerObject </code>方法获得<code> ActivationID </code>。
 * 在<code> Activatable.register </code>调用期间还会获取<code> ActivationID </code>。
 * 
 * @author  Ann Wollrath
 * @since   1.2
 * @see     java.rmi.activation.Activatable
 * @see     java.rmi.activation.ActivationGroup
 * @see     java.rmi.activation.ActivationID
 * @see     java.rmi.activation.ActivationMonitor
 * @see     java.rmi.activation.ActivationSystem
 * @see     java.rmi.activation.Activator
 */
public class UnknownObjectException extends ActivationException {

    /** indicate compatibility with the Java 2 SDK v1.2 version of class */
    private static final long serialVersionUID = 3425547551622251430L;

    /**
     * Constructs an <code>UnknownObjectException</code> with the specified
     * detail message.
     *
     * <p>
     * 
     * 
     * @param s the detail message
     * @since 1.2
     */
    public UnknownObjectException(String s) {
        super(s);
    }
}
