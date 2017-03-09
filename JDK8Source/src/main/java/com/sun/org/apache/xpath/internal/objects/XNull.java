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
 * $Id: XNull.java,v 1.2.4.1 2005/09/14 20:34:46 jeffsuttor Exp $
 * <p>
 *  $ Id：XNull.java,v 1.2.4.1 2005/09/14 20:34:46 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xpath.internal.XPathContext;

/**
 * This class represents an XPath null object, and is capable of
 * converting the null to other types, such as a string.
 * @xsl.usage general
 * <p>
 *  此类表示XPath空对象,并且能够将null转换为其他类型,例如字符串。 @ xsl.usage general
 * 
 */
public class XNull extends XNodeSet
{
    static final long serialVersionUID = -6841683711458983005L;

  /**
   * Create an XObject.
   * <p>
   *  创建XObject。
   * 
   */
  public XNull()
  {
    super();
  }

  /**
   * Tell what kind of class this is.
   *
   * <p>
   *  告诉这是什么类的类。
   * 
   * 
   * @return type CLASS_NULL
   */
  public int getType()
  {
    return CLASS_NULL;
  }

  /**
   * Given a request type, return the equivalent string.
   * For diagnostic purposes.
   *
   * <p>
   *  给定一个请求类型,返回等效的字符串。用于诊断目的。
   * 
   * 
   * @return type string "#CLASS_NULL"
   */
  public String getTypeString()
  {
    return "#CLASS_NULL";
  }

  /**
   * Cast result object to a number.
   *
   * <p>
   *  将结果对象转换为数字。
   * 
   * 
   * @return 0.0
   */

  public double num()
  {
    return 0.0;
  }

  /**
   * Cast result object to a boolean.
   *
   * <p>
   *  将结果对象转换为布尔值。
   * 
   * 
   * @return false
   */
  public boolean bool()
  {
    return false;
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return empty string ""
   */
  public String str()
  {
    return "";
  }

  /**
   * Cast result object to a result tree fragment.
   *
   * <p>
   *  将结果对象强制转换到结果树片段。
   * 
   * 
   * @param support XPath context to use for the conversion
   *
   * @return The object as a result tree fragment.
   */
  public int rtf(XPathContext support)
  {
    // DTM frag = support.createDocumentFragment();
    // %REVIEW%
    return DTM.NULL;
  }

//  /**
//   * Cast result object to a nodelist.
//   *
//   * <p>
//   *  // *将结果对象转换为一个节点列表。 // *
//   * 
//   * 
//   * @return null
//   */
//  public DTMIterator iter()
//  {
//    return null;
//  }

  /**
   * Tell if two objects are functionally equal.
   *
   * <p>
   *  告诉两个对象在功能上是否相等。
   * 
   * @param obj2 Object to compare this to
   *
   * @return True if the given object is of type CLASS_NULL
   */
  public boolean equals(XObject obj2)
  {
    return obj2.getType() == CLASS_NULL;
  }
}
