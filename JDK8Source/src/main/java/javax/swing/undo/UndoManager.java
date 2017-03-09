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

package javax.swing.undo;

import javax.swing.event.*;
import javax.swing.UIManager;
import java.util.*;

/**
 * {@code UndoManager} manages a list of {@code UndoableEdits},
 * providing a way to undo or redo the appropriate edits.  There are
 * two ways to add edits to an <code>UndoManager</code>.  Add the edit
 * directly using the <code>addEdit</code> method, or add the
 * <code>UndoManager</code> to a bean that supports
 * <code>UndoableEditListener</code>.  The following examples creates
 * an <code>UndoManager</code> and adds it as an
 * <code>UndoableEditListener</code> to a <code>JTextField</code>:
 * <pre>
 *   UndoManager undoManager = new UndoManager();
 *   JTextField tf = ...;
 *   tf.getDocument().addUndoableEditListener(undoManager);
 * </pre>
 * <p>
 * <code>UndoManager</code> maintains an ordered list of edits and the
 * index of the next edit in that list. The index of the next edit is
 * either the size of the current list of edits, or if
 * <code>undo</code> has been invoked it corresponds to the index
 * of the last significant edit that was undone. When
 * <code>undo</code> is invoked all edits from the index of the next
 * edit to the last significant edit are undone, in reverse order.
 * For example, consider an <code>UndoManager</code> consisting of the
 * following edits: <b>A</b> <i>b</i> <i>c</i> <b>D</b>.  Edits with a
 * upper-case letter in bold are significant, those in lower-case
 * and italicized are insignificant.
 * <p>
 * <a name="figure1"></a>
 * <table border=0 summary="">
 * <tr><td>
 *     <img src="doc-files/UndoManager-1.gif" alt="">
 * <tr><td align=center>Figure 1
 * </table>
 * <p>
 * As shown in <a href="#figure1">figure 1</a>, if <b>D</b> was just added, the
 * index of the next edit will be 4. Invoking <code>undo</code>
 * results in invoking <code>undo</code> on <b>D</b> and setting the
 * index of the next edit to 3 (edit <i>c</i>), as shown in the following
 * figure.
 * <p>
 * <a name="figure2"></a>
 * <table border=0 summary="">
 * <tr><td>
 *     <img src="doc-files/UndoManager-2.gif" alt="">
 * <tr><td align=center>Figure 2
 * </table>
 * <p>
 * The last significant edit is <b>A</b>, so that invoking
 * <code>undo</code> again invokes <code>undo</code> on <i>c</i>,
 * <i>b</i>, and <b>A</b>, in that order, setting the index of the
 * next edit to 0, as shown in the following figure.
 * <p>
 * <a name="figure3"></a>
 * <table border=0 summary="">
 * <tr><td>
 *     <img src="doc-files/UndoManager-3.gif" alt="">
 * <tr><td align=center>Figure 3
 * </table>
 * <p>
 * Invoking <code>redo</code> results in invoking <code>redo</code> on
 * all edits between the index of the next edit and the next
 * significant edit (or the end of the list).  Continuing with the previous
 * example if <code>redo</code> were invoked, <code>redo</code> would in
 * turn be invoked on <b>A</b>, <i>b</i> and <i>c</i>.  In addition
 * the index of the next edit is set to 3 (as shown in <a
 * href="#figure2">figure 2</a>).
 * <p>
 * Adding an edit to an <code>UndoManager</code> results in
 * removing all edits from the index of the next edit to the end of
 * the list.  Continuing with the previous example, if a new edit,
 * <i>e</i>, is added the edit <b>D</b> is removed from the list
 * (after having <code>die</code> invoked on it).  If <i>c</i> is not
 * incorporated by the next edit
 * (<code><i>c</i>.addEdit(<i>e</i>)</code> returns true), or replaced
 * by it (<code><i>e</i>.replaceEdit(<i>c</i>)</code> returns true),
 * the new edit is added after <i>c</i>, as shown in the following
 * figure.
 * <p>
 * <a name="figure4"></a>
 * <table border=0 summary="">
 * <tr><td>
 *     <img src="doc-files/UndoManager-4.gif" alt="">
 * <tr><td align=center>Figure 4
 * </table>
 * <p>
 * Once <code>end</code> has been invoked on an <code>UndoManager</code>
 * the superclass behavior is used for all <code>UndoableEdit</code>
 * methods.  Refer to <code>CompoundEdit</code> for more details on its
 * behavior.
 * <p>
 * Unlike the rest of Swing, this class is thread safe.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  {@code UndoManager}管理{@code UndoableEdits}的列表,提供了撤消或重做相应修改的方法。
 * 有两种方法可以将修改添加到<code> UndoManager </code>。
 * 直接使用<code> addEdit </code>方法添加编辑,或将<code> UndoManager </code>添加到支持<code> UndoableEditListener </code>
 * 的bean。
 * 有两种方法可以将修改添加到<code> UndoManager </code>。
 * 以下示例创建一个<code> UndoManager </code>并将其作为<code> UndoableEditListener </code>添加到<code> JTextField </code>
 * 。
 * 有两种方法可以将修改添加到<code> UndoManager </code>。
 * <pre>
 *  UndoManager undoManager = new UndoManager(); JTextField tf = ...; tf.getDocument()。
 * addUndoableEditListener(undoManager);。
 * </pre>
 * <p>
 *  <code> UndoManager </code>维护编辑的有序列表和该列表中下一个编辑的索引。
 * 下一个编辑的索引是当前编辑列表的大小,或者如果<code> undo </code>已被调用,它对应于已撤销的最后一个有效编辑的索引。
 * 当调用<code> undo </code>时,所有从下一个编辑的索引到最后一个有效编辑的编辑都将以相反的顺序撤消。
 * 例如,考虑由以下修改组成的<code> UndoManager </code>：<b> A </b> <i> b </i> <i> c </i> <b> D </b >。
 * 带粗体字母的大写字母的编辑很重要,小写字母和斜体字母的编辑则不重要。
 * <p>
 *  <a name="figure1"> </a>
 * <table border=0 summary="">
 *  <tr> <td>
 * <img src="doc-files/UndoManager-1.gif" alt="">
 *  <tr> <td align = center>图1
 * </table>
 * <p>
 * 如<a href="#figure1">图1 </a>所示,如果刚刚添加了<b> D </b>,则下一个编辑的索引将为4.调用<code> undo </code >会在<b> D </b>上调用<code>
 *  undo </code>,并将下一个编辑的索引设置为3(编辑<i> c </i>),如下图所示。
 * <p>
 *  <a name="figure2"> </a>
 * <table border=0 summary="">
 *  <tr> <td>
 * <img src="doc-files/UndoManager-2.gif" alt="">
 *  <tr> <td align = center>图2
 * </table>
 * <p>
 *  最后一个有效编辑是<b> A </b>,因此调用<code> undo </code>会再次调用<c> </i>,<i> / i>和<b> A </b>,将下一个编辑的索引设置为0,如下图所示。
 * <p>
 *  <a name="figure3"> </a>
 * <table border=0 summary="">
 *  <tr> <td>
 * <img src="doc-files/UndoManager-3.gif" alt="">
 *  <tr> <td align = center>图3
 * </table>
 * <p>
 *  调用<code> redo </code>会导致在下一个编辑的索引和下一个重要编辑(或列表的结尾)之间的所有编辑上调用<code>重做</code>。
 * 继续上一个示例,如果<code> redo </code>被调用,<code> redo </code>将依次在<b> A </b>,<i> b < i> c。
 * 此外,下一个编辑的索引设置为3(如<a href="#figure2">图2 </a>所示)。
 * <p>
 * 向<code> UndoManager </code>添加编辑会导致将下一个编辑的索引中的所有编辑删除到列表末尾。
 * 继续前面的示例,如果添加了新的编辑<i> e </i>,则从列表中移除编辑<b> D </b>(在<code>它)。
 * 如果<i> c </i>未通过下一个编辑(<code> <i> c </i> .addEdit(<e> e </i>)</code>返回true) (<code> <i> e </i> .replace
 * Edit(<i> c </i>)</code>返回true),则新的编辑会在<i> c </i>之后添加如下图所示。
 * 继续前面的示例,如果添加了新的编辑<i> e </i>,则从列表中移除编辑<b> D </b>(在<code>它)。
 * <p>
 *  <a name="figure4"> </a>
 * <table border=0 summary="">
 *  <tr> <td>
 * <img src="doc-files/UndoManager-4.gif" alt="">
 *  <tr> <td align = center>图4
 * </table>
 * <p>
 *  在<code> UndoManager </code>上调用<code> end </code>后,超类行为将用于所有<code> UndoableEdit </code>方法。
 * 有关其行为的更多详细信息,请参阅<code> CompoundEdit </code>。
 * <p>
 *  与Swing的其余部分不同,这个类是线程安全的。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Ray Ryan
 */
