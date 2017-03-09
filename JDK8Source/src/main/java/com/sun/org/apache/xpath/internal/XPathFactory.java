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
 * $Id: XPathFactory.java,v 1.1.2.1 2005/08/01 01:30:14 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathFactory.java,v 1.1.2.1 2005/08/01 01:30:14 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import javax.xml.transform.SourceLocator;

import com.sun.org.apache.xml.internal.utils.PrefixResolver;

/**
 * Factory class for creating an XPath.  Implementors of XPath derivatives
 * will need to make a derived class of this.
 * @xsl.usage advanced
 * <p>
 *  用于创建XPath的工厂类。 XPath派生类的实现者需要创建一个派生类。 @ xsl.usage advanced
 * 
 */
public interface XPathFactory
{

  /**
   * Create an XPath.
   *
   * <p>
   *  创建XPath。
   * 
   * @param exprString The XPath expression string.
   * @param locator The location of the expression string, mainly for diagnostic
   *                purposes.
   * @param prefixResolver This will be called in order to resolve prefixes
   *        to namespace URIs.
   * @param type One of {@link com.sun.org.apache.xpath.internal.XPath#SELECT} or
   *             {@link com.sun.org.apache.xpath.internal.XPath#MATCH}.
   *
   * @return an XPath ready for execution.
   */
  XPath create(String exprString, SourceLocator locator,
               PrefixResolver prefixResolver, int type);
}
