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

package javax.swing.tree;

import javax.swing.event.*;
import java.beans.PropertyChangeListener;

/**
  * This interface represents the current state of the selection for
  * the tree component.
  * For information and examples of using tree selection models,
  * see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
  * in <em>The Java Tutorial.</em>
  *
  * <p>
  * The state of the tree selection is characterized by
  * a set of TreePaths, and optionally a set of integers. The mapping
  * from TreePath to integer is done by way of an instance of RowMapper.
  * It is not necessary for a TreeSelectionModel to have a RowMapper to
  * correctly operate, but without a RowMapper <code>getSelectionRows</code>
  * will return null.
  *
  * <p>
  *
  * A TreeSelectionModel can be configured to allow only one
  * path (<code>SINGLE_TREE_SELECTION</code>) a number of
  * contiguous paths (<code>CONTIGUOUS_TREE_SELECTION</code>) or a number of
  * discontiguous paths (<code>DISCONTIGUOUS_TREE_SELECTION</code>).
  * A <code>RowMapper</code> is used to determine if TreePaths are
  * contiguous.
  * In the absence of a RowMapper <code>CONTIGUOUS_TREE_SELECTION</code> and
  * <code>DISCONTIGUOUS_TREE_SELECTION</code> behave the same, that is they
  * allow any number of paths to be contained in the TreeSelectionModel.
  *
  * <p>
  *
  * For a selection model of <code>CONTIGUOUS_TREE_SELECTION</code> any
  * time the paths are changed (<code>setSelectionPath</code>,
  * <code>addSelectionPath</code> ...) the TreePaths are again checked to
  * make they are contiguous. A check of the TreePaths can also be forced
  * by invoking <code>resetRowSelection</code>. How a set of discontiguous
  * TreePaths is mapped to a contiguous set is left to implementors of
  * this interface to enforce a particular policy.
  *
  * <p>
  *
  * Implementations should combine duplicate TreePaths that are
  * added to the selection. For example, the following code
  * <pre>
  *   TreePath[] paths = new TreePath[] { treePath, treePath };
  *   treeSelectionModel.setSelectionPaths(paths);
  * </pre>
  * should result in only one path being selected:
  * <code>treePath</code>, and
  * not two copies of <code>treePath</code>.
  *
  * <p>
  *
  * The lead TreePath is the last path that was added (or set). The lead
  * row is then the row that corresponds to the TreePath as determined
  * from the RowMapper.
  *
  * <p>
  *  此接口表示树组件的选择的当前状态。
  * 有关使用树选择模型的信息和示例,请参阅<em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">
  * 如何使用树</a> > Java教程。
  *  此接口表示树组件的选择的当前状态。</em>。
  * 
  * <p>
  *  树选择的状态的特征在于一组TreePath,以及可选的一组整数。从TreePath到整数的映射是通过RowMapper的一个实例来完成的。
  *  TreeSelectionModel没有必要使RowMapper正确操作,但是没有RowMapper <code> getSelectionRows </code>将返回null。
  * 
  * <p>
  * 
  *  TreeSelectionModel可被配置为仅允许多个连续路径(<code> CONTIGUOUS_TREE_SELECTION </code>)或多个不连续路径(<code> DISCONTIGU
  * OUS_TREE_SELECTION </code>)中的一个路径(<code> SINGLE_TREE_SELECTION </code> )。
  *  <code> RowMapper </code>用于确定TreePath是否是连续的。
  * 在没有RowMapper <code> CONTIGUOUS_TREE_SELECTION </code>和<code> DISCONTIGUOUS_TREE_SELECTION </code>行为相同
  * 的情况下,也就是说,它们允许在TreeSelectionModel中包含任何数量的路径。
  *  <code> RowMapper </code>用于确定TreePath是否是连续的。
  * 
  * <p>
  * 
  * 对于<code> CONTIGUOUS_TREE_SELECTION </code>的选择模型,任何时候路径被改变(<code> setSelectionPath </code>,<code> addS
  * electionPath </code> ...),再次检查TreePath,连续。
  * 还可以通过调用<code> resetRowSelection </code>来强制检查TreePath。如何将一组不连续的TreePath映射到连续集合,留给此接口的实现者来强制执行特定策略。
  * 
  * <p>
  * 
  *  实现应该合并添加到选择中的重复TreePath。例如,下面的代码
  * <pre>
  *  TreePath [] paths = new TreePath [] {treePath,treePath}; treeSelectionModel.setSelectionPaths(paths)
  * ;。
  * </pre>
  *  应该只导致选择一个路径：<code> treePath </code>,而不是<code> treePath </code>的两个副本。
  * 
  * <p>
  * 
  *  引导TreePath是添加(或设置)的最后一个路径。引导行是对应于从RowMapper确定的TreePath的行。
  * 
  * 
  * @author Scott Violet
  */

