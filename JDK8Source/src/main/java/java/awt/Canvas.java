/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2010, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import java.awt.image.BufferStrategy;
import java.awt.peer.CanvasPeer;
import javax.accessibility.*;

/**
 * A <code>Canvas</code> component represents a blank rectangular
 * area of the screen onto which the application can draw or from
 * which the application can trap input events from the user.
 * <p>
 * An application must subclass the <code>Canvas</code> class in
 * order to get useful functionality such as creating a custom
 * component. The <code>paint</code> method must be overridden
 * in order to perform custom graphics on the canvas.
 *
 * <p>
 *  <code> Canvas </code>组件表示应用程序可以在其上绘制或者应用程序可以捕获来自用户的输入事件的屏幕的空白矩形区域。
 * <p>
 *  应用程序必须子类化<code> Canvas </code>类才能获得有用的功能,例如创建自定义组件。必须覆盖<code> paint </code>方法才能在画布上执行自定义图形。
 * 
 * 
 * @author      Sami Shaio
 * @since       JDK1.0
 */
public class Canvas extends Component implements Accessible {

    private static final String base = "canvas";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -2284879212465893870L;

    /**
     * Constructs a new Canvas.
     * <p>
     *  构造一个新的画布。
     * 
     */
    public Canvas() {
    }

    /**
     * Constructs a new Canvas given a GraphicsConfiguration object.
     *
     * <p>
     *  构造一个给定GraphicsConfiguration对象的新Canvas。
     * 
     * 
     * @param config a reference to a GraphicsConfiguration object.
     *
     * @see GraphicsConfiguration
     */
    public Canvas(GraphicsConfiguration config) {
        this();
        setGraphicsConfiguration(config);
    }

