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
 * The <code>DataOutput</code> interface provides
 * for converting data from any of the Java
 * primitive types to a series of bytes and
 * writing these bytes to a binary stream.
 * There is  also a facility for converting
 * a <code>String</code> into
 * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
 * format and writing the resulting series
 * of bytes.
 * <p>
 * For all the methods in this interface that
 * write bytes, it is generally true that if
 * a byte cannot be written for any reason,
 * an <code>IOException</code> is thrown.
 *
 * <p>
 *  <code> DataOutput </code>接口提供将任何Java基本类型的数据转换为一系列字节,并将这些字节写入二进制流。
 * 还有一个工具可以转换<code> String </code>转换成<a href=\"DataInputhtml#modified-utf-8\">修改的UTF-8 </a>格式并将结果系列字节。
 * <p>
 *  对于该接口中写入字节的所有方法,一般来说,如果一个字节由于任何原因而不能被写入,则抛出一个<code> IOException </code>
 * 
 * 
 * @author  Frank Yellin
 * @see     java.io.DataInput
 * @see     java.io.DataOutputStream
 * @since   JDK1.0
 */
public
interface DataOutput {
    /**
     * Writes to the output stream the eight
     * low-order bits of the argument <code>b</code>.
     * The 24 high-order  bits of <code>b</code>
     * are ignored.
     *
     * <p>
     *  将输出流的参数<code> b </code>的八个低阶位写入<code> b </code>的24个高阶位将被忽略
     * 
     * 
     * @param      b   the byte to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void write(int b) throws IOException;

    /**
     * Writes to the output stream all the bytes in array <code>b</code>.
     * If <code>b</code> is <code>null</code>,
     * a <code>NullPointerException</code> is thrown.
     * If <code>b.length</code> is zero, then
     * no bytes are written. Otherwise, the byte
     * <code>b[0]</code> is written first, then
     * <code>b[1]</code>, and so on; the last byte
     * written is <code>b[b.length-1]</code>.
     *
     * <p>
     * 对输出流写入数组中的所有字节<code> b </code>如果<code> b </code>是<code> null </code>,则抛出一个<code> NullPointerException
     *  </code>代码> blength </code>为零,则不写入任何字节。
     * 否则,先写入字节<code> b [0] </code>,然后写入<code> b [1] </code> ;所写的最后一个字节是<code> b [blength-1] </code>。
     * 
     * 
     * @param      b   the data.
     * @throws     IOException  if an I/O error occurs.
     */
    void write(byte b[]) throws IOException;

    /**
     * Writes <code>len</code> bytes from array
     * <code>b</code>, in order,  to
     * the output stream.  If <code>b</code>
     * is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.  If <code>off</code> is negative,
     * or <code>len</code> is negative, or <code>off+len</code>
     * is greater than the length of the array
     * <code>b</code>, then an <code>IndexOutOfBoundsException</code>
     * is thrown.  If <code>len</code> is zero,
     * then no bytes are written. Otherwise, the
     * byte <code>b[off]</code> is written first,
     * then <code>b[off+1]</code>, and so on; the
     * last byte written is <code>b[off+len-1]</code>.
     *
     * <p>
     * 将<code> len </code>字节从数组<code> b </code>中依次写入输出流如果<code> b </code>是<code> null </code> > NullPointerE
     * xception </code>被抛出如果<code> off </code>为负数,或<code> len </code>为负数,或<code> off + len </code>数组<code> b
     *  </code>,则抛出<code> IndexOutOfBoundsException </code>如果<code> len </code>为零,则不写入字节。
     * 否则, </code>先写,然后<code> b [off + 1] </code>,依此类推;写入的最后一个字节是<code> b [off + len-1] </code>。
     * 
     * 
     * @param      b     the data.
     * @param      off   the start offset in the data.
     * @param      len   the number of bytes to write.
     * @throws     IOException  if an I/O error occurs.
     */
    void write(byte b[], int off, int len) throws IOException;

