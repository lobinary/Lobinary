/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
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
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: DOM2Helper.java,v 1.1.4.1 2005/09/08 11:03:09 suresh_emailid Exp $
 * <p>
 *  $ Id：DOM2Helper.java,v 1.1.4.1 2005/09/08 11:03:09 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer.utils;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;

/**
 * This class provides a DOM level 2 "helper", which provides services currently
 * not provided be the DOM standard.
 *
 * This class is a copy of the one in com.sun.org.apache.xml.internal.utils.
 * It exists to cut the serializers dependancy on that package.
 *
 * The differences from the original class are:
 * it doesn't extend DOMHelper, not depricated,
 * dropped method isNodeAfter(Node node1, Node node2)
 * dropped method parse(InputSource)
 * dropped method supportSAX()
 * dropped method setDocument(doc)
 * dropped method checkNode(Node)
 * dropped method getDocument()
 * dropped method getElementByID(String id, Document doc)
 * dropped method getParentOfNode(Node node)
 * dropped field Document m_doc;
 * made class non-public
 *
 * This class is not a public API, it is only public because it is
 * used in com.sun.org.apache.xml.internal.serializer.
 *
 * @xsl.usage internal
 * <p>
 *  这个类提供了一个DOM级别2"助手",它提供了当前未提供的服务是DOM标准。
 * 
 *  这个类是com.sun.org.apache.xml.internal.utils中的一个副本。它存在于减少序列化程序对该包的依赖。
 * 
 *  与原始类的区别是：它不扩展DOMHelper,不是depricated,删除方法isNodeAfter(节点node1,节点node2)删除方法parse(InputSource)删除方法suppor
 * tSAX()删除方法setDocument(doc)删除方法checkNode )drop方法getDocument()删除方法getElementByID(String id,Document doc
 * )删除方法getParentOfNode(Node node)丢弃字段Document m_doc;非公开类。
 * 
 * 此类不是公共API,它只是公共的,因为它在com.sun.org.apache.xml.internal.serializer中使用。
 * 
 *  @ xsl.usage internal
 * 
 */
public final class DOM2Helper
{

  /**
   * Construct an instance.
   * <p>
   *  构造一个实例。
   * 
   */
  public DOM2Helper(){}

  /**
   * Returns the local name of the given node, as defined by the
   * XML Namespaces specification. This is prepared to handle documents
   * built using DOM Level 1 methods by falling back upon explicitly
   * parsing the node name.
   *
   * <p>
   *  返回给定节点的本地名称,由XML命名空间规范定义。这是准备处理使用DOM 1级方法构建的文档,通过回退显式解析节点名称。
   * 
   * 
   * @param n Node to be examined
   *
   * @return String containing the local name, or null if the node
   * was not assigned a Namespace.
   */
  public String getLocalNameOfNode(Node n)
  {

    String name = n.getLocalName();

    return (null == name) ? getLocalNameOfNodeFallback(n) : name;
  }

  /**
   * Returns the local name of the given node. If the node's name begins
   * with a namespace prefix, this is the part after the colon; otherwise
   * it's the full node name.
   *
   * This method is copied from com.sun.org.apache.xml.internal.utils.DOMHelper
   *
   * <p>
   *  返回给定节点的本地名称。如果节点的名称以命名空间前缀开头,则这是冒号之后的部分;否则它是完整的节点名称。
   * 
   *  此方法从com.sun.org.apache.xml.internal.utils.DOMHelper复制
   * 
   * 
   * @param n the node to be examined.
   *
   * @return String containing the Local Name
   */
  private String getLocalNameOfNodeFallback(Node n)
  {

    String qname = n.getNodeName();
    int index = qname.indexOf(':');

    return (index < 0) ? qname : qname.substring(index + 1);
  }

  /**
   * Returns the Namespace Name (Namespace URI) for the given node.
   * In a Level 2 DOM, you can ask the node itself. Note, however, that
   * doing so conflicts with our decision in getLocalNameOfNode not
   * to trust the that the DOM was indeed created using the Level 2
   * methods. If Level 1 methods were used, these two functions will
   * disagree with each other.
   * <p>
   * TODO: Reconcile with getLocalNameOfNode.
   *
   * <p>
   *  返回给定节点的命名空间名称(命名空间URI)。在Level 2 DOM中,您可以询问节点本身。
   * 但请注意,这样做与我们在getLocalNameOfNode中的决定不相信DOM确实是使用Level 2方法创建的。如果使用1级方法,这两个功能将彼此不同意。
   * <p>
   *  TODO：与getLocalNameOfNode进行协调。
   * 
   * @param n Node to be examined
   *
   * @return String containing the Namespace URI bound to this DOM node
   * at the time the Node was created.
   */
  public String getNamespaceOfNode(Node n)
  {
    return n.getNamespaceURI();
  }

  /** Field m_useDOM2getNamespaceURI is a compile-time flag which
   *  gates some of the parser options used to build a DOM -- but
   * that code is commented out at this time and nobody else
   * <p>
   * 
   * 
   * references it, so I've commented this out as well. */
  //private boolean m_useDOM2getNamespaceURI = false;
}
