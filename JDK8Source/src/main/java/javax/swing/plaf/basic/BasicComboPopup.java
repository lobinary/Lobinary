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

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;


/**
 * This is a basic implementation of the <code>ComboPopup</code> interface.
 *
 * This class represents the ui for the popup portion of the combo box.
 * <p>
 * All event handling is handled by listener classes created with the
 * <code>createxxxListener()</code> methods and internal classes.
 * You can change the behavior of this class by overriding the
 * <code>createxxxListener()</code> methods and supplying your own
 * event listeners or subclassing from the ones supplied in this class.
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
 * <p>
 *  这是<code> ComboPopup </code>接口的基本实现。
 * 
 *  此类表示组合框的弹出部分的ui。
 * <p>
 *  所有事件处理都由使用<code> createxxxListener()</code>方法和内部类创建的侦听器类处理。
 * 您可以通过覆盖<code> createxxxListener()</code>方法并提供自己的事件侦听器或从此类中提供的子类化来更改此类的行为。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Tom Santos
 * @author Mark Davidson
 */
public class BasicComboPopup extends JPopupMenu implements ComboPopup {
    // An empty ListMode, this is used when the UI changes to allow
    // the JList to be gc'ed.
    private static class EmptyListModelClass implements ListModel<Object>, Serializable {
        public int getSize() { return 0; }
        public Object getElementAt(int index) { return null; }
        public void addListDataListener(ListDataListener l) {}
        public void removeListDataListener(ListDataListener l) {}
    };

    static final ListModel EmptyListModel = new EmptyListModelClass();

    private static Border LIST_BORDER = new LineBorder(Color.BLACK, 1);

    protected JComboBox                comboBox;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用访问器方法。
     * 
     * 
     * @see #getList
     * @see #createList
     */
    protected JList                    list;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法
     * 
     * 
     * @see #createScroller
     */
    protected JScrollPane              scroller;

    /**
     * As of Java 2 platform v1.4 this previously undocumented field is no
     * longer used.
     * <p>
     *  从Java 2平台v1.4这个以前未记录的字段不再使用。
     * 
     */
    protected boolean                  valueIsAdjusting = false;

    // Listeners that are required by the ComboPopup interface

    /**
     * Implementation of all the listener classes.
     * <p>
     *  实现所有监听器类。
     * 
     */
    private Handler handler;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor or create methods instead.
     *
     * <p>
     * 此受保护字段是实现特定的。不要直接访问或覆盖。使用访问器或创建方法。
     * 
     * 
     * @see #getMouseMotionListener
     * @see #createMouseMotionListener
     */
    protected MouseMotionListener      mouseMotionListener;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor or create methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。使用访问器或创建方法。
     * 
     * 
     * @see #getMouseListener
     * @see #createMouseListener
     */
    protected MouseListener            mouseListener;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the accessor or create methods instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。使用访问器或创建方法。
     * 
     * 
     * @see #getKeyListener
     * @see #createKeyListener
     */
    protected KeyListener              keyListener;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法。
     * 
     * 
     * @see #createListSelectionListener
     */
    protected ListSelectionListener    listSelectionListener;

    // Listeners that are attached to the list
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead.
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法。
     * 
     * 
     * @see #createListMouseListener
     */
    protected MouseListener            listMouseListener;
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法
     * 
     * 
     * @see #createListMouseMotionListener
     */
    protected MouseMotionListener      listMouseMotionListener;

    // Added to the combo box for bound properties
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法
     * 
     * 
     * @see #createPropertyChangeListener
     */
    protected PropertyChangeListener   propertyChangeListener;

    // Added to the combo box model
    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法
     * 
     * 
     * @see #createListDataListener
     */
    protected ListDataListener         listDataListener;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override. Use the create method instead
     *
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。请改用create方法
     * 
     * 
     * @see #createItemListener
     */
    protected ItemListener             itemListener;

    /**
     * This protected field is implementation specific. Do not access directly
     * or override.
     * <p>
     *  此受保护字段是实现特定的。不要直接访问或覆盖。
     * 
     */
    protected Timer                    autoscrollTimer;
    protected boolean                  hasEntered = false;
    protected boolean                  isAutoScrolling = false;
    protected int                      scrollDirection = SCROLL_UP;

    protected static final int         SCROLL_UP = 0;
    protected static final int         SCROLL_DOWN = 1;


    //========================================
    // begin ComboPopup method implementations
    //

    /**
     * Implementation of ComboPopup.show().
     * <p>
     *  实现ComboPopup.show()。
     * 
     */
    public void show() {
        comboBox.firePopupMenuWillBecomeVisible();
        setListSelection(comboBox.getSelectedIndex());
        Point location = getPopupLocation();
        show( comboBox, location.x, location.y );
    }


    /**
     * Implementation of ComboPopup.hide().
     * <p>
     *  实现ComboPopup.hide()。
     * 
     */
    public void hide() {
        MenuSelectionManager manager = MenuSelectionManager.defaultManager();
        MenuElement [] selection = manager.getSelectedPath();
        for ( int i = 0 ; i < selection.length ; i++ ) {
            if ( selection[i] == this ) {
                manager.clearSelectedPath();
                break;
            }
        }
        if (selection.length > 0) {
            comboBox.repaint();
        }
    }

