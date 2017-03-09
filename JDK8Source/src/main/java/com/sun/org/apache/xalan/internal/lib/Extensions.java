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
 * $Id: Extensions.java,v 1.2.4.1 2005/09/10 18:53:32 jeffsuttor Exp $
 * <p>
 *  $ Id：Extensions.java,v 1.2.4.1 2005/09/10 18:53:32 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.lib;

import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck;
import com.sun.org.apache.xpath.internal.NodeSet;
import com.sun.org.apache.xpath.internal.objects.XBoolean;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.traversal.NodeIterator;

import org.xml.sax.SAXNotSupportedException;

/**
 * This class contains many of the Xalan-supplied extensions.
 * It is accessed by specifying a namespace URI as follows:
 * <pre>
 *    xmlns:xalan="http://xml.apache.org/xalan"
 * </pre>
 * @xsl.usage general
 * <p>
 *  此类包含许多由Xalan提供的扩展。可通过指定名称空间URI来访问它,如下所示：
 * <pre>
 *  xmlns：xalan ="http://xml.apache.org/xalan"
 * </pre>
 *  @ xsl.usage general
 * 
 */
public class Extensions
{
    static final String JDK_DEFAULT_DOM = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";
  /**
   * Constructor Extensions
   *
   * <p>
   *  构造函数扩展
   * 
   */
  private Extensions(){}  // Make sure class cannot be instantiated

  /**
   * This method is an extension that implements as a Xalan extension
   * the node-set function also found in xt and saxon.
   * If the argument is a Result Tree Fragment, then <code>nodeset</code>
   * returns a node-set consisting of a single root node as described in
   * section 11.1 of the XSLT 1.0 Recommendation.  If the argument is a
   * node-set, <code>nodeset</code> returns a node-set.  If the argument
   * is a string, number, or boolean, then <code>nodeset</code> returns
   * a node-set consisting of a single root node with a single text node
   * child that is the result of calling the XPath string() function on the
   * passed parameter.  If the argument is anything else, then a node-set
   * is returned consisting of a single root node with a single text node
   * child that is the result of calling the java <code>toString()</code>
   * method on the passed argument.
   * Most of the
   * actual work here is done in <code>MethodResolver</code> and
   * <code>XRTreeFrag</code>.
   * <p>
   * 此方法是一个扩展,实现作为Xalan扩展的节点集函数也在xt和saxon中找到。
   * 如果参数是结果树片段,则<code> nodeset </code>返回由单个根节点组成的节点集,如XSLT 1.0建议书第11.1节所述。
   * 如果参数是节点集,则<code> nodeset </code>返回节点集。
   * 如果参数是字符串,数字或布尔值,则<code> nodeset </code>返回一个由单个根节点组成的节点集,其中有一个文本节点子节点,它是调用XPath string()函数的结果对传递的参数。
   * 如果参数是任何其他参数,则返回一个节点集,它由具有单个文本节点子节点的单个根节点组成,这是对传递的参数调用java <code> toString()</code>方法的结果。
   * 这里的大部分实际工作是在<code> MethodResolver </code>和<code> XRTreeFrag </code>中完成的。
   * 
   * 
   * @param myProcessor Context passed by the extension processor
   * @param rtf Argument in the stylesheet to the nodeset extension function
   *
   * NEEDSDOC ($objectName$) @return
   */
  public static NodeSet nodeset(ExpressionContext myProcessor, Object rtf)
  {

    String textNodeValue;

    if (rtf instanceof NodeIterator)
    {
      return new NodeSet((NodeIterator) rtf);
    }
    else
    {
      if (rtf instanceof String)
      {
        textNodeValue = (String) rtf;
      }
      else if (rtf instanceof Boolean)
      {
        textNodeValue = new XBoolean(((Boolean) rtf).booleanValue()).str();
      }
      else if (rtf instanceof Double)
      {
        textNodeValue = new XNumber(((Double) rtf).doubleValue()).str();
      }
      else
      {
        textNodeValue = rtf.toString();
      }

      // This no longer will work right since the DTM.
      // Document myDoc = myProcessor.getContextNode().getOwnerDocument();
      Document myDoc = getDocument();

        Text textNode = myDoc.createTextNode(textNodeValue);
        DocumentFragment docFrag = myDoc.createDocumentFragment();

        docFrag.appendChild(textNode);

      return new NodeSet(docFrag);
    }
  }

