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
 * Writes text to a character-output stream, buffering characters so as to
 * provide for the efficient writing of single characters, arrays, and strings.
 *
 * <p> The buffer size may be specified, or the default size may be accepted.
 * The default is large enough for most purposes.
 *
 * <p> A newLine() method is provided, which uses the platform's own notion of
 * line separator as defined by the system property <tt>line.separator</tt>.
 * Not all platforms use the newline character ('\n') to terminate lines.
 * Calling this method to terminate each output line is therefore preferred to
 * writing a newline character directly.
 *
 * <p> In general, a Writer sends its output immediately to the underlying
 * character or byte stream.  Unless prompt output is required, it is advisable
 * to wrap a BufferedWriter around any Writer whose write() operations may be
 * costly, such as FileWriters and OutputStreamWriters.  For example,
 *
 * <pre>
 * PrintWriter out
 *   = new PrintWriter(new BufferedWriter(new FileWriter("foo.out")));
 * </pre>
 *
 * will buffer the PrintWriter's output to the file.  Without buffering, each
 * invocation of a print() method would cause characters to be converted into
 * bytes that would then be written immediately to the file, which can be very
 * inefficient.
 *
 * <p>
 *  将文本写入字符输出流,缓冲字符以便提供单个字符,数组和字符串的有效写入。
 * 
 *  <p>可以指定缓冲区大小,也可以接受默认大小。默认值对于大多数用途都足够大。
 * 
 *  <p>提供了一个newLine()方法,它使用平台自己的由系统属性<tt> line.separator </tt>定义的行分隔符的概念。并非所有平台都使用换行符('\ n')来终止行。
 * 因此,调用此方法来终止每个输出行都优于直接写入换行符。
 * 
 *  <p>通常,Writer将其输出立即发送到底层字符或字节流。
 * 除非需要提示输出,否则建议在写入操作可能很昂贵的任何Writer(例如FileWriter和OutputStreamWriter)周围封装BufferedWriter。例如,。
 * 
 * <pre>
 *  PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("foo.out")));
 * </pre>
 * 
 *  将缓冲PrintWriter的输出到文件。没有缓冲,每次调用print()方法将导致字符被转换为字节,然后立即写入文件,这可能是非常低效的。
 * 
 * 
 * @see PrintWriter
 * @see FileWriter
 * @see OutputStreamWriter
 * @see java.nio.file.Files#newBufferedWriter
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class BufferedWriter extends Writer {

    private Writer out;

    private char cb[];
    private int nChars, nextChar;

    private static int defaultCharBufferSize = 8192;

    /**
     * Line separator string.  This is the value of the line.separator
     * property at the moment that the stream was created.
     * <p>
     *  行分隔符字符串。这是创建流时的line.separator属性的值。
     * 
     */
    private String lineSeparator;

    /**
     * Creates a buffered character-output stream that uses a default-sized
     * output buffer.
     *
     * <p>
     * 创建使用默认大小的输出缓冲区的缓冲字符输出流。
     * 
     * 
     * @param  out  A Writer
     */
    public BufferedWriter(Writer out) {
        this(out, defaultCharBufferSize);
    }

    /**
     * Creates a new buffered character-output stream that uses an output
     * buffer of the given size.
     *
     * <p>
     *  创建使用给定大小的输出缓冲区的新缓冲字符输出流。
     * 
     * 
     * @param  out  A Writer
     * @param  sz   Output-buffer size, a positive integer
     *
     * @exception  IllegalArgumentException  If {@code sz <= 0}
     */
    public BufferedWriter(Writer out, int sz) {
        super(out);
        if (sz <= 0)
            throw new IllegalArgumentException("Buffer size <= 0");
        this.out = out;
        cb = new char[sz];
        nChars = sz;
        nextChar = 0;

        lineSeparator = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
    }

    /** Checks to make sure that the stream has not been closed */
    private void ensureOpen() throws IOException {
        if (out == null)
            throw new IOException("Stream closed");
    }

    /**
     * Flushes the output buffer to the underlying character stream, without
     * flushing the stream itself.  This method is non-private only so that it
     * may be invoked by PrintStream.
     * <p>
     *  将输出缓冲区刷新到基础字符流,而不刷新流本身。这个方法是非私有的,所以它可以被PrintStream调用。
     * 
     */
    void flushBuffer() throws IOException {
        synchronized (lock) {
            ensureOpen();
            if (nextChar == 0)
                return;
            out.write(cb, 0, nextChar);
            nextChar = 0;
        }
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
        synchronized (lock) {
            ensureOpen();
            if (nextChar >= nChars)
                flushBuffer();
            cb[nextChar++] = (char) c;
        }
    }

    /**
     * Our own little min method, to avoid loading java.lang.Math if we've run
     * out of file descriptors and we're trying to print a stack trace.
     * <p>
     *  我们自己的小min方法,以避免加载java.lang.Math,如果我们用尽了文件描述符,我们试图打印一个堆栈跟踪。
     * 
     */
    private int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    /**
     * Writes a portion of an array of characters.
     *
     * <p> Ordinarily this method stores characters from the given array into
     * this stream's buffer, flushing the buffer to the underlying stream as
     * needed.  If the requested length is at least as large as the buffer,
     * however, then this method will flush the buffer and write the characters
     * directly to the underlying stream.  Thus redundant
     * <code>BufferedWriter</code>s will not copy data unnecessarily.
     *
     * <p>
     *  写入字符数组的一部分。
     * 
     *  通常这种方法将来自给定数组的字符存储到该流的缓冲区中,根据需要将缓冲区刷新到底层流。然而,如果所请求的长度至少与缓冲器一样大,则该方法将刷新缓冲器并将字符直接写入基础流。
     * 因此,冗余的<code> BufferedWriter </code>将不会不必要地复制数据。
     * 
     * 
     * @param  cbuf  A character array
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();
            if ((off < 0) || (off > cbuf.length) || (len < 0) ||
                ((off + len) > cbuf.length) || ((off + len) < 0)) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return;
            }

            if (len >= nChars) {
                /* If the request length exceeds the size of the output buffer,
                   flush the buffer and then write the data directly.  In this
                /* <p>
                /*  刷新缓冲区,然后直接写入数据。在这里
                /* 
                /* 
                   way buffered streams will cascade harmlessly. */
                flushBuffer();
                out.write(cbuf, off, len);
                return;
            }

            int b = off, t = off + len;
            while (b < t) {
                int d = min(nChars - nextChar, t - b);
                System.arraycopy(cbuf, b, cb, nextChar, d);
                b += d;
                nextChar += d;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }

    /**
     * Writes a portion of a String.
     *
     * <p> If the value of the <tt>len</tt> parameter is negative then no
     * characters are written.  This is contrary to the specification of this
     * method in the {@linkplain java.io.Writer#write(java.lang.String,int,int)
     * superclass}, which requires that an {@link IndexOutOfBoundsException} be
     * thrown.
     *
     * <p>
     *  写入字符串的一部分。
     * 
     *  <p>如果<tt> len </tt>参数的值为负,则不会写入任何字符。
     * 这与{@linkplain java.io.Writer#write(java.lang.String,int,int)superclass}中的这个方法的规范相反,这需要抛出一个{@link IndexOutOfBoundsException}
     * 。
     *  <p>如果<tt> len </tt>参数的值为负,则不会写入任何字符。
     * 
     * 
     * @param  s     String to be written
     * @param  off   Offset from which to start reading characters
     * @param  len   Number of characters to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(String s, int off, int len) throws IOException {
        synchronized (lock) {
            ensureOpen();

            int b = off, t = off + len;
            while (b < t) {
                int d = min(nChars - nextChar, t - b);
                s.getChars(b, b + d, cb, nextChar);
                b += d;
                nextChar += d;
                if (nextChar >= nChars)
                    flushBuffer();
            }
        }
    }

    /**
     * Writes a line separator.  The line separator string is defined by the
     * system property <tt>line.separator</tt>, and is not necessarily a single
     * newline ('\n') character.
     *
     * <p>
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void newLine() throws IOException {
        write(lineSeparator);
    }

    /**
     * Flushes the stream.
     *
     * <p>
     * 写入行分隔符。行分隔符字符串由系统属性<tt> line.separator </tt>定义,不一定是单个换行符('\ n')字符。
     * 
     * 
     * @exception  IOException  If an I/O error occurs
     */
    public void flush() throws IOException {
        synchronized (lock) {
            flushBuffer();
            out.flush();
        }
    }

    @SuppressWarnings("try")
    public void close() throws IOException {
        synchronized (lock) {
            if (out == null) {
                return;
            }
            try (Writer w = out) {
                flushBuffer();
            } finally {
                out = null;
                cb = null;
            }
        }
    }
}
