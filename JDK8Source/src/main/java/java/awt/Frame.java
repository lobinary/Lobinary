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

import java.awt.peer.FramePeer;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import sun.awt.AppContext;
import sun.awt.SunToolkit;
import sun.awt.AWTAccessor;
import java.lang.ref.WeakReference;
import javax.accessibility.*;

/**
 * A <code>Frame</code> is a top-level window with a title and a border.
 * <p>
 * The size of the frame includes any area designated for the
 * border.  The dimensions of the border area may be obtained
 * using the <code>getInsets</code> method, however, since
 * these dimensions are platform-dependent, a valid insets
 * value cannot be obtained until the frame is made displayable
 * by either calling <code>pack</code> or <code>show</code>.
 * Since the border area is included in the overall size of the
 * frame, the border effectively obscures a portion of the frame,
 * constraining the area available for rendering and/or displaying
 * subcomponents to the rectangle which has an upper-left corner
 * location of <code>(insets.left, insets.top)</code>, and has a size of
 * <code>width - (insets.left + insets.right)</code> by
 * <code>height - (insets.top + insets.bottom)</code>.
 * <p>
 * The default layout for a frame is <code>BorderLayout</code>.
 * <p>
 * A frame may have its native decorations (i.e. <code>Frame</code>
 * and <code>Titlebar</code>) turned off
 * with <code>setUndecorated</code>. This can only be done while the frame
 * is not {@link Component#isDisplayable() displayable}.
 * <p>
 * In a multi-screen environment, you can create a <code>Frame</code>
 * on a different screen device by constructing the <code>Frame</code>
 * with {@link #Frame(GraphicsConfiguration)} or
 * {@link #Frame(String title, GraphicsConfiguration)}.  The
 * <code>GraphicsConfiguration</code> object is one of the
 * <code>GraphicsConfiguration</code> objects of the target screen
 * device.
 * <p>
 * In a virtual device multi-screen environment in which the desktop
 * area could span multiple physical screen devices, the bounds of all
 * configurations are relative to the virtual-coordinate system.  The
 * origin of the virtual-coordinate system is at the upper left-hand
 * corner of the primary physical screen.  Depending on the location
 * of the primary screen in the virtual device, negative coordinates
 * are possible, as shown in the following figure.
 * <p>
 * <img src="doc-files/MultiScreen.gif"
 * alt="Diagram of virtual device encompassing three physical screens and one primary physical screen. The primary physical screen
 * shows (0,0) coords while a different physical screen shows (-80,-100) coords."
 * style="float:center; margin: 7px 10px;">
 * <p>
 * In such an environment, when calling <code>setLocation</code>,
 * you must pass a virtual coordinate to this method.  Similarly,
 * calling <code>getLocationOnScreen</code> on a <code>Frame</code>
 * returns virtual device coordinates.  Call the <code>getBounds</code>
 * method of a <code>GraphicsConfiguration</code> to find its origin in
 * the virtual coordinate system.
 * <p>
 * The following code sets the
 * location of the <code>Frame</code> at (10, 10) relative
 * to the origin of the physical screen of the corresponding
 * <code>GraphicsConfiguration</code>.  If the bounds of the
 * <code>GraphicsConfiguration</code> is not taken into account, the
 * <code>Frame</code> location would be set at (10, 10) relative to the
 * virtual-coordinate system and would appear on the primary physical
 * screen, which might be different from the physical screen of the
 * specified <code>GraphicsConfiguration</code>.
 *
 * <pre>
 *      Frame f = new Frame(GraphicsConfiguration gc);
 *      Rectangle bounds = gc.getBounds();
 *      f.setLocation(10 + bounds.x, 10 + bounds.y);
 * </pre>
 *
 * <p>
 * Frames are capable of generating the following types of
 * <code>WindowEvent</code>s:
 * <ul>
 * <li><code>WINDOW_OPENED</code>
 * <li><code>WINDOW_CLOSING</code>:
 *     <br>If the program doesn't
 *     explicitly hide or dispose the window while processing
 *     this event, the window close operation is canceled.
 * <li><code>WINDOW_CLOSED</code>
 * <li><code>WINDOW_ICONIFIED</code>
 * <li><code>WINDOW_DEICONIFIED</code>
 * <li><code>WINDOW_ACTIVATED</code>
 * <li><code>WINDOW_DEACTIVATED</code>
 * <li><code>WINDOW_GAINED_FOCUS</code>
 * <li><code>WINDOW_LOST_FOCUS</code>
 * <li><code>WINDOW_STATE_CHANGED</code>
 * </ul>
 *
 * <p>
 *  <code> Frame </code>是具有标题和边框的顶级窗口。
 * <p>
 *  帧的大小包括为边界指定的任何区域。
 * 边界区域的尺寸可以使用<code> getInsets </code>方法获得,但是,由于这些维度是平台相关的,所以在框架通过调用<code> pack </code>或<code> show </code>
 * 。
 *  帧的大小包括为边界指定的任何区域。
 * 由于边界区域被包括在帧的整体大小中,所以边界有效地遮蔽了帧的一部分,限制了可用于呈现和/或显示具有<code>的左上角位置的矩形的子组件的区域, (insets.left,insets.top)</code>
 * ,并且<code> height  - (insets.top + insets)的大小为<code> width  - (insets.left + insets.right)</code>底部)</code>
 * 。
 *  帧的大小包括为边界指定的任何区域。
 * <p>
 *  框架的默认布局为<code> BorderLayout </code>。
 * <p>
 *  一个帧可以具有用<code> setUndecorated </code>关闭的本地装饰(即<code> Frame </code>和<code> Titlebar </code>)。
 * 这只能在框架不是{@link Component#isDisplayable()displayable}时才能完成。
 * <p>
 * 在多屏幕环境中,您可以通过使用{@link #Frame(GraphicsConfiguration)}或{@link #Frame(GraphicsConfiguration)}构建<code> Fr
 * ame </code> Frame(String title,GraphicsConfiguration)}。
 *  <code> GraphicsConfiguration </code>对象是目标屏幕设备的<code> GraphicsConfiguration </code>对象之一。
 * <p>
 *  在其中桌面区域可以跨越多个物理屏幕设备的虚拟设备多屏幕环境中,所有配置的边界相对于虚拟坐标系统。虚拟坐标系的原点在主物理屏幕的左上角。根据虚拟设备中主屏幕的位置,负坐标是可能的,如下图所示。
 * <p>
 *  <img src ="doc-files / MultiScreen.gif"alt ="包含三个物理屏幕和一个主物理屏幕的虚拟设备图。
 * 主物理屏幕显示(0,0)coords,而不同的物理屏幕显示,-100)coords"。
 * style="float:center; margin: 7px 10px;">
 * <p>
 *  在这种环境中,当调用<code> setLocation </code>时,必须将虚拟坐标传递给此方法。
 * 类似地,在<code> Frame </code>上调用<code> getLocationOnScreen </code>会返回虚拟设备坐标。
 * 调用<code> GraphicsConfiguration </code>的<code> getBounds </code>方法在虚拟坐标系中找到它的原点。
 * <p>
 * 以下代码设置了相对于相应的<code> GraphicsConfiguration </code>的物理屏幕的原点的(10,10)处的<code> Frame </code>的位置。
 * 如果不考虑<code> GraphicsConfiguration </code>的边界,则<code> Frame </code>位置将被设置为相对于虚拟坐标系的(10,10),并且将出现在主物理屏幕
 * ,这可能不同于指定的<code> GraphicsConfiguration </code>的物理屏幕。
 * 以下代码设置了相对于相应的<code> GraphicsConfiguration </code>的物理屏幕的原点的(10,10)处的<code> Frame </code>的位置。
 * 
 * <pre>
 *  Frame f = new Frame(GraphicsConfiguration gc); Rectangle bounds = gc.getBounds(); f.setLocation(10 +
 *  bounds.x,10 + bounds.y);。
 * </pre>
 * 
 * <p>
 *  帧能够生成以下类型的<code> WindowEvent </code>：
 * <ul>
 *  <li> <code> WINDOW_OPENED </code> <li> <code> WINDOW_CLOSING </code>：<br>如果程序在处理此事件时没有显式隐藏或处理窗口,则窗口关
 * 闭操作将被取消。
 *  <li> <code> WINDOW_CLOSED </code> <li> <code> WINDOW_ACTIVATED </code> <li> <code> WINDOW_ICONIFIED 
 * </code> <li> <code> WINDOW_DEICONIFIED </code> WINDOW_DEACTIVATED </code> <li> <code> WINDOW_GAINED_F
 * OCUS </code> <li> <code> WINDOW_LOST_FOCUS </code> <li> <code> WINDOW_STATE_CHANGED </code>。
 * </ul>
 * 
 * 
 * @author      Sami Shaio
 * @see WindowEvent
 * @see Window#addWindowListener
 * @since       JDK1.0
 */
