/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.security.auth.Subject;

/**
 * <p>The client end of a JMX API connector.  An object of this type can
 * be used to establish a connection to a connector server.</p>
 *
 * <p>A newly-created object of this type is unconnected.  Its {@link
 * #connect connect} method must be called before it can be used.
 * However, objects created by {@link
 * JMXConnectorFactory#connect(JMXServiceURL, Map)
 * JMXConnectorFactory.connect} are already connected.</p>
 *
 * <p>
 *  <p> JMX API连接器的客户端。此类型的对象可用于建立与连接器服务器的连接。</p>
 * 
 *  <p>此类型的新创建对象未连接。必须先调用它的{@link #connect connect}方法,然后才能使用它。
 * 但是,由{@link JMXConnectorFactory#connect(JMXServiceURL,Map)JMXConnectorFactory.connect}创建的对象已连接。</p>。
 * 
 * 
 * @since 1.5
 */
public interface JMXConnector extends Closeable {
    /**
      * <p>Name of the attribute that specifies the credentials to send
      * to the connector server during connection.  The value
      * associated with this attribute, if any, is a serializable
      * object of an appropriate type for the server's {@link
      * JMXAuthenticator}.
      * <p>
      *  <p>指定在连接期间发送到连接器服务器的凭据的属性的名称。与此属性相关联的值(如果有)是服务器的{@link JMXAuthenticator}的适当类型的可序列化对象。
      * 
      */
     public static final String CREDENTIALS =
         "jmx.remote.credentials";

    /**
     * <p>Establishes the connection to the connector server.  This
     * method is equivalent to {@link #connect(Map)
     * connect(null)}.</p>
     *
     * <p>
     *  <p>建立与连接器服务器的连接。此方法等效于{@link #connect(Map)connect(null)}。</p>
     * 
     * 
     * @exception IOException if the connection could not be made
     * because of a communication problem.
     *
     * @exception SecurityException if the connection could not be
     * made for security reasons.
     */
    public void connect() throws IOException;

    /**
     * <p>Establishes the connection to the connector server.</p>
     *
     * <p>If <code>connect</code> has already been called successfully
     * on this object, calling it again has no effect.  If, however,
     * {@link #close} was called after <code>connect</code>, the new
     * <code>connect</code> will throw an <code>IOException</code>.
     *
     * <p>Otherwise, either <code>connect</code> has never been called
     * on this object, or it has been called but produced an
     * exception.  Then calling <code>connect</code> will attempt to
     * establish a connection to the connector server.</p>
     *
     * <p>
     *  <p>建立与连接器服务器的连接。</p>
     * 
     *  <p>如果已经在此对象上成功调用<code> connect </code>,则再次调用此方法没有任何效果。
     * 但是,如果在<code> connect </code>之后调用{@link #close},新的<code> connect </code>将抛出<code> IOException </code>。
     *  <p>如果已经在此对象上成功调用<code> connect </code>,则再次调用此方法没有任何效果。
     * 
     *  <p>否则,<code> connect </code>从未在此对象上调用,或者它已被调用但产生了异常。然后调用<code> connect </code>将尝试建立与连接器服务器的连接。</p>
     * 
     * 
     * @param env the properties of the connection.  Properties in
     * this map override properties in the map specified when the
     * <code>JMXConnector</code> was created, if any.  This parameter
     * can be null, which is equivalent to an empty map.
     *
     * @exception IOException if the connection could not be made
     * because of a communication problem.
     *
     * @exception SecurityException if the connection could not be
     * made for security reasons.
     */
    public void connect(Map<String,?> env) throws IOException;

