/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.namespace.QName;
import java.util.Iterator;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.ws.spi.ServiceDelegate;
import javax.xml.ws.spi.Provider;

/**
 * <code>Service</code> objects provide the client view of a Web service.
 * <p><code>Service</code> acts as a factory of the following:
 * <ul>
 * <li>Proxies for a target service endpoint.</li>
 * <li>Instances of {@link javax.xml.ws.Dispatch} for
 *     dynamic message-oriented invocation of a remote
 *     operation.
 * </li>
 * </ul>
 *
 * <p>The ports available on a service can be enumerated using the
 * <code>getPorts</code> method. Alternatively, you can pass a
 * service endpoint interface to the unary <code>getPort</code> method
 * and let the runtime select a compatible port.
 *
 * <p>Handler chains for all the objects created by a <code>Service</code>
 * can be set by means of a <code>HandlerResolver</code>.
 *
 * <p>An <code>Executor</code> may be set on the service in order
 * to gain better control over the threads used to dispatch asynchronous
 * callbacks. For instance, thread pooling with certain parameters
 * can be enabled by creating a <code>ThreadPoolExecutor</code> and
 * registering it with the service.
 *
 * <p>
 *  <code> Service </code>对象提供Web服务的客户端视图。 <p> <code>服务</code>充当以下产品的工厂：
 * <ul>
 *  <li>目标服务端点的代理</li> <li>面向动态面向消息的远程操作调用的{@link javax.xml.ws.Dispatch}实例。
 * </li>
 * </ul>
 * 
 *  <p>服务上可用的端口可以使用<code> getPorts </code>方法枚举。或者,您可以将服务端点接口传递到一元<code> getPort </code>方法,并让运行时选择兼容端口。
 * 
 *  <p>由<code> Service </code>创建的所有对象的处理程序链可以通过<code> HandlerResolver </code>设置。
 * 
 *  <p>可以在服务上设置<code> Executor </code>,以便更好地控制用于分派异步回调的线程。
 * 例如,可以通过创建<code> ThreadPoolExecutor </code>并将其注册到服务来启用具有某些参数的线程池。
 * 
 * 
 * @since JAX-WS 2.0
 *
 * @see javax.xml.ws.spi.Provider
 * @see javax.xml.ws.handler.HandlerResolver
 * @see java.util.concurrent.Executor
 **/
public class Service {

    private ServiceDelegate delegate;
    /**
     * The orientation of a dynamic client or service. <code>MESSAGE</code> provides
     * access to entire protocol message, <code>PAYLOAD</code> to protocol message
     * payload only.
     * <p>
     *  动态客户端或服务的方向。 <code> MESSAGE </code>提供对协议消息有效负载的整个协议消息,<code> PAYLOAD </code>的访问。
     * 
     * 
     **/
    public enum Mode { MESSAGE, PAYLOAD }

    protected Service(java.net.URL wsdlDocumentLocation, QName serviceName) {
        delegate = Provider.provider().createServiceDelegate(wsdlDocumentLocation,
                serviceName,
                this.getClass());
    }

    protected Service(java.net.URL wsdlDocumentLocation, QName serviceName, WebServiceFeature ... features) {
        delegate = Provider.provider().createServiceDelegate(wsdlDocumentLocation,
                serviceName,
                this.getClass(), features);
    }


    /**
     * The <code>getPort</code> method returns a proxy. A service client
     * uses this proxy to invoke operations on the target
     * service endpoint. The <code>serviceEndpointInterface</code>
     * specifies the service endpoint interface that is supported by
     * the created dynamic proxy instance.
     *
     * <p>
     * <code> getPort </code>方法返回一个代理。服务客户端使用此代理调用目标服务端点上的操作。
     *  <code> serviceEndpointInterface </code>指定所创建的动态代理实例支持的服务端点接口。
     * 
     * 
     * @param portName  Qualified name of the service endpoint in
     *                  the WSDL service description.
     * @param serviceEndpointInterface Service endpoint interface
     *                  supported by the dynamic proxy instance.
     * @return Object Proxy instance that
     *                supports the specified service endpoint
     *                interface.
     * @throws WebServiceException This exception is thrown in the
     *                  following cases:
     *                  <UL>
     *                  <LI>If there is an error in creation of
     *                      the proxy.
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                  <LI>If an illegal
     *                      <code>serviceEndpointInterface</code>
     *                      or <code>portName</code> is specified.
     *                  </UL>
     * @see java.lang.reflect.Proxy
     * @see java.lang.reflect.InvocationHandler
     **/
    public <T> T getPort(QName portName,
            Class<T> serviceEndpointInterface) {
        return delegate.getPort(portName, serviceEndpointInterface);
    }

