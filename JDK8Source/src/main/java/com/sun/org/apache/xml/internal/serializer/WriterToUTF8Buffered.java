/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2005 The Apache Software Foundation.
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
 *  版权所有1999-2005 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: WriterToUTF8Buffered.java,v 1.2.4.1 2005/09/15 08:15:31 suresh_emailid Exp $
 * <p>
 *  $ Id：WriterToUTF8Bufferedjava,v 1241 2005/09/15 08:15:31 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;


/**
 * This class writes unicode characters to a byte stream (java.io.OutputStream)
 * as quickly as possible. It buffers the output in an internal
 * buffer which must be flushed to the OutputStream when done. This flushing
 * is done via the close() flush() or flushBuffer() method.
 *
 * This class is only used internally within Xalan.
 *
 * @xsl.usage internal
 * <p>
 * 这个类尽可能快地将unicode字符写入字节流(javaioOutputStream)它缓冲在内部缓冲区中的输出,当完成时必须刷新到OutputStream此刷新是通过close()flush()或fl
 * ushBuffer()方法。
 * 
 *  此类仅在Xalan内部使用
 * 
 *  @xslusage内部
 * 
 */
final class WriterToUTF8Buffered extends Writer implements WriterChain
{

  /** number of bytes that the byte buffer can hold.
   * This is a fixed constant is used rather than m_outputBytes.lenght for performance.
   * <p>
   *  这是一个固定常数而不是m_outputByteslenght的性能
   * 
   */
  private static final int BYTES_MAX=16*1024;
  /** number of characters that the character buffer can hold.
   * This is 1/3 of the number of bytes because UTF-8 encoding
   * can expand one unicode character by up to 3 bytes.
   * <p>
   *  这是字节数的1/3,因为UTF-8编码可以将一个unicode字符扩展最多3个字节
   * 
   */
  private static final int CHARS_MAX=(BYTES_MAX/3);

 // private static final int

  /** The byte stream to write to. (sc & sb remove final to compile in JDK 1.1.8) */
  private final OutputStream m_os;

  /**
   * The internal buffer where data is stored.
   * (sc & sb remove final to compile in JDK 1.1.8)
   * <p>
   *  存储数据的内部缓冲区(sc&sb remove JDK 118中的最终编译)
   * 
   */
  private final byte m_outputBytes[];

  private final char m_inputChars[];

  /**
   * The number of valid bytes in the buffer. This value is always
   * in the range <tt>0</tt> through <tt>m_outputBytes.length</tt>; elements
   * <tt>m_outputBytes[0]</tt> through <tt>m_outputBytes[count-1]</tt> contain valid
   * byte data.
   * <p>
   * 缓冲区中的有效字节数此值始终在<tt> 0 </tt>到<tt> m_outputByteslength </tt>范围内;元素<tt> m_outputBytes [0] </tt>到<tt> m_o
   * utputBytes [count-1] </tt>包含有效字节数据。
   * 
   */
  private int count;

  /**
   * Create an buffered UTF-8 writer.
   *
   *
   * <p>
   *  创建缓冲的UTF-8 writer
   * 
   * 
   * @param   out    the underlying output stream.
   *
   * @throws UnsupportedEncodingException
   */
  public WriterToUTF8Buffered(OutputStream out)
          throws UnsupportedEncodingException
  {
      m_os = out;
      // get 3 extra bytes to make buffer overflow checking simpler and faster
      // we won't have to keep checking for a few extra characters
      m_outputBytes = new byte[BYTES_MAX + 3];

      // Big enough to hold the input chars that will be transformed
      // into output bytes in m_ouputBytes.
      m_inputChars = new char[CHARS_MAX + 2];
      count = 0;

//      the old body of this constructor, before the buffersize was changed to a constant
//      this(out, 8*1024);
  }

