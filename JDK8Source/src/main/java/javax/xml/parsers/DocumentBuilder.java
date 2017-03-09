/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Defines the API to obtain DOM Document instances from an XML
 * document. Using this class, an application programmer can obtain a
 * {@link Document} from XML.<p>
 *
 * An instance of this class can be obtained from the
 * {@link DocumentBuilderFactory#newDocumentBuilder()} method. Once
 * an instance of this class is obtained, XML can be parsed from a
 * variety of input sources. These input sources are InputStreams,
 * Files, URLs, and SAX InputSources.<p>
 *
 * Note that this class reuses several classes from the SAX API. This
 * does not require that the implementor of the underlying DOM
 * implementation use a SAX parser to parse XML document into a
 * <code>Document</code>. It merely requires that the implementation
 * communicate with the application using these existing APIs.
 *
 * <p>
 *  定义用于从XML文档获取DOM文档实例的API。使用这个类,应用程序员可以从XML获得{@link Document}。<p>
 * 
 *  此类的实例可以从{@link DocumentBuilderFactory#newDocumentBuilder()}方法获取。获取此类的实例后,可以从各种输入源解析XML。
 * 这些输入源是InputStreams,Files,URL和SAX InputSources。<p>。
 * 
 *  注意,这个类重用了SAX API中的几个类。这不要求底层DOM实现的实现者使用SAX解析器将XML文档解析为<code> Document </code>。
 * 它只需要实现使用这些现有的API与应用程序通信。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */

public abstract class DocumentBuilder {


    /** Protected constructor */
    protected DocumentBuilder () {
    }

    /**
     * <p>Reset this <code>DocumentBuilder</code> to its original configuration.</p>
     *
     * <p><code>DocumentBuilder</code> is reset to the same state as when it was created with
     * {@link DocumentBuilderFactory#newDocumentBuilder()}.
     * <code>reset()</code> is designed to allow the reuse of existing <code>DocumentBuilder</code>s
     * thus saving resources associated with the creation of new <code>DocumentBuilder</code>s.</p>
     *
     * <p>The reset <code>DocumentBuilder</code> is not guaranteed to have the same {@link EntityResolver} or {@link ErrorHandler}
     * <code>Object</code>s, e.g. {@link Object#equals(Object obj)}.  It is guaranteed to have a functionally equal
     * <code>EntityResolver</code> and <code>ErrorHandler</code>.</p>
     *
     * <p>
     *  <p>将此<code> DocumentBuilder </code>重置为原始配置。</p>
     * 
     *  <p> <code> DocumentBuilder </code>重置为与使用{@link DocumentBuilderFactory#newDocumentBuilder()}创建时相同的状态。
     *  <code> reset()</code>旨在允许重用现有的<code> DocumentBuilder </code>,从而节省与创建新的<code> DocumentBuilder </code>
     * 相关的资源。
     * </p>。
     * 
     * <p>重置<code> DocumentBuilder </code>不保证具有相同的{@link EntityResolver}或{@link ErrorHandler} <code> Object 
     * </code>,例如{@link Object#equals(Object obj)}。
     * 它保证有一个功能相等的<code> EntityResolver </code>和<code> ErrorHandler </code>。</p>。
     * 
     * 
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
     *
     * @since 1.5
     */
    public void reset() {

        // implementors should override this method
        throw new UnsupportedOperationException(
                "This DocumentBuilder, \"" + this.getClass().getName() + "\", does not support the reset functionality."
                + "  Specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\""
                + " version \"" + this.getClass().getPackage().getSpecificationVersion() + "\""
                );
    }

    /**
     * Parse the content of the given <code>InputStream</code> as an XML
     * document and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>InputStream</code> is null.
     *
     * <p>
     *  将给定的<code> InputStream </code>的内容解析为XML文档,并返回一个新的DOM {@link Document}对象。
     * 如果<code> InputStream </code>为null,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     *
     * @return <code>Document</code> result of parsing the
     *  <code>InputStream</code>
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>is</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     */

    public Document parse(InputStream is)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource in = new InputSource(is);
        return parse(in);
    }

    /**
     * Parse the content of the given <code>InputStream</code> as an
     * XML document and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>InputStream</code> is null.
     *
     * <p>
     *  将给定的<code> InputStream </code>的内容解析为XML文档,并返回一个新的DOM {@link Document}对象。
     * 如果<code> InputStream </code>为null,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param is InputStream containing the content to be parsed.
     * @param systemId Provide a base for resolving relative URIs.
     *
     * @return A new DOM Document object.
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>is</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     */

    public Document parse(InputStream is, String systemId)
        throws SAXException, IOException {
        if (is == null) {
            throw new IllegalArgumentException("InputStream cannot be null");
        }

        InputSource in = new InputSource(is);
        in.setSystemId(systemId);
        return parse(in);
    }

    /**
     * Parse the content of the given URI as an XML document
     * and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * URI is <code>null</code> null.
     *
     * <p>
     *  将给定URI的内容解析为XML文档,并返回一个新的DOM {@link Document}对象。
     * 如果URI是<code> null </code> null,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param uri The location of the content to be parsed.
     *
     * @return A new DOM Document object.
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>uri</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     */

    public Document parse(String uri)
        throws SAXException, IOException {
        if (uri == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }

        InputSource in = new InputSource(uri);
        return parse(in);
    }

    /**
     * Parse the content of the given file as an XML document
     * and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>File</code> is <code>null</code> null.
     *
     * <p>
     *  将给定文件的内容解析为XML文档并返回一个新的DOM {@link Document}对象。
     * 如果<code> File </code>是<code> null </code> null,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param f The file containing the XML to parse.
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>f</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     * @return A new DOM Document object.
     */

    public Document parse(File f) throws SAXException, IOException {
        if (f == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        InputSource in = new InputSource(f.toURI().toASCIIString());
        return parse(in);
    }

    /**
     * Parse the content of the given input source as an XML document
     * and return a new DOM {@link Document} object.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>InputSource</code> is <code>null</code> null.
     *
     * <p>
     *  将给定输入源的内容解析为XML文档,并返回一个新的DOM {@link Document}对象。
     * 如果<code> InputSource </code>是<code> null </code> null,则会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param is InputSource containing the content to be parsed.
     *
     * @return A new DOM Document object.
     *
     * @throws IOException If any IO errors occur.
     * @throws SAXException If any parse errors occur.
     * @throws IllegalArgumentException When <code>is</code> is <code>null</code>
     *
     * @see org.xml.sax.DocumentHandler
     */

    public abstract Document parse(InputSource is)
        throws SAXException, IOException;


    /**
     * Indicates whether or not this parser is configured to
     * understand namespaces.
     *
     * <p>
     *  指示此解析器是否配置为理解命名空间。
     * 
     * 
     * @return true if this parser is configured to understand
     *         namespaces; false otherwise.
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
     * @return true if this parser is configured to validate
     *         XML documents; false otherwise.
     */

    public abstract boolean isValidating();

    /**
     * Specify the {@link EntityResolver} to be used to resolve
     * entities present in the XML document to be parsed. Setting
     * this to <code>null</code> will result in the underlying
     * implementation using it's own default implementation and
     * behavior.
     *
     * <p>
     * 指定要用于解析呈现在要解析的XML文档中的实体的{@link EntityResolver}。将此设置为<code> null </code>会导致使用它自己的默认实现和行为的底层实现。
     * 
     * 
     * @param er The <code>EntityResolver</code> to be used to resolve entities
     *           present in the XML document to be parsed.
     */

    public abstract void setEntityResolver(EntityResolver er);

    /**
     * Specify the {@link ErrorHandler} to be used by the parser.
     * Setting this to <code>null</code> will result in the underlying
     * implementation using it's own default implementation and
     * behavior.
     *
     * <p>
     *  指定解析器要使用的{@link ErrorHandler}。将此设置为<code> null </code>会导致使用它自己的默认实现和行为的底层实现。
     * 
     * 
     * @param eh The <code>ErrorHandler</code> to be used by the parser.
     */

    public abstract void setErrorHandler(ErrorHandler eh);

    /**
     * Obtain a new instance of a DOM {@link Document} object
     * to build a DOM tree with.
     *
     * <p>
     *  获取一个DOM {@link Document}对象的新实例来构建一个DOM树。
     * 
     * 
     * @return A new instance of a DOM Document object.
     */

    public abstract Document newDocument();

    /**
     * Obtain an instance of a {@link DOMImplementation} object.
     *
     * <p>
     *  获取{@link DOMImplementation}对象的实例。
     * 
     * 
     * @return A new instance of a <code>DOMImplementation</code>.
     */

    public abstract DOMImplementation getDOMImplementation();

    /** <p>Get current state of canonicalization.</p>
     *
     * <p>
     * 
     * @return current state canonicalization control
     */
    /*
    public boolean getCanonicalization() {
        return canonicalState;
    }
    /* <p>
    /*  public boolean getCanonicalization(){return canonicalState; }}
    /* 
    */

    /** <p>Get a reference to the the {@link Schema} being used by
     * the XML processor.</p>
     *
     * <p>If no schema is being used, <code>null</code> is returned.</p>
     *
     * <p>
     *  XML处理器。</p>
     * 
     *  <p>如果未使用任何模式,则会返回<code> null </code>。</p>
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
     * 
     * @return
     *      the return value of
     *      the {@link DocumentBuilderFactory#isXIncludeAware()}
     *      when this parser was created from factory.
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method
     *
     * @since 1.5
     *
     * @see DocumentBuilderFactory#setXIncludeAware(boolean)
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
