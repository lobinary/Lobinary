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
 * $Id: ChunkedIntArray.java,v 1.2.4.1 2005/09/15 08:14:58 suresh_emailid Exp $
 * <p>
 *  $ Id：ChunkedIntArray.java,v 1.2.4.1 2005/09/15 08:14:58 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;

/**
 * <code>ChunkedIntArray</code> is an extensible array of blocks of integers.
 * (I'd consider Vector, but it's unable to handle integers except by
 * turning them into Objects.)

 * <p>Making this a separate class means some call-and-return overhead. But
 * doing it all inline tends to be fragile and expensive in coder time,
 * not to mention driving up code size. If you want to inline it, feel free.
 * The Java text suggest that private and Final methods may be inlined,
 * and one can argue that this beast need not be made subclassable...</p>
 *
 * <p>%REVIEW% This has strong conceptual overlap with the IntVector class.
 * It would probably be a good thing to merge the two, when time permits.<p>
 * <p>
 *  <code> ChunkedIntArray </code>是一个可扩展的整数块数组。 (我会考虑Vector,但它不能处理整数,除非将它们转换为对象。)
 * 
 *  <p>将此作为一个单独的类意味着一些调用和返回开销。但是做所有内联在编码器时间往往是脆弱和昂贵的,更不用说提高代码大小。如果你想内联它,随意。
 *  Java文本建议私有和Final方法可以内联,并且可以认为这个兽不需要被子类化... </p>。
 * 
 *  <p>％REVIEW％这与IntVector类有很强的概念重叠。当时间允许时,合并两者可能是一件好事。<p>
 * 
 */
final class ChunkedIntArray
{
  final int slotsize=4; // Locked, MUST be power of two in current code
  // Debugging tip: Cranking lowbits down to 4 or so is a good
  // way to pound on the array addressing code.
  static final int lowbits=10; // How many bits address within chunks
  static final int chunkalloc=1<<lowbits;
  static final int lowmask=chunkalloc-1;

  ChunksVector chunks=new ChunksVector();
  final int fastArray[] = new int[chunkalloc];
  int lastUsed=0;

