/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil;

import java.io.StringWriter;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Writes each input byte as a 2 byte hexidecimal output pair making it
 * possible to turn arbitrary binary data into an ASCII format.
 * The high 4 bits of the byte is translated into the first byte.
 *
 * <p>
 *  将每个输入字节写为2字节十六进制输出对,使得可以将任意二进制数据转换为ASCII格式。字节的高4位转换为第一个字节。
 * 
 * 
 * @author      Jeff Nisewanger
 */
public class HexOutputStream extends OutputStream
{
    static private final char hex[] = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private StringWriter writer;

    /**
     * Creates a new HexOutputStream.
     * <p>
     *  创建一个新的HexOutputStream。
     * 
     * 
     * @param w The underlying StringWriter.
     */
    public
        HexOutputStream(StringWriter w) {
        writer = w;
    }


    /**
     * Writes a byte. Will block until the byte is actually
     * written.
     * param b The byte to write out.
     * <p>
     *  写入一个字节。将阻塞,直到字节实际写入。 param b要写出的字节。
     * 
     * @exception java.io.IOException I/O error occurred.
     */
    public synchronized void write(int b) throws IOException {
        writer.write(hex[((b >> 4) & 0xF)]);
        writer.write(hex[((b >> 0) & 0xF)]);
    }

    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public synchronized void write(byte[] b, int off, int len)
        throws IOException
    {
        for(int i=0; i < len; i++) {
            write(b[off + i]);
        }
    }
}
