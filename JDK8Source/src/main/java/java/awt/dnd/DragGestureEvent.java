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

import java.awt.Component;
import java.awt.Cursor;

import java.awt.Image;
import java.awt.Point;

import java.awt.event.InputEvent;

import java.awt.datatransfer.Transferable;

import java.io.InvalidObjectException;
import java.util.EventObject;

import java.util.Collections;
import java.util.List;
import java.util.Iterator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * A <code>DragGestureEvent</code> is passed
 * to <code>DragGestureListener</code>'s
 * dragGestureRecognized() method
 * when a particular <code>DragGestureRecognizer</code> detects that a
 * platform dependent drag initiating gesture has occurred
 * on the <code>Component</code> that it is tracking.
 *
 * The {@code action} field of any {@code DragGestureEvent} instance should take one of the following
 * values:
 * <ul>
 * <li> {@code DnDConstants.ACTION_COPY}
 * <li> {@code DnDConstants.ACTION_MOVE}
 * <li> {@code DnDConstants.ACTION_LINK}
 * </ul>
 * Assigning the value different from listed above will cause an unspecified behavior.
 *
 * <p>
 *  当特定的<code> DragGestureRecognizer </code>检测到平台相关的拖动启动手势发生在<code> DragGestureRecognized()方法时,将<code> D
 * ragGestureEvent </code>代码>组件</code>。
 * 
 *  任何{@code DragGestureEvent}实例的{@code action}字段应采用以下值之一：
 * <ul>
 *  <li> {@code DnDConstants.ACTION_COPY} <li> {@code DnDConstants.ACTION_MOVE} <li> {@code DnDConstants.ACTION_LINK}
 * 。
 * </ul>
 *  分配与上述不同的值将导致未指定的行为。
 * 
 * 
 * @see java.awt.dnd.DragGestureRecognizer
 * @see java.awt.dnd.DragGestureListener
 * @see java.awt.dnd.DragSource
 * @see java.awt.dnd.DnDConstants
 */

public class DragGestureEvent extends EventObject {

    private static final long serialVersionUID = 9080172649166731306L;

    /**
     * Constructs a <code>DragGestureEvent</code> object given by the
     * <code>DragGestureRecognizer</code> instance firing this event,
     * an {@code act} parameter representing
     * the user's preferred action, an {@code ori} parameter
     * indicating the origin of the drag, and a {@code List} of
     * events that comprise the gesture({@code evs} parameter).
     * <P>
     * <p>
     *  构造由触发此事件的<code> DragGestureRecognizer </code>实例给出的<code> DragGestureEvent </code>对象,表示用户首选操作的{@code act}
     * 参数,表示用户首选操作的{@code ori} ,以及构成手势({@ code evs}参数)的{@code List}事件。
     * <P>
     * 
     * @param dgr The <code>DragGestureRecognizer</code> firing this event
     * @param act The user's preferred action.
     *            For information on allowable values, see
     *            the class description for {@link DragGestureEvent}
     * @param ori The origin of the drag
     * @param evs The <code>List</code> of events that comprise the gesture
     * <P>
     * @throws IllegalArgumentException if any parameter equals {@code null}
     * @throws IllegalArgumentException if the act parameter does not comply with
     *                                  the values given in the class
     *                                  description for {@link DragGestureEvent}
     * @see java.awt.dnd.DnDConstants
     */

    public DragGestureEvent(DragGestureRecognizer dgr, int act, Point ori,
                            List<? extends InputEvent> evs)
    {
        super(dgr);

        if ((component = dgr.getComponent()) == null)
            throw new IllegalArgumentException("null component");
        if ((dragSource = dgr.getDragSource()) == null)
            throw new IllegalArgumentException("null DragSource");

        if (evs == null || evs.isEmpty())
            throw new IllegalArgumentException("null or empty list of events");

        if (act != DnDConstants.ACTION_COPY &&
            act != DnDConstants.ACTION_MOVE &&
            act != DnDConstants.ACTION_LINK)
            throw new IllegalArgumentException("bad action");

        if (ori == null) throw new IllegalArgumentException("null origin");

        events     = evs;
        action     = act;
        origin     = ori;
    }

