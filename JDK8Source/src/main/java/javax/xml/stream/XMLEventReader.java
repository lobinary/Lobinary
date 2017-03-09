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

package javax.xml.stream;

import javax.xml.stream.events.XMLEvent;

import java.util.Iterator;

/**
 *
 * This is the top level interface for parsing XML Events.  It provides
 * the ability to peek at the next event and returns configuration
 * information through the property interface.
 *
 * <p>
 *  这是解析XML事件的顶级接口。它提供了查看下一个事件并通过属性接口返回配置信息的功能。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see XMLInputFactory
 * @see XMLEventWriter
 * @since 1.6
 */
public interface XMLEventReader extends Iterator {
  /**
   * Get the next XMLEvent
   * <p>
   *  获取下一个XMLEvent
   * 
   * 
   * @see XMLEvent
   * @throws XMLStreamException if there is an error with the underlying XML.
   * @throws NoSuchElementException iteration has no more elements.
   */
  public XMLEvent nextEvent() throws XMLStreamException;

  /**
   * Check if there are more events.
   * Returns true if there are more events and false otherwise.
   * <p>
   *  检查是否有更多事件。如果有更多的事件则返回true,否则返回false。
   * 
   * 
   * @return true if the event reader has more events, false otherwise
   */
  public boolean hasNext();

  /**
   * Check the next XMLEvent without reading it from the stream.
   * Returns null if the stream is at EOF or has no more XMLEvents.
   * A call to peek() will be equal to the next return of next().
   * <p>
   *  检查下一个XMLEvent,而不从流中读取它。如果流处于EOF或没有更多的XMLEvent,则返回null。对peek()的调用将等于next()的下一个返回。
   * 
   * 
   * @see XMLEvent
   * @throws XMLStreamException
   */
  public XMLEvent peek() throws XMLStreamException;

  /**
   * Reads the content of a text-only element. Precondition:
   * the current event is START_ELEMENT. Postcondition:
   * The current event is the corresponding END_ELEMENT.
   * <p>
   *  读取纯文本元素的内容。前提条件：当前活动为START_ELEMENT。后置条件：当前事件是相应的END_ELEMENT。
   * 
   * 
   * @throws XMLStreamException if the current event is not a START_ELEMENT
   * or if a non text element is encountered
   */
  public String getElementText() throws XMLStreamException;

  /**
   * Skips any insignificant space events until a START_ELEMENT or
   * END_ELEMENT is reached. If anything other than space characters are
   * encountered, an exception is thrown. This method should
   * be used when processing element-only content because
   * the parser is not able to recognize ignorable whitespace if
   * the DTD is missing or not interpreted.
   * <p>
   *  跳过任何不重要的空间事件,直到达到START_ELEMENT或END_ELEMENT。如果遇到空格字符以外的任何字符,将抛出异常。
   * 当处理纯元素内容时,应使用此方法,因为如果DTD丢失或未解释,解析器不能识别可忽略的空格。
   * 
   * 
   * @throws XMLStreamException if anything other than space characters are encountered
   */
  public XMLEvent nextTag() throws XMLStreamException;

  /**
   * Get the value of a feature/property from the underlying implementation
   * <p>
   *  从底层实现获取要素/属性的值
   * 
   * 
   * @param name The name of the property
   * @return The value of the property
   * @throws IllegalArgumentException if the property is not supported
   */
  public Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException;

  /**
   * Frees any resources associated with this Reader.  This method does not close the
   * underlying input source.
   * <p>
   *  释放与此阅读器相关联的任何资源。此方法不会关闭底层输入源。
   * 
   * @throws XMLStreamException if there are errors freeing associated resources
   */
  public void close() throws XMLStreamException;
}
