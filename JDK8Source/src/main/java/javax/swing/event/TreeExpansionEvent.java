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

import java.util.EventObject;
import javax.swing.tree.TreePath;

/**
 * An event used to identify a single path in a tree.  The source
 * returned by <b>getSource</b> will be an instance of JTree.
 * <p>
 * For further documentation and examples see
 * the following sections in <em>The Java Tutorial</em>:
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeexpansionlistener.html">How to Write a Tree Expansion Listener</a> and
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">How to Write a Tree-Will-Expand Listener</a>.
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
 *  用于标识树中单个路径的事件。由<b> getSource </b>返回的源将是JTree的实例。
 * <p>
 *  有关其他文档和示例,请参阅<em> Java教程</em>中的以下部分：<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treeexpansionlistener.html">
 * 写树扩展侦听器</a>和<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">
 * 如何编写树 - 将展开侦听器< / a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Scott Violet
 */
public class TreeExpansionEvent extends EventObject
{
    /**
      * Path to the value this event represents.
      * <p>
      */
    protected TreePath              path;

    /**
     * Constructs a TreeExpansionEvent object.
     *
     * <p>
     *  此事件表示的值的路径。
     * 
     * 
     * @param source  the Object that originated the event
     *                (typically <code>this</code>)
     * @param path    a TreePath object identifying the newly expanded
     *                node
     */
    public TreeExpansionEvent(Object source, TreePath path) {
        super(source);
        this.path = path;
    }

    /**
      * Returns the path to the value that has been expanded/collapsed.
      * <p>
      *  构造一个TreeExpansionEvent对象。
      * 
      */
    public TreePath getPath() { return path; }
}
