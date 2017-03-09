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
// $Id: JAXPPrefixResolver.java,v 1.1.2.1 2005/08/01 01:30:18 jeffsuttor Exp $

package com.sun.org.apache.xpath.internal.jaxp;

import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;

import javax.xml.namespace.NamespaceContext;

/**
 * <meta name="usage" content="general"/>
 * This class implements a Default PrefixResolver which
 * can be used to perform prefix-to-namespace lookup
 * for the XPath object.
 * This class delegates the resolution to the passed NamespaceContext
 * <p>
 * <meta name="usage" content="general"/>
 *  此类实现了一个Default PrefixResolver,可用于对XPath对象执行前缀到命名空间的查找。此类将解析委派给传递的NamespaceContext
 * 
 */
public class JAXPPrefixResolver implements PrefixResolver
{

    private NamespaceContext namespaceContext;


    public JAXPPrefixResolver ( NamespaceContext nsContext ) {
        this.namespaceContext = nsContext;
    }


    public String getNamespaceForPrefix( String prefix ) {
        return namespaceContext.getNamespaceURI( prefix );
    }

    /**
     * Return the base identifier.
     *
     * <p>
     *  返回基本标识符。
     * 
     * 
     * @return null
     */
    public String getBaseIdentifier() {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see PrefixResolver#handlesNullPrefixes()
     */
    public boolean handlesNullPrefixes() {
        return false;
    }


    /**
     * The URI for the XML namespace.
     * (Duplicate of that found in com.sun.org.apache.xpath.internal.XPathContext).
     * <p>
     *  XML命名空间的URI。 (在com.sun.org.apache.xpath.internal.XPathContext中找到的重复)。
     * 
     */

    public static final String S_XMLNAMESPACEURI =
        "http://www.w3.org/XML/1998/namespace";


    /**
     * Given a prefix and a Context Node, get the corresponding namespace.
     * Warning: This will not work correctly if namespaceContext
     * is an attribute node.
     * <p>
     *  给定前缀和上下文节点,获取相应的命名空间。警告：如果namespaceContext是属性节点,这将无法正常工作。
     * 
     * @param prefix Prefix to resolve.
     * @param namespaceContext Node from which to start searching for a
     * xmlns attribute that binds a prefix to a namespace.
     * @return Namespace that prefix resolves to, or null if prefix
     * is not bound.
     */
    public String getNamespaceForPrefix(String prefix,
                                      org.w3c.dom.Node namespaceContext) {
        Node parent = namespaceContext;
        String namespace = null;

        if (prefix.equals("xml")) {
            namespace = S_XMLNAMESPACEURI;
        } else {
            int type;

            while ((null != parent) && (null == namespace)
                && (((type = parent.getNodeType()) == Node.ELEMENT_NODE)
                    || (type == Node.ENTITY_REFERENCE_NODE))) {

                if (type == Node.ELEMENT_NODE) {
                    NamedNodeMap nnm = parent.getAttributes();

                    for (int i = 0; i < nnm.getLength(); i++) {
                        Node attr = nnm.item(i);
                        String aname = attr.getNodeName();
                        boolean isPrefix = aname.startsWith("xmlns:");

                        if (isPrefix || aname.equals("xmlns")) {
                            int index = aname.indexOf(':');
                            String p =isPrefix ?aname.substring(index + 1) :"";

                            if (p.equals(prefix)) {
                                namespace = attr.getNodeValue();
                                break;
                            }
                        }
                    }
                }

                parent = parent.getParentNode();
            }
        }
        return namespace;
    }

}
