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

import java.io.*;
import java.beans.ConstructorProperties;

/**
 * {@code TreePath} represents an array of objects that uniquely
 * identify the path to a node in a tree. The elements of the array
 * are ordered with the root as the first element of the array. For
 * example, a file on the file system is uniquely identified based on
 * the array of parent directories and the name of the file. The path
 * {@code /tmp/foo/bar} could be represented by a {@code TreePath} as
 * {@code new TreePath(new Object[] {"tmp", "foo", "bar"})}.
 * <p>
 * {@code TreePath} is used extensively by {@code JTree} and related classes.
 * For example, {@code JTree} represents the selection as an array of
 * {@code TreePath}s. When used with {@code JTree}, the elements of the
 * path are the objects returned from the {@code TreeModel}. When {@code JTree}
 * is paired with {@code DefaultTreeModel}, the elements of the
 * path are {@code TreeNode}s. The following example illustrates extracting
 * the user object from the selection of a {@code JTree}:
 * <pre>
 *   DefaultMutableTreeNode root = ...;
 *   DefaultTreeModel model = new DefaultTreeModel(root);
 *   JTree tree = new JTree(model);
 *   ...
 *   TreePath selectedPath = tree.getSelectionPath();
 *   DefaultMutableTreeNode selectedNode =
 *       ((DefaultMutableTreeNode)selectedPath.getLastPathComponent()).
 *       getUserObject();
 * </pre>
 * Subclasses typically need override only {@code
 * getLastPathComponent}, and {@code getParentPath}. As {@code JTree}
 * internally creates {@code TreePath}s at various points, it's
 * generally not useful to subclass {@code TreePath} and use with
 * {@code JTree}.
 * <p>
 * While {@code TreePath} is serializable, a {@code
 * NotSerializableException} is thrown if any elements of the path are
 * not serializable.
 * <p>
 * For further information and examples of using tree paths,
 * see <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
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
 *  {@code TreePath}表示唯一标识树中节点路径的对象数组。数组的元素以root作为数组的第一个元素排序。例如,基于父目录的数组和文件的名称唯一地标识文件系统上的文件。
 * 路径{@code / tmp / foo / bar}可以由{@code TreePath}表示为{@code new TreePath(new Object [] {"tmp","foo","bar"}
 * )}。
 *  {@code TreePath}表示唯一标识树中节点路径的对象数组。数组的元素以root作为数组的第一个元素排序。例如,基于父目录的数组和文件的名称唯一地标识文件系统上的文件。
 * <p>
 *  {@code TreePath}被{@code JTree}和相关类广泛使用。例如,{@code JTree}表示选择为{@code TreePath}的数组。
 * 当与{@code JTree}一起使用时,路径的元素是从{@code TreeModel}返回的对象。
 * 当{@code JTree}与{@code DefaultTreeModel}配对时,路径的元素是{@code TreeNode}。以下示例说明如何从{@code JTree}的选择中提取用户对象：。
 * <pre>
 *  DefaultMutableTreeNode root = ...; DefaultTreeModel model = new DefaultTreeModel(root); JTree tree =
 *  new JTree(model); ... TreePath selectedPath = tree.getSelectionPath(); DefaultMutableTreeNode select
 * edNode =((DefaultMutableTreeNode)selectedPath.getLastPathComponent())。
 *  getUserObject();。
 * </pre>
 * 子类通常只需要覆盖{@code getLastPathComponent}和{@code getParentPath}。
 * 由于{@code JTree}在各个点内部创建了{@code TreePath},所以通常不需要子类化{@code TreePath}并与{@code JTree}结合使用。
 * <p>
 *  虽然{@code TreePath}是可序列化的,但如果路径的任何元素不可序列化,则会抛出{@code NotSerializableException}。
 * <p>
 *  有关使用树路径的详细信息和示例,请参阅<em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">
 * 如何使用树</a> > Java教程。
 * </em>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Scott Violet
 * @author Philip Milne
 */
public class TreePath extends Object implements Serializable {
    /** Path representing the parent, null if lastPathComponent represents
    /* <p>
    /* 
     * the root. */
    private TreePath           parentPath;
    /** Last path component. */
    private Object lastPathComponent;

    /**
     * Creates a {@code TreePath} from an array. The array uniquely
     * identifies the path to a node.
     *
     * <p>
     *  从数组创建{@code TreePath}。数组唯一标识节点的路径。
     * 
     * 
     * @param path an array of objects representing the path to a node
     * @throws IllegalArgumentException if {@code path} is {@code null},
     *         empty, or contains a {@code null} value
     */
    @ConstructorProperties({"path"})
    public TreePath(Object[] path) {
        if(path == null || path.length == 0)
            throw new IllegalArgumentException("path in TreePath must be non null and not empty.");
        lastPathComponent = path[path.length - 1];
        if (lastPathComponent == null) {
            throw new IllegalArgumentException(
                "Last path component must be non-null");
        }
        if(path.length > 1)
            parentPath = new TreePath(path, path.length - 1);
    }