public class UndoManager extends CompoundEdit implements UndoableEditListener {
    int indexOfNextAdd;
    int limit;

    /**
     * Creates a new <code>UndoManager</code>.
     * <p>
     *  创建新的<code> UndoManager </code>。
     * 
     */
    public UndoManager() {
        super();
        indexOfNextAdd = 0;
        limit = 100;
        edits.ensureCapacity(limit);
    }

    /**
     * Returns the maximum number of edits this {@code UndoManager}
     * holds. A value less than 0 indicates the number of edits is not
     * limited.
     *
     * <p>
     *  返回此{@code UndoManager}所拥有的最大编辑数。小于0的值表示编辑的数量不受限制。
     * 
     * 
     * @return the maximum number of edits this {@code UndoManager} holds
     * @see #addEdit
     * @see #setLimit
     */
    public synchronized int getLimit() {
        return limit;
    }

    /**
     * Empties the undo manager sending each edit a <code>die</code> message
     * in the process.
     *
     * <p>
     * 清空undo管理器,在进程中发送每个编辑<code> die </code>消息。
     * 
     * 
     * @see AbstractUndoableEdit#die
     */
    public synchronized void discardAllEdits() {
        for (UndoableEdit e : edits) {
            e.die();
        }
        edits = new Vector<UndoableEdit>();
        indexOfNextAdd = 0;
        // PENDING(rjrjr) when vector grows a removeRange() method
        // (expected in JDK 1.2), trimEdits() will be nice and
        // efficient, and this method can call that instead.
    }

