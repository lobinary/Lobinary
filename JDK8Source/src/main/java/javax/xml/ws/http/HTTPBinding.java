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

package javax.xml.ws.http;


import javax.xml.ws.Binding;

/** The <code>HTTPBinding</code> interface is an
 *  abstraction for the XML/HTTP binding.
 *
 * <p>
 *  XML / HTTP绑定的抽象。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public interface HTTPBinding extends Binding {

  /**
   * A constant representing the identity of the XML/HTTP binding.
   * <p>
   *  表示XML / HTTP绑定的标识的常量。
   */
  public static final String HTTP_BINDING = "http://www.w3.org/2004/08/wsdl/http";
}
