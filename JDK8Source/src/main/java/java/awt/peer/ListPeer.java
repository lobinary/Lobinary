/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 1998, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Dimension;
import java.awt.List;

/**
 * The peer interface for {@link List}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link List}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface ListPeer extends ComponentPeer {

    /**
     * Returns the indices of the list items that are currently selected.
     * The returned array is not required to be a copy, the callers of this
     * method already make sure it is not modified.
     *
     * <p>
     *  返回当前选择的列表项的索引。返回的数组不需要是一个副本,这个方法的调用者已经确保它不被修改。
     * 
     * 
     * @return the indices of the list items that are currently selected
     *
     * @see List#getSelectedIndexes()
     */
    int[] getSelectedIndexes();

    /**
     * Adds an item to the list at the specified index.
     *
     * <p>
     *  将项目添加到指定索引的列表中。
     * 
     * 
     * @param item the item to add to the list
     * @param index the index where to add the item into the list
     *
     * @see List#add(String, int)
     */
    void add(String item, int index);

    /**
     * Deletes items from the list. All items from start to end should are
     * deleted, including the item at the start and end indices.
     *
     * <p>
     *  从列表中删除项目。从开始到结束的所有项目都应删除,包括开始和结束索引处的项目。
     * 
     * 
     * @param start the first item to be deleted
     * @param end the last item to be deleted
     */
    void delItems(int start, int end);

    /**
     * Removes all items from the list.
     *
     * <p>
     *  从列表中删除所有项目。
     * 
     * 
     * @see List#removeAll()
     */
    void removeAll();

    /**
     * Selects the item at the specified {@code index}.
     *
     * <p>
     *  在指定的{@code index}处选择项目。
     * 
     * 
     * @param index the index of the item to select
     *
     * @see List#select(int)
     */
    void select(int index);

    /**
     * De-selects the item at the specified {@code index}.
     *
     * <p>
     *  取消选择指定的{@code index}处的项目。
     * 
     * 
     * @param index the index of the item to de-select
     *
     * @see List#deselect(int)
     */
    void deselect(int index);

    /**
     * Makes sure that the item at the specified {@code index} is visible,
     * by scrolling the list or similar.
     *
     * <p>
     *  通过滚动列表或类似内容,确保指定的{@code index}上的项目可见。
     * 
     * 
     * @param index the index of the item to make visible
     *
     * @see List#makeVisible(int)
     */
    void makeVisible(int index);

    /**
     * Toggles multiple selection mode on or off.
     *
     * <p>
     *  打开或关闭多重选择模式。
     * 
     * 
     * @param m {@code true} for multiple selection mode,
     *        {@code false} for single selection mode
     *
     * @see List#setMultipleMode(boolean)
     */
    void setMultipleMode(boolean m);

    /**
     * Returns the preferred size for a list with the specified number of rows.
     *
     * <p>
     *  返回具有指定行数的列表的首选大小。
     * 
     * 
     * @param rows the number of rows
     *
     * @return the preferred size of the list
     *
     * @see List#getPreferredSize(int)
     */
    Dimension getPreferredSize(int rows);

    /**
     * Returns the minimum size for a list with the specified number of rows.
     *
     * <p>
     *  返回具有指定行数的列表的最小大小。
     * 
     * @param rows the number of rows
     *
     * @return the minimum size of the list
     *
     * @see List#getMinimumSize(int)
     */
    Dimension getMinimumSize(int rows);

}
