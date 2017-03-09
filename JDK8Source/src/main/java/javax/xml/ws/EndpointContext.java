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

package javax.xml.ws;

import javax.xml.ws.Endpoint;
import java.util.Set;

/**
 * <code>EndpointContext</code> allows multiple endpoints in an application
 * to share any information. For example, servlet application's war may
 * contain multiple endpoints and these endpoints can get addresses of each
 * other by sharing this context. If multiple endpoints share different
 * ports of a WSDL, then the multiple port addresses can be patched
 * when the WSDL is accessed. It also allows all endpoints to share any
 * other runtime information.
 *
 * <p>
 * This needs to be set by using {@link Endpoint#setEndpointContext}
 * before {@link Endpoint#publish} methods.
 *
 * <p>
 *  <code> EndpointContext </code>允许应用程序中的多个端点共享任何信息。例如,servlet应用程序的war可能包含多个端点,这些端点可以通过共享此上下文来获取彼此的地址。
 * 如果多个端点共享WSDL的不同端口,则可以在访问WSDL时修补多个端口地址。它还允许所有端点共享任何其他运行时信息。
 * 
 * <p>
 *  这需要通过在{@link Endpoint#publish}方法之前使用{@link Endpoint#setEndpointContext}来设置。
 * 
 * @author Jitendra Kotamraju
 * @since JAX-WS 2.2
 */
public abstract class EndpointContext {

    /**
     * This gives list of endpoints in an application. For e.g in
     * servlet container, a war file may contain multiple endpoints.
     * In case of http, these endpoints are hosted on the same http
     * server.
     *
     * <p>
     * 
     * 
     * @return list of all endpoints in an application
     */
    public abstract Set<Endpoint> getEndpoints();

}
