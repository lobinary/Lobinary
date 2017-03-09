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
 * $Id: ExpressionContext.java,v 1.2.4.1 2005/09/10 19:34:03 jeffsuttor Exp $
 * <p>
 *  $ Id：ExpressionContext.java,v 1.2.4.1 2005/09/10 19:34:03 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.extensions;

import javax.xml.transform.ErrorListener;

import com.sun.org.apache.xpath.internal.objects.XObject;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * An object that implements this interface can supply
 * information about the current XPath expression context.
 * <p>
 *  实现此接口的对象可以提供有关当前XPath表达式上下文的信息。
 * 
 */
public interface ExpressionContext
{

  /**
   * Get the current context node.
   * <p>
   *  获取当前上下文节点。
   * 
   * 
   * @return The current context node.
   */
  public Node getContextNode();

  /**
   * Get the current context node list.
   * <p>
   *  获取当前上下文节点列表。
   * 
   * 
   * @return An iterator for the current context list, as
   * defined in XSLT.
   */
  public NodeIterator getContextNodes();

  /**
   * Get the error listener.
   * <p>
   *  获取错误侦听器。
   * 
   * 
   * @return The registered error listener.
   */
  public ErrorListener getErrorListener();

  /**
   * Get the value of a node as a number.
   * <p>
   *  获取节点的值作为数字。
   * 
   * 
   * @param n Node to be converted to a number.  May be null.
   * @return value of n as a number.
   */
  public double toNumber(Node n);

  /**
   * Get the value of a node as a string.
   * <p>
   *  以字符串形式获取节点的值。
   * 
   * 
   * @param n Node to be converted to a string.  May be null.
   * @return value of n as a string, or an empty string if n is null.
   */
  public String toString(Node n);

  /**
   * Get a variable based on it's qualified name.
   *
   * <p>
   *  基于其限定名称获取变量。
   * 
   * 
   * @param qname The qualified name of the variable.
   *
   * @return The evaluated value of the variable.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject getVariableOrParam(com.sun.org.apache.xml.internal.utils.QName qname)
            throws javax.xml.transform.TransformerException;

  /**
   * Get the XPathContext that owns this ExpressionContext.
   *
   * Note: exslt:function requires the XPathContext to access
   * the variable stack and TransformerImpl.
   *
   * <p>
   *  获取拥有此ExpressionContext的XPathContext。
   * 
   * 
   * @return The current XPathContext.
   * @throws javax.xml.transform.TransformerException
   */
  public com.sun.org.apache.xpath.internal.XPathContext getXPathContext()
            throws javax.xml.transform.TransformerException;

}
