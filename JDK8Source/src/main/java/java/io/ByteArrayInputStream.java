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
 * A <code>ByteArrayInputStream</code> contains
 * an internal buffer that contains bytes that
 * may be read from the stream. An internal
 * counter keeps track of the next byte to
 * be supplied by the <code>read</code> method.
 * <p>
 * Closing a <tt>ByteArrayInputStream</tt> has no effect. The methods in
 * this class can be called after the stream has been closed without
 * generating an <tt>IOException</tt>.
 *
 * <p>
 *  <code> ByteArrayInputStream </code>包含一个包含可从流中读取的字节的内部缓冲区内部计数器跟踪由<code> read </code>方法提供的下一个字节
 * <p>
 *  关闭<tt> ByteArrayInputStream </tt>没有效果此类中的方法可以在流关闭后调用,而不会生成<tt> IOException </tt>
 * 
 * 
 * @author  Arthur van Hoff
 * @see     java.io.StringBufferInputStream
 * @since   JDK1.0
 */
public
class ByteArrayInputStream extends InputStream {

    /**
     * An array of bytes that was provided
     * by the creator of the stream. Elements <code>buf[0]</code>
     * through <code>buf[count-1]</code> are the
     * only bytes that can ever be read from the
     * stream;  element <code>buf[pos]</code> is
     * the next byte to be read.
     * <p>
     *  流的创建者提供的字节数组元素<code> buf [0] </code>到<code> buf [count-1] </code>是唯一可以从流;元素<code> buf [pos] </code>是
     * 要读取的下一个字节。
     * 
     */
    protected byte buf[];

    /**
     * The index of the next character to read from the input stream buffer.
     * This value should always be nonnegative
     * and not larger than the value of <code>count</code>.
     * The next byte to be read from the input stream buffer
     * will be <code>buf[pos]</code>.
     * <p>
     * 从输入流缓冲区读取的下一个字符的索引此值应始终为非负且不大于<code> count </code>的值从输入流缓冲区读取的下一个字节为<code> buf [pos] </code>
     * 
     */
    protected int pos;

    /**
     * The currently marked position in the stream.
     * ByteArrayInputStream objects are marked at position zero by
     * default when constructed.  They may be marked at another
     * position within the buffer by the <code>mark()</code> method.
     * The current buffer position is set to this point by the
     * <code>reset()</code> method.
     * <p>
     * If no mark has been set, then the value of mark is the offset
     * passed to the constructor (or 0 if the offset was not supplied).
     *
     * <p>
     *  当构造时,流ByteArrayInputStream对象中当前标记的位置被默认标记在位置零。
     * 可以通过<code> mark()</code>方法在缓冲区中的另一个位置标记当前缓冲区位置设置为此点通过<code> reset()</code>方法。
     * <p>
     *  如果没有设置标记,则mark的值是传递给构造函数的偏移量(如果未提供偏移量,则为0)
     * 
     * 
     * @since   JDK1.1
     */
    protected int mark = 0;

    /**
     * The index one greater than the last valid character in the input
     * stream buffer.
     * This value should always be nonnegative
     * and not larger than the length of <code>buf</code>.
     * It  is one greater than the position of
     * the last byte within <code>buf</code> that
     * can ever be read  from the input stream buffer.
     * <p>
     * 大于输入流缓冲区中最后一个有效字符的索引值该值应始终为非负且不大于<code>的长度</code>它大于<code>中最后一个字节的位置, buf </code>,它们可以从输入流缓冲区读取
     * 
     */
    protected int count;

