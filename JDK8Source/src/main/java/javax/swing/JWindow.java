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
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.Vector;
import java.io.Serializable;

import javax.accessibility.*;

/**
 * A <code>JWindow</code> is a container that can be displayed anywhere on the
 * user's desktop. It does not have the title bar, window-management buttons,
 * or other trimmings associated with a <code>JFrame</code>, but it is still a
 * "first-class citizen" of the user's desktop, and can exist anywhere
 * on it.
 * <p>
 * The <code>JWindow</code> component contains a <code>JRootPane</code>
 * as its only child.  The <code>contentPane</code> should be the parent
 * of any children of the <code>JWindow</code>.
 * As a convenience, the {@code add}, {@code remove}, and {@code setLayout}
 * methods of this class are overridden, so that they delegate calls
 * to the corresponding methods of the {@code ContentPane}.
 * For example, you can add a child component to a window as follows:
 * <pre>
 *       window.add(child);
 * </pre>
 * And the child will be added to the contentPane.
 * The <code>contentPane</code> will always be non-<code>null</code>.
 * Attempting to set it to <code>null</code> will cause the <code>JWindow</code>
 * to throw an exception. The default <code>contentPane</code> will have a
 * <code>BorderLayout</code> manager set on it.
 * Refer to {@link javax.swing.RootPaneContainer}
 * for details on adding, removing and setting the <code>LayoutManager</code>
 * of a <code>JWindow</code>.
 * <p>
 * Please see the {@link JRootPane} documentation for a complete description of
 * the <code>contentPane</code>, <code>glassPane</code>, and
 * <code>layeredPane</code> components.
 * <p>
 * In a multi-screen environment, you can create a <code>JWindow</code>
 * on a different screen device.  See {@link java.awt.Window} for more
 * information.
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
 * <p>
 *  <code> JWindow </code>是一个容器,可以显示在用户桌面上的任何地方。
 * 它没有与<code> JFrame </code>相关联的标题栏,窗口管理按钮或其他修剪,但它仍然是用户桌面的"一流公民",可以存在于任何地方。
 * <p>
 *  <code> JWindow </code>组件包含一个<code> JRootPane </code>作为其唯一的孩子。
 *  <code> contentPane </code>应该是<code> JWindow </code>的任何子级的父级。
 * 为方便起见,此类的{@code add},{@code remove}和{@code setLayout}方法被覆盖,因此它们将调用委派给{@code ContentPane}的相应方法。
 * 例如,您可以将子组件添加到窗口,如下所示：。
 * <pre>
 *  window.add(child);
 * </pre>
 *  并且孩子将被添加到contentPane。 <code> contentPane </code>将始终为非<code> null </code>。
 * 尝试将其设置为<code> null </code>会导致<code> JWindow </code>抛出异常。
 * 默认的<code> contentPane </code>会有一个<code> BorderLayout </code>管理器。
 * 有关添加,删除和设置<code> JWindow </code>的<code> LayoutManager </code>的详细信息,请参阅{@link javax.swing.RootPaneContainer}
 * 。
 * 默认的<code> contentPane </code>会有一个<code> BorderLayout </code>管理器。
 * <p>
 * 有关<code> contentPane </code>,<code> glassPane </code>和<code> layeredPane </code>组件的完整说明,请参阅{@link JRootPane}
 * 文档。
 * <p>
 *  在多屏幕环境中,您可以在其他屏幕设备上创建<code> JWindow </code>。有关详细信息,请参阅{@link java.awt.Window}。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JRootPane
 *
 * @beaninfo
 *      attribute: isContainer true
 *      attribute: containerDelegate getContentPane
 *    description: A toplevel window which has no system border or controls.
 *
 * @author David Kloba
 */
