/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.AWTEventMulticaster;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.EventListener;


/**
 * A class extends <code>AWTEventMulticaster</code> to implement efficient and
 * thread-safe multi-cast event dispatching for the drag-and-drop events defined
 * in the java.awt.dnd package.
 *
 * <p>
 *  类扩展了<code> AWTEventMulticaster </code>,以便为java.awt.dnd包中定义的拖放事件实现高效的线程安全的多播事件分派。
 * 
 * 
 * @since       1.4
 * @see AWTEventMulticaster
 */

class DnDEventMulticaster extends AWTEventMulticaster
    implements DragSourceListener, DragSourceMotionListener {

    /**
     * Creates an event multicaster instance which chains listener-a
     * with listener-b. Input parameters <code>a</code> and <code>b</code>
     * should not be <code>null</code>, though implementations may vary in
     * choosing whether or not to throw <code>NullPointerException</code>
     * in that case.
     *
     * <p>
     *  创建一个事件multicaster实例,它将listener-a与listener-b链接。
     * 输入参数<code> a </code>和<code> b </code>不应该是<code> null </code>,但是选择是否抛出<code>在这种情况下。
     * 
     * 
     * @param a listener-a
     * @param b listener-b
     */
    protected DnDEventMulticaster(EventListener a, EventListener b) {
        super(a,b);
    }

    /**
     * Handles the <code>DragSourceDragEvent</code> by invoking
     * <code>dragEnter</code> on listener-a and listener-b.
     *
     * <p>
     *  通过在侦听器a和侦听器b上调用<code> dragEnter </code>来处理<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragEnter(DragSourceDragEvent dsde) {
        ((DragSourceListener)a).dragEnter(dsde);
        ((DragSourceListener)b).dragEnter(dsde);
    }

    /**
     * Handles the <code>DragSourceDragEvent</code> by invoking
     * <code>dragOver</code> on listener-a and listener-b.
     *
     * <p>
     *  通过在侦听器a和侦听器b上调用<code> dragOver </code>来处理<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragOver(DragSourceDragEvent dsde) {
        ((DragSourceListener)a).dragOver(dsde);
        ((DragSourceListener)b).dragOver(dsde);
    }

    /**
     * Handles the <code>DragSourceDragEvent</code> by invoking
     * <code>dropActionChanged</code> on listener-a and listener-b.
     *
     * <p>
     *  通过在侦听器a和侦听器b上调用<code> dropActionChanged </code>来处理<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dropActionChanged(DragSourceDragEvent dsde) {
        ((DragSourceListener)a).dropActionChanged(dsde);
        ((DragSourceListener)b).dropActionChanged(dsde);
    }

    /**
     * Handles the <code>DragSourceEvent</code> by invoking
     * <code>dragExit</code> on listener-a and listener-b.
     *
     * <p>
     *  通过在侦听器a和侦听器b上调用<code> dragExit </code>来处理<code> DragSourceEvent </code>。
     * 
     * 
     * @param dse the <code>DragSourceEvent</code>
     */
    public void dragExit(DragSourceEvent dse) {
        ((DragSourceListener)a).dragExit(dse);
        ((DragSourceListener)b).dragExit(dse);
    }

    /**
     * Handles the <code>DragSourceDropEvent</code> by invoking
     * <code>dragDropEnd</code> on listener-a and listener-b.
     *
     * <p>
     *  通过调用侦听器a和侦听器b上的<code> dragDropEnd </code>来处理<code> DragSourceDropEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDropEvent</code>
     */
    public void dragDropEnd(DragSourceDropEvent dsde) {
        ((DragSourceListener)a).dragDropEnd(dsde);
        ((DragSourceListener)b).dragDropEnd(dsde);
    }

    /**
     * Handles the <code>DragSourceDragEvent</code> by invoking
     * <code>dragMouseMoved</code> on listener-a and listener-b.
     *
     * <p>
     *  通过调用侦听器a和侦听器b上的<code> dragMouseMoved </code>来处理<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragMouseMoved(DragSourceDragEvent dsde) {
        ((DragSourceMotionListener)a).dragMouseMoved(dsde);
        ((DragSourceMotionListener)b).dragMouseMoved(dsde);
    }

    /**
     * Adds drag-source-listener-a with drag-source-listener-b and
     * returns the resulting multicast listener.
     *
     * <p>
     *  使用drag-source-listener-b添加drag-source-listener-a并返回最终的组播侦听器。
     * 
     * 
     * @param a drag-source-listener-a
     * @param b drag-source-listener-b
     */
    public static DragSourceListener add(DragSourceListener a,
                                         DragSourceListener b) {
        return (DragSourceListener)addInternal(a, b);
    }

    /**
     * Adds drag-source-motion-listener-a with drag-source-motion-listener-b and
     * returns the resulting multicast listener.
     *
     * <p>
     * 使用drag-source-motion-listener-b添加drag-source-motion-listener-a并返回最终的组播侦听器。
     * 
     * 
     * @param a drag-source-motion-listener-a
     * @param b drag-source-motion-listener-b
     */
    public static DragSourceMotionListener add(DragSourceMotionListener a,
                                               DragSourceMotionListener b) {
        return (DragSourceMotionListener)addInternal(a, b);
    }

    /**
     * Removes the old drag-source-listener from drag-source-listener-l
     * and returns the resulting multicast listener.
     *
     * <p>
     *  从drag-source-listener-l中删除旧的drag-source-listener,并返回最终的组播侦听器。
     * 
     * 
     * @param l drag-source-listener-l
     * @param oldl the drag-source-listener being removed
     */
    public static DragSourceListener remove(DragSourceListener l,
                                            DragSourceListener oldl) {
        return (DragSourceListener)removeInternal(l, oldl);
    }

    /**
     * Removes the old drag-source-motion-listener from
     * drag-source-motion-listener-l and returns the resulting multicast
     * listener.
     *
     * <p>
     *  从drag-source-motion-listener-l中删除旧的拖拽源运动监听器并返回结果组播侦听器。
     * 
     * 
     * @param l drag-source-motion-listener-l
     * @param ol the drag-source-motion-listener being removed
     */
    public static DragSourceMotionListener remove(DragSourceMotionListener l,
                                                  DragSourceMotionListener ol) {
        return (DragSourceMotionListener)removeInternal(l, ol);
    }

    /**
     * Returns the resulting multicast listener from adding listener-a
     * and listener-b together.
     * If listener-a is null, it returns listener-b;
     * If listener-b is null, it returns listener-a
     * If neither are null, then it creates and returns
     * a new AWTEventMulticaster instance which chains a with b.
     * <p>
     *  返回结果多播侦听器从添加侦听器a和侦听器b在一起。
     * 如果listener-a为null,则返回listener-b;如果listener-b为null,它返回listener-a如果两者都不为null,则它创建并返回一个新的AWTEventMultica
     * ster实例,它将a与a连接。
     *  返回结果多播侦听器从添加侦听器a和侦听器b在一起。
     * 
     * 
     * @param a event listener-a
     * @param b event listener-b
     */
    protected static EventListener addInternal(EventListener a, EventListener b) {
        if (a == null)  return b;
        if (b == null)  return a;
        return new DnDEventMulticaster(a, b);
    }

    /**
     * Removes a listener from this multicaster and returns the
     * resulting multicast listener.
     * <p>
     *  从此多任务器删除侦听器并返回结果多播侦听器。
     * 
     * 
     * @param oldl the listener to be removed
     */
    protected EventListener remove(EventListener oldl) {
        if (oldl == a)  return b;
        if (oldl == b)  return a;
        EventListener a2 = removeInternal(a, oldl);
        EventListener b2 = removeInternal(b, oldl);
        if (a2 == a && b2 == b) {
            return this;        // it's not here
        }
        return addInternal(a2, b2);
    }

    /**
     * Returns the resulting multicast listener after removing the
     * old listener from listener-l.
     * If listener-l equals the old listener OR listener-l is null,
     * returns null.
     * Else if listener-l is an instance of AWTEventMulticaster,
     * then it removes the old listener from it.
     * Else, returns listener l.
     * <p>
     *  从侦听器-l中删除旧的侦听器后,返回结果组播侦听器。如果listener-l等于旧侦听器或listener-l为null,则返回null。
     * 否则,如果listener-l是AWTEventMulticaster的实例,那么它会从中删除旧的侦听器。否则,返回侦听器l。
     * 
     * @param l the listener being removed from
     * @param oldl the listener being removed
     */
    protected static EventListener removeInternal(EventListener l, EventListener oldl) {
        if (l == oldl || l == null) {
            return null;
        } else if (l instanceof DnDEventMulticaster) {
            return ((DnDEventMulticaster)l).remove(oldl);
        } else {
            return l;           // it's not here
        }
    }

    protected static void save(ObjectOutputStream s, String k, EventListener l)
      throws IOException {
        AWTEventMulticaster.save(s, k, l);
    }
}
