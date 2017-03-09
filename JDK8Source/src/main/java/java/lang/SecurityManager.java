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

package java.lang;

import java.security.*;
import java.io.FileDescriptor;
import java.io.File;
import java.io.FilePermission;
import java.awt.AWTPermission;
import java.util.PropertyPermission;
import java.lang.RuntimePermission;
import java.net.SocketPermission;
import java.net.NetPermission;
import java.util.Hashtable;
import java.net.InetAddress;
import java.lang.reflect.*;
import java.net.URL;

import sun.reflect.CallerSensitive;
import sun.security.util.SecurityConstants;

/**
 * The security manager is a class that allows
 * applications to implement a security policy. It allows an
 * application to determine, before performing a possibly unsafe or
 * sensitive operation, what the operation is and whether
 * it is being attempted in a security context that allows the
 * operation to be performed. The
 * application can allow or disallow the operation.
 * <p>
 * The <code>SecurityManager</code> class contains many methods with
 * names that begin with the word <code>check</code>. These methods
 * are called by various methods in the Java libraries before those
 * methods perform certain potentially sensitive operations. The
 * invocation of such a <code>check</code> method typically looks like this:
 * <blockquote><pre>
 *     SecurityManager security = System.getSecurityManager();
 *     if (security != null) {
 *         security.check<i>XXX</i>(argument, &nbsp;.&nbsp;.&nbsp;.&nbsp;);
 *     }
 * </pre></blockquote>
 * <p>
 * The security manager is thereby given an opportunity to prevent
 * completion of the operation by throwing an exception. A security
 * manager routine simply returns if the operation is permitted, but
 * throws a <code>SecurityException</code> if the operation is not
 * permitted. The only exception to this convention is
 * <code>checkTopLevelWindow</code>, which returns a
 * <code>boolean</code> value.
 * <p>
 * The current security manager is set by the
 * <code>setSecurityManager</code> method in class
 * <code>System</code>. The current security manager is obtained
 * by the <code>getSecurityManager</code> method.
 * <p>
 * The special method
 * {@link SecurityManager#checkPermission(java.security.Permission)}
 * determines whether an access request indicated by a specified
 * permission should be granted or denied. The
 * default implementation calls
 *
 * <pre>
 *   AccessController.checkPermission(perm);
 * </pre>
 *
 * <p>
 * If a requested access is allowed,
 * <code>checkPermission</code> returns quietly. If denied, a
 * <code>SecurityException</code> is thrown.
 * <p>
 * As of Java 2 SDK v1.2, the default implementation of each of the other
 * <code>check</code> methods in <code>SecurityManager</code> is to
 * call the <code>SecurityManager checkPermission</code> method
 * to determine if the calling thread has permission to perform the requested
 * operation.
 * <p>
 * Note that the <code>checkPermission</code> method with
 * just a single permission argument always performs security checks
 * within the context of the currently executing thread.
 * Sometimes a security check that should be made within a given context
 * will actually need to be done from within a
 * <i>different</i> context (for example, from within a worker thread).
 * The {@link SecurityManager#getSecurityContext getSecurityContext} method
 * and the {@link SecurityManager#checkPermission(java.security.Permission,
 * java.lang.Object) checkPermission}
 * method that includes a context argument are provided
 * for this situation. The
 * <code>getSecurityContext</code> method returns a "snapshot"
 * of the current calling context. (The default implementation
 * returns an AccessControlContext object.) A sample call is
 * the following:
 *
 * <pre>
 *   Object context = null;
 *   SecurityManager sm = System.getSecurityManager();
 *   if (sm != null) context = sm.getSecurityContext();
 * </pre>
 *
 * <p>
 * The <code>checkPermission</code> method
 * that takes a context object in addition to a permission
 * makes access decisions based on that context,
 * rather than on that of the current execution thread.
 * Code within a different context can thus call that method,
 * passing the permission and the
 * previously-saved context object. A sample call, using the
 * SecurityManager <code>sm</code> obtained as in the previous example,
 * is the following:
 *
 * <pre>
 *   if (sm != null) sm.checkPermission(permission, context);
 * </pre>
 *
 * <p>Permissions fall into these categories: File, Socket, Net,
 * Security, Runtime, Property, AWT, Reflect, and Serializable.
 * The classes managing these various
 * permission categories are <code>java.io.FilePermission</code>,
 * <code>java.net.SocketPermission</code>,
 * <code>java.net.NetPermission</code>,
 * <code>java.security.SecurityPermission</code>,
 * <code>java.lang.RuntimePermission</code>,
 * <code>java.util.PropertyPermission</code>,
 * <code>java.awt.AWTPermission</code>,
 * <code>java.lang.reflect.ReflectPermission</code>, and
 * <code>java.io.SerializablePermission</code>.
 *
 * <p>All but the first two (FilePermission and SocketPermission) are
 * subclasses of <code>java.security.BasicPermission</code>, which itself
 * is an abstract subclass of the
 * top-level class for permissions, which is
 * <code>java.security.Permission</code>. BasicPermission defines the
 * functionality needed for all permissions that contain a name
 * that follows the hierarchical property naming convention
 * (for example, "exitVM", "setFactory", "queuePrintJob", etc).
 * An asterisk
 * may appear at the end of the name, following a ".", or by itself, to
 * signify a wildcard match. For example: "a.*" or "*" is valid,
 * "*a" or "a*b" is not valid.
 *
 * <p>FilePermission and SocketPermission are subclasses of the
 * top-level class for permissions
 * (<code>java.security.Permission</code>). Classes like these
 * that have a more complicated name syntax than that used by
 * BasicPermission subclass directly from Permission rather than from
 * BasicPermission. For example,
 * for a <code>java.io.FilePermission</code> object, the permission name is
 * the path name of a file (or directory).
 *
 * <p>Some of the permission classes have an "actions" list that tells
 * the actions that are permitted for the object.  For example,
 * for a <code>java.io.FilePermission</code> object, the actions list
 * (such as "read, write") specifies which actions are granted for the
 * specified file (or for files in the specified directory).
 *
 * <p>Other permission classes are for "named" permissions -
 * ones that contain a name but no actions list; you either have the
 * named permission or you don't.
 *
 * <p>Note: There is also a <code>java.security.AllPermission</code>
 * permission that implies all permissions. It exists to simplify the work
 * of system administrators who might need to perform multiple
 * tasks that require all (or numerous) permissions.
 * <p>
 * See <a href ="../../../technotes/guides/security/permissions.html">
 * Permissions in the JDK</a> for permission-related information.
 * This document includes, for example, a table listing the various SecurityManager
 * <code>check</code> methods and the permission(s) the default
 * implementation of each such method requires.
 * It also contains a table of all the version 1.2 methods
 * that require permissions, and for each such method tells
 * which permission it requires.
 * <p>
 * For more information about <code>SecurityManager</code> changes made in
 * the JDK and advice regarding porting of 1.1-style security managers,
 * see the <a href="../../../technotes/guides/security/index.html">security documentation</a>.
 *
 * <p>
 *  安全管理器是一种允许应用程序实现安全策略的类。它允许应用在执行可能不安全或敏感的操作之前确定操作是什么以及它是否正在允许执行操作的安全上下文中被尝试。应用程序可以允许或禁止操作。
 * <p>
 *  <code> SecurityManager </code>类包含许多以<code> check </code>开头的名称的方法。
 * 在这些方法执行某些潜在敏感操作之前,Java库中的各种方法调用这些方法。
 * 调用这种<code>检查</code>方法通常看起来像这样：<blockquote> <pre> SecurityManager security = System.getSecurityManager
 * (); if(security！= null){security.check <i> XXX </i>(argument,&nbsp;。
 * 在这些方法执行某些潜在敏感操作之前,Java库中的各种方法调用这些方法。&nbsp;。&nbsp;。&nbsp;); } </pre> </blockquote>。
 * <p>
 *  由此,安全管理器被给予通过抛出异常来防止操作完成的机会。如果操作被允许,安全管理器例程简单地返回,但是如果不允许该操作,则抛出一个<code> SecurityException </code>。
 * 这个约定的唯一例外是<code> checkTopLevelWindow </code>,它返回一个<code> boolean </code>值。
 * <p>
 * 当前安全管理器由<code> System </code>类中的<code> setSecurityManager </code>方法设置。
 * 当前安全管理器是通过<code> getSecurityManager </code>方法获取的。
 * <p>
 *  特殊方法{@link SecurityManager#checkPermission(java.security.Permission)}确定是否应授予或拒绝由指定的权限指示的访问请求。
 * 默认实现调用。
 * 
 * <pre>
 *  AccessController.checkPermission(perm);
 * </pre>
 * 
 * <p>
 *  如果允许请求访问,<code> checkPermission </code>会安静返回。如果拒绝,将抛出<code> SecurityException </code>。
 * <p>
 *  从Java 2 SDK v1.2开始,<code> SecurityManager </code>中每个其他<code>检查</code>方法的默认实现是调用<code> SecurityManage
 * r checkPermission </code>方法确定调用线程是否具有执行所请求的操作的许可。
 * <p>
 * 注意,只有一个权限参数的<code> checkPermission </code>方法总是在当前正在执行的线程的上下文中执行安全检查。
 * 有时,应该在给定上下文中进行的安全检查实际上需要在<i>不同的上下文(例如,从工作者线程内)中完成。
 * 为此情况提供了{@link SecurityManager#getSecurityContext getSecurityContext}方法和包含上下文参数的{@link SecurityManager#checkPermission(java.security.Permission,java.lang.Object)checkPermission}
 * 方法。
 * 有时,应该在给定上下文中进行的安全检查实际上需要在<i>不同的上下文(例如,从工作者线程内)中完成。
 *  <code> getSecurityContext </code>方法返回当前调用上下文的"快照"。 (默认实现返回一个AccessControlContext对象。)示例调用如下：。
 * 
 * <pre>
 *  Object context = null; SecurityManager sm = System.getSecurityManager(); if(sm！= null)context = sm.g
 * etSecurityContext();。
 * </pre>
 * 
 * <p>
 *  除了权限之外还需要一个上下文对象的<code> checkPermission </code>方法基于该上下文而不是当前执行线程的访问决策。
 * 不同上下文中的代码因此可以调用该方法,传递许可和先前保存的上下文对象。使用上一个示例中获取的SecurityManager <code> sm </code>进行示例调用如下：。
 * 
 * <pre>
 *  if(sm！= null)sm.checkPermission(permission,context);
 * </pre>
 * 
 * <p>权限属于以下类别：文件,套接字,网络,安全,运行时,属性,AWT,反映和可序列化。
 * 管理这些不同权限类别的类是<code> java.io.FilePermission </code>,<code> java.net.SocketPermission </code>,<code> ja
 * va.net.NetPermission </code> java.security.SecurityPermission </code>,<code> java.lang.RuntimePermiss
 * ion </code>,<code> java.util.PropertyPermission </code>,<code> java.awt.AWTPermission </code>代码> java
 * .lang.reflect.ReflectPermission </code>和<code> java.io.SerializablePermission </code>。
 * <p>权限属于以下类别：文件,套接字,网络,安全,运行时,属性,AWT,反映和可序列化。
 * 
 *  <p>除了前两个(FilePermission和SocketPermission)是<code> java.security.BasicPermission </code>的子类,它本身是一个顶级类的
 * 权限的抽象子类,它是<code> java .security.Permission </code>。
 *  BasicPermission定义了包含遵循层次属性命名约定的名称(例如,"exitVM","setFactory","queuePrintJob"等)的所有权限所需的功能。
 * 星号可以出现在名称的末尾,后面跟着"。",或者表示一个通配符匹配。例如："a。*"或"*"有效,"* a"或"a * b"无效。
 * 
 * <p> FilePermission和SocketPermission是顶级类的权限子类(<code> java.security.Permission </code>)。
 * 类似这样的类具有比由BasicPermission子类直接从Permission而不是从BasicPermission使用更复杂的名称语法。
 * 例如,对于<code> java.io.FilePermission </code>对象,权限名称是文件(或目录)的路径名。
 * 
 *  <p>一些权限类具有一个"动作"列表,用于说明对象允许的操作。
 * 例如,对于<code> java.io.FilePermission </code>对象,操作列表(例如"读取,写入")​​指定为指定的文件(或指定目录中的文件)授予哪些操作。
 * 
 *  <p>其他权限类用于"命名"权限 - 包含名称但没有操作列表的权限;你有命名的权限或你不。
 * 
 *  <p>注意：还有一个<code> java.security.AllPermission </code>权限,表示所有权限。
 * 它可以简化系统管理员的工作,系统管理员可能需要执行需要所有(或多个)权限的多个任务。
 * <p>
 * 有关权限相关信息,请参见<a href="../../../technotes/guides/security/permissions.html"> JDK中的权限</a>。
 * 该文档包括例如列出各种SecurityManager <code>检查</code>方法的表格和每个这样的方法所需的默认实现的权限。
 * 它还包含需要权限的所有版本1.2方法的表,并且每个这样的方法告诉它需要哪个权限。
 * <p>
 *  有关在JDK中进行的<code> SecurityManager </code>更改和有关移植1.1样式安全管理器的建议的详细信息,请参阅<a href ="../../../ technotes / guides / security / index.html">
 * 安全文档</a>。
 * 
 * 
 * @author  Arthur van Hoff
 * @author  Roland Schemers
 *
 * @see     java.lang.ClassLoader
 * @see     java.lang.SecurityException
 * @see     java.lang.SecurityManager#checkTopLevelWindow(java.lang.Object)
 *  checkTopLevelWindow
 * @see     java.lang.System#getSecurityManager() getSecurityManager
 * @see     java.lang.System#setSecurityManager(java.lang.SecurityManager)
 *  setSecurityManager
 * @see     java.security.AccessController AccessController
 * @see     java.security.AccessControlContext AccessControlContext
 * @see     java.security.AccessControlException AccessControlException
 * @see     java.security.Permission
 * @see     java.security.BasicPermission
 * @see     java.io.FilePermission
 * @see     java.net.SocketPermission
 * @see     java.util.PropertyPermission
 * @see     java.lang.RuntimePermission
 * @see     java.awt.AWTPermission
 * @see     java.security.Policy Policy
 * @see     java.security.SecurityPermission SecurityPermission
 * @see     java.security.ProtectionDomain
 *
 * @since   JDK1.0
 */
