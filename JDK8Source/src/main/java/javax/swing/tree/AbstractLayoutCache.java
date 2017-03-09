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

package javax.swing.tree;

import javax.swing.event.TreeModelEvent;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Enumeration;

/**
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
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Scott Violet
 */

public abstract class AbstractLayoutCache implements RowMapper {
    /** Object responsible for getting the size of a node. */
    protected NodeDimensions     nodeDimensions;

    /** Model providing information. */
    protected TreeModel          treeModel;

    /** Selection model. */
    protected TreeSelectionModel treeSelectionModel;

    /**
     * True if the root node is displayed, false if its children are
     * the highest visible nodes.
     * <p>
     *  如果显示根节点,则为True,如果其子节点是最高可见节点,则为false。
     * 
     */
    protected boolean            rootVisible;

    /**
      * Height to use for each row.  If this is &lt;= 0 the renderer will be
      * used to determine the height for each row.
      * <p>
      *  每行使用的高度。如果这是&lt; = 0,则渲染器将用于确定每行的高度。
      * 
      */
    protected int                rowHeight;


    /**
     * Sets the renderer that is responsible for drawing nodes in the tree
     * and which is therefore responsible for calculating the dimensions of
     * individual nodes.
     *
     * <p>
     *  设置负责绘制树中节点的渲染器,因此负责计算单个节点的维度。
     * 
     * 
     * @param nd a <code>NodeDimensions</code> object
     */
    public void setNodeDimensions(NodeDimensions nd) {
        this.nodeDimensions = nd;
    }

    /**
     * Returns the object that renders nodes in the tree, and which is
     * responsible for calculating the dimensions of individual nodes.
     *
     * <p>
     *  返回在树中呈现节点的对象,它负责计算各个节点的维度。
     * 
     * 
     * @return the <code>NodeDimensions</code> object
     */
    public NodeDimensions getNodeDimensions() {
        return nodeDimensions;
    }

    /**
     * Sets the <code>TreeModel</code> that will provide the data.
     *
     * <p>
     *  设置将提供数据的<code> TreeModel </code>。
     * 
     * 
     * @param newModel the <code>TreeModel</code> that is to
     *          provide the data
     */
    public void setModel(TreeModel newModel) {
        treeModel = newModel;
    }

    /**
     * Returns the <code>TreeModel</code> that is providing the data.
     *
     * <p>
     *  返回提供数据的<code> TreeModel </code>。
     * 
     * 
     * @return the <code>TreeModel</code> that is providing the data
     */
    public TreeModel getModel() {
        return treeModel;
    }

    /**
     * Determines whether or not the root node from
     * the <code>TreeModel</code> is visible.
     *
     * <p>
     *  确定来自<code> TreeModel </code>的根节点是否可见。
     * 
     * 
     * @param rootVisible true if the root node of the tree is to be displayed
     * @see #rootVisible
     * @beaninfo
     *        bound: true
     *  description: Whether or not the root node
     *               from the TreeModel is visible.
     */
    public void setRootVisible(boolean rootVisible) {
        this.rootVisible = rootVisible;
    }

    /**
     * Returns true if the root node of the tree is displayed.
     *
     * <p>
     *  如果显示树的根节点,则返回true。
     * 
     * 
     * @return true if the root node of the tree is displayed
     * @see #rootVisible
     */
    public boolean isRootVisible() {
        return rootVisible;
    }

    /**
     * Sets the height of each cell.  If the specified value
     * is less than or equal to zero the current cell renderer is
     * queried for each row's height.
     *
     * <p>
     *  设置每个单元格的高度。如果指定的值小于或等于零,则查询每一行的高度的当前单元格渲染器。
     * 
     * 
     * @param rowHeight the height of each cell, in pixels
     * @beaninfo
     *        bound: true
     *  description: The height of each cell.
     */
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    /**
     * Returns the height of each row.  If the returned value is less than
     * or equal to 0 the height for each row is determined by the
     * renderer.
     * <p>
     * 返回每行的高度。如果返回值小于或等于0,每行的高度由渲染器确定。
     * 
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * Sets the <code>TreeSelectionModel</code> used to manage the
     * selection to new LSM.
     *
     * <p>
     *  将用于管理选择的<code> TreeSelectionModel </code>设置为新的LSM。
     * 
     * 
     * @param newLSM  the new <code>TreeSelectionModel</code>
     */
    public void setSelectionModel(TreeSelectionModel newLSM) {
        if(treeSelectionModel != null)
            treeSelectionModel.setRowMapper(null);
        treeSelectionModel = newLSM;
        if(treeSelectionModel != null)
            treeSelectionModel.setRowMapper(this);
    }

