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

package javax.swing.undo;

import java.io.Serializable;
import javax.swing.UIManager;

/**
 * An abstract implementation of <code>UndoableEdit</code>,
 * implementing simple responses to all boolean methods in
 * that interface.
 *
 * <p>
 *  <code> UndoableEdit </code>的抽象实现,实现对该接口中所有布尔方法的简单响应。
 * 
 * 
 * @author Ray Ryan
 */
public class AbstractUndoableEdit implements UndoableEdit, Serializable {

    /**
     * String returned by <code>getUndoPresentationName</code>;
     * as of Java 2 platform v1.3.1 this field is no longer used. This value
     * is now localized and comes from the defaults table with key
     * <code>AbstractUndoableEdit.undoText</code>.
     *
     * <p>
     *  <code> getUndoPresentationName </code>返回的字符串;从Java 2平台v1.3.1开始,此字段不再使用。
     * 此值现在已本地化,并来自带有<code> AbstractUndoableEdit.undoText </code>键的defaults表。
     * 
     * 
     * @see javax.swing.UIDefaults
     */
    protected static final String UndoName = "Undo";

    /**
     * String returned by <code>getRedoPresentationName</code>;
     * as of Java 2 platform v1.3.1 this field is no longer used. This value
     * is now localized and comes from the defaults table with key
     * <code>AbstractUndoableEdit.redoText</code>.
     *
     * <p>
     *  <code> getRedoPresentationName </code>返回的字符串;从Java 2平台v1.3.1开始,此字段不再使用。
     * 此值现在已本地化,并来自默认表,键为<code> AbstractUndoableEdit.redoText </code>。
     * 
     * 
     * @see javax.swing.UIDefaults
     */
    protected static final String RedoName = "Redo";

    /**
     * Defaults to true; becomes false if this edit is undone, true
     * again if it is redone.
     * <p>
     *  默认为true;如果此编辑被撤消,则变为假;如果重做,则为真。
     * 
     */
    boolean hasBeenDone;

    /**
     * True if this edit has not received <code>die</code>; defaults
     * to <code>true</code>.
     * <p>
     *  如果此修改未收到<code> die </code>,则为true;默认为<code> true </code>。
     * 
     */
    boolean alive;

    /**
     * Creates an <code>AbstractUndoableEdit</code> which defaults
     * <code>hasBeenDone</code> and <code>alive</code> to <code>true</code>.
     * <p>
     *  创建一个默认<code> hasBeenDone </code>和<code> alive </code>为<code> true </code>的<code> AbstractUndoableEdi
     * t </code>。
     * 
     */
    public AbstractUndoableEdit() {
        super();

        hasBeenDone = true;
        alive = true;
    }

    /**
     * Sets <code>alive</code> to false. Note that this
     * is a one way operation; dead edits cannot be resurrected.
     * Sending <code>undo</code> or <code>redo</code> to
     * a dead edit results in an exception being thrown.
     *
     * <p>Typically an edit is killed when it is consolidated by
     * another edit's <code>addEdit</code> or <code>replaceEdit</code>
     * method, or when it is dequeued from an <code>UndoManager</code>.
     * <p>
     *  将<code> alive </code>设置为false。注意这是一个单向操作;死亡的编辑不能复活。
     * 发送<code> undo </code>或<code> redo </code>到死的编辑会导致抛出异常。
     * 
     *  <p>通常,当编辑由另一个编辑的<code> addEdit </code>或<code> replaceEdit </code>方法合并时,或者从<code> UndoManager </code>
     * 。
     * 
     */
    public void die() {
        alive = false;
    }

    /**
     * Throws <code>CannotUndoException</code> if <code>canUndo</code>
     * returns <code>false</code>. Sets <code>hasBeenDone</code>
     * to <code>false</code>. Subclasses should override to undo the
     * operation represented by this edit. Override should begin with
     * a call to super.
     *
     * <p>
     * 抛出<code> CannotUndoException </code>如果<code> canUndo </code>返回<code> false </code>。
     * 将<code> hasBeenDone </code>设置为<code> false </code>。子类应重写以撤消此编辑表示的操作。覆盖应该从调用super开始。
     * 
     * 
     * @exception CannotUndoException if <code>canUndo</code>
     *    returns <code>false</code>
     * @see     #canUndo
     */
    public void undo() throws CannotUndoException {
        if (!canUndo()) {
            throw new CannotUndoException();
        }
        hasBeenDone = false;
    }

    /**
     * Returns true if this edit is <code>alive</code>
     * and <code>hasBeenDone</code> is <code>true</code>.
     *
     * <p>
     *  如果此编辑为<code> alive </code>且<code> hasBeenDone </code>为<code> true </code>,则返回true。
     * 
     * 
     * @return true if this edit is <code>alive</code>
     *    and <code>hasBeenDone</code> is <code>true</code>
     *
     * @see     #die
     * @see     #undo
     * @see     #redo
     */
    public boolean canUndo() {
        return alive && hasBeenDone;
    }

