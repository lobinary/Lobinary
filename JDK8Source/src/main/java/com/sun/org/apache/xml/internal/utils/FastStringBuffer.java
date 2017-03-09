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
 * $Id: FastStringBuffer.java,v 1.2.4.1 2005/09/15 08:15:44 suresh_emailid Exp $
 * <p>
 *  $ Id：FastStringBuffer.java,v 1.2.4.1 2005/09/15 08:15:44 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * Bare-bones, unsafe, fast string buffer. No thread-safety, no
 * parameter range checking, exposed fields. Note that in typical
 * applications, thread-safety of a StringBuffer is a somewhat
 * dubious concept in any case.
 * <p>
 * Note that Stree and DTM used a single FastStringBuffer as a string pool,
 * by recording start and length indices within this single buffer. This
 * minimizes heap overhead, but of course requires more work when retrieving
 * the data.
 * <p>
 * FastStringBuffer operates as a "chunked buffer". Doing so
 * reduces the need to recopy existing information when an append
 * exceeds the space available; we just allocate another chunk and
 * flow across to it. (The array of chunks may need to grow,
 * admittedly, but that's a much smaller object.) Some excess
 * recopying may arise when we extract Strings which cross chunk
 * boundaries; larger chunks make that less frequent.
 * <p>
 * The size values are parameterized, to allow tuning this code. In
 * theory, Result Tree Fragments might want to be tuned differently
 * from the main document's text.
 * <p>
 * %REVIEW% An experiment in self-tuning is
 * included in the code (using nested FastStringBuffers to achieve
 * variation in chunk sizes), but this implementation has proven to
 * be problematic when data may be being copied from the FSB into itself.
 * We should either re-architect that to make this safe (if possible)
 * or remove that code and clean up for performance/maintainability reasons.
 * <p>
 * <p>
 *  裸骨,不安全,快速字符串缓冲。无螺纹安全,无参数范围检查,外露字段。注意,在典型的应用中,StringBuffer的线程安全在任何情况下都是一个有点可疑的概念。
 * <p>
 *  注意,Stree和DTM使用单个FastStringBuffer作为字符串池,通过在此单个缓冲区中记录开始和长度索引。这最小化了堆开销,但当检索数据时当然需要更多的工作。
 * <p>
 * FastStringBuffer作为"分块缓冲区"操作。这样做可以减少在追加超出可用空间时重新复制现有信息的需要;我们只是分配另一个块并流向它。
 *  (块的数组可能需要增长,不可否认,但是这是一个更小的对象。)当我们提取跨块的边界的字符串时,可能会出现一些多余的重复;较大的块使得较少频繁。
 * <p>
 *  参数化了大小值,以允许调整此代码。理论上,结果树片段可能希望与主文档的文本不同地进行调整。
 * <p>
 *  ％REVIEW％代码中包含了自调整实验(使用嵌套的FastStringBuffers实现块大小的变化),但是当数据可能从FSB复制到本身时,这种实现已经被证明是有问题的。
 * 我们应该重新架构,使这个安全(如果可能)或删除该代码,并清除性能/可维护性的原因。
 * <p>
 */
public class FastStringBuffer
{
  // If nonzero, forces the inial chunk size.
  /**/static final int DEBUG_FORCE_INIT_BITS=0;

        // %BUG% %REVIEW% *****PROBLEM SUSPECTED: If data from an FSB is being copied
        // back into the same FSB (variable set from previous variable, for example)
        // and blocksize changes in mid-copy... there's risk of severe malfunction in
        // the read process, due to how the resizing code re-jiggers storage. Arggh.
        // If we want to retain the variable-size-block feature, we need to reconsider
        // that issue. For now, I have forced us into fixed-size mode.
    static final boolean DEBUG_FORCE_FIXED_CHUNKSIZE=true;

        /** Manifest constant: Suppress leading whitespace.
         * This should be used when normalize-to-SAX is called for the first chunk of a
         * multi-chunk output, or one following unsuppressed whitespace in a previous
         * chunk.
         * <p>
         *  //％BUG％％REVIEW％***** PROBLEM SUSPECTED：如果来自FSB的数据正在被复制回到同一个FSB(例如,从前一个变量设置的变量)//并且中间复制...有读取过程中严重故障的
         * 风险,由于如何调整大小代码重新开始存储。
         *  Arggh。 //如果我们想保留可变大小块功能,我们需要重新考虑//这个问题。现在,我已经迫使我们进入固定大小的模式。
         *  static final boolean DEBUG_FORCE_FIXED_CHUNKSIZE = true;。
         * 
         * / **清单常量：抑制领先的空格。当对多块输出的第一个块调用标准化到SAX时,或者在先前块中的未被抑制的空格时,应该使用这个。
         * 
         * 
         * @see #sendNormalizedSAXcharacters(org.xml.sax.ContentHandler,int,int)
         */
        public static final int SUPPRESS_LEADING_WS=0x01;

        /** Manifest constant: Suppress trailing whitespace.
         * This should be used when normalize-to-SAX is called for the last chunk of a
         * multi-chunk output; it may have to be or'ed with SUPPRESS_LEADING_WS.
         * <p>
         *  当对多块输出的最后一个块调用SAX时,应使用此选项;它可能必须与SUPPRESS_LEADING_WS或。
         * 
         */
        public static final int SUPPRESS_TRAILING_WS=0x02;

        /** Manifest constant: Suppress both leading and trailing whitespace.
         * This should be used when normalize-to-SAX is called for a complete string.
         * (I'm not wild about the name of this one. Ideas welcome.)
         * <p>
         *  当为一个完整的字符串调用标准化到SAX时,应使用此选项。 (我不喜欢这个名字,想法欢迎。)
         * 
         * 
         * @see #sendNormalizedSAXcharacters(org.xml.sax.ContentHandler,int,int)
         */
        public static final int SUPPRESS_BOTH
                = SUPPRESS_LEADING_WS | SUPPRESS_TRAILING_WS;

        /** Manifest constant: Carry trailing whitespace of one chunk as leading
         * whitespace of the next chunk. Used internally; I don't see any reason
         * to make it public right now.
         * <p>
         *  空格的下一个块。内部使用;我没有看到任何理由让它公开现在。
         * 
         */
        private static final int CARRY_WS=0x04;

        /**
   * Field m_chunkBits sets our chunking strategy, by saying how many
   * bits of index can be used within a single chunk before flowing over
   * to the next chunk. For example, if m_chunkbits is set to 15, each
   * chunk can contain up to 2^15 (32K) characters
   * <p>
   *  字段m_chunkBits设置我们的分块策略,通过说在流向下一个块之前在单个块内可以使用多少比特的索引。例如,如果m_chunkbits设置为15,则每个块最多可包含2 ^ 15(32K)个字符
   * 
   */
  int m_chunkBits = 15;

  /**
   * Field m_maxChunkBits affects our chunk-growth strategy, by saying what
   * the largest permissible chunk size is in this particular FastStringBuffer
   * hierarchy.
   * <p>
   *  字段m_maxChunkBits通过说明在此特定FastStringBuffer层次结构中允许的最大块大小,影响我们的块增长策略。
   * 
   */
  int m_maxChunkBits = 15;

  /**
   * Field m_rechunkBits affects our chunk-growth strategy, by saying how
   * many chunks should be allocated at one size before we encapsulate them
   * into the first chunk of the next size up. For example, if m_rechunkBits
   * is set to 3, then after 8 chunks at a given size we will rebundle
   * them as the first element of a FastStringBuffer using a chunk size
   * 8 times larger (chunkBits shifted left three bits).
   * <p>
   *  字段m_rechunkBits影响我们的块增长策略,通过说,在我们将它们封装到下一个大小的第一个块之前应该分配一个大小的块。
   * 例如,如果m_rechunkBits设置为3,则在给定大小的8个块之后,我们将它们重新绑定为FastStringBuffer的第一个元素,使用块大小的8倍大(chunkBits左移3位)。
   * 
   */
  int m_rebundleBits = 2;

  /**
   * Field m_chunkSize establishes the maximum size of one chunk of the array
   * as 2**chunkbits characters.
   * (Which may also be the minimum size if we aren't tuning for storage)
   * <p>
   * 字段m_chunkSize将数组的一个块的最大大小确定为2 ** chunkbits个字符。 (如果我们不调优存储,它也可以是最小大小)
   * 
   */
  int m_chunkSize;  // =1<<(m_chunkBits-1);

  /**
   * Field m_chunkMask is m_chunkSize-1 -- in other words, m_chunkBits
   * worth of low-order '1' bits, useful for shift-and-mask addressing
   * within the chunks.
   * <p>
   *  字段m_chunkMask是m_chunkSize-1  - 换句话说,m_chunkBits的低阶"1"位,对块内的移位和掩码寻址有用。
   * 
   */
  int m_chunkMask;  // =m_chunkSize-1;

  /**
   * Field m_array holds the string buffer's text contents, using an
   * array-of-arrays. Note that this array, and the arrays it contains, may be
   * reallocated when necessary in order to allow the buffer to grow;
   * references to them should be considered to be invalidated after any
   * append. However, the only time these arrays are directly exposed
   * is in the sendSAXcharacters call.
   * <p>
   *  字段m_array保存字符串缓冲区的文本内容,使用数组数组。注意,当需要时,可以重新分配该数组及其包含的数组,以便允许缓冲器增长;在任何附加之后,对它们的引用应被视为无效。
   * 但是,这些数组只有直接暴露的时间是在sendSAXcharacters调用中。
   * 
   */
  char[][] m_array;

  /**
   * Field m_lastChunk is an index into m_array[], pointing to the last
   * chunk of the Chunked Array currently in use. Note that additional
   * chunks may actually be allocated, eg if the FastStringBuffer had
   * previously been truncated or if someone issued an ensureSpace request.
   * <p>
   * The insertion point for append operations is addressed by the combination
   * of m_lastChunk and m_firstFree.
   * <p>
   *  字段m_lastChunk是进入m_array []的索引,指向当前使用的Chunked数组的最后一个数据块。
   * 注意,实际上可以分配附加的块,例如,如果FastStringBuffer以前被截断或者有人发出了ensureSpace请求。
   * <p>
   *  附加操作的插入点由m_lastChunk和m_firstFree的组合来处理。
   * 
   */
  int m_lastChunk = 0;

  /**
   * Field m_firstFree is an index into m_array[m_lastChunk][], pointing to
   * the first character in the Chunked Array which is not part of the
   * FastStringBuffer's current content. Since m_array[][] is zero-based,
   * the length of that content can be calculated as
   * (m_lastChunk<<m_chunkBits) + m_firstFree
   * <p>
   *  字段m_firstFree是进入m_array [m_lastChunk] []的索引,指向Chunked数组中不是FastStringBuffer当前内容一部分的第一个字符。
   * 由于m_array [] []是从零开始的,所以内容的长度可以计算为(m_lastChunk << m_chunkBits)+ m_firstFree。
   * 
   */
  int m_firstFree = 0;

  /**
   * Field m_innerFSB, when non-null, is a FastStringBuffer whose total
   * length equals m_chunkSize, and which replaces m_array[0]. This allows
   * building a hierarchy of FastStringBuffers, where early appends use
   * a smaller chunkSize (for less wasted memory overhead) but later
   * ones use a larger chunkSize (for less heap activity overhead).
   * <p>
   * 字段m_innerFSB,当非空时,是一个FastStringBuffer,其总长度等于m_chunkSize,并替换m_array [0]。
   * 这允许构建FastStringBuffers的层次结构,早期追加使用较小的chunkSize(用于较少浪费的内存开销),但后来使用较大的chunkSize(较少的堆活动开销)。
   * 
   */
  FastStringBuffer m_innerFSB = null;

  /**
   * Construct a FastStringBuffer, with allocation policy as per parameters.
   * <p>
   * For coding convenience, I've expressed both allocation sizes in terms of
   * a number of bits. That's needed for the final size of a chunk,
   * to permit fast and efficient shift-and-mask addressing. It's less critical
   * for the inital size, and may be reconsidered.
   * <p>
   * An alternative would be to accept integer sizes and round to powers of two;
   * that really doesn't seem to buy us much, if anything.
   *
   * <p>
   *  构造一个FastStringBuffer,按照参数分配策略。
   * <p>
   *  为了编码方便,我已经用位数表示了两种分配大小。这是块的最终大小所需要的,以允许快速和有效的移位和掩码寻址。它对于初始尺寸不太重要,并且可以重新考虑。
   * <p>
   *  一个替代方法是接受整数大小和round到2的幂;这真的似乎不买我们多,如果有什么。
   * 
   * 
   * @param initChunkBits Length in characters of the initial allocation
   * of a chunk, expressed in log-base-2. (That is, 10 means allocate 1024
   * characters.) Later chunks will use larger allocation units, to trade off
   * allocation speed of large document against storage efficiency of small
   * ones.
   * @param maxChunkBits Number of character-offset bits that should be used for
   * addressing within a chunk. Maximum length of a chunk is 2^chunkBits
   * characters.
   * @param rebundleBits Number of character-offset bits that addressing should
   * advance before we attempt to take a step from initChunkBits to maxChunkBits
   */
  public FastStringBuffer(int initChunkBits, int maxChunkBits,
                          int rebundleBits)
  {
    if(DEBUG_FORCE_INIT_BITS!=0) initChunkBits=DEBUG_FORCE_INIT_BITS;

    // %REVIEW%
    // Should this force to larger value, or smaller? Smaller less efficient, but if
    // someone requested variable mode it's because they care about storage space.
    // On the other hand, given the other changes I'm making, odds are that we should
    // adopt the larger size. Dither, dither, dither... This is just stopgap workaround
    // anyway; we need a permanant solution.
    //
    if(DEBUG_FORCE_FIXED_CHUNKSIZE) maxChunkBits=initChunkBits;
    //if(DEBUG_FORCE_FIXED_CHUNKSIZE) initChunkBits=maxChunkBits;

    m_array = new char[16][];

    // Don't bite off more than we're prepared to swallow!
    if (initChunkBits > maxChunkBits)
      initChunkBits = maxChunkBits;

    m_chunkBits = initChunkBits;
    m_maxChunkBits = maxChunkBits;
    m_rebundleBits = rebundleBits;
    m_chunkSize = 1 << (initChunkBits);
    m_chunkMask = m_chunkSize - 1;
    m_array[0] = new char[m_chunkSize];
  }

  /**
   * Construct a FastStringBuffer, using a default rebundleBits value.
   *
   * <p>
   *  构造一个FastStringBuffer,使用默认的rebundleBits值。
   * 
   * 
   * NEEDSDOC @param initChunkBits
   * NEEDSDOC @param maxChunkBits
   */
  public FastStringBuffer(int initChunkBits, int maxChunkBits)
  {
    this(initChunkBits, maxChunkBits, 2);
  }

  /**
   * Construct a FastStringBuffer, using default maxChunkBits and
   * rebundleBits values.
   * <p>
   * ISSUE: Should this call assert initial size, or fixed size?
   * Now configured as initial, with a default for fixed.
   *
   * <p>
   *  构造一个FastStringBuffer,使用默认的maxChunkBits和rebundleBits值。
   * <p>
   *  问题：此调用是否应声明初始大小或固定大小?现在配置为初始,默认为固定。
   * 
   * 
   * NEEDSDOC @param initChunkBits
   */
  public FastStringBuffer(int initChunkBits)
  {
    this(initChunkBits, 15, 2);
  }

  /**
   * Construct a FastStringBuffer, using a default allocation policy.
   * <p>
   *  使用默认分配策略构造FastStringBuffer。
   * 
   */
  public FastStringBuffer()
  {

    // 10 bits is 1K. 15 bits is 32K. Remember that these are character
    // counts, so actual memory allocation unit is doubled for UTF-16 chars.
    //
    // For reference: In the original FastStringBuffer, we simply
    // overallocated by blocksize (default 1KB) on each buffer-growth.
    this(10, 15, 2);
  }

  /**
   * Get the length of the list. Synonym for length().
   *
   * <p>
   *  获取列表的长度。 length()的同义词。
   * 
   * 
   * @return the number of characters in the FastStringBuffer's content.
   */
  public final int size()
  {
    return (m_lastChunk << m_chunkBits) + m_firstFree;
  }

  /**
   * Get the length of the list. Synonym for size().
   *
   * <p>
   *  获取列表的长度。 size()的同义词。
   * 
   * 
   * @return the number of characters in the FastStringBuffer's content.
   */
  public final int length()
  {
    return (m_lastChunk << m_chunkBits) + m_firstFree;
  }

  /**
   * Discard the content of the FastStringBuffer, and most of the memory
   * that was allocated by it, restoring the initial state. Note that this
   * may eventually be different from setLength(0), which see.
   * <p>
   *  丢弃FastStringBuffer的内容,以及由它分配的大部分内存,恢复初始状态。注意,这可能最终不同于setLength(0),参见。
   * 
   */
  public final void reset()
  {

    m_lastChunk = 0;
    m_firstFree = 0;

    // Recover the original chunk size
    FastStringBuffer innermost = this;

    while (innermost.m_innerFSB != null)
    {
      innermost = innermost.m_innerFSB;
    }

    m_chunkBits = innermost.m_chunkBits;
    m_chunkSize = innermost.m_chunkSize;
    m_chunkMask = innermost.m_chunkMask;

    // Discard the hierarchy
    m_innerFSB = null;
    m_array = new char[16][0];
    m_array[0] = new char[m_chunkSize];
  }

  /**
   * Directly set how much of the FastStringBuffer's storage is to be
   * considered part of its content. This is a fast but hazardous
   * operation. It is not protected against negative values, or values
   * greater than the amount of storage currently available... and even
   * if additional storage does exist, its contents are unpredictable.
   * The only safe use for our setLength() is to truncate the FastStringBuffer
   * to a shorter string.
   *
   * <p>
   * 直接设置FastStringBuffer的存储量是多少被认为是其内容的一部分。这是一个快速但危险的操作。它不受负值保护,或者大于当前可用存储量的值...,即使存在额外存储,其内容也是不可预测的。
   *  setLength()的唯一安全用法是将FastStringBuffer截断为较短的字符串。
   * 
   * 
   * @param l New length. If l<0 or l>=getLength(), this operation will
   * not report an error but future operations will almost certainly fail.
   */
  public final void setLength(int l)
  {
    m_lastChunk = l >>> m_chunkBits;

    if (m_lastChunk == 0 && m_innerFSB != null)
    {
      // Replace this FSB with the appropriate inner FSB, truncated
      m_innerFSB.setLength(l, this);
    }
    else
    {
      m_firstFree = l & m_chunkMask;

          // There's an edge case if l is an exact multiple of m_chunkBits, which risks leaving
          // us pointing at the start of a chunk which has not yet been allocated. Rather than
          // pay the cost of dealing with that in the append loops (more scattered and more
          // inner-loop), we correct it here by moving to the safe side of that
          // line -- as we would have left the indexes had we appended up to that point.
      if(m_firstFree==0 && m_lastChunk>0)
      {
        --m_lastChunk;
        m_firstFree=m_chunkSize;
      }
    }
  }

  /**
   * Subroutine for the public setLength() method. Deals with the fact
   * that truncation may require restoring one of the innerFSBs
   *
   * <p>
   *  public setLength()方法的子例程。由于截断可能需要恢复一个内部FSB的事实
   * 
   * 
   * NEEDSDOC @param l
   * NEEDSDOC @param rootFSB
   */
  private final void setLength(int l, FastStringBuffer rootFSB)
  {

    m_lastChunk = l >>> m_chunkBits;

    if (m_lastChunk == 0 && m_innerFSB != null)
    {
      m_innerFSB.setLength(l, rootFSB);
    }
    else
    {

      // Undo encapsulation -- pop the innerFSB data back up to root.
      // Inefficient, but attempts to keep the code simple.
      rootFSB.m_chunkBits = m_chunkBits;
      rootFSB.m_maxChunkBits = m_maxChunkBits;
      rootFSB.m_rebundleBits = m_rebundleBits;
      rootFSB.m_chunkSize = m_chunkSize;
      rootFSB.m_chunkMask = m_chunkMask;
      rootFSB.m_array = m_array;
      rootFSB.m_innerFSB = m_innerFSB;
      rootFSB.m_lastChunk = m_lastChunk;

      // Finally, truncate this sucker.
      rootFSB.m_firstFree = l & m_chunkMask;
    }
  }

  /**
   * Note that this operation has been somewhat deoptimized by the shift to a
   * chunked array, as there is no factory method to produce a String object
   * directly from an array of arrays and hence a double copy is needed.
   * By using ensureCapacity we hope to minimize the heap overhead of building
   * the intermediate StringBuffer.
   * <p>
   * (It really is a pity that Java didn't design String as a final subclass
   * of MutableString, rather than having StringBuffer be a separate hierarchy.
   * We'd avoid a <strong>lot</strong> of double-buffering.)
   *
   * <p>
   *  注意,由于没有工厂方法直接从数组数组产生一个String对象,因此需要双重拷贝,所以这个操作已经通过转移到分块数组而被去优化。
   * 通过使用ensureCapacity,我们希望最小化构建中间StringBuffer的堆开销。
   * <p>
   *  (真的很遗憾,Java没有将String设计为MutableString的最后一个子类,而不是将StringBuffer作为一个单独的层次结构,我们可以避免<strong>很多</strong>的双缓
   * 冲。
   * 
   * 
   * @return the contents of the FastStringBuffer as a standard Java string.
   */
  public final String toString()
  {

    int length = (m_lastChunk << m_chunkBits) + m_firstFree;

    return getString(new StringBuffer(length), 0, 0, length).toString();
  }

  /**
   * Append a single character onto the FastStringBuffer, growing the
   * storage if necessary.
   * <p>
   * NOTE THAT after calling append(), previously obtained
   * references to m_array[][] may no longer be valid....
   * though in fact they should be in this instance.
   *
   * <p>
   *  将单个字符附加到FastStringBuffer上,如有必要,增加存储空间。
   * <p>
   *  注意在调用append()之后,以前获得的对m_array [] []的引用可能不再有效....虽然实际上它们应该在这种情况下。
   * 
   * 
   * @param value character to be appended.
   */
  public final void append(char value)
  {

    char[] chunk;

    // We may have preallocated chunks. If so, all but last should
    // be at full size.
    boolean lastchunk = (m_lastChunk + 1 == m_array.length);

    if (m_firstFree < m_chunkSize)  // Simplified test single-character-fits
      chunk = m_array[m_lastChunk];
    else
    {

      // Extend array?
      int i = m_array.length;

      if (m_lastChunk + 1 == i)
      {
        char[][] newarray = new char[i + 16][];

        System.arraycopy(m_array, 0, newarray, 0, i);

        m_array = newarray;
      }

      // Advance one chunk
      chunk = m_array[++m_lastChunk];

      if (chunk == null)
      {

        // Hierarchical encapsulation
        if (m_lastChunk == 1 << m_rebundleBits
                && m_chunkBits < m_maxChunkBits)
        {

          // Should do all the work of both encapsulating
          // existing data and establishing new sizes/offsets
          m_innerFSB = new FastStringBuffer(this);
        }

        // Add a chunk.
        chunk = m_array[m_lastChunk] = new char[m_chunkSize];
      }

      m_firstFree = 0;
    }

    // Space exists in the chunk. Append the character.
    chunk[m_firstFree++] = value;
  }

  /**
   * Append the contents of a String onto the FastStringBuffer,
   * growing the storage if necessary.
   * <p>
   * NOTE THAT after calling append(), previously obtained
   * references to m_array[] may no longer be valid.
   *
   * <p>
   *  将String的内容附加到FastStringBuffer,如果需要,增加存储。
   * <p>
   * 注意在调用append()之后,先前获得的对m_array []的引用可能不再有效。
   * 
   * 
   * @param value String whose contents are to be appended.
   */
  public final void append(String value)
  {

    if (value == null)
      return;
    int strlen = value.length();

    if (0 == strlen)
      return;

    int copyfrom = 0;
    char[] chunk = m_array[m_lastChunk];
    int available = m_chunkSize - m_firstFree;

    // Repeat while data remains to be copied
    while (strlen > 0)
    {

      // Copy what fits
      if (available > strlen)
        available = strlen;

      value.getChars(copyfrom, copyfrom + available, m_array[m_lastChunk],
                     m_firstFree);

      strlen -= available;
      copyfrom += available;

      // If there's more left, allocate another chunk and continue
      if (strlen > 0)
      {

        // Extend array?
        int i = m_array.length;

        if (m_lastChunk + 1 == i)
        {
          char[][] newarray = new char[i + 16][];

          System.arraycopy(m_array, 0, newarray, 0, i);

          m_array = newarray;
        }

        // Advance one chunk
        chunk = m_array[++m_lastChunk];

        if (chunk == null)
        {

          // Hierarchical encapsulation
          if (m_lastChunk == 1 << m_rebundleBits
                  && m_chunkBits < m_maxChunkBits)
          {

            // Should do all the work of both encapsulating
            // existing data and establishing new sizes/offsets
            m_innerFSB = new FastStringBuffer(this);
          }

          // Add a chunk.
          chunk = m_array[m_lastChunk] = new char[m_chunkSize];
        }

        available = m_chunkSize;
        m_firstFree = 0;
      }
    }

    // Adjust the insert point in the last chunk, when we've reached it.
    m_firstFree += available;
  }

  /**
   * Append the contents of a StringBuffer onto the FastStringBuffer,
   * growing the storage if necessary.
   * <p>
   * NOTE THAT after calling append(), previously obtained
   * references to m_array[] may no longer be valid.
   *
   * <p>
   *  将StringBuffer的内容附加到FastStringBuffer,如果需要,增加存储。
   * <p>
   *  注意在调用append()之后,先前获得的对m_array []的引用可能不再有效。
   * 
   * 
   * @param value StringBuffer whose contents are to be appended.
   */
  public final void append(StringBuffer value)
  {

    if (value == null)
      return;
    int strlen = value.length();

    if (0 == strlen)
      return;

    int copyfrom = 0;
    char[] chunk = m_array[m_lastChunk];
    int available = m_chunkSize - m_firstFree;

    // Repeat while data remains to be copied
    while (strlen > 0)
    {

      // Copy what fits
      if (available > strlen)
        available = strlen;

      value.getChars(copyfrom, copyfrom + available, m_array[m_lastChunk],
                     m_firstFree);

      strlen -= available;
      copyfrom += available;

      // If there's more left, allocate another chunk and continue
      if (strlen > 0)
      {

        // Extend array?
        int i = m_array.length;

        if (m_lastChunk + 1 == i)
        {
          char[][] newarray = new char[i + 16][];

          System.arraycopy(m_array, 0, newarray, 0, i);

          m_array = newarray;
        }

        // Advance one chunk
        chunk = m_array[++m_lastChunk];

        if (chunk == null)
        {

          // Hierarchical encapsulation
          if (m_lastChunk == 1 << m_rebundleBits
                  && m_chunkBits < m_maxChunkBits)
          {

            // Should do all the work of both encapsulating
            // existing data and establishing new sizes/offsets
            m_innerFSB = new FastStringBuffer(this);
          }

          // Add a chunk.
          chunk = m_array[m_lastChunk] = new char[m_chunkSize];
        }

        available = m_chunkSize;
        m_firstFree = 0;
      }
    }

    // Adjust the insert point in the last chunk, when we've reached it.
    m_firstFree += available;
  }

  /**
   * Append part of the contents of a Character Array onto the
   * FastStringBuffer,  growing the storage if necessary.
   * <p>
   * NOTE THAT after calling append(), previously obtained
   * references to m_array[] may no longer be valid.
   *
   * <p>
   *  将字符数组的部分内容附加到FastStringBuffer上,如有必要,增加存储空间。
   * <p>
   *  注意在调用append()之后,先前获得的对m_array []的引用可能不再有效。
   * 
   * 
   * @param chars character array from which data is to be copied
   * @param start offset in chars of first character to be copied,
   * zero-based.
   * @param length number of characters to be copied
   */
  public final void append(char[] chars, int start, int length)
  {

    int strlen = length;

    if (0 == strlen)
      return;

    int copyfrom = start;
    char[] chunk = m_array[m_lastChunk];
    int available = m_chunkSize - m_firstFree;

    // Repeat while data remains to be copied
    while (strlen > 0)
    {

      // Copy what fits
      if (available > strlen)
        available = strlen;

      System.arraycopy(chars, copyfrom, m_array[m_lastChunk], m_firstFree,
                       available);

      strlen -= available;
      copyfrom += available;

      // If there's more left, allocate another chunk and continue
      if (strlen > 0)
      {

        // Extend array?
        int i = m_array.length;

        if (m_lastChunk + 1 == i)
        {
          char[][] newarray = new char[i + 16][];

          System.arraycopy(m_array, 0, newarray, 0, i);

          m_array = newarray;
        }

        // Advance one chunk
        chunk = m_array[++m_lastChunk];

        if (chunk == null)
        {

          // Hierarchical encapsulation
          if (m_lastChunk == 1 << m_rebundleBits
                  && m_chunkBits < m_maxChunkBits)
          {

            // Should do all the work of both encapsulating
            // existing data and establishing new sizes/offsets
            m_innerFSB = new FastStringBuffer(this);
          }

          // Add a chunk.
          chunk = m_array[m_lastChunk] = new char[m_chunkSize];
        }

        available = m_chunkSize;
        m_firstFree = 0;
      }
    }

    // Adjust the insert point in the last chunk, when we've reached it.
    m_firstFree += available;
  }

  /**
   * Append the contents of another FastStringBuffer onto
   * this FastStringBuffer, growing the storage if necessary.
   * <p>
   * NOTE THAT after calling append(), previously obtained
   * references to m_array[] may no longer be valid.
   *
   * <p>
   *  将另一个FastStringBuffer的内容附加到此FastStringBuffer上,如有必要,增加存储空间。
   * <p>
   *  注意在调用append()之后,先前获得的对m_array []的引用可能不再有效。
   * 
   * 
   * @param value FastStringBuffer whose contents are
   * to be appended.
   */
  public final void append(FastStringBuffer value)
  {

    // Complicating factor here is that the two buffers may use
    // different chunk sizes, and even if they're the same we're
    // probably on a different alignment due to previously appended
    // data. We have to work through the source in bite-sized chunks.
    if (value == null)
      return;
    int strlen = value.length();

    if (0 == strlen)
      return;

    int copyfrom = 0;
    char[] chunk = m_array[m_lastChunk];
    int available = m_chunkSize - m_firstFree;

    // Repeat while data remains to be copied
    while (strlen > 0)
    {

      // Copy what fits
      if (available > strlen)
        available = strlen;

      int sourcechunk = (copyfrom + value.m_chunkSize - 1)
                        >>> value.m_chunkBits;
      int sourcecolumn = copyfrom & value.m_chunkMask;
      int runlength = value.m_chunkSize - sourcecolumn;

      if (runlength > available)
        runlength = available;

      System.arraycopy(value.m_array[sourcechunk], sourcecolumn,
                       m_array[m_lastChunk], m_firstFree, runlength);

      if (runlength != available)
        System.arraycopy(value.m_array[sourcechunk + 1], 0,
                         m_array[m_lastChunk], m_firstFree + runlength,
                         available - runlength);

      strlen -= available;
      copyfrom += available;

      // If there's more left, allocate another chunk and continue
      if (strlen > 0)
      {

        // Extend array?
        int i = m_array.length;

        if (m_lastChunk + 1 == i)
        {
          char[][] newarray = new char[i + 16][];

          System.arraycopy(m_array, 0, newarray, 0, i);

          m_array = newarray;
        }

        // Advance one chunk
        chunk = m_array[++m_lastChunk];

        if (chunk == null)
        {

          // Hierarchical encapsulation
          if (m_lastChunk == 1 << m_rebundleBits
                  && m_chunkBits < m_maxChunkBits)
          {

            // Should do all the work of both encapsulating
            // existing data and establishing new sizes/offsets
            m_innerFSB = new FastStringBuffer(this);
          }

          // Add a chunk.
          chunk = m_array[m_lastChunk] = new char[m_chunkSize];
        }

        available = m_chunkSize;
        m_firstFree = 0;
      }
    }

    // Adjust the insert point in the last chunk, when we've reached it.
    m_firstFree += available;
  }

  /**
  /* <p>
  /* 
   * @return true if the specified range of characters are all whitespace,
   * as defined by XMLCharacterRecognizer.
   * <p>
   * CURRENTLY DOES NOT CHECK FOR OUT-OF-RANGE.
   *
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   */
  public boolean isWhitespace(int start, int length)
  {

    int sourcechunk = start >>> m_chunkBits;
    int sourcecolumn = start & m_chunkMask;
    int available = m_chunkSize - sourcecolumn;
    boolean chunkOK;

    while (length > 0)
    {
      int runlength = (length <= available) ? length : available;

      if (sourcechunk == 0 && m_innerFSB != null)
        chunkOK = m_innerFSB.isWhitespace(sourcecolumn, runlength);
      else
        chunkOK = com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace(
          m_array[sourcechunk], sourcecolumn, runlength);

      if (!chunkOK)
        return false;

      length -= runlength;

      ++sourcechunk;

      sourcecolumn = 0;
      available = m_chunkSize;
    }

    return true;
  }

  /**
  /* <p>
  /* 
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   * @return a new String object initialized from the specified range of
   * characters.
   */
  public String getString(int start, int length)
  {
    int startColumn = start & m_chunkMask;
    int startChunk = start >>> m_chunkBits;
    if (startColumn + length < m_chunkMask && m_innerFSB == null) {
      return getOneChunkString(startChunk, startColumn, length);
    }
    return getString(new StringBuffer(length), startChunk, startColumn,
                     length).toString();
  }

  protected String getOneChunkString(int startChunk, int startColumn,
                                     int length) {
    return new String(m_array[startChunk], startColumn, length);
  }

  /**
  /* <p>
  /* 
   * @param sb StringBuffer to be appended to
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   * @return sb with the requested text appended to it
   */
  StringBuffer getString(StringBuffer sb, int start, int length)
  {
    return getString(sb, start >>> m_chunkBits, start & m_chunkMask, length);
  }

  /**
   * Internal support for toString() and getString().
   * PLEASE NOTE SIGNATURE CHANGE from earlier versions; it now appends into
   * and returns a StringBuffer supplied by the caller. This simplifies
   * m_innerFSB support.
   * <p>
   * Note that this operation has been somewhat deoptimized by the shift to a
   * chunked array, as there is no factory method to produce a String object
   * directly from an array of arrays and hence a double copy is needed.
   * By presetting length we hope to minimize the heap overhead of building
   * the intermediate StringBuffer.
   * <p>
   * (It really is a pity that Java didn't design String as a final subclass
   * of MutableString, rather than having StringBuffer be a separate hierarchy.
   * We'd avoid a <strong>lot</strong> of double-buffering.)
   *
   *
   * <p>
   *  toString()和getString()的内部支持。请注意早期版本的签名更改;它现在追加到并返回由调用者提供的StringBuffer。这简化了m_innerFSB支持。
   * <p>
   *  注意,由于没有工厂方法直接从数组数组产生一个String对象,因此需要双重拷贝,所以这个操作已经通过转移到分块数组而被去优化。通过预设长度,我们希望最小化构建中间StringBuffer的堆开销。
   * <p>
   *  (真的很遗憾,Java没有将String设计为MutableString的最后一个子类,而不是将StringBuffer作为一个单独的层次结构,我们可以避免<strong>很多</strong>的双缓
   * 冲。
   * 
   * 
   * @param sb
   * @param startChunk
   * @param startColumn
   * @param length
   *
   * @return the contents of the FastStringBuffer as a standard Java string.
   */
  StringBuffer getString(StringBuffer sb, int startChunk, int startColumn,
                         int length)
  {

    int stop = (startChunk << m_chunkBits) + startColumn + length;
    int stopChunk = stop >>> m_chunkBits;
    int stopColumn = stop & m_chunkMask;

    // Factored out
    //StringBuffer sb=new StringBuffer(length);
    for (int i = startChunk; i < stopChunk; ++i)
    {
      if (i == 0 && m_innerFSB != null)
        m_innerFSB.getString(sb, startColumn, m_chunkSize - startColumn);
      else
        sb.append(m_array[i], startColumn, m_chunkSize - startColumn);

      startColumn = 0;  // after first chunk
    }

    if (stopChunk == 0 && m_innerFSB != null)
      m_innerFSB.getString(sb, startColumn, stopColumn - startColumn);
    else if (stopColumn > startColumn)
      sb.append(m_array[stopChunk], startColumn, stopColumn - startColumn);

    return sb;
  }

  /**
   * Get a single character from the string buffer.
   *
   *
   * <p>
   * 从字符串缓冲区获取单个字符。
   * 
   * 
   * @param pos character position requested.
   * @return A character from the requested position.
   */
  public char charAt(int pos)
  {
    int startChunk = pos >>> m_chunkBits;

    if (startChunk == 0 && m_innerFSB != null)
      return m_innerFSB.charAt(pos & m_chunkMask);
    else
      return m_array[startChunk][pos & m_chunkMask];
  }

  /**
   * Sends the specified range of characters as one or more SAX characters()
   * events.
   * Note that the buffer reference passed to the ContentHandler may be
   * invalidated if the FastStringBuffer is edited; it's the user's
   * responsibility to manage access to the FastStringBuffer to prevent this
   * problem from arising.
   * <p>
   * Note too that there is no promise that the output will be sent as a
   * single call. As is always true in SAX, one logical string may be split
   * across multiple blocks of memory and hence delivered as several
   * successive events.
   *
   * <p>
   *  将指定的字符范围作为一个或多个SAX字符()事件发送。
   * 请注意,如果编辑FastStringBuffer,则传递给ContentHandler的缓冲区引用可能会失效;管理对FastStringBuffer的访问以防止出现此问题是用户的责任。
   * <p>
   *  还要注意,没有承诺输出将作为单个调用发送。在SAX中总是如此,一个逻辑字符串可以分割在多个存储器块上,并且因此作为若干连续事件传送。
   * 
   * 
   * @param ch SAX ContentHandler object to receive the event.
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   * @exception org.xml.sax.SAXException may be thrown by handler's
   * characters() method.
   */
  public void sendSAXcharacters(
          org.xml.sax.ContentHandler ch, int start, int length)
            throws org.xml.sax.SAXException
  {

    int startChunk = start >>> m_chunkBits;
    int startColumn = start & m_chunkMask;
    if (startColumn + length < m_chunkMask && m_innerFSB == null) {
        ch.characters(m_array[startChunk], startColumn, length);
        return;
    }

    int stop = start + length;
    int stopChunk = stop >>> m_chunkBits;
    int stopColumn = stop & m_chunkMask;

    for (int i = startChunk; i < stopChunk; ++i)
    {
      if (i == 0 && m_innerFSB != null)
        m_innerFSB.sendSAXcharacters(ch, startColumn,
                                     m_chunkSize - startColumn);
      else
        ch.characters(m_array[i], startColumn, m_chunkSize - startColumn);

      startColumn = 0;  // after first chunk
    }

    // Last, or only, chunk
    if (stopChunk == 0 && m_innerFSB != null)
      m_innerFSB.sendSAXcharacters(ch, startColumn, stopColumn - startColumn);
    else if (stopColumn > startColumn)
    {
      ch.characters(m_array[stopChunk], startColumn,
                    stopColumn - startColumn);
    }
  }

  /**
   * Sends the specified range of characters as one or more SAX characters()
   * events, normalizing the characters according to XSLT rules.
   *
   * <p>
   *  将指定的字符范围作为一个或多个SAX字符()事件发送,根据XSLT规则对字符进行规范化。
   * 
   * 
   * @param ch SAX ContentHandler object to receive the event.
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   * @return normalization status to apply to next chunk (because we may
   * have been called recursively to process an inner FSB):
   * <dl>
   * <dt>0</dt>
   * <dd>if this output did not end in retained whitespace, and thus whitespace
   * at the start of the following chunk (if any) should be converted to a
   * single space.
   * <dt>SUPPRESS_LEADING_WS</dt>
   * <dd>if this output ended in retained whitespace, and thus whitespace
   * at the start of the following chunk (if any) should be completely
   * suppressed.</dd>
   * </dd>
   * </dl>
   * @exception org.xml.sax.SAXException may be thrown by handler's
   * characters() method.
   */
  public int sendNormalizedSAXcharacters(
          org.xml.sax.ContentHandler ch, int start, int length)
            throws org.xml.sax.SAXException
  {
        // This call always starts at the beginning of the
    // string being written out, either because it was called directly or
    // because it was an m_innerFSB recursion. This is important since
        // it gives us a well-known initial state for this flag:
        int stateForNextChunk=SUPPRESS_LEADING_WS;

    int stop = start + length;
    int startChunk = start >>> m_chunkBits;
    int startColumn = start & m_chunkMask;
    int stopChunk = stop >>> m_chunkBits;
    int stopColumn = stop & m_chunkMask;

    for (int i = startChunk; i < stopChunk; ++i)
    {
      if (i == 0 && m_innerFSB != null)
                                stateForNextChunk=
        m_innerFSB.sendNormalizedSAXcharacters(ch, startColumn,
                                     m_chunkSize - startColumn);
      else
                                stateForNextChunk=
        sendNormalizedSAXcharacters(m_array[i], startColumn,
                                    m_chunkSize - startColumn,
                                                                                                                                                ch,stateForNextChunk);

      startColumn = 0;  // after first chunk
    }

    // Last, or only, chunk
    if (stopChunk == 0 && m_innerFSB != null)
                        stateForNextChunk= // %REVIEW% Is this update really needed?
      m_innerFSB.sendNormalizedSAXcharacters(ch, startColumn, stopColumn - startColumn);
    else if (stopColumn > startColumn)
    {
                        stateForNextChunk= // %REVIEW% Is this update really needed?
      sendNormalizedSAXcharacters(m_array[stopChunk],
                                                                                                                                        startColumn, stopColumn - startColumn,
                                                                                                                                        ch, stateForNextChunk | SUPPRESS_TRAILING_WS);
    }
                return stateForNextChunk;
  }

  static final char[] SINGLE_SPACE = {' '};

  /**
   * Internal method to directly normalize and dispatch the character array.
   * This version is aware of the fact that it may be called several times
   * in succession if the data is made up of multiple "chunks", and thus
   * must actively manage the handling of leading and trailing whitespace.
   *
   * Note: The recursion is due to the possible recursion of inner FSBs.
   *
   * <p>
   *  内部方法直接规范和调度字符数组。该版本意识到如果数据由多个"块"组成,并且因此必须主动地管理前导和尾随空白的处理,则它可以被连续地多次调用的事实。
   * 
   *  注意：递归是由于内部FSB的可能递归。
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @param handler SAX ContentHandler object to receive the event.
   * @param edgeTreatmentFlags How leading/trailing spaces should be handled.
   * This is a bitfield contining two flags, bitwise-ORed together:
   * <dl>
   * <dt>SUPPRESS_LEADING_WS</dt>
   * <dd>When false, causes leading whitespace to be converted to a single
   * space; when true, causes it to be discarded entirely.
   * Should be set TRUE for the first chunk, and (in multi-chunk output)
   * whenever the previous chunk ended in retained whitespace.</dd>
   * <dt>SUPPRESS_TRAILING_WS</dt>
   * <dd>When false, causes trailing whitespace to be converted to a single
   * space; when true, causes it to be discarded entirely.
   * Should be set TRUE for the last or only chunk.
   * </dd>
   * </dl>
   * @return normalization status, as in the edgeTreatmentFlags parameter:
   * <dl>
   * <dt>0</dt>
   * <dd>if this output did not end in retained whitespace, and thus whitespace
   * at the start of the following chunk (if any) should be converted to a
   * single space.
   * <dt>SUPPRESS_LEADING_WS</dt>
   * <dd>if this output ended in retained whitespace, and thus whitespace
   * at the start of the following chunk (if any) should be completely
   * suppressed.</dd>
   * </dd>
   * </dl>
   *
   *
   * @exception org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  static int sendNormalizedSAXcharacters(char ch[],
             int start, int length,
             org.xml.sax.ContentHandler handler,
                                                 int edgeTreatmentFlags)
          throws org.xml.sax.SAXException
  {
     boolean processingLeadingWhitespace =
                       ((edgeTreatmentFlags & SUPPRESS_LEADING_WS) != 0);
     boolean seenWhitespace = ((edgeTreatmentFlags & CARRY_WS) != 0);
     boolean suppressTrailingWhitespace =
                       ((edgeTreatmentFlags & SUPPRESS_TRAILING_WS) != 0);
     int currPos = start;
     int limit = start+length;

     // Strip any leading spaces first, if required
     if (processingLeadingWhitespace) {
         for (; currPos < limit
                && XMLCharacterRecognizer.isWhiteSpace(ch[currPos]);
              currPos++) { }

         // If we've only encountered leading spaces, the
         // current state remains unchanged
         if (currPos == limit) {
             return edgeTreatmentFlags;
         }
     }

     // If we get here, there are no more leading spaces to strip
     while (currPos < limit) {
         int startNonWhitespace = currPos;

         // Grab a chunk of non-whitespace characters
         for (; currPos < limit
                && !XMLCharacterRecognizer.isWhiteSpace(ch[currPos]);
              currPos++) { }

         // Non-whitespace seen - emit them, along with a single
         // space for any preceding whitespace characters
         if (startNonWhitespace != currPos) {
             if (seenWhitespace) {
                 handler.characters(SINGLE_SPACE, 0, 1);
                 seenWhitespace = false;
             }
             handler.characters(ch, startNonWhitespace,
                                currPos - startNonWhitespace);
         }

         int startWhitespace = currPos;

         // Consume any whitespace characters
         for (; currPos < limit
                && XMLCharacterRecognizer.isWhiteSpace(ch[currPos]);
              currPos++) { }

         if (startWhitespace != currPos) {
             seenWhitespace = true;
         }
     }

     return (seenWhitespace ? CARRY_WS : 0)
            | (edgeTreatmentFlags & SUPPRESS_TRAILING_WS);
  }

  /**
   * Directly normalize and dispatch the character array.
   *
   * <p>
   *  直接规范和分派字符数组。
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @param handler SAX ContentHandler object to receive the event.
   * @exception org.xml.sax.SAXException Any SAX exception, possibly
   *            wrapping another exception.
   */
  public static void sendNormalizedSAXcharacters(char ch[],
             int start, int length,
             org.xml.sax.ContentHandler handler)
          throws org.xml.sax.SAXException
  {
                sendNormalizedSAXcharacters(ch, start, length,
             handler, SUPPRESS_BOTH);
        }

        /**
   * Sends the specified range of characters as sax Comment.
   * <p>
   * Note that, unlike sendSAXcharacters, this has to be done as a single
   * call to LexicalHandler#comment.
   *
   * <p>
   *  将指定的字符范围作为sax注释发送。
   * <p>
   *  注意,与sendSAXcharacters不同,这必须作为对LexicalHandler#comment的单个调用来完成。
   * 
   * 
   * @param ch SAX LexicalHandler object to receive the event.
   * @param start Offset of first character in the range.
   * @param length Number of characters to send.
   * @exception org.xml.sax.SAXException may be thrown by handler's
   * characters() method.
   */
  public void sendSAXComment(
          org.xml.sax.ext.LexicalHandler ch, int start, int length)
            throws org.xml.sax.SAXException
  {

    // %OPT% Do it this way for now...
    String comment = getString(start, length);
    ch.comment(comment.toCharArray(), 0, length);
  }

  /**
   * Copies characters from this string into the destination character
   * array.
   *
   * <p>
   *  将字符串从此字符串复制到目标字符数组中。
   * 
   * 
   * @param      srcBegin   index of the first character in the string
   *                        to copy.
   * @param      srcEnd     index after the last character in the string
   *                        to copy.
   * @param      dst        the destination array.
   * @param      dstBegin   the start offset in the destination array.
   * @exception IndexOutOfBoundsException If any of the following
   *            is true:
   *            <ul><li><code>srcBegin</code> is negative.
   *            <li><code>srcBegin</code> is greater than <code>srcEnd</code>
   *            <li><code>srcEnd</code> is greater than the length of this
   *                string
   *            <li><code>dstBegin</code> is negative
   *            <li><code>dstBegin+(srcEnd-srcBegin)</code> is larger than
   *                <code>dst.length</code></ul>
   * @exception NullPointerException if <code>dst</code> is <code>null</code>
   */
  private void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin)
  {
    // %TBD% Joe needs to write this function.  Make public when implemented.
  }

  /**
   * Encapsulation c'tor. After this is called, the source FastStringBuffer
   * will be reset to use the new object as its m_innerFSB, and will have
   * had its chunk size reset appropriately. IT SHOULD NEVER BE CALLED
   * EXCEPT WHEN source.length()==1<<(source.m_chunkBits+source.m_rebundleBits)
   *
   * <p>
   * 封装c'tor。调用此函数之后,源FastStringBuffer将被重置为使用新对象作为其m_innerFSB,并且将已适当地重置其块大小。
   * 除了source.length()== 1 <<(source.m_chunkBits + source.m_rebundleBits),它不应该被调用除外。
   * 
   * NEEDSDOC @param source
   */
  private FastStringBuffer(FastStringBuffer source)
  {

    // Copy existing information into new encapsulation
    m_chunkBits = source.m_chunkBits;
    m_maxChunkBits = source.m_maxChunkBits;
    m_rebundleBits = source.m_rebundleBits;
    m_chunkSize = source.m_chunkSize;
    m_chunkMask = source.m_chunkMask;
    m_array = source.m_array;
    m_innerFSB = source.m_innerFSB;

    // These have to be adjusted because we're calling just at the time
    // when we would be about to allocate another chunk
    m_lastChunk = source.m_lastChunk - 1;
    m_firstFree = source.m_chunkSize;

    // Establish capsule as the Inner FSB, reset chunk sizes/addressing
    source.m_array = new char[16][];
    source.m_innerFSB = this;

    // Since we encapsulated just as we were about to append another
    // chunk, return ready to create the chunk after the innerFSB
    // -- 1, not 0.
    source.m_lastChunk = 1;
    source.m_firstFree = 0;
    source.m_chunkBits += m_rebundleBits;
    source.m_chunkSize = 1 << (source.m_chunkBits);
    source.m_chunkMask = source.m_chunkSize - 1;
  }
}
