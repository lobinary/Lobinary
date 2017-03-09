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
 * $Id: FunctionDef1Arg.java,v 1.2.4.1 2005/09/14 20:18:42 jeffsuttor Exp $
 * <p>
 *  $ Id：FunctionDef1Arg.java,v 1.2.4.1 2005/09/14 20:18:42 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.functions;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 * Base class for functions that accept one argument that can be defaulted if
 * not specified.
 * @xsl.usage advanced
 * <p>
 *  接受一个参数的函数的基类,如果未指定,该参数可以是默认的。 @ xsl.usage advanced
 * 
 */
public class FunctionDef1Arg extends FunctionOneArg
{
    static final long serialVersionUID = 2325189412814149264L;

  /**
   * Execute the first argument expression that is expected to return a
   * nodeset.  If the argument is null, then return the current context node.
   *
   * <p>
   *  执行预期返回节点集的第一个参数表达式。如果参数为null,则返回当前上下文节点。
   * 
   * 
   * @param xctxt Runtime XPath context.
   *
   * @return The first node of the executed nodeset, or the current context
   *         node if the first argument is null.
   *
   * @throws javax.xml.transform.TransformerException if an error occurs while
   *                                   executing the argument expression.
   */
  protected int getArg0AsNode(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    return (null == m_arg0)
           ? xctxt.getCurrentNode() : m_arg0.asNode(xctxt);
  }

  /**
   * Tell if the expression is a nodeset expression.
   * <p>
   *  告诉表达式是否是一个nodeet表达式。
   * 
   * 
   * @return true if the expression can be represented as a nodeset.
   */
  public boolean Arg0IsNodesetExpr()
  {
    return (null == m_arg0) ? true : m_arg0.isNodesetExpr();
  }

  /**
   * Execute the first argument expression that is expected to return a
   * string.  If the argument is null, then get the string value from the
   * current context node.
   *
   * <p>
   *  执行期望返回字符串的第一个参数表达式。如果参数为null,则从当前上下文节点获取字符串值。
   * 
   * 
   * @param xctxt Runtime XPath context.
   *
   * @return The string value of the first argument, or the string value of the
   *         current context node if the first argument is null.
   *
   * @throws javax.xml.transform.TransformerException if an error occurs while
   *                                   executing the argument expression.
   */
  protected XMLString getArg0AsString(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    if(null == m_arg0)
    {
      int currentNode = xctxt.getCurrentNode();
      if(DTM.NULL == currentNode)
        return XString.EMPTYSTRING;
      else
      {
        DTM dtm = xctxt.getDTM(currentNode);
        return dtm.getStringValue(currentNode);
      }

    }
    else
      return m_arg0.execute(xctxt).xstr();
  }

  /**
   * Execute the first argument expression that is expected to return a
   * number.  If the argument is null, then get the number value from the
   * current context node.
   *
   * <p>
   *  执行预期返回数字的第一个参数表达式。如果参数为null,则从当前上下文节点获取数值。
   * 
   * 
   * @param xctxt Runtime XPath context.
   *
   * @return The number value of the first argument, or the number value of the
   *         current context node if the first argument is null.
   *
   * @throws javax.xml.transform.TransformerException if an error occurs while
   *                                   executing the argument expression.
   */
  protected double getArg0AsNumber(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    if(null == m_arg0)
    {
      int currentNode = xctxt.getCurrentNode();
      if(DTM.NULL == currentNode)
        return 0;
      else
      {
        DTM dtm = xctxt.getDTM(currentNode);
        XMLString str = dtm.getStringValue(currentNode);
        return str.toDouble();
      }

    }
    else
      return m_arg0.execute(xctxt).num();
  }

  /**
   * Check that the number of arguments passed to this function is correct.
   *
   * <p>
   *  检查传递给此函数的参数数是否正确。
   * 
   * 
   * @param argNum The number of arguments that is being passed to the function.
   *
   * @throws WrongNumberArgsException if the number of arguments is not 0 or 1.
   */
  public void checkNumberArgs(int argNum) throws WrongNumberArgsException
  {
    if (argNum > 1)
      reportWrongNumberArgs();
  }

  /**
   * Constructs and throws a WrongNumberArgException with the appropriate
   * message for this function object.
   *
   * <p>
   * 构造并抛出一个WrongNumberArgException与此函数对象的相应消息。
   * 
   * 
   * @throws WrongNumberArgsException
   */
  protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_ZERO_OR_ONE, null)); //"0 or 1");
  }

  /**
   * Tell if this expression or it's subexpressions can traverse outside
   * the current subtree.
   *
   * <p>
   *  告诉这个表达式或它的子表达式是否可以遍历当前子树。
   * 
   * @return true if traversal outside the context node's subtree can occur.
   */
  public boolean canTraverseOutsideSubtree()
  {
    return (null == m_arg0) ? false : super.canTraverseOutsideSubtree();
  }
}
