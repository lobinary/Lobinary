/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.validation.Schema;
import java.io.Reader;

/**
 * The <tt>Unmarshaller</tt> class governs the process of deserializing XML
 * data into newly created Java content trees, optionally validating the XML
 * data as it is unmarshalled.  It provides an overloading of unmarshal methods
 * for many different input kinds.
 *
 * <p>
 * Unmarshalling from a File:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       Object o = u.unmarshal( new File( "nosferatu.xml" ) );
 *    </pre>
 * </blockquote>
 *
 *
 * <p>
 * Unmarshalling from an InputStream:
 * <blockquote>
 *    <pre>
 *       InputStream is = new FileInputStream( "nosferatu.xml" );
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       Object o = u.unmarshal( is );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a URL:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       URL url = new URL( "http://beaker.east/nosferatu.xml" );
 *       Object o = u.unmarshal( url );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a StringBuffer using a
 * <tt>javax.xml.transform.stream.StreamSource</tt>:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       StringBuffer xmlStr = new StringBuffer( "&lt;?xml version=&quot;1.0&quot;?&gt;..." );
 *       Object o = u.unmarshal( new StreamSource( new StringReader( xmlStr.toString() ) ) );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a <tt>org.w3c.dom.Node</tt>:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *
 *       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 *       dbf.setNamespaceAware(true);
 *       DocumentBuilder db = dbf.newDocumentBuilder();
 *       Document doc = db.parse(new File( "nosferatu.xml"));

 *       Object o = u.unmarshal( doc );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a <tt>javax.xml.transform.sax.SAXSource</tt> using a
 * client specified validating SAX2.0 parser:
 * <blockquote>
 *    <pre>
 *       // configure a validating SAX2.0 parser (Xerces2)
 *       static final String JAXP_SCHEMA_LANGUAGE =
 *           "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
 *       static final String JAXP_SCHEMA_LOCATION =
 *           "http://java.sun.com/xml/jaxp/properties/schemaSource";
 *       static final String W3C_XML_SCHEMA =
 *           "http://www.w3.org/2001/XMLSchema";
 *
 *       System.setProperty( "javax.xml.parsers.SAXParserFactory",
 *                           "org.apache.xerces.jaxp.SAXParserFactoryImpl" );
 *
 *       SAXParserFactory spf = SAXParserFactory.newInstance();
 *       spf.setNamespaceAware(true);
 *       spf.setValidating(true);
 *       SAXParser saxParser = spf.newSAXParser();
 *
 *       try {
 *           saxParser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
 *           saxParser.setProperty(JAXP_SCHEMA_LOCATION, "http://....");
 *       } catch (SAXNotRecognizedException x) {
 *           // exception handling omitted
 *       }
 *
 *       XMLReader xmlReader = saxParser.getXMLReader();
 *       SAXSource source =
 *           new SAXSource( xmlReader, new InputSource( "http://..." ) );
 *
 *       // Setup JAXB to unmarshal
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       ValidationEventCollector vec = new ValidationEventCollector();
 *       u.setEventHandler( vec );
 *
 *       // turn off the JAXB provider's default validation mechanism to
 *       // avoid duplicate validation
 *       u.setValidating( false )
 *
 *       // unmarshal
 *       Object o = u.unmarshal( source );
 *
 *       // check for events
 *       if( vec.hasEvents() ) {
 *          // iterate over events
 *       }
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a StAX XMLStreamReader:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *
 *       javax.xml.stream.XMLStreamReader xmlStreamReader =
 *           javax.xml.stream.XMLInputFactory().newInstance().createXMLStreamReader( ... );
 *
 *       Object o = u.unmarshal( xmlStreamReader );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Unmarshalling from a StAX XMLEventReader:
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *
 *       javax.xml.stream.XMLEventReader xmlEventReader =
 *           javax.xml.stream.XMLInputFactory().newInstance().createXMLEventReader( ... );
 *
 *       Object o = u.unmarshal( xmlEventReader );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * <a name="unmarshalEx"></a>
 * <b>Unmarshalling XML Data</b><br>
 * <blockquote>
 * Unmarshalling can deserialize XML data that represents either an entire XML document
 * or a subtree of an XML document. Typically, it is sufficient to use the
 * unmarshalling methods described by
 * <a href="#unmarshalGlobal">Unmarshal root element that is declared globally</a>.
 * These unmarshal methods utilize {@link JAXBContext}'s mapping of global XML element
 * declarations and type definitions to JAXB mapped classes to initiate the
 * unmarshalling of the root element of  XML data.  When the {@link JAXBContext}'s
 * mappings are not sufficient to unmarshal the root element of XML data,
 * the application can assist the unmarshalling process by using the
 * <a href="#unmarshalByDeclaredType">unmarshal by declaredType methods</a>.
 * These methods are useful for unmarshalling XML data where
 * the root element corresponds to a local element declaration in the schema.
 * </blockquote>
 *
 * <blockquote>
 * An unmarshal method never returns null. If the unmarshal process is unable to unmarshal
 * the root of XML content to a JAXB mapped object, a fatal error is reported that
 * terminates processing by throwing JAXBException.
 * </blockquote>
 *
 * <p>
 * <a name="unmarshalGlobal"></a>
 * <b>Unmarshal a root element that is globally declared</b><br>
 * <blockquote>
 * The unmarshal methods that do not have an <tt>declaredType</tt> parameter use
 * {@link JAXBContext} to unmarshal the root element of an XML data. The {@link JAXBContext}
 * instance is the one that was used to create this <tt>Unmarshaller</tt>. The {@link JAXBContext}
 * instance maintains a mapping of globally declared XML element and type definition names to
 * JAXB mapped classes. The unmarshal method checks if {@link JAXBContext} has a mapping
 * from the root element's XML name and/or <tt>@xsi:type</tt> to a JAXB mapped class.  If it does, it umarshalls the
 * XML data using the appropriate JAXB mapped class. Note that when the root element name is unknown and the root
 * element has an <tt>@xsi:type</tt>, the XML data is unmarshalled
 * using that JAXB mapped class as the value of a {@link JAXBElement}.
 * When the {@link JAXBContext} object does not have a mapping for the root element's name
 * nor its <tt>@xsi:type</tt>, if it exists,
 * then the unmarshal operation will abort immediately by throwing a {@link UnmarshalException
 * UnmarshalException}. This exception scenario can be worked around by using the unmarshal by
 * declaredType methods described in the next subsection.
 * </blockquote>
 *
 * <p>
 * <a name="unmarshalByDeclaredType"></a>
 * <b>Unmarshal by Declared Type</b><br>
 * <blockquote>
 * The unmarshal methods with a <code>declaredType</code> parameter enable an
 * application to deserialize a root element of XML data, even when
 * there is no mapping in {@link JAXBContext} of the root element's XML name.
 * The unmarshaller unmarshals the root element using the application provided
 * mapping specified as the <tt>declaredType</tt> parameter.
 * Note that even when the root element's element name is mapped by {@link JAXBContext},
 * the <code>declaredType</code> parameter overrides that mapping for
 * deserializing the root element when using these unmarshal methods.
 * Additionally, when the root element of XML data has an <tt>xsi:type</tt> attribute and
 * that attribute's value references a type definition that is mapped
 * to a JAXB mapped class by {@link JAXBContext}, that the root
 * element's <tt>xsi:type</tt> attribute takes
 * precedence over the unmarshal methods <tt>declaredType</tt> parameter.
 * These methods always return a <tt>JAXBElement&lt;declaredType></tt>
 * instance. The table below shows how the properties of the returned JAXBElement
 * instance are set.
 *
 * <a name="unmarshalDeclaredTypeReturn"></a>
 * <p>
 *   <table border="2" rules="all" cellpadding="4">
 *   <thead>
 *     <tr>
 *       <th align="center" colspan="2">
 *       Unmarshal By Declared Type returned JAXBElement
 *       </tr>
 *     <tr>
 *       <th>JAXBElement Property</th>
 *       <th>Value</th>
 *     </tr>
 *     <tr>
 *       <td>name</td>
 *       <td><code>xml element name</code></td>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td>value</td>
 *       <td><code>instanceof declaredType</code></td>
 *     </tr>
 *     <tr>
 *       <td>declaredType</td>
 *       <td>unmarshal method <code>declaredType</code> parameter</td>
 *     </tr>
 *     <tr>
 *       <td>scope</td>
 *       <td><code>null</code> <i>(actual scope is unknown)</i></td>
 *     </tr>
 *   </tbody>
 *  </table>
 * </blockquote>
 *
 * <p>
 * The following is an example of
 * <a href="#unmarshalByDeclaredType">unmarshal by declaredType method</a>.
 * <p>
 * Unmarshal by declaredType from a <tt>org.w3c.dom.Node</tt>:
 * <blockquote>
 *    <pre>
 *       Schema fragment for example
 *       &lt;xs:schema>
 *          &lt;xs:complexType name="FooType">...&lt;\xs:complexType>
 *          &lt;!-- global element declaration "PurchaseOrder" -->
 *          &lt;xs:element name="PurchaseOrder">
 *              &lt;xs:complexType>
 *                 &lt;xs:sequence>
 *                    &lt;!-- local element declaration "foo" -->
 *                    &lt;xs:element name="foo" type="FooType"/>
 *                    ...
 *                 &lt;/xs:sequence>
 *              &lt;/xs:complexType>
 *          &lt;/xs:element>
 *       &lt;/xs:schema>
 *
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *
 *       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 *       dbf.setNamespaceAware(true);
 *       DocumentBuilder db = dbf.newDocumentBuilder();
 *       Document doc = db.parse(new File( "nosferatu.xml"));
 *       Element  fooSubtree = ...; // traverse DOM till reach xml element foo, constrained by a
 *                                  // local element declaration in schema.
 *
 *       // FooType is the JAXB mapping of the type of local element declaration foo.
 *       JAXBElement&lt;FooType> foo = u.unmarshal( fooSubtree, FooType.class);
 *    </pre>
 * </blockquote>
 *
 * <p>
 * <b>Support for SAX2.0 Compliant Parsers</b><br>
 * <blockquote>
 * A client application has the ability to select the SAX2.0 compliant parser
 * of their choice.  If a SAX parser is not selected, then the JAXB Provider's
 * default parser will be used.  Even though the JAXB Provider's default parser
 * is not required to be SAX2.0 compliant, all providers are required to allow
 * a client application to specify their own SAX2.0 parser.  Some providers may
 * require the client application to specify the SAX2.0 parser at schema compile
 * time. See {@link #unmarshal(javax.xml.transform.Source) unmarshal(Source)}
 * for more detail.
 * </blockquote>
 *
 * <p>
 * <b>Validation and Well-Formedness</b><br>
 * <blockquote>
 * <p>
 * A client application can enable or disable JAXP 1.3 validation
 * mechanism via the <tt>setSchema(javax.xml.validation.Schema)</tt> API.
 * Sophisticated clients can specify their own validating SAX 2.0 compliant
 * parser and bypass the JAXP 1.3 validation mechanism using the
 * {@link #unmarshal(javax.xml.transform.Source) unmarshal(Source)}  API.
 *
 * <p>
 * Since unmarshalling invalid XML content is defined in JAXB 2.0,
 * the Unmarshaller default validation event handler was made more lenient
 * than in JAXB 1.0.  When schema-derived code generated
 * by JAXB 1.0 binding compiler is registered with {@link JAXBContext},
 * the default unmarshal validation handler is
 * {@link javax.xml.bind.helpers.DefaultValidationEventHandler} and it
 * terminates the marshal  operation after encountering either a fatal error or an error.
 * For a JAXB 2.0 client application, there is no explicitly defined default
 * validation handler and the default event handling only
 * terminates the unmarshal operation after encountering a fatal error.
 *
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * There currently are not any properties required to be supported by all
 * JAXB Providers on Unmarshaller.  However, some providers may support
 * their own set of provider specific properties.
 * </blockquote>
 *
 * <p>
 * <a name="unmarshalEventCallback"></a>
 * <b>Unmarshal Event Callbacks</b><br>
 * <blockquote>
 * The {@link Unmarshaller} provides two styles of callback mechanisms
 * that allow application specific processing during key points in the
 * unmarshalling process.  In 'class defined' event callbacks, application
 * specific code placed in JAXB mapped classes is triggered during
 * unmarshalling.  'External listeners' allow for centralized processing
 * of unmarshal events in one callback method rather than by type event callbacks.
 * <p>
 * 'Class defined' event callback methods allow any JAXB mapped class to specify
 * its own specific callback methods by defining methods with the following method signature:
 * <blockquote>
 * <pre>
 *   // This method is called immediately after the object is created and before the unmarshalling of this
 *   // object begins. The callback provides an opportunity to initialize JavaBean properties prior to unmarshalling.
 *   void beforeUnmarshal(Unmarshaller, Object parent);
 *
 *   //This method is called after all the properties (except IDREF) are unmarshalled for this object,
 *   //but before this object is set to the parent object.
 *   void afterUnmarshal(Unmarshaller, Object parent);
 * </pre>
 * </blockquote>
 * The class defined callback methods should be used when the callback method requires
 * access to non-public methods and/or fields of the class.
 * <p>
 * The external listener callback mechanism enables the registration of a {@link Listener}
 * instance with an {@link Unmarshaller#setListener(Listener)}. The external listener receives all callback events,
 * allowing for more centralized processing than per class defined callback methods.  The external listener
 * receives events when unmarshalling proces is marshalling to a JAXB element or to JAXB mapped class.
 * <p>
 * The 'class defined' and external listener event callback methods are independent of each other,
 * both can be called for one event.  The invocation ordering when both listener callback methods exist is
 * defined in {@link Listener#beforeUnmarshal(Object, Object)} and {@link Listener#afterUnmarshal(Object, Object)}.
* <p>
 * An event callback method throwing an exception terminates the current unmarshal process.
 *
 * </blockquote>
 *
 * <p>
 *  <tt> Unmarshaller </tt>类管理将XML数据反序列化为新创建的Java内容树的过程,可选地在未解组合时验证XML数据。它为许多不同的输入类型提供了解组方法的重载。
 * 
 * <p>
 *  从文件解组：
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller(); 
 * Object o = u.unmarshal(new File("nosferatu.xml"));。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  从InputStream解组：
 * <blockquote>
 * <pre>
 *  InputStream is = new FileInputStream("nosferatu.xml"); JAXBContext jc = JAXBContext.newInstance("com
 * .acme.foo"); Unmarshaller u = jc.createUnmarshaller(); Object o = u.unmarshal(is);。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  从网址解组：
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller();网
 * 址url =新网址("http：//beaker.east/nosferatu.xml"); Object o = u.unmarshal(url);。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  使用<tt> javax.xml.transform.stream.StreamSource </tt>从StringBuffer解组：
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller(); 
 * StringBuffer xmlStr = new StringBuffer("&lt;?xml version ="1.0"?&gt; ..."); Object o = u.unmarshal(ne
 * w StreamSource(new StringReader(xmlStr.toString())));。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  从<tt> org.w3c.dom.Node </tt>取消编组：
 * <blockquote>
 * <pre>
 * JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller();
 * 
 *  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); dbf.setNamespaceAware(true); Docu
 * mentBuilder db = dbf.newDocumentBuilder();文档doc = db.parse(新文件("nosferatu.xml"));。
 * 
 *  Object o = u.unmarshal(doc);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  使用客户端指定的验证SAX2.0解析器从<tt> javax.xml.transform.sax.SAXSource </tt>取消编组：
 * <blockquote>
 * <pre>
 *  // configure a validating SAX2.0 parser(Xerces2)static final String JAXP_SCHEMA_LANGUAGE ="http://ja
 * va.sun.com/xml/jaxp/properties/schemaLanguage"; static final String JAXP_SCHEMA_LOCATION ="http://jav
 * a.sun.com/xml/jaxp/properties/schemaSource"; static final String W3C_XML_SCHEMA ="http://www.w3.org/2
 * 001/XMLSchema";。
 * 
 *  System.setProperty("javax.xml.parsers.SAXParserFactory","org.apache.xerces.jaxp.SAXParserFactoryImpl
 * ");。
 * 
 *  SAXParserFactory spf = SAXParserFactory.newInstance(); spf.setNamespaceAware(true); spf.setValidatin
 * g(true); SAXParser saxParser = spf.newSAXParser();。
 * 
 *  try {saxParser.setProperty(JAXP_SCHEMA_LANGUAGE,W3C_XML_SCHEMA); saxParser.setProperty(JAXP_SCHEMA_LOCATION,"http：// ...."); }
 *  catch(SAXNotRecognizedException x){// exception handling omitted}。
 * 
 *  XMLReader xmlReader = saxParser.getXMLReader(); SAXSource source = new SAXSource(xmlReader,new Input
 * Source("http：// ..."));。
 * 
 * //设置JAXB来解散JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmars
 * haller(); ValidationEventCollector vec = new ValidationEventCollector(); u.setEventHandler(vec);。
 * 
 *  //关闭JAXB提供程序的默认验证机制,以避免重复验证u.setValidating(false)
 * 
 *  // unmarshal Object o = u.unmarshal(source);
 * 
 *  //检查事件if(vec.hasEvents()){//迭代事件}
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  从StAX解组XMLStreamReader：
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller();。
 * 
 *  javax.xml.stream.XMLStreamReader xmlStreamReader = javax.xml.stream.XMLInputFactory()。
 * newInstance()。createXMLStreamReader(...);。
 * 
 *  Object o = u.unmarshal(xmlStreamReader);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  从StAX解组XMLEventReader：
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller();。
 * 
 *  javax.xml.stream.XMLEventReader xmlEventReader = javax.xml.stream.XMLInputFactory()。newInstance()。
 * createXMLEventReader(...);。
 * 
 *  Object o = u.unmarshal(xmlEventReader);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  <a name="unmarshalEx"> </a> <b>取消编码XML数据</b> <br>
 * <blockquote>
 * 解组可以反序列化表示整个XML文档或XML文档的子树的XML数据。通常,使用<a href="#unmarshalGlobal">描述全局声明的根元素</a>所描述的解组方法就足够了。
 * 这些解组方法利用{@link JAXBContext}的全局XML元素声明和类型定义到JAXB映射类的映射,以启动XML数据的根元素的解组。
 * 当{@link JAXBContext}的映射不足以解组XML数据的根元素时,应用程序可以通过使用<a href="#unmarshalByDeclaredType">通过declaredType方法解
 * 组方法</a>来帮助解组过程。
 * 这些解组方法利用{@link JAXBContext}的全局XML元素声明和类型定义到JAXB映射类的映射,以启动XML数据的根元素的解组。
 * 这些方法对于解组XML数据非常有用,其中根元素对应于模式中的本地元素声明。
 * </blockquote>
 * 
 * <blockquote>
 *  解组方法从不返回null。如果解组进程无法将XML内容的根解组到JAXB映射对象,则会报告致命错误,通过抛出JAXBException来终止处理。
 * </blockquote>
 * 
 * <p>
 *  <a name="unmarshalGlobal"> </a> <b>取消集合全局声明的根元素</b> <br>
 * <blockquote>
 * 没有<tt> declaredType </tt>参数的解组方法使用{@link JAXBContext}解组XML数据的根元素。
 *  {@link JAXBContext}实例是用于创建此<tt>取消编组</tt>的实例。 {@link JAXBContext}实例维护了全局声明的XML元素和类型定义名称到JAXB映射类的映射。
 * 解组方法检查{@link JAXBContext}是否具有从根元素的XML名称和/或<tt> @xsi：type </tt>到JAXB映射类的映射。
 * 如果是这样,它使用适当的JAXB映射类来解析XML数据。
 * 请注意,当根元素名称未知且根元素具有<tt> @xsi：type </tt>时,使用该JAXB映射类作为{@link JAXBElement}的值来解组XML数据。
 * 当{@link JAXBContext}对象没有根元素名称或其<tt> @xsi：type </tt>的映射时,如果它存在,则解组操作将立即中止,方法是抛出一个{@link UnmarshalException UnmarshalException}
 * 。
 * 请注意,当根元素名称未知且根元素具有<tt> @xsi：type </tt>时,使用该JAXB映射类作为{@link JAXBElement}的值来解组XML数据。
 * 可以通过使用下一小节中描述的通过declaredType方法解组来解决此异常情况。
 * </blockquote>
 * 
 * <p>
 *  <a name="unmarshalByDeclaredType"> </a> <b>按声明的类型取消编组</b> <br>
 * <blockquote>
 * 使用<code> declaredType </code>参数的解组方法使应用程序能够反序列化XML数据的根元素,即使在根元素的XML名称的{@link JAXBContext}中没有映射。
 *  unmarshaller使用指定为<tt> declaredType </tt>参数的应用程序提供的映射对根元素进行解组。
 * 注意,即使当根元素的元素名称由{@link JAXBContext}映射时,<code> declaredType </code>参数将覆盖用于在使用这些解组方法时反序列化根元素的映射。
 * 此外,当XML数据的根元素具有<tt> xsi：type </tt>属性,并且该属性的值引用通过{@link JAXBContext}映射到JAXB映射类的类型定义时,根元素的< tt> xsi：typ
 * e </tt>属性优先于解组方法<tt> declaredType </tt>参数。
 * 注意,即使当根元素的元素名称由{@link JAXBContext}映射时,<code> declaredType </code>参数将覆盖用于在使用这些解组方法时反序列化根元素的映射。
 * 这些方法总是返回一个<tt> JAXBElement&lt; declaredType> </tt>实例。下表显示如何设置返回的JAXBElement实例的属性。
 * 
 *  <a name="unmarshalDeclaredTypeReturn"> </a>
 * <p>
 * <table border="2" rules="all" cellpadding="4">
 * <thead>
 * <tr>
 * <th align="center" colspan="2">
 *  Unmarshal By Declared类型返回JAXBElement
 * </tr>
 * <tr>
 *  <th> JAXBElement属性</th> <th>值</th>
 * </tr>
 * <tr>
 *  <td> name </td> <td> <code> xml元素名称</code> </td>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <td> value </td> <td> <code> declarationof declaredType </code> </td>
 * </tr>
 * <tr>
 *  <td> declaredType </td> <td> unmarshal方法<code> declaredType </code>参数</td>
 * </tr>
 * <tr>
 *  <td>范围</td> <td> <code> null </code> <i>(实际范围未知)</i> </td>
 * </tr>
 * </tbody>
 * </table>
 * </blockquote>
 * 
 * <p>
 * 以下是<a href="#unmarshalByDeclaredType">通过declaredType方法解组</a>的示例。
 * <p>
 *  通过<tt> org.w3c.dom.Node </tt>取消声明的类型
 * <blockquote>
 * <pre>
 *  例如模式片段
 * &lt;xs:schema>
 * &lt;xs:complexType name="FooType">...&lt;\xs:complexType>
 * &lt;!-- global element declaration "PurchaseOrder" -->
 * &lt;xs:element name="PurchaseOrder">
 * &lt;xs:complexType>
 * &lt;xs:sequence>
 * &lt;!-- local element declaration "foo" -->
 * &lt;xs:element name="foo" type="FooType"/>
 *  ... ...
 * &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * &lt;/xs:element>
 * &lt;/xs:schema>
 * 
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller();。
 * 
 *  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); dbf.setNamespaceAware(true); Docu
 * mentBuilder db = dbf.newDocumentBuilder();文档doc = db.parse(新文件("nosferatu.xml"));元素fooSubtree = ...; 
 * //遍历DOM直到到达xml元素foo,由模式中的//局部元素声明约束。
 * 
 *  // FooType是本地元素声明foo的类型的JAXB映射。
 *  JAXBElement&lt; FooType&gt; foo = u.unmarshal(fooSubtree,FooType.class);。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  <b>支持SAX2.0兼容解析器</b> <br>
 * <blockquote>
 *  客户端应用程序有能力选择他们选择的SAX2.0兼容解析器。如果没有选择SAX解析器,那么将使用JAXB提供程序的默认解析器。
 * 即使JAXB提供程序的默认解析器不需要符合SAX2.0,所有提供程序都需要允许客户端应用程序指定自己的SAX2.0解析器。一些提供程序可能需要客户端应用程序在模式编译时指定SAX2.0解析器。
 * 有关更多详细信息,请参阅{@link #unmarshal(javax.xml.transform.Source)unmarshal(Source)}。
 * </blockquote>
 * 
 * <p>
 *  <b>验证和成功</b> <br>
 * <blockquote>
 * <p>
 * 客户端应用程序可以通过<tt> setSchema(javax.xml.validation.Schema)</tt> API启用或禁用JAXP 1.3验证机制。
 * 复杂的客户端可以使用{@link #unmarshal(javax.xml.transform.Source)unmarshal(Source)} API指定自己的验证SAX 2.0兼容解析器并绕过JA
 * XP 1.3验证机制。
 * 客户端应用程序可以通过<tt> setSchema(javax.xml.validation.Schema)</tt> API启用或禁用JAXP 1.3验证机制。
 * 
 * <p>
 *  由于在JAXB 2.0中定义了解组无效的XML内容,因此Unmarshaller默认验证事件处理程序比JAXB 1.0更为宽松。
 * 当使用{@link JAXBContext}注册由JAXB 1.0绑定编译器生成的模式派生代码时,默认的解组验证处理程序为{@link javax.xml.bind.helpers.DefaultValidationEventHandler}
 * ,并且在遇到致命错误后终止元组操作错误或错误。
 *  由于在JAXB 2.0中定义了解组无效的XML内容,因此Unmarshaller默认验证事件处理程序比JAXB 1.0更为宽松。
 * 对于JAXB 2.0客户端应用程序,没有显式定义的默认验证处理程序,并且默认事件处理仅在遇到致命错误后终止解组操作。
 * 
 * </blockquote>
 * 
 * <p>
 *  <a name="supportedProps"> </a> <b>支持的属性</b> <br>
 * <blockquote>
 * <p>
 *  目前没有任何属性需要由Unmarshaller上的所有JAXB提供程序支持。然而,一些提供者可以支持他们自己的提供者特定属性集合。
 * </blockquote>
 * 
 * <p>
 *  <a name="unmarshalEventCallback"> </a> <b>解除事件回调</b> <br>
 * <blockquote>
 * {@link Unmarshaller}提供了两种类型的回调机制,允许在取消集中过程的关键点期间进行特定于应用程序的处理。
 * 在"类定义"事件回调中,放置在JAXB映射类中的特定于应用程序的代码在解组期间被触发。 '外部监听器'允许在一个回调方法而不是类型事件回调中集中处理解组事件。
 * <p>
 *  '类定义'事件回调方法允许任何JAXB映射类通过使用以下方法签名定义方法来指定其自己的特定回调方法：
 * <blockquote>
 * <pre>
 *  //这个方法在对象被创建之后立即被调用之前,在这个//对象的解组之前开始。回调提供了在解组之前初始化JavaBean属性的机会。
 *  void beforeUnmarshal(Unmarshaller,Object parent);。
 * 
 *  //此方法在所有属性(除IDREF之外)未解组为此对象//,但此对象设置为父对象之前调用。 void afterUnmarshal(Unmarshaller,Object parent);
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see JAXBContext
 * @see Marshaller
 * @see Validator
 * @since JAXB1.0
 */
