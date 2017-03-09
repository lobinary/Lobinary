/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2004 The Apache Software Foundation.
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
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ObjectVector.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * <p>
 *  $ Id：ObjectVector.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A very simple table that stores a list of objects.
 *
 * This version is based on a "realloc" strategy -- a simle array is
 * used, and when more storage is needed, a larger array is obtained
 * and all existing data is recopied into it. As a result, read/write
 * access to existing nodes is O(1) fast but appending may be O(N**2)
 * slow.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的表,用于存储对象列表。
 * 
 *  此版本基于"realloc"策略 - 使用了一个simle数组,当需要更多存储时,将获取一个更大的数组,并将所有现有数据重新复制到其中。
 * 因此,对现有节点的读/写访问是O(1)快,但是附加可以是O(N ** 2)慢。 @ xsl.usage internal。
 * 
 */
public class ObjectVector implements Cloneable
{

  /** Size of blocks to allocate          */
  protected int m_blocksize;

  /** Array of objects          */
  protected Object m_map[];

  /** Number of ints in array          */
  protected int m_firstFree = 0;

  /** Size of array          */
  protected int m_mapSize;

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public ObjectVector()
  {

    m_blocksize = 32;
    m_mapSize = m_blocksize;
    m_map = new Object[m_blocksize];
  }

  /**
   * Construct a IntVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造IntVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public ObjectVector(int blocksize)
  {

    m_blocksize = blocksize;
    m_mapSize = blocksize;
    m_map = new Object[blocksize];
  }

  /**
   * Construct a IntVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造IntVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public ObjectVector(int blocksize, int increaseSize)
  {

    m_blocksize = increaseSize;
    m_mapSize = blocksize;
    m_map = new Object[blocksize];
  }

  /**
   * Copy constructor for ObjectVector
   *
   * <p>
   *  ObjectVector的复制构造函数
   * 
   * 
   * @param v Existing ObjectVector to copy
   */
  public ObjectVector(ObjectVector v)
  {
        m_map = new Object[v.m_mapSize];
    m_mapSize = v.m_mapSize;
    m_firstFree = v.m_firstFree;
        m_blocksize = v.m_blocksize;
        System.arraycopy(v.m_map, 0, m_map, 0, m_firstFree);
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return length of the list
   */
  public final int size()
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
   * @return length of the list
   */
  public final void setSize(int sz)
  {
    m_firstFree = sz;
  }


  /**
   * Append an object onto the vector.
   *
   * <p>
   *  将对象附加到向量。
   * 
   * 
   * @param value Object to add to the list
   */
  public final void addElement(Object value)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      Object newMap[] = new Object[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Append several Object values onto the vector.
   *
   * <p>
   * 将多个对象值附加到向量上。
   * 
   * 
   * @param value Object to add to the list
   */
  public final void addElements(Object value, int numberOfElements)
  {

    if ((m_firstFree + numberOfElements) >= m_mapSize)
    {
      m_mapSize += (m_blocksize+numberOfElements);

      Object newMap[] = new Object[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    for (int i = 0; i < numberOfElements; i++)
    {
      m_map[m_firstFree] = value;
      m_firstFree++;
    }
  }

  /**
   * Append several slots onto the vector, but do not set the values.
   *
   * <p>
   *  将多个插槽添加到向量上,但不要设置值。
   * 
   * 
   * @param numberOfElements number of slots to append
   */
  public final void addElements(int numberOfElements)
  {

    if ((m_firstFree + numberOfElements) >= m_mapSize)
    {
      m_mapSize += (m_blocksize+numberOfElements);

      Object newMap[] = new Object[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_firstFree += numberOfElements;
  }


  /**
   * Inserts the specified object in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   *
   * <p>
   *  在指定索引处在此向量中插入指定的对象。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   * 
   * @param value Object to insert
   * @param at Index of where to insert
   */
  public final void insertElementAt(Object value, int at)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      Object newMap[] = new Object[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    if (at <= (m_firstFree - 1))
    {
      System.arraycopy(m_map, at, m_map, at + 1, m_firstFree - at);
    }

    m_map[at] = value;

    m_firstFree++;
  }

  /**
   * Remove all elements objects from the list.
   * <p>
   *  从列表中删除所有元素对象。
   * 
   */
  public final void removeAllElements()
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      m_map[i] = null;
    }

    m_firstFree = 0;
  }

  /**
   * Removes the first occurrence of the argument from this vector.
   * If the object is found in this vector, each component in the vector
   * with an index greater or equal to the object's index is shifted
   * downward to have an index one smaller than the value it had
   * previously.
   *
   * <p>
   *  从此向量中删除参数的第一次出现。如果在该向量中找到对象,则具有大于或等于对象的索引的索引的向量中的每个分量向下移位,以使索引1小于其先前的值。
   * 
   * 
   * @param s Object to remove from array
   *
   * @return True if the object was removed, false if it was not found
   */
  public final boolean removeElement(Object s)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i] == s)
      {
        if ((i + 1) < m_firstFree)
          System.arraycopy(m_map, i + 1, m_map, i - 1, m_firstFree - i);
        else
          m_map[i] = null;

        m_firstFree--;

        return true;
      }
    }

    return false;
  }

