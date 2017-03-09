/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

import java.util.EventListener;

/**
 * A listener interface for receiving mouse motion events during a drag
 * operation.
 * <p>
 * The class that is interested in processing mouse motion events during
 * a drag operation either implements this interface or extends the abstract
 * <code>DragSourceAdapter</code> class (overriding only the methods of
 * interest).
 * <p>
 * Create a listener object using that class and then register it with
 * a <code>DragSource</code>. Whenever the mouse moves during a drag
 * operation initiated with this <code>DragSource</code>, that object's
 * <code>dragMouseMoved</code> method is invoked, and the
 * <code>DragSourceDragEvent</code> is passed to it.
 *
 * <p>
 *  用于在拖动操作期间接收鼠标运动事件的侦听器接口。
 * <p>
 *  在拖动操作期间对处理鼠标运动事件感兴趣的类实现此接口或扩展抽象<code> DragSourceAdapter </code>类(仅覆盖感兴趣的方法)。
 * <p>
 *  使用该类创建一个监听器对象,然后用<code> DragSource </code>注册它。
 * 每当鼠标在使用此<code> DragSource </code>启动的拖动操作期间移动时,将调用该对象的<code> dragMouseMoved </code>方法,并将<code> DragSou
 * 
 * @see DragSourceDragEvent
 * @see DragSource
 * @see DragSourceListener
 * @see DragSourceAdapter
 *
 * @since 1.4
 */

public interface DragSourceMotionListener extends EventListener {

    /**
     * Called whenever the mouse is moved during a drag operation.
     *
     * <p>
     * rceDragEvent </code>传递给它。
     *  使用该类创建一个监听器对象,然后用<code> DragSource </code>注册它。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void dragMouseMoved(DragSourceDragEvent dsde);
}
