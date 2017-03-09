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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Stack;
import javax.imageio.IIOException;

/**
 * An abstract class implementing the <code>ImageInputStream</code> interface.
 * This class is designed to reduce the number of methods that must
 * be implemented by subclasses.
 *
 * <p> In particular, this class handles most or all of the details of
 * byte order interpretation, buffering, mark/reset, discarding,
 * closing, and disposing.
 * <p>
 *  一个实现<code> ImageInputStream </code>接口的抽象类。这个类被设计为减少必须由子类实现的方法的数量。
 * 
 *  <p>特别地,此类处理字节顺序解释,缓冲,标记/重置,丢弃,关闭和处理的大部分或全部细节。
 * 
 */
public abstract class ImageInputStreamImpl implements ImageInputStream {

    private Stack markByteStack = new Stack();

    private Stack markBitStack = new Stack();

    private boolean isClosed = false;

    // Length of the buffer used for readFully(type[], int, int)
    private static final int BYTE_BUF_LENGTH = 8192;

    /**
     * Byte buffer used for readFully(type[], int, int).  Note that this
     * array is also used for bulk reads in readShort(), readInt(), etc, so
     * it should be large enough to hold a primitive value (i.e. >= 8 bytes).
     * Also note that this array is package protected, so that it can be
     * used by ImageOutputStreamImpl in a similar manner.
     * <p>
     *  用于readFully的字节缓冲区(type [],int,int)。
     * 请注意,此数组也用于readShort(),readInt()等中的批量读取,因此它应该足够大以容纳一个原始值(即> = 8个字节)。
     * 还要注意,这个数组是受包保护的,所以它可以由ImageOutputStreamImpl以类似的方式使用。
     * 
     */
    byte[] byteBuf = new byte[BYTE_BUF_LENGTH];

    /**
     * The byte order of the stream as an instance of the enumeration
     * class <code>java.nio.ByteOrder</code>, where
     * <code>ByteOrder.BIG_ENDIAN</code> indicates network byte order
     * and <code>ByteOrder.LITTLE_ENDIAN</code> indicates the reverse
     * order.  By default, the value is
     * <code>ByteOrder.BIG_ENDIAN</code>.
     * <p>
     *  作为枚举类实例的流的字节顺序<code> java.nio.ByteOrder </code>,其中<code> ByteOrder.BIG_ENDIAN </code>表示网络字节顺序和<code>
     *  ByteOrder.LITTLE_ENDIAN <代码>表示逆序。
     * 默认情况下,值为<code> ByteOrder.BIG_ENDIAN </code>。
     * 
     */
    protected ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

    /**
     * The current read position within the stream.  Subclasses are
     * responsible for keeping this value current from any method they
     * override that alters the read position.
     * <p>
     *  流中的当前读取位置。子类负责保持这个值从它们覆盖的任何方法改变为读取位置。
     * 
     */
    protected long streamPos;

    /**
     * The current bit offset within the stream.  Subclasses are
     * responsible for keeping this value current from any method they
     * override that alters the bit offset.
     * <p>
     *  流中的当前位偏移。子类负责将此值保持为来自它们覆盖的任何方法的当前值,以改变位偏移。
     * 
     */
    protected int bitOffset;

    /**
     * The position prior to which data may be discarded.  Seeking
     * to a smaller position is not allowed.  <code>flushedPos</code>
     * will always be {@literal >= 0}.
     * <p>
     * 可以丢弃数据之前的位置。不允许寻求较小的职位。 <code> flushedPos </code>将始终为{@literal> = 0}。
     * 
     */
    protected long flushedPos = 0;

    /**
     * Constructs an <code>ImageInputStreamImpl</code>.
     * <p>
     *  构造一个<code> ImageInputStreamImpl </code>。
     * 
     */
    public ImageInputStreamImpl() {
    }

