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

import javax.xml.stream.events.*;
import javax.xml.stream.util.XMLEventConsumer;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * This is the top level interface for writing XML documents.
 *
 * Instances of this interface are not required to validate the
 * form of the XML.
 *
 * <p>
 *  这是用于编写XML文档的顶级接口。
 * 
 *  不需要此接口的实例来验证XML的形式。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see XMLEventReader
 * @see javax.xml.stream.events.XMLEvent
 * @see javax.xml.stream.events.Characters
 * @see javax.xml.stream.events.ProcessingInstruction
 * @see javax.xml.stream.events.StartElement
 * @see javax.xml.stream.events.EndElement
 * @since 1.6
 */
public interface XMLEventWriter extends XMLEventConsumer {

  /**
   * Writes any cached events to the underlying output mechanism
   * <p>
   *  将任何缓存的事件写入底层输出机制
   * 
   * 
   * @throws XMLStreamException
   */
  public void flush() throws XMLStreamException;

  /**
   * Frees any resources associated with this stream
   * <p>
   *  释放与此流关联的任何资源
   * 
   * 
   * @throws XMLStreamException
   */
  public void close() throws XMLStreamException;

  /**
   * Add an event to the output stream
   * Adding a START_ELEMENT will open a new namespace scope that
   * will be closed when the corresponding END_ELEMENT is written.
   * <table border="2" rules="all" cellpadding="4">
   *   <thead>
   *     <tr>
   *       <th align="center" colspan="2">
   *         Required and optional fields for events added to the writer
   *       </th>
   *     </tr>
   *   </thead>
   *   <tbody>
   *     <tr>
   *       <th>Event Type</th>
   *       <th>Required Fields</th>
   *       <th>Optional Fields</th>
   *       <th>Required Behavior</th>
   *     </tr>
   *     <tr>
   *       <td> START_ELEMENT  </td>
   *       <td> QName name </td>
   *       <td> namespaces , attributes </td>
   *       <td> A START_ELEMENT will be written by writing the name,
   *       namespaces, and attributes of the event in XML 1.0 valid
   *       syntax for START_ELEMENTs.
   *       The name is written by looking up the prefix for
   *       the namespace uri.  The writer can be configured to
   *       respect prefixes of QNames.  If the writer is respecting
   *       prefixes it must use the prefix set on the QName.  The
   *       default behavior is to lookup the value for the prefix
   *       on the EventWriter's internal namespace context.
   *       Each attribute (if any)
   *       is written using the behavior specified in the attribute
   *       section of this table.  Each namespace (if any) is written
   *       using the behavior specified in the namespace section of this
   *       table.
   *       </td>
   *     </tr>
   *     <tr>
   *       <td> END_ELEMENT  </td>
   *       <td> Qname name  </td>
   *       <td> None </td>
   *       <td> A well formed END_ELEMENT tag is written.
   *       The name is written by looking up the prefix for
   *       the namespace uri.  The writer can be configured to
   *       respect prefixes of QNames.  If the writer is respecting
   *       prefixes it must use the prefix set on the QName.  The
   *       default behavior is to lookup the value for the prefix
   *       on the EventWriter's internal namespace context.
   *       If the END_ELEMENT name does not match the START_ELEMENT
   *       name an XMLStreamException is thrown.
   *       </td>
   *     </tr>
   *     <tr>
   *       <td> ATTRIBUTE  </td>
   *       <td> QName name , String value </td>
   *       <td> QName type </td>
   *       <td> An attribute is written using the same algorithm
   *            to find the lexical form as used in START_ELEMENT.
   *            The default is to use double quotes to wrap attribute
   *            values and to escape any double quotes found in the
   *            value.  The type value is ignored.
   *       </td>
   *     </tr>
   *     <tr>
   *       <td> NAMESPACE  </td>
   *       <td> String prefix, String namespaceURI,
   *            boolean isDefaultNamespaceDeclaration
   *      </td>
   *       <td> None  </td>
   *       <td> A namespace declaration is written.  If the
   *            namespace is a default namespace declaration
   *            (isDefault is true) then xmlns="$namespaceURI"
   *            is written and the prefix is optional.  If
   *            isDefault is false, the prefix must be declared
   *            and the writer must prepend xmlns to the prefix
   *            and write out a standard prefix declaration.
   *      </td>
   *     </tr>
   *     <tr>
   *       <td> PROCESSING_INSTRUCTION  </td>
   *       <td>   None</td>
   *       <td>   String target, String data</td>
   *       <td>   The data does not need to be present and may be
   *              null.  Target is required and many not be null.
   *              The writer
   *              will write data section
   *              directly after the target,
   *              enclosed in appropriate XML 1.0 syntax
   *      </td>
   *     </tr>
   *     <tr>
   *       <td> COMMENT  </td>
   *       <td> None  </td>
   *       <td> String comment  </td>
   *       <td> If the comment is present (not null) it is written, otherwise an
   *            an empty comment is written
   *      </td>
   *     </tr>
   *     <tr>
   *       <td> START_DOCUMENT  </td>
   *       <td> None  </td>
   *       <td> String encoding , boolean standalone, String version  </td>
   *       <td> A START_DOCUMENT event is not required to be written to the
   *             stream.  If present the attributes are written inside
   *             the appropriate XML declaration syntax
   *      </td>
   *     </tr>
   *     <tr>
   *       <td> END_DOCUMENT  </td>
   *       <td> None </td>
   *       <td> None  </td>
   *       <td> Nothing is written to the output  </td>
   *     </tr>
   *     <tr>
   *       <td> DTD  </td>
   *       <td> String DocumentTypeDefinition  </td>
   *       <td> None  </td>
   *       <td> The DocumentTypeDefinition is written to the output  </td>
   *     </tr>
   *   </tbody>
   * </table>
   * <p>
   *  向输出流添加事件添加一个START_ELEMENT将打开一个新的命名空间范围,将在写入相应的END_ELEMENT时关闭。
   * <table border="2" rules="all" cellpadding="4">
   * <thead>
   * <tr>
   * <th align="center" colspan="2">
   *  添加到编写器的事件的必填和可选字段
   * </th>
   * </tr>
   * </thead>
   * <tbody>
   * <tr>
   *  <th>事件类型</th> <th>必填字段</th> <th>可选字段</th> <th>必需的行为</th>
   * </tr>
   * <tr>
   *  <td> START_ELEMENT </td> <td> QName名称</td> <td>命名空间,属性</td> <td> START_ELEMENT将写入XML 1.0中事件的名称,名称空间和
   * 属性START_ELEMENT的有效语法。
   * 该名称是通过查找命名空间uri的前缀来编写的。可以将写入程序配置为遵循QNames的前缀。如果作者尊重前缀,它必须使用QName上设置的前缀。
   * 默认行为是在EventWriter的内部命名空间上下文中查找前缀的值。每个属性(如果有)都使用此表的属性部分中指定的行为写入。每个命名空间(如果有)都是使用此表的命名空间部分中指定的行为编写的。
   * </td>
   * </tr>
   * <tr>
   * <td> END_ELEMENT </td> <td> Qname name </td> <td>无</td> <td>写入格式良好的END_ELEMENT标记。
   * 该名称是通过查找命名空间uri的前缀来编写的。可以将写入程序配置为遵循QNames的前缀。如果作者尊重前缀,它必须使用QName上设置的前缀。
   * 默认行为是在EventWriter的内部命名空间上下文中查找前缀的值。如果END_ELEMENT名称与START_ELEMENT名称不匹配,则抛出XMLStreamException。
   * </td>
   * </tr>
   * <tr>
   *  <td> ATTRIBUTE </td> <td> QName名称,字符串值</td> <td> QName类型</td> <td>使用相同的算法编写属性以查找START_ELEMENT中使用的词法形
   * 式。
   * 默认值是使用双引号来包装属性值,并转义在值中找到的任何双引号。将忽略类型值。
   * </td>
   * </tr>
   * <tr>
   *  <td> NAMESPACE </td> <td>字符串前缀,String namespaceURI,boolean isDefaultNamespaceDeclaration
   * </td>
   *  <td>无</td> <td>写入命名空间声明。如果命名空间是默认命名空间声明(isDefault为true),那么将写入xmlns ="$ namespaceURI",前缀是可选的。
   * 如果isDefault为false,则必须声明前缀,并且写入程序必须在前缀前面添加xmlns并写出标准前缀声明。
   * </td>
   * </tr>
   * <tr>
   * <td> PROCESSING_INSTRUCTION </td> <td>无</td> <td>字符串目标,字符串数据</td> <td>数据不需要存在,可以为空。目标是必需的,并且许多不能为空。
   * 
   * @param event the event to be added
   * @throws XMLStreamException
   */
  public void add(XMLEvent event) throws XMLStreamException;

