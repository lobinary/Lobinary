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

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.security.AccessController;
import javax.accessibility.*;
import javax.swing.plaf.RootPaneUI;
import java.util.Vector;
import java.io.Serializable;
import javax.swing.border.*;
import sun.awt.AWTAccessor;
import sun.security.action.GetBooleanAction;


/**
 * A lightweight container used behind the scenes by
 * <code>JFrame</code>, <code>JDialog</code>, <code>JWindow</code>,
 * <code>JApplet</code>, and <code>JInternalFrame</code>.
 * For task-oriented information on functionality provided by root panes
 * see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/rootpane.html">How to Use Root Panes</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 * The following image shows the relationships between
 * the classes that use root panes.
 * <p style="text-align:center"><img src="doc-files/JRootPane-1.gif"
 * alt="The following text describes this graphic."
 * HEIGHT=484 WIDTH=629></p>
 * The &quot;heavyweight&quot; components (those that delegate to a peer, or native
 * component on the host system) are shown with a darker, heavier box. The four
 * heavyweight JFC/Swing containers (<code>JFrame</code>, <code>JDialog</code>,
 * <code>JWindow</code>, and <code>JApplet</code>) are
 * shown in relation to the AWT classes they extend.
 * These four components are the
 * only heavyweight containers in the Swing library. The lightweight container
 * <code>JInternalFrame</code> is also shown.
 * All five of these JFC/Swing containers implement the
 * <code>RootPaneContainer</code> interface,
 * and they all delegate their operations to a
 * <code>JRootPane</code> (shown with a little "handle" on top).
 * <blockquote>
 * <b>Note:</b> The <code>JComponent</code> method <code>getRootPane</code>
 * can be used to obtain the <code>JRootPane</code> that contains
 * a given component.
 * </blockquote>
 * <table style="float:right" border="0" summary="layout">
 * <tr>
 * <td align="center">
 * <img src="doc-files/JRootPane-2.gif"
 * alt="The following text describes this graphic." HEIGHT=386 WIDTH=349>
 * </td>
 * </tr>
 * </table>
 * The diagram at right shows the structure of a <code>JRootPane</code>.
 * A <code>JRootpane</code> is made up of a <code>glassPane</code>,
 * an optional <code>menuBar</code>, and a <code>contentPane</code>.
 * (The <code>JLayeredPane</code> manages the <code>menuBar</code>
 * and the <code>contentPane</code>.)
 * The <code>glassPane</code> sits over the top of everything,
 * where it is in a position to intercept mouse movements.
 * Since the <code>glassPane</code> (like the <code>contentPane</code>)
 * can be an arbitrary component, it is also possible to set up the
 * <code>glassPane</code> for drawing. Lines and images on the
 * <code>glassPane</code> can then range
 * over the frames underneath without being limited by their boundaries.
 * <p>
 * Although the <code>menuBar</code> component is optional,
 * the <code>layeredPane</code>, <code>contentPane</code>,
 * and <code>glassPane</code> always exist.
 * Attempting to set them to <code>null</code> generates an exception.
 * <p>
 * To add components to the <code>JRootPane</code> (other than the
 * optional menu bar), you add the object to the <code>contentPane</code>
 * of the <code>JRootPane</code>, like this:
 * <pre>
 *       rootPane.getContentPane().add(child);
 * </pre>
 * The same principle holds true for setting layout managers, removing
 * components, listing children, etc. All these methods are invoked on
 * the <code>contentPane</code> instead of on the <code>JRootPane</code>.
 * <blockquote>
 * <b>Note:</b> The default layout manager for the <code>contentPane</code> is
 *  a <code>BorderLayout</code> manager. However, the <code>JRootPane</code>
 *  uses a custom <code>LayoutManager</code>.
 *  So, when you want to change the layout manager for the components you added
 *  to a <code>JRootPane</code>, be sure to use code like this:
 * <pre>
 *    rootPane.getContentPane().setLayout(new BoxLayout());
 * </pre></blockquote>
 * If a <code>JMenuBar</code> component is set on the <code>JRootPane</code>,
 * it is positioned along the upper edge of the frame.
 * The <code>contentPane</code> is adjusted in location and size to
 * fill the remaining area.
 * (The <code>JMenuBar</code> and the <code>contentPane</code> are added to the
 * <code>layeredPane</code> component at the
 * <code>JLayeredPane.FRAME_CONTENT_LAYER</code> layer.)
 * <p>
 * The <code>layeredPane</code> is the parent of all children in the
 * <code>JRootPane</code> -- both as the direct parent of the menu and
 * the grandparent of all components added to the <code>contentPane</code>.
 * It is an instance of <code>JLayeredPane</code>,
 * which provides the ability to add components at several layers.
 * This capability is very useful when working with menu popups,
 * dialog boxes, and dragging -- situations in which you need to place
 * a component on top of all other components in the pane.
 * <p>
 * The <code>glassPane</code> sits on top of all other components in the
 * <code>JRootPane</code>.
 * That provides a convenient place to draw above all other components,
 * and makes it possible to intercept mouse events,
 * which is useful both for dragging and for drawing.
 * Developers can use <code>setVisible</code> on the <code>glassPane</code>
 * to control when the <code>glassPane</code> displays over the other children.
 * By default the <code>glassPane</code> is not visible.
 * <p>
 * The custom <code>LayoutManager</code> used by <code>JRootPane</code>
 * ensures that:
 * <OL>
 * <LI>The <code>glassPane</code> fills the entire viewable
 *     area of the <code>JRootPane</code> (bounds - insets).
 * <LI>The <code>layeredPane</code> fills the entire viewable area of the
 *     <code>JRootPane</code>. (bounds - insets)
 * <LI>The <code>menuBar</code> is positioned at the upper edge of the
 *     <code>layeredPane</code>.
 * <LI>The <code>contentPane</code> fills the entire viewable area,
 *     minus the <code>menuBar</code>, if present.
 * </OL>
 * Any other views in the <code>JRootPane</code> view hierarchy are ignored.
 * <p>
 * If you replace the <code>LayoutManager</code> of the <code>JRootPane</code>,
 * you are responsible for managing all of these views.
 * So ordinarily you will want to be sure that you
 * change the layout manager for the <code>contentPane</code> rather than
 * for the <code>JRootPane</code> itself!
 * <p>
 * The painting architecture of Swing requires an opaque
 * <code>JComponent</code>
 * to exist in the containment hierarchy above all other components. This is
 * typically provided by way of the content pane. If you replace the content
 * pane, it is recommended that you make the content pane opaque
 * by way of <code>setOpaque(true)</code>. Additionally, if the content pane
 * overrides <code>paintComponent</code>, it
 * will need to completely fill in the background in an opaque color in
 * <code>paintComponent</code>.
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
 *  由<code> JFrame </code>,<code> JDialog </code>,<code> JWindow </code>,<code> JApplet </code>和<code> J
 * InternalFrame </code> / code>。
 * 有关根窗口提供的功能的面向任务的信息,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/rootpane.html">
 * 如何使用根窗格</a>, Java教程</em>中的一个部分。
 * 
 * <p>
 *  下图显示了使用根窗格的类之间的关系。
 *  <p style ="text-align：center"> <img src ="doc-files / JRootPane-1.gif"alt ="以下文字描述此图形。
 *  HEIGHT = 484 WIDTH = 629> </p>"重量级"组件(委派给对等体或主机系统上的本地组件)显示为具有更深,更重的框。
 * 四个重量级JFC / Swing容器(<code> JFrame </code>,<code> JDialog </code>,<code> JWindow </code>和<code> JApplet
 *  </code>它们扩展的AWT类。
 *  HEIGHT = 484 WIDTH = 629> </p>"重量级"组件(委派给对等体或主机系统上的本地组件)显示为具有更深,更重的框。这四个组件是Swing库中唯一的重量级容器。
 * 还显示了轻量级容器<code> JInternalFrame </code>。
 * 所有这五个JFC / Swing容器实现<code> RootPaneContainer </code>接口,并且它们都将它们的操作委托给一个<code> JRootPane </code>(顶部有一个
 * "手柄")。
 * 还显示了轻量级容器<code> JInternalFrame </code>。
 * <blockquote>
 * <b>注意：</b> <code> JComponent </code>方法<code> getRootPane </code>可用于获取包含给定组件的<code> JRootPane </code>。
 * </blockquote>
 * <table style="float:right" border="0" summary="layout">
 * <tr>
 * <td align="center">
 *  <img src ="doc-files / JRootPane-2.gif"
 * alt="The following text describes this graphic." HEIGHT=386 WIDTH=349>
 * </td>
 * </tr>
 * </table>
 *  右图显示了一个<code> JRootPane </code>的结构。
 *  <code> JRootpane </code>由<code> glassPane </code>,可选的<code> menuBar </code>和<code> contentPane </code>
 * 组成。
 *  右图显示了一个<code> JRootPane </code>的结构。
 *  (<code> JLayeredPane </code>管理<code> menuBar </code>和<code> contentPane </code>。
 * )<code> glassPane </code>位于一切的顶部,是能够拦截鼠标移动的位置。
 * 因为<code> glassPane </code>(像<code> contentPane </code>)可以是任意组件,还可以设置用于绘图的<code> glassPane </code>。
 *  <code> glassPane </code>上的线条和图像可以在下面的框架范围内,而不受其边界的限制。
 * <p>
 *  虽然<code> menuBar </code>组件是可选的,但<code> layeredPane </code>,<code> contentPane </code>和<code> glassPa
 * ne </code>尝试将其设置为<code> null </code>会生成异常。
 * <p>
 *  要向<code> JRootPane </code>(可选菜单栏除外)添加组件,请将对象添加到<code> JRootPane </code>的<code> contentPane </code>,如
 * 下所示：。
 * <pre>
 *  rootPane.getContentPane()。add(child);
 * </pre>
 * 同样的原则适用于设置布局管理器,删除组件,列出子项等。所有这些方法都在<code> contentPane </code>而不是在<code> JRootPane </code>上调用。
 * <blockquote>
 *  <b>注意：</b> <code> contentPane </code>的默认布局管理器是一个<code> BorderLayout </code>管理器。
 * 但是,<code> JRootPane </code>使用自定义<code> LayoutManager </code>。
 * 因此,当您想要更改添加到<code> JRootPane </code>的组件的布局管理器时,请务必使用如下代码：。
 * <pre>
 *  rootPane.getContentPane()。
 * setLayout(new BoxLayout()); </pre> </blockquote>如果<code> JMenuBar </code>组件设置在<code> JRootPane </code>
 * 上,则它位于框架的上边缘。
 *  rootPane.getContentPane()。在位置和大小上调整<code> contentPane </code>以填充剩余区域。
 *  (在<code> JLayeredPane.FRAME_CONTENT_LAYER </code>图层的<code> layeredPane </code>组件中添加<code> JMenuBar </code>
 * 和<code> contentPane </code>。
 *  rootPane.getContentPane()。在位置和大小上调整<code> contentPane </code>以填充剩余区域。
 * <p>
 * <code> layeredPane </code>是<code> JRootPane </code>中所有子级的父级 - 它们都作为菜单的直接父级和所有组件的祖父级添加到<code> contentP
 * ane <代码>。
 * 它是<code> JLayeredPane </code>的一个实例,它提供了在几个层添加组件的能力。当使用菜单弹出,对话框和拖动时,此功能非常有用 - 需要将组件放置在窗格中所有其他组件之上的情况。
 * <p>
 *  <code> glassPane </code>位于<code> JRootPane </code>中的所有其他组件之上。
 * 这提供了一个方便的地方绘制高于所有其他组件,并使得有可能拦截鼠标事件,这对于拖动和绘图都有用。
 * 开发人员可以在<code> glassPane </code>上使用<code> setVisible </code>来控制<code> glassPane </code>何时显示在其他子项上。
 * 默认情况下,<code> glassPane </code>不可见。
 * <p>
 *  <code> JRootPane </code>使用的自定义<code> LayoutManager </code>可确保：
 * <OL>
 *  <LI> <code> glassPane </code>填充<code> JRootPane </code>(bounds  -  insets)的整个可见区域。
 *  <LI> <code> layeredPane </code>填充<code> JRootPane </code>的整个可见区域。
 *  (bounds  -  insets)<li> <code> menuBar </code>位于<code> layeredPane </code>的上边缘。
 *  <LI> <code> contentPane </code>填充整个可见区域,减去<code> menuBar </code>(如果存在)。
 * </OL>
 * 将忽略<code> JRootPane </code>视图层次结构中的任何其他视图。
 * <p>
 *  如果替换<code> JRootPane </code>的<code> LayoutManager </code>,您将负责管理所有这些视图。
 * 所以通常你会想要确保你改变布局管理器的<code> contentPane </code>,而不是<code> JRootPane </code>本身！。
 * <p>
 *  Swing的绘制架构需要一个不透明的<code> JComponent </code>存在于所有其他组件之上的包含层次结构中。这通常通过内容窗格提供。
 * 如果替换内容窗格,建议您通过<code> setOpaque(true)</code>使内容窗格不透明。
 * 此外,如果内容窗格覆盖<code> paintComponent </code>,它将需要在<code> paintComponent </code>中以不透明颜色完全填充背景。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see JLayeredPane
 * @see JMenuBar
 * @see JWindow
 * @see JFrame
 * @see JDialog
 * @see JApplet
 * @see JInternalFrame
 * @see JComponent
 * @see BoxLayout
 *
 * @see <a href="http://java.sun.com/products/jfc/tsc/articles/mixing/">
 * Mixing Heavy and Light Components</a>
 *
 * @author David Kloba
 */
