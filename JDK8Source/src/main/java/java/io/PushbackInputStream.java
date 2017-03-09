/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>PushbackInputStream</code> adds
 * functionality to another input stream, namely
 * the  ability to "push back" or "unread"
 * one byte. This is useful in situations where
 * it is  convenient for a fragment of code
 * to read an indefinite number of data bytes
 * that  are delimited by a particular byte
 * value; after reading the terminating byte,
 * the  code fragment can "unread" it, so that
 * the next read operation on the input stream
 * will reread the byte that was pushed back.
 * For example, bytes representing the  characters
 * constituting an identifier might be terminated
 * by a byte representing an  operator character;
 * a method whose job is to read just an identifier
 * can read until it  sees the operator and
 * then push the operator back to be re-read.
 *
 * <p>
 *  <code> PushbackInputStream </code>向另一个输入流添加功能,即"推回"或"未读"一个字节的能力。
 * 这在方便代码片段读取由特定字节值分隔的不定数量的数据字节的情况下是有用的;在读取终止字节之后,代码片段可以"未读"它,使得输入流上的下一个读取操作将重新读取被推回的字节。
 * 例如,表示构成标识符的字符的字节可以由表示操作符的字节终止;一种其工作是只读取标识符的方法可以读取,直到它看到操作者,然后推回操作员重新读取。
 * 
 * 
 * @author  David Connelly
 * @author  Jonathan Payne
 * @since   JDK1.0
 */
public
class PushbackInputStream extends FilterInputStream {
    /**
     * The pushback buffer.
     * <p>
     *  推回缓冲区。
     * 
     * 
     * @since   JDK1.1
     */
    protected byte[] buf;

    /**
     * The position within the pushback buffer from which the next byte will
     * be read.  When the buffer is empty, <code>pos</code> is equal to
     * <code>buf.length</code>; when the buffer is full, <code>pos</code> is
     * equal to zero.
     *
     * <p>
     *  回读缓冲器内的下一个字节将被读取的位置。
     * 当缓冲区为空时,<code> pos </code>等于<code> buf.length </code>;当缓冲区已满时,<code> pos </code>等于零。
     * 
     * 
     * @since   JDK1.1
     */
    protected int pos;

    /**
     * Check to make sure that this stream has not been closed
     * <p>
     *  请检查以确保此流未关闭
     * 
     */
    private void ensureOpen() throws IOException {
        if (in == null)
            throw new IOException("Stream closed");
    }

    /**
     * Creates a <code>PushbackInputStream</code>
     * with a pushback buffer of the specified <code>size</code>,
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. Initially,
     * there is no pushed-back byte  (the field
     * <code>pushBack</code> is initialized to
     * <code>-1</code>).
     *
     * <p>
     *  使用指定<code> size </code>的推回缓冲区创建<code> PushbackInputStream </code>,并将输入流<code>保存在</code>中以供日后使用。
     * 最初,没有推回字节(字段<code> pushBack </code>被初始化为<code> -1 </code>)。
     * 
     * 
     * @param  in    the input stream from which bytes will be read.
     * @param  size  the size of the pushback buffer.
     * @exception IllegalArgumentException if {@code size <= 0}
     * @since  JDK1.1
     */
    public PushbackInputStream(InputStream in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.buf = new byte[size];
        this.pos = size;
    }

    /**
     * Creates a <code>PushbackInputStream</code>
     * and saves its  argument, the input stream
     * <code>in</code>, for later use. Initially,
     * there is no pushed-back byte  (the field
     * <code>pushBack</code> is initialized to
     * <code>-1</code>).
     *
     * <p>
     * 创建<code> PushbackInputStream </code>并保存其参数,输入流<code>在</code>中,供以后使用。
     * 最初,没有推回字节(字段<code> pushBack </code>被初始化为<code> -1 </code>)。
     * 
     * 
     * @param   in   the input stream from which bytes will be read.
     */
    public PushbackInputStream(InputStream in) {
        this(in, 1);
    }

    /**
     * Reads the next byte of data from this input stream. The value
     * byte is returned as an <code>int</code> in the range
     * <code>0</code> to <code>255</code>. If no byte is available
     * because the end of the stream has been reached, the value
     * <code>-1</code> is returned. This method blocks until input data
     * is available, the end of the stream is detected, or an exception
     * is thrown.
     *
     * <p> This method returns the most recently pushed-back byte, if there is
     * one, and otherwise calls the <code>read</code> method of its underlying
     * input stream and returns whatever value that method returns.
     *
     * <p>
     *  从此输入流读取数据的下一个字节。值字节作为<code> 0 </code>到<code> 255 </code>范围内的<code> int </code>返回。
     * 如果没有字节可用,因为已经到达流的结尾,则返回值<code> -1 </code>。此方法阻塞,直到输入数据可用,检测到流的结尾,或抛出异常。
     * 
     *  <p>此方法返回最近推回的字节(如果有的话),否则调用其底层输入流的<code> read </code>方法,并返回该方法返回的任何值。
     * 
     * 
     * @return     the next byte of data, or <code>-1</code> if the end of the
     *             stream has been reached.
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.InputStream#read()
     */
    public int read() throws IOException {
        ensureOpen();
        if (pos < buf.length) {
            return buf[pos++] & 0xff;
        }
        return super.read();
    }