    /**
     * Returns the source as a <code>DragGestureRecognizer</code>.
     * <P>
     * <p>
     *  将源作为<code> DragGestureRecognizer </code>返回。
     * <P>
     * 
     * @return the source as a <code>DragGestureRecognizer</code>
     */

    public DragGestureRecognizer getSourceAsDragGestureRecognizer() {
        return (DragGestureRecognizer)getSource();
    }

    /**
     * Returns the <code>Component</code> associated
     * with this <code>DragGestureEvent</code>.
     * <P>
     * <p>
     *  返回与此<code> DragGestureEvent </code>关联的<code> Component </code>。
     * <P>
     * 
     * @return the Component
     */

    public Component getComponent() { return component; }

    /**
     * Returns the <code>DragSource</code>.
     * <P>
     * <p>
     *  返回<code> DragSource </code>。
     * <P>
     * 
     * @return the <code>DragSource</code>
     */

    public DragSource getDragSource() { return dragSource; }

    /**
     * Returns a <code>Point</code> in the coordinates
     * of the <code>Component</code> over which the drag originated.
     * <P>
     * <p>
     *  在拖动所源于的<code> Component </code>的坐标中返回<code> Point </code>。
     * <P>
     * 
     * @return the Point where the drag originated in Component coords.
     */

    public Point getDragOrigin() {
        return origin;
    }

    /**
     * Returns an <code>Iterator</code> for the events
     * comprising the gesture.
     * <P>
     * <p>
     *  为包含手势的事件返回<code>迭代器</code>。
     * <P>
     * 
     * @return an Iterator for the events comprising the gesture
     */
    @SuppressWarnings("unchecked")
    public Iterator<InputEvent> iterator() { return events.iterator(); }

    /**
     * Returns an <code>Object</code> array of the
     * events comprising the drag gesture.
     * <P>
     * <p>
     * 返回包含拖动手势的事件的<code> Object </code>数组。
     * <P>
     * 
     * @return an array of the events comprising the gesture
     */

    public Object[] toArray() { return events.toArray(); }

    /**
     * Returns an array of the events comprising the drag gesture.
     * <P>
     * <p>
     *  返回包含拖动手势的事件数组。
     * <P>
     * 
     * @param array the array of <code>EventObject</code> sub(types)
     * <P>
     * @return an array of the events comprising the gesture
     */
    @SuppressWarnings("unchecked")
    public Object[] toArray(Object[] array) { return events.toArray(array); }

    /**
     * Returns an <code>int</code> representing the
     * action selected by the user.
     * <P>
     * <p>
     *  返回表示用户选择的操作的<code> int </code>。
     * <P>
     * 
     * @return the action selected by the user
     */

    public int getDragAction() { return action; }

    /**
     * Returns the initial event that triggered the gesture.
     * <P>
     * <p>
     *  返回触发手势的初始事件。
     * <P>
     * 
     * @return the first "triggering" event in the sequence of the gesture
     */

    public InputEvent getTriggerEvent() {
        return getSourceAsDragGestureRecognizer().getTriggerEvent();
    }

