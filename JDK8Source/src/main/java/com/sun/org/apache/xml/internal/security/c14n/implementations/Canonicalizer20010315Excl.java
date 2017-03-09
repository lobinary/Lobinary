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
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.params.InclusiveNamespaces;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Implements &quot; <A
 * HREF="http://www.w3.org/TR/2002/REC-xml-exc-c14n-20020718/">Exclusive XML
 * Canonicalization, Version 1.0 </A>&quot; <BR />
 * Credits: During restructuring of the Canonicalizer framework, Ren??
 * Kollmorgen from Software AG submitted an implementation of ExclC14n which
 * fitted into the old architecture and which based heavily on my old (and slow)
 * implementation of "Canonical XML". A big "thank you" to Ren?? for this.
 * <BR />
 * <i>THIS </i> implementation is a complete rewrite of the algorithm.
 *
 * <p>
 *  实现" <A HREF="http://www.w3.org/TR/2002/REC-xml-exc-c14n-20020718/">独家XML规范化版本1.0 </A>" <BR /> Credit
 * s：在重构Canonicalizer框架期间,Ren? Software AG的Kollmorgen提交了一个ExclC14n的实现,它适用于旧的体系结构,并且在很大程度上依赖于我原来的(和缓慢的)"规
 * 范XML"的实现。
 * 一个大"谢谢"任仁?为了这。
 * <BR />
 *  <i> THIS </i>实现是算法的完全重写。
 * 
 * 
 * @author Christian Geuer-Pollmann <geuerp@apache.org>
 * @version $Revision: 1147448 $
 * @see <a href="http://www.w3.org/TR/2002/REC-xml-exc-c14n-20020718/ Exclusive#">
 *          XML Canonicalization, Version 1.0</a>
 */
public abstract class Canonicalizer20010315Excl extends CanonicalizerBase {

    private static final String XML_LANG_URI = Constants.XML_LANG_SPACE_SpecNS;
    private static final String XMLNS_URI = Constants.NamespaceSpecNS;

    /**
      * This Set contains the names (Strings like "xmlns" or "xmlns:foo") of
      * the inclusive namespaces.
      * <p>
      *  此集合包含包含名称空间的名称(类似"xmlns"或"xmlns：foo"的字符串)。
      * 
      */
    private SortedSet<String> inclusiveNSSet;

    private final SortedSet<Attr> result = new TreeSet<Attr>(COMPARE);

    /**
     * Constructor Canonicalizer20010315Excl
     *
     * <p>
     * 构造函数Canonicalizer20010315Excl
     * 
     * 
     * @param includeComments
     */
    public Canonicalizer20010315Excl(boolean includeComments) {
        super(includeComments);
    }

    /**
     * Method engineCanonicalizeSubTree
     * @inheritDoc
     * <p>
     *  方法engineCanonicalizeSubTree @inheritDoc
     * 
     * 
     * @param rootNode
     *
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeSubTree(Node rootNode)
        throws CanonicalizationException {
        return engineCanonicalizeSubTree(rootNode, "", null);
    }

    /**
     * Method engineCanonicalizeSubTree
     *  @inheritDoc
     * <p>
     *  方法engineCanonicalizeSubTree @inheritDoc
     * 
     * 
     * @param rootNode
     * @param inclusiveNamespaces
     *
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeSubTree(
        Node rootNode, String inclusiveNamespaces
    ) throws CanonicalizationException {
        return engineCanonicalizeSubTree(rootNode, inclusiveNamespaces, null);
    }

    /**
     * Method engineCanonicalizeSubTree
     * <p>
     *  方法engineCanonicalizeSubTree
     * 
     * 
     * @param rootNode
     * @param inclusiveNamespaces
     * @param excl A element to exclude from the c14n process.
     * @return the rootNode c14n.
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeSubTree(
        Node rootNode, String inclusiveNamespaces, Node excl
    ) throws CanonicalizationException{
        inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
        return super.engineCanonicalizeSubTree(rootNode, excl);
    }

    /**
     *
     * <p>
     * 
     * @param rootNode
     * @param inclusiveNamespaces
     * @return the rootNode c14n.
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalize(
        XMLSignatureInput rootNode, String inclusiveNamespaces
    ) throws CanonicalizationException {
        inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
        return super.engineCanonicalize(rootNode);
    }

    /**
     * Method engineCanonicalizeXPathNodeSet
     * @inheritDoc
     * <p>
     *  方法engineCanonicalizeXPathNodeSet @inheritDoc
     * 
     * 
     * @param xpathNodeSet
     * @param inclusiveNamespaces
     * @throws CanonicalizationException
     */
    public byte[] engineCanonicalizeXPathNodeSet(
        Set<Node> xpathNodeSet, String inclusiveNamespaces
    ) throws CanonicalizationException {
        inclusiveNSSet = InclusiveNamespaces.prefixStr2Set(inclusiveNamespaces);
        return super.engineCanonicalizeXPathNodeSet(xpathNodeSet);
    }