    /**
     * Creates a <code>ByteArrayInputStream</code>
     * so that it  uses <code>buf</code> as its
     * buffer array.
     * The buffer array is not copied.
     * The initial value of <code>pos</code>
     * is <code>0</code> and the initial value
     * of  <code>count</code> is the length of
     * <code>buf</code>.
     *
     * <p>
     *  创建<code> ByteArrayInputStream </code>以便它使用<code> buf </code>作为其缓冲区数组不复制缓冲区数组<code> pos </code>的初始值为<code>
     *  0 < / code>,并且<code> count </code>的初始值是<code> buf </code>的长度。
     * 
     * 
     * @param   buf   the input buffer.
     */
    public ByteArrayInputStream(byte buf[]) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    /**
     * Creates <code>ByteArrayInputStream</code>
     * that uses <code>buf</code> as its
     * buffer array. The initial value of <code>pos</code>
     * is <code>offset</code> and the initial value
     * of <code>count</code> is the minimum of <code>offset+length</code>
     * and <code>buf.length</code>.
     * The buffer array is not copied. The buffer's mark is
     * set to the specified offset.
     *
     * <p>
     * 创建使用<code> buf </code>作为其缓冲区数组的<code> ByteArrayInputStream </code> <code> pos </code>的初始值为<code> offs
     * et </code>代码> count </code>是<code> offset + length </code>和<code> buflength </code>中的最小值缓冲区的标记被设置为指定的
     * 偏移量。
     * 
     * 
     * @param   buf      the input buffer.
     * @param   offset   the offset in the buffer of the first byte to read.
     * @param   length   the maximum number of bytes to read from the buffer.
     */
    public ByteArrayInputStream(byte buf[], int offset, int length) {
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }

    /**
     * Reads the next byte of data from this input stream. The value
     * byte is returned as an <code>int</code> in the range
     * <code>0</code> to <code>255</code>. If no byte is available
     * because the end of the stream has been reached, the value
     * <code>-1</code> is returned.
     * <p>
     * This <code>read</code> method
     * cannot block.
     *
     * <p>
     *  从此输入流读取数据的下一个字节值字节作为<code> int </code>在<code> 0 </code>到<code> 255 </code>范围内返回如果没有字节可用因为已经到达流的结尾,返回
     * 值<code> -1 </code>。
     * <p>
     *  这个<code> read </code>方法不能阻塞
     * 
     * 
     * @return  the next byte of data, or <code>-1</code> if the end of the
     *          stream has been reached.
     */
    public synchronized int read() {
        return (pos < count) ? (buf[pos++] & 0xff) : -1;
    }

    /**
     * Reads up to <code>len</code> bytes of data into an array of bytes
     * from this input stream.
     * If <code>pos</code> equals <code>count</code>,
     * then <code>-1</code> is returned to indicate
     * end of file. Otherwise, the  number <code>k</code>
     * of bytes read is equal to the smaller of
     * <code>len</code> and <code>count-pos</code>.
     * If <code>k</code> is positive, then bytes
     * <code>buf[pos]</code> through <code>buf[pos+k-1]</code>
     * are copied into <code>b[off]</code>  through
     * <code>b[off+k-1]</code> in the manner performed
     * by <code>System.arraycopy</code>. The
     * value <code>k</code> is added into <code>pos</code>
     * and <code>k</code> is returned.
     * <p>
     * This <code>read</code> method cannot block.
     *
     * <p>
     * 如果<code> pos </code>等于<code> count </code>,则<code> len </code>字节的数据到该输入流的字节数组。代码>返回以指示文件结束。
     * 否则,读取的字节数<code> k </code>等于<code> len </code>和<code> count-pos </code>中的较小值<code> k </code>为正,则通过<code>
     *  buf [pos + k-1] </code>的字节<code> buf [pos] <code> Systemarraycopy </code>执行的方式</code>通过<code> b [off
     *  + k-1] </code>将<code> k </code>值添加到<code> pos </code>和<code> k </code>。
     * 如果<code> pos </code>等于<code> count </code>,则<code> len </code>字节的数据到该输入流的字节数组。代码>返回以指示文件结束。
     * <p>
     *  这个<code> read </code>方法不能阻塞
     * 
     * 
     * @param   b     the buffer into which the data is read.
     * @param   off   the start offset in the destination array <code>b</code>
     * @param   len   the maximum number of bytes read.
     * @return  the total number of bytes read into the buffer, or
     *          <code>-1</code> if there is no more data because the end of
     *          the stream has been reached.
     * @exception  NullPointerException If <code>b</code> is <code>null</code>.
     * @exception  IndexOutOfBoundsException If <code>off</code> is negative,
     * <code>len</code> is negative, or <code>len</code> is greater than
     * <code>b.length - off</code>
     */
    public synchronized int read(byte b[], int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        }

