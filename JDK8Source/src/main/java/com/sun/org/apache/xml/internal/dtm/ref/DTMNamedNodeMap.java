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
 * $Id: DTMNamedNodeMap.java,v 1.2.4.1 2005/09/15 08:15:03 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMNamedNodeMap.java,v 1.2.4.1 2005/09/15 08:15:03 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.DTM;

import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * DTMNamedNodeMap is a quickie (as opposed to quick) implementation of the DOM's
 * NamedNodeMap interface, intended to support DTMProxy's getAttributes()
 * call.
 * <p>
 * ***** Note: this does _not_ current attempt to cache any of the data;
 * if you ask for attribute 27 and then 28, you'll have to rescan the first
 * 27. It should probably at least keep track of the last one retrieved,
 * and possibly buffer the whole array.
 * <p>
 * ***** Also note that there's no fastpath for the by-name query; we search
 * linearly until we find it or fail to find it. Again, that could be
 * optimized at some cost in object creation/storage.
 * @xsl.usage internal
 * <p>
 *  DTMNamedNodeMap是DOM的NamedNodeMap接口的快速(而不是快速)实现,旨在支持DTMProxy的getAttributes()调用。
 * <p>
 *  *****注意：这样做_not_当前尝试缓存任何数据;如果你要求属性27,然后28,你将不得不重新扫描第一个27.它应该至少保持跟踪的最后一个检索,并可能缓冲整个数组。
 * <p>
 *  *****还要注意,没有快速路径的名字查询;我们线性搜索,直到我们找到它或找不到它。再次,这可以在对象创建/存储中以一些成本被优化。 @ xsl.usage internal
 * 
 */
public class DTMNamedNodeMap implements NamedNodeMap
{

  /** The DTM for this node. */
  DTM dtm;

  /** The DTM element handle. */
  int element;

  /** The number of nodes in this map. */
  short m_count = -1;

  /**
   * Create a getAttributes NamedNodeMap for a given DTM element node
   *
   * <p>
   *  为给定的DTM元素节点创建getAttributes NamedNodeMap
   * 
   * 
   * @param dtm The DTM Reference, must be non-null.
   * @param element The DTM element handle.
   */
  public DTMNamedNodeMap(DTM dtm, int element)
  {
    this.dtm = dtm;
    this.element = element;
  }

  /**
   * Return the number of Attributes on this Element
   *
   * <p>
   * 返回此元素上的属性数
   * 
   * 
   * @return The number of nodes in this map.
   */
  public int getLength()
  {

    if (m_count == -1)
    {
      short count = 0;

      for (int n = dtm.getFirstAttribute(element); n != -1;
              n = dtm.getNextAttribute(n))
      {
        ++count;
      }

      m_count = count;
    }

    return (int) m_count;
  }

  /**
   * Retrieves a node specified by name.
   * <p>
   *  检索由名称指定的节点。
   * 
   * 
   * @param name The <code>nodeName</code> of a node to retrieve.
   * @return A <code>Node</code> (of any type) with the specified
   *   <code>nodeName</code>, or <code>null</code> if it does not identify
   *   any node in this map.
   */
  public Node getNamedItem(String name)
  {

    for (int n = dtm.getFirstAttribute(element); n != DTM.NULL;
            n = dtm.getNextAttribute(n))
    {
      if (dtm.getNodeName(n).equals(name))
        return dtm.getNode(n);
    }

    return null;
  }

  /**
   * Returns the <code>index</code>th item in the map. If <code>index</code>
   * is greater than or equal to the number of nodes in this map, this
   * returns <code>null</code>.
   * <p>
   *  返回地图中的<code> index </code> th项。如果<code> index </code>大于或等于此映射中的节点数,则返回<code> null </code>。
   * 
   * 
   * @param i The index of the requested item.
   * @return The node at the <code>index</code>th position in the map, or
   *   <code>null</code> if that is not a valid index.
   */
  public Node item(int i)
  {

    int count = 0;

    for (int n = dtm.getFirstAttribute(element); n != -1;
            n = dtm.getNextAttribute(n))
    {
      if (count == i)
        return dtm.getNode(n);
      else
        ++count;
    }

    return null;
  }

