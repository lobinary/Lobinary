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

package javax.xml.ws.soap;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.soap.AddressingFeature.Responses;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;

/**
 * This annotation represents the use of WS-Addressing with either
 * the SOAP 1.1/HTTP or SOAP 1.2/HTTP binding. Using this annotation
 * with any other binding is undefined.
 * <p>
 * This annotation MUST only be used in conjunction with the
 * {@link javax.jws.WebService}, {@link WebServiceProvider},
 *  and {@link WebServiceRef} annotations.
 * When used with a <code>javax.jws.WebService</code> annotation, this
 * annotation MUST only be used on the service endpoint implementation
 * class.
 * When used with a <code>WebServiceRef</code> annotation, this annotation
 * MUST only be used when a proxy instance is created. The injected SEI
 * proxy, and endpoint MUST honor the values of the <code>Addressing</code>
 * annotation.
 * <p>
 * This annotation's behaviour is defined by the corresponding feature
 * {@link AddressingFeature}.
 *
 * <p>
 *  此注释表示使用带有SOAP 1.1 / HTTP或SOAP 1.2 / HTTP绑定的WS-Addressing。将此注释与任何其他绑定一起使用是未定义的。
 * <p>
 *  此注释必须只能与{@link javax.jws.WebService},{@link WebServiceProvider}和{@link WebServiceRef}注释结合使用。
 * 当与<code> javax.jws.WebService </code>注释一起使用时,此注释必须仅在服务端点实现类上使用。
 * 当与<code> WebServiceRef </code>注释一起使用时,此注释必须仅在创建代理实例时使用。注入的SEI代理和端点务必遵守<code> Addressing </code>注释的值。
 * <p>
 *  此注释的行为由对应的特性{@link AddressingFeature}定义。
 * 
 * 
 * @since JAX-WS 2.1
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id=AddressingFeature.ID,bean=AddressingFeature.class)
public @interface Addressing {
    /**
     * Specifies if this feature is enabled or disabled. If enabled, it means
     * the endpoint supports WS-Addressing but does not require its use.
     * Corresponding
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyaddressing">
     * 3.1.1 Addressing Assertion</a> must be generated in the generated WSDL.
     * <p>
     *  指定是否启用或禁用此功能。如果启用,则意味着端点支持WS-Addressing,但不需要使用它。相应
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyaddressing">
     *  3.1.1必须在生成的WSDL中生成寻址断言</a>。
     * 
     */
    boolean enabled() default true;

    /**
     * If addressing is enabled, this property determines whether the endpoint
     * requires WS-Addressing. If required is true, the endpoint requires
     * WS-Addressing and WS-Addressing headers MUST
     * be present on incoming messages. A corresponding
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyaddressing">
     * 3.1.1 Addressing Assertion</a> must be generated in the WSDL.
     * <p>
     *  如果启用寻址,此属性确定端点是否需要WS-Addressing。如果需要,则端点需要WS-Addressing和WS-Addressing头必须存在于输入消息上。 A对应
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyaddressing">
     *  3.1.1必须在WSDL中生成寻址断言</a>。
     * 
     */
    boolean required() default false;

    /**
     * If addressing is enabled, this property determines whether endpoint
     * requires the use of anonymous responses, or non-anonymous responses,
     * or all.
     *
     * <p>
     * {@link Responses#ALL} supports all response types and this is the
     * default value.
     *
     * <p>
     * {@link Responses#ANONYMOUS} requires the use of only anonymous
     * responses. It will result into wsam:AnonymousResponses nested assertion
     * as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
     * 3.1.2 AnonymousResponses Assertion</a> in the generated WSDL.
     *
     * <p>
     * {@link Responses#NON_ANONYMOUS} requires the use of only non-anonymous
     * responses. It will result into
     * wsam:NonAnonymousResponses nested assertion as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
     * 3.1.3 NonAnonymousResponses Assertion</a> in the generated WSDL.
     *
     * <p>
     * 如果启用寻址,此属性确定端点是否需要使用匿名响应,非匿名响应或全部。
     * 
     * <p>
     *  {@link Responses#ALL}支持所有响应类型,这是默认值。
     * 
     * <p>
     *  {@link回应#ANONYMOUS}需要使用匿名回应。它将导致wsam：AnonymousResponses在中指定的嵌套断言
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
     * 
     * @since JAX-WS 2.2
     */
    Responses responses() default Responses.ALL;

}
