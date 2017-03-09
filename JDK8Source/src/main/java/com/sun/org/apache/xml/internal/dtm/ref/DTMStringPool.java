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
 * $Id: DTMStringPool.java,v 1.2.4.1 2005/09/15 08:15:05 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMStringPool.java,v 1.2.4.1 2005/09/15 08:15:05 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref;

import java.util.Vector;

import com.sun.org.apache.xml.internal.utils.IntVector;

/** <p>DTMStringPool is an "interning" mechanism for strings. It will
 * create a stable 1:1 mapping between a set of string values and a set of
 * integer index values, so the integers can be used to reliably and
 * uniquely identify (and when necessary retrieve) the strings.</p>
 *
 * <p>Design Priorities:
 * <ul>
 * <li>String-to-index lookup speed is critical.</li>
 * <li>Index-to-String lookup speed is slightly less so.</li>
 * <li>Threadsafety is not guaranteed at this level.
 * Enforce that in the application if needed.</li>
 * <li>Storage efficiency is an issue but not a huge one.
 * It is expected that string pools won't exceed about 2000 entries.</li>
 * </ul>
 * </p>
 *
 * <p>Implementation detail: A standard Hashtable is relatively
 * inefficient when looking up primitive int values, especially when
 * we're already maintaining an int-to-string vector.  So I'm
 * maintaining a simple hash chain within this class.</p>
 *
 * <p>NOTE: There is nothing in the code that has a real dependency upon
 * String. It would work with any object type that implements reliable
 * .hashCode() and .equals() operations. The API enforces Strings because
 * it's safer that way, but this could trivially be turned into a general
 * ObjectPool if one was needed.</p>
 *
 * <p>Status: Passed basic test in main().</p>
 * <p>
 *  在一组字符串值和一组整数索引值之间创建稳定的1：1映射,因此可以使用整数可靠且唯一地标识(以及必要时检索)字符串。</p>
 * 
 *  <p>设计重点：
 * <ul>
 *  <li>字符串到索引的查找速度至关重要。</li> <li>索引到字符串的查找速度稍微小一些。</li> <li> Threadsafety在这个级别不能保证。如果需要,请在应用程序中强制执行。
 * </li> <li>存储效率是一个问题,但不是一个很大的问题。预期字符串池不会超过约2000个条目。</li>。
 * </ul>
 * </p>
 * 
 * <p>实现细节：当查找原始int值时,标准的Hashtable是相对低效的,尤其是当我们已经维护一个int到字符串的向量。所以我在这个类中维护一个简单的哈希链。</p>
 * 
 *  <p>注意：代码中没有什么与String有真正的依赖关系。它将与实现可靠的.hashCode()和.equals()操作的任何对象类型一起工作。
 * 
 * */
public class DTMStringPool
{
  Vector m_intToString;
  static final int HASHPRIME=101;
  int[] m_hashStart=new int[HASHPRIME];
  IntVector m_hashChain;
  public static final int NULL=-1;

  /**
   * Create a DTMStringPool using the given chain size
   *
   * <p>
   *  API强制使用Strings,因为它更安全,但是如果需要的话,这可能会变成一个一般的ObjectPool。</p>。
   * 
   *  <p>状态：在main()中通过基本测试。</p>
   * 
   * 
   * @param chainSize The size of the hash chain vector
   */
  public DTMStringPool(int chainSize)
    {
      m_intToString=new Vector();
      m_hashChain=new IntVector(chainSize);
      removeAllElements();

      // -sb Add this to force empty strings to be index 0.
      stringToIndex("");
    }

  public DTMStringPool()
    {
      this(512);
    }

  public void removeAllElements()
    {
      m_intToString.removeAllElements();
      for(int i=0;i<HASHPRIME;++i)
        m_hashStart[i]=NULL;
      m_hashChain.removeAllElements();
    }

  /** @return string whose value is uniquely identified by this integer index.
  /* <p>
  /*  使用给定的链大小创建DTMStringPool
  /* 
  /* 
   * @throws java.lang.ArrayIndexOutOfBoundsException
   *  if index doesn't map to a string.
   * */
  public String indexToString(int i)
    throws java.lang.ArrayIndexOutOfBoundsException
    {
      if(i==NULL) return null;
      return (String) m_intToString.elementAt(i);
    }

  /** @return integer index uniquely identifying the value of this string. */
  public int stringToIndex(String s)
    {
      if(s==null) return NULL;

      int hashslot=s.hashCode()%HASHPRIME;
      if(hashslot<0) hashslot=-hashslot;

      // Is it one we already know?
      int hashlast=m_hashStart[hashslot];
      int hashcandidate=hashlast;
      while(hashcandidate!=NULL)
        {
          if(m_intToString.elementAt(hashcandidate).equals(s))
            return hashcandidate;

          hashlast=hashcandidate;
          hashcandidate=m_hashChain.elementAt(hashcandidate);
        }

      // New value. Add to tables.
      int newIndex=m_intToString.size();
      m_intToString.addElement(s);

      m_hashChain.addElement(NULL);     // Initialize to no-following-same-hash
      if(hashlast==NULL)  // First for this hash
        m_hashStart[hashslot]=newIndex;
      else // Link from previous with same hash
        m_hashChain.setElementAt(newIndex,hashlast);

      return newIndex;
    }

  /** Command-line unit test driver. This test relies on the fact that
   * this version of the pool assigns indices consecutively, starting
   * from zero, as new unique strings are encountered.
   * <p>
   */
  public static void _main(String[] args)
  {
    String[] word={
      "Zero","One","Two","Three","Four","Five",
      "Six","Seven","Eight","Nine","Ten",
      "Eleven","Twelve","Thirteen","Fourteen","Fifteen",
      "Sixteen","Seventeen","Eighteen","Nineteen","Twenty",
      "Twenty-One","Twenty-Two","Twenty-Three","Twenty-Four",
      "Twenty-Five","Twenty-Six","Twenty-Seven","Twenty-Eight",
      "Twenty-Nine","Thirty","Thirty-One","Thirty-Two",
      "Thirty-Three","Thirty-Four","Thirty-Five","Thirty-Six",
      "Thirty-Seven","Thirty-Eight","Thirty-Nine"};

    DTMStringPool pool=new DTMStringPool();

    System.out.println("If no complaints are printed below, we passed initial test.");

    for(int pass=0;pass<=1;++pass)
      {
        int i;

        for(i=0;i<word.length;++i)
          {
            int j=pool.stringToIndex(word[i]);
            if(j!=i)
              System.out.println("\tMismatch populating pool: assigned "+
                                 j+" for create "+i);
          }

        for(i=0;i<word.length;++i)
          {
            int j=pool.stringToIndex(word[i]);
            if(j!=i)
              System.out.println("\tMismatch in stringToIndex: returned "+
                                 j+" for lookup "+i);
          }

        for(i=0;i<word.length;++i)
          {
            String w=pool.indexToString(i);
            if(!word[i].equals(w))
              System.out.println("\tMismatch in indexToString: returned"+
                                 w+" for lookup "+i);
          }

        pool.removeAllElements();

        System.out.println("\nPass "+pass+" complete\n");
      } // end pass loop
  }
}
