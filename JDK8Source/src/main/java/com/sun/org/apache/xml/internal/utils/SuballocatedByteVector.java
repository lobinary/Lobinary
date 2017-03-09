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
 * $Id: SuballocatedByteVector.java,v 1.2.4.1 2005/09/15 08:15:57 suresh_emailid Exp $
 * <p>
 *  $ Id：SuballocatedByteVector.java,v 1.2.4.1 2005/09/15 08:15:57 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A very simple table that stores a list of byte. Very similar API to our
 * IntVector class (same API); different internal storage.
 *
 * This version uses an array-of-arrays solution. Read/write access is thus
 * a bit slower than the simple IntVector, and basic storage is a trifle
 * higher due to the top-level array -- but appending is O(1) fast rather
 * than O(N**2) slow, which will swamp those costs in situations where
 * long vectors are being built up.
 *
 * Known issues:
 *
 * Some methods are private because they haven't yet been tested properly.
 *
 * If an element has not been set (because we skipped it), its value will
 * initially be 0. Shortening the vector does not clear old storage; if you
 * then skip values and setElementAt a higher index again, you may see old data
 * reappear in the truncated-and-restored section. Doing anything else would
 * have performance costs.
 * @xsl.usage internal
 * <p>
 *  一个非常简单的表,存储一个字节列表。非常类似于我们的IntVector类(相同的API)的API;不同的内部存储。
 * 
 *  此版本使用数组数组解决方案。
 * 读/写访问因此比简单的IntVector慢一点,并且由于顶层数组,基本存储是一个微小的高 - 但是追加是O(1)快,而不是O(N ** 2)慢,将在那些长向量正在建立的情况下淹没这些成本。
 * 
 *  已知的问题：
 * 
 *  有些方法是私有的,因为它们尚未正确测试。
 * 
 * 如果一个元素没有被设置(因为我们跳过它),它的值最初将为0.缩短向量不会清除旧的存储;如果您随后跳过值并再次设置setElementA更高的索引,您可能会在截断和恢复部分中看到旧数据重现。
 * 做其他任何事情都会产生性能成本。 @ xsl.usage internal。
 * 
 */
public class SuballocatedByteVector
{
  /** Size of blocks to allocate          */
  protected int m_blocksize;

  /** Number of blocks to (over)allocate by */
  protected  int m_numblocks=32;

  /** Array of arrays of bytes          */
  protected byte m_map[][];

  /** Number of bytes in array          */
  protected int m_firstFree = 0;

  /** "Shortcut" handle to m_map[0] */
  protected byte m_map0[];

  /**
   * Default constructor.  Note that the default
   * block size is very small, for small lists.
   * <p>
   *  默认构造函数。请注意,对于小列表,默认块大小非常小。
   * 
   */
  public SuballocatedByteVector()
  {
    this(2048);
  }

