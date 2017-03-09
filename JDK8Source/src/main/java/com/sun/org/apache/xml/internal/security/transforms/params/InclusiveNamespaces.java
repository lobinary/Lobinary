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
package com.sun.org.apache.xml.internal.security.transforms.params;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This Object serves as Content for the ds:Transforms for exclusive
 * Canonicalization.
 * <BR />
 * It implements the {@link Element} interface
 * and can be used directly in a DOM tree.
 *
 * <p>
 *  此对象用作专用规范化的ds：Transforms的内容。
 * <BR />
 *  它实现了{@link Element}接口,可以直接在DOM树中使用。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class InclusiveNamespaces extends ElementProxy implements TransformParam {

    /** Field _TAG_EC_INCLUSIVENAMESPACES */
    public static final String _TAG_EC_INCLUSIVENAMESPACES =
        "InclusiveNamespaces";

    /** Field _ATT_EC_PREFIXLIST */
    public static final String _ATT_EC_PREFIXLIST = "PrefixList";

    /** Field ExclusiveCanonicalizationNamespace */
    public static final String ExclusiveCanonicalizationNamespace =
        "http://www.w3.org/2001/10/xml-exc-c14n#";

    /**
     * Constructor XPathContainer
     *
     * <p>
     *  构造函数XPathContainer
     * 
     * 
     * @param doc
     * @param prefixList
     */
    public InclusiveNamespaces(Document doc, String prefixList) {
        this(doc, InclusiveNamespaces.prefixStr2Set(prefixList));
    }

    /**
     * Constructor InclusiveNamespaces
     *
     * <p>
     *  构造函数包含
     * 
     * 
     * @param doc
     * @param prefixes
     */
    public InclusiveNamespaces(Document doc, Set<String> prefixes) {
        super(doc);

        SortedSet<String> prefixList = null;
        if (prefixes instanceof SortedSet<?>) {
            prefixList = (SortedSet<String>)prefixes;
        } else {
            prefixList = new TreeSet<String>(prefixes);
        }

        StringBuilder sb = new StringBuilder();
        for (String prefix : prefixList) {
            if (prefix.equals("xmlns")) {
                sb.append("#default ");
            } else {
                sb.append(prefix + " ");
            }
        }

        this.constructionElement.setAttributeNS(
            null, InclusiveNamespaces._ATT_EC_PREFIXLIST, sb.toString().trim());
    }

    /**
     * Constructor InclusiveNamespaces
     *
     * <p>
     *  构造函数包含
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public InclusiveNamespaces(Element element, String BaseURI)
        throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Method getInclusiveNamespaces
     *
     * <p>
     *  方法getInclusiveNamespaces
     * 
     * 
     * @return The Inclusive Namespace string
     */
    public String getInclusiveNamespaces() {
        return this.constructionElement.getAttributeNS(null, InclusiveNamespaces._ATT_EC_PREFIXLIST);
    }

    /**
     * Decodes the <code>inclusiveNamespaces</code> String and returns all
     * selected namespace prefixes as a Set. The <code>#default</code>
     * namespace token is represented as an empty namespace prefix
     * (<code>"xmlns"</code>).
     * <BR/>
     * The String <code>inclusiveNamespaces=" xenc    ds #default"</code>
     * is returned as a Set containing the following Strings:
     * <UL>
     * <LI><code>xmlns</code></LI>
     * <LI><code>xenc</code></LI>
     * <LI><code>ds</code></LI>
     * </UL>
     *
     * <p>
     *  解码<code> inclusiveNamespaces </code>字符串并返回所有选定的命名空间前缀作为一个集合。
     *  <code> #default </code>命名空间令牌表示为空命名空间前缀(<code>"xmlns"</code>)。
     * <BR/>
     * 字符串<code> inclusiveNamespaces ="xenc ds #default"</code>作为包含以下字符串的Set返回：
     * <UL>
     *  <LI> <code> xmlns </code> </li> <li> <code> xenc </code> </li> <li> <code>
     * </UL>
     * 
     * 
     * @param inclusiveNamespaces
     * @return A set to string
     */
    public static SortedSet<String> prefixStr2Set(String inclusiveNamespaces) {
        SortedSet<String> prefixes = new TreeSet<String>();

        if ((inclusiveNamespaces == null) || (inclusiveNamespaces.length() == 0)) {
            return prefixes;
        }

        String[] tokens = inclusiveNamespaces.split("\\s");
        for (String prefix : tokens) {
            if (prefix.equals("#default")) {
                prefixes.add("xmlns");
            } else {
                prefixes.add(prefix);
            }
        }

        return prefixes;
    }

    /**
     * Method getBaseNamespace
     *
     * @inheritDoc
     * <p>
     *  方法getBaseNamespace
     * 
     *  @inheritDoc
     * 
     */
    public String getBaseNamespace() {
        return InclusiveNamespaces.ExclusiveCanonicalizationNamespace;
    }

    /**
     * Method getBaseLocalName
     *
     * @inheritDoc
     * <p>
     *  方法getBaseLocalName
     * 
     */
    public String getBaseLocalName() {
        return InclusiveNamespaces._TAG_EC_INCLUSIVENAMESPACES;
    }
}