  /**
   * Adds an entire stream to an output stream,
   * calls next() on the inputStream argument until hasNext() returns false
   * This should be treated as a convenience method that will
   * perform the following loop over all the events in an
   * event reader and call add on each event.
   *
   * <p>
   * 写入器将直接在目标之后写入数据段,并包含在适当的XML 1.0语法中。
   * </td>
   * </tr>
   * <tr>
   *  <td> COMMENT </td> <td>无</td> <td>字符串注释</td> <td>如果注释存在(非空),则写入,否则写入空注释
   * </td>
   * </tr>
   * <tr>
   *  <td> START_DOCUMENT </td> <td>无</td> <td>字符串编码,boolean standalone,String version </td> <td> START_DO
   * CUMENT事件不需要写入流。
   * 如果存在,属性写在适当的XML声明语法内。
   * </td>
   * </tr>
   * <tr>
   *  <td> END_DOCUMENT </td> <td>无</td> <td>无</td> <td>没有任何内容写入输出</td>
   * </tr>
   * <tr>
   *  <td> DTD </td> <td> String DocumentTypeDefinition </td> <td>无</td> <td> DocumentTypeDefinition写入输出</td>
   * 。
   * </tr>
   * </tbody>
   * 
   * @param reader the event stream to add to the output
   * @throws XMLStreamException
   */

