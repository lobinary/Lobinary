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
 *  版权所有(c)2009 by Oracle Corporation保留所有权利
 * 
 */

package javax.xml.stream;

import java.io.Reader;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

/**
 *  The XMLStreamReader interface allows forward, read-only access to XML.
 *  It is designed to be the lowest level and most efficient way to
 *  read XML data.
 *
 * <p> The XMLStreamReader is designed to iterate over XML using
 * next() and hasNext().  The data can be accessed using methods such as getEventType(),
 * getNamespaceURI(), getLocalName() and getText();
 *
 * <p> The <a href="#next()">next()</a> method causes the reader to read the next parse event.
 * The next() method returns an integer which identifies the type of event just read.
 * <p> The event type can be determined using <a href="#getEventType()">getEventType()</a>.
 * <p> Parsing events are defined as the XML Declaration, a DTD,
 * start tag, character data, white space, end tag, comment,
 * or processing instruction.  An attribute or namespace event may be encountered
 * at the root level of a document as the result of a query operation.
 *
 * <p>For XML 1.0 compliance an XML processor must pass the
 * identifiers of declared unparsed entities, notation declarations and their
 * associated identifiers to the application.  This information is
 * provided through the property API on this interface.
 * The following two properties allow access to this information:
 * javax.xml.stream.notations and javax.xml.stream.entities.
 * When the current event is a DTD the following call will return a
 * list of Notations
 *  <code>List l = (List) getProperty("javax.xml.stream.notations");</code>
 * The following call will return a list of entity declarations:
 * <code>List l = (List) getProperty("javax.xml.stream.entities");</code>
 * These properties can only be accessed during a DTD event and
 * are defined to return null if the information is not available.
 *
 * <p>The following table describes which methods are valid in what state.
 * If a method is called in an invalid state the method will throw a
 * java.lang.IllegalStateException.
 *
 * <table border="2" rules="all" cellpadding="4">
 *   <thead>
 *     <tr>
 *       <th align="center" colspan="2">
 *         Valid methods for each state
 *       </th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <th>Event Type</th>
 *       <th>Valid Methods</th>
 *     </tr>
 *     <tr>
 *       <td> All States  </td>
 *       <td> getProperty(), hasNext(), require(), close(),
 *            getNamespaceURI(), isStartElement(),
 *            isEndElement(), isCharacters(), isWhiteSpace(),
 *            getNamespaceContext(), getEventType(),getLocation(),
 *            hasText(), hasName()
 *       </td>
 *     </tr>
 *     <tr>
 *       <td> START_ELEMENT  </td>
 *       <td> next(), getName(), getLocalName(), hasName(), getPrefix(),
 *            getAttributeXXX(), isAttributeSpecified(),
 *            getNamespaceXXX(),
 *            getElementText(), nextTag()
 *       </td>
 *     </tr>
 *       <td> ATTRIBUTE  </td>
 *       <td> next(), nextTag()
 *            getAttributeXXX(), isAttributeSpecified(),
 *       </td>
 *     </tr>
 *     </tr>
 *       <td> NAMESPACE  </td>
 *       <td> next(), nextTag()
 *            getNamespaceXXX()
 *       </td>
 *     </tr>
 *     <tr>
 *       <td> END_ELEMENT  </td>
 *       <td> next(), getName(), getLocalName(), hasName(), getPrefix(),
 *            getNamespaceXXX(), nextTag()
 *      </td>
 *     </tr>
 *     <tr>
 *       <td> CHARACTERS  </td>
 *       <td> next(), getTextXXX(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> CDATA  </td>
 *       <td> next(), getTextXXX(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> COMMENT  </td>
 *       <td> next(), getTextXXX(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> SPACE  </td>
 *       <td> next(), getTextXXX(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> START_DOCUMENT  </td>
 *       <td> next(), getEncoding(), getVersion(), isStandalone(), standaloneSet(),
 *            getCharacterEncodingScheme(), nextTag()</td>
 *     </tr>
 *     <tr>
 *       <td> END_DOCUMENT  </td>
 *       <td> close()</td>
 *     </tr>
 *     <tr>
 *       <td> PROCESSING_INSTRUCTION  </td>
 *       <td> next(), getPITarget(), getPIData(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> ENTITY_REFERENCE  </td>
 *       <td> next(), getLocalName(), getText(), nextTag() </td>
 *     </tr>
 *     <tr>
 *       <td> DTD  </td>
 *       <td> next(), getText(), nextTag() </td>
 *     </tr>
 *   </tbody>
 *  </table>
 *
 * <p>
 *  XMLStreamReader接口允许对XML的前向,只读访问它被设计为读取XML数据的最低级别和最高效的方式
 * 
 *  <p> XMLStreamReader设计为使用next()和hasNext()迭代XML。
 * 可以使用getEventType(),getNamespaceURI(),getLocalName()和getText()等方法访问数据。
 * 
 * <p> <a href=\"#next()\">下一个()</a>方法会使读取器读取下一个解析事件next()方法返回一个整数,它标识刚刚读取的事件的类型<p >可以使用<a href=\"#getEventType()\">
 *  getEventType()</a>确定事件类型。
 * 解析事件定义为XML声明,DTD,开始标记,字符数据,空格,结束标记,注释或处理指令作为查询操作的结果,可能在文档的根级别遇到属性或命名空间事件。
 * 
 * <p>对于XML 10合规性,XML处理器必须将已声明的解析实体,符号声明及其关联标识符的标识符传递给应用程序。此信息通过此接口上的属性API提供。
 * 以下两个属性允许访问此信息：javaxxmlstreamnotations和javaxxmlstreamentities当当前事件是DTD时,下面的调用将返回一个Notations的列表<code> L
 * ist l =(List)getProperty("javaxxmlstreamnotations"); </code>以下调用将返回实体声明列表：代码> List l =(List)getProper
 * ty("javaxxmlstreamentities"); </code>这些属性只能在DTD事件期间访问,并且定义为在信息不可用时返回null。
 * <p>对于XML 10合规性,XML处理器必须将已声明的解析实体,符号声明及其关联标识符的标识符传递给应用程序。此信息通过此接口上的属性API提供。
 * 
 * <p>下表说明哪些方法在什么状态下有效如果方法在无效状态中调用,则方法将抛出javalangIllegalStateException异常
 * 
 * <table border="2" rules="all" cellpadding="4">
 * <thead>
 * <tr>
 * <th align="center" colspan="2">
 *  每个状态的有效方法
 * </th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <th>事件类型</th> <th>有效方法</th>
 * </tr>
 * <tr>
 *  <td>所有国家</td> <td> getProperty(),hasNext(),require(),close(),getNamespaceURI(),isStartElement(),isEn
 * dElement(),isCharacters(),isWhiteSpace(),getNamespaceContext ),getEventType(),getLocation(),hasText()
 * ,hasName()。
 * </td>
 * </tr>
 * <tr>
 *  <td> START_ELEMENT </td> <td> next(),getName(),getLocalName(),hasName(),getPrefix(),getAttributeXXX(
 * ),isAttributeSpecified(),getNamespaceXXX(),getElementText。
 * </td>
 * </tr>
 *  <td> ATTRIBUTE </td> <td> next(),nextTag()getAttributeXXX(),isAttributeSpecified(),
 * </td>
 * </tr>
 * </tr>
 * <td> NAMESPACE </td> <td> next(),nextTag()getNamespaceXXX()
 * </td>
 * </tr>
 * <tr>
 *  <td> END_ELEMENT </td> <td> next(),getName(),getLocalName(),hasName(),getPrefix(),getNamespaceXXX
 * </td>
 * </tr>
 * <tr>
 *  <td> CHARACTERS </td> <td> next(),getTextXXX(),nextTag()</td>
 * </tr>
 * <tr>
 *  <td> CDATA </td> <td> next(),getTextXXX(),nextTag()</td>
 * </tr>
 * <tr>
 *  <td> COMMENT </td> <td> next(),getTextXXX(),nextTag()</td>
 * </tr>
 * <tr>
 *  <td> SPACE </td> <td> next(),getTextXXX(),nextTag()</td>
 * </tr>
 * <tr>
 *  <td> START_DOCUMENT </td> <td> next(),getEncoding(),getVersion(),isStandalone(),standaloneSet(),getC
 * haracterEncodingScheme(),nextTag()</td>。
 * </tr>
 * <tr>
 *  <td> END_DOCUMENT </td> <td> close()</td>
 * </tr>
 * <tr>
 *  <td> PROCESSING_INSTRUCTION </td> <td> next(),getPITarget(),getPIData(),nextTag()</td>
 * </tr>
 * <tr>
 * <td> ENTITY_REFERENCE </td> <td> next(),getLocalName(),getText(),nextTag()</td>
 * </tr>
 * <tr>
 *  <td> DTD </td> <td> next(),getText(),nextTag()</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see javax.xml.stream.events.XMLEvent
 * @see XMLInputFactory
 * @see XMLStreamWriter
 * @since 1.6
 */
