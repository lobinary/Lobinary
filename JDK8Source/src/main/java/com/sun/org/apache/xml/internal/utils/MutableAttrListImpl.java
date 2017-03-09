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
 * $Id: MutableAttrListImpl.java,v 1.2.4.1 2005/09/15 08:15:47 suresh_emailid Exp $
 * <p>
 *  $ Id：MutableAttrListImpl.java,v 1.2.4.1 2005/09/15 08:15:47 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.Serializable;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Mutable version of AttributesImpl.
 * @xsl.usage advanced
 * <p>
 *  AttributesImpl的可变版本。 @ xsl.usage advanced
 * 
 */
public class MutableAttrListImpl extends AttributesImpl
        implements Serializable
{
    static final long serialVersionUID = 6289452013442934470L;

/**
 * Construct a new, empty AttributesImpl object.
 * <p>
 *  构造一个新的,空的AttributesImpl对象。
 * 
 */

public MutableAttrListImpl()
  {
    super();
  }

  /**
   * Copy an existing Attributes object.
   *
   * <p>This constructor is especially useful inside a start
   * element event.</p>
   *
   * <p>
   *  复制现有的Attributes对象。
   * 
   *  <p>此构造函数在start元素事件内特别有用。</p>
   * 
   * 
   * @param atts The existing Attributes object.
   */
  public MutableAttrListImpl(Attributes atts)
  {
    super(atts);
  }

  /**
   * Add an attribute to the end of the list.
   *
   * <p>For the sake of speed, this method does no checking
   * to see if the attribute is already in the list: that is
   * the responsibility of the application.</p>
   *
   * <p>
   *  将属性添加到列表的末尾。
   * 
   *  <p>为了速度,此方法不检查属性是否已在列表中：这是应用程序的职责。</p>
   * 
   * 
   * @param uri The Namespace URI, or the empty string if
   *        none is available or Namespace processing is not
   *        being performed.
   * @param localName The local name, or the empty string if
   *        Namespace processing is not being performed.
   * @param qName The qualified (prefixed) name, or the empty string
   *        if qualified names are not available.
   * @param type The attribute type as a string.
   * @param value The attribute value.
   */
  public void addAttribute(String uri, String localName, String qName,
                           String type, String value)
  {

    if (null == uri)
      uri = "";

    // getIndex(qName) seems to be more reliable than getIndex(uri, localName),
    // in the case of the xmlns attribute anyway.
    int index = this.getIndex(qName);
    // int index = this.getIndex(uri, localName);

    // System.out.println("MutableAttrListImpl#addAttribute: "+uri+":"+localName+", "+index+", "+qName+", "+this);

    if (index >= 0)
      this.setAttribute(index, uri, localName, qName, type, value);
    else
      super.addAttribute(uri, localName, qName, type, value);
  }

  /**
   * Add the contents of the attribute list to this list.
   *
   * <p>
   *  将属性列表的内容添加到此列表。
   * 
   * 
   * @param atts List of attributes to add to this list
   */
  public void addAttributes(Attributes atts)
  {

    int nAtts = atts.getLength();

    for (int i = 0; i < nAtts; i++)
    {
      String uri = atts.getURI(i);

      if (null == uri)
        uri = "";

      String localName = atts.getLocalName(i);
      String qname = atts.getQName(i);
      int index = this.getIndex(uri, localName);
      // System.out.println("MutableAttrListImpl#addAttributes: "+uri+":"+localName+", "+index+", "+atts.getQName(i)+", "+this);
      if (index >= 0)
        this.setAttribute(index, uri, localName, qname, atts.getType(i),
                          atts.getValue(i));
      else
        addAttribute(uri, localName, qname, atts.getType(i),
                     atts.getValue(i));
    }
  }

  /**
   * Return true if list contains the given (raw) attribute name.
   *
   * <p>
   *  如果列表包含给定(原始)属性名称,则返回true。
   * 
   * @param name Raw name of attribute to look for
   *
   * @return true if an attribute is found with this name
   */
  public boolean contains(String name)
  {
    return getValue(name) != null;
  }
}

// end of MutableAttrListImpl.java