@SuppressWarnings("serial")
public class JWindow extends Window implements Accessible,
                                               RootPaneContainer,
                               TransferHandler.HasGetTransferHandler
{
    /**
     * The <code>JRootPane</code> instance that manages the
     * <code>contentPane</code>
     * and optional <code>menuBar</code> for this frame, as well as the
     * <code>glassPane</code>.
     *
     * <p>
     *  管理此框架的<code> contentPane </code>和可选<code> menuBar </code>以及<code> glassPane </code>的<code> JRootPane
     *  </code>实例。
     * 
     * 
     * @see #getRootPane
     * @see #setRootPane
     */
    protected JRootPane rootPane;

    /**
     * If true then calls to <code>add</code> and <code>setLayout</code>
     * will be forwarded to the <code>contentPane</code>. This is initially
     * false, but is set to true when the <code>JWindow</code> is constructed.
     *
     * <p>
     *  如果为true,那么对<code> add </code>和<code> setLayout </code>的调用将被转发到<code> contentPane </code>。
     * 这最初是false,但在构建<code> JWindow </code>时设置为true。
     * 
     * 
     * @see #isRootPaneCheckingEnabled
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean rootPaneCheckingEnabled = false;

    /**
     * The <code>TransferHandler</code> for this window.
     * <p>
     *  此窗口的<code> TransferHandler </code>。
     * 
     */
    private TransferHandler transferHandler;

    /**
     * Creates a window with no specified owner. This window will not be
     * focusable.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  创建没有指定所有者的窗口。此窗口将不可对焦。
     * <p>
     * 此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @throws HeadlessException if
     *         <code>GraphicsEnvironment.isHeadless()</code> returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isFocusableWindow
     * @see JComponent#getDefaultLocale
     */
    public JWindow() {
        this((Frame)null);
    }

    /**
     * Creates a window with the specified <code>GraphicsConfiguration</code>
     * of a screen device. This window will not be focusable.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  使用指定的屏幕设备的<code> GraphicsConfiguration </code>创建一个窗口。此窗口将不可对焦。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param gc the <code>GraphicsConfiguration</code> that is used
     *          to construct the new window with; if gc is <code>null</code>,
     *          the system default <code>GraphicsConfiguration</code>
     *          is assumed
     * @throws HeadlessException If
     *         <code>GraphicsEnvironment.isHeadless()</code> returns true.
     * @throws IllegalArgumentException if <code>gc</code> is not from
     *         a screen device.
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isFocusableWindow
     * @see JComponent#getDefaultLocale
     *
     * @since  1.3
     */
    public JWindow(GraphicsConfiguration gc) {
        this(null, gc);
        super.setFocusableWindowState(false);
    }

    /**
     * Creates a window with the specified owner frame.
     * If <code>owner</code> is <code>null</code>, the shared owner
     * will be used and this window will not be focusable. Also,
     * this window will not be focusable unless its owner is showing
     * on the screen.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  创建具有指定所有者框架的窗口。如果<code>所有者</code>为<code> null </code>,则将使用共享所有者,并且此窗口将无法聚焦。
     * 此外,此窗口将不可焦点,除非其所有者显示在屏幕上。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param owner the frame from which the window is displayed
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isFocusableWindow
     * @see JComponent#getDefaultLocale
     */
    public JWindow(Frame owner) {
        super(owner == null? SwingUtilities.getSharedOwnerFrame() : owner);
        if (owner == null) {
            WindowListener ownerShutdownListener =
                    SwingUtilities.getSharedOwnerFrameShutdownListener();
            addWindowListener(ownerShutdownListener);
        }
        windowInit();
    }

    /**
     * Creates a window with the specified owner window. This window
     * will not be focusable unless its owner is showing on the screen.
     * If <code>owner</code> is <code>null</code>, the shared owner
     * will be used and this window will not be focusable.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  创建具有指定所有者窗口的窗口。除非其所有者显示在屏幕上,否则此窗口将无法对焦。如果<code>所有者</code>为<code> null </code>,则将使用共享所有者,并且此窗口将无法聚焦。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param owner the window from which the window is displayed
     * @throws HeadlessException if
     *         <code>GraphicsEnvironment.isHeadless()</code> returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isFocusableWindow
     * @see JComponent#getDefaultLocale
     */
    public JWindow(Window owner) {
        super(owner == null ? (Window)SwingUtilities.getSharedOwnerFrame() :
              owner);
        if (owner == null) {
            WindowListener ownerShutdownListener =
                    SwingUtilities.getSharedOwnerFrameShutdownListener();
            addWindowListener(ownerShutdownListener);
        }
        windowInit();
    }

    /**
     * Creates a window with the specified owner window and
     * <code>GraphicsConfiguration</code> of a screen device. If
     * <code>owner</code> is <code>null</code>, the shared owner will be used
     * and this window will not be focusable.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  创建具有指定的所有者窗口和屏幕设备的<code> GraphicsConfiguration </code>的窗口。
     * 如果<code>所有者</code>为<code> null </code>,则将使用共享所有者,并且此窗口将无法聚焦。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param owner the window from which the window is displayed
     * @param gc the <code>GraphicsConfiguration</code> that is used
     *          to construct the new window with; if gc is <code>null</code>,
     *          the system default <code>GraphicsConfiguration</code>
     *          is assumed, unless <code>owner</code> is also null, in which
     *          case the <code>GraphicsConfiguration</code> from the
     *          shared owner frame will be used.
     * @throws HeadlessException if
     *         <code>GraphicsEnvironment.isHeadless()</code> returns true.
     * @throws IllegalArgumentException if <code>gc</code> is not from
     *         a screen device.
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isFocusableWindow
     * @see JComponent#getDefaultLocale
     *
     * @since  1.3
     */
    public JWindow(Window owner, GraphicsConfiguration gc) {
        super(owner == null ? (Window)SwingUtilities.getSharedOwnerFrame() :
              owner, gc);
        if (owner == null) {
            WindowListener ownerShutdownListener =
                    SwingUtilities.getSharedOwnerFrameShutdownListener();
            addWindowListener(ownerShutdownListener);
        }
        windowInit();
    }

    /**
     * Called by the constructors to init the <code>JWindow</code> properly.
     * <p>
     * 由构造函数调用以正确初始化<code> JWindow </code>。
     * 
     */
    protected void windowInit() {
        setLocale( JComponent.getDefaultLocale() );
        setRootPane(createRootPane());
        setRootPaneCheckingEnabled(true);
        sun.awt.SunToolkit.checkAndSetPolicy(this);
    }

    /**
     * Called by the constructor methods to create the default
     * <code>rootPane</code>.
     * <p>
     *  通过构造方法调用来创建默认的<code> rootPane </code>。
     * 
     */
    protected JRootPane createRootPane() {
        JRootPane rp = new JRootPane();
        // NOTE: this uses setOpaque vs LookAndFeel.installProperty as there
        // is NO reason for the RootPane not to be opaque. For painting to
        // work the contentPane must be opaque, therefor the RootPane can
        // also be opaque.
        rp.setOpaque(true);
        return rp;
    }

    /**
     * Returns whether calls to <code>add</code> and
     * <code>setLayout</code> are forwarded to the <code>contentPane</code>.
     *
     * <p>
     *  返回是否将对<code> add </code>和<code> setLayout </code>的调用转发到<code> contentPane </code>。
     * 
     * 
     * @return true if <code>add</code> and <code>setLayout</code>
     *         are forwarded; false otherwise
     *
     * @see #addImpl
     * @see #setLayout
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean isRootPaneCheckingEnabled() {
        return rootPaneCheckingEnabled;
    }

    /**
     * Sets the {@code transferHandler} property, which is a mechanism to
     * support transfer of data into this component. Use {@code null}
     * if the component does not support data transfer operations.
     * <p>
     * If the system property {@code suppressSwingDropSupport} is {@code false}
     * (the default) and the current drop target on this component is either
     * {@code null} or not a user-set drop target, this method will change the
     * drop target as follows: If {@code newHandler} is {@code null} it will
     * clear the drop target. If not {@code null} it will install a new
     * {@code DropTarget}.
     * <p>
     * Note: When used with {@code JWindow}, {@code TransferHandler} only
     * provides data import capability, as the data export related methods
     * are currently typed to {@code JComponent}.
     * <p>
     * Please see
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
     * How to Use Drag and Drop and Data Transfer</a>, a section in
     * <em>The Java Tutorial</em>, for more information.
     *
     * <p>
     *  设置{@code transferHandler}属性,这是一种支持将数据传输到此组件的机制。如果组件不支持数据传输操作,请使用{@code null}。
     * <p>
     *  如果系统属性{@code suppressSwingDropSupport}是{@code false}(默认值),并且此组件上的当前放置目标是{@code null}或不是用户设置放置目标,则此方法
     * 将更改放置目标如下所示：如果{@code newHandler}是{@code null},它将清除放置目标。
     * 如果不是{@code null},它会安装一个新的{@code DropTarget}。
     * <p>
     *  注意：与{@code JWindow}结合使用时,{@code TransferHandler}仅提供数据导入功能,因为数据导出相关方法目前类型为{@code JComponent}。
     * <p>
     *  请参见
     * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
     *  如何使用拖放和数据传输</a>,有关更多信息,请参阅<em> Java教程</em>中的一节。
     * 
     * 
     * @param newHandler the new {@code TransferHandler}
     *
     * @see TransferHandler
     * @see #getTransferHandler
     * @see java.awt.Component#setDropTarget
     * @since 1.6
     *
     * @beaninfo
     *        bound: true
     *       hidden: true
     *  description: Mechanism for transfer of data into the component
     */
    public void setTransferHandler(TransferHandler newHandler) {
        TransferHandler oldHandler = transferHandler;
        transferHandler = newHandler;
        SwingUtilities.installSwingDropTargetAsNecessary(this, transferHandler);
        firePropertyChange("transferHandler", oldHandler, newHandler);
    }

    /**
     * Gets the <code>transferHandler</code> property.
     *
     * <p>
     *  获取<code> transferHandler </code>属性。
     * 
     * 
     * @return the value of the <code>transferHandler</code> property
     *
     * @see TransferHandler
     * @see #setTransferHandler
     * @since 1.6
     */
    public TransferHandler getTransferHandler() {
        return transferHandler;
    }

    /**
     * Calls <code>paint(g)</code>.  This method was overridden to
     * prevent an unnecessary call to clear the background.
     *
     * <p>
     *  调用<code> paint(g)</code>。此方法被覆盖,以防止不必要的调用清除背景。
     * 
     * 
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Sets whether calls to <code>add</code> and
     * <code>setLayout</code> are forwarded to the <code>contentPane</code>.
     *
     * <p>
     *  设置是否将对<code> add </code>和<code> setLayout </code>的调用转发到<code> contentPane </code>。
     * 
     * 
     * @param enabled  true if <code>add</code> and <code>setLayout</code>
     *        are forwarded, false if they should operate directly on the
     *        <code>JWindow</code>.
     *
     * @see #addImpl
     * @see #setLayout
     * @see #isRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     * @beaninfo
     *      hidden: true
     * description: Whether the add and setLayout methods are forwarded
     */
    protected void setRootPaneCheckingEnabled(boolean enabled) {
        rootPaneCheckingEnabled = enabled;
    }


    /**
     * Adds the specified child <code>Component</code>.
     * This method is overridden to conditionally forward calls to the
     * <code>contentPane</code>.
     * By default, children are added to the <code>contentPane</code> instead
     * of the frame, refer to {@link javax.swing.RootPaneContainer} for
     * details.
     *
     * <p>
     * 添加指定的子<code> Component </code>。将覆盖此方法以有条件地将调用转发到<code> contentPane </code>。
     * 默认情况下,将子代添加到<code> contentPane </code>而不是框架中,有关详细信息,请参阅{@link javax.swing.RootPaneContainer}。
     * 
     * 
     * @param comp the component to be enhanced
     * @param constraints the constraints to be respected
     * @param index the index
     * @exception IllegalArgumentException if <code>index</code> is invalid
     * @exception IllegalArgumentException if adding the container's parent
     *                  to itself
     * @exception IllegalArgumentException if adding a window to a container
     *
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected void addImpl(Component comp, Object constraints, int index)
    {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().add(comp, constraints, index);
        }
        else {
            super.addImpl(comp, constraints, index);
        }
    }

    /**
     * Removes the specified component from the container. If
     * <code>comp</code> is not the <code>rootPane</code>, this will forward
     * the call to the <code>contentPane</code>. This will do nothing if
     * <code>comp</code> is not a child of the <code>JWindow</code> or
     * <code>contentPane</code>.
     *
     * <p>
     *  从容器中删除指定的组件。如果<code> comp </code>不是<code> rootPane </code>,这将转发到<code> contentPane </code>的调用。
     * 如果<code> comp </code>不是<code> JWindow </code>或<code> contentPane </code>的子级,则不会执行任何操作。
     * 
     * 
     * @param comp the component to be removed
     * @throws NullPointerException if <code>comp</code> is null
     * @see #add
     * @see javax.swing.RootPaneContainer
     */
    public void remove(Component comp) {
        if (comp == rootPane) {
            super.remove(comp);
        } else {
            getContentPane().remove(comp);
        }
    }


    /**
     * Sets the <code>LayoutManager</code>.
     * Overridden to conditionally forward the call to the
     * <code>contentPane</code>.
     * Refer to {@link javax.swing.RootPaneContainer} for
     * more information.
     *
     * <p>
     *  设置<code> LayoutManager </code>。重写以有条件地将调用转发到<code> contentPane </code>。
     * 有关详细信息,请参阅{@link javax.swing.RootPaneContainer}。
     * 
     * 
     * @param manager the <code>LayoutManager</code>
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    public void setLayout(LayoutManager manager) {
        if(isRootPaneCheckingEnabled()) {
            getContentPane().setLayout(manager);
        }
        else {
            super.setLayout(manager);
        }
    }


    /**
     * Returns the <code>rootPane</code> object for this window.
     * <p>
     *  返回此窗口的<code> rootPane </code>对象。
     * 
     * 
     * @return the <code>rootPane</code> property for this window
     *
     * @see #setRootPane
     * @see RootPaneContainer#getRootPane
     */
    public JRootPane getRootPane() {
        return rootPane;
    }


    /**
     * Sets the new <code>rootPane</code> object for this window.
     * This method is called by the constructor.
     *
     * <p>
     *  为此窗口设置新的<code> rootPane </code>对象。此方法由构造函数调用。
     * 
     * 
     * @param root the new <code>rootPane</code> property
     * @see #getRootPane
     *
     * @beaninfo
     *        hidden: true
     *   description: the RootPane object for this window.
     */
    protected void setRootPane(JRootPane root) {
        if(rootPane != null) {
            remove(rootPane);
        }
        rootPane = root;
        if(rootPane != null) {
            boolean checkingEnabled = isRootPaneCheckingEnabled();
            try {
                setRootPaneCheckingEnabled(false);
                add(rootPane, BorderLayout.CENTER);
            }
            finally {
                setRootPaneCheckingEnabled(checkingEnabled);
            }
        }
    }


    /**
     * Returns the <code>Container</code> which is the <code>contentPane</code>
     * for this window.
     *
     * <p>
     *  返回<code> Container </code>这是此窗口的<code> contentPane </code>。
     * 
     * 
     * @return the <code>contentPane</code> property
     * @see #setContentPane
     * @see RootPaneContainer#getContentPane
     */
    public Container getContentPane() {
        return getRootPane().getContentPane();
    }

    /**
     * Sets the <code>contentPane</code> property for this window.
     * This method is called by the constructor.
     *
     * <p>
     *  设置此窗口的<code> contentPane </code>属性。此方法由构造函数调用。
     * 
     * 
     * @param contentPane the new <code>contentPane</code>
     *
     * @exception IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is <code>null</code>
     * @see #getContentPane
     * @see RootPaneContainer#setContentPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The client area of the window where child
     *                  components are normally inserted.
     */
    public void setContentPane(Container contentPane) {
        getRootPane().setContentPane(contentPane);
    }

    /**
     * Returns the <code>layeredPane</code> object for this window.
     *
     * <p>
     *  返回此窗口的<code> layeredPane </code>对象。
     * 
     * 
     * @return the <code>layeredPane</code> property
     * @see #setLayeredPane
     * @see RootPaneContainer#getLayeredPane
     */
    public JLayeredPane getLayeredPane() {
        return getRootPane().getLayeredPane();
    }

    /**
     * Sets the <code>layeredPane</code> property.
     * This method is called by the constructor.
     *
     * <p>
     *  设置<code> layeredPane </code>属性。此方法由构造函数调用。
     * 
     * 
     * @param layeredPane the new <code>layeredPane</code> object
     *
     * @exception IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is <code>null</code>
     * @see #getLayeredPane
     * @see RootPaneContainer#setLayeredPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The pane which holds the various window layers.
     */
    public void setLayeredPane(JLayeredPane layeredPane) {
        getRootPane().setLayeredPane(layeredPane);
    }

    /**
     * Returns the <code>glassPane Component</code> for this window.
     *
     * <p>
     *  返回此窗口的<code> glassPane Component </code>。
     * 
     * 
     * @return the <code>glassPane</code> property
     * @see #setGlassPane
     * @see RootPaneContainer#getGlassPane
     */
    public Component getGlassPane() {
        return getRootPane().getGlassPane();
    }

    /**
     * Sets the <code>glassPane</code> property.
     * This method is called by the constructor.
     * <p>
     *  设置<code> glassPane </code>属性。此方法由构造函数调用。
     * 
     * 
     * @param glassPane the <code>glassPane</code> object for this window
     *
     * @see #getGlassPane
     * @see RootPaneContainer#setGlassPane
     *
     * @beaninfo
     *     hidden: true
     *     description: A transparent pane used for menu rendering.
     */
    public void setGlassPane(Component glassPane) {
        getRootPane().setGlassPane(glassPane);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public Graphics getGraphics() {
        JComponent.getGraphicsInvoked(this);
        return super.getGraphics();
    }

    /**
     * Repaints the specified rectangle of this component within
     * <code>time</code> milliseconds.  Refer to <code>RepaintManager</code>
     * for details on how the repaint is handled.
     *
     * <p>
     * 在<code> time </code>毫秒内重新绘制此组件的指定矩形。有关如何处理重绘的详细信息,请参阅<code> RepaintManager </code>。
     * 
     * 
     * @param     time   maximum time in milliseconds before update
     * @param     x    the <i>x</i> coordinate
     * @param     y    the <i>y</i> coordinate
     * @param     width    the width
     * @param     height   the height
     * @see       RepaintManager
     * @since     1.6
     */
    public void repaint(long time, int x, int y, int width, int height) {
        if (RepaintManager.HANDLE_TOP_LEVEL_PAINT) {
            RepaintManager.currentManager(this).addDirtyRegion(
                              this, x, y, width, height);
        }
        else {
            super.repaint(time, x, y, width, height);
        }
    }

    /**
     * Returns a string representation of this <code>JWindow</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JWindow </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JWindow</code>
     */
    protected String paramString() {
        String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",rootPaneCheckingEnabled=" + rootPaneCheckingEnabledString;
    }


/////////////////
// Accessibility support
////////////////

    /** The accessible context property. */
    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JWindow.
     * For JWindows, the AccessibleContext takes the form of an
     * AccessibleJWindow.
     * A new AccessibleJWindow instance is created if necessary.
     *
     * <p>
     *  获取与此JWindow相关联的AccessibleContext。对于JWindows,AccessibleContext采用AccessibleJWindow的形式。
     * 如果需要,将创建一个新的AccessibleJWindow实例。
     * 
     * 
     * @return an AccessibleJWindow that serves as the
     *         AccessibleContext of this JWindow
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJWindow();
        }
        return accessibleContext;
    }


    /**
     * This class implements accessibility support for the
     * <code>JWindow</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to window user-interface
     * elements.
     * <p>
     */
    @SuppressWarnings("serial")
    protected class AccessibleJWindow extends AccessibleAWTWindow {
        // everything is in the new parent, AccessibleAWTWindow
    }

}