public interface XMLStreamReader extends XMLStreamConstants {
  /**
   * Get the value of a feature/property from the underlying implementation
   * <p>
   *  从底层实现获取要素/属性的值
   * 
   * 
   * @param name The name of the property, may not be null
   * @return The value of the property
   * @throws IllegalArgumentException if name is null
   */
  public Object getProperty(java.lang.String name) throws java.lang.IllegalArgumentException;

  /**
   * Get next parsing event - a processor may return all contiguous
   * character data in a single chunk, or it may split it into several chunks.
   * If the property javax.xml.stream.isCoalescing is set to true
   * element content must be coalesced and only one CHARACTERS event
   * must be returned for contiguous element content or
   * CDATA Sections.
   *
   * By default entity references must be
   * expanded and reported transparently to the application.
   * An exception will be thrown if an entity reference cannot be expanded.
   * If element content is empty (i.e. content is "") then no CHARACTERS event will be reported.
   *
   * <p>Given the following XML:<br>
   * &lt;foo>&lt;!--description-->content text&lt;![CDATA[&lt;greeting>Hello&lt;/greeting>]]>other content&lt;/foo><br>
   * The behavior of calling next() when being on foo will be:<br>
   * 1- the comment (COMMENT)<br>
   * 2- then the characters section (CHARACTERS)<br>
   * 3- then the CDATA section (another CHARACTERS)<br>
   * 4- then the next characters section (another CHARACTERS)<br>
   * 5- then the END_ELEMENT<br>
   *
   * <p><b>NOTE:</b> empty element (such as &lt;tag/>) will be reported
   *  with  two separate events: START_ELEMENT, END_ELEMENT - This preserves
   *   parsing equivalency of empty element to &lt;tag>&lt;/tag>.
   *
   * This method will throw an IllegalStateException if it is called after hasNext() returns false.
   * <p>
   *  获取下一个解析事件 - 处理器可以返回单个块中的所有连续字符数据,或者它可以将其拆分成几个块如果属性javaxxmlstreamisCoalescing设置为true,则必须合并元素内容,并且必须为连
   * 续元素返回一个CHARACTERS事件内容或CDATA部分。
   * 
   * 默认情况下,实体引用必须被扩展并以透明方式报告给应用程序如果实体引用无法扩展,则会抛出异常如果元素内容为空(即内容为""),则不会报告CHARACTERS事件
   * 
   *  <p>给定以下XML：<br>&lt; foo>&lt;！ -  description  - > content text&lt;！[CDATA [&lt; greeting> Hello&lt; 
   * / greeting] br>当在foo上调用next()的行为将是：<br> 1-注释(COMMENT)<br> 2-然后字符部分(CHARACTERS)<br> 3-然后CDATA部分(另一个CHA
   * RACTERS )<br>然后下一个字符部分(另一个字符)<br> 5-然后END_ELEMENT <br>。
   * 
   * <p> <b>注意：</b>空元素(例如&lt; tag />)会报告两个不同的事件：START_ELEMENT,END_ELEMENT  - 这会将空元素的解析等效保留为&lt; tag&标签>
   * 
   *  如果在hasNext()返回false之后调用,此方法将抛出IllegalStateException
   * 
   * 
   * @see javax.xml.stream.events.XMLEvent
   * @return the integer code corresponding to the current parse event
   * @throws NoSuchElementException if this is called when hasNext() returns false
   * @throws XMLStreamException  if there is an error processing the underlying XML source
   */
  public int next() throws XMLStreamException;

