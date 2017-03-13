/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * The {@code DataInput} interface provides
 * for reading bytes from a binary stream and
 * reconstructing from them data in any of
 * the Java primitive types. There is also
 * a
 * facility for reconstructing a {@code String}
 * from data in
 * <a href="#modified-utf-8">modified UTF-8</a>
 * format.
 * <p>
 * It is generally true of all the reading
 * routines in this interface that if end of
 * file is reached before the desired number
 * of bytes has been read, an {@code EOFException}
 * (which is a kind of {@code IOException})
 * is thrown. If any byte cannot be read for
 * any reason other than end of file, an {@code IOException}
 * other than {@code EOFException} is
 * thrown. In particular, an {@code IOException}
 * may be thrown if the input stream has been
 * closed.
 *
 * <h3><a name="modified-utf-8">Modified UTF-8</a></h3>
 * <p>
 * Implementations of the DataInput and DataOutput interfaces represent
 * Unicode strings in a format that is a slight modification of UTF-8.
 * (For information regarding the standard UTF-8 format, see section
 * <i>3.9 Unicode Encoding Forms</i> of <i>The Unicode Standard, Version
 * 4.0</i>).
 * Note that in the following table, the most significant bit appears in the
 * far left-hand column.
 *
 * <blockquote>
 *   <table border="1" cellspacing="0" cellpadding="8"
 *          summary="Bit values and bytes">
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         All characters in the range {@code '\u005Cu0001'} to
 *         {@code '\u005Cu007F'} are represented by a single byte:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8" id="bit_a">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_a">Byte 1</th>
 *       <td><center>0</center>
 *       <td colspan="7"><center>bits 6-0</center>
 *     </tr>
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         The null character {@code '\u005Cu0000'} and characters
 *         in the range {@code '\u005Cu0080'} to {@code '\u005Cu07FF'} are
 *         represented by a pair of bytes:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8" id="bit_b">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_b">Byte 1</th>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="5"><center>bits 10-6</center>
 *     </tr>
 *     <tr>
 *       <th id="byte2_a">Byte 2</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 5-0</center>
 *     </tr>
 *     <tr>
 *       <th colspan="9"><span style="font-weight:normal">
 *         {@code char} values in the range {@code '\u005Cu0800'}
 *         to {@code '\u005CuFFFF'} are represented by three bytes:</span></th>
 *     </tr>
 *     <tr>
 *       <td></td>
 *       <th colspan="8"id="bit_c">Bit Values</th>
 *     </tr>
 *     <tr>
 *       <th id="byte1_c">Byte 1</th>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="4"><center>bits 15-12</center>
 *     </tr>
 *     <tr>
 *       <th id="byte2_b">Byte 2</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 11-6</center>
 *     </tr>
 *     <tr>
 *       <th id="byte3">Byte 3</th>
 *       <td><center>1</center>
 *       <td><center>0</center>
 *       <td colspan="6"><center>bits 5-0</center>
 *     </tr>
 *   </table>
 * </blockquote>
 * <p>
 * The differences between this format and the
 * standard UTF-8 format are the following:
 * <ul>
 * <li>The null byte {@code '\u005Cu0000'} is encoded in 2-byte format
 *     rather than 1-byte, so that the encoded strings never have
 *     embedded nulls.
 * <li>Only the 1-byte, 2-byte, and 3-byte formats are used.
 * <li><a href="../lang/Character.html#unicode">Supplementary characters</a>
 *     are represented in the form of surrogate pairs.
 * </ul>
 * <p>
 *  {@code DataInput}接口提供从二进制流中读取字节并从中重构任何Java原语类型的数据。
 * 还有一个工具,用于从<a href ="#modified中的数据重建{@code String} -utf-8">修改的UTF-8 </a>格式。
 * <p>
 * 在这个接口中的所有读取例程通常是正确的,如果在读取所需的字节数之前达到文件结尾,则抛出{@code EOFException}(这是一种{@code IOException})If任何字节不能被除文件结
 * 束以外的任何原因读取,则会抛出除{@code EOFException}之外的{@code IOException}特别是,如果输入流已关闭,可能会抛出{@code IOException}。
 * 
 *  <h3> <a name=\"modified-utf-8\">修改的UTF-8 </a> </h3>
 * <p>
 * DataInput和DataOutput接口的实现以一种稍微修改UTF-8的格式表示Unicode字符串(有关标准UTF-8格式的信息,请参见<i> </i> 39的Unicode编码格式</i> Un
 * icode Standard,Version 40 </i>)请注意,在下表中,最高有效位出现在最左边的列。
 * 
 * <blockquote>
 *  <table border ="1"cellspacing ="0"cellpadding ="8"
 * summary="Bit values and bytes">
 * <tr>
 *  <th colspan ="9"> <span style ="font-weight：normal">范围{@code'\\ u005Cu0001'}到{@code'\\ u005Cu007F'}中
 * 的所有字符由单个字节表示：< / span> </th>。
 * </tr>
 * <tr>
 *  <td> </td> <th colspan ="8"id ="bit_a">位值</th>
 * </tr>
 * <tr>
 *  <th id ="byte1_a">字节1 </th> <td> <center> 0 </center> <td colspan ="7"> <center>
 * </tr>
 * <tr>
 * <th colspan ="9"> <span style ="font-weight：normal">空字符{@code'\\ u005Cu0000'}和范围{@code'\\ u005Cu0080'}
 * 中的字符到{@code' u005Cu07FF'}由一对字节表示：</span> </th>。
 * </tr>
 * <tr>
 *  <td> </td> <th colspan ="8"id ="bit_b">位值</th>
 * </tr>
 * <tr>
 *  <th id ="byte1_b">字节1 </th> <td> <center> 1 </center> <td> <center> 1 </center> <td> <center> 0 </center>
 *  <td colspan ="5"> <center> bits 10-6 </center>。
 * </tr>
 * <tr>
 *  <th id ="byte2_a">字节2 </th> <td> <center> 1 </center> <td> <center> 0 </center> <td colspan ="6"> < 0 </center>
 * 。
 * </tr>
 * <tr>
 *  范围{@code'\\ u005Cu0800'}至{@code'\\ u005CuFFFF'}中的<th colspan ="9"> <span style ="font-weight：normal">
 *  {@code char}字节：</span> </th>。
 * </tr>
 * <tr>
 *  <td> </td> <th colspan ="8"id ="bit_c">位值</th>
 * </tr>
 * <tr>
 * <th id ="byte1_c">字节1 </th> <td> <center> 1 </center> <td> <center> 1 </center> <td> <center> 1 </center>
 *  <td> <center> 0 </center> <td colspan ="4"> <center>位15-12 </center>。
 * </tr>
 * <tr>
 *  <th id ="byte2_b">字节2 </th> <td> <center> 1 </center> <td> <center> 0 </center> <td colspan ="6"> <center>
 *  6 </center>。
 * </tr>
 * <tr>
 *  <th id ="byte3">字节3 </th> <td> <center> 1 </center> <td> <center> 0 </center> <td colspan ="6" 0 </center>
 * 。
 * </tr>
 * </table>
 * </blockquote>
 * <p>
 *  此格式与标准UTF-8格式之间的差异如下：
 * <ul>
 * <li>空字节{@code'\\ u005Cu0000'}以2字节格式而不是1字节编码,因此编码字符串从不具有嵌入的空值<li>只有1字节,2字节和使用3个字节的格式<li> <a href=\"/lang/Characterhtml#unicode\">
 * 补充字符</a>以代理对的形式表示。
 * </ul>
 * 
 * @author  Frank Yellin
 * @see     java.io.DataInputStream
 * @see     java.io.DataOutput
 * @since   JDK1.0
 */