public interface TreeSelectionModel
{
    /** Selection can only contain one path at a time. */
    public static final int               SINGLE_TREE_SELECTION = 1;

    /** Selection can only be contiguous. This will only be enforced if
     * a RowMapper instance is provided. That is, if no RowMapper is set
     * <p>
     *  提供了一个RowMapper实例。也就是说,如果没有设置RowMapper
     * 
     * 
     * this behaves the same as DISCONTIGUOUS_TREE_SELECTION. */
    public static final int               CONTIGUOUS_TREE_SELECTION = 2;

    /** Selection can contain any number of items that are not necessarily
    /* <p>
    /* 
     * contiguous. */
    public static final int               DISCONTIGUOUS_TREE_SELECTION = 4;

    /**
     * Sets the selection model, which must be one of SINGLE_TREE_SELECTION,
     * CONTIGUOUS_TREE_SELECTION or DISCONTIGUOUS_TREE_SELECTION.
     * <p>
     * This may change the selection if the current selection is not valid
     * for the new mode. For example, if three TreePaths are
     * selected when the mode is changed to <code>SINGLE_TREE_SELECTION</code>,
     * only one TreePath will remain selected. It is up to the particular
     * implementation to decide what TreePath remains selected.
     * <p>
     *  设置选择模型,它必须是SINGLE_TREE_SELECTION,CONTIGUOUS_TREE_SELECTION或DISCONTIGUOUS_TREE_SELECTION之一。
     * <p>
     *  如果当前选择对于新模式无效,则这可以改变选择。
     * 例如,如果在将模式更改为<code> SINGLE_TREE_SELECTION </code>时选择了三个TreePath,则只会保留一个TreePath。
     * 由特定实现决定什么TreePath保持选择。
     * 
     */
    void setSelectionMode(int mode);

    /**
     * Returns the current selection mode, one of
     * <code>SINGLE_TREE_SELECTION</code>,
     * <code>CONTIGUOUS_TREE_SELECTION</code> or
     * <code>DISCONTIGUOUS_TREE_SELECTION</code>.
     * <p>
     * 返回当前选择模式,<code> SINGLE_TREE_SELECTION </code>,<code> CONTIGUOUS_TREE_SELECTION </code>或<code> DISCONT
     * IGUOUS_TREE_SELECTION </code>之一。
     * 
     */
    int getSelectionMode();

    /**
      * Sets the selection to path. If this represents a change, then
      * the TreeSelectionListeners are notified. If <code>path</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      *
      * <p>
      *  将选择设置为路径。如果这表示更改,那么将通知TreeSelectionListeners。
      * 如果<code> path </code>为null,这与调用<code> clearSelection </code>具有相同的效果。
      * 
      * 
      * @param path new path to select
      */
    void setSelectionPath(TreePath path);