    /**
     * <p>Returns an <code>MBeanServerConnection</code> object
     * representing a remote MBean server.  For a given
     * <code>JMXConnector</code>, two successful calls to this method
     * will usually return the same <code>MBeanServerConnection</code>
     * object, though this is not required.</p>
     *
     * <p>For each method in the returned
     * <code>MBeanServerConnection</code>, calling the method causes
     * the corresponding method to be called in the remote MBean
     * server.  The value returned by the MBean server method is the
     * value returned to the client.  If the MBean server method
     * produces an <code>Exception</code>, the same
     * <code>Exception</code> is seen by the client.  If the MBean
     * server method, or the attempt to call it, produces an
     * <code>Error</code>, the <code>Error</code> is wrapped in a
     * {@link JMXServerErrorException}, which is seen by the
     * client.</p>
     *
     * <p>Calling this method is equivalent to calling
     * {@link #getMBeanServerConnection(Subject) getMBeanServerConnection(null)}
     * meaning that no delegation subject is specified and that all the
     * operations called on the <code>MBeanServerConnection</code> must
     * use the authenticated subject, if any.</p>
     *
     * <p>
     * <p>返回代表远程MBean服务器的<code> MBeanServerConnection </code>对象。
     * 对于给定的<code> JMXConnector </code>,对此方法的两个成功调用通常会返回相同的<code> MBeanServerConnection </code>对象,但这不是必需的。
     * </p>。
     * 
     *  <p>对于返回的<code> MBeanServerConnection </code>中的每个方法,调用该方法会导致在远程MBean服务器中调用相应的方法。
     *  MBean服务器方法返回的值是返回给客户端的值。如果MBean服务器方法产生<code>异常</code>,客户端看到相同的<code>异常</code>。
     * 如果MBean服务器方法或尝试调用它产生<code> Error </code>,则<code>错误</code>被包装在{@link JMXServerErrorException}中,客户端可以看到
     * 。
     *  MBean服务器方法返回的值是返回给客户端的值。如果MBean服务器方法产生<code>异常</code>,客户端看到相同的<code>异常</code>。 </p>。
     * 
     *  <p>调用此方法等效于调用{@link #getMBeanServerConnection(Subject)getMBeanServerConnection(null)},这意味着未指定委托主题,并且
     * 在<code> MBeanServerConnection </code>上调用的所有操作都必须使用已验证主题,如果有的话。
     * </p>。
     * 
     * 
     * @return an object that implements the
     * <code>MBeanServerConnection</code> interface by forwarding its
     * methods to the remote MBean server.
     *
     * @exception IOException if a valid
     * <code>MBeanServerConnection</code> cannot be created, for
     * instance because the connection to the remote MBean server has
     * not yet been established (with the {@link #connect(Map)
     * connect} method), or it has been closed, or it has broken.
     */
    public MBeanServerConnection getMBeanServerConnection()
            throws IOException;