    /**
     * Reduces the number of queued edits to a range of size limit,
     * centered on the index of the next edit.
     * <p>
     *  将排队编辑的数量减少到以下一个编辑的索引为中心的大小限制范围。
     * 
     */
    protected void trimForLimit() {
        if (limit >= 0) {
            int size = edits.size();
//          System.out.print("limit: " + limit +
//                           " size: " + size +
//                           " indexOfNextAdd: " + indexOfNextAdd +
//                           "\n");

            if (size > limit) {
                int halfLimit = limit/2;
                int keepFrom = indexOfNextAdd - 1 - halfLimit;
                int keepTo   = indexOfNextAdd - 1 + halfLimit;

                // These are ints we're playing with, so dividing by two
                // rounds down for odd numbers, so make sure the limit was
                // honored properly. Note that the keep range is
                // inclusive.

                if (keepTo - keepFrom + 1 > limit) {
                    keepFrom++;
                }

                // The keep range is centered on indexOfNextAdd,
                // but odds are good that the actual edits Vector
                // isn't. Move the keep range to keep it legal.

                if (keepFrom < 0) {
                    keepTo -= keepFrom;
                    keepFrom = 0;
                }
                if (keepTo >= size) {
                    int delta = size - keepTo - 1;
                    keepTo += delta;
                    keepFrom += delta;
                }

//              System.out.println("Keeping " + keepFrom + " " + keepTo);
                trimEdits(keepTo+1, size-1);
                trimEdits(0, keepFrom-1);
            }
        }
    }