    /**
     * The <code>getPort</code> method returns a proxy. A service client
     * uses this proxy to invoke operations on the target
     * service endpoint. The <code>serviceEndpointInterface</code>
     * specifies the service endpoint interface that is supported by
     * the created dynamic proxy instance.
     *
     * <p>
     *  <code> getPort </code>方法返回一个代理。服务客户端使用此代理调用目标服务端点上的操作。
     *  <code> serviceEndpointInterface </code>指定所创建的动态代理实例支持的服务端点接口。
     * 
     * 
     * @param portName  Qualified name of the service endpoint in
     *                  the WSDL service description.
     * @param serviceEndpointInterface Service endpoint interface
     *                  supported by the dynamic proxy instance.
     * @param features  A list of WebServiceFeatures to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     * @return Object Proxy instance that
     *                supports the specified service endpoint
     *                interface.
     * @throws WebServiceException This exception is thrown in the
     *                  following cases:
     *                  <UL>
     *                  <LI>If there is an error in creation of
     *                      the proxy.
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                  <LI>If an illegal
     *                      <code>serviceEndpointInterface</code>
     *                      or <code>portName</code> is specified.
     *                  <LI>If a feature is enabled that is not compatible
     *                      with this port or is unsupported.
     *                  </UL>
     * @see java.lang.reflect.Proxy
     * @see java.lang.reflect.InvocationHandler
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
     **/
    public <T> T getPort(QName portName,
            Class<T> serviceEndpointInterface, WebServiceFeature... features) {
        return delegate.getPort(portName, serviceEndpointInterface, features);
    }


    /**
     * The <code>getPort</code> method returns a proxy. The parameter
     * <code>serviceEndpointInterface</code> specifies the service
     * endpoint interface that is supported by the returned proxy.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the proxy accordingly.
     * The returned proxy should not be reconfigured by the client.
     *
     * <p>
     *  <code> getPort </code>方法返回一个代理。参数<code> serviceEndpointInterface </code>指定返回的代理支持的服务端点接口。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地配置代理。返回的代理不应该由客户端重新配置。
     * 
     * 
     * @param serviceEndpointInterface Service endpoint interface.
     * @return Object instance that supports the
     *                  specified service endpoint interface.
     * @throws WebServiceException
     *                  <UL>
     *                  <LI>If there is an error during creation
     *                      of the proxy.
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                  <LI>If an illegal
     *                      <code>serviceEndpointInterface</code>
     *                      is specified.
     *                  </UL>
     **/
    public <T> T getPort(Class<T> serviceEndpointInterface) {
        return delegate.getPort(serviceEndpointInterface);
    }


    /**
     * The <code>getPort</code> method returns a proxy. The parameter
     * <code>serviceEndpointInterface</code> specifies the service
     * endpoint interface that is supported by the returned proxy.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the proxy accordingly.
     * The returned proxy should not be reconfigured by the client.
     *
     * <p>
     *  <code> getPort </code>方法返回一个代理。参数<code> serviceEndpointInterface </code>指定返回的代理支持的服务端点接口。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地配置代理。返回的代理不应该由客户端重新配置。
     * 
     * 
     * @param serviceEndpointInterface Service endpoint interface.
     * @param features  A list of WebServiceFeatures to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     * @return Object instance that supports the
     *                  specified service endpoint interface.
     * @throws WebServiceException
     *                  <UL>
     *                  <LI>If there is an error during creation
     *                      of the proxy.
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                  <LI>If an illegal
     *                      <code>serviceEndpointInterface</code>
     *                      is specified.
     *                  <LI>If a feature is enabled that is not compatible
     *                      with this port or is unsupported.
     *                  </UL>
     *
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
     **/
    public <T> T getPort(Class<T> serviceEndpointInterface,
            WebServiceFeature... features) {
        return delegate.getPort(serviceEndpointInterface, features);
    }


