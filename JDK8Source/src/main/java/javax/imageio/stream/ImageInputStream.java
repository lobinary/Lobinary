/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Closeable;
import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * A seekable input stream interface for use by
 * <code>ImageReader</code>s.  Various input sources, such as
 * <code>InputStream</code>s and <code>File</code>s,
 * as well as future fast I/O sources may be "wrapped" by a suitable
 * implementation of this interface for use by the Image I/O API.
 *
 * <p>
 *  可搜索的输入流接口供<code> ImageReader </code>使用。
 * 诸如<code> InputStream </code>和<code> File </code>之类的各种输入源以及未来的快速I / O源可以由该接口的适当实现来"包装" Image I / O API
 * 。
 *  可搜索的输入流接口供<code> ImageReader </code>使用。
 * 
 * 
 * @see ImageInputStreamImpl
 * @see FileImageInputStream
 * @see FileCacheImageInputStream
 * @see MemoryCacheImageInputStream
 *
 */
public interface ImageInputStream extends DataInput, Closeable {

    /**
     * Sets the desired byte order for future reads of data values
     * from this stream.  For example, the sequence of bytes '0x01
     * 0x02 0x03 0x04' if read as a 4-byte integer would have the
     * value '0x01020304' using network byte order and the value
     * '0x04030201' under the reverse byte order.
     *
     * <p> The enumeration class <code>java.nio.ByteOrder</code> is
     * used to specify the byte order.  A value of
     * <code>ByteOrder.BIG_ENDIAN</code> specifies so-called
     * big-endian or network byte order, in which the high-order byte
     * comes first.  Motorola and Sparc processors store data in this
     * format, while Intel processors store data in the reverse
     * <code>ByteOrder.LITTLE_ENDIAN</code> order.
     *
     * <p> The byte order has no effect on the results returned from
     * the <code>readBits</code> method (or the value written by
     * <code>ImageOutputStream.writeBits</code>).
     *
     * <p>
     *  设置从此流中将来读取数据值所需的字节顺序。
     * 例如,如果读取为4字节整数,则字节序列"0x01 0x02 0x03 0x04"在使用网络字节顺序时将具有值"0x01020304",而在反向字节顺序下具有值"0x04030201"。
     * 
     *  <p>枚举类<code> java.nio.ByteOrder </code>用于指定字节顺序。
     *  <code> ByteOrder.BIG_ENDIAN </code>的值指定所谓的大端或网络字节顺序,其中高位字节优先。
     * 摩托罗拉和Sparc处理器以这种格式存储数据,而Intel处理器以反向<code> ByteOrder.LITTLE_ENDIAN </code>顺序存储数据。
     * 
     *  <p>字节顺序对从<code> readBits </code>方法(或由<code> ImageOutputStream.writeBits </code>写入的值)返回的结果没有影响。
     * 
     * 
     * @param byteOrder one of <code>ByteOrder.BIG_ENDIAN</code> or
     * <code>java.nio.ByteOrder.LITTLE_ENDIAN</code>, indicating whether
     * network byte order or its reverse will be used for future
     * reads.
     *
     * @see java.nio.ByteOrder
     * @see #getByteOrder
     * @see #readBits(int)
     */
    void setByteOrder(ByteOrder byteOrder);

    /**
     * Returns the byte order with which data values will be read from
     * this stream as an instance of the
     * <code>java.nio.ByteOrder</code> enumeration.
     *
     * <p>
     *  返回从此流中读取数据值的字节顺序,作为<code> java.nio.ByteOrder </code>枚举的实例。
     * 
     * 
     * @return one of <code>ByteOrder.BIG_ENDIAN</code> or
     * <code>ByteOrder.LITTLE_ENDIAN</code>, indicating which byte
     * order is being used.
     *
     * @see java.nio.ByteOrder
     * @see #setByteOrder
     */
    ByteOrder getByteOrder();

    /**
     * Reads a single byte from the stream and returns it as an
     * integer between 0 and 255.  If the end of the stream is
     * reached, -1 is returned.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 从流中读取单个字节,并将其作为0到255之间的整数返回。如果到达流的结尾,则返回-1。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a byte value from the stream, as an int, or -1 to
     * indicate EOF.
     *
     * @exception IOException if an I/O error occurs.
     */
    int read() throws IOException;

    /**
     * Reads up to <code>b.length</code> bytes from the stream, and
     * stores them into <code>b</code> starting at index 0.  The
     * number of bytes read is returned.  If no bytes can be read
     * because the end of the stream has been reached, -1 is returned.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取<code> b.length </code>字节,并将它们存储在从索引0开始的<code> b </code>中。返回读取的字节数。如果没有字节可以读取,因为已经到达流的结尾,返回-1。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param b an array of bytes to be written to.
     *
     * @return the number of bytes actually read, or <code>-1</code>
     * to indicate EOF.
     *
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     *
     * @exception IOException if an I/O error occurs.
     */
    int read(byte[] b) throws IOException;

    /**
     * Reads up to <code>len</code> bytes from the stream, and stores
     * them into <code>b</code> starting at index <code>off</code>.
     * The number of bytes read is returned.  If no bytes can be read
     * because the end of the stream has been reached, <code>-1</code>
     * is returned.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取<code> len </code>个字节,并将它们从索引<code> off </code>开始存储到<code> b </code>中。返回读取的字节数。
     * 如果没有字节可以读取,因为到达流的结尾,返回<code> -1 </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param b an array of bytes to be written to.
     * @param off the starting position within <code>b</code> to write to.
     * @param len the maximum number of <code>byte</code>s to read.
     *
     * @return the number of bytes actually read, or <code>-1</code>
     * to indicate EOF.
     *
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>b.length</code>.
     * @exception IOException if an I/O error occurs.
     */
    int read(byte[] b, int off, int len) throws IOException;

