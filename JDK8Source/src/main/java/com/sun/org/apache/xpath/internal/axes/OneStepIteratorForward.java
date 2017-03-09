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
 * $Id: OneStepIteratorForward.java,v 1.2.4.2 2005/09/14 19:45:22 jeffsuttor Exp $
 * <p>
 *  $ Id：OneStepIteratorForward.java,v 1.2.4.2 2005/09/14 19:45:22 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMFilter;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.compiler.OpMap;

/**
 * This class implements a general iterator for
 * those LocationSteps with only one step, and perhaps a predicate,
 * that only go forward (i.e. it can not be used with ancestors,
 * preceding, etc.)
 * <p>
 *  这个类为那些只有一个步骤,也许一个谓词,只向前(即它不能与祖先,前面,等等)使用的那些LocationSteps实现一个通用迭代器。
 * 
 * 
 * @see com.sun.org.apache.xpath.internal.axes#ChildTestIterator
 * @xsl.usage advanced
 */
public class OneStepIteratorForward extends ChildTestIterator
{
    static final long serialVersionUID = -1576936606178190566L;
  /** The traversal axis from where the nodes will be filtered. */
  protected int m_axis = -1;

  /**
   * Create a OneStepIterator object.
   *
   * <p>
   *  创建OneStepIterator对象。
   * 
   * 
   * @param compiler A reference to the Compiler that contains the op map.
   * @param opPos The position within the op map, which contains the
   * location path expression for this itterator.
   *
   * @throws javax.xml.transform.TransformerException
   */
  OneStepIteratorForward(Compiler compiler, int opPos, int analysis)
          throws javax.xml.transform.TransformerException
  {
    super(compiler, opPos, analysis);
    int firstStepPos = OpMap.getFirstChildPos(opPos);

    m_axis = WalkerFactory.getAxisFromStep(compiler, firstStepPos);

  }

  /**
   * Create a OneStepIterator object that will just traverse the self axes.
   *
   * <p>
   *  创建一个只会遍历自身轴的OneStepIterator对象。
   * 
   * 
   * @param axis One of the com.sun.org.apache.xml.internal.dtm.Axis integers.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public OneStepIteratorForward(int axis)
  {
    super(null);

    m_axis = axis;
    int whatToShow = DTMFilter.SHOW_ALL;
    initNodeTest(whatToShow);
  }




  /**
   * Initialize the context values for this expression
   * after it is cloned.
   *
   * <p>
   *  在克隆后,对此表达式的上下文值进行初始化。
   * 
   * 
   * @param context The XPath runtime context for this
   * transformation.
   */
  public void setRoot(int context, Object environment)
  {
    super.setRoot(context, environment);
    m_traverser = m_cdtm.getAxisTraverser(m_axis);

  }

//  /**
//   * Return the first node out of the nodeset, if this expression is
//   * a nodeset expression.  This is the default implementation for
//   * nodesets.
//   * <p>WARNING: Do not mutate this class from this function!</p>
//   * <p>
//   *  // *将第一个节点返回出节点集,如果此表达式是// *一个节点集表达式。这是// * nodesets的默认实现。 // * <p>警告：不要将此类从此函数改变！</p>
//   * 
//   * 
//   * @param xctxt The XPath runtime context.
//   * @return the first node out of the nodeset, or DTM.NULL.
//   */
//  public int asNode(XPathContext xctxt)
//    throws javax.xml.transform.TransformerException
//  {
//    if(getPredicateCount() > 0)
//      return super.asNode(xctxt);
//
//    int current = xctxt.getCurrentNode();
//
//    DTM dtm = xctxt.getDTM(current);
//    DTMAxisTraverser traverser = dtm.getAxisTraverser(m_axis);
//
//    String localName = getLocalName();
//    String namespace = getNamespace();
//    int what = m_whatToShow;
//
//    // System.out.println("what: ");
//    // NodeTest.debugWhatToShow(what);
//    if(DTMFilter.SHOW_ALL == what
//       || ((DTMFilter.SHOW_ELEMENT & what) == 0)
//       || localName == NodeTest.WILD
//       || namespace == NodeTest.WILD)
//    {
//      return traverser.first(current);
//    }
//    else
//    {
//      int type = getNodeTypeTest(what);
//      int extendedType = dtm.getExpandedTypeID(namespace, localName, type);
//      return traverser.first(current, extendedType);
//    }
//  }

  /**
   * Get the next node via getFirstAttribute && getNextAttribute.
   * <p>
   *  通过getFirstAttribute && getNextAttribute获取下一个节点。
   * 
   */
  protected int getNextNode()
  {
    m_lastFetched = (DTM.NULL == m_lastFetched)
                     ? m_traverser.first(m_context)
                     : m_traverser.next(m_context, m_lastFetched);
    return m_lastFetched;
  }

  /**
   * Returns the axis being iterated, if it is known.
   *
   * <p>
   * 返回正在迭代的轴(如果已知)。
   * 
   * 
   * @return Axis.CHILD, etc., or -1 if the axis is not known or is of multiple
   * types.
   */
  public int getAxis()
  {
    return m_axis;
  }

  /**
  /* <p>
  /* 
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
        if(!super.deepEquals(expr))
                return false;

        if(m_axis != ((OneStepIteratorForward)expr).m_axis)
                return false;

        return true;
  }


}
