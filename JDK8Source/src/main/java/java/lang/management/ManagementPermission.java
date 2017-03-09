/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.management;

/**
 * The permission which the SecurityManager will check when code
 * that is running with a SecurityManager calls methods defined
 * in the management interface for the Java platform.
 * <P>
 * The following table
 * provides a summary description of what the permission allows,
 * and discusses the risks of granting code the permission.
 *
 * <table border=1 cellpadding=5 summary="Table shows permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>control</td>
 *   <td>Ability to control the runtime characteristics of the Java virtual
 *       machine, for example, enabling and disabling the verbose output for
 *       the class loading or memory system, setting the threshold of a memory
 *       pool, and enabling and disabling the thread contention monitoring
 *       support. Some actions controlled by this permission can disclose
 *       information about the running application, like the -verbose:class
 *       flag.
 *   </td>
 *   <td>This allows an attacker to control the runtime characteristics
 *       of the Java virtual machine and cause the system to misbehave. An
 *       attacker can also access some information related to the running
 *       application.
 *   </td>
 * </tr>
 * <tr>
 *   <td>monitor</td>
 *   <td>Ability to retrieve runtime information about
 *       the Java virtual machine such as thread
 *       stack trace, a list of all loaded class names, and input arguments
 *       to the Java virtual machine.</td>
 *   <td>This allows malicious code to monitor runtime information and
 *       uncover vulnerabilities.</td>
 * </tr>
 *
 * </table>
 *
 * <p>
 * Programmers do not normally create ManagementPermission objects directly.
 * Instead they are created by the security policy code based on reading
 * the security policy file.
 *
 * <p>
 *  SecurityManager将在使用SecurityManager运行的代码调用Java平台的管理接口中定义的方法时检查的权限。
 * <P>
 *  下表提供了权限允许的摘要说明,并讨论了授予代码权限的风险。
 * 
 * <table border=1 cellpadding=5 summary="Table shows permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td>控制</td> <td>能够控制Java虚拟机的运行时特征,例如,启用和禁用类加载或内存系统的详细输出,设置内存池的阈值,以及启用以及禁用所述线程争用监视支持。
 * 由此权限控制的某些操作可以公开有关正在运行的应用程序的信息,如-verbose：class标志。
 * </td>
 *  <td>这允许攻击者控制Java虚拟机的运行时特性,并导致系统运行不正常。攻击者还可以访问与正在运行的应用程序相关的一些信息。
 * </td>
 * </tr>
 * 
 * @author  Mandy Chung
 * @since   1.5
 *
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 */

public final class ManagementPermission extends java.security.BasicPermission {
    private static final long serialVersionUID = 1897496590799378737L;

    /**
     * Constructs a ManagementPermission with the specified name.
     *
     * <p>
     * <tr>
     * <td> monitor </td> <td>能够检索有关Java虚拟机的运行时信息,例如线程堆栈跟踪,所有已加载类名的列表以及Java虚拟机的输入参数。
     * </td> <td >这允许恶意代码监视运行时信息和发现漏洞。</td>。
     * </tr>
     * 
     * </table>
     * 
     * <p>
     * 
     * @param name Permission name. Must be either "monitor" or "control".
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty or invalid.
     */
    public ManagementPermission(String name) {
        super(name);
        if (!name.equals("control") && !name.equals("monitor")) {
            throw new IllegalArgumentException("name: " + name);
        }
    }

    /**
     * Constructs a new ManagementPermission object.
     *
     * <p>
     *  程序员通常不直接创建ManagementPermission对象。相反,它们由基于读取安全策略文件的安全策略代码创建。
     * 
     * 
     * @param name Permission name. Must be either "monitor" or "control".
     * @param actions Must be either null or the empty string.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty or
     * if arguments are invalid.
     */
    public ManagementPermission(String name, String actions)
        throws IllegalArgumentException {
        super(name);
        if (!name.equals("control") && !name.equals("monitor")) {
            throw new IllegalArgumentException("name: " + name);
        }
        if (actions != null && actions.length() > 0) {
            throw new IllegalArgumentException("actions: " + actions);
        }
    }
}