    /**
     * Throws <code>CannotRedoException</code> if <code>canRedo</code>
     * returns false. Sets <code>hasBeenDone</code> to <code>true</code>.
     * Subclasses should override to redo the operation represented by
     * this edit. Override should begin with a call to super.
     *
     * <p>
     *  抛出<code> CannotRedoException </code>如果<code> canRedo </code>返回false。
     * 将<code> hasBeenDone </code>设置为<code> true </code>。子类应该覆盖以重做由此编辑表示的操作。覆盖应该从调用super开始。
     * 
     * 
     * @exception CannotRedoException if <code>canRedo</code>
     *     returns <code>false</code>
     * @see     #canRedo
     */
    public void redo() throws CannotRedoException {
        if (!canRedo()) {
            throw new CannotRedoException();
        }
        hasBeenDone = true;
    }

    /**
     * Returns <code>true</code> if this edit is <code>alive</code>
     * and <code>hasBeenDone</code> is <code>false</code>.
     *
     * <p>
     *  如果此编辑为<code> alive </code>且<code> hasBeenDone </code>为<code> false </code>,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this edit is <code>alive</code>
     *   and <code>hasBeenDone</code> is <code>false</code>
     * @see     #die
     * @see     #undo
     * @see     #redo
     */
    public boolean canRedo() {
        return alive && !hasBeenDone;
    }

    /**
     * This default implementation returns false.
     *
     * <p>
     *  此默认实现返回false。
     * 
     * 
     * @param anEdit the edit to be added
     * @return false
     *
     * @see UndoableEdit#addEdit
     */
    public boolean addEdit(UndoableEdit anEdit) {
        return false;
    }

    /**
     * This default implementation returns false.
     *
     * <p>
     *  此默认实现返回false。
     * 
     * 
     * @param anEdit the edit to replace
     * @return false
     *
     * @see UndoableEdit#replaceEdit
     */
    public boolean replaceEdit(UndoableEdit anEdit) {
        return false;
    }

    /**
     * This default implementation returns true.
     *
     * <p>
     *  此默认实现返回true。
     * 
     * 
     * @return true
     * @see UndoableEdit#isSignificant
     */
    public boolean isSignificant() {
        return true;
    }

    /**
     * This default implementation returns "". Used by
     * <code>getUndoPresentationName</code> and
     * <code>getRedoPresentationName</code> to
     * construct the strings they return. Subclasses should override to
     * return an appropriate description of the operation this edit
     * represents.
     *
     * <p>
     *  此默认实现返回""。
     * 由<code> getUndoPresentationName </code>和<code> getRedoPresentationName </code>使用以构造它们返回的字符串。
     * 子类应该覆盖以返回此编辑表示的操作的适当描述。
     * 
     * 
     * @return the empty string ""
     *
     * @see     #getUndoPresentationName
     * @see     #getRedoPresentationName
     */
    public String getPresentationName() {
        return "";
    }

    /**
     * Retreives the value from the defaults table with key
     * <code>AbstractUndoableEdit.undoText</code> and returns
     * that value followed by a space, followed by
     * <code>getPresentationName</code>.
     * If <code>getPresentationName</code> returns "",
     * then the defaults value is returned alone.
     *
     * <p>
     *  使用键<code> AbstractUndoableEdit.undoText </code>从defaults表中检索值,并返回该值后面跟一个空格,后面跟<code> getPresentation
     * Name </code>。
     * 如果<code> getPresentationName </code>返回"",那么将单独返回默认值。
     * 
     * 
     * @return the value from the defaults table with key
     *    <code>AbstractUndoableEdit.undoText</code>, followed
     *    by a space, followed by <code>getPresentationName</code>
     *    unless <code>getPresentationName</code> is "" in which
     *    case, the defaults value is returned alone.
     * @see #getPresentationName
     */
    public String getUndoPresentationName() {
        String name = getPresentationName();
        if (!"".equals(name)) {
            name = UIManager.getString("AbstractUndoableEdit.undoText") +
                " " + name;
        } else {
            name = UIManager.getString("AbstractUndoableEdit.undoText");
        }

        return name;
    }

    /**
     * Retreives the value from the defaults table with key
     * <code>AbstractUndoableEdit.redoText</code> and returns
     * that value followed by a space, followed by
     * <code>getPresentationName</code>.
     * If <code>getPresentationName</code> returns "",
     * then the defaults value is returned alone.
     *
     * <p>
     * 使用键<code> AbstractUndoableEdit.redoText </code>从defaults表中检索值,并返回该值后跟一个空格,后面跟<code> getPresentationNa
     * me </code>。
     * 如果<code> getPresentationName </code>返回"",那么将单独返回默认值。
     * 
     * 
     * @return the value from the defaults table with key
     *    <code>AbstractUndoableEdit.redoText</code>, followed
     *    by a space, followed by <code>getPresentationName</code>
     *    unless <code>getPresentationName</code> is "" in which
     *    case, the defaults value is returned alone.
     * @see #getPresentationName
     */
    public String getRedoPresentationName() {
        String name = getPresentationName();
        if (!"".equals(name)) {
            name = UIManager.getString("AbstractUndoableEdit.redoText") +
                " " + name;
        } else {
            name = UIManager.getString("AbstractUndoableEdit.redoText");
        }

        return name;
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     * 
     * @return a String representation of this object
     */
    public String toString()
    {
        return super.toString()
            + " hasBeenDone: " + hasBeenDone
            + " alive: " + alive;
    }
}
