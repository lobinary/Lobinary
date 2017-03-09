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

package java.awt;

import java.security.BasicPermission;

/**
 * This class is for AWT permissions.
 * An <code>AWTPermission</code> contains a target name but
 * no actions list; you either have the named permission
 * or you don't.
 *
 * <P>
 * The target name is the name of the AWT permission (see below). The naming
 * convention follows the hierarchical property naming convention.
 * Also, an asterisk could be used to represent all AWT permissions.
 *
 * <P>
 * The following table lists all the possible <code>AWTPermission</code>
 * target names, and for each provides a description of what the
 * permission allows and a discussion of the risks of granting code
 * the permission.
 *
 * <table border=1 cellpadding=5 summary="AWTPermission target names, descriptions, and associated risks.">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>accessClipboard</td>
 *   <td>Posting and retrieval of information to and from the AWT clipboard</td>
 *   <td>This would allow malfeasant code to share
 * potentially sensitive or confidential information.</td>
 * </tr>
 *
 * <tr>
 *   <td>accessEventQueue</td>
 *   <td>Access to the AWT event queue</td>
 *   <td>After retrieving the AWT event queue,
 * malicious code may peek at and even remove existing events
 * from its event queue, as well as post bogus events which may purposefully
 * cause the application or applet to misbehave in an insecure manner.</td>
 * </tr>
 *
 * <tr>
 *   <td>accessSystemTray</td>
 *   <td>Access to the AWT SystemTray instance</td>
 *   <td>This would allow malicious code to add tray icons to the system tray.
 * First, such an icon may look like the icon of some known application
 * (such as a firewall or anti-virus) and order a user to do something unsafe
 * (with help of balloon messages). Second, the system tray may be glutted with
 * tray icons so that no one could add a tray icon anymore.</td>
 * </tr>
 *
 * <tr>
 *   <td>createRobot</td>
 *   <td>Create java.awt.Robot objects</td>
 *   <td>The java.awt.Robot object allows code to generate native-level
 * mouse and keyboard events as well as read the screen. It could allow
 * malicious code to control the system, run other programs, read the
 * display, and deny mouse and keyboard access to the user.</td>
 * </tr>
 *
 * <tr>
 *   <td>fullScreenExclusive</td>
 *   <td>Enter full-screen exclusive mode</td>
 *   <td>Entering full-screen exclusive mode allows direct access to
 * low-level graphics card memory.  This could be used to spoof the
 * system, since the program is in direct control of rendering. Depending on
 * the implementation, the security warning may not be shown for the windows
 * used to enter the full-screen exclusive mode (assuming that the {@code
 * fullScreenExclusive} permission has been granted to this application). Note
 * that this behavior does not mean that the {@code
 * showWindowWithoutWarningBanner} permission will be automatically granted to
 * the application which has the {@code fullScreenExclusive} permission:
 * non-full-screen windows will continue to be shown with the security
 * warning.</td>
 * </tr>
 *
 * <tr>
 *   <td>listenToAllAWTEvents</td>
 *   <td>Listen to all AWT events, system-wide</td>
 *   <td>After adding an AWT event listener,
 * malicious code may scan all AWT events dispatched in the system,
 * allowing it to read all user input (such as passwords).  Each
 * AWT event listener is called from within the context of that
 * event queue's EventDispatchThread, so if the accessEventQueue
 * permission is also enabled, malicious code could modify the
 * contents of AWT event queues system-wide, causing the application
 * or applet to misbehave in an insecure manner.</td>
 * </tr>
 *
 * <tr>
 *   <td>readDisplayPixels</td>
 *   <td>Readback of pixels from the display screen</td>
 *   <td>Interfaces such as the java.awt.Composite interface or the
 * java.awt.Robot class allow arbitrary code to examine pixels on the
 * display enable malicious code to snoop on the activities of the user.</td>
 * </tr>
 *
 * <tr>
 *   <td>replaceKeyboardFocusManager</td>
 *   <td>Sets the <code>KeyboardFocusManager</code> for
 *       a particular thread.
 *   <td>When <code>SecurityManager</code> is installed, the invoking
 *       thread must be granted this permission in order to replace
 *       the current <code>KeyboardFocusManager</code>.  If permission
 *       is not granted, a <code>SecurityException</code> will be thrown.
 * </tr>
 *
 * <tr>
 *   <td>setAppletStub</td>
 *   <td>Setting the stub which implements Applet container services</td>
 *   <td>Malicious code could set an applet's stub and result in unexpected
 * behavior or denial of service to an applet.</td>
 * </tr>
 *
 * <tr>
 *   <td>setWindowAlwaysOnTop</td>
 *   <td>Setting always-on-top property of the window: {@link Window#setAlwaysOnTop}</td>
 *   <td>The malicious window might make itself look and behave like a real full desktop, so that
 * information entered by the unsuspecting user is captured and subsequently misused </td>
 * </tr>
 *
 * <tr>
 *   <td>showWindowWithoutWarningBanner</td>
 *   <td>Display of a window without also displaying a banner warning
 * that the window was created by an applet</td>
 *   <td>Without this warning,
 * an applet may pop up windows without the user knowing that they
 * belong to an applet.  Since users may make security-sensitive
 * decisions based on whether or not the window belongs to an applet
 * (entering a username and password into a dialog box, for example),
 * disabling this warning banner may allow applets to trick the user
 * into entering such information.</td>
 * </tr>
 *
 * <tr>
 *   <td>toolkitModality</td>
 *   <td>Creating {@link Dialog.ModalityType#TOOLKIT_MODAL TOOLKIT_MODAL} dialogs
 *       and setting the {@link Dialog.ModalExclusionType#TOOLKIT_EXCLUDE
 *       TOOLKIT_EXCLUDE} window property.</td>
 *   <td>When a toolkit-modal dialog is shown from an applet, it blocks all other
 * applets in the browser. When launching applications from Java Web Start,
 * its windows (such as the security dialog) may also be blocked by toolkit-modal
 * dialogs, shown from these applications.</td>
 * </tr>
 *
 * <tr>
 *   <td>watchMousePointer</td>
 *   <td>Getting the information about the mouse pointer position at any
 * time</td>
 *   <td>Constantly watching the mouse pointer,
 * an applet can make guesses about what the user is doing, i.e. moving
 * the mouse to the lower left corner of the screen most likely means that
 * the user is about to launch an application. If a virtual keypad is used
 * so that keyboard is emulated using the mouse, an applet may guess what
 * is being typed.</td>
 * </tr>
 * </table>
 *
 * <p>
 *  此类是用于AWT权限。 <code> AWTPermission </code>包含目标名称,但没有操作列表;你有命名权限或者你没有。
 * 
 * <P>
 *  目标名称是AWT权限的名称(见下文)。命名约定遵循分层属性命名约定。此外,星号可用于表示所有AWT权限。
 * 
 * <P>
 *  下表列出了所有可能的<code> AWTPermission </code>目标名称,并为每个提供了权限允许的描述以及授予代码权限的风险的讨论。
 * 
 * <table border=1 cellpadding=5 summary="AWTPermission target names, descriptions, and associated risks.">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> accessClipboard </td> <td>在AWT剪贴簿中发布和检索信息</td> <td>这会允许不良代码共享可能敏感或机密的信息。</td>
 * </tr>
 * 
 * <tr>
 *  <td> accessEventQueue </td> <td>访问AWT事件队列</td> <td>检索AWT事件队列后,恶意代码可能会窥探甚至从其事件队列中删除现有事件,以及发布伪造的事件,可能故
 * 意导致应用程序或小程序以不安全的方式行为不端。
 * </td>。
 * </tr>
 * 
 * <tr>
 * <td> accessSystemTray </td> <td>访问AWT SystemTray实例</td> <td>这将允许恶意代码将托盘图标添加到系统托盘。
 * 首先,这样的图标可能看起来像一些已知应用程序的图标(例如防火墙或防病毒),并命令用户做不安全的事情(借助气球消息)。第二,系统托盘可能会填充托盘图标,以便没有人可以再添加托盘图标。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> createRobot </td> <td>创建java.awt.Robot对象</td> <td> java.awt.Robot对象允许代码生成本机级鼠标和键盘事件以及读取屏幕。
 * 它可能允许恶意代码控制系统,运行其他程序,读取显示,并拒绝鼠标和键盘访问用户。</td>。
 * </tr>
 * 
 * <tr>
 * <td> fullScreenExclusive </td> <td>进入全屏独占模式</td> <td>进入全屏独占模式可以直接访问低级显卡内存。这可以用于欺骗系统,因为程序直接控制着渲染。
 * 根据实施情况,可能不会为用于进入全屏独占模式的窗口显示安全警告(假设已授予此应用程序的{@code fullScreenExclusive}权限)。
 * 请注意,此行为并不意味着{@code showWindowWithoutWarningBanner}权限将自动授予具有{@code fullScreenExclusive}权限的应用程序：非全屏窗口将继
 * 续显示安全警告。
 * 根据实施情况,可能不会为用于进入全屏独占模式的窗口显示安全警告(假设已授予此应用程序的{@code fullScreenExclusive}权限)。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> listenToAllAWTEvents </td> <td>在系统范围内侦听所有AWT事件</td> <td>在添加AWT事件侦听器之后,恶意代码可能会扫描系统中分派的所有AWT事件,所有
 * 用户输入(如密码)。
 * 每个AWT事件侦听器从该事件队列的EventDispatchThread的上下文中调用,因此如果accessEventQueue权限也被启用,恶意代码可以修改系统范围内的AWT事件队列的内容,导致应用程
 * 序或小程序以不安全的方式。
 * </td>。
 * </tr>
 * 
 * 
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 *
 * @author Marianne Mueller
 * @author Roland Schemers
 */

