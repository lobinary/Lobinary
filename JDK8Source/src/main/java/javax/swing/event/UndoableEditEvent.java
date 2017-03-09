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

import javax.swing.undo.*;

/**
 * An event indicating that an operation which can be undone has occurred.
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
 *  指示已经发生可以撤销的操作的事件。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Ray Ryan
 */
public class UndoableEditEvent extends java.util.EventObject {
    private UndoableEdit myEdit;

    /**
     * Constructs an UndoableEditEvent object.
     *
     * <p>
     *  构造一个UndoableEditEvent对象。
     * 
     * 
     * @param source  the Object that originated the event
     *                (typically <code>this</code>)
     * @param edit    an UndoableEdit object
     */
    public UndoableEditEvent(Object source, UndoableEdit edit) {
        super(source);
        myEdit = edit;
    }

    /**
     * Returns the edit value.
     *
     * <p>
     *  返回编辑值。
     * 
     * @return the UndoableEdit object encapsulating the edit
     */
    public UndoableEdit getEdit() {
        return myEdit;
    }
}
