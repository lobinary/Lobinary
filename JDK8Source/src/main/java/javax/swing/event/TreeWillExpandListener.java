/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
import javax.swing.tree.ExpandVetoException;

/**
  * The listener that's notified when a tree expands or collapses
  * a node.
  * For further information and examples see
  * <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">How to Write a Tree-Will-Expand Listener</a>,
  * a section in <em>The Java Tutorial.</em>
  *
  * <p>
  *  树在展开或折叠节点时通知的侦听器。
  * 有关详细信息和示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">
  * 如何编写树 - 将展开侦听器</a>, Java教程</em>中的<em>部分。
  *  树在展开或折叠节点时通知的侦听器。
  * 
  * 
  * @author Scott Violet
  */

public interface TreeWillExpandListener extends EventListener {
    /**
     * Invoked whenever a node in the tree is about to be expanded.
     * <p>
     *  当树中的节点将要扩展时调用。
     * 
     */
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException;

    /**
     * Invoked whenever a node in the tree is about to be collapsed.
     * <p>
     *  当树中的节点即将被折叠时调用。
     */
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException;
}
