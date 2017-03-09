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
package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles <code>&lt;ds:SignatureProperties&gt;</code> elements
 * This Element holds {@link SignatureProperty} that contian additional information items
 * concerning the generation of the signature.
 * for example, data-time stamp, serial number of cryptographic hardware.
 *
 * <p>
 *  句柄<code>&lt; ds：SignatureProperties&gt; </code>元素此元素保存{@link SignatureProperty}关于生成签名的附加信息项。
 * 例如,数据时间戳,密码硬件的序列号。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class SignatureProperties extends SignatureElementProxy {

    /**
     * Constructor SignatureProperties
     *
     * <p>
     *  构造符签名属性
     * 
     * 
     * @param doc
     */
    public SignatureProperties(Document doc) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructs {@link SignatureProperties} from {@link Element}
     * <p>
     *  构造{@link SignatureProperties} {@link Element}
     * 
     * 
     * @param element <code>SignatureProperties</code> element
     * @param BaseURI the URI of the resource where the XML instance was stored
     * @throws XMLSecurityException
     */
    public SignatureProperties(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);

        Attr attr = element.getAttributeNodeNS(null, "Id");
        if (attr != null) {
            element.setIdAttributeNode(attr, true);
        }

        int length = getLength();
        for (int i = 0; i < length; i++) {
            Element propertyElem =
                XMLUtils.selectDsNode(this.constructionElement, Constants._TAG_SIGNATUREPROPERTY, i);
            Attr propertyAttr = propertyElem.getAttributeNodeNS(null, "Id");
            if (propertyAttr != null) {
                propertyElem.setIdAttributeNode(propertyAttr, true);
            }
        }
    }

    /**
     * Return the nonnegative number of added SignatureProperty elements.
     *
     * <p>
     *  返回添加的SignatureProperty元素的非负数。
     * 
     * 
     * @return the number of SignatureProperty elements
     */
    public int getLength() {
        Element[] propertyElems =
            XMLUtils.selectDsNodes(this.constructionElement, Constants._TAG_SIGNATUREPROPERTY);

        return propertyElems.length;
    }

    /**
     * Return the <it>i</it><sup>th</sup> SignatureProperty. Valid <code>i</code>
     * values are 0 to <code>{link@ getSize}-1</code>.
     *
     * <p>
     *  返回<it> i </it> <sup> th </sup> SignatureProperty。
     * 有效的<code> i </code>值为0到<code> {link @ getSize} -1 </code>。
     * 
     * 
     * @param i Index of the requested {@link SignatureProperty}
     * @return the <it>i</it><sup>th</sup> SignatureProperty
     * @throws XMLSignatureException
     */
    public SignatureProperty item(int i) throws XMLSignatureException {
        try {
            Element propertyElem =
                XMLUtils.selectDsNode(this.constructionElement, Constants._TAG_SIGNATUREPROPERTY, i);

            if (propertyElem == null) {
                return null;
            }
            return new SignatureProperty(propertyElem, this.baseURI);
        } catch (XMLSecurityException ex) {
            throw new XMLSignatureException("empty", ex);
        }
    }

    /**
     * Sets the <code>Id</code> attribute
     *
     * <p>
     *  设置<code> Id </code>属性
     * 
     * 
     * @param Id the <code>Id</code> attribute
     */
    public void setId(String Id) {
        if (Id != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_ID, Id);
            this.constructionElement.setIdAttributeNS(null, Constants._ATT_ID, true);
        }
    }

    /**
     * Returns the <code>Id</code> attribute
     *
     * <p>
     * 返回<code> Id </code>属性
     * 
     * 
     * @return the <code>Id</code> attribute
     */
    public String getId() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ID);
    }

    /**
     * Method addSignatureProperty
     *
     * <p>
     *  方法addSignatureProperty
     * 
     * @param sp
     */
    public void addSignatureProperty(SignatureProperty sp) {
        this.constructionElement.appendChild(sp.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_SIGNATUREPROPERTIES;
    }
}