    /**
      * Sets the selection to path. If this represents a change, then
      * the TreeSelectionListeners are notified. If <code>paths</code> is
      * null, this has the same effect as invoking <code>clearSelection</code>.
      *
      * <p>
      *  将选择设置为路径。如果这表示更改,那么将通知TreeSelectionListeners。
      * 如果<code> paths </code>为null,这与调用<code> clearSelection </code>具有相同的效果。
      * 
      * 
      * @param paths new selection
      */
    void setSelectionPaths(TreePath[] paths);

    /**
      * Adds path to the current selection. If path is not currently
      * in the selection the TreeSelectionListeners are notified. This has
      * no effect if <code>path</code> is null.
      *
      * <p>
      *  添加当前选择的路径。如果路径当前不在选择中,则通知TreeSelectionListeners。如果<code> path </code>为null,则没有效果。
      * 
      * 
      * @param path the new path to add to the current selection
      */
    void addSelectionPath(TreePath path);

    /**
      * Adds paths to the current selection.  If any of the paths in
      * paths are not currently in the selection the TreeSelectionListeners
      * are notified. This has
      * no effect if <code>paths</code> is null.
      *
      * <p>
      *  添加当前选择的路径。如果路径中的任何路径当前不在选择中,则通知TreeSelectionListeners。如果<code> paths </code>为null,则没有任何效果。
      * 
      * 
      * @param paths the new paths to add to the current selection
      */
    void addSelectionPaths(TreePath[] paths);

    /**
      * Removes path from the selection. If path is in the selection
      * The TreeSelectionListeners are notified. This has no effect if
      * <code>path</code> is null.
      *
      * <p>
      *  从选择中删除路径。如果路径在选择中,则通知TreeSelectionListeners。如果<code> path </code>为null,则没有效果。
      * 
      * 
      * @param path the path to remove from the selection
      */
    void removeSelectionPath(TreePath path);

    /**
      * Removes paths from the selection.  If any of the paths in
      * <code>paths</code>
      * are in the selection, the TreeSelectionListeners are notified.
      * This method has no effect if <code>paths</code> is null.
      *
      * <p>
      *  从选择中删除路径。如果<code>路径</code>中的任何路径在选择中,则通知TreeSelectionListeners。如果<code> paths </code>为null,此方法不起作用。
      * 
      * 
      * @param paths the path to remove from the selection
      */
    void removeSelectionPaths(TreePath[] paths);

    /**
      * Returns the first path in the selection. How first is defined is
      * up to implementors, and may not necessarily be the TreePath with
      * the smallest integer value as determined from the
      * <code>RowMapper</code>.
      * <p>
      *  返回选择中的第一个路径。如何首先定义的是实现者,并且可能不一定是具有从<code> RowMapper </code>确定的最小整数值的TreePath。
      * 
      */
    TreePath getSelectionPath();

    /**
      * Returns the paths in the selection. This will return null (or an
      * empty array) if nothing is currently selected.
      * <p>
      * 返回选择中的路径。如果当前没有选择,则返回null(或空数组)。
      * 
      */
    TreePath[] getSelectionPaths();

    /**
     * Returns the number of paths that are selected.
     * <p>
     *  返回所选路径的数量。
     * 
     */
    int getSelectionCount();

    /**
      * Returns true if the path, <code>path</code>, is in the current
      * selection.
      * <p>
      *  如果路径<code> path </code>在当前选择中,则返回true。
      * 
      */
    boolean isPathSelected(TreePath path);

    /**
      * Returns true if the selection is currently empty.
      * <p>
      *  如果选择当前为空,则返回true。
      * 
      */
    boolean isSelectionEmpty();

    /**
      * Empties the current selection.  If this represents a change in the
      * current selection, the selection listeners are notified.
      * <p>
      *  清空当前选择。如果这表示当前选择中的更改,则通知选择侦听器。
      * 
      */
    void clearSelection();

    /**
     * Sets the RowMapper instance. This instance is used to determine
     * the row for a particular TreePath.
     * <p>
     *  设置RowMapper实例。此实例用于确定特定TreePath的行。
     * 
     */
    void setRowMapper(RowMapper newMapper);