    /**
     * The <code>getPort</code> method returns a proxy.
     * The parameter <code>endpointReference</code> specifies the
     * endpoint that will be invoked by the returned proxy.  If there
     * are any reference parameters in the
     * <code>endpointReference</code>, then those reference
     * parameters MUST appear as SOAP headers, indicating them to be
     * reference parameters, on all messages sent to the endpoint.
     * The <code>endpointReference's</code> address MUST be used
     * for invocations on the endpoint.
     * The parameter <code>serviceEndpointInterface</code> specifies
     * the service endpoint interface that is supported by the
     * returned proxy.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the proxy accordingly from
     * the WSDL associated with this <code>Service</code> instance or
     * from the metadata from the <code>endpointReference</code>.
     * If this <code>Service</code> instance has a WSDL and
     * the <code>endpointReference</code> metadata
     * also has a WSDL, then the WSDL from this instance MUST be used.
     * If this <code>Service</code> instance does not have a WSDL and
     * the <code>endpointReference</code> does have a WSDL, then the
     * WSDL from the <code>endpointReference</code> MAY be used.
     * The returned proxy should not be reconfigured by the client.
     * If this <code>Service</code> instance has a known proxy
     * port that matches the information contained in
     * the WSDL,
     * then that proxy is returned, otherwise a WebServiceException
     * is thrown.
     * <p>
     * Calling this method has the same behavior as the following
     * <pre>
     * <code>port = service.getPort(portName, serviceEndpointInterface);</code>
     * </pre>
     * where the <code>portName</code> is retrieved from the
     * metadata of the <code>endpointReference</code> or from the
     * <code>serviceEndpointInterface</code> and the WSDL
     * associated with this <code>Service</code> instance.
     *
     * <p>
     * <code> getPort </code>方法返回一个代理。参数<code> endpointReference </code>指定将由返回的代理调用的端点。
     * 如果在<code> endpointReference </code>中有任何引用参数,那么这些引用参数必须作为SOAP头部显示,表明它们是发送到端点的所有消息的引用参数。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 参数<code> serviceEndpointInterface </code>指定返回的代理支持的服务端点接口。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地从与此<code> Service </code>实例相关联的WSDL或从元数据从<code> endpointReferenc
     * e </code>。
     * 参数<code> serviceEndpointInterface </code>指定返回的代理支持的服务端点接口。
     * 如果这个<code> Service </code>实例有一个WSDL并且<code> endpointReference </code>元数据也有一个WSDL,那么必须使用这个实例的WSDL。
     * 如果这个<code> Service </code>实例没有WSDL并且<code> endpointReference </code>有一个WSDL,那么可以使用<code> endpointRefe
     * rence </code>中的WSDL。
     * 如果这个<code> Service </code>实例有一个WSDL并且<code> endpointReference </code>元数据也有一个WSDL,那么必须使用这个实例的WSDL。
     * 返回的代理不应该由客户端重新配置。如果此<code> Service </code>实例具有与WSDL中包含的信息匹配的已知代理端口,则返回该代理,否则抛出WebServiceException。
     * <p>
     * 调用此方法具有与以下相同的行为
     * <pre>
     *  <code> port = service.getPort(portName,serviceEndpointInterface); </code>
     * </pre>
     *  其中从<code> endpointReference </code>或从<code> serviceEndpointInterface </code>的元数据和与此<code> Service </code>
     * 关联的WSDL检索<code> portName </code>实例。
     * 
     * 
     * @param endpointReference  The <code>EndpointReference</code>
     * for the target service endpoint that will be invoked by the
     * returned proxy.
     * @param serviceEndpointInterface Service endpoint interface.
     * @param features  A list of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     * @return Object Proxy instance that supports the
     *                  specified service endpoint interface.
     * @throws WebServiceException
     *                  <UL>
     *                  <LI>If there is an error during creation
     *                      of the proxy.
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                  <LI>If the <code>endpointReference</code> metadata does
     *                      not match the <code>serviceName</code> of this
     *                      <code>Service</code> instance.
     *                  <LI>If a <code>portName</code> cannot be extracted
     *                      from the WSDL or <code>endpointReference</code> metadata.
     *                  <LI>If an invalid
     *                      <code>endpointReference</code>
     *                      is specified.
     *                  <LI>If an invalid
     *                      <code>serviceEndpointInterface</code>
     *                      is specified.
     *                  <LI>If a feature is enabled that is not compatible
     *                      with this port or is unsupported.
     *                  </UL>
     *
     * @since JAX-WS 2.1
     **/
    public <T> T getPort(EndpointReference endpointReference,
           Class<T> serviceEndpointInterface, WebServiceFeature... features) {
        return delegate.getPort(endpointReference, serviceEndpointInterface, features);
    }

