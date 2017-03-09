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

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.WindowEvent;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleState;
import javax.accessibility.AccessibleStateSet;


/**
 * An extended version of <code>java.awt.Frame</code> that adds support for
 * the JFC/Swing component architecture.
 * You can find task-oriented documentation about using <code>JFrame</code>
 * in <em>The Java Tutorial</em>, in the section
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html">How to Make Frames</a>.
 *
 * <p>
 * The <code>JFrame</code> class is slightly incompatible with <code>Frame</code>.
 * Like all other JFC/Swing top-level containers,
 * a <code>JFrame</code> contains a <code>JRootPane</code> as its only child.
 * The <b>content pane</b> provided by the root pane should,
 * as a rule, contain
 * all the non-menu components displayed by the <code>JFrame</code>.
 * This is different from the AWT <code>Frame</code> case.
 * As a convenience, the {@code add}, {@code remove}, and {@code setLayout}
 * methods of this class are overridden, so that they delegate calls
 * to the corresponding methods of the {@code ContentPane}.
 * For example, you can add a child component to a frame as follows:
 * <pre>
 *       frame.add(child);
 * </pre>
 * And the child will be added to the contentPane.
 * The content pane will
 * always be non-null. Attempting to set it to null will cause the JFrame
 * to throw an exception. The default content pane will have a BorderLayout
 * manager set on it.
 * Refer to {@link javax.swing.RootPaneContainer}
 * for details on adding, removing and setting the <code>LayoutManager</code>
 * of a <code>JFrame</code>.
 * <p>
 * Unlike a <code>Frame</code>, a <code>JFrame</code> has some notion of how to
 * respond when the user attempts to close the window. The default behavior
 * is to simply hide the JFrame when the user closes the window. To change the
 * default behavior, you invoke the method
 * {@link #setDefaultCloseOperation}.
 * To make the <code>JFrame</code> behave the same as a <code>Frame</code>
 * instance, use
 * <code>setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)</code>.
 * <p>
 * For more information on content panes
 * and other features that root panes provide,
 * see <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html">Using Top-Level Containers</a> in <em>The Java Tutorial</em>.
 * <p>
 * In a multi-screen environment, you can create a <code>JFrame</code>
 * on a different screen device.  See {@link java.awt.Frame} for more
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
 *  扩展版本的<code> java.awt.Frame </code>,增加了对JFC / Swing组件架构的支持。
 * 您可以在<em> Java教程</em>中的<a href ="https://docs.oracle.com/javase/tutorial一节中找到有关使用<code> JFrame </code>
 * 的面向任务的文档/uiswing/components/frame.html">如何制作框架</a>。
 *  扩展版本的<code> java.awt.Frame </code>,增加了对JFC / Swing组件架构的支持。
 * 
 * <p>
 *  <code> JFrame </code>类与<code> Frame </code>稍有不兼容。
 * 像所有其他JFC / Swing顶级容器一样,<code> JFrame </code>包含一个<code> JRootPane </code>作为它的唯一的孩子。
 * 根窗格提供的<b>内容窗格</b>应该包含由<code> JFrame </code>显示的所有非菜单组件。这与AWT <code> Frame </code>案例不同。
 * 为方便起见,此类的{@code add},{@code remove}和{@code setLayout}方法被覆盖,因此它们将调用委派给{@code ContentPane}的相应方法。
 * 例如,您可以将子组件添加到框架,如下所示：。
 * <pre>
 *  frame.add(child);
 * </pre>
 * 并且孩子将被添加到contentPane。内容窗格将始终为非空。尝试将其设置为null将导致JFrame抛出异常。默认内容窗格将设置一个BorderLayout管理器。
 * 有关添加,删除和设置<code> JFrame </code>的<code> LayoutManager </code>的详细信息,请参阅{@link javax.swing.RootPaneContainer}
 * 。
 * 并且孩子将被添加到contentPane。内容窗格将始终为非空。尝试将其设置为null将导致JFrame抛出异常。默认内容窗格将设置一个BorderLayout管理器。
 * <p>
 *  与<code> Frame </code>不同,<code> JFrame </code>有一些用户尝试关闭窗口时如何响应的概念。默认行为是在用户关闭窗口时简单地隐藏JFrame。
 * 要更改默认行为,请调用{@link #setDefaultCloseOperation}方法。
 * 要使<code> JFrame </code>的行为与<code> Frame </code>实例相同,请使用<code> setDefaultCloseOperation(WindowConstant
 * s.DO_NOTHING_ON_CLOSE)</code>。
 * 要更改默认行为,请调用{@link #setDefaultCloseOperation}方法。
 * <p>
 *  有关内容窗格和根窗格提供的其他功能的详细信息,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/toplevel.html">
 * 使用顶级容器</span> a>在Java教程</em>中。
 * <p>
 *  在多屏幕环境中,您可以在其他屏幕设备上创建<code> JFrame </code>。有关详细信息,请参阅{@link java.awt.Frame}。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JRootPane
 * @see #setDefaultCloseOperation
 * @see java.awt.event.WindowListener#windowClosing
 * @see javax.swing.RootPaneContainer
 *
 * @beaninfo
 *      attribute: isContainer true
 *      attribute: containerDelegate getContentPane
 *    description: A toplevel window which can be minimized to an icon.
 *
 * @author Jeff Dinkins
 * @author Georges Saab
 * @author David Kloba
 */
