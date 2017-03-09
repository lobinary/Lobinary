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
package com.sun.org.apache.xml.internal.security.keys.content;

import java.security.PublicKey;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The KeyValue element contains a single public key that may be useful in
 * validating the signature. Structured formats for defining DSA (REQUIRED)
 * and RSA (RECOMMENDED) public keys are defined in Signature Algorithms
 * (section 6.4). The KeyValue element may include externally defined public
 * keys values represented as PCDATA or element types from an external
 * namespace.
 *
 * <p>
 *  KeyValue元素包含可用于验证签名的单个公钥。用于定义DSA(REQUIRED)和RSA(RECOMMENDED)公钥的结构化格式在签名算法(第6.4节)中定义。
 *  KeyValue元素可以包括外部定义的公钥值,其表示为来自外部命名空间的PCDATA或元素类型。
 * 
 * 
 * @author $Author: coheigea $
 */
public class KeyValue extends SignatureElementProxy implements KeyInfoContent {

    /**
     * Constructor KeyValue
     *
     * <p>
     *  构造函数KeyValue
     * 
     * 
     * @param doc
     * @param dsaKeyValue
     */
    public KeyValue(Document doc, DSAKeyValue dsaKeyValue) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
        this.constructionElement.appendChild(dsaKeyValue.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructor KeyValue
     *
     * <p>
     *  构造函数KeyValue
     * 
     * 
     * @param doc
     * @param rsaKeyValue
     */
    public KeyValue(Document doc, RSAKeyValue rsaKeyValue) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
        this.constructionElement.appendChild(rsaKeyValue.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructor KeyValue
     *
     * <p>
     *  构造函数KeyValue
     * 
     * 
     * @param doc
     * @param unknownKeyValue
     */
    public KeyValue(Document doc, Element unknownKeyValue) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
        this.constructionElement.appendChild(unknownKeyValue);
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructor KeyValue
     *
     * <p>
     *  构造函数KeyValue
     * 
     * 
     * @param doc
     * @param pk
     */
    public KeyValue(Document doc, PublicKey pk) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);

        if (pk instanceof java.security.interfaces.DSAPublicKey) {
            DSAKeyValue dsa = new DSAKeyValue(this.doc, pk);

            this.constructionElement.appendChild(dsa.getElement());
            XMLUtils.addReturnToElement(this.constructionElement);
        } else if (pk instanceof java.security.interfaces.RSAPublicKey) {
            RSAKeyValue rsa = new RSAKeyValue(this.doc, pk);

            this.constructionElement.appendChild(rsa.getElement());
            XMLUtils.addReturnToElement(this.constructionElement);
        }
    }

    /**
     * Constructor KeyValue
     *
     * <p>
     *  构造函数KeyValue
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public KeyValue(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Method getPublicKey
     *
     * <p>
     *  方法getPublicKey
     * 
     * @return the public key
     * @throws XMLSecurityException
     */
    public PublicKey getPublicKey() throws XMLSecurityException {
        Element rsa =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_RSAKEYVALUE, 0);

        if (rsa != null) {
            RSAKeyValue kv = new RSAKeyValue(rsa, this.baseURI);
            return kv.getPublicKey();
        }

        Element dsa =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_DSAKEYVALUE, 0);

        if (dsa != null) {
            DSAKeyValue kv = new DSAKeyValue(dsa, this.baseURI);
            return kv.getPublicKey();
        }

        return null;
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_KEYVALUE;
    }
}
