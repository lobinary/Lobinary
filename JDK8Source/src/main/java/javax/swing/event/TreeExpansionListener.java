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
  * The listener that's notified when a tree expands or collapses
  * a node.
  * For further documentation and examples see
  * <a
  href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeexpansionlistener.html">How to Write a Tree Expansion Listener</a>,
  * a section in <em>The Java Tutorial.</em>
  *
  * <p>
  *  树在展开或折叠节点时通知的侦听器。
  * 有关其他文档和示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeexpansionlistener.html">
  * 如何编写树扩展侦听器</a>, em> Java教程。
  *  树在展开或折叠节点时通知的侦听器。</em>。
  * 
  * 
  * @author Scott Violet
  */

public interface TreeExpansionListener extends EventListener
{
    /**
      * Called whenever an item in the tree has been expanded.
      * <p>
      *  当树中的项目已展开时调用。
      * 
      */
    public void treeExpanded(TreeExpansionEvent event);

    /**
      * Called whenever an item in the tree has been collapsed.
      * <p>
      *  当树中的项目已折叠时调用。
      */
    public void treeCollapsed(TreeExpansionEvent event);
}
