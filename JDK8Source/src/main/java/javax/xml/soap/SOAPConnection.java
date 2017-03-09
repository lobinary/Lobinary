/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;


/**
 * A point-to-point connection that a client can use for sending messages
 * directly to a remote party (represented by a URL, for instance).
 * <p>
 * The SOAPConnection class is optional. Some implementations may
 * not implement this interface in which case the call to
 * <code>SOAPConnectionFactory.newInstance()</code> (see below) will
 * throw an <code>UnsupportedOperationException</code>.
 * <p>
 * A client can obtain a <code>SOAPConnection</code> object using a
 * {@link SOAPConnectionFactory} object as in the following example:
 * <PRE>
 *      SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
 *      SOAPConnection con = factory.createConnection();
 * </PRE>
 * A <code>SOAPConnection</code> object can be used to send messages
 * directly to a URL following the request/response paradigm.  That is,
 * messages are sent using the method <code>call</code>, which sends the
 * message and then waits until it gets a reply.
 * <p>
 *  客户端可用于将消息直接发送到远程方(例如,由URL表示)的点对点连接。
 * <p>
 *  SOAPConnection类是可选的。
 * 一些实现可能不实现此接口,在这种情况下,调用<code> SOAPConnectionFactory.newInstance()</code>(见下文)将抛出一个<code> UnsupportedOp
 * erationException </code>。
 *  SOAPConnection类是可选的。
 * <p>
 *  客户端可以使用{@link SOAPConnectionFactory}对象获取<code> SOAPConnection </code>对象,如以下示例所示：
 * <PRE>
 *  SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance(); SOAPConnection con = factory.cr
 * eateConnection();。
 * </PRE>
 */
public abstract class SOAPConnection {

    /**
     * Sends the given message to the specified endpoint and blocks until
     * it has returned the response.
     *
     * <p>
     *  可以使用<code> SOAPConnection </code>对象在请求/响应范例之后将消息直接发送到URL。
     * 也就是说,消息使用方法<code> call </code>发送,它发送消息,然后等待,直到它得到答复。
     * 
     * 
     * @param request the <code>SOAPMessage</code> object to be sent
     * @param to an <code>Object</code> that identifies
     *         where the message should be sent. It is required to
     *         support Objects of type
     *         <code>java.lang.String</code>,
     *         <code>java.net.URL</code>, and when JAXM is present
     *         <code>javax.xml.messaging.URLEndpoint</code>
     *
     * @return the <code>SOAPMessage</code> object that is the response to the
     *         message that was sent
     * @throws SOAPException if there is a SOAP error
     */
    public abstract SOAPMessage call(SOAPMessage request,
                                     Object to) throws SOAPException;

    /**
     * Gets a message from a specific endpoint and blocks until it receives,
     *
     * <p>
     *  将给定消息发送到指定的端点并阻塞,直到它返回响应。
     * 
     * 
     * @param to an <code>Object</code> that identifies where
     *                  the request should be sent. Objects of type
     *                 <code>java.lang.String</code> and
     *                 <code>java.net.URL</code> must be supported.
     *
     * @return the <code>SOAPMessage</code> object that is the response to the
     *                  get message request
     * @throws SOAPException if there is a SOAP error
     * @since SAAJ 1.3
     */
    public SOAPMessage get(Object to)
                                throws SOAPException {
        throw new UnsupportedOperationException("All subclasses of SOAPConnection must override get()");
    }

    /**
     * Closes this <code>SOAPConnection</code> object.
     *
     * <p>
     *  从特定端点获取消息并阻塞,直到接收到消息,
     * 
     * 
     * @throws SOAPException if there is a SOAP error
     */
    public abstract void close()
        throws SOAPException;
}
