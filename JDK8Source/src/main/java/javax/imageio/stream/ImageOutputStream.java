/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.stream;

import java.io.DataOutput;
import java.io.IOException;

/**
 * A seekable output stream interface for use by
 * <code>ImageWriter</code>s.  Various output destinations, such as
 * <code>OutputStream</code>s and <code>File</code>s, as well as
 * future fast I/O destinations may be "wrapped" by a suitable
 * implementation of this interface for use by the Image I/O API.
 *
 * <p> Unlike a standard <code>OutputStream</code>, ImageOutputStream
 * extends its counterpart, <code>ImageInputStream</code>.  Thus it is
 * possible to read from the stream as it is being written.  The same
 * seek and flush positions apply to both reading and writing, although
 * the semantics for dealing with a non-zero bit offset before a byte-aligned
 * write are necessarily different from the semantics for dealing with
 * a non-zero bit offset before a byte-aligned read.  When reading bytes,
 * any bit offset is set to 0 before the read; when writing bytes, a
 * non-zero bit offset causes the remaining bits in the byte to be written
 * as 0s.  The byte-aligned write then starts at the next byte position.
 *
 * <p>
 *  可搜索的输出流接口供<code> ImageWriter </code>使用。
 * 诸如<code> OutputStream </code>和<code> File </code>之类的各种输出目的地以及未来的快速I / O目的地可以由该接口的适当实现来"包装" Image I / 
 * O API。
 *  可搜索的输出流接口供<code> ImageWriter </code>使用。
 * 
 *  <p>与标准<code> OutputStream </code>不同,ImageOutputStream扩展了其对应的<code> ImageInputStream </code>。
 * 因此,可以在流被写入时从流中读取。尽管在字节对齐写入之前处理非零位偏移的语义必然不同于在字节对齐之前处理非零位偏移的语义,但是相同的查找和刷新位置适用于读取和写入,对齐读。
 * 当读取字节时,在读取之前将任何位偏移设置为0;当写入字节时,非零位偏移导致字节中的剩余位被写为0。然后,字节对齐写入从下一个字节位置开始。
 * 
 * 
 * @see ImageInputStream
 *
 */
public interface ImageOutputStream extends ImageInputStream, DataOutput {

