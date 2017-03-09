/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;

import java.security.Principal;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.w3c.dom.Element;


/**
 *  A <code>WebServiceContext</code> makes it possible for
 *  a web service endpoint implementation class to access
 *  message context and security information relative to
 *  a request being served.
 *
 *  Typically a <code>WebServiceContext</code> is injected
 *  into an endpoint implementation class using the
 *  <code>Resource</code> annotation.
 *
 * <p>
 *  <code> WebServiceContext </code>使得Web服务端点实现类可以访问与正在提供的请求相关的消息上下文和安全信息。
 * 
 *  通常,使用<code> Resource </code>注释将<code> WebServiceContext </code>注入到端点实现类中。
 * 
 * 
 *  @since JAX-WS 2.0
 *
 *  @see javax.annotation.Resource
 **/
public interface WebServiceContext {

    /**
     * Returns the <code>MessageContext</code> for the request being served
     * at the time this method is called. Only properties with
     * APPLICATION scope will be visible to the application.
     *
     * <p>
     *  返回在调用此方法时正在投放的请求的<code> MessageContext </code>。只有具有APPLICATION范围的属性将对应用程序可见。
     * 
     * 
     * @return MessageContext The message context.
     *
     * @throws IllegalStateException This exception is thrown
     *         if the method is called while no request is
     *         being serviced.
     *
     * @see javax.xml.ws.handler.MessageContext
     * @see javax.xml.ws.handler.MessageContext.Scope
     * @see java.lang.IllegalStateException
     **/
    public MessageContext getMessageContext();

    /**
     * Returns the Principal that identifies the sender
     * of the request currently being serviced. If the
     * sender has not been authenticated, the method
     * returns <code>null</code>.
     *
     * <p>
     *  返回标识当前正在服务的请求的发件人的Principal。如果发送方未经认证,则该方法返回<code> null </code>。
     * 
     * 
     * @return Principal The principal object.
     *
     * @throws IllegalStateException This exception is thrown
     *         if the method is called while no request is
     *         being serviced.
     *
     * @see java.security.Principal
     * @see java.lang.IllegalStateException
     **/
    public Principal getUserPrincipal();

    /**
     * Returns a boolean indicating whether the
     * authenticated user is included in the specified
     * logical role. If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * <p>
     *  返回一个布尔值,指示经过身份验证的用户是否包含在指定的逻辑角色中。如果用户未经过身份验证,则该方法返回<code> false </code>。
     * 
     * 
     * @param role  A <code>String</code> specifying the name of the role
     *
     * @return a <code>boolean</code> indicating whether
     * the sender of the request belongs to a given role
     *
     * @throws IllegalStateException This exception is thrown
     *         if the method is called while no request is
     *         being serviced.
     **/
    public boolean isUserInRole(String role);

    /**
     * Returns the <code>EndpointReference</code> for this
     * endpoint.
     * <p>
     * If the {@link Binding} for this <code>bindingProvider</code> is
     * either SOAP1.1/HTTP or SOAP1.2/HTTP, then a
     * <code>W3CEndpointReference</code> MUST be returned.
     *
     * <p>
     *  返回此端点的<code> EndpointReference </code>。
     * <p>
     *  如果这个<code> bindingProvider </code>的{@link Binding}是SOAP1.1 / HTTP或SOAP1.2 / HTTP,那么必须返回一个<code> W3CE
     * ndpointReference </code>。
     * 
     * @param referenceParameters Reference parameters to be associated with the
     * returned <code>EndpointReference</code> instance.
     * @return EndpointReference of the endpoint associated with this
     * <code>WebServiceContext</code>.
     * If the returned <code>EndpointReference</code> is of type
     * <code>W3CEndpointReference</code> then it MUST contain the
     * the specified <code>referenceParameters</code>.
     *
     * @throws IllegalStateException This exception is thrown
     *         if the method is called while no request is
     *         being serviced.
     *
     * @see W3CEndpointReference
     *
     * @since JAX-WS 2.1
     */
    public EndpointReference getEndpointReference(Element... referenceParameters);

    /**
     * Returns the <code>EndpointReference</code> associated with
     * this endpoint.
     *
     * <p>
     * 
     * 
     * @param clazz The type of <code>EndpointReference</code> that
     * MUST be returned.
     * @param referenceParameters Reference parameters to be associated with the
     * returned <code>EndpointReference</code> instance.
     * @return EndpointReference of type <code>clazz</code> of the endpoint
     * associated with this <code>WebServiceContext</code> instance.
     * If the returned <code>EndpointReference</code> is of type
     * <code>W3CEndpointReference</code> then it MUST contain the
     * the specified <code>referenceParameters</code>.
     *
     * @throws IllegalStateException This exception is thrown
     *         if the method is called while no request is
     *         being serviced.
     * @throws WebServiceException If the <code>clazz</code> type of
     * <code>EndpointReference</code> is not supported.
     *
     * @since JAX-WS 2.1
     **/
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz,
            Element... referenceParameters);
}