    /**
     * Reads up to <code>len</code> bytes of data from this input stream into
     * an array of bytes.  This method first reads any pushed-back bytes; after
     * that, if fewer than <code>len</code> bytes have been read then it
     * reads from the underlying input stream. If <code>len</code> is not zero, the method
     * blocks until at least 1 byte of input is available; otherwise, no
     * bytes are read and <code>0</code> is returned.
     *
     * <p>
     *  从此输入流中读取最多<code> len </code>字节的数据为字节数组。此方法首先读取任何推回的字节;之后,如果少于<code> len </code>个字节被读取,则它从底层输入流读取。
     * 如果<code> len </code>不为零,则该方法阻塞,直到至少1个字节的输入可用;否则,不读取任何字节,并返回<code> 0 </code>。
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
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.InputStream#read(byte[], int, int)
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

        int avail = buf.length - pos;
        if (avail > 0) {
            if (len < avail) {
                avail = len;
            }
            System.arraycopy(buf, pos, b, off, avail);
            pos += avail;
            off += avail;
            len -= avail;
        }
        if (len > 0) {
            len = super.read(b, off, len);
            if (len == -1) {
                return avail == 0 ? -1 : avail;
            }
            return avail + len;
        }
        return avail;
    }

    /**
     * Pushes back a byte by copying it to the front of the pushback buffer.
     * After this method returns, the next byte to be read will have the value
     * <code>(byte)b</code>.
     *
     * <p>
     *  通过将一个字节复制到回推缓冲区的前面来推回一个字节。此方法返回后,要读取的下一个字节将具有值<code>(byte)b </code>。
     * 
     * 
     * @param      b   the <code>int</code> value whose low-order
     *                  byte is to be pushed back.
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the byte, or this input stream has been closed by
     *            invoking its {@link #close()} method.
     */
    public void unread(int b) throws IOException {
        ensureOpen();
        if (pos == 0) {
            throw new IOException("Push back buffer is full");
        }
        buf[--pos] = (byte)b;
    }

    /**
     * Pushes back a portion of an array of bytes by copying it to the front
     * of the pushback buffer.  After this method returns, the next byte to be
     * read will have the value <code>b[off]</code>, the byte after that will
     * have the value <code>b[off+1]</code>, and so forth.
     *
     * <p>
     * 通过将字节数组的一部分复制到推回缓冲区的前面来将其推回。
     * 该方法返回后,要读取的下一个字节将具有值<code> b [off] </code>,其后的字节将具有值<code> b [off + 1] </code>等等。
     * 
     * 
     * @param b the byte array to push back.
     * @param off the start offset of the data.
     * @param len the number of bytes to push back.
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the specified number of bytes,
     *            or this input stream has been closed by
     *            invoking its {@link #close()} method.
     * @since     JDK1.1
     */
    public void unread(byte[] b, int off, int len) throws IOException {
        ensureOpen();
        if (len > pos) {
            throw new IOException("Push back buffer is full");
        }
        pos -= len;
        System.arraycopy(b, off, buf, pos, len);
    }

