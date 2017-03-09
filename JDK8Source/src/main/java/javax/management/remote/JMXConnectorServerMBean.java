/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2008, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.util.Map;

/**
 * <p>MBean interface for connector servers.  A JMX API connector server
 * is attached to an MBean server, and establishes connections to that
 * MBean server for remote clients.</p>
 *
 * <p>A newly-created connector server is <em>inactive</em>, and does
 * not yet listen for connections.  Only when its {@link #start start}
 * method has been called does it start listening for connections.</p>
 *
 * <p>
 *  <p>连接器服务器的MBean接口。 JMX API连接器服务器连接到MBean服务器,并为远程客户端的MBean服务器建立连接。</p>
 * 
 *  <p>新创建的连接器服务器<em>无效</em>,尚未侦听连接。只有当它的{@link #start start}方法被调用时,它才开始侦听连接。</p>
 * 
 * 
 * @since 1.5
 */
public interface JMXConnectorServerMBean {
    /**
     * <p>Activates the connector server, that is, starts listening for
     * client connections.  Calling this method when the connector
     * server is already active has no effect.  Calling this method
     * when the connector server has been stopped will generate an
     * {@link IOException}.</p>
     *
     * <p>
     *  <p>激活连接器服务器,即开始侦听客户端连接。在连接器服务器已处于活动状态时调用此方法不起作用。在连接器服务器已停止时调用此方法将生成{@link IOException}。</p>
     * 
     * 
     * @exception IOException if it is not possible to start listening
     * or if the connector server has been stopped.
     *
     * @exception IllegalStateException if the connector server has
     * not been attached to an MBean server.
     */
    public void start() throws IOException;

    /**
     * <p>Deactivates the connector server, that is, stops listening for
     * client connections.  Calling this method will also close all
     * client connections that were made by this server.  After this
     * method returns, whether normally or with an exception, the
     * connector server will not create any new client
     * connections.</p>
     *
     * <p>Once a connector server has been stopped, it cannot be started
     * again.</p>
     *
     * <p>Calling this method when the connector server has already
     * been stopped has no effect.  Calling this method when the
     * connector server has not yet been started will disable the
     * connector server object permanently.</p>
     *
     * <p>If closing a client connection produces an exception, that
     * exception is not thrown from this method.  A {@link
     * JMXConnectionNotification} with type {@link
     * JMXConnectionNotification#FAILED} is emitted from this MBean
     * with the connection ID of the connection that could not be
     * closed.</p>
     *
     * <p>Closing a connector server is a potentially slow operation.
     * For example, if a client machine with an open connection has
     * crashed, the close operation might have to wait for a network
     * protocol timeout.  Callers that do not want to block in a close
     * operation should do it in a separate thread.</p>
     *
     * <p>
     *  <p>停用连接器服务器,即停止侦听客户端连接。调用此方法还将关闭此服务器所创建的所有客户端连接。此方法返回后,无论是正常还是异常,连接器服务器都不会创建任何新的客户端连接。</p>
     * 
     *  <p>连接器服务器停止后,无法再次启动。</p>
     * 
     *  <p>在连接器服务器已停止时调用此方法无效。当连接器服务器尚未启动时调用此方法将永久禁用连接器服务器对象。</p>
     * 
     * <p>如果关闭客户端连接产生异常,则不会从此方法抛出该异常。
     * 将从此MBean发出类型为{@link JMXConnectionNotification#FAILED}的{@link JMXConnectionNotification},并显示无法关闭的连接的连接
     * ID。
     * <p>如果关闭客户端连接产生异常,则不会从此方法抛出该异常。</p>。
     * 
     *  <p>关闭连接器服务器是一个潜在的缓慢操作。例如,如果具有打开的连接的客户端计算机崩溃,则关闭操作可能必须等待网络协议超时。不想在关闭操作中阻止的调用方应在单独的线程中执行。</p>
     * 
     * 
     * @exception IOException if the server cannot be closed cleanly.
     * When this exception is thrown, the server has already attempted
     * to close all client connections.  All client connections are
     * closed except possibly those that generated exceptions when the
     * server attempted to close them.
     */
    public void stop() throws IOException;

    /**
     * <p>Determines whether the connector server is active.  A connector
     * server starts being active when its {@link #start start} method
     * returns successfully and remains active until either its
     * {@link #stop stop} method is called or the connector server
     * fails.</p>
     *
     * <p>
     *  <p>确定连接器服务器是否处于活动状态。
     * 连接器服务器在其{@link #start start}方法成功返回并保持活动状态时启动,直到调用其{@link #stop stop}方法或连接器服务器失败。</p>。
     * 
     * 
     * @return true if the connector server is active.
     */
    public boolean isActive();

