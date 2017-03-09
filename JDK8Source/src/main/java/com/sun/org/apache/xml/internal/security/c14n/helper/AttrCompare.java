/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.c14n.helper;

import com.sun.org.apache.xml.internal.security.utils.Constants;
import org.w3c.dom.Attr;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Compares two attributes based on the C14n specification.
 *
 * <UL>
 * <LI>Namespace nodes have a lesser document order position than attribute
 *   nodes.
 * <LI> An element's namespace nodes are sorted lexicographically by
 *   local name (the default namespace node, if one exists, has no
 *   local name and is therefore lexicographically least).
 * <LI> An element's attribute nodes are sorted lexicographically with
 *   namespace URI as the primary key and local name as the secondary
 *   key (an empty namespace URI is lexicographically least).
 * </UL>
 *
 * <p>
 *  基于C14n规范比较两个属性。
 * 
 * <UL>
 *  <LI>命名空间节点具有比属性节点更小的文档顺序位置。元素的命名空间节点按照本地名称(默认命名空间节点,如果存在,没有本地名称,因此在字典上最小)按字典顺序排序。
 * 元素的属性节点按照字典顺序排序,命名空间URI作为主键,本地名称作为辅助键(空的命名空间URI按字典顺序最小)。
 * </UL>
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class AttrCompare implements Comparator<Attr>, Serializable {

    private static final long serialVersionUID = -7113259629930576230L;
    private static final int ATTR0_BEFORE_ATTR1 = -1;
    private static final int ATTR1_BEFORE_ATTR0 = 1;
    private static final String XMLNS = Constants.NamespaceSpecNS;

    /**
     * Compares two attributes based on the C14n specification.
     *
     * <UL>
     * <LI>Namespace nodes have a lesser document order position than
     *   attribute nodes.
     * <LI> An element's namespace nodes are sorted lexicographically by
     *   local name (the default namespace node, if one exists, has no
     *   local name and is therefore lexicographically least).
     * <LI> An element's attribute nodes are sorted lexicographically with
     *   namespace URI as the primary key and local name as the secondary
     *   key (an empty namespace URI is lexicographically least).
     * </UL>
     *
     * <p>
     *  基于C14n规范比较两个属性。
     * 
     * <UL>
     * <LI>命名空间节点具有比属性节点更小的文档顺序位置。元素的命名空间节点按照本地名称(默认命名空间节点,如果存在,没有本地名称,因此在字典上最小)按字典顺序排序。
     * 
     * @param attr0
     * @param attr1
     * @return returns a negative integer, zero, or a positive integer as
     *   obj0 is less than, equal to, or greater than obj1
     *
     */
    public int compare(Attr attr0, Attr attr1) {
        String namespaceURI0 = attr0.getNamespaceURI();
        String namespaceURI1 = attr1.getNamespaceURI();

        boolean isNamespaceAttr0 = XMLNS.equals(namespaceURI0);
        boolean isNamespaceAttr1 = XMLNS.equals(namespaceURI1);

        if (isNamespaceAttr0) {
            if (isNamespaceAttr1) {
                // both are namespaces
                String localname0 = attr0.getLocalName();
                String localname1 = attr1.getLocalName();

                if ("xmlns".equals(localname0)) {
                    localname0 = "";
                }

                if ("xmlns".equals(localname1)) {
                    localname1 = "";
                }

                return localname0.compareTo(localname1);
            }
            // attr0 is a namespace, attr1 is not
            return ATTR0_BEFORE_ATTR1;
        } else if (isNamespaceAttr1) {
            // attr1 is a namespace, attr0 is not
            return ATTR1_BEFORE_ATTR0;
        }

        // none is a namespace
        if (namespaceURI0 == null) {
            if (namespaceURI1 == null) {
                String name0 = attr0.getName();
                String name1 = attr1.getName();
                return name0.compareTo(name1);
            }
            return ATTR0_BEFORE_ATTR1;
        } else if (namespaceURI1 == null) {
            return ATTR1_BEFORE_ATTR0;
        }

        int a = namespaceURI0.compareTo(namespaceURI1);
        if (a != 0) {
            return a;
        }

        return (attr0.getLocalName()).compareTo(attr1.getLocalName());
    }
}
