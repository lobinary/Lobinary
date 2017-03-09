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

import java.awt.Menu;
import java.awt.MenuBar;

/**
 * The peer interface for {@link MenuBar}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link MenuBar}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface MenuBarPeer extends MenuComponentPeer {

    /**
     * Adds a menu to the menu bar.
     *
     * <p>
     *  将菜单添加到菜单栏。
     * 
     * 
     * @param m the menu to add
     *
     * @see MenuBar#add(Menu)
     */
    void addMenu(Menu m);

    /**
     * Deletes a menu from the menu bar.
     *
     * <p>
     *  从菜单栏中删除菜单。
     * 
     * 
     * @param index the index of the menu to remove
     *
     * @see MenuBar#remove(int)
     */
    void delMenu(int index);

    /**
     * Adds a help menu to the menu bar.
     *
     * <p>
     *  将帮助菜单添加到菜单栏。
     * 
     * @param m the help menu to add
     *
     * @see MenuBar#setHelpMenu(Menu)
     */
    void addHelpMenu(Menu m);
}
