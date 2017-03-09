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
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Stack;

import sun.swing.SwingUtilities2;

/**
 * NOTE: This will become more open in a future release.
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
 *  注意：这将在未来的版本中变得更加开放。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Scott Violet
 */

public class FixedHeightLayoutCache extends AbstractLayoutCache {
    /** Root node. */
    private FHTreeStateNode    root;

    /** Number of rows currently visible. */
    private int                rowCount;

    /**
     * Used in getting sizes for nodes to avoid creating a new Rectangle
     * every time a size is needed.
     * <p>
     *  用于获取节点的大小,以避免在每次需要大小时创建新的矩形。
     * 
     */
    private Rectangle          boundsBuffer;

    /**
     * Maps from TreePath to a FHTreeStateNode.
     * <p>
     *  从TreePath映射到FHTreeStateNode。
     * 
     */
    private Hashtable<TreePath, FHTreeStateNode> treePathMapping;

    /**
     * Used for getting path/row information.
     * <p>
     *  用于获取路径/行信息。
     * 
     */
    private SearchInfo         info;

    private Stack<Stack<TreePath>> tempStacks;


    public FixedHeightLayoutCache() {
        super();
        tempStacks = new Stack<Stack<TreePath>>();
        boundsBuffer = new Rectangle();
        treePathMapping = new Hashtable<TreePath, FHTreeStateNode>();
        info = new SearchInfo();
        setRowHeight(1);
    }

    /**
     * Sets the TreeModel that will provide the data.
     *
     * <p>
     *  设置将提供数据的TreeModel。
     * 
     * 
     * @param newModel the TreeModel that is to provide the data
     */
    public void setModel(TreeModel newModel) {
        super.setModel(newModel);
        rebuild(false);
    }

    /**
     * Determines whether or not the root node from
     * the TreeModel is visible.
     *
     * <p>
     *  确定来自TreeModel的根节点是否可见。
     * 
     * 
     * @param rootVisible true if the root node of the tree is to be displayed
     * @see #rootVisible
     */
    public void setRootVisible(boolean rootVisible) {
        if(isRootVisible() != rootVisible) {
            super.setRootVisible(rootVisible);
            if(root != null) {
                if(rootVisible) {
                    rowCount++;
                    root.adjustRowBy(1);
                }
                else {
                    rowCount--;
                    root.adjustRowBy(-1);
                }
                visibleNodesChanged();
            }
        }
    }

    /**
     * Sets the height of each cell. If rowHeight is less than or equal to
     * 0 this will throw an IllegalArgumentException.
     *
     * <p>
     *  设置每个单元格的高度。如果rowHeight小于或等于0,这将抛出IllegalArgumentException。
     * 
     * 
     * @param rowHeight the height of each cell, in pixels
     */
    public void setRowHeight(int rowHeight) {
        if(rowHeight <= 0)
            throw new IllegalArgumentException("FixedHeightLayoutCache only supports row heights greater than 0");
        if(getRowHeight() != rowHeight) {
            super.setRowHeight(rowHeight);
            visibleNodesChanged();
        }
    }

    /**
     * Returns the number of visible rows.
     * <p>
     *  返回可见行的数量。
     * 
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Does nothing, FixedHeightLayoutCache doesn't cache width, and that
     * is all that could change.
     * <p>
     *  什么都不做,FixedHeightLayoutCache不缓存宽度,这是所有可能改变。
     * 
     */
    public void invalidatePathBounds(TreePath path) {
    }


    /**
     * Informs the TreeState that it needs to recalculate all the sizes
     * it is referencing.
     * <p>
     *  通知TreeState它需要重新计算它引用的所有大小。
     * 
     */
    public void invalidateSizes() {
        // Nothing to do here, rowHeight still same, which is all
        // this is interested in, visible region may have changed though.
        visibleNodesChanged();
    }

    /**
      * Returns true if the value identified by row is currently expanded.
      * <p>
      *  如果由行标​​识的值当前已展开,则返回true。
      * 
      */
    public boolean isExpanded(TreePath path) {
        if(path != null) {
            FHTreeStateNode     lastNode = getNodeForPath(path, true, false);

            return (lastNode != null && lastNode.isExpanded());
        }
        return false;
    }

    /**
     * Returns a rectangle giving the bounds needed to draw path.
     *
     * <p>
     *  返回一个给出绘制路径所需的边界的矩形。
     * 
     * 
     * @param path     a TreePath specifying a node
     * @param placeIn  a Rectangle object giving the available space
     * @return a Rectangle object specifying the space to be used
     */
    public Rectangle getBounds(TreePath path, Rectangle placeIn) {
        if(path == null)
            return null;

        FHTreeStateNode      node = getNodeForPath(path, true, false);

        if(node != null)
            return getBounds(node, -1, placeIn);

        // node hasn't been created yet.
        TreePath       parentPath = path.getParentPath();

        node = getNodeForPath(parentPath, true, false);
        if (node != null && node.isExpanded()) {
            int              childIndex = treeModel.getIndexOfChild
                                 (parentPath.getLastPathComponent(),
                                  path.getLastPathComponent());

            if(childIndex != -1)
                return getBounds(node, childIndex, placeIn);
        }
        return null;
    }

    /**
      * Returns the path for passed in row.  If row is not visible
      * null is returned.
      * <p>
      *  返回在行中传递的路径。如果行不可见,则返回null。
      * 
      */
    public TreePath getPathForRow(int row) {
        if(row >= 0 && row < getRowCount()) {
            if(root.getPathForRow(row, getRowCount(), info)) {
                return info.getPath();
            }
        }
        return null;
    }

    /**
      * Returns the row that the last item identified in path is visible
      * at.  Will return -1 if any of the elements in path are not
      * currently visible.
      * <p>
      * 返回在路径中标识的最后一个项目所在的行在哪里可见。如果路径中的任何元素当前不可见,将返回-1。
      * 
      */
    public int getRowForPath(TreePath path) {
        if(path == null || root == null)
            return -1;

        FHTreeStateNode         node = getNodeForPath(path, true, false);

        if(node != null)
            return node.getRow();

        TreePath       parentPath = path.getParentPath();

        node = getNodeForPath(parentPath, true, false);
        if(node != null && node.isExpanded()) {
            return node.getRowToModelIndex(treeModel.getIndexOfChild
                                           (parentPath.getLastPathComponent(),
                                            path.getLastPathComponent()));
        }
        return -1;
    }