  /**
   * Test if the current event is of the given type and if the namespace and name match the current
   * namespace and name of the current event.  If the namespaceURI is null it is not checked for equality,
   * if the localName is null it is not checked for equality.
   * <p>
   *  测试当前事件是否为给定类型,以及命名空间和名称是否与当前事件的当前命名空间和名称匹配如果namespaceURI为null,则不检查其是否相等,如果localName为null,则不检查其是否相等
   * 
   * 
   * @param type the event type
   * @param namespaceURI the uri of the event, may be null
   * @param localName the localName of the event, may be null
   * @throws XMLStreamException if the required values are not matched.
   */
  public void require(int type, String namespaceURI, String localName) throws XMLStreamException;

  /**
   * Reads the content of a text-only element, an exception is thrown if this is
   * not a text-only element.
   * Regardless of value of javax.xml.stream.isCoalescing this method always returns coalesced content.
   * <br /> Precondition: the current event is START_ELEMENT.
   * <br /> Postcondition: the current event is the corresponding END_ELEMENT.
   *
   * <br />The method does the following (implementations are free to optimized
   * but must do equivalent processing):
   * <pre>
   * if(getEventType() != XMLStreamConstants.START_ELEMENT) {
   * throw new XMLStreamException(
   * "parser must be on START_ELEMENT to read next text", getLocation());
   * }
   * int eventType = next();
   * StringBuffer content = new StringBuffer();
   * while(eventType != XMLStreamConstants.END_ELEMENT ) {
   * if(eventType == XMLStreamConstants.CHARACTERS
   * || eventType == XMLStreamConstants.CDATA
   * || eventType == XMLStreamConstants.SPACE
   * || eventType == XMLStreamConstants.ENTITY_REFERENCE) {
   * buf.append(getText());
   * } else if(eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
   * || eventType == XMLStreamConstants.COMMENT) {
   * // skipping
   * } else if(eventType == XMLStreamConstants.END_DOCUMENT) {
   * throw new XMLStreamException(
   * "unexpected end of document when reading element text content", this);
   * } else if(eventType == XMLStreamConstants.START_ELEMENT) {
   * throw new XMLStreamException(
   * "element text content may not contain START_ELEMENT", getLocation());
   * } else {
   * throw new XMLStreamException(
   * "Unexpected event type "+eventType, getLocation());
   * }
   * eventType = next();
   * }
   * return buf.toString();
   * </pre>
   *
   * <p>
   * 读取纯文本元素的内容,如果这不是纯文本元素,则抛出异常不管javaxxmlstreamisCoalescing的值是什么,此方法总是返回合并内容<br />前提条件：当前事件为START_ELEMENT
   *  <br /> Postcondition ：当前事件是对应的END_ELEMENT。
   * 
   *  <br />该方法执行以下操作(实现可以自由优化,但必须进行等效处理)：
   * <pre>
   * if(getEventType()！= XMLStreamConstantsSTART_ELEMENT){throw new XMLStreamException("parser must be on START_ELEMENT to read next text",getLocation()); }
   *  int eventType = next(); StringBuffer content = new StringBuffer(); while(eventType！= XMLStreamConsta
   * ntsEND_ELEMENT){if(eventType == XMLStreamConstantsCHARACTERS || eventType == XMLStreamConstantsCDATA || eventType == XMLStreamConstantsSPACE || eventType == XMLStreamConstantsENTITY_REFERENCE){bufappend(getText()); }
   *  else if(eventType == XMLStreamConstantsPROCESSING_INSTRUCTION || eventType == XMLStreamConstantsCOMM
   * ENT){// skipping} else if(eventType == XMLStreamConstants END_DOCUMENT){throw new XMLStreamException("reading end of document when reading element text content",this); }
   *  else if(eventType == XMLStreamConstantsSTART_ELEMENT){throw new XMLStreamException("element text content may not contain START_ELEMENT",getLocation()); }
   *  else {throw new XMLStreamException("Unexpected event type"+ eventType,getLocation()); } eventType = 
   * next(); } return buftoString();。
   * </pre>
   * 
   * 
   * @throws XMLStreamException if the current event is not a START_ELEMENT
   * or if a non text element is encountered
   */
  public String getElementText() throws XMLStreamException;