  /**
   * Create a new CIA with specified record size. Currently record size MUST
   * be a power of two... and in fact is hardcoded to 4.
   * <p>
   * 创建具有指定记录大小的新CIA。当前记录大小必须是二的幂...并且实际上是硬编码到4。
   * 
   */
  ChunkedIntArray(int slotsize)
  {
    if(this.slotsize<slotsize)
      throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage(XMLErrorResources.ER_CHUNKEDINTARRAY_NOT_SUPPORTED, new Object[]{Integer.toString(slotsize)})); //"ChunkedIntArray("+slotsize+") not currently supported");
    else if (this.slotsize>slotsize)
      System.out.println("*****WARNING: ChunkedIntArray("+slotsize+") wasting "+(this.slotsize-slotsize)+" words per slot");
    chunks.addElement(fastArray);
  }
  /**
   * Append a 4-integer record to the CIA, starting with record 1. (Since
   * arrays are initialized to all-0, 0 has been reserved as the "unknown"
   * value in DTM.)
   * <p>
   *  将一个4整数记录附加到CIA,从记录1开始。(由于数组被初始化为全0,0已被保留为DTM中的"未知"值。)
   * 
   * 
   * @return the index at which this record was inserted.
   */
  int appendSlot(int w0, int w1, int w2, int w3)
  {
    /*
    try
    {
      int newoffset = (lastUsed+1)*slotsize;
      fastArray[newoffset] = w0;
      fastArray[newoffset+1] = w1;
      fastArray[newoffset+2] = w2;
      fastArray[newoffset+3] = w3;
      return ++lastUsed;
    }
    catch(ArrayIndexOutOfBoundsException aioobe)
      int newoffset = (lastUsed+1)* <p>
      int newoffset = (lastUsed+1)*  try {int newoffset =(lastUsed + 1)* slotsize; fastArray [newoffset] = w0; fastArray [newoffset + 1] = w1; fastArray [newoffset + 2] = w2; fastArray [newoffset + 3] = w3; return ++ lastUsed; }
      int newoffset = (lastUsed+1)*  catch(ArrayIndexOutOfBoundsException aioobe)。
      int newoffset = (lastUsed+1)* 
    */
    {
      final int slotsize=4;
      int newoffset = (lastUsed+1)*slotsize;
      int chunkpos = newoffset >> lowbits;
      int slotpos = (newoffset & lowmask);

      // Grow if needed
      if (chunkpos > chunks.size() - 1)
        chunks.addElement(new int[chunkalloc]);
      int[] chunk = chunks.elementAt(chunkpos);
      chunk[slotpos] = w0;
      chunk[slotpos+1] = w1;
      chunk[slotpos+2] = w2;
      chunk[slotpos+3] = w3;

      return ++lastUsed;
    }
  }
  /**
   * Retrieve an integer from the CIA by record number and column within
   * the record, both 0-based (though position 0 is reserved for special
   * purposes).
   * <p>
   *  通过记录中的记录号和列从CIA中检索整数,都是基于0的(尽管位置0保留用于特殊目的)。
   * 
   * 
   * @param position int Record number
   * @param slotpos int Column number
   */
  int readEntry(int position, int offset) throws ArrayIndexOutOfBoundsException
  {
    /*
    try
    {
      return fastArray[(position*slotsize)+offset];
    }
    catch(ArrayIndexOutOfBoundsException aioobe)
      return fastArray[(position* <p>
      return fastArray[(position*  try {return fastArray [(position * slotsize)+ offset]; } catch(ArrayIndexOutOfBoundsException aioobe
      return fastArray[(position* )。
      return fastArray[(position* 
    */
    {
      // System.out.println("Using slow read (1)");
      if (offset>=slotsize)
        throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage(XMLErrorResources.ER_OFFSET_BIGGER_THAN_SLOT, null)); //"Offset bigger than slot");
      position*=slotsize;
      int chunkpos = position >> lowbits;
      int slotpos = position & lowmask;
      int[] chunk = chunks.elementAt(chunkpos);
      return chunk[slotpos + offset];
    }
  }

  // Check that the node at index "position" is not an ancestor
  // of the node at index "startPos". IF IT IS, DO NOT ACCEPT IT AND
  // RETURN -1. If position is NOT an ancestor, return position.
  // Special case: The Document node (position==0) is acceptable.
  //
  // This test supports DTM.getNextPreceding.
  int specialFind(int startPos, int position)
  {
          // We have to look all the way up the ancestor chain
          // to make sure we don't have an ancestor.
          int ancestor = startPos;
          while(ancestor > 0)
          {
                // Get the node whose index == ancestor
                ancestor*=slotsize;
                int chunkpos = ancestor >> lowbits;
                int slotpos = ancestor & lowmask;
                int[] chunk = chunks.elementAt(chunkpos);

                // Get that node's parent (Note that this assumes w[1]
                // is the parent node index. That's really a DTM feature
                // rather than a ChunkedIntArray feature.)
                ancestor = chunk[slotpos + 1];

                if(ancestor == position)
                         break;
          }

          if (ancestor <= 0)
          {
                  return position;
          }
          return -1;
  }

  /**
  /* <p>
  /* 
   * @return int index of highest-numbered record currently in use
   */
  int slotsUsed()
  {
    return lastUsed;
  }

  /** Disard the highest-numbered record. This is used in the string-buffer
   CIA; when only a single characters() chunk has been recieved, its index
   is moved into the Text node rather than being referenced by indirection
   into the text accumulator.
  /* <p>
  /*  中央情报局;当只有一个字符()块被接收时,它的索引被移动到Text节点中,而不是被间接引入到文本累加器中。
  /* 
   */
  void discardLast()
  {
    --lastUsed;
  }

  /**
   * Overwrite the integer found at a specific record and column.
   * Used to back-patch existing records, most often changing their
   * "next sibling" reference from 0 (unknown) to something meaningful
   * <p>
   *  覆盖在特定记录和列中找到的整数。用于修补现有记录,通常将其"下一个同级"引用从0(未知)更改为有意义的
   * 
   * 
   * @param position int Record number
   * @param offset int Column number
   * @param value int New contents
   */
  void writeEntry(int position, int offset, int value) throws ArrayIndexOutOfBoundsException
  {
    /*
    try
    {
      fastArray[( position*slotsize)+offset] = value;
    }
    catch(ArrayIndexOutOfBoundsException aioobe)
      fastArray[( position* <p>
      fastArray[( position*  try {fastArray [(position * slotsize)+ offset] = value; } catch(ArrayIndexOutOfBoundsException aioob
      fastArray[( position* e)。
      fastArray[( position* 
    */
    {
      if (offset >= slotsize)
        throw new ArrayIndexOutOfBoundsException(XMLMessages.createXMLMessage(XMLErrorResources.ER_OFFSET_BIGGER_THAN_SLOT, null)); //"Offset bigger than slot");
      position*=slotsize;
      int chunkpos = position >> lowbits;
      int slotpos = position & lowmask;
      int[] chunk = chunks.elementAt(chunkpos);
      chunk[slotpos + offset] = value; // ATOMIC!
    }
  }

  /**
   * Overwrite an entire (4-integer) record at the specified index.
   * Mostly used to create record 0, the Document node.
   * <p>
   *  在指定的索引处覆盖整个(4整数)记录。主要用于创建记录0,即Document节点。
   * 
   * 
   * @param position integer Record number
   * @param w0 int
   * @param w1 int
   * @param w2 int
   * @param w3 int
   */
  void writeSlot(int position, int w0, int w1, int w2, int w3)
  {
      position *= slotsize;
      int chunkpos = position >> lowbits;
      int slotpos = (position & lowmask);

    // Grow if needed
    if (chunkpos > chunks.size() - 1)
      chunks.addElement(new int[chunkalloc]);
    int[] chunk = chunks.elementAt(chunkpos);
    chunk[slotpos] = w0;
    chunk[slotpos + 1] = w1;
    chunk[slotpos + 2] = w2;
    chunk[slotpos + 3] = w3;
  }

  /**
   * Retrieve the contents of a record into a user-supplied buffer array.
   * Used to reduce addressing overhead when code will access several
   * columns of the record.
   * <p>
   *  将记录的内容检索到用户提供的缓冲区数组中。用于在代码访问记录的多个列时减少寻址开销。
   * 
   * 
   * @param position int Record number
   * @param buffer int[] Integer array provided by user, must be large enough
   * to hold a complete record.
   */
  void readSlot(int position, int[] buffer)
  {
    /*
    try
    {
      System.arraycopy(fastArray, position*slotsize, buffer, 0, slotsize);
    }
    catch(ArrayIndexOutOfBoundsException aioobe)
      System.arraycopy(fastArray, position* <p>
      System.arraycopy(fastArray, position* 尝试{System.arraycopy(fastArray,定位*锁大小,缓冲区,0,槽的大小); }赶上(ArrayIndexOutOfBoundsException异常aioobe)
    */
    {
      // System.out.println("Using slow read (2): "+position);
      position *= slotsize;
      int chunkpos = position >> lowbits;
      int slotpos = (position & lowmask);

      // Grow if needed
      if (chunkpos > chunks.size() - 1)
        chunks.addElement(new int[chunkalloc]);
      int[] chunk = chunks.elementAt(chunkpos);
      System.arraycopy(chunk,slotpos,buffer,0,slotsize);
    }
  }

  class ChunksVector
  {
    final int BLOCKSIZE = 64;
    int[] m_map[] = new int[BLOCKSIZE][];
    int m_mapSize = BLOCKSIZE;
    int pos = 0;

    ChunksVector()
    {
    }

    final int size()
    {
      return pos;
    }

    void addElement(int[] value)
    {
      if(pos >= m_mapSize)
      {
        int orgMapSize = m_mapSize;
        while(pos >= m_mapSize)
          m_mapSize+=BLOCKSIZE;
        int[] newMap[] = new int[m_mapSize][];
        System.arraycopy(m_map, 0, newMap, 0, orgMapSize);
        m_map = newMap;
      }
      // For now, just do a simple append.  A sorted insert only
      // makes sense if we're doing an binary search or some such.
      m_map[pos] = value;
      pos++;
    }

    final int[] elementAt(int pos)
    {
      return m_map[pos];
    }
  }
}