  /**
   * Create an buffered UTF-8 writer to write data to the
   * specified underlying output stream with the specified buffer
   * size.
   *
   * <p>
   *  创建一个缓冲的UTF-8写入程序,用于将数据写入指定缓冲区大小的指定基础输出流
   * 
   * 
   * @param   out    the underlying output stream.
   * @param   size   the buffer size.
   * @exception IllegalArgumentException if size <= 0.
   */
//  public WriterToUTF8Buffered(final OutputStream out, final int size)
//  {
//
//    m_os = out;
//
//    if (size <= 0)
//    {
//      throw new IllegalArgumentException(
//        SerializerMessages.createMessage(SerializerErrorResources.ER_BUFFER_SIZE_LESSTHAN_ZERO, null)); //"Buffer size <= 0");
//    }
//
//    m_outputBytes = new byte[size];
//    count = 0;
//  }

  /**
   * Write a single character.  The character to be written is contained in
   * the 16 low-order bits of the given integer value; the 16 high-order bits
   * are ignored.
   *
   * <p> Subclasses that intend to support efficient single-character output
   * should override this method.
   *
   * <p>
   *  写入单个字符要写入的字符包含在给定整数值的16个低位中;则忽略16个高阶位
   * 
   *  <p>要支持高效单字符输出的子类应该覆盖此方法
   * 
   * 
   * @param c  int specifying a character to be written.
   * @exception  IOException  If an I/O error occurs
   */
  public void write(final int c) throws IOException
  {

    /* If we are close to the end of the buffer then flush it.
     * Remember the buffer can hold a few more bytes than BYTES_MAX
     * <p>
     *  记住,缓冲区可以容纳比BYTES_MAX多几个字节
     * 
     */
    if (count >= BYTES_MAX)
        flushBuffer();

    if (c < 0x80)
    {
       m_outputBytes[count++] = (byte) (c);
    }
    else if (c < 0x800)
    {
      m_outputBytes[count++] = (byte) (0xc0 + (c >> 6));
      m_outputBytes[count++] = (byte) (0x80 + (c & 0x3f));
    }
    else if (c < 0x10000)
    {
      m_outputBytes[count++] = (byte) (0xe0 + (c >> 12));
      m_outputBytes[count++] = (byte) (0x80 + ((c >> 6) & 0x3f));
      m_outputBytes[count++] = (byte) (0x80 + (c & 0x3f));
    }
        else
        {
          m_outputBytes[count++] = (byte) (0xf0 + (c >> 18));
          m_outputBytes[count++] = (byte) (0x80 + ((c >> 12) & 0x3f));
          m_outputBytes[count++] = (byte) (0x80 + ((c >> 6) & 0x3f));
          m_outputBytes[count++] = (byte) (0x80 + (c & 0x3f));
        }

  }


