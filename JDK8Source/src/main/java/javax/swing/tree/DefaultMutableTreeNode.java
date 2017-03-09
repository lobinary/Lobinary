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
   // ISSUE: this class depends on nothing in AWT -- move to java.util?

import java.beans.Transient;
import java.io.*;
import java.util.*;


/**
 * A <code>DefaultMutableTreeNode</code> is a general-purpose node in a tree data
 * structure.
 * For examples of using default mutable tree nodes, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
 * in <em>The Java Tutorial.</em>
 *
 * <p>
 *
 * A tree node may have at most one parent and 0 or more children.
 * <code>DefaultMutableTreeNode</code> provides operations for examining and modifying a
 * node's parent and children and also operations for examining the tree that
 * the node is a part of.  A node's tree is the set of all nodes that can be
 * reached by starting at the node and following all the possible links to
 * parents and children.  A node with no parent is the root of its tree; a
 * node with no children is a leaf.  A tree may consist of many subtrees,
 * each node acting as the root for its own subtree.
 * <p>
 * This class provides enumerations for efficiently traversing a tree or
 * subtree in various orders or for following the path between two nodes.
 * A <code>DefaultMutableTreeNode</code> may also hold a reference to a user object, the
 * use of which is left to the user.  Asking a <code>DefaultMutableTreeNode</code> for its
 * string representation with <code>toString()</code> returns the string
 * representation of its user object.
 * <p>
 * <b>This is not a thread safe class.</b>If you intend to use
 * a DefaultMutableTreeNode (or a tree of TreeNodes) in more than one thread, you
 * need to do your own synchronizing. A good convention to adopt is
 * synchronizing on the root node of a tree.
 * <p>
 * While DefaultMutableTreeNode implements the MutableTreeNode interface and
 * will allow you to add in any implementation of MutableTreeNode not all
 * of the methods in DefaultMutableTreeNode will be applicable to all
 * MutableTreeNodes implementations. Especially with some of the enumerations
 * that are provided, using some of these methods assumes the
 * DefaultMutableTreeNode contains only DefaultMutableNode instances. All
 * of the TreeNode/MutableTreeNode methods will behave as defined no
 * matter what implementations are added.
 *
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
 *  <code> DefaultMutableTreeNode </code>是树数据结构中的通用节点。
 * 有关使用默认可变树节点的示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">如何使用
 * 树</a>。
 *  <code> DefaultMutableTreeNode </code>是树数据结构中的通用节点。 Java教程。</em>。
 * 
 * <p>
 * 
 *  树节点最多可以有一个父节点和0个或更多个子节点。
 *  <code> DefaultMutableTreeNode </code>提供用于检查和修改节点的父和子的操作,以及用于检查节点是其一部分的树的操作。
 * 节点的树是可以通过在节点处开始并遵循到父节点和子节点的所有可能链接而到达的所有节点的集合。没有父节点的节点是其树的根;没有子节点的节点是叶子。树可以由许多子树组成,每个节点充当它自己的子树的根。
 * <p>
 *  此类提供用于以各种顺序高效地遍历树或子树或用于遵循两个节点之间的路径的枚举。
 *  <code> DefaultMutableTreeNode </code>还可以保存对用户对象的引用,其使用由用户自己决定。
 * 通过<code> toString()</code>请求<code> DefaultMutableTreeNode </code>字符串表示形式,返回其用户对象的字符串表示形式。
 * <p>
 * <b>这不是线程安全类。</b>如果您打算在多个线程中使用DefaultMutableTreeNode(或TreeNodes的树),您需要进行自己的同步。一个好的习惯是在树的根节点上同步。
 * <p>
 *  虽然DefaultMutableTreeNode实现了MutableTreeNode接口,并且将允许您在MutableTreeNode的任何实现中添加,但并不是DefaultMutableTreeNo
 * de中的所有方法都适用于所有MutableTreeNodes实现。
 * 特别是提供了一些枚举,使用这些方法中的一些假定DefaultMutableTreeNode只包含DefaultMutableNode实例。
 * 所有的TreeNode / MutableTreeNode方法将无论添加什么实现都会像定义的一样。
 * 
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see MutableTreeNode
 *
 * @author Rob Davis
 */
