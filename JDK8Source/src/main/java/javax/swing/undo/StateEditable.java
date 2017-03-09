/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.undo;

import java.util.Hashtable;


/**
 * StateEditable defines the interface for objects that can have
 * their state undone/redone by a StateEdit.
 *
 * <p>
 *  StateEditable定义了一个对象的接口,这些对象可以通过StateEdit来撤销/重做它们的状态。
 * 
 * 
 * @see StateEdit
 */

public interface StateEditable {

    /** Resource ID for this class. */
    public static final String RCSID = "$Id: StateEditable.java,v 1.2 1997/09/08 19:39:08 marklin Exp $";

    /**
     * Upon receiving this message the receiver should place any relevant
     * state into <EM>state</EM>.
     * <p>
     *  在接收到该消息时,接收器应将任何相关状态置于<EM>状态</EM>。
     * 
     */
    public void storeState(Hashtable<Object,Object> state);

    /**
     * Upon receiving this message the receiver should extract any relevant
     * state out of <EM>state</EM>.
     * <p>
     *  在接收到该消息时,接收器应提取<EM>状态</EM>之外的任何相关状态。
     */
    public void restoreState(Hashtable<?,?> state);
} // End of interface StateEditable
