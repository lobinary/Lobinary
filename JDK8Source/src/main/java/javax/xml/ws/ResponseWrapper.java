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

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * Used to annotate methods in the Service Endpoint Interface with the response
 * wrapper bean to be used at runtime. The default value of the <code>localName</code> is
 * the <code>operationName</code> as defined in <code>WebMethod</code> annotation appended with
 * <code>Response</code> and the <code>targetNamespace</code> is the target namespace of the SEI.
 * <p> When starting from Java this annotation is used resolve
 * overloading conflicts in document literal mode. Only the <code>className</code>
 * is required in this case.
 *
 * <p>
 *  用于使用在运行时使用的响应包装器bean注释服务端点接口中的方法。
 *  <code> localName </code>的默认值是在<code> Response </code>附加的<code> WebMethod </code>注释中定义的<code> operati
 * onName </code> targetNamespace </code>是SEI的目标命名空间。
 *  用于使用在运行时使用的响应包装器bean注释服务端点接口中的方法。 <p>从Java启动时,此注释用于解决文档文字模式中的重载冲突。
 * 在这种情况下,只需要<code> className </code>。
 * 
 * 
 *  @since JAX-WS 2.0
**/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrapper {

    /**
     * Element's local name.
     * <p>
     *  元素的本地名称。
     * 
     */
    public String localName() default "";

    /**
     * Element's namespace name.
     * <p>
     *  元素的命名空间名称。
     * 
     */
    public String targetNamespace() default "";

    /**
     * Response wrapper bean name.
     * <p>
     *  响应包装器bean名称。
     * 
     */
    public String className() default "";

    /**
     * wsdl:part name for the wrapper part
     *
     * <p>
     *  wsdl：包装器部件的部件名称
     * 
     * @since JAX-WS 2.2
     */
    public String partName() default "";

}
