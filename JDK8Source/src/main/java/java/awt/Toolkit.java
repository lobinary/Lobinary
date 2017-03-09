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

import java.beans.PropertyChangeEvent;
import java.awt.event.*;
import java.awt.peer.*;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.ColorModel;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.net.URL;
import java.io.File;
import java.io.FileInputStream;

import java.util.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import sun.awt.AppContext;

import sun.awt.HeadlessToolkit;
import sun.awt.NullComponentPeer;
import sun.awt.PeerEvent;
import sun.awt.SunToolkit;
import sun.awt.AWTAccessor;
import sun.security.util.SecurityConstants;

import sun.util.CoreResourceBundleControl;

/**
 * This class is the abstract superclass of all actual
 * implementations of the Abstract Window Toolkit. Subclasses of
 * the <code>Toolkit</code> class are used to bind the various components
 * to particular native toolkit implementations.
 * <p>
 * Many GUI events may be delivered to user
 * asynchronously, if the opposite is not specified explicitly.
 * As well as
 * many GUI operations may be performed asynchronously.
 * This fact means that if the state of a component is set, and then
 * the state immediately queried, the returned value may not yet
 * reflect the requested change.  This behavior includes, but is not
 * limited to:
 * <ul>
 * <li>Scrolling to a specified position.
 * <br>For example, calling <code>ScrollPane.setScrollPosition</code>
 *     and then <code>getScrollPosition</code> may return an incorrect
 *     value if the original request has not yet been processed.
 *
 * <li>Moving the focus from one component to another.
 * <br>For more information, see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html#transferTiming">Timing
 * Focus Transfers</a>, a section in
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/">The Swing
 * Tutorial</a>.
 *
 * <li>Making a top-level container visible.
 * <br>Calling <code>setVisible(true)</code> on a <code>Window</code>,
 *     <code>Frame</code> or <code>Dialog</code> may occur
 *     asynchronously.
 *
 * <li>Setting the size or location of a top-level container.
 * <br>Calls to <code>setSize</code>, <code>setBounds</code> or
 *     <code>setLocation</code> on a <code>Window</code>,
 *     <code>Frame</code> or <code>Dialog</code> are forwarded
 *     to the underlying window management system and may be
 *     ignored or modified.  See {@link java.awt.Window} for
 *     more information.
 * </ul>
 * <p>
 * Most applications should not call any of the methods in this
 * class directly. The methods defined by <code>Toolkit</code> are
 * the "glue" that joins the platform-independent classes in the
 * <code>java.awt</code> package with their counterparts in
 * <code>java.awt.peer</code>. Some methods defined by
 * <code>Toolkit</code> query the native operating system directly.
 *
 * <p>
 *  这个类是抽象窗口工具包的所有实际实现的抽象超类。 <code> Toolkit </code>类的子类用于将各种组件绑定到特定的本地工具包实现。
 * <p>
 *  如果没有明确指定相反的情况,许多GUI事件可以异步地传递给用户。以及许多GUI操作可以异步地执行。此事实意味着如果设置了组件的状态,然后状态立即查询,则返回的值可能尚未反映所请求的更改。
 * 此行为包括但不限于：。
 * <ul>
 *  <li>滚动到指定位置。
 *  <br>例如,如果尚未处理原始请求,则调用<code> ScrollPane.setScrollPosition </code>然后调用<code> getScrollPosition </code>
 * 可能会返回不正确的值。
 *  <li>滚动到指定位置。
 * 
 *  <li>将焦点从一个组件移动到另一个组件。
 *  <br>有关详细信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html#transferTiming">
 * 计时聚焦传输</a>, a href ="http://java.sun.com/docs/books/tutorial/uiswing/"> The Swing教程</a>。
 *  <li>将焦点从一个组件移动到另一个组件。
 * 
 *  <li>显示顶层容器。
 *  <br>在<code>窗口</code>,<code>框架</code>或<code>对话框</code>上调用<code> setVisible(true)</code>可能会异步发生。
 * 
 * <li>设置顶级容器的大小或位置。
 *  <br>在<code> Window </code>,<code> Frame </code>上调用<code> setSize </code>,<code> setBounds </code>或<code>
 *  setLocation </code>或<code> Dialog </code>被转发到基础窗口管理系统,并可能被忽略或修改。
 * <li>设置顶级容器的大小或位置。有关详细信息,请参阅{@link java.awt.Window}。
 * </ul>
 * <p>
 *  大多数应用程序不应该直接调用此类中的任何方法。
 *  <code> Toolkit </code>定义的方法是将<code> java.awt </code>包中与平台无关的类与<code> java.awt.peer </code> / code>。
 * 由<code> Toolkit </code>定义的一些方法可以直接查询本机操作系统。
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @author      Fred Ecks
 * @since       JDK1.0
 */
public abstract class Toolkit {

    /**
     * Creates this toolkit's implementation of the <code>Desktop</code>
     * using the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Desktop </code>实现。
     * 
     * 
     * @param     target the desktop to be implemented
     * @return    this toolkit's implementation of the <code>Desktop</code>
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Desktop
     * @see       java.awt.peer.DesktopPeer
     * @since 1.6
     */
    protected abstract DesktopPeer createDesktopPeer(Desktop target)
      throws HeadlessException;


