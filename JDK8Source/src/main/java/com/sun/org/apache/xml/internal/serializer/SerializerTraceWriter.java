/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: SerializerTraceWriter.java,v 1.2.4.1 2005/09/15 08:15:25 suresh_emailid Exp $
 * <p>
 *  $ Id：SerializerTraceWriter.java,v 1.2.4.1 2005/09/15 08:15:25 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * This class wraps the real writer, it only purpose is to send
 * CHARACTERTOSTREAM events to the trace listener.
 * Each method immediately sends the call to the wrapped writer unchanged, but
 * in addition it collects characters to be issued to a trace listener.
 *
 * In this way the trace
 * listener knows what characters have been written to the output Writer.
 *
 * There may still be differences in what the trace events say is going to the
 * output writer and what is really going there. These differences will be due
 * to the fact that this class is UTF-8 encoding before emiting the trace event
 * and the underlying writer may not be UTF-8 encoding. There may also be
 * encoding differences.  So the main pupose of this class is to provide a
 * resonable facsimile of the true output.
 *
 * @xsl.usage internal
 * <p>
 *  这个类包装了真正的写程序,它的目的只是将CHARACTERTOSTREAM事件发送到跟踪侦听器。每个方法立即将调用发送到未更改的包装写入程序,但此外,它收集要发送到跟踪侦听器的字符。
 * 
 *  这样,跟踪监听器知道已经写入输出写入器的字符。
 * 
 * 跟踪事件对输出作者的描述以及实际发生的事情可能仍然存在差异。这些差异将是由于这个类在发出跟踪事件之前是UTF-8编码的,并且底层写入器可能不是UTF-8编码。也可能存在编码差异。
 * 因此,这一类的主要目的是提供真正输出的可谐振的传真。
 * 
 *  @ xsl.usage internal
 * 
 */
final class SerializerTraceWriter extends Writer implements WriterChain
{

    /** The real writer to immediately write to.
     * This reference may be null, in which case nothing is written out, but
     * only the trace events are fired for output.
     * <p>
     *  这个引用可以是null,在这种情况下不写出任何内容,但只有跟踪事件被触发输出。
     * 
     */
    private final java.io.Writer m_writer;

    /** The tracer to send events to */
    private final SerializerTrace m_tracer;

    /** The size of the internal buffer, just to keep too many
     * events from being sent to the tracer
     * <p>
     *  事件被发送到跟踪器
     * 
     */
    private int buf_length;

    /**
     * Internal buffer to collect the characters to go to the trace listener.
     *
     * <p>
     *  内部缓冲区用于收集要转到跟踪侦听器的字符。
     * 
     */
    private byte buf[];

    /**
     * How many bytes have been collected and still need to go to trace
     * listener.
     * <p>
     *  已收集了多少字节,但仍需要转到跟踪侦听器。
     * 
     */
    private int count;

    /**
     * Creates or replaces the internal buffer, and makes sure it has a few
     * extra bytes slight overflow of the last UTF8 encoded character.
     * <p>
     *  创建或替换内部缓冲区,并确保它有一些额外的字节轻微溢出的最后一个UTF8编码字符。
     * 
     * 
     * @param size
     */
    private void setBufferSize(int size)
    {
        buf = new byte[size + 3];
        buf_length = size;
        count = 0;
    }

    /**
     * Constructor.
     * If the writer passed in is null, then this SerializerTraceWriter will
     * only signal trace events of what would have been written to that writer.
     * If the writer passed in is not null then the trace events will mirror
     * what is going to that writer. In this way tools, such as a debugger, can
     * gather information on what is being written out.
     *
     * <p>
     *  构造函数。如果传入的写入程序为null,那么这个SerializerTraceWriter只会发出跟踪事件的信息。如果传入的写入器不为空,那么跟踪事件将镜像该写入器将发生什么。
     * 这样,诸如调试器的工具可以收集关于正在写出的内容的信息。
     * 
     * 
     * @param out the Writer to write to (possibly null)
     * @param tracer the tracer to inform that characters are being written
     */
    public SerializerTraceWriter(Writer out, SerializerTrace tracer)
    {
        m_writer = out;
        m_tracer = tracer;
        setBufferSize(1024);
    }

    /**
     * Flush out the collected characters by sending them to the trace
     * listener.  These characters are never written to the real writer
     * (m_writer) because that has already happened with every method
     * call. This method simple informs the listener of what has already
     * happened.
     * <p>
     *  通过将收集的字符发送到跟踪侦听器来清除收集的字符。这些字符从不写入真正的写入器(m_writer),因为这已经发生在每个方法调用。这个方法简单告诉听者已经发生了什么。
     * 
     * 
     * @throws IOException
     */
    private void flushBuffer() throws IOException
    {

        // Just for tracing purposes
        if (count > 0)
        {
            char[] chars = new char[count];
            for(int i=0; i<count; i++)
                chars[i] = (char) buf[i];

            if (m_tracer != null)
                m_tracer.fireGenerateEvent(
                    SerializerTrace.EVENTTYPE_OUTPUT_CHARACTERS,
                    chars,
                    0,
                    chars.length);

            count = 0;
        }
    }

    /**
     * Flush the internal buffer and flush the Writer
     * <p>
     * 刷新内部缓冲区并刷新写入机
     * 
     * 
     * @see java.io.Writer#flush()
     */
    public void flush() throws java.io.IOException
    {
        // send to the real writer
        if (m_writer != null)
            m_writer.flush();

        // from here on just for tracing purposes
        flushBuffer();
    }