    /**
     * Removes edits in the specified range.
     * All edits in the given range (inclusive, and in reverse order)
     * will have <code>die</code> invoked on them and are removed from
     * the list of edits. This has no effect if
     * <code>from</code> &gt; <code>to</code>.
     *
     * <p>
     *  删除指定范围内的修改。在给定范围内(包括和反向顺序)的所有修改都会对它们调用<code> die </code>,并从编辑列表中删除。
     * 如果<code>从</code>&gt; <code>到</code>。
     * 
     * 
     * @param from the minimum index to remove
     * @param to the maximum index to remove
     */
    protected void trimEdits(int from, int to) {
        if (from <= to) {
//          System.out.println("Trimming " + from + " " + to + " with index " +
//                           indexOfNextAdd);
            for (int i = to; from <= i; i--) {
                UndoableEdit e = edits.elementAt(i);
//              System.out.println("JUM: Discarding " +
//                                 e.getUndoPresentationName());
                e.die();
                // PENDING(rjrjr) when Vector supports range deletion (JDK
                // 1.2) , we can optimize the next line considerably.
                edits.removeElementAt(i);
            }

            if (indexOfNextAdd > to) {
//              System.out.print("...right...");
                indexOfNextAdd -= to-from+1;
            } else if (indexOfNextAdd >= from) {
//              System.out.println("...mid...");
                indexOfNextAdd = from;
            }

//          System.out.println("new index " + indexOfNextAdd);
        }
    }

    /**
     * Sets the maximum number of edits this <code>UndoManager</code>
     * holds. A value less than 0 indicates the number of edits is not
     * limited. If edits need to be discarded to shrink the limit,
     * <code>die</code> will be invoked on them in the reverse
     * order they were added.  The default is 100.
     *
     * <p>
     *  设置此<code> UndoManager </code>所保存的最大编辑数。小于0的值表示编辑的数量不受限制。
     * 如果编辑需要被丢弃以缩小限制,则<code> die </code>将按照它们添加的相反顺序在它们上被调用。默认值为100。
     * 
     * 
     * @param l the new limit
     * @throws RuntimeException if this {@code UndoManager} is not in progress
     *                          ({@code end} has been invoked)
     * @see #isInProgress
     * @see #end
     * @see #addEdit
     * @see #getLimit
     */
    public synchronized void setLimit(int l) {
        if (!inProgress) throw new RuntimeException("Attempt to call UndoManager.setLimit() after UndoManager.end() has been called");
        limit = l;
        trimForLimit();
    }


    /**
     * Returns the the next significant edit to be undone if <code>undo</code>
     * is invoked. This returns <code>null</code> if there are no edits
     * to be undone.
     *
     * <p>
     *  如果调用<code> undo </code>,则返回要撤销的下一个有效编辑。如果没有要撤销的修改,则返回<code> null </code>。
     * 
     * 
     * @return the next significant edit to be undone
     */
    protected UndoableEdit editToBeUndone() {
        int i = indexOfNextAdd;
        while (i > 0) {
            UndoableEdit edit = edits.elementAt(--i);
            if (edit.isSignificant()) {
                return edit;
            }
        }

        return null;
    }

    /**
     * Returns the the next significant edit to be redone if <code>redo</code>
     * is invoked. This returns <code>null</code> if there are no edits
     * to be redone.
     *
     * <p>
     *  如果调用<code> redo </code>,则返回要重做的下一个有效编辑。如果没有要重做的修改,则返回<code> null </code>。
     * 
     * 
     * @return the next significant edit to be redone
     */
    protected UndoableEdit editToBeRedone() {
        int count = edits.size();
        int i = indexOfNextAdd;

        while (i < count) {
            UndoableEdit edit = edits.elementAt(i++);
            if (edit.isSignificant()) {
                return edit;
            }
        }

        return null;
    }

    /**
     * Undoes all changes from the index of the next edit to
     * <code>edit</code>, updating the index of the next edit appropriately.
     *
     * <p>
     *  撤消下一个编辑的索引到<code> edit </code>的所有更改,适当更新下一个编辑的索引。
     * 
     * 
     * @throws CannotUndoException if one of the edits throws
     *         <code>CannotUndoException</code>
     */
    protected void undoTo(UndoableEdit edit) throws CannotUndoException {
        boolean done = false;
        while (!done) {
            UndoableEdit next = edits.elementAt(--indexOfNextAdd);
            next.undo();
            done = next == edit;
        }
    }

