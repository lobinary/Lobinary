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

import java.util.Arrays;

/**
 * This class implements an output stream in which the data is
 * written into a byte array. The buffer automatically grows as data
 * is written to it.
 * The data can be retrieved using <code>toByteArray()</code> and
 * <code>toString()</code>.
 * <p>
 * Closing a <tt>ByteArrayOutputStream</tt> has no effect. The methods in
 * this class can be called after the stream has been closed without
 * generating an <tt>IOException</tt>.
 *
 * <p>
 *  这个类实现了一个输出流,其中数据被写入一个字节数组。缓冲区随着数据写入缓冲区而自动增长。
 * 可以使用<code> toByteArray()</code>和<code> toString()</code>来检索数据。
 * <p>
 *  关闭<tt> ByteArrayOutputStream </tt>没有任何效果。可以在流关闭后调用此类中的方法,而不生成<tt> IOException </tt>。
 * 
 * 
 * @author  Arthur van Hoff
 * @since   JDK1.0
 */

public class ByteArrayOutputStream extends OutputStream {

    /**
     * The buffer where data is stored.
     * <p>
     *  存储数据的缓冲区。
     * 
     */
    protected byte buf[];

    /**
     * The number of valid bytes in the buffer.
     * <p>
     *  缓冲区中的有效字节数。
     * 
     */
    protected int count;

    /**
     * Creates a new byte array output stream. The buffer capacity is
     * initially 32 bytes, though its size increases if necessary.
     * <p>
     *  创建一个新的字节数组输出流。缓冲区容量最初为32字节,但如果必要,则其大小增加。
     * 
     */
    public ByteArrayOutputStream() {
        this(32);
    }

