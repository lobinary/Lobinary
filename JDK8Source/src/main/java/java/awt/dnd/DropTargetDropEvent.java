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

import java.awt.Point;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import java.util.List;

/**
 * The <code>DropTargetDropEvent</code> is delivered
 * via the <code>DropTargetListener</code> drop() method.
 * <p>
 * The <code>DropTargetDropEvent</code> reports the <i>source drop actions</i>
 * and the <i>user drop action</i> that reflect the current state of the
 * drag-and-drop operation.
 * <p>
 * <i>Source drop actions</i> is a bitwise mask of <code>DnDConstants</code>
 * that represents the set of drop actions supported by the drag source for
 * this drag-and-drop operation.
 * <p>
 * <i>User drop action</i> depends on the drop actions supported by the drag
 * source and the drop action selected by the user. The user can select a drop
 * action by pressing modifier keys during the drag operation:
 * <pre>
 *   Ctrl + Shift -&gt; ACTION_LINK
 *   Ctrl         -&gt; ACTION_COPY
 *   Shift        -&gt; ACTION_MOVE
 * </pre>
 * If the user selects a drop action, the <i>user drop action</i> is one of
 * <code>DnDConstants</code> that represents the selected drop action if this
 * drop action is supported by the drag source or
 * <code>DnDConstants.ACTION_NONE</code> if this drop action is not supported
 * by the drag source.
 * <p>
 * If the user doesn't select a drop action, the set of
 * <code>DnDConstants</code> that represents the set of drop actions supported
 * by the drag source is searched for <code>DnDConstants.ACTION_MOVE</code>,
 * then for <code>DnDConstants.ACTION_COPY</code>, then for
 * <code>DnDConstants.ACTION_LINK</code> and the <i>user drop action</i> is the
 * first constant found. If no constant is found the <i>user drop action</i>
 * is <code>DnDConstants.ACTION_NONE</code>.
 *
 * <p>
 *  <code> DropTargetDropEvent </code>通过<code> DropTargetListener </code> drop()方法传递。
 * <p>
 *  <code> DropTargetDropEvent </code>报告反映拖放操作的当前状态的<i>源拖放操作</i>和<i>用户拖放操作</i>。
 * <p>
 *  <i>源拖放操作</i>是<code> DnDConstants </code>的位掩码,表示拖放操作支持的拖放操作的集合。
 * <p>
 *  <i>用户放置操作</i>取决于拖动源支持的放下操作和用户选择的放下操作。用户可以在拖动操作期间通过按修改键来选择拖放动作：
 * <pre>
 *  Ctrl + Shift  - &gt; ACTION_LINK Ctrl  - &gt; ACTION_COPY Shift  - &gt; ACTION_MOVE
 * </pre>
 *  如果用户选择删除操作,则<i>用户删除操作</i>是代表所选删除操作的<code> DnDConstants </code>之一,如果此拖放操作由拖动源或<code> DnDConstants.ACT
 * ION_NONE </code>如果拖放源不支持此拖放操作。
 * <p>
 * 如果用户未选择删除操作,则会搜索代表拖动源支持的一组拖放操作的<code> DnDConstants </code>集合,然后搜索<code> DnDConstants.ACTION_MOVE </code>
 *  <code> DnDConstants.ACTION_COPY </code>,然后是<code> DnDConstants.ACTION_LINK </code>,而<i>用户删除操作</i>是找到
 * 的第一个常数。
 * 如果未找到常数,则<i>用户删除操作</i>是<code> DnDConstants.ACTION_NONE </code>。
 * 
 * 
 * @since 1.2
 */

public class DropTargetDropEvent extends DropTargetEvent {

    private static final long serialVersionUID = -1721911170440459322L;

