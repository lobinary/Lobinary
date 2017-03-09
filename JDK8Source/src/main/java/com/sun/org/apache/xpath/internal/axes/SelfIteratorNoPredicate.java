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
 * $Id: SelfIteratorNoPredicate.java,v 1.2.4.2 2005/09/14 19:45:21 jeffsuttor Exp $
 * <p>
 *  $ Id：SelfIteratorNoPredicate.java,v 1.2.4.2 2005/09/14 19:45:21 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.compiler.Compiler;

/**
 * This class implements an optimized iterator for
 * "." patterns, that is, the self axes without any predicates.
 * <p>
 *  这个类为"。"实现了一个优化的迭代器。模式,即没有任何谓词的自身轴。
 * 
 * 
 * @see com.sun.org.apache.xpath.internal.axes.LocPathIterator
 * @xsl.usage advanced
 */
public class SelfIteratorNoPredicate extends LocPathIterator
{
    static final long serialVersionUID = -4226887905279814201L;

  /**
   * Create a SelfIteratorNoPredicate object.
   *
   * <p>
   *  创建SelfIteratorNoPredicate对象。
   * 
   * 
   * @param compiler A reference to the Compiler that contains the op map.
   * @param opPos The position within the op map, which contains the
   * location path expression for this itterator.
   * @param analysis Analysis bits.
   *
   * @throws javax.xml.transform.TransformerException
   */
  SelfIteratorNoPredicate(Compiler compiler, int opPos, int analysis)
          throws javax.xml.transform.TransformerException
  {
    super(compiler, opPos, analysis, false);
  }

  /**
   * Create a SelfIteratorNoPredicate object.
   *
   * <p>
   *  创建SelfIteratorNoPredicate对象。
   * 
   * 
   * @throws javax.xml.transform.TransformerException
   */
  public SelfIteratorNoPredicate()
          throws javax.xml.transform.TransformerException
  {
    super(null);
  }


  /**
   *  Returns the next node in the set and advances the position of the
   * iterator in the set. After a NodeIterator is created, the first call
   * to nextNode() returns the first node in the set.
   *
   * <p>
   *  返回集合中的下一个节点,并推进迭代器在集合中的位置。创建NodeIterator之后,第一次调用nextNode()返回集合中的第一个节点。
   * 
   * 
   * @return  The next <code>Node</code> in the set being iterated over, or
   *   <code>null</code> if there are no more members in that set.
   */
  public int nextNode()
  {
    if (m_foundLast)
      return DTM.NULL;

    int next;
    DTM dtm = m_cdtm;

    m_lastFetched = next = (DTM.NULL == m_lastFetched)
                           ? m_context
                           : DTM.NULL;

    // m_lastFetched = next;
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

  /**
   * Return the first node out of the nodeset, if this expression is
   * a nodeset expression.  This is the default implementation for
   * nodesets.  Derived classes should try and override this and return a
   * value without having to do a clone operation.
   * <p>
   *  如果此表达式是节点集表达式,则将第一个节点返回节点集。这是节点集的默认实现。派生类应该尝试并覆盖此并返回一个值,而不必进行克隆操作。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return the first node out of the nodeset, or DTM.NULL.
   */
  public int asNode(XPathContext xctxt)
    throws javax.xml.transform.TransformerException
  {
    return xctxt.getCurrentNode();
  }

  /**
   * Get the index of the last node that can be itterated to.
   * This probably will need to be overridded by derived classes.
   *
   * <p>
   * 获取可以重写的最后一个节点的索引。这可能需要被派生类覆盖。
   * 
   * @param xctxt XPath runtime context.
   *
   * @return the index of the last node that can be itterated to.
   */
  public int getLastPos(XPathContext xctxt)
  {
    return 1;
  }


}