    /**
     * Creates a {@code TreePath} containing a single element. This is
     * used to construct a {@code TreePath} identifying the root.
     *
     * <p>
     *  创建包含单个元素的{@code TreePath}。这用于构造一个{@code TreePath}标识根。
     * 
     * 
     * @param lastPathComponent the root
     * @see #TreePath(Object[])
     * @throws IllegalArgumentException if {@code lastPathComponent} is
     *         {@code null}
     */
    public TreePath(Object lastPathComponent) {
        if(lastPathComponent == null)
            throw new IllegalArgumentException("path in TreePath must be non null.");
        this.lastPathComponent = lastPathComponent;
        parentPath = null;
    }

    /**
     * Creates a {@code TreePath} with the specified parent and element.
     *
     * <p>
     *  使用指定的父元素创建{@code TreePath}。
     * 
     * 
     * @param parent the path to the parent, or {@code null} to indicate
     *        the root
     * @param lastPathComponent the last path element
     * @throws IllegalArgumentException if {@code lastPathComponent} is
     *         {@code null}
     */
    protected TreePath(TreePath parent, Object lastPathComponent) {
        if(lastPathComponent == null)
            throw new IllegalArgumentException("path in TreePath must be non null.");
        parentPath = parent;
        this.lastPathComponent = lastPathComponent;
    }

    /**
     * Creates a {@code TreePath} from an array. The returned
     * {@code TreePath} represents the elements of the array from
     * {@code 0} to {@code length - 1}.
     * <p>
     * This constructor is used internally, and generally not useful outside
     * of subclasses.
     *
     * <p>
     *  从数组创建{@code TreePath}。返回的{@code TreePath}表示从{@code 0}到{@code length  -  1}的数组元素。
     * <p>
     * 此构造函数在内部使用,通常在子类外部无用。
     * 
     * 
     * @param path the array to create the {@code TreePath} from
     * @param length identifies the number of elements in {@code path} to
     *        create the {@code TreePath} from
     * @throws NullPointerException if {@code path} is {@code null}
     * @throws ArrayIndexOutOfBoundsException if {@code length - 1} is
     *         outside the range of the array
     * @throws IllegalArgumentException if any of the elements from
     *         {@code 0} to {@code length - 1} are {@code null}
     */
    protected TreePath(Object[] path, int length) {
        lastPathComponent = path[length - 1];
        if (lastPathComponent == null) {
            throw new IllegalArgumentException(
                "Path elements must be non-null");
        }
        if(length > 1)
            parentPath = new TreePath(path, length - 1);
    }

    /**
     * Creates an empty {@code TreePath}.  This is provided for
     * subclasses that represent paths in a different
     * manner. Subclasses that use this constructor must override
     * {@code getLastPathComponent}, and {@code getParentPath}.
     * <p>
     *  创建一个空的{@code TreePath}。这是为以不同方式表示路径的子类提供的。
     * 使用此构造函数的子类必须覆盖{@code getLastPathComponent}和{@code getParentPath}。
     * 
     */
    protected TreePath() {
    }

    /**
     * Returns an ordered array of the elements of this {@code TreePath}.
     * The first element is the root.
     *
     * <p>
     *  返回此{@code TreePath}的元素的有序数组。第一个元素是根。
     * 
     * 
     * @return an array of the elements in this {@code TreePath}
     */
    public Object[] getPath() {
        int            i = getPathCount();
        Object[]       result = new Object[i--];

        for(TreePath path = this; path != null; path = path.getParentPath()) {
            result[i--] = path.getLastPathComponent();
        }
        return result;
    }

    /**
     * Returns the last element of this path.
     *
     * <p>
     *  返回此路径的最后一个元素。
     * 
     * 
     * @return the last element in the path
     */
    public Object getLastPathComponent() {
        return lastPathComponent;
    }

    /**
     * Returns the number of elements in the path.
     *
     * <p>
     *  返回路径中的元素数。
     * 
     * 
     * @return the number of elements in the path
     */
    public int getPathCount() {
        int        result = 0;
        for(TreePath path = this; path != null; path = path.getParentPath()) {
            result++;
        }
        return result;
    }

    /**
     * Returns the path element at the specified index.
     *
     * <p>
     *  返回指定索引处的路径元素。
     * 
     * 
     * @param index the index of the element requested
     * @return the element at the specified index
     * @throws IllegalArgumentException if the index is outside the
     *         range of this path
     */
    public Object getPathComponent(int index) {
        int          pathLength = getPathCount();

        if(index < 0 || index >= pathLength)
            throw new IllegalArgumentException("Index " + index +
                                           " is out of the specified range");

        TreePath         path = this;

        for(int i = pathLength-1; i != index; i--) {
            path = path.getParentPath();
        }
        return path.getLastPathComponent();
    }

