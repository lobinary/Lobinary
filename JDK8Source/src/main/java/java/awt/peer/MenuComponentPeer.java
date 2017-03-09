/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Font;
import java.awt.MenuComponent;

/**
 * The base interface for all kinds of menu components. This is used by
 * {@link MenuComponent}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  基本界面用于各种菜单组件。这由{@link MenuComponent}使用。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface MenuComponentPeer {

    /**
     * Disposes the menu component.
     *
     * <p>
     *  布置菜单组件。
     * 
     * 
     * @see MenuComponent#removeNotify()
     */
    void dispose();

    /**
     * Sets the font for the menu component.
     *
     * <p>
     *  设置菜单组件的字体。
     * 
     * @param f the font to use for the menu component
     *
     * @see MenuComponent#setFont(Font)
     */
    void setFont(Font f);
}
