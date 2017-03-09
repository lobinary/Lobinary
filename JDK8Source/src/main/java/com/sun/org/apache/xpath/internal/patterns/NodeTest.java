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
 * $Id: NodeTest.java,v 1.2.4.2 2005/09/15 00:21:14 jeffsuttor Exp $
 * <p>
 *  $ Id：NodeTest.java,v 1.2.4.2 2005/09/15 00:21:14 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.patterns;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMFilter;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * This is the basic node test class for both match patterns and location path
 * steps.
 * @xsl.usage advanced
 * <p>
 *  这是匹配模式和位置​​路径步骤的基本节点测试类。 @ xsl.usage advanced
 * 
 */
public class NodeTest extends Expression
{
    static final long serialVersionUID = -5736721866747906182L;

  /**
   * The namespace or local name for node tests with a wildcard.
   * <p>
   *  使用通配符的节点测试的命名空间或本地名称。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xpath#NT-NameTest">the XPath NameTest production.</a>
   */
  public static final String WILD = "*";

  /**
   * The URL to pass to the Node#supports method, to see if the
   * DOM has already been stripped of whitespace nodes.
   * <p>
   *  传递给Node#的URL支持方法,以查看DOM是否已经去除了空白节点。
   * 
   */
  public static final String SUPPORTS_PRE_STRIPPING =
    "http://xml.apache.org/xpath/features/whitespace-pre-stripping";

  /**
   * This attribute determines which node types are accepted.
   * <p>
   *  此属性确定接受哪些节点类型。
   * 
   * 
   * @serial
   */
  protected int m_whatToShow;

  /**
   * Special bitmap for match patterns starting with a function.
   * Make sure this does not conflict with {@link org.w3c.dom.traversal.NodeFilter}.
   * <p>
   *  用于从函数开始的匹配模式的特殊位图。请确保这不与{@link org.w3c.dom.traversal.NodeFilter}冲突。
   * 
   */
  public static final int SHOW_BYFUNCTION = 0x00010000;

  /**
   * This attribute determines which node types are accepted.
   * These constants are defined in the {@link org.w3c.dom.traversal.NodeFilter}
   * interface.
   *
   * <p>
   *  此属性确定接受哪些节点类型。这些常量在{@link org.w3c.dom.traversal.NodeFilter}接口中定义。
   * 
   * 
   * @return bitset mainly defined in {@link org.w3c.dom.traversal.NodeFilter}.
   */
  public int getWhatToShow()
  {
    return m_whatToShow;
  }

  /**
   * This attribute determines which node types are accepted.
   * These constants are defined in the {@link org.w3c.dom.traversal.NodeFilter}
   * interface.
   *
   * <p>
   * 此属性确定接受哪些节点类型。这些常量在{@link org.w3c.dom.traversal.NodeFilter}接口中定义。
   * 
   * 
   * @param what bitset mainly defined in {@link org.w3c.dom.traversal.NodeFilter}.
   */
  public void setWhatToShow(int what)
  {
    m_whatToShow = what;
  }

  /**
   * The namespace to be tested for, which may be null.
   * <p>
   *  要测试的名称空间,可以为null。
   * 
   * 
   *  @serial
   */
  String m_namespace;

  /**
   * Return the namespace to be tested.
   *
   * <p>
   *  返回要测试的命名空间。
   * 
   * 
   * @return The namespace to be tested for, or {@link #WILD}, or null.
   */
  public String getNamespace()
  {
    return m_namespace;
  }

  /**
   * Set the namespace to be tested.
   *
   * <p>
   *  设置要测试的命名空间。
   * 
   * 
   * @param ns The namespace to be tested for, or {@link #WILD}, or null.
   */
  public void setNamespace(String ns)
  {
    m_namespace = ns;
  }

  /**
   * The local name to be tested for.
   * <p>
   *  要测试的本地名称。
   * 
   * 
   *  @serial
   */
  protected String m_name;

  /**
   * Return the local name to be tested.
   *
   * <p>
   *  返回要测试的本地名称。
   * 
   * 
   * @return the local name to be tested, or {@link #WILD}, or an empty string.
   */
  public String getLocalName()
  {
    return (null == m_name) ? "" : m_name;
  }

  /**
   * Set the local name to be tested.
   *
   * <p>
   *  设置要测试的本地名。
   * 
   * 
   * @param name the local name to be tested, or {@link #WILD}, or an empty string.
   */
  public void setLocalName(String name)
  {
    m_name = name;
  }

