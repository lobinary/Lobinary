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
 * $Id: Operation.java,v 1.2.4.1 2005/09/14 21:31:42 jeffsuttor Exp $
 * <p>
 *  $ Id：Operationjava,v 1241 2005/09/14 21:31:42 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.operations;

import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * The baseclass for a binary operation.
 * <p>
 * 二进制操作的基类
 * 
 */
public class Operation extends Expression implements ExpressionOwner
{
    static final long serialVersionUID = -3037139537171050430L;

  /** The left operand expression.
  /* <p>
  /* 
   *  @serial */
  protected Expression m_left;

  /** The right operand expression.
  /* <p>
  /* 
   *  @serial */
  protected Expression m_right;

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
    m_left.fixupVariables(vars, globalsSize);
    m_right.fixupVariables(vars, globalsSize);
  }


  /**
   * Tell if this expression or it's subexpressions can traverse outside
   * the current subtree.
   *
   * <p>
   *  告诉这个表达式或它的子表达式是否可以遍历当前子树
   * 
   * 
   * @return true if traversal outside the context node's subtree can occur.
   */
  public boolean canTraverseOutsideSubtree()
  {

    if (null != m_left && m_left.canTraverseOutsideSubtree())
      return true;

    if (null != m_right && m_right.canTraverseOutsideSubtree())
      return true;

    return false;
  }

  /**
   * Set the left and right operand expressions for this operation.
   *
   *
   * <p>
   *  设置此操作的左和右操作数表达式
   * 
   * 
   * @param l The left expression operand.
   * @param r The right expression operand.
   */
  public void setLeftRight(Expression l, Expression r)
  {
    m_left = l;
    m_right = r;
    l.exprSetParent(this);
    r.exprSetParent(this);
  }

  /**
   * Execute a binary operation by calling execute on each of the operands,
   * and then calling the operate method on the derived class.
   *
   *
   * <p>
   *  通过在每个操作数上调用execute,然后在派生类上调用operate方法来执行二进制操作
   * 
   * 
   * @param xctxt The runtime execution context.
   *
   * @return The XObject result of the operation.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    XObject left = m_left.execute(xctxt, true);
    XObject right = m_right.execute(xctxt, true);

    XObject result = operate(left, right);
    left.detach();
    right.detach();
    return result;
  }

  /**
   * Apply the operation to two operands, and return the result.
   *
   *
   * <p>
   *  将操作应用于两个操作数,并返回结果
   * 
   * 
   * @param left non-null reference to the evaluated left operand.
   * @param right non-null reference to the evaluated right operand.
   *
   * @return non-null reference to the XObject that represents the result of the operation.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject operate(XObject left, XObject right)
          throws javax.xml.transform.TransformerException
  {
    return null;  // no-op
  }

  /** @return the left operand of binary operation, as an Expression.
  /* <p>
   */
  public Expression getLeftOperand(){
    return m_left;
  }

  /** @return the right operand of binary operation, as an Expression.
  /* <p>
   */
  public Expression getRightOperand(){
    return m_right;
  }

  class LeftExprOwner implements ExpressionOwner
  {
    /**
    /* <p>
    /* 
     * @see ExpressionOwner#getExpression()
     */
    public Expression getExpression()
    {
      return m_left;
    }

    /**
    /* <p>
    /* 
     * @see ExpressionOwner#setExpression(Expression)
     */
    public void setExpression(Expression exp)
    {
        exp.exprSetParent(Operation.this);
        m_left = exp;
    }
  }

  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        if(visitor.visitBinaryOperation(owner, this))
        {
                m_left.callVisitors(new LeftExprOwner(), visitor);
                m_right.callVisitors(this, visitor);
        }
  }

  /**
  /* <p>
  /* 
   * @see ExpressionOwner#getExpression()
   */
  public Expression getExpression()
  {
    return m_right;
  }

  /**
  /* <p>
  /* 
   * @see ExpressionOwner#setExpression(Expression)
   */
  public void setExpression(Expression exp)
  {
        exp.exprSetParent(this);
        m_right = exp;
  }

  /**
  /* <p>
  /* 
   * @see Expression#deepEquals(Expression)
   */
  public boolean deepEquals(Expression expr)
  {
        if(!isSameClass(expr))
                return false;

        if(!m_left.deepEquals(((Operation)expr).m_left))
                return false;

        if(!m_right.deepEquals(((Operation)expr).m_right))
                return false;

        return true;
  }
}