    /**
      * Returns the path to the node that is closest to x,y.  If
      * there is nothing currently visible this will return null, otherwise
      * it'll always return a valid path.  If you need to test if the
      * returned object is exactly at x, y you should get the bounds for
      * the returned path and test x, y against that.
      * <p>
      *  返回最接近x,y的节点的路径。如果目前没有可见的,这将返回null,否则它将总是返回一个有效的路径。如果你需要测试返回的对象是否正好在x,y,你应该得到返回的路径的边界,并测试x,y。
      * 
      */
    public TreePath getPathClosestTo(int x, int y) {
        if(getRowCount() == 0)
            return null;

        int                row = getRowContainingYLocation(y);

        return getPathForRow(row);
    }

    /**
     * Returns the number of visible children for row.
     * <p>
     *  返回行的可见子项数。
     * 
     */
    public int getVisibleChildCount(TreePath path) {
        FHTreeStateNode         node = getNodeForPath(path, true, false);

        if(node == null)
            return 0;
        return node.getTotalChildCount();
    }

    /**
     * Returns an Enumerator that increments over the visible paths
     * starting at the passed in location. The ordering of the enumeration
     * is based on how the paths are displayed.
     * <p>
     *  返回一个枚举器,在从传入位置开始的可见路径上递增。枚举的排序基于如何显示路径。
     * 
     */
    public Enumeration<TreePath> getVisiblePathsFrom(TreePath path) {
        if(path == null)
            return null;

        FHTreeStateNode         node = getNodeForPath(path, true, false);

        if(node != null) {
            return new VisibleFHTreeStateNodeEnumeration(node);
        }
        TreePath            parentPath = path.getParentPath();

        node = getNodeForPath(parentPath, true, false);
        if(node != null && node.isExpanded()) {
            return new VisibleFHTreeStateNodeEnumeration(node,
                  treeModel.getIndexOfChild(parentPath.getLastPathComponent(),
                                            path.getLastPathComponent()));
        }
        return null;
    }

    /**
     * Marks the path <code>path</code> expanded state to
     * <code>isExpanded</code>.
     * <p>
     *  将路径<code> path </code>扩展状态标记为<code> isExpanded </code>。
     * 
     */
    public void setExpandedState(TreePath path, boolean isExpanded) {
        if(isExpanded)
            ensurePathIsExpanded(path, true);
        else if(path != null) {
            TreePath              parentPath = path.getParentPath();

            // YECK! Make the parent expanded.
            if(parentPath != null) {
                FHTreeStateNode     parentNode = getNodeForPath(parentPath,
                                                                false, true);
                if(parentNode != null)
                    parentNode.makeVisible();
            }
            // And collapse the child.
            FHTreeStateNode         childNode = getNodeForPath(path, true,
                                                               false);

            if(childNode != null)
                childNode.collapse(true);
        }
    }

    /**
     * Returns true if the path is expanded, and visible.
     * <p>
     *  如果路径已展开,则返回true,并且可见。
     * 
     */
    public boolean getExpandedState(TreePath path) {
        FHTreeStateNode       node = getNodeForPath(path, true, false);

        return (node != null) ? (node.isVisible() && node.isExpanded()) :
                                 false;
    }

    //
    // TreeModelListener methods
    //

    /**
     * <p>Invoked after a node (or a set of siblings) has changed in some
     * way. The node(s) have not changed locations in the tree or
     * altered their children arrays, but other attributes have
     * changed and may affect presentation. Example: the name of a
     * file has changed, but it is in the same location in the file
     * system.</p>
     *
     * <p>e.path() returns the path the parent of the changed node(s).</p>
     *
     * <p>e.childIndices() returns the index(es) of the changed node(s).</p>
     * <p>
     *  <p>在某个节点(或一组兄弟节点)以某种方式更改后调用。节点没有改变树中的位置或改变它们的子数组,但是其他属性已经改变并且可能影响呈现。示例：文件的名称已更改,但它位于文件系统中的相同位置。
     * </p>。
     * 
     *  <p> e.path()返回变更节点的父节点的路径。</p>
     * 
     *  <p> e.childIndices()返回变更节点的索引。</p>
     * 
     */
    public void treeNodesChanged(TreeModelEvent e) {
        if(e != null) {
            int                 changedIndexs[];
            FHTreeStateNode     changedParent = getNodeForPath
                                  (SwingUtilities2.getTreePath(e, getModel()), false, false);
            int                 maxCounter;

            changedIndexs = e.getChildIndices();
            /* Only need to update the children if the node has been
            /* <p>
            /* 
               expanded once. */
            // PENDING(scott): make sure childIndexs is sorted!
            if (changedParent != null) {
                if (changedIndexs != null &&
                    (maxCounter = changedIndexs.length) > 0) {
                    Object       parentValue = changedParent.getUserObject();

                    for(int counter = 0; counter < maxCounter; counter++) {
                        FHTreeStateNode    child = changedParent.
                                 getChildAtModelIndex(changedIndexs[counter]);

                        if(child != null) {
                            child.setUserObject(treeModel.getChild(parentValue,
                                                     changedIndexs[counter]));
                        }
                    }
                    if(changedParent.isVisible() && changedParent.isExpanded())
                        visibleNodesChanged();
                }
                // Null for root indicates it changed.
                else if (changedParent == root && changedParent.isVisible() &&
                         changedParent.isExpanded()) {
                    visibleNodesChanged();
                }
            }
        }
    }

    /**
     * <p>Invoked after nodes have been inserted into the tree.</p>
     *
     * <p>e.path() returns the parent of the new nodes
     * <p>e.childIndices() returns the indices of the new nodes in
     * ascending order.
     * <p>
     *  <p>在树已插入节点后调用。</p>
     * 
     *  <p> e.path()返回新节点的父节点<p> e.childIndices()以升序返回新节点的索引。
     * 
     */
    public void treeNodesInserted(TreeModelEvent e) {
        if(e != null) {
            int                 changedIndexs[];
            FHTreeStateNode     changedParent = getNodeForPath
                                  (SwingUtilities2.getTreePath(e, getModel()), false, false);
            int                 maxCounter;

            changedIndexs = e.getChildIndices();
            /* Only need to update the children if the node has been
            /* <p>
            /* 
               expanded once. */
            // PENDING(scott): make sure childIndexs is sorted!
            if(changedParent != null && changedIndexs != null &&
               (maxCounter = changedIndexs.length) > 0) {
                boolean          isVisible =
                    (changedParent.isVisible() &&
                     changedParent.isExpanded());

                for(int counter = 0; counter < maxCounter; counter++) {
                    changedParent.childInsertedAtModelIndex
                        (changedIndexs[counter], isVisible);
                }
                if(isVisible && treeSelectionModel != null)
                    treeSelectionModel.resetRowSelection();
                if(changedParent.isVisible())
                    this.visibleNodesChanged();
            }
        }
    }

