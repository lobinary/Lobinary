/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2007, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Choice;

/**
 * The peer interface for {@link Choice}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Choice}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface ChoicePeer extends ComponentPeer {

    /**
     * Adds an item with the string {@code item} to the combo box list
     * at index {@code index}.
     *
     * <p>
     *  将一个带有字符串{@code item}的项目添加到索引为{@code index}的组合框列表中。
     * 
     * 
     * @param item the label to be added to the list
     * @param index the index where to add the item
     *
     * @see Choice#add(String)
     */
    void add(String item, int index);

    /**
     * Removes the item at index {@code index} from the combo box list.
     *
     * <p>
     *  从组合框列表中删除索引{@code index}处的项目。
     * 
     * 
     * @param index the index where to remove the item
     *
     * @see Choice#remove(int)
     */
    void remove(int index);

    /**
     * Removes all items from the combo box list.
     *
     * <p>
     *  从组合框列表中删除所有项目。
     * 
     * 
     * @see Choice#removeAll()
     */
    void removeAll();

    /**
     * Selects the item at index {@code index}.
     *
     * <p>
     *  选择索引{@code index}处的项目。
     * 
     * @param index the index which should be selected
     *
     * @see Choice#select(int)
     */
    void select(int index);

}