  /**
   * Returns the intersection of two node-sets.
   *
   * <p>
   *  返回两个节点集的交集。
   * 
   * 
   * @param nl1 NodeList for first node-set
   * @param nl2 NodeList for second node-set
   * @return a NodeList containing the nodes in nl1 that are also in nl2
   *
   * Note: The usage of this extension function in the xalan namespace
   * is deprecated. Please use the same function in the EXSLT sets extension
   * (http://exslt.org/sets).
   */
  public static NodeList intersection(NodeList nl1, NodeList nl2)
  {
    return ExsltSets.intersection(nl1, nl2);
  }

  /**
   * Returns the difference between two node-sets.
   *
   * <p>
   *  返回两个节点集之间的差异。
   * 
   * 
   * @param nl1 NodeList for first node-set
   * @param nl2 NodeList for second node-set
   * @return a NodeList containing the nodes in nl1 that are not in nl2
   *
   * Note: The usage of this extension function in the xalan namespace
   * is deprecated. Please use the same function in the EXSLT sets extension
   * (http://exslt.org/sets).
   */
  public static NodeList difference(NodeList nl1, NodeList nl2)
  {
    return ExsltSets.difference(nl1, nl2);
  }

  /**
   * Returns node-set containing distinct string values.
   *
   * <p>
   *  返回包含不同字符串值的节点集。
   * 
   * 
   * @param nl NodeList for node-set
   * @return a NodeList with nodes from nl containing distinct string values.
   * In other words, if more than one node in nl contains the same string value,
   * only include the first such node found.
   *
   * Note: The usage of this extension function in the xalan namespace
   * is deprecated. Please use the same function in the EXSLT sets extension
   * (http://exslt.org/sets).
   */
  public static NodeList distinct(NodeList nl)
  {
    return ExsltSets.distinct(nl);
  }

  /**
   * Returns true if both node-sets contain the same set of nodes.
   *
   * <p>
   *  如果两个节点集包含相同的节点集,则返回true。
   * 
   * 
   * @param nl1 NodeList for first node-set
   * @param nl2 NodeList for second node-set
   * @return true if nl1 and nl2 contain exactly the same set of nodes.
   */
  public static boolean hasSameNodes(NodeList nl1, NodeList nl2)
  {

    NodeSet ns1 = new NodeSet(nl1);
    NodeSet ns2 = new NodeSet(nl2);

    if (ns1.getLength() != ns2.getLength())
      return false;

    for (int i = 0; i < ns1.getLength(); i++)
    {
      Node n = ns1.elementAt(i);

      if (!ns2.contains(n))
        return false;
    }

    return true;
  }

  /**
   * Returns the result of evaluating the argument as a string containing
   * an XPath expression.  Used where the XPath expression is not known until
   * run-time.  The expression is evaluated as if the run-time value of the
   * argument appeared in place of the evaluate function call at compile time.
   *
   * <p>
   *  返回将参数计算为包含XPath表达式的字符串的结果。用于XPath表达式在运行时之前未知的位置。该表达式的计算方式与在编译时出现的参数的运行时值代替evaluate函数调用一样。
   * 
   * 
   * @param myContext an <code>ExpressionContext</code> passed in by the
   *                  extension mechanism.  This must be an XPathContext.
   * @param xpathExpr The XPath expression to be evaluated.
   * @return the XObject resulting from evaluating the XPath
   *
   * @throws SAXNotSupportedException
   *
   * Note: The usage of this extension function in the xalan namespace
   * is deprecated. Please use the same function in the EXSLT dynamic extension
   * (http://exslt.org/dynamic).
   */
  public static XObject evaluate(ExpressionContext myContext, String xpathExpr)
         throws SAXNotSupportedException
  {
    return ExsltDynamic.evaluate(myContext, xpathExpr);
  }

