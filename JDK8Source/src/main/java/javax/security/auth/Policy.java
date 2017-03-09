/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth;

import java.security.Security;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Objects;
import sun.security.util.Debug;

/**
 * <p> This is an abstract class for representing the system policy for
 * Subject-based authorization.  A subclass implementation
 * of this class provides a means to specify a Subject-based
 * access control {@code Policy}.
 *
 * <p> A {@code Policy} object can be queried for the set of
 * Permissions granted to code running as a
 * {@code Principal} in the following manner:
 *
 * <pre>
 *      policy = Policy.getPolicy();
 *      PermissionCollection perms = policy.getPermissions(subject,
 *                                                      codeSource);
 * </pre>
 *
 * The {@code Policy} object consults the local policy and returns
 * and appropriate {@code Permissions} object with the
 * Permissions granted to the Principals associated with the
 * provided <i>subject</i>, and granted to the code specified
 * by the provided <i>codeSource</i>.
 *
 * <p> A {@code Policy} contains the following information.
 * Note that this example only represents the syntax for the default
 * {@code Policy} implementation. Subclass implementations of this class
 * may implement alternative syntaxes and may retrieve the
 * {@code Policy} from any source such as files, databases,
 * or servers.
 *
 * <p> Each entry in the {@code Policy} is represented as
 * a <b><i>grant</i></b> entry.  Each <b><i>grant</i></b> entry
 * specifies a codebase, code signers, and Principals triplet,
 * as well as the Permissions granted to that triplet.
 *
 * <pre>
 *      grant CodeBase ["URL"], Signedby ["signers"],
 *            Principal [Principal_Class] "Principal_Name" {
 *          Permission Permission_Class ["Target_Name"]
 *                                      [, "Permission_Actions"]
 *                                      [, signedBy "SignerName"];
 *      };
 * </pre>
 *
 * The CodeBase and Signedby components of the triplet name/value pairs
 * are optional.  If they are not present, then any any codebase will match,
 * and any signer (including unsigned code) will match.
 * For Example,
 *
 * <pre>
 *      grant CodeBase "foo.com", Signedby "foo",
 *            Principal com.sun.security.auth.SolarisPrincipal "duke" {
 *          permission java.io.FilePermission "/home/duke", "read, write";
 *      };
 * </pre>
 *
 * This <b><i>grant</i></b> entry specifies that code from "foo.com",
 * signed by "foo', and running as a {@code SolarisPrincipal} with the
 * name, duke, has one {@code Permission}.  This {@code Permission}
 * permits the executing code to read and write files in the directory,
 * "/home/duke".
 *
 * <p> To "run" as a particular {@code Principal},
 * code invokes the {@code Subject.doAs(subject, ...)} method.
 * After invoking that method, the code runs as all the Principals
 * associated with the specified {@code Subject}.
 * Note that this {@code Policy} (and the Permissions
 * granted in this {@code Policy}) only become effective
 * after the call to {@code Subject.doAs} has occurred.
 *
 * <p> Multiple Principals may be listed within one <b><i>grant</i></b> entry.
 * All the Principals in the grant entry must be associated with
 * the {@code Subject} provided to {@code Subject.doAs}
 * for that {@code Subject} to be granted the specified Permissions.
 *
 * <pre>
 *      grant Principal com.sun.security.auth.SolarisPrincipal "duke",
 *            Principal com.sun.security.auth.SolarisNumericUserPrincipal "0" {
 *          permission java.io.FilePermission "/home/duke", "read, write";
 *          permission java.net.SocketPermission "duke.com", "connect";
 *      };
 * </pre>
 *
 * This entry grants any code running as both "duke" and "0"
 * permission to read and write files in duke's home directory,
 * as well as permission to make socket connections to "duke.com".
 *
 * <p> Note that non Principal-based grant entries are not permitted
 * in this {@code Policy}.  Therefore, grant entries such as:
 *
 * <pre>
 *      grant CodeBase "foo.com", Signedby "foo" {
 *          permission java.io.FilePermission "/tmp/scratch", "read, write";
 *      };
 * </pre>
 *
 * are rejected.  Such permission must be listed in the
 * {@code java.security.Policy}.
 *
 * <p> The default {@code Policy} implementation can be changed by
 * setting the value of the {@code auth.policy.provider} security property to
 * the fully qualified name of the desired {@code Policy} implementation class.
 *
 * <p>
 *  <p>这是一个抽象类,用于表示基于主题的授权的系统策略。这个类的子类实现提供了一种方法来指定基于主体的访问控制{@code Policy}。
 * 
 *  <p>可以通过以下方式查询{@code Policy}对象的一组授予运行为{@code Principal}的代码的权限：
 * 
 * <pre>
 *  policy = Policy.getPolicy(); PermissionCollection perms = policy.getPermissions(subject,codeSource);
 * 。
 * </pre>
 * 
 *  {@code Policy}对象查阅本地策略,并返回并具有授予与所提供的<i>主题</i>关联的主体的权限的适当{@code Permissions}对象,并授予由所提供的<i> codeSource
 *  </i>。
 * 
 *  <p> {@code Policy}包含以下信息。请注意,此示例仅表示默认{@code Policy}实现的语法。
 * 这个类的子类实现可以实现替代语法,并且可以从任何源(例如文件,数据库或服务器)检索{@code Policy}。
 * 
 *  <p> {@code Policy}中的每个条目都表示为<b> <i> grant </i> </b>条目。
 * 每个<b> <i> grant </i> </b>条目指定一个代码库,代码签名者和Principals三元组,以及授予该三元组的权限。
 * 
 * <pre>
 * 授权代码"["URL"],签名者["签名者"],主体[Principal_Class]"Principal_Name"{权限Permission_Class ["Target_Name"] [,"Permission_Actions"] [,signedBy"SignerName"]; }
 * ;。
 * </pre>
 * 
 *  三元组名称/值对的CodeBase和Signedby组件是可选的。如果它们不存在,则任何任何代码库将匹配,并且任何签名者(包括未签名的代码)将匹配。例如,
 * 
 * <pre>
 *  授予CodeBase"foo.com",Signedby"foo",主体com.sun.security.auth.SolarisPrincipal"duke"{permission java.io.FilePermission"/ home / duke","read,write"; }
 * ;。
 * </pre>
 * 
 *  此<b> <i> grant </i> </b>条目指定来自"foo.com"的代码,由"foo"签名,并以名称duke运行为{@code SolarisPrincipal} {@code Permission}
 * 。
 * 此{@code Permission}允许执行代码读取和写入目录"/ home / duke"中的文件。
 * 
 *  <p>要以特定的{@code Principal}执行「run」,代码会调用{@code Subject.doAs(subject,...)}方法。
 * 调用该方法后,代码将作为与指定的{@code Subject}关联的所有主体运行。
 * 请注意,此{@code Policy}(以及此{@code Policy}中授予的权限)只有在发生对{@code Subject.doAs}的调用后才会生效。
 * 
 *  <p>多个校长可能列在一个<b> <i>授权</i> </b>条目中。
 * 授权项目中的所有负责人必须与提供给{@code Subject.doAs}的{@code Subject}相关联,{@code Subject}才会授予指定的权限。
 * 
 * <pre>
 * grant主体com.sun.security.auth.SolarisPrincipal"duke",Principal com.sun.security.auth.SolarisNumericUse
 * rPrincipal"0"{permission java.io.FilePermission"/ home / duke","read,write"; permission java.net.SocketPermission"duke.com","connect"; }
 * ;。
 * </pre>
 * 
 *  此条目授予任何运行为"duke"和"0"权限的代码,以读取和写入duke的主目录中的文件,以及将套接字连接到"duke.com"的权限。
 * 
 * @deprecated  as of JDK version 1.4 -- Replaced by java.security.Policy.
 *              java.security.Policy has a method:
 * <pre>
 *      public PermissionCollection getPermissions
 *          (java.security.ProtectionDomain pd)
 *
 * </pre>
 * and ProtectionDomain has a constructor:
 * <pre>
 *      public ProtectionDomain
 *          (CodeSource cs,
 *           PermissionCollection permissions,
 *           ClassLoader loader,
 *           Principal[] principals)
 * </pre>
 *
 * These two APIs provide callers the means to query the
 * Policy for Principal-based Permission entries.
 *
 * @see java.security.Security security properties
 */
