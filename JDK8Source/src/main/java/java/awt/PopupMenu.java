/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

import java.awt.peer.PopupMenuPeer;
import javax.accessibility.*;


import sun.awt.AWTAccessor;

/**
 * A class that implements a menu which can be dynamically popped up
 * at a specified position within a component.
 * <p>
 * As the inheritance hierarchy implies, a <code>PopupMenu</code>
 *  can be used anywhere a <code>Menu</code> can be used.
 * However, if you use a <code>PopupMenu</code> like a <code>Menu</code>
 * (e.g., you add it to a <code>MenuBar</code>), then you <b>cannot</b>
 * call <code>show</code> on that <code>PopupMenu</code>.
 *
 * <p>
 *  实现可以在组件中的指定位置动态弹出的菜单的类。
 * <p>
 *  正如继承层次结构所暗示的,可以在可以使用<code> Menu </code>的任何地方使用<code> PopupMenu </code>。
 * 但是,如果您像<code> Menu </code>(例如,将其添加到<code> MenuBar </code>)中使用<code> PopupMenu </code>,则您<b> b>在<code>
 *  PopupMenu </code>上调用<code> show </code>。
 *  正如继承层次结构所暗示的,可以在可以使用<code> Menu </code>的任何地方使用<code> PopupMenu </code>。
 * 
 * 
 * @author      Amy Fowler
 */
public class PopupMenu extends Menu {

    private static final String base = "popup";
    static int nameCounter = 0;

    transient boolean isTrayIconPopup = false;

    static {
        AWTAccessor.setPopupMenuAccessor(
            new AWTAccessor.PopupMenuAccessor() {
                public boolean isTrayIconPopup(PopupMenu popupMenu) {
                    return popupMenu.isTrayIconPopup;
                }
            });
    }

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4620452533522760060L;

    /**
     * Creates a new popup menu with an empty name.
     * <p>
     *  创建一个带有空名称的新弹出菜单。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public PopupMenu() throws HeadlessException {
        this("");
    }

    /**
     * Creates a new popup menu with the specified name.
     *
     * <p>
     *  创建具有指定名称的新弹出菜单。
     * 
     * 
     * @param label a non-<code>null</code> string specifying
     *                the popup menu's label
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public PopupMenu(String label) throws HeadlessException {
        super(label);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public MenuContainer getParent() {
        if (isTrayIconPopup) {
            return null;
        }
        return super.getParent();
    }

    /**
     * Constructs a name for this <code>MenuComponent</code>.
     * Called by <code>getName</code> when the name is <code>null</code>.
     * <p>
     *  为此<code> MenuComponent </code>构造一个名称。当名称为<code> null </code>时,由<code> getName </code>调用。
     * 
     */
    String constructComponentName() {
        synchronized (PopupMenu.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the popup menu's peer.
     * The peer allows us to change the appearance of the popup menu without
     * changing any of the popup menu's functionality.
     * <p>
     *  创建弹出菜单的对等项。对等体允许我们改变弹出菜单的外观,而不改变任何弹出菜单的功能。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            // If our parent is not a Component, then this PopupMenu is
            // really just a plain, old Menu.
            if (parent != null && !(parent instanceof Component)) {
                super.addNotify();
            }
            else {
                if (peer == null)
                    peer = Toolkit.getDefaultToolkit().createPopupMenu(this);
                int nitems = getItemCount();
                for (int i = 0 ; i < nitems ; i++) {
                    MenuItem mi = getItem(i);
                    mi.parent = this;
                    mi.addNotify();
                }
            }
        }
    }

   /**
     * Shows the popup menu at the x, y position relative to an origin
     * component.
     * The origin component must be contained within the component
     * hierarchy of the popup menu's parent.  Both the origin and the parent
     * must be showing on the screen for this method to be valid.
     * <p>
     * If this <code>PopupMenu</code> is being used as a <code>Menu</code>
     * (i.e., it has a non-<code>Component</code> parent),
     * then you cannot call this method on the <code>PopupMenu</code>.
     *
     * <p>
     *  在相对于原点组件的x,y位置显示弹出菜单。原始组件必须包含在弹出菜单的父组件的组件层次结构中。原点和父代必须在屏幕上显示,以使此方法有效。
     * <p>
     *  如果这个<code> PopupMenu </code>被用作<code> Menu </code>(即它有一个非<code> Component </code> parent),那么你不能在<code>
     *  PopupMenu </code>。
     * 
     * 
     * @param origin the component which defines the coordinate space
     * @param x the x coordinate position to popup the menu
     * @param y the y coordinate position to popup the menu
     * @exception NullPointerException  if the parent is <code>null</code>
     * @exception IllegalArgumentException  if this <code>PopupMenu</code>
     *                has a non-<code>Component</code> parent
     * @exception IllegalArgumentException if the origin is not in the
     *                parent's hierarchy
     * @exception RuntimeException if the parent is not showing on screen
     */
    public void show(Component origin, int x, int y) {
        // Use localParent for thread safety.
        MenuContainer localParent = parent;
        if (localParent == null) {
            throw new NullPointerException("parent is null");
        }
        if (!(localParent instanceof Component)) {
            throw new IllegalArgumentException(
                "PopupMenus with non-Component parents cannot be shown");
        }
        Component compParent = (Component)localParent;
        //Fixed 6278745: Incorrect exception throwing in PopupMenu.show() method
        //Exception was not thrown if compParent was not equal to origin and
        //was not Container
        if (compParent != origin) {
            if (compParent instanceof Container) {
                if (!((Container)compParent).isAncestorOf(origin)) {
                    throw new IllegalArgumentException("origin not in parent's hierarchy");
                }
            } else {
                throw new IllegalArgumentException("origin not in parent's hierarchy");
            }
        }
        if (compParent.getPeer() == null || !compParent.isShowing()) {
            throw new RuntimeException("parent not showing on screen");
        }
        if (peer == null) {
            addNotify();
        }
        synchronized (getTreeLock()) {
            if (peer != null) {
                ((PopupMenuPeer)peer).show(
                    new Event(origin, 0, Event.MOUSE_DOWN, x, y, 0, 0));
            }
        }
    }


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>PopupMenu</code>.
     *
     * <p>
     * 获取与此<code> PopupMenu </code>关联的<code> AccessibleContext </code>。
     * 
     * 
     * @return the <code>AccessibleContext</code> of this
     *                <code>PopupMenu</code>
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTPopupMenu();
        }
        return accessibleContext;
    }

    /**
     * Inner class of PopupMenu used to provide default support for
     * accessibility.  This class is not meant to be used directly by
     * application developers, but is instead meant only to be
     * subclassed by menu component developers.
     * <p>
     * The class used to obtain the accessible role for this object.
     * <p>
     *  PopupMenu的内部类用于提供对辅助功能的默认支持。这个类不是直接由应用程序开发人员使用,而是意味着只能由菜单组件开发人员进行子类化。
     * <p>
     *  用于获取此对象的可访问角色的类。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTPopupMenu extends AccessibleAWTMenu
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -4282044795947239955L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.POPUP_MENU;
        }

    } // class AccessibleAWTPopupMenu

}
