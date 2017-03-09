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
import java.awt.peer.MenuPeer;
import java.awt.event.KeyEvent;
import javax.accessibility.*;
import sun.awt.AWTAccessor;

/**
 * A <code>Menu</code> object is a pull-down menu component
 * that is deployed from a menu bar.
 * <p>
 * A menu can optionally be a <i>tear-off</i> menu. A tear-off menu
 * can be opened and dragged away from its parent menu bar or menu.
 * It remains on the screen after the mouse button has been released.
 * The mechanism for tearing off a menu is platform dependent, since
 * the look and feel of the tear-off menu is determined by its peer.
 * On platforms that do not support tear-off menus, the tear-off
 * property is ignored.
 * <p>
 * Each item in a menu must belong to the <code>MenuItem</code>
 * class. It can be an instance of <code>MenuItem</code>, a submenu
 * (an instance of <code>Menu</code>), or a check box (an instance of
 * <code>CheckboxMenuItem</code>).
 *
 * <p>
 *  <code> Menu </code>对象是从菜单栏部署的下拉菜单组件。
 * <p>
 *  菜单可以可选地是<i>撕下</i>菜单。可以打开撕离菜单,并从其父菜单栏或菜单中拖出。鼠标按钮释放后,它仍保留在屏幕上。撕开菜单的机制取决于平台,因为撕离菜单的外观和感觉由其对等体确定。
 * 在不支持拆除菜单的平台上,将忽略拆除属性。
 * <p>
 *  菜单中的每个项目都必须属于<code> MenuItem </code>类。
 * 它可以是<code> MenuItem </code>,子菜单(<code> Menu </code>的实例)或复选框(<code> CheckboxMenuItem </code>的实例)的实例。
 * 
 * 
 * @author Sami Shaio
 * @see     java.awt.MenuItem
 * @see     java.awt.CheckboxMenuItem
 * @since   JDK1.0
 */
