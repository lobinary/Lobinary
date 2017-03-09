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
import org.w3c.dom.NodeList;

/**
 * Implements the parameters for the <A
 * HREF="http://www.w3.org/TR/xmldsig-filter2/">XPath Filter v2.0</A>.
 *
 * <p>
 *  实施<A HREF="http://www.w3.org/TR/xmldsig-filter2/"> XPath过滤器v2.0 </A>的参数。
 * 
 * 
 * @author $Author: coheigea $
 * @see <A HREF="http://www.w3.org/TR/xmldsig-filter2/">XPath Filter v2.0 (TR)</A>
 */
public class XPath2FilterContainer04 extends ElementProxy implements TransformParam {

    /** Field _ATT_FILTER */
    private static final String _ATT_FILTER = "Filter";

    /** Field _ATT_FILTER_VALUE_INTERSECT */
    private static final String _ATT_FILTER_VALUE_INTERSECT = "intersect";

    /** Field _ATT_FILTER_VALUE_SUBTRACT */
    private static final String _ATT_FILTER_VALUE_SUBTRACT = "subtract";

    /** Field _ATT_FILTER_VALUE_UNION */
    private static final String _ATT_FILTER_VALUE_UNION = "union";

    /** Field _TAG_XPATH2 */
    public static final String _TAG_XPATH2 = "XPath";

    /** Field XPathFiler2NS */
    public static final String XPathFilter2NS =
        "http://www.w3.org/2002/04/xmldsig-filter2";

    /**
     * Constructor XPath2FilterContainer04
     *
     * <p>
     *  构造函数XPath2FilterContainer04
     * 
     */
    private XPath2FilterContainer04() {

        // no instantiation
    }

    /**
     * Constructor XPath2FilterContainer04
     *
     * <p>
     *  构造函数XPath2FilterContainer04
     * 
     * 
     * @param doc
     * @param xpath2filter
     * @param filterType
     */
    private XPath2FilterContainer04(Document doc, String xpath2filter, String filterType) {
        super(doc);

        this.constructionElement.setAttributeNS(
            null, XPath2FilterContainer04._ATT_FILTER, filterType);

        if ((xpath2filter.length() > 2)
            && (!Character.isWhitespace(xpath2filter.charAt(0)))) {
            XMLUtils.addReturnToElement(this.constructionElement);
            this.constructionElement.appendChild(doc.createTextNode(xpath2filter));
            XMLUtils.addReturnToElement(this.constructionElement);
        } else {
            this.constructionElement.appendChild(doc.createTextNode(xpath2filter));
        }
    }

    /**
     * Constructor XPath2FilterContainer04
     *
     * <p>
     *  构造函数XPath2FilterContainer04
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    private XPath2FilterContainer04(Element element, String BaseURI)
        throws XMLSecurityException {

        super(element, BaseURI);

        String filterStr =
            this.constructionElement.getAttributeNS(null, XPath2FilterContainer04._ATT_FILTER);

        if (!filterStr.equals(XPath2FilterContainer04._ATT_FILTER_VALUE_INTERSECT)
            && !filterStr.equals(XPath2FilterContainer04._ATT_FILTER_VALUE_SUBTRACT)
            && !filterStr.equals(XPath2FilterContainer04._ATT_FILTER_VALUE_UNION)) {
            Object exArgs[] = { XPath2FilterContainer04._ATT_FILTER, filterStr,
                                XPath2FilterContainer04._ATT_FILTER_VALUE_INTERSECT
                                + ", "
                                + XPath2FilterContainer04._ATT_FILTER_VALUE_SUBTRACT
                                + " or "
                                + XPath2FilterContainer04._ATT_FILTER_VALUE_UNION };

            throw new XMLSecurityException("attributeValueIllegal", exArgs);
        }
    }

    /**
     * Creates a new XPath2FilterContainer04 with the filter type "intersect".
     *
     * <p>
     *  使用过滤器类型"intersect"创建新的XPath2FilterContainer04。
     * 
     * 
     * @param doc
     * @param xpath2filter
     * @return the instance
     */
    public static XPath2FilterContainer04 newInstanceIntersect(
        Document doc, String xpath2filter
    ) {
        return new XPath2FilterContainer04(
            doc, xpath2filter, XPath2FilterContainer04._ATT_FILTER_VALUE_INTERSECT);
    }

