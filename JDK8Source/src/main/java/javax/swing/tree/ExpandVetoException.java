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

package javax.swing.tree;

import javax.swing.event.TreeExpansionEvent;

/**
 * Exception used to stop and expand/collapse from happening.
 * See <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">How to Write a Tree-Will-Expand Listener</a>
 * in <em>The Java Tutorial</em>
 * for further information and examples.
 *
 * <p>
 *  异常用于停止和展开/崩溃发生。
 * 请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/events/treewillexpandlistener.html">如何编写树
 *  - 将展开侦听器</a> in the Java Tutorial </em>了解更多信息和示例。
 *  异常用于停止和展开/崩溃发生。
 * 
 * 
 * @author Scott Violet
 */
public class ExpandVetoException extends Exception {
    /** The event that the exception was created for. */
    protected TreeExpansionEvent      event;

    /**
     * Constructs an ExpandVetoException object with no message.
     *
     * <p>
     *  构造没有消息的ExpandVetoException对象。
     * 
     * 
     * @param event  a TreeExpansionEvent object
     */

    public ExpandVetoException(TreeExpansionEvent event) {
        this(event, null);
    }

    /**
     * Constructs an ExpandVetoException object with the specified message.
     *
     * <p>
     *  构造具有指定消息的ExpandVetoException对象。
     * 
     * @param event    a TreeExpansionEvent object
     * @param message  a String containing the message
     */
    public ExpandVetoException(TreeExpansionEvent event, String message) {
        super(message);
        this.event = event;
    }
}
