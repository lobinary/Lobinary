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

package java.sql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * The mapping in the JavaTM programming language for the SQL XML type.
 * XML is a built-in type that stores an XML value
 * as a column value in a row of a database table.
 * By default drivers implement an SQLXML object as
 * a logical pointer to the XML data
 * rather than the data itself.
 * An SQLXML object is valid for the duration of the transaction in which it was created.
 * <p>
 * The SQLXML interface provides methods for accessing the XML value
 * as a String, a Reader or Writer, or as a Stream.  The XML value
 * may also be accessed through a Source or set as a Result, which
 * are used with XML Parser APIs such as DOM, SAX, and StAX, as
 * well as with XSLT transforms and XPath evaluations.
 * <p>
 * Methods in the interfaces ResultSet, CallableStatement, and PreparedStatement,
 * such as getSQLXML allow a programmer to access an XML value.
 * In addition, this interface has methods for updating an XML value.
 * <p>
 * The XML value of the SQLXML instance may be obtained as a BinaryStream using
 * <pre>
 *   SQLXML sqlxml = resultSet.getSQLXML(column);
 *   InputStream binaryStream = sqlxml.getBinaryStream();
 * </pre>
 * For example, to parse an XML value with a DOM parser:
 * <pre>
 *   DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
 *   Document result = parser.parse(binaryStream);
 * </pre>
 * or to parse an XML value with a SAX parser to your handler:
 * <pre>
 *   SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
 *   parser.parse(binaryStream, myHandler);
 * </pre>
 * or to parse an XML value with a StAX parser:
 * <pre>
 *   XMLInputFactory factory = XMLInputFactory.newInstance();
 *   XMLStreamReader streamReader = factory.createXMLStreamReader(binaryStream);
 * </pre>
 * <p>
 * Because databases may use an optimized representation for the XML,
 * accessing the value through getSource() and
 * setResult() can lead to improved processing performance
 * without serializing to a stream representation and parsing the XML.
 * <p>
 * For example, to obtain a DOM Document Node:
 * <pre>
 *   DOMSource domSource = sqlxml.getSource(DOMSource.class);
 *   Document document = (Document) domSource.getNode();
 * </pre>
 * or to set the value to a DOM Document Node to myNode:
 * <pre>
 *   DOMResult domResult = sqlxml.setResult(DOMResult.class);
 *   domResult.setNode(myNode);
 * </pre>
 * or, to send SAX events to your handler:
 * <pre>
 *   SAXSource saxSource = sqlxml.getSource(SAXSource.class);
 *   XMLReader xmlReader = saxSource.getXMLReader();
 *   xmlReader.setContentHandler(myHandler);
 *   xmlReader.parse(saxSource.getInputSource());
 * </pre>
 * or, to set the result value from SAX events:
 * <pre>
 *   SAXResult saxResult = sqlxml.setResult(SAXResult.class);
 *   ContentHandler contentHandler = saxResult.getHandler();
 *   contentHandler.startDocument();
 *   // set the XML elements and attributes into the result
 *   contentHandler.endDocument();
 * </pre>
 * or, to obtain StAX events:
 * <pre>
 *   StAXSource staxSource = sqlxml.getSource(StAXSource.class);
 *   XMLStreamReader streamReader = staxSource.getXMLStreamReader();
 * </pre>
 * or, to set the result value from StAX events:
 * <pre>
 *   StAXResult staxResult = sqlxml.setResult(StAXResult.class);
 *   XMLStreamWriter streamWriter = staxResult.getXMLStreamWriter();
 * </pre>
 * or, to perform XSLT transformations on the XML value using the XSLT in xsltFile
 * output to file resultFile:
 * <pre>
 *   File xsltFile = new File("a.xslt");
 *   File myFile = new File("result.xml");
 *   Transformer xslt = TransformerFactory.newInstance().newTransformer(new StreamSource(xsltFile));
 *   Source source = sqlxml.getSource(null);
 *   Result result = new StreamResult(myFile);
 *   xslt.transform(source, result);
 * </pre>
 * or, to evaluate an XPath expression on the XML value:
 * <pre>
 *   XPath xpath = XPathFactory.newInstance().newXPath();
 *   DOMSource domSource = sqlxml.getSource(DOMSource.class);
 *   Document document = (Document) domSource.getNode();
 *   String expression = "/foo/@bar";
 *   String barValue = xpath.evaluate(expression, document);
 * </pre>
 * To set the XML value to be the result of an XSLT transform:
 * <pre>
 *   File sourceFile = new File("source.xml");
 *   Transformer xslt = TransformerFactory.newInstance().newTransformer(new StreamSource(xsltFile));
 *   Source streamSource = new StreamSource(sourceFile);
 *   Result result = sqlxml.setResult(null);
 *   xslt.transform(streamSource, result);
 * </pre>
 * Any Source can be transformed to a Result using the identity transform
 * specified by calling newTransformer():
 * <pre>
 *   Transformer identity = TransformerFactory.newInstance().newTransformer();
 *   Source source = sqlxml.getSource(null);
 *   File myFile = new File("result.xml");
 *   Result result = new StreamResult(myFile);
 *   identity.transform(source, result);
 * </pre>
 * To write the contents of a Source to standard output:
 * <pre>
 *   Transformer identity = TransformerFactory.newInstance().newTransformer();
 *   Source source = sqlxml.getSource(null);
 *   Result result = new StreamResult(System.out);
 *   identity.transform(source, result);
 * </pre>
 * To create a DOMSource from a DOMResult:
 * <pre>
 *    DOMSource domSource = new DOMSource(domResult.getNode());
 * </pre>
 * <p>
 * Incomplete or invalid XML values may cause an SQLException when
 * set or the exception may occur when execute() occurs.  All streams
 * must be closed before execute() occurs or an SQLException will be thrown.
 * <p>
 * Reading and writing XML values to or from an SQLXML object can happen at most once.
 * The conceptual states of readable and not readable determine if one
 * of the reading APIs will return a value or throw an exception.
 * The conceptual states of writable and not writable determine if one
 * of the writing APIs will set a value or throw an exception.
 * <p>
 * The state moves from readable to not readable once free() or any of the
 * reading APIs are called: getBinaryStream(), getCharacterStream(), getSource(), and getString().
 * Implementations may also change the state to not writable when this occurs.
 * <p>
 * The state moves from writable to not writeable once free() or any of the
 * writing APIs are called: setBinaryStream(), setCharacterStream(), setResult(), and setString().
 * Implementations may also change the state to not readable when this occurs.
 *
 * <p>
 * All methods on the <code>SQLXML</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  Java™编程语言中用于SQL XML类型的映射。 XML是一种内置类型,它将XML值作为列值存储在数据库表的行中。
 * 默认情况下,驱动程序实现一个SQLXML对象作为指向XML数据的逻辑指针,而不是数据本身。 SQLXML对象在创建它的事务的持续时间内有效。
 * <p>
 *  SQLXML接口提供了作为String,Reader或Writer或Stream访问XML值的方法。
 *  XML值还可以通过源访问或设置为结果,它们与XML解析器API(如DOM,SAX和StAX)以及XSLT转换和XPath评估一起使用。
 * <p>
 *  接口中的方法ResultSet,CallableStatement和PreparedStatement,例如getSQLXML允许程序员访问XML值。此外,此接口具有用于更新XML值的方法。
 * <p>
 *  SQLXML实例的XML值可以作为BinaryStream使用来获取
 * <pre>
 *  SQLXML sqlxml = resultSet.getSQLXML(column); InputStream binaryStream = sqlxml.getBinaryStream();
 * </pre>
 *  例如,要使用DOM解析器解析XML值：
 * <pre>
 *  DocumentBuilder parser = DocumentBuilderFactory.newInstance()。
 * newDocumentBuilder();文档result = parser.parse(binaryStream);。
 * </pre>
 *  或者使用SAX解析器将XML值解析到您的处理程序：
 * <pre>
 * SAXParser parser = SAXParserFactory.newInstance()。
 * newSAXParser(); parser.parse(binaryStream,myHandler);。
 * </pre>
 *  或使用StAX解析器解析XML值：
 * <pre>
 *  XMLInputFactory factory = XMLInputFactory.newInstance(); XMLStreamReader streamReader = factory.crea
 * teXMLStreamReader(binaryStream);。
 * </pre>
 * <p>
 *  因为数据库可能使用XML的优化表示,通过getSource()和setResult()访问值可以导致提高的处理性能,而不需要序列化到流表示和解析XML。
 * <p>
 *  例如,要获取DOM文档节点：
 * <pre>
 *  DOMSource domSource = sqlxml.getSource(DOMSource.class);文档文档=(文档)domSource.getNode();
 * </pre>
 *  或将DOM文档节点的值设置为myNode：
 * <pre>
 *  DOMResult domResult = sqlxml.setResult(DOMResult.class); domResult.setNode(myNode);
 * </pre>
 *  或者,向您的处理程序发送SAX事件：
 * <pre>
 *  SAXSource saxSource = sqlxml.getSource(SAXSource.class); XMLReader xmlReader = saxSource.getXMLReade
 * r(); xmlReader.setContentHandler(myHandler); xmlReader.parse(saxSource.getInputSource());。
 * </pre>
 *  或,设置SAX事件的结果值：
 * <pre>
 *  SAXResult saxResult = sqlxml.setResult(SAXResult.class); ContentHandler contentHandler = saxResult.g
 * etHandler(); contentHandler.startDocument(); //将XML元素和属性设置为结果contentHandler.endDocument();。
 * </pre>
 *  或者,获取StAX事件：
 * <pre>
 *  StAXSource staxSource = sqlxml.getSource(StAXSource.class); XMLStreamReader streamReader = staxSourc
 * e.getXMLStreamReader();。
 * </pre>
 * 或,设置StAX事件的结果值：
 * <pre>
 *  StAXResult staxResult = sqlxml.setResult(StAXResult.class); XMLStreamWriter streamWriter = staxResul
 * t.getXMLStreamWriter();。
 * </pre>
 *  或者,使用xsltFile输出中的XSLT对文件resultFile执行对XML值的XSLT转换：
 * <pre>
 *  文件xsltFile =新文件("a.xslt"); File myFile = new File("result.xml"); Transformer xslt = TransformerFacto
 * ry.newInstance()。
 * newTransformer(new StreamSource(xsltFile));源代码= sqlxml.getSource(null);结果result = new StreamResult(my
 * File); xslt.transform(source,result);。
 * </pre>
 *  或者,评估XML值上的XPath表达式：
 * <pre>
 *  XPath xpath = XPathFactory.newInstance()。
 * newXPath(); DOMSource domSource = sqlxml.getSource(DOMSource.class);文档文档=(文档)domSource.getNode(); Str
 * ing expression ="/ foo / @ bar"; String barValue = xpath.evaluate(expression,document);。
 *  XPath xpath = XPathFactory.newInstance()。
 * </pre>
 *  要将XML值设置为XSLT变换的结果：
 * <pre>
 *  File sourceFile = new File("source.xml"); Transformer xslt = TransformerFactory.newInstance()。
 * newTransformer(new StreamSource(xsltFile)); source streamSource = new StreamSource(sourceFile);结果resu
 * lt = sqlxml.setResult(null); xslt.transform(streamSource,result);。
 *  File sourceFile = new File("source.xml"); Transformer xslt = TransformerFactory.newInstance()。
 * </pre>
 *  任何源都可以使用通过调用newTransformer()指定的标识转换转换为结果：
 * <pre>
 * Transformer identity = TransformerFactory.newInstance()。
 * newTransformer();源代码= sqlxml.getSource(null); File myFile = new File("result.xml");结果result = new Str
 * eamResult(myFile); identity.transform(source,result);。
 * Transformer identity = TransformerFactory.newInstance()。
 * </pre>
 *  将源的内容写入标准输出：
 * <pre>
 *  Transformer identity = TransformerFactory.newInstance()。
 * newTransformer();源代码= sqlxml.getSource(null);结果result = new StreamResult(System.out); identity.transf
 * orm(source,result);。
 *  Transformer identity = TransformerFactory.newInstance()。
 * </pre>
 *  从DOMResult创建DOMSource：
 * 
 * @see javax.xml.parsers
 * @see javax.xml.stream
 * @see javax.xml.transform
 * @see javax.xml.xpath
 * @since 1.6
 */
