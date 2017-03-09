/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform.stream;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.Source;

/**
 * <p>Acts as an holder for a transformation Source in the form
 * of a stream of XML markup.</p>
 *
 * <p><em>Note:</em> Due to their internal use of either a {@link Reader} or {@link InputStream} instance,
 * <code>StreamSource</code> instances may only be used once.</p>
 *
 * <p>
 *  <p>以XML标记流的形式作为转换源的持有者。</p>
 * 
 *  <p> <em>注意：</em>由于内部使用{@link Reader}或{@link InputStream}实例,<code> StreamSource </code>实例只能使用一次。 p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public class StreamSource implements Source {

    /** If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns true when passed this value as an argument,
     * the Transformer supports Source input of this type.
     * <p>
     *  当传递此值作为参数时返回true,Transformer支持此类型的Source输入。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.stream.StreamSource/feature";

    /**
     * <p>Zero-argument default constructor.  If this constructor is used, and
     * no Stream source is set using
     * {@link #setInputStream(java.io.InputStream inputStream)} or
     * {@link #setReader(java.io.Reader reader)}, then the
     * <code>Transformer</code> will
     * create an empty source {@link java.io.InputStream} using
     * {@link java.io.InputStream#InputStream() new InputStream()}.</p>
     *
     * <p>
     *  <p>零参数默认构造函数。
     * 如果使用这个构造函数,并且没有使用{@link #setInputStream(java.io.InputStream inputStream)}或{@link #setReader(java.io.Reader reader)}
     * 设置Stream源,那么<code> Transformer < / code>将使用{@link java.io.InputStream#InputStream()new InputStream()}
     * 创建一个空源{@link java.io.InputStream}。
     *  <p>零参数默认构造函数。</p>。
     * 
     * 
     * @see javax.xml.transform.Transformer#transform(Source xmlSource, Result outputTarget)
     */
    public StreamSource() { }

    /**
     * Construct a StreamSource from a byte stream.  Normally,
     * a stream should be used rather than a reader, so
     * the XML parser can resolve character encoding specified
     * by the XML declaration.
     *
     * <p>If this constructor is used to process a stylesheet, normally
     * setSystemId should also be called, so that relative URI references
     * can be resolved.</p>
     *
     * <p>
     *  从字节流构造StreamSource。通常,应该使用流而不是阅读器,因此XML解析器可以解析XML声明指定的字符编码。
     * 
     *  <p>如果此构造函数用于处理样式表,通常也应调用setSystemId,以便可以解析相对URI引用。</p>
     * 
     * 
     * @param inputStream A valid InputStream reference to an XML stream.
     */
    public StreamSource(InputStream inputStream) {
        setInputStream(inputStream);
    }

    /**
     * Construct a StreamSource from a byte stream.  Normally,
     * a stream should be used rather than a reader, so that
     * the XML parser can resolve character encoding specified
     * by the XML declaration.
     *
     * <p>This constructor allows the systemID to be set in addition
     * to the input stream, which allows relative URIs
     * to be processed.</p>
     *
     * <p>
     *  从字节流构造StreamSource。通常,应该使用流而不是阅读器,以便XML解析器可以解析XML声明指定的字符编码。
     * 
     * <p>此构造函数允许除了输入流之外还设置systemID,这允许处理相对URI。</p>
     * 
     * 
     * @param inputStream A valid InputStream reference to an XML stream.
     * @param systemId Must be a String that conforms to the URI syntax.
     */
    public StreamSource(InputStream inputStream, String systemId) {
        setInputStream(inputStream);
        setSystemId(systemId);
    }

    /**
     * Construct a StreamSource from a character reader.  Normally,
     * a stream should be used rather than a reader, so that
     * the XML parser can resolve character encoding specified
     * by the XML declaration.  However, in many cases the encoding
     * of the input stream is already resolved, as in the case of
     * reading XML from a StringReader.
     *
     * <p>
     *  从字符阅读器构造StreamSource。通常,应该使用流而不是阅读器,以便XML解析器可以解析XML声明指定的字符编码。
     * 然而,在许多情况下,输入流的编码已经解决,如在从StringReader读取XML的情况下。
     * 
     * 
     * @param reader A valid Reader reference to an XML character stream.
     */
    public StreamSource(Reader reader) {
        setReader(reader);
    }

    /**
     * Construct a StreamSource from a character reader.  Normally,
     * a stream should be used rather than a reader, so that
     * the XML parser may resolve character encoding specified
     * by the XML declaration.  However, in many cases the encoding
     * of the input stream is already resolved, as in the case of
     * reading XML from a StringReader.
     *
     * <p>
     *  从字符阅读器构造StreamSource。通常,应该使用流而不是阅读器,以便XML解析器可以解析由XML声明指定的字符编码。
     * 然而,在许多情况下,输入流的编码已经解决,如在从StringReader读取XML的情况下。
     * 
     * 
     * @param reader A valid Reader reference to an XML character stream.
     * @param systemId Must be a String that conforms to the URI syntax.
     */
    public StreamSource(Reader reader, String systemId) {
        setReader(reader);
        setSystemId(systemId);
    }

    /**
     * Construct a StreamSource from a URL.
     *
     * <p>
     *  从URL构造StreamSource。
     * 
     * 
     * @param systemId Must be a String that conforms to the URI syntax.
     */
    public StreamSource(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Construct a StreamSource from a File.
     *
     * <p>
     *  从文件构造StreamSource。
     * 
     * 
     * @param f Must a non-null File reference.
     */
    public StreamSource(File f) {
        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        setSystemId(f.toURI().toASCIIString());
    }

    /**
     * Set the byte stream to be used as input.  Normally,
     * a stream should be used rather than a reader, so that
     * the XML parser can resolve character encoding specified
     * by the XML declaration.
     *
     * <p>If this Source object is used to process a stylesheet, normally
     * setSystemId should also be called, so that relative URL references
     * can be resolved.</p>
     *
     * <p>
     *  设置要用作输入的字节流。通常,应该使用流而不是阅读器,以便XML解析器可以解析XML声明指定的字符编码。
     * 
     *  <p>如果此Source对象用于处理样式表,通常也应调用setSystemId,以便可以解析相对URL引用。</p>
     * 
     * 
     * @param inputStream A valid InputStream reference to an XML stream.
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Get the byte stream that was set with setByteStream.
     *
     * <p>
     *  获取使用setByteStream设置的字节流。
     * 
     * 
     * @return The byte stream that was set with setByteStream, or null
     * if setByteStream or the ByteStream constructor was not called.
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * Set the input to be a character reader.  Normally,
     * a stream should be used rather than a reader, so that
     * the XML parser can resolve character encoding specified
     * by the XML declaration.  However, in many cases the encoding
     * of the input stream is already resolved, as in the case of
     * reading XML from a StringReader.
     *
     * <p>
     * 将输入设置为字符阅读器。通常,应该使用流而不是阅读器,以便XML解析器可以解析XML声明指定的字符编码。然而,在许多情况下,输入流的编码已经解决,如在从StringReader读取XML的情况下。
     * 
     * 
     * @param reader A valid Reader reference to an XML CharacterStream.
     */
    public void setReader(Reader reader) {
        this.reader = reader;
    }

    /**
     * Get the character stream that was set with setReader.
     *
     * <p>
     *  获取使用setReader设置的字符流。
     * 
     * 
     * @return The character stream that was set with setReader, or null
     * if setReader or the Reader constructor was not called.
     */
    public Reader getReader() {
        return reader;
    }

    /**
     * Set the public identifier for this Source.
     *
     * <p>The public identifier is always optional: if the application
     * writer includes one, it will be provided as part of the
     * location information.</p>
     *
     * <p>
     *  设置此源的公共标识符。
     * 
     *  <p>公共标识符始终是可选的：如果应用程序编写器包含一个,则它将作为位置信息的一部分提供。</p>
     * 
     * 
     * @param publicId The public identifier as a string.
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * Get the public identifier that was set with setPublicId.
     *
     * <p>
     *  获取使用setPublicId设置的公共标识符。
     * 
     * 
     * @return The public identifier that was set with setPublicId, or null
     * if setPublicId was not called.
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * Set the system identifier for this Source.
     *
     * <p>The system identifier is optional if there is a byte stream
     * or a character stream, but it is still useful to provide one,
     * since the application can use it to resolve relative URIs
     * and can include it in error messages and warnings (the parser
     * will attempt to open a connection to the URI only if
     * there is no byte stream or character stream specified).</p>
     *
     * <p>
     *  设置此源的系统标识符。
     * 
     *  <p>如果有字节流或字符流,则系统标识符是可选的,但是仍然可以提供一个,因为应用程序可以使用它来解析相对URI,并且可以将其包括在错误消息和警告中(解析器将尝试仅在没有指定字节流或字符流时打开与URI
     * 的连接)。
     * </p>。
     * 
     * 
     * @param systemId The system identifier as a URL string.
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Get the system identifier that was set with setSystemId.
     *
     * <p>
     *  获取使用setSystemId设置的系统标识符。
     * 
     * 
     * @return The system identifier that was set with setSystemId, or null
     * if setSystemId was not called.
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Set the system ID from a File reference.
     *
     * <p>
     *  从文件引用设置系统ID。
     * 
     * 
     * @param f Must a non-null File reference.
     */
    public void setSystemId(File f) {
        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        this.systemId = f.toURI().toASCIIString();
    }

    //////////////////////////////////////////////////////////////////////
    // Internal state.
    //////////////////////////////////////////////////////////////////////

    /**
     * The public identifier for this input source, or null.
     * <p>
     *  此输入源的公共标识符,或为null。
     * 
     */
    private String publicId;

    /**
     * The system identifier as a URL string, or null.
     * <p>
     *  系统标识符作为URL字符串,或为null。
     * 
     */
    private String systemId;

    /**
     * The byte stream for this Source, or null.
     * <p>
     *  此源的字节流,或为null。
     * 
     */
    private InputStream inputStream;

    /**
     * The character stream for this Source, or null.
     * <p>
     *  此源的字符流,或null。
     */
    private Reader reader;
}
