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


import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.TransformParam;
import com.sun.org.apache.xml.internal.security.utils.ElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Implements the parameters for a custom Transform which has a better performance
 * than the xfilter2.
 *
 * <p>
 *  实现自定义Transform的参数,它的性能比xfilter2更好。
 * 
 * 
 * @author $Author: coheigea $
 */
public class XPathFilterCHGPContainer extends ElementProxy implements TransformParam {

    public static final String TRANSFORM_XPATHFILTERCHGP =
        "http://www.nue.et-inf.uni-siegen.de/~geuer-pollmann/#xpathFilter";

    /** Field _ATT_FILTER_VALUE_INTERSECT */
    private static final String _TAG_INCLUDE_BUT_SEARCH = "IncludeButSearch";

    /** Field _ATT_FILTER_VALUE_SUBTRACT */
    private static final String _TAG_EXCLUDE_BUT_SEARCH = "ExcludeButSearch";

    /** Field _ATT_FILTER_VALUE_UNION */
    private static final String _TAG_EXCLUDE = "Exclude";

    /** Field _TAG_XPATHCHGP */
    public static final String _TAG_XPATHCHGP = "XPathAlternative";

    /** Field _ATT_INCLUDESLASH */
    public static final String _ATT_INCLUDESLASH = "IncludeSlashPolicy";

    /** Field IncludeSlash           */
    public static final boolean IncludeSlash = true;

    /** Field ExcludeSlash           */
    public static final boolean ExcludeSlash = false;

    /**
     * Constructor XPathFilterCHGPContainer
     *
     * <p>
     *  构造函数XPathFilterCHGPContainer
     * 
     */
    private XPathFilterCHGPContainer() {
        // no instantiation
    }