  /**
   * Skips any white space (isWhiteSpace() returns true), COMMENT,
   * or PROCESSING_INSTRUCTION,
   * until a START_ELEMENT or END_ELEMENT is reached.
   * If other than white space characters, COMMENT, PROCESSING_INSTRUCTION, START_ELEMENT, END_ELEMENT
   * are encountered, an exception is thrown. This method should
   * be used when processing element-only content seperated by white space.
   *
   * <br /> Precondition: none
   * <br /> Postcondition: the current event is START_ELEMENT or END_ELEMENT
   * and cursor may have moved over any whitespace event.
   *
   * <br />Essentially it does the following (implementations are free to optimized
   * but must do equivalent processing):
   * <pre>
   * int eventType = next();
   * while((eventType == XMLStreamConstants.CHARACTERS &amp;&amp; isWhiteSpace()) // skip whitespace
   * || (eventType == XMLStreamConstants.CDATA &amp;&amp; isWhiteSpace())
   * // skip whitespace
   * || eventType == XMLStreamConstants.SPACE
   * || eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
   * || eventType == XMLStreamConstants.COMMENT
   * ) {
   * eventType = next();
   * }
   * if (eventType != XMLStreamConstants.START_ELEMENT &amp;&amp; eventType != XMLStreamConstants.END_ELEMENT) {
   * throw new String XMLStreamException("expected start or end tag", getLocation());
   * }
   * return eventType;
   * </pre>
   *
   * <p>
   * 跳过任何空格(isWhiteSpace()返回true),COMMENT或PROCESSING_INSTRUCTION,直到达到START_ELEMENT或END_ELEMENT如果遇到空格字符COMM
   * ENT,PROCESSING_INSTRUCTION,START_ELEMENT,END_ELEMENT,则抛出异常此方法应该使用当处理由空白分隔的仅元素内容时。
   * 
   *  <br />前提条件：无<br /> Postcondition：当前事件为START_ELEMENT或END_ELEMENT,并且光标可能已移过任何空格事件
   * 
   *  <br />本质上它做以下(实现可以自由优化,但必须做等效处理)：
   * <pre>
   * int eventType = next(); while((eventType == XMLStreamConstantsCHARACTERS&amp; isWhiteSpace())// skip 
   * whitespace ||(eventType == XMLStreamConstantsCDATA&amp;&amp; isWhiteSpace())// skip whitespace || eve
   * ntType == XMLStreamConstantsSPACE || eventType == XMLStreamConstantsPROCESSING_INSTRUCTION || eventTy
   * pe == XMLStreamConstantsCOMMENT){eventType = next(); } if(eventType！= XMLStreamConstantsSTART_ELEMENT
   * &amp;&amp;&amp; eventType！= XMLStreamConstantsEND_ELEMENT){throw new String XMLStreamException("expected start或end tag",getLocation()); }
   *  return eventType;。
   * </pre>
   * 
   * 
   * @return the event type of the element read (START_ELEMENT or END_ELEMENT)
   * @throws XMLStreamException if the current event is not white space, PROCESSING_INSTRUCTION,
   * START_ELEMENT or END_ELEMENT
   * @throws NoSuchElementException if this is called when hasNext() returns false
   */
  public int nextTag() throws XMLStreamException;

