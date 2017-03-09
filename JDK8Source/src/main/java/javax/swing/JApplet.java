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
import java.applet.Applet;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.Vector;
import java.io.Serializable;
import javax.accessibility.*;

/**
 * An extended version of <code>java.applet.Applet</code> that adds support for
 * the JFC/Swing component architecture.
 * You can find task-oriented documentation about using <code>JApplet</code>
 * in <em>The Java Tutorial</em>,
 * in the section
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/applet.html">How to Make Applets</a>.
 * <p>
 * The <code>JApplet</code> class is slightly incompatible with
 * <code>java.applet.Applet</code>.  <code>JApplet</code> contains a
 * <code>JRootPane</code> as its only child.  The <code>contentPane</code>
 * should be the parent of any children of the <code>JApplet</code>.
 * As a convenience, the {@code add}, {@code remove}, and {@code setLayout}
 * methods of this class are overridden, so that they delegate calls
 * to the corresponding methods of the {@code ContentPane}.
 * For example, you can add a child component to an applet as follows:
 * <pre>
 *       applet.add(child);
 * </pre>
 *
 * And the child will be added to the <code>contentPane</code>.
 * The <code>contentPane</code> will always be non-<code>null</code>.
 * Attempting to set it to <code>null</code> will cause the
 * <code>JApplet</code> to throw an exception. The default
 * <code>contentPane</code> will have a <code>BorderLayout</code>
 * manager set on it.
 * Refer to {@link javax.swing.RootPaneContainer}
 * for details on adding, removing and setting the <code>LayoutManager</code>
 * of a <code>JApplet</code>.
 * <p>
 * Please see the <code>JRootPane</code> documentation for a
 * complete description of the <code>contentPane</code>, <code>glassPane</code>,
 * and <code>layeredPane</code> properties.
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
 *  <code> java.applet.Applet </code>的扩展版本,增加了对JFC / Swing组件架构的支持。
 * 您可以在<em> Java教程</em>中的<a href ="https://docs.oracle.com/javase/tutorial一节中找到关于使用<code> JApplet </code>
 * 的面向任务的文档/uiswing/components/applet.html">如何制作小苹果</a>。
 *  <code> java.applet.Applet </code>的扩展版本,增加了对JFC / Swing组件架构的支持。
 * <p>
 *  <code> JApplet </code>类与<code> java.applet.Applet </code>稍有不兼容。
 *  <code> JApplet </code>包含一个<code> JRootPane </code>作为其唯一的孩子。
 *  <code> contentPane </code>应该是<code> JApplet </code>的任何子级的父级。
 * 为方便起见,此类的{@code add},{@code remove}和{@code setLayout}方法被覆盖,因此它们将调用委派给{@code ContentPane}的相应方法。
 * 例如,您可以将子组件添加到小程序,如下所示：。
 * <pre>
 *  applet.add(child);
 * </pre>
 * 
 * 并且孩子将被添加到<code> contentPane </code>。 <code> contentPane </code>将始终为非<code> null </code>。
 * 尝试将其设置为<code> null </code>会导致<code> JApplet </code>抛出异常。
 * 默认的<code> contentPane </code>会有一个<code> BorderLayout </code>管理器。
 * 有关添加,删除和设置<code> JApplet </code>的<code> LayoutManager </code>的详细信息,请参阅{@link javax.swing.RootPaneContainer}
 * 。
 * 默认的<code> contentPane </code>会有一个<code> BorderLayout </code>管理器。
 * <p>
 *  有关<code> contentPane </code>,<code> glassPane </code>和<code> layeredPane </code>属性的完整说明,请参阅<code> JR
 * ootPane </code>文档。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see javax.swing.RootPaneContainer
 * @beaninfo
 *      attribute: isContainer true
 *      attribute: containerDelegate getContentPane
 *    description: Swing's Applet subclass.
 *
 * @author Arnaud Weber
 */