    /**
     * Reads up to <code>len</code> bytes from the stream, and
     * modifies the supplied <code>IIOByteBuffer</code> to indicate
     * the byte array, offset, and length where the data may be found.
     * The caller should not attempt to modify the data found in the
     * <code>IIOByteBuffer</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取<code> len </code>字节,并修改提供的<code> IIOByteBuffer </code>以指示可以找到数据的字节数组,偏移和长度。
     * 调用者不应该尝试修改在<code> IIOByteBuffer </code>中找到的数据。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param buf an IIOByteBuffer object to be modified.
     * @param len the maximum number of <code>byte</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>len</code> is
     * negative.
     * @exception NullPointerException if <code>buf</code> is
     * <code>null</code>.
     *
     * @exception IOException if an I/O error occurs.
     */
    void readBytes(IIOByteBuffer buf, int len) throws IOException;

    /**
     * Reads a byte from the stream and returns a <code>boolean</code>
     * value of <code>true</code> if it is nonzero, <code>false</code>
     * if it is zero.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取一个字节,如果为非零,则返回<code> boolean </code>值<code> true </code>;如果值为零,则返回<code> false </code>
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a boolean value from the stream.
     *
     * @exception java.io.EOFException if the end of the stream is reached.
     * @exception IOException if an I/O error occurs.
     */
    boolean readBoolean() throws IOException;

    /**
     * Reads a byte from the stream and returns it as a
     * <code>byte</code> value.  Byte values between <code>0x00</code>
     * and <code>0x7f</code> represent integer values between
     * <code>0</code> and <code>127</code>.  Values between
     * <code>0x80</code> and <code>0xff</code> represent negative
     * values from <code>-128</code> to <code>/1</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 从流中读取一个字节,并将其作为<code> byte </code>值返回。
     *  <code> 0x00 </code>和<code> 0x7f </code>之间的字节值表示<code> 0 </code>和<code> 127 </code>之间的整数值。
     *  <code> 0x80 </code>和<code> 0xff </code>之间的值表示从<code> -128 </code>到<code> / 1 </code>的负值。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a signed byte value from the stream.
     *
     * @exception java.io.EOFException if the end of the stream is reached.
     * @exception IOException if an I/O error occurs.
     */
    byte readByte() throws IOException;

    /**
     * Reads a byte from the stream, and (conceptually) converts it to
     * an int, masks it with <code>0xff</code> in order to strip off
     * any sign-extension bits, and returns it as a <code>byte</code>
     * value.
     *
     * <p> Thus, byte values between <code>0x00</code> and
     * <code>0x7f</code> are simply returned as integer values between
     * <code>0</code> and <code>127</code>.  Values between
     * <code>0x80</code> and <code>0xff</code>, which normally
     * represent negative <code>byte</code>values, will be mapped into
     * positive integers between <code>128</code> and
     * <code>255</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取一个字节,并且(在概念上)将其转换为int,用<code> 0xff </code>屏蔽它以便去掉任何符号扩展位,并将其作为<code>代码>值。
     * 
     *  <p>因此,<code> 0x00 </code>和<code> 0x7f </code>之间的字节值只是作为<code> 0 </code>和<code> 127 </code>之间的整数值返回。
     * 通常代表负<code>字节</code>值的<code> 0x80 </code>和<code> 0xff </code>之间的值将被映射为<code> 128 </code> <code> 255 </code>
     * 。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return an unsigned byte value from the stream.
     *
     * @exception java.io.EOFException if the end of the stream is reached.
     * @exception IOException if an I/O error occurs.
     */
    int readUnsignedByte() throws IOException;

    /**
     * Reads two bytes from the stream, and (conceptually)
     * concatenates them according to the current byte order, and
     * returns the result as a <code>short</code> value.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取两个字节,并且(在概念上)根据当前字节顺序连接它们,并将结果作为<code> short </code>值返回。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a signed short value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    short readShort() throws IOException;

    /**
     * Reads two bytes from the stream, and (conceptually)
     * concatenates them according to the current byte order, converts
     * the resulting value to an <code>int</code>, masks it with
     * <code>0xffff</code> in order to strip off any sign-extension
     * buts, and returns the result as an unsigned <code>int</code>
     * value.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 从流中读取两个字节,并且(在概念上)根据当前字节顺序将它们连接,将结果值转换为<code> int </code>,用<code> 0xffff </code>关闭任何符号扩展buts,并返回结果作为u
     * nsigned <code> int </code>值。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return an unsigned short value from the stream, as an int.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    int readUnsignedShort() throws IOException;

    /**
     * Equivalent to <code>readUnsignedShort</code>, except that the
     * result is returned using the <code>char</code> datatype.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  等效于<code> readUnsignedShort </code>,除了使用<code> char </code>数据类型返回结果。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return an unsigned char value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #readUnsignedShort
     */
    char readChar() throws IOException;