public final class AWTPermission extends BasicPermission {

    /** use serialVersionUID from the Java 2 platform for interoperability */
    private static final long serialVersionUID = 8890392402588814465L;

    /**
     * Creates a new <code>AWTPermission</code> with the specified name.
     * The name is the symbolic name of the <code>AWTPermission</code>,
     * such as "topLevelWindow", "systemClipboard", etc. An asterisk
     * may be used to indicate all AWT permissions.
     *
     * <p>
     * <tr>
     * <td> readDisplayPixels </td> <td>从显示屏回读像素</td> <td>接口(如java.awt.Composite接口或java.awt.Robot类)允许任意代码检查像
     * 素该显示使得恶意代码能够窥探用户的活动。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> replaceKeyboardFocusManager </td> <td>为特定线程设置<code> KeyboardFocusManager </code>。
     *  <td>安装<code> SecurityManager </code>时,调用线程必须授予此权限才能替换当前的<code> KeyboardFocusManager </code>。
     * 如果未授予权限,将抛出<code> SecurityException </code>。
     * </tr>
     * 
     * <tr>
     *  <td> setAppletStub </td> <td>设置实现Applet容器服务的存根</td> <td>恶意代码可能会设置applet的存根并导致意外行为或拒绝服务到小程序。</td>
     * </tr>
     * 
     * <tr>
     *  <td> setWindowAlwaysOnTop </td> <td>设置窗口的always-on-top属性：{@link Window#setAlwaysOnTop} </td> <td>恶意窗
     * 口可能使自身看起来像一个真正的完整桌面,使得由未被怀疑的用户输入的信息被捕获并随后被滥用</td>。
     * </tr>
     * 
     * <tr>
     * <td> showWindowWithoutWarningBanner </td> <td>显示窗口而不显示一个横幅警告窗口是由小程序创建的</td> <td>没有此警告,小程序可能会弹出窗口,他们属于
     * 一个小程序。
     * 由于用户可以基于窗口是否属于小应用程序(例如,在对话框中输入用户名和密码)来做出安全敏感的决定,所以禁用该警告横幅可以允许小程序欺骗用户输入这样的信息。 </td>。
     * 
     * @param name the name of the AWTPermission
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public AWTPermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new <code>AWTPermission</code> object with the specified name.
     * The name is the symbolic name of the <code>AWTPermission</code>, and the
     * actions string is currently unused and should be <code>null</code>.
     *
     * <p>
     * </tr>
     * 
     * <tr>
     *  <td> toolkitModality </td> <td>创建{@link Dialog.ModalityType#TOOLKIT_MODAL TOOLKIT_MODAL}对话框并设置{@link Dialog.ModalExclusionType#TOOLKIT_EXCLUDE TOOLKIT_EXCLUDE}
     * 窗口属性。
     * </td> <td>模态对话框从一个小程序显示,它阻止浏览器中的所有其他小程序。当从Java Web Start启动应用程序时,其窗口(例如安全对话框)也可能被这些应用程序显示的工具箱模式对话框阻止。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> watchMousePointer </td> <td>随时获取有关鼠标指针位置的信息</td> <td>一个小程序不断地观察鼠标指针,可以猜测用户正在做什么,即移动鼠标到屏幕的左下角最可能
     * 意味着用户即将启动一个应用程序。
     * 
     * @param name the name of the <code>AWTPermission</code>
     * @param actions should be <code>null</code>
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public AWTPermission(String name, String actions)
    {
        super(name, actions);
    }
}