    /**
     * Creates a new XPath2FilterContainer04 with the filter type "subtract".
     *
     * <p>
     *  使用过滤器类型"减"创建新的XPath2FilterContainer04。
     * 
     * 
     * @param doc
     * @param xpath2filter
     * @return the instance
     */
    public static XPath2FilterContainer04 newInstanceSubtract(
        Document doc, String xpath2filter
    ) {
        return new XPath2FilterContainer04(
            doc, xpath2filter, XPath2FilterContainer04._ATT_FILTER_VALUE_SUBTRACT);
    }

    /**
     * Creates a new XPath2FilterContainer04 with the filter type "union".
     *
     * <p>
     *  使用过滤器类型"union"创建新的XPath2FilterContainer04。
     * 
     * 
     * @param doc
     * @param xpath2filter
     * @return the instance
     */
    public static XPath2FilterContainer04 newInstanceUnion(
        Document doc, String xpath2filter
    ) {
        return new XPath2FilterContainer04(
            doc, xpath2filter, XPath2FilterContainer04._ATT_FILTER_VALUE_UNION);
    }

    /**
     * Creates a XPath2FilterContainer04 from an existing Element; needed for verification.
     *
     * <p>
     *  从现有元素创建XPath2FilterContainer04;需要验证。
     * 
     * 
     * @param element
     * @param BaseURI
     * @return the instance
     *
     * @throws XMLSecurityException
     */
    public static XPath2FilterContainer04 newInstance(
        Element element, String BaseURI
    ) throws XMLSecurityException {
        return new XPath2FilterContainer04(element, BaseURI);
    }

    /**
     * Returns <code>true</code> if the <code>Filter</code> attribute has value "intersect".
     *
     * <p>
     *  如果<code> Filter </code>属性的值为"intersect",则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the <code>Filter</code> attribute has value "intersect".
     */
    public boolean isIntersect() {
        return this.constructionElement.getAttributeNS(
            null, XPath2FilterContainer04._ATT_FILTER
        ).equals(XPath2FilterContainer04._ATT_FILTER_VALUE_INTERSECT);
    }

    /**
     * Returns <code>true</code> if the <code>Filter</code> attribute has value "subtract".
     *
     * <p>
     * 如果<code> Filter </code>属性的值为"subtract",则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the <code>Filter</code> attribute has value "subtract".
     */
    public boolean isSubtract() {
        return this.constructionElement.getAttributeNS(
            null, XPath2FilterContainer04._ATT_FILTER
        ).equals(XPath2FilterContainer04._ATT_FILTER_VALUE_SUBTRACT);
    }

    /**
     * Returns <code>true</code> if the <code>Filter</code> attribute has value "union".
     *
     * <p>
     *  如果<code> Filter </code>属性的值为"union",则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the <code>Filter</code> attribute has value "union".
     */
    public boolean isUnion() {
        return this.constructionElement.getAttributeNS(
            null, XPath2FilterContainer04._ATT_FILTER
        ).equals(XPath2FilterContainer04._ATT_FILTER_VALUE_UNION);
    }

    /**
     * Returns the XPath 2 Filter String
     *
     * <p>
     *  返回XPath 2过滤器字符串
     * 
     * 
     * @return the XPath 2 Filter String
     */
    public String getXPathFilterStr() {
        return this.getTextFromTextChild();
    }

    /**
     * Returns the first Text node which contains information from the XPath 2
     * Filter String. We must use this stupid hook to enable the here() function
     * to work.
     *
     * $todo$ I dunno whether this crashes: <XPath> here()<!-- comment -->/ds:Signature[1]</XPath>
     * <p>
     *  返回包含来自XPath 2过滤器字符串信息的第一个Text节点。我们必须使用这个愚蠢的钩子来启用here()函数。
     * 
     * 
     * @return the first Text node which contains information from the XPath 2 Filter String
     */
    public Node getXPathFilterTextNode() {
        NodeList children = this.constructionElement.getChildNodes();
        int length = children.getLength();

        for (int i = 0; i < length; i++) {
            if (children.item(i).getNodeType() == Node.TEXT_NODE) {
                return children.item(i);
            }
        }

        return null;
    }

    /** @inheritDoc */
    public final String getBaseLocalName() {
        return XPath2FilterContainer04._TAG_XPATH2;
    }

    /** @inheritDoc */
    public final String getBaseNamespace() {
        return XPath2FilterContainer04.XPathFilter2NS;
    }
}
