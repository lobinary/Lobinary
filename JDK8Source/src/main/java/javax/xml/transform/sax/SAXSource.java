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

package javax.xml.transform.sax;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * <p>Acts as an holder for SAX-style Source.</p>
 *
 * <p>Note that XSLT requires namespace support. Attempting to transform an
 * input source that is not
 * generated with a namespace-aware parser may result in errors.
 * Parsers can be made namespace aware by calling the
 * {@link javax.xml.parsers.SAXParserFactory#setNamespaceAware(boolean awareness)} method.</p>
 *
 * <p>
 *  <p>充当SAX式来源的持有人。</p>
 * 
 *  <p>请注意,XSLT需要命名空间支持。尝试转换不是使用命名空间感知解析器生成的输入源可能会导致错误。
 * 解析器可以通过调用{@link javax.xml.parsers.SAXParserFactory#setNamespaceAware(boolean awareness)}方法使命名空间感知。
 * </p>。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public class SAXSource implements Source {

    /**
     * If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns true when passed this value as an argument,
     * the Transformer supports Source input of this type.
     * <p>
     *  如果{@link javax.xml.transform.TransformerFactory#getFeature}在将此值作为参数传递时返回true,则Transformer支持此类型的Sourc
     * e输入。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.sax.SAXSource/feature";

    /**
     * <p>Zero-argument default constructor.  If this constructor is used, and
     * no SAX source is set using
     * {@link #setInputSource(InputSource inputSource)} , then the
     * <code>Transformer</code> will
     * create an empty source {@link org.xml.sax.InputSource} using
     * {@link org.xml.sax.InputSource#InputSource() new InputSource()}.</p>
     *
     * <p>
     *  <p>零参数默认构造函数。
     * 如果使用这个构造函数,并且没有使用{@link #setInputSource(InputSource inputSource)}设置SAX源,那么<code> Transformer </code>将
     * 创建一个空源{@link org.xml.sax.InputSource}使用{@link org.xml.sax.InputSource#InputSource()new InputSource()}
     * 。
     *  <p>零参数默认构造函数。</p>。
     * 
     * 
     * @see javax.xml.transform.Transformer#transform(Source xmlSource, Result outputTarget)
     */
    public SAXSource() { }

    /**
     * Create a <code>SAXSource</code>, using an {@link org.xml.sax.XMLReader}
     * and a SAX InputSource. The {@link javax.xml.transform.Transformer}
     * or {@link javax.xml.transform.sax.SAXTransformerFactory} will set itself
     * to be the reader's {@link org.xml.sax.ContentHandler}, and then will call
     * reader.parse(inputSource).
     *
     * <p>
     *  使用{@link org.xml.sax.XMLReader}和SAX InputSource创建<code> SAXSource </code>。
     *  {@link javax.xml.transform.Transformer}或{@link javax.xml.transform.sax.SAXTransformerFactory}将自己设置为读
     * 者的{@link org.xml.sax.ContentHandler},然后将调用reader .parse(inputSource)。
     *  使用{@link org.xml.sax.XMLReader}和SAX InputSource创建<code> SAXSource </code>。
     * 
     * 
     * @param reader An XMLReader to be used for the parse.
     * @param inputSource A SAX input source reference that must be non-null
     * and that will be passed to the reader parse method.
     */
    public SAXSource(XMLReader reader, InputSource inputSource) {
        this.reader      = reader;
        this.inputSource = inputSource;
    }

    /**
     * Create a <code>SAXSource</code>, using a SAX <code>InputSource</code>.
     * The {@link javax.xml.transform.Transformer} or
     * {@link javax.xml.transform.sax.SAXTransformerFactory} creates a
     * reader via {@link org.xml.sax.helpers.XMLReaderFactory}
     * (if setXMLReader is not used), sets itself as
     * the reader's {@link org.xml.sax.ContentHandler}, and calls
     * reader.parse(inputSource).
     *
     * <p>
     * 使用SAX <code> InputSource </code>创建<code> SAXSource </code>。
     *  {@link javax.xml.transform.Transformer}或{@link javax.xml.transform.sax.SAXTransformerFactory}通过{@link org.xml.sax.helpers.XMLReaderFactory}
     * 创建一个阅读器(如果不使用setXMLReader) ,将自己设置为读者的{@link org.xml.sax.ContentHandler},并调用reader.parse(inputSource)。
     * 使用SAX <code> InputSource </code>创建<code> SAXSource </code>。
     * 
     * 
     * @param inputSource An input source reference that must be non-null
     * and that will be passed to the parse method of the reader.
     */
    public SAXSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    /**
     * Set the XMLReader to be used for the Source.
     *
     * <p>
     *  设置要用于源的XMLReader。
     * 
     * 
     * @param reader A valid XMLReader or XMLFilter reference.
     */
    public void setXMLReader(XMLReader reader) {
        this.reader = reader;
    }

    /**
     * Get the XMLReader to be used for the Source.
     *
     * <p>
     *  获取要用于源的XMLReader。
     * 
     * 
     * @return A valid XMLReader or XMLFilter reference, or null.
     */
    public XMLReader getXMLReader() {
        return reader;
    }

    /**
     * Set the SAX InputSource to be used for the Source.
     *
     * <p>
     *  设置要用于源的SAX InputSource。
     * 
     * 
     * @param inputSource A valid InputSource reference.
     */
    public void setInputSource(InputSource inputSource) {
        this.inputSource = inputSource;
    }

    /**
     * Get the SAX InputSource to be used for the Source.
     *
     * <p>
     *  获取要用于源的SAX InputSource。
     * 
     * 
     * @return A valid InputSource reference, or null.
     */
    public InputSource getInputSource() {
        return inputSource;
    }

    /**
     * Set the system identifier for this Source.  If an input source
     * has already been set, it will set the system ID or that
     * input source, otherwise it will create a new input source.
     *
     * <p>The system identifier is optional if there is a byte stream
     * or a character stream, but it is still useful to provide one,
     * since the application can use it to resolve relative URIs
     * and can include it in error messages and warnings (the parser
     * will attempt to open a connection to the URI only if
     * no byte stream or character stream is specified).</p>
     *
     * <p>
     *  设置此源的系统标识符。如果已经设置了输入源,它将设置系统ID或该输入源,否则将创建一个新的输入源。
     * 
     *  <p>如果有字节流或字符流,则系统标识符是可选的,但是仍然可以提供一个,因为应用程序可以使用它来解析相对URI,并将其包含在错误消息和警告中(解析器将尝试仅在未指定字节流或字符流时打开与URI的连接)
     * 。
     * </p>。
     * 
     * 
     * @param systemId The system identifier as a URI string.
     */
    public void setSystemId(String systemId) {

        if (null == inputSource) {
            inputSource = new InputSource(systemId);
        } else {
            inputSource.setSystemId(systemId);
        }
    }

    /**
     * <p>Get the base ID (URI or system ID) from where URIs
     * will be resolved.</p>
     *
     * <p>
     *  <p>获取要解析URI的基本ID(URI或系统ID)。</p>
     * 
     * 
     * @return Base URL for the <code>Source</code>, or <code>null</code>.
     */
    public String getSystemId() {

        if (inputSource == null) {
            return null;
        } else {
            return inputSource.getSystemId();
        }
    }

    /**
     * The XMLReader to be used for the source tree input. May be null.
     * <p>
     *  用于源树输入的XMLReader。可能为null。
     * 
     */
    private XMLReader reader;

    /**
     * <p>The SAX InputSource to be used for the source tree input.
     * Should not be <code>null</code>.</p>
     * <p>
     *  <p>要用于源树输入的SAX InputSource。不应该是<code> null </code>。</p>
     * 
     */
    private InputSource inputSource;

    /**
     * Attempt to obtain a SAX InputSource object from a Source
     * object.
     *
     * <p>
     *  尝试从源对象获取SAX InputSource对象。
     * 
     * @param source Must be a non-null Source reference.
     *
     * @return An InputSource, or null if Source can not be converted.
     */
    public static InputSource sourceToInputSource(Source source) {

        if (source instanceof SAXSource) {
            return ((SAXSource) source).getInputSource();
        } else if (source instanceof StreamSource) {
            StreamSource ss      = (StreamSource) source;
            InputSource  isource = new InputSource(ss.getSystemId());

            isource.setByteStream(ss.getInputStream());
            isource.setCharacterStream(ss.getReader());
            isource.setPublicId(ss.getPublicId());

            return isource;
        } else {
            return null;
        }
    }
}
