/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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


package java.util.logging;

import java.security.*;

/**
 * The permission which the SecurityManager will check when code
 * that is running with a SecurityManager calls one of the logging
 * control methods (such as Logger.setLevel).
 * <p>
 * Currently there is only one named LoggingPermission.  This is "control"
 * and it grants the ability to control the logging configuration, for
 * example by adding or removing Handlers, by adding or removing Filters,
 * or by changing logging levels.
 * <p>
 * Programmers do not normally create LoggingPermission objects directly.
 * Instead they are created by the security policy code based on reading
 * the security policy file.
 *
 *
 * <p>
 *  当SecurityManager运行的代码调用其中一个日志记录控制方法(例如Logger.setLevel)时,SecurityManager将检查的权限。
 * <p>
 *  目前只有一个命名为LoggingPermission。这是"控制",它允许控制日志配置,例如通过添加或删除处理程序,添加或删除过滤器或更改日志记录级别。
 * <p>
 *  程序员通常不直接创建LoggingPermission对象。相反,它们由基于读取安全策略文件的安全策略代码创建。
 * 
 * @since 1.4
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 */

public final class LoggingPermission extends java.security.BasicPermission {

    private static final long serialVersionUID = 63564341580231582L;

    /**
     * Creates a new LoggingPermission object.
     *
     * <p>
     * 
     * 
     * @param name Permission name.  Must be "control".
     * @param actions Must be either null or the empty string.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty or if
     * arguments are invalid.
     */
    public LoggingPermission(String name, String actions) throws IllegalArgumentException {
        super(name);
        if (!name.equals("control")) {
            throw new IllegalArgumentException("name: " + name);
        }
        if (actions != null && actions.length() > 0) {
            throw new IllegalArgumentException("actions: " + actions);
        }
    }
}
