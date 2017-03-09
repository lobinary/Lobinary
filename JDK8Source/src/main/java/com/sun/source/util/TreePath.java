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

import java.util.Iterator;

import com.sun.source.tree.*;

/**
 * A path of tree nodes, typically used to represent the sequence of ancestor
 * nodes of a tree node up to the top level CompilationUnitTree node.
 *
 * <p>
 *  树节点的路径,通常用于表示树节点的祖先节点到顶层CompilationUnitTree节点的序列。
 * 
 * 
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public class TreePath implements Iterable<Tree> {
    /**
     * Gets a tree path for a tree node within a compilation unit.
     * <p>
     *  获取编译单元中树节点的树路径。
     * 
     * 
     * @return null if the node is not found
     */
    public static TreePath getPath(CompilationUnitTree unit, Tree target) {
        return getPath(new TreePath(unit), target);
    }

    /**
     * Gets a tree path for a tree node within a subtree identified by a TreePath object.
     * <p>
     *  获取由TreePath对象标识的子树内的树节点的树路径。
     * 
     * 
     * @return null if the node is not found
     */
    public static TreePath getPath(TreePath path, Tree target) {
        path.getClass();
        target.getClass();

        class Result extends Error {
            static final long serialVersionUID = -5942088234594905625L;
            TreePath path;
            Result(TreePath path) {
                this.path = path;
            }
        }

        class PathFinder extends TreePathScanner<TreePath,Tree> {
            public TreePath scan(Tree tree, Tree target) {
                if (tree == target) {
                    throw new Result(new TreePath(getCurrentPath(), target));
                }
                return super.scan(tree, target);
            }
        }

        if (path.getLeaf() == target) {
            return path;
        }

        try {
            new PathFinder().scan(path, target);
        } catch (Result result) {
            return result.path;
        }
        return null;
    }

    /**
     * Creates a TreePath for a root node.
     * <p>
     *  为根节点创建TreePath。
     * 
     */
    public TreePath(CompilationUnitTree t) {
        this(null, t);
    }

    /**
     * Creates a TreePath for a child node.
     * <p>
     *  为子节点创建TreePath。
     * 
     */
    public TreePath(TreePath p, Tree t) {
        if (t.getKind() == Tree.Kind.COMPILATION_UNIT) {
            compilationUnit = (CompilationUnitTree) t;
            parent = null;
        }
        else {
            compilationUnit = p.compilationUnit;
            parent = p;
        }
        leaf = t;
    }
    /**
     * Get the compilation unit associated with this path.
     * <p>
     *  获取与此路径关联的编译单元。
     * 
     */
    public CompilationUnitTree getCompilationUnit() {
        return compilationUnit;
    }

    /**
     * Get the leaf node for this path.
     * <p>
     *  获取此路径的叶节点。
     * 
     */
    public Tree getLeaf() {
        return leaf;
    }

    /**
     * Get the path for the enclosing node, or null if there is no enclosing node.
     * <p>
     *  获取封闭节点的路径,如果没有封闭节点,则返回null。
     * 
     */
    public TreePath getParentPath() {
        return parent;
    }

    /**
     *  Iterates from leaves to root.
     * <p>
     *  从叶子到根。
     */
    @Override
    public Iterator<Tree> iterator() {
        return new Iterator<Tree>() {
            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public Tree next() {
                Tree t = next.leaf;
                next = next.parent;
                return t;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            private TreePath next = TreePath.this;
        };
    }

    private CompilationUnitTree compilationUnit;
    private Tree leaf;
    private TreePath parent;
}