  /**
   * Adds a node using its <code>nodeName</code> attribute. If a node with
   * that name is already present in this map, it is replaced by the new
   * one.
   * <br>As the <code>nodeName</code> attribute is used to derive the name
   * which the node must be stored under, multiple nodes of certain types
   * (those that have a "special" string value) cannot be stored as the
   * names would clash. This is seen as preferable to allowing nodes to be
   * aliased.
   * <p>
   *  使用其<code> nodeName </code>属性添加节点。如果具有该名称的节点已经存在于此映射中,则它将被新映射替换。
   *  <br>由于<code> nodeName </code>属性用于派生节点必须存储的名称,因此不能将某些类型的多个节点(具有"特殊"字符串值的节点)存储为名称会发生冲突。
   * 这被认为是允许节点混叠的优选方案。
   * 
   * 
   * @param newNode node to store in this map. The node will later be
   *   accessible using the value of its <code>nodeName</code> attribute.
   *
   * @return If the new <code>Node</code> replaces an existing node the
   *   replaced <code>Node</code> is returned, otherwise <code>null</code>
   *   is returned.
   * @exception DOMException
   *   WRONG_DOCUMENT_ERR: Raised if <code>arg</code> was created from a
   *   different document than the one that created this map.
   *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
   *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>arg</code> is an
   *   <code>Attr</code> that is already an attribute of another
   *   <code>Element</code> object. The DOM user must explicitly clone
   *   <code>Attr</code> nodes to re-use them in other elements.
   */
  public Node setNamedItem(Node newNode)
  {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /**
   * Removes a node specified by name. When this map contains the attributes
   * attached to an element, if the removed attribute is known to have a
   * default value, an attribute immediately appears containing the
   * default value as well as the corresponding namespace URI, local name,
   * and prefix when applicable.
   * <p>
   *  删除名称指定的节点。当此映射包含附加到元素的属性时,如果已知已删除的属性具有默认值,则在适用时,将立即出现包含默认值以及对应的命名空间URI,本地名称和前缀的属性。
   * 
   * 
   * @param name The <code>nodeName</code> of the node to remove.
   *
   * @return The node removed from this map if a node with such a name
   *   exists.
   * @exception DOMException
   *   NOT_FOUND_ERR: Raised if there is no node named <code>name</code> in
   *   this map.
   *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
   */
  public Node removeNamedItem(String name)
  {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /**
   * Retrieves a node specified by local name and namespace URI. HTML-only
   * DOM implementations do not need to implement this method.
   * <p>
   *  检索由本地名称和命名空间URI指定的节点。仅HTML的DOM实现不需要实现这个方法。
   * 
   * 
   * @param namespaceURI The namespace URI of the node to retrieve.
   * @param localName The local name of the node to retrieve.
   *
   * @return A <code>Node</code> (of any type) with the specified local
   *   name and namespace URI, or <code>null</code> if they do not
   *   identify any node in this map.
   * @since DOM Level 2
   */
  public Node getNamedItemNS(String namespaceURI, String localName)
  {
       Node retNode = null;
       for (int n = dtm.getFirstAttribute(element); n != DTM.NULL;
                       n = dtm.getNextAttribute(n))
       {
         if (localName.equals(dtm.getLocalName(n)))
         {
           String nsURI = dtm.getNamespaceURI(n);
           if ((namespaceURI == null && nsURI == null)
                  || (namespaceURI != null && namespaceURI.equals(nsURI)))
           {
             retNode = dtm.getNode(n);
             break;
           }
         }
       }
       return retNode;
  }

  /**
   * Adds a node using its <code>namespaceURI</code> and
   * <code>localName</code>. If a node with that namespace URI and that
   * local name is already present in this map, it is replaced by the new
   * one.
   * <br>HTML-only DOM implementations do not need to implement this method.
   * <p>
   *  使用其<code> namespaceURI </code>和<code> localName </code>添加节点。
   * 如果具有该名称空间URI和该本地名称的节点已经存在于此映射中,则它将被新映射替换。 <br>仅HTML的DOM实现不需要实现此方法。
   * 
   * 
   * @param arg A node to store in this map. The node will later be
   *   accessible using the value of its <code>namespaceURI</code> and
   *   <code>localName</code> attributes.
   *
   * @return If the new <code>Node</code> replaces an existing node the
   *   replaced <code>Node</code> is returned, otherwise <code>null</code>
   *   is returned.
   * @exception DOMException
   *   WRONG_DOCUMENT_ERR: Raised if <code>arg</code> was created from a
   *   different document than the one that created this map.
   *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
   *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>arg</code> is an
   *   <code>Attr</code> that is already an attribute of another
   *   <code>Element</code> object. The DOM user must explicitly clone
   *   <code>Attr</code> nodes to re-use them in other elements.
   * @since DOM Level 2
   */
  public Node setNamedItemNS(Node arg) throws DOMException
  {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /**
   * Removes a node specified by local name and namespace URI. A removed
   * attribute may be known to have a default value when this map contains
   * the attributes attached to an element, as returned by the attributes
   * attribute of the <code>Node</code> interface. If so, an attribute
   * immediately appears containing the default value as well as the
   * corresponding namespace URI, local name, and prefix when applicable.
   * <br>HTML-only DOM implementations do not need to implement this method.
   *
   * <p>
   * 删除由本地名称和命名空间URI指定的节点。当此映射包含由<code> Node </code>接口的attributes属性返回的附加到元素的属性时,可以知道已移除的属性具有默认值。
   * 如果是这样,将立即出现一个属性,包含默认值以及相应的命名空间URI,本地名称和前缀(如果适用)。 <br>仅HTML的DOM实现不需要实现此方法。
   * 
   * 
   * @param namespaceURI The namespace URI of the node to remove.
   * @param localName The local name of the node to remove.
   *
   * @return The node removed from this map if a node with such a local
   *   name and namespace URI exists.
   * @exception DOMException
   *   NOT_FOUND_ERR: Raised if there is no node with the specified
   *   <code>namespaceURI</code> and <code>localName</code> in this map.
   *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this map is readonly.
   * @since DOM Level 2
   */
  public Node removeNamedItemNS(String namespaceURI, String localName)
          throws DOMException
  {
    throw new DTMException(DTMException.NO_MODIFICATION_ALLOWED_ERR);
  }

  /**
   * Simple implementation of DOMException.
   * @xsl.usage internal
   * <p>
   *  简单实现DOMException。 @ xsl.usage internal
   * 
   */
  public class DTMException extends org.w3c.dom.DOMException
  {
          static final long serialVersionUID = -8290238117162437678L;
    /**
     * Constructs a DOM/DTM exception.
     *
     * <p>
     *  构造DOM / DTM异常。
     * 
     * 
     * @param code
     * @param message
     */
    public DTMException(short code, String message)
    {
      super(code, message);
    }

    /**
     * Constructor DTMException
     *
     *
     * <p>
     *  构造函数DTMException
     * 
     * @param code
     */
    public DTMException(short code)
    {
      super(code, "");
    }
  }
}