    /**
     * Pushes back an array of bytes by copying it to the front of the
     * pushback buffer.  After this method returns, the next byte to be read
     * will have the value <code>b[0]</code>, the byte after that will have the
     * value <code>b[1]</code>, and so forth.
     *
     * <p>
     *  通过将字节数组复制到推回缓冲区的前面来推回字节数组。
     * 在该方法返回之后,要读取的下一个字节将具有值<code> b [0] </code>,其后的字节将具有值<code> b [1] </code> 。
     * 
     * 
     * @param b the byte array to push back
     * @exception IOException If there is not enough room in the pushback
     *            buffer for the specified number of bytes,
     *            or this input stream has been closed by
     *            invoking its {@link #close()} method.
     * @since     JDK1.1
     */
    public void unread(byte[] b) throws IOException {
        unread(b, 0, b.length);
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or
     * skipped over) from this input stream without blocking by the next
     * invocation of a method for this input stream. The next invocation might be
     * the same thread or another thread.  A single read or skip of this
     * many bytes will not block, but may read or skip fewer bytes.
     *
     * <p> The method returns the sum of the number of bytes that have been
     * pushed back and the value returned by {@link
     * java.io.FilterInputStream#available available}.
     *
     * <p>
     *  返回此输入流中可以读取(或跳过)的字节数的估计值,而不会通过下一次调用此输入流的方法进行阻止。下一次调用可能是同一个线程或另一个线程。单个读取或跳过这么多字节不会阻塞,但可以读取或跳过更少的字节。
     * 
     *  <p>该方法返回已压回的字节数与{@link java.io.FilterInputStream#available available}返回的值之和。
     * 
     * 
     * @return     the number of bytes that can be read (or skipped over) from
     *             the input stream without blocking.
     * @exception  IOException  if this input stream has been closed by
     *             invoking its {@link #close()} method,
     *             or an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.InputStream#available()
     */
    public int available() throws IOException {
        ensureOpen();
        int n = buf.length - pos;
        int avail = super.available();
        return n > (Integer.MAX_VALUE - avail)
                    ? Integer.MAX_VALUE
                    : n + avail;
    }

    /**
     * Skips over and discards <code>n</code> bytes of data from this
     * input stream. The <code>skip</code> method may, for a variety of
     * reasons, end up skipping over some smaller number of bytes,
     * possibly zero.  If <code>n</code> is negative, no bytes are skipped.
     *
     * <p> The <code>skip</code> method of <code>PushbackInputStream</code>
     * first skips over the bytes in the pushback buffer, if any.  It then
     * calls the <code>skip</code> method of the underlying input stream if
     * more bytes need to be skipped.  The actual number of bytes skipped
     * is returned.
     *
     * <p>
     *  跳过并丢弃来自此输入流的<code> n </code>个字节的数据。由于各种原因,<code> skip </code>方法可能跳过一些较小数量的字节,可能为零。
     * 如果<code> n </code>为负,则不跳过任何字节。
     * 
     * <p> <code> PushbackInputStream </code>的<code> skip </code>方法会跳过回推缓冲区中的字节(如果有的话)。
     * 如果需要跳过更多的字节,它然后调用底层输入流的<code> skip </code>方法。将返回实际跳过的字节数。
     * 
     * 
     * @param      n  {@inheritDoc}
     * @return     {@inheritDoc}
     * @exception  IOException  if the stream does not support seek,
     *            or the stream has been closed by
     *            invoking its {@link #close()} method,
     *            or an I/O error occurs.
     * @see        java.io.FilterInputStream#in
     * @see        java.io.InputStream#skip(long n)
     * @since      1.2
     */
    public long skip(long n) throws IOException {
        ensureOpen();
        if (n <= 0) {
            return 0;
        }

        long pskip = buf.length - pos;
        if (pskip > 0) {
            if (n < pskip) {
                pskip = n;
            }
            pos += pskip;
            n -= pskip;
        }
        if (n > 0) {
            pskip += super.skip(n);
        }
        return pskip;
    }

    /**
     * Tests if this input stream supports the <code>mark</code> and
     * <code>reset</code> methods, which it does not.
     *
     * <p>
     *  测试此输入流是否支持<code>标记</code>和<code>重置</code>方法,而不是。
     * 
     * 
     * @return   <code>false</code>, since this class does not support the
     *           <code>mark</code> and <code>reset</code> methods.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.InputStream#reset()
     */
    public boolean markSupported() {
        return false;
    }

    /**
     * Marks the current position in this input stream.
     *
     * <p> The <code>mark</code> method of <code>PushbackInputStream</code>
     * does nothing.
     *
     * <p>
     *  标记此输入流中的当前位置。
     * 
     *  <p> <code> PushbackInputStream </code>的<code>标记</code>方法什么也不做。
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
     * <code>PushbackInputStream</code> does nothing except throw an
     * <code>IOException</code>.
     *
     * <p>
     *  将此流重新定位到在此输入流上最后调用<code>标记</code>方法时的位置。
     * 
     *  <p>类<code> PushbackInputStream </code>中<code> reset </code>的方法除了抛出一个<code> IOException </code>
     * 
     * 
     * @exception  IOException  if this method is invoked.
     * @see     java.io.InputStream#mark(int)
     * @see     java.io.IOException
     */
    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Closes this input stream and releases any system resources
     * associated with the stream.
     * Once the stream has been closed, further read(), unread(),
     * available(), reset(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * <p>
     * 
     * @exception  IOException  if an I/O error occurs.
     */
    public synchronized void close() throws IOException {
        if (in == null)
            return;
        in.close();
        in = null;
        buf = null;
    }
}
