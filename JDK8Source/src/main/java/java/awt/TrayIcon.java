/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.peer.TrayIconPeer;
import sun.awt.AppContext;
import sun.awt.SunToolkit;
import sun.awt.AWTAccessor;
import sun.awt.HeadlessToolkit;
import java.util.EventObject;
import java.security.AccessControlContext;
import java.security.AccessController;

/**
 * A <code>TrayIcon</code> object represents a tray icon that can be
 * added to the {@link SystemTray system tray}. A
 * <code>TrayIcon</code> can have a tooltip (text), an image, a popup
 * menu, and a set of listeners associated with it.
 *
 * <p>A <code>TrayIcon</code> can generate various {@link MouseEvent
 * MouseEvents} and supports adding corresponding listeners to receive
 * notification of these events.  <code>TrayIcon</code> processes some
 * of the events by itself.  For example, by default, when the
 * right-mouse click is performed on the <code>TrayIcon</code> it
 * displays the specified popup menu.  When the mouse hovers
 * over the <code>TrayIcon</code> the tooltip is displayed.
 *
 * <p><strong>Note:</strong> When the <code>MouseEvent</code> is
 * dispatched to its registered listeners its <code>component</code>
 * property will be set to <code>null</code>.  (See {@link
 * java.awt.event.ComponentEvent#getComponent}) The
 * <code>source</code> property will be set to this
 * <code>TrayIcon</code>. (See {@link
 * java.util.EventObject#getSource})
 *
 * <p><b>Note:</b> A well-behaved {@link TrayIcon} implementation
 * will assign different gestures to showing a popup menu and
 * selecting a tray icon.
 *
 * <p>A <code>TrayIcon</code> can generate an {@link ActionEvent
 * ActionEvent}.  On some platforms, this occurs when the user selects
 * the tray icon using either the mouse or keyboard.
 *
 * <p>If a SecurityManager is installed, the AWTPermission
 * {@code accessSystemTray} must be granted in order to create
 * a {@code TrayIcon}. Otherwise the constructor will throw a
 * SecurityException.
 *
 * <p> See the {@link SystemTray} class overview for an example on how
 * to use the <code>TrayIcon</code> API.
 *
 * <p>
 *  <code> TrayIcon </code>对象表示可以添加到{@link SystemTray系统托盘}的托盘图标。
 *  <code> TrayIcon </code>可以有一个工具提示(文本),一个图像,一个弹出菜单和一组与之相关联的监听器。
 * 
 *  <p> <code> TrayIcon </code>可以生成各种{@link MouseEvent MouseEvents},并支持添加相应的侦听器以接收这些事件的通知。
 *  <code> TrayIcon </code>自己处理一些事件。例如,默认情况下,当在<code> TrayIcon </code>上执行鼠标右键单击时,它会显示指定的弹出菜单。
 * 当鼠标悬停在<code> TrayIcon </code>上时,将显示工具提示。
 * 
 *  <p> <strong>注意</strong>：<code> MouseEvent </code>被分派给注册的侦听器时,其<code> component </code>属性将设置为<code> n
 * ull </code> 。
 *  (参见{@link java.awt.event.ComponentEvent#getComponent})<code> source </code>属性将设置为<code> TrayIcon </code>
 * 。
 *  (请参阅{@link java.util.EventObject#getSource})。
 * 
 *  <p> <b>注意：</b>一个行为良好的{@link TrayIcon}实现会分配不同的手势来显示弹出式菜单并选择一个托盘图标。
 * 
 *  <p> <code> TrayIcon </code>可以生成{@link ActionEvent ActionEvent}。在某些平台上,当用户使用鼠标或键盘选择托盘图标时,会发生这种情况。
 * 
 * <p>如果安装了SecurityManager,则必须授予AWTermission {@code accessSystemTray}以创建{@code TrayIcon}。
 * 否则构造函数将抛出一个SecurityException。
 * 
 *  <p>有关如何使用<code> TrayIcon </code> API的示例,请参阅{@link SystemTray}类概述。
 * 
 * 
 * @since 1.6
 * @see SystemTray#add
 * @see java.awt.event.ComponentEvent#getComponent
 * @see java.util.EventObject#getSource
 *
 * @author Bino George
 * @author Denis Mikhalkin
 * @author Sharon Zakhour
 * @author Anton Tarasov
 */
public class TrayIcon {

