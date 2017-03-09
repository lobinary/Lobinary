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

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.beans.*;

import java.util.Locale;
import java.util.Vector;
import java.util.Hashtable;
import javax.accessibility.*;
import javax.swing.plaf.PopupMenuUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.event.*;

import sun.awt.SunToolkit;
import sun.security.util.SecurityConstants;

import java.applet.Applet;

/**
 * An implementation of a popup menu -- a small window that pops up
 * and displays a series of choices. A <code>JPopupMenu</code> is used for the
 * menu that appears when the user selects an item on the menu bar.
 * It is also used for "pull-right" menu that appears when the
 * selects a menu item that activates it. Finally, a <code>JPopupMenu</code>
 * can also be used anywhere else you want a menu to appear.  For
 * example, when the user right-clicks in a specified area.
 * <p>
 * For information and examples of using popup menus, see
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
 * description: A small window that pops up and displays a series of choices.
 *
 * <p>
 *  弹出菜单的实现 - 一个小窗口弹出并显示一系列选择。 <code> JPopupMenu </code>用于当用户选择菜单栏上的项目时出现的菜单。它也用于当选择激活它的菜单项时出现的"拉右"菜单。
 * 最后,一个<code> JPopupMenu </code>也可以在你想要菜单出现的任何地方使用。例如,当用户在指定区域中右键单击时。
 * <p>
 *  有关使用弹出式菜单的信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html">如何
 * 使用菜单</a>。
 *  Java教程。</em>。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 *  @beaninfo属性：isContainer false description：一个小窗口,弹出并显示一系列选择。
 * 
 * 
 * @author Georges Saab
 * @author David Karlton
 * @author Arnaud Weber
 */
@SuppressWarnings("serial")
public class JPopupMenu extends JComponent implements Accessible,MenuElement {

    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "PopupMenuUI";

    /**
     * Key used in AppContext to determine if light way popups are the default.
     * <p>
     * 在AppContext中使用的键来确定光照方式弹出窗口是否为默认。
     * 
     */
    private static final Object defaultLWPopupEnabledKey =
        new StringBuffer("JPopupMenu.defaultLWPopupEnabledKey");

    /** Bug#4425878-Property javax.swing.adjustPopupLocationToFit introduced */
    static boolean popupPostionFixDisabled = false;

    static {
        popupPostionFixDisabled = java.security.AccessController.doPrivileged(
                new sun.security.action.GetPropertyAction(
                "javax.swing.adjustPopupLocationToFit","")).equals("false");

    }

    transient  Component invoker;
    transient  Popup popup;
    transient  Frame frame;
    private    int desiredLocationX,desiredLocationY;

    private    String     label                   = null;
    private    boolean   paintBorder              = true;
    private    Insets    margin                   = null;

    /**
     * Used to indicate if lightweight popups should be used.
     * <p>
     *  用于指示是否应使用轻量级弹出窗口。
     * 
     */
    private    boolean   lightWeightPopup         = true;

    /*
     * Model for the selected subcontrol.
     * <p>
     *  所选子控件的模型。
     * 
     */
    private SingleSelectionModel selectionModel;

    /* Lock object used in place of class object for synchronization.
     * (4187686)
     * <p>
     *  (4187686)
     * 
     */
    private static final Object classLock = new Object();

    /* diagnostic aids -- should be false for production builds. */
    private static final boolean TRACE =   false; // trace creates and disposes
    private static final boolean VERBOSE = false; // show reuse hits/misses
    private static final boolean DEBUG =   false;  // show bad params, misc.

    /**
     *  Sets the default value of the <code>lightWeightPopupEnabled</code>
     *  property.
     *
     * <p>
     *  设置<code> lightWeightPopupEnabled </code>属性的默认值。
     * 
     * 
     *  @param aFlag <code>true</code> if popups can be lightweight,
     *               otherwise <code>false</code>
     *  @see #getDefaultLightWeightPopupEnabled
     *  @see #setLightWeightPopupEnabled
     */
    public static void setDefaultLightWeightPopupEnabled(boolean aFlag) {
        SwingUtilities.appContextPut(defaultLWPopupEnabledKey,
                                     Boolean.valueOf(aFlag));
    }

    /**
     *  Gets the <code>defaultLightWeightPopupEnabled</code> property,
     *  which by default is <code>true</code>.
     *
     * <p>
     *  获取<code> defaultLightWeightPopupEnabled </code>属性,默认情况下为<code> true </code>。
     * 
     * 
     *  @return the value of the <code>defaultLightWeightPopupEnabled</code>
     *          property
     *
     *  @see #setDefaultLightWeightPopupEnabled
     */
    public static boolean getDefaultLightWeightPopupEnabled() {
        Boolean b = (Boolean)
            SwingUtilities.appContextGet(defaultLWPopupEnabledKey);
        if (b == null) {
            SwingUtilities.appContextPut(defaultLWPopupEnabledKey,
                                         Boolean.TRUE);
            return true;
        }
        return b.booleanValue();
    }

    /**
     * Constructs a <code>JPopupMenu</code> without an "invoker".
     * <p>
     *  构造一个没有"调用者"的<code> JPopupMenu </code>。
     * 
     */
    public JPopupMenu() {
        this(null);
    }

    /**
     * Constructs a <code>JPopupMenu</code> with the specified title.
     *
     * <p>
     *  构造具有指定标题的<code> JPopupMenu </code>。
     * 
     * 
     * @param label  the string that a UI may use to display as a title
     * for the popup menu.
     */
    public JPopupMenu(String label) {
        this.label = label;
        lightWeightPopup = getDefaultLightWeightPopupEnabled();
        setSelectionModel(new DefaultSingleSelectionModel());
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setFocusTraversalKeysEnabled(false);
        updateUI();
    }