    /**
     * Creates a new port for the service. Ports created in this way contain
     * no WSDL port type information and can only be used for creating
     * <code>Dispatch</code>instances.
     *
     * <p>
     *  为服务创建新端口。以这种方式创建的端口不包含WSDL端口类型信息,并且只能用于创建<code> Dispatch </code>实例。
     * 
     * 
     * @param portName  Qualified name for the target service endpoint.
     * @param bindingId A String identifier of a binding.
     * @param endpointAddress Address of the target service endpoint as a URI.
     * @throws WebServiceException If any error in the creation of
     * the port.
     *
     * @see javax.xml.ws.soap.SOAPBinding#SOAP11HTTP_BINDING
     * @see javax.xml.ws.soap.SOAPBinding#SOAP12HTTP_BINDING
     * @see javax.xml.ws.http.HTTPBinding#HTTP_BINDING
     **/
    public void addPort(QName portName, String bindingId, String endpointAddress) {
        delegate.addPort(portName, bindingId, endpointAddress);
    }


    /**
     * Creates a <code>Dispatch</code> instance for use with objects of
     * the client's choosing.
     *
     * <p>
     *  创建<code> Dispatch </code>实例以供客户选择的对象使用。
     * 
     * 
     * @param portName  Qualified name for the target service endpoint
     * @param type The class of object used for messages or message
     * payloads. Implementations are required to support
     * <code>javax.xml.transform.Source</code>, <code>javax.xml.soap.SOAPMessage</code>
     * and <code>javax.activation.DataSource</code>, depending on
     * the binding in use.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body. Mode MUST be MESSAGE
     * when type is SOAPMessage.
     *
     * @return Dispatch instance.
     * @throws WebServiceException If any error in the creation of
     *                  the <code>Dispatch</code> object.
     *
     * @see javax.xml.transform.Source
     * @see javax.xml.soap.SOAPMessage
     **/
    public <T> Dispatch<T> createDispatch(QName portName, Class<T> type, Mode mode) {
        return delegate.createDispatch(portName, type, mode);
    }


    /**
     * Creates a <code>Dispatch</code> instance for use with objects of
     * the client's choosing.
     *
     * <p>
     *  创建<code> Dispatch </code>实例以供客户选择的对象使用。
     * 
     * 
     * @param portName  Qualified name for the target service endpoint
     * @param type The class of object used for messages or message
     * payloads. Implementations are required to support
     * <code>javax.xml.transform.Source</code> and <code>javax.xml.soap.SOAPMessage</code>.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body. Mode MUST be <code>MESSAGE</code>
     * when type is <code>SOAPMessage</code>.
     * @param features  A list of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     *
     * @return Dispatch instance.
     * @throws WebServiceException If any error in the creation of
     *                  the <code>Dispatch</code> object or if a
     *                  feature is enabled that is not compatible with
     *                  this port or is unsupported.
     *
     * @see javax.xml.transform.Source
     * @see javax.xml.soap.SOAPMessage
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
     **/
    public <T> Dispatch<T> createDispatch(QName portName, Class<T> type,
            Service.Mode mode, WebServiceFeature... features) {
        return delegate.createDispatch(portName, type, mode, features);
    }


