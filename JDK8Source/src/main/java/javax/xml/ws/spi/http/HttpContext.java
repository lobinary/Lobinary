/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws.spi.http;

import javax.xml.ws.Endpoint;
import java.util.Set;

/**
 * HttpContext represents a mapping between the root URI path of a web
 * service to a {@link HttpHandler} which is invoked to handle requests
 * destined for that path on the associated container.
 * <p>
 * Container provides the implementation for this and it matches
 * web service requests to corresponding HttpContext objects.
 *
 * <p>
 *  HttpContext表示Web服务的根URI路径到{@link HttpHandler}之间的映射,该请求被调用以处理发往相关容器上的该路径的请求。
 * <p>
 *  容器提供了这个的实现,它匹配Web服务请求到相应的HttpContext对象。
 * 
 * 
 * @author Jitendra Kotamraju
 * @since JAX-WS 2.2
 */
public abstract class HttpContext {

    protected HttpHandler handler;

    /**
     * JAX-WS runtime sets its handler during
     * {@link Endpoint#publish(HttpContext)} to handle
     * HTTP requests for this context. Container or its extensions
     * use this handler to process the requests.
     *
     * <p>
     *  JAX-WS运行时在{@link Endpoint#publish(HttpContext)}期间设置其处理程序以处理该上下文的HTTP请求。容器或其扩展使用此处理程序处理请求。
     * 
     * 
     * @param handler the handler to set for this context
     */
    public void setHandler(HttpHandler handler) {
        this.handler = handler;
    }

    /**
     * Returns the path for this context. This path uniquely identifies
     * an endpoint inside an application and the path is relative to
     * application's context path. Container should give this
     * path based on how it matches request URIs to this HttpContext object.
     *
     * <p>
     * For servlet container, this is typically a url-pattern for an endpoint.
     *
     * <p>
     * Endpoint's address for this context can be computed as follows:
     * <pre>
     *  HttpExchange exch = ...;
     *  String endpointAddress =
     *      exch.getScheme() + "://"
     *      + exch.getLocalAddress().getHostName()
     *      + ":" + exch.getLocalAddress().getPort()
     *      + exch.getContextPath() + getPath();
     * </pre>
     *
     * <p>
     *  返回此上下文的路径。此路径唯一标识应用程序内的端点,并且路径相对于应用程序的上下文路径。容器应该根据它如何匹配请求URI到这个HttpContext对象给出这个路径。
     * 
     * <p>
     *  对于servlet容器,这通常是端点的url模式。
     * 
     * <p>
     *  此上下文的端点地址可以计算如下：
     * <pre>
     *  HttpExchange exch = ...; String endpointAddress = exch.getScheme()+"：//"+ exch.getLocalAddress()。
     * 
     * @return this context's path
     */
    public abstract String getPath();

    /**
     * Returns an attribute value for container's configuration
     * and other data that can be used by jax-ws runtime.
     *
     * <p>
     * getHostName()+"："+ exch.getLocalAddress()getPort()+ exch.getContextPath()+ getPath。
     * </pre>
     * 
     * 
     * @param name attribute name
     * @return attribute value
     */
    public abstract Object getAttribute(String name);

    /**
     * Returns all attribute names for container's configuration
     * and other data that can be used by jax-ws runtime.
     *
     * <p>
     *  返回容器配置的属性值和可以由jax-ws runtime使用的其他数据。
     * 
     * 
     * @return set of all attribute names
     */
    public abstract Set<String> getAttributeNames();

}