    /**
     * Returns the RowMapper instance that is able to map a TreePath to a
     * row.
     * <p>
     *  返回能够将TreePath映射到行的RowMapper实例。
     * 
     */
    RowMapper getRowMapper();

    /**
      * Returns all of the currently selected rows. This will return
      * null (or an empty array) if there are no selected TreePaths or
      * a RowMapper has not been set.
      * <p>
      *  返回所有当前选定的行。如果没有选择TreePath或没有设置RowMapper,这将返回null(或一个空数组)。
      * 
      */
    int[] getSelectionRows();

    /**
     * Returns the smallest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
     * <p>
     *  返回从当前选定TreePath集合的RowMapper获取的最小值。如果没有选择,或没有RowMapper,这将返回-1。
     * 
      */
    int getMinSelectionRow();

    /**
     * Returns the largest value obtained from the RowMapper for the
     * current set of selected TreePaths. If nothing is selected,
     * or there is no RowMapper, this will return -1.
     * <p>
     *  返回从当前选定TreePaths集合的RowMapper获取的最大值。如果没有选择,或没有RowMapper,这将返回-1。
     * 
      */
    int getMaxSelectionRow();

    /**
      * Returns true if the row identified by <code>row</code> is selected.
      * <p>
      *  如果选择由<code> row </code>标识的行,则返回true。
      * 
      */
    boolean isRowSelected(int row);

    /**
     * Updates this object's mapping from TreePaths to rows. This should
     * be invoked when the mapping from TreePaths to integers has changed
     * (for example, a node has been expanded).
     * <p>
     * You do not normally have to call this; JTree and its associated
     * listeners will invoke this for you. If you are implementing your own
     * view class, then you will have to invoke this.
     * <p>
     *  将此对象的映射从TreePath更新为行。当从TreePaths到整数的映射已经改变(例如,一个节点已经被展开)时,应该调用这个方法。
     * <p>
     * 你通常不必叫这个; JTree及其相关的侦听器将为您调用此方法。如果你正在实现自己的视图类,那么你将不得不调用这个。
     * 
     */
    void resetRowSelection();

    /**
     * Returns the lead selection index. That is the last index that was
     * added.
     * <p>
     *  返回潜在客户选择索引。这是添加的最后一个索引。
     * 
     */
    int getLeadSelectionRow();

    /**
     * Returns the last path that was added. This may differ from the
     * leadSelectionPath property maintained by the JTree.
     * <p>
     *  返回添加的最后一个路径。这可能不同于JTree维护的leadSelectionPath属性。
     * 
     */
    TreePath getLeadSelectionPath();

    /**
     * Adds a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * <p>
     * A PropertyChangeEvent will get fired when the selection mode
     * changes.
     *
     * <p>
     *  将PropertyChangeListener添加到侦听器列表。侦听器为所有属性注册。
     * <p>
     *  当选择模式更改时,PropertyChangeEvent将触发。
     * 
     * 
     * @param listener  the PropertyChangeListener to be added
     */
    void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Removes a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered
     * for all properties.
     *
     * <p>
     *  从侦听器列表中删除PropertyChangeListener。这将删除为所有属性注册的PropertyChangeListener。
     * 
     * 
     * @param listener  the PropertyChangeListener to be removed
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

    /**
      * Adds x to the list of listeners that are notified each time the
      * set of selected TreePaths changes.
      *
      * <p>
      *  将x添加到每当选定的TreePaths集更改时通知的侦听器列表。
      * 
      * 
      * @param x the new listener to be added
      */
    void addTreeSelectionListener(TreeSelectionListener x);

    /**
      * Removes x from the list of listeners that are notified each time
      * the set of selected TreePaths changes.
      *
      * <p>
      *  从每当选定的TreePaths集更改时通知的侦听器列表中删除x。
      * 
      * @param x the listener to remove
      */
    void removeTreeSelectionListener(TreeSelectionListener x);
}