    /**
     * <p>Returns an <code>MBeanServerConnection</code> object representing
     * a remote MBean server on which operations are performed on behalf of
     * the supplied delegation subject. For a given <code>JMXConnector</code>
     * and <code>Subject</code>, two successful calls to this method will
     * usually return the same <code>MBeanServerConnection</code> object,
     * though this is not required.</p>
     *
     * <p>For each method in the returned
     * <code>MBeanServerConnection</code>, calling the method causes
     * the corresponding method to be called in the remote MBean
     * server on behalf of the given delegation subject instead of the
     * authenticated subject. The value returned by the MBean server
     * method is the value returned to the client. If the MBean server
     * method produces an <code>Exception</code>, the same
     * <code>Exception</code> is seen by the client. If the MBean
     * server method, or the attempt to call it, produces an
     * <code>Error</code>, the <code>Error</code> is wrapped in a
     * {@link JMXServerErrorException}, which is seen by the
     * client.</p>
     *
     * <p>
     * <p>返回代表代表所提供的委托主题执行操作的远程MBean服务器的<code> MBeanServerConnection </code>对象。
     * 对于给定的<code> JMXConnector </code>和<code> Subject </code>,对此方法的两个成功调用通常会返回相同的<code> MBeanServerConnecti
     * on </code>对象, p>。
     * <p>返回代表代表所提供的委托主题执行操作的远程MBean服务器的<code> MBeanServerConnection </code>对象。
     * 
     *  <p>对于返回的<code> MBeanServerConnection </code>中的每个方法,调用该方法将导致在远程MBean服务器中代表给定的委托主题而不是已验证的主题来调用相应的方法。
     *  MBean服务器方法返回的值是返回给客户端的值。如果MBean服务器方法产生<code>异常</code>,客户端看到相同的<code>异常</code>。
     * 如果MBean服务器方法或尝试调用它产生<code> Error </code>,则<code>错误</code>被包装在{@link JMXServerErrorException}中,客户端可以看到
     * 。
     *  MBean服务器方法返回的值是返回给客户端的值。如果MBean服务器方法产生<code>异常</code>,客户端看到相同的<code>异常</code>。 </p>。
     * 
     * 
     * @param delegationSubject the <code>Subject</code> on behalf of
     * which requests will be performed.  Can be null, in which case
     * requests will be performed on behalf of the authenticated
     * Subject, if any.
     *
     * @return an object that implements the <code>MBeanServerConnection</code>
     * interface by forwarding its methods to the remote MBean server on behalf
     * of a given delegation subject.
     *
     * @exception IOException if a valid <code>MBeanServerConnection</code>
     * cannot be created, for instance because the connection to the remote
     * MBean server has not yet been established (with the {@link #connect(Map)
     * connect} method), or it has been closed, or it has broken.
     */
    public MBeanServerConnection getMBeanServerConnection(
                                               Subject delegationSubject)
            throws IOException;

    /**
     * <p>Closes the client connection to its server.  Any ongoing or new
     * request using the MBeanServerConnection returned by {@link
     * #getMBeanServerConnection()} will get an
     * <code>IOException</code>.</p>
     *
     * <p>If <code>close</code> has already been called successfully
     * on this object, calling it again has no effect.  If
     * <code>close</code> has never been called, or if it was called
     * but produced an exception, an attempt will be made to close the
     * connection.  This attempt can succeed, in which case
     * <code>close</code> will return normally, or it can generate an
     * exception.</p>
     *
     * <p>Closing a connection is a potentially slow operation.  For
     * example, if the server has crashed, the close operation might
     * have to wait for a network protocol timeout.  Callers that do
     * not want to block in a close operation should do it in a
     * separate thread.</p>
     *
     * <p>
     *  <p>关闭与其服务器的客户端连接。
     * 使用{@link #getMBeanServerConnection()}返回的MBeanServerConnection的任何正在进行的或新的请求都会获得<code> IOException </code>
     * 。
     *  <p>关闭与其服务器的客户端连接。</p>。
     * 
     * <p>如果已经在此对象上成功调用<code> close </code>,则再次调用此对象没有任何效果。
     * 如果<code> close </code>从未被调用,或者如果它被调用但产生了异常,将尝试关闭连接。
     * 这个尝试可以成功,在这种情况下,<code> close </code>会正常返回,否则会产生异常。</p>。
     * 
     *  <p>关闭连接是一个潜在的缓慢操作。例如,如果服务器崩溃,关闭操作可能必须等待网络协议超时。不想在关闭操作中阻止的调用方应在单独的线程中执行。</p>
     * 
     * 
     * @exception IOException if the connection cannot be closed
     * cleanly.  If this exception is thrown, it is not known whether
     * the server end of the connection has been cleanly closed.
     */
    public void close() throws IOException;

