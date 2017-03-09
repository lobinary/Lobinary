/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import java.util.Enumeration;
import sun.awt.AWTAccessor;
import java.awt.peer.MenuBarPeer;
import java.awt.event.KeyEvent;
import javax.accessibility.*;

/**
 * The <code>MenuBar</code> class encapsulates the platform's
 * concept of a menu bar bound to a frame. In order to associate
 * the menu bar with a <code>Frame</code> object, call the
 * frame's <code>setMenuBar</code> method.
 * <p>
 * <A NAME="mbexample"></A><!-- target for cross references -->
 * This is what a menu bar might look like:
 * <p>
 * <img src="doc-files/MenuBar-1.gif"
 * alt="Diagram of MenuBar containing 2 menus: Examples and Options.
 * Examples menu is expanded showing items: Basic, Simple, Check, and More Examples."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * A menu bar handles keyboard shortcuts for menu items, passing them
 * along to its child menus.
 * (Keyboard shortcuts, which are optional, provide the user with
 * an alternative to the mouse for invoking a menu item and the
 * action that is associated with it.)
 * Each menu item can maintain an instance of <code>MenuShortcut</code>.
 * The <code>MenuBar</code> class defines several methods,
 * {@link MenuBar#shortcuts} and
 * {@link MenuBar#getShortcutMenuItem}
 * that retrieve information about the shortcuts a given
 * menu bar is managing.
 *
 * <p>
 *  <code> MenuBar </code>类封装了平台的绑定到框架的菜单栏的概念。
 * 为了将菜单栏与<code> Frame </code>对象相关联,请调用框架的<code> setMenuBar </code>方法。
 * <p>
 *  <A NAME="mbexample"> </A> <！ - 交叉引用的目标 - >这是菜单栏可能如下所示：
 * <p>
 *  <img src ="doc-files / MenuBar-1.gif"alt ="包含2个菜单的MenuBar图：示例和选项。示例菜单被展开显示项目：基本,简单,检查和更多示例。
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  菜单栏处理菜单项的键盘快捷键,将其传递到其子菜单。 (键盘快捷键是可选的,为用户提供了用于调用菜单项和与其相关联的动作的替代鼠标)。
 * 每个菜单项可以维护<code> MenuShortcut </code>的实例。
 *  <code> MenuBar </code>类定义了几个方法,{@link MenuBar#shortcuts}和{@link MenuBar#getShortcutMenuItem},用于检索有关菜
 * 单栏正在管理的快捷方式的信息。
 * 每个菜单项可以维护<code> MenuShortcut </code>的实例。
 * 
 * 
 * @author Sami Shaio
 * @see        java.awt.Frame
 * @see        java.awt.Frame#setMenuBar(java.awt.MenuBar)
 * @see        java.awt.Menu
 * @see        java.awt.MenuItem
 * @see        java.awt.MenuShortcut
 * @since      JDK1.0
 */
