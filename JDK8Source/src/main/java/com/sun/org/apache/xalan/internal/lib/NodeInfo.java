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
 * $Id: NodeInfo.java,v 1.2.4.1 2005/09/10 18:54:37 jeffsuttor Exp $
 * <p>
 *  $ Id：NodeInfo.java,v 1.2.4.1 2005/09/10 18:54:37 jeffsuttor Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.lib;

import javax.xml.transform.SourceLocator;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>NodeInfo</code> defines a set of XSLT extension functions to be
 * used from stylesheets.
 *
 * <p>
 *  <code> NodeInfo </code>定义了一组从样式表中使用的XSLT扩展函数。
 * 
 * 
 * @author <a href="mailto:ovidiu@cup.hp.com">Ovidiu Predescu</a>
 * @since May 24, 2001
 */
public class NodeInfo
{
  /**
   * <code>systemId</code> returns the system id of the current
   * context node.
   *
   * <p>
   *  <code> systemId </code>返回当前上下文节点的系统ID。
   * 
   * 
   * @param context an <code>ExpressionContext</code> value
   * @return a <code>String</code> value
   */
  public static String systemId(ExpressionContext context)
  {
    Node contextNode = context.getContextNode();
    int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getSystemId();
    else
      return null;
  }

  /**
   * <code>systemId</code> returns the system id of the node passed as
   * argument. If a node set is passed as argument, the system id of
   * the first node in the set is returned.
   *
   * <p>
   *  <code> systemId </code>返回作为参数传递的节点的系统ID。如果将节点集作为参数传递,则返回集合中第一个节点的系统ID。
   * 
   * 
   * @param nodeList a <code>NodeList</code> value
   * @return a <code>String</code> value
   */
  public static String systemId(NodeList nodeList)
  {
    if (nodeList == null || nodeList.getLength() == 0)
      return null;

    Node node = nodeList.item(0);
    int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)node).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getSystemId();
    else
      return null;
  }

  /**
   * <code>publicId</code> returns the public identifier of the current
   * context node.
   *
   * Xalan does not currently record this value, and will return null.
   *
   * <p>
   *  <code> publicId </code>返回当前上下文节点的公共标识符。
   * 
   *  Xalan当前不记录此值,并将返回null。
   * 
   * 
   * @param context an <code>ExpressionContext</code> value
   * @return a <code>String</code> value
   */
  public static String publicId(ExpressionContext context)
  {
    Node contextNode = context.getContextNode();
    int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getPublicId();
    else
      return null;
  }

  /**
   * <code>publicId</code> returns the public identifier of the node passed as
   * argument. If a node set is passed as argument, the public identifier of
   * the first node in the set is returned.
   *
   * Xalan does not currently record this value, and will return null.
   *
   * <p>
   *  <code> publicId </code>返回作为参数传递的节点的公共标识符。如果将节点集作为参数传递,则返回集合中第一个节点的公共标识符。
   * 
   * Xalan当前不记录此值,并将返回null。
   * 
   * 
   * @param nodeList a <code>NodeList</code> value
   * @return a <code>String</code> value
   */
  public static String publicId(NodeList nodeList)
  {
    if (nodeList == null || nodeList.getLength() == 0)
      return null;

    Node node = nodeList.item(0);
    int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)node).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getPublicId();
    else
      return null;
  }

  /**
   * <code>lineNumber</code> returns the line number of the current
   * context node.
   *
   * NOTE: Xalan does not normally record location information for each node.
   * To obtain it, you must set the custom TrAX attribute
   * "http://xml.apache.org/xalan/features/source_location"
   * true in the TransformerFactory before generating the Transformer and executing
   * the stylesheet. Storage cost per node will be noticably increased in this mode.
   *
   * <p>
   *  <code> lineNumber </code>返回当前上下文节点的行号。
   * 
   *  注意：Xalan通常不会记录每个节点的位置信息。
   * 要获取它,您必须在TransformerFactory中设置自定义TrAX属性"http://xml.apache.org/xalan/features/source_location"true,然后再
   * 生成Transformer并执行样式表。
   *  注意：Xalan通常不会记录每个节点的位置信息。在这种模式下,每个节点的存储成本将明显增加。
   * 
   * 
   * @param context an <code>ExpressionContext</code> value
   * @return an <code>int</code> value. This may be -1 to indicate that the
   * line number is not known.
   */
  public static int lineNumber(ExpressionContext context)
  {
    Node contextNode = context.getContextNode();
    int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getLineNumber();
    else
      return -1;
  }

  /**
   * <code>lineNumber</code> returns the line number of the node
   * passed as argument. If a node set is passed as argument, the line
   * number of the first node in the set is returned.
   *
   * NOTE: Xalan does not normally record location information for each node.
   * To obtain it, you must set the custom TrAX attribute
   * "http://xml.apache.org/xalan/features/source_location"
   * true in the TransformerFactory before generating the Transformer and executing
   * the stylesheet. Storage cost per node will be noticably increased in this mode.
   *
   * <p>
   *  <code> lineNumber </code>返回作为参数传递的节点的行号。如果将节点集作为参数传递,则返回集合中第一个节点的行号。
   * 
   *  注意：Xalan通常不会记录每个节点的位置信息。
   * 要获取它,您必须在TransformerFactory中设置自定义TrAX属性"http://xml.apache.org/xalan/features/source_location"true,然后再
   * 生成Transformer并执行样式表。
   *  注意：Xalan通常不会记录每个节点的位置信息。在这种模式下,每个节点的存储成本将明显增加。
   * 
   * 
   * @param nodeList a <code>NodeList</code> value
   * @return an <code>int</code> value. This may be -1 to indicate that the
   * line number is not known.
   */
  public static int lineNumber(NodeList nodeList)
  {
    if (nodeList == null || nodeList.getLength() == 0)
      return -1;

    Node node = nodeList.item(0);
    int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)node).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getLineNumber();
    else
      return -1;
  }

  /**
   * <code>columnNumber</code> returns the column number of the
   * current context node.
   *
   * NOTE: Xalan does not normally record location information for each node.
   * To obtain it, you must set the custom TrAX attribute
   * "http://xml.apache.org/xalan/features/source_location"
   * true in the TransformerFactory before generating the Transformer and executing
   * the stylesheet. Storage cost per node will be noticably increased in this mode.
   *
   * <p>
   *  <code> columnNumber </code>返回当前上下文节点的列号。
   * 
   *  注意：Xalan通常不会记录每个节点的位置信息。
   * 要获取它,您必须在TransformerFactory中设置自定义TrAX属性"http://xml.apache.org/xalan/features/source_location"true,然后再
   * 生成Transformer并执行样式表。
   *  注意：Xalan通常不会记录每个节点的位置信息。在这种模式下,每个节点的存储成本将明显增加。
   * 
   * 
   * @param context an <code>ExpressionContext</code> value
   * @return an <code>int</code> value. This may be -1 to indicate that the
   * column number is not known.
   */
  public static int columnNumber(ExpressionContext context)
  {
    Node contextNode = context.getContextNode();
    int nodeHandler = ((DTMNodeProxy)contextNode).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)contextNode).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getColumnNumber();
    else
      return -1;
  }

  /**
   * <code>columnNumber</code> returns the column number of the node
   * passed as argument. If a node set is passed as argument, the line
   * number of the first node in the set is returned.
   *
   * NOTE: Xalan does not normally record location information for each node.
   * To obtain it, you must set the custom TrAX attribute
   * "http://xml.apache.org/xalan/features/source_location"
   * true in the TransformerFactory before generating the Transformer and executing
   * the stylesheet. Storage cost per node will be noticably increased in this mode.
   *
   * <p>
   * <code> columnNumber </code>返回作为参数传递的节点的列号。如果将节点集作为参数传递,则返回集合中第一个节点的行号。
   * 
   *  注意：Xalan通常不会记录每个节点的位置信息。
   * 要获取它,您必须在TransformerFactory中设置自定义TrAX属性"http://xml.apache.org/xalan/features/source_location"true,然后再
   * 
   * @param nodeList a <code>NodeList</code> value
   * @return an <code>int</code> value. This may be -1 to indicate that the
   * column number is not known.
   */
  public static int columnNumber(NodeList nodeList)
  {
    if (nodeList == null || nodeList.getLength() == 0)
      return -1;

    Node node = nodeList.item(0);
    int nodeHandler = ((DTMNodeProxy)node).getDTMNodeNumber();
    SourceLocator locator = ((DTMNodeProxy)node).getDTM()
      .getSourceLocatorFor(nodeHandler);

    if (locator != null)
      return locator.getColumnNumber();
    else
      return -1;
  }
}
