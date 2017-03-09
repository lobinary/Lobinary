/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.imageio.plugins.jpeg;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.IIOException;

import java.io.IOException;

/**
 * A class wrapping a buffer and its state.  For efficiency,
 * the members are made visible to other classes in this package.
 * <p>
 *  一个类包装一个缓冲区及其状态。为了效率,成员对此包中的其他类可见。
 * 
 */
class JPEGBuffer {

    private boolean debug = false;

    /**
     * The size of the buffer.  This is large enough to hold all
     * known marker segments (other than thumbnails and icc profiles)
     * <p>
     *  缓冲区的大小。这足够大以容纳所有已知的标记段(缩略图和icc配置文件除外)
     * 
     */
    final int BUFFER_SIZE = 4096;

    /**
     * The actual buffer.
     * <p>
     *  实际缓冲区。
     * 
     */
    byte [] buf;

    /**
     * The number of bytes available for reading from the buffer.
     * Anytime data is read from the buffer, this should be updated.
     * <p>
     *  可用于从缓冲区读取的字节数。从缓冲区读取任何时间的数据,应该更新。
     * 
     */
    int bufAvail;

    /**
     * A pointer to the next available byte in the buffer.  This is
     * used to read data from the buffer and must be updated to
     * move through the buffer.
     * <p>
     *  指向缓冲区中下一个可用字节的指针。这用于从缓冲区读取数据,必须更新以通过缓冲区。
     * 
     */
    int bufPtr;

    /**
     * The ImageInputStream buffered.
     * <p>
     *  缓冲的ImageInputStream。
     * 
     */
    ImageInputStream iis;

    JPEGBuffer (ImageInputStream iis) {
        buf = new byte[BUFFER_SIZE];
        bufAvail = 0;
        bufPtr = 0;
        this.iis = iis;
    }

    /**
     * Ensures that there are at least <code>count</code> bytes available
     * in the buffer, loading more data and moving any remaining
     * bytes to the front.  A count of 0 means to just fill the buffer.
     * If the count is larger than the buffer size, just fills the buffer.
     * If the end of the stream is encountered before a non-0 count can
     * be satisfied, an <code>IIOException</code> is thrown with the
     * message "Image Format Error".
     * <p>
     *  确保缓冲区中至少有<code> count </code>个字节可用,加载更多数据并将任何剩余字节移到前面。计数0意味着只填充缓冲区。如果计数大于缓冲区大小,则只填充缓冲区。
     * 如果在满足非0计数之前遇到流的结尾,则在消息"图像格式错误"中引发<code> IIOException </code>。
     * 
     */
    void loadBuf(int count) throws IOException {
        if (debug) {
            System.out.print("loadbuf called with ");
            System.out.print("count " + count + ", ");
            System.out.println("bufAvail " + bufAvail + ", ");
        }
        if (count != 0) {
            if (bufAvail >= count) {  // have enough
                return;
            }
        } else {
            if (bufAvail == BUFFER_SIZE) {  // already full
                return;
            }
        }
        // First copy any remaining bytes down to the beginning
        if ((bufAvail > 0) && (bufAvail < BUFFER_SIZE)) {
            System.arraycopy(buf, bufPtr, buf, 0, bufAvail);
        }
        // Now fill the rest of the buffer
        int ret = iis.read(buf, bufAvail, buf.length - bufAvail);
        if (debug) {
            System.out.println("iis.read returned " + ret);
        }
        if (ret != -1) {
            bufAvail += ret;
        }
        bufPtr = 0;
        int minimum = Math.min(BUFFER_SIZE, count);
        if (bufAvail < minimum) {
            throw new IIOException ("Image Format Error");
        }
    }

    /**
     * Fills the data array from the stream, starting with
     * the buffer and then reading directly from the stream
     * if necessary.  The buffer is left in an appropriate
     * state.  If the end of the stream is encountered, an
     * <code>IIOException</code> is thrown with the
     * message "Image Format Error".
     * <p>
     *  从流中填充数据数组,从缓冲区开始,然后如果需要,直接从流中读取。缓冲器处于适当的状态。
     * 如果遇到流的结尾,则会在消息"Image Format Error"中抛出<code> IIOException </code>。
     * 
     */
    void readData(byte [] data) throws IOException {
        int count = data.length;
        // First see what's left in the buffer.
        if (bufAvail >= count) {  // It's enough
            System.arraycopy(buf, bufPtr, data, 0, count);
            bufAvail -= count;
            bufPtr += count;
            return;
        }
        int offset = 0;
        if (bufAvail > 0) {  // Some there, but not enough
            System.arraycopy(buf, bufPtr, data, 0, bufAvail);
            offset = bufAvail;
            count -= bufAvail;
            bufAvail = 0;
            bufPtr = 0;
        }
        // Now read the rest directly from the stream
        if (iis.read(data, offset, count) != count) {
            throw new IIOException ("Image format Error");
        }
    }