  /**
   * Deletes the component at the specified index. Each component in
   * this vector with an index greater or equal to the specified
   * index is shifted downward to have an index one smaller than
   * the value it had previously.
   *
   * <p>
   *  删除指定索引处的组件。该向量中具有大于或等于指定索引的索引的每个分量向下移动,以使索引1小于其先前的值。
   * 
   * 
   * @param i index of where to remove an object
   */
  public final void removeElementAt(int i)
  {

    if (i > m_firstFree)
      System.arraycopy(m_map, i + 1, m_map, i, m_firstFree);
    else
      m_map[i] = null;

    m_firstFree--;
  }

  /**
   * Sets the component at the specified index of this vector to be the
   * specified object. The previous component at that position is discarded.
   *
   * The index must be a value greater than or equal to 0 and less
   * than the current size of the vector.
   *
   * <p>
   *  将该向量的指定索引处的组件设置为指定的对象。在该位置的前一个组件被丢弃。
   * 
   *  索引必须是大于或等于0且小于向量当前大小的值。
   * 
   * 
   * @param value object to set
   * @param index Index of where to set the object
   */
  public final void setElementAt(Object value, int index)
  {
    m_map[index] = value;
  }

  /**
   * Get the nth element.
   *
   * <p>
   *  获取第n个元素。
   * 
   * 
   * @param i index of object to get
   *
   * @return object at given index
   */
  public final Object elementAt(int i)
  {
    return m_map[i];
  }

  /**
   * Tell if the table contains the given Object.
   *
   * <p>
   *  告诉表是否包含给定的对象。
   * 
   * 
   * @param s object to look for
   *
   * @return true if the object is in the list
   */
  public final boolean contains(Object s)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i] == s)
        return true;
    }

    return false;
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   *  搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * 
   * @param elem object to look for
   * @param index Index of where to begin search
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public final int indexOf(Object elem, int index)
  {

    for (int i = index; i < m_firstFree; i++)
    {
      if (m_map[i] == elem)
        return i;
    }

    return java.lang.Integer.MIN_VALUE;
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   *  搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * 
   * @param elem object to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public final int indexOf(Object elem)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (m_map[i] == elem)
        return i;
    }

    return java.lang.Integer.MIN_VALUE;
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   * 搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * 
   * @param elem Object to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public final int lastIndexOf(Object elem)
  {

    for (int i = (m_firstFree - 1); i >= 0; i--)
    {
      if (m_map[i] == elem)
        return i;
    }

    return java.lang.Integer.MIN_VALUE;
  }

  /*
   * Reset the array to the supplied size.
   *
   * <p>
   *  将阵列重置为提供的大小。
   * 
   * 
   * @param size
   */
  public final void setToSize(int size) {

    Object newMap[] = new Object[size];

    System.arraycopy(m_map, 0, newMap, 0, m_firstFree);
    m_mapSize = size;

    m_map = newMap;

  }

  /**
   * Returns clone of current ObjectVector
   *
   * <p>
   *  返回当前ObjectVector的克隆
   * 
   * @return clone of current ObjectVector
   */
  public Object clone()
    throws CloneNotSupportedException
  {
        return new ObjectVector(this);
  }
}