    /**
     * <p>Invoked after nodes have been removed from the tree.  Note that
     * if a subtree is removed from the tree, this method may only be
     * invoked once for the root of the removed subtree, not once for
     * each individual set of siblings removed.</p>
     *
     * <p>e.path() returns the former parent of the deleted nodes.</p>
     *
     * <p>e.childIndices() returns the indices the nodes had before they were deleted in ascending order.</p>
     * <p>
     * <p>在从树中删除节点后调用。注意,如果从树中删除子树,则该方法对于删除的子树的根可以只被调用一次,而对于去除的每个兄弟姐妹的每个集合不能被调用一次。</p>
     * 
     *  <p> e.path()返回已删除节点的前父节点。</p>
     * 
     *  <p> e.childIndices()返回节点在以升序删除之前所具有的索引。</p>
     * 
     */
    public void treeNodesRemoved(TreeModelEvent e) {
        if(e != null) {
            int                  changedIndexs[];
            int                  maxCounter;
            TreePath             parentPath = SwingUtilities2.getTreePath(e, getModel());
            FHTreeStateNode      changedParentNode = getNodeForPath
                                       (parentPath, false, false);

            changedIndexs = e.getChildIndices();
            // PENDING(scott): make sure that changedIndexs are sorted in
            // ascending order.
            if(changedParentNode != null && changedIndexs != null &&
               (maxCounter = changedIndexs.length) > 0) {
                Object[]           children = e.getChildren();
                boolean            isVisible =
                    (changedParentNode.isVisible() &&
                     changedParentNode.isExpanded());

                for(int counter = maxCounter - 1; counter >= 0; counter--) {
                    changedParentNode.removeChildAtModelIndex
                                     (changedIndexs[counter], isVisible);
                }
                if(isVisible) {
                    if(treeSelectionModel != null)
                        treeSelectionModel.resetRowSelection();
                    if (treeModel.getChildCount(changedParentNode.
                                                getUserObject()) == 0 &&
                                  changedParentNode.isLeaf()) {
                        // Node has become a leaf, collapse it.
                        changedParentNode.collapse(false);
                    }
                    visibleNodesChanged();
                }
                else if(changedParentNode.isVisible())
                    visibleNodesChanged();
            }
        }
    }

    /**
     * <p>Invoked after the tree has drastically changed structure from a
     * given node down.  If the path returned by e.getPath() is of length
     * one and the first element does not identify the current root node
     * the first element should become the new root of the tree.
     *
     * <p>e.path() holds the path to the node.</p>
     * <p>e.childIndices() returns null.</p>
     * <p>
     *  <p>在树已从给定节点向下大幅更改结构后调用。如果e.getPath()返回的路径长度为1,并且第一个元素不标识当前根节点,则第一个元素应该成为树的新根。
     * 
     *  <p> e.path()包含节点的路径。</p> <p> e.childIndices()返回null。</p>
     * 
     */
    public void treeStructureChanged(TreeModelEvent e) {
        if(e != null) {
            TreePath          changedPath = SwingUtilities2.getTreePath(e, getModel());
            FHTreeStateNode   changedNode = getNodeForPath
                                                (changedPath, false, false);

            // Check if root has changed, either to a null root, or
            // to an entirely new root.
            if (changedNode == root ||
                (changedNode == null &&
                 ((changedPath == null && treeModel != null &&
                   treeModel.getRoot() == null) ||
                  (changedPath != null && changedPath.getPathCount() <= 1)))) {
                rebuild(true);
            }
            else if(changedNode != null) {
                boolean             wasExpanded, wasVisible;
                FHTreeStateNode     parent = (FHTreeStateNode)
                                              changedNode.getParent();

                wasExpanded = changedNode.isExpanded();
                wasVisible = changedNode.isVisible();

                int index = parent.getIndex(changedNode);
                changedNode.collapse(false);
                parent.remove(index);

                if(wasVisible && wasExpanded) {
                    int row = changedNode.getRow();
                    parent.resetChildrenRowsFrom(row, index,
                                                 changedNode.getChildIndex());
                    changedNode = getNodeForPath(changedPath, false, true);
                    changedNode.expand();
                }
                if(treeSelectionModel != null && wasVisible && wasExpanded)
                    treeSelectionModel.resetRowSelection();
                if(wasVisible)
                    this.visibleNodesChanged();
            }
        }
    }


    //
    // Local methods
    //

    private void visibleNodesChanged() {
    }

    /**
     * Returns the bounds for the given node. If <code>childIndex</code>
     * is -1, the bounds of <code>parent</code> are returned, otherwise
     * the bounds of the node at <code>childIndex</code> are returned.
     * <p>
     *  返回给定节点的边界。
     * 如果<code> childIndex </code>为-1,则返回<code> parent </code>的边界,否则返回<code> childIndex </code>节点的边界。
     * 
     */
    private Rectangle getBounds(FHTreeStateNode parent, int childIndex,
                                  Rectangle placeIn) {
        boolean              expanded;
        int                  level;
        int                  row;
        Object               value;

        if(childIndex == -1) {
            // Getting bounds for parent
            row = parent.getRow();
            value = parent.getUserObject();
            expanded = parent.isExpanded();
            level = parent.getLevel();
        }
        else {
            row = parent.getRowToModelIndex(childIndex);
            value = treeModel.getChild(parent.getUserObject(), childIndex);
            expanded = false;
            level = parent.getLevel() + 1;
        }

        Rectangle      bounds = getNodeDimensions(value, row, level,
                                                  expanded, boundsBuffer);
        // No node dimensions, bail.
        if(bounds == null)
            return null;

        if(placeIn == null)
            placeIn = new Rectangle();

        placeIn.x = bounds.x;
        placeIn.height = getRowHeight();
        placeIn.y = row * placeIn.height;
        placeIn.width = bounds.width;
        return placeIn;
    }

    /**
     * Adjust the large row count of the AbstractTreeUI the receiver was
     * created with.
     * <p>
     *  调整创建接收器的AbstractTreeUI的大行数。
     * 
     */
    private void adjustRowCountBy(int changeAmount) {
        rowCount += changeAmount;
    }

    /**
     * Adds a mapping for node.
     * <p>
     *  为节点添加映射。
     * 
     */
    private void addMapping(FHTreeStateNode node) {
        treePathMapping.put(node.getTreePath(), node);
    }

    /**
     * Removes the mapping for a previously added node.
     * <p>
     *  删除先前添加的节点的映射。
     * 
     */
    private void removeMapping(FHTreeStateNode node) {
        treePathMapping.remove(node.getTreePath());
    }

