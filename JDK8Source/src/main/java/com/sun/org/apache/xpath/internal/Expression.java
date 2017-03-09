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
 * $Id: Expression.java,v 1.2.4.2 2005/09/14 19:50:20 jeffsuttor Exp $
 * <p>
 *  $ Id：Expression.java,v 1.2.4.2 2005/09/14 19:50:20 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

import org.xml.sax.ContentHandler;

/**
 * This abstract class serves as the base for all expression objects.  An
 * Expression can be executed to return a {@link com.sun.org.apache.xpath.internal.objects.XObject},
 * normally has a location within a document or DOM, can send error and warning
 * events, and normally do not hold state and are meant to be immutable once
 * construction has completed.  An exception to the immutibility rule is iterators
 * and walkers, which must be cloned in order to be used -- the original must
 * still be immutable.
 * <p>
 *  这个抽象类作为所有表达式对象的基础。
 * 可以执行表达式来返回{@link com.sun.org.apache.xpath.internal.objects.XObject},通常在文档或DOM中有一个位置,可以发送错误和警告事件,通常不保留
 * 状态,并且一旦构建完成就意味着是不可变的。
 *  这个抽象类作为所有表达式对象的基础。不变性规则的一个例外是迭代器和步行器,它们必须被克隆以便使用 - 原始的仍然是不可变的。
 * 
 */
public abstract class Expression implements java.io.Serializable, ExpressionNode, XPathVisitable
{
    static final long serialVersionUID = 565665869777906902L;
  /**
   * The location where this expression was built from.  Need for diagnostic
   *  messages. May be null.
   * <p>
   *  构建此表达式的位置。需要诊断消息。可能为null。
   * 
   * 
   *  @serial
   */
  private ExpressionNode m_parent;

  /**
   * Tell if this expression or it's subexpressions can traverse outside
   * the current subtree.
   *
   * <p>
   *  告诉这个表达式或它的子表达式是否可以遍历当前子树。
   * 
   * 
   * @return true if traversal outside the context node's subtree can occur.
   */
  public boolean canTraverseOutsideSubtree()
  {
    return false;
  }

//  /**
//   * Set the location where this expression was built from.
//   *
//   *
//   * <p>
//   * // *设置构建此表达式的位置。 // * // *
//   * 
//   * 
//   * @param locator the location where this expression was built from, may be
//   *                null.
//   */
//  public void setSourceLocator(SourceLocator locator)
//  {
//    m_slocator = locator;
//  }

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param currentNode The currentNode.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   */
  public XObject execute(XPathContext xctxt, int currentNode)
          throws javax.xml.transform.TransformerException
  {

    // For now, the current node is already pushed.
    return execute(xctxt);
  }

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param currentNode The currentNode.
   * @param dtm The DTM of the current node.
   * @param expType The expanded type ID of the current node.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   */
  public XObject execute(
          XPathContext xctxt, int currentNode, DTM dtm, int expType)
            throws javax.xml.transform.TransformerException
  {

    // For now, the current node is already pushed.
    return execute(xctxt);
  }

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   */
  public abstract XObject execute(XPathContext xctxt)
    throws javax.xml.transform.TransformerException;

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression, but tell that a "safe" object doesn't have
   * to be returned.  The default implementation just calls execute(xctxt).
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果,但告知不必返回"安全"对象。默认实现只是调用execute(xctxt)。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param destructiveOK true if a "safe" object doesn't need to be returned.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   */
  public XObject execute(XPathContext xctxt, boolean destructiveOK)
    throws javax.xml.transform.TransformerException
  {
        return execute(xctxt);
  }


  /**
   * Evaluate expression to a number.
   *
   *
   * <p>
   *  将表达式计算为数字。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return The expression evaluated as a double.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public double num(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return execute(xctxt).num();
  }

  /**
   * Evaluate expression to a boolean.
   *
   *
   * <p>
   *  将表达式评估为布尔值。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return false
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean bool(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return execute(xctxt).bool();
  }

  /**
   * Cast result object to a string.
   *
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return The string this wraps or the empty string if null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XMLString xstr(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return execute(xctxt).xstr();
  }

  /**
   * Tell if the expression is a nodeset expression.  In other words, tell
   * if you can execute {@link #asNode(XPathContext) asNode} without an exception.
   * <p>
   *  告诉表达式是否是一个nodeet表达式。换句话说,告诉你是否可以执行{@link #asNode(XPathContext)asNode},没有异常。
   * 
   * 
   * @return true if the expression can be represented as a nodeset.
   */
  public boolean isNodesetExpr()
  {
    return false;
  }

