/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.util.EventListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.event.*;
import javax.accessibility.*;

/**
 * An implementation of an item in a menu. A menu item is essentially a button
 * sitting in a list. When the user selects the "button", the action
 * associated with the menu item is performed. A <code>JMenuItem</code>
 * contained in a <code>JPopupMenu</code> performs exactly that function.
 * <p>
 * Menu items can be configured, and to some degree controlled, by
 * <code><a href="Action.html">Action</a></code>s.  Using an
 * <code>Action</code> with a menu item has many benefits beyond directly
 * configuring a menu item.  Refer to <a href="Action.html#buttonActions">
 * Swing Components Supporting <code>Action</code></a> for more
 * details, and you can find more information in <a
 * href="https://docs.oracle.com/javase/tutorial/uiswing/misc/action.html">How
 * to Use Actions</a>, a section in <em>The Java Tutorial</em>.
 * <p>
 * For further documentation and for examples, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">How to Use Menus</a>
 * in <em>The Java Tutorial.</em>
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * @beaninfo
 *   attribute: isContainer false
 * description: An item which can be selected in a menu.
 *
 * <p>
 *  菜单中项目的实现。菜单项本质上是位于列表中的按钮。当用户选择"按钮"时,执行与菜单项相关联的动作。
 * 包含在<code> JPopupMenu </code>中的<code> JMenuItem </code>执行该函数。
 * <p>
 *  菜单项可以通过<code> <a href="Action.html">操作</a> </code>进行配置,并在某种程度上受到控制。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * 有关详情,请参阅<a href="Action.html#buttonActions"> Swing组件支持<code>操作</code> </a>,您可以在<a href ="https：// docs中找到更多信息.oracle.com / javase / tutorial / uiswing / misc / action.html">
 * 如何使用操作</a>,<em> Java教程</em>中的一节。
 * 对菜单项使用<code> Action </code>除了直接配置菜单项之外还有许多好处。
 * <p>
 *  有关其他文档和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">如何使用菜单</a>
 * 。
 * 教程。</em>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：可以在菜单中选择的项目。
 * 
 * 
 * @author Georges Saab
 * @author David Karlton
 * @see JPopupMenu
 * @see JMenu
 * @see JCheckBoxMenuItem
 * @see JRadioButtonMenuItem
 */
@SuppressWarnings("serial")
public class JMenuItem extends AbstractButton implements Accessible,MenuElement  {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "MenuItemUI";

    /* diagnostic aids -- should be false for production builds. */
    private static final boolean TRACE =   false; // trace creates and disposes
    private static final boolean VERBOSE = false; // show reuse hits/misses
    private static final boolean DEBUG =   false;  // show bad params, misc.

    private boolean isMouseDragged = false;

