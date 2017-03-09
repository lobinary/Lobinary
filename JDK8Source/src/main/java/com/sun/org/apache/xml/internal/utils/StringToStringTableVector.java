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
 * $Id: StringToStringTableVector.java,v 1.2.4.1 2005/09/15 08:15:56 suresh_emailid Exp $
 * <p>
 *  $ Id：StringToStringTableVector.java,v 1.2.4.1 2005/09/15 08:15:56 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A very simple table that stores a list of StringToStringTables, optimized
 * for small lists.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的表,存储StringToStringTables的列表,针对小列表进行了优化。 @ xsl.usage internal
 * 
 */
public class StringToStringTableVector
{

  /** Size of blocks to allocate         */
  private int m_blocksize;

  /** Array of StringToStringTable objects          */
  private StringToStringTable m_map[];

  /** Number of StringToStringTable objects in this array          */
  private int m_firstFree = 0;

  /** Size of this array          */
  private int m_mapSize;

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public StringToStringTableVector()
  {

    m_blocksize = 8;
    m_mapSize = m_blocksize;
    m_map = new StringToStringTable[m_blocksize];
  }

  /**
   * Construct a StringToStringTableVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造StringToStringTableVector。
   * 
   * 
   * @param blocksize Size of blocks to allocate
   */
  public StringToStringTableVector(int blocksize)
  {

    m_blocksize = blocksize;
    m_mapSize = blocksize;
    m_map = new StringToStringTable[blocksize];
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Number of StringToStringTable objects in the list
   */
  public final int getLength()
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
   * @return Number of StringToStringTable objects in the list
   */
  public final int size()
  {
    return m_firstFree;
  }

  /**
   * Append a StringToStringTable object onto the vector.
   *
   * <p>
   *  将一个StringToStringTable对象附加到向量。
   * 
   * 
   * @param value StringToStringTable object to add
   */
  public final void addElement(StringToStringTable value)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      StringToStringTable newMap[] = new StringToStringTable[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Given a string, find the last added occurance value
   * that matches the key.
   *
   * <p>
   *  给定一个字符串,找到与键相匹配的最后一次出现的值。
   * 
   * 
   * @param key String to look up
   *
   * @return the last added occurance value that matches the key
   * or null if not found.
   */
  public final String get(String key)
  {

    for (int i = m_firstFree - 1; i >= 0; --i)
    {
      String nsuri = m_map[i].get(key);

      if (nsuri != null)
        return nsuri;
    }

    return null;
  }

  /**
   * Given a string, find out if there is a value in this table
   * that matches the key.
   *
   * <p>
   *  给定一个字符串,找出这个表中是否有一个与该键匹配的值。
   * 
   * 
   * @param key String to look for
   *
   * @return True if the string was found in table, null if not
   */
  public final boolean containsKey(String key)
  {

    for (int i = m_firstFree - 1; i >= 0; --i)
    {
      if (m_map[i].get(key) != null)
        return true;
    }

    return false;
  }

  /**
   * Remove the last element.
   * <p>
   *  删除最后一个元素。
   * 
   */
  public final void removeLastElem()
  {

    if (m_firstFree > 0)
    {
      m_map[m_firstFree] = null;

      m_firstFree--;
    }
  }

  /**
   * Get the nth element.
   *
   * <p>
   *  获取第n个元素。
   * 
   * 
   * @param i Index of element to find
   *
   * @return The StringToStringTable object at the given index
   */
  public final StringToStringTable elementAt(int i)
  {
    return m_map[i];
  }

  /**
   * Tell if the table contains the given StringToStringTable.
   *
   * <p>
   *  告诉表格是否包含给定的StringToStringTable。
   * 
   * @param s The StringToStringTable to find
   *
   * @return True if the StringToStringTable is found
   */
  public final boolean contains(StringToStringTable s)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i].equals(s))
        return true;
    }

    return false;
  }
}