    /**
     * Flush the internal buffer and close the Writer
     * <p>
     *  清空内部缓冲区并关闭Writer
     * 
     * 
     * @see java.io.Writer#close()
     */
    public void close() throws java.io.IOException
    {
        // send to the real writer
        if (m_writer != null)
            m_writer.close();

        // from here on just for tracing purposes
        flushBuffer();
    }


    /**
     * Write a single character.  The character to be written is contained in
     * the 16 low-order bits of the given integer value; the 16 high-order bits
     * are ignored.
     *
     * <p> Subclasses that intend to support efficient single-character output
     * should override this method.
     *
     * <p>
     *  写一个单个字符。要写入的字符包含在给定整数值的16个低位中;则忽略16个高阶位。
     * 
     *  <p>要支持高效单字符输出的子类应该覆盖此方法。
     * 
     * 
     * @param c  int specifying a character to be written.
     * @exception  IOException  If an I/O error occurs
     */
    public void write(final int c) throws IOException
    {
        // send to the real writer
        if (m_writer != null)
            m_writer.write(c);

        // ---------- from here on just collect for tracing purposes

        /* If we are close to the end of the buffer then flush it.
         * Remember the buffer can hold a few more characters than buf_length
         * <p>
         *  记住,缓冲区可以容纳比buf_length多几个字符
         * 
         */
        if (count >= buf_length)
            flushBuffer();

        if (c < 0x80)
        {
            buf[count++] = (byte) (c);
        }
        else if (c < 0x800)
        {
            buf[count++] = (byte) (0xc0 + (c >> 6));
            buf[count++] = (byte) (0x80 + (c & 0x3f));
        }
        else
        {
            buf[count++] = (byte) (0xe0 + (c >> 12));
            buf[count++] = (byte) (0x80 + ((c >> 6) & 0x3f));
            buf[count++] = (byte) (0x80 + (c & 0x3f));
        }
    }

    /**
     * Write a portion of an array of characters.
     *
     * <p>
     *  写一个字符数组的一部分。
     * 
     * 
     * @param  chars  Array of characters
     * @param  start   Offset from which to start writing characters
     * @param  length   Number of characters to write
     *
     * @exception  IOException  If an I/O error occurs
     *
     * @throws java.io.IOException
     */
    public void write(final char chars[], final int start, final int length)
        throws java.io.IOException
    {
        // send to the real writer
        if (m_writer != null)
            m_writer.write(chars, start, length);

        // from here on just collect for tracing purposes
        int lengthx3 = (length << 1) + length;

        if (lengthx3 >= buf_length)
        {

            /* If the request length exceeds the size of the output buffer,
              * flush the output buffer and make the buffer bigger to handle.
              * <p>
              *  刷新输出缓冲区并使缓冲区更大以处理。
              * 
              */

            flushBuffer();
            setBufferSize(2 * lengthx3);

        }

        if (lengthx3 > buf_length - count)
        {
            flushBuffer();
        }

        final int n = length + start;
        for (int i = start; i < n; i++)
        {
            final char c = chars[i];

            if (c < 0x80)
                buf[count++] = (byte) (c);
            else if (c < 0x800)
            {
                buf[count++] = (byte) (0xc0 + (c >> 6));
                buf[count++] = (byte) (0x80 + (c & 0x3f));
            }
            else
            {
                buf[count++] = (byte) (0xe0 + (c >> 12));
                buf[count++] = (byte) (0x80 + ((c >> 6) & 0x3f));
                buf[count++] = (byte) (0x80 + (c & 0x3f));
            }
        }

    }

    /**
     * Write a string.
     *
     * <p>
     *  写一个字符串。
     * 
     * 
     * @param  s  String to be written
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void write(final String s) throws IOException
    {
        // send to the real writer
        if (m_writer != null)
            m_writer.write(s);

        // from here on just collect for tracing purposes
        final int length = s.length();

        // We multiply the length by three since this is the maximum length
        // of the characters that we can put into the buffer.  It is possible
        // for each Unicode character to expand to three bytes.

        int lengthx3 = (length << 1) + length;

        if (lengthx3 >= buf_length)
        {

            /* If the request length exceeds the size of the output buffer,
              * flush the output buffer and make the buffer bigger to handle.
              * <p>
              *  刷新输出缓冲区并使缓冲区更大以处理。
              * 
              */

            flushBuffer();
            setBufferSize(2 * lengthx3);
        }

        if (lengthx3 > buf_length - count)
        {
            flushBuffer();
        }

        for (int i = 0; i < length; i++)
        {
            final char c = s.charAt(i);

            if (c < 0x80)
                buf[count++] = (byte) (c);
            else if (c < 0x800)
            {
                buf[count++] = (byte) (0xc0 + (c >> 6));
                buf[count++] = (byte) (0x80 + (c & 0x3f));
            }
            else
            {
                buf[count++] = (byte) (0xe0 + (c >> 12));
                buf[count++] = (byte) (0x80 + ((c >> 6) & 0x3f));
                buf[count++] = (byte) (0x80 + (c & 0x3f));
            }
        }
    }

    /**
     * Get the writer that this one directly wraps.
     * <p>
     *  得到这个人直接包裹的作家。
     * 
     */
    public Writer getWriter()
    {
        return m_writer;
    }

    /**
     * Get the OutputStream that is the at the end of the
     * chain of writers.
     * <p>
     *  获取在作者链的末尾的OutputStream。
     */
    public OutputStream getOutputStream()
    {
        OutputStream retval = null;
        if (m_writer instanceof WriterChain)
            retval = ((WriterChain) m_writer).getOutputStream();
        return retval;
    }
}