    private Image image;
    private String tooltip;
    private PopupMenu popup;
    private boolean autosize;
    private int id;
    private String actionCommand;

    transient private TrayIconPeer peer;

    transient MouseListener mouseListener;
    transient MouseMotionListener mouseMotionListener;
    transient ActionListener actionListener;

    /*
     * The tray icon's AccessControlContext.
     *
     * Unlike the acc in Component, this field is made final
     * because TrayIcon is not serializable.
     * <p>
     *  托盘图标的AccessControlContext。
     * 
     *  与组件中的acc不同,此字段是最终的,因为TrayIcon不可序列化。
     * 
     */
    private final AccessControlContext acc = AccessController.getContext();

    /*
     * Returns the acc this tray icon was constructed with.
     * <p>
     *  返回此托盘图标所构造的acc。
     * 
     */
    final AccessControlContext getAccessControlContext() {
        if (acc == null) {
            throw new SecurityException("TrayIcon is missing AccessControlContext");
        }
        return acc;
    }

    static {
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }

        AWTAccessor.setTrayIconAccessor(
            new AWTAccessor.TrayIconAccessor() {
                public void addNotify(TrayIcon trayIcon) throws AWTException {
                    trayIcon.addNotify();
                }
                public void removeNotify(TrayIcon trayIcon) {
                    trayIcon.removeNotify();
                }
            });
    }

    private TrayIcon()
      throws UnsupportedOperationException, HeadlessException, SecurityException
    {
        SystemTray.checkSystemTrayAllowed();
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
        if (!SystemTray.isSupported()) {
            throw new UnsupportedOperationException();
        }
        SunToolkit.insertTargetMapping(this, AppContext.getAppContext());
    }

    /**
     * Creates a <code>TrayIcon</code> with the specified image.
     *
     * <p>
     *  使用指定的图像创建<code> TrayIcon </code>。
     * 
     * 
     * @param image the <code>Image</code> to be used
     * @throws IllegalArgumentException if <code>image</code> is
     * <code>null</code>
     * @throws UnsupportedOperationException if the system tray isn't
     * supported by the current platform
     * @throws HeadlessException if
     * {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission
     * is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String, PopupMenu)
     * @see TrayIcon#TrayIcon(Image, String)
     * @see SecurityManager#checkPermission
     * @see AWTPermission
     */
    public TrayIcon(Image image) {
        this();
        if (image == null) {
            throw new IllegalArgumentException("creating TrayIcon with null Image");
        }
        setImage(image);
    }

    /**
     * Creates a <code>TrayIcon</code> with the specified image and
     * tooltip text.
     *
     * <p>
     *  使用指定的图像和工具提示文本创建<code> TrayIcon </code>。
     * 
     * 
     * @param image the <code>Image</code> to be used
     * @param tooltip the string to be used as tooltip text; if the
     * value is <code>null</code> no tooltip is shown
     * @throws IllegalArgumentException if <code>image</code> is
     * <code>null</code>
     * @throws UnsupportedOperationException if the system tray isn't
     * supported by the current platform
     * @throws HeadlessException if
     * {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission
     * is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image)
     * @see TrayIcon#TrayIcon(Image, String, PopupMenu)
     * @see SecurityManager#checkPermission
     * @see AWTPermission
     */
    public TrayIcon(Image image, String tooltip) {
        this(image);
        setToolTip(tooltip);
    }

    /**
     * Creates a <code>TrayIcon</code> with the specified image,
     * tooltip and popup menu.
     *
     * <p>
     *  使用指定的图像,工具提示和弹出式菜单创建<code> TrayIcon </code>。
     * 
     * 
     * @param image the <code>Image</code> to be used
     * @param tooltip the string to be used as tooltip text; if the
     * value is <code>null</code> no tooltip is shown
     * @param popup the menu to be used for the tray icon's popup
     * menu; if the value is <code>null</code> no popup menu is shown
     * @throws IllegalArgumentException if <code>image</code> is <code>null</code>
     * @throws UnsupportedOperationException if the system tray isn't
     * supported by the current platform
     * @throws HeadlessException if
     * {@code GraphicsEnvironment.isHeadless()} returns {@code true}
     * @throws SecurityException if {@code accessSystemTray} permission
     * is not granted
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String)
     * @see TrayIcon#TrayIcon(Image)
     * @see PopupMenu
     * @see MouseListener
     * @see #addMouseListener(MouseListener)
     * @see SecurityManager#checkPermission
     * @see AWTPermission
     */
    public TrayIcon(Image image, String tooltip, PopupMenu popup) {
        this(image, tooltip);
        setPopupMenu(popup);
    }

    /**
     * Sets the image for this <code>TrayIcon</code>.  The previous
     * tray icon image is discarded without calling the {@link
     * java.awt.Image#flush} method &#151; you will need to call it
     * manually.
     *
     * <p> If the image represents an animated image, it will be
     * animated automatically.
     *
     * <p> See the {@link #setImageAutoSize(boolean)} property for
     * details on the size of the displayed image.
     *
     * <p> Calling this method with the same image that is currently
     * being used has no effect.
     *
     * <p>
     *  为此<code> TrayIcon </code>设置图像。系统会舍弃之前的托盘图标图片,而不调用{@link java.awt.Image#flush}方法 - 您需要手动调用它。
     * 
     *  <p>如果图像表示动画图像,则会自动动画。
     * 
     *  <p>有关所显示图片大小的详细信息,请参阅{@link #setImageAutoSize(boolean)}属性。
     * 
     *  <p>使用当前正在使用的同一图像调用此方法无效。
     * 
     * 
     * @throws NullPointerException if <code>image</code> is <code>null</code>
     * @param image the non-null <code>Image</code> to be used
     * @see #getImage
     * @see Image
     * @see SystemTray#add(TrayIcon)
     * @see TrayIcon#TrayIcon(Image, String)
     */
    public void setImage(Image image) {
        if (image == null) {
            throw new NullPointerException("setting null Image");
        }
        this.image = image;

        TrayIconPeer peer = this.peer;
        if (peer != null) {
            peer.updateImage();
        }
    }

    /**
     * Returns the current image used for this <code>TrayIcon</code>.
     *
     * <p>
     *  返回用于此<code> TrayIcon </code>的当前图像。
     * 
     * 
     * @return the image
     * @see #setImage(Image)
     * @see Image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the popup menu for this <code>TrayIcon</code>.  If
     * <code>popup</code> is <code>null</code>, no popup menu will be
     * associated with this <code>TrayIcon</code>.
     *
     * <p>Note that this <code>popup</code> must not be added to any
     * parent before or after it is set on the tray icon.  If you add
     * it to some parent, the <code>popup</code> may be removed from
     * that parent.
     *
     * <p>The {@code popup} can be set on one {@code TrayIcon} only.
     * Setting the same popup on multiple {@code TrayIcon}s will cause
     * an {@code IllegalArgumentException}.
     *
     * <p><strong>Note:</strong> Some platforms may not support
     * showing the user-specified popup menu component when the user
     * right-clicks the tray icon.  In this situation, either no menu
     * will be displayed or, on some systems, a native version of the
     * menu may be displayed.
     *
     * <p>
     *  为此<code> TrayIcon </code>设置弹出菜单。
     * 如果<code>弹出</code>是<code> null </code>,则不会有弹出式菜单与此<code> TrayIcon </code>相关联。
     * 
     * <p>请注意,此<code>弹出式窗口</code>不得在托盘图标上设置之前或之后添加到任何父级。如果将它添加到某个父级,可以从该父级中删除<code>弹出</code>。
     * 
     *  <p> {@code popup}只能在一个{@code TrayIcon}上设置。
     * 在多个{@code TrayIcon}上设置相同的弹出窗口将导致{@code IllegalArgumentException}。
     * 
     *  <p> <strong>注意</strong>：当用户右键点击托盘图标时,某些平台可能不支持显示用户指定的弹出菜单组件。在这种情况下,将不显示菜单,或者在一些系统上,可以显示菜单的本机版本。
     * 
     * 
     * @throws IllegalArgumentException if the {@code popup} is already
     * set for another {@code TrayIcon}
     * @param popup a <code>PopupMenu</code> or <code>null</code> to
     * remove any popup menu
     * @see #getPopupMenu
     */
    public void setPopupMenu(PopupMenu popup) {
        if (popup == this.popup) {
            return;
        }
        synchronized (TrayIcon.class) {
            if (popup != null) {
                if (popup.isTrayIconPopup) {
                    throw new IllegalArgumentException("the PopupMenu is already set for another TrayIcon");
                }
                popup.isTrayIconPopup = true;
            }
            if (this.popup != null) {
                this.popup.isTrayIconPopup = false;
            }
            this.popup = popup;
        }
    }

    /**
     * Returns the popup menu associated with this <code>TrayIcon</code>.
     *
     * <p>
     *  返回与此<code> TrayIcon </code>关联的弹出菜单。
     * 
     * 
     * @return the popup menu or <code>null</code> if none exists
     * @see #setPopupMenu(PopupMenu)
     */
    public PopupMenu getPopupMenu() {
        return popup;
    }

    /**
     * Sets the tooltip string for this <code>TrayIcon</code>. The
     * tooltip is displayed automatically when the mouse hovers over
     * the icon.  Setting the tooltip to <code>null</code> removes any
     * tooltip text.
     *
     * When displayed, the tooltip string may be truncated on some platforms;
     * the number of characters that may be displayed is platform-dependent.
     *
     * <p>
     *  为此<code> TrayIcon </code>设置工具提示字符串。当鼠标悬停在图标上时,会自动显示工具提示。将工具提示设置为<code> null </code>可删除任何工具提示文本。
     * 
     *  显示时,工具提示字符串可能在某些平台上被截断;可以显示的字符的数量是平台相关的。
     * 
     * 
     * @param tooltip the string for the tooltip; if the value is
     * <code>null</code> no tooltip is shown
     * @see #getToolTip
     */
    public void setToolTip(String tooltip) {
        this.tooltip = tooltip;

        TrayIconPeer peer = this.peer;
        if (peer != null) {
            peer.setToolTip(tooltip);
        }
    }

    /**
     * Returns the tooltip string associated with this
     * <code>TrayIcon</code>.
     *
     * <p>
     *  返回与此<code> TrayIcon </code>关联的工具提示字符串。
     * 
     * 
     * @return the tooltip string or <code>null</code> if none exists
     * @see #setToolTip(String)
     */
    public String getToolTip() {
        return tooltip;
    }

    /**
     * Sets the auto-size property.  Auto-size determines whether the
     * tray image is automatically sized to fit the space allocated
     * for the image on the tray.  By default, the auto-size property
     * is set to <code>false</code>.
     *
     * <p> If auto-size is <code>false</code>, and the image size
     * doesn't match the tray icon space, the image is painted as-is
     * inside that space &#151; if larger than the allocated space, it will
     * be cropped.
     *
     * <p> If auto-size is <code>true</code>, the image is stretched or shrunk to
     * fit the tray icon space.
     *
     * <p>
     *  设置自动大小属性。自动调整尺寸决定了托盘图像是否自动调整大小以适合托盘上分配给图像的空间。默认情况下,auto-size属性设置为<code> false </code>。
     * 
     * <p>如果自动大小为<code> false </code>,且图片大小与托盘图标空间不匹配,则图片将按原样绘制在该空间中 - 如果大于分配的空间,裁剪。
     * 
     *  <p>如果自动调整大小为<code> true </code>,则会拉伸或缩小图像以适应托盘图标空间。
     * 
     * 
     * @param autosize <code>true</code> to auto-size the image,
     * <code>false</code> otherwise
     * @see #isImageAutoSize
     */
    public void setImageAutoSize(boolean autosize) {
        this.autosize = autosize;

        TrayIconPeer peer = this.peer;
        if (peer != null) {
            peer.updateImage();
        }
    }

    /**
     * Returns the value of the auto-size property.
     *
     * <p>
     *  返回自动大小属性的值。
     * 
     * 
     * @return <code>true</code> if the image will be auto-sized,
     * <code>false</code> otherwise
     * @see #setImageAutoSize(boolean)
     */
    public boolean isImageAutoSize() {
        return autosize;
    }

    /**
     * Adds the specified mouse listener to receive mouse events from
     * this <code>TrayIcon</code>.  Calling this method with a
     * <code>null</code> value has no effect.
     *
     * <p><b>Note</b>: The {@code MouseEvent}'s coordinates (received
     * from the {@code TrayIcon}) are relative to the screen, not the
     * {@code TrayIcon}.
     *
     * <p> <b>Note: </b>The <code>MOUSE_ENTERED</code> and
     * <code>MOUSE_EXITED</code> mouse events are not supported.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的鼠标监听器以从此<code> TrayIcon </code>接收鼠标事件。使用<code> null </code>值调用此方法没有任何效果。
     * 
     *  <p> <b>注意</b>：{@code MouseEvent}的坐标(从{@code TrayIcon}接收)相对于屏幕,而不是{@code TrayIcon}。
     * 
     *  <p> <b>注意：</b>不支持<code> MOUSE_ENTERED </code>和<code> MOUSE_EXITED </code>鼠标事件。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    listener the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseListener
     * @see      #removeMouseListener(MouseListener)
     * @see      #getMouseListeners
     */
    public synchronized void addMouseListener(MouseListener listener) {
        if (listener == null) {
            return;
        }
        mouseListener = AWTEventMulticaster.add(mouseListener, listener);
    }

    /**
     * Removes the specified mouse listener.  Calling this method with
     * <code>null</code> or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的鼠标监听器。使用<code> null </code>或无效值调用此方法无效。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseListener
     * @see      #addMouseListener(MouseListener)
     * @see      #getMouseListeners
     */
    public synchronized void removeMouseListener(MouseListener listener) {
        if (listener == null) {
            return;
        }
        mouseListener = AWTEventMulticaster.remove(mouseListener, listener);
    }

    /**
     * Returns an array of all the mouse listeners
     * registered on this <code>TrayIcon</code>.
     *
     * <p>
     *  返回在此<code> TrayIcon </code>上注册的所有鼠标监听器的数组。
     * 
     * 
     * @return all of the <code>MouseListeners</code> registered on
     * this <code>TrayIcon</code> or an empty array if no mouse
     * listeners are currently registered
     *
     * @see      #addMouseListener(MouseListener)
     * @see      #removeMouseListener(MouseListener)
     * @see      java.awt.event.MouseListener
     */
    public synchronized MouseListener[] getMouseListeners() {
        return AWTEventMulticaster.getListeners(mouseListener, MouseListener.class);
    }

    /**
     * Adds the specified mouse listener to receive mouse-motion
     * events from this <code>TrayIcon</code>.  Calling this method
     * with a <code>null</code> value has no effect.
     *
     * <p><b>Note</b>: The {@code MouseEvent}'s coordinates (received
     * from the {@code TrayIcon}) are relative to the screen, not the
     * {@code TrayIcon}.
     *
     * <p> <b>Note: </b>The <code>MOUSE_DRAGGED</code> mouse event is not supported.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的鼠标监听器以从此<code> TrayIcon </code>接收鼠标运动事件。使用<code> null </code>值调用此方法无效。
     * 
     * <p> <b>注意</b>：{@code MouseEvent}的坐标(从{@code TrayIcon}接收)相对于屏幕,而不是{@code TrayIcon}。
     * 
     *  <p> <b>注意：</b>不支援<code> MOUSE_DRAGGED </code>滑鼠事件。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseMotionListener
     * @see      #removeMouseMotionListener(MouseMotionListener)
     * @see      #getMouseMotionListeners
     */
    public synchronized void addMouseMotionListener(MouseMotionListener listener) {
        if (listener == null) {
            return;
        }
        mouseMotionListener = AWTEventMulticaster.add(mouseMotionListener, listener);
    }

    /**
     * Removes the specified mouse-motion listener.  Calling this method with
     * <code>null</code> or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的鼠标移动侦听器。使用<code> null </code>或无效值调用此方法无效。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    listener   the mouse listener
     * @see      java.awt.event.MouseEvent
     * @see      java.awt.event.MouseMotionListener
     * @see      #addMouseMotionListener(MouseMotionListener)
     * @see      #getMouseMotionListeners
     */
    public synchronized void removeMouseMotionListener(MouseMotionListener listener) {
        if (listener == null) {
            return;
        }
        mouseMotionListener = AWTEventMulticaster.remove(mouseMotionListener, listener);
    }

    /**
     * Returns an array of all the mouse-motion listeners
     * registered on this <code>TrayIcon</code>.
     *
     * <p>
     *  返回在此<code> TrayIcon </code>上注册的所有鼠标移动侦听器的数组。
     * 
     * 
     * @return all of the <code>MouseInputListeners</code> registered on
     * this <code>TrayIcon</code> or an empty array if no mouse
     * listeners are currently registered
     *
     * @see      #addMouseMotionListener(MouseMotionListener)
     * @see      #removeMouseMotionListener(MouseMotionListener)
     * @see      java.awt.event.MouseMotionListener
     */
    public synchronized MouseMotionListener[] getMouseMotionListeners() {
        return AWTEventMulticaster.getListeners(mouseMotionListener, MouseMotionListener.class);
    }

    /**
     * Returns the command name of the action event fired by this tray icon.
     *
     * <p>
     *  返回此托盘图标触发的操作事件的命令名称。
     * 
     * 
     * @return the action command name, or <code>null</code> if none exists
     * @see #addActionListener(ActionListener)
     * @see #setActionCommand(String)
     */
    public String getActionCommand() {
        return actionCommand;
    }

    /**
     * Sets the command name for the action event fired by this tray
     * icon.  By default, this action command is set to
     * <code>null</code>.
     *
     * <p>
     *  设置此托盘图标触发的操作事件的命令名称。默认情况下,此操作命令设置为<code> null </code>。
     * 
     * 
     * @param command  a string used to set the tray icon's
     *                 action command.
     * @see java.awt.event.ActionEvent
     * @see #addActionListener(ActionListener)
     * @see #getActionCommand
     */
    public void setActionCommand(String command) {
        actionCommand = command;
    }

    /**
     * Adds the specified action listener to receive
     * <code>ActionEvent</code>s from this <code>TrayIcon</code>.
     * Action events usually occur when a user selects the tray icon,
     * using either the mouse or keyboard.  The conditions in which
     * action events are generated are platform-dependent.
     *
     * <p>Calling this method with a <code>null</code> value has no
     * effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的动作侦听器以从<code> TrayIcon </code>接收<code> ActionEvent </code>。当用户使用鼠标或键盘选择托盘图标时,通常会发生操作事件。
     * 生成操作事件的条件是平台相关的。
     * 
     *  <p>使用<code> null </code>值调用此方法没有任何效果。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         listener the action listener
     * @see           #removeActionListener
     * @see           #getActionListeners
     * @see           java.awt.event.ActionListener
     * @see #setActionCommand(String)
     */
    public synchronized void addActionListener(ActionListener listener) {
        if (listener == null) {
            return;
        }
        actionListener = AWTEventMulticaster.add(actionListener, listener);
    }

    /**
     * Removes the specified action listener.  Calling this method with
     * <code>null</code> or an invalid value has no effect.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     * 删除指定的操作侦听器。使用<code> null </code>或无效值调用此方法无效。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    listener   the action listener
     * @see      java.awt.event.ActionEvent
     * @see      java.awt.event.ActionListener
     * @see      #addActionListener(ActionListener)
     * @see      #getActionListeners
     * @see #setActionCommand(String)
     */
    public synchronized void removeActionListener(ActionListener listener) {
        if (listener == null) {
            return;
        }
        actionListener = AWTEventMulticaster.remove(actionListener, listener);
    }

    /**
     * Returns an array of all the action listeners
     * registered on this <code>TrayIcon</code>.
     *
     * <p>
     *  返回在此<code> TrayIcon </code>上注册的所有操作侦听器的数组。
     * 
     * 
     * @return all of the <code>ActionListeners</code> registered on
     * this <code>TrayIcon</code> or an empty array if no action
     * listeners are currently registered
     *
     * @see      #addActionListener(ActionListener)
     * @see      #removeActionListener(ActionListener)
     * @see      java.awt.event.ActionListener
     */
    public synchronized ActionListener[] getActionListeners() {
        return AWTEventMulticaster.getListeners(actionListener, ActionListener.class);
    }

    /**
     * The message type determines which icon will be displayed in the
     * caption of the message, and a possible system sound a message
     * may generate upon showing.
     *
     * <p>
     *  消息类型确定将在消息的标题中显示哪个图标,以及消息在显示时可能产生的系统声音。
     * 
     * 
     * @see TrayIcon
     * @see TrayIcon#displayMessage(String, String, MessageType)
     * @since 1.6
     */
    public enum MessageType {
        /** An error message */
        ERROR,
        /** A warning message */
        WARNING,
        /** An information message */
        INFO,
        /** Simple message */
        NONE
    };

    /**
     * Displays a popup message near the tray icon.  The message will
     * disappear after a time or if the user clicks on it.  Clicking
     * on the message may trigger an {@code ActionEvent}.
     *
     * <p>Either the caption or the text may be <code>null</code>, but an
     * <code>NullPointerException</code> is thrown if both are
     * <code>null</code>.
     *
     * When displayed, the caption or text strings may be truncated on
     * some platforms; the number of characters that may be displayed is
     * platform-dependent.
     *
     * <p><strong>Note:</strong> Some platforms may not support
     * showing a message.
     *
     * <p>
     *  在托盘图标附近显示弹出消息。消息将在一段时间后消失,或者如果用户点击它。点击邮件可能会触发{@code ActionEvent}。
     * 
     *  <p>标题或文本可以是<code> null </code>,但如果两者都是<code> null </code>,则会抛出<code> NullPointerException </code>。
     * 
     *  显示时,字幕或文本字符串可能在某些平台上被截断;可以显示的字符的数量是平台相关的。
     * 
     * 
     * @param caption the caption displayed above the text, usually in
     * bold; may be <code>null</code>
     * @param text the text displayed for the particular message; may be
     * <code>null</code>
     * @param messageType an enum indicating the message type
     * @throws NullPointerException if both <code>caption</code>
     * and <code>text</code> are <code>null</code>
     */
    public void displayMessage(String caption, String text, MessageType messageType) {
        if (caption == null && text == null) {
            throw new NullPointerException("displaying the message with both caption and text being null");
        }

        TrayIconPeer peer = this.peer;
        if (peer != null) {
            peer.displayMessage(caption, text, messageType.name());
        }
    }

    /**
     * Returns the size, in pixels, of the space that the tray icon
     * occupies in the system tray.  For the tray icon that is not yet
     * added to the system tray, the returned size is equal to the
     * result of the {@link SystemTray#getTrayIconSize}.
     *
     * <p>
     *  <p> <strong>请注意</strong>：某些平台可能不支持显示消息。
     * 
     * 
     * @return the size of the tray icon, in pixels
     * @see TrayIcon#setImageAutoSize(boolean)
     * @see java.awt.Image
     * @see TrayIcon#getSize()
     */
    public Dimension getSize() {
        return SystemTray.getSystemTray().getTrayIconSize();
    }

    // ****************************************************************
    // ****************************************************************

    void addNotify()
      throws AWTException
    {
        synchronized (this) {
            if (peer == null) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                if (toolkit instanceof SunToolkit) {
                    peer = ((SunToolkit)Toolkit.getDefaultToolkit()).createTrayIcon(this);
                } else if (toolkit instanceof HeadlessToolkit) {
                    peer = ((HeadlessToolkit)Toolkit.getDefaultToolkit()).createTrayIcon(this);
                }
            }
        }
        peer.setToolTip(tooltip);
    }

    void removeNotify() {
        TrayIconPeer p = null;
        synchronized (this) {
            p = peer;
            peer = null;
        }
        if (p != null) {
            p.dispose();
        }
    }

    void setID(int id) {
        this.id = id;
    }

    int getID(){
        return id;
    }

    void dispatchEvent(AWTEvent e) {
        EventQueue.setCurrentEventAndMostRecentTime(e);
        Toolkit.getDefaultToolkit().notifyAWTEventListeners(e);
        processEvent(e);
    }

    void processEvent(AWTEvent e) {
        if (e instanceof MouseEvent) {
            switch(e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
            case MouseEvent.MOUSE_RELEASED:
            case MouseEvent.MOUSE_CLICKED:
                processMouseEvent((MouseEvent)e);
                break;
            case MouseEvent.MOUSE_MOVED:
                processMouseMotionEvent((MouseEvent)e);
                break;
            default:
                return;
            }
        } else if (e instanceof ActionEvent) {
            processActionEvent((ActionEvent)e);
        }
    }

    void processMouseEvent(MouseEvent e) {
        MouseListener listener = mouseListener;

        if (listener != null) {
            int id = e.getID();
            switch(id) {
            case MouseEvent.MOUSE_PRESSED:
                listener.mousePressed(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                listener.mouseReleased(e);
                break;
            case MouseEvent.MOUSE_CLICKED:
                listener.mouseClicked(e);
                break;
            default:
                return;
            }
        }
    }

    void processMouseMotionEvent(MouseEvent e) {
        MouseMotionListener listener = mouseMotionListener;
        if (listener != null &&
            e.getID() == MouseEvent.MOUSE_MOVED)
        {
            listener.mouseMoved(e);
        }
    }

    void processActionEvent(ActionEvent e) {
        ActionListener listener = actionListener;
        if (listener != null) {
            listener.actionPerformed(e);
        }
    }

    private static native void initIDs();
}
