/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.metal;

import sun.swing.SwingUtilities2;
import sun.awt.SunToolkit;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.util.Locale;
import javax.accessibility.*;


/**
 * Class that manages a JLF awt.Window-descendant class's title bar.
 * <p>
 * This class assumes it will be created with a particular window
 * decoration style, and that if the style changes, a new one will
 * be created.
 *
 * <p>
 *  管理JLF awt.Window后代类的标题栏的类。
 * <p>
 *  这个类假设它将使用特定的窗口装饰风格创建,如果风格更改,将创建一个新的。
 * 
 * 
 * @author Terry Kellerman
 * @since 1.4
 */
class MetalTitlePane extends JComponent {
    private static final Border handyEmptyBorder = new EmptyBorder(0,0,0,0);
    private static final int IMAGE_HEIGHT = 16;
    private static final int IMAGE_WIDTH = 16;

    /**
     * PropertyChangeListener added to the JRootPane.
     * <p>
     *  PropertyChangeListener添加到JRootPane。
     * 
     */
    private PropertyChangeListener propertyChangeListener;

    /**
     * JMenuBar, typically renders the system menu items.
     * <p>
     *  JMenuBar,通常呈现系统菜单项。
     * 
     */
    private JMenuBar menuBar;
    /**
     * Action used to close the Window.
     * <p>
     *  用于关闭窗口的动作。
     * 
     */
    private Action closeAction;

    /**
     * Action used to iconify the Frame.
     * <p>
     *  用于图标框架的操作。
     * 
     */
    private Action iconifyAction;

    /**
     * Action to restore the Frame size.
     * <p>
     *  恢复框架大小的操作。
     * 
     */
    private Action restoreAction;

    /**
     * Action to restore the Frame size.
     * <p>
     *  恢复框架大小的操作。
     * 
     */
    private Action maximizeAction;

    /**
     * Button used to maximize or restore the Frame.
     * <p>
     *  用于最大化或恢复框架的按钮。
     * 
     */
    private JButton toggleButton;

    /**
     * Button used to maximize or restore the Frame.
     * <p>
     *  用于最大化或恢复框架的按钮。
     * 
     */
    private JButton iconifyButton;

    /**
     * Button used to maximize or restore the Frame.
     * <p>
     *  用于最大化或恢复框架的按钮。
     * 
     */
    private JButton closeButton;

    /**
     * Icon used for toggleButton when window is normal size.
     * <p>
     *  当窗口为正常大小时用于toggleButton的图标。
     * 
     */
    private Icon maximizeIcon;

    /**
     * Icon used for toggleButton when window is maximized.
     * <p>
     *  当窗口最大化时用于toggleButton的图标。
     * 
     */
    private Icon minimizeIcon;

    /**
     * Image used for the system menu icon
     * <p>
     *  用于系统菜单图标的图像
     * 
     */
    private Image systemIcon;

    /**
     * Listens for changes in the state of the Window listener to update
     * the state of the widgets.
     * <p>
     *  侦听Window侦听器状态的更改以更新窗口小部件的状态。
     * 
     */
    private WindowListener windowListener;

    /**
     * Window we're currently in.
     * <p>
     *  我们目前在的窗口。
     * 
     */
    private Window window;

    /**
     * JRootPane rendering for.
     * <p>
     *  JRootPane渲染。
     * 
     */
    private JRootPane rootPane;

    /**
     * Room remaining in title for bumps.
     * <p>
     *  房间剩下的标题颠簸。
     * 
     */
    private int buttonsWidth;

    /**
     * Buffered Frame.state property. As state isn't bound, this is kept
     * to determine when to avoid updating widgets.
     * <p>
     *  缓冲Frame.state属性。因为状态没有绑定,所以保持以确定何时避免更新小部件。
     * 
     */
    private int state;

    /**
     * MetalRootPaneUI that created us.
     * <p>
     *  MetalRootPaneUI创建我们。
     * 
     */
    private MetalRootPaneUI rootPaneUI;


