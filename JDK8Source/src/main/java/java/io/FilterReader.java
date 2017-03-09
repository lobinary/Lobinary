/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Abstract class for reading filtered character streams.
 * The abstract class <code>FilterReader</code> itself
 * provides default methods that pass all requests to
 * the contained stream. Subclasses of <code>FilterReader</code>
 * should override some of these methods and may also provide
 * additional methods and fields.
 *
 * <p>
 *  用于读取过滤的字符流的抽象类。抽象类<code> FilterReader </code>本身提供了将所有请求传递给包含的流的默认方法。
 *  <code> FilterReader </code>的子类应该覆盖这些方法中的一些,并且还可以提供其他方法和字段。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public abstract class FilterReader extends Reader {

    /**
     * The underlying character-input stream.
     * <p>
     *  底层字符输入流。
     * 
     */
    protected Reader in;

    /**
     * Creates a new filtered reader.
     *
     * <p>
     *  创建新的过滤阅读器。
     * 
     * 
     * @param in  a Reader object providing the underlying stream.
     * @throws NullPointerException if <code>in</code> is <code>null</code>
     */
    protected FilterReader(Reader in) {
        super(in);
        this.in = in;
    }

    /**
     * Reads a single character.
     *
     * <p>
     *  读取单个字符。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads characters into a portion of an array.
     *
     * <p>
     *  将字符读入数组的一部分。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        return in.read(cbuf, off, len);
    }

    /**
     * Skips characters.
     *
     * <p>
     *  跳过字符。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    /**
     * Tells whether this stream is ready to be read.
     *
     * <p>
     *  告诉这个流是否准备好被读取。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public boolean ready() throws IOException {
        return in.ready();
    }

    /**
     * Tells whether this stream supports the mark() operation.
     * <p>
     *  告诉这个流是否支持mark()操作。
     * 
     */
    public boolean markSupported() {
        return in.markSupported();
    }

    /**
     * Marks the present position in the stream.
     *
     * <p>
     *  标记流中的当前位置。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        in.mark(readAheadLimit);
    }

    /**
     * Resets the stream.
     *
     * <p>
     *  重置流。
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void reset() throws IOException {
        in.reset();
    }

    public void close() throws IOException {
        in.close();
    }

}
