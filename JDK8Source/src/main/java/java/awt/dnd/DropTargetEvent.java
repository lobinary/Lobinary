/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd;

import java.util.EventObject;
import java.awt.dnd.DropTargetContext;

/**
 * The <code>DropTargetEvent</code> is the base
 * class for both the <code>DropTargetDragEvent</code>
 * and the <code>DropTargetDropEvent</code>.
 * It encapsulates the current state of the Drag and
 * Drop operations, in particular the current
 * <code>DropTargetContext</code>.
 *
 * <p>
 *  <code> DropTargetEvent </code>是<code> DropTargetDragEvent </code>和<code> DropTargetDropEvent </code>
 * 的基类。
 * 它封装了拖放操作的当前状态,特别是当前<code> DropTargetContext </code>。
 * 
 * 
 * @since 1.2
 *
 */

public class DropTargetEvent extends java.util.EventObject {

    private static final long serialVersionUID = 2821229066521922993L;

    /**
     * Construct a <code>DropTargetEvent</code> object with
     * the specified <code>DropTargetContext</code>.
     * <P>
     * <p>
     *  使用指定的<code> DropTargetContext </code>构造一个<code> DropTargetEvent </code>对象。
     * <P>
     * 
     * @param dtc The <code>DropTargetContext</code>
     * @throws NullPointerException if {@code dtc} equals {@code null}.
     * @see #getSource()
     * @see #getDropTargetContext()
     */

    public DropTargetEvent(DropTargetContext dtc) {
        super(dtc.getDropTarget());

        context  = dtc;
    }

    /**
     * This method returns the <code>DropTargetContext</code>
     * associated with this <code>DropTargetEvent</code>.
     * <P>
     * <p>
     *  此方法返回与此<code> DropTargetEvent </code>关联的<code> DropTargetContext </code>。
     * <P>
     * 
     * @return the <code>DropTargetContext</code>
     */

    public DropTargetContext getDropTargetContext() {
        return context;
    }

    /**
     * The <code>DropTargetContext</code> associated with this
     * <code>DropTargetEvent</code>.
     *
     * <p>
     *  与此<code> DropTargetEvent </code>关联的<code> DropTargetContext </code>。
     * 
     * @serial
     */
    protected DropTargetContext   context;
}