    /**
     * Returns the look and feel (L&amp;F) object that renders this component.
     *
     * <p>
     *  返回呈现此组件的外观和感觉(L&amp; F)对象。
     * 
     * 
     * @return the <code>PopupMenuUI</code> object that renders this component
     */
    public PopupMenuUI getUI() {
        return (PopupMenuUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui the new <code>PopupMenuUI</code> L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(PopupMenuUI ui) {
        super.setUI(ui);
    }

    /**
     * Resets the UI property to a value from the current look and feel.
     *
     * <p>
     *  将UI属性重置为当前外观的值。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((PopupMenuUI)UIManager.getUI(this));
    }


    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "PopupMenuUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    protected void processFocusEvent(FocusEvent evt) {
        super.processFocusEvent(evt);
    }

    /**
     * Processes key stroke events such as mnemonics and accelerators.
     *
     * <p>
     *  处理关键笔划事件,如助记符和加速器。
     * 
     * 
     * @param evt  the key event to be processed
     */
    protected void processKeyEvent(KeyEvent evt) {
        MenuSelectionManager.defaultManager().processKeyEvent(evt);
        if (evt.isConsumed()) {
            return;
        }
        super.processKeyEvent(evt);
    }


    /**
     * Returns the model object that handles single selections.
     *
     * <p>
     *  返回处理单个选择的模型对象。
     * 
     * 
     * @return the <code>selectionModel</code> property
     * @see SingleSelectionModel
     */
    public SingleSelectionModel getSelectionModel() {
        return selectionModel;
    }

    /**
     * Sets the model object to handle single selections.
     *
     * <p>
     *  设置模型对象以处理单个选择。
     * 
     * 
     * @param model the new <code>SingleSelectionModel</code>
     * @see SingleSelectionModel
     * @beaninfo
     * description: The selection model for the popup menu
     *      expert: true
     */
    public void setSelectionModel(SingleSelectionModel model) {
        selectionModel = model;
    }

    /**
     * Appends the specified menu item to the end of this menu.
     *
     * <p>
     *  将指定的菜单项添加到此菜单的末尾。
     * 
     * 
     * @param menuItem the <code>JMenuItem</code> to add
     * @return the <code>JMenuItem</code> added
     */
    public JMenuItem add(JMenuItem menuItem) {
        super.add(menuItem);
        return menuItem;
    }

    /**
     * Creates a new menu item with the specified text and appends
     * it to the end of this menu.
     *
     * <p>
     *  创建具有指定文本的新菜单项,并将其附加到此菜单的末尾。
     * 
     * 
     * @param s the string for the menu item to be added
     */
    public JMenuItem add(String s) {
        return add(new JMenuItem(s));
    }

    /**
     * Appends a new menu item to the end of the menu which
     * dispatches the specified <code>Action</code> object.
     *
     * <p>
     *  将新的菜单项附加到菜单的末尾,该菜单项分派指定的<code> Action </code>对象。
     * 
     * 
     * @param a the <code>Action</code> to add to the menu
     * @return the new menu item
     * @see Action
     */
    public JMenuItem add(Action a) {
        JMenuItem mi = createActionComponent(a);
        mi.setAction(a);
        add(mi);
        return mi;
    }

    /**
     * Returns an point which has been adjusted to take into account of the
     * desktop bounds, taskbar and multi-monitor configuration.
     * <p>
     * This adustment may be cancelled by invoking the application with
     * -Djavax.swing.adjustPopupLocationToFit=false
     * <p>
     *  返回已调整以考虑桌面边界,任务栏和多显示器配置的点。
     * <p>
     *  此调用可以通过调用具有-Djavax.swing.adjustPopupLocationToFit = false的应用程序来取消
     * 
     */
    Point adjustPopupLocationToFitScreen(int xPosition, int yPosition) {
        Point popupLocation = new Point(xPosition, yPosition);

        if(popupPostionFixDisabled == true || GraphicsEnvironment.isHeadless()) {
            return popupLocation;
        }

        // Get screen bounds
        Rectangle scrBounds;
        GraphicsConfiguration gc = getCurrentGraphicsConfiguration(popupLocation);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if(gc != null) {
            // If we have GraphicsConfiguration use it to get screen bounds
            scrBounds = gc.getBounds();
        } else {
            // If we don't have GraphicsConfiguration use primary screen
            scrBounds = new Rectangle(toolkit.getScreenSize());
        }

        // Calculate the screen size that popup should fit
        Dimension popupSize = JPopupMenu.this.getPreferredSize();
        long popupRightX = (long)popupLocation.x + (long)popupSize.width;
        long popupBottomY = (long)popupLocation.y + (long)popupSize.height;
        int scrWidth = scrBounds.width;
        int scrHeight = scrBounds.height;

        if (!canPopupOverlapTaskBar()) {
            // Insets include the task bar. Take them into account.
            Insets scrInsets = toolkit.getScreenInsets(gc);
            scrBounds.x += scrInsets.left;
            scrBounds.y += scrInsets.top;
            scrWidth -= scrInsets.left + scrInsets.right;
            scrHeight -= scrInsets.top + scrInsets.bottom;
        }
        int scrRightX = scrBounds.x + scrWidth;
        int scrBottomY = scrBounds.y + scrHeight;

        // Ensure that popup menu fits the screen
        if (popupRightX > (long) scrRightX) {
            popupLocation.x = scrRightX - popupSize.width;
        }

        if (popupBottomY > (long) scrBottomY) {
            popupLocation.y = scrBottomY - popupSize.height;
        }

        if (popupLocation.x < scrBounds.x) {
            popupLocation.x = scrBounds.x;
        }

        if (popupLocation.y < scrBounds.y) {
            popupLocation.y = scrBounds.y;
        }

        return popupLocation;
    }

    /**
     * Tries to find GraphicsConfiguration
     * that contains the mouse cursor position.
     * Can return null.
     * <p>
     * 尝试查找包含鼠标光标位置的GraphicsConfiguration。可以返回null。
     * 
     */
    private GraphicsConfiguration getCurrentGraphicsConfiguration(
            Point popupLocation) {
        GraphicsConfiguration gc = null;
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        for(int i = 0; i < gd.length; i++) {
            if(gd[i].getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
                GraphicsConfiguration dgc =
                    gd[i].getDefaultConfiguration();
                if(dgc.getBounds().contains(popupLocation)) {
                    gc = dgc;
                    break;
                }
            }
        }
        // If not found and we have invoker, ask invoker about his gc
        if(gc == null && getInvoker() != null) {
            gc = getInvoker().getGraphicsConfiguration();
        }
        return gc;
    }

    /**
     * Returns whether popup is allowed to be shown above the task bar.
     * <p>
     *  返回是否允许弹出窗口显示在任务栏上方。
     * 
     */
    static boolean canPopupOverlapTaskBar() {
        boolean result = true;

        Toolkit tk = Toolkit.getDefaultToolkit();
        if (tk instanceof SunToolkit) {
            result = ((SunToolkit)tk).canPopupOverlapTaskBar();
        }

        return result;
    }

    /**
     * Factory method which creates the <code>JMenuItem</code> for
     * <code>Actions</code> added to the <code>JPopupMenu</code>.
     *
     * <p>
     *  为添加到<code> JPopupMenu </code>中的<code> Actions </code>创建<code> JMenuItem </code>的工厂方法。
     * 
     * 
     * @param a the <code>Action</code> for the menu item to be added
     * @return the new menu item
     * @see Action
     *
     * @since 1.3
     */
    protected JMenuItem createActionComponent(Action a) {
        JMenuItem mi = new JMenuItem() {
            protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
                PropertyChangeListener pcl = createActionChangeListener(this);
                if (pcl == null) {
                    pcl = super.createActionPropertyChangeListener(a);
                }
                return pcl;
            }
        };
        mi.setHorizontalTextPosition(JButton.TRAILING);
        mi.setVerticalTextPosition(JButton.CENTER);
        return mi;
    }

    /**
     * Returns a properly configured <code>PropertyChangeListener</code>
     * which updates the control as changes to the <code>Action</code> occur.
     * <p>
     *  返回正确配置的<code> PropertyChangeListener </code>,该更改将随着对<code> Action </code>发生的更改而更新控件。
     * 
     */
    protected PropertyChangeListener createActionChangeListener(JMenuItem b) {
        return b.createActionPropertyChangeListener0(b.getAction());
    }

    /**
     * Removes the component at the specified index from this popup menu.
     *
     * <p>
     *  从此弹出菜单中删除指定索引处的组件。
     * 
     * 
     * @param       pos the position of the item to be removed
     * @exception   IllegalArgumentException if the value of
     *                          <code>pos</code> &lt; 0, or if the value of
     *                          <code>pos</code> is greater than the
     *                          number of items
     */
    public void remove(int pos) {
        if (pos < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }
        if (pos > getComponentCount() -1) {
            throw new IllegalArgumentException("index greater than the number of items.");
        }
        super.remove(pos);
    }

    /**
     * Sets the value of the <code>lightWeightPopupEnabled</code> property,
     * which by default is <code>true</code>.
     * By default, when a look and feel displays a popup,
     * it can choose to
     * use a lightweight (all-Java) popup.
     * Lightweight popup windows are more efficient than heavyweight
     * (native peer) windows,
     * but lightweight and heavyweight components do not mix well in a GUI.
     * If your application mixes lightweight and heavyweight components,
     * you should disable lightweight popups.
     * Some look and feels might always use heavyweight popups,
     * no matter what the value of this property.
     *
     * <p>
     *  设置<code> lightWeightPopupEnabled </code>属性的值,默认为<code> true </code>。
     * 默认情况下,当外观显示弹出窗口时,它可以选择使用轻量级(全Java)弹出窗口。轻量级弹出窗口比重量级(本地对等)窗口更高效,但轻量级和重量级组件在GUI中不能很好地混合。
     * 如果您的应用程序混合轻量级和重量级组件,则应禁用轻量级弹出窗口。一些外观和感觉可能总是使用重量级弹出窗口,无论这个属性的值。
     * 
     * 
     * @param aFlag  <code>false</code> to disable lightweight popups
     * @beaninfo
     * description: Determines whether lightweight popups are used when possible
     *      expert: true
     *
     * @see #isLightWeightPopupEnabled
     */
    public void setLightWeightPopupEnabled(boolean aFlag) {
        // NOTE: this use to set the flag on a shared JPopupMenu, which meant
        // this effected ALL JPopupMenus.
        lightWeightPopup = aFlag;
    }

    /**
     * Gets the <code>lightWeightPopupEnabled</code> property.
     *
     * <p>
     *  获取<code> lightWeightPopupEnabled </code>属性。
     * 
     * 
     * @return the value of the <code>lightWeightPopupEnabled</code> property
     * @see #setLightWeightPopupEnabled
     */
    public boolean isLightWeightPopupEnabled() {
        return lightWeightPopup;
    }

    /**
     * Returns the popup menu's label
     *
     * <p>
     *  返回弹出菜单的标签
     * 
     * 
     * @return a string containing the popup menu's label
     * @see #setLabel
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the popup menu's label.  Different look and feels may choose
     * to display or not display this.
     *
     * <p>
     *  设置弹出菜单的标签。不同的外观和感觉可以选择显示或不显示此。
     * 
     * 
     * @param label a string specifying the label for the popup menu
     *
     * @see #setLabel
     * @beaninfo
     * description: The label for the popup menu.
     *       bound: true
     */
    public void setLabel(String label) {
        String oldValue = this.label;
        this.label = label;
        firePropertyChange("label", oldValue, label);
        if (accessibleContext != null) {
            accessibleContext.firePropertyChange(
                AccessibleContext.ACCESSIBLE_VISIBLE_DATA_PROPERTY,
                oldValue, label);
        }
        invalidate();
        repaint();
    }

    /**
     * Appends a new separator at the end of the menu.
     * <p>
     *  在菜单末尾追加一个新的分隔符。
     * 
     */
    public void addSeparator() {
        add( new JPopupMenu.Separator() );
    }

    /**
     * Inserts a menu item for the specified <code>Action</code> object at
     * a given position.
     *
     * <p>
     *  在给定位置插入指定<code> Action </code>对象的菜单项。
     * 
     * 
     * @param a  the <code>Action</code> object to insert
     * @param index      specifies the position at which to insert the
     *                   <code>Action</code>, where 0 is the first
     * @exception IllegalArgumentException if <code>index</code> &lt; 0
     * @see Action
     */
    public void insert(Action a, int index) {
        JMenuItem mi = createActionComponent(a);
        mi.setAction(a);
        insert(mi, index);
    }

    /**
     * Inserts the specified component into the menu at a given
     * position.
     *
     * <p>
     *  将指定的组件插入到给定位置的菜单中。
     * 
     * 
     * @param component  the <code>Component</code> to insert
     * @param index      specifies the position at which
     *                   to insert the component, where 0 is the first
     * @exception IllegalArgumentException if <code>index</code> &lt; 0
     */
    public void insert(Component component, int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index less than zero.");
        }

        int nitems = getComponentCount();
        // PENDING(ges): Why not use an array?
        Vector<Component> tempItems = new Vector<Component>();

        /* Remove the item at index, nitems-index times
           storing them in a temporary vector in the
           order they appear on the menu.
        /* <p>
        /* 将它们按照它们在菜单上出现的顺序存储在临时向量中。
        /* 
           */
        for (int i = index ; i < nitems; i++) {
            tempItems.addElement(getComponent(index));
            remove(index);
        }

        add(component);

        /* Add the removed items back to the menu, they are
           already in the correct order in the temp vector.
        /* <p>
        /*  已经在temp矢量中的正确顺序。
        /* 
           */
        for (Component tempItem : tempItems) {
            add(tempItem);
        }
    }