    /**
     * Writes a <code>boolean</code> value to this output stream.
     * If the argument <code>v</code>
     * is <code>true</code>, the value <code>(byte)1</code>
     * is written; if <code>v</code> is <code>false</code>,
     * the  value <code>(byte)0</code> is written.
     * The byte written by this method may
     * be read by the <code>readBoolean</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>boolean</code>
     * equal to <code>v</code>.
     *
     * <p>
     * 将<code> boolean </code>值写入此输出流如果参数<code> v </code>为<code> true </code>,则<code>(byte)1 </code>书面;如果<code>
     *  v </code>是<code> false </code>,则写入<code>(byte)0 </code>的值通过此方法写入的字节可以由<code> readBoolean < / code>接口
     * <code> DataInput </code>的方法,然后它将返回一个<code> boolean </code>等于<code> v </code>。
     * 
     * 
     * @param      v   the boolean to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeBoolean(boolean v) throws IOException;

    /**
     * Writes to the output stream the eight low-
     * order bits of the argument <code>v</code>.
     * The 24 high-order bits of <code>v</code>
     * are ignored. (This means  that <code>writeByte</code>
     * does exactly the same thing as <code>write</code>
     * for an integer argument.) The byte written
     * by this method may be read by the <code>readByte</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>byte</code>
     * equal to <code>(byte)v</code>.
     *
     * <p>
     * 向输出流写入参数<code> v </code>的八个低位bit <code> v </code>的24个高位被忽略(这意味着<code> writeByte </code >对整数参数执行与<code>
     *  write </code>完全相同的事情)由此方法写入的字节可以由接口<code> DataInput </code>的<code> readByte </code> ,然后它将返回等于<code>(
     * byte)v </code>的<code>字节</code>。
     * 
     * 
     * @param      v   the byte value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeByte(int v) throws IOException;

    /**
     * Writes two bytes to the output
     * stream to represent the value of the argument.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 8))
     * (byte)(0xff & v)
     * }</pre> <p>
     * The bytes written by this method may be
     * read by the <code>readShort</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>short</code> equal
     * to <code>(short)v</code>.
     *
     * <p>
     * 将两个字节写入输出流以表示参数的值按所示顺序写入的字节值为：<pre> {@ code(byte)(0xff&(v >> 8))(byte) (0xff&v)} </pre> <p>由此方法写入的字节可
     * 以由接口<code> DataInput </code>的<code> readShort </code>方法读取, code> short </code>等于<code>(short)v </code>
     * 。
     * 
     * 
     * @param      v   the <code>short</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeShort(int v) throws IOException;

    /**
     * Writes a <code>char</code> value, which
     * is comprised of two bytes, to the
     * output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be
     * read by the <code>readChar</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>char</code> equal
     * to <code>(char)v</code>.
     *
     * <p>
     *  向输出流写入由两个字节组成的<code> char </code>值按所示顺序写入的字节值为：<pre> {@ code(byte)(0xff&(该方法写入的字节可以由接口<code> DataInput </code>的<code> readChar </code>方法读取。
     *  code>,然后它将返回等于<code>(char)v </code>的<code> char </code>。
     * 
     * 
     * @param      v   the <code>char</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeChar(int v) throws IOException;

    /**
     * Writes an <code>int</code> value, which is
     * comprised of four bytes, to the output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 24))
     * (byte)(0xff & (v >> 16))
     * (byte)(0xff & (v >>  8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be read
     * by the <code>readInt</code> method of interface
     * <code>DataInput</code> , which will then
     * return an <code>int</code> equal to <code>v</code>.
     *
     * <p>
     * 向输出流写入由四个字节组成的<code> int </code>值按所示顺序写入的字节值为：<pre> {@ code(byte)(0xff&( v >> 24))(byte)(0xff&(v >> 16))(byte)(0xff&(v >> 8))(byte)(0xff&v)}
     *  </pre>可以通过接口<code> DataInput </code>的<code> readInt </code>方法读取该方法写入的数据,然后它将返回等于<code> v </code>的<code>
     *  int </code>代码>。
     * 
     * 
     * @param      v   the <code>int</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeInt(int v) throws IOException;

    /**
     * Writes a <code>long</code> value, which is
     * comprised of eight bytes, to the output stream.
     * The byte values to be written, in the  order
     * shown, are:
     * <pre>{@code
     * (byte)(0xff & (v >> 56))
     * (byte)(0xff & (v >> 48))
     * (byte)(0xff & (v >> 40))
     * (byte)(0xff & (v >> 32))
     * (byte)(0xff & (v >> 24))
     * (byte)(0xff & (v >> 16))
     * (byte)(0xff & (v >>  8))
     * (byte)(0xff & v)
     * }</pre><p>
     * The bytes written by this method may be
     * read by the <code>readLong</code> method
     * of interface <code>DataInput</code> , which
     * will then return a <code>long</code> equal
     * to <code>v</code>.
     *
     * <p>
     * 向输出流写入由8个字节组成的<code> long </code>值按所示顺序写入的字节值为：<pre> {@ code(byte)(0xff&( v >> 56))(字节)(0xff&(v >> 48))(字节)(0xff&(v >> 40) &(v >> 24))(byte)(0xff&(v >> 16))(byte)(0xff&(v >> 8))(byte)(0xff&v)由此方法写入的字节可以由接口<code> DataInput </code>的<code> readLong </code>方法读取,然后它将返回等于<code> v的<code> long </code> </code>。
     * 
     * 
     * @param      v   the <code>long</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeLong(long v) throws IOException;

    /**
     * Writes a <code>float</code> value,
     * which is comprised of four bytes, to the output stream.
     * It does this as if it first converts this
     * <code>float</code> value to an <code>int</code>
     * in exactly the manner of the <code>Float.floatToIntBits</code>
     * method  and then writes the <code>int</code>
     * value in exactly the manner of the  <code>writeInt</code>
     * method.  The bytes written by this method
     * may be read by the <code>readFloat</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>float</code>
     * equal to <code>v</code>.
     *
     * <p>
     * 将一个由四个字节组成的<code> float </code>值写入输出流。
     * 这就好像它首先将<code> float </code>值转换为<code> int </code >以<code> FloatfloatToIntBits </code>方法的方式,然后以<code>
     *  writeInt </code>方法的方式写入<code> int </code>值。
     * 将一个由四个字节组成的<code> float </code>值写入输出流。
     * 可以由接口<code> DataInput </code>的<code> readFloat </code>方法读取,然后它将返回等于<code> v </code>的<code> float </code>
     * 。
     * 将一个由四个字节组成的<code> float </code>值写入输出流。
     * 
     * 
     * @param      v   the <code>float</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeFloat(float v) throws IOException;

    /**
     * Writes a <code>double</code> value,
     * which is comprised of eight bytes, to the output stream.
     * It does this as if it first converts this
     * <code>double</code> value to a <code>long</code>
     * in exactly the manner of the <code>Double.doubleToLongBits</code>
     * method  and then writes the <code>long</code>
     * value in exactly the manner of the  <code>writeLong</code>
     * method. The bytes written by this method
     * may be read by the <code>readDouble</code>
     * method of interface <code>DataInput</code>,
     * which will then return a <code>double</code>
     * equal to <code>v</code>.
     *
     * <p>
     * 向输出流写入一个由八个字节组成的<code> double </code>值。
     * 这样做就好像它首先将<code> double </code>值转换为<code> long </code >以与<code> DoubledoubleToLongBits </code>方法完全相同的
     * 方式,然后以<code> writeLong </code>方法的方式写入<code> long </code>可以由接口<code> DataInput </code>的<code> readDoub
     * le </code>方法读取,然后它将返回等于<code> v </code>的<code> double </code>。
     * 向输出流写入一个由八个字节组成的<code> double </code>值。
     * 
     * 
     * @param      v   the <code>double</code> value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeDouble(double v) throws IOException;

    /**
     * Writes a string to the output stream.
     * For every character in the string
     * <code>s</code>,  taken in order, one byte
     * is written to the output stream.  If
     * <code>s</code> is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.<p>  If <code>s.length</code>
     * is zero, then no bytes are written. Otherwise,
     * the character <code>s[0]</code> is written
     * first, then <code>s[1]</code>, and so on;
     * the last character written is <code>s[s.length-1]</code>.
     * For each character, one byte is written,
     * the low-order byte, in exactly the manner
     * of the <code>writeByte</code> method . The
     * high-order eight bits of each character
     * in the string are ignored.
     *
     * <p>
     * 将字符串写入输出流对于字符串<code> s </code>中的每个字符,按顺序将一个字节写入输出流If <code> s </code> is <code> null <如果<code> slengt
     * h </code>为零,则不写入任何字节。
     * 否则,字符<code> s [0] </code>先写,然后<code> s [1] </code>,依此类推;最后写入的字符是<code> s [slength-1] </code>对于每个字符,以<code>
     *  writeByte </code>方法的方式写入一个字节,低字节忽略字符串中每个字符的8位。
     * 
     * 
     * @param      s   the string of bytes to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeBytes(String s) throws IOException;

    /**
     * Writes every character in the string <code>s</code>,
     * to the output stream, in order,
     * two bytes per character. If <code>s</code>
     * is <code>null</code>, a <code>NullPointerException</code>
     * is thrown.  If <code>s.length</code>
     * is zero, then no characters are written.
     * Otherwise, the character <code>s[0]</code>
     * is written first, then <code>s[1]</code>,
     * and so on; the last character written is
     * <code>s[s.length-1]</code>. For each character,
     * two bytes are actually written, high-order
     * byte first, in exactly the manner of the
     * <code>writeChar</code> method.
     *
     * <p>
     * 将字符串<code> s </code>中的每个字符按顺序写入输出流。
     * 如果<code> s </code>是<code> null </code>,则<code>抛出NullPointerException </code>如果<code> slength </code>为
     * 零,则不写入任何字符。
     * 将字符串<code> s </code>中的每个字符按顺序写入输出流。
     * 否则,首先写入字符<code> s [0] </code>,然后输入<code> s [1 ] </code>,等等;最后写入的字符是<code> s [slength-1] </code>对于每个字符
     * ,两个字节实际写入,高位字节优先,以<code> writeChar </code>。
     * 将字符串<code> s </code>中的每个字符按顺序写入输出流。
     * 
     * 
     * @param      s   the string value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeChars(String s) throws IOException;

    /**
     * Writes two bytes of length information
     * to the output stream, followed
     * by the
     * <a href="DataInput.html#modified-utf-8">modified UTF-8</a>
     * representation
     * of  every character in the string <code>s</code>.
     * If <code>s</code> is <code>null</code>,
     * a <code>NullPointerException</code> is thrown.
     * Each character in the string <code>s</code>
     * is converted to a group of one, two, or
     * three bytes, depending on the value of the
     * character.<p>
     * If a character <code>c</code>
     * is in the range <code>&#92;u0001</code> through
     * <code>&#92;u007f</code>, it is represented
     * by one byte:
     * <pre>(byte)c </pre>  <p>
     * If a character <code>c</code> is <code>&#92;u0000</code>
     * or is in the range <code>&#92;u0080</code>
     * through <code>&#92;u07ff</code>, then it is
     * represented by two bytes, to be written
     * in the order shown: <pre>{@code
     * (byte)(0xc0 | (0x1f & (c >> 6)))
     * (byte)(0x80 | (0x3f & c))
     * }</pre> <p> If a character
     * <code>c</code> is in the range <code>&#92;u0800</code>
     * through <code>uffff</code>, then it is
     * represented by three bytes, to be written
     * in the order shown: <pre>{@code
     * (byte)(0xe0 | (0x0f & (c >> 12)))
     * (byte)(0x80 | (0x3f & (c >>  6)))
     * (byte)(0x80 | (0x3f & c))
     * }</pre>  <p> First,
     * the total number of bytes needed to represent
     * all the characters of <code>s</code> is
     * calculated. If this number is larger than
     * <code>65535</code>, then a <code>UTFDataFormatException</code>
     * is thrown. Otherwise, this length is written
     * to the output stream in exactly the manner
     * of the <code>writeShort</code> method;
     * after this, the one-, two-, or three-byte
     * representation of each character in the
     * string <code>s</code> is written.<p>  The
     * bytes written by this method may be read
     * by the <code>readUTF</code> method of interface
     * <code>DataInput</code> , which will then
     * return a <code>String</code> equal to <code>s</code>.
     *
     * <p>
     * 将两个字节的长度信息写入输出流,然后是字符串<code> s </code中的每个字符的<a href=\"DataInputhtml#modified-utf-8\">修改的UTF-8 </a>表示形
     * 式>如果<code> s </code>是<code> null </code>,则抛出<code> NullPointerException </code>字符串<code> s </code>中的每
     * 个字符都将转换为一组一个,两个或三个字节,取决于字符的值<p>如果字符<code> c </code>在<code> \\ u0001 </code>到<code> \\ u007f </code>的范
     * 围内,则由一个字节表示：<pre> )c </pre> <p>如果字符<code> c </code>是<code> \\ u0000 </code>或在<code> \\ u0080 </code>到
     * <code> \\ u07ff </code>,则它由两个字节表示,以所示的顺序写入：<pre> {@ code(byte)(0xc0 |(0x1f&(c >> 6)))(byte) (0x3f&c))}
     *  </pre> <p>如果字符<code> c </code>在<code> \\ u0800 </code>到<code> uffff </code> (0xe0 |(0x0f&(c >> 12)))
     * (字节)(0x80 |(0x3f&(c >> 12))表示为三个字节, 6)))(byte)(0x80 |(0x3f&c))} </pre> <p>首先,计算<code> s </code>的字符将两个
     * 
     * @param      s   the string value to be written.
     * @throws     IOException  if an I/O error occurs.
     */
    void writeUTF(String s) throws IOException;
}