public class JApplet extends Applet implements Accessible,
                                               RootPaneContainer,
                               TransferHandler.HasGetTransferHandler
{
    /**
    /* <p>
    /* 
     * @see #getRootPane
     * @see #setRootPane
     */
    protected JRootPane rootPane;

    /**
     * If true then calls to <code>add</code> and <code>setLayout</code>
     * will be forwarded to the <code>contentPane</code>. This is initially
     * false, but is set to true when the <code>JApplet</code> is constructed.
     *
     * <p>
     *  如果为true,那么对<code> add </code>和<code> setLayout </code>的调用将被转发到<code> contentPane </code>。
     * 这最初是false,但是当构造<code> JApplet </code>时设置为true。
     * 
     * 
     * @see #isRootPaneCheckingEnabled
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean rootPaneCheckingEnabled = false;

    /**
     * The <code>TransferHandler</code> for this applet.
     * <p>
     *  此小程序的<code> TransferHandler </code>。
     * 
     */
    private TransferHandler transferHandler;

    /**
     * Creates a swing applet instance.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     * 创建swing applet实例。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JApplet() throws HeadlessException {
        super();
        // Check the timerQ and restart if necessary.
        TimerQueue q = TimerQueue.sharedInstance();
        if(q != null) {
            q.startIfNeeded();
        }

        /* Workaround for bug 4155072.  The shared double buffer image
         * may hang on to a reference to this applet; unfortunately
         * Image.getGraphics() will continue to call JApplet.getForeground()
         * and getBackground() even after this applet has been destroyed.
         * So we ensure that these properties are non-null here.
         * <p>
         *  可能会挂在这个applet的引用上;不幸的是Image.getGraphics()将继续调用JApplet.getForeground()和getBackground(),即使这个applet被销毁。
         * 所以我们确保这些属性在这里是非null的。
         * 
         */
        setForeground(Color.black);
        setBackground(Color.white);

        setLocale( JComponent.getDefaultLocale() );
        setLayout(new BorderLayout());
        setRootPane(createRootPane());
        setRootPaneCheckingEnabled(true);

        setFocusTraversalPolicyProvider(true);
        sun.awt.SunToolkit.checkAndSetPolicy(this);

        enableEvents(AWTEvent.KEY_EVENT_MASK);
    }


    /** Called by the constructor methods to create the default rootPane. */
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
     * Note: When used with {@code JApplet}, {@code TransferHandler} only
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
     *  注意：与{@code JApplet}结合使用时,{@code TransferHandler}仅提供数据导入功能,因为数据导出相关方法目前类型为{@code JComponent}。
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
     * Just calls <code>paint(g)</code>.  This method was overridden to
     * prevent an unnecessary call to clear the background.
     * <p>
     *  只需调用<code> paint(g)</code>。此方法被覆盖,以防止不必要的调用清除背景。
     * 
     */
    public void update(Graphics g) {
        paint(g);
    }

   /**
    * Sets the menubar for this applet.
    * <p>
    *  设置此applet的菜单。
    * 
    * 
    * @param menuBar the menubar being placed in the applet
    *
    * @see #getJMenuBar
    *
    * @beaninfo
    *      hidden: true
    * description: The menubar for accessing pulldown menus from this applet.
    */
    public void setJMenuBar(JMenuBar menuBar) {
        getRootPane().setMenuBar(menuBar);
    }

   /**
    * Returns the menubar set on this applet.
    *
    * <p>
    * 返回此applet上的菜单集。
    * 
    * 
    * @see #setJMenuBar
    */
    public JMenuBar getJMenuBar() {
        return getRootPane().getMenuBar();
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
     * Sets whether calls to <code>add</code> and
     * <code>setLayout</code> are forwarded to the <code>contentPane</code>.
     *
     * <p>
     *  设置是否将对<code> add </code>和<code> setLayout </code>的调用转发到<code> contentPane </code>。
     * 
     * 
     * @param enabled  true if <code>add</code> and <code>setLayout</code>
     *        are forwarded, false if they should operate directly on the
     *        <code>JApplet</code>.
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
     *  添加指定的子<code> Component </code>。将覆盖此方法以有条件地将调用转发到<code> contentPane </code>。
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
     * <code>comp</code> is not a child of the <code>JFrame</code> or
     * <code>contentPane</code>.
     *
     * <p>
     *  从容器中删除指定的组件。如果<code> comp </code>不是<code> rootPane </code>,这将转发到<code> contentPane </code>的调用。
     * 如果<code> comp </code>不是<code> JFrame </code>或<code> contentPane </code>的子级,则此操作无效。
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
     * Returns the rootPane object for this applet.
     *
     * <p>
     *  返回此applet的rootPane对象。
     * 
     * 
     * @see #setRootPane
     * @see RootPaneContainer#getRootPane
     */
    public JRootPane getRootPane() {
        return rootPane;
    }


    /**
     * Sets the rootPane property.  This method is called by the constructor.
     * <p>
     *  设置rootPane属性。此方法由构造函数调用。
     * 
     * 
     * @param root the rootPane object for this applet
     *
     * @see #getRootPane
     *
     * @beaninfo
     *   hidden: true
     * description: the RootPane object for this applet.
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
     * Returns the contentPane object for this applet.
     *
     * <p>
     *  返回此applet的contentPane对象。
     * 
     * 
     * @see #setContentPane
     * @see RootPaneContainer#getContentPane
     */
    public Container getContentPane() {
        return getRootPane().getContentPane();
    }

   /**
     * Sets the contentPane property.  This method is called by the constructor.
     * <p>
     *  设置contentPane属性。此方法由构造函数调用。
     * 
     * 
     * @param contentPane the contentPane object for this applet
     *
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is null
     * @see #getContentPane
     * @see RootPaneContainer#setContentPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The client area of the applet where child
     *                  components are normally inserted.
     */
    public void setContentPane(Container contentPane) {
        getRootPane().setContentPane(contentPane);
    }

    /**
     * Returns the layeredPane object for this applet.
     *
     * <p>
     *  返回此applet的layeredPane对象。
     * 
     * 
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the layered pane parameter is null
     * @see #setLayeredPane
     * @see RootPaneContainer#getLayeredPane
     */
    public JLayeredPane getLayeredPane() {
        return getRootPane().getLayeredPane();
    }

    /**
     * Sets the layeredPane property.  This method is called by the constructor.
     * <p>
     *  设置layeredPane属性。此方法由构造函数调用。
     * 
     * 
     * @param layeredPane the layeredPane object for this applet
     *
     * @see #getLayeredPane
     * @see RootPaneContainer#setLayeredPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The pane which holds the various applet layers.
     */
    public void setLayeredPane(JLayeredPane layeredPane) {
        getRootPane().setLayeredPane(layeredPane);
    }

    /**
     * Returns the glassPane object for this applet.
     *
     * <p>
     *  返回此applet的glassPane对象。
     * 
     * 
     * @see #setGlassPane
     * @see RootPaneContainer#getGlassPane
     */
    public Component getGlassPane() {
        return getRootPane().getGlassPane();
    }

    /**
     * Sets the glassPane property.
     * This method is called by the constructor.
     * <p>
     * 设置glassPane属性。此方法由构造函数调用。
     * 
     * 
     * @param glassPane the glassPane object for this applet
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
     *  在<code> time </code>毫秒内重新绘制此组件的指定矩形。有关如何处理重绘的详细信息,请参阅<code> RepaintManager </code>。
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
     * Returns a string representation of this JApplet. This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此JApplet的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this JApplet.
     */
    protected String paramString() {
        String rootPaneString = (rootPane != null ?
                                 rootPane.toString() : "");
        String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",rootPane=" + rootPaneString +
        ",rootPaneCheckingEnabled=" + rootPaneCheckingEnabledString;
    }



/////////////////
// Accessibility support
////////////////

    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JApplet.
     * For JApplets, the AccessibleContext takes the form of an
     * AccessibleJApplet.
     * A new AccessibleJApplet instance is created if necessary.
     *
     * <p>
     *  获取与此JApplet相关联的AccessibleContext。对于JApplet,AccessibleContext采用AccessibleJApplet的形式。
     * 如果需要,将创建一个新的AccessibleJApplet实例。
     * 
     * 
     * @return an AccessibleJApplet that serves as the
     *         AccessibleContext of this JApplet
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJApplet();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JApplet</code> class.
     * <p>
     */
    protected class AccessibleJApplet extends AccessibleApplet {
        // everything moved to new parent, AccessibleApplet
    }
}