/// PENDING(klobad) Who should be opaque in this component?
@SuppressWarnings("serial")
public class JRootPane extends JComponent implements Accessible {

    private static final String uiClassID = "RootPaneUI";

    /**
     * Whether or not we should dump the stack when true double buffering
     * is disabled. Default is false.
     * <p>
     * 当禁用真双缓冲时,是否应该转储堆栈。默认值为false。
     * 
     */
    private static final boolean LOG_DISABLE_TRUE_DOUBLE_BUFFERING;

    /**
     * Whether or not we should ignore requests to disable true double
     * buffering. Default is false.
     * <p>
     *  是否应忽略禁用真正双缓冲的请求。默认值为false。
     * 
     */
    private static final boolean IGNORE_DISABLE_TRUE_DOUBLE_BUFFERING;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should not provide any sort of
     * Window decorations.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>不应提供任何类型的Window装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int NONE = 0;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Frame.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合框架的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int FRAME = 1;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int PLAIN_DIALOG = 2;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to display an informational message.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合用于显示信息性消息的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int INFORMATION_DIALOG = 3;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to display an error message.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合用于显示错误消息的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int ERROR_DIALOG = 4;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to display a <code>JColorChooser</code>.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。
     * 表示<code> JRootPane </code>应提供适合用于显示<code> JColorChooser </code>的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int COLOR_CHOOSER_DIALOG = 5;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to display a <code>JFileChooser</code>.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合用于显示<code> JFileChooser </code>的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int FILE_CHOOSER_DIALOG = 6;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to present a question to the user.
     *
     * <p>
     * 用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合用于向用户提出问题的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int QUESTION_DIALOG = 7;