  /**
   * Construct a ByteVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造一个ByteVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public SuballocatedByteVector(int blocksize)
  {
    m_blocksize = blocksize;
    m_map0=new byte[blocksize];
    m_map = new byte[m_numblocks][];
    m_map[0]=m_map0;
  }

  /**
   * Construct a ByteVector, using the given block size.
   *
   * <p>
   *  使用给定的块大小构造一个ByteVector。
   * 
   * 
   * @param blocksize Size of block to allocate
   */
  public SuballocatedByteVector(int blocksize, int increaseSize)
  {
    // increaseSize not currently used.
    this(blocksize);
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
  public int size()
  {
    return m_firstFree;
  }

  /**
   * Set the length of the list.
   *
   * <p>
   *  设置列表的长度。
   * 
   * 
   * @return length of the list
   */
  private  void setSize(int sz)
  {
    if(m_firstFree<sz)
      m_firstFree = sz;
  }

  /**
   * Append a byte onto the vector.
   *
   * <p>
   *  向矢量附加一个字节。
   * 
   * 
   * @param value Byte to add to the list
   */
  public  void addElement(byte value)
  {
    if(m_firstFree<m_blocksize)
      m_map0[m_firstFree++]=value;
    else
    {
      int index=m_firstFree/m_blocksize;
      int offset=m_firstFree%m_blocksize;
      ++m_firstFree;

      if(index>=m_map.length)
      {
        int newsize=index+m_numblocks;
        byte[][] newMap=new byte[newsize][];
        System.arraycopy(m_map, 0, newMap, 0, m_map.length);
        m_map=newMap;
      }
      byte[] block=m_map[index];
      if(null==block)
        block=m_map[index]=new byte[m_blocksize];
      block[offset]=value;
    }
  }

  /**
   * Append several byte values onto the vector.
   *
   * <p>
   *  将几个字节值附加到向量。
   * 
   * 
   * @param value Byte to add to the list
   */
  private  void addElements(byte value, int numberOfElements)
  {
    if(m_firstFree+numberOfElements<m_blocksize)
      for (int i = 0; i < numberOfElements; i++)
      {
        m_map0[m_firstFree++]=value;
      }
    else
    {
      int index=m_firstFree/m_blocksize;
      int offset=m_firstFree%m_blocksize;
      m_firstFree+=numberOfElements;
      while( numberOfElements>0)
      {
        if(index>=m_map.length)
        {
          int newsize=index+m_numblocks;
          byte[][] newMap=new byte[newsize][];
          System.arraycopy(m_map, 0, newMap, 0, m_map.length);
          m_map=newMap;
        }
        byte[] block=m_map[index];
        if(null==block)
          block=m_map[index]=new byte[m_blocksize];
        int copied=(m_blocksize-offset < numberOfElements)
          ? m_blocksize-offset : numberOfElements;
        numberOfElements-=copied;
        while(copied-- > 0)
          block[offset++]=value;

        ++index;offset=0;
      }
    }
  }

  /**
   * Append several slots onto the vector, but do not set the values.
   * Note: "Not Set" means the value is unspecified.
   *
   * <p>
   *  将多个插槽添加到向量上,但不要设置值。注意："未设置"表示该值未指定。
   * 
   * 
   * @param numberOfElements
   */
  private  void addElements(int numberOfElements)
  {
    int newlen=m_firstFree+numberOfElements;
    if(newlen>m_blocksize)
    {
      int index=m_firstFree%m_blocksize;
      int newindex=(m_firstFree+numberOfElements)%m_blocksize;
      for(int i=index+1;i<=newindex;++i)
        m_map[i]=new byte[m_blocksize];
    }
    m_firstFree=newlen;
  }

  /**
   * Inserts the specified node in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   *
   * Insertion may be an EXPENSIVE operation!
   *
   * <p>
   *  在指定的索引处将指定的节点插入到此向量中。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   *  插入可能是费用操作！
   * 
   * 
   * @param value Byte to insert
   * @param at Index of where to insert
   */
  private  void insertElementAt(byte value, int at)
  {
    if(at==m_firstFree)
      addElement(value);
    else if (at>m_firstFree)
    {
      int index=at/m_blocksize;
      if(index>=m_map.length)
      {
        int newsize=index+m_numblocks;
        byte[][] newMap=new byte[newsize][];
        System.arraycopy(m_map, 0, newMap, 0, m_map.length);
        m_map=newMap;
      }
      byte[] block=m_map[index];
      if(null==block)
        block=m_map[index]=new byte[m_blocksize];
      int offset=at%m_blocksize;
      block[offset]=value;
      m_firstFree=offset+1;
    }
    else
    {
      int index=at/m_blocksize;
      int maxindex=m_firstFree+1/m_blocksize;
      ++m_firstFree;
      int offset=at%m_blocksize;
      byte push;

      // ***** Easier to work down from top?
      while(index<=maxindex)
      {
        int copylen=m_blocksize-offset-1;
        byte[] block=m_map[index];
        if(null==block)
        {
          push=0;
          block=m_map[index]=new byte[m_blocksize];
        }
        else
        {
          push=block[m_blocksize-1];
          System.arraycopy(block, offset , block, offset+1, copylen);
        }
        block[offset]=value;
        value=push;
        offset=0;
        ++index;
      }
    }
  }

  /**
   * Wipe it out.
   * <p>
   *  擦干净。
   * 
   */
  public void removeAllElements()
  {
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
   * @param s Byte to remove from array
   *
   * @return True if the byte was removed, false if it was not found
   */
  private  boolean removeElement(byte s)
  {
    int at=indexOf(s,0);
    if(at<0)
      return false;
    removeElementAt(at);
    return true;
  }

  /**
   * Deletes the component at the specified index. Each component in
   * this vector with an index greater or equal to the specified
   * index is shifted downward to have an index one smaller than
   * the value it had previously.
   *
   * <p>
   * 删除指定索引处的组件。该向量中具有大于或等于指定索引的索引的每个分量向下移动,以使索引1小于其先前的值。
   * 
   * 
   * @param at index of where to remove a byte
   */
  private  void removeElementAt(int at)
  {
    // No point in removing elements that "don't exist"...
    if(at<m_firstFree)
    {
      int index=at/m_blocksize;
      int maxindex=m_firstFree/m_blocksize;
      int offset=at%m_blocksize;

      while(index<=maxindex)
      {
        int copylen=m_blocksize-offset-1;
        byte[] block=m_map[index];
        if(null==block)
          block=m_map[index]=new byte[m_blocksize];
        else
          System.arraycopy(block, offset+1, block, offset, copylen);
        if(index<maxindex)
        {
          byte[] next=m_map[index+1];
          if(next!=null)
            block[m_blocksize-1]=(next!=null) ? next[0] : 0;
        }
        else
          block[m_blocksize-1]=0;
        offset=0;
        ++index;
      }
    }
    --m_firstFree;
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
   * @param value
   * @param at     Index of where to set the object
   */
  public void setElementAt(byte value, int at)
  {
    if(at<m_blocksize)
    {
      m_map0[at]=value;
      return;
    }

    int index=at/m_blocksize;
    int offset=at%m_blocksize;

    if(index>=m_map.length)
    {
      int newsize=index+m_numblocks;
      byte[][] newMap=new byte[newsize][];
      System.arraycopy(m_map, 0, newMap, 0, m_map.length);
      m_map=newMap;
    }

    byte[] block=m_map[index];
    if(null==block)
      block=m_map[index]=new byte[m_blocksize];
    block[offset]=value;

    if(at>=m_firstFree)
      m_firstFree=at+1;
  }

  /**
   * Get the nth element. This is often at the innermost loop of an
   * application, so performance is critical.
   *
   * <p>
   *  获取第n个元素。这通常在应用程序的最内层循环,因此性能至关重要。
   * 
   * 
   * @param i index of value to get
   *
   * @return value at given index. If that value wasn't previously set,
   * the result is undefined for performance reasons. It may throw an
   * exception (see below), may return zero, or (if setSize has previously
   * been used) may return stale data.
   *
   * @throws ArrayIndexOutOfBoundsException if the index was _clearly_
   * unreasonable (negative, or past the highest block).
   *
   * @throws NullPointerException if the index points to a block that could
   * have existed (based on the highest index used) but has never had anything
   * set into it.
   * %REVIEW% Could add a catch to create the block in that case, or return 0.
   * Try/Catch is _supposed_ to be nearly free when not thrown to. Do we
   * believe that? Should we have a separate safeElementAt?
   */
  public byte elementAt(int i)
  {
    // %OPT% Does this really buy us anything? Test versus division for small,
    // test _plus_ division for big docs.
    if(i<m_blocksize)
      return m_map0[i];

    return m_map[i/m_blocksize][i%m_blocksize];
  }

  /**
   * Tell if the table contains the given node.
   *
   * <p>
   *  告诉表是否包含给定的节点。
   * 
   * 
   * @param s object to look for
   *
   * @return true if the object is in the list
   */
  private  boolean contains(byte s)
  {
    return (indexOf(s,0) >= 0);
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
  public int indexOf(byte elem, int index)
  {
    if(index>=m_firstFree)
      return -1;

    int bindex=index/m_blocksize;
    int boffset=index%m_blocksize;
    int maxindex=m_firstFree/m_blocksize;
    byte[] block;

    for(;bindex<maxindex;++bindex)
    {
      block=m_map[bindex];
      if(block!=null)
        for(int offset=boffset;offset<m_blocksize;++offset)
          if(block[offset]==elem)
            return offset+bindex*m_blocksize;
      boffset=0; // after first
    }
    // Last block may need to stop before end
    int maxoffset=m_firstFree%m_blocksize;
    block=m_map[maxindex];
    for(int offset=boffset;offset<maxoffset;++offset)
      if(block[offset]==elem)
        return offset+maxindex*m_blocksize;

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
   * @param elem object to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public int indexOf(byte elem)
  {
    return indexOf(elem,0);
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   *  搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * @param elem Object to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  private  int lastIndexOf(byte elem)
  {
    int boffset=m_firstFree%m_blocksize;
    for(int index=m_firstFree/m_blocksize;
        index>=0;
        --index)
    {
      byte[] block=m_map[index];
      if(block!=null)
        for(int offset=boffset; offset>=0; --offset)
          if(block[offset]==elem)
            return offset+index*m_blocksize;
      boffset=0; // after first
    }
    return -1;
  }

}
