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
 *  Used to annotate a generated service interface.
 *
 *  <p>The information specified in this annotation is sufficient
 *  to uniquely identify a <code>wsdl:service</code>
 *  element inside a WSDL document. This <code>wsdl:service</code>
 *  element represents the Web service for which the generated
 *  service interface provides a client view.
 *
 * <p>
 *  用于注释生成的服务接口。
 * 
 *  <p>此注释中指定的信息足以唯一标识WSDL文档中的<code> wsdl：service </code>元素。
 * 此<code> wsdl：service </code>元素表示生成的服务接口提供了客户端视图的Web服务。
 * 
 * 
 *  @since JAX-WS 2.0
**/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebServiceClient {
  /**
   *  The local name of the Web service.
   * <p>
   *  Web服务的本地名称。
   * 
   * 
  **/
  String name() default "";

  /**
   *  The namespace for the Web service.
   * <p>
   *  Web服务的命名空间。
   * 
   * 
  **/
  String targetNamespace() default "";

  /**
   *  The location of the WSDL document for the service (a URL).
   * <p>
   *  服务的WSDL文档的位置(URL)。
   * 
  **/
  String wsdlLocation() default "";
}
