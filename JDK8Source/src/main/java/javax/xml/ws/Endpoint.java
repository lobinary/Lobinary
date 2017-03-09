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

import java.util.List;
import java.util.Map;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.w3c.dom.Element;


/**
 * A Web service endpoint.
 *
 * <p>Endpoints are created using the static methods defined in this
 * class. An endpoint is always tied to one <code>Binding</code>
 * and one implementor, both set at endpoint creation time.
 *
 * <p>An endpoint is either in a published or an unpublished state.
 * The <code>publish</code> methods can be used to start publishing
 * an endpoint, at which point it starts accepting incoming requests.
 * Conversely, the <code>stop</code> method can be used to stop
 * accepting incoming requests and take the endpoint down.
 * Once stopped, an endpoint cannot be published again.
 *
 * <p>An <code>Executor</code> may be set on the endpoint in order
 * to gain better control over the threads used to dispatch incoming
 * requests. For instance, thread pooling with certain parameters
 * can be enabled by creating a <code>ThreadPoolExecutor</code> and
 * registering it with the endpoint.
 *
 * <p>Handler chains can be set using the contained <code>Binding</code>.
 *
 * <p>An endpoint may have a list of metadata documents, such as WSDL
 * and XMLSchema documents, bound to it. At publishing time, the
 * JAX-WS implementation will try to reuse as much of that metadata
 * as possible instead of generating new ones based on the annotations
 * present on the implementor.
 *
 * <p>
 *  Web服务端点。
 * 
 *  <p>使用此类中定义的静态方法创建端点。端点总是绑定到一个<code>绑定</code>和一个实现者,都在端点创建时设置。
 * 
 *  <p>端点处于已发布状态或未发布状态。 <code> publish </code>方法可用于开始发布端点,此时它开始接受传入请求。
 * 相反,可以使用<code> stop </code>方法停止接受传入的请求并关闭端点。一旦停止,将无法再次发布端点。
 * 
 *  <p>可以在端点上设置<code> Executor </code>,以便更好地控制用于分派传入请求的线程。
 * 例如,可以通过创建<code> ThreadPoolExecutor </code>并将其注册到端点来启用具有某些参数的线程池。
 * 
 *  <p>可以使用包含的<code> Binding </code>设置处理程序链。
 * 
 *  <p>端点可能具有与其绑定的元数据文档列表,例如WSDL和XMLSchema文档。在发布时,JAX-WS实现将尝试重用尽可能多的元数据,而不是基于实现者上存在的注释生成新的元数据。
 * 
 * 
 * @since JAX-WS 2.0
 *
 * @see javax.xml.ws.Binding
 * @see javax.xml.ws.BindingType
 * @see javax.xml.ws.soap.SOAPBinding
 * @see java.util.concurrent.Executor
 *
 **/
public abstract class Endpoint {

    /** Standard property: name of WSDL service.
     *  <p>Type: javax.xml.namespace.QName
     * <p>
     *  <p>类型：javax.xml.namespace.QName
     * 
     * 
     **/
    public static final String WSDL_SERVICE = "javax.xml.ws.wsdl.service";

    /** Standard property: name of WSDL port.
     *  <p>Type: javax.xml.namespace.QName
     * <p>
     *  <p>类型：javax.xml.namespace.QName
     * 
     * 
     **/
    public static final String WSDL_PORT = "javax.xml.ws.wsdl.port";


    /**
     * Creates an endpoint with the specified implementor object. If there is
     * a binding specified via a BindingType annotation then it MUST be used else
     * a default of SOAP 1.1 / HTTP binding MUST be used.
     * <p>
     * The newly created endpoint may be published by calling
     * one of the {@link javax.xml.ws.Endpoint#publish(String)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods.
     *
     *
     * <p>
     * 使用指定的实现器对象创建端点。如果存在通过BindingType注释指定的绑定,则必须使用否则必须使用默认的SOAP 1.1 / HTTP绑定。
     * <p>
     *  新创建的端点可以通过调用{@link javax.xml.ws.Endpoint#publish(String)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法之一发布。
     * 
     * 
     * @param implementor The endpoint implementor.
     *
     * @return The newly created endpoint.
     *
     **/
    public static Endpoint create(Object implementor) {
        return create(null, implementor);
    }