  /**
   * Returns a NodeSet containing one text node for each token in the first argument.
   * Delimiters are specified in the second argument.
   * Tokens are determined by a call to <code>StringTokenizer</code>.
   * If the first argument is an empty string or contains only delimiters, the result
   * will be an empty NodeSet.
   *
   * Contributed to XalanJ1 by <a href="mailto:benoit.cerrina@writeme.com">Benoit Cerrina</a>.
   *
   * <p>
   * 返回包含第一个参数中每个标记的一个文本节点的NodeSet。在第二个参数中指定分隔符。令牌由调用<code> StringTokenizer </code>确定。
   * 如果第一个参数是空字符串或仅包含定界符,则结果将是空的NodeSet。
   * 
   *  通过<a href="mailto:benoit.cerrina@writeme.com"> Benoit Cerrina </a>贡献给XalanJ1。
   * 
   * 
   * @param toTokenize The string to be split into text tokens.
   * @param delims The delimiters to use.
   * @return a NodeSet as described above.
   */
  public static NodeList tokenize(String toTokenize, String delims)
  {

    Document doc = getDocument();

    StringTokenizer lTokenizer = new StringTokenizer(toTokenize, delims);
    NodeSet resultSet = new NodeSet();

    synchronized (doc)
    {
      while (lTokenizer.hasMoreTokens())
      {
        resultSet.addNode(doc.createTextNode(lTokenizer.nextToken()));
      }
    }

    return resultSet;
  }

  /**
   * Returns a NodeSet containing one text node for each token in the first argument.
   * Delimiters are whitespace.  That is, the delimiters that are used are tab (&#x09),
   * linefeed (&#x0A), return (&#x0D), and space (&#x20).
   * Tokens are determined by a call to <code>StringTokenizer</code>.
   * If the first argument is an empty string or contains only delimiters, the result
   * will be an empty NodeSet.
   *
   * Contributed to XalanJ1 by <a href="mailto:benoit.cerrina@writeme.com">Benoit Cerrina</a>.
   *
   * <p>
   *  返回包含第一个参数中每个标记的一个文本节点的NodeSet。分隔符是空格。
   * 也就是说,使用的分隔符是tab(&#x09),linefeed(&#x0A),return(&#x0D)和空格(&#x20)。
   * 令牌由调用<code> StringTokenizer </code>确定。如果第一个参数是空字符串或仅包含定界符,则结果将是空的NodeSet。
   * 
   *  通过<a href="mailto:benoit.cerrina@writeme.com"> Benoit Cerrina </a>贡献给XalanJ1。
   * 
   * 
   * @param toTokenize The string to be split into text tokens.
   * @return a NodeSet as described above.
   */
  public static NodeList tokenize(String toTokenize)
  {
    return tokenize(toTokenize, " \t\n\r");
  }

