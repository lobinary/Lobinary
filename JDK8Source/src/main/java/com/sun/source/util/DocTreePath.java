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

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.doctree.DocTree;
import java.util.Iterator;

/**
 * A path of tree nodes, typically used to represent the sequence of ancestor
 * nodes of a tree node up to the top level DocCommentTree node.
 *
 * <p>
 *  树节点的路径,通常用于表示树节点的祖先节点到顶级DocCommentTree节点的序列。
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public class DocTreePath implements Iterable<DocTree> {
    /**
     * Gets a documentation tree path for a tree node within a compilation unit.
     * <p>
     *  获取编译单元中树节点的文档树路径。
     * 
     * 
     * @return null if the node is not found
     */
    public static DocTreePath getPath(TreePath treePath, DocCommentTree doc, DocTree target) {
        return getPath(new DocTreePath(treePath, doc), target);
    }

    /**
     * Gets a documentation tree path for a tree node within a subtree identified by a DocTreePath object.
     * <p>
     *  获取由DocTreePath对象标识的子树内的树节点的文档树路径。
     * 
     * 
     * @return null if the node is not found
     */
    public static DocTreePath getPath(DocTreePath path, DocTree target) {
        path.getClass();
        target.getClass();

        class Result extends Error {
            static final long serialVersionUID = -5942088234594905625L;
            DocTreePath path;
            Result(DocTreePath path) {
                this.path = path;
            }
        }

        class PathFinder extends DocTreePathScanner<DocTreePath,DocTree> {
            public DocTreePath scan(DocTree tree, DocTree target) {
                if (tree == target) {
                    throw new Result(new DocTreePath(getCurrentPath(), target));
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
     * Creates a DocTreePath for a root node.
     *
     * <p>
     *  为根节点创建DocTreePath。
     * 
     * 
     * @param treePath the TreePath from which the root node was created.
     * @param t the DocCommentTree to create the path for.
     */
    public DocTreePath(TreePath treePath, DocCommentTree t) {
        treePath.getClass();
        t.getClass();

        this.treePath = treePath;
        this.docComment = t;
        this.parent = null;
        this.leaf = t;
    }

    /**
     * Creates a DocTreePath for a child node.
     * <p>
     *  为子节点创建DocTreePath。
     * 
     */
    public DocTreePath(DocTreePath p, DocTree t) {
        if (t.getKind() == DocTree.Kind.DOC_COMMENT) {
            throw new IllegalArgumentException("Use DocTreePath(TreePath, DocCommentTree) to construct DocTreePath for a DocCommentTree.");
        } else {
            treePath = p.treePath;
            docComment = p.docComment;
            parent = p;
        }
        leaf = t;
    }

    /**
     * Get the TreePath associated with this path.
     * <p>
     *  获取与此路径相关联的TreePath。
     * 
     * 
     * @return TreePath for this DocTreePath
     */
    public TreePath getTreePath() {
        return treePath;
    }

    /**
     * Get the DocCommentTree associated with this path.
     * <p>
     *  获取与此路径相关联的DocCommentTree。
     * 
     * 
     * @return DocCommentTree for this DocTreePath
     */
    public DocCommentTree getDocComment() {
        return docComment;
    }

    /**
     * Get the leaf node for this path.
     * <p>
     *  获取此路径的叶节点。
     * 
     * 
     * @return DocTree for this DocTreePath
     */
    public DocTree getLeaf() {
        return leaf;
    }

    /**
     * Get the path for the enclosing node, or null if there is no enclosing node.
     * <p>
     *  获取封闭节点的路径,如果没有封闭节点,则返回null。
     * 
     * @return DocTreePath of parent
     */
    public DocTreePath getParentPath() {
        return parent;
    }

    public Iterator<DocTree> iterator() {
        return new Iterator<DocTree>() {
            public boolean hasNext() {
                return next != null;
            }

            public DocTree next() {
                DocTree t = next.leaf;
                next = next.parent;
                return t;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            private DocTreePath next = DocTreePath.this;
        };
    }

    private final TreePath treePath;
    private final DocCommentTree docComment;
    private final DocTree leaf;
    private final DocTreePath parent;
}