    /**
     * Creates a <code>JMenuItem</code> with no set text or icon.
     * <p>
     *  创建一个没有设置文本或图标的<code> JMenuItem </code>。
     * 
     */
    public JMenuItem() {
        this(null, (Icon)null);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified icon.
     *
     * <p>
     *  使用指定的图标创建<code> JMenuItem </code>。
     * 
     * 
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public JMenuItem(Icon icon) {
        this(null, icon);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text.
     *
     * <p>
     *  使用指定的文本创建<code> JMenuItem </code>。
     * 
     * 
     * @param text the text of the <code>JMenuItem</code>
     */
    public JMenuItem(String text) {
        this(text, (Icon)null);
    }

    /**
     * Creates a menu item whose properties are taken from the
     * specified <code>Action</code>.
     *
     * <p>
     *  创建一个菜单项,其属性取自指定的<code> Action </code>。
     * 
     * 
     * @param a the action of the <code>JMenuItem</code>
     * @since 1.3
     */
    public JMenuItem(Action a) {
        this();
        setAction(a);
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and icon.
     *
     * <p>
     *  使用指定的文本和图标创建<code> JMenuItem </code>。
     * 
     * 
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    public JMenuItem(String text, Icon icon) {
        setModel(new DefaultButtonModel());
        init(text, icon);
        initFocusability();
    }

    /**
     * Creates a <code>JMenuItem</code> with the specified text and
     * keyboard mnemonic.
     *
     * <p>
     *  使用指定的文本和键盘助记符创建<code> JMenuItem </code>。
     * 
     * 
     * @param text the text of the <code>JMenuItem</code>
     * @param mnemonic the keyboard mnemonic for the <code>JMenuItem</code>
     */
    public JMenuItem(String text, int mnemonic) {
        setModel(new DefaultButtonModel());
        init(text, null);
        setMnemonic(mnemonic);
        initFocusability();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setModel(ButtonModel newModel) {
        super.setModel(newModel);
        if(newModel instanceof DefaultButtonModel) {
            ((DefaultButtonModel)newModel).setMenuItem(true);
        }
    }

    /**
     * Inititalizes the focusability of the the <code>JMenuItem</code>.
     * <code>JMenuItem</code>'s are focusable, but subclasses may
     * want to be, this provides them the opportunity to override this
     * and invoke something else, or nothing at all. Refer to
     * {@link javax.swing.JMenu#initFocusability} for the motivation of
     * this.
     * <p>
     *  初始化<code> JMenuItem </code>的可集中性。 <code> JMenuItem </code>是可以聚焦的,但子类可能想要的,这让他们有机会覆盖和调用别的东西,或什么也没有。
     * 参考{@link javax.swing.JMenu#initFocusability}的动机。
     * 
     */
    void initFocusability() {
        setFocusable(false);
    }

    /**
     * Initializes the menu item with the specified text and icon.
     *
     * <p>
     *  使用指定的文本和图标初始化菜单项。
     * 
     * 
     * @param text the text of the <code>JMenuItem</code>
     * @param icon the icon of the <code>JMenuItem</code>
     */
    protected void init(String text, Icon icon) {
        if(text != null) {
            setText(text);
        }

        if(icon != null) {
            setIcon(icon);
        }

        // Listen for Focus events
        addFocusListener(new MenuItemFocusListener());
        setUIProperty("borderPainted", Boolean.FALSE);
        setFocusPainted(false);
        setHorizontalTextPosition(JButton.TRAILING);
        setHorizontalAlignment(JButton.LEADING);
        updateUI();
    }

    private static class MenuItemFocusListener implements FocusListener,
        Serializable {
        public void focusGained(FocusEvent event) {}
        public void focusLost(FocusEvent event) {
            // When focus is lost, repaint if
            // the focus information is painted
            JMenuItem mi = (JMenuItem)event.getSource();
            if(mi.isFocusPainted()) {
                mi.repaint();
            }
        }
    }


    /**
     * Sets the look and feel object that renders this component.
     *
     * <p>
     *  设置呈现此组件的外观和对象对象。
     * 
     * 
     * @param ui  the <code>JMenuItemUI</code> L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(MenuItemUI ui) {
        super.setUI(ui);
    }

    /**
     * Resets the UI property with a value from the current look and feel.
     *
     * <p>
     *  使用当前外观的值重置UI属性。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((MenuItemUI)UIManager.getUI(this));
    }


    /**
     * Returns the suffix used to construct the name of the L&amp;F class used to
     * render this component.
     *
     * <p>
     * 返回用于构造用于渲染此组件的L&amp; F类的名称的后缀。
     * 
     * 
     * @return the string "MenuItemUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Identifies the menu item as "armed". If the mouse button is
     * released while it is over this item, the menu's action event
     * will fire. If the mouse button is released elsewhere, the
     * event will not fire and the menu item will be disarmed.
     *
     * <p>
     *  将菜单项标识为"布防"。如果鼠标按钮在该项目结束时释放,则菜单的动作事件将触发。如果鼠标按钮在其他位置释放,则事件不会触发,菜单项将被撤防。
     * 
     * 
     * @param b true to arm the menu item so it can be selected
     * @beaninfo
     *    description: Mouse release will fire an action event
     *         hidden: true
     */
    public void setArmed(boolean b) {
        ButtonModel model = getModel();

        boolean oldValue = model.isArmed();
        if(model.isArmed() != b) {
            model.setArmed(b);
        }
    }

    /**
     * Returns whether the menu item is "armed".
     *
     * <p>
     *  返回菜单项是否"装备"。
     * 
     * 
     * @return true if the menu item is armed, and it can be selected
     * @see #setArmed
     */
    public boolean isArmed() {
        ButtonModel model = getModel();
        return model.isArmed();
    }

    /**
     * Enables or disables the menu item.
     *
     * <p>
     *  启用或禁用菜单项。
     * 
     * 
     * @param b  true to enable the item
     * @beaninfo
     *    description: Does the component react to user interaction
     *          bound: true
     *      preferred: true
     */
    public void setEnabled(boolean b) {
        // Make sure we aren't armed!
        if (!b && !UIManager.getBoolean("MenuItem.disabledAreNavigable")) {
            setArmed(false);
        }
        super.setEnabled(b);
    }


    /**
     * Returns true since <code>Menu</code>s, by definition,
     * should always be on top of all other windows.  If the menu is
     * in an internal frame false is returned due to the rollover effect
     * for windows laf where the menu is not always on top.
     * <p>
     *  返回true,因为<code> Menu </code>根据定义,应始终位于所有其他窗口之上。如果菜单在内部帧中,由于窗口laf的翻转效果返回false,其中菜单不总是在顶部。
     * 
     */
    // package private
    boolean alwaysOnTop() {
        // Fix for bug #4482165
        if (SwingUtilities.getAncestorOfClass(JInternalFrame.class, this) !=
                null) {
            return false;
        }
        return true;
    }


    /* The keystroke which acts as the menu item's accelerator
    /* <p>
     */
    private KeyStroke accelerator;

    /**
     * Sets the key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy. It is the
     * UI's responsibility to install the correct action.  Note that
     * when the keyboard accelerator is typed, it will work whether or
     * not the menu is currently displayed.
     *
     * <p>
     *  设置调用菜单项的动作侦听器而不导航菜单层次结构的组合键。 UI是负责安装正确的操作。注意,当键入键盘加速器时,无论菜单当前是否显示,它都将工作。
     * 
     * 
     * @param keyStroke the <code>KeyStroke</code> which will
     *          serve as an accelerator
     * @beaninfo
     *     description: The keystroke combination which will invoke the
     *                  JMenuItem's actionlisteners without navigating the
     *                  menu hierarchy
     *           bound: true
     *       preferred: true
     */
    public void setAccelerator(KeyStroke keyStroke) {
        KeyStroke oldAccelerator = accelerator;
        this.accelerator = keyStroke;
        repaint();
        revalidate();
        firePropertyChange("accelerator", oldAccelerator, accelerator);
    }

    /**
     * Returns the <code>KeyStroke</code> which serves as an accelerator
     * for the menu item.
     * <p>
     *  返回用作菜单项加速器的<code> KeyStroke </code>。
     * 
     * 
     * @return a <code>KeyStroke</code> object identifying the
     *          accelerator key
     */
    public KeyStroke getAccelerator() {
        return this.accelerator;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.3
     */
    protected void configurePropertiesFromAction(Action a) {
        super.configurePropertiesFromAction(a);
        configureAcceleratorFromAction(a);
    }

    void setIconFromAction(Action a) {
        Icon icon = null;
        if (a != null) {
            icon = (Icon)a.getValue(Action.SMALL_ICON);
        }
        setIcon(icon);
    }

    void largeIconChanged(Action a) {
    }

    void smallIconChanged(Action a) {
        setIconFromAction(a);
    }

    void configureAcceleratorFromAction(Action a) {
        KeyStroke ks = (a==null) ? null :
            (KeyStroke)a.getValue(Action.ACCELERATOR_KEY);
        setAccelerator(ks);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    protected void actionPropertyChanged(Action action, String propertyName) {
        if (propertyName == Action.ACCELERATOR_KEY) {
            configureAcceleratorFromAction(action);
        }
        else {
            super.actionPropertyChanged(action, propertyName);
        }
    }

    /**
     * Processes a mouse event forwarded from the
     * <code>MenuSelectionManager</code> and changes the menu
     * selection, if necessary, by using the
     * <code>MenuSelectionManager</code>'s API.
     * <p>
     * Note: you do not have to forward the event to sub-components.
     * This is done automatically by the <code>MenuSelectionManager</code>.
     *
     * <p>
     *  处理从<code> MenuSelectionManager </code>转发的鼠标事件,并根据需要使用<code> MenuSelectionManager </code>的API更改菜单选项。
     * <p>
     *  注意：您不必将事件转发到子组件。这是由<code> MenuSelectionManager </code>自动完成的。
     * 
     * 
     * @param e   a <code>MouseEvent</code>
     * @param path  the <code>MenuElement</code> path array
     * @param manager   the <code>MenuSelectionManager</code>
     */
    public void processMouseEvent(MouseEvent e,MenuElement path[],MenuSelectionManager manager) {
        processMenuDragMouseEvent(
                 new MenuDragMouseEvent(e.getComponent(), e.getID(),
                                        e.getWhen(),
                                        e.getModifiers(), e.getX(), e.getY(),
                                        e.getXOnScreen(), e.getYOnScreen(),
                                        e.getClickCount(), e.isPopupTrigger(),
                                        path, manager));
    }


    /**
     * Processes a key event forwarded from the
     * <code>MenuSelectionManager</code> and changes the menu selection,
     * if necessary, by using <code>MenuSelectionManager</code>'s API.
     * <p>
     * Note: you do not have to forward the event to sub-components.
     * This is done automatically by the <code>MenuSelectionManager</code>.
     *
     * <p>
     * 处理从<code> MenuSelectionManager </code>转发的键事件,并根据需要使用<code> MenuSelectionManager </code>的API更改菜单选项。
     * <p>
     *  注意：您不必将事件转发到子组件。这是由<code> MenuSelectionManager </code>自动完成的。
     * 
     * 
     * @param e  a <code>KeyEvent</code>
     * @param path the <code>MenuElement</code> path array
     * @param manager   the <code>MenuSelectionManager</code>
     */
    public void processKeyEvent(KeyEvent e,MenuElement path[],MenuSelectionManager manager) {
        if (DEBUG) {
            System.out.println("in JMenuItem.processKeyEvent/3 for " + getText() +
                                   "  " + KeyStroke.getKeyStrokeForEvent(e));
        }
        MenuKeyEvent mke = new MenuKeyEvent(e.getComponent(), e.getID(),
                                             e.getWhen(), e.getModifiers(),
                                             e.getKeyCode(), e.getKeyChar(),
                                             path, manager);
        processMenuKeyEvent(mke);

        if (mke.isConsumed())  {
            e.consume();
        }
    }



    /**
     * Handles mouse drag in a menu.
     *
     * <p>
     *  在菜单中处理鼠标拖动。
     * 
     * 
     * @param e  a <code>MenuDragMouseEvent</code> object
     */
    public void processMenuDragMouseEvent(MenuDragMouseEvent e) {
        switch (e.getID()) {
        case MouseEvent.MOUSE_ENTERED:
            isMouseDragged = false; fireMenuDragMouseEntered(e); break;
        case MouseEvent.MOUSE_EXITED:
            isMouseDragged = false; fireMenuDragMouseExited(e); break;
        case MouseEvent.MOUSE_DRAGGED:
            isMouseDragged = true; fireMenuDragMouseDragged(e); break;
        case MouseEvent.MOUSE_RELEASED:
            if(isMouseDragged) fireMenuDragMouseReleased(e); break;
        default:
            break;
        }
    }

    /**
     * Handles a keystroke in a menu.
     *
     * <p>
     *  在菜单中处理击键。
     * 
     * 
     * @param e  a <code>MenuKeyEvent</code> object
     */
    public void processMenuKeyEvent(MenuKeyEvent e) {
        if (DEBUG) {
            System.out.println("in JMenuItem.processMenuKeyEvent for " + getText()+
                                   "  " + KeyStroke.getKeyStrokeForEvent(e));
        }
        switch (e.getID()) {
        case KeyEvent.KEY_PRESSED:
            fireMenuKeyPressed(e); break;
        case KeyEvent.KEY_RELEASED:
            fireMenuKeyReleased(e); break;
        case KeyEvent.KEY_TYPED:
            fireMenuKeyTyped(e); break;
        default:
            break;
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuMouseDragEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuDragMouseEntered(MenuDragMouseEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuDragMouseListener.class) {
                // Lazily create the event:
                ((MenuDragMouseListener)listeners[i+1]).menuDragMouseEntered(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuDragMouseEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuDragMouseExited(MenuDragMouseEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuDragMouseListener.class) {
                // Lazily create the event:
                ((MenuDragMouseListener)listeners[i+1]).menuDragMouseExited(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuDragMouseEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuDragMouseDragged(MenuDragMouseEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuDragMouseListener.class) {
                // Lazily create the event:
                ((MenuDragMouseListener)listeners[i+1]).menuDragMouseDragged(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuDragMouseEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuDragMouseReleased(MenuDragMouseEvent event) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuDragMouseListener.class) {
                // Lazily create the event:
                ((MenuDragMouseListener)listeners[i+1]).menuDragMouseReleased(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuKeyEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuKeyPressed(MenuKeyEvent event) {
        if (DEBUG) {
            System.out.println("in JMenuItem.fireMenuKeyPressed for " + getText()+
                                   "  " + KeyStroke.getKeyStrokeForEvent(event));
        }
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
                // Lazily create the event:
                ((MenuKeyListener)listeners[i+1]).menuKeyPressed(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuKeyEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuKeyReleased(MenuKeyEvent event) {
        if (DEBUG) {
            System.out.println("in JMenuItem.fireMenuKeyReleased for " + getText()+
                                   "  " + KeyStroke.getKeyStrokeForEvent(event));
        }
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
                // Lazily create the event:
                ((MenuKeyListener)listeners[i+1]).menuKeyReleased(event);
            }
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     *
     * <p>
     *  通知所有已注册有关此事件类型的通知的收件人。
     * 
     * 
     * @param event a <code>MenuKeyEvent</code>
     * @see EventListenerList
     */
    protected void fireMenuKeyTyped(MenuKeyEvent event) {
        if (DEBUG) {
            System.out.println("in JMenuItem.fireMenuKeyTyped for " + getText()+
                                   "  " + KeyStroke.getKeyStrokeForEvent(event));
        }
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
                // Lazily create the event:
                ((MenuKeyListener)listeners[i+1]).menuKeyTyped(event);
            }
        }
    }

    /**
     * Called by the <code>MenuSelectionManager</code> when the
     * <code>MenuElement</code> is selected or unselected.
     *
     * <p>
     *  当选择或取消选择<code> MenuElement </code>时,由<code> MenuSelectionManager </code>调用。
     * 
     * 
     * @param isIncluded  true if this menu item is on the part of the menu
     *                    path that changed, false if this menu is part of the
     *                    a menu path that changed, but this particular part of
     *                    that path is still the same
     * @see MenuSelectionManager#setSelectedPath(MenuElement[])
     */
    public void menuSelectionChanged(boolean isIncluded) {
        setArmed(isIncluded);
    }

    /**
     * This method returns an array containing the sub-menu
     * components for this menu component.
     *
     * <p>
     *  此方法返回包含此菜单组件的子菜单组件的数组。
     * 
     * 
     * @return an array of <code>MenuElement</code>s
     */
    public MenuElement[] getSubElements() {
        return new MenuElement[0];
    }

    /**
     * Returns the <code>java.awt.Component</code> used to paint
     * this object. The returned component will be used to convert
     * events and detect if an event is inside a menu component.
     *
     * <p>
     *  返回用于绘制此对象的<code> java.awt.Component </code>。返回的组件将用于转换事件并检测菜单组件中是否有事件。
     * 
     * 
     * @return the <code>Component</code> that paints this menu item
     */
    public Component getComponent() {
        return this;
    }

    /**
     * Adds a <code>MenuDragMouseListener</code> to the menu item.
     *
     * <p>
     *  向菜单项中添加<code> MenuDragMouseListener </code>。
     * 
     * 
     * @param l the <code>MenuDragMouseListener</code> to be added
     */
    public void addMenuDragMouseListener(MenuDragMouseListener l) {
        listenerList.add(MenuDragMouseListener.class, l);
    }

    /**
     * Removes a <code>MenuDragMouseListener</code> from the menu item.
     *
     * <p>
     * 从菜单项中删除<code> MenuDragMouseListener </code>。
     * 
     * 
     * @param l the <code>MenuDragMouseListener</code> to be removed
     */
    public void removeMenuDragMouseListener(MenuDragMouseListener l) {
        listenerList.remove(MenuDragMouseListener.class, l);
    }

    /**
     * Returns an array of all the <code>MenuDragMouseListener</code>s added
     * to this JMenuItem with addMenuDragMouseListener().
     *
     * <p>
     *  返回使用addMenuDragMouseListener()添加到此JMenuItem的所有<code> MenuDragMouseListener </code>数组。
     * 
     * 
     * @return all of the <code>MenuDragMouseListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public MenuDragMouseListener[] getMenuDragMouseListeners() {
        return listenerList.getListeners(MenuDragMouseListener.class);
    }

    /**
     * Adds a <code>MenuKeyListener</code> to the menu item.
     *
     * <p>
     *  向菜单项添加<code> MenuKeyListener </code>。
     * 
     * 
     * @param l the <code>MenuKeyListener</code> to be added
     */
    public void addMenuKeyListener(MenuKeyListener l) {
        listenerList.add(MenuKeyListener.class, l);
    }

    /**
     * Removes a <code>MenuKeyListener</code> from the menu item.
     *
     * <p>
     *  从菜单项中删除<code> MenuKeyListener </code>。
     * 
     * 
     * @param l the <code>MenuKeyListener</code> to be removed
     */
    public void removeMenuKeyListener(MenuKeyListener l) {
        listenerList.remove(MenuKeyListener.class, l);
    }

    /**
     * Returns an array of all the <code>MenuKeyListener</code>s added
     * to this JMenuItem with addMenuKeyListener().
     *
     * <p>
     *  返回通过addMenuKeyListener()添加到此JMenuItem的所有<code> MenuKeyListener </code>数组。
     * 
     * 
     * @return all of the <code>MenuKeyListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public MenuKeyListener[] getMenuKeyListeners() {
        return listenerList.getListeners(MenuKeyListener.class);
    }

    /**
     * See JComponent.readObject() for information about serialization
     * in Swing.
     * <p>
     *  有关Swing中序列化的信息,请参阅JComponent.readObject()。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();
        if (getUIClassID().equals(uiClassID)) {
            updateUI();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * Returns a string representation of this <code>JMenuItem</code>.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JMenuItem </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JMenuItem</code>
     */
    protected String paramString() {
        return super.paramString();
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Returns the <code>AccessibleContext</code> associated with this
     * <code>JMenuItem</code>. For <code>JMenuItem</code>s,
     * the <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJMenuItem</code>.
     * A new AccessibleJMenuItme instance is created if necessary.
     *
     * <p>
     *  返回与此<code> JMenuItem </code>关联的<code> AccessibleContext </code>。
     * 对于<code> JMenuItem </code>,<code> AccessibleContext </code>采用<code> AccessibleJMenuItem </code>的形式。
     * 如果需要,将创建一个新的AccessibleJMenuItme实例。
     * 
     * 
     * @return an <code>AccessibleJMenuItem</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JMenuItem</code>
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJMenuItem();
        }
        return accessibleContext;
    }


    /**
     * This class implements accessibility support for the
     * <code>JMenuItem</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to menu item user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  此类实现<code> JMenuItem </code>类的辅助功能支持。它提供了适用于菜单项用户界面元素的Java辅助功能API的实现。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleJMenuItem extends AccessibleAbstractButton implements ChangeListener {

        private boolean isArmed = false;
        private boolean hasFocus = false;
        private boolean isPressed = false;
        private boolean isSelected = false;

        AccessibleJMenuItem() {
            super();
            JMenuItem.this.addChangeListener(this);
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.MENU_ITEM;
        }

        private void fireAccessibilityFocusedEvent(JMenuItem toCheck) {
            MenuElement [] path =
                MenuSelectionManager.defaultManager().getSelectedPath();
            if (path.length > 0) {
                Object menuItem = path[path.length - 1];
                if (toCheck == menuItem) {
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        null, AccessibleState.FOCUSED);
                }
            }
        }

        /**
         * Supports the change listener interface and fires property changes.
         * <p>
         *  支持更改侦听器接口并触发属性更改。
         */
        public void stateChanged(ChangeEvent e) {
            firePropertyChange(AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                               Boolean.valueOf(false), Boolean.valueOf(true));
            if (JMenuItem.this.getModel().isArmed()) {
                if (!isArmed) {
                    isArmed = true;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        null, AccessibleState.ARMED);
                    // Fix for 4848220 moved here to avoid major memory leak
                    // Here we will fire the event in case of JMenuItem
                    // See bug 4910323 for details [zav]
                    fireAccessibilityFocusedEvent(JMenuItem.this);
                }
            } else {
                if (isArmed) {
                    isArmed = false;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        AccessibleState.ARMED, null);
                }
            }
            if (JMenuItem.this.isFocusOwner()) {
                if (!hasFocus) {
                    hasFocus = true;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        null, AccessibleState.FOCUSED);
                }
            } else {
                if (hasFocus) {
                    hasFocus = false;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        AccessibleState.FOCUSED, null);
                }
            }
            if (JMenuItem.this.getModel().isPressed()) {
                if (!isPressed) {
                    isPressed = true;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        null, AccessibleState.PRESSED);
                }
            } else {
                if (isPressed) {
                    isPressed = false;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        AccessibleState.PRESSED, null);
                }
            }
            if (JMenuItem.this.getModel().isSelected()) {
                if (!isSelected) {
                    isSelected = true;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        null, AccessibleState.CHECKED);

                    // Fix for 4848220 moved here to avoid major memory leak
                    // Here we will fire the event in case of JMenu
                    // See bug 4910323 for details [zav]
                    fireAccessibilityFocusedEvent(JMenuItem.this);
                }
            } else {
                if (isSelected) {
                    isSelected = false;
                    firePropertyChange(
                        AccessibleContext.ACCESSIBLE_STATE_PROPERTY,
                        AccessibleState.CHECKED, null);
                }
            }

        }
    } // inner class AccessibleJMenuItem


}
