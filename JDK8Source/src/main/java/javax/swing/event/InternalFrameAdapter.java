/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

/**
 * An abstract adapter class for receiving internal frame events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects, and is functionally
 * equivalent to the WindowAdapter class in the AWT.
 * <p>
 * See <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/internalframelistener.html">How to Write an Internal Frame Listener</a>
 * in <em>The Java Tutorial</em>
 *
 * <p>
 *  用于接收内部帧事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象,并且在功能上等效于AWT中的WindowAdapter类。
 * <p>
 *  请参阅<em> Java教程</em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/internalframelistener.html">
 * 如何编写内部框架监听器</a> >。
 * 
 * 
 * @see InternalFrameEvent
 * @see InternalFrameListener
 * @see java.awt.event.WindowListener
 *
 * @author Thomas Ball
 */
public abstract class InternalFrameAdapter implements InternalFrameListener {
    /**
     * Invoked when an internal frame has been opened.
     * <p>
     *  当内部框架已打开时调用。
     * 
     */
    public void internalFrameOpened(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame is in the process of being closed.
     * The close operation can be overridden at this point.
     * <p>
     *  当内部框架处于关闭过程中时调用。此时可以覆盖关闭操作。
     * 
     */
    public void internalFrameClosing(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame has been closed.
     * <p>
     *  在内部框架关闭时调用。
     * 
     */
    public void internalFrameClosed(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame is iconified.
     * <p>
     *  当内部框架图标化时调用。
     * 
     */
    public void internalFrameIconified(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame is de-iconified.
     * <p>
     *  当内部框架取消图标化时调用。
     * 
     */
    public void internalFrameDeiconified(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame is activated.
     * <p>
     *  激活内部框架时调用。
     * 
     */
    public void internalFrameActivated(InternalFrameEvent e) {}

    /**
     * Invoked when an internal frame is de-activated.
     * <p>
     *  当内部帧被禁用时调用。
     */
    public void internalFrameDeactivated(InternalFrameEvent e) {}
}