    /**
     * Implementation of ComboPopup.getList().
     * <p>
     *  实现ComboPopup.getList()。
     * 
     */
    public JList getList() {
        return list;
    }

    /**
     * Implementation of ComboPopup.getMouseListener().
     *
     * <p>
     *  实现ComboPopup.getMouseListener()。
     * 
     * 
     * @return a <code>MouseListener</code> or null
     * @see ComboPopup#getMouseListener
     */
    public MouseListener getMouseListener() {
        if (mouseListener == null) {
            mouseListener = createMouseListener();
        }
        return mouseListener;
    }

    /**
     * Implementation of ComboPopup.getMouseMotionListener().
     *
     * <p>
     *  ComboPopup.getMouseMotionListener()的实现。
     * 
     * 
     * @return a <code>MouseMotionListener</code> or null
     * @see ComboPopup#getMouseMotionListener
     */
    public MouseMotionListener getMouseMotionListener() {
        if (mouseMotionListener == null) {
            mouseMotionListener = createMouseMotionListener();
        }
        return mouseMotionListener;
    }

    /**
     * Implementation of ComboPopup.getKeyListener().
     *
     * <p>
     *  实现ComboPopup.getKeyListener()。
     * 
     * 
     * @return a <code>KeyListener</code> or null
     * @see ComboPopup#getKeyListener
     */
    public KeyListener getKeyListener() {
        if (keyListener == null) {
            keyListener = createKeyListener();
        }
        return keyListener;
    }

    /**
     * Called when the UI is uninstalling.  Since this popup isn't in the component
     * tree, it won't get it's uninstallUI() called.  It removes the listeners that
     * were added in addComboBoxListeners().
     * <p>
     * 在UI卸载时调用。因为这个弹出框不在组件树中,它不会得到它的uninstallUI()调用。它删除在addComboBoxListeners()中添加的侦听器。
     * 
     */
    public void uninstallingUI() {
        if (propertyChangeListener != null) {
            comboBox.removePropertyChangeListener( propertyChangeListener );
        }
        if (itemListener != null) {
            comboBox.removeItemListener( itemListener );
        }
        uninstallComboBoxModelListeners(comboBox.getModel());
        uninstallKeyboardActions();
        uninstallListListeners();
        // We do this, otherwise the listener the ui installs on
        // the model (the combobox model in this case) will keep a
        // reference to the list, causing the list (and us) to never get gced.
        list.setModel(EmptyListModel);
    }

    //
    // end ComboPopup method implementations
    //======================================

    /**
     * Removes the listeners from the combo box model
     *
     * <p>
     *  从组合框模型中删除侦听器
     * 
     * 
     * @param model The combo box model to install listeners
     * @see #installComboBoxModelListeners
     */
    protected void uninstallComboBoxModelListeners( ComboBoxModel model ) {
        if (model != null && listDataListener != null) {
            model.removeListDataListener(listDataListener);
        }
    }

    protected void uninstallKeyboardActions() {
        // XXX - shouldn't call this method
//        comboBox.unregisterKeyboardAction( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ) );
    }



    //===================================================================
    // begin Initialization routines
    //
    public BasicComboPopup( JComboBox combo ) {
        super();
        setName("ComboPopup.popup");
        comboBox = combo;

        setLightWeightPopupEnabled( comboBox.isLightWeightPopupEnabled() );

        // UI construction of the popup.
        list = createList();
        list.setName("ComboBox.list");
        configureList();
        scroller = createScroller();
        scroller.setName("ComboBox.scrollPane");
        configureScroller();
        configurePopup();

        installComboBoxListeners();
        installKeyboardActions();
    }

    // Overriden PopupMenuListener notification methods to inform combo box
    // PopupMenuListeners.

    protected void firePopupMenuWillBecomeVisible() {
        super.firePopupMenuWillBecomeVisible();
        // comboBox.firePopupMenuWillBecomeVisible() is called from BasicComboPopup.show() method
        // to let the user change the popup menu from the PopupMenuListener.popupMenuWillBecomeVisible()
    }

    protected void firePopupMenuWillBecomeInvisible() {
        super.firePopupMenuWillBecomeInvisible();
        comboBox.firePopupMenuWillBecomeInvisible();
    }

    protected void firePopupMenuCanceled() {
        super.firePopupMenuCanceled();
        comboBox.firePopupMenuCanceled();
    }

    /**
     * Creates a listener
     * that will watch for mouse-press and release events on the combo box.
     *
     * <strong>Warning:</strong>
     * When overriding this method, make sure to maintain the existing
     * behavior.
     *
     * <p>
     *  创建一个监听器,它将监视组合框上的鼠标按钮和释放事件。
     * 
     *  <strong>警告：</strong>覆盖此方法时,请务必保持现有行为。
     * 
     * 
     * @return a <code>MouseListener</code> which will be added to
     * the combo box or null
     */
    protected MouseListener createMouseListener() {
        return getHandler();
    }

    /**
     * Creates the mouse motion listener which will be added to the combo
     * box.
     *
     * <strong>Warning:</strong>
     * When overriding this method, make sure to maintain the existing
     * behavior.
     *
     * <p>
     *  创建将添加到组合框的鼠标移动侦听器。
     * 
     *  <strong>警告：</strong>覆盖此方法时,请务必保持现有行为。
     * 
     * 
     * @return a <code>MouseMotionListener</code> which will be added to
     *         the combo box or null
     */
    protected MouseMotionListener createMouseMotionListener() {
        return getHandler();
    }

