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

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import java.io.StringWriter;

/**
 * This class represents an WS-Addressing EndpointReference
 * which is a remote reference to a web service endpoint.
 * See <a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/">
 * Web Services Addressing 1.0 - Core</a>
 * for more information on WS-Addressing EndpointReferences.
 * <p>
 * This class is immutable as the typical web service developer
 * need not be concerned with its contents.  The web service
 * developer should use this class strictly as a mechanism to
 * reference a remote web service endpoint. See the {@link Service} APIs
 * that clients can use to that utilize an <code>EndpointReference</code>.
 * See the {@link javax.xml.ws.Endpoint}, and
 * {@link javax.xml.ws.BindingProvider} APIs on how
 * <code>EndpointReferences</code> can be created for published
 * endpoints.
 * <p>
 * Concrete implementations of this class will represent
 * an <code>EndpointReference</code> for a particular version of Addressing.
 * For example the {@link W3CEndpointReference} is for use
 * with W3C Web Services Addressing 1.0 - Core Recommendation.
 * If JAX-WS implementors need to support different versions
 * of addressing, they should write their own
 * <code>EndpointReference</code> subclass for that version.
 * This will allow a JAX-WS implementation to create
 * a vendor specific <code>EndpointReferences</code> that the
 * vendor can use to flag a different version of
 * addressing.
 * <p>
 * Web service developers that wish to pass or return
 * <code>EndpointReference</code> in Java methods in an
 * SEI should use
 * concrete instances of an <code>EndpointReference</code> such
 * as the <code>W3CEndpointReference</code>.  This way the
 * schema mapped from the SEI will be more descriptive of the
 * type of endpoint reference being passed.
 * <p>
 * JAX-WS implementors are expected to extract the XML infoset
 * from an <CODE>EndpointReferece</CODE> using the
 * <code>{@link EndpointReference#writeTo}</code>
 * method.
 * <p>
 * JAXB will bind this class to xs:anyType. If a better binding
 * is desired, web services developers should use a concrete
 * subclass such as {@link W3CEndpointReference}.
 *
 * <p>
 *  此类表示WS-Addressing EndpointReference,它是对Web服务端点的远程引用。
 * 有关WS-Addressing EndpointReferences的更多信息,请参见<a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/">
 *  Web服务寻址1.0  - 内核</a> 。
 *  此类表示WS-Addressing EndpointReference,它是对Web服务端点的远程引用。
 * <p>
 *  这个类是不可变的,因为典型的web服务开发者不需要关心它的内容。 Web服务开发人员应严格使用此类作为引用远程Web服务端点的机制。
 * 查看客户端可以使用的{@link Service} API,使用<code> EndpointReference </code>。
 * 请参阅{@link javax.xml.ws.Endpoint}和{@link javax.xml.ws.BindingProvider} API,了解如何为发布的端点创建<code> Endpoint
 * References </code>。
 * 查看客户端可以使用的{@link Service} API,使用<code> EndpointReference </code>。
 * <p>
 *  这个类的具体实现将表示一个特定版本的Addressing的<code> EndpointReference </code>。
 * 例如,{@link W3CEndpointReference}用于W3C Web Services寻址1.0  - 核心推荐。
 * 如果JAX-WS实现者需要支持不同版本的寻址,他们应为那个版本自己编写<code> EndpointReference </code>子类。
 * 这将允许JAX-WS实现创建供应商特定的<code> EndpointReferences </code>,供应商可以使用它来标记不同版本的寻址。
 * <p>
 * 希望在SEI中的Java方法中传递或返回<code> EndpointReference </code>的Web服务开发人员应使用<code> EndpointReference </code>(例如<code>
 *  W3CEndpointReference </code>)的具体实例。
 * 这样,从SEI映射的模式将更加描述正在传递的端点引用的类型。
 * <p>
 *  JAX-WS实现者期望使用<code> {@ link EndpointReference#writeTo} </code>方法从<CODE> EndpointReferece </CODE>提取XM
 * L信息集。
 * <p>
 *  JAXB将此类绑定到xs：anyType。如果需要更好的绑定,Web服务开发人员应该使用一个具体的子类,例如{@link W3CEndpointReference}。
 * 
 * 
 * @see W3CEndpointReference
 * @see Service
 * @since JAX-WS 2.1
 */
@XmlTransient // to treat this class like Object as far as databinding is concerned (proposed JAXB 2.1 feature)
public abstract class EndpointReference {
    //
    //Default constructor to be only called by derived types.
    //
    protected EndpointReference(){}

