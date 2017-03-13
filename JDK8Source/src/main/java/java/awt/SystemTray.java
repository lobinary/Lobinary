/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Vector;
import java.awt.peer.SystemTrayPeer;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import sun.awt.AppContext;
import sun.awt.SunToolkit;
import sun.awt.HeadlessToolkit;
import sun.security.util.SecurityConstants;
import sun.awt.AWTAccessor;

/**
 * The <code>SystemTray</code> class represents the system tray for a
 * desktop.  On Microsoft Windows it is referred to as the "Taskbar
 * Status Area", on Gnome it is referred to as the "Notification
 * Area", on KDE it is referred to as the "System Tray".  The system
 * tray is shared by all applications running on the desktop.
 *
 * <p> On some platforms the system tray may not be present or may not
 * be supported, in this case {@link SystemTray#getSystemTray()}
 * throws {@link UnsupportedOperationException}.  To detect whether the
 * system tray is supported, use {@link SystemTray#isSupported}.
 *
 * <p>The <code>SystemTray</code> may contain one or more {@link
 * TrayIcon TrayIcons}, which are added to the tray using the {@link
 * #add} method, and removed when no longer needed, using the
 * {@link #remove}.  <code>TrayIcon</code> consists of an
 * image, a popup menu and a set of associated listeners.  Please see
 * the {@link TrayIcon} class for details.
 *
 * <p>Every Java application has a single <code>SystemTray</code>
 * instance that allows the app to interface with the system tray of
 * the desktop while the app is running.  The <code>SystemTray</code>
 * instance can be obtained from the {@link #getSystemTray} method.
 * An application may not create its own instance of
 * <code>SystemTray</code>.
 *
 * <p>The following code snippet demonstrates how to access
 * and customize the system tray:
 * <pre>
 * <code>
 *     {@link TrayIcon} trayIcon = null;
 *     if (SystemTray.isSupported()) {
 *         // get the SystemTray instance
 *         SystemTray tray = SystemTray.{@link #getSystemTray};
 *         // load an image
 *         {@link java.awt.Image} image = {@link java.awt.Toolkit#getImage(String) Toolkit.getDefaultToolkit().getImage}(...);
 *         // create a action listener to listen for default action executed on the tray icon
 *         {@link java.awt.event.ActionListener} listener = new {@link java.awt.event.ActionListener ActionListener}() {
 *             public void {@link java.awt.event.ActionListener#actionPerformed actionPerformed}({@link java.awt.event.ActionEvent} e) {
 *                 // execute default action of the application
 *                 // ...
 *             }
 *         };
 *         // create a popup menu
 *         {@link java.awt.PopupMenu} popup = new {@link java.awt.PopupMenu#PopupMenu PopupMenu}();
 *         // create menu item for the default action
 *         MenuItem defaultItem = new MenuItem(...);
 *         defaultItem.addActionListener(listener);
 *         popup.add(defaultItem);
 *         /// ... add other items
 *         // construct a TrayIcon
 *         trayIcon = new {@link TrayIcon#TrayIcon(java.awt.Image, String, java.awt.PopupMenu) TrayIcon}(image, "Tray Demo", popup);
 *         // set the TrayIcon properties
 *         trayIcon.{@link TrayIcon#addActionListener(java.awt.event.ActionListener) addActionListener}(listener);
 *         // ...
 *         // add the tray image
 *         try {
 *             tray.{@link SystemTray#add(TrayIcon) add}(trayIcon);
 *         } catch (AWTException e) {
 *             System.err.println(e);
 *         }
 *         // ...
 *     } else {
 *         // disable tray option in your application or
 *         // perform other actions
 *         ...
 *     }
 *     // ...
 *     // some time later
 *     // the application state has changed - update the image
 *     if (trayIcon != null) {
 *         trayIcon.{@link TrayIcon#setImage(java.awt.Image) setImage}(updatedImage);
 *     }
 *     // ...
 * </code>
 * </pre>
 *
 * <p>
 *  <code> SystemTray </code>类代表桌面的系统托盘在Microsoft Windows上,它被称为"任务栏状态区域",在Gnome上它被称为"通知区域",在KDE上被引用作为"系统
 * 托盘"系统托盘由在桌面上运行的所有应用程序共享。
 * 
 *  <p>在某些平台上,系统托盘可能不存在或可能不受支持,在这种情况下{@link SystemTray#getSystemTray()} throws {@link UnsupportedOperationException}
 * 要检测系统托盘是否受支持,请使用{@link SystemTray#isSupported}。
 * 
 * <p> <code> SystemTray </code>可能包含一个或多个{@link TrayIcon TrayIcons},它们使用{@link #add}方法添加到托盘中,并在不再需要时删除,使
 * 用{ @link #remove} <code> TrayIcon </code>包含图片,弹出式菜单和一组关联的侦听器有关详细信息,请参阅{@link TrayIcon}类。
 * 
 *  <p>每个Java应用程序都有一个<code> SystemTray </code>实例,允许应用程序在应用程序运行时与桌面的系统托盘进行交互</> SystemTray </code>实例可以从{@link #getSystemTray}
 * 方法应用程序可能不会创建自己的实例<code> SystemTray </code>。
 * 
 *  <p>以下代码段演示了如何访问和自定义系统托盘：
 * <pre>
 * <code>
 * {@link TrayIcon} trayIcon = null; if(SystemTrayisSupported()){//获取SystemTray实例SystemTray tray = SystemTray {@link #getSystemTray}
 * ; //加载图像{@link javaawtImage} image = {@link javaawtToolkit#getImage(String)ToolkitgetDefaultToolkit()getImage}
 * (); //创建一个动作监听器,监听托盘图示上执行的预设动作{@link javaawteventActionListener} listener = new {@link javaawteventActionListener ActionListener}
 * (){public void {@link javaawteventActionListener#actionPerformed actionPerformed}({@ link javaawteventActionEvent}
 *  e ){//执行应用程序的默认动作//}}; //创建一个弹出菜单{@link javaawtPopupMenu} popup = new {@link javaawtPopupMenu#PopupMenu PopupMenu}
 * (); //创建默认操作的菜单项MenuItem defaultItem = new MenuItem(); defaultItemaddActionListener(listener); popupa
 * dd(defaultItem); /// add other items //构造一个TrayIcon trayIcon = new {@link TrayIcon#TrayIcon(javaawtImage,String,javaawtPopupMenu)TrayIcon}
 * (image,"Tray Demo",popup); //设置TrayIcon属性trayIcon {@link TrayIcon#addActionListener(javaawteventActionListener)addActionListener}
 * (listener); // //添加托盘图片try {tray {@link SystemTray#add(TrayIcon)add}(trayIcon); } catch(AWTException 
 * e){Systemerrprintln(e); } //} else {//在应用程序中禁用托盘选项或//执行其他操作} ////一段时间后//应用程序状态已经改变 - 如果(trayIcon！= nu
 * ll){trayIcon {@link TrayIcon#setImage(javaawtImage)setImage}(updatedImage) } //。
 * </code>
 * </pre>
 * 
 * 
 * @since 1.6
 * @see TrayIcon
 *
 * @author Bino George
 * @author Denis Mikhalkin
 * @author Sharon Zakhour
 * @author Anton Tarasov
 */
