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

import javax.xml.transform.Result;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;

/**
 * <p>Acts as an holder for a transformation result,
 * which may be XML, plain Text, HTML, or some other form of markup.</p>
 *
 * <p>
 *  <p>作为转换结果的持有者,可以是XML,纯文本,HTML或某种其他形式的标记。</p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public class StreamResult implements Result {

    /** If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns true when passed this value as an argument,
     * the Transformer supports Result output of this type.
     * <p>
     *  当传递此值作为参数时返回true,Transformer支持此类型的Result输出。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.stream.StreamResult/feature";

    /**
     * Zero-argument default constructor.
     * <p>
     *  零参数默认构造函数。
     * 
     */
    public StreamResult() {
    }

    /**
     * Construct a StreamResult from a byte stream.  Normally,
     * a stream should be used rather than a reader, so that
     * the transformer may use instructions contained in the
     * transformation instructions to control the encoding.
     *
     * <p>
     *  从字节流构造StreamResult。通常,应当使用流而不是读取器,使得变换器可以使用包含在变换指令中的指令来控制编码。
     * 
     * 
     * @param outputStream A valid OutputStream reference.
     */
    public StreamResult(OutputStream outputStream) {
        setOutputStream(outputStream);
    }

    /**
     * Construct a StreamResult from a character stream.  Normally,
     * a stream should be used rather than a reader, so that
     * the transformer may use instructions contained in the
     * transformation instructions to control the encoding.  However,
     * there are times when it is useful to write to a character
     * stream, such as when using a StringWriter.
     *
     * <p>
     *  从字符流构造StreamResult。通常,应当使用流而不是读取器,使得变换器可以使用包含在变换指令中的指令来控制编码。但是,有时写入字符流是有用的,例如使用StringWriter时。
     * 
     * 
     * @param writer  A valid Writer reference.
     */
    public StreamResult(Writer writer) {
        setWriter(writer);
    }

    /**
     * Construct a StreamResult from a URL.
     *
     * <p>
     *  从URL构造StreamResult。
     * 
     * 
     * @param systemId Must be a String that conforms to the URI syntax.
     */
    public StreamResult(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Construct a StreamResult from a File.
     *
     * <p>
     *  从文件构造StreamResult。
     * 
     * 
     * @param f Must a non-null File reference.
     */
    public StreamResult(File f) {
        //convert file to appropriate URI, f.toURI().toASCIIString()
        //converts the URI to string as per rule specified in
        //RFC 2396,
        setSystemId(f.toURI().toASCIIString());
    }

    /**
     * Set the ByteStream that is to be written to.  Normally,
     * a stream should be used rather than a reader, so that
     * the transformer may use instructions contained in the
     * transformation instructions to control the encoding.
     *
     * <p>
     *  设置要写入的ByteStream。通常,应当使用流而不是读取器,使得变换器可以使用包含在变换指令中的指令来控制编码。
     * 
     * 
     * @param outputStream A valid OutputStream reference.
     */
    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Get the byte stream that was set with setOutputStream.
     *
     * <p>
     *  获取使用setOutputStream设置的字节流。
     * 
     * 
     * @return The byte stream that was set with setOutputStream, or null
     * if setOutputStream or the ByteStream constructor was not called.
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * Set the writer that is to receive the result.  Normally,
     * a stream should be used rather than a writer, so that
     * the transformer may use instructions contained in the
     * transformation instructions to control the encoding.  However,
     * there are times when it is useful to write to a writer,
     * such as when using a StringWriter.
     *
     * <p>
     * 设置要接收结果的writer。通常,应当使用流而不是写入器,使得变换器可以使用包含在变换指令中的指令来控制编码。但是,有时写入写入程序很有用,例如使用StringWriter时。
     * 
     * 
     * @param writer  A valid Writer reference.
     */
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Get the character stream that was set with setWriter.
     *
     * <p>
     *  获取使用setWriter设置的字符流。
     * 
     * 
     * @return The character stream that was set with setWriter, or null
     * if setWriter or the Writer constructor was not called.
     */
    public Writer getWriter() {
        return writer;
    }

    /**
     * Set the systemID that may be used in association
     * with the byte or character stream, or, if neither is set, use
     * this value as a writeable URI (probably a file name).
     *
     * <p>
     *  设置可以与字节或字符流关联使用的systemID,或者如果没有设置,则将此值用作可写URI(可能是文件名)。
     * 
     * 
     * @param systemId The system identifier as a URI string.
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * <p>Set the system ID from a <code>File</code> reference.</p>
     *
     *
     * <p>
     *  <p>从<code> File </code>参考中设置系统ID。</p>
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

    //////////////////////////////////////////////////////////////////////
    // Internal state.
    //////////////////////////////////////////////////////////////////////

    /**
     * The systemID that may be used in association
     * with the byte or character stream, or, if neither is set, use
     * this value as a writeable URI (probably a file name).
     * <p>
     *  可以与字节或字符流相关联使用的systemID,或者如果没有设置,则使用此值作为可写URI(可能是文件名)。
     * 
     */
    private String systemId;

    /**
     * The byte stream that is to be written to.
     * <p>
     *  要写入的字节流。
     * 
     */
    private OutputStream outputStream;

    /**
     * The character stream that is to be written to.
     * <p>
     *  要写入的字符流。
     */
    private Writer writer;
}
