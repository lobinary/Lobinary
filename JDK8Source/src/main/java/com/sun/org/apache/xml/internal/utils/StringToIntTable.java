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
 * $Id: StringToIntTable.java,v 1.2.4.1 2005/09/15 08:15:55 suresh_emailid Exp $
 * <p>
 *  $ Id：StringToIntTable.java,v 1.2.4.1 2005/09/15 08:15:55 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A very simple lookup table that stores a list of strings, the even
 * number strings being keys, and the odd number strings being values.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的查找表,用于存储字符串列表,偶数字符串是键,奇数字符串是值。 @ xsl.usage internal
 * 
 */
public class StringToIntTable
{

  public static final int INVALID_KEY = -10000;

  /** Block size to allocate          */
  private int m_blocksize;

  /** Array of strings this table points to. Associated with ints
  /* <p>
  /* 
   * in m_values         */
  private String m_map[];

  /** Array of ints this table points. Associated with strings from
  /* <p>
  /* 
   * m_map.         */
  private int m_values[];

  /** Number of ints in the table          */
  private int m_firstFree = 0;

  /** Size of this table         */
  private int m_mapSize;

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public StringToIntTable()
  {

    m_blocksize = 8;
    m_mapSize = m_blocksize;
    m_map = new String[m_blocksize];
    m_values = new int[m_blocksize];
  }

  /**
   * Construct a StringToIntTable, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造StringToIntTable。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public StringToIntTable(int blocksize)
  {

    m_blocksize = blocksize;
    m_mapSize = blocksize;
    m_map = new String[blocksize];
    m_values = new int[m_blocksize];
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return the length of the list
   */
  public final int getLength()
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
   * @param key String to append
   * @param value The int value of the string
   */
  public final void put(String key, int value)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      String newMap[] = new String[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;

      int newValues[] = new int[m_mapSize];

      System.arraycopy(m_values, 0, newValues, 0, m_firstFree + 1);

      m_values = newValues;
    }

    m_map[m_firstFree] = key;
    m_values[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Tell if the table contains the given string.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。
   * 
   * 
   * @param key String to look for
   *
   * @return The String's int value
   *
   */
  public final int get(String key)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equals(key))
        return m_values[i];
    }

        return INVALID_KEY;
  }

  /**
   * Tell if the table contains the given string. Ignore case.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。忽略大小写。
   * 
   * 
   * @param key String to look for
   *
   * @return The string's int value
   */
  public final int getIgnoreCase(String key)
  {

    if (null == key)
        return INVALID_KEY;

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equalsIgnoreCase(key))
        return m_values[i];
    }

    return INVALID_KEY;
  }

  /**
   * Tell if the table contains the given string.
   *
   * <p>
   *  告诉表格是否包含给定的字符串。
   * 
   * 
   * @param key String to look for
   *
   * @return True if the string is in the table
   */
  public final boolean contains(String key)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equals(key))
        return true;
    }

    return false;
  }

  /**
   * Return array of keys in the table.
   *
   * <p>
   *  返回表中的键的数组。
   * 
   * @return Array of strings
   */
  public final String[] keys()
  {
    String [] keysArr = new String[m_firstFree];

    for (int i = 0; i < m_firstFree; i++)
    {
      keysArr[i] = m_map[i];
    }

    return keysArr;
  }
}
