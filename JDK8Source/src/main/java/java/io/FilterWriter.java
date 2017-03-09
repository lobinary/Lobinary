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
 * Abstract class for writing filtered character streams.
 * The abstract class <code>FilterWriter</code> itself
 * provides default methods that pass all requests to the
 * contained stream. Subclasses of <code>FilterWriter</code>
 * should override some of these methods and may also
 * provide additional methods and fields.
 *
 * <p>
 *  写过滤的字符流的抽象类。抽象类<code> FilterWriter </code>本身提供了将所有请求传递给包含的流的默认方法。
 *  <code> FilterWriter </code>的子类应该覆盖这些方法中的一些,并且还可以提供其他方法和字段。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public abstract class FilterWriter extends Writer {

    /**
     * The underlying character-output stream.
     * <p>
     *  底层字符输出流。
     * 
     */
    protected Writer out;

    /**
     * Create a new filtered writer.
     *
     * <p>
     *  创建新的过滤的写入程序。
     * 
     * 
     * @param out  a Writer object to provide the underlying stream.
     * @throws NullPointerException if <code>out</code> is <code>null</code>
     */
    protected FilterWriter(Writer out) {
        super(out);
        this.out = out;
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
        out.write(c);
    }

    /**
     * Writes a portion of an array of characters.
     *
     * <p>
     *  写入字符数组的一部分。
     * 
     * 
     * @param  cbuf  Buffer of characters to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        out.write(cbuf, off, len);
    }

    /**
     * Writes a portion of a string.
     *
     * <p>
     *  写入字符串的一部分。
     * 
     * 
     * @param  str  String to be written
     * @param  off  Offset from which to start reading characters
     * @param  len  Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String str, int off, int len) throws IOException {
        out.write(str, off, len);
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
        out.flush();
    }

    public void close() throws IOException {
        out.close();
    }

}