    /**
     * Throws an <code>IOException</code> if the stream has been closed.
     * Subclasses may call this method from any of their methods that
     * require the stream not to be closed.
     *
     * <p>
     *  如果流已关闭,则抛出<code> IOException </code>。子类可以从需要不关闭流的任何方法调用此方法。
     * 
     * 
     * @exception IOException if the stream is closed.
     */
    protected final void checkClosed() throws IOException {
        if (isClosed) {
            throw new IOException("closed");
        }
    }

    public void setByteOrder(ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    /**
     * Reads a single byte from the stream and returns it as an
     * <code>int</code> between 0 and 255.  If EOF is reached,
     * <code>-1</code> is returned.
     *
     * <p> Subclasses must provide an implementation for this method.
     * The subclass implementation should update the stream position
     * before exiting.
     *
     * <p> The bit offset within the stream must be reset to zero before
     * the read occurs.
     *
     * <p>
     *  从流中读取一个字节,并以0到255之间的<code> int </code>返回它。如果到达EOF,则返回<code> -1 </code>。
     * 
     *  <p>子类必须提供此方法的实现。子类实现应在退出之前更新流位置。
     * 
     *  <p>在读取发生之前,流中的位偏移必须重置为零。
     * 
     * 
     * @return the value of the next byte in the stream, or <code>-1</code>
     * if EOF is reached.
     *
     * @exception IOException if the stream has been closed.
     */
    public abstract int read() throws IOException;

    /**
     * A convenience method that calls <code>read(b, 0, b.length)</code>.
     *
     * <p> The bit offset within the stream is reset to zero before
     * the read occurs.
     *
     * <p>
     *  一个方便的方法,调用<code> read(b,0,b.length)</code>。
     * 
     *  <p>在读取发生之前,流内的位偏移重置为零。
     * 
     * 
     * @return the number of bytes actually read, or <code>-1</code>
     * to indicate EOF.
     *
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    /**
     * Reads up to <code>len</code> bytes from the stream, and stores
     * them into <code>b</code> starting at index <code>off</code>.
     * If no bytes can be read because the end of the stream has been
     * reached, <code>-1</code> is returned.
     *
     * <p> The bit offset within the stream must be reset to zero before
     * the read occurs.
     *
     * <p> Subclasses must provide an implementation for this method.
     * The subclass implementation should update the stream position
     * before exiting.
     *
     * <p>
     *  从流中读取<code> len </code>个字节,并将它们从索引<code> off </code>开始存储到<code> b </code>中。
     * 如果没有字节可以读取,因为到达流的结尾,返回<code> -1 </code>。
     * 
     *  <p>在读取发生之前,流中的位偏移必须重置为零。
     * 
     *  <p>子类必须提供此方法的实现。子类实现应在退出之前更新流位置。
     * 
     * 
     * @param b an array of bytes to be written to.
     * @param off the starting position within <code>b</code> to write to.
     * @param len the maximum number of bytes to read.
     *
     * @return the number of bytes actually read, or <code>-1</code>
     * to indicate EOF.
     *
     * @exception IndexOutOfBoundsException if <code>off</code> is
     * negative, <code>len</code> is negative, or <code>off +
     * len</code> is greater than <code>b.length</code>.
     * @exception NullPointerException if <code>b</code> is
     * <code>null</code>.
     * @exception IOException if an I/O error occurs.
     */
    public abstract int read(byte[] b, int off, int len) throws IOException;

    public void readBytes(IIOByteBuffer buf, int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException("len < 0!");
        }
        if (buf == null) {
            throw new NullPointerException("buf == null!");
        }

        byte[] data = new byte[len];
        len = read(data, 0, len);

        buf.setData(data);
        buf.setOffset(0);
        buf.setLength(len);
    }

    public boolean readBoolean() throws IOException {
        int ch = this.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (ch != 0);
    }

    public byte readByte() throws IOException {
        int ch = this.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return (byte)ch;
    }

    public int readUnsignedByte() throws IOException {
        int ch = this.read();
        if (ch < 0) {
            throw new EOFException();
        }
        return ch;
    }