    /**
     * Creates a <code>Dispatch</code> instance for use with objects of
     * the client's choosing. If there
     * are any reference parameters in the
     * <code>endpointReference</code>, then those reference
     * parameters MUST appear as SOAP headers, indicating them to be
     * reference parameters, on all messages sent to the endpoint.
     * The <code>endpointReference's</code> address MUST be used
     * for invocations on the endpoint.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the dispatch accordingly from
     * the WSDL associated with this <code>Service</code> instance or
     * from the metadata from the <code>endpointReference</code>.
     * If this <code>Service</code> instance has a WSDL and
     * the <code>endpointReference</code>
     * also has a WSDL in its metadata, then the WSDL from this instance MUST be used.
     * If this <code>Service</code> instance does not have a WSDL and
     * the <code>endpointReference</code> does have a WSDL, then the
     * WSDL from the <code>endpointReference</code> MAY be used.
     * An implementation MUST be able to retrieve the <code>portName</code> from the
     * <code>endpointReference</code> metadata.
     * <p>
     * This method behaves the same as calling
     * <pre>
     * <code>dispatch = service.createDispatch(portName, type, mode, features);</code>
     * </pre>
     * where the <code>portName</code> is retrieved from the
     * WSDL or <code>EndpointReference</code> metadata.
     *
     * <p>
     * 创建<code> Dispatch </code>实例以供客户选择的对象使用。
     * 如果在<code> endpointReference </code>中有任何引用参数,那么这些引用参数必须作为SOAP头部显示,表明它们是发送到端点的所有消息的引用参数。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地从与此<code> Service </code>实例相关联的WSDL或从元数据从<code> endpointReferenc
     * e </code>。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 如果这个<code> Service </code>实例有一个WSDL并且<code> endpointReference </code>在它的元数据中也有一个WSDL,那么必须使用这个实例的WSDL。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 如果这个<code> Service </code>实例没有WSDL并且<code> endpointReference </code>有一个WSDL,那么可以使用<code> endpointRefe
     * rence </code>中的WSDL。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 实现必须能够从<code> endpointReference </code>元数据中检索<code> portName </code>。
     * <p>
     *  此方法的行为与调用相同
     * <pre>
     *  <code> dispatch = service.createDispatch(portName,type,mode,features); </code>
     * </pre>
     *  其中从WSDL或<code> EndpointReference </code>元数据检索<code> portName </code>。
     * 
     * 
     * @param endpointReference  The <code>EndpointReference</code>
     * for the target service endpoint that will be invoked by the
     * returned <code>Dispatch</code> object.
     * @param type The class of object used to messages or message
     * payloads. Implementations are required to support
     * <code>javax.xml.transform.Source</code> and <code>javax.xml.soap.SOAPMessage</code>.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body. Mode MUST be <code>MESSAGE</code>
     * when type is <code>SOAPMessage</code>.
     * @param features  An array of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     *
     * @return Dispatch instance
     * @throws WebServiceException
     *                  <UL>
     *                    <LI>If there is any missing WSDL metadata
     *                      as required by this method.
     *                    <li>If the <code>endpointReference</code> metadata does
     *                      not match the <code>serviceName</code> or <code>portName</code>
     *                      of a WSDL associated
     *                      with this <code>Service</code> instance.
     *                    <li>If the <code>portName</code> cannot be determined
     *                    from the <code>EndpointReference</code> metadata.
     *                    <li>If any error in the creation of
     *                     the <code>Dispatch</code> object.
     *                    <li>If a feature is enabled that is not
     *                    compatible with this port or is unsupported.
     *                  </UL>
     *
     * @see javax.xml.transform.Source
     * @see javax.xml.soap.SOAPMessage
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
     **/
    public <T> Dispatch<T> createDispatch(EndpointReference endpointReference,
            Class<T> type, Service.Mode mode,
            WebServiceFeature... features) {
        return delegate.createDispatch(endpointReference, type, mode, features);
    }