  /**
   * Return the first node out of the nodeset, if this expression is
   * a nodeset expression.
   * <p>
   *  如果此表达式是节点集表达式,则将第一个节点返回节点集。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return the first node out of the nodeset, or DTM.NULL.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public int asNode(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
        DTMIterator iter = execute(xctxt).iter();
    return iter.nextNode();
  }

  /**
   * Given an select expression and a context, evaluate the XPath
   * and return the resulting iterator.
   *
   * <p>
   *  给定一个select表达式和一个上下文,评估XPath并返回结果迭代器。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   *
   *
   * @return A valid DTMIterator.
   * @throws TransformerException thrown if the active ProblemListener decides
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public DTMIterator asIterator(XPathContext xctxt, int contextNode)
          throws javax.xml.transform.TransformerException
  {

    try
    {
      xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);

      return execute(xctxt).iter();
    }
    finally
    {
      xctxt.popCurrentNodeAndExpression();
    }
  }

  /**
   * Given an select expression and a context, evaluate the XPath
   * and return the resulting iterator, but do not clone.
   *
   * <p>
   *  给定select表达式和上下文,评估XPath并返回所得到的迭代器,但不克隆。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   *
   *
   * @return A valid DTMIterator.
   * @throws TransformerException thrown if the active ProblemListener decides
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public DTMIterator asIteratorRaw(XPathContext xctxt, int contextNode)
          throws javax.xml.transform.TransformerException
  {

    try
    {
      xctxt.pushCurrentNodeAndExpression(contextNode, contextNode);

      XNodeSet nodeset = (XNodeSet)execute(xctxt);
      return nodeset.iterRaw();
    }
    finally
    {
      xctxt.popCurrentNodeAndExpression();
    }
  }


  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   *  在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * NEEDSDOC @param handler
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   * @throws org.xml.sax.SAXException
   */
  public void executeCharsToContentHandler(
          XPathContext xctxt, ContentHandler handler)
            throws javax.xml.transform.TransformerException,
                   org.xml.sax.SAXException
  {

    XObject obj = execute(xctxt);

    obj.dispatchCharactersEvents(handler);
    obj.detach();
  }

