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
package com.sun.org.apache.xml.internal.security.c14n.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.SAXException;

/**
 * Serializes the physical representation of the subtree. All the attributes
 * present in the subtree are emitted. The attributes are sorted within an element,
 * with the namespace declarations appearing before the regular attributes.
 * This algorithm is not a true canonicalization since equivalent subtrees
 * may produce different output. It is therefore unsuitable for digital signatures.
 * This same property makes it ideal for XML Encryption Syntax and Processing,
 * because the decrypted XML content will share the same physical representation
 * as the original XML content that was encrypted.
 * <p>
 *  序列化子树的物理表示。发出子树中存在的所有属性。属性在元素内排序,命名空间声明出现在常规属性之前。该算法不是真正的规范化,因为等效子树可能产生不同的输出。因此,它不适合于数字签名。
 * 此相同的属性使其成为XML加密语法和处理的理想选择,因为解密的XML内容将与被加密的原始XML内容共享相同的物理表示。
 * 
 */
public class CanonicalizerPhysical extends CanonicalizerBase {

    private final SortedSet<Attr> result = new TreeSet<Attr>(COMPARE);

    /**
     * Constructor Canonicalizer20010315
     * <p>
     * 构造函数Canonicalizer20010315
     * 
     */
    public CanonicalizerPhysical() {
        super(true);
    }

    /**
     * Always throws a CanonicalizationException.
     *
     * <p>
     *  总是抛出CanonicalizationException。
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @return none it always fails
     * @throws CanonicalizationException always
     */
    public byte[] engineCanonicalizeXPathNodeSet(Set<Node> xpathNodeSet, String inclusiveNamespaces)
        throws CanonicalizationException {

        /** $todo$ well, should we throw UnsupportedOperationException ? */
        throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
    }

    /**
     * Always throws a CanonicalizationException.
     *
     * <p>
     *  总是抛出CanonicalizationException。
     * 
     * 
     * @param rootNode
     * @param inclusiveNamespaces
     * @return none it always fails
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeSubTree(Node rootNode, String inclusiveNamespaces)
        throws CanonicalizationException {

        /** $todo$ well, should we throw UnsupportedOperationException ? */
        throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
    }

    /**
     * Returns the Attr[]s to be output for the given element.
     * <br>
     * The code of this method is a copy of {@link #handleAttributes(Element,
     * NameSpaceSymbTable)},
     * whereas it takes into account that subtree-c14n is -- well -- subtree-based.
     * So if the element in question isRoot of c14n, it's parent is not in the
     * node set, as well as all other ancestors.
     *
     * <p>
     *  返回要为给定元素输出的Attr []。
     * <br>
     *  此方法的代码是{@link #handleAttributes(Element,NameSpaceSymbTable)}的副本,而它考虑到子树-c14n是基于良好子树的。
     * 因此,如果有问题的元素是ROW的c14n,它的父节点不在节点集,以及所有其他祖先。
     * 
     * @param element
     * @param ns
     * @return the Attr[]s to be output
     * @throws CanonicalizationException
     */
    @Override
    protected Iterator<Attr> handleAttributesSubtree(Element element, NameSpaceSymbTable ns)
        throws CanonicalizationException {
        if (!element.hasAttributes()) {
            return null;
        }

        // result will contain all the attrs declared directly on that element
        final SortedSet<Attr> result = this.result;
        result.clear();

        if (element.hasAttributes()) {
            NamedNodeMap attrs = element.getAttributes();
            int attrsLength = attrs.getLength();

            for (int i = 0; i < attrsLength; i++) {
                Attr attribute = (Attr) attrs.item(i);
                result.add(attribute);
            }
        }

        return result.iterator();
    }

    /**
     * Returns the Attr[]s to be output for the given element.
     *
     * <p>
     * 
     * 
     * @param element
     * @param ns
     * @return the Attr[]s to be output
     * @throws CanonicalizationException
     */
    @Override
    protected Iterator<Attr> handleAttributes(Element element, NameSpaceSymbTable ns)
        throws CanonicalizationException {

        /** $todo$ well, should we throw UnsupportedOperationException ? */
        throw new CanonicalizationException("c14n.Canonicalizer.UnsupportedOperation");
    }

    protected void circumventBugIfNeeded(XMLSignatureInput input)
        throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
        // nothing to do
    }

    @Override
    protected void handleParent(Element e, NameSpaceSymbTable ns) {
        // nothing to do
    }

    /** @inheritDoc */
    public final String engineGetURI() {
        return Canonicalizer.ALGO_ID_C14N_PHYSICAL;
    }

    /** @inheritDoc */
    public final boolean engineGetIncludeComments() {
        return true;
    }

    @Override
    protected void outputPItoWriter(ProcessingInstruction currentPI,
                                    OutputStream writer, int position) throws IOException {
        // Processing Instructions before or after the document element are not treated specially
        super.outputPItoWriter(currentPI, writer, NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT);
    }

    @Override
    protected void outputCommentToWriter(Comment currentComment,
                                         OutputStream writer, int position) throws IOException {
        // Comments before or after the document element are not treated specially
        super.outputCommentToWriter(currentComment, writer, NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT);
    }

}
