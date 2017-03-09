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
 * $Id: ExsltDynamic.java,v 1.1.2.1 2005/08/01 02:08:51 jeffsuttor Exp $
 * <p>
 *  $ Id：ExsltDynamic.java,v 1.1.2.1 2005/08/01 02:08:51 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.lib;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xalan.internal.res.XSLTErrorResources;
import com.sun.org.apache.xpath.internal.NodeSet;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XBoolean;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.xml.sax.SAXNotSupportedException;

/**
 * This class contains EXSLT dynamic extension functions.
 *
 * It is accessed by specifying a namespace URI as follows:
 * <pre>
 *    xmlns:dyn="http://exslt.org/dynamic"
 * </pre>
 * The documentation for each function has been copied from the relevant
 * EXSLT Implementer page.
 *
 * <p>
 *  这个类包含EXSLT动态扩展函数。
 * 
 *  可通过指定名称空间URI来访问它,如下所示：
 * <pre>
 *  xmlns：dyn ="http://exslt.org/dynamic"
 * </pre>
 *  每个函数的文档已从相关的EXSLT实施者页面复制。
 * 
 * 
 * @see <a href="http://www.exslt.org/">EXSLT</a>

 * @xsl.usage general
 */
public class ExsltDynamic extends ExsltBase
{

   public static final String EXSL_URI = "http://exslt.org/common";

