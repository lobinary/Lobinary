/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * <p>
 *  Oracle Corporation的版权所有(c)2009。版权所有。
 * 
 */

package javax.xml.stream.util;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLStreamException;

/**
 * This interface defines an event consumer interface.  The contract of the
 * of a consumer is to accept the event.  This interface can be used to
 * mark an object as able to receive events.  Add may be called several
 * times in immediate succession so a consumer must be able to cache
 * events it hasn't processed yet.
 *
 * <p>
 *  此接口定义事件使用者接口。消费者的合同是接受该事件。此接口可用于将对象标记为能够接收事件。添加可以被直接调用几次,因此消费者必须能够缓存尚未处理的事件。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface XMLEventConsumer {

  /**
   * This method adds an event to the consumer. Calling this method
   * invalidates the event parameter. The client application should
   * discard all references to this event upon calling add.
   * The behavior of an application that continues to use such references
   * is undefined.
   *
   * <p>
   *  此方法向消费者添加事件。调用此方法会使事件参数无效。客户端应用程序应在调用add时放弃对此事件的所有引用。继续使用此类引用的应用程序的行为是未定义的。
   * 
   * @param event the event to add, may not be null
   */
  public void add(XMLEvent event)
    throws XMLStreamException;
}
