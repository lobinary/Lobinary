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
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

/**
 * This interface defines a class that allows a user to register
 * a way to allocate events given an XMLStreamReader.  An implementation
 * is not required to use the XMLEventFactory implementation but this
 * is recommended.  The XMLEventAllocator can be set on an XMLInputFactory
 * using the property "javax.xml.stream.allocator"
 *
 * <p>
 *  此接口定义一个类,允许用户注册一个方式来分配给定XMLStreamReader的事件。实现不需要使用XMLEventFactory实现,但这是推荐。
 *  XMLEventAllocator可以在XMLInputFactory上使用属性"javax.xml.stream.allocator"设置,。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see javax.xml.stream.XMLInputFactory
 * @see javax.xml.stream.XMLEventFactory
 * @since 1.6
 */
public interface XMLEventAllocator {

  /**
   * This method creates an instance of the XMLEventAllocator. This
   * allows the XMLInputFactory to allocate a new instance per reader.
   * <p>
   *  此方法创建XMLEventAllocator的实例。这允许XMLInputFactory为每个阅读器分配一个新实例。
   * 
   */
  public XMLEventAllocator newInstance();

  /**
   * This method allocates an event given the current
   * state of the XMLStreamReader.  If this XMLEventAllocator
   * does not have a one-to-one mapping between reader states
   * and events this method will return null.  This method
   * must not modify the state of the XMLStreamReader.
   * <p>
   *  此方法分配给定XMLStreamReader的当前状态的事件。如果此XMLEventAllocator在读取器状态和事件之间没有一对一映射,则此方法将返回null。
   * 此方法不能修改XMLStreamReader的状态。
   * 
   * 
   * @param reader The XMLStreamReader to allocate from
   * @return the event corresponding to the current reader state
   */
  public XMLEvent allocate(XMLStreamReader reader)
    throws XMLStreamException;

  /**
   * This method allocates an event or set of events
   * given the current
   * state of the XMLStreamReader and adds the event
   * or set of events to the
   * consumer that was passed in.  This method can be used
   * to expand or contract reader states into event states.
   * This method may modify the state of the XMLStreamReader.
   * <p>
   *  此方法在给定XMLStreamReader的当前状态的情况下分配事件或事件集合,并将事件或事件集合添加到传入的使用者。此方法可用于将读取器状态扩展或收缩为事件状态。
   * 此方法可以修改XMLStreamReader的状态。
   * 
   * @param reader The XMLStreamReader to allocate from
   * @param consumer The XMLEventConsumer to add to.
   */
  public void allocate(XMLStreamReader reader, XMLEventConsumer consumer)
    throws XMLStreamException;

}