    /**
     * Redoes all changes from the index of the next edit to
     * <code>edit</code>, updating the index of the next edit appropriately.
     *
     * <p>
     *  将下一次编辑的索引中的所有更改重做到<code> edit </code>,以适当地更新下一个编辑的索引。
     * 
     * 
     * @throws CannotRedoException if one of the edits throws
     *         <code>CannotRedoException</code>
     */
    protected void redoTo(UndoableEdit edit) throws CannotRedoException {
        boolean done = false;
        while (!done) {
            UndoableEdit next = edits.elementAt(indexOfNextAdd++);
            next.redo();
            done = next == edit;
        }
    }

    /**
     * Convenience method that invokes one of <code>undo</code> or
     * <code>redo</code>. If any edits have been undone (the index of
     * the next edit is less than the length of the edits list) this
     * invokes <code>redo</code>, otherwise it invokes <code>undo</code>.
     *
     * <p>
     * 调用<code> undo </code>或<code>重做</code>之一的方便方法。
     * 如果任何编辑已撤消(下一个编辑的索引小于编辑列表的长度),则调用<code> redo </code>,否则调用<code> undo </code>。
     * 
     * 
     * @see #canUndoOrRedo
     * @see #getUndoOrRedoPresentationName
     * @throws CannotUndoException if one of the edits throws
     *         <code>CannotUndoException</code>
     * @throws CannotRedoException if one of the edits throws
     *         <code>CannotRedoException</code>
     */
    public synchronized void undoOrRedo() throws CannotRedoException,
        CannotUndoException {
        if (indexOfNextAdd == edits.size()) {
            undo();
        } else {
            redo();
        }
    }

    /**
     * Returns true if it is possible to invoke <code>undo</code> or
     * <code>redo</code>.
     *
     * <p>
     *  如果可以调用<code> undo </code>或<code> redo </code>,则返回true。
     * 
     * 
     * @return true if invoking <code>canUndoOrRedo</code> is valid
     * @see #undoOrRedo
     */
    public synchronized boolean canUndoOrRedo() {
        if (indexOfNextAdd == edits.size()) {
            return canUndo();
        } else {
            return canRedo();
        }
    }

    /**
     * Undoes the appropriate edits.  If <code>end</code> has been
     * invoked this calls through to the superclass, otherwise
     * this invokes <code>undo</code> on all edits between the
     * index of the next edit and the last significant edit, updating
     * the index of the next edit appropriately.
     *
     * <p>
     *  撤消相应的修改。如果调用了<code> end </code>,则调用超类,否则调用下一个编辑的索引和最后一个有效编辑之间的所有编辑的<code> undo </code>下一次编辑适当。
     * 
     * 
     * @throws CannotUndoException if one of the edits throws
     *         <code>CannotUndoException</code> or there are no edits
     *         to be undone
     * @see CompoundEdit#end
     * @see #canUndo
     * @see #editToBeUndone
     */
    public synchronized void undo() throws CannotUndoException {
        if (inProgress) {
            UndoableEdit edit = editToBeUndone();
            if (edit == null) {
                throw new CannotUndoException();
            }
            undoTo(edit);
        } else {
            super.undo();
        }
    }

    /**
     * Returns true if edits may be undone.  If <code>end</code> has
     * been invoked, this returns the value from super.  Otherwise
     * this returns true if there are any edits to be undone
     * (<code>editToBeUndone</code> returns non-<code>null</code>).
     *
     * <p>
     *  如果修改可能被撤消,则返回true。如果<code> end </code>被调用,则返回super的值。
     * 否则,如果有任何编辑要撤消(<code> editToBeUndone </code>返回非<code> null </code>),则返回true。
     * 
     * 
     * @return true if there are edits to be undone
     * @see CompoundEdit#canUndo
     * @see #editToBeUndone
     */
    public synchronized boolean canUndo() {
        if (inProgress) {
            UndoableEdit edit = editToBeUndone();
            return edit != null && edit.canUndo();
        } else {
            return super.canUndo();
        }
    }

