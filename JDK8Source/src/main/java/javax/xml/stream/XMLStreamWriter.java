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

import javax.xml.namespace.NamespaceContext;

/**
 * The XMLStreamWriter interface specifies how to write XML.  The XMLStreamWriter  does
 * not perform well formedness checking on its input.  However
 * the writeCharacters method is required to escape &amp; , &lt; and &gt;
 * For attribute values the writeAttribute method will escape the
 * above characters plus &quot; to ensure that all character content
 * and attribute values are well formed.
 *
 * Each NAMESPACE
 * and ATTRIBUTE must be individually written.
 *
 * <table border="1" cellpadding="2" cellspacing="0">
 *     <thead>
 *         <tr>
 *             <th colspan="5">XML Namespaces, <code>javax.xml.stream.isRepairingNamespaces</code> and write method behaviour</th>
 *         </tr>
 *         <tr>
 *             <th>Method</th> <!-- method -->
 *             <th colspan="2"><code>isRepairingNamespaces</code> == true</th>
 *             <th colspan="2"><code>isRepairingNamespaces</code> == false</th>
 *         </tr>
 *         <tr>
 *             <th></th> <!-- method -->
 *             <th>namespaceURI bound</th>
 *             <th>namespaceURI unbound</th>
 *             <th>namespaceURI bound</th>
 *             <th>namespaceURI unbound</th>
 *         </tr>
 *     </thead>
 *
 *     <tbody>
 *         <tr>
 *             <th><code>writeAttribute(namespaceURI, localName, value)</code></th>
 *             <!-- isRepairingNamespaces == true -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 prefix:localName="value"&nbsp;<sup>[1]</sup>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 xmlns:{generated}="namespaceURI" {generated}:localName="value"
 *             </td>
 *             <!-- isRepairingNamespaces == false -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 prefix:localName="value"&nbsp;<sup>[1]</sup>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 <code>XMLStreamException</code>
 *             </td>
 *         </tr>
 *
 *         <tr>
 *             <th><code>writeAttribute(prefix, namespaceURI, localName, value)</code></th>
 *             <!-- isRepairingNamespaces == true -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 bound to same prefix:<br />
 *                 prefix:localName="value"&nbsp;<sup>[1]</sup><br />
 *                 <br />
 *                 bound to different prefix:<br />
 *                 xmlns:{generated}="namespaceURI" {generated}:localName="value"
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 xmlns:prefix="namespaceURI" prefix:localName="value"&nbsp;<sup>[3]</sup>
 *             </td>
 *             <!-- isRepairingNamespaces == false -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 bound to same prefix:<br />
 *                 prefix:localName="value"&nbsp;<sup>[1][2]</sup><br />
 *                 <br />
 *                 bound to different prefix:<br />
 *                 <code>XMLStreamException</code><sup>[2]</sup>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 xmlns:prefix="namespaceURI" prefix:localName="value"&nbsp;<sup>[2][5]</sup>
 *             </td>
 *         </tr>
 *
 *         <tr>
 *             <th><code>writeStartElement(namespaceURI, localName)</code><br />
 *                 <br />
 *                 <code>writeEmptyElement(namespaceURI, localName)</code></th>
 *             <!-- isRepairingNamespaces == true -->
 *             <td >
 *                 <!-- namespaceURI bound -->
 *                 &lt;prefix:localName&gt;&nbsp;<sup>[1]</sup>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 &lt;{generated}:localName xmlns:{generated}="namespaceURI"&gt;
 *             </td>
 *             <!-- isRepairingNamespaces == false -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 &lt;prefix:localName&gt;&nbsp;<sup>[1]</sup>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 <code>XMLStreamException</code>
 *             </td>
 *         </tr>
 *
 *         <tr>
 *             <th><code>writeStartElement(prefix, localName, namespaceURI)</code><br />
 *                 <br />
 *                 <code>writeEmptyElement(prefix, localName, namespaceURI)</code></th>
 *             <!-- isRepairingNamespaces == true -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 bound to same prefix:<br />
 *                 &lt;prefix:localName&gt;&nbsp;<sup>[1]</sup><br />
 *                 <br />
 *                 bound to different prefix:<br />
 *                 &lt;{generated}:localName xmlns:{generated}="namespaceURI"&gt;
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 &lt;prefix:localName xmlns:prefix="namespaceURI"&gt;&nbsp;<sup>[4]</sup>
 *             </td>
 *             <!-- isRepairingNamespaces == false -->
 *             <td>
 *                 <!-- namespaceURI bound -->
 *                 bound to same prefix:<br />
 *                 &lt;prefix:localName&gt;&nbsp;<sup>[1]</sup><br />
 *                 <br />
 *                 bound to different prefix:<br />
 *                 <code>XMLStreamException</code>
 *             </td>
 *             <td>
 *                 <!-- namespaceURI unbound -->
 *                 &lt;prefix:localName&gt;&nbsp;
 *             </td>
 *         </tr>
 *     </tbody>
 *     <tfoot>
 *         <tr>
 *             <td colspan="5">
 *                 Notes:
 *                 <ul>
 *                     <li>[1] if namespaceURI == default Namespace URI, then no prefix is written</li>
 *                     <li>[2] if prefix == "" || null && namespaceURI == "", then no prefix or Namespace declaration is generated or written</li>
 *                     <li>[3] if prefix == "" || null, then a prefix is randomly generated</li>
 *                     <li>[4] if prefix == "" || null, then it is treated as the default Namespace and no prefix is generated or written, an xmlns declaration is generated and written if the namespaceURI is unbound</li>
 *                     <li>[5] if prefix == "" || null, then it is treated as an invalid attempt to define the default Namespace and an XMLStreamException is thrown</li>
 *                 </ul>
 *             </td>
 *         </tr>
 *     </tfoot>
 * </table>
 *
 * <p>
 *  XMLStreamWriter接口指定如何写XML XMLStreamWriter不对其输入执行良好的形成检查但是writeCharacters方法是必需的, ,1.95和&gt;对于属性值,writ
 * eAttribute方法将转义上述字符加"以确保所有字符内容和属性值形成良好。
 * 
 *  每个NAMESPACE和ATTRIBUTE必须单独写入
 * 
 * <table border="1" cellpadding="2" cellspacing="0">
 * <thead>
 * <tr>
 *  <th colspan ="5"> XML命名空间,<code> javaxxmlstreamisRepairingNamespaces </code>和写入方法行为</th>
 * </tr>
 * <tr>
 * <th>方法</th> <！-method  - > <th colspan ="2"> <code> isRepairingNamespaces </code> == true </th> <th colspan ="2">
 *  <code> isRepairingNamespaces </code> == false </th>。
 * </tr>
 * <tr>
 *  <th> </th> </ - > </span> </span> </span> </span> </span> >
 * </tr>
 * </thead>
 * 
 * <tbody>
 * <tr>
 *  <th> <code> writeAttribute(namespaceURI,localName,value)</code> </th>
 * <!-- isRepairingNamespaces == true -->
 * <td>
 * <!-- namespaceURI bound -->
 *  prefix：localName ="value"&nbsp; <sup> [1] </sup>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  xmlns：{generated} ="namespaceURI"{generated}：localName ="value"
 * </td>
 * <!-- isRepairingNamespaces == false -->
 * <td>
 * <!-- namespaceURI bound -->
 *  prefix：localName ="value"&nbsp; <sup> [1] </sup>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  <code> XMLStreamException </code>
 * </td>
 * </tr>
 * 
 * <tr>
 *  <th> <code> writeAttribute(prefix,namespaceURI,localName,value)</code> </th>
 * <!-- isRepairingNamespaces == true -->
 * <td>
 * <!-- namespaceURI bound -->
 *  绑定到相同的前缀：<br /> prefix：localName ="value"&nbsp; <sup> [1] </sup> <br />
 * <br />
 * 绑定到不同的前缀：<br /> xmlns：{generated} ="namespaceURI"{generated}：localName ="value"
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  xmlns：prefix ="namespaceURI"prefix：localName ="value"&nbsp; <sup> [3] </sup>
 * </td>
 * <!-- isRepairingNamespaces == false -->
 * <td>
 * <!-- namespaceURI bound -->
 *  绑定到相同的前缀：<br /> prefix：localName ="value"&nbsp; <sup> [1] [2] </sup> <br />
 * <br />
 *  绑定到不同的前缀：<br /> <code> XMLStreamException </code> <sup> [2] </sup>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  xmlns：prefix ="namespaceURI"prefix：localName ="value"&nbsp; <sup> [2] [5] </sup>
 * </td>
 * </tr>
 * 
 * <tr>
 *  <th> <code> writeStartElement(namespaceURI,localName)</code> <br />
 * <br />
 *  <code> writeEmptyElement(namespaceURI,localName)</code> </th>
 * <!-- isRepairingNamespaces == true -->
 * <td >
 * <!-- namespaceURI bound -->
 *  &lt; prefix：localName&gt;&nbsp; <sup> [1] </sup>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  &lt; {generated}：localName xmlns：{generated} ="namespaceURI"&gt;
 * </td>
 * <!-- isRepairingNamespaces == false -->
 * <td>
 * <!-- namespaceURI bound -->
 *  &lt; prefix：localName&gt;&nbsp; <sup> [1] </sup>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  <code> XMLStreamException </code>
 * </td>
 * </tr>
 * 
 * <tr>
 * <th> <code> writeStartElement(prefix,localName,namespaceURI)</code> <br />
 * <br />
 *  <code> writeEmptyElement(prefix,localName,namespaceURI)</code> </th>
 * <!-- isRepairingNamespaces == true -->
 * <td>
 * <!-- namespaceURI bound -->
 *  绑定到同一个前缀：<br />&lt; prefix：localName&gt;&nbsp; <sup> [1] </sup> <br />
 * <br />
 *  绑定到不同的前缀：<br />&lt; {generated}：localName xmlns：{generated} ="namespaceURI"&gt;
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  &lt; prefix：localName xmlns：prefix ="namespaceURI"&gt;&nbsp; <sup> [4] </sup>
 * </td>
 * <!-- isRepairingNamespaces == false -->
 * <td>
 * <!-- namespaceURI bound -->
 *  绑定到相同的前缀：<br />&lt; prefix：localName&gt;&nbsp; <sup> [1] </sup> <br />
 * <br />
 *  绑定到不同的前缀：<br /> <code> XMLStreamException </code>
 * </td>
 * <td>
 * <!-- namespaceURI unbound -->
 *  &lt; prefix：localName&gt;&nbsp;
 * </td>
 * </tr>
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @see XMLOutputFactory
 * @see XMLStreamReader
 * @since 1.6
 */