public class JFrame  extends Frame implements WindowConstants,
                                              Accessible,
                                              RootPaneContainer,
                              TransferHandler.HasGetTransferHandler
{
    /**
     * The exit application default window close operation. If a window
     * has this set as the close operation and is closed in an applet,
     * a <code>SecurityException</code> may be thrown.
     * It is recommended you only use this in an application.
     * <p>
     * <p>
     *  退出应用程序默认窗口关闭操作。如果一个窗口被设置为关闭操作并且在一个applet中被关闭,那么可能会抛出一个<code> SecurityException </code>。
     * 建议您只在应用程序中使用它。
     * <p>
     * 
     * @since 1.3
     */
    public static final int EXIT_ON_CLOSE = 3;

    /**
     * Key into the AppContext, used to check if should provide decorations
     * by default.
     * <p>
     *  Key进入AppContext,用于检查是否应该默认提供装饰。
     * 
     */
    private static final Object defaultLookAndFeelDecoratedKey =
            new StringBuffer("JFrame.defaultLookAndFeelDecorated");

    private int defaultCloseOperation = HIDE_ON_CLOSE;

    /**
     * The <code>TransferHandler</code> for this frame.
     * <p>
     *  此框架的<code> TransferHandler </code>。
     * 
     */
    private TransferHandler transferHandler;

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
     * @see JRootPane
     * @see RootPaneContainer
     */
    protected JRootPane rootPane;

    /**
     * If true then calls to <code>add</code> and <code>setLayout</code>
     * will be forwarded to the <code>contentPane</code>. This is initially
     * false, but is set to true when the <code>JFrame</code> is constructed.
     *
     * <p>
     *  如果为true,那么对<code> add </code>和<code> setLayout </code>的调用将被转发到<code> contentPane </code>。
     * 这最初是false,但在构建<code> JFrame </code>时设置为true。
     * 
     * 
     * @see #isRootPaneCheckingEnabled
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    protected boolean rootPaneCheckingEnabled = false;


    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  构造一个最初不可见的新框架。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public JFrame() throws HeadlessException {
        super();
        frameInit();
    }

    /**
     * Creates a <code>Frame</code> in the specified
     * <code>GraphicsConfiguration</code> of
     * a screen device and a blank title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  在屏幕设备的指定<code> GraphicsConfiguration </code>中创建<code>框架</code>和空白标题。
     * <p>
     * 此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param gc the <code>GraphicsConfiguration</code> that is used
     *          to construct the new <code>Frame</code>;
     *          if <code>gc</code> is <code>null</code>, the system
     *          default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     *          a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since     1.3
     */
    public JFrame(GraphicsConfiguration gc) {
        super(gc);
        frameInit();
    }

    /**
     * Creates a new, initially invisible <code>Frame</code> with the
     * specified title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  使用指定的标题创建一个新的,初始不可见的<code> Frame </code>。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param title the title for the frame
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public JFrame(String title) throws HeadlessException {
        super(title);
        frameInit();
    }

    /**
     * Creates a <code>JFrame</code> with the specified title and the
     * specified <code>GraphicsConfiguration</code> of a screen device.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * <p>
     *  使用指定的标题和指定的屏幕设备的<code> GraphicsConfiguration </code>创建<code> JFrame </code>。
     * <p>
     *  此构造函数将组件的locale属性设置为<code> JComponent.getDefaultLocale </code>返回的值。
     * 
     * 
     * @param title the title to be displayed in the
     *          frame's border. A <code>null</code> value is treated as
     *          an empty string, "".
     * @param gc the <code>GraphicsConfiguration</code> that is used
     *          to construct the new <code>JFrame</code> with;
     *          if <code>gc</code> is <code>null</code>, the system
     *          default <code>GraphicsConfiguration</code> is assumed
     * @exception IllegalArgumentException if <code>gc</code> is not from
     *          a screen device.  This exception is always thrown when
     *      GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since     1.3
     */
    public JFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        frameInit();
    }

    /** Called by the constructors to init the <code>JFrame</code> properly. */
    protected void frameInit() {
        enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
        setLocale( JComponent.getDefaultLocale() );
        setRootPane(createRootPane());
        setBackground(UIManager.getColor("control"));
        setRootPaneCheckingEnabled(true);
        if (JFrame.isDefaultLookAndFeelDecorated()) {
            boolean supportsWindowDecorations =
            UIManager.getLookAndFeel().getSupportsWindowDecorations();
            if (supportsWindowDecorations) {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            }
        }
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
     * Processes window events occurring on this component.
     * Hides the window or disposes of it, as specified by the setting
     * of the <code>defaultCloseOperation</code> property.
     *
     * <p>
     *  处理在此组件上发生的窗口事件。根据<code> defaultCloseOperation </code>属性的设置指定隐藏窗口或处理窗口。
     * 
     * 
     * @param  e  the window event
     * @see    #setDefaultCloseOperation
     * @see    java.awt.Window#processWindowEvent
     */
    protected void processWindowEvent(final WindowEvent e) {
        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch (defaultCloseOperation) {
                case HIDE_ON_CLOSE:
                    setVisible(false);
                    break;
                case DISPOSE_ON_CLOSE:
                    dispose();
                    break;
                case EXIT_ON_CLOSE:
                    // This needs to match the checkExit call in
                    // setDefaultCloseOperation
                    System.exit(0);
                    break;
                case DO_NOTHING_ON_CLOSE:
                default:
            }
        }
    }

    /**
     * Sets the operation that will happen by default when
     * the user initiates a "close" on this frame.
     * You must specify one of the following choices:
     * <br><br>
     * <ul>
     * <li><code>DO_NOTHING_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Don't do anything; require the
     * program to handle the operation in the <code>windowClosing</code>
     * method of a registered <code>WindowListener</code> object.
     *
     * <li><code>HIDE_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Automatically hide the frame after
     * invoking any registered <code>WindowListener</code>
     * objects.
     *
     * <li><code>DISPOSE_ON_CLOSE</code>
     * (defined in <code>WindowConstants</code>):
     * Automatically hide and dispose the
     * frame after invoking any registered <code>WindowListener</code>
     * objects.
     *
     * <li><code>EXIT_ON_CLOSE</code>
     * (defined in <code>JFrame</code>):
     * Exit the application using the <code>System</code>
     * <code>exit</code> method.  Use this only in applications.
     * </ul>
     * <p>
     * The value is set to <code>HIDE_ON_CLOSE</code> by default. Changes
     * to the value of this property cause the firing of a property
     * change event, with property name "defaultCloseOperation".
     * <p>
     * <b>Note</b>: When the last displayable window within the
     * Java virtual machine (VM) is disposed of, the VM may
     * terminate.  See <a href="../../java/awt/doc-files/AWTThreadIssues.html">
     * AWT Threading Issues</a> for more information.
     *
     * <p>
     *  设置当用户在此帧上启动"关闭"时默认发生的操作。您必须指定以下选项之一：<br> <br>
     * <ul>
     *  <li> <code> DO_NOTHING_ON_CLOSE </code>(在<code> WindowConstants </code>中定义)：不要做任何事情;要求程序处理注册的<code> 
     * WindowListener </code>对象的<code> windowClosing </code>方法中的操作。
     * 
     *  <li> <code> HIDE_ON_CLOSE </code>(在<code> WindowConstants </code>中定义)：在调用任何注册的<code> WindowListener 
     * </code>对象后自动隐藏框架。
     * 
     * <li> <code> DISPOSE_ON_CLOSE </code>(在<code> WindowConstants </code>中定义)：在调用任何注册的<code> WindowListene
     * r </code>对象后自动隐藏和处理框架。
     * 
     *  <li> <code> EXIT_ON_CLOSE </code>(在<code> JFrame </code>中定义)：使用<code> System </code> <code> exit </code>
     * 方法退出应用程序。
     * 仅在应用程序中使用它。
     * </ul>
     * <p>
     *  默认情况下,该值设置为<code> HIDE_ON_CLOSE </code>。对此属性的值的更改导致触发属性更改事件,属性名称为"defaultCloseOperation"。
     * <p>
     *  <b>注意</b>：当处理Java虚拟机(VM)中的最后一个可显示窗口时,VM可能终止。
     * 有关详细信息,请参见<a href="../../java/awt/doc-files/AWTThreadIssues.html"> AWT线程问题</a>。
     * 
     * 
     * @param operation the operation which should be performed when the
     *        user closes the frame
     * @exception IllegalArgumentException if defaultCloseOperation value
     *             isn't one of the above valid values
     * @see #addWindowListener
     * @see #getDefaultCloseOperation
     * @see WindowConstants
     * @throws  SecurityException
     *        if <code>EXIT_ON_CLOSE</code> has been specified and the
     *        <code>SecurityManager</code> will
     *        not allow the caller to invoke <code>System.exit</code>
     * @see        java.lang.Runtime#exit(int)
     *
     * @beaninfo
     *   preferred: true
     *       bound: true
     *        enum: DO_NOTHING_ON_CLOSE WindowConstants.DO_NOTHING_ON_CLOSE
     *              HIDE_ON_CLOSE       WindowConstants.HIDE_ON_CLOSE
     *              DISPOSE_ON_CLOSE    WindowConstants.DISPOSE_ON_CLOSE
     *              EXIT_ON_CLOSE       WindowConstants.EXIT_ON_CLOSE
     * description: The frame's default close operation.
     */
    public void setDefaultCloseOperation(int operation) {
        if (operation != DO_NOTHING_ON_CLOSE &&
            operation != HIDE_ON_CLOSE &&
            operation != DISPOSE_ON_CLOSE &&
            operation != EXIT_ON_CLOSE) {
            throw new IllegalArgumentException("defaultCloseOperation must be one of: DO_NOTHING_ON_CLOSE, HIDE_ON_CLOSE, DISPOSE_ON_CLOSE, or EXIT_ON_CLOSE");
        }

        if (operation == EXIT_ON_CLOSE) {
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkExit(0);
            }
        }
        if (this.defaultCloseOperation != operation) {
            int oldValue = this.defaultCloseOperation;
            this.defaultCloseOperation = operation;
            firePropertyChange("defaultCloseOperation", oldValue, operation);
        }
    }


   /**
    * Returns the operation that occurs when the user
    * initiates a "close" on this frame.
    *
    * <p>
    *  返回用户在此框架上启动"关闭"时发生的操作。
    * 
    * 
    * @return an integer indicating the window-close operation
    * @see #setDefaultCloseOperation
    */
    public int getDefaultCloseOperation() {
        return defaultCloseOperation;
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
     * Note: When used with {@code JFrame}, {@code TransferHandler} only
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
     * 注意：与{@code JFrame}结合使用时,{@code TransferHandler}仅提供数据导入功能,因为数据导出相关方法目前类型为{@code JComponent}。
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
     *
     * <p>
     *  只需调用<code> paint(g)</code>。此方法被覆盖,以防止不必要的调用清除背景。
     * 
     * 
     * @param g the Graphics context in which to paint
     */
    public void update(Graphics g) {
        paint(g);
    }

   /**
    * Sets the menubar for this frame.
    * <p>
    *  设置此框架的菜单栏。
    * 
    * 
    * @param menubar the menubar being placed in the frame
    *
    * @see #getJMenuBar
    *
    * @beaninfo
    *      hidden: true
    * description: The menubar for accessing pulldown menus from this frame.
    */
    public void setJMenuBar(JMenuBar menubar) {
        getRootPane().setMenuBar(menubar);
    }

   /**
    * Returns the menubar set on this frame.
    * <p>
    *  返回此框架上的菜单集。
    * 
    * 
    * @return the menubar for this frame
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
     *        <code>JFrame</code>.
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
     * 设置<code> LayoutManager </code>。重写以有条件地将调用转发到<code> contentPane </code>。
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
     * Returns the <code>rootPane</code> object for this frame.
     * <p>
     *  返回此框架的<code> rootPane </code>对象。
     * 
     * 
     * @return the <code>rootPane</code> property
     *
     * @see #setRootPane
     * @see RootPaneContainer#getRootPane
     */
    public JRootPane getRootPane() {
        return rootPane;
    }


    /**
     * Sets the <code>rootPane</code> property.
     * This method is called by the constructor.
     * <p>
     *  设置<code> rootPane </code>属性。此方法由构造函数调用。
     * 
     * 
     * @param root the <code>rootPane</code> object for this frame
     *
     * @see #getRootPane
     *
     * @beaninfo
     *   hidden: true
     * description: the RootPane object for this frame.
     */
    protected void setRootPane(JRootPane root)
    {
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
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void setIconImage(Image image) {
        super.setIconImage(image);
    }

    /**
     * Returns the <code>contentPane</code> object for this frame.
     * <p>
     *  返回此框架的<code> contentPane </code>对象。
     * 
     * 
     * @return the <code>contentPane</code> property
     *
     * @see #setContentPane
     * @see RootPaneContainer#getContentPane
     */
    public Container getContentPane() {
        return getRootPane().getContentPane();
    }

    /**
     * Sets the <code>contentPane</code> property.
     * This method is called by the constructor.
     * <p>
     * Swing's painting architecture requires an opaque <code>JComponent</code>
     * in the containment hierarchy. This is typically provided by the
     * content pane. If you replace the content pane it is recommended you
     * replace it with an opaque <code>JComponent</code>.
     *
     * <p>
     *  设置<code> contentPane </code>属性。此方法由构造函数调用。
     * <p>
     *  Swing的绘制架构需要在包含层次结构中有一个不透明的<code> JComponent </code>。这通常由内容窗格提供。
     * 如果替换内容窗格,建议您将其替换为不透明的<code> JComponent </code>。
     * 
     * 
     * @param contentPane the <code>contentPane</code> object for this frame
     *
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is <code>null</code>
     * @see #getContentPane
     * @see RootPaneContainer#setContentPane
     * @see JRootPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The client area of the frame where child
     *                  components are normally inserted.
     */
    public void setContentPane(Container contentPane) {
        getRootPane().setContentPane(contentPane);
    }

    /**
     * Returns the <code>layeredPane</code> object for this frame.
     * <p>
     *  返回此框架的<code> layeredPane </code>对象。
     * 
     * 
     * @return the <code>layeredPane</code> property
     *
     * @see #setLayeredPane
     * @see RootPaneContainer#getLayeredPane
     */
    public JLayeredPane getLayeredPane() {
        return getRootPane().getLayeredPane();
    }

    /**
     * Sets the <code>layeredPane</code> property.
     * This method is called by the constructor.
     * <p>
     *  设置<code> layeredPane </code>属性。此方法由构造函数调用。
     * 
     * 
     * @param layeredPane the <code>layeredPane</code> object for this frame
     *
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the layered pane parameter is <code>null</code>
     * @see #getLayeredPane
     * @see RootPaneContainer#setLayeredPane
     *
     * @beaninfo
     *     hidden: true
     *     description: The pane that holds the various frame layers.
     */
    public void setLayeredPane(JLayeredPane layeredPane) {
        getRootPane().setLayeredPane(layeredPane);
    }

    /**
     * Returns the <code>glassPane</code> object for this frame.
     * <p>
     *  返回此框架的<code> glassPane </code>对象。
     * 
     * 
     * @return the <code>glassPane</code> property
     *
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
     * @param glassPane the <code>glassPane</code> object for this frame
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
     * Provides a hint as to whether or not newly created <code>JFrame</code>s
     * should have their Window decorations (such as borders, widgets to
     * close the window, title...) provided by the current look
     * and feel. If <code>defaultLookAndFeelDecorated</code> is true,
     * the current <code>LookAndFeel</code> supports providing window
     * decorations, and the current window manager supports undecorated
     * windows, then newly created <code>JFrame</code>s will have their
     * Window decorations provided by the current <code>LookAndFeel</code>.
     * Otherwise, newly created <code>JFrame</code>s will have their
     * Window decorations provided by the current window manager.
     * <p>
     * You can get the same effect on a single JFrame by doing the following:
     * <pre>
     *    JFrame frame = new JFrame();
     *    frame.setUndecorated(true);
     *    frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
     * </pre>
     *
     * <p>
     * 提供关于新创建的<code> JFrame </code>是否应该具有由当前外观和感觉提供的窗口装饰(例如边框,窗口小部件关闭窗口,标题...)的提示。
     * 如果<code> defaultLookAndFeelDecorated </code>为true,当前<code> LookAndFeel </code>支持提供窗口装饰,并且当前窗口管理器支持未装饰
     * 的窗口,则新创建的<code> JFrame </code>它们的窗口装饰由当前<code> LookAndFeel </code>提供。
     * 提供关于新创建的<code> JFrame </code>是否应该具有由当前外观和感觉提供的窗口装饰(例如边框,窗口小部件关闭窗口,标题...)的提示。
     * 否则,新创建的<code> JFrame </code>将具有由当前窗口管理器提供的窗口装饰。
     * <p>
     *  您可以通过执行以下操作在单个JFrame上获得相同的效果：
     * <pre>
     *  JFrame frame = new JFrame(); frame.setUndecorated(true); frame.getRootPane()。
     * setWindowDecorationStyle(JRootPane.FRAME);。
     * </pre>
     * 
     * 
     * @param defaultLookAndFeelDecorated A hint as to whether or not current
     *        look and feel should provide window decorations
     * @see javax.swing.LookAndFeel#getSupportsWindowDecorations
     * @since 1.4
     */
    public static void setDefaultLookAndFeelDecorated(boolean defaultLookAndFeelDecorated) {
        if (defaultLookAndFeelDecorated) {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.TRUE);
        } else {
            SwingUtilities.appContextPut(defaultLookAndFeelDecoratedKey, Boolean.FALSE);
        }
    }


    /**
     * Returns true if newly created <code>JFrame</code>s should have their
     * Window decorations provided by the current look and feel. This is only
     * a hint, as certain look and feels may not support this feature.
     *
     * <p>
     *  如果新创建的<code> JFrame </code>应该具有由当前外观提供的窗口装饰,则返回true。这只是一个提示,因为某些外观和感觉可能不支持此功能。
     * 
     * 
     * @return true if look and feel should provide Window decorations.
     * @since 1.4
     */
    public static boolean isDefaultLookAndFeelDecorated() {
        Boolean defaultLookAndFeelDecorated =
            (Boolean) SwingUtilities.appContextGet(defaultLookAndFeelDecoratedKey);
        if (defaultLookAndFeelDecorated == null) {
            defaultLookAndFeelDecorated = Boolean.FALSE;
        }
        return defaultLookAndFeelDecorated.booleanValue();
    }

    /**
     * Returns a string representation of this <code>JFrame</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JFrame </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JFrame</code>
     */
    protected String paramString() {
        String defaultCloseOperationString;
        if (defaultCloseOperation == HIDE_ON_CLOSE) {
            defaultCloseOperationString = "HIDE_ON_CLOSE";
        } else if (defaultCloseOperation == DISPOSE_ON_CLOSE) {
            defaultCloseOperationString = "DISPOSE_ON_CLOSE";
        } else if (defaultCloseOperation == DO_NOTHING_ON_CLOSE) {
            defaultCloseOperationString = "DO_NOTHING_ON_CLOSE";
        } else if (defaultCloseOperation == 3) {
            defaultCloseOperationString = "EXIT_ON_CLOSE";
        } else defaultCloseOperationString = "";
        String rootPaneString = (rootPane != null ?
                                 rootPane.toString() : "");
        String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ?
                                                "true" : "false");

        return super.paramString() +
        ",defaultCloseOperation=" + defaultCloseOperationString +
        ",rootPane=" + rootPaneString +
        ",rootPaneCheckingEnabled=" + rootPaneCheckingEnabledString;
    }