    /**
     * Creates a <code>Dispatch</code> instance for use with JAXB
     * generated objects.
     *
     * <p>
     *  创建用于JAXB生成对象的<code> Dispatch </code>实例。
     * 
     * 
     * @param portName  Qualified name for the target service endpoint
     * @param context The JAXB context used to marshall and unmarshall
     * messages or message payloads.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body.
     *
     * @return Dispatch instance.
     * @throws WebServiceException If any error in the creation of
     *                  the <code>Dispatch</code> object.
     *
     * @see javax.xml.bind.JAXBContext
     **/
    public Dispatch<Object> createDispatch(QName portName, JAXBContext context,
            Mode mode) {
        return delegate.createDispatch(portName, context,  mode);
    }


    /**
     * Creates a <code>Dispatch</code> instance for use with JAXB
     * generated objects.
     *
     * <p>
     * 创建用于JAXB生成对象的<code> Dispatch </code>实例。
     * 
     * 
     * @param portName  Qualified name for the target service endpoint
     * @param context The JAXB context used to marshall and unmarshall
     * messages or message payloads.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body.
     * @param features  A list of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     *
     * @return Dispatch instance.
     * @throws WebServiceException If any error in the creation of
     *                  the <code>Dispatch</code> object or if a
     *                  feature is enabled that is not compatible with
     *                  this port or is unsupported.
     *
     * @see javax.xml.bind.JAXBContext
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
     **/
    public Dispatch<Object> createDispatch(QName portName,
            JAXBContext context, Service.Mode mode, WebServiceFeature... features) {
        return delegate.createDispatch(portName, context, mode, features);
    }


    /**
     * Creates a <code>Dispatch</code> instance for use with JAXB
     * generated objects. If there
     * are any reference parameters in the
     * <code>endpointReference</code>, then those reference
     * parameters MUST appear as SOAP headers, indicating them to be
     * reference parameters, on all messages sent to the endpoint.
     * The <code>endpointReference's</code> address MUST be used
     * for invocations on the endpoint.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the dispatch accordingly from
     * the WSDL associated with this <code>Service</code> instance or
     * from the metadata from the <code>endpointReference</code>.
     * If this <code>Service</code> instance has a WSDL and
     * the <code>endpointReference</code>
     * also has a WSDL in its metadata, then the WSDL from this instance
     * MUST be used.
     * If this <code>Service</code> instance does not have a WSDL and
     * the <code>endpointReference</code> does have a WSDL, then the
     * WSDL from the <code>endpointReference</code> MAY be used.
     * An implementation MUST be able to retrieve the <code>portName</code> from the
     * <code>endpointReference</code> metadata.
     * <p>
     * This method behavies the same as calling
     * <pre>
     * <code>dispatch = service.createDispatch(portName, context, mode, features);</code>
     * </pre>
     * where the <code>portName</code> is retrieved from the
     * WSDL or <code>endpointReference</code> metadata.
     *
     * <p>
     *  创建用于JAXB生成对象的<code> Dispatch </code>实例。
     * 如果在<code> endpointReference </code>中有任何引用参数,那么这些引用参数必须作为SOAP头部显示,表明它们是发送到端点的所有消息的引用参数。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地从与此<code> Service </code>实例相关联的WSDL或从元数据从<code> endpointReferenc
     * e </code>。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 如果这个<code> Service </code>实例有一个WSDL并且<code> endpointReference </code>在它的元数据中也有一个WSDL,那么必须使用这个实例的WSDL。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 如果这个<code> Service </code>实例没有WSDL并且<code> endpointReference </code>有一个WSDL,那么可以使用<code> endpointRefe
     * rence </code>中的WSDL。
     *  <code> endpointReference的</code>地址必须用于端点上的调用。
     * 实现必须能够从<code> endpointReference </code>元数据中检索<code> portName </code>。
     * <p>
     *  此方法的行为与调用相同
     * <pre>
     *  <code> dispatch = service.createDispatch(portName,context,mode,features); </code>
     * </pre>
     *  其中从WSDL或<code> endpointReference </code>元数据检索<code> portName </code>。
     * 
     * 
     * @param endpointReference  The <code>EndpointReference</code>
     * for the target service endpoint that will be invoked by the
     * returned <code>Dispatch</code> object.
     * @param context The JAXB context used to marshall and unmarshall
     * messages or message payloads.
     * @param mode Controls whether the created dispatch instance is message
     * or payload oriented, i.e. whether the client will work with complete
     * protocol messages or message payloads. E.g. when using the SOAP
     * protocol, this parameter controls whether the client will work with
     * SOAP messages or the contents of a SOAP body.
     * @param features  An array of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     *
     * @return Dispatch instance
     * @throws WebServiceException
     *                  <UL>
     *                    <li>If there is any missing WSDL metadata
     *                      as required by this method.
     *                    <li>If the <code>endpointReference</code> metadata does
     *                    not match the <code>serviceName</code> or <code>portName</code>
     *                    of a WSDL associated
     *                    with this <code>Service</code> instance.
     *                    <li>If the <code>portName</code> cannot be determined
     *                    from the <code>EndpointReference</code> metadata.
     *                    <li>If any error in the creation of
     *                    the <code>Dispatch</code> object.
     *                    <li>if a feature is enabled that is not
     *                    compatible with this port or is unsupported.
     *                  </UL>
     *
     * @see javax.xml.bind.JAXBContext
     * @see WebServiceFeature
     *
     * @since JAX-WS 2.1
    **/
    public Dispatch<Object> createDispatch(EndpointReference endpointReference,
            JAXBContext context, Service.Mode mode,
            WebServiceFeature... features) {
        return delegate.createDispatch(endpointReference, context, mode, features);
    }