    /**
     * Redoes the appropriate edits.  If <code>end</code> has been
     * invoked this calls through to the superclass.  Otherwise
     * this invokes <code>redo</code> on all edits between the
     * index of the next edit and the next significant edit, updating
     * the index of the next edit appropriately.
     *
     * <p>
     *  重做相应的编辑。如果<code> end </code>被调用,这将调用到超类。
     * 否则,将在下一个编辑的索引和下一个有效编辑之间的所有编辑上调用<code>重做</code>,以适当地更新下一个编辑的索引。
     * 
     * 
     * @throws CannotRedoException if one of the edits throws
     *         <code>CannotRedoException</code> or there are no edits
     *         to be redone
     * @see CompoundEdit#end
     * @see #canRedo
     * @see #editToBeRedone
     */
    public synchronized void redo() throws CannotRedoException {
        if (inProgress) {
            UndoableEdit edit = editToBeRedone();
            if (edit == null) {
                throw new CannotRedoException();
            }
            redoTo(edit);
        } else {
            super.redo();
        }
    }

    /**
     * Returns true if edits may be redone.  If <code>end</code> has
     * been invoked, this returns the value from super.  Otherwise,
     * this returns true if there are any edits to be redone
     * (<code>editToBeRedone</code> returns non-<code>null</code>).
     *
     * <p>
     *  如果修改可能被重做,则返回true。如果<code> end </code>被调用,则返回super的值。
     * 否则,如果有任何编辑要重做(<code> editToBeRedone </code>返回非<code> null </code>),则返回true。
     * 
     * 
     * @return true if there are edits to be redone
     * @see CompoundEdit#canRedo
     * @see #editToBeRedone
     */
    public synchronized boolean canRedo() {
        if (inProgress) {
            UndoableEdit edit = editToBeRedone();
            return edit != null && edit.canRedo();
        } else {
            return super.canRedo();
        }
    }

    /**
     * Adds an <code>UndoableEdit</code> to this
     * <code>UndoManager</code>, if it's possible.  This removes all
     * edits from the index of the next edit to the end of the edits
     * list.  If <code>end</code> has been invoked the edit is not added
     * and <code>false</code> is returned.  If <code>end</code> hasn't
     * been invoked this returns <code>true</code>.
     *
     * <p>
     * 向此<code> UndoManager </code>添加<code> UndoableEdit </code>(如果可能)。这会将下一个编辑的索引中的所有编辑内容移除到编辑列表的结尾。
     * 如果已调用<code> end </code>,则不会添加编辑,并返回<code> false </code>。
     * 如果未调用<code> end </code>,则返回<code> true </code>。
     * 
     * 
     * @param anEdit the edit to be added
     * @return true if <code>anEdit</code> can be incorporated into this
     *              edit
     * @see CompoundEdit#end
     * @see CompoundEdit#addEdit
     */
    public synchronized boolean addEdit(UndoableEdit anEdit) {
        boolean retVal;

        // Trim from the indexOfNextAdd to the end, as we'll
        // never reach these edits once the new one is added.
        trimEdits(indexOfNextAdd, edits.size()-1);

        retVal = super.addEdit(anEdit);
        if (inProgress) {
          retVal = true;
        }

        // Maybe super added this edit, maybe it didn't (perhaps
        // an in progress compound edit took it instead. Or perhaps
        // this UndoManager is no longer in progress). So make sure
        // the indexOfNextAdd is pointed at the right place.
        indexOfNextAdd = edits.size();

        // Enforce the limit
        trimForLimit();

        return retVal;
    }


    /**
     * Turns this <code>UndoManager</code> into a normal
     * <code>CompoundEdit</code>.  This removes all edits that have
     * been undone.
     *
     * <p>
     *  将<code> UndoManager </code>变成正常的<code> CompoundEdit </code>。此操作会删除所有已撤消的修改。
     * 
     * 
     * @see CompoundEdit#end
     */
    public synchronized void end() {
        super.end();
        this.trimEdits(indexOfNextAdd, edits.size()-1);
    }