    /**
     * Reads 4 bytes from the stream, and (conceptually) concatenates
     * them according to the current byte order and returns the result
     * as an <code>int</code>.
     *
     * <p> The bit offset within the stream is ignored and treated as
     * though it were zero.
     *
     * <p>
     *  从流中读取4个字节,并且(在概念上)根据当前字节顺序连接它们,并以<code> int </code>返回结果。
     * 
     *  <p>流中的位偏移将被忽略,并被视为零。
     * 
     * 
     * @return a signed int value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    int readInt() throws IOException;

    /**
     * Reads 4 bytes from the stream, and (conceptually) concatenates
     * them according to the current byte order, converts the result
     * to a long, masks it with <code>0xffffffffL</code> in order to
     * strip off any sign-extension bits, and returns the result as an
     * unsigned <code>long</code> value.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取4个字节,并且(在概念上)根据当前字节顺序将它们连接起来,将结果转换为long,用<code> 0xffffffffL </code>对其进行掩码,以去除任何符号扩展位,将结果作为unsig
     * ned <code> long </code>值返回。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return an unsigned int value from the stream, as a long.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    long readUnsignedInt() throws IOException;

    /**
     * Reads 8 bytes from the stream, and (conceptually) concatenates
     * them according to the current byte order and returns the result
     * as a <code>long</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取8个字节,并且(在概念上)根据当前字节顺序连接它们,并将结果作为<code> long </code>返回。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a signed long value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    long readLong() throws IOException;

    /**
     * Reads 4 bytes from the stream, and (conceptually) concatenates
     * them according to the current byte order and returns the result
     * as a <code>float</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 从流中读取4个字节,并且(在概念上)根据当前字节顺序连接它们,并将结果作为<code> float </code>返回。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a float value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    float readFloat() throws IOException;

    /**
     * Reads 8 bytes from the stream, and (conceptually) concatenates
     * them according to the current byte order and returns the result
     * as a <code>double</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取8个字节,并且(在概念上)根据当前字节顺序连接它们,并将结果作为<code> double </code>返回。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a double value from the stream.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getByteOrder
     */
    double readDouble() throws IOException;

    /**
     * Reads the next line of text from the input stream.  It reads
     * successive bytes, converting each byte separately into a
     * character, until it encounters a line terminator or end of
     * file; the characters read are then returned as a
     * <code>String</code>. Note that because this method processes
     * bytes, it does not support input of the full Unicode character
     * set.
     *
     * <p> If end of file is encountered before even one byte can be
     * read, then <code>null</code> is returned. Otherwise, each byte
     * that is read is converted to type <code>char</code> by
     * zero-extension. If the character <code>'\n'</code> is
     * encountered, it is discarded and reading ceases. If the
     * character <code>'\r'</code> is encountered, it is discarded
     * and, if the following byte converts &#32;to the character
     * <code>'\n'</code>, then that is discarded also; reading then
     * ceases. If end of file is encountered before either of the
     * characters <code>'\n'</code> and <code>'\r'</code> is
     * encountered, reading ceases. Once reading has ceased, a
     * <code>String</code> is returned that contains all the
     * characters read and not discarded, taken in order.  Note that
     * every character in this string will have a value less than
     * <code>&#92;u0100</code>, that is, <code>(char)256</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从输入流读取下一行文本。它读取连续字节,将每个字节分别转换为字符,直到遇到行终止符或文件结尾;读取的字符将作为<code> String </code>返回。
     * 请注意,因为此方法处理字节,它不支持输入完整的Unicode字符集。
     * 
     * <p>如果在读取一个字节之前遇到文件结尾,则返回<code> null </code>。否则,每个读取的字节通过零扩展转换为<code> char </code>类型。
     * 如果遇到字符<code>'\ n'</code>,它将被丢弃并且读取停止。
     * 如果遇到字符<code>'\ r'</code>,它将被丢弃,如果后面的字节转换为字符<code>'\ n'</code>阅读然后停止。
     * 如果在遇到<code>'\ n'</code>和<code>'\ r'</code>字符之前遇到文件结尾,则读取停止。
     * 一旦读取停止,将返回一个<code> String </code>,其中包含已读取和未废弃的所有字符。
     * 请注意,此字符串中的每个字符都将具有小于<code> \ u0100 </code>的值,即<code>(char)256 </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return a String containing a line of text from the stream.
     *
     * @exception IOException if an I/O error occurs.
     */
    String readLine() throws IOException;