    /**
     * Creates an endpoint with the specified implementor object and web
     * service features. If there is a binding specified via a BindingType
     * annotation then it MUST be used else a default of SOAP 1.1 / HTTP
     * binding MUST be used.
     * <p>
     * The newly created endpoint may be published by calling
     * one of the {@link javax.xml.ws.Endpoint#publish(String)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods.
     *
     *
     * <p>
     *  创建具有指定的实现者对象和Web服务功能的端点。如果存在通过BindingType注释指定的绑定,则必须使用否则必须使用默认的SOAP 1.1 / HTTP绑定。
     * <p>
     *  新创建的端点可以通过调用{@link javax.xml.ws.Endpoint#publish(String)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法之一发布。
     * 
     * 
     * @param implementor The endpoint implementor.
     * @param features A list of WebServiceFeature to configure on the
     *        endpoint. Supported features not in the <code>features
     *        </code> parameter will have their default values.
     *
     *
     * @return The newly created endpoint.
     * @since JAX-WS 2.2
     *
     */
    public static Endpoint create(Object implementor, WebServiceFeature ... features) {
        return create(null, implementor, features);
    }

    /**
     * Creates an endpoint with the specified binding type and
     * implementor object.
     * <p>
     * The newly created endpoint may be published by calling
     * one of the {@link javax.xml.ws.Endpoint#publish(String)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods.
     *
     * <p>
     *  创建具有指定的绑定类型和实现器对象的端点。
     * <p>
     *  新创建的端点可以通过调用{@link javax.xml.ws.Endpoint#publish(String)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法之一发布。
     * 
     * 
     * @param bindingId A URI specifying the binding to use. If the bindingID is
     * <code>null</code> and no binding is specified via a BindingType
     * annotation then a default SOAP 1.1 / HTTP binding MUST be used.
     *
     * @param implementor The endpoint implementor.
     *
     * @return The newly created endpoint.
     *
     **/
    public static Endpoint create(String bindingId, Object implementor) {
        return Provider.provider().createEndpoint(bindingId, implementor);
    }

    /**
     * Creates an endpoint with the specified binding type,
     * implementor object, and web service features.
     * <p>
     * The newly created endpoint may be published by calling
     * one of the {@link javax.xml.ws.Endpoint#publish(String)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods.
     *
     * <p>
     *  创建具有指定的绑定类型,实现器对象和Web服务功能的端点。
     * <p>
     *  新创建的端点可以通过调用{@link javax.xml.ws.Endpoint#publish(String)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法之一发布。
     * 
     * 
     * @param bindingId A URI specifying the binding to use. If the bindingID is
     * <code>null</code> and no binding is specified via a BindingType
     * annotation then a default SOAP 1.1 / HTTP binding MUST be used.
     *
     * @param implementor The endpoint implementor.
     *
     * @param features A list of WebServiceFeature to configure on the
     *        endpoint. Supported features not in the <code>features
     *        </code> parameter will have their default values.
     *
     * @return The newly created endpoint.
     * @since JAX-WS 2.2
     */
    public static Endpoint create(String bindingId, Object implementor, WebServiceFeature ... features) {
        return Provider.provider().createEndpoint(bindingId, implementor, features);
    }

    /**
     * Returns the binding for this endpoint.
     *
     * <p>
     *  返回此端点的绑定。
     * 
     * 
     * @return The binding for this endpoint
     **/
    public abstract Binding getBinding();

    /**
     * Returns the implementation object for this endpoint.
     *
     * <p>
     *  返回此端点的实现对象。
     * 
     * 
     * @return The implementor for this endpoint
     **/
    public abstract Object getImplementor();

    /**
     * Publishes this endpoint at the given address.
     * The necessary server infrastructure will be created and
     * configured by the JAX-WS implementation using some default configuration.
     * In order to get more control over the server configuration, please
     * use the {@link javax.xml.ws.Endpoint#publish(Object)} method instead.
     *
     * <p>
     * 在给定地址发布此端点。必要的服务器基础结构将由JAX-WS实现使用某些默认配置创建和配置。
     * 为了更好地控制服务器配置,请改用{@link javax.xml.ws.Endpoint#publish(Object)}方法。
     * 
     * 
     * @param address A URI specifying the address to use. The address
     *        MUST be compatible with the binding specified at the
     *        time the endpoint was created.
     *
     * @throws java.lang.IllegalArgumentException
     *          If the provided address URI is not usable
     *          in conjunction with the endpoint's binding.
     *
     * @throws java.lang.IllegalStateException
     *          If the endpoint has been published already or it has been stopped.
     *
     * @throws java.lang.SecurityException
     *          If a <code>java.lang.SecurityManger</code>
     *          is being used and the application doesn't have the
     *          <code>WebServicePermission("publishEndpoint")</code> permission.
     **/
    public abstract void publish(String address);

