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
 * $Id: SubContextList.java,v 1.1.2.1 2005/08/01 01:30:28 jeffsuttor Exp $
 * <p>
 *  $ Id：SubContextList.java,v 1.1.2.1 2005/08/01 01:30:28 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xpath.internal.XPathContext;

/**
 * A class that implements this interface is a sub context node list, meaning it
 * is a node list for a location path step for a predicate.
 * @xsl.usage advanced
 * <p>
 *  实现此接口的类是子上下文节点列表,这意味着它是谓词的位置路径步骤的节点列表。 @ xsl.usage advanced
 * 
 */
public interface SubContextList
{

  /**
   * Get the number of nodes in the node list, which, in the XSLT 1 based
   * counting system, is the last index position.
   *
   *
   * <p>
   *  获取节点列表中的节点数,在基于XSLT 1的计数系统中,节点列表是最后一个索引位置。
   * 
   * 
   * @param xctxt The XPath runtime context.
   *
   * @return the number of nodes in the node list.
   */
  public int getLastPos(XPathContext xctxt);

  /**
   * Get the current sub-context position.
   *
   * <p>
   *  获取当前子上下文位置。
   * 
   * @param xctxt The XPath runtime context.
   *
   * @return The position of the current node in the list.
   */
  public int getProximityPosition(XPathContext xctxt);
}
