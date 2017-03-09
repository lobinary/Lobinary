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

package javax.swing.tree;

import javax.swing.tree.TreePath;

/**
 * Defines the requirements for an object that translates paths in
 * the tree into display rows.
 *
 * <p>
 *  定义将树中的路径转换为显示行的对象的要求。
 * 
 * 
 * @author Scott Violet
 */
public interface RowMapper
{
    /**
     * Returns the rows that the TreePath instances in <code>path</code>
     * are being displayed at. The receiver should return an array of
     * the same length as that passed in, and if one of the TreePaths
     * in <code>path</code> is not valid its entry in the array should
     * be set to -1.
     * <p>
     *  返回<code> path </code>中显示的TreePath实例的行。
     * 接收器应该返回一个与传入的数据长度相同的数组,如果<code> path </code>中的一个TreePath无效,则它在数组中的条目应该设置为-1。
     */
    int[] getRowsForPaths(TreePath[] path);
}
