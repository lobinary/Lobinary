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

import javax.swing.event.*;

/**
 * The model used by <code>JTree</code>.
 * <p>
 * <code>JTree</code> and its related classes make extensive use of
 * <code>TreePath</code>s for identifying nodes in the <code>TreeModel</code>.
 * If a <code>TreeModel</code> returns the same object, as compared by
 * <code>equals</code>, at two different indices under the same parent
 * than the resulting <code>TreePath</code> objects will be considered equal
 * as well. Some implementations may assume that if two
 * <code>TreePath</code>s are equal, they identify the same node. If this
 * condition is not met, painting problems and other oddities may result.
 * In other words, if <code>getChild</code> for a given parent returns
 * the same Object (as determined by <code>equals</code>) problems may
 * result, and it is recommended you avoid doing this.
 * <p>
 * Similarly <code>JTree</code> and its related classes place
 * <code>TreePath</code>s in <code>Map</code>s.  As such if
 * a node is requested twice, the return values must be equal
 * (using the <code>equals</code> method) and have the same
 * <code>hashCode</code>.
 * <p>
 * For further information on tree models,
 * including an example of a custom implementation,
 * see <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
 * in <em>The Java Tutorial.</em>
 *
 * <p>
 *  <code> JTree </code>使用的模型。
 * <p>
 *  <code> JTree </code>及其相关类广泛使用<code> TreePath </code>来标识<code> TreeModel </code>中的节点。
 * 如果<code> TreeModel </code>返回相同的对象,与<code> equals </code>相比,在相同父亲的两个不同索引下,将会考虑<code> TreePath </code>相
 * 等。
 *  <code> JTree </code>及其相关类广泛使用<code> TreePath </code>来标识<code> TreeModel </code>中的节点。
 * 一些实现可以假设如果两个<code> TreePath </code>相等,它们标识相同的节点。如果不满足这个条件,可能会导致喷漆问题和其他问题。
 * 换句话说,如果给定父对象的<code> getChild </code>返回相同的对象(由<code> equals </code>确定),则可能会导致问题,因此建议您避免这样做。
 * <p>
 *  类似地,<code> JTree </code>及其相关类将<code> TreePath </code>放置在<code> Map </code>中。
 * 因此,如果一个节点被请求两次,返回值必须相等(使用<code> equals </code>方法),并具有相同的<code> hashCode </code>。
 * <p>
 *  有关树模型的详细信息(包括自定义实施的示例),请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">
 * 如何使用树< a>在<em> Java教程</em>中。
 * 
 * 
 * @see TreePath
 *
 * @author Rob Davis
 * @author Ray Ryan
 */
public interface TreeModel
{

    /**
     * Returns the root of the tree.  Returns <code>null</code>
     * only if the tree has no nodes.
     *
     * <p>
     *  返回树的根。如果树没有节点,则返回<code> null </code>。
     * 
     * 
     * @return  the root of the tree
     */
    public Object getRoot();


    /**
     * Returns the child of <code>parent</code> at index <code>index</code>
     * in the parent's
     * child array.  <code>parent</code> must be a node previously obtained
     * from this data source. This should not return <code>null</code>
     * if <code>index</code>
     * is a valid index for <code>parent</code> (that is <code>index &gt;= 0 &amp;&amp;
     * index &lt; getChildCount(parent</code>)).
     *
     * <p>
     * 返回父代的子数组中索引<code> index </code>处的<code> parent </code>子代。 <code> parent </code>必须是以前从此数据源获取的节点。
     * 如果<code> index </code>是<code> parent </code>的有效索引(即<code> index&gt; = 0&amp;&amp;&amp;&amp; index),则不
     * 应返回<code> null </code> &lt; getChildCount(parent </code>))。
     * 返回父代的子数组中索引<code> index </code>处的<code> parent </code>子代。 <code> parent </code>必须是以前从此数据源获取的节点。
     * 
     * 
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the child of <code>parent</code> at index <code>index</code>
     */
    public Object getChild(Object parent, int index);


    /**
     * Returns the number of children of <code>parent</code>.
     * Returns 0 if the node
     * is a leaf or if it has no children.  <code>parent</code> must be a node
     * previously obtained from this data source.
     *
     * <p>
     *  返回<code> parent </code>的子项数。如果节点是叶子或没有子节点,则返回0。 <code> parent </code>必须是以前从此数据源获取的节点。
     * 
     * 
     * @param   parent  a node in the tree, obtained from this data source
     * @return  the number of children of the node <code>parent</code>
     */
    public int getChildCount(Object parent);


    /**
     * Returns <code>true</code> if <code>node</code> is a leaf.
     * It is possible for this method to return <code>false</code>
     * even if <code>node</code> has no children.
     * A directory in a filesystem, for example,
     * may contain no files; the node representing
     * the directory is not a leaf, but it also has no children.
     *
     * <p>
     *  如果<code> node </code>是叶子,则返回<code> true </code>。
     * 即使<code> node </code>没有子级,此方法也可能返回<code> false </code>。例如,文件系统中的目录可以不包含文件;表示目录的节点不是叶子,但它也没有子节点。
     * 
     * 
     * @param   node  a node in the tree, obtained from this data source
     * @return  true if <code>node</code> is a leaf
     */
    public boolean isLeaf(Object node);

    /**
      * Messaged when the user has altered the value for the item identified
      * by <code>path</code> to <code>newValue</code>.
      * If <code>newValue</code> signifies a truly new value
      * the model should post a <code>treeNodesChanged</code> event.
      *
      * <p>
      *  当用户将由<code> path </code>标识的项目的值更改为<code> newValue </code>时,消息。
      * 如果<code> newValue </code>表示一个真正的新值,模型应该发布一个<code> treeNodesChanged </code>事件。
      * 
      * 
      * @param path path to the node that the user has altered
      * @param newValue the new value from the TreeCellEditor
      */
    public void valueForPathChanged(TreePath path, Object newValue);

    /**
     * Returns the index of child in parent.  If either <code>parent</code>
     * or <code>child</code> is <code>null</code>, returns -1.
     * If either <code>parent</code> or <code>child</code> don't
     * belong to this tree model, returns -1.
     *
     * <p>
     *  返回父级中的子级的索引。如果<code> parent </code>或<code> child </code>为<code> null </code>,则返回-1。
     * 如果<code> parent </code>或<code> child </code>不属于此树模型,则返回-1。
     * 
     * 
     * @param parent a node in the tree, obtained from this data source
     * @param child the node we are interested in
     * @return the index of the child in the parent, or -1 if either
     *    <code>child</code> or <code>parent</code> are <code>null</code>
     *    or don't belong to this tree model
     */
    public int getIndexOfChild(Object parent, Object child);

//
//  Change Events
//

    /**
     * Adds a listener for the <code>TreeModelEvent</code>
     * posted after the tree changes.
     *
     * <p>
     *  为在树更改后发布的<code> TreeModelEvent </code>添加侦听器。
     * 
     * 
     * @param   l       the listener to add
     * @see     #removeTreeModelListener
     */
    void addTreeModelListener(TreeModelListener l);

    /**
     * Removes a listener previously added with
     * <code>addTreeModelListener</code>.
     *
     * <p>
     *  删除先前使用<code> addTreeModelListener </code>添加的侦听器。
     * 
     * @see     #addTreeModelListener
     * @param   l       the listener to remove
     */
    void removeTreeModelListener(TreeModelListener l);

}