    /**
     * Creates and publishes an endpoint for the specified implementor
     * object at the given address.
     * <p>
     * The necessary server infrastructure will be created and
     * configured by the JAX-WS implementation using some default configuration.
     *
     * In order to get more control over the server configuration, please
     * use the {@link javax.xml.ws.Endpoint#create(String,Object)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods instead.
     *
     * <p>
     *  在给定地址为指定的实现者对象创建和发布一个端点。
     * <p>
     *  必要的服务器基础结构将由JAX-WS实现使用某些默认配置创建和配置。
     * 
     *  为了更好地控制服务器配置,请使用{@link javax.xml.ws.Endpoint#create(String,Object)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法代替。
     * 
     * 
     * @param address A URI specifying the address and transport/protocol
     *        to use. A http: URI MUST result in the SOAP 1.1/HTTP
     *        binding being used. Implementations may support other
     *        URI schemes.
     * @param implementor The endpoint implementor.
     *
     * @return The newly created endpoint.
     *
     * @throws java.lang.SecurityException
     *          If a <code>java.lang.SecurityManger</code>
     *          is being used and the application doesn't have the
     *          <code>WebServicePermission("publishEndpoint")</code> permission.
     *
     **/
    public static Endpoint publish(String address, Object implementor) {
        return Provider.provider().createAndPublishEndpoint(address, implementor);
    }

    /**
     * Creates and publishes an endpoint for the specified implementor
     * object at the given address. The created endpoint is configured
     * with the web service features.
     * <p>
     * The necessary server infrastructure will be created and
     * configured by the JAX-WS implementation using some default configuration.
     *
     * In order to get more control over the server configuration, please
     * use the {@link javax.xml.ws.Endpoint#create(String,Object)} and
     * {@link javax.xml.ws.Endpoint#publish(Object)} methods instead.
     *
     * <p>
     *  在给定地址为指定的实现者对象创建和发布一个端点。创建的端点配置有Web服务功能。
     * <p>
     *  必要的服务器基础结构将由JAX-WS实现使用某些默认配置创建和配置。
     * 
     *  为了更好地控制服务器配置,请使用{@link javax.xml.ws.Endpoint#create(String,Object)}和{@link javax.xml.ws.Endpoint#publish(Object)}
     * 方法代替。
     * 
     * 
     * @param address A URI specifying the address and transport/protocol
     *        to use. A http: URI MUST result in the SOAP 1.1/HTTP
     *        binding being used. Implementations may support other
     *        URI schemes.
     * @param implementor The endpoint implementor.
     * @param features A list of WebServiceFeature to configure on the
     *        endpoint. Supported features not in the <code>features
     *        </code> parameter will have their default values.
     * @return The newly created endpoint.
     *
     * @throws java.lang.SecurityException
     *          If a <code>java.lang.SecurityManger</code>
     *          is being used and the application doesn't have the
     *          <code>WebServicePermission("publishEndpoint")</code> permission.
     * @since JAX-WS 2.2
     */
    public static Endpoint publish(String address, Object implementor, WebServiceFeature ... features) {
        return Provider.provider().createAndPublishEndpoint(address, implementor, features);
    }


    /**
     * Publishes this endpoint at the provided server context.
     * A server context encapsulates the server infrastructure
     * and addressing information for a particular transport.
     * For a call to this method to succeed, the server context
     * passed as an argument to it MUST be compatible with the
     * endpoint's binding.
     *
     * <p>
     * 在提供的服务器上下文发布此端点。服务器上下文封装了特定传输的服务器基础结构和寻址信息。对于调用此方法成功,作为参数传递给它的服务器上下文必须与端点的绑定兼容。
     * 
     * 
     * @param serverContext An object representing a server
     *           context to be used for publishing the endpoint.
     *
     * @throws java.lang.IllegalArgumentException
     *              If the provided server context is not
     *              supported by the implementation or turns
     *              out to be unusable in conjunction with the
     *              endpoint's binding.
     *
     * @throws java.lang.IllegalStateException
     *         If the endpoint has been published already or it has been stopped.
     *
     * @throws java.lang.SecurityException
     *          If a <code>java.lang.SecurityManger</code>
     *          is being used and the application doesn't have the
     *          <code>WebServicePermission("publishEndpoint")</code> permission.
     **/
    public abstract void publish(Object serverContext);

