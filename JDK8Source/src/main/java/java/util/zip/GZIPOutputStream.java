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

import java.io.OutputStream;
import java.io.IOException;

/**
 * This class implements a stream filter for writing compressed data in
 * the GZIP file format.
 * <p>
 *  此类实现用于以GZIP文件格式写入压缩数据的流过滤器。
 * 
 * 
 * @author      David Connelly
 *
 */
public
class GZIPOutputStream extends DeflaterOutputStream {
    /**
     * CRC-32 of uncompressed data.
     * <p>
     *  CRC-32的未压缩数据。
     * 
     */
    protected CRC32 crc = new CRC32();

    /*
     * GZIP header magic number.
     * <p>
     *  GZIP标题幻数。
     * 
     */
    private final static int GZIP_MAGIC = 0x8b1f;

    /*
     * Trailer size in bytes.
     *
     * <p>
     *  预告片大小(以字节为单位)。
     * 
     */
    private final static int TRAILER_SIZE = 8;

    /**
     * Creates a new output stream with the specified buffer size.
     *
     * <p>The new output stream instance is created as if by invoking
     * the 3-argument constructor GZIPOutputStream(out, size, false).
     *
     * <p>
     *  创建具有指定缓冲区大小的新输出流。
     * 
     *  <p>新的输出流实例是通过调用3参数构造函数GZIPOutputStream(out,size,false)来创建的。
     * 
     * 
     * @param out the output stream
     * @param size the output buffer size
     * @exception IOException If an I/O error has occurred.
     * @exception IllegalArgumentException if {@code size <= 0}
     */
    public GZIPOutputStream(OutputStream out, int size) throws IOException {
        this(out, size, false);
    }

    /**
     * Creates a new output stream with the specified buffer size and
     * flush mode.
     *
     * <p>
     *  创建具有指定缓冲区大小和刷新模式的新输出流。
     * 
     * 
     * @param out the output stream
     * @param size the output buffer size
     * @param syncFlush
     *        if {@code true} invocation of the inherited
     *        {@link DeflaterOutputStream#flush() flush()} method of
     *        this instance flushes the compressor with flush mode
     *        {@link Deflater#SYNC_FLUSH} before flushing the output
     *        stream, otherwise only flushes the output stream
     * @exception IOException If an I/O error has occurred.
     * @exception IllegalArgumentException if {@code size <= 0}
     *
     * @since 1.7
     */
    public GZIPOutputStream(OutputStream out, int size, boolean syncFlush)
        throws IOException
    {
        super(out, new Deflater(Deflater.DEFAULT_COMPRESSION, true),
              size,
              syncFlush);
        usesDefaultDeflater = true;
        writeHeader();
        crc.reset();
    }


    /**
     * Creates a new output stream with a default buffer size.
     *
     * <p>The new output stream instance is created as if by invoking
     * the 2-argument constructor GZIPOutputStream(out, false).
     *
     * <p>
     *  创建具有默认缓冲区大小的新输出流。
     * 
     *  <p>新的输出流实例是通过调用2参数构造函数GZIPOutputStream(out,false)来创建的。
     * 
     * 
     * @param out the output stream
     * @exception IOException If an I/O error has occurred.
     */
    public GZIPOutputStream(OutputStream out) throws IOException {
        this(out, 512, false);
    }

    /**
     * Creates a new output stream with a default buffer size and
     * the specified flush mode.
     *
     * <p>
     *  创建具有默认缓冲区大小和指定刷新模式的新输出流。
     * 
     * 
     * @param out the output stream
     * @param syncFlush
     *        if {@code true} invocation of the inherited
     *        {@link DeflaterOutputStream#flush() flush()} method of
     *        this instance flushes the compressor with flush mode
     *        {@link Deflater#SYNC_FLUSH} before flushing the output
     *        stream, otherwise only flushes the output stream
     *
     * @exception IOException If an I/O error has occurred.
     *
     * @since 1.7
     */
    public GZIPOutputStream(OutputStream out, boolean syncFlush)
        throws IOException
    {
        this(out, 512, syncFlush);
    }

    /**
     * Writes array of bytes to the compressed output stream. This method
     * will block until all the bytes are written.
     * <p>
     *  将字节数组写入压缩输出流。此方法将阻塞,直到写入所有字节。
     * 
     * 
     * @param buf the data to be written
     * @param off the start offset of the data
     * @param len the length of the data
     * @exception IOException If an I/O error has occurred.
     */
    public synchronized void write(byte[] buf, int off, int len)
        throws IOException
    {
        super.write(buf, off, len);
        crc.update(buf, off, len);
    }

    /**
     * Finishes writing compressed data to the output stream without closing
     * the underlying stream. Use this method when applying multiple filters
     * in succession to the same output stream.
     * <p>
     *  完成将压缩数据写入输出流,而不关闭底层流。当将多个过滤器连续应用于同一输出流时,请使用此方法。
     * 
     * 
     * @exception IOException if an I/O error has occurred
     */
    public void finish() throws IOException {
        if (!def.finished()) {
            def.finish();
            while (!def.finished()) {
                int len = def.deflate(buf, 0, buf.length);
                if (def.finished() && len <= buf.length - TRAILER_SIZE) {
                    // last deflater buffer. Fit trailer at the end
                    writeTrailer(buf, len);
                    len = len + TRAILER_SIZE;
                    out.write(buf, 0, len);
                    return;
                }
                if (len > 0)
                    out.write(buf, 0, len);
            }
            // if we can't fit the trailer at the end of the last
            // deflater buffer, we write it separately
            byte[] trailer = new byte[TRAILER_SIZE];
            writeTrailer(trailer, 0);
            out.write(trailer);
        }
    }

    /*
     * Writes GZIP member header.
     * <p>
     *  写入GZIP成员头。
     * 
     */
    private void writeHeader() throws IOException {
        out.write(new byte[] {
                      (byte) GZIP_MAGIC,        // Magic number (short)
                      (byte)(GZIP_MAGIC >> 8),  // Magic number (short)
                      Deflater.DEFLATED,        // Compression method (CM)
                      0,                        // Flags (FLG)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Modification time MTIME (int)
                      0,                        // Extra flags (XFLG)
                      0                         // Operating system (OS)
                  });
    }

    /*
     * Writes GZIP member trailer to a byte array, starting at a given
     * offset.
     * <p>
     *  将GZIP成员预告片写入字节数组,从给定的偏移量开始。
     * 
     */
    private void writeTrailer(byte[] buf, int offset) throws IOException {
        writeInt((int)crc.getValue(), buf, offset); // CRC-32 of uncompr. data
        writeInt(def.getTotalIn(), buf, offset + 4); // Number of uncompr. bytes
    }

    /*
     * Writes integer in Intel byte order to a byte array, starting at a
     * given offset.
     * <p>
     *  将英特尔字节顺序的整数写入字节数组,从给定的偏移量开始。
     * 
     */
    private void writeInt(int i, byte[] buf, int offset) throws IOException {
        writeShort(i & 0xffff, buf, offset);
        writeShort((i >> 16) & 0xffff, buf, offset + 2);
    }

    /*
     * Writes short integer in Intel byte order to a byte array, starting
     * at a given offset
     * <p>
     *  以英特尔字节顺序将短整数写入字节数组,从给定的偏移量开始
     */
    private void writeShort(int s, byte[] buf, int offset) throws IOException {
        buf[offset] = (byte)(s & 0xff);
        buf[offset + 1] = (byte)((s >> 8) & 0xff);
    }
}