    public short readShort() throws IOException {
        if (read(byteBuf, 0, 2) < 0) {
            throw new EOFException();
        }

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return (short)
                (((byteBuf[0] & 0xff) << 8) | ((byteBuf[1] & 0xff) << 0));
        } else {
            return (short)
                (((byteBuf[1] & 0xff) << 8) | ((byteBuf[0] & 0xff) << 0));
        }
    }

    public int readUnsignedShort() throws IOException {
        return ((int)readShort()) & 0xffff;
    }

    public char readChar() throws IOException {
        return (char)readShort();
    }

    public int readInt() throws IOException {
        if (read(byteBuf, 0, 4) < 0) {
            throw new EOFException();
        }

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return
                (((byteBuf[0] & 0xff) << 24) | ((byteBuf[1] & 0xff) << 16) |
                 ((byteBuf[2] & 0xff) <<  8) | ((byteBuf[3] & 0xff) <<  0));
        } else {
            return
                (((byteBuf[3] & 0xff) << 24) | ((byteBuf[2] & 0xff) << 16) |
                 ((byteBuf[1] & 0xff) <<  8) | ((byteBuf[0] & 0xff) <<  0));
        }
    }

    public long readUnsignedInt() throws IOException {
        return ((long)readInt()) & 0xffffffffL;
    }

    public long readLong() throws IOException {
        // REMIND: Once 6277756 is fixed, we should do a bulk read of all 8
        // bytes here as we do in readShort() and readInt() for even better
        // performance (see 6347575 for details).
        int i1 = readInt();
        int i2 = readInt();

        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return ((long)i1 << 32) + (i2 & 0xFFFFFFFFL);
        } else {
            return ((long)i2 << 32) + (i1 & 0xFFFFFFFFL);
        }
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public String readLine() throws IOException {
        StringBuffer input = new StringBuffer();
        int c = -1;
        boolean eol = false;

        while (!eol) {
            switch (c = read()) {
            case -1:
            case '\n':
                eol = true;
                break;
            case '\r':
                eol = true;
                long cur = getStreamPosition();
                if ((read()) != '\n') {
                    seek(cur);
                }
                break;
            default:
                input.append((char)c);
                break;
            }
        }

        if ((c == -1) && (input.length() == 0)) {
            return null;
        }
        return input.toString();
    }

    public String readUTF() throws IOException {
        this.bitOffset = 0;

        // Fix 4494369: method ImageInputStreamImpl.readUTF()
        // does not work as specified (it should always assume
        // network byte order).
        ByteOrder oldByteOrder = getByteOrder();
        setByteOrder(ByteOrder.BIG_ENDIAN);

        String ret;
        try {
            ret = DataInputStream.readUTF(this);
        } catch (IOException e) {
            // Restore the old byte order even if an exception occurs
            setByteOrder(oldByteOrder);
            throw e;
        }

        setByteOrder(oldByteOrder);
        return ret;
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > b.length!");
        }

        while (len > 0) {
            int nbytes = read(b, off, len);
            if (nbytes == -1) {
                throw new EOFException();
            }
            off += nbytes;
            len -= nbytes;
        }
    }

    public void readFully(byte[] b) throws IOException {
        readFully(b, 0, b.length);
    }

    public void readFully(short[] s, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > s.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > s.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/2);
            readFully(byteBuf, 0, nelts*2);
            toShorts(byteBuf, s, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    public void readFully(char[] c, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > c.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > c.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/2);
            readFully(byteBuf, 0, nelts*2);
            toChars(byteBuf, c, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    public void readFully(int[] i, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > i.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > i.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/4);
            readFully(byteBuf, 0, nelts*4);
            toInts(byteBuf, i, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    public void readFully(long[] l, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > l.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > l.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/8);
            readFully(byteBuf, 0, nelts*8);
            toLongs(byteBuf, l, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    public void readFully(float[] f, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > f.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > f.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/4);
            readFully(byteBuf, 0, nelts*4);
            toFloats(byteBuf, f, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    public void readFully(double[] d, int off, int len) throws IOException {
        // Fix 4430357 - if off + len < 0, overflow occurred
        if (off < 0 || len < 0 || off + len > d.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > d.length!");
        }

        while (len > 0) {
            int nelts = Math.min(len, byteBuf.length/8);
            readFully(byteBuf, 0, nelts*8);
            toDoubles(byteBuf, d, off, nelts);
            off += nelts;
            len -= nelts;
        }
    }

    private void toShorts(byte[] b, short[] s, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                s[off + j] = (short)((b0 << 8) | b1);
                boff += 2;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 1];
                int b1 = b[boff] & 0xff;
                s[off + j] = (short)((b0 << 8) | b1);
                boff += 2;
            }
        }
    }

    private void toChars(byte[] b, char[] c, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                c[off + j] = (char)((b0 << 8) | b1);
                boff += 2;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 1];
                int b1 = b[boff] & 0xff;
                c[off + j] = (char)((b0 << 8) | b1);
                boff += 2;
            }
        }
    }

    private void toInts(byte[] b, int[] i, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                int b2 = b[boff + 2] & 0xff;
                int b3 = b[boff + 3] & 0xff;
                i[off + j] = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                boff += 4;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 3];
                int b1 = b[boff + 2] & 0xff;
                int b2 = b[boff + 1] & 0xff;
                int b3 = b[boff] & 0xff;
                i[off + j] = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                boff += 4;
            }
        }
    }

    private void toLongs(byte[] b, long[] l, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                int b2 = b[boff + 2] & 0xff;
                int b3 = b[boff + 3] & 0xff;
                int b4 = b[boff + 4];
                int b5 = b[boff + 5] & 0xff;
                int b6 = b[boff + 6] & 0xff;
                int b7 = b[boff + 7] & 0xff;

                int i0 = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                int i1 = (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;

                l[off + j] = ((long)i0 << 32) | (i1 & 0xffffffffL);
                boff += 8;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 7];
                int b1 = b[boff + 6] & 0xff;
                int b2 = b[boff + 5] & 0xff;
                int b3 = b[boff + 4] & 0xff;
                int b4 = b[boff + 3];
                int b5 = b[boff + 2] & 0xff;
                int b6 = b[boff + 1] & 0xff;
                int b7 = b[boff]     & 0xff;

                int i0 = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                int i1 = (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;

                l[off + j] = ((long)i0 << 32) | (i1 & 0xffffffffL);
                boff += 8;
            }
        }
    }

    private void toFloats(byte[] b, float[] f, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                int b2 = b[boff + 2] & 0xff;
                int b3 = b[boff + 3] & 0xff;
                int i = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                f[off + j] = Float.intBitsToFloat(i);
                boff += 4;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 3];
                int b1 = b[boff + 2] & 0xff;
                int b2 = b[boff + 1] & 0xff;
                int b3 = b[boff + 0] & 0xff;
                int i = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                f[off + j] = Float.intBitsToFloat(i);
                boff += 4;
            }
        }
    }

    private void toDoubles(byte[] b, double[] d, int off, int len) {
        int boff = 0;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff];
                int b1 = b[boff + 1] & 0xff;
                int b2 = b[boff + 2] & 0xff;
                int b3 = b[boff + 3] & 0xff;
                int b4 = b[boff + 4];
                int b5 = b[boff + 5] & 0xff;
                int b6 = b[boff + 6] & 0xff;
                int b7 = b[boff + 7] & 0xff;

                int i0 = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                int i1 = (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
                long l = ((long)i0 << 32) | (i1 & 0xffffffffL);

                d[off + j] = Double.longBitsToDouble(l);
                boff += 8;
            }
        } else {
            for (int j = 0; j < len; j++) {
                int b0 = b[boff + 7];
                int b1 = b[boff + 6] & 0xff;
                int b2 = b[boff + 5] & 0xff;
                int b3 = b[boff + 4] & 0xff;
                int b4 = b[boff + 3];
                int b5 = b[boff + 2] & 0xff;
                int b6 = b[boff + 1] & 0xff;
                int b7 = b[boff] & 0xff;

                int i0 = (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
                int i1 = (b4 << 24) | (b5 << 16) | (b6 << 8) | b7;
                long l = ((long)i0 << 32) | (i1 & 0xffffffffL);

                d[off + j] = Double.longBitsToDouble(l);
                boff += 8;
            }
        }
    }

    public long getStreamPosition() throws IOException {
        checkClosed();
        return streamPos;
    }

    public int getBitOffset() throws IOException {
        checkClosed();
        return bitOffset;
    }

    public void setBitOffset(int bitOffset) throws IOException {
        checkClosed();
        if (bitOffset < 0 || bitOffset > 7) {
            throw new IllegalArgumentException("bitOffset must be betwwen 0 and 7!");
        }
        this.bitOffset = bitOffset;
    }

    public int readBit() throws IOException {
        checkClosed();

        // Compute final bit offset before we call read() and seek()
        int newBitOffset = (this.bitOffset + 1) & 0x7;

        int val = read();
        if (val == -1) {
            throw new EOFException();
        }

        if (newBitOffset != 0) {
            // Move byte position back if in the middle of a byte
            seek(getStreamPosition() - 1);
            // Shift the bit to be read to the rightmost position
            val >>= 8 - newBitOffset;
        }
        this.bitOffset = newBitOffset;

        return val & 0x1;
    }

    public long readBits(int numBits) throws IOException {
        checkClosed();

        if (numBits < 0 || numBits > 64) {
            throw new IllegalArgumentException();
        }
        if (numBits == 0) {
            return 0L;
        }

        // Have to read additional bits on the left equal to the bit offset
        int bitsToRead = numBits + bitOffset;

        // Compute final bit offset before we call read() and seek()
        int newBitOffset = (this.bitOffset + numBits) & 0x7;

        // Read a byte at a time, accumulate
        long accum = 0L;
        while (bitsToRead > 0) {
            int val = read();
            if (val == -1) {
                throw new EOFException();
            }

            accum <<= 8;
            accum |= val;
            bitsToRead -= 8;
        }

        // Move byte position back if in the middle of a byte
        if (newBitOffset != 0) {
            seek(getStreamPosition() - 1);
        }
        this.bitOffset = newBitOffset;

        // Shift away unwanted bits on the right.
        accum >>>= (-bitsToRead); // Negative of bitsToRead == extra bits read

        // Mask out unwanted bits on the left
        accum &= (-1L >>> (64 - numBits));

        return accum;
    }

    /**
     * Returns <code>-1L</code> to indicate that the stream has unknown
     * length.  Subclasses must override this method to provide actual
     * length information.
     *
     * <p>
     *  返回<code> -1L </code>以指示流的长度未知。子类必须覆盖此方法以提供实际的长度信息。
     * 
     * 
     * @return -1L to indicate unknown length.
     */
    public long length() {
        return -1L;
    }

    /**
     * Advances the current stream position by calling
     * <code>seek(getStreamPosition() + n)</code>.
     *
     * <p> The bit offset is reset to zero.
     *
     * <p>
     * 通过调用<code> seek(getStreamPosition()+ n)</code>提高当前流的位置。
     * 
     *  <p>位偏移重置为零。
     * 
     * 
     * @param n the number of bytes to seek forward.
     *
     * @return an <code>int</code> representing the number of bytes
     * skipped.
     *
     * @exception IOException if <code>getStreamPosition</code>
     * throws an <code>IOException</code> when computing either
     * the starting or ending position.
     */
    public int skipBytes(int n) throws IOException {
        long pos = getStreamPosition();
        seek(pos + n);
        return (int)(getStreamPosition() - pos);
    }

    /**
     * Advances the current stream position by calling
     * <code>seek(getStreamPosition() + n)</code>.
     *
     * <p> The bit offset is reset to zero.
     *
     * <p>
     *  通过调用<code> seek(getStreamPosition()+ n)</code>提高当前流的位置。
     * 
     *  <p>位偏移重置为零。
     * 
     * 
     * @param n the number of bytes to seek forward.
     *
     * @return a <code>long</code> representing the number of bytes
     * skipped.
     *
     * @exception IOException if <code>getStreamPosition</code>
     * throws an <code>IOException</code> when computing either
     * the starting or ending position.
     */
    public long skipBytes(long n) throws IOException {
        long pos = getStreamPosition();
        seek(pos + n);
        return getStreamPosition() - pos;
    }

    public void seek(long pos) throws IOException {
        checkClosed();

        // This test also covers pos < 0
        if (pos < flushedPos) {
            throw new IndexOutOfBoundsException("pos < flushedPos!");
        }

        this.streamPos = pos;
        this.bitOffset = 0;
    }

    /**
     * Pushes the current stream position onto a stack of marked
     * positions.
     * <p>
     *  将当前流位置推到一堆标记位置。
     * 
     */
    public void mark() {
        try {
            markByteStack.push(Long.valueOf(getStreamPosition()));
            markBitStack.push(Integer.valueOf(getBitOffset()));
        } catch (IOException e) {
        }
    }

    /**
     * Resets the current stream byte and bit positions from the stack
     * of marked positions.
     *
     * <p> An <code>IOException</code> will be thrown if the previous
     * marked position lies in the discarded portion of the stream.
     *
     * <p>
     *  从标记位置的堆栈重置当前流字节和位位置。
     * 
     *  <p>如果先前标记的位置在流的丢弃部分中,则将抛出<code> IOException </code>。
     * 
     * 
     * @exception IOException if an I/O error occurs.
     */
    public void reset() throws IOException {
        if (markByteStack.empty()) {
            return;
        }

        long pos = ((Long)markByteStack.pop()).longValue();
        if (pos < flushedPos) {
            throw new IIOException
                ("Previous marked position has been discarded!");
        }
        seek(pos);

        int offset = ((Integer)markBitStack.pop()).intValue();
        setBitOffset(offset);
    }

    public void flushBefore(long pos) throws IOException {
        checkClosed();
        if (pos < flushedPos) {
            throw new IndexOutOfBoundsException("pos < flushedPos!");
        }
        if (pos > getStreamPosition()) {
            throw new IndexOutOfBoundsException("pos > getStreamPosition()!");
        }
        // Invariant: flushedPos >= 0
        flushedPos = pos;
    }

    public void flush() throws IOException {
        flushBefore(getStreamPosition());
    }

    public long getFlushedPosition() {
        return flushedPos;
    }

    /**
     * Default implementation returns false.  Subclasses should
     * override this if they cache data.
     * <p>
     *  默认实现返回false。如果子类缓存数据,子类应该覆盖此。
     * 
     */
    public boolean isCached() {
        return false;
    }

    /**
     * Default implementation returns false.  Subclasses should
     * override this if they cache data in main memory.
     * <p>
     *  默认实现返回false。如果子类在主内存中缓存数据,子类应该覆盖此。
     * 
     */
    public boolean isCachedMemory() {
        return false;
    }

    /**
     * Default implementation returns false.  Subclasses should
     * override this if they cache data in a temporary file.
     * <p>
     *  默认实现返回false。如果子类在临时文件中缓存数据,子类应该覆盖此。
     * 
     */
    public boolean isCachedFile() {
        return false;
    }

    public void close() throws IOException {
        checkClosed();

        isClosed = true;
    }

    /**
     * Finalizes this object prior to garbage collection.  The
     * <code>close</code> method is called to close any open input
     * source.  This method should not be called from application
     * code.
     *
     * <p>
     *  在垃圾收集之前完成此对象。调用<code> close </code>方法以关闭任何打开的输入源。不应该从应用程序代码调用此方法。
     * 
     * @exception Throwable if an error occurs during superclass
     * finalization.
     */
    protected void finalize() throws Throwable {
        if (!isClosed) {
            try {
                close();
            } catch (IOException e) {
            }
        }
        super.finalize();
    }
}
