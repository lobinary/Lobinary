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
 * $Id: StringBufferPool.java,v 1.2.4.1 2005/09/15 08:15:54 suresh_emailid Exp $
 * <p>
 *  $ Id：StringBufferPool.java,v 1.2.4.1 2005/09/15 08:15:54 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * This class pools string buffers, since they are reused so often.
 * String buffers are good candidates for pooling, because of
 * their supporting character arrays.
 * @xsl.usage internal
 * <p>
 *  此类汇集字符串缓冲区,因为它们经常被重用。字符串缓冲区是池的好候选,因为它们支持字符数组。 @ xsl.usage internal
 * 
 */
public class StringBufferPool
{

  /** The global pool of string buffers.   */
  private static ObjectPool m_stringBufPool =
    new ObjectPool(com.sun.org.apache.xml.internal.utils.FastStringBuffer.class);

  /**
   * Get the first free instance of a string buffer, or create one
   * if there are no free instances.
   *
   * <p>
   *  获取字符串缓冲区的第一个自由实例,或者如果没有自由实例,则创建一个。
   * 
   * 
   * @return A string buffer ready for use.
   */
  public synchronized static FastStringBuffer get()
  {
    return (FastStringBuffer) m_stringBufPool.getInstance();
  }

  /**
   * Return a string buffer back to the pool.
   *
   * <p>
   *  将字符串缓冲区返回到池。
   * 
   * @param sb Must be a non-null reference to a string buffer.
   */
  public synchronized static void free(FastStringBuffer sb)
  {
    // Since this isn't synchronized, setLength must be
    // done before the instance is freed.
    // Fix attributed to Peter Speck <speck@ruc.dk>.
    sb.setLength(0);
    m_stringBufPool.freeInstance(sb);
  }
}
