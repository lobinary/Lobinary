/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.undo;

import javax.swing.event.*;
import java.util.*;

/**
 * A support class used for managing <code>UndoableEdit</code> listeners.
 *
 * <p>
 *  用于管理<code> UndoableEdit </code>侦听器的支持类。
 * 
 * 
 * @author Ray Ryan
 */
public class UndoableEditSupport {
    protected int updateLevel;
    protected CompoundEdit compoundEdit;
    protected Vector<UndoableEditListener> listeners;
    protected Object realSource;

    /**
     * Constructs an <code>UndoableEditSupport</code> object.
     * <p>
     *  构造一个<code> UndoableEditSupport </code>对象。
     * 
     */
    public UndoableEditSupport() {
        this(null);
    }

    /**
     * Constructs an <code>UndoableEditSupport</code> object.
     *
     * <p>
     *  构造一个<code> UndoableEditSupport </code>对象。
     * 
     * 
     * @param r  an <code>Object</code>
     */
    public UndoableEditSupport(Object r) {
        realSource = r == null ? this : r;
        updateLevel = 0;
        compoundEdit = null;
        listeners = new Vector<UndoableEditListener>();
    }

    /**
     * Registers an <code>UndoableEditListener</code>.
     * The listener is notified whenever an edit occurs which can be undone.
     *
     * <p>
     *  注册<code> UndoableEditListener </code>。每当发生可以撤消的编辑时,通知侦听器。
     * 
     * 
     * @param l  an <code>UndoableEditListener</code> object
     * @see #removeUndoableEditListener
     */
    public synchronized void addUndoableEditListener(UndoableEditListener l) {
        listeners.addElement(l);
    }

    /**
     * Removes an <code>UndoableEditListener</code>.
     *
     * <p>
     *  删除<code> UndoableEditListener </code>。
     * 
     * 
     * @param l  the <code>UndoableEditListener</code> object to be removed
     * @see #addUndoableEditListener
     */
    public synchronized void removeUndoableEditListener(UndoableEditListener l)
    {
        listeners.removeElement(l);
    }

    /**
     * Returns an array of all the <code>UndoableEditListener</code>s added
     * to this UndoableEditSupport with addUndoableEditListener().
     *
     * <p>
     *  返回通过addUndoableEditListener()添加到此UndoableEditSupport的所有<code> UndoableEditListener </code>数组。
     * 
     * 
     * @return all of the <code>UndoableEditListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public synchronized UndoableEditListener[] getUndoableEditListeners() {
        return listeners.toArray(new UndoableEditListener[0]);
    }

    /**
     * Called only from <code>postEdit</code> and <code>endUpdate</code>. Calls
     * <code>undoableEditHappened</code> in all listeners. No synchronization
     * is performed here, since the two calling methods are synchronized.
     * <p>
     *  仅从<code> postEdit </code>和<code> endUpdate </code>调用。在所有侦听器中调用<code> undoableEditHappened </code>。
     * 这里不执行同步,因为两个调用方法是同步的。
     * 
     */
    protected void _postEdit(UndoableEdit e) {
        UndoableEditEvent ev = new UndoableEditEvent(realSource, e);
        Enumeration cursor = ((Vector)listeners.clone()).elements();
        while (cursor.hasMoreElements()) {
            ((UndoableEditListener)cursor.nextElement()).
                undoableEditHappened(ev);
        }
    }

    /**
     * DEADLOCK WARNING: Calling this method may call
     * <code>undoableEditHappened</code> in all listeners.
     * It is unwise to call this method from one of its listeners.
     * <p>
     *  DEADLOCK警告：调用此方法可能会在所有侦听器中调用<code> undoableEditHappened </code>。从其中一个侦听器调用此方法是不明智的。
     * 
     */
    public synchronized void postEdit(UndoableEdit e) {
        if (updateLevel == 0) {
            _postEdit(e);
        } else {
            // PENDING(rjrjr) Throw an exception if this fails?
            compoundEdit.addEdit(e);
        }
    }

    /**
     * Returns the update level value.
     *
     * <p>
     *  返回更新级别值。
     * 
     * 
     * @return an integer representing the update level
     */
    public int getUpdateLevel() {
        return updateLevel;
    }

    /**
     *
     * <p>
     */
    public synchronized void beginUpdate() {
        if (updateLevel == 0) {
            compoundEdit = createCompoundEdit();
        }
        updateLevel++;
    }

    /**
     * Called only from <code>beginUpdate</code>.
     * Exposed here for subclasses' use.
     * <p>
     *  仅从<code> beginUpdate </code>调用。这里暴露子类的使用。
     * 
     */
    protected CompoundEdit createCompoundEdit() {
        return new CompoundEdit();
    }

    /**
     * DEADLOCK WARNING: Calling this method may call
     * <code>undoableEditHappened</code> in all listeners.
     * It is unwise to call this method from one of its listeners.
     * <p>
     *  DEADLOCK警告：调用此方法可能会在所有侦听器中调用<code> undoableEditHappened </code>。从其中一个侦听器调用此方法是不明智的。
     * 
     */
    public synchronized void endUpdate() {
        updateLevel--;
        if (updateLevel == 0) {
            compoundEdit.end();
            _postEdit(compoundEdit);
            compoundEdit = null;
        }
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * @return a <code>String</code> representation of this object
     */
    public String toString() {
        return super.toString() +
            " updateLevel: " + updateLevel +
            " listeners: " + listeners +
            " compoundEdit: " + compoundEdit;
    }
}