    // Colors
    private Color inactiveBackground = UIManager.getColor("inactiveCaption");
    private Color inactiveForeground = UIManager.getColor("inactiveCaptionText");
    private Color inactiveShadow = UIManager.getColor("inactiveCaptionBorder");
    private Color activeBumpsHighlight = MetalLookAndFeel.getPrimaryControlHighlight();
    private Color activeBumpsShadow = MetalLookAndFeel.getPrimaryControlDarkShadow();
    private Color activeBackground = null;
    private Color activeForeground = null;
    private Color activeShadow = null;

    // Bumps
    private MetalBumps activeBumps
        = new MetalBumps( 0, 0,
                          activeBumpsHighlight,
                          activeBumpsShadow,
                          MetalLookAndFeel.getPrimaryControl() );
    private MetalBumps inactiveBumps
        = new MetalBumps( 0, 0,
                          MetalLookAndFeel.getControlHighlight(),
                          MetalLookAndFeel.getControlDarkShadow(),
                          MetalLookAndFeel.getControl() );


    public MetalTitlePane(JRootPane root, MetalRootPaneUI ui) {
        this.rootPane = root;
        rootPaneUI = ui;

        state = -1;

        installSubcomponents();
        determineColors();
        installDefaults();

        setLayout(createLayout());
    }

    /**
     * Uninstalls the necessary state.
     * <p>
     *  卸载必要的状态。
     * 
     */
    private void uninstall() {
        uninstallListeners();
        window = null;
        removeAll();
    }

    /**
     * Installs the necessary listeners.
     * <p>
     *  安装必要的侦听器。
     * 
     */
    private void installListeners() {
        if (window != null) {
            windowListener = createWindowListener();
            window.addWindowListener(windowListener);
            propertyChangeListener = createWindowPropertyChangeListener();
            window.addPropertyChangeListener(propertyChangeListener);
        }
    }

    /**
     * Uninstalls the necessary listeners.
     * <p>
     *  卸载必需的侦听器。
     * 
     */
    private void uninstallListeners() {
        if (window != null) {
            window.removeWindowListener(windowListener);
            window.removePropertyChangeListener(propertyChangeListener);
        }
    }

    /**
     * Returns the <code>WindowListener</code> to add to the
     * <code>Window</code>.
     * <p>
     *  返回<code> WindowListener </code>以添加到<code> Window </code>。
     * 
     */
    private WindowListener createWindowListener() {
        return new WindowHandler();
    }

    /**
     * Returns the <code>PropertyChangeListener</code> to install on
     * the <code>Window</code>.
     * <p>
     *  返回<code> PropertyChangeListener </code>以安装在<code> Window </code>上。
     * 
     */
    private PropertyChangeListener createWindowPropertyChangeListener() {
        return new PropertyChangeHandler();
    }

    /**
     * Returns the <code>JRootPane</code> this was created for.
     * <p>
     * 返回此代码的创建代码<code> JRootPane </code>。
     * 
     */
    public JRootPane getRootPane() {
        return rootPane;
    }

    /**
     * Returns the decoration style of the <code>JRootPane</code>.
     * <p>
     *  返回<code> JRootPane </code>的装饰样式。
     * 
     */
    private int getWindowDecorationStyle() {
        return getRootPane().getWindowDecorationStyle();
    }