    /**
     * Creates a new byte array output stream, with a buffer capacity of
     * the specified size, in bytes.
     *
     * <p>
     *  创建一个新的字节数组输出流,具有指定大小的缓冲区容量,以字节为单位。
     * 
     * 
     * @param   size   the initial size.
     * @exception  IllegalArgumentException if size is negative.
     */
    public ByteArrayOutputStream(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Negative initial size: "
                                               + size);
        }
        buf = new byte[size];
    }

    /**
     * Increases the capacity if necessary to ensure that it can hold
     * at least the number of elements specified by the minimum
     * capacity argument.
     *
     * <p>
     *  如果需要,增加容量,以确保它至少可以容纳由最小容量参数指定的元素数量。
     * 
     * 
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if {@code minCapacity < 0}.  This is
     * interpreted as a request for the unsatisfiably large capacity
     * {@code (long) Integer.MAX_VALUE + (minCapacity - Integer.MAX_VALUE)}.
     */
    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - buf.length > 0)
            grow(minCapacity);
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     * <p>
     *  要分配的数组的最大大小。一些VM在数组中保留一些标题字。尝试分配较大的数组可能会导致OutOfMemoryError：请求的数组大小超过VM限制
     * 
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * <p>
     *  增加容量以确保它至少可以容纳由最小容量参数指定的元素数。
     * 
     * 
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = buf.length;
        int newCapacity = oldCapacity << 1;
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        buf = Arrays.copyOf(buf, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }

    /**
     * Writes the specified byte to this byte array output stream.
     *
     * <p>
     *  将指定的字节写入此字节数组输出流。
     * 
     * 
     * @param   b   the byte to be written.
     */
    public synchronized void write(int b) {
        ensureCapacity(count + 1);
        buf[count] = (byte) b;
        count += 1;
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array
     * starting at offset <code>off</code> to this byte array output stream.
     *
     * <p>
     *  从指定的字节数组开始,从<code> off </code>开始,将<code> len </code>字节写入此字节数组输出流。
     * 
     * 
     * @param   b     the data.
     * @param   off   the start offset in the data.
     * @param   len   the number of bytes to write.
     */
    public synchronized void write(byte b[], int off, int len) {
        if ((off < 0) || (off > b.length) || (len < 0) ||
            ((off + len) - b.length > 0)) {
            throw new IndexOutOfBoundsException();
        }
        ensureCapacity(count + len);
        System.arraycopy(b, off, buf, count, len);
        count += len;
    }

    /**
     * Writes the complete contents of this byte array output stream to
     * the specified output stream argument, as if by calling the output
     * stream's write method using <code>out.write(buf, 0, count)</code>.
     *
     * <p>
     * 将此字节数组输出流的完整内容写入指定的输出流参数,就像使用<code> out.write(buf,0,count)</code>调用输出流的写入方法。
     * 
     * 
     * @param      out   the output stream to which to write the data.
     * @exception  IOException  if an I/O error occurs.
     */
    public synchronized void writeTo(OutputStream out) throws IOException {
        out.write(buf, 0, count);
    }

    /**
     * Resets the <code>count</code> field of this byte array output
     * stream to zero, so that all currently accumulated output in the
     * output stream is discarded. The output stream can be used again,
     * reusing the already allocated buffer space.
     *
     * <p>
     *  将此字节数组输出流的<code> count </code>字段重置为零,以便丢弃输出流中的所有当前累积输出。输出流可以再次使用,重用已分配的缓冲区空间。
     * 
     * 
     * @see     java.io.ByteArrayInputStream#count
     */
    public synchronized void reset() {
        count = 0;
    }

    /**
     * Creates a newly allocated byte array. Its size is the current
     * size of this output stream and the valid contents of the buffer
     * have been copied into it.
     *
     * <p>
     *  创建新分配的字节数组。其大小是此输出流的当前大小,并且缓冲区的有效内容已复制到其中。
     * 
     * 
     * @return  the current contents of this output stream, as a byte array.
     * @see     java.io.ByteArrayOutputStream#size()
     */
    public synchronized byte toByteArray()[] {
        return Arrays.copyOf(buf, count);
    }

    /**
     * Returns the current size of the buffer.
     *
     * <p>
     *  返回缓冲区的当前大小。
     * 
     * 
     * @return  the value of the <code>count</code> field, which is the number
     *          of valid bytes in this output stream.
     * @see     java.io.ByteArrayOutputStream#count
     */
    public synchronized int size() {
        return count;
    }

    /**
     * Converts the buffer's contents into a string decoding bytes using the
     * platform's default character set. The length of the new <tt>String</tt>
     * is a function of the character set, and hence may not be equal to the
     * size of the buffer.
     *
     * <p> This method always replaces malformed-input and unmappable-character
     * sequences with the default replacement string for the platform's
     * default character set. The {@linkplain java.nio.charset.CharsetDecoder}
     * class should be used when more control over the decoding process is
     * required.
     *
     * <p>
     *  使用平台的默认字符集将缓冲区的内容转换为字符串解码字节。新<tt> String </tt>的长度是字符集的函数,因此可能不等于缓冲区的大小。
     * 
     *  <p>此方法始终使用平台默认字符集的默认替换字符串替换格式不正确的输入和不可映射字符序列。
     * 当需要更多地控制解码过程时,应该使用{@linkplain java.nio.charset.CharsetDecoder}类。
     * 
     * 
     * @return String decoded from the buffer's contents.
     * @since  JDK1.1
     */
    public synchronized String toString() {
        return new String(buf, 0, count);
    }

    /**
     * Converts the buffer's contents into a string by decoding the bytes using
     * the named {@link java.nio.charset.Charset charset}. The length of the new
     * <tt>String</tt> is a function of the charset, and hence may not be equal
     * to the length of the byte array.
     *
     * <p> This method always replaces malformed-input and unmappable-character
     * sequences with this charset's default replacement string. The {@link
     * java.nio.charset.CharsetDecoder} class should be used when more control
     * over the decoding process is required.
     *
     * <p>
     *  使用命名的{@link java.nio.charset.Charset charset}解码字节,将缓冲区的内容转换为字符串。
     * 新<tt> String </tt>的长度是charset的函数,因此可能不等于字节数组的长度。
     * 
     * <p>此方法总是使用此字符集的默认替换字符串替换格式不正确的输入和不可映射字符序列。
     * 当需要更多地控制解码过程时,应该使用{@link java.nio.charset.CharsetDecoder}类。
     * 
     * 
     * @param      charsetName  the name of a supported
     *             {@link java.nio.charset.Charset charset}
     * @return     String decoded from the buffer's contents.
     * @exception  UnsupportedEncodingException
     *             If the named charset is not supported
     * @since      JDK1.1
     */
    public synchronized String toString(String charsetName)
        throws UnsupportedEncodingException
    {
        return new String(buf, 0, count, charsetName);
    }

    /**
     * Creates a newly allocated string. Its size is the current size of
     * the output stream and the valid contents of the buffer have been
     * copied into it. Each character <i>c</i> in the resulting string is
     * constructed from the corresponding element <i>b</i> in the byte
     * array such that:
     * <blockquote><pre>
     *     c == (char)(((hibyte &amp; 0xff) &lt;&lt; 8) | (b &amp; 0xff))
     * </pre></blockquote>
     *
     * <p>
     *  创建新分配的字符串。其大小是输出流的当前大小,并且缓冲区的有效内容已复制到其中。
     * 从字节数组中的相应元素<b> </i>构造所得字符串中的每个字符</i>,使得：<blockquote> <pre> c ==(char)(( hibyte&amp; 0xff)<8)|(b&amp; 0xff))</pre>
     *  </blockquote>。
     *  创建新分配的字符串。其大小是输出流的当前大小,并且缓冲区的有效内容已复制到其中。
     * 
     * @deprecated This method does not properly convert bytes into characters.
     * As of JDK&nbsp;1.1, the preferred way to do this is via the
     * <code>toString(String enc)</code> method, which takes an encoding-name
     * argument, or the <code>toString()</code> method, which uses the
     * platform's default character encoding.
     *
     * @param      hibyte    the high byte of each resulting Unicode character.
     * @return     the current contents of the output stream, as a string.
     * @see        java.io.ByteArrayOutputStream#size()
     * @see        java.io.ByteArrayOutputStream#toString(String)
     * @see        java.io.ByteArrayOutputStream#toString()
     */
    @Deprecated
    public synchronized String toString(int hibyte) {
        return new String(buf, hibyte, 0, count);
    }

    /**
     * Closing a <tt>ByteArrayOutputStream</tt> has no effect. The methods in
     * this class can be called after the stream has been closed without
     * generating an <tt>IOException</tt>.
     * <p>
     * 
     */
    public void close() throws IOException {
    }

}
