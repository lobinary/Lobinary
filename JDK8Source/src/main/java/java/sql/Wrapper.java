/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Interface for JDBC classes which provide the ability to retrieve the delegate instance when the instance
 * in question is in fact a proxy class.
 * <p>
 * The wrapper pattern is employed by many JDBC driver implementations to provide extensions beyond
 * the traditional JDBC API that are specific to a data source. Developers may wish to gain access to
 * these resources that are wrapped (the delegates) as  proxy class instances representing the
 * the actual resources. This interface describes a standard mechanism to access
 * these wrapped resources
 * represented by their proxy, to permit direct access to the resource delegates.
 *
 * <p>
 *  JDBC类的接口,当有问题的实例实际上是代理类时,它提供检索委托实例的能力。
 * <p>
 *  许多JDBC驱动程序实现使用包装器模式来提供特定于数据源的传统JDBC API之外的扩展。开发人员可能希望访问被包装(代理)作为代表实际资源的代理类实例的这些资源。
 * 此接口描述了一种标准机制,用于访问由其代理表示的这些封装资源,以允许直接访问资源代理。
 * 
 * 
 * @since 1.6
 */

public interface Wrapper {

    /**
     * Returns an object that implements the given interface to allow access to
     * non-standard methods, or standard methods not exposed by the proxy.
     *
     * If the receiver implements the interface then the result is the receiver
     * or a proxy for the receiver. If the receiver is a wrapper
     * and the wrapped object implements the interface then the result is the
     * wrapped object or a proxy for the wrapped object. Otherwise return the
     * the result of calling <code>unwrap</code> recursively on the wrapped object
     * or a proxy for that result. If the receiver is not a
     * wrapper and does not implement the interface, then an <code>SQLException</code> is thrown.
     *
     * <p>
     *  返回实现给定接口的对象,以允许访问非标准方法或代理未公开的标准方法。
     * 
     *  如果接收器实现接口,则结果是接收器或接收器的代理。如果接收器是包装器,并且包装对象实现接口,那么结果是包装对象或包装对象的代理。
     * 否则返回在包装对象或该结果的代理上递归调用<code> unwrap </code>的结果。如果接收者不是包装器并且不实现接口,则抛出<code> SQLException </code>。
     * 
     * 
     * @param <T> the type of the class modeled by this Class object
     * @param iface A Class defining an interface that the result must implement.
     * @return an object that implements the interface. May be a proxy for the actual implementing object.
     * @throws java.sql.SQLException If no object found that implements the interface
     * @since 1.6
     */
        <T> T unwrap(java.lang.Class<T> iface) throws java.sql.SQLException;

    /**
     * Returns true if this either implements the interface argument or is directly or indirectly a wrapper
     * for an object that does. Returns false otherwise. If this implements the interface then return true,
     * else if this is a wrapper then return the result of recursively calling <code>isWrapperFor</code> on the wrapped
     * object. If this does not implement the interface and is not a wrapper, return false.
     * This method should be implemented as a low-cost operation compared to <code>unwrap</code> so that
     * callers can use this method to avoid expensive <code>unwrap</code> calls that may fail. If this method
     * returns true then calling <code>unwrap</code> with the same argument should succeed.
     *
     * <p>
     * 如果这或者实现了接口参数,或者直接或间接地是一个对象的包装器,则返回true。否则返回false。
     * 如果这实现接口然后返回true,否则如果这是一个包装,然后返回递归调用包装对象上的<code> isWrapperFor </code>的结果。如果这不实现接口并且不是包装器,返回false。
     * 与<code> unwrap </code>相比,该方法应该作为一种低成本操作来实现,以便调用者可以使用此方法避免可能失败的昂贵的<code> unwrap </code>调用。
     * 
     * @param iface a Class defining an interface.
     * @return true if this implements the interface or directly or indirectly wraps an object that does.
     * @throws java.sql.SQLException  if an error occurs while determining whether this is a wrapper
     * for an object with the given interface.
     * @since 1.6
     */
    boolean isWrapperFor(java.lang.Class<?> iface) throws java.sql.SQLException;

}
