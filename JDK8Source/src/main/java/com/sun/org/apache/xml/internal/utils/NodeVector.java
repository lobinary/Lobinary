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
 * $Id: NodeVector.java,v 1.2.4.1 2005/09/15 08:15:50 suresh_emailid Exp $
 * <p>
 *  $ Id：NodeVector.java,v 1.2.4.1 2005/09/15 08:15:50 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.Serializable;

import com.sun.org.apache.xml.internal.dtm.DTM;

/**
 * A very simple table that stores a list of Nodes.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的表,用于存储节点列表。 @ xsl.usage internal
 * 
 */
public class NodeVector implements Serializable, Cloneable
{
    static final long serialVersionUID = -713473092200731870L;

  /**
   * Size of blocks to allocate.
   * <p>
   *  要分配的块的大小。
   * 
   * 
   *  @serial
   */
  private int m_blocksize;

  /**
   * Array of nodes this points to.
   * <p>
   *  这个指向的节点数组。
   * 
   * 
   *  @serial
   */
  private int m_map[];

  /**
   * Number of nodes in this NodeVector.
   * <p>
   *  此NodeVector中的节点数。
   * 
   * 
   *  @serial
   */
  protected int m_firstFree = 0;

  /**
   * Size of the array this points to.
   * <p>
   *  这指向的数组的大小。
   * 
   * 
   *  @serial
   */
  private int m_mapSize;  // lazy initialization

  /**
   * Default constructor.
   * <p>
   *  默认构造函数。
   * 
   */
  public NodeVector()
  {
    m_blocksize = 32;
    m_mapSize = 0;
  }

  /**
   * Construct a NodeVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造NodeVector。
   * 
   * 
   * @param blocksize Size of blocks to allocate
   */
  public NodeVector(int blocksize)
  {
    m_blocksize = blocksize;
    m_mapSize = 0;
  }

  /**
   * Get a cloned LocPathIterator.
   *
   * <p>
   *  获取克隆的LocPathIterator。
   * 
   * 
   * @return A clone of this
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {

    NodeVector clone = (NodeVector) super.clone();

    if ((null != this.m_map) && (this.m_map == clone.m_map))
    {
      clone.m_map = new int[this.m_map.length];

      System.arraycopy(this.m_map, 0, clone.m_map, 0, this.m_map.length);
    }

    return clone;
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Number of nodes in this NodeVector
   */
  public int size()
  {
    return m_firstFree;
  }

