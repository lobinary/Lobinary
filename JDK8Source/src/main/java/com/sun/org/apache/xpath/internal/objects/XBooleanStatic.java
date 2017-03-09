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
 * $Id: XBooleanStatic.java,v 1.2.4.2 2005/09/14 20:34:46 jeffsuttor Exp $
 * <p>
 *  $ Id：XBooleanStatic.java,v 1.2.4.2 2005/09/14 20:34:46 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

/**
 * This class doesn't have any XPathContext, so override
 * whatever to ensure it works OK.
 * @xsl.usage internal
 * <p>
 *  这个类没有任何XPathContext,所以覆盖任何,以确保它工作正常。 @ xsl.usage internal
 * 
 */
public class XBooleanStatic extends XBoolean
{
    static final long serialVersionUID = -8064147275772687409L;

  /** The value of the object.
  /* <p>
  /* 
   *  @serial          */
  private final boolean m_val;

  /**
   * Construct a XBooleanStatic object.
   *
   * <p>
   *  构造一个XBooleanStatic对象。
   * 
   * 
   * @param b The value of the object
   */
  public XBooleanStatic(boolean b)
  {

    super(b);

    m_val = b;
  }

  /**
   * Tell if two objects are functionally equal.
   *
   * <p>
   *  告诉两个对象在功能上是否相等。
   * 
   * @param obj2 Object to compare to this
   *
   * @return True if the two objects are equal
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean equals(XObject obj2)
  {
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