public class Menu extends MenuItem implements MenuContainer, Accessible {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }

        AWTAccessor.setMenuAccessor(
            new AWTAccessor.MenuAccessor() {
                public Vector<MenuComponent> getItems(Menu menu) {
                    return menu.items;
                }
            });
    }

    /**
     * A vector of the items that will be part of the Menu.
     *
     * <p>
     *  将成为菜单一部分的项目的向量。
     * 
     * 
     * @serial
     * @see #countItems()
     */
    Vector<MenuComponent> items = new Vector<>();

    /**
     * This field indicates whether the menu has the
     * tear of property or not.  It will be set to
     * <code>true</code> if the menu has the tear off
     * property and it will be set to <code>false</code>
     * if it does not.
     * A torn off menu can be deleted by a user when
     * it is no longer needed.
     *
     * <p>
     *  该字段指示菜单是否具有属性的撕裂。如果菜单有tear off属性,它将被设置为<code> true </code>,如果没有,它将被设置为<code> false </code>。
     * 当不再需要时,用户可以删除撕下的菜单。
     * 
     * 
     * @serial
     * @see #isTearOff()
     */
    boolean             tearOff;

    /**
     * This field will be set to <code>true</code>
     * if the Menu in question is actually a help
     * menu.  Otherwise it will be set to <code>
     * false</code>.
     *
     * <p>
     *  如果相关菜单实际上是帮助菜单,则此字段将设置为<code> true </code>。否则它将被设置为<code> false </code>。
     * 
     * 
     * @serial
     */
    boolean             isHelpMenu;

    private static final String base = "menu";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = -8809584163345499784L;

    /**
     * Constructs a new menu with an empty label. This menu is not
     * a tear-off menu.
     * <p>
     *  构造一个带有空标签的新菜单。此菜单不是可撕下的菜单。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since      JDK1.1
     */
    public Menu() throws HeadlessException {
        this("", false);
    }

    /**
     * Constructs a new menu with the specified label. This menu is not
     * a tear-off menu.
     * <p>
     * 构造具有指定标签的新菜单。此菜单不是可撕下的菜单。
     * 
     * 
     * @param       label the menu's label in the menu bar, or in
     *                   another menu of which this menu is a submenu.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public Menu(String label) throws HeadlessException {
        this(label, false);
    }

    /**
     * Constructs a new menu with the specified label,
     * indicating whether the menu can be torn off.
     * <p>
     * Tear-off functionality may not be supported by all
     * implementations of AWT.  If a particular implementation doesn't
     * support tear-off menus, this value is silently ignored.
     * <p>
     *  构造具有指定标签的新菜单,指示菜单是否可以被撕下。
     * <p>
     *  AWT的所有实施可能不支持撕除功能。如果特定实现不支持拆除菜单,则会默认忽略此值。
     * 
     * 
     * @param       label the menu's label in the menu bar, or in
     *                   another menu of which this menu is a submenu.
     * @param       tearOff   if <code>true</code>, the menu
     *                   is a tear-off menu.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since       JDK1.0.
     */
    public Menu(String label, boolean tearOff) throws HeadlessException {
        super(label);
        this.tearOff = tearOff;
    }

    /**
     * Construct a name for this MenuComponent.  Called by getName() when
     * the name is null.
     * <p>
     *  为此MenuComponent构造名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Menu.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Creates the menu's peer.  The peer allows us to modify the
     * appearance of the menu without changing its functionality.
     * <p>
     *  创建菜单的对等项。对等体允许我们在不改变其功能的情况下修改菜单的外观。
     * 
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null)
                peer = Toolkit.getDefaultToolkit().createMenu(this);
            int nitems = getItemCount();
            for (int i = 0 ; i < nitems ; i++) {
                MenuItem mi = getItem(i);
                mi.parent = this;
                mi.addNotify();
            }
        }
    }

    /**
     * Removes the menu's peer.  The peer allows us to modify the appearance
     * of the menu without changing its functionality.
     * <p>
     *  删除菜单的对等项。对等体允许我们在不改变其功能的情况下修改菜单的外观。
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            int nitems = getItemCount();
            for (int i = 0 ; i < nitems ; i++) {
                getItem(i).removeNotify();
            }
            super.removeNotify();
        }
    }

    /**
     * Indicates whether this menu is a tear-off menu.
     * <p>
     * Tear-off functionality may not be supported by all
     * implementations of AWT.  If a particular implementation doesn't
     * support tear-off menus, this value is silently ignored.
     * <p>
     *  指示此菜单是否为撕离菜单。
     * <p>
     *  AWT的所有实现可能不支持撕除功能。如果特定实现不支持拆除菜单,则会默认忽略此值。
     * 
     * 
     * @return      <code>true</code> if this is a tear-off menu;
     *                         <code>false</code> otherwise.
     */
    public boolean isTearOff() {
        return tearOff;
    }

    /**
      * Get the number of items in this menu.
      * <p>
      *  获取此菜单中的项目数。
      * 
      * 
      * @return     the number of items in this menu.
      * @since      JDK1.1
      */
    public int getItemCount() {
        return countItems();
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>getItemCount()</code>.
     */
    @Deprecated
    public int countItems() {
        return countItemsImpl();
    }

    /*
     * This is called by the native code, so client code can't
     * be called on the toolkit thread.
     * <p>
     *  这是由本地代码调用的,所以客户端代码不能在工具包线程上调用。
     * 
     */
    final int countItemsImpl() {
        return items.size();
    }

    /**
     * Gets the item located at the specified index of this menu.
     * <p>
     *  获取位于此菜单的指定索引处的项目。
     * 
     * 
     * @param     index the position of the item to be returned.
     * @return    the item located at the specified index.
     */
    public MenuItem getItem(int index) {
        return getItemImpl(index);
    }

    /*
     * This is called by the native code, so client code can't
     * be called on the toolkit thread.
     * <p>
     *  这是由本地代码调用的,所以客户端代码不能在工具包线程上调用。
     * 
     */
    final MenuItem getItemImpl(int index) {
        return (MenuItem)items.elementAt(index);
    }

    /**
     * Adds the specified menu item to this menu. If the
     * menu item has been part of another menu, removes it
     * from that menu.
     *
     * <p>
     *  将指定的菜单项添加到此菜单。如果菜单项已作为另一个菜单的一部分,则从该菜单中删除该菜单项。
     * 
     * 
     * @param       mi   the menu item to be added
     * @return      the menu item added
     * @see         java.awt.Menu#insert(java.lang.String, int)
     * @see         java.awt.Menu#insert(java.awt.MenuItem, int)
     */
    public MenuItem add(MenuItem mi) {
        synchronized (getTreeLock()) {
            if (mi.parent != null) {
                mi.parent.remove(mi);
            }
            items.addElement(mi);
            mi.parent = this;
            MenuPeer peer = (MenuPeer)this.peer;
            if (peer != null) {
                mi.addNotify();
                peer.addItem(mi);
            }
            return mi;
        }
    }

    /**
     * Adds an item with the specified label to this menu.
     *
     * <p>
     *  将具有指定标签的项目添加到此菜单。
     * 
     * 
     * @param       label   the text on the item
     * @see         java.awt.Menu#insert(java.lang.String, int)
     * @see         java.awt.Menu#insert(java.awt.MenuItem, int)
     */
    public void add(String label) {
        add(new MenuItem(label));
    }

    /**
     * Inserts a menu item into this menu
     * at the specified position.
     *
     * <p>
     *  在指定位置将菜单项插入此菜单。
     * 
     * 
     * @param         menuitem  the menu item to be inserted.
     * @param         index     the position at which the menu
     *                          item should be inserted.
     * @see           java.awt.Menu#add(java.lang.String)
     * @see           java.awt.Menu#add(java.awt.MenuItem)
     * @exception     IllegalArgumentException if the value of
     *                    <code>index</code> is less than zero
     * @since         JDK1.1
     */

    public void insert(MenuItem menuitem, int index) {
        synchronized (getTreeLock()) {
            if (index < 0) {
                throw new IllegalArgumentException("index less than zero.");
            }

            int nitems = getItemCount();
            Vector<MenuItem> tempItems = new Vector<>();

            /* Remove the item at index, nitems-index times
               storing them in a temporary vector in the
               order they appear on the menu.
            /* <p>
            /*  将它们按照它们在菜单上出现的顺序存储在临时向量中。
            /* 
            */
            for (int i = index ; i < nitems; i++) {
                tempItems.addElement(getItem(index));
                remove(index);
            }

            add(menuitem);

            /* Add the removed items back to the menu, they are
               already in the correct order in the temp vector.
            /* <p>
            /* 已经在temp矢量中的正确顺序。
            /* 
            */
            for (int i = 0; i < tempItems.size()  ; i++) {
                add(tempItems.elementAt(i));
            }
        }
    }

    /**
     * Inserts a menu item with the specified label into this menu
     * at the specified position.  This is a convenience method for
     * <code>insert(menuItem, index)</code>.
     *
     * <p>
     *  在指定位置将带有指定标签的菜单项插入此菜单。这是<code> insert(menuItem,index)</code>的一个方便的方法。
     * 
     * 
     * @param       label the text on the item
     * @param       index the position at which the menu item
     *                      should be inserted
     * @see         java.awt.Menu#add(java.lang.String)
     * @see         java.awt.Menu#add(java.awt.MenuItem)
     * @exception     IllegalArgumentException if the value of
     *                    <code>index</code> is less than zero
     * @since       JDK1.1
     */

    public void insert(String label, int index) {
        insert(new MenuItem(label), index);
    }

    /**
     * Adds a separator line, or a hypen, to the menu at the current position.
     * <p>
     *  将分隔线或连字号添加到当前位置的菜单。
     * 
     * 
     * @see         java.awt.Menu#insertSeparator(int)
     */
    public void addSeparator() {
        add("-");
    }

    /**
     * Inserts a separator at the specified position.
     * <p>
     *  在指定位置插入分隔符。
     * 
     * 
     * @param       index the position at which the
     *                       menu separator should be inserted.
     * @exception   IllegalArgumentException if the value of
     *                       <code>index</code> is less than 0.
     * @see         java.awt.Menu#addSeparator
     * @since       JDK1.1
     */

    public void insertSeparator(int index) {
        synchronized (getTreeLock()) {
            if (index < 0) {
                throw new IllegalArgumentException("index less than zero.");
            }

            int nitems = getItemCount();
            Vector<MenuItem> tempItems = new Vector<>();

            /* Remove the item at index, nitems-index times
               storing them in a temporary vector in the
               order they appear on the menu.
            /* <p>
            /*  将它们按照它们在菜单上出现的顺序存储在临时向量中。
            /* 
            */
            for (int i = index ; i < nitems; i++) {
                tempItems.addElement(getItem(index));
                remove(index);
            }

            addSeparator();

            /* Add the removed items back to the menu, they are
               already in the correct order in the temp vector.
            /* <p>
            /*  已经在temp矢量中的正确顺序。
            /* 
            */
            for (int i = 0; i < tempItems.size()  ; i++) {
                add(tempItems.elementAt(i));
            }
        }
    }

    /**
     * Removes the menu item at the specified index from this menu.
     * <p>
     *  从此菜单中删除指定索引处的菜单项。
     * 
     * 
     * @param       index the position of the item to be removed.
     */
    public void remove(int index) {
        synchronized (getTreeLock()) {
            MenuItem mi = getItem(index);
            items.removeElementAt(index);
            MenuPeer peer = (MenuPeer)this.peer;
            if (peer != null) {
                mi.removeNotify();
                mi.parent = null;
                peer.delItem(index);
            }
        }
    }

    /**
     * Removes the specified menu item from this menu.
     * <p>
     *  从此菜单中删除指定的菜单项。
     * 
     * 
     * @param  item the item to be removed from the menu.
     *         If <code>item</code> is <code>null</code>
     *         or is not in this menu, this method does
     *         nothing.
     */
    public void remove(MenuComponent item) {
        synchronized (getTreeLock()) {
            int index = items.indexOf(item);
            if (index >= 0) {
                remove(index);
            }
        }
    }

    /**
     * Removes all items from this menu.
     * <p>
     *  从此菜单中删除所有项目。
     * 
     * 
     * @since       JDK1.0.
     */
    public void removeAll() {
        synchronized (getTreeLock()) {
            int nitems = getItemCount();
            for (int i = nitems-1 ; i >= 0 ; i--) {
                remove(i);
            }
        }
    }

    /*
     * Post an ActionEvent to the target of the MenuPeer
     * associated with the specified keyboard event (on
     * keydown).  Returns true if there is an associated
     * keyboard event.
     * <p>
     *  将ActionEvent发布到与指定键盘事件相关联的MenuPeer的目标(在键下)。如果存在相关联的键盘事件,则返回true。
     * 
     */
    boolean handleShortcut(KeyEvent e) {
        int nitems = getItemCount();
        for (int i = 0 ; i < nitems ; i++) {
            MenuItem mi = getItem(i);
            if (mi.handleShortcut(e)) {
                return true;
            }
        }
        return false;
    }

    MenuItem getShortcutMenuItem(MenuShortcut s) {
        int nitems = getItemCount();
        for (int i = 0 ; i < nitems ; i++) {
            MenuItem mi = getItem(i).getShortcutMenuItem(s);
            if (mi != null) {
                return mi;
            }
        }
        return null;
    }

    synchronized Enumeration<MenuShortcut> shortcuts() {
        Vector<MenuShortcut> shortcuts = new Vector<>();
        int nitems = getItemCount();
        for (int i = 0 ; i < nitems ; i++) {
            MenuItem mi = getItem(i);
            if (mi instanceof Menu) {
                Enumeration<MenuShortcut> e = ((Menu)mi).shortcuts();
                while (e.hasMoreElements()) {
                    shortcuts.addElement(e.nextElement());
                }
            } else {
                MenuShortcut ms = mi.getShortcut();
                if (ms != null) {
                    shortcuts.addElement(ms);
                }
            }
        }
        return shortcuts.elements();
    }

    void deleteShortcut(MenuShortcut s) {
        int nitems = getItemCount();
        for (int i = 0 ; i < nitems ; i++) {
            getItem(i).deleteShortcut(s);
        }
    }


    /* Serialization support.  A MenuContainer is responsible for
     * restoring the parent fields of its children.
     * <p>
     *  恢复其子项的父字段。
     * 
     */

    /**
     * The menu serialized Data Version.
     *
     * <p>
     *  菜单序列化了数据版本。
     * 
     * 
     * @serial
     */
    private int menuSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.
     *
     * <p>
     *  将缺省可序列化字段写入流。
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(java.io.ObjectOutputStream s)
      throws java.io.IOException
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
     * @see #writeObject(ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws IOException, ClassNotFoundException, HeadlessException
    {
      // HeadlessException will be thrown from MenuComponent's readObject
      s.defaultReadObject();
      for(int i = 0; i < items.size(); i++) {
        MenuItem item = (MenuItem)items.elementAt(i);
        item.parent = this;
      }
    }

    /**
     * Returns a string representing the state of this <code>Menu</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回表示此<code> Menu </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return the parameter string of this menu
     */
    public String paramString() {
        String str = ",tearOff=" + tearOff+",isHelpMenu=" + isHelpMenu;
        return super.paramString() + str;
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
     * Gets the AccessibleContext associated with this Menu.
     * For menus, the AccessibleContext takes the form of an
     * AccessibleAWTMenu.
     * A new AccessibleAWTMenu instance is created if necessary.
     *
     * <p>
     *  获取与此菜单相关联的AccessibleContext。对于菜单,AccessibleContext采用AccessibleAWTMenu的形式。
     * 如果需要,将创建一个新的AccessibleAWTMenu实例。
     * 
     * 
     * @return an AccessibleAWTMenu that serves as the
     *         AccessibleContext of this Menu
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTMenu();
        }
        return accessibleContext;
    }

    /**
     * Defined in MenuComponent. Overridden here.
     * <p>
     * 在MenuComponent中定义。在这里覆盖。
     * 
     */
    int getAccessibleChildIndex(MenuComponent child) {
        return items.indexOf(child);
    }

    /**
     * Inner class of Menu used to provide default support for
     * accessibility.  This class is not meant to be used directly by
     * application developers, but is instead meant only to be
     * subclassed by menu component developers.
     * <p>
     * This class implements accessibility support for the
     * <code>Menu</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to menu user-interface elements.
     * <p>
     *  内部类Menu用于提供对辅助功能的默认支持。这个类不是直接由应用程序开发人员使用,而是意味着只能由菜单组件开发人员进行子类化。
     * <p>
     *  此类实现<code> Menu </code>类的辅助功能支持。它提供了适用于菜单用户界面元素的Java可访问性API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTMenu extends AccessibleAWTMenuItem
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 5228160894980069094L;

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
            return AccessibleRole.MENU;
        }

    } // class AccessibleAWTMenu

}
