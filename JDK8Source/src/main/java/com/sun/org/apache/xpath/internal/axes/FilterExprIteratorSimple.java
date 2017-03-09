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
 * $Id: FilterExprIteratorSimple.java,v 1.2.4.2 2005/09/14 19:45:21 jeffsuttor Exp $
 * <p>
 *  $ Id：FilterExprIteratorSimple.java,v 1.2.4.2 2005/09/14 19:45:21 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.Axis;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.VariableStack;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;

/**
 * Class to use for one-step iteration that doesn't have a predicate, and
 * doesn't need to set the context.
 * <p>
 *  用于一步迭代的类,它没有谓词,并且不需要设置上下文。
 * 
 */
public class FilterExprIteratorSimple extends LocPathIterator
{
    static final long serialVersionUID = -6978977187025375579L;
  /** The contained expression. Should be non-null.
  /* <p>
  /* 
   *  @serial   */
  private Expression m_expr;

  /** The result of executing m_expr.  Needs to be deep cloned on clone op.  */
  transient private XNodeSet m_exprObj;

  private boolean m_mustHardReset = false;
  private boolean m_canDetachNodeset = true;

  /**
   * Create a FilterExprIteratorSimple object.
   *
   * <p>
   *  创建FilterExprIteratorSimple对象。
   * 
   */
  public FilterExprIteratorSimple()
  {
    super(null);
  }

  /**
   * Create a FilterExprIteratorSimple object.
   *
   * <p>
   *  创建FilterExprIteratorSimple对象。
   * 
   */
  public FilterExprIteratorSimple(Expression expr)
  {
    super(null);
    m_expr = expr;
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
        m_exprObj = executeFilterExpr(context, m_execContext, getPrefixResolver(),
                          getIsTopLevel(), m_stackFrame, m_expr);
  }

  /**
   * Execute the expression.  Meant for reuse by other FilterExpr iterators
   * that are not derived from this object.
   * <p>
   *  执行表达式。可以由不是从此对象派生的其他FilterExpr迭代器重用。
   * 
   */
  public static XNodeSet executeFilterExpr(int context, XPathContext xctxt,
                                                                                                PrefixResolver prefixResolver,
                                                                                                boolean isTopLevel,
                                                                                                int stackFrame,
                                                                                                Expression expr )
    throws com.sun.org.apache.xml.internal.utils.WrappedRuntimeException
  {
    PrefixResolver savedResolver = xctxt.getNamespaceContext();
    XNodeSet result = null;

    try
    {
      xctxt.pushCurrentNode(context);
      xctxt.setNamespaceContext(prefixResolver);

      // The setRoot operation can take place with a reset operation,
      // and so we may not be in the context of LocPathIterator#nextNode,
      // so we have to set up the variable context, execute the expression,
      // and then restore the variable context.

      if (isTopLevel)
      {
        // System.out.println("calling m_expr.execute(getXPathContext())");
        VariableStack vars = xctxt.getVarStack();

        // These three statements need to be combined into one operation.
        int savedStart = vars.getStackFrame();
        vars.setStackFrame(stackFrame);

        result = (com.sun.org.apache.xpath.internal.objects.XNodeSet) expr.execute(xctxt);
        result.setShouldCacheNodes(true);

        // These two statements need to be combined into one operation.
        vars.setStackFrame(savedStart);
      }
      else
          result = (com.sun.org.apache.xpath.internal.objects.XNodeSet) expr.execute(xctxt);

    }
    catch (javax.xml.transform.TransformerException se)
    {

      // TODO: Fix...
      throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(se);
    }
    finally
    {
      xctxt.popCurrentNode();
      xctxt.setNamespaceContext(savedResolver);
    }
    return result;
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
        if(m_foundLast)
                return DTM.NULL;

    int next;

    if (null != m_exprObj)
    {
      m_lastFetched = next = m_exprObj.nextNode();
    }
    else
      m_lastFetched = next = DTM.NULL;

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
   * Detaches the walker from the set which it iterated over, releasing
   * any computational resources and placing the iterator in the INVALID
   * state.
   * <p>
   * 将步行器从迭代的集合中分离,释放任何计算资源并将迭代器置于INVALID状态。
   * 
   */
  public void detach()
  {
    if(m_allowDetach)
    {
                super.detach();
                m_exprObj.detach();
                m_exprObj = null;
    }
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量修复为堆栈帧索引。
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
    m_expr.fixupVariables(vars, globalsSize);
  }

  /**
   * Get the inner contained expression of this filter.
   * <p>
   *  获取此过滤器的内部包含的表达式。
   * 
   */
  public Expression getInnerExpression()
  {
    return m_expr;
  }

  /**
   * Set the inner contained expression of this filter.
   * <p>
   *  设置此过滤器的内部包含的表达式。
   * 
   */
  public void setInnerExpression(Expression expr)
  {
    expr.exprSetParent(this);
    m_expr = expr;
  }

  /**
   * Get the analysis bits for this walker, as defined in the WalkerFactory.
   * <p>
   *  获取WalkerFactory中定义的此Walker的分析位。
   * 
   * 
   * @return One of WalkerFactory#BIT_DESCENDANT, etc.
   */
  public int getAnalysisBits()
  {
    if (null != m_expr && m_expr instanceof PathComponent)
    {
      return ((PathComponent) m_expr).getAnalysisBits();
    }
    return WalkerFactory.BIT_FILTER;
  }

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   * Warning: This can only be called after setRoot has been called!
   *
   * <p>
   *  如果迭代中的所有节点都以文档顺序返回,则返回true。警告：这只能在setRoot被调用后调用！
   * 
   * 
   * @return true as a default.
   */
  public boolean isDocOrdered()
  {
    return m_exprObj.isDocOrdered();
  }

  class filterExprOwner implements ExpressionOwner
  {
    /**
    /* <p>
    /* 
    * @see ExpressionOwner#getExpression()
    */
    public Expression getExpression()
    {
      return m_expr;
    }

    /**
    /* <p>
    /* 
     * @see ExpressionOwner#setExpression(Expression)
     */
    public void setExpression(Expression exp)
    {
      exp.exprSetParent(FilterExprIteratorSimple.this);
      m_expr = exp;
    }

  }

  /**
   * This will traverse the heararchy, calling the visitor for
   * each member.  If the called visitor method returns
   * false, the subtree should not be called.
   *
   * <p>
   *  这将遍历听证,调用每个成员的访问者。如果被调用的visitor方法返回false,则不应该调用子树。
   * 
   * 
   * @param visitor The visitor whose appropriate method will be called.
   */
  public void callPredicateVisitors(XPathVisitor visitor)
  {
    m_expr.callVisitors(new filterExprOwner(), visitor);

    super.callPredicateVisitors(visitor);
  }

  /**
  /* <p>
  /* 
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
    if (!super.deepEquals(expr))
      return false;

    FilterExprIteratorSimple fet = (FilterExprIteratorSimple) expr;
    if (!m_expr.deepEquals(fet.m_expr))
      return false;

    return true;
  }

  /**
   * Returns the axis being iterated, if it is known.
   *
   * <p>
   *  返回正在迭代的轴(如果已知)。
   * 
   * @return Axis.CHILD, etc., or -1 if the axis is not known or is of multiple
   * types.
   */
  public int getAxis()
  {
        if(null != m_exprObj)
        return m_exprObj.getAxis();
    else
        return Axis.FILTEREDLIST;
  }


}