public interface Unmarshaller {

    /**
     * Unmarshal XML data from the specified file and return the resulting
     * content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     * </pre>
     * </blockquote>
     *  当回调方法需要访问非公共方法和/或类的字段时,应使用类定义的回调方法。
     * <p>
     * 外部侦听器回调机制支持将{@link Listener}实例与{@link Unmarshaller#setListener(Listener)}注册。
     * 外部监听器接收所有回调事件,允许比每个类定义的回调方法更集中的处理。外部侦听器在解组处理程序编组到JAXB元素或JAXB映射类时接收事件。
     * <p>
     *  '类定义'和外部监听器事件回调方法是彼此独立的,两者都可以调用一个事件。
     * 当两个侦听器回调方法都存在时,调用顺序在{@link Listener#beforeUnmarshal(Object,Object)}和{@link Listener#afterUnmarshal(Object,Object)}
     * 中定义。
     *  '类定义'和外部监听器事件回调方法是彼此独立的,两者都可以调用一个事件。
     * <p>
     *  抛出异常的事件回调方法会终止当前的解组过程。
     * 
     * </blockquote>
     * 
     * 
     * @param f the file to unmarshal XML data from
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the file parameter is null
     */
    public Object unmarshal( java.io.File f ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified InputStream and return the
     * resulting content tree.  Validation event location information may
     * be incomplete when using this form of the unmarshal API.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     *  从指定的文件解组XML数据,并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param is the InputStream to unmarshal XML data from
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the InputStream parameter is null
     */
    public Object unmarshal( java.io.InputStream is ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified Reader and return the
     * resulting content tree.  Validation event location information may
     * be incomplete when using this form of the unmarshal API,
     * because a Reader does not provide the system ID.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     *  从指定的InputStream解组XML数据并返回结果内容树。使用此表单的解编API时,验证事件位置信息可能不完整。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param reader the Reader to unmarshal XML data from
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the InputStream parameter is null
     * @since JAXB2.0
     */
    public Object unmarshal( Reader reader ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified URL and return the resulting
     * content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     *  从指定的阅读器解组XML数据,并返回结果内容树。使用此表单的解编API时,验证事件位置信息可能不完整,因为Reader不提供系统ID。
     * 
     * <p>
     * 实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param url the url to unmarshal XML data from
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the URL parameter is null
     */
    public Object unmarshal( java.net.URL url ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified SAX InputSource and return the
     * resulting content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     *  从指定的URL解组XML数据,并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param source the input source to unmarshal XML data from
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the InputSource parameter is null
     */
    public Object unmarshal( org.xml.sax.InputSource source ) throws JAXBException;

    /**
     * Unmarshal global XML data from the specified DOM tree and return the resulting
     * content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     *  从指定的SAX InputSource解组XML数据,并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param node
     *      the document/element to unmarshal XML data from.
     *      The caller must support at least Document and Element.
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the Node parameter is null
     * @see #unmarshal(org.w3c.dom.Node, Class)
     */
    public Object unmarshal( org.w3c.dom.Node node ) throws JAXBException;

    /**
     * Unmarshal XML data by JAXB mapped <tt>declaredType</tt>
     * and return the resulting content tree.
     *
     * <p>
     * Implements <a href="#unmarshalByDeclaredType">Unmarshal by Declared Type</a>
     *
     * <p>
     *  从指定的DOM树解组全局XML数据,并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * 
     * @param node
     *      the document/element to unmarshal XML data from.
     *      The caller must support at least Document and Element.
     * @param declaredType
     *      appropriate JAXB mapped class to hold <tt>node</tt>'s XML data.
     *
     * @return <a href="#unmarshalDeclaredTypeReturn">JAXB Element</a> representation of <tt>node</tt>
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If any parameter is null
     * @since JAXB2.0
     */
    public <T> JAXBElement<T> unmarshal( org.w3c.dom.Node node, Class<T> declaredType ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified XML Source and return the
     * resulting content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     * <a name="saxParserPlugable"></a>
     * <b>SAX 2.0 Parser Pluggability</b>
     * <p>
     * A client application can choose not to use the default parser mechanism
     * supplied with their JAXB provider.  Any SAX 2.0 compliant parser can be
     * substituted for the JAXB provider's default mechanism.  To do so, the
     * client application must properly configure a <tt>SAXSource</tt> containing
     * an <tt>XMLReader</tt> implemented by the SAX 2.0 parser provider.  If the
     * <tt>XMLReader</tt> has an <tt>org.xml.sax.ErrorHandler</tt> registered
     * on it, it will be replaced by the JAXB Provider so that validation errors
     * can be reported via the <tt>ValidationEventHandler</tt> mechanism of
     * JAXB.  If the <tt>SAXSource</tt> does not contain an <tt>XMLReader</tt>,
     * then the JAXB provider's default parser mechanism will be used.
     * <p>
     * This parser replacement mechanism can also be used to replace the JAXB
     * provider's unmarshal-time validation engine.  The client application
     * must properly configure their SAX 2.0 compliant parser to perform
     * validation (as shown in the example above).  Any <tt>SAXParserExceptions
     * </tt> encountered by the parser during the unmarshal operation will be
     * processed by the JAXB provider and converted into JAXB
     * <tt>ValidationEvent</tt> objects which will be reported back to the
     * client via the <tt>ValidationEventHandler</tt> registered with the
     * <tt>Unmarshaller</tt>.  <i>Note:</i> specifying a substitute validating
     * SAX 2.0 parser for unmarshalling does not necessarily replace the
     * validation engine used by the JAXB provider for performing on-demand
     * validation.
     * <p>
     * The only way for a client application to specify an alternate parser
     * mechanism to be used during unmarshal is via the
     * <tt>unmarshal(SAXSource)</tt> API.  All other forms of the unmarshal
     * method (File, URL, Node, etc) will use the JAXB provider's default
     * parser and validator mechanisms.
     *
     * <p>
     *  通过JAXB映射XML数据,映射<tt> declaredType </tt>并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalByDeclaredType">按声明类型取消编组</a>
     * 
     * 
     * @param source the XML Source to unmarshal XML data from (providers are
     *        only required to support SAXSource, DOMSource, and StreamSource)
     * @return the newly created root object of the java content tree
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the Source parameter is null
     * @see #unmarshal(javax.xml.transform.Source, Class)
     */
    public Object unmarshal( javax.xml.transform.Source source )
        throws JAXBException;


    /**
     * Unmarshal XML data from the specified XML Source by <tt>declaredType</tt> and return the
     * resulting content tree.
     *
     * <p>
     * Implements <a href="#unmarshalByDeclaredType">Unmarshal by Declared Type</a>
     *
     * <p>
     * See <a href="#saxParserPlugable">SAX 2.0 Parser Pluggability</a>
     *
     * <p>
     *  从指定的XML源解组XML数据并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * <p>
     *  <a name="saxParserPlugable"> </a> <b> SAX 2.0解析器可插入性</b>
     * <p>
     * 客户端应用程序可以选择不使用其JAXB提供程序提供的默认解析器机制。任何SAX 2.0兼容解析器都可以替代JAXB提供程序的默认机制。
     * 为此,客户端应用程序必须正确配置包含由SAX 2.0解析器提供程序实现的<tt> XMLReader </tt>的<tt> SAXSource </tt>。
     * 如果<tt> XMLReader </tt>在其上注册了<tt> org.xml.sax.ErrorHandler </tt>,它将被JAXB提供程序替换,以便验证错误可以通过<tt> Validati
     * onEventHandler </tt> JAXB的机制。
     * 为此,客户端应用程序必须正确配置包含由SAX 2.0解析器提供程序实现的<tt> XMLReader </tt>的<tt> SAXSource </tt>。
     * 如果<tt> SAXSource </tt>不包含<tt> XMLReader </tt>,那么将使用JAXB提供程序的默认解析器机制。
     * <p>
     *  此解析器替换机制也可用于替换JAXB提供程序的解组时验证引擎。客户端应用程序必须正确配置其SAX 2.0兼容解析器以执行验证(如上面的示例所示)。
     * 解析器在解组操作期间遇到的任何<tt> SAXParserExceptions </tt>将由JAXB提供程序处理并转换为JAXB <tt> ValidationEvent </tt>对象,这些对象将通
     * 过<tt> ValidationEventHandler </tt>中注册<tt> Unmarshaller </tt>。
     *  此解析器替换机制也可用于替换JAXB提供程序的解组时验证引擎。客户端应用程序必须正确配置其SAX 2.0兼容解析器以执行验证(如上面的示例所示)。
     *  <i>注意：</i>指定替代验证SAX 2.0解析器进行解组并不一定取代JAXB提供程序用于执行按需验证的验证引擎。
     * <p>
     * 客户端应用程序指定要在解组期间使用的备用解析器机制的唯一方法是通过<tt> unmarshal(SAXSource)</tt> API。
     * 所有其他形式的解组方法(文件,URL,节点等)将使用JAXB提供程序的默认解析器和验证器机制。
     * 
     * 
     * @param source the XML Source to unmarshal XML data from (providers are
     *        only required to support SAXSource, DOMSource, and StreamSource)
     * @param declaredType
     *      appropriate JAXB mapped class to hold <tt>source</tt>'s xml root element
     * @return Java content rooted by <a href="#unmarshalDeclaredTypeReturn">JAXB Element</a>
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If any parameter is null
     * @since JAXB2.0
     */
    public <T> JAXBElement<T> unmarshal( javax.xml.transform.Source source, Class<T> declaredType )
        throws JAXBException;

    /**
     * Unmarshal XML data from the specified pull parser and return the
     * resulting content tree.
     *
     * <p>
     * Implements <a href="#unmarshalGlobal">Unmarshal Global Root Element</a>.
     *
     * <p>
     * This method assumes that the parser is on a START_DOCUMENT or
     * START_ELEMENT event.  Unmarshalling will be done from this
     * start event to the corresponding end event.  If this method
     * returns successfully, the <tt>reader</tt> will be pointing at
     * the token right after the end event.
     *
     * <p>
     *  通过<tt> declaredType </tt>从指定的XML源解组XML数据,并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalByDeclaredType">按声明类型取消编组</a>
     * 
     * <p>
     *  请参见<a href="#saxParserPlugable"> SAX 2.0解析器可插入性</a>
     * 
     * 
     * @param reader
     *      The parser to be read.
     * @return
     *      the newly created root object of the java content tree.
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the <tt>reader</tt> parameter is null
     * @throws IllegalStateException
     *      If <tt>reader</tt> is not pointing to a START_DOCUMENT or
     *      START_ELEMENT  event.
     * @since JAXB2.0
     * @see #unmarshal(javax.xml.stream.XMLStreamReader, Class)
     */
    public Object unmarshal( javax.xml.stream.XMLStreamReader reader )
        throws JAXBException;

    /**
     * Unmarshal root element to JAXB mapped <tt>declaredType</tt>
     * and return the resulting content tree.
     *
     * <p>
     * This method implements <a href="#unmarshalByDeclaredType">unmarshal by declaredType</a>.
     * <p>
     * This method assumes that the parser is on a START_DOCUMENT or
     * START_ELEMENT event. Unmarshalling will be done from this
     * start event to the corresponding end event.  If this method
     * returns successfully, the <tt>reader</tt> will be pointing at
     * the token right after the end event.
     *
     * <p>
     *  从指定的pull解析器解组XML数据并返回结果内容树。
     * 
     * <p>
     *  实现<a href="#unmarshalGlobal">解散全局根元素</a>。
     * 
     * <p>
     *  此方法假设解析器位于START_DOCUMENT或START_ELEMENT事件。将从此开始事件到相应的结束事件进行解组。如果此方法成功返回,则<tt>读取器</tt>将在结束事件后立即指向令牌。
     * 
     * 
     * @param reader
     *      The parser to be read.
     * @param declaredType
     *      appropriate JAXB mapped class to hold <tt>reader</tt>'s START_ELEMENT XML data.
     *
     * @return   content tree rooted by <a href="#unmarshalDeclaredTypeReturn">JAXB Element representation</a>
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If any parameter is null
     * @since JAXB2.0
     */
    public <T> JAXBElement<T> unmarshal( javax.xml.stream.XMLStreamReader reader, Class<T> declaredType ) throws JAXBException;

    /**
     * Unmarshal XML data from the specified pull parser and return the
     * resulting content tree.
     *
     * <p>
     * This method is an <a href="#unmarshalGlobal">Unmarshal Global Root method</a>.
     *
     * <p>
     * This method assumes that the parser is on a START_DOCUMENT or
     * START_ELEMENT event.  Unmarshalling will be done from this
     * start event to the corresponding end event.  If this method
     * returns successfully, the <tt>reader</tt> will be pointing at
     * the token right after the end event.
     *
     * <p>
     *  将根元素解组为JAXB映射<tt> declaredType </tt>并返回结果内容树。
     * 
     * <p>
     *  此方法实现<a href="#unmarshalByDeclaredType">通过declType取消组合</a>。
     * <p>
     *  此方法假设解析器位于START_DOCUMENT或START_ELEMENT事件。将从此开始事件到相应的结束事件进行解组。如果此方法成功返回,则<tt>读取器</tt>将在结束事件后立即指向令牌。
     * 
     * 
     * @param reader
     *      The parser to be read.
     * @return
     *      the newly created root object of the java content tree.
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If the <tt>reader</tt> parameter is null
     * @throws IllegalStateException
     *      If <tt>reader</tt> is not pointing to a START_DOCUMENT or
     *      START_ELEMENT event.
     * @since JAXB2.0
     * @see #unmarshal(javax.xml.stream.XMLEventReader, Class)
     */
    public Object unmarshal( javax.xml.stream.XMLEventReader reader )
        throws JAXBException;

    /**
     * Unmarshal root element to JAXB mapped <tt>declaredType</tt>
     * and return the resulting content tree.
     *
     * <p>
     * This method implements <a href="#unmarshalByDeclaredType">unmarshal by declaredType</a>.
     *
     * <p>
     * This method assumes that the parser is on a START_DOCUMENT or
     * START_ELEMENT event. Unmarshalling will be done from this
     * start event to the corresponding end event.  If this method
     * returns successfully, the <tt>reader</tt> will be pointing at
     * the token right after the end event.
     *
     * <p>
     * 从指定的pull解析器解组XML数据并返回结果内容树。
     * 
     * <p>
     *  此方法是<a href="#unmarshalGlobal">解散全局根方法</a>。
     * 
     * <p>
     *  此方法假设解析器位于START_DOCUMENT或START_ELEMENT事件。将从此开始事件到相应的结束事件进行解组。如果此方法成功返回,则<tt>读取器</tt>将在结束事件后立即指向令牌。
     * 
     * 
     * @param reader
     *      The parser to be read.
     * @param declaredType
     *      appropriate JAXB mapped class to hold <tt>reader</tt>'s START_ELEMENT XML data.
     *
     * @return   content tree rooted by <a href="#unmarshalDeclaredTypeReturn">JAXB Element representation</a>
     *
     * @throws JAXBException
     *     If any unexpected errors occur while unmarshalling
     * @throws UnmarshalException
     *     If the {@link ValidationEventHandler ValidationEventHandler}
     *     returns false from its <tt>handleEvent</tt> method or the
     *     <tt>Unmarshaller</tt> is unable to perform the XML to Java
     *     binding.  See <a href="#unmarshalEx">Unmarshalling XML Data</a>
     * @throws IllegalArgumentException
     *      If any parameter is null
     * @since JAXB2.0
     */
    public <T> JAXBElement<T> unmarshal( javax.xml.stream.XMLEventReader reader, Class<T> declaredType ) throws JAXBException;

    /**
     * Get an unmarshaller handler object that can be used as a component in
     * an XML pipeline.
     *
     * <p>
     * The JAXB Provider can return the same handler object for multiple
     * invocations of this method. In other words, this method does not
     * necessarily create a new instance of <tt>UnmarshallerHandler</tt>. If the
     * application needs to use more than one <tt>UnmarshallerHandler</tt>, it
     * should create more than one <tt>Unmarshaller</tt>.
     *
     * <p>
     *  将根元素解组为JAXB映射<tt> declaredType </tt>并返回结果内容树。
     * 
     * <p>
     *  此方法实现<a href="#unmarshalByDeclaredType">通过declType取消组合</a>。
     * 
     * <p>
     *  此方法假设解析器位于START_DOCUMENT或START_ELEMENT事件。将从此开始事件到相应的结束事件进行解组。如果此方法成功返回,则<tt>读取器</tt>将在结束事件后立即指向令牌。
     * 
     * 
     * @return the unmarshaller handler object
     * @see UnmarshallerHandler
     */
    public UnmarshallerHandler getUnmarshallerHandler();

    /**
     * Specifies whether or not the default validation mechanism of the
     * <tt>Unmarshaller</tt> should validate during unmarshal operations.
     * By default, the <tt>Unmarshaller</tt> does not validate.
     * <p>
     * This method may only be invoked before or after calling one of the
     * unmarshal methods.
     * <p>
     * This method only controls the JAXB Provider's default unmarshal-time
     * validation mechanism - it has no impact on clients that specify their
     * own validating SAX 2.0 compliant parser.  Clients that specify their
     * own unmarshal-time validation mechanism may wish to turn off the JAXB
     * Provider's default validation mechanism via this API to avoid "double
     * validation".
     * <p>
     * This method is deprecated as of JAXB 2.0 - please use the new
     * {@link #setSchema(javax.xml.validation.Schema)} API.
     *
     * <p>
     *  获取可以用作XML管道中的组件的解组器处理程序对象。
     * 
     * <p>
     *  JAXB提供程序可以返回相同的处理程序对象以进行此方法的多个调用。换句话说,此方法不一定创建<tt> UnmarshallerHandler </tt>的新实例。
     * 如果应用程序需要使用多个<tt> UnmarshallerHandler </tt>,则应创建多个<tt> Unmarshaller </tt>。
     * 
     * 
     * @param validating true if the Unmarshaller should validate during
     *        unmarshal, false otherwise
     * @throws JAXBException if an error occurred while enabling or disabling
     *         validation at unmarshal time
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Unmarshaller created from a JAXBContext referencing
     *         JAXB 2.0 mapped classes
     * @deprecated since JAXB2.0, please see {@link #setSchema(javax.xml.validation.Schema)}
     */
    public void setValidating( boolean validating )
        throws JAXBException;

    /**
     * Indicates whether or not the <tt>Unmarshaller</tt> is configured to
     * validate during unmarshal operations.
     * <p>
     * This API returns the state of the JAXB Provider's default unmarshal-time
     * validation mechanism.
     * <p>
     * This method is deprecated as of JAXB 2.0 - please use the new
     * {@link #getSchema()} API.
     *
     * <p>
     * 指定在解组操作期间<tt>取消合并器</tt>的默认验证机制是否应验证。默认情况下,<tt>取消绑定器</tt>不验证。
     * <p>
     *  此方法只能在调用其中一个解组方法之前或之后调用。
     * <p>
     *  此方法仅控制JAXB提供程序的缺省解组时验证机制 - 它对指定自己的验证SAX 2.0兼容解析器的客户端没有影响。
     * 指定自己的解编组时间验证机制的客户端可能希望通过此API关闭JAXB提供程序的默认验证机制,以避免"双重验证"。
     * <p>
     *  此方法自JAXB 2.0起已弃用 - 请使用新的{@link #setSchema(javax.xml.validation.Schema)} API。
     * 
     * 
     * @return true if the Unmarshaller is configured to validate during
     *         unmarshal operations, false otherwise
     * @throws JAXBException if an error occurs while retrieving the validating
     *         flag
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Unmarshaller created from a JAXBContext referencing
     *         JAXB 2.0 mapped classes
     * @deprecated since JAXB2.0, please see {@link #getSchema()}
     */
    public boolean isValidating()
        throws JAXBException;

    /**
     * Allow an application to register a <tt>ValidationEventHandler</tt>.
     * <p>
     * The <tt>ValidationEventHandler</tt> will be called by the JAXB Provider
     * if any validation errors are encountered during calls to any of the
     * unmarshal methods.  If the client application does not register a
     * <tt>ValidationEventHandler</tt> before invoking the unmarshal methods,
     * then <tt>ValidationEvents</tt> will be handled by the default event
     * handler which will terminate the unmarshal operation after the first
     * error or fatal error is encountered.
     * <p>
     * Calling this method with a null parameter will cause the Unmarshaller
     * to revert back to the default event handler.
     *
     * <p>
     *  指示是否将<tt>取消编组器</tt>配置为在取消编组操作期间进行验证。
     * <p>
     *  此API返回JAXB提供程序的缺省解组时验证机制的状态。
     * <p>
     *  此方法自JAXB 2.0起已弃用 - 请使用新的{@link #getSchema()} API。
     * 
     * 
     * @param handler the validation event handler
     * @throws JAXBException if an error was encountered while setting the
     *         event handler
     */
    public void setEventHandler( ValidationEventHandler handler )
        throws JAXBException;

    /**
     * Return the current event handler or the default event handler if one
     * hasn't been set.
     *
     * <p>
     *  允许应用程序注册<tt> ValidationEventHandler </tt>。
     * <p>
     * 如果在调用任何解组方法时遇到任何验证错误,则JAXB提供程序将调用<tt> ValidationEventHandler </tt>。
     * 如果客户端应用程序在调用解组方法之前未注册<tt> ValidationEventHandler </tt>,则<tt> ValidationEvents </tt>将由默认事件处理程序处理,该处理程序
     * 将在第一个错误后终止解组操作;遇到致命错误。
     * 如果在调用任何解组方法时遇到任何验证错误,则JAXB提供程序将调用<tt> ValidationEventHandler </tt>。
     * <p>
     *  使用null参数调用此方法将导致Unmarshaller恢复为默认事件处理程序。
     * 
     * 
     * @return the current ValidationEventHandler or the default event handler
     *         if it hasn't been set
     * @throws JAXBException if an error was encountered while getting the
     *         current event handler
     */
    public ValidationEventHandler getEventHandler()
        throws JAXBException;

    /**
     * Set the particular property in the underlying implementation of
     * <tt>Unmarshaller</tt>.  This method can only be used to set one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to set an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     *  返回当前事件处理程序或默认事件处理程序(如果尚未设置)。
     * 
     * 
     * @param name the name of the property to be set. This value can either
     *              be specified using one of the constant fields or a user
     *              supplied string.
     * @param value the value of the property to be set
     *
     * @throws PropertyException when there is an error processing the given
     *                            property or value
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public void setProperty( String name, Object value )
        throws PropertyException;

    /**
     * Get the particular property in the underlying implementation of
     * <tt>Unmarshaller</tt>.  This method can only be used to get one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     *  在<tt> Unmarshaller </tt>的底层实现中设置特定属性。此方法只能用于设置上面的标准JAXB定义属性或特定于提供程序的属性。
     * 尝试设置未定义的属性将导致抛出PropertyException。请参见<a href="#supportedProps">支持的属性</a>。
     * 
     * 
     * @param name the name of the property to retrieve
     * @return the value of the requested property
     *
     * @throws PropertyException
     *      when there is an error retrieving the given property or value
     *      property name
     * @throws IllegalArgumentException
     *      If the name parameter is null
     */
    public Object getProperty( String name ) throws PropertyException;

    /**
     * Specify the JAXP 1.3 {@link javax.xml.validation.Schema Schema}
     * object that should be used to validate subsequent unmarshal operations
     * against.  Passing null into this method will disable validation.
     * <p>
     * This method replaces the deprecated {@link #setValidating(boolean) setValidating(boolean)}
     * API.
     *
     * <p>
     * Initially this property is set to <tt>null</tt>.
     *
     * <p>
     *  获取<tt> Unmarshaller </tt>的基本实现中的特定属性。此方法只能用于获取上面标准的JAXB定义的属性或特定于提供程序的属性。
     * 尝试获取未定义的属性将导致PropertyException被抛出。请参见<a href="#supportedProps">支持的属性</a>。
     * 
     * 
     * @param schema Schema object to validate unmarshal operations against or null to disable validation
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Unmarshaller created from a JAXBContext referencing
     *         JAXB 1.0 mapped classes
     * @since JAXB2.0
     */
    public void setSchema( javax.xml.validation.Schema schema );

    /**
     * Get the JAXP 1.3 {@link javax.xml.validation.Schema Schema} object
     * being used to perform unmarshal-time validation.  If there is no
     * Schema set on the unmarshaller, then this method will return null
     * indicating that unmarshal-time validation will not be performed.
     * <p>
     * This method provides replacement functionality for the deprecated
     * {@link #isValidating()} API as well as access to the Schema object.
     * To determine if the Unmarshaller has validation enabled, simply
     * test the return type for null:
     * <p>
     * <code>
     *   boolean isValidating = u.getSchema()!=null;
     * </code>
     *
     * <p>
     * 指定应用于验证后续解除编码操作的JAXP 1.3 {@link javax.xml.validation.Schema Schema}对象。将null传递到此方法将禁用验证。
     * <p>
     *  此方法替换已弃用的{@link #setValidating(boolean)setValidating(boolean)} API。
     * 
     * <p>
     *  最初,此属性设置为<tt> null </tt>。
     * 
     * 
     * @return the Schema object being used to perform unmarshal-time
     *      validation or null if not present
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Unmarshaller created from a JAXBContext referencing
     *         JAXB 1.0 mapped classes
     * @since JAXB2.0
     */
    public javax.xml.validation.Schema getSchema();

    /**
     * Associates a configured instance of {@link XmlAdapter} with this unmarshaller.
     *
     * <p>
     * This is a convenience method that invokes <code>setAdapter(adapter.getClass(),adapter);</code>.
     *
     * <p>
     *  获取用于执行解组时验证的JAXP 1.3 {@link javax.xml.validation.Schema Schema}对象。
     * 如果在解组器上没有模式集,那么这个方法将返回null,表示不会执行解组时验证。
     * <p>
     *  此方法为弃用的{@link #isValidating()} API提供替换功能,以及访问Schema对象。要确定Unmarshaller是否已启用验证,只需测试null的返回类型：
     * <p>
     * <code>
     *  boolean isValidating = u.getSchema()！= null;
     * </code>
     * 
     * 
     * @see #setAdapter(Class,XmlAdapter)
     * @throws IllegalArgumentException
     *      if the adapter parameter is null.
     * @throws UnsupportedOperationException
     *      if invoked agains a JAXB 1.0 implementation.
     * @since JAXB2.0
     */
    public void setAdapter( XmlAdapter adapter );

    /**
     * Associates a configured instance of {@link XmlAdapter} with this unmarshaller.
     *
     * <p>
     * Every unmarshaller internally maintains a
     * {@link java.util.Map}&lt;{@link Class},{@link XmlAdapter}>,
     * which it uses for unmarshalling classes whose fields/methods are annotated
     * with {@link javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter}.
     *
     * <p>
     * This method allows applications to use a configured instance of {@link XmlAdapter}.
     * When an instance of an adapter is not given, an unmarshaller will create
     * one by invoking its default constructor.
     *
     * <p>
     *  将配置的{@link XmlAdapter}实例与此解组器关联。
     * 
     * <p>
     *  这是一个方便的方法,调用<code> setAdapter(adapter.getClass(),adapter); </code>。
     * 
     * 
     * @param type
     *      The type of the adapter. The specified instance will be used when
     *      {@link javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter#value()}
     *      refers to this type.
     * @param adapter
     *      The instance of the adapter to be used. If null, it will un-register
     *      the current adapter set for this type.
     * @throws IllegalArgumentException
     *      if the type parameter is null.
     * @throws UnsupportedOperationException
     *      if invoked agains a JAXB 1.0 implementation.
     * @since JAXB2.0
     */
    public <A extends XmlAdapter> void setAdapter( Class<A> type, A adapter );

    /**
     * Gets the adapter associated with the specified type.
     *
     * This is the reverse operation of the {@link #setAdapter} method.
     *
     * <p>
     *  将配置的{@link XmlAdapter}实例与此解组器关联。
     * 
     * <p>
     *  每个解组器在内部维护一个{@link java.util.Map}&lt; {@ link Class},{@ link XmlAdapter}>,它用于解组其字段/方法注释为{@link javax.xml.bind .annotation.adapters.XmlJavaTypeAdapter}
     * 。
     * 
     * <p>
     * 此方法允许应用程序使用配置的{@link XmlAdapter}实例。当未给出适配器的实例时,解组器将通过调用其默认构造函数来创建一个实例。
     * 
     * 
     * @throws IllegalArgumentException
     *      if the type parameter is null.
     * @throws UnsupportedOperationException
     *      if invoked agains a JAXB 1.0 implementation.
     * @since JAXB2.0
     */
    public <A extends XmlAdapter> A getAdapter( Class<A> type );

    /**
     * <p>Associate a context that resolves cid's, content-id URIs, to
     * binary data passed as attachments.</p>
     * <p/>
     * <p>Unmarshal time validation, enabled via {@link #setSchema(Schema)},
     * must be supported even when unmarshaller is performing XOP processing.
     * </p>
     *
     * <p>
     *  获取与指定类型关联的适配器。
     * 
     *  这是{@link #setAdapter}方法的逆操作。
     * 
     * 
     * @throws IllegalStateException if attempt to concurrently call this
     *                               method during a unmarshal operation.
     */
    void setAttachmentUnmarshaller(AttachmentUnmarshaller au);

    AttachmentUnmarshaller getAttachmentUnmarshaller();

    /**
     * <p/>
     * Register an instance of an implementation of this class with {@link Unmarshaller} to externally listen
     * for unmarshal events.
     * <p/>
     * <p/>
     * This class enables pre and post processing of an instance of a JAXB mapped class
     * as XML data is unmarshalled into it. The event callbacks are called when unmarshalling
     * XML content into a JAXBElement instance or a JAXB mapped class that represents a complex type definition.
     * The event callbacks are not called when unmarshalling to an instance of a
     * Java datatype that represents a simple type definition.
     * <p/>
     * <p/>
     * External listener is one of two different mechanisms for defining unmarshal event callbacks.
     * See <a href="Unmarshaller.html#unmarshalEventCallback">Unmarshal Event Callbacks</a> for an overview.
     * <p/>
     * (@link #setListener(Listener)}
     * (@link #getListener()}
     *
     * <p>
     *  <p>将解析cid,content-id URI的上下文关联到作为附件传递的二进制数据。</p>
     * <p/>
     *  <p>必须支持通过{@link #setSchema(Schema)}启用的取消编组时间验证,即使解组器正在执行XOP处理。
     * </p>
     * 
     * 
     * @since JAXB2.0
     */
    public static abstract class Listener {
        /**
         * <p/>
         * Callback method invoked before unmarshalling into <tt>target</tt>.
         * <p/>
         * <p/>
         * This method is invoked immediately after <tt>target</tt> was created and
         * before the unmarshalling of this object begins. Note that
         * if the class of <tt>target</tt> defines its own <tt>beforeUnmarshal</tt> method,
         * the class specific callback method is invoked before this method is invoked.
         *
         * <p>
         * <p/>
         *  使用{@link Unmarshaller}注册此类的实现的实例以外部侦听解组件事件。
         * <p/>
         * <p/>
         *  此类允许对JAXB映射类的实例进行预处理和后处理,因为XML数据未解组到其中。在将XML内容解组成JAXBElement实例或表示复杂类型定义的JAXB映射类时,将调用事件回调。
         * 在解组合到表示简单类型定义的Java数据类型的实例时,不会调用事件回调。
         * <p/>
         * <p/>
         *  外部侦听器是定义解组件事件回调的两种不同机制之一。有关概述,请参见<a href="Unmarshaller.html#unmarshalEventCallback">解除事件回调</a>。
         * <p/>
         *  (@link #setListener(Listener)}(@link #getListener()}
         * 
         * 
         * @param target non-null instance of JAXB mapped class prior to unmarshalling into it.
         * @param parent instance of JAXB mapped class that will eventually reference <tt>target</tt>.
         *               <tt>null</tt> when <tt>target</tt> is root element.
         */
        public void beforeUnmarshal(Object target, Object parent) {
        }

        /**
         * <p/>
         * Callback method invoked after unmarshalling XML data into <tt>target</tt>.
         * <p/>
         * <p/>
         * This method is invoked after all the properties (except IDREF) are unmarshalled into <tt>target</tt>,
         * but before <tt>target</tt> is set into its <tt>parent</tt> object.
         * Note that if the class of <tt>target</tt> defines its own <tt>afterUnmarshal</tt> method,
         * the class specific callback method is invoked before this method is invoked.
         *
         * <p>
         * <p/>
         *  回调方法在取消分组到<tt>目标</tt>之前调用。
         * <p/>
         * <p/>
         * 此方法在创建<tt> target </tt>之后并在此对象的解组开始之前立即调用。
         * 请注意,如果<tt> target </tt>的类定义了自己的<tt> beforeUnmarshal </tt>方法,则在调用此方法之前会调用类特定的回调方法。
         * 
         * 
         * @param target non-null instance of JAXB mapped class prior to unmarshalling into it.
         * @param parent instance of JAXB mapped class that will reference <tt>target</tt>.
         *               <tt>null</tt> when <tt>target</tt> is root element.
         */
        public void afterUnmarshal(Object target, Object parent) {
        }
    }

    /**
     * <p>
     * Register unmarshal event callback {@link Listener} with this {@link Unmarshaller}.
     *
     * <p>
     * There is only one Listener per Unmarshaller. Setting a Listener replaces the previous set Listener.
     * One can unregister current Listener by setting listener to <tt>null</tt>.
     *
     * <p>
     * <p/>
     *  在将XML数据解组成<tt>目标</tt>后调用回调方法。
     * <p/>
     * <p/>
     *  在所有属性(除IDREF之外)未解组为<tt>目标</tt>,但在<tt>目标</tt>设置为其<tt>父</tt>对象之前,调用此方法。
     * 请注意,如果<tt> target </tt>的类定义了自己的<tt> afterUnmarshal </tt>方法,那么在调用此方法之前将调用类特定的回调方法。
     * 
     * 
     * @param listener  provides unmarshal event callbacks for this {@link Unmarshaller}
     * @since JAXB2.0
     */
    public void     setListener(Listener listener);

    /**
     * <p>Return {@link Listener} registered with this {@link Unmarshaller}.
     *
     * <p>
     * <p>
     *  使用此{@link Unmarshaller}注册解除事件回调{@link Listener}。
     * 
     * <p>
     *  每个Unmarshaller只有一个侦听器。设置侦听器将替换先前设置的侦听器。可以通过将侦听器设置为<tt> null </tt>来取消注册当前侦听器。
     * 
     * @return registered {@link Listener} or <code>null</code> if no Listener is registered with this Unmarshaller.
     * @since JAXB2.0
     */
    public Listener getListener();
}
