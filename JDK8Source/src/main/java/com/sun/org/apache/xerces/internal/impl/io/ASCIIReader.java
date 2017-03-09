/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.io;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Locale;
import com.sun.org.apache.xerces.internal.util.MessageFormatter;
import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;

import com.sun.xml.internal.stream.util.BufferAllocator;
import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;

/**
 * A simple ASCII byte reader. This is an optimized reader for reading
 * byte streams that only contain 7-bit ASCII characters.
 *
 * @xerces.internal
 *
 * <p>
 *  一个简单的ASCII字节读取器。这是一个优化的读取器,用于读取只包含7位ASCII字符的字节流。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public class ASCIIReader
    extends Reader {

    //
    // Constants
    //

    /** Default byte buffer size (2048). */
    public static final int DEFAULT_BUFFER_SIZE = 2048;

    //
    // Data
    //

    /** Input stream. */
    protected InputStream fInputStream;

    /** Byte buffer. */
    protected byte[] fBuffer;

    // message formatter; used to produce localized
    // exception messages
    private MessageFormatter fFormatter = null;

    //Locale to use for messages
    private Locale fLocale = null;

    //
    // Constructors
    //

    /**
     * Constructs an ASCII reader from the specified input stream
     * using the default buffer size.
     *
     * <p>
     *  使用默认缓冲区大小从指定的输入流构造ASCII读取器。
     * 
     * 
     * @param inputStream The input stream.
     * @param messageFormatter  the MessageFormatter to use to message reporting.
     * @param locale    the Locale for which messages are to be reported
     */
    public ASCIIReader(InputStream inputStream, MessageFormatter messageFormatter,
            Locale locale) {
        this(inputStream, DEFAULT_BUFFER_SIZE, messageFormatter, locale);
    } // <init>(InputStream, MessageFormatter, Locale)

    /**
     * Constructs an ASCII reader from the specified input stream
     * and buffer size.
     *
     * <p>
     *  从指定的输入流和缓冲区大小构造ASCII读取器。
     * 
     * 
     * @param inputStream The input stream.
     * @param size        The initial buffer size.
     * @param messageFormatter  the MessageFormatter to use to message reporting.
     * @param locale    the Locale for which messages are to be reported
     */
    public ASCIIReader(InputStream inputStream, int size,
            MessageFormatter messageFormatter, Locale locale) {
        fInputStream = inputStream;
        BufferAllocator ba = ThreadLocalBufferAllocator.getBufferAllocator();
        fBuffer = ba.getByteBuffer(size);
        if (fBuffer == null) {
            fBuffer = new byte[size];
        }
        fFormatter = messageFormatter;
        fLocale = locale;
    } // <init>(InputStream,int, MessageFormatter, Locale)

    //
    // Reader methods
    //

    /**
     * Read a single character.  This method will block until a character is
     * available, an I/O error occurs, or the end of the stream is reached.
     *
     * <p> Subclasses that intend to support efficient single-character input
     * should override this method.
     *
     * <p>
     *  读取单个字符。此方法将阻塞,直到一个字符可用,I / O错误发生或达到流的结束。
     * 
     *  <p>打算支持高效单字符输入的子类应该覆盖此方法。
     * 
     * 
     * @return     The character read, as an integer in the range 0 to 127
     *             (<tt>0x00-0x7f</tt>), or -1 if the end of the stream has
     *             been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read() throws IOException {
        int b0 = fInputStream.read();
        if (b0 >= 0x80) {
            throw new MalformedByteSequenceException(fFormatter,
                fLocale, XMLMessageFormatter.XML_DOMAIN,
                "InvalidASCII", new Object [] {Integer.toString(b0)});
        }
        return b0;
    } // read():int

    /**
     * Read characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * <p>
     *  将字符读入数组的一部分。此方法将阻塞,直到某些输入可用,发生I / O错误或达到流的结束。
     * 
     * 
     * @param      ch     Destination buffer
     * @param      offset Offset at which to start storing characters
     * @param      length Maximum number of characters to read
     *
     * @return     The number of characters read, or -1 if the end of the
     *             stream has been reached
     *
     * @exception  IOException  If an I/O error occurs
     */
    public int read(char ch[], int offset, int length) throws IOException {
        if (length > fBuffer.length) {
            length = fBuffer.length;
        }
        int count = fInputStream.read(fBuffer, 0, length);
        for (int i = 0; i < count; i++) {
            int b0 = fBuffer[i];
            if (b0 < 0) {
                throw new MalformedByteSequenceException(fFormatter,
                    fLocale, XMLMessageFormatter.XML_DOMAIN,
                    "InvalidASCII", new Object [] {Integer.toString(b0 & 0x0FF)});
            }
            ch[offset + i] = (char)b0;
        }
        return count;
    } // read(char[],int,int)

    /**
     * Skip characters.  This method will block until some characters are
     * available, an I/O error occurs, or the end of the stream is reached.
     *
     * <p>
     * 跳过字符。此方法将阻塞,直到一些字符可用,I / O错误发生或到达流的结束。
     * 
     * 
     * @param  n  The number of characters to skip
     *
     * @return    The number of characters actually skipped
     *
     * @exception  IOException  If an I/O error occurs
     */
    public long skip(long n) throws IOException {
        return fInputStream.skip(n);
    } // skip(long):long

    /**
     * Tell whether this stream is ready to be read.
     *
     * <p>
     *  告诉这个流是否准备好被读取。
     * 
     * 
     * @return True if the next read() is guaranteed not to block for input,
     * false otherwise.  Note that returning false does not guarantee that the
     * next read will block.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public boolean ready() throws IOException {
            return false;
    } // ready()

    /**
     * Tell whether this stream supports the mark() operation.
     * <p>
     *  告诉这个流是否支持mark()操作。
     * 
     */
    public boolean markSupported() {
            return fInputStream.markSupported();
    } // markSupported()

    /**
     * Mark the present position in the stream.  Subsequent calls to reset()
     * will attempt to reposition the stream to this point.  Not all
     * character-input streams support the mark() operation.
     *
     * <p>
     *  标记流中的当前位置。后续对reset()的调用将尝试将流重新定位到此点。不是所有的字符输入流都支持mark()操作。
     * 
     * 
     * @param  readAheadLimit  Limit on the number of characters that may be
     *                         read while still preserving the mark.  After
     *                         reading this many characters, attempting to
     *                         reset the stream may fail.
     *
     * @exception  IOException  If the stream does not support mark(),
     *                          or if some other I/O error occurs
     */
    public void mark(int readAheadLimit) throws IOException {
            fInputStream.mark(readAheadLimit);
    } // mark(int)

    /**
     * Reset the stream.  If the stream has been marked, then attempt to
     * reposition it at the mark.  If the stream has not been marked, then
     * attempt to reset it in some way appropriate to the particular stream,
     * for example by repositioning it to its starting point.  Not all
     * character-input streams support the reset() operation, and some support
     * reset() without supporting mark().
     *
     * <p>
     *  重置流。如果流已被标记,则尝试将其重新定位在标记处。如果流没有被标记,则尝试以适合于特定流的某种方式来重置流,例如通过将其重新定位到其起始点。
     * 不是所有的字符输入流都支持reset()操作,有些支持reset()而不支持mark()。
     * 
     * 
     * @exception  IOException  If the stream has not been marked,
     *                          or if the mark has been invalidated,
     *                          or if the stream does not support reset(),
     *                          or if some other I/O error occurs
     */
    public void reset() throws IOException {
        fInputStream.reset();
    } // reset()

    /**
     * Close the stream.  Once a stream has been closed, further read(),
     * ready(), mark(), or reset() invocations will throw an IOException.
     * Closing a previously-closed stream, however, has no effect.
     *
     * <p>
     * 
     * @exception  IOException  If an I/O error occurs
     */
     public void close() throws IOException {
         BufferAllocator ba = ThreadLocalBufferAllocator.getBufferAllocator();
         ba.returnByteBuffer(fBuffer);
         fBuffer = null;
         fInputStream.close();
     } // close()

} // class ASCIIReader
