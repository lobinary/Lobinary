/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.awt.dnd.peer.DragSourceContextPeer;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import java.util.TooManyListenersException;

/**
 * The <code>DragSourceContext</code> class is responsible for managing the
 * initiator side of the Drag and Drop protocol. In particular, it is responsible
 * for managing drag event notifications to the
 * {@linkplain DragSourceListener DragSourceListeners}
 * and {@linkplain DragSourceMotionListener DragSourceMotionListeners}, and providing the
 * {@link Transferable} representing the source data for the drag operation.
 * <p>
 * Note that the <code>DragSourceContext</code> itself
 * implements the <code>DragSourceListener</code> and
 * <code>DragSourceMotionListener</code> interfaces.
 * This is to allow the platform peer
 * (the {@link DragSourceContextPeer} instance)
 * created by the {@link DragSource} to notify
 * the <code>DragSourceContext</code> of
 * state changes in the ongoing operation. This allows the
 * <code>DragSourceContext</code> object to interpose
 * itself between the platform and the
 * listeners provided by the initiator of the drag operation.
 * <p>
 * <a name="defaultCursor"></a>
 * By default, {@code DragSourceContext} sets the cursor as appropriate
 * for the current state of the drag and drop operation. For example, if
 * the user has chosen {@linkplain DnDConstants#ACTION_MOVE the move action},
 * and the pointer is over a target that accepts
 * the move action, the default move cursor is shown. When
 * the pointer is over an area that does not accept the transfer,
 * the default "no drop" cursor is shown.
 * <p>
 * This default handling mechanism is disabled when a custom cursor is set
 * by the {@link #setCursor} method. When the default handling is disabled,
 * it becomes the responsibility
 * of the developer to keep the cursor up to date, by listening
 * to the {@code DragSource} events and calling the {@code setCursor()} method.
 * Alternatively, you can provide custom cursor behavior by providing
 * custom implementations of the {@code DragSource}
 * and the {@code DragSourceContext} classes.
 *
 * <p>
 *  <code> DragSourceContext </code>类负责管理拖放协议的启动器端。
 * 特别是,它负责管理对{@linkplain DragSourceListener DragSourceListeners}和{@linkplain DragSourceMotionListener DragSourceMotionListeners}
 * 的拖动事件通知,并提供表示拖动操作的源数据的{@link Transferable}。
 *  <code> DragSourceContext </code>类负责管理拖放协议的启动器端。
 * <p>
 *  注意,<code> DragSourceContext </code>本身实现<code> DragSourceListener </code>和<code> DragSourceMotionList
 * ener </code>接口。
 * 这是为了允许由{@link DragSource}创建的平台对等体({@link DragSourceContextPeer}实例)通知正在进行的操作中的状态变化的<code> DragSourceCo
 * ntext </code>。
 * 这允许<code> DragSourceContext </code>对象将自身插入平台和拖动操作的启动器提供的侦听器之间。
 * <p>
 * <a name="defaultCursor"> </a>默认情况下,{@code DragSourceContext}将光标设置为适用于拖放操作的当前状态。
 * 例如,如果用户选择了{@linkplain DnDConstants#ACTION_MOVE移动动作},并且指针位于接受移动动作的目标上,则显示默认移动光标。
 * 当指针位于不接受传输的区域上时,将显示默认的"无下拉"光标。
 * <p>
 *  当通过{@link #setCursor}方法设置自定义游标时,将禁用此默认处理机制。
 * 当禁用默认处理时,通过侦听{@code DragSource}事件并调用{@code setCursor()}方法,开发人员有责任使光标保持最新。
 * 或者,您可以通过提供{@code DragSource}和{@code DragSourceContext}类的自定义实现来提供自定义游标行为。
 * 
 * 
 * @see DragSourceListener
 * @see DragSourceMotionListener
 * @see DnDConstants
 * @since 1.2
 */