public
class SecurityManager {

    /**
     * This field is <code>true</code> if there is a security check in
     * progress; <code>false</code> otherwise.
     *
     * <p>
     *  如果正在进行安全检查,则此字段为<code> true </code>; <code> false </code>。
     * 
     * 
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     */
    @Deprecated
    protected boolean inCheck;

    /*
     * Have we been initialized. Effective against finalizer attacks.
     * <p>
     *  我们已经初始化。有效对抗终结者攻击。
     * 
     */
    private boolean initialized = false;


    /**
     * returns true if the current context has been granted AllPermission
     * <p>
     *  如果当前上下文已授予AllPermission,则返回true
     * 
     */
    private boolean hasAllPermission()
    {
        try {
            checkPermission(SecurityConstants.ALL_PERMISSION);
            return true;
        } catch (SecurityException se) {
            return false;
        }
    }

    /**
     * Tests if there is a security check in progress.
     *
     * <p>
     *  测试是否存在正在进行的安全检查。
     * 
     * 
     * @return the value of the <code>inCheck</code> field. This field
     *          should contain <code>true</code> if a security check is
     *          in progress,
     *          <code>false</code> otherwise.
     * @see     java.lang.SecurityManager#inCheck
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     */
    @Deprecated
    public boolean getInCheck() {
        return inCheck;
    }