    /**
     * Writes a single byte to the stream at the current position.
     * The 24 high-order bits of <code>b</code> are ignored.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.  Implementers can use the
     * {@link ImageOutputStreamImpl#flushBits flushBits}
     * method of {@link ImageOutputStreamImpl ImageOutputStreamImpl}
     * to guarantee this.
     *
     * <p>
     *  在当前位置的流中写入一个字节。忽略<code> b </code>的24个高位。
     * 
     * <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 实现者可以使用{@link ImageOutputStreamImpl ImageOutputStreamImpl}的{@link ImageOutputStreamImpl#flushBits flushBits}
     * 方法来保证这一点。
     * <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param b an <code>int</code> whose lower 8 bits are to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void write(int b) throws IOException;

    /**
     * Writes a sequence of bytes to the stream at the current
     * position.  If <code>b.length</code> is 0, nothing is written.
     * The byte <code>b[0]</code> is written first, then the byte
     * <code>b[1]</code>, and so on.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  在当前位置的流中写入一个字节序列。如果<code> b.length </code>为0,则不写入任何内容。
     * 首先写入字节<code> b [0] </code>,然后写入字节<code> b [1] </code>,依此类推。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param b an array of <code>byte</code>s to be written.
     *
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void write(byte b[]) throws IOException;

    /**
     * Writes a sequence of bytes to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The byte <code>b[off]</code> is written first, then the byte
     * <code>b[off + 1]</code>, and so on.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.  Implementers can use the
     * {@link ImageOutputStreamImpl#flushBits flushBits}
     * method of {@link ImageOutputStreamImpl ImageOutputStreamImpl}
     * to guarantee this.
     *
     * <p>
     *  在当前位置的流中写入一个字节序列。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入字节<code> b [off] </code>,然后写入字节<code> b [off + 1] </code>,依此类推。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 实现者可以使用{@link ImageOutputStreamImpl ImageOutputStreamImpl}的{@link ImageOutputStreamImpl#flushBits flushBits}
     * 方法来保证这一点。
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param b an array of <code>byte</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>byte</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>b.length</code>.
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void write(byte b[], int off, int len) throws IOException;

    /**
     * Writes a <code>boolean</code> value to the stream.  If
     * <code>v</code> is true, the value <code>(byte)1</code> is
     * written; if <code>v</code> is false, the value
     * <code>(byte)0</code> is written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  向流中写入<code> boolean </code>值。
     * 如果<code> v </code>为真,则写入<code>(byte)1 </code>如果<code> v </code>为false,则写入<code>(byte)0 </code>。
     * 
     * <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v the <code>boolean</code> to be written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeBoolean(boolean v) throws IOException;

    /**
     * Writes the 8 low-order bits of <code>v</code> to the
     * stream. The 24 high-order bits of <code>v</code> are ignored.
     * (This means that <code>writeByte</code> does exactly the same
     * thing as <code>write</code> for an integer argument.)
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将<code> v </code>的8个低位写入流。忽略<code> v </code>的24个高阶位。
     *  (这意味着<code> writeByte </code>和整数参数<code> write </code>完全一样)。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v an <code>int</code> containing the byte value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeByte(int v) throws IOException;

    /**
     * Writes the 16 low-order bits of <code>v</code> to the
     * stream. The 16 high-order bits of <code>v</code> are ignored.
     * If the stream uses network byte order, the bytes written, in
     * order, will be:
     *
     * <pre>
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * (byte)(v &amp; 0xff)
     * </pre>
     *
     * Otherwise, the bytes written will be:
     *
     * <pre>
     * (byte)(v &amp; 0xff)
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * </pre>
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将<code> v </code>的16个低位写入流。忽略<code> v </code>的高16位。如果流使用网络字节顺序,则按顺序写入的字节将是：
     * 
     * <pre>
     *  (字节)((v> 8)&amp; 0xff)(字节)(v&amp; 0xff)
     * </pre>
     * 
     *  否则,写入的字节将是：
     * 
     * <pre>
     *  (字节)(v&amp; 0xff)(字节)((v>&gt; 8)&amp; 0xff)
     * </pre>
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v an <code>int</code> containing the short value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeShort(int v) throws IOException;

    /**
     * This method is a synonym for {@link #writeShort writeShort}.
     *
     * <p>
     *  此方法是{@link #writeShort writeShort}的同义词。
     * 
     * 
     * @param v an <code>int</code> containing the char (unsigned
     * short) value to be written.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see #writeShort(int)
     */
    void writeChar(int v) throws IOException;