  /**
   * Tell if this expression returns a stable number that will not change during
   * iterations within the expression.  This is used to determine if a proximity
   * position predicate can indicate that no more searching has to occur.
   *
   *
   * <p>
   *  告诉这个表达式是否返回一个稳定的数字,在表达式的迭代期间不会改变。这用于确定接近位置谓词是否可以指示不必进行更多搜索。
   * 
   * 
   * @return true if the expression represents a stable number.
   */
  public boolean isStableNumber()
  {
    return false;
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   * 此函数用于在样式表构建时将QNames中的变量固定到堆栈帧索引。
   * 
   * 
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   * NEEDSDOC @param globalsSize
   */
  public abstract void fixupVariables(java.util.Vector vars, int globalsSize);

  /**
   * Compare this object with another object and see
   * if they are equal, include the sub heararchy.
   *
   * <p>
   *  将此对象与另一个对象进行比较,看看它们是否相等,包括子听觉。
   * 
   * 
   * @param expr Another expression object.
   * @return true if this objects class and the expr
   * object's class are the same, and the data contained
   * within both objects are considered equal.
   */
  public abstract boolean deepEquals(Expression expr);

  /**
   * This is a utility method to tell if the passed in
   * class is the same class as this.  It is to be used by
   * the deepEquals method.  I'm bottlenecking it here
   * because I'm not totally confident that comparing the
   * class objects is the best way to do this.
   * <p>
   *  这是一个实用方法来判断传入的类是否与此类相同。它由deepEquals方法使用。我在这里的瓶颈,因为我不完全有信心比较类对象是最好的方法做到这一点。
   * 
   * 
   * @return true of the passed in class is the exact same
   * class as this class.
   */
  protected final boolean isSameClass(Expression expr)
  {
        if(null == expr)
          return false;

        return (getClass() == expr.getClass());
  }

  /**
   * Warn the user of an problem.
   *
   * <p>
   *  警告用户有问题。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param msg An error msgkey that corresponds to one of the conststants found
   *            in {@link com.sun.org.apache.xpath.internal.res.XPATHErrorResources}, which is
   *            a key for a format string.
   * @param args An array of arguments represented in the format string, which
   *             may be null.
   *
   * @throws TransformerException if the current ErrorListoner determines to
   *                              throw an exception.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public void warn(XPathContext xctxt, String msg, Object[] args)
          throws javax.xml.transform.TransformerException
  {

    java.lang.String fmsg = XSLMessages.createXPATHWarning(msg, args);

    if (null != xctxt)
    {
      ErrorListener eh = xctxt.getErrorListener();

      // TO DO: Need to get stylesheet Locator from here.
      eh.warning(new TransformerException(fmsg, xctxt.getSAXLocator()));
    }
  }

  /**
   * Tell the user of an assertion error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户断言错误,并可能抛出异常。
   * 
   * 
   * @param b  If false, a runtime exception will be thrown.
   * @param msg The assertion message, which should be informative.
   *
   * @throws RuntimeException if the b argument is false.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public void assertion(boolean b, java.lang.String msg)
  {

    if (!b)
    {
      java.lang.String fMsg = XSLMessages.createXPATHMessage(
        XPATHErrorResources.ER_INCORRECT_PROGRAMMER_ASSERTION,
        new Object[]{ msg });

      throw new RuntimeException(fMsg);
    }
  }

  /**
   * Tell the user of an error, and probably throw an
   * exception.
   *
   * <p>
   *  告诉用户一个错误,并可能抛出异常。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param msg An error msgkey that corresponds to one of the constants found
   *            in {@link com.sun.org.apache.xpath.internal.res.XPATHErrorResources}, which is
   *            a key for a format string.
   * @param args An array of arguments represented in the format string, which
   *             may be null.
   *
   * @throws TransformerException if the current ErrorListoner determines to
   *                              throw an exception.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public void error(XPathContext xctxt, String msg, Object[] args)
          throws javax.xml.transform.TransformerException
  {

    java.lang.String fmsg = XSLMessages.createXPATHMessage(msg, args);

    if (null != xctxt)
    {
      ErrorListener eh = xctxt.getErrorListener();
      TransformerException te = new TransformerException(fmsg, this);

      eh.fatalError(te);
    }
  }

  /**
   * Get the first non-Expression parent of this node.
   * <p>
   *  获取此节点的第一个非表达式父代。
   * 
   * 
   * @return null or first ancestor that is not an Expression.
   */
  public ExpressionNode getExpressionOwner()
  {
        ExpressionNode parent = exprGetParent();
        while((null != parent) && (parent instanceof Expression))
                parent = parent.exprGetParent();
        return parent;
  }

  //=============== ExpressionNode methods ================

  /** This pair of methods are used to inform the node of its
  /* <p>
  /* 
    parent. */
  public void exprSetParent(ExpressionNode n)
  {
        assertion(n != this, "Can not parent an expression to itself!");
        m_parent = n;
  }

  public ExpressionNode exprGetParent()
  {
        return m_parent;
  }

  /** This method tells the node to add its argument to the node's
  /* <p>
  /* 
    list of children.  */
  public void exprAddChild(ExpressionNode n, int i)
  {
        assertion(false, "exprAddChild method not implemented!");
  }

  /** This method returns a child node.  The children are numbered
  /* <p>
  /* 
     from zero, left to right. */
  public ExpressionNode exprGetChild(int i)
  {
        return null;
  }

  /** Return the number of children the node has. */
  public int exprGetNumChildren()
  {
        return 0;
  }

  //=============== SourceLocator methods ================

  /**
   * Return the public identifier for the current document event.
   *
   * <p>The return value is the public identifier of the document
   * entity or of the external parsed entity in which the markup that
   * triggered the event appears.</p>
   *
   * <p>
   *  返回当前文档事件的公共标识符。
   * 
   *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体的公共标识符。</p>
   * 
   * 
   * @return A string containing the public identifier, or
   *         null if none is available.
   * @see #getSystemId
   */
  public String getPublicId()
  {
        if(null == m_parent)
          return null;
        return m_parent.getPublicId();
  }

  /**
   * Return the system identifier for the current document event.
   *
   * <p>The return value is the system identifier of the document
   * entity or of the external parsed entity in which the markup that
   * triggered the event appears.</p>
   *
   * <p>If the system identifier is a URL, the parser must resolve it
   * fully before passing it to the application.</p>
   *
   * <p>
   *  返回当前文档事件的系统标识符。
   * 
   *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体的系统标识符。</p>
   * 
   *  <p>如果系统标识符是URL,则解析器必须在将其传递给应用程序之前完全解析。</p>
   * 
   * 
   * @return A string containing the system identifier, or null
   *         if none is available.
   * @see #getPublicId
   */
  public String getSystemId()
  {
        if(null == m_parent)
          return null;
        return m_parent.getSystemId();
  }

  /**
   * Return the line number where the current document event ends.
   *
   * <p><strong>Warning:</strong> The return value from the method
   * is intended only as an approximation for the sake of error
   * reporting; it is not intended to provide sufficient information
   * to edit the character content of the original XML document.</p>
   *
   * <p>The return value is an approximation of the line number
   * in the document entity or external parsed entity where the
   * markup that triggered the event appears.</p>
   *
   * <p>
   *  返回当前文档事件结束的行号。
   * 
   * <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
   * 
   *  <p>返回值是触发事件的标记出现的文档实体或外部解析实体中行数的近似值。</p>
   * 
   * 
   * @return The line number, or -1 if none is available.
   * @see #getColumnNumber
   */
  public int getLineNumber()
  {
        if(null == m_parent)
          return 0;
        return m_parent.getLineNumber();
  }

  /**
   * Return the character position where the current document event ends.
   *
   * <p><strong>Warning:</strong> The return value from the method
   * is intended only as an approximation for the sake of error
   * reporting; it is not intended to provide sufficient information
   * to edit the character content of the original XML document.</p>
   *
   * <p>The return value is an approximation of the column number
   * in the document entity or external parsed entity where the
   * markup that triggered the event appears.</p>
   *
   * <p>
   *  返回当前文档事件结束的字符位置。
   * 
   *  <p> <strong>警告：</strong>该方法的返回值仅用作误差报告的近似值;它不打算提供足够的信息来编辑原始XML文档的字符内容。</p>
   * 
   * @return The column number, or -1 if none is available.
   * @see #getLineNumber
   */
  public int getColumnNumber()
  {
        if(null == m_parent)
          return 0;
        return m_parent.getColumnNumber();
  }
}
