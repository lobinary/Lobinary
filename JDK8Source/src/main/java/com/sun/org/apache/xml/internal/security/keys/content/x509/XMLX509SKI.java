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

import java.security.cert.X509Certificate;
import java.util.Arrays;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Handles SubjectKeyIdentifier (SKI) for X.509v3.
 *
 * <p>
 *  处理X.509v3的SubjectKeyIdentifier(SKI)。
 * 
 * 
 * @see <A HREF="https://docs.oracle.com/javase/1.5.0/docs/api/java/security/cert/X509Extension.html">
 * Interface X509Extension</A>
 */
public class XMLX509SKI extends SignatureElementProxy implements XMLX509DataContent {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(XMLX509SKI.class.getName());

    /**
     * <CODE>SubjectKeyIdentifier (id-ce-subjectKeyIdentifier) (2.5.29.14)</CODE>:
     * This extension identifies the public key being certified. It enables
     * distinct keys used by the same subject to be differentiated
     * (e.g., as key updating occurs).
     * <BR />
     * A key identifier shall be unique with respect to all key identifiers
     * for the subject with which it is used. This extension is always non-critical.
     * <p>
     *  <CODE> SubjectKeyIdentifier(id-ce-subjectKeyIdentifier)(2.5.29.14)</CODE>：此扩展标识被认证的公钥。
     * 它使得能够区分相同主体使用的不同密钥(例如,当密钥更新发生时)。
     * <BR />
     *  密钥标识符对于其使用的主题的所有密钥标识符应是唯一的。此扩展名始终不是关键的。
     * 
     */
    public static final String SKI_OID = "2.5.29.14";

    /**
     * Constructor X509SKI
     *
     * <p>
     *  构造函数X509SKI
     * 
     * 
     * @param doc
     * @param skiBytes
     */
    public XMLX509SKI(Document doc, byte[] skiBytes) {
        super(doc);
        this.addBase64Text(skiBytes);
    }

    /**
     * Constructor XMLX509SKI
     *
     * <p>
     *  构造函数XMLX509SKI
     * 
     * 
     * @param doc
     * @param x509certificate
     * @throws XMLSecurityException
     */
    public XMLX509SKI(Document doc, X509Certificate x509certificate)
        throws XMLSecurityException {
        super(doc);
        this.addBase64Text(XMLX509SKI.getSKIBytesFromCert(x509certificate));
    }

    /**
     * Constructor XMLX509SKI
     *
     * <p>
     *  构造函数XMLX509SKI
     * 
     * 
     * @param element
     * @param BaseURI
     * @throws XMLSecurityException
     */
    public XMLX509SKI(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     * Method getSKIBytes
     *
     * <p>
     *  方法getSKIBytes
     * 
     * 
     * @return the skibytes
     * @throws XMLSecurityException
     */
    public byte[] getSKIBytes() throws XMLSecurityException {
        return this.getBytesFromTextChild();
    }

    /**
     * Method getSKIBytesFromCert
     *
     * <p>
     *  方法getSKIBytesFromCert
     * 
     * 
     * @param cert
     * @return ski bytes from the given certificate
     *
     * @throws XMLSecurityException
     * @see java.security.cert.X509Extension#getExtensionValue(java.lang.String)
     */
    public static byte[] getSKIBytesFromCert(X509Certificate cert)
        throws XMLSecurityException {

        if (cert.getVersion() < 3) {
            Object exArgs[] = { Integer.valueOf(cert.getVersion()) };
            throw new XMLSecurityException("certificate.noSki.lowVersion", exArgs);
        }

        /*
         * Gets the DER-encoded OCTET string for the extension value
         * (extnValue) identified by the passed-in oid String. The oid
         * string is represented by a set of positive whole numbers
         * separated by periods.
         * <p>
         * 获取由传入的oid字符串标识的扩展值(extnValue)的DER编码的OCTET字符串。 oid字符串由一组由句点分隔的正整数表示。
         * 
         */
        byte[] extensionValue = cert.getExtensionValue(XMLX509SKI.SKI_OID);
        if (extensionValue == null) {
            throw new XMLSecurityException("certificate.noSki.null");
        }

        /**
         * Strip away first four bytes from the extensionValue
         * The first two bytes are the tag and length of the extensionValue
         * OCTET STRING, and the next two bytes are the tag and length of
         * the ski OCTET STRING.
         * <p>
         *  从extensionValue剥离前四个字节前两个字节是extensionValue OCTET STRING的标签和长度,接下来的两个字节是滑块OCTET STRING的标签和长度。
         */
        byte skidValue[] = new byte[extensionValue.length - 4];

        System.arraycopy(extensionValue, 4, skidValue, 0, skidValue.length);

        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "Base64 of SKI is " + Base64.encode(skidValue));
        }

        return skidValue;
    }

    /** @inheritDoc */
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLX509SKI)) {
            return false;
        }

        XMLX509SKI other = (XMLX509SKI) obj;

        try {
            return Arrays.equals(other.getSKIBytes(), this.getSKIBytes());
        } catch (XMLSecurityException ex) {
            return false;
        }
    }

    public int hashCode() {
        int result = 17;
        try {
            byte[] bytes = getSKIBytes();
            for (int i = 0; i < bytes.length; i++) {
                result = 31 * result + bytes[i];
            }
        } catch (XMLSecurityException e) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, e.getMessage(), e);
            }
        }
        return result;

    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_X509SKI;
    }
}