    @Override
    protected Iterator<Attr> handleAttributesSubtree(Element element, NameSpaceSymbTable ns)
        throws CanonicalizationException {
        // result will contain the attrs which have to be output
        final SortedSet<Attr> result = this.result;
        result.clear();

        // The prefix visibly utilized (in the attribute or in the name) in
        // the element
        SortedSet<String> visiblyUtilized = new TreeSet<String>();
        if (inclusiveNSSet != null && !inclusiveNSSet.isEmpty()) {
            visiblyUtilized.addAll(inclusiveNSSet);
        }

        if (element.hasAttributes()) {
            NamedNodeMap attrs = element.getAttributes();
            int attrsLength = attrs.getLength();
            for (int i = 0; i < attrsLength; i++) {
                Attr attribute = (Attr) attrs.item(i);
                String NName = attribute.getLocalName();
                String NNodeValue = attribute.getNodeValue();

                if (!XMLNS_URI.equals(attribute.getNamespaceURI())) {
                    // Not a namespace definition.
                    // The Element is output element, add the prefix (if used) to
                    // visiblyUtilized
                    String prefix = attribute.getPrefix();
                    if (prefix != null && !(prefix.equals(XML) || prefix.equals(XMLNS))) {
                        visiblyUtilized.add(prefix);
                    }
                    // Add to the result.
                    result.add(attribute);
                } else if (!(XML.equals(NName) && XML_LANG_URI.equals(NNodeValue))
                    && ns.addMapping(NName, NNodeValue, attribute)
                    && C14nHelper.namespaceIsRelative(NNodeValue)) {
                    // The default mapping for xml must not be output.
                    // New definition check if it is relative.
                    Object exArgs[] = {element.getTagName(), NName, attribute.getNodeValue()};
                    throw new CanonicalizationException(
                        "c14n.Canonicalizer.RelativeNamespace", exArgs
                    );
                }
            }
        }
        String prefix = null;
        if (element.getNamespaceURI() != null
            && !(element.getPrefix() == null || element.getPrefix().length() == 0)) {
            prefix = element.getPrefix();
        } else {
            prefix = XMLNS;
        }
        visiblyUtilized.add(prefix);

        for (String s : visiblyUtilized) {
            Attr key = ns.getMapping(s);
            if (key != null) {
                result.add(key);
            }
        }

        return result.iterator();
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     * 
     * @param element
     * @throws CanonicalizationException
     */
    @Override
    protected final Iterator<Attr> handleAttributes(Element element, NameSpaceSymbTable ns)
        throws CanonicalizationException {
        // result will contain the attrs which have to be output
        final SortedSet<Attr> result = this.result;
        result.clear();

        // The prefix visibly utilized (in the attribute or in the name) in
        // the element
        Set<String> visiblyUtilized = null;
        // It's the output selected.
        boolean isOutputElement = isVisibleDO(element, ns.getLevel()) == 1;
        if (isOutputElement) {
            visiblyUtilized = new TreeSet<String>();
            if (inclusiveNSSet != null && !inclusiveNSSet.isEmpty()) {
                visiblyUtilized.addAll(inclusiveNSSet);
            }
        }

        if (element.hasAttributes()) {
            NamedNodeMap attrs = element.getAttributes();
            int attrsLength = attrs.getLength();
            for (int i = 0; i < attrsLength; i++) {
                Attr attribute = (Attr) attrs.item(i);

                String NName = attribute.getLocalName();
                String NNodeValue = attribute.getNodeValue();

                if (!XMLNS_URI.equals(attribute.getNamespaceURI())) {
                    if (isVisible(attribute) && isOutputElement) {
                        // The Element is output element, add the prefix (if used)
                        // to visibyUtilized
                        String prefix = attribute.getPrefix();
                        if (prefix != null && !(prefix.equals(XML) || prefix.equals(XMLNS))) {
                            visiblyUtilized.add(prefix);
                        }
                        // Add to the result.
                        result.add(attribute);
                    }
                } else if (isOutputElement && !isVisible(attribute) && !XMLNS.equals(NName)) {
                    ns.removeMappingIfNotRender(NName);
                } else {
                    if (!isOutputElement && isVisible(attribute)
                        && inclusiveNSSet.contains(NName)
                        && !ns.removeMappingIfRender(NName)) {
                        Node n = ns.addMappingAndRender(NName, NNodeValue, attribute);
                        if (n != null) {
                            result.add((Attr)n);
                            if (C14nHelper.namespaceIsRelative(attribute)) {
                                Object exArgs[] = { element.getTagName(), NName, attribute.getNodeValue() };
                                throw new CanonicalizationException(
                                    "c14n.Canonicalizer.RelativeNamespace", exArgs
                                );
                            }
                        }
                    }

                    if (ns.addMapping(NName, NNodeValue, attribute)
                        && C14nHelper.namespaceIsRelative(NNodeValue)) {
                        // New definition check if it is relative
                        Object exArgs[] = { element.getTagName(), NName, attribute.getNodeValue() };
                        throw new CanonicalizationException(
                            "c14n.Canonicalizer.RelativeNamespace", exArgs
                        );
                    }
                }
            }
        }

        if (isOutputElement) {
            // The element is visible, handle the xmlns definition
            Attr xmlns = element.getAttributeNodeNS(XMLNS_URI, XMLNS);
            if (xmlns != null && !isVisible(xmlns)) {
                // There is a definition but the xmlns is not selected by the
                // xpath. then xmlns=""
                ns.addMapping(XMLNS, "", getNullNode(xmlns.getOwnerDocument()));
            }

            String prefix = null;
            if (element.getNamespaceURI() != null
                && !(element.getPrefix() == null || element.getPrefix().length() == 0)) {
                prefix = element.getPrefix();
            } else {
                prefix = XMLNS;
            }
            visiblyUtilized.add(prefix);

            for (String s : visiblyUtilized) {
                Attr key = ns.getMapping(s);
                if (key != null) {
                    result.add(key);
                }
            }
        }

        return result.iterator();
    }

    protected void circumventBugIfNeeded(XMLSignatureInput input)
        throws CanonicalizationException, ParserConfigurationException,
               IOException, SAXException {
        if (!input.isNeedsToBeExpanded() || inclusiveNSSet.isEmpty() || inclusiveNSSet.isEmpty()) {
            return;
        }
        Document doc = null;
        if (input.getSubNode() != null) {
            doc = XMLUtils.getOwnerDocument(input.getSubNode());
        } else {
            doc = XMLUtils.getOwnerDocument(input.getNodeSet());
        }
        XMLUtils.circumventBug2650(doc);
    }
}
