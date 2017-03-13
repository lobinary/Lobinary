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
 *  版权所有1999-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: UnionPathIterator.java,v 1.2.4.1 2005/09/14 19:43:25 jeffsuttor Exp $
 * <p>
 *  $ Id：UnionPathIteratorjava,v 1241 2005/09/14 19:43:25 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.Axis;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.compiler.OpCodes;
import com.sun.org.apache.xpath.internal.compiler.OpMap;

/**
 * This class extends NodeSetDTM, which implements DTMIterator,
 * and fetches nodes one at a time in document order based on a XPath
 * <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
 * As each node is iterated via nextNode(), the node is also stored
 * in the NodeVector, so that previousNode() can easily be done.
 * @xsl.usage advanced
 * <p>
 * 此类扩展了NodeSetDTM,它实现了DTMIterator,并根据XPath <a href=\"http://wwww3org/TR/xpath#NT-UnionExpr\"> UnionExpr
 *  </a>以文档顺序一次获取一个节点。
 * 每个节点通过nextNode()进行迭代,该节点也存储在NodeVector中,这样previousNode()可以很容易地完成@xslusage advanced。
 * 
 */
public class UnionPathIterator extends LocPathIterator
        implements Cloneable, DTMIterator, java.io.Serializable, PathComponent
{
    static final long serialVersionUID = -3910351546843826781L;

  /**
   * Constructor to create an instance which you can add location paths to.
   * <p>
   *  构造函数创建一个可以添加位置路径的实例
   * 
   */
  public UnionPathIterator()
  {

    super();

    // m_mutable = false;
    // m_cacheNodes = false;
    m_iterators = null;
    m_exprs = null;
  }

  /**
   * Initialize the context values for this expression
   * after it is cloned.
   *
   * <p>
   *  在克隆后,对此表达式的上下文值进行初始化
   * 
   * 
   * @param context The XPath runtime context for this
   * transformation.
   */
  public void setRoot(int context, Object environment)
  {
    super.setRoot(context, environment);

    try
    {
      if (null != m_exprs)
      {
        int n = m_exprs.length;
        DTMIterator newIters[] = new DTMIterator[n];

        for (int i = 0; i < n; i++)
        {
          DTMIterator iter = m_exprs[i].asIterator(m_execContext, context);
          newIters[i] = iter;
          iter.nextNode();
        }
        m_iterators = newIters;
      }
    }
    catch(Exception e)
    {
      throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(e);
    }
  }

  /**
   * Add an iterator to the union list.
   *
   * <p>
   *  向联合列表中添加迭代器
   * 
   * 
   * @param expr non-null reference to a location path iterator.
   */
  public void addIterator(DTMIterator expr)
  {

    // Increase array size by only 1 at a time.  Fix this
    // if it looks to be a problem.
    if (null == m_iterators)
    {
      m_iterators = new DTMIterator[1];
      m_iterators[0] = expr;
    }
    else
    {
      DTMIterator[] exprs = m_iterators;
      int len = m_iterators.length;

      m_iterators = new DTMIterator[len + 1];

      System.arraycopy(exprs, 0, m_iterators, 0, len);

      m_iterators[len] = expr;
    }
    expr.nextNode();
    if(expr instanceof Expression)
        ((Expression)expr).exprSetParent(this);
  }

  /**
   *  Detaches the iterator from the set which it iterated over, releasing
   * any computational resources and placing the iterator in the INVALID
   * state. After<code>detach</code> has been invoked, calls to
   * <code>nextNode</code> or<code>previousNode</code> will raise the
   * exception INVALID_STATE_ERR.
   * <p>
   * 从迭代的集合中分离迭代器,释放任何计算资源并将迭代器置于INVALID状态调用<code> detach </code>之后,调用<code> nextNode </code>或<code> previ
   * ousNode </code>将引发异常INVALID_STATE_ERR。
   * 
   */
  public void detach()
  {
          if(m_allowDetach && null != m_iterators){
                  int n = m_iterators.length;
                  for(int i = 0; i < n; i++)
                  {
                          m_iterators[i].detach();
                  }
                  m_iterators = null;
          }
  }


  /**
   * Create a UnionPathIterator object, including creation
   * of location path iterators from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   *  创建UnionPathIterator对象,包括从操作码列表创建位置路径迭代器,并调用回编译器以创建谓词表达式
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public UnionPathIterator(Compiler compiler, int opPos)
          throws javax.xml.transform.TransformerException
  {

    super();

    opPos = OpMap.getFirstChildPos(opPos);

    loadLocationPaths(compiler, opPos, 0);
  }

  /**
   * This will return an iterator capable of handling the union of paths given.
   *
   * <p>
   *  这将返回一个能够处理给定路径的并集的迭代器
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   *
   * @return Object that is derived from LocPathIterator.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public static LocPathIterator createUnionIterator(Compiler compiler, int opPos)
          throws javax.xml.transform.TransformerException
  {
        // For the moment, I'm going to first create a full UnionPathIterator, and
        // then see if I can reduce it to a UnionChildIterator.  It would obviously
        // be more effecient to just test for the conditions for a UnionChildIterator,
        // and then create that directly.
        UnionPathIterator upi = new UnionPathIterator(compiler, opPos);
        int nPaths = upi.m_exprs.length;
        boolean isAllChildIterators = true;
        for(int i = 0; i < nPaths; i++)
        {
                LocPathIterator lpi = upi.m_exprs[i];

                if(lpi.getAxis() != Axis.CHILD)
                {
                        isAllChildIterators = false;
                        break;
                }
                else
                {
                        // check for positional predicates or position function, which won't work.
                        if(HasPositionalPredChecker.check(lpi))
                        {
                                isAllChildIterators = false;
                                break;
                        }
                }
        }
        if(isAllChildIterators)
        {
                UnionChildIterator uci = new UnionChildIterator();

                for(int i = 0; i < nPaths; i++)
                {
                        PredicatedNodeTest lpi = upi.m_exprs[i];
                        // I could strip the lpi down to a pure PredicatedNodeTest, but
                        // I don't think it's worth it.  Note that the test can be used
                        // as a static object... so it doesn't have to be cloned.
                        uci.addNodeTest(lpi);
                }
                return uci;

        }
        else
                return upi;
  }

  /**
   * Get the analysis bits for this walker, as defined in the WalkerFactory.
   * <p>
   *  获取WalkerFactory中定义的此Walker的分析位
   * 
   * 
   * @return One of WalkerFactory#BIT_DESCENDANT, etc.
   */
  public int getAnalysisBits()
  {
    int bits = 0;

    if (m_exprs != null)
    {
      int n = m_exprs.length;

      for (int i = 0; i < n; i++)
      {
        int bit = m_exprs[i].getAnalysisBits();
        bits |= bit;
      }
    }

    return bits;
  }

  /**
   * Read the object from a serialization stream.
   *
   * <p>
   *  从序列化流中读取对象
   * 
   * 
   * @param stream Input stream to read from
   *
   * @throws java.io.IOException
   * @throws javax.xml.transform.TransformerException
   */
  private void readObject(java.io.ObjectInputStream stream)
          throws java.io.IOException, javax.xml.transform.TransformerException
  {
    try
    {
      stream.defaultReadObject();
      m_clones =  new IteratorPool(this);
    }
    catch (ClassNotFoundException cnfe)
    {
      throw new javax.xml.transform.TransformerException(cnfe);
    }
  }

  /**
   * Get a cloned LocPathIterator that holds the same
   * position as this iterator.
   *
   * <p>
   *  获取与此迭代器具有相同位置的克隆LocPathIterator
   * 
   * 
   * @return A clone of this iterator that holds the same node position.
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {

    UnionPathIterator clone = (UnionPathIterator) super.clone();
    if (m_iterators != null)
    {
      int n = m_iterators.length;

      clone.m_iterators = new DTMIterator[n];

      for (int i = 0; i < n; i++)
      {
        clone.m_iterators[i] = (DTMIterator)m_iterators[i].clone();
      }
    }

    return clone;
  }


  /**
   * Create a new location path iterator.
   *
   * <p>
   *  创建一个新的位置路径迭代器
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   *
   * @return New location path iterator.
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected LocPathIterator createDTMIterator(
          Compiler compiler, int opPos) throws javax.xml.transform.TransformerException
  {
    LocPathIterator lpi = (LocPathIterator)WalkerFactory.newDTMIterator(compiler, opPos,
                                      (compiler.getLocationPathDepth() <= 0));
    return lpi;
  }

  /**
   * Initialize the location path iterators.  Recursive.
   *
   * <p>
   * 初始化位置路径迭代器
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   * @param count The insert position of the iterator.
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected void loadLocationPaths(Compiler compiler, int opPos, int count)
          throws javax.xml.transform.TransformerException
  {

    // TODO: Handle unwrapped FilterExpr
    int steptype = compiler.getOp(opPos);

    if (steptype == OpCodes.OP_LOCATIONPATH)
    {
      loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);

      m_exprs[count] = createDTMIterator(compiler, opPos);
      m_exprs[count].exprSetParent(this);
    }
    else
    {

      // Have to check for unwrapped functions, which the LocPathIterator
      // doesn't handle.
      switch (steptype)
      {
      case OpCodes.OP_VARIABLE :
      case OpCodes.OP_EXTFUNCTION :
      case OpCodes.OP_FUNCTION :
      case OpCodes.OP_GROUP :
        loadLocationPaths(compiler, compiler.getNextOpPos(opPos), count + 1);

        WalkingIterator iter =
          new WalkingIterator(compiler.getNamespaceContext());
        iter.exprSetParent(this);

        if(compiler.getLocationPathDepth() <= 0)
          iter.setIsTopLevel(true);

        iter.m_firstWalker = new com.sun.org.apache.xpath.internal.axes.FilterExprWalker(iter);

        iter.m_firstWalker.init(compiler, opPos, steptype);

        m_exprs[count] = iter;
        break;
      default :
        m_exprs = new LocPathIterator[count];
      }
    }
  }

  /**
   *  Returns the next node in the set and advances the position of the
   * iterator in the set. After a DTMIterator is created, the first call
   * to nextNode() returns the first node in the set.
   * <p>
   *  返回集合中的下一个节点,并推进迭代器在集合中的位置创建DTMIterator后,第一次调用nextNode()返回集合中的第一个节点
   * 
   * 
   * @return  The next <code>Node</code> in the set being iterated over, or
   *   <code>null</code> if there are no more members in that set.
   */
  public int nextNode()
  {
        if(m_foundLast)
                return DTM.NULL;

    // Loop through the iterators getting the current fetched
    // node, and get the earliest occuring in document order
    int earliestNode = DTM.NULL;

    if (null != m_iterators)
    {
      int n = m_iterators.length;
      int iteratorUsed = -1;

      for (int i = 0; i < n; i++)
      {
        int node = m_iterators[i].getCurrentNode();

        if (DTM.NULL == node)
          continue;
        else if (DTM.NULL == earliestNode)
        {
          iteratorUsed = i;
          earliestNode = node;
        }
        else
        {
          if (node == earliestNode)
          {

            // Found a duplicate, so skip past it.
            m_iterators[i].nextNode();
          }
          else
          {
            DTM dtm = getDTM(node);

            if (dtm.isNodeAfter(node, earliestNode))
            {
              iteratorUsed = i;
              earliestNode = node;
            }
          }
        }
      }

      if (DTM.NULL != earliestNode)
      {
        m_iterators[iteratorUsed].nextNode();

        incrementCurrentPos();
      }
      else
        m_foundLast = true;
    }

    m_lastFetched = earliestNode;

    return earliestNode;
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量修复为堆栈帧索引
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
    for (int i = 0; i < m_exprs.length; i++)
    {
      m_exprs[i].fixupVariables(vars, globalsSize);
    }

  }

  /**
   * The location path iterators, one for each
   * <a href="http://www.w3.org/TR/xpath#NT-LocationPath">location
   * path</a> contained in the union expression.
   * <p>
   *  位置路径迭代器,包含在联合表达式中的每个<a href=\"http://wwww3org/TR/xpath#NT-LocationPath\">位置路径</a>
   * 
   * 
   * @serial
   */
  protected LocPathIterator[] m_exprs;


  /**
   * The location path iterators, one for each
   * <a href="http://www.w3.org/TR/xpath#NT-LocationPath">location
   * path</a> contained in the union expression.
   * <p>
   *  位置路径迭代器,包含在联合表达式中的每个<a href=\"http://wwww3org/TR/xpath#NT-LocationPath\">位置路径</a>
   * 
   * 
   * @serial
   */
  protected DTMIterator[] m_iterators;

  /**
   * Returns the axis being iterated, if it is known.
   *
   * <p>
   *  返回正在迭代的轴(如果已知)
   * 
   * 
   * @return Axis.CHILD, etc., or -1 if the axis is not known or is of multiple
   * types.
   */
  public int getAxis()
  {
    // Could be smarter.
    return -1;
  }

  class iterOwner implements ExpressionOwner
  {
        int m_index;

        iterOwner(int index)
        {
                m_index = index;
        }

    /**
    /* <p>
    /* 
     * @see ExpressionOwner#getExpression()
     */
    public Expression getExpression()
    {
      return m_exprs[m_index];
    }

    /**
    /* <p>
    /* 
     * @see ExpressionOwner#setExpression(Expression)
     */
    public void setExpression(Expression exp)
    {

        if(!(exp instanceof LocPathIterator))
        {
                // Yuck.  Need FilterExprIter.  Or make it so m_exprs can be just
                // plain expressions?
                WalkingIterator wi = new WalkingIterator(getPrefixResolver());
                FilterExprWalker few = new FilterExprWalker(wi);
                wi.setFirstWalker(few);
                few.setInnerExpression(exp);
                wi.exprSetParent(UnionPathIterator.this);
                few.exprSetParent(wi);
                exp.exprSetParent(few);
                exp = wi;
        }
        else
                exp.exprSetParent(UnionPathIterator.this);
        m_exprs[m_index] = (LocPathIterator)exp;
    }

  }

  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
                if(visitor.visitUnionPath(owner, this))
                {
                        if(null != m_exprs)
                        {
                                int n = m_exprs.length;
                                for(int i = 0; i < n; i++)
                                {
                                        m_exprs[i].callVisitors(new iterOwner(i), visitor);
                                }
                        }
                }
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

      UnionPathIterator upi = (UnionPathIterator) expr;

      if (null != m_exprs)
      {
        int n = m_exprs.length;

        if((null == upi.m_exprs) || (upi.m_exprs.length != n))
                return false;

        for (int i = 0; i < n; i++)
        {
          if(!m_exprs[i].deepEquals(upi.m_exprs[i]))
                return false;
        }
      }
      else if (null != upi.m_exprs)
      {
          return false;
      }

      return true;
    }


}