    /**
     * Writes the 32 bits of <code>v</code> to the stream.  If the
     * stream uses network byte order, the bytes written, in order,
     * will be:
     *
     * <pre>
     * (byte)((v &gt;&gt; 24) &amp; 0xff)
     * (byte)((v &gt;&gt; 16) &amp; 0xff)
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * (byte)(v &amp; 0xff)
     * </pre>
     *
     * Otheriwse, the bytes written will be:
     *
     * <pre>
     * (byte)(v &amp; 0xff)
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * (byte)((v &gt;&gt; 16) &amp; 0xff)
     * (byte)((v &gt;&gt; 24) &amp; 0xff)
     * </pre>
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将32位<code> v </code>写入流。如果流使用网络字节顺序,则按顺序写入的字节将是：
     * 
     * <pre>
     *  (字节)((v&gt;&gt; 24)&amp; 0xff)(字节)((v> 16)&amp; 0xff) )(v&amp; 0xff)
     * </pre>
     * 
     *  Otheriwse,写入的字节将是：
     * 
     * <pre>
     * (字节)(v&amp; 0xff)(字节)((v> 8)&amp; 0xff)(字节)((v> 16)&amp; 0xff) ; 24)&amp; 0xff)
     * </pre>
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v an <code>int</code> containing the value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeInt(int v) throws IOException;

    /**
     * Writes the 64 bits of <code>v</code> to the stream.  If the
     * stream uses network byte order, the bytes written, in order,
     * will be:
     *
     * <pre>
     * (byte)((v &gt;&gt; 56) &amp; 0xff)
     * (byte)((v &gt;&gt; 48) &amp; 0xff)
     * (byte)((v &gt;&gt; 40) &amp; 0xff)
     * (byte)((v &gt;&gt; 32) &amp; 0xff)
     * (byte)((v &gt;&gt; 24) &amp; 0xff)
     * (byte)((v &gt;&gt; 16) &amp; 0xff)
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * (byte)(v &amp; 0xff)
     * </pre>
     *
     * Otherwise, the bytes written will be:
     *
     * <pre>
     * (byte)(v &amp; 0xff)
     * (byte)((v &gt;&gt; 8) &amp; 0xff)
     * (byte)((v &gt;&gt; 16) &amp; 0xff)
     * (byte)((v &gt;&gt; 24) &amp; 0xff)
     * (byte)((v &gt;&gt; 32) &amp; 0xff)
     * (byte)((v &gt;&gt; 40) &amp; 0xff)
     * (byte)((v &gt;&gt; 48) &amp; 0xff)
     * (byte)((v &gt;&gt; 56) &amp; 0xff)
     * </pre>
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将64位的<code> v </code>写入流。如果流使用网络字节顺序,则按顺序写入的字节将是：
     * 
     * <pre>
     *  (字节)((v> 56)&amp; 0xff)(字节)((v> 48)&amp; 0xff) )((v&gt;&gt; 32)&amp; 0xff)(字节)((v> 24)&amp; 0xff) (v
     * >&gt; 8)&amp; 0xff)(字节)(v&amp; 0xff)。
     * </pre>
     * 
     *  否则,写入的字节将是：
     * 
     * <pre>
     *  (字节)(v&amp; 0xff)(字节)((v> 8)&amp; 0xff)(字节)((v> 16)&amp; 0xff) ; 24)&amp; 0xff)(字节)((v> 32)&amp; 0xf
     * f)(字节)((v> 40)&amp; 0xff) )&amp; 0xff)(字节)((v>&gt; 56)&amp; 0xff)。
     * </pre>
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v a <code>long</code> containing the value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeLong(long v) throws IOException;

    /**
     * Writes a <code>float</code> value, which is comprised of four
     * bytes, to the output stream. It does this as if it first
     * converts this <code>float</code> value to an <code>int</code>
     * in exactly the manner of the <code>Float.floatToIntBits</code>
     * method and then writes the int value in exactly the manner of
     * the <code>writeInt</code> method.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     * 将一个由四个字节组成的<code> float </code>值写入输出流。
     * 它这样做,好像它首先按照<code> Float.floatToIntBits </code>方法的方式将此<code> float </code>值转换为<code> int </code>值与<code>
     *  writeInt </code>方法的方式完全相同。
     * 将一个由四个字节组成的<code> float </code>值写入输出流。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v a <code>float</code> containing the value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeFloat(float v) throws IOException;

    /**
     * Writes a <code>double</code> value, which is comprised of four
     * bytes, to the output stream. It does this as if it first
     * converts this <code>double</code> value to an <code>long</code>
     * in exactly the manner of the
     * <code>Double.doubleToLongBits</code> method and then writes the
     * long value in exactly the manner of the <code>writeLong</code>
     * method.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将一个由四个字节组成的<code> double </code>值写入输出流。
     * 它这样做,好像它首先以<code> Double.doubleToLongBits </code>方法的方式将此<code> double </code>值转换为<code> long </code>,
     * 然后将long值与<code> writeLong </code>方法的方式完全相同。
     *  将一个由四个字节组成的<code> double </code>值写入输出流。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param v a <code>double</code> containing the value to be
     * written.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeDouble(double v) throws IOException;

    /**
     * Writes a string to the output stream. For every character in
     * the string <code>s</code>, taken in order, one byte is written
     * to the output stream. If <code>s</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.
     *
     * <p> If <code>s.length</code> is zero, then no bytes are
     * written. Otherwise, the character <code>s[0]</code> is written
     * first, then <code>s[1]</code>, and so on; the last character
     * written is <code>s[s.length-1]</code>. For each character, one
     * byte is written, the low-order byte, in exactly the manner of
     * the <code>writeByte</code> method. The high-order eight bits of
     * each character in the string are ignored.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将字符串写入输出流。对于字符串<code> s </code>中的每个字符,按顺序,将一个字节写入输出流。
     * 如果<code> s </code>是<code> null </code>,则抛出<code> NullPointerException </code>。
     * 
     * <p>如果<code> s.length </code>为零,则不会写入任何字节。
     * 否则,首先写入字符<code> s [0] </code>,然后写入<code> s [1] </code>,依此类推;所写的最后一个字符是<code> s [s.length-1] </code>。
     * 对于每个字符,以<code> writeByte </code>方法的方式写入一个字节,低字节。字符串中每个字符的高8位将被忽略。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param s a <code>String</code> containing the value to be
     * written.
     *
     * @exception NullPointerException if <code>s</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeBytes(String s) throws IOException;

    /**
     * Writes a string to the output stream. For every character in
     * the string <code>s</code>, taken in order, two bytes are
     * written to the output stream, ordered according to the current
     * byte order setting.  If network byte order is being used, the
     * high-order byte is written first; the order is reversed
     * otherwise.  If <code>s</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.
     *
     * <p> If <code>s.length</code> is zero, then no bytes are
     * written. Otherwise, the character <code>s[0]</code> is written
     * first, then <code>s[1]</code>, and so on; the last character
     * written is <code>s[s.length-1]</code>.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  将字符串写入输出流。对于字符串<code> s </code>中的每个字符,按顺序,将两个字节写入输出流,根据当前字节顺序设置排序。如果使用网络字节顺序,则首先写入高位字节;否则顺序相反。
     * 如果<code> s </code>是<code> null </code>,则抛出<code> NullPointerException </code>。
     * 
     *  <p>如果<code> s.length </code>为零,则不会写入任何字节。
     * 否则,首先写入字符<code> s [0] </code>,然后写入<code> s [1] </code>,依此类推;所写的最后一个字符是<code> s [s.length-1] </code>。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param s a <code>String</code> containing the value to be
     * written.
     *
     * @exception NullPointerException if <code>s</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeChars(String s) throws IOException;

    /**
     * Writes two bytes of length information to the output stream in
     * network byte order, followed by the
     * <a href="../../../java/io/DataInput.html#modified-utf-8">modified
     * UTF-8</a>
     * representation of every character in the string <code>s</code>.
     * If <code>s</code> is <code>null</code>, a
     * <code>NullPointerException</code> is thrown.  Each character in
     * the string <code>s</code> is converted to a group of one, two,
     * or three bytes, depending on the value of the character.
     *
     * <p> If a character <code>c</code> is in the range
     * <code>&#92;u0001</code> through <code>&#92;u007f</code>, it is
     * represented by one byte:
     *
     * <p><pre>
     * (byte)c
     * </pre>
     *
     * <p> If a character <code>c</code> is <code>&#92;u0000</code> or
     * is in the range <code>&#92;u0080</code> through
     * <code>&#92;u07ff</code>, then it is represented by two bytes,
     * to be written in the order shown:
     *
     * <p> <pre><code>
     * (byte)(0xc0 | (0x1f &amp; (c &gt;&gt; 6)))
     * (byte)(0x80 | (0x3f &amp; c))
     * </code></pre>
     *
     * <p> If a character <code>c</code> is in the range
     * <code>&#92;u0800</code> through <code>uffff</code>, then it is
     * represented by three bytes, to be written in the order shown:
     *
     * <p> <pre><code>
     * (byte)(0xe0 | (0x0f &amp; (c &gt;&gt; 12)))
     * (byte)(0x80 | (0x3f &amp; (c &gt;&gt; 6)))
     * (byte)(0x80 | (0x3f &amp; c))
     * </code></pre>
     *
     * <p> First, the total number of bytes needed to represent all
     * the characters of <code>s</code> is calculated. If this number
     * is larger than <code>65535</code>, then a
     * <code>UTFDataFormatException</code> is thrown. Otherwise, this
     * length is written to the output stream in exactly the manner of
     * the <code>writeShort</code> method; after this, the one-, two-,
     * or three-byte representation of each character in the string
     * <code>s</code> is written.
     *
     * <p> The current byte order setting is ignored.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p><strong>Note:</strong> This method should not be used in
     * the  implementation of image formats that use standard UTF-8,
     * because  the modified UTF-8 used here is incompatible with
     * standard UTF-8.
     *
     * <p>
     * 按网络字节顺序将两个字节的长度信息写入输出流,然后是<a href="../../../java/io/DataInput.html#modified-utf-8">修改的UTF- 8 </a>表示字符
     * 串<code> s </code>中的每个字符。
     * 如果<code> s </code>是<code> null </code>,则抛出<code> NullPointerException </code>。
     * 字符串<code> s </code>中的每个字符都将转换为一个包含一个,两个或三个字节的组,具体取决于字符的值。
     * 
     *  <p>如果字符<code> c </code>在<code> \ u0001 </code>到<code> \ u007f </code>的范围内,
     * 
     *  <p> <pre>(byte)c
     * </pre>
     * 
     *  <p>如果字符<code> c </code>为<code> \ u0000 </code>或位于<code> \ u0080 </code>到<code> \ u07ff </code>它由两个字节
     * 表示,以所示的顺序写入：。
     * 
     *  <p> <pre> <code>(byte)(0xc0 |(0x1f&amp;(c> 6)))(byte)(0x80 |(0x3f&amp; c))</code>
     * 
     *  <p>如果字符<code> c </code>在<code> \ u0800 </code>到<code> uffff </code>的范围内,则由三个字节表示,订单显示：
     * 
     *  <p> <pre> <code>(byte)(0xe0 |(0x0f&amp;(c> 12)))(byte)(0x80 |(0x3f&amp;(c> 6)))字节)(0x80 |(0x3f&amp; 
     * c))</code> </pre>。
     * 
     * <p>首先,计算表示<code> s </code>的所有字符所需的总字节数。
     * 如果此数字大于<code> 65535 </code>,则会抛出<code> UTFDataFormatException </code>。
     * 否则,该长度以<code> writeShort </code>方法的方式写入输出流;在此之后,写入字符串<code> s </code>中的每个字符的一个,两个或三个字节的表示。
     * 
     *  <p>当前字节顺序设置被忽略。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     *  <p> <strong>请注意</strong>：此方法不应用于实施使用标准UTF-8的图像格式,因为此处使用的修改的UTF-8与标准UTF-8不兼容。
     * 
     * 
     * @param s a <code>String</code> containing the value to be
     * written.
     *
     * @exception NullPointerException if <code>s</code> is
     * <code>null</code>.
     * @exception java.io.UTFDataFormatException if the modified UTF-8
     * representation of <code>s</code> requires more than 65536 bytes.
     * @exception IOException if an I/O error occurs.
     */
    void writeUTF(String s) throws IOException;

