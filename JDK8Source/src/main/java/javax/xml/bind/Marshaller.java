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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.validation.Schema;
import java.io.File;

/**
 * <p>
 * The <tt>Marshaller</tt> class is responsible for governing the process
 * of serializing Java content trees back into XML data.  It provides the basic
 * marshalling methods:
 *
 * <p>
 * <i>Assume the following setup code for all following code fragments:</i>
 * <blockquote>
 *    <pre>
 *       JAXBContext jc = JAXBContext.newInstance( "com.acme.foo" );
 *       Unmarshaller u = jc.createUnmarshaller();
 *       Object element = u.unmarshal( new File( "foo.xml" ) );
 *       Marshaller m = jc.createMarshaller();
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a File:
 * <blockquote>
 *    <pre>
 *       OutputStream os = new FileOutputStream( "nosferatu.xml" );
 *       m.marshal( element, os );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a SAX ContentHandler:
 * <blockquote>
 *    <pre>
 *       // assume MyContentHandler instanceof ContentHandler
 *       m.marshal( element, new MyContentHandler() );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a DOM Node:
 * <blockquote>
 *    <pre>
 *       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 *       dbf.setNamespaceAware(true);
 *       DocumentBuilder db = dbf.newDocumentBuilder();
 *       Document doc = db.newDocument();
 *
 *       m.marshal( element, doc );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a java.io.OutputStream:
 * <blockquote>
 *    <pre>
 *       m.marshal( element, System.out );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a java.io.Writer:
 * <blockquote>
 *    <pre>
 *       m.marshal( element, new PrintWriter( System.out ) );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.SAXResult:
 * <blockquote>
 *    <pre>
 *       // assume MyContentHandler instanceof ContentHandler
 *       SAXResult result = new SAXResult( new MyContentHandler() );
 *
 *       m.marshal( element, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.DOMResult:
 * <blockquote>
 *    <pre>
 *       DOMResult result = new DOMResult();
 *
 *       m.marshal( element, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.transform.StreamResult:
 * <blockquote>
 *    <pre>
 *       StreamResult result = new StreamResult( System.out );
 *
 *       m.marshal( element, result );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.stream.XMLStreamWriter:
 * <blockquote>
 *    <pre>
 *       XMLStreamWriter xmlStreamWriter =
 *           XMLOutputFactory.newInstance().createXMLStreamWriter( ... );
 *
 *       m.marshal( element, xmlStreamWriter );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * Marshalling to a javax.xml.stream.XMLEventWriter:
 * <blockquote>
 *    <pre>
 *       XMLEventWriter xmlEventWriter =
 *           XMLOutputFactory.newInstance().createXMLEventWriter( ... );
 *
 *       m.marshal( element, xmlEventWriter );
 *    </pre>
 * </blockquote>
 *
 * <p>
 * <a name="elementMarshalling"></a>
 * <b>Marshalling content tree rooted by a JAXB element</b><br>
 * <blockquote>
 * The first parameter of the overloaded
 * <tt>Marshaller.marshal(java.lang.Object, ...)</tt> methods must be a
 * JAXB element as computed by
 * {@link JAXBIntrospector#isElement(java.lang.Object)};
 * otherwise, a <tt>Marshaller.marshal</tt> method must throw a
 * {@link MarshalException}. There exist two mechanisms
 * to enable marshalling an instance that is not a JAXB element.
 * One method is to wrap the instance as a value of a {@link JAXBElement},
 * and pass the wrapper element as the first parameter to
 * a <tt>Marshaller.marshal</tt> method. For java to schema binding, it
 * is also possible to simply annotate the instance's class with
 * &#64;{@link XmlRootElement}.
 * </blockquote>
 *
 * <p>
 * <b>Encoding</b><br>
 * <blockquote>
 * By default, the Marshaller will use UTF-8 encoding when generating XML data
 * to a <tt>java.io.OutputStream</tt>, or a <tt>java.io.Writer</tt>.  Use the
 * {@link #setProperty(String,Object) setProperty} API to change the output
 * encoding used during these marshal operations.  Client applications are
 * expected to supply a valid character encoding name as defined in the
 * <a href="http://www.w3.org/TR/2000/REC-xml-20001006#charencoding">W3C XML 1.0
 * Recommendation</a> and supported by your Java Platform</a>.
 * </blockquote>
 *
 * <p>
 * <b>Validation and Well-Formedness</b><br>
 * <blockquote>
 * <p>
 * Client applications are not required to validate the Java content tree prior
 * to calling any of the marshal API's.  Furthermore, there is no requirement
 * that the Java content tree be valid with respect to its original schema in
 * order to marshal it back into XML data.  Different JAXB Providers will
 * support marshalling invalid Java content trees at varying levels, however
 * all JAXB Providers must be able to marshal a valid content tree back to
 * XML data.  A JAXB Provider must throw a <tt>MarshalException</tt> when it
 * is unable to complete the marshal operation due to invalid content.  Some
 * JAXB Providers will fully allow marshalling invalid content, others will fail
 * on the first validation error.
 * <p>
 * Even when schema validation is not explictly enabled for the marshal operation,
 * it is possible that certain types of validation events will be detected
 * during the operation.  Validation events will be reported to the registered
 * event handler.  If the client application has not registered an event handler
 * prior to invoking one of the marshal API's, then events will be delivered to
 * a default event handler which will terminate the marshal operation after
 * encountering the first error or fatal error. Note that for JAXB 2.0 and
 * later versions, {@link javax.xml.bind.helpers.DefaultValidationEventHandler} is
 * no longer used.
 *
 * </blockquote>
 *
 * <p>
 * <a name="supportedProps"></a>
 * <b>Supported Properties</b><br>
 * <blockquote>
 * <p>
 * All JAXB Providers are required to support the following set of properties.
 * Some providers may support additional properties.
 * <dl>
 *   <dt><tt>jaxb.encoding</tt> - value must be a java.lang.String</dt>
 *   <dd>The output encoding to use when marshalling the XML data.  The
 *               Marshaller will use "UTF-8" by default if this property is not
 *       specified.</dd>
 *   <dt><tt>jaxb.formatted.output</tt> - value must be a java.lang.Boolean</dt>
 *   <dd>This property controls whether or not the Marshaller will format
 *       the resulting XML data with line breaks and indentation.  A
 *       true value for this property indicates human readable indented
 *       xml data, while a false value indicates unformatted xml data.
 *       The Marshaller will default to false (unformatted) if this
 *       property is not specified.</dd>
 *   <dt><tt>jaxb.schemaLocation</tt> - value must be a java.lang.String</dt>
 *   <dd>This property allows the client application to specify an
 *       xsi:schemaLocation attribute in the generated XML data.  The format of
 *       the schemaLocation attribute value is discussed in an easy to
 *       understand, non-normative form in
 *       <a href="http://www.w3.org/TR/xmlschema-0/#schemaLocation">Section 5.6
 *       of the W3C XML Schema Part 0: Primer</a> and specified in
 *       <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 *       Section 2.6 of the W3C XML Schema Part 1: Structures</a>.</dd>
 *   <dt><tt>jaxb.noNamespaceSchemaLocation</tt> - value must be a java.lang.String</dt>
 *   <dd>This property allows the client application to specify an
 *       xsi:noNamespaceSchemaLocation attribute in the generated XML
 *       data.  The format of the schemaLocation attribute value is discussed in
 *       an easy to understand, non-normative form in
 *       <a href="http://www.w3.org/TR/xmlschema-0/#schemaLocation">Section 5.6
 *       of the W3C XML Schema Part 0: Primer</a> and specified in
 *       <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 *       Section 2.6 of the W3C XML Schema Part 1: Structures</a>.</dd>
 *   <dt><tt>jaxb.fragment</tt> - value must be a java.lang.Boolean</dt>
 *   <dd>This property determines whether or not document level events will be
 *       generated by the Marshaller.  If the property is not specified, the
 *       default is <tt>false</tt>. This property has different implications depending
 *       on which marshal api you are using - when this property is set to true:<br>
 *       <ul>
 *         <li>{@link #marshal(Object,org.xml.sax.ContentHandler) marshal(Object,ContentHandler)} - the Marshaller won't
 *             invoke {@link org.xml.sax.ContentHandler#startDocument()} and
 *             {@link org.xml.sax.ContentHandler#endDocument()}.</li>
 *         <li>{@link #marshal(Object,org.w3c.dom.Node) marshal(Object,Node)} - the property has no effect on this
 *             API.</li>
 *         <li>{@link #marshal(Object,java.io.OutputStream) marshal(Object,OutputStream)} - the Marshaller won't
 *             generate an xml declaration.</li>
 *         <li>{@link #marshal(Object,java.io.Writer) marshal(Object,Writer)} - the Marshaller won't
 *             generate an xml declaration.</li>
 *         <li>{@link #marshal(Object,javax.xml.transform.Result) marshal(Object,Result)} - depends on the kind of
 *             Result object, see semantics for Node, ContentHandler, and Stream APIs</li>
 *         <li>{@link #marshal(Object,javax.xml.stream.XMLEventWriter) marshal(Object,XMLEventWriter)} - the
 *             Marshaller will not generate {@link javax.xml.stream.events.XMLEvent#START_DOCUMENT} and
 *             {@link javax.xml.stream.events.XMLEvent#END_DOCUMENT} events.</li>
 *         <li>{@link #marshal(Object,javax.xml.stream.XMLStreamWriter) marshal(Object,XMLStreamWriter)} - the
 *             Marshaller will not generate {@link javax.xml.stream.events.XMLEvent#START_DOCUMENT} and
 *             {@link javax.xml.stream.events.XMLEvent#END_DOCUMENT} events.</li>
 *       </ul>
 *   </dd>
 * </dl>
 * </blockquote>
 *
 * <p>
 * <a name="marshalEventCallback"></a>
 * <b>Marshal Event Callbacks</b><br>
 * <blockquote>
 * "The {@link Marshaller} provides two styles of callback mechanisms
 * that allow application specific processing during key points in the
 * unmarshalling process.  In 'class defined' event callbacks, application
 * specific code placed in JAXB mapped classes is triggered during
 * marshalling.  'External listeners' allow for centralized processing
 * of marshal events in one callback method rather than by type event callbacks.
 *
 * <p>
 * Class defined event callback methods allow any JAXB mapped class to specify
 * its own specific callback methods by defining methods with the following method signatures:
 * <blockquote>
 * <pre>
 *   // Invoked by Marshaller after it has created an instance of this object.
 *   boolean beforeMarshal(Marshaller);
 *
 *   // Invoked by Marshaller after it has marshalled all properties of this object.
 *   void afterMarshal(Marshaller);
 * </pre>
 * </blockquote>
 * The class defined event callback methods should be used when the callback method requires
 * access to non-public methods and/or fields of the class.
 * <p>
 * The external listener callback mechanism enables the registration of a {@link Listener}
 * instance with a {@link Marshaller#setListener(Listener)}. The external listener receives all callback events,
 * allowing for more centralized processing than per class defined callback methods.
 * <p>
 * The 'class defined' and external listener event callback methods are independent of each other,
 * both can be called for one event. The invocation ordering when both listener callback methods exist is
 * defined in {@link Listener#beforeMarshal(Object)} and {@link Listener#afterMarshal(Object)}.
 * <p>
 * An event callback method throwing an exception terminates the current marshal process.
 * </blockquote>
 *
 * <p>
 * <p>
 *  <tt> Marshaller </tt>类负责管理将Java内容树序列化回XML数据的过程。它提供了基本的编组方法：
 * 
 * <p>
 *  <i>假设以下所有代码片段的设置代码如下：</i>
 * <blockquote>
 * <pre>
 *  JAXBContext jc = JAXBContext.newInstance("com.acme.foo"); Unmarshaller u = jc.createUnmarshaller(); 
 * Object element = u.unmarshal(new File("foo.xml")); Marshaller m = jc.createMarshaller();。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到文件：
 * <blockquote>
 * <pre>
 *  OutputStream os = new FileOutputStream("nosferatu.xml");元素(元素,os);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到SAX ContentHandler：
 * <blockquote>
 * <pre>
 *  //假设MyContentHandler instanceof ContentHandler m.marshal(element,new MyContentHandler());
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到DOM节点：
 * <blockquote>
 * <pre>
 *  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); dbf.setNamespaceAware(true); Docu
 * mentBuilder db = dbf.newDocumentBuilder();文档doc = db.newDocument();。
 * 
 *  m.marshal(element,doc);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到java.io.OutputStream：
 * <blockquote>
 * <pre>
 *  m.marshal(element,System.out);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到java.io.Writer：
 * <blockquote>
 * <pre>
 *  m.marshal(element,new PrintWriter(System.out));
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到javax.xml.transform.SAXResult：
 * <blockquote>
 * <pre>
 *  //假设MyContentHandler instanceof ContentHandler SAXResult result = new SAXResult(new MyContentHandler
 * ());。
 * 
 *  m.marshal(element,result);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到javax.xml.transform.DOMResult：
 * <blockquote>
 * <pre>
 * DOMResult result = new DOMResult();
 * 
 *  m.marshal(element,result);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到javax.xml.transform.StreamResult：
 * <blockquote>
 * <pre>
 *  StreamResult result = new StreamResult(System.out);
 * 
 *  m.marshal(element,result);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到javax.xml.stream.XMLStreamWriter：
 * <blockquote>
 * <pre>
 *  XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newInstance()。createXMLStreamWriter(...);
 * 
 *  m.marshal(element,xmlStreamWriter);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  编组到javax.xml.stream.XMLEventWriter：
 * <blockquote>
 * <pre>
 *  XMLEventWriter xmlEventWriter = XMLOutputFactory.newInstance()。createXMLEventWriter(...);
 * 
 *  m.marshal(element,xmlEventWriter);
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  <a name="elementMarshalling"> </a> <b>编排由JAXB元素创建的内容树</b> <br>
 * <blockquote>
 *  重载的<tt> Marshaller.marshal(java.lang.Object,...)</tt>方法的第一个参数必须是由{@link JAXBIntrospector#isElement(java.lang.Object)}
 * 计算的JAXB元素;否则,<tt> Marshaller.marshal </tt>方法必须抛出{@link MarshalException}。
 * 存在两种机制来启用编组不是JAXB元素的实例。
 * 一个方法是将实例封装为{@link JAXBElement}的值,并将wrapper元素作为第一个参数传递给<tt> Marshaller.marshal </tt>方法。
 * 对于java到模式绑定,也可以使用@ {@ link XmlRootElement}简单地注释实例的类。
 * </blockquote>
 * 
 * <p>
 *  <b>编码</b> <br>
 * <blockquote>
 * 默认情况下,Marshaller在向<tt> java.io.OutputStream </tt>或<tt> java.io.Writer </tt>生成XML数据时将使用UTF-8编码。
 * 使用{@link #setProperty(String,Object)setProperty} API更改在这些元组操作期间使用的输出编码。
 * 客户端应用程序应提供有效的字符编码名称,如<a href="http://www.w3.org/TR/2000/REC-xml-20001006#charencoding"> W3C XML 1.0建议
 * 书</a >并由您的Java平台</a>支持。
 * 使用{@link #setProperty(String,Object)setProperty} API更改在这些元组操作期间使用的输出编码。
 * </blockquote>
 * 
 * <p>
 *  <b>验证和成功</b> <br>
 * <blockquote>
 * <p>
 *  客户端应用程序在调用任何元素API之前不需要验证Java内容树。此外,不要求Java内容树相对于其原始模式是有效的,以便将其重新组合成XML数据。
 * 不同的JAXB提供程序将支持在不同级别编排无效的Java内容树,但是所有JAXB提供程序必须能够将有效的内容树编组回XML数据。
 * 当JAXB提供程序由于无效内容而无法完成元数据操作时,必须抛出<tt> MarshalException </tt>。一些JAXB提供程序将完全允许编组无效内容,其他的会在第一个验证错误时失败。
 * <p>
 * 即使当模式验证没有显式地为元组操作启用时,也可能在操作期间检测到某些类型的验证事件。验证事件将报告给已注册的事件处理程序。
 * 如果客户端应用程序在调用元素API之一之前没有注册事件处理程序,则事件将被传递到默认事件处理程序,在遇到第一个错误或致命错误后,将终止元组操作。
 * 请注意,对于JAXB 2.0和更高版本,不再使用{@link javax.xml.bind.helpers.DefaultValidationEventHandler}。
 * 
 * </blockquote>
 * 
 * <p>
 *  <a name="supportedProps"> </a> <b>支持的属性</b> <br>
 * <blockquote>
 * <p>
 *  所有JAXB提供程序都必须支持以下属性集。一些提供程序可能支持其他属性。
 * <dl>
 * 
 * <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 * W3C XML Schema第1部分：结构</a>的第2.6节。
 * </dd> <dt> <tt> jaxb.noNamespaceSchemaLocation </tt>  - 值必须是java.lang.String </dt> <dd>此属性允许客户端应用程序在生
 * 成的XML数据中指定xsi：noNamespaceSchemaLocation属性。
 * W3C XML Schema第1部分：结构</a>的第2.6节。
 * 在<a href="http://www.w3.org/TR/xmlschema-0/#schemaLocation"> W3C的第5.6节中,以易于理解的非规范形式讨论了schemaLocation属
 * 性值的格式XML Schema Part 0：Primer </a>并在中指定。
 * W3C XML Schema第1部分：结构</a>的第2.6节。
 * <a href="http://www.w3.org/TR/xmlschema-1/#Instance_Document_Constructions">
 *  W3C XML Schema第1部分：结构</a>的第2.6节。
 * </dd> <dt> <tt> jaxb.fragment </tt>  - 值必须是java.lang.Boolean </dt> <dd>此属性确定Marshaller是否将生成文档级别事件。
 * 如果未指定属性,则默认值为<tt> false </tt>。此属性具有不同的含义,具体取决于您使用的marshal api  - 当此属性设置为true时：<br>。
 * <ul>
 * <li> {@ link #marshal(Object,org.xml.sax.ContentHandler)marshal(Object,ContentHandler)}  -  Marshalle
 * r不会调用{@link org.xml.sax.ContentHandler#startDocument()}和{ @link org.xml.sax.ContentHandler#endDocument()}
 * 。
 * </li> <li> {@ link #marshal(Object,org.w3c.dom.Node)marshal(Object,Node)}  - </li> <li> {@ link #marshal(Object,java.io.OutputStream)marshal(Object,OutputStream)}
 *   -  Marshaller不会生成xml声明。
 * </li> <li > {@ link #marshal(Object,java.io.Writer)marshal(Object,Writer)}  -  Marshaller不会生成一个xml声明。
 * </li> <li> {@ link #marshal(Object,javax。
 *  xml.transform.Result)marshal(Object,Result)}  - 取决于Result对象的类型,请参阅Node,ContentHandler和Stream API的语义。
 * </li> <li> {@ link #marshal(Object,javax。
 * </li> <li> {@ link #marshal(Object,javax.xml .stream.XMLEventWriter)marshal(Object,XMLEventWriter)}  
 * -  Marshaller不会生成{@link javax.xml.stream.events.XMLEvent#START_DOCUMENT}和{@link javax.xml.stream.events.XMLEvent#END_DOCUMENT}
 * 个事件。
 * </li> <li> {@ link #marshal(Object,javax。
 * </li> <li> {@ link #marshal(Object,javax.xml.stream.XMLStreamWriter)marshal(Object,XMLStreamWriter)} 
 *  -  Marshaller不会生成{@link javax.xml.stream.events.XMLEvent# START_DOCUMENT}和{@link javax.xml.stream.events.XMLEvent#END_DOCUMENT}
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see JAXBContext
 * @see Validator
 * @see Unmarshaller
 * @since JAXB1.0
 */
