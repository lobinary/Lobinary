/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf;

import java.awt.Rectangle;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * Pluggable look and feel interface for JTree.
 *
 * <p>
 *  JTree的可插拔外观界面。
 * 
 * 
 * @author Rob Davis
 * @author Scott Violet
 */
public abstract class TreeUI extends ComponentUI
{
    /**
      * Returns the Rectangle enclosing the label portion that the
      * last item in path will be drawn into.  Will return null if
      * any component in path is currently valid.
      * <p>
      *  返回包含路径中最后一个项目将被绘制的标签部分的Rectangle。如果路径中的任何组件当前有效,则返回null。
      * 
      */
    public abstract Rectangle getPathBounds(JTree tree, TreePath path);

    /**
      * Returns the path for passed in row.  If row is not visible
      * null is returned.
      * <p>
      *  返回在行中传递的路径。如果行不可见,则返回null。
      * 
      */
    public abstract TreePath getPathForRow(JTree tree, int row);

    /**
      * Returns the row that the last item identified in path is visible
      * at.  Will return -1 if any of the elements in path are not
      * currently visible.
      * <p>
      *  返回在路径中标识的最后一个项目所在的行在哪里可见。如果路径中的任何元素当前不可见,将返回-1。
      * 
      */
    public abstract int getRowForPath(JTree tree, TreePath path);

    /**
      * Returns the number of rows that are being displayed.
      * <p>
      *  返回要显示的行数。
      * 
      */
    public abstract int getRowCount(JTree tree);

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
    public abstract TreePath getClosestPathForLocation(JTree tree, int x,
                                                       int y);

    /**
      * Returns true if the tree is being edited.  The item that is being
      * edited can be returned by getEditingPath().
      * <p>
      *  如果树正在编辑,则返回true。正在编辑的项目可以通过getEditingPath()返回。
      * 
      */
    public abstract boolean isEditing(JTree tree);

    /**
      * Stops the current editing session.  This has no effect if the
      * tree isn't being edited.  Returns true if the editor allows the
      * editing session to stop.
      * <p>
      *  停止当前编辑会话。如果树没有被编辑,这没有效果。如果编辑器允许编辑会话停止,则返回true。
      * 
      */
    public abstract boolean stopEditing(JTree tree);

    /**
      * Cancels the current editing session. This has no effect if the
      * tree isn't being edited.  Returns true if the editor allows the
      * editing session to stop.
      * <p>
      *  取消当前编辑会话。如果树没有被编辑,这没有效果。如果编辑器允许编辑会话停止,则返回true。
      * 
      */
    public abstract void cancelEditing(JTree tree);

    /**
      * Selects the last item in path and tries to edit it.  Editing will
      * fail if the CellEditor won't allow it for the selected item.
      * <p>
      *  选择路径中的最后一个项目并尝试编辑它。如果CellEditor不允许所选项目,编辑将失败。
      * 
      */
    public abstract void startEditingAtPath(JTree tree, TreePath path);

    /**
     * Returns the path to the element that is being edited.
     * <p>
     * 返回正在编辑的元素的路径。
     */
    public abstract TreePath getEditingPath(JTree tree);
}