public class MenuBar extends MenuComponent implements MenuContainer, Accessible {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
        AWTAccessor.setMenuBarAccessor(
            new AWTAccessor.MenuBarAccessor() {
                public Menu getHelpMenu(MenuBar menuBar) {
                    return menuBar.helpMenu;
                }

                public Vector<Menu> getMenus(MenuBar menuBar) {
                    return menuBar.menus;
                }
            });
    }

    /**
     * This field represents a vector of the
     * actual menus that will be part of the MenuBar.
     *
     * <p>
     *  此字段表示将成为MenuBar的一部分的实际菜单的向量。
     * 
     * 
     * @serial
     * @see #countMenus()
     */
    Vector<Menu> menus = new Vector<>();

    /**
     * This menu is a special menu dedicated to
     * help.  The one thing to note about this menu
     * is that on some platforms it appears at the
     * right edge of the menubar.
     *
     * <p>
     *  这个菜单是一个专门的帮助菜单。关于这个菜单的一个要注意的是,在一些平台上,它出现在菜单的右边缘。
     * 
     * 
     * @serial
     * @see #getHelpMenu()
     * @see #setHelpMenu(Menu)
     */
    Menu helpMenu;

    private static final String base = "menubar";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -4930327919388951260L;

    /**
     * Creates a new menu bar.
     * <p>
     *  创建一个新的菜单栏。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public MenuBar() throws HeadlessException {
    }

    /**
     * Construct a name for this MenuComponent.  Called by getName() when
     * the name is null.
     * <p>
     * 为此MenuComponent构造名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (MenuBar.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the menu bar's peer.  The peer allows us to change the
     * appearance of the menu bar without changing any of the menu bar's
     * functionality.
     * <p>
     *  创建菜单栏的对等项。对等体允许我们改变菜单栏的外观,而不改变任何菜单栏的功能。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = Toolkit.getDefaultToolkit().createMenuBar(this);

            int nmenus = getMenuCount();
            for (int i = 0 ; i < nmenus ; i++) {
                getMenu(i).addNotify();
            }
        }
    }

    /**
     * Removes the menu bar's peer.  The peer allows us to change the
     * appearance of the menu bar without changing any of the menu bar's
     * functionality.
     * <p>
     *  删除菜单栏的对等项。对等体允许我们改变菜单栏的外观,而不改变任何菜单栏的功能。
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            int nmenus = getMenuCount();
            for (int i = 0 ; i < nmenus ; i++) {
                getMenu(i).removeNotify();
            }
            super.removeNotify();
        }
    }

    /**
     * Gets the help menu on the menu bar.
     * <p>
     *  获取菜单栏上的帮助菜单。
     * 
     * 
     * @return    the help menu on this menu bar.
     */
    public Menu getHelpMenu() {
        return helpMenu;
    }

    /**
     * Sets the specified menu to be this menu bar's help menu.
     * If this menu bar has an existing help menu, the old help menu is
     * removed from the menu bar, and replaced with the specified menu.
     * <p>
     *  将指定的菜单设置为此菜单栏的帮助菜单。如果此菜单栏具有现有帮助菜单,则旧帮助菜单将从菜单栏中删除,并替换为指定的菜单。
     * 
     * 
     * @param m    the menu to be set as the help menu
     */
    public void setHelpMenu(Menu m) {
        synchronized (getTreeLock()) {
            if (helpMenu == m) {
                return;
            }
            if (helpMenu != null) {
                remove(helpMenu);
            }
            if (m.parent != this) {
                add(m);
            }
            helpMenu = m;
            if (m != null) {
                m.isHelpMenu = true;
                m.parent = this;
                MenuBarPeer peer = (MenuBarPeer)this.peer;
                if (peer != null) {
                    if (m.peer == null) {
                        m.addNotify();
                    }
                    peer.addHelpMenu(m);
                }
            }
        }
    }

    /**
     * Adds the specified menu to the menu bar.
     * If the menu has been part of another menu bar,
     * removes it from that menu bar.
     *
     * <p>
     *  将指定的菜单添加到菜单栏。如果菜单已经是另一个菜单栏的一部分,则从该菜单栏中删除它。
     * 
     * 
     * @param        m   the menu to be added
     * @return       the menu added
     * @see          java.awt.MenuBar#remove(int)
     * @see          java.awt.MenuBar#remove(java.awt.MenuComponent)
     */
    public Menu add(Menu m) {
        synchronized (getTreeLock()) {
            if (m.parent != null) {
                m.parent.remove(m);
            }
            menus.addElement(m);
            m.parent = this;

            MenuBarPeer peer = (MenuBarPeer)this.peer;
            if (peer != null) {
                if (m.peer == null) {
                    m.addNotify();
                }
                peer.addMenu(m);
            }
            return m;
        }
    }

    /**
     * Removes the menu located at the specified
     * index from this menu bar.
     * <p>
     *  从此菜单栏中删除位于指定索引处的菜单。
     * 
     * 
     * @param        index   the position of the menu to be removed.
     * @see          java.awt.MenuBar#add(java.awt.Menu)
     */
    public void remove(int index) {
        synchronized (getTreeLock()) {
            Menu m = getMenu(index);
            menus.removeElementAt(index);
            MenuBarPeer peer = (MenuBarPeer)this.peer;
            if (peer != null) {
                m.removeNotify();
                m.parent = null;
                peer.delMenu(index);
            }
        }
    }

    /**
     * Removes the specified menu component from this menu bar.
     * <p>
     *  从此菜单栏中删除指定的菜单组件。
     * 
     * 
     * @param        m the menu component to be removed.
     * @see          java.awt.MenuBar#add(java.awt.Menu)
     */
    public void remove(MenuComponent m) {
        synchronized (getTreeLock()) {
            int index = menus.indexOf(m);
            if (index >= 0) {
                remove(index);
            }
        }
    }

    /**
     * Gets the number of menus on the menu bar.
     * <p>
     *  获取菜单栏上的菜单数。
     * 
     * 
     * @return     the number of menus on the menu bar.
     * @since      JDK1.1
     */
    public int getMenuCount() {
        return countMenus();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getMenuCount()</code>.
     */
    @Deprecated
    public int countMenus() {
        return getMenuCountImpl();
    }

    /*
     * This is called by the native code, so client code can't
     * be called on the toolkit thread.
     * <p>
     *  这是由本地代码调用的,所以客户端代码不能在工具包线程上调用。
     * 
     */
    final int getMenuCountImpl() {
        return menus.size();
    }

    /**
     * Gets the specified menu.
     * <p>
     *  获取指定的菜单。
     * 
     * 
     * @param      i the index position of the menu to be returned.
     * @return     the menu at the specified index of this menu bar.
     */
    public Menu getMenu(int i) {
        return getMenuImpl(i);
    }

    /*
     * This is called by the native code, so client code can't
     * be called on the toolkit thread.
     * <p>
     *  这是由本地代码调用的,所以客户端代码不能在工具包线程上调用。
     * 
     */
    final Menu getMenuImpl(int i) {
        return menus.elementAt(i);
    }

    /**
     * Gets an enumeration of all menu shortcuts this menu bar
     * is managing.
     * <p>
     *  获取此菜单栏正在管理的所有菜单快捷方式的枚举。
     * 
     * 
     * @return      an enumeration of menu shortcuts that this
     *                      menu bar is managing.
     * @see         java.awt.MenuShortcut
     * @since       JDK1.1
     */
    public synchronized Enumeration<MenuShortcut> shortcuts() {
        Vector<MenuShortcut> shortcuts = new Vector<>();
        int nmenus = getMenuCount();
        for (int i = 0 ; i < nmenus ; i++) {
            Enumeration<MenuShortcut> e = getMenu(i).shortcuts();
            while (e.hasMoreElements()) {
                shortcuts.addElement(e.nextElement());
            }
        }
        return shortcuts.elements();
    }

    /**
     * Gets the instance of <code>MenuItem</code> associated
     * with the specified <code>MenuShortcut</code> object,
     * or <code>null</code> if none of the menu items being managed
     * by this menu bar is associated with the specified menu
     * shortcut.
     * <p>
     *  获取与指定的<code> MenuShortcut </code>对象相关联的<code> MenuItem </code>的实例,如果没有由此菜单栏管理的菜单项与指定的菜单快捷方式。
     * 
     * 
     * @param        s the specified menu shortcut.
     * @see          java.awt.MenuItem
     * @see          java.awt.MenuShortcut
     * @since        JDK1.1
     */
     public MenuItem getShortcutMenuItem(MenuShortcut s) {
        int nmenus = getMenuCount();
        for (int i = 0 ; i < nmenus ; i++) {
            MenuItem mi = getMenu(i).getShortcutMenuItem(s);
            if (mi != null) {
                return mi;
            }
        }
        return null;  // MenuShortcut wasn't found
     }

    /*
     * Post an ACTION_EVENT to the target of the MenuPeer
     * associated with the specified keyboard event (on
     * keydown).  Returns true if there is an associated
     * keyboard event.
     * <p>
     * 向与指定键盘事件相关联的MenuPeer的目标发出ACTION_EVENT(在键下)。如果存在相关联的键盘事件,则返回true。
     * 
     */
    boolean handleShortcut(KeyEvent e) {
        // Is it a key event?
        int id = e.getID();
        if (id != KeyEvent.KEY_PRESSED && id != KeyEvent.KEY_RELEASED) {
            return false;
        }

        // Is the accelerator modifier key pressed?
        int accelKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        if ((e.getModifiers() & accelKey) == 0) {
            return false;
        }

        // Pass MenuShortcut on to child menus.
        int nmenus = getMenuCount();
        for (int i = 0 ; i < nmenus ; i++) {
            Menu m = getMenu(i);
            if (m.handleShortcut(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes the specified menu shortcut.
     * <p>
     *  删除指定的菜单快捷方式。
     * 
     * 
     * @param     s the menu shortcut to delete.
     * @since     JDK1.1
     */
    public void deleteShortcut(MenuShortcut s) {
        int nmenus = getMenuCount();
        for (int i = 0 ; i < nmenus ; i++) {
            getMenu(i).deleteShortcut(s);
        }
    }

    /* Serialization support.  Restore the (transient) parent
     * fields of Menubar menus here.
     * <p>
     *  菜单栏目在这里。
     * 
     */

    /**
     * The MenuBar's serialized data version.
     *
     * <p>
     *  MenuBar的序列化数据版本。
     * 
     * 
     * @serial
     */
    private int menuBarSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.
     *
     * <p>
     *  将缺省可序列化字段写入流。
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see #readObject(java.io.ObjectInputStream)
     */
    private void writeObject(java.io.ObjectOutputStream s)
      throws java.lang.ClassNotFoundException,
             java.io.IOException
    {
      s.defaultWriteObject();
    }

    /**
     * Reads the <code>ObjectInputStream</code>.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     *  读取<code> ObjectInputStream </code>。无法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject(java.io.ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
      // HeadlessException will be thrown from MenuComponent's readObject
      s.defaultReadObject();
      for (int i = 0; i < menus.size(); i++) {
        Menu m = menus.elementAt(i);
        m.parent = this;
      }
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();


/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this MenuBar.
     * For menu bars, the AccessibleContext takes the form of an
     * AccessibleAWTMenuBar.
     * A new AccessibleAWTMenuBar instance is created if necessary.
     *
     * <p>
     *  获取与此MenuBar相关联的AccessibleContext。对于菜单栏,AccessibleContext采用AccessibleAWTMenuBar的形式。
     * 如果需要,将创建一个新的AccessibleAWTMenuBar实例。
     * 
     * 
     * @return an AccessibleAWTMenuBar that serves as the
     *         AccessibleContext of this MenuBar
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTMenuBar();
        }
        return accessibleContext;
    }

    /**
     * Defined in MenuComponent. Overridden here.
     * <p>
     *  在MenuComponent中定义。在这里覆盖。
     * 
     */
    int getAccessibleChildIndex(MenuComponent child) {
        return menus.indexOf(child);
    }

    /**
     * Inner class of MenuBar used to provide default support for
     * accessibility.  This class is not meant to be used directly by
     * application developers, but is instead meant only to be
     * subclassed by menu component developers.
     * <p>
     * This class implements accessibility support for the
     * <code>MenuBar</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to menu bar user-interface elements.
     * <p>
     *  MenuBar的内部类用于提供对辅助功能的默认支持。这个类不是直接由应用程序开发人员使用,而是意味着只能由菜单组件开发人员进行子类化。
     * <p>
     *  此类实现<code> MenuBar </code>类的辅助功能支持。它提供了适用于菜单栏用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTMenuBar extends AccessibleAWTMenuComponent
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -8577604491830083815L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @since 1.4
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.MENU_BAR;
        }

    } // class AccessibleAWTMenuBar

}
