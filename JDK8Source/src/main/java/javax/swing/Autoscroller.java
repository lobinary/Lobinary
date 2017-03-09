/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

import java.awt.*;
import java.awt.event.*;

/**
 * Autoscroller is responsible for generating synthetic mouse dragged
 * events. It is the responsibility of the Component (or its MouseListeners)
 * that receive the events to do the actual scrolling in response to the
 * mouse dragged events.
 *
 * <p>
 *  Autoscroller负责生成合成鼠标拖动事件。它是组件(或其MouseListeners)的责任,接收事件做实际滚动响应鼠标拖动事件。
 * 
 * 
 * @author Dave Moore
 * @author Scott Violet
 */
class Autoscroller implements ActionListener {
    /**
     * Global Autoscroller.
     * <p>
     *  全局自动滚动。
     * 
     */
    private static Autoscroller sharedInstance = new Autoscroller();

    // As there can only ever be one autoscroller active these fields are
    // static. The Timer is recreated as necessary to target the appropriate
    // Autoscroller instance.
    private static MouseEvent event;
    private static Timer timer;
    private static JComponent component;

    //
    // The public API, all methods are cover methods for an instance method
    //
    /**
     * Stops autoscroll events from happening on the specified component.
     * <p>
     *  停止在指定组件上发生自动滚动事件。
     * 
     */
    public static void stop(JComponent c) {
        sharedInstance._stop(c);
    }

    /**
     * Stops autoscroll events from happening on the specified component.
     * <p>
     *  停止在指定组件上发生自动滚动事件。
     * 
     */
    public static boolean isRunning(JComponent c) {
        return sharedInstance._isRunning(c);
    }

    /**
     * Invoked when a mouse dragged event occurs, will start the autoscroller
     * if necessary.
     * <p>
     *  在发生鼠标拖动事件时调用,将在必要时启动自动滚动器。
     * 
     */
    public static void processMouseDragged(MouseEvent e) {
        sharedInstance._processMouseDragged(e);
    }


    Autoscroller() {
    }

    /**
     * Starts the timer targeting the passed in component.
     * <p>
     *  启动定向传递组件的计时器。
     * 
     */
    private void start(JComponent c, MouseEvent e) {
        Point screenLocation = c.getLocationOnScreen();

        if (component != c) {
            _stop(component);
        }
        component = c;
        event = new MouseEvent(component, e.getID(), e.getWhen(),
                               e.getModifiers(), e.getX() + screenLocation.x,
                               e.getY() + screenLocation.y,
                               e.getXOnScreen(),
                               e.getYOnScreen(),
                               e.getClickCount(), e.isPopupTrigger(),
                               MouseEvent.NOBUTTON);

        if (timer == null) {
            timer = new Timer(100, this);
        }

        if (!timer.isRunning()) {
            timer.start();
        }
    }

    //
    // Methods mirror the public static API
    //

    /**
     * Stops scrolling for the passed in widget.
     * <p>
     *  停止传递的窗口小部件的滚动。
     * 
     */
    private void _stop(JComponent c) {
        if (component == c) {
            if (timer != null) {
                timer.stop();
            }
            timer = null;
            event = null;
            component = null;
        }
    }

    /**
     * Returns true if autoscrolling is currently running for the specified
     * widget.
     * <p>
     *  如果当前正在为指定的窗口小部件运行自动滚动,则返回true。
     * 
     */
    private boolean _isRunning(JComponent c) {
        return (c == component && timer != null && timer.isRunning());
    }

    /**
     * MouseListener method, invokes start/stop as necessary.
     * <p>
     *  MouseListener方法,根据需要调用start / stop。
     * 
     */
    private void _processMouseDragged(MouseEvent e) {
        JComponent component = (JComponent)e.getComponent();
        boolean stop = true;
        if (component.isShowing()) {
            Rectangle visibleRect = component.getVisibleRect();
            stop = visibleRect.contains(e.getX(), e.getY());
        }
        if (stop) {
            _stop(component);
        } else {
            start(component, e);
        }
    }

    //
    // ActionListener
    //
    /**
     * ActionListener method. Invoked when the Timer fires. This will scroll
     * if necessary.
     * <p>
     *  ActionListener方法。定时器触发时调用。如果需要,将滚动。
     */
    public void actionPerformed(ActionEvent x) {
        JComponent component = Autoscroller.component;

        if (component == null || !component.isShowing() || (event == null)) {
            _stop(component);
            return;
        }
        Point screenLocation = component.getLocationOnScreen();
        MouseEvent e = new MouseEvent(component, event.getID(),
                                      event.getWhen(), event.getModifiers(),
                                      event.getX() - screenLocation.x,
                                      event.getY() - screenLocation.y,
                                      event.getXOnScreen(),
                                      event.getYOnScreen(),
                                      event.getClickCount(),
                                      event.isPopupTrigger(),
                                      MouseEvent.NOBUTTON);
        component.superProcessMouseMotionEvent(e);
    }

}