    /**
     * Convenience method that returns either
     * <code>getUndoPresentationName</code> or
     * <code>getRedoPresentationName</code>.  If the index of the next
     * edit equals the size of the edits list,
     * <code>getUndoPresentationName</code> is returned, otherwise
     * <code>getRedoPresentationName</code> is returned.
     *
     * <p>
     *  方便方法返回<code> getUndoPresentationName </code>或<code> getRedoPresentationName </code>。
     * 如果下一个编辑的索引等于编辑列表的大小,则返回<code> getUndoPresentationName </code>,否则返回<code> getRedoPresentationName </code>
     * 。
     *  方便方法返回<code> getUndoPresentationName </code>或<code> getRedoPresentationName </code>。
     * 
     * 
     * @return undo or redo name
     */
    public synchronized String getUndoOrRedoPresentationName() {
        if (indexOfNextAdd == edits.size()) {
            return getUndoPresentationName();
        } else {
            return getRedoPresentationName();
        }
    }

    /**
     * Returns a description of the undoable form of this edit.
     * If <code>end</code> has been invoked this calls into super.
     * Otherwise if there are edits to be undone, this returns
     * the value from the next significant edit that will be undone.
     * If there are no edits to be undone and <code>end</code> has not
     * been invoked this returns the value from the <code>UIManager</code>
     * property "AbstractUndoableEdit.undoText".
     *
     * <p>
     *  返回此修改的可撤消表单的说明。如果<code> end </code>被调用,则调用super。否则,如果有要撤消的修改,则会返回下一个要撤消的重要修改的值。
     * 如果没有要撤消的编辑并且未调用<code> end </code>,则返回<code> UIManager </code>属性"AbstractUndoableEdit.undoText"中的值。
     * 
     * 
     * @return a description of the undoable form of this edit
     * @see     #undo
     * @see     CompoundEdit#getUndoPresentationName
     */
    public synchronized String getUndoPresentationName() {
        if (inProgress) {
            if (canUndo()) {
                return editToBeUndone().getUndoPresentationName();
            } else {
                return UIManager.getString("AbstractUndoableEdit.undoText");
            }
        } else {
            return super.getUndoPresentationName();
        }
    }

    /**
     * Returns a description of the redoable form of this edit.
     * If <code>end</code> has been invoked this calls into super.
     * Otherwise if there are edits to be redone, this returns
     * the value from the next significant edit that will be redone.
     * If there are no edits to be redone and <code>end</code> has not
     * been invoked this returns the value from the <code>UIManager</code>
     * property "AbstractUndoableEdit.redoText".
     *
     * <p>
     * 返回此修改的可重做表单的说明。如果<code> end </code>被调用,则调用super。否则,如果有要重做的编辑,则返回将要重做的下一个有效编辑的值。
     * 如果没有要重做的编辑,并且未调用<code> end </code>,则返回<code> UIManager </code>属性"AbstractUndoableEdit.redoText"中的值。
     * 
     * 
     * @return a description of the redoable form of this edit
     * @see     #redo
     * @see     CompoundEdit#getRedoPresentationName
     */
    public synchronized String getRedoPresentationName() {
        if (inProgress) {
            if (canRedo()) {
                return editToBeRedone().getRedoPresentationName();
            } else {
                return UIManager.getString("AbstractUndoableEdit.redoText");
            }
        } else {
            return super.getRedoPresentationName();
        }
    }

    /**
     * An <code>UndoableEditListener</code> method. This invokes
     * <code>addEdit</code> with <code>e.getEdit()</code>.
     *
     * <p>
     *  <code> UndoableEditListener </code>方法。这会使用<code> e.getEdit()</code>调用<code> addEdit </code>。
     * 
     * 
     * @param e the <code>UndoableEditEvent</code> the
     *        <code>UndoableEditEvent</code> will be added from
     * @see #addEdit
     */
    public void undoableEditHappened(UndoableEditEvent e) {
        addEdit(e.getEdit());
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * @return a String representation of this object
     */
    public String toString() {
        return super.toString() + " limit: " + limit +
            " indexOfNextAdd: " + indexOfNextAdd;
    }
}
