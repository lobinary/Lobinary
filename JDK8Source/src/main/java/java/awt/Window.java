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

import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.im.InputContext;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.peer.ComponentPeer;
import java.awt.peer.WindowPeer;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.accessibility.*;
import sun.awt.AWTAccessor;
import sun.awt.AppContext;
import sun.awt.CausedFocusEvent;
import sun.awt.SunToolkit;
import sun.awt.util.IdentityArrayList;
import sun.java2d.Disposer;
import sun.java2d.pipe.Region;
import sun.security.action.GetPropertyAction;
import sun.security.util.SecurityConstants;
import sun.util.logging.PlatformLogger;

/**
 * A {@code Window} object is a top-level window with no borders and no
 * menubar.
 * The default layout for a window is {@code BorderLayout}.
 * <p>
 * A window must have either a frame, dialog, or another window defined as its
 * owner when it's constructed.
 * <p>
 * In a multi-screen environment, you can create a {@code Window}
 * on a different screen device by constructing the {@code Window}
 * with {@link #Window(Window, GraphicsConfiguration)}.  The
 * {@code GraphicsConfiguration} object is one of the
 * {@code GraphicsConfiguration} objects of the target screen device.
 * <p>
 * In a virtual device multi-screen environment in which the desktop
 * area could span multiple physical screen devices, the bounds of all
 * configurations are relative to the virtual device coordinate system.
 * The origin of the virtual-coordinate system is at the upper left-hand
 * corner of the primary physical screen.  Depending on the location of
 * the primary screen in the virtual device, negative coordinates are
 * possible, as shown in the following figure.
 * <p>
 * <img src="doc-files/MultiScreen.gif"
 * alt="Diagram shows virtual device containing 4 physical screens. Primary physical screen shows coords (0,0), other screen shows (-80,-100)."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * In such an environment, when calling {@code setLocation},
 * you must pass a virtual coordinate to this method.  Similarly,
 * calling {@code getLocationOnScreen} on a {@code Window} returns
 * virtual device coordinates.  Call the {@code getBounds} method
 * of a {@code GraphicsConfiguration} to find its origin in the virtual
 * coordinate system.
 * <p>
 * The following code sets the location of a {@code Window}
 * at (10, 10) relative to the origin of the physical screen
 * of the corresponding {@code GraphicsConfiguration}.  If the
 * bounds of the {@code GraphicsConfiguration} is not taken
 * into account, the {@code Window} location would be set
 * at (10, 10) relative to the virtual-coordinate system and would appear
 * on the primary physical screen, which might be different from the
 * physical screen of the specified {@code GraphicsConfiguration}.
 *
 * <pre>
 *      Window w = new Window(Window owner, GraphicsConfiguration gc);
 *      Rectangle bounds = gc.getBounds();
 *      w.setLocation(10 + bounds.x, 10 + bounds.y);
 * </pre>
 *
 * <p>
 * Note: the location and size of top-level windows (including
 * {@code Window}s, {@code Frame}s, and {@code Dialog}s)
 * are under the control of the desktop's window management system.
 * Calls to {@code setLocation}, {@code setSize}, and
 * {@code setBounds} are requests (not directives) which are
 * forwarded to the window management system.  Every effort will be
 * made to honor such requests.  However, in some cases the window
 * management system may ignore such requests, or modify the requested
 * geometry in order to place and size the {@code Window} in a way
 * that more closely matches the desktop settings.
 * <p>
 * Due to the asynchronous nature of native event handling, the results
 * returned by {@code getBounds}, {@code getLocation},
 * {@code getLocationOnScreen}, and {@code getSize} might not
 * reflect the actual geometry of the Window on screen until the last
 * request has been processed.  During the processing of subsequent
 * requests these values might change accordingly while the window
 * management system fulfills the requests.
 * <p>
 * An application may set the size and location of an invisible
 * {@code Window} arbitrarily, but the window management system may
 * subsequently change its size and/or location when the
 * {@code Window} is made visible. One or more {@code ComponentEvent}s
 * will be generated to indicate the new geometry.
 * <p>
 * Windows are capable of generating the following WindowEvents:
 * WindowOpened, WindowClosed, WindowGainedFocus, WindowLostFocus.
 *
 * <p>
 *  {@code Window}对象是没有边框和没有菜单的顶级窗口窗口的默认布局是{@code BorderLayout}
 * <p>
 *  在构建窗口时,窗口必须具有框架,对话框或定义为其所有者的另一个窗口
 * <p>
 *  在多屏幕环境中,您可以通过使用{@link #Window(Window,GraphicsConfiguration)构造{@code Window}来在不同的屏幕设备上创建{@code Window}
 * 。
 * {@code GraphicsConfiguration}对象是一个的目标屏幕设备的{@code GraphicsConfiguration}对象。
 * <p>
 * 在虚拟设备多屏幕环境中,其中桌面区域可以跨越多个物理屏幕设备,所有配置的边界相对于虚拟设备坐标系统。虚拟坐标系统的原点在左上角主物理屏幕根据虚拟设备中主屏幕的位置,可能有负坐标,如下图所示
 * <p>
 *  <img src ="doc-files / MultiScreengif"alt ="图表显示包含4个物理屏幕的虚拟设备主物理屏幕显示coords(0,0),其他屏幕显示(-80,-100)
 * style="float:center; margin: 7px 10px;">
 * <p>
 * 在这样的环境中,当调用{@code setLocation}时,必须向这个方法传递虚拟坐标同样,在{@code Window}上调用{@code getLocationOnScreen}返回虚拟设备坐标
 * 调用{@code getBounds}一个{@code GraphicsConfiguration}在虚拟坐标系中找到它的原点。
 * <p>
 * 以下代码设置{@code Window}在(10,10)相对于相应{@code GraphicsConfiguration}的物理屏幕的原点的位置。
 * 如果未考虑{@code GraphicsConfiguration}的边界,{@code Window}位置将被设置为相对于虚拟坐标系统的(10,10),并将出现在主物理屏幕上,这可能不同于指定的{@code GraphicsConfiguration}
 * 的物理屏幕。
 * 以下代码设置{@code Window}在(10,10)相对于相应{@code GraphicsConfiguration}的物理屏幕的原点的位置。
 * 
 * <pre>
 *  Window w = new Window(Window owner,GraphicsConfiguration gc); Rectangle bounds = gcgetBounds(); wset
 * Location(10 + boundsx,10 + boundsy);。
 * </pre>
 * 
 * <p>
 * 注意：顶级窗口(包括{@code Window},{@code Frame}和{@code Dialog})的位置和大小都在桌面窗口管理系统的控制下。
 * 调用{@code setLocation},{@code setSize}和{@code setBounds}是被转发到窗口管理系统的请求(不是指令)。将尽一切努力来满足这样的请求。
 * 然而,在一些情况下,窗口管理系统可以忽略请求或修改请求的几何体,以便以与桌面设置更紧密匹配的方式放置和设置{@code Window}。
 * <p>
 * 由于本机事件处理的异步性质,{@code getBounds},{@code getLocation},{@code getLocationOnScreen}和{@code getSize}返回的结果可
 * 能不会反映屏幕上的窗口的实际几何,直到最后一个请求已经被处理在后续请求的处理期间,这些值可能在窗口管理系统满足请求时相应地改变。
 * <p>
 *  应用程序可以任意设置不可见{@code Window}的大小和位置,但是当{@code Window}变为可见时,窗口管理系统随后可以改变其大小和/或位置一个或多个{@code ComponentEvent}
 * 将生成s以指示新的几何。
 * <p>
 * Windows能够生成以下WindowEvents：WindowOpened,WindowClosed,WindowGainedFocus,WindowLostFocus
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @see WindowEvent
 * @see #addWindowListener
 * @see java.awt.BorderLayout
 * @since       JDK1.0
 */
public class Window extends Container implements Accessible {

    /**
     * Enumeration of available <i>window types</i>.
     *
     * A window type defines the generic visual appearance and behavior of a
     * top-level window. For example, the type may affect the kind of
     * decorations of a decorated {@code Frame} or {@code Dialog} instance.
     * <p>
     * Some platforms may not fully support a certain window type. Depending on
     * the level of support, some properties of the window type may be
     * disobeyed.
     *
     * <p>
     *  可用的<i>窗口类型</i>的枚举
     * 
     *  窗口类型定义顶级窗口的通用可视外观和行为例如,类型可能影响装饰的{@code Frame}或{@code Dialog}实例的装饰类型
     * <p>
     *  某些平台可能不完全支持某种窗口类型根据支持的级别,窗口类型的某些属性可能不符合
     * 
     * 
     * @see   #getType
     * @see   #setType
     * @since 1.7
     */
    public static enum Type {
        /**
         * Represents a <i>normal</i> window.
         *
         * This is the default type for objects of the {@code Window} class or
         * its descendants. Use this type for regular top-level windows.
         * <p>
         *  表示<i>正常</i>窗口
         * 
         *  这是{@code Window}类或其后代的对象的默认类型对于常规顶级窗口使用此类型
         * 
         */
        NORMAL,

        /**
         * Represents a <i>utility</i> window.
         *
         * A utility window is usually a small window such as a toolbar or a
         * palette. The native system may render the window with smaller
         * title-bar if the window is either a {@code Frame} or a {@code
         * Dialog} object, and if it has its decorations enabled.
         * <p>
         *  表示<i>实用程序</i>窗口
         * 
         * 实用程序窗口通常是小窗口,例如工具栏或调色板。如果窗口是{@code Frame}或{@code Dialog}对象,则原生系统可以使用较小的标题栏来呈现窗口,如果它已启用其装饰
         * 
         */
        UTILITY,

        /**
         * Represents a <i>popup</i> window.
         *
         * A popup window is a temporary window such as a drop-down menu or a
         * tooltip. On some platforms, windows of that type may be forcibly
         * made undecorated even if they are instances of the {@code Frame} or
         * {@code Dialog} class, and have decorations enabled.
         * <p>
         *  表示<i>弹出式窗口</i>
         * 
         *  弹出窗口是一个临时窗口,例如下拉菜单或工具提示在某些平台上,即使该类型的窗口是{@code Frame}或{@code Dialog}类的实例,该窗口也可能被强制无序装饰,并启用装饰
         * 
         */
        POPUP
    }

    /**
     * This represents the warning message that is
     * to be displayed in a non secure window. ie :
     * a window that has a security manager installed that denies
     * {@code AWTPermission("showWindowWithoutWarningBanner")}.
     * This message can be displayed anywhere in the window.
     *
     * <p>
     *  这表示要在非安全窗口中显示的警告消息,即：安装了安全管理器的窗口,拒绝{@code AWTPermission("showWindowWithoutWarningBanner")}此消息可以显示在窗口
     * 中的任何位置。
     * 
     * 
     * @serial
     * @see #getWarningString
     */
    String      warningString;

    /**
     * {@code icons} is the graphical way we can
     * represent the frames and dialogs.
     * {@code Window} can't display icon but it's
     * being inherited by owned {@code Dialog}s.
     *
     * <p>
     * {@code icons}是我们可以表示框架和对话框的图形方式{@code Window}无法显示图标,但它被所有的{@code Dialog}继承
     * 
     * 
     * @serial
     * @see #getIconImages
     * @see #setIconImages
     */
    transient java.util.List<Image> icons;

    /**
     * Holds the reference to the component which last had focus in this window
     * before it lost focus.
     * <p>
     *  在失去焦点之前,保持对此窗口中最后焦点的组件的引用
     * 
     */
    private transient Component temporaryLostComponent;

    static boolean systemSyncLWRequests = false;
    boolean     syncLWRequests = false;
    transient boolean beforeFirstShow = true;
    private transient boolean disposing = false;
    transient WindowDisposerRecord disposerRecord = null;

    static final int OPENED = 0x01;

    /**
     * An Integer value representing the Window State.
     *
     * <p>
     *  表示窗口状态的整数值
     * 
     * 
     * @serial
     * @since 1.2
     * @see #show
     */
    int state;

    /**
     * A boolean value representing Window always-on-top state
     * <p>
     *  表示Window始终在顶状态的布尔值
     * 
     * 
     * @since 1.5
     * @serial
     * @see #setAlwaysOnTop
     * @see #isAlwaysOnTop
     */
    private boolean alwaysOnTop;

    /**
     * Contains all the windows that have a peer object associated,
     * i. e. between addNotify() and removeNotify() calls. The list
     * of all Window instances can be obtained from AppContext object.
     *
     * <p>
     *  包含与对等对象关联的所有窗口,在addNotify()和removeNotify()调用之间。所有Window实例的列表可以从AppContext对象
     * 
     * 
     * @since 1.6
     */
    private static final IdentityArrayList<Window> allWindows = new IdentityArrayList<Window>();

    /**
     * A vector containing all the windows this
     * window currently owns.
     * <p>
     *  包含此窗口当前拥有的所有窗口的向量
     * 
     * 
     * @since 1.2
     * @see #getOwnedWindows
     */
    transient Vector<WeakReference<Window>> ownedWindowList =
                                            new Vector<WeakReference<Window>>();

    /*
     * We insert a weak reference into the Vector of all Windows in AppContext
     * instead of 'this' so that garbage collection can still take place
     * correctly.
     * <p>
     *  我们在AppContext中的所有窗口的Vector中插入一个弱引用,而不是"this",这样垃圾回收仍然可以正确地进行
     * 
     */
    private transient WeakReference<Window> weakThis;

    transient boolean showWithParent;

    /**
     * Contains the modal dialog that blocks this window, or null
     * if the window is unblocked.
     *
     * <p>
     * 包含阻止此窗口的模态对话框,如果窗口已解除阻止,则为null
     * 
     * 
     * @since 1.6
     */
    transient Dialog modalBlocker;

    /**
    /* <p>
    /* 
     * @serial
     *
     * @see java.awt.Dialog.ModalExclusionType
     * @see #getModalExclusionType
     * @see #setModalExclusionType
     *
     * @since 1.6
     */
    Dialog.ModalExclusionType modalExclusionType;

    transient WindowListener windowListener;
    transient WindowStateListener windowStateListener;
    transient WindowFocusListener windowFocusListener;

    transient InputContext inputContext;
    private transient Object inputContextLock = new Object();

    /**
     * Unused. Maintained for serialization backward-compatibility.
     *
     * <p>
     *  未使用保持序列化向后兼容性
     * 
     * 
     * @serial
     * @since 1.2
     */
    private FocusManager focusMgr;

    /**
     * Indicates whether this Window can become the focused Window.
     *
     * <p>
     *  指示此窗口是否可以成为关注的窗口
     * 
     * 
     * @serial
     * @see #getFocusableWindowState
     * @see #setFocusableWindowState
     * @since 1.4
     */
    private boolean focusableWindowState = true;

    /**
     * Indicates whether this window should receive focus on
     * subsequently being shown (with a call to {@code setVisible(true)}), or
     * being moved to the front (with a call to {@code toFront()}).
     *
     * <p>
     *  指示此窗口在随后显示时是否应收到焦点(调用{@code setVisible(true)}),或移到前面(调用{@code toFront()})
     * 
     * 
     * @serial
     * @see #setAutoRequestFocus
     * @see #isAutoRequestFocus
     * @since 1.7
     */
    private volatile boolean autoRequestFocus = true;

    /*
     * Indicates that this window is being shown. This flag is set to true at
     * the beginning of show() and to false at the end of show().
     *
     * <p>
     *  表示正在显示此窗口此标志在show()开头设置为true,在show()结束时设置为false,
     * 
     * 
     * @see #show()
     * @see Dialog#shouldBlock
     */
    transient boolean isInShow = false;

    /**
     * The opacity level of the window
     *
     * <p>
     *  窗口的不透明度级别
     * 
     * 
     * @serial
     * @see #setOpacity(float)
     * @see #getOpacity()
     * @since 1.7
     */
    private float opacity = 1.0f;

    /**
     * The shape assigned to this window. This field is set to {@code null} if
     * no shape is set (rectangular window).
     *
     * <p>
     *  分配给此窗口的形状如果未设置形状(矩形窗口),此字段设置为{@code null}
     * 
     * 
     * @serial
     * @see #getShape()
     * @see #setShape(Shape)
     * @since 1.7
     */
    private Shape shape = null;

    private static final String base = "win";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 11 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 4497834738069338734L;

    private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.Window");

    private static final boolean locationByPlatformProp;

    transient boolean isTrayIconWindow = false;

    /**
     * These fields are initialized in the native peer code
     * or via AWTAccessor's WindowAccessor.
     * <p>
     *  这些字段在本地对等体代码或通过AWTAccessor的WindowAccessor初始化
     * 
     */
    private transient volatile int securityWarningWidth = 0;
    private transient volatile int securityWarningHeight = 0;

