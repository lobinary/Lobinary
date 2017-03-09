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


package javax.swing.plaf.basic;


import sun.swing.DefaultLookup;
import sun.swing.UIAction;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.peer.ComponentPeer;
import java.awt.peer.LightweightPeer;
import java.beans.*;
import java.util.*;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import sun.swing.SwingUtilities2;


/**
 * A Basic L&amp;F implementation of the SplitPaneUI.
 *
 * <p>
 *  SplitPaneUI的基本L&amp; F实现。
 * 
 * 
 * @author Scott Violet
 * @author Steve Wilson
 * @author Ralph Kar
 */
public class BasicSplitPaneUI extends SplitPaneUI
{
    /**
     * The divider used for non-continuous layout is added to the split pane
     * with this object.
     * <p>
     *  用于非连续布局的分隔符将添加到具有此对象的拆分窗格中。
     * 
     */
    protected static final String NON_CONTINUOUS_DIVIDER =
        "nonContinuousDivider";


    /**
     * How far (relative) the divider does move when it is moved around by
     * the cursor keys on the keyboard.
     * <p>
     *  当键盘上的光标键移动分隔符时,分隔符移动的距离(相对距离)。
     * 
     */
    protected static int KEYBOARD_DIVIDER_MOVE_OFFSET = 3;


    /**
     * JSplitPane instance this instance is providing
     * the look and feel for.
     * <p>
     *  JSplitPane实例这个实例提供了外观和感觉。
     * 
     */
    protected JSplitPane splitPane;


    /**
     * LayoutManager that is created and placed into the split pane.
     * <p>
     *  创建并放置到拆分窗格中的LayoutManager。
     * 
     */
    protected BasicHorizontalLayoutManager layoutManager;


    /**
     * Instance of the divider for this JSplitPane.
     * <p>
     *  此JSplitPane的分隔符实例。
     * 
     */
    protected BasicSplitPaneDivider divider;


    /**
     * Instance of the PropertyChangeListener for this JSplitPane.
     * <p>
     *  此JSplitPane的PropertyChangeListener的实例。
     * 
     */
    protected PropertyChangeListener propertyChangeListener;


    /**
     * Instance of the FocusListener for this JSplitPane.
     * <p>
     *  此JSplitPane的FocusListener的实例。
     * 
     */
    protected FocusListener focusListener;

    private Handler handler;


    /**
     * Keys to use for forward focus traversal when the JComponent is
     * managing focus.
     * <p>
     *  当JComponent正在管理焦点时,用于正向聚焦遍历的键。
     * 
     */
    private Set<KeyStroke> managingFocusForwardTraversalKeys;

    /**
     * Keys to use for backward focus traversal when the JComponent is
     * managing focus.
     * <p>
     *  当JComponent正在管理焦点时,用于向后焦点遍历的键。
     * 
     */
    private Set<KeyStroke> managingFocusBackwardTraversalKeys;


    /**
     * The size of the divider while the dragging session is valid.
     * <p>
     *  拖动会话期间分隔符的大小有效。
     * 
     */
    protected int dividerSize;


    /**
     * Instance for the shadow of the divider when non continuous layout
     * is being used.
     * <p>
     *  当使用非连续布局时,分隔符的阴影的实例。
     * 
     */
    protected Component nonContinuousLayoutDivider;


    /**
     * Set to true in startDragging if any of the children
     * (not including the nonContinuousLayoutDivider) are heavy weights.
     * <p>
     *  如果任何子项(不包括nonContinuousLayoutDivider)是重量级,则在startDragging中设置为true。
     * 
     */
    protected boolean draggingHW;


    /**
     * Location of the divider when the dragging session began.
     * <p>
     *  拖动会话开始时分隔符的位置。
     * 
     */
    protected int beginDragDividerLocation;


    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke upKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     * 从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke downKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke leftKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke rightKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke homeKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke endKey;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected KeyStroke dividerResizeToggleKey;

    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener keyboardUpLeftListener;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     * 从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener keyboardDownRightListener;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener keyboardHomeListener;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener keyboardEndListener;
    /**
     * As of Java 2 platform v1.3 this previously undocumented field is no
     * longer used.
     * Key bindings are now defined by the LookAndFeel, please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这个以前未记录的字段不再使用。键绑定现在由LookAndFeel定义,有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener keyboardResizeToggleListener;


    // Private data of the instance
    private int         orientation;
    private int         lastDragLocation;
    private boolean     continuousLayout;
    private boolean     dividerKeyboardResize;
    private boolean     dividerLocationIsSet;  // needed for tracking
                                               // the first occurrence of
                                               // setDividerLocation()
    private Color dividerDraggingColor;
    private boolean rememberPaneSizes;

    // Indicates whether the one of splitpane sides is expanded
    private boolean keepHidden = false;

    /** Indicates that we have painted once. */
    // This is used by the LayoutManager to determine when it should use
    // the divider location provided by the JSplitPane. This is used as there
    // is no way to determine when the layout process has completed.
    boolean             painted;
    /** If true, setDividerLocation does nothing. */
    boolean             ignoreDividerLocationChange;


    /**
     * Creates a new BasicSplitPaneUI instance
     * <p>
     *  创建一个新的BasicSplitPaneUI实例
     * 
     */
    public static ComponentUI createUI(JComponent x) {
        return new BasicSplitPaneUI();
    }

    static void loadActionMap(LazyActionMap map) {
        map.put(new Actions(Actions.NEGATIVE_INCREMENT));
        map.put(new Actions(Actions.POSITIVE_INCREMENT));
        map.put(new Actions(Actions.SELECT_MIN));
        map.put(new Actions(Actions.SELECT_MAX));
        map.put(new Actions(Actions.START_RESIZE));
        map.put(new Actions(Actions.TOGGLE_FOCUS));
        map.put(new Actions(Actions.FOCUS_OUT_FORWARD));
        map.put(new Actions(Actions.FOCUS_OUT_BACKWARD));
    }



    /**
     * Installs the UI.
     * <p>
     *  安装UI。
     * 
     */
    public void installUI(JComponent c) {
        splitPane = (JSplitPane) c;
        dividerLocationIsSet = false;
        dividerKeyboardResize = false;
        keepHidden = false;
        installDefaults();
        installListeners();
        installKeyboardActions();
        setLastDragLocation(-1);
    }