    /**
     * Returns the model used to maintain the selection.
     *
     * <p>
     *  返回用于维护选择的模型。
     * 
     * 
     * @return the <code>treeSelectionModel</code>
     */
    public TreeSelectionModel getSelectionModel() {
        return treeSelectionModel;
    }

    /**
     * Returns the preferred height.
     *
     * <p>
     *  返回首选高度。
     * 
     * 
     * @return the preferred height
     */
    public int getPreferredHeight() {
        // Get the height
        int           rowCount = getRowCount();

        if(rowCount > 0) {
            Rectangle     bounds = getBounds(getPathForRow(rowCount - 1),
                                             null);

            if(bounds != null)
                return bounds.y + bounds.height;
        }
        return 0;
    }

    /**
     * Returns the preferred width for the passed in region.
     * The region is defined by the path closest to
     * <code>(bounds.x, bounds.y)</code> and
     * ends at <code>bounds.height + bounds.y</code>.
     * If <code>bounds</code> is <code>null</code>,
     * the preferred width for all the nodes
     * will be returned (and this may be a VERY expensive
     * computation).
     *
     * <p>
     *  返回传入的区域的首选宽度。
     * 该区域由最接近<code>(bounds.x,bounds.y)</code>的路径定义,并以<code> bounds.height + bounds.y </code>结束。
     * 如果<code> bounds </code>是<code> null </code>,将返回所有节点的首选宽度(这可能是一个非常昂贵的计算)。
     * 
     * 
     * @param bounds the region being queried
     * @return the preferred width for the passed in region
     */
    public int getPreferredWidth(Rectangle bounds) {
        int           rowCount = getRowCount();

        if(rowCount > 0) {
            // Get the width
            TreePath      firstPath;
            int           endY;

            if(bounds == null) {
                firstPath = getPathForRow(0);
                endY = Integer.MAX_VALUE;
            }
            else {
                firstPath = getPathClosestTo(bounds.x, bounds.y);
                endY = bounds.height + bounds.y;
            }

            Enumeration   paths = getVisiblePathsFrom(firstPath);

            if(paths != null && paths.hasMoreElements()) {
                Rectangle   pBounds = getBounds((TreePath)paths.nextElement(),
                                                null);
                int         width;

                if(pBounds != null) {
                    width = pBounds.x + pBounds.width;
                    if (pBounds.y >= endY) {
                        return width;
                    }
                }
                else
                    width = 0;
                while (pBounds != null && paths.hasMoreElements()) {
                    pBounds = getBounds((TreePath)paths.nextElement(),
                                        pBounds);
                    if (pBounds != null && pBounds.y < endY) {
                        width = Math.max(width, pBounds.x + pBounds.width);
                    }
                    else {
                        pBounds = null;
                    }
                }
                return width;
            }
        }
        return 0;
    }

    //
    // Abstract methods that must be implemented to be concrete.
    //

    /**
      * Returns true if the value identified by row is currently expanded.
      * <p>
      *  如果由行标​​识的值当前已展开,则返回true。
      * 
      */
    public abstract boolean isExpanded(TreePath path);

    /**
     * Returns a rectangle giving the bounds needed to draw path.
     *
     * <p>
     *  返回一个给出绘制路径所需的边界的矩形。
     * 
     * 
     * @param path     a <code>TreePath</code> specifying a node
     * @param placeIn  a <code>Rectangle</code> object giving the
     *          available space
     * @return a <code>Rectangle</code> object specifying the space to be used
     */
    public abstract Rectangle getBounds(TreePath path, Rectangle placeIn);

