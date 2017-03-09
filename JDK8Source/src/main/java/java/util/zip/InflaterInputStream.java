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

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;

/**
 * This class implements a stream filter for uncompressing data in the
 * "deflate" compression format. It is also used as the basis for other
 * decompression filters, such as GZIPInputStream.
 *
 * <p>
 *  这个类实现了一个流过滤器,用于以"deflate"压缩格式解压缩数据。它也用作其他解压缩过滤器的基础,如GZIPInputStream。
 * 
 * 
 * @see         Inflater
 * @author      David Connelly
 */
public
class InflaterInputStream extends FilterInputStream {
    /**
     * Decompressor for this stream.
     * <p>
     *  此流的解压缩。
     * 
     */
    protected Inflater inf;

    /**
     * Input buffer for decompression.
     * <p>
     *  用于解压缩的输入缓冲区。
     * 
     */
    protected byte[] buf;

    /**
     * Length of input buffer.
     * <p>
     *  输入缓冲区的长度。
     * 
     */
    protected int len;

    private boolean closed = false;
    // this flag is set to true after EOF has reached
    private boolean reachEOF = false;

    /**
     * Check to make sure that this stream has not been closed
     * <p>
     *  请检查以确保此流未关闭
     * 
     */
    private void ensureOpen() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        }
    }


    /**
     * Creates a new input stream with the specified decompressor and
     * buffer size.
     * <p>
     *  使用指定的解压缩器和缓冲区大小创建新的输入流。
     * 
     * 
     * @param in the input stream
     * @param inf the decompressor ("inflater")
     * @param size the input buffer size
     * @exception IllegalArgumentException if {@code size <= 0}
     */
    public InflaterInputStream(InputStream in, Inflater inf, int size) {
        super(in);
        if (in == null || inf == null) {
            throw new NullPointerException();
        } else if (size <= 0) {
            throw new IllegalArgumentException("buffer size <= 0");
        }
        this.inf = inf;
        buf = new byte[size];
    }

    /**
     * Creates a new input stream with the specified decompressor and a
     * default buffer size.
     * <p>
     *  使用指定的解压缩器和默认缓冲区大小创建新的输入流。
     * 
     * 
     * @param in the input stream
     * @param inf the decompressor ("inflater")
     */
    public InflaterInputStream(InputStream in, Inflater inf) {
        this(in, inf, 512);
    }

    boolean usesDefaultInflater = false;

    /**
     * Creates a new input stream with a default decompressor and buffer size.
     * <p>
     *  使用默认解压缩器和缓冲区大小创建新的输入流。
     * 
     * 
     * @param in the input stream
     */
    public InflaterInputStream(InputStream in) {
        this(in, new Inflater());
        usesDefaultInflater = true;
    }

    private byte[] singleByteBuf = new byte[1];

    /**
     * Reads a byte of uncompressed data. This method will block until
     * enough input is available for decompression.
     * <p>
     *  读取一个字节的未压缩数据。此方法将阻塞,直到有足够的输入可用于解压缩。
     * 
     * 
     * @return the byte read, or -1 if end of compressed input is reached
     * @exception IOException if an I/O error has occurred
     */
    public int read() throws IOException {
        ensureOpen();
        return read(singleByteBuf, 0, 1) == -1 ? -1 : Byte.toUnsignedInt(singleByteBuf[0]);
    }

    /**
     * Reads uncompressed data into an array of bytes. If <code>len</code> is not
     * zero, the method will block until some input can be decompressed; otherwise,
     * no bytes are read and <code>0</code> is returned.
     * <p>
     *  将未压缩的数据读入字节数组。如果<code> len </code>不为零,则该方法将阻塞,直到某些输入可以解压缩;否则,不读取任何字节,并返回<code> 0 </code>。
     * 
     * 
     * @param b the buffer into which the data is read
     * @param off the start offset in the destination array <code>b</code>
     * @param len the maximum number of bytes read
     * @return the actual number of bytes read, or -1 if the end of the
     *         compressed input is reached or a preset dictionary is needed
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     * @exception ZipException if a ZIP format error has occurred
     * @exception IOException if an I/O error has occurred
     */
    public int read(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }
        try {
            int n;
            while ((n = inf.inflate(b, off, len)) == 0) {
                if (inf.finished() || inf.needsDictionary()) {
                    reachEOF = true;
                    return -1;
                }
                if (inf.needsInput()) {
                    fill();
                }
            }
            return n;
        } catch (DataFormatException e) {
            String s = e.getMessage();
            throw new ZipException(s != null ? s : "Invalid ZLIB data format");
        }
    }

    /**
     * Returns 0 after EOF has been reached, otherwise always return 1.
     * <p>
     * Programs should not count on this method to return the actual number
     * of bytes that could be read without blocking.
     *
     * <p>
     *  在达到EOF后返回0,否则始终返回1。
     * <p>
     *  程序不应该依赖此方法返回可以无阻塞地读取的实际字节数。
     * 
     * 
     * @return     1 before EOF and 0 after EOF.
     * @exception  IOException  if an I/O error occurs.
     *
     */
    public int available() throws IOException {
        ensureOpen();
        if (reachEOF) {
            return 0;
        } else {
            return 1;
        }
    }

    private byte[] b = new byte[512];

    /**
     * Skips specified number of bytes of uncompressed data.
     * <p>
     *  跳过指定的未压缩数据字节数。
     * 
     * 
     * @param n the number of bytes to skip
     * @return the actual number of bytes skipped.
     * @exception IOException if an I/O error has occurred
     * @exception IllegalArgumentException if {@code n < 0}
     */
    public long skip(long n) throws IOException {
        if (n < 0) {
            throw new IllegalArgumentException("negative skip length");
        }
        ensureOpen();
        int max = (int)Math.min(n, Integer.MAX_VALUE);
        int total = 0;
        while (total < max) {
            int len = max - total;
            if (len > b.length) {
                len = b.length;
            }
            len = read(b, 0, len);
            if (len == -1) {
                reachEOF = true;
                break;
            }
            total += len;
        }
        return total;
    }

    /**
     * Closes this input stream and releases any system resources associated
     * with the stream.
     * <p>
     *  关闭此输入流,并释放与流相关联的任何系统资源。
     * 
     * 
     * @exception IOException if an I/O error has occurred
     */
    public void close() throws IOException {
        if (!closed) {
            if (usesDefaultInflater)
                inf.end();
            in.close();
            closed = true;
        }
    }

    /**
     * Fills input buffer with more data to decompress.
     * <p>
     *  用更多数据填充输入缓冲区以进行解压缩。
     * 
     * 
     * @exception IOException if an I/O error has occurred
     */
    protected void fill() throws IOException {
        ensureOpen();
        len = in.read(buf, 0, buf.length);
        if (len == -1) {
            throw new EOFException("Unexpected end of ZLIB input stream");
        }
        inf.setInput(buf, 0, len);
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods. The <code>markSupported</code>
     * method of <code>InflaterInputStream</code> returns
     * <code>false</code>.
     *
     * <p>
     * 测试此输入流是否支持<code>标记</code>和<code>重置</code>方法。
     *  <code> InflaterInputStream </code>的<code> markSupported </code>方法返回<code> false </code>。
     * 
     * 
     * @return  a <code>boolean</code> indicating if this stream type supports
     *          the <code>mark</code> and <code>reset</code> methods.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

    /**
     * Marks the current position in this input stream.
     *
     * <p> The <code>mark</code> method of <code>InflaterInputStream</code>
     * does nothing.
     *
     * <p>
     *  标记此输入流中的当前位置。
     * 
     *  <p> <code> InflaterInputStream </code>的<code> mark </code>方法什么都不做。
     * 
     * 
     * @param   readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     * @see     java.io.InputStream#reset()
     */
    public synchronized void mark(int readlimit) {
    }

    /**
     * Repositions this stream to the position at the time the
     * <code>mark</code> method was last called on this input stream.
     *
     * <p> The method <code>reset</code> for class
     * <code>InflaterInputStream</code> does nothing except throw an
     * <code>IOException</code>.
     *
     * <p>
     *  将此流重新定位到在此输入流上最后调用<code>标记</code>方法时的位置。
     * 
     *  <p>类<code> InflaterInputStream </code>的方法<code> reset </code>不会产生任何异常,除了抛出一个<code> IOException </code>
     * 
     * @exception  IOException  if this method is invoked.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }
}
