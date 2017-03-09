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
 * $Id: PrefixResolver.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * <p>
 *  $ Id：PrefixResolver.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * The class that implements this interface can resolve prefixes to
 * namespaces. Examples would include resolving the meaning of a
 * prefix at a particular point in a document, or mapping the prefixes
 * used in an XPath expression.
 * @xsl.usage advanced
 * <p>
 *  实现此接口的类可以解析名称空间的前缀。示例将包括解析文档中特定点处的前缀的含义,或映射XPath表达式中使用的前缀。 @ xsl.usage advanced
 * 
 */
public interface PrefixResolver
{

  /**
   * Given a namespace, get the corrisponding prefix.  This assumes that
   * the PrefixResolver holds its own namespace context, or is a namespace
   * context itself.
   *
   * <p>
   *  给定一个命名空间,得到相应的前缀。这假设PrefixResolver保存自己的命名空间上下文,或者是一个命名空间上下文本身。
   * 
   * 
   * @param prefix The prefix to look up, which may be an empty string ("") for the default Namespace.
   *
   * @return The associated Namespace URI, or null if the prefix
   *         is undeclared in this context.
   */
  String getNamespaceForPrefix(String prefix);

  /**
   * Given a namespace, get the corresponding prefix, based on the context node.
   *
   * <p>
   *  给定一个命名空间,根据上下文节点获取相应的前缀。
   * 
   * 
   * @param prefix The prefix to look up, which may be an empty string ("") for the default Namespace.
   * @param context The node context from which to look up the URI.
   *
   * @return The associated Namespace URI as a string, or null if the prefix
   *         is undeclared in this context.
   */
  String getNamespaceForPrefix(String prefix, org.w3c.dom.Node context);

  /**
   * Return the base identifier.
   *
   * <p>
   *  返回基本标识符。
   * 
   * @return The base identifier from where relative URIs should be absolutized, or null
   * if the base ID is unknown.
   * <p>
   * CAVEAT: Note that the base URI in an XML document may vary with where
   * you are in the document, if part of the doc's contents were brought in
   * via an external entity reference or if mechanisms such as xml:base have
   * been used. Unless this PrefixResolver is bound to a specific portion of
   * the document, or has been kept up to date via some other mechanism, it
   * may not accurately reflect that context information.
   */
  public String getBaseIdentifier();

  public boolean handlesNullPrefixes();
}