    /**
     * Gets the name of this service.
     * <p>
     * 获取此服务的名称。
     * 
     * 
     * @return Qualified name of this service
     **/
    public QName getServiceName() {
        return delegate.getServiceName();
    }

    /**
     * Returns an <code>Iterator</code> for the list of
     * <code>QName</code>s of service endpoints grouped by this
     * service
     *
     * <p>
     *  对于按此服务分组的服务端点的<code> QName </code>列表,返回<code>迭代器</code>
     * 
     * 
     * @return Returns <code>java.util.Iterator</code> with elements
     *         of type <code>javax.xml.namespace.QName</code>.
     * @throws WebServiceException If this Service class does not
     *         have access to the required WSDL metadata.
     **/
    public Iterator<javax.xml.namespace.QName> getPorts() {
        return delegate.getPorts();
    }

    /**
     * Gets the location of the WSDL document for this Service.
     *
     * <p>
     *  获取此服务的WSDL文档的位置。
     * 
     * 
     * @return URL for the location of the WSDL document for
     *         this service.
     **/
    public java.net.URL getWSDLDocumentLocation() {
        return delegate.getWSDLDocumentLocation();
    }

    /**
     * Returns the configured handler resolver.
     *
     * <p>
     *  返回配置的处理程序解析器。
     * 
     * 
     * @return HandlerResolver The <code>HandlerResolver</code> being
     *         used by this <code>Service</code> instance, or <code>null</code>
     *         if there isn't one.
     **/
    public HandlerResolver getHandlerResolver() {
        return delegate.getHandlerResolver();
    }

    /**
     * Sets the <code>HandlerResolver</code> for this <code>Service</code>
     * instance.
     * <p>
     * The handler resolver, if present, will be called once for each
     * proxy or dispatch instance that is created, and the handler chain
     * returned by the resolver will be set on the instance.
     *
     * <p>
     *  为<code> Service </code>实例设置<code> HandlerResolver </code>。
     * <p>
     *  处理程序解析器(如果存在)将为创建的每个代理或分派实例调用一次,并且解析器返回的处理程序链将在实例上设置。
     * 
     * 
     * @param handlerResolver The <code>HandlerResolver</code> to use
     *        for all subsequently created proxy/dispatch objects.
     *
     * @see javax.xml.ws.handler.HandlerResolver
     **/
    public void setHandlerResolver(HandlerResolver handlerResolver) {
        delegate.setHandlerResolver(handlerResolver);
    }