  /**
   * The dyn:max function calculates the maximum value for the nodes passed as
   * the first argument, where the value of each node is calculated dynamically
   * using an XPath expression passed as a string as the second argument.
   * <p>
   * The expressions are evaluated relative to the nodes passed as the first argument.
   * In other words, the value for each node is calculated by evaluating the XPath
   * expression with all context information being the same as that for the call to
   * the dyn:max function itself, except for the following:
   * <p>
   * <ul>
   *  <li>the context node is the node whose value is being calculated.</li>
   *  <li>the context position is the position of the node within the node set passed as
   *   the first argument to the dyn:max function, arranged in document order.</li>
   *  <li>the context size is the number of nodes passed as the first argument to the
   *   dyn:max function.</li>
   * </ul>
   * <p>
   * The dyn:max function returns the maximum of these values, calculated in exactly
   * the same way as for math:max.
   * <p>
   * If the expression string passed as the second argument is an invalid XPath
   * expression (including an empty string), this function returns NaN.
   * <p>
   * This function must take a second argument. To calculate the maximum of a set of
   * nodes based on their string values, you should use the math:max function.
   *
   * <p>
   *  dyn：max函数计算作为第一个参数传递的节点的最大值,其中每个节点的值使用作为第二个参数作为字符串传递的XPath表达式动态计算。
   * <p>
   * 表达式相对于作为第一个参数传递的节点进行求值。换句话说,通过评估XPath表达式计算每个节点的值,所有上下文信息与对dyn：max函数本身的调用相同,但以下情况除外：
   * <p>
   * <ul>
   *  <li>上下文位置是节点集中作为第一个参数传递给dyn：max函数的节点的位置,排列在文档中</li> <li>上下文大小是作为dyn：max函数的第一个参数传递的节点数。</li>
   * </ul>
   * <p>
   *  dyn：max函数返回这些值的最大值,以与math：max完全相同的方式计算。
   * <p>
   *  如果作为第二个参数传递的表达式字符串是无效的XPath表达式(包括空字符串),则此函数返回NaN。
   * <p>
   *  此函数必须使用第二个参数。要根据字符串值计算一组节点的最大值,应使用math：max函数。
   * 
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param nl The node set
   * @param expr The expression string
   *
   * @return The maximum evaluation value
   */
  public static double max(ExpressionContext myContext, NodeList nl, String expr)
    throws SAXNotSupportedException
  {

    XPathContext xctxt = null;
    if (myContext instanceof XPathContext.XPathExpressionContext)
      xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext }));

    if (expr == null || expr.length() == 0)
      return Double.NaN;

    NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
    xctxt.pushContextNodeList(contextNodes);

    double maxValue = - Double.MAX_VALUE;
    for (int i = 0; i < contextNodes.getLength(); i++)
    {
      int contextNode = contextNodes.item(i);
      xctxt.pushCurrentNode(contextNode);

      double result = 0;
      try
      {
        XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(),
                                       xctxt.getNamespaceContext(),
                                       XPath.SELECT);
        result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
      }
      catch (TransformerException e)
      {
        xctxt.popCurrentNode();
        xctxt.popContextNodeList();
        return Double.NaN;
      }

      xctxt.popCurrentNode();

      if (result > maxValue)
          maxValue = result;
    }

    xctxt.popContextNodeList();
    return maxValue;

  }

  /**
   * The dyn:min function calculates the minimum value for the nodes passed as the
   * first argument, where the value of each node is calculated dynamically using
   * an XPath expression passed as a string as the second argument.
   * <p>
   * The expressions are evaluated relative to the nodes passed as the first argument.
   * In other words, the value for each node is calculated by evaluating the XPath
   * expression with all context information being the same as that for the call to
   * the dyn:min function itself, except for the following:
   * <p>
   * <ul>
   *  <li>the context node is the node whose value is being calculated.</li>
   *  <li>the context position is the position of the node within the node set passed
   *    as the first argument to the dyn:min function, arranged in document order.</li>
   *  <li>the context size is the number of nodes passed as the first argument to the
   *    dyn:min function.</li>
   * </ul>
   * <p>
   * The dyn:min function returns the minimum of these values, calculated in exactly
   * the same way as for math:min.
   * <p>
   * If the expression string passed as the second argument is an invalid XPath expression
   * (including an empty string), this function returns NaN.
   * <p>
   * This function must take a second argument. To calculate the minimum of a set of
   * nodes based on their string values, you should use the math:min function.
   *
   * <p>
   *  dyn：min函数计算作为第一个参数传递的节点的最小值,其中每个节点的值是使用作为第二个参数的字符串传递的XPath表达式动态计算的。
   * <p>
   * 表达式相对于作为第一个参数传递的节点进行求值。换句话说,通过评估XPath表达式来计算每个节点的值,所有上下文信息与对dyn：min函数本身的调用相同,但以下情况除外：
   * <p>
   * <ul>
   *  <li>上下文位置是节点集中作为第一个参数传递给dyn：min函数的节点的位置,排列在文档中</li> <li>上下文大小是作为dyn：min函数的第一个参数传递的节点数。</li>
   * </ul>
   * <p>
   *  dyn：min函数返回这些值的最小值,以与math：min完全相同的方式计算。
   * <p>
   *  如果作为第二个参数传递的表达式字符串是无效的XPath表达式(包括空字符串),则此函数返回NaN。
   * <p>
   *  此函数必须使用第二个参数。要根据它们的字符串值计算一组节点的最小值,您应该使用math：min函数。
   * 
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param nl The node set
   * @param expr The expression string
   *
   * @return The minimum evaluation value
   */
  public static double min(ExpressionContext myContext, NodeList nl, String expr)
    throws SAXNotSupportedException
  {

    XPathContext xctxt = null;
    if (myContext instanceof XPathContext.XPathExpressionContext)
      xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext }));

    if (expr == null || expr.length() == 0)
      return Double.NaN;

    NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
    xctxt.pushContextNodeList(contextNodes);

    double minValue = Double.MAX_VALUE;
    for (int i = 0; i < nl.getLength(); i++)
    {
      int contextNode = contextNodes.item(i);
      xctxt.pushCurrentNode(contextNode);

      double result = 0;
      try
      {
        XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(),
                                       xctxt.getNamespaceContext(),
                                       XPath.SELECT);
        result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
      }
      catch (TransformerException e)
      {
        xctxt.popCurrentNode();
        xctxt.popContextNodeList();
        return Double.NaN;
      }

      xctxt.popCurrentNode();

      if (result < minValue)
          minValue = result;
    }

    xctxt.popContextNodeList();
    return minValue;

  }

  /**
   * The dyn:sum function calculates the sum for the nodes passed as the first argument,
   * where the value of each node is calculated dynamically using an XPath expression
   * passed as a string as the second argument.
   * <p>
   * The expressions are evaluated relative to the nodes passed as the first argument.
   * In other words, the value for each node is calculated by evaluating the XPath
   * expression with all context information being the same as that for the call to
   * the dyn:sum function itself, except for the following:
   * <p>
   * <ul>
   *  <li>the context node is the node whose value is being calculated.</li>
   *  <li>the context position is the position of the node within the node set passed as
   *    the first argument to the dyn:sum function, arranged in document order.</li>
   *  <li>the context size is the number of nodes passed as the first argument to the
   *    dyn:sum function.</li>
   * </ul>
   * <p>
   * The dyn:sum function returns the sumimum of these values, calculated in exactly
   * the same way as for sum.
   * <p>
   * If the expression string passed as the second argument is an invalid XPath
   * expression (including an empty string), this function returns NaN.
   * <p>
   * This function must take a second argument. To calculate the sumimum of a set of
   * nodes based on their string values, you should use the sum function.
   *
   * <p>
   *  dyn：sum函数计算作为第一个参数传递的节点的总和,其中每个节点的值是使用作为第二个参数的字符串传递的XPath表达式动态计算的。
   * <p>
   * 表达式相对于作为第一个参数传递的节点进行求值。换句话说,通过评估XPath表达式计算每个节点的值,其中所有上下文信息与对dyn：sum函数本身的调用相同,但以下情况除外：
   * <p>
   * <ul>
   *  <li>上下文位置是节点集合中作为第一个参数传递给dyn：sum函数的节点的位置,排列在文档中</li> <li>上下文大小是作为dyn：sum函数的第一个参数传递的节点数。</li>
   * </ul>
   * <p>
   *  dyn：sum函数返回这些值的最大值,按照与sum完全相同的方式计算。
   * <p>
   *  如果作为第二个参数传递的表达式字符串是无效的XPath表达式(包括空字符串),则此函数返回NaN。
   * <p>
   *  此函数必须使用第二个参数。要基于其字符串值计算一组节点的最大值,应使用sum函数。
   * 
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param nl The node set
   * @param expr The expression string
   *
   * @return The sum of the evaluation value on each node
   */
  public static double sum(ExpressionContext myContext, NodeList nl, String expr)
    throws SAXNotSupportedException
  {
    XPathContext xctxt = null;
    if (myContext instanceof XPathContext.XPathExpressionContext)
      xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext }));

    if (expr == null || expr.length() == 0)
      return Double.NaN;

    NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
    xctxt.pushContextNodeList(contextNodes);

    double sum = 0;
    for (int i = 0; i < nl.getLength(); i++)
    {
      int contextNode = contextNodes.item(i);
      xctxt.pushCurrentNode(contextNode);

      double result = 0;
      try
      {
        XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(),
                                       xctxt.getNamespaceContext(),
                                       XPath.SELECT);
        result = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext()).num();
      }
      catch (TransformerException e)
      {
        xctxt.popCurrentNode();
        xctxt.popContextNodeList();
        return Double.NaN;
      }

      xctxt.popCurrentNode();

      sum = sum + result;

    }

    xctxt.popContextNodeList();
    return sum;
  }

  /**
   * The dyn:map function evaluates the expression passed as the second argument for
   * each of the nodes passed as the first argument, and returns a node set of those values.
   * <p>
   * The expressions are evaluated relative to the nodes passed as the first argument.
   * In other words, the value for each node is calculated by evaluating the XPath
   * expression with all context information being the same as that for the call to
   * the dyn:map function itself, except for the following:
   * <p>
   * <ul>
   *  <li>The context node is the node whose value is being calculated.</li>
   *  <li>the context position is the position of the node within the node set passed
   *    as the first argument to the dyn:map function, arranged in document order.</li>
   *  <li>the context size is the number of nodes passed as the first argument to the
   *    dyn:map function.</li>
   * </ul>
   * <p>
   * If the expression string passed as the second argument is an invalid XPath
   * expression (including an empty string), this function returns an empty node set.
   * <p>
   * If the XPath expression evaluates as a node set, the dyn:map function returns
   * the union of the node sets returned by evaluating the expression for each of the
   * nodes in the first argument. Note that this may mean that the node set resulting
   * from the call to the dyn:map function contains a different number of nodes from
   * the number in the node set passed as the first argument to the function.
   * <p>
   * If the XPath expression evaluates as a number, the dyn:map function returns a
   * node set containing one exsl:number element (namespace http://exslt.org/common)
   * for each node in the node set passed as the first argument to the dyn:map function,
   * in document order. The string value of each exsl:number element is the same as
   * the result of converting the number resulting from evaluating the expression to
   * a string as with the number function, with the exception that Infinity results
   * in an exsl:number holding the highest number the implementation can store, and
   * -Infinity results in an exsl:number holding the lowest number the implementation
   * can store.
   * <p>
   * If the XPath expression evaluates as a boolean, the dyn:map function returns a
   * node set containing one exsl:boolean element (namespace http://exslt.org/common)
   * for each node in the node set passed as the first argument to the dyn:map function,
   * in document order. The string value of each exsl:boolean element is 'true' if the
   * expression evaluates as true for the node, and '' if the expression evaluates as
   * false.
   * <p>
   * Otherwise, the dyn:map function returns a node set containing one exsl:string
   * element (namespace http://exslt.org/common) for each node in the node set passed
   * as the first argument to the dyn:map function, in document order. The string
   * value of each exsl:string element is the same as the result of converting the
   * result of evaluating the expression for the relevant node to a string as with
   * the string function.
   *
   * <p>
   *  dyn：map函数计算作为第一个参数传递的每个节点的第二个参数传递的表达式,并返回这些值的节点集。
   * <p>
   *  表达式相对于作为第一个参数传递的节点进行求值。换句话说,通过评估XPath表达式来计算每个节点的值,其中所有上下文信息与调用dyn：map函数本身的情况相同,但以下情况除外：
   * <p>
   * <ul>
   * <li>上下文节点是要计算其值的节点。
   * </li> <li>上下文位置是节点集中作为第一个参数传递给dyn：map函数的节点的位置, </li> <li>上下文大小是作为dyn：map函数的第一个参数传递的节点数。</li>。
   * </ul>
   * <p>
   *  如果作为第二个参数传递的表达式字符串是无效的XPath表达式(包括空字符串),则此函数将返回空节点集。
   * <p>
   *  如果XPath表达式计算为节点集,则dyn：map函数返回通过计算第一个参数中每个节点的表达式而返回的节点集的并集。
   * 注意,这可能意味着从对dyn：map函数的调用导致的节点集包含与作为函数的第一个参数传递的节点集中的数字不同数量的节点。
   * <p>
   * 如果XPath表达式计算为数字,dyn：map函数返回一个包含一个exsl：number元素(命名空间http://exslt.org/common)的节点集,作为第一个参数传递给节点集中的每个节点dy
   * n：map函数,按文档顺序。
   * 每个exsl：number元素的字符串值与将使用number函数计算表达式得到的数字转换为字符串的结果相同,但Infinity会导致exsl：number中包含最大数字的实现可以存储,而-Infinit
   * y导致一个exsl：number保存实现可以存储的最小数字。
   * <p>
   *  如果XPath表达式计算为布尔值,则dyn：map函数返回一个节点集,该节点集包含作为第一个参数传递给节点集的每个节点的一个exsl：boolean元素(命名空间http://exslt.org/co
   * mmon) dyn：map函数,按文档顺序。
   * 如果表达式对节点求值为true,则每个exsl：boolean元素的字符串值为'true',如果表达式求值为false,则为''。
   * <p>
   *  否则,dyn：map函数返回一个节点集,该节点集包含作为第一个参数传递给dyn：map函数的节点集中每个节点的一个exsl：string元素(命名空间http://exslt.org/common)订
   * 购。
   * 每个exsl：string元素的字符串值与将相关节点的表达式计算结果转换为string函数的字符串的结果相同。
   * 
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param nl The node set
   * @param expr The expression string
   *
   * @return The node set after evaluation
   */
  public static NodeList map(ExpressionContext myContext, NodeList nl, String expr)
    throws SAXNotSupportedException
  {
    XPathContext xctxt = null;
    Document lDoc = null;

    if (myContext instanceof XPathContext.XPathExpressionContext)
      xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext }));

    if (expr == null || expr.length() == 0)
      return new NodeSet();

    NodeSetDTM contextNodes = new NodeSetDTM(nl, xctxt);
    xctxt.pushContextNodeList(contextNodes);

    NodeSet resultSet = new NodeSet();
    resultSet.setShouldCacheNodes(true);

    for (int i = 0; i < nl.getLength(); i++)
    {
      int contextNode = contextNodes.item(i);
      xctxt.pushCurrentNode(contextNode);

      XObject object = null;
      try
      {
        XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(),
                                       xctxt.getNamespaceContext(),
                                       XPath.SELECT);
        object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());

        if (object instanceof XNodeSet)
        {
          NodeList nodelist = null;
          nodelist = ((XNodeSet)object).nodelist();

          for (int k = 0; k < nodelist.getLength(); k++)
          {
            Node n = nodelist.item(k);
            if (!resultSet.contains(n))
              resultSet.addNode(n);
          }
        }
        else
        {
          if (lDoc == null)
          {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            lDoc = db.newDocument();
          }

          Element element = null;
          if (object instanceof XNumber)
            element = lDoc.createElementNS(EXSL_URI, "exsl:number");
          else if (object instanceof XBoolean)
            element = lDoc.createElementNS(EXSL_URI, "exsl:boolean");
          else
            element = lDoc.createElementNS(EXSL_URI, "exsl:string");

          Text textNode = lDoc.createTextNode(object.str());
          element.appendChild(textNode);
          resultSet.addNode(element);
        }
      }
      catch (Exception e)
      {
        xctxt.popCurrentNode();
        xctxt.popContextNodeList();
        return new NodeSet();
      }

      xctxt.popCurrentNode();

    }

    xctxt.popContextNodeList();
    return resultSet;
  }

  /**
   * The dyn:evaluate function evaluates a string as an XPath expression and returns
   * the resulting value, which might be a boolean, number, string, node set, result
   * tree fragment or external object. The sole argument is the string to be evaluated.
   * <p>
   * If the expression string passed as the second argument is an invalid XPath
   * expression (including an empty string), this function returns an empty node set.
   * <p>
   * You should only use this function if the expression must be constructed dynamically,
   * otherwise it is much more efficient to use the expression literally.
   *
   * <p>
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param xpathExpr The XPath expression string
   *
   * @return The evaluation result
   */
  public static XObject evaluate(ExpressionContext myContext, String xpathExpr)
    throws SAXNotSupportedException
  {
    if (myContext instanceof XPathContext.XPathExpressionContext)
    {
      XPathContext xctxt = null;
      try
      {
        xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
        XPath dynamicXPath = new XPath(xpathExpr, xctxt.getSAXLocator(),
                                       xctxt.getNamespaceContext(),
                                       XPath.SELECT);

        return dynamicXPath.execute(xctxt, myContext.getContextNode(),
                                    xctxt.getNamespaceContext());
      }
      catch (TransformerException e)
      {
        return new XNodeSet(xctxt.getDTMManager());
      }
    }
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext })); //"Invalid context passed to evaluate "
  }

  /**
   * The dyn:closure function creates a node set resulting from transitive closure of
   * evaluating the expression passed as the second argument on each of the nodes passed
   * as the first argument, then on the node set resulting from that and so on until no
   * more nodes are found. For example:
   * <pre>
   *  dyn:closure(., '*')
   * </pre>
   * returns all the descendant elements of the node (its element children, their
   * children, their children's children and so on).
   * <p>
   * The expression is thus evaluated several times, each with a different node set
   * acting as the context of the expression. The first time the expression is
   * evaluated, the context node set is the first argument passed to the dyn:closure
   * function. In other words, the node set for each node is calculated by evaluating
   * the XPath expression with all context information being the same as that for
   * the call to the dyn:closure function itself, except for the following:
   * <p>
   * <ul>
   *  <li>the context node is the node whose value is being calculated.</li>
   *  <li>the context position is the position of the node within the node set passed
   *    as the first argument to the dyn:closure function, arranged in document order.</li>
   *  <li>the context size is the number of nodes passed as the first argument to the
   *    dyn:closure function.</li>
   *  <li>the current node is the node whose value is being calculated.</li>
   * </ul>
   * <p>
   * The result for a particular iteration is the union of the node sets resulting
   * from evaluting the expression for each of the nodes in the source node set for
   * that iteration. This result is then used as the source node set for the next
   * iteration, and so on. The result of the function as a whole is the union of
   * the node sets generated by each iteration.
   * <p>
   * If the expression string passed as the second argument is an invalid XPath
   * expression (including an empty string) or an expression that does not return a
   * node set, this function returns an empty node set.
   *
   * <p>
   * dyn：evaluate函数将字符串计算为XPath表达式,并返回结果值,其可能是布尔值,数字,字符串,节点集,结果树片段或外部对象。唯一的参数是要评估的字符串。
   * <p>
   *  如果作为第二个参数传递的表达式字符串是无效的XPath表达式(包括空字符串),则此函数将返回空节点集。
   * <p>
   *  如果表达式必须动态构造,那么只应使用此函数,否则使用字面意义上的表达式要高效得多。
   * 
   * 
   * @param myContext The ExpressionContext passed by the extension processor
   * @param nl The node set
   * @param expr The expression string
   *
   * @return The node set after evaluation
   */
  public static NodeList closure(ExpressionContext myContext, NodeList nl, String expr)
    throws SAXNotSupportedException
  {
    XPathContext xctxt = null;
    if (myContext instanceof XPathContext.XPathExpressionContext)
      xctxt = ((XPathContext.XPathExpressionContext) myContext).getXPathContext();
    else
      throw new SAXNotSupportedException(XSLMessages.createMessage(XSLTErrorResources.ER_INVALID_CONTEXT_PASSED, new Object[]{myContext }));

    if (expr == null || expr.length() == 0)
      return new NodeSet();

    NodeSet closureSet = new NodeSet();
    closureSet.setShouldCacheNodes(true);

    NodeList iterationList = nl;
    do
    {

      NodeSet iterationSet = new NodeSet();

      NodeSetDTM contextNodes = new NodeSetDTM(iterationList, xctxt);
      xctxt.pushContextNodeList(contextNodes);

      for (int i = 0; i < iterationList.getLength(); i++)
      {
        int contextNode = contextNodes.item(i);
        xctxt.pushCurrentNode(contextNode);

        XObject object = null;
        try
        {
          XPath dynamicXPath = new XPath(expr, xctxt.getSAXLocator(),
                                         xctxt.getNamespaceContext(),
                                         XPath.SELECT);
          object = dynamicXPath.execute(xctxt, contextNode, xctxt.getNamespaceContext());

          if (object instanceof XNodeSet)
          {
            NodeList nodelist = null;
            nodelist = ((XNodeSet)object).nodelist();

            for (int k = 0; k < nodelist.getLength(); k++)
            {
              Node n = nodelist.item(k);
              if (!iterationSet.contains(n))
                iterationSet.addNode(n);
            }
          }
          else
          {
            xctxt.popCurrentNode();
            xctxt.popContextNodeList();
            return new NodeSet();
          }
        }
        catch (TransformerException e)
        {
          xctxt.popCurrentNode();
          xctxt.popContextNodeList();
          return new NodeSet();
        }

        xctxt.popCurrentNode();

      }

      xctxt.popContextNodeList();

      iterationList = iterationSet;

      for (int i = 0; i < iterationList.getLength(); i++)
      {
        Node n = iterationList.item(i);
        if (!closureSet.contains(n))
          closureSet.addNode(n);
      }

    } while(iterationList.getLength() > 0);

    return closureSet;

  }

}
