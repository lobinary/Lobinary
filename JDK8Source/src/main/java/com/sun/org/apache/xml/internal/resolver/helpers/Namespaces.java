/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Namespaces.java - Analyze namespace nodes in a DOM tree

/*
 * Copyright 2001-2004 The Apache Software Foundation or its licensors,
 * as applicable.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会或其许可方(如适用)。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xml.internal.resolver.helpers;

import org.w3c.dom.*;

/**
 * Static Namespace query methods.
 *
 * <p>This class defines a set of static methods that can be called
 * to analyze the namespace properties of DOM nodes.</p>
 *
 * <p>
 *  静态命名空间查询方法。
 * 
 *  <p>此类定义了一组静态方法,可以调用它们来分析DOM节点的命名空间属性。</p>
 * 
 * 
 * @author Norman Walsh
 * <a href="mailto:Norman.Walsh@Sun.COM">Norman.Walsh@Sun.COM</a>
 *
 */
public class Namespaces {
    /**
     * Returns the "prefix" part of a QName or the empty string (not
     * null) if the name has no prefix.
     *
     * <p>
     *  如果名称没有前缀,则返回QName的"前缀"部分或空字符串(非空)。
     * 
     * 
     * @param element The QName of an element.
     * @return The prefix part of the element name.
     */
    public static String getPrefix(Element element) {
        String name = element.getTagName();
        String prefix = "";

        if (name.indexOf(':') > 0) {
            prefix = name.substring(0, name.indexOf(':'));
        }

        return prefix;
    }

    /**
     * Returns the "localname" part of a QName, which is the whole
     * name if it has no prefix.
     *
     * <p>
     *  返回QName的"localname"部分,如果它没有前缀,它是整个名称。
     * 
     * 
     * @param element The QName of an element.
     * @return The local part of a QName.
     */
    public static String getLocalName(Element element) {
        String name = element.getTagName();

        if (name.indexOf(':') > 0) {
            name = name.substring(name.indexOf(':')+1);
        }

        return name;
    }

    /**
     * Returns the namespace URI for the specified prefix at the
     * specified context node.
     *
     * <p>
     *  返回指定上下文节点上指定前缀的命名空间URI。
     * 
     * 
     * @param node The context node.
     * @param prefix The prefix.
     * @return The namespace URI associated with the prefix, or
     * null if no namespace declaration exists for the prefix.
     */
    public static String getNamespaceURI(Node node, String prefix) {
        if (node == null || node.getNodeType() != Node.ELEMENT_NODE) {
            return null;
        }

        if (prefix.equals("")) {
            if (((Element) node).hasAttribute("xmlns")) {
                return ((Element) node).getAttribute("xmlns");
            }
        } else {
            String nsattr = "xmlns:" + prefix;
            if (((Element) node).hasAttribute(nsattr)) {
                return ((Element) node).getAttribute(nsattr);
            }
        }

        return getNamespaceURI(node.getParentNode(), prefix);
    }

    /**
     * Returns the namespace URI for the namespace to which the
     * element belongs.
     *
     * <p>
     *  返回元素所属的命名空间的命名空间URI。
     * 
     * @param element The element.
     * @return The namespace URI associated with the namespace of the
     * element, or null if no namespace declaration exists for it.
     */
    public static String getNamespaceURI(Element element) {
        String prefix = getPrefix(element);
        return getNamespaceURI(element, prefix);
    }
}