public class Frame extends Window implements MenuContainer {

    /* Note: These are being obsoleted;  programs should use the Cursor class
     * variables going forward. See Cursor and Component.setCursor.
     * <p>
     *  变量。请参见Cursor和Component.setCursor。
     * 
     */

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.DEFAULT_CURSOR</code>.
    */
    @Deprecated
    public static final int     DEFAULT_CURSOR                  = Cursor.DEFAULT_CURSOR;


   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.CROSSHAIR_CURSOR</code>.
    */
    @Deprecated
    public static final int     CROSSHAIR_CURSOR                = Cursor.CROSSHAIR_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.TEXT_CURSOR</code>.
    */
    @Deprecated
    public static final int     TEXT_CURSOR                     = Cursor.TEXT_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.WAIT_CURSOR</code>.
    */
    @Deprecated
    public static final int     WAIT_CURSOR                     = Cursor.WAIT_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.SW_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     SW_RESIZE_CURSOR                = Cursor.SW_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.SE_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     SE_RESIZE_CURSOR                = Cursor.SE_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.NW_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     NW_RESIZE_CURSOR                = Cursor.NW_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.NE_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     NE_RESIZE_CURSOR                = Cursor.NE_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.N_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     N_RESIZE_CURSOR                 = Cursor.N_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.S_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     S_RESIZE_CURSOR                 = Cursor.S_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.W_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     W_RESIZE_CURSOR                 = Cursor.W_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.E_RESIZE_CURSOR</code>.
    */
    @Deprecated
    public static final int     E_RESIZE_CURSOR                 = Cursor.E_RESIZE_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.HAND_CURSOR</code>.
    */
    @Deprecated
    public static final int     HAND_CURSOR                     = Cursor.HAND_CURSOR;

   /**
   /* <p>
   /* 
    * @deprecated   replaced by <code>Cursor.MOVE_CURSOR</code>.
    */
    @Deprecated
    public static final int     MOVE_CURSOR                     = Cursor.MOVE_CURSOR;


    /**
     * Frame is in the "normal" state.  This symbolic constant names a
     * frame state with all state bits cleared.
     * <p>
     *  框架处于"正常"状态。此符号常量命名一个帧状态,并清除所有状态位。
     * 
     * 
     * @see #setExtendedState(int)
     * @see #getExtendedState
     */
    public static final int NORMAL = 0;

    /**
     * This state bit indicates that frame is iconified.
     * <p>
     *  该状态位指示帧被图标化。
     * 
     * 
     * @see #setExtendedState(int)
     * @see #getExtendedState
     */
    public static final int ICONIFIED = 1;

    /**
     * This state bit indicates that frame is maximized in the
     * horizontal direction.
     * <p>
     * 该状态位指示帧在水平方向上最大化。
     * 
     * 
     * @see #setExtendedState(int)
     * @see #getExtendedState
     * @since 1.4
     */
    public static final int MAXIMIZED_HORIZ = 2;

    /**
     * This state bit indicates that frame is maximized in the
     * vertical direction.
     * <p>
     *  该状态位指示帧在垂直方向上被最大化。
     * 
     * 
     * @see #setExtendedState(int)
     * @see #getExtendedState
     * @since 1.4
     */
    public static final int MAXIMIZED_VERT = 4;

    /**
     * This state bit mask indicates that frame is fully maximized
     * (that is both horizontally and vertically).  It is just a
     * convenience alias for
     * <code>MAXIMIZED_VERT&nbsp;|&nbsp;MAXIMIZED_HORIZ</code>.
     *
     * <p>Note that the correct test for frame being fully maximized is
     * <pre>
     *     (state &amp; Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH
     * </pre>
     *
     * <p>To test is frame is maximized in <em>some</em> direction use
     * <pre>
     *     (state &amp; Frame.MAXIMIZED_BOTH) != 0
     * </pre>
     *
     * <p>
     *  该状态位掩码指示帧完全最大化(即水平和垂直)。它只是<code> MAXIMIZED_VERT&nbsp; |&nbsp; MAXIMIZED_HORIZ </code>的一个方便别名。
     * 
     *  <p>请注意,对帧进行完全最大化的正确测试是
     * <pre>
     *  (state&amp; Frame.MAXIMIZED_BOTH)== Frame.MAXIMIZED_BOTH
     * </pre>
     * 
     *  <p>要测试是否在<em>方向使用中最大化框架
     * <pre>
     *  (state&amp; Frame.MAXIMIZED_BOTH)！= 0
     * </pre>
     * 
     * 
     * @see #setExtendedState(int)
     * @see #getExtendedState
     * @since 1.4
     */
    public static final int MAXIMIZED_BOTH = MAXIMIZED_VERT | MAXIMIZED_HORIZ;

