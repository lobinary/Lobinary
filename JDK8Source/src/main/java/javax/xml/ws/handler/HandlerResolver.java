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

/**
 *  <code>HandlerResolver</code> is an interface implemented
 *  by an application to get control over the handler chain
 *  set on proxy/dispatch objects at the time of their creation.
 *  <p>
 *  A <code>HandlerResolver</code> may be set on a <code>Service</code>
 *  using the <code>setHandlerResolver</code> method.
 * <p>
 *  When the runtime invokes a <code>HandlerResolver</code>, it will
 *  pass it a <code>PortInfo</code> object containing information
 *  about the port that the proxy/dispatch object will be accessing.
 *
 * <p>
 *  <code> HandlerResolver </code>是一个由应用程序实现的接口,用于在创建代理/分派对象时对其设置的处理程序链进行控制。
 * <p>
 *  可以使用<code> setHandlerResolver </code>方法在<code> Service </code>上设置<code> HandlerResolver </code>。
 * <p>
 *  当运行时调用<code> HandlerResolver </code>时,它会传递一个<code> PortInfo </code>对象,该对象包含有关代理/分发对象将要访问的端口的信息。
 * 
 *  @see javax.xml.ws.Service#setHandlerResolver
 *
 *  @since JAX-WS 2.0
**/
public interface HandlerResolver {

  /**
   *  Gets the handler chain for the specified port.
   *
   * <p>
   * 
   * 
   *  @param portInfo Contains information about the port being accessed.
   *  @return java.util.List&lt;Handler> chain
  **/
  public java.util.List<Handler> getHandlerChain(PortInfo portInfo);
}
