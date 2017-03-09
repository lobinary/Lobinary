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
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * <p>
 *  版权所有(c)2005,2013,Oracle和/或其附属公司。版权所有。
 * 
 */
/*
 * $Id$
 * <p>
 *  $ Id $
 * 
 */
package com.sun.org.apache.xml.internal.security.signature.reference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * A representation of a <code>ReferenceNodeSetData</code> type containing a node-set.
 * This is a subtype of NodeSetData that represents a dereferenced
 * same-document URI as the root of a subdocument. The main reason is
 * for efficiency and performance, as some transforms can operate
 * directly on the subdocument and there is no need to convert it
 * first to an XPath node-set.
 * <p>
 *  包含节点集的<code> ReferenceNodeSetData </code>类型的表示形式。这是NodeSetData的子类型,表示与子文档的根解引用的同一文档URI。
 * 主要原因是效率和性能,因为一些转换可以直接对子文档操作,并且没有必要将它首先转换为XPath节点集。
 * 
 */
public class ReferenceSubTreeData implements ReferenceNodeSetData {

    private boolean excludeComments;
    private Node root;

    public ReferenceSubTreeData(Node root, boolean excludeComments) {
        this.root = root;
        this.excludeComments = excludeComments;
    }

    public Iterator<Node> iterator() {
        return new DelayedNodeIterator(root, excludeComments);
    }

    public Node getRoot() {
        return root;
    }

    public boolean excludeComments() {
        return excludeComments;
    }

    /**
     * This is an Iterator that contains a backing node-set that is
     * not populated until the caller first attempts to advance the iterator.
     * <p>
     *  这是一个迭代器,它包含一个后备节点集,在调用者首次尝试提前迭代器之前,它不会被填充。
     * 
     */
    static class DelayedNodeIterator implements Iterator<Node> {
        private Node root;
        private List<Node> nodeSet;
        private ListIterator<Node> li;
        private boolean withComments;

        DelayedNodeIterator(Node root, boolean excludeComments) {
            this.root = root;
            this.withComments = !excludeComments;
        }

        public boolean hasNext() {
            if (nodeSet == null) {
                nodeSet = dereferenceSameDocumentURI(root);
                li = nodeSet.listIterator();
            }
            return li.hasNext();
        }

        public Node next() {
            if (nodeSet == null) {
                nodeSet = dereferenceSameDocumentURI(root);
                li = nodeSet.listIterator();
            }
            if (li.hasNext()) {
                return li.next();
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Dereferences a same-document URI fragment.
         *
         * <p>
         * 解除同一文档URI片段。
         * 
         * 
         * @param node the node (document or element) referenced by the
         *        URI fragment. If null, returns an empty set.
         * @return a set of nodes (minus any comment nodes)
         */
        private List<Node> dereferenceSameDocumentURI(Node node) {
            List<Node> nodeSet = new ArrayList<Node>();
            if (node != null) {
                nodeSetMinusCommentNodes(node, nodeSet, null);
            }
            return nodeSet;
        }

        /**
         * Recursively traverses the subtree, and returns an XPath-equivalent
         * node-set of all nodes traversed, excluding any comment nodes,
         * if specified.
         *
         * <p>
         *  递归地遍历子树,并返回所有经过的节点的XPath等效节点集,排除任何注释节点(如果指定)。
         * 
         * @param node the node to traverse
         * @param nodeSet the set of nodes traversed so far
         * @param the previous sibling node
         */
        @SuppressWarnings("fallthrough")
        private void nodeSetMinusCommentNodes(Node node, List<Node> nodeSet,
                                              Node prevSibling)
        {
            switch (node.getNodeType()) {
                case Node.ELEMENT_NODE :
                    nodeSet.add(node);
                    NamedNodeMap attrs = node.getAttributes();
                    if (attrs != null) {
                        for (int i = 0, len = attrs.getLength(); i < len; i++) {
                            nodeSet.add(attrs.item(i));
                        }
                    }
                    Node pSibling = null;
                    for (Node child = node.getFirstChild(); child != null;
                        child = child.getNextSibling()) {
                        nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                        pSibling = child;
                    }
                    break;
                case Node.DOCUMENT_NODE :
                    pSibling = null;
                    for (Node child = node.getFirstChild(); child != null;
                        child = child.getNextSibling()) {
                        nodeSetMinusCommentNodes(child, nodeSet, pSibling);
                        pSibling = child;
                    }
                    break;
                case Node.TEXT_NODE :
                case Node.CDATA_SECTION_NODE:
                    // emulate XPath which only returns the first node in
                    // contiguous text/cdata nodes
                    if (prevSibling != null &&
                        (prevSibling.getNodeType() == Node.TEXT_NODE ||
                         prevSibling.getNodeType() == Node.CDATA_SECTION_NODE)) {
                        return;
                    }
                    nodeSet.add(node);
                    break;
                case Node.PROCESSING_INSTRUCTION_NODE :
                    nodeSet.add(node);
                    break;
                case Node.COMMENT_NODE:
                    if (withComments) {
                        nodeSet.add(node);
                    }
            }
        }
    }
}