    /**
      * Returns the path for passed in row.  If row is not visible
      * <code>null</code> is returned.
      *
      * <p>
      *  返回在行中传递的路径。如果行不可见,则返回<code> null </code>。
      * 
      * 
      * @param row  the row being queried
      * @return the <code>TreePath</code> for the given row
      */
    public abstract TreePath getPathForRow(int row);

    /**
      * Returns the row that the last item identified in path is visible
      * at.  Will return -1 if any of the elements in path are not
      * currently visible.
      *
      * <p>
      *  返回在路径中标识的最后一个项目所在的行在哪里可见。如果路径中的任何元素当前不可见,将返回-1。
      * 
      * 
      * @param path the <code>TreePath</code> being queried
      * @return the row where the last item in path is visible or -1
      *         if any elements in path aren't currently visible
      */
    public abstract int getRowForPath(TreePath path);

    /**
      * Returns the path to the node that is closest to x,y.  If
      * there is nothing currently visible this will return <code>null</code>,
      * otherwise it'll always return a valid path.
      * If you need to test if the
      * returned object is exactly at x, y you should get the bounds for
      * the returned path and test x, y against that.
      *
      * <p>
      *  返回最接近x,y的节点的路径。如果当前没有可见的,这将返回<code> null </code>,否则它总是返回一个有效的路径。
      * 如果你需要测试返回的对象是否正好在x,y,你应该得到返回的路径的边界,并测试x,y。
      * 
      * 
      * @param x the horizontal component of the desired location
      * @param y the vertical component of the desired location
      * @return the <code>TreePath</code> closest to the specified point
      */
    public abstract TreePath getPathClosestTo(int x, int y);

    /**
     * Returns an <code>Enumerator</code> that increments over the visible
     * paths starting at the passed in location. The ordering of the
     * enumeration is based on how the paths are displayed.
     * The first element of the returned enumeration will be path,
     * unless it isn't visible,
     * in which case <code>null</code> will be returned.
     *
     * <p>
     * 返回一个<code>枚举器</code>,它在从传入位置开始的可见路径上递增。枚举的排序基于如何显示路径。
     * 返回的枚举的第一个元素将是path,除非它不可见,在这种情况下将返回<code> null </code>。
     * 
     * 
     * @param path the starting location for the enumeration
     * @return the <code>Enumerator</code> starting at the desired location
     */
    public abstract Enumeration<TreePath> getVisiblePathsFrom(TreePath path);

    /**
     * Returns the number of visible children for row.
     *
     * <p>
     *  返回行的可见子项数。
     * 
     * 
     * @param path  the path being queried
     * @return the number of visible children for the specified path
     */
    public abstract int getVisibleChildCount(TreePath path);

    /**
     * Marks the path <code>path</code> expanded state to
     * <code>isExpanded</code>.
     *
     * <p>
     *  将路径<code> path </code>扩展状态标记为<code> isExpanded </code>。
     * 
     * 
     * @param path  the path being expanded or collapsed
     * @param isExpanded true if the path should be expanded, false otherwise
     */
    public abstract void setExpandedState(TreePath path, boolean isExpanded);

    /**
     * Returns true if the path is expanded, and visible.
     *
     * <p>
     *  如果路径已展开,则返回true,并且可见。
     * 
     * 
     * @param path  the path being queried
     * @return true if the path is expanded and visible, false otherwise
     */
    public abstract boolean getExpandedState(TreePath path);

    /**
     * Number of rows being displayed.
     *
     * <p>
     *  显示的行数。
     * 
     * 
     * @return the number of rows being displayed
     */
    public abstract int getRowCount();

    /**
     * Informs the <code>TreeState</code> that it needs to recalculate
     * all the sizes it is referencing.
     * <p>
     *  通知<code> TreeState </code>,它需要重新计算它引用的所有大小。
     * 
     */
    public abstract void invalidateSizes();

    /**
     * Instructs the <code>LayoutCache</code> that the bounds for
     * <code>path</code> are invalid, and need to be updated.
     *
     * <p>
     *  指示<code> LayoutCache </code> <code> path </code>的边界无效,需要更新。
     * 
     * 
     * @param path the path being updated
     */
    public abstract void invalidatePathBounds(TreePath path);

