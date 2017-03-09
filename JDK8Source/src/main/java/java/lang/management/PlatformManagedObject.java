/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2011, Oracle and/or its affiliates. All rights reserved.
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

import javax.management.ObjectName;

/**
 * A platform managed object is a {@linkplain javax.management.MXBean JMX MXBean}
 * for monitoring and managing a component in the Java platform.
 * Each platform managed object has a unique
 * <a href="ManagementFactory.html#MXBean">object name</a>
 * for the {@linkplain ManagementFactory#getPlatformMBeanServer
 * platform MBeanServer} access.
 * All platform MXBeans will implement this interface.
 *
 * <p>
 * Note:
 * The platform MXBean interfaces (i.e. all subinterfaces
 * of {@code PlatformManagedObject}) are implemented
 * by the Java platform only.  New methods may be added in these interfaces
 * in future Java SE releases.
 * In addition, this {@code PlatformManagedObject} interface is only
 * intended for the management interfaces for the platform to extend but
 * not for applications.
 *
 * <p>
 *  平台托管对象是用于在Java平台中监视和管理组件的{@linkplain javax.management.MXBean JMX MXBean}。
 * 每个平台托管对象都具有{@linkplain ManagementFactory#getPlatformMBeanServer platform MBeanServer}访问权限的唯一<a href="ManagementFactory.html#MXBean">
 * 对象名称</a>。
 *  平台托管对象是用于在Java平台中监视和管理组件的{@linkplain javax.management.MXBean JMX MXBean}。所有平台MXBeans将实现此接口。
 * 
 * <p>
 * 
 * @see ManagementFactory
 * @since 1.7
 */
public interface PlatformManagedObject {
    /**
     * Returns an {@link ObjectName ObjectName} instance representing
     * the object name of this platform managed object.
     *
     * <p>
     *  注意：平台MXBean接口(即{@code PlatformManagedObject}的所有子接口)仅由Java平台实现。在未来的Java SE版本中,可以在这些接口中添加新的方法。
     * 此外,此{@code PlatformManagedObject}接口仅用于平台的管理接口,但不适用于应用程序。
     * 
     * 
     * @return an {@link ObjectName ObjectName} instance representing
     * the object name of this platform managed object.
     */
    public ObjectName getObjectName();
}