    /**
     * Skips <code>count</code> bytes, leaving the buffer
     * in an appropriate state.  If the end of the stream is
     * encountered, an <code>IIOException</code> is thrown with the
     * message "Image Format Error".
     * <p>
     * 跳过<code> count </code>字节,使缓冲区处于适当的状态。
     * 如果遇到流的结尾,则会在消息"Image Format Error"中抛出<code> IIOException </code>。
     * 
     */
    void skipData(int count) throws IOException {
        // First see what's left in the buffer.
        if (bufAvail >= count) {  // It's enough
            bufAvail -= count;
            bufPtr += count;
            return;
        }
        if (bufAvail > 0) {  // Some there, but not enough
            count -= bufAvail;
            bufAvail = 0;
            bufPtr = 0;
        }
        // Now read the rest directly from the stream
        if (iis.skipBytes(count) != count) {
            throw new IIOException ("Image format Error");
        }
    }

    /**
     * Push back the remaining contents of the buffer by
     * repositioning the input stream.
     * <p>
     *  通过重新定位输入流来推回缓冲区的剩余内容。
     * 
     */
    void pushBack() throws IOException {
        iis.seek(iis.getStreamPosition()-bufAvail);
        bufAvail = 0;
        bufPtr = 0;
    }

    /**
     * Return the stream position corresponding to the next
     * available byte in the buffer.
     * <p>
     *  返回对应于缓冲区中下一个可用字节的流位置。
     * 
     */
    long getStreamPosition() throws IOException {
        return (iis.getStreamPosition()-bufAvail);
    }

    /**
     * Scan the buffer until the next 0xff byte, reloading
     * the buffer as necessary.  The buffer position is left
     * pointing to the first non-0xff byte after a run of
     * 0xff bytes.  If the end of the stream is encountered,
     * an EOI marker is inserted into the buffer and <code>true</code>
     * is returned.  Otherwise returns <code>false</code>.
     * <p>
     *  扫描缓冲区,直到下一个0xff字节,根据需要重新加载缓冲区。在运行0xff字节后,缓冲器位置指向第一个非0xff字节。
     * 如果遇到流的结尾,则将EOI标记插入到缓冲器中,并返回<code> true </code>。否则返回<code> false </code>。
     * 
     */
    boolean scanForFF(JPEGImageReader reader) throws IOException {
        boolean retval = false;
        boolean foundFF = false;
        while (foundFF == false) {
            while (bufAvail > 0) {
                if ((buf[bufPtr++] & 0xff) == 0xff) {
                    bufAvail--;
                    foundFF = true;
                    break;  // out of inner while
                }
                bufAvail--;
            }
            // Reload the buffer and keep going
            loadBuf(0);
            // Skip any remaining pad bytes
            if (foundFF == true) {
                while ((bufAvail > 0) && (buf[bufPtr] & 0xff) == 0xff) {
                    bufPtr++;  // Only if it still is 0xff
                    bufAvail--;
                }
            }
            if (bufAvail == 0) {  // Premature EOF
                // send out a warning, but treat it as EOI
                //reader.warningOccurred(JPEGImageReader.WARNING_NO_EOI);
                retval = true;
                buf[0] = (byte)JPEG.EOI;
                bufAvail = 1;
                bufPtr = 0;
                foundFF = true;
            }
        }
        return retval;
    }

    /**
     * Prints the contents of the buffer, in hex.
     * <p>
     * 
     * @param count the number of bytes to print,
     * starting at the current available byte.
     */
    void print(int count) {
        System.out.print("buffer has ");
        System.out.print(bufAvail);
        System.out.println(" bytes available");
        if (bufAvail < count) {
            count = bufAvail;
        }
        for (int ptr = bufPtr; count > 0; count--) {
            int val = (int)buf[ptr++] & 0xff;
            System.out.print(" " + Integer.toHexString(val));
        }
        System.out.println();
    }

}
