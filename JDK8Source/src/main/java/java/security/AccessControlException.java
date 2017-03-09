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

package java.security;

/**
 * <p> This exception is thrown by the AccessController to indicate
 * that a requested access (to a critical system resource such as the
 * file system or the network) is denied.
 *
 * <p> The reason to deny access can vary.  For example, the requested
 * permission might be of an incorrect type,  contain an invalid
 * value, or request access that is not allowed according to the
 * security policy.  Such information should be given whenever
 * possible at the time the exception is thrown.
 *
 * <p>
 *  <p> AccessController抛出此异常,以指示拒绝请求的访问(对关键系统资源,如文件系统或网络)。
 * 
 *  <p>拒绝访问的原因可能有所不同。例如,请求的权限可能是不正确的类型,包含无效的值,或请求根据安全策略不允许的访问。应尽可能在抛出异常时提供此类信息。
 * 
 * 
 * @author Li Gong
 * @author Roland Schemers
 */

public class AccessControlException extends SecurityException {

    private static final long serialVersionUID = 5138225684096988535L;

    // the permission that caused the exception to be thrown.
    private Permission perm;

    /**
     * Constructs an {@code AccessControlException} with the
     * specified, detailed message.
     *
     * <p>
     *  使用指定的详细消息构造{@code AccessControlException}。
     * 
     * 
     * @param   s   the detail message.
     */
    public AccessControlException(String s) {
        super(s);
    }

    /**
     * Constructs an {@code AccessControlException} with the
     * specified, detailed message, and the requested permission that caused
     * the exception.
     *
     * <p>
     *  使用指定的详细消息和导致异常的请求权限构造{@code AccessControlException}。
     * 
     * 
     * @param   s   the detail message.
     * @param   p   the permission that caused the exception.
     */
    public AccessControlException(String s, Permission p) {
        super(s);
        perm = p;
    }

    /**
     * Gets the Permission object associated with this exception, or
     * null if there was no corresponding Permission object.
     *
     * <p>
     *  获取与此异常相关联的Permission对象,如果没有对应的Permission对象,则返回null。
     * 
     * @return the Permission object.
     */
    public Permission getPermission() {
        return perm;
    }
}
