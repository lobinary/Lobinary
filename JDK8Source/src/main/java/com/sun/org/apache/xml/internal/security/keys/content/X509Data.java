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

import java.math.BigInteger;
import java.security.cert.X509Certificate;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509CRL;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Certificate;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509Digest;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509IssuerSerial;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SKI;
import com.sun.org.apache.xml.internal.security.keys.content.x509.XMLX509SubjectName;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class X509Data extends SignatureElementProxy implements KeyInfoContent {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(X509Data.class.getName());

    /**
     * Constructor X509Data
     *
     * <p>
     *  构造函数X509Data
     * 
     * 
     * @param doc
     */
    public X509Data(Document doc) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructor X509Data
     *
     * <p>
     *  构造函数X509Data
     * 
     * 
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public X509Data(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);

        Node sibling = this.constructionElement.getFirstChild();
        while (sibling != null) {
            if (sibling.getNodeType() != Node.ELEMENT_NODE) {
                sibling = sibling.getNextSibling();
                continue;
            }
            return;
        }
        /* No Elements found */
        Object exArgs[] = { "Elements", Constants._TAG_X509DATA };
        throw new XMLSecurityException("xml.WrongContent", exArgs);
    }

    /**
     * Method addIssuerSerial
     *
     * <p>
     *  方法addIssuerSerial
     * 
     * 
     * @param X509IssuerName
     * @param X509SerialNumber
     */
    public void addIssuerSerial(String X509IssuerName, BigInteger X509SerialNumber) {
        this.add(new XMLX509IssuerSerial(this.doc, X509IssuerName, X509SerialNumber));
    }

    /**
     * Method addIssuerSerial
     *
     * <p>
     *  方法addIssuerSerial
     * 
     * 
     * @param X509IssuerName
     * @param X509SerialNumber
     */
    public void addIssuerSerial(String X509IssuerName, String X509SerialNumber) {
        this.add(new XMLX509IssuerSerial(this.doc, X509IssuerName, X509SerialNumber));
    }

    /**
     * Method addIssuerSerial
     *
     * <p>
     *  方法addIssuerSerial
     * 
     * 
     * @param X509IssuerName
     * @param X509SerialNumber
     */
    public void addIssuerSerial(String X509IssuerName, int X509SerialNumber) {
        this.add(new XMLX509IssuerSerial(this.doc, X509IssuerName, X509SerialNumber));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param xmlX509IssuerSerial
     */
    public void add(XMLX509IssuerSerial xmlX509IssuerSerial) {

        this.constructionElement.appendChild(xmlX509IssuerSerial.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addSKI
     *
     * <p>
     *  方法addSKI
     * 
     * 
     * @param skiBytes
     */
    public void addSKI(byte[] skiBytes) {
        this.add(new XMLX509SKI(this.doc, skiBytes));
    }

    /**
     * Method addSKI
     *
     * <p>
     *  方法addSKI
     * 
     * 
     * @param x509certificate
     * @throws XMLSecurityException
     */
    public void addSKI(X509Certificate x509certificate)
        throws XMLSecurityException {
        this.add(new XMLX509SKI(this.doc, x509certificate));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param xmlX509SKI
     */
    public void add(XMLX509SKI xmlX509SKI) {
        this.constructionElement.appendChild(xmlX509SKI.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addSubjectName
     *
     * <p>
     *  方法addSubjectName
     * 
     * 
     * @param subjectName
     */
    public void addSubjectName(String subjectName) {
        this.add(new XMLX509SubjectName(this.doc, subjectName));
    }

    /**
     * Method addSubjectName
     *
     * <p>
     *  method addSubjectName
     * 
     * 
     * @param x509certificate
     */
    public void addSubjectName(X509Certificate x509certificate) {
        this.add(new XMLX509SubjectName(this.doc, x509certificate));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param xmlX509SubjectName
     */
    public void add(XMLX509SubjectName xmlX509SubjectName) {
        this.constructionElement.appendChild(xmlX509SubjectName.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addCertificate
     *
     * <p>
     *  方法addCertificate
     * 
     * 
     * @param x509certificate
     * @throws XMLSecurityException
     */
    public void addCertificate(X509Certificate x509certificate)
        throws XMLSecurityException {
        this.add(new XMLX509Certificate(this.doc, x509certificate));
    }

    /**
     * Method addCertificate
     *
     * <p>
     *  方法addCertificate
     * 
     * 
     * @param x509certificateBytes
     */
    public void addCertificate(byte[] x509certificateBytes) {
        this.add(new XMLX509Certificate(this.doc, x509certificateBytes));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param xmlX509Certificate
     */
    public void add(XMLX509Certificate xmlX509Certificate) {
        this.constructionElement.appendChild(xmlX509Certificate.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addCRL
     *
     * <p>
     *  方法addCRL
     * 
     * 
     * @param crlBytes
     */
    public void addCRL(byte[] crlBytes) {
        this.add(new XMLX509CRL(this.doc, crlBytes));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param xmlX509CRL
     */
    public void add(XMLX509CRL xmlX509CRL) {
        this.constructionElement.appendChild(xmlX509CRL.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addDigest
     *
     * <p>
     *  方法addDigest
     * 
     * 
     * @param x509certificate
     * @param algorithmURI
     * @throws XMLSecurityException
     */
    public void addDigest(X509Certificate x509certificate, String algorithmURI)
        throws XMLSecurityException {
        this.add(new XMLX509Digest(this.doc, x509certificate, algorithmURI));
    }

    /**
     * Method addDigest
     *
     * <p>
     *  方法addDigest
     * 
     * 
     * @param x509CertificateDigestByes
     * @param algorithmURI
     */
    public void addDigest(byte[] x509certificateDigestBytes, String algorithmURI) {
        this.add(new XMLX509Digest(this.doc, x509certificateDigestBytes, algorithmURI));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param XMLX509Digest
     */
    public void add(XMLX509Digest xmlX509Digest) {
        this.constructionElement.appendChild(xmlX509Digest.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addUnknownElement
     *
     * <p>
     *  方法addUnknownElement
     * 
     * 
     * @param element
     */
    public void addUnknownElement(Element element) {
        this.constructionElement.appendChild(element);
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method lengthIssuerSerial
     *
     * <p>
     *  方法lengthIssuerSerial
     * 
     * 
     * @return the number of IssuerSerial elements in this X509Data
     */
    public int lengthIssuerSerial() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509ISSUERSERIAL);
    }

    /**
     * Method lengthSKI
     *
     * <p>
     *  方法长度SKI
     * 
     * 
     * @return the number of SKI elements in this X509Data
     */
    public int lengthSKI() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509SKI);
    }

    /**
     * Method lengthSubjectName
     *
     * <p>
     *  方法lengthSubjectName
     * 
     * 
     * @return the number of SubjectName elements in this X509Data
     */
    public int lengthSubjectName() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509SUBJECTNAME);
    }

    /**
     * Method lengthCertificate
     *
     * <p>
     *  方法lengthCertificate
     * 
     * 
     * @return the number of Certificate elements in this X509Data
     */
    public int lengthCertificate() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509CERTIFICATE);
    }

    /**
     * Method lengthCRL
     *
     * <p>
     *  方法lengthCRL
     * 
     * 
     * @return the number of CRL elements in this X509Data
     */
    public int lengthCRL() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509CRL);
    }

    /**
     * Method lengthDigest
     *
     * <p>
     *  方法lengthDigest
     * 
     * 
     * @return the number of X509Digest elements in this X509Data
     */
    public int lengthDigest() {
        return this.length(Constants.SignatureSpec11NS, Constants._TAG_X509DIGEST);
    }

    /**
     * Method lengthUnknownElement
     *
     * <p>
     *  方法lengthUnknownElement
     * 
     * 
     * @return the number of UnknownElement elements in this X509Data
     */
    public int lengthUnknownElement() {
        int result = 0;
        Node n = this.constructionElement.getFirstChild();
        while (n != null){
            if ((n.getNodeType() == Node.ELEMENT_NODE)
                && !n.getNamespaceURI().equals(Constants.SignatureSpecNS)) {
                result++;
            }
            n = n.getNextSibling();
        }

        return result;
    }

    /**
     * Method itemIssuerSerial
     *
     * <p>
     *  方法itemIssuerSerial
     * 
     * 
     * @param i
     * @return the X509IssuerSerial, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509IssuerSerial itemIssuerSerial(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509ISSUERSERIAL, i);

        if (e != null) {
            return new XMLX509IssuerSerial(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemSKI
     *
     * <p>
     *  方法项目SKI
     * 
     * 
     * @param i
     * @return the X509SKI, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509SKI itemSKI(int i) throws XMLSecurityException {

        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509SKI, i);

        if (e != null) {
            return new XMLX509SKI(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemSubjectName
     *
     * <p>
     * 方法itemSubjectName
     * 
     * 
     * @param i
     * @return the X509SubjectName, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509SubjectName itemSubjectName(int i) throws XMLSecurityException {

        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509SUBJECTNAME, i);

        if (e != null) {
            return new XMLX509SubjectName(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemCertificate
     *
     * <p>
     *  方法itemCertificate
     * 
     * 
     * @param i
     * @return the X509Certifacte, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509Certificate itemCertificate(int i) throws XMLSecurityException {

        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509CERTIFICATE, i);

        if (e != null) {
            return new XMLX509Certificate(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemCRL
     *
     * <p>
     *  方法项CRL
     * 
     * 
     * @param i
     * @return the X509CRL, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509CRL itemCRL(int i) throws XMLSecurityException {

        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509CRL, i);

        if (e != null) {
            return new XMLX509CRL(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemDigest
     *
     * <p>
     *  方法项Digest
     * 
     * 
     * @param i
     * @return the X509Digest, null if not present
     * @throws XMLSecurityException
     */
    public XMLX509Digest itemDigest(int i) throws XMLSecurityException {

        Element e =
            XMLUtils.selectDs11Node(
                this.constructionElement.getFirstChild(), Constants._TAG_X509DIGEST, i);

        if (e != null) {
            return new XMLX509Digest(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemUnknownElement
     *
     * <p>
     *  方法itemUnknownElement
     * 
     * 
     * @param i
     * @return the Unknown Element at i
     * TODO implement
     **/
    public Element itemUnknownElement(int i) {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "itemUnknownElement not implemented:" + i);
        }
        return null;
    }

    /**
     * Method containsIssuerSerial
     *
     * <p>
     *  方法包含IssuerSerial
     * 
     * 
     * @return true if this X509Data contains a IssuerSerial
     */
    public boolean containsIssuerSerial() {
        return this.lengthIssuerSerial() > 0;
    }

    /**
     * Method containsSKI
     *
     * <p>
     *  方法包含SKI
     * 
     * 
     * @return true if this X509Data contains a SKI
     */
    public boolean containsSKI() {
        return this.lengthSKI() > 0;
    }

    /**
     * Method containsSubjectName
     *
     * <p>
     *  方法containsSubjectName
     * 
     * 
     * @return true if this X509Data contains a SubjectName
     */
    public boolean containsSubjectName() {
        return this.lengthSubjectName() > 0;
    }

    /**
     * Method containsCertificate
     *
     * <p>
     *  方法containsCertificate
     * 
     * 
     * @return true if this X509Data contains a Certificate
     */
    public boolean containsCertificate() {
        return this.lengthCertificate() > 0;
    }

    /**
     * Method containsDigest
     *
     * <p>
     *  方法containsDigest
     * 
     * 
     * @return true if this X509Data contains an X509Digest
     */
    public boolean containsDigest() {
        return this.lengthDigest() > 0;
    }

    /**
     * Method containsCRL
     *
     * <p>
     *  方法包含CRL
     * 
     * 
     * @return true if this X509Data contains a CRL
     */
    public boolean containsCRL() {
        return this.lengthCRL() > 0;
    }

    /**
     * Method containsUnknownElement
     *
     * <p>
     *  方法containsUnknownElement
     * 
     * @return true if this X509Data contains an UnknownElement
     */
    public boolean containsUnknownElement() {
        return this.lengthUnknownElement() > 0;
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_X509DATA;
    }
}