    /**
     * These fields represent the desired location for the security
     * warning if this window is untrusted.
     * See com.sun.awt.SecurityWarning for more details.
     * <p>
     * 如果此窗口不可信,这些字段表示安全警告的所需位置有关详细信息,请参阅comsunawtSecurityWarning
     * 
     */
    private transient double securityWarningPointX = 2.0;
    private transient double securityWarningPointY = 0.0;
    private transient float securityWarningAlignmentX = RIGHT_ALIGNMENT;
    private transient float securityWarningAlignmentY = TOP_ALIGNMENT;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }

        String s = java.security.AccessController.doPrivileged(
            new GetPropertyAction("java.awt.syncLWRequests"));
        systemSyncLWRequests = (s != null && s.equals("true"));
        s = java.security.AccessController.doPrivileged(
            new GetPropertyAction("java.awt.Window.locationByPlatform"));
        locationByPlatformProp = (s != null && s.equals("true"));
    }

    /**
     * Initialize JNI field and method IDs for fields that may be
       accessed from C.
     * <p>
     *  初始化可从C访问的字段的JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    /**
     * Constructs a new, initially invisible window in default size with the
     * specified {@code GraphicsConfiguration}.
     * <p>
     * If there is a security manager, then it is invoked to check
     * {@code AWTPermission("showWindowWithoutWarningBanner")}
     * to determine whether or not the window must be displayed with
     * a warning banner.
     *
     * <p>
     *  构造一个新的,初始不可见的窗口的默认大小与指定的{@code GraphicsConfiguration}
     * <p>
     *  如果有安全管理器,则调用它来检查{@code AWTPermission("showWindowWithoutWarningBanner")}以确定窗口是否必须显示警告横幅
     * 
     * 
     * @param gc the {@code GraphicsConfiguration} of the target screen
     *     device. If {@code gc} is {@code null}, the system default
     *     {@code GraphicsConfiguration} is assumed
     * @exception IllegalArgumentException if {@code gc}
     *    is not from a screen device
     * @exception HeadlessException when
     *     {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    Window(GraphicsConfiguration gc) {
        init(gc);
    }

    transient Object anchor = new Object();
    static class WindowDisposerRecord implements sun.java2d.DisposerRecord {
        WeakReference<Window> owner;
        final WeakReference<Window> weakThis;
        final WeakReference<AppContext> context;

        WindowDisposerRecord(AppContext context, Window victim) {
            weakThis = victim.weakThis;
            this.context = new WeakReference<AppContext>(context);
        }

        public void updateOwner() {
            Window victim = weakThis.get();
            owner = (victim == null)
                    ? null
                    : new WeakReference<Window>(victim.getOwner());
        }

        public void dispose() {
            if (owner != null) {
                Window parent = owner.get();
                if (parent != null) {
                    parent.removeOwnedWindow(weakThis);
                }
            }
            AppContext ac = context.get();
            if (null != ac) {
                Window.removeFromWindowList(ac, weakThis);
            }
        }
    }

    private GraphicsConfiguration initGC(GraphicsConfiguration gc) {
        GraphicsEnvironment.checkHeadless();

        if (gc == null) {
            gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
                getDefaultScreenDevice().getDefaultConfiguration();
        }
        setGraphicsConfiguration(gc);

        return gc;
    }

    private void init(GraphicsConfiguration gc) {
        GraphicsEnvironment.checkHeadless();

        syncLWRequests = systemSyncLWRequests;

        weakThis = new WeakReference<Window>(this);
        addToWindowList();

        setWarningString();
        this.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        this.visible = false;

        gc = initGC(gc);

        if (gc.getDevice().getType() !=
            GraphicsDevice.TYPE_RASTER_SCREEN) {
            throw new IllegalArgumentException("not a screen device");
        }
        setLayout(new BorderLayout());

        /* offset the initial location with the original of the screen */
        /* and any insets                                              */
        Rectangle screenBounds = gc.getBounds();
        Insets screenInsets = getToolkit().getScreenInsets(gc);
        int x = getX() + screenBounds.x + screenInsets.left;
        int y = getY() + screenBounds.y + screenInsets.top;
        if (x != this.x || y != this.y) {
            setLocation(x, y);
            /* reset after setLocation */
            setLocationByPlatform(locationByPlatformProp);
        }

        modalExclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
        disposerRecord = new WindowDisposerRecord(appContext, this);
        sun.java2d.Disposer.addRecord(anchor, disposerRecord);

        SunToolkit.checkAndSetPolicy(this);
    }

    /**
     * Constructs a new, initially invisible window in the default size.
     * <p>
     * If there is a security manager set, it is invoked to check
     * {@code AWTPermission("showWindowWithoutWarningBanner")}.
     * If that check fails with a {@code SecurityException} then a warning
     * banner is created.
     *
     * <p>
     *  构造一个默认大小的新的,初始不可见的窗口
     * <p>
     * 如果有一个安全管理器设置,它被调用检查{@code AWTPermission("showWindowWithoutWarningBanner")}如果检查失败与{@code SecurityException}
     * ,那么警告横幅创建。
     * 
     * 
     * @exception HeadlessException when
     *     {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    Window() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        init((GraphicsConfiguration)null);
    }

    /**
     * Constructs a new, initially invisible window with the specified
     * {@code Frame} as its owner. The window will not be focusable
     * unless its owner is showing on the screen.
     * <p>
     * If there is a security manager set, it is invoked to check
     * {@code AWTPermission("showWindowWithoutWarningBanner")}.
     * If that check fails with a {@code SecurityException} then a warning
     * banner is created.
     *
     * <p>
     *  构造一个以指定的{@code Frame}作为其所有者的新的,初始不可见的窗口除非窗口的所有者显示在屏幕上,否则窗口将不可被聚焦
     * <p>
     *  如果有一个安全管理器设置,它被调用检查{@code AWTPermission("showWindowWithoutWarningBanner")}如果检查失败与{@code SecurityException}
     * ,那么警告横幅创建。
     * 
     * 
     * @param owner the {@code Frame} to act as owner or {@code null}
     *    if this window has no owner
     * @exception IllegalArgumentException if the {@code owner}'s
     *    {@code GraphicsConfiguration} is not from a screen device
     * @exception HeadlessException when
     *    {@code GraphicsEnvironment.isHeadless} returns {@code true}
     *
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #isShowing
     */
    public Window(Frame owner) {
        this(owner == null ? (GraphicsConfiguration)null :
            owner.getGraphicsConfiguration());
        ownedInit(owner);
    }

    /**
     * Constructs a new, initially invisible window with the specified
     * {@code Window} as its owner. This window will not be focusable
     * unless its nearest owning {@code Frame} or {@code Dialog}
     * is showing on the screen.
     * <p>
     * If there is a security manager set, it is invoked to check
     * {@code AWTPermission("showWindowWithoutWarningBanner")}.
     * If that check fails with a {@code SecurityException} then a
     * warning banner is created.
     *
     * <p>
     *  构造一个以指定的{@code Window}作为其所有者的新的,初始不可见的窗口除非最近拥有的{@code Frame}或{@code Dialog}显示在屏幕上,否则此窗口将不可焦点
     * <p>
     * 如果有一个安全管理器设置,它被调用检查{@code AWTPermission("showWindowWithoutWarningBanner")}如果检查失败与{@code SecurityException}
     * ,那么警告横幅创建。
     * 
     * 
     * @param owner the {@code Window} to act as owner or
     *     {@code null} if this window has no owner
     * @exception IllegalArgumentException if the {@code owner}'s
     *     {@code GraphicsConfiguration} is not from a screen device
     * @exception HeadlessException when
     *     {@code GraphicsEnvironment.isHeadless()} returns
     *     {@code true}
     *
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       #isShowing
     *
     * @since     1.2
     */
    public Window(Window owner) {
        this(owner == null ? (GraphicsConfiguration)null :
            owner.getGraphicsConfiguration());
        ownedInit(owner);
    }

    /**
     * Constructs a new, initially invisible window with the specified owner
     * {@code Window} and a {@code GraphicsConfiguration}
     * of a screen device. The Window will not be focusable unless
     * its nearest owning {@code Frame} or {@code Dialog}
     * is showing on the screen.
     * <p>
     * If there is a security manager set, it is invoked to check
     * {@code AWTPermission("showWindowWithoutWarningBanner")}. If that
     * check fails with a {@code SecurityException} then a warning banner
     * is created.
     *
     * <p>
     *  使用指定的所有者{@code Window}和屏幕设备的{@code GraphicsConfiguration}构造一个新的,初始不可见的窗口除非显示最近拥有的{@code Frame}或{@code Dialog}
     * 窗口,否则窗口将不可对焦屏幕上。
     * <p>
     *  如果有一个安全管理器设置,它被调用检查{@code AWTPermission("showWindowWithoutWarningBanner")}如果检查失败与{@code SecurityException}
     * ,那么警告横幅创建。
     * 
     * 
     * @param owner the window to act as owner or {@code null}
     *     if this window has no owner
     * @param gc the {@code GraphicsConfiguration} of the target
     *     screen device; if {@code gc} is {@code null},
     *     the system default {@code GraphicsConfiguration} is assumed
     * @exception IllegalArgumentException if {@code gc}
     *     is not from a screen device
     * @exception HeadlessException when
     *     {@code GraphicsEnvironment.isHeadless()} returns
     *     {@code true}
     *
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       GraphicsConfiguration#getBounds
     * @see       #isShowing
     * @since     1.3
     */
    public Window(Window owner, GraphicsConfiguration gc) {
        this(gc);
        ownedInit(owner);
    }

    private void ownedInit(Window owner) {
        this.parent = owner;
        if (owner != null) {
            owner.addOwnedWindow(weakThis);
            if (owner.isAlwaysOnTop()) {
                try {
                    setAlwaysOnTop(true);
                } catch (SecurityException ignore) {
                }
            }
        }

        // WindowDisposerRecord requires a proper value of parent field.
        disposerRecord.updateOwner();
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称名称为null时由getName()调用
     * 
     */
    String constructComponentName() {
        synchronized (Window.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Returns the sequence of images to be displayed as the icon for this window.
     * <p>
     * This method returns a copy of the internally stored list, so all operations
     * on the returned object will not affect the window's behavior.
     *
     * <p>
     * 返回要显示为此窗口的图标的图像序列
     * <p>
     *  此方法返回内部存储的列表的副本,所以对返回的对象的所有操作不会影响窗口的行为
     * 
     * 
     * @return    the copy of icon images' list for this window, or
     *            empty list if this window doesn't have icon images.
     * @see       #setIconImages
     * @see       #setIconImage(Image)
     * @since     1.6
     */
    public java.util.List<Image> getIconImages() {
        java.util.List<Image> icons = this.icons;
        if (icons == null || icons.size() == 0) {
            return new ArrayList<Image>();
        }
        return new ArrayList<Image>(icons);
    }

    /**
     * Sets the sequence of images to be displayed as the icon
     * for this window. Subsequent calls to {@code getIconImages} will
     * always return a copy of the {@code icons} list.
     * <p>
     * Depending on the platform capabilities one or several images
     * of different dimensions will be used as the window's icon.
     * <p>
     * The {@code icons} list is scanned for the images of most
     * appropriate dimensions from the beginning. If the list contains
     * several images of the same size, the first will be used.
     * <p>
     * Ownerless windows with no icon specified use platfrom-default icon.
     * The icon of an owned window may be inherited from the owner
     * unless explicitly overridden.
     * Setting the icon to {@code null} or empty list restores
     * the default behavior.
     * <p>
     * Note : Native windowing systems may use different images of differing
     * dimensions to represent a window, depending on the context (e.g.
     * window decoration, window list, taskbar, etc.). They could also use
     * just a single image for all contexts or no image at all.
     *
     * <p>
     *  设置要显示为此窗口的图标的图像序列随后调用{@code getIconImages}将始终返回{@code icons}列表的副本
     * <p>
     *  根据平台功能,一个或多个不同尺寸的图像将被用作窗口的图标
     * <p>
     *  {@code icons}列表将从头开始扫描最合适尺寸的图像。如果列表包含多个相同尺寸的图像,则将使用第一个
     * <p>
     * 没有指定图标的无用窗口使用platfrom-default图标拥有窗口的图标可以从所有者继承,除非显式覆盖将图标设置为{@code null}或空列表恢复默认行为
     * <p>
     *  注意：本地窗口系统可以使用不同尺寸的不同图像来表示窗口,这取决于上下文(例如窗口装饰,窗口列表,任务栏等)。它们也可以仅使用单个图像用于所有上下文,或者根本不使用图像
     * 
     * 
     * @param     icons the list of icon images to be displayed.
     * @see       #getIconImages()
     * @see       #setIconImage(Image)
     * @since     1.6
     */
    public synchronized void setIconImages(java.util.List<? extends Image> icons) {
        this.icons = (icons == null) ? new ArrayList<Image>() :
            new ArrayList<Image>(icons);
        WindowPeer peer = (WindowPeer)this.peer;
        if (peer != null) {
            peer.updateIconImages();
        }
        // Always send a property change event
        firePropertyChange("iconImage", null, null);
    }

    /**
     * Sets the image to be displayed as the icon for this window.
     * <p>
     * This method can be used instead of {@link #setIconImages setIconImages()}
     * to specify a single image as a window's icon.
     * <p>
     * The following statement:
     * <pre>
     *     setIconImage(image);
     * </pre>
     * is equivalent to:
     * <pre>
     *     ArrayList&lt;Image&gt; imageList = new ArrayList&lt;Image&gt;();
     *     imageList.add(image);
     *     setIconImages(imageList);
     * </pre>
     * <p>
     * Note : Native windowing systems may use different images of differing
     * dimensions to represent a window, depending on the context (e.g.
     * window decoration, window list, taskbar, etc.). They could also use
     * just a single image for all contexts or no image at all.
     *
     * <p>
     *  将要显示的图像设置为此窗口的图标
     * <p>
     *  可以使用此方法代替{@link #setIconImages setIconImages()},以将单个图像指定为窗口的图标
     * <p>
     *  下面的语句：
     * <pre>
     *  setIconImage(image);
     * </pre>
     *  等效于：
     * <pre>
     * ArrayList&lt; Image&gt; imageList = new ArrayList&lt; Image&gt;(); ImageListadd(image); setIconImages
     * (imageList);。
     * </pre>
     * <p>
     *  注意：本地窗口系统可以使用不同尺寸的不同图像来表示窗口,这取决于上下文(例如窗口装饰,窗口列表,任务栏等)。它们也可以仅使用单个图像用于所有上下文,或者根本不使用图像
     * 
     * 
     * @param     image the icon image to be displayed.
     * @see       #setIconImages
     * @see       #getIconImages()
     * @since     1.6
     */
    public void setIconImage(Image image) {
        ArrayList<Image> imageList = new ArrayList<Image>();
        if (image != null) {
            imageList.add(image);
        }
        setIconImages(imageList);
    }

    /**
     * Makes this Window displayable by creating the connection to its
     * native screen resource.
     * This method is called internally by the toolkit and should
     * not be called directly by programs.
     * <p>
     *  通过创建与其本机屏幕资源的连接,使此窗口可显示此方法由工具包在内部调用,不应由程序直接调用
     * 
     * 
     * @see Component#isDisplayable
     * @see Container#removeNotify
     * @since JDK1.0
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            Container parent = this.parent;
            if (parent != null && parent.getPeer() == null) {
                parent.addNotify();
            }
            if (peer == null) {
                peer = getToolkit().createWindow(this);
            }
            synchronized (allWindows) {
                allWindows.add(this);
            }
            super.addNotify();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            synchronized (allWindows) {
                allWindows.remove(this);
            }
            super.removeNotify();
        }
    }

    /**
     * Causes this Window to be sized to fit the preferred size
     * and layouts of its subcomponents. The resulting width and
     * height of the window are automatically enlarged if either
     * of dimensions is less than the minimum size as specified
     * by the previous call to the {@code setMinimumSize} method.
     * <p>
     * If the window and/or its owner are not displayable yet,
     * both of them are made displayable before calculating
     * the preferred size. The Window is validated after its
     * size is being calculated.
     *
     * <p>
     * 导致此窗口的大小适合其子组件的首选大小和布局。如果任一维度小于先前调用{@code setMinimumSize}时指定的最小大小,则会自动放大窗口的最终宽度和高度,方法
     * <p>
     *  如果窗口和/或其所有者不可显示,则在计算优选大小之前使它们都可显示。在计算窗口大小之后验证窗口
     * 
     * 
     * @see Component#isDisplayable
     * @see #setMinimumSize
     */
    public void pack() {
        Container parent = this.parent;
        if (parent != null && parent.getPeer() == null) {
            parent.addNotify();
        }
        if (peer == null) {
            addNotify();
        }
        Dimension newSize = getPreferredSize();
        if (peer != null) {
            setClientSize(newSize.width, newSize.height);
        }

        if(beforeFirstShow) {
            isPacked = true;
        }

        validateUnconditionally();
    }

    /**
     * Sets the minimum size of this window to a constant
     * value.  Subsequent calls to {@code getMinimumSize}
     * will always return this value. If current window's
     * size is less than {@code minimumSize} the size of the
     * window is automatically enlarged to honor the minimum size.
     * <p>
     * If the {@code setSize} or {@code setBounds} methods
     * are called afterwards with a width or height less than
     * that was specified by the {@code setMinimumSize} method
     * the window is automatically enlarged to meet
     * the {@code minimumSize} value. The {@code minimumSize}
     * value also affects the behaviour of the {@code pack} method.
     * <p>
     * The default behavior is restored by setting the minimum size
     * parameter to the {@code null} value.
     * <p>
     * Resizing operation may be restricted if the user tries
     * to resize window below the {@code minimumSize} value.
     * This behaviour is platform-dependent.
     *
     * <p>
     *  将此窗口的最小大小设置为常量值后续调用{@code getMinimumSize}将始终返回此值如果当前窗口的大小小于{@code minimumSize},窗口的大小将自动放大以满足最小大小
     * <p>
     * 如果之后调用{@code setSize}或{@code setBounds}方法的宽度或高度小于{@code setMinimumSize}方法指定的宽度或高度,则窗口会自动放大以满足{@code minimumSize}
     * 的值。
     *  {@code minimumSize}值也会影响{@code pack}方法的行为。
     * <p>
     *  通过将minimum size参数设置为{@code null}值来恢复默认行为
     * <p>
     *  如果用户尝试在{@code minimumSize}值下调整窗口大小,则调整大小操作可能受到限制此行为是平台相关的
     * 
     * 
     * @param minimumSize the new minimum size of this window
     * @see Component#setMinimumSize
     * @see #getMinimumSize
     * @see #isMinimumSizeSet
     * @see #setSize(Dimension)
     * @see #pack
     * @since 1.6
     */
    public void setMinimumSize(Dimension minimumSize) {
        synchronized (getTreeLock()) {
            super.setMinimumSize(minimumSize);
            Dimension size = getSize();
            if (isMinimumSizeSet()) {
                if (size.width < minimumSize.width || size.height < minimumSize.height) {
                    int nw = Math.max(width, minimumSize.width);
                    int nh = Math.max(height, minimumSize.height);
                    setSize(nw, nh);
                }
            }
            if (peer != null) {
                ((WindowPeer)peer).updateMinimumSize();
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * The {@code d.width} and {@code d.height} values
     * are automatically enlarged if either is less than
     * the minimum size as specified by previous call to
     * {@code setMinimumSize}.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  如果{@code dwidth}和{@code dheight}值小于先前调用{@code setMinimumSize}所指定的最小大小,则会自动放大
     * <p>
     * 该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     * 
     * @see #getSize
     * @see #setBounds
     * @see #setMinimumSize
     * @since 1.6
     */
    public void setSize(Dimension d) {
        super.setSize(d);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The {@code width} and {@code height} values
     * are automatically enlarged if either is less than
     * the minimum size as specified by previous call to
     * {@code setMinimumSize}.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  如果{@code width}和{@code height}值小于先前调用{@code setMinimumSize}所指定的最小大小,则会自动放大
     * <p>
     *  该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     * 
     * @see #getSize
     * @see #setBounds
     * @see #setMinimumSize
     * @since 1.6
     */
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     * <p>
     *  {@inheritDoc}
     * <p>
     * 该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     */
    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     * <p>
     *  {@inheritDoc}
     * <p>
     *  该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     */
    @Override
    public void setLocation(Point p) {
        super.setLocation(p);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by {@code setBounds(int, int, int, int)}.
     */
    @Deprecated
    public void reshape(int x, int y, int width, int height) {
        if (isMinimumSizeSet()) {
            Dimension minSize = getMinimumSize();
            if (width < minSize.width) {
                width = minSize.width;
            }
            if (height < minSize.height) {
                height = minSize.height;
            }
        }
        super.reshape(x, y, width, height);
    }

    void setClientSize(int w, int h) {
        synchronized (getTreeLock()) {
            setBoundsOp(ComponentPeer.SET_CLIENT_SIZE);
            setBounds(x, y, w, h);
        }
    }

    static private final AtomicBoolean
        beforeFirstWindowShown = new AtomicBoolean(true);

    final void closeSplashScreen() {
        if (isTrayIconWindow) {
            return;
        }
        if (beforeFirstWindowShown.getAndSet(false)) {
            // We don't use SplashScreen.getSplashScreen() to avoid instantiating
            // the object if it hasn't been requested by user code explicitly
            SunToolkit.closeSplashScreen();
            SplashScreen.markClosed();
        }
    }

    /**
     * Shows or hides this {@code Window} depending on the value of parameter
     * {@code b}.
     * <p>
     * If the method shows the window then the window is also made
     * focused under the following conditions:
     * <ul>
     * <li> The {@code Window} meets the requirements outlined in the
     *      {@link #isFocusableWindow} method.
     * <li> The {@code Window}'s {@code autoRequestFocus} property is of the {@code true} value.
     * <li> Native windowing system allows the {@code Window} to get focused.
     * </ul>
     * There is an exception for the second condition (the value of the
     * {@code autoRequestFocus} property). The property is not taken into account if the
     * window is a modal dialog, which blocks the currently focused window.
     * <p>
     * Developers must never assume that the window is the focused or active window
     * until it receives a WINDOW_GAINED_FOCUS or WINDOW_ACTIVATED event.
     * <p>
     *  根据参数{@code b}的值显示或隐藏此{@code Window}
     * <p>
     *  如果方法显示窗口,则窗口也在以下条件下进行聚焦：
     * <ul>
     * <li> {@code Window}符合{@link #isFocusableWindow}方法中列出的要求<li> {@code Window}的{@code autoRequestFocus}属性
     * 是{@code true}值<li >本机窗口系统允许{@code Window}得到关注。
     * </ul>
     *  第二个条件({@code autoRequestFocus}属性的值)有一个异常。如果窗口是一个模态对话框,该属性不被考虑,该对话框阻止当前聚焦的窗口
     * <p>
     *  开发人员不能假定窗口是焦点或活动窗口,直到它接收到WINDOW_GAINED_FOCUS或WINDOW_ACTIVATED事件
     * 
     * 
     * @param b  if {@code true}, makes the {@code Window} visible,
     * otherwise hides the {@code Window}.
     * If the {@code Window} and/or its owner
     * are not yet displayable, both are made displayable.  The
     * {@code Window} will be validated prior to being made visible.
     * If the {@code Window} is already visible, this will bring the
     * {@code Window} to the front.<p>
     * If {@code false}, hides this {@code Window}, its subcomponents, and all
     * of its owned children.
     * The {@code Window} and its subcomponents can be made visible again
     * with a call to {@code #setVisible(true)}.
     * @see java.awt.Component#isDisplayable
     * @see java.awt.Component#setVisible
     * @see java.awt.Window#toFront
     * @see java.awt.Window#dispose
     * @see java.awt.Window#setAutoRequestFocus
     * @see java.awt.Window#isFocusableWindow
     */
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    /**
     * Makes the Window visible. If the Window and/or its owner
     * are not yet displayable, both are made displayable.  The
     * Window will be validated prior to being made visible.
     * If the Window is already visible, this will bring the Window
     * to the front.
     * <p>
     * 使窗口可见如果窗口和/或其所有者不可显示,则两者都可显示。窗口在被显示之前将被验证如果窗口已经可见,这将使窗口前面
     * 
     * 
     * @see       Component#isDisplayable
     * @see       #toFront
     * @deprecated As of JDK version 1.5, replaced by
     * {@link #setVisible(boolean)}.
     */
    @Deprecated
    public void show() {
        if (peer == null) {
            addNotify();
        }
        validateUnconditionally();

        isInShow = true;
        if (visible) {
            toFront();
        } else {
            beforeFirstShow = false;
            closeSplashScreen();
            Dialog.checkShouldBeBlocked(this);
            super.show();
            synchronized (getTreeLock()) {
                this.locationByPlatform = false;
            }
            for (int i = 0; i < ownedWindowList.size(); i++) {
                Window child = ownedWindowList.elementAt(i).get();
                if ((child != null) && child.showWithParent) {
                    child.show();
                    child.showWithParent = false;
                }       // endif
            }   // endfor
            if (!isModalBlocked()) {
                updateChildrenBlocking();
            } else {
                // fix for 6532736: after this window is shown, its blocker
                // should be raised to front
                modalBlocker.toFront_NoClientCode();
            }
            if (this instanceof Frame || this instanceof Dialog) {
                updateChildFocusableWindowState(this);
            }
        }
        isInShow = false;

        // If first time shown, generate WindowOpened event
        if ((state & OPENED) == 0) {
            postWindowEvent(WindowEvent.WINDOW_OPENED);
            state |= OPENED;
        }
    }

    static void updateChildFocusableWindowState(Window w) {
        if (w.getPeer() != null && w.isShowing()) {
            ((WindowPeer)w.getPeer()).updateFocusableWindowState();
        }
        for (int i = 0; i < w.ownedWindowList.size(); i++) {
            Window child = w.ownedWindowList.elementAt(i).get();
            if (child != null) {
                updateChildFocusableWindowState(child);
            }
        }
    }

    synchronized void postWindowEvent(int id) {
        if (windowListener != null
            || (eventMask & AWTEvent.WINDOW_EVENT_MASK) != 0
            ||  Toolkit.enabledOnToolkit(AWTEvent.WINDOW_EVENT_MASK)) {
            WindowEvent e = new WindowEvent(this, id);
            Toolkit.getEventQueue().postEvent(e);
        }
    }

    /**
     * Hide this Window, its subcomponents, and all of its owned children.
     * The Window and its subcomponents can be made visible again
     * with a call to {@code show}.
     * <p>
     *  隐藏此窗口及其子组件及其所有的子项窗口及其子组件可以通过调用{@code show}
     * 
     * 
     * @see #show
     * @see #dispose
     * @deprecated As of JDK version 1.5, replaced by
     * {@link #setVisible(boolean)}.
     */
    @Deprecated
    public void hide() {
        synchronized(ownedWindowList) {
            for (int i = 0; i < ownedWindowList.size(); i++) {
                Window child = ownedWindowList.elementAt(i).get();
                if ((child != null) && child.visible) {
                    child.hide();
                    child.showWithParent = true;
                }
            }
        }
        if (isModalBlocked()) {
            modalBlocker.unblockWindow(this);
        }
        super.hide();
        synchronized (getTreeLock()) {
            this.locationByPlatform = false;
        }
    }

    final void clearMostRecentFocusOwnerOnHide() {
        /* do nothing */
    }

    /**
     * Releases all of the native screen resources used by this
     * {@code Window}, its subcomponents, and all of its owned
     * children. That is, the resources for these {@code Component}s
     * will be destroyed, any memory they consume will be returned to the
     * OS, and they will be marked as undisplayable.
     * <p>
     * The {@code Window} and its subcomponents can be made displayable
     * again by rebuilding the native resources with a subsequent call to
     * {@code pack} or {@code show}. The states of the recreated
     * {@code Window} and its subcomponents will be identical to the
     * states of these objects at the point where the {@code Window}
     * was disposed (not accounting for additional modifications between
     * those actions).
     * <p>
     * <b>Note</b>: When the last displayable window
     * within the Java virtual machine (VM) is disposed of, the VM may
     * terminate.  See <a href="doc-files/AWTThreadIssues.html#Autoshutdown">
     * AWT Threading Issues</a> for more information.
     * <p>
     *  释放此{@code Window},其子组件及其所有拥有的子系统使用的所有本机屏幕资源。
     * 也就是说,这些{@code Component}的资源将被销毁,它们消耗的任何内存将被返回操作系统,并且它们将被标记为不可显示。
     * <p>
     * {@code Window}及其子组件可以通过随后调用{@code pack}或{@code show}重建本地资源再次显示。
     * 重新创建的{@code Window}及其子组件的状态将是与{@code Window}处理时的这些对象的状态相同(不考虑这些动作之间的额外修改)。
     * <p>
     *  <b>注意</b>：当处理Java虚拟机(VM)中的最后一个可显示窗口时,虚拟机可能终止请参见<a href=\"doc-files/AWTThreadIssueshtml#Autoshutdown\">
     *  AWT线程问题< a>获取更多信息。
     * 
     * 
     * @see Component#isDisplayable
     * @see #pack
     * @see #show
     */
    public void dispose() {
        doDispose();
    }

    /*
     * Fix for 4872170.
     * If dispose() is called on parent then its children have to be disposed as well
     * as reported in javadoc. So we need to implement this functionality even if a
     * child overrides dispose() in a wrong way without calling super.dispose().
     * <p>
     * 修复4872170如果dispose()在父上调用,那么它的孩子必须被处理,并在javadoc中报告所以我们需要实现这个功能,即使一个孩子以错误的方式覆盖dispose(),而不调用superdispo
     * se()。
     * 
     */
    void disposeImpl() {
        dispose();
        if (getPeer() != null) {
            doDispose();
        }
    }

    void doDispose() {
    class DisposeAction implements Runnable {
        public void run() {
            disposing = true;
            try {
                // Check if this window is the fullscreen window for the
                // device. Exit the fullscreen mode prior to disposing
                // of the window if that's the case.
                GraphicsDevice gd = getGraphicsConfiguration().getDevice();
                if (gd.getFullScreenWindow() == Window.this) {
                    gd.setFullScreenWindow(null);
                }

                Object[] ownedWindowArray;
                synchronized(ownedWindowList) {
                    ownedWindowArray = new Object[ownedWindowList.size()];
                    ownedWindowList.copyInto(ownedWindowArray);
                }
                for (int i = 0; i < ownedWindowArray.length; i++) {
                    Window child = (Window) (((WeakReference)
                                   (ownedWindowArray[i])).get());
                    if (child != null) {
                        child.disposeImpl();
                    }
                }
                hide();
                beforeFirstShow = true;
                removeNotify();
                synchronized (inputContextLock) {
                    if (inputContext != null) {
                        inputContext.dispose();
                        inputContext = null;
                    }
                }
                clearCurrentFocusCycleRootOnHide();
            } finally {
                disposing = false;
            }
        }
    }
        boolean fireWindowClosedEvent = isDisplayable();
        DisposeAction action = new DisposeAction();
        if (EventQueue.isDispatchThread()) {
            action.run();
        }
        else {
            try {
                EventQueue.invokeAndWait(this, action);
            }
            catch (InterruptedException e) {
                System.err.println("Disposal was interrupted:");
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                System.err.println("Exception during disposal:");
                e.printStackTrace();
            }
        }
        // Execute outside the Runnable because postWindowEvent is
        // synchronized on (this). We don't need to synchronize the call
        // on the EventQueue anyways.
        if (fireWindowClosedEvent) {
            postWindowEvent(WindowEvent.WINDOW_CLOSED);
        }
    }

    /*
     * Should only be called while holding the tree lock.
     * It's overridden here because parent == owner in Window,
     * and we shouldn't adjust counter on owner
     * <p>
     *  应该只有在持有树锁时才被调用它在这里被覆盖,因为parent ==所有者在Window中,我们不应该调整所有者
     * 
     */
    void adjustListeningChildrenOnParent(long mask, int num) {
    }

    // Should only be called while holding tree lock
    void adjustDecendantsOnParent(int num) {
        // do nothing since parent == owner and we shouldn't
        // ajust counter on owner
    }

    /**
     * If this Window is visible, brings this Window to the front and may make
     * it the focused Window.
     * <p>
     * Places this Window at the top of the stacking order and shows it in
     * front of any other Windows in this VM. No action will take place if this
     * Window is not visible. Some platforms do not allow Windows which own
     * other Windows to appear on top of those owned Windows. Some platforms
     * may not permit this VM to place its Windows above windows of native
     * applications, or Windows of other VMs. This permission may depend on
     * whether a Window in this VM is already focused. Every attempt will be
     * made to move this Window as high as possible in the stacking order;
     * however, developers should not assume that this method will move this
     * Window above all other windows in every situation.
     * <p>
     * Developers must never assume that this Window is the focused or active
     * Window until this Window receives a WINDOW_GAINED_FOCUS or WINDOW_ACTIVATED
     * event. On platforms where the top-most window is the focused window, this
     * method will <b>probably</b> focus this Window (if it is not already focused)
     * under the following conditions:
     * <ul>
     * <li> The window meets the requirements outlined in the
     *      {@link #isFocusableWindow} method.
     * <li> The window's property {@code autoRequestFocus} is of the
     *      {@code true} value.
     * <li> Native windowing system allows the window to get focused.
     * </ul>
     * On platforms where the stacking order does not typically affect the focused
     * window, this method will <b>probably</b> leave the focused and active
     * Windows unchanged.
     * <p>
     * If this method causes this Window to be focused, and this Window is a
     * Frame or a Dialog, it will also become activated. If this Window is
     * focused, but it is not a Frame or a Dialog, then the first Frame or
     * Dialog that is an owner of this Window will be activated.
     * <p>
     * If this window is blocked by modal dialog, then the blocking dialog
     * is brought to the front and remains above the blocked window.
     *
     * <p>
     *  如果此窗口可见,则将此窗口置于前端,并使其成为聚焦窗口
     * <p>
     * 将此窗口置于堆叠顺序的顶部,并在此VM中任何其他Windows的前面显示此窗口如果此窗口不可见,将不会执行任何操作某些平台不允许Windows拥有其他Windows以显示在所有Windows某些平台可能
     * 不允许此VM将其Windows放置在本机应用程序的窗口或其他VM的Windows上此权限可能取决于此VM中的窗口是否已集中每次尝试将此窗口尽可能高在堆叠顺序中;然而,开发人员不应该假设这种方法会将此窗口
     * 移动到所有其他窗口之上。
     * <p>
     * 开发人员不得假设此窗口是焦点或活动窗口,直到此窗口接收到WINDOW_GAINED_FOCUS或WINDOW_ACTIVATED事件。
     * 在最顶层窗口是关注窗口的平台上,此方法将<b>可能</b>聚焦此窗口如果它没有被聚焦)在以下条件：。
     * <ul>
     *  <li>窗口符合{@link #isFocusableWindow}方法中列出的要求<li>窗口的属性{@code autoRequestFocus}是{@code true}值<li>本机窗口系统允
     * 许窗口聚焦。
     * </ul>
     *  在堆叠顺序通常不会影响聚焦窗口的平台上,此方法将<b>可能</b>保持焦点和活动窗口不变
     * <p>
     * 如果此方法导致此窗口被聚焦,并且此窗口是框架或对话框,它也将被激活如果此窗口被聚焦,但它不是框架或对话框,则第一个框架或对话框是一个此窗口的所有者将被激活
     * <p>
     *  如果此窗口被模态对话框阻止,则阻止对话框将显示在前面,并保留在阻止的窗口上方
     * 
     * 
     * @see       #toBack
     * @see       #setAutoRequestFocus
     * @see       #isFocusableWindow
     */
    public void toFront() {
        toFront_NoClientCode();
    }

    // This functionality is implemented in a final package-private method
    // to insure that it cannot be overridden by client subclasses.
    final void toFront_NoClientCode() {
        if (visible) {
            WindowPeer peer = (WindowPeer)this.peer;
            if (peer != null) {
                peer.toFront();
            }
            if (isModalBlocked()) {
                modalBlocker.toFront_NoClientCode();
            }
        }
    }

    /**
     * If this Window is visible, sends this Window to the back and may cause
     * it to lose focus or activation if it is the focused or active Window.
     * <p>
     * Places this Window at the bottom of the stacking order and shows it
     * behind any other Windows in this VM. No action will take place is this
     * Window is not visible. Some platforms do not allow Windows which are
     * owned by other Windows to appear below their owners. Every attempt will
     * be made to move this Window as low as possible in the stacking order;
     * however, developers should not assume that this method will move this
     * Window below all other windows in every situation.
     * <p>
     * Because of variations in native windowing systems, no guarantees about
     * changes to the focused and active Windows can be made. Developers must
     * never assume that this Window is no longer the focused or active Window
     * until this Window receives a WINDOW_LOST_FOCUS or WINDOW_DEACTIVATED
     * event. On platforms where the top-most window is the focused window,
     * this method will <b>probably</b> cause this Window to lose focus. In
     * that case, the next highest, focusable Window in this VM will receive
     * focus. On platforms where the stacking order does not typically affect
     * the focused window, this method will <b>probably</b> leave the focused
     * and active Windows unchanged.
     *
     * <p>
     *  如果此窗口可见,则将此窗口发送到后面,如果该窗口是聚焦窗口或活动窗口,则可能导致其失去焦点或激活
     * <p>
     * 将此窗口置于堆叠顺序的底部,并显示在此VM中任何其他Windows后面不会执行任何操作此窗口不可见某些平台不允许由其他Windows所有的Windows显示在其所有者下面每次尝试将使该窗口在堆叠顺序中尽
     * 可能低;然而,开发人员不应该假设这种方法会将此窗口移动到所有其他窗口之下。
     * <p>
     * 由于本地窗口系统的变化,不能保证关注的和活动的Windows的变化。
     * 开发人员不得假设此窗口不再是焦点或活动窗口,直到此窗口接收到WINDOW_LOST_FOCUS或WINDOW_DEACTIVATED事件在顶部这个方法将<b>可能</b>导致此窗口失去焦点在这种情况下,
     * 此VM中的下一个最高的可聚焦窗口将接收焦点在堆叠顺序通常不会影响聚焦窗口,此方法将<b>可能</b>保持焦点和活动窗口不变。
     * 由于本地窗口系统的变化,不能保证关注的和活动的Windows的变化。
     * 
     * 
     * @see       #toFront
     */
    public void toBack() {
        toBack_NoClientCode();
    }

    // This functionality is implemented in a final package-private method
    // to insure that it cannot be overridden by client subclasses.
    final void toBack_NoClientCode() {
        if(isAlwaysOnTop()) {
            try {
                setAlwaysOnTop(false);
            }catch(SecurityException e) {
            }
        }
        if (visible) {
            WindowPeer peer = (WindowPeer)this.peer;
            if (peer != null) {
                peer.toBack();
            }
        }
    }

    /**
     * Returns the toolkit of this frame.
     * <p>
     *  返回此框架的工具包
     * 
     * 
     * @return    the toolkit of this window.
     * @see       Toolkit
     * @see       Toolkit#getDefaultToolkit
     * @see       Component#getToolkit
     */
    public Toolkit getToolkit() {
        return Toolkit.getDefaultToolkit();
    }

    /**
     * Gets the warning string that is displayed with this window.
     * If this window is insecure, the warning string is displayed
     * somewhere in the visible area of the window. A window is
     * insecure if there is a security manager and the security
     * manager denies
     * {@code AWTPermission("showWindowWithoutWarningBanner")}.
     * <p>
     * If the window is secure, then {@code getWarningString}
     * returns {@code null}. If the window is insecure, this
     * method checks for the system property
     * {@code awt.appletWarning}
     * and returns the string value of that property.
     * <p>
     * 获取与此窗口一起显示的警告字符串如果此窗口不安全,则警告字符串显示在窗口的可见区域中的某个位置如果存在安全管理器,并且安全管理器拒绝{@code AWTPermission(" showWindowWithoutWarningBanner")}
     * 。
     * <p>
     *  如果窗口是安全的,那么{@code getWarningString}返回{@code null}如果窗口不安全,此方法检查系统属性{@code awtappletWarning},并返回该属性的字符
     * 串值。
     * 
     * 
     * @return    the warning string for this window.
     */
    public final String getWarningString() {
        return warningString;
    }

    private void setWarningString() {
        warningString = null;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            try {
                sm.checkPermission(SecurityConstants.AWT.TOPLEVEL_WINDOW_PERMISSION);
            } catch (SecurityException se) {
                // make sure the privileged action is only
                // for getting the property! We don't want the
                // above checkPermission call to always succeed!
                warningString = AccessController.doPrivileged(
                      new GetPropertyAction("awt.appletWarning",
                                            "Java Applet Window"));
            }
        }
    }

    /**
     * Gets the {@code Locale} object that is associated
     * with this window, if the locale has been set.
     * If no locale has been set, then the default locale
     * is returned.
     * <p>
     *  获取与此窗口相关联的{@code Locale}对象(如果已设置语言环境)如果未设置语言环境,则返回默认语言环境
     * 
     * 
     * @return    the locale that is set for this window.
     * @see       java.util.Locale
     * @since     JDK1.1
     */
    public Locale getLocale() {
      if (this.locale == null) {
        return Locale.getDefault();
      }
      return this.locale;
    }

    /**
     * Gets the input context for this window. A window always has an input context,
     * which is shared by subcomponents unless they create and set their own.
     * <p>
     * 获取此窗口的输入上下文窗口始终有一个输入上下文,由子组件共享,除非它们创建和设置自己的
     * 
     * 
     * @see Component#getInputContext
     * @since 1.2
     */
    public InputContext getInputContext() {
        synchronized (inputContextLock) {
            if (inputContext == null) {
                inputContext = InputContext.getInstance();
            }
        }
        return inputContext;
    }

    /**
     * Set the cursor image to a specified cursor.
     * <p>
     * The method may have no visual effect if the Java platform
     * implementation and/or the native system do not support
     * changing the mouse cursor shape.
     * <p>
     *  将光标图像设置为指定的光标
     * <p>
     *  如果Java平台实现和/或本地系统不支持改变鼠标光标形状,则该方法可以没有视觉效果
     * 
     * 
     * @param     cursor One of the constants defined
     *            by the {@code Cursor} class. If this parameter is null
     *            then the cursor for this window will be set to the type
     *            Cursor.DEFAULT_CURSOR.
     * @see       Component#getCursor
     * @see       Cursor
     * @since     JDK1.1
     */
    public void setCursor(Cursor cursor) {
        if (cursor == null) {
            cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
        }
        super.setCursor(cursor);
    }

    /**
     * Returns the owner of this window.
     * <p>
     *  返回此窗口的所有者
     * 
     * 
     * @since 1.2
     */
    public Window getOwner() {
        return getOwner_NoClientCode();
    }
    final Window getOwner_NoClientCode() {
        return (Window)parent;
    }

    /**
     * Return an array containing all the windows this
     * window currently owns.
     * <p>
     *  返回一个包含此窗口当前拥有的所有窗口的数组
     * 
     * 
     * @since 1.2
     */
    public Window[] getOwnedWindows() {
        return getOwnedWindows_NoClientCode();
    }
    final Window[] getOwnedWindows_NoClientCode() {
        Window realCopy[];

        synchronized(ownedWindowList) {
            // Recall that ownedWindowList is actually a Vector of
            // WeakReferences and calling get() on one of these references
            // may return null. Make two arrays-- one the size of the
            // Vector (fullCopy with size fullSize), and one the size of
            // all non-null get()s (realCopy with size realSize).
            int fullSize = ownedWindowList.size();
            int realSize = 0;
            Window fullCopy[] = new Window[fullSize];

            for (int i = 0; i < fullSize; i++) {
                fullCopy[realSize] = ownedWindowList.elementAt(i).get();

                if (fullCopy[realSize] != null) {
                    realSize++;
                }
            }

            if (fullSize != realSize) {
                realCopy = Arrays.copyOf(fullCopy, realSize);
            } else {
                realCopy = fullCopy;
            }
        }

        return realCopy;
    }

    boolean isModalBlocked() {
        return modalBlocker != null;
    }

    void setModalBlocked(Dialog blocker, boolean blocked, boolean peerCall) {
        this.modalBlocker = blocked ? blocker : null;
        if (peerCall) {
            WindowPeer peer = (WindowPeer)this.peer;
            if (peer != null) {
                peer.setModalBlocked(blocker, blocked);
            }
        }
    }

    Dialog getModalBlocker() {
        return modalBlocker;
    }

    /*
     * Returns a list of all displayable Windows, i. e. all the
     * Windows which peer is not null.
     *
     * <p>
     *  返回所有可显示的Windows的列表,即所有Windows的对等端不为null
     * 
     * 
     * @see #addNotify
     * @see #removeNotify
     */
    static IdentityArrayList<Window> getAllWindows() {
        synchronized (allWindows) {
            IdentityArrayList<Window> v = new IdentityArrayList<Window>();
            v.addAll(allWindows);
            return v;
        }
    }

    static IdentityArrayList<Window> getAllUnblockedWindows() {
        synchronized (allWindows) {
            IdentityArrayList<Window> unblocked = new IdentityArrayList<Window>();
            for (int i = 0; i < allWindows.size(); i++) {
                Window w = allWindows.get(i);
                if (!w.isModalBlocked()) {
                    unblocked.add(w);
                }
            }
            return unblocked;
        }
    }

    private static Window[] getWindows(AppContext appContext) {
        synchronized (Window.class) {
            Window realCopy[];
            @SuppressWarnings("unchecked")
            Vector<WeakReference<Window>> windowList =
                (Vector<WeakReference<Window>>)appContext.get(Window.class);
            if (windowList != null) {
                int fullSize = windowList.size();
                int realSize = 0;
                Window fullCopy[] = new Window[fullSize];
                for (int i = 0; i < fullSize; i++) {
                    Window w = windowList.get(i).get();
                    if (w != null) {
                        fullCopy[realSize++] = w;
                    }
                }
                if (fullSize != realSize) {
                    realCopy = Arrays.copyOf(fullCopy, realSize);
                } else {
                    realCopy = fullCopy;
                }
            } else {
                realCopy = new Window[0];
            }
            return realCopy;
        }
    }

    /**
     * Returns an array of all {@code Window}s, both owned and ownerless,
     * created by this application.
     * If called from an applet, the array includes only the {@code Window}s
     * accessible by that applet.
     * <p>
     * <b>Warning:</b> this method may return system created windows, such
     * as a print dialog. Applications should not assume the existence of
     * these dialogs, nor should an application assume anything about these
     * dialogs such as component positions, {@code LayoutManager}s
     * or serialization.
     *
     * <p>
     *  返回由此应用程序创建的所有{@code Window}(由拥有者和无所有者创建)的数组如果从applet调用,则该数组仅包含该applet可访问的{@code Window}
     * <p>
     * <b>警告：</b>此方法可能会返回系统创建的窗口,例如打印对话框应用程序不应假设这些对话框的存在,应用程序也不应该假设任何关于这些对话框,如组件位置,{@code LayoutManager }或序列
     * 化。
     * 
     * 
     * @see Frame#getFrames
     * @see Window#getOwnerlessWindows
     *
     * @since 1.6
     */
    public static Window[] getWindows() {
        return getWindows(AppContext.getAppContext());
    }

    /**
     * Returns an array of all {@code Window}s created by this application
     * that have no owner. They include {@code Frame}s and ownerless
     * {@code Dialog}s and {@code Window}s.
     * If called from an applet, the array includes only the {@code Window}s
     * accessible by that applet.
     * <p>
     * <b>Warning:</b> this method may return system created windows, such
     * as a print dialog. Applications should not assume the existence of
     * these dialogs, nor should an application assume anything about these
     * dialogs such as component positions, {@code LayoutManager}s
     * or serialization.
     *
     * <p>
     *  返回由此应用程序创建的没有所有者的所有{@code Window}的数组他们包括{@code Frame}和无拥有者{@code Dialog}和{@code Window}如果从applet调用,只
     * 包括该applet可访问的{@code Window}。
     * <p>
     * <b>警告：</b>此方法可能会返回系统创建的窗口,例如打印对话框应用程序不应假设这些对话框的存在,应用程序也不应该假设任何关于这些对话框,如组件位置,{@code LayoutManager }或序列
     * 化。
     * 
     * 
     * @see Frame#getFrames
     * @see Window#getWindows()
     *
     * @since 1.6
     */
    public static Window[] getOwnerlessWindows() {
        Window[] allWindows = Window.getWindows();

        int ownerlessCount = 0;
        for (Window w : allWindows) {
            if (w.getOwner() == null) {
                ownerlessCount++;
            }
        }

        Window[] ownerless = new Window[ownerlessCount];
        int c = 0;
        for (Window w : allWindows) {
            if (w.getOwner() == null) {
                ownerless[c++] = w;
            }
        }

        return ownerless;
    }

    Window getDocumentRoot() {
        synchronized (getTreeLock()) {
            Window w = this;
            while (w.getOwner() != null) {
                w = w.getOwner();
            }
            return w;
        }
    }

    /**
     * Specifies the modal exclusion type for this window. If a window is modal
     * excluded, it is not blocked by some modal dialogs. See {@link
     * java.awt.Dialog.ModalExclusionType Dialog.ModalExclusionType} for
     * possible modal exclusion types.
     * <p>
     * If the given type is not supported, {@code NO_EXCLUDE} is used.
     * <p>
     * Note: changing the modal exclusion type for a visible window may have no
     * effect until it is hidden and then shown again.
     *
     * <p>
     *  指定此窗口的模态排除类型如果窗口被排除模式,则不会被某些模态对话框阻止。
     * 有关可能的模态排除类型,请参阅{@link javaawtDialogModalExclusionType DialogModalExclusionType}。
     * <p>
     *  如果不支持给定类型,则使用{@code NO_EXCLUDE}
     * <p>
     *  注意：更改可见窗口的模式排除类型可能会被隐藏,然后再次显示可能无效
     * 
     * 
     * @param exclusionType the modal exclusion type for this window; a {@code null}
     *     value is equivalent to {@link Dialog.ModalExclusionType#NO_EXCLUDE
     *     NO_EXCLUDE}
     * @throws SecurityException if the calling thread does not have permission
     *     to set the modal exclusion property to the window with the given
     *     {@code exclusionType}
     * @see java.awt.Dialog.ModalExclusionType
     * @see java.awt.Window#getModalExclusionType
     * @see java.awt.Toolkit#isModalExclusionTypeSupported
     *
     * @since 1.6
     */
    public void setModalExclusionType(Dialog.ModalExclusionType exclusionType) {
        if (exclusionType == null) {
            exclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
        }
        if (!Toolkit.getDefaultToolkit().isModalExclusionTypeSupported(exclusionType)) {
            exclusionType = Dialog.ModalExclusionType.NO_EXCLUDE;
        }
        if (modalExclusionType == exclusionType) {
            return;
        }
        if (exclusionType == Dialog.ModalExclusionType.TOOLKIT_EXCLUDE) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPermission(SecurityConstants.AWT.TOOLKIT_MODALITY_PERMISSION);
            }
        }
        modalExclusionType = exclusionType;

        // if we want on-fly changes, we need to uncomment the lines below
        //   and override the method in Dialog to use modalShow() instead
        //   of updateChildrenBlocking()
 /*
        if (isModalBlocked()) {
            modalBlocker.unblockWindow(this);
        }
        Dialog.checkShouldBeBlocked(this);
        updateChildrenBlocking();
 /* <p>
 /* if(isModalBlocked()){modalBlockerunblockWindow(this); } DialogcheckShouldBeBlocked(this); updateChild
 /* renBlocking();。
 /* 
 */
    }

    /**
     * Returns the modal exclusion type of this window.
     *
     * <p>
     *  返回此窗口的模态排除类型
     * 
     * 
     * @return the modal exclusion type of this window
     *
     * @see java.awt.Dialog.ModalExclusionType
     * @see java.awt.Window#setModalExclusionType
     *
     * @since 1.6
     */
    public Dialog.ModalExclusionType getModalExclusionType() {
        return modalExclusionType;
    }

    boolean isModalExcluded(Dialog.ModalExclusionType exclusionType) {
        if ((modalExclusionType != null) &&
            modalExclusionType.compareTo(exclusionType) >= 0)
        {
            return true;
        }
        Window owner = getOwner_NoClientCode();
        return (owner != null) && owner.isModalExcluded(exclusionType);
    }

    void updateChildrenBlocking() {
        Vector<Window> childHierarchy = new Vector<Window>();
        Window[] ownedWindows = getOwnedWindows();
        for (int i = 0; i < ownedWindows.length; i++) {
            childHierarchy.add(ownedWindows[i]);
        }
        int k = 0;
        while (k < childHierarchy.size()) {
            Window w = childHierarchy.get(k);
            if (w.isVisible()) {
                if (w.isModalBlocked()) {
                    Dialog blocker = w.getModalBlocker();
                    blocker.unblockWindow(w);
                }
                Dialog.checkShouldBeBlocked(w);
                Window[] wOwned = w.getOwnedWindows();
                for (int j = 0; j < wOwned.length; j++) {
                    childHierarchy.add(wOwned[j]);
                }
            }
            k++;
        }
    }

    /**
     * Adds the specified window listener to receive window events from
     * this window.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的窗口侦听器以从此窗口接收窗口事件如果l为null,则不会抛出任何异常并且不执行任何操作<p>请参阅<a href=\"doc-files/AWTThreadIssueshtml#ListenersThreads\">
     *  AWT线程问题< a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param   l the window listener
     * @see #removeWindowListener
     * @see #getWindowListeners
     */
    public synchronized void addWindowListener(WindowListener l) {
        if (l == null) {
            return;
        }
        newEventsOnly = true;
        windowListener = AWTEventMulticaster.add(windowListener, l);
    }

    /**
     * Adds the specified window state listener to receive window
     * events from this window.  If {@code l} is {@code null},
     * no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的窗口状态侦听器以从此窗口接收窗口事件如果{@code l}是{@code null},则不会抛出异常,并且不执行任何操作<p>请参阅<a href ="doc-files / AWTThreadIssueshtml #ListenersThreads">
     *  AWT线程问题</a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param   l the window state listener
     * @see #removeWindowStateListener
     * @see #getWindowStateListeners
     * @since 1.4
     */
    public synchronized void addWindowStateListener(WindowStateListener l) {
        if (l == null) {
            return;
        }
        windowStateListener = AWTEventMulticaster.add(windowStateListener, l);
        newEventsOnly = true;
    }

    /**
     * Adds the specified window focus listener to receive window events
     * from this window.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 添加指定的窗口焦点侦听器以从此窗口接收窗口事件如果l为null,则不会抛出异常并且不执行任何操作<p>请参阅<a href=\"doc-files/AWTThreadIssueshtml#ListenersThreads\">
     *  AWT线程问题< / a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param   l the window focus listener
     * @see #removeWindowFocusListener
     * @see #getWindowFocusListeners
     * @since 1.4
     */
    public synchronized void addWindowFocusListener(WindowFocusListener l) {
        if (l == null) {
            return;
        }
        windowFocusListener = AWTEventMulticaster.add(windowFocusListener, l);
        newEventsOnly = true;
    }

    /**
     * Removes the specified window listener so that it no longer
     * receives window events from this window.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的窗口侦听器,以使其不再从此窗口接收窗口事件如果l为null,则不抛出异常,并且不执行任何操作<p>请参阅<a href=\"doc-files/AWTThreadIssueshtml#ListenersThreads\">
     *  AWT线程问题</a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param   l the window listener
     * @see #addWindowListener
     * @see #getWindowListeners
     */
    public synchronized void removeWindowListener(WindowListener l) {
        if (l == null) {
            return;
        }
        windowListener = AWTEventMulticaster.remove(windowListener, l);
    }

    /**
     * Removes the specified window state listener so that it no
     * longer receives window events from this window.  If
     * {@code l} is {@code null}, no exception is thrown and
     * no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 删除指定的窗口状态侦听器,以使其不再从此窗口接收窗口事件如果{@code l}是{@code null},则不会抛出异常并且不执行任何操作<p>请参阅<a href ="doc -files / AWTThreadIssueshtml#ListenersThreads">
     *  AWT线程问题</a>有关AWT的线程模型的详细信息。
     * 
     * 
     * @param   l the window state listener
     * @see #addWindowStateListener
     * @see #getWindowStateListeners
     * @since 1.4
     */
    public synchronized void removeWindowStateListener(WindowStateListener l) {
        if (l == null) {
            return;
        }
        windowStateListener = AWTEventMulticaster.remove(windowStateListener, l);
    }

    /**
     * Removes the specified window focus listener so that it no longer
     * receives window events from this window.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的窗口焦点侦听器,以使其不再从此窗口接收窗口事件如果l为null,则不会抛出任何异常,并且不执行任何操作<p>请参阅<a href=\"doc-files/AWTThreadIssueshtml#ListenersThreads\">
     *  AWT线程模型的详细信息。
     * 
     * 
     * @param   l the window focus listener
     * @see #addWindowFocusListener
     * @see #getWindowFocusListeners
     * @since 1.4
     */
    public synchronized void removeWindowFocusListener(WindowFocusListener l) {
        if (l == null) {
            return;
        }
        windowFocusListener = AWTEventMulticaster.remove(windowFocusListener, l);
    }

    /**
     * Returns an array of all the window listeners
     * registered on this window.
     *
     * <p>
     *  返回在此窗口上注册的所有窗口侦听器的数组
     * 
     * 
     * @return all of this window's {@code WindowListener}s
     *         or an empty array if no window
     *         listeners are currently registered
     *
     * @see #addWindowListener
     * @see #removeWindowListener
     * @since 1.4
     */
    public synchronized WindowListener[] getWindowListeners() {
        return getListeners(WindowListener.class);
    }

    /**
     * Returns an array of all the window focus listeners
     * registered on this window.
     *
     * <p>
     *  返回在此窗口上注册的所有窗口焦点侦听器的数组
     * 
     * 
     * @return all of this window's {@code WindowFocusListener}s
     *         or an empty array if no window focus
     *         listeners are currently registered
     *
     * @see #addWindowFocusListener
     * @see #removeWindowFocusListener
     * @since 1.4
     */
    public synchronized WindowFocusListener[] getWindowFocusListeners() {
        return getListeners(WindowFocusListener.class);
    }

    /**
     * Returns an array of all the window state listeners
     * registered on this window.
     *
     * <p>
     * 返回在此窗口上注册的所有窗口状态侦听器的数组
     * 
     * 
     * @return all of this window's {@code WindowStateListener}s
     *         or an empty array if no window state
     *         listeners are currently registered
     *
     * @see #addWindowStateListener
     * @see #removeWindowStateListener
     * @since 1.4
     */
    public synchronized WindowStateListener[] getWindowStateListeners() {
        return getListeners(WindowStateListener.class);
    }


    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this {@code Window}.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     *
     * You can specify the {@code listenerType} argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * {@code Window} {@code w}
     * for its window listeners with the following code:
     *
     * <pre>WindowListener[] wls = (WindowListener[])(w.getListeners(WindowListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * <p>
     *  返回当前在此{@code Window} <code> <em> Foo </em>侦听器</code>上注册为<code> <em> Foo </em> Listener </code> s使用<code>
     *  add <em> </em>侦听器</code>方法注册。
     * 
     * <p>
     * 
     *  您可以使用类文字指定{@code listenerType}参数,例如<code> <em> Foo </em> Listenerclass </code>例如,可以查询{@code Window} 
     * {@code w}为它的窗口侦听器使用以下代码：。
     * 
     *  <pre> WindowListener [] wls =(WindowListener [])(wgetListeners(WindowListenerclass)); </pre>
     * 
     *  如果不存在此类侦听器,则此方法将返回一个空数组
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          {@code java.util.EventListener}
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this window,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if {@code listenerType}
     *          doesn't specify a class or interface that implements
     *          {@code java.util.EventListener}
     * @exception NullPointerException if {@code listenerType} is {@code null}
     *
     * @see #getWindowListeners
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if (listenerType == WindowFocusListener.class) {
            l = windowFocusListener;
        } else if (listenerType == WindowStateListener.class) {
            l = windowStateListener;
        } else if (listenerType == WindowListener.class) {
            l = windowListener;
        } else {
            return super.getListeners(listenerType);
        }
        return AWTEventMulticaster.getListeners(l, listenerType);
    }

    // REMIND: remove when filtering is handled at lower level
    boolean eventEnabled(AWTEvent e) {
        switch(e.id) {
          case WindowEvent.WINDOW_OPENED:
          case WindowEvent.WINDOW_CLOSING:
          case WindowEvent.WINDOW_CLOSED:
          case WindowEvent.WINDOW_ICONIFIED:
          case WindowEvent.WINDOW_DEICONIFIED:
          case WindowEvent.WINDOW_ACTIVATED:
          case WindowEvent.WINDOW_DEACTIVATED:
            if ((eventMask & AWTEvent.WINDOW_EVENT_MASK) != 0 ||
                windowListener != null) {
                return true;
            }
            return false;
          case WindowEvent.WINDOW_GAINED_FOCUS:
          case WindowEvent.WINDOW_LOST_FOCUS:
            if ((eventMask & AWTEvent.WINDOW_FOCUS_EVENT_MASK) != 0 ||
                windowFocusListener != null) {
                return true;
            }
            return false;
          case WindowEvent.WINDOW_STATE_CHANGED:
            if ((eventMask & AWTEvent.WINDOW_STATE_EVENT_MASK) != 0 ||
                windowStateListener != null) {
                return true;
            }
            return false;
          default:
            break;
        }
        return super.eventEnabled(e);
    }

    /**
     * Processes events on this window. If the event is an
     * {@code WindowEvent}, it invokes the
     * {@code processWindowEvent} method, else it invokes its
     * superclass's {@code processEvent}.
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     * 在这个窗口上处理事件如果事件是一个{@code WindowEvent},它调用{@code processWindowEvent}方法,否则调用它的超类的{@code processEvent} <p>
     * 注意,如果事件参数是{@code null }该行为未指定,可能会导致异常。
     * 
     * 
     * @param e the event
     */
    protected void processEvent(AWTEvent e) {
        if (e instanceof WindowEvent) {
            switch (e.getID()) {
                case WindowEvent.WINDOW_OPENED:
                case WindowEvent.WINDOW_CLOSING:
                case WindowEvent.WINDOW_CLOSED:
                case WindowEvent.WINDOW_ICONIFIED:
                case WindowEvent.WINDOW_DEICONIFIED:
                case WindowEvent.WINDOW_ACTIVATED:
                case WindowEvent.WINDOW_DEACTIVATED:
                    processWindowEvent((WindowEvent)e);
                    break;
                case WindowEvent.WINDOW_GAINED_FOCUS:
                case WindowEvent.WINDOW_LOST_FOCUS:
                    processWindowFocusEvent((WindowEvent)e);
                    break;
                case WindowEvent.WINDOW_STATE_CHANGED:
                    processWindowStateEvent((WindowEvent)e);
                    break;
            }
            return;
        }
        super.processEvent(e);
    }

    /**
     * Processes window events occurring on this window by
     * dispatching them to any registered WindowListener objects.
     * NOTE: This method will not be called unless window events
     * are enabled for this component; this happens when one of the
     * following occurs:
     * <ul>
     * <li>A WindowListener object is registered via
     *     {@code addWindowListener}
     * <li>Window events are enabled via {@code enableEvents}
     * </ul>
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将窗口事件分派到任何已注册的WindowListener对象来处理在此窗口上发生的窗口事件注：除非为此组件启用窗口事件,否则不会调用此方法;当发生以下情况之一时会发生这种情况：
     * <ul>
     *  <li>通过{@code addWindowListener}注册一个WindowListener对象<li>通过{@code enableEvents}启用窗口事件
     * </ul>
     *  <p>请注意,如果事件参数是{@code null},则此行为未指定,并可能导致异常
     * 
     * 
     * @param e the window event
     * @see Component#enableEvents
     */
    protected void processWindowEvent(WindowEvent e) {
        WindowListener listener = windowListener;
        if (listener != null) {
            switch(e.getID()) {
                case WindowEvent.WINDOW_OPENED:
                    listener.windowOpened(e);
                    break;
                case WindowEvent.WINDOW_CLOSING:
                    listener.windowClosing(e);
                    break;
                case WindowEvent.WINDOW_CLOSED:
                    listener.windowClosed(e);
                    break;
                case WindowEvent.WINDOW_ICONIFIED:
                    listener.windowIconified(e);
                    break;
                case WindowEvent.WINDOW_DEICONIFIED:
                    listener.windowDeiconified(e);
                    break;
                case WindowEvent.WINDOW_ACTIVATED:
                    listener.windowActivated(e);
                    break;
                case WindowEvent.WINDOW_DEACTIVATED:
                    listener.windowDeactivated(e);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Processes window focus event occurring on this window by
     * dispatching them to any registered WindowFocusListener objects.
     * NOTE: this method will not be called unless window focus events
     * are enabled for this window. This happens when one of the
     * following occurs:
     * <ul>
     * <li>a WindowFocusListener is registered via
     *     {@code addWindowFocusListener}
     * <li>Window focus events are enabled via {@code enableEvents}
     * </ul>
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     * 通过将它们分派到任何已注册的WindowFocusListener对象来处理在此窗口上发生的窗口焦点事件注意：除非为此窗口启用了窗口焦点事件,否则不会调用此方法发生以下情况之一时会发生：
     * <ul>
     *  <li>通过{@code addWindowFocusListener}注册WindowFocusListener <li>通过{@code enableEvents}启用窗口焦点事件
     * </ul>
     *  <p>请注意,如果事件参数是{@code null},则此行为未指定,并可能导致异常
     * 
     * 
     * @param e the window focus event
     * @see Component#enableEvents
     * @since 1.4
     */
    protected void processWindowFocusEvent(WindowEvent e) {
        WindowFocusListener listener = windowFocusListener;
        if (listener != null) {
            switch (e.getID()) {
                case WindowEvent.WINDOW_GAINED_FOCUS:
                    listener.windowGainedFocus(e);
                    break;
                case WindowEvent.WINDOW_LOST_FOCUS:
                    listener.windowLostFocus(e);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Processes window state event occurring on this window by
     * dispatching them to any registered {@code WindowStateListener}
     * objects.
     * NOTE: this method will not be called unless window state events
     * are enabled for this window.  This happens when one of the
     * following occurs:
     * <ul>
     * <li>a {@code WindowStateListener} is registered via
     *    {@code addWindowStateListener}
     * <li>window state events are enabled via {@code enableEvents}
     * </ul>
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * <p>
     *  通过将它们分派到任何已注册的{@code WindowStateListener}对象来处理在此窗口上发生的窗口状态事件注意：除非为此窗口启用窗口状态事件,否则不会调用此方法发生以下情况之一时会发生：
     * 。
     * <ul>
     * <li>通过{@code addWindowStateListener}注册{@code WindowStateListener} <li>通过{@code enableEvents}启用窗口状态事件。
     * </ul>
     *  <p>请注意,如果事件参数是{@code null},则此行为未指定,并可能导致异常
     * 
     * 
     * @param e the window state event
     * @see java.awt.Component#enableEvents
     * @since 1.4
     */
    protected void processWindowStateEvent(WindowEvent e) {
        WindowStateListener listener = windowStateListener;
        if (listener != null) {
            switch (e.getID()) {
                case WindowEvent.WINDOW_STATE_CHANGED:
                    listener.windowStateChanged(e);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Implements a debugging hook -- checks to see if
     * the user has typed <i>control-shift-F1</i>.  If so,
     * the list of child windows is dumped to {@code System.out}.
     * <p>
     *  实现一个调试挂钩 - 检查用户是否键入<i> control-shift-F1 </i>如果是这样,子窗口列表转储到{@code Systemout}
     * 
     * 
     * @param e  the keyboard event
     */
    void preProcessKeyEvent(KeyEvent e) {
        // Dump the list of child windows to System.out.
        if (e.isActionKey() && e.getKeyCode() == KeyEvent.VK_F1 &&
            e.isControlDown() && e.isShiftDown() &&
            e.getID() == KeyEvent.KEY_PRESSED) {
            list(System.out, 0);
        }
    }

    void postProcessKeyEvent(KeyEvent e) {
        // Do nothing
    }


    /**
     * Sets whether this window should always be above other windows.  If
     * there are multiple always-on-top windows, their relative order is
     * unspecified and platform dependent.
     * <p>
     * If some other window is already always-on-top then the
     * relative order between these windows is unspecified (depends on
     * platform).  No window can be brought to be over the always-on-top
     * window except maybe another always-on-top window.
     * <p>
     * All windows owned by an always-on-top window inherit this state and
     * automatically become always-on-top.  If a window ceases to be
     * always-on-top, the windows that it owns will no longer be
     * always-on-top.  When an always-on-top window is sent {@link #toBack
     * toBack}, its always-on-top state is set to {@code false}.
     *
     * <p> When this method is called on a window with a value of
     * {@code true}, and the window is visible and the platform
     * supports always-on-top for this window, the window is immediately
     * brought forward, "sticking" it in the top-most position. If the
     * window isn`t currently visible, this method sets the always-on-top
     * state to {@code true} but does not bring the window forward.
     * When the window is later shown, it will be always-on-top.
     *
     * <p> When this method is called on a window with a value of
     * {@code false} the always-on-top state is set to normal. It may also
     * cause an unspecified, platform-dependent change in the z-order of
     * top-level windows, but other always-on-top windows will remain in
     * top-most position. Calling this method with a value of {@code false}
     * on a window that has a normal state has no effect.
     *
     * <p><b>Note</b>: some platforms might not support always-on-top
     * windows.  To detect if always-on-top windows are supported by the
     * current platform, use {@link Toolkit#isAlwaysOnTopSupported()} and
     * {@link Window#isAlwaysOnTopSupported()}.  If always-on-top mode
     * isn't supported for this window or this window's toolkit does not
     * support always-on-top windows, calling this method has no effect.
     * <p>
     * If a SecurityManager is installed, the calling thread must be
     * granted the AWTPermission "setWindowAlwaysOnTop" in
     * order to set the value of this property. If this
     * permission is not granted, this method will throw a
     * SecurityException, and the current value of the property will
     * be left unchanged.
     *
     * <p>
     *  设置此窗口是否应始终高于其他窗口如果有多个总在顶上的窗口,它们的相对顺序是未指定的,并且取决于平台
     * <p>
     * 如果一些其他窗口已经总是在顶部,则这些窗口之间的相对顺序是未指定的(取决于平台)没有窗口可以被带到总是在顶部窗口上,除了另一个总是在顶部窗口
     * <p>
     *  由始终在顶的窗口拥有的所有窗口继承此状态并自动变为始终在顶部如果窗口不再是始终在顶部,则它所拥有的窗口将不再是始终位于顶部。
     * 始终在顶部窗口被发送{@link #toBack toBack},其始终在顶部状态被设置为{@code false}。
     * 
     * <p>当在值为{@code true}的窗口上调用此方法,并且窗口可见且平台支持此窗口始终在顶部时,窗口立即提前,"粘贴"在最顶部位置如果窗口目前不可见,则此方法将始终在顶部状态设置为{@code true}
     * ,但不会使窗口向前。
     * 当窗口稍后显示时,在上面。
     * 
     * <p>当在值为{@code false}的窗口上调用此方法时,始终处于顶部的状态设置为normal。
     * 它也可能导致顶层函数的z次序中未指定的,但是其他始终在顶的窗口将保持在最高位置在具有正常状态的窗口上调用具有{@code false}的值的该方法没有效果。
     * 
     *  <p> <b>注意</b>：某些平台可能不支持常驻窗口要检测当前平台是否支持常驻窗口,请使用{@link Toolkit#isAlwaysOnTopSupported()} {@link Window#isAlwaysOnTopSupported()}
     * 如果此窗口不支持always-on-top模式或此窗口的工具包不支持总在顶层窗口,调用此方法没有效果。
     * <p>
     * 如果安装了SecurityManager,则必须授予调用线程AWTPermission"setWindowAlwaysOnTop"以设置此属性的值如果未授予此权限,则此方法将抛出SecurityExce
     * ption,并且属性的当前值将保留不变。
     * 
     * 
     * @param alwaysOnTop true if the window should always be above other
     *        windows
     * @throws SecurityException if the calling thread does not have
     *         permission to set the value of always-on-top property
     *
     * @see #isAlwaysOnTop
     * @see #toFront
     * @see #toBack
     * @see AWTPermission
     * @see #isAlwaysOnTopSupported
     * @see #getToolkit
     * @see Toolkit#isAlwaysOnTopSupported
     * @since 1.5
     */
    public final void setAlwaysOnTop(boolean alwaysOnTop) throws SecurityException {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.SET_WINDOW_ALWAYS_ON_TOP_PERMISSION);
        }

        boolean oldAlwaysOnTop;
        synchronized(this) {
            oldAlwaysOnTop = this.alwaysOnTop;
            this.alwaysOnTop = alwaysOnTop;
        }
        if (oldAlwaysOnTop != alwaysOnTop ) {
            if (isAlwaysOnTopSupported()) {
                WindowPeer peer = (WindowPeer)this.peer;
                synchronized(getTreeLock()) {
                    if (peer != null) {
                        peer.updateAlwaysOnTopState();
                    }
                }
            }
            firePropertyChange("alwaysOnTop", oldAlwaysOnTop, alwaysOnTop);
        }
        setOwnedWindowsAlwaysOnTop(alwaysOnTop);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setOwnedWindowsAlwaysOnTop(boolean alwaysOnTop) {
        WeakReference<Window>[] ownedWindowArray;
        synchronized (ownedWindowList) {
            ownedWindowArray = new WeakReference[ownedWindowList.size()];
            ownedWindowList.copyInto(ownedWindowArray);
        }

        for (WeakReference<Window> ref : ownedWindowArray) {
            Window window = ref.get();
            if (window != null) {
                try {
                    window.setAlwaysOnTop(alwaysOnTop);
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    /**
     * Returns whether the always-on-top mode is supported for this
     * window. Some platforms may not support always-on-top windows, some
     * may support only some kinds of top-level windows; for example,
     * a platform may not support always-on-top modal dialogs.
     *
     * <p>
     *  返回此窗口是否支持always-on-top模式某些平台可能不支持总在顶层窗口,有些平台可能只支持某些顶级窗口;例如,平台可能不支持始终在顶模态对话
     * 
     * 
     * @return {@code true}, if the always-on-top mode is supported for
     *         this window and this window's toolkit supports always-on-top windows,
     *         {@code false} otherwise
     *
     * @see #setAlwaysOnTop(boolean)
     * @see #getToolkit
     * @see Toolkit#isAlwaysOnTopSupported
     * @since 1.6
     */
    public boolean isAlwaysOnTopSupported() {
        return Toolkit.getDefaultToolkit().isAlwaysOnTopSupported();
    }


    /**
     * Returns whether this window is an always-on-top window.
     * <p>
     *  返回此窗口是否为始终在顶的窗口
     * 
     * 
     * @return {@code true}, if the window is in always-on-top state,
     *         {@code false} otherwise
     * @see #setAlwaysOnTop
     * @since 1.5
     */
    public final boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }


    /**
     * Returns the child Component of this Window that has focus if this Window
     * is focused; returns null otherwise.
     *
     * <p>
     *  返回此窗口的焦点的子组件,如果此窗口聚焦;否则返回null
     * 
     * 
     * @return the child Component with focus, or null if this Window is not
     *         focused
     * @see #getMostRecentFocusOwner
     * @see #isFocused
     */
    public Component getFocusOwner() {
        return (isFocused())
            ? KeyboardFocusManager.getCurrentKeyboardFocusManager().
                  getFocusOwner()
            : null;
    }

    /**
     * Returns the child Component of this Window that will receive the focus
     * when this Window is focused. If this Window is currently focused, this
     * method returns the same Component as {@code getFocusOwner()}. If
     * this Window is not focused, then the child Component that most recently
     * requested focus will be returned. If no child Component has ever
     * requested focus, and this is a focusable Window, then this Window's
     * initial focusable Component is returned. If no child Component has ever
     * requested focus, and this is a non-focusable Window, null is returned.
     *
     * <p>
     * 返回此窗口的子组件将在此窗口聚焦时接收焦点如果此窗口当前焦点,此方法将返回与{@code getFocusOwner()}相同的组件。
     * 如果此窗口不聚焦,则子组件最近请求的焦点将被返回如果没有子组件曾经请求焦点,并且这是一个可聚焦的窗口,则返回此窗口的初始可焦点组件如果没有子组件曾请求焦点,并且这是一个不可聚焦的窗口,null返回。
     * 
     * 
     * @return the child Component that will receive focus when this Window is
     *         focused
     * @see #getFocusOwner
     * @see #isFocused
     * @see #isFocusableWindow
     * @since 1.4
     */
    public Component getMostRecentFocusOwner() {
        if (isFocused()) {
            return getFocusOwner();
        } else {
            Component mostRecent =
                KeyboardFocusManager.getMostRecentFocusOwner(this);
            if (mostRecent != null) {
                return mostRecent;
            } else {
                return (isFocusableWindow())
                    ? getFocusTraversalPolicy().getInitialComponent(this)
                    : null;
            }
        }
    }

    /**
     * Returns whether this Window is active. Only a Frame or a Dialog may be
     * active. The native windowing system may denote the active Window or its
     * children with special decorations, such as a highlighted title bar. The
     * active Window is always either the focused Window, or the first Frame or
     * Dialog that is an owner of the focused Window.
     *
     * <p>
     * 返回此窗口是否处于活动状态只有框架或对话框可能处于活动状态本地窗口系统可能表示活动窗口或其特殊装饰的子窗口,例如突出显示的标题栏。活动窗口始终是焦点窗口或第一个框架或对话框是所关注的窗口的所有者
     * 
     * 
     * @return whether this is the active Window.
     * @see #isFocused
     * @since 1.4
     */
    public boolean isActive() {
        return (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getActiveWindow() == this);
    }

    /**
     * Returns whether this Window is focused. If there exists a focus owner,
     * the focused Window is the Window that is, or contains, that focus owner.
     * If there is no focus owner, then no Window is focused.
     * <p>
     * If the focused Window is a Frame or a Dialog it is also the active
     * Window. Otherwise, the active Window is the first Frame or Dialog that
     * is an owner of the focused Window.
     *
     * <p>
     *  返回此窗口是否聚焦如果存在焦点所有者,则关注的窗口是属于或包含该焦点所有者的窗口如果没有焦点所有者,则不会聚焦窗口
     * <p>
     *  如果聚焦的窗口是一个框架或对话框,它也是活动窗口否则,活动窗口是第一个框架或对话框是焦点窗口的所有者
     * 
     * 
     * @return whether this is the focused Window.
     * @see #isActive
     * @since 1.4
     */
    public boolean isFocused() {
        return (KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getGlobalFocusedWindow() == this);
    }

    /**
     * Gets a focus traversal key for this Window. (See {@code
     * setFocusTraversalKeys} for a full description of each key.)
     * <p>
     * If the traversal key has not been explicitly set for this Window,
     * then this Window's parent's traversal key is returned. If the
     * traversal key has not been explicitly set for any of this Window's
     * ancestors, then the current KeyboardFocusManager's default traversal key
     * is returned.
     *
     * <p>
     * 获取此窗口的焦点遍历键(有关每个键的完整说明,请参阅{@code setFocusTraversalKeys})
     * <p>
     *  如果未为此窗口显式设置遍历键,则返回此窗口的父的遍历键如果遍历键未针对此窗口的祖先显式设置,则返回当前KeyboardFocusManager的默认遍历键
     * 
     * 
     * @param id one of KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *         KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
     *         KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, or
     *         KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS
     * @return the AWTKeyStroke for the specified key
     * @see Container#setFocusTraversalKeys
     * @see KeyboardFocusManager#FORWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#BACKWARD_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#UP_CYCLE_TRAVERSAL_KEYS
     * @see KeyboardFocusManager#DOWN_CYCLE_TRAVERSAL_KEYS
     * @throws IllegalArgumentException if id is not one of
     *         KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
     *         KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
     *         KeyboardFocusManager.UP_CYCLE_TRAVERSAL_KEYS, or
     *         KeyboardFocusManager.DOWN_CYCLE_TRAVERSAL_KEYS
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    public Set<AWTKeyStroke> getFocusTraversalKeys(int id) {
        if (id < 0 || id >= KeyboardFocusManager.TRAVERSAL_KEY_LENGTH) {
            throw new IllegalArgumentException("invalid focus traversal key identifier");
        }

        // Okay to return Set directly because it is an unmodifiable view
        @SuppressWarnings("rawtypes")
        Set keystrokes = (focusTraversalKeys != null)
            ? focusTraversalKeys[id]
            : null;

        if (keystrokes != null) {
            return keystrokes;
        } else {
            return KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getDefaultFocusTraversalKeys(id);
        }
    }

    /**
     * Does nothing because Windows must always be roots of a focus traversal
     * cycle. The passed-in value is ignored.
     *
     * <p>
     *  什么都不做,因为Windows必须始终是焦点遍历循环的根。忽略传入的值
     * 
     * 
     * @param focusCycleRoot this value is ignored
     * @see #isFocusCycleRoot
     * @see Container#setFocusTraversalPolicy
     * @see Container#getFocusTraversalPolicy
     * @since 1.4
     */
    public final void setFocusCycleRoot(boolean focusCycleRoot) {
    }

    /**
     * Always returns {@code true} because all Windows must be roots of a
     * focus traversal cycle.
     *
     * <p>
     *  始终返回{@code true},因为所有Windows都必须是焦点遍历循环的根
     * 
     * 
     * @return {@code true}
     * @see #setFocusCycleRoot
     * @see Container#setFocusTraversalPolicy
     * @see Container#getFocusTraversalPolicy
     * @since 1.4
     */
    public final boolean isFocusCycleRoot() {
        return true;
    }

    /**
     * Always returns {@code null} because Windows have no ancestors; they
     * represent the top of the Component hierarchy.
     *
     * <p>
     *  始终返回{@code null},因为Windows没有祖先;它们表示组件层次结构的顶部
     * 
     * 
     * @return {@code null}
     * @see Container#isFocusCycleRoot()
     * @since 1.4
     */
    public final Container getFocusCycleRootAncestor() {
        return null;
    }

    /**
     * Returns whether this Window can become the focused Window, that is,
     * whether this Window or any of its subcomponents can become the focus
     * owner. For a Frame or Dialog to be focusable, its focusable Window state
     * must be set to {@code true}. For a Window which is not a Frame or
     * Dialog to be focusable, its focusable Window state must be set to
     * {@code true}, its nearest owning Frame or Dialog must be
     * showing on the screen, and it must contain at least one Component in
     * its focus traversal cycle. If any of these conditions is not met, then
     * neither this Window nor any of its subcomponents can become the focus
     * owner.
     *
     * <p>
     * 返回此窗口是否可以成为关注的窗口,即此窗口或其任何子组件是否可以成为焦点所有者对于框架或对话框是可聚焦的,其可聚焦的窗口状态必须设置为{@code true} For a窗口不是框架或对话框可聚焦,其可
     * 聚焦窗口状态必须设置为{@code true},其最近拥有的框架或对话框必须显示在屏幕上,并且它必须包含至少一个组件在其焦点遍历循环如果不满足这些条件中的任何一个,则此窗口或其任何子组件都不能成为焦点所
     * 有者。
     * 
     * 
     * @return {@code true} if this Window can be the focused Window;
     *         {@code false} otherwise
     * @see #getFocusableWindowState
     * @see #setFocusableWindowState
     * @see #isShowing
     * @see Component#isFocusable
     * @since 1.4
     */
    public final boolean isFocusableWindow() {
        // If a Window/Frame/Dialog was made non-focusable, then it is always
        // non-focusable.
        if (!getFocusableWindowState()) {
            return false;
        }

        // All other tests apply only to Windows.
        if (this instanceof Frame || this instanceof Dialog) {
            return true;
        }

        // A Window must have at least one Component in its root focus
        // traversal cycle to be focusable.
        if (getFocusTraversalPolicy().getDefaultComponent(this) == null) {
            return false;
        }

        // A Window's nearest owning Frame or Dialog must be showing on the
        // screen.
        for (Window owner = getOwner(); owner != null;
             owner = owner.getOwner())
        {
            if (owner instanceof Frame || owner instanceof Dialog) {
                return owner.isShowing();
            }
        }

        return false;
    }

    /**
     * Returns whether this Window can become the focused Window if it meets
     * the other requirements outlined in {@code isFocusableWindow}. If
     * this method returns {@code false}, then
     * {@code isFocusableWindow} will return {@code false} as well.
     * If this method returns {@code true}, then
     * {@code isFocusableWindow} may return {@code true} or
     * {@code false} depending upon the other requirements which must be
     * met in order for a Window to be focusable.
     * <p>
     * By default, all Windows have a focusable Window state of
     * {@code true}.
     *
     * <p>
     * 返回此窗口是否可以成为焦点窗口,如果它满足{@code isFocusableWindow}中列出的其他要求如果此方法返回{@code false},则{@code isFocusableWindow}
     * 将返回{@code false}以及如果此方法返回{@code true},则{@code isFocusableWindow}可能会返回{@code true}或{@code false},具体取决于
     * 必须满足的其他要求才能使窗口可聚焦。
     * <p>
     *  默认情况下,所有Windows都具有可聚焦的窗口状态{@code true}
     * 
     * 
     * @return whether this Window can be the focused Window
     * @see #isFocusableWindow
     * @see #setFocusableWindowState
     * @see #isShowing
     * @see Component#setFocusable
     * @since 1.4
     */
    public boolean getFocusableWindowState() {
        return focusableWindowState;
    }

    /**
     * Sets whether this Window can become the focused Window if it meets
     * the other requirements outlined in {@code isFocusableWindow}. If
     * this Window's focusable Window state is set to {@code false}, then
     * {@code isFocusableWindow} will return {@code false}. If this
     * Window's focusable Window state is set to {@code true}, then
     * {@code isFocusableWindow} may return {@code true} or
     * {@code false} depending upon the other requirements which must be
     * met in order for a Window to be focusable.
     * <p>
     * Setting a Window's focusability state to {@code false} is the
     * standard mechanism for an application to identify to the AWT a Window
     * which will be used as a floating palette or toolbar, and thus should be
     * a non-focusable Window.
     *
     * Setting the focusability state on a visible {@code Window}
     * can have a delayed effect on some platforms &#151; the actual
     * change may happen only when the {@code Window} becomes
     * hidden and then visible again.  To ensure consistent behavior
     * across platforms, set the {@code Window}'s focusable state
     * when the {@code Window} is invisible and then show it.
     *
     * <p>
     * 设置此窗口是否可以成为焦点窗口,如果它满足{@code isFocusableWindow}中列出的其他要求如果此窗口的可聚焦窗口状态设置为{@code false},则{@code isFocusableWindow}
     * 将返回{@code false}如果此窗口的可聚焦窗口状态设置为{@code true},则{@code isFocusableWindow}可能返回{@code true}或{@code false}
     * ,取决于必须满足的其他要求,可聚焦。
     * <p>
     *  将窗口的可聚焦性状态设置为{@code false}是应用程序向AWT标识将被用作浮动调色板或工具栏的窗口的标准机制,因此应该是不可聚焦的窗口
     * 
     * 在可见的{@code Window}上设置可聚焦状态可能对某些平台有延迟的影响 - 实际的变化可能只发生在{@code Window}被隐藏然后可见的情况下为了确保跨平台的一致行为,设置{ @code Window}
     * 的可聚焦状态,当{@code Window}是不可见的,然后显示它。
     * 
     * 
     * @param focusableWindowState whether this Window can be the focused
     *        Window
     * @see #isFocusableWindow
     * @see #getFocusableWindowState
     * @see #isShowing
     * @see Component#setFocusable
     * @since 1.4
     */
    public void setFocusableWindowState(boolean focusableWindowState) {
        boolean oldFocusableWindowState;
        synchronized (this) {
            oldFocusableWindowState = this.focusableWindowState;
            this.focusableWindowState = focusableWindowState;
        }
        WindowPeer peer = (WindowPeer)this.peer;
        if (peer != null) {
            peer.updateFocusableWindowState();
        }
        firePropertyChange("focusableWindowState", oldFocusableWindowState,
                           focusableWindowState);
        if (oldFocusableWindowState && !focusableWindowState && isFocused()) {
            for (Window owner = getOwner();
                 owner != null;
                 owner = owner.getOwner())
                {
                    Component toFocus =
                        KeyboardFocusManager.getMostRecentFocusOwner(owner);
                    if (toFocus != null && toFocus.requestFocus(false, CausedFocusEvent.Cause.ACTIVATION)) {
                        return;
                    }
                }
            KeyboardFocusManager.getCurrentKeyboardFocusManager().
                clearGlobalFocusOwnerPriv();
        }
    }

    /**
     * Sets whether this window should receive focus on
     * subsequently being shown (with a call to {@link #setVisible setVisible(true)}),
     * or being moved to the front (with a call to {@link #toFront}).
     * <p>
     * Note that {@link #setVisible setVisible(true)} may be called indirectly
     * (e.g. when showing an owner of the window makes the window to be shown).
     * {@link #toFront} may also be called indirectly (e.g. when
     * {@link #setVisible setVisible(true)} is called on already visible window).
     * In all such cases this property takes effect as well.
     * <p>
     * The value of the property is not inherited by owned windows.
     *
     * <p>
     *  设置此窗口是否应在随后显示时调用(调用{@link #setVisible setVisible(true)})或移动到前面(调用{@link #toFront})
     * <p>
     * 注意,{@link #setVisible setVisible(true)}可以被间接调用(例如,当显示窗口的所有者使窗口被显示时){@link #toFront}也可以被间接调用(例如,当{@link# setVisible setVisible(true)}
     * 在已经可见的窗口上调用)在所有这些情况下,此属性也生效。
     * <p>
     *  该属性的值不会被拥有的窗口继承
     * 
     * 
     * @param autoRequestFocus whether this window should be focused on
     *        subsequently being shown or being moved to the front
     * @see #isAutoRequestFocus
     * @see #isFocusableWindow
     * @see #setVisible
     * @see #toFront
     * @since 1.7
     */
    public void setAutoRequestFocus(boolean autoRequestFocus) {
        this.autoRequestFocus = autoRequestFocus;
    }

    /**
     * Returns whether this window should receive focus on subsequently being shown
     * (with a call to {@link #setVisible setVisible(true)}), or being moved to the front
     * (with a call to {@link #toFront}).
     * <p>
     * By default, the window has {@code autoRequestFocus} value of {@code true}.
     *
     * <p>
     *  返回此窗口是否应在随后显示时(调用{@link #setVisible setVisible(true)})或移至前端(调用{@link #toFront})时接收焦点
     * <p>
     *  默认情况下,该窗口的{@code autoRequestFocus}值为{@code true}
     * 
     * 
     * @return {@code autoRequestFocus} value
     * @see #setAutoRequestFocus
     * @since 1.7
     */
    public boolean isAutoRequestFocus() {
        return autoRequestFocus;
    }

    /**
     * Adds a PropertyChangeListener to the listener list. The listener is
     * registered for all bound properties of this class, including the
     * following:
     * <ul>
     *    <li>this Window's font ("font")</li>
     *    <li>this Window's background color ("background")</li>
     *    <li>this Window's foreground color ("foreground")</li>
     *    <li>this Window's focusability ("focusable")</li>
     *    <li>this Window's focus traversal keys enabled state
     *        ("focusTraversalKeysEnabled")</li>
     *    <li>this Window's Set of FORWARD_TRAVERSAL_KEYS
     *        ("forwardFocusTraversalKeys")</li>
     *    <li>this Window's Set of BACKWARD_TRAVERSAL_KEYS
     *        ("backwardFocusTraversalKeys")</li>
     *    <li>this Window's Set of UP_CYCLE_TRAVERSAL_KEYS
     *        ("upCycleFocusTraversalKeys")</li>
     *    <li>this Window's Set of DOWN_CYCLE_TRAVERSAL_KEYS
     *        ("downCycleFocusTraversalKeys")</li>
     *    <li>this Window's focus traversal policy ("focusTraversalPolicy")
     *        </li>
     *    <li>this Window's focusable Window state ("focusableWindowState")
     *        </li>
     *    <li>this Window's always-on-top state("alwaysOnTop")</li>
     * </ul>
     * Note that if this Window is inheriting a bound property, then no
     * event will be fired in response to a change in the inherited property.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     *
     * <p>
     * 将PropertyChangeListener添加到侦听器列表为该类的所有绑定属性注册侦听器,包括以下内容：
     * <ul>
     * <li>此窗口的背景颜色("背景")</li> <li>此窗口的前景色("前景")</li> <li> </li> <li>此窗口的FORWARD_TRAVERSAL_KEYS("forwardFocu
     * sTraversalKeys")集</li> <li>此窗口的焦点遍历键已启用状态("focusTraversalKeysEnabled" </li> </li> </li> </li> </li> </li>
     *  </li> </li> </li> </li> </li>此窗口的设定为BACKWARD_TRAVERSAL_KEYS("backwardFocusTraversalKeys" li>此窗口的焦点遍历
     * 策略("focusTraversalPolicy")。
     * </li>
     *  <li>此窗口的可聚焦窗口状态("focusableWindowState")
     * </li>
     * <li>此窗口的始终在顶状态("alwaysOnTop")</li>
     * </ul>
     *  注意,如果这个窗口继承了一个bound属性,那么没有事件会响应继承属性的改变而被触发
     * <p>
     *  如果侦听器为null,则不抛出异常,并且不执行任何操作
     * 
     * 
     * @param    listener  the PropertyChangeListener to be added
     *
     * @see Component#removePropertyChangeListener
     * @see #addPropertyChangeListener(java.lang.String,java.beans.PropertyChangeListener)
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }

    /**
     * Adds a PropertyChangeListener to the listener list for a specific
     * property. The specified property may be user-defined, or one of the
     * following:
     * <ul>
     *    <li>this Window's font ("font")</li>
     *    <li>this Window's background color ("background")</li>
     *    <li>this Window's foreground color ("foreground")</li>
     *    <li>this Window's focusability ("focusable")</li>
     *    <li>this Window's focus traversal keys enabled state
     *        ("focusTraversalKeysEnabled")</li>
     *    <li>this Window's Set of FORWARD_TRAVERSAL_KEYS
     *        ("forwardFocusTraversalKeys")</li>
     *    <li>this Window's Set of BACKWARD_TRAVERSAL_KEYS
     *        ("backwardFocusTraversalKeys")</li>
     *    <li>this Window's Set of UP_CYCLE_TRAVERSAL_KEYS
     *        ("upCycleFocusTraversalKeys")</li>
     *    <li>this Window's Set of DOWN_CYCLE_TRAVERSAL_KEYS
     *        ("downCycleFocusTraversalKeys")</li>
     *    <li>this Window's focus traversal policy ("focusTraversalPolicy")
     *        </li>
     *    <li>this Window's focusable Window state ("focusableWindowState")
     *        </li>
     *    <li>this Window's always-on-top state("alwaysOnTop")</li>
     * </ul>
     * Note that if this Window is inheriting a bound property, then no
     * event will be fired in response to a change in the inherited property.
     * <p>
     * If listener is null, no exception is thrown and no action is performed.
     *
     * <p>
     *  将PropertyChangeListener添加到特定属性的侦听器列表指定的属性可以是用户定义的,也可以是以下之一：
     * <ul>
     * <li>此窗口的背景颜色("背景")</li> <li>此窗口的前景色("前景")</li> <li> </li> <li>此窗口的FORWARD_TRAVERSAL_KEYS("forwardFocu
     * sTraversalKeys")集</li> <li>此窗口的焦点遍历键已启用状态("focusTraversalKeysEnabled" </li> </li> </li> </li> </li> </li>
     *  </li> </li> </li> </li> </li>此窗口的设定为BACKWARD_TRAVERSAL_KEYS("backwardFocusTraversalKeys" li>此窗口的焦点遍历
     * 策略("focusTraversalPolicy")。
     * </li>
     *  <li>此窗口的可聚焦窗口状态("focusableWindowState")
     * </li>
     * <li>此窗口的始终在顶状态("alwaysOnTop")</li>
     * </ul>
     *  注意,如果这个窗口继承了一个bound属性,那么没有事件会响应继承属性的改变而被触发
     * <p>
     *  如果侦听器为null,则不抛出异常,并且不执行任何操作
     * 
     * 
     * @param propertyName one of the property names listed above
     * @param listener the PropertyChangeListener to be added
     *
     * @see #addPropertyChangeListener(java.beans.PropertyChangeListener)
     * @see Component#removePropertyChangeListener
     */
    public void addPropertyChangeListener(String propertyName,
                                          PropertyChangeListener listener) {
        super.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Indicates if this container is a validate root.
     * <p>
     * {@code Window} objects are the validate roots, and, therefore, they
     * override this method to return {@code true}.
     *
     * <p>
     *  指示此容器是否为验证根
     * <p>
     *  {@code Window}对象是验证根,因此,它们覆盖此方法返回{@code true}
     * 
     * 
     * @return {@code true}
     * @since 1.7
     * @see java.awt.Container#isValidateRoot
     */
    @Override
    public boolean isValidateRoot() {
        return true;
    }

    /**
     * Dispatches an event to this window or one of its sub components.
     * <p>
     *  将事件分派到此窗口或其子组件之一
     * 
     * 
     * @param e the event
     */
    void dispatchEventImpl(AWTEvent e) {
        if (e.getID() == ComponentEvent.COMPONENT_RESIZED) {
            invalidate();
            validate();
        }
        super.dispatchEventImpl(e);
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1
     * replaced by {@code dispatchEvent(AWTEvent)}.
     */
    @Deprecated
    public boolean postEvent(Event e) {
        if (handleEvent(e)) {
            e.consume();
            return true;
        }
        return false;
    }

    /**
     * Checks if this Window is showing on screen.
     * <p>
     *  检查此窗口是否显示在屏幕上
     * 
     * 
     * @see Component#setVisible
    */
    public boolean isShowing() {
        return visible;
    }

    boolean isDisposing() {
        return disposing;
    }

    /**
    /* <p>
    /* 
     * @deprecated As of J2SE 1.4, replaced by
     * {@link Component#applyComponentOrientation Component.applyComponentOrientation}.
     */
    @Deprecated
    public void applyResourceBundle(ResourceBundle rb) {
        applyComponentOrientation(ComponentOrientation.getOrientation(rb));
    }

    /**
    /* <p>
    /* 
     * @deprecated As of J2SE 1.4, replaced by
     * {@link Component#applyComponentOrientation Component.applyComponentOrientation}.
     */
    @Deprecated
    public void applyResourceBundle(String rbName) {
        applyResourceBundle(ResourceBundle.getBundle(rbName));
    }

   /*
    * Support for tracking all windows owned by this window
    * <p>
    *  支持跟踪此窗口拥有的所有窗口
    * 
    */
    void addOwnedWindow(WeakReference<Window> weakWindow) {
        if (weakWindow != null) {
            synchronized(ownedWindowList) {
                // this if statement should really be an assert, but we don't
                // have asserts...
                if (!ownedWindowList.contains(weakWindow)) {
                    ownedWindowList.addElement(weakWindow);
                }
            }
        }
    }

    void removeOwnedWindow(WeakReference<Window> weakWindow) {
        if (weakWindow != null) {
            // synchronized block not required since removeElement is
            // already synchronized
            ownedWindowList.removeElement(weakWindow);
        }
    }

    void connectOwnedWindow(Window child) {
        child.parent = this;
        addOwnedWindow(child.weakThis);
        child.disposerRecord.updateOwner();
    }

    private void addToWindowList() {
        synchronized (Window.class) {
            @SuppressWarnings("unchecked")
            Vector<WeakReference<Window>> windowList = (Vector<WeakReference<Window>>)appContext.get(Window.class);
            if (windowList == null) {
                windowList = new Vector<WeakReference<Window>>();
                appContext.put(Window.class, windowList);
            }
            windowList.add(weakThis);
        }
    }

    private static void removeFromWindowList(AppContext context, WeakReference<Window> weakThis) {
        synchronized (Window.class) {
            @SuppressWarnings("unchecked")
            Vector<WeakReference<Window>> windowList = (Vector<WeakReference<Window>>)context.get(Window.class);
            if (windowList != null) {
                windowList.remove(weakThis);
            }
        }
    }

    private void removeFromWindowList() {
        removeFromWindowList(appContext, weakThis);
    }

    /**
     * Window type.
     *
     * Synchronization: ObjectLock
     * <p>
     *  窗口类型
     * 
     *  同步：ObjectLock
     * 
     */
    private Type type = Type.NORMAL;

    /**
     * Sets the type of the window.
     *
     * This method can only be called while the window is not displayable.
     *
     * <p>
     *  设置窗口的类型
     * 
     *  此方法只能在窗口不可显示时调用
     * 
     * 
     * @throws IllegalComponentStateException if the window
     *         is displayable.
     * @throws IllegalArgumentException if the type is {@code null}
     * @see    Component#isDisplayable
     * @see    #getType
     * @since 1.7
     */
    public void setType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("type should not be null.");
        }
        synchronized (getTreeLock()) {
            if (isDisplayable()) {
                throw new IllegalComponentStateException(
                        "The window is displayable.");
            }
            synchronized (getObjectLock()) {
                this.type = type;
            }
        }
    }

    /**
     * Returns the type of the window.
     *
     * <p>
     *  返回窗口的类型
     * 
     * 
     * @see   #setType
     * @since 1.7
     */
    public Type getType() {
        synchronized (getObjectLock()) {
            return type;
        }
    }

    /**
     * The window serialized data version.
     *
     * <p>
     * 窗口序列化数据版本
     * 
     * 
     * @serial
     */
    private int windowSerializedDataVersion = 2;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable {@code WindowListener}s and
     * {@code WindowFocusListener}s as optional data.
     * Writes a list of child windows as optional data.
     * Writes a list of icon images as optional data
     *
     * <p>
     *  将默认可序列化字段写入流将可序列化的{@code WindowListener}和{@code WindowFocusListener}列表作为可选数据写入将子窗口列表作为可选数据写入将图标图像列表作
     * 为可选数据。
     * 
     * 
     * @param s the {@code ObjectOutputStream} to write
     * @serialData {@code null} terminated sequence of
     *    0 or more pairs; the pair consists of a {@code String}
     *    and {@code Object}; the {@code String}
     *    indicates the type of object and is one of the following:
     *    {@code windowListenerK} indicating a
     *      {@code WindowListener} object;
     *    {@code windowFocusWindowK} indicating a
     *      {@code WindowFocusListener} object;
     *    {@code ownedWindowK} indicating a child
     *      {@code Window} object
     *
     * @see AWTEventMulticaster#save(java.io.ObjectOutputStream, java.lang.String, java.util.EventListener)
     * @see Component#windowListenerK
     * @see Component#windowFocusListenerK
     * @see Component#ownedWindowK
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        synchronized (this) {
            // Update old focusMgr fields so that our object stream can be read
            // by previous releases
            focusMgr = new FocusManager();
            focusMgr.focusRoot = this;
            focusMgr.focusOwner = getMostRecentFocusOwner();

            s.defaultWriteObject();

            // Clear fields so that we don't keep extra references around
            focusMgr = null;

            AWTEventMulticaster.save(s, windowListenerK, windowListener);
            AWTEventMulticaster.save(s, windowFocusListenerK, windowFocusListener);
            AWTEventMulticaster.save(s, windowStateListenerK, windowStateListener);
        }

        s.writeObject(null);

        synchronized (ownedWindowList) {
            for (int i = 0; i < ownedWindowList.size(); i++) {
                Window child = ownedWindowList.elementAt(i).get();
                if (child != null) {
                    s.writeObject(ownedWindowK);
                    s.writeObject(child);
                }
            }
        }
        s.writeObject(null);

        //write icon array
        if (icons != null) {
            for (Image i : icons) {
                if (i instanceof Serializable) {
                    s.writeObject(i);
                }
            }
        }
        s.writeObject(null);
    }

    //
    // Part of deserialization procedure to be called before
    // user's code.
    //
    private void initDeserializedWindow() {
        setWarningString();
        inputContextLock = new Object();

        // Deserialized Windows are not yet visible.
        visible = false;

        weakThis = new WeakReference<>(this);

        anchor = new Object();
        disposerRecord = new WindowDisposerRecord(appContext, this);
        sun.java2d.Disposer.addRecord(anchor, disposerRecord);

        addToWindowList();
        initGC(null);
        ownedWindowList = new Vector<>();
    }

    private void deserializeResources(ObjectInputStream s)
        throws ClassNotFoundException, IOException, HeadlessException {

            if (windowSerializedDataVersion < 2) {
                // Translate old-style focus tracking to new model. For 1.4 and
                // later releases, we'll rely on the Window's initial focusable
                // Component.
                if (focusMgr != null) {
                    if (focusMgr.focusOwner != null) {
                        KeyboardFocusManager.
                            setMostRecentFocusOwner(this, focusMgr.focusOwner);
                    }
                }

                // This field is non-transient and relies on default serialization.
                // However, the default value is insufficient, so we need to set
                // it explicitly for object data streams prior to 1.4.
                focusableWindowState = true;


            }

        Object keyOrNull;
        while(null != (keyOrNull = s.readObject())) {
            String key = ((String)keyOrNull).intern();

            if (windowListenerK == key) {
                addWindowListener((WindowListener)(s.readObject()));
            } else if (windowFocusListenerK == key) {
                addWindowFocusListener((WindowFocusListener)(s.readObject()));
            } else if (windowStateListenerK == key) {
                addWindowStateListener((WindowStateListener)(s.readObject()));
            } else // skip value for unrecognized key
                s.readObject();
        }

        try {
            while (null != (keyOrNull = s.readObject())) {
                String key = ((String)keyOrNull).intern();

                if (ownedWindowK == key)
                    connectOwnedWindow((Window) s.readObject());

                else // skip value for unrecognized key
                    s.readObject();
            }

            //read icons
            Object obj = s.readObject(); //Throws OptionalDataException
                                         //for pre1.6 objects.
            icons = new ArrayList<Image>(); //Frame.readObject() assumes
                                            //pre1.6 version if icons is null.
            while (obj != null) {
                if (obj instanceof Image) {
                    icons.add((Image)obj);
                }
                obj = s.readObject();
            }
        }
        catch (OptionalDataException e) {
            // 1.1 serialized form
            // ownedWindowList will be updated by Frame.readObject
        }

    }

    /**
     * Reads the {@code ObjectInputStream} and an optional
     * list of listeners to receive various events fired by
     * the component; also reads a list of
     * (possibly {@code null}) child windows.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     *  读取{@code ObjectInputStream}和一个可选的侦听器列表,以接收组件触发的各种事件;还读取(可能为{@code null})子窗口的列表将无法识别的键或值将被忽略
     * 
     * 
     * @param s the {@code ObjectInputStream} to read
     * @exception HeadlessException if
     *   {@code GraphicsEnvironment.isHeadless} returns
     *   {@code true}
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
         GraphicsEnvironment.checkHeadless();
         initDeserializedWindow();
         ObjectInputStream.GetField f = s.readFields();

         syncLWRequests = f.get("syncLWRequests", systemSyncLWRequests);
         state = f.get("state", 0);
         focusableWindowState = f.get("focusableWindowState", true);
         windowSerializedDataVersion = f.get("windowSerializedDataVersion", 1);
         locationByPlatform = f.get("locationByPlatform", locationByPlatformProp);
         // Note: 1.4 (or later) doesn't use focusMgr
         focusMgr = (FocusManager)f.get("focusMgr", null);
         Dialog.ModalExclusionType et = (Dialog.ModalExclusionType)
             f.get("modalExclusionType", Dialog.ModalExclusionType.NO_EXCLUDE);
         setModalExclusionType(et); // since 6.0
         boolean aot = f.get("alwaysOnTop", false);
         if(aot) {
             setAlwaysOnTop(aot); // since 1.5; subject to permission check
         }
         shape = (Shape)f.get("shape", null);
         opacity = (Float)f.get("opacity", 1.0f);

         this.securityWarningWidth = 0;
         this.securityWarningHeight = 0;
         this.securityWarningPointX = 2.0;
         this.securityWarningPointY = 0.0;
         this.securityWarningAlignmentX = RIGHT_ALIGNMENT;
         this.securityWarningAlignmentY = TOP_ALIGNMENT;

         deserializeResources(s);
    }

    /*
     * --- Accessibility Support ---
     *
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Gets the AccessibleContext associated with this Window.
     * For windows, the AccessibleContext takes the form of an
     * AccessibleAWTWindow.
     * A new AccessibleAWTWindow instance is created if necessary.
     *
     * <p>
     *  获取与此窗口相关联的AccessibleContext对于Windows,AccessibleContext采用AccessibleAWTWindow的形式如果必要,创建一个新的AccessibleA
     * WTWindow实例。
     * 
     * 
     * @return an AccessibleAWTWindow that serves as the
     *         AccessibleContext of this Window
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTWindow();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * {@code Window} class.  It provides an implementation of the
     * Java Accessibility API appropriate to window user-interface elements.
     * <p>
     * 这个类实现了对{@code Window}类的辅助支持。它提供了一个适用于窗口用户界面元素的Java辅助功能API的实现
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTWindow extends AccessibleAWTContainer
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 13 serialVersionUID
         * 
         */
        private static final long serialVersionUID = 4215068635060671780L;

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see javax.accessibility.AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.WINDOW;
        }

        /**
         * Get the state of this object.
         *
         * <p>
         *  获取此对象的状态
         * 
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see javax.accessibility.AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            if (getFocusOwner() != null) {
                states.add(AccessibleState.ACTIVE);
            }
            return states;
        }

    } // inner class AccessibleAWTWindow

    @Override
    void setGraphicsConfiguration(GraphicsConfiguration gc) {
        if (gc == null) {
            gc = GraphicsEnvironment.
                    getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().
                    getDefaultConfiguration();
        }
        synchronized (getTreeLock()) {
            super.setGraphicsConfiguration(gc);
            if (log.isLoggable(PlatformLogger.Level.FINER)) {
                log.finer("+ Window.setGraphicsConfiguration(): new GC is \n+ " + getGraphicsConfiguration_NoClientCode() + "\n+ this is " + this);
            }
        }
    }

    /**
     * Sets the location of the window relative to the specified
     * component according to the following scenarios.
     * <p>
     * The target screen mentioned below is a screen to which
     * the window should be placed after the setLocationRelativeTo
     * method is called.
     * <ul>
     * <li>If the component is {@code null}, or the {@code
     * GraphicsConfiguration} associated with this component is
     * {@code null}, the window is placed in the center of the
     * screen. The center point can be obtained with the {@link
     * GraphicsEnvironment#getCenterPoint
     * GraphicsEnvironment.getCenterPoint} method.
     * <li>If the component is not {@code null}, but it is not
     * currently showing, the window is placed in the center of
     * the target screen defined by the {@code
     * GraphicsConfiguration} associated with this component.
     * <li>If the component is not {@code null} and is shown on
     * the screen, then the window is located in such a way that
     * the center of the window coincides with the center of the
     * component.
     * </ul>
     * <p>
     * If the screens configuration does not allow the window to
     * be moved from one screen to another, then the window is
     * only placed at the location determined according to the
     * above conditions and its {@code GraphicsConfiguration} is
     * not changed.
     * <p>
     * <b>Note</b>: If the lower edge of the window is out of the screen,
     * then the window is placed to the side of the {@code Component}
     * that is closest to the center of the screen. So if the
     * component is on the right part of the screen, the window
     * is placed to its left, and vice versa.
     * <p>
     * If after the window location has been calculated, the upper,
     * left, or right edge of the window is out of the screen,
     * then the window is located in such a way that the upper,
     * left, or right edge of the window coincides with the
     * corresponding edge of the screen. If both left and right
     * edges of the window are out of the screen, the window is
     * placed at the left side of the screen. The similar placement
     * will occur if both top and bottom edges are out of the screen.
     * In that case, the window is placed at the top side of the screen.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     *  根据以下场景设置窗口相对于指定组件的位置
     * <p>
     *  下面提到的目标屏幕是在调用setLocationRelativeTo方法之后窗口应放置到的屏幕
     * <ul>
     * <li>如果组件是{@code null}或与此组件关联的{@code GraphicsConfiguration}是{@code null},则窗口位于屏幕的中心。
     * 中心点可以通过{ @link GraphicsEnvironment#getCenterPoint GraphicsEnvironmentgetCenterPoint}方法<li>如果组件不是{@code null}
     * ,但当前未显示,则窗口将放置在与{@code GraphicsConfiguration}关联的目标屏幕的中心此组件<li>如果组件不是{@code null}并显示在屏幕上,则窗口的位置使得窗口中心与
     * 组件中心重合。
     * <li>如果组件是{@code null}或与此组件关联的{@code GraphicsConfiguration}是{@code null},则窗口位于屏幕的中心。
     * </ul>
     * <p>
     * 如果屏幕配置不允许窗口从一个屏幕移动到另一个屏幕,则该窗口仅放置在根据上述条件确定的位置,并且其{@code GraphicsConfiguration}不改变
     * <p>
     *  <b>注意</b>：如果窗口的下边缘超出屏幕,则窗口将放置在{@code Component}的最靠近屏幕中心的一侧。因此,如果组件在屏幕的右侧部分,窗口放置在其左侧,反之亦然
     * <p>
     * 如果在计算出窗口位置之后,窗口的上边缘,左边缘或右边缘在屏幕之外,则该窗口被定位成使得窗口的上边缘,左边缘或右边缘与屏幕的相应边缘如果窗口的左右边缘都在屏幕外,则窗口位于屏幕的左侧。
     * 如果顶部和底部边缘都在屏幕外,则会发生类似的放置在这种情况下,则窗口位于屏幕的顶部。
     * <p>
     *  该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     * 
     * @param c  the component in relation to which the window's location
     *           is determined
     * @see java.awt.GraphicsEnvironment#getCenterPoint
     * @since 1.4
     */
    public void setLocationRelativeTo(Component c) {
        // target location
        int dx = 0, dy = 0;
        // target GC
        GraphicsConfiguration gc = getGraphicsConfiguration_NoClientCode();
        Rectangle gcBounds = gc.getBounds();

        Dimension windowSize = getSize();

        // search a top-level of c
        Window componentWindow = SunToolkit.getContainingWindow(c);
        if ((c == null) || (componentWindow == null)) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
            gcBounds = gc.getBounds();
            Point centerPoint = ge.getCenterPoint();
            dx = centerPoint.x - windowSize.width / 2;
            dy = centerPoint.y - windowSize.height / 2;
        } else if (!c.isShowing()) {
            gc = componentWindow.getGraphicsConfiguration();
            gcBounds = gc.getBounds();
            dx = gcBounds.x + (gcBounds.width - windowSize.width) / 2;
            dy = gcBounds.y + (gcBounds.height - windowSize.height) / 2;
        } else {
            gc = componentWindow.getGraphicsConfiguration();
            gcBounds = gc.getBounds();
            Dimension compSize = c.getSize();
            Point compLocation = c.getLocationOnScreen();
            dx = compLocation.x + ((compSize.width - windowSize.width) / 2);
            dy = compLocation.y + ((compSize.height - windowSize.height) / 2);

            // Adjust for bottom edge being offscreen
            if (dy + windowSize.height > gcBounds.y + gcBounds.height) {
                dy = gcBounds.y + gcBounds.height - windowSize.height;
                if (compLocation.x - gcBounds.x + compSize.width / 2 < gcBounds.width / 2) {
                    dx = compLocation.x + compSize.width;
                } else {
                    dx = compLocation.x - windowSize.width;
                }
            }
        }

        // Avoid being placed off the edge of the screen:
        // bottom
        if (dy + windowSize.height > gcBounds.y + gcBounds.height) {
            dy = gcBounds.y + gcBounds.height - windowSize.height;
        }
        // top
        if (dy < gcBounds.y) {
            dy = gcBounds.y;
        }
        // right
        if (dx + windowSize.width > gcBounds.x + gcBounds.width) {
            dx = gcBounds.x + gcBounds.width - windowSize.width;
        }
        // left
        if (dx < gcBounds.x) {
            dx = gcBounds.x;
        }

        setLocation(dx, dy);
    }

    /**
     * Overridden from Component.  Top-level Windows should not propagate a
     * MouseWheelEvent beyond themselves into their owning Windows.
     * <p>
     * 从组件顶层窗口覆盖不应该将MouseWheelEvent传播到他们自己的Windows中
     * 
     */
    void deliverMouseWheelToAncestor(MouseWheelEvent e) {}

    /**
     * Overridden from Component.  Top-level Windows don't dispatch to ancestors
     * <p>
     *  从组件顶层窗口覆盖不会调度到祖先
     * 
     */
    boolean dispatchMouseWheelToAncestor(MouseWheelEvent e) {return false;}

    /**
     * Creates a new strategy for multi-buffering on this component.
     * Multi-buffering is useful for rendering performance.  This method
     * attempts to create the best strategy available with the number of
     * buffers supplied.  It will always create a {@code BufferStrategy}
     * with that number of buffers.
     * A page-flipping strategy is attempted first, then a blitting strategy
     * using accelerated buffers.  Finally, an unaccelerated blitting
     * strategy is used.
     * <p>
     * Each time this method is called,
     * the existing buffer strategy for this component is discarded.
     * <p>
     *  在此组件上创建多缓冲的新策略多缓冲对渲染性能很有用此方法尝试创建可用的最佳策略,并提供所提供的缓冲区数它将始终使用该缓冲区数量创建一个{@code BufferStrategy}首先尝试页面翻转策略,
     * 然后尝试使用加速缓冲器的打乱策略。
     * 最后,使用未加速的打乱策略。
     * <p>
     *  每次调用此方法时,将丢弃此组件的现有缓冲区策略
     * 
     * 
     * @param numBuffers number of buffers to create
     * @exception IllegalArgumentException if numBuffers is less than 1.
     * @exception IllegalStateException if the component is not displayable
     * @see #isDisplayable
     * @see #getBufferStrategy
     * @since 1.4
     */
    public void createBufferStrategy(int numBuffers) {
        super.createBufferStrategy(numBuffers);
    }

    /**
     * Creates a new strategy for multi-buffering on this component with the
     * required buffer capabilities.  This is useful, for example, if only
     * accelerated memory or page flipping is desired (as specified by the
     * buffer capabilities).
     * <p>
     * Each time this method
     * is called, the existing buffer strategy for this component is discarded.
     * <p>
     * 在需要缓冲区能力的组件上创建多缓冲的新策略这是有用的,例如,如果只需要加速内存或页面翻转(由缓冲区功能指定)
     * <p>
     *  每次调用此方法时,将丢弃此组件的现有缓冲区策略
     * 
     * 
     * @param numBuffers number of buffers to create, including the front buffer
     * @param caps the required capabilities for creating the buffer strategy;
     * cannot be {@code null}
     * @exception AWTException if the capabilities supplied could not be
     * supported or met; this may happen, for example, if there is not enough
     * accelerated memory currently available, or if page flipping is specified
     * but not possible.
     * @exception IllegalArgumentException if numBuffers is less than 1, or if
     * caps is {@code null}
     * @see #getBufferStrategy
     * @since 1.4
     */
    public void createBufferStrategy(int numBuffers,
        BufferCapabilities caps) throws AWTException {
        super.createBufferStrategy(numBuffers, caps);
    }

    /**
     * Returns the {@code BufferStrategy} used by this component.  This
     * method will return null if a {@code BufferStrategy} has not yet
     * been created or has been disposed.
     *
     * <p>
     *  返回此组件使用的{@code BufferStrategy}此方法将返回null如果{@code BufferStrategy}尚未创建或已处置
     * 
     * 
     * @return the buffer strategy used by this component
     * @see #createBufferStrategy
     * @since 1.4
     */
    public BufferStrategy getBufferStrategy() {
        return super.getBufferStrategy();
    }

    Component getTemporaryLostComponent() {
        return temporaryLostComponent;
    }
    Component setTemporaryLostComponent(Component component) {
        Component previousComp = temporaryLostComponent;
        // Check that "component" is an acceptable focus owner and don't store it otherwise
        // - or later we will have problems with opposite while handling  WINDOW_GAINED_FOCUS
        if (component == null || component.canBeFocusOwner()) {
            temporaryLostComponent = component;
        } else {
            temporaryLostComponent = null;
        }
        return previousComp;
    }

    /**
     * Checks whether this window can contain focus owner.
     * Verifies that it is focusable and as container it can container focus owner.
     * <p>
     *  检查此窗口是否可以包含focus owner验证其是否可聚焦,以及作为容器可以是容器焦点所有者
     * 
     * 
     * @since 1.5
     */
    boolean canContainFocusOwner(Component focusOwnerCandidate) {
        return super.canContainFocusOwner(focusOwnerCandidate) && isFocusableWindow();
    }

    private boolean locationByPlatform = locationByPlatformProp;


    /**
     * Sets whether this Window should appear at the default location for the
     * native windowing system or at the current location (returned by
     * {@code getLocation}) the next time the Window is made visible.
     * This behavior resembles a native window shown without programmatically
     * setting its location.  Most windowing systems cascade windows if their
     * locations are not explicitly set. The actual location is determined once the
     * window is shown on the screen.
     * <p>
     * This behavior can also be enabled by setting the System Property
     * "java.awt.Window.locationByPlatform" to "true", though calls to this method
     * take precedence.
     * <p>
     * Calls to {@code setVisible}, {@code setLocation} and
     * {@code setBounds} after calling {@code setLocationByPlatform} clear
     * this property of the Window.
     * <p>
     * For example, after the following code is executed:
     * <pre>
     * setLocationByPlatform(true);
     * setVisible(true);
     * boolean flag = isLocationByPlatform();
     * </pre>
     * The window will be shown at platform's default location and
     * {@code flag} will be {@code false}.
     * <p>
     * In the following sample:
     * <pre>
     * setLocationByPlatform(true);
     * setLocation(10, 10);
     * boolean flag = isLocationByPlatform();
     * setVisible(true);
     * </pre>
     * The window will be shown at (10, 10) and {@code flag} will be
     * {@code false}.
     *
     * <p>
     * 设置此窗口是否应显示在本机窗口系统的默认位置,或下一次使窗口可见时显示在当前位置(由{@code getLocation}返回)此行为类似于显示的本机窗口,无需通过编程方式设置其位置窗口系统级联窗口(如
     * 果它们的位置没有明确设置)一旦窗口显示在屏幕上,就确定实际位置。
     * <p>
     *  也可以通过将系统属性"javaawtWindowlocationByPlatform"设置为"true"来启用此行为,但是调用此方法的优先级
     * <p>
     *  调用{@code setLocationByPlatform}之后调用{@code setVisible},{@code setLocation}和{@code setBounds}清除窗口的此属性。
     * <p>
     * 例如,在执行以下代码之后：
     * <pre>
     *  setLocationByPlatform(true); setVisible(true); boolean flag = isLocationByPlatform();
     * </pre>
     *  该窗口将显示在平台的默认位置,{@code flag}将为{@code false}
     * <p>
     *  在以下示例中：
     * <pre>
     *  setLocationByPlatform(true); setLocation(10,10); boolean flag = isLocationByPlatform(); setVisible(t
     * rue);。
     * </pre>
     *  窗口将显示在(10,10),{@code flag}将会是{@code false}
     * 
     * 
     * @param locationByPlatform {@code true} if this Window should appear
     *        at the default location, {@code false} if at the current location
     * @throws IllegalComponentStateException if the window
     *         is showing on screen and locationByPlatform is {@code true}.
     * @see #setLocation
     * @see #isShowing
     * @see #setVisible
     * @see #isLocationByPlatform
     * @see java.lang.System#getProperty(String)
     * @since 1.5
     */
    public void setLocationByPlatform(boolean locationByPlatform) {
        synchronized (getTreeLock()) {
            if (locationByPlatform && isShowing()) {
                throw new IllegalComponentStateException("The window is showing on screen.");
            }
            this.locationByPlatform = locationByPlatform;
        }
    }

    /**
     * Returns {@code true} if this Window will appear at the default location
     * for the native windowing system the next time this Window is made visible.
     * This method always returns {@code false} if the Window is showing on the
     * screen.
     *
     * <p>
     *  返回{@code true}如果此窗口将出现在本窗口系统的默认位置下次此窗口可见此方法总是返回{@code false}如果窗口显示在屏幕上
     * 
     * 
     * @return whether this Window will appear at the default location
     * @see #setLocationByPlatform
     * @see #isShowing
     * @since 1.5
     */
    public boolean isLocationByPlatform() {
        synchronized (getTreeLock()) {
            return locationByPlatform;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * The {@code width} or {@code height} values
     * are automatically enlarged if either is less than
     * the minimum size as specified by previous call to
     * {@code setMinimumSize}.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     * 如果{@code width}或{@code height}值小于先前调用{@code setMinimumSize}所指定的最小大小,则会自动放大
     * <p>
     *  该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     * 
     * @see #getBounds
     * @see #setLocation(int, int)
     * @see #setLocation(Point)
     * @see #setSize(int, int)
     * @see #setSize(Dimension)
     * @see #setMinimumSize
     * @see #setLocationByPlatform
     * @see #isLocationByPlatform
     * @since 1.6
     */
    public void setBounds(int x, int y, int width, int height) {
        synchronized (getTreeLock()) {
            if (getBoundsOp() == ComponentPeer.SET_LOCATION ||
                getBoundsOp() == ComponentPeer.SET_BOUNDS)
            {
                locationByPlatform = false;
            }
            super.setBounds(x, y, width, height);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * The {@code r.width} or {@code r.height} values
     * will be automatically enlarged if either is less than
     * the minimum size as specified by previous call to
     * {@code setMinimumSize}.
     * <p>
     * The method changes the geometry-related data. Therefore,
     * the native windowing system may ignore such requests, or it may modify
     * the requested data, so that the {@code Window} object is placed and sized
     * in a way that corresponds closely to the desktop settings.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     *  如果{@code rwidth}或{@code rheight}值小于先前调用{@code setMinimumSize}所指定的最小大小,则会自动放大
     * <p>
     * 该方法改变与几何相关的数据因此,本地窗口系统可以忽略这样的请求,或者其可以修改所请求的数据,使得{@code Window}对象以与桌面设置紧密对应的方式放置和调整大小
     * 
     * 
     * @see #getBounds
     * @see #setLocation(int, int)
     * @see #setLocation(Point)
     * @see #setSize(int, int)
     * @see #setSize(Dimension)
     * @see #setMinimumSize
     * @see #setLocationByPlatform
     * @see #isLocationByPlatform
     * @since 1.6
     */
    public void setBounds(Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    /**
     * Determines whether this component will be displayed on the screen.
     * <p>
     *  确定此组件是否将显示在屏幕上
     * 
     * 
     * @return {@code true} if the component and all of its ancestors
     *          until a toplevel window are visible, {@code false} otherwise
     */
    boolean isRecursivelyVisible() {
        // 5079694 fix: for a toplevel to be displayed, its parent doesn't have to be visible.
        // We're overriding isRecursivelyVisible to implement this policy.
        return visible;
    }


    // ******************** SHAPES & TRANSPARENCY CODE ********************

    /**
     * Returns the opacity of the window.
     *
     * <p>
     *  返回窗口的不透明度
     * 
     * 
     * @return the opacity of the window
     *
     * @see Window#setOpacity(float)
     * @see GraphicsDevice.WindowTranslucency
     *
     * @since 1.7
     */
    public float getOpacity() {
        synchronized (getTreeLock()) {
            return opacity;
        }
    }

    /**
     * Sets the opacity of the window.
     * <p>
     * The opacity value is in the range [0..1]. Note that setting the opacity
     * level of 0 may or may not disable the mouse event handling on this
     * window. This is a platform-dependent behavior.
     * <p>
     * The following conditions must be met in order to set the opacity value
     * less than {@code 1.0f}:
     * <ul>
     * <li>The {@link GraphicsDevice.WindowTranslucency#TRANSLUCENT TRANSLUCENT}
     * translucency must be supported by the underlying system
     * <li>The window must be undecorated (see {@link Frame#setUndecorated}
     * and {@link Dialog#setUndecorated})
     * <li>The window must not be in full-screen mode (see {@link
     * GraphicsDevice#setFullScreenWindow(Window)})
     * </ul>
     * <p>
     * If the requested opacity value is less than {@code 1.0f}, and any of the
     * above conditions are not met, the window opacity will not change,
     * and the {@code IllegalComponentStateException} will be thrown.
     * <p>
     * The translucency levels of individual pixels may also be effected by the
     * alpha component of their color (see {@link Window#setBackground(Color)}) and the
     * current shape of this window (see {@link #setShape(Shape)}).
     *
     * <p>
     *  设置窗口的不透明度
     * <p>
     *  不透明度值在范围[01]中注意,将不透明度级别设置为0可以禁用或不禁用在此窗口上的鼠标事件处理这是一个平台相关的行为
     * <p>
     *  为了将不透明度值设置为小于{@code 10f},必须满足以下条件：
     * <ul>
     * <li>底层系统必须支援{@link GraphicsDeviceWindowTranslucency#TRANSLUCENT TRANSLUCENT}半透明<li>视窗必须无装饰(请参阅{@link Frame#setUndecorated}
     * 和{@link Dialog#setUndecorated})<li>窗口不能处于全屏模式(请参阅{@link GraphicsDevice#setFullScreenWindow(Window)})。
     * </ul>
     * <p>
     *  如果请求的不透明度值小于{@code 10f},并且不满足上述任何条件,则窗口不透明度将不会更改,并且将抛出{@code IllegalComponentStateException}
     * <p>
     *  单个像素的半透明级别也可以通过它们的颜色的alpha分量(参见{@link Window#setBackground(Color)})和该窗口的当前形状(参见{@link #setShape(Shape)}
     * )。
     * 
     * 
     * @param opacity the opacity level to set to the window
     *
     * @throws IllegalArgumentException if the opacity is out of the range
     *     [0..1]
     * @throws IllegalComponentStateException if the window is decorated and
     *     the opacity is less than {@code 1.0f}
     * @throws IllegalComponentStateException if the window is in full screen
     *     mode, and the opacity is less than {@code 1.0f}
     * @throws UnsupportedOperationException if the {@code
     *     GraphicsDevice.WindowTranslucency#TRANSLUCENT TRANSLUCENT}
     *     translucency is not supported and the opacity is less than
     *     {@code 1.0f}
     *
     * @see Window#getOpacity
     * @see Window#setBackground(Color)
     * @see Window#setShape(Shape)
     * @see Frame#isUndecorated
     * @see Dialog#isUndecorated
     * @see GraphicsDevice.WindowTranslucency
     * @see GraphicsDevice#isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency)
     *
     * @since 1.7
     */
    public void setOpacity(float opacity) {
        synchronized (getTreeLock()) {
            if (opacity < 0.0f || opacity > 1.0f) {
                throw new IllegalArgumentException(
                    "The value of opacity should be in the range [0.0f .. 1.0f].");
            }
            if (opacity < 1.0f) {
                GraphicsConfiguration gc = getGraphicsConfiguration();
                GraphicsDevice gd = gc.getDevice();
                if (gc.getDevice().getFullScreenWindow() == this) {
                    throw new IllegalComponentStateException(
                        "Setting opacity for full-screen window is not supported.");
                }
                if (!gd.isWindowTranslucencySupported(
                    GraphicsDevice.WindowTranslucency.TRANSLUCENT))
                {
                    throw new UnsupportedOperationException(
                        "TRANSLUCENT translucency is not supported.");
                }
            }
            this.opacity = opacity;
            WindowPeer peer = (WindowPeer)getPeer();
            if (peer != null) {
                peer.setOpacity(opacity);
            }
        }
    }

    /**
     * Returns the shape of the window.
     *
     * The value returned by this method may not be the same as
     * previously set with {@code setShape(shape)}, but it is guaranteed
     * to represent the same shape.
     *
     * <p>
     *  返回窗口的形状
     * 
     * 此方法返回的值可能不同于先前使用{@code setShape(shape)}设置的值,但它保证表示相同的形状
     * 
     * 
     * @return the shape of the window or {@code null} if no
     *     shape is specified for the window
     *
     * @see Window#setShape(Shape)
     * @see GraphicsDevice.WindowTranslucency
     *
     * @since 1.7
     */
    public Shape getShape() {
        synchronized (getTreeLock()) {
            return shape == null ? null : new Path2D.Float(shape);
        }
    }

    /**
     * Sets the shape of the window.
     * <p>
     * Setting a shape cuts off some parts of the window. Only the parts that
     * belong to the given {@link Shape} remain visible and clickable. If
     * the shape argument is {@code null}, this method restores the default
     * shape, making the window rectangular on most platforms.
     * <p>
     * The following conditions must be met to set a non-null shape:
     * <ul>
     * <li>The {@link GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSPARENT
     * PERPIXEL_TRANSPARENT} translucency must be supported by the
     * underlying system
     * <li>The window must be undecorated (see {@link Frame#setUndecorated}
     * and {@link Dialog#setUndecorated})
     * <li>The window must not be in full-screen mode (see {@link
     * GraphicsDevice#setFullScreenWindow(Window)})
     * </ul>
     * <p>
     * If the requested shape is not {@code null}, and any of the above
     * conditions are not met, the shape of this window will not change,
     * and either the {@code UnsupportedOperationException} or {@code
     * IllegalComponentStateException} will be thrown.
     * <p>
     * The translucency levels of individual pixels may also be effected by the
     * alpha component of their color (see {@link Window#setBackground(Color)}) and the
     * opacity value (see {@link #setOpacity(float)}). See {@link
     * GraphicsDevice.WindowTranslucency} for more details.
     *
     * <p>
     *  设置窗口的形状
     * <p>
     *  设置形状会截断窗口的某些部分只有属于给定{@link Shape}的部分保持可见和可点击如果shape参数是{@code null},此方法将恢复默认形状,使窗口呈矩形大多数平台
     * <p>
     *  必须满足以下条件才能设置非空形状：
     * <ul>
     * <li>底层系统必须支援{@link GraphicsDeviceWindowTranslucency#PERPIXEL_TRANSPARENT PERPIXEL_TRANSPARENT}半透明<li>
     * 视窗必须无装饰(请参阅{@link Frame#setUndecorated}和{@link Dialog#setUndecorated})<li>窗口不能处于全屏模式(请参阅{@link GraphicsDevice#setFullScreenWindow(Window)}
     * )。
     * </ul>
     * <p>
     *  如果请求的形状不是{@code null},并且不满足上述任何条件,则此窗口的形状不会改变,并且将抛出{@code UnsupportedOperationException}或{@code IllegalComponentStateException}
     * 。
     * <p>
     * 单个像素的半透明度级别也可以通过它们颜色的alpha分量(参见{@link Window#setBackground(Color)})和不透明度值(参见{@link #setOpacity(float)}
     * )来实现。
     *  GraphicsDeviceWindowTranslucency}了解更多信息。
     * 
     * 
     * @param shape the shape to set to the window
     *
     * @throws IllegalComponentStateException if the shape is not {@code
     *     null} and the window is decorated
     * @throws IllegalComponentStateException if the shape is not {@code
     *     null} and the window is in full-screen mode
     * @throws UnsupportedOperationException if the shape is not {@code
     *     null} and {@link GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSPARENT
     *     PERPIXEL_TRANSPARENT} translucency is not supported
     *
     * @see Window#getShape()
     * @see Window#setBackground(Color)
     * @see Window#setOpacity(float)
     * @see Frame#isUndecorated
     * @see Dialog#isUndecorated
     * @see GraphicsDevice.WindowTranslucency
     * @see GraphicsDevice#isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency)
     *
     * @since 1.7
     */
    public void setShape(Shape shape) {
        synchronized (getTreeLock()) {
            if (shape != null) {
                GraphicsConfiguration gc = getGraphicsConfiguration();
                GraphicsDevice gd = gc.getDevice();
                if (gc.getDevice().getFullScreenWindow() == this) {
                    throw new IllegalComponentStateException(
                        "Setting shape for full-screen window is not supported.");
                }
                if (!gd.isWindowTranslucencySupported(
                        GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSPARENT))
                {
                    throw new UnsupportedOperationException(
                        "PERPIXEL_TRANSPARENT translucency is not supported.");
                }
            }
            this.shape = (shape == null) ? null : new Path2D.Float(shape);
            WindowPeer peer = (WindowPeer)getPeer();
            if (peer != null) {
                peer.applyShape(shape == null ? null : Region.getInstance(shape, null));
            }
        }
    }

    /**
     * Gets the background color of this window.
     * <p>
     * Note that the alpha component of the returned color indicates whether
     * the window is in the non-opaque (per-pixel translucent) mode.
     *
     * <p>
     *  获取此窗口的背景颜色
     * <p>
     *  注意,返回颜色的alpha分量表示窗口是否处于非不透明(每像素半透明)模式
     * 
     * 
     * @return this component's background color
     *
     * @see Window#setBackground(Color)
     * @see Window#isOpaque
     * @see GraphicsDevice.WindowTranslucency
     */
    @Override
    public Color getBackground() {
        return super.getBackground();
    }

    /**
     * Sets the background color of this window.
     * <p>
     * If the windowing system supports the {@link
     * GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSLUCENT PERPIXEL_TRANSLUCENT}
     * translucency, the alpha component of the given background color
     * may effect the mode of operation for this window: it indicates whether
     * this window must be opaque (alpha equals {@code 1.0f}) or per-pixel translucent
     * (alpha is less than {@code 1.0f}). If the given background color is
     * {@code null}, the window is considered completely opaque.
     * <p>
     * All the following conditions must be met to enable the per-pixel
     * transparency mode for this window:
     * <ul>
     * <li>The {@link GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSLUCENT
     * PERPIXEL_TRANSLUCENT} translucency must be supported by the graphics
     * device where this window is located
     * <li>The window must be undecorated (see {@link Frame#setUndecorated}
     * and {@link Dialog#setUndecorated})
     * <li>The window must not be in full-screen mode (see {@link
     * GraphicsDevice#setFullScreenWindow(Window)})
     * </ul>
     * <p>
     * If the alpha component of the requested background color is less than
     * {@code 1.0f}, and any of the above conditions are not met, the background
     * color of this window will not change, the alpha component of the given
     * background color will not affect the mode of operation for this window,
     * and either the {@code UnsupportedOperationException} or {@code
     * IllegalComponentStateException} will be thrown.
     * <p>
     * When the window is per-pixel translucent, the drawing sub-system
     * respects the alpha value of each individual pixel. If a pixel gets
     * painted with the alpha color component equal to zero, it becomes
     * visually transparent. If the alpha of the pixel is equal to 1.0f, the
     * pixel is fully opaque. Interim values of the alpha color component make
     * the pixel semi-transparent. In this mode, the background of the window
     * gets painted with the alpha value of the given background color. If the
     * alpha value of the argument of this method is equal to {@code 0}, the
     * background is not painted at all.
     * <p>
     * The actual level of translucency of a given pixel also depends on window
     * opacity (see {@link #setOpacity(float)}), as well as the current shape of
     * this window (see {@link #setShape(Shape)}).
     * <p>
     * Note that painting a pixel with the alpha value of {@code 0} may or may
     * not disable the mouse event handling on this pixel. This is a
     * platform-dependent behavior. To make sure the mouse events do not get
     * dispatched to a particular pixel, the pixel must be excluded from the
     * shape of the window.
     * <p>
     * Enabling the per-pixel translucency mode may change the graphics
     * configuration of this window due to the native platform requirements.
     *
     * <p>
     *  设置此窗口的背景颜色
     * <p>
     * 如果窗口系统支持{@link GraphicsDeviceWindowTranslucency#PERPIXEL_TRANSLUCENT PERPIXEL_TRANSLUCENT}半透明度,给定背景颜色
     * 的alpha分量可能影响此窗口的操作模式：它指示此窗口是否必须是不透明的(alpha等于{@code 10f} )或每像素半透明(alpha小于{@code 10f})如果给定的背景颜色是{@code null}
     * ,则窗口被认为是完全不透明的。
     * <p>
     *  必须满足以下所有条件才能为此窗口启用每像素透明模式：
     * <ul>
     * <li>此窗口所在的图形设备必须支持{@link GraphicsDeviceWindowTranslucency#PERPIXEL_TRANSLUCENT PERPIXEL_TRANSLUCENT}半
     * 透明<li>窗口必须未装饰(请参阅{@link Frame#setUndecorated}和{@link Dialog#setUndecorated} )<li>该窗口不能处于全屏模式(请参阅{@link GraphicsDevice#setFullScreenWindow(Window)}
     * )。
     * </ul>
     * <p>
     *  如果所请求的背景颜色的alpha分量小于{@code 10f},并且不满足上述任何条件,则此窗口的背景颜色不会改变,给定背景颜色的alpha分量不会影响此窗口的操作模式,并且将抛出{@code UnsupportedOperationException}
     * 或{@code IllegalComponentStateException}。
     * <p>
     * 当窗口是每像素半透明时,绘图子系统遵循每个单独像素的α值。如果像素被涂上等于零的α颜色分量,则其变为视觉上透明的。
     * 如果像素的α等于10f ,像素是完全不透明的alpha颜色分量的中间值使像素半透明在这种模式下,窗口的背景被绘制与给定的背景颜色的alpha值如果此方法的参数的alpha值是等于{@code 0},背景
     * 根本没有画。
     * 当窗口是每像素半透明时,绘图子系统遵循每个单独像素的α值。如果像素被涂上等于零的α颜色分量,则其变为视觉上透明的。
     * <p>
     *  给定像素的实际透明度水平还取决于窗口不透明度(请参阅{@link #setOpacity(float)})以及此窗口的当前形状(请参阅{@link #setShape(Shape)})
     * <p>
     * 注意,使用{@code 0}的alpha值绘制像素可能会或不会禁用对此像素的鼠标事件处理这是一个与平台相关的行为要确保鼠标事件不会被分派到特定像素,像素必须从窗口的形状中排除
     * <p>
     *  由于本地平台要求,启用每像素半透明模式可以改变该窗口的图形配置
     * 
     * 
     * @param bgColor the color to become this window's background color.
     *
     * @throws IllegalComponentStateException if the alpha value of the given
     *     background color is less than {@code 1.0f} and the window is decorated
     * @throws IllegalComponentStateException if the alpha value of the given
     *     background color is less than {@code 1.0f} and the window is in
     *     full-screen mode
     * @throws UnsupportedOperationException if the alpha value of the given
     *     background color is less than {@code 1.0f} and {@link
     *     GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSLUCENT
     *     PERPIXEL_TRANSLUCENT} translucency is not supported
     *
     * @see Window#getBackground
     * @see Window#isOpaque
     * @see Window#setOpacity(float)
     * @see Window#setShape(Shape)
     * @see Frame#isUndecorated
     * @see Dialog#isUndecorated
     * @see GraphicsDevice.WindowTranslucency
     * @see GraphicsDevice#isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency)
     * @see GraphicsConfiguration#isTranslucencyCapable()
     */
    @Override
    public void setBackground(Color bgColor) {
        Color oldBg = getBackground();
        super.setBackground(bgColor);
        if (oldBg != null && oldBg.equals(bgColor)) {
            return;
        }
        int oldAlpha = oldBg != null ? oldBg.getAlpha() : 255;
        int alpha = bgColor != null ? bgColor.getAlpha() : 255;
        if ((oldAlpha == 255) && (alpha < 255)) { // non-opaque window
            GraphicsConfiguration gc = getGraphicsConfiguration();
            GraphicsDevice gd = gc.getDevice();
            if (gc.getDevice().getFullScreenWindow() == this) {
                throw new IllegalComponentStateException(
                    "Making full-screen window non opaque is not supported.");
            }
            if (!gc.isTranslucencyCapable()) {
                GraphicsConfiguration capableGC = gd.getTranslucencyCapableGC();
                if (capableGC == null) {
                    throw new UnsupportedOperationException(
                        "PERPIXEL_TRANSLUCENT translucency is not supported");
                }
                setGraphicsConfiguration(capableGC);
            }
            setLayersOpaque(this, false);
        } else if ((oldAlpha < 255) && (alpha == 255)) {
            setLayersOpaque(this, true);
        }
        WindowPeer peer = (WindowPeer)getPeer();
        if (peer != null) {
            peer.setOpaque(alpha == 255);
        }
    }

    /**
     * Indicates if the window is currently opaque.
     * <p>
     * The method returns {@code false} if the background color of the window
     * is not {@code null} and the alpha component of the color is less than
     * {@code 1.0f}. The method returns {@code true} otherwise.
     *
     * <p>
     *  指示窗口当前是否不透明
     * <p>
     *  如果窗口的背景颜色不是{@code null},并且颜色的alpha分量小于{@code 10f},则该方法返回{@code false}该方法返回{@code true}否则
     * 
     * 
     * @return {@code true} if the window is opaque, {@code false} otherwise
     *
     * @see Window#getBackground
     * @see Window#setBackground(Color)
     * @since 1.7
     */
    @Override
    public boolean isOpaque() {
        Color bg = getBackground();
        return bg != null ? bg.getAlpha() == 255 : true;
    }

    private void updateWindow() {
        synchronized (getTreeLock()) {
            WindowPeer peer = (WindowPeer)getPeer();
            if (peer != null) {
                peer.updateWindow();
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.7
     */
    @Override
    public void paint(Graphics g) {
        if (!isOpaque()) {
            Graphics gg = g.create();
            try {
                if (gg instanceof Graphics2D) {
                    gg.setColor(getBackground());
                    ((Graphics2D)gg).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
                    gg.fillRect(0, 0, getWidth(), getHeight());
                }
            } finally {
                gg.dispose();
            }
        }
        super.paint(g);
    }

    private static void setLayersOpaque(Component component, boolean isOpaque) {
        // Shouldn't use instanceof to avoid loading Swing classes
        //    if it's a pure AWT application.
        if (SunToolkit.isInstanceOf(component, "javax.swing.RootPaneContainer")) {
            javax.swing.RootPaneContainer rpc = (javax.swing.RootPaneContainer)component;
            javax.swing.JRootPane root = rpc.getRootPane();
            javax.swing.JLayeredPane lp = root.getLayeredPane();
            Container c = root.getContentPane();
            javax.swing.JComponent content =
                (c instanceof javax.swing.JComponent) ? (javax.swing.JComponent)c : null;
            lp.setOpaque(isOpaque);
            root.setOpaque(isOpaque);
            if (content != null) {
                content.setOpaque(isOpaque);

                // Iterate down one level to see whether we have a JApplet
                // (which is also a RootPaneContainer) which requires processing
                int numChildren = content.getComponentCount();
                if (numChildren > 0) {
                    Component child = content.getComponent(0);
                    // It's OK to use instanceof here because we've
                    // already loaded the RootPaneContainer class by now
                    if (child instanceof javax.swing.RootPaneContainer) {
                        setLayersOpaque(child, isOpaque);
                    }
                }
            }
        }
    }


    // ************************** MIXING CODE *******************************

    // A window has an owner, but it does NOT have a container
    @Override
    final Container getContainer() {
        return null;
    }

    /**
     * Applies the shape to the component
     * <p>
     *  将形状应用于组件
     * 
     * 
     * @param shape Shape to be applied to the component
     */
    @Override
    final void applyCompoundShape(Region shape) {
        // The shape calculated by mixing code is not intended to be applied
        // to windows or frames
    }

    @Override
    final void applyCurrentShape() {
        // The shape calculated by mixing code is not intended to be applied
        // to windows or frames
    }

    @Override
    final void mixOnReshaping() {
        // The shape calculated by mixing code is not intended to be applied
        // to windows or frames
    }

    @Override
    final Point getLocationOnWindow() {
        return new Point(0, 0);
    }

    // ****************** END OF MIXING CODE ********************************

    /**
     * Limit the given double value with the given range.
     * <p>
     *  用给定的范围限制给定的double值
     * 
     */
    private static double limit(double value, double min, double max) {
        value = Math.max(value, min);
        value = Math.min(value, max);
        return value;
    }

    /**
     * Calculate the position of the security warning.
     *
     * This method gets the window location/size as reported by the native
     * system since the locally cached values may represent outdated data.
     *
     * The method is used from the native code, or via AWTAccessor.
     *
     * NOTE: this method is invoked on the toolkit thread, and therefore is not
     * supposed to become public/user-overridable.
     * <p>
     * 计算安全警告的位置
     * 
     *  此方法获取本地系统报告的窗口位置/大小,因为本地缓存的值可能表示过时的数据
     * 
     *  该方法从本地代码或通过AWTAccessor使用
     * 
     *  注意：这个方法在工具包线程上被调用,因此不应该成为公共的/用户可覆盖的
     */
    private Point2D calculateSecurityWarningPosition(double x, double y,
            double w, double h)
    {
        // The position according to the spec of SecurityWarning.setPosition()
        double wx = x + w * securityWarningAlignmentX + securityWarningPointX;
        double wy = y + h * securityWarningAlignmentY + securityWarningPointY;

        // First, make sure the warning is not too far from the window bounds
        wx = Window.limit(wx,
                x - securityWarningWidth - 2,
                x + w + 2);
        wy = Window.limit(wy,
                y - securityWarningHeight - 2,
                y + h + 2);

        // Now make sure the warning window is visible on the screen
        GraphicsConfiguration graphicsConfig =
            getGraphicsConfiguration_NoClientCode();
        Rectangle screenBounds = graphicsConfig.getBounds();
        Insets screenInsets =
            Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfig);

        wx = Window.limit(wx,
                screenBounds.x + screenInsets.left,
                screenBounds.x + screenBounds.width - screenInsets.right
                - securityWarningWidth);
        wy = Window.limit(wy,
                screenBounds.y + screenInsets.top,
                screenBounds.y + screenBounds.height - screenInsets.bottom
                - securityWarningHeight);

        return new Point2D.Double(wx, wy);
    }

    static {
        AWTAccessor.setWindowAccessor(new AWTAccessor.WindowAccessor() {
            public float getOpacity(Window window) {
                return window.opacity;
            }
            public void setOpacity(Window window, float opacity) {
                window.setOpacity(opacity);
            }
            public Shape getShape(Window window) {
                return window.getShape();
            }
            public void setShape(Window window, Shape shape) {
                window.setShape(shape);
            }
            public void setOpaque(Window window, boolean opaque) {
                Color bg = window.getBackground();
                if (bg == null) {
                    bg = new Color(0, 0, 0, 0);
                }
                window.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue(),
                                               opaque ? 255 : 0));
            }
            public void updateWindow(Window window) {
                window.updateWindow();
            }

            public Dimension getSecurityWarningSize(Window window) {
                return new Dimension(window.securityWarningWidth,
                        window.securityWarningHeight);
            }

            public void setSecurityWarningSize(Window window, int width, int height)
            {
                window.securityWarningWidth = width;
                window.securityWarningHeight = height;
            }

            public void setSecurityWarningPosition(Window window,
                    Point2D point, float alignmentX, float alignmentY)
            {
                window.securityWarningPointX = point.getX();
                window.securityWarningPointY = point.getY();
                window.securityWarningAlignmentX = alignmentX;
                window.securityWarningAlignmentY = alignmentY;

                synchronized (window.getTreeLock()) {
                    WindowPeer peer = (WindowPeer)window.getPeer();
                    if (peer != null) {
                        peer.repositionSecurityWarning();
                    }
                }
            }

            public Point2D calculateSecurityWarningPosition(Window window,
                    double x, double y, double w, double h)
            {
                return window.calculateSecurityWarningPosition(x, y, w, h);
            }

            public void setLWRequestStatus(Window changed, boolean status) {
                changed.syncLWRequests = status;
            }

            public boolean isAutoRequestFocus(Window w) {
                return w.autoRequestFocus;
            }

            public boolean isTrayIconWindow(Window w) {
                return w.isTrayIconWindow;
            }

            public void setTrayIconWindow(Window w, boolean isTrayIconWindow) {
                w.isTrayIconWindow = isTrayIconWindow;
            }
        }); // WindowAccessor
    } // static

    // a window doesn't need to be updated in the Z-order.
    @Override
    void updateZOrder() {}

} // class Window


/**
 * This class is no longer used, but is maintained for Serialization
 * backward-compatibility.
 * <p>
 * 
 */
class FocusManager implements java.io.Serializable {
    Container focusRoot;
    Component focusOwner;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  此类不再使用,但是为了序列化向后兼容性而维护
     * 
     */
    static final long serialVersionUID = 2491878825643557906L;
}
