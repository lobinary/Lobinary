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



package java.awt.dnd;

import java.util.EventListener;

/**
 * The listener interface for receiving drag gesture events.
 * This interface is intended for a drag gesture recognition
 * implementation. See a specification for {@code DragGestureRecognizer}
 * for details on how to register the listener interface.
 * Upon recognition of a drag gesture the {@code
 * DragGestureRecognizer} calls this interface's
 * {@link #dragGestureRecognized dragGestureRecognized()}
 * method and passes a {@code DragGestureEvent}.

 *
 * <p>
 *  用于接收拖动手势事件的侦听器接口。该接口旨在用于拖动姿势识别实现。有关如何注册侦听器接口的详细信息,请参阅{@code DragGestureRecognizer}的规范。
 * 识别拖动手势后,{@code DragGestureRecognizer}调用此界面的{@link #dragGestureRecognized dragGestureRecognized()}方法,并
 * 传递{@code DragGestureEvent}。
 *  用于接收拖动手势事件的侦听器接口。该接口旨在用于拖动姿势识别实现。有关如何注册侦听器接口的详细信息,请参阅{@code DragGestureRecognizer}的规范。
 * 
 * 
 * @see java.awt.dnd.DragGestureRecognizer
 * @see java.awt.dnd.DragGestureEvent
 * @see java.awt.dnd.DragSource
 */

 public interface DragGestureListener extends EventListener {

    /**
     * This method is invoked by the {@code DragGestureRecognizer}
     * when the {@code DragGestureRecognizer} detects a platform-dependent
     * drag initiating gesture. To initiate the drag and drop operation,
     * if appropriate, {@link DragGestureEvent#startDrag startDrag()} method on
     * the {@code DragGestureEvent} has to be invoked.
     * <P>
     * <p>
     * 
     * @see java.awt.dnd.DragGestureRecognizer
     * @see java.awt.dnd.DragGestureEvent
     * @param dge the <code>DragGestureEvent</code> describing
     * the gesture that has just occurred
     */

     void dragGestureRecognized(DragGestureEvent dge);
}
