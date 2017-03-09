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

package java.lang;

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 * This class is for runtime permissions. A RuntimePermission
 * contains a name (also referred to as a "target name") but
 * no actions list; you either have the named permission
 * or you don't.
 *
 * <P>
 * The target name is the name of the runtime permission (see below). The
 * naming convention follows the  hierarchical property naming convention.
 * Also, an asterisk
 * may appear at the end of the name, following a ".", or by itself, to
 * signify a wildcard match. For example: "loadLibrary.*" and "*" signify a
 * wildcard match, while "*loadLibrary" and "a*b" do not.
 * <P>
 * The following table lists all the possible RuntimePermission target names,
 * and for each provides a description of what the permission allows
 * and a discussion of the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5 summary="permission target name,
 *  what the target allows,and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>createClassLoader</td>
 *   <td>Creation of a class loader</td>
 *   <td>This is an extremely dangerous permission to grant.
 * Malicious applications that can instantiate their own class
 * loaders could then load their own rogue classes into the system.
 * These newly loaded classes could be placed into any protection
 * domain by the class loader, thereby automatically granting the
 * classes the permissions for that domain.</td>
 * </tr>
 *
 * <tr>
 *   <td>getClassLoader</td>
 *   <td>Retrieval of a class loader (e.g., the class loader for the calling
 * class)</td>
 *   <td>This would grant an attacker permission to get the
 * class loader for a particular class. This is dangerous because
 * having access to a class's class loader allows the attacker to
 * load other classes available to that class loader. The attacker
 * would typically otherwise not have access to those classes.</td>
 * </tr>
 *
 * <tr>
 *   <td>setContextClassLoader</td>
 *   <td>Setting of the context class loader used by a thread</td>
 *   <td>The context class loader is used by system code and extensions
 * when they need to lookup resources that might not exist in the system
 * class loader. Granting setContextClassLoader permission would allow
 * code to change which context class loader is used
 * for a particular thread, including system threads.</td>
 * </tr>
 *
 * <tr>
 *   <td>enableContextClassLoaderOverride</td>
 *   <td>Subclass implementation of the thread context class loader methods</td>
 *   <td>The context class loader is used by system code and extensions
 * when they need to lookup resources that might not exist in the system
 * class loader. Granting enableContextClassLoaderOverride permission would allow
 * a subclass of Thread to override the methods that are used
 * to get or set the context class loader for a particular thread.</td>
 * </tr>
 *
 * <tr>
 *   <td>closeClassLoader</td>
 *   <td>Closing of a ClassLoader</td>
 *   <td>Granting this permission allows code to close any URLClassLoader
 * that it has a reference to.</td>
 * </tr>
 *
 * <tr>
 *   <td>setSecurityManager</td>
 *   <td>Setting of the security manager (possibly replacing an existing one)
 * </td>
 *   <td>The security manager is a class that allows
 * applications to implement a security policy. Granting the setSecurityManager
 * permission would allow code to change which security manager is used by
 * installing a different, possibly less restrictive security manager,
 * thereby bypassing checks that would have been enforced by the original
 * security manager.</td>
 * </tr>
 *
 * <tr>
 *   <td>createSecurityManager</td>
 *   <td>Creation of a new security manager</td>
 *   <td>This gives code access to protected, sensitive methods that may
 * disclose information about other classes or the execution stack.</td>
 * </tr>
 *
 * <tr>
 *   <td>getenv.{variable name}</td>
 *   <td>Reading of the value of the specified environment variable</td>
 *   <td>This would allow code to read the value, or determine the
 *       existence, of a particular environment variable.  This is
 *       dangerous if the variable contains confidential data.</td>
 * </tr>
 *
 * <tr>
 *   <td>exitVM.{exit status}</td>
 *   <td>Halting of the Java Virtual Machine with the specified exit status</td>
 *   <td>This allows an attacker to mount a denial-of-service attack
 * by automatically forcing the virtual machine to halt.
 * Note: The "exitVM.*" permission is automatically granted to all code
 * loaded from the application class path, thus enabling applications
 * to terminate themselves. Also, the "exitVM" permission is equivalent to
 * "exitVM.*".</td>
 * </tr>
 *
 * <tr>
 *   <td>shutdownHooks</td>
 *   <td>Registration and cancellation of virtual-machine shutdown hooks</td>
 *   <td>This allows an attacker to register a malicious shutdown
 * hook that interferes with the clean shutdown of the virtual machine.</td>
 * </tr>
 *
 * <tr>
 *   <td>setFactory</td>
 *   <td>Setting of the socket factory used by ServerSocket or Socket,
 * or of the stream handler factory used by URL</td>
 *   <td>This allows code to set the actual implementation
 * for the socket, server socket, stream handler, or RMI socket factory.
 * An attacker may set a faulty implementation which mangles the data
 * stream.</td>
 * </tr>
 *
 * <tr>
 *   <td>setIO</td>
 *   <td>Setting of System.out, System.in, and System.err</td>
 *   <td>This allows changing the value of the standard system streams.
 * An attacker may change System.in to monitor and
 * steal user input, or may set System.err to a "null" OutputStream,
 * which would hide any error messages sent to System.err. </td>
 * </tr>
 *
 * <tr>
 *   <td>modifyThread</td>
 *   <td>Modification of threads, e.g., via calls to Thread
 * <tt>interrupt</tt>, <tt>stop</tt>, <tt>suspend</tt>,
 * <tt>resume</tt>, <tt>setDaemon</tt>, <tt>setPriority</tt>,
 * <tt>setName</tt> and <tt>setUncaughtExceptionHandler</tt>
 * methods</td>
 * <td>This allows an attacker to modify the behaviour of
 * any thread in the system.</td>
 * </tr>
 *
 * <tr>
 *   <td>stopThread</td>
 *   <td>Stopping of threads via calls to the Thread <code>stop</code>
 * method</td>
 *   <td>This allows code to stop any thread in the system provided that it is
 * already granted permission to access that thread.
 * This poses as a threat, because that code may corrupt the system by
 * killing existing threads.</td>
 * </tr>
 *
 * <tr>
 *   <td>modifyThreadGroup</td>
 *   <td>modification of thread groups, e.g., via calls to ThreadGroup
 * <code>destroy</code>, <code>getParent</code>, <code>resume</code>,
 * <code>setDaemon</code>, <code>setMaxPriority</code>, <code>stop</code>,
 * and <code>suspend</code> methods</td>
 *   <td>This allows an attacker to create thread groups and
 * set their run priority.</td>
 * </tr>
 *
 * <tr>
 *   <td>getProtectionDomain</td>
 *   <td>Retrieval of the ProtectionDomain for a class</td>
 *   <td>This allows code to obtain policy information
 * for a particular code source. While obtaining policy information
 * does not compromise the security of the system, it does give
 * attackers additional information, such as local file names for
 * example, to better aim an attack.</td>
 * </tr>
 *
 * <tr>
 *   <td>getFileSystemAttributes</td>
 *   <td>Retrieval of file system attributes</td>
 *   <td>This allows code to obtain file system information such as disk usage
 *       or disk space available to the caller.  This is potentially dangerous
 *       because it discloses information about the system hardware
 *       configuration and some information about the caller's privilege to
 *       write files.</td>
 * </tr>
 *
 * <tr>
 *   <td>readFileDescriptor</td>
 *   <td>Reading of file descriptors</td>
 *   <td>This would allow code to read the particular file associated
 *       with the file descriptor read. This is dangerous if the file
 *       contains confidential data.</td>
 * </tr>
 *
 * <tr>
 *   <td>writeFileDescriptor</td>
 *   <td>Writing to file descriptors</td>
 *   <td>This allows code to write to a particular file associated
 *       with the descriptor. This is dangerous because it may allow
 *       malicious code to plant viruses or at the very least, fill up
 *       your entire disk.</td>
 * </tr>
 *
 * <tr>
 *   <td>loadLibrary.{library name}</td>
 *   <td>Dynamic linking of the specified library</td>
 *   <td>It is dangerous to allow an applet permission to load native code
 * libraries, because the Java security architecture is not designed to and
 * does not prevent malicious behavior at the level of native code.</td>
 * </tr>
 *
 * <tr>
 *   <td>accessClassInPackage.{package name}</td>
 *   <td>Access to the specified package via a class loader's
 * <code>loadClass</code> method when that class loader calls
 * the SecurityManager <code>checkPackageAccess</code> method</td>
 *   <td>This gives code access to classes in packages
 * to which it normally does not have access. Malicious code
 * may use these classes to help in its attempt to compromise
 * security in the system.</td>
 * </tr>
 *
 * <tr>
 *   <td>defineClassInPackage.{package name}</td>
 *   <td>Definition of classes in the specified package, via a class
 * loader's <code>defineClass</code> method when that class loader calls
 * the SecurityManager <code>checkPackageDefinition</code> method.</td>
 *   <td>This grants code permission to define a class
 * in a particular package. This is dangerous because malicious
 * code with this permission may define rogue classes in
 * trusted packages like <code>java.security</code> or <code>java.lang</code>,
 * for example.</td>
 * </tr>
 *
 * <tr>
 *   <td>accessDeclaredMembers</td>
 *   <td>Access to the declared members of a class</td>
 *   <td>This grants code permission to query a class for its public,
 * protected, default (package) access, and private fields and/or
 * methods. Although the code would have
 * access to the private and protected field and method names, it would not
 * have access to the private/protected field data and would not be able
 * to invoke any private methods. Nevertheless, malicious code
 * may use this information to better aim an attack.
 * Additionally, it may invoke any public methods and/or access public fields
 * in the class.  This could be dangerous if
 * the code would normally not be able to invoke those methods and/or
 * access the fields  because
 * it can't cast the object to the class/interface with those methods
 * and fields.