    /**
     * Starts the drag operation given the <code>Cursor</code> for this drag
     * operation and the <code>Transferable</code> representing the source data
     * for this drag operation.
     * <br>
     * If a <code>null</code> <code>Cursor</code> is specified no exception will
     * be thrown and default drag cursors will be used instead.
     * <br>
     * If a <code>null</code> <code>Transferable</code> is specified
     * <code>NullPointerException</code> will be thrown.
     * <p>
     *  给定用于此拖动操作的<code> Cursor </code>和表示此拖动操作的源数据的<code> Transferable </code>开始拖动操作。
     * <br>
     *  如果指定<code> null </code> <code> Cursor </code>,则不会抛出异常,而是使用默认的拖动光标。
     * <br>
     *  如果指定<code> null </code> <code>可转移</code> <code>将抛出NullPointerException </code>。
     * 
     * 
     * @param dragCursor     The initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see
     *                       <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism
     *                       during drag and drop
     * @param transferable The <code>Transferable</code> representing the source
     *                     data for this drag operation.
     *
     * @throws InvalidDnDOperationException if the Drag and Drop
     *         system is unable to initiate a drag operation, or if the user
     *         attempts to start a drag while an existing drag operation is
     *         still executing.
     * @throws NullPointerException if the {@code Transferable} is {@code null}
     * @since 1.4
     */
    public void startDrag(Cursor dragCursor, Transferable transferable)
      throws InvalidDnDOperationException {
        dragSource.startDrag(this, dragCursor, transferable, null);
    }

    /**
     * Starts the drag given the initial <code>Cursor</code> to display,
     * the <code>Transferable</code> object,
     * and the <code>DragSourceListener</code> to use.
     * <P>
     * <p>
     *  开始拖动,给出显示的初始<code> Cursor </code>,<code> Transferable </code>对象和<code> DragSourceListener </code>
     * <P>
     * 
     * @param dragCursor     The initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see
     *                       <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism
     *                       during drag and drop
     * @param transferable The source's Transferable
     * @param dsl          The source's DragSourceListener
     * <P>
     * @throws InvalidDnDOperationException if
     * the Drag and Drop system is unable to
     * initiate a drag operation, or if the user
     * attempts to start a drag while an existing
     * drag operation is still executing.
     */

    public void startDrag(Cursor dragCursor, Transferable transferable, DragSourceListener dsl) throws InvalidDnDOperationException {
        dragSource.startDrag(this, dragCursor, transferable, dsl);
    }

    /**
     * Start the drag given the initial <code>Cursor</code> to display,
     * a drag <code>Image</code>, the offset of
     * the <code>Image</code>,
     * the <code>Transferable</code> object, and
     * the <code>DragSourceListener</code> to use.
     * <P>
     * <p>
     *  开始拖动给出的初始<code> Cursor </code>以显示,拖动<code> Image </code>,偏移量<code> Image </code>,<code> Transferable
     *  </code>对象,并使用<code> DragSourceListener </code>。
     * <P>
     * 
     * @param dragCursor     The initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see
     *                       <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism
     *                       during drag and drop
     * @param dragImage    The source's dragImage
     * @param imageOffset  The dragImage's offset
     * @param transferable The source's Transferable
     * @param dsl          The source's DragSourceListener
     * <P>
     * @throws InvalidDnDOperationException if
     * the Drag and Drop system is unable to
     * initiate a drag operation, or if the user
     * attempts to start a drag while an existing
     * drag operation is still executing.
     */

    public void startDrag(Cursor dragCursor, Image dragImage, Point imageOffset, Transferable transferable, DragSourceListener dsl) throws InvalidDnDOperationException {
        dragSource.startDrag(this,  dragCursor, dragImage, imageOffset, transferable, dsl);
    }

