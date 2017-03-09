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

package javax.xml.ws.handler;

import javax.xml.namespace.QName;

/**
 *  The <code>PortInfo</code> interface is used by a
 *  <code>HandlerResolver</code> to query information about
 *  the port it is being asked to create a handler chain for.
 *  <p>
 *  This interface is never implemented by an application,
 *  only by a JAX-WS implementation.
 *
 * <p>
 *  <code> PortInfo </code>接口由<code> HandlerResolver </code>使用,以查询有关要求为其创建处理程序链的端口的信息。
 * <p>
 *  此接口从不由应用程序实现,仅由JAX-WS实现实现。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public interface PortInfo {

  /**
   *  Gets the qualified name of the WSDL service name containing
   *  the port being accessed.
   *
   * <p>
   *  获取包含正在访问的端口的WSDL服务名称的限定名称。
   * 
   * 
   *  @return javax.xml.namespace.QName The qualified name of the WSDL service.
  **/
  public QName getServiceName();

  /**
   *  Gets the qualified name of the WSDL port being accessed.
   *
   * <p>
   *  获取正在访问的WSDL端口的限定名称。
   * 
   * 
   *  @return javax.xml.namespace.QName The qualified name of the WSDL port.
  **/
  public QName getPortName();

  /**
   *  Gets the URI identifying the binding used by the port being accessed.
   *
   * <p>
   *  获取标识正在访问的端口使用的绑定的URI。
   * 
   *  @return String The binding identifier for the port.
   *
   *  @see javax.xml.ws.Binding
  **/
  public String getBindingID();

}
