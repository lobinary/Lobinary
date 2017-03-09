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
 * $Id: XBoolean.java,v 1.2.4.2 2005/09/14 20:34:45 jeffsuttor Exp $
 * <p>
 *  $ Id：XBoolean.java,v 1.2.4.2 2005/09/14 20:34:45 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

/**
 * This class represents an XPath boolean object, and is capable of
 * converting the boolean to other types, such as a string.
 * @xsl.usage advanced
 * <p>
 *  此类表示一个XPath布尔对象,并且能够将布尔值转换为其他类型,例如字符串。 @ xsl.usage advanced
 * 
 */
public class XBoolean extends XObject
{
    static final long serialVersionUID = -2964933058866100881L;

  /**
   * A true boolean object so we don't have to keep creating them.
   * @xsl.usage internal
   * <p>
   *  一个真正的布尔对象,所以我们不必继续创建它们。 @ xsl.usage internal
   * 
   */
  public static final XBoolean S_TRUE = new XBooleanStatic(true);

  /**
   * A true boolean object so we don't have to keep creating them.
   * @xsl.usage internal
   * <p>
   *  一个真正的布尔对象,所以我们不必继续创建它们。 @ xsl.usage internal
   * 
   */
  public static final XBoolean S_FALSE = new XBooleanStatic(false);

  /** Value of the object.
  /* <p>
  /* 
   *  @serial         */
  private final boolean m_val;

  /**
   * Construct a XBoolean object.
   *
   * <p>
   *  构造一个XBoolean对象。
   * 
   * 
   * @param b Value of the boolean object
   */
  public XBoolean(boolean b)
  {

    super();

    m_val = b;
  }

  /**
   * Construct a XBoolean object.
   *
   * <p>
   *  构造一个XBoolean对象。
   * 
   * 
   * @param b Value of the boolean object
   */
  public XBoolean(Boolean b)
  {

    super();

    m_val = b.booleanValue();
    setObject(b);
  }


  /**
   * Tell that this is a CLASS_BOOLEAN.
   *
   * <p>
   *  告诉这是一个CLASS_BOOLEAN。
   * 
   * 
   * @return type of CLASS_BOOLEAN
   */
  public int getType()
  {
    return CLASS_BOOLEAN;
  }

  /**
   * Given a request type, return the equivalent string.
   * For diagnostic purposes.
   *
   * <p>
   *  给定一个请求类型,返回等效的字符串。用于诊断目的。
   * 
   * 
   * @return type string "#BOOLEAN"
   */
  public String getTypeString()
  {
    return "#BOOLEAN";
  }

  /**
   * Cast result object to a number.
   *
   * <p>
   *  将结果对象转换为数字。
   * 
   * 
   * @return numeric value of the object value
   */
  public double num()
  {
    return m_val ? 1.0 : 0.0;
  }

  /**
   * Cast result object to a boolean.
   *
   * <p>
   *  将结果对象转换为布尔值。
   * 
   * 
   * @return The object value as a boolean
   */
  public boolean bool()
  {
    return m_val;
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return The object's value as a string
   */
  public String str()
  {
    return m_val ? "true" : "false";
  }

  /**
   * Return a java object that's closest to the representation
   * that should be handed to an extension.
   *
   * <p>
   *  返回最接近应该传递给扩展的表示的java对象。
   * 
   * 
   * @return The object's value as a java object
   */
  public Object object()
  {
    if(null == m_obj)
      setObject(new Boolean(m_val));
    return m_obj;
  }

  /**
   * Tell if two objects are functionally equal.
   *
   * <p>
   * 告诉两个对象在功能上是否相等。
   * 
   * @param obj2 Object to compare to this
   *
   * @return True if the two objects are equal
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean equals(XObject obj2)
  {

    // In order to handle the 'all' semantics of
    // nodeset comparisons, we always call the
    // nodeset function.
    if (obj2.getType() == XObject.CLASS_NODESET)
      return obj2.equals(this);

    try
    {
      return m_val == obj2.bool();
    }
    catch(javax.xml.transform.TransformerException te)
    {
      throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(te);
    }
  }

}