    /**
     * Reads in a string that has been encoded using a
     * <a href="../../../java/io/DataInput.html#modified-utf-8">modified
     * UTF-8</a>
     * format.  The general contract of <code>readUTF</code> is that
     * it reads a representation of a Unicode character string encoded
     * in modified UTF-8 format; this string of characters is
     * then returned as a <code>String</code>.
     *
     * <p> First, two bytes are read and used to construct an unsigned
     * 16-bit integer in the manner of the
     * <code>readUnsignedShort</code> method, using network byte order
     * (regardless of the current byte order setting). This integer
     * value is called the <i>UTF length</i> and specifies the number
     * of additional bytes to be read. These bytes are then converted
     * to characters by considering them in groups. The length of each
     * group is computed from the value of the first byte of the
     * group. The byte following a group, if any, is the first byte of
     * the next group.
     *
     * <p> If the first byte of a group matches the bit pattern
     * <code>0xxxxxxx</code> (where <code>x</code> means "may be
     * <code>0</code> or <code>1</code>"), then the group consists of
     * just that byte. The byte is zero-extended to form a character.
     *
     * <p> If the first byte of a group matches the bit pattern
     * <code>110xxxxx</code>, then the group consists of that byte
     * <code>a</code> and a second byte <code>b</code>. If there is no
     * byte <code>b</code> (because byte <code>a</code> was the last
     * of the bytes to be read), or if byte <code>b</code> does not
     * match the bit pattern <code>10xxxxxx</code>, then a
     * <code>UTFDataFormatException</code> is thrown. Otherwise, the
     * group is converted to the character:
     *
     * <p> <pre><code>
     * (char)(((a&amp; 0x1F) &lt;&lt; 6) | (b &amp; 0x3F))
     * </code></pre>
     *
     * If the first byte of a group matches the bit pattern
     * <code>1110xxxx</code>, then the group consists of that byte
     * <code>a</code> and two more bytes <code>b</code> and
     * <code>c</code>.  If there is no byte <code>c</code> (because
     * byte <code>a</code> was one of the last two of the bytes to be
     * read), or either byte <code>b</code> or byte <code>c</code>
     * does not match the bit pattern <code>10xxxxxx</code>, then a
     * <code>UTFDataFormatException</code> is thrown. Otherwise, the
     * group is converted to the character:
     *
     * <p> <pre><code>
     * (char)(((a &amp; 0x0F) &lt;&lt; 12) | ((b &amp; 0x3F) &lt;&lt; 6) | (c &amp; 0x3F))
     * </code></pre>
     *
     * If the first byte of a group matches the pattern
     * <code>1111xxxx</code> or the pattern <code>10xxxxxx</code>,
     * then a <code>UTFDataFormatException</code> is thrown.
     *
     * <p> If end of file is encountered at any time during this
     * entire process, then an <code>java.io.EOFException</code> is thrown.
     *
     * <p> After every group has been converted to a character by this
     * process, the characters are gathered, in the same order in
     * which their corresponding groups were read from the input
     * stream, to form a <code>String</code>, which is returned.
     *
     * <p> The current byte order setting is ignored.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p><strong>Note:</strong> This method should not be used in
     * the  implementation of image formats that use standard UTF-8,
     * because  the modified UTF-8 used here is incompatible with
     * standard UTF-8.
     *
     * <p>
     *  在使用<a href="../../../java/io/DataInput.html#modified-utf-8">修改的UTF-8 </a>格式编码的字符串中读取。
     *  <code> readUTF </code>的一般约定是它读取以修改的UTF-8格式编码的Unicode字符串的表示;这个字符串然后作为<code> String </code>返回。
     * 
     * <p>首先,使用网络字节顺序(不考虑当前字节顺序设置),以<code> readUnsignedShort </code>方法的方式读取和使用两个字节构造无符号16位整数。
     * 此整数值称为<i> UTF长度</i>,并指定要读取的附加字节数。然后,通过以组的形式考虑这些字节,将这些字节转换为字符。每个组的长度从组的第一个字节的值计算。
     * 组后的字节(如果有的话)是下一组的第一个字节。
     * 
     *  <p>如果组的第一个字节与<code> 0xxxxxxx </code>(其中<code> x </code>表示"可以是<code> 0 </code>或<code> 1 < / code>"),那
     * 么该组只包含该字节。
     * 该字节是零扩展形成一个字符。
     * 
     *  <p>如果组的第一个字节与<code> 110xxxxx </code>的位模式匹配,则该组由该字节<code> a </code>和第二个字节<code> b </code> 。
     * 如果没有字节<code> b </code>(因为字节<code> a </code>是要读取的最后一个字节),或者如果byte <code> b </code>位模式<code> 10xxxxxx </code>
     * ,那么将抛出一个<code> UTFDataFormatException </code>。
     *  <p>如果组的第一个字节与<code> 110xxxxx </code>的位模式匹配,则该组由该字节<code> a </code>和第二个字节<code> b </code> 。
     * 否则,组将转换为字符：。
     * 
     *  <p> <pre> <code>(char)(((a&amp; 0x1F)&lt;&lt; 6)|(b&amp; 0x3F))</code> </
     * 
     * 如果组的第一个字节与<code> 1110xxxx </code>的位模式匹配,则该组由该字节<code> a </code>和两个字节<code> b </code> > c </code>。
     * 如果没有字节<code> c </code>(因为字节<code> a </code>是要读取的最后两个字节之一),或者字节<code> b </code>字节<code> c </code>与位模式<code>
     *  10xxxxxx </code>不匹配,则会抛出一个<code> UTFDataFormatException </code>。
     * 如果组的第一个字节与<code> 1110xxxx </code>的位模式匹配,则该组由该字节<code> a </code>和两个字节<code> b </code> > c </code>。
     * 否则,组将转换为字符：。
     * 
     *  <p> <pre> <code>(char)(((a&amp; 0x0F)<&lt; 12)|((b&amp; 0x3F)<6)|(c&amp; 0x3F)代码> </pre>
     * 
     *  如果组的第一个字节与模式<code> 1111xxxx </code>或模式<code> 10xxxxxx </code>匹配,则会抛出<code> UTFDataFormatException </code>
     * 。
     * 
     *  <p>如果在整个过程中的任何时候遇到文件结束,则会抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在每个组通过此过程转换为字符之后,以与从输入流读取其相应组的顺序相同的顺序收集字符,以形成<code> String </code>返回。
     * 
     *  <p>当前字节顺序设置被忽略。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     *  <p> <strong>请注意</strong>：此方法不应用于实施使用标准UTF-8的图像格式,因为此处使用的修改的UTF-8与标准UTF-8不兼容。
     * 
     * 
     * @return a String read from the stream.
     *
     * @exception  java.io.EOFException  if this stream reaches the end
     * before reading all the bytes.
     * @exception  java.io.UTFDataFormatException if the bytes do not represent
     * a valid modified UTF-8 encoding of a string.
     * @exception IOException if an I/O error occurs.
     */
    String readUTF() throws IOException;