    /**
     * Constant used for the windowDecorationStyle property. Indicates that
     * the <code>JRootPane</code> should provide decorations appropriate for
     * a Dialog used to display a warning message.
     *
     * <p>
     *  用于windowDecorationStyle属性的常量。表示<code> JRootPane </code>应提供适合用于显示警告消息的对话框的装饰。
     * 
     * 
     * @since 1.4
     */
    public static final int WARNING_DIALOG = 8;

    private int windowDecorationStyle;

    /** The menu bar. */
    protected JMenuBar menuBar;

    /** The content pane. */
    protected Container contentPane;

    /** The layered pane that manages the menu bar and content pane. */
    protected JLayeredPane layeredPane;

    /**
     * The glass pane that overlays the menu bar and content pane,
     *  so it can intercept mouse movements and such.
     * <p>
     *  覆盖菜单栏和内容窗格的玻璃窗格,因此它可以拦截鼠标移动等。
     * 
     */
    protected Component glassPane;
    /**
     * The button that gets activated when the pane has the focus and
     * a UI-specific action like pressing the <b>Enter</b> key occurs.
     * <p>
     *  当窗格具有焦点时激活的按钮和类似按下<b> Enter </b>键的UI特定操作。
     * 
     */
    protected JButton defaultButton;
    /**
     * As of Java 2 platform v1.3 this unusable field is no longer used.
     * To override the default button you should replace the <code>Action</code>
     * in the <code>JRootPane</code>'s <code>ActionMap</code>. Please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这不可用的字段不再使用。
     * 要覆盖默认按钮,您应该替换<code> JRootPane </code>的<code> ActionMap </code>中的<code> Action </code>。
     * 有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     *  @see #defaultButton
     */
    @Deprecated
    protected DefaultAction defaultPressAction;
    /**
     * As of Java 2 platform v1.3 this unusable field is no longer used.
     * To override the default button you should replace the <code>Action</code>
     * in the <code>JRootPane</code>'s <code>ActionMap</code>. Please refer to
     * the key bindings specification for further details.
     *
     * <p>
     *  从Java 2平台v1.3,这不可用的字段不再使用。
     * 要覆盖默认按钮,您应该替换<code> JRootPane </code>的<code> ActionMap </code>中的<code> Action </code>。
     * 有关更多详细信息,请参阅键绑定规范。
     * 
     * 
     * @deprecated As of Java 2 platform v1.3.
     *  @see #defaultButton
     */
    @Deprecated
    protected DefaultAction defaultReleaseAction;