    /**
     * Writes a sequence of shorts to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The short <code>s[off]</code> is written first, then the short
     * <code>s[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  在当前位置向流中写入短序列。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入短<code> s [off] </code>,然后写短<code> s [off + 1] </code>,等等。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param s an array of <code>short</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>short</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>s.length</code>.
     * @exception NullPointerException if <code>s</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeShorts(short[] s, int off, int len) throws IOException;

    /**
     * Writes a sequence of chars to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The char <code>c[off]</code> is written first, then the char
     * <code>c[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     * 在当前位置的流中写入一个字符序列。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入char <code> c [off] </code>,然后输入char <code> c [off + 1] </code>,等等。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param c an array of <code>char</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>char</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>c.length</code>.
     * @exception NullPointerException if <code>c</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeChars(char[] c, int off, int len) throws IOException;

    /**
     * Writes a sequence of ints to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The int <code>i[off]</code> is written first, then the int
     * <code>i[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  在当前位置的流中写入一个int序列。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入int <code> i [off] </code>,然后是int <code> i [off + 1] </code>,依此类推。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param i an array of <code>int</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>int</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>i.length</code>.
     * @exception NullPointerException if <code>i</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeInts(int[] i, int off, int len) throws IOException;

    /**
     * Writes a sequence of longs to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The long <code>l[off]</code> is written first, then the long
     * <code>l[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  在当前位置的流中写入一个longs序列。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入长<code> l [off] </code>,然后写长<code> l [off + 1] </code>,等等。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param l an array of <code>long</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>long</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>l.length</code>.
     * @exception NullPointerException if <code>l</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeLongs(long[] l, int off, int len) throws IOException;

    /**
     * Writes a sequence of floats to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The float <code>f[off]</code> is written first, then the float
     * <code>f[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     * 在当前位置的流中写入一系列浮点数。如果<code> len </code>为0,则不写入任何内容。
     * 首先写入浮动<code> f [off] </code>,然后浮动<code> f [off + 1] </code>,依此类推。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param f an array of <code>float</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>float</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>f.length</code>.
     * @exception NullPointerException if <code>f</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeFloats(float[] f, int off, int len) throws IOException;

    /**
     * Writes a sequence of doubles to the stream at the current
     * position.  If <code>len</code> is 0, nothing is written.
     * The double <code>d[off]</code> is written first, then the double
     * <code>d[off + 1]</code>, and so on.  The byte order of the
     * stream is used to determine the order in which the individual
     * bytes are written.
     *
     * <p> If the bit offset within the stream is non-zero, the
     * remainder of the current byte is padded with 0s
     * and written out first.  The bit offset will be 0 after the
     * write.
     *
     * <p>
     *  在当前位置的流中写入一个双精度序列。如果<code> len </code>为0,则不写入任何内容。
     * 双写<code> d [off] </code>先写,然后双写<code> d [off + 1] </code>,依此类推。流的字节顺序用于确定写入各个字节的顺序。
     * 
     *  <p>如果流中的位偏移量不为零,则当前字节的剩余部分将用0填充并首先写入。写入后位偏移为0。
     * 
     * 
     * @param d an array of <code>doubles</code>s to be written.
     * @param off the start offset in the data.
     * @param len the number of <code>double</code>s to write.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>d.length</code>.
     * @exception NullPointerException if <code>d</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    void writeDoubles(double[] d, int off, int len) throws IOException;

    /**
     * Writes a single bit, given by the least significant bit of the
     * argument, to the stream at the current bit offset within the
     * current byte position.  The upper 31 bits of the argument are
     * ignored.  The given bit replaces the previous bit at that
     * position.  The bit offset is advanced by one and reduced modulo
     * 8.
     *
     * <p> If any bits of a particular byte have never been set
     * at the time the byte is flushed to the destination, those
     * bits will be set to 0 automatically.
     *
     * <p>
     *  将由参数的最低有效位给出的单个位写入当前字节位置内当前位偏移处的流。参数的高31位将被忽略。给定位替换该位置处的前一位。比特偏移被提前一并减模8。
     * 
     *  <p>如果特定字节的任何位在字节刷新到目的地时从未设置过,那么这些位将自动设置为0。
     * 
     * 
     * @param bit an <code>int</code> whose least significant bit
     * is to be written to the stream.
     *
     * @exception IOException if an I/O error occurs.
     */
    void writeBit(int bit) throws IOException;