    /**
     * Reads <code>len</code> bytes from the stream, and stores them
     * into <code>b</code> starting at index <code>off</code>.
     * If the end of the stream is reached, an <code>java.io.EOFException</code>
     * will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 从流中读取<code> len </code>字节,并将它们存储在<code> b </code>中,从索引<code> off </code>开始。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param b an array of bytes to be written to.
     * @param off the starting position within <code>b</code> to write to.
     * @param len the maximum number of <code>byte</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>b.length</code>.
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(byte[] b, int off, int len) throws IOException;

    /**
     * Reads <code>b.length</code> bytes from the stream, and stores them
     * into <code>b</code> starting at index <code>0</code>.
     * If the end of the stream is reached, an <code>java.io.EOFException</code>
     * will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取<code> b.length </code>字节,并将它们从索引<code> 0 </code>开始存储到<code> b </code>中。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param b an array of <code>byte</code>s.
     *
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(byte[] b) throws IOException;

    /**
     * Reads <code>len</code> shorts (signed 16-bit integers) from the
     * stream according to the current byte order, and
     * stores them into <code>s</code> starting at index
     * <code>off</code>.  If the end of the stream is reached, an
     * <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  根据当前字节顺序从流中读取<code> len </code> shorts(带符号的16位整数),并从索引<code> off </code>开始将它们存储到<code> s </code> 。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param s an array of shorts to be written to.
     * @param off the starting position within <code>s</code> to write to.
     * @param len the maximum number of <code>short</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>s.length</code>.
     * @exception NullPointerException if <code>s</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(short[] s, int off, int len) throws IOException;

    /**
     * Reads <code>len</code> chars (unsigned 16-bit integers) from the
     * stream according to the current byte order, and
     * stores them into <code>c</code> starting at index
     * <code>off</code>.  If the end of the stream is reached, an
     * <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  根据当前字节顺序从流中读取<code> len </code> chars(无符号的16位整数),并将它们存储到<code> c </code> 。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param c an array of chars to be written to.
     * @param off the starting position within <code>c</code> to write to.
     * @param len the maximum number of <code>char</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>c.length</code>.
     * @exception NullPointerException if <code>c</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(char[] c, int off, int len) throws IOException;

    /**
     * Reads <code>len</code> ints (signed 32-bit integers) from the
     * stream according to the current byte order, and
     * stores them into <code>i</code> starting at index
     * <code>off</code>.  If the end of the stream is reached, an
     * <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     * 根据当前字节顺序从流中读取<code> len </code> ints(带符号的32位整数),并将它们存储到<code> i </code> 。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param i an array of ints to be written to.
     * @param off the starting position within <code>i</code> to write to.
     * @param len the maximum number of <code>int</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>i.length</code>.
     * @exception NullPointerException if <code>i</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(int[] i, int off, int len) throws IOException;

    /**
     * Reads <code>len</code> longs (signed 64-bit integers) from the
     * stream according to the current byte order, and
     * stores them into <code>l</code> starting at index
     * <code>off</code>.  If the end of the stream is reached, an
     * <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  根据当前字节顺序从流中读取<code> len </code> longs(有符号64位整数),并将它们存储在<code> l </code> 。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param l an array of longs to be written to.
     * @param off the starting position within <code>l</code> to write to.
     * @param len the maximum number of <code>long</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>l.length</code>.
     * @exception NullPointerException if <code>l</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(long[] l, int off, int len) throws IOException;

    /**
     * Reads <code>len</code> floats (32-bit IEEE single-precision
     * floats) from the stream according to the current byte order,
     * and stores them into <code>f</code> starting at
     * index <code>off</code>.  If the end of the stream is reached,
     * an <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  根据当前字节顺序从流中读取<code> len </code>浮点(32位IEEE单精度浮点数),并将它们存储在<code> f </code> / code>。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param f an array of floats to be written to.
     * @param off the starting position within <code>f</code> to write to.
     * @param len the maximum number of <code>float</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>f.length</code>.
     * @exception NullPointerException if <code>f</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(float[] f, int off, int len) throws IOException;

    /**
     * Reads <code>len</code> doubles (64-bit IEEE double-precision
     * floats) from the stream according to the current byte order,
     * and stores them into <code>d</code> starting at
     * index <code>off</code>.  If the end of the stream is reached,
     * an <code>java.io.EOFException</code> will be thrown.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  根据当前字节顺序从流中读取<code> len </code>双精度浮点数(64位IEEE双精度浮点数),并将其存储在<code> d </code> / code>。
     * 如果到达流的结尾,将抛出<code> java.io.EOFException </code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @param d an array of doubles to be written to.
     * @param off the starting position within <code>d</code> to write to.
     * @param len the maximum number of <code>double</code>s to read.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>d.length</code>.
     * @exception NullPointerException if <code>d</code> is
     * <code>null</code>.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bytes.
     * @exception IOException if an I/O error occurs.
     */
    void readFully(double[] d, int off, int len) throws IOException;