  /**
   * Append a Node onto the vector.
   *
   * <p>
   *  将一个节点附加到向量。
   * 
   * 
   * @param value Node to add to the vector
   */
  public void addElement(int value)
  {

    if ((m_firstFree + 1) >= m_mapSize)
    {
      if (null == m_map)
      {
        m_map = new int[m_blocksize];
        m_mapSize = m_blocksize;
      }
      else
      {
        m_mapSize += m_blocksize;

        int newMap[] = new int[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

        m_map = newMap;
      }
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Append a Node onto the vector.
   *
   * <p>
   *  将一个节点附加到向量。
   * 
   * 
   * @param value Node to add to the vector
   */
  public final void push(int value)
  {

    int ff = m_firstFree;

    if ((ff + 1) >= m_mapSize)
    {
      if (null == m_map)
      {
        m_map = new int[m_blocksize];
        m_mapSize = m_blocksize;
      }
      else
      {
        m_mapSize += m_blocksize;

        int newMap[] = new int[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, ff + 1);

        m_map = newMap;
      }
    }

    m_map[ff] = value;

    ff++;

    m_firstFree = ff;
  }

  /**
   * Pop a node from the tail of the vector and return the result.
   *
   * <p>
   *  从向量的尾部弹出一个节点并返回结果。
   * 
   * 
   * @return the node at the tail of the vector
   */
  public final int pop()
  {

    m_firstFree--;

    int n = m_map[m_firstFree];

    m_map[m_firstFree] = DTM.NULL;

    return n;
  }

  /**
   * Pop a node from the tail of the vector and return the
   * top of the stack after the pop.
   *
   * <p>
   *  从向量的尾部弹出一个节点,并在弹出后返回堆栈的顶部。
   * 
   * 
   * @return The top of the stack after it's been popped
   */
  public final int popAndTop()
  {

    m_firstFree--;

    m_map[m_firstFree] = DTM.NULL;

    return (m_firstFree == 0) ? DTM.NULL : m_map[m_firstFree - 1];
  }

  /**
   * Pop a node from the tail of the vector.
   * <p>
   *  从向量的尾部弹出节点。
   * 
   */
  public final void popQuick()
  {

    m_firstFree--;

    m_map[m_firstFree] = DTM.NULL;
  }

  /**
   * Return the node at the top of the stack without popping the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   * 返回堆栈顶部的节点,而不弹出堆栈。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node at the top of the stack or null if stack is empty.
   */
  public final int peepOrNull()
  {
    return ((null != m_map) && (m_firstFree > 0))
           ? m_map[m_firstFree - 1] : DTM.NULL;
  }

  /**
   * Push a pair of nodes into the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将一对节点推入堆栈。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param v1 First node to add to vector
   * @param v2 Second node to add to vector
   */
  public final void pushPair(int v1, int v2)
  {

    if (null == m_map)
    {
      m_map = new int[m_blocksize];
      m_mapSize = m_blocksize;
    }
    else
    {
      if ((m_firstFree + 2) >= m_mapSize)
      {
        m_mapSize += m_blocksize;

        int newMap[] = new int[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, m_firstFree);

        m_map = newMap;
      }
    }

    m_map[m_firstFree] = v1;
    m_map[m_firstFree + 1] = v2;
    m_firstFree += 2;
  }

  /**
   * Pop a pair of nodes from the tail of the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   * <p>
   *  从堆栈的尾部弹出一对节点。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   */
  public final void popPair()
  {

    m_firstFree -= 2;
    m_map[m_firstFree] = DTM.NULL;
    m_map[m_firstFree + 1] = DTM.NULL;
  }

  /**
   * Set the tail of the stack to the given node.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将堆栈的尾部设置为给定节点。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param n Node to set at the tail of vector
   */
  public final void setTail(int n)
  {
    m_map[m_firstFree - 1] = n;
  }

  /**
   * Set the given node one position from the tail.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将给定节点从尾部设置一个位置。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param n Node to set
   */
  public final void setTailSub1(int n)
  {
    m_map[m_firstFree - 2] = n;
  }

  /**
   * Return the node at the tail of the vector without popping
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  返回向量的尾部节点而不弹出TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node at the tail of the vector
   */
  public final int peepTail()
  {
    return m_map[m_firstFree - 1];
  }

  /**
   * Return the node one position from the tail without popping.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  从尾部返回节点一个位置而不弹出。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node one away from the tail
   */
  public final int peepTailSub1()
  {
    return m_map[m_firstFree - 2];
  }

  /**
   * Insert a node in order in the list.
   *
   * <p>
   *  在列表中按顺序插入节点。
   * 
   * 
   * @param value Node to insert
   */
  public void insertInOrder(int value)
  {

    for (int i = 0; i < m_firstFree; i++)
    {
      if (value < m_map[i])
      {
        insertElementAt(value, i);

        return;
      }
    }

    addElement(value);
  }

  /**
   * Inserts the specified node in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   *
   * <p>
   *  在指定的索引处将指定的节点插入到此向量中。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   * 
   * @param value Node to insert
   * @param at Position where to insert
   */
  public void insertElementAt(int value, int at)
  {

    if (null == m_map)
    {
      m_map = new int[m_blocksize];
      m_mapSize = m_blocksize;
    }
    else if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      int newMap[] = new int[m_mapSize];

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
   * Append the nodes to the list.
   *
   * <p>
   *  将节点附加到列表。
   * 
   * 
   * @param nodes NodeVector to append to this list
   */
  public void appendNodes(NodeVector nodes)
  {

    int nNodes = nodes.size();

    if (null == m_map)
    {
      m_mapSize = nNodes + m_blocksize;
      m_map = new int[m_mapSize];
    }
    else if ((m_firstFree + nNodes) >= m_mapSize)
    {
      m_mapSize += (nNodes + m_blocksize);

      int newMap[] = new int[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + nNodes);

      m_map = newMap;
    }

    System.arraycopy(nodes.m_map, 0, m_map, m_firstFree, nNodes);

    m_firstFree += nNodes;
  }

  /**
   * Inserts the specified node in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   * <p>
   * 在指定的索引处将指定的节点插入到此向量中。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   */
  public void removeAllElements()
  {

    if (null == m_map)
      return;

    for (int i = 0; i < m_firstFree; i++)
    {
      m_map[i] = DTM.NULL;
    }

    m_firstFree = 0;
  }

  /**
   * Set the length to zero, but don't clear the array.
   * <p>
   *  将长度设置为零,但不清除数组。
   * 
   */
  public void RemoveAllNoClear()
  {

    if (null == m_map)
      return;

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
   * @param s Node to remove from the list
   *
   * @return True if the node was successfully removed
   */
  public boolean removeElement(int s)
  {

    if (null == m_map)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      int node = m_map[i];

      if (node == s)
      {
        if (i > m_firstFree)
          System.arraycopy(m_map, i + 1, m_map, i - 1, m_firstFree - i);
        else
          m_map[i] = DTM.NULL;

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
   * @param i Index of node to remove
   */
  public void removeElementAt(int i)
  {

    if (null == m_map)
      return;

    if (i > m_firstFree)
      System.arraycopy(m_map, i + 1, m_map, i - 1, m_firstFree - i);
    else
      m_map[i] = DTM.NULL;
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
   * @param node Node to set
   * @param index Index of where to set the node
   */
  public void setElementAt(int node, int index)
  {

    if (null == m_map)
    {
      m_map = new int[m_blocksize];
      m_mapSize = m_blocksize;
    }

    if(index == -1)
        addElement(node);

    m_map[index] = node;
  }

  /**
   * Get the nth element.
   *
   * <p>
   *  获取第n个元素。
   * 
   * 
   * @param i Index of node to get
   *
   * @return Node at specified index
   */
  public int elementAt(int i)
  {

    if (null == m_map)
      return DTM.NULL;

    return m_map[i];
  }

  /**
   * Tell if the table contains the given node.
   *
   * <p>
   *  告诉表是否包含给定的节点。
   * 
   * 
   * @param s Node to look for
   *
   * @return True if the given node was found.
   */
  public boolean contains(int s)
  {

    if (null == m_map)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      int node = m_map[i];

      if (node == s)
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
   * @param elem Node to look for
   * @param index Index of where to start the search
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem, int index)
  {

    if (null == m_map)
      return -1;

    for (int i = index; i < m_firstFree; i++)
    {
      int node = m_map[i];

      if (node == elem)
        return i;
    }

    return -1;
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
   * @param elem Node to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem)
  {

    if (null == m_map)
      return -1;

    for (int i = 0; i < m_firstFree; i++)
    {
      int node = m_map[i];

      if (node == elem)
        return i;
    }

    return -1;
  }

  /**
   * Sort an array using a quicksort algorithm.
   *
   * <p>
   *  使用快速排序算法对数组排序。
   * 
   * 
   * @param a The array to be sorted.
   * @param lo0  The low index.
   * @param hi0  The high index.
   *
   * @throws Exception
   */
  public void sort(int a[], int lo0, int hi0) throws Exception
  {

    int lo = lo0;
    int hi = hi0;

    // pause(lo, hi);
    if (lo >= hi)
    {
      return;
    }
    else if (lo == hi - 1)
    {

      /*
       *  sort a two element list by swapping if necessary
       * <p>
       *  如果需要,通过交换来排序两个元素列表
       * 
       */
      if (a[lo] > a[hi])
      {
        int T = a[lo];

        a[lo] = a[hi];
        a[hi] = T;
      }

      return;
    }

    /*
     *  Pick a pivot and move it out of the way
     * <p>
     *  选择一个枢轴,并移动它的方式
     * 
     */
    int pivot = a[(lo + hi) / 2];

    a[(lo + hi) / 2] = a[hi];
    a[hi] = pivot;

    while (lo < hi)
    {

      /*
       *  Search forward from a[lo] until an element is found that
       *  is greater than the pivot or lo >= hi
       * <p>
       * 从[lo]向前搜索,直到找到大于枢轴或lo> = hi的元素
       * 
       */
      while (a[lo] <= pivot && lo < hi)
      {
        lo++;
      }

      /*
       *  Search backward from a[hi] until element is found that
       *  is less than the pivot, or lo >= hi
       * <p>
       *  从[hi]向后搜索,直到找到小于枢轴的元素,或lo> = hi
       * 
       */
      while (pivot <= a[hi] && lo < hi)
      {
        hi--;
      }

      /*
       *  Swap elements a[lo] and a[hi]
       * <p>
       *  交换元素a [lo]和[hi]
       * 
       */
      if (lo < hi)
      {
        int T = a[lo];

        a[lo] = a[hi];
        a[hi] = T;

        // pause();
      }

      // if (stopRequested) {
      //    return;
      // }
    }

    /*
     *  Put the median in the "center" of the list
     * <p>
     *  将中值放在列表的"中心"
     * 
     */
    a[hi0] = a[hi];
    a[hi] = pivot;

    /*
     *  Recursive calls, elements a[lo0] to a[lo-1] are less than or
     *  equal to pivot, elements a[hi+1] to a[hi0] are greater than
     *  pivot.
     * <p>
     *  递归调用,元素a [lo0]到a [lo-1]小于或等于pivot,元素a [hi + 1]到a [hi0]大于pivot。
     * 
     */
    sort(a, lo0, lo - 1);
    sort(a, hi + 1, hi0);
  }

  /**
   * Sort an array using a quicksort algorithm.
   *
   * <p>
   *  使用快速排序算法对数组排序。
   * 
   * @throws Exception
   */
  public void sort() throws Exception
  {
    sort(m_map, 0, m_firstFree - 1);
  }
}