    /**
     * Installs the UI defaults.
     * <p>
     *  安装UI默认值。
     * 
     */
    protected void installDefaults(){
        LookAndFeel.installBorder(splitPane, "SplitPane.border");
        LookAndFeel.installColors(splitPane, "SplitPane.background",
                                  "SplitPane.foreground");
        LookAndFeel.installProperty(splitPane, "opaque", Boolean.TRUE);

        if (divider == null) divider = createDefaultDivider();
        divider.setBasicSplitPaneUI(this);

        Border    b = divider.getBorder();

        if (b == null || !(b instanceof UIResource)) {
            divider.setBorder(UIManager.getBorder("SplitPaneDivider.border"));
        }

        dividerDraggingColor = UIManager.getColor("SplitPaneDivider.draggingColor");

        setOrientation(splitPane.getOrientation());

        // note: don't rename this temp variable to dividerSize
        // since it will conflict with "this.dividerSize" field
        Integer temp = (Integer)UIManager.get("SplitPane.dividerSize");
        LookAndFeel.installProperty(splitPane, "dividerSize", temp == null? 10: temp);

        divider.setDividerSize(splitPane.getDividerSize());
        dividerSize = divider.getDividerSize();
        splitPane.add(divider, JSplitPane.DIVIDER);

        setContinuousLayout(splitPane.isContinuousLayout());

        resetLayoutManager();

        /* Install the nonContinuousLayoutDivider here to avoid having to
        /* <p>
        /* 
        add/remove everything later. */
        if(nonContinuousLayoutDivider == null) {
            setNonContinuousLayoutDivider(
                                createDefaultNonContinuousLayoutDivider(),
                                true);
        } else {
            setNonContinuousLayoutDivider(nonContinuousLayoutDivider, true);
        }

        // focus forward traversal key
        if (managingFocusForwardTraversalKeys==null) {
            managingFocusForwardTraversalKeys = new HashSet<KeyStroke>();
            managingFocusForwardTraversalKeys.add(
                KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0));
        }
        splitPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
                                        managingFocusForwardTraversalKeys);
        // focus backward traversal key
        if (managingFocusBackwardTraversalKeys==null) {
            managingFocusBackwardTraversalKeys = new HashSet<KeyStroke>();
            managingFocusBackwardTraversalKeys.add(
                KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK));
        }
        splitPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
                                        managingFocusBackwardTraversalKeys);
    }


    /**
     * Installs the event listeners for the UI.
     * <p>
     *  为UI安装事件侦听器。
     * 
     */
    protected void installListeners() {
        if ((propertyChangeListener = createPropertyChangeListener()) !=
            null) {
            splitPane.addPropertyChangeListener(propertyChangeListener);
        }

        if ((focusListener = createFocusListener()) != null) {
            splitPane.addFocusListener(focusListener);
        }
    }


    /**
     * Installs the keyboard actions for the UI.
     * <p>
     *  安装UI的键盘操作。
     * 
     */
    protected void installKeyboardActions() {
        InputMap km = getInputMap(JComponent.
                                  WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        SwingUtilities.replaceUIInputMap(splitPane, JComponent.
                                       WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
                                       km);
        LazyActionMap.installLazyActionMap(splitPane, BasicSplitPaneUI.class,
                                           "SplitPane.actionMap");
    }

    InputMap getInputMap(int condition) {
        if (condition == JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) {
            return (InputMap)DefaultLookup.get(splitPane, this,
                                               "SplitPane.ancestorInputMap");
        }
        return null;
    }

    /**
     * Uninstalls the UI.
     * <p>
     *  卸载UI。
     * 
     */
    public void uninstallUI(JComponent c) {
        uninstallKeyboardActions();
        uninstallListeners();
        uninstallDefaults();
        dividerLocationIsSet = false;
        dividerKeyboardResize = false;
        splitPane = null;
    }


    /**
     * Uninstalls the UI defaults.
     * <p>
     *  卸载UI默认值。
     * 
     */
    protected void uninstallDefaults() {
        if(splitPane.getLayout() == layoutManager) {
            splitPane.setLayout(null);
        }

        if(nonContinuousLayoutDivider != null) {
            splitPane.remove(nonContinuousLayoutDivider);
        }

        LookAndFeel.uninstallBorder(splitPane);

        Border    b = divider.getBorder();

        if (b instanceof UIResource) {
            divider.setBorder(null);
        }

        splitPane.remove(divider);
        divider.setBasicSplitPaneUI(null);
        layoutManager = null;
        divider = null;
        nonContinuousLayoutDivider = null;

        setNonContinuousLayoutDivider(null);

        // sets the focus forward and backward traversal keys to null
        // to restore the defaults
        splitPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
        splitPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
    }


    /**
     * Uninstalls the event listeners for the UI.
     * <p>
     *  卸载UI的事件侦听器。
     * 
     */
    protected void uninstallListeners() {
        if (propertyChangeListener != null) {
            splitPane.removePropertyChangeListener(propertyChangeListener);
            propertyChangeListener = null;
        }
        if (focusListener != null) {
            splitPane.removeFocusListener(focusListener);
            focusListener = null;
        }

        keyboardUpLeftListener = null;
        keyboardDownRightListener = null;
        keyboardHomeListener = null;
        keyboardEndListener = null;
        keyboardResizeToggleListener = null;
        handler = null;
    }


    /**
     * Uninstalls the keyboard actions for the UI.
     * <p>
     *  卸载UI的键盘操作。
     * 
     */
    protected void uninstallKeyboardActions() {
        SwingUtilities.replaceUIActionMap(splitPane, null);
        SwingUtilities.replaceUIInputMap(splitPane, JComponent.
                                      WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
                                      null);
    }


    /**
     * Creates a PropertyChangeListener for the JSplitPane UI.
     * <p>
     *  为JSplitPane UI创建PropertyChangeListener。
     * 
     */
    protected PropertyChangeListener createPropertyChangeListener() {
        return getHandler();
    }

    private Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }


    /**
     * Creates a FocusListener for the JSplitPane UI.
     * <p>
     *  为JSplitPane UI创建FocusListener。
     * 
     */
    protected FocusListener createFocusListener() {
        return getHandler();
    }


    /**
     * As of Java 2 platform v1.3 this method is no
     * longer used. Subclassers previously using this method should
     * instead create an Action wrapping the ActionListener, and register
     * that Action by overriding <code>installKeyboardActions</code> and
     * placing the Action in the SplitPane's ActionMap. Please refer to
     * the key bindings specification for further details.
     * <p>
     * Creates a ActionListener for the JSplitPane UI that listens for
     * specific key presses.
     *
     * <p>
     * 从Java 2平台v1.3这个方法不再使用。
     * 以前使用此方法的子类应该创建一个包装ActionListener的Action,并通过重写<code> installKeyboardActions </code>并将Action放入SplitPane
     * 的ActionMap中来注册该Action。
     * 从Java 2平台v1.3这个方法不再使用。有关更多详细信息,请参阅键绑定规范。
     * <p>
     *  为监听特定按键的JSplitPane UI创建一个ActionListener。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener createKeyboardUpLeftListener() {
        return new KeyboardUpLeftHandler();
    }


    /**
     * As of Java 2 platform v1.3 this method is no
     * longer used. Subclassers previously using this method should
     * instead create an Action wrapping the ActionListener, and register
     * that Action by overriding <code>installKeyboardActions</code> and
     * placing the Action in the SplitPane's ActionMap. Please refer to
     * the key bindings specification for further details.
     * <p>
     * Creates a ActionListener for the JSplitPane UI that listens for
     * specific key presses.
     *
     * <p>
     *  从Java 2平台v1.3这个方法不再使用。
     * 以前使用此方法的子类应该创建一个包装ActionListener的Action,并通过重写<code> installKeyboardActions </code>并将Action放入SplitPane
     * 的ActionMap中来注册该Action。
     *  从Java 2平台v1.3这个方法不再使用。有关更多详细信息,请参阅键绑定规范。
     * <p>
     *  为监听特定按键的JSplitPane UI创建一个ActionListener。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener createKeyboardDownRightListener() {
        return new KeyboardDownRightHandler();
    }


    /**
     * As of Java 2 platform v1.3 this method is no
     * longer used. Subclassers previously using this method should
     * instead create an Action wrapping the ActionListener, and register
     * that Action by overriding <code>installKeyboardActions</code> and
     * placing the Action in the SplitPane's ActionMap. Please refer to
     * the key bindings specification for further details.
     * <p>
     * Creates a ActionListener for the JSplitPane UI that listens for
     * specific key presses.
     *
     * <p>
     *  从Java 2平台v1.3这个方法不再使用。
     * 以前使用此方法的子类应该创建一个包装ActionListener的Action,并通过重写<code> installKeyboardActions </code>并将Action放入SplitPane
     * 的ActionMap中来注册该Action。
     *  从Java 2平台v1.3这个方法不再使用。有关更多详细信息,请参阅键绑定规范。
     * <p>
     *  为监听特定按键的JSplitPane UI创建一个ActionListener。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener createKeyboardHomeListener() {
        return new KeyboardHomeHandler();
    }


    /**
     * As of Java 2 platform v1.3 this method is no
     * longer used. Subclassers previously using this method should
     * instead create an Action wrapping the ActionListener, and register
     * that Action by overriding <code>installKeyboardActions</code> and
     * placing the Action in the SplitPane's ActionMap. Please refer to
     * the key bindings specification for further details.
     * <p>
     * Creates a ActionListener for the JSplitPane UI that listens for
     * specific key presses.
     *
     * <p>
     * 从Java 2平台v1.3这个方法不再使用。
     * 以前使用此方法的子类应该创建一个包装ActionListener的Action,并通过重写<code> installKeyboardActions </code>并将Action放入SplitPane
     * 的ActionMap中来注册该Action。
     * 从Java 2平台v1.3这个方法不再使用。有关更多详细信息,请参阅键绑定规范。
     * <p>
     *  为监听特定按键的JSplitPane UI创建一个ActionListener。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener createKeyboardEndListener() {
        return new KeyboardEndHandler();
    }


    /**
     * As of Java 2 platform v1.3 this method is no
     * longer used. Subclassers previously using this method should
     * instead create an Action wrapping the ActionListener, and register
     * that Action by overriding <code>installKeyboardActions</code> and
     * placing the Action in the SplitPane's ActionMap. Please refer to
     * the key bindings specification for further details.
     * <p>
     * Creates a ActionListener for the JSplitPane UI that listens for
     * specific key presses.
     *
     * <p>
     *  从Java 2平台v1.3这个方法不再使用。
     * 以前使用此方法的子类应该创建一个包装ActionListener的Action,并通过重写<code> installKeyboardActions </code>并将Action放入SplitPane
     * 的ActionMap中来注册该Action。
     *  从Java 2平台v1.3这个方法不再使用。有关更多详细信息,请参阅键绑定规范。
     * <p>
     *  为监听特定按键的JSplitPane UI创建一个ActionListener。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     */
    @Deprecated
    protected ActionListener createKeyboardResizeToggleListener() {
        return new KeyboardResizeToggleHandler();
    }


    /**
     * Returns the orientation for the JSplitPane.
     * <p>
     *  返回JSplitPane的方向。
     * 
     */
    public int getOrientation() {
        return orientation;
    }


    /**
     * Set the orientation for the JSplitPane.
     * <p>
     *  设置JSplitPane的方向。
     * 
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }


    /**
     * Determines whether the JSplitPane is set to use a continuous layout.
     * <p>
     *  确定JSplitPane是否设置为使用连续布局。
     * 
     */
    public boolean isContinuousLayout() {
        return continuousLayout;
    }


    /**
     * Turn continuous layout on/off.
     * <p>
     *  打开/关闭连续布局。
     * 
     */
    public void setContinuousLayout(boolean b) {
        continuousLayout = b;
    }


    /**
     * Returns the last drag location of the JSplitPane.
     * <p>
     *  返回JSplitPane的最后一个拖动位置。
     * 
     */
    public int getLastDragLocation() {
        return lastDragLocation;
    }


    /**
     * Set the last drag location of the JSplitPane.
     * <p>
     *  设置JSplitPane的最后一个拖动位置。
     * 
     */
    public void setLastDragLocation(int l) {
        lastDragLocation = l;
    }

    /**
    /* <p>
    /* 
     * @return increment via keyboard methods.
     */
    int getKeyboardMoveIncrement() {
        return 3;
    }

    /**
     * Implementation of the PropertyChangeListener
     * that the JSplitPane UI uses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI使用的PropertyChangeListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class PropertyHandler implements PropertyChangeListener
    {
        // NOTE: This class exists only for backward compatibility. All
        // its functionality has been moved into Handler. If you need to add
        // new functionality add it to the Handler, but make sure this
        // class calls into the Handler.

        /**
         * Messaged from the <code>JSplitPane</code> the receiver is
         * contained in.  May potentially reset the layout manager and cause a
         * <code>validate</code> to be sent.
         * <p>
         * 来自<code> JSplitPane </code>的消息包含在接收器中。可能重置布局管理器并导致发送<code>验证</code>。
         * 
         */
        public void propertyChange(PropertyChangeEvent e) {
            getHandler().propertyChange(e);
        }
    }


    /**
     * Implementation of the FocusListener that the JSplitPane UI uses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI使用的FocusListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class FocusHandler extends FocusAdapter
    {
        // NOTE: This class exists only for backward compatibility. All
        // its functionality has been moved into Handler. If you need to add
        // new functionality add it to the Handler, but make sure this
        // class calls into the Handler.
        public void focusGained(FocusEvent ev) {
            getHandler().focusGained(ev);
        }

        public void focusLost(FocusEvent ev) {
            getHandler().focusLost(ev);
        }
    }


    /**
     * Implementation of an ActionListener that the JSplitPane UI uses for
     * handling specific key presses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI用于处理特定按键的ActionListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class KeyboardUpLeftHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ev) {
            if (dividerKeyboardResize) {
                splitPane.setDividerLocation(Math.max(0,getDividerLocation
                                  (splitPane) - getKeyboardMoveIncrement()));
            }
        }
    }

    /**
     * Implementation of an ActionListener that the JSplitPane UI uses for
     * handling specific key presses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI用于处理特定按键的ActionListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class KeyboardDownRightHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ev) {
            if (dividerKeyboardResize) {
                splitPane.setDividerLocation(getDividerLocation(splitPane) +
                                             getKeyboardMoveIncrement());
            }
        }
    }


    /**
     * Implementation of an ActionListener that the JSplitPane UI uses for
     * handling specific key presses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI用于处理特定按键的ActionListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class KeyboardHomeHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ev) {
            if (dividerKeyboardResize) {
                splitPane.setDividerLocation(0);
            }
        }
    }


    /**
     * Implementation of an ActionListener that the JSplitPane UI uses for
     * handling specific key presses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI用于处理特定按键的ActionListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class KeyboardEndHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ev) {
            if (dividerKeyboardResize) {
                Insets   insets = splitPane.getInsets();
                int      bottomI = (insets != null) ? insets.bottom : 0;
                int      rightI = (insets != null) ? insets.right : 0;

                if (orientation == JSplitPane.VERTICAL_SPLIT) {
                    splitPane.setDividerLocation(splitPane.getHeight() -
                                       bottomI);
                }
                else {
                    splitPane.setDividerLocation(splitPane.getWidth() -
                                                 rightI);
                }
            }
        }
    }


    /**
     * Implementation of an ActionListener that the JSplitPane UI uses for
     * handling specific key presses.
     * <p>
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of BasicSplitPaneUI.
     * <p>
     *  JSplitPane UI用于处理特定按键的ActionListener的实现。
     * <p>
     *  该类应当被视为"受保护的"内部类。实例化它只在BasicSplitPaneUI的子类中。
     * 
     */
    public class KeyboardResizeToggleHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent ev) {
            if (!dividerKeyboardResize) {
                splitPane.requestFocus();
            }
        }
    }

    /**
     * Returns the divider between the top Components.
     * <p>
     * 返回顶部组件之间的分隔符。
     * 
     */
    public BasicSplitPaneDivider getDivider() {
        return divider;
    }


    /**
     * Returns the default non continuous layout divider, which is an
     * instance of {@code Canvas} that fills in the background with dark gray.
     * <p>
     *  返回默认的非连续布局分隔符,它是以深灰色填充背景的{@code Canvas}的实例。
     * 
     */
    protected Component createDefaultNonContinuousLayoutDivider() {
        return new Canvas() {
            public void paint(Graphics g) {
                if(!isContinuousLayout() && getLastDragLocation() != -1) {
                    Dimension      size = splitPane.getSize();

                    g.setColor(dividerDraggingColor);
                    if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                        g.fillRect(0, 0, dividerSize - 1, size.height - 1);
                    } else {
                        g.fillRect(0, 0, size.width - 1, dividerSize - 1);
                    }
                }
            }
        };
    }


    /**
     * Sets the divider to use when the splitPane is configured to
     * not continuously layout. This divider will only be used during a
     * dragging session. It is recommended that the passed in component
     * be a heavy weight.
     * <p>
     *  当splitPane配置为不连续布局时,设置要使用的分频器。此分隔符仅在拖动会话期间使用。建议传入的组件重量很重。
     * 
     */
    protected void setNonContinuousLayoutDivider(Component newDivider) {
        setNonContinuousLayoutDivider(newDivider, true);
    }


    /**
     * Sets the divider to use.
     * <p>
     *  设置要使用的分频器。
     * 
     */
    protected void setNonContinuousLayoutDivider(Component newDivider,
        boolean rememberSizes) {
        rememberPaneSizes = rememberSizes;
        if(nonContinuousLayoutDivider != null && splitPane != null) {
            splitPane.remove(nonContinuousLayoutDivider);
        }
        nonContinuousLayoutDivider = newDivider;
    }

    private void addHeavyweightDivider() {
        if(nonContinuousLayoutDivider != null && splitPane != null) {

            /* Needs to remove all the components and re-add them! YECK! */
            // This is all done so that the nonContinuousLayoutDivider will
            // be drawn on top of the other components, without this, one
            // of the heavyweights will draw over the divider!
            Component             leftC = splitPane.getLeftComponent();
            Component             rightC = splitPane.getRightComponent();
            int                   lastLocation = splitPane.
                                              getDividerLocation();

            if(leftC != null)
                splitPane.setLeftComponent(null);
            if(rightC != null)
                splitPane.setRightComponent(null);
            splitPane.remove(divider);
            splitPane.add(nonContinuousLayoutDivider, BasicSplitPaneUI.
                          NON_CONTINUOUS_DIVIDER,
                          splitPane.getComponentCount());
            splitPane.setLeftComponent(leftC);
            splitPane.setRightComponent(rightC);
            splitPane.add(divider, JSplitPane.DIVIDER);
            if(rememberPaneSizes) {
                splitPane.setDividerLocation(lastLocation);
            }
        }

    }


    /**
     * Returns the divider to use when the splitPane is configured to
     * not continuously layout. This divider will only be used during a
     * dragging session.
     * <p>
     *  返回当splitPane配置为不连续布局时要使用的分隔符。此分隔符仅在拖动会话期间使用。
     * 
     */
    public Component getNonContinuousLayoutDivider() {
        return nonContinuousLayoutDivider;
    }


    /**
     * Returns the splitpane this instance is currently contained
     * in.
     * <p>
     *  返回此实例当前包含的splitpane。
     * 
     */
    public JSplitPane getSplitPane() {
        return splitPane;
    }


    /**
     * Creates the default divider.
     * <p>
     *  创建默认分频器。
     * 
     */
    public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this);
    }


    /**
     * Messaged to reset the preferred sizes.
     * <p>
     *  消息以重置首选大小。
     * 
     */
    public void resetToPreferredSizes(JSplitPane jc) {
        if(splitPane != null) {
            layoutManager.resetToPreferredSizes();
            splitPane.revalidate();
            splitPane.repaint();
        }
    }


    /**
     * Sets the location of the divider to location.
     * <p>
     *  将分隔符的位置设置为位置。
     * 
     */
    public void setDividerLocation(JSplitPane jc, int location) {
        if (!ignoreDividerLocationChange) {
            dividerLocationIsSet = true;
            splitPane.revalidate();
            splitPane.repaint();

            if (keepHidden) {
                Insets insets = splitPane.getInsets();
                int orientation = splitPane.getOrientation();
                if ((orientation == JSplitPane.VERTICAL_SPLIT &&
                     location != insets.top &&
                     location != splitPane.getHeight()-divider.getHeight()-insets.top) ||
                    (orientation == JSplitPane.HORIZONTAL_SPLIT &&
                     location != insets.left &&
                     location != splitPane.getWidth()-divider.getWidth()-insets.left)) {
                    setKeepHidden(false);
                }
            }
        }
        else {
            ignoreDividerLocationChange = false;
        }
    }


    /**
     * Returns the location of the divider, which may differ from what
     * the splitpane thinks the location of the divider is.
     * <p>
     *  返回分隔符的位置,这可能与拆分窗口认为分隔符的位置不同。
     * 
     */
    public int getDividerLocation(JSplitPane jc) {
        if(orientation == JSplitPane.HORIZONTAL_SPLIT)
            return divider.getLocation().x;
        return divider.getLocation().y;
    }


    /**
     * Gets the minimum location of the divider.
     * <p>
     *  获取分隔线的最小位置。
     * 
     */
    public int getMinimumDividerLocation(JSplitPane jc) {
        int       minLoc = 0;
        Component leftC = splitPane.getLeftComponent();

        if ((leftC != null) && (leftC.isVisible())) {
            Insets    insets = splitPane.getInsets();
            Dimension minSize = leftC.getMinimumSize();
            if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                minLoc = minSize.width;
            } else {
                minLoc = minSize.height;
            }
            if(insets != null) {
                if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    minLoc += insets.left;
                } else {
                    minLoc += insets.top;
                }
            }
        }
        return minLoc;
    }


    /**
     * Gets the maximum location of the divider.
     * <p>
     *  获取分隔线的最大位置。
     * 
     */
    public int getMaximumDividerLocation(JSplitPane jc) {
        Dimension splitPaneSize = splitPane.getSize();
        int       maxLoc = 0;
        Component rightC = splitPane.getRightComponent();

        if (rightC != null) {
            Insets    insets = splitPane.getInsets();
            Dimension minSize = new Dimension(0, 0);
            if (rightC.isVisible()) {
                minSize = rightC.getMinimumSize();
            }
            if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                maxLoc = splitPaneSize.width - minSize.width;
            } else {
                maxLoc = splitPaneSize.height - minSize.height;
            }
            maxLoc -= dividerSize;
            if(insets != null) {
                if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    maxLoc -= insets.right;
                } else {
                    maxLoc -= insets.top;
                }
            }
        }
        return Math.max(getMinimumDividerLocation(splitPane), maxLoc);
    }


    /**
     * Called when the specified split pane has finished painting
     * its children.
     * <p>
     *  当指定的分割窗格完成绘制其子代时调用。
     * 
     */
    public void finishedPaintingChildren(JSplitPane sp, Graphics g) {
        if(sp == splitPane && getLastDragLocation() != -1 &&
           !isContinuousLayout() && !draggingHW) {
            Dimension      size = splitPane.getSize();

            g.setColor(dividerDraggingColor);
            if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                g.fillRect(getLastDragLocation(), 0, dividerSize - 1,
                           size.height - 1);
            } else {
                g.fillRect(0, lastDragLocation, size.width - 1,
                           dividerSize - 1);
            }
        }
    }


    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void paint(Graphics g, JComponent jc) {
        if (!painted && splitPane.getDividerLocation()<0) {
            ignoreDividerLocationChange = true;
            splitPane.setDividerLocation(getDividerLocation(splitPane));
        }
        painted = true;
    }


    /**
     * Returns the preferred size for the passed in component,
     * This is passed off to the current layout manager.
     * <p>
     *  返回传入组件的首选大小,这将传递到当前布局管理器。
     * 
     */
    public Dimension getPreferredSize(JComponent jc) {
        if(splitPane != null)
            return layoutManager.preferredLayoutSize(splitPane);
        return new Dimension(0, 0);
    }


    /**
     * Returns the minimum size for the passed in component,
     * This is passed off to the current layout manager.
     * <p>
     *  返回传入组件的最小大小,这将传递到当前布局管理器。
     * 
     */
    public Dimension getMinimumSize(JComponent jc) {
        if(splitPane != null)
            return layoutManager.minimumLayoutSize(splitPane);
        return new Dimension(0, 0);
    }


    /**
     * Returns the maximum size for the passed in component,
     * This is passed off to the current layout manager.
     * <p>
     *  返回传入组件的最大大小,这被传递到当前布局管理器。
     * 
     */
    public Dimension getMaximumSize(JComponent jc) {
        if(splitPane != null)
            return layoutManager.maximumLayoutSize(splitPane);
        return new Dimension(0, 0);
    }


    /**
     * Returns the insets. The insets are returned from the border insets
     * of the current border.
     * <p>
     *  返回插图。插入从当前边界的边框插入返回。
     * 
     */
    public Insets getInsets(JComponent jc) {
        return null;
    }


    /**
     * Resets the layout manager based on orientation and messages it
     * with invalidateLayout to pull in appropriate Components.
     * <p>
     * 根据方向重置布局管理器,并使用invalidateLayout发送消息,以拉入相应的组件。
     * 
     */
    protected void resetLayoutManager() {
        if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
            layoutManager = new BasicHorizontalLayoutManager(0);
        } else {
            layoutManager = new BasicHorizontalLayoutManager(1);
        }
        splitPane.setLayout(layoutManager);
        layoutManager.updateComponents();
        splitPane.revalidate();
        splitPane.repaint();
    }

    /**
     * Set the value to indicate if one of the splitpane sides is expanded.
     * <p>
     *  设置值以指示是否扩展了其中一个分裂面。
     * 
     */
    void setKeepHidden(boolean keepHidden) {
        this.keepHidden = keepHidden;
    }

    /**
     * The value returned indicates if one of the splitpane sides is expanded.
     * <p>
     *  返回的值指示是否扩展了其中一个分裂面。
     * 
     * 
     * @return true if one of the splitpane sides is expanded, false otherwise.
     */
    private boolean getKeepHidden() {
        return keepHidden;
    }

    /**
     * Should be messaged before the dragging session starts, resets
     * lastDragLocation and dividerSize.
     * <p>
     *  在拖动会话开始之前应该发送消息,重置lastDragLocation和dividerSize。
     * 
     */
    protected void startDragging() {
        Component       leftC = splitPane.getLeftComponent();
        Component       rightC = splitPane.getRightComponent();
        ComponentPeer   cPeer;

        beginDragDividerLocation = getDividerLocation(splitPane);
        draggingHW = false;
        if(leftC != null && (cPeer = leftC.getPeer()) != null &&
           !(cPeer instanceof LightweightPeer)) {
            draggingHW = true;
        } else if(rightC != null && (cPeer = rightC.getPeer()) != null
                  && !(cPeer instanceof LightweightPeer)) {
            draggingHW = true;
        }
        if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
            setLastDragLocation(divider.getBounds().x);
            dividerSize = divider.getSize().width;
            if(!isContinuousLayout() && draggingHW) {
                nonContinuousLayoutDivider.setBounds
                        (getLastDragLocation(), 0, dividerSize,
                         splitPane.getHeight());
                      addHeavyweightDivider();
            }
        } else {
            setLastDragLocation(divider.getBounds().y);
            dividerSize = divider.getSize().height;
            if(!isContinuousLayout() && draggingHW) {
                nonContinuousLayoutDivider.setBounds
                        (0, getLastDragLocation(), splitPane.getWidth(),
                         dividerSize);
                      addHeavyweightDivider();
            }
        }
    }


    /**
     * Messaged during a dragging session to move the divider to the
     * passed in location. If continuousLayout is true the location is
     * reset and the splitPane validated.
     * <p>
     *  在拖动会话期间消息,将分隔符移动到传入的位置。如果continuousLayout为true,则会重置位置,并验证splitPane。
     * 
     */
    protected void dragDividerTo(int location) {
        if(getLastDragLocation() != location) {
            if(isContinuousLayout()) {
                splitPane.setDividerLocation(location);
                setLastDragLocation(location);
            } else {
                int lastLoc = getLastDragLocation();

                setLastDragLocation(location);
                if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    if(draggingHW) {
                        nonContinuousLayoutDivider.setLocation(
                            getLastDragLocation(), 0);
                    } else {
                        int   splitHeight = splitPane.getHeight();
                        splitPane.repaint(lastLoc, 0, dividerSize,
                                          splitHeight);
                        splitPane.repaint(location, 0, dividerSize,
                                          splitHeight);
                    }
                } else {
                    if(draggingHW) {
                        nonContinuousLayoutDivider.setLocation(0,
                            getLastDragLocation());
                    } else {
                        int    splitWidth = splitPane.getWidth();

                        splitPane.repaint(0, lastLoc, splitWidth,
                                          dividerSize);
                        splitPane.repaint(0, location, splitWidth,
                                          dividerSize);
                    }
                }
            }
        }
    }


    /**
     * Messaged to finish the dragging session. If not continuous display
     * the dividers location will be reset.
     * <p>
     *  消息完成拖动会话。如果不连续显示,分频器位置将被重置。
     * 
     */
    protected void finishDraggingTo(int location) {
        dragDividerTo(location);
        setLastDragLocation(-1);
        if(!isContinuousLayout()) {
            Component   leftC = splitPane.getLeftComponent();
            Rectangle   leftBounds = leftC.getBounds();

            if (draggingHW) {
                if(orientation == JSplitPane.HORIZONTAL_SPLIT) {
                    nonContinuousLayoutDivider.setLocation(-dividerSize, 0);
                }
                else {
                    nonContinuousLayoutDivider.setLocation(0, -dividerSize);
                }
                splitPane.remove(nonContinuousLayoutDivider);
            }
            splitPane.setDividerLocation(location);
        }
    }


    /**
     * As of Java 2 platform v1.3 this method is no longer used. Instead
     * you should set the border on the divider.
     * <p>
     * Returns the width of one side of the divider border.
     *
     * <p>
     *  从Java 2平台v1.3这个方法不再使用。相反,你应该在分隔线上设置边框。
     * <p>
     *  返回分隔边框一侧的宽度。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3, instead set the border on the
     * divider.
     */
    @Deprecated
    protected int getDividerBorderSize() {
        return 1;
    }


    /**
     * LayoutManager for JSplitPanes that have an orientation of
     * HORIZONTAL_SPLIT.
     * <p>
     *  具有HORIZONTAL_SPLIT方向的JSplitPanes的LayoutManager。
     * 
     */
    public class BasicHorizontalLayoutManager implements LayoutManager2
    {
        /* left, right, divider. (in this exact order) */
        protected int[]         sizes;
        protected Component[]   components;
        /** Size of the splitpane the last time laid out. */
        private int             lastSplitPaneSize;
        /** True if resetToPreferredSizes has been invoked. */
        private boolean         doReset;
        /** Axis, 0 for horizontal, or 1 for veritcal. */
        private int             axis;


        BasicHorizontalLayoutManager() {
            this(0);
        }

        BasicHorizontalLayoutManager(int axis) {
            this.axis = axis;
            components = new Component[3];
            components[0] = components[1] = components[2] = null;
            sizes = new int[3];
        }

        //
        // LayoutManager
        //

        /**
         * Does the actual layout.
         * <p>
         *  是否实际布局。
         * 
         */
        public void layoutContainer(Container container) {
            Dimension   containerSize = container.getSize();

            // If the splitpane has a zero size then no op out of here.
            // If we execute this function now, we're going to cause ourselves
            // much grief.
            if (containerSize.height <= 0 || containerSize.width <= 0 ) {
                lastSplitPaneSize = 0;
                return;
            }

            int         spDividerLocation = splitPane.getDividerLocation();
            Insets      insets = splitPane.getInsets();
            int         availableSize = getAvailableSize(containerSize,
                                                         insets);
            int         newSize = getSizeForPrimaryAxis(containerSize);
            int         beginLocation = getDividerLocation(splitPane);
            int         dOffset = getSizeForPrimaryAxis(insets, true);
            Dimension   dSize = (components[2] == null) ? null :
                                 components[2].getPreferredSize();

            if ((doReset && !dividerLocationIsSet) || spDividerLocation < 0) {
                resetToPreferredSizes(availableSize);
            }
            else if (lastSplitPaneSize <= 0 ||
                     availableSize == lastSplitPaneSize || !painted ||
                     (dSize != null &&
                      getSizeForPrimaryAxis(dSize) != sizes[2])) {
                if (dSize != null) {
                    sizes[2] = getSizeForPrimaryAxis(dSize);
                }
                else {
                    sizes[2] = 0;
                }
                setDividerLocation(spDividerLocation - dOffset, availableSize);
                dividerLocationIsSet = false;
            }
            else if (availableSize != lastSplitPaneSize) {
                distributeSpace(availableSize - lastSplitPaneSize,
                                getKeepHidden());
            }
            doReset = false;
            dividerLocationIsSet = false;
            lastSplitPaneSize = availableSize;

            // Reset the bounds of each component
            int nextLocation = getInitialLocation(insets);
            int counter = 0;

            while (counter < 3) {
                if (components[counter] != null &&
                    components[counter].isVisible()) {
                    setComponentToSize(components[counter], sizes[counter],
                                       nextLocation, insets, containerSize);
                    nextLocation += sizes[counter];
                }
                switch (counter) {
                case 0:
                    counter = 2;
                    break;
                case 2:
                    counter = 1;
                    break;
                case 1:
                    counter = 3;
                    break;
                }
            }
            if (painted) {
                // This is tricky, there is never a good time for us
                // to push the value to the splitpane, painted appears to
                // the best time to do it. What is really needed is
                // notification that layout has completed.
                int      newLocation = getDividerLocation(splitPane);

                if (newLocation != (spDividerLocation - dOffset)) {
                    int  lastLocation = splitPane.getLastDividerLocation();

                    ignoreDividerLocationChange = true;
                    try {
                        splitPane.setDividerLocation(newLocation);
                        // This is not always needed, but is rather tricky
                        // to determine when... The case this is needed for
                        // is if the user sets the divider location to some
                        // bogus value, say 0, and the actual value is 1, the
                        // call to setDividerLocation(1) will preserve the
                        // old value of 0, when we really want the divider
                        // location value  before the call. This is needed for
                        // the one touch buttons.
                        splitPane.setLastDividerLocation(lastLocation);
                    } finally {
                        ignoreDividerLocationChange = false;
                    }
                }
            }
        }


        /**
         * Adds the component at place.  Place must be one of
         * JSplitPane.LEFT, RIGHT, TOP, BOTTOM, or null (for the
         * divider).
         * <p>
         *  在地方添加组件。地方必须是JSplitPane.LEFT,RIGHT,TOP,BOTTOM或null(用于分隔符)之一。
         * 
         */
        public void addLayoutComponent(String place, Component component) {
            boolean isValid = true;

            if(place != null) {
                if(place.equals(JSplitPane.DIVIDER)) {
                    /* Divider. */
                    components[2] = component;
                    sizes[2] = getSizeForPrimaryAxis(component.
                                                     getPreferredSize());
                } else if(place.equals(JSplitPane.LEFT) ||
                          place.equals(JSplitPane.TOP)) {
                    components[0] = component;
                    sizes[0] = 0;
                } else if(place.equals(JSplitPane.RIGHT) ||
                          place.equals(JSplitPane.BOTTOM)) {
                    components[1] = component;
                    sizes[1] = 0;
                } else if(!place.equals(
                                    BasicSplitPaneUI.NON_CONTINUOUS_DIVIDER))
                    isValid = false;
            } else {
                isValid = false;
            }
            if(!isValid)
                throw new IllegalArgumentException("cannot add to layout: " +
                    "unknown constraint: " +
                    place);
            doReset = true;
        }


        /**
         * Returns the minimum size needed to contain the children.
         * The width is the sum of all the children's min widths and
         * the height is the largest of the children's minimum heights.
         * <p>
         *  返回包含子元素所需的最小大小。宽度是所有儿童最小宽度的总和,高度是儿童最小高度中最大的。
         * 
         */
        public Dimension minimumLayoutSize(Container container) {
            int         minPrimary = 0;
            int         minSecondary = 0;
            Insets      insets = splitPane.getInsets();

            for (int counter=0; counter<3; counter++) {
                if(components[counter] != null) {
                    Dimension   minSize = components[counter].getMinimumSize();
                    int         secSize = getSizeForSecondaryAxis(minSize);

                    minPrimary += getSizeForPrimaryAxis(minSize);
                    if(secSize > minSecondary)
                        minSecondary = secSize;
                }
            }
            if(insets != null) {
                minPrimary += getSizeForPrimaryAxis(insets, true) +
                              getSizeForPrimaryAxis(insets, false);
                minSecondary += getSizeForSecondaryAxis(insets, true) +
                              getSizeForSecondaryAxis(insets, false);
            }
            if (axis == 0) {
                return new Dimension(minPrimary, minSecondary);
            }
            return new Dimension(minSecondary, minPrimary);
        }


        /**
         * Returns the preferred size needed to contain the children.
         * The width is the sum of all the preferred widths of the children and
         * the height is the largest preferred height of the children.
         * <p>
         *  返回包含子元素所需的首选大小。宽度是孩子的所有优选宽度的总和,高度是孩子的最大优选高度。
         * 
         */
        public Dimension preferredLayoutSize(Container container) {
            int         prePrimary = 0;
            int         preSecondary = 0;
            Insets      insets = splitPane.getInsets();

            for(int counter = 0; counter < 3; counter++) {
                if(components[counter] != null) {
                    Dimension   preSize = components[counter].
                                          getPreferredSize();
                    int         secSize = getSizeForSecondaryAxis(preSize);

                    prePrimary += getSizeForPrimaryAxis(preSize);
                    if(secSize > preSecondary)
                        preSecondary = secSize;
                }
            }
            if(insets != null) {
                prePrimary += getSizeForPrimaryAxis(insets, true) +
                              getSizeForPrimaryAxis(insets, false);
                preSecondary += getSizeForSecondaryAxis(insets, true) +
                              getSizeForSecondaryAxis(insets, false);
            }
            if (axis == 0) {
                return new Dimension(prePrimary, preSecondary);
            }
            return new Dimension(preSecondary, prePrimary);
        }


        /**
         * Removes the specified component from our knowledge.
         * <p>
         *  从我们的知识中删除指定的组件。
         * 
         */
        public void removeLayoutComponent(Component component) {
            for(int counter = 0; counter < 3; counter++) {
                if(components[counter] == component) {
                    components[counter] = null;
                    sizes[counter] = 0;
                    doReset = true;
                }
            }
        }


        //
        // LayoutManager2
        //


        /**
         * Adds the specified component to the layout, using the specified
         * constraint object.
         * <p>
         * 使用指定的约束对象将指定的组件添加到布局。
         * 
         * 
         * @param comp the component to be added
         * @param constraints  where/how the component is added to the layout.
         */
        public void addLayoutComponent(Component comp, Object constraints) {
            if ((constraints == null) || (constraints instanceof String)) {
                addLayoutComponent((String)constraints, comp);
            } else {
                throw new IllegalArgumentException("cannot add to layout: " +
                                                   "constraint must be a " +
                                                   "string (or null)");
            }
        }


        /**
         * Returns the alignment along the x axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         * <p>
         *  返回沿x轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
         * 
         */
        public float getLayoutAlignmentX(Container target) {
            return 0.0f;
        }


        /**
         * Returns the alignment along the y axis.  This specifies how
         * the component would like to be aligned relative to other
         * components.  The value should be a number between 0 and 1
         * where 0 represents alignment along the origin, 1 is aligned
         * the furthest away from the origin, 0.5 is centered, etc.
         * <p>
         *  返回沿y轴的对齐。这指定了组件将如何相对于其他组件对齐。该值应为0和1之间的数字,其中0表示沿原点的对齐,1对齐距离原点最远,0.5为中心等。
         * 
         */
        public float getLayoutAlignmentY(Container target) {
            return 0.0f;
        }


        /**
         * Does nothing. If the developer really wants to change the
         * size of one of the views JSplitPane.resetToPreferredSizes should
         * be messaged.
         * <p>
         *  什么也没做。如果开发人员真的想改变其中一个视图的大小JSplitPane.resetToPreferredSizes应该被消息。
         * 
         */
        public void invalidateLayout(Container c) {
        }


        /**
         * Returns the maximum layout size, which is Integer.MAX_VALUE
         * in both directions.
         * <p>
         *  返回最大布局大小,即两个方向上的Integer.MAX_VALUE。
         * 
         */
        public Dimension maximumLayoutSize(Container target) {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }


        //
        // New methods.
        //

        /**
         * Marks the receiver so that the next time this instance is
         * laid out it'll ask for the preferred sizes.
         * <p>
         *  标记接收器,以便下次该实例布局时,它会询问首选大小。
         * 
         */
        public void resetToPreferredSizes() {
            doReset = true;
        }

        /**
         * Resets the size of the Component at the passed in location.
         * <p>
         *  重置在传入位置的组件的大小。
         * 
         */
        protected void resetSizeAt(int index) {
            sizes[index] = 0;
            doReset = true;
        }


        /**
         * Sets the sizes to <code>newSizes</code>.
         * <p>
         *  将大小设置为<code> newSizes </code>。
         * 
         */
        protected void setSizes(int[] newSizes) {
            System.arraycopy(newSizes, 0, sizes, 0, 3);
        }


        /**
         * Returns the sizes of the components.
         * <p>
         *  返回组件的大小。
         * 
         */
        protected int[] getSizes() {
            int[]         retSizes = new int[3];

            System.arraycopy(sizes, 0, retSizes, 0, 3);
            return retSizes;
        }


        /**
         * Returns the width of the passed in Components preferred size.
         * <p>
         *  返回传入的组件首选大小的宽度。
         * 
         */
        protected int getPreferredSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getPreferredSize());
        }


        /**
         * Returns the width of the passed in Components minimum size.
         * <p>
         *  返回传入的组件最小大小的宽度。
         * 
         */
        int getMinimumSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getMinimumSize());
        }


        /**
         * Returns the width of the passed in component.
         * <p>
         *  返回传入组件的宽度。
         * 
         */
        protected int getSizeOfComponent(Component c) {
            return getSizeForPrimaryAxis(c.getSize());
        }


        /**
         * Returns the available width based on the container size and
         * Insets.
         * <p>
         *  根据容器大小和Insets返回可用宽度。
         * 
         */
        protected int getAvailableSize(Dimension containerSize,
                                       Insets insets) {
            if(insets == null)
                return getSizeForPrimaryAxis(containerSize);
            return (getSizeForPrimaryAxis(containerSize) -
                    (getSizeForPrimaryAxis(insets, true) +
                     getSizeForPrimaryAxis(insets, false)));
        }


        /**
         * Returns the left inset, unless the Insets are null in which case
         * 0 is returned.
         * <p>
         *  返回左插入,除非Insets为null,在这种情况下返回0。
         * 
         */
        protected int getInitialLocation(Insets insets) {
            if(insets != null)
                return getSizeForPrimaryAxis(insets, true);
            return 0;
        }


        /**
         * Sets the width of the component c to be size, placing its
         * x location at location, y to the insets.top and height
         * to the containersize.height less the top and bottom insets.
         * <p>
         * 将组件c的宽度设置为size,将x位置放在位置,y放在insets.top,高度放在containersize.height减去顶部和底部插入。
         * 
         */
        protected void setComponentToSize(Component c, int size,
                                          int location, Insets insets,
                                          Dimension containerSize) {
            if(insets != null) {
                if (axis == 0) {
                    c.setBounds(location, insets.top, size,
                                containerSize.height -
                                (insets.top + insets.bottom));
                }
                else {
                    c.setBounds(insets.left, location, containerSize.width -
                                (insets.left + insets.right), size);
                }
            }
            else {
                if (axis == 0) {
                    c.setBounds(location, 0, size, containerSize.height);
                }
                else {
                    c.setBounds(0, location, containerSize.width, size);
                }
            }
        }

        /**
         * If the axis == 0, the width is returned, otherwise the height.
         * <p>
         *  如果axis == 0,则返回宽度,否则返回height。
         * 
         */
        int getSizeForPrimaryAxis(Dimension size) {
            if (axis == 0) {
                return size.width;
            }
            return size.height;
        }

        /**
         * If the axis == 0, the width is returned, otherwise the height.
         * <p>
         *  如果axis == 0,则返回宽度,否则返回height。
         * 
         */
        int getSizeForSecondaryAxis(Dimension size) {
            if (axis == 0) {
                return size.height;
            }
            return size.width;
        }

        /**
         * Returns a particular value of the inset identified by the
         * axis and <code>isTop</code><p>
         *   axis isTop
         *    0    true    - left
         *    0    false   - right
         *    1    true    - top
         *    1    false   - bottom
         * <p>
         *  返回由轴和<code> isTop </code> <p>标识的插入的特定值axis isTop 0 true  -  left 0 false  -  right 1 true  -  top 1 
         * false  -  bottom。
         * 
         */
        int getSizeForPrimaryAxis(Insets insets, boolean isTop) {
            if (axis == 0) {
                if (isTop) {
                    return insets.left;
                }
                return insets.right;
            }
            if (isTop) {
                return insets.top;
            }
            return insets.bottom;
        }

        /**
         * Returns a particular value of the inset identified by the
         * axis and <code>isTop</code><p>
         *   axis isTop
         *    0    true    - left
         *    0    false   - right
         *    1    true    - top
         *    1    false   - bottom
         * <p>
         *  返回由轴和<code> isTop </code> <p>标识的插入的特定值axis isTop 0 true  -  left 0 false  -  right 1 true  -  top 1 
         * false  -  bottom。
         * 
         */
        int getSizeForSecondaryAxis(Insets insets, boolean isTop) {
            if (axis == 0) {
                if (isTop) {
                    return insets.top;
                }
                return insets.bottom;
            }
            if (isTop) {
                return insets.left;
            }
            return insets.right;
        }

        /**
         * Determines the components. This should be called whenever
         * a new instance of this is installed into an existing
         * SplitPane.
         * <p>
         *  确定组件。每当将新实例安装到现有SplitPane中时,应调用此方法。
         * 
         */
        protected void updateComponents() {
            Component comp;

            comp = splitPane.getLeftComponent();
            if(components[0] != comp) {
                components[0] = comp;
                if(comp == null) {
                    sizes[0] = 0;
                } else {
                    sizes[0] = -1;
                }
            }

            comp = splitPane.getRightComponent();
            if(components[1] != comp) {
                components[1] = comp;
                if(comp == null) {
                    sizes[1] = 0;
                } else {
                    sizes[1] = -1;
                }
            }

            /* Find the divider. */
            Component[] children = splitPane.getComponents();
            Component   oldDivider = components[2];

            components[2] = null;
            for(int counter = children.length - 1; counter >= 0; counter--) {
                if(children[counter] != components[0] &&
                   children[counter] != components[1] &&
                   children[counter] != nonContinuousLayoutDivider) {
                    if(oldDivider != children[counter]) {
                        components[2] = children[counter];
                    } else {
                        components[2] = oldDivider;
                    }
                    break;
                }
            }
            if(components[2] == null) {
                sizes[2] = 0;
            }
            else {
                sizes[2] = getSizeForPrimaryAxis(components[2].getPreferredSize());
            }
        }

        /**
         * Resets the size of the first component to <code>leftSize</code>,
         * and the right component to the remainder of the space.
         * <p>
         *  将第一个组件的大小重置为<code> leftSize </code>,将右侧组件重置为空间的其余部分。
         * 
         */
        void setDividerLocation(int leftSize, int availableSize) {
            boolean          lValid = (components[0] != null &&
                                       components[0].isVisible());
            boolean          rValid = (components[1] != null &&
                                       components[1].isVisible());
            boolean          dValid = (components[2] != null &&
                                       components[2].isVisible());
            int              max = availableSize;

            if (dValid) {
                max -= sizes[2];
            }
            leftSize = Math.max(0, Math.min(leftSize, max));
            if (lValid) {
                if (rValid) {
                    sizes[0] = leftSize;
                    sizes[1] = max - leftSize;
                }
                else {
                    sizes[0] = max;
                    sizes[1] = 0;
                }
            }
            else if (rValid) {
                sizes[1] = max;
                sizes[0] = 0;
            }
        }

        /**
         * Returns an array of the minimum sizes of the components.
         * <p>
         *  返回组件的最小大小的数组。
         * 
         */
        int[] getPreferredSizes() {
            int[]         retValue = new int[3];

            for (int counter = 0; counter < 3; counter++) {
                if (components[counter] != null &&
                    components[counter].isVisible()) {
                    retValue[counter] = getPreferredSizeOfComponent
                                        (components[counter]);
                }
                else {
                    retValue[counter] = -1;
                }
            }
            return retValue;
        }

        /**
         * Returns an array of the minimum sizes of the components.
         * <p>
         *  返回组件的最小大小的数组。
         * 
         */
        int[] getMinimumSizes() {
            int[]         retValue = new int[3];

            for (int counter = 0; counter < 2; counter++) {
                if (components[counter] != null &&
                    components[counter].isVisible()) {
                    retValue[counter] = getMinimumSizeOfComponent
                                        (components[counter]);
                }
                else {
                    retValue[counter] = -1;
                }
            }
            retValue[2] = (components[2] != null) ?
                getMinimumSizeOfComponent(components[2]) : -1;
            return retValue;
        }

        /**
         * Resets the components to their preferred sizes.
         * <p>
         *  将组件重置为其首选大小。
         * 
         */
        void resetToPreferredSizes(int availableSize) {
            // Set the sizes to the preferred sizes (if fits), otherwise
            // set to min sizes and distribute any extra space.
            int[]       testSizes = getPreferredSizes();
            int         totalSize = 0;

            for (int counter = 0; counter < 3; counter++) {
                if (testSizes[counter] != -1) {
                    totalSize += testSizes[counter];
                }
            }
            if (totalSize > availableSize) {
                testSizes = getMinimumSizes();

                totalSize = 0;
                for (int counter = 0; counter < 3; counter++) {
                    if (testSizes[counter] != -1) {
                        totalSize += testSizes[counter];
                    }
                }
            }
            setSizes(testSizes);
            distributeSpace(availableSize - totalSize, false);
        }

        /**
         * Distributes <code>space</code> between the two components
         * (divider won't get any extra space) based on the weighting. This
         * attempts to honor the min size of the components.
         *
         * <p>
         *  基于加权,在两个组件之间分配<code>空间</code>(分隔符不会获得任何额外的空间)。这试图满足组件的最小尺寸。
         * 
         * 
         * @param keepHidden if true and one of the components is 0x0
         *                   it gets none of the extra space
         */
        void distributeSpace(int space, boolean keepHidden) {
            boolean          lValid = (components[0] != null &&
                                       components[0].isVisible());
            boolean          rValid = (components[1] != null &&
                                       components[1].isVisible());

            if (keepHidden) {
                if (lValid && getSizeForPrimaryAxis(
                                 components[0].getSize()) == 0) {
                    lValid = false;
                    if (rValid && getSizeForPrimaryAxis(
                                     components[1].getSize()) == 0) {
                        // Both aren't valid, force them both to be valid
                        lValid = true;
                    }
                }
                else if (rValid && getSizeForPrimaryAxis(
                                   components[1].getSize()) == 0) {
                    rValid = false;
                }
            }
            if (lValid && rValid) {
                double        weight = splitPane.getResizeWeight();
                int           lExtra = (int)(weight * (double)space);
                int           rExtra = (space - lExtra);

                sizes[0] += lExtra;
                sizes[1] += rExtra;

                int           lMin = getMinimumSizeOfComponent(components[0]);
                int           rMin = getMinimumSizeOfComponent(components[1]);
                boolean       lMinValid = (sizes[0] >= lMin);
                boolean       rMinValid = (sizes[1] >= rMin);

                if (!lMinValid && !rMinValid) {
                    if (sizes[0] < 0) {
                        sizes[1] += sizes[0];
                        sizes[0] = 0;
                    }
                    else if (sizes[1] < 0) {
                        sizes[0] += sizes[1];
                        sizes[1] = 0;
                    }
                }
                else if (!lMinValid) {
                    if (sizes[1] - (lMin - sizes[0]) < rMin) {
                        // both below min, just make sure > 0
                        if (sizes[0] < 0) {
                            sizes[1] += sizes[0];
                            sizes[0] = 0;
                        }
                    }
                    else {
                        sizes[1] -= (lMin - sizes[0]);
                        sizes[0] = lMin;
                    }
                }
                else if (!rMinValid) {
                    if (sizes[0] - (rMin - sizes[1]) < lMin) {
                        // both below min, just make sure > 0
                        if (sizes[1] < 0) {
                            sizes[0] += sizes[1];
                            sizes[1] = 0;
                        }
                    }
                    else {
                        sizes[0] -= (rMin - sizes[1]);
                        sizes[1] = rMin;
                    }
                }
                if (sizes[0] < 0) {
                    sizes[0] = 0;
                }
                if (sizes[1] < 0) {
                    sizes[1] = 0;
                }
            }
            else if (lValid) {
                sizes[0] = Math.max(0, sizes[0] + space);
            }
            else if (rValid) {
                sizes[1] = Math.max(0, sizes[1] + space);
            }
        }
    }


    /**
     * LayoutManager used for JSplitPanes with an orientation of
     * VERTICAL_SPLIT.
     *
     * <p>
     *  LayoutManager用于具有VERTICAL_SPLIT方向的JSplitPanes。
     * 
     */
    public class BasicVerticalLayoutManager extends
            BasicHorizontalLayoutManager
    {
        public BasicVerticalLayoutManager() {
            super(1);
        }
    }


    private class Handler implements FocusListener, PropertyChangeListener {
        //
        // PropertyChangeListener
        //
        /**
         * Messaged from the <code>JSplitPane</code> the receiver is
         * contained in.  May potentially reset the layout manager and cause a
         * <code>validate</code> to be sent.
         * <p>
         *  来自<code> JSplitPane </code>的消息包含在接收器中。可能重置布局管理器并导致发送<code>验证</code>。
         */
        public void propertyChange(PropertyChangeEvent e) {
            if(e.getSource() == splitPane) {
                String changeName = e.getPropertyName();

                if(changeName == JSplitPane.ORIENTATION_PROPERTY) {
                    orientation = splitPane.getOrientation();
                    resetLayoutManager();
                } else if(changeName == JSplitPane.CONTINUOUS_LAYOUT_PROPERTY){
                    setContinuousLayout(splitPane.isContinuousLayout());
                    if(!isContinuousLayout()) {
                        if(nonContinuousLayoutDivider == null) {
                            setNonContinuousLayoutDivider(
                                createDefaultNonContinuousLayoutDivider(),
                                true);
                        } else if(nonContinuousLayoutDivider.getParent() ==
                                  null) {
                            setNonContinuousLayoutDivider(
                                nonContinuousLayoutDivider,
                                true);
                        }
                    }
                } else if(changeName == JSplitPane.DIVIDER_SIZE_PROPERTY){
                    divider.setDividerSize(splitPane.getDividerSize());
                    dividerSize = divider.getDividerSize();
                    splitPane.revalidate();
                    splitPane.repaint();
                }
            }
        }

        //
        // FocusListener
        //
        public void focusGained(FocusEvent ev) {
            dividerKeyboardResize = true;
            splitPane.repaint();
        }

        public void focusLost(FocusEvent ev) {
            dividerKeyboardResize = false;
            splitPane.repaint();
        }
    }


    private static class Actions extends UIAction {
        private static final String NEGATIVE_INCREMENT = "negativeIncrement";
        private static final String POSITIVE_INCREMENT = "positiveIncrement";
        private static final String SELECT_MIN = "selectMin";
        private static final String SELECT_MAX = "selectMax";
        private static final String START_RESIZE = "startResize";
        private static final String TOGGLE_FOCUS = "toggleFocus";
        private static final String FOCUS_OUT_FORWARD = "focusOutForward";
        private static final String FOCUS_OUT_BACKWARD = "focusOutBackward";

        Actions(String key) {
            super(key);
        }

        public void actionPerformed(ActionEvent ev) {
            JSplitPane splitPane = (JSplitPane)ev.getSource();
            BasicSplitPaneUI ui = (BasicSplitPaneUI)BasicLookAndFeel.
                      getUIOfType(splitPane.getUI(), BasicSplitPaneUI.class);

            if (ui == null) {
                return;
            }
            String key = getName();
            if (key == NEGATIVE_INCREMENT) {
                if (ui.dividerKeyboardResize) {
                    splitPane.setDividerLocation(Math.max(
                              0, ui.getDividerLocation
                              (splitPane) - ui.getKeyboardMoveIncrement()));
                }
            }
            else if (key == POSITIVE_INCREMENT) {
                if (ui.dividerKeyboardResize) {
                    splitPane.setDividerLocation(
                        ui.getDividerLocation(splitPane) +
                        ui.getKeyboardMoveIncrement());
                }
            }
            else if (key == SELECT_MIN) {
                if (ui.dividerKeyboardResize) {
                    splitPane.setDividerLocation(0);
                }
            }
            else if (key == SELECT_MAX) {
                if (ui.dividerKeyboardResize) {
                    Insets   insets = splitPane.getInsets();
                    int      bottomI = (insets != null) ? insets.bottom : 0;
                    int      rightI = (insets != null) ? insets.right : 0;

                    if (ui.orientation == JSplitPane.VERTICAL_SPLIT) {
                        splitPane.setDividerLocation(splitPane.getHeight() -
                                                     bottomI);
                    }
                    else {
                        splitPane.setDividerLocation(splitPane.getWidth() -
                                                     rightI);
                    }
                }
            }
            else if (key == START_RESIZE) {
                if (!ui.dividerKeyboardResize) {
                    splitPane.requestFocus();
                } else {
                    JSplitPane parentSplitPane =
                        (JSplitPane)SwingUtilities.getAncestorOfClass(
                                         JSplitPane.class, splitPane);
                    if (parentSplitPane!=null) {
                        parentSplitPane.requestFocus();
                    }
                }
            }
            else if (key == TOGGLE_FOCUS) {
                toggleFocus(splitPane);
            }
            else if (key == FOCUS_OUT_FORWARD) {
                moveFocus(splitPane, 1);
            }
            else if (key == FOCUS_OUT_BACKWARD) {
                moveFocus(splitPane, -1);
            }
        }

        private void moveFocus(JSplitPane splitPane, int direction) {
            Container rootAncestor = splitPane.getFocusCycleRootAncestor();
            FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
            Component focusOn = (direction > 0) ?
                policy.getComponentAfter(rootAncestor, splitPane) :
                policy.getComponentBefore(rootAncestor, splitPane);
            HashSet<Component> focusFrom = new HashSet<Component>();
            if (splitPane.isAncestorOf(focusOn)) {
                do {
                    focusFrom.add(focusOn);
                    rootAncestor = focusOn.getFocusCycleRootAncestor();
                    policy = rootAncestor.getFocusTraversalPolicy();
                    focusOn = (direction > 0) ?
                        policy.getComponentAfter(rootAncestor, focusOn) :
                        policy.getComponentBefore(rootAncestor, focusOn);
                } while (splitPane.isAncestorOf(focusOn) &&
                         !focusFrom.contains(focusOn));
            }
            if ( focusOn!=null && !splitPane.isAncestorOf(focusOn) ) {
                focusOn.requestFocus();
            }
        }

        private void toggleFocus(JSplitPane splitPane) {
            Component left = splitPane.getLeftComponent();
            Component right = splitPane.getRightComponent();

            KeyboardFocusManager manager =
                KeyboardFocusManager.getCurrentKeyboardFocusManager();
            Component focus = manager.getFocusOwner();
            Component focusOn = getNextSide(splitPane, focus);
            if (focusOn != null) {
                // don't change the focus if the new focused component belongs
                // to the same splitpane and the same side
                if ( focus!=null &&
                     ( (SwingUtilities.isDescendingFrom(focus, left) &&
                        SwingUtilities.isDescendingFrom(focusOn, left)) ||
                       (SwingUtilities.isDescendingFrom(focus, right) &&
                        SwingUtilities.isDescendingFrom(focusOn, right)) ) ) {
                    return;
                }
                SwingUtilities2.compositeRequestFocus(focusOn);
            }
        }

        private Component getNextSide(JSplitPane splitPane, Component focus) {
            Component left = splitPane.getLeftComponent();
            Component right = splitPane.getRightComponent();
            Component next;
            if (focus!=null && SwingUtilities.isDescendingFrom(focus, left) &&
                right!=null) {
                next = getFirstAvailableComponent(right);
                if (next != null) {
                    return next;
                }
            }
            JSplitPane parentSplitPane = (JSplitPane)SwingUtilities.getAncestorOfClass(JSplitPane.class, splitPane);
            if (parentSplitPane!=null) {
                // focus next side of the parent split pane
                next = getNextSide(parentSplitPane, focus);
            } else {
                next = getFirstAvailableComponent(left);
                if (next == null) {
                    next = getFirstAvailableComponent(right);
                }
            }
            return next;
        }

        private Component getFirstAvailableComponent(Component c) {
            if (c!=null && c instanceof JSplitPane) {
                JSplitPane sp = (JSplitPane)c;
                Component left = getFirstAvailableComponent(sp.getLeftComponent());
                if (left != null) {
                    c = left;
                } else {
                    c = getFirstAvailableComponent(sp.getRightComponent());
                }
            }
            return c;
        }
    }
}
