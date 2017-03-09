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

import java.awt.MenuItem;

/**
 * The peer interface for menu items. This is used by {@link MenuItem}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  菜单项的对等接口。这是{@link MenuItem}使用的。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface MenuItemPeer extends MenuComponentPeer {

    /**
     * Sets the label to be displayed in this menu item.
     *
     * <p>
     *  设置要在此菜单项中显示的标签。
     * 
     * 
     * @param label the label to be displayed
     */
    void setLabel(String label);

    /**
     * Enables or disables the menu item.
     *
     * <p>
     *  启用或禁用菜单项。
     * 
     * @param e {@code true} to enable the menu item, {@code false}
     *        to disable it
     */
    void setEnabled(boolean e);

}
