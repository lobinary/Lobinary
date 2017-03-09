/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.AWTPermission;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.peer.DesktopPeer;
import sun.awt.SunToolkit;
import sun.awt.HeadlessToolkit;
import java.io.FilePermission;
import sun.security.util.SecurityConstants;

/**
 * The {@code Desktop} class allows a Java application to launch
 * associated applications registered on the native desktop to handle
 * a {@link java.net.URI} or a file.
 *
 * <p> Supported operations include:
 * <ul>
 *   <li>launching the user-default browser to show a specified
 *       URI;</li>
 *   <li>launching the user-default mail client with an optional
 *       {@code mailto} URI;</li>
 *   <li>launching a registered application to open, edit or print a
 *       specified file.</li>
 * </ul>
 *
 * <p> This class provides methods corresponding to these
 * operations. The methods look for the associated application
 * registered on the current platform, and launch it to handle a URI
 * or file. If there is no associated application or the associated
 * application fails to be launched, an exception is thrown.
 *
 * <p> An application is registered to a URI or file type; for
 * example, the {@code "sxi"} file extension is typically registered
 * to StarOffice.  The mechanism of registering, accessing, and
 * launching the associated application is platform-dependent.
 *
 * <p> Each operation is an action type represented by the {@link
 * Desktop.Action} class.
 *
 * <p> Note: when some action is invoked and the associated
 * application is executed, it will be executed on the same system as
 * the one on which the Java application was launched.
 *
 * <p>
 *  {@code Desktop}类允许Java应用程序启动在本地桌面上注册的关联应用程序来处理{@link java.net.URI}或文件。
 * 
 *  <p>支持的操作包括：
 * <ul>
 *  <li>启动用户默认浏览器以显示指定的URI; </li> <li>使用可选的{@code mailto} URI启动用户默认邮件客户端; </li> <li>启动已注册的应用程序以打开,编辑或打印指
 * 定的文件。
 * </li>。
 * </ul>
 * 
 *  <p>此类提供了与这些操作相对应的方法。这些方法查找在当前平台上注册的关联应用程序,并启动它来处理URI或文件。如果没有关联的应用程序或相关应用程序无法启动,则抛出异常。
 * 
 *  <p>应用程序已注册到URI或文件类型;例如,{@code"sxi"}文件扩展名通常注册到StarSuite。注册,访问和启动相关联的应用程序的机制是平台相关的。
 * 
 *  <p>每个操作都是由{@link Desktop.Action}类表示的操作类型。
 * 
 *  <p>注意：当一些操作被调用并且关联的应用程序被执行时,它将在与启动Java应用程序的系统相同的系统上执行。
 * 
 * 
 * @since 1.6
 * @author Armin Chen
 * @author George Zhang
 */
public class Desktop {

    /**
     * Represents an action type.  Each platform supports a different
     * set of actions.  You may use the {@link Desktop#isSupported}
     * method to determine if the given action is supported by the
     * current platform.
     * <p>
     * 表示操作类型。每个平台支持一组不同的操作。您可以使用{@link Desktop#isSupported}方法来确定当前平台是否支持给定的操作。
     * 
     * 
     * @see java.awt.Desktop#isSupported(java.awt.Desktop.Action)
     * @since 1.6
     */
    public static enum Action {
        /**
         * Represents an "open" action.
         * <p>
         *  表示"打开"操作。
         * 
         * 
         * @see Desktop#open(java.io.File)
         */
        OPEN,
        /**
         * Represents an "edit" action.
         * <p>
         *  表示"编辑"操作。
         * 
         * 
         * @see Desktop#edit(java.io.File)
         */
        EDIT,
        /**
         * Represents a "print" action.
         * <p>
         *  表示"打印"操作。
         * 
         * 
         * @see Desktop#print(java.io.File)
         */
        PRINT,
        /**
         * Represents a "mail" action.
         * <p>
         *  表示"邮件"操作。
         * 
         * 
         * @see Desktop#mail()
         * @see Desktop#mail(java.net.URI)
         */
        MAIL,
        /**
         * Represents a "browse" action.
         * <p>
         *  表示"浏览"操作。
         * 
         * 
         * @see Desktop#browse(java.net.URI)
         */
        BROWSE
    };

    private DesktopPeer peer;

    /**
     * Suppresses default constructor for noninstantiability.
     * <p>
     *  抑制默认构造函数的非不可置性。
     * 
     */
    private Desktop() {
        peer = Toolkit.getDefaultToolkit().createDesktopPeer(this);
    }