    /**
     * Publishes this endpoint at the provided server context.
     * A server context encapsulates the server infrastructure
     * and addressing information for a particular transport.
     * For a call to this method to succeed, the server context
     * passed as an argument to it MUST be compatible with the
     * endpoint's binding.
     *
     * <p>
     * This is meant for container developers to publish the
     * the endpoints portably and not intended for the end
     * developers.
     *
     *
     * <p>
     *  在提供的服务器上下文发布此端点。服务器上下文封装了特定传输的服务器基础结构和寻址信息。对于调用此方法成功,作为参数传递给它的服务器上下文必须与端点的绑定兼容。
     * 
     * <p>
     *  这是为了容器开发人员可移植地发布端点,而不是为最终开发人员。
     * 
     * 
     * @param serverContext An object representing a server
     *           context to be used for publishing the endpoint.
     *
     * @throws java.lang.IllegalArgumentException
     *              If the provided server context is not
     *              supported by the implementation or turns
     *              out to be unusable in conjunction with the
     *              endpoint's binding.
     *
     * @throws java.lang.IllegalStateException
     *         If the endpoint has been published already or it has been stopped.
     *
     * @throws java.lang.SecurityException
     *          If a <code>java.lang.SecurityManger</code>
     *          is being used and the application doesn't have the
     *          <code>WebServicePermission("publishEndpoint")</code> permission.
     * @since JAX-WS 2.2
     */
    public void publish(HttpContext serverContext) {
        throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
    }

    /**
     * Stops publishing this endpoint.
     *
     * If the endpoint is not in a published state, this method
     * has no effect.
     *
     * <p>
     *  停止发布此端点。
     * 
     *  如果端点不处于已发布状态,则此方法无效。
     * 
     * 
     **/
    public abstract void stop();

    /**
     * Returns true if the endpoint is in the published state.
     *
     * <p>
     *  如果端点处于已发布状态,则返回true。
     * 
     * 
     * @return <code>true</code> if the endpoint is in the published state.
     **/
    public abstract boolean isPublished();

    /**
     * Returns a list of metadata documents for the service.
     *
     * <p>
     *  返回服务的元数据文档列表。
     * 
     * 
     * @return <code>List&lt;javax.xml.transform.Source&gt;</code> A list of metadata documents for the service
     **/
    public abstract List<javax.xml.transform.Source> getMetadata();

    /**
     * Sets the metadata for this endpoint.
     *
     * <p>
     *  设置此端点的元数据。
     * 
     * 
     * @param metadata A list of XML document sources containing
     *           metadata information for the endpoint (e.g.
     *           WSDL or XML Schema documents)
     *
     * @throws java.lang.IllegalStateException  If the endpoint
     *         has already been published.
     **/
    public abstract void setMetadata(List<javax.xml.transform.Source> metadata);

    /**
     * Returns the executor for this <code>Endpoint</code>instance.
     *
     * The executor is used to dispatch an incoming request to
     * the implementor object.
     *
     * <p>
     *  返回此<code> Endpoint </code>实例的执行程序。
     * 
     *  执行器用于将传入请求分派给实现者对象。
     * 
     * 
     * @return The <code>java.util.concurrent.Executor</code> to be
     *         used to dispatch a request.
     *
     * @see java.util.concurrent.Executor
     **/
    public abstract java.util.concurrent.Executor getExecutor();

    /**
     * Sets the executor for this <code>Endpoint</code> instance.
     *
     * The executor is used to dispatch an incoming request to
     * the implementor object.
     *
     * If this <code>Endpoint</code> is published using the
     * <code>publish(Object)</code> method and the specified server
     * context defines its own threading behavior, the executor
     * may be ignored.
     *
     * <p>
     *  设置<code> Endpoint </code>实例的执行器。
     * 
     *  执行器用于将传入请求分派给实现者对象。
     * 
     *  如果使用<code> publish(Object)</code>方法发布此<code> Endpoint </code>,并且指定的服务器上下文定义了自己的线程行为,则可以忽略执行器。
     * 
     * 
     * @param executor The <code>java.util.concurrent.Executor</code>
     *        to be used to dispatch a request.
     *
     * @throws SecurityException  If the instance does not support
     *         setting an executor for security reasons (e.g. the
     *         necessary permissions are missing).
     *
     * @see java.util.concurrent.Executor
     **/
    public abstract void setExecutor(java.util.concurrent.Executor executor);