    /**
     * Construct a <code>DropTargetDropEvent</code> given
     * the <code>DropTargetContext</code> for this operation,
     * the location of the drag <code>Cursor</code>'s
     * hotspot in the <code>Component</code>'s coordinates,
     * the currently
     * selected user drop action, and the current set of
     * actions supported by the source.
     * By default, this constructor
     * assumes that the target is not in the same virtual machine as
     * the source; that is, {@link #isLocalTransfer()} will
     * return <code>false</code>.
     * <P>
     * <p>
     *  为此操作提供<code> DropTargetDropEvent </code>,构造<code> DropTargetDropEvent </code>,将<code> Cursor </code>
     * 的热点拖到<code> Component </code>的坐标,当前选择的用户放置动作和源支持的当前动作集。
     * 默认情况下,此构造函数假定目标不在与源相同的虚拟机中;也就是说,{@link #isLocalTransfer()}将返回<code> false </code>。
     * <P>
     * 
     * @param dtc        The <code>DropTargetContext</code> for this operation
     * @param cursorLocn The location of the "Drag" Cursor's
     * hotspot in <code>Component</code> coordinates
     * @param dropAction the user drop action.
     * @param srcActions the source drop actions.
     *
     * @throws NullPointerException
     * if cursorLocn is <code>null</code>
     * @throws IllegalArgumentException
     *         if dropAction is not one of  <code>DnDConstants</code>.
     * @throws IllegalArgumentException
     *         if srcActions is not a bitwise mask of <code>DnDConstants</code>.
     * @throws IllegalArgumentException if dtc is <code>null</code>.
     */

    public DropTargetDropEvent(DropTargetContext dtc, Point cursorLocn, int dropAction, int srcActions)  {
        super(dtc);

        if (cursorLocn == null) throw new NullPointerException("cursorLocn");

        if (dropAction != DnDConstants.ACTION_NONE &&
            dropAction != DnDConstants.ACTION_COPY &&
            dropAction != DnDConstants.ACTION_MOVE &&
            dropAction != DnDConstants.ACTION_LINK
        ) throw new IllegalArgumentException("dropAction = " + dropAction);

        if ((srcActions & ~(DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK)) != 0) throw new IllegalArgumentException("srcActions");

        location        = cursorLocn;
        actions         = srcActions;
        this.dropAction = dropAction;
    }

    /**
     * Construct a <code>DropTargetEvent</code> given the
     * <code>DropTargetContext</code> for this operation,
     * the location of the drag <code>Cursor</code>'s hotspot
     * in the <code>Component</code>'s
     * coordinates, the currently selected user drop action,
     * the current set of actions supported by the source,
     * and a <code>boolean</code> indicating if the source is in the same JVM
     * as the target.
     * <P>
     * <p>
     *  为此操作提供<code> DropTargetContext </code>构造<code> DropTargetEvent </code>,将<code> Cursor </code>的热点拖动到<code>
     *  Component </code>当前选择的用户放置动作,源支持的当前动作集,以及指示源是否与目标位于相同JVM中的<code> boolean </code>。
     * <P>
     * 
     * @param dtc        The DropTargetContext for this operation
     * @param cursorLocn The location of the "Drag" Cursor's
     * hotspot in Component's coordinates
     * @param dropAction the user drop action.
     * @param srcActions the source drop actions.
     * @param isLocal  True if the source is in the same JVM as the target
     *
     * @throws NullPointerException
     *         if cursorLocn is  <code>null</code>
     * @throws IllegalArgumentException
     *         if dropAction is not one of <code>DnDConstants</code>.
     * @throws IllegalArgumentException if srcActions is not a bitwise mask of <code>DnDConstants</code>.
     * @throws IllegalArgumentException  if dtc is <code>null</code>.
     */

    public DropTargetDropEvent(DropTargetContext dtc, Point cursorLocn, int dropAction, int srcActions, boolean isLocal)  {
        this(dtc, cursorLocn, dropAction, srcActions);

        isLocalTx = isLocal;
    }

    /**
     * This method returns a <code>Point</code>
     * indicating the <code>Cursor</code>'s current
     * location in the <code>Component</code>'s coordinates.
     * <P>
     * <p>
     *  此方法在<code> Component </code>的坐标中返回<code> Point </code>,指示<code> Cursor </code>的当前位置。
     * <P>
     * 
     * @return the current <code>Cursor</code> location in Component's coords.
     */

    public Point getLocation() {
        return location;
    }


    /**
     * This method returns the current DataFlavors.
     * <P>
     * <p>
     *  此方法返回当前DataFlavors。
     * <P>
     * 
     * @return current DataFlavors
     */