    /**
     * Returns the <code>Desktop</code> instance of the current
     * browser context.  On some platforms the Desktop API may not be
     * supported; use the {@link #isDesktopSupported} method to
     * determine if the current desktop is supported.
     * <p>
     *  返回当前浏览器上下文的<code> Desktop </code>实例。
     * 在某些平台上,可能不支持Desktop API;请使用{@link #isDesktopSupported}方法确定是否支持当前桌面。
     * 
     * 
     * @return the Desktop instance of the current browser context
     * @throws HeadlessException if {@link
     * GraphicsEnvironment#isHeadless()} returns {@code true}
     * @throws UnsupportedOperationException if this class is not
     * supported on the current platform
     * @see #isDesktopSupported()
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static synchronized Desktop getDesktop(){
        if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();
        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop API is not " +
                                                    "supported on the current platform");
        }

        sun.awt.AppContext context = sun.awt.AppContext.getAppContext();
        Desktop desktop = (Desktop)context.get(Desktop.class);

        if (desktop == null) {
            desktop = new Desktop();
            context.put(Desktop.class, desktop);
        }

        return desktop;
    }

    /**
     * Tests whether this class is supported on the current platform.
     * If it's supported, use {@link #getDesktop()} to retrieve an
     * instance.
     *
     * <p>
     *  测试当前平台是否支持此类。如果支持,请使用{@link #getDesktop()}检索实例。
     * 
     * 
     * @return <code>true</code> if this class is supported on the
     *         current platform; <code>false</code> otherwise
     * @see #getDesktop()
     */
    public static boolean isDesktopSupported(){
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        if (defaultToolkit instanceof SunToolkit) {
            return ((SunToolkit)defaultToolkit).isDesktopSupported();
        }
        return false;
    }

    /**
     * Tests whether an action is supported on the current platform.
     *
     * <p>Even when the platform supports an action, a file or URI may
     * not have a registered application for the action.  For example,
     * most of the platforms support the {@link Desktop.Action#OPEN}
     * action.  But for a specific file, there may not be an
     * application registered to open it.  In this case, {@link
     * #isSupported} may return {@code true}, but the corresponding
     * action method will throw an {@link IOException}.
     *
     * <p>
     *  测试当前平台上是否支持某个操作。
     * 
     *  <p>即使平台支持某个操作,文件或URI也可能没有注册的操作应用程序。例如,大多数平台都支持{@link Desktop.Action#OPEN}操作。
     * 但对于一个特定的文件,可能没有一个注册的应用程序打开它。
     * 在这种情况下,{@link #isSupported}可能会返回{@code true},但相应的操作方法将抛出{@link IOException}。
     * 
     * 
     * @param action the specified {@link Action}
     * @return <code>true</code> if the specified action is supported on
     *         the current platform; <code>false</code> otherwise
     * @see Desktop.Action
     */
    public boolean isSupported(Action action) {
        return peer.isSupported(action);
    }

    /**
     * Checks if the file is a valid file and readable.
     *
     * <p>
     *  检查文件是否是有效的文件并且可读。
     * 
     * 
     * @throws SecurityException If a security manager exists and its
     *         {@link SecurityManager#checkRead(java.lang.String)} method
     *         denies read access to the file
     * @throws NullPointerException if file is null
     * @throws IllegalArgumentException if file doesn't exist
     */
    private static void checkFileValidation(File file){
        if (file == null) throw new NullPointerException("File must not be null");

        if (!file.exists()) {
            throw new IllegalArgumentException("The file: "
                                               + file.getPath() + " doesn't exist.");
        }

        file.canRead();
    }

    /**
     * Checks if the action type is supported.
     *
     * <p>
     *  检查操作类型是否受支持。
     * 
     * 
     * @param actionType the action type in question
     * @throws UnsupportedOperationException if the specified action type is not
     *         supported on the current platform
     */
    private void checkActionSupport(Action actionType){
        if (!isSupported(actionType)) {
            throw new UnsupportedOperationException("The " + actionType.name()
                                                    + " action is not supported on the current platform!");
        }
    }


