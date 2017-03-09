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

package javax.xml.stream.events;

import java.io.Writer;
import javax.xml.namespace.QName;
/**
 * This is the base event interface for handling markup events.
 * Events are value objects that are used to communicate the
 * XML 1.0 InfoSet to the Application.  Events may be cached
 * and referenced after the parse has completed.
 *
 * <p>
 *  这是处理标记事件的基本事件接口。事件是用于将XML 1.0 InfoSet传递到应用程序的值对象。事件可以在解析完成后缓存和引用。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see javax.xml.stream.XMLEventReader
 * @see Characters
 * @see ProcessingInstruction
 * @see StartElement
 * @see EndElement
 * @see StartDocument
 * @see EndDocument
 * @see EntityReference
 * @see EntityDeclaration
 * @see NotationDeclaration
 * @since 1.6
 */
public interface XMLEvent extends javax.xml.stream.XMLStreamConstants {

  /**
   * Returns an integer code for this event.
   * <p>
   *  返回此事件的整数代码。
   * 
   * 
   * @see #START_ELEMENT
   * @see #END_ELEMENT
   * @see #CHARACTERS
   * @see #ATTRIBUTE
   * @see #NAMESPACE
   * @see #PROCESSING_INSTRUCTION
   * @see #COMMENT
   * @see #START_DOCUMENT
   * @see #END_DOCUMENT
   * @see #DTD
   */
  public int getEventType();

  /**
   * Return the location of this event.  The Location
   * returned from this method is non-volatile and
   * will retain its information.
   * <p>
   *  返回此事件的位置。从此方法返回的位置是非易失性的,并且将保留其信息。
   * 
   * 
   * @see javax.xml.stream.Location
   */
  javax.xml.stream.Location getLocation();

  /**
   * A utility function to check if this event is a StartElement.
   * <p>
   *  用于检查此事件是否为StartElement的实用程序函数。
   * 
   * 
   * @see StartElement
   */
  public boolean isStartElement();

  /**
   * A utility function to check if this event is an Attribute.
   * <p>
   *  用于检查此事件是否为属性的效用函数。
   * 
   * 
   * @see Attribute
   */
  public boolean isAttribute();

  /**
   * A utility function to check if this event is a Namespace.
   * <p>
   *  用于检查此事件是否为命名空间的实用程序函数。
   * 
   * 
   * @see Namespace
   */
  public boolean isNamespace();


  /**
   * A utility function to check if this event is a EndElement.
   * <p>
   *  用于检查此事件是否为EndElement的效用函数。
   * 
   * 
   * @see EndElement
   */
  public boolean isEndElement();

  /**
   * A utility function to check if this event is an EntityReference.
   * <p>
   *  用于检查此事件是否为EntityReference的实用程序函数。
   * 
   * 
   * @see EntityReference
   */
  public boolean isEntityReference();

  /**
   * A utility function to check if this event is a ProcessingInstruction.
   * <p>
   *  用于检查此事件是否为ProcessingInstruction的实用程序函数。
   * 
   * 
   * @see ProcessingInstruction
   */
  public boolean isProcessingInstruction();

  /**
   * A utility function to check if this event is Characters.
   * <p>
   *  用于检查此事件是否为字符的效用函数。
   * 
   * 
   * @see Characters
   */
  public boolean isCharacters();

  /**
   * A utility function to check if this event is a StartDocument.
   * <p>
   *  用于检查此事件是否为StartDocument的实用程序函数。
   * 
   * 
   * @see StartDocument
   */
  public boolean isStartDocument();

  /**
   * A utility function to check if this event is an EndDocument.
   * <p>
   *  用于检查此事件是否为EndDocument的实用程序函数。
   * 
   * 
   * @see EndDocument
   */
  public boolean isEndDocument();

  /**
   * Returns this event as a start element event, may result in
   * a class cast exception if this event is not a start element.
   * <p>
   *  将此事件作为start元素事件返回,如果此事件不是start元素,可能会导致类转换异常。
   * 
   */
  public StartElement asStartElement();

  /**
   * Returns this event as an end  element event, may result in
   * a class cast exception if this event is not a end element.
   * <p>
   *  返回此事件作为结束元素事件,如果此事件不是结束元素,可能会导致类转换异常。
   * 
   */
  public EndElement asEndElement();

  /**
   * Returns this event as Characters, may result in
   * a class cast exception if this event is not Characters.
   * <p>
   *  将此事件返回为Characters,如果此事件不是Characters,可能会导致类转换异常。
   * 
   */
  public Characters asCharacters();

  /**
   * This method is provided for implementations to provide
   * optional type information about the associated event.
   * It is optional and will return null if no information
   * is available.
   * <p>
   * 该方法被提供用于实现以提供关于相关联的事件的可选类型信息。它是可选的,如果没有可用的信息,将返回null。
   * 
   */
  public QName getSchemaType();

  /**
   * This method will write the XMLEvent as per the XML 1.0 specification as Unicode characters.
   * No indentation or whitespace should be outputted.
   *
   * Any user defined event type SHALL have this method
   * called when being written to on an output stream.
   * Built in Event types MUST implement this method,
   * but implementations MAY choose not call these methods
   * for optimizations reasons when writing out built in
   * Events to an output stream.
   * The output generated MUST be equivalent in terms of the
   * infoset expressed.
   *
   * <p>
   *  此方法将根据XML 1.0规范将XMLEvent编写为Unicode字符。不应输出缩进或空格。
   * 
   * 
   * @param writer The writer that will output the data
   * @throws XMLStreamException if there is a fatal error writing the event
   */
  public void writeAsEncodedUnicode(Writer writer)
    throws javax.xml.stream.XMLStreamException;

}
