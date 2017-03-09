/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: WriterToASCI.java,v 1.2.4.1 2005/09/15 08:15:31 suresh_emailid Exp $
 * <p>
 *  $ Id：WriterToASCI.java,v 1.2.4.1 2005/09/15 08:15:31 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;



/**
 * This class writes ASCII to a byte stream as quickly as possible.  For the
 * moment it does not do buffering, though I reserve the right to do some
 * buffering down the line if I can prove that it will be faster even if the
 * output stream is buffered.
 *
 * This class is only used internally within Xalan.
 *
 * @xsl.usage internal
 * <p>
 *  该类将ASCII尽快写入字节流。目前它不做缓冲,虽然我保留权利做一些缓冲下来,如果我可以证明它将更快,即使输出流缓冲。
 * 
 *  此类仅在Xalan内部使用。
 * 
 *  @ xsl.usage internal
 * 
 */
class WriterToASCI extends Writer implements WriterChain
{

  /** The byte stream to write to.  */
  private final OutputStream m_os;

  /**
   * Create an unbuffered ASCII writer.
   *
   *
   * <p>
   *  创建无缓冲的ASCII编写器。
   * 
   * 
   * @param os The byte stream to write to.
   */
  public WriterToASCI(OutputStream os)
  {
    m_os = os;
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
  public void write(char chars[], int start, int length)
          throws java.io.IOException
  {

    int n = length+start;

    for (int i = start; i < n; i++)
    {
      m_os.write(chars[i]);
    }
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
  public void write(int c) throws IOException
  {
    m_os.write(c);
  }

  /**
   * Write a string.
   *
   * <p>
   *  写一个字符串。
   * 
   * 
   * @param  s String to be written
   *
   * @exception  IOException  If an I/O error occurs
   */
  public void write(String s) throws IOException
  {
    int n = s.length();
    for (int i = 0; i < n; i++)
    {
      m_os.write(s.charAt(i));
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
   * 刷新流。如果流已经从缓冲区中的各种write()方法保存了任何字符,请立即将它们写入其预期目的地。然后,如果该目标是另一个字符或字节流,请清除它。
   * 因此,一个flush()调用将刷写Writer和OutputStreams链中的所有缓冲区。
   * 
   * 
   * @exception  IOException  If an I/O error occurs
   */
  public void flush() throws java.io.IOException
  {
    m_os.flush();
  }

  /**
   * Close the stream, flushing it first.  Once a stream has been closed,
   * further write() or flush() invocations will cause an IOException to be
   * thrown.  Closing a previously-closed stream, however, has no effect.
   *
   * <p>
   *  关闭流,先冲洗它。一旦流被关闭,进一步的write()或flush()调用将导致抛出IOException。然而,关闭先前关闭的流没有效果。
   * 
   * 
   * @exception  IOException  If an I/O error occurs
   */
  public void close() throws java.io.IOException
  {
    m_os.close();
  }

  /**
   * Get the output stream where the events will be serialized to.
   *
   * <p>
   *  获取事件将序列化到的输出流。
   * 
   * 
   * @return reference to the result stream, or null of only a writer was
   * set.
   */
  public OutputStream getOutputStream()
  {
    return m_os;
  }

  /**
   * Get the writer that this writer directly chains to.
   * <p>
   *  得到这个作家直接链接的作家。
   */
  public Writer getWriter()
  {
      return null;
  }
}