    /**
     * Returns the current byte position of the stream.  The next read
     * will take place starting at this offset.
     *
     * <p>
     * 返回流的当前字节位置。下一次读取将从该偏移开始。
     * 
     * 
     * @return a long containing the position of the stream.
     *
     * @exception IOException if an I/O error occurs.
     */
    long getStreamPosition() throws IOException;

    /**
     * Returns the current bit offset, as an integer between 0 and 7,
     * inclusive.  The bit offset is updated implicitly by calls to
     * the <code>readBits</code> method.  A value of 0 indicates the
     * most-significant bit, and a value of 7 indicates the least
     * significant bit, of the byte being read.
     *
     * <p> The bit offset is set to 0 when a stream is first
     * opened, and is reset to 0 by calls to <code>seek</code>,
     * <code>skipBytes</code>, or any <code>read</code> or
     * <code>readFully</code> method.
     *
     * <p>
     *  以0和7之间的整数(包括0和7)返回当前位偏移量。位偏移通过调用<code> readBits </code>方法隐式更新。值0表示正在读取的字节的最高有效位,值7表示最低有效位。
     * 
     *  <p>当流首次打开时,位偏移设置为0,并通过调用<code> seek </code>,<code> skipBytes </code>或任何<code> / code>或<code> readFul
     * ly </code>方法。
     * 
     * 
     * @return an <code>int</code> containing the bit offset between
     * 0 and 7, inclusive.
     *
     * @exception IOException if an I/O error occurs.
     *
     * @see #setBitOffset
     */
    int getBitOffset() throws IOException;

    /**
     * Sets the bit offset to an integer between 0 and 7, inclusive.
     * The byte offset within the stream, as returned by
     * <code>getStreamPosition</code>, is left unchanged.
     * A value of 0 indicates the
     * most-significant bit, and a value of 7 indicates the least
     * significant bit, of the byte being read.
     *
     * <p>
     *  将位偏移设置为0到7之间的整数,包括0和7。由<code> getStreamPosition </code>返回的流内的字节偏移保持不变。值0表示正在读取的字节的最高有效位,值7表示最低有效位。
     * 
     * 
     * @param bitOffset the desired offset, as an <code>int</code>
     * between 0 and 7, inclusive.
     *
     * @exception IllegalArgumentException if <code>bitOffset</code>
     * is not between 0 and 7, inclusive.
     * @exception IOException if an I/O error occurs.
     *
     * @see #getBitOffset
     */
    void setBitOffset(int bitOffset) throws IOException;

    /**
     * Reads a single bit from the stream and returns it as an
     * <code>int</code> with the value <code>0</code> or
     * <code>1</code>.  The bit offset is advanced by one and reduced
     * modulo 8.
     *
     * <p>
     *  从流中读取单个位,并将其作为<code> 0 </code>或<code> 1 </code>的值返回为<code> int </code>。比特偏移被提前一并减模8。
     * 
     * 
     * @return an <code>int</code> containing the value <code>0</code>
     * or <code>1</code>.
     *
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bits.
     * @exception IOException if an I/O error occurs.
     */
    int readBit() throws IOException;

    /**
     * Reads a bitstring from the stream and returns it as a
     * <code>long</code>, with the first bit read becoming the most
     * significant bit of the output.  The read starts within the byte
     * indicated by <code>getStreamPosition</code>, at the bit given
     * by <code>getBitOffset</code>.  The bit offset is advanced by
     * <code>numBits</code> and reduced modulo 8.
     *
     * <p> The byte order of the stream has no effect on this
     * method.  The return value of this method is constructed as
     * though the bits were read one at a time, and shifted into
     * the right side of the return value, as shown by the following
     * pseudo-code:
     *
     * <pre>{@code
     * long accum = 0L;
     * for (int i = 0; i < numBits; i++) {
     *   accum <<= 1; // Shift left one bit to make room
     *   accum |= readBit();
     * }
     * }</pre>
     *
     * Note that the result of <code>readBits(32)</code> may thus not
     * be equal to that of <code>readInt()</code> if a reverse network
     * byte order is being used (i.e., <code>getByteOrder() ==
     * false</code>).
     *
     * <p> If the end of the stream is encountered before all the bits
     * have been read, an <code>java.io.EOFException</code> is thrown.
     *
     * <p>
     *  从流中读取一个位串,并将其作为<code> long </code>返回,第一位读取将成为输出的最高有效位。
     * 读取在<code> getStreamPosition </code>指示的字节内开始,由<code> getBitOffset </code>给出的位开始。
     * 位偏移提前<code> numBits </code>并减少模8。
     * 
     * <p>流的字节顺序对此方法没有影响。该方法的返回值被构造为好像一次读取一个位,并且移位到返回值的右侧,如下面的伪代码所示：
     * 
     *  <pre> {@ code long accum = 0L; for(int i = 0; i <numBits; i ++){accum << = 1; // Shift left one bit to make room accum | = readBit(); }} </pre>
     * 。
     * 
     *  注意,如果正在使用反向网络字节顺序,则<code> readBits(32)</code>的结果可能不等于<code> readInt()</code>的结果(即<code> getByteOrder
     *  ()== false </code>)。
     * 
     *  <p>如果在读取所有位之前遇到流的末尾,则会抛出<code> java.io.EOFException </code>。
     * 
     * 
     * @param numBits the number of bits to read, as an <code>int</code>
     * between 0 and 64, inclusive.
     * @return the bitstring, as a <code>long</code> with the last bit
     * read stored in the least significant bit.
     *
     * @exception IllegalArgumentException if <code>numBits</code>
     * is not between 0 and 64, inclusive.
     * @exception java.io.EOFException if the stream reaches the end before
     * reading all the bits.
     * @exception IOException if an I/O error occurs.
     */
    long readBits(int numBits) throws IOException;

