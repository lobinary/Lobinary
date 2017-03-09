/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Defines the requirements for a tree node object that can change --
 * by adding or removing child nodes, or by changing the contents
 * of a user object stored in the node.
 *
 * <p>
 *  定义可以更改的树节点对象的要求 - 通过添加或删除子节点,或更改存储在节点中的用户对象的内容。
 * 
 * 
 * @see DefaultMutableTreeNode
 * @see javax.swing.JTree
 *
 * @author Rob Davis
 * @author Scott Violet
 */

public interface MutableTreeNode extends TreeNode
{
    /**
     * Adds <code>child</code> to the receiver at <code>index</code>.
     * <code>child</code> will be messaged with <code>setParent</code>.
     * <p>
     *  在<code> index </code>处向接收者添加<code> child </code>。 <code> child </code>会被<code> setParent </code>消息。
     * 
     */
    void insert(MutableTreeNode child, int index);

    /**
     * Removes the child at <code>index</code> from the receiver.
     * <p>
     *  从接收器中删除<code> index </code>处的子对象。
     * 
     */
    void remove(int index);

    /**
     * Removes <code>node</code> from the receiver. <code>setParent</code>
     * will be messaged on <code>node</code>.
     * <p>
     *  从接收器中删除<code> node </code>。 <code> setParent </code>会在<code>节点</code>上发送消息。
     * 
     */
    void remove(MutableTreeNode node);

    /**
     * Resets the user object of the receiver to <code>object</code>.
     * <p>
     *  将接收器的用户对象重置为<code> object </code>。
     * 
     */
    void setUserObject(Object object);

    /**
     * Removes the receiver from its parent.
     * <p>
     *  从其父级删除接收者。
     * 
     */
    void removeFromParent();

    /**
     * Sets the parent of the receiver to <code>newParent</code>.
     * <p>
     *  将接收者的父级设置为<code> newParent </code>。
     */
    void setParent(MutableTreeNode newParent);
}