public class SystemTray {
    private static SystemTray systemTray;
    private int currentIconID = 0; // each TrayIcon added gets a unique ID

    transient private SystemTrayPeer peer;

    private static final TrayIcon[] EMPTY_TRAY_ARRAY = new TrayIcon[0];

    static {
        AWTAccessor.setSystemTrayAccessor(
            new AWTAccessor.SystemTrayAccessor() {
                public void firePropertyChange(SystemTray tray,
                                               String propertyName,
                                               Object oldValue,
                                               Object newValue) {
                    tray.firePropertyChange(propertyName, oldValue, newValue);
                }
            });
    }

    /**
     * Private <code>SystemTray</code> constructor.
     *
     * <p>
     * 私有<code> SystemTray </code>构造函数
     * 
     */
    private SystemTray() {
        addNotify();
    }

    /**
     * Gets the <code>SystemTray</code> instance that represents the
     * desktop's tray area.  This always returns the same instance per
     * application.  On some platforms the system tray may not be
     * supported.  You may use the {@link #isSupported} method to
     * check if the system tray is supported.
     *
     * <p>If a SecurityManager is installed, the AWTPermission
     * {@code accessSystemTray} must be granted in order to get the
     * {@code SystemTray} instance. Otherwise this method will throw a
     * SecurityException.
     *
     * <p>
     *  获取代表桌面托盘区域的<code> SystemTray </code>实例这总是返回每个应用程序相同的实例在某些平台上,可能不支持系统托盘您可以使用{@link #isSupported}方法检查系
     * 统托盘。
     * 
     *  <p>如果安装了SecurityManager,则必须授予AWTermission {@code accessSystemTray}以获取{@code SystemTray}实例。
     * 否则,此方法将抛出SecurityException异常。
     * 
     * 
     * @return the <code>SystemTray</code> instance that represents
     * the desktop's tray area
     * @throws UnsupportedOperationException if the system tray isn't
     * supported by the current platform
     * @throws HeadlessException if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>
     * @throws SecurityException if {@code accessSystemTray} permission
     * is not granted
     * @see #add(TrayIcon)
     * @see TrayIcon
     * @see #isSupported
     * @see SecurityManager#checkPermission
     * @see AWTPermission
     */
    public static SystemTray getSystemTray() {
        checkSystemTrayAllowed();
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }

        initializeSystemTrayIfNeeded();

        if (!isSupported()) {
            throw new UnsupportedOperationException(
                "The system tray is not supported on the current platform.");
        }

        return systemTray;
    }

    /**
     * Returns whether the system tray is supported on the current
     * platform.  In addition to displaying the tray icon, minimal
     * system tray support includes either a popup menu (see {@link
     * TrayIcon#setPopupMenu(PopupMenu)}) or an action event (see
     * {@link TrayIcon#addActionListener(ActionListener)}).
     *
     * <p>Developers should not assume that all of the system tray
     * functionality is supported.  To guarantee that the tray icon's
     * default action is always accessible, add the default action to
     * both the action listener and the popup menu.  See the {@link
     * SystemTray example} for an example of how to do this.
     *
     * <p><b>Note</b>: When implementing <code>SystemTray</code> and
     * <code>TrayIcon</code> it is <em>strongly recommended</em> that
     * you assign different gestures to the popup menu and an action
     * event.  Overloading a gesture for both purposes is confusing
     * and may prevent the user from accessing one or the other.
     *
     * <p>
     * 返回系统托盘在当前平台上是否受支持除了显示托盘图标,最小系统托盘支持包括弹出菜单(请参阅{@link TrayIcon#setPopupMenu(PopupMenu)})或操作事件(请参阅{@link TrayIcon#addActionListener(ActionListener)}
     * )。
     * 
     *  <p>开发人员不应假定所有系统托盘功能都受支持要确保托盘图标的默认操作始终可访问,请将默认操作添加到操作侦听器和弹出菜单中。参见{@link SystemTray示例}一个如何做到这一点的例子
     * 
     * <p> <b>注意</b>：在实施<code> SystemTray </code>和<code> TrayIcon </code>时,强烈建议您向弹出式窗口分配不同的手势菜单和动作事件为了这两个目的重
     * 载手势是混乱的,并且可能阻止用户访问一个或另一个。
     * 
     * 
     * @see #getSystemTray
     * @return <code>false</code> if no system tray access is supported; this
     * method returns <code>true</code> if the minimal system tray access is
     * supported but does not guarantee that all system tray
     * functionality is supported for the current platform
     */
    public static boolean isSupported() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (toolkit instanceof SunToolkit) {
            // connecting tray to native resource
            initializeSystemTrayIfNeeded();
            return ((SunToolkit)toolkit).isTraySupported();
        } else if (toolkit instanceof HeadlessToolkit) {
            // skip initialization as the init routine
            // throws HeadlessException
            return ((HeadlessToolkit)toolkit).isTraySupported();
        } else {
            return false;
        }
    }

    /**
     * Adds a <code>TrayIcon</code> to the <code>SystemTray</code>.
     * The tray icon becomes visible in the system tray once it is
     * added.  The order in which icons are displayed in a tray is not
     * specified - it is platform and implementation-dependent.
     *
     * <p> All icons added by the application are automatically
     * removed from the <code>SystemTray</code> upon application exit
     * and also when the desktop system tray becomes unavailable.
     *
     * <p>
     *  将<code> TrayIcon </code>添加到<code> SystemTray </code>托盘图标在添加后会显示在系统托盘中托盘中显示图标的顺序未指定 - 和实现相关
     * 
     *  <p>应用程序退出时,以及桌面系统托盘不可用时,应用程序添加的所有图标都会自动从<code> SystemTray </code>中删除
     * 
     * 
     * @param trayIcon the <code>TrayIcon</code> to be added
     * @throws NullPointerException if <code>trayIcon</code> is
     * <code>null</code>
     * @throws IllegalArgumentException if the same instance of
     * a <code>TrayIcon</code> is added more than once
     * @throws AWTException if the desktop system tray is missing
     * @see #remove(TrayIcon)
     * @see #getSystemTray
     * @see TrayIcon
     * @see java.awt.Image
     */
    public void add(TrayIcon trayIcon) throws AWTException {
        if (trayIcon == null) {
            throw new NullPointerException("adding null TrayIcon");
        }
        TrayIcon[] oldArray = null, newArray = null;
        Vector<TrayIcon> icons = null;
        synchronized (this) {
            oldArray = systemTray.getTrayIcons();
            icons = (Vector<TrayIcon>)AppContext.getAppContext().get(TrayIcon.class);
            if (icons == null) {
                icons = new Vector<TrayIcon>(3);
                AppContext.getAppContext().put(TrayIcon.class, icons);

            } else if (icons.contains(trayIcon)) {
                throw new IllegalArgumentException("adding TrayIcon that is already added");
            }
            icons.add(trayIcon);
            newArray = systemTray.getTrayIcons();

            trayIcon.setID(++currentIconID);
        }
        try {
            trayIcon.addNotify();
        } catch (AWTException e) {
            icons.remove(trayIcon);
            throw e;
        }
        firePropertyChange("trayIcons", oldArray, newArray);
    }

    /**
     * Removes the specified <code>TrayIcon</code> from the
     * <code>SystemTray</code>.
     *
     * <p> All icons added by the application are automatically
     * removed from the <code>SystemTray</code> upon application exit
     * and also when the desktop system tray becomes unavailable.
     *
     * <p> If <code>trayIcon</code> is <code>null</code> or was not
     * added to the system tray, no exception is thrown and no action
     * is performed.
     *
     * <p>
     * 从<code> SystemTray </code>中删除指定的<code> TrayIcon </code>
     * 
     *  <p>应用程序退出时,以及桌面系统托盘不可用时,应用程序添加的所有图标都会自动从<code> SystemTray </code>中删除
     * 
     *  <p>如果<code> trayIcon </code>为<code> null </code>或未添加到系统托盘,则不会抛出任何异常,并且不执行任何操作
     * 
     * 
     * @param trayIcon the <code>TrayIcon</code> to be removed
     * @see #add(TrayIcon)
     * @see TrayIcon
     */
    public void remove(TrayIcon trayIcon) {
        if (trayIcon == null) {
            return;
        }
        TrayIcon[] oldArray = null, newArray = null;
        synchronized (this) {
            oldArray = systemTray.getTrayIcons();
            Vector<TrayIcon> icons = (Vector<TrayIcon>)AppContext.getAppContext().get(TrayIcon.class);
            // TrayIcon with no peer is not contained in the array.
            if (icons == null || !icons.remove(trayIcon)) {
                return;
            }
            trayIcon.removeNotify();
            newArray = systemTray.getTrayIcons();
        }
        firePropertyChange("trayIcons", oldArray, newArray);
    }

    /**
     * Returns an array of all icons added to the tray by this
     * application.  You can't access the icons added by another
     * application.  Some browsers partition applets in different
     * code bases into separate contexts, and establish walls between
     * these contexts.  In such a scenario, only the tray icons added
     * from this context will be returned.
     *
     * <p> The returned array is a copy of the actual array and may be
     * modified in any way without affecting the system tray.  To
     * remove a <code>TrayIcon</code> from the
     * <code>SystemTray</code>, use the {@link
     * #remove(TrayIcon)} method.
     *
     * <p>
     *  返回此应用程序添加到托盘中的所有图标的数组您无法访问由其他应用程序添加的图标某些浏览器将不同代码库中的applet分割为单独的上下文,并在这些上下文之间建立墙壁在这种情况下,只有托盘将返回从此上下文添
     * 加的图标。
     * 
     * <p>返回的数组是实际数组的副本,可以以任何方式修改而不影响系统托盘要从<code> SystemTray </code>中删除<code> TrayIcon </code> @link #remove
     * (TrayIcon)}方法。
     * 
     * 
     * @return an array of all tray icons added to this tray, or an
     * empty array if none has been added
     * @see #add(TrayIcon)
     * @see TrayIcon
     */
    public TrayIcon[] getTrayIcons() {
        Vector<TrayIcon> icons = (Vector<TrayIcon>)AppContext.getAppContext().get(TrayIcon.class);
        if (icons != null) {
            return (TrayIcon[])icons.toArray(new TrayIcon[icons.size()]);
        }
        return EMPTY_TRAY_ARRAY;
    }

    /**
     * Returns the size, in pixels, of the space that a tray icon will
     * occupy in the system tray.  Developers may use this methods to
     * acquire the preferred size for the image property of a tray icon
     * before it is created.  For convenience, there is a similar
     * method {@link TrayIcon#getSize} in the <code>TrayIcon</code> class.
     *
     * <p>
     *  返回托盘图标在系统托盘中占据的空间大小(以像素为单位)开发人员可以使用此方法在创建托盘图标之前获取托盘图标的image属性的首选大小为方便起见,还有一个类似的方法{@link TrayIcon#getSize}
     * 在<code> TrayIcon </code>类中。
     * 
     * 
     * @return the default size of a tray icon, in pixels
     * @see TrayIcon#setImageAutoSize(boolean)
     * @see java.awt.Image
     * @see TrayIcon#getSize()
     */
    public Dimension getTrayIconSize() {
        return peer.getTrayIconSize();
    }

    /**
     * Adds a {@code PropertyChangeListener} to the list of listeners for the
     * specific property. The following properties are currently supported:
     *
     * <table border=1 summary="SystemTray properties">
     * <tr>
     *    <th>Property</th>
     *    <th>Description</th>
     * </tr>
     * <tr>
     *    <td>{@code trayIcons}</td>
     *    <td>The {@code SystemTray}'s array of {@code TrayIcon} objects.
     *        The array is accessed via the {@link #getTrayIcons} method.<br>
     *        This property is changed when a tray icon is added to (or removed
     *        from) the system tray.<br> For example, this property is changed
     *        when the system tray becomes unavailable on the desktop<br>
     *        and the tray icons are automatically removed.</td>
     * </tr>
     * <tr>
     *    <td>{@code systemTray}</td>
     *    <td>This property contains {@code SystemTray} instance when the system tray
     *        is available or <code>null</code> otherwise.<br> This property is changed
     *        when the system tray becomes available or unavailable on the desktop.<br>
     *        The property is accessed by the {@link #getSystemTray} method.</td>
     * </tr>
     * </table>
     * <p>
     * The {@code listener} listens to property changes only in this context.
     * <p>
     * If {@code listener} is {@code null}, no exception is thrown
     * and no action is performed.
     *
     * <p>
     *  将{@code PropertyChangeListener}添加到特定属性的侦听器列表当前支持以下属性：
     * 
     * <table border=1 summary="SystemTray properties">
     * <tr>
     *  <th>属性</th> <th>描述</th>
     * </tr>
     * <tr>
     * <td> {@ code trayIcons} </td> <td> {@code SystemTray}的{@code TrayIcon}对象数组通过{@link #getTrayIcons}方法访问
     * 数组<br>此属性托盘图标添加到系统托盘(或从系统托盘中删除)时更改<br>例如,如果系统托盘在桌面上不可用,则会更改此属性<br>并自动删除托盘图标</td>。
     * </tr>
     * <tr>
     *  <td> {@ code systemTray} </td> <td>当系统托盘可用或<code> null </code>时,此属性包含{@code SystemTray}实例<br>此属性在系统托
     * 盘在桌面上可用或不可用<br>此属性由{@link #getSystemTray}方法访问</td>。
     * </tr>
     * </table>
     * <p>
     *  {@code listener}仅在此上下文中侦听属性更改
     * <p>
     * 如果{@code listener}是{@code null},则不会抛出任何异常,并且不会执行任何操作
     * 
     * @param propertyName the specified property
     * @param listener the property change listener to be added
     *
     * @see #removePropertyChangeListener
     * @see #getPropertyChangeListeners
     */
    public synchronized void addPropertyChangeListener(String propertyName,
                                                       PropertyChangeListener listener)
    {
        if (listener == null) {
            return;
        }
        getCurrentChangeSupport().addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Removes a {@code PropertyChangeListener} from the listener list
     * for a specific property.
     * <p>
     * The {@code PropertyChangeListener} must be from this context.
     * <p>
     * If {@code propertyName} or {@code listener} is {@code null} or invalid,
     * no exception is thrown and no action is taken.
     *
     * <p>
     * 
     * 
     * @param propertyName the specified property
     * @param listener the PropertyChangeListener to be removed
     *
     * @see #addPropertyChangeListener
     * @see #getPropertyChangeListeners
     */
    public synchronized void removePropertyChangeListener(String propertyName,
                                                          PropertyChangeListener listener)
    {
        if (listener == null) {
            return;
        }
        getCurrentChangeSupport().removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Returns an array of all the listeners that have been associated
     * with the named property.
     * <p>
     * Only the listeners in this context are returned.
     *
     * <p>
     *  从特定属性的侦听器列表中删除{@code PropertyChangeListener}
     * <p>
     *  {@code PropertyChangeListener}必须来自此上下文
     * <p>
     *  如果{@code propertyName}或{@code listener}为{@code null}或无效,则不会抛出任何异常,并且不会执行任何操作
     * 
     * 
     * @param propertyName the specified property
     * @return all of the {@code PropertyChangeListener}s associated with
     *         the named property; if no such listeners have been added or
     *         if {@code propertyName} is {@code null} or invalid, an empty
     *         array is returned
     *
     * @see #addPropertyChangeListener
     * @see #removePropertyChangeListener
     */
    public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return getCurrentChangeSupport().getPropertyChangeListeners(propertyName);
    }


    // ***************************************************************
    // ***************************************************************


    /**
     * Support for reporting bound property changes for Object properties.
     * This method can be called when a bound property has changed and it will
     * send the appropriate PropertyChangeEvent to any registered
     * PropertyChangeListeners.
     *
     * <p>
     *  返回与命名属性关联的所有侦听器的数组
     * <p>
     *  仅返回此上下文中的侦听器
     * 
     * 
     * @param propertyName the property whose value has changed
     * @param oldValue the property's previous value
     * @param newValue the property's new value
     */
    private void firePropertyChange(String propertyName,
                                    Object oldValue, Object newValue)
    {
        if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
            return;
        }
        getCurrentChangeSupport().firePropertyChange(propertyName, oldValue, newValue);
    }

    /**
     * Returns the current PropertyChangeSupport instance for the
     * calling thread's context.
     *
     * <p>
     *  支持为对象属性报告绑定的属性更改当绑定属性更改时,可以调用此方法,并且它将向任何已注册的PropertyChangeListeners发送适当的PropertyChangeEvent
     * 
     * 
     * @return this thread's context's PropertyChangeSupport
     */
    private synchronized PropertyChangeSupport getCurrentChangeSupport() {
        PropertyChangeSupport changeSupport =
            (PropertyChangeSupport)AppContext.getAppContext().get(SystemTray.class);

        if (changeSupport == null) {
            changeSupport = new PropertyChangeSupport(this);
            AppContext.getAppContext().put(SystemTray.class, changeSupport);
        }
        return changeSupport;
    }

    synchronized void addNotify() {
        if (peer == null) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            if (toolkit instanceof SunToolkit) {
                peer = ((SunToolkit)Toolkit.getDefaultToolkit()).createSystemTray(this);
            } else if (toolkit instanceof HeadlessToolkit) {
                peer = ((HeadlessToolkit)Toolkit.getDefaultToolkit()).createSystemTray(this);
            }
        }
    }

    static void checkSystemTrayAllowed() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.ACCESS_SYSTEM_TRAY_PERMISSION);
        }
    }

    private static void initializeSystemTrayIfNeeded() {
        synchronized (SystemTray.class) {
            if (systemTray == null) {
                systemTray = new SystemTray();
            }
        }
    }
}
