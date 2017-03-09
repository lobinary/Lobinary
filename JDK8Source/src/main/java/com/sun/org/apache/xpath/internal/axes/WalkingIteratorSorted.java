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
 * $Id: WalkingIteratorSorted.java,v 1.2.4.1 2005/09/14 19:45:23 jeffsuttor Exp $
 * <p>
 *  $ Id：WalkingIteratorSorted.java,v 1.2.4.1 2005/09/14 19:45:23 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.Axis;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xpath.internal.compiler.Compiler;

/**
 * This class iterates over set of nodes that needs to be sorted.
 * @xsl.usage internal
 * <p>
 *  此类遍历需要排序的节点集。 @ xsl.usage internal
 * 
 */
public class WalkingIteratorSorted extends WalkingIterator
{
    static final long serialVersionUID = -4512512007542368213L;

//  /** True if the nodes will be found in document order */
//  protected boolean m_inNaturalOrder = false;

  /** True if the nodes will be found in document order, and this can
  /* <p>
  /* 
   * be determined statically. */
  protected boolean m_inNaturalOrderStatic = false;

  /**
   * Create a WalkingIteratorSorted object.
   *
   * <p>
   *  创建一个WalkingIteratorSorted对象。
   * 
   * 
   * @param nscontext The namespace context for this iterator,
   * should be OK if null.
   */
  public WalkingIteratorSorted(PrefixResolver nscontext)
  {
    super(nscontext);
  }

  /**
   * Create a WalkingIterator iterator, including creation
   * of step walkers from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   *  创建一个WalkingIterator迭代器,包括从操作码列表创建步骤步行器,并回调到编译器以创建谓词表达式。
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   * @param shouldLoadWalkers True if walkers should be
   * loaded, or false if this is a derived iterator and
   * it doesn't wish to load child walkers.
   *
   * @throws javax.xml.transform.TransformerException
   */
  WalkingIteratorSorted(
          Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
            throws javax.xml.transform.TransformerException
  {
    super(compiler, opPos, analysis, shouldLoadWalkers);
  }

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   *
   * <p>
   *  如果迭代中的所有节点都以文档顺序返回,则返回true。
   * 
   * 
   * @return true as a default.
   */
  public boolean isDocOrdered()
  {
    return m_inNaturalOrderStatic;
  }


  /**
   * Tell if the nodeset can be walked in doc order, via static analysis.
   *
   *
   * <p>
   *  告诉节点集是否可以通过静态分析在文档顺序中走。
   * 
   * 
   * @return true if the nodeset can be walked in doc order, without sorting.
   */
  boolean canBeWalkedInNaturalDocOrderStatic()
  {

    if (null != m_firstWalker)
    {
      AxesWalker walker = m_firstWalker;
      int prevAxis = -1;
      boolean prevIsSimpleDownAxis = true;

      for(int i = 0; null != walker; i++)
      {
        int axis = walker.getAxis();

        if(walker.isDocOrdered())
        {
          boolean isSimpleDownAxis = ((axis == Axis.CHILD)
                                   || (axis == Axis.SELF)
                                   || (axis == Axis.ROOT));
          // Catching the filtered list here is only OK because
          // FilterExprWalker#isDocOrdered() did the right thing.
          if(isSimpleDownAxis || (axis == -1))
            walker = walker.getNextWalker();
          else
          {
            boolean isLastWalker = (null == walker.getNextWalker());
            if(isLastWalker)
            {
              if(walker.isDocOrdered() && (axis == Axis.DESCENDANT ||
                 axis == Axis.DESCENDANTORSELF || axis == Axis.DESCENDANTSFROMROOT
                 || axis == Axis.DESCENDANTSORSELFFROMROOT) || (axis == Axis.ATTRIBUTE))
                return true;
            }
            return false;
          }
        }
        else
          return false;
      }
      return true;
    }
    return false;
  }


//  /**
//   * NEEDSDOC Method canBeWalkedInNaturalDocOrder
//   *
//   *
//   * <p>
//   *  // * NEEDSDOC方法canBeWalkedInNaturalDocOrder // * // *
//   * 
//   * 
//   * NEEDSDOC (canBeWalkedInNaturalDocOrder) @return
//   */
//  boolean canBeWalkedInNaturalDocOrder()
//  {
//
//    if (null != m_firstWalker)
//    {
//      AxesWalker walker = m_firstWalker;
//      int prevAxis = -1;
//      boolean prevIsSimpleDownAxis = true;
//
//      for(int i = 0; null != walker; i++)
//      {
//        int axis = walker.getAxis();
//
//        if(walker.isDocOrdered())
//        {
//          boolean isSimpleDownAxis = ((axis == Axis.CHILD)
//                                   || (axis == Axis.SELF)
//                                   || (axis == Axis.ROOT));
//          // Catching the filtered list here is only OK because
//          // FilterExprWalker#isDocOrdered() did the right thing.
//          if(isSimpleDownAxis || (axis == -1))
//            walker = walker.getNextWalker();
//          else
//          {
//            boolean isLastWalker = (null == walker.getNextWalker());
//            if(isLastWalker)
//            {
//              if(walker.isDocOrdered() && (axis == Axis.DESCENDANT ||
//                 axis == Axis.DESCENDANTORSELF || axis == Axis.DESCENDANTSFROMROOT
//                 || axis == Axis.DESCENDANTSORSELFFROMROOT) || (axis == Axis.ATTRIBUTE))
//                return true;
//            }
//            return false;
//          }
//        }
//        else
//          return false;
//      }
//      return true;
//    }
//    return false;
//  }

  /**
   * This function is used to perform some extra analysis of the iterator.
   *
   * <p>
   *  此函数用于对迭代器执行一些额外的分析。
   * 
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    super.fixupVariables(vars, globalsSize);

    int analysis = getAnalysisBits();
    if(WalkerFactory.isNaturalDocOrder(analysis))
    {
        m_inNaturalOrderStatic = true;
    }
    else
    {
        m_inNaturalOrderStatic = false;
        // System.out.println("Setting natural doc order to false: "+
        //    WalkerFactory.getAnalysisString(analysis));
    }

  }

}