    /**
     * <p>Inserts an object that intercepts requests for the MBean server
     * that arrive through this connector server.  This object will be
     * supplied as the <code>MBeanServer</code> for any new connection
     * created by this connector server.  Existing connections are
     * unaffected.</p>
     *
     * <p>This method can be called more than once with different
     * {@link MBeanServerForwarder} objects.  The result is a chain
     * of forwarders.  The last forwarder added is the first in the chain.
     * In more detail:</p>
     *
     * <ul>
     * <li><p>If this connector server is already associated with an
     * <code>MBeanServer</code> object, then that object is given to
     * {@link MBeanServerForwarder#setMBeanServer
     * mbsf.setMBeanServer}.  If doing so produces an exception, this
     * method throws the same exception without any other effect.</p>
     *
     * <li><p>If this connector is not already associated with an
     * <code>MBeanServer</code> object, or if the
     * <code>mbsf.setMBeanServer</code> call just mentioned succeeds,
     * then <code>mbsf</code> becomes this connector server's
     * <code>MBeanServer</code>.</p>
     * </ul>
     *
     * <p>
     *  <p>插入一个对象,拦截通过此连接器服务器到达的MBean服务器的请求。此对象将作为此连接器服务器创建的任何新连接的<code> MBeanServer </code>提供。现有连接不受影响。
     * </p>。
     * 
     *  <p>此方法可以使用不同的{@link MBeanServerForwarder}对象多次调用。结果是一个转发器链。添加的最后一个转发器是链中的第一个。更详细：</p>
     * 
     * <ul>
     * <li> <p>如果此连接器服务器已与<code> MBeanServer </code>对象关联,则该对象将提供给{@link MBeanServerForwarder#setMBeanServer mbsf.setMBeanServer}
     * 。
     * 如果这样做会产生异常,则此方法会抛出相同的异常,而不会产生任何其他影响。</p>。
     * 
     *  <li> <p>如果此连接器尚未与<code> MBeanServer </code>对象关联,或者刚刚提到的<code> mbsf.setMBeanServer </code>调用成功,则<code>
     *  mbsf <代码>成为此连接器服务器的<code> MBeanServer </code>。
     * </p>。
     * </ul>
     * 
     * 
     * @param mbsf the new <code>MBeanServerForwarder</code>.
     *
     * @exception IllegalArgumentException if the call to {@link
     * MBeanServerForwarder#setMBeanServer mbsf.setMBeanServer} fails
     * with <code>IllegalArgumentException</code>.  This includes the
     * case where <code>mbsf</code> is null.
     */
    public void setMBeanServerForwarder(MBeanServerForwarder mbsf);

    /**
     * <p>The list of IDs for currently-open connections to this
     * connector server.</p>
     *
     * <p>
     *  <p>此连接器服务器的当前打开连接的ID列表。</p>
     * 
     * 
     * @return a new string array containing the list of IDs.  If
     * there are no currently-open connections, this array will be
     * empty.
     */
    public String[] getConnectionIds();

    /**
     * <p>The address of this connector server.</p>
     * <p>
     * The address returned may not be the exact original {@link JMXServiceURL}
     * that was supplied when creating the connector server, since the original
     * address may not always be complete. For example the port number may be
     * dynamically allocated when starting the connector server. Instead the
     * address returned is the actual {@link JMXServiceURL} of the
     * {@link JMXConnectorServer}. This is the address that clients supply
     * to {@link JMXConnectorFactory#connect(JMXServiceURL)}.
     * </p>
     * <p>Note that the address returned may be {@code null} if
     *    the {@code JMXConnectorServer} is not yet {@link #isActive active}.
     * </p>
     *
     * <p>
     *  <p>此连接器服务器的地址。</p>
     * <p>
     *  返回的地址可能不是创建连接器服务器时提供的原始{@link JMXServiceURL},因为原始地址可能并不总是完整的。例如,在启动连接器服务器时可以动态分配端口号。
     * 而是返回的地址是{@link JMXConnectorServer}的实际{@link JMXServiceURL}。
     * 这是客户端提供给{@link JMXConnectorFactory#connect(JMXServiceURL)}的地址。
     * </p>
     *  <p>请注意,如果{@code JMXConnectorServer}尚未{@link #isActive active},则返回的地址可能为{@code null}。
     * </p>
     * 
     * 
     * @return the address of this connector server, or null if it
     * does not have one.
     */
    public JMXServiceURL getAddress();

    /**
     * <p>The attributes for this connector server.</p>
     *
     * <p>
     * 
     * @return a read-only map containing the attributes for this
     * connector server.  Attributes whose values are not serializable
     * are omitted from this map.  If there are no serializable
     * attributes, the returned map is empty.
     */
    public Map<String,?> getAttributes();

    /**
     * <p>Returns a client stub for this connector server.  A client
     * stub is a serializable object whose {@link
     * JMXConnector#connect(Map) connect} method can be used to make
     * one new connection to this connector server.</p>
     *
     * <p>A given connector need not support the generation of client
     * stubs.  However, the connectors specified by the JMX Remote API do
     * (JMXMP Connector and RMI Connector).</p>
     *
     * <p>
     *  <p>此连接器服务器的属性。</p>
     * 
     * 
     * @param env client connection parameters of the same sort that
     * can be provided to {@link JMXConnector#connect(Map)
     * JMXConnector.connect(Map)}.  Can be null, which is equivalent
     * to an empty map.
     *
     * @return a client stub that can be used to make a new connection
     * to this connector server.
     *
     * @exception UnsupportedOperationException if this connector
     * server does not support the generation of client stubs.
     *
     * @exception IllegalStateException if the JMXConnectorServer is
     * not started (see {@link JMXConnectorServerMBean#isActive()}).
     *
     * @exception IOException if a communications problem means that a
     * stub cannot be created.
     *
     */
    public JMXConnector toJMXConnector(Map<String,?> env)
        throws IOException;
}