    /**
     * Factory method to read an EndpointReference from the infoset contained in
     * <code>eprInfoset</code>. This method delegates to the vendor specific
     * implementation of the {@link javax.xml.ws.spi.Provider#readEndpointReference} method.
     *
     * <p>
     *  工厂方法从包含在<code> eprInfoset </code>中的信息集读取EndpointReference。
     * 此方法委托供应商特定的{@link javax.xml.ws.spi.Provider#readEndpointReference}方法实现。
     * 
     * 
     * @param eprInfoset The <code>EndpointReference</code> infoset to be unmarshalled
     *
     * @return the EndpointReference unmarshalled from <code>eprInfoset</code>
     *    never <code>null</code>
     * @throws WebServiceException
     *    if an error occurs while creating the
     *    <code>EndpointReference</code> from the <CODE>eprInfoset</CODE>
     * @throws java.lang.IllegalArgumentException
     *     if the <code>null</code> <code>eprInfoset</code> value is given.
     */
    public static EndpointReference readFrom(Source eprInfoset) {
        return Provider.provider().readEndpointReference(eprInfoset);
    }

    /**
     * write this <code>EndpointReference</code> to the specified infoset format
     *
     * <p>
     *  将此<code> EndpointReference </code>写入指定的信息集格式
     * 
     * 
     * @param result for writing infoset
     * @throws WebServiceException
     *   if there is an error writing the
     *   <code>EndpointReference</code> to the specified <code>result</code>.
     *
     * @throws java.lang.IllegalArgumentException
     *      If the <code>null</code> <code>result</code> value is given.
     */
    public abstract void writeTo(Result result);


    /**
     * The <code>getPort</code> method returns a proxy. If there
     * are any reference parameters in the
     * <code>EndpointReference</code> instance, then those reference
     * parameters MUST appear as SOAP headers, indicating them to be
     * reference parameters, on all messages sent to the endpoint.
     * The parameter  <code>serviceEndpointInterface</code> specifies
     * the service endpoint interface that is supported by the
     * returned proxy.
     * The <code>EndpointReference</code> instance specifies the
     * endpoint that will be invoked by the returned proxy.
     * In the implementation of this method, the JAX-WS
     * runtime system takes the responsibility of selecting a protocol
     * binding (and a port) and configuring the proxy accordingly from
     * the WSDL Metadata from this <code>EndpointReference</code> or from
     * annotations on the <code>serviceEndpointInterface</code>.  For this method
     * to successfully return a proxy, WSDL metadata MUST be available and the
     * <code>EndpointReference</code> instance MUST contain an implementation understood
     * <code>serviceName</code> metadata.
     * <p>
     * Because this port is not created from a <code>Service</code> object, handlers
     * will not automatically be configured, and the <code>HandlerResolver</code>
     * and <code>Executor</code> cannot be get or set for this port. The
     * <code>BindingProvider().getBinding().setHandlerChain()</code>
     * method can be used to manually configure handlers for this port.
     *
     *
     * <p>
     * <code> getPort </code>方法返回一个代理。
     * 如果在<code> EndpointReference </code>实例中有任何引用参数,那么这些引用参数必须显示为SOAP头,表明它们是发送到端点的所有消息的引用参数。
     * 参数<code> serviceEndpointInterface </code>指定返回的代理支持的服务端点接口。
     *  <code> EndpointReference </code>实例指定将由返回的代理调用的端点。
     * 在该方法的实现中,JAX-WS运行时系统负责选择协议绑定(和端口)并相应地从来自此<code> EndpointReference </code>的WSDL元数据或从<code> serviceEndp
     * ointInterface </code>。
     *  <code> EndpointReference </code>实例指定将由返回的代理调用的端点。
     * 为了成功返回代理的方法,WSDL元数据必须是可用的,并且<code> EndpointReference </code>实例必须包含一个理解为<code> serviceName </code>元数据的
     * 实现。
     * 
     * @param serviceEndpointInterface Service endpoint interface
     * @param features  An array of <code>WebServiceFeatures</code> to configure on the
     *                proxy.  Supported features not in the <code>features
     *                </code> parameter will have their default values.
     * @return Object Proxy instance that supports the
     *                  specified service endpoint interface
     * @throws WebServiceException
     *                  <UL>
     *                  <LI>If there is an error during creation
     *                      of the proxy
     *                  <LI>If there is any missing WSDL metadata
     *                      as required by this method
     *                  <LI>If this
     *                      <code>endpointReference</code>
     *                      is invalid
     *                  <LI>If an illegal
     *                      <code>serviceEndpointInterface</code>
     *                      is specified
     *                  <LI>If a feature is enabled that is not compatible with
     *                      this port or is unsupported.
     *                   </UL>
     *
     * @see java.lang.reflect.Proxy
     * @see WebServiceFeature
     **/
    public <T> T getPort(Class<T> serviceEndpointInterface,
                         WebServiceFeature... features) {
        return Provider.provider().getPort(this, serviceEndpointInterface,
                                           features);
    }

    /**
     * Displays EPR infoset for debugging convenience.
     * <p>
     *  <code> EndpointReference </code>实例指定将由返回的代理调用的端点。
     * <p>
     *  因为此端口不是从<code> Service </code>对象创建的,处理程序不会自动配置,并且<code> HandlerResolver </code>和<code> Executor </code>
     * 这个端口。
     *  <code> BindingProvider()。getBinding()。setHandlerChain()</code>方法可用于手动配置此端口的处理程序。
     */
    public String toString() {
        StringWriter w = new StringWriter();
        writeTo(new StreamResult(w));
        return w.toString();
    }
}
