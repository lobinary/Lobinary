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
 * An <code>RMIFailureHandler</code> can be registered via the
 * <code>RMISocketFactory.setFailureHandler</code> call. The
 * <code>failure</code> method of the handler is invoked when the RMI
 * runtime is unable to create a <code>ServerSocket</code> to listen
 * for incoming calls. The <code>failure</code> method returns a boolean
 * indicating whether the runtime should attempt to re-create the
 * <code>ServerSocket</code>.
 *
 * <p>
 *  可以通过<code> RMISocketFactory.setFailureHandler </code>调用注册<code> RMIFailureHandler </code>。
 * 当RMI运行时无法创建<code> ServerSocket </code>来侦听传入呼叫时,会调用处理程序的<code> failure </code>方法。
 *  <code> failure </code>方法返回一个布尔值,指示运行时是否应该尝试重新创建<code> ServerSocket </code>。
 * 
 * 
 * @author      Ann Wollrath
 * @since       JDK1.1
 */
public interface RMIFailureHandler {

    /**
     * The <code>failure</code> callback is invoked when the RMI
     * runtime is unable to create a <code>ServerSocket</code> via the
     * <code>RMISocketFactory</code>. An <code>RMIFailureHandler</code>
     * is registered via a call to
     * <code>RMISocketFacotry.setFailureHandler</code>.  If no failure
     * handler is installed, the default behavior is to attempt to
     * re-create the ServerSocket.
     *
     * <p>
     *  当RMI运行时无法通过<code> RMISocketFactory </code>创建<code> ServerSocket </code>时,会调用<code> failure </code>回调
     * 。
     * 通过调用<code> RMISocketFacotry.setFailureHandler </code>来注册<code> RMIFailureHandler </code>。
     * 
     * @param ex the exception that occurred during <code>ServerSocket</code>
     *           creation
     * @return if true, the RMI runtime attempts to retry
     * <code>ServerSocket</code> creation
     * @see java.rmi.server.RMISocketFactory#setFailureHandler(RMIFailureHandler)
     * @since JDK1.1
     */
    public boolean failure(Exception ex);

}