    /**
     * Compares this {@code TreePath} to the specified object. This returns
     * {@code true} if {@code o} is a {@code TreePath} with the exact
     * same elements (as determined by using {@code equals} on each
     * element of the path).
     *
     * <p>
     *  将此{@code TreePath}与指定的对象进行比较。
     * 如果{@code o}是一个{@code TreePath},并且具有完全相同的元素(通过在路径的每个元素上使用{@code equals}确定),这将返回{@code true}。
     * 
     * 
     * @param o the object to compare
     */
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o instanceof TreePath) {
            TreePath            oTreePath = (TreePath)o;

            if(getPathCount() != oTreePath.getPathCount())
                return false;
            for(TreePath path = this; path != null;
                    path = path.getParentPath()) {
                if (!(path.getLastPathComponent().equals
                      (oTreePath.getLastPathComponent()))) {
                    return false;
                }
                oTreePath = oTreePath.getParentPath();
            }
            return true;
        }
        return false;
    }

    /**
     * Returns the hash code of this {@code TreePath}. The hash code of a
     * {@code TreePath} is the hash code of the last element in the path.
     *
     * <p>
     *  返回此{@code TreePath}的哈希码。 {@code TreePath}的哈希码是路径中最后一个元素的哈希码。
     * 
     * 
     * @return the hashCode for the object
     */
    public int hashCode() {
        return getLastPathComponent().hashCode();
    }

    /**
     * Returns true if <code>aTreePath</code> is a
     * descendant of this
     * {@code TreePath}. A {@code TreePath} {@code P1} is a descendant of a
     * {@code TreePath} {@code P2}
     * if {@code P1} contains all of the elements that make up
     * {@code P2's} path.
     * For example, if this object has the path {@code [a, b]},
     * and <code>aTreePath</code> has the path {@code [a, b, c]},
     * then <code>aTreePath</code> is a descendant of this object.
     * However, if <code>aTreePath</code> has the path {@code [a]},
     * then it is not a descendant of this object.  By this definition
     * a {@code TreePath} is always considered a descendant of itself.
     * That is, <code>aTreePath.isDescendant(aTreePath)</code> returns
     * {@code true}.
     *
     * <p>
     * 如果<code> aTreePath </code>是此{@code TreePath}的后代,则返回true。
     * 如果{@code P1}包含构成{@code P2's}路径的所有元素,则{@code TreePath} {@code P1}是{@code TreePath} {@code P2}的后代。
     * 例如,如果此对象具有路径{@code [a,b]},并且<code> aTreePath </code>具有路径{@code [a,b,c]},则<code> aTreePath <代码>是此对象的后代
     * 。
     * 如果{@code P1}包含构成{@code P2's}路径的所有元素,则{@code TreePath} {@code P1}是{@code TreePath} {@code P2}的后代。
     * 但是,如果<code> aTreePath </code>有路径{@code [a]},那么它不是此对象的后代。通过这个定义,{@code TreePath}总是被认为是它自己的后代。
     * 也就是说,<code> aTreePath.isDescendant(aTreePath)</code>返回{@code true}。
     * 
     * 
     * @param aTreePath the {@code TreePath} to check
     * @return true if <code>aTreePath</code> is a descendant of this path
     */
    public boolean isDescendant(TreePath aTreePath) {
        if(aTreePath == this)
            return true;

        if(aTreePath != null) {
            int                 pathLength = getPathCount();
            int                 oPathLength = aTreePath.getPathCount();

            if(oPathLength < pathLength)
                // Can't be a descendant, has fewer components in the path.
                return false;
            while(oPathLength-- > pathLength)
                aTreePath = aTreePath.getParentPath();
            return equals(aTreePath);
        }
        return false;
    }

    /**
     * Returns a new path containing all the elements of this path
     * plus <code>child</code>. <code>child</code> is the last element
     * of the newly created {@code TreePath}.
     *
     * <p>
     *  返回包含此路径的所有元素和<code> child </code>的新路径。 <code> child </code>是新创建的{@code TreePath}的最后一个元素。
     * 
     * 
     * @param child the path element to add
     * @throws NullPointerException if {@code child} is {@code null}
     */
    public TreePath pathByAddingChild(Object child) {
        if(child == null)
            throw new NullPointerException("Null child not allowed");

        return new TreePath(this, child);
    }

    /**
     * Returns the {@code TreePath} of the parent. A return value of
     * {@code null} indicates this is the root node.
     *
     * <p>
     *  返回父级的{@code TreePath}。返回值{@code null}表示这是根节点。
     * 
     * 
     * @return the parent path
     */
    public TreePath getParentPath() {
        return parentPath;
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * @return a String representation of this object
     */
    public String toString() {
        StringBuffer tempSpot = new StringBuffer("[");

        for(int counter = 0, maxCounter = getPathCount();counter < maxCounter;
            counter++) {
            if(counter > 0)
                tempSpot.append(", ");
            tempSpot.append(getPathComponent(counter));
        }
        tempSpot.append("]");
        return tempSpot.toString();
    }
}
