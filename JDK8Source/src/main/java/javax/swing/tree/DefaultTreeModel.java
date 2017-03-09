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

import java.util.*;
import java.beans.ConstructorProperties;
import java.io.*;
import javax.swing.event.*;

/**
 * A simple tree data model that uses TreeNodes.
 * For further information and examples that use DefaultTreeModel,
 * see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
 * in <em>The Java Tutorial.</em>
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
 *  使用TreeNodes的简单树数据模型。
 * 有关使用DefaultTreeModel的详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">
 * 如何使用树</a>。
 *  使用TreeNodes的简单树数据模型。 Java教程。</em>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
public class DefaultTreeModel implements Serializable, TreeModel {
    /** Root of the tree. */
    protected TreeNode root;
    /** Listeners. */
    protected EventListenerList listenerList = new EventListenerList();
    /**
      * Determines how the <code>isLeaf</code> method figures
      * out if a node is a leaf node. If true, a node is a leaf
      * node if it does not allow children. (If it allows
      * children, it is not a leaf node, even if no children
      * are present.) That lets you distinguish between <i>folder</i>
      * nodes and <i>file</i> nodes in a file system, for example.
      * <p>
      * If this value is false, then any node which has no
      * children is a leaf node, and any node may acquire
      * children.
      *
      * <p>
      *  确定<code> isLeaf </code>方法如何计算出节点是否为叶节点。如果为true,如果节点不允许子节点,则它是叶节点。 (如果它允许孩子,它不是叶节点,即使没有孩子存在)。
      * 这允许您在文件系统中区分<i>文件夹</i>节点和<i>文件</i>节点,例如。
      * <p>
      *  如果该值为false,则没有子节点的任何节点是叶节点,并且任何节点可以获取子节点。
      * 
      * 
      * @see TreeNode#getAllowsChildren
      * @see TreeModel#isLeaf
      * @see #setAsksAllowsChildren
      */
    protected boolean asksAllowsChildren;


    /**
      * Creates a tree in which any node can have children.
      *
      * <p>
      *  创建一个树,其中任何节点都可以有子节点。
      * 
      * 
      * @param root a TreeNode object that is the root of the tree
      * @see #DefaultTreeModel(TreeNode, boolean)
      */
     @ConstructorProperties({"root"})
     public DefaultTreeModel(TreeNode root) {
        this(root, false);
    }

    /**
      * Creates a tree specifying whether any node can have children,
      * or whether only certain nodes can have children.
      *
      * <p>
      *  创建一个树,指定任何节点是否可以有子节点,或者只有某些节点可以有子节点。
      * 
      * 
      * @param root a TreeNode object that is the root of the tree
      * @param asksAllowsChildren a boolean, false if any node can
      *        have children, true if each node is asked to see if
      *        it can have children
      * @see #asksAllowsChildren
      */
    public DefaultTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super();
        this.root = root;
        this.asksAllowsChildren = asksAllowsChildren;
    }

    /**
      * Sets whether or not to test leafness by asking getAllowsChildren()
      * or isLeaf() to the TreeNodes.  If newvalue is true, getAllowsChildren()
      * is messaged, otherwise isLeaf() is messaged.
      * <p>
      * 设置是否通过向TreeNodes请求getAllowsChildren()或isLeaf()来测试叶度。
      * 如果newvalue为true,getAllowsChildren()被消息,否则isLeaf()被消息。
      * 
      */
    public void setAsksAllowsChildren(boolean newValue) {
        asksAllowsChildren = newValue;
    }

    /**
      * Tells how leaf nodes are determined.
      *
      * <p>
      *  告诉如何确定叶节点。
      * 
      * 
      * @return true if only nodes which do not allow children are
      *         leaf nodes, false if nodes which have no children
      *         (even if allowed) are leaf nodes
      * @see #asksAllowsChildren
      */
    public boolean asksAllowsChildren() {
        return asksAllowsChildren;
    }

    /**
     * Sets the root to <code>root</code>. A null <code>root</code> implies
     * the tree is to display nothing, and is legal.
     * <p>
     *  将根设置为<code> root </code>。一个null <code> root </code>意味着该树不显示任何内容,并且是合法的。
     * 
     */
    public void setRoot(TreeNode root) {
        Object oldRoot = this.root;
        this.root = root;
        if (root == null && oldRoot != null) {
            fireTreeStructureChanged(this, null);
        }
        else {
            nodeStructureChanged(root);
        }
    }

    /**
     * Returns the root of the tree.  Returns null only if the tree has
     * no nodes.
     *
     * <p>
     *  返回树的根。如果树没有节点,则返回null。
     * 
     * 
     * @return  the root of the tree
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Returns the index of child in parent.
     * If either the parent or child is <code>null</code>, returns -1.
     * <p>
     *  返回父级中的子级的索引。如果父代或子代是<code> null </code>,则返回-1。
     * 
     * 
     * @param parent a note in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1
     *    if either the parent or the child is <code>null</code>
     */
    public int getIndexOfChild(Object parent, Object child) {
        if(parent == null || child == null)
            return -1;
        return ((TreeNode)parent).getIndex((TreeNode)child);
    }

    /**
     * Returns the child of <I>parent</I> at index <I>index</I> in the parent's
     * child array.  <I>parent</I> must be a node previously obtained from
     * this data source. This should not return null if <i>index</i>
     * is a valid index for <i>parent</i> (that is <i>index</i> &gt;= 0 &amp;&amp;
     * <i>index</i> &lt; getChildCount(<i>parent</i>)).
     *
     * <p>
     *  返回父级子数组中索引<I>索引</I>处<I>父级</I>的子级。 <I>父</I>必须是先前从此数据源获取的节点。
     * 如果<i> index </i>是<i>父</i>的有效索引(即<i> index </i>&gt; = 0&amp;&amp; <i>索引</i>&lt; getChildCount(<i> pare
     * nt </i>))。
     *  返回父级子数组中索引<I>索引</I>处<I>父级</I>的子级。 <I>父</I>必须是先前从此数据源获取的节点。
     * 
     * 
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the child of <I>parent</I> at index <I>index</I>
     */
    public Object getChild(Object parent, int index) {
        return ((TreeNode)parent).getChildAt(index);
    }

    /**
     * Returns the number of children of <I>parent</I>.  Returns 0 if the node
     * is a leaf or if it has no children.  <I>parent</I> must be a node
     * previously obtained from this data source.
     *
     * <p>
     *  返回<I>父级</I>的子级数。如果节点是叶子或没有子节点,则返回0。 <I>父</I>必须是先前从此数据源获取的节点。
     * 
     * 
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the number of children of the node <I>parent</I>
     */
    public int getChildCount(Object parent) {
        return ((TreeNode)parent).getChildCount();
    }

    /**
     * Returns whether the specified node is a leaf node.
     * The way the test is performed depends on the
     * <code>askAllowsChildren</code> setting.
     *
     * <p>
     *  返回指定的节点是否为叶节点。执行测试的方式取决于<code> askAllowsChildren </code>设置。
     * 
     * 
     * @param node the node to check
     * @return true if the node is a leaf node
     *
     * @see #asksAllowsChildren
     * @see TreeModel#isLeaf
     */
    public boolean isLeaf(Object node) {
        if(asksAllowsChildren)
            return !((TreeNode)node).getAllowsChildren();
        return ((TreeNode)node).isLeaf();
    }

    /**
     * Invoke this method if you've modified the {@code TreeNode}s upon which
     * this model depends. The model will notify all of its listeners that the
     * model has changed.
     * <p>
     *  如果您修改了此模型所依赖的{@code TreeNode},请调用此方法。模型将通知所有的监听器模型已经改变。
     * 
     */
    public void reload() {
        reload(root);
    }

    /**
      * This sets the user object of the TreeNode identified by path
      * and posts a node changed.  If you use custom user objects in
      * the TreeModel you're going to need to subclass this and
      * set the user object of the changed node to something meaningful.
      * <p>
      * 这将设置由path标识的TreeNode的用户对象,并发布节点更改。如果在TreeModel中使用自定义用户对象,则需要对其进行子类化,并将更改的节点的用户对象设置为有意义的对象。
      * 
      */
    public void valueForPathChanged(TreePath path, Object newValue) {
        MutableTreeNode   aNode = (MutableTreeNode)path.getLastPathComponent();

        aNode.setUserObject(newValue);
        nodeChanged(aNode);
    }

    /**
     * Invoked this to insert newChild at location index in parents children.
     * This will then message nodesWereInserted to create the appropriate
     * event. This is the preferred way to add children as it will create
     * the appropriate event.
     * <p>
     *  调用此方法在位置索引处插入newChild在父项子项中。这将然后消息nodesWereInserted创建适当的事件。这是添加子项的首选方式,因为它将创建相应的事件。
     * 
     */
    public void insertNodeInto(MutableTreeNode newChild,
                               MutableTreeNode parent, int index){
        parent.insert(newChild, index);

        int[]           newIndexs = new int[1];

        newIndexs[0] = index;
        nodesWereInserted(parent, newIndexs);
    }

    /**
     * Message this to remove node from its parent. This will message
     * nodesWereRemoved to create the appropriate event. This is the
     * preferred way to remove a node as it handles the event creation
     * for you.
     * <p>
     *  消息此删除节点从其父。这将消息nodesWereRemoved创建适当的事件。在处理为您创建事件时,这是删除节点的首选方法。
     * 
     */
    public void removeNodeFromParent(MutableTreeNode node) {
        MutableTreeNode         parent = (MutableTreeNode)node.getParent();

        if(parent == null)
            throw new IllegalArgumentException("node does not have a parent.");

        int[]            childIndex = new int[1];
        Object[]         removedArray = new Object[1];

        childIndex[0] = parent.getIndex(node);
        parent.remove(childIndex[0]);
        removedArray[0] = node;
        nodesWereRemoved(parent, childIndex, removedArray);
    }

    /**
      * Invoke this method after you've changed how node is to be
      * represented in the tree.
      * <p>
      *  在更改如何在树中表示节点后调用此方法。
      * 
      */
    public void nodeChanged(TreeNode node) {
        if(listenerList != null && node != null) {
            TreeNode         parent = node.getParent();

            if(parent != null) {
                int        anIndex = parent.getIndex(node);
                if(anIndex != -1) {
                    int[]        cIndexs = new int[1];

                    cIndexs[0] = anIndex;
                    nodesChanged(parent, cIndexs);
                }
            }
            else if (node == getRoot()) {
                nodesChanged(node, null);
            }
        }
    }

    /**
     * Invoke this method if you've modified the {@code TreeNode}s upon which
     * this model depends. The model will notify all of its listeners that the
     * model has changed below the given node.
     *
     * <p>
     *  如果您修改了此模型所依赖的{@code TreeNode},请调用此方法。模型将通知所有的监听器模型在给定节点下面已经改变。
     * 
     * 
     * @param node the node below which the model has changed
     */
    public void reload(TreeNode node) {
        if(node != null) {
            fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }

    /**
      * Invoke this method after you've inserted some TreeNodes into
      * node.  childIndices should be the index of the new elements and
      * must be sorted in ascending order.
      * <p>
      *  在将一些TreeNodes插入节点后调用此方法。 childIndices应该是新元素的索引,必须按升序排序。
      * 
      */
    public void nodesWereInserted(TreeNode node, int[] childIndices) {
        if(listenerList != null && node != null && childIndices != null
           && childIndices.length > 0) {
            int               cCount = childIndices.length;
            Object[]          newChildren = new Object[cCount];

            for(int counter = 0; counter < cCount; counter++)
                newChildren[counter] = node.getChildAt(childIndices[counter]);
            fireTreeNodesInserted(this, getPathToRoot(node), childIndices,
                                  newChildren);
        }
    }

    /**
      * Invoke this method after you've removed some TreeNodes from
      * node.  childIndices should be the index of the removed elements and
      * must be sorted in ascending order. And removedChildren should be
      * the array of the children objects that were removed.
      * <p>
      *  从节点中删除一些TreeNodes后调用此方法。 childIndices应该是已删除元素的索引,必须按升序排序。而removedChildren应该是被删除的子对象的数组。
      * 
      */
    public void nodesWereRemoved(TreeNode node, int[] childIndices,
                                 Object[] removedChildren) {
        if(node != null && childIndices != null) {
            fireTreeNodesRemoved(this, getPathToRoot(node), childIndices,
                                 removedChildren);
        }
    }

    /**
      * Invoke this method after you've changed how the children identified by
      * childIndicies are to be represented in the tree.
      * <p>
      *  在更改由childIndicies标识的子代理将如何在树中表示后,调用此方法。
      * 
      */
    public void nodesChanged(TreeNode node, int[] childIndices) {
        if(node != null) {
            if (childIndices != null) {
                int            cCount = childIndices.length;

                if(cCount > 0) {
                    Object[]       cChildren = new Object[cCount];

                    for(int counter = 0; counter < cCount; counter++)
                        cChildren[counter] = node.getChildAt
                            (childIndices[counter]);
                    fireTreeNodesChanged(this, getPathToRoot(node),
                                         childIndices, cChildren);
                }
            }
            else if (node == getRoot()) {
                fireTreeNodesChanged(this, getPathToRoot(node), null, null);
            }
        }
    }

    /**
      * Invoke this method if you've totally changed the children of
      * node and its children's children...  This will post a
      * treeStructureChanged event.
      * <p>
      * 如果您完全更改了节点及其子节点的子节点的子节点,则调用此方法...这将发布一个treeStructureChanged事件。
      * 
      */
    public void nodeStructureChanged(TreeNode node) {
        if(node != null) {
           fireTreeStructureChanged(this, getPathToRoot(node), null, null);
        }
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * <p>
     *  构建节点的父节点,直到并包括根节点,其中原始节点是返回的数组中的最后一个元素。返回的数组的长度给出了树中节点的深度。
     * 
     * 
     * @param aNode the TreeNode to get the path for
     */
    public TreeNode[] getPathToRoot(TreeNode aNode) {
        return getPathToRoot(aNode, 0);
    }

    /**
     * Builds the parents of node up to and including the root node,
     * where the original node is the last element in the returned array.
     * The length of the returned array gives the node's depth in the
     * tree.
     *
     * <p>
     *  构建节点的父节点,直到并包括根节点,其中原始节点是返回的数组中的最后一个元素。返回的数组的长度给出了树中节点的深度。
     * 
     * 
     * @param aNode  the TreeNode to get the path for
     * @param depth  an int giving the number of steps already taken towards
     *        the root (on recursive calls), used to size the returned array
     * @return an array of TreeNodes giving the path from the root to the
     *         specified node
     */
    protected TreeNode[] getPathToRoot(TreeNode aNode, int depth) {
        TreeNode[]              retNodes;
        // This method recurses, traversing towards the root in order
        // size the array. On the way back, it fills in the nodes,
        // starting from the root and working back to the original node.

        /* Check for null, in case someone passed in a null node, or
        /* <p>
        /* 
           they passed in an element that isn't rooted at root. */
        if(aNode == null) {
            if(depth == 0)
                return null;
            else
                retNodes = new TreeNode[depth];
        }
        else {
            depth++;
            if(aNode == root)
                retNodes = new TreeNode[depth];
            else
                retNodes = getPathToRoot(aNode.getParent(), depth);
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    //
    //  Events
    //

    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     *
     * <p>
     *  为树更改后发布的TreeModelEvent添加侦听器。
     * 
     * 
     * @see     #removeTreeModelListener
     * @param   l       the listener to add
     */
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    /**
     * Removes a listener previously added with <B>addTreeModelListener()</B>.
     *
     * <p>
     *  删除以前用<B> addTreeModelListener()</B>添加的侦听器。
     * 
     * 
     * @see     #addTreeModelListener
     * @param   l       the listener to remove
     */
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    /**
     * Returns an array of all the tree model listeners
     * registered on this model.
     *
     * <p>
     *  返回在此模型上注册的所有树模型侦听器的数组。
     * 
     * 
     * @return all of this model's <code>TreeModelListener</code>s
     *         or an empty
     *         array if no tree model listeners are currently registered
     *
     * @see #addTreeModelListener
     * @see #removeTreeModelListener
     *
     * @since 1.4
     */
    public TreeModelListener[] getTreeModelListeners() {
        return listenerList.getListeners(TreeModelListener.class);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用传递到fire方法的参数进行延迟创建。
     * 
     * 
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent of the nodes that changed; use
     *             {@code null} to identify the root has changed
     * @param childIndices the indices of the changed elements
     * @param children the changed elements
     */
    protected void fireTreeNodesChanged(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用传递到fire方法的参数进行延迟创建。
     * 
     * 
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent the nodes were added to
     * @param childIndices the indices of the new elements
     * @param children the new elements
     */
    protected void fireTreeNodesInserted(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesInserted(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用传递到fire方法的参数进行延迟创建。
     * 
     * 
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent the nodes were removed from
     * @param childIndices the indices of the removed elements
     * @param children the removed elements
     */
    protected void fireTreeNodesRemoved(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeNodesRemoved(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。事件实例使用传递到fire方法的参数进行延迟创建。
     * 
     * 
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent of the structure that has changed;
     *             use {@code null} to identify the root has changed
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     */
    protected void fireTreeStructureChanged(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * <p>
     * 通知所有已注册有关此事件类型的通知的收件人。事件实例使用传递到fire方法的参数进行延迟创建。
     * 
     * 
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent of the structure that has changed;
     *             use {@code null} to identify the root has changed
     */
    private void fireTreeStructureChanged(Object source, TreePath path) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
            }
        }
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this model.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     *
     * You can specify the <code>listenerType</code> argument
     * with a class literal,
     * such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * <code>DefaultTreeModel</code> <code>m</code>
     * for its tree model listeners with the following code:
     *
     * <pre>TreeModelListener[] tmls = (TreeModelListener[])(m.getListeners(TreeModelListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     *  返回当前在此模型上注册为<code> <em> Foo </em> Listener </code>的所有对象的数组。
     * 使用<code> add <em> </em>侦听器</code>方法注册<code> <em> </em>侦听器</code>。
     * 
     * <p>
     * 
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listener.class </code>。
     * 例如,您可以使用以下代码查询其树模型侦听器的<code> DefaultTreeModel </code> <code> m </code>：。
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this component,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getTreeModelListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }

    // Serialization support.
    private void writeObject(ObjectOutputStream s) throws IOException {
        Vector<Object> values = new Vector<Object>();

        s.defaultWriteObject();
        // Save the root, if its Serializable.
        if(root != null && root instanceof Serializable) {
            values.addElement("root");
            values.addElement(root);
        }
        s.writeObject(values);
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        Vector          values = (Vector)s.readObject();
        int             indexCounter = 0;
        int             maxCounter = values.size();

        if(indexCounter < maxCounter && values.elementAt(indexCounter).
           equals("root")) {
            root = (TreeNode)values.elementAt(++indexCounter);
            indexCounter++;
        }
    }


} // End of class DefaultTreeModel