    /**
     * Maximized bounds for this frame.
     * <p>
     *  此框架的最大边界。
     * 
     * 
     * @see     #setMaximizedBounds(Rectangle)
     * @see     #getMaximizedBounds
     * @serial
     * @since 1.4
     */
    Rectangle maximizedBounds;


    /**
     * This is the title of the frame.  It can be changed
     * at any time.  <code>title</code> can be null and if
     * this is the case the <code>title</code> = "".
     *
     * <p>
     *  这是框架的标题。它可以随时更改。 <code> title </code>可以为null,如果是<code> title </code> =""。
     * 
     * 
     * @serial
     * @see #getTitle
     * @see #setTitle(String)
     */
    String      title = "Untitled";

    /**
     * The frames menubar.  If <code>menuBar</code> = null
     * the frame will not have a menubar.
     *
     * <p>
     *  框架菜单。如果<code> menuBar </code> = null,框架将不会有菜单栏。
     * 
     * 
     * @serial
     * @see #getMenuBar
     * @see #setMenuBar(MenuBar)
     */
    MenuBar     menuBar;

    /**
     * This field indicates whether the frame is resizable.
     * This property can be changed at any time.
     * <code>resizable</code> will be true if the frame is
     * resizable, otherwise it will be false.
     *
     * <p>
     *  此字段指示框架是否可调整大小。此属性可以随时更改。 <code> resizable </code>将为true,如果框架是可调整大小,否则将为false。
     * 
     * 
     * @serial
     * @see #isResizable()
     */
    boolean     resizable = true;

    /**
     * This field indicates whether the frame is undecorated.
     * This property can only be changed while the frame is not displayable.
     * <code>undecorated</code> will be true if the frame is
     * undecorated, otherwise it will be false.
     *
     * <p>
     *  此字段指示框架是否未装饰。此属性只能在框架无法显示时更改。 <code> undecorated </code>将为true,如果框架未装饰,否则将为false。
     * 
     * 
     * @serial
     * @see #setUndecorated(boolean)
     * @see #isUndecorated()
     * @see Component#isDisplayable()
     * @since 1.4
     */
    boolean undecorated = false;

    /**
     * <code>mbManagement</code> is only used by the Motif implementation.
     *
     * <p>
     *  <code> mbManagement </code>只被Motif实现使用。
     * 
     * 
     * @serial
     */
    boolean     mbManagement = false;   /* used only by the Motif impl. */

    // XXX: uwe: abuse old field for now
    // will need to take care of serialization
    private int state = NORMAL;

    /*
     * The Windows owned by the Frame.
     * Note: in 1.2 this has been superceded by Window.ownedWindowList
     *
     * <p>
     *  Windows拥有的Windows。注意：在1.2这已被Window.ownedWindowList取代
     * 
     * 
     * @serial
     * @see java.awt.Window#ownedWindowList
     */
    Vector<Window> ownedWindows;

    private static final String base = "frame";
    private static int nameCounter = 0;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 2673458971256075116L;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Constructs a new instance of <code>Frame</code> that is
     * initially invisible.  The title of the <code>Frame</code>
     * is empty.
     * <p>
     * 构造一个最初不可见的<code> Frame </code>的新实例。 <code> Frame </code>的标题为空。
     * 
     * 
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless()
     * @see Component#setSize
     * @see Component#setVisible(boolean)
     */
    public Frame() throws HeadlessException {
        this("");
    }

    /**
     * Constructs a new, initially invisible {@code Frame} with the
     * specified {@code GraphicsConfiguration}.
     *
     * <p>
     *  使用指定的{@code GraphicsConfiguration}构造一个新的,初始不可见的{@code Frame}。
     * 
     * 
     * @param gc the <code>GraphicsConfiguration</code>
     * of the target screen device. If <code>gc</code>
     * is <code>null</code>, the system default
     * <code>GraphicsConfiguration</code> is assumed.
     * @exception IllegalArgumentException if
     * <code>gc</code> is not from a screen device.
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless()
     * @since     1.3
     */
    public Frame(GraphicsConfiguration gc) {
        this("", gc);
    }

    /**
     * Constructs a new, initially invisible <code>Frame</code> object
     * with the specified title.
     * <p>
     *  构造一个新的,初始不可见的具有指定标题的<code> Frame </code>对象。
     * 
     * 
     * @param title the title to be displayed in the frame's border.
     *              A <code>null</code> value
     *              is treated as an empty string, "".
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless()
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible(boolean)
     * @see java.awt.GraphicsConfiguration#getBounds
     */
    public Frame(String title) throws HeadlessException {
        init(title, null);
    }

    /**
     * Constructs a new, initially invisible <code>Frame</code> object
     * with the specified title and a
     * <code>GraphicsConfiguration</code>.
     * <p>
     *  构造一个新的,初始不可见的具有指定标题和<code> GraphicsConfiguration </code>的<code> Frame </code>对象。
     * 
     * 
     * @param title the title to be displayed in the frame's border.
     *              A <code>null</code> value
     *              is treated as an empty string, "".
     * @param gc the <code>GraphicsConfiguration</code>
     * of the target screen device.  If <code>gc</code> is
     * <code>null</code>, the system default
     * <code>GraphicsConfiguration</code> is assumed.
     * @exception IllegalArgumentException if <code>gc</code>
     * is not from a screen device.
     * @exception HeadlessException when
     *     <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless()
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible(boolean)
     * @see java.awt.GraphicsConfiguration#getBounds
     * @since 1.3
     */
    public Frame(String title, GraphicsConfiguration gc) {
        super(gc);
        init(title, gc);
    }

