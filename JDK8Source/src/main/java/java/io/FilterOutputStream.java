/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * This class is the superclass of all classes that filter output
 * streams. These streams sit on top of an already existing output
 * stream (the <i>underlying</i> output stream) which it uses as its
 * basic sink of data, but possibly transforming the data along the
 * way or providing additional functionality.
 * <p>
 * The class <code>FilterOutputStream</code> itself simply overrides
 * all methods of <code>OutputStream</code> with versions that pass
 * all requests to the underlying output stream. Subclasses of
 * <code>FilterOutputStream</code> may further override some of these
 * methods as well as provide additional methods and fields.
 *
 * <p>
 *  这个类是过滤输出流的所有类的超类。这些流位于已经存在的输出流(<下层</i>输出流)的顶部,它用作其数据的基本接收器,但可能沿着该方式变换数据或提供附加功能。
 * <p>
 *  类<code> FilterOutputStream </code>本身简单地覆盖所有的方法,<code> OutputStream </code>与传递所有请求到底层输出流的版本。
 *  <code> FilterOutputStream </code>的子类可以进一步覆盖这些方法中的一些,以及提供额外的方法和字段。
 * 
 * 
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class FilterOutputStream extends OutputStream {
    /**
     * The underlying output stream to be filtered.
     * <p>
     *  要过滤的基本输出流。
     * 
     */
    protected OutputStream out;

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * <p>
     *  创建在指定的底层输出流之上构建的输出流过滤器。
     * 
     * 
     * @param   out   the underlying output stream to be assigned to
     *                the field <tt>this.out</tt> for later use, or
     *                <code>null</code> if this instance is to be
     *                created without an underlying stream.
     */
    public FilterOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes the specified <code>byte</code> to this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls the <code>write</code> method of its underlying output stream,
     * that is, it performs <tt>out.write(b)</tt>.
     * <p>
     * Implements the abstract <tt>write</tt> method of <tt>OutputStream</tt>.
     *
     * <p>
     *  将指定的<code>字节</code>写入此输出流。
     * <p>
     *  <code> FilterOutputStream </code>的<code> write </code>方法调用其底层输出流的<code> write </code>方法,即执行<tt> out.
     * write </tt>。
     * <p>
     *  实现<tt> OutputStream </tt>的抽象<tt>写</tt>方法。
     * 
     * 
     * @param      b   the <code>byte</code>.
     * @exception  IOException  if an I/O error occurs.
     */
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Writes <code>b.length</code> bytes to this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls its <code>write</code> method of three arguments with the
     * arguments <code>b</code>, <code>0</code>, and
     * <code>b.length</code>.
     * <p>
     * Note that this method does not call the one-argument
     * <code>write</code> method of its underlying stream with the single
     * argument <code>b</code>.
     *
     * <p>
     *  将<code> b.length </code>字节写入此输出流。
     * <p>
     *  <code> FilterOutputStream </code>的<code> write </code>方法调用其参数<code> b </code>,<code> 0 < / code>和<code>
     *  b.length </code>。
     * <p>
     * 请注意,此方法不会使用单个参数<code> b </code>调用其基础流的单参数<code> write </code>方法。
     * 
     * 
     * @param      b   the data to be written.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterOutputStream#write(byte[], int, int)
     */
    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * Writes <code>len</code> bytes from the specified
     * <code>byte</code> array starting at offset <code>off</code> to
     * this output stream.
     * <p>
     * The <code>write</code> method of <code>FilterOutputStream</code>
     * calls the <code>write</code> method of one argument on each
     * <code>byte</code> to output.
     * <p>
     * Note that this method does not call the <code>write</code> method
     * of its underlying input stream with the same arguments. Subclasses
     * of <code>FilterOutputStream</code> should provide a more efficient
     * implementation of this method.
     *
     * <p>
     *  从指定的<code>字节</code>数组开始,从偏移<code> off </code>向此输出流写入<code> len </code>字节。
     * <p>
     *  <code> FilterOutputStream </code>的<code> write </code>方法调用每个<code>字节</code>上的一个参数的<code> write </code>
     * 方法输出。
     * <p>
     *  请注意,此方法不会调用具有相同参数的其基础输入流的<code> write </code>方法。 <code> FilterOutputStream </code>的子类应提供此方法的更有效的实现。
     * 
     * 
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterOutputStream#write(int)
     */
    public void write(byte b[], int off, int len) throws IOException {
        if ((off | len | (b.length - (len + off)) | (off + len)) < 0)
            throw new IndexOutOfBoundsException();

        for (int i = 0 ; i < len ; i++) {
            write(b[off + i]);
        }
    }

    /**
     * Flushes this output stream and forces any buffered output bytes
     * to be written out to the stream.
     * <p>
     * The <code>flush</code> method of <code>FilterOutputStream</code>
     * calls the <code>flush</code> method of its underlying output stream.
     *
     * <p>
     *  刷新此输出流并强制任何缓冲的输出字节被写入流。
     * <p>
     *  <code> FilterOutputStream </code>的<code> flush </code>方法调用其底层输出流的<code> flush </code>方法。
     * 
     * 
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterOutputStream#out
     */
    public void flush() throws IOException {
        out.flush();
    }

    /**
     * Closes this output stream and releases any system resources
     * associated with the stream.
     * <p>
     * The <code>close</code> method of <code>FilterOutputStream</code>
     * calls its <code>flush</code> method, and then calls the
     * <code>close</code> method of its underlying output stream.
     *
     * <p>
     *  关闭此输出流并释放与流相关联的任何系统资源。
     * <p>
     *  <code> FilterOutputStream </code>的<code> close </code>方法调用其<code> flush </code>方法,然后调用其底层输出流的<code> 
     * 
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.FilterOutputStream#flush()
     * @see        java.io.FilterOutputStream#out
     */
    @SuppressWarnings("try")
    public void close() throws IOException {
        try (OutputStream ostream = out) {
            flush();
        }
    }
}
