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

import javax.xml.ws.soap.AddressingFeature;

/**
 * This feature clarifies the use of the <code>wsdl:binding</code>
 * in a JAX-WS runtime.
 *
 * This feature can be used during the creation of SEI proxy, and
 * {@link Dispatch} instances on the client side and {@link Endpoint}
 * instances on the server side. This feature cannot be used for {@link Service}
 * instance creation on the client side.
 * <p>
 * This feature is only useful with web services that have an
 * associated WSDL. Enabling this feature requires that a JAX-WS
 * implementation inspect the <code>wsdl:binding</code> for an
 * endpoint at runtime to make sure that all <code>wsdl:extensions</code>
 * that have the <code>required</code> attribute set to <code>true</code>
 * are understood and are being used.
 * <p>
 * The following describes the affects of this feature with respect
 * to be enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, a JAX-WS runtime MUST assure that all
 *  required <code>wsdl:binding</code> extensions(including policies) are
 *  either understood and used by the runtime, or explicitly disabled by the
 *  web service application. A web service can disable a particular
 *  extension if there is a corresponding {@link WebServiceFeature} or annotation.
 *  Similarly, a web service client can disable
 *  particular extension using the corresponding <code>WebServiceFeature</code> while
 *  creating a proxy or Dispatch instance.
 *  The runtime MUST also make sure that binding of
 *  SEI parameters/return values respect the <code>wsdl:binding</code>.
 *  With this feature enabled, if a required (<code>wsdl:required="true"</code>)
 *  <code>wsdl:binding</code> extension is in the WSDL and it is not
 *  supported by a JAX-WS runtime and it has not
 *  been explicitly turned off by the web service developer, then
 *  that JAX-WS runtime MUST behave appropriately based on whether it is
 *  on the client or server:
 *  <UL>
 *    <li>Client: runtime MUST throw a
 *  {@link WebServiceException} no sooner than when one of the methods
 *  above is invoked but no later than the first invocation of an endpoint
 *  operation.
 *    <li>Server: throw a {@link WebServiceException} and the endpoint MUST fail to deploy
 *  </ul>
 *
 *  <li> DISABLED: In this Mode, an implementation may choose whether
 *  to inspect the <code>wsdl:binding</code> or not and to what degree
 *  the <code>wsdl:binding</code> will be inspected.  For example,
 *  one implementation may choose to behave as if this feature is enabled,
 *  another implementation may only choose to verify the SEI's
 *  parameter/return type bindings.
 * </ul>
 *
 * <p>
 *  此功能阐明了在JAX-WS运行时中使用<code> wsdl：binding </code>。
 * 
 *  此功能可在创建SEI代理时使用,在客户端使用{@link Dispatch}实例,并在服务器端使用{@link Endpoint}实例。
 * 此功能不能用于在客户端上创建{@link Service}实例。
 * <p>
 *  此功能仅对具有关联的WSDL的Web服务有用。
 * 启用此功能需要JAX-WS实现在运行时检查端点的<code> wsdl：binding </code>,以确保所有<code> wsdl：extensions </code> / code>属性设置为<code>
 *  true </code>被理解和正在使用。
 *  此功能仅对具有关联的WSDL的Web服务有用。
 * <p>
 *  下面介绍此功能对启用或禁用的影响：
 * <ul>
 * <li> ENABLED：在此模式下,JAX-WS运行时务必确保所有必需的<code> wsdl：binding </code>扩展(包括策略)都被运行时理解和使用,应用。
 * 如果有相应的{@link WebServiceFeature}或注释,Web服务可以禁用特定扩展。
 * 类似地,Web服务客户端可以在创建代理或Dispatch实例时使用相应的<code> WebServiceFeature </code>禁用特定的扩展。
 * 运行时还必须确保SEI参数/返回值的绑定遵守<code> wsdl：binding </code>。
 * 启用此功能后,如果必需的(<code> wsdl：required ="true"</code>)<code> wsdl：binding </code>扩展位于WSDL中,并且JAX-WS运行时并且它没
 * 有被Web服务开发人员显式地关闭,那么JAX-WS运行时必须基于它是在客户端还是服务器上适当地表现：。
 * 运行时还必须确保SEI参数/返回值的绑定遵守<code> wsdl：binding </code>。
 * <UL>
 * 
 * @see AddressingFeature
 *
 * @since JAX-WS 2.1
 */
public final class RespectBindingFeature extends WebServiceFeature {
    /**
     *
     * Constant value identifying the RespectBindingFeature
     * <p>
     *  <li>客户端：运行时必须在调用上述方法之一时不要发出{@link WebServiceException},但不得晚于第一次调用端点操作。
     *  <li>服务器：抛出{@link WebServiceException},并且端点必须部署失败。
     * </ul>
     * 
     * <li> DISABLED：在这种模式下,实现可以选择是否检查<code> wsdl：binding </code>,以及检查<code> wsdl：binding </code>的程度。
     * 例如,一个实现可以选择表现为如果该特征被启用,另一个实现可以仅选择验证SEI的参数/返回类型绑定。
     * </ul>
     * 
     */
    public static final String ID = "javax.xml.ws.RespectBindingFeature";


    /**
     * Creates an <code>RespectBindingFeature</code>.
     * The instance created will be enabled.
     * <p>
     *  用于标识RespectBindingFeature的常量值
     * 
     */
    public RespectBindingFeature() {
        this.enabled = true;
    }

    /**
     * Creates an RespectBindingFeature
     *
     * <p>
     *  创建<code> RespectBindingFeature </code>。将创建已创建的实例。
     * 
     * 
     * @param enabled specifies whether this feature should
     * be enabled or not.
     */
    public RespectBindingFeature(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  创建一个RespectBindingFeature
     * 
     */
    public String getID() {
        return ID;
    }
}