public class DefaultMutableTreeNode implements Cloneable,
       MutableTreeNode, Serializable
{
    private static final long serialVersionUID = -4298474751201349152L;

    /**
     * An enumeration that is always empty. This is used when an enumeration
     * of a leaf node's children is requested.
     * <p>
     *  始终为空的枚举。这是在请求叶节点的子节点的枚举时使用的。
     * 
     */
    static public final Enumeration<TreeNode> EMPTY_ENUMERATION
        = Collections.emptyEnumeration();

    /** this node's parent, or null if this node has no parent */
    protected MutableTreeNode   parent;

    /** array of children, may be null if this node has no children */
    protected Vector children;

    /** optional user object */
    transient protected Object  userObject;

    /** true if the node is able to have children */
    protected boolean           allowsChildren;


    /**
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     * <p>
     *  创建一个没有父级但没有子级,但允许子级的树节点。
     * 
     */
    public DefaultMutableTreeNode() {
        this(null);
    }

    /**
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * <p>
     * 创建一个没有父节点,没有子节点,但允许子节点的树节点,并使用指定的用户对象初始化它。
     * 
     * 
     * @param userObject an Object provided by the user that constitutes
     *                   the node's data
     */
    public DefaultMutableTreeNode(Object userObject) {
        this(userObject, true);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with
     * the specified user object, and that allows children only if
     * specified.
     *
     * <p>
     *  创建没有父对象的树节点,没有子对象,使用指定的用户对象初始化,并且仅当指定了子节点时才允许子节点。
     * 
     * 
     * @param userObject an Object provided by the user that constitutes
     *        the node's data
     * @param allowsChildren if true, the node is allowed to have child
     *        nodes -- otherwise, it is always a leaf node
     */
    public DefaultMutableTreeNode(Object userObject, boolean allowsChildren) {
        super();
        parent = null;
        this.allowsChildren = allowsChildren;
        this.userObject = userObject;
    }


    //
    //  Primitives
    //

    /**
     * Removes <code>newChild</code> from its present parent (if it has a
     * parent), sets the child's parent to this node, and then adds the child
     * to this node's child array at index <code>childIndex</code>.
     * <code>newChild</code> must not be null and must not be an ancestor of
     * this node.
     *
     * <p>
     *  从其当前父级(如果它有父级)中删除<code> newChild </code>,将子级的父级设置为此节点,然后将该子级添加到index <code> childIndex </code>处的该节点的
     * 子级数组。
     *  <code> newChild </code>不能为空,并且不能为此节点的祖先。
     * 
     * 
     * @param   newChild        the MutableTreeNode to insert under this node
     * @param   childIndex      the index in this node's child array
     *                          where this node is to be inserted
     * @exception       ArrayIndexOutOfBoundsException  if
     *                          <code>childIndex</code> is out of bounds
     * @exception       IllegalArgumentException        if
     *                          <code>newChild</code> is null or is an
     *                          ancestor of this node
     * @exception       IllegalStateException   if this node does not allow
     *                                          children
     * @see     #isNodeDescendant
     */
    public void insert(MutableTreeNode newChild, int childIndex) {
        if (!allowsChildren) {
            throw new IllegalStateException("node does not allow children");
        } else if (newChild == null) {
            throw new IllegalArgumentException("new child is null");
        } else if (isNodeAncestor(newChild)) {
            throw new IllegalArgumentException("new child is an ancestor");
        }

            MutableTreeNode oldParent = (MutableTreeNode)newChild.getParent();

            if (oldParent != null) {
                oldParent.remove(newChild);
            }
            newChild.setParent(this);
            if (children == null) {
                children = new Vector();
            }
            children.insertElementAt(newChild, childIndex);
    }

    /**
     * Removes the child at the specified index from this node's children
     * and sets that node's parent to null. The child node to remove
     * must be a <code>MutableTreeNode</code>.
     *
     * <p>
     *  从此节点的子节点删除指定索引处的子节点,并将该节点的父节点设置为null。要删除的子节点必须是<code> MutableTreeNode </code>。
     * 
     * 
     * @param   childIndex      the index in this node's child array
     *                          of the child to remove
     * @exception       ArrayIndexOutOfBoundsException  if
     *                          <code>childIndex</code> is out of bounds
     */
    public void remove(int childIndex) {
        MutableTreeNode child = (MutableTreeNode)getChildAt(childIndex);
        children.removeElementAt(childIndex);
        child.setParent(null);
    }

    /**
     * Sets this node's parent to <code>newParent</code> but does not
     * change the parent's child array.  This method is called from
     * <code>insert()</code> and <code>remove()</code> to
     * reassign a child's parent, it should not be messaged from anywhere
     * else.
     *
     * <p>
     *  将此节点的父代设置为<code> newParent </code>,但不会更改父代的子数组。
     * 此方法从<code> insert()</code>和<code> remove()</code>调用以重新分配孩子的父级,它不应该从其他地方消息。
     * 
     * 
     * @param   newParent       this node's new parent
     */
    @Transient
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    /**
     * Returns this node's parent or null if this node has no parent.
     *
     * <p>
     *  返回此节点的父节点或null(如果此节点没有父节点)。
     * 
     * 
     * @return  this node's parent TreeNode, or null if this node has no parent
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * Returns the child at the specified index in this node's child array.
     *
     * <p>
     *  返回此节点的子数组中指定索引处的子元素。
     * 
     * 
     * @param   index   an index into this node's child array
     * @exception       ArrayIndexOutOfBoundsException  if <code>index</code>
     *                                          is out of bounds
     * @return  the TreeNode in this node's child array at  the specified index
     */
    public TreeNode getChildAt(int index) {
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException("node has no children");
        }
        return (TreeNode)children.elementAt(index);
    }

    /**
     * Returns the number of children of this node.
     *
     * <p>
     *  返回此节点的子节点数。
     * 
     * 
     * @return  an int giving the number of children of this node
     */
    public int getChildCount() {
        if (children == null) {
            return 0;
        } else {
            return children.size();
        }
    }

    /**
     * Returns the index of the specified child in this node's child array.
     * If the specified node is not a child of this node, returns
     * <code>-1</code>.  This method performs a linear search and is O(n)
     * where n is the number of children.
     *
     * <p>
     *  返回此节点的子数组中指定子节点的索引。如果指定的节点不是此节点的子节点,则返回<code> -1 </code>。该方法执行线性搜索,并且是O(n),其中n是子节点数。
     * 
     * 
     * @param   aChild  the TreeNode to search for among this node's children
     * @exception       IllegalArgumentException        if <code>aChild</code>
     *                                                  is null
     * @return  an int giving the index of the node in this node's child
     *          array, or <code>-1</code> if the specified node is a not
     *          a child of this node
     */
    public int getIndex(TreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(aChild)) {
            return -1;
        }
        return children.indexOf(aChild);        // linear search
    }

    /**
     * Creates and returns a forward-order enumeration of this node's
     * children.  Modifying this node's child array invalidates any child
     * enumerations created before the modification.
     *
     * <p>
     * 创建并返回此节点的子节点的前向顺序枚举。修改此节点的子数组会使修改前创建的所有子枚举无效。
     * 
     * 
     * @return  an Enumeration of this node's children
     */
    public Enumeration children() {
        if (children == null) {
            return EMPTY_ENUMERATION;
        } else {
            return children.elements();
        }
    }

    /**
     * Determines whether or not this node is allowed to have children.
     * If <code>allows</code> is false, all of this node's children are
     * removed.
     * <p>
     * Note: By default, a node allows children.
     *
     * <p>
     *  确定此节点是否允许有子级。如果<code>允许</code>为false,则删除此节点的所有子节点。
     * <p>
     *  注意：默认情况下,一个节点允许子节点。
     * 
     * 
     * @param   allows  true if this node is allowed to have children
     */
    public void setAllowsChildren(boolean allows) {
        if (allows != allowsChildren) {
            allowsChildren = allows;
            if (!allowsChildren) {
                removeAllChildren();
            }
        }
    }

    /**
     * Returns true if this node is allowed to have children.
     *
     * <p>
     *  如果此节点允许有子项,则返回true。
     * 
     * 
     * @return  true if this node allows children, else false
     */
    public boolean getAllowsChildren() {
        return allowsChildren;
    }

    /**
     * Sets the user object for this node to <code>userObject</code>.
     *
     * <p>
     *  将此节点的用户对象设置为<code> userObject </code>。
     * 
     * 
     * @param   userObject      the Object that constitutes this node's
     *                          user-specified data
     * @see     #getUserObject
     * @see     #toString
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    /**
     * Returns this node's user object.
     *
     * <p>
     *  返回此节点的用户对象。
     * 
     * 
     * @return  the Object stored at this node by the user
     * @see     #setUserObject
     * @see     #toString
     */
    public Object getUserObject() {
        return userObject;
    }


    //
    //  Derived methods
    //

    /**
     * Removes the subtree rooted at this node from the tree, giving this
     * node a null parent.  Does nothing if this node is the root of its
     * tree.
     * <p>
     *  从树中删除以此节点为根的子树,将此节点设为空父。如果此节点是其树的根,则不执行任何操作。
     * 
     */
    public void removeFromParent() {
        MutableTreeNode parent = (MutableTreeNode)getParent();
        if (parent != null) {
            parent.remove(this);
        }
    }

    /**
     * Removes <code>aChild</code> from this node's child array, giving it a
     * null parent.
     *
     * <p>
     *  从此节点的子数组中删除<code> aChild </code>,赋给它一个空父亲。
     * 
     * 
     * @param   aChild  a child of this node to remove
     * @exception       IllegalArgumentException        if <code>aChild</code>
     *                                  is null or is not a child of this node
     */
    public void remove(MutableTreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(aChild)) {
            throw new IllegalArgumentException("argument is not a child");
        }
        remove(getIndex(aChild));       // linear search
    }

    /**
     * Removes all of this node's children, setting their parents to null.
     * If this node has no children, this method does nothing.
     * <p>
     *  删除所有此节点的子项,将其父项设置为null。如果此节点没有子节点,则此方法不执行任何操作。
     * 
     */
    public void removeAllChildren() {
        for (int i = getChildCount()-1; i >= 0; i--) {
            remove(i);
        }
    }

    /**
     * Removes <code>newChild</code> from its parent and makes it a child of
     * this node by adding it to the end of this node's child array.
     *
     * <p>
     *  从其父代中删除<code> newChild </code>,并将其添加到此节点的子数组的末尾,使其成为此节点的子代。
     * 
     * 
     * @see             #insert
     * @param   newChild        node to add as a child of this node
     * @exception       IllegalArgumentException    if <code>newChild</code>
     *                                          is null
     * @exception       IllegalStateException   if this node does not allow
     *                                          children
     */
    public void add(MutableTreeNode newChild) {
        if(newChild != null && newChild.getParent() == this)
            insert(newChild, getChildCount() - 1);
        else
            insert(newChild, getChildCount());
    }



    //
    //  Tree Queries
    //

    /**
     * Returns true if <code>anotherNode</code> is an ancestor of this node
     * -- if it is this node, this node's parent, or an ancestor of this
     * node's parent.  (Note that a node is considered an ancestor of itself.)
     * If <code>anotherNode</code> is null, this method returns false.  This
     * operation is at worst O(h) where h is the distance from the root to
     * this node.
     *
     * <p>
     *  如果<code> anotherNode </code>是此节点的祖先,如果它是此节点,此节点的父级或此节点的父级的祖先,则返回true。 (注意,一个节点被认为是自己的祖先。
     * )如果<code> anotherNode </code>为null,此方法返回false。此操作在最差O(h)处,其中h是从根到该节点的距离。
     * 
     * 
     * @see             #isNodeDescendant
     * @see             #getSharedAncestor
     * @param   anotherNode     node to test as an ancestor of this node
     * @return  true if this node is a descendant of <code>anotherNode</code>
     */
    public boolean isNodeAncestor(TreeNode anotherNode) {
        if (anotherNode == null) {
            return false;
        }

        TreeNode ancestor = this;

        do {
            if (ancestor == anotherNode) {
                return true;
            }
        } while((ancestor = ancestor.getParent()) != null);

        return false;
    }

    /**
     * Returns true if <code>anotherNode</code> is a descendant of this node
     * -- if it is this node, one of this node's children, or a descendant of
     * one of this node's children.  Note that a node is considered a
     * descendant of itself.  If <code>anotherNode</code> is null, returns
     * false.  This operation is at worst O(h) where h is the distance from the
     * root to <code>anotherNode</code>.
     *
     * <p>
     * 如果<code> anotherNode </code>是此节点的后代,则返回true  - 如果此节点是此节点的子节点之一,或者是此节点的子节点的后代。注意,一个节点被认为是它自己的后代。
     * 如果<code> anotherNode </code>为null,则返回false。此操作在最差O(h)处,其中h是从根到<code> anotherNode </code>的距离。
     * 
     * 
     * @see     #isNodeAncestor
     * @see     #getSharedAncestor
     * @param   anotherNode     node to test as descendant of this node
     * @return  true if this node is an ancestor of <code>anotherNode</code>
     */
    public boolean isNodeDescendant(DefaultMutableTreeNode anotherNode) {
        if (anotherNode == null)
            return false;

        return anotherNode.isNodeAncestor(this);
    }

    /**
     * Returns the nearest common ancestor to this node and <code>aNode</code>.
     * Returns null, if no such ancestor exists -- if this node and
     * <code>aNode</code> are in different trees or if <code>aNode</code> is
     * null.  A node is considered an ancestor of itself.
     *
     * <p>
     *  返回此节点和<code> aNode </code>的最近公共祖先。
     * 如果此节点和<code> aNode </code>在不同的树中或<code> aNode </code>为null,则返回null。节点被认为是其自身的祖先。
     * 
     * 
     * @see     #isNodeAncestor
     * @see     #isNodeDescendant
     * @param   aNode   node to find common ancestor with
     * @return  nearest ancestor common to this node and <code>aNode</code>,
     *          or null if none
     */
    public TreeNode getSharedAncestor(DefaultMutableTreeNode aNode) {
        if (aNode == this) {
            return this;
        } else if (aNode == null) {
            return null;
        }

        int             level1, level2, diff;
        TreeNode        node1, node2;

        level1 = getLevel();
        level2 = aNode.getLevel();

        if (level2 > level1) {
            diff = level2 - level1;
            node1 = aNode;
            node2 = this;
        } else {
            diff = level1 - level2;
            node1 = this;
            node2 = aNode;
        }

        // Go up the tree until the nodes are at the same level
        while (diff > 0) {
            node1 = node1.getParent();
            diff--;
        }

        // Move up the tree until we find a common ancestor.  Since we know
        // that both nodes are at the same level, we won't cross paths
        // unknowingly (if there is a common ancestor, both nodes hit it in
        // the same iteration).

        do {
            if (node1 == node2) {
                return node1;
            }
            node1 = node1.getParent();
            node2 = node2.getParent();
        } while (node1 != null);// only need to check one -- they're at the
        // same level so if one is null, the other is

        if (node1 != null || node2 != null) {
            throw new Error ("nodes should be null");
        }

        return null;
    }


    /**
     * Returns true if and only if <code>aNode</code> is in the same tree
     * as this node.  Returns false if <code>aNode</code> is null.
     *
     * <p>
     *  当且仅当<code> aNode </code>与此节点位于同一树中时,返回true。如果<code> aNode </code>为null,则返回false。
     * 
     * 
     * @see     #getSharedAncestor
     * @see     #getRoot
     * @return  true if <code>aNode</code> is in the same tree as this node;
     *          false if <code>aNode</code> is null
     */
    public boolean isNodeRelated(DefaultMutableTreeNode aNode) {
        return (aNode != null) && (getRoot() == aNode.getRoot());
    }


    /**
     * Returns the depth of the tree rooted at this node -- the longest
     * distance from this node to a leaf.  If this node has no children,
     * returns 0.  This operation is much more expensive than
     * <code>getLevel()</code> because it must effectively traverse the entire
     * tree rooted at this node.
     *
     * <p>
     *  返回根在此节点的树的深度 - 从此节点到叶的最长距离。如果此节点没有子节点,则返回0.此操作比<code> getLevel()</code>要昂贵得多,因为它必须有效地遍历以此节点为根的整个树。
     * 
     * 
     * @see     #getLevel
     * @return  the depth of the tree whose root is this node
     */
    public int getDepth() {
        Object  last = null;
        Enumeration     enum_ = breadthFirstEnumeration();

        while (enum_.hasMoreElements()) {
            last = enum_.nextElement();
        }

        if (last == null) {
            throw new Error ("nodes should be null");
        }

        return ((DefaultMutableTreeNode)last).getLevel() - getLevel();
    }



    /**
     * Returns the number of levels above this node -- the distance from
     * the root to this node.  If this node is the root, returns 0.
     *
     * <p>
     *  返回此节点上面的级别数 - 从根节点到此节点的距离。如果此节点是根,返回0。
     * 
     * 
     * @see     #getDepth
     * @return  the number of levels above this node
     */
    public int getLevel() {
        TreeNode ancestor;
        int levels = 0;

        ancestor = this;
        while((ancestor = ancestor.getParent()) != null){
            levels++;
        }

        return levels;
    }


    /**
      * Returns the path from the root, to get to this node.  The last
      * element in the path is this node.
      *
      * <p>
      *  返回从根路径,到达此节点。路径中的最后一个元素是此节点。
      * 
      * 
      * @return an array of TreeNode objects giving the path, where the
      *         first element in the path is the root and the last
      *         element is this node.
      */
    public TreeNode[] getPath() {
        return getPathToRoot(this, 0);
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
            retNodes = getPathToRoot(aNode.getParent(), depth);
            retNodes[retNodes.length - depth] = aNode;
        }
        return retNodes;
    }

    /**
      * Returns the user object path, from the root, to get to this node.
      * If some of the TreeNodes in the path have null user objects, the
      * returned path will contain nulls.
      * <p>
      * 返回用户对象路径,从根,到达此节点。如果路径中的某些TreeNodes具有空用户对象,则返回的路径将包含null。
      * 
      */
    public Object[] getUserObjectPath() {
        TreeNode[]          realPath = getPath();
        Object[]            retPath = new Object[realPath.length];

        for(int counter = 0; counter < realPath.length; counter++)
            retPath[counter] = ((DefaultMutableTreeNode)realPath[counter])
                               .getUserObject();
        return retPath;
    }

    /**
     * Returns the root of the tree that contains this node.  The root is
     * the ancestor with a null parent.
     *
     * <p>
     *  返回包含此节点的树的根。根是具有空父亲的祖先。
     * 
     * 
     * @see     #isNodeAncestor
     * @return  the root of the tree that contains this node
     */
    public TreeNode getRoot() {
        TreeNode ancestor = this;
        TreeNode previous;

        do {
            previous = ancestor;
            ancestor = ancestor.getParent();
        } while (ancestor != null);

        return previous;
    }


    /**
     * Returns true if this node is the root of the tree.  The root is
     * the only node in the tree with a null parent; every tree has exactly
     * one root.
     *
     * <p>
     *  如果此节点是树的根,则返回true。根是树中具有空父父的唯一节点;每棵树都有一个根。
     * 
     * 
     * @return  true if this node is the root of its tree
     */
    public boolean isRoot() {
        return getParent() == null;
    }


    /**
     * Returns the node that follows this node in a preorder traversal of this
     * node's tree.  Returns null if this node is the last node of the
     * traversal.  This is an inefficient way to traverse the entire tree; use
     * an enumeration, instead.
     *
     * <p>
     *  返回此节点树的前序遍历中跟随此节点的节点。如果此节点是遍历的最后一个节点,则返回null。这是遍历整个树的低效方法;使用枚举,而不是。
     * 
     * 
     * @see     #preorderEnumeration
     * @return  the node that follows this node in a preorder traversal, or
     *          null if this node is last
     */
    public DefaultMutableTreeNode getNextNode() {
        if (getChildCount() == 0) {
            // No children, so look for nextSibling
            DefaultMutableTreeNode nextSibling = getNextSibling();

            if (nextSibling == null) {
                DefaultMutableTreeNode aNode = (DefaultMutableTreeNode)getParent();

                do {
                    if (aNode == null) {
                        return null;
                    }

                    nextSibling = aNode.getNextSibling();
                    if (nextSibling != null) {
                        return nextSibling;
                    }

                    aNode = (DefaultMutableTreeNode)aNode.getParent();
                } while(true);
            } else {
                return nextSibling;
            }
        } else {
            return (DefaultMutableTreeNode)getChildAt(0);
        }
    }


    /**
     * Returns the node that precedes this node in a preorder traversal of
     * this node's tree.  Returns <code>null</code> if this node is the
     * first node of the traversal -- the root of the tree.
     * This is an inefficient way to
     * traverse the entire tree; use an enumeration, instead.
     *
     * <p>
     *  返回此节点树前的遍历节点的节点。如果此节点是遍历的第一个节点,则返回<code> null </code>  - 树的根。这是遍历整个树的低效方法;使用枚举,而不是。
     * 
     * 
     * @see     #preorderEnumeration
     * @return  the node that precedes this node in a preorder traversal, or
     *          null if this node is the first
     */
    public DefaultMutableTreeNode getPreviousNode() {
        DefaultMutableTreeNode previousSibling;
        DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)getParent();

        if (myParent == null) {
            return null;
        }

        previousSibling = getPreviousSibling();

        if (previousSibling != null) {
            if (previousSibling.getChildCount() == 0)
                return previousSibling;
            else
                return previousSibling.getLastLeaf();
        } else {
            return myParent;
        }
    }

    /**
     * Creates and returns an enumeration that traverses the subtree rooted at
     * this node in preorder.  The first node returned by the enumeration's
     * <code>nextElement()</code> method is this node.<P>
     *
     * Modifying the tree by inserting, removing, or moving a node invalidates
     * any enumerations created before the modification.
     *
     * <p>
     *  创建并返回一个枚举,遍历以preorder为根节点的子树。枚举的<code> nextElement()</code>方法返回的第一个节点是此节点。<P>
     * 
     *  通过插入,删除或移动节点来修改树会使修改之前创建的任何枚举无效。
     * 
     * 
     * @see     #postorderEnumeration
     * @return  an enumeration for traversing the tree in preorder
     */
    public Enumeration preorderEnumeration() {
        return new PreorderEnumeration(this);
    }

    /**
     * Creates and returns an enumeration that traverses the subtree rooted at
     * this node in postorder.  The first node returned by the enumeration's
     * <code>nextElement()</code> method is the leftmost leaf.  This is the
     * same as a depth-first traversal.<P>
     *
     * Modifying the tree by inserting, removing, or moving a node invalidates
     * any enumerations created before the modification.
     *
     * <p>
     *  创建并返回一个枚举,遍历以postorder为根节点的子树。枚举的<code> nextElement()</code>方法返回的第一个节点是最左边的叶子。这与深度优先遍历相同
     * 
     * 通过插入,删除或移动节点来修改树会使修改之前创建的任何枚举无效。
     * 
     * 
     * @see     #depthFirstEnumeration
     * @see     #preorderEnumeration
     * @return  an enumeration for traversing the tree in postorder
     */
    public Enumeration postorderEnumeration() {
        return new PostorderEnumeration(this);
    }

    /**
     * Creates and returns an enumeration that traverses the subtree rooted at
     * this node in breadth-first order.  The first node returned by the
     * enumeration's <code>nextElement()</code> method is this node.<P>
     *
     * Modifying the tree by inserting, removing, or moving a node invalidates
     * any enumerations created before the modification.
     *
     * <p>
     *  创建并返回枚举,遍历以宽度优先顺序在此节点上生根的子树。枚举的<code> nextElement()</code>方法返回的第一个节点是此节点。<P>
     * 
     *  通过插入,删除或移动节点来修改树会使修改之前创建的任何枚举无效。
     * 
     * 
     * @see     #depthFirstEnumeration
     * @return  an enumeration for traversing the tree in breadth-first order
     */
    public Enumeration breadthFirstEnumeration() {
        return new BreadthFirstEnumeration(this);
    }

    /**
     * Creates and returns an enumeration that traverses the subtree rooted at
     * this node in depth-first order.  The first node returned by the
     * enumeration's <code>nextElement()</code> method is the leftmost leaf.
     * This is the same as a postorder traversal.<P>
     *
     * Modifying the tree by inserting, removing, or moving a node invalidates
     * any enumerations created before the modification.
     *
     * <p>
     *  创建并返回以深度优先顺序遍历遍历此节点的子树的枚举。枚举的<code> nextElement()</code>方法返回的第一个节点是最左边的叶子。这与后序遍历相同。<P>
     * 
     *  通过插入,删除或移动节点来修改树会使修改之前创建的任何枚举无效。
     * 
     * 
     * @see     #breadthFirstEnumeration
     * @see     #postorderEnumeration
     * @return  an enumeration for traversing the tree in depth-first order
     */
    public Enumeration depthFirstEnumeration() {
        return postorderEnumeration();
    }

    /**
     * Creates and returns an enumeration that follows the path from
     * <code>ancestor</code> to this node.  The enumeration's
     * <code>nextElement()</code> method first returns <code>ancestor</code>,
     * then the child of <code>ancestor</code> that is an ancestor of this
     * node, and so on, and finally returns this node.  Creation of the
     * enumeration is O(m) where m is the number of nodes between this node
     * and <code>ancestor</code>, inclusive.  Each <code>nextElement()</code>
     * message is O(1).<P>
     *
     * Modifying the tree by inserting, removing, or moving a node invalidates
     * any enumerations created before the modification.
     *
     * <p>
     *  创建并返回从<code> ancestor </code>到此节点的路径后面的枚举。
     * 枚举的<code> nextElement()</code>方法首先返回<code> ancestor </code>,然后返回作为该节点的祖先的<code> ancestor </code>的子元素,
     * 返回此节点。
     *  创建并返回从<code> ancestor </code>到此节点的路径后面的枚举。枚举的创建是O(m),其中m是该节点和<code> ancestor </code>(包括)之间的节点数。
     * 每个<code> nextElement()</code>消息是O(1)。<P>。
     * 
     *  通过插入,删除或移动节点来修改树会使修改之前创建的任何枚举无效。
     * 
     * 
     * @see             #isNodeAncestor
     * @see             #isNodeDescendant
     * @exception       IllegalArgumentException if <code>ancestor</code> is
     *                                          not an ancestor of this node
     * @return  an enumeration for following the path from an ancestor of
     *          this node to this one
     */
    public Enumeration pathFromAncestorEnumeration(TreeNode ancestor) {
        return new PathBetweenNodesEnumeration(ancestor, this);
    }


    //
    //  Child Queries
    //

    /**
     * Returns true if <code>aNode</code> is a child of this node.  If
     * <code>aNode</code> is null, this method returns false.
     *
     * <p>
     * 如果<code> aNode </code>是此节点的子节点,则返回true。如果<code> aNode </code>为null,则此方法返回false。
     * 
     * 
     * @return  true if <code>aNode</code> is a child of this node; false if
     *                  <code>aNode</code> is null
     */
    public boolean isNodeChild(TreeNode aNode) {
        boolean retval;

        if (aNode == null) {
            retval = false;
        } else {
            if (getChildCount() == 0) {
                retval = false;
            } else {
                retval = (aNode.getParent() == this);
            }
        }

        return retval;
    }


    /**
     * Returns this node's first child.  If this node has no children,
     * throws NoSuchElementException.
     *
     * <p>
     *  返回此节点的第一个子节点。如果此节点没有子节点,则抛出NoSuchElementException。
     * 
     * 
     * @return  the first child of this node
     * @exception       NoSuchElementException  if this node has no children
     */
    public TreeNode getFirstChild() {
        if (getChildCount() == 0) {
            throw new NoSuchElementException("node has no children");
        }
        return getChildAt(0);
    }


    /**
     * Returns this node's last child.  If this node has no children,
     * throws NoSuchElementException.
     *
     * <p>
     *  返回此节点的最后一个子节点。如果此节点没有子节点,则抛出NoSuchElementException。
     * 
     * 
     * @return  the last child of this node
     * @exception       NoSuchElementException  if this node has no children
     */
    public TreeNode getLastChild() {
        if (getChildCount() == 0) {
            throw new NoSuchElementException("node has no children");
        }
        return getChildAt(getChildCount()-1);
    }


    /**
     * Returns the child in this node's child array that immediately
     * follows <code>aChild</code>, which must be a child of this node.  If
     * <code>aChild</code> is the last child, returns null.  This method
     * performs a linear search of this node's children for
     * <code>aChild</code> and is O(n) where n is the number of children; to
     * traverse the entire array of children, use an enumeration instead.
     *
     * <p>
     *  返回此节点的子数组中紧跟<code> aChild </code>后的子节点,该子节点必须是此节点的子节点。如果<code> aChild </code>是最后一个子代,则返回null。
     * 此方法对<code> aChild </code>执行此节点的子项的线性搜索,并且是O(n),其中n是子节点数;要遍历整个数组的孩子,请使用枚举。
     * 
     * 
     * @see             #children
     * @exception       IllegalArgumentException if <code>aChild</code> is
     *                                  null or is not a child of this node
     * @return  the child of this node that immediately follows
     *          <code>aChild</code>
     */
    public TreeNode getChildAfter(TreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        int index = getIndex(aChild);           // linear search

        if (index == -1) {
            throw new IllegalArgumentException("node is not a child");
        }

        if (index < getChildCount() - 1) {
            return getChildAt(index + 1);
        } else {
            return null;
        }
    }


    /**
     * Returns the child in this node's child array that immediately
     * precedes <code>aChild</code>, which must be a child of this node.  If
     * <code>aChild</code> is the first child, returns null.  This method
     * performs a linear search of this node's children for <code>aChild</code>
     * and is O(n) where n is the number of children.
     *
     * <p>
     *  返回此节点的子数组中紧接在<code> aChild </code>之前的子节点,该子节点必须是此节点的子节点。如果<code> aChild </code>是第一个子代,则返回null。
     * 此方法对<code> aChild </code>执行此节点的子项的线性搜索,并且是O(n),其中n是子节点数。
     * 
     * 
     * @exception       IllegalArgumentException if <code>aChild</code> is null
     *                                          or is not a child of this node
     * @return  the child of this node that immediately precedes
     *          <code>aChild</code>
     */
    public TreeNode getChildBefore(TreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }

        int index = getIndex(aChild);           // linear search

        if (index == -1) {
            throw new IllegalArgumentException("argument is not a child");
        }

        if (index > 0) {
            return getChildAt(index - 1);
        } else {
            return null;
        }
    }


    //
    //  Sibling Queries
    //


    /**
     * Returns true if <code>anotherNode</code> is a sibling of (has the
     * same parent as) this node.  A node is its own sibling.  If
     * <code>anotherNode</code> is null, returns false.
     *
     * <p>
     *  如果<code> anotherNode </code>是此节点的兄弟节点(与父节点相同),则返回true。节点是它自己的兄弟。
     * 如果<code> anotherNode </code>为null,则返回false。
     * 
     * 
     * @param   anotherNode     node to test as sibling of this node
     * @return  true if <code>anotherNode</code> is a sibling of this node
     */
    public boolean isNodeSibling(TreeNode anotherNode) {
        boolean retval;

        if (anotherNode == null) {
            retval = false;
        } else if (anotherNode == this) {
            retval = true;
        } else {
            TreeNode  myParent = getParent();
            retval = (myParent != null && myParent == anotherNode.getParent());

            if (retval && !((DefaultMutableTreeNode)getParent())
                           .isNodeChild(anotherNode)) {
                throw new Error("sibling has different parent");
            }
        }

        return retval;
    }


    /**
     * Returns the number of siblings of this node.  A node is its own sibling
     * (if it has no parent or no siblings, this method returns
     * <code>1</code>).
     *
     * <p>
     *  返回此节点的兄弟节点数。一个节点是它自己的兄弟节点(如果没有父节点或没有兄弟节点,这个方法返回<code> 1 </code>)。
     * 
     * 
     * @return  the number of siblings of this node
     */
    public int getSiblingCount() {
        TreeNode myParent = getParent();

        if (myParent == null) {
            return 1;
        } else {
            return myParent.getChildCount();
        }
    }


    /**
     * Returns the next sibling of this node in the parent's children array.
     * Returns null if this node has no parent or is the parent's last child.
     * This method performs a linear search that is O(n) where n is the number
     * of children; to traverse the entire array, use the parent's child
     * enumeration instead.
     *
     * <p>
     * 返回父节点的children数组中此节点的下一个兄弟节点。如果此节点没有父节点或是父节点的最后一个子节点,则返回null。
     * 该方法执行O(n)的线性搜索,其中n是子节点的数目;要遍历整个数组,请使用父级的子枚举。
     * 
     * 
     * @see     #children
     * @return  the sibling of this node that immediately follows this node
     */
    public DefaultMutableTreeNode getNextSibling() {
        DefaultMutableTreeNode retval;

        DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)getParent();

        if (myParent == null) {
            retval = null;
        } else {
            retval = (DefaultMutableTreeNode)myParent.getChildAfter(this);      // linear search
        }

        if (retval != null && !isNodeSibling(retval)) {
            throw new Error("child of parent is not a sibling");
        }

        return retval;
    }


    /**
     * Returns the previous sibling of this node in the parent's children
     * array.  Returns null if this node has no parent or is the parent's
     * first child.  This method performs a linear search that is O(n) where n
     * is the number of children.
     *
     * <p>
     *  返回父节点的children数组中此节点的前一个兄弟节点。如果此节点没有父节点或是父节点的第一个子节点,则返回null。此方法执行O(n)的线性搜索,其中n是子数。
     * 
     * 
     * @return  the sibling of this node that immediately precedes this node
     */
    public DefaultMutableTreeNode getPreviousSibling() {
        DefaultMutableTreeNode retval;

        DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)getParent();

        if (myParent == null) {
            retval = null;
        } else {
            retval = (DefaultMutableTreeNode)myParent.getChildBefore(this);     // linear search
        }

        if (retval != null && !isNodeSibling(retval)) {
            throw new Error("child of parent is not a sibling");
        }

        return retval;
    }



    //
    //  Leaf Queries
    //

    /**
     * Returns true if this node has no children.  To distinguish between
     * nodes that have no children and nodes that <i>cannot</i> have
     * children (e.g. to distinguish files from empty directories), use this
     * method in conjunction with <code>getAllowsChildren</code>
     *
     * <p>
     *  如果此节点没有子节点,则返回true。
     * 为了区分不具有子节点的节点和不能</i>具有子节点的节点(例如,为了将文件与空目录区分开),将此方法与<code> getAllowsChildren </code>。
     * 
     * 
     * @see     #getAllowsChildren
     * @return  true if this node has no children
     */
    public boolean isLeaf() {
        return (getChildCount() == 0);
    }


    /**
     * Finds and returns the first leaf that is a descendant of this node --
     * either this node or its first child's first leaf.
     * Returns this node if it is a leaf.
     *
     * <p>
     *  查找并返回作为此节点的后代的第一个叶子 - 此节点或其第一个子节点的第一个叶子。如果它是叶子,则返回此节点。
     * 
     * 
     * @see     #isLeaf
     * @see     #isNodeDescendant
     * @return  the first leaf in the subtree rooted at this node
     */
    public DefaultMutableTreeNode getFirstLeaf() {
        DefaultMutableTreeNode node = this;

        while (!node.isLeaf()) {
            node = (DefaultMutableTreeNode)node.getFirstChild();
        }

        return node;
    }


    /**
     * Finds and returns the last leaf that is a descendant of this node --
     * either this node or its last child's last leaf.
     * Returns this node if it is a leaf.
     *
     * <p>
     *  找到并返回这个节点的后代的最后一个叶 - 这个节点或它的最后一个孩子的最后一个叶。如果它是叶子,则返回此节点。
     * 
     * 
     * @see     #isLeaf
     * @see     #isNodeDescendant
     * @return  the last leaf in the subtree rooted at this node
     */
    public DefaultMutableTreeNode getLastLeaf() {
        DefaultMutableTreeNode node = this;

        while (!node.isLeaf()) {
            node = (DefaultMutableTreeNode)node.getLastChild();
        }

        return node;
    }


    /**
     * Returns the leaf after this node or null if this node is the
     * last leaf in the tree.
     * <p>
     * In this implementation of the <code>MutableNode</code> interface,
     * this operation is very inefficient. In order to determine the
     * next node, this method first performs a linear search in the
     * parent's child-list in order to find the current node.
     * <p>
     * That implementation makes the operation suitable for short
     * traversals from a known position. But to traverse all of the
     * leaves in the tree, you should use <code>depthFirstEnumeration</code>
     * to enumerate the nodes in the tree and use <code>isLeaf</code>
     * on each node to determine which are leaves.
     *
     * <p>
     *  返回此节点后的叶,如果此节点是树中的最后一个叶,则返回null。
     * <p>
     *  在这个<code> MutableNode </code>接口的实现中,这个操作是非常低效的。为了确定下一个节点,该方法首先在父的子列表中执行线性搜索,以找到当前节点。
     * <p>
     * 该实现使得该操作适合于从已知位置进行短遍历。
     * 但是要遍历树中的所有叶子,你应该使用<code> depthFirstEnumeration </code>来枚举树中的节点,并在每个节点上使用<code> isLeaf </code>来确定哪些是树叶
     * 。
     * 该实现使得该操作适合于从已知位置进行短遍历。
     * 
     * 
     * @see     #depthFirstEnumeration
     * @see     #isLeaf
     * @return  returns the next leaf past this node
     */
    public DefaultMutableTreeNode getNextLeaf() {
        DefaultMutableTreeNode nextSibling;
        DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)getParent();

        if (myParent == null)
            return null;

        nextSibling = getNextSibling(); // linear search

        if (nextSibling != null)
            return nextSibling.getFirstLeaf();

        return myParent.getNextLeaf();  // tail recursion
    }


    /**
     * Returns the leaf before this node or null if this node is the
     * first leaf in the tree.
     * <p>
     * In this implementation of the <code>MutableNode</code> interface,
     * this operation is very inefficient. In order to determine the
     * previous node, this method first performs a linear search in the
     * parent's child-list in order to find the current node.
     * <p>
     * That implementation makes the operation suitable for short
     * traversals from a known position. But to traverse all of the
     * leaves in the tree, you should use <code>depthFirstEnumeration</code>
     * to enumerate the nodes in the tree and use <code>isLeaf</code>
     * on each node to determine which are leaves.
     *
     * <p>
     *  返回此节点之前的叶,如果此节点是树中的第一个叶,则返回null。
     * <p>
     *  在这个<code> MutableNode </code>接口的实现中,这个操作是非常低效的。为了确定先前的节点,该方法首先在父的子列表中执行线性搜索,以便找到当前节点。
     * <p>
     *  该实现使得该操作适合于从已知位置进行短遍历。
     * 但是要遍历树中的所有叶子,你应该使用<code> depthFirstEnumeration </code>来枚举树中的节点,并在每个节点上使用<code> isLeaf </code>来确定哪些是树叶
     * 。
     *  该实现使得该操作适合于从已知位置进行短遍历。
     * 
     * 
     * @see             #depthFirstEnumeration
     * @see             #isLeaf
     * @return  returns the leaf before this node
     */
    public DefaultMutableTreeNode getPreviousLeaf() {
        DefaultMutableTreeNode previousSibling;
        DefaultMutableTreeNode myParent = (DefaultMutableTreeNode)getParent();

        if (myParent == null)
            return null;

        previousSibling = getPreviousSibling(); // linear search

        if (previousSibling != null)
            return previousSibling.getLastLeaf();

        return myParent.getPreviousLeaf();              // tail recursion
    }


    /**
     * Returns the total number of leaves that are descendants of this node.
     * If this node is a leaf, returns <code>1</code>.  This method is O(n)
     * where n is the number of descendants of this node.
     *
     * <p>
     * 
     * @see     #isNodeAncestor
     * @return  the number of leaves beneath this node
     */
    public int getLeafCount() {
        int count = 0;

        TreeNode node;
        Enumeration enum_ = breadthFirstEnumeration(); // order matters not

        while (enum_.hasMoreElements()) {
            node = (TreeNode)enum_.nextElement();
            if (node.isLeaf()) {
                count++;
            }
        }

        if (count < 1) {
            throw new Error("tree has zero leaves");
        }

        return count;
    }


    //
    //  Overrides
    //

    /**
     * Returns the result of sending <code>toString()</code> to this node's
     * user object, or the empty string if the node has no user object.
     *
     * <p>
     *  返回此节点的后代的叶子总数。如果此节点是叶,则返回<code> 1 </code>。此方法是O(n),其中n是此节点的后代数。
     * 
     * 
     * @see     #getUserObject
     */
    public String toString() {
        if (userObject == null) {
            return "";
        } else {
            return userObject.toString();
        }
    }

    /**
     * Overridden to make clone public.  Returns a shallow copy of this node;
     * the new node has no parent or children and has a reference to the same
     * user object, if any.
     *
     * <p>
     *  返回将<code> toString()</code>发送到此节点的用户对象的结果,如果节点没有用户对象,则返回空字符串。
     * 
     * 
     * @return  a copy of this node
     */
    public Object clone() {
        DefaultMutableTreeNode newNode;

        try {
            newNode = (DefaultMutableTreeNode)super.clone();

            // shallow copy -- the new node has no parent or children
            newNode.children = null;
            newNode.parent = null;

        } catch (CloneNotSupportedException e) {
            // Won't happen because we implement Cloneable
            throw new Error(e.toString());
        }

        return newNode;
    }


    // Serialization support.
    private void writeObject(ObjectOutputStream s) throws IOException {
        Object[]             tValues;

        s.defaultWriteObject();
        // Save the userObject, if its Serializable.
        if(userObject != null && userObject instanceof Serializable) {
            tValues = new Object[2];
            tValues[0] = "userObject";
            tValues[1] = userObject;
        }
        else
            tValues = new Object[0];
        s.writeObject(tValues);
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        Object[]      tValues;

        s.defaultReadObject();

        tValues = (Object[])s.readObject();

        if(tValues.length > 0 && tValues[0].equals("userObject"))
            userObject = tValues[1];
    }

    private final class PreorderEnumeration implements Enumeration<TreeNode> {
        private final Stack<Enumeration> stack = new Stack<Enumeration>();

        public PreorderEnumeration(TreeNode rootNode) {
            super();
            Vector<TreeNode> v = new Vector<TreeNode>(1);
            v.addElement(rootNode);     // PENDING: don't really need a vector
            stack.push(v.elements());
        }

        public boolean hasMoreElements() {
            return (!stack.empty() && stack.peek().hasMoreElements());
        }

        public TreeNode nextElement() {
            Enumeration enumer = stack.peek();
            TreeNode    node = (TreeNode)enumer.nextElement();
            Enumeration children = node.children();

            if (!enumer.hasMoreElements()) {
                stack.pop();
            }
            if (children.hasMoreElements()) {
                stack.push(children);
            }
            return node;
        }

    }  // End of class PreorderEnumeration



    final class PostorderEnumeration implements Enumeration<TreeNode> {
        protected TreeNode root;
        protected Enumeration<TreeNode> children;
        protected Enumeration<TreeNode> subtree;

        public PostorderEnumeration(TreeNode rootNode) {
            super();
            root = rootNode;
            children = root.children();
            subtree = EMPTY_ENUMERATION;
        }

        public boolean hasMoreElements() {
            return root != null;
        }

        public TreeNode nextElement() {
            TreeNode retval;

            if (subtree.hasMoreElements()) {
                retval = subtree.nextElement();
            } else if (children.hasMoreElements()) {
                subtree = new PostorderEnumeration(children.nextElement());
                retval = subtree.nextElement();
            } else {
                retval = root;
                root = null;
            }

            return retval;
        }

    }  // End of class PostorderEnumeration



    final class BreadthFirstEnumeration implements Enumeration<TreeNode> {
        protected Queue queue;

        public BreadthFirstEnumeration(TreeNode rootNode) {
            super();
            Vector<TreeNode> v = new Vector<TreeNode>(1);
            v.addElement(rootNode);     // PENDING: don't really need a vector
            queue = new Queue();
            queue.enqueue(v.elements());
        }

        public boolean hasMoreElements() {
            return (!queue.isEmpty() &&
                    ((Enumeration)queue.firstObject()).hasMoreElements());
        }

        public TreeNode nextElement() {
            Enumeration enumer = (Enumeration)queue.firstObject();
            TreeNode    node = (TreeNode)enumer.nextElement();
            Enumeration children = node.children();

            if (!enumer.hasMoreElements()) {
                queue.dequeue();
            }
            if (children.hasMoreElements()) {
                queue.enqueue(children);
            }
            return node;
        }


        // A simple queue with a linked list data structure.
        final class Queue {
            QNode head; // null if empty
            QNode tail;

            final class QNode {
                public Object   object;
                public QNode    next;   // null if end
                public QNode(Object object, QNode next) {
                    this.object = object;
                    this.next = next;
                }
            }

            public void enqueue(Object anObject) {
                if (head == null) {
                    head = tail = new QNode(anObject, null);
                } else {
                    tail.next = new QNode(anObject, null);
                    tail = tail.next;
                }
            }

            public Object dequeue() {
                if (head == null) {
                    throw new NoSuchElementException("No more elements");
                }

                Object retval = head.object;
                QNode oldHead = head;
                head = head.next;
                if (head == null) {
                    tail = null;
                } else {
                    oldHead.next = null;
                }
                return retval;
            }

            public Object firstObject() {
                if (head == null) {
                    throw new NoSuchElementException("No more elements");
                }

                return head.object;
            }

            public boolean isEmpty() {
                return head == null;
            }

        } // End of class Queue

    }  // End of class BreadthFirstEnumeration



    final class PathBetweenNodesEnumeration implements Enumeration<TreeNode> {
        protected Stack<TreeNode> stack;

        public PathBetweenNodesEnumeration(TreeNode ancestor,
                                           TreeNode descendant)
        {
            super();

            if (ancestor == null || descendant == null) {
                throw new IllegalArgumentException("argument is null");
            }

            TreeNode current;

            stack = new Stack<TreeNode>();
            stack.push(descendant);

            current = descendant;
            while (current != ancestor) {
                current = current.getParent();
                if (current == null && descendant != ancestor) {
                    throw new IllegalArgumentException("node " + ancestor +
                                " is not an ancestor of " + descendant);
                }
                stack.push(current);
            }
        }

        public boolean hasMoreElements() {
            return stack.size() > 0;
        }

        public TreeNode nextElement() {
            try {
                return stack.pop();
            } catch (EmptyStackException e) {
                throw new NoSuchElementException("No more elements");
            }
        }

    } // End of class PathBetweenNodesEnumeration



} // End of class DefaultMutableTreeNode
