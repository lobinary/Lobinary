/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * ListDataListener
 *
 * <p>
 *  ListDataListener
 * 
 * 
 * @author Hans Muller
 */
public interface ListDataListener extends EventListener {

    /**
     * Sent after the indices in the index0,index1
     * interval have been inserted in the data model.
     * The new interval includes both index0 and index1.
     *
     * <p>
     *  发送后索引index0,index1的间隔已经插入到数据模型中。新的间隔包括index0和index1。
     * 
     * 
     * @param e  a <code>ListDataEvent</code> encapsulating the
     *    event information
     */
    void intervalAdded(ListDataEvent e);


    /**
     * Sent after the indices in the index0,index1 interval
     * have been removed from the data model.  The interval
     * includes both index0 and index1.
     *
     * <p>
     *  发送后索引index0,index1的间隔已经从数据模型中删除。间隔包括index0和index1。
     * 
     * 
     * @param e  a <code>ListDataEvent</code> encapsulating the
     *    event information
     */
    void intervalRemoved(ListDataEvent e);


    /**
     * Sent when the contents of the list has changed in a way
     * that's too complex to characterize with the previous
     * methods. For example, this is sent when an item has been
     * replaced. Index0 and index1 bracket the change.
     *
     * <p>
     *  当列表的内容以太复杂而无法用以前的方法进行表征的方式更改时发送。例如,当项目已被替换时发送。索引0和索引1括起来的变化。
     * 
     * @param e  a <code>ListDataEvent</code> encapsulating the
     *    event information
     */
    void contentsChanged(ListDataEvent e);
}
