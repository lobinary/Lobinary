/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

import java.nio.ByteBuffer;
import sun.nio.ch.DirectBuffer;

/**
 * A class that can be used to compute the Adler-32 checksum of a data
 * stream. An Adler-32 checksum is almost as reliable as a CRC-32 but
 * can be computed much faster.
 *
 * <p> Passing a {@code null} argument to a method in this class will cause
 * a {@link NullPointerException} to be thrown.
 *
 * <p>
 *  可用于计算数据流的Adler-32校验和的类。 Adler-32校验和几乎与CRC-32一样可靠,但可以计算得快得多。
 * 
 *  <p>将{@code null}参数传递给此类中的方法将导致抛出{@link NullPointerException}。
 * 
 * 
 * @see         Checksum
 * @author      David Connelly
 */
public
class Adler32 implements Checksum {

    private int adler = 1;

    /**
     * Creates a new Adler32 object.
     * <p>
     *  创建一个新的Adler32对象。
     * 
     */
    public Adler32() {
    }

    /**
     * Updates the checksum with the specified byte (the low eight
     * bits of the argument b).
     *
     * <p>
     *  使用指定的字节(参数b的低8位)更新校验和。
     * 
     * 
     * @param b the byte to update the checksum with
     */
    public void update(int b) {
        adler = update(adler, b);
    }

    /**
     * Updates the checksum with the specified array of bytes.
     *
     * <p>
     *  使用指定的字节数更新校验和。
     * 
     * 
     * @throws  ArrayIndexOutOfBoundsException
     *          if {@code off} is negative, or {@code len} is negative,
     *          or {@code off+len} is greater than the length of the
     *          array {@code b}
     */
    public void update(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        adler = updateBytes(adler, b, off, len);
    }

    /**
     * Updates the checksum with the specified array of bytes.
     *
     * <p>
     *  使用指定的字节数更新校验和。
     * 
     * 
     * @param b the byte array to update the checksum with
     */
    public void update(byte[] b) {
        adler = updateBytes(adler, b, 0, b.length);
    }


    /**
     * Updates the checksum with the bytes from the specified buffer.
     *
     * The checksum is updated using
     * buffer.{@link java.nio.Buffer#remaining() remaining()}
     * bytes starting at
     * buffer.{@link java.nio.Buffer#position() position()}
     * Upon return, the buffer's position will be updated to its
     * limit; its limit will not have been changed.
     *
     * <p>
     *  使用指定缓冲区中的字节更新校验和。
     * 
     *  使用缓冲区更新校验和。
     * {@link java.nio.Buffer#remaining()remaining()} bytes from buffer.{@link java.nio.Buffer#position()position()}
     * 返回后,缓冲区的位置将更新到其限制;其极限不会改变。
     *  使用缓冲区更新校验和。
     * 
     * 
     * @param buffer the ByteBuffer to update the checksum with
     * @since 1.8
     */
    public void update(ByteBuffer buffer) {
        int pos = buffer.position();
        int limit = buffer.limit();
        assert (pos <= limit);
        int rem = limit - pos;
        if (rem <= 0)
            return;
        if (buffer instanceof DirectBuffer) {
            adler = updateByteBuffer(adler, ((DirectBuffer)buffer).address(), pos, rem);
        } else if (buffer.hasArray()) {
            adler = updateBytes(adler, buffer.array(), pos + buffer.arrayOffset(), rem);
        } else {
            byte[] b = new byte[rem];
            buffer.get(b);
            adler = updateBytes(adler, b, 0, b.length);
        }
        buffer.position(limit);
    }

    /**
     * Resets the checksum to initial value.
     * <p>
     */
    public void reset() {
        adler = 1;
    }

    /**
     * Returns the checksum value.
     * <p>
     *  将校验和重置为初始值。
     * 
     */
    public long getValue() {
        return (long)adler & 0xffffffffL;
    }

    private native static int update(int adler, int b);
    private native static int updateBytes(int adler, byte[] b, int off,
                                          int len);
    private native static int updateByteBuffer(int adler, long addr,
                                               int off, int len);
}
