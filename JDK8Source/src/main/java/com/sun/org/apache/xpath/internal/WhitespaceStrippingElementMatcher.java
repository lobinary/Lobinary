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
 * $Id: WhitespaceStrippingElementMatcher.java,v 1.1.2.1 2005/08/01 01:30:15 jeffsuttor Exp $
 * <p>
 *  $ Id：WhitespaceStrippingElementMatcher.java,v 1.1.2.1 2005/08/01 01:30:15 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Element;

/**
 * A class that implements this interface can tell if a given element should
 * strip whitespace nodes from it's children.
 * <p>
 *  实现这个接口的类可以告诉给定的元素是否应该从它的子节点去除空白节点。
 * 
 */
public interface WhitespaceStrippingElementMatcher
{
  /**
   * Get information about whether or not an element should strip whitespace.
   * <p>
   *  获取有关元素是否应剥除空格的信息。
   * 
   * 
   * @see <a href="http://www.w3.org/TR/xslt#strip">strip in XSLT Specification</a>
   *
   * @param support The XPath runtime state.
   * @param targetElement Element to check
   *
   * @return true if the whitespace should be stripped.
   *
   * @throws TransformerException
   */
  public boolean shouldStripWhiteSpace(
          XPathContext support, Element targetElement) throws TransformerException;

  /**
   * Get information about whether or not whitespace can be stripped.
   * <p>
   *  获取有关是否可以删除空格的信息。
   * 
   * @see <a href="http://www.w3.org/TR/xslt#strip">strip in XSLT Specification</a>
   *
   * @return true if the whitespace can be stripped.
   */
  public boolean canStripWhiteSpace();
}