  /**
   * Write a portion of an array of characters.
   *
   * <p>
   *  写一个字符数组的一部分
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

    // We multiply the length by three since this is the maximum length
    // of the characters that we can put into the buffer.  It is possible
    // for each Unicode character to expand to three bytes.

    int lengthx3 = 3*length;

    if (lengthx3 >= BYTES_MAX - count)
    {
      // The requested length is greater than the unused part of the buffer
      flushBuffer();

      if (lengthx3 > BYTES_MAX)
      {
        /*
         * The requested length exceeds the size of the buffer.
         * Cut the buffer up into chunks, each of which will
         * not cause an overflow to the output buffer m_outputBytes,
         * and make multiple recursive calls.
         * Be careful about integer overflows in multiplication.
         * <p>
         * 所请求的长度超过缓冲区的大小将缓冲区分成块,每个块不会导致输出缓冲区溢出m_outputBytes,并进行多次递归调用在乘法中小心整数溢出
         * 
         */
        int split = length/CHARS_MAX;
        final int chunks;
        if (length % CHARS_MAX > 0)
            chunks = split + 1;
        else
            chunks = split;
        int end_chunk = start;
        for (int chunk = 1; chunk <= chunks; chunk++)
        {
            int start_chunk = end_chunk;
            end_chunk = start + (int) ((((long) length) * chunk) / chunks);

            // Adjust the end of the chunk if it ends on a high char
            // of a Unicode surrogate pair and low char of the pair
            // is not going to be in the same chunk
            final char c = chars[end_chunk - 1];
            int ic = chars[end_chunk - 1];
            if (c >= 0xD800 && c <= 0xDBFF) {
                // The last Java char that we were going
                // to process is the first of a
                // Java surrogate char pair that
                // represent a Unicode character.

                if (end_chunk < start + length) {
                    // Avoid spanning by including the low
                    // char in the current chunk of chars.
                    end_chunk++;
                } else {
                    /* This is the last char of the last chunk,
                     * and it is the high char of a high/low pair with
                     * no low char provided.
                     * TODO: error message needed.
                     * The char array incorrectly ends in a high char
                     * of a high/low surrogate pair, but there is
                     * no corresponding low as the high is the last char
                     * <p>
                     *  并且它是高/低对没有低char提供的高字符TODO：错误消息需要char数组不正确地结束在高/低代理对的高字符,但没有对应的低,因为高是最后一个字符
                     * 
                     */
                    end_chunk--;
                }
            }


            int len_chunk = (end_chunk - start_chunk);
            this.write(chars,start_chunk, len_chunk);
        }
        return;
      }
    }



    final int n = length+start;
    final byte[] buf_loc = m_outputBytes; // local reference for faster access
    int count_loc = count;      // local integer for faster access
    int i = start;
    {
        /* This block could be omitted and the code would produce
         * the same result. But this block exists to give the JIT
         * a better chance of optimizing a tight and common loop which
         * occurs when writing out ASCII characters.
         * <p>
         *  相同的结果但是这个块存在给JIT更好的机会优化紧凑和公共循环,当写出ASCII字符时发生
         * 
         */
        char c;
        for(; i < n && (c = chars[i])< 0x80 ; i++ )
            buf_loc[count_loc++] = (byte)c;
    }
    for (; i < n; i++)
    {

      final char c = chars[i];

      if (c < 0x80)
        buf_loc[count_loc++] = (byte) (c);
      else if (c < 0x800)
      {
        buf_loc[count_loc++] = (byte) (0xc0 + (c >> 6));
        buf_loc[count_loc++] = (byte) (0x80 + (c & 0x3f));
      }
      /**
        * The following else if condition is added to support XML 1.1 Characters for
        * UTF-8:   [1111 0uuu] [10uu zzzz] [10yy yyyy] [10xx xxxx]*
        * Unicode: [1101 10ww] [wwzz zzyy] (high surrogate)
        *          [1101 11yy] [yyxx xxxx] (low surrogate)
        *          * uuuuu = wwww + 1
        * <p>
        * 添加以下else if条件以支持UTF-8的XML 11字符：[1111 0uuu] [10uu zzzz] [10yy yyyy] [10xx xxxx] * Unicode：[1101 10ww] 
        * [wwzz zzyy](high surrogate)[1101 11yy] [yyxx xxxx](low surrogate)* uuuuu = wwww + 1。
        * 
        */
      else if (c >= 0xD800 && c <= 0xDBFF)
      {
          char high, low;
          high = c;
          i++;
          low = chars[i];

          buf_loc[count_loc++] = (byte) (0xF0 | (((high + 0x40) >> 8) & 0xf0));
          buf_loc[count_loc++] = (byte) (0x80 | (((high + 0x40) >> 2) & 0x3f));
          buf_loc[count_loc++] = (byte) (0x80 | ((low >> 6) & 0x0f) + ((high << 4) & 0x30));
          buf_loc[count_loc++] = (byte) (0x80 | (low & 0x3f));
      }
      else
      {
        buf_loc[count_loc++] = (byte) (0xe0 + (c >> 12));
        buf_loc[count_loc++] = (byte) (0x80 + ((c >> 6) & 0x3f));
        buf_loc[count_loc++] = (byte) (0x80 + (c & 0x3f));
      }
    }
    // Store the local integer back into the instance variable
    count = count_loc;

  }

  /**
   * Write a string.
   *
   * <p>
   *  写一个字符串
   * 
   * 
   * @param  s  String to be written
   *
   * @exception  IOException  If an I/O error occurs
   */
  public void write(final String s) throws IOException
  {

    // We multiply the length by three since this is the maximum length
    // of the characters that we can put into the buffer.  It is possible
    // for each Unicode character to expand to three bytes.
    final int length = s.length();
    int lengthx3 = 3*length;

    if (lengthx3 >= BYTES_MAX - count)
    {
      // The requested length is greater than the unused part of the buffer
      flushBuffer();

      if (lengthx3 > BYTES_MAX)
      {
        /*
         * The requested length exceeds the size of the buffer,
         * so break it up in chunks that don't exceed the buffer size.
         * <p>
         *  请求的长度超过缓冲区的大小,因此将其分成不超过缓冲区大小的块
         * 
         */
         final int start = 0;
         int split = length/CHARS_MAX;
         final int chunks;
         if (length % CHARS_MAX > 0)
             chunks = split + 1;
         else
             chunks = split;
         int end_chunk = 0;
         for (int chunk = 1; chunk <= chunks; chunk++)
         {
             int start_chunk = end_chunk;
             end_chunk = start + (int) ((((long) length) * chunk) / chunks);
             s.getChars(start_chunk,end_chunk, m_inputChars,0);
             int len_chunk = (end_chunk - start_chunk);

             // Adjust the end of the chunk if it ends on a high char
             // of a Unicode surrogate pair and low char of the pair
             // is not going to be in the same chunk
             final char c = m_inputChars[len_chunk - 1];
             if (c >= 0xD800 && c <= 0xDBFF) {
                 // Exclude char in this chunk,
                 // to avoid spanning a Unicode character
                 // that is in two Java chars as a high/low surrogate
                 end_chunk--;
                 len_chunk--;
                 if (chunk == chunks) {
                     /* TODO: error message needed.
                      * The String incorrectly ends in a high char
                      * of a high/low surrogate pair, but there is
                      * no corresponding low as the high is the last char
                      * Recover by ignoring this last char.
                      * <p>
                      *  字符串错误地结束在高/低代理对的高字符,但没有相应的低,因为高是最后的字符恢复通过忽略这最后一个字符
                      * 
                      */
                 }
             }

             this.write(m_inputChars,0, len_chunk);
         }
         return;
      }
    }


    s.getChars(0, length , m_inputChars, 0);
    final char[] chars = m_inputChars;
    final int n = length;
    final byte[] buf_loc = m_outputBytes; // local reference for faster access
    int count_loc = count;      // local integer for faster access
    int i = 0;
    {
        /* This block could be omitted and the code would produce
         * the same result. But this block exists to give the JIT
         * a better chance of optimizing a tight and common loop which
         * occurs when writing out ASCII characters.
         * <p>
         *  相同的结果但是这个块存在给JIT更好的机会优化紧凑和公共循环,当写出ASCII字符时发生
         * 
         */
        char c;
        for(; i < n && (c = chars[i])< 0x80 ; i++ )
            buf_loc[count_loc++] = (byte)c;
    }
    for (; i < n; i++)
    {

      final char c = chars[i];

      if (c < 0x80)
        buf_loc[count_loc++] = (byte) (c);
      else if (c < 0x800)
      {
        buf_loc[count_loc++] = (byte) (0xc0 + (c >> 6));
        buf_loc[count_loc++] = (byte) (0x80 + (c & 0x3f));
      }
    /**
      * The following else if condition is added to support XML 1.1 Characters for
      * UTF-8:   [1111 0uuu] [10uu zzzz] [10yy yyyy] [10xx xxxx]*
      * Unicode: [1101 10ww] [wwzz zzyy] (high surrogate)
      *          [1101 11yy] [yyxx xxxx] (low surrogate)
      *          * uuuuu = wwww + 1
      * <p>
      * 添加以下else if条件以支持UTF-8的XML 11字符：[1111 0uuu] [10uu zzzz] [10yy yyyy] [10xx xxxx] * Unicode：[1101 10ww] 
      * [wwzz zzyy](high surrogate)[1101 11yy] [yyxx xxxx](low surrogate)* uuuuu = wwww + 1。
      * 
      */
    else if (c >= 0xD800 && c <= 0xDBFF)
    {
        char high, low;
        high = c;
        i++;
        low = chars[i];

        buf_loc[count_loc++] = (byte) (0xF0 | (((high + 0x40) >> 8) & 0xf0));
        buf_loc[count_loc++] = (byte) (0x80 | (((high + 0x40) >> 2) & 0x3f));
        buf_loc[count_loc++] = (byte) (0x80 | ((low >> 6) & 0x0f) + ((high << 4) & 0x30));
        buf_loc[count_loc++] = (byte) (0x80 | (low & 0x3f));
    }
      else
      {
        buf_loc[count_loc++] = (byte) (0xe0 + (c >> 12));
        buf_loc[count_loc++] = (byte) (0x80 + ((c >> 6) & 0x3f));
        buf_loc[count_loc++] = (byte) (0x80 + (c & 0x3f));
      }
    }
    // Store the local integer back into the instance variable
    count = count_loc;

  }

  /**
   * Flush the internal buffer
   *
   * <p>
   *  刷新内部缓冲区
   * 
   * 
   * @throws IOException
   */
  public void flushBuffer() throws IOException
  {

    if (count > 0)
    {
      m_os.write(m_outputBytes, 0, count);

      count = 0;
    }
  }

  /**
   * Flush the stream.  If the stream has saved any characters from the
   * various write() methods in a buffer, write them immediately to their
   * intended destination.  Then, if that destination is another character or
   * byte stream, flush it.  Thus one flush() invocation will flush all the
   * buffers in a chain of Writers and OutputStreams.
   *
   * <p>
   *  刷新流如果流已经从缓冲区中的各种write()方法保存了任何字符,则立即将它们写入到其预期目标。
   * 如果该目标是另一个字符或字节流,则刷新它因此,一个flush()调用将刷新在Writer和OutputStreams链中的所有缓冲区。
   * 
   * 
   * @exception  IOException  If an I/O error occurs
   *
   * @throws java.io.IOException
   */
  public void flush() throws java.io.IOException
  {
    flushBuffer();
    m_os.flush();
  }

  /**
   * Close the stream, flushing it first.  Once a stream has been closed,
   * further write() or flush() invocations will cause an IOException to be
   * thrown.  Closing a previously-closed stream, however, has no effect.
   *
   * <p>
   * 关闭流,首先刷新流一旦流被关闭,进一步的write()或flush()调用将导致抛出IOException。然而,关闭先前关闭的流,没有效果
   * 
   * 
   * @exception  IOException  If an I/O error occurs
   *
   * @throws java.io.IOException
   */
  public void close() throws java.io.IOException
  {
    flushBuffer();
    m_os.close();
  }

  /**
   * Get the output stream where the events will be serialized to.
   *
   * <p>
   *  获取事件将序列化到的输出流
   * 
   * @return reference to the result stream, or null of only a writer was
   * set.
   */
  public OutputStream getOutputStream()
  {
    return m_os;
  }

  public Writer getWriter()
  {
    // Only one of getWriter() or getOutputStream() can return null
    // This type of writer wraps an OutputStream, not a Writer.
    return null;
  }
}