</td>
 * </tr>
 * <tr>
 *   <td>queuePrintJob</td>
 *   <td>Initiation of a print job request</td>
 *   <td>This could print sensitive information to a printer,
 * or simply waste paper.</td>
 * </tr>
 *
 * <tr>
 *   <td>getStackTrace</td>
 *   <td>Retrieval of the stack trace information of another thread.</td>
 *   <td>This allows retrieval of the stack trace information of
 * another thread.  This might allow malicious code to monitor the
 * execution of threads and discover vulnerabilities in applications.</td>
 * </tr>
 *
 * <tr>
 *   <td>setDefaultUncaughtExceptionHandler</td>
 *   <td>Setting the default handler to be used when a thread
 *   terminates abruptly due to an uncaught exception</td>
 *   <td>This allows an attacker to register a malicious
 *   uncaught exception handler that could interfere with termination
 *   of a thread</td>
 * </tr>
 *
 * <tr>
 *   <td>preferences</td>
 *   <td>Represents the permission required to get access to the
 *   java.util.prefs.Preferences implementations user or system root
 *   which in turn allows retrieval or update operations within the
 *   Preferences persistent backing store.) </td>
 *   <td>This permission allows the user to read from or write to the
 *   preferences backing store if the user running the code has
 *   sufficient OS privileges to read/write to that backing store.
 *   The actual backing store may reside within a traditional filesystem
 *   directory or within a registry depending on the platform OS</td>
 * </tr>
 *
 * <tr>
 *   <td>usePolicy</td>
 *   <td>Granting this permission disables the Java Plug-In's default
 *   security prompting behavior.</td>
 *   <td>For more information, refer to Java Plug-In's guides, <a href=
 *   "../../../technotes/guides/plugin/developer_guide/security.html">
 *   Applet Security Basics</a> and <a href=
 *   "../../../technotes/guides/plugin/developer_guide/rsa_how.html#use">
 *   usePolicy Permission</a>.</td>
 * </tr>
 * </table>
 *
 * <p>
 *  此类用于运行时权限。 RuntimePermission包含一个名称(也称为"目标名称"),但没有动作列表;你有命名的权限或你不。
 * 
 * <P>
 *  目标名称是运行时权限的名称(见下文)。命名约定遵循分层属性命名约定。此外,星号可能出现在名称的末尾,跟在"。"后面,或者它本身,表示通配符匹配。例如："loadLibrary。
 * *"和"*"表示通配符匹配,而"* loadLibrary"和"a * b"则不匹配。
 * <P>
 *  下表列出了所有可能的RuntimePermission目标名称,并为每个提供了权限允许的描述以及授予代码权限的风险的讨论。
 * 
 *  <table border = 1 cellpadding = 5 summary ="权限目标名称,
 * what the target allows,and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> createClassLoader </td> <td>创建类加载器</td> <td>这是一个极其危险的授予权限。可以实例化自己的类加载器的恶意应用程序可以将自己的恶意类加载到系统中。
 * 这些新加载的类可以由类加载器放入任何保护域,从而自动授予类对该域的权限。</td>。
 * </tr>
 * 
 * <tr>
 * <td> getClassLoader </td> <td>检索类加载器(例如,调用类的类加载器)</td> <td>这将授予攻击者获取特定类的类加载器的权限。
 * 这是危险的,因为访问类的类加载器允许攻击者加载该类加载器可用的其他类。攻击者通常无法访问这些类。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> setContextClassLoader </td> <td>线程使用的上下文类加载器的设置</td> <td>当系统代码和扩展需要查找可能不存在的资源时,系统代码和扩展使用上下文类加载器
 * 系统类加载器。
 * 授予setContextClassLoader权限将允许代码更改特定线程(包括系统线程)使用的上下文类加载器。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> enableContextClassLoaderOverride </td> <td>线程上下文类加载器方法的子类实现</td> <td>当系统代码和扩展需要查找可能不存在的资源时,系统类装
 * 载器。
 * 授予enableContextClassLoaderOverride权限将允许Thread的子类重写用于获取或设置特定线程的上下文类加载器的方法。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> closeClassLoader </td> <td>关闭ClassLoader </td> <td>授予此权限允许代码关闭任何引用它的URLClassLoader。</td>
 * </tr>
 * 
 * <tr>
 * <td> setSecurityManager </td> <td>安全管理器的设置(可能替换现有的安全管理器)
 * </td>
 *  <td>安全管理器是一个允许应用程序实现安全策略的类。
 * 授予setSecurityManager权限将允许代码通过安装不同的,可能较少限制的安全管理器来更改所使用的安全管理器,从而绕过原始安全管理器将执行的检查。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> createSecurityManager </td> <td>创建新的安全管理器</td> <td>这会让代码访问受保护的敏感方法,这些方法可能会泄露有关其他类或执行堆栈的信息。
 * </td>。
 * </tr>
 * 
 * <tr>
 *  <td> getenv。{variable name} </td> <td>读取指定环境变量的值</td> <td>这将允许代码读取特定环境的值或确定其存在变量。如果变量包含机密数据,这是很危险的。
 * </td>。
 * </tr>
 * 
 * <tr>
 *  <td> exitVM。{exit status} </td> <td>停止具有指定退出状态的Java虚拟机</td> <td>这允许攻击者通过自动强制安装拒绝服务攻击虚拟机停止。
 * 注意："exitVM。*"权限被自动授予从应用程序类路径加载的所有代码,从而使应用程序能够自行终止。此外,"exitVM"权限等同于"exitVM。*"。</td>。
 * </tr>
 * 
 * <tr>
 * <td> shutdownHooks </td> <td>注册和取消虚拟机关闭挂接</td> <td>这允许攻击者注册一个恶意关闭挂接,干扰虚拟机的完全关闭。 td>
 * </tr>
 * 
 * <tr>
 *  <td> setFactory </td> <td>由ServerSocket或Socket使用的套接字工厂或URL使用的流处理程序工厂的设置</td> <td>这允许代码设置套接字的实际实现,服务器
 * 套接字,流处理程序或RMI套接字工厂。
 * 攻击者可能会设置一个错误的实现,损坏数据流。</td>。
 * </tr>
 * 
 * <tr>
 *  <td> setIO </td> <td> System.out,System.in和System.err的设置</td> <td>这允许更改标准系统流的值。
 * 攻击者可以更改System.in来监视和窃取用户输入,或者可以将System.err设置为"null"OutputStream,这将隐藏发送到System.err的任何错误消息。 </td>。
 * </tr>
 * 
 * <tr>
 *  <td> modifyThread </td> <td>修改线程,例如通过调用Thread <tt>中断</tt>,<tt>停止</tt>,<tt>挂起</tt> > resume </tt>,<tt>
 *  setDaemon </tt>,<tt> setPriority </tt>,<tt> setName </tt>和<tt> setUncaughtExceptionHandler </tt>这允许攻
 * 击者修改系统中任何线程的行为。
 * </td>。
 * </tr>
 * 
 * <tr>
 * <td> stopThread </td> <td>通过调用Thread <code> stop </code>方法停止线程</td> <td>这允许代码停止系统中的任何线程,授予访问该线程的权限。
 * 这构成威胁,因为该代码可能会通过杀死现有线程来破坏系统。</td>。
 * </tr>
 * 
 * <tr>
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