/////////////////
// Accessibility support
////////////////

    /** The accessible context property. */
    protected AccessibleContext accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this JFrame.
     * For JFrames, the AccessibleContext takes the form of an
     * AccessibleJFrame.
     * A new AccessibleJFrame instance is created if necessary.
     *
     * <p>
     * 获取与此JFrame关联的AccessibleContext。对于JFrames,AccessibleContext采用AccessibleJFrame的形式。
     * 如果需要,将创建一个新的AccessibleJFrame实例。
     * 
     * 
     * @return an AccessibleJFrame that serves as the
     *         AccessibleContext of this JFrame
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJFrame();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JFrame</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to frame user-interface
     * elements.
     * <p>
     *  此类实现<code> JFrame </code>类的辅助功能支持。它提供了适用于框架用户界面元素的Java辅助功能API的实现。
     * 
     */
    protected class AccessibleJFrame extends AccessibleAWTFrame {

        // AccessibleContext methods
        /**
         * Get the accessible name of this object.
         *
         * <p>
         *  获取此对象的可访问名称。
         * 
         * 
         * @return the localized name of the object -- can be null if this
         * object does not have a name
         */
        public String getAccessibleName() {
            if (accessibleName != null) {
                return accessibleName;
            } else {
                if (getTitle() == null) {
                    return super.getAccessibleName();
                } else {
                    return getTitle();
                }
            }
        }

        /**
         * Get the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();

            if (isResizable()) {
                states.add(AccessibleState.RESIZABLE);
            }
            if (getFocusOwner() != null) {
                states.add(AccessibleState.ACTIVE);
            }
            // FIXME:  [[[WDW - should also return ICONIFIED and ICONIFIABLE
            // if we can ever figure these out]]]
            return states;
        }
    } // inner class AccessibleJFrame
}