  public void add(XMLEventReader reader) throws XMLStreamException;

  /**
   * Gets the prefix the uri is bound to
   * <p>
   * </table>
   * 
   * @param uri the uri to look up
   * @throws XMLStreamException
   */
  public String getPrefix(String uri) throws XMLStreamException;

  /**
   * Sets the prefix the uri is bound to.  This prefix is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the prefix is bound in the root scope.
   * <p>
   *  将整个流添加到输出流,对输入流参数调用next(),直到hasNext()返回false这应该被视为方便的方法,将对事件读取器中的所有事件执行以下循环,并调用add事件。
   * 
   * 
   * @param prefix the prefix to bind to the uri
   * @param uri the uri to bind to the prefix
   * @throws XMLStreamException
   */
  public void setPrefix(String prefix, String uri) throws XMLStreamException;

  /**
   * Binds a URI to the default namespace
   * This URI is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the uri is bound in the root scope.
   * <p>
   *  获取uri绑定的前缀
   * 
   * 
   * @param uri the uri to bind to the default namespace
   * @throws XMLStreamException
   */
  public void setDefaultNamespace(String uri) throws XMLStreamException;

  /**
   * Sets the current namespace context for prefix and uri bindings.
   * This context becomes the root namespace context for writing and
   * will replace the current root namespace context.  Subsequent calls
   * to setPrefix and setDefaultNamespace will bind namespaces using
   * the context passed to the method as the root context for resolving
   * namespaces.
   * <p>
   *  设置uri绑定的前缀。此前缀绑定在当前START_ELEMENT / END_ELEMENT对的范围内。如果在写入START_ELEMENT之前调用此方法,则前缀在根范围内绑定。
   * 
   * 
   * @param context the namespace context to use for this writer
   * @throws XMLStreamException
   */
  public void setNamespaceContext(NamespaceContext context)
    throws XMLStreamException;

  /**
   * Returns the current namespace context.
   * <p>
   * 将URI绑定到默认名称空间此URI绑定在当前START_ELEMENT / END_ELEMENT对的范围内。如果在写入START_ELEMENT之前调用此方法,则uri将绑定到根范围。
   * 
   * 
   * @return the current namespace context
   */
  public NamespaceContext getNamespaceContext();


}