  /**
   * Returns true if there are more parsing events and false
   * if there are no more events.  This method will return
   * false if the current state of the XMLStreamReader is
   * END_DOCUMENT
   * <p>
   *  如果有更多的解析事件则返回true,如果没有更多事件则返回false如果XMLStreamReader的当前状态为END_DOCUMENT,此方法将返回false
   * 
   * 
   * @return true if there are more events, false otherwise
   * @throws XMLStreamException if there is a fatal error detecting the next state
   */
  public boolean hasNext() throws XMLStreamException;

  /**
   * Frees any resources associated with this Reader.  This method does not close the
   * underlying input source.
   * <p>
   * 释放与此读取器关联的任何资源此方法不会关闭底层输入源
   * 
   * 
   * @throws XMLStreamException if there are errors freeing associated resources
   */
  public void close() throws XMLStreamException;

  /**
   * Return the uri for the given prefix.
   * The uri returned depends on the current state of the processor.
   *
   * <p><strong>NOTE:</strong>The 'xml' prefix is bound as defined in
   * <a href="http://www.w3.org/TR/REC-xml-names/#ns-using">Namespaces in XML</a>
   * specification to "http://www.w3.org/XML/1998/namespace".
   *
   * <p><strong>NOTE:</strong> The 'xmlns' prefix must be resolved to following namespace
   * <a href="http://www.w3.org/2000/xmlns/">http://www.w3.org/2000/xmlns/</a>
   * <p>
   *  返回给定前缀的uri返回的uri取决于处理器的当前状态
   * 
   *  <p> <strong>注意</strong>：</strong>"xml"前缀的定义如<a href=\"http://wwww3org/TR/REC-xml-names/#ns-using\"> 
   * XML中的命名空间</a>规范改为"http：// wwww3org / XML / 1998 / namespace"。
   * 
   *  <p> <strong>注意</strong>：</strong>"xmlns"前缀必须解析为以下名称空间<a href=\"http://wwww3org/2000/xmlns/\"> http：/
   * / wwww3org / 2000 / xmlns / </a>。
   * 
   * 
   * @param prefix The prefix to lookup, may not be null
   * @return the uri bound to the given prefix or null if it is not bound
   * @throws IllegalArgumentException if the prefix is null
   */
  public String getNamespaceURI(String prefix);

  /**
   * Returns true if the cursor points to a start tag (otherwise false)
   * <p>
   *  如果光标指向开始标签,则返回true(否则为false)
   * 
   * 
   * @return true if the cursor points to a start tag, false otherwise
   */
  public boolean isStartElement();

  /**
   * Returns true if the cursor points to an end tag (otherwise false)
   * <p>
   *  如果光标指向结束标记,则返回true(否则为false)
   * 
   * 
   * @return true if the cursor points to an end tag, false otherwise
   */
  public boolean isEndElement();

  /**
   * Returns true if the cursor points to a character data event
   * <p>
   *  如果游标指向字符数据事件,则返回true
   * 
   * 
   * @return true if the cursor points to character data, false otherwise
   */
  public boolean isCharacters();

  /**
   * Returns true if the cursor points to a character data event
   * that consists of all whitespace
   * <p>
   * 如果光标指向由所有空格组成的字符数据事件,则返回true
   * 
   * 
   * @return true if the cursor points to all whitespace, false otherwise
   */
  public boolean isWhiteSpace();


  /**
   * Returns the normalized attribute value of the
   * attribute with the namespace and localName
   * If the namespaceURI is null the namespace
   * is not checked for equality
   * <p>
   *  返回带有名称空间和localName的属性的规范化属性值如果namespaceURI为null,则不会检查命名空间是否相等
   * 
   * 
   * @param namespaceURI the namespace of the attribute
   * @param localName the local name of the attribute, cannot be null
   * @return returns the value of the attribute , returns null if not found
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributeValue(String namespaceURI,
                                  String localName);

  /**
   * Returns the count of attributes on this START_ELEMENT,
   * this method is only valid on a START_ELEMENT or ATTRIBUTE.  This
   * count excludes namespace definitions.  Attribute indices are
   * zero-based.
   * <p>
   *  返回此START_ELEMENT上的属性计数,此方法仅在START_ELEMENT或ATTRIBUTE上有效此计数不包括命名空间定义属性索引为零
   * 
   * 
   * @return returns the number of attributes
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public int getAttributeCount();

  /** Returns the qname of the attribute at the provided index
   *
   * <p>
   * 
   * @param index the position of the attribute
   * @return the QName of the attribute
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public QName getAttributeName(int index);

  /**
   * Returns the namespace of the attribute at the provided
   * index
   * <p>
   *  返回提供的索引处的属性的命名空间
   * 
   * 
   * @param index the position of the attribute
   * @return the namespace URI (can be null)
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributeNamespace(int index);

  /**
   * Returns the localName of the attribute at the provided
   * index
   * <p>
   *  返回所提供索引处的属性的localName
   * 
   * 
   * @param index the position of the attribute
   * @return the localName of the attribute
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributeLocalName(int index);

  /**
   * Returns the prefix of this attribute at the
   * provided index
   * <p>
   *  在提供的索引处返回此属性的前缀
   * 
   * 
   * @param index the position of the attribute
   * @return the prefix of the attribute
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributePrefix(int index);

  /**
   * Returns the XML type of the attribute at the provided
   * index
   * <p>
   *  返回所提供索引处的属性的XML类型
   * 
   * 
   * @param index the position of the attribute
   * @return the XML type of the attribute
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributeType(int index);

  /**
   * Returns the value of the attribute at the
   * index
   * <p>
   *  返回索引处属性的值
   * 
   * 
   * @param index the position of the attribute
   * @return the attribute value
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public String getAttributeValue(int index);

  /**
   * Returns a boolean which indicates if this
   * attribute was created by default
   * <p>
   * 返回一个布尔值,指示此属性是默认创建的
   * 
   * 
   * @param index the position of the attribute
   * @return true if this is a default attribute
   * @throws IllegalStateException if this is not a START_ELEMENT or ATTRIBUTE
   */
  public boolean isAttributeSpecified(int index);