public interface SQLXML
{
  /**
   * This method closes this object and releases the resources that it held.
   * The SQL XML object becomes invalid and neither readable or writeable
   * when this method is called.
   *
   * After <code>free</code> has been called, any attempt to invoke a
   * method other than <code>free</code> will result in a <code>SQLException</code>
   * being thrown.  If <code>free</code> is called multiple times, the subsequent
   * calls to <code>free</code> are treated as a no-op.
   * <p>
   * <pre>
   *  DOMSource domSource = new DOMSource(domResult.getNode());
   * </pre>
   * <p>
   *  不完整或无效的XML值可能会在设置时导致SQLException,或者在发生execute()时可能会发生异常。所有流必须在execute()发生或抛出SQLException之前关闭。
   * <p>
   *  向或从SQLXML对象读取和写入XML值最多只能发生一次。可读和不可读的概念状态确定读取API之一是否将返回值或抛出异常。可写和不可写的概念状态决定了写API之一是否将设置值或抛出异常。
   * <p>
   *  当free()或任何读取API被调用时,状态从可读到不可读：getBinaryStream(),getCharacterStream(),getSource()和getString()。
   * 当发生这种情况时,实现也可能将状态更改为不可写。
   * <p>
   * 当free()或任何编写的API被调用时,状态从可写到不可写：setBinaryStream(),setCharacterStream(),setResult()和setString()。
   * 当发生这种情况时,实现也可能将状态改变为不可读。
   * 
   * <p>
   *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> SQLXML </code>接口上的所有方法。
   * 
   * 
   * @throws SQLException if there is an error freeing the XML value.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void free() throws SQLException;

  /**
   * Retrieves the XML value designated by this SQLXML instance as a stream.
   * The bytes of the input stream are interpreted according to appendix F of the XML 1.0 specification.
   * The behavior of this method is the same as ResultSet.getBinaryStream()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not readable when this method is called and
   * may also become not writable depending on implementation.
   *
   * <p>
   *  此方法关闭此对象并释放它保存的资源。调用此方法时,SQL XML对象变得无效,既不可读也不可写。
   * 
   *  在调用<code> free </code>之后,任何试图调用<code> free </code>以外的方法都会导致抛出<code> SQLException </code>。
   * 如果多次调用<code> free </code>,则对<code> free </code>的后续调用将被视为无操作。
   * 
   * 
   * @return a stream containing the XML data.
   * @throws SQLException if there is an error processing the XML value.
   *   An exception is thrown if the state is not readable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  InputStream getBinaryStream() throws SQLException;

  /**
   * Retrieves a stream that can be used to write the XML value that this SQLXML instance represents.
   * The stream begins at position 0.
   * The bytes of the stream are interpreted according to appendix F of the XML 1.0 specification
   * The behavior of this method is the same as ResultSet.updateBinaryStream()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not writeable when this method is called and
   * may also become not readable depending on implementation.
   *
   * <p>
   *  将此SQLXML实例指定的XML值作为流检索。根据XML 1.0规范的附录F解释输入流的字节。
   * 当ResultSet的指定列具有SQLXML的类型java.sql.Types时,此方法的行为与ResultSet.getBinaryStream()相同。
   * <p>
   *  调用此方法时,SQL XML对象变得不可读,并且根据实现方式也可能变得不可写。
   * 
   * 
   * @return a stream to which data can be written.
   * @throws SQLException if there is an error processing the XML value.
   *   An exception is thrown if the state is not writable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  OutputStream setBinaryStream() throws SQLException;

  /**
   * Retrieves the XML value designated by this SQLXML instance as a java.io.Reader object.
   * The format of this stream is defined by org.xml.sax.InputSource,
   * where the characters in the stream represent the unicode code points for
   * XML according to section 2 and appendix B of the XML 1.0 specification.
   * Although an encoding declaration other than unicode may be present,
   * the encoding of the stream is unicode.
   * The behavior of this method is the same as ResultSet.getCharacterStream()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not readable when this method is called and
   * may also become not writable depending on implementation.
   *
   * <p>
   * 检索可用于写入此SQLXML实例表示的XML值的流。流从0开始。根据XML 1.0规范的附录F解释流的字节。
   * 当ResultSet的指定列具有类型java.sql时,此方法的行为与ResultSet.updateBinaryStream()的行为相同。SQLXML的类型。
   * <p>
   *  调用此方法时,SQL XML对象变得不可写,并且根据实现可能也变得不可读。
   * 
   * 
   * @return a stream containing the XML data.
   * @throws SQLException if there is an error processing the XML value.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if the stream does not contain valid characters.
   *   An exception is thrown if the state is not readable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  Reader getCharacterStream() throws SQLException;

  /**
   * Retrieves a stream to be used to write the XML value that this SQLXML instance represents.
   * The format of this stream is defined by org.xml.sax.InputSource,
   * where the characters in the stream represent the unicode code points for
   * XML according to section 2 and appendix B of the XML 1.0 specification.
   * Although an encoding declaration other than unicode may be present,
   * the encoding of the stream is unicode.
   * The behavior of this method is the same as ResultSet.updateCharacterStream()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not writeable when this method is called and
   * may also become not readable depending on implementation.
   *
   * <p>
   *  将此SQLXML实例指定的XML值作为java.io.Reader对象检索。
   * 此流的格式由org.xml.sax.InputSource定义,其中根据XML 1.0规范的第2节和附录B,流中的字符表示XML的Unicode代码点。
   * 虽然可能存在除了unicode之外的编码声明,但是流的编码是unicode。
   * 当ResultSet的指定列具有SQLXML的类型java.sql.Types时,此方法的行为与ResultSet.getCharacterStream()相同。
   * <p>
   *  调用此方法时,SQL XML对象变得不可读,并且根据实现方式也可能变得不可写。
   * 
   * 
   * @return a stream to which data can be written.
   * @throws SQLException if there is an error processing the XML value.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if the stream does not contain valid characters.
   *   An exception is thrown if the state is not writable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  Writer setCharacterStream() throws SQLException;

  /**
   * Returns a string representation of the XML value designated by this SQLXML instance.
   * The format of this String is defined by org.xml.sax.InputSource,
   * where the characters in the stream represent the unicode code points for
   * XML according to section 2 and appendix B of the XML 1.0 specification.
   * Although an encoding declaration other than unicode may be present,
   * the encoding of the String is unicode.
   * The behavior of this method is the same as ResultSet.getString()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not readable when this method is called and
   * may also become not writable depending on implementation.
   *
   * <p>
   * 检索要用于写入此SQLXML实例表示的XML值的流。
   * 此流的格式由org.xml.sax.InputSource定义,其中根据XML 1.0规范的第2节和附录B,流中的字符表示XML的Unicode代码点。
   * 虽然可能存在除了unicode之外的编码声明,但是流的编码是unicode。
   * 当ResultSet的指定列具有SQLXML的类型java.sql.Types时,此方法的行为与ResultSet.updateCharacterStream()相同。
   * <p>
   *  调用此方法时,SQL XML对象变得不可写,并且根据实现可能也变得不可读。
   * 
   * 
   * @return a string representation of the XML value designated by this SQLXML instance.
   * @throws SQLException if there is an error processing the XML value.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if the stream does not contain valid characters.
   *   An exception is thrown if the state is not readable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  String getString() throws SQLException;

  /**
   * Sets the XML value designated by this SQLXML instance to the given String representation.
   * The format of this String is defined by org.xml.sax.InputSource,
   * where the characters in the stream represent the unicode code points for
   * XML according to section 2 and appendix B of the XML 1.0 specification.
   * Although an encoding declaration other than unicode may be present,
   * the encoding of the String is unicode.
   * The behavior of this method is the same as ResultSet.updateString()
   * when the designated column of the ResultSet has a type java.sql.Types of SQLXML.
   * <p>
   * The SQL XML object becomes not writeable when this method is called and
   * may also become not readable depending on implementation.
   *
   * <p>
   *  返回此SQLXML实例指定的XML值的字符串表示形式。
   * 此String的格式由org.xml.sax.InputSource定义,其中根据XML 1.0规范的第2节和附录B,流中的字符表示XML的Unicode代码点。
   * 虽然可能存在除unicode之外的编码声明,但String的编码是unicode。
   * 当ResultSet的指定列具有SQLXML的类型java.sql.Types时,此方法的行为与ResultSet.getString()相同。
   * <p>
   *  调用此方法时,SQL XML对象变得不可读,并且根据实现方式也可能变得不可写。
   * 
   * 
   * @param value the XML value
   * @throws SQLException if there is an error processing the XML value.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if the stream does not contain valid characters.
   *   An exception is thrown if the state is not writable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  void setString(String value) throws SQLException;

  /**
   * Returns a Source for reading the XML value designated by this SQLXML instance.
   * Sources are used as inputs to XML parsers and XSLT transformers.
   * <p>
   * Sources for XML parsers will have namespace processing on by default.
   * The systemID of the Source is implementation dependent.
   * <p>
   * The SQL XML object becomes not readable when this method is called and
   * may also become not writable depending on implementation.
   * <p>
   * Note that SAX is a callback architecture, so a returned
   * SAXSource should then be set with a content handler that will
   * receive the SAX events from parsing.  The content handler
   * will receive callbacks based on the contents of the XML.
   * <pre>
   *   SAXSource saxSource = sqlxml.getSource(SAXSource.class);
   *   XMLReader xmlReader = saxSource.getXMLReader();
   *   xmlReader.setContentHandler(myHandler);
   *   xmlReader.parse(saxSource.getInputSource());
   * </pre>
   *
   * <p>
   * 将此SQLXML实例指定的XML值设置为给定的String表示形式。
   * 此String的格式由org.xml.sax.InputSource定义,其中根据XML 1.0规范的第2节和附录B,流中的字符表示XML的Unicode代码点。
   * 虽然可能存在除unicode之外的编码声明,但String的编码是unicode。
   * 当ResultSet的指定列具有SQLXML的类型java.sql.Types时,此方法的行为与ResultSet.updateString()相同。
   * <p>
   *  调用此方法时,SQL XML对象变得不可写,并且根据实现可能也变得不可读。
   * 
   * 
   * @param <T> the type of the class modeled by this Class object
   * @param sourceClass The class of the source, or null.
   * If the class is null, a vendor specific Source implementation will be returned.
   * The following classes are supported at a minimum:
   * <pre>
   *   javax.xml.transform.dom.DOMSource - returns a DOMSource
   *   javax.xml.transform.sax.SAXSource - returns a SAXSource
   *   javax.xml.transform.stax.StAXSource - returns a StAXSource
   *   javax.xml.transform.stream.StreamSource - returns a StreamSource
   * </pre>
   * @return a Source for reading the XML value.
   * @throws SQLException if there is an error processing the XML value
   *   or if this feature is not supported.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if an XML parser exception occurs.
   *   An exception is thrown if the state is not readable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  <T extends Source> T getSource(Class<T> sourceClass) throws SQLException;

  /**
   * Returns a Result for setting the XML value designated by this SQLXML instance.
   * <p>
   * The systemID of the Result is implementation dependent.
   * <p>
   * The SQL XML object becomes not writeable when this method is called and
   * may also become not readable depending on implementation.
   * <p>
   * Note that SAX is a callback architecture and the returned
   * SAXResult has a content handler assigned that will receive the
   * SAX events based on the contents of the XML.  Call the content
   * handler with the contents of the XML document to assign the values.
   * <pre>
   *   SAXResult saxResult = sqlxml.setResult(SAXResult.class);
   *   ContentHandler contentHandler = saxResult.getXMLReader().getContentHandler();
   *   contentHandler.startDocument();
   *   // set the XML elements and attributes into the result
   *   contentHandler.endDocument();
   * </pre>
   *
   * <p>
   *  返回一个Source,用于读取此SQLXML实例指定的XML值。源用作XML解析器和XSLT变换器的输入。
   * <p>
   *  默认情况下,XML解析器的源将启用命名空间处理。源的systemID是依赖于实现的。
   * <p>
   *  调用此方法时,SQL XML对象变得不可读,并且根据实现方式也可能变得不可写。
   * <p>
   *  注意,SAX是一个回调体系结构,所以一个返回的SAXSource应该设置一个内容处理程序,它将从解析接收SAX事件。内容处理程序将基于XML的内容接收回调。
   * <pre>
   * SAXSource saxSource = sqlxml.getSource(SAXSource.class); XMLReader xmlReader = saxSource.getXMLReader
   * (); xmlReader.setContentHandler(myHandler); xmlReader.parse(saxSource.getInputSource());。
   * </pre>
   * 
   * 
   * @param <T> the type of the class modeled by this Class object
   * @param resultClass The class of the result, or null.
   * If resultClass is null, a vendor specific Result implementation will be returned.
   * The following classes are supported at a minimum:
   * <pre>
   *   javax.xml.transform.dom.DOMResult - returns a DOMResult
   *   javax.xml.transform.sax.SAXResult - returns a SAXResult
   *   javax.xml.transform.stax.StAXResult - returns a StAXResult
   *   javax.xml.transform.stream.StreamResult - returns a StreamResult
   * </pre>
   * @return Returns a Result for setting the XML value.
   * @throws SQLException if there is an error processing the XML value
   *   or if this feature is not supported.
   *   The getCause() method of the exception may provide a more detailed exception, for example,
   *   if an XML parser exception occurs.
   *   An exception is thrown if the state is not writable.
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.6
   */
  <T extends Result> T setResult(Class<T> resultClass) throws SQLException;

}