    /**
     * Creates this toolkit's implementation of <code>Button</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Button </code>实现。
     * 
     * 
     * @param     target the button to be implemented.
     * @return    this toolkit's implementation of <code>Button</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Button
     * @see       java.awt.peer.ButtonPeer
     */
    protected abstract ButtonPeer createButton(Button target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>TextField</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> TextField </code>实现。
     * 
     * 
     * @param     target the text field to be implemented.
     * @return    this toolkit's implementation of <code>TextField</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.TextField
     * @see       java.awt.peer.TextFieldPeer
     */
    protected abstract TextFieldPeer createTextField(TextField target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Label</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Label </code>实现。
     * 
     * 
     * @param     target the label to be implemented.
     * @return    this toolkit's implementation of <code>Label</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Label
     * @see       java.awt.peer.LabelPeer
     */
    protected abstract LabelPeer createLabel(Label target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>List</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> List </code>实现。
     * 
     * 
     * @param     target the list to be implemented.
     * @return    this toolkit's implementation of <code>List</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.List
     * @see       java.awt.peer.ListPeer
     */
    protected abstract ListPeer createList(java.awt.List target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Checkbox</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具箱的<code>复选框</code>实现。
     * 
     * 
     * @param     target the check box to be implemented.
     * @return    this toolkit's implementation of <code>Checkbox</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Checkbox
     * @see       java.awt.peer.CheckboxPeer
     */
    protected abstract CheckboxPeer createCheckbox(Checkbox target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Scrollbar</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Scrollbar </code>实现。
     * 
     * 
     * @param     target the scroll bar to be implemented.
     * @return    this toolkit's implementation of <code>Scrollbar</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Scrollbar
     * @see       java.awt.peer.ScrollbarPeer
     */
    protected abstract ScrollbarPeer createScrollbar(Scrollbar target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>ScrollPane</code> using
     * the specified peer interface.
     * <p>
     * 使用指定的对等接口创建此工具包的<code> ScrollPane </code>实现。
     * 
     * 
     * @param     target the scroll pane to be implemented.
     * @return    this toolkit's implementation of <code>ScrollPane</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.ScrollPane
     * @see       java.awt.peer.ScrollPanePeer
     * @since     JDK1.1
     */
    protected abstract ScrollPanePeer createScrollPane(ScrollPane target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>TextArea</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> TextArea </code>实现。
     * 
     * 
     * @param     target the text area to be implemented.
     * @return    this toolkit's implementation of <code>TextArea</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.TextArea
     * @see       java.awt.peer.TextAreaPeer
     */
    protected abstract TextAreaPeer createTextArea(TextArea target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Choice</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具箱的<code> Choice </code>实现。
     * 
     * 
     * @param     target the choice to be implemented.
     * @return    this toolkit's implementation of <code>Choice</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Choice
     * @see       java.awt.peer.ChoicePeer
     */
    protected abstract ChoicePeer createChoice(Choice target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Frame</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Frame </code>实现。
     * 
     * 
     * @param     target the frame to be implemented.
     * @return    this toolkit's implementation of <code>Frame</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Frame
     * @see       java.awt.peer.FramePeer
     */
    protected abstract FramePeer createFrame(Frame target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Canvas</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Canvas </code>实现。
     * 
     * 
     * @param     target the canvas to be implemented.
     * @return    this toolkit's implementation of <code>Canvas</code>.
     * @see       java.awt.Canvas
     * @see       java.awt.peer.CanvasPeer
     */
    protected abstract CanvasPeer       createCanvas(Canvas target);

    /**
     * Creates this toolkit's implementation of <code>Panel</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Panel </code>实现。
     * 
     * 
     * @param     target the panel to be implemented.
     * @return    this toolkit's implementation of <code>Panel</code>.
     * @see       java.awt.Panel
     * @see       java.awt.peer.PanelPeer
     */
    protected abstract PanelPeer        createPanel(Panel target);

    /**
     * Creates this toolkit's implementation of <code>Window</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Window </code>实现。
     * 
     * 
     * @param     target the window to be implemented.
     * @return    this toolkit's implementation of <code>Window</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Window
     * @see       java.awt.peer.WindowPeer
     */
    protected abstract WindowPeer createWindow(Window target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Dialog</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Dialog </code>实现。
     * 
     * 
     * @param     target the dialog to be implemented.
     * @return    this toolkit's implementation of <code>Dialog</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Dialog
     * @see       java.awt.peer.DialogPeer
     */
    protected abstract DialogPeer createDialog(Dialog target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>MenuBar</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> MenuBar </code>实现。
     * 
     * 
     * @param     target the menu bar to be implemented.
     * @return    this toolkit's implementation of <code>MenuBar</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.MenuBar
     * @see       java.awt.peer.MenuBarPeer
     */
    protected abstract MenuBarPeer createMenuBar(MenuBar target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>Menu</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Menu </code>实现。
     * 
     * 
     * @param     target the menu to be implemented.
     * @return    this toolkit's implementation of <code>Menu</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.Menu
     * @see       java.awt.peer.MenuPeer
     */
    protected abstract MenuPeer createMenu(Menu target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>PopupMenu</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> PopupMenu </code>实现。
     * 
     * 
     * @param     target the popup menu to be implemented.
     * @return    this toolkit's implementation of <code>PopupMenu</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.PopupMenu
     * @see       java.awt.peer.PopupMenuPeer
     * @since     JDK1.1
     */
    protected abstract PopupMenuPeer createPopupMenu(PopupMenu target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>MenuItem</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> MenuItem </code>实现。
     * 
     * 
     * @param     target the menu item to be implemented.
     * @return    this toolkit's implementation of <code>MenuItem</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.MenuItem
     * @see       java.awt.peer.MenuItemPeer
     */
    protected abstract MenuItemPeer createMenuItem(MenuItem target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>FileDialog</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> FileDialog </code>实现。
     * 
     * 
     * @param     target the file dialog to be implemented.
     * @return    this toolkit's implementation of <code>FileDialog</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.FileDialog
     * @see       java.awt.peer.FileDialogPeer
     */
    protected abstract FileDialogPeer createFileDialog(FileDialog target)
        throws HeadlessException;

    /**
     * Creates this toolkit's implementation of <code>CheckboxMenuItem</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具箱的<code> CheckboxMenuItem </code>实现。
     * 
     * 
     * @param     target the checkbox menu item to be implemented.
     * @return    this toolkit's implementation of <code>CheckboxMenuItem</code>.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.CheckboxMenuItem
     * @see       java.awt.peer.CheckboxMenuItemPeer
     */
    protected abstract CheckboxMenuItemPeer createCheckboxMenuItem(
        CheckboxMenuItem target) throws HeadlessException;

    /**
     * Obtains this toolkit's implementation of helper class for
     * <code>MouseInfo</code> operations.
     * <p>
     *  获取此工具包的<code> MouseInfo </code>操作的辅助类的实现。
     * 
     * 
     * @return    this toolkit's implementation of  helper for <code>MouseInfo</code>
     * @throws    UnsupportedOperationException if this operation is not implemented
     * @see       java.awt.peer.MouseInfoPeer
     * @see       java.awt.MouseInfo
     * @since 1.5
     */
    protected MouseInfoPeer getMouseInfoPeer() {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static LightweightPeer lightweightMarker;

    /**
     * Creates a peer for a component or container.  This peer is windowless
     * and allows the Component and Container classes to be extended directly
     * to create windowless components that are defined entirely in java.
     *
     * <p>
     * 为组件或容器创建对等体。此对等体是无窗口的,并允许组件和容器类直接扩展以创建完全在java中定义的无窗组件。
     * 
     * 
     * @param target The Component to be created.
     */
    protected LightweightPeer createComponent(Component target) {
        if (lightweightMarker == null) {
            lightweightMarker = new NullComponentPeer();
        }
        return lightweightMarker;
    }

    /**
     * Creates this toolkit's implementation of <code>Font</code> using
     * the specified peer interface.
     * <p>
     *  使用指定的对等接口创建此工具包的<code> Font </code>实现。
     * 
     * 
     * @param     name the font to be implemented
     * @param     style the style of the font, such as <code>PLAIN</code>,
     *            <code>BOLD</code>, <code>ITALIC</code>, or a combination
     * @return    this toolkit's implementation of <code>Font</code>
     * @see       java.awt.Font
     * @see       java.awt.peer.FontPeer
     * @see       java.awt.GraphicsEnvironment#getAllFonts
     * @deprecated  see java.awt.GraphicsEnvironment#getAllFonts
     */
    @Deprecated
    protected abstract FontPeer getFontPeer(String name, int style);

    // The following method is called by the private method
    // <code>updateSystemColors</code> in <code>SystemColor</code>.

    /**
     * Fills in the integer array that is supplied as an argument
     * with the current system color values.
     *
     * <p>
     *  填充作为当前系统颜色值的参数提供的整数数组。
     * 
     * 
     * @param     systemColors an integer array.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     JDK1.1
     */
    protected void loadSystemColors(int[] systemColors)
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
    }

    /**
     * Controls whether the layout of Containers is validated dynamically
     * during resizing, or statically, after resizing is complete.
     * Use {@code isDynamicLayoutActive()} to detect if this feature enabled
     * in this program and is supported by this operating system
     * and/or window manager.
     * Note that this feature is supported not on all platforms, and
     * conversely, that this feature cannot be turned off on some platforms.
     * On these platforms where dynamic layout during resizing is not supported
     * (or is always supported), setting this property has no effect.
     * Note that this feature can be set or unset as a property of the
     * operating system or window manager on some platforms.  On such
     * platforms, the dynamic resize property must be set at the operating
     * system or window manager level before this method can take effect.
     * This method does not change support or settings of the underlying
     * operating system or
     * window manager.  The OS/WM support can be
     * queried using getDesktopProperty("awt.dynamicLayoutSupported") method.
     *
     * <p>
     *  控制是否在调整大小期间动态地验证容器的布局,或者在调整大小完成后静态地验证容器的布局。
     * 使用{@code isDynamicLayoutActive()}来检测此功能是否在此程序中启用,并且此操作系统和/或窗口管理器支持此功能。
     * 请注意,并非所有平台都支持此功能,反之,某些平台上无法关闭此功能。在这些平台上,不支持(或始终支持)调整大小期间的动态布局,设置此属性不起作用。
     * 请注意,此功能可以设置或取消设置为某些平台上的操作系统或窗口管理器的属性。在此类平台上,必须在操作系统或窗口管理器级别设置动态resize属性,然后此方法才能生效。
     * 此方法不会更改底层操作系统或窗口管理器的支持或设置。可以使用getDesktopProperty("awt.dynamicLayoutSupported")方法查询OS / WM支持。
     * 
     * 
     * @param     dynamic  If true, Containers should re-layout their
     *            components as the Container is being resized.  If false,
     *            the layout will be validated after resizing is completed.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true
     * @see       #isDynamicLayoutSet()
     * @see       #isDynamicLayoutActive()
     * @see       #getDesktopProperty(String propertyName)
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     */
    public void setDynamicLayout(final boolean dynamic)
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        if (this != getDefaultToolkit()) {
            getDefaultToolkit().setDynamicLayout(dynamic);
        }
    }

    /**
     * Returns whether the layout of Containers is validated dynamically
     * during resizing, or statically, after resizing is complete.
     * Note: this method returns the value that was set programmatically;
     * it does not reflect support at the level of the operating system
     * or window manager for dynamic layout on resizing, or the current
     * operating system or window manager settings.  The OS/WM support can
     * be queried using getDesktopProperty("awt.dynamicLayoutSupported").
     *
     * <p>
     * 返回在调整大小期间动态地验证容器的布局,或在调整大小完成后静态地验证。
     * 注意：此方法返回以编程方式设置的值;它不反映在操作系统或窗口管理器级别上对于调整大小的动态布局或当前操作系统或窗口管理器设置的支持。
     * 可以使用getDesktopProperty("awt.dynamicLayoutSupported")查询OS / WM支持。
     * 
     * 
     * @return    true if validation of Containers is done dynamically,
     *            false if validation is done after resizing is finished.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true
     * @see       #setDynamicLayout(boolean dynamic)
     * @see       #isDynamicLayoutActive()
     * @see       #getDesktopProperty(String propertyName)
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     */
    protected boolean isDynamicLayoutSet()
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().isDynamicLayoutSet();
        } else {
            return false;
        }
    }

    /**
     * Returns whether dynamic layout of Containers on resize is
     * currently active (both set in program
     *( {@code isDynamicLayoutSet()} )
     *, and supported
     * by the underlying operating system and/or window manager).
     * If dynamic layout is currently inactive then Containers
     * re-layout their components when resizing is completed. As a result
     * the {@code Component.validate()} method will be invoked only
     * once per resize.
     * If dynamic layout is currently active then Containers
     * re-layout their components on every native resize event and
     * the {@code validate()} method will be invoked each time.
     * The OS/WM support can be queried using
     * the getDesktopProperty("awt.dynamicLayoutSupported") method.
     *
     * <p>
     *  返回在调整大小时Containers的动态布局是否当前处于活动状态(都在程序{@code isDynamicLayoutSet()}中设置),并由底层操作系统和/或窗口管理器支持。
     * 如果动态布局当前处于非活动状态,则容器在重新调整大小时重新布局其组件。因此,{@code Component.validate()}方法只会在每次调整大小时调用一次。
     * 如果动态布局当前处于活动状态,则容器会在每个原生resize事件上重新布局其组件,并且每次都将调用{@code validate()}方法。
     * 可以使用getDesktopProperty("awt.dynamicLayoutSupported")方法查询OS / WM支持。
     * 
     * 
     * @return    true if dynamic layout of Containers on resize is
     *            currently active, false otherwise.
     * @exception HeadlessException if the GraphicsEnvironment.isHeadless()
     *            method returns true
     * @see       #setDynamicLayout(boolean dynamic)
     * @see       #isDynamicLayoutSet()
     * @see       #getDesktopProperty(String propertyName)
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     */
    public boolean isDynamicLayoutActive()
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().isDynamicLayoutActive();
        } else {
            return false;
        }
    }

    /**
     * Gets the size of the screen.  On systems with multiple displays, the
     * primary display is used.  Multi-screen aware display dimensions are
     * available from <code>GraphicsConfiguration</code> and
     * <code>GraphicsDevice</code>.
     * <p>
     *  获取屏幕的大小。在具有多个显示器的系统上,使用主显示器。
     * 多屏幕感知显示尺寸可从<code> GraphicsConfiguration </code>和<code> GraphicsDevice </code>获得。
     * 
     * 
     * @return    the size of this toolkit's screen, in pixels.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsConfiguration#getBounds
     * @see       java.awt.GraphicsDevice#getDisplayMode
     * @see       java.awt.GraphicsEnvironment#isHeadless
     */
    public abstract Dimension getScreenSize()
        throws HeadlessException;

    /**
     * Returns the screen resolution in dots-per-inch.
     * <p>
     *  以英寸为单位返回屏幕分辨率。
     * 
     * 
     * @return    this toolkit's screen resolution, in dots-per-inch.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     */
    public abstract int getScreenResolution()
        throws HeadlessException;

    /**
     * Gets the insets of the screen.
     * <p>
     *  获取屏幕的插入。
     * 
     * 
     * @param     gc a <code>GraphicsConfiguration</code>
     * @return    the insets of this toolkit's screen, in pixels.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.4
     */
    public Insets getScreenInsets(GraphicsConfiguration gc)
        throws HeadlessException {
        GraphicsEnvironment.checkHeadless();
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getScreenInsets(gc);
        } else {
            return new Insets(0, 0, 0, 0);
        }
    }

    /**
     * Determines the color model of this toolkit's screen.
     * <p>
     * <code>ColorModel</code> is an abstract class that
     * encapsulates the ability to translate between the
     * pixel values of an image and its red, green, blue,
     * and alpha components.
     * <p>
     * This toolkit method is called by the
     * <code>getColorModel</code> method
     * of the <code>Component</code> class.
     * <p>
     *  确定此工具包屏幕的颜色模型。
     * <p>
     * <code> ColorModel </code>是一个抽象类,它封装了在图像的像素值及其红,绿,蓝和alpha分量之间进行转换的能力。
     * <p>
     *  此工具包方法由<code> Component </code>类的<code> getColorModel </code>方法调用。
     * 
     * 
     * @return    the color model of this toolkit's screen.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.image.ColorModel
     * @see       java.awt.Component#getColorModel
     */
    public abstract ColorModel getColorModel()
        throws HeadlessException;

    /**
     * Returns the names of the available fonts in this toolkit.<p>
     * For 1.1, the following font names are deprecated (the replacement
     * name follows):
     * <ul>
     * <li>TimesRoman (use Serif)
     * <li>Helvetica (use SansSerif)
     * <li>Courier (use Monospaced)
     * </ul><p>
     * The ZapfDingbats fontname is also deprecated in 1.1 but the characters
     * are defined in Unicode starting at 0x2700, and as of 1.1 Java supports
     * those characters.
     * <p>
     *  返回此工具包中可用字体的名称。<p>对于1.1,不推荐使用以下字体名称(替换名称如下)：
     * <ul>
     *  <li> TimeRoman(使用Serif)<li> Helvetica(使用SansSerif)<li> Courier(使用Monospaced)</ul> <p> ZapfDingbats字体
     * 名称在1.1中已弃用,但字符定义在Unicode从0x2700开始,并从1.1版开始支持这些字符。
     * 
     * 
     * @return    the names of the available fonts in this toolkit.
     * @deprecated see {@link java.awt.GraphicsEnvironment#getAvailableFontFamilyNames()}
     * @see java.awt.GraphicsEnvironment#getAvailableFontFamilyNames()
     */
    @Deprecated
    public abstract String[] getFontList();

    /**
     * Gets the screen device metrics for rendering of the font.
     * <p>
     *  获取用于呈现字体的屏幕设备指标。
     * 
     * 
     * @param     font   a font
     * @return    the screen metrics of the specified font in this toolkit
     * @deprecated  As of JDK version 1.2, replaced by the <code>Font</code>
     *          method <code>getLineMetrics</code>.
     * @see java.awt.font.LineMetrics
     * @see java.awt.Font#getLineMetrics
     * @see java.awt.GraphicsEnvironment#getScreenDevices
     */
    @Deprecated
    public abstract FontMetrics getFontMetrics(Font font);

    /**
     * Synchronizes this toolkit's graphics state. Some window systems
     * may do buffering of graphics events.
     * <p>
     * This method ensures that the display is up-to-date. It is useful
     * for animation.
     * <p>
     *  同步此工具包的图形状态。一些窗口系统可以进行图形事件的缓冲。
     * <p>
     *  此方法确保显示是最新的。它对动画有用。
     * 
     */
    public abstract void sync();

    /**
     * The default toolkit.
     * <p>
     *  默认工具包。
     * 
     */
    private static Toolkit toolkit;

    /**
     * Used internally by the assistive technologies functions; set at
     * init time and used at load time
     * <p>
     *  辅助技术功能内部使用;在init时间设置并在加载时使用
     * 
     */
    private static String atNames;

    /**
     * Initializes properties related to assistive technologies.
     * These properties are used both in the loadAssistiveProperties()
     * function below, as well as other classes in the jdk that depend
     * on the properties (such as the use of the screen_magnifier_present
     * property in Java2D hardware acceleration initialization).  The
     * initialization of the properties must be done before the platform-
     * specific Toolkit class is instantiated so that all necessary
     * properties are set up properly before any classes dependent upon them
     * are initialized.
     * <p>
     * 初始化与辅助技术相关的属性。
     * 这些属性在下面的loadAssistiveProperties()函数中使用,以及jdk中依赖于属性的其他类(例如在Java2D硬件加速初始化中使用screen_magnifier_present属性)
     * 。
     * 初始化与辅助技术相关的属性。属性的初始化必须在平台特定的Toolkit类实例化之前完成,以便在依赖于它们的任何类被初始化之前正确地设置所有必要的属性。
     * 
     */
    private static void initAssistiveTechnologies() {

        // Get accessibility properties
        final String sep = File.separator;
        final Properties properties = new Properties();


        atNames = java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<String>() {
            public String run() {

                // Try loading the per-user accessibility properties file.
                try {
                    File propsFile = new File(
                      System.getProperty("user.home") +
                      sep + ".accessibility.properties");
                    FileInputStream in =
                        new FileInputStream(propsFile);

                    // Inputstream has been buffered in Properties class
                    properties.load(in);
                    in.close();
                } catch (Exception e) {
                    // Per-user accessibility properties file does not exist
                }

                // Try loading the system-wide accessibility properties
                // file only if a per-user accessibility properties
                // file does not exist or is empty.
                if (properties.size() == 0) {
                    try {
                        File propsFile = new File(
                            System.getProperty("java.home") + sep + "lib" +
                            sep + "accessibility.properties");
                        FileInputStream in =
                            new FileInputStream(propsFile);

                        // Inputstream has been buffered in Properties class
                        properties.load(in);
                        in.close();
                    } catch (Exception e) {
                        // System-wide accessibility properties file does
                        // not exist;
                    }
                }

                // Get whether a screen magnifier is present.  First check
                // the system property and then check the properties file.
                String magPresent = System.getProperty("javax.accessibility.screen_magnifier_present");
                if (magPresent == null) {
                    magPresent = properties.getProperty("screen_magnifier_present", null);
                    if (magPresent != null) {
                        System.setProperty("javax.accessibility.screen_magnifier_present", magPresent);
                    }
                }

                // Get the names of any assistive technolgies to load.  First
                // check the system property and then check the properties
                // file.
                String classNames = System.getProperty("javax.accessibility.assistive_technologies");
                if (classNames == null) {
                    classNames = properties.getProperty("assistive_technologies", null);
                    if (classNames != null) {
                        System.setProperty("javax.accessibility.assistive_technologies", classNames);
                    }
                }
                return classNames;
            }
        });
    }

    /**
     * Loads additional classes into the VM, using the property
     * 'assistive_technologies' specified in the Sun reference
     * implementation by a line in the 'accessibility.properties'
     * file.  The form is "assistive_technologies=..." where
     * the "..." is a comma-separated list of assistive technology
     * classes to load.  Each class is loaded in the order given
     * and a single instance of each is created using
     * Class.forName(class).newInstance().  All errors are handled
     * via an AWTError exception.
     *
     * <p>The assumption is made that assistive technology classes are supplied
     * as part of INSTALLED (as opposed to: BUNDLED) extensions or specified
     * on the class path
     * (and therefore can be loaded using the class loader returned by
     * a call to <code>ClassLoader.getSystemClassLoader</code>, whose
     * delegation parent is the extension class loader for installed
     * extensions).
     * <p>
     *  使用"accessibility.properties"文件中的行在Sun引用实现中指定的属性"assistive_technologies"将其他类加载到VM中。
     * 形式是"assistive_technologies = ...",其中"..."是加载的辅助技术类的逗号分隔列表。
     * 每个类以给定的顺序加载,并且每个类的单个实例使用Class.forName(类).newInstance()创建。所有错误都通过AWTError异常处理。
     * 
     *  <p>假设辅助技术类作为INSTALLED(而不是BUNDLED)扩展的一部分提供或者在类路径上指定(因此可以使用类调用器返回的类加载器加载<code> ClassLoader .getSystemC
     * lassLoader </code>,其代理父级是已安装扩展的扩展类加载器)。
     * 
     */
    private static void loadAssistiveTechnologies() {
        // Load any assistive technologies
        if (atNames != null) {
            ClassLoader cl = ClassLoader.getSystemClassLoader();
            StringTokenizer parser = new StringTokenizer(atNames," ,");
            String atName;
            while (parser.hasMoreTokens()) {
                atName = parser.nextToken();
                try {
                    Class<?> clazz;
                    if (cl != null) {
                        clazz = cl.loadClass(atName);
                    } else {
                        clazz = Class.forName(atName);
                    }
                    clazz.newInstance();
                } catch (ClassNotFoundException e) {
                    throw new AWTError("Assistive Technology not found: "
                            + atName);
                } catch (InstantiationException e) {
                    throw new AWTError("Could not instantiate Assistive"
                            + " Technology: " + atName);
                } catch (IllegalAccessException e) {
                    throw new AWTError("Could not access Assistive"
                            + " Technology: " + atName);
                } catch (Exception e) {
                    throw new AWTError("Error trying to install Assistive"
                            + " Technology: " + atName + " " + e);
                }
            }
        }
    }

    /**
     * Gets the default toolkit.
     * <p>
     * If a system property named <code>"java.awt.headless"</code> is set
     * to <code>true</code> then the headless implementation
     * of <code>Toolkit</code> is used.
     * <p>
     * If there is no <code>"java.awt.headless"</code> or it is set to
     * <code>false</code> and there is a system property named
     * <code>"awt.toolkit"</code>,
     * that property is treated as the name of a class that is a subclass
     * of <code>Toolkit</code>;
     * otherwise the default platform-specific implementation of
     * <code>Toolkit</code> is used.
     * <p>
     * Also loads additional classes into the VM, using the property
     * 'assistive_technologies' specified in the Sun reference
     * implementation by a line in the 'accessibility.properties'
     * file.  The form is "assistive_technologies=..." where
     * the "..." is a comma-separated list of assistive technology
     * classes to load.  Each class is loaded in the order given
     * and a single instance of each is created using
     * Class.forName(class).newInstance().  This is done just after
     * the AWT toolkit is created.  All errors are handled via an
     * AWTError exception.
     * <p>
     *  获取默认工具包。
     * <p>
     * 如果名为<code>"java.awt.headless"</code>的系统属性设置为<code> true </code>,则使用<code> Toolkit </code>的无头实现。
     * <p>
     *  如果没有<code>"java.awt.headless"</code>或者它设置为<code> false </code>,并且有一个名为<code>"awt.toolkit"的系统属性</code>
     *  ,该属性被视为<code> Toolkit </code>的子类的类的名称;否则使用<code> Toolkit </code>的缺省平台特定实现。
     * <p>
     *  还可以使用"accessibility.properties"文件中的行在Sun引用实现中指定的属性"assistive_technologies"将其他类加载到VM中。
     * 形式是"assistive_technologies = ...",其中"..."是加载的辅助技术类的逗号分隔列表。
     * 每个类以给定的顺序加载,并且每个类的单个实例使用Class.forName(类).newInstance()创建。这是在创建AWT工具包之后完成的。所有错误都通过AWTError异常处理。
     * 
     * 
     * @return    the default toolkit.
     * @exception  AWTError  if a toolkit could not be found, or
     *                 if one could not be accessed or instantiated.
     */
    public static synchronized Toolkit getDefaultToolkit() {
        if (toolkit == null) {
            java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    Class<?> cls = null;
                    String nm = System.getProperty("awt.toolkit");
                    try {
                        cls = Class.forName(nm);
                    } catch (ClassNotFoundException e) {
                        ClassLoader cl = ClassLoader.getSystemClassLoader();
                        if (cl != null) {
                            try {
                                cls = cl.loadClass(nm);
                            } catch (final ClassNotFoundException ignored) {
                                throw new AWTError("Toolkit not found: " + nm);
                            }
                        }
                    }
                    try {
                        if (cls != null) {
                            toolkit = (Toolkit)cls.newInstance();
                            if (GraphicsEnvironment.isHeadless()) {
                                toolkit = new HeadlessToolkit(toolkit);
                            }
                        }
                    } catch (final InstantiationException ignored) {
                        throw new AWTError("Could not instantiate Toolkit: " + nm);
                    } catch (final IllegalAccessException ignored) {
                        throw new AWTError("Could not access Toolkit: " + nm);
                    }
                    return null;
                }
            });
            loadAssistiveTechnologies();
        }
        return toolkit;
    }

    /**
     * Returns an image which gets pixel data from the specified file,
     * whose format can be either GIF, JPEG or PNG.
     * The underlying toolkit attempts to resolve multiple requests
     * with the same filename to the same returned Image.
     * <p>
     * Since the mechanism required to facilitate this sharing of
     * <code>Image</code> objects may continue to hold onto images
     * that are no longer in use for an indefinite period of time,
     * developers are encouraged to implement their own caching of
     * images by using the {@link #createImage(java.lang.String) createImage}
     * variant wherever available.
     * If the image data contained in the specified file changes,
     * the <code>Image</code> object returned from this method may
     * still contain stale information which was loaded from the
     * file after a prior call.
     * Previously loaded image data can be manually discarded by
     * calling the {@link Image#flush flush} method on the
     * returned <code>Image</code>.
     * <p>
     * This method first checks if there is a security manager installed.
     * If so, the method calls the security manager's
     * <code>checkRead</code> method with the file specified to ensure
     * that the access to the image is allowed.
     * <p>
     *  返回从指定文件获取像素数据的图像,其格式可以是GIF,JPEG或PNG。底层工具包尝试使用相同的文件名将多个请求解析为相同的返回图像。
     * <p>
     * 由于促进<code> Image </code>对象的共享所需的机制可以继续保持在不确定的时间段内不再使用的图像,因此鼓励开发者通过使用{@link #createImage(java.lang.String)createImage}
     *  variant。
     * 如果指定文件中包含的图像数据发生更改,则从此方法返回的<code> Image </code>对象仍可能包含过期调用后从文件加载的陈旧信息。
     * 先前加载的图像数据可以通过在返回的<code> Image </code>上调用{@link Image#flush flush}方法来手动删除。
     * <p>
     *  此方法首先检查是否安装了安全管理器。如果是这样,该方法调用安全管理器的<code> checkRead </code>方法与指定的文件,以确保允许对映像的访问。
     * 
     * 
     * @param     filename   the name of a file containing pixel data
     *                         in a recognized file format.
     * @return    an image which gets its pixel data from
     *                         the specified file.
     * @throws SecurityException  if a security manager exists and its
     *                            checkRead method doesn't allow the operation.
     * @see #createImage(java.lang.String)
     */
    public abstract Image getImage(String filename);

    /**
     * Returns an image which gets pixel data from the specified URL.
     * The pixel data referenced by the specified URL must be in one
     * of the following formats: GIF, JPEG or PNG.
     * The underlying toolkit attempts to resolve multiple requests
     * with the same URL to the same returned Image.
     * <p>
     * Since the mechanism required to facilitate this sharing of
     * <code>Image</code> objects may continue to hold onto images
     * that are no longer in use for an indefinite period of time,
     * developers are encouraged to implement their own caching of
     * images by using the {@link #createImage(java.net.URL) createImage}
     * variant wherever available.
     * If the image data stored at the specified URL changes,
     * the <code>Image</code> object returned from this method may
     * still contain stale information which was fetched from the
     * URL after a prior call.
     * Previously loaded image data can be manually discarded by
     * calling the {@link Image#flush flush} method on the
     * returned <code>Image</code>.
     * <p>
     * This method first checks if there is a security manager installed.
     * If so, the method calls the security manager's
     * <code>checkPermission</code> method with the
     * url.openConnection().getPermission() permission to ensure
     * that the access to the image is allowed. For compatibility
     * with pre-1.2 security managers, if the access is denied with
     * <code>FilePermission</code> or <code>SocketPermission</code>,
     * the method throws the <code>SecurityException</code>
     * if the corresponding 1.1-style SecurityManager.checkXXX method
     * also denies permission.
     * <p>
     *  返回从指定的URL获取像素数据的图像。指定网址引用的像素数据必须采用以下格式之一：GIF,JPEG或PNG。底层工具包尝试使用相同的URL将多个请求解析为相同的返回图像。
     * <p>
     * 由于促进<code> Image </code>对象的共享所需的机制可以继续保持在不确定的时间段内不再使用的图像,因此鼓励开发者通过使用{@link #createImage(java.net.URL)createImage}
     *  variant。
     * 如果存储在指定URL的图像数据发生更改,则从此方法返回的<code> Image </code>对象仍可能包含在先前调用后从URL中提取的过时信息。
     * 先前加载的图像数据可以通过在返回的<code> Image </code>上调用{@link Image#flush flush}方法来手动删除。
     * <p>
     *  此方法首先检查是否安装了安全管理器。如果是这样,该方法使用url.openConnection()。
     * getPermission()权限调用安全管理器的<code> checkPermission </code>方法,以确保允许访问映像。
     * 为了与前1.2安全管理器兼容,如果访问被拒绝与<code> FilePermission </code>或<code> SocketPermission </code>,该方法抛出<code> Secu
     * rityException </code>如果相应的1.1-风格SecurityManager.checkXXX方法也拒绝权限。
     * getPermission()权限调用安全管理器的<code> checkPermission </code>方法,以确保允许访问映像。
     * 
     * 
     * @param     url   the URL to use in fetching the pixel data.
     * @return    an image which gets its pixel data from
     *                         the specified URL.
     * @throws SecurityException  if a security manager exists and its
     *                            checkPermission method doesn't allow
     *                            the operation.
     * @see #createImage(java.net.URL)
     */
    public abstract Image getImage(URL url);

    /**
     * Returns an image which gets pixel data from the specified file.
     * The returned Image is a new object which will not be shared
     * with any other caller of this method or its getImage variant.
     * <p>
     * This method first checks if there is a security manager installed.
     * If so, the method calls the security manager's
     * <code>checkRead</code> method with the specified file to ensure
     * that the image creation is allowed.
     * <p>
     *  返回从指定文件获取像素数据的图像。返回的图像是一个新对象,不会与此方法的任何其他调用者或其getImage变体共享。
     * <p>
     * 此方法首先检查是否安装了安全管理器。如果是这样,该方法将使用指定的文件调用安全管理器的<code> checkRead </code>方法,以确保允许创建映像。
     * 
     * 
     * @param     filename   the name of a file containing pixel data
     *                         in a recognized file format.
     * @return    an image which gets its pixel data from
     *                         the specified file.
     * @throws SecurityException  if a security manager exists and its
     *                            checkRead method doesn't allow the operation.
     * @see #getImage(java.lang.String)
     */
    public abstract Image createImage(String filename);

    /**
     * Returns an image which gets pixel data from the specified URL.
     * The returned Image is a new object which will not be shared
     * with any other caller of this method or its getImage variant.
     * <p>
     * This method first checks if there is a security manager installed.
     * If so, the method calls the security manager's
     * <code>checkPermission</code> method with the
     * url.openConnection().getPermission() permission to ensure
     * that the image creation is allowed. For compatibility
     * with pre-1.2 security managers, if the access is denied with
     * <code>FilePermission</code> or <code>SocketPermission</code>,
     * the method throws <code>SecurityException</code>
     * if the corresponding 1.1-style SecurityManager.checkXXX method
     * also denies permission.
     * <p>
     *  返回从指定的URL获取像素数据的图像。返回的图像是一个新对象,不会与此方法的任何其他调用者或其getImage变体共享。
     * <p>
     *  此方法首先检查是否安装了安全管理器。如果是这样,该方法使用url.openConnection()。
     * getPermission()权限调用安全管理器的<code> checkPermission </code>方法,以确保允许创建映像。
     * 为了与pre-1.2安全管理器兼容,如果访问被拒绝与<code> FilePermission </code>或<code> SocketPermission </code>,该方法throws <code>
     *  SecurityException </code>如果相应的1.1-样式SecurityManager.checkXXX方法也拒绝了权限。
     * getPermission()权限调用安全管理器的<code> checkPermission </code>方法,以确保允许创建映像。
     * 
     * 
     * @param     url   the URL to use in fetching the pixel data.
     * @return    an image which gets its pixel data from
     *                         the specified URL.
     * @throws SecurityException  if a security manager exists and its
     *                            checkPermission method doesn't allow
     *                            the operation.
     * @see #getImage(java.net.URL)
     */
    public abstract Image createImage(URL url);

    /**
     * Prepares an image for rendering.
     * <p>
     * If the values of the width and height arguments are both
     * <code>-1</code>, this method prepares the image for rendering
     * on the default screen; otherwise, this method prepares an image
     * for rendering on the default screen at the specified width and height.
     * <p>
     * The image data is downloaded asynchronously in another thread,
     * and an appropriately scaled screen representation of the image is
     * generated.
     * <p>
     * This method is called by components <code>prepareImage</code>
     * methods.
     * <p>
     * Information on the flags returned by this method can be found
     * with the definition of the <code>ImageObserver</code> interface.

     * <p>
     *  准备要渲染的图像。
     * <p>
     *  如果width和height参数的值均为<code> -1 </code>,则此方法准备要在默认屏幕上呈现的图像;否则,此方法准备要在默认屏幕上以指定宽度和高度渲染的图像。
     * <p>
     *  图像数据异步地下载在另一线程中,并且生成图像的适当缩放的屏幕表示。
     * <p>
     *  此方法由组件<code> prepareImage </code>方法调用。
     * <p>
     * 有关此方法返回的标志的信息可以通过<code> ImageObserver </code>接口的定义找到。
     * 
     * 
     * @param     image      the image for which to prepare a
     *                           screen representation.
     * @param     width      the width of the desired screen
     *                           representation, or <code>-1</code>.
     * @param     height     the height of the desired screen
     *                           representation, or <code>-1</code>.
     * @param     observer   the <code>ImageObserver</code>
     *                           object to be notified as the
     *                           image is being prepared.
     * @return    <code>true</code> if the image has already been
     *                 fully prepared; <code>false</code> otherwise.
     * @see       java.awt.Component#prepareImage(java.awt.Image,
     *                 java.awt.image.ImageObserver)
     * @see       java.awt.Component#prepareImage(java.awt.Image,
     *                 int, int, java.awt.image.ImageObserver)
     * @see       java.awt.image.ImageObserver
     */
    public abstract boolean prepareImage(Image image, int width, int height,
                                         ImageObserver observer);

    /**
     * Indicates the construction status of a specified image that is
     * being prepared for display.
     * <p>
     * If the values of the width and height arguments are both
     * <code>-1</code>, this method returns the construction status of
     * a screen representation of the specified image in this toolkit.
     * Otherwise, this method returns the construction status of a
     * scaled representation of the image at the specified width
     * and height.
     * <p>
     * This method does not cause the image to begin loading.
     * An application must call <code>prepareImage</code> to force
     * the loading of an image.
     * <p>
     * This method is called by the component's <code>checkImage</code>
     * methods.
     * <p>
     * Information on the flags returned by this method can be found
     * with the definition of the <code>ImageObserver</code> interface.
     * <p>
     *  指示正在准备显示的指定图像的构造状态。
     * <p>
     *  如果width和height参数的值均为<code> -1 </code>,则此方法返回此工具包中指定图像的屏幕表示的构造状态。否则,此方法返回在指定宽度和高度的图像的缩放表示的构造状态。
     * <p>
     *  此方法不会导致图像开始加载。应用程序必须调用<code> prepareImage </code>强制加载图像。
     * <p>
     *  此方法由组件的<code> checkImage </code>方法调用。
     * <p>
     *  有关此方法返回的标志的信息可以通过<code> ImageObserver </code>接口的定义找到。
     * 
     * 
     * @param     image   the image whose status is being checked.
     * @param     width   the width of the scaled version whose status is
     *                 being checked, or <code>-1</code>.
     * @param     height  the height of the scaled version whose status
     *                 is being checked, or <code>-1</code>.
     * @param     observer   the <code>ImageObserver</code> object to be
     *                 notified as the image is being prepared.
     * @return    the bitwise inclusive <strong>OR</strong> of the
     *                 <code>ImageObserver</code> flags for the
     *                 image data that is currently available.
     * @see       java.awt.Toolkit#prepareImage(java.awt.Image,
     *                 int, int, java.awt.image.ImageObserver)
     * @see       java.awt.Component#checkImage(java.awt.Image,
     *                 java.awt.image.ImageObserver)
     * @see       java.awt.Component#checkImage(java.awt.Image,
     *                 int, int, java.awt.image.ImageObserver)
     * @see       java.awt.image.ImageObserver
     */
    public abstract int checkImage(Image image, int width, int height,
                                   ImageObserver observer);

    /**
     * Creates an image with the specified image producer.
     * <p>
     *  使用指定的图像生成器创建图像。
     * 
     * 
     * @param     producer the image producer to be used.
     * @return    an image with the specified image producer.
     * @see       java.awt.Image
     * @see       java.awt.image.ImageProducer
     * @see       java.awt.Component#createImage(java.awt.image.ImageProducer)
     */
    public abstract Image createImage(ImageProducer producer);

    /**
     * Creates an image which decodes the image stored in the specified
     * byte array.
     * <p>
     * The data must be in some image format, such as GIF or JPEG,
     * that is supported by this toolkit.
     * <p>
     *  创建对存储在指定字节数组中的图像进行解码的图像。
     * <p>
     *  数据必须是某种图像格式,例如GIF或JPEG,该工具包支持。
     * 
     * 
     * @param     imagedata   an array of bytes, representing
     *                         image data in a supported image format.
     * @return    an image.
     * @since     JDK1.1
     */
    public Image createImage(byte[] imagedata) {
        return createImage(imagedata, 0, imagedata.length);
    }

    /**
     * Creates an image which decodes the image stored in the specified
     * byte array, and at the specified offset and length.
     * The data must be in some image format, such as GIF or JPEG,
     * that is supported by this toolkit.
     * <p>
     *  创建一个图像,对存储在指定字节数组中的图像进行解码,并以指定的偏移和长度进行解码。数据必须是某种图像格式,例如GIF或JPEG,该工具包支持。
     * 
     * 
     * @param     imagedata   an array of bytes, representing
     *                         image data in a supported image format.
     * @param     imageoffset  the offset of the beginning
     *                         of the data in the array.
     * @param     imagelength  the length of the data in the array.
     * @return    an image.
     * @since     JDK1.1
     */
    public abstract Image createImage(byte[] imagedata,
                                      int imageoffset,
                                      int imagelength);

    /**
     * Gets a <code>PrintJob</code> object which is the result of initiating
     * a print operation on the toolkit's platform.
     * <p>
     * Each actual implementation of this method should first check if there
     * is a security manager installed. If there is, the method should call
     * the security manager's <code>checkPrintJobAccess</code> method to
     * ensure initiation of a print operation is allowed. If the default
     * implementation of <code>checkPrintJobAccess</code> is used (that is,
     * that method is not overriden), then this results in a call to the
     * security manager's <code>checkPermission</code> method with a <code>
     * RuntimePermission("queuePrintJob")</code> permission.
     *
     * <p>
     *  获取一个<code> PrintJob </code>对象,它是在工具包平台上启动打印操作的结果。
     * <p>
     * 此方法的每个实际实现应首先检查是否安装了安全管理器。如果存在,该方法应该调用安全管理器的<code> checkPrintJobAccess </code>方法,以确保允许启动打印操作。
     * 如果使用<code> checkPrintJobAccess </code>的默认实现(即,该方法不被覆盖),则这将导致对安全管理器的<code> checkPermission </code> Run
     * timePermission("queuePrintJob")</code>权限。
     * 此方法的每个实际实现应首先检查是否安装了安全管理器。如果存在,该方法应该调用安全管理器的<code> checkPrintJobAccess </code>方法,以确保允许启动打印操作。
     * 
     * 
     * @param   frame the parent of the print dialog. May not be null.
     * @param   jobtitle the title of the PrintJob. A null title is equivalent
     *          to "".
     * @param   props a Properties object containing zero or more properties.
     *          Properties are not standardized and are not consistent across
     *          implementations. Because of this, PrintJobs which require job
     *          and page control should use the version of this function which
     *          takes JobAttributes and PageAttributes objects. This object
     *          may be updated to reflect the user's job choices on exit. May
     *          be null.
     * @return  a <code>PrintJob</code> object, or <code>null</code> if the
     *          user cancelled the print job.
     * @throws  NullPointerException if frame is null
     * @throws  SecurityException if this thread is not allowed to initiate a
     *          print job request
     * @see     java.awt.GraphicsEnvironment#isHeadless
     * @see     java.awt.PrintJob
     * @see     java.lang.RuntimePermission
     * @since   JDK1.1
     */
    public abstract PrintJob getPrintJob(Frame frame, String jobtitle,
                                         Properties props);

    /**
     * Gets a <code>PrintJob</code> object which is the result of initiating
     * a print operation on the toolkit's platform.
     * <p>
     * Each actual implementation of this method should first check if there
     * is a security manager installed. If there is, the method should call
     * the security manager's <code>checkPrintJobAccess</code> method to
     * ensure initiation of a print operation is allowed. If the default
     * implementation of <code>checkPrintJobAccess</code> is used (that is,
     * that method is not overriden), then this results in a call to the
     * security manager's <code>checkPermission</code> method with a <code>
     * RuntimePermission("queuePrintJob")</code> permission.
     *
     * <p>
     *  获取一个<code> PrintJob </code>对象,它是在工具包平台上启动打印操作的结果。
     * <p>
     *  此方法的每个实际实现应首先检查是否安装了安全管理器。如果存在,该方法应该调用安全管理器的<code> checkPrintJobAccess </code>方法,以确保允许启动打印操作。
     * 如果使用<code> checkPrintJobAccess </code>的默认实现(即,该方法不被覆盖),则这将导致对安全管理器的<code> checkPermission </code> Run
     * timePermission("queuePrintJob")</code>权限。
     *  此方法的每个实际实现应首先检查是否安装了安全管理器。如果存在,该方法应该调用安全管理器的<code> checkPrintJobAccess </code>方法,以确保允许启动打印操作。
     * 
     * 
     * @param   frame the parent of the print dialog. May not be null.
     * @param   jobtitle the title of the PrintJob. A null title is equivalent
     *          to "".
     * @param   jobAttributes a set of job attributes which will control the
     *          PrintJob. The attributes will be updated to reflect the user's
     *          choices as outlined in the JobAttributes documentation. May be
     *          null.
     * @param   pageAttributes a set of page attributes which will control the
     *          PrintJob. The attributes will be applied to every page in the
     *          job. The attributes will be updated to reflect the user's
     *          choices as outlined in the PageAttributes documentation. May be
     *          null.
     * @return  a <code>PrintJob</code> object, or <code>null</code> if the
     *          user cancelled the print job.
     * @throws  NullPointerException if frame is null
     * @throws  IllegalArgumentException if pageAttributes specifies differing
     *          cross feed and feed resolutions. Also if this thread has
     *          access to the file system and jobAttributes specifies
     *          print to file, and the specified destination file exists but
     *          is a directory rather than a regular file, does not exist but
     *          cannot be created, or cannot be opened for any other reason.
     *          However in the case of print to file, if a dialog is also
     *          requested to be displayed then the user will be given an
     *          opportunity to select a file and proceed with printing.
     *          The dialog will ensure that the selected output file
     *          is valid before returning from this method.
     * @throws  SecurityException if this thread is not allowed to initiate a
     *          print job request, or if jobAttributes specifies print to file,
     *          and this thread is not allowed to access the file system
     * @see     java.awt.PrintJob
     * @see     java.awt.GraphicsEnvironment#isHeadless
     * @see     java.lang.RuntimePermission
     * @see     java.awt.JobAttributes
     * @see     java.awt.PageAttributes
     * @since   1.3
     */
    public PrintJob getPrintJob(Frame frame, String jobtitle,
                                JobAttributes jobAttributes,
                                PageAttributes pageAttributes) {
        // Override to add printing support with new job/page control classes

        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getPrintJob(frame, jobtitle,
                                                           jobAttributes,
                                                           pageAttributes);
        } else {
            return getPrintJob(frame, jobtitle, null);
        }
    }

    /**
     * Emits an audio beep depending on native system settings and hardware
     * capabilities.
     * <p>
     *  根据本机系统设置和硬件功能发出音频提示音。
     * 
     * 
     * @since     JDK1.1
     */
    public abstract void beep();

    /**
     * Gets the singleton instance of the system Clipboard which interfaces
     * with clipboard facilities provided by the native platform. This
     * clipboard enables data transfer between Java programs and native
     * applications which use native clipboard facilities.
     * <p>
     * In addition to any and all formats specified in the flavormap.properties
     * file, or other file specified by the <code>AWT.DnD.flavorMapFileURL
     * </code> Toolkit property, text returned by the system Clipboard's <code>
     * getTransferData()</code> method is available in the following flavors:
     * <ul>
     * <li>DataFlavor.stringFlavor</li>
     * <li>DataFlavor.plainTextFlavor (<b>deprecated</b>)</li>
     * </ul>
     * As with <code>java.awt.datatransfer.StringSelection</code>, if the
     * requested flavor is <code>DataFlavor.plainTextFlavor</code>, or an
     * equivalent flavor, a Reader is returned. <b>Note:</b> The behavior of
     * the system Clipboard's <code>getTransferData()</code> method for <code>
     * DataFlavor.plainTextFlavor</code>, and equivalent DataFlavors, is
     * inconsistent with the definition of <code>DataFlavor.plainTextFlavor
     * </code>. Because of this, support for <code>
     * DataFlavor.plainTextFlavor</code>, and equivalent flavors, is
     * <b>deprecated</b>.
     * <p>
     * Each actual implementation of this method should first check if there
     * is a security manager installed. If there is, the method should call
     * the security manager's {@link SecurityManager#checkPermission
     * checkPermission} method to check {@code AWTPermission("accessClipboard")}.
     *
     * <p>
     * 获取与本机平台提供的剪贴板设施接口的系统剪贴板的单例实例。此剪贴板支持使用本机剪贴板工具的Java程序和本机应用程序之间的数据传输。
     * <p>
     *  除了在flavormap.properties文件或由<code> AWT.DnD.flavorMapFileURL </code> Toolkit属性指定的其他文件中指定的任何和所有格式,系统剪贴板
     * 的<code> getTransferData()</代码>方法可用于以下类型：。
     * <ul>
     *  <li> DataFlavor.stringFlavor </li> <li> DataFlavor.plainTextFlavor(<b>已弃用</b>)</li>
     * </ul>
     *  与<code> java.awt.datatransfer.StringSelection </code>一样,如果请求的flavor是<code> DataFlavor.plainTextFlavo
     * r </code>或等效的风格,则返回一个Reader。
     *  <b>注意</b>：<code> DataFlavor.plainTextFlavor </code>和等效DataFlavors系统剪贴板的<code> getTransferData()</code>
     * 方法的行为与<code> > DataFlavor.plainTextFlavor </code>。
     * 因此,对<code> DataFlavor.plainTextFlavor </code>和等效风格的支持<b>已弃用</b>。
     * <p>
     *  此方法的每个实际实现应首先检查是否安装了安全管理器。
     * 如果存在,该方法应调用安全管理器的{@link SecurityManager#checkPermission checkPermission}方法来检查{@code AWTPermission("accessClipboard")}
     * 。
     *  此方法的每个实际实现应首先检查是否安装了安全管理器。
     * 
     * 
     * @return    the system Clipboard
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.datatransfer.Clipboard
     * @see       java.awt.datatransfer.StringSelection
     * @see       java.awt.datatransfer.DataFlavor#stringFlavor
     * @see       java.awt.datatransfer.DataFlavor#plainTextFlavor
     * @see       java.io.Reader
     * @see       java.awt.AWTPermission
     * @since     JDK1.1
     */
    public abstract Clipboard getSystemClipboard()
        throws HeadlessException;

    /**
     * Gets the singleton instance of the system selection as a
     * <code>Clipboard</code> object. This allows an application to read and
     * modify the current, system-wide selection.
     * <p>
     * An application is responsible for updating the system selection whenever
     * the user selects text, using either the mouse or the keyboard.
     * Typically, this is implemented by installing a
     * <code>FocusListener</code> on all <code>Component</code>s which support
     * text selection, and, between <code>FOCUS_GAINED</code> and
     * <code>FOCUS_LOST</code> events delivered to that <code>Component</code>,
     * updating the system selection <code>Clipboard</code> when the selection
     * changes inside the <code>Component</code>. Properly updating the system
     * selection ensures that a Java application will interact correctly with
     * native applications and other Java applications running simultaneously
     * on the system. Note that <code>java.awt.TextComponent</code> and
     * <code>javax.swing.text.JTextComponent</code> already adhere to this
     * policy. When using these classes, and their subclasses, developers need
     * not write any additional code.
     * <p>
     * Some platforms do not support a system selection <code>Clipboard</code>.
     * On those platforms, this method will return <code>null</code>. In such a
     * case, an application is absolved from its responsibility to update the
     * system selection <code>Clipboard</code> as described above.
     * <p>
     * Each actual implementation of this method should first check if there
     * is a security manager installed. If there is, the method should call
     * the security manager's {@link SecurityManager#checkPermission
     * checkPermission} method to check {@code AWTPermission("accessClipboard")}.
     *
     * <p>
     * 将系统选择的单例实例作为<code> Clipboard </code>对象。这允许应用程序读取和修改当前的系统范围选择。
     * <p>
     *  当用户使用鼠标或键盘选择文本时,应用程序负责更新系统选择。
     * 通常,通过在支持文本选择的所有<code> Component </code>上安装<code> FocusListener </code>,并在<code> FOCUS_GAINED </code>和
     * <code> FOCUS_LOST </code>当<code> Component </code>中的选择更改时,更新系统选择<code> Clipboard </code>正确更新系统选择可确保Ja
     * va应用程序与本机应用程序和系统上同时运行的其他Java应用程序正确交互。
     *  当用户使用鼠标或键盘选择文本时,应用程序负责更新系统选择。
     * 请注意,<code> java.awt.TextComponent </code>和<code> javax.swing.text.JTextComponent </code>已遵守此政策。
     * 当使用这些类及其子类时,开发人员不需要编写任何额外的代码。
     * <p>
     *  一些平台不支持系统选择<code> Clipboard </code>。在这些平台上,此方法将返回<code> null </code>。
     * 在这种情况下,应用被免除其如上所述更新系统选择<code> Clipboard </code>的责任。
     * <p>
     * 此方法的每个实际实现应首先检查是否安装了安全管理器。
     * 如果存在,该方法应调用安全管理器的{@link SecurityManager#checkPermission checkPermission}方法来检查{@code AWTPermission("accessClipboard")}
     * 。
     * 此方法的每个实际实现应首先检查是否安装了安全管理器。
     * 
     * 
     * @return the system selection as a <code>Clipboard</code>, or
     *         <code>null</code> if the native platform does not support a
     *         system selection <code>Clipboard</code>
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true
     *
     * @see java.awt.datatransfer.Clipboard
     * @see java.awt.event.FocusListener
     * @see java.awt.event.FocusEvent#FOCUS_GAINED
     * @see java.awt.event.FocusEvent#FOCUS_LOST
     * @see TextComponent
     * @see javax.swing.text.JTextComponent
     * @see AWTPermission
     * @see GraphicsEnvironment#isHeadless
     * @since 1.4
     */
    public Clipboard getSystemSelection() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getSystemSelection();
        } else {
            GraphicsEnvironment.checkHeadless();
            return null;
        }
    }

    /**
     * Determines which modifier key is the appropriate accelerator
     * key for menu shortcuts.
     * <p>
     * Menu shortcuts, which are embodied in the
     * <code>MenuShortcut</code> class, are handled by the
     * <code>MenuBar</code> class.
     * <p>
     * By default, this method returns <code>Event.CTRL_MASK</code>.
     * Toolkit implementations should override this method if the
     * <b>Control</b> key isn't the correct key for accelerators.
     * <p>
     *  确定哪个修饰键是菜单快捷键的相应加速键。
     * <p>
     *  包含在<code> MenuShortcut </code>类中的菜单快捷方式由<code> MenuBar </code>类处理。
     * <p>
     *  默认情况下,此方法返回<code> Event.CTRL_MASK </code>。如果<b> Control </b>键不是加速器的正确键,Toolkit实现应该覆盖此方法。
     * 
     * 
     * @return    the modifier mask on the <code>Event</code> class
     *                 that is used for menu shortcuts on this toolkit.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @see       java.awt.MenuBar
     * @see       java.awt.MenuShortcut
     * @since     JDK1.1
     */
    public int getMenuShortcutKeyMask() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        return Event.CTRL_MASK;
    }

    /**
     * Returns whether the given locking key on the keyboard is currently in
     * its "on" state.
     * Valid key codes are
     * {@link java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK},
     * {@link java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK},
     * {@link java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK}, and
     * {@link java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK}.
     *
     * <p>
     *  返回键盘上给定的锁定键是否处于其"打开"状态。
     * 有效的键码是{@link java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK},{@link java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK}
     * ,{@link java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK},以及{@link java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK}
     * 。
     *  返回键盘上给定的锁定键是否处于其"打开"状态。
     * 
     * 
     * @exception java.lang.IllegalArgumentException if <code>keyCode</code>
     * is not one of the valid key codes
     * @exception java.lang.UnsupportedOperationException if the host system doesn't
     * allow getting the state of this key programmatically, or if the keyboard
     * doesn't have this key
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since 1.3
     */
    public boolean getLockingKeyState(int keyCode)
        throws UnsupportedOperationException
    {
        GraphicsEnvironment.checkHeadless();

        if (! (keyCode == KeyEvent.VK_CAPS_LOCK || keyCode == KeyEvent.VK_NUM_LOCK ||
               keyCode == KeyEvent.VK_SCROLL_LOCK || keyCode == KeyEvent.VK_KANA_LOCK)) {
            throw new IllegalArgumentException("invalid key for Toolkit.getLockingKeyState");
        }
        throw new UnsupportedOperationException("Toolkit.getLockingKeyState");
    }

    /**
     * Sets the state of the given locking key on the keyboard.
     * Valid key codes are
     * {@link java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK},
     * {@link java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK},
     * {@link java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK}, and
     * {@link java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK}.
     * <p>
     * Depending on the platform, setting the state of a locking key may
     * involve event processing and therefore may not be immediately
     * observable through getLockingKeyState.
     *
     * <p>
     *  设置键盘上给定锁定键的状态。
     * 有效的键码是{@link java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK},{@link java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK}
     * ,{@link java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK},以及{@link java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK}
     * 。
     *  设置键盘上给定锁定键的状态。
     * <p>
     * 根据平台,设置锁定键的状态可能涉及事件处理,因此可能无法通过getLockingKeyState立即查看。
     * 
     * 
     * @exception java.lang.IllegalArgumentException if <code>keyCode</code>
     * is not one of the valid key codes
     * @exception java.lang.UnsupportedOperationException if the host system doesn't
     * allow setting the state of this key programmatically, or if the keyboard
     * doesn't have this key
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since 1.3
     */
    public void setLockingKeyState(int keyCode, boolean on)
        throws UnsupportedOperationException
    {
        GraphicsEnvironment.checkHeadless();

        if (! (keyCode == KeyEvent.VK_CAPS_LOCK || keyCode == KeyEvent.VK_NUM_LOCK ||
               keyCode == KeyEvent.VK_SCROLL_LOCK || keyCode == KeyEvent.VK_KANA_LOCK)) {
            throw new IllegalArgumentException("invalid key for Toolkit.setLockingKeyState");
        }
        throw new UnsupportedOperationException("Toolkit.setLockingKeyState");
    }

    /**
     * Give native peers the ability to query the native container
     * given a native component (eg the direct parent may be lightweight).
     * <p>
     *  给予本地同伴在给定本地组件(例如,直接父组可以是轻量级的)的情况下查询本地容器的能力。
     * 
     */
    protected static Container getNativeContainer(Component c) {
        return c.getNativeContainer();
    }

    /**
     * Creates a new custom cursor object.
     * If the image to display is invalid, the cursor will be hidden (made
     * completely transparent), and the hotspot will be set to (0, 0).
     *
     * <p>Note that multi-frame images are invalid and may cause this
     * method to hang.
     *
     * <p>
     *  创建新的自定义光标对象。如果要显示的图像无效,则光标将被隐藏(完全透明),热点将设置为(0,0)。
     * 
     *  <p>请注意,多帧图片无效,可能会导致此方法挂起。
     * 
     * 
     * @param cursor the image to display when the cursor is activated
     * @param hotSpot the X and Y of the large cursor's hot spot; the
     *   hotSpot values must be less than the Dimension returned by
     *   <code>getBestCursorSize</code>
     * @param     name a localized description of the cursor, for Java Accessibility use
     * @exception IndexOutOfBoundsException if the hotSpot values are outside
     *   the bounds of the cursor
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.2
     */
    public Cursor createCustomCursor(Image cursor, Point hotSpot, String name)
        throws IndexOutOfBoundsException, HeadlessException
    {
        // Override to implement custom cursor support.
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().
                createCustomCursor(cursor, hotSpot, name);
        } else {
            return new Cursor(Cursor.DEFAULT_CURSOR);
        }
    }

    /**
     * Returns the supported cursor dimension which is closest to the desired
     * sizes.  Systems which only support a single cursor size will return that
     * size regardless of the desired sizes.  Systems which don't support custom
     * cursors will return a dimension of 0, 0. <p>
     * Note:  if an image is used whose dimensions don't match a supported size
     * (as returned by this method), the Toolkit implementation will attempt to
     * resize the image to a supported size.
     * Since converting low-resolution images is difficult,
     * no guarantees are made as to the quality of a cursor image which isn't a
     * supported size.  It is therefore recommended that this method
     * be called and an appropriate image used so no image conversion is made.
     *
     * <p>
     *  返回最接近所需尺寸的支持的光标尺寸。只支持单个游标大小的系统将返回该大小,而不考虑所需的大小。不支持自定义游标的系统将返回0,0的维度。
     * <p>注意：如果使用的维度与支持的大小(由此方法返回的)不匹配的图像,Toolkit实现将尝试将图像大小调整为支持的大小。由于难以转换低分辨率图像,因此不能保证不是所支持的尺寸的光标图像的质量。
     * 因此,建议调用此方法,并使用合适的图像,以便不进行图像转换。
     * 
     * 
     * @param     preferredWidth the preferred cursor width the component would like
     * to use.
     * @param     preferredHeight the preferred cursor height the component would like
     * to use.
     * @return    the closest matching supported cursor size, or a dimension of 0,0 if
     * the Toolkit implementation doesn't support custom cursors.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.2
     */
    public Dimension getBestCursorSize(int preferredWidth,
        int preferredHeight) throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        // Override to implement custom cursor support.
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().
                getBestCursorSize(preferredWidth, preferredHeight);
        } else {
            return new Dimension(0, 0);
        }
    }

    /**
     * Returns the maximum number of colors the Toolkit supports in a custom cursor
     * palette.<p>
     * Note: if an image is used which has more colors in its palette than
     * the supported maximum, the Toolkit implementation will attempt to flatten the
     * palette to the maximum.  Since converting low-resolution images is difficult,
     * no guarantees are made as to the quality of a cursor image which has more
     * colors than the system supports.  It is therefore recommended that this method
     * be called and an appropriate image used so no image conversion is made.
     *
     * <p>
     * 返回Toolkit在自定义游标调色板中支持的最大颜色数。<p>注意：如果使用的图片在其调色板中具有比支持的最大值更多的颜色,则Toolkit实现将尝试将调色板平整到最大值。
     * 由于转换低分辨率图像是困难的,因此不能保证具有比系统支持的颜色更多的光标图像的质量。因此,建议调用此方法,并使用合适的图像,以便不进行图像转换。
     * 
     * 
     * @return    the maximum number of colors, or zero if custom cursors are not
     * supported by this Toolkit implementation.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since     1.2
     */
    public int getMaximumCursorColors() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        // Override to implement custom cursor support.
        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().getMaximumCursorColors();
        } else {
            return 0;
        }
    }

    /**
     * Returns whether Toolkit supports this state for
     * <code>Frame</code>s.  This method tells whether the <em>UI
     * concept</em> of, say, maximization or iconification is
     * supported.  It will always return false for "compound" states
     * like <code>Frame.ICONIFIED|Frame.MAXIMIZED_VERT</code>.
     * In other words, the rule of thumb is that only queries with a
     * single frame state constant as an argument are meaningful.
     * <p>Note that supporting a given concept is a platform-
     * dependent feature. Due to native limitations the Toolkit
     * object may report a particular state as supported, however at
     * the same time the Toolkit object will be unable to apply the
     * state to a given frame.  This circumstance has two following
     * consequences:
     * <ul>
     * <li>Only the return value of {@code false} for the present
     * method actually indicates that the given state is not
     * supported. If the method returns {@code true} the given state
     * may still be unsupported and/or unavailable for a particular
     * frame.
     * <li>The developer should consider examining the value of the
     * {@link java.awt.event.WindowEvent#getNewState} method of the
     * {@code WindowEvent} received through the {@link
     * java.awt.event.WindowStateListener}, rather than assuming
     * that the state given to the {@code setExtendedState()} method
     * will be definitely applied. For more information see the
     * documentation for the {@link Frame#setExtendedState} method.
     * </ul>
     *
     * <p>
     *  返回Toolkit是否支持<code> Frame </code>的此状态。此方法会告诉您是否支持最大化或图标化的<em> UI概念</em>。
     * 对于"复合"状态,如<code> Frame.ICONIFIED | Frame.MAXIMIZED_VERT </code>,它总是返回false。
     * 换句话说,经验法则是只有具有单个帧状态常量作为参数的查询是有意义的。 <p>请注意,支持给定的概念是一个平台相关的功能。
     * 由于本地限制,Toolkit对象可以报告支持的特定状态,但是同时Toolkit对象将无法将状态应用于给定的帧。这种情况有两个后果：。
     * <ul>
     * <li>对于本方法,只有{@code false}的返回值实际上表示不支持给定状态。如果该方法返回{@code true},给定状态对于特定帧可能仍然不受支持和/或不可用。
     *  <li>开发人员应考虑检查通过{@link java.awt.event.WindowStateListener}接收的{@code WindowEvent}的{@link java.awt.event.WindowEvent#getNewState}
     * 方法的值,而不是假设给定到{@code setExtendedState()}方法的状态将被明确应用。
     * <li>对于本方法,只有{@code false}的返回值实际上表示不支持给定状态。如果该方法返回{@code true},给定状态对于特定帧可能仍然不受支持和/或不可用。
     * 有关详细信息,请参阅{@link Frame#setExtendedState}方法的文档。
     * </ul>
     * 
     * 
     * @param state one of named frame state constants.
     * @return <code>true</code> is this frame state is supported by
     *     this Toolkit implementation, <code>false</code> otherwise.
     * @exception HeadlessException
     *     if <code>GraphicsEnvironment.isHeadless()</code>
     *     returns <code>true</code>.
     * @see java.awt.Window#addWindowStateListener
     * @since   1.4
     */
    public boolean isFrameStateSupported(int state)
        throws HeadlessException
    {
        GraphicsEnvironment.checkHeadless();

        if (this != Toolkit.getDefaultToolkit()) {
            return Toolkit.getDefaultToolkit().
                isFrameStateSupported(state);
        } else {
            return (state == Frame.NORMAL); // others are not guaranteed
        }
    }

    /**
     * Support for I18N: any visible strings should be stored in
     * sun.awt.resources.awt.properties.  The ResourceBundle is stored
     * here, so that only one copy is maintained.
     * <p>
     *  支持I18N：任何可见的字符串都应该存储在sun.awt.resources.awt.properties中。 ResourceBundle存储在这里,因此只保留一个副本。
     * 
     */
    private static ResourceBundle resources;
    private static ResourceBundle platformResources;

    // called by platform toolkit
    private static void setPlatformResources(ResourceBundle bundle) {
        platformResources = bundle;
    }

    /**
     * Initialize JNI field and method ids
     * <p>
     *  初始化JNI字段和方法标识
     * 
     */
    private static native void initIDs();

    /**
     * WARNING: This is a temporary workaround for a problem in the
     * way the AWT loads native libraries. A number of classes in the
     * AWT package have a native method, initIDs(), which initializes
     * the JNI field and method ids used in the native portion of
     * their implementation.
     *
     * Since the use and storage of these ids is done by the
     * implementation libraries, the implementation of these method is
     * provided by the particular AWT implementations (for example,
     * "Toolkit"s/Peer), such as Motif, Microsoft Windows, or Tiny. The
     * problem is that this means that the native libraries must be
     * loaded by the java.* classes, which do not necessarily know the
     * names of the libraries to load. A better way of doing this
     * would be to provide a separate library which defines java.awt.*
     * initIDs, and exports the relevant symbols out to the
     * implementation libraries.
     *
     * For now, we know it's done by the implementation, and we assume
     * that the name of the library is "awt".  -br.
     *
     * If you change loadLibraries(), please add the change to
     * java.awt.image.ColorModel.loadLibraries(). Unfortunately,
     * classes can be loaded in java.awt.image that depend on
     * libawt and there is no way to call Toolkit.loadLibraries()
     * directly.  -hung
     * <p>
     *  警告：这是AWT加载本机库的问题的临时解决方法。 AWT包中的多个类具有本地方法initIDs(),它初始化JNI字段和在其实现的本地部分中使用的方法ID。
     * 
     * 由于这些id的使用和存储由实现库完成,所以这些方法的实现由特定的AWT实现(例如,"Toolkit / s / Peer")提供,例如Motif,Microsoft Windows或Tiny。
     * 问题是这意味着本地库必须由java。*类加载,这不一定知道要加载的库的名称。一个更好的方法是提供一个独立的库,它定义了java.awt。* initIDs,并将相关符号导出到实现库中。
     * 
     *  现在,我们知道它是由实现完成的,我们假设库的名称是"awt"。 -br。
     * 
     *  如果更改loadLibraries(),请将更改添加到java.awt.image.ColorModel.loadLibraries()。
     * 不幸的是,类可以在依赖libawt的java.awt.image中加载,没有办法直接调用Toolkit.loadLibraries()。 -hung。
     * 
     */
    private static boolean loaded = false;
    static void loadLibraries() {
        if (!loaded) {
            java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction<Void>() {
                    public Void run() {
                        System.loadLibrary("awt");
                        return null;
                    }
                });
            loaded = true;
        }
    }

    static {
        AWTAccessor.setToolkitAccessor(
                new AWTAccessor.ToolkitAccessor() {
                    @Override
                    public void setPlatformResources(ResourceBundle bundle) {
                        Toolkit.setPlatformResources(bundle);
                    }
                });

        java.security.AccessController.doPrivileged(
                                 new java.security.PrivilegedAction<Void>() {
            public Void run() {
                try {
                    resources =
                        ResourceBundle.getBundle("sun.awt.resources.awt",
                                                 CoreResourceBundleControl.getRBControlInstance());
                } catch (MissingResourceException e) {
                    // No resource file; defaults will be used.
                }
                return null;
            }
        });

        // ensure that the proper libraries are loaded
        loadLibraries();
        initAssistiveTechnologies();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Gets a property with the specified key and default.
     * This method returns defaultValue if the property is not found.
     * <p>
     *  获取具有指定键和默认值的属性。如果未找到属性,此方法返回defaultValue。
     * 
     */
    public static String getProperty(String key, String defaultValue) {
        // first try platform specific bundle
        if (platformResources != null) {
            try {
                return platformResources.getString(key);
            }
            catch (MissingResourceException e) {}
        }

        // then shared one
        if (resources != null) {
            try {
                return resources.getString(key);
            }
            catch (MissingResourceException e) {}
        }

        return defaultValue;
    }

    /**
     * Get the application's or applet's EventQueue instance.
     * Depending on the Toolkit implementation, different EventQueues
     * may be returned for different applets.  Applets should
     * therefore not assume that the EventQueue instance returned
     * by this method will be shared by other applets or the system.
     *
     * <p> If there is a security manager then its
     * {@link SecurityManager#checkPermission checkPermission} method
     * is called to check {@code AWTPermission("accessEventQueue")}.
     *
     * <p>
     *  获取应用程序或applet的EventQueue实例。根据Toolkit实现,可以为不同的applet返回不同的EventQueue。
     * 因此,Applet不应假定由此方法返回的EventQueue实例将被其他applet或系统共享。
     * 
     * <p>如果有安全管理员,则会调用其{@link SecurityManager#checkPermission checkPermission}方法来检查{@code AWTPermission("accessEventQueue")}
     * 。
     * 
     * 
     * @return    the <code>EventQueue</code> object
     * @throws  SecurityException
     *          if a security manager is set and it denies access to
     *          the {@code EventQueue}
     * @see     java.awt.AWTPermission
    */
    public final EventQueue getSystemEventQueue() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION);
        }
        return getSystemEventQueueImpl();
    }

    /**
     * Gets the application's or applet's <code>EventQueue</code>
     * instance, without checking access.  For security reasons,
     * this can only be called from a <code>Toolkit</code> subclass.
     * <p>
     *  获取应用程序或小程序的<code> EventQueue </code>实例,而不检查访问权限。出于安全原因,这只能从<code> Toolkit </code>子类中调用。
     * 
     * 
     * @return the <code>EventQueue</code> object
     */
    protected abstract EventQueue getSystemEventQueueImpl();

    /* Accessor method for use by AWT package routines. */
    static EventQueue getEventQueue() {
        return getDefaultToolkit().getSystemEventQueueImpl();
    }

    /**
     * Creates the peer for a DragSourceContext.
     * Always throws InvalidDndOperationException if
     * GraphicsEnvironment.isHeadless() returns true.
     * <p>
     *  为DragSourceContext创建对等体。如果GraphicsEnvironment.isHeadless()返回true,则始终抛出InvalidDndOperationException。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent dge) throws InvalidDnDOperationException;

    /**
     * Creates a concrete, platform dependent, subclass of the abstract
     * DragGestureRecognizer class requested, and associates it with the
     * DragSource, Component and DragGestureListener specified.
     *
     * subclasses should override this to provide their own implementation
     *
     * <p>
     *  创建一个具体的,平台相关的,请求的抽象DragGestureRecognizer类的子类,并将其与DragSource,Component和DragGestureListener指定的关联。
     * 
     *  子类应该重写这个来提供自己的实现
     * 
     * 
     * @param abstractRecognizerClass The abstract class of the required recognizer
     * @param ds                      The DragSource
     * @param c                       The Component target for the DragGestureRecognizer
     * @param srcActions              The actions permitted for the gesture
     * @param dgl                     The DragGestureListener
     *
     * @return the new object or null.  Always returns null if
     * GraphicsEnvironment.isHeadless() returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public <T extends DragGestureRecognizer> T
        createDragGestureRecognizer(Class<T> abstractRecognizerClass,
                                    DragSource ds, Component c, int srcActions,
                                    DragGestureListener dgl)
    {
        return null;
    }

    /**
     * Obtains a value for the specified desktop property.
     *
     * A desktop property is a uniquely named value for a resource that
     * is Toolkit global in nature. Usually it also is an abstract
     * representation for an underlying platform dependent desktop setting.
     * For more information on desktop properties supported by the AWT see
     * <a href="doc-files/DesktopProperties.html">AWT Desktop Properties</a>.
     * <p>
     *  获取指定的桌面属性的值。
     * 
     *  桌面属性是Toolkit全局性质的资源的唯一命名值。通常,它也是底层平台相关桌面设置的抽象表示。
     * 有关AWT支持的桌面属性的详细信息,请参阅<a href="doc-files/DesktopProperties.html"> AWT桌面属性</a>。
     * 
     */
    public final synchronized Object getDesktopProperty(String propertyName) {
        // This is a workaround for headless toolkits.  It would be
        // better to override this method but it is declared final.
        // "this instanceof" syntax defeats polymorphism.
        // --mm, 03/03/00
        if (this instanceof HeadlessToolkit) {
            return ((HeadlessToolkit)this).getUnderlyingToolkit()
                .getDesktopProperty(propertyName);
        }

        if (desktopProperties.isEmpty()) {
            initializeDesktopProperties();
        }

        Object value;

        // This property should never be cached
        if (propertyName.equals("awt.dynamicLayoutSupported")) {
            return getDefaultToolkit().lazilyLoadDesktopProperty(propertyName);
        }

        value = desktopProperties.get(propertyName);

        if (value == null) {
            value = lazilyLoadDesktopProperty(propertyName);

            if (value != null) {
                setDesktopProperty(propertyName, value);
            }
        }

        /* for property "awt.font.desktophints" */
        if (value instanceof RenderingHints) {
            value = ((RenderingHints)value).clone();
        }

        return value;
    }

    /**
     * Sets the named desktop property to the specified value and fires a
     * property change event to notify any listeners that the value has changed.
     * <p>
     *  将命名的桌面属性设置为指定的值,并触发属性更改事件以通知任何侦听器值已更改。
     * 
     */
    protected final void setDesktopProperty(String name, Object newValue) {
        // This is a workaround for headless toolkits.  It would be
        // better to override this method but it is declared final.
        // "this instanceof" syntax defeats polymorphism.
        // --mm, 03/03/00
        if (this instanceof HeadlessToolkit) {
            ((HeadlessToolkit)this).getUnderlyingToolkit()
                .setDesktopProperty(name, newValue);
            return;
        }
        Object oldValue;

        synchronized (this) {
            oldValue = desktopProperties.get(name);
            desktopProperties.put(name, newValue);
        }

        // Don't fire change event if old and new values are null.
        // It helps to avoid recursive resending of WM_THEMECHANGED
        if (oldValue != null || newValue != null) {
            desktopPropsSupport.firePropertyChange(name, oldValue, newValue);
        }
    }

    /**
     * an opportunity to lazily evaluate desktop property values.
     * <p>
     *  懒散地评估桌面属性值的机会。
     * 
     */
    protected Object lazilyLoadDesktopProperty(String name) {
        return null;
    }

    /**
     * initializeDesktopProperties
     * <p>
     *  initializeDesktopProperties
     * 
     */
    protected void initializeDesktopProperties() {
    }

    /**
     * Adds the specified property change listener for the named desktop
     * property. When a {@link java.beans.PropertyChangeListenerProxy} object is added,
     * its property name is ignored, and the wrapped listener is added.
     * If {@code name} is {@code null} or {@code pcl} is {@code null},
     * no exception is thrown and no action is performed.
     *
     * <p>
     * 为指定的桌面属性添加指定的属性更改侦听器。添加{@link java.beans.PropertyChangeListenerProxy}对象时,将忽略其属性名称,并添加包装的侦听器。
     * 如果{@code name}为{@code null}或{@code pcl}为{@code null},则不会抛出任何异常,也不会执行任何操作。
     * 
     * 
     * @param   name The name of the property to listen for
     * @param   pcl The property change listener
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
                PropertyChangeListener)
     * @since   1.2
     */
    public void addPropertyChangeListener(String name, PropertyChangeListener pcl) {
        desktopPropsSupport.addPropertyChangeListener(name, pcl);
    }

    /**
     * Removes the specified property change listener for the named
     * desktop property. When a {@link java.beans.PropertyChangeListenerProxy} object
     * is removed, its property name is ignored, and
     * the wrapped listener is removed.
     * If {@code name} is {@code null} or {@code pcl} is {@code null},
     * no exception is thrown and no action is performed.
     *
     * <p>
     *  删除指定的桌面属性的指定的属性更改侦听器。当删除{@link java.beans.PropertyChangeListenerProxy}对象时,将忽略其属性名称,并删除包装的侦听器。
     * 如果{@code name}为{@code null}或{@code pcl}为{@code null},则不会抛出任何异常,也不会执行任何操作。
     * 
     * 
     * @param   name The name of the property to remove
     * @param   pcl The property change listener
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
                PropertyChangeListener)
     * @since   1.2
     */
    public void removePropertyChangeListener(String name, PropertyChangeListener pcl) {
        desktopPropsSupport.removePropertyChangeListener(name, pcl);
    }

    /**
     * Returns an array of all the property change listeners
     * registered on this toolkit. The returned array
     * contains {@link java.beans.PropertyChangeListenerProxy} objects
     * that associate listeners with the names of desktop properties.
     *
     * <p>
     *  返回在此工具包上注册的所有属性更改侦听器的数组。
     * 返回的数组包含{@link java.beans.PropertyChangeListenerProxy}对象,这些对象将侦听器与桌面属性的名称相关联。
     * 
     * 
     * @return all of this toolkit's {@link PropertyChangeListener}
     *         objects wrapped in {@code java.beans.PropertyChangeListenerProxy} objects
     *         or an empty array  if no listeners are added
     *
     * @see PropertyChangeSupport#getPropertyChangeListeners()
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return desktopPropsSupport.getPropertyChangeListeners();
    }

    /**
     * Returns an array of all property change listeners
     * associated with the specified name of a desktop property.
     *
     * <p>
     *  返回与桌面属性的指定名称相关联的所有属性更改侦听器的数组。
     * 
     * 
     * @param  propertyName the named property
     * @return all of the {@code PropertyChangeListener} objects
     *         associated with the specified name of a desktop property
     *         or an empty array if no such listeners are added
     *
     * @see PropertyChangeSupport#getPropertyChangeListeners(String)
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return desktopPropsSupport.getPropertyChangeListeners(propertyName);
    }

    protected final Map<String,Object> desktopProperties =
            new HashMap<String,Object>();
    protected final PropertyChangeSupport desktopPropsSupport =
            Toolkit.createPropertyChangeSupport(this);

    /**
     * Returns whether the always-on-top mode is supported by this toolkit.
     * To detect whether the always-on-top mode is supported for a
     * particular Window, use {@link Window#isAlwaysOnTopSupported}.
     * <p>
     *  返回此工具包是否支持始终在顶模式。要检测特定窗口是否支持始终在顶模式,请使用{@link Window#isAlwaysOnTopSupported}。
     * 
     * 
     * @return <code>true</code>, if current toolkit supports the always-on-top mode,
     *     otherwise returns <code>false</code>
     * @see Window#isAlwaysOnTopSupported
     * @see Window#setAlwaysOnTop(boolean)
     * @since 1.6
     */
    public boolean isAlwaysOnTopSupported() {
        return true;
    }

    /**
     * Returns whether the given modality type is supported by this toolkit. If
     * a dialog with unsupported modality type is created, then
     * <code>Dialog.ModalityType.MODELESS</code> is used instead.
     *
     * <p>
     *  返回此工具包是否支持给定的模态类型。如果创建了具有不支持的模态类型的对话框,则使用<code> Dialog.ModalityType.MODELESS </code>。
     * 
     * 
     * @param modalityType modality type to be checked for support by this toolkit
     *
     * @return <code>true</code>, if current toolkit supports given modality
     *     type, <code>false</code> otherwise
     *
     * @see java.awt.Dialog.ModalityType
     * @see java.awt.Dialog#getModalityType
     * @see java.awt.Dialog#setModalityType
     *
     * @since 1.6
     */
    public abstract boolean isModalityTypeSupported(Dialog.ModalityType modalityType);

    /**
     * Returns whether the given modal exclusion type is supported by this
     * toolkit. If an unsupported modal exclusion type property is set on a window,
     * then <code>Dialog.ModalExclusionType.NO_EXCLUDE</code> is used instead.
     *
     * <p>
     * 返回此工具包是否支持给定的模态排除类型。如果在窗口上设置了不受支持的模式排除类型属性,则使用<code> Dialog.ModalExclusionType.NO_EXCLUDE </code>。
     * 
     * 
     * @param modalExclusionType modal exclusion type to be checked for support by this toolkit
     *
     * @return <code>true</code>, if current toolkit supports given modal exclusion
     *     type, <code>false</code> otherwise
     *
     * @see java.awt.Dialog.ModalExclusionType
     * @see java.awt.Window#getModalExclusionType
     * @see java.awt.Window#setModalExclusionType
     *
     * @since 1.6
     */
    public abstract boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType modalExclusionType);

    // 8014718: logging has been removed from SunToolkit

    private static final int LONG_BITS = 64;
    private int[] calls = new int[LONG_BITS];
    private static volatile long enabledOnToolkitMask;
    private AWTEventListener eventListener = null;
    private WeakHashMap<AWTEventListener, SelectiveAWTEventListener> listener2SelectiveListener = new WeakHashMap<>();

    /*
     * Extracts a "pure" AWTEventListener from a AWTEventListenerProxy,
     * if the listener is proxied.
     * <p>
     *  如果侦听器被代理,则从AWTEventListenerProxy提取"纯"AWTEventListener。
     * 
     */
    static private AWTEventListener deProxyAWTEventListener(AWTEventListener l)
    {
        AWTEventListener localL = l;

        if (localL == null) {
            return null;
        }
        // if user passed in a AWTEventListenerProxy object, extract
        // the listener
        if (l instanceof AWTEventListenerProxy) {
            localL = ((AWTEventListenerProxy)l).getListener();
        }
        return localL;
    }

    /**
     * Adds an AWTEventListener to receive all AWTEvents dispatched
     * system-wide that conform to the given <code>eventMask</code>.
     * <p>
     * First, if there is a security manager, its <code>checkPermission</code>
     * method is called with an
     * <code>AWTPermission("listenToAllAWTEvents")</code> permission.
     * This may result in a SecurityException.
     * <p>
     * <code>eventMask</code> is a bitmask of event types to receive.
     * It is constructed by bitwise OR-ing together the event masks
     * defined in <code>AWTEvent</code>.
     * <p>
     * Note:  event listener use is not recommended for normal
     * application use, but are intended solely to support special
     * purpose facilities including support for accessibility,
     * event record/playback, and diagnostic tracing.
     *
     * If listener is null, no exception is thrown and no action is performed.
     *
     * <p>
     *  添加AWTEventListener以接收符合给定<code> eventMask </code>的系统范围内分发的所有AWTEvent。
     * <p>
     *  首先,如果有安全管理器,则使用<code> AWTPermission("listenToAllAWTEvents")</code>权限调用其<code> checkPermission </code>
     * 方法。
     * 这可能导致SecurityException。
     * <p>
     *  <code> eventMask </code>是要接收的事件类型的位掩码。它是通过将<code> AWTEvent </code>中定义的事件掩码进行位或运算来构造的。
     * <p>
     *  注意：事件监听器的使用不推荐用于正常的应用程序使用,但仅用于支持特殊用途的工具,包括对辅助功能,事件记录/播放和诊断跟踪的支持。
     * 
     *  如果侦听器为null,则不抛出异常,并且不执行任何操作。
     * 
     * 
     * @param    listener   the event listener.
     * @param    eventMask  the bitmask of event types to receive
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow the operation.
     * @see      #removeAWTEventListener
     * @see      #getAWTEventListeners
     * @see      SecurityManager#checkPermission
     * @see      java.awt.AWTEvent
     * @see      java.awt.AWTPermission
     * @see      java.awt.event.AWTEventListener
     * @see      java.awt.event.AWTEventListenerProxy
     * @since    1.2
     */
    public void addAWTEventListener(AWTEventListener listener, long eventMask) {
        AWTEventListener localL = deProxyAWTEventListener(listener);

        if (localL == null) {
            return;
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
          security.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
        }
        synchronized (this) {
            SelectiveAWTEventListener selectiveListener =
                listener2SelectiveListener.get(localL);

            if (selectiveListener == null) {
                // Create a new selectiveListener.
                selectiveListener = new SelectiveAWTEventListener(localL,
                                                                 eventMask);
                listener2SelectiveListener.put(localL, selectiveListener);
                eventListener = ToolkitEventMulticaster.add(eventListener,
                                                            selectiveListener);
            }
            // OR the eventMask into the selectiveListener's event mask.
            selectiveListener.orEventMasks(eventMask);

            enabledOnToolkitMask |= eventMask;

            long mask = eventMask;
            for (int i=0; i<LONG_BITS; i++) {
                // If no bits are set, break out of loop.
                if (mask == 0) {
                    break;
                }
                if ((mask & 1L) != 0) {  // Always test bit 0.
                    calls[i]++;
                }
                mask >>>= 1;  // Right shift, fill with zeros on left.
            }
        }
    }

    /**
     * Removes an AWTEventListener from receiving dispatched AWTEvents.
     * <p>
     * First, if there is a security manager, its <code>checkPermission</code>
     * method is called with an
     * <code>AWTPermission("listenToAllAWTEvents")</code> permission.
     * This may result in a SecurityException.
     * <p>
     * Note:  event listener use is not recommended for normal
     * application use, but are intended solely to support special
     * purpose facilities including support for accessibility,
     * event record/playback, and diagnostic tracing.
     *
     * If listener is null, no exception is thrown and no action is performed.
     *
     * <p>
     *  从接收调度的AWTEvent中移除AWTEventListener。
     * <p>
     *  首先,如果有安全管理器,则使用<code> AWTPermission("listenToAllAWTEvents")</code>权限调用其<code> checkPermission </code>
     * 方法。
     * 这可能导致SecurityException。
     * <p>
     * 注意：事件监听器的使用不推荐用于正常的应用程序使用,但仅用于支持特殊用途的工具,包括对辅助功能,事件记录/播放和诊断跟踪的支持。
     * 
     *  如果侦听器为null,则不抛出异常,并且不执行任何操作。
     * 
     * 
     * @param    listener   the event listener.
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow the operation.
     * @see      #addAWTEventListener
     * @see      #getAWTEventListeners
     * @see      SecurityManager#checkPermission
     * @see      java.awt.AWTEvent
     * @see      java.awt.AWTPermission
     * @see      java.awt.event.AWTEventListener
     * @see      java.awt.event.AWTEventListenerProxy
     * @since    1.2
     */
    public void removeAWTEventListener(AWTEventListener listener) {
        AWTEventListener localL = deProxyAWTEventListener(listener);

        if (listener == null) {
            return;
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
        }

        synchronized (this) {
            SelectiveAWTEventListener selectiveListener =
                listener2SelectiveListener.get(localL);

            if (selectiveListener != null) {
                listener2SelectiveListener.remove(localL);
                int[] listenerCalls = selectiveListener.getCalls();
                for (int i=0; i<LONG_BITS; i++) {
                    calls[i] -= listenerCalls[i];
                    assert calls[i] >= 0: "Negative Listeners count";

                    if (calls[i] == 0) {
                        enabledOnToolkitMask &= ~(1L<<i);
                    }
                }
            }
            eventListener = ToolkitEventMulticaster.remove(eventListener,
            (selectiveListener == null) ? localL : selectiveListener);
        }
    }

    static boolean enabledOnToolkit(long eventMask) {
        return (enabledOnToolkitMask & eventMask) != 0;
        }

    synchronized int countAWTEventListeners(long eventMask) {
        int ci = 0;
        for (; eventMask != 0; eventMask >>>= 1, ci++) {
        }
        ci--;
        return calls[ci];
    }
    /**
     * Returns an array of all the <code>AWTEventListener</code>s
     * registered on this toolkit.
     * If there is a security manager, its {@code checkPermission}
     * method is called with an
     * {@code AWTPermission("listenToAllAWTEvents")} permission.
     * This may result in a SecurityException.
     * Listeners can be returned
     * within <code>AWTEventListenerProxy</code> objects, which also contain
     * the event mask for the given listener.
     * Note that listener objects
     * added multiple times appear only once in the returned array.
     *
     * <p>
     *  返回此工具包上注册的所有<code> AWTEventListener </code>的数组。
     * 如果有安全管理员,则会使用{@code AWTPermission("listenToAllAWTEvents")}权限调用其{@code checkPermission}方法。
     * 这可能导致SecurityException。侦听器可以在<code> AWTEventListenerProxy </code>对象中返回,这些对象还包含给定侦听器的事件掩码。
     * 注意,多次添加的侦听器对象在返回的数组中只出现一次。
     * 
     * 
     * @return all of the <code>AWTEventListener</code>s or an empty
     *         array if no listeners are currently registered
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow the operation.
     * @see      #addAWTEventListener
     * @see      #removeAWTEventListener
     * @see      SecurityManager#checkPermission
     * @see      java.awt.AWTEvent
     * @see      java.awt.AWTPermission
     * @see      java.awt.event.AWTEventListener
     * @see      java.awt.event.AWTEventListenerProxy
     * @since 1.4
     */
    public AWTEventListener[] getAWTEventListeners() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
        }
        synchronized (this) {
            EventListener[] la = ToolkitEventMulticaster.getListeners(eventListener,AWTEventListener.class);

            AWTEventListener[] ret = new AWTEventListener[la.length];
            for (int i = 0; i < la.length; i++) {
                SelectiveAWTEventListener sael = (SelectiveAWTEventListener)la[i];
                AWTEventListener tempL = sael.getListener();
                //assert tempL is not an AWTEventListenerProxy - we should
                // have weeded them all out
                // don't want to wrap a proxy inside a proxy
                ret[i] = new AWTEventListenerProxy(sael.getEventMask(), tempL);
            }
            return ret;
        }
    }

    /**
     * Returns an array of all the <code>AWTEventListener</code>s
     * registered on this toolkit which listen to all of the event
     * types specified in the {@code eventMask} argument.
     * If there is a security manager, its {@code checkPermission}
     * method is called with an
     * {@code AWTPermission("listenToAllAWTEvents")} permission.
     * This may result in a SecurityException.
     * Listeners can be returned
     * within <code>AWTEventListenerProxy</code> objects, which also contain
     * the event mask for the given listener.
     * Note that listener objects
     * added multiple times appear only once in the returned array.
     *
     * <p>
     *  返回此工具包上注册的所有<code> AWTEventListener </code>的数组,该数组侦听{@code eventMask}参数中指定的所有事件类型。
     * 如果有安全管理员,则会使用{@code AWTPermission("listenToAllAWTEvents")}权限调用其{@code checkPermission}方法。
     * 这可能导致SecurityException。侦听器可以在<code> AWTEventListenerProxy </code>对象中返回,这些对象还包含给定侦听器的事件掩码。
     * 注意,多次添加的侦听器对象在返回的数组中只出现一次。
     * 
     * 
     * @param  eventMask the bitmask of event types to listen for
     * @return all of the <code>AWTEventListener</code>s registered
     *         on this toolkit for the specified
     *         event types, or an empty array if no such listeners
     *         are currently registered
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow the operation.
     * @see      #addAWTEventListener
     * @see      #removeAWTEventListener
     * @see      SecurityManager#checkPermission
     * @see      java.awt.AWTEvent
     * @see      java.awt.AWTPermission
     * @see      java.awt.event.AWTEventListener
     * @see      java.awt.event.AWTEventListenerProxy
     * @since 1.4
     */
    public AWTEventListener[] getAWTEventListeners(long eventMask) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
        }
        synchronized (this) {
            EventListener[] la = ToolkitEventMulticaster.getListeners(eventListener,AWTEventListener.class);

            java.util.List<AWTEventListenerProxy> list = new ArrayList<>(la.length);

            for (int i = 0; i < la.length; i++) {
                SelectiveAWTEventListener sael = (SelectiveAWTEventListener)la[i];
                if ((sael.getEventMask() & eventMask) == eventMask) {
                    //AWTEventListener tempL = sael.getListener();
                    list.add(new AWTEventListenerProxy(sael.getEventMask(),
                                                       sael.getListener()));
                }
            }
            return list.toArray(new AWTEventListener[0]);
        }
    }

    /*
     * This method notifies any AWTEventListeners that an event
     * is about to be dispatched.
     *
     * <p>
     *  此方法通知任何AWTEventListeners事件将要分派。
     * 
     * 
     * @param theEvent the event which will be dispatched.
     */
    void notifyAWTEventListeners(AWTEvent theEvent) {
        // This is a workaround for headless toolkits.  It would be
        // better to override this method but it is declared package private.
        // "this instanceof" syntax defeats polymorphism.
        // --mm, 03/03/00
        if (this instanceof HeadlessToolkit) {
            ((HeadlessToolkit)this).getUnderlyingToolkit()
                .notifyAWTEventListeners(theEvent);
            return;
        }

        AWTEventListener eventListener = this.eventListener;
        if (eventListener != null) {
            eventListener.eventDispatched(theEvent);
        }
    }

    static private class ToolkitEventMulticaster extends AWTEventMulticaster
        implements AWTEventListener {
        // Implementation cloned from AWTEventMulticaster.

        ToolkitEventMulticaster(AWTEventListener a, AWTEventListener b) {
            super(a, b);
        }

        static AWTEventListener add(AWTEventListener a,
                                    AWTEventListener b) {
            if (a == null)  return b;
            if (b == null)  return a;
            return new ToolkitEventMulticaster(a, b);
        }

        static AWTEventListener remove(AWTEventListener l,
                                       AWTEventListener oldl) {
            return (AWTEventListener) removeInternal(l, oldl);
        }

        // #4178589: must overload remove(EventListener) to call our add()
        // instead of the static addInternal() so we allocate a
        // ToolkitEventMulticaster instead of an AWTEventMulticaster.
        // Note: this method is called by AWTEventListener.removeInternal(),
        // so its method signature must match AWTEventListener.remove().
        protected EventListener remove(EventListener oldl) {
            if (oldl == a)  return b;
            if (oldl == b)  return a;
            AWTEventListener a2 = (AWTEventListener)removeInternal(a, oldl);
            AWTEventListener b2 = (AWTEventListener)removeInternal(b, oldl);
            if (a2 == a && b2 == b) {
                return this;    // it's not here
            }
            return add(a2, b2);
        }

        public void eventDispatched(AWTEvent event) {
            ((AWTEventListener)a).eventDispatched(event);
            ((AWTEventListener)b).eventDispatched(event);
        }
    }

    private class SelectiveAWTEventListener implements AWTEventListener {
        AWTEventListener listener;
        private long eventMask;
        // This array contains the number of times to call the eventlistener
        // for each event type.
        int[] calls = new int[Toolkit.LONG_BITS];

        public AWTEventListener getListener() {return listener;}
        public long getEventMask() {return eventMask;}
        public int[] getCalls() {return calls;}

        public void orEventMasks(long mask) {
            eventMask |= mask;
            // For each event bit set in mask, increment its call count.
            for (int i=0; i<Toolkit.LONG_BITS; i++) {
                // If no bits are set, break out of loop.
                if (mask == 0) {
                    break;
                }
                if ((mask & 1L) != 0) {  // Always test bit 0.
                    calls[i]++;
                }
                mask >>>= 1;  // Right shift, fill with zeros on left.
            }
        }

        SelectiveAWTEventListener(AWTEventListener l, long mask) {
            listener = l;
            eventMask = mask;
        }

        public void eventDispatched(AWTEvent event) {
            long eventBit = 0; // Used to save the bit of the event type.
            if (((eventBit = eventMask & AWTEvent.COMPONENT_EVENT_MASK) != 0 &&
                 event.id >= ComponentEvent.COMPONENT_FIRST &&
                 event.id <= ComponentEvent.COMPONENT_LAST)
             || ((eventBit = eventMask & AWTEvent.CONTAINER_EVENT_MASK) != 0 &&
                 event.id >= ContainerEvent.CONTAINER_FIRST &&
                 event.id <= ContainerEvent.CONTAINER_LAST)
             || ((eventBit = eventMask & AWTEvent.FOCUS_EVENT_MASK) != 0 &&
                 event.id >= FocusEvent.FOCUS_FIRST &&
                 event.id <= FocusEvent.FOCUS_LAST)
             || ((eventBit = eventMask & AWTEvent.KEY_EVENT_MASK) != 0 &&
                 event.id >= KeyEvent.KEY_FIRST &&
                 event.id <= KeyEvent.KEY_LAST)
             || ((eventBit = eventMask & AWTEvent.MOUSE_WHEEL_EVENT_MASK) != 0 &&
                 event.id == MouseEvent.MOUSE_WHEEL)
             || ((eventBit = eventMask & AWTEvent.MOUSE_MOTION_EVENT_MASK) != 0 &&
                 (event.id == MouseEvent.MOUSE_MOVED ||
                  event.id == MouseEvent.MOUSE_DRAGGED))
             || ((eventBit = eventMask & AWTEvent.MOUSE_EVENT_MASK) != 0 &&
                 event.id != MouseEvent.MOUSE_MOVED &&
                 event.id != MouseEvent.MOUSE_DRAGGED &&
                 event.id != MouseEvent.MOUSE_WHEEL &&
                 event.id >= MouseEvent.MOUSE_FIRST &&
                 event.id <= MouseEvent.MOUSE_LAST)
             || ((eventBit = eventMask & AWTEvent.WINDOW_EVENT_MASK) != 0 &&
                 (event.id >= WindowEvent.WINDOW_FIRST &&
                 event.id <= WindowEvent.WINDOW_LAST))
             || ((eventBit = eventMask & AWTEvent.ACTION_EVENT_MASK) != 0 &&
                 event.id >= ActionEvent.ACTION_FIRST &&
                 event.id <= ActionEvent.ACTION_LAST)
             || ((eventBit = eventMask & AWTEvent.ADJUSTMENT_EVENT_MASK) != 0 &&
                 event.id >= AdjustmentEvent.ADJUSTMENT_FIRST &&
                 event.id <= AdjustmentEvent.ADJUSTMENT_LAST)
             || ((eventBit = eventMask & AWTEvent.ITEM_EVENT_MASK) != 0 &&
                 event.id >= ItemEvent.ITEM_FIRST &&
                 event.id <= ItemEvent.ITEM_LAST)
             || ((eventBit = eventMask & AWTEvent.TEXT_EVENT_MASK) != 0 &&
                 event.id >= TextEvent.TEXT_FIRST &&
                 event.id <= TextEvent.TEXT_LAST)
             || ((eventBit = eventMask & AWTEvent.INPUT_METHOD_EVENT_MASK) != 0 &&
                 event.id >= InputMethodEvent.INPUT_METHOD_FIRST &&
                 event.id <= InputMethodEvent.INPUT_METHOD_LAST)
             || ((eventBit = eventMask & AWTEvent.PAINT_EVENT_MASK) != 0 &&
                 event.id >= PaintEvent.PAINT_FIRST &&
                 event.id <= PaintEvent.PAINT_LAST)
             || ((eventBit = eventMask & AWTEvent.INVOCATION_EVENT_MASK) != 0 &&
                 event.id >= InvocationEvent.INVOCATION_FIRST &&
                 event.id <= InvocationEvent.INVOCATION_LAST)
             || ((eventBit = eventMask & AWTEvent.HIERARCHY_EVENT_MASK) != 0 &&
                 event.id == HierarchyEvent.HIERARCHY_CHANGED)
             || ((eventBit = eventMask & AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK) != 0 &&
                 (event.id == HierarchyEvent.ANCESTOR_MOVED ||
                  event.id == HierarchyEvent.ANCESTOR_RESIZED))
             || ((eventBit = eventMask & AWTEvent.WINDOW_STATE_EVENT_MASK) != 0 &&
                 event.id == WindowEvent.WINDOW_STATE_CHANGED)
             || ((eventBit = eventMask & AWTEvent.WINDOW_FOCUS_EVENT_MASK) != 0 &&
                 (event.id == WindowEvent.WINDOW_GAINED_FOCUS ||
                  event.id == WindowEvent.WINDOW_LOST_FOCUS))
                || ((eventBit = eventMask & sun.awt.SunToolkit.GRAB_EVENT_MASK) != 0 &&
                    (event instanceof sun.awt.UngrabEvent))) {
                // Get the index of the call count for this event type.
                // Instead of using Math.log(...) we will calculate it with
                // bit shifts. That's what previous implementation looked like:
                //
                // int ci = (int) (Math.log(eventBit)/Math.log(2));
                int ci = 0;
                for (long eMask = eventBit; eMask != 0; eMask >>>= 1, ci++) {
                }
                ci--;
                // Call the listener as many times as it was added for this
                // event type.
                for (int i=0; i<calls[ci]; i++) {
                    listener.eventDispatched(event);
                }
            }
        }
    }

    /**
     * Returns a map of visual attributes for the abstract level description
     * of the given input method highlight, or null if no mapping is found.
     * The style field of the input method highlight is ignored. The map
     * returned is unmodifiable.
     * <p>
     * 返回给定输入法高亮的抽象级别描述的视觉属性的映射,如果未找到映射,则返回null。忽略输入法高亮显示的样式字段。返回的映射是不可修改的。
     * 
     * 
     * @param highlight input method highlight
     * @return style attribute map, or <code>null</code>
     * @exception HeadlessException if
     *     <code>GraphicsEnvironment.isHeadless</code> returns true
     * @see       java.awt.GraphicsEnvironment#isHeadless
     * @since 1.3
     */
    public abstract Map<java.awt.font.TextAttribute,?>
        mapInputMethodHighlight(InputMethodHighlight highlight)
        throws HeadlessException;

    private static PropertyChangeSupport createPropertyChangeSupport(Toolkit toolkit) {
        if (toolkit instanceof SunToolkit || toolkit instanceof HeadlessToolkit) {
            return new DesktopPropertyChangeSupport(toolkit);
        } else {
            return new PropertyChangeSupport(toolkit);
        }
    }

    @SuppressWarnings("serial")
    private static class DesktopPropertyChangeSupport extends PropertyChangeSupport {

        private static final StringBuilder PROP_CHANGE_SUPPORT_KEY =
                new StringBuilder("desktop property change support key");
        private final Object source;

        public DesktopPropertyChangeSupport(Object sourceBean) {
            super(sourceBean);
            source = sourceBean;
        }

        @Override
        public synchronized void addPropertyChangeListener(
                String propertyName,
                PropertyChangeListener listener)
        {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null == pcs) {
                pcs = new PropertyChangeSupport(source);
                AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, pcs);
            }
            pcs.addPropertyChangeListener(propertyName, listener);
        }

        @Override
        public synchronized void removePropertyChangeListener(
                String propertyName,
                PropertyChangeListener listener)
        {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null != pcs) {
                pcs.removePropertyChangeListener(propertyName, listener);
            }
        }

        @Override
        public synchronized PropertyChangeListener[] getPropertyChangeListeners()
        {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null != pcs) {
                return pcs.getPropertyChangeListeners();
            } else {
                return new PropertyChangeListener[0];
            }
        }

        @Override
        public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName)
        {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null != pcs) {
                return pcs.getPropertyChangeListeners(propertyName);
            } else {
                return new PropertyChangeListener[0];
            }
        }

        @Override
        public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null == pcs) {
                pcs = new PropertyChangeSupport(source);
                AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, pcs);
            }
            pcs.addPropertyChangeListener(listener);
        }

        @Override
        public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
            PropertyChangeSupport pcs = (PropertyChangeSupport)
                    AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
            if (null != pcs) {
                pcs.removePropertyChangeListener(listener);
            }
        }

        /*
         * we do expect that all other fireXXX() methods of java.beans.PropertyChangeSupport
         * use this method.  If this will be changed we will need to change this class.
         * <p>
         *  我们确实期望java.beans.PropertyChangeSupport的所有其他fireXXX()方法使用此方法。如果这将被改变,我们将需要改变这个类。
         * 
         */
        @Override
        public void firePropertyChange(final PropertyChangeEvent evt) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            String propertyName = evt.getPropertyName();
            if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
                return;
            }
            Runnable updater = new Runnable() {
                public void run() {
                    PropertyChangeSupport pcs = (PropertyChangeSupport)
                            AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
                    if (null != pcs) {
                        pcs.firePropertyChange(evt);
                    }
                }
            };
            final AppContext currentAppContext = AppContext.getAppContext();
            for (AppContext appContext : AppContext.getAppContexts()) {
                if (null == appContext || appContext.isDisposed()) {
                    continue;
                }
                if (currentAppContext == appContext) {
                    updater.run();
                } else {
                    final PeerEvent e = new PeerEvent(source, updater, PeerEvent.ULTIMATE_PRIORITY_EVENT);
                    SunToolkit.postEvent(appContext, e);
                }
            }
        }
    }

    /**
    * Reports whether events from extra mouse buttons are allowed to be processed and posted into
    * {@code EventQueue}.
    * <br>
    * To change the returned value it is necessary to set the {@code sun.awt.enableExtraMouseButtons}
    * property before the {@code Toolkit} class initialization. This setting could be done on the application
    * startup by the following command:
    * <pre>
    * java -Dsun.awt.enableExtraMouseButtons=false Application
    * </pre>
    * Alternatively, the property could be set in the application by using the following code:
    * <pre>
    * System.setProperty("sun.awt.enableExtraMouseButtons", "true");
    * </pre>
    * before the {@code Toolkit} class initialization.
    * If not set by the time of the {@code Toolkit} class initialization, this property will be
    * initialized with {@code true}.
    * Changing this value after the {@code Toolkit} class initialization will have no effect.
    * <p>
    * <p>
    *  报告是否允许处理来自额外鼠标按钮的事件并将其发布到{@code EventQueue}。
    * <br>
    *  要更改返回值,必须在{@code Toolkit}类初始化之前设置{@code sun.awt.enableExtraMouseButtons}属性。此设置可以通过以下命令在应用程序启动时完成：
    * <pre>
    *  java -Dsun.awt.enableExtraMouseButtons = false应用程序
    * </pre>
    *  或者,可以通过使用以下代码在应用程序中设置属性：
    * 
    * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns true
    * @return {@code true} if events from extra mouse buttons are allowed to be processed and posted;
    *         {@code false} otherwise
    * @see System#getProperty(String propertyName)
    * @see System#setProperty(String propertyName, String value)
    * @see java.awt.EventQueue
    * @since 1.7
     */
    public boolean areExtraMouseButtonsEnabled() throws HeadlessException {
        GraphicsEnvironment.checkHeadless();

        return Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled();
    }
}