public interface Marshaller {

    /**
     * The name of the property used to specify the output encoding in
     * the marshalled XML data.
     * <p>
     * 个活动。
     * </li> <li> {@ link #marshal(Object,javax。</li>。
     * </ul>
     * </dd>
     * </dl>
     * </blockquote>
     * 
     * <p>
     *  <a name="marshalEventCallback"> </a> <b>元数事件回调</b> <br>
     * <blockquote>
     * "{@link Marshaller}提供了两种类型的回调机制,允许在解组过程的关键点处进行应用程序特定的处理。在"类定义"事件回调中,放置在JAXB映射类中的应用程序特定代码在编组期间被触发。
     *  '允许在一个回调方法中集中处理元数据事件,而不是通过类型事件回调。
     * 
     * <p>
     *  类定义的事件回调方法允许任何JAXB映射类通过使用以下方法签名定义方法来指定自己的特定回调方法：
     * <blockquote>
     * <pre>
     *  //由Marshaller在创建此对象的实例后调用。 boolean beforeMarshal(Marshaller);
     * 
     *  //在Marshaller调度此对象的所有属性后调用。 void afterMarshal(Marshaller);
     * </pre>
     * </blockquote>
     *  当回调方法需要访问非公共方法和/或类的字段时,应使用类定义的事件回调方法。
     * <p>
     *  外部侦听器回调机制允许使用{@link Marshaller#setListener(Listener)}注册{@link Listener}实例。
     * 外部监听器接收所有回调事件,允许比每个类定义的回调方法更集中的处理。
     * <p>
     * '类定义'和外部监听器事件回调方法是彼此独立的,两者都可以调用一个事件。
     * 当两个侦听器回调方法都存在时,调用顺序在{@link Listener#beforeMarshal(Object)}和{@link Listener#afterMarshal(Object)}中定义。
     * <p>
     *  抛出异常的事件回调方法会终止当前的元数据流程。
     * </blockquote>
     * 
     */
    public static final String JAXB_ENCODING =
        "jaxb.encoding";