    /**
     * Constructs a new <code>SecurityManager</code>.
     *
     * <p> If there is a security manager already installed, this method first
     * calls the security manager's <code>checkPermission</code> method
     * with the <code>RuntimePermission("createSecurityManager")</code>
     * permission to ensure the calling thread has permission to create a new
     * security manager.
     * This may result in throwing a <code>SecurityException</code>.
     *
     * <p>
     *  构造一个新的<code> SecurityManager </code>。
     * 
     *  <p>如果已经安装了安全管理器,此方法首先使用<code> RuntimePermission("createSecurityManager")</code>权限调用安全管理器的<code> chec
     * kPermission </code>方法,创建新安全管理器的权限。
     * 这可能会导致抛出一个<code> SecurityException </code>。
     * 
     * 
     * @exception  java.lang.SecurityException if a security manager already
     *             exists and its <code>checkPermission</code> method
     *             doesn't allow creation of a new security manager.
     * @see        java.lang.System#getSecurityManager()
     * @see        #checkPermission(java.security.Permission) checkPermission
     * @see java.lang.RuntimePermission
     */
    public SecurityManager() {
        synchronized(SecurityManager.class) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                // ask the currently installed security manager if we
                // can create a new one.
                sm.checkPermission(new RuntimePermission
                                   ("createSecurityManager"));
            }
            initialized = true;
        }
    }

    /**
     * Returns the current execution stack as an array of classes.
     * <p>
     * The length of the array is the number of methods on the execution
     * stack. The element at index <code>0</code> is the class of the
     * currently executing method, the element at index <code>1</code> is
     * the class of that method's caller, and so on.
     *
     * <p>
     *  将当前执行堆栈作为类数组返回。
     * <p>
     * 数组的长度是执行堆栈上的方法数。索引<code> 0 </code>处的元素是当前执行方法的类,索引<code> 1 </code>处的元素是该方法调用者的类,等等。
     * 
     * 
     * @return  the execution stack.
     */
    protected native Class[] getClassContext();

    /**
     * Returns the class loader of the most recently executing method from
     * a class defined using a non-system class loader. A non-system
     * class loader is defined as being a class loader that is not equal to
     * the system class loader (as returned
     * by {@link ClassLoader#getSystemClassLoader}) or one of its ancestors.
     * <p>
     * This method will return
     * <code>null</code> in the following three cases:
     * <ol>
     *   <li>All methods on the execution stack are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li>All methods on the execution stack up to the first
     *   "privileged" caller
     *   (see {@link java.security.AccessController#doPrivileged})
     *   are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li> A call to <code>checkPermission</code> with
     *   <code>java.security.AllPermission</code> does not
     *   result in a SecurityException.
     *
     * </ol>
     *
     * <p>
     *  从使用非系统类加载器定义的类中返回最近执行的方法的类加载器。
     * 非系统类加载器被定义为不等于系统类加载器(由{@link ClassLoader#getSystemClassLoader}返回)或其祖先之一的类加载器。
     * <p>
     *  此方法将在以下三种情况下返回<code> null </code>：
     * <ol>
     *  <li>执行堆栈上的所有方法都来自使用系统类装入器或其祖先之一定义的类。
     * 
     *  <li>执行堆栈上的所有方法,直到第一个"特权"调用者(参见{@link java.security.AccessController#doPrivileged})来自使用系统类加载器或其祖先之一定义
     * 的类。
     * 
     *  <li>对<code> checkPermission </code>与<code> java.security.AllPermission </code>的调用不会导致SecurityExcepti
     * on。
     * 
     * </ol>
     * 
     * 
     * @return  the class loader of the most recent occurrence on the stack
     *          of a method from a class defined using a non-system class
     *          loader.
     *
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     *
     * @see  java.lang.ClassLoader#getSystemClassLoader() getSystemClassLoader
     * @see  #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    protected ClassLoader currentClassLoader()
    {
        ClassLoader cl = currentClassLoader0();
        if ((cl != null) && hasAllPermission())
            cl = null;
        return cl;
    }

    private native ClassLoader currentClassLoader0();

    /**
     * Returns the class of the most recently executing method from
     * a class defined using a non-system class loader. A non-system
     * class loader is defined as being a class loader that is not equal to
     * the system class loader (as returned
     * by {@link ClassLoader#getSystemClassLoader}) or one of its ancestors.
     * <p>
     * This method will return
     * <code>null</code> in the following three cases:
     * <ol>
     *   <li>All methods on the execution stack are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li>All methods on the execution stack up to the first
     *   "privileged" caller
     *   (see {@link java.security.AccessController#doPrivileged})
     *   are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li> A call to <code>checkPermission</code> with
     *   <code>java.security.AllPermission</code> does not
     *   result in a SecurityException.
     *
     * </ol>
     *
     * <p>
     *  返回从使用非系统类加载器定义的类中最近执行的方法的类。
     * 非系统类加载器被定义为不等于系统类加载器(由{@link ClassLoader#getSystemClassLoader}返回)或其祖先之一的类加载器。
     * <p>
     *  此方法将在以下三种情况下返回<code> null </code>：
     * <ol>
     * <li>执行堆栈上的所有方法都来自使用系统类装入器或其祖先之一定义的类。
     * 
     *  <li>执行堆栈上的所有方法,直到第一个"特权"调用者(参见{@link java.security.AccessController#doPrivileged})来自使用系统类加载器或其祖先之一定义
     * 的类。
     * 
     *  <li>对<code> checkPermission </code>与<code> java.security.AllPermission </code>的调用不会导致SecurityExcepti
     * on。
     * 
     * </ol>
     * 
     * 
     * @return  the class  of the most recent occurrence on the stack
     *          of a method from a class defined using a non-system class
     *          loader.
     *
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     *
     * @see  java.lang.ClassLoader#getSystemClassLoader() getSystemClassLoader
     * @see  #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    protected Class<?> currentLoadedClass() {
        Class<?> c = currentLoadedClass0();
        if ((c != null) && hasAllPermission())
            c = null;
        return c;
    }

    /**
     * Returns the stack depth of the specified class.
     *
     * <p>
     *  返回指定类的堆栈深度。
     * 
     * 
     * @param   name   the fully qualified name of the class to search for.
     * @return  the depth on the stack frame of the first occurrence of a
     *          method from a class with the specified name;
     *          <code>-1</code> if such a frame cannot be found.
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     *
     */
    @Deprecated
    protected native int classDepth(String name);

    /**
     * Returns the stack depth of the most recently executing method
     * from a class defined using a non-system class loader.  A non-system
     * class loader is defined as being a class loader that is not equal to
     * the system class loader (as returned
     * by {@link ClassLoader#getSystemClassLoader}) or one of its ancestors.
     * <p>
     * This method will return
     * -1 in the following three cases:
     * <ol>
     *   <li>All methods on the execution stack are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li>All methods on the execution stack up to the first
     *   "privileged" caller
     *   (see {@link java.security.AccessController#doPrivileged})
     *   are from classes
     *   defined using the system class loader or one of its ancestors.
     *
     *   <li> A call to <code>checkPermission</code> with
     *   <code>java.security.AllPermission</code> does not
     *   result in a SecurityException.
     *
     * </ol>
     *
     * <p>
     *  从使用非系统类加载器定义的类返回最近执行的方法的堆栈深度。
     * 非系统类加载器被定义为不等于系统类加载器(由{@link ClassLoader#getSystemClassLoader}返回)或其祖先之一的类加载器。
     * <p>
     *  此方法将在以下三种情况下返回-1：
     * <ol>
     *  <li>执行堆栈上的所有方法都来自使用系统类装入器或其祖先之一定义的类。
     * 
     *  <li>执行堆栈上的所有方法,直到第一个"特权"调用者(参见{@link java.security.AccessController#doPrivileged})来自使用系统类加载器或其祖先之一定义
     * 的类。
     * 
     *  <li>对<code> checkPermission </code>与<code> java.security.AllPermission </code>的调用不会导致SecurityExcepti
     * on。
     * 
     * </ol>
     * 
     * 
     * @return the depth on the stack frame of the most recent occurrence of
     *          a method from a class defined using a non-system class loader.
     *
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     *
     * @see   java.lang.ClassLoader#getSystemClassLoader() getSystemClassLoader
     * @see   #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    protected int classLoaderDepth()
    {
        int depth = classLoaderDepth0();
        if (depth != -1) {
            if (hasAllPermission())
                depth = -1;
            else
                depth--; // make sure we don't include ourself
        }
        return depth;
    }

    private native int classLoaderDepth0();

    /**
     * Tests if a method from a class with the specified
     *         name is on the execution stack.
     *
     * <p>
     *  测试来自具有指定名称的类的方法是否在执行堆栈上。
     * 
     * 
     * @param  name   the fully qualified name of the class.
     * @return <code>true</code> if a method from a class with the specified
     *         name is on the execution stack; <code>false</code> otherwise.
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     */
    @Deprecated
    protected boolean inClass(String name) {
        return classDepth(name) >= 0;
    }

    /**
     * Basically, tests if a method from a class defined using a
     *          class loader is on the execution stack.
     *
     * <p>
     * 基本上,测试来自使用类加载器定义的类的方法是否在执行堆栈上。
     * 
     * 
     * @return  <code>true</code> if a call to <code>currentClassLoader</code>
     *          has a non-null return value.
     *
     * @deprecated This type of security checking is not recommended.
     *  It is recommended that the <code>checkPermission</code>
     *  call be used instead.
     * @see        #currentClassLoader() currentClassLoader
     */
    @Deprecated
    protected boolean inClassLoader() {
        return currentClassLoader() != null;
    }

    /**
     * Creates an object that encapsulates the current execution
     * environment. The result of this method is used, for example, by the
     * three-argument <code>checkConnect</code> method and by the
     * two-argument <code>checkRead</code> method.
     * These methods are needed because a trusted method may be called
     * on to read a file or open a socket on behalf of another method.
     * The trusted method needs to determine if the other (possibly
     * untrusted) method would be allowed to perform the operation on its
     * own.
     * <p> The default implementation of this method is to return
     * an <code>AccessControlContext</code> object.
     *
     * <p>
     *  创建封装当前执行环境的对象。该方法的结果例如由三参数<code> checkConnect </code>方法和两参数<code> checkRead </code>方法使用。
     * 需要这些方法是因为可以调用可信方法来代表另一方法读取文件或打开套接字。可信方法需要确定是否允许另一个(可能不可信的)方法自己执行操作。
     *  <p>此方法的默认实现是返回一个<code> AccessControlContext </code>对象。
     * 
     * 
     * @return  an implementation-dependent object that encapsulates
     *          sufficient information about the current execution environment
     *          to perform some security checks later.
     * @see     java.lang.SecurityManager#checkConnect(java.lang.String, int,
     *   java.lang.Object) checkConnect
     * @see     java.lang.SecurityManager#checkRead(java.lang.String,
     *   java.lang.Object) checkRead
     * @see     java.security.AccessControlContext AccessControlContext
     */
    public Object getSecurityContext() {
        return AccessController.getContext();
    }

    /**
     * Throws a <code>SecurityException</code> if the requested
     * access, specified by the given permission, is not permitted based
     * on the security policy currently in effect.
     * <p>
     * This method calls <code>AccessController.checkPermission</code>
     * with the given permission.
     *
     * <p>
     *  如果根据当前有效的安全策略,不允许由给定权限指定的请求访问,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法调用具有给定权限的<code> AccessController.checkPermission </code>。
     * 
     * 
     * @param     perm   the requested permission.
     * @exception SecurityException if access is not permitted based on
     *            the current security policy.
     * @exception NullPointerException if the permission argument is
     *            <code>null</code>.
     * @since     1.2
     */
    public void checkPermission(Permission perm) {
        java.security.AccessController.checkPermission(perm);
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * specified security context is denied access to the resource
     * specified by the given permission.
     * The context must be a security
     * context returned by a previous call to
     * <code>getSecurityContext</code> and the access control
     * decision is based upon the configured security policy for
     * that security context.
     * <p>
     * If <code>context</code> is an instance of
     * <code>AccessControlContext</code> then the
     * <code>AccessControlContext.checkPermission</code> method is
     * invoked with the specified permission.
     * <p>
     * If <code>context</code> is not an instance of
     * <code>AccessControlContext</code> then a
     * <code>SecurityException</code> is thrown.
     *
     * <p>
     *  如果指定的安全上下文被拒绝访问由给定权限指定的资源,则抛出<code> SecurityException </code>。
     * 上下文必须是先前调用<code> getSecurityContext </code>返回的安全上下文,并且访问控制决策基于为该安全上下文配置的安全策略。
     * <p>
     *  如果<code> context </code>是<code> AccessControlContext </code>的实例,则使用指定的权限调用<code> AccessControlContex
     * t.checkPermission </code>方法。
     * <p>
     * 如果<code> context </code>不是<code> AccessControlContext </code>的实例,那么会抛出<code> SecurityException </code>
     * 。
     * 
     * 
     * @param      perm      the specified permission
     * @param      context   a system-dependent security context.
     * @exception  SecurityException  if the specified security context
     *             is not an instance of <code>AccessControlContext</code>
     *             (e.g., is <code>null</code>), or is denied access to the
     *             resource specified by the given permission.
     * @exception  NullPointerException if the permission argument is
     *             <code>null</code>.
     * @see        java.lang.SecurityManager#getSecurityContext()
     * @see java.security.AccessControlContext#checkPermission(java.security.Permission)
     * @since      1.2
     */
    public void checkPermission(Permission perm, Object context) {
        if (context instanceof AccessControlContext) {
            ((AccessControlContext)context).checkPermission(perm);
        } else {
            throw new SecurityException();
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to create a new class loader.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("createClassLoader")</code>
     * permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkCreateClassLoader</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许创建新的类加载器,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> RuntimePermission("createClassLoader")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkCreateClassLoader </code>,在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @exception SecurityException if the calling thread does not
     *             have permission
     *             to create a new class loader.
     * @see        java.lang.ClassLoader#ClassLoader()
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkCreateClassLoader() {
        checkPermission(SecurityConstants.CREATE_CLASSLOADER_PERMISSION);
    }

    /**
     * reference to the root thread group, used for the checkAccess
     * methods.
     * <p>
     *  引用根线程组,用于checkAccess方法。
     * 
     */

    private static ThreadGroup rootGroup = getRootGroup();

    private static ThreadGroup getRootGroup() {
        ThreadGroup root =  Thread.currentThread().getThreadGroup();
        while (root.getParent() != null) {
            root = root.getParent();
        }
        return root;
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to modify the thread argument.
     * <p>
     * This method is invoked for the current security manager by the
     * <code>stop</code>, <code>suspend</code>, <code>resume</code>,
     * <code>setPriority</code>, <code>setName</code>, and
     * <code>setDaemon</code> methods of class <code>Thread</code>.
     * <p>
     * If the thread argument is a system thread (belongs to
     * the thread group with a <code>null</code> parent) then
     * this method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("modifyThread")</code> permission.
     * If the thread argument is <i>not</i> a system thread,
     * this method just returns silently.
     * <p>
     * Applications that want a stricter policy should override this
     * method. If this method is overridden, the method that overrides
     * it should additionally check to see if the calling thread has the
     * <code>RuntimePermission("modifyThread")</code> permission, and
     * if so, return silently. This is to ensure that code granted
     * that permission (such as the JDK itself) is allowed to
     * manipulate any thread.
     * <p>
     * If this method is overridden, then
     * <code>super.checkAccess</code> should
     * be called by the first statement in the overridden method, or the
     * equivalent security check should be placed in the overridden method.
     *
     * <p>
     *  如果调用线程不允许修改线程参数,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由当前安全管理器通过<code> stop </code>,<code> suspend </code>,<code> resume </code>,<code> setPriority </code>
     *  setName </code>方法和<code> Thread </code>类的<code> setDaemon </code>方法。
     * <p>
     *  如果线程参数是系统线程(属于具有<code> null </code> parent的线程组),则此方法使用<code> RuntimePermission("modifyThread")调用<code>
     *  checkPermission </code> / code>权限。
     * 如果线程参数<i>不是</i>一个系统线程,这个方法只是静默返回。
     * <p>
     * 需要更严格策略的应用程序应覆盖此方法。
     * 如果覆盖此方法,覆盖它的方法应另外检查调用线程是否具有<code> RuntimePermission("modifyThread")</code>权限,如果是,则以静默方式返回。
     * 这是为了确保授予该权限的代码(例如JDK本身)被允许操作任何线程。
     * <p>
     *  如果这个方法被覆盖,那么被覆盖的方法中的第一个语句应该调用<code> super.checkAccess </code>,或者应该在覆盖的方法中放置等效的安全检查。
     * 
     * 
     * @param      t   the thread to be checked.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to modify the thread.
     * @exception  NullPointerException if the thread argument is
     *             <code>null</code>.
     * @see        java.lang.Thread#resume() resume
     * @see        java.lang.Thread#setDaemon(boolean) setDaemon
     * @see        java.lang.Thread#setName(java.lang.String) setName
     * @see        java.lang.Thread#setPriority(int) setPriority
     * @see        java.lang.Thread#stop() stop
     * @see        java.lang.Thread#suspend() suspend
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkAccess(Thread t) {
        if (t == null) {
            throw new NullPointerException("thread can't be null");
        }
        if (t.getThreadGroup() == rootGroup) {
            checkPermission(SecurityConstants.MODIFY_THREAD_PERMISSION);
        } else {
            // just return
        }
    }
    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to modify the thread group argument.
     * <p>
     * This method is invoked for the current security manager when a
     * new child thread or child thread group is created, and by the
     * <code>setDaemon</code>, <code>setMaxPriority</code>,
     * <code>stop</code>, <code>suspend</code>, <code>resume</code>, and
     * <code>destroy</code> methods of class <code>ThreadGroup</code>.
     * <p>
     * If the thread group argument is the system thread group (
     * has a <code>null</code> parent) then
     * this method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("modifyThreadGroup")</code> permission.
     * If the thread group argument is <i>not</i> the system thread group,
     * this method just returns silently.
     * <p>
     * Applications that want a stricter policy should override this
     * method. If this method is overridden, the method that overrides
     * it should additionally check to see if the calling thread has the
     * <code>RuntimePermission("modifyThreadGroup")</code> permission, and
     * if so, return silently. This is to ensure that code granted
     * that permission (such as the JDK itself) is allowed to
     * manipulate any thread.
     * <p>
     * If this method is overridden, then
     * <code>super.checkAccess</code> should
     * be called by the first statement in the overridden method, or the
     * equivalent security check should be placed in the overridden method.
     *
     * <p>
     *  如果调用线程不允许修改线程组参数,则抛出<code> SecurityException </code>。
     * <p>
     *  在创建新的子线程或子线程组时,通过<code> setDaemon </code>,<code> setMaxPriority </code>,<code> stop </code>,调用当前安全管理
     * 器的此方法。
     *  ,<code> suspend </code>,<code> resume </code>和<code> destroy </code>的方法。
     * <p>
     *  如果线程组参数是系统线程组(具有<code> null </code> parent),则此方法使用<code> RuntimePermission("modifyThreadGroup")</code>
     * 调用<code> checkPermission </code>允许。
     * 如果线程组参数<i>不是</i>系统线程组,此方法只是静默返回。
     * <p>
     * 需要更严格策略的应用程序应覆盖此方法。
     * 如果覆盖此方法,覆盖它的方法应另外检查调用线程是否具有<code> RuntimePermission("modifyThreadGroup")</code>权限,如果是,则以静默方式返回。
     * 这是为了确保授予该权限的代码(例如JDK本身)被允许操作任何线程。
     * <p>
     *  如果这个方法被覆盖,那么被覆盖的方法中的第一个语句应该调用<code> super.checkAccess </code>,或者应该在覆盖的方法中放置等效的安全检查。
     * 
     * 
     * @param      g   the thread group to be checked.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to modify the thread group.
     * @exception  NullPointerException if the thread group argument is
     *             <code>null</code>.
     * @see        java.lang.ThreadGroup#destroy() destroy
     * @see        java.lang.ThreadGroup#resume() resume
     * @see        java.lang.ThreadGroup#setDaemon(boolean) setDaemon
     * @see        java.lang.ThreadGroup#setMaxPriority(int) setMaxPriority
     * @see        java.lang.ThreadGroup#stop() stop
     * @see        java.lang.ThreadGroup#suspend() suspend
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkAccess(ThreadGroup g) {
        if (g == null) {
            throw new NullPointerException("thread group can't be null");
        }
        if (g == rootGroup) {
            checkPermission(SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        } else {
            // just return
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to cause the Java Virtual Machine to
     * halt with the specified status code.
     * <p>
     * This method is invoked for the current security manager by the
     * <code>exit</code> method of class <code>Runtime</code>. A status
     * of <code>0</code> indicates success; other values indicate various
     * errors.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("exitVM."+status)</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkExit</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许使用指定的状态代码停止Java虚拟机,则抛出<code> SecurityException </code>。
     * <p>
     *  通过<code> Runtime </code>类的<code> exit </code>方法为当前安全管理器调用此方法。状态<code> 0 </code>表示成功;其他值表示各种错误。
     * <p>
     *  此方法使用<code> RuntimePermission("exitVM。"+ status)</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkExit </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      status   the exit status.
     * @exception SecurityException if the calling thread does not have
     *              permission to halt the Java Virtual Machine with
     *              the specified status.
     * @see        java.lang.Runtime#exit(int) exit
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkExit(int status) {
        checkPermission(new RuntimePermission("exitVM."+status));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to create a subprocess.
     * <p>
     * This method is invoked for the current security manager by the
     * <code>exec</code> methods of class <code>Runtime</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>FilePermission(cmd,"execute")</code> permission
     * if cmd is an absolute path, otherwise it calls
     * <code>checkPermission</code> with
     * <code>FilePermission("&lt;&lt;ALL FILES&gt;&gt;","execute")</code>.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkExec</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许创建子进程,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由<code> Runtime </code>类的<code> exec </code>方法调用当前安全管理器。
     * <p>
     * 如果cmd是绝对路径,此方法调用<code> checkPermission </code>与<code> FilePermission(cmd,"execute")</code>权限,否则调用<code>
     *  checkPermission </code> > FilePermission("&lt;&lt; ALL FILES&gt;&gt;","execute")</code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkExec </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      cmd   the specified system command.
     * @exception  SecurityException if the calling thread does not have
     *             permission to create a subprocess.
     * @exception  NullPointerException if the <code>cmd</code> argument is
     *             <code>null</code>.
     * @see     java.lang.Runtime#exec(java.lang.String)
     * @see     java.lang.Runtime#exec(java.lang.String, java.lang.String[])
     * @see     java.lang.Runtime#exec(java.lang.String[])
     * @see     java.lang.Runtime#exec(java.lang.String[], java.lang.String[])
     * @see     #checkPermission(java.security.Permission) checkPermission
     */
    public void checkExec(String cmd) {
        File f = new File(cmd);
        if (f.isAbsolute()) {
            checkPermission(new FilePermission(cmd,
                SecurityConstants.FILE_EXECUTE_ACTION));
        } else {
            checkPermission(new FilePermission("<<ALL FILES>>",
                SecurityConstants.FILE_EXECUTE_ACTION));
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to dynamic link the library code
     * specified by the string argument file. The argument is either a
     * simple library name or a complete filename.
     * <p>
     * This method is invoked for the current security manager by
     * methods <code>load</code> and <code>loadLibrary</code> of class
     * <code>Runtime</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("loadLibrary."+lib)</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkLink</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许动态链接由字符串参数文件指定的库代码,则抛出<code> SecurityException </code>。参数是一个简单的库名或一个完整的文件名。
     * <p>
     *  通过<code> Runtime </code>类的方法<code> load </code>和<code> loadLibrary </code>,调用当前安全管理器的此方法。
     * <p>
     *  此方法使用<code> RuntimePermission("loadLibrary。"+ lib)</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkLink </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      lib   the name of the library.
     * @exception  SecurityException if the calling thread does not have
     *             permission to dynamically link the library.
     * @exception  NullPointerException if the <code>lib</code> argument is
     *             <code>null</code>.
     * @see        java.lang.Runtime#load(java.lang.String)
     * @see        java.lang.Runtime#loadLibrary(java.lang.String)
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkLink(String lib) {
        if (lib == null) {
            throw new NullPointerException("library can't be null");
        }
        checkPermission(new RuntimePermission("loadLibrary."+lib));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to read from the specified file
     * descriptor.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("readFileDescriptor")</code>
     * permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkRead</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许从指定的文件描述符读取,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> RuntimePermission("readFileDescriptor")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkRead </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      fd   the system-dependent file descriptor.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the specified file descriptor.
     * @exception  NullPointerException if the file descriptor argument is
     *             <code>null</code>.
     * @see        java.io.FileDescriptor
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkRead(FileDescriptor fd) {
        if (fd == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
        checkPermission(new RuntimePermission("readFileDescriptor"));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to read the file specified by the
     * string argument.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>FilePermission(file,"read")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkRead</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     * 如果调用线程不允许读取由字符串参数指定的文件,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> FilePermission(file,"read")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkRead </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      file   the system-dependent file name.
     * @exception  SecurityException if the calling thread does not have
     *             permission to access the specified file.
     * @exception  NullPointerException if the <code>file</code> argument is
     *             <code>null</code>.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkRead(String file) {
        checkPermission(new FilePermission(file,
            SecurityConstants.FILE_READ_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * specified security context is not allowed to read the file
     * specified by the string argument. The context must be a security
     * context returned by a previous call to
     * <code>getSecurityContext</code>.
     * <p> If <code>context</code> is an instance of
     * <code>AccessControlContext</code> then the
     * <code>AccessControlContext.checkPermission</code> method will
     * be invoked with the <code>FilePermission(file,"read")</code> permission.
     * <p> If <code>context</code> is not an instance of
     * <code>AccessControlContext</code> then a
     * <code>SecurityException</code> is thrown.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkRead</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果指定的安全上下文不允许读取由字符串参数指定的文件,则抛出<code> SecurityException </code>。
     * 上下文必须是先前调用<code> getSecurityContext </code>返回的安全上下文。
     *  <p>如果<code> context </code>是<code> AccessControlContext </code>的实例,那么<code> AccessControlContext.che
     * ckPermission </code>方法将使用<code> FilePermission读")</code>权限。
     * 上下文必须是先前调用<code> getSecurityContext </code>返回的安全上下文。
     *  <p>如果<code> context </code>不是<code> AccessControlContext </code>的实例,则会抛出<code> SecurityException </code>
     * 。
     * 上下文必须是先前调用<code> getSecurityContext </code>返回的安全上下文。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkRead </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      file      the system-dependent filename.
     * @param      context   a system-dependent security context.
     * @exception  SecurityException  if the specified security context
     *             is not an instance of <code>AccessControlContext</code>
     *             (e.g., is <code>null</code>), or does not have permission
     *             to read the specified file.
     * @exception  NullPointerException if the <code>file</code> argument is
     *             <code>null</code>.
     * @see        java.lang.SecurityManager#getSecurityContext()
     * @see        java.security.AccessControlContext#checkPermission(java.security.Permission)
     */
    public void checkRead(String file, Object context) {
        checkPermission(
            new FilePermission(file, SecurityConstants.FILE_READ_ACTION),
            context);
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to write to the specified file
     * descriptor.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("writeFileDescriptor")</code>
     * permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkWrite</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许写入指定的文件描述符,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> RuntimePermission("writeFileDescriptor")</code>权限调用<code> checkPermission </code>。
     * <p>
     * 如果你重写这个方法,你应该调用<code> super.checkWrite </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      fd   the system-dependent file descriptor.
     * @exception SecurityException  if the calling thread does not have
     *             permission to access the specified file descriptor.
     * @exception  NullPointerException if the file descriptor argument is
     *             <code>null</code>.
     * @see        java.io.FileDescriptor
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkWrite(FileDescriptor fd) {
        if (fd == null) {
            throw new NullPointerException("file descriptor can't be null");
        }
        checkPermission(new RuntimePermission("writeFileDescriptor"));

    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to write to the file specified by
     * the string argument.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>FilePermission(file,"write")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkWrite</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许写入由字符串参数指定的文件,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> FilePermission(file,"write")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkWrite </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      file   the system-dependent filename.
     * @exception  SecurityException  if the calling thread does not
     *             have permission to access the specified file.
     * @exception  NullPointerException if the <code>file</code> argument is
     *             <code>null</code>.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkWrite(String file) {
        checkPermission(new FilePermission(file,
            SecurityConstants.FILE_WRITE_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to delete the specified file.
     * <p>
     * This method is invoked for the current security manager by the
     * <code>delete</code> method of class <code>File</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>FilePermission(file,"delete")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkDelete</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许删除指定的文件,则抛出<code> SecurityException </code>。
     * <p>
     *  通过<code> File </code>类的<code> delete </code>方法为当前安全管理器调用此方法。
     * <p>
     *  此方法使用<code> FilePermission(file,"delete")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkDelete </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      file   the system-dependent filename.
     * @exception  SecurityException if the calling thread does not
     *             have permission to delete the file.
     * @exception  NullPointerException if the <code>file</code> argument is
     *             <code>null</code>.
     * @see        java.io.File#delete()
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkDelete(String file) {
        checkPermission(new FilePermission(file,
            SecurityConstants.FILE_DELETE_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to open a socket connection to the
     * specified host and port number.
     * <p>
     * A port number of <code>-1</code> indicates that the calling
     * method is attempting to determine the IP address of the specified
     * host name.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>SocketPermission(host+":"+port,"connect")</code> permission if
     * the port is not equal to -1. If the port is equal to -1, then
     * it calls <code>checkPermission</code> with the
     * <code>SocketPermission(host,"resolve")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkConnect</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许打开到指定主机和端口号的套接字连接,则抛出<code> SecurityException </code>。
     * <p>
     *  端口号<code> -1 </code>表示调用方法正在尝试确定指定主机名的IP地址。
     * <p>
     * 如果端口不等于-1,则此方法使用<code> SocketPermission(host +"："+ port,"connect")</code>权限调用<code> checkPermission </code>
     * 如果端口等于-1,则它使用<code> SocketPermission(host,"resolve")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkConnect </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      host   the host name port to connect to.
     * @param      port   the protocol port to connect to.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to open a socket connection to the specified
     *               <code>host</code> and <code>port</code>.
     * @exception  NullPointerException if the <code>host</code> argument is
     *             <code>null</code>.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkConnect(String host, int port) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        if (port == -1) {
            checkPermission(new SocketPermission(host,
                SecurityConstants.SOCKET_RESOLVE_ACTION));
        } else {
            checkPermission(new SocketPermission(host+":"+port,
                SecurityConstants.SOCKET_CONNECT_ACTION));
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * specified security context is not allowed to open a socket
     * connection to the specified host and port number.
     * <p>
     * A port number of <code>-1</code> indicates that the calling
     * method is attempting to determine the IP address of the specified
     * host name.
     * <p> If <code>context</code> is not an instance of
     * <code>AccessControlContext</code> then a
     * <code>SecurityException</code> is thrown.
     * <p>
     * Otherwise, the port number is checked. If it is not equal
     * to -1, the <code>context</code>'s <code>checkPermission</code>
     * method is called with a
     * <code>SocketPermission(host+":"+port,"connect")</code> permission.
     * If the port is equal to -1, then
     * the <code>context</code>'s <code>checkPermission</code> method
     * is called with a
     * <code>SocketPermission(host,"resolve")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkConnect</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果指定的安全上下文不允许打开到指定的主机和端口号的套接字连接,则抛出<code> SecurityException </code>。
     * <p>
     *  端口号<code> -1 </code>表示调用方法正在尝试确定指定主机名的IP地址。
     *  <p>如果<code> context </code>不是<code> AccessControlContext </code>的实例,则会抛出<code> SecurityException </code>
     * 。
     *  端口号<code> -1 </code>表示调用方法正在尝试确定指定主机名的IP地址。
     * <p>
     *  否则,将检查端口号。
     * 如果不等于-1,则使用<code> SocketPermission(host +"："+ port,"connect")方法调用<code> context </code>的<code> checkP
     * ermission </code> / code>权限。
     *  否则,将检查端口号。
     * 如果端口等于-1,则使用<code> SocketPermission(host,"resolve")</code>权限调用<code> context </code>的<code> checkPerm
     * ission </code> 。
     *  否则,将检查端口号。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkConnect </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      host      the host name port to connect to.
     * @param      port      the protocol port to connect to.
     * @param      context   a system-dependent security context.
     * @exception  SecurityException if the specified security context
     *             is not an instance of <code>AccessControlContext</code>
     *             (e.g., is <code>null</code>), or does not have permission
     *             to open a socket connection to the specified
     *             <code>host</code> and <code>port</code>.
     * @exception  NullPointerException if the <code>host</code> argument is
     *             <code>null</code>.
     * @see        java.lang.SecurityManager#getSecurityContext()
     * @see        java.security.AccessControlContext#checkPermission(java.security.Permission)
     */
    public void checkConnect(String host, int port, Object context) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        if (port == -1)
            checkPermission(new SocketPermission(host,
                SecurityConstants.SOCKET_RESOLVE_ACTION),
                context);
        else
            checkPermission(new SocketPermission(host+":"+port,
                SecurityConstants.SOCKET_CONNECT_ACTION),
                context);
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to wait for a connection request on
     * the specified local port number.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>SocketPermission("localhost:"+port,"listen")</code>.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkListen</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     * 如果调用线程不允许在指定的本地端口号上等待连接请求,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> SocketPermission("localhost："+ port,"listen")</code>调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkListen </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      port   the local port.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to listen on the specified port.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkListen(int port) {
        checkPermission(new SocketPermission("localhost:"+port,
            SecurityConstants.SOCKET_LISTEN_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not permitted to accept a socket connection from
     * the specified host and port number.
     * <p>
     * This method is invoked for the current security manager by the
     * <code>accept</code> method of class <code>ServerSocket</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>SocketPermission(host+":"+port,"accept")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkAccept</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许从指定的主机和端口号接受套接字连接,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由当前安全管理器通过<code> ServerSocket </code>类的<code> accept </code>方法调用。
     * <p>
     *  此方法使用<code> SocketPermission(host +"："+ port,"accept")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkAccept </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      host   the host name of the socket connection.
     * @param      port   the port number of the socket connection.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to accept the connection.
     * @exception  NullPointerException if the <code>host</code> argument is
     *             <code>null</code>.
     * @see        java.net.ServerSocket#accept()
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkAccept(String host, int port) {
        if (host == null) {
            throw new NullPointerException("host can't be null");
        }
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        checkPermission(new SocketPermission(host+":"+port,
            SecurityConstants.SOCKET_ACCEPT_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to use
     * (join/leave/send/receive) IP multicast.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>java.net.SocketPermission(maddr.getHostAddress(),
     * "accept,connect")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkMulticast</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许使用(加入/离开/发送/接收)IP多播,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> java.net.SocketPermission(maddr.getHostAddress(),"accept,connect")</code>权限调用<code> chec
     * kPermission </code>。
     * <p>
     *  如果你重写这个方法,那么你应该在被覆盖的方法通常抛出异常的时候调用<code> super.checkMulticast </code>。
     * 
     * 
     * @param      maddr  Internet group address to be used.
     * @exception  SecurityException  if the calling thread is not allowed to
     *  use (join/leave/send/receive) IP multicast.
     * @exception  NullPointerException if the address argument is
     *             <code>null</code>.
     * @since      JDK1.1
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkMulticast(InetAddress maddr) {
        String host = maddr.getHostAddress();
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        checkPermission(new SocketPermission(host,
            SecurityConstants.SOCKET_CONNECT_ACCEPT_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to use
     * (join/leave/send/receive) IP multicast.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>java.net.SocketPermission(maddr.getHostAddress(),
     * "accept,connect")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkMulticast</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     * 如果调用线程不允许使用(加入/离开/发送/接收)IP多播,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> java.net.SocketPermission(maddr.getHostAddress(),"accept,connect")</code>权限调用<code> chec
     * kPermission </code>。
     * <p>
     *  如果你重写这个方法,那么你应该在被覆盖的方法通常抛出异常的时候调用<code> super.checkMulticast </code>。
     * 
     * 
     * @param      maddr  Internet group address to be used.
     * @param      ttl        value in use, if it is multicast send.
     * Note: this particular implementation does not use the ttl
     * parameter.
     * @exception  SecurityException  if the calling thread is not allowed to
     *  use (join/leave/send/receive) IP multicast.
     * @exception  NullPointerException if the address argument is
     *             <code>null</code>.
     * @since      JDK1.1
     * @deprecated Use #checkPermission(java.security.Permission) instead
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    public void checkMulticast(InetAddress maddr, byte ttl) {
        String host = maddr.getHostAddress();
        if (!host.startsWith("[") && host.indexOf(':') != -1) {
            host = "[" + host + "]";
        }
        checkPermission(new SocketPermission(host,
            SecurityConstants.SOCKET_CONNECT_ACCEPT_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access or modify the system
     * properties.
     * <p>
     * This method is used by the <code>getProperties</code> and
     * <code>setProperties</code> methods of class <code>System</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>PropertyPermission("*", "read,write")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkPropertiesAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     * <p>
     *
     * <p>
     *  如果调用线程不允许访问或修改系统属性,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由<code> System </code>类的<code> getProperties </code>和<code> setProperties </code>方法使用。
     * <p>
     *  此方法使用<code> PropertyPermission("*","read,write")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkPropertiesAccess </code>在被覆盖的方法通常会抛出异常。
     * <p>
     * 
     * 
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access or modify the system properties.
     * @see        java.lang.System#getProperties()
     * @see        java.lang.System#setProperties(java.util.Properties)
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkPropertiesAccess() {
        checkPermission(new PropertyPermission("*",
            SecurityConstants.PROPERTY_RW_ACTION));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access the system property with
     * the specified <code>key</code> name.
     * <p>
     * This method is used by the <code>getProperty</code> method of
     * class <code>System</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>PropertyPermission(key, "read")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkPropertyAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许访问具有指定的<code>键</code>名称的系统属性,则会抛出<code> SecurityException </code>。
     * <p>
     *  此方法由<code> System </code>类的<code> getProperty </code>方法使用。
     * <p>
     *  此方法使用<code> PropertyPermission(key,"read")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkPropertyAccess </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @param      key   a system property key.
     *
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the specified system property.
     * @exception  NullPointerException if the <code>key</code> argument is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     *
     * @see        java.lang.System#getProperty(java.lang.String)
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkPropertyAccess(String key) {
        checkPermission(new PropertyPermission(key,
            SecurityConstants.PROPERTY_READ_ACTION));
    }

    /**
     * Returns <code>false</code> if the calling
     * thread is not trusted to bring up the top-level window indicated
     * by the <code>window</code> argument. In this case, the caller can
     * still decide to show the window, but the window should include
     * some sort of visual warning. If the method returns
     * <code>true</code>, then the window can be shown without any
     * special restrictions.
     * <p>
     * See class <code>Window</code> for more information on trusted and
     * untrusted windows.
     * <p>
     * This method calls
     * <code>checkPermission</code> with the
     * <code>AWTPermission("showWindowWithoutWarningBanner")</code> permission,
     * and returns <code>true</code> if a SecurityException is not thrown,
     * otherwise it returns <code>false</code>.
     * In the case of subset Profiles of Java SE that do not include the
     * {@code java.awt} package, {@code checkPermission} is instead called
     * to check the permission {@code java.security.AllPermission}.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkTopLevelWindow</code>
     * at the point the overridden method would normally return
     * <code>false</code>, and the value of
     * <code>super.checkTopLevelWindow</code> should
     * be returned.
     *
     * <p>
     * 如果调用线程不受信任,则返回<code> false </code>,以显示由<code> window </code>参数指示的顶级窗口。
     * 在这种情况下,调用者仍然可以决定显示窗口,但窗口应该包括某种视觉警告。如果方法返回<code> true </code>,那么可以显示窗口而没有任何特殊限制。
     * <p>
     *  有关可信和不可信窗口的更多信息,请参阅类<code> Window </code>。
     * <p>
     *  此方法使用<code> AWTPermission("showWindowWithoutWarningBanner")</code>权限调用<code> checkPermission </code>
     * ,如果未抛出SecurityException,则返回<code> true </code>,否则返回<code > false </code>。
     * 在Java SE的子集Profiles不包括{@code java.awt}包的情况下,将调用{@code checkPermission}来检查权限{@code java.security.AllPermission}
     * 。
     * <p>
     *  如果你重写这个方法,那么你应该调用<code> super.checkTopLevelWindow </code>,在重写的方法通常返回<code> false </code>和<code> supe
     * r的值。
     * 应返回checkTopLevelWindow </code>。
     * 
     * 
     * @param      window   the new window that is being created.
     * @return     <code>true</code> if the calling thread is trusted to put up
     *             top-level windows; <code>false</code> otherwise.
     * @exception  NullPointerException if the <code>window</code> argument is
     *             <code>null</code>.
     * @deprecated The dependency on {@code AWTPermission} creates an
     *             impediment to future modularization of the Java platform.
     *             Users of this method should instead invoke
     *             {@link #checkPermission} directly.
     *             This method will be changed in a future release to check
     *             the permission {@code java.security.AllPermission}.
     * @see        java.awt.Window
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    public boolean checkTopLevelWindow(Object window) {
        if (window == null) {
            throw new NullPointerException("window can't be null");
        }
        Permission perm = SecurityConstants.AWT.TOPLEVEL_WINDOW_PERMISSION;
        if (perm == null) {
            perm = SecurityConstants.ALL_PERMISSION;
        }
        try {
            checkPermission(perm);
            return true;
        } catch (SecurityException se) {
            // just return false
        }
        return false;
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to initiate a print job request.
     * <p>
     * This method calls
     * <code>checkPermission</code> with the
     * <code>RuntimePermission("queuePrintJob")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkPrintJobAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     * <p>
     *
     * <p>
     *  如果调用线程不允许启动打印作业请求,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> RuntimePermission("queuePrintJob")</code>权限调用<code> checkPermission </code>。
     * <p>
     * 如果你重写这个方法,你应该调用<code> super.checkPrintJobAccess </code>在被覆盖的方法通常会抛出异常。
     * <p>
     * 
     * 
     * @exception  SecurityException  if the calling thread does not have
     *             permission to initiate a print job request.
     * @since   JDK1.1
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkPrintJobAccess() {
        checkPermission(new RuntimePermission("queuePrintJob"));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access the system clipboard.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>AWTPermission("accessClipboard")</code>
     * permission.
     * In the case of subset Profiles of Java SE that do not include the
     * {@code java.awt} package, {@code checkPermission} is instead called
     * to check the permission {@code java.security.AllPermission}.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkSystemClipboardAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许访问系统剪贴板,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> AWTPermission("accessClipboard")</code>权限调用<code> checkPermission </code>。
     * 在Java SE的子集Profiles不包括{@code java.awt}包的情况下,将调用{@code checkPermission}来检查权限{@code java.security.AllPermission}
     * 。
     *  此方法使用<code> AWTPermission("accessClipboard")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkSystemClipboardAccess </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @since   JDK1.1
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the system clipboard.
     * @deprecated The dependency on {@code AWTPermission} creates an
     *             impediment to future modularization of the Java platform.
     *             Users of this method should instead invoke
     *             {@link #checkPermission} directly.
     *             This method will be changed in a future release to check
     *             the permission {@code java.security.AllPermission}.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    public void checkSystemClipboardAccess() {
        Permission perm = SecurityConstants.AWT.ACCESS_CLIPBOARD_PERMISSION;
        if (perm == null) {
            perm = SecurityConstants.ALL_PERMISSION;
        }
        checkPermission(perm);
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access the AWT event queue.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>AWTPermission("accessEventQueue")</code> permission.
     * In the case of subset Profiles of Java SE that do not include the
     * {@code java.awt} package, {@code checkPermission} is instead called
     * to check the permission {@code java.security.AllPermission}.
     *
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkAwtEventQueueAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  如果调用线程不允许访问AWT事件队列,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法使用<code> AWTPermission("accessEventQueue")</code>权限调用<code> checkPermission </code>。
     * 在Java SE的子集Profiles不包括{@code java.awt}包的情况下,将调用{@code checkPermission}来检查权限{@code java.security.AllPermission}
     * 。
     *  此方法使用<code> AWTPermission("accessEventQueue")</code>权限调用<code> checkPermission </code>。
     * 
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkAwtEventQueueAccess </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @since   JDK1.1
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the AWT event queue.
     * @deprecated The dependency on {@code AWTPermission} creates an
     *             impediment to future modularization of the Java platform.
     *             Users of this method should instead invoke
     *             {@link #checkPermission} directly.
     *             This method will be changed in a future release to check
     *             the permission {@code java.security.AllPermission}.
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    public void checkAwtEventQueueAccess() {
        Permission perm = SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION;
        if (perm == null) {
            perm = SecurityConstants.ALL_PERMISSION;
        }
        checkPermission(perm);
    }

    /*
     * We have an initial invalid bit (initially false) for the class
     * variables which tell if the cache is valid.  If the underlying
     * java.security.Security property changes via setProperty(), the
     * Security class uses reflection to change the variable and thus
     * invalidate the cache.
     *
     * Locking is handled by synchronization to the
     * packageAccessLock/packageDefinitionLock objects.  They are only
     * used in this class.
     *
     * Note that cache invalidation as a result of the property change
     * happens without using these locks, so there may be a delay between
     * when a thread updates the property and when other threads updates
     * the cache.
     * <p>
     * 我们有一个初始的无效位(最初为false)用于告诉缓存是否有效的类变量。
     * 如果基础java.security.Security属性通过setProperty()更改,则Security类使用反射来更改变量,从而使缓存失效。
     * 
     *  锁定是通过同步到packageAccessLock / packageDefinitionLock对象来处理的。它们只用在这个类中。
     * 
     *  请注意,由于属性更改而导致的高速缓存失效不使用这些锁,因此在线程更新属性和其他线程更新高速缓存时之间可能会有延迟。
     * 
     */
    private static boolean packageAccessValid = false;
    private static String[] packageAccess;
    private static final Object packageAccessLock = new Object();

    private static boolean packageDefinitionValid = false;
    private static String[] packageDefinition;
    private static final Object packageDefinitionLock = new Object();

    private static String[] getPackages(String p) {
        String packages[] = null;
        if (p != null && !p.equals("")) {
            java.util.StringTokenizer tok =
                new java.util.StringTokenizer(p, ",");
            int n = tok.countTokens();
            if (n > 0) {
                packages = new String[n];
                int i = 0;
                while (tok.hasMoreElements()) {
                    String s = tok.nextToken().trim();
                    packages[i++] = s;
                }
            }
        }

        if (packages == null)
            packages = new String[0];
        return packages;
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access the package specified by
     * the argument.
     * <p>
     * This method is used by the <code>loadClass</code> method of class
     * loaders.
     * <p>
     * This method first gets a list of
     * restricted packages by obtaining a comma-separated list from
     * a call to
     * <code>java.security.Security.getProperty("package.access")</code>,
     * and checks to see if <code>pkg</code> starts with or equals
     * any of the restricted packages. If it does, then
     * <code>checkPermission</code> gets called with the
     * <code>RuntimePermission("accessClassInPackage."+pkg)</code>
     * permission.
     * <p>
     * If this method is overridden, then
     * <code>super.checkPackageAccess</code> should be called
     * as the first line in the overridden method.
     *
     * <p>
     *  如果调用线程不允许访问参数指定的包,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由类装载器的<code> loadClass </code>方法使用。
     * <p>
     *  此方法首先通过从调用<code> java.security.Security.getProperty("package.access")</code>获取逗号分隔列表来获取受限包的列表,并检查<code>
     *  pkg </code>开头为或等于任何受限包。
     * 如果是,则使用<code> RuntimePermission("accessClassInPackage。
     * "+ pkg)</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果覆盖此方法,则应将<code> super.checkPackageAccess </code>调用为覆盖方法中的第一行。
     * 
     * 
     * @param      pkg   the package name.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to access the specified package.
     * @exception  NullPointerException if the package name argument is
     *             <code>null</code>.
     * @see        java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     *  loadClass
     * @see        java.security.Security#getProperty getProperty
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkPackageAccess(String pkg) {
        if (pkg == null) {
            throw new NullPointerException("package name can't be null");
        }

        String[] pkgs;
        synchronized (packageAccessLock) {
            /*
             * Do we need to update our property array?
             * <p>
             *  我们需要更新属性数组吗?
             * 
             */
            if (!packageAccessValid) {
                String tmpPropertyStr =
                    AccessController.doPrivileged(
                        new PrivilegedAction<String>() {
                            public String run() {
                                return java.security.Security.getProperty(
                                    "package.access");
                            }
                        }
                    );
                packageAccess = getPackages(tmpPropertyStr);
                packageAccessValid = true;
            }

            // Using a snapshot of packageAccess -- don't care if static field
            // changes afterwards; array contents won't change.
            pkgs = packageAccess;
        }

        /*
         * Traverse the list of packages, check for any matches.
         * <p>
         *  遍历包的列表,检查任何匹配。
         * 
         */
        for (int i = 0; i < pkgs.length; i++) {
            if (pkg.startsWith(pkgs[i]) || pkgs[i].equals(pkg + ".")) {
                checkPermission(
                    new RuntimePermission("accessClassInPackage."+pkg));
                break;  // No need to continue; only need to check this once
            }
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to define classes in the package
     * specified by the argument.
     * <p>
     * This method is used by the <code>loadClass</code> method of some
     * class loaders.
     * <p>
     * This method first gets a list of restricted packages by
     * obtaining a comma-separated list from a call to
     * <code>java.security.Security.getProperty("package.definition")</code>,
     * and checks to see if <code>pkg</code> starts with or equals
     * any of the restricted packages. If it does, then
     * <code>checkPermission</code> gets called with the
     * <code>RuntimePermission("defineClassInPackage."+pkg)</code>
     * permission.
     * <p>
     * If this method is overridden, then
     * <code>super.checkPackageDefinition</code> should be called
     * as the first line in the overridden method.
     *
     * <p>
     * 如果调用线程不允许在参数指定的包中定义类,则抛出<code> SecurityException </code>。
     * <p>
     *  此方法由一些类加载器的<code> loadClass </code>方法使用。
     * <p>
     *  此方法首先通过从调用<code> java.security.Security.getProperty("package.definition")</code>获取逗号分隔列表来获取受限包的列表,并检
     * 查<code> pkg </code>开头为或等于任何受限包。
     * 如果是,则使用<code> RuntimePermission("defineClassInPackage。
     * "+ pkg)</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果这个方法被覆盖,那么<code> super.checkPackageDefinition </code>应该被调用作为覆盖方法中的第一行。
     * 
     * 
     * @param      pkg   the package name.
     * @exception  SecurityException  if the calling thread does not have
     *             permission to define classes in the specified package.
     * @see        java.lang.ClassLoader#loadClass(java.lang.String, boolean)
     * @see        java.security.Security#getProperty getProperty
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkPackageDefinition(String pkg) {
        if (pkg == null) {
            throw new NullPointerException("package name can't be null");
        }

        String[] pkgs;
        synchronized (packageDefinitionLock) {
            /*
             * Do we need to update our property array?
             * <p>
             *  我们需要更新属性数组吗?
             * 
             */
            if (!packageDefinitionValid) {
                String tmpPropertyStr =
                    AccessController.doPrivileged(
                        new PrivilegedAction<String>() {
                            public String run() {
                                return java.security.Security.getProperty(
                                    "package.definition");
                            }
                        }
                    );
                packageDefinition = getPackages(tmpPropertyStr);
                packageDefinitionValid = true;
            }
            // Using a snapshot of packageDefinition -- don't care if static
            // field changes afterwards; array contents won't change.
            pkgs = packageDefinition;
        }

        /*
         * Traverse the list of packages, check for any matches.
         * <p>
         *  遍历包的列表,检查任何匹配。
         * 
         */
        for (int i = 0; i < pkgs.length; i++) {
            if (pkg.startsWith(pkgs[i]) || pkgs[i].equals(pkg + ".")) {
                checkPermission(
                    new RuntimePermission("defineClassInPackage."+pkg));
                break; // No need to continue; only need to check this once
            }
        }
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to set the socket factory used by
     * <code>ServerSocket</code> or <code>Socket</code>, or the stream
     * handler factory used by <code>URL</code>.
     * <p>
     * This method calls <code>checkPermission</code> with the
     * <code>RuntimePermission("setFactory")</code> permission.
     * <p>
     * If you override this method, then you should make a call to
     * <code>super.checkSetFactory</code>
     * at the point the overridden method would normally throw an
     * exception.
     * <p>
     *
     * <p>
     *  如果调用线程不允许设置<code> ServerSocket </code>或<code> Socket </code>使用的套接字工厂,或者</code>使用的流处理程序工厂,则抛出<code> S
     * ecurityException </code>代码> URL </code>。
     * <p>
     *  此方法使用<code> RuntimePermission("setFactory")</code>权限调用<code> checkPermission </code>。
     * <p>
     *  如果你重写这个方法,你应该调用<code> super.checkSetFactory </code>在被覆盖的方法通常会抛出异常。
     * <p>
     * 
     * 
     * @exception  SecurityException  if the calling thread does not have
     *             permission to specify a socket factory or a stream
     *             handler factory.
     *
     * @see        java.net.ServerSocket#setSocketFactory(java.net.SocketImplFactory) setSocketFactory
     * @see        java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory) setSocketImplFactory
     * @see        java.net.URL#setURLStreamHandlerFactory(java.net.URLStreamHandlerFactory) setURLStreamHandlerFactory
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkSetFactory() {
        checkPermission(new RuntimePermission("setFactory"));
    }

    /**
     * Throws a <code>SecurityException</code> if the
     * calling thread is not allowed to access members.
     * <p>
     * The default policy is to allow access to PUBLIC members, as well
     * as access to classes that have the same class loader as the caller.
     * In all other cases, this method calls <code>checkPermission</code>
     * with the <code>RuntimePermission("accessDeclaredMembers")
     * </code> permission.
     * <p>
     * If this method is overridden, then a call to
     * <code>super.checkMemberAccess</code> cannot be made,
     * as the default implementation of <code>checkMemberAccess</code>
     * relies on the code being checked being at a stack depth of
     * 4.
     *
     * <p>
     *  如果调用线程不允许访问成员,则抛出<code> SecurityException </code>。
     * <p>
     * 默认策略是允许访问PUBLIC成员,以及访问具有与调用者相同的类加载器的类。
     * 在所有其他情况下,此方法使用<code> RuntimePermission("accessDeclaredMembers")</code>权限调用<code> checkPermission </code>
     * 。
     * 默认策略是允许访问PUBLIC成员,以及访问具有与调用者相同的类加载器的类。
     * <p>
     *  如果这个方法被覆盖,则不能调用<code> super.checkMemberAccess </code>,因为<code> checkMemberAccess </code>的默认实现依赖于被检查的
     * 代码处于堆栈深度4 。
     * 
     * 
     * @param clazz the class that reflection is to be performed on.
     *
     * @param which type of access, PUBLIC or DECLARED.
     *
     * @exception  SecurityException if the caller does not have
     *             permission to access members.
     * @exception  NullPointerException if the <code>clazz</code> argument is
     *             <code>null</code>.
     *
     * @deprecated This method relies on the caller being at a stack depth
     *             of 4 which is error-prone and cannot be enforced by the runtime.
     *             Users of this method should instead invoke {@link #checkPermission}
     *             directly.  This method will be changed in a future release
     *             to check the permission {@code java.security.AllPermission}.
     *
     * @see java.lang.reflect.Member
     * @since JDK1.1
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    @Deprecated
    @CallerSensitive
    public void checkMemberAccess(Class<?> clazz, int which) {
        if (clazz == null) {
            throw new NullPointerException("class can't be null");
        }
        if (which != Member.PUBLIC) {
            Class<?> stack[] = getClassContext();
            /*
             * stack depth of 4 should be the caller of one of the
             * methods in java.lang.Class that invoke checkMember
             * access. The stack should look like:
             *
             * someCaller                        [3]
             * java.lang.Class.someReflectionAPI [2]
             * java.lang.Class.checkMemberAccess [1]
             * SecurityManager.checkMemberAccess [0]
             *
             * <p>
             *  堆栈深度为4应该是java.lang.Class中调用checkMember访问的方法之一的调用者。堆栈应该看起来像：
             * 
             *  someCaller [3] java.lang.Class.someReflectionAPI [2] java.lang.Class.checkMemberAccess [1] SecurityM
             * anager.checkMemberAccess [0]。
             * 
             */
            if ((stack.length<4) ||
                (stack[3].getClassLoader() != clazz.getClassLoader())) {
                checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
            }
        }
    }

    /**
     * Determines whether the permission with the specified permission target
     * name should be granted or denied.
     *
     * <p> If the requested permission is allowed, this method returns
     * quietly. If denied, a SecurityException is raised.
     *
     * <p> This method creates a <code>SecurityPermission</code> object for
     * the given permission target name and calls <code>checkPermission</code>
     * with it.
     *
     * <p> See the documentation for
     * <code>{@link java.security.SecurityPermission}</code> for
     * a list of possible permission target names.
     *
     * <p> If you override this method, then you should make a call to
     * <code>super.checkSecurityAccess</code>
     * at the point the overridden method would normally throw an
     * exception.
     *
     * <p>
     *  确定是否应授予或拒绝具有指定的权限目标名称的权限。
     * 
     *  <p>如果允许所请求的权限,此方法会静静返回。如果拒绝,则引发SecurityException。
     * 
     *  <p>此方法为给定的权限目标名称创建一个<code> SecurityPermission </code>对象,并使用它调用<code> checkPermission </code>。
     * 
     *  <p>有关可能的权限目标名称列表,请参阅<code> {@ link java.security.SecurityPermission} </code>的文档。
     * 
     * @param target the target name of the <code>SecurityPermission</code>.
     *
     * @exception SecurityException if the calling thread does not have
     * permission for the requested access.
     * @exception NullPointerException if <code>target</code> is null.
     * @exception IllegalArgumentException if <code>target</code> is empty.
     *
     * @since   JDK1.1
     * @see        #checkPermission(java.security.Permission) checkPermission
     */
    public void checkSecurityAccess(String target) {
        checkPermission(new SecurityPermission(target));
    }

    private native Class<?> currentLoadedClass0();

    /**
     * Returns the thread group into which to instantiate any new
     * thread being created at the time this is being called.
     * By default, it returns the thread group of the current
     * thread. This should be overridden by a specific security
     * manager to return the appropriate thread group.
     *
     * <p>
     * 
     *  <p>如果你重写这个方法,你应该调用<code> super.checkSecurityAccess </code>在被覆盖的方法通常会抛出异常。
     * 
     * 
     * @return  ThreadGroup that new threads are instantiated into
     * @since   JDK1.1
     * @see     java.lang.ThreadGroup
     */
    public ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }

}
