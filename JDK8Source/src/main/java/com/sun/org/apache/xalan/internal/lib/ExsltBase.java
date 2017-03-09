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
 * $Id: ExsltBase.java,v 1.1.2.1 2005/08/01 02:08:51 jeffsuttor Exp $
 * <p>
 *  $ Id：ExsltBase.java,v 1.1.2.1 2005/08/01 02:08:51 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.lib;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The base class for some EXSLT extension classes.
 * It contains common utility methods to be used by the sub-classes.
 * <p>
 *  一些EXSLT扩展类的基类。它包含子类使用的常用实用程序方法。
 * 
 */
public abstract class ExsltBase
{
  /**
   * Return the string value of a Node
   *
   * <p>
   *  返回节点的字符串值
   * 
   * 
   * @param n The Node.
   * @return The string value of the Node
   */
  protected static String toString(Node n)
  {
    if (n instanceof DTMNodeProxy)
         return ((DTMNodeProxy)n).getStringValue();
    else
    {
      String value = n.getNodeValue();
      if (value == null)
      {
        NodeList nodelist = n.getChildNodes();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < nodelist.getLength(); i++)
        {
          Node childNode = nodelist.item(i);
          buf.append(toString(childNode));
        }
        return buf.toString();
      }
      else
        return value;
    }
  }

  /**
   * Convert the string value of a Node to a number.
   * Return NaN if the string is not a valid number.
   *
   * <p>
   *  将节点的字符串值转换为数字。如果字符串不是有效的数字,则返回NaN。
   * 
   * @param n The Node.
   * @return The number value of the Node
   */
  protected static double toNumber(Node n)
  {
    double d = 0.0;
    String str = toString(n);
    try
    {
      d = Double.valueOf(str).doubleValue();
    }
    catch (NumberFormatException e)
    {
      d= Double.NaN;
    }
    return d;
  }
}