    /**
     * Returns the property bag for this <code>Endpoint</code> instance.
     *
     * <p>
     * 返回此<code> Endpoint </code>实例的属性包。
     * 
     * 
     * @return Map&lt;String,Object&gt; The property bag
     *         associated with this instance.
     **/
    public abstract Map<String,Object> getProperties();

    /**
     * Sets the property bag for this <code>Endpoint</code> instance.
     *
     * <p>
     *  为此<code> Endpoint </code>实例设置属性包。
     * 
     * 
     * @param properties The property bag associated with
     *        this instance.
     **/
    public abstract void setProperties(Map<String,Object> properties);

    /**
     * Returns the <code>EndpointReference</code> associated with
     * this <code>Endpoint</code> instance.
     * <p>
     * If the Binding for this <code>bindingProvider</code> is
     * either SOAP1.1/HTTP or SOAP1.2/HTTP, then a
     * <code>W3CEndpointReference</code> MUST be returned.
     *
     * <p>
     *  返回与此<code> Endpoint </code>实例关联的<code> EndpointReference </code>。
     * <p>
     *  如果这个<code> bindingProvider </code>的绑定是SOAP1.1 / HTTP或SOAP1.2 / HTTP,那么必须返回一个<code> W3CEndpointRefere
     * nce </code>。
     * 
     * 
     * @param referenceParameters Reference parameters to be associated with the
     * returned <code>EndpointReference</code> instance.
     * @return EndpointReference of this <code>Endpoint</code> instance.
     * If the returned <code>EndpointReference</code> is of type
     * <code>W3CEndpointReference</code> then it MUST contain the
     * the specified <code>referenceParameters</code>.

     * @throws WebServiceException If any error in the creation of
     * the <code>EndpointReference</code> or if the <code>Endpoint</code> is
     * not in the published state.
     * @throws UnsupportedOperationException If this <code>BindingProvider</code>
     * uses the XML/HTTP binding.
     *
     * @see W3CEndpointReference
     *
     * @since JAX-WS 2.1
     **/
    public abstract EndpointReference getEndpointReference(Element... referenceParameters);


    /**
     * Returns the <code>EndpointReference</code> associated with
     * this <code>Endpoint</code> instance.
     *
     * <p>
     *  返回与此<code> Endpoint </code>实例关联的<code> EndpointReference </code>。
     * 
     * 
     * @param clazz Specifies the type of EndpointReference  that MUST be returned.
     * @param referenceParameters Reference parameters to be associated with the
     * returned <code>EndpointReference</code> instance.
     * @return EndpointReference of type <code>clazz</code> of this
     * <code>Endpoint</code> instance.
     * If the returned <code>EndpointReference</code> is of type
     * <code>W3CEndpointReference</code> then it MUST contain the
     * the specified <code>referenceParameters</code>.

     * @throws WebServiceException If any error in the creation of
     * the <code>EndpointReference</code> or if the <code>Endpoint</code> is
     * not in the published state or if the <code>clazz</code> is not a supported
     * <code>EndpointReference</code> type.
     * @throws UnsupportedOperationException If this <code>BindingProvider</code>
     * uses the XML/HTTP binding.
     *
     *
     * @since JAX-WS 2.1
     **/
    public abstract <T extends EndpointReference> T getEndpointReference(Class<T> clazz,
            Element... referenceParameters);

    /**
     * By settng a <code>EndpointContext</code>, JAX-WS runtime knows about
     * addresses of other endpoints in an application. If multiple endpoints
     * share different ports of a WSDL, then the multiple port addresses
     * are patched when the WSDL is accessed.
     *
     * <p>
     * This needs to be set before publishing the endpoints.
     *
     * <p>
     *  通过设置<code> EndpointContext </code>,JAX-WS运行时知道应用程序中其他端点的地址。如果多个端点共享WSDL的不同端口,则在访问WSDL时修补多个端口地址。
     * 
     * <p>
     * 
     * @param ctxt that is shared for multiple endpoints
     * @throws java.lang.IllegalStateException
     *        If the endpoint has been published already or it has been stopped.
     *
     * @since JAX-WS 2.2
     */
    public void setEndpointContext(EndpointContext ctxt) {
        throw new UnsupportedOperationException("JAX-WS 2.2 implementation must override this default behaviour.");
    }
}