    /**
     * Returns the node previously added for <code>path</code>. This may
     * return null, if you to create a node use getNodeForPath.
     * <p>
     *  返回先前为<code> path </code>添加的节点。这可能返回null,如果你创建一个节点使用getNodeForPath。
     * 
     */
    private FHTreeStateNode getMapping(TreePath path) {
        return treePathMapping.get(path);
    }

    /**
     * Sent to completely rebuild the visible tree. All nodes are collapsed.
     * <p>
     *  发送完全重建可见树。所有节点都已折叠。
     * 
     */
    private void rebuild(boolean clearSelection) {
        Object            rootUO;

        treePathMapping.clear();
        if(treeModel != null && (rootUO = treeModel.getRoot()) != null) {
            root = createNodeForValue(rootUO, 0);
            root.path = new TreePath(rootUO);
            addMapping(root);
            if(isRootVisible()) {
                rowCount = 1;
                root.row = 0;
            }
            else {
                rowCount = 0;
                root.row = -1;
            }
            root.expand();
        }
        else {
            root = null;
            rowCount = 0;
        }
        if(clearSelection && treeSelectionModel != null) {
            treeSelectionModel.clearSelection();
        }
        this.visibleNodesChanged();
    }

    /**
      * Returns the index of the row containing location.  If there
      * are no rows, -1 is returned.  If location is beyond the last
      * row index, the last row index is returned.
      * <p>
      *  返回包含位置的行的索引。如果没有行,则返回-1。如果位置超出最后一行索引,则返回最后一行索引。
      * 
      */
    private int getRowContainingYLocation(int location) {
        if(getRowCount() == 0)
            return -1;
        return Math.max(0, Math.min(getRowCount() - 1,
                                    location / getRowHeight()));
    }