    private void init(String title, GraphicsConfiguration gc) {
        this.title = title;
        SunToolkit.checkAndSetPolicy(this);
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     * <p>
     *  构造此组件的名称。当名称为null时由getName()调用。
     * 
     */
    String constructComponentName() {
        synchronized (Frame.class) {
            return base + nameCounter++;
        }
    }

    /**
     * Makes this Frame displayable by connecting it to
     * a native screen resource.  Making a frame displayable will
     * cause any of its children to be made displayable.
     * This method is called internally by the toolkit and should
     * not be called directly by programs.
     * <p>
     *  将此框架连接到本机屏幕资源,使其可显示。使框架可显示将导致它的任何孩子被显示。此方法由工具包在内部调用,不应由程序直接调用。
     * 
     * 
     * @see Component#isDisplayable
     * @see #removeNotify
     */
    public void addNotify() {
        synchronized (getTreeLock()) {
            if (peer == null) {
                peer = getToolkit().createFrame(this);
            }
            FramePeer p = (FramePeer)peer;
            MenuBar menuBar = this.menuBar;
            if (menuBar != null) {
                mbManagement = true;
                menuBar.addNotify();
                p.setMenuBar(menuBar);
            }
            p.setMaximizedBounds(maximizedBounds);
            super.addNotify();
        }
    }

    /**
     * Gets the title of the frame.  The title is displayed in the
     * frame's border.
     * <p>
     *  获取框架的标题。标题显示在框架的边框中。
     * 
     * 
     * @return    the title of this frame, or an empty string ("")
     *                if this frame doesn't have a title.
     * @see       #setTitle(String)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this frame to the specified string.
     * <p>
     *  将此帧的标题设置为指定的字符串。
     * 
     * 
     * @param title the title to be displayed in the frame's border.
     *              A <code>null</code> value
     *              is treated as an empty string, "".
     * @see      #getTitle
     */
    public void setTitle(String title) {
        String oldTitle = this.title;
        if (title == null) {
            title = "";
        }


        synchronized(this) {
            this.title = title;
            FramePeer peer = (FramePeer)this.peer;
            if (peer != null) {
                peer.setTitle(title);
            }
        }
        firePropertyChange("title", oldTitle, title);
    }

    /**
     * Returns the image to be displayed as the icon for this frame.
     * <p>
     * This method is obsolete and kept for backward compatibility
     * only. Use {@link Window#getIconImages Window.getIconImages()} instead.
     * <p>
     * If a list of several images was specified as a Window's icon,
     * this method will return the first item of the list.
     *
     * <p>
     *  返回要显示为此框架图标的图像。
     * <p>
     *  此方法已过时,仅保留向后兼容性。使用{@link Window#getIconImages Window.getIconImages()}。
     * <p>
     *  如果将几个图像的列表指定为窗口的图标,则此方法将返回列表的第一个项目。
     * 
     * 
     * @return    the icon image for this frame, or <code>null</code>
     *                    if this frame doesn't have an icon image.
     * @see       #setIconImage(Image)
     * @see       Window#getIconImages()
     * @see       Window#setIconImages
     */
    public Image getIconImage() {
        java.util.List<Image> icons = this.icons;
        if (icons != null) {
            if (icons.size() > 0) {
                return icons.get(0);
            }
        }
        return null;
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
     * Gets the menu bar for this frame.
     * <p>
     *  获取此框架的菜单栏。
     * 
     * 
     * @return    the menu bar for this frame, or <code>null</code>
     *                   if this frame doesn't have a menu bar.
     * @see       #setMenuBar(MenuBar)
     */
    public MenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Sets the menu bar for this frame to the specified menu bar.
     * <p>
     *  将此框的菜单栏设置为指定的菜单栏。
     * 
     * 
     * @param     mb the menu bar being set.
     *            If this parameter is <code>null</code> then any
     *            existing menu bar on this frame is removed.
     * @see       #getMenuBar
     */
    public void setMenuBar(MenuBar mb) {
        synchronized (getTreeLock()) {
            if (menuBar == mb) {
                return;
            }
            if ((mb != null) && (mb.parent != null)) {
                mb.parent.remove(mb);
            }
            if (menuBar != null) {
                remove(menuBar);
            }
            menuBar = mb;
            if (menuBar != null) {
                menuBar.parent = this;

                FramePeer peer = (FramePeer)this.peer;
                if (peer != null) {
                    mbManagement = true;
                    menuBar.addNotify();
                    invalidateIfValid();
                    peer.setMenuBar(menuBar);
                }
            }
        }
    }

    /**
     * Indicates whether this frame is resizable by the user.
     * By default, all frames are initially resizable.
     * <p>
     *  指示此框架是否可由用户调整大小。默认情况下,所有帧都可以初始调整大小。
     * 
     * 
     * @return    <code>true</code> if the user can resize this frame;
     *                        <code>false</code> otherwise.
     * @see       java.awt.Frame#setResizable(boolean)
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets whether this frame is resizable by the user.
     * <p>
     *  设置此框架是否可由用户调整大小。
     * 
     * 
     * @param    resizable   <code>true</code> if this frame is resizable;
     *                       <code>false</code> otherwise.
     * @see      java.awt.Frame#isResizable
     */
    public void setResizable(boolean resizable) {
        boolean oldResizable = this.resizable;
        boolean testvalid = false;

        synchronized (this) {
            this.resizable = resizable;
            FramePeer peer = (FramePeer)this.peer;
            if (peer != null) {
                peer.setResizable(resizable);
                testvalid = true;
            }
        }

        // On some platforms, changing the resizable state affects
        // the insets of the Frame. If we could, we'd call invalidate()
        // from the peer, but we need to guarantee that we're not holding
        // the Frame lock when we call invalidate().
        if (testvalid) {
            invalidateIfValid();
        }
        firePropertyChange("resizable", oldResizable, resizable);
    }


    /**
     * Sets the state of this frame (obsolete).
     * <p>
     * In older versions of JDK a frame state could only be NORMAL or
     * ICONIFIED.  Since JDK 1.4 set of supported frame states is
     * expanded and frame state is represented as a bitwise mask.
     * <p>
     * For compatibility with applications developed
     * earlier this method still accepts
     * {@code Frame.NORMAL} and
     * {@code Frame.ICONIFIED} only.  The iconic
     * state of the frame is only changed, other aspects
     * of frame state are not affected by this method. If
     * the state passed to this method is neither {@code
     * Frame.NORMAL} nor {@code Frame.ICONIFIED} the
     * method performs no actions at all.
     * <p>Note that if the state is not supported on a
     * given platform, neither the state nor the return
     * value of the {@link #getState} method will be
     * changed. The application may determine whether a
     * specific state is supported via the {@link
     * java.awt.Toolkit#isFrameStateSupported} method.
     * <p><b>If the frame is currently visible on the
     * screen</b> (the {@link #isShowing} method returns
     * {@code true}), the developer should examine the
     * return value of the  {@link
     * java.awt.event.WindowEvent#getNewState} method of
     * the {@code WindowEvent} received through the
     * {@link java.awt.event.WindowStateListener} to
     * determine that the state has actually been
     * changed.
     * <p><b>If the frame is not visible on the
     * screen</b>, the events may or may not be
     * generated.  In this case the developer may assume
     * that the state changes immediately after this
     * method returns.  Later, when the {@code
     * setVisible(true)} method is invoked, the frame
     * will attempt to apply this state. Receiving any
     * {@link
     * java.awt.event.WindowEvent#WINDOW_STATE_CHANGED}
     * events is not guaranteed in this case also.
     *
     * <p>
     * 设置此框架的状态(已过时)。
     * <p>
     *  在旧版本的JDK中,帧状态只能是NORMAL或ICONIFIED。由于JDK 1.4支持的帧状态的集合被扩展,并且帧状态被表示为按位掩码。
     * <p>
     * 为了与先前开发的应用程序兼容,此方法仍然只接受{@code Frame.NORMAL}和{@code Frame.ICONIFIED}。帧的图标状态只被改变,帧状态的其他方面不受这种方法的影响。
     * 如果传递给此方法的状态既不是{@code Frame.NORMAL}也不是{@code Frame.ICONIFIED},则该方法根本不执行任何操作。
     *  <p>请注意,如果在给定平台上不支持状态,{@link #getState}方法的状态和返回值都不会更改。
     * 应用程序可以通过{@link java.awt.Toolkit#isFrameStateSupported}方法确定是否支持特定状态。
     *  <p> <b>如果框架当前在屏幕上可见</b>({@link #isShowing}方法返回{@code true}),开发人员应检查{@link java。
     * 通过{@link java.awt.event.WindowStateListener}接收的{@code WindowEvent}的awt.event.WindowEvent#getNewState}
     * 方法来确定状态实际上已被更改。
     *  <p> <b>如果框架当前在屏幕上可见</b>({@link #isShowing}方法返回{@code true}),开发人员应检查{@link java。
     *  <p> <b>如果框架在屏幕上不可见,则可能生成或可能不生成事件。在这种情况下,开发人员可以假定状态在此方法返回后立即更改。
     * 稍后,当调用{@code setVisible(true)}方法时,框架将尝试应用此状态。
     * 在此情况下,也不保证接收任何{@link java.awt.event.WindowEvent#WINDOW_STATE_CHANGED}个事件。
     * 
     * 
     * @param state either <code>Frame.NORMAL</code> or
     *     <code>Frame.ICONIFIED</code>.
     * @see #setExtendedState(int)
     * @see java.awt.Window#addWindowStateListener
     */
    public synchronized void setState(int state) {
        int current = getExtendedState();
        if (state == ICONIFIED && (current & ICONIFIED) == 0) {
            setExtendedState(current | ICONIFIED);
        }
        else if (state == NORMAL && (current & ICONIFIED) != 0) {
            setExtendedState(current & ~ICONIFIED);
        }
    }

    /**
     * Sets the state of this frame. The state is
     * represented as a bitwise mask.
     * <ul>
     * <li><code>NORMAL</code>
     * <br>Indicates that no state bits are set.
     * <li><code>ICONIFIED</code>
     * <li><code>MAXIMIZED_HORIZ</code>
     * <li><code>MAXIMIZED_VERT</code>
     * <li><code>MAXIMIZED_BOTH</code>
     * <br>Concatenates <code>MAXIMIZED_HORIZ</code>
     * and <code>MAXIMIZED_VERT</code>.
     * </ul>
     * <p>Note that if the state is not supported on a
     * given platform, neither the state nor the return
     * value of the {@link #getExtendedState} method will
     * be changed. The application may determine whether
     * a specific state is supported via the {@link
     * java.awt.Toolkit#isFrameStateSupported} method.
     * <p><b>If the frame is currently visible on the
     * screen</b> (the {@link #isShowing} method returns
     * {@code true}), the developer should examine the
     * return value of the {@link
     * java.awt.event.WindowEvent#getNewState} method of
     * the {@code WindowEvent} received through the
     * {@link java.awt.event.WindowStateListener} to
     * determine that the state has actually been
     * changed.
     * <p><b>If the frame is not visible on the
     * screen</b>, the events may or may not be
     * generated.  In this case the developer may assume
     * that the state changes immediately after this
     * method returns.  Later, when the {@code
     * setVisible(true)} method is invoked, the frame
     * will attempt to apply this state. Receiving any
     * {@link
     * java.awt.event.WindowEvent#WINDOW_STATE_CHANGED}
     * events is not guaranteed in this case also.
     *
     * <p>
     * 设置此帧的状态。状态表示为按位掩码。
     * <ul>
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     *  <li> <code> ICONIFIED </code> <li> <code> MAXIMIZED_HORIZ </code> <li> <code> MAXIMIZED_VERT </code>
     *  <li> <code> MAXIMIZED_BOTH </code> > MAXIMIZED_HORIZ </code>和<code> MAXIMIZED_VERT </code>。
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     * </ul>
     *  <p>请注意,如果在给定平台上不支持状态,则{@link #getExtendedState}方法的状态和返回值都不会更改。
     * 应用程序可以通过{@link java.awt.Toolkit#isFrameStateSupported}方法确定是否支持特定状态。
     *  <p> <b>如果框架当前在屏幕上可见</b>({@link #isShowing}方法返回{@code true}),开发人员应检查{@link java。
     * 通过{@link java.awt.event.WindowStateListener}接收的{@code WindowEvent}的awt.event.WindowEvent#getNewState}
     * 方法来确定状态实际上已被更改。
     *  <p> <b>如果框架当前在屏幕上可见</b>({@link #isShowing}方法返回{@code true}),开发人员应检查{@link java。
     *  <p> <b>如果框架在屏幕上不可见,则可能生成或可能不生成事件。在这种情况下,开发人员可以假定状态在此方法返回后立即更改。
     * 稍后,当调用{@code setVisible(true)}方法时,框架将尝试应用此状态。
     * 在此情况下,也不保证接收任何{@link java.awt.event.WindowEvent#WINDOW_STATE_CHANGED}个事件。
     * 
     * 
     * @param state a bitwise mask of frame state constants
     * @since   1.4
     * @see java.awt.Window#addWindowStateListener
     */
    public void setExtendedState(int state) {
        if ( !isFrameStateSupported( state ) ) {
            return;
        }
        synchronized (getObjectLock()) {
            this.state = state;
        }
        // peer.setState must be called outside of object lock
        // synchronization block to avoid possible deadlock
        FramePeer peer = (FramePeer)this.peer;
        if (peer != null) {
            peer.setState(state);
        }
    }
    private boolean isFrameStateSupported(int state) {
        if( !getToolkit().isFrameStateSupported( state ) ) {
            // * Toolkit.isFrameStateSupported returns always false
            // on compound state even if all parts are supported;
            // * if part of state is not supported, state is not supported;
            // * MAXIMIZED_BOTH is not a compound state.
            if( ((state & ICONIFIED) != 0) &&
                !getToolkit().isFrameStateSupported( ICONIFIED )) {
                return false;
            }else {
                state &= ~ICONIFIED;
            }
            return getToolkit().isFrameStateSupported( state );
        }
        return true;
    }

    /**
     * Gets the state of this frame (obsolete).
     * <p>
     * In older versions of JDK a frame state could only be NORMAL or
     * ICONIFIED.  Since JDK 1.4 set of supported frame states is
     * expanded and frame state is represented as a bitwise mask.
     * <p>
     * For compatibility with old programs this method still returns
     * <code>Frame.NORMAL</code> and <code>Frame.ICONIFIED</code> but
     * it only reports the iconic state of the frame, other aspects of
     * frame state are not reported by this method.
     *
     * <p>
     *  获取此框架的状态(已过时)。
     * <p>
     * 在旧版本的JDK中,帧状态只能是NORMAL或ICONIFIED。由于JDK 1.4支持的帧状态的集合被扩展,并且帧状态被表示为按位掩码。
     * <p>
     *  为了与旧程序兼容,该方法仍然返回<code> Frame.NORMAL </code>和<code> Frame.ICONIFIED </code>,但它只报告帧的图标状态,帧状态的其他方面不报告这个
     * 方法。
     * 
     * 
     * @return  <code>Frame.NORMAL</code> or <code>Frame.ICONIFIED</code>.
     * @see     #setState(int)
     * @see     #getExtendedState
     */
    public synchronized int getState() {
        return (getExtendedState() & ICONIFIED) != 0 ? ICONIFIED : NORMAL;
    }


    /**
     * Gets the state of this frame. The state is
     * represented as a bitwise mask.
     * <ul>
     * <li><code>NORMAL</code>
     * <br>Indicates that no state bits are set.
     * <li><code>ICONIFIED</code>
     * <li><code>MAXIMIZED_HORIZ</code>
     * <li><code>MAXIMIZED_VERT</code>
     * <li><code>MAXIMIZED_BOTH</code>
     * <br>Concatenates <code>MAXIMIZED_HORIZ</code>
     * and <code>MAXIMIZED_VERT</code>.
     * </ul>
     *
     * <p>
     *  获取此帧的状态。状态表示为按位掩码。
     * <ul>
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     *  <li> <code> ICONIFIED </code> <li> <code> MAXIMIZED_HORIZ </code> <li> <code> MAXIMIZED_VERT </code>
     *  <li> <code> MAXIMIZED_BOTH </code> > MAXIMIZED_HORIZ </code>和<code> MAXIMIZED_VERT </code>。
     *  <li> <code> NORMAL </code> <br>表示未设置状态位。
     * </ul>
     * 
     * 
     * @return  a bitwise mask of frame state constants
     * @see     #setExtendedState(int)
     * @since 1.4
     */
    public int getExtendedState() {
        synchronized (getObjectLock()) {
            return state;
        }
    }

    static {
        AWTAccessor.setFrameAccessor(
            new AWTAccessor.FrameAccessor() {
                public void setExtendedState(Frame frame, int state) {
                    synchronized(frame.getObjectLock()) {
                        frame.state = state;
                    }
                }
                public int getExtendedState(Frame frame) {
                    synchronized(frame.getObjectLock()) {
                        return frame.state;
                    }
                }
                public Rectangle getMaximizedBounds(Frame frame) {
                    synchronized(frame.getObjectLock()) {
                        return frame.maximizedBounds;
                    }
                }
            }
        );
    }

    /**
     * Sets the maximized bounds for this frame.
     * <p>
     * When a frame is in maximized state the system supplies some
     * defaults bounds.  This method allows some or all of those
     * system supplied values to be overridden.
     * <p>
     * If <code>bounds</code> is <code>null</code>, accept bounds
     * supplied by the system.  If non-<code>null</code> you can
     * override some of the system supplied values while accepting
     * others by setting those fields you want to accept from system
     * to <code>Integer.MAX_VALUE</code>.
     * <p>
     * Note, the given maximized bounds are used as a hint for the native
     * system, because the underlying platform may not support setting the
     * location and/or size of the maximized windows.  If that is the case, the
     * provided values do not affect the appearance of the frame in the
     * maximized state.
     *
     * <p>
     *  设置此帧的最大边界。
     * <p>
     *  当帧处于最大化状态时,系统提供一些默认边界。此方法允许覆盖某些或所有系统提供的值。
     * <p>
     *  如果<code> bounds </code>是<code> null </code>,请接受系统提供的界限。
     * 如果非<code> null </code>,您可以覆盖一些系统提供的值,同时通过将您希望接受的字段从系统设置为<code> Integer.MAX_VALUE </code>来接受其他值。
     * <p>
     * 注意,给定的最大化边界用作本地系统的提示,因为底层平台可能不支持设置最大化窗口的位置和/或大小。如果是这种情况,则所提供的值不影响最大化状态下的帧的外观。
     * 
     * 
     * @param bounds  bounds for the maximized state
     * @see #getMaximizedBounds()
     * @since 1.4
     */
    public void setMaximizedBounds(Rectangle bounds) {
        synchronized(getObjectLock()) {
            this.maximizedBounds = bounds;
        }
        FramePeer peer = (FramePeer)this.peer;
        if (peer != null) {
            peer.setMaximizedBounds(bounds);
        }
    }

    /**
     * Gets maximized bounds for this frame.
     * Some fields may contain <code>Integer.MAX_VALUE</code> to indicate
     * that system supplied values for this field must be used.
     *
     * <p>
     *  获取此帧的最大边界。某些字段可能包含<code> Integer.MAX_VALUE </code>,表示必须使用该字段的系统提供的值。
     * 
     * 
     * @return  maximized bounds for this frame;  may be <code>null</code>
     * @see     #setMaximizedBounds(Rectangle)
     * @since   1.4
     */
    public Rectangle getMaximizedBounds() {
        synchronized(getObjectLock()) {
            return maximizedBounds;
        }
    }


    /**
     * Disables or enables decorations for this frame.
     * <p>
     * This method can only be called while the frame is not displayable. To
     * make this frame decorated, it must be opaque and have the default shape,
     * otherwise the {@code IllegalComponentStateException} will be thrown.
     * Refer to {@link Window#setShape}, {@link Window#setOpacity} and {@link
     * Window#setBackground} for details
     *
     * <p>
     *  禁用或启用此框架的装饰。
     * <p>
     *  此方法只能在帧不可显示时调用。要使此框架装饰,它必须是不透明的,并具有默认形状,否则将抛出{@code IllegalComponentStateException}。
     * 有关详情,请参阅{@link Window#setShape},{@link Window#setOpacity}和{@link Window#setBackground}。
     * 
     * 
     * @param  undecorated {@code true} if no frame decorations are to be
     *         enabled; {@code false} if frame decorations are to be enabled
     *
     * @throws IllegalComponentStateException if the frame is displayable
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and this frame does not have the default shape
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and this frame opacity is less than {@code 1.0f}
     * @throws IllegalComponentStateException if {@code undecorated} is
     *      {@code false}, and the alpha value of this frame background
     *      color is less than {@code 1.0f}
     *
     * @see    #isUndecorated
     * @see    Component#isDisplayable
     * @see    Window#getShape
     * @see    Window#getOpacity
     * @see    Window#getBackground
     * @see    javax.swing.JFrame#setDefaultLookAndFeelDecorated(boolean)
     *
     * @since 1.4
     */
    public void setUndecorated(boolean undecorated) {
        /* Make sure we don't run in the middle of peer creation.*/
        synchronized (getTreeLock()) {
            if (isDisplayable()) {
                throw new IllegalComponentStateException("The frame is displayable.");
            }
            if (!undecorated) {
                if (getOpacity() < 1.0f) {
                    throw new IllegalComponentStateException("The frame is not opaque");
                }
                if (getShape() != null) {
                    throw new IllegalComponentStateException("The frame does not have a default shape");
                }
                Color bg = getBackground();
                if ((bg != null) && (bg.getAlpha() < 255)) {
                    throw new IllegalComponentStateException("The frame background color is not opaque");
                }
            }
            this.undecorated = undecorated;
        }
    }

    /**
     * Indicates whether this frame is undecorated.
     * By default, all frames are initially decorated.
     * <p>
     *  指示此框架是否未装饰。默认情况下,所有帧都是最初装饰的。
     * 
     * 
     * @return    <code>true</code> if frame is undecorated;
     *                        <code>false</code> otherwise.
     * @see       java.awt.Frame#setUndecorated(boolean)
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
                throw new IllegalComponentStateException("The frame is decorated");
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
                throw new IllegalComponentStateException("The frame is decorated");
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
                throw new IllegalComponentStateException("The frame is decorated");
            }
            super.setBackground(bgColor);
        }
    }

    /**
     * Removes the specified menu bar from this frame.
     * <p>
     *  从此框架中删除指定的菜单栏。
     * 
     * 
     * @param    m   the menu component to remove.
     *           If <code>m</code> is <code>null</code>, then
     *           no action is taken
     */
    public void remove(MenuComponent m) {
        if (m == null) {
            return;
        }
        synchronized (getTreeLock()) {
            if (m == menuBar) {
                menuBar = null;
                FramePeer peer = (FramePeer)this.peer;
                if (peer != null) {
                    mbManagement = true;
                    invalidateIfValid();
                    peer.setMenuBar(null);
                    m.removeNotify();
                }
                m.parent = null;
            } else {
                super.remove(m);
            }
        }
    }

    /**
     * Makes this Frame undisplayable by removing its connection
     * to its native screen resource. Making a Frame undisplayable
     * will cause any of its children to be made undisplayable.
     * This method is called by the toolkit internally and should
     * not be called directly by programs.
     * <p>
     *  通过删除其与其本机屏幕资源的连接,使此框架不可显示。使框架不可显示将导致其任何子项被设置为不可显示。此方法由内部工具包调用,不应由程序直接调用。
     * 
     * 
     * @see Component#isDisplayable
     * @see #addNotify
     */
    public void removeNotify() {
        synchronized (getTreeLock()) {
            FramePeer peer = (FramePeer)this.peer;
            if (peer != null) {
                // get the latest Frame state before disposing
                getState();

                if (menuBar != null) {
                    mbManagement = true;
                    peer.setMenuBar(null);
                    menuBar.removeNotify();
                }
            }
            super.removeNotify();
        }
    }

    void postProcessKeyEvent(KeyEvent e) {
        if (menuBar != null && menuBar.handleShortcut(e)) {
            e.consume();
            return;
        }
        super.postProcessKeyEvent(e);
    }

    /**
     * Returns a string representing the state of this <code>Frame</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     * 返回一个表示此<code> Frame </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return the parameter string of this frame
     */
    protected String paramString() {
        String str = super.paramString();
        if (title != null) {
            str += ",title=" + title;
        }
        if (resizable) {
            str += ",resizable";
        }
        int state = getExtendedState();
        if (state == NORMAL) {
            str += ",normal";
        }
        else {
            if ((state & ICONIFIED) != 0) {
                str += ",iconified";
            }
            if ((state & MAXIMIZED_BOTH) == MAXIMIZED_BOTH) {
                str += ",maximized";
            }
            else if ((state & MAXIMIZED_HORIZ) != 0) {
                str += ",maximized_horiz";
            }
            else if ((state & MAXIMIZED_VERT) != 0) {
                str += ",maximized_vert";
            }
        }
        return str;
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Component.setCursor(Cursor)</code>.
     */
    @Deprecated
    public void setCursor(int cursorType) {
        if (cursorType < DEFAULT_CURSOR || cursorType > MOVE_CURSOR) {
            throw new IllegalArgumentException("illegal cursor type");
        }
        setCursor(Cursor.getPredefinedCursor(cursorType));
    }

    /**
    /* <p>
    /* 
     * @deprecated As of JDK version 1.1,
     * replaced by <code>Component.getCursor()</code>.
     */
    @Deprecated
    public int getCursorType() {
        return (getCursor().getType());
    }

    /**
     * Returns an array of all {@code Frame}s created by this application.
     * If called from an applet, the array includes only the {@code Frame}s
     * accessible by that applet.
     * <p>
     * <b>Warning:</b> this method may return system created frames, such
     * as a shared, hidden frame which is used by Swing. Applications
     * should not assume the existence of these frames, nor should an
     * application assume anything about these frames such as component
     * positions, <code>LayoutManager</code>s or serialization.
     * <p>
     * <b>Note</b>: To obtain a list of all ownerless windows, including
     * ownerless {@code Dialog}s (introduced in release 1.6), use {@link
     * Window#getOwnerlessWindows Window.getOwnerlessWindows}.
     *
     * <p>
     *  返回此应用程序创建的所有{@code Frame}的数组。如果从applet调用,则该数组仅包括该applet可访问的{@code Frame}。
     * <p>
     *  <b>警告：</b>此方法可能会返回系统创建的框架,例如Swing使用的共享的隐藏框架。
     * 应用程序不应假定存在这些帧,应用程序也不应该假设关于这些帧的任何内容,例如组件位置,<code> LayoutManager </code>或序列化。
     * <p>
     *  <b>注意</b>：要获取所有无拥有窗口的列表,包括无所有{@code Dialog}(在1.6版本中介绍),请使用{@link Window#getOwnerlessWindows Window.getOwnerlessWindows}
     * 。
     * 
     * 
     * @see Window#getWindows()
     * @see Window#getOwnerlessWindows
     *
     * @since 1.2
     */
    public static Frame[] getFrames() {
        Window[] allWindows = Window.getWindows();

        int frameCount = 0;
        for (Window w : allWindows) {
            if (w instanceof Frame) {
                frameCount++;
            }
        }

        Frame[] frames = new Frame[frameCount];
        int c = 0;
        for (Window w : allWindows) {
            if (w instanceof Frame) {
                frames[c++] = (Frame)w;
            }
        }

        return frames;
    }

    /* Serialization support.  If there's a MenuBar we restore
     * its (transient) parent field here.  Likewise for top level
     * windows that are "owned" by this frame.
     * <p>
     *  其(临时)父域。同样,对于由此框架"拥有"的顶级窗口。
     * 
     */

    /**
     * <code>Frame</code>'s Serialized Data Version.
     *
     * <p>
     *  <code> Frame </code>的序列化数据版本。
     * 
     * 
     * @serial
     */
    private int frameSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * an optional serializable icon <code>Image</code>, which is
     * available as of 1.4.
     *
     * <p>
     *  将缺省可序列化字段写入流。写入可选的可序列化图标<code> Image </code>,从1.4版开始提供。
     * 
     * 
     * @param s the <code>ObjectOutputStream</code> to write
     * @serialData an optional icon <code>Image</code>
     * @see java.awt.Image
     * @see #getIconImage
     * @see #setIconImage(Image)
     * @see #readObject(ObjectInputStream)
     */
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
        s.defaultWriteObject();
        if (icons != null && icons.size() > 0) {
            Image icon1 = icons.get(0);
            if (icon1 instanceof Serializable) {
                s.writeObject(icon1);
                return;
            }
        }
        s.writeObject(null);
    }

    /**
     * Reads the <code>ObjectInputStream</code>.  Tries
     * to read an icon <code>Image</code>, which is optional
     * data available as of 1.4.  If an icon <code>Image</code>
     * is not available, but anything other than an EOF
     * is detected, an <code>OptionalDataException</code>
     * will be thrown.
     * Unrecognized keys or values will be ignored.
     *
     * <p>
     * 读取<code> ObjectInputStream </code>。尝试读取图标<code> Image </code>,它是1.4版本的可选数据。
     * 如果图标<code> Image </code>不可用,但检测到除EOF之外的任何图标,将抛出<code> OptionalDataException </code>。无法识别的键或值将被忽略。
     * 
     * 
     * @param s the <code>ObjectInputStream</code> to read
     * @exception java.io.OptionalDataException if an icon <code>Image</code>
     *   is not available, but anything other than an EOF
     *   is detected
     * @exception HeadlessException if
     *   <code>GraphicsEnvironment.isHeadless</code> returns
     *   <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless()
     * @see java.awt.Image
     * @see #getIconImage
     * @see #setIconImage(Image)
     * @see #writeObject(ObjectOutputStream)
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
      // HeadlessException is thrown by Window's readObject
      s.defaultReadObject();
      try {
          Image icon = (Image) s.readObject();
          if (icons == null) {
              icons = new ArrayList<Image>();
              icons.add(icon);
          }
      } catch (java.io.OptionalDataException e) {
          // pre-1.4 instances will not have this optional data.
          // 1.6 and later instances serialize icons in the Window class
          // e.eof will be true to indicate that there is no more
          // data available for this object.

          // If e.eof is not true, throw the exception as it
          // might have been caused by unrelated reasons.
          if (!e.eof) {
              throw (e);
          }
      }

      if (menuBar != null)
        menuBar.parent = this;

      // Ensure 1.1 serialized Frames can read & hook-up
      // owned windows properly
      //
      if (ownedWindows != null) {
          for (int i = 0; i < ownedWindows.size(); i++) {
              connectOwnedWindow(ownedWindows.elementAt(i));
          }
          ownedWindows = null;
      }
    }

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    /*
     * --- Accessibility Support ---
     *
     * <p>
     *  ---辅助功能
     * 
     */

    /**
     * Gets the AccessibleContext associated with this Frame.
     * For frames, the AccessibleContext takes the form of an
     * AccessibleAWTFrame.
     * A new AccessibleAWTFrame instance is created if necessary.
     *
     * <p>
     *  获取与此帧相关联的AccessibleContext。对于框架,AccessibleContext采用AccessibleAWTFrame的形式。
     * 如果需要,将创建一个新的AccessibleAWTFrame实例。
     * 
     * 
     * @return an AccessibleAWTFrame that serves as the
     *         AccessibleContext of this Frame
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleAWTFrame();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>Frame</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to frame user-interface elements.
     * <p>
     *  此类实现<code> Frame </code>类的辅助功能支持。它提供了适用于框架用户界面元素的Java辅助功能API的实现。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleAWTFrame extends AccessibleAWTWindow
    {
        /*
         * JDK 1.3 serialVersionUID
         * <p>
         *  JDK 1.3 serialVersionUID
         * 
         */
        private static final long serialVersionUID = -6172960752956030250L;

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
            return AccessibleRole.FRAME;
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
            if (isResizable()) {
                states.add(AccessibleState.RESIZABLE);
            }
            return states;
        }


    } // inner class AccessibleAWTFrame

}
