/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * A popup menu listener
 *
 * <p>
 *  弹出菜单侦听器
 * 
 * 
 * @author Arnaud Weber
 */
public interface PopupMenuListener extends EventListener {

    /**
     *  This method is called before the popup menu becomes visible
     * <p>
     *  在弹出菜单变为可见之前调用此方法
     * 
     */
    void popupMenuWillBecomeVisible(PopupMenuEvent e);

    /**
     * This method is called before the popup menu becomes invisible
     * Note that a JPopupMenu can become invisible any time
     * <p>
     *  此方法在弹出菜单变为不可见之前调用请注意,JPopupMenu可以随时变得不可见
     * 
     */
    void popupMenuWillBecomeInvisible(PopupMenuEvent e);

    /**
     * This method is called when the popup menu is canceled
     * <p>
     *  当取消弹出菜单时调用此方法
     */
    void popupMenuCanceled(PopupMenuEvent e);
}
