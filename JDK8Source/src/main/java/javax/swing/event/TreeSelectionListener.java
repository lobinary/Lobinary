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
 * The listener that's notified when the selection in a TreeSelectionModel
 * changes.
 * For more information and examples see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeselectionlistener.html">How to Write a Tree Selection Listener</a>,
 * a section in <em>The Java Tutorial.</em>
 *
 * <p>
 *  在TreeSelectionModel中的选择更改时通知的侦听器。
 * 有关详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeselectionlistener.html">
 * 如何编写树选择监听器</a>, em> Java教程。
 *  在TreeSelectionModel中的选择更改时通知的侦听器。</em>。
 * 
 * @see javax.swing.tree.TreeSelectionModel
 * @see javax.swing.JTree
 *
 * @author Scott Violet
 */
public interface TreeSelectionListener extends EventListener
{
    /**
      * Called whenever the value of the selection changes.
      * <p>
      * 
      * 
      * @param e the event that characterizes the change.
      */
    void valueChanged(TreeSelectionEvent e);
}