    /**
     *  Calls to the security manager's <code>checkPermission</code> method with
     *  an <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     *  permission.
     * <p>
     *  使用<code> AWTPermission("showWindowWithoutWarningBanner")</code>权限调用安全管理器的<code> checkPermission </code>
     * 方法。
     * 
     */
    private void checkAWTPermission(){
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new AWTPermission(
                                   "showWindowWithoutWarningBanner"));
        }
    }

    /**
     * Launches the associated application to open the file.
     *
     * <p> If the specified file is a directory, the file manager of
     * the current platform is launched to open it.
     *
     * <p>
     * 启动相关应用程序以打开文件。
     * 
     *  <p>如果指定的文件是目录,则启动当前平台的文件管理器以将其打开。
     * 
     * 
     * @param file the file to be opened with the associated application
     * @throws NullPointerException if {@code file} is {@code null}
     * @throws IllegalArgumentException if the specified file doesn't
     * exist
     * @throws UnsupportedOperationException if the current platform
     * does not support the {@link Desktop.Action#OPEN} action
     * @throws IOException if the specified file has no associated
     * application or the associated application fails to be launched
     * @throws SecurityException if a security manager exists and its
     * {@link java.lang.SecurityManager#checkRead(java.lang.String)}
     * method denies read access to the file, or it denies the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     * permission, or the calling thread is not allowed to create a
     * subprocess
     * @see java.awt.AWTPermission
     */
    public void open(File file) throws IOException {
        checkAWTPermission();
        checkExec();
        checkActionSupport(Action.OPEN);
        checkFileValidation(file);

        peer.open(file);
    }

    /**
     * Launches the associated editor application and opens a file for
     * editing.
     *
     * <p>
     *  启动关联的编辑器应用程序,并打开一个文件进行编辑。
     * 
     * 
     * @param file the file to be opened for editing
     * @throws NullPointerException if the specified file is {@code null}
     * @throws IllegalArgumentException if the specified file doesn't
     * exist
     * @throws UnsupportedOperationException if the current platform
     * does not support the {@link Desktop.Action#EDIT} action
     * @throws IOException if the specified file has no associated
     * editor, or the associated application fails to be launched
     * @throws SecurityException if a security manager exists and its
     * {@link java.lang.SecurityManager#checkRead(java.lang.String)}
     * method denies read access to the file, or {@link
     * java.lang.SecurityManager#checkWrite(java.lang.String)} method
     * denies write access to the file, or it denies the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     * permission, or the calling thread is not allowed to create a
     * subprocess
     * @see java.awt.AWTPermission
     */
    public void edit(File file) throws IOException {
        checkAWTPermission();
        checkExec();
        checkActionSupport(Action.EDIT);
        file.canWrite();
        checkFileValidation(file);

        peer.edit(file);
    }

    /**
     * Prints a file with the native desktop printing facility, using
     * the associated application's print command.
     *
     * <p>
     *  使用相关应用程序的打印命令使用本地桌面打印设备打印文件。
     * 
     * 
     * @param file the file to be printed
     * @throws NullPointerException if the specified file is {@code
     * null}
     * @throws IllegalArgumentException if the specified file doesn't
     * exist
     * @throws UnsupportedOperationException if the current platform
     *         does not support the {@link Desktop.Action#PRINT} action
     * @throws IOException if the specified file has no associated
     * application that can be used to print it
     * @throws SecurityException if a security manager exists and its
     * {@link java.lang.SecurityManager#checkRead(java.lang.String)}
     * method denies read access to the file, or its {@link
     * java.lang.SecurityManager#checkPrintJobAccess()} method denies
     * the permission to print the file, or the calling thread is not
     * allowed to create a subprocess
     */
    public void print(File file) throws IOException {
        checkExec();
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPrintJobAccess();
        }
        checkActionSupport(Action.PRINT);
        checkFileValidation(file);

        peer.print(file);
    }

    /**
     * Launches the default browser to display a {@code URI}.
     * If the default browser is not able to handle the specified
     * {@code URI}, the application registered for handling
     * {@code URIs} of the specified type is invoked. The application
     * is determined from the protocol and path of the {@code URI}, as
     * defined by the {@code URI} class.
     * <p>
     * If the calling thread does not have the necessary permissions,
     * and this is invoked from within an applet,
     * {@code AppletContext.showDocument()} is used. Similarly, if the calling
     * does not have the necessary permissions, and this is invoked from within
     * a Java Web Started application, {@code BasicService.showDocument()}
     * is used.
     *
     * <p>
     *  启动默认浏览器以显示{@code URI}。如果默认浏览器无法处理指定的{@code URI},则会调用为处理指定类型的{@code URIs}而注册的应用程序。
     * 应用程序根据{@code URI}类定义的{@code URI}的协议和路径确定。
     * <p>
     *  如果调用线程没有必要的权限,并且这是从一个applet中调用,则使用{@code AppletContext.showDocument()}。
     * 同样,如果调用没有必要的权限,并且这是从Java Web Started应用程序中调用,则使用{@code BasicService.showDocument()}。
     * 
     * 
     * @param uri the URI to be displayed in the user default browser
     * @throws NullPointerException if {@code uri} is {@code null}
     * @throws UnsupportedOperationException if the current platform
     * does not support the {@link Desktop.Action#BROWSE} action
     * @throws IOException if the user default browser is not found,
     * or it fails to be launched, or the default handler application
     * failed to be launched
     * @throws SecurityException if a security manager exists and it
     * denies the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     * permission, or the calling thread is not allowed to create a
     * subprocess; and not invoked from within an applet or Java Web Started
     * application
     * @throws IllegalArgumentException if the necessary permissions
     * are not available and the URI can not be converted to a {@code URL}
     * @see java.net.URI
     * @see java.awt.AWTPermission
     * @see java.applet.AppletContext
     */
    public void browse(URI uri) throws IOException {
        SecurityException securityException = null;
        try {
            checkAWTPermission();
            checkExec();
        } catch (SecurityException e) {
            securityException = e;
        }
        checkActionSupport(Action.BROWSE);
        if (uri == null) {
            throw new NullPointerException();
        }
        if (securityException == null) {
            peer.browse(uri);
            return;
        }

        // Calling thread doesn't have necessary priviledges.
        // Delegate to DesktopBrowse so that it can work in
        // applet/webstart.
        URL url = null;
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Unable to convert URI to URL", e);
        }
        sun.awt.DesktopBrowse db = sun.awt.DesktopBrowse.getInstance();
        if (db == null) {
            // Not in webstart/applet, throw the exception.
            throw securityException;
        }
        db.browse(url);
    }

    /**
     * Launches the mail composing window of the user default mail
     * client.
     *
     * <p>
     *  启动用户默认邮件客户端的邮件编写窗口。
     * 
     * 
     * @throws UnsupportedOperationException if the current platform
     * does not support the {@link Desktop.Action#MAIL} action
     * @throws IOException if the user default mail client is not
     * found, or it fails to be launched
     * @throws SecurityException if a security manager exists and it
     * denies the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     * permission, or the calling thread is not allowed to create a
     * subprocess
     * @see java.awt.AWTPermission
     */
    public void mail() throws IOException {
        checkAWTPermission();
        checkExec();
        checkActionSupport(Action.MAIL);
        URI mailtoURI = null;
        try{
            mailtoURI = new URI("mailto:?");
            peer.mail(mailtoURI);
        } catch (URISyntaxException e){
            // won't reach here.
        }
    }

    /**
     * Launches the mail composing window of the user default mail
     * client, filling the message fields specified by a {@code
     * mailto:} URI.
     *
     * <p> A <code>mailto:</code> URI can specify message fields
     * including <i>"to"</i>, <i>"cc"</i>, <i>"subject"</i>,
     * <i>"body"</i>, etc.  See <a
     * href="http://www.ietf.org/rfc/rfc2368.txt">The mailto URL
     * scheme (RFC 2368)</a> for the {@code mailto:} URI specification
     * details.
     *
     * <p>
     *  启动用户默认邮件客户端的邮件编写窗口,填写{@code mailto：} URI指定的邮件字段。
     * 
     *  <p> <code> mailto：</code> URI可以指定包含<i>"至"</i>,<i>"cc"</i>,<i> >,<i>"body"</i>等。
     * 请参阅<a href="http://www.ietf.org/rfc/rfc2368.txt"> mailto URL方案(RFC 2368)</a>用于{@code mailto：} URI规范详细
     * 
     * @param mailtoURI the specified {@code mailto:} URI
     * @throws NullPointerException if the specified URI is {@code
     * null}
     * @throws IllegalArgumentException if the URI scheme is not
     *         <code>"mailto"</code>
     * @throws UnsupportedOperationException if the current platform
     * does not support the {@link Desktop.Action#MAIL} action
     * @throws IOException if the user default mail client is not
     * found or fails to be launched
     * @throws SecurityException if a security manager exists and it
     * denies the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code>
     * permission, or the calling thread is not allowed to create a
     * subprocess
     * @see java.net.URI
     * @see java.awt.AWTPermission
     */
    public  void mail(URI mailtoURI) throws IOException {
        checkAWTPermission();
        checkExec();
        checkActionSupport(Action.MAIL);
        if (mailtoURI == null) throw new NullPointerException();

        if (!"mailto".equalsIgnoreCase(mailtoURI.getScheme())) {
            throw new IllegalArgumentException("URI scheme is not \"mailto\"");
        }

        peer.mail(mailtoURI);
    }

    private void checkExec() throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new FilePermission("<<ALL FILES>>",
                                                  SecurityConstants.FILE_EXECUTE_ACTION));
        }
    }
}
