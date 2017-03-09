/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * A buffered character-input stream that keeps track of line numbers.  This
 * class defines methods {@link #setLineNumber(int)} and {@link
 * #getLineNumber()} for setting and getting the current line number
 * respectively.
 *
 * <p> By default, line numbering begins at 0. This number increments at every
 * <a href="#lt">line terminator</a> as the data is read, and can be changed
 * with a call to <tt>setLineNumber(int)</tt>.  Note however, that
 * <tt>setLineNumber(int)</tt> does not actually change the current position in
 * the stream; it only changes the value that will be returned by
 * <tt>getLineNumber()</tt>.
 *
 * <p> A line is considered to be <a name="lt">terminated</a> by any one of a
 * line feed ('\n'), a carriage return ('\r'), or a carriage return followed
 * immediately by a linefeed.
 *
 * <p>
 *  缓冲的字符输入流,其跟踪行号。此类定义方法{@link #setLineNumber(int)}和{@link #getLineNumber()}分别设置和获取当前行号。
 * 
 *  <p>默认情况下,行编号从0开始。该数字在读取数据时在每个<a href="#lt">行终止符</a>处递增,并且可以通过调用<tt> setLineNumber (int)</tt>。
 * 但请注意,<tt> setLineNumber(int)</tt>实际上不会更改流中的当前位置;它只更改<tt> getLineNumber()</tt>返回的值。
 * 
 *  <p>线被认为是<a name="lt">终止于</a>换行符('\ n'),回车符('\ r')或回车符然后立即换行。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class LineNumberReader extends BufferedReader {

    /** The current line number */
    private int lineNumber = 0;

    /** The line number of the mark, if any */
    private int markedLineNumber; // Defaults to 0

    /** If the next character is a line feed, skip it */
    private boolean skipLF;

    /** The skipLF flag when the mark was set */
    private boolean markedSkipLF;

    /**
     * Create a new line-numbering reader, using the default input-buffer
     * size.
     *
     * <p>
     *  使用默认的输入缓冲区大小创建一个新的行号码阅读器。
     * 
     * 
     * @param  in
     *         A Reader object to provide the underlying stream
     */
    public LineNumberReader(Reader in) {
        super(in);
    }

    /**
     * Create a new line-numbering reader, reading characters into a buffer of
     * the given size.
     *
     * <p>
     *  创建一个新的行号码阅读器,读取字符到给定大小的缓冲区。
     * 
     * 
     * @param  in
     *         A Reader object to provide the underlying stream
     *
     * @param  sz
     *         An int specifying the size of the buffer
     */
    public LineNumberReader(Reader in, int sz) {
        super(in, sz);
    }

    /**
     * Set the current line number.
     *
     * <p>
     *  设置当前行号。
     * 
     * 
     * @param  lineNumber
     *         An int specifying the line number
     *
     * @see #getLineNumber
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Get the current line number.
     *
     * <p>
     *  获取当前行号。
     * 
     * 
     * @return  The current line number
     *
     * @see #setLineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Read a single character.  <a href="#lt">Line terminators</a> are
     * compressed into single newline ('\n') characters.  Whenever a line
     * terminator is read the current line number is incremented.
     *
     * <p>
     *  读取单个字符。 <a href="#lt">行终结符</a>压缩为单个换行符('\ n')个字符。无论何时读取行终止符,当前行号都会递增。
     * 
     * 
     * @return  The character read, or -1 if the end of the stream has been
     *          reached
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    @SuppressWarnings("fallthrough")
    public int read() throws IOException {
        synchronized (lock) {
            int c = super.read();
            if (skipLF) {
                if (c == '\n')
                    c = super.read();
                skipLF = false;
            }
            switch (c) {
            case '\r':
                skipLF = true;
            case '\n':          /* Fall through */
                lineNumber++;
                return '\n';
            }
            return c;
        }
    }

    /**
     * Read characters into a portion of an array.  Whenever a <a
     * href="#lt">line terminator</a> is read the current line number is
     * incremented.
     *
     * <p>
     *  将字符读入数组的一部分。每当读取<a href="#lt">行终结符</a>时,当前行号都会递增。
     * 
     * 
     * @param  cbuf
     *         Destination buffer
     *
     * @param  off
     *         Offset at which to start storing characters
     *
     * @param  len
     *         Maximum number of characters to read
     *
     * @return  The number of bytes read, or -1 if the end of the stream has
     *          already been reached
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    @SuppressWarnings("fallthrough")
    public int read(char cbuf[], int off, int len) throws IOException {
        synchronized (lock) {
            int n = super.read(cbuf, off, len);

            for (int i = off; i < off + n; i++) {
                int c = cbuf[i];
                if (skipLF) {
                    skipLF = false;
                    if (c == '\n')
                        continue;
                }
                switch (c) {
                case '\r':
                    skipLF = true;
                case '\n':      /* Fall through */
                    lineNumber++;
                    break;
                }
            }

            return n;
        }
    }

    /**
     * Read a line of text.  Whenever a <a href="#lt">line terminator</a> is
     * read the current line number is incremented.
     *
     * <p>
     * 读一行文本。每当读取<a href="#lt">行终结符</a>时,当前行号都会递增。
     * 
     * 
     * @return  A String containing the contents of the line, not including
     *          any <a href="#lt">line termination characters</a>, or
     *          <tt>null</tt> if the end of the stream has been reached
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public String readLine() throws IOException {
        synchronized (lock) {
            String l = super.readLine(skipLF);
            skipLF = false;
            if (l != null)
                lineNumber++;
            return l;
        }
    }

    /** Maximum skip-buffer size */
    private static final int maxSkipBufferSize = 8192;

    /** Skip buffer, null until allocated */
    private char skipBuffer[] = null;

    /**
     * Skip characters.
     *
     * <p>
     *  跳过字符。
     * 
     * 
     * @param  n
     *         The number of characters to skip
     *
     * @return  The number of characters actually skipped
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  IllegalArgumentException
     *          If <tt>n</tt> is negative
     */
    public long skip(long n) throws IOException {
        if (n < 0)
            throw new IllegalArgumentException("skip() value is negative");
        int nn = (int) Math.min(n, maxSkipBufferSize);
        synchronized (lock) {
            if ((skipBuffer == null) || (skipBuffer.length < nn))
                skipBuffer = new char[nn];
            long r = n;
            while (r > 0) {
                int nc = read(skipBuffer, 0, (int) Math.min(r, nn));
                if (nc == -1)
                    break;
                r -= nc;
            }
            return n - r;
        }
    }

    /**
     * Mark the present position in the stream.  Subsequent calls to reset()
     * will attempt to reposition the stream to this point, and will also reset
     * the line number appropriately.
     *
     * <p>
     *  标记流中的当前位置。后续调用reset()将尝试将流重新定位到此点,并且还将适当地重置行号。
     * 
     * 
     * @param  readAheadLimit
     *         Limit on the number of characters that may be read while still
     *         preserving the mark.  After reading this many characters,
     *         attempting to reset the stream may fail.
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
        synchronized (lock) {
            super.mark(readAheadLimit);
            markedLineNumber = lineNumber;
            markedSkipLF     = skipLF;
        }
    }

    /**
     * Reset the stream to the most recent mark.
     *
     * <p>
     *  将流重置为最近的标记。
     * 
     * @throws  IOException
     *          If the stream has not been marked, or if the mark has been
     *          invalidated
     */
    public void reset() throws IOException {
        synchronized (lock) {
            super.reset();
            lineNumber = markedLineNumber;
            skipLF     = markedSkipLF;
        }
    }

}
