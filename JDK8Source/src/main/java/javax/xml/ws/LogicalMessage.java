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

import javax.xml.transform.Source;
import javax.xml.bind.JAXBContext;

/** The <code>LogicalMessage</code> interface represents a
 *  protocol agnostic XML message and contains methods that
 *  provide access to the payload of the message.
 *
 * <p>
 *  协议不可知的XML消息,并且包含提供对消息的有效载荷的访问的方法。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public interface LogicalMessage {

  /** Gets the message payload as an XML source, may be called
   *  multiple times on the same LogicalMessage instance, always
   *  returns a new <code>Source</code> that may be used to retrieve the entire
   *  message payload.
   *
   *  <p>If the returned <code>Source</code> is an instance of
   *  <code>DOMSource</code>, then
   *  modifications to the encapsulated DOM tree change the message
   *  payload in-place, there is no need to susequently call
   *  <code>setPayload</code>. Other types of <code>Source</code> provide only
   *  read access to the message payload.
   *
   * <p>
   *  多次在同一个LogicalMessage实例上,始终返回一个新的<code> Source </code>,可用于检索整个消息有效内容。
   * 
   *  <p>如果返回的<code> Source </code>是<code> DOMSource </code>的一个实例,那么对封装的DOM树的修改会改变消息有效载荷的位置,没有必要调用<代码> set
   * Payload </code>。
   * 其他类型的<code> Source </code>只提供对邮件有效内容的读取访问。
   * 
   * 
   *  @return The contained message payload; returns <code>null</code> if no
   *          payload is present in this message.
  **/
  public Source getPayload();

  /** Sets the message payload
   *
   * <p>
   * 
   *  @param  payload message payload
   *  @throws WebServiceException If any error during the setting
   *          of the payload in this message
   *  @throws java.lang.UnsupportedOperationException If this
   *          operation is not supported
  **/
  public void setPayload(Source payload);

  /** Gets the message payload as a JAXB object. Note that there is no
   *  connection between the returned object and the message payload,
   *  changes to the payload require calling <code>setPayload</code>.
   *
   * <p>
   * 
   *  @param  context The JAXBContext that should be used to unmarshall
   *          the message payload
   *  @return The contained message payload; returns <code>null</code> if no
   *          payload is present in this message
   *  @throws WebServiceException If an error occurs when using a supplied
   *     JAXBContext to unmarshall the payload. The cause of
   *     the WebServiceException is the original JAXBException.
  **/
  public Object getPayload(JAXBContext context);

  /** Sets the message payload
   *
   * <p>
   *  返回的对象和消息有效载荷之间的连接,对有效载荷的改变需要调用<code> setPayload </code>。
   * 
   * 
   *  @param  payload message payload
   *  @param  context The JAXBContext that should be used to marshall
   *          the payload
   *  @throws java.lang.UnsupportedOperationException If this
   *          operation is not supported
   *  @throws WebServiceException If an error occurs when using the supplied
   *     JAXBContext to marshall the payload. The cause of
   *     the WebServiceException is the original JAXBException.
  **/
  public void setPayload(Object payload, JAXBContext context);
}
