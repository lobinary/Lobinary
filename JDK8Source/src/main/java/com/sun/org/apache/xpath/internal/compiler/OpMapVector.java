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
 * $Id: OpMapVector.java,v 1.2.4.1 2005/09/10 03:57:14 jeffsuttor Exp $
 * <p>
 *  $ Id：OpMapVector.java,v 1.2.4.1 2005/09/10 03:57:14 jeffsuttor Exp $
 * 
 */

package com.sun.org.apache.xpath.internal.compiler;

/**
 *
 * Like IntVector, but used only for the OpMap array.  Length of array
 * is kept in the m_lengthPos position of the array.  Only the required methods
 * are in included here.
 * @xsl.usage internal
 * <p>
 *  与IntVector类似,但仅用于OpMap数组。数组的长度保存在数组的m_lengthPos位置。此处仅包含必需的方法。 @ xsl.usage internal
 * 
 */
public class OpMapVector {

 /** Size of blocks to allocate          */
  protected int m_blocksize;

  /** Array of ints          */
  protected int m_map[]; // IntStack is trying to see this directly

  /** Position where size of array is kept          */
  protected int m_lengthPos = 0;

  /** Size of array          */
  protected int m_mapSize;

    /**
   * Construct a OpMapVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造OpMapVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public OpMapVector(int blocksize, int increaseSize, int lengthPos)
  {

    m_blocksize = increaseSize;
    m_mapSize = blocksize;
    m_lengthPos = lengthPos;
    m_map = new int[blocksize];
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
  public final int elementAt(int i)
  {
    return m_map[i];
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
  public final void setElementAt(int value, int index)
  {
    if (index >= m_mapSize)
    {
      int oldSize = m_mapSize;

      m_mapSize += m_blocksize;

      int newMap[] = new int[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, oldSize);

      m_map = newMap;
    }

    m_map[index] = value;
  }


  /*
   * Reset the array to the supplied size.  No checking is done.
   *
   * <p>
   * 
   * @param size The size to trim to.
   */
  public final void setToSize(int size) {

    int newMap[] = new int[size];

    System.arraycopy(m_map, 0, newMap, 0, m_map[m_lengthPos]);

    m_mapSize = size;
    m_map = newMap;

  }

}