public class DragSourceContext
    implements DragSourceListener, DragSourceMotionListener, Serializable {

    private static final long serialVersionUID = -115407898692194719L;

    // used by updateCurrentCursor

    /**
     * An <code>int</code> used by updateCurrentCursor()
     * indicating that the <code>Cursor</code> should change
     * to the default (no drop) <code>Cursor</code>.
     * <p>
     *  updateCurrentCursor()使用的<code> int </code>指示<code> Cursor </code>应该更改为默认值(无下拉)<code> Cursor </code>。
     * 
     */
    protected static final int DEFAULT = 0;

    /**
     * An <code>int</code> used by updateCurrentCursor()
     * indicating that the <code>Cursor</code>
     * has entered a <code>DropTarget</code>.
     * <p>
     *  updateCurrentCursor()使用的<code> int </code>指示<code> Cursor </code>已输入<code> DropTarget </code>。
     * 
     */
    protected static final int ENTER   = 1;

    /**
     * An <code>int</code> used by updateCurrentCursor()
     * indicating that the <code>Cursor</code> is
     * over a <code>DropTarget</code>.
     * <p>
     *  updateCurrentCursor()使用的<code> int </code>表示<code> Cursor </code>位于<code> DropTarget </code>之上。
     * 
     */
    protected static final int OVER    = 2;

    /**
     * An <code>int</code> used by updateCurrentCursor()
     * indicating that the user operation has changed.
     * <p>
     *  updateCurrentCursor()使用的<code> int </code>,表示用户操作已更改。
     * 
     */

    protected static final int CHANGED = 3;

    /**
     * Called from <code>DragSource</code>, this constructor creates a new
     * <code>DragSourceContext</code> given the
     * <code>DragSourceContextPeer</code> for this Drag, the
     * <code>DragGestureEvent</code> that triggered the Drag, the initial
     * <code>Cursor</code> to use for the Drag, an (optional)
     * <code>Image</code> to display while the Drag is taking place, the offset
     * of the <code>Image</code> origin from the hotspot at the instant of the
     * triggering event, the <code>Transferable</code> subject data, and the
     * <code>DragSourceListener</code> to use during the Drag and Drop
     * operation.
     * <br>
     * If <code>DragSourceContextPeer</code> is <code>null</code>
     * <code>NullPointerException</code> is thrown.
     * <br>
     * If <code>DragGestureEvent</code> is <code>null</code>
     * <code>NullPointerException</code> is thrown.
     * <br>
     * If <code>Cursor</code> is <code>null</code> no exception is thrown and
     * the default drag cursor behavior is activated for this drag operation.
     * <br>
     * If <code>Image</code> is <code>null</code> no exception is thrown.
     * <br>
     * If <code>Image</code> is not <code>null</code> and the offset is
     * <code>null</code> <code>NullPointerException</code> is thrown.
     * <br>
     * If <code>Transferable</code> is <code>null</code>
     * <code>NullPointerException</code> is thrown.
     * <br>
     * If <code>DragSourceListener</code> is <code>null</code> no exception
     * is thrown.
     *
     * <p>
     * 从<code> DragSource </code>调用,此构造函数创建一个新的<code> DragSourceContext </code>给定此Drag的<code> DragSourceCont
     * extPeer </code>,<code> DragGestureEvent </code>拖动,用于拖动的初始<code> Cursor </code>,用于拖动的(可选)<code> Image 
     * </code>显示,<code> Image </code >源于触发事件时的热点,<code>可传递的</code>主题数据,以及在拖放操作期间使用的<code> DragSourceListener
     *  </code>。
     * <br>
     *  如果<code> DragSourceContextPeer </code>是<code> null </code> <code> NullPointerException </code>被抛出。
     * <br>
     *  如果<code> DragGestureEvent </code>是<code> null </code> <code> NullPointerException </code>被抛出。
     * <br>
     *  如果<code> Cursor </code>是<code> null </code>,则不抛出异常,并为此拖动操作激活默认的拖动光标行为。
     * <br>
     *  如果<code> Image </code>是<code> null </code>,则不会抛出异常。
     * <br>
     *  如果<code> Image </code>不是<code> null </code>,并且偏移量是<code> null </code> <code> NullPointerException </code>
     * 。
     * <br>
     *  如果<code> Transferable </code>是<code> null </code> <code> NullPointerException </code>被抛出。
     * <br>
     *  如果<code> DragSourceListener </code>是<code> null </code>,则不会抛出异常。
     * 
     * 
     * @param dscp       the <code>DragSourceContextPeer</code> for this drag
     * @param trigger    the triggering event
     * @param dragCursor     the initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">class level documentation</a>
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param dragImage  the <code>Image</code> to drag (or <code>null</code>)
     * @param offset     the offset of the image origin from the hotspot at the
     *                   instant of the triggering event
     * @param t          the <code>Transferable</code>
     * @param dsl        the <code>DragSourceListener</code>
     *
     * @throws IllegalArgumentException if the <code>Component</code> associated
     *         with the trigger event is <code>null</code>.
     * @throws IllegalArgumentException if the <code>DragSource</code> for the
     *         trigger event is <code>null</code>.
     * @throws IllegalArgumentException if the drag action for the
     *         trigger event is <code>DnDConstants.ACTION_NONE</code>.
     * @throws IllegalArgumentException if the source actions for the
     *         <code>DragGestureRecognizer</code> associated with the trigger
     *         event are equal to <code>DnDConstants.ACTION_NONE</code>.
     * @throws NullPointerException if dscp, trigger, or t are null, or
     *         if dragImage is non-null and offset is null
     */
    public DragSourceContext(DragSourceContextPeer dscp,
                             DragGestureEvent trigger, Cursor dragCursor,
                             Image dragImage, Point offset, Transferable t,
                             DragSourceListener dsl) {
        if (dscp == null) {
            throw new NullPointerException("DragSourceContextPeer");
        }

        if (trigger == null) {
            throw new NullPointerException("Trigger");
        }

        if (trigger.getDragSource() == null) {
            throw new IllegalArgumentException("DragSource");
        }

        if (trigger.getComponent() == null) {
            throw new IllegalArgumentException("Component");
        }

        if (trigger.getSourceAsDragGestureRecognizer().getSourceActions() ==
                 DnDConstants.ACTION_NONE) {
            throw new IllegalArgumentException("source actions");
        }

        if (trigger.getDragAction() == DnDConstants.ACTION_NONE) {
            throw new IllegalArgumentException("no drag action");
        }

        if (t == null) {
            throw new NullPointerException("Transferable");
        }

        if (dragImage != null && offset == null) {
            throw new NullPointerException("offset");
        }

        peer         = dscp;
        this.trigger = trigger;
        cursor       = dragCursor;
        transferable = t;
        listener     = dsl;
        sourceActions =
            trigger.getSourceAsDragGestureRecognizer().getSourceActions();

        useCustomCursor = (dragCursor != null);

        updateCurrentCursor(trigger.getDragAction(), getSourceActions(), DEFAULT);
    }

    /**
     * Returns the <code>DragSource</code>
     * that instantiated this <code>DragSourceContext</code>.
     *
     * <p>
     *  返回实例化此<code> DragSourceContext </code>的<code> DragSource </code>。
     * 
     * 
     * @return the <code>DragSource</code> that
     *   instantiated this <code>DragSourceContext</code>
     */

    public DragSource   getDragSource() { return trigger.getDragSource(); }

    /**
     * Returns the <code>Component</code> associated with this
     * <code>DragSourceContext</code>.
     *
     * <p>
     *  返回与此<code> DragSourceContext </code>关联的<code> Component </code>。
     * 
     * 
     * @return the <code>Component</code> that started the drag
     */

    public Component    getComponent() { return trigger.getComponent(); }

    /**
     * Returns the <code>DragGestureEvent</code>
     * that initially triggered the drag.
     *
     * <p>
     * 返回最初触发拖动的<code> DragGestureEvent </code>。
     * 
     * 
     * @return the Event that triggered the drag
     */

    public DragGestureEvent getTrigger() { return trigger; }

    /**
     * Returns a bitwise mask of <code>DnDConstants</code> that
     * represent the set of drop actions supported by the drag source for the
     * drag operation associated with this <code>DragSourceContext</code>.
     *
     * <p>
     *  返回<code> DnDConstants </code>的位掩码,表示与此<code> DragSourceContext </code>关联的拖动操作的拖动源支持的拖放操作的集合。
     * 
     * 
     * @return the drop actions supported by the drag source
     */
    public int  getSourceActions() {
        return sourceActions;
    }

    /**
     * Sets the cursor for this drag operation to the specified
     * <code>Cursor</code>.  If the specified <code>Cursor</code>
     * is <code>null</code>, the default drag cursor behavior is
     * activated for this drag operation, otherwise it is deactivated.
     *
     * <p>
     *  将此拖动操作的光标设置为指定的<code> Cursor </code>。
     * 如果指定的<code> Cursor </code>为<code> null </code>,则此拖动操作的默认拖动光标行为被激活,否则被禁用。
     * 
     * 
     * @param c     the initial {@code Cursor} for this drag operation,
     *                       or {@code null} for the default cursor handling;
     *                       see {@linkplain Cursor class
     *                       level documentation} for more details
     *                       on the cursor handling during drag and drop
     *
     */

    public synchronized void setCursor(Cursor c) {
        useCustomCursor = (c != null);
        setCursorImpl(c);
    }

    /**
     * Returns the current drag <code>Cursor</code>.
     * <P>
     * <p>
     *  返回当前拖动<code> Cursor </code>。
     * <P>
     * 
     * @return the current drag <code>Cursor</code>
     */

    public Cursor getCursor() { return cursor; }

    /**
     * Add a <code>DragSourceListener</code> to this
     * <code>DragSourceContext</code> if one has not already been added.
     * If a <code>DragSourceListener</code> already exists,
     * this method throws a <code>TooManyListenersException</code>.
     * <P>
     * <p>
     *  将<code> DragSourceListener </code>添加到此<code> DragSourceContext </code>(如果尚未添加)。
     * 如果<code> DragSourceListener </code>已经存在,此方法会抛出一个<code> TooManyListenersException </code>。
     * <P>
     * 
     * @param dsl the <code>DragSourceListener</code> to add.
     * Note that while <code>null</code> is not prohibited,
     * it is not acceptable as a parameter.
     * <P>
     * @throws TooManyListenersException if
     * a <code>DragSourceListener</code> has already been added
     */

    public synchronized void addDragSourceListener(DragSourceListener dsl) throws TooManyListenersException {
        if (dsl == null) return;

        if (equals(dsl)) throw new IllegalArgumentException("DragSourceContext may not be its own listener");

        if (listener != null)
            throw new TooManyListenersException();
        else
            listener = dsl;
    }

    /**
     * Removes the specified <code>DragSourceListener</code>
     * from  this <code>DragSourceContext</code>.
     *
     * <p>
     *  从此<code> DragSourceContext </code>中删除指定的<code> DragSourceListener </code>。
     * 
     * 
     * @param dsl the <code>DragSourceListener</code> to remove;
     *     note that while <code>null</code> is not prohibited,
     *     it is not acceptable as a parameter
     */

    public synchronized void removeDragSourceListener(DragSourceListener dsl) {
        if (listener != null && listener.equals(dsl)) {
            listener = null;
        } else
            throw new IllegalArgumentException();
    }

    /**
     * Notifies the peer that the <code>Transferable</code>'s
     * <code>DataFlavor</code>s have changed.
     * <p>
     *  通知对等体<code>可传输</code>的<code> DataFlavor </code>已更改。
     * 
     */

    public void transferablesFlavorsChanged() {
        if (peer != null) peer.transferablesFlavorsChanged();
    }

    /**
     * Calls <code>dragEnter</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSourceContext</code> and with the associated
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  在<code> DragSourceContext </code>注册的<code> DragSourceListener </code>以及相关的<code> DragSource </code>中
     * 调用<code> dragEnter </code>代码> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragEnter(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragEnter(dsde);
        }
        getDragSource().processDragEnter(dsde);

        updateCurrentCursor(getSourceActions(), dsde.getTargetActions(), ENTER);
    }

    /**
     * Calls <code>dragOver</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSourceContext</code> and with the associated
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  在与此<code> DragSourceContext </code>和相关联的<code> DragSource </code>注册的<code> DragSourceListener </code>
     * 上调用<code> dragOver </code>代码> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dragOver(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragOver(dsde);
        }
        getDragSource().processDragOver(dsde);

        updateCurrentCursor(getSourceActions(), dsde.getTargetActions(), OVER);
    }

    /**
     * Calls <code>dragExit</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSourceContext</code> and with the associated
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceEvent</code>.
     *
     * <p>
     * 在向<code> DragSourceContext </code>注册的<code> DragSourceListener </code>和与之关联的<code> DragSource </code>
     * 中调用<code> dragExit </code>代码> DragSourceEvent </code>。
     * 
     * 
     * @param dse the <code>DragSourceEvent</code>
     */
    public void dragExit(DragSourceEvent dse) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragExit(dse);
        }
        getDragSource().processDragExit(dse);

        updateCurrentCursor(DnDConstants.ACTION_NONE, DnDConstants.ACTION_NONE, DEFAULT);
    }

    /**
     * Calls <code>dropActionChanged</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSourceContext</code> and with the associated
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  在与此<code> DragSourceContext </code>和相关联的<code> DragSource </code>注册的<code> DragSourceListener </code>
     * 上调用<code> dropActionChanged </code>代码> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    public void dropActionChanged(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dropActionChanged(dsde);
        }
        getDragSource().processDropActionChanged(dsde);

        updateCurrentCursor(getSourceActions(), dsde.getTargetActions(), CHANGED);
    }

    /**
     * Calls <code>dragDropEnd</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSourceContext</code> and with the associated
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDropEvent</code>.
     *
     * <p>
     *  在向<code> DragSourceContext </code>注册的<code> DragSourceListener </code>以及相关的<code> DragSource </code>
     * 中调用<code> dragDropEnd </code>代码> DragSourceDropEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDropEvent</code>
     */
    public void dragDropEnd(DragSourceDropEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragDropEnd(dsde);
        }
        getDragSource().processDragDropEnd(dsde);
    }

    /**
     * Calls <code>dragMouseMoved</code> on the
     * <code>DragSourceMotionListener</code>s registered with the
     * <code>DragSource</code> associated with this
     * <code>DragSourceContext</code>, and them passes the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  在与<code> DragSourceContext </code>关联的<code> DragSource </code>注册的<code> DragSourceMotionListener </code>
     * 上调用<code> dragMouseMoved </code>,并传递指定的<code > DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     * @since 1.4
     */
    public void dragMouseMoved(DragSourceDragEvent dsde) {
        getDragSource().processDragMouseMoved(dsde);
    }

    /**
     * Returns the <code>Transferable</code> associated with
     * this <code>DragSourceContext</code>.
     *
     * <p>
     *  返回与此<code> DragSourceContext </code>关联的<code> Transferable </code>。
     * 
     * 
     * @return the <code>Transferable</code>
     */
    public Transferable getTransferable() { return transferable; }

    /**
     * If the default drag cursor behavior is active, this method
     * sets the default drag cursor for the specified actions
     * supported by the drag source, the drop target action,
     * and status, otherwise this method does nothing.
     *
     * <p>
     *  如果默认拖动光标行为是活动的,则此方法为拖动源支持的指定操作,放置目标操作和状态设置默认拖动光标,否则此方法不执行任何操作。
     * 
     * 
     * @param sourceAct the actions supported by the drag source
     * @param targetAct the drop target action
     * @param status one of the fields <code>DEFAULT</code>,
     *               <code>ENTER</code>, <code>OVER</code>,
     *               <code>CHANGED</code>
     */

    protected synchronized void updateCurrentCursor(int sourceAct, int targetAct, int status) {

        // if the cursor has been previously set then don't do any defaults
        // processing.

        if (useCustomCursor) {
            return;
        }

        // do defaults processing

        Cursor c = null;

        switch (status) {
            default:
                targetAct = DnDConstants.ACTION_NONE;
            case ENTER:
            case OVER:
            case CHANGED:
                int    ra = sourceAct & targetAct;

                if (ra == DnDConstants.ACTION_NONE) { // no drop possible
                    if ((sourceAct & DnDConstants.ACTION_LINK) == DnDConstants.ACTION_LINK)
                        c = DragSource.DefaultLinkNoDrop;
                    else if ((sourceAct & DnDConstants.ACTION_MOVE) == DnDConstants.ACTION_MOVE)
                        c = DragSource.DefaultMoveNoDrop;
                    else
                        c = DragSource.DefaultCopyNoDrop;
                } else { // drop possible
                    if ((ra & DnDConstants.ACTION_LINK) == DnDConstants.ACTION_LINK)
                        c = DragSource.DefaultLinkDrop;
                    else if ((ra & DnDConstants.ACTION_MOVE) == DnDConstants.ACTION_MOVE)
                        c = DragSource.DefaultMoveDrop;
                    else
                        c = DragSource.DefaultCopyDrop;
                }
        }

        setCursorImpl(c);
    }

    private void setCursorImpl(Cursor c) {
        if (cursor == null || !cursor.equals(c)) {
            cursor = c;
            if (peer != null) peer.setCursor(cursor);
        }
    }

    /**
     * Serializes this <code>DragSourceContext</code>. This method first
     * performs default serialization. Next, this object's
     * <code>Transferable</code> is written out if and only if it can be
     * serialized. If not, <code>null</code> is written instead. In this case,
     * a <code>DragSourceContext</code> created from the resulting deserialized
     * stream will contain a dummy <code>Transferable</code> which supports no
     * <code>DataFlavor</code>s. Finally, this object's
     * <code>DragSourceListener</code> is written out if and only if it can be
     * serialized. If not, <code>null</code> is written instead.
     *
     * <p>
     * 序列化此<code> DragSourceContext </code>。此方法首先执行默认序列化。
     * 接下来,当且仅当它可以被序列化时,该对象的<code> Transferable </code>被写出。如果不是,写入<code> null </code>。
     * 在这种情况下,从所得到的反序列化流创建的<code> DragSourceContext </code>将包含不支持<code> DataFlavor </code>的虚拟<code> Transfer
     * able </code>。
     * 接下来,当且仅当它可以被序列化时,该对象的<code> Transferable </code>被写出。如果不是,写入<code> null </code>。
     * 最后,这个对象的<code> DragSourceListener </code>被写出,当且仅当它可以被序列化。如果不是,写入<code> null </code>。
     * 
     * 
     * @serialData The default serializable fields, in alphabetical order,
     *             followed by either a <code>Transferable</code> instance, or
     *             <code>null</code>, followed by either a
     *             <code>DragSourceListener</code> instance, or
     *             <code>null</code>.
     * @since 1.4
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeObject(SerializationTester.test(transferable)
                      ? transferable : null);
        s.writeObject(SerializationTester.test(listener)
                      ? listener : null);
    }

    /**
     * Deserializes this <code>DragSourceContext</code>. This method first
     * performs default deserialization for all non-<code>transient</code>
     * fields. This object's <code>Transferable</code> and
     * <code>DragSourceListener</code> are then deserialized as well by using
     * the next two objects in the stream. If the resulting
     * <code>Transferable</code> is <code>null</code>, this object's
     * <code>Transferable</code> is set to a dummy <code>Transferable</code>
     * which supports no <code>DataFlavor</code>s.
     *
     * <p>
     *  反序列化此<code> DragSourceContext </code>。该方法首先对所有非<code> transient </code>字段执行默认反序列化。
     * 然后,通过使用流中的下两个对象,该对象的<code> Transferable </code>和<code> DragSourceListener </code>也会被反序列化。
     * 如果结果<code> Transferable </code>是<code> null </code>,则该对象的<code> Transferable </code>被设置为一个虚拟<code> Tr
     * ansferable </code> DataFlavor </code>。
     * 然后,通过使用流中的下两个对象,该对象的<code> Transferable </code>和<code> DragSourceListener </code>也会被反序列化。
     * 
     * 
     * @since 1.4
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        ObjectInputStream.GetField f = s.readFields();

        DragGestureEvent newTrigger = (DragGestureEvent)f.get("trigger", null);
        if (newTrigger == null) {
            throw new InvalidObjectException("Null trigger");
        }
        if (newTrigger.getDragSource() == null) {
            throw new InvalidObjectException("Null DragSource");
        }
        if (newTrigger.getComponent() == null) {
            throw new InvalidObjectException("Null trigger component");
        }

        int newSourceActions = f.get("sourceActions", 0)
                & (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);
        if (newSourceActions == DnDConstants.ACTION_NONE) {
            throw new InvalidObjectException("Invalid source actions");
        }
        int triggerActions = newTrigger.getDragAction();
        if (triggerActions != DnDConstants.ACTION_COPY &&
                triggerActions != DnDConstants.ACTION_MOVE &&
                triggerActions != DnDConstants.ACTION_LINK) {
            throw new InvalidObjectException("No drag action");
        }
        trigger = newTrigger;

        cursor = (Cursor)f.get("cursor", null);
        useCustomCursor = f.get("useCustomCursor", false);
        sourceActions = newSourceActions;

        transferable = (Transferable)s.readObject();
        listener = (DragSourceListener)s.readObject();

        // Implementation assumes 'transferable' is never null.
        if (transferable == null) {
            if (emptyTransferable == null) {
                emptyTransferable = new Transferable() {
                        public DataFlavor[] getTransferDataFlavors() {
                            return new DataFlavor[0];
                        }
                        public boolean isDataFlavorSupported(DataFlavor flavor)
                        {
                            return false;
                        }
                        public Object getTransferData(DataFlavor flavor)
                            throws UnsupportedFlavorException
                        {
                            throw new UnsupportedFlavorException(flavor);
                        }
                    };
            }
            transferable = emptyTransferable;
        }
    }

    private static Transferable emptyTransferable;

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    private transient DragSourceContextPeer peer;

    /**
     * The event which triggered the start of the drag.
     *
     * <p>
     *  触发拖动开始的事件。
     * 
     * 
     * @serial
     */
    private DragGestureEvent    trigger;

    /**
     * The current drag cursor.
     *
     * <p>
     *  当前拖动游标。
     * 
     * 
     * @serial
     */
    private Cursor              cursor;

    private transient Transferable      transferable;

    private transient DragSourceListener    listener;

    /**
     * <code>true</code> if the custom drag cursor is used instead of the
     * default one.
     *
     * <p>
     *  <code> true </code>如果使用自定义拖动光标而不是默认的光标。
     * 
     * 
     * @serial
     */
    private boolean useCustomCursor;

    /**
     * A bitwise mask of <code>DnDConstants</code> that represents the set of
     * drop actions supported by the drag source for the drag operation associated
     * with this <code>DragSourceContext.</code>
     *
     * <p>
     *  <code> DnDConstants </code>的位掩码,表示与此<code> DragSourceContext相关联的拖动操作的拖动源支持的拖放操作的集合。</code>
     * 
     * @serial
     */
    private int sourceActions;
}
