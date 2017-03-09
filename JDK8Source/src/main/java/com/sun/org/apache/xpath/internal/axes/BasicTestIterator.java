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
 * $Id: BasicTestIterator.java,v 1.2.4.1 2005/09/14 19:45:20 jeffsuttor Exp $
 * <p>
 *  $ Id：BasicTestIterator.java,v 1.2.4.1 2005/09/14 19:45:20 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMFilter;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.compiler.OpMap;

/**
 * Base for iterators that handle predicates.  Does the basic next
 * node logic, so all the derived iterator has to do is get the
 * next node.
 * <p>
 *  用于处理谓词的迭代器的基础。基本的下一个节点逻辑,所以派生迭代器必须做的是获得下一个节点。
 * 
 */
public abstract class BasicTestIterator extends LocPathIterator
{
    static final long serialVersionUID = 3505378079378096623L;
  /**
   * Create a LocPathIterator object.
   *
   * <p>
   *  创建LocPathIterator对象。
   * 
   * 
   * @param nscontext The namespace context for this iterator,
   * should be OK if null.
   */
  protected BasicTestIterator()
  {
  }


  /**
   * Create a LocPathIterator object.
   *
   * <p>
   *  创建LocPathIterator对象。
   * 
   * 
   * @param nscontext The namespace context for this iterator,
   * should be OK if null.
   */
  protected BasicTestIterator(PrefixResolver nscontext)
  {

    super(nscontext);
  }

  /**
   * Create a LocPathIterator object, including creation
   * of step walkers from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   *  创建LocPathIterator对象,包括从操作码列表创建步骤步行器,并回调到编译器以创建谓词表达式。
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected BasicTestIterator(Compiler compiler, int opPos, int analysis)
          throws javax.xml.transform.TransformerException
  {
    super(compiler, opPos, analysis, false);

    int firstStepPos = OpMap.getFirstChildPos(opPos);
    int whatToShow = compiler.getWhatToShow(firstStepPos);

    if ((0 == (whatToShow
               & (DTMFilter.SHOW_ATTRIBUTE
               | DTMFilter.SHOW_NAMESPACE
               | DTMFilter.SHOW_ELEMENT
               | DTMFilter.SHOW_PROCESSING_INSTRUCTION)))
               || (whatToShow == DTMFilter.SHOW_ALL))
      initNodeTest(whatToShow);
    else
    {
      initNodeTest(whatToShow, compiler.getStepNS(firstStepPos),
                              compiler.getStepLocalName(firstStepPos));
    }
    initPredicateInfo(compiler, firstStepPos);
  }

  /**
   * Create a LocPathIterator object, including creation
   * of step walkers from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   *  创建LocPathIterator对象,包括从操作码列表创建步骤步行器,并回调到编译器以创建谓词表达式。
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
  protected BasicTestIterator(
          Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
            throws javax.xml.transform.TransformerException
  {
    super(compiler, opPos, analysis, shouldLoadWalkers);
  }


  /**
   * Get the next node via getNextXXX.  Bottlenecked for derived class override.
   * <p>
   *  通过getNextXXX获取下一个节点。瓶颈为派生类覆盖。
   * 
   * 
   * @return The next node on the axis, or DTM.NULL.
   */
  protected abstract int getNextNode();

  /**
   *  Returns the next node in the set and advances the position of the
   * iterator in the set. After a NodeIterator is created, the first call
   * to nextNode() returns the first node in the set.
   *
   * <p>
   * 返回集合中的下一个节点,并推进迭代器在集合中的位置。创建NodeIterator之后,第一次调用nextNode()返回集合中的第一个节点。
   * 
   * 
   * @return  The next <code>Node</code> in the set being iterated over, or
   *   <code>null</code> if there are no more members in that set.
   */
  public int nextNode()
  {
        if(m_foundLast)
        {
                m_lastFetched = DTM.NULL;
                return DTM.NULL;
        }

    if(DTM.NULL == m_lastFetched)
    {
      resetProximityPositions();
    }

    int next;

    com.sun.org.apache.xpath.internal.VariableStack vars;
    int savedStart;
    if (-1 != m_stackFrame)
    {
      vars = m_execContext.getVarStack();

      // These three statements need to be combined into one operation.
      savedStart = vars.getStackFrame();

      vars.setStackFrame(m_stackFrame);
    }
    else
    {
      // Yuck.  Just to shut up the compiler!
      vars = null;
      savedStart = 0;
    }

    try
    {
      do
      {
        next = getNextNode();

        if (DTM.NULL != next)
        {
          if(DTMIterator.FILTER_ACCEPT == acceptNode(next))
            break;
          else
            continue;
        }
        else
          break;
      }
      while (next != DTM.NULL);

      if (DTM.NULL != next)
      {
        m_pos++;
        return next;
      }
      else
      {
        m_foundLast = true;

        return DTM.NULL;
      }
    }
    finally
    {
      if (-1 != m_stackFrame)
      {
        // These two statements need to be combined into one operation.
        vars.setStackFrame(savedStart);
      }
    }
  }

  /**
   *  Get a cloned Iterator that is reset to the beginning
   *  of the query.
   *
   * <p>
   *  获取复位到查询开头的克隆迭代器。
   * 
   *  @return A cloned NodeIterator set of the start of the query.
   *
   *  @throws CloneNotSupportedException
   */
  public DTMIterator cloneWithReset() throws CloneNotSupportedException
  {

    ChildTestIterator clone = (ChildTestIterator) super.cloneWithReset();

    clone.resetProximityPositions();

    return clone;
  }


}