    /**
     * Returns the executor for this <code>Service</code>instance.
     *
     * The executor is used for all asynchronous invocations that
     * require callbacks.
     *
     * <p>
     *  返回此<> Service </code>实例的执行程序。
     * 
     *  执行器用于所有需要回调的异步调用。
     * 
     * 
     * @return The <code>java.util.concurrent.Executor</code> to be
     *         used to invoke a callback.
     *
     * @see java.util.concurrent.Executor
     **/
    public java.util.concurrent.Executor getExecutor() {
        return delegate.getExecutor();
    }

    /**
     * Sets the executor for this <code>Service</code> instance.
     *
     * The executor is used for all asynchronous invocations that
     * require callbacks.
     *
     * <p>
     *  设置此<code> Service </code>实例的执行程序。
     * 
     *  执行器用于所有需要回调的异步调用。
     * 
     * 
     * @param executor The <code>java.util.concurrent.Executor</code>
     *        to be used to invoke a callback.
     *
     * @throws SecurityException If the instance does not support
     *         setting an executor for security reasons (e.g. the
     *         necessary permissions are missing).
     *
     * @see java.util.concurrent.Executor
     **/
    public void setExecutor(java.util.concurrent.Executor executor) {
        delegate.setExecutor(executor);
    }

    /**
     * Creates a <code>Service</code> instance.
     *
     * The specified WSDL document location and service qualified name MUST
     * uniquely identify a <code>wsdl:service</code> element.
     *
     * <p>
     *  创建<code>服务</code>实例。
     * 
     *  指定的WSDL文档位置和服务限定名必须唯一标识一个<code> wsdl：service </code>元素。
     * 
     * 
     * @param wsdlDocumentLocation <code>URL</code> for the WSDL document location
     *                             for the service
     * @param serviceName <code>QName</code> for the service
     * @throws WebServiceException If any error in creation of the
     *                    specified service.
     **/
    public static Service create(
            java.net.URL wsdlDocumentLocation,
            QName serviceName) {
        return new Service(wsdlDocumentLocation, serviceName);
    }

    /**
     * Creates a <code>Service</code> instance. The created instance is
     * configured with the web service features.
     *
     * The specified WSDL document location and service qualified name MUST
     * uniquely identify a <code>wsdl:service</code> element.
     *
     * <p>
     *  创建<code>服务</code>实例。创建的实例使用Web服务功能配置。
     * 
     *  指定的WSDL文档位置和服务限定名必须唯一标识一个<code> wsdl：service </code>元素。
     * 
     * 
     * @param wsdlDocumentLocation <code>URL</code> for the WSDL document location
     *                             for the service
     * @param serviceName <code>QName</code> for the service
     * @param features Web Service features that must be configured on
     *        the service. If the provider doesn't understand a feature,
     *        it must throw a WebServiceException.
     * @throws WebServiceException If any error in creation of the
     *                    specified service.
     * @since JAX-WS 2.2
     **/
    public static Service create(
            java.net.URL wsdlDocumentLocation,
            QName serviceName, WebServiceFeature ... features) {
        return new Service(wsdlDocumentLocation, serviceName, features);
    }

    /**
     * Creates a <code>Service</code> instance.
     *
     * <p>
     *  创建<code>服务</code>实例。
     * 
     * 
     * @param serviceName <code>QName</code> for the service
     * @throws WebServiceException If any error in creation of the
     *                    specified service
     */
    public static Service create(QName serviceName) {
        return new Service(null, serviceName);
    }

    /**
     * Creates a <code>Service</code> instance. The created instance is
     * configured with the web service features.
     *
     * <p>
     *  创建<code>服务</code>实例。创建的实例使用Web服务功能配置。
     * 
     * @param serviceName <code>QName</code> for the service
     * @param features Web Service features that must be configured on
     *        the service. If the provider doesn't understand a feature,
     *        it must throw a WebServiceException.
     * @throws WebServiceException If any error in creation of the
     *                    specified service
     *
     * @since JAX-WS 2.2
     */
    public static Service create(QName serviceName, WebServiceFeature ... features) {
        return new Service(null, serviceName, features);
    }
}