    /**
     * Writes a sequence of bits, given by the <code>numBits</code>
     * least significant bits of the <code>bits</code> argument in
     * left-to-right order, to the stream at the current bit offset
     * within the current byte position.  The upper <code>64 -
     * numBits</code> bits of the argument are ignored.  The bit
     * offset is advanced by <code>numBits</code> and reduced modulo
     * 8.  Note that a bit offset of 0 always indicates the
     * most-significant bit of the byte, and bytes of bits are written
     * out in sequence as they are encountered.  Thus bit writes are
     * always effectively in network byte order.  The actual stream
     * byte order setting is ignored.
     *
     * <p> Bit data may be accumulated in memory indefinitely, until
     * <code>flushBefore</code> is called.  At that time, all bit data
     * prior to the flushed position will be written.
     *
     * <p> If any bits of a particular byte have never been set
     * at the time the byte is flushed to the destination, those
     * bits will be set to 0 automatically.
     *
     * <p>
     * 以从左到右的顺序写入由<code> bits </code>参数的<code> numBits </code>个最低有效位给出的位序列到当前位在当前位偏移处的流中字节位置。
     * 参数的上部<code> 64  -  numBits </code>位将被忽略。
     * 位偏移提前<code> numBits </code>并减少模8.注意,位偏移量0始终指示字节的最高有效位,并且位的字节按顺序写出,因为它们被遇到。因此,位写入总是以网络字节顺序有效。
     * 实际流字节顺序设置被忽略。
     * 
     *  <p>位数据可能会无限期地累积在存储器中,直到调用<code> flushBefore </code>。此时,将写入冲刷位置之前的所有位数据。
     * 
     * 
     * @param bits a <code>long</code> containing the bits to be
     * written, starting with the bit in position <code>numBits -
     * 1</code> down to the least significant bit.
     *
     * @param numBits an <code>int</code> between 0 and 64, inclusive.
     *
     * @exception IllegalArgumentException if <code>numBits</code> is
     * not between 0 and 64, inclusive.
     * @exception IOException if an I/O error occurs.
     */
    void writeBits(long bits, int numBits) throws IOException;

    /**
     * Flushes all data prior to the given position to the underlying
     * destination, such as an <code>OutputStream</code> or
     * <code>File</code>.  Attempting to seek to the flushed portion
     * of the stream will result in an
     * <code>IndexOutOfBoundsException</code>.
     *
     * <p>
     *  <p>如果特定字节的任何位在字节刷新到目的地时从未设置过,那么这些位将自动设置为0。
     * 
     * 
     * @param pos a <code>long</code> containing the length of the
     * stream prefix that may be flushed to the destination.
     *
     * @exception IndexOutOfBoundsException if <code>pos</code> lies
     * in the flushed portion of the stream or past the current stream
     * position.
     * @exception IOException if an I/O error occurs.
     */
    void flushBefore(long pos) throws IOException;
}
