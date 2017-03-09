/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A class representing a mutable reference to an array of bytes and
 * an offset and length within that array.  <code>IIOByteBuffer</code>
 * is used by <code>ImageInputStream</code> to supply a sequence of bytes
 * to the caller, possibly with fewer copies than using the conventional
 * <code>read</code> methods that take a user-supplied byte array.
 *
 * <p> The byte array referenced by an <code>IIOByteBuffer</code> will
 * generally be part of an internal data structure belonging to an
 * <code>ImageReader</code> implementation; its contents should be
 * considered read-only and must not be modified.
 *
 * <p>
 *  表示对字节数组的可变引用的类,以及该数组内的偏移和长度。
 *  <code> ImageInputStream </code>使用<code> IIOByteBuffer </code>向调用者提供一个字节序列,可能比使用传统的<code> read </code>
 * 方法提供的字节数组。
 *  表示对字节数组的可变引用的类,以及该数组内的偏移和长度。
 * 
 *  <p>由<code> IIOByteBuffer </code>引用的字节数组通常是属于<code> ImageReader </code>实现的内部数据结构的一部分;其内容应被视为只读,且不得修改。
 * 
 */
public class IIOByteBuffer {

    private byte[] data;

    private int offset;

    private int length;

    /**
     * Constructs an <code>IIOByteBuffer</code> that references a
     * given byte array, offset, and length.
     *
     * <p>
     *  构造一个引用给定字节数组,偏移量和长度的<code> IIOByteBuffer </code>。
     * 
     * 
     * @param data a byte array.
     * @param offset an int offset within the array.
     * @param length an int specifying the length of the data of
     * interest within byte array, in bytes.
     */
    public IIOByteBuffer(byte[] data, int offset, int length) {
        this.data = data;
        this.offset = offset;
        this.length = length;
    }

    /**
     * Returns a reference to the byte array.  The returned value should
     * be treated as read-only, and only the portion specified by the
     * values of <code>getOffset</code> and <code>getLength</code> should
     * be used.
     *
     * <p>
     *  返回对字节数组的引用。返回的值应该被视为只读,并且只应该使用由<code> getOffset </code>和<code> getLength </code>的值指定的部分。
     * 
     * 
     * @return a byte array reference.
     *
     * @see #getOffset
     * @see #getLength
     * @see #setData
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Updates the array reference that will be returned by subsequent calls
     * to the <code>getData</code> method.
     *
     * <p>
     *  更新将通过对<code> getData </code>方法的后续调用返回的数组引用。
     * 
     * 
     * @param data a byte array reference containing the new data value.
     *
     * @see #getData
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * Returns the offset within the byte array returned by
     * <code>getData</code> at which the data of interest start.
     *
     * <p>
     *  返回由感兴趣的数据开始的<code> getData </code>返回的字节数组内的偏移量。
     * 
     * 
     * @return an int offset.
     *
     * @see #getData
     * @see #getLength
     * @see #setOffset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Updates the value that will be returned by subsequent calls
     * to the <code>getOffset</code> method.
     *
     * <p>
     *  更新后续调用返回到<code> getOffset </code>方法的值。
     * 
     * 
     * @param offset an int containing the new offset value.
     *
     * @see #getOffset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Returns the length of the data of interest within the byte
     * array returned by <code>getData</code>.
     *
     * <p>
     *  返回<code> getData </code>返回的字节数组中感兴趣的数据的长度。
     * 
     * 
     * @return an int length.
     *
     * @see #getData
     * @see #getOffset
     * @see #setLength
     */
    public int getLength() {
        return length;
    }

    /**
     * Updates the value that will be returned by subsequent calls
     * to the <code>getLength</code> method.
     *
     * <p>
     * 更新后续调用返回到<code> getLength </code>方法的值。
     * 
     * @param length an int containing the new length value.
     *
     * @see #getLength
     */
    public void setLength(int length) {
        this.length = length;
    }
}