    //
    // TreeModelListener methods
    // AbstractTreeState does not directly become a TreeModelListener on
    // the model, it is up to some other object to forward these methods.
    //

    /**
     * <p>
     * Invoked after a node (or a set of siblings) has changed in some
     * way. The node(s) have not changed locations in the tree or
     * altered their children arrays, but other attributes have
     * changed and may affect presentation. Example: the name of a
     * file has changed, but it is in the same location in the file
     * system.</p>
     *
     * <p>e.path() returns the path the parent of the changed node(s).</p>
     *
     * <p>e.childIndices() returns the index(es) of the changed node(s).</p>
     *
     * <p>
     * <p>
     *  在一个节点(或一组兄弟节点)以某种方式更改后调用。节点没有改变树中的位置或改变它们的子数组,但是其他属性已经改变并且可能影响呈现。示例：文件的名称已更改,但它位于文件系统中的相同位置。</p>
     * 
     *  <p> e.path()返回变更节点的父节点的路径。</p>
     * 
     *  <p> e.childIndices()返回变更节点的索引。</p>
     * 
     * 
     * @param e  the <code>TreeModelEvent</code>
     */
    public abstract void treeNodesChanged(TreeModelEvent e);

    /**
     * <p>Invoked after nodes have been inserted into the tree.</p>
     *
     * <p>e.path() returns the parent of the new nodes</p>
     * <p>e.childIndices() returns the indices of the new nodes in
     * ascending order.</p>
     *
     * <p>
     *  <p>在树已插入节点后调用。</p>
     * 
     *  <p> e.path()返回新节点的父节点</p> <p> e.childIndices()以升序返回新节点的索引。</p>
     * 
     * 
     * @param e the <code>TreeModelEvent</code>
     */
    public abstract void treeNodesInserted(TreeModelEvent e);

    /**
     * <p>Invoked after nodes have been removed from the tree.  Note that
     * if a subtree is removed from the tree, this method may only be
     * invoked once for the root of the removed subtree, not once for
     * each individual set of siblings removed.</p>
     *
     * <p>e.path() returns the former parent of the deleted nodes.</p>
     *
     * <p>e.childIndices() returns the indices the nodes had before they were deleted in ascending order.</p>
     *
     * <p>
     * <p>在从树中删除节点后调用。注意,如果从树中删除子树,则该方法对于删除的子树的根可以只被调用一次,而对于去除的每个兄弟姐妹的每个集合不能被调用一次。</p>
     * 
     *  <p> e.path()返回已删除节点的前父节点。</p>
     * 
     *  <p> e.childIndices()返回节点在以升序删除之前所具有的索引。</p>
     * 
     * 
     * @param e the <code>TreeModelEvent</code>
     */
    public abstract void treeNodesRemoved(TreeModelEvent e);

    /**
     * <p>Invoked after the tree has drastically changed structure from a
     * given node down.  If the path returned by <code>e.getPath()</code>
     * is of length one and the first element does not identify the
     * current root node the first element should become the new root
     * of the tree.</p>
     *
     * <p>e.path() holds the path to the node.</p>
     * <p>e.childIndices() returns null.</p>
     *
     * <p>
     *  <p>在树已从给定节点向下大幅更改结构后调用。如果<code> e.getPath()</code>返回的路径长度为1,并且第一个元素不标识当前根节点,则第一个元素应该成为树的新根。</p>
     * 
     *  <p> e.path()包含节点的路径。</p> <p> e.childIndices()返回null。</p>
     * 
     * 
     * @param e the <code>TreeModelEvent</code>
     */
    public abstract void treeStructureChanged(TreeModelEvent e);

    //
    // RowMapper
    //