    /**
     *  Adds a <code>PopupMenu</code> listener.
     *
     * <p>
     *  添加<code> PopupMenu </code>侦听器。
     * 
     * 
     *  @param l  the <code>PopupMenuListener</code> to add
     */
    public void addPopupMenuListener(PopupMenuListener l) {
        listenerList.add(PopupMenuListener.class,l);
    }

    /**
     * Removes a <code>PopupMenu</code> listener.
     *
     * <p>
     *  删除<code> PopupMenu </code>侦听器。
     * 
     * 
     * @param l  the <code>PopupMenuListener</code> to remove
     */
    public void removePopupMenuListener(PopupMenuListener l) {
        listenerList.remove(PopupMenuListener.class,l);
    }

    /**
     * Returns an array of all the <code>PopupMenuListener</code>s added
     * to this JMenuItem with addPopupMenuListener().
     *
     * <p>
     *  返回通过addPopupMenuListener()添加到此JMenuItem的所有<code> PopupMenuListener </code>数组。
     * 
     * 
     * @return all of the <code>PopupMenuListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public PopupMenuListener[] getPopupMenuListeners() {
        return listenerList.getListeners(PopupMenuListener.class);
    }

    /**
     * Adds a <code>MenuKeyListener</code> to the popup menu.
     *
     * <p>
     *  在弹出式菜单中添加<code> MenuKeyListener </code>。
     * 
     * 
     * @param l the <code>MenuKeyListener</code> to be added
     * @since 1.5
     */
    public void addMenuKeyListener(MenuKeyListener l) {
        listenerList.add(MenuKeyListener.class, l);
    }

