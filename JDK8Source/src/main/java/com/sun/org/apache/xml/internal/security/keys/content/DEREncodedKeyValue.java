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

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.Signature11ElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides content model support for the <code>dsig11:DEREncodedKeyvalue</code> element.
 *
 * <p>
 *  为<code> dsig11：DEREncodedKeyvalue </code>元素提供内容模型支持。
 * 
 * 
 * @author Brent Putman (putmanb@georgetown.edu)
 */
public class DEREncodedKeyValue extends Signature11ElementProxy implements KeyInfoContent {

    /** JCA algorithm key types supported by this implementation. */
    private static final String supportedKeyTypes[] = { "RSA", "DSA", "EC"};

    /**
     * Constructor DEREncodedKeyValue
     *
     * <p>
     *  构造函数DEREncodedKeyValue
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public DEREncodedKeyValue(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Constructor DEREncodedKeyValue
     *
     * <p>
     *  构造函数DEREncodedKeyValue
     * 
     * 
     * @param doc
     * @param publicKey
     * @throws XMLSecurityException
     */
    public DEREncodedKeyValue(Document doc, PublicKey publicKey) throws XMLSecurityException {
        super(doc);

        this.addBase64Text(getEncodedDER(publicKey));
    }

    /**
     * Constructor DEREncodedKeyValue
     *
     * <p>
     *  构造函数DEREncodedKeyValue
     * 
     * 
     * @param doc
     * @param base64EncodedKey
     */
    public DEREncodedKeyValue(Document doc, byte[] encodedKey) {
        super(doc);

        this.addBase64Text(encodedKey);
    }

    /**
     * Sets the <code>Id</code> attribute
     *
     * <p>
     *  设置<code> Id </code>属性
     * 
     * 
     * @param Id ID
     */
    public void setId(String id) {
        if (id != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_ID, id);
            this.constructionElement.setIdAttributeNS(null, Constants._ATT_ID, true);
        } else {
            this.constructionElement.removeAttributeNS(null, Constants._ATT_ID);
        }
    }

    /**
     * Returns the <code>Id</code> attribute
     *
     * <p>
     *  返回<code> Id </code>属性
     * 
     * 
     * @return the <code>Id</code> attribute
     */
    public String getId() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ID);
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_DERENCODEDKEYVALUE;
    }

    /**
     * Method getPublicKey
     *
     * <p>
     *  方法getPublicKey
     * 
     * 
     * @return the public key
     * @throws XMLSecurityException
     */
    public PublicKey getPublicKey() throws XMLSecurityException {
        byte[] encodedKey = getBytesFromTextChild();

        // Iterate over the supported key types until one produces a public key.
        for (String keyType : supportedKeyTypes) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(keyType);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
                PublicKey publicKey = keyFactory.generatePublic(keySpec);
                if (publicKey != null) {
                    return publicKey;
                }
            } catch (NoSuchAlgorithmException e) {
                // Do nothing, try the next type
            } catch (InvalidKeySpecException e) {
                // Do nothing, try the next type
            }
        }
        throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedEncodedKey");
    }

    /**
     * Method getEncodedDER
     *
     * <p>
     *  方法getEncodedDER
     * 
     * @return the public key
     * @throws XMLSecurityException
     */
    protected byte[] getEncodedDER(PublicKey publicKey) throws XMLSecurityException {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(publicKey.getAlgorithm());
            X509EncodedKeySpec keySpec = keyFactory.getKeySpec(publicKey, X509EncodedKeySpec.class);
            return keySpec.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            Object exArgs[] = { publicKey.getAlgorithm(), publicKey.getFormat(), publicKey.getClass().getName() };
            throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedPublicKey", exArgs, e);
        } catch (InvalidKeySpecException e) {
            Object exArgs[] = { publicKey.getAlgorithm(), publicKey.getFormat(), publicKey.getClass().getName() };
            throw new XMLSecurityException("DEREncodedKeyValue.UnsupportedPublicKey", exArgs, e);
        }
    }

}
