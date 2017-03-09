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

import java.awt.event.InputEvent;
import java.awt.Component;
import java.awt.Point;

import java.io.InvalidObjectException;
import java.util.Collections;
import java.util.TooManyListenersException;
import java.util.ArrayList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The <code>DragGestureRecognizer</code> is an
 * abstract base class for the specification
 * of a platform-dependent listener that can be associated with a particular
 * <code>Component</code> in order to
 * identify platform-dependent drag initiating gestures.
 * <p>
 * The appropriate <code>DragGestureRecognizer</code>
 * subclass instance is obtained from the
 * {@link DragSource} associated with
 * a particular <code>Component</code>, or from the <code>Toolkit</code> object via its
 * {@link java.awt.Toolkit#createDragGestureRecognizer createDragGestureRecognizer()}
 * method.
 * <p>
 * Once the <code>DragGestureRecognizer</code>
 * is associated with a particular <code>Component</code>
 * it will register the appropriate listener interfaces on that
 * <code>Component</code>
 * in order to track the input events delivered to the <code>Component</code>.
 * <p>
 * Once the <code>DragGestureRecognizer</code> identifies a sequence of events
 * on the <code>Component</code> as a drag initiating gesture, it will notify
 * its unicast <code>DragGestureListener</code> by
 * invoking its
 * {@link java.awt.dnd.DragGestureListener#dragGestureRecognized gestureRecognized()}
 * method.
 * <P>
 * When a concrete <code>DragGestureRecognizer</code>
 * instance detects a drag initiating
 * gesture on the <code>Component</code> it is associated with,
 * it fires a {@link DragGestureEvent} to
 * the <code>DragGestureListener</code> registered on
 * its unicast event source for <code>DragGestureListener</code>
 * events. This <code>DragGestureListener</code> is responsible
 * for causing the associated
 * <code>DragSource</code> to start the Drag and Drop operation (if
 * appropriate).
 * <P>
 * <p>
 *  <code> DragGestureRecognizer </code>是用于指定与平台相关的监听器的抽象基类,其可以与特定的<code> Component </code>相关联,以便识别依赖于平台
 * 的拖动发起手势。
 * <p>
 *  适当的<code> DragGestureRecognizer </code>子类实例是从与特定<code> Component </code>相关联的{@link DragSource}或通过其{@链接java.awt.Toolkit#createDragGestureRecognizer createDragGestureRecognizer()}
 * 方法。
 * <p>
 *  一旦<code> DragGestureRecognizer </code>与特定<code> Component </code>相关联,它将在<code> Component </code>上注册适
 * 当的侦听器接口,以跟踪传递到<code>组件</code>。
 * <p>
 *  一旦<code> DragGestureRecognizer </code>将<code> Component </code>上的事件序列识别为拖动发起手势,它就会通过调用其{@link)来通知其单播<code> DragGestureListener </code> java.awt.dnd.DragGestureListener#dragGestureRecognized gestureRecognized()}
 * 方法。
 * <P>
 * 当具体的<code> DragGestureRecognizer </code>实例检测到与它相关联的<code> Component </code>上的拖动启动手势时,会向{code} DragGes
 * tureListener </code>在其单播事件源上注册<code> DragGestureListener </code>事件。
 * 此<code> DragGestureListener </code>负责使关联的<code> DragSource </code>启动拖放操作(如果适用)。
 * <P>
 * 
 * @author Laurence P. G. Cable
 * @see java.awt.dnd.DragGestureListener
 * @see java.awt.dnd.DragGestureEvent
 * @see java.awt.dnd.DragSource
 */

public abstract class DragGestureRecognizer implements Serializable {

    private static final long serialVersionUID = 8996673345831063337L;

    /**
     * Construct a new <code>DragGestureRecognizer</code>
     * given the <code>DragSource</code> to be used
     * in this Drag and Drop operation, the <code>Component</code>
     * this <code>DragGestureRecognizer</code> should "observe"
     * for drag initiating gestures, the action(s) supported
     * for this Drag and Drop operation, and the
     * <code>DragGestureListener</code> to notify
     * once a drag initiating gesture has been detected.
     * <P>
     * <p>
     *  构造一个新的<code> DragGestureRecognizer </code>给定</code> DragSource </code>以用于此拖放操作,<code> Component </code>
     *  this <code> DragGestureRecognizer </code> "观察"拖动发起手势,该拖放操作支持的动作,以及一旦检测到拖动发起手势就通知<code> DragGestureLi
     * stener </code>。
     * <P>
     * 
     * @param ds  the <code>DragSource</code> this
     * <code>DragGestureRecognizer</code>
     * will use to process the Drag and Drop operation
     *
     * @param c the <code>Component</code>
     * this <code>DragGestureRecognizer</code>
     * should "observe" the event stream to,
     * in order to detect a drag initiating gesture.
     * If this value is <code>null</code>, the
     * <code>DragGestureRecognizer</code>
     * is not associated with any <code>Component</code>.
     *
     * @param sa  the set (logical OR) of the
     * <code>DnDConstants</code>
     * that this Drag and Drop operation will support
     *
     * @param dgl the <code>DragGestureRecognizer</code>
     * to notify when a drag gesture is detected
     * <P>
     * @throws IllegalArgumentException
     * if ds is <code>null</code>.
     */

    protected DragGestureRecognizer(DragSource ds, Component c, int sa, DragGestureListener dgl) {
        super();

        if (ds == null) throw new IllegalArgumentException("null DragSource");

        dragSource    = ds;
        component     = c;
        sourceActions = sa & (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);

        try {
            if (dgl != null) addDragGestureListener(dgl);
        } catch (TooManyListenersException tmle) {
            // cant happen ...
        }
    }

    /**
     * Construct a new <code>DragGestureRecognizer</code>
     * given the <code>DragSource</code> to be used in this
     * Drag and Drop
     * operation, the <code>Component</code> this
     * <code>DragGestureRecognizer</code> should "observe"
     * for drag initiating gestures, and the action(s)
     * supported for this Drag and Drop operation.
     * <P>
     * <p>
     *  构造一个新的<code> DragGestureRecognizer </code>给定</code> DragSource </code>以用于此拖放操作,<code> Component </code>
     *  this <code> DragGestureRecognizer </code> "观察"拖动发起手势,以及此拖放操作支持的操作。
     * <P>
     * 
     * @param ds  the <code>DragSource</code> this
     * <code>DragGestureRecognizer</code> will use to
     * process the Drag and Drop operation
     *
     * @param c   the <code>Component</code> this
     * <code>DragGestureRecognizer</code> should "observe" the event
     * stream to, in order to detect a drag initiating gesture.
     * If this value is <code>null</code>, the
     * <code>DragGestureRecognizer</code>
     * is not associated with any <code>Component</code>.
     *
     * @param sa the set (logical OR) of the <code>DnDConstants</code>
     * that this Drag and Drop operation will support
     * <P>
     * @throws IllegalArgumentException
     * if ds is <code>null</code>.
     */

    protected DragGestureRecognizer(DragSource ds, Component c, int sa) {
        this(ds, c, sa, null);
    }

    /**
     * Construct a new <code>DragGestureRecognizer</code>
     * given the <code>DragSource</code> to be used
     * in this Drag and Drop operation, and
     * the <code>Component</code> this
     * <code>DragGestureRecognizer</code>
     * should "observe" for drag initiating gestures.
     * <P>
     * <p>
     *  给定在此拖放操作中使用的<code> DragSource </code>以及<code> Component </code>此<code> DragGestureRecognizer </code>
     * 构造新的<code> DragGestureRecognizer </code>应该"观察"拖动发起手势。
     * <P>
     * 
     * @param ds the <code>DragSource</code> this
     * <code>DragGestureRecognizer</code>
     * will use to process the Drag and Drop operation
     *
     * @param c the <code>Component</code>
     * this <code>DragGestureRecognizer</code>
     * should "observe" the event stream to,
     * in order to detect a drag initiating gesture.
     * If this value is <code>null</code>,
     * the <code>DragGestureRecognizer</code>
     * is not associated with any <code>Component</code>.
     * <P>
     * @throws IllegalArgumentException
     * if ds is <code>null</code>.
     */

    protected DragGestureRecognizer(DragSource ds, Component c) {
        this(ds, c, DnDConstants.ACTION_NONE);
    }

    /**
     * Construct a new <code>DragGestureRecognizer</code>
     * given the <code>DragSource</code> to be used in this
     * Drag and Drop operation.
     * <P>
     * <p>
     * 构造一个新的&lt; code&gt; DragGestureRecognizer&lt; / code&gt;给定用于拖放操作的<code> DragSource </code>。
     * <P>
     * 
     * @param ds the <code>DragSource</code> this
     * <code>DragGestureRecognizer</code> will
     * use to process the Drag and Drop operation
     * <P>
     * @throws IllegalArgumentException
     * if ds is <code>null</code>.
     */

    protected DragGestureRecognizer(DragSource ds) {
        this(ds, null);
    }

    /**
     * register this DragGestureRecognizer's Listeners with the Component
     *
     * subclasses must override this method
     * <p>
     *  使用Component注册此DragGestureRecognizer的Listeners
     * 
     *  子类必须重写此方法
     * 
     */

    protected abstract void registerListeners();

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

    protected abstract void unregisterListeners();

    /**
     * This method returns the <code>DragSource</code>
     * this <code>DragGestureRecognizer</code>
     * will use in order to process the Drag and Drop
     * operation.
     * <P>
     * <p>
     *  此方法返回<code> DragSource </code>此<code> DragGestureRecognizer </code>将使用为了处理拖放操作。
     * <P>
     * 
     * @return the DragSource
     */

    public DragSource getDragSource() { return dragSource; }

    /**
     * This method returns the <code>Component</code>
     * that is to be "observed" by the
     * <code>DragGestureRecognizer</code>
     * for drag initiating gestures.
     * <P>
     * <p>
     *  此方法返回由<code> DragGestureRecognizer </code>"监视"的用于拖动启动手势的<code> Component </code>。
     * <P>
     * 
     * @return The Component this DragGestureRecognizer
     * is associated with
     */

    public synchronized Component getComponent() { return component; }

    /**
     * set the Component that the DragGestureRecognizer is associated with
     *
     * registerListeners() and unregisterListeners() are called as a side
     * effect as appropriate.
     * <P>
     * <p>
     *  设置DragGestureRecognizer所关联的组件
     * 
     *  registerListeners()和unregisterListeners()作为副作用调用。
     * <P>
     * 
     * @param c The <code>Component</code> or <code>null</code>
     */

    public synchronized void setComponent(Component c) {
        if (component != null && dragGestureListener != null)
            unregisterListeners();

        component = c;

        if (component != null && dragGestureListener != null)
            registerListeners();
    }

    /**
     * This method returns an int representing the
     * type of action(s) this Drag and Drop
     * operation will support.
     * <P>
     * <p>
     *  此方法返回一个int,表示此拖放操作将支持的操作类型。
     * <P>
     * 
     * @return the currently permitted source action(s)
     */

    public synchronized int getSourceActions() { return sourceActions; }

    /**
     * This method sets the permitted source drag action(s)
     * for this Drag and Drop operation.
     * <P>
     * <p>
     *  此方法为此拖放操作设置允许的源拖曳动作。
     * <P>
     * 
     * @param actions the permitted source drag action(s)
     */

    public synchronized void setSourceActions(int actions) {
        sourceActions = actions & (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);
    }

    /**
     * This method returns the first event in the
     * series of events that initiated
     * the Drag and Drop operation.
     * <P>
     * <p>
     *  此方法返回启动拖放操作的一系列事件中的第一个事件。
     * <P>
     * 
     * @return the initial event that triggered the drag gesture
     */

    public InputEvent getTriggerEvent() { return events.isEmpty() ? null : events.get(0); }

    /**
     * Reset the Recognizer, if its currently recognizing a gesture, ignore
     * it.
     * <p>
     *  重置识别器,如果它当前识别一个手势,忽略它。
     * 
     */

    public void resetRecognizer() { events.clear(); }

    /**
     * Register a new <code>DragGestureListener</code>.
     * <P>
     * <p>
     *  注册新的<code> DragGestureListener </code>。
     * <P>
     * 
     * @param dgl the <code>DragGestureListener</code> to register
     * with this <code>DragGestureRecognizer</code>.
     * <P>
     * @throws java.util.TooManyListenersException if a
     * <code>DragGestureListener</code> has already been added.
     */

    public synchronized void addDragGestureListener(DragGestureListener dgl) throws TooManyListenersException {
        if (dragGestureListener != null)
            throw new TooManyListenersException();
        else {
            dragGestureListener = dgl;

            if (component != null) registerListeners();
        }
    }

    /**
     * unregister the current DragGestureListener
     * <P>
     * <p>
     *  注销当前DragGestureListener
     * <P>
     * 
     * @param dgl the <code>DragGestureListener</code> to unregister
     * from this <code>DragGestureRecognizer</code>
     * <P>
     * @throws IllegalArgumentException if
     * dgl is not (equal to) the currently registered <code>DragGestureListener</code>.
     */

    public synchronized void removeDragGestureListener(DragGestureListener dgl) {
        if (dragGestureListener == null || !dragGestureListener.equals(dgl))
            throw new IllegalArgumentException();
        else {
            dragGestureListener = null;

            if (component != null) unregisterListeners();
        }
    }

    /**
     * Notify the DragGestureListener that a Drag and Drop initiating
     * gesture has occurred. Then reset the state of the Recognizer.
     * <P>
     * <p>
     *  通知DragGestureListener已发生拖放启动手势。然后重置识别器的状态。
     * <P>
     * 
     * @param dragAction The action initially selected by the users gesture
     * @param p          The point (in Component coords) where the gesture originated
     */
    protected synchronized void fireDragGestureRecognized(int dragAction, Point p) {
        try {
            if (dragGestureListener != null) {
                dragGestureListener.dragGestureRecognized(new DragGestureEvent(this, dragAction, p, events));
            }
        } finally {
            events.clear();
        }
    }

    /**
     * Listeners registered on the Component by this Recognizer shall record
     * all Events that are recognized as part of the series of Events that go
     * to comprise a Drag and Drop initiating gesture via this API.
     *<P>
     * This method is used by a <code>DragGestureRecognizer</code>
     * implementation to add an <code>InputEvent</code>
     * subclass (that it believes is one in a series
     * of events that comprise a Drag and Drop operation)
     * to the array of events that this
     * <code>DragGestureRecognizer</code> maintains internally.
     * <P>
     * <p>
     * 此识别器在组件上注册的侦听器应记录所有被识别为通过此API构成拖放启动手势的事件系列的一部分的事件。
     * P>
     *  这个方法被一个<code> DragGestureRecognizer </code>实现使用来添加一个<code> InputEvent </code>子类(它认为是一系列包含拖放操作的事件中的一个
     * )到数组这个<code> DragGestureRecognizer </code>内部维护的事件。
     * <P>
     * 
     * @param awtie the <code>InputEvent</code>
     * to add to this <code>DragGestureRecognizer</code>'s
     * internal array of events. Note that <code>null</code>
     * is not a valid value, and will be ignored.
     */

    protected synchronized void appendEvent(InputEvent awtie) {
        events.add(awtie);
    }

    /**
     * Serializes this <code>DragGestureRecognizer</code>. This method first
     * performs default serialization. Then, this object's
     * <code>DragGestureListener</code> is written out if and only if it can be
     * serialized. If not, <code>null</code> is written instead.
     *
     * <p>
     *  序列化此<code> DragGestureRecognizer </code>。此方法首先执行默认序列化。
     * 然后,当且仅当它可以被序列化时,该对象的<code> DragGestureListener </code>被写出。如果不是,写入<code> null </code>。
     * 
     * 
     * @serialData The default serializable fields, in alphabetical order,
     *             followed by either a <code>DragGestureListener</code>, or
     *             <code>null</code>.
     * @since 1.4
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeObject(SerializationTester.test(dragGestureListener)
                      ? dragGestureListener : null);
    }

    /**
     * Deserializes this <code>DragGestureRecognizer</code>. This method first
     * performs default deserialization for all non-<code>transient</code>
     * fields. This object's <code>DragGestureListener</code> is then
     * deserialized as well by using the next object in the stream.
     *
     * <p>
     *  反序列化此<code> DragGestureRecognizer </code>。该方法首先对所有非<code> transient </code>字段执行默认反序列化。
     * 然后,通过使用流中的下一个对象,该对象的<code> DragGestureListener </code>也被反序列化。
     * 
     * 
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        ObjectInputStream.GetField f = s.readFields();

        DragSource newDragSource = (DragSource)f.get("dragSource", null);
        if (newDragSource == null) {
            throw new InvalidObjectException("null DragSource");
        }
        dragSource = newDragSource;

        component = (Component)f.get("component", null);
        sourceActions = f.get("sourceActions", 0) & (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);
        events = (ArrayList<InputEvent>)f.get("events", new ArrayList<>(1));

        dragGestureListener = (DragGestureListener)s.readObject();
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * The <code>DragSource</code>
     * associated with this
     * <code>DragGestureRecognizer</code>.
     *
     * <p>
     *  与此<code> DragGestureRecognizer </code>关联的<code> DragSource </code>。
     * 
     * 
     * @serial
     */
    protected DragSource          dragSource;

    /**
     * The <code>Component</code>
     * associated with this <code>DragGestureRecognizer</code>.
     *
     * <p>
     *  与此<code> DragGestureRecognizer </code>关联的<code>组件</code>。
     * 
     * 
     * @serial
     */
    protected Component           component;

    /**
     * The <code>DragGestureListener</code>
     * associated with this <code>DragGestureRecognizer</code>.
     * <p>
     *  与此<code> DragGestureRecognizer </code>关联的<code> DragGestureListener </code>。
     * 
     */
    protected transient DragGestureListener dragGestureListener;

  /**
   * An <code>int</code> representing
   * the type(s) of action(s) used
   * in this Drag and Drop operation.
   *
   * <p>
   *  表示此拖放操作中使用的操作类型的<code> int </code>。
   * 
   * 
   * @serial
   */
  protected int  sourceActions;

   /**
    * The list of events (in order) that
    * the <code>DragGestureRecognizer</code>
    * "recognized" as a "gesture" that triggers a drag.
    *
    * <p>
    * 事件列表(按顺序),将<code> DragGestureRecognizer </code>"识别为"触发拖动的"手势"。
    * 
    * @serial
    */
   protected ArrayList<InputEvent> events = new ArrayList<InputEvent>(1);
}