  /**
   * Statically calculated score for this test.  One of
   *  {@link #SCORE_NODETEST},
   *  {@link #SCORE_NONE},
   *  {@link #SCORE_NSWILD},
   *  {@link #SCORE_QNAME}, or
   *  {@link #SCORE_OTHER}.
   * <p>
   *  该测试的静态计算得分。
   *  {@link #SCORE_NODETEST},{@link #SCORE_NONE},{@link #SCORE_NSWILD},{@link #SCORE_QNAME}或{@link #SCORE_OTHER}
   * 之一。
   *  该测试的静态计算得分。
   * 
   * 
   *  @serial
   */
  XNumber m_score;

  /**
   * The match score if the pattern consists of just a NodeTest.
   * <p>
   *  如果模式仅由NodeTest组成,则匹配分数。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution for Template Rules</a>
   */
  public static final XNumber SCORE_NODETEST =
    new XNumber(XPath.MATCH_SCORE_NODETEST);

  /**
   * The match score if the pattern pattern has the form NCName:*.
   * <p>
   *  如果模式模式具有NCName：*形式,则匹配分数。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution for Template Rules</a>
   */
  public static final XNumber SCORE_NSWILD =
    new XNumber(XPath.MATCH_SCORE_NSWILD);

  /**
   * The match score if the pattern has the form
   * of a QName optionally preceded by an @ character.
   * <p>
   *  如果模式具有QName的形式(可选地前面带有@字符),则匹配分数。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution for Template Rules</a>
   */
  public static final XNumber SCORE_QNAME =
    new XNumber(XPath.MATCH_SCORE_QNAME);

  /**
   * The match score if the pattern consists of something
   * other than just a NodeTest or just a qname.
   * <p>
   *  如果模式由除NodeTest之外的其他内容或仅由qname组成的匹配分数。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution for Template Rules</a>
   */
  public static final XNumber SCORE_OTHER =
    new XNumber(XPath.MATCH_SCORE_OTHER);

  /**
   * The match score if no match is made.
   * <p>
   *  如果没有匹配的匹配分数。
   * 
   * 
   *  @see <a href="http://www.w3.org/TR/xslt#conflict">XSLT Specification - 5.5 Conflict Resolution for Template Rules</a>
   */
  public static final XNumber SCORE_NONE =
    new XNumber(XPath.MATCH_SCORE_NONE);

  /**
   * Construct an NodeTest that tests for namespaces and node names.
   *
   *
   * <p>
   *  构造一个用于测试名称空间和节点名称的NodeTest。
   * 
   * 
   * @param whatToShow Bit set defined mainly by {@link org.w3c.dom.traversal.NodeFilter}.
   * @param namespace The namespace to be tested.
   * @param name The local name to be tested.
   */
  public NodeTest(int whatToShow, String namespace, String name)
  {
    initNodeTest(whatToShow, namespace, name);
  }

  /**
   * Construct an NodeTest that doesn't test for node names.
   *
   *
   * <p>
   *  构造不测试节点名称的NodeTest。
   * 
   * 
   * @param whatToShow Bit set defined mainly by {@link org.w3c.dom.traversal.NodeFilter}.
   */
  public NodeTest(int whatToShow)
  {
    initNodeTest(whatToShow);
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

        NodeTest nt = (NodeTest)expr;

        if(null != nt.m_name)
        {
                if(null == m_name)
                        return false;
                else if(!nt.m_name.equals(m_name))
                        return false;
        }
        else if(null != m_name)
                return false;

        if(null != nt.m_namespace)
        {
                if(null == m_namespace)
                        return false;
                else if(!nt.m_namespace.equals(m_namespace))
                        return false;
        }
        else if(null != m_namespace)
                return false;

        if(m_whatToShow != nt.m_whatToShow)
                return false;

        if(m_isTotallyWild != nt.m_isTotallyWild)
                return false;

        return true;
  }

  /**
   * Null argument constructor.
   * <p>
   *  空参数构造函数。
   * 
   */
  public NodeTest(){}

  /**
   * Initialize this node test by setting the whatToShow property, and
   * calculating the score that this test will return if a test succeeds.
   *
   *
   * <p>
   *  通过设置whatToShow属性来初始化此节点测试,并计算测试成功时此测试将返回的分数。
   * 
   * 
   * @param whatToShow Bit set defined mainly by {@link org.w3c.dom.traversal.NodeFilter}.
   */
  public void initNodeTest(int whatToShow)
  {

    m_whatToShow = whatToShow;

    calcScore();
  }

