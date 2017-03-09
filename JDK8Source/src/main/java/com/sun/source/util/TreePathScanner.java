/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.util;

import com.sun.source.tree.*;

/**
 * A TreeVisitor that visits all the child tree nodes, and provides
 * support for maintaining a path for the parent nodes.
 * To visit nodes of a particular type, just override the
 * corresponding visitorXYZ method.
 * Inside your method, call super.visitXYZ to visit descendant
 * nodes.
 *
 * <p>
 *  访问所有子树节点的TreeVisitor,并为维护父节点的路径提供支持。要访问特定类型的节点,只需覆盖相应的visitorXYZ方法。在你的方法中,调用super.visitXYZ访问后代节点。
 * 
 * 
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public class TreePathScanner<R, P> extends TreeScanner<R, P> {

    /**
     * Scan a tree from a position identified by a TreePath.
     * <p>
     *  从由TreePath标识的位置扫描树。
     * 
     */
    public R scan(TreePath path, P p) {
        this.path = path;
        try {
            return path.getLeaf().accept(this, p);
        } finally {
            this.path = null;
        }
    }

    /**
     * Scan a single node.
     * The current path is updated for the duration of the scan.
     * <p>
     *  扫描单个节点。在扫描期间更新当前路径。
     * 
     */
    @Override
    public R scan(Tree tree, P p) {
        if (tree == null)
            return null;

        TreePath prev = path;
        path = new TreePath(path, tree);
        try {
            return tree.accept(this, p);
        } finally {
            path = prev;
        }
    }

    /**
     * Get the current path for the node, as built up by the currently
     * active set of scan calls.
     * <p>
     *  获取由当前活动的扫描调用集构建的节点的当前路径。
     */
    public TreePath getCurrentPath() {
        return path;
    }

    private TreePath path;
}
