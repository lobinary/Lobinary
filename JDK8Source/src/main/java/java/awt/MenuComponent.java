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

import java.awt.peer.MenuComponentPeer;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import sun.awt.AppContext;
import sun.awt.AWTAccessor;
import javax.accessibility.*;

import java.security.AccessControlContext;
import java.security.AccessController;

/**
 * The abstract class <code>MenuComponent</code> is the superclass
 * of all menu-related components. In this respect, the class
 * <code>MenuComponent</code> is analogous to the abstract superclass
 * <code>Component</code> for AWT components.
 * <p>
 * Menu components receive and process AWT events, just as components do,
 * through the method <code>processEvent</code>.
 *
 * <p>
 *  抽象类<code> MenuComponent </code>是所有菜单相关组件的超类。
 * 在这方面,类<code> MenuComponent </code>类似于AWT组件的抽象超类<code> Component </code>。
 * <p>
 *  菜单组件通过方法<code> processEvent </code>接收和处理AWT事件,就像组件一样。
 * 
 * 
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public abstract class MenuComponent implements java.io.Serializable {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    transient MenuComponentPeer peer;
    transient MenuContainer parent;

    /**
     * The <code>AppContext</code> of the <code>MenuComponent</code>.
     * This is set in the constructor and never changes.
     * <p>
     *  MenuComponent </code>的<code> AppContext </code>。这是在构造函数中设置的,从不改变。
     * 
     */
    transient AppContext appContext;

    /**
     * The menu component's font. This value can be
     * <code>null</code> at which point a default will be used.
     * This defaults to <code>null</code>.
     *
     * <p>
     *  菜单组件的字体。此值可以是<code> null </code>,此时将使用默认值。默认为<code> null </code>。
     * 
     * 
     * @serial
     * @see #setFont(Font)
     * @see #getFont()
     */
    Font font;

    /**
     * The menu component's name, which defaults to <code>null</code>.
     * <p>
     *  菜单组件的名称,默认为<code> null </code>。
     * 
     * 
     * @serial
     * @see #getName()
     * @see #setName(String)
     */
    private String name;

    /**
     * A variable to indicate whether a name is explicitly set.
     * If <code>true</code> the name will be set explicitly.
     * This defaults to <code>false</code>.
     * <p>
     *  用于指示是否显式设置名称的变量。如果<code> true </code>,将明确设置名称。默认为<code> false </code>。
     * 
     * 
     * @serial
     * @see #setName(String)
     */
    private boolean nameExplicitlySet = false;

    /**
     * Defaults to <code>false</code>.
     * <p>
     *  默认为<code> false </code>。
     * 
     * 
     * @serial
     * @see #dispatchEvent(AWTEvent)
     */
    boolean newEventsOnly = false;

    /*
     * The menu's AccessControlContext.
     * <p>
     *  菜单的AccessControlContext。
     * 
     */
    private transient volatile AccessControlContext acc =
            AccessController.getContext();

    /*
     * Returns the acc this menu component was constructed with.
     * <p>
     *  返回此菜单组件构造的acc。
     * 
     */
    final AccessControlContext getAccessControlContext() {
        if (acc == null) {
            throw new SecurityException(
                    "MenuComponent is missing AccessControlContext");
        }
        return acc;
    }

    /*
     * Internal constants for serialization.
     * <p>
     *  序列化的内部常量。
     * 
     */
    final static String actionListenerK = Component.actionListenerK;
    final static String itemListenerK = Component.itemListenerK;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4536902356223894379L;

    static {
        AWTAccessor.setMenuComponentAccessor(
            new AWTAccessor.MenuComponentAccessor() {
                public AppContext getAppContext(MenuComponent menuComp) {
                    return menuComp.appContext;
                }
                public void setAppContext(MenuComponent menuComp,
                                          AppContext appContext) {
                    menuComp.appContext = appContext;
                }
                public MenuContainer getParent(MenuComponent menuComp) {
                    return menuComp.parent;
                }
                public Font getFont_NoClientCode(MenuComponent menuComp) {
                    return menuComp.getFont_NoClientCode();
                }
            });
    }

    /**
     * Creates a <code>MenuComponent</code>.
     * <p>
     *  创建<code> MenuComponent </code>。
     * 
     * 
     * @exception HeadlessException if
     *    <code>GraphicsEnvironment.isHeadless</code>
     *    returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public MenuComponent() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        appContext = AppContext.getAppContext();
    }

    /**
     * Constructs a name for this <code>MenuComponent</code>.
     * Called by <code>getName</code> when the name is <code>null</code>.
     * <p>
     *  为此<code> MenuComponent </code>构造一个名称。当名称为<code> null </code>时,由<code> getName </code>调用。
     * 
     * 
     * @return a name for this <code>MenuComponent</code>
     */
    String constructComponentName() {
        return null; // For strict compliance with prior platform versions, a MenuComponent
                     // that doesn't set its name should return null from
                     // getName()
    }

    /**
     * Gets the name of the menu component.
     * <p>
     *  获取菜单组件的名称。
     * 
     * 
     * @return        the name of the menu component
     * @see           java.awt.MenuComponent#setName(java.lang.String)
     * @since         JDK1.1
     */
    public String getName() {
        if (name == null && !nameExplicitlySet) {
            synchronized(this) {
                if (name == null && !nameExplicitlySet)
                    name = constructComponentName();
            }
        }
        return name;
    }

    /**
     * Sets the name of the component to the specified string.
     * <p>
     *  将组件的名称设置为指定的字符串。
     * 
     * 
     * @param         name    the name of the menu component
     * @see           java.awt.MenuComponent#getName
     * @since         JDK1.1
     */
    public void setName(String name) {
        synchronized(this) {
            this.name = name;
            nameExplicitlySet = true;
        }
    }

    /**
     * Returns the parent container for this menu component.
     * <p>
     *  返回此菜单组件的父容器。
     * 
     * 
     * @return    the menu component containing this menu component,
     *                 or <code>null</code> if this menu component
     *                 is the outermost component, the menu bar itself
     */
    public MenuContainer getParent() {
        return getParent_NoClientCode();
    }
    // NOTE: This method may be called by privileged threads.
    //       This functionality is implemented in a package-private method
    //       to insure that it cannot be overridden by client subclasses.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    final MenuContainer getParent_NoClientCode() {
        return parent;
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * programs should not directly manipulate peers.
     */
    @Deprecated
    public MenuComponentPeer getPeer() {
        return peer;
    }

    /**
     * Gets the font used for this menu component.
     * <p>
     * 获取此菜单组件使用的字体。
     * 
     * 
     * @return   the font used in this menu component, if there is one;
     *                  <code>null</code> otherwise
     * @see     java.awt.MenuComponent#setFont
     */
    public Font getFont() {
        Font font = this.font;
        if (font != null) {
            return font;
        }
        MenuContainer parent = this.parent;
        if (parent != null) {
            return parent.getFont();
        }
        return null;
    }

    // NOTE: This method may be called by privileged threads.
    //       This functionality is implemented in a package-private method
    //       to insure that it cannot be overridden by client subclasses.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    final Font getFont_NoClientCode() {
        Font font = this.font;
        if (font != null) {
            return font;
        }

        // The MenuContainer interface does not have getFont_NoClientCode()
        // and it cannot, because it must be package-private. Because of
        // this, we must manually cast classes that implement
        // MenuContainer.
        Object parent = this.parent;
        if (parent != null) {
            if (parent instanceof Component) {
                font = ((Component)parent).getFont_NoClientCode();
            } else if (parent instanceof MenuComponent) {
                font = ((MenuComponent)parent).getFont_NoClientCode();
            }
        }
        return font;
    } // getFont_NoClientCode()


    /**
     * Sets the font to be used for this menu component to the specified
     * font. This font is also used by all subcomponents of this menu
     * component, unless those subcomponents specify a different font.
     * <p>
     * Some platforms may not support setting of all font attributes
     * of a menu component; in such cases, calling <code>setFont</code>
     * will have no effect on the unsupported font attributes of this
     * menu component.  Unless subcomponents of this menu component
     * specify a different font, this font will be used by those
     * subcomponents if supported by the underlying platform.
     *
     * <p>
     *  将用于此菜单组件的字体设置为指定的字体。此字体也被此菜单组件的所有子组件使用,除非这些子组件指定不同的字体。
     * <p>
     *  一些平台可能不支持设置菜单组件的所有字体属性;在这种情况下,调用<code> setFont </code>将不会影响此菜单组件的不受支持的字体属性。
     * 除非此菜单组件的子组件指定不同的字体,否则如果底层平台支持,这些子组件将使用此字体。
     * 
     * 
     * @param     f   the font to be set
     * @see       #getFont
     * @see       Font#getAttributes
     * @see       java.awt.font.TextAttribute
     */
    public void setFont(Font f) {
        font = f;
        //Fixed 6312943: NullPointerException in method MenuComponent.setFont(Font)
        MenuComponentPeer peer = this.peer;
        if (peer != null) {
            peer.setFont(f);
        }
    }

    /**
     * Removes the menu component's peer.  The peer allows us to modify the
     * appearance of the menu component without changing the functionality of
     * the menu component.
     * <p>
     *  删除菜单组件的对等项。对等体允许我们在不改变菜单组件的功能的情况下修改菜单组件的外观。
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            MenuComponentPeer p = this.peer;
            if (p != null) {
                Toolkit.getEventQueue().removeSourceEvents(this, true);
                this.peer = null;
                p.dispose();
            }
        }
    }

    /**
     * Posts the specified event to the menu.
     * This method is part of the Java&nbsp;1.0 event system
     * and it is maintained only for backwards compatibility.
     * Its use is discouraged, and it may not be supported
     * in the future.
     * <p>
     *  将指定的事件发布到菜单。此方法是Java&nbsp; 1.0事件系统的一部分,并且仅为向后兼容性而进行维护。它的使用是不鼓励的,它可能不会在将来得到支持。
     * 
     * 
     * @param evt the event which is to take place
     * @deprecated As of JDK version 1.1, replaced by {@link
     * #dispatchEvent(AWTEvent) dispatchEvent}.
     */
    @Deprecated
    public boolean postEvent(Event evt) {
        MenuContainer parent = this.parent;
        if (parent != null) {
            parent.postEvent(evt);
        }
        return false;
    }

    /**
     * Delivers an event to this component or one of its sub components.
     * <p>
     *  将事件提交给此组件或其某个子组件。
     * 
     * 
     * @param e the event
     */
    public final void dispatchEvent(AWTEvent e) {
        dispatchEventImpl(e);
    }

    void dispatchEventImpl(AWTEvent e) {
        EventQueue.setCurrentEventAndMostRecentTime(e);

        Toolkit.getDefaultToolkit().notifyAWTEventListeners(e);

        if (newEventsOnly ||
            (parent != null && parent instanceof MenuComponent &&
             ((MenuComponent)parent).newEventsOnly)) {
            if (eventEnabled(e)) {
                processEvent(e);
            } else if (e instanceof ActionEvent && parent != null) {
                e.setSource(parent);
                ((MenuComponent)parent).dispatchEvent(e);
            }

        } else { // backward compatibility
            Event olde = e.convertToOld();
            if (olde != null) {
                postEvent(olde);
            }
        }
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
        return false;
    }
    /**
     * Processes events occurring on this menu component.
     * <p>Note that if the event parameter is <code>null</code>
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  处理在此菜单组件上发生的事件。 <p>请注意,如果事件参数为<code> null </code>,则此行为未指定,并可能导致异常。
     * 
     * 
     * @param e the event
     * @since JDK1.1
     */
    protected void processEvent(AWTEvent e) {
    }

    /**
     * Returns a string representing the state of this
     * <code>MenuComponent</code>. This method is intended to be used
     * only for debugging purposes, and the content and format of the
     * returned string may vary between implementations. The returned
     * string may be empty but may not be <code>null</code>.
     *
     * <p>
     * 返回表示此<code> MenuComponent </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return     the parameter string of this menu component
     */
    protected String paramString() {
        String thisName = getName();
        return (thisName != null? thisName : "");
    }

    /**
     * Returns a representation of this menu component as a string.
     * <p>
     *  以字符串形式返回此菜单组件的表示形式。
     * 
     * 
     * @return  a string representation of this menu component
     */
    public String toString() {
        return getClass().getName() + "[" + paramString() + "]";
    }

    /**
     * Gets this component's locking object (the object that owns the thread
     * synchronization monitor) for AWT component-tree and layout
     * operations.
     * <p>
     *  获取此组件的锁定对象(拥有线程同步监视器的对象)以进行AWT组件树和布局操作。
     * 
     * 
     * @return this component's locking object
     */
    protected final Object getTreeLock() {
        return Component.LOCK;
    }

    /**
     * Reads the menu component from an object input stream.
     *
     * <p>
     *  从对象输入流读取菜单组件。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @serial
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException
    {
        GraphicsEnvironment.checkHeadless();

        acc = AccessController.getContext();

        s.defaultReadObject();

        appContext = AppContext.getAppContext();
    }

    /**
     * Initialize JNI field and method IDs.
     * <p>
     *  初始化JNI字段和方法ID。
     * 
     */
    private static native void initIDs();


    /*
     * --- Accessibility Support ---
     *
     *  MenuComponent will contain all of the methods in interface Accessible,
     *  though it won't actually implement the interface - that will be up
     *  to the individual objects which extend MenuComponent.
     * <p>
     *  ---辅助功能
     * 
     *  MenuComponent将包含接口Accessible中的所有方法,虽然它不会实际实现接口 - 这将是扩展MenuComponent的单个对象。
     * 
     */

    AccessibleContext accessibleContext = null;

    /**
     * Gets the <code>AccessibleContext</code> associated with
     * this <code>MenuComponent</code>.
     *
     * The method implemented by this base class returns <code>null</code>.
     * Classes that extend <code>MenuComponent</code>
     * should implement this method to return the
     * <code>AccessibleContext</code> associated with the subclass.
     *
     * <p>
     *  获取与此<code> MenuComponent </code>关联的<code> AccessibleContext </code>。
     * 
     *  该基类实现的方法返回<code> null </code>。
     * 扩展<code> MenuComponent </code>的类应实现此方法以返回与子类关联的<code> AccessibleContext </code>。
     * 
     * 
     * @return the <code>AccessibleContext</code> of this
     *     <code>MenuComponent</code>
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    /**
     * Inner class of <code>MenuComponent</code> used to provide
     * default support for accessibility.  This class is not meant
     * to be used directly by application developers, but is instead
     * meant only to be subclassed by menu component developers.
     * <p>
     * The class used to obtain the accessible role for this object.
     * <p>
     *  内部类<code> MenuComponent </code>用于提供对辅助功能的默认支持。这个类不是直接由应用程序开发人员使用,而是意味着只能由菜单组件开发人员进行子类化。
     * <p>
     *  用于获取此对象的可访问角色的类。
     * 
     * 
     * @since 1.3
     */
    protected abstract class AccessibleAWTMenuComponent
        extends AccessibleContext
        implements java.io.Serializable, AccessibleComponent,
                   AccessibleSelection
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -4269533416223798698L;

        /**
         * Although the class is abstract, this should be called by
         * all sub-classes.
         * <p>
         * 虽然类是抽象的,但是这应该被所有子类调用。
         * 
         */
        protected AccessibleAWTMenuComponent() {
        }

        // AccessibleContext methods
        //

        /**
         * Gets the <code>AccessibleSelection</code> associated with this
         * object which allows its <code>Accessible</code> children to be selected.
         *
         * <p>
         *  获取与此对象关联的<code> AccessibleSelection </code>,以允许选择其<code>可访问</code>子代。
         * 
         * 
         * @return <code>AccessibleSelection</code> if supported by object;
         *      else return <code>null</code>
         * @see AccessibleSelection
         */
        public AccessibleSelection getAccessibleSelection() {
            return this;
        }

        /**
         * Gets the accessible name of this object.  This should almost never
         * return <code>java.awt.MenuComponent.getName</code>, as that
         * generally isn't a localized name, and doesn't have meaning for the
         * user.  If the object is fundamentally a text object (e.g. a menu item), the
         * accessible name should be the text of the object (e.g. "save").
         * If the object has a tooltip, the tooltip text may also be an
         * appropriate String to return.
         *
         * <p>
         *  获取此对象的可访问名称。这应该几乎不会返回<code> java.awt.MenuComponent.getName </code>,因为一般不是本地化的名称,并且没有用户的意义。
         * 如果对象基本上是文本对象(例如菜单项),则可访问名称应该是对象的文本(例如"保存")。如果对象有一个工具提示,工具提示文本也可能是一个合适的返回字符串。
         * 
         * 
         * @return the localized name of the object -- can be <code>null</code>
         *         if this object does not have a name
         * @see AccessibleContext#setAccessibleName
         */
        public String getAccessibleName() {
            return accessibleName;
        }

        /**
         * Gets the accessible description of this object.  This should be
         * a concise, localized description of what this object is - what
         * is its meaning to the user.  If the object has a tooltip, the
         * tooltip text may be an appropriate string to return, assuming
         * it contains a concise description of the object (instead of just
         * the name of the object - e.g. a "Save" icon on a toolbar that
         * had "save" as the tooltip text shouldn't return the tooltip
         * text as the description, but something like "Saves the current
         * text document" instead).
         *
         * <p>
         *  获取此对象的可访问描述。这应该是一个简洁的,本地化的描述这个对象是什么 - 它对用户的意义。
         * 如果对象有一个工具提示,工具提示文本可能是一个合适的返回字符串,假设它包含对象的简明描述(而不仅仅是对象的名称 - 例如工具栏上的"保存"图标, ",因为工具提示文本不应返回工具提示文本作为描述,而是类
         * 似"保存当前文本文档")。
         *  获取此对象的可访问描述。这应该是一个简洁的,本地化的描述这个对象是什么 - 它对用户的意义。
         * 
         * 
         * @return the localized description of the object -- can be
         *     <code>null</code> if this object does not have a description
         * @see AccessibleContext#setAccessibleDescription
         */
        public String getAccessibleDescription() {
            return accessibleDescription;
        }

        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of <code>AccessibleRole</code>
         *     describing the role of the object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.AWT_COMPONENT; // Non-specific -- overridden in subclasses
        }

        /**
         * Gets the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * 
         * @return an instance of <code>AccessibleStateSet</code>
         *     containing the current state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            return MenuComponent.this.getAccessibleStateSet();
        }

        /**
         * Gets the <code>Accessible</code> parent of this object.
         * If the parent of this object implements <code>Accessible</code>,
         * this method should simply return <code>getParent</code>.
         *
         * <p>
         *  获取此对象的<code> Accessible </code> parent。
         * 如果此对象的父对象实现<code> Accessible </code>,此方法应该只返回<code> getParent </code>。
         * 
         * 
         * @return the <code>Accessible</code> parent of this object -- can
         *    be <code>null</code> if this object does not have an
         *    <code>Accessible</code> parent
         */
        public Accessible getAccessibleParent() {
            if (accessibleParent != null) {
                return accessibleParent;
            } else {
                MenuContainer parent = MenuComponent.this.getParent();
                if (parent instanceof Accessible) {
                    return (Accessible) parent;
                }
            }
            return null;
        }

        /**
         * Gets the index of this object in its accessible parent.
         *
         * <p>
         *  获取此对象在其可访问父级中的索引。
         * 
         * 
         * @return the index of this object in its parent; -1 if this
         *     object does not have an accessible parent
         * @see #getAccessibleParent
         */
        public int getAccessibleIndexInParent() {
            return MenuComponent.this.getAccessibleIndexInParent();
        }

        /**
         * Returns the number of accessible children in the object.  If all
         * of the children of this object implement <code>Accessible</code>,
         * then this method should return the number of children of this object.
         *
         * <p>
         * 返回对象中可访问的子项数。如果这个对象的所有子实现<code> Accessible </code>,那么这个方法应该返回这个对象的子数。
         * 
         * 
         * @return the number of accessible children in the object
         */
        public int getAccessibleChildrenCount() {
            return 0; // MenuComponents don't have children
        }

        /**
         * Returns the nth <code>Accessible</code> child of the object.
         *
         * <p>
         *  返回对象的第n个<code> Accessible </code>子对象。
         * 
         * 
         * @param i zero-based index of child
         * @return the nth Accessible child of the object
         */
        public Accessible getAccessibleChild(int i) {
            return null; // MenuComponents don't have children
        }

        /**
         * Returns the locale of this object.
         *
         * <p>
         *  返回此对象的语言环境。
         * 
         * 
         * @return the locale of this object
         */
        public java.util.Locale getLocale() {
            MenuContainer parent = MenuComponent.this.getParent();
            if (parent instanceof Component)
                return ((Component)parent).getLocale();
            else
                return java.util.Locale.getDefault();
        }

        /**
         * Gets the <code>AccessibleComponent</code> associated with
         * this object if one exists.  Otherwise return <code>null</code>.
         *
         * <p>
         *  获取与此对象关联的<code> AccessibleComponent </code>(如果存在)。否则返回<code> null </code>。
         * 
         * 
         * @return the component
         */
        public AccessibleComponent getAccessibleComponent() {
            return this;
        }


        // AccessibleComponent methods
        //
        /**
         * Gets the background color of this object.
         *
         * <p>
         *  获取此对象的背景颜色。
         * 
         * 
         * @return the background color, if supported, of the object;
         *     otherwise, <code>null</code>
         */
        public Color getBackground() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Sets the background color of this object.
         * (For transparency, see <code>isOpaque</code>.)
         *
         * <p>
         *  设置此对象的背景颜色。 (有关透明度,请参阅<code> isOpaque </code>。)
         * 
         * 
         * @param c the new <code>Color</code> for the background
         * @see Component#isOpaque
         */
        public void setBackground(Color c) {
            // Not supported for MenuComponents
        }

        /**
         * Gets the foreground color of this object.
         *
         * <p>
         *  获取此对象的前景颜色。
         * 
         * 
         * @return the foreground color, if supported, of the object;
         *     otherwise, <code>null</code>
         */
        public Color getForeground() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Sets the foreground color of this object.
         *
         * <p>
         *  设置此对象的前景颜色。
         * 
         * 
         * @param c the new <code>Color</code> for the foreground
         */
        public void setForeground(Color c) {
            // Not supported for MenuComponents
        }

        /**
         * Gets the <code>Cursor</code> of this object.
         *
         * <p>
         *  获取此对象的<code> Cursor </code>。
         * 
         * 
         * @return the <code>Cursor</code>, if supported, of the object;
         *     otherwise, <code>null</code>
         */
        public Cursor getCursor() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Sets the <code>Cursor</code> of this object.
         * <p>
         * The method may have no visual effect if the Java platform
         * implementation and/or the native system do not support
         * changing the mouse cursor shape.
         * <p>
         *  设置此对象的<code> Cursor </code>。
         * <p>
         *  如果Java平台实现和/或本地系统不支持改变鼠标光标形状,则该方法可以没有视觉效果。
         * 
         * 
         * @param cursor the new <code>Cursor</code> for the object
         */
        public void setCursor(Cursor cursor) {
            // Not supported for MenuComponents
        }

        /**
         * Gets the <code>Font</code> of this object.
         *
         * <p>
         *  获取此对象的<code> Font </code>。
         * 
         * 
         * @return the <code>Font</code>,if supported, for the object;
         *     otherwise, <code>null</code>
         */
        public Font getFont() {
            return MenuComponent.this.getFont();
        }

        /**
         * Sets the <code>Font</code> of this object.
         *
         * <p>
         *  设置此对象的<code> Font </code>。
         * 
         * 
         * @param f the new <code>Font</code> for the object
         */
        public void setFont(Font f) {
            MenuComponent.this.setFont(f);
        }

        /**
         * Gets the <code>FontMetrics</code> of this object.
         *
         * <p>
         *  获取此对象的<code> FontMetrics </code>。
         * 
         * 
         * @param f the <code>Font</code>
         * @return the FontMetrics, if supported, the object;
         *              otherwise, <code>null</code>
         * @see #getFont
         */
        public FontMetrics getFontMetrics(Font f) {
            return null; // Not supported for MenuComponents
        }

        /**
         * Determines if the object is enabled.
         *
         * <p>
         *  确定对象是否已启用。
         * 
         * 
         * @return true if object is enabled; otherwise, false
         */
        public boolean isEnabled() {
            return true; // Not supported for MenuComponents
        }

        /**
         * Sets the enabled state of the object.
         *
         * <p>
         *  设置对象的启用状态。
         * 
         * 
         * @param b if true, enables this object; otherwise, disables it
         */
        public void setEnabled(boolean b) {
            // Not supported for MenuComponents
        }

        /**
         * Determines if the object is visible.  Note: this means that the
         * object intends to be visible; however, it may not in fact be
         * showing on the screen because one of the objects that this object
         * is contained by is not visible.  To determine if an object is
         * showing on the screen, use <code>isShowing</code>.
         *
         * <p>
         *  确定对象是否可见。注意：这意味着对象是可见的;然而,它可能实际上不在屏幕上显示,因为包含该对象的对象之一是不可见的。
         * 要确定对象是否显示在屏幕上,请使用<code> isShowing </code>。
         * 
         * 
         * @return true if object is visible; otherwise, false
         */
        public boolean isVisible() {
            return true; // Not supported for MenuComponents
        }

        /**
         * Sets the visible state of the object.
         *
         * <p>
         *  设置对象的可见状态。
         * 
         * 
         * @param b if true, shows this object; otherwise, hides it
         */
        public void setVisible(boolean b) {
            // Not supported for MenuComponents
        }

        /**
         * Determines if the object is showing.  This is determined by checking
         * the visibility of the object and ancestors of the object.  Note:
         * this will return true even if the object is obscured by another
         * (for example, it happens to be underneath a menu that was pulled
         * down).
         *
         * <p>
         * 确定对象是否正在显示。这是通过检查对象和对象的祖先的可见性来确定的。注意：即使对象被另一个对象遮盖,这将返回true(例如,它恰好在下拉菜单下)。
         * 
         * 
         * @return true if object is showing; otherwise, false
         */
        public boolean isShowing() {
            return true; // Not supported for MenuComponents
        }

        /**
         * Checks whether the specified point is within this object's bounds,
         * where the point's x and y coordinates are defined to be relative to
         * the coordinate system of the object.
         *
         * <p>
         *  检查指定点是否在此对象的边界内,其中点的x和y坐标被定义为相对于对象的坐标系。
         * 
         * 
         * @param p the <code>Point</code> relative to the coordinate
         *     system of the object
         * @return true if object contains <code>Point</code>; otherwise false
         */
        public boolean contains(Point p) {
            return false; // Not supported for MenuComponents
        }

        /**
         * Returns the location of the object on the screen.
         *
         * <p>
         *  返回对象在屏幕上的位置。
         * 
         * 
         * @return location of object on screen -- can be <code>null</code>
         *     if this object is not on the screen
         */
        public Point getLocationOnScreen() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Gets the location of the object relative to the parent in the form
         * of a point specifying the object's top-left corner in the screen's
         * coordinate space.
         *
         * <p>
         *  以指定对象在屏幕坐标空间中左上角的点的形式获取对象相对于父对象的位置。
         * 
         * 
         * @return an instance of <code>Point</code> representing the
         *    top-left corner of the object's bounds in the coordinate
         *    space of the screen; <code>null</code> if
         *    this object or its parent are not on the screen
         */
        public Point getLocation() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Sets the location of the object relative to the parent.
         * <p>
         *  设置对象相对于父对象的位置。
         * 
         */
        public void setLocation(Point p) {
            // Not supported for MenuComponents
        }

        /**
         * Gets the bounds of this object in the form of a
         * <code>Rectangle</code> object.
         * The bounds specify this object's width, height, and location
         * relative to its parent.
         *
         * <p>
         *  以<code> Rectangle </code>对象的形式获取此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
         * 
         * 
         * @return a rectangle indicating this component's bounds;
         *     <code>null</code> if this object is not on the screen
         */
        public Rectangle getBounds() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Sets the bounds of this object in the form of a
         * <code>Rectangle</code> object.
         * The bounds specify this object's width, height, and location
         * relative to its parent.
         *
         * <p>
         *  以<code> Rectangle </code>对象的形式设置此对象的边界。 bounds指定此对象的宽度,高度和相对于其父级的位置。
         * 
         * 
         * @param r a rectangle indicating this component's bounds
         */
        public void setBounds(Rectangle r) {
            // Not supported for MenuComponents
        }

        /**
         * Returns the size of this object in the form of a
         * <code>Dimension</code> object. The height field of
         * the <code>Dimension</code> object contains this object's
         * height, and the width field of the <code>Dimension</code>
         * object contains this object's width.
         *
         * <p>
         *  以<code> Dimension </code>对象的形式返回此对象的大小。
         *  <code> Dimension </code>对象的高度字段包含此对象的高度,<code> Dimension </code>对象的宽度字段包含此对象的宽度。
         * 
         * 
         * @return a <code>Dimension</code> object that indicates the
         *         size of this component; <code>null</code>
         *         if this object is not on the screen
         */
        public Dimension getSize() {
            return null; // Not supported for MenuComponents
        }

        /**
         * Resizes this object.
         *
         * <p>
         *  调整此对象的大小。
         * 
         * 
         * @param d - the <code>Dimension</code> specifying the
         *    new size of the object
         */
        public void setSize(Dimension d) {
            // Not supported for MenuComponents
        }

        /**
         * Returns the <code>Accessible</code> child, if one exists,
         * contained at the local coordinate <code>Point</code>.
         * If there is no <code>Accessible</code> child, <code>null</code>
         * is returned.
         *
         * <p>
         *  返回包含在本地坐标<code> Point </code>处的<code> Accessible </code>子代(如果存在)。
         * 如果没有<code> Accessible </code>子代,则返回<code> null </code>。
         * 
         * 
         * @param p the point defining the top-left corner of the
         *    <code>Accessible</code>, given in the coordinate space
         *    of the object's parent
         * @return the <code>Accessible</code>, if it exists,
         *    at the specified location; else <code>null</code>
         */
        public Accessible getAccessibleAt(Point p) {
            return null; // MenuComponents don't have children
        }

        /**
         * Returns whether this object can accept focus or not.
         *
         * <p>
         * 返回此对象是否可以接受焦点。
         * 
         * 
         * @return true if object can accept focus; otherwise false
         */
        public boolean isFocusTraversable() {
            return true; // Not supported for MenuComponents
        }

        /**
         * Requests focus for this object.
         * <p>
         *  此对象的请求焦点。
         * 
         */
        public void requestFocus() {
            // Not supported for MenuComponents
        }

        /**
         * Adds the specified focus listener to receive focus events from this
         * component.
         *
         * <p>
         *  添加指定的焦点侦听器以从此组件接收焦点事件。
         * 
         * 
         * @param l the focus listener
         */
        public void addFocusListener(java.awt.event.FocusListener l) {
            // Not supported for MenuComponents
        }

        /**
         * Removes the specified focus listener so it no longer receives focus
         * events from this component.
         *
         * <p>
         *  删除指定的焦点侦听器,使其不再从此组件接收焦点事件。
         * 
         * 
         * @param l the focus listener
         */
        public void removeFocusListener(java.awt.event.FocusListener l) {
            // Not supported for MenuComponents
        }

        // AccessibleSelection methods
        //

        /**
         * Returns the number of <code>Accessible</code> children currently selected.
         * If no children are selected, the return value will be 0.
         *
         * <p>
         *  返回当前选择的<code>可访问</code>子项数。如果未选择任何子项,则返回值为0。
         * 
         * 
         * @return the number of items currently selected
         */
         public int getAccessibleSelectionCount() {
             return 0;  //  To be fully implemented in a future release
         }

        /**
         * Returns an <code>Accessible</code> representing the specified
         * selected child in the object.  If there isn't a selection, or there are
         * fewer children selected than the integer passed in, the return
         * value will be <code>null</code>.
         * <p>Note that the index represents the i-th selected child, which
         * is different from the i-th child.
         *
         * <p>
         *  返回表示对象中指定的选定子项的<code> Accessible </code>。如果没有选择,或者选择的子选择比传递的整数少,返回值将是<code> null </code>。
         *  <p>请注意,索引表示第i个选定子项,它与第i个子项不同。
         * 
         * 
         * @param i the zero-based index of selected children
         * @return the i-th selected child
         * @see #getAccessibleSelectionCount
         */
         public Accessible getAccessibleSelection(int i) {
             return null;  //  To be fully implemented in a future release
         }

        /**
         * Determines if the current child of this object is selected.
         *
         * <p>
         *  确定是否选择此对象的当前子项。
         * 
         * 
         * @return true if the current child of this object is selected;
         *    else false
         * @param i the zero-based index of the child in this
         *      <code>Accessible</code> object
         * @see AccessibleContext#getAccessibleChild
         */
         public boolean isAccessibleChildSelected(int i) {
             return false;  //  To be fully implemented in a future release
         }

        /**
         * Adds the specified <code>Accessible</code> child of the object
         * to the object's selection.  If the object supports multiple selections,
         * the specified child is added to any existing selection, otherwise
         * it replaces any existing selection in the object.  If the
         * specified child is already selected, this method has no effect.
         *
         * <p>
         *  将对象的指定<code> Accessible </code>子对象添加到对象的选择。如果对象支持多个选择,则将指定的子项添加到任何现有选择,否则将替换对象中的任何现有选择。
         * 如果已选择指定的子项,则此方法无效。
         * 
         * 
         * @param i the zero-based index of the child
         * @see AccessibleContext#getAccessibleChild
         */
         public void addAccessibleSelection(int i) {
               //  To be fully implemented in a future release
         }

        /**
         * Removes the specified child of the object from the object's
         * selection.  If the specified item isn't currently selected, this
         * method has no effect.
         *
         * <p>
         *  从对象的选择中删除对象的指定子项。如果当前未选择指定的项目,则此方法无效。
         * 
         * 
         * @param i the zero-based index of the child
         * @see AccessibleContext#getAccessibleChild
         */
         public void removeAccessibleSelection(int i) {
               //  To be fully implemented in a future release
         }

        /**
         * Clears the selection in the object, so that no children in the
         * object are selected.
         * <p>
         *  清除对象中的选择,以便不会选择对象中的任何子对象。
         * 
         */
         public void clearAccessibleSelection() {
               //  To be fully implemented in a future release
         }

        /**
         * Causes every child of the object to be selected
         * if the object supports multiple selections.
         * <p>
         *  如果对象支持多个选择,则导致选择对象的每个子项。
         * 
         */
         public void selectAllAccessibleSelection() {
               //  To be fully implemented in a future release
         }

    } // inner class AccessibleAWTComponent

    /**
     * Gets the index of this object in its accessible parent.
     *
     * <p>
     *  获取此对象在其可访问父级中的索引。
     * 
     * 
     * @return -1 if this object does not have an accessible parent;
     *      otherwise, the index of the child in its accessible parent.
     */
    int getAccessibleIndexInParent() {
        MenuContainer localParent = parent;
        if (!(localParent instanceof MenuComponent)) {
            // MenuComponents only have accessible index when inside MenuComponents
            return -1;
        }
        MenuComponent localParentMenu = (MenuComponent)localParent;
        return localParentMenu.getAccessibleChildIndex(this);
    }

    /**
     * Gets the index of the child within this MenuComponent.
     *
     * <p>
     * 获取此MenuComponent中的孩子的索引。
     * 
     * 
     * @param child MenuComponent whose index we are interested in.
     * @return -1 if this object doesn't contain the child,
     *      otherwise, index of the child.
     */
    int getAccessibleChildIndex(MenuComponent child) {
        return -1; // Overridden in subclasses.
    }

    /**
     * Gets the state of this object.
     *
     * <p>
     *  获取此对象的状态。
     * 
     * @return an instance of <code>AccessibleStateSet</code>
     *     containing the current state set of the object
     * @see AccessibleState
     */
    AccessibleStateSet getAccessibleStateSet() {
        AccessibleStateSet states = new AccessibleStateSet();
        return states;
    }

}
