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
 * $Id: ExsltCommon.java,v 1.2.4.1 2005/09/15 02:45:24 jeffsuttor Exp $
 * <p>
 *  $ Id：ExsltCommon.java,v 1.2.4.1 2005/09/15 02:45:24 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.lib;

import com.sun.org.apache.xalan.internal.extensions.ExpressionContext;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeIterator;
import com.sun.org.apache.xpath.internal.NodeSet;

/**
 * This class contains EXSLT common extension functions.
 * It is accessed by specifying a namespace URI as follows:
 * <pre>
 *    xmlns:exslt="http://exslt.org/common"
 * </pre>
 *
 * The documentation for each function has been copied from the relevant
 * EXSLT Implementer page.
 *
 * <p>
 *  此类包含EXSLT公共扩展函数。可通过指定名称空间URI来访问它,如下所示：
 * <pre>
 *  xmlns：exslt ="http://exslt.org/common"
 * </pre>
 * 
 *  每个函数的文档已从相关的EXSLT实施者页面复制。
 * 
 * 
 * @see <a href="http://www.exslt.org/">EXSLT</a>
 * @xsl.usage general
 */
public class ExsltCommon
{
  /**
   * The exsl:object-type function returns a string giving the type of the object passed
   * as the argument. The possible object types are: 'string', 'number', 'boolean',
   * 'node-set', 'RTF', or 'external'.
   *
   * Most XSLT object types can be coerced to each other without error. However, there are
   * certain coercions that raise errors, most importantly treating anything other than a
   * node set as a node set. Authors of utilities such as named templates or user-defined
   * extension functions may wish to give some flexibility in the parameter and argument values
   * that are accepted by the utility; the exsl:object-type function enables them to do so.
   *
   * The Xalan extensions MethodResolver converts 'object-type' to 'objectType'.
   *
   * <p>
   *  exsl：object-type函数返回一个字符串,给出作为参数传递的对象的类型。
   * 可能的对象类型有：'string','number','boolean','node-set','RTF'或'external'。
   * 
   * 大多数XSLT对象类型可以彼此强制转换而不会出错。但是,存在某些强制提高错误,最重要的是将除节点集之外的任何东西视为节点集。
   * 诸如命名模板或用户定义的扩展函数的实用程序的作者可能希望给予该实用程序接受的参数和参数值一些灵活性; exsl：object-type函数使它们能够这样做。
   * 
   *  Xalan扩展MethodResolver将'object-type'转换为'objectType'。
   * 
   * 
   * @param obj The object to be typed.
   * @return objectType 'string', 'number', 'boolean', 'node-set', 'RTF', or 'external'.
   *
   * @see <a href="http://www.exslt.org/">EXSLT</a>
   */
  public static String objectType (Object obj)
  {
    if (obj instanceof String)
      return "string";
    else if (obj instanceof Boolean)
      return "boolean";
    else if (obj instanceof Number)
      return "number";
    else if (obj instanceof DTMNodeIterator)
    {
      DTMIterator dtmI = ((DTMNodeIterator)obj).getDTMIterator();
      if (dtmI instanceof com.sun.org.apache.xpath.internal.axes.RTFIterator)
        return "RTF";
      else
        return "node-set";
    }
    else
      return "unknown";
  }

  /**
   * The exsl:node-set function converts a result tree fragment (which is what you get
   * when you use the content of xsl:variable rather than its select attribute to give
   * a variable value) into a node set. This enables you to process the XML that you create
   * within a variable, and therefore do multi-step processing.
   *
   * You can also use this function to turn a string into a text node, which is helpful
   * if you want to pass a string to a function that only accepts a node set.
   *
   * The Xalan extensions MethodResolver converts 'node-set' to 'nodeSet'.
   *
   * <p>
   * 
   * @param myProcessor is passed in by the Xalan extension processor
   * @param rtf The result tree fragment to be converted to a node-set.
   *
   * @return node-set with the contents of the result tree fragment.
   *
   * Note: Already implemented in the xalan namespace as nodeset.
   *
   * @see <a href="http://www.exslt.org/">EXSLT</a>
   */
  public static NodeSet nodeSet(ExpressionContext myProcessor, Object rtf)
  {
    return Extensions.nodeset(myProcessor, rtf);
  }

}