public
interface DataInput {
    /**
     * Reads some bytes from an input
     * stream and stores them into the buffer
     * array {@code b}. The number of bytes
     * read is equal
     * to the length of {@code b}.
     * <p>
     * This method blocks until one of the
     * following conditions occurs:
     * <ul>
     * <li>{@code b.length}
     * bytes of input data are available, in which
     * case a normal return is made.
     *
     * <li>End of
     * file is detected, in which case an {@code EOFException}
     * is thrown.
     *
     * <li>An I/O error occurs, in
     * which case an {@code IOException} other
     * than {@code EOFException} is thrown.
     * </ul>
     * <p>
     * If {@code b} is {@code null},
     * a {@code NullPointerException} is thrown.
     * If {@code b.length} is zero, then
     * no bytes are read. Otherwise, the first
     * byte read is stored into element {@code b[0]},
     * the next one into {@code b[1]}, and
     * so on.
     * If an exception is thrown from
     * this method, then it may be that some but
     * not all bytes of {@code b} have been
     * updated with data from the input stream.
     *
     * <p>
     *  从输入流读取一些字节并将它们存储到缓冲区数组{@code b}读取的字节数等于{@code b}
     * <p>
     *  此方法阻止,直到出现以下情况之一：
     * <ul>
     *  <li>输入数据的{@ code blength}字节可用,在这种情况下,将返回正常
     * 
     *  <li>检测到文件结束,在这种情况下会抛出{@code EOFException}
     * 
     * <li>发生I / O错误,在这种情况下,会抛出除{@code EOFException}之外的{@code IOException}
     * </ul>
     * <p>
     *  如果{@code b}是{@code null},则抛出{@code NullPointerException}如果{@code blength}为零,则不读取任何字节。
     * 否则,读取的第一个字节存储到{@code b [ 0]},下一个进入{@code b [1]},等等如果从这个方法抛出一个异常,那么可能是{@code b}的一些但不是所有字节都已经更新了数据从输入流。
     *  如果{@code b}是{@code null},则抛出{@code NullPointerException}如果{@code blength}为零,则不读取任何字节。
     * 
     * 
     * @param     b   the buffer into which the data is read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    void readFully(byte b[]) throws IOException;

    /**
     *
     * Reads {@code len}
     * bytes from
     * an input stream.
     * <p>
     * This method
     * blocks until one of the following conditions
     * occurs:
     * <ul>
     * <li>{@code len} bytes
     * of input data are available, in which case
     * a normal return is made.
     *
     * <li>End of file
     * is detected, in which case an {@code EOFException}
     * is thrown.
     *
     * <li>An I/O error occurs, in
     * which case an {@code IOException} other
     * than {@code EOFException} is thrown.
     * </ul>
     * <p>
     * If {@code b} is {@code null},
     * a {@code NullPointerException} is thrown.
     * If {@code off} is negative, or {@code len}
     * is negative, or {@code off+len} is
     * greater than the length of the array {@code b},
     * then an {@code IndexOutOfBoundsException}
     * is thrown.
     * If {@code len} is zero,
     * then no bytes are read. Otherwise, the first
     * byte read is stored into element {@code b[off]},
     * the next one into {@code b[off+1]},
     * and so on. The number of bytes read is,
     * at most, equal to {@code len}.
     *
     * <p>
     *  从输入流读取{@code len}字节
     * <p>
     *  此方法阻止,直到出现以下情况之一：
     * <ul>
     *  <li>可以使用{@ code len}字节的输入数据,在这种情况下,将返回正常
     * 
     *  <li>检测到文件结束,在这种情况下会抛出{@code EOFException}
     * 
     * <li>发生I / O错误,在这种情况下,会抛出除{@code EOFException}之外的{@code IOException}
     * </ul>
     * <p>
     *  如果{@code b}是{@code null},则会抛出{@code NullPointerException}如果{@code off}为负值,或者{@code len}为负值或{@code off + len}
     * 数组{@code b}的长度,然后抛出一个{@code IndexOutOfBoundsException}如果{@code len}为0,那么没有字节被读取。
     * 否则,读取的第一个字节存储到元素{@code b [off ]},下一个变成{@code b [off + 1]},等等读取的字节数最多等于{@code len}。
     * 
     * 
     * @param     b   the buffer into which the data is read.
     * @param off  an int specifying the offset into the data.
     * @param len  an int specifying the number of bytes to read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    void readFully(byte b[], int off, int len) throws IOException;

    /**
     * Makes an attempt to skip over
     * {@code n} bytes
     * of data from the input
     * stream, discarding the skipped bytes. However,
     * it may skip
     * over some smaller number of
     * bytes, possibly zero. This may result from
     * any of a
     * number of conditions; reaching
     * end of file before {@code n} bytes
     * have been skipped is
     * only one possibility.
     * This method never throws an {@code EOFException}.
     * The actual
     * number of bytes skipped is returned.
     *
     * <p>
     * 尝试从输入流中跳过{@code n}字节的数据,丢弃跳过的字节然而,它可能跳过一些较小的字节数,可能为零这可能是由多个条件中的任何一个引起的;在{@code n}字节被跳过之前到达文件末尾只有一种可能性
     * 此方法不会抛出{@code EOFException}返回实际跳过的字节数。
     * 
     * 
     * @param      n   the number of bytes to be skipped.
     * @return     the number of bytes actually skipped.
     * @exception  IOException   if an I/O error occurs.
     */
    int skipBytes(int n) throws IOException;