  /**
   * Return a Node of basic debugging information from the
   * EnvironmentCheck utility about the Java environment.
   *
   * <p>Simply calls the {@link com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck}
   * utility to grab info about the Java environment and CLASSPATH,
   * etc., and then returns the resulting Node.  Stylesheets can
   * then maniuplate this data or simply xsl:copy-of the Node.  Note
   * that we first attempt to load the more advanced
   * org.apache.env.Which utility by reflection; only if that fails
   * to we still use the internal version.  Which is available from
   * <a href="http://xml.apache.org/commons/">http://xml.apache.org/commons/</a>.</p>
   *
   * <p>We throw a WrappedRuntimeException in the unlikely case
   * that reading information from the environment throws us an
   * exception. (Is this really the best thing to do?)</p>
   *
   * <p>
   *  从EnvironmentCheck实用程序返回一个关于Java环境的基本调试信息的节点。
   * 
   * <p>只需调用{@link com.sun.org.apache.xalan.internal.xslt.EnvironmentCheck}实用程序来获取有关Java环境和CLASSPATH等的信息,然
   * 后返回生成的节点。
   * 样式表然后可以对该数据进行合并,或者简单地使用节点的xsl：copy。注意,我们首先尝试通过反射加载更高级的org.apache.env.Which实用程序;只有如果失败我们仍然使用内部版本。
   * 其中可从<a href="http://xml.apache.org/commons/"> http://xml.apache.org/commons/ </a>获取。</p>。
   * 
   * 
   * @param myContext an <code>ExpressionContext</code> passed in by the
   *                  extension mechanism.  This must be an XPathContext.
   * @return a Node as described above.
   */
  public static Node checkEnvironment(ExpressionContext myContext)
  {

    Document factoryDocument = getDocument();

    Node resultNode = null;
    try
    {
      // First use reflection to try to load Which, which is a
      //  better version of EnvironmentCheck
      resultNode = checkEnvironmentUsingWhich(myContext, factoryDocument);

      if (null != resultNode)
        return resultNode;

      // If reflection failed, fallback to our internal EnvironmentCheck
      EnvironmentCheck envChecker = new EnvironmentCheck();
      Hashtable h = envChecker.getEnvironmentHash();
      resultNode = factoryDocument.createElement("checkEnvironmentExtension");
      envChecker.appendEnvironmentReport(resultNode, factoryDocument, h);
      envChecker = null;
    }
    catch(Exception e)
    {
      throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(e);
    }

    return resultNode;
  }

  /**
   * Private worker method to attempt to use org.apache.env.Which.
   *
   * <p>
   *  <p>我们抛出一个WrappedRuntimeException在不太可能的情况下,从环境中读取信息抛出我们的异常。 (这是真的最好的事吗?)</p>
   * 
   * 
   * @param myContext an <code>ExpressionContext</code> passed in by the
   *                  extension mechanism.  This must be an XPathContext.
   * @param factoryDocument providing createElement services, etc.
   * @return a Node with environment info; null if any error
   */
  private static Node checkEnvironmentUsingWhich(ExpressionContext myContext,
        Document factoryDocument)
  {
    final String WHICH_CLASSNAME = "org.apache.env.Which";
    final String WHICH_METHODNAME = "which";
    final Class WHICH_METHOD_ARGS[] = { java.util.Hashtable.class,
                                        java.lang.String.class,
                                        java.lang.String.class };
    try
    {
      // Use reflection to try to find xml-commons utility 'Which'
      Class clazz = ObjectFactory.findProviderClass(WHICH_CLASSNAME, true);
      if (null == clazz)
        return null;

      // Fully qualify names since this is the only method they're used in
      java.lang.reflect.Method method = clazz.getMethod(WHICH_METHODNAME, WHICH_METHOD_ARGS);
      Hashtable report = new Hashtable();

      // Call the method with our Hashtable, common options, and ignore return value
      Object[] methodArgs = { report, "XmlCommons;Xalan;Xerces;Crimson;Ant", "" };
      Object returnValue = method.invoke(null, methodArgs);

      // Create a parent to hold the report and append hash to it
      Node resultNode = factoryDocument.createElement("checkEnvironmentExtension");
      com.sun.org.apache.xml.internal.utils.Hashtree2Node.appendHashToNode(report, "whichReport",
            resultNode, factoryDocument);

      return resultNode;
    }
    catch (Throwable t)
    {
      // Simply return null; no need to report error
      return null;
    }
  }

    /**
    /* <p>
    /*  私人工作方法试图使用org.apache.env.Hhich。
    /* 
    /* 
   * @return an instance of DOM Document
     */
   private static Document getDocument()
   {
        try
        {
            if (System.getSecurityManager() == null) {
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            } else {
                return DocumentBuilderFactory.newInstance(JDK_DEFAULT_DOM, null).newDocumentBuilder().newDocument();
            }
        }
        catch(ParserConfigurationException pce)
        {
            throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(pce);
        }
    }
}
