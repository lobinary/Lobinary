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
 * $Id: PrefixResolverDefault.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * <p>
 *  $ Id：PrefixResolverDefault.java,v 1.2.4.1 2005/09/15 08:15:51 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * This class implements a generic PrefixResolver that
 * can be used to perform prefix-to-namespace lookup
 * for the XPath object.
 * @xsl.usage general
 * <p>
 *  此类实现一个通用的PrefixResolver,可用于对XPath对象执行前缀到命名空间的查找。 @ xsl.usage general
 * 
 */
public class PrefixResolverDefault implements PrefixResolver
{

  /**
   * The context to resolve the prefix from, if the context
   * is not given.
   * <p>
   *  解析前缀的上下文,如果上下文没有给出。
   * 
   */
  Node m_context;

  /**
   * Construct a PrefixResolverDefault object.
   * <p>
   *  构造PrefixResolverDefault对象。
   * 
   * 
   * @param xpathExpressionContext The context from
   * which XPath expression prefixes will be resolved.
   * Warning: This will not work correctly if xpathExpressionContext
   * is an attribute node.
   */
  public PrefixResolverDefault(Node xpathExpressionContext)
  {
    m_context = xpathExpressionContext;
  }

  /**
   * Given a namespace, get the corrisponding prefix.  This assumes that
   * the PrevixResolver hold's it's own namespace context, or is a namespace
   * context itself.
   * <p>
   *  给定一个命名空间,得到相应的前缀。这假设PrevixResolver保持它自己的命名空间上下文,或者是一个命名空间上下文本身。
   * 
   * 
   * @param prefix Prefix to resolve.
   * @return Namespace that prefix resolves to, or null if prefix
   * is not bound.
   */
  public String getNamespaceForPrefix(String prefix)
  {
    return getNamespaceForPrefix(prefix, m_context);
  }

  /**
   * Given a namespace, get the corrisponding prefix.
   * Warning: This will not work correctly if namespaceContext
   * is an attribute node.
   * <p>
   *  给定一个命名空间,得到相应的前缀。警告：如果namespaceContext是属性节点,这将无法正常工作。
   * 
   * 
   * @param prefix Prefix to resolve.
   * @param namespaceContext Node from which to start searching for a
   * xmlns attribute that binds a prefix to a namespace.
   * @return Namespace that prefix resolves to, or null if prefix
   * is not bound.
   */
  public String getNamespaceForPrefix(String prefix,
                                      org.w3c.dom.Node namespaceContext)
  {

    Node parent = namespaceContext;
    String namespace = null;

    if (prefix.equals("xml"))
    {
      namespace = Constants.S_XMLNAMESPACEURI;
    }
    else
    {
      int type;

      while ((null != parent) && (null == namespace)
             && (((type = parent.getNodeType()) == Node.ELEMENT_NODE)
                 || (type == Node.ENTITY_REFERENCE_NODE)))
      {
        if (type == Node.ELEMENT_NODE)
        {
                if (parent.getNodeName().indexOf(prefix+":") == 0)
                        return parent.getNamespaceURI();
          NamedNodeMap nnm = parent.getAttributes();

          for (int i = 0; i < nnm.getLength(); i++)
          {
            Node attr = nnm.item(i);
            String aname = attr.getNodeName();
            boolean isPrefix = aname.startsWith("xmlns:");

            if (isPrefix || aname.equals("xmlns"))
            {
              int index = aname.indexOf(':');
              String p = isPrefix ? aname.substring(index + 1) : "";

              if (p.equals(prefix))
              {
                namespace = attr.getNodeValue();

                break;
              }
            }
          }
        }

        parent = parent.getParentNode();
      }
    }

    return namespace;
  }

  /**
   * Return the base identifier.
   *
   * <p>
   *  返回基本标识符。
   * 
   * 
   * @return null
   */
  public String getBaseIdentifier()
  {
    return null;
  }
        /**
        /* <p>
        /* 
         * @see PrefixResolver#handlesNullPrefixes()
         */
        public boolean handlesNullPrefixes() {
                return false;
        }

}