    /**
     * Ensures that all the path components in path are expanded, accept
     * for the last component which will only be expanded if expandLast
     * is true.
     * Returns true if succesful in finding the path.
     * <p>
     * 确保路径中的所有路径组件都被展开,接受最后一个组件,只有在expandLast为true时才会展开。如果成功找到路径,则返回true。
     * 
     */
    private boolean ensurePathIsExpanded(TreePath aPath,
                                           boolean expandLast) {
        if(aPath != null) {
            // Make sure the last entry isn't a leaf.
            if(treeModel.isLeaf(aPath.getLastPathComponent())) {
                aPath = aPath.getParentPath();
                expandLast = true;
            }
            if(aPath != null) {
                FHTreeStateNode     lastNode = getNodeForPath(aPath, false,
                                                              true);

                if(lastNode != null) {
                    lastNode.makeVisible();
                    if(expandLast)
                        lastNode.expand();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates and returns an instance of FHTreeStateNode.
     * <p>
     *  创建并返回FHTreeStateNode的实例。
     * 
     */
    private FHTreeStateNode createNodeForValue(Object value,int childIndex) {
        return new FHTreeStateNode(value, childIndex, -1);
    }

    /**
     * Messages getTreeNodeForPage(path, onlyIfVisible, shouldCreate,
     * path.length) as long as path is non-null and the length is {@literal >} 0.
     * Otherwise returns null.
     * <p>
     *  消息getTreeNodeForPage(path,onlyIfVisible,shouldCreate,path.length),只要路径为非空,长度为{@literal>} 0。
     * 否则返回null。
     * 
     */
    private FHTreeStateNode getNodeForPath(TreePath path,
                                             boolean onlyIfVisible,
                                             boolean shouldCreate) {
        if(path != null) {
            FHTreeStateNode      node;

            node = getMapping(path);
            if(node != null) {
                if(onlyIfVisible && !node.isVisible())
                    return null;
                return node;
            }
            if(onlyIfVisible)
                return null;

            // Check all the parent paths, until a match is found.
            Stack<TreePath> paths;

            if(tempStacks.size() == 0) {
                paths = new Stack<TreePath>();
            }
            else {
                paths = tempStacks.pop();
            }

            try {
                paths.push(path);
                path = path.getParentPath();
                node = null;
                while(path != null) {
                    node = getMapping(path);
                    if(node != null) {
                        // Found a match, create entries for all paths in
                        // paths.
                        while(node != null && paths.size() > 0) {
                            path = paths.pop();
                            node = node.createChildFor(path.
                                                       getLastPathComponent());
                        }
                        return node;
                    }
                    paths.push(path);
                    path = path.getParentPath();
                }
            }
            finally {
                paths.removeAllElements();
                tempStacks.push(paths);
            }
            // If we get here it means they share a different root!
            return null;
        }
        return null;
    }

    /**
     * FHTreeStateNode is used to track what has been expanded.
     * FHTreeStateNode differs from VariableHeightTreeState.TreeStateNode
     * in that it is highly model intensive. That is almost all queries to a
     * FHTreeStateNode result in the TreeModel being queried. And it
     * obviously does not support variable sized row heights.
     * <p>
     *  FHTreeStateNode用于跟踪已扩展的内容。 FHTreeStateNode不同于VariableHeightTreeState.TreeStateNode,因为它是高度模型密集型。
     * 这几乎是对正在查询的TreeModel中的FHTreeStateNode结果的所有查询。它显然不支持可变大小的行高。
     * 
     */
    private class FHTreeStateNode extends DefaultMutableTreeNode {
        /** Is this node expanded? */
        protected boolean         isExpanded;

        /** Index of this node from the model. */
        protected int             childIndex;

        /** Child count of the receiver. */
        protected int             childCount;

        /** Row of the receiver. This is only valid if the row is expanded.
        /* <p>
         */
        protected int             row;

        /** Path of this node. */
        protected TreePath        path;


        public FHTreeStateNode(Object userObject, int childIndex, int row) {
            super(userObject);
            this.childIndex = childIndex;
            this.row = row;
        }

        //
        // Overriden DefaultMutableTreeNode methods
        //

        /**
         * Messaged when this node is added somewhere, resets the path
         * and adds a mapping from path to this node.
         * <p>
         *  在将此节点添加到某处时发生消息,重置路径并添加从路径到此节点的映射。
         * 
         */
        public void setParent(MutableTreeNode parent) {
            super.setParent(parent);
            if(parent != null) {
                path = ((FHTreeStateNode)parent).getTreePath().
                            pathByAddingChild(getUserObject());
                addMapping(this);
            }
        }

        /**
         * Messaged when this node is removed from its parent, this messages
         * <code>removedFromMapping</code> to remove all the children.
         * <p>
         *  当此节点从其父级删除时,此消息会消失,此消息<code> removedFromMapping </code>可删除所有子级。
         * 
         */
        public void remove(int childIndex) {
            FHTreeStateNode     node = (FHTreeStateNode)getChildAt(childIndex);

            node.removeFromMapping();
            super.remove(childIndex);
        }

        /**
         * Messaged to set the user object. This resets the path.
         * <p>
         *  Messaged设置用户对象。这将重置路径。
         * 
         */
        public void setUserObject(Object o) {
            super.setUserObject(o);
            if(path != null) {
                FHTreeStateNode      parent = (FHTreeStateNode)getParent();

                if(parent != null)
                    resetChildrenPaths(parent.getTreePath());
                else
                    resetChildrenPaths(null);
            }
        }

        //
        //

        /**
         * Returns the index of the receiver in the model.
         * <p>
         *  返回模型中接收器的索引。
         * 
         */
        public int getChildIndex() {
            return childIndex;
        }

        /**
         * Returns the <code>TreePath</code> of the receiver.
         * <p>
         *  返回接收器的<code> TreePath </code>。
         * 
         */
        public TreePath getTreePath() {
            return path;
        }

        /**
         * Returns the child for the passed in model index, this will
         * return <code>null</code> if the child for <code>index</code>
         * has not yet been created (expanded).
         * <p>
         *  返回传递给模型索引的子元素,如果<code> index </code>的子元素尚未创建(展开),则返回<code> null </code>。
         * 
         */
        public FHTreeStateNode getChildAtModelIndex(int index) {
            // PENDING: Make this a binary search!
            for(int counter = getChildCount() - 1; counter >= 0; counter--)
                if(((FHTreeStateNode)getChildAt(counter)).childIndex == index)
                    return (FHTreeStateNode)getChildAt(counter);
            return null;
        }

        /**
         * Returns true if this node is visible. This is determined by
         * asking all the parents if they are expanded.
         * <p>
         *  如果此节点可见,则返回true。这是通过询问所有的父母如果他们被扩展确定。
         * 
         */
        public boolean isVisible() {
            FHTreeStateNode         parent = (FHTreeStateNode)getParent();

            if(parent == null)
                return true;
            return (parent.isExpanded() && parent.isVisible());
        }

        /**
         * Returns the row of the receiver.
         * <p>
         *  返回接收器的行。
         * 
         */
        public int getRow() {
            return row;
        }

        /**
         * Returns the row of the child with a model index of
         * <code>index</code>.
         * <p>
         *  返回模型索引为<code> index </code>的子元素的行。
         * 
         */
        public int getRowToModelIndex(int index) {
            FHTreeStateNode      child;
            int                  lastRow = getRow() + 1;
            int                  retValue = lastRow;

            // This too could be a binary search!
            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                child = (FHTreeStateNode)getChildAt(counter);
                if(child.childIndex >= index) {
                    if(child.childIndex == index)
                        return child.row;
                    if(counter == 0)
                        return getRow() + 1 + index;
                    return child.row - (child.childIndex - index);
                }
            }
            // YECK!
            return getRow() + 1 + getTotalChildCount() -
                             (childCount - index);
        }

        /**
         * Returns the number of children in the receiver by descending all
         * expanded nodes and messaging them with getTotalChildCount.
         * <p>
         * 通过递减所有扩展节点并通过getTotalChildCount将它们发送消息来返回接收器中的子节点数。
         * 
         */
        public int getTotalChildCount() {
            if(isExpanded()) {
                FHTreeStateNode      parent = (FHTreeStateNode)getParent();
                int                  pIndex;

                if(parent != null && (pIndex = parent.getIndex(this)) + 1 <
                   parent.getChildCount()) {
                    // This node has a created sibling, to calc total
                    // child count directly from that!
                    FHTreeStateNode  nextSibling = (FHTreeStateNode)parent.
                                           getChildAt(pIndex + 1);

                    return nextSibling.row - row -
                           (nextSibling.childIndex - childIndex);
                }
                else {
                    int retCount = childCount;

                    for(int counter = getChildCount() - 1; counter >= 0;
                        counter--) {
                        retCount += ((FHTreeStateNode)getChildAt(counter))
                                                  .getTotalChildCount();
                    }
                    return retCount;
                }
            }
            return 0;
        }

        /**
         * Returns true if this node is expanded.
         * <p>
         *  如果此节点已展开,则返回true。
         * 
         */
        public boolean isExpanded() {
            return isExpanded;
        }

        /**
         * The highest visible nodes have a depth of 0.
         * <p>
         *  最高可见节点的深度为0。
         * 
         */
        public int getVisibleLevel() {
            if (isRootVisible()) {
                return getLevel();
            } else {
                return getLevel() - 1;
            }
        }

        /**
         * Recreates the receivers path, and all its children's paths.
         * <p>
         *  重新创建接收器路径及其所有子路径。
         * 
         */
        protected void resetChildrenPaths(TreePath parentPath) {
            removeMapping(this);
            if(parentPath == null)
                path = new TreePath(getUserObject());
            else
                path = parentPath.pathByAddingChild(getUserObject());
            addMapping(this);
            for(int counter = getChildCount() - 1; counter >= 0; counter--)
                ((FHTreeStateNode)getChildAt(counter)).
                               resetChildrenPaths(path);
        }

        /**
         * Removes the receiver, and all its children, from the mapping
         * table.
         * <p>
         *  从映射表中删除接收方及其所有子项。
         * 
         */
        protected void removeFromMapping() {
            if(path != null) {
                removeMapping(this);
                for(int counter = getChildCount() - 1; counter >= 0; counter--)
                    ((FHTreeStateNode)getChildAt(counter)).removeFromMapping();
            }
        }

        /**
         * Creates a new node to represent <code>userObject</code>.
         * This does NOT check to ensure there isn't already a child node
         * to manage <code>userObject</code>.
         * <p>
         *  创建一个新节点来表示<code> userObject </code>。这不检查以确保还没有一个子节点来管理<code> userObject </code>。
         * 
         */
        protected FHTreeStateNode createChildFor(Object userObject) {
            int      newChildIndex = treeModel.getIndexOfChild
                                     (getUserObject(), userObject);

            if(newChildIndex < 0)
                return null;

            FHTreeStateNode     aNode;
            FHTreeStateNode     child = createNodeForValue(userObject,
                                                           newChildIndex);
            int                 childRow;

            if(isVisible()) {
                childRow = getRowToModelIndex(newChildIndex);
            }
            else {
                childRow = -1;
            }
            child.row = childRow;
            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                aNode = (FHTreeStateNode)getChildAt(counter);
                if(aNode.childIndex > newChildIndex) {
                    insert(child, counter);
                    return child;
                }
            }
            add(child);
            return child;
        }

        /**
         * Adjusts the receiver, and all its children rows by
         * <code>amount</code>.
         * <p>
         *  通过<code> amount </code>调整接收器及其所有子行。
         * 
         */
        protected void adjustRowBy(int amount) {
            row += amount;
            if(isExpanded) {
                for(int counter = getChildCount() - 1; counter >= 0;
                    counter--)
                    ((FHTreeStateNode)getChildAt(counter)).adjustRowBy(amount);
            }
        }

        /**
         * Adjusts this node, its child, and its parent starting at
         * an index of <code>index</code> index is the index of the child
         * to start adjusting from, which is not necessarily the model
         * index.
         * <p>
         *  从<code> index </code> index的索引开始调整此节点及其子节点及其父节点是子节点开始调整的索引,它不一定是模型索引。
         * 
         */
        protected void adjustRowBy(int amount, int startIndex) {
            // Could check isVisible, but probably isn't worth it.
            if(isExpanded) {
                // children following startIndex.
                for(int counter = getChildCount() - 1; counter >= startIndex;
                    counter--)
                    ((FHTreeStateNode)getChildAt(counter)).adjustRowBy(amount);
            }
            // Parent
            FHTreeStateNode        parent = (FHTreeStateNode)getParent();

            if(parent != null) {
                parent.adjustRowBy(amount, parent.getIndex(this) + 1);
            }
        }

        /**
         * Messaged when the node has expanded. This updates all of
         * the receivers children rows, as well as the total row count.
         * <p>
         *  节点扩展时消息。这会更新所有接收器子行,以及总行数。
         * 
         */
        protected void didExpand() {
            int               nextRow = setRowAndChildren(row);
            FHTreeStateNode   parent = (FHTreeStateNode)getParent();
            int               childRowCount = nextRow - row - 1;

            if(parent != null) {
                parent.adjustRowBy(childRowCount, parent.getIndex(this) + 1);
            }
            adjustRowCountBy(childRowCount);
        }

        /**
         * Sets the receivers row to <code>nextRow</code> and recursively
         * updates all the children of the receivers rows. The index the
         * next row is to be placed as is returned.
         * <p>
         *  将接收器行设置为<code> nextRow </code>,并递归更新接收器行的所有子节点。索引下一行将被放置为返回。
         * 
         */
        protected int setRowAndChildren(int nextRow) {
            row = nextRow;

            if(!isExpanded())
                return row + 1;

            int              lastRow = row + 1;
            int              lastModelIndex = 0;
            FHTreeStateNode  child;
            int              maxCounter = getChildCount();

            for(int counter = 0; counter < maxCounter; counter++) {
                child = (FHTreeStateNode)getChildAt(counter);
                lastRow += (child.childIndex - lastModelIndex);
                lastModelIndex = child.childIndex + 1;
                if(child.isExpanded) {
                    lastRow = child.setRowAndChildren(lastRow);
                }
                else {
                    child.row = lastRow++;
                }
            }
            return lastRow + childCount - lastModelIndex;
        }

        /**
         * Resets the receivers children's rows. Starting with the child
         * at <code>childIndex</code> (and <code>modelIndex</code>) to
         * <code>newRow</code>. This uses <code>setRowAndChildren</code>
         * to recursively descend children, and uses
         * <code>resetRowSelection</code> to ascend parents.
         * <p>
         *  重置接收器的子行。从<code> childIndex </code>(和<code> modelIndex </code>)到<code> newRow </code>的子代开始。
         * 这使用<code> setRowAndChildren </code>递归下降子级,并使用<code> resetRowSelection </code>来升级父级。
         * 
         */
        // This can be rather expensive, but is needed for the collapse
        // case this is resulting from a remove (although I could fix
        // that by having instances of FHTreeStateNode hold a ref to
        // the number of children). I prefer this though, making determing
        // the row of a particular node fast is very nice!
        protected void resetChildrenRowsFrom(int newRow, int childIndex,
                                            int modelIndex) {
            int              lastRow = newRow;
            int              lastModelIndex = modelIndex;
            FHTreeStateNode  node;
            int              maxCounter = getChildCount();

            for(int counter = childIndex; counter < maxCounter; counter++) {
                node = (FHTreeStateNode)getChildAt(counter);
                lastRow += (node.childIndex - lastModelIndex);
                lastModelIndex = node.childIndex + 1;
                if(node.isExpanded) {
                    lastRow = node.setRowAndChildren(lastRow);
                }
                else {
                    node.row = lastRow++;
                }
            }
            lastRow += childCount - lastModelIndex;
            node = (FHTreeStateNode)getParent();
            if(node != null) {
                node.resetChildrenRowsFrom(lastRow, node.getIndex(this) + 1,
                                           this.childIndex + 1);
            }
            else { // This is the root, reset total ROWCOUNT!
                rowCount = lastRow;
            }
        }

        /**
         * Makes the receiver visible, but invoking
         * <code>expandParentAndReceiver</code> on the superclass.
         * <p>
         *  使接收者可见,但调用超类上的<code> expandParentAndReceiver </code>。
         * 
         */
        protected void makeVisible() {
            FHTreeStateNode       parent = (FHTreeStateNode)getParent();

            if(parent != null)
                parent.expandParentAndReceiver();
        }

        /**
         * Invokes <code>expandParentAndReceiver</code> on the parent,
         * and expands the receiver.
         * <p>
         * 在父级上调用<code> expandParentAndReceiver </code>,然后展开接收者。
         * 
         */
        protected void expandParentAndReceiver() {
            FHTreeStateNode       parent = (FHTreeStateNode)getParent();

            if(parent != null)
                parent.expandParentAndReceiver();
            expand();
        }

        /**
         * Expands the receiver.
         * <p>
         *  扩展接收器。
         * 
         */
        protected void expand() {
            if(!isExpanded && !isLeaf()) {
                boolean            visible = isVisible();

                isExpanded = true;
                childCount = treeModel.getChildCount(getUserObject());

                if(visible) {
                    didExpand();
                }

                // Update the selection model.
                if(visible && treeSelectionModel != null) {
                    treeSelectionModel.resetRowSelection();
                }
            }
        }

        /**
         * Collapses the receiver. If <code>adjustRows</code> is true,
         * the rows of nodes after the receiver are adjusted.
         * <p>
         *  折叠接收器。如果<code> adjustRows </code>为true,则调整接收器后的节点行。
         * 
         */
        protected void collapse(boolean adjustRows) {
            if(isExpanded) {
                if(isVisible() && adjustRows) {
                    int              childCount = getTotalChildCount();

                    isExpanded = false;
                    adjustRowCountBy(-childCount);
                    // We can do this because adjustRowBy won't descend
                    // the children.
                    adjustRowBy(-childCount, 0);
                }
                else
                    isExpanded = false;

                if(adjustRows && isVisible() && treeSelectionModel != null)
                    treeSelectionModel.resetRowSelection();
            }
        }

        /**
         * Returns true if the receiver is a leaf.
         * <p>
         *  如果接收器是叶子,则返回true。
         * 
         */
        public boolean isLeaf() {
            TreeModel model = getModel();

            return (model != null) ? model.isLeaf(this.getUserObject()) :
                   true;
        }

        /**
         * Adds newChild to this nodes children at the appropriate location.
         * The location is determined from the childIndex of newChild.
         * <p>
         *  将newChild添加到此节点中适当位置的子节点。位置是从newChild的childIndex确定的。
         * 
         */
        protected void addNode(FHTreeStateNode newChild) {
            boolean         added = false;
            int             childIndex = newChild.getChildIndex();

            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                if(((FHTreeStateNode)getChildAt(counter)).getChildIndex() >
                   childIndex) {
                    added = true;
                    insert(newChild, counter);
                    counter = maxCounter;
                }
            }
            if(!added)
                add(newChild);
        }

        /**
         * Removes the child at <code>modelIndex</code>.
         * <code>isChildVisible</code> should be true if the receiver
         * is visible and expanded.
         * <p>
         *  在<code> modelIndex </code>中删除子项。 <code> isChildVisible </code>应该为true,如果接收器是可见的和展开。
         * 
         */
        protected void removeChildAtModelIndex(int modelIndex,
                                               boolean isChildVisible) {
            FHTreeStateNode     childNode = getChildAtModelIndex(modelIndex);

            if(childNode != null) {
                int          row = childNode.getRow();
                int          index = getIndex(childNode);

                childNode.collapse(false);
                remove(index);
                adjustChildIndexs(index, -1);
                childCount--;
                if(isChildVisible) {
                    // Adjust the rows.
                    resetChildrenRowsFrom(row, index, modelIndex);
                }
            }
            else {
                int                  maxCounter = getChildCount();
                FHTreeStateNode      aChild;

                for(int counter = 0; counter < maxCounter; counter++) {
                    aChild = (FHTreeStateNode)getChildAt(counter);
                    if(aChild.childIndex >= modelIndex) {
                        if(isChildVisible) {
                            adjustRowBy(-1, counter);
                            adjustRowCountBy(-1);
                        }
                        // Since matched and children are always sorted by
                        // index, no need to continue testing with the
                        // above.
                        for(; counter < maxCounter; counter++)
                            ((FHTreeStateNode)getChildAt(counter)).
                                              childIndex--;
                        childCount--;
                        return;
                    }
                }
                // No children to adjust, but it was a child, so we still need
                // to adjust nodes after this one.
                if(isChildVisible) {
                    adjustRowBy(-1, maxCounter);
                    adjustRowCountBy(-1);
                }
                childCount--;
            }
        }

        /**
         * Adjusts the child indexs of the receivers children by
         * <code>amount</code>, starting at <code>index</code>.
         * <p>
         *  从<code> index </code>开始,通过<code> amount </code>调整接收器子代的子索引。
         * 
         */
        protected void adjustChildIndexs(int index, int amount) {
            for(int counter = index, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                ((FHTreeStateNode)getChildAt(counter)).childIndex += amount;
            }
        }

        /**
         * Messaged when a child has been inserted at index. For all the
         * children that have a childIndex &ge; index their index is incremented
         * by one.
         * <p>
         *  在索引处插入子代时发生消息。对于所有有childIndex&ge的孩子;索引,它们的索引增加1。
         * 
         */
        protected void childInsertedAtModelIndex(int index,
                                               boolean isExpandedAndVisible) {
            FHTreeStateNode                aChild;
            int                            maxCounter = getChildCount();

            for(int counter = 0; counter < maxCounter; counter++) {
                aChild = (FHTreeStateNode)getChildAt(counter);
                if(aChild.childIndex >= index) {
                    if(isExpandedAndVisible) {
                        adjustRowBy(1, counter);
                        adjustRowCountBy(1);
                    }
                    /* Since matched and children are always sorted by
                    /* <p>
                    /* 
                       index, no need to continue testing with the above. */
                    for(; counter < maxCounter; counter++)
                        ((FHTreeStateNode)getChildAt(counter)).childIndex++;
                    childCount++;
                    return;
                }
            }
            // No children to adjust, but it was a child, so we still need
            // to adjust nodes after this one.
            if(isExpandedAndVisible) {
                adjustRowBy(1, maxCounter);
                adjustRowCountBy(1);
            }
            childCount++;
        }

        /**
         * Returns true if there is a row for <code>row</code>.
         * <code>nextRow</code> gives the bounds of the receiver.
         * Information about the found row is returned in <code>info</code>.
         * This should be invoked on root with <code>nextRow</code> set
         * to <code>getRowCount</code>().
         * <p>
         *  如果<code> row </code>中有一行,则返回true。 <code> nextRow </code>给出接收器的边界。有关找到的行的信息在<code> info </code>中返回。
         * 这应该在根上调用<code> nextRow </code>设置为<code> getRowCount </code>()。
         * 
         */
        protected boolean getPathForRow(int row, int nextRow,
                                        SearchInfo info) {
            if(this.row == row) {
                info.node = this;
                info.isNodeParentNode = false;
                info.childIndex = childIndex;
                return true;
            }

            FHTreeStateNode            child;
            FHTreeStateNode            lastChild = null;

            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                child = (FHTreeStateNode)getChildAt(counter);
                if(child.row > row) {
                    if(counter == 0) {
                        // No node exists for it, and is first.
                        info.node = this;
                        info.isNodeParentNode = true;
                        info.childIndex = row - this.row - 1;
                        return true;
                    }
                    else {
                        // May have been in last child's bounds.
                        int          lastChildEndRow = 1 + child.row -
                                     (child.childIndex - lastChild.childIndex);

                        if(row < lastChildEndRow) {
                            return lastChild.getPathForRow(row,
                                                       lastChildEndRow, info);
                        }
                        // Between last child and child, but not in last child
                        info.node = this;
                        info.isNodeParentNode = true;
                        info.childIndex = row - lastChildEndRow +
                                                lastChild.childIndex + 1;
                        return true;
                    }
                }
                lastChild = child;
            }

            // Not in children, but we should have it, offset from
            // nextRow.
            if(lastChild != null) {
                int        lastChildEndRow = nextRow -
                                  (childCount - lastChild.childIndex) + 1;

                if(row < lastChildEndRow) {
                    return lastChild.getPathForRow(row, lastChildEndRow, info);
                }
                // Between last child and child, but not in last child
                info.node = this;
                info.isNodeParentNode = true;
                info.childIndex = row - lastChildEndRow +
                                             lastChild.childIndex + 1;
                return true;
            }
            else {
                // No children.
                int         retChildIndex = row - this.row - 1;

                if(retChildIndex >= childCount) {
                    return false;
                }
                info.node = this;
                info.isNodeParentNode = true;
                info.childIndex = retChildIndex;
                return true;
            }
        }

        /**
         * Asks all the children of the receiver for their totalChildCount
         * and returns this value (plus stopIndex).
         * <p>
         *  询问接收器的所有子节点的totalChildCount,并返回这个值(加上stopIndex)。
         * 
         */
        protected int getCountTo(int stopIndex) {
            FHTreeStateNode    aChild;
            int                retCount = stopIndex + 1;

            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                aChild = (FHTreeStateNode)getChildAt(counter);
                if(aChild.childIndex >= stopIndex)
                    counter = maxCounter;
                else
                    retCount += aChild.getTotalChildCount();
            }
            if(parent != null)
                return retCount + ((FHTreeStateNode)getParent())
                                   .getCountTo(childIndex);
            if(!isRootVisible())
                return (retCount - 1);
            return retCount;
        }