        if (pos >= count) {
            return -1;
        }

        int avail = count - pos;
        if (len > avail) {
            len = avail;
        }
        if (len <= 0) {
            return 0;
        }
        System.arraycopy(buf, pos, b, off, len);
        pos += len;
        return len;
    }

    /**
     * Skips <code>n</code> bytes of input from this input stream. Fewer
     * bytes might be skipped if the end of the input stream is reached.
     * The actual number <code>k</code>
     * of bytes to be skipped is equal to the smaller
     * of <code>n</code> and  <code>count-pos</code>.
     * The value <code>k</code> is added into <code>pos</code>
     * and <code>k</code> is returned.
     *
     * <p>
     * 从该输入流跳过<code> n </code>个字节的输入如果到达输入流的末尾,可能跳过较少的字节。
     * 要跳过的字节的实际数字<code> k </code>等于<code> n </code>和<code> count-pos </code>中的<code>值<code> >。
     * 
     * 
     * @param   n   the number of bytes to be skipped.
     * @return  the actual number of bytes skipped.
     */
    public synchronized long skip(long n) {
        long k = count - pos;
        if (n < k) {
            k = n < 0 ? 0 : n;
        }

        pos += k;
        return k;
    }

    /**
     * Returns the number of remaining bytes that can be read (or skipped over)
     * from this input stream.
     * <p>
     * The value returned is <code>count&nbsp;- pos</code>,
     * which is the number of bytes remaining to be read from the input buffer.
     *
     * <p>
     *  返回可从此输入流读取(或跳过)的剩余字节数
     * <p>
     *  返回的值为<code> count&nbsp;  -  pos </code>,这是要从输入缓冲区中读取的剩余字节数
     * 
     * 
     * @return  the number of remaining bytes that can be read (or skipped
     *          over) from this input stream without blocking.
     */
    public synchronized int available() {
        return count - pos;
    }

    /**
     * Tests if this <code>InputStream</code> supports mark/reset. The
     * <code>markSupported</code> method of <code>ByteArrayInputStream</code>
     * always returns <code>true</code>.
     *
     * <p>
     *  测试此<code> InputStream </code>是否支持标记/重置<code> ByteArrayInputStream </code>的<code> markSupported </code>
     * 方法总是返回<code> true </code>。
     * 
     * 
     * @since   JDK1.1
     */
    public boolean markSupported() {
        return true;
    }

    /**
     * Set the current marked position in the stream.
     * ByteArrayInputStream objects are marked at position zero by
     * default when constructed.  They may be marked at another
     * position within the buffer by this method.
     * <p>
     * If no mark has been set, then the value of the mark is the
     * offset passed to the constructor (or 0 if the offset was not
     * supplied).
     *
     * <p> Note: The <code>readAheadLimit</code> for this class
     *  has no meaning.
     *
     * <p>
     * 设置流中当前标记的位置ByteArrayInputStream对象在构造时默认在位置零标记它们可以通过此方法在缓冲区中的另一个位置标记
     * <p>
     *  如果没有设置标记,则标记的值是传递给构造函数的偏移量(如果未提供偏移量,则为0)
     * 
     *  <p>注意：这个类的<code> readAheadLimit </code>没有意义
     * 
     * 
     * @since   JDK1.1
     */
    public void mark(int readAheadLimit) {
        mark = pos;
    }

    /**
     * Resets the buffer to the marked position.  The marked position
     * is 0 unless another position was marked or an offset was specified
     * in the constructor.
     * <p>
     */
    public synchronized void reset() {
        pos = mark;
    }

    /**
     * Closing a <tt>ByteArrayInputStream</tt> has no effect. The methods in
     * this class can be called after the stream has been closed without
     * generating an <tt>IOException</tt>.
     * <p>
     *  将缓冲区重置为标记位置除非标记了另一个位置或在构造函数中指定了偏移量,否则标记位置为0
     * 
     */
    public void close() throws IOException {
    }

}