  /**
   * Returns the count of namespaces declared on this START_ELEMENT or END_ELEMENT,
   * this method is only valid on a START_ELEMENT, END_ELEMENT or NAMESPACE. On
   * an END_ELEMENT the count is of the namespaces that are about to go
   * out of scope.  This is the equivalent of the information reported
   * by SAX callback for an end element event.
   * <p>
   *  返回在此START_ELEMENT或END_ELEMENT上声明的命名空间的计数,此方法仅对START_ELEMENT,END_ELEMENT或NAMESPACE有效END_ELEMENT命名空间的计
   * 数超出范围这是相当于报告的信息结束元素事件的SAX回调。
   * 
   * 
   * @return returns the number of namespace declarations on this specific element
   * @throws IllegalStateException if this is not a START_ELEMENT, END_ELEMENT or NAMESPACE
   */
  public int getNamespaceCount();

  /**
   * Returns the prefix for the namespace declared at the
   * index.  Returns null if this is the default namespace
   * declaration
   *
   * <p>
   *  返回在索引处声明的命名空间的前缀如果这是默认的命名空间声明,则返回null
   * 
   * 
   * @param index the position of the namespace declaration
   * @return returns the namespace prefix
   * @throws IllegalStateException if this is not a START_ELEMENT, END_ELEMENT or NAMESPACE
   */
  public String getNamespacePrefix(int index);

  /**
   * Returns the uri for the namespace declared at the
   * index.
   *
   * <p>
   *  返回在索引处声明的命名空间的uri
   * 
   * 
   * @param index the position of the namespace declaration
   * @return returns the namespace uri
   * @throws IllegalStateException if this is not a START_ELEMENT, END_ELEMENT or NAMESPACE
   */
  public String getNamespaceURI(int index);

  /**
   * Returns a read only namespace context for the current
   * position.  The context is transient and only valid until
   * a call to next() changes the state of the reader.
   * <p>
   *  返回当前位置的只读命名空间上下文上下文是瞬时的,只有在调用next()更改读取器的状态时才有效
   * 
   * 
   * @return return a namespace context
   */
  public NamespaceContext getNamespaceContext();

  /**
   * Returns a reader that points to the current start element
   * and all of its contents.  Throws an XMLStreamException if the
   * cursor does not point to a START_ELEMENT.<p>
   * The sub stream is read from it MUST be read before the parent stream is
   * moved on, if not any call on the sub stream will cause an XMLStreamException to be
   * thrown.   The parent stream will always return the same result from next()
   * whatever is done to the sub stream.
   * <p>
   * 返回指向当前开始元素及其所有内容的读取器如果游标未指向START_ELEMENT <p>,则抛出XMLStreamException。
   * 读取子流必须在父流移动之前读取,如果没有对子流的任何调用都将导致抛出XMLStreamException父流将始终从next()返回相同的结果,无论对子流做什么。
   * 
   * 
   * @return an XMLStreamReader which points to the next element
   */
  //  public XMLStreamReader subReader() throws XMLStreamException;

  /**
   * Allows the implementation to reset and reuse any underlying tables
   * <p>
   *  允许实现重置和重用任何基础表
   * 
   */
  //  public void recycle() throws XMLStreamException;

  /**
   * Returns an integer code that indicates the type
   * of the event the cursor is pointing to.
   * <p>
   *  返回一个整数代码,指示光标所指向的事件的类型
   * 
   */
  public int getEventType();

  /**
   * Returns the current value of the parse event as a string,
   * this returns the string value of a CHARACTERS event,
   * returns the value of a COMMENT, the replacement value
   * for an ENTITY_REFERENCE, the string value of a CDATA section,
   * the string value for a SPACE event,
   * or the String value of the internal subset of the DTD.
   * If an ENTITY_REFERENCE has been resolved, any character data
   * will be reported as CHARACTERS events.
   * <p>
   * 以字符串形式返回解析事件的当前值,返回CHARACTERS事件的字符串值,返回COMMENT的值,ENTITY_REFERENCE的替换值,CDATA段的字符串值,SPACE的字符串值事件或DTD的内部
   * 子集的String值如果ENTITY_REFERENCE已解决,则任何字符数据都将报告为CHARACTERS事件。
   * 
   * 
   * @return the current text or null
   * @throws java.lang.IllegalStateException if this state is not
   * a valid text state.
   */
  public String getText();

