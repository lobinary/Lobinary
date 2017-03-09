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

package javax.swing.event;

import java.util.EventListener;

/**
 * Defines the interface for an object that listens
 * to changes in a TreeModel.
 * For further information and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html">How to Write a Tree Model Listener</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 *  定义侦听TreeModel中的更改的对象的接口。
 * 有关详细信息和示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treemodellistener.html">
 * 如何编写树模型侦听器</a>, em> Java教程。
 *  定义侦听TreeModel中的更改的对象的接口。</em>。
 * 
 * 
 * @author Rob Davis
 * @author Ray Ryan
 */
public interface TreeModelListener extends EventListener {

    /**
     * <p>Invoked after a node (or a set of siblings) has changed in some
     * way. The node(s) have not changed locations in the tree or
     * altered their children arrays, but other attributes have
     * changed and may affect presentation. Example: the name of a
     * file has changed, but it is in the same location in the file
     * system.</p>
     * <p>To indicate the root has changed, childIndices and children
     * will be null. </p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the parent of the changed node(s).
     * <code>e.getChildIndices()</code>
     * returns the index(es) of the changed node(s).</p>
     * <p>
     *  <p>在某个节点(或一组兄弟节点)以某种方式更改后调用。节点没有改变树中的位置或改变它们的子数组,但是其他属性已经改变并且可能影响呈现。示例：文件的名称已更改,但它位于文件系统中的相同位置。
     * </p> <p>为了指示根已更改,childIndices和children将为null。 </p>。
     * 
     *  <p>使用<code> e.getPath()</code>获得更改的节点的父级。 <code> e.getChildIndices()</code>返回更改的节点的索引。</p>
     * 
     */
    void treeNodesChanged(TreeModelEvent e);

    /**
     * <p>Invoked after nodes have been inserted into the tree.</p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the parent of the new node(s).
     * <code>e.getChildIndices()</code>
     * returns the index(es) of the new node(s)
     * in ascending order.</p>
     * <p>
     *  <p>在树已插入节点后调用。</p>
     * 
     *  <p>使用<code> e.getPath()</code>获取新节点的父级。 <code> e.getChildIndices()</code>以升序返回新节点的索引。</p>
     * 
     */
    void treeNodesInserted(TreeModelEvent e);

    /**
     * <p>Invoked after nodes have been removed from the tree.  Note that
     * if a subtree is removed from the tree, this method may only be
     * invoked once for the root of the removed subtree, not once for
     * each individual set of siblings removed.</p>
     *
     * <p>Use <code>e.getPath()</code>
     * to get the former parent of the deleted node(s).
     * <code>e.getChildIndices()</code>
     * returns, in ascending order, the index(es)
     * the node(s) had before being deleted.</p>
     * <p>
     *  <p>在从树中删除节点后调用。注意,如果从树中删除子树,则该方法对于删除的子树的根可以只被调用一次,而对于去除的每个兄弟姐妹的每个集合不能被调用一次。</p>
     * 
     * <p>使用<code> e.getPath()</code>获得已删除节点的前父节点。 <code> e.getChildIndices()</code>以升序返回节点在删除之前的索引。</p>
     * 
     */
    void treeNodesRemoved(TreeModelEvent e);

    /**
     * <p>Invoked after the tree has drastically changed structure from a
     * given node down.  If the path returned by e.getPath() is of length
     * one and the first element does not identify the current root node
     * the first element should become the new root of the tree.
     *
     * <p>Use <code>e.getPath()</code>
     * to get the path to the node.
     * <code>e.getChildIndices()</code>
     * returns null.</p>
     * <p>
     *  <p>在树已从给定节点向下大幅更改结构后调用。如果e.getPath()返回的路径长度为1,并且第一个元素不标识当前根节点,则第一个元素应该成为树的新根。
     * 
     */
    void treeStructureChanged(TreeModelEvent e);

}