    /**
     * Reads one input byte and returns
     * {@code true} if that byte is nonzero,
     * {@code false} if that byte is zero.
     * This method is suitable for reading
     * the byte written by the {@code writeBoolean}
     * method of interface {@code DataOutput}.
     *
     * <p>
     *  读取一个输入字节并返回{@code true}(如果该字节为非零),如果该字节为零,则返回{@code false}此方法适用于读取由接口{@code DataOutput}的{@code writeBoolean}
     *  }}。
     * 
     * 
     * @return     the {@code boolean} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    boolean readBoolean() throws IOException;

    /**
     * Reads and returns one input byte.
     * The byte is treated as a signed value in
     * the range {@code -128} through {@code 127},
     * inclusive.
     * This method is suitable for
     * reading the byte written by the {@code writeByte}
     * method of interface {@code DataOutput}.
     *
     * <p>
     * 读取并返回一个输入字节该字节在{@code -128}到{@code 127}范围内被视为有符号值,包含此方法适用于读取接口的{@code writeByte}方法写入的字节{@code DataOutput}
     * 。
     * 
     * 
     * @return     the 8-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    byte readByte() throws IOException;

    /**
     * Reads one input byte, zero-extends
     * it to type {@code int}, and returns
     * the result, which is therefore in the range
     * {@code 0}
     * through {@code 255}.
     * This method is suitable for reading
     * the byte written by the {@code writeByte}
     * method of interface {@code DataOutput}
     * if the argument to {@code writeByte}
     * was intended to be a value in the range
     * {@code 0} through {@code 255}.
     *
     * <p>
     *  读取一个输入字节,将其零扩展为类型{@code int},并返回结果,因此在{@code 0}到{@code 255}的范围内此方法适用于读取由如果{@code writeByte}的参数是一个在{@code 0}
     * 到{@code 255}范围内的值,{@code writeByte}接口{@code DataOutput}。
     * 
     * 
     * @return     the unsigned 8-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readUnsignedByte() throws IOException;

    /**
     * Reads two input bytes and returns
     * a {@code short} value. Let {@code a}
     * be the first byte read and {@code b}
     * be the second byte. The value
     * returned
     * is:
     * <pre>{@code (short)((a << 8) | (b & 0xff))
     * }</pre>
     * This method
     * is suitable for reading the bytes written
     * by the {@code writeShort} method of
     * interface {@code DataOutput}.
     *
     * <p>
     * 读取两个输入字节并返回一个{@code short}值让{@code a}是第一个字节读取,{@code b}是第二个字节返回的值是：<pre> {@ code(short)(( a << 8)|(b&0xff))} </pre>
     * 此方法适用于读取由接口{@code DataOutput}的{@code writeShort}。
     * 
     * 
     * @return     the 16-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    short readShort() throws IOException;

    /**
     * Reads two input bytes and returns
     * an {@code int} value in the range {@code 0}
     * through {@code 65535}. Let {@code a}
     * be the first byte read and
     * {@code b}
     * be the second byte. The value returned is:
     * <pre>{@code (((a & 0xff) << 8) | (b & 0xff))
     * }</pre>
     * This method is suitable for reading the bytes
     * written by the {@code writeShort} method
     * of interface {@code DataOutput}  if
     * the argument to {@code writeShort}
     * was intended to be a value in the range
     * {@code 0} through {@code 65535}.
     *
     * <p>
     * 读取两个输入字节并返回{@code 0}到{@code 65535}范围内的{@code int}值让{@code a}成为第一个字节读取,{@code b}成为第二个字节值返回的是：<pre> {@ code((a&0xff)<< 8)|(b&0xff))} </pre>
     * 此方法适用于读取由{@code writeShort} interface {@code DataOutput},如果{@code writeShort}的参数是要在{@code 0}到{@code 65535}
     * 范围内的值,。
     * 
     * 
     * @return     the unsigned 16-bit value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readUnsignedShort() throws IOException;

    /**
     * Reads two input bytes and returns a {@code char} value.
     * Let {@code a}
     * be the first byte read and {@code b}
     * be the second byte. The value
     * returned is:
     * <pre>{@code (char)((a << 8) | (b & 0xff))
     * }</pre>
     * This method
     * is suitable for reading bytes written by
     * the {@code writeChar} method of interface
     * {@code DataOutput}.
     *
     * <p>
     *  读取两个输入字节并返回一个{@code char}值让{@code a}是第一个字节读取,{@code b}是第二个字节返回的值是：<pre> {@ code(char)(( a << 8)|(b&0xff))} </pre>
     * 此方法适用于读取接口{@code DataOutput}的{@code writeChar}。
     * 
     * 
     * @return     the {@code char} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    char readChar() throws IOException;

    /**
     * Reads four input bytes and returns an
     * {@code int} value. Let {@code a-d}
     * be the first through fourth bytes read. The value returned is:
     * <pre>{@code
     * (((a & 0xff) << 24) | ((b & 0xff) << 16) |
     *  ((c & 0xff) <<  8) | (d & 0xff))
     * }</pre>
     * This method is suitable
     * for reading bytes written by the {@code writeInt}
     * method of interface {@code DataOutput}.
     *
     * <p>
     * 读取四个输入字节并返回一个{@code int}值让{@code ad}成为第一到第四个字节读返回的值是：<pre> {@ code(((a&0xff)<< 24)| (b&0xff)<< 16)|((c&0xff)<< 8)|(d&0xff))} </pre>
     * 此方法适用于读取由接口{@code writeInt} {@code DataOutput}。
     * 
     * 
     * @return     the {@code int} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    int readInt() throws IOException;

    /**
     * Reads eight input bytes and returns
     * a {@code long} value. Let {@code a-h}
     * be the first through eighth bytes read.
     * The value returned is:
     * <pre>{@code
     * (((long)(a & 0xff) << 56) |
     *  ((long)(b & 0xff) << 48) |
     *  ((long)(c & 0xff) << 40) |
     *  ((long)(d & 0xff) << 32) |
     *  ((long)(e & 0xff) << 24) |
     *  ((long)(f & 0xff) << 16) |
     *  ((long)(g & 0xff) <<  8) |
     *  ((long)(h & 0xff)))
     * }</pre>
     * <p>
     * This method is suitable
     * for reading bytes written by the {@code writeLong}
     * method of interface {@code DataOutput}.
     *
     * <p>
     *  读取8个输入字节并返回一个{@code long}值Let {@code ah}是第一个到第八个字节读取返回的值是：<pre> {@ code((long)(a&0xff)<< 56 )|((长)(b&0xff)<< 48)|((长)(c&0xff)<< 40) &0xff)<< 24)|((long)(f&0xff)<< 16)|((long)(g&0xff)<< 8) pre>。
     * <p>
     * 此方法适用于读取由接口{@code DataOutput}的{@code writeLong}方法写入的字节,
     * 
     * 
     * @return     the {@code long} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    long readLong() throws IOException;

    /**
     * Reads four input bytes and returns
     * a {@code float} value. It does this
     * by first constructing an {@code int}
     * value in exactly the manner
     * of the {@code readInt}
     * method, then converting this {@code int}
     * value to a {@code float} in
     * exactly the manner of the method {@code Float.intBitsToFloat}.
     * This method is suitable for reading
     * bytes written by the {@code writeFloat}
     * method of interface {@code DataOutput}.
     *
     * <p>
     *  读取四个输入字节并返回一个{@code float}值这是通过首先以{@code readInt}方法的方式构造一个{@code int}值,然后将此{@code int}值转换为{@code float}
     * 的方法与方法{@code FloatintBitsToFloat}的方式相同。
     * 此方法适用于读取接口{@code DataOutput}的{@code writeFloat}方法写入的字节。
     * 
     * 
     * @return     the {@code float} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    float readFloat() throws IOException;

    /**
     * Reads eight input bytes and returns
     * a {@code double} value. It does this
     * by first constructing a {@code long}
     * value in exactly the manner
     * of the {@code readLong}
     * method, then converting this {@code long}
     * value to a {@code double} in exactly
     * the manner of the method {@code Double.longBitsToDouble}.
     * This method is suitable for reading
     * bytes written by the {@code writeDouble}
     * method of interface {@code DataOutput}.
     *
     * <p>
     * 读取8个输入字节并返回一个{@code double}值它首先构造一个{@code long}值,方法与{@code readLong}方法完全相同,然后将此{@code long}值转换为{@code double}
     * 方法的方式{@code DoublelongBitsToDouble}此方法适用于读取接口{@code DataOutput}的{@code writeDouble}方法写入的字节,。
     * 
     * 
     * @return     the {@code double} value read.
     * @exception  EOFException  if this stream reaches the end before reading
     *               all the bytes.
     * @exception  IOException   if an I/O error occurs.
     */
    double readDouble() throws IOException;

