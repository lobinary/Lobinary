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
 * $Id: AttList.java,v 1.1.4.1 2005/09/08 11:03:08 suresh_emailid Exp $
 * <p>
 *  $ Id：AttList.java,v 1.1.4.1 2005/09/08 11:03:08 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer.utils;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import org.xml.sax.Attributes;

/**
 * Wraps a DOM attribute list in a SAX Attributes.
 *
 * This class is a copy of the one in com.sun.org.apache.xml.internal.utils.
 * It exists to cut the serializers dependancy on that package.
 * A minor changes from that package are:
 * DOMHelper reference changed to DOM2Helper, class is not "public"
 *
 * This class is not a public API, it is only public because it is
 * used in com.sun.org.apache.xml.internal.serializer.
 *
 * @xsl.usage internal
 * <p>
 *  将DOM属性列表包装在SAX属性中。
 * 
 *  这个类是com.sun.org.apache.xml.internal.utils中的一个副本。它存在于减少序列化程序对该包的依赖。
 * 对该包的一些小改动是：DOMHelper引用更改为DOM2Helper,类不是"public"。
 * 
 *  此类不是公共API,它只是公共的,因为它在com.sun.org.apache.xml.internal.serializer中使用。
 * 
 *  @ xsl.usage internal
 * 
 */
public final class AttList implements Attributes
{

  /** List of attribute nodes          */
  NamedNodeMap m_attrs;

  /** Index of last attribute node          */
  int m_lastIndex;

  // ARGHH!!  JAXP Uses Xerces without setting the namespace processing to ON!
  // DOM2Helper m_dh = new DOM2Helper();

  /** Local reference to DOMHelper          */
  DOM2Helper m_dh;

//  /**
//   * Constructor AttList
//   *
//   *
//   * <p>
//   *  // * Constructor AttList // * // *
//   * 
//   * 
//   * @param attrs List of attributes this will contain
//   */
//  public AttList(NamedNodeMap attrs)
//  {
//
//    m_attrs = attrs;
//    m_lastIndex = m_attrs.getLength() - 1;
//    m_dh = new DOM2Helper();
//  }

  /**
   * Constructor AttList
   *
   *
   * <p>
   *  构造函数AttList
   * 
   * 
   * @param attrs List of attributes this will contain
   * @param dh DOMHelper
   */
  public AttList(NamedNodeMap attrs, DOM2Helper dh)
  {

    m_attrs = attrs;
    m_lastIndex = m_attrs.getLength() - 1;
    m_dh = dh;
  }

  /**
   * Get the number of attribute nodes in the list
   *
   *
   * <p>
   *  获取列表中的属性节点数
   * 
   * 
   * @return number of attribute nodes
   */
  public int getLength()
  {
    return m_attrs.getLength();
  }

  /**
   * Look up an attribute's Namespace URI by index.
   *
   * <p>
   *  按索引查找属性的命名空间URI。
   * 
   * 
   * @param index The attribute index (zero-based).
   * @return The Namespace URI, or the empty string if none
   *         is available, or null if the index is out of
   *         range.
   */
  public String getURI(int index)
  {
    String ns = m_dh.getNamespaceOfNode(((Attr) m_attrs.item(index)));
    if(null == ns)
      ns = "";
    return ns;
  }

  /**
   * Look up an attribute's local name by index.
   *
   * <p>
   *  按索引查找属性的本地名称。
   * 
   * 
   * @param index The attribute index (zero-based).
   * @return The local name, or the empty string if Namespace
   *         processing is not being performed, or null
   *         if the index is out of range.
   */
  public String getLocalName(int index)
  {
    return m_dh.getLocalNameOfNode(((Attr) m_attrs.item(index)));
  }

  /**
   * Look up an attribute's qualified name by index.
   *
   *
   * <p>
   *  通过索引查找属性的限定名称。
   * 
   * 
   * @param i The attribute index (zero-based).
   *
   * @return The attribute's qualified name
   */
  public String getQName(int i)
  {
    return ((Attr) m_attrs.item(i)).getName();
  }

  /**
   * Get the attribute's node type by index
   *
   *
   * <p>
   * 通过索引获取属性的节点类型
   * 
   * 
   * @param i The attribute index (zero-based)
   *
   * @return the attribute's node type
   */
  public String getType(int i)
  {
    return "CDATA";  // for the moment
  }

  /**
   * Get the attribute's node value by index
   *
   *
   * <p>
   *  通过索引获取属性的节点值
   * 
   * 
   * @param i The attribute index (zero-based)
   *
   * @return the attribute's node value
   */
  public String getValue(int i)
  {
    return ((Attr) m_attrs.item(i)).getValue();
  }

  /**
   * Get the attribute's node type by name
   *
   *
   * <p>
   *  按名称获取属性的节点类型
   * 
   * 
   * @param name Attribute name
   *
   * @return the attribute's node type
   */
  public String getType(String name)
  {
    return "CDATA";  // for the moment
  }

  /**
   * Look up an attribute's type by Namespace name.
   *
   * <p>
   *  按命名空间名称查找属性的类型。
   * 
   * 
   * @param uri The Namespace URI, or the empty String if the
   *        name has no Namespace URI.
   * @param localName The local name of the attribute.
   * @return The attribute type as a string, or null if the
   *         attribute is not in the list or if Namespace
   *         processing is not being performed.
   */
  public String getType(String uri, String localName)
  {
    return "CDATA";  // for the moment
  }

  /**
   * Look up an attribute's value by name.
   *
   *
   * <p>
   *  按名称查找属性的值。
   * 
   * 
   * @param name The attribute node's name
   *
   * @return The attribute node's value
   */
  public String getValue(String name)
  {
    Attr attr = ((Attr) m_attrs.getNamedItem(name));
    return (null != attr)
          ? attr.getValue() : null;
  }

  /**
   * Look up an attribute's value by Namespace name.
   *
   * <p>
   *  按命名空间名称查找属性的值。
   * 
   * 
   * @param uri The Namespace URI, or the empty String if the
   *        name has no Namespace URI.
   * @param localName The local name of the attribute.
   * @return The attribute value as a string, or null if the
   *         attribute is not in the list.
   */
  public String getValue(String uri, String localName)
  {
        Node a=m_attrs.getNamedItemNS(uri,localName);
        return (a==null) ? null : a.getNodeValue();
  }

  /**
   * Look up the index of an attribute by Namespace name.
   *
   * <p>
   *  按名称空间名称查找属性的索引。
   * 
   * 
   * @param uri The Namespace URI, or the empty string if
   *        the name has no Namespace URI.
   * @param localPart The attribute's local name.
   * @return The index of the attribute, or -1 if it does not
   *         appear in the list.
   */
  public int getIndex(String uri, String localPart)
  {
    for(int i=m_attrs.getLength()-1;i>=0;--i)
    {
      Node a=m_attrs.item(i);
      String u=a.getNamespaceURI();
      if( (u==null ? uri==null : u.equals(uri))
      &&
      a.getLocalName().equals(localPart) )
    return i;
    }
    return -1;
  }

  /**
   * Look up the index of an attribute by raw XML 1.0 name.
   *
   * <p>
   *  按原始XML 1.0名称查找属性的索引。
   * 
   * @param qName The qualified (prefixed) name.
   * @return The index of the attribute, or -1 if it does not
   *         appear in the list.
   */
  public int getIndex(String qName)
  {
    for(int i=m_attrs.getLength()-1;i>=0;--i)
    {
      Node a=m_attrs.item(i);
      if(a.getNodeName().equals(qName) )
    return i;
    }
    return -1;
  }
}