    /**
     * Removes a <code>MenuKeyListener</code> from the popup menu.
     *
     * <p>
     *  从弹出式菜单中删除<code> MenuKeyListener </code>。
     * 
     * 
     * @param l the <code>MenuKeyListener</code> to be removed
     * @since 1.5
     */
    public void removeMenuKeyListener(MenuKeyListener l) {
        listenerList.remove(MenuKeyListener.class, l);
    }

    /**
     * Returns an array of all the <code>MenuKeyListener</code>s added
     * to this JPopupMenu with addMenuKeyListener().
     *
     * <p>
     *  返回使用addMenuKeyListener()添加到此JPopupMenu的所有<code> MenuKeyListener </code>的数组。
     * 
     * 
     * @return all of the <code>MenuKeyListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.5
     */
    public MenuKeyListener[] getMenuKeyListeners() {
        return listenerList.getListeners(MenuKeyListener.class);
    }

    /**
     * Notifies <code>PopupMenuListener</code>s that this popup menu will
     * become visible.
     * <p>
     *  通知<code> PopupMenuListener </code>这个弹出式菜单将变为可见。
     * 
     */
    protected void firePopupMenuWillBecomeVisible() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuWillBecomeVisible(e);
            }
        }
    }

    /**
     * Notifies <code>PopupMenuListener</code>s that this popup menu will
     * become invisible.
     * <p>
     *  通知<code> PopupMenuListener </code>表示此弹出式菜单将不可见。
     * 
     */
    protected void firePopupMenuWillBecomeInvisible() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuWillBecomeInvisible(e);
            }
        }
    }

    /**
     * Notifies <code>PopupMenuListeners</code> that this popup menu is
     * cancelled.
     * <p>
     *  通知<code> PopupMenuListeners </code>此弹出式菜单已取消。
     * 
     */
    protected void firePopupMenuCanceled() {
        Object[] listeners = listenerList.getListenerList();
        PopupMenuEvent e=null;
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==PopupMenuListener.class) {
                if (e == null)
                    e = new PopupMenuEvent(this);
                ((PopupMenuListener)listeners[i+1]).popupMenuCanceled(e);
            }
        }
    }

    /**
     * Always returns true since popups, by definition, should always
     * be on top of all other windows.
     * <p>
     *  始终返回true,因为根据定义,弹出窗口应始终位于所有其他窗口之上。
     * 
     * 
     * @return true
     */
    // package private
    boolean alwaysOnTop() {
        return true;
    }

    /**
     * Lays out the container so that it uses the minimum space
     * needed to display its contents.
     * <p>
     *  放出容器,以便使用显示其内容所需的最小空间。
     * 
     */
    public void pack() {
        if(popup != null) {
            Dimension pref = getPreferredSize();

            if (pref == null || pref.width != getWidth() ||
                                pref.height != getHeight()) {
                showPopup();
            } else {
                validate();
            }
        }
    }

    /**
     * Sets the visibility of the popup menu.
     *
     * <p>
     *  设置弹出菜单的可见性。
     * 
     * 
     * @param b true to make the popup visible, or false to
     *          hide it
     * @beaninfo
     *           bound: true
     *     description: Makes the popup visible
     */
    public void setVisible(boolean b) {
        if (DEBUG) {
            System.out.println("JPopupMenu.setVisible " + b);
        }

        // Is it a no-op?
        if (b == isVisible())
            return;

        // if closing, first close all Submenus
        if (b == false) {

            // 4234793: This is a workaround because JPopupMenu.firePopupMenuCanceled is
            // a protected method and cannot be called from BasicPopupMenuUI directly
            // The real solution could be to make
            // firePopupMenuCanceled public and call it directly.
            Boolean doCanceled = (Boolean)getClientProperty("JPopupMenu.firePopupMenuCanceled");
            if (doCanceled != null && doCanceled == Boolean.TRUE) {
                putClientProperty("JPopupMenu.firePopupMenuCanceled", Boolean.FALSE);
                firePopupMenuCanceled();
            }
            getSelectionModel().clearSelection();

        } else {
            // This is a popup menu with MenuElement children,
            // set selection path before popping up!
            if (isPopupMenu()) {
                MenuElement me[] = new MenuElement[1];
                me[0] = this;
                MenuSelectionManager.defaultManager().setSelectedPath(me);
            }
        }

        if(b) {
            firePopupMenuWillBecomeVisible();
            showPopup();
            firePropertyChange("visible", Boolean.FALSE, Boolean.TRUE);


        } else if(popup != null) {
            firePopupMenuWillBecomeInvisible();
            popup.hide();
            popup = null;
            firePropertyChange("visible", Boolean.TRUE, Boolean.FALSE);
            // 4694797: When popup menu is made invisible, selected path
            // should be cleared
            if (isPopupMenu()) {
                MenuSelectionManager.defaultManager().clearSelectedPath();
            }
        }
    }

    /**
     * Retrieves <code>Popup</code> instance from the
     * <code>PopupMenuUI</code> that has had <code>show</code> invoked on
     * it. If the current <code>popup</code> is non-null,
     * this will invoke <code>dispose</code> of it, and then
     * <code>show</code> the new one.
     * <p>
     * This does NOT fire any events, it is up the caller to dispatch
     * the necessary events.
     * <p>
     *  从已经调用<code> show </code>的<code> PopupMenuUI </code>中检索<code> Popup </code>如果当前<code>弹出</code>是非null,
     * 这将调用<code> dispose </code>,然后<code>显示</code>新的。
     * <p>
     *  这不会触发任何事件,它是调用者调度必要的事件。
     * 
     */
    private void showPopup() {
        Popup oldPopup = popup;

        if (oldPopup != null) {
            oldPopup.hide();
        }
        PopupFactory popupFactory = PopupFactory.getSharedInstance();

        if (isLightWeightPopupEnabled()) {
            popupFactory.setPopupType(PopupFactory.LIGHT_WEIGHT_POPUP);
        }
        else {
            popupFactory.setPopupType(PopupFactory.HEAVY_WEIGHT_POPUP);
        }

        // adjust the location of the popup
        Point p = adjustPopupLocationToFitScreen(desiredLocationX,desiredLocationY);
        desiredLocationX = p.x;
        desiredLocationY = p.y;

        Popup newPopup = getUI().getPopup(this, desiredLocationX,
                                          desiredLocationY);

        popupFactory.setPopupType(PopupFactory.LIGHT_WEIGHT_POPUP);
        popup = newPopup;
        newPopup.show();
    }

    /**
     * Returns true if the popup menu is visible (currently
     * being displayed).
     * <p>
     *  如果弹出菜单可见(当前正在显示),则返回true。
     * 
     */
    public boolean isVisible() {
        return popup != null;
    }

    /**
     * Sets the location of the upper left corner of the
     * popup menu using x, y coordinates.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code JPopupMenu} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     * 使用x,y坐标设置弹出式菜单左上角的位置。
     * <p>
     *  该方法更改几何相关数据。因此,本地窗口系统可以忽略这样的请求,或者它可以修改所请求的数据,使得{@code JPopupMenu}对象以与桌面设置紧密对应的方式放置和调整大小。
     * 
     * 
     * @param x the x coordinate of the popup's new position
     *          in the screen's coordinate space
     * @param y the y coordinate of the popup's new position
     *          in the screen's coordinate space
     * @beaninfo
     * description: The location of the popup menu.
     */
    public void setLocation(int x, int y) {
        int oldX = desiredLocationX;
        int oldY = desiredLocationY;

        desiredLocationX = x;
        desiredLocationY = y;
        if(popup != null && (x != oldX || y != oldY)) {
            showPopup();
        }
    }

    /**
     * Returns true if the popup menu is a standalone popup menu
     * rather than the submenu of a <code>JMenu</code>.
     *
     * <p>
     *  如果弹出菜单是一个独立的弹出菜单,而不是<code> JMenu </code>的子菜单,则返回true。
     * 
     * 
     * @return true if this menu is a standalone popup menu, otherwise false
     */
    private boolean isPopupMenu() {
        return  ((invoker != null) && !(invoker instanceof JMenu));
    }

    /**
     * Returns the component which is the 'invoker' of this
     * popup menu.
     *
     * <p>
     *  返回此弹出式菜单的"调用者"组件。
     * 
     * 
     * @return the <code>Component</code> in which the popup menu is displayed
     */
    public Component getInvoker() {
        return this.invoker;
    }

    /**
     * Sets the invoker of this popup menu -- the component in which
     * the popup menu menu is to be displayed.
     *
     * <p>
     *  设置此弹出菜单的调用器 - 要显示弹出菜单菜单的组件。
     * 
     * 
     * @param invoker the <code>Component</code> in which the popup
     *          menu is displayed
     * @beaninfo
     * description: The invoking component for the popup menu
     *      expert: true
     */
    public void setInvoker(Component invoker) {
        Component oldInvoker = this.invoker;
        this.invoker = invoker;
        if ((oldInvoker != this.invoker) && (ui != null)) {
            ui.uninstallUI(this);
            ui.installUI(this);
        }
        invalidate();
    }

    /**
     * Displays the popup menu at the position x,y in the coordinate
     * space of the component invoker.
     *
     * <p>
     *  在组件调用器的坐标空间中的位置x,y处显示弹出菜单。
     * 
     * 
     * @param invoker the component in whose space the popup menu is to appear
     * @param x the x coordinate in invoker's coordinate space at which
     * the popup menu is to be displayed
     * @param y the y coordinate in invoker's coordinate space at which
     * the popup menu is to be displayed
     */
    public void show(Component invoker, int x, int y) {
        if (DEBUG) {
            System.out.println("in JPopupMenu.show " );
        }
        setInvoker(invoker);
        Frame newFrame = getFrame(invoker);
        if (newFrame != frame) {
            // Use the invoker's frame so that events
            // are propagated properly
            if (newFrame!=null) {
                this.frame = newFrame;
                if(popup != null) {
                    setVisible(false);
                }
            }
        }
        Point invokerOrigin;
        if (invoker != null) {
            invokerOrigin = invoker.getLocationOnScreen();

            // To avoid integer overflow
            long lx, ly;
            lx = ((long) invokerOrigin.x) +
                 ((long) x);
            ly = ((long) invokerOrigin.y) +
                 ((long) y);
            if(lx > Integer.MAX_VALUE) lx = Integer.MAX_VALUE;
            if(lx < Integer.MIN_VALUE) lx = Integer.MIN_VALUE;
            if(ly > Integer.MAX_VALUE) ly = Integer.MAX_VALUE;
            if(ly < Integer.MIN_VALUE) ly = Integer.MIN_VALUE;

            setLocation((int) lx, (int) ly);
        } else {
            setLocation(x, y);
        }
        setVisible(true);
    }

    /**
     * Returns the popup menu which is at the root of the menu system
     * for this popup menu.
     *
     * <p>
     *  返回此弹出菜单的菜单系统根目录下的弹出菜单。
     * 
     * 
     * @return the topmost grandparent <code>JPopupMenu</code>
     */
    JPopupMenu getRootPopupMenu() {
        JPopupMenu mp = this;
        while((mp!=null) && (mp.isPopupMenu()!=true) &&
              (mp.getInvoker() != null) &&
              (mp.getInvoker().getParent() != null) &&
              (mp.getInvoker().getParent() instanceof JPopupMenu)
              ) {
            mp = (JPopupMenu) mp.getInvoker().getParent();
        }
        return mp;
    }

    /**
     * Returns the component at the specified index.
     *
     * <p>
     *  返回指定索引处的组件。
     * 
     * 
     * @param i  the index of the component, where 0 is the first
     * @return the <code>Component</code> at that index
     * @deprecated replaced by {@link java.awt.Container#getComponent(int)}
     */
    @Deprecated
    public Component getComponentAtIndex(int i) {
        return getComponent(i);
    }

    /**
     * Returns the index of the specified component.
     *
     * <p>
     *  返回指定组件的索引。
     * 
     * 
     * @param  c the <code>Component</code> to find
     * @return the index of the component, where 0 is the first;
     *         or -1 if the component is not found
     */
    public int getComponentIndex(Component c) {
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for (int i = 0 ; i < ncomponents ; i++) {
            Component comp = component[i];
            if (comp == c)
                return i;
        }
        return -1;
    }

    /**
     * Sets the size of the Popup window using a <code>Dimension</code> object.
     * This is equivalent to <code>setPreferredSize(d)</code>.
     *
     * <p>
     *  使用<code> Dimension </code>对象设置Popup窗口的大小。这相当于<code> setPreferredSize(d)</code>。
     * 
     * 
     * @param d   the <code>Dimension</code> specifying the new size
     * of this component.
     * @beaninfo
     * description: The size of the popup menu
     */
    public void setPopupSize(Dimension d) {
        Dimension oldSize = getPreferredSize();

        setPreferredSize(d);
        if (popup != null) {
            Dimension newSize = getPreferredSize();

            if (!oldSize.equals(newSize)) {
                showPopup();
            }
        }
    }

    /**
     * Sets the size of the Popup window to the specified width and
     * height. This is equivalent to
     *  <code>setPreferredSize(new Dimension(width, height))</code>.
     *
     * <p>
     *  将弹出窗口的大小设置为指定的宽度和高度。这相当于<code> setPreferredSize(new Dimension(width,height))</code>。
     * 
     * 
     * @param width the new width of the Popup in pixels
     * @param height the new height of the Popup in pixels
     * @beaninfo
     * description: The size of the popup menu
     */
    public void setPopupSize(int width, int height) {
        setPopupSize(new Dimension(width, height));
    }

    /**
     * Sets the currently selected component,  This will result
     * in a change to the selection model.
     *
     * <p>
     *  设置当前选定的组件,这将导致选择模型的更改。
     * 
     * 
     * @param sel the <code>Component</code> to select
     * @beaninfo
     * description: The selected component on the popup menu
     *      expert: true
     *      hidden: true
     */
    public void setSelected(Component sel) {
        SingleSelectionModel model = getSelectionModel();
        int index = getComponentIndex(sel);
        model.setSelectedIndex(index);
    }

    /**
     * Checks whether the border should be painted.
     *
     * <p>
     *  检查是否应该绘制边框。
     * 
     * 
     * @return true if the border is painted, false otherwise
     * @see #setBorderPainted
     */
    public boolean isBorderPainted() {
        return paintBorder;
    }

    /**
     * Sets whether the border should be painted.
     *
     * <p>
     *  设置是否应该绘制边框。
     * 
     * 
     * @param b if true, the border is painted.
     * @see #isBorderPainted
     * @beaninfo
     * description: Is the border of the popup menu painted
     */
    public void setBorderPainted(boolean b) {
        paintBorder = b;
        repaint();
    }

    /**
     * Paints the popup menu's border if the <code>borderPainted</code>
     * property is <code>true</code>.
     * <p>
     *  如果<code> borderPainted </code>属性是<code> true </code>,则绘制弹出菜单的边框。
     * 
     * 
     * @param g  the <code>Graphics</code> object
     *
     * @see JComponent#paint
     * @see JComponent#setBorder
     */
    protected void paintBorder(Graphics g) {
        if (isBorderPainted()) {
            super.paintBorder(g);
        }
    }

    /**
     * Returns the margin, in pixels, between the popup menu's border and
     * its containers.
     *
     * <p>
     * 返回弹出菜单边框和其容器之间的边距(以像素为单位)。
     * 
     * 
     * @return an <code>Insets</code> object containing the margin values.
     */
    public Insets getMargin() {
        if(margin == null) {
            return new Insets(0,0,0,0);
        } else {
            return margin;
        }
    }


    /**
     * Examines the list of menu items to determine whether
     * <code>popup</code> is a popup menu.
     *
     * <p>
     *  检查菜单项列表以确定<code>弹出</code>是否是弹出式菜单。
     * 
     * 
     * @param popup  a <code>JPopupMenu</code>
     * @return true if <code>popup</code>
     */
    boolean isSubPopupMenu(JPopupMenu popup) {
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for (int i = 0 ; i < ncomponents ; i++) {
            Component comp = component[i];
            if (comp instanceof JMenu) {
                JMenu menu = (JMenu)comp;
                JPopupMenu subPopup = menu.getPopupMenu();
                if (subPopup == popup)
                    return true;
                if (subPopup.isSubPopupMenu(popup))
                    return true;
            }
        }
        return false;
    }


    private static Frame getFrame(Component c) {
        Component w = c;

        while(!(w instanceof Frame) && (w!=null)) {
            w = w.getParent();
        }
        return (Frame)w;
    }


    /**
     * Returns a string representation of this <code>JPopupMenu</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JPopupMenu </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JPopupMenu</code>.
     */
    protected String paramString() {
        String labelString = (label != null ?
                              label : "");
        String paintBorderString = (paintBorder ?
                                    "true" : "false");
        String marginString = (margin != null ?
                              margin.toString() : "");
        String lightWeightPopupEnabledString = (isLightWeightPopupEnabled() ?
                                                "true" : "false");
        return super.paramString() +
            ",desiredLocationX=" + desiredLocationX +
            ",desiredLocationY=" + desiredLocationY +
        ",label=" + labelString +
        ",lightWeightPopupEnabled=" + lightWeightPopupEnabledString +
        ",margin=" + marginString +
        ",paintBorder=" + paintBorderString;
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JPopupMenu.
     * For JPopupMenus, the AccessibleContext takes the form of an
     * AccessibleJPopupMenu.
     * A new AccessibleJPopupMenu instance is created if necessary.
     *
     * <p>
     *  获取与此JPopupMenu相关联的AccessibleContext。对于JPopupMenus,AccessibleContext采用AccessibleJPopupMenu的形式。
     * 如果需要,将创建一个新的AccessibleJPopupMenu实例。
     * 
     * 
     * @return an AccessibleJPopupMenu that serves as the
     *         AccessibleContext of this JPopupMenu
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJPopupMenu();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JPopupMenu</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to popup menu user-interface
     * elements.
     * <p>
     *  此类实现<code> JPopupMenu </code>类的辅助功能支持。它提供了适用于弹出菜单用户界面元素的Java可访问性API的实现。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleJPopupMenu extends AccessibleJComponent
        implements PropertyChangeListener {

        /**
         * AccessibleJPopupMenu constructor
         *
         * <p>
         *  AccessibleJPopupMenu构造函数
         * 
         * 
         * @since 1.5
         */
        protected AccessibleJPopupMenu() {
            JPopupMenu.this.addPropertyChangeListener(this);
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of
         * the object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.POPUP_MENU;
        }

        /**
         * This method gets called when a bound property is changed.
         * <p>
         *  当绑定属性更改时,将调用此方法。
         * 
         * 
         * @param e A <code>PropertyChangeEvent</code> object describing
         * the event source and the property that has changed. Must not be null.
         *
         * @throws NullPointerException if the parameter is null.
         * @since 1.5
         */
        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (propertyName == "visible") {
                if (e.getOldValue() == Boolean.FALSE &&
                    e.getNewValue() == Boolean.TRUE) {
                    handlePopupIsVisibleEvent(true);

                } else if (e.getOldValue() == Boolean.TRUE &&
                           e.getNewValue() == Boolean.FALSE) {
                    handlePopupIsVisibleEvent(false);
                }
            }
        }

        /*
         * Handles popup "visible" PropertyChangeEvent
         * <p>
         *  处理弹出"可见"PropertyChangeEvent
         * 
         */
        private void handlePopupIsVisibleEvent(boolean visible) {
            if (visible) {
                // notify listeners that the popup became visible
                firePropertyChange(ACCESSIBLE_STATE_PROPERTY,
                                   null, AccessibleState.VISIBLE);
                // notify listeners that a popup list item is selected
                fireActiveDescendant();
            } else {
                // notify listeners that the popup became hidden
                firePropertyChange(ACCESSIBLE_STATE_PROPERTY,
                                   AccessibleState.VISIBLE, null);
            }
        }

        /*
         * Fires AccessibleActiveDescendant PropertyChangeEvent to notify listeners
         * on the popup menu invoker that a popup list item has been selected
         * <p>
         *  Fires AccessibleActiveDescendant PropertyChangeEvent用于在弹出菜单调用器上通知侦听器已选择弹出列表项
         * 
         */
        private void fireActiveDescendant() {
            if (JPopupMenu.this instanceof BasicComboPopup) {
                // get the popup list
                JList<?> popupList = ((BasicComboPopup)JPopupMenu.this).getList();
                if (popupList == null) {
                    return;
                }

                // get the first selected item
                AccessibleContext ac = popupList.getAccessibleContext();
                AccessibleSelection selection = ac.getAccessibleSelection();
                if (selection == null) {
                    return;
                }
                Accessible a = selection.getAccessibleSelection(0);
                if (a == null) {
                    return;
                }
                AccessibleContext selectedItem = a.getAccessibleContext();

                // fire the event with the popup invoker as the source.
                if (selectedItem != null && invoker != null) {
                    AccessibleContext invokerContext = invoker.getAccessibleContext();
                    if (invokerContext != null) {
                        // Check invokerContext because Component.getAccessibleContext
                        // returns null. Classes that extend Component are responsible
                        // for returning a non-null AccessibleContext.
                        invokerContext.firePropertyChange(
                            ACCESSIBLE_ACTIVE_DESCENDANT_PROPERTY,
                            null, selectedItem);
                    }
                }
            }
        }
    } // inner class AccessibleJPopupMenu