  /**
   * Returns an array which contains the characters from this event.
   * This array should be treated as read-only and transient. I.e. the array will
   * contain the text characters until the XMLStreamReader moves on to the next event.
   * Attempts to hold onto the character array beyond that time or modify the
   * contents of the array are breaches of the contract for this interface.
   * <p>
   *  返回包含此事件中的字符的数组此数组应被视为只读和瞬态数组将包含文本字符,直到XMLStreamReader移动到下一个事件尝试保存到超出该时间的字符数组或修改数组的内容违反了此接口的合同
   * 
   * 
   * @return the current text or an empty array
   * @throws java.lang.IllegalStateException if this state is not
   * a valid text state.
   */
  public char[] getTextCharacters();

  /**
   * Gets the the text associated with a CHARACTERS, SPACE or CDATA event.
   * Text starting a "sourceStart" is copied into "target" starting at "targetStart".
   * Up to "length" characters are copied.  The number of characters actually copied is returned.
   *
   * The "sourceStart" argument must be greater or equal to 0 and less than or equal to
   * the number of characters associated with the event.  Usually, one requests text starting at a "sourceStart" of 0.
   * If the number of characters actually copied is less than the "length", then there is no more text.
   * Otherwise, subsequent calls need to be made until all text has been retrieved. For example:
   *
   *<code>
   * int length = 1024;
   * char[] myBuffer = new char[ length ];
   *
   * for ( int sourceStart = 0 ; ; sourceStart += length )
   * {
   *    int nCopied = stream.getTextCharacters( sourceStart, myBuffer, 0, length );
   *
   *   if (nCopied < length)
   *       break;
   * }
   * </code>
   * XMLStreamException may be thrown if there are any XML errors in the underlying source.
   * The "targetStart" argument must be greater than or equal to 0 and less than the length of "target",
   * Length must be greater than 0 and "targetStart + length" must be less than or equal to length of "target".
   *
   * <p>
   * 获取与CHARACTERS,SPACE或CDATA事件相关联的文本从"targetStart"开始将开始"sourceStart"的文本复制到"target"中直到复制"length"个字符。
   * 返回实际复制的字符数。
   * 
   *  "sourceStart"参数必须大于或等于0且小于或等于与事件相关联的字符数通常,一个请求从"sourceStart"为0开始的文本如果实际复制的字符数小于"length",则没有更多的文本否则,需
   * 要进行后续调用,直到检索到所有文本例如：。
   * 
   * code>
   *  int length = 1024; char [] myBuffer = new char [length];
   * 
   * for(int sourceStart = 0;; sourceStart + = length){int nCopied = streamgetTextCharacters(sourceStart,myBuffer,0,length);。
   * 
   *  if(nCopied <length)break; }}
   * </code>
   *  如果基础源中存在任何XML错误,则可能抛出XMLStreamException"targetStart"参数必须大于或等于0且小于"target"的长度,长度必须大于0,且"targetStart +
   *  length"必须小于或等于"目标"的长度。
   * 
   * 
   * @param sourceStart the index of the first character in the source array to copy
   * @param target the destination array
   * @param targetStart the start offset in the target array
   * @param length the number of characters to copy
   * @return the number of characters actually copied
   * @throws XMLStreamException if the underlying XML source is not well-formed
   * @throws IndexOutOfBoundsException if targetStart < 0 or > than the length of target
   * @throws IndexOutOfBoundsException if length < 0 or targetStart + length > length of target
   * @throws UnsupportedOperationException if this method is not supported
   * @throws NullPointerException is if target is null
   */
   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length)
     throws XMLStreamException;

  /**
   * Gets the text associated with a CHARACTERS, SPACE or CDATA event.  Allows the underlying
   * implementation to return the text as a stream of characters.  The reference to the
   * Reader returned by this method is only valid until next() is called.
   *
   * All characters must have been checked for well-formedness.
   *
   * <p> This method is optional and will throw UnsupportedOperationException if it is not supported.
   * <p>
   *  获取与CHARACTERS,SPACE或CDATA事件相关联的文本允许底层实现以文本流返回文本对此方法返回的Reader的引用仅在调用next()之前有效
   * 
   *  所有字符必须已检查良好的形状
   * 
   * <p>此方法是可选的,如果不支持,将抛出UnsupportedOperationException
   * 
   * 
   * @throws UnsupportedOperationException if this method is not supported
   * @throws IllegalStateException if this is not a valid text state
   */
  //public Reader getTextStream();

  /**
   * Returns the offset into the text character array where the first
   * character (of this text event) is stored.
   * <p>
   *  返回存储文本字符数组的第一个字符(此文本事件的第一个字符)的偏移量
   * 
   * 
   * @throws java.lang.IllegalStateException if this state is not
   * a valid text state.
   */
  public int getTextStart();

  /**
   * Returns the length of the sequence of characters for this
   * Text event within the text character array.
   * <p>
   *  返回文本字符数组中此Text事件的字符序列的长度
   * 
   * 
   * @throws java.lang.IllegalStateException if this state is not
   * a valid text state.
   */
  public int getTextLength();

  /**
   * Return input encoding if known or null if unknown.
   * <p>
   *  返回输入编码如果已知或null如果未知
   * 
   * 
   * @return the encoding of this instance or null
   */
  public String getEncoding();

  /**
   * Return true if the current event has text, false otherwise
   * The following events have text:
   * CHARACTERS,DTD ,ENTITY_REFERENCE, COMMENT, SPACE
   * <p>
   *  如果当前事件有文本,则返回true,否则返回false以下事件具有文本：CHARACTERS,DTD,ENTITY_REFERENCE,COMMENT,SPACE
   * 
   */
  public boolean hasText();

  /**
   * Return the current location of the processor.
   * If the Location is unknown the processor should return
   * an implementation of Location that returns -1 for the
   * location and null for the publicId and systemId.
   * The location information is only valid until next() is
   * called.
   * <p>
   *  返回处理器的当前位置如果位置未知,则处理器应返回位置的实现,该位置对于位置返回-1,对于publicId和systemId返回null。位置信息仅在调用next()之前有效
   * 
   */
  public Location getLocation();

  /**
   * Returns a QName for the current START_ELEMENT or END_ELEMENT event
   * <p>
   * 返回当前START_ELEMENT或END_ELEMENT事件的QName
   * 
   * 
   * @return the QName for the current START_ELEMENT or END_ELEMENT event
   * @throws IllegalStateException if this is not a START_ELEMENT or
   * END_ELEMENT
   */
  public QName getName();

  /**
   * Returns the (local) name of the current event.
   * For START_ELEMENT or END_ELEMENT returns the (local) name of the current element.
   * For ENTITY_REFERENCE it returns entity name.
   * The current event must be START_ELEMENT or END_ELEMENT,
   * or ENTITY_REFERENCE
   * <p>
   *  返回当前事件的(本地)名称对于START_ELEMENT或END_ELEMENT返回当前元素的(本地)名称对于ENTITY_REFERENCE,它返回实体名称当前事件必须为START_ELEMENT或
   * END_ELEMENT,或ENTITY_REFERENCE。
   * 
   * 
   * @return the localName
   * @throws IllegalStateException if this not a START_ELEMENT,
   * END_ELEMENT or ENTITY_REFERENCE
   */
  public String getLocalName();

  /**
   * returns true if the current event has a name (is a START_ELEMENT or END_ELEMENT)
   * returns false otherwise
   * <p>
   *  如果当前事件有名称(为START_ELEMENT或END_ELEMENT),则返回true,否则返回false
   * 
   */
  public boolean hasName();

  /**
   * If the current event is a START_ELEMENT or END_ELEMENT  this method
   * returns the URI of the prefix or the default namespace.
   * Returns null if the event does not have a prefix.
   * <p>
   *  如果当前事件是START_ELEMENT或END_ELEMENT,则此方法返回前缀或默认名称空间的URI如果事件没有前缀,则返回null
   * 
   * 
   * @return the URI bound to this elements prefix, the default namespace, or null
   */
  public String getNamespaceURI();

  /**
   * Returns the prefix of the current event or null if the event does not have a prefix
   * <p>
   *  返回当前事件的前缀,如果事件没有前缀,则返回null
   * 
   * 
   * @return the prefix or null
   */
  public String getPrefix();

  /**
   * Get the xml version declared on the xml declaration
   * Returns null if none was declared
   * <p>
   *  获取在xml声明上声明的xml版本如果没有声明,返回null
   * 
   * 
   * @return the XML version or null
   */
  public String getVersion();

  /**
   * Get the standalone declaration from the xml declaration
   * <p>
   * 从xml声明获取独立声明
   * 
   * 
   * @return true if this is standalone, or false otherwise
   */
  public boolean isStandalone();

  /**
   * Checks if standalone was set in the document
   * <p>
   *  检查文档中是否设置了独立
   * 
   * 
   * @return true if standalone was set in the document, or false otherwise
   */
  public boolean standaloneSet();

  /**
   * Returns the character encoding declared on the xml declaration
   * Returns null if none was declared
   * <p>
   *  返回在xml声明上声明的字符编码如果没有声明,则返回null
   * 
   * 
   * @return the encoding declared in the document or null
   */
  public String getCharacterEncodingScheme();

  /**
   * Get the target of a processing instruction
   * <p>
   *  获取处理指令的目标
   * 
   * 
   * @return the target or null
   */
  public String getPITarget();

  /**
   * Get the data section of a processing instruction
   * <p>
   *  获取处理指令的数据部分
   * 
   * @return the data or null
   */
  public String getPIData();
}
