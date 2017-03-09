/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.io.FilterOutputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * An output stream that also maintains a checksum of the data being
 * written. The checksum can then be used to verify the integrity of
 * the output data.
 *
 * <p>
 *  也保持正在写入的数据的校验和的输出流。然后可以使用校验和来验证输出数据的完整性。
 * 
 * 
 * @see         Checksum
 * @author      David Connelly
 */
public
class CheckedOutputStream extends FilterOutputStream {
    private Checksum cksum;

    /**
     * Creates an output stream with the specified Checksum.
     * <p>
     *  创建具有指定校验和的输出流。
     * 
     * 
     * @param out the output stream
     * @param cksum the checksum
     */
    public CheckedOutputStream(OutputStream out, Checksum cksum) {
        super(out);
        this.cksum = cksum;
    }

    /**
     * Writes a byte. Will block until the byte is actually written.
     * <p>
     *  写入一个字节。将阻塞,直到字节实际写入。
     * 
     * 
     * @param b the byte to be written
     * @exception IOException if an I/O error has occurred
     */
    public void write(int b) throws IOException {
        out.write(b);
        cksum.update(b);
    }

    /**
     * Writes an array of bytes. Will block until the bytes are
     * actually written.
     * <p>
     *  写入字节数组。将阻塞直到字节实际写入。
     * 
     * 
     * @param b the data to be written
     * @param off the start offset of the data
     * @param len the number of bytes to be written
     * @exception IOException if an I/O error has occurred
     */
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        cksum.update(b, off, len);
    }

    /**
     * Returns the Checksum for this output stream.
     * <p>
     *  返回此输出流的校验和。
     * 
     * @return the Checksum
     */
    public Checksum getChecksum() {
        return cksum;
    }
}