////////////
// Serialization support.
////////////
    private void writeObject(ObjectOutputStream s) throws IOException {
        Vector<Object> values = new Vector<Object>();

        s.defaultWriteObject();
        // Save the invoker, if its Serializable.
        if(invoker != null && invoker instanceof Serializable) {
            values.addElement("invoker");
            values.addElement(invoker);
        }
        // Save the popup, if its Serializable.
        if(popup != null && popup instanceof Serializable) {
            values.addElement("popup");
            values.addElement(popup);
        }
        s.writeObject(values);

        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }

    // implements javax.swing.MenuElement
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        Vector<?>          values = (Vector)s.readObject();
        int             indexCounter = 0;
        int             maxCounter = values.size();

        if(indexCounter < maxCounter && values.elementAt(indexCounter).
           equals("invoker")) {
            invoker = (Component)values.elementAt(++indexCounter);
            indexCounter++;
        }
        if(indexCounter < maxCounter && values.elementAt(indexCounter).
           equals("popup")) {
            popup = (Popup)values.elementAt(++indexCounter);
            indexCounter++;
        }
    }


    /**
     * This method is required to conform to the
     * <code>MenuElement</code> interface, but it not implemented.
     * <p>
     *  此方法需要符合<code> MenuElement </code>接口,但未实现。
     * 
     * 
     * @see MenuElement#processMouseEvent(MouseEvent, MenuElement[], MenuSelectionManager)
     */
    public void processMouseEvent(MouseEvent event,MenuElement path[],MenuSelectionManager manager) {}

    /**
     * Processes a key event forwarded from the
     * <code>MenuSelectionManager</code> and changes the menu selection,
     * if necessary, by using <code>MenuSelectionManager</code>'s API.
     * <p>
     * Note: you do not have to forward the event to sub-components.
     * This is done automatically by the <code>MenuSelectionManager</code>.
     *
     * <p>
     *  处理从<code> MenuSelectionManager </code>转发的键事件,并根据需要使用<code> MenuSelectionManager </code>的API更改菜单选项。
     * <p>
     * 注意：您不必将事件转发到子组件。这是由<code> MenuSelectionManager </code>自动完成的。
     * 
     * 
     * @param e  a <code>KeyEvent</code>
     * @param path the <code>MenuElement</code> path array
     * @param manager   the <code>MenuSelectionManager</code>
     */
    public void processKeyEvent(KeyEvent e, MenuElement path[],
                                MenuSelectionManager manager) {
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
     * Handles a keystroke in a menu.
     *
     * <p>
     *  在菜单中处理击键。
     * 
     * 
     * @param e  a <code>MenuKeyEvent</code> object
     * @since 1.5
     */
    private void processMenuKeyEvent(MenuKeyEvent e) {
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
     * @param event a <code>MenuKeyEvent</code>
     * @see EventListenerList
     */
    private void fireMenuKeyPressed(MenuKeyEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
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
    private void fireMenuKeyReleased(MenuKeyEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
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
    private void fireMenuKeyTyped(MenuKeyEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==MenuKeyListener.class) {
                ((MenuKeyListener)listeners[i+1]).menuKeyTyped(event);
            }
        }
    }

    /**
     * Messaged when the menubar selection changes to activate or
     * deactivate this menu. This implements the
     * <code>javax.swing.MenuElement</code> interface.
     * Overrides <code>MenuElement.menuSelectionChanged</code>.
     *
     * <p>
     *  当菜单选择更改以激活或停用此菜单时消息。这实现了<code> javax.swing.MenuElement </code>接口。
     * 覆盖<code> MenuElement.menuSelectionChanged </code>。
     * 
     * 
     * @param isIncluded  true if this menu is active, false if
     *        it is not
     * @see MenuElement#menuSelectionChanged(boolean)
     */
    public void menuSelectionChanged(boolean isIncluded) {
        if (DEBUG) {
            System.out.println("In JPopupMenu.menuSelectionChanged " + isIncluded);
        }
        if(invoker instanceof JMenu) {
            JMenu m = (JMenu) invoker;
            if(isIncluded)
                m.setPopupMenuVisible(true);
            else
                m.setPopupMenuVisible(false);
        }
        if (isPopupMenu() && !isIncluded)
          setVisible(false);
    }

    /**
     * Returns an array of <code>MenuElement</code>s containing the submenu
     * for this menu component.  It will only return items conforming to
     * the <code>JMenuElement</code> interface.
     * If popup menu is <code>null</code> returns
     * an empty array.  This method is required to conform to the
     * <code>MenuElement</code> interface.
     *
     * <p>
     *  返回一个包含此菜单组件子菜单的<code> MenuElement </code>数组。它只会返回符合<code> JMenuElement </code>接口的项目。
     * 如果弹出菜单是<code> null </code>返回一个空数组。此方法需要符合<code> MenuElement </code>界面。
     * 
     * 
     * @return an array of <code>MenuElement</code> objects
     * @see MenuElement#getSubElements
     */
    public MenuElement[] getSubElements() {
        MenuElement result[];
        Vector<MenuElement> tmp = new Vector<MenuElement>();
        int c = getComponentCount();
        int i;
        Component m;

        for(i=0 ; i < c ; i++) {
            m = getComponent(i);
            if(m instanceof MenuElement)
                tmp.addElement((MenuElement) m);
        }

        result = new MenuElement[tmp.size()];
        for(i=0,c=tmp.size() ; i < c ; i++)
            result[i] = tmp.elementAt(i);
        return result;
    }

    /**
     * Returns this <code>JPopupMenu</code> component.
     * <p>
     *  返回这个<code> JPopupMenu </code>组件。
     * 
     * 
     * @return this <code>JPopupMenu</code> object
     * @see MenuElement#getComponent
     */
    public Component getComponent() {
        return this;
    }


    /**
     * A popup menu-specific separator.
     * <p>
     *  特定于弹出式菜单的分隔符。
     * 
     */
    @SuppressWarnings("serial")
    static public class Separator extends JSeparator
    {
        public Separator( )
        {
            super( JSeparator.HORIZONTAL );
        }

        /**
         * Returns the name of the L&amp;F class that renders this component.
         *
         * <p>
         *  返回呈现此组件的L&amp; F类的名称。
         * 
         * 
         * @return the string "PopupMenuSeparatorUI"
         * @see JComponent#getUIClassID
         * @see UIDefaults#getUI
         */
        public String getUIClassID()
        {
            return "PopupMenuSeparatorUI";

        }
    }

    /**
     * Returns true if the <code>MouseEvent</code> is considered a popup trigger
     * by the <code>JPopupMenu</code>'s currently installed UI.
     *
     * <p>
     *  如果<code> MouseEvent </code>被<code> JPopupMenu </code>当前安装的UI视为弹出触发器,则返回true。
     * 
     * @return true if the mouse event is a popup trigger
     * @since 1.3
     */
    public boolean isPopupTrigger(MouseEvent e) {
        return getUI().isPopupTrigger(e);
    }
}
