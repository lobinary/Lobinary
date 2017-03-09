/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2010, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>FilterInputStream</code> contains
 * some other input stream, which it uses as
 * its  basic source of data, possibly transforming
 * the data along the way or providing  additional
 * functionality. The class <code>FilterInputStream</code>
 * itself simply overrides all  methods of
 * <code>InputStream</code> with versions that
 * pass all requests to the contained  input
 * stream. Subclasses of <code>FilterInputStream</code>
 * may further override some of  these methods
 * and may also provide additional methods
 * and fields.
 *
 * <p>
 *  <code> FilterInputStream </code>包含一些其他输入流,它用作其基本数据源,可能会沿着数据转换或提供额外的功能。
 * 类<code> FilterInputStream </code>本身简单地覆盖了所有方法的<code> InputStream </code>与传递所有请求到包含的输入流的版本。
 *  <code> FilterInputStream </code>的子类可以进一步覆盖这些方法中的一些,并且还可以提供附加的方法和字段。
 * 
 * 
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class FilterInputStream extends InputStream {
    /**
     * The input stream to be filtered.
     * <p>
     *  要过滤的输入流。
     * 
     */
    protected volatile InputStream in;

    /**
     * Creates a <code>FilterInputStream</code>
     * by assigning the  argument <code>in</code>
     * to the field <code>this.in</code> so as
     * to remember it for later use.
     *
     * <p>
     *  通过将</code>中的参数<code>分配给<code> this.in </code>字段来创建<code> FilterInputStream </code>,以便记住以供日后使用。
     * 
     * 
     * @param   in   the underlying input stream, or <code>null</code> if
     *          this instance is to be created without an underlying stream.
     */
    protected FilterInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads the next byte of data from this input stream. The value
     * byte is returned as an <code>int</code> in the range
     * <code>0</code> to <code>255</code>. If no byte is available
     * because the end of the stream has been reached, the value
     * <code>-1</code> is returned. This method blocks until input data
     * is available, the end of the stream is detected, or an exception
     * is thrown.
     * <p>
     * This method
     * simply performs <code>in.read()</code> and returns the result.
     *
     * <p>
     *  从此输入流读取数据的下一个字节。值字节作为<code> 0 </code>到<code> 255 </code>范围内的<code> int </code>返回。
     * 如果没有字节可用,因为已经到达流的结尾,则返回值<code> -1 </code>。此方法阻塞,直到输入数据可用,检测到流的结尾,或抛出异常。
     * <p>
     *  这个方法只是执行<code> in.read()</code>并返回结果。
     * 
     * 
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream is reached.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     */
    public int read() throws IOException {
        return in.read();
    }

    /**
     * Reads up to <code>byte.length</code> bytes of data from this
     * input stream into an array of bytes. This method blocks until some
     * input is available.
     * <p>
     * This method simply performs the call
     * <code>read(b, 0, b.length)</code> and returns
     * the  result. It is important that it does
     * <i>not</i> do <code>in.read(b)</code> instead;
     * certain subclasses of  <code>FilterInputStream</code>
     * depend on the implementation strategy actually
     * used.
     *
     * <p>
     *  从此输入流中读取最多<code> byte.length </code>字节的数据为字节数组。此方法阻塞,直到某些输入可用。
     * <p>
     * 这个方法只是执行调用<code> read(b,0,b.length)</code>并返回结果。
     * 重要的是它不会</i>做<code> in.read(b)</code>; <code> FilterInputStream </code>的某些子类依赖于实际使用的实现策略。
     * 
     * 
     * @param      b   the buffer into which the data is read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#read(byte[], int, int)
     */
    public int read(byte b[]) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream
     * into an array of bytes. If <code>len</code> is not zero, the method
     * blocks until some input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     * <p>
     * This method simply performs <code>in.read(b, off, len)</code>
     * and returns the result.
     *
     * <p>
     *  从此输入流中读取最多<code> len </code>字节的数据为字节数组。
     * 如果<code> len </code>不为零,则该方法阻塞,直到某些输入可用;否则,不读取任何字节,并返回<code> 0 </code>。
     * <p>
     *  这个方法只是执行<code> in.read(b,off,len)</code>并返回结果。
     * 
     * 
     * @param      b     the buffer into which the data is read.
     * @param      off   the start offset in the destination array <code>b</code>
     * @param      len   the maximum number of bytes read.
     * @return     the total number of bytes read into the buffer, or
     *             <code>-1</code> if there is no more data because the end of
     *             the stream has been reached.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     */
    public int read(byte b[], int off, int len) throws IOException {
        return in.read(b, off, len);
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from the
     * input stream. The <code>skip</code> method may, for a variety of
     * reasons, end up skipping over some smaller number of bytes,
     * possibly <code>0</code>. The actual number of bytes skipped is
     * returned.
     * <p>
     * This method simply performs <code>in.skip(n)</code>.
     *
     * <p>
     *  跳过并丢弃来自输入流的<code> n </code>字节数据。由于各种原因,<code> skip </code>方法可能跳过一些较小数量的字节,可能是<code> 0 </code>。
     * 将返回实际跳过的字节数。
     * <p>
     *  这个方法只是执行<code> in.skip(n)</code>。
     * 
     * 
     * @param      n   the number of bytes to be skipped.
     * @return     the actual number of bytes skipped.
     * @exception  IOException  if the stream does not support seek,
     *                          or if some other I/O error occurs.
     */
    public long skip(long n) throws IOException {
        return in.skip(n);
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * caller of a method for this input stream. The next caller might be
     * the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     * <p>
     * This method returns the result of {@link #in in}.available().
     *
     * <p>
     *  返回此输入流中可以读取(或跳过)的字节数的估计值,而不会被此输入流的方法的下一个调用者阻止。下一个调用者可能是同一个线程或另一个线程。单个读取或跳过这么多字节不会阻塞,但可以读取或跳过更少的字节。
     * <p>
     *  此方法返回{@link #in in} .available()的结果。
     * 
     * 
     * @return     an estimate of the number of bytes that can be read (or skipped
     *             over) from this input stream without blocking.
     * @exception  IOException  if an I/O error occurs.
     */
    public int available() throws IOException {
        return in.available();
    }

    /**
     * Closes this input stream and releases any system resources
     * associated with the stream.
     * This
     * method simply performs <code>in.close()</code>.
     *
     * <p>
     *  关闭此输入流,并释放与流相关联的任何系统资源。这个方法只是执行<code> in.close()</code>。
     * 
     * 
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     */
    public void close() throws IOException {
        in.close();
    }

    /**
     * Marks the current position in this input stream. A subsequent
     * call to the <code>reset</code> method repositions this stream at
     * the last marked position so that subsequent reads re-read the same bytes.
     * <p>
     * The <code>readlimit</code> argument tells this input stream to
     * allow that many bytes to be read before the mark position gets
     * invalidated.
     * <p>
     * This method simply performs <code>in.mark(readlimit)</code>.
     *
     * <p>
     * 标记此输入流中的当前位置。随后对<code> reset </code>方法的调用将该流重新定位在最后标记的位置,使得后续读取重新读取相同的字节。
     * <p>
     *  <code> readlimit </code>参数告诉此输入流允许在标记位置无效之前读取许多字节。
     * <p>
     *  这个方法只是执行<code> in.mark(readlimit)</code>。
     * 
     * 
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.FilterInputStream#in
     * @see     java.io.FilterInputStream#reset()
     */
    public synchronized void mark(int readlimit) {
        in.mark(readlimit);
    }

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     * <p>
     * This method
     * simply performs <code>in.reset()</code>.
     * <p>
     * Stream marks are intended to be used in
     * situations where you need to read ahead a little to see what's in
     * the stream. Often this is most easily done by invoking some
     * general parser. If the stream is of the type handled by the
     * parse, it just chugs along happily. If the stream is not of
     * that type, the parser should toss an exception when it fails.
     * If this happens within readlimit bytes, it allows the outer
     * code to reset the stream and try another parser.
     *
     * <p>
     *  将此流重新定位到在此输入流上最后调用<code>标记</code>方法时的位置。
     * <p>
     *  这个方法只是执行<code> in.reset()</code>。
     * <p>
     *  流标记用于需要提前阅读以查看流中的内容的情况。通常这最容易通过调用一些一般的解析器来完成。如果流是由解析处理的类型,它只是愉快地chugs。如果流不是该类型,解析器在失败时应抛出异常。
     * 
     * @exception  IOException  if the stream has not been marked or if the
     *               mark has been invalidated.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.FilterInputStream#mark(int)
     */
    public synchronized void reset() throws IOException {
        in.reset();
    }

    /**
     * Tests if this input stream supports the <code>mark</code>
     * and <code>reset</code> methods.
     * This method
     * simply performs <code>in.markSupported()</code>.
     *
     * <p>
     * 如果这发生在readlimit字节内,它允许外部代码重置流并尝试另一个解析器。
     * 
     * 
     * @return  <code>true</code> if this stream type supports the
     *          <code>mark</code> and <code>reset</code> method;
     *          <code>false</code> otherwise.
     * @see     java.io.FilterInputStream#in
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return in.markSupported();
    }
}