    /**
     * Creates the key listener that will be added to the combo box. If
     * this method returns null then it will not be added to the combo box.
     *
     * <p>
     *  创建将添加到组合框的键监听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return a <code>KeyListener</code> or null
     */
    protected KeyListener createKeyListener() {
        return null;
    }

    /**
     * Creates a list selection listener that watches for selection changes in
     * the popup's list.  If this method returns null then it will not
     * be added to the popup list.
     *
     * <p>
     *  创建一个列表选择监听器,在弹出的列表中监视选择更改。如果此方法返回null,那么它不会被添加到弹出列表。
     * 
     * 
     * @return an instance of a <code>ListSelectionListener</code> or null
     */
    protected ListSelectionListener createListSelectionListener() {
        return null;
    }

    /**
     * Creates a list data listener which will be added to the
     * <code>ComboBoxModel</code>. If this method returns null then
     * it will not be added to the combo box model.
     *
     * <p>
     *  创建将添加到<code> ComboBoxModel </code>中的列表数据侦听器。如果此方法返回null,那么它不会被添加到组合框模型。
     * 
     * 
     * @return an instance of a <code>ListDataListener</code> or null
     */
    protected ListDataListener createListDataListener() {
        return null;
    }

    /**
     * Creates a mouse listener that watches for mouse events in
     * the popup's list. If this method returns null then it will
     * not be added to the combo box.
     *
     * <p>
     *  创建在弹出的列表中监视鼠标事件的鼠标监听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return an instance of a <code>MouseListener</code> or null
     */
    protected MouseListener createListMouseListener() {
        return getHandler();
    }

    /**
     * Creates a mouse motion listener that watches for mouse motion
     * events in the popup's list. If this method returns null then it will
     * not be added to the combo box.
     *
     * <p>
     *  创建在弹出式列表中监视鼠标运动事件的鼠标运动监听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return an instance of a <code>MouseMotionListener</code> or null
     */
    protected MouseMotionListener createListMouseMotionListener() {
        return getHandler();
    }

    /**
     * Creates a <code>PropertyChangeListener</code> which will be added to
     * the combo box. If this method returns null then it will not
     * be added to the combo box.
     *
     * <p>
     * 创建一个<code> PropertyChangeListener </code>,它将被添加到组合框。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return an instance of a <code>PropertyChangeListener</code> or null
     */
    protected PropertyChangeListener createPropertyChangeListener() {
        return getHandler();
    }

