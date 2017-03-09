/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.validation.Schema;

import org.xml.sax.HandlerBase;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Defines the API that wraps an {@link org.xml.sax.XMLReader}
 * implementation class. In JAXP 1.0, this class wrapped the
 * {@link org.xml.sax.Parser} interface, however this interface was
 * replaced by the {@link org.xml.sax.XMLReader}. For ease
 * of transition, this class continues to support the same name
 * and interface as well as supporting new methods.
 *
 * An instance of this class can be obtained from the
 * {@link javax.xml.parsers.SAXParserFactory#newSAXParser()} method.
 * Once an instance of this class is obtained, XML can be parsed from
 * a variety of input sources. These input sources are InputStreams,
 * Files, URLs, and SAX InputSources.<p>
 *
 * This static method creates a new factory instance based
 * on a system property setting or uses the platform default
 * if no property has been defined.<p>
 *
 * The system property that controls which Factory implementation
 * to create is named <code>&quot;javax.xml.parsers.SAXParserFactory&quot;</code>.
 * This property names a class that is a concrete subclass of this
 * abstract class. If no property is defined, a platform default
 * will be used.</p>
 *
 * As the content is parsed by the underlying parser, methods of the
 * given {@link org.xml.sax.HandlerBase} or the
 * {@link org.xml.sax.helpers.DefaultHandler} are called.<p>
 *
 * Implementors of this class which wrap an underlaying implementation
 * can consider using the {@link org.xml.sax.helpers.ParserAdapter}
 * class to initially adapt their SAX1 implementation to work under
 * this revised class.
 *
 * <p>
 *  定义包装{@link org.xml.sax.XMLReader}实现类的API。
 * 在JAXP 1.0中,此类包装了{@link org.xml.sax.Parser}接口,但是此接口被{@link org.xml.sax.XMLReader}替换。
 * 为了方便过渡,这个类继续支持相同的名称和接口以及支持新的方法。
 * 
 *  此类的实例可以从{@link javax.xml.parsers.SAXParserFactory#newSAXParser()}方法获取。获取此类的实例后,可以从各种输入源解析XML。
 * 这些输入源是InputStreams,Files,URL和SAX InputSources。<p>。
 * 
 *  此静态方法根据系统属性设置创建新的工厂实例,或者如果未定义属性,则使用平台默认值。<p>
 * 
 *  用于控制要创建的Factory实现的系统属性命名为<code>"javax.xml.parsers.SAXParserFactory"</code>。此属性命名一个类,该类是此抽象类的一个具体子类。
 * 如果未定义属性,将使用平台默认值。</p>。
 * 
 *  当内容由底层解析器解析时,调用给定{@link org.xml.sax.HandlerBase}或{@link org.xml.sax.helpers.DefaultHandler}的方法。<p>
 * 
 * 这个类的实现器包含一个底层实现可以考虑使用{@link org.xml.sax.helpers.ParserAdapter}类来初始调整他们的SAX1实现在这个修订的类下工作。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public abstract class SAXParser {

    /**
     * <p>Protected constructor to prevent instaniation.
     * Use {@link javax.xml.parsers.SAXParserFactory#newSAXParser()}.</p>
     * <p>
     *  <p>受保护的构造函数以防止实例化。使用{@link javax.xml.parsers.SAXParserFactory#newSAXParser()}。</p>
     * 
     */
    protected SAXParser () {

    }

        /**
         * <p>Reset this <code>SAXParser</code> to its original configuration.</p>
         *
         * <p><code>SAXParser</code> is reset to the same state as when it was created with
         * {@link SAXParserFactory#newSAXParser()}.
         * <code>reset()</code> is designed to allow the reuse of existing <code>SAXParser</code>s
         * thus saving resources associated with the creation of new <code>SAXParser</code>s.</p>
         *
         * <p>The reset <code>SAXParser</code> is not guaranteed to have the same {@link Schema}
         * <code>Object</code>, e.g. {@link Object#equals(Object obj)}.  It is guaranteed to have a functionally equal
         * <code>Schema</code>.</p>
     *
     * <p>
     *  <p>将此<code> SAXParser </code>重置为其原始配置。</p>
     * 
     *  <p> <code> SAXParser </code>被重置为与使用{@link SAXParserFactory#newSAXParser()}创建时相同的状态。
     *  <code> reset()</code>旨在允许重用现有的<code> SAXParser </code>,从而节省与创建新的<code> SAXParser </code>相关联的资源。
     * 
     *  <p>重置<code> SAXParser </code>不保证具有相同的{@link Schema} <code> Object </code>,例如{@link Object#equals(Object obj)}
     * 。
     * 它保证有一个功能相等的<code> Schema </code>。</p>。
     * 
     * 
     * @throws UnsupportedOperationException When Implementations do not
     *   override this method
         *
         * @since 1.5
         */
        public void reset() {

                // implementors should override this method
                throw new UnsupportedOperationException(
                        "This SAXParser, \"" + this.getClass().getName() + "\", does not support the reset functionality."
                        + "  Specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\""
                        + " version \"" + this.getClass().getPackage().getSpecificationVersion() + "\""
                        );
        }

    /**
     * <p>Parse the content of the given {@link java.io.InputStream}
     * instance as XML using the specified {@link org.xml.sax.HandlerBase}.
     * <i> Use of the DefaultHandler version of this method is recommended as
     * the HandlerBase class has been deprecated in SAX 2.0</i>.</p>
     *
     * <p>
     *  <p>使用指定的{@link org.xml.sax.HandlerBase}以XML格式解析给定的{@link java.io.InputStream}实例的内容。
     *  <i>建议使用此方法的DefaultHandler版本,因为HandlerBase类已在SAX 2.0 </i>中弃用。</p>。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     * @param hb The SAX HandlerBase to use.
     *
     * @throws IllegalArgumentException If the given InputStream is null.
     * @throws SAXException If parse produces a SAX error.
     * @throws IOException If an IO error occurs interacting with the
     *   <code>InputStream</code>.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(InputStream is, HandlerBase hb)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource input = new InputSource(is);
        this.parse(input, hb);
    }

    /**
     * <p>Parse the content of the given {@link java.io.InputStream}
     * instance as XML using the specified {@link org.xml.sax.HandlerBase}.
     * <i> Use of the DefaultHandler version of this method is recommended as
     * the HandlerBase class has been deprecated in SAX 2.0</i>.</p>
     *
     * <p>
     *  <p>使用指定的{@link org.xml.sax.HandlerBase}以XML格式解析给定的{@link java.io.InputStream}实例的内容。
     *  <i>建议使用此方法的DefaultHandler版本,因为HandlerBase类已在SAX 2.0 </i>中弃用。</p>。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     * @param hb The SAX HandlerBase to use.
     * @param systemId The systemId which is needed for resolving relative URIs.
     *
     * @throws IllegalArgumentException If the given <code>InputStream</code> is
     *   <code>null</code>.
     * @throws IOException If any IO error occurs interacting with the
     *   <code>InputStream</code>.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler version of this method instead.
     */
    public void parse(
        InputStream is,
        HandlerBase hb,
        String systemId)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource input = new InputSource(is);
        input.setSystemId(systemId);
        this.parse(input, hb);
    }

    /**
     * Parse the content of the given {@link java.io.InputStream}
     * instance as XML using the specified
     * {@link org.xml.sax.helpers.DefaultHandler}.
     *
     * <p>
     * 使用指定的{@link org.xml.sax.helpers.DefaultHandler}以XML格式解析给定的{@link java.io.InputStream}实例的内容。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     * @param dh The SAX DefaultHandler to use.
     *
     * @throws IllegalArgumentException If the given InputStream is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(InputStream is, DefaultHandler dh)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource input = new InputSource(is);
        this.parse(input, dh);
    }

    /**
     * Parse the content of the given {@link java.io.InputStream}
     * instance as XML using the specified
     * {@link org.xml.sax.helpers.DefaultHandler}.
     *
     * <p>
     *  使用指定的{@link org.xml.sax.helpers.DefaultHandler}以XML格式解析给定的{@link java.io.InputStream}实例的内容。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     * @param dh The SAX DefaultHandler to use.
     * @param systemId The systemId which is needed for resolving relative URIs.
     *
     * @throws IllegalArgumentException If the given InputStream is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler version of this method instead.
     */
    public void parse(
        InputStream is,
        DefaultHandler dh,
        String systemId)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource input = new InputSource(is);
        input.setSystemId(systemId);
        this.parse(input, dh);
    }

    /**
     * Parse the content described by the giving Uniform Resource
     * Identifier (URI) as XML using the specified
     * {@link org.xml.sax.HandlerBase}.
     * <i> Use of the DefaultHandler version of this method is recommended as
     * the <code>HandlerBase</code> class has been deprecated in SAX 2.0</i>
     *
     * <p>
     *  使用指定的{@link org.xml.sax.HandlerBase}将使用统一资源标识符(URI)描述的内容解析为XML。
     *  <i>建议使用此方法的DefaultHandler版本,因为<code> HandlerBase </code>类已在SAX 2.0中弃用</i>。
     * 
     * 
     * @param uri The location of the content to be parsed.
     * @param hb The SAX HandlerBase to use.
     *
     * @throws IllegalArgumentException If the uri is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(String uri, HandlerBase hb)
        throws SAXException, IOException {
        if (uri == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }

        InputSource input = new InputSource(uri);
        this.parse(input, hb);
    }

    /**
     * Parse the content described by the giving Uniform Resource
     * Identifier (URI) as XML using the specified
     * {@link org.xml.sax.helpers.DefaultHandler}.
     *
     * <p>
     *  使用指定的{@link org.xml.sax.helpers.DefaultHandler}将使用统一资源标识符(URI)描述的内容解析为XML。
     * 
     * 
     * @param uri The location of the content to be parsed.
     * @param dh The SAX DefaultHandler to use.
     *
     * @throws IllegalArgumentException If the uri is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(String uri, DefaultHandler dh)
        throws SAXException, IOException {
        if (uri == null) {
            throw new IllegalArgumentException("uri cannot be null");
        }

        InputSource input = new InputSource(uri);
        this.parse(input, dh);
    }

    /**
     * Parse the content of the file specified as XML using the
     * specified {@link org.xml.sax.HandlerBase}.
     * <i> Use of the DefaultHandler version of this method is recommended as
     * the HandlerBase class has been deprecated in SAX 2.0</i>
     *
     * <p>
     *  使用指定的{@link org.xml.sax.HandlerBase}解析指定为XML的文件的内容。
     *  <i>建议使用此方法的DefaultHandler版本,因为HandlerBase类已在SAX 2.0中弃用</i>。
     * 
     * 
     * @param f The file containing the XML to parse
     * @param hb The SAX HandlerBase to use.
     *
     * @throws IllegalArgumentException If the File object is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(File f, HandlerBase hb)
        throws SAXException, IOException {
        if (f == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        InputSource input = new InputSource(f.toURI().toASCIIString());
        this.parse(input, hb);
    }

    /**
     * Parse the content of the file specified as XML using the
     * specified {@link org.xml.sax.helpers.DefaultHandler}.
     *
     * <p>
     *  使用指定的{@link org.xml.sax.helpers.DefaultHandler}解析指定为XML的文件的内容。
     * 
     * 
     * @param f The file containing the XML to parse
     * @param dh The SAX DefaultHandler to use.
     *
     * @throws IllegalArgumentException If the File object is null.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(File f, DefaultHandler dh)
        throws SAXException, IOException {
        if (f == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        InputSource input = new InputSource(f.toURI().toASCIIString());
        this.parse(input, dh);
    }

    /**
     * Parse the content given {@link org.xml.sax.InputSource}
     * as XML using the specified
     * {@link org.xml.sax.HandlerBase}.
     * <i> Use of the DefaultHandler version of this method is recommended as
     * the HandlerBase class has been deprecated in SAX 2.0</i>
     *
     * <p>
     *  使用指定的{@link org.xml.sax.HandlerBase}以XML格式解析{@link org.xml.sax.InputSource}中提供的内容。
     *  <i>建议使用此方法的DefaultHandler版本,因为HandlerBase类已在SAX 2.0中弃用</i>。
     * 
     * 
     * @param is The InputSource containing the content to be parsed.
     * @param hb The SAX HandlerBase to use.
     *
     * @throws IllegalArgumentException If the <code>InputSource</code> object
     *   is <code>null</code>.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(InputSource is, HandlerBase hb)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputSource cannot be null");
        }

        Parser parser = this.getParser();
        if (hb != null) {
            parser.setDocumentHandler(hb);
            parser.setEntityResolver(hb);
            parser.setErrorHandler(hb);
            parser.setDTDHandler(hb);
        }
        parser.parse(is);
    }

    /**
     * Parse the content given {@link org.xml.sax.InputSource}
     * as XML using the specified
     * {@link org.xml.sax.helpers.DefaultHandler}.
     *
     * <p>
     *  使用指定的{@link org.xml.sax.helpers.DefaultHandler},以{@link org.xml.sax.InputSource}作为XML解析内容。
     * 
     * 
     * @param is The InputSource containing the content to be parsed.
     * @param dh The SAX DefaultHandler to use.
     *
     * @throws IllegalArgumentException If the <code>InputSource</code> object
     *   is <code>null</code>.
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any SAX errors occur during processing.
     *
     * @see org.xml.sax.DocumentHandler
     */
    public void parse(InputSource is, DefaultHandler dh)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputSource cannot be null");
        }

        XMLReader reader = this.getXMLReader();
        if (dh != null) {
            reader.setContentHandler(dh);
            reader.setEntityResolver(dh);
            reader.setErrorHandler(dh);
            reader.setDTDHandler(dh);
        }
        reader.parse(is);
    }

    /**
     * Returns the SAX parser that is encapsultated by the
     * implementation of this class.
     *
     * <p>
     * 返回由此类的实现封装的SAX解析器。
     * 
     * 
     * @return The SAX parser that is encapsultated by the
     *         implementation of this class.
     *
     * @throws SAXException If any SAX errors occur during processing.
     */
    public abstract org.xml.sax.Parser getParser() throws SAXException;

    /**
     * Returns the {@link org.xml.sax.XMLReader} that is encapsulated by the
     * implementation of this class.
     *
     * <p>
     *  返回由此类的实现封装的{@link org.xml.sax.XMLReader}。
     * 
     * 
     * @return The XMLReader that is encapsulated by the
     *         implementation of this class.
     *
     * @throws SAXException If any SAX errors occur during processing.
     */

    public abstract org.xml.sax.XMLReader getXMLReader() throws SAXException;

    /**
     * Indicates whether or not this parser is configured to
     * understand namespaces.
     *
     * <p>
     *  指示此解析器是否配置为理解命名空间。
     * 
     * 
     * @return true if this parser is configured to
     *         understand namespaces; false otherwise.
     */

    public abstract boolean isNamespaceAware();

    /**
     * Indicates whether or not this parser is configured to
     * validate XML documents.
     *
     * <p>
     *  指示此解析器是否配置为验证XML文档。
     * 
     * 
     * @return true if this parser is configured to
     *         validate XML documents; false otherwise.
     */

    public abstract boolean isValidating();

    /**
     * <p>Sets the particular property in the underlying implementation of
     * {@link org.xml.sax.XMLReader}.
     * A list of the core features and properties can be found at
     * <a href="http://sax.sourceforge.net/?selected=get-set">
     * http://sax.sourceforge.net/?selected=get-set</a>.</p>
     * <p>
     * All implementations that implement JAXP 1.5 or newer are required to
     * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} and
     * {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} properties.
     * </p>
     * <ul>
     *   <li>
     *      <p>
     *      Setting the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property
     *      restricts the access to external DTDs, external Entity References to
     *      the protocols specified by the property.  If access is denied during parsing
     *      due to the restriction of this property, {@link org.xml.sax.SAXException}
     *      will be thrown by the parse methods defined by {@link javax.xml.parsers.SAXParser}.
     *      </p>
     *      <p>
     *      Setting the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} property
     *      restricts the access to external Schema set by the schemaLocation attribute to
     *      the protocols specified by the property.  If access is denied during parsing
     *      due to the restriction of this property, {@link org.xml.sax.SAXException}
     *      will be thrown by the parse methods defined by the {@link javax.xml.parsers.SAXParser}.
     *      </p>
     *   </li>
     * </ul>
     *
     * <p>
     *  <p>设置{@link org.xml.sax.XMLReader}的基础实现中的特定属性。有关核心功能和属性的列表,请参见
     * <a href="http://sax.sourceforge.net/?selected=get-set">
     *  http://sax.sourceforge.net/?selected=get-set </a>。</p>
     * <p>
     *  实现JAXP 1.5或更高版本的所有实现都需要支持{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}和{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}
     * 属性。
     * </p>
     * <ul>
     * <li>
     * <p>
     *  设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性会限制对外部DTD的访问,对属性指定的协议的外部实体引用。
     * 如果由于此属性的限制而在解析期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.parsers.SAXParser}定义的解析方法抛出。
     * </p>
     * <p>
     * 设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性会将对由schemaLocation属性设置的外部模式的访问限制为属性指定的协议。
     * 如果由于此属性的限制而在解析期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.parsers.SAXParser}定义的解析方法抛出。
     * </p>
     * </li>
     * 
     * @param name The name of the property to be set.
     * @param value The value of the property to be set.
     *
     * @throws SAXNotRecognizedException When the underlying XMLReader does
     *   not recognize the property name.
     * @throws SAXNotSupportedException When the underlying XMLReader
     *  recognizes the property name but doesn't support the property.
     *
     * @see org.xml.sax.XMLReader#setProperty
     */
    public abstract void setProperty(String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * <p>Returns the particular property requested for in the underlying
     * implementation of {@link org.xml.sax.XMLReader}.</p>
     *
     * <p>
     * </ul>
     * 
     * 
     * @param name The name of the property to be retrieved.
     * @return Value of the requested property.
     *
     * @throws SAXNotRecognizedException When the underlying XMLReader does
     *    not recognize the property name.
     * @throws SAXNotSupportedException When the underlying XMLReader
     *  recognizes the property name but doesn't support the property.
     *
     * @see org.xml.sax.XMLReader#getProperty
     */
    public abstract Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /** <p>Get current state of canonicalization.</p>
     *
     * <p>
     *  <p>返回在{@link org.xml.sax.XMLReader}的基础实现中请求的特定属性。</p>
     * 
     * 
     * @return current state canonicalization control
     */
    /*
    public boolean getCanonicalization() {
        return canonicalState;
    }
    /* <p>
    */

    /** <p>Get a reference to the the {@link Schema} being used by
     * the XML processor.</p>
     *
     * <p>If no schema is being used, <code>null</code> is returned.</p>
     *
     * <p>
     *  public boolean getCanonicalization(){return canonicalState; }}
     * 
     * 
     * @return {@link Schema} being used or <code>null</code>
     *  if none in use
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method
     *
     * @since 1.5
     */
    public Schema getSchema() {
        throw new UnsupportedOperationException(
            "This parser does not support specification \""
            + this.getClass().getPackage().getSpecificationTitle()
            + "\" version \""
            + this.getClass().getPackage().getSpecificationVersion()
            + "\""
            );
    }

    /**
     * <p>Get the XInclude processing mode for this parser.</p>
     *
     * <p>
     *  XML处理器。</p>
     * 
     *  <p>如果未使用任何模式,则会返回<code> null </code>。</p>
     * 
     * 
     * @return
     *      the return value of
     *      the {@link SAXParserFactory#isXIncludeAware()}
     *      when this parser was created from factory.
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method
     *
     * @since 1.5
     *
     * @see SAXParserFactory#setXIncludeAware(boolean)
     */
    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException(
            "This parser does not support specification \""
            + this.getClass().getPackage().getSpecificationTitle()
            + "\" version \""
            + this.getClass().getPackage().getSpecificationVersion()
            + "\""
            );
    }
}
