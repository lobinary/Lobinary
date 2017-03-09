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

import java.util.Arrays;

/**
 * This class implements a character buffer that can be used as an Writer.
 * The buffer automatically grows when data is written to the stream.  The data
 * can be retrieved using toCharArray() and toString().
 * <P>
 * Note: Invoking close() on this class has no effect, and methods
 * of this class can be called after the stream has closed
 * without generating an IOException.
 *
 * <p>
 *  这个类实现了一个可以用作Writer的字符缓冲区。当数据写入流时,缓冲区自动增长。可以使用toCharArray()和toString()检索数据。
 * <P>
 *  注意：在这个类上调用close()没有效果,这个类的方法可以在流关闭后调用,而不会产生IOException。
 * 
 * 
 * @author      Herb Jellinek
 * @since       JDK1.1
 */
public
class CharArrayWriter extends Writer {
    /**
     * The buffer where data is stored.
     * <p>
     *  存储数据的缓冲区。
     * 
     */
    protected char buf[];

    /**
     * The number of chars in the buffer.
     * <p>
     *  缓冲区中的字符数。
     * 
     */
    protected int count;

    /**
     * Creates a new CharArrayWriter.
     * <p>
     *  创建一个新的CharArrayWriter。
     * 
     */
    public CharArrayWriter() {
        this(32);
    }

    /**
     * Creates a new CharArrayWriter with the specified initial size.
     *
     * <p>
     *  创建具有指定初始大小的新CharArrayWriter。
     * 
     * 
     * @param initialSize  an int specifying the initial buffer size.
     * @exception IllegalArgumentException if initialSize is negative
     */
    public CharArrayWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + initialSize);
        }
        buf = new char[initialSize];
    }

    /**
     * Writes a character to the buffer.
     * <p>
     *  将字符写入缓冲区。
     * 
     */
    public void write(int c) {
        synchronized (lock) {
            int newcount = count + 1;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            buf[count] = (char)c;
            count = newcount;
        }
    }

    /**
     * Writes characters to the buffer.
     * <p>
     *  将字符写入缓冲区。
     * 
     * 
     * @param c the data to be written
     * @param off       the start offset in the data
     * @param len       the number of chars that are written
     */
    public void write(char c[], int off, int len) {
        if ((off < 0) || (off > c.length) || (len < 0) ||
            ((off + len) > c.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        synchronized (lock) {
            int newcount = count + len;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            System.arraycopy(c, off, buf, count, len);
            count = newcount;
        }
    }

    /**
     * Write a portion of a string to the buffer.
     * <p>
     *  将一个字符串的一部分写入缓冲区。
     * 
     * 
     * @param  str  String to be written from
     * @param  off  Offset from which to start reading characters
     * @param  len  Number of characters to be written
     */
    public void write(String str, int off, int len) {
        synchronized (lock) {
            int newcount = count + len;
            if (newcount > buf.length) {
                buf = Arrays.copyOf(buf, Math.max(buf.length << 1, newcount));
            }
            str.getChars(off, off + len, buf, count);
            count = newcount;
        }
    }

    /**
     * Writes the contents of the buffer to another character stream.
     *
     * <p>
     *  将缓冲区的内容写入另一个字符流。
     * 
     * 
     * @param out       the output stream to write to
     * @throws IOException If an I/O error occurs.
     */
    public void writeTo(Writer out) throws IOException {
        synchronized (lock) {
            out.write(buf, 0, count);
        }
    }

    /**
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended. For instance, invoking the <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     *
     * <p>
     *  将指定的字符序列附加到此writer。
     * 
     *  <p>调用此方法的形式<tt> out.append(csq)</tt>的行为与调用的方式完全相同
     * 
     * <pre>
     *  out.write(csq.toString())</pre>
     * 
     *  <p>根据<tt> toString </tt>对字符序列<tt> csq </tt>的规定,整个序列可能不会附加。
     * 例如,调用字符缓冲区的<tt> toString </tt>方法将返回一个子序列,其内容取决于缓冲区的位置和限制。
     * 
     * 
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public CharArrayWriter append(CharSequence csq) {
        String s = (csq == null ? "null" : csq.toString());
        write(s, 0, s.length());
        return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.subSequence(start, end).toString()) </pre>
     *
     * <p>
     *  将指定字符序列的子序列附加到此writer。
     * 
     * <p>当<tt> csq </tt>不是<tt> null </tt>时,对<tt> out.append(csq,start,end)</tt>形式的此方法的调用完全与调用相同的方式
     * 
     * <pre>
     *  out.write(csq.subSequence(start,end).toString())</pre>
     * 
     * 
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public CharArrayWriter append(CharSequence csq, int start, int end) {
        String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
        write(s, 0, s.length());
        return this;
    }

    /**
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     *
     * <p>
     *  将指定的字符附加到此writer。
     * 
     *  <p>调用此方法的形式<tt> out.append(c)</tt>的行为与调用的方式完全相同
     * 
     * <pre>
     *  out.write(c)</pre>
     * 
     * 
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    public CharArrayWriter append(char c) {
        write(c);
        return this;
    }

    /**
     * Resets the buffer so that you can use it again without
     * throwing away the already allocated buffer.
     * <p>
     *  重置缓冲区,以便您可以再次使用它,而不会丢弃已分配的缓冲区。
     * 
     */
    public void reset() {
        count = 0;
    }

    /**
     * Returns a copy of the input data.
     *
     * <p>
     *  返回输入数据的副本。
     * 
     * 
     * @return an array of chars copied from the input data.
     */
    public char toCharArray()[] {
        synchronized (lock) {
            return Arrays.copyOf(buf, count);
        }
    }

    /**
     * Returns the current size of the buffer.
     *
     * <p>
     *  返回缓冲区的当前大小。
     * 
     * 
     * @return an int representing the current size of the buffer.
     */
    public int size() {
        return count;
    }

    /**
     * Converts input data to a string.
     * <p>
     *  将输入数据转换为字符串。
     * 
     * 
     * @return the string.
     */
    public String toString() {
        synchronized (lock) {
            return new String(buf, 0, count);
        }
    }

    /**
     * Flush the stream.
     * <p>
     *  刷新流。
     * 
     */
    public void flush() { }

    /**
     * Close the stream.  This method does not release the buffer, since its
     * contents might still be required. Note: Invoking this method in this class
     * will have no effect.
     * <p>
     *  关闭流。此方法不释放缓冲区,因为它的内容可能仍然是必需的。注意：在此类中调用此方法将不起作用。
     */
    public void close() { }

}
