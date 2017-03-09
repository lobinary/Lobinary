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


/**
 * A character-stream reader that allows characters to be pushed back into the
 * stream.
 *
 * <p>
 *  一个字符流读取器,允许字符推送回流。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class PushbackReader extends FilterReader {

    /** Pushback buffer */
    private char[] buf;

    /** Current position in buffer */
    private int pos;

    /**
     * Creates a new pushback reader with a pushback buffer of the given size.
     *
     * <p>
     *  创建具有给定大小的回推缓冲区的新推回读取器。
     * 
     * 
     * @param   in   The reader from which characters will be read
     * @param   size The size of the pushback buffer
     * @exception IllegalArgumentException if {@code size <= 0}
     */
    public PushbackReader(Reader in, int size) {
        super(in);
        if (size <= 0) {
            throw new IllegalArgumentException("size <= 0");
        }
        this.buf = new char[size];
        this.pos = size;
    }

    /**
     * Creates a new pushback reader with a one-character pushback buffer.
     *
     * <p>
     *  创建具有单字符推回缓冲区的新推回读取器。
     * 
     * 
     * @param   in  The reader from which characters will be read
     */
    public PushbackReader(Reader in) {
        this(in, 1);
    }

    /** Checks to make sure that the stream has not been closed. */
    private void ensureOpen() throws IOException {
        if (buf == null)
            throw new IOException("Stream closed");
    }

    /**
     * Reads a single character.
     *
     * <p>
     *  读取单个字符。
     * 
     * 
     * @return     The character read, or -1 if the end of the stream has been
     *             reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos < buf.length)
                return buf[pos++];
            else
                return super.read();
        }
    }

    /**
     * Reads characters into a portion of an array.
     *
     * <p>
     *  将字符读入数组的一部分。
     * 
     * 
     * @param      cbuf  Destination buffer
     * @param      off   Offset at which to start writing characters
     * @param      len   Maximum number of characters to read
     *
     * @return     The number of characters read, or -1 if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            try {
                if (len <= 0) {
                    if (len < 0) {
                        throw new IndexOutOfBoundsException();
                    } else if ((off < 0) || (off > cbuf.length)) {
                        throw new IndexOutOfBoundsException();
                    }
                    return 0;
                }
                int avail = buf.length - pos;
                if (avail > 0) {
                    if (len < avail)
                        avail = len;
                    System.arraycopy(buf, pos, cbuf, off, avail);
                    pos += avail;
                    off += avail;
                    len -= avail;
                }
                if (len > 0) {
                    len = super.read(cbuf, off, len);
                    if (len == -1) {
                        return (avail == 0) ? -1 : avail;
                    }
                    return avail + len;
                }
                return avail;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
        }
    }

    /**
     * Pushes back a single character by copying it to the front of the
     * pushback buffer. After this method returns, the next character to be read
     * will have the value <code>(char)c</code>.
     *
     * <p>
     *  通过将单个字符复制到回推缓冲区的前面来将其推回。此方法返回后,要读取的下一个字符将具有值<code>(char)c </code>。
     * 
     * 
     * @param  c  The int value representing a character to be pushed back
     *
     * @exception  IOException  If the pushback buffer is full,
     *                          or if some other I/O error occurs
     */
    public void unread(int c) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (pos == 0)
                throw new IOException("Pushback buffer overflow");
            buf[--pos] = (char) c;
        }
    }

    /**
     * Pushes back a portion of an array of characters by copying it to the
     * front of the pushback buffer.  After this method returns, the next
     * character to be read will have the value <code>cbuf[off]</code>, the
     * character after that will have the value <code>cbuf[off+1]</code>, and
     * so forth.
     *
     * <p>
     *  通过将字符数组的一部分复制到推回缓冲区的前面来将其推回。
     * 此方法返回后,下一个要读取的字符将具有<code> cbuf [off] </code>的值,之后的字符将具有值<code> cbuf [off + 1] </code>等等。
     * 
     * 
     * @param  cbuf  Character array
     * @param  off   Offset of first character to push back
     * @param  len   Number of characters to push back
     *
     * @exception  IOException  If there is insufficient room in the pushback
     *                          buffer, or if some other I/O error occurs
     */
    public void unread(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (len > pos)
                throw new IOException("Pushback buffer overflow");
            pos -= len;
            System.arraycopy(cbuf, off, buf, pos, len);
        }
    }

    /**
     * Pushes back an array of characters by copying it to the front of the
     * pushback buffer.  After this method returns, the next character to be
     * read will have the value <code>cbuf[0]</code>, the character after that
     * will have the value <code>cbuf[1]</code>, and so forth.
     *
     * <p>
     *  通过将字符数组复制到推回缓冲区的前面来推回字符数组。
     * 该方法返回后,要读取的下一个字符将具有值<code> cbuf [0] </code>,其后的字符将具有值<code> cbuf [1] </code> 。
     * 
     * 
     * @param  cbuf  Character array to push back
     *
     * @exception  IOException  If there is insufficient room in the pushback
     *                          buffer, or if some other I/O error occurs
     */
    public void unread(char cbuf[]) throws IOException {
        unread(cbuf, 0, cbuf.length);
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
        synchronized (lock) {
            ensureOpen();
            return (pos < buf.length) || super.ready();
        }
    }

    /**
     * Marks the present position in the stream. The <code>mark</code>
     * for class <code>PushbackReader</code> always throws an exception.
     *
     * <p>
     *  标记流中的当前位置。类<code> PushbackReader </code>的<code>标记</code>总是引发异常。
     * 
     * 
     * @exception  IOException  Always, since mark is not supported
     */
    public void mark(int readAheadLimit) throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Resets the stream. The <code>reset</code> method of
     * <code>PushbackReader</code> always throws an exception.
     *
     * <p>
     *  重置流。 <code> PushbackReader </code>的<code> reset </code>方法总是引发异常。
     * 
     * 
     * @exception  IOException  Always, since reset is not supported
     */
    public void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    /**
     * Tells whether this stream supports the mark() operation, which it does
     * not.
     * <p>
     * 告诉这个流是否支持mark()操作,而不支持。
     * 
     */
    public boolean markSupported() {
        return false;
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it. Once the stream has been closed, further read(),
     * unread(), ready(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * <p>
     *  关闭流并释放与其关联的任何系统资源。一旦流被关闭,进一步的read(),unread(),ready()或skip()调用将抛出IOException。关闭先前关闭的流没有任何效果。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void close() throws IOException {
        super.close();
        buf = null;
    }

    /**
     * Skips characters.  This method will block until some characters are
     * available, an I/O error occurs, or the end of the stream is reached.
     *
     * <p>
     *  跳过字符。此方法将阻塞,直到一些字符可用,I / O错误发生或到达流的结束。
     * 
     * @param  n  The number of characters to skip
     *
     * @return    The number of characters actually skipped
     *
     * @exception  IllegalArgumentException  If <code>n</code> is negative.
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        if (n < 0L)
            throw new IllegalArgumentException("skip value is negative");
        synchronized (lock) {
            ensureOpen();
            int avail = buf.length - pos;
            if (avail > 0) {
                if (n <= avail) {
                    pos += n;
                    return n;
                } else {
                    pos = buf.length;
                    n -= avail;
                }
            }
            return avail + super.skip(n);
        }
    }
}
