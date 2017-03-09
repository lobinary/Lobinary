/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An interface that must be implemented when a {@linkplain Driver} wants to be
 * notified by {@code DriverManager}.
 *<P>
 * A {@code DriverAction} implementation is not intended to be used
 * directly by applications. A JDBC Driver  may choose
 * to create its {@code DriverAction} implementation in a private class
 * to avoid it being called directly.
 * <p>
 * The JDBC driver's static initialization block must call
 * {@linkplain DriverManager#registerDriver(java.sql.Driver, java.sql.DriverAction) } in order
 * to inform {@code DriverManager} which {@code DriverAction} implementation to
 * call when the JDBC driver is de-registered.
 * <p>
 *  当{@linkplain Driver}希望被{@code DriverManager}通知时必须实现的接口。
 * P>
 *  {@code DriverAction}实现不是由应用程序直接使用。 JDBC驱动程序可以选择在私有类中创建其{@code DriverAction}实现,以避免直接调用它。
 * <p>
 *  JDBC驱动程序的静态初始化块必须调用{@linkplain DriverManager#registerDriver(java.sql.Driver,java.sql.DriverAction)},
 * 以通知{@code DriverManager}哪个{@code DriverAction}实现在JDBC驱动程序已取消注册。
 * 
 * 
 * @since 1.8
 */
public interface DriverAction {
    /**
     * Method called by
     * {@linkplain DriverManager#deregisterDriver(Driver) }
     *  to notify the JDBC driver that it was de-registered.
     * <p>
     * The {@code deregister} method is intended only to be used by JDBC Drivers
     * and not by applications.  JDBC drivers are recommended to not implement
     * {@code DriverAction} in a public class.  If there are active
     * connections to the database at the time that the {@code deregister}
     * method is called, it is implementation specific as to whether the
     * connections are closed or allowed to continue. Once this method is
     * called, it is implementation specific as to whether the driver may
     * limit the ability to create new connections to the database, invoke
     * other {@code Driver} methods or throw a {@code SQLException}.
     * Consult your JDBC driver's documentation for additional information
     * on its behavior.
     * <p>
     *  由{@linkplain DriverManager#deregisterDriver(Driver)}调用的方法来通知JDBC驱动程序它已注销。
     * <p>
     * {@code deregister}方法仅供JDBC驱动程序使用,而不是由应用程序使用。建议不要在公共类中实现{@code DriverAction}的JDBC驱动程序。
     * 如果在调用{@code deregister}方法时存在到数据库的活动连接,则它是实现特定的,关于连接是关闭还是允许继续。
     * 
     * @see DriverManager#registerDriver(java.sql.Driver, java.sql.DriverAction)
     * @see DriverManager#deregisterDriver(Driver)
     * @since 1.8
     */
    void deregister();

}
