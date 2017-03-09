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
 * $Id: UnionChildIterator.java,v 1.2.4.1 2005/09/14 19:45:20 jeffsuttor Exp $
 * <p>
 *  $ Id：UnionChildIterator.java,v 1.2.4.1 2005/09/14 19:45:20 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.patterns.NodeTest;

/**
 * This class defines a simplified type of union iterator that only
 * tests along the child axes.  If the conditions are right, it is
 * much faster than using a UnionPathIterator.
 * <p>
 *  这个类定义了一个简化类型的联合迭代器,它只沿着子轴进行测试。如果条件是正确的,它比使用UnionPathIterator要快得多。
 * 
 */
public class UnionChildIterator extends ChildTestIterator
{
    static final long serialVersionUID = 3500298482193003495L;
  /**
   * Even though these may hold full LocPathIterators, this array does
   * not have to be cloned, since only the node test and predicate
   * portion are used, and these only need static information.  However,
   * also note that index predicates can not be used!
   * <p>
   *  即使这些可能包含完整的LocPathIterator,也不必克隆此数组,因为只使用节点测试和谓词部分,并且这些只需要静态信息。但是,还要注意,索引谓词不能使用！
   * 
   */
  private PredicatedNodeTest[] m_nodeTests = null;

  /**
   * Constructor for UnionChildIterator
   * <p>
   *  UnionChildIterator的构造函数
   * 
   */
  public UnionChildIterator()
  {
    super(null);
  }

  /**
   * Add a node test to the union list.
   *
   * <p>
   *  将一个节点测试添加到并集列表中。
   * 
   * 
   * @param test reference to a NodeTest, which will be added
   * directly to the list of node tests (in other words, it will
   * not be cloned).  The parent of this test will be set to
   * this object.
   */
  public void addNodeTest(PredicatedNodeTest test)
  {

    // Increase array size by only 1 at a time.  Fix this
    // if it looks to be a problem.
    if (null == m_nodeTests)
    {
      m_nodeTests = new PredicatedNodeTest[1];
      m_nodeTests[0] = test;
    }
    else
    {
      PredicatedNodeTest[] tests = m_nodeTests;
      int len = m_nodeTests.length;

      m_nodeTests = new PredicatedNodeTest[len + 1];

      System.arraycopy(tests, 0, m_nodeTests, 0, len);

      m_nodeTests[len] = test;
    }
    test.exprSetParent(this);
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量固定到堆栈帧索引。
   * 
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
    if (m_nodeTests != null) {
      for (int i = 0; i < m_nodeTests.length; i++) {
        m_nodeTests[i].fixupVariables(vars, globalsSize);
      }
    }
  }

  /**
   * Test whether a specified node is visible in the logical view of a
   * TreeWalker or NodeIterator. This function will be called by the
   * implementation of TreeWalker and NodeIterator; it is not intended to
   * be called directly from user code.
   * <p>
   * 测试指定的节点在TreeWalker或NodeIterator的逻辑视图中是否可见。这个函数将通过TreeWalker和NodeIterator的实现来调用;它不打算从用户代码直接调用。
   * 
   * @param n  The node to check to see if it passes the filter or not.
   * @return  a constant to determine whether the node is accepted,
   *   rejected, or skipped, as defined  above .
   */
  public short acceptNode(int n)
  {
    XPathContext xctxt = getXPathContext();
    try
    {
      xctxt.pushCurrentNode(n);
      for (int i = 0; i < m_nodeTests.length; i++)
      {
        PredicatedNodeTest pnt = m_nodeTests[i];
        XObject score = pnt.execute(xctxt, n);
        if (score != NodeTest.SCORE_NONE)
        {
          // Note that we are assuming there are no positional predicates!
          if (pnt.getPredicateCount() > 0)
          {
            if (pnt.executePredicates(n, xctxt))
              return DTMIterator.FILTER_ACCEPT;
          }
          else
            return DTMIterator.FILTER_ACCEPT;

        }
      }
    }
    catch (javax.xml.transform.TransformerException se)
    {

      // TODO: Fix this.
      throw new RuntimeException(se.getMessage());
    }
    finally
    {
      xctxt.popCurrentNode();
    }
    return DTMIterator.FILTER_SKIP;
  }

}
