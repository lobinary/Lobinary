/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.remote;

/**
 * <p>Implemented by objects that can have a {@code JMXServiceURL} address.
 * All {@link JMXConnectorServer} objects implement this interface.
 * Depending on the connector implementation, a {@link JMXConnector}
 * object may implement this interface too.  {@code JMXConnector}
 * objects for the RMI Connector are instances of
 * {@link javax.management.remote.rmi.RMIConnector RMIConnector} which
 * implements this interface.</p>
 *
 * <p>An object implementing this interface might not have an address
 * at a given moment.  This is indicated by a null return value from
 * {@link #getAddress()}.</p>
 *
 * <p>
 *  <p>由可拥有{@code JMXServiceURL}地址的对象实施。所有{@link JMXConnectorServer}对象实现此接口。
 * 根据连接器实现,{@link JMXConnector}对象也可以实现此接口。
 *  RMI连接器的{@code JMXConnector}对象是实现此接口的{@link javax.management.remote.rmi.RMIConnector RMIConnector}的实例
 * 。
 * 根据连接器实现,{@link JMXConnector}对象也可以实现此接口。</p>。
 * 
 * 
 * @since 1.6
 */
public interface JMXAddressable {
    /**
     * <p>The address of this object.</p>
     *
     * <p>
     *  <p>实现此接口的对象在给定时刻可能没有地址。这由{@link #getAddress()}的空返回值表示。</p>
     * 
     * 
     * @return the address of this object, or null if it
     * does not have one.
     */
    public JMXServiceURL getAddress();
}
