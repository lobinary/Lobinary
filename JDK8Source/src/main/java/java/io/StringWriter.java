/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * A character stream that collects its output in a string buffer, which can
 * then be used to construct a string.
 * <p>
 * Closing a <tt>StringWriter</tt> has no effect. The methods in this class
 * can be called after the stream has been closed without generating an
 * <tt>IOException</tt>.
 *
 * <p>
 *  一个字符流,它在字符串缓冲区中收集其输出,然后可以用于构造字符串。
 * <p>
 *  关闭<tt> StringWriter </tt>没有任何效果。可以在流关闭后调用此类中的方法,而不生成<tt> IOException </tt>。
 * 
 * 
 * @author      Mark Reinhold
 * @since       JDK1.1
 */

public class StringWriter extends Writer {

    private StringBuffer buf;

    /**
     * Create a new string writer using the default initial string-buffer
     * size.
     * <p>
     *  使用默认的初始字符串缓冲区大小创建新的字符串写入程序。
     * 
     */
    public StringWriter() {
        buf = new StringBuffer();
        lock = buf;
    }

    /**
     * Create a new string writer using the specified initial string-buffer
     * size.
     *
     * <p>
     *  使用指定的初始字符串缓冲区大小创建新的字符串写入程序。
     * 
     * 
     * @param initialSize
     *        The number of <tt>char</tt> values that will fit into this buffer
     *        before it is automatically expanded
     *
     * @throws IllegalArgumentException
     *         If <tt>initialSize</tt> is negative
     */
    public StringWriter(int initialSize) {
        if (initialSize < 0) {
            throw new IllegalArgumentException("Negative buffer size");
        }
        buf = new StringBuffer(initialSize);
        lock = buf;
    }

    /**
     * Write a single character.
     * <p>
     *  写一个单个字符。
     * 
     */
    public void write(int c) {
        buf.append((char) c);
    }

    /**
     * Write a portion of an array of characters.
     *
     * <p>
     *  写一个字符数组的一部分。
     * 
     * 
     * @param  cbuf  Array of characters
     * @param  off   Offset from which to start writing characters
     * @param  len   Number of characters to write
     */
    public void write(char cbuf[], int off, int len) {
        if ((off < 0) || (off > cbuf.length) || (len < 0) ||
            ((off + len) > cbuf.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        buf.append(cbuf, off, len);
    }

    /**
     * Write a string.
     * <p>
     *  写一个字符串。
     * 
     */
    public void write(String str) {
        buf.append(str);
    }

    /**
     * Write a portion of a string.
     *
     * <p>
     *  写一个字符串的一部分。
     * 
     * 
     * @param  str  String to be written
     * @param  off  Offset from which to start writing characters
     * @param  len  Number of characters to write
     */
    public void write(String str, int off, int len)  {
        buf.append(str.substring(off, off + len));
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
    public StringWriter append(CharSequence csq) {
        if (csq == null)
            write("null");
        else
            write(csq.toString());
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
     *  <p>当<tt> csq </tt>不是<tt> null </tt>时,对<tt> out.append(csq,start,end)</tt>形式的此方法的调用完全与调用相同的方式
     * 
     * <pre>
     * out.write(csq.subSequence(start,end).toString())</pre>
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
    public StringWriter append(CharSequence csq, int start, int end) {
        CharSequence cs = (csq == null ? "null" : csq);
        write(cs.subSequence(start, end).toString());
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
    public StringWriter append(char c) {
        write(c);
        return this;
    }

    /**
     * Return the buffer's current value as a string.
     * <p>
     *  以字符串形式返回缓冲区的当前值。
     * 
     */
    public String toString() {
        return buf.toString();
    }

    /**
     * Return the string buffer itself.
     *
     * <p>
     *  返回字符串缓冲区本身。
     * 
     * 
     * @return StringBuffer holding the current buffer value.
     */
    public StringBuffer getBuffer() {
        return buf;
    }

    /**
     * Flush the stream.
     * <p>
     *  刷新流。
     * 
     */
    public void flush() {
    }

    /**
     * Closing a <tt>StringWriter</tt> has no effect. The methods in this
     * class can be called after the stream has been closed without generating
     * an <tt>IOException</tt>.
     * <p>
     *  关闭<tt> StringWriter </tt>没有任何效果。可以在流关闭后调用此类中的方法,而不生成<tt> IOException </tt>。
     */
    public void close() throws IOException {
    }

}
