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

import java.util.Enumeration;

/**
 * Defines the requirements for an object that can be used as a
 * tree node in a JTree.
 * <p>
 * Implementations of <code>TreeNode</code> that override <code>equals</code>
 * will typically need to override <code>hashCode</code> as well.  Refer
 * to {@link javax.swing.tree.TreeModel} for more information.
 *
 * For further information and examples of using tree nodes,
 * see <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Tree Nodes</a>
 * in <em>The Java Tutorial.</em>
 *
 * <p>
 *  定义可在JTree中用作树节点的对象的要求。
 * <p>
 *  覆盖<code> equals </code>的<code> TreeNode </code>的实现通常也需要重写<code> hashCode </code>。
 * 有关详细信息,请参阅{@link javax.swing.tree.TreeModel}。
 * 
 *  有关使用树节点的更多信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">如何
 * 使用树节点</a>。
 *  em> Java教程。</em>。
 * 
 * 
 * @author Rob Davis
 * @author Scott Violet
 */

public interface TreeNode
{
    /**
     * Returns the child <code>TreeNode</code> at index
     * <code>childIndex</code>.
     * <p>
     *  返回索引<code> childIndex </code>处的子<code> TreeNode </code>。
     * 
     */
    TreeNode getChildAt(int childIndex);

    /**
     * Returns the number of children <code>TreeNode</code>s the receiver
     * contains.
     * <p>
     *  返回接收器包含的子代码<code> TreeNode </code>。
     * 
     */
    int getChildCount();

    /**
     * Returns the parent <code>TreeNode</code> of the receiver.
     * <p>
     *  返回接收者的父代码<code> TreeNode </code>。
     * 
     */
    TreeNode getParent();

    /**
     * Returns the index of <code>node</code> in the receivers children.
     * If the receiver does not contain <code>node</code>, -1 will be
     * returned.
     * <p>
     *  返回接收器children中<code> node </code>的索引。如果接收方不包含<code> node </code>,将返回-1。
     * 
     */
    int getIndex(TreeNode node);

    /**
     * Returns true if the receiver allows children.
     * <p>
     *  如果接收者允许孩子,则返回true。
     * 
     */
    boolean getAllowsChildren();

    /**
     * Returns true if the receiver is a leaf.
     * <p>
     *  如果接收器是叶子,则返回true。
     * 
     */
    boolean isLeaf();

    /**
     * Returns the children of the receiver as an <code>Enumeration</code>.
     * <p>
     *  以<code>枚举</code>返回接收器的子代。
     */
    Enumeration children();
}
