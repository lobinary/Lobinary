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

package java.util.zip;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * This class implements an output stream filter for compressing data in
 * the "deflate" compression format. It is also used as the basis for other
 * types of compression filters, such as GZIPOutputStream.
 *
 * <p>
 *  这个类实现了一个输出流过滤器,用于以"deflate"压缩格式压缩数据。它也用作其他类型的压缩过滤器的基础,例如GZIPOutputStream。
 * 
 * 
 * @see         Deflater
 * @author      David Connelly
 */
public
class DeflaterOutputStream extends FilterOutputStream {
    /**
     * Compressor for this stream.
     * <p>
     *  此流的压缩程序。
     * 
     */
    protected Deflater def;

    /**
     * Output buffer for writing compressed data.
     * <p>
     *  写入压缩数据的输出缓冲器。
     * 
     */
    protected byte[] buf;

    /**
     * Indicates that the stream has been closed.
     * <p>
     *  表示流已关闭。
     * 
     */

    private boolean closed = false;

    private final boolean syncFlush;

    /**
     * Creates a new output stream with the specified compressor,
     * buffer size and flush mode.

     * <p>
     *  创建具有指定压缩器,缓冲区大小和刷新模式的新输出流。
     * 
     * 
     * @param out the output stream
     * @param def the compressor ("deflater")
     * @param size the output buffer size
     * @param syncFlush
     *        if {@code true} the {@link #flush()} method of this
     *        instance flushes the compressor with flush mode
     *        {@link Deflater#SYNC_FLUSH} before flushing the output
     *        stream, otherwise only flushes the output stream
     *
     * @throws IllegalArgumentException if {@code size <= 0}
     *
     * @since 1.7
     */
    public DeflaterOutputStream(OutputStream out,
                                Deflater def,
                                int size,
                                boolean syncFlush) {
        super(out);
        if (out == null || def == null) {
            throw new NullPointerException();
        } else if (size <= 0) {
            throw new IllegalArgumentException("buffer size <= 0");
        }
        this.def = def;
        this.buf = new byte[size];
        this.syncFlush = syncFlush;
    }


    /**
     * Creates a new output stream with the specified compressor and
     * buffer size.
     *
     * <p>The new output stream instance is created as if by invoking
     * the 4-argument constructor DeflaterOutputStream(out, def, size, false).
     *
     * <p>
     *  创建具有指定压缩器和缓冲区大小的新输出流。
     * 
     *  <p>新的输出流实例是通过调用4参数构造函数DeflaterOutputStream(out,def,size,false)创建的。
     * 
     * 
     * @param out the output stream
     * @param def the compressor ("deflater")
     * @param size the output buffer size
     * @exception IllegalArgumentException if {@code size <= 0}
     */
    public DeflaterOutputStream(OutputStream out, Deflater def, int size) {
        this(out, def, size, false);
    }

    /**
     * Creates a new output stream with the specified compressor, flush
     * mode and a default buffer size.
     *
     * <p>
     *  创建具有指定压缩器,刷新模式和默认缓冲区大小的新输出流。
     * 
     * 
     * @param out the output stream
     * @param def the compressor ("deflater")
     * @param syncFlush
     *        if {@code true} the {@link #flush()} method of this
     *        instance flushes the compressor with flush mode
     *        {@link Deflater#SYNC_FLUSH} before flushing the output
     *        stream, otherwise only flushes the output stream
     *
     * @since 1.7
     */
    public DeflaterOutputStream(OutputStream out,
                                Deflater def,
                                boolean syncFlush) {
        this(out, def, 512, syncFlush);
    }


    /**
     * Creates a new output stream with the specified compressor and
     * a default buffer size.
     *
     * <p>The new output stream instance is created as if by invoking
     * the 3-argument constructor DeflaterOutputStream(out, def, false).
     *
     * <p>
     *  创建具有指定压缩器和默认缓冲区大小的新输出流。
     * 
     *  <p>新的输出流实例是通过调用3参数构造函数DeflaterOutputStream(out,def,false)来创建的。
     * 
     * 
     * @param out the output stream
     * @param def the compressor ("deflater")
     */
    public DeflaterOutputStream(OutputStream out, Deflater def) {
        this(out, def, 512, false);
    }

    boolean usesDefaultDeflater = false;