public final class RuntimePermission extends BasicPermission {

    private static final long serialVersionUID = 7399184964622342223L;

    /**
     * Creates a new RuntimePermission with the specified name.
     * The name is the symbolic name of the RuntimePermission, such as
     * "exit", "setFactory", etc. An asterisk
     * may appear at the end of the name, following a ".", or by itself, to
     * signify a wildcard match.
     *
     * <p>
     *  修改线程组,例如通过调用ThreadGroup <code> destroy </code>,<code> getParent </code>,<code> resume </code>,<td> m
     * odifyThreadGroup </td>代码> setDaemon </code>,<code> setMaxPriority </code>,<code> stop </code>和<code> 
     * suspend </code>方法</td> <td>组并设置其运行优先级。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> getProtectionDomain </td> <td>检索类的ProtectionDomain </td> <td>这允许代码获取特定代码源的策略信息。
     * 虽然获取策略信息不会危及系统的安全性,但它会向攻击者提供其他信息,例如本地文件名,以更好地攻击攻击。</td>。
     * </tr>
     * 
     * <tr>
     *  <td> getFileSystemAttributes </td> <td>文件系统属性的检索</td> <td>这允许代码获取文件系统信息,例如调用者可用的磁盘使用情况或磁盘空间。
     * 这是潜在的危险,因为它公开了关于系统硬件配置的信息和关于调用者写入文件的特权的一些信息。</td>。
     * </tr>
     * 
     * <tr>
     * <td> readFileDescriptor </td> <td>读取文件描述符</td> <td>这将允许代码读取与读取的文件描述符相关联的特定文件。如果文件包含机密数据,这是很危险的。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> writeFileDescriptor </td> <td>写入文件描述符</td> <td>这允许代码写入与描述符相关联的特定文件。
     * 这是危险的,因为它可能允许恶意代码植入病毒,或至少,填满你的整个磁盘。</td>。
     * </tr>
     * 
     * <tr>
     *  <td> loadLibrary。
     * {library name} </td> <td>指定库的动态链接</td> <td>允许applet权限加载本机代码库很危险,因为Java安全体系结构不是设计为并且不防止本机代码级别的恶意行为。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> accessClassInPackage。
     * {package name} </td> <td>当类加载器调用SecurityManager <code> checkPackageAccess </code>方法时,通过类加载器的<code> lo
     * adClass </code>方法访问指定的包</td> <td>这使得代码可以访问通常无法访问的包中的类。
     *  <td> accessClassInPackage。恶意代码可能会使用这些类来帮助企图危及系统的安全。</td>。
     * </tr>
     * 
     * <tr>
     * <td> defineClassInPackage。
     * {package name} </td> <td>当类加载器调用SecurityManager时,通过类加载器的<code> defineClass </code>方法定义指定包中的类<code> ch
     * eckPackageDefinition <代码>方法。
     * <td> defineClassInPackage。</td> <td>这将授予代码在特定包中定义类的权限。
     * 这是危险的,因为具有此权限的恶意代码可以在受信任包中定义欺骗类,例如<code> java.security </code>或<code> java.lang </code>。</td>。
     * 
     * @param name the name of the RuntimePermission.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public RuntimePermission(String name)
    {
        super(name);
    }

    /**
     * Creates a new RuntimePermission object with the specified name.
     * The name is the symbolic name of the RuntimePermission, and the
     * actions String is currently unused and should be null.
     *
     * <p>
     * </tr>
     * 
     * <tr>
     *  <td> accessDeclaredMembers </td> <td>访问类的声明成员</td> <td>这将授予代码查询类的公共,受保护,默认(包)访问和私有字段的权限, /或方法。
     * 尽管代码可以访问私有和受保护的字段和方法名称,但它不能访问私有/受保护的字段数据,并且不能调用任何私有方法。然而,恶意代码可以使用该信息来更好地瞄准攻击。
     * 此外,它可以调用任何公共方法和/或访问类中的公共字段。如果代码通常不能调用这些方法和/或访问字段,这可能是危险的,因为它不能将对象转换为具有那些方法和字段的类/接口。
     * </td>
     * </tr>
     * <tr>
     *  <td> queuePrintJob </td> <td>启动打印作业请求</td> <td>这可以将敏感信息打印到打印机,或只是废纸。</td>
     * </tr>
     * 
     * <tr>
     * <td> getStackTrace </td> <td>检索另一个线程的堆栈跟踪信息。</td> <td>这允许检索另一个线程的堆栈跟踪信息。这可能允许恶意代码监视线程的执行和发现应用程序中的漏洞。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> setDefaultUncaughtExceptionHandler </td> <td>设置当线程由于未捕获异常突然终止时使用的默认处理程序</td> <td>这允许攻击者注册可能会干扰的
     * 恶意未捕获异常处理程序终止线程</td>。
     * </tr>
     * 
     * 
     * @param name the name of the RuntimePermission.
     * @param actions should be null.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public RuntimePermission(String name, String actions)
    {
        super(name, actions);
    }
}
