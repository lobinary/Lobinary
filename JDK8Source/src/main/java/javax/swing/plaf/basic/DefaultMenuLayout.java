/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.basic;

import javax.swing.*;
import javax.swing.plaf.UIResource;

import java.awt.Container;
import java.awt.Dimension;

/**
 * The default layout manager for Popup menus and menubars.  This
 * class is an extension of BoxLayout which adds the UIResource tag
 * so that pluggable L&amp;Fs can distinguish it from user-installed
 * layout managers on menus.
 *
 * <p>
 *  弹出菜单和菜单栏的默认布局管理器。这个类是BoxLayout的扩展,它添加了UIResource标签,以便可插入的L&amp; Fs可以将其与菜单上的用户安装的布局管理器区分开来。
 * 
 * @author Georges Saab
 */

public class DefaultMenuLayout extends BoxLayout implements UIResource {
    public DefaultMenuLayout(Container target, int axis) {
        super(target, axis);
    }

    public Dimension preferredLayoutSize(Container target) {
        if (target instanceof JPopupMenu) {
            JPopupMenu popupMenu = (JPopupMenu) target;
            sun.swing.MenuItemLayoutHelper.clearUsedClientProperties(popupMenu);
            if (popupMenu.getComponentCount() == 0) {
                return new Dimension(0, 0);
            }
        }

        // Make BoxLayout recalculate cached preferred sizes
        super.invalidateLayout(target);

        return super.preferredLayoutSize(target);
    }
}
