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
 * $Id: StringVector.java,v 1.2.4.1 2005/09/15 08:15:56 suresh_emailid Exp $
 * <p>
 *  $ Id：StringVector.java,v 1.2.4.1 2005/09/15 08:15:56 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A very simple table that stores a list of strings, optimized
 * for small lists.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的表,存储字符串列表,针对小列表进行了优化。 @ xsl.usage internal
 * 
 */
public class StringVector implements java.io.Serializable
{
    static final long serialVersionUID = 4995234972032919748L;

  /** @serial Size of blocks to allocate           */
  protected int m_blocksize;

  /** @serial Array of strings this contains          */
  protected String m_map[];

  /** @serial Number of strings this contains          */
  protected int m_firstFree = 0;

  /** @serial Size of the array          */
  protected int m_mapSize;

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public StringVector()
  {

    m_blocksize = 8;
    m_mapSize = m_blocksize;
    m_map = new String[m_blocksize];
  }

  /**
   * Construct a StringVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造StringVector。
   * 
   * 
   * @param blocksize Size of the blocks to allocate
   */
  public StringVector(int blocksize)
  {

    m_blocksize = blocksize;
    m_mapSize = blocksize;
    m_map = new String[blocksize];
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Number of strings in the list
   */
  public int getLength()
  {
    return m_firstFree;
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Number of strings in the list
   */
  public final int size()
  {
    return m_firstFree;
  }

  /**
   * Append a string onto the vector.
   *
   * <p>
   *  将一个字符串附加到向量。
   * 
   * 
   * @param value Sting to add to the vector
   */
  public final void addElement(String value)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      String newMap[] = new String[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Get the nth element.
   *
   * <p>
   *  获取第n个元素。
   * 
   * 
   * @param i Index of string to find
   *
   * @return String at given index
   */
  public final String elementAt(int i)
  {
    return m_map[i];
  }

  /**
   * Tell if the table contains the given string.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。
   * 
   * 
   * @param s String to look for
   *
   * @return True if the string is in this table
   */
  public final boolean contains(String s)
  {

    if (null == s)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equals(s))
        return true;
    }

    return false;
  }

  /**
   * Tell if the table contains the given string. Ignore case.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。忽略大小写。
   * 
   * 
   * @param s String to find
   *
   * @return True if the String is in this vector
   */
  public final boolean containsIgnoreCase(String s)
  {

    if (null == s)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equalsIgnoreCase(s))
        return true;
    }

    return false;
  }

  /**
   * Tell if the table contains the given string.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。
   * 
   * 
   * @param s String to push into the vector
   */
  public final void push(String s)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      String newMap[] = new String[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = s;

    m_firstFree++;
  }

  /**
   * Pop the tail of this vector.
   *
   * <p>
   *  弹出这个向量的尾部。
   * 
   * 
   * @return The String last added to this vector or null not found.
   * The string is removed from the vector.
   */
  public final String pop()
  {

    if (m_firstFree <= 0)
      return null;

    m_firstFree--;

    String s = m_map[m_firstFree];

    m_map[m_firstFree] = null;

    return s;
  }

  /**
   * Get the string at the tail of this vector without popping.
   *
   * <p>
   *  获取这个向量的尾部的字符串,不会弹出。
   * 
   * @return The string at the tail of this vector.
   */
  public final String peek()
  {
    return (m_firstFree <= 0) ? null : m_map[m_firstFree - 1];
  }
}