    /**
     * Reads the next line of text from the input stream.
     * It reads successive bytes, converting
     * each byte separately into a character,
     * until it encounters a line terminator or
     * end of
     * file; the characters read are then
     * returned as a {@code String}. Note
     * that because this
     * method processes bytes,
     * it does not support input of the full Unicode
     * character set.
     * <p>
     * If end of file is encountered
     * before even one byte can be read, then {@code null}
     * is returned. Otherwise, each byte that is
     * read is converted to type {@code char}
     * by zero-extension. If the character {@code '\n'}
     * is encountered, it is discarded and reading
     * ceases. If the character {@code '\r'}
     * is encountered, it is discarded and, if
     * the following byte converts &#32;to the
     * character {@code '\n'}, then that is
     * discarded also; reading then ceases. If
     * end of file is encountered before either
     * of the characters {@code '\n'} and
     * {@code '\r'} is encountered, reading
     * ceases. Once reading has ceased, a {@code String}
     * is returned that contains all the characters
     * read and not discarded, taken in order.
     * Note that every character in this string
     * will have a value less than {@code \u005Cu0100},
     * that is, {@code (char)256}.
     *
     * <p>
     *  从输入流读取下一行文本它读取连续字节,将每个字节分别转换为字符,直到遇到行终止符或文件结尾;读取的字符将作为{@code String}返回。
     * 请注意,因为此方法处理字节,所以不支持输入完整的Unicode字符集。
     * <p>
     * 如果在可以读取一个字节之前遇到文件结尾,则返回{@code null}否则,通过零扩展将读取的每个字节转换为类型{@code char}如果字符{@code' n#'},它被丢弃并且读取停止如果遇到字符
     * {@code'\\ r'},它被丢弃,并且如果下面的字节转换为字符{@code'\n'}那么也被丢弃;读取然后停止如果在遇到字符{@code'\n'}和{@code'\\ r'}之一之前遇到文件结束,读
     * 取停止一旦读取停止,则返回{@code String}其中包含所有读取的字符而不是丢弃,按顺序进行请注意,此字符串中的每个字符都将具有小于{@code \\ u005Cu0100}的值,即{@code(char)256}
     * 。
     * 
     * 
     * @return the next line of text from the input stream,
     *         or {@code null} if the end of file is
     *         encountered before a byte can be read.
     * @exception  IOException  if an I/O error occurs.
     */
    String readLine() throws IOException;

