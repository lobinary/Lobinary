/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;

/**
 * <p>Superclass of every connector server.  A connector server is
 * attached to an MBean server.  It listens for client connection
 * requests and creates a connection for each one.</p>
 *
 * <p>A connector server is associated with an MBean server either by
 * registering it in that MBean server, or by passing the MBean server
 * to its constructor.</p>
 *
 * <p>A connector server is inactive when created.  It only starts
 * listening for client connections when the {@link #start() start}
 * method is called.  A connector server stops listening for client
 * connections when the {@link #stop() stop} method is called or when
 * the connector server is unregistered from its MBean server.</p>
 *
 * <p>Stopping a connector server does not unregister it from its
 * MBean server.  A connector server once stopped cannot be
 * restarted.</p>
 *
 * <p>Each time a client connection is made or broken, a notification
 * of class {@link JMXConnectionNotification} is emitted.</p>
 *
 * <p>
 *  <p>每个连接器服务器的超类。连接器服务器连接到MBean服务器。它侦听客户端连接请求并为每个连接创建一个连接。</p>
 * 
 *  <p>连接器服务器通过将MBean服务器注册到MBean服务器,或将MBean服务器传递到其构造函数,与MBean服务器相关联。</p>
 * 
 *  <p>连接器服务器在创建时处于非活动状态。它仅在调用{@link #start()start}方法时开始侦听客户端连接。
 * 当调用{@link #stop()stop}方法或连接器服务器从其MBean服务器注销时,连接器服务器将停止侦听客户端连接。</p>。
 * 
 *  <p>停止连接器服务器不会从其MBean服务器取消注册。连接器服务器一旦停止,就无法重新启动。</p>
 * 
 *  <p>每次建立或中断客户端连接时,都会发出{@link JMXConnectionNotification}类的通知。</p>
 * 
 * 
 * @since 1.5
 */