    @Override
    void setGraphicsConfiguration(GraphicsConfiguration gc) {
        synchronized(getTreeLock()) {
            CanvasPeer peer = (CanvasPeer)getPeer();
            if (peer != null) {
                gc = peer.getAppropriateGraphicsConfiguration(gc);
            }
            super.setGraphicsConfiguration(gc);
        }
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Canvas.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the peer of the canvas.  This peer allows you to change the
     * user interface of the canvas without changing its functionality.
     * <p>
     *  创建画布的对等体。此对等体允许您更改画布的用户界面,而不更改其功能。
     * 
     * 
     * @see     java.awt.Toolkit#createCanvas(java.awt.Canvas)
     * @see     java.awt.Component#getToolkit()
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = getToolkit().createCanvas(this);
            super.addNotify();
        }
    }

    /**
     * Paints this canvas.
     * <p>
     * Most applications that subclass <code>Canvas</code> should
     * override this method in order to perform some useful operation
     * (typically, custom painting of the canvas).
     * The default operation is simply to clear the canvas.
     * Applications that override this method need not call
     * super.paint(g).
     *
     * <p>
     *  画这幅画。
     * <p>
     *  大多数子类<code> Canvas </code>的应用程序应该重写这个方法,以便执行一些有用的操作(通常是画布的自定义绘制)。默认操作只是清除画布。
     * 覆盖此方法的应用程序不必调用super.paint(g)。
     * 
     * 
     * @param      g   the specified Graphics context
     * @see        #update(Graphics)
     * @see        Component#paint(Graphics)
     */
    public void paint(Graphics g) {
        g.clearRect(0, 0, width, height);
    }

    /**
     * Updates this canvas.
     * <p>
     * This method is called in response to a call to <code>repaint</code>.
     * The canvas is first cleared by filling it with the background
     * color, and then completely redrawn by calling this canvas's
     * <code>paint</code> method.
     * Note: applications that override this method should either call
     * super.update(g) or incorporate the functionality described
     * above into their own code.
     *
     * <p>
     *  更新此画布。
     * <p>
     * 调用此方法以响应对<code>重绘</code>的调用。画布首先通过填充背景颜色来清除,然后通过调用此画布的<code> paint </code>方法完全重绘。
     * 注意：覆盖此方法的应用程序应调用super.update(g)或将上述功能合并到自己的代码中。
     * 
     * 
     * @param g the specified Graphics context
     * @see   #paint(Graphics)
     * @see   Component#update(Graphics)
     */
    public void update(Graphics g) {
        g.clearRect(0, 0, width, height);
        paint(g);
    }

    boolean postsOldMouseEvents() {
        return true;
    }

    /**
     * Creates a new strategy for multi-buffering on this component.
     * Multi-buffering is useful for rendering performance.  This method
     * attempts to create the best strategy available with the number of
     * buffers supplied.  It will always create a <code>BufferStrategy</code>
     * with that number of buffers.
     * A page-flipping strategy is attempted first, then a blitting strategy
     * using accelerated buffers.  Finally, an unaccelerated blitting
     * strategy is used.
     * <p>
     * Each time this method is called,
     * the existing buffer strategy for this component is discarded.
     * <p>
     *  在此组件上创建多缓冲的新策略。多缓冲对于呈现性能很有用。此方法尝试创建可用的最佳策略,提供的缓冲区数。它将总是创建一个<code> BufferStrategy </code>。
     * 首先尝试页面翻转策略,然后尝试使用加速缓冲区的blitting策略。最后,使用未加速的打击策略。
     * <p>
     *  每次调用此方法时,将丢弃此组件的现有缓冲区策略。
     * 
     * 
     * @param numBuffers number of buffers to create, including the front buffer
     * @exception IllegalArgumentException if numBuffers is less than 1.
     * @exception IllegalStateException if the component is not displayable
     * @see #isDisplayable
     * @see #getBufferStrategy
     * @since 1.4
     */
    public void createBufferStrategy(int numBuffers) {
        super.createBufferStrategy(numBuffers);
    }

    /**
     * Creates a new strategy for multi-buffering on this component with the
     * required buffer capabilities.  This is useful, for example, if only
     * accelerated memory or page flipping is desired (as specified by the
     * buffer capabilities).
     * <p>
     * Each time this method
     * is called, the existing buffer strategy for this component is discarded.
     * <p>
     *  使用所需的缓冲区功能在此组件上创建多缓冲的新策略。这是有用的,例如,如果只需要加速的存储器或页面翻转(由缓冲器功能指定)。
     * <p>
     *  每次调用此方法时,将丢弃此组件的现有缓冲区策略。
     * 
     * 
     * @param numBuffers number of buffers to create
     * @param caps the required capabilities for creating the buffer strategy;
     * cannot be <code>null</code>
     * @exception AWTException if the capabilities supplied could not be
     * supported or met; this may happen, for example, if there is not enough
     * accelerated memory currently available, or if page flipping is specified
     * but not possible.
     * @exception IllegalArgumentException if numBuffers is less than 1, or if
     * caps is <code>null</code>
     * @see #getBufferStrategy
     * @since 1.4
     */
    public void createBufferStrategy(int numBuffers,
        BufferCapabilities caps) throws AWTException {
        super.createBufferStrategy(numBuffers, caps);
    }

    /**
     * Returns the <code>BufferStrategy</code> used by this component.  This
     * method will return null if a <code>BufferStrategy</code> has not yet
     * been created or has been disposed.
     *
     * <p>
     *  返回此组件使用的<code> BufferStrategy </code>。如果<code> BufferStrategy </code>尚未创建或已被处理,此方法将返回null。
     * 
     * 
     * @return the buffer strategy used by this component
     * @see #createBufferStrategy
     * @since 1.4
     */
    public BufferStrategy getBufferStrategy() {
        return super.getBufferStrategy();
    }

    /*
     * --- Accessibility Support ---
     *
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Gets the AccessibleContext associated with this Canvas.
     * For canvases, the AccessibleContext takes the form of an
     * AccessibleAWTCanvas.
     * A new AccessibleAWTCanvas instance is created if necessary.
     *
     * <p>
     * 获取与此Canvas相关联的AccessibleContext。对于画布,AccessibleContext采用AccessibleAWTCanvas的形式。
     * 如果需要,将创建一个新的AccessibleAWTCanvas实例。
     * 
     * 
     * @return an AccessibleAWTCanvas that serves as the
     *         AccessibleContext of this Canvas
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTCanvas();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Canvas</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to canvas user-interface elements.
     * <p>
     *  此类实现<code> Canvas </code>类的辅助功能支持。它提供了适用于canvas用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTCanvas extends AccessibleAWTComponent
    {
        private static final long serialVersionUID = -6325592262103146699L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.CANVAS;
        }

    } // inner class AccessibleAWTCanvas
}
