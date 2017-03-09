/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.ws.ProtocolException;
import javax.xml.ws.handler.MessageContext;

/** The <code>Handler</code> interface
 *  is the base interface for JAX-WS handlers.
 *
 * <p>
 *  是JAX-WS处理程序的基本接口。
 * 
 * 
 *  @since JAX-WS 2.0
**/
public interface Handler<C extends MessageContext> {

  /** The <code>handleMessage</code> method is invoked for normal processing
   *  of inbound and outbound messages. Refer to the description of the handler
   *  framework in the JAX-WS specification for full details.
   *
   * <p>
   *  的入站和出站邮件。有关完整的详细信息,请参阅JAX-WS规范中的处理程序框架的描述。
   * 
   * 
   *  @param context the message context.
   *  @return An indication of whether handler processing should continue for
   *  the current message
   *                 <ul>
   *                 <li>Return <code>true</code> to continue
   *                     processing.</li>
   *                 <li>Return <code>false</code> to block
   *                     processing.</li>
   *                  </ul>
   *  @throws RuntimeException Causes the JAX-WS runtime to cease
   *    handler processing and generate a fault.
   *  @throws ProtocolException Causes the JAX-WS runtime to switch to
   *    fault message processing.
  **/
  public boolean handleMessage(C context);

  /** The <code>handleFault</code> method is invoked for fault message
   *  processing.  Refer to the description of the handler
   *  framework in the JAX-WS specification for full details.
   *
   * <p>
   *  处理。有关完整的详细信息,请参阅JAX-WS规范中的处理程序框架的描述。
   * 
   * 
   *  @param context the message context
   *  @return An indication of whether handler fault processing should continue
   *  for the current message
   *                 <ul>
   *                 <li>Return <code>true</code> to continue
   *                     processing.</li>
   *                 <li>Return <code>false</code> to block
   *                     processing.</li>
   *                  </ul>
   *  @throws RuntimeException Causes the JAX-WS runtime to cease
   *    handler fault processing and dispatch the fault.
   *  @throws ProtocolException Causes the JAX-WS runtime to cease
   *    handler fault processing and dispatch the fault.
  **/
  public boolean handleFault(C context);

  /**
   * Called at the conclusion of a message exchange pattern just prior to
   * the JAX-WS runtime dispatching a message, fault or exception.  Refer to
   * the description of the handler
   * framework in the JAX-WS specification for full details.
   *
   * <p>
   *  在刚好在JAX-WS运行时分派消息,故障或异常之前的消息交换模式的结束时调用。有关完整的详细信息,请参阅JAX-WS规范中的处理程序框架的描述。
   * 
   * @param context the message context
  **/
  public void close(MessageContext context);
}