    /**
     * Constructor XPathFilterCHGPContainer
     *
     * <p>
     *  构造函数XPathFilterCHGPContainer
     * 
     * 
     * @param doc
     * @param includeSlashPolicy
     * @param includeButSearch
     * @param excludeButSearch
     * @param exclude
     */
    private XPathFilterCHGPContainer(
        Document doc, boolean includeSlashPolicy, String includeButSearch,
        String excludeButSearch, String exclude
    ) {
        super(doc);

        if (includeSlashPolicy) {
            this.constructionElement.setAttributeNS(
                null, XPathFilterCHGPContainer._ATT_INCLUDESLASH, "true"
            );
        } else {
            this.constructionElement.setAttributeNS(
                null, XPathFilterCHGPContainer._ATT_INCLUDESLASH, "false"
            );
        }

        if ((includeButSearch != null) && (includeButSearch.trim().length() > 0)) {
            Element includeButSearchElem =
                ElementProxy.createElementForFamily(
                    doc, this.getBaseNamespace(), XPathFilterCHGPContainer._TAG_INCLUDE_BUT_SEARCH
                );

            includeButSearchElem.appendChild(
                this.doc.createTextNode(indentXPathText(includeButSearch))
            );
            XMLUtils.addReturnToElement(this.constructionElement);
            this.constructionElement.appendChild(includeButSearchElem);
        }

        if ((excludeButSearch != null) && (excludeButSearch.trim().length() > 0)) {
            Element excludeButSearchElem =
                ElementProxy.createElementForFamily(
                    doc, this.getBaseNamespace(), XPathFilterCHGPContainer._TAG_EXCLUDE_BUT_SEARCH
                );

            excludeButSearchElem.appendChild(
                this.doc.createTextNode(indentXPathText(excludeButSearch)));

            XMLUtils.addReturnToElement(this.constructionElement);
            this.constructionElement.appendChild(excludeButSearchElem);
        }

        if ((exclude != null) && (exclude.trim().length() > 0)) {
            Element excludeElem =
                ElementProxy.createElementForFamily(
                   doc, this.getBaseNamespace(), XPathFilterCHGPContainer._TAG_EXCLUDE);

            excludeElem.appendChild(this.doc.createTextNode(indentXPathText(exclude)));
            XMLUtils.addReturnToElement(this.constructionElement);
            this.constructionElement.appendChild(excludeElem);
        }

        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method indentXPathText
     *
     * <p>
     *  方法indentXPathText
     * 
     * 
     * @param xp
     * @return the string with enters
     */
    static String indentXPathText(String xp) {
        if ((xp.length() > 2) && (!Character.isWhitespace(xp.charAt(0)))) {
            return "\n" + xp + "\n";
        }
        return xp;
    }

    /**
     * Constructor XPathFilterCHGPContainer
     *
     * <p>
     *  构造函数XPathFilterCHGPContainer
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    private XPathFilterCHGPContainer(Element element, String BaseURI)
        throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Creates a new XPathFilterCHGPContainer; needed for generation.
     *
     * <p>
     *  创建一个新的XPathFilterCHGPContainer;需要生成。
     * 
     * 
     * @param doc
     * @param includeSlashPolicy
     * @param includeButSearch
     * @param excludeButSearch
     * @param exclude
     * @return the created object
     */
    public static XPathFilterCHGPContainer getInstance(
        Document doc, boolean includeSlashPolicy, String includeButSearch,
        String excludeButSearch, String exclude
    ) {
        return new XPathFilterCHGPContainer(
            doc, includeSlashPolicy, includeButSearch, excludeButSearch, exclude);
    }

    /**
     * Creates a XPathFilterCHGPContainer from an existing Element; needed for verification.
     *
     * <p>
     *  从现有元素创建XPathFilterCHGPContainer;需要验证。
     * 
     * 
     * @param element
     * @param BaseURI
     *
     * @throws XMLSecurityException
     * @return the created object.
     */
    public static XPathFilterCHGPContainer getInstance(
        Element element, String BaseURI
    ) throws XMLSecurityException {
        return new XPathFilterCHGPContainer(element, BaseURI);
    }

    /**
     * Method getXStr
     *
     * <p>
     *  方法getXStr
     * 
     * 
     * @param type
     * @return The Xstr
     */
    private String getXStr(String type) {
        if (this.length(this.getBaseNamespace(), type) != 1) {
            return "";
        }

        Element xElem =
            XMLUtils.selectNode(
                this.constructionElement.getFirstChild(), this.getBaseNamespace(), type, 0
            );

        return XMLUtils.getFullTextChildrenFromElement(xElem);
    }

    /**
     * Method getIncludeButSearch
     *
     * <p>
     *  方法getIncludeButSearch
     * 
     * 
     * @return the string
     */
    public String getIncludeButSearch() {
        return this.getXStr(XPathFilterCHGPContainer._TAG_INCLUDE_BUT_SEARCH);
    }

    /**
     * Method getExcludeButSearch
     *
     * <p>
     *  方法getExcludeButSearch
     * 
     * 
     * @return the string
     */
    public String getExcludeButSearch() {
        return this.getXStr(XPathFilterCHGPContainer._TAG_EXCLUDE_BUT_SEARCH);
    }

    /**
     * Method getExclude
     *
     * <p>
     *  方法getExclude
     * 
     * 
     * @return the string
     */
    public String getExclude() {
        return this.getXStr(XPathFilterCHGPContainer._TAG_EXCLUDE);
    }

    /**
     * Method getIncludeSlashPolicy
     *
     * <p>
     *  方法getIncludeSlashPolicy
     * 
     * 
     * @return the string
     */
    public boolean getIncludeSlashPolicy() {
        return this.constructionElement.getAttributeNS(
            null, XPathFilterCHGPContainer._ATT_INCLUDESLASH).equals("true");
    }

    /**
     * Returns the first Text node which contains information from the XPath
     * Filter String. We must use this stupid hook to enable the here() function
     * to work.
     *
     * $todo$ I dunno whether this crashes: <XPath> he<!-- comment -->re()/ds:Signature[1]</XPath>
     * <p>
     * 返回包含来自XPath过滤器字符串信息的第一个Text节点。我们必须使用这个愚蠢的钩子来启用here()函数。
     * 
     *  $ todo $ I dunno是否崩溃：<XPath> he <！ -  comment  - > re()/ ds：Signature [1] </XPath>
     * 
     * 
     * @param type
     * @return the first Text node which contains information from the XPath 2 Filter String
     */
    private Node getHereContextNode(String type) {

        if (this.length(this.getBaseNamespace(), type) != 1) {
            return null;
        }

        return XMLUtils.selectNodeText(
            this.constructionElement.getFirstChild(), this.getBaseNamespace(), type, 0
        );
    }

    /**
     * Method getHereContextNodeIncludeButSearch
     *
     * <p>
     *  方法getHereContextNodeIncludeButSearch
     * 
     * 
     * @return the string
     */
    public Node getHereContextNodeIncludeButSearch() {
        return this.getHereContextNode(XPathFilterCHGPContainer._TAG_INCLUDE_BUT_SEARCH);
    }

    /**
     * Method getHereContextNodeExcludeButSearch
     *
     * <p>
     *  方法getHereContextNodeExcludeButSearch
     * 
     * 
     * @return the string
     */
    public Node getHereContextNodeExcludeButSearch() {
        return this.getHereContextNode(XPathFilterCHGPContainer._TAG_EXCLUDE_BUT_SEARCH);
    }

    /**
     * Method getHereContextNodeExclude
     *
     * <p>
     *  方法getHereContextNodeExclude
     * 
     * 
     * @return the string
     */
    public Node getHereContextNodeExclude() {
        return this.getHereContextNode(XPathFilterCHGPContainer._TAG_EXCLUDE);
    }

    /**
     * Method getBaseLocalName
     *
     * @inheritDoc
     * <p>
     *  方法getBaseLocalName
     * 
     *  @inheritDoc
     * 
     */
    public final String getBaseLocalName() {
        return XPathFilterCHGPContainer._TAG_XPATHCHGP;
    }

    /**
     * Method getBaseNamespace
     *
     * @inheritDoc
     * <p>
     *  方法getBaseNamespace
     * 
     */
    public final String getBaseNamespace() {
        return TRANSFORM_XPATHFILTERCHGP;
    }
}
