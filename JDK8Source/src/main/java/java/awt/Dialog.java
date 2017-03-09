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

import java.awt.peer.DialogPeer;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.accessibility.*;
import sun.awt.AppContext;
import sun.awt.SunToolkit;
import sun.awt.PeerEvent;
import sun.awt.util.IdentityArrayList;
import sun.awt.util.IdentityLinkedList;
import sun.security.util.SecurityConstants;
import java.security.AccessControlException;

/**
 * A Dialog is a top-level window with a title and a border
 * that is typically used to take some form of input from the user.
 *
 * The size of the dialog includes any area designated for the
 * border.  The dimensions of the border area can be obtained
 * using the <code>getInsets</code> method, however, since
 * these dimensions are platform-dependent, a valid insets
 * value cannot be obtained until the dialog is made displayable
 * by either calling <code>pack</code> or <code>show</code>.
 * Since the border area is included in the overall size of the
 * dialog, the border effectively obscures a portion of the dialog,
 * constraining the area available for rendering and/or displaying
 * subcomponents to the rectangle which has an upper-left corner
 * location of <code>(insets.left, insets.top)</code>, and has a size of
 * <code>width - (insets.left + insets.right)</code> by
 * <code>height - (insets.top + insets.bottom)</code>.
 * <p>
 * The default layout for a dialog is <code>BorderLayout</code>.
 * <p>
 * A dialog may have its native decorations (i.e. Frame &amp; Titlebar) turned off
 * with <code>setUndecorated</code>.  This can only be done while the dialog
 * is not {@link Component#isDisplayable() displayable}.
 * <p>
 * A dialog may have another window as its owner when it's constructed.  When
 * the owner window of a visible dialog is minimized, the dialog will
 * automatically be hidden from the user. When the owner window is subsequently
 * restored, the dialog is made visible to the user again.
 * <p>
 * In a multi-screen environment, you can create a <code>Dialog</code>
 * on a different screen device than its owner.  See {@link java.awt.Frame} for
 * more information.
 * <p>
 * A dialog can be either modeless (the default) or modal.  A modal
 * dialog is one which blocks input to some other top-level windows
 * in the application, except for any windows created with the dialog
 * as their owner. See <a href="doc-files/Modality.html">AWT Modality</a>
 * specification for details.
 * <p>
 * Dialogs are capable of generating the following
 * <code>WindowEvents</code>:
 * <code>WindowOpened</code>, <code>WindowClosing</code>,
 * <code>WindowClosed</code>, <code>WindowActivated</code>,
 * <code>WindowDeactivated</code>, <code>WindowGainedFocus</code>,
 * <code>WindowLostFocus</code>.
 *
 * <p>
 *  对话框是具有标题和边框的顶级窗口,通常用于从用户采取某种形式的输入。
 * 
 *  对话框的大小包括为边框指定的任何区域。
 * 边界区域的尺寸可以使用<code> getInsets </code>方法获得,但是,因为这些维度是平台相关的,所以在通过调用<code>可以显示对话框之前,不能获得有效的insets值。
 *  pack </code>或<code> show </code>。
 * 由于边界区域被包括在对话的整体大小中,所以边界有效地遮蔽对话的一部分,限制可用于呈现和/或显示具有<code>的左上角位置的矩形的子组件的区域, (insets.left,insets.top)</code>
 * ,并且<code> height  - (insets.top + insets)的大小为<code> width  - (insets.left + insets.right)</code>底部)</code>
 * 。
 *  pack </code>或<code> show </code>。
 * <p>
 *  对话框的默认布局为<code> BorderLayout </code>。
 * <p>
 *  对话框可以通过<code> setUndecorated </code>关闭其本机装饰(即Frame&amp; Titlebar)。
 * 这只能在对话框不是{@link Component#isDisplayable()displayable}时才能完成。
 * <p>
 * 构建对话框时,可能有另一个窗口作为其所有者。当可见对话框的所有者窗口最小化时,对话框将自动从用户隐藏。当随后恢复所有者窗口时,对话框再次对用户可见。
 * <p>
 *  在多屏环境中,您可以在与其所有者不同的屏幕设备上创建<code> Dialog </code>。有关详细信息,请参阅{@link java.awt.Frame}。
 * <p>
 *  对话框可以是无模式(默认)或模态。模态对话框阻止对应用程序中某些其他顶级窗口的输入,除了使用对话框作为其所有者创建的任何窗口。
 * 有关详细信息,请参见<a href="doc-files/Modality.html"> AWT模式</a>规范。
 * <p>
 *  对话框能够生成以下<code> WindowEvents </code>：<code> WindowOpened </code>,<code> WindowClosing </code>,<code>
 *  WindowClosed </code> >,<code> WindowDeactivated </code>,<code> WindowGainedFocus </code>,<code> Wind
 * owLostFocus </code>。
 * 
 * 
 * @see WindowEvent
 * @see Window#addWindowListener
 *
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public class Dialog extends Window {

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * A dialog's resizable property. Will be true
     * if the Dialog is to be resizable, otherwise
     * it will be false.
     *
     * <p>
     *  对话框的可调整大小的属性。如果对话框是可调整大小,将是真的,否则将是假的。
     * 
     * 
     * @serial
     * @see #setResizable(boolean)
     */
    boolean resizable = true;


    /**
     * This field indicates whether the dialog is undecorated.
     * This property can only be changed while the dialog is not displayable.
     * <code>undecorated</code> will be true if the dialog is
     * undecorated, otherwise it will be false.
     *
     * <p>
     *  此字段指示对话框是否未装饰。只有在对话框不可显示时,才能更改此属性。如果对话框未装饰,<code> undecorated </code>将为true,否则为false。
     * 
     * 
     * @serial
     * @see #setUndecorated(boolean)
     * @see #isUndecorated()
     * @see Component#isDisplayable()
     * @since 1.4
     */
    boolean undecorated = false;

    private transient boolean initialized = false;

    /**
     * Modal dialogs block all input to some top-level windows.
     * Whether a particular window is blocked depends on dialog's type
     * of modality; this is called the "scope of blocking". The
     * <code>ModalityType</code> enum specifies modal types and their
     * associated scopes.
     *
     * <p>
     * 模态对话框阻止所有输入到一些顶级窗口。特定窗口是否被阻塞取决于对话框的模态类型;这被称为"阻塞范围"。 <code> ModalityType </code>枚举指定模态类型及其关联的范围。
     * 
     * 
     * @see Dialog#getModalityType
     * @see Dialog#setModalityType
     * @see Toolkit#isModalityTypeSupported
     *
     * @since 1.6
     */
    public static enum ModalityType {
        /**
         * <code>MODELESS</code> dialog doesn't block any top-level windows.
         * <p>
         *  <code> MODELESS </code>对话框不会阻止任何顶级窗口。
         * 
         */
        MODELESS,
        /**
         * A <code>DOCUMENT_MODAL</code> dialog blocks input to all top-level windows
         * from the same document except those from its own child hierarchy.
         * A document is a top-level window without an owner. It may contain child
         * windows that, together with the top-level window are treated as a single
         * solid document. Since every top-level window must belong to some
         * document, its root can be found as the top-nearest window without an owner.
         * <p>
         *  <code> DOCUMENT_MODAL </code>对话框阻止对来自同一文档的所有顶级窗口的输入,除了来自其自己的子层次结构的窗口。文档是没有所有者的顶级窗口。
         * 它可能包含子窗口,与顶层窗口一起被视为单个实体文档。由于每个顶级窗口必须属于某个文档,所以它的根可以被找到为没有所有者的最近的窗口。
         * 
         */
        DOCUMENT_MODAL,
        /**
         * An <code>APPLICATION_MODAL</code> dialog blocks all top-level windows
         * from the same Java application except those from its own child hierarchy.
         * If there are several applets launched in a browser, they can be
         * treated either as separate applications or a single one. This behavior
         * is implementation-dependent.
         * <p>
         *  一个<code> APPLICATION_MODAL </code>对话框阻止来自同一Java应用程序的所有顶级窗口,除了来自它自己的子层次结构的窗口。
         * 如果在浏览器中启动了几个applet,它们可以作为单独的应用程序或单个应用程序。此行为是实现相关的。
         * 
         */
        APPLICATION_MODAL,
        /**
         * A <code>TOOLKIT_MODAL</code> dialog blocks all top-level windows run
         * from the same toolkit except those from its own child hierarchy. If there
         * are several applets launched in a browser, all of them run with the same
         * toolkit; thus, a toolkit-modal dialog displayed by an applet may affect
         * other applets and all windows of the browser instance which embeds the
         * Java runtime environment for this toolkit.
         * Special <code>AWTPermission</code> "toolkitModality" must be granted to use
         * toolkit-modal dialogs. If a <code>TOOLKIT_MODAL</code> dialog is being created
         * and this permission is not granted, a <code>SecurityException</code> will be
         * thrown, and no dialog will be created. If a modality type is being changed
         * to <code>TOOLKIT_MODAL</code> and this permission is not granted, a
         * <code>SecurityException</code> will be thrown, and the modality type will
         * be left unchanged.
         * <p>
         * 一个<code> TOOLKIT_MODAL </code>对话框阻止所有顶级窗口从相同的工具包运行,除了它们自己的子层次结构。
         * 如果在浏览器中启动了几个applet,它们都使用相同的工具包运行;因此,由小应用程序显示的工具包模态对话可以影响嵌入用于该工具包的Java运行时环境的浏览器实例的其他小应用程序和所有窗口。
         * 必须授予特殊<code> AWTPermission </code>"toolkitModality"才能使用工具包模式对话框。
         * 如果正在创建<code> TOOLKIT_MODAL </code>对话框,并且未授予此权限,则会抛出<code> SecurityException </code>,并且不会创建任何对话框。
         * 如果模态类型更改为<code> TOOLKIT_MODAL </code>,并且未授予此权限,则会抛出<code> SecurityException </code>,并且模态类型将保持不变。
         * 
         */
        TOOLKIT_MODAL
    };

    /**
     * Default modality type for modal dialogs. The default modality type is
     * <code>APPLICATION_MODAL</code>. Calling the oldstyle <code>setModal(true)</code>
     * is equal to <code>setModalityType(DEFAULT_MODALITY_TYPE)</code>.
     *
     * <p>
     *  模态对话框的默认模态类型。默认模态类型为<code> APPLICATION_MODAL </code>。
     * 调用oldstyle <code> setModal(true)</code>等于<code> setModalityType(DEFAULT_MODALITY_TYPE)</code>。
     * 
     * 
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     *
     * @since 1.6
     */
    public final static ModalityType DEFAULT_MODALITY_TYPE = ModalityType.APPLICATION_MODAL;

    /**
     * True if this dialog is modal, false is the dialog is modeless.
     * A modal dialog blocks user input to some application top-level
     * windows. This field is kept only for backwards compatibility. Use the
     * {@link Dialog.ModalityType ModalityType} enum instead.
     *
     * <p>
     *  如果此对话框是模态,则为true,false为对话框为无模式。模态对话框阻止用户对某些应用程序顶级窗口的输入。此字段仅用于向后兼容性。
     * 请改用{@link Dialog.ModalityType ModalityType}枚举。
     * 
     * 
     * @serial
     *
     * @see #isModal
     * @see #setModal
     * @see #getModalityType
     * @see #setModalityType
     * @see ModalityType
     * @see ModalityType#MODELESS
     * @see #DEFAULT_MODALITY_TYPE
     */
    boolean modal;

    /**
     * Modality type of this dialog. If the dialog's modality type is not
     * {@link Dialog.ModalityType#MODELESS ModalityType.MODELESS}, it blocks all
     * user input to some application top-level windows.
     *
     * <p>
     * 此对话框的模态类型。
     * 如果对话框的模态类型不是{@link Dialog.ModalityType#MODELESS ModalityType.MODELESS},它会阻止所有用户对某些应用程序顶级窗口的输入。
     * 
     * 
     * @serial
     *
     * @see ModalityType
     * @see #getModalityType
     * @see #setModalityType
     *
     * @since 1.6
     */
    ModalityType modalityType;

    /**
     * Any top-level window can be marked not to be blocked by modal
     * dialogs. This is called "modal exclusion". This enum specifies
     * the possible modal exclusion types.
     *
     * <p>
     *  任何顶级窗口都可以被标记为不被模态对话框阻止。这被称为"模态排除"。此枚举指定可能的模态排除类型。
     * 
     * 
     * @see Window#getModalExclusionType
     * @see Window#setModalExclusionType
     * @see Toolkit#isModalExclusionTypeSupported
     *
     * @since 1.6
     */
    public static enum ModalExclusionType {
        /**
         * No modal exclusion.
         * <p>
         *  没有模态排除。
         * 
         */
        NO_EXCLUDE,
        /**
         * <code>APPLICATION_EXCLUDE</code> indicates that a top-level window
         * won't be blocked by any application-modal dialogs. Also, it isn't
         * blocked by document-modal dialogs from outside of its child hierarchy.
         * <p>
         *  <code> APPLICATION_EXCLUDE </code>表示顶层窗口不会被任何应用程序模式对话框阻止。此外,它不会被来自其子层次结构外部的文档模式对话框阻止。
         * 
         */
        APPLICATION_EXCLUDE,
        /**
         * <code>TOOLKIT_EXCLUDE</code> indicates that a top-level window
         * won't be blocked by  application-modal or toolkit-modal dialogs. Also,
         * it isn't blocked by document-modal dialogs from outside of its
         * child hierarchy.
         * The "toolkitModality" <code>AWTPermission</code> must be granted
         * for this exclusion. If an exclusion property is being changed to
         * <code>TOOLKIT_EXCLUDE</code> and this permission is not granted, a
         * <code>SecurityEcxeption</code> will be thrown, and the exclusion
         * property will be left unchanged.
         * <p>
         *  <code> TOOLKIT_EXCLUDE </code>表示顶层窗口不会被应用程序模式或工具包模式对话框阻止。此外,它不会被来自其子层次结构外部的文档模式对话框阻止。
         * 必须为此排除授予"toolkitModality"<code> AWTPermission </code>。
         * 如果将排除属性更改为<code> TOOLKIT_EXCLUDE </code>并且未授予此权限,则会抛出<code> SecurityEcxeption </code>,并且排除属性将保持不变。
         * 
         */
        TOOLKIT_EXCLUDE
    };

    /* operations with this list should be synchronized on tree lock*/
    transient static IdentityArrayList<Dialog> modalDialogs = new IdentityArrayList<Dialog>();

    transient IdentityArrayList<Window> blockedWindows = new IdentityArrayList<Window>();

    /**
     * Specifies the title of the Dialog.
     * This field can be null.
     *
     * <p>
     *  指定对话框的标题。此字段可以为null。
     * 
     * 
     * @serial
     * @see #getTitle()
     * @see #setTitle(String)
     */
    String title;

    private transient ModalEventFilter modalFilter;
    private transient volatile SecondaryLoop secondaryLoop;

    /*
     * Indicates that this dialog is being hidden. This flag is set to true at
     * the beginning of hide() and to false at the end of hide().
     *
     * <p>
     *  表示此对话框正在被隐藏。此标志在hide()开始时设置为true,在hide()结束时设置为false。
     * 
     * 
     * @see #hide()
     * @see #hideAndDisposePreHandler()
     * @see #hideAndDisposeHandler()
     * @see #shouldBlock()
     */
    transient volatile boolean isInHide = false;

    /*
     * Indicates that this dialog is being disposed. This flag is set to true at
     * the beginning of doDispose() and to false at the end of doDispose().
     *
     * <p>
     *  表示正在处理此对话框。此标志在doDispose()的开头设置为true,在doDispose()的末尾设置为false。
     * 
     * 
     * @see #hide()
     * @see #hideAndDisposePreHandler()
     * @see #hideAndDisposeHandler()
     * @see #doDispose()
     */
    transient volatile boolean isInDispose = false;

    private static final String base = "dialog";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 5920926903803293709L;

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code> with
     * the specified owner <code>Frame</code> and an empty title.
     *
     * <p>
     * 用指定的所有者<code> Frame </code>构造一个初始不可见的无模式<code>对话框</code>和一个空标题。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if
     *     this dialog has no owner
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     */
     public Dialog(Frame owner) {
         this(owner, "", false);
     }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the specified
     * owner <code>Frame</code> and modality and an empty title.
     *
     * <p>
     *  构造与指定所有者<code>框架</code>和模态的初始不可见的<code>对话框</code>和空标题。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if
     *     this dialog has no owner
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If <code>false</code>, the dialog is <code>MODELESS</code>;
     *     if <code>true</code>, the modality type property is set to
     *     <code>DEFAULT_MODALITY_TYPE</code>
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
     public Dialog(Frame owner, boolean modal) {
         this(owner, "", modal);
     }

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code> with
     * the specified owner <code>Frame</code> and title.
     *
     * <p>
     *  用指定的所有者<code> Frame </code>和标题构造一个初始不可见的无模式<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if
     *     this dialog has no owner
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @exception IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     */
     public Dialog(Frame owner, String title) {
         this(owner, title, false);
     }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Frame</code>, title and modality.
     *
     * <p>
     *  用指定的所有者<code>框架</code>,标题和模态构造一个初始不可见的<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if
     *     this dialog has no owner
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If <code>false</code>, the dialog is <code>MODELESS</code>;
     *     if <code>true</code>, the modality type property is set to
     *     <code>DEFAULT_MODALITY_TYPE</code>
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     */
     public Dialog(Frame owner, String title, boolean modal) {
         this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
     }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the specified owner
     * <code>Frame</code>, title, modality, and <code>GraphicsConfiguration</code>.
     * <p>
     *  用指定的所有者<code> Frame </code>,标题,模式和<code> GraphicsConfiguration </code>构造一个初始不可见的<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if this dialog
     *     has no owner
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If <code>false</code>, the dialog is <code>MODELESS</code>;
     *     if <code>true</code>, the modality type property is set to
     *     <code>DEFAULT_MODALITY_TYPE</code>
     * @param gc the <code>GraphicsConfiguration</code> of the target screen device;
     *     if <code>null</code>, the default system <code>GraphicsConfiguration</code>
     *     is assumed
     * @exception java.lang.IllegalArgumentException if <code>gc</code>
     *     is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @since 1.4
     */
     public Dialog(Frame owner, String title, boolean modal,
                   GraphicsConfiguration gc) {
         this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, gc);
     }

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code> with
     * the specified owner <code>Dialog</code> and an empty title.
     *
     * <p>
     *  用指定的所有者<code> Dialog </code>构造一个初始不可见的无模式<code>对话框</code>和一个空标题。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if this
     *     dialog has no owner
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.2
     */
     public Dialog(Dialog owner) {
         this(owner, "", false);
     }

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code>
     * with the specified owner <code>Dialog</code> and title.
     *
     * <p>
     *  用指定的所有者<code> Dialog </code>和标题构造一个初始不可见的无模式<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if this
     *     has no owner
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @since 1.2
     */
     public Dialog(Dialog owner, String title) {
         this(owner, title, false);
     }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Dialog</code>, title, and modality.
     *
     * <p>
     *  用指定的所有者<code>对话框</code>,标题和模态构造一个初始不可见的<code>对话框</code>
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if this
     *     dialog has no owner
     * @param title the title of the dialog or <code>null</code> if this
     *     dialog has no title
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If <code>false</code>, the dialog is <code>MODELESS</code>;
     *     if <code>true</code>, the modality type property is set to
     *     <code>DEFAULT_MODALITY_TYPE</code>
     * @exception IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     *
     * @since 1.2
     */
     public Dialog(Dialog owner, String title, boolean modal) {
         this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
     }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Dialog</code>, title, modality and
     * <code>GraphicsConfiguration</code>.
     *
     * <p>
     *  用指定的所有者<code> Dialog </code>,标题,模态和<code> GraphicsConfiguration </code>构造一个初始不可见的<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog or <code>null</code> if this
     *     dialog has no owner
     * @param title the title of the dialog or <code>null</code> if this
     *     dialog has no title
     * @param modal specifies whether dialog blocks user input to other top-level
     *     windows when shown. If <code>false</code>, the dialog is <code>MODELESS</code>;
     *     if <code>true</code>, the modality type property is set to
     *     <code>DEFAULT_MODALITY_TYPE</code>
     * @param gc the <code>GraphicsConfiguration</code> of the target screen device;
     *     if <code>null</code>, the default system <code>GraphicsConfiguration</code>
     *     is assumed
     * @exception java.lang.IllegalArgumentException if <code>gc</code>
     *    is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog.ModalityType#MODELESS
     * @see java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     *
     * @since 1.4
     */
     public Dialog(Dialog owner, String title, boolean modal,
                   GraphicsConfiguration gc) {
         this(owner, title, modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS, gc);
     }

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code> with the
     * specified owner <code>Window</code> and an empty title.
     *
     * <p>
     *  用指定的所有者<code> Window </code>构造一个初始不可见的无模式<code>对话框</code>和一个空标题。
     * 
     * 
     * @param owner the owner of the dialog. The owner must be an instance of
     *     {@link java.awt.Dialog Dialog}, {@link java.awt.Frame Frame}, any
     *     of their descendents or <code>null</code>
     *
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>
     *     is not an instance of {@link java.awt.Dialog Dialog} or {@link
     *     java.awt.Frame Frame}
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     *
     * @since 1.6
     */
    public Dialog(Window owner) {
        this(owner, "", ModalityType.MODELESS);
    }

    /**
     * Constructs an initially invisible, modeless <code>Dialog</code> with
     * the specified owner <code>Window</code> and title.
     *
     * <p>
     *  用指定的所有者<code> Window </code>和标题构造一个初始不可见的无模式<code>对话框</code>。
     * 
     * 
     * @param owner the owner of the dialog. The owner must be an instance of
     *    {@link java.awt.Dialog Dialog}, {@link java.awt.Frame Frame}, any
     *    of their descendents or <code>null</code>
     * @param title the title of the dialog or <code>null</code> if this dialog
     *    has no title
     *
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>
     *    is not an instance of {@link java.awt.Dialog Dialog} or {@link
     *    java.awt.Frame Frame}
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     *
     * @since 1.6
     */
    public Dialog(Window owner, String title) {
        this(owner, title, ModalityType.MODELESS);
    }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Window</code> and modality and an empty title.
     *
     * <p>
     * 使用指定的所有者<code> Window </code>和模式构建一个初始不可见的<code>对话框</code>和一个空标题。
     * 
     * 
     * @param owner the owner of the dialog. The owner must be an instance of
     *    {@link java.awt.Dialog Dialog}, {@link java.awt.Frame Frame}, any
     *    of their descendents or <code>null</code>
     * @param modalityType specifies whether dialog blocks input to other
     *    windows when shown. <code>null</code> value and unsupported modality
     *    types are equivalent to <code>MODELESS</code>
     *
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>
     *    is not an instance of {@link java.awt.Dialog Dialog} or {@link
     *    java.awt.Frame Frame}
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *    <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *    <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @exception SecurityException if the calling thread does not have permission
     *    to create modal dialogs with the given <code>modalityType</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Toolkit#isModalityTypeSupported
     *
     * @since 1.6
     */
    public Dialog(Window owner, ModalityType modalityType) {
        this(owner, "", modalityType);
    }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Window</code>, title and modality.
     *
     * <p>
     *  构造与指定所有者<code> Window </code>,标题和模态的初始不可见的<code>对话框</code>
     * 
     * 
     * @param owner the owner of the dialog. The owner must be an instance of
     *     {@link java.awt.Dialog Dialog}, {@link java.awt.Frame Frame}, any
     *     of their descendents or <code>null</code>
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @param modalityType specifies whether dialog blocks input to other
     *    windows when shown. <code>null</code> value and unsupported modality
     *    types are equivalent to <code>MODELESS</code>
     *
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>
     *     is not an instance of {@link java.awt.Dialog Dialog} or {@link
     *     java.awt.Frame Frame}
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>'s
     *     <code>GraphicsConfiguration</code> is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @exception SecurityException if the calling thread does not have permission
     *     to create modal dialogs with the given <code>modalityType</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Toolkit#isModalityTypeSupported
     *
     * @since 1.6
     */
    public Dialog(Window owner, String title, ModalityType modalityType) {
        super(owner);

        if ((owner != null) &&
            !(owner instanceof Frame) &&
            !(owner instanceof Dialog))
        {
            throw new IllegalArgumentException("Wrong parent window");
        }

        this.title = title;
        setModalityType(modalityType);
        SunToolkit.checkAndSetPolicy(this);
        initialized = true;
    }

    /**
     * Constructs an initially invisible <code>Dialog</code> with the
     * specified owner <code>Window</code>, title, modality and
     * <code>GraphicsConfiguration</code>.
     *
     * <p>
     *  用指定的所有者<code> Window </code>,标题,模态和<code> GraphicsConfiguration </code>构造一个初始不可见的<code> Dialog </code>
     * 。
     * 
     * 
     * @param owner the owner of the dialog. The owner must be an instance of
     *     {@link java.awt.Dialog Dialog}, {@link java.awt.Frame Frame}, any
     *     of their descendents or <code>null</code>
     * @param title the title of the dialog or <code>null</code> if this dialog
     *     has no title
     * @param modalityType specifies whether dialog blocks input to other
     *    windows when shown. <code>null</code> value and unsupported modality
     *    types are equivalent to <code>MODELESS</code>
     * @param gc the <code>GraphicsConfiguration</code> of the target screen device;
     *     if <code>null</code>, the default system <code>GraphicsConfiguration</code>
     *     is assumed
     *
     * @exception java.lang.IllegalArgumentException if the <code>owner</code>
     *     is not an instance of {@link java.awt.Dialog Dialog} or {@link
     *     java.awt.Frame Frame}
     * @exception java.lang.IllegalArgumentException if <code>gc</code>
     *     is not from a screen device
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @exception SecurityException if the calling thread does not have permission
     *     to create modal dialogs with the given <code>modalityType</code>
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#setModal
     * @see java.awt.Dialog#setModalityType
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Toolkit#isModalityTypeSupported
     *
     * @since 1.6
     */
    public Dialog(Window owner, String title, ModalityType modalityType,
                  GraphicsConfiguration gc) {
        super(owner, gc);

        if ((owner != null) &&
            !(owner instanceof Frame) &&
            !(owner instanceof Dialog))
        {
            throw new IllegalArgumentException("wrong owner window");
        }

        this.title = title;
        setModalityType(modalityType);
        SunToolkit.checkAndSetPolicy(this);
        initialized = true;
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Dialog.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Makes this Dialog displayable by connecting it to
     * a native screen resource.  Making a dialog displayable will
     * cause any of its children to be made displayable.
     * This method is called internally by the toolkit and should
     * not be called directly by programs.
     * <p>
     *  将此对话框连接到本机屏幕资源,使其显示。使对话框可显示将使其任何子对象可显示。此方法由工具包在内部调用,不应由程序直接调用。
     * 
     * 
     * @see Component#isDisplayable
     * @see #removeNotify
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (parent != null && parent.getPeer() == null) {
                parent.addNotify();
            }

            if (peer == null) {
                peer = getToolkit().createDialog(this);
            }
            super.addNotify();
        }
    }

    /**
     * Indicates whether the dialog is modal.
     * <p>
     * This method is obsolete and is kept for backwards compatibility only.
     * Use {@link #getModalityType getModalityType()} instead.
     *
     * <p>
     *  指示对话框是否为模态。
     * <p>
     *  此方法已过时,仅用于向后兼容。请改用{@link #getModalityType getModalityType()}。
     * 
     * 
     * @return    <code>true</code> if this dialog window is modal;
     *            <code>false</code> otherwise
     *
     * @see       java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see       java.awt.Dialog.ModalityType#MODELESS
     * @see       java.awt.Dialog#setModal
     * @see       java.awt.Dialog#getModalityType
     * @see       java.awt.Dialog#setModalityType
     */
    public boolean isModal() {
        return isModal_NoClientCode();
    }
    final boolean isModal_NoClientCode() {
        return modalityType != ModalityType.MODELESS;
    }

    /**
     * Specifies whether this dialog should be modal.
     * <p>
     * This method is obsolete and is kept for backwards compatibility only.
     * Use {@link #setModalityType setModalityType()} instead.
     * <p>
     * Note: changing modality of the visible dialog may have no effect
     * until it is hidden and then shown again.
     *
     * <p>
     *  指定此对话框是否应为模态。
     * <p>
     *  此方法已过时,仅用于向后兼容。请改用{@link #setModalityType setModalityType()}。
     * <p>
     *  注意：更改可见对话框的模式可能没有效果,直到它被隐藏,然后再次显示。
     * 
     * 
     * @param modal specifies whether dialog blocks input to other windows
     *     when shown; calling to <code>setModal(true)</code> is equivalent to
     *     <code>setModalityType(Dialog.DEFAULT_MODALITY_TYPE)</code>, and
     *     calling to <code>setModal(false)</code> is equvivalent to
     *     <code>setModalityType(Dialog.ModalityType.MODELESS)</code>
     *
     * @see       java.awt.Dialog#DEFAULT_MODALITY_TYPE
     * @see       java.awt.Dialog.ModalityType#MODELESS
     * @see       java.awt.Dialog#isModal
     * @see       java.awt.Dialog#getModalityType
     * @see       java.awt.Dialog#setModalityType
     *
     * @since     1.1
     */
    public void setModal(boolean modal) {
        this.modal = modal;
        setModalityType(modal ? DEFAULT_MODALITY_TYPE : ModalityType.MODELESS);
    }

    /**
     * Returns the modality type of this dialog.
     *
     * <p>
     *  返回此对话框的模态类型。
     * 
     * 
     * @return modality type of this dialog
     *
     * @see java.awt.Dialog#setModalityType
     *
     * @since 1.6
     */
    public ModalityType getModalityType() {
        return modalityType;
    }

    /**
     * Sets the modality type for this dialog. See {@link
     * java.awt.Dialog.ModalityType ModalityType} for possible modality types.
     * <p>
     * If the given modality type is not supported, <code>MODELESS</code>
     * is used. You may want to call <code>getModalityType()</code> after calling
     * this method to ensure that the modality type has been set.
     * <p>
     * Note: changing modality of the visible dialog may have no effect
     * until it is hidden and then shown again.
     *
     * <p>
     *  设置此对话框的模态类型。有关可能的模态类型,请参阅{@link java.awt.Dialog.ModalityType ModalityType}。
     * <p>
     * 如果不支持给定的模态类型,则使用<code> MODELESS </code>。在调用此方法后,您可能需要调用<code> getModalityType()</code>以确保模态类型已设置。
     * <p>
     *  注意：更改可见对话框的模式可能没有效果,直到它被隐藏,然后再次显示。
     * 
     * 
     * @param type specifies whether dialog blocks input to other
     *     windows when shown. <code>null</code> value and unsupported modality
     *     types are equivalent to <code>MODELESS</code>
     * @exception SecurityException if the calling thread does not have permission
     *     to create modal dialogs with the given <code>modalityType</code>
     *
     * @see       java.awt.Dialog#getModalityType
     * @see       java.awt.Toolkit#isModalityTypeSupported
     *
     * @since     1.6
     */
    public void setModalityType(ModalityType type) {
        if (type == null) {
            type = Dialog.ModalityType.MODELESS;
        }
        if (!Toolkit.getDefaultToolkit().isModalityTypeSupported(type)) {
            type = Dialog.ModalityType.MODELESS;
        }
        if (modalityType == type) {
            return;
        }

        checkModalityPermission(type);

        modalityType = type;
        modal = (modalityType != ModalityType.MODELESS);
    }

    /**
     * Gets the title of the dialog. The title is displayed in the
     * dialog's border.
     * <p>
     *  获取对话框的标题。标题显示在对话框的边框中。
     * 
     * 
     * @return    the title of this dialog window. The title may be
     *            <code>null</code>.
     * @see       java.awt.Dialog#setTitle
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the Dialog.
     * <p>
     *  设置对话框的标题。
     * 
     * 
     * @param title the title displayed in the dialog's border;
         * a null value results in an empty title
     * @see #getTitle
     */
    public void setTitle(String title) {
        String oldTitle = this.title;

        synchronized(this) {
            this.title = title;
            DialogPeer peer = (DialogPeer)this.peer;
            if (peer != null) {
                peer.setTitle(title);
            }
        }
        firePropertyChange("title", oldTitle, title);
    }

    /**
    /* <p>
    /* 
     * @return true if we actually showed, false if we just called toFront()
     */
    private boolean conditionalShow(Component toFocus, AtomicLong time) {
        boolean retval;

        closeSplashScreen();

        synchronized (getTreeLock()) {
            if (peer == null) {
                addNotify();
            }
            validateUnconditionally();
            if (visible) {
                toFront();
                retval = false;
            } else {
                visible = retval = true;

                // check if this dialog should be modal blocked BEFORE calling peer.show(),
                // otherwise, a pair of FOCUS_GAINED and FOCUS_LOST may be mistakenly
                // generated for the dialog
                if (!isModal()) {
                    checkShouldBeBlocked(this);
                } else {
                    modalDialogs.add(this);
                    modalShow();
                }

                if (toFocus != null && time != null && isFocusable() &&
                    isEnabled() && !isModalBlocked()) {
                    // keep the KeyEvents from being dispatched
                    // until the focus has been transfered
                    time.set(Toolkit.getEventQueue().getMostRecentKeyEventTime());
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().
                        enqueueKeyEvents(time.get(), toFocus);
                }

                // This call is required as the show() method of the Dialog class
                // does not invoke the super.show(). So wried... :(
                mixOnShowing();

                peer.setVisible(true); // now guaranteed never to block
                if (isModalBlocked()) {
                    modalBlocker.toFront();
                }

                setLocationByPlatform(false);
                for (int i = 0; i < ownedWindowList.size(); i++) {
                    Window child = ownedWindowList.elementAt(i).get();
                    if ((child != null) && child.showWithParent) {
                        child.show();
                        child.showWithParent = false;
                    }       // endif
                }   // endfor
                Window.updateChildFocusableWindowState(this);

                createHierarchyEvents(HierarchyEvent.HIERARCHY_CHANGED,
                                      this, parent,
                                      HierarchyEvent.SHOWING_CHANGED,
                                      Toolkit.enabledOnToolkit(AWTEvent.HIERARCHY_EVENT_MASK));
                if (componentListener != null ||
                        (eventMask & AWTEvent.COMPONENT_EVENT_MASK) != 0 ||
                        Toolkit.enabledOnToolkit(AWTEvent.COMPONENT_EVENT_MASK)) {
                    ComponentEvent e =
                        new ComponentEvent(this, ComponentEvent.COMPONENT_SHOWN);
                    Toolkit.getEventQueue().postEvent(e);
                }
            }
        }

        if (retval && (state & OPENED) == 0) {
            postWindowEvent(WindowEvent.WINDOW_OPENED);
            state |= OPENED;
        }

        return retval;
    }

    /**
     * Shows or hides this {@code Dialog} depending on the value of parameter
     * {@code b}.
     * <p>
     *  根据参数{@code b}的值显示或隐藏此{@code对话框}。
     * 
     * 
     * @param b if {@code true}, makes the {@code Dialog} visible,
     * otherwise hides the {@code Dialog}.
     * If the dialog and/or its owner
     * are not yet displayable, both are made displayable.  The
     * dialog will be validated prior to being made visible.
     * If {@code false}, hides the {@code Dialog} and then causes {@code setVisible(true)}
     * to return if it is currently blocked.
     * <p>
     * <b>Notes for modal dialogs</b>.
     * <ul>
     * <li>{@code setVisible(true)}:  If the dialog is not already
     * visible, this call will not return until the dialog is
     * hidden by calling {@code setVisible(false)} or
     * {@code dispose}.
     * <li>{@code setVisible(false)}:  Hides the dialog and then
     * returns on {@code setVisible(true)} if it is currently blocked.
     * <li>It is OK to call this method from the event dispatching
     * thread because the toolkit ensures that other events are
     * not blocked while this method is blocked.
     * </ul>
     * @see java.awt.Window#setVisible
     * @see java.awt.Window#dispose
     * @see java.awt.Component#isDisplayable
     * @see java.awt.Component#validate
     * @see java.awt.Dialog#isModal
     */
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

   /**
     * Makes the {@code Dialog} visible. If the dialog and/or its owner
     * are not yet displayable, both are made displayable.  The
     * dialog will be validated prior to being made visible.
     * If the dialog is already visible, this will bring the dialog
     * to the front.
     * <p>
     * If the dialog is modal and is not already visible, this call
     * will not return until the dialog is hidden by calling hide or
     * dispose. It is permissible to show modal dialogs from the event
     * dispatching thread because the toolkit will ensure that another
     * event pump runs while the one which invoked this method is blocked.
     * <p>
     *  使{@code对话框}可见。如果对话框和/或其所有者不可显示,则两者都是可显示的。对话框将在被显示之前被验证。如果对话框已经可见,这将使对话框在前面。
     * <p>
     *  如果对话框是模态的并且不可见,则在调用hide或dispose隐藏对话框之前,此调用不会返回。允许从事件分派线程显示模式对话框,因为工具包将确保另一个事件泵运行,而调用此方法的对象被阻止。
     * 
     * 
     * @see Component#hide
     * @see Component#isDisplayable
     * @see Component#validate
     * @see #isModal
     * @see Window#setVisible(boolean)
     * @deprecated As of JDK version 1.5, replaced by
     * {@link #setVisible(boolean) setVisible(boolean)}.
     */
    @Deprecated
    public void show() {
        if (!initialized) {
            throw new IllegalStateException("The dialog component " +
                "has not been initialized properly");
        }

        beforeFirstShow = false;
        if (!isModal()) {
            conditionalShow(null, null);
        } else {
            AppContext showAppContext = AppContext.getAppContext();

            AtomicLong time = new AtomicLong();
            Component predictedFocusOwner = null;
            try {
                predictedFocusOwner = getMostRecentFocusOwner();
                if (conditionalShow(predictedFocusOwner, time)) {
                    modalFilter = ModalEventFilter.createFilterForDialog(this);
                    final Conditional cond = new Conditional() {
                        @Override
                        public boolean evaluate() {
                            return windowClosingException == null;
                        }
                    };

                    // if this dialog is toolkit-modal, the filter should be added
                    // to all EDTs (for all AppContexts)
                    if (modalityType == ModalityType.TOOLKIT_MODAL) {
                        Iterator<AppContext> it = AppContext.getAppContexts().iterator();
                        while (it.hasNext()) {
                            AppContext appContext = it.next();
                            if (appContext == showAppContext) {
                                continue;
                            }
                            EventQueue eventQueue = (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY);
                            // it may occur that EDT for appContext hasn't been started yet, so
                            // we post an empty invocation event to trigger EDT initialization
                            Runnable createEDT = new Runnable() {
                                public void run() {};
                            };
                            eventQueue.postEvent(new InvocationEvent(this, createEDT));
                            EventDispatchThread edt = eventQueue.getDispatchThread();
                            edt.addEventFilter(modalFilter);
                        }
                    }

                    modalityPushed();
                    try {
                        final EventQueue eventQueue = AccessController.doPrivileged(
                            new PrivilegedAction<EventQueue>() {
                                public EventQueue run() {
                                    return Toolkit.getDefaultToolkit().getSystemEventQueue();
                                }
                        });
                        secondaryLoop = eventQueue.createSecondaryLoop(cond, modalFilter, 0);
                        if (!secondaryLoop.enter()) {
                            secondaryLoop = null;
                        }
                    } finally {
                        modalityPopped();
                    }

                    // if this dialog is toolkit-modal, its filter must be removed
                    // from all EDTs (for all AppContexts)
                    if (modalityType == ModalityType.TOOLKIT_MODAL) {
                        Iterator<AppContext> it = AppContext.getAppContexts().iterator();
                        while (it.hasNext()) {
                            AppContext appContext = it.next();
                            if (appContext == showAppContext) {
                                continue;
                            }
                            EventQueue eventQueue = (EventQueue)appContext.get(AppContext.EVENT_QUEUE_KEY);
                            EventDispatchThread edt = eventQueue.getDispatchThread();
                            edt.removeEventFilter(modalFilter);
                        }
                    }

                    if (windowClosingException != null) {
                        windowClosingException.fillInStackTrace();
                        throw windowClosingException;
                    }
                }
            } finally {
                if (predictedFocusOwner != null) {
                    // Restore normal key event dispatching
                    KeyboardFocusManager.getCurrentKeyboardFocusManager().
                        dequeueKeyEvents(time.get(), predictedFocusOwner);
                }
            }
        }
    }

    final void modalityPushed() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        if (tk instanceof SunToolkit) {
            SunToolkit stk = (SunToolkit)tk;
            stk.notifyModalityPushed(this);
        }
    }

    final void modalityPopped() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        if (tk instanceof SunToolkit) {
            SunToolkit stk = (SunToolkit)tk;
            stk.notifyModalityPopped(this);
        }
    }

    void interruptBlocking() {
        if (isModal()) {
            disposeImpl();
        } else if (windowClosingException != null) {
            windowClosingException.fillInStackTrace();
            windowClosingException.printStackTrace();
            windowClosingException = null;
        }
    }

    private void hideAndDisposePreHandler() {
        isInHide = true;
        synchronized (getTreeLock()) {
            if (secondaryLoop != null) {
                modalHide();
                // dialog can be shown and then disposed before its
                // modal filter is created
                if (modalFilter != null) {
                    modalFilter.disable();
                }
                modalDialogs.remove(this);
            }
        }
    }
    private void hideAndDisposeHandler() {
        if (secondaryLoop != null) {
            secondaryLoop.exit();
            secondaryLoop = null;
        }
        isInHide = false;
    }

    /**
     * Hides the Dialog and then causes {@code show} to return if it is currently
     * blocked.
     * <p>
     *  隐藏对话框,然后导致{@code show}返回,如果它当前被阻止。
     * 
     * 
     * @see Window#show
     * @see Window#dispose
     * @see Window#setVisible(boolean)
     * @deprecated As of JDK version 1.5, replaced by
     * {@link #setVisible(boolean) setVisible(boolean)}.
     */
    @Deprecated
    public void hide() {
        hideAndDisposePreHandler();
        super.hide();
        // fix for 5048370: if hide() is called from super.doDispose(), then
        // hideAndDisposeHandler() should not be called here as it will be called
        // at the end of doDispose()
        if (!isInDispose) {
            hideAndDisposeHandler();
        }
    }

    /**
     * Disposes the Dialog and then causes show() to return if it is currently
     * blocked.
     * <p>
     *  处理对话框,然后如果它当前被阻止,则使show()返回。
     * 
     */
    void doDispose() {
        // fix for 5048370: set isInDispose flag to true to prevent calling
        // to hideAndDisposeHandler() from hide()
        isInDispose = true;
        super.doDispose();
        hideAndDisposeHandler();
        isInDispose = false;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If this dialog is modal and blocks some windows, then all of them are
     * also sent to the back to keep them below the blocking dialog.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  如果此对话框是模态的并且阻止某些窗口,则所有这些窗口也被发送到后面以使它们低于阻塞对话框。
     * 
     * 
     * @see java.awt.Window#toBack
     */
    public void toBack() {
        super.toBack();
        if (visible) {
            synchronized (getTreeLock()) {
                for (Window w : blockedWindows) {
                    w.toBack_NoClientCode();
                }
            }
        }
    }

    /**
     * Indicates whether this dialog is resizable by the user.
     * By default, all dialogs are initially resizable.
     * <p>
     *  指示此对话框是否可由用户调整大小。默认情况下,所有对话框最初都可以调整大小。
     * 
     * 
     * @return    <code>true</code> if the user can resize the dialog;
     *            <code>false</code> otherwise.
     * @see       java.awt.Dialog#setResizable
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets whether this dialog is resizable by the user.
     * <p>
     * 设置此对话框是否可由用户调整大小。
     * 
     * 
     * @param     resizable <code>true</code> if the user can
     *                 resize this dialog; <code>false</code> otherwise.
     * @see       java.awt.Dialog#isResizable
     */
    public void setResizable(boolean resizable) {
        boolean testvalid = false;

        synchronized (this) {
            this.resizable = resizable;
            DialogPeer peer = (DialogPeer)this.peer;
            if (peer != null) {
                peer.setResizable(resizable);
                testvalid = true;
            }
        }

        // On some platforms, changing the resizable state affects
        // the insets of the Dialog. If we could, we'd call invalidate()
        // from the peer, but we need to guarantee that we're not holding
        // the Dialog lock when we call invalidate().
        if (testvalid) {
            invalidateIfValid();
        }
    }


    /**
     * Disables or enables decorations for this dialog.
     * <p>
     * This method can only be called while the dialog is not displayable. To
     * make this dialog decorated, it must be opaque and have the default shape,
     * otherwise the {@code IllegalComponentStateException} will be thrown.
     * Refer to {@link Window#setShape}, {@link Window#setOpacity} and {@link
     * Window#setBackground} for details
     *
     * <p>
     *  禁用或启用此对话框的装饰。
     * <p>
     *  此方法只能在对话框无法显示时调用。要使此对话框装饰,它必须是不透明的,并具有默认形状,否则将抛出{@code IllegalComponentStateException}。
     * 有关详情,请参阅{@link Window#setShape},{@link Window#setOpacity}和{@link Window#setBackground}。
     * 
     * 
     * @param  undecorated {@code true} if no dialog decorations are to be
     *         enabled; {@code false} if dialog decorations are to be enabled
     *
     * @throws IllegalComponentStateException if the dialog is displayable
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and this dialog does not have the default shape
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and this dialog opacity is less than {@code 1.0f}
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and the alpha value of this dialog background
     *      color is less than {@code 1.0f}
     *
     * @see    #isUndecorated
     * @see    Component#isDisplayable
     * @see    Window#getShape
     * @see    Window#getOpacity
     * @see    Window#getBackground
     *
     * @since 1.4
     */
    public void setUndecorated(boolean undecorated) {
        /* Make sure we don't run in the middle of peer creation.*/
        synchronized (getTreeLock()) {
            if (isDisplayable()) {
                throw new IllegalComponentStateException("The dialog is displayable.");
            }
            if (!undecorated) {
                if (getOpacity() < 1.0f) {
                    throw new IllegalComponentStateException("The dialog is not opaque");
                }
                if (getShape() != null) {
                    throw new IllegalComponentStateException("The dialog does not have a default shape");
                }
                Color bg = getBackground();
                if ((bg != null) && (bg.getAlpha() < 255)) {
                    throw new IllegalComponentStateException("The dialog background color is not opaque");
                }
            }
            this.undecorated = undecorated;
        }
    }

    /**
     * Indicates whether this dialog is undecorated.
     * By default, all dialogs are initially decorated.
     * <p>
     *  指示此对话框是否未装饰。默认情况下,所有对话框都是最初装饰的。
     * 
     * 
     * @return    <code>true</code> if dialog is undecorated;
     *                        <code>false</code> otherwise.
     * @see       java.awt.Dialog#setUndecorated
     * @since 1.4
     */
    public boolean isUndecorated() {
        return undecorated;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void setOpacity(float opacity) {
        synchronized (getTreeLock()) {
            if ((opacity < 1.0f) && !isUndecorated()) {
                throw new IllegalComponentStateException("The dialog is decorated");
            }
            super.setOpacity(opacity);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void setShape(Shape shape) {
        synchronized (getTreeLock()) {
            if ((shape != null) && !isUndecorated()) {
                throw new IllegalComponentStateException("The dialog is decorated");
            }
            super.setShape(shape);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    @Override
    public void setBackground(Color bgColor) {
        synchronized (getTreeLock()) {
            if ((bgColor != null) && (bgColor.getAlpha() < 255) && !isUndecorated()) {
                throw new IllegalComponentStateException("The dialog is decorated");
            }
            super.setBackground(bgColor);
        }
    }

    /**
     * Returns a string representing the state of this dialog. This
     * method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  返回一个表示此对话框状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return    the parameter string of this dialog window.
     */
    protected String paramString() {
        String str = super.paramString() + "," + modalityType;
        if (title != null) {
            str += ",title=" + title;
        }
        return str;
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    /*
     * --- Modality support ---
     *
     * <p>
     *  ---模态支持---
     * 
     */

    /*
     * This method is called only for modal dialogs.
     *
     * Goes through the list of all visible top-level windows and
     * divide them into three distinct groups: blockers of this dialog,
     * blocked by this dialog and all others. Then blocks this dialog
     * by first met dialog from the first group (if any) and blocks all
     * the windows from the second group.
     * <p>
     *  此方法仅用于模态对话框。
     * 
     *  浏览所有可见顶级窗口的列表,并将其分为三个不同的组：此对话框的阻止程序,此对话框和所有其他阻止。然后通过第一组的第一个会话对话框(如果有)阻止此对话框,并阻止来自第二组的所有窗口。
     * 
     */
    void modalShow() {
        // find all the dialogs that block this one
        IdentityArrayList<Dialog> blockers = new IdentityArrayList<Dialog>();
        for (Dialog d : modalDialogs) {
            if (d.shouldBlock(this)) {
                Window w = d;
                while ((w != null) && (w != this)) {
                    w = w.getOwner_NoClientCode();
                }
                if ((w == this) || !shouldBlock(d) || (modalityType.compareTo(d.getModalityType()) < 0)) {
                    blockers.add(d);
                }
            }
        }

        // add all blockers' blockers to blockers :)
        for (int i = 0; i < blockers.size(); i++) {
            Dialog blocker = blockers.get(i);
            if (blocker.isModalBlocked()) {
                Dialog blockerBlocker = blocker.getModalBlocker();
                if (!blockers.contains(blockerBlocker)) {
                    blockers.add(i + 1, blockerBlocker);
                }
            }
        }

        if (blockers.size() > 0) {
            blockers.get(0).blockWindow(this);
        }

        // find all windows from blockers' hierarchies
        IdentityArrayList<Window> blockersHierarchies = new IdentityArrayList<Window>(blockers);
        int k = 0;
        while (k < blockersHierarchies.size()) {
            Window w = blockersHierarchies.get(k);
            Window[] ownedWindows = w.getOwnedWindows_NoClientCode();
            for (Window win : ownedWindows) {
                blockersHierarchies.add(win);
            }
            k++;
        }

        java.util.List<Window> toBlock = new IdentityLinkedList<Window>();
        // block all windows from scope of blocking except from blockers' hierarchies
        IdentityArrayList<Window> unblockedWindows = Window.getAllUnblockedWindows();
        for (Window w : unblockedWindows) {
            if (shouldBlock(w) && !blockersHierarchies.contains(w)) {
                if ((w instanceof Dialog) && ((Dialog)w).isModal_NoClientCode()) {
                    Dialog wd = (Dialog)w;
                    if (wd.shouldBlock(this) && (modalDialogs.indexOf(wd) > modalDialogs.indexOf(this))) {
                        continue;
                    }
                }
                toBlock.add(w);
            }
        }
        blockWindows(toBlock);

        if (!isModalBlocked()) {
            updateChildrenBlocking();
        }
    }

    /*
     * This method is called only for modal dialogs.
     *
     * Unblocks all the windows blocked by this modal dialog. After
     * each of them has been unblocked, it is checked to be blocked by
     * any other modal dialogs.
     * <p>
     *  此方法仅用于模态对话框。
     * 
     *  取消阻止此模态对话框阻止的所有窗口。在它们中的每一个被解除阻塞之后,检查它是否被任何其它模态对话阻塞。
     * 
     */
    void modalHide() {
        // we should unblock all the windows first...
        IdentityArrayList<Window> save = new IdentityArrayList<Window>();
        int blockedWindowsCount = blockedWindows.size();
        for (int i = 0; i < blockedWindowsCount; i++) {
            Window w = blockedWindows.get(0);
            save.add(w);
            unblockWindow(w); // also removes w from blockedWindows
        }
        // ... and only after that check if they should be blocked
        // by another dialogs
        for (int i = 0; i < blockedWindowsCount; i++) {
            Window w = save.get(i);
            if ((w instanceof Dialog) && ((Dialog)w).isModal_NoClientCode()) {
                Dialog d = (Dialog)w;
                d.modalShow();
            } else {
                checkShouldBeBlocked(w);
            }
        }
    }

    /*
     * Returns whether the given top-level window should be blocked by
     * this dialog. Note, that the given window can be also a modal dialog
     * and it should block this dialog, but this method do not take such
     * situations into consideration (such checks are performed in the
     * modalShow() and modalHide() methods).
     *
     * This method should be called on the getTreeLock() lock.
     * <p>
     * 返回此对话框是否应阻止给定的顶级窗口。注意,给定的窗口也可以是一个模态对话框,它应该阻止这个对话框,但是这种方法不考虑这样的情况(这种检查在modalShow()和modalHide()方法中执行)。
     * 
     *  这个方法应该在getTreeLock()锁上调用。
     * 
     */
    boolean shouldBlock(Window w) {
        if (!isVisible_NoClientCode() ||
            (!w.isVisible_NoClientCode() && !w.isInShow) ||
            isInHide ||
            (w == this) ||
            !isModal_NoClientCode())
        {
            return false;
        }
        if ((w instanceof Dialog) && ((Dialog)w).isInHide) {
            return false;
        }
        // check if w is from children hierarchy
        // fix for 6271546: we should also take into consideration child hierarchies
        // of this dialog's blockers
        Window blockerToCheck = this;
        while (blockerToCheck != null) {
            Component c = w;
            while ((c != null) && (c != blockerToCheck)) {
                c = c.getParent_NoClientCode();
            }
            if (c == blockerToCheck) {
                return false;
            }
            blockerToCheck = blockerToCheck.getModalBlocker();
        }
        switch (modalityType) {
            case MODELESS:
                return false;
            case DOCUMENT_MODAL:
                if (w.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE)) {
                    // application- and toolkit-excluded windows are not blocked by
                    // document-modal dialogs from outside their children hierarchy
                    Component c = this;
                    while ((c != null) && (c != w)) {
                        c = c.getParent_NoClientCode();
                    }
                    return c == w;
                } else {
                    return getDocumentRoot() == w.getDocumentRoot();
                }
            case APPLICATION_MODAL:
                return !w.isModalExcluded(ModalExclusionType.APPLICATION_EXCLUDE) &&
                    (appContext == w.appContext);
            case TOOLKIT_MODAL:
                return !w.isModalExcluded(ModalExclusionType.TOOLKIT_EXCLUDE);
        }

        return false;
    }

    /*
     * Adds the given top-level window to the list of blocked
     * windows for this dialog and marks it as modal blocked.
     * If the window is already blocked by some modal dialog,
     * does nothing.
     * <p>
     *  将给定的顶级窗口添加到此对话框的已阻止窗口列表,并将其标记为模态阻止。如果窗口已经被一些模态对话框阻塞,什么也不做。
     * 
     */
    void blockWindow(Window w) {
        if (!w.isModalBlocked()) {
            w.setModalBlocked(this, true, true);
            blockedWindows.add(w);
        }
    }

    void blockWindows(java.util.List<Window> toBlock) {
        DialogPeer dpeer = (DialogPeer)peer;
        if (dpeer == null) {
            return;
        }
        Iterator<Window> it = toBlock.iterator();
        while (it.hasNext()) {
            Window w = it.next();
            if (!w.isModalBlocked()) {
                w.setModalBlocked(this, true, false);
            } else {
                it.remove();
            }
        }
        dpeer.blockWindows(toBlock);
        blockedWindows.addAll(toBlock);
    }

    /*
     * Removes the given top-level window from the list of blocked
     * windows for this dialog and marks it as unblocked. If the
     * window is not modal blocked, does nothing.
     * <p>
     *  从此对话框的已阻止窗口列表中删除给定的顶级窗口,并将其标记为未阻止。如果窗口没有模态阻塞,什么都不做。
     * 
     */
    void unblockWindow(Window w) {
        if (w.isModalBlocked() && blockedWindows.contains(w)) {
            blockedWindows.remove(w);
            w.setModalBlocked(this, false, true);
        }
    }

    /*
     * Checks if any other modal dialog D blocks the given window.
     * If such D exists, mark the window as blocked by D.
     * <p>
     *  检查是否有其他模态对话框D阻塞给定窗口。如果存在这样的D,则将窗口标记为被D阻塞。
     * 
     */
    static void checkShouldBeBlocked(Window w) {
        synchronized (w.getTreeLock()) {
            for (int i = 0; i < modalDialogs.size(); i++) {
                Dialog modalDialog = modalDialogs.get(i);
                if (modalDialog.shouldBlock(w)) {
                    modalDialog.blockWindow(w);
                    break;
                }
            }
        }
    }

    private void checkModalityPermission(ModalityType mt) {
        if (mt == ModalityType.TOOLKIT_MODAL) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPermission(
                    SecurityConstants.AWT.TOOLKIT_MODALITY_PERMISSION
                );
            }
        }
    }

    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException
    {
        GraphicsEnvironment.checkHeadless();

        java.io.ObjectInputStream.GetField fields =
            s.readFields();

        ModalityType localModalityType = (ModalityType)fields.get("modalityType", null);

        try {
            checkModalityPermission(localModalityType);
        } catch (AccessControlException ace) {
            localModalityType = DEFAULT_MODALITY_TYPE;
        }

        // in 1.5 or earlier modalityType was absent, so use "modal" instead
        if (localModalityType == null) {
            this.modal = fields.get("modal", false);
            setModal(modal);
        } else {
            this.modalityType = localModalityType;
        }

        this.resizable = fields.get("resizable", true);
        this.undecorated = fields.get("undecorated", false);
        this.title = (String)fields.get("title", "");

        blockedWindows = new IdentityArrayList<>();

        SunToolkit.checkAndSetPolicy(this);

        initialized = true;

    }

    /*
     * --- Accessibility Support ---
     *
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Gets the AccessibleContext associated with this Dialog.
     * For dialogs, the AccessibleContext takes the form of an
     * AccessibleAWTDialog.
     * A new AccessibleAWTDialog instance is created if necessary.
     *
     * <p>
     *  获取与此对话框相关联的AccessibleContext。对于对话框,AccessibleContext采用AccessibleAWTDialog的形式。
     * 如果需要,将创建一个新的AccessibleAWTDialog实例。
     * 
     * 
     * @return an AccessibleAWTDialog that serves as the
     *         AccessibleContext of this Dialog
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTDialog();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Dialog</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to dialog user-interface elements.
     * <p>
     *  此类实现<code> Dialog </code>类的辅助功能支持。它提供了适用于对话框用户界面元素的Java可访问性API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTDialog extends AccessibleAWTWindow
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 4837230331833941201L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.DIALOG;
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
            if (getFocusOwner() != null) {
                states.add(AccessibleState.ACTIVE);
            }
            if (isModal()) {
                states.add(AccessibleState.MODAL);
            }
            if (isResizable()) {
                states.add(AccessibleState.RESIZABLE);
            }
            return states;
        }

    } // inner class AccessibleAWTDialog
}