    /**
     * Whether or not true double buffering should be used.  This is typically
     * true, but may be set to false in special situations.  For example,
     * heavy weight popups (backed by a window) set this to false.
     * <p>
     *  是否应使用真双缓冲。这通常是真的,但在特殊情况下可能设置为false。例如,重量大的弹出窗口(由窗口支持)将此值设置为false。
     * 
     */
    boolean useTrueDoubleBuffering = true;

    static {
        LOG_DISABLE_TRUE_DOUBLE_BUFFERING =
            AccessController.doPrivileged(new GetBooleanAction(
                                   "swing.logDoubleBufferingDisable"));
        IGNORE_DISABLE_TRUE_DOUBLE_BUFFERING =
            AccessController.doPrivileged(new GetBooleanAction(
                                   "swing.ignoreDoubleBufferingDisable"));
    }

    /**
     * Creates a <code>JRootPane</code>, setting up its
     * <code>glassPane</code>, <code>layeredPane</code>,
     * and <code>contentPane</code>.
     * <p>
     *  创建<code> JRootPane </code>,设置其<code> glassPane </code>,<code> layeredPane </code>和<code> contentPane
     *  </code>。
     * 
     */
    public JRootPane() {
        setGlassPane(createGlassPane());
        setLayeredPane(createLayeredPane());
        setContentPane(createContentPane());
        setLayout(createRootLayout());
        setDoubleBuffered(true);
        updateUI();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public void setDoubleBuffered(boolean aFlag) {
        if (isDoubleBuffered() != aFlag) {
            super.setDoubleBuffered(aFlag);
            RepaintManager.currentManager(this).doubleBufferingChanged(this);
        }
    }

    /**
     * Returns a constant identifying the type of Window decorations the
     * <code>JRootPane</code> is providing.
     *
     * <p>
     * 返回一个常量,用于标识<code> JRootPane </code>提供的Window装饰类型。
     * 
     * 
     * @return One of <code>NONE</code>, <code>FRAME</code>,
     *        <code>PLAIN_DIALOG</code>, <code>INFORMATION_DIALOG</code>,
     *        <code>ERROR_DIALOG</code>, <code>COLOR_CHOOSER_DIALOG</code>,
     *        <code>FILE_CHOOSER_DIALOG</code>, <code>QUESTION_DIALOG</code> or
     *        <code>WARNING_DIALOG</code>.
     * @see #setWindowDecorationStyle
     * @since 1.4
     */
    public int getWindowDecorationStyle() {
        return windowDecorationStyle;
    }

    /**
     * Sets the type of Window decorations (such as borders, widgets for
     * closing a Window, title ...) the <code>JRootPane</code> should
     * provide. The default is to provide no Window decorations
     * (<code>NONE</code>).
     * <p>
     * This is only a hint, and some look and feels may not support
     * this.
     * This is a bound property.
     *
     * <p>
     *  设置窗口装饰的类型(如边框,用于关闭窗口的窗口小部件,标题...)<code> JRootPane </code>应该提供。默认是不提供窗口装饰(<code> NONE </code>)。
     * <p>
     *  这只是一个提示,一些外观和感觉可能不支持这一点。这是一个bound属性。
     * 
     * 
     * @param windowDecorationStyle Constant identifying Window decorations
     *        to provide.
     * @see JDialog#setDefaultLookAndFeelDecorated
     * @see JFrame#setDefaultLookAndFeelDecorated
     * @see LookAndFeel#getSupportsWindowDecorations
     * @throws IllegalArgumentException if <code>style</code> is
     *        not one of: <code>NONE</code>, <code>FRAME</code>,
     *        <code>PLAIN_DIALOG</code>, <code>INFORMATION_DIALOG</code>,
     *        <code>ERROR_DIALOG</code>, <code>COLOR_CHOOSER_DIALOG</code>,
     *        <code>FILE_CHOOSER_DIALOG</code>, <code>QUESTION_DIALOG</code>, or
     *        <code>WARNING_DIALOG</code>.
     * @since 1.4
     * @beaninfo
     *        bound: true
     *         enum: NONE                   JRootPane.NONE
     *               FRAME                  JRootPane.FRAME
     *               PLAIN_DIALOG           JRootPane.PLAIN_DIALOG
     *               INFORMATION_DIALOG     JRootPane.INFORMATION_DIALOG
     *               ERROR_DIALOG           JRootPane.ERROR_DIALOG
     *               COLOR_CHOOSER_DIALOG   JRootPane.COLOR_CHOOSER_DIALOG
     *               FILE_CHOOSER_DIALOG    JRootPane.FILE_CHOOSER_DIALOG
     *               QUESTION_DIALOG        JRootPane.QUESTION_DIALOG
     *               WARNING_DIALOG         JRootPane.WARNING_DIALOG
     *       expert: true
     *    attribute: visualUpdate true
     *  description: Identifies the type of Window decorations to provide
     */
    public void setWindowDecorationStyle(int windowDecorationStyle) {
        if (windowDecorationStyle < 0 ||
                  windowDecorationStyle > WARNING_DIALOG) {
            throw new IllegalArgumentException("Invalid decoration style");
        }
        int oldWindowDecorationStyle = getWindowDecorationStyle();
        this.windowDecorationStyle = windowDecorationStyle;
        firePropertyChange("windowDecorationStyle",
                            oldWindowDecorationStyle,
                            windowDecorationStyle);
    }

    /**
     * Returns the L&amp;F object that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F对象。
     * 
     * 
     * @return <code>LabelUI</code> object
     * @since 1.3
     */
    public RootPaneUI getUI() {
        return (RootPaneUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the <code>LabelUI</code> L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *      expert: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     * @since 1.3
     */
    public void setUI(RootPaneUI ui) {
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
        setUI((RootPaneUI)UIManager.getUI(this));
    }


    /**
     * Returns a string that specifies the name of the L&amp;F class
     * that renders this component.
     *
     * <p>
     *  返回一个字符串,指定呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "RootPaneUI"
     *
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }

    /**
      * Called by the constructor methods to create the default
      * <code>layeredPane</code>.
      * Bt default it creates a new <code>JLayeredPane</code>.
      * <p>
      *  通过构造方法调用来创建默认的<code> layeredPane </code>。 Bt默认它创建一个新的<code> JLayeredPane </code>。
      * 
      * 
      * @return the default <code>layeredPane</code>
      */
    protected JLayeredPane createLayeredPane() {
        JLayeredPane p = new JLayeredPane();
        p.setName(this.getName()+".layeredPane");
        return p;
    }

    /**
     * Called by the constructor methods to create the default
     * <code>contentPane</code>.
     * By default this method creates a new <code>JComponent</code> add sets a
     * <code>BorderLayout</code> as its <code>LayoutManager</code>.
     * <p>
     *  通过构造方法调用来创建默认的<code> contentPane </code>。
     * 默认情况下,此方法创建一个新的<code> JComponent </code> add将<code> BorderLayout </code>作为其<code> LayoutManager </code>
     * 。
     *  通过构造方法调用来创建默认的<code> contentPane </code>。
     * 
     * 
     * @return the default <code>contentPane</code>
     */
    protected Container createContentPane() {
        JComponent c = new JPanel();
        c.setName(this.getName()+".contentPane");
        c.setLayout(new BorderLayout() {
            /* This BorderLayout subclass maps a null constraint to CENTER.
             * Although the reference BorderLayout also does this, some VMs
             * throw an IllegalArgumentException.
             * <p>
             *  虽然参考BorderLayout也这样做,一些VM抛出IllegalArgumentException。
             * 
             */
            public void addLayoutComponent(Component comp, Object constraints) {
                if (constraints == null) {
                    constraints = BorderLayout.CENTER;
                }
                super.addLayoutComponent(comp, constraints);
            }
        });
        return c;
    }

    /**
      * Called by the constructor methods to create the default
      * <code>glassPane</code>.
      * By default this method creates a new <code>JComponent</code>
      * with visibility set to false.
      * <p>
      *  通过构造函数方法调用来创建默认的<code> glassPane </code>。
      * 默认情况下,此方法创建一个新的<code> JComponent </code>,并将visibility设置为false。
      * 
      * 
      * @return the default <code>glassPane</code>
      */
    protected Component createGlassPane() {
        JComponent c = new JPanel();
        c.setName(this.getName()+".glassPane");
        c.setVisible(false);
        ((JPanel)c).setOpaque(false);
        return c;
    }

    /**
     * Called by the constructor methods to create the default
     * <code>layoutManager</code>.
     * <p>
     *  通过构造方法调用来创建默认的<code> layoutManager </code>。
     * 
     * 
     * @return the default <code>layoutManager</code>.
     */
    protected LayoutManager createRootLayout() {
        return new RootLayout();
    }

    /**
     * Adds or changes the menu bar used in the layered pane.
     * <p>
     *  添加或更改在分层窗格中使用的菜单栏。
     * 
     * 
     * @param menu the <code>JMenuBar</code> to add
     */
    public void setJMenuBar(JMenuBar menu) {
        if(menuBar != null && menuBar.getParent() == layeredPane)
            layeredPane.remove(menuBar);
        menuBar = menu;

        if(menuBar != null)
            layeredPane.add(menuBar, JLayeredPane.FRAME_CONTENT_LAYER);
    }

    /**
     * Specifies the menu bar value.
     * <p>
     *  指定菜单栏值。
     * 
     * 
     * @deprecated As of Swing version 1.0.3
     *  replaced by <code>setJMenuBar(JMenuBar menu)</code>.
     * @param menu the <code>JMenuBar</code> to add.
     */
    @Deprecated
    public void setMenuBar(JMenuBar menu){
        if(menuBar != null && menuBar.getParent() == layeredPane)
            layeredPane.remove(menuBar);
        menuBar = menu;

        if(menuBar != null)
            layeredPane.add(menuBar, JLayeredPane.FRAME_CONTENT_LAYER);
    }

    /**
     * Returns the menu bar from the layered pane.
     * <p>
     * 从分层窗格返回菜单栏。
     * 
     * 
     * @return the <code>JMenuBar</code> used in the pane
     */
    public JMenuBar getJMenuBar() { return menuBar; }

    /**
     * Returns the menu bar value.
     * <p>
     *  返回菜单栏值。
     * 
     * 
     * @deprecated As of Swing version 1.0.3
     *  replaced by <code>getJMenuBar()</code>.
     * @return the <code>JMenuBar</code> used in the pane
     */
    @Deprecated
    public JMenuBar getMenuBar() { return menuBar; }

    /**
     * Sets the content pane -- the container that holds the components
     * parented by the root pane.
     * <p>
     * Swing's painting architecture requires an opaque <code>JComponent</code>
     * in the containment hierarchy. This is typically provided by the
     * content pane. If you replace the content pane it is recommended you
     * replace it with an opaque <code>JComponent</code>.
     *
     * <p>
     *  设置内容窗格 - 容纳由根窗格内置的组件的容器。
     * <p>
     *  Swing的绘制架构需要在包含层次结构中有一个不透明的<code> JComponent </code>。这通常由内容窗格提供。
     * 如果替换内容窗格,建议您将其替换为不透明的<code> JComponent </code>。
     * 
     * 
     * @param content the <code>Container</code> to use for component-contents
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is <code>null</code>
     */
    public void setContentPane(Container content) {
        if(content == null)
            throw new IllegalComponentStateException("contentPane cannot be set to null.");
        if(contentPane != null && contentPane.getParent() == layeredPane)
            layeredPane.remove(contentPane);
        contentPane = content;

        layeredPane.add(contentPane, JLayeredPane.FRAME_CONTENT_LAYER);
    }

    /**
     * Returns the content pane -- the container that holds the components
     * parented by the root pane.
     *
     * <p>
     *  返回内容窗格 - 容纳由根窗格内置的组件的容器。
     * 
     * 
     * @return the <code>Container</code> that holds the component-contents
     */
    public Container getContentPane() { return contentPane; }

// PENDING(klobad) Should this reparent the contentPane and MenuBar?
    /**
     * Sets the layered pane for the root pane. The layered pane
     * typically holds a content pane and an optional <code>JMenuBar</code>.
     *
     * <p>
     *  设置根窗格的分层窗格。分层窗格通常包含内容窗格和可选的<code> JMenuBar </code>。
     * 
     * 
     * @param layered  the <code>JLayeredPane</code> to use
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the layered pane parameter is <code>null</code>
     */
    public void setLayeredPane(JLayeredPane layered) {
        if(layered == null)
            throw new IllegalComponentStateException("layeredPane cannot be set to null.");
        if(layeredPane != null && layeredPane.getParent() == this)
            this.remove(layeredPane);
        layeredPane = layered;

        this.add(layeredPane, -1);
    }
    /**
     * Gets the layered pane used by the root pane. The layered pane
     * typically holds a content pane and an optional <code>JMenuBar</code>.
     *
     * <p>
     *  获取根窗格所使用的分层窗格。分层窗格通常包含内容窗格和可选的<code> JMenuBar </code>。
     * 
     * 
     * @return the <code>JLayeredPane</code> currently in use
     */
    public JLayeredPane getLayeredPane() { return layeredPane; }

    /**
     * Sets a specified <code>Component</code> to be the glass pane for this
     * root pane.  The glass pane should normally be a lightweight,
     * transparent component, because it will be made visible when
     * ever the root pane needs to grab input events.
     * <p>
     * The new glass pane's visibility is changed to match that of
     * the current glass pane.  An implication of this is that care
     * must be taken when you want to replace the glass pane and
     * make it visible.  Either of the following will work:
     * <pre>
     *   root.setGlassPane(newGlassPane);
     *   newGlassPane.setVisible(true);
     * </pre>
     * or:
     * <pre>
     *   root.getGlassPane().setVisible(true);
     *   root.setGlassPane(newGlassPane);
     * </pre>
     *
     * <p>
     *  将指定的<code> Component </code>设置为此根窗格的玻璃窗格。玻璃窗格通常应该是一个轻量级的,透明的组件,因为当根窗格需要抓取输入事件时,它将被显示。
     * <p>
     *  新的玻璃窗格的可见性更改为与当前玻璃窗格的可见性匹配。这意味着,当您想要更换玻璃板并使其可见时,必须小心。以下任一工作原理：
     * <pre>
     *  root.setGlassPane(newGlassPane); newGlassPane.setVisible(true);
     * </pre>
     *  要么：
     * <pre>
     *  root.getGlassPane()。setVisible(true); root.setGlassPane(newGlassPane);
     * </pre>
     * 
     * 
     * @param glass the <code>Component</code> to use as the glass pane
     *              for this <code>JRootPane</code>
     * @exception NullPointerException if the <code>glass</code> parameter is
     *          <code>null</code>
     */
    public void setGlassPane(Component glass) {
        if (glass == null) {
            throw new NullPointerException("glassPane cannot be set to null.");
        }

        AWTAccessor.getComponentAccessor().setMixingCutoutShape(glass,
                new Rectangle());

        boolean visible = false;
        if (glassPane != null && glassPane.getParent() == this) {
            this.remove(glassPane);
            visible = glassPane.isVisible();
        }

        glass.setVisible(visible);
        glassPane = glass;
        this.add(glassPane, 0);
        if (visible) {
            repaint();
        }
    }

    /**
     * Returns the current glass pane for this <code>JRootPane</code>.
     * <p>
     *  返回此<code> JRootPane </code>的当前玻璃窗格。
     * 
     * 
     * @return the current glass pane
     * @see #setGlassPane
     */
    public Component getGlassPane() {
        return glassPane;
    }

    /**
     * If a descendant of this <code>JRootPane</code> calls
     * <code>revalidate</code>, validate from here on down.
     *<p>
     * Deferred requests to layout a component and its descendents again.
     * For example, calls to <code>revalidate</code>, are pushed upwards to
     * either a <code>JRootPane</code> or a <code>JScrollPane</code>
     * because both classes override <code>isValidateRoot</code> to return true.
     *
     * <p>
     * 如果此<code> JRootPane </code>的后代调用<code> revalidate </code>,请从此处开始验证。
     * p>
     *  延迟请求以再次布置组件及其后代。
     * 例如,对<code> revalidate </code>的调用被向上推送到<code> JRootPane </code>或<code> JScrollPane </code>,因为这两个类都覆盖<code>
     *  isValidateRoot </code>返回true。
     *  延迟请求以再次布置组件及其后代。
     * 
     * 
     * @see JComponent#isValidateRoot
     * @see java.awt.Container#isValidateRoot
     * @return true
     */
    @Override
    public boolean isValidateRoot() {
        return true;
    }

    /**
     * The <code>glassPane</code> and <code>contentPane</code>
     * have the same bounds, which means <code>JRootPane</code>
     * does not tiles its children and this should return false.
     * On the other hand, the <code>glassPane</code>
     * is normally not visible, and so this can return true if the
     * <code>glassPane</code> isn't visible. Therefore, the
     * return value here depends upon the visibility of the
     * <code>glassPane</code>.
     *
     * <p>
     *  <code> glassPane </code>和<code> contentPane </code>具有相同的边界,这意味着<code> JRootPane </code>不会对其子元素进行平铺,并
     * 且应该返回false。
     * 另一方面,<code> glassPane </code>通常不可见,因此如果<code> glassPane </code>不可见,则可以返回true。
     * 因此,这里的返回值取决于<code> glassPane </code>的可见性。
     * 
     * 
     * @return true if this component's children don't overlap
     */
    public boolean isOptimizedDrawingEnabled() {
        return !glassPane.isVisible();
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void addNotify() {
        super.addNotify();
        enableEvents(AWTEvent.KEY_EVENT_MASK);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeNotify() {
        super.removeNotify();
    }


    /**
     * Sets the <code>defaultButton</code> property,
     * which determines the current default button for this <code>JRootPane</code>.
     * The default button is the button which will be activated
     * when a UI-defined activation event (typically the <b>Enter</b> key)
     * occurs in the root pane regardless of whether or not the button
     * has keyboard focus (unless there is another component within
     * the root pane which consumes the activation event,
     * such as a <code>JTextPane</code>).
     * For default activation to work, the button must be an enabled
     * descendent of the root pane when activation occurs.
     * To remove a default button from this root pane, set this
     * property to <code>null</code>.
     *
     * <p>
     *  设置<code> defaultButton </code>属性,它确定此<code> JRootPane </code>的当前默认按钮。
     * 默认按钮是当在根窗格中发生UI定义的激活事件(通常为<b> Enter </b>键)时激活的按钮,无论按钮是否具有键盘焦点(除非有另一个在消费激活事件的根窗格内的组件,例如<code> JTextPan
     * e </code>)。
     *  设置<code> defaultButton </code>属性,它确定此<code> JRootPane </code>的当前默认按钮。要使默认激活工作,在激活时,按钮必须是根窗格的已启用子代。
     * 要从此根窗格中删除默认按钮,请将此属性设置为<code> null </code>。
     * 
     * 
     * @see JButton#isDefaultButton
     * @param defaultButton the <code>JButton</code> which is to be the default button
     *
     * @beaninfo
     *  description: The button activated by default in this root pane
     */
    public void setDefaultButton(JButton defaultButton) {
        JButton oldDefault = this.defaultButton;

        if (oldDefault != defaultButton) {
            this.defaultButton = defaultButton;

            if (oldDefault != null) {
                oldDefault.repaint();
            }
            if (defaultButton != null) {
                defaultButton.repaint();
            }
        }

        firePropertyChange("defaultButton", oldDefault, defaultButton);
    }

    /**
     * Returns the value of the <code>defaultButton</code> property.
     * <p>
     * 返回<code> defaultButton </code>属性的值。
     * 
     * 
     * @return the <code>JButton</code> which is currently the default button
     * @see #setDefaultButton
     */
    public JButton getDefaultButton() {
        return defaultButton;
    }

    final void setUseTrueDoubleBuffering(boolean useTrueDoubleBuffering) {
        this.useTrueDoubleBuffering = useTrueDoubleBuffering;
    }

    final boolean getUseTrueDoubleBuffering() {
        return useTrueDoubleBuffering;
    }

    final void disableTrueDoubleBuffering() {
        if (useTrueDoubleBuffering) {
            if (!IGNORE_DISABLE_TRUE_DOUBLE_BUFFERING) {
                if (LOG_DISABLE_TRUE_DOUBLE_BUFFERING) {
                    System.out.println("Disabling true double buffering for " +
                                       this);
                    Thread.dumpStack();
                }
                useTrueDoubleBuffering = false;
                RepaintManager.currentManager(this).
                        doubleBufferingChanged(this);
            }
        }
    }

    @SuppressWarnings("serial")
    static class DefaultAction extends AbstractAction {
        JButton owner;
        JRootPane root;
        boolean press;
        DefaultAction(JRootPane root, boolean press) {
            this.root = root;
            this.press = press;
        }
        public void setOwner(JButton owner) {
            this.owner = owner;
        }
        public void actionPerformed(ActionEvent e) {
            if (owner != null && SwingUtilities.getRootPane(owner) == root) {
                ButtonModel model = owner.getModel();
                if (press) {
                    model.setArmed(true);
                    model.setPressed(true);
                } else {
                    model.setPressed(false);
                }
            }
        }
        public boolean isEnabled() {
            return owner.getModel().isEnabled();
        }
    }


    /**
     * Overridden to enforce the position of the glass component as
     * the zero child.
     *
     * <p>
     *  覆盖以强制玻璃组件的位置为零孩子。
     * 
     * 
     * @param comp the component to be enhanced
     * @param constraints the constraints to be respected
     * @param index the index
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);

        /// We are making sure the glassPane is on top.
        if(glassPane != null
            && glassPane.getParent() == this
            && getComponent(0) != glassPane) {
            add(glassPane, 0);
        }
    }


///////////////////////////////////////////////////////////////////////////////
//// Begin Inner Classes
///////////////////////////////////////////////////////////////////////////////


    /**
     * A custom layout manager that is responsible for the layout of
     * layeredPane, glassPane, and menuBar.
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
     *  一个自定义布局管理器,负责layeredPane,glassPane和menuBar的布局。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class RootLayout implements LayoutManager2, Serializable
    {
        /**
         * Returns the amount of space the layout would like to have.
         *
         * <p>
         *  返回布局想要拥有的空间量。
         * 
         * 
         * @param parent the Container for which this layout manager
         * is being used
         * @return a Dimension object containing the layout's preferred size
         */
        public Dimension preferredLayoutSize(Container parent) {
            Dimension rd, mbd;
            Insets i = getInsets();

            if(contentPane != null) {
                rd = contentPane.getPreferredSize();
            } else {
                rd = parent.getSize();
            }
            if(menuBar != null && menuBar.isVisible()) {
                mbd = menuBar.getPreferredSize();
            } else {
                mbd = new Dimension(0, 0);
            }
            return new Dimension(Math.max(rd.width, mbd.width) + i.left + i.right,
                                        rd.height + mbd.height + i.top + i.bottom);
        }

        /**
         * Returns the minimum amount of space the layout needs.
         *
         * <p>
         *  返回布局需要的最小空间量。
         * 
         * 
         * @param parent the Container for which this layout manager
         * is being used
         * @return a Dimension object containing the layout's minimum size
         */
        public Dimension minimumLayoutSize(Container parent) {
            Dimension rd, mbd;
            Insets i = getInsets();
            if(contentPane != null) {
                rd = contentPane.getMinimumSize();
            } else {
                rd = parent.getSize();
            }
            if(menuBar != null && menuBar.isVisible()) {
                mbd = menuBar.getMinimumSize();
            } else {
                mbd = new Dimension(0, 0);
            }
            return new Dimension(Math.max(rd.width, mbd.width) + i.left + i.right,
                        rd.height + mbd.height + i.top + i.bottom);
        }

        /**
         * Returns the maximum amount of space the layout can use.
         *
         * <p>
         *  返回布局可以使用的最大空间大小。
         * 
         * 
         * @param target the Container for which this layout manager
         * is being used
         * @return a Dimension object containing the layout's maximum size
         */
        public Dimension maximumLayoutSize(Container target) {
            Dimension rd, mbd;
            Insets i = getInsets();
            if(menuBar != null && menuBar.isVisible()) {
                mbd = menuBar.getMaximumSize();
            } else {
                mbd = new Dimension(0, 0);
            }
            if(contentPane != null) {
                rd = contentPane.getMaximumSize();
            } else {
                // This is silly, but should stop an overflow error
                rd = new Dimension(Integer.MAX_VALUE,
                        Integer.MAX_VALUE - i.top - i.bottom - mbd.height - 1);
            }
            return new Dimension(Math.min(rd.width, mbd.width) + i.left + i.right,
                                         rd.height + mbd.height + i.top + i.bottom);
        }

        /**
         * Instructs the layout manager to perform the layout for the specified
         * container.
         *
         * <p>
         *  指示布局管理器执行指定容器的布局。
         * 
         * 
         * @param parent the Container for which this layout manager
         * is being used
         */
        public void layoutContainer(Container parent) {
            Rectangle b = parent.getBounds();
            Insets i = getInsets();
            int contentY = 0;
            int w = b.width - i.right - i.left;
            int h = b.height - i.top - i.bottom;

            if(layeredPane != null) {
                layeredPane.setBounds(i.left, i.top, w, h);
            }
            if(glassPane != null) {
                glassPane.setBounds(i.left, i.top, w, h);
            }
            // Note: This is laying out the children in the layeredPane,
            // technically, these are not our children.
            if(menuBar != null && menuBar.isVisible()) {
                Dimension mbd = menuBar.getPreferredSize();
                menuBar.setBounds(0, 0, w, mbd.height);
                contentY += mbd.height;
            }
            if(contentPane != null) {
                contentPane.setBounds(0, contentY, w, h - contentY);
            }
        }

        public void addLayoutComponent(String name, Component comp) {}
        public void removeLayoutComponent(Component comp) {}
        public void addLayoutComponent(Component comp, Object constraints) {}
        public float getLayoutAlignmentX(Container target) { return 0.0f; }
        public float getLayoutAlignmentY(Container target) { return 0.0f; }
        public void invalidateLayout(Container target) {}
    }

    /**
     * Returns a string representation of this <code>JRootPane</code>.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JRootPane </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JRootPane</code>.
     */
    protected String paramString() {
        return super.paramString();
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the <code>AccessibleContext</code> associated with this
     * <code>JRootPane</code>. For root panes, the
     * <code>AccessibleContext</code> takes the form of an
     * <code>AccessibleJRootPane</code>.
     * A new <code>AccessibleJRootPane</code> instance is created if necessary.
     *
     * <p>
     *  获取与此<code> JRootPane </code>关联的<code> AccessibleContext </code>。
     * 对于根窗格,<code> AccessibleContext </code>采用<code> AccessibleJRootPane </code>的形式。
     * 如果需要,将创建一个新的<code> AccessibleJRootPane </code>实例。
     * 
     * 
     * @return an <code>AccessibleJRootPane</code> that serves as the
     *         <code>AccessibleContext</code> of this <code>JRootPane</code>
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJRootPane();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JRootPane</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to root pane user-interface elements.
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
     * 此类实现<code> JRootPane </code>类的辅助功能支持。它提供了适用于根窗格用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    @SuppressWarnings("serial")
    protected class AccessibleJRootPane extends AccessibleJComponent {
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
            return AccessibleRole.ROOT_PANE;
        }

        /**
         * Returns the number of accessible children of the object.
         *
         * <p>
         *  返回对象的可访问子项数。
         * 
         * 
         * @return the number of accessible children of the object.
         */
        public int getAccessibleChildrenCount() {
            return super.getAccessibleChildrenCount();
        }

        /**
         * Returns the specified Accessible child of the object.  The Accessible
         * children of an Accessible object are zero-based, so the first child
         * of an Accessible child is at index 0, the second child is at index 1,
         * and so on.
         *
         * <p>
         *  返回对象的指定Accessible子项。可访问对象的可访问子对象是基于零的,因此可访问子对象的第一个子对象位于索引0,第二个子对象位于索引1,依此类推。
         * 
         * @param i zero-based index of child
         * @return the Accessible child of the object
         * @see #getAccessibleChildrenCount
         */
        public Accessible getAccessibleChild(int i) {
            return super.getAccessibleChild(i);
        }
    } // inner class AccessibleJRootPane
}