    /**
     * Creates a new output stream with a default compressor, a default
     * buffer size and the specified flush mode.
     *
     * <p>
     *  使用默认压缩器,默认缓冲区大小和指定的刷新模式创建新的输出流。
     * 
     * 
     * @param out the output stream
     * @param syncFlush
     *        if {@code true} the {@link #flush()} method of this
     *        instance flushes the compressor with flush mode
     *        {@link Deflater#SYNC_FLUSH} before flushing the output
     *        stream, otherwise only flushes the output stream
     *
     * @since 1.7
     */
    public DeflaterOutputStream(OutputStream out, boolean syncFlush) {
        this(out, new Deflater(), 512, syncFlush);
        usesDefaultDeflater = true;
    }

    /**
     * Creates a new output stream with a default compressor and buffer size.
     *
     * <p>The new output stream instance is created as if by invoking
     * the 2-argument constructor DeflaterOutputStream(out, false).
     *
     * <p>
     *  使用默认压缩器和缓冲区大小创建新的输出流。
     * 
     *  <p>新的输出流实例是通过调用2参数构造函数DeflaterOutputStream(out,false)来创建的。
     * 
     * 
     * @param out the output stream
     */
    public DeflaterOutputStream(OutputStream out) {
        this(out, false);
        usesDefaultDeflater = true;
    }

    /**
     * Writes a byte to the compressed output stream. This method will
     * block until the byte can be written.
     * <p>
     *  将一个字节写入压缩输出流。此方法将阻塞,直到可以写入该字节。
     * 
     * 
     * @param b the byte to be written
     * @exception IOException if an I/O error has occurred
     */
    public void write(int b) throws IOException {
        byte[] buf = new byte[1];
        buf[0] = (byte)(b & 0xff);
        write(buf, 0, 1);
    }

    /**
     * Writes an array of bytes to the compressed output stream. This
     * method will block until all the bytes are written.
     * <p>
     * 将一个字节数组写入压缩输出流。此方法将阻塞,直到写入所有字节。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset of the data
     * @param len the length of the data
     * @exception IOException if an I/O error has occurred
     */
    public void write(byte[] b, int off, int len) throws IOException {
        if (def.finished()) {
            throw new IOException("write beyond end of stream");
        }
        if ((off | len | (off + len) | (b.length - (off + len))) < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        if (!def.finished()) {
            def.setInput(b, off, len);
            while (!def.needsInput()) {
                deflate();
            }
        }
    }

    /**
     * Finishes writing compressed data to the output stream without closing
     * the underlying stream. Use this method when applying multiple filters
     * in succession to the same output stream.
     * <p>
     *  完成将压缩数据写入输出流,而不关闭底层流。在将多个过滤器连续应用于同一输出流时,请使用此方法。
     * 
     * 
     * @exception IOException if an I/O error has occurred
     */
    public void finish() throws IOException {
        if (!def.finished()) {
            def.finish();
            while (!def.finished()) {
                deflate();
            }
        }
    }

    /**
     * Writes remaining compressed data to the output stream and closes the
     * underlying stream.
     * <p>
     *  将剩余的压缩数据写入输出流,并关闭基础流。
     * 
     * 
     * @exception IOException if an I/O error has occurred
     */
    public void close() throws IOException {
        if (!closed) {
            finish();
            if (usesDefaultDeflater)
                def.end();
            out.close();
            closed = true;
        }
    }

    /**
     * Writes next block of compressed data to the output stream.
     * <p>
     *  将下一个压缩数据块写入输出流。
     * 
     * 
     * @throws IOException if an I/O error has occurred
     */
    protected void deflate() throws IOException {
        int len = def.deflate(buf, 0, buf.length);
        if (len > 0) {
            out.write(buf, 0, len);
        }
    }

    /**
     * Flushes the compressed output stream.
     *
     * If {@link #DeflaterOutputStream(OutputStream, Deflater, int, boolean)
     * syncFlush} is {@code true} when this compressed output stream is
     * constructed, this method first flushes the underlying {@code compressor}
     * with the flush mode {@link Deflater#SYNC_FLUSH} to force
     * all pending data to be flushed out to the output stream and then
     * flushes the output stream. Otherwise this method only flushes the
     * output stream without flushing the {@code compressor}.
     *
     * <p>
     *  刷新压缩输出流。
     * 
     *  如果构造此压缩输出流时,{@link #DeflaterOutputStream(OutputStream,Deflater,int,boolean)syncFlush}为{@code true},则
     * 
     * @throws IOException if an I/O error has occurred
     *
     * @since 1.7
     */
    public void flush() throws IOException {
        if (syncFlush && !def.finished()) {
            int len = 0;
            while ((len = def.deflate(buf, 0, buf.length, Deflater.SYNC_FLUSH)) > 0)
            {
                out.write(buf, 0, len);
                if (len < buf.length)
                    break;
            }
        }
        out.flush();
    }
}