    /**
     * Reads in a string that has been encoded using a
     * <a href="#modified-utf-8">modified UTF-8</a>
     * format.
     * The general contract of {@code readUTF}
     * is that it reads a representation of a Unicode
     * character string encoded in modified
     * UTF-8 format; this string of characters
     * is then returned as a {@code String}.
     * <p>
     * First, two bytes are read and used to
     * construct an unsigned 16-bit integer in
     * exactly the manner of the {@code readUnsignedShort}
     * method . This integer value is called the
     * <i>UTF length</i> and specifies the number
     * of additional bytes to be read. These bytes
     * are then converted to characters by considering
     * them in groups. The length of each group
     * is computed from the value of the first
     * byte of the group. The byte following a
     * group, if any, is the first byte of the
     * next group.
     * <p>
     * If the first byte of a group
     * matches the bit pattern {@code 0xxxxxxx}
     * (where {@code x} means "may be {@code 0}
     * or {@code 1}"), then the group consists
     * of just that byte. The byte is zero-extended
     * to form a character.
     * <p>
     * If the first byte
     * of a group matches the bit pattern {@code 110xxxxx},
     * then the group consists of that byte {@code a}
     * and a second byte {@code b}. If there
     * is no byte {@code b} (because byte
     * {@code a} was the last of the bytes
     * to be read), or if byte {@code b} does
     * not match the bit pattern {@code 10xxxxxx},
     * then a {@code UTFDataFormatException}
     * is thrown. Otherwise, the group is converted
     * to the character:
     * <pre>{@code (char)(((a & 0x1F) << 6) | (b & 0x3F))
     * }</pre>
     * If the first byte of a group
     * matches the bit pattern {@code 1110xxxx},
     * then the group consists of that byte {@code a}
     * and two more bytes {@code b} and {@code c}.
     * If there is no byte {@code c} (because
     * byte {@code a} was one of the last
     * two of the bytes to be read), or either
     * byte {@code b} or byte {@code c}
     * does not match the bit pattern {@code 10xxxxxx},
     * then a {@code UTFDataFormatException}
     * is thrown. Otherwise, the group is converted
     * to the character:
     * <pre>{@code
     * (char)(((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F))
     * }</pre>
     * If the first byte of a group matches the
     * pattern {@code 1111xxxx} or the pattern
     * {@code 10xxxxxx}, then a {@code UTFDataFormatException}
     * is thrown.
     * <p>
     * If end of file is encountered
     * at any time during this entire process,
     * then an {@code EOFException} is thrown.
     * <p>
     * After every group has been converted to
     * a character by this process, the characters
     * are gathered, in the same order in which
     * their corresponding groups were read from
     * the input stream, to form a {@code String},
     * which is returned.
     * <p>
     * The {@code writeUTF}
     * method of interface {@code DataOutput}
     * may be used to write data that is suitable
     * for reading by this method.
     * <p>
     * 在使用<a href=\"#modified-utf-8\">修改的UTF-8 </a>格式编码的字符串中读取{@code readUTF}的一般合同是它读取Unicode的表示形式以修改的UTF-8格
     * 式编码的字符串;这个字符串然后作为{@code String}返回,。
     * <p>
     *  首先,读取两个字节并用于以{@code readUnsignedShort}方法的方式构造无符号的16位整数。
     * 该整数值称为<i> UTF长度</i>,并指定附加字节数要读取这些字节然后通过以组的形式考虑将其转换为字符每组的长度从组的第一个字节的值计算组的后面的字节(如果有的话)是下一组的第一个字节。
     * <p>
     * 如果组的第一个字节与位模式{@code 0xxxxxxx}匹配(其中{@code x}表示"可能是{@code 0}或{@code 1}"),那么组只包含该字节字节是零扩展形成一个字符
     * <p>
     * 如果组的第一个字节与位模式{@code 110xxxxx}匹配,则该组由该字节{@code a}和第二个字节{@code b}组成。
     * 如果没有字节{@code b} byte {@code a}是要读取的最后一个字节),或者如果byte {@code b}与位模式{@code 10xxxxxx}不匹配,则抛出{@code UTFDataFormatException}
     * 否则,组被转换为字符：<pre> {@ code(char)(((a&0x1F)<< 6)|(b&0x3F))} </pre>如果组的第一个字节匹配位模式{ @code 1110xxxx},那么该组由该
     * 
     * @return     a Unicode string.
     * @exception  EOFException            if this stream reaches the end
     *               before reading all the bytes.
     * @exception  IOException             if an I/O error occurs.
     * @exception  UTFDataFormatException  if the bytes do not represent a
     *               valid modified UTF-8 encoding of a string.
     */
    String readUTF() throws IOException;
}