    /**
     * Serializes this <code>DragGestureEvent</code>. Performs default
     * serialization and then writes out this object's <code>List</code> of
     * gesture events if and only if the <code>List</code> can be serialized.
     * If not, <code>null</code> is written instead. In this case, a
     * <code>DragGestureEvent</code> created from the resulting deserialized
     * stream will contain an empty <code>List</code> of gesture events.
     *
     * <p>
     *  将此<code> DragGestureEvent </code>序列化。
     * 执行默认序列化,然后当且仅当<code> List </code>可以序列化时,写出该对象的<code> List </code>手势事件。如果不是,写入<code> null </code>。
     * 在这种情况下,从生成的反序列化流创建的<code> DragGestureEvent </code>将包含一个空的<code> List </code>手势事件。
     * 
     * 
     * @serialData The default serializable fields, in alphabetical order,
     *             followed by either a <code>List</code> instance, or
     *             <code>null</code>.
     * @since 1.4
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeObject(SerializationTester.test(events) ? events : null);
    }

    /**
     * Deserializes this <code>DragGestureEvent</code>. This method first
     * performs default deserialization for all non-<code>transient</code>
     * fields. An attempt is then made to deserialize this object's
     * <code>List</code> of gesture events as well. This is first attempted
     * by deserializing the field <code>events</code>, because, in releases
     * prior to 1.4, a non-<code>transient</code> field of this name stored the
     * <code>List</code> of gesture events. If this fails, the next object in
     * the stream is used instead. If the resulting <code>List</code> is
     * <code>null</code>, this object's <code>List</code> of gesture events
     * is set to an empty <code>List</code>.
     *
     * <p>
     * 反序列化此<code> DragGestureEvent </code>。该方法首先对所有非<code> transient </code>字段执行默认反序列化。
     * 然后尝试反序列化该对象的手势事件的<code> List </code>。
     * 这首先通过反序列化<code>事件</code>字段来尝试,因为在1.4之前的版本中,该名称的非<代码>瞬态</code>字段存储了<code> List </code>的手势事件。
     * 如果失败,则使用流中的下一个对象。
     * 如果结果<code> List </code>是<code> null </code>,则该对象的<code> List </code>手势事件被设置为空的<code> List </code>。
     * 
     * 
     * @since 1.4
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        ObjectInputStream.GetField f = s.readFields();

        DragSource newDragSource = (DragSource)f.get("dragSource", null);
        if (newDragSource == null) {
            throw new InvalidObjectException("null DragSource");
        }
        dragSource = newDragSource;

        Component newComponent = (Component)f.get("component", null);
        if (newComponent == null) {
            throw new InvalidObjectException("null component");
        }
        component = newComponent;

        Point newOrigin = (Point)f.get("origin", null);
        if (newOrigin == null) {
            throw new InvalidObjectException("null origin");
        }
        origin = newOrigin;

        int newAction = f.get("action", 0);
        if (newAction != DnDConstants.ACTION_COPY &&
                newAction != DnDConstants.ACTION_MOVE &&
                newAction != DnDConstants.ACTION_LINK) {
            throw new InvalidObjectException("bad action");
        }
        action = newAction;

        // Pre-1.4 support. 'events' was previously non-transient
        List newEvents;
        try {
            newEvents = (List)f.get("events", null);
        } catch (IllegalArgumentException e) {
            // 1.4-compatible byte stream. 'events' was written explicitly
            newEvents = (List)s.readObject();
        }

        // Implementation assumes 'events' is never null.
        if (newEvents != null && newEvents.isEmpty()) {
            // Constructor treats empty events list as invalid value
            // Throw exception if serialized list is empty
            throw new InvalidObjectException("empty list of events");
        } else if (newEvents == null) {
            newEvents = Collections.emptyList();
        }
        events = newEvents;
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */
    @SuppressWarnings("rawtypes")
    private transient List events;

    /**
     * The DragSource associated with this DragGestureEvent.
     *
     * <p>
     *  与此DragGestureEvent相关联的DragSource。
     * 
     * 
     * @serial
     */
    private DragSource dragSource;

    /**
     * The Component associated with this DragGestureEvent.
     *
     * <p>
     *  与此DragGestureEvent相关联的组件。
     * 
     * 
     * @serial
     */
    private Component  component;

    /**
     * The origin of the drag.
     *
     * <p>
     *  拖动的原点。
     * 
     * 
     * @serial
     */
    private Point      origin;

    /**
     * The user's preferred action.
     *
     * <p>
     *  用户的首选操作。
     * 
     * @serial
     */
    private int        action;
}