public interface XMLStreamWriter {

  /**
   * Writes a start tag to the output.  All writeStartElement methods
   * open a new scope in the internal namespace context.  Writing the
   * corresponding EndElement causes the scope to be closed.
   * <p>
   * </tbody>
   * <tfoot>
   * <tr>
   * <td colspan="5">
   *  笔记：
   * <ul>
   * <li> [1] if namespaceURI == default Namespace URI,then not prefix is written </li> <li> [2] if prefix
   *  ==""|| null && namespaceURI =="",则不生成或写入前缀或命名空间声明</li> <li> [3] if prefix ==""|| null,则随机生成一个前缀</li>
   *  <li> [4] if prefix ==""||如果前缀==""|| null,那么它将被视为默认的命名空间,并且不生成或写入前缀,如果namespaceURI未绑定,则会生成并写入xmlns声明</li>
   *  null,则它被视为无效尝试定义默认命名空间,并抛出XMLStreamException </li>。
   * </ul>
   * </td>
   * </tr>
   * </tfoot>
   * </table>
   * 
   * 
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException
   */
  public void writeStartElement(String localName)
    throws XMLStreamException;

  /**
   * Writes a start tag to the output
   * <p>
   * 向输出中写入开始标记所有writeStartElement方法在内部命名空间上下文中打开一个新作用域编写相应的EndElement将导致作用域关闭
   * 
   * 
   * @param namespaceURI the namespaceURI of the prefix to use, may not be null
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeStartElement(String namespaceURI, String localName)
    throws XMLStreamException;

  /**
   * Writes a start tag to the output
   * <p>
   *  在输出中写入开始标签
   * 
   * 
   * @param localName local name of the tag, may not be null
   * @param prefix the prefix of the tag, may not be null
   * @param namespaceURI the uri to bind the prefix to, may not be null
   * @throws XMLStreamException
   */
  public void writeStartElement(String prefix,
                                String localName,
                                String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * <p>
   *  在输出中写入开始标签
   * 
   * 
   * @param namespaceURI the uri to bind the tag to, may not be null
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeEmptyElement(String namespaceURI, String localName)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * <p>
   *  向输出中写入空元素标记
   * 
   * 
   * @param prefix the prefix of the tag, may not be null
   * @param localName local name of the tag, may not be null
   * @param namespaceURI the uri to bind the tag to, may not be null
   * @throws XMLStreamException
   */
  public void writeEmptyElement(String prefix, String localName, String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an empty element tag to the output
   * <p>
   *  向输出中写入空元素标记
   * 
   * 
   * @param localName local name of the tag, may not be null
   * @throws XMLStreamException
   */
  public void writeEmptyElement(String localName)
    throws XMLStreamException;

  /**
   * Writes string data to the output without checking for well formedness.
   * The data is opaque to the XMLStreamWriter, i.e. the characters are written
   * blindly to the underlying output.  If the method cannot be supported
   * in the currrent writing context the implementation may throw a
   * UnsupportedOperationException.  For example note that any
   * namespace declarations, end tags, etc. will be ignored and could
   * interfere with proper maintanence of the writers internal state.
   *
   * <p>
   *  向输出中写入空元素标记
   * 
   * 
   * @param data the data to write
   */
  //  public void writeRaw(String data) throws XMLStreamException;

  /**
   * Writes an end tag to the output relying on the internal
   * state of the writer to determine the prefix and local name
   * of the event.
   * <p>
   * 将字符串数据写入输出,而不检查良好的形成数据对XMLStreamWriter是不透明的,即字符被盲目地写入底层输出如果方法不能在currrent写上下文中支持,实现可能会抛出UnsupportedOpe
   * rationException例如注意,任何命名空间声明,结束标签等将被忽略并且可能干扰写入者内部状态的适当维护。
   * 
   * 
   * @throws XMLStreamException
   */
  public void writeEndElement()
    throws XMLStreamException;

  /**
   * Closes any start tags and writes corresponding end tags.
   * <p>
   *  根据写入程序的内部状态将结束标记写入输出,以确定事件的前缀和本地名称
   * 
   * 
   * @throws XMLStreamException
   */
  public void writeEndDocument()
    throws XMLStreamException;

  /**
   * Close this writer and free any resources associated with the
   * writer.  This must not close the underlying output stream.
   * <p>
   *  关闭任何开始标签并写入相应的结束标签
   * 
   * 
   * @throws XMLStreamException
   */
  public void close()
    throws XMLStreamException;

  /**
   * Write any cached data to the underlying output mechanism.
   * <p>
   *  关闭此作者并释放与该作者相关联的任何资源这不得关闭底层输出流
   * 
   * 
   * @throws XMLStreamException
   */
  public void flush()
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream without
   * a prefix.
   * <p>
   * 将任何缓存的数据写入底层输出机制
   * 
   * 
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException
   */
  public void writeAttribute(String localName, String value)
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream
   * <p>
   *  将属性写入无前缀的输出流
   * 
   * 
   * @param prefix the prefix for this attribute
   * @param namespaceURI the uri of the prefix for this attribute
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * javax.xml.stream.isRepairingNamespaces has not been set to true
   */

  public void writeAttribute(String prefix,
                             String namespaceURI,
                             String localName,
                             String value)
    throws XMLStreamException;

  /**
   * Writes an attribute to the output stream
   * <p>
   *  将属性写入输出流
   * 
   * 
   * @param namespaceURI the uri of the prefix for this attribute
   * @param localName the local name of the attribute
   * @param value the value of the attribute
   * @throws IllegalStateException if the current state does not allow Attribute writing
   * @throws XMLStreamException if the namespace URI has not been bound to a prefix and
   * javax.xml.stream.isRepairingNamespaces has not been set to true
   */
  public void writeAttribute(String namespaceURI,
                             String localName,
                             String value)
    throws XMLStreamException;

  /**
   * Writes a namespace to the output stream
   * If the prefix argument to this method is the empty string,
   * "xmlns", or null this method will delegate to writeDefaultNamespace
   *
   * <p>
   *  将属性写入输出流
   * 
   * 
   * @param prefix the prefix to bind this namespace to
   * @param namespaceURI the uri to bind the prefix to
   * @throws IllegalStateException if the current state does not allow Namespace writing
   * @throws XMLStreamException
   */
  public void writeNamespace(String prefix, String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes the default namespace to the stream
   * <p>
   *  将命名空间写入输出流如果此方法的前缀参数为空字符串"xmlns"或null,则此方法将委托给writeDefaultNamespace
   * 
   * 
   * @param namespaceURI the uri to bind the default namespace to
   * @throws IllegalStateException if the current state does not allow Namespace writing
   * @throws XMLStreamException
   */
  public void writeDefaultNamespace(String namespaceURI)
    throws XMLStreamException;

  /**
   * Writes an xml comment with the data enclosed
   * <p>
   *  将默认命名空间写入流
   * 
   * 
   * @param data the data contained in the comment, may be null
   * @throws XMLStreamException
   */
  public void writeComment(String data)
    throws XMLStreamException;

  /**
   * Writes a processing instruction
   * <p>
   *  使用附带的数据写入xml注释
   * 
   * 
   * @param target the target of the processing instruction, may not be null
   * @throws XMLStreamException
   */
  public void writeProcessingInstruction(String target)
    throws XMLStreamException;

  /**
   * Writes a processing instruction
   * <p>
   *  写入处理指令
   * 
   * 
   * @param target the target of the processing instruction, may not be null
   * @param data the data contained in the processing instruction, may not be null
   * @throws XMLStreamException
   */
  public void writeProcessingInstruction(String target,
                                         String data)
    throws XMLStreamException;

  /**
   * Writes a CData section
   * <p>
   *  写入处理指令
   * 
   * 
   * @param data the data contained in the CData Section, may not be null
   * @throws XMLStreamException
   */
  public void writeCData(String data)
    throws XMLStreamException;

  /**
   * Write a DTD section.  This string represents the entire doctypedecl production
   * from the XML 1.0 specification.
   *
   * <p>
   *  写入CData节
   * 
   * 
   * @param dtd the DTD to be written
   * @throws XMLStreamException
   */
  public void writeDTD(String dtd)
    throws XMLStreamException;

  /**
   * Writes an entity reference
   * <p>
   *  编写DTD节此字符串表示从XML 10规范的整个doctypedecl生产
   * 
   * 
   * @param name the name of the entity
   * @throws XMLStreamException
   */
  public void writeEntityRef(String name)
    throws XMLStreamException;

  /**
   * Write the XML Declaration. Defaults the XML version to 1.0, and the encoding to utf-8
   * <p>
   *  写入实体引用
   * 
   * 
   * @throws XMLStreamException
   */
  public void writeStartDocument()
    throws XMLStreamException;

  /**
   * Write the XML Declaration. Defaults the XML version to 1.0
   * <p>
   *  编写XML声明将XML版本默认为10,并将编码设置为utf-8
   * 
   * 
   * @param version version of the xml document
   * @throws XMLStreamException
   */
  public void writeStartDocument(String version)
    throws XMLStreamException;

  /**
   * Write the XML Declaration.  Note that the encoding parameter does
   * not set the actual encoding of the underlying output.  That must
   * be set when the instance of the XMLStreamWriter is created using the
   * XMLOutputFactory
   * <p>
   * 编写XML声明将XML版本默认为10
   * 
   * 
   * @param encoding encoding of the xml declaration
   * @param version version of the xml document
   * @throws XMLStreamException If given encoding does not match encoding
   * of the underlying stream
   */
  public void writeStartDocument(String encoding,
                                 String version)
    throws XMLStreamException;

  /**
   * Write text to the output
   * <p>
   *  编写XML声明请注意,encoding参数不会设置底层输出的实际编码必须在使用XMLOutputFactory创建XMLStreamWriter实例时设置
   * 
   * 
   * @param text the value to write
   * @throws XMLStreamException
   */
  public void writeCharacters(String text)
    throws XMLStreamException;

  /**
   * Write text to the output
   * <p>
   *  将文本写入输出
   * 
   * 
   * @param text the value to write
   * @param start the starting position in the array
   * @param len the number of characters to write
   * @throws XMLStreamException
   */
  public void writeCharacters(char[] text, int start, int len)
    throws XMLStreamException;

  /**
   * Gets the prefix the uri is bound to
   * <p>
   *  将文本写入输出
   * 
   * 
   * @return the prefix or null
   * @throws XMLStreamException
   */
  public String getPrefix(String uri)
    throws XMLStreamException;

  /**
   * Sets the prefix the uri is bound to.  This prefix is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the prefix is bound in the root scope.
   * <p>
   *  获取uri绑定的前缀
   * 
   * 
   * @param prefix the prefix to bind to the uri, may not be null
   * @param uri the uri to bind to the prefix, may be null
   * @throws XMLStreamException
   */
  public void setPrefix(String prefix, String uri)
    throws XMLStreamException;


  /**
   * Binds a URI to the default namespace
   * This URI is bound
   * in the scope of the current START_ELEMENT / END_ELEMENT pair.
   * If this method is called before a START_ELEMENT has been written
   * the uri is bound in the root scope.
   * <p>
   *  设置uri绑定的前缀此前缀绑定在当前START_ELEMENT / END_ELEMENT对的范围内如果在写入START_ELEMENT之前调用此方法,前缀将绑定在根范围中
   * 
   * 
   * @param uri the uri to bind to the default namespace, may be null
   * @throws XMLStreamException
   */
  public void setDefaultNamespace(String uri)
    throws XMLStreamException;

  /**
   * Sets the current namespace context for prefix and uri bindings.
   * This context becomes the root namespace context for writing and
   * will replace the current root namespace context.  Subsequent calls
   * to setPrefix and setDefaultNamespace will bind namespaces using
   * the context passed to the method as the root context for resolving
   * namespaces.  This method may only be called once at the start of
   * the document.  It does not cause the namespaces to be declared.
   * If a namespace URI to prefix mapping is found in the namespace
   * context it is treated as declared and the prefix may be used
   * by the StreamWriter.
   * <p>
   * 将URI绑定到默认名称空间此URI绑定在当前START_ELEMENT / END_ELEMENT对的作用域中如果在写入START_ELEMENT之前调用此方法,则uri将绑定到根作用域
   * 
   * 
   * @param context the namespace context to use for this writer, may not be null
   * @throws XMLStreamException
   */
  public void setNamespaceContext(NamespaceContext context)
    throws XMLStreamException;

  /**
   * Returns the current namespace context.
   * <p>
   * 设置前缀和uri绑定的当前命名空间上下文此上下文成为用于写入的根命名空间上下文,并将替换当前根命名空间上下文对setPrefix和setDefaultNamespace的后续调用将使用传递到方法的上下文
   * 作为用于解析命名空间的根上下文来绑定命名空间此方法只能在文档开始时调用一次它不会导致命名空间被声明如果在命名空间上下文中找到命名空间URI前缀映射,则将其视为已声明,并且前缀可以由StreamWrite
   * r使用。
   * 
   * 
   * @return the current NamespaceContext
   */
  public NamespaceContext getNamespaceContext();

  /**
   * Get the value of a feature/property from the underlying implementation
   * <p>
   *  返回当前的命名空间上下文
   * 
   * 
   * @param name The name of the property, may not be null
   * @return The value of the property
   * @throws IllegalArgumentException if the property is not supported
   * @throws NullPointerException if the name is null
   */
  public Object getProperty(java.lang.String name) throws IllegalArgumentException;

}