    /**
     * Returns the total length of the stream, if known.  Otherwise,
     * <code>-1</code> is returned.
     *
     * <p>
     *  返回流的总长度(如果已知)。否则,返回<code> -1 </code>。
     * 
     * 
     * @return a <code>long</code> containing the length of the
     * stream, if known, or else <code>-1</code>.
     *
     * @exception IOException if an I/O error occurs.
     */
    long length() throws IOException;

    /**
     * Moves the stream position forward by a given number of bytes.  It
     * is possible that this method will only be able to skip forward
     * by a smaller number of bytes than requested, for example if the
     * end of the stream is reached.  In all cases, the actual number
     * of bytes skipped is returned.  The bit offset is set to zero
     * prior to advancing the position.
     *
     * <p>
     *  将流位置向前移动给定数量的字节。可能的是,该方法仅能够比所请求的更少数量的字节向前跳过,例如如果到达流的结尾。在所有情况下,都会返回实际跳过的字节数。位置偏移在提前位置之前设置为零。
     * 
     * 
     * @param n an <code>int</code> containing the number of bytes to
     * be skipped.
     *
     * @return an <code>int</code> representing the number of bytes skipped.
     *
     * @exception IOException if an I/O error occurs.
     */
    int skipBytes(int n) throws IOException;

    /**
     * Moves the stream position forward by a given number of bytes.
     * This method is identical to <code>skipBytes(int)</code> except
     * that it allows for a larger skip distance.
     *
     * <p>
     *  将流位置向前移动给定数量的字节。该方法与<code> skipBytes(int)</code>完全相同,只是它允许更大的跳过距离。
     * 
     * 
     * @param n a <code>long</code> containing the number of bytes to
     * be skipped.
     *
     * @return a <code>long</code> representing the number of bytes
     * skipped.
     *
     * @exception IOException if an I/O error occurs.
     */
    long skipBytes(long n) throws IOException;

    /**
     * Sets the current stream position to the desired location.  The
     * next read will occur at this location.  The bit offset is set
     * to 0.
     *
     * <p> An <code>IndexOutOfBoundsException</code> will be thrown if
     * <code>pos</code> is smaller than the flushed position (as
     * returned by <code>getflushedPosition</code>).
     *
     * <p> It is legal to seek past the end of the file; an
     * <code>java.io.EOFException</code> will be thrown only if a read is
     * performed.
     *
     * <p>
     *  将当前流位置设置为所需位置。下一次读取将发生在此位置。位偏移设置为0。
     * 
     * <p>如果<code> pos </code>小于刷新位置(由<code> getflushedPosition </code>返回),则会抛出<code> IndexOutOfBoundsExcept
     * ion </code>。
     * 
     *  <p>寻求超过文件的结尾是合法的;只有在执行读取时才会抛出<code> java.io.EOFException </code>。
     * 
     * 
     * @param pos a <code>long</code> containing the desired file
     * pointer position.
     *
     * @exception IndexOutOfBoundsException if <code>pos</code> is smaller
     * than the flushed position.
     * @exception IOException if any other I/O error occurs.
     */
    void seek(long pos) throws IOException;

    /**
     * Marks a position in the stream to be returned to by a
     * subsequent call to <code>reset</code>.  Unlike a standard
     * <code>InputStream</code>, all <code>ImageInputStream</code>s
     * support marking.  Additionally, calls to <code>mark</code> and
     * <code>reset</code> may be nested arbitrarily.
     *
     * <p> Unlike the <code>mark</code> methods declared by the
     * <code>Reader</code> and <code>InputStream</code> interfaces, no
     * <code>readLimit</code> parameter is used.  An arbitrary amount
     * of data may be read following the call to <code>mark</code>.
     *
     * <p> The bit position used by the <code>readBits</code> method
     * is saved and restored by each pair of calls to
     * <code>mark</code> and <code>reset</code>.
     *
     * <p> Note that it is valid for an <code>ImageReader</code> to call
     * <code>flushBefore</code> as part of a read operation.
     * Therefore, if an application calls <code>mark</code> prior to
     * passing that stream to an <code>ImageReader</code>, the application
     * should not assume that the marked position will remain valid after
     * the read operation has completed.
     * <p>
     *  在随后调用<code> reset </code>时返回的流中标记一个位置。
     * 与标准<code> InputStream </code>不同,所有<code> ImageInputStream </code>都支持标记。
     * 此外,对<code>标记</code>和<code>重置</code>的调用可以任意嵌套。
     * 
     *  <p>与<code> Reader </code>和<code> InputStream </code>接口声明的<code>标记</code>方法不同,不使用<code> readLimit </code>
     * 参数。
     * 可以在调用<code> mark </code>之后读取任意数量的数据。
     * 
     *  <p> <code> readBits </code>方法使用的位位置由每次调用<code> mark </code>和<code> reset </code>保存和恢复。
     * 
     *  <p>请注意,作为读取操作的一部分,<code> ImageReader </code>调用<code> flushBefore </code>是有效的。
     * 因此,如果应用程序在将该流传递到<code> ImageReader </code>之前调用<code> mark </code>,则应用程序不应假定在读取操作完成后标记位置将保持有效。
     * 
     */
    void mark();

