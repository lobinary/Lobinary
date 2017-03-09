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

import javax.xml.ws.soap.Addressing;
import javax.xml.ws.spi.WebServiceFeatureAnnotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The <code>WebServiceRef</code> annotation is used to
 * define a reference to a web service and
 * (optionally) an injection target for it.
 * It can be used to inject both service and proxy
 * instances. These injected references are not thread safe.
 * If the references are accessed by multiple threads,
 * usual synchronization techinques can be used to
 * support multiple threads.
 *
 * <p>
 * Web service references are resources in the Java EE 5 sense.
 * The annotations (for example, {@link Addressing}) annotated with
 * meta-annotation {@link WebServiceFeatureAnnotation}
 * can be used in conjunction with <code>WebServiceRef</code>.
 * The created reference MUST be configured with annotation's web service
 * feature.
 *
 * <p>
 * For example, in the code below, the injected
 * <code>StockQuoteProvider</code> proxy MUST
 * have WS-Addressing enabled as specifed by the
 * {@link Addressing}
 * annotation.
 *
 * <pre><code>
 *    public class MyClient {
 *       &#64;Addressing
 *       &#64;WebServiceRef(StockQuoteService.class)
 *       private StockQuoteProvider stockQuoteProvider;
 *       ...
 *    }
 * </code></pre>
 *
 * <p>
 * If a JAX-WS implementation encounters an unsupported or unrecognized
 * annotation annotated with the <code>WebServiceFeatureAnnotation</code>
 * that is specified with <code>WebServiceRef</code>, an ERROR MUST be given.
 *
 * <p>
 *  <code> WebServiceRef </code>注释用于定义对Web服务的引用和(可选)它的注入目标。它可以用于注入服务和代理实例。这些注入的引用不是线程安全的。
 * 如果引用被多个线程访问,则通常的同步技术可以用于支持多个线程。
 * 
 * <p>
 *  Web服务引用是Java EE 5意义上的资源。
 * 使用元注释{@link WebServiceFeatureAnnotation}注释的注释(例如,{@link Addressing})可以与<code> WebServiceRef </code>结合
 * 使用。
 *  Web服务引用是Java EE 5意义上的资源。创建的引用必须使用注释的Web服务功能进行配置。
 * 
 * <p>
 *  例如,在下面的代码中,注入的<code> StockQuoteProvider </code>代理必须具有由{@link Addressing}注释指定的WS-Addressing。
 * 
 *  <pre> <code> public class MyClient {@Addressing @WebServiceRef(StockQuoteService.class)private StockQuoteProvider stockQuoteProvider; ...}
 *  </code> </pre>。
 * 
 * <p>
 *  如果JAX-WS实现遇到使用<code> WebServiceRef </code>指定的<code> WebServiceFeatureAnnotation </code>注释的不支持或无法识别的注
 * 释,则必须给出错误。
 * 
 * 
 * @see javax.annotation.Resource
 * @see WebServiceFeatureAnnotation
 *
 * @since JAX-WS 2.0
 *
**/

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServiceRef {
    /**
     * The JNDI name of the resource.  For field annotations,
     * the default is the field name.  For method annotations,
     * the default is the JavaBeans property name corresponding
     * to the method.  For class annotations, there is no default
     * and this MUST be specified.
     *
     * The JNDI name can be absolute(with any logical namespace) or relative
     * to JNDI <code>java:comp/env</code> namespace.
     * <p>
     * 资源的JNDI名称。对于字段注释,默认为字段名称。对于方法注释,默认值是与方法对应的JavaBeans属性名称。对于类注解,没有默认值,必须指定。
     * 
     *  JNDI名称可以是绝对的(使用任何逻辑命名空间)或相对于JNDI <code> java：comp / env </code>命名空间。
     * 
     */
    String name() default "";

    /**
     * The Java type of the resource.  For field annotations,
     * the default is the type of the field.  For method annotations,
     * the default is the type of the JavaBeans property.
     * For class annotations, there is no default and this MUST be
     * specified.
     * <p>
     *  资源的Java类型。对于字段注释,默认值为字段的类型。对于方法注释,缺省值是JavaBeans属性的类型。对于类注解,没有默认值,必须指定。
     * 
     */
    Class<?> type() default Object.class;

    /**
     * A product specific name that this resource should be mapped to.
     * The name of this resource, as defined by the <code>name</code>
     * element or defaulted, is a name that is local to the application
     * component using the resource.  (When a relative JNDI name
     * is specified, then it's a name in the JNDI
     * <code>java:comp/env</code> namespace.)  Many application servers
     * provide a way to map these local names to names of resources
     * known to the application server.  This mapped name is often a
     * <i>global</i> JNDI name, but may be a name of any form.
     * <p>
     * Application servers are not required to support any particular
     * form or type of mapped name, nor the ability to use mapped names.
     * The mapped name is product-dependent and often installation-dependent.
     * No use of a mapped name is portable.
     * <p>
     *  此资源应映射到的产品特定名称。由<code> name </code>元素或defaulted定义的此资源的名称是使用该资源的应用程序组件的本地名称。
     *  (当指定相对的JNDI名称时,它是JNDI <code> java：comp / env </code>命名空间中的一个名称。
     * )许多应用程序服务器提供了一种将这些本地名称映射到应用程序已知的资源名称的方法服务器。此映射名称通常是<i>全局</i> JNDI名称,但可以是任何形式的名称。
     * <p>
     *  应用程序服务器不需要支持任何特定形式或类型的映射名称,也不能使用映射名称。映射的名称是产品相关的,通常是安装相关的。不使用映射名称是可移植的。
     * 
     */
    String mappedName() default "";

    /**
     * The service class, alwiays a type extending
     * <code>javax.xml.ws.Service</code>. This element MUST be specified
     * whenever the type of the reference is a service endpoint interface.
     * <p>
     * 服务类,延续一个类型扩展<code> javax.xml.ws.Service </code>。只要引用的类型是服务端点接口,就必须指定此元素。
     * 
     */
    // 2.1 has Class value() default Object.class;
    // Fixing this raw Class type correctly in 2.2 API. This shouldn't cause
    // any compatibility issues for applications.
    Class<? extends Service> value() default Service.class;

    /**
     * A URL pointing to the WSDL document for the web service.
     * If not specified, the WSDL location specified by annotations
     * on the resource type is used instead.
     * <p>
     *  指向Web服务的WSDL文档的URL。如果未指定,则使用资源类型上的注释指定的WSDL位置。
     * 
     */
    String wsdlLocation() default "";

    /**
     * A portable JNDI lookup name that resolves to the target
     * web service reference.
     *
     * <p>
     *  解析到目标Web服务引用的便携式JNDI查找名称。
     * 
     * @since JAX-WS 2.2
     */
    String lookup() default "";

}
