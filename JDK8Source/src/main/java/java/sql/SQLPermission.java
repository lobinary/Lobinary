/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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


package java.sql;

import java.security.*;

/**
 * The permission for which the <code>SecurityManager</code> will check
 * when code that is running an application with a
 * <code>SecurityManager</code> enabled, calls the
 * {@code DriverManager.deregisterDriver} method,
 * <code>DriverManager.setLogWriter</code> method,
 * <code>DriverManager.setLogStream</code> (deprecated) method,
 * {@code SyncFactory.setJNDIContext} method,
 * {@code SyncFactory.setLogger} method,
 * {@code Connection.setNetworktimeout} method,
 * or the <code>Connection.abort</code> method.
 * If there is no <code>SQLPermission</code> object, these methods
 * throw a <code>java.lang.SecurityException</code> as a runtime exception.
 * <P>
 * A <code>SQLPermission</code> object contains
 * a name (also referred to as a "target name") but no actions
 * list; there is either a named permission or there is not.
 * The target name is the name of the permission (see below). The
 * naming convention follows the  hierarchical property naming convention.
 * In addition, an asterisk
 * may appear at the end of the name, following a ".", or by itself, to
 * signify a wildcard match. For example: <code>loadLibrary.*</code>
 * and <code>*</code> signify a wildcard match,
 * while <code>*loadLibrary</code> and <code>a*b</code> do not.
 * <P>
 * The following table lists all the possible <code>SQLPermission</code> target names.
 * The table gives a description of what the permission allows
 * and a discussion of the risks of granting code the permission.
 *
 *
 * <table border=1 cellpadding=5 summary="permission target name, what the permission allows, and associated risks">
 * <tr>
 * <th>Permission Target Name</th>
 * <th>What the Permission Allows</th>
 * <th>Risks of Allowing this Permission</th>
 * </tr>
 *
 * <tr>
 *   <td>setLog</td>
 *   <td>Setting of the logging stream</td>
 *   <td>This is a dangerous permission to grant.
 * The contents of the log may contain usernames and passwords,
 * SQL statements, and SQL data.</td>
 * </tr>
 * <tr>
 * <td>callAbort</td>
 *   <td>Allows the invocation of the {@code Connection} method
 *   {@code abort}</td>
 *   <td>Permits an application to terminate a physical connection to a
 *  database.</td>
 * </tr>
 * <tr>
 * <td>setSyncFactory</td>
 *   <td>Allows the invocation of the {@code SyncFactory} methods
 *   {@code setJNDIContext} and {@code setLogger}</td>
 *   <td>Permits an application to specify the JNDI context from which the
 *   {@code SyncProvider} implementations can be retrieved from and the logging
 *   object to be used by the {@code SyncProvider} implementation.</td>
 * </tr>
 *
 * <tr>
 * <td>setNetworkTimeout</td>
 *   <td>Allows the invocation of the {@code Connection} method
 *   {@code setNetworkTimeout}</td>
 *   <td>Permits an application to specify the maximum period a
 * <code>Connection</code> or
 * objects created from the <code>Connection</code>
 * will wait for the database to reply to any one request.</td>
 * <tr>
 * <td>deregisterDriver</td>
 *   <td>Allows the invocation of the {@code DriverManager}
 * method {@code deregisterDriver}</td>
 *   <td>Permits an application to remove a JDBC driver from the list of
 * registered Drivers and release its resources.</td>
 * </tr>
 * </table>
 *<p>
 * <p>
 *  当运行启用了<code> SecurityManager </code>的应用程序的代码调用{@code DriverManager.deregisterDriver}方法<code> DriverM
 * anager时,<code> SecurityManager </code>将检查的权限。
 *  setLogWriter </code>方法,<code> DriverManager.setLogStream </code>(弃用)方法,{@code SyncFactory.setJNDIContext}
 * 方法,{@code SyncFactory.setLogger}方法,{@code Connection.setNetworktimeout}方法, <code> Connection.abort </code>
 * 方法。
 * 如果没有<code> SQLPermission </code>对象,这些方法会抛出一个<code> java.lang.SecurityException </code>作为运行时异常。
 * <P>
 *  一个<code> SQLPermission </code>对象包含一个名称(也称为"目标名称"),但没有动作列表;有一个命名的权限或者没有。目标名称是权限的名称(见下文)。
 * 命名约定遵循分层属性命名约定。此外,星号可以出现在名称的末尾,紧跟在"。"后面,或者表示一个通配符匹配。例如：<code> loadLibrary。
 * * </code>和<code> * </code>表示通配符匹配,而<code> * loadLibrary </code>和<code> a * b </code> 。
 * <P>
 * 下表列出了所有可能的<code> SQLPermission </code>目标名称。该表给出了权限允许的描述,并讨论了授予代码权限的风险。
 * 
 * <table border=1 cellpadding=5 summary="permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> setLog </td> <td>日志记录流的设置</td> <td>这是一个危险的授予权限。日志的内容可能包含用户名和密码,SQL语句和SQL数据。</td>
 * </tr>
 * <tr>
 *  <td> callAbort </td> <td>允许调用{@code Connection}方法{@code abort} </td> <td>允许应用程序终止与数据库的物理连接。</td>
 * </tr>
 * <tr>
 *  <td> setSyncFactory </td> <td>允许调用{@code SyncFactory}方法{@code setJNDIContext}和{@code setLogger} </td>
 * 
 * @since 1.3
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 */

public final class SQLPermission extends BasicPermission {

    /**
     * Creates a new <code>SQLPermission</code> object with the specified name.
     * The name is the symbolic name of the <code>SQLPermission</code>.
     *
     * <p>
     *  <td>允许应用程序指定JNDI上下文{@code SyncProvider}实现可以从{@code SyncProvider}实现中使用的日志对象中检索。
     * </td>。
     * </tr>
     * 
     * <tr>
     *  <td> setNetworkTimeout </td> <td>允许调用{@code Connection}方法{@code setNetworkTimeout} </td> <td>允许应用程序指
     * 定<code> Connection </code >或从<code> Connection </code>创建的对象将等待数据库回复任何一个请求。
     * </td>。
     * <tr>
     * <td> deregisterDriver </td> <td>允许调用{@code DriverManager}方法{@code deregisterDriver} </td> <td>允许应用程序从
     * 注册的驱动程序列表中删除JDBC驱动程序,其资源。
     * </td>。
     * </tr>
     * 
     * @param name the name of this <code>SQLPermission</code> object, which must
     * be either {@code  setLog}, {@code callAbort}, {@code setSyncFactory},
     *  {@code deregisterDriver}, or {@code setNetworkTimeout}
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.

     */

    public SQLPermission(String name) {
        super(name);
    }

    /**
     * Creates a new <code>SQLPermission</code> object with the specified name.
     * The name is the symbolic name of the <code>SQLPermission</code>; the
     * actions <code>String</code> is currently unused and should be
     * <code>null</code>.
     *
     * <p>
     * </table>
     * p>
     * 
     * @param name the name of this <code>SQLPermission</code> object, which must
     * be either {@code  setLog}, {@code callAbort}, {@code setSyncFactory},
     *  {@code deregisterDriver}, or {@code setNetworkTimeout}
     * @param actions should be <code>null</code>
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.

     */

    public SQLPermission(String name, String actions) {
        super(name, actions);
    }

    /**
     * Private serial version unique ID to ensure serialization
     * compatibility.
     * <p>
     *  使用指定的名称创建新的<code> SQLPermission </code>对象。名称是<code> SQLPermission </code>的符号名称。
     * 
     */
    static final long serialVersionUID = -1439323187199563495L;

}