    public void addNotify() {
        super.addNotify();

        uninstallListeners();

        window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            if (window instanceof Frame) {
                setState(((Frame)window).getExtendedState());
            }
            else {
                setState(0);
            }
            setActive(window.isActive());
            installListeners();
            updateSystemIcon();
        }
    }

    public void removeNotify() {
        super.removeNotify();

        uninstallListeners();
        window = null;
    }

    /**
     * Adds any sub-Components contained in the <code>MetalTitlePane</code>.
     * <p>
     *  添加<code> MetalTitlePane </code>中包含的任何子组件。
     * 
     */
    private void installSubcomponents() {
        int decorationStyle = getWindowDecorationStyle();
        if (decorationStyle == JRootPane.FRAME) {
            createActions();
            menuBar = createMenuBar();
            add(menuBar);
            createButtons();
            add(iconifyButton);
            add(toggleButton);
            add(closeButton);
        } else if (decorationStyle == JRootPane.PLAIN_DIALOG ||
                decorationStyle == JRootPane.INFORMATION_DIALOG ||
                decorationStyle == JRootPane.ERROR_DIALOG ||
                decorationStyle == JRootPane.COLOR_CHOOSER_DIALOG ||
                decorationStyle == JRootPane.FILE_CHOOSER_DIALOG ||
                decorationStyle == JRootPane.QUESTION_DIALOG ||
                decorationStyle == JRootPane.WARNING_DIALOG) {
            createActions();
            createButtons();
            add(closeButton);
        }
    }

    /**
     * Determines the Colors to draw with.
     * <p>
     *  确定要绘制的颜色。
     * 
     */
    private void determineColors() {
        switch (getWindowDecorationStyle()) {
        case JRootPane.FRAME:
            activeBackground = UIManager.getColor("activeCaption");
            activeForeground = UIManager.getColor("activeCaptionText");
            activeShadow = UIManager.getColor("activeCaptionBorder");
            break;
        case JRootPane.ERROR_DIALOG:
            activeBackground = UIManager.getColor(
                "OptionPane.errorDialog.titlePane.background");
            activeForeground = UIManager.getColor(
                "OptionPane.errorDialog.titlePane.foreground");
            activeShadow = UIManager.getColor(
                "OptionPane.errorDialog.titlePane.shadow");
            break;
        case JRootPane.QUESTION_DIALOG:
        case JRootPane.COLOR_CHOOSER_DIALOG:
        case JRootPane.FILE_CHOOSER_DIALOG:
            activeBackground = UIManager.getColor(
                "OptionPane.questionDialog.titlePane.background");
            activeForeground = UIManager.getColor(
                "OptionPane.questionDialog.titlePane.foreground");
            activeShadow = UIManager.getColor(
                "OptionPane.questionDialog.titlePane.shadow");
            break;
        case JRootPane.WARNING_DIALOG:
            activeBackground = UIManager.getColor(
                "OptionPane.warningDialog.titlePane.background");
            activeForeground = UIManager.getColor(
                "OptionPane.warningDialog.titlePane.foreground");
            activeShadow = UIManager.getColor(
                "OptionPane.warningDialog.titlePane.shadow");
            break;
        case JRootPane.PLAIN_DIALOG:
        case JRootPane.INFORMATION_DIALOG:
        default:
            activeBackground = UIManager.getColor("activeCaption");
            activeForeground = UIManager.getColor("activeCaptionText");
            activeShadow = UIManager.getColor("activeCaptionBorder");
            break;
        }
        activeBumps.setBumpColors(activeBumpsHighlight, activeBumpsShadow,
                                  activeBackground);
    }

    /**
     * Installs the fonts and necessary properties on the MetalTitlePane.
     * <p>
     *  在MetalTitlePane上安装字体和必要的属性。
     * 
     */
    private void installDefaults() {
        setFont(UIManager.getFont("InternalFrame.titleFont", getLocale()));
    }

    /**
     * Uninstalls any previously installed UI values.
     * <p>
     *  卸载任何以前安装的UI值。
     * 
     */
    private void uninstallDefaults() {
    }

    /**
     * Returns the <code>JMenuBar</code> displaying the appropriate
     * system menu items.
     * <p>
     *  返回显示相应系统菜单项的<code> JMenuBar </code>。
     * 
     */
    protected JMenuBar createMenuBar() {
        menuBar = new SystemMenuBar();
        menuBar.setFocusable(false);
        menuBar.setBorderPainted(true);
        menuBar.add(createMenu());
        return menuBar;
    }

    /**
     * Closes the Window.
     * <p>
     *  关闭窗口。
     * 
     */
    private void close() {
        Window window = getWindow();

        if (window != null) {
            window.dispatchEvent(new WindowEvent(
                                 window, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Iconifies the Frame.
     * <p>
     *  图标框架。
     * 
     */
    private void iconify() {
        Frame frame = getFrame();
        if (frame != null) {
            frame.setExtendedState(state | Frame.ICONIFIED);
        }
    }

    /**
     * Maximizes the Frame.
     * <p>
     *  最大化框架。
     * 
     */
    private void maximize() {
        Frame frame = getFrame();
        if (frame != null) {
            frame.setExtendedState(state | Frame.MAXIMIZED_BOTH);
        }
    }

    /**
     * Restores the Frame size.
     * <p>
     *  恢复帧大小。
     * 
     */
    private void restore() {
        Frame frame = getFrame();

        if (frame == null) {
            return;
        }

        if ((state & Frame.ICONIFIED) != 0) {
            frame.setExtendedState(state & ~Frame.ICONIFIED);
        } else {
            frame.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
        }
    }

    /**
     * Create the <code>Action</code>s that get associated with the
     * buttons and menu items.
     * <p>
     *  创建与按钮和菜单项相关联的<code> Action </code>。
     * 
     */
    private void createActions() {
        closeAction = new CloseAction();
        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            iconifyAction = new IconifyAction();
            restoreAction = new RestoreAction();
            maximizeAction = new MaximizeAction();
        }
    }

    /**
     * Returns the <code>JMenu</code> displaying the appropriate menu items
     * for manipulating the Frame.
     * <p>
     *  返回显示用于操作框架的相应菜单项的<code> JMenu </code>。
     * 
     */
    private JMenu createMenu() {
        JMenu menu = new JMenu("");
        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            addMenuItems(menu);
        }
        return menu;
    }

    /**
     * Adds the necessary <code>JMenuItem</code>s to the passed in menu.
     * <p>
     *  将所需的<code> JMenuItem </code>添加到传入的菜单中。
     * 
     */
    private void addMenuItems(JMenu menu) {
        Locale locale = getRootPane().getLocale();
        JMenuItem mi = menu.add(restoreAction);
        int mnemonic = MetalUtils.getInt("MetalTitlePane.restoreMnemonic", -1);

        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }

        mi = menu.add(iconifyAction);
        mnemonic = MetalUtils.getInt("MetalTitlePane.iconifyMnemonic", -1);
        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }

        if (Toolkit.getDefaultToolkit().isFrameStateSupported(
                Frame.MAXIMIZED_BOTH)) {
            mi = menu.add(maximizeAction);
            mnemonic =
                MetalUtils.getInt("MetalTitlePane.maximizeMnemonic", -1);
            if (mnemonic != -1) {
                mi.setMnemonic(mnemonic);
            }
        }

        menu.add(new JSeparator());

        mi = menu.add(closeAction);
        mnemonic = MetalUtils.getInt("MetalTitlePane.closeMnemonic", -1);
        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }
    }

    /**
     * Returns a <code>JButton</code> appropriate for placement on the
     * TitlePane.
     * <p>
     *  返回适用于在TitlePane上放置的<code> JButton </code>。
     * 
     */
    private JButton createTitleButton() {
        JButton button = new JButton();

        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setOpaque(true);
        return button;
    }

    /**
     * Creates the Buttons that will be placed on the TitlePane.
     * <p>
     *  创建将放置在TitlePane上的按钮。
     * 
     */
    private void createButtons() {
        closeButton = createTitleButton();
        closeButton.setAction(closeAction);
        closeButton.setText(null);
        closeButton.putClientProperty("paintActive", Boolean.TRUE);
        closeButton.setBorder(handyEmptyBorder);
        closeButton.putClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
                                      "Close");
        closeButton.setIcon(UIManager.getIcon("InternalFrame.closeIcon"));

        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            maximizeIcon = UIManager.getIcon("InternalFrame.maximizeIcon");
            minimizeIcon = UIManager.getIcon("InternalFrame.minimizeIcon");

            iconifyButton = createTitleButton();
            iconifyButton.setAction(iconifyAction);
            iconifyButton.setText(null);
            iconifyButton.putClientProperty("paintActive", Boolean.TRUE);
            iconifyButton.setBorder(handyEmptyBorder);
            iconifyButton.putClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
                                            "Iconify");
            iconifyButton.setIcon(UIManager.getIcon("InternalFrame.iconifyIcon"));

            toggleButton = createTitleButton();
            toggleButton.setAction(restoreAction);
            toggleButton.putClientProperty("paintActive", Boolean.TRUE);
            toggleButton.setBorder(handyEmptyBorder);
            toggleButton.putClientProperty(AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
                                           "Maximize");
            toggleButton.setIcon(maximizeIcon);
        }
    }

    /**
     * Returns the <code>LayoutManager</code> that should be installed on
     * the <code>MetalTitlePane</code>.
     * <p>
     *  返回应该安装在<code> MetalTitlePane </code>上的<code> LayoutManager </code>。
     * 
     */
    private LayoutManager createLayout() {
        return new TitlePaneLayout();
    }

    /**
     * Updates state dependant upon the Window's active state.
     * <p>
     *  更新状态取决于窗口的活动状态。
     * 
     */
    private void setActive(boolean isActive) {
        Boolean activeB = isActive ? Boolean.TRUE : Boolean.FALSE;

        closeButton.putClientProperty("paintActive", activeB);
        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            iconifyButton.putClientProperty("paintActive", activeB);
            toggleButton.putClientProperty("paintActive", activeB);
        }
        // Repaint the whole thing as the Borders that are used have
        // different colors for active vs inactive
        getRootPane().repaint();
    }

    /**
     * Sets the state of the Window.
     * <p>
     *  设置窗口的状态。
     * 
     */
    private void setState(int state) {
        setState(state, false);
    }

    /**
     * Sets the state of the window. If <code>updateRegardless</code> is
     * true and the state has not changed, this will update anyway.
     * <p>
     *  设置窗口的状态。如果<code> updateRegardless </code>为true,且状态未更改,则无论如何都会更新。
     * 
     */
    private void setState(int state, boolean updateRegardless) {
        Window w = getWindow();

        if (w != null && getWindowDecorationStyle() == JRootPane.FRAME) {
            if (this.state == state && !updateRegardless) {
                return;
            }
            Frame frame = getFrame();

            if (frame != null) {
                JRootPane rootPane = getRootPane();

                if (((state & Frame.MAXIMIZED_BOTH) != 0) &&
                        (rootPane.getBorder() == null ||
                        (rootPane.getBorder() instanceof UIResource)) &&
                            frame.isShowing()) {
                    rootPane.setBorder(null);
                }
                else if ((state & Frame.MAXIMIZED_BOTH) == 0) {
                    // This is a croak, if state becomes bound, this can
                    // be nuked.
                    rootPaneUI.installBorder(rootPane);
                }
                if (frame.isResizable()) {
                    if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                        updateToggleButton(restoreAction, minimizeIcon);
                        maximizeAction.setEnabled(false);
                        restoreAction.setEnabled(true);
                    }
                    else {
                        updateToggleButton(maximizeAction, maximizeIcon);
                        maximizeAction.setEnabled(true);
                        restoreAction.setEnabled(false);
                    }
                    if (toggleButton.getParent() == null ||
                        iconifyButton.getParent() == null) {
                        add(toggleButton);
                        add(iconifyButton);
                        revalidate();
                        repaint();
                    }
                    toggleButton.setText(null);
                }
                else {
                    maximizeAction.setEnabled(false);
                    restoreAction.setEnabled(false);
                    if (toggleButton.getParent() != null) {
                        remove(toggleButton);
                        revalidate();
                        repaint();
                    }
                }
            }
            else {
                // Not contained in a Frame
                maximizeAction.setEnabled(false);
                restoreAction.setEnabled(false);
                iconifyAction.setEnabled(false);
                remove(toggleButton);
                remove(iconifyButton);
                revalidate();
                repaint();
            }
            closeAction.setEnabled(true);
            this.state = state;
        }
    }

    /**
     * Updates the toggle button to contain the Icon <code>icon</code>, and
     * Action <code>action</code>.
     * <p>
     *  更新切换按钮以包含Icon <code>图标</code>和Action <code> action </code>。
     * 
     */
    private void updateToggleButton(Action action, Icon icon) {
        toggleButton.setAction(action);
        toggleButton.setIcon(icon);
        toggleButton.setText(null);
    }

    /**
     * Returns the Frame rendering in. This will return null if the
     * <code>JRootPane</code> is not contained in a <code>Frame</code>.
     * <p>
     *  返回帧渲染。如果<code> JRootPane </code>未包含在<code> Frame </code>中,则将返回null。
     * 
     */
    private Frame getFrame() {
        Window window = getWindow();

        if (window instanceof Frame) {
            return (Frame)window;
        }
        return null;
    }

    /**
     * Returns the <code>Window</code> the <code>JRootPane</code> is
     * contained in. This will return null if there is no parent ancestor
     * of the <code>JRootPane</code>.
     * <p>
     * 返回<code> Window </code> <code> JRootPane </code>包含在中。如果没有<code> JRootPane </code>的父祖先,它将返回null。
     * 
     */
    private Window getWindow() {
        return window;
    }

    /**
     * Returns the String to display as the title.
     * <p>
     *  返回要显示为标题的字符串。
     * 
     */
    private String getTitle() {
        Window w = getWindow();

        if (w instanceof Frame) {
            return ((Frame)w).getTitle();
        }
        else if (w instanceof Dialog) {
            return ((Dialog)w).getTitle();
        }
        return null;
    }

    /**
     * Renders the TitlePane.
     * <p>
     *  渲染TitlePane。
     * 
     */
    public void paintComponent(Graphics g)  {
        // As state isn't bound, we need a convenience place to check
        // if it has changed. Changing the state typically changes the
        if (getFrame() != null) {
            setState(getFrame().getExtendedState());
        }
        JRootPane rootPane = getRootPane();
        Window window = getWindow();
        boolean leftToRight = (window == null) ?
                               rootPane.getComponentOrientation().isLeftToRight() :
                               window.getComponentOrientation().isLeftToRight();
        boolean isSelected = (window == null) ? true : window.isActive();
        int width = getWidth();
        int height = getHeight();

        Color background;
        Color foreground;
        Color darkShadow;

        MetalBumps bumps;

        if (isSelected) {
            background = activeBackground;
            foreground = activeForeground;
            darkShadow = activeShadow;
            bumps = activeBumps;
        } else {
            background = inactiveBackground;
            foreground = inactiveForeground;
            darkShadow = inactiveShadow;
            bumps = inactiveBumps;
        }

        g.setColor(background);
        g.fillRect(0, 0, width, height);

        g.setColor( darkShadow );
        g.drawLine ( 0, height - 1, width, height -1);
        g.drawLine ( 0, 0, 0 ,0);
        g.drawLine ( width - 1, 0 , width -1, 0);

        int xOffset = leftToRight ? 5 : width - 5;

        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            xOffset += leftToRight ? IMAGE_WIDTH + 5 : - IMAGE_WIDTH - 5;
        }

        String theTitle = getTitle();
        if (theTitle != null) {
            FontMetrics fm = SwingUtilities2.getFontMetrics(rootPane, g);

            g.setColor(foreground);

            int yOffset = ( (height - fm.getHeight() ) / 2 ) + fm.getAscent();

            Rectangle rect = new Rectangle(0, 0, 0, 0);
            if (iconifyButton != null && iconifyButton.getParent() != null) {
                rect = iconifyButton.getBounds();
            }
            int titleW;

            if( leftToRight ) {
                if (rect.x == 0) {
                    rect.x = window.getWidth() - window.getInsets().right-2;
                }
                titleW = rect.x - xOffset - 4;
                theTitle = SwingUtilities2.clipStringIfNecessary(
                                rootPane, fm, theTitle, titleW);
            } else {
                titleW = xOffset - rect.x - rect.width - 4;
                theTitle = SwingUtilities2.clipStringIfNecessary(
                                rootPane, fm, theTitle, titleW);
                xOffset -= SwingUtilities2.stringWidth(rootPane, fm,
                                                       theTitle);
            }
            int titleLength = SwingUtilities2.stringWidth(rootPane, fm,
                                                          theTitle);
            SwingUtilities2.drawString(rootPane, g, theTitle, xOffset,
                                       yOffset );
            xOffset += leftToRight ? titleLength + 5  : -5;
        }

        int bumpXOffset;
        int bumpLength;
        if( leftToRight ) {
            bumpLength = width - buttonsWidth - xOffset - 5;
            bumpXOffset = xOffset;
        } else {
            bumpLength = xOffset - buttonsWidth - 5;
            bumpXOffset = buttonsWidth + 5;
        }
        int bumpYOffset = 3;
        int bumpHeight = getHeight() - (2 * bumpYOffset);
        bumps.setBumpArea( bumpLength, bumpHeight );
        bumps.paintIcon(this, g, bumpXOffset, bumpYOffset);
    }

    /**
     * Actions used to <code>close</code> the <code>Window</code>.
     * <p>
     *  用于<code>关闭</code> <code>窗口</code>的操作。
     * 
     */
    private class CloseAction extends AbstractAction {
        public CloseAction() {
            super(UIManager.getString("MetalTitlePane.closeTitle",
                                      getLocale()));
        }

        public void actionPerformed(ActionEvent e) {
            close();
        }
    }


    /**
     * Actions used to <code>iconfiy</code> the <code>Frame</code>.
     * <p>
     *  用于<code> iconfiy </code> <code>框架</code>的操作。
     * 
     */
    private class IconifyAction extends AbstractAction {
        public IconifyAction() {
            super(UIManager.getString("MetalTitlePane.iconifyTitle",
                                      getLocale()));
        }

        public void actionPerformed(ActionEvent e) {
            iconify();
        }
    }


    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     * <p>
     *  用于<code>恢复</code> <code> Frame </code>的操作。
     * 
     */
    private class RestoreAction extends AbstractAction {
        public RestoreAction() {
            super(UIManager.getString
                  ("MetalTitlePane.restoreTitle", getLocale()));
        }

        public void actionPerformed(ActionEvent e) {
            restore();
        }
    }


    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     * <p>
     *  用于<code>恢复</code> <code> Frame </code>的操作。
     * 
     */
    private class MaximizeAction extends AbstractAction {
        public MaximizeAction() {
            super(UIManager.getString("MetalTitlePane.maximizeTitle",
                                      getLocale()));
        }

        public void actionPerformed(ActionEvent e) {
            maximize();
        }
    }


    /**
     * Class responsible for drawing the system menu. Looks up the
     * image to draw from the Frame associated with the
     * <code>JRootPane</code>.
     * <p>
     *  负责绘制系统菜单的类。查找从与<code> JRootPane </code>相关联的框架绘制的图像。
     * 
     */
    private class SystemMenuBar extends JMenuBar {
        public void paint(Graphics g) {
            if (isOpaque()) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            if (systemIcon != null) {
                g.drawImage(systemIcon, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            } else {
                Icon icon = UIManager.getIcon("InternalFrame.icon");

                if (icon != null) {
                    icon.paintIcon(this, g, 0, 0);
                }
            }
        }
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();

            return new Dimension(Math.max(IMAGE_WIDTH, size.width),
                                 Math.max(size.height, IMAGE_HEIGHT));
        }
    }

    private class TitlePaneLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component c) {}
        public void removeLayoutComponent(Component c) {}
        public Dimension preferredLayoutSize(Container c)  {
            int height = computeHeight();
            return new Dimension(height, height);
        }

        public Dimension minimumLayoutSize(Container c) {
            return preferredLayoutSize(c);
        }

        private int computeHeight() {
            FontMetrics fm = rootPane.getFontMetrics(getFont());
            int fontHeight = fm.getHeight();
            fontHeight += 7;
            int iconHeight = 0;
            if (getWindowDecorationStyle() == JRootPane.FRAME) {
                iconHeight = IMAGE_HEIGHT;
            }

            int finalHeight = Math.max( fontHeight, iconHeight );
            return finalHeight;
        }

        public void layoutContainer(Container c) {
            boolean leftToRight = (window == null) ?
                getRootPane().getComponentOrientation().isLeftToRight() :
                window.getComponentOrientation().isLeftToRight();

            int w = getWidth();
            int x;
            int y = 3;
            int spacing;
            int buttonHeight;
            int buttonWidth;

            if (closeButton != null && closeButton.getIcon() != null) {
                buttonHeight = closeButton.getIcon().getIconHeight();
                buttonWidth = closeButton.getIcon().getIconWidth();
            }
            else {
                buttonHeight = IMAGE_HEIGHT;
                buttonWidth = IMAGE_WIDTH;
            }

            // assumes all buttons have the same dimensions
            // these dimensions include the borders

            x = leftToRight ? w : 0;

            spacing = 5;
            x = leftToRight ? spacing : w - buttonWidth - spacing;
            if (menuBar != null) {
                menuBar.setBounds(x, y, buttonWidth, buttonHeight);
            }

            x = leftToRight ? w : 0;
            spacing = 4;
            x += leftToRight ? -spacing -buttonWidth : spacing;
            if (closeButton != null) {
                closeButton.setBounds(x, y, buttonWidth, buttonHeight);
            }

            if( !leftToRight ) x += buttonWidth;

            if (getWindowDecorationStyle() == JRootPane.FRAME) {
                if (Toolkit.getDefaultToolkit().isFrameStateSupported(
                        Frame.MAXIMIZED_BOTH)) {
                    if (toggleButton.getParent() != null) {
                        spacing = 10;
                        x += leftToRight ? -spacing -buttonWidth : spacing;
                        toggleButton.setBounds(x, y, buttonWidth, buttonHeight);
                        if (!leftToRight) {
                            x += buttonWidth;
                        }
                    }
                }

                if (iconifyButton != null && iconifyButton.getParent() != null) {
                    spacing = 2;
                    x += leftToRight ? -spacing -buttonWidth : spacing;
                    iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);
                    if (!leftToRight) {
                        x += buttonWidth;
                    }
                }
            }
            buttonsWidth = leftToRight ? w - x : x;
        }
    }



    /**
     * PropertyChangeListener installed on the Window. Updates the necessary
     * state as the state of the Window changes.
     * <p>
     *  PropertyChangeListener安装在Window上。随着窗口状态的更改,更新必要的状态。
     * 
     */
    private class PropertyChangeHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent pce) {
            String name = pce.getPropertyName();

            // Frame.state isn't currently bound.
            if ("resizable".equals(name) || "state".equals(name)) {
                Frame frame = getFrame();

                if (frame != null) {
                    setState(frame.getExtendedState(), true);
                }
                if ("resizable".equals(name)) {
                    getRootPane().repaint();
                }
            }
            else if ("title".equals(name)) {
                repaint();
            }
            else if ("componentOrientation" == name) {
                revalidate();
                repaint();
            }
            else if ("iconImage" == name) {
                updateSystemIcon();
                revalidate();
                repaint();
            }
        }
    }

    /**
     * Update the image used for the system icon
     * <p>
     *  更新用于系统图标的图像
     * 
     */
    private void updateSystemIcon() {
        Window window = getWindow();
        if (window == null) {
            systemIcon = null;
            return;
        }
        java.util.List<Image> icons = window.getIconImages();
        assert icons != null;

        if (icons.size() == 0) {
            systemIcon = null;
        }
        else if (icons.size() == 1) {
            systemIcon = icons.get(0);
        }
        else {
            systemIcon = SunToolkit.getScaledIconImage(icons,
                                                       IMAGE_WIDTH,
                                                       IMAGE_HEIGHT);
        }
    }


    /**
     * WindowListener installed on the Window, updates the state as necessary.
     * <p>
     *  WindowListener安装在Window上,根据需要更新状态。
     */
    private class WindowHandler extends WindowAdapter {
        public void windowActivated(WindowEvent ev) {
            setActive(true);
        }

        public void windowDeactivated(WindowEvent ev) {
            setActive(false);
        }
    }
}