public abstract class JMXConnectorServer
        extends NotificationBroadcasterSupport
        implements JMXConnectorServerMBean, MBeanRegistration, JMXAddressable {

    /**
     * <p>Name of the attribute that specifies the authenticator for a
     * connector server.  The value associated with this attribute, if
     * any, must be an object that implements the interface {@link
     * JMXAuthenticator}.</p>
     * <p>
     *  <p>指定连接器服务器的验证器的属性名称。与此属性关联的值(如果有)必须是实现接口{@link JMXAuthenticator}的对象。</p>
     * 
     */
    public static final String AUTHENTICATOR =
        "jmx.remote.authenticator";

    /**
     * <p>Constructs a connector server that will be registered as an
     * MBean in the MBean server it is attached to.  This constructor
     * is typically called by one of the <code>createMBean</code>
     * methods when creating, within an MBean server, a connector
     * server that makes it available remotely.</p>
     * <p>
     * <p>构造将在其连接的MBean服务器中注册为MBean的连接器服务器。
     * 在MBean服务器中创建使其可远程访问的连接器服务器时,此构造函数通常由<code> createMBean </code>方法之一调用。</p>。
     * 
     */
    public JMXConnectorServer() {
        this(null);
    }

    /**
     * <p>Constructs a connector server that is attached to the given
     * MBean server.  A connector server that is created in this way
     * can be registered in a different MBean server, or not registered
     * in any MBean server.</p>
     *
     * <p>
     *  <p>构造连接到给定MBean服务器的连接器服务器。以此方式创建的连接器服务器可以注册在不同的MBean服务器中,或未在任何MBean服务器中注册。</p>
     * 
     * 
     * @param mbeanServer the MBean server that this connector server
     * is attached to.  Null if this connector server will be attached
     * to an MBean server by being registered in it.
     */
    public JMXConnectorServer(MBeanServer mbeanServer) {
        this.mbeanServer = mbeanServer;
    }

    /**
     * <p>Returns the MBean server that this connector server is
     * attached to.</p>
     *
     * <p>
     *  <p>返回此连接器服务器所连接的MBean服务器。</p>
     * 
     * 
     * @return the MBean server that this connector server is attached
     * to, or null if it is not yet attached to an MBean server.
     */
    public synchronized MBeanServer getMBeanServer() {
        return mbeanServer;
    }

    public synchronized void setMBeanServerForwarder(MBeanServerForwarder mbsf)
    {
        if (mbsf == null)
            throw new IllegalArgumentException("Invalid null argument: mbsf");

        if (mbeanServer !=  null) mbsf.setMBeanServer(mbeanServer);
        mbeanServer = mbsf;
    }

    public String[] getConnectionIds() {
        synchronized (connectionIds) {
            return connectionIds.toArray(new String[connectionIds.size()]);
        }
    }

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
     * <p>The default implementation of this method uses {@link
     * #getAddress} and {@link JMXConnectorFactory} to generate the
     * stub, with code equivalent to the following:</p>
     *
     * <pre>
     * JMXServiceURL addr = {@link #getAddress() getAddress()};
     * return {@link JMXConnectorFactory#newJMXConnector(JMXServiceURL, Map)
     *          JMXConnectorFactory.newJMXConnector(addr, env)};
     * </pre>
     *
     * <p>A connector server for which this is inappropriate must
     * override this method so that it either implements the
     * appropriate logic or throws {@link
     * UnsupportedOperationException}.</p>
     *
     * <p>
     *  <p>返回此连接器服务器的客户端存根。客户端存根是可序列化的对象,其{@link JMXConnector#connect(Map)connect}方法可用于与此连接器服务器建立一个新连接。
     * </p>。
     * 
     *  <p>给定的连接器不需要支持生成客户端存根。但是,由JMX远程API指定的连接器(JMXMP连接器和RMI连接器)。</p>
     * 
     *  <p>此方法的默认实现使用{@link #getAddress}和{@link JMXConnectorFactory}生成存根,代码等效于以下内容：</p>
     * 
     * <pre>
     *  JMXServiceURL addr = {@link #getAddress()getAddress()}; return {@link JMXConnectorFactory#newJMXConnector(JMXServiceURL,Map)JMXConnectorFactory.newJMXConnector(addr,env)}
     * ;。
     * </pre>
     * 
     *  <p>这是不合适的连接器服务器必须重写此方法,以便它实现相应的逻辑或抛出{@link UnsupportedOperationException}。</p>
     * 
     * 
     * @param env client connection parameters of the same sort that
     * could be provided to {@link JMXConnector#connect(Map)
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
     **/
    public JMXConnector toJMXConnector(Map<String,?> env)
        throws IOException
    {
        if (!isActive()) throw new
            IllegalStateException("Connector is not active");
        JMXServiceURL addr = getAddress();
        return JMXConnectorFactory.newJMXConnector(addr, env);
    }

    /**
     * <p>Returns an array indicating the notifications that this MBean
     * sends. The implementation in <code>JMXConnectorServer</code>
     * returns an array with one element, indicating that it can emit
     * notifications of class {@link JMXConnectionNotification} with
     * the types defined in that class.  A subclass that can emit other
     * notifications should return an array that contains this element
     * plus descriptions of the other notifications.</p>
     *
     * <p>
     * <p>返回一个数组,指示此MBean发送的通知。
     * 在<code> JMXConnectorServer </code>中的实现返回一个具有一个元素的数组,表示它可以发出类{@link JMXConnectionNotification}的类和类中定义的
     * 类型的通知。
     * <p>返回一个数组,指示此MBean发送的通知。可以发出其他通知的子类应该返回包含此元素的数组以及其他通知的描述。</p>。
     * 
     * 
     * @return the array of possible notifications.
     */
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        final String[] types = {
            JMXConnectionNotification.OPENED,
            JMXConnectionNotification.CLOSED,
            JMXConnectionNotification.FAILED,
        };
        final String className = JMXConnectionNotification.class.getName();
        final String description =
            "A client connection has been opened or closed";
        return new MBeanNotificationInfo[] {
            new MBeanNotificationInfo(types, className, description),
        };
    }

    /**
     * <p>Called by a subclass when a new client connection is opened.
     * Adds <code>connectionId</code> to the list returned by {@link
     * #getConnectionIds()}, then emits a {@link
     * JMXConnectionNotification} with type {@link
     * JMXConnectionNotification#OPENED}.</p>
     *
     * <p>
     *  <p>在打开新客户端连接时由子类调用。
     * 在{@link #getConnectionIds()}返回的列表中添加<code> connectionId </code>,然后发出一个类型为{@link JMXConnectionNotification#OPENED}
     * 的{@link JMXConnectionNotification}。
     *  <p>在打开新客户端连接时由子类调用。</p>。
     * 
     * 
     * @param connectionId the ID of the new connection.  This must be
     * different from the ID of any connection previously opened by
     * this connector server.
     *
     * @param message the message for the emitted {@link
     * JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getMessage()}.
     *
     * @param userData the <code>userData</code> for the emitted
     * {@link JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getUserData()}.
     *
     * @exception NullPointerException if <code>connectionId</code> is
     * null.
     */
    protected void connectionOpened(String connectionId,
                                    String message,
                                    Object userData) {

        if (connectionId == null)
            throw new NullPointerException("Illegal null argument");

        synchronized (connectionIds) {
            connectionIds.add(connectionId);
        }

        sendNotification(JMXConnectionNotification.OPENED, connectionId,
                         message, userData);
    }

    /**
     * <p>Called by a subclass when a client connection is closed
     * normally.  Removes <code>connectionId</code> from the list returned
     * by {@link #getConnectionIds()}, then emits a {@link
     * JMXConnectionNotification} with type {@link
     * JMXConnectionNotification#CLOSED}.</p>
     *
     * <p>
     *  <p>当客户端连接正常关闭时由子类调用。
     * 从{@link #getConnectionIds()}返回的列表中删除<code> connectionId </code>,然后发出类型为{@link JMXConnectionNotification#CLOSED}
     * 的{@link JMXConnectionNotification}。
     *  <p>当客户端连接正常关闭时由子类调用。</p>。
     * 
     * 
     * @param connectionId the ID of the closed connection.
     *
     * @param message the message for the emitted {@link
     * JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getMessage()}.
     *
     * @param userData the <code>userData</code> for the emitted
     * {@link JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getUserData()}.
     *
     * @exception NullPointerException if <code>connectionId</code>
     * is null.
     */
    protected void connectionClosed(String connectionId,
                                    String message,
                                    Object userData) {

        if (connectionId == null)
            throw new NullPointerException("Illegal null argument");

        synchronized (connectionIds) {
            connectionIds.remove(connectionId);
        }

        sendNotification(JMXConnectionNotification.CLOSED, connectionId,
                         message, userData);
    }

    /**
     * <p>Called by a subclass when a client connection fails.
     * Removes <code>connectionId</code> from the list returned by
     * {@link #getConnectionIds()}, then emits a {@link
     * JMXConnectionNotification} with type {@link
     * JMXConnectionNotification#FAILED}.</p>
     *
     * <p>
     *  <p>当客户端连接失败时,由子类调用。
     * 从{@link #getConnectionIds()}返回的列表中删除<code> connectionId </code>,然后发出类型为{@link JMXConnectionNotification#FAILED}
     * 的{@link JMXConnectionNotification}。
     *  <p>当客户端连接失败时,由子类调用。</p>。
     * 
     * 
     * @param connectionId the ID of the failed connection.
     *
     * @param message the message for the emitted {@link
     * JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getMessage()}.
     *
     * @param userData the <code>userData</code> for the emitted
     * {@link JMXConnectionNotification}.  Can be null.  See {@link
     * Notification#getUserData()}.
     *
     * @exception NullPointerException if <code>connectionId</code> is
     * null.
     */
    protected void connectionFailed(String connectionId,
                                    String message,
                                    Object userData) {

        if (connectionId == null)
            throw new NullPointerException("Illegal null argument");

        synchronized (connectionIds) {
            connectionIds.remove(connectionId);
        }

        sendNotification(JMXConnectionNotification.FAILED, connectionId,
                         message, userData);
    }

    private void sendNotification(String type, String connectionId,
                                  String message, Object userData) {
        Notification notif =
            new JMXConnectionNotification(type,
                                          getNotificationSource(),
                                          connectionId,
                                          nextSequenceNumber(),
                                          message,
                                          userData);
        sendNotification(notif);
    }

    private synchronized Object getNotificationSource() {
        if (myName != null)
            return myName;
        else
            return this;
    }

    private static long nextSequenceNumber() {
        synchronized (sequenceNumberLock) {
            return sequenceNumber++;
        }
    }

    // implements MBeanRegistration
    /**
     * <p>Called by an MBean server when this connector server is
     * registered in that MBean server.  This connector server becomes
     * attached to the MBean server and its {@link #getMBeanServer()}
     * method will return <code>mbs</code>.</p>
     *
     * <p>If this connector server is already attached to an MBean
     * server, this method has no effect.  The MBean server it is
     * attached to is not necessarily the one it is being registered
     * in.</p>
     *
     * <p>
     *  <p>当此连接器服务器在MBean服务器中注册时,由MBean服务器调用。
     * 此连接器服务器已连接到MBean服务器,其{@link #getMBeanServer()}方法将返回<code> mbs </code>。</p>。
     * 
     * <p>如果此连接器服务器已连接到MBean服务器,则此方法无效。它所连接的MBean服务器不一定是它正在注册的服务器。</p>
     * 
     * 
     * @param mbs the MBean server in which this connection server is
     * being registered.
     *
     * @param name The object name of the MBean.
     *
     * @return The name under which the MBean is to be registered.
     *
     * @exception NullPointerException if <code>mbs</code> or
     * <code>name</code> is null.
     */
    public synchronized ObjectName preRegister(MBeanServer mbs,
                                               ObjectName name) {
        if (mbs == null || name == null)
            throw new NullPointerException("Null MBeanServer or ObjectName");
        if (mbeanServer == null) {
            mbeanServer = mbs;
            myName = name;
        }
        return name;
    }

    public void postRegister(Boolean registrationDone) {
        // do nothing
    }

    /**
     * <p>Called by an MBean server when this connector server is
     * unregistered from that MBean server.  If this connector server
     * was attached to that MBean server by being registered in it,
     * and if the connector server is still active,
     * then unregistering it will call the {@link #stop stop} method.
     * If the <code>stop</code> method throws an exception, the
     * unregistration attempt will fail.  It is recommended to call
     * the <code>stop</code> method explicitly before unregistering
     * the MBean.</p>
     *
     * <p>
     *  <p>当此连接器服务器从MBean服务器注销时由MBean服务器调用。
     * 如果此连接器服务器已通过在其中注册连接到该MBean服务器,并且连接器服务器仍处于活动状态,则取消注册将调用{@link #stop stop}方法。
     * 如果<code> stop </code>方法抛出异常,注销尝试将失败。建议在取消注册MBean之前明确调用<code> stop </code>方法。</p>。
     * 
     * 
     * @exception IOException if thrown by the {@link #stop stop} method.
     */
    public synchronized void preDeregister() throws Exception {
        if (myName != null && isActive()) {
            stop();
            myName = null; // just in case stop is buggy and doesn't stop
        }
    }

    public void postDeregister() {
        myName = null;
    }

    /**
     * The MBeanServer used by this server to execute a client request.
     * <p>
     *  此服务器用于执行客户端请求的MBeanServer。
     * 
     */
    private MBeanServer mbeanServer = null;

    /**
     * The name used to registered this server in an MBeanServer.
     * It is null if the this server is not registered or has been unregistered.
     * <p>
     *  用于在MBeanServer中注册此服务器的名称。如果此服务器未注册或已注销,则为null。
     */
    private ObjectName myName;

    private final List<String> connectionIds = new ArrayList<String>();

    private static final int[] sequenceNumberLock = new int[0];
    private static long sequenceNumber;
}