  /**
   * Initialize this node test by setting the whatToShow property and the
   * namespace and local name, and
   * calculating the score that this test will return if a test succeeds.
   *
   *
   * <p>
   *  通过设置whatToShow属性以及命名空间和本地名称,并计算测试成功时此测试将返回的分数来初始化此节点测试。
   * 
   * 
   * @param whatToShow Bit set defined mainly by {@link org.w3c.dom.traversal.NodeFilter}.
   * @param namespace The namespace to be tested.
   * @param name The local name to be tested.
   */
  public void initNodeTest(int whatToShow, String namespace, String name)
  {

    m_whatToShow = whatToShow;
    m_namespace = namespace;
    m_name = name;

    calcScore();
  }

  /**
   * True if this test has a null namespace and a local name of {@link #WILD}.
   * <p>
   *  如果此测试具有空命名空间和本地名称{@link #WILD},则为true。
   * 
   * 
   *  @serial
   */
  private boolean m_isTotallyWild;

  /**
   * Get the static score for this node test.
   * <p>
   *  获取此节点测试的静态分数。
   * 
   * 
   * @return Should be one of the SCORE_XXX constants.
   */
  public XNumber getStaticScore()
  {
    return m_score;
  }

  /**
   * Set the static score for this node test.
   * <p>
   * 设置此节点测试的静态分数。
   * 
   * 
   * @param score Should be one of the SCORE_XXX constants.
   */
  public void setStaticScore(XNumber score)
  {
    m_score = score;
  }

  /**
   * Static calc of match score.
   * <p>
   *  静态计算匹配得分。
   * 
   */
  protected void calcScore()
  {

    if ((m_namespace == null) && (m_name == null))
      m_score = SCORE_NODETEST;
    else if (((m_namespace == WILD) || (m_namespace == null))
             && (m_name == WILD))
      m_score = SCORE_NODETEST;
    else if ((m_namespace != WILD) && (m_name == WILD))
      m_score = SCORE_NSWILD;
    else
      m_score = SCORE_QNAME;

    m_isTotallyWild = (m_namespace == null && m_name == WILD);
  }

  /**
   * Get the score that this test will return if a test succeeds.
   *
   *
   * <p>
   *  获取测试成功后此测试将返回的分数。
   * 
   * 
   * @return the score that this test will return if a test succeeds.
   */
  public double getDefaultScore()
  {
    return m_score.num();
  }

