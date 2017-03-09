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

package java.lang.reflect;

/**
 * The Permission class for reflective operations.
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
 *   <td>suppressAccessChecks</td>
 *   <td>ability to suppress the standard Java language access checks
 *       on fields and methods in a class; allow access not only public members
 *       but also allow access to default (package) access, protected,
 *       and private members.</td>
 *   <td>This is dangerous in that information (possibly confidential) and
 *       methods normally unavailable would be accessible to malicious code.</td>
 * </tr>
 * <tr>
 *   <td>newProxyInPackage.{package name}</td>
 *   <td>ability to create a proxy instance in the specified package of which
 *       the non-public interface that the proxy class implements.</td>
 *   <td>This gives code access to classes in packages to which it normally
 *       does not have access and the dynamic proxy class is in the system
 *       protection domain. Malicious code may use these classes to
 *       help in its attempt to compromise security in the system.</td>
 * </tr>
 *
 * </table>
 *
 * <p>
 *  反射操作的Permission类。
 * <P>
 *  下表提供了权限允许的摘要说明,并讨论了授予代码权限的风险。
 * 
 * <table border=1 cellpadding=5 summary="Table shows permission target name, what the permission allows, and associated risks">
 * <tr>
 *  <th>权限目标名称</th> <th>权限允许</th> <th>允许此权限的风险</th>
 * </tr>
 * 
 * <tr>
 *  <td> suppressAccessChecks </td> <td>禁止对类中的字段和方法进行标准Java语言访问检查的功能;允许访问不仅公共成员,而且允许访问默认(包)访问,受保护和私有成员。
 * </td> <td>这是危险的,因为信息(可能是机密)和通常不可用的方法可以访问恶意代码。</td>。
 * </tr>
 * 
 * @see java.security.Permission
 * @see java.security.BasicPermission
 * @see AccessibleObject
 * @see Field#get
 * @see Field#set
 * @see Method#invoke
 * @see Constructor#newInstance
 * @see Proxy#newProxyInstance
 *
 * @since 1.2
 */
public final
class ReflectPermission extends java.security.BasicPermission {

    private static final long serialVersionUID = 7412737110241507485L;

    /**
     * Constructs a ReflectPermission with the specified name.
     *
     * <p>
     * <tr>
     *  <td> newProxyInPackage。{package name} </td> <td>在指定的包中创建代理实例的能力,代理实现的非公共接口。
     * </td> <td>到通常不具有访问权限的包中的类,并且动态代理类位于系统保护域中。恶意代码可能会使用这些类来帮助企图危及系统的安全。</td>。
     * </tr>
     * 
     * </table>
     * 
     * 
     * @param name the name of the ReflectPermission
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public ReflectPermission(String name) {
        super(name);
    }

    /**
     * Constructs a ReflectPermission with the specified name and actions.
     * The actions should be null; they are ignored.
     *
     * <p>
     * 
     * @param name the name of the ReflectPermission
     *
     * @param actions should be null
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public ReflectPermission(String name, String actions) {
        super(name, actions);
    }

}