    /**
     * The name of the property used to specify whether or not the marshalled
     * XML data is formatted with linefeeds and indentation.
     * <p>
     *  用于在经编组的XML数据中指定输出编码的属性的名称。
     * 
     */
    public static final String JAXB_FORMATTED_OUTPUT =
        "jaxb.formatted.output";

    /**
     * The name of the property used to specify the xsi:schemaLocation
     * attribute value to place in the marshalled XML output.
     * <p>
     *  用于指定经整理的XML数据是否使用换行和缩进格式化的属性名称。
     * 
     */
    public static final String JAXB_SCHEMA_LOCATION =
        "jaxb.schemaLocation";

    /**
     * The name of the property used to specify the
     * xsi:noNamespaceSchemaLocation attribute value to place in the marshalled
     * XML output.
     * <p>
     *  用于指定要放入已编组XML输出中的xsi：schemaLocation属性值的属性名称。
     * 
     */
    public static final String JAXB_NO_NAMESPACE_SCHEMA_LOCATION =
        "jaxb.noNamespaceSchemaLocation";

    /**
     * The name of the property used to specify whether or not the marshaller
     * will generate document level events (ie calling startDocument or endDocument).
     * <p>
     *  用于指定要放入经编组的XML输出中的xsi：noNamespaceSchemaLocation属性值的属性的名称。
     * 
     */
    public static final String JAXB_FRAGMENT =
        "jaxb.fragment";

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into the specified
     * <tt>javax.xml.transform.Result</tt>.
     *
     * <p>
     * All JAXB Providers must at least support
     * {@link javax.xml.transform.dom.DOMResult},
     * {@link javax.xml.transform.sax.SAXResult}, and
     * {@link javax.xml.transform.stream.StreamResult}. It can
     * support other derived classes of <tt>Result</tt> as well.
     *
     * <p>
     *  用于指定编组者是否将生成文档级事件(即调用startDocument或endDocument)的属性的名称。
     * 
     * 
     * @param jaxbElement
     *      The root of content tree to be marshalled.
     * @param result
     *      XML will be sent to this Result
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object jaxbElement, javax.xml.transform.Result result )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into an output stream.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组到指定的<tt> javax.xml.transform.Result </tt>中。
     * 
     * <p>
     *  所有JAXB提供商必须至少支持{@link javax.xml.transform.dom.DOMResult},{@link javax.xml.transform.sax.SAXResult}和{@link javax.xml.transform.stream.StreamResult}
     * 。
     * 它还可以支持<tt> Result </tt>的其他派生类。
     * 
     * 
     * @param jaxbElement
     *      The root of content tree to be marshalled.
     * @param os
     *      XML will be added to this stream.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object jaxbElement, java.io.OutputStream os )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into a file.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组到输出流中。
     * 
     * 
     * @param jaxbElement
     *      The root of content tree to be marshalled.
     * @param output
     *      File to be written. If this file already exists, it will be overwritten.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     * @since JAXB2.1
     */
    public void marshal( Object jaxbElement, File output )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into a Writer.
     *
     * <p>
     * 将根源于<tt> jaxbElement </tt>的内容树编组到文件中。
     * 
     * 
     * @param jaxbElement
     *      The root of content tree to be marshalled.
     * @param writer
     *      XML will be sent to this writer.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object jaxbElement, java.io.Writer writer )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into SAX2 events.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组到Writer中。
     * 
     * 
     * @param jaxbElement
     *      The root of content tree to be marshalled.
     * @param handler
     *      XML will be sent to this handler as SAX2 events.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object jaxbElement, org.xml.sax.ContentHandler handler )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into a DOM tree.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树归入SAX2事件。
     * 
     * 
     * @param jaxbElement
     *      The content tree to be marshalled.
     * @param node
     *      DOM nodes will be added as children of this node.
     *      This parameter must be a Node that accepts children
     *      ({@link org.w3c.dom.Document},
     *      {@link  org.w3c.dom.DocumentFragment}, or
     *      {@link  org.w3c.dom.Element})
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>jaxbElement</tt> (or any
     *      object reachable from <tt>jaxbElement</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     */
    public void marshal( Object jaxbElement, org.w3c.dom.Node node )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into a
     * {@link javax.xml.stream.XMLStreamWriter}.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组到DOM树中。
     * 
     * 
     * @param jaxbElement
     *      The content tree to be marshalled.
     * @param writer
     *      XML will be sent to this writer.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     * @since JAXB 2.0
     */
    public void marshal( Object jaxbElement, javax.xml.stream.XMLStreamWriter writer )
        throws JAXBException;

    /**
     * Marshal the content tree rooted at <tt>jaxbElement</tt> into a
     * {@link javax.xml.stream.XMLEventWriter}.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组为{@link javax.xml.stream.XMLStreamWriter}。
     * 
     * 
     * @param jaxbElement
     *      The content tree rooted at jaxbElement to be marshalled.
     * @param writer
     *      XML will be sent to this writer.
     *
     * @throws JAXBException
     *      If any unexpected problem occurs during the marshalling.
     * @throws MarshalException
     *      If the {@link ValidationEventHandler ValidationEventHandler}
     *      returns false from its <tt>handleEvent</tt> method or the
     *      <tt>Marshaller</tt> is unable to marshal <tt>obj</tt> (or any
     *      object reachable from <tt>obj</tt>).  See <a href="#elementMarshalling">
     *      Marshalling a JAXB element</a>.
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     * @since JAXB 2.0
     */
    public void marshal( Object jaxbElement, javax.xml.stream.XMLEventWriter writer )
        throws JAXBException;

    /**
     * Get a DOM tree view of the content tree(Optional).
     *
     * If the returned DOM tree is updated, these changes are also
     * visible in the content tree.
     * Use {@link #marshal(Object, org.w3c.dom.Node)} to force
     * a deep copy of the content tree to a DOM representation.
     *
     * <p>
     *  将根源于<tt> jaxbElement </tt>的内容树编组到{@link javax.xml.stream.XMLEventWriter}中。
     * 
     * 
     * @param contentTree - JAXB Java representation of XML content
     *
     * @return the DOM tree view of the contentTree
     *
     * @throws UnsupportedOperationException
     *      If the JAXB provider implementation does not support a
     *      DOM view of the content tree
     *
     * @throws IllegalArgumentException
     *      If any of the method parameters are null
     *
     * @throws JAXBException
     *      If any unexpected problem occurs
     *
     */
    public org.w3c.dom.Node getNode( java.lang.Object contentTree )
        throws JAXBException;

    /**
     * Set the particular property in the underlying implementation of
     * <tt>Marshaller</tt>.  This method can only be used to set one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to set an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     *  获取内容树的DOM树视图(可选)。
     * 
     *  如果更新了返回的DOM树,这些更改也会在内容树中可见。使用{@link #marshal(Object,org.w3c.dom.Node)}强制将内容树的深层副本转换为DOM表示。
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
     * <tt>Marshaller</tt>.  This method can only be used to get one of
     * the standard JAXB defined properties above or a provider specific
     * property.  Attempting to get an undefined property will result in
     * a PropertyException being thrown.  See <a href="#supportedProps">
     * Supported Properties</a>.
     *
     * <p>
     *  在<tt> Marshaller </tt>的底层实现中设置特定属性。此方法只能用于设置上面的标准JAXB定义属性或特定于提供程序的属性。
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
     * Allow an application to register a validation event handler.
     * <p>
     * The validation event handler will be called by the JAXB Provider if any
     * validation errors are encountered during calls to any of the marshal
     * API's.  If the client application does not register a validation event
     * handler before invoking one of the marshal methods, then validation
     * events will be handled by the default event handler which will terminate
     * the marshal operation after the first error or fatal error is encountered.
     * <p>
     * Calling this method with a null parameter will cause the Marshaller
     * to revert back to the default default event handler.
     *
     * <p>
     *  获取<tt> Marshaller </tt>的基础实现中的特定属性。此方法只能用于获取上面标准的JAXB定义的属性或特定于提供程序的属性。
     * 尝试获取未定义的属性将导致PropertyException被抛出。请参见<a href="#supportedProps">支持的属性</a>。
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
     * 允许应用程序注册验证事件处理程序。
     * <p>
     *  如果在调用任何marshal API的过程中遇到任何验证错误,JAXB Provider将调用验证事件处理程序。
     * 如果客户端应用程序在调用某个元组方法之前未注册验证事件处理程序,则验证事件将由默认事件处理程序处理,这将在遇到第一个错误或致命错误后终止元组操作。
     * <p>
     *  使用null参数调用此方法将导致Marshaller恢复为默认的默认事件处理程序。
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
     * Associates a configured instance of {@link XmlAdapter} with this marshaller.
     *
     * <p>
     * This is a convenience method that invokes <code>setAdapter(adapter.getClass(),adapter);</code>.
     *
     * <p>
     *  返回当前事件处理程序或默认事件处理程序(如果尚未设置)。
     * 
     * 
     * @see #setAdapter(Class,XmlAdapter)
     * @throws IllegalArgumentException
     *      if the adapter parameter is null.
     * @throws UnsupportedOperationException
     *      if invoked agains a JAXB 1.0 implementation.
     * @since JAXB 2.0
     */
    public void setAdapter( XmlAdapter adapter );

    /**
     * Associates a configured instance of {@link XmlAdapter} with this marshaller.
     *
     * <p>
     * Every marshaller internally maintains a
     * {@link java.util.Map}&lt;{@link Class},{@link XmlAdapter}>,
     * which it uses for marshalling classes whose fields/methods are annotated
     * with {@link javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter}.
     *
     * <p>
     * This method allows applications to use a configured instance of {@link XmlAdapter}.
     * When an instance of an adapter is not given, a marshaller will create
     * one by invoking its default constructor.
     *
     * <p>
     *  将配置的{@link XmlAdapter}实例与此编组相关联。
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
     * @since JAXB 2.0
     */
    public <A extends XmlAdapter> void setAdapter( Class<A> type, A adapter );

    /**
     * Gets the adapter associated with the specified type.
     *
     * This is the reverse operation of the {@link #setAdapter} method.
     *
     * <p>
     *  将配置的{@link XmlAdapter}实例与此编组相关联。
     * 
     * <p>
     *  每个编组器在内部维护一个{@link java.util.Map}&lt; {@ link Class},{@ link XmlAdapter}>,它用于编组类的字段/方法用{@link javax.xml.bind .annotation.adapters.XmlJavaTypeAdapter}
     * 。
     * 
     * <p>
     *  此方法允许应用程序使用配置的{@link XmlAdapter}实例。当未给出适配器的实例时,编组器将通过调用其默认构造函数来创建一个实例。
     * 
     * 
     * @throws IllegalArgumentException
     *      if the type parameter is null.
     * @throws UnsupportedOperationException
     *      if invoked agains a JAXB 1.0 implementation.
     * @since JAXB 2.0
     */
    public <A extends XmlAdapter> A getAdapter( Class<A> type );


    /**
     * <p>Associate a context that enables binary data within an XML document
     * to be transmitted as XML-binary optimized attachment.
     * The attachment is referenced from the XML document content model
     * by content-id URIs(cid) references stored within the xml document.
     *
     * <p>
     *  获取与指定类型关联的适配器。
     * 
     * 这是{@link #setAdapter}方法的逆操作。
     * 
     * 
     * @throws IllegalStateException if attempt to concurrently call this
     *                               method during a marshal operation.
     */
    void setAttachmentMarshaller(AttachmentMarshaller am);

    AttachmentMarshaller getAttachmentMarshaller();

    /**
     * Specify the JAXP 1.3 {@link javax.xml.validation.Schema Schema}
     * object that should be used to validate subsequent marshal operations
     * against.  Passing null into this method will disable validation.
     *
     * <p>
     * This method allows the caller to validate the marshalled XML as it's marshalled.
     *
     * <p>
     * Initially this property is set to <tt>null</tt>.
     *
     * <p>
     *  <p>关联使XML文档中的二进制数据能够作为XML二进制优化附件传输的上下文。附件是通过存储在xml文档中的content-id URIs(cid)引用从XML文档内容模型引用的。
     * 
     * 
     * @param schema Schema object to validate marshal operations against or null to disable validation
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Marshaller created from a JAXBContext referencing
     *         JAXB 1.0 mapped classes
     * @since JAXB2.0
     */
    public void setSchema( Schema schema );

    /**
     * Get the JAXP 1.3 {@link javax.xml.validation.Schema Schema} object
     * being used to perform marshal-time validation.  If there is no
     * Schema set on the marshaller, then this method will return null
     * indicating that marshal-time validation will not be performed.
     *
     * <p>
     *  指定应用于验证后续的marshal操作的JAXP 1.3 {@link javax.xml.validation.Schema Schema}对象。将null传递到此方法将禁用验证。
     * 
     * <p>
     *  此方法允许调用者在编组的XML被编组时验证它。
     * 
     * <p>
     *  最初,此属性设置为<tt> null </tt>。
     * 
     * 
     * @return the Schema object being used to perform marshal-time
     *      validation or null if not present.
     * @throws UnsupportedOperationException could be thrown if this method is
     *         invoked on an Marshaller created from a JAXBContext referencing
     *         JAXB 1.0 mapped classes
     * @since JAXB2.0
     */
    public Schema getSchema();

    /**
     * <p/>
     * Register an instance of an implementation of this class with a {@link Marshaller} to externally listen
     * for marshal events.
     * <p/>
     * <p/>
     * This class enables pre and post processing of each marshalled object.
     * The event callbacks are called when marshalling from an instance that maps to an xml element or
     * complex type definition. The event callbacks are not called when marshalling from an instance of a
     * Java datatype that represents a simple type definition.
     * <p/>
     * <p/>
     * External listener is one of two different mechanisms for defining marshal event callbacks.
     * See <a href="Marshaller.html#marshalEventCallback">Marshal Event Callbacks</a> for an overview.
     *
     * <p>
     *  获取用于执行元帅时间验证的JAXP 1.3 {@link javax.xml.validation.Schema Schema}对象。
     * 如果在编组器上没有设置模式,那么这个方法将返回null,表示不会执行marshal时间验证。
     * 
     * 
     * @see Marshaller#setListener(Listener)
     * @see Marshaller#getListener()
     * @since JAXB2.0
     */
    public static abstract class Listener {
        /**
         * <p/>
         * Callback method invoked before marshalling from <tt>source</tt> to XML.
         * <p/>
         * <p/>
         * This method is invoked just before marshalling process starts to marshal <tt>source</tt>.
         * Note that if the class of <tt>source</tt> defines its own <tt>beforeMarshal</tt> method,
         * the class specific callback method is invoked just before this method is invoked.
         *
         * <p>
         * <p/>
         *  使用{@link Marshaller}注册此类的实现的实例,以从外部侦听事件。
         * <p/>
         * <p/>
         *  此类允许对每个编组对象进行预处理和后处理。当从映射到xml元素或复杂类型定义的实例进行编组时,将调用事件回调。从表示简单类型定义的Java数据类型的实例编组时,不会调用事件回调。
         * <p/>
         * <p/>
         * 外部监听器是定义元组事件回调的两种不同机制之一。有关概述,请参见<a href="Marshaller.html#marshalEventCallback"> Marshal事件回调</a>。
         * 
         * 
         * @param source instance of JAXB mapped class prior to marshalling from it.
         */
        public void beforeMarshal(Object source) {
        }

        /**
         * <p/>
         * Callback method invoked after marshalling <tt>source</tt> to XML.
         * <p/>
         * <p/>
         * This method is invoked after <tt>source</tt> and all its descendants have been marshalled.
         * Note that if the class of <tt>source</tt> defines its own <tt>afterMarshal</tt> method,
         * the class specific callback method is invoked just before this method is invoked.
         *
         * <p>
         * <p/>
         *  回调方法在从<tt>源</tt>编组到XML之前调用。
         * <p/>
         * <p/>
         *  此方法在调度过程开始前调用<tt>源</tt>时调用。
         * 请注意,如果<tt> source </tt>的类定义了自己的<tt> beforeMarshal </tt>方法,则在调用该方法之前将调用类特定的回调方法。
         * 
         * 
         * @param source instance of JAXB mapped class after marshalling it.
         */
        public void afterMarshal(Object source) {
        }
    }

    /**
     * <p>
     * Register marshal event callback {@link Listener} with this {@link Marshaller}.
     *
     * <p>
     * There is only one Listener per Marshaller. Setting a Listener replaces the previous set Listener.
     * One can unregister current Listener by setting listener to <tt>null</tt>.
     *
     * <p>
     * <p/>
     *  在将<tt>源</tt>编入XML之后调用的回调方法。
     * <p/>
     * <p/>
     *  此方法在<tt>源</tt>之后调用,并且其所有后代已经编组。
     * 请注意,如果<tt> source </tt>的类定义了自己的<tt> afterMarshal </tt>方法,则在调用此方法之前将调用类特定的回调方法。
     * 
     * 
     * @param listener an instance of a class that implements {@link Listener}
     * @since JAXB2.0
     */
    public void setListener(Listener listener);

    /**
     * <p>Return {@link Listener} registered with this {@link Marshaller}.
     *
     * <p>
     * <p>
     *  使用此{@link Marshaller}注册元事件回调{@link Listener}。
     * 
     * <p>
     *  每个Marshaller只有一个Listener。设置侦听器将替换先前设置的侦听器。可以通过将侦听器设置为<tt> null </tt>来取消注册当前侦听器。
     * 
     * @return registered {@link Listener} or <code>null</code> if no Listener is registered with this Marshaller.
     * @since JAXB2.0
     */
    public Listener getListener();
}