@Deprecated
public abstract class Policy {

    private static Policy policy;
    private final static String AUTH_POLICY =
        "sun.security.provider.AuthPolicyFile";

    private final java.security.AccessControlContext acc =
            java.security.AccessController.getContext();

    // true if a custom (not AUTH_POLICY) system-wide policy object is set
    private static boolean isCustomPolicy;

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     * 
     *  <p>请注意,此{@code Policy}中不允许使用非基于主体的授权条目。因此,授予条目如：
     * 
     * <pre>
     *  grant codeBase"foo.com",Signedby"foo"{permission java.io.FilePermission"/ tmp / scratch","read,write"; }
     * ;。
     * </pre>
     * 
     *  被拒绝。此类权限必须列在{@code java.security.Policy}中。
     * 
     *  <p>默认的{@code Policy}实现可以通过将{@code auth.policy.provider}安全属性的值设置为所需的{@code Policy}实现类的完全限定名称来更改。
     * 
     */
    protected Policy() { }

    /**
     * Returns the installed Policy object.
     * This method first calls
     * {@code SecurityManager.checkPermission} with the
     * {@code AuthPermission("getPolicy")} permission
     * to ensure the caller has permission to get the Policy object.
     *
     * <p>
     *
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     * 
     * @return the installed Policy.  The return value cannot be
     *          {@code null}.
     *
     * @exception java.lang.SecurityException if the current thread does not
     *      have permission to get the Policy object.
     *
     * @see #setPolicy
     */
    public static Policy getPolicy() {
        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new AuthPermission("getPolicy"));
        return getPolicyNoCheck();
    }

    /**
     * Returns the installed Policy object, skipping the security check.
     *
     * <p>
     *  返回已安装的Policy对象。
     * 此方法首先使用{@code AuthPermission("getPolicy")}权限调用{@code SecurityManager.checkPermission}以确保调用者具有获取Policy
     * 对象的权限。
     *  返回已安装的Policy对象。
     * 
     * <p>
     * 
     * 
     * @return the installed Policy.
     *
     */
    static Policy getPolicyNoCheck() {
        if (policy == null) {

            synchronized(Policy.class) {

                if (policy == null) {
                    String policy_class = null;
                    policy_class = AccessController.doPrivileged
                        (new PrivilegedAction<String>() {
                        public String run() {
                            return java.security.Security.getProperty
                                ("auth.policy.provider");
                        }
                    });
                    if (policy_class == null) {
                        policy_class = AUTH_POLICY;
                    }

                    try {
                        final String finalClass = policy_class;

                        Policy untrustedImpl = AccessController.doPrivileged(
                                new PrivilegedExceptionAction<Policy>() {
                                    public Policy run() throws ClassNotFoundException,
                                            InstantiationException,
                                            IllegalAccessException {
                                        Class<? extends Policy> implClass = Class.forName(
                                                finalClass, false,
                                                Thread.currentThread().getContextClassLoader()
                                        ).asSubclass(Policy.class);
                                        return implClass.newInstance();
                                    }
                                });
                        AccessController.doPrivileged(
                                new PrivilegedExceptionAction<Void>() {
                                    public Void run() {
                                        setPolicy(untrustedImpl);
                                        isCustomPolicy = !finalClass.equals(AUTH_POLICY);
                                        return null;
                                    }
                                }, Objects.requireNonNull(untrustedImpl.acc)
                        );
                    } catch (Exception e) {
                        throw new SecurityException
                                (sun.security.util.ResourcesMgr.getString
                                ("unable.to.instantiate.Subject.based.policy"));
                    }
                }
            }
        }
        return policy;
    }


    /**
     * Sets the system-wide Policy object. This method first calls
     * {@code SecurityManager.checkPermission} with the
     * {@code AuthPermission("setPolicy")}
     * permission to ensure the caller has permission to set the Policy.
     *
     * <p>
     *
     * <p>
     *  返回已安装的Policy对象,跳过安全检查。
     * 
     * 
     * @param policy the new system Policy object.
     *
     * @exception java.lang.SecurityException if the current thread does not
     *          have permission to set the Policy.
     *
     * @see #getPolicy
     */
    public static void setPolicy(Policy policy) {
        java.lang.SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkPermission(new AuthPermission("setPolicy"));
        Policy.policy = policy;
        // all non-null policy objects are assumed to be custom
        isCustomPolicy = policy != null ? true : false;
    }

    /**
     * Returns true if a custom (not AUTH_POLICY) system-wide policy object
     * has been set or installed. This method is called by
     * SubjectDomainCombiner to provide backwards compatibility for
     * developers that provide their own javax.security.auth.Policy
     * implementations.
     *
     * <p>
     * 设置系统范围的Policy对象。
     * 此方法首先使用{@code AuthPermission("setPolicy")}权限调用{@code SecurityManager.checkPermission},以确保调用者有权设置策略。
     * 
     * <p>
     * 
     * 
     * @return true if a custom (not AUTH_POLICY) system-wide policy object
     * has been set; false otherwise
     */
    static boolean isCustomPolicySet(Debug debug) {
        if (policy != null) {
            if (debug != null && isCustomPolicy) {
                debug.println("Providing backwards compatibility for " +
                              "javax.security.auth.policy implementation: " +
                              policy.toString());
            }
            return isCustomPolicy;
        }
        // check if custom policy has been set using auth.policy.provider prop
        String policyClass = java.security.AccessController.doPrivileged
            (new java.security.PrivilegedAction<String>() {
                public String run() {
                    return Security.getProperty("auth.policy.provider");
                }
        });
        if (policyClass != null && !policyClass.equals(AUTH_POLICY)) {
            if (debug != null) {
                debug.println("Providing backwards compatibility for " +
                              "javax.security.auth.policy implementation: " +
                              policyClass);
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieve the Permissions granted to the Principals associated with
     * the specified {@code CodeSource}.
     *
     * <p>
     *
     * <p>
     *  如果已设置或安装了自定义(非AUTH_POLICY)系统范围的策略对象,则返回true。
     * 此方法由SubjectDomainCombiner调用,为提供自己的javax.security.auth.Policy实现的开发人员提供向后兼容性。
     * 
     * 
     * @param subject the {@code Subject}
     *                  whose associated Principals,
     *                  in conjunction with the provided
     *                  {@code CodeSource}, determines the Permissions
     *                  returned by this method.  This parameter
     *                  may be {@code null}. <p>
     *
     * @param cs the code specified by its {@code CodeSource}
     *                  that determines, in conjunction with the provided
     *                  {@code Subject}, the Permissions
     *                  returned by this method.  This parameter may be
     *                  {@code null}.
     *
     * @return the Collection of Permissions granted to all the
     *                  {@code Subject} and code specified in
     *                  the provided <i>subject</i> and <i>cs</i>
     *                  parameters.
     */
    public abstract java.security.PermissionCollection getPermissions
                                        (Subject subject,
                                        java.security.CodeSource cs);

    /**
     * Refresh and reload the Policy.
     *
     * <p>This method causes this object to refresh/reload its current
     * Policy. This is implementation-dependent.
     * For example, if the Policy object is stored in
     * a file, calling {@code refresh} will cause the file to be re-read.
     *
     * <p>
     *
     * <p>
     *  检索授予与指定{@code CodeSource}相关联的负责人的权限。
     * 
     * <p>
     * 
     * 
     * @exception SecurityException if the caller does not have permission
     *                          to refresh the Policy.
     */
    public abstract void refresh();
}
