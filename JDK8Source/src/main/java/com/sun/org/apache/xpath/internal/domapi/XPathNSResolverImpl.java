/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2005 The Apache Software Foundation.
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
 *  版权所有2002-2005 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XPathNSResolverImpl.java,v 1.2.4.1 2005/09/10 04:13:19 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathNSResolverImpl.java,v 1.2.4.1 2005/09/10 04:13:19 jeffsuttor Exp $
 * 
 */

package com.sun.org.apache.xpath.internal.domapi;

import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathNSResolver;

/**
 *
 * The class provides an implementation XPathNSResolver according
 * to the DOM L3 XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>See also the <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document Object Model (DOM) Level 3 XPath Specification</a>.</p>
 *
 * <p>The <code>XPathNSResolver</code> interface permit <code>prefix</code>
 * strings in the expression to be properly bound to
 * <code>namespaceURI</code> strings. <code>XPathEvaluator</code> can
 * construct an implementation of <code>XPathNSResolver</code> from a node,
 * or the interface may be implemented by any application.</p>
 *
 * <p>
 *  该类根据DOM L3 XPath规范提供了一个实现XPathNSResolver,工作组注2004年2月26日。
 * 
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>文档对象模型(DOM)3级XPath规范< a>。
 * </p>。
 * 
 *  <p> <code> XPathNSResolver </code>接口允许<code>前缀</code>字符串正确绑定到<code> namespaceURI </code>字符串。
 * 
 * @see org.w3c.dom.xpath.XPathNSResolver
 * @xsl.usage internal
 */
class XPathNSResolverImpl extends PrefixResolverDefault implements XPathNSResolver {

        /**
         * Constructor for XPathNSResolverImpl.
         * <p>
         *  <code> XPathEvaluator </code>可以从节点构造<code> XPathNSResolver </code>的实现,或者接口可以由任何应用程序实现。</p>。
         * 
         * 
         * @param xpathExpressionContext
         */
        public XPathNSResolverImpl(Node xpathExpressionContext) {
                super(xpathExpressionContext);
        }

        /**
        /* <p>
        /*  XPathNSResolverImpl的构造函数。
        /* 
        /* 
         * @see org.w3c.dom.xpath.XPathNSResolver#lookupNamespaceURI(String)
         */
        public String lookupNamespaceURI(String prefix) {
                return super.getNamespaceForPrefix(prefix);
        }

}
