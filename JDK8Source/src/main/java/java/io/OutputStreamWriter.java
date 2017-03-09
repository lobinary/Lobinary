/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.StreamEncoder;


/**
 * An OutputStreamWriter is a bridge from character streams to byte streams:
 * Characters written to it are encoded into bytes using a specified {@link
 * java.nio.charset.Charset charset}.  The charset that it uses
 * may be specified by name or may be given explicitly, or the platform's
 * default charset may be accepted.
 *
 * <p> Each invocation of a write() method causes the encoding converter to be
 * invoked on the given character(s).  The resulting bytes are accumulated in a
 * buffer before being written to the underlying output stream.  The size of
 * this buffer may be specified, but by default it is large enough for most
 * purposes.  Note that the characters passed to the write() methods are not
 * buffered.
 *
 * <p> For top efficiency, consider wrapping an OutputStreamWriter within a
 * BufferedWriter so as to avoid frequent converter invocations.  For example:
 *
 * <pre>
 * Writer out
 *   = new BufferedWriter(new OutputStreamWriter(System.out));
 * </pre>
 *
 * <p> A <i>surrogate pair</i> is a character represented by a sequence of two
 * <tt>char</tt> values: A <i>high</i> surrogate in the range '&#92;uD800' to
 * '&#92;uDBFF' followed by a <i>low</i> surrogate in the range '&#92;uDC00' to
 * '&#92;uDFFF'.
 *
 * <p> A <i>malformed surrogate element</i> is a high surrogate that is not
 * followed by a low surrogate or a low surrogate that is not preceded by a
 * high surrogate.
 *
 * <p> This class always replaces malformed surrogate elements and unmappable
 * character sequences with the charset's default <i>substitution sequence</i>.
 * The {@linkplain java.nio.charset.CharsetEncoder} class should be used when more
 * control over the encoding process is required.
 *
 * <p>
 *  OutputStreamWriter是从字符流到字节流的桥梁：使用指定的{@link java.nio.charset.Charset charset}将写入它的字符编码为字节。
 * 它使用的字符集可以通过名称指定或可以明确给出,或者可以接受平台的默认字符集。
 * 
 *  <p>每次调用write()方法都会导致编码转换器在给定字符上被调用。所得到的字节在被写入底层输出流之前在缓冲器中累积。可以指定该缓冲区的大小,但是默认情况下,它对于大多数目的来说足够大。
 * 请注意,传递给write()方法的字符不进行缓冲。
 * 
 *  <p>为了提高效率,请考虑在BufferedWriter中封装一个OutputStreamWriter,以避免频繁的转换器调用。例如：
 * 
 * <pre>
 *  Writer out = new BufferedWriter(new OutputStreamWriter(System.out));
 * </pre>
 * 
 *  <p> A <i>代理对</i>是由两个<tt> char </tt>值序列表示的字符：<u> '\ uDBFF'后跟在范围'\ uDC00'到'\ uDFFF'中的<i>低</i>代理。
 * 
 *  <p> A <i>格式错误的替代元素</i>是一个高代理,后面没有低代理或低代理,而没有高代理。
 * 
 * <p>此类始终使用字符集的默认<i>替换序列</i>替换格式不正确的替代元素和不可映射的字符序列。
 * 当需要更多地控制编码过程时,应该使用{@linkplain java.nio.charset.CharsetEncoder}类。
 * 
 * 
 * @see BufferedWriter
 * @see OutputStream
 * @see java.nio.charset.Charset
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class OutputStreamWriter extends Writer {

    private final StreamEncoder se;

    /**
     * Creates an OutputStreamWriter that uses the named charset.
     *
     * <p>
     *  创建使用命名的字符集的OutputStreamWriter。
     * 
     * 
     * @param  out
     *         An OutputStream
     *
     * @param  charsetName
     *         The name of a supported
     *         {@link java.nio.charset.Charset charset}
     *
     * @exception  UnsupportedEncodingException
     *             If the named encoding is not supported
     */
    public OutputStreamWriter(OutputStream out, String charsetName)
        throws UnsupportedEncodingException
    {
        super(out);
        if (charsetName == null)
            throw new NullPointerException("charsetName");
        se = StreamEncoder.forOutputStreamWriter(out, this, charsetName);
    }

    /**
     * Creates an OutputStreamWriter that uses the default character encoding.
     *
     * <p>
     *  创建使用默认字符编码的OutputStreamWriter。
     * 
     * 
     * @param  out  An OutputStream
     */
    public OutputStreamWriter(OutputStream out) {
        super(out);
        try {
            se = StreamEncoder.forOutputStreamWriter(out, this, (String)null);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }

    /**
     * Creates an OutputStreamWriter that uses the given charset.
     *
     * <p>
     *  创建使用给定字符集的OutputStreamWriter。
     * 
     * 
     * @param  out
     *         An OutputStream
     *
     * @param  cs
     *         A charset
     *
     * @since 1.4
     * @spec JSR-51
     */
    public OutputStreamWriter(OutputStream out, Charset cs) {
        super(out);
        if (cs == null)
            throw new NullPointerException("charset");
        se = StreamEncoder.forOutputStreamWriter(out, this, cs);
    }

    /**
     * Creates an OutputStreamWriter that uses the given charset encoder.
     *
     * <p>
     *  创建使用给定字符集编码器的OutputStreamWriter。
     * 
     * 
     * @param  out
     *         An OutputStream
     *
     * @param  enc
     *         A charset encoder
     *
     * @since 1.4
     * @spec JSR-51
     */
    public OutputStreamWriter(OutputStream out, CharsetEncoder enc) {
        super(out);
        if (enc == null)
            throw new NullPointerException("charset encoder");
        se = StreamEncoder.forOutputStreamWriter(out, this, enc);
    }

    /**
     * Returns the name of the character encoding being used by this stream.
     *
     * <p> If the encoding has an historical name then that name is returned;
     * otherwise the encoding's canonical name is returned.
     *
     * <p> If this instance was created with the {@link
     * #OutputStreamWriter(OutputStream, String)} constructor then the returned
     * name, being unique for the encoding, may differ from the name passed to
     * the constructor.  This method may return <tt>null</tt> if the stream has
     * been closed. </p>
     *
     * <p>
     *  返回此流使用的字符编码的名称。
     * 
     *  <p>如果编码具有历史名称,则返回该名称;否则返回编码的规范名称。
     * 
     *  <p>如果此实例是使用{@link #OutputStreamWriter(OutputStream,String)}构造函数创建的,则对于编码唯一的返回名称可能与传递给构造函数的名称不同。
     * 如果流已关闭,此方法可能返回<tt> null </tt>。 </p>。
     * 
     * 
     * @return The historical name of this encoding, or possibly
     *         <code>null</code> if the stream has been closed
     *
     * @see java.nio.charset.Charset
     *
     * @revised 1.4
     * @spec JSR-51
     */
    public String getEncoding() {
        return se.getEncoding();
    }

    /**
     * Flushes the output buffer to the underlying byte stream, without flushing
     * the byte stream itself.  This method is non-private only so that it may
     * be invoked by PrintStream.
     * <p>
     *  将输出缓冲区刷新到底层字节流,而不刷新字节流本身。这个方法是非私有的,所以它可以被PrintStream调用。
     * 
     */
    void flushBuffer() throws IOException {
        se.flushBuffer();
    }

    /**
     * Writes a single character.
     *
     * <p>
     *  写入单个字符。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void write(int c) throws IOException {
        se.write(c);
    }

    /**
     * Writes a portion of an array of characters.
     *
     * <p>
     *  写入字符数组的一部分。
     * 
     * 
     * @param  cbuf  Buffer of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        se.write(cbuf, off, len);
    }

    /**
     * Writes a portion of a string.
     *
     * <p>
     *  写入字符串的一部分。
     * 
     * 
     * @param  str  A String
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String str, int off, int len) throws IOException {
        se.write(str, off, len);
    }

    /**
     * Flushes the stream.
     *
     * <p>
     *  刷新流。
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void flush() throws IOException {
        se.flush();
    }

    public void close() throws IOException {
        se.close();
    }
}