    /**
     * Creates an <code>ItemListener</code> which will be added to the
     * combo box. If this method returns null then it will not
     * be added to the combo box.
     * <p>
     * Subclasses may override this method to return instances of their own
     * ItemEvent handlers.
     *
     * <p>
     *  创建将添加到组合框中的<code> ItemListener </code>。如果此方法返回null,那么它不会被添加到组合框。
     * <p>
     *  子类可以覆盖此方法以返回它们自己的ItemEvent处理程序的实例。
     * 
     * 
     * @return an instance of an <code>ItemListener</code> or null
     */
    protected ItemListener createItemListener() {
        return getHandler();
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    /**
     * Creates the JList used in the popup to display
     * the items in the combo box model. This method is called when the UI class
     * is created.
     *
     * <p>
     *  创建弹出窗口中使用的JList以显示组合框模型中的项目。创建UI类时调用此方法。
     * 
     * 
     * @return a <code>JList</code> used to display the combo box items
     */
    protected JList createList() {
        return new JList( comboBox.getModel() ) {
            public void processMouseEvent(MouseEvent e)  {
                if (BasicGraphicsUtils.isMenuShortcutKeyDown(e))  {
                    // Fix for 4234053. Filter out the Control Key from the list.
                    // ie., don't allow CTRL key deselection.
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    e = new MouseEvent((Component)e.getSource(), e.getID(), e.getWhen(),
                                       e.getModifiers() ^ toolkit.getMenuShortcutKeyMask(),
                                       e.getX(), e.getY(),
                                       e.getXOnScreen(), e.getYOnScreen(),
                                       e.getClickCount(),
                                       e.isPopupTrigger(),
                                       MouseEvent.NOBUTTON);
                }
                super.processMouseEvent(e);
            }
        };
    }

    /**
     * Configures the list which is used to hold the combo box items in the
     * popup. This method is called when the UI class
     * is created.
     *
     * <p>
     *  配置用于在弹出窗口中保存组合框项目的列表。创建UI类时调用此方法。
     * 
     * 
     * @see #createList
     */
    protected void configureList() {
        list.setFont( comboBox.getFont() );
        list.setForeground( comboBox.getForeground() );
        list.setBackground( comboBox.getBackground() );
        list.setSelectionForeground( UIManager.getColor( "ComboBox.selectionForeground" ) );
        list.setSelectionBackground( UIManager.getColor( "ComboBox.selectionBackground" ) );
        list.setBorder( null );
        list.setCellRenderer( comboBox.getRenderer() );
        list.setFocusable( false );
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        setListSelection( comboBox.getSelectedIndex() );
        installListListeners();
    }

    /**
     * Adds the listeners to the list control.
     * <p>
     *  将侦听器添加到列表控件。
     * 
     */
    protected void installListListeners() {
        if ((listMouseListener = createListMouseListener()) != null) {
            list.addMouseListener( listMouseListener );
        }
        if ((listMouseMotionListener = createListMouseMotionListener()) != null) {
            list.addMouseMotionListener( listMouseMotionListener );
        }
        if ((listSelectionListener = createListSelectionListener()) != null) {
            list.addListSelectionListener( listSelectionListener );
        }
    }

    void uninstallListListeners() {
        if (listMouseListener != null) {
            list.removeMouseListener(listMouseListener);
            listMouseListener = null;
        }
        if (listMouseMotionListener != null) {
            list.removeMouseMotionListener(listMouseMotionListener);
            listMouseMotionListener = null;
        }
        if (listSelectionListener != null) {
            list.removeListSelectionListener(listSelectionListener);
            listSelectionListener = null;
        }
        handler = null;
    }

    /**
     * Creates the scroll pane which houses the scrollable list.
     * <p>
     *  创建包含可滚动列表的滚动窗格。
     * 
     */
    protected JScrollPane createScroller() {
        JScrollPane sp = new JScrollPane( list,
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
        sp.setHorizontalScrollBar(null);
        return sp;
    }

    /**
     * Configures the scrollable portion which holds the list within
     * the combo box popup. This method is called when the UI class
     * is created.
     * <p>
     *  在组合框弹出窗口中配置保存列表的可滚动部分。创建UI类时调用此方法。
     * 
     */
    protected void configureScroller() {
        scroller.setFocusable( false );
        scroller.getVerticalScrollBar().setFocusable( false );
        scroller.setBorder( null );
    }

    /**
     * Configures the popup portion of the combo box. This method is called
     * when the UI class is created.
     * <p>
     *  配置组合框的弹出部分。创建UI类时调用此方法。
     * 
     */
    protected void configurePopup() {
        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
        setBorderPainted( true );
        setBorder(LIST_BORDER);
        setOpaque( false );
        add( scroller );
        setDoubleBuffered( true );
        setFocusable( false );
    }

    /**
     * This method adds the necessary listeners to the JComboBox.
     * <p>
     *  此方法将必要的侦听器添加到JComboBox。
     * 
     */
    protected void installComboBoxListeners() {
        if ((propertyChangeListener = createPropertyChangeListener()) != null) {
            comboBox.addPropertyChangeListener(propertyChangeListener);
        }
        if ((itemListener = createItemListener()) != null) {
            comboBox.addItemListener(itemListener);
        }
        installComboBoxModelListeners(comboBox.getModel());
    }

    /**
     * Installs the listeners on the combo box model. Any listeners installed
     * on the combo box model should be removed in
     * <code>uninstallComboBoxModelListeners</code>.
     *
     * <p>
     *  在组合框模型上安装侦听器。组合框模型上安装的任何侦听器都应该在<code> uninstallComboBoxModelListeners </code>中删除。
     * 
     * 
     * @param model The combo box model to install listeners
     * @see #uninstallComboBoxModelListeners
     */
    protected void installComboBoxModelListeners( ComboBoxModel model ) {
        if (model != null && (listDataListener = createListDataListener()) != null) {
            model.addListDataListener(listDataListener);
        }
    }

    protected void installKeyboardActions() {

        /* XXX - shouldn't call this method. take it out for testing.
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e){
            }
        };

        comboBox.registerKeyboardAction( action,
                                         KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ),
        /* <p>
        /*  ActionListener action = new ActionListener(){public void actionPerformed(ActionEvent e){}};
        /* 
        /*  comboBox.registerKeyboardAction(action,KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),
        /* 
        /* 
                                         JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT ); */

    }

    //
    // end Initialization routines
    //=================================================================


    //===================================================================
    // begin Event Listenters
    //

    /**
     * A listener to be registered upon the combo box
     * (<em>not</em> its popup menu)
     * to handle mouse events
     * that affect the state of the popup menu.
     * The main purpose of this listener is to make the popup menu
     * appear and disappear.
     * This listener also helps
     * with click-and-drag scenarios by setting the selection if the mouse was
     * released over the list during a drag.
     *
     * <p>
     * <strong>Warning:</strong>
     * We recommend that you <em>not</em>
     * create subclasses of this class.
     * If you absolutely must create a subclass,
     * be sure to invoke the superclass
     * version of each method.
     *
     * <p>
     * 要在组合框上注册的侦听器(<em>不是</em>的弹出菜单)来处理影响弹出菜单状态的鼠标事件。这个监听器的主要目的是使弹出菜单出现和消失。
     * 如果在拖动期间鼠标在列表上释放,此侦听器还可通过设置选择来帮助单击和拖动方案。
     * 
     * <p>
     *  <strong>警告：</strong>我们建议您<em>不</em>创建此类的子类。如果绝对必须创建一个子类,请务必调用每个方法的超类版本。
     * 
     * 
     * @see BasicComboPopup#createMouseListener
     */
    protected class InvocationMouseHandler extends MouseAdapter {
        /**
         * Responds to mouse-pressed events on the combo box.
         *
         * <p>
         *  响应组合框上的鼠标按下事件。
         * 
         * 
         * @param e the mouse-press event to be handled
         */
        public void mousePressed( MouseEvent e ) {
            getHandler().mousePressed(e);
        }

        /**
         * Responds to the user terminating
         * a click or drag that began on the combo box.
         *
         * <p>
         *  响应用户终止在组合框上开始的点击或拖动。
         * 
         * 
         * @param e the mouse-release event to be handled
         */
        public void mouseReleased( MouseEvent e ) {
            getHandler().mouseReleased(e);
        }
    }

    /**
     * This listener watches for dragging and updates the current selection in the
     * list if it is dragging over the list.
     * <p>
     *  此监听器监视拖动并更新列表中的当前选择(如果它在列表上拖动)。
     * 
     */
    protected class InvocationMouseMotionHandler extends MouseMotionAdapter {
        public void mouseDragged( MouseEvent e ) {
            getHandler().mouseDragged(e);
        }
    }

    /**
     * As of Java 2 platform v 1.4, this class is now obsolete and is only included for
     * backwards API compatibility. Do not instantiate or subclass.
     * <p>
     * All the functionality of this class has been included in
     * BasicComboBoxUI ActionMap/InputMap methods.
     * <p>
     *  从Java 2平台v 1.4,这个类现在已过时,只包括向后的API兼容性。不要实例化或子类化。
     * <p>
     *  此类的所有功能已包含在BasicComboBoxUI ActionMap / InputMap方法中。
     * 
     */
    public class InvocationKeyHandler extends KeyAdapter {
        public void keyReleased( KeyEvent e ) {}
    }

    /**
     * As of Java 2 platform v 1.4, this class is now obsolete, doesn't do anything, and
     * is only included for backwards API compatibility. Do not call or
     * override.
     * <p>
     *  从Java 2平台v 1.4,这个类现在已过时,不做任何事情,只包括向后的API兼容性。不要调用或覆盖。
     * 
     */
    protected class ListSelectionHandler implements ListSelectionListener {
        public void valueChanged( ListSelectionEvent e ) {}
    }

    /**
     * As of 1.4, this class is now obsolete, doesn't do anything, and
     * is only included for backwards API compatibility. Do not call or
     * override.
     * <p>
     * The functionality has been migrated into <code>ItemHandler</code>.
     *
     * <p>
     *  从1.4开始,这个类现在已经过时了,不做任何事情,只是为了向后兼容API而包含的。不要调用或覆盖。
     * <p>
     *  该功能已迁移到<code> ItemHandler </code>中。
     * 
     * 
     * @see #createItemListener
     */
    public class ListDataHandler implements ListDataListener {
        public void contentsChanged( ListDataEvent e ) {}

        public void intervalAdded( ListDataEvent e ) {
        }

        public void intervalRemoved( ListDataEvent e ) {
        }
    }

    /**
     * This listener hides the popup when the mouse is released in the list.
     * <p>
     *  当鼠标在列表中释放时,此侦听器隐藏弹出窗口。
     * 
     */
    protected class ListMouseHandler extends MouseAdapter {
        public void mousePressed( MouseEvent e ) {
        }
        public void mouseReleased(MouseEvent anEvent) {
            getHandler().mouseReleased(anEvent);
        }
    }

    /**
     * This listener changes the selected item as you move the mouse over the list.
     * The selection change is not committed to the model, this is for user feedback only.
     * <p>
     * 当您将鼠标移动到列表上时,此监听器会更改所选项目。选择更改不会提交给模型,这只适用于用户反馈。
     * 
     */
    protected class ListMouseMotionHandler extends MouseMotionAdapter {
        public void mouseMoved( MouseEvent anEvent ) {
            getHandler().mouseMoved(anEvent);
        }
    }

    /**
     * This listener watches for changes to the selection in the
     * combo box.
     * <p>
     *  此侦听器监视对组合框中的选择所做的更改。
     * 
     */
    protected class ItemHandler implements ItemListener {
        public void itemStateChanged( ItemEvent e ) {
            getHandler().itemStateChanged(e);
        }
    }

    /**
     * This listener watches for bound properties that have changed in the
     * combo box.
     * <p>
     * Subclasses which wish to listen to combo box property changes should
     * call the superclass methods to ensure that the combo popup correctly
     * handles property changes.
     *
     * <p>
     *  此侦听器监视已在组合框中更改的绑定属性。
     * <p>
     *  希望监听组合框属性更改的子类应调用超类方法以确保组合弹出框正确处理属性更改。
     * 
     * 
     * @see #createPropertyChangeListener
     */
    protected class PropertyChangeHandler implements PropertyChangeListener {
        public void propertyChange( PropertyChangeEvent e ) {
            getHandler().propertyChange(e);
        }
    }


    private class AutoScrollActionHandler implements ActionListener {
        private int direction;

        AutoScrollActionHandler(int direction) {
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent e) {
            if (direction == SCROLL_UP) {
                autoScrollUp();
            }
            else {
                autoScrollDown();
            }
        }
    }


    private class Handler implements ItemListener, MouseListener,
                          MouseMotionListener, PropertyChangeListener,
                          Serializable {
        //
        // MouseListener
        // NOTE: this is added to both the JList and JComboBox
        //
        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if (e.getSource() == list) {
                return;
            }
            if (!SwingUtilities.isLeftMouseButton(e) || !comboBox.isEnabled())
                return;

            if ( comboBox.isEditable() ) {
                Component comp = comboBox.getEditor().getEditorComponent();
                if ((!(comp instanceof JComponent)) || ((JComponent)comp).isRequestFocusEnabled()) {
                    comp.requestFocus();
                }
            }
            else if (comboBox.isRequestFocusEnabled()) {
                comboBox.requestFocus();
            }
            togglePopup();
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getSource() == list) {
                if (list.getModel().getSize() > 0) {
                    // JList mouse listener
                    if (comboBox.getSelectedIndex() == list.getSelectedIndex()) {
                        comboBox.getEditor().setItem(list.getSelectedValue());
                    }
                    comboBox.setSelectedIndex(list.getSelectedIndex());
                }
                comboBox.setPopupVisible(false);
                // workaround for cancelling an edited item (bug 4530953)
                if (comboBox.isEditable() && comboBox.getEditor() != null) {
                    comboBox.configureEditor(comboBox.getEditor(),
                                             comboBox.getSelectedItem());
                }
                return;
            }
            // JComboBox mouse listener
            Component source = (Component)e.getSource();
            Dimension size = source.getSize();
            Rectangle bounds = new Rectangle( 0, 0, size.width - 1, size.height - 1 );
            if ( !bounds.contains( e.getPoint() ) ) {
                MouseEvent newEvent = convertMouseEvent( e );
                Point location = newEvent.getPoint();
                Rectangle r = new Rectangle();
                list.computeVisibleRect( r );
                if ( r.contains( location ) ) {
                    if (comboBox.getSelectedIndex() == list.getSelectedIndex()) {
                        comboBox.getEditor().setItem(list.getSelectedValue());
                    }
                    comboBox.setSelectedIndex(list.getSelectedIndex());
                }
                comboBox.setPopupVisible(false);
            }
            hasEntered = false;
            stopAutoScrolling();
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        //
        // MouseMotionListener:
        // NOTE: this is added to both the List and ComboBox
        //
        public void mouseMoved(MouseEvent anEvent) {
            if (anEvent.getSource() == list) {
                Point location = anEvent.getPoint();
                Rectangle r = new Rectangle();
                list.computeVisibleRect( r );
                if ( r.contains( location ) ) {
                    updateListBoxSelectionForEvent( anEvent, false );
                }
            }
        }

        public void mouseDragged( MouseEvent e ) {
            if (e.getSource() == list) {
                return;
            }
            if ( isVisible() ) {
                MouseEvent newEvent = convertMouseEvent( e );
                Rectangle r = new Rectangle();
                list.computeVisibleRect( r );

                if ( newEvent.getPoint().y >= r.y && newEvent.getPoint().y <= r.y + r.height - 1 ) {
                    hasEntered = true;
                    if ( isAutoScrolling ) {
                        stopAutoScrolling();
                    }
                    Point location = newEvent.getPoint();
                    if ( r.contains( location ) ) {
                        updateListBoxSelectionForEvent( newEvent, false );
                    }
                }
                else {
                    if ( hasEntered ) {
                        int directionToScroll = newEvent.getPoint().y < r.y ? SCROLL_UP : SCROLL_DOWN;
                        if ( isAutoScrolling && scrollDirection != directionToScroll ) {
                            stopAutoScrolling();
                            startAutoScrolling( directionToScroll );
                        }
                        else if ( !isAutoScrolling ) {
                            startAutoScrolling( directionToScroll );
                        }
                    }
                    else {
                        if ( e.getPoint().y < 0 ) {
                            hasEntered = true;
                            startAutoScrolling( SCROLL_UP );
                        }
                    }
                }
            }
        }

        //
        // PropertyChangeListener
        //
        public void propertyChange(PropertyChangeEvent e) {
            JComboBox comboBox = (JComboBox)e.getSource();
            String propertyName = e.getPropertyName();

            if ( propertyName == "model" ) {
                ComboBoxModel oldModel = (ComboBoxModel)e.getOldValue();
                ComboBoxModel newModel = (ComboBoxModel)e.getNewValue();
                uninstallComboBoxModelListeners(oldModel);
                installComboBoxModelListeners(newModel);

                list.setModel(newModel);

                if ( isVisible() ) {
                    hide();
                }
            }
            else if ( propertyName == "renderer" ) {
                list.setCellRenderer( comboBox.getRenderer() );
                if ( isVisible() ) {
                    hide();
                }
            }
            else if (propertyName == "componentOrientation") {
                // Pass along the new component orientation
                // to the list and the scroller

                ComponentOrientation o =(ComponentOrientation)e.getNewValue();

                JList list = getList();
                if (list!=null && list.getComponentOrientation()!=o) {
                    list.setComponentOrientation(o);
                }

                if (scroller!=null && scroller.getComponentOrientation()!=o) {
                    scroller.setComponentOrientation(o);
                }

                if (o!=getComponentOrientation()) {
                    setComponentOrientation(o);
                }
            }
            else if (propertyName == "lightWeightPopupEnabled") {
                setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
            }
        }

        //
        // ItemListener
        //
        public void itemStateChanged( ItemEvent e ) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                JComboBox comboBox = (JComboBox)e.getSource();
                setListSelection(comboBox.getSelectedIndex());
            }
        }
    }

    //
    // end Event Listeners
    //=================================================================


    /**
     * Overridden to unconditionally return false.
     * <p>
     *  重写到无条件返回false。
     * 
     */
    public boolean isFocusTraversable() {
        return false;
    }

    //===================================================================
    // begin Autoscroll methods
    //

    /**
     * This protected method is implementation specific and should be private.
     * do not call or override.
     * <p>
     *  这种受保护的方法是实现特定的,应该是私有的。不要调用或覆盖。
     * 
     */
    protected void startAutoScrolling( int direction ) {
        // XXX - should be a private method within InvocationMouseMotionHandler
        // if possible.
        if ( isAutoScrolling ) {
            autoscrollTimer.stop();
        }

        isAutoScrolling = true;

        if ( direction == SCROLL_UP ) {
            scrollDirection = SCROLL_UP;
            Point convertedPoint = SwingUtilities.convertPoint( scroller, new Point( 1, 1 ), list );
            int top = list.locationToIndex( convertedPoint );
            list.setSelectedIndex( top );

            autoscrollTimer = new Timer( 100, new AutoScrollActionHandler(
                                             SCROLL_UP) );
        }
        else if ( direction == SCROLL_DOWN ) {
            scrollDirection = SCROLL_DOWN;
            Dimension size = scroller.getSize();
            Point convertedPoint = SwingUtilities.convertPoint( scroller,
                                                                new Point( 1, (size.height - 1) - 2 ),
                                                                list );
            int bottom = list.locationToIndex( convertedPoint );
            list.setSelectedIndex( bottom );

            autoscrollTimer = new Timer(100, new AutoScrollActionHandler(
                                            SCROLL_DOWN));
        }
        autoscrollTimer.start();
    }

    /**
     * This protected method is implementation specific and should be private.
     * do not call or override.
     * <p>
     *  这种受保护的方法是实现特定的,应该是私有的。不要调用或覆盖。
     * 
     */
    protected void stopAutoScrolling() {
        isAutoScrolling = false;

        if ( autoscrollTimer != null ) {
            autoscrollTimer.stop();
            autoscrollTimer = null;
        }
    }

    /**
     * This protected method is implementation specific and should be private.
     * do not call or override.
     * <p>
     *  这种受保护的方法是实现特定的,应该是私有的。不要调用或覆盖。
     * 
     */
    protected void autoScrollUp() {
        int index = list.getSelectedIndex();
        if ( index > 0 ) {
            list.setSelectedIndex( index - 1 );
            list.ensureIndexIsVisible( index - 1 );
        }
    }

    /**
     * This protected method is implementation specific and should be private.
     * do not call or override.
     * <p>
     *  这种受保护的方法是实现特定的,应该是私有的。不要调用或覆盖。
     * 
     */
    protected void autoScrollDown() {
        int index = list.getSelectedIndex();
        int lastItem = list.getModel().getSize() - 1;
        if ( index < lastItem ) {
            list.setSelectedIndex( index + 1 );
            list.ensureIndexIsVisible( index + 1 );
        }
    }

    //
    // end Autoscroll methods
    //=================================================================


    //===================================================================
    // begin Utility methods
    //

    /**
     * Gets the AccessibleContext associated with this BasicComboPopup.
     * The AccessibleContext will have its parent set to the ComboBox.
     *
     * <p>
     *  获取与此BasicComboPopup相关联的AccessibleContext。 AccessibleContext将其父设置为ComboBox。
     * 
     * 
     * @return an AccessibleContext for the BasicComboPopup
     * @since 1.5
     */
    public AccessibleContext getAccessibleContext() {
        AccessibleContext context = super.getAccessibleContext();
        context.setAccessibleParent(comboBox);
        return context;
    }


    /**
     * This is is a utility method that helps event handlers figure out where to
     * send the focus when the popup is brought up.  The standard implementation
     * delegates the focus to the editor (if the combo box is editable) or to
     * the JComboBox if it is not editable.
     * <p>
     *  这是一个实用程序方法,帮助事件处理程序找出在弹出窗口时发送焦点。标准实现将焦点委托给编辑器(如果组合框是可编辑的)或JComboBox如果它不可编辑。
     * 
     */
    protected void delegateFocus( MouseEvent e ) {
        if ( comboBox.isEditable() ) {
            Component comp = comboBox.getEditor().getEditorComponent();
            if ((!(comp instanceof JComponent)) || ((JComponent)comp).isRequestFocusEnabled()) {
                comp.requestFocus();
            }
        }
        else if (comboBox.isRequestFocusEnabled()) {
            comboBox.requestFocus();
        }
    }

    /**
     * Makes the popup visible if it is hidden and makes it hidden if it is
     * visible.
     * <p>
     *  使弹出窗口可见,如果它是隐藏的,并使其隐藏,如果它是可见的。
     * 
     */
    protected void togglePopup() {
        if ( isVisible() ) {
            hide();
        }
        else {
            show();
        }
    }

    /**
     * Sets the list selection index to the selectedIndex. This
     * method is used to synchronize the list selection with the
     * combo box selection.
     *
     * <p>
     * 将列表选择索引设置为selectedIndex。此方法用于将列表选择与组合框选择同步。
     * 
     * 
     * @param selectedIndex the index to set the list
     */
    private void setListSelection(int selectedIndex) {
        if ( selectedIndex == -1 ) {
            list.clearSelection();
        }
        else {
            list.setSelectedIndex( selectedIndex );
            list.ensureIndexIsVisible( selectedIndex );
        }
    }

    protected MouseEvent convertMouseEvent( MouseEvent e ) {
        Point convertedPoint = SwingUtilities.convertPoint( (Component)e.getSource(),
                                                            e.getPoint(), list );
        MouseEvent newEvent = new MouseEvent( (Component)e.getSource(),
                                              e.getID(),
                                              e.getWhen(),
                                              e.getModifiers(),
                                              convertedPoint.x,
                                              convertedPoint.y,
                                              e.getXOnScreen(),
                                              e.getYOnScreen(),
                                              e.getClickCount(),
                                              e.isPopupTrigger(),
                                              MouseEvent.NOBUTTON );
        return newEvent;
    }


    /**
     * Retrieves the height of the popup based on the current
     * ListCellRenderer and the maximum row count.
     * <p>
     *  根据当前的ListCellRenderer和最大行数检索弹出窗口的高度。
     * 
     */
    protected int getPopupHeightForRowCount(int maxRowCount) {
        // Set the cached value of the minimum row count
        int minRowCount = Math.min( maxRowCount, comboBox.getItemCount() );
        int height = 0;
        ListCellRenderer renderer = list.getCellRenderer();
        Object value = null;

        for ( int i = 0; i < minRowCount; ++i ) {
            value = list.getModel().getElementAt( i );
            Component c = renderer.getListCellRendererComponent( list, value, i, false, false );
            height += c.getPreferredSize().height;
        }

        if (height == 0) {
            height = comboBox.getHeight();
        }

        Border border = scroller.getViewportBorder();
        if (border != null) {
            Insets insets = border.getBorderInsets(null);
            height += insets.top + insets.bottom;
        }

        border = scroller.getBorder();
        if (border != null) {
            Insets insets = border.getBorderInsets(null);
            height += insets.top + insets.bottom;
        }

        return height;
    }

    /**
     * Calculate the placement and size of the popup portion of the combo box based
     * on the combo box location and the enclosing screen bounds. If
     * no transformations are required, then the returned rectangle will
     * have the same values as the parameters.
     *
     * <p>
     *  基于组合框位置和封闭屏幕边界计算组合框的弹出部分的位置和大小。如果不需要转换,则返回的矩形将具有与参数相同的值。
     * 
     * 
     * @param px starting x location
     * @param py starting y location
     * @param pw starting width
     * @param ph starting height
     * @return a rectangle which represents the placement and size of the popup
     */
    protected Rectangle computePopupBounds(int px,int py,int pw,int ph) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Rectangle screenBounds;

        // Calculate the desktop dimensions relative to the combo box.
        GraphicsConfiguration gc = comboBox.getGraphicsConfiguration();
        Point p = new Point();
        SwingUtilities.convertPointFromScreen(p, comboBox);
        if (gc != null) {
            Insets screenInsets = toolkit.getScreenInsets(gc);
            screenBounds = gc.getBounds();
            screenBounds.width -= (screenInsets.left + screenInsets.right);
            screenBounds.height -= (screenInsets.top + screenInsets.bottom);
            screenBounds.x += (p.x + screenInsets.left);
            screenBounds.y += (p.y + screenInsets.top);
        }
        else {
            screenBounds = new Rectangle(p, toolkit.getScreenSize());
        }

        Rectangle rect = new Rectangle(px,py,pw,ph);
        if (py+ph > screenBounds.y+screenBounds.height
            && ph < screenBounds.height) {
            rect.y = -rect.height;
        }
        return rect;
    }

    /**
     * Calculates the upper left location of the Popup.
     * <p>
     *  计算Popup的左上角位置。
     * 
     */
    private Point getPopupLocation() {
        Dimension popupSize = comboBox.getSize();
        Insets insets = getInsets();

        // reduce the width of the scrollpane by the insets so that the popup
        // is the same width as the combo box.
        popupSize.setSize(popupSize.width - (insets.right + insets.left),
                          getPopupHeightForRowCount( comboBox.getMaximumRowCount()));
        Rectangle popupBounds = computePopupBounds( 0, comboBox.getBounds().height,
                                                    popupSize.width, popupSize.height);
        Dimension scrollSize = popupBounds.getSize();
        Point popupLocation = popupBounds.getLocation();

        scroller.setMaximumSize( scrollSize );
        scroller.setPreferredSize( scrollSize );
        scroller.setMinimumSize( scrollSize );

        list.revalidate();

        return popupLocation;
    }

    /**
     * A utility method used by the event listeners.  Given a mouse event, it changes
     * the list selection to the list item below the mouse.
     * <p>
     *  事件侦听器使用的实用程序方法。给定鼠标事件,它将列表选择更改为鼠标下方的列表项。
     */
    protected void updateListBoxSelectionForEvent(MouseEvent anEvent,boolean shouldScroll) {
        // XXX - only seems to be called from this class. shouldScroll flag is
        // never true
        Point location = anEvent.getPoint();
        if ( list == null )
            return;
        int index = list.locationToIndex(location);
        if ( index == -1 ) {
            if ( location.y < 0 )
                index = 0;
            else
                index = comboBox.getModel().getSize() - 1;
        }
        if ( list.getSelectedIndex() != index ) {
            list.setSelectedIndex(index);
            if ( shouldScroll )
                list.ensureIndexIsVisible(index);
        }
    }

    //
    // end Utility methods
    //=================================================================
}
