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
package com.sun.org.apache.xml.internal.security.keys.content.x509;

import java.security.MessageDigest;
import java.security.cert.X509Certificate;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.Signature11ElementProxy;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Provides content model support for the <code>dsig11:X509Digest</code> element.
 *
 * <p>
 *  为<code> dsig11：X509Digest </code>元素提供内容模型支持。
 * 
 * 
 * @author Brent Putman (putmanb@georgetown.edu)
 */
public class XMLX509Digest extends Signature11ElementProxy implements XMLX509DataContent {

    /**
     * Constructor XMLX509Digest
     *
     * <p>
     *  构造函数XMLX509Digest
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public XMLX509Digest(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Constructor XMLX509Digest
     *
     * <p>
     *  构造函数XMLX509Digest
     * 
     * 
     * @param doc
     * @param digestBytes
     * @param algorithmURI
     */
    public XMLX509Digest(Document doc, byte[] digestBytes, String algorithmURI) {
        super(doc);
        this.addBase64Text(digestBytes);
        this.constructionElement.setAttributeNS(null, Constants._ATT_ALGORITHM, algorithmURI);
    }

    /**
     * Constructor XMLX509Digest
     *
     * <p>
     *  构造函数XMLX509Digest
     * 
     * 
     * @param doc
     * @param x509certificate
     * @param algorithmURI
     * @throws XMLSecurityException
     */
    public XMLX509Digest(Document doc, X509Certificate x509certificate, String algorithmURI) throws XMLSecurityException {
        super(doc);
        this.addBase64Text(getDigestBytesFromCert(x509certificate, algorithmURI));
        this.constructionElement.setAttributeNS(null, Constants._ATT_ALGORITHM, algorithmURI);
    }

    /**
     * Method getAlgorithmAttr
     *
     * <p>
     *  方法getAlgorithmAttr
     * 
     * 
     * @return the Algorithm attribute
     */
    public Attr getAlgorithmAttr() {
        return this.constructionElement.getAttributeNodeNS(null, Constants._ATT_ALGORITHM);
    }

    /**
     * Method getAlgorithm
     *
     * <p>
     *  方法getAlgorithm
     * 
     * 
     * @return Algorithm string
     */
    public String getAlgorithm() {
        return this.getAlgorithmAttr().getNodeValue();
    }

    /**
     * Method getDigestBytes
     *
     * <p>
     *  方法getDigestBytes
     * 
     * 
     * @return the digestbytes
     * @throws XMLSecurityException
     */
    public byte[] getDigestBytes() throws XMLSecurityException {
        return this.getBytesFromTextChild();
    }

    /**
     * Method getDigestBytesFromCert
     *
     * <p>
     *  方法getDigestBytesFromCert
     * 
     * @param cert
     * @param algorithmURI
     * @return digest bytes from the given certificate
     *
     * @throws XMLSecurityException
     */
    public static byte[] getDigestBytesFromCert(X509Certificate cert, String algorithmURI) throws XMLSecurityException {
        String jcaDigestAlgorithm = JCEMapper.translateURItoJCEID(algorithmURI);
        if (jcaDigestAlgorithm == null) {
            Object exArgs[] = { algorithmURI };
            throw new XMLSecurityException("XMLX509Digest.UnknownDigestAlgorithm", exArgs);
        }

        try {
            MessageDigest md = MessageDigest.getInstance(jcaDigestAlgorithm);
            return md.digest(cert.getEncoded());
        } catch (Exception e) {
            Object exArgs[] = { jcaDigestAlgorithm };
            throw new XMLSecurityException("XMLX509Digest.FailedDigest", exArgs);
        }

    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_X509DIGEST;
    }
}