  /**
   * Tell what node type to test, if not DTMFilter.SHOW_ALL.
   *
   * <p>
   *  告诉测试什么节点类型,如果没有DTMFilter.SHOW_ALL。
   * 
   * 
   * @param whatToShow Bit set defined mainly by
   *        {@link com.sun.org.apache.xml.internal.dtm.DTMFilter}.
   * @return the node type for the whatToShow.  Since whatToShow can specify
   *         multiple types, it will return the first bit tested that is on,
   *         so the caller of this function should take care that this is
   *         the function they really want to call.  If none of the known bits
   *         are set, this function will return zero.
   */
  public static int getNodeTypeTest(int whatToShow)
  {
    // %REVIEW% Is there a better way?
    if (0 != (whatToShow & DTMFilter.SHOW_ELEMENT))
      return DTM.ELEMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ATTRIBUTE))
      return DTM.ATTRIBUTE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_TEXT))
      return DTM.TEXT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT))
      return DTM.DOCUMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_FRAGMENT))
      return DTM.DOCUMENT_FRAGMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_NAMESPACE))
      return DTM.NAMESPACE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_COMMENT))
      return DTM.COMMENT_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_PROCESSING_INSTRUCTION))
      return DTM.PROCESSING_INSTRUCTION_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_TYPE))
      return DTM.DOCUMENT_TYPE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY))
      return DTM.ENTITY_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY_REFERENCE))
      return DTM.ENTITY_REFERENCE_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_NOTATION))
      return DTM.NOTATION_NODE;

    if (0 != (whatToShow & DTMFilter.SHOW_CDATA_SECTION))
      return DTM.CDATA_SECTION_NODE;


    return 0;
  }


  /**
   * Do a diagnostics dump of a whatToShow bit set.
   *
   *
   * <p>
   *  执行whatToShow位设置的诊断转储。
   * 
   * 
   * @param whatToShow Bit set defined mainly by
   *        {@link com.sun.org.apache.xml.internal.dtm.DTMFilter}.
   */
  public static void debugWhatToShow(int whatToShow)
  {

    java.util.Vector v = new java.util.Vector();

    if (0 != (whatToShow & DTMFilter.SHOW_ATTRIBUTE))
      v.addElement("SHOW_ATTRIBUTE");

    if (0 != (whatToShow & DTMFilter.SHOW_NAMESPACE))
      v.addElement("SHOW_NAMESPACE");

    if (0 != (whatToShow & DTMFilter.SHOW_CDATA_SECTION))
      v.addElement("SHOW_CDATA_SECTION");

    if (0 != (whatToShow & DTMFilter.SHOW_COMMENT))
      v.addElement("SHOW_COMMENT");

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT))
      v.addElement("SHOW_DOCUMENT");

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_FRAGMENT))
      v.addElement("SHOW_DOCUMENT_FRAGMENT");

    if (0 != (whatToShow & DTMFilter.SHOW_DOCUMENT_TYPE))
      v.addElement("SHOW_DOCUMENT_TYPE");

    if (0 != (whatToShow & DTMFilter.SHOW_ELEMENT))
      v.addElement("SHOW_ELEMENT");

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY))
      v.addElement("SHOW_ENTITY");

    if (0 != (whatToShow & DTMFilter.SHOW_ENTITY_REFERENCE))
      v.addElement("SHOW_ENTITY_REFERENCE");

    if (0 != (whatToShow & DTMFilter.SHOW_NOTATION))
      v.addElement("SHOW_NOTATION");

    if (0 != (whatToShow & DTMFilter.SHOW_PROCESSING_INSTRUCTION))
      v.addElement("SHOW_PROCESSING_INSTRUCTION");

    if (0 != (whatToShow & DTMFilter.SHOW_TEXT))
      v.addElement("SHOW_TEXT");

    int n = v.size();

    for (int i = 0; i < n; i++)
    {
      if (i > 0)
        System.out.print(" | ");

      System.out.print(v.elementAt(i));
    }

    if (0 == n)
      System.out.print("empty whatToShow: " + whatToShow);

    System.out.println();
  }

  /**
   * Two names are equal if they and either both are null or
   * the name t is wild and the name p is non-null, or the two
   * strings are equal.
   *
   * <p>
   *  如果两个名称都为空或名称t为wild并且名称p为非空,或两个字符串相等,则两个名称相等。
   * 
   * 
   * @param p part string from the node.
   * @param t target string, which may be {@link #WILD}.
   *
   * @return true if the strings match according to the rules of this method.
   */
  private static final boolean subPartMatch(String p, String t)
  {

    // boolean b = (p == t) || ((null != p) && ((t == WILD) || p.equals(t)));
    // System.out.println("subPartMatch - p: "+p+", t: "+t+", result: "+b);
    return (p == t) || ((null != p) && ((t == WILD) || p.equals(t)));
  }

  /**
   * This is temporary to patch over Xerces issue with representing DOM
   * namespaces as "".
   *
   * <p>
   *  这是临时的补丁Xerces问题,将DOM命名空间表示为""。
   * 
   * 
   * @param p part string from the node, which may represent the null namespace
   *        as null or as "".
   * @param t target string, which may be {@link #WILD}.
   *
   * @return true if the strings match according to the rules of this method.
   */
  private static final boolean subPartMatchNS(String p, String t)
  {

    return (p == t)
           || ((null != p)
               && ((p.length() > 0)
                   ? ((t == WILD) || p.equals(t)) : null == t));
  }

  /**
   * Tell what the test score is for the given node.
   *
   *
   * <p>
   *  告诉给定节点的测试分数。
   * 
   * 
   * @param xctxt XPath runtime context.
   * @param context The node being tested.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, int context)
          throws javax.xml.transform.TransformerException
  {

    DTM dtm = xctxt.getDTM(context);
    short nodeType = dtm.getNodeType(context);

    if (m_whatToShow == DTMFilter.SHOW_ALL)
      return m_score;

    int nodeBit = (m_whatToShow & (0x00000001 << (nodeType - 1)));

    switch (nodeBit)
    {
    case DTMFilter.SHOW_DOCUMENT_FRAGMENT :
    case DTMFilter.SHOW_DOCUMENT :
      return SCORE_OTHER;
    case DTMFilter.SHOW_COMMENT :
      return m_score;
    case DTMFilter.SHOW_CDATA_SECTION :
    case DTMFilter.SHOW_TEXT :

      // was:
      // return (!xctxt.getDOMHelper().shouldStripSourceNode(context))
      //       ? m_score : SCORE_NONE;
      return m_score;
    case DTMFilter.SHOW_PROCESSING_INSTRUCTION :
      return subPartMatch(dtm.getNodeName(context), m_name)
             ? m_score : SCORE_NONE;

    // From the draft: "Two expanded names are equal if they
    // have the same local part, and either both have no URI or
    // both have the same URI."
    // "A node test * is true for any node of the principal node type.
    // For example, child::* will select all element children of the
    // context node, and attribute::* will select all attributes of
    // the context node."
    // "A node test can have the form NCName:*. In this case, the prefix
    // is expanded in the same way as with a QName using the context
    // namespace declarations. The node test will be true for any node
    // of the principal type whose expanded name has the URI to which
    // the prefix expands, regardless of the local part of the name."
    case DTMFilter.SHOW_NAMESPACE :
    {
      String ns = dtm.getLocalName(context);

      return (subPartMatch(ns, m_name)) ? m_score : SCORE_NONE;
    }
    case DTMFilter.SHOW_ATTRIBUTE :
    case DTMFilter.SHOW_ELEMENT :
    {
      return (m_isTotallyWild || (subPartMatchNS(dtm.getNamespaceURI(context), m_namespace) && subPartMatch(dtm.getLocalName(context), m_name)))
             ? m_score : SCORE_NONE;
    }
    default :
      return SCORE_NONE;
    }  // end switch(testType)
  }

  /**
   * Tell what the test score is for the given node.
   *
   *
   * <p>
   *  告诉给定节点的测试分数。
   * 
   * 
   * @param xctxt XPath runtime context.
   * @param context The node being tested.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt, int context,
                         DTM dtm, int expType)
          throws javax.xml.transform.TransformerException
  {

    if (m_whatToShow == DTMFilter.SHOW_ALL)
      return m_score;

    int nodeBit = (m_whatToShow & (0x00000001
                   << ((dtm.getNodeType(context)) - 1)));

    switch (nodeBit)
    {
    case DTMFilter.SHOW_DOCUMENT_FRAGMENT :
    case DTMFilter.SHOW_DOCUMENT :
      return SCORE_OTHER;
    case DTMFilter.SHOW_COMMENT :
      return m_score;
    case DTMFilter.SHOW_CDATA_SECTION :
    case DTMFilter.SHOW_TEXT :

      // was:
      // return (!xctxt.getDOMHelper().shouldStripSourceNode(context))
      //       ? m_score : SCORE_NONE;
      return m_score;
    case DTMFilter.SHOW_PROCESSING_INSTRUCTION :
      return subPartMatch(dtm.getNodeName(context), m_name)
             ? m_score : SCORE_NONE;

    // From the draft: "Two expanded names are equal if they
    // have the same local part, and either both have no URI or
    // both have the same URI."
    // "A node test * is true for any node of the principal node type.
    // For example, child::* will select all element children of the
    // context node, and attribute::* will select all attributes of
    // the context node."
    // "A node test can have the form NCName:*. In this case, the prefix
    // is expanded in the same way as with a QName using the context
    // namespace declarations. The node test will be true for any node
    // of the principal type whose expanded name has the URI to which
    // the prefix expands, regardless of the local part of the name."
    case DTMFilter.SHOW_NAMESPACE :
    {
      String ns = dtm.getLocalName(context);

      return (subPartMatch(ns, m_name)) ? m_score : SCORE_NONE;
    }
    case DTMFilter.SHOW_ATTRIBUTE :
    case DTMFilter.SHOW_ELEMENT :
    {
      return (m_isTotallyWild || (subPartMatchNS(dtm.getNamespaceURI(context), m_namespace) && subPartMatch(dtm.getLocalName(context), m_name)))
             ? m_score : SCORE_NONE;
    }
    default :
      return SCORE_NONE;
    }  // end switch(testType)
  }

  /**
   * Test the current node to see if it matches the given node test.
   *
   * <p>
   *  测试当前节点,看看它是否匹配给定的节点测试。
   * 
   * 
   * @param xctxt XPath runtime context.
   *
   * @return {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NODETEST},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NONE},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_NSWILD},
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_QNAME}, or
   *         {@link com.sun.org.apache.xpath.internal.patterns.NodeTest#SCORE_OTHER}.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return execute(xctxt, xctxt.getCurrentNode());
  }

  /**
   * Node tests by themselves do not need to fix up variables.
   * <p>
   *  节点测试本身不需要修复变量。
   * 
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    // no-op
  }

  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        assertion(false, "callVisitors should not be called for this object!!!");
  }

}