    /**
     * Returns the stream pointer to its previous position, including
     * the bit offset, at the time of the most recent unmatched call
     * to <code>mark</code>.
     *
     * <p> Calls to <code>reset</code> without a corresponding call
     * to <code>mark</code> have no effect.
     *
     * <p> An <code>IOException</code> will be thrown if the previous
     * marked position lies in the discarded portion of the stream.
     *
     * <p>
     *  在最近对<code>标记</code>的不匹配调用时,将流指针返回到其上一个位置,包括位偏移量。
     * 
     * <p>调用<code>重置</code>没有对<code>标记</code>的相应调用不起作用。
     * 
     *  <p>如果先前标记的位置在流的丢弃部分中,则将抛出<code> IOException </code>。
     * 
     * 
     * @exception IOException if an I/O error occurs.
     */
    void reset() throws IOException;

    /**
     * Discards the initial portion of the stream prior to the
     * indicated position.  Attempting to seek to an offset within the
     * flushed portion of the stream will result in an
     * <code>IndexOutOfBoundsException</code>.
     *
     * <p> Calling <code>flushBefore</code> may allow classes
     * implementing this interface to free up resources such as memory
     * or disk space that are being used to store data from the
     * stream.
     *
     * <p>
     *  在指示位置之前丢弃流的初始部分。尝试在流的刷新部分内寻找偏移将导致<code> IndexOutOfBoundsException </code>。
     * 
     *  调用<code> flushBefore </code>可以允许实现此接口的类释放用于存储来自流的数据的资源,例如内存或磁盘空间。
     * 
     * 
     * @param pos a <code>long</code> containing the length of the
     * stream prefix that may be flushed.
     *
     * @exception IndexOutOfBoundsException if <code>pos</code> lies
     * in the flushed portion of the stream or past the current stream
     * position.
     * @exception IOException if an I/O error occurs.
     */
    void flushBefore(long pos) throws IOException;

    /**
     * Discards the initial position of the stream prior to the current
     * stream position.  Equivalent to
     * <code>flushBefore(getStreamPosition())</code>.
     *
     * <p>
     *  丢弃当前流位置之前的流的初始位置。等同于<code> flushBefore(getStreamPosition())</code>。
     * 
     * 
     * @exception IOException if an I/O error occurs.
     */
    void flush() throws IOException;

    /**
     * Returns the earliest position in the stream to which seeking
     * may be performed.  The returned value will be the maximum of
     * all values passed into previous calls to
     * <code>flushBefore</code>.
     *
     * <p>
     *  返回可执行搜索的流中的最早位置。返回的值将是传递到以前调用<code> flushBefore </code>的所有值的最大值。
     * 
     * 
     * @return the earliest legal position for seeking, as a
     * <code>long</code>.
     */
    long getFlushedPosition();

    /**
     * Returns <code>true</code> if this <code>ImageInputStream</code>
     * caches data itself in order to allow seeking backwards.
     * Applications may consult this in order to decide how frequently,
     * or whether, to flush in order to conserve cache resources.
     *
     * <p>
     *  如果此<code> ImageInputStream </code>缓存数据本身以允许向后搜索,则返回<code> true </code>。
     * 应用程序可以咨询这一点,以便决定多长时间或者是否刷新以节省缓存资源。
     * 
     * 
     * @return <code>true</code> if this <code>ImageInputStream</code>
     * caches data.
     *
     * @see #isCachedMemory
     * @see #isCachedFile
     */
    boolean isCached();

    /**
     * Returns <code>true</code> if this <code>ImageInputStream</code>
     * caches data itself in order to allow seeking backwards, and
     * the cache is kept in main memory.  Applications may consult
     * this in order to decide how frequently, or whether, to flush
     * in order to conserve cache resources.
     *
     * <p>
     *  如果此<code> ImageInputStream </code>缓存数据本身以允许向后搜索,则返回<code> true </code>,并且缓存保存在主存储器中。
     * 应用程序可以咨询这一点,以便决定多长时间或者是否刷新以节省缓存资源。
     * 
     * 
     * @return <code>true</code> if this <code>ImageInputStream</code>
     * caches data in main memory.
     *
     * @see #isCached
     * @see #isCachedFile
     */
    boolean isCachedMemory();

    /**
     * Returns <code>true</code> if this <code>ImageInputStream</code>
     * caches data itself in order to allow seeking backwards, and
     * the cache is kept in a temporary file.  Applications may consult
     * this in order to decide how frequently, or whether, to flush
     * in order to conserve cache resources.
     *
     * <p>
     * 如果此<code> ImageInputStream </code>缓存数据本身以允许向后搜索,则返回<code> true </code>,并且缓存保存在临时文件中。
     * 应用程序可以咨询这一点,以便决定多长时间或者是否刷新以节省缓存资源。
     * 
     * 
     * @return <code>true</code> if this <code>ImageInputStream</code>
     * caches data in a temporary file.
     *
     * @see #isCached
     * @see #isCachedMemory
     */
    boolean isCachedFile();

    /**
     * Closes the stream.  Attempts to access a stream that has been
     * closed may result in <code>IOException</code>s or incorrect
     * behavior.  Calling this method may allow classes implementing
     * this interface to release resources associated with the stream
     * such as memory, disk space, or file descriptors.
     *
     * <p>
     * 
     * @exception IOException if an I/O error occurs.
     */
    void close() throws IOException;
}
