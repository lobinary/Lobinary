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

import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceProvider;

/**
 * This feature represents the use of MTOM with a
 * web service.
 * <p>
 * This annotation MUST only be used in conjunction the
 * <code>javax.jws.WebService</code>, {@link WebServiceProvider},
 * {@link WebServiceRef} annotations.
 * When used with the <code>javax.jws.WebService</code> annotation this
 * annotation MUST only be used on the service endpoint implementation
 * class.
 * When used with a <code>WebServiceRef</code> annotation, this annotation
 * MUST only be used when a proxy instance is created. The injected SEI
 * proxy, and endpoint MUST honor the values of the <code>MTOM</code>
 * annotation.
 * <p>
 *
 * This annotation's behaviour is defined by the corresponding feature
 * {@link MTOMFeature}.
 *
 * <p>
 *  此功能表示MTOM与Web服务的使用。
 * <p>
 *  此注释必须只能与<code> javax.jws.WebService </code>,{@link WebServiceProvider},{@link WebServiceRef}注释结合使用。
 * 当与<code> javax.jws.WebService </code>注释一起使用时,此注释必须仅在服务端点实现类上使用。
 * 当与<code> WebServiceRef </code>注释一起使用时,此注释必须仅在创建代理实例时使用。注入的SEI代理和端点务必遵守<code> MTOM </code>注释的值。
 * <p>
 * 
 *  此注释的行为由相应的特性{@link MTOMFeature}定义。
 * 
 * @since JAX-WS 2.1
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WebServiceFeatureAnnotation(id=MTOMFeature.ID,bean=MTOMFeature.class)
public @interface MTOM {
    /**
     * Specifies if this feature is enabled or disabled.
     * <p>
     * 
     */
    boolean enabled() default true;

    /**
     * Property for MTOM threshold value. When MTOM is enabled, binary data above this
     * size in bytes will be XOP encoded or sent as attachment. The value of this property
     * MUST always be >= 0. Default value is 0.
     * <p>
     *  指定是否启用或禁用此功能。
     * 
     */
    int threshold() default 0;
}