    public DataFlavor[] getCurrentDataFlavors() {
        return getDropTargetContext().getCurrentDataFlavors();
    }

    /**
     * This method returns the currently available
     * <code>DataFlavor</code>s as a <code>java.util.List</code>.
     * <P>
     * <p>
     * 此方法将当前可用的<code> DataFlavor </code>作为<code> java.util.List </code>返回。
     * <P>
     * 
     * @return the currently available DataFlavors as a java.util.List
     */

    public List<DataFlavor> getCurrentDataFlavorsAsList() {
        return getDropTargetContext().getCurrentDataFlavorsAsList();
    }

    /**
     * This method returns a <code>boolean</code> indicating if the
     * specified <code>DataFlavor</code> is available
     * from the source.
     * <P>
     * <p>
     *  此方法返回<code> boolean </code>,指示是否从源中提供了指定的<code> DataFlavor </code>。
     * <P>
     * 
     * @param df the <code>DataFlavor</code> to test
     * <P>
     * @return if the DataFlavor specified is available from the source
     */

    public boolean isDataFlavorSupported(DataFlavor df) {
        return getDropTargetContext().isDataFlavorSupported(df);
    }

    /**
     * This method returns the source drop actions.
     *
     * <p>
     *  此方法返回源拖放操作。
     * 
     * 
     * @return the source drop actions.
     */
    public int getSourceActions() { return actions; }

    /**
     * This method returns the user drop action.
     *
     * <p>
     *  此方法返回用户放置操作。
     * 
     * 
     * @return the user drop actions.
     */
    public int getDropAction() { return dropAction; }

    /**
     * This method returns the <code>Transferable</code> object
     * associated with the drop.
     * <P>
     * <p>
     *  此方法返回与drop相关联的<code> Transferable </code>对象。
     * <P>
     * 
     * @return the <code>Transferable</code> associated with the drop
     */

    public Transferable getTransferable() {
        return getDropTargetContext().getTransferable();
    }

    /**
     * accept the drop, using the specified action.
     * <P>
     * <p>
     *  接受删除,使用指定的操作。
     * <P>
     * 
     * @param dropAction the specified action
     */

    public void acceptDrop(int dropAction) {
        getDropTargetContext().acceptDrop(dropAction);
    }

    /**
     * reject the Drop.
     * <p>
     *  拒绝掉落。
     * 
     */

    public void rejectDrop() {
        getDropTargetContext().rejectDrop();
    }

    /**
     * This method notifies the <code>DragSource</code>
     * that the drop transfer(s) are completed.
     * <P>
     * <p>
     *  此方法通知<code> DragSource </code>完成下拉传输。
     * <P>
     * 
     * @param success a <code>boolean</code> indicating that the drop transfer(s) are completed.
     */

    public void dropComplete(boolean success) {
        getDropTargetContext().dropComplete(success);
    }

    /**
     * This method returns an <code>int</code> indicating if
     * the source is in the same JVM as the target.
     * <P>
     * <p>
     *  此方法返回一个<code> int </code>,指示源是否与目标位于同一个JVM中。
     * <P>
     * 
     * @return if the Source is in the same JVM
     */

    public boolean isLocalTransfer() {
        return isLocalTx;
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    static final private Point  zero     = new Point(0,0);

    /**
     * The location of the drag cursor's hotspot in Component coordinates.
     *
     * <p>
     *  在组件坐标中拖动光标的热点的位置。
     * 
     * 
     * @serial
     */
    private Point               location   = zero;

    /**
     * The source drop actions.
     *
     * <p>
     *  源拖放操作。
     * 
     * 
     * @serial
     */
    private int                 actions    = DnDConstants.ACTION_NONE;

    /**
     * The user drop action.
     *
     * <p>
     *  用户删除操作。
     * 
     * 
     * @serial
     */
    private int                 dropAction = DnDConstants.ACTION_NONE;

    /**
     * <code>true</code> if the source is in the same JVM as the target.
     *
     * <p>
     *  <code> true </code>如果源与目标位于同一个JVM中。
     * 
     * @serial
     */
    private boolean             isLocalTx = false;
}