    /**
     * <p>Adds a listener to be informed of changes in connection
     * status.  The listener will receive notifications of type {@link
     * JMXConnectionNotification}.  An implementation can send other
     * types of notifications too.</p>
     *
     * <p>Any number of listeners can be added with this method.  The
     * same listener can be added more than once with the same or
     * different values for the filter and handback.  There is no
     * special treatment of a duplicate entry.  For example, if a
     * listener is registered twice with no filter, then its
     * <code>handleNotification</code> method will be called twice for
     * each notification.</p>
     *
     * <p>
     *  <p>添加监听器以通知连接状态的更改。侦听器将接收{@link JMXConnectionNotification}类型的通知。一个实现也可以发送其他类型的通知。</p>
     * 
     *  <p>可以使用此方法添加任意数量的侦听器。可以多次添加相同的侦听器,并为过滤器和回传使用相同或不同的值。没有对重复条目的特殊处理。
     * 例如,如果监听器注册两次没有过滤器,那么每个通知的<code> handleNotification </code>方法将被调用两次。</p>。
     * 
     * 
     * @param listener a listener to receive connection status
     * notifications.
     * @param filter a filter to select which notifications are to be
     * delivered to the listener, or null if all notifications are to
     * be delivered.
     * @param handback an object to be given to the listener along
     * with each notification.  Can be null.
     *
     * @exception NullPointerException if <code>listener</code> is
     * null.
     *
     * @see #removeConnectionNotificationListener
     * @see javax.management.NotificationBroadcaster#addNotificationListener
     */
    public void
        addConnectionNotificationListener(NotificationListener listener,
                                          NotificationFilter filter,
                                          Object handback);

    /**
     * <p>Removes a listener from the list to be informed of changes
     * in status.  The listener must previously have been added.  If
     * there is more than one matching listener, all are removed.</p>
     *
     * <p>
     *  <p>从列表中删除侦听器以通知状态更改。侦听器必须先前已添加。如果有多个匹配的侦听器,则会删除所有。</p>
     * 
     * 
     * @param listener a listener to receive connection status
     * notifications.
     *
     * @exception NullPointerException if <code>listener</code> is
     * null.
     *
     * @exception ListenerNotFoundException if the listener is not
     * registered with this <code>JMXConnector</code>.
     *
     * @see #removeConnectionNotificationListener(NotificationListener,
     * NotificationFilter, Object)
     * @see #addConnectionNotificationListener
     * @see javax.management.NotificationEmitter#removeNotificationListener
     */
    public void
        removeConnectionNotificationListener(NotificationListener listener)
            throws ListenerNotFoundException;

    /**
     * <p>Removes a listener from the list to be informed of changes
     * in status.  The listener must previously have been added with
     * the same three parameters.  If there is more than one matching
     * listener, only one is removed.</p>
     *
     * <p>
     * <p>从列表中删除侦听器以通知状态更改。侦听器必须预先添加了相同的三个参数。如果有多个匹配的侦听器,则只会删除一个。</p>
     * 
     * 
     * @param l a listener to receive connection status notifications.
     * @param f a filter to select which notifications are to be
     * delivered to the listener.  Can be null.
     * @param handback an object to be given to the listener along
     * with each notification.  Can be null.
     *
     * @exception ListenerNotFoundException if the listener is not
     * registered with this <code>JMXConnector</code>, or is not
     * registered with the given filter and handback.
     *
     * @see #removeConnectionNotificationListener(NotificationListener)
     * @see #addConnectionNotificationListener
     * @see javax.management.NotificationEmitter#removeNotificationListener
     */
    public void removeConnectionNotificationListener(NotificationListener l,
                                                     NotificationFilter f,
                                                     Object handback)
            throws ListenerNotFoundException;

    /**
     * <p>Gets this connection's ID from the connector server.  For a
     * given connector server, every connection will have a unique id
     * which does not change during the lifetime of the
     * connection.</p>
     *
     * <p>
     *  <p>从连接器服务器获取此连接的ID。对于给定的连接器服务器,每个连接都将具有唯一的ID,在连接的生命周期内不会更改。</p>
     * 
     * @return the unique ID of this connection.  This is the same as
     * the ID that the connector server includes in its {@link
     * JMXConnectionNotification}s.  The {@link
     * javax.management.remote package description} describes the
     * conventions for connection IDs.
     *
     * @exception IOException if the connection ID cannot be obtained,
     * for instance because the connection is closed or broken.
     */
    public String getConnectionId() throws IOException;
}