        /**
         * Returns the number of children that are expanded to
         * <code>stopIndex</code>. This does not include the number
         * of children that the child at <code>stopIndex</code> might
         * have.
         * <p>
         *  返回展开到<code> stopIndex </code>的子级数。这不包括<code> stopIndex </code>的子级可能具有的子级数。
         * 
         */
        protected int getNumExpandedChildrenTo(int stopIndex) {
            FHTreeStateNode    aChild;
            int                retCount = stopIndex;

            for(int counter = 0, maxCounter = getChildCount();
                counter < maxCounter; counter++) {
                aChild = (FHTreeStateNode)getChildAt(counter);
                if(aChild.childIndex >= stopIndex)
                    return retCount;
                else {
                    retCount += aChild.getTotalChildCount();
                }
            }
            return retCount;
        }

        /**
         * Messaged when this node either expands or collapses.
         * <p>
         *  此节点展开或折叠时消失。
         * 
         */
        protected void didAdjustTree() {
        }

    } // FixedHeightLayoutCache.FHTreeStateNode


    /**
     * Used as a placeholder when getting the path in FHTreeStateNodes.
     * <p>
     *  在FHTreeStateNodes中获取路径时用作占位符。
     * 
     */
    private class SearchInfo {
        protected FHTreeStateNode   node;
        protected boolean           isNodeParentNode;
        protected int               childIndex;

        protected TreePath getPath() {
            if(node == null)
                return null;

            if(isNodeParentNode)
                return node.getTreePath().pathByAddingChild(treeModel.getChild
                                            (node.getUserObject(),
                                             childIndex));
            return node.path;
        }
    } // FixedHeightLayoutCache.SearchInfo


    /**
     * An enumerator to iterate through visible nodes.
     * <p>
     *  枚举器,用于遍历可见节点。
     * 
     */
    // This is very similar to
    // VariableHeightTreeState.VisibleTreeStateNodeEnumeration
    private class VisibleFHTreeStateNodeEnumeration
        implements Enumeration<TreePath>
    {
        /** Parent thats children are being enumerated. */
        protected FHTreeStateNode     parent;
        /** Index of next child. An index of -1 signifies parent should be
        /* <p>
        /* 
         * visibled next. */
        protected int                 nextIndex;
        /** Number of children in parent. */
        protected int                 childCount;

        protected VisibleFHTreeStateNodeEnumeration(FHTreeStateNode node) {
            this(node, -1);
        }

        protected VisibleFHTreeStateNodeEnumeration(FHTreeStateNode parent,
                                                    int startIndex) {
            this.parent = parent;
            this.nextIndex = startIndex;
            this.childCount = treeModel.getChildCount(this.parent.
                                                      getUserObject());
        }

        /**
        /* <p>
        /* 
         * @return true if more visible nodes.
         */
        public boolean hasMoreElements() {
            return (parent != null);
        }

        /**
        /* <p>
        /* 
         * @return next visible TreePath.
         */
        public TreePath nextElement() {
            if(!hasMoreElements())
                throw new NoSuchElementException("No more visible paths");

            TreePath                retObject;

            if(nextIndex == -1)
                retObject = parent.getTreePath();
            else {
                FHTreeStateNode  node = parent.getChildAtModelIndex(nextIndex);

                if(node == null)
                    retObject = parent.getTreePath().pathByAddingChild
                                  (treeModel.getChild(parent.getUserObject(),
                                                      nextIndex));
                else
                    retObject = node.getTreePath();
            }
            updateNextObject();
            return retObject;
        }

        /**
         * Determines the next object by invoking <code>updateNextIndex</code>
         * and if not succesful <code>findNextValidParent</code>.
         * <p>
         * 通过调用<code> updateNextIndex </code>确定下一个对象,如果不成功<code> findNextValidParent </code>。
         * 
         */
        protected void updateNextObject() {
            if(!updateNextIndex()) {
                findNextValidParent();
            }
        }

        /**
         * Finds the next valid parent, this should be called when nextIndex
         * is beyond the number of children of the current parent.
         * <p>
         *  查找下一个有效的父对象,当nextIndex超出当前父对象的子对象数时,应调用此对象。
         * 
         */
        protected boolean findNextValidParent() {
            if(parent == root) {
                // mark as invalid!
                parent = null;
                return false;
            }
            while(parent != null) {
                FHTreeStateNode      newParent = (FHTreeStateNode)parent.
                                                  getParent();

                if(newParent != null) {
                    nextIndex = parent.childIndex;
                    parent = newParent;
                    childCount = treeModel.getChildCount
                                            (parent.getUserObject());
                    if(updateNextIndex())
                        return true;
                }
                else
                    parent = null;
            }
            return false;
        }

        /**
         * Updates <code>nextIndex</code> returning false if it is beyond
         * the number of children of parent.
         * <p>
         *  更新<code> nextIndex </code>如果超出父级的子级数,则返回false。
         */
        protected boolean updateNextIndex() {
            // nextIndex == -1 identifies receiver, make sure is expanded
            // before descend.
            if(nextIndex == -1 && !parent.isExpanded()) {
                return false;
            }

            // Check that it can have kids
            if(childCount == 0) {
                return false;
            }
            // Make sure next index not beyond child count.
            else if(++nextIndex >= childCount) {
                return false;
            }

            FHTreeStateNode    child = parent.getChildAtModelIndex(nextIndex);

            if(child != null && child.isExpanded()) {
                parent = child;
                nextIndex = -1;
                childCount = treeModel.getChildCount(child.getUserObject());
            }
            return true;
        }
    } // FixedHeightLayoutCache.VisibleFHTreeStateNodeEnumeration
}