    /**
     * Returns the rows that the <code>TreePath</code> instances in
     * <code>path</code> are being displayed at.
     * This method should return an array of the same length as that passed
     * in, and if one of the <code>TreePaths</code>
     * in <code>path</code> is not valid its entry in the array should
     * be set to -1.
     *
     * <p>
     *  返回<code> path </code>中显示的<code> TreePath </code>实例的行。
     * 此方法应返回与传入的长度相同的数组,如果<code> path </code>中的<code> TreePaths </code>之一无效,则其数组中的条目应设置为 - 1。
     * 
     * 
     * @param paths the array of <code>TreePath</code>s being queried
     * @return an array of the same length that is passed in containing
     *          the rows that each corresponding where each
     *          <code>TreePath</code> is displayed; if <code>paths</code>
     *          is <code>null</code>, <code>null</code> is returned
     */
    public int[] getRowsForPaths(TreePath[] paths) {
        if(paths == null)
            return null;

        int               numPaths = paths.length;
        int[]             rows = new int[numPaths];

        for(int counter = 0; counter < numPaths; counter++)
            rows[counter] = getRowForPath(paths[counter]);
        return rows;
    }

    //
    // Local methods that subclassers may wish to use that are primarly
    // convenience methods.
    //

    /**
     * Returns, by reference in <code>placeIn</code>,
     * the size needed to represent <code>value</code>.
     * If <code>inPlace</code> is <code>null</code>, a newly created
     * <code>Rectangle</code> should be returned, otherwise the value
     * should be placed in <code>inPlace</code> and returned. This will
     * return <code>null</code> if there is no renderer.
     *
     * <p>
     *  通过<code> placeIn </code>中的引用返回表示<code> value </code>所需的大小。
     * 如果<code> inPlace </code>是<code> null </code>,则应返回一个新创建的<code> Rectangle </code>,否则该值应放在<code> inPlace
     *  </code>回。
     *  通过<code> placeIn </code>中的引用返回表示<code> value </code>所需的大小。如果没有渲染器,将返回<code> null </code>。
     * 
     * 
     * @param value the <code>value</code> to be represented
     * @param row  row being queried
     * @param depth the depth of the row
     * @param expanded true if row is expanded, false otherwise
     * @param placeIn  a <code>Rectangle</code> containing the size needed
     *          to represent <code>value</code>
     * @return a <code>Rectangle</code> containing the node dimensions,
     *          or <code>null</code> if node has no dimension
     */
    protected Rectangle getNodeDimensions(Object value, int row, int depth,
                                          boolean expanded,
                                          Rectangle placeIn) {
        NodeDimensions            nd = getNodeDimensions();

        if(nd != null) {
            return nd.getNodeDimensions(value, row, depth, expanded, placeIn);
        }
        return null;
    }

    /**
      * Returns true if the height of each row is a fixed size.
      * <p>
      *  如果每行的高度为固定大小,则返回true。
      * 
      */
    protected boolean isFixedRowHeight() {
        return (rowHeight > 0);
    }


    /**
     * Used by <code>AbstractLayoutCache</code> to determine the size
     * and x origin of a particular node.
     * <p>
     * 由<code> AbstractLayoutCache </code>使用以确定特定节点的大小和x起点。
     * 
     */
    static public abstract class NodeDimensions {
        /**
         * Returns, by reference in bounds, the size and x origin to
         * place value at. The calling method is responsible for determining
         * the Y location. If bounds is <code>null</code>, a newly created
         * <code>Rectangle</code> should be returned,
         * otherwise the value should be placed in bounds and returned.
         *
         * <p>
         *  通过引用以边界返回大小和x原点到位置值。调用方法负责确定Y位置。
         * 如果bounds是<code> null </code>,应该返回一个新创建的<code> Rectangle </code>,否则该值应该放在边界中并返回。
         * 
         * @param value the <code>value</code> to be represented
         * @param row row being queried
         * @param depth the depth of the row
         * @param expanded true if row is expanded, false otherwise
         * @param bounds  a <code>Rectangle</code> containing the size needed
         *              to represent <code>value</code>
         * @return a <code>Rectangle</code> containing the node dimensions,
         *              or <code>null</code> if node has no dimension
         */
        public abstract Rectangle getNodeDimensions(Object value, int row,
                                                    int depth,
                                                    boolean expanded,
                                                    Rectangle bounds);
    }
}
