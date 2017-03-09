/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

/**
 * <code>RowSorterListener</code>s are notified of changes to a
 * <code>RowSorter</code>.
 *
 * <p>
 *  <code> RowSorterListener </code>会通知对<code> RowSorter </code>的更改。
 * 
 * 
 * @see javax.swing.RowSorter
 * @since 1.6
 */
public interface RowSorterListener extends java.util.EventListener {
    /**
     * Notification that the <code>RowSorter</code> has changed.  The event
     * describes the scope of the change.
     *
     * <p>
     *  通知<code> RowSorter </code>已更改。事件描述更改的范围。
     * 
     * @param e the event, will not be null
     */
    public void sorterChanged(RowSorterEvent e);
}
