/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd;

import java.awt.Component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This abstract subclass of <code>DragGestureRecognizer</code>
 * defines a <code>DragGestureRecognizer</code>
 * for mouse-based gestures.
 *
 * Each platform implements its own concrete subclass of this class,
 * available via the Toolkit.createDragGestureRecognizer() method,
 * to encapsulate
 * the recognition of the platform dependent mouse gesture(s) that initiate
 * a Drag and Drop operation.
 * <p>
 * Mouse drag gesture recognizers should honor the
 * drag gesture motion threshold, available through
 * {@link DragSource#getDragThreshold}.
 * A drag gesture should be recognized only when the distance
 * in either the horizontal or vertical direction between
 * the location of the latest mouse dragged event and the
 * location of the corresponding mouse button pressed event
 * is greater than the drag gesture motion threshold.
 * <p>
 * Drag gesture recognizers created with
 * {@link DragSource#createDefaultDragGestureRecognizer}
 * follow this convention.
 *
 * <p>
 *  这个<code> DragGestureRecognizer </code>的抽象子类为基于鼠标的手势定义了一个<code> DragGestureRecognizer </code>。
 * 
 *  每个平台实现它自己的这个类的具体子类,通过Toolkit.createDragGestureRecognizer()方法可用,以封装启动拖放操作的平台相关鼠标手势的识别。
 * <p>
 *  鼠标拖动手势识别器应该遵守拖动手势运动阈值,可通过{@link DragSource#getDragThreshold}获得。
 * 只有当最近的鼠标拖动事件的位置和相应的鼠标按钮按下事件的位置之间的水平或垂直方向上的距离大于拖动手势运动阈值时,才应该识别拖动手势。
 * <p>
 *  使用{@link DragSource#createDefaultDragGestureRecognizer}创建的拖动手势识别器遵循此约定。
 * 
 * 
 * @author Laurence P. G. Cable
 *
 * @see java.awt.dnd.DragGestureListener
 * @see java.awt.dnd.DragGestureEvent
 * @see java.awt.dnd.DragSource
 */

public abstract class MouseDragGestureRecognizer extends DragGestureRecognizer implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 6220099344182281120L;

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the
     * <code>Component</code> c, the <code>Component</code>
     * to observe, the action(s)
     * permitted for this drag operation, and
     * the <code>DragGestureListener</code> to
     * notify when a drag gesture is detected.
     * <P>
     * <p>
     *  构造一个新的<code> MouseDragGestureRecognizer </code>,给定<code> DragSource </code>为<code> Component </code>
     *  c,<code> Component </code>允许此拖动操作,并且<code> DragGestureListener </code>可在检测到拖动手势时通知。
     * <P>
     * 
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     * @param act The actions permitted for this Drag
     * @param dgl The DragGestureListener to notify when a gesture is detected
     *
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c, int act, DragGestureListener dgl) {
        super(ds, c, act, dgl);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for
     * the <code>Component</code> c,
     * the <code>Component</code> to observe, and the action(s)
     * permitted for this drag operation.
     * <P>
     * <p>
     * 构造一个新的<code> MouseDragGestureRecognizer </code>给定<code> DragSource </code>用于<code> Component </code> 
     * c,<code> Component </code> )允许进行此拖动操作。
     * <P>
     * 
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     * @param act The actions permitted for this drag
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c, int act) {
        this(ds, c, act, null);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the
     * <code>Component</code> c, and the
     * <code>Component</code> to observe.
     * <P>
     * <p>
     *  为<code> Component </code> c和<code> Component </code>提供<code> DragSource </code>来构造一个新的<code> MouseDr
     * agGestureRecognizer </code>。
     * <P>
     * 
     * @param ds  The DragSource for the Component c
     * @param c   The Component to observe
     */

    protected MouseDragGestureRecognizer(DragSource ds, Component c) {
        this(ds, c, DnDConstants.ACTION_NONE);
    }

    /**
     * Construct a new <code>MouseDragGestureRecognizer</code>
     * given the <code>DragSource</code> for the <code>Component</code>.
     * <P>
     * <p>
     *  构造一个新的<code> MouseDragGestureRecognizer </code>给定<code> DragSource </code>为<code> Component </code>。
     * <P>
     * 
     * @param ds  The DragSource for the Component
     */

    protected MouseDragGestureRecognizer(DragSource ds) {
        this(ds, null);
    }

    /**
     * register this DragGestureRecognizer's Listeners with the Component
     * <p>
     *  使用Component注册此DragGestureRecognizer的Listeners
     * 
     */

    protected void registerListeners() {
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    }

    /**
     * unregister this DragGestureRecognizer's Listeners with the Component
     *
     * subclasses must override this method
     * <p>
     *  请使用Component取消注册此DragGestureRecognizer的侦听器
     * 
     *  子类必须重写此方法
     * 
     */


    protected void unregisterListeners() {
        component.removeMouseListener(this);
        component.removeMouseMotionListener(this);
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     * <P>
     * <p>
     *  当鼠标在某个组件上单击时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseClicked(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been
     * pressed on a <code>Component</code>.
     * <P>
     * <p>
     *  在<code> Component </code>上按下鼠标按钮时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mousePressed(MouseEvent e) { }

    /**
     * Invoked when a mouse button has been released on a component.
     * <P>
     * <p>
     *  在组件上释放鼠标按钮时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseReleased(MouseEvent e) { }

    /**
     * Invoked when the mouse enters a component.
     * <P>
     * <p>
     *  当鼠标进入组件时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseEntered(MouseEvent e) { }

    /**
     * Invoked when the mouse exits a component.
     * <P>
     * <p>
     *  在鼠标退出组件时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseExited(MouseEvent e) { }

    /**
     * Invoked when a mouse button is pressed on a component.
     * <P>
     * <p>
     *  在组件上按下鼠标按钮时调用。
     * <P>
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseDragged(MouseEvent e) { }

    /**
     * Invoked when the mouse button has been moved on a component
     * (with no buttons no down).
     * <P>
     * <p>
     *  在组件上移动鼠标按钮时调用(没有按钮没有向下)。
     * 
     * @param e the <code>MouseEvent</code>
     */

    public void mouseMoved(MouseEvent e) { }
}
