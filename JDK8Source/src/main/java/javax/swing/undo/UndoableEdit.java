/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An <code>UndoableEdit</code> represents an edit.  The edit may
 * be undone, or if already undone the edit may be redone.
 * <p>
 * <code>UndoableEdit</code> is designed to be used with the
 * <code>UndoManager</code>.  As <code>UndoableEdit</code>s are generated
 * by an <code>UndoableEditListener</code> they are typically added to
 * the <code>UndoManager</code>.  When an <code>UndoableEdit</code>
 * is added to an <code>UndoManager</code> the following occurs (assuming
 * <code>end</code> has not been called on the <code>UndoManager</code>):
 * <ol>
 * <li>If the <code>UndoManager</code> contains edits it will call
 *     <code>addEdit</code> on the current edit passing in the new edit
 *     as the argument.  If <code>addEdit</code> returns true the
 *     new edit is assumed to have been incorporated into the current edit and
 *     the new edit will not be added to the list of current edits.
 *     Edits can use <code>addEdit</code> as a way for smaller edits to
 *     be incorporated into a larger edit and treated as a single edit.
 * <li>If <code>addEdit</code> returns false <code>replaceEdit</code>
 *     is called on the new edit with the current edit passed in as the
 *     argument. This is the inverse of <code>addEdit</code> &#151;
 *     if the new edit returns true from <code>replaceEdit</code>, the new
 *     edit replaces the current edit.
 * </ol>
 * The <code>UndoManager</code> makes use of
 * <code>isSignificant</code> to determine how many edits should be
 * undone or redone.  The <code>UndoManager</code> will undo or redo
 * all insignificant edits (<code>isSignificant</code> returns false)
 * between the current edit and the last or
 * next significant edit.   <code>addEdit</code> and
 * <code>replaceEdit</code> can be used to treat multiple edits as
 * a single edit, returning false from <code>isSignificant</code>
 * allows for treating can be used to
 * have many smaller edits undone or redone at once.  Similar functionality
 * can also be done using the <code>addEdit</code> method.
 *
 * <p>
 *  <code> UndoableEdit </code>表示编辑。编辑可以撤消,或者如果已经撤消,则可以重新编辑编辑。
 * <p>
 *  <code> UndoableEdit </code>旨在与<code> UndoManager </code>配合使用。
 * 由于<code> UndoableEdit </code>由<code> UndoableEditListener </code>生成,它们通常被添加到<code> UndoManager </code>
 * 。
 *  <code> UndoableEdit </code>旨在与<code> UndoManager </code>配合使用。
 * 当将<code> UndoableEdit </code>添加到<code> UndoManager </code>时,会发生以下情况(假设未在<code> UndoManager </code>上调用
 * <code> end </code> ：。
 *  <code> UndoableEdit </code>旨在与<code> UndoManager </code>配合使用。
 * <ol>
 *  <li>如果<code> UndoManager </code>包含编辑,它将在当前编辑中调用<code> addEdit </code>作为参数传递。
 * 如果<code> addEdit </code>返回true,则假定新编辑已合并到当前编辑中,并且新编辑将不会添加到当前编辑的列表中。
 * 编辑可以使用<code> addEdit </code>作为较小编辑内容整合到较大编辑中并视为单个编辑的方式。
 *  <li>如果<code> addEdit </code>返回false <code> replaceEdit </code>在新编辑中调用,并将当前编辑作为参数传入。
 * 这是<code> addEdit </code>的反码 - 如果新编辑从<code> replaceEdit </code>返回true,新的编辑将替换当前编辑。
 * </ol>
 * <code> UndoManager </code>使用<code> isSignificant </code>来确定应该撤销或重做的修改数量。
 *  <code> UndoManager </code>将撤消或重做当前编辑和最后一个或下一个有效编辑之间的所有无关紧要的编辑(<code> isSignificant </code>返回false)。
 *  <code> addEdit </code>和<code> replaceEdit </code>可用于将多个编辑视为单个编辑,从<code> isSignificant返回false </code>
 * 允许处理可用于具有许多较小编辑立即取消或重做。
 *  <code> UndoManager </code>将撤消或重做当前编辑和最后一个或下一个有效编辑之间的所有无关紧要的编辑(<code> isSignificant </code>返回false)。
 * 类似的功能也可以使用<code> addEdit </code>方法来完成。
 * 
 * 
 * @author Ray Ryan
 */
public interface UndoableEdit {
    /**
     * Undo the edit.
     *
     * <p>
     *  撤消编辑。
     * 
     * 
     * @throws CannotUndoException if this edit can not be undone
     */
    public void undo() throws CannotUndoException;

    /**
     * Returns true if this edit may be undone.
     *
     * <p>
     *  如果此修改可以撤销,则返回true。
     * 
     * 
     * @return true if this edit may be undone
     */
    public boolean canUndo();

    /**
     * Re-applies the edit.
     *
     * <p>
     *  重新应用编辑。
     * 
     * 
     * @throws CannotRedoException if this edit can not be redone
     */
    public void redo() throws CannotRedoException;

    /**
     * Returns true if this edit may be redone.
     *
     * <p>
     *  如果此修改可能被重做,则返回true。
     * 
     * 
     * @return true if this edit may be redone
     */
    public boolean canRedo();

    /**
     * Informs the edit that it should no longer be used. Once an
     * <code>UndoableEdit</code> has been marked as dead it can no longer
     * be undone or redone.
     * <p>
     * This is a useful hook for cleaning up state no longer
     * needed once undoing or redoing is impossible--for example,
     * deleting file resources used by objects that can no longer be
     * undeleted. <code>UndoManager</code> calls this before it dequeues edits.
     * <p>
     * Note that this is a one-way operation. There is no "un-die"
     * method.
     *
     * <p>
     *  通知编辑它不应再使用。一旦<code> UndoableEdit </code>被标记为已死,就无法再恢复或重做。
     * <p>
     *  这是一个有用的挂钩,用于清除状态不再需要一旦撤消或重做是不可能的 - 例如,删除由不能再删除的对象使用的文件资源。 <code> UndoManager </code>在出队编辑之前调用此方法。
     * <p>
     *  注意这是一个单向操作。没有"非模"方法。
     * 
     * 
     * @see CompoundEdit#die
     */
    public void die();

    /**
     * Adds an <code>UndoableEdit</code> to this <code>UndoableEdit</code>.
     * This method can be used to coalesce smaller edits into a larger
     * compound edit.  For example, text editors typically allow
     * undo operations to apply to words or sentences.  The text
     * editor may choose to generate edits on each key event, but allow
     * those edits to be coalesced into a more user-friendly unit, such as
     * a word. In this case, the <code>UndoableEdit</code> would
     * override <code>addEdit</code> to return true when the edits may
     * be coalesced.
     * <p>
     * A return value of true indicates <code>anEdit</code> was incorporated
     * into this edit.  A return value of false indicates <code>anEdit</code>
     * may not be incorporated into this edit.
     * <p>Typically the receiver is already in the queue of a
     * <code>UndoManager</code> (or other <code>UndoableEditListener</code>),
     * and is being given a chance to incorporate <code>anEdit</code>
     * rather than letting it be added to the queue in turn.</p>
     *
     * <p>If true is returned, from now on <code>anEdit</code> must return
     * false from <code>canUndo</code> and <code>canRedo</code>,
     * and must throw the appropriate exception on <code>undo</code> or
     * <code>redo</code>.</p>
     *
     * <p>
     * 向此<code> UndoableEdit </code>中添加<code> UndoableEdit </code>。此方法可用于将较小的编辑合并为较大的复合编辑。
     * 例如,文本编辑器通常允许撤消操作应用于单词或句子。文本编辑器可以选择对每个键事件生成编辑,但允许将这些编辑合并到更加用户友好的单元中,例如单词。
     * 在这种情况下,当编辑可能合并时,<code> UndoableEdit </code>会覆盖<code> addEdit </code>返回true。
     * <p>
     *  返回值true表示<code> anEdit </code>已合并到此编辑中。返回值false表示<code> anEdit </code>可能不会合并到此编辑中。
     *  <p>通常,接收者已经在<code> UndoManager </code>(或其他<code> UndoableEditListener </code>)的队列中,并且有机会将<code> anEd
     * it </code>而不是让它依次添加到队列中。
     *  返回值true表示<code> anEdit </code>已合并到此编辑中。返回值false表示<code> anEdit </code>可能不会合并到此编辑中。</p>。
     * 
     *  <p>如果返回true,则从<code> anEdit </code>必须从<code> canUndo </code>和<code> canRedo </code>返回false,并且必须在<code>
     *  > undo </code>或<code> redo </code>。
     * </p>。
     * 
     * 
     * @param anEdit the edit to be added
     * @return true if <code>anEdit</code> may be incorporated into this
     *              edit
     */
    public boolean addEdit(UndoableEdit anEdit);

    /**
     * Returns true if this <code>UndoableEdit</code> should replace
     * <code>anEdit</code>. This method is used by <code>CompoundEdit</code>
     * and the <code>UndoManager</code>; it is called if
     * <code>anEdit</code> could not be added to the current edit
     * (<code>addEdit</code> returns false).
     * <p>
     * This method provides a way for an edit to replace an existing edit.
     * <p>This message is the opposite of addEdit--anEdit has typically
     * already been queued in an <code>UndoManager</code> (or other
     * UndoableEditListener), and the receiver is being given a chance
     * to take its place.</p>
     *
     * <p>If true is returned, from now on anEdit must return false from
     * canUndo() and canRedo(), and must throw the appropriate
     * exception on undo() or redo().</p>
     *
     * <p>
     *  如果此<code> UndoableEdit </code>应替换<code> anEdit </code>,则返回true。
     * 此方法由<code> CompoundEdit </code>和<code> UndoManager </code>使用;如果<code> anEdit </code>无法添加到当前编辑(<code> 
     * addEdit </code>返回false),则会调用它。
     *  如果此<code> UndoableEdit </code>应替换<code> anEdit </code>,则返回true。
     * <p>
     * 此方法提供了编辑替换现有编辑的方法。
     *  <p>此消息与addEdit相反,anEdit通常已在<code> UndoManager </code>(或其他UndoableEditListener)中排队,接收方将有机会取代它。</p >。
     * 
     *  <p>如果返回true,则从现在起anEdit必须从canUndo()和canRedo()返回false,并且必须在undo()或redo()上抛出相应的异常。</p>
     * 
     * 
     * @param anEdit the edit that replaces the current edit
     * @return true if this edit should replace <code>anEdit</code>
     */
    public boolean replaceEdit(UndoableEdit anEdit);

    /**
     * Returns true if this edit is considered significant.  A significant
     * edit is typically an edit that should be presented to the user, perhaps
     * on a menu item or tooltip.  The <code>UndoManager</code> will undo,
     * or redo, all insignificant edits to the next significant edit.
     *
     * <p>
     *  如果此编辑被视为重要,则返回true。重要的编辑通常是应当向用户呈现的编辑,可能在菜单项或工具提示上。
     *  <code> UndoManager </code>将撤消或重做对下一个重要编辑的所有无关紧要的修改。
     * 
     * 
     * @return true if this edit is significant
     */
    public boolean isSignificant();

    /**
     * Returns a localized, human-readable description of this edit, suitable
     * for use in a change log, for example.
     *
     * <p>
     *  返回此编辑的本地化的,可读的描述,例如适用于更改日志。
     * 
     * 
     * @return description of this edit
     */
    public String getPresentationName();

    /**
     * Returns a localized, human-readable description of the undoable form of
     * this edit, suitable for use as an Undo menu item, for example.
     * This is typically derived from <code>getPresentationName</code>.
     *
     * <p>
     *  返回此编辑的可撤销表单的本地化的,可读的描述,例如适合用作撤消菜单项。这通常来自<code> getPresentationName </code>。
     * 
     * 
     * @return a description of the undoable form of this edit
     */
    public String getUndoPresentationName();

    /**
     * Returns a localized, human-readable description of the redoable form of
     * this edit, suitable for use as a Redo menu item, for example. This is
     * typically derived from <code>getPresentationName</code>.
     *
     * <p>
     *  返回此编辑的可重做形式的本地化的,可读的描述,例如适合用作重做菜单项。这通常来自<code> getPresentationName </code>。
     * 
     * @return a description of the redoable form of this edit
     */
    public String getRedoPresentationName();
}
