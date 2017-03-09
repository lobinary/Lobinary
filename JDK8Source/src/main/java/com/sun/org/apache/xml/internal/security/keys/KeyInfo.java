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
package com.sun.org.apache.xml.internal.security.keys;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.crypto.SecretKey;

import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.keys.content.DEREncodedKeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.KeyInfoReference;
import com.sun.org.apache.xml.internal.security.keys.content.KeyName;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.MgmtData;
import com.sun.org.apache.xml.internal.security.keys.content.PGPData;
import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;
import com.sun.org.apache.xml.internal.security.keys.content.SPKIData;
import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.DSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.content.keyvalues.RSAKeyValue;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverException;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolverSpi;
import com.sun.org.apache.xml.internal.security.keys.storage.StorageResolver;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.EncryptionConstants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class stand for KeyInfo Element that may contain keys, names,
 * certificates and other public key management information,
 * such as in-band key distribution or key agreement data.
 * <BR />
 * KeyInfo Element has two basic functions:
 * One is KeyResolve for getting the public key in signature validation processing.
 * the other one is toElement for getting the element in signature generation processing.
 * <BR />
 * The <CODE>lengthXXX()</CODE> methods provide access to the internal Key
 * objects:
 * <UL>
 * <LI>If the <CODE>KeyInfo</CODE> was constructed from an Element
 * (Signature verification), the <CODE>lengthXXX()</CODE> methods searches
 * for child elements of <CODE>ds:KeyInfo</CODE> for known types. </LI>
 * <LI>If the <CODE>KeyInfo</CODE> was constructed from scratch (during
 * Signature generation), the <CODE>lengthXXX()</CODE> methods return the number
 * of <CODE>XXXs</CODE> objects already passed to the KeyInfo</LI>
 * </UL>
 * <BR />
 * The <CODE>addXXX()</CODE> methods are used for adding Objects of the
 * appropriate type to the <CODE>KeyInfo</CODE>. This is used during signature
 * generation.
 * <BR />
 * The <CODE>itemXXX(int i)</CODE> methods return the i'th object of the
 * corresponding type.
 * <BR />
 * The <CODE>containsXXX()</CODE> methods return <I>whether</I> the KeyInfo
 * contains the corresponding type.
 *
 * <p>
 *  该类代表KeyInfo Element,它可以包含密钥,名称,证书和其他公钥管理信息,例如带内密钥分发或密钥协议数据。
 * <BR />
 *  KeyInfo Element有两个基本功能：一个是KeyResolve,用于在签名验证处理中获取公钥。另一个是在签名生成处理中获得元素的Element。
 * <BR />
 *  <CODE> lengthXXX()</CODE>方法提供对内部Key对象的访问：
 * <UL>
 * <LI>如果<CODE> KeyInfo </CODE>是从元素(签名验证)构造的,则<CODE> lengthXXX()</CODE>方法搜索<CODE> ds：KeyInfo </CODE>为已知类
 * 型。
 *  </LI> <LI>如果<CODE> KeyInfo </CODE>是从头开始构建的(在签名生成期间),<CODE> lengthXXX()</CODE>方法返回<CODE> XXXs </CODE >
 * 已经传递给KeyInfo的对象</LI>。
 * </UL>
 * <BR />
 *  <CODE> addXXX()</CODE>方法用于将适当类型的对象添加到<CODE> KeyInfo </CODE>中。这在签名生成期间使用。
 * <BR />
 *  <CODE> itemXXX(int i)</CODE>方法返回相应类型的第i个对象。
 * <BR />
 *  <CODE> containsXXX()</CODE>方法返回<I>是否</I> KeyInfo包含相应的类型。
 * 
 */
public class KeyInfo extends SignatureElementProxy {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(KeyInfo.class.getName());

    // We need at least one StorageResolver otherwise
    // the KeyResolvers would not be called.
    // The default StorageResolver is null.

    private List<X509Data> x509Datas = null;
    private List<EncryptedKey> encryptedKeys = null;

    private static final List<StorageResolver> nullList;
    static {
        List<StorageResolver> list = new ArrayList<StorageResolver>(1);
        list.add(null);
        nullList = java.util.Collections.unmodifiableList(list);
    }

    /** Field storageResolvers */
    private List<StorageResolver> storageResolvers = nullList;

    /**
     * Stores the individual (per-KeyInfo) {@link KeyResolverSpi}s
     * <p>
     *  存储个人(每个KeyInfo){@link KeyResolverSpi}
     * 
     */
    private List<KeyResolverSpi> internalKeyResolvers = new ArrayList<KeyResolverSpi>();

    private boolean secureValidation;

    /**
     * Constructor KeyInfo
     * <p>
     *  构造函数KeyInfo
     * 
     * 
     * @param doc
     */
    public KeyInfo(Document doc) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructor KeyInfo
     *
     * <p>
     *  构造函数KeyInfo
     * 
     * 
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public KeyInfo(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);

        Attr attr = element.getAttributeNodeNS(null, "Id");
        if (attr != null) {
            element.setIdAttributeNode(attr, true);
        }
    }

    /**
     * Set whether secure processing is enabled or not. The default is false.
     * <p>
     *  设置是否启用安全处理。默认值为false。
     * 
     */
    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
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

    /**
     * Method addKeyName
     *
     * <p>
     *  方法addKeyName
     * 
     * 
     * @param keynameString
     */
    public void addKeyName(String keynameString) {
        this.add(new KeyName(this.doc, keynameString));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param keyname
     */
    public void add(KeyName keyname) {
        this.constructionElement.appendChild(keyname.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addKeyValue
     *
     * <p>
     *  方法addKeyValue
     * 
     * 
     * @param pk
     */
    public void addKeyValue(PublicKey pk) {
        this.add(new KeyValue(this.doc, pk));
    }

    /**
     * Method addKeyValue
     *
     * <p>
     *  方法addKeyValue
     * 
     * 
     * @param unknownKeyValueElement
     */
    public void addKeyValue(Element unknownKeyValueElement) {
        this.add(new KeyValue(this.doc, unknownKeyValueElement));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param dsakeyvalue
     */
    public void add(DSAKeyValue dsakeyvalue) {
        this.add(new KeyValue(this.doc, dsakeyvalue));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param rsakeyvalue
     */
    public void add(RSAKeyValue rsakeyvalue) {
        this.add(new KeyValue(this.doc, rsakeyvalue));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param pk
     */
    public void add(PublicKey pk) {
        this.add(new KeyValue(this.doc, pk));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param keyvalue
     */
    public void add(KeyValue keyvalue) {
        this.constructionElement.appendChild(keyvalue.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addMgmtData
     *
     * <p>
     *  方法addMgmtData
     * 
     * 
     * @param mgmtdata
     */
    public void addMgmtData(String mgmtdata) {
        this.add(new MgmtData(this.doc, mgmtdata));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param mgmtdata
     */
    public void add(MgmtData mgmtdata) {
        this.constructionElement.appendChild(mgmtdata.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addPGPData
     *
     * <p>
     *  方法addPGPData
     * 
     * 
     * @param pgpdata
     */
    public void add(PGPData pgpdata) {
        this.constructionElement.appendChild(pgpdata.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addRetrievalMethod
     *
     * <p>
     *  方法addRetrievalMethod
     * 
     * 
     * @param uri
     * @param transforms
     * @param Type
     */
    public void addRetrievalMethod(String uri, Transforms transforms, String Type) {
        this.add(new RetrievalMethod(this.doc, uri, transforms, Type));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param retrievalmethod
     */
    public void add(RetrievalMethod retrievalmethod) {
        this.constructionElement.appendChild(retrievalmethod.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param spkidata
     */
    public void add(SPKIData spkidata) {
        this.constructionElement.appendChild(spkidata.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addX509Data
     *
     * <p>
     *  方法addX509Data
     * 
     * 
     * @param x509data
     */
    public void add(X509Data x509data) {
        if (x509Datas == null) {
            x509Datas = new ArrayList<X509Data>();
        }
        x509Datas.add(x509data);
        this.constructionElement.appendChild(x509data.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addEncryptedKey
     *
     * <p>
     *  方法addEncryptedKey
     * 
     * 
     * @param encryptedKey
     * @throws XMLEncryptionException
     */

    public void add(EncryptedKey encryptedKey) throws XMLEncryptionException {
        if (encryptedKeys == null) {
            encryptedKeys = new ArrayList<EncryptedKey>();
        }
        encryptedKeys.add(encryptedKey);
        XMLCipher cipher = XMLCipher.getInstance();
        this.constructionElement.appendChild(cipher.martial(encryptedKey));
    }

    /**
     * Method addDEREncodedKeyValue
     *
     * <p>
     *  方法addDEREncodedKeyValue
     * 
     * 
     * @param pk
     * @throws XMLSecurityException
     */
    public void addDEREncodedKeyValue(PublicKey pk) throws XMLSecurityException {
        this.add(new DEREncodedKeyValue(this.doc, pk));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param derEncodedKeyValue
     */
    public void add(DEREncodedKeyValue derEncodedKeyValue) {
        this.constructionElement.appendChild(derEncodedKeyValue.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Method addKeyInfoReference
     *
     * <p>
     *  方法addKeyInfoReference
     * 
     * 
     * @param URI
     * @throws XMLSecurityException
     */
    public void addKeyInfoReference(String URI) throws XMLSecurityException {
        this.add(new KeyInfoReference(this.doc, URI));
    }

    /**
     * Method add
     *
     * <p>
     *  方法添加
     * 
     * 
     * @param keyInfoReference
     */
    public void add(KeyInfoReference keyInfoReference) {
        this.constructionElement.appendChild(keyInfoReference.getElement());
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
     * Method lengthKeyName
     *
     * <p>
     *  方法lengthKeyName
     * 
     * 
     * @return the number of the KeyName tags
     */
    public int lengthKeyName() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_KEYNAME);
    }

    /**
     * Method lengthKeyValue
     *
     * <p>
     *  方法lengthKeyValue
     * 
     * 
     *@return the number of the KeyValue tags
     */
    public int lengthKeyValue() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_KEYVALUE);
    }

    /**
     * Method lengthMgmtData
     *
     * <p>
     *  方法lengthMgmtData
     * 
     * 
     *@return the number of the MgmtData tags
     */
    public int lengthMgmtData() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_MGMTDATA);
    }

    /**
     * Method lengthPGPData
     *
     * <p>
     *  方法lengthPGPData
     * 
     * 
     *@return the number of the PGPDat. tags
     */
    public int lengthPGPData() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_PGPDATA);
    }

    /**
     * Method lengthRetrievalMethod
     *
     * <p>
     * 方法lengthRetrievalMethod
     * 
     * 
     *@return the number of the RetrievalMethod tags
     */
    public int lengthRetrievalMethod() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_RETRIEVALMETHOD);
    }

    /**
     * Method lengthSPKIData
     *
     * <p>
     *  方法lengthSPKIData
     * 
     * 
     *@return the number of the SPKIData tags
     */
    public int lengthSPKIData() {
        return this.length(Constants.SignatureSpecNS, Constants._TAG_SPKIDATA);
    }

    /**
     * Method lengthX509Data
     *
     * <p>
     *  方法lengthX509Data
     * 
     * 
     *@return the number of the X509Data tags
     */
    public int lengthX509Data() {
        if (x509Datas != null) {
            return x509Datas.size();
        }
        return this.length(Constants.SignatureSpecNS, Constants._TAG_X509DATA);
    }

    /**
     * Method lengthDEREncodedKeyValue
     *
     * <p>
     *  方法lengthDEREncodedKeyValue
     * 
     * 
     *@return the number of the DEREncodedKeyValue tags
     */
    public int lengthDEREncodedKeyValue() {
        return this.length(Constants.SignatureSpec11NS, Constants._TAG_DERENCODEDKEYVALUE);
    }

    /**
     * Method lengthKeyInfoReference
     *
     * <p>
     *  方法lengthKeyInfoReference
     * 
     * 
     *@return the number of the KeyInfoReference tags
     */
    public int lengthKeyInfoReference() {
        return this.length(Constants.SignatureSpec11NS, Constants._TAG_KEYINFOREFERENCE);
    }

    /**
     * Method lengthUnknownElement
     * NOTE possibly buggy.
     * <p>
     *  方法lengthUnknownElement注意可能是buggy。
     * 
     * 
     * @return the number of the UnknownElement tags
     */
    public int lengthUnknownElement() {
        int res = 0;
        NodeList nl = this.constructionElement.getChildNodes();

        for (int i = 0; i < nl.getLength(); i++) {
            Node current = nl.item(i);

            /**
             * $todo$ using this method, we don't see unknown Elements
             *  from Signature NS; revisit
             * <p>
             *  $ todo $使用这个方法,我们没有看到来自Signature NS的未知元素;重访
             * 
             */
            if ((current.getNodeType() == Node.ELEMENT_NODE)
                && current.getNamespaceURI().equals(Constants.SignatureSpecNS)) {
                res++;
            }
        }

        return res;
    }

    /**
     * Method itemKeyName
     *
     * <p>
     *  方法itemKeyName
     * 
     * 
     * @param i
     * @return the asked KeyName element, null if the index is too big
     * @throws XMLSecurityException
     */
    public KeyName itemKeyName(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_KEYNAME, i);

        if (e != null) {
            return new KeyName(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemKeyValue
     *
     * <p>
     *  方法itemKeyValue
     * 
     * 
     * @param i
     * @return the asked KeyValue element, null if the index is too big
     * @throws XMLSecurityException
     */
    public KeyValue itemKeyValue(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_KEYVALUE, i);

        if (e != null) {
            return new KeyValue(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemMgmtData
     *
     * <p>
     *  方法itemMgmtData
     * 
     * 
     * @param i
     * @return the asked MgmtData element, null if the index is too big
     * @throws XMLSecurityException
     */
    public MgmtData itemMgmtData(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_MGMTDATA, i);

        if (e != null) {
            return new MgmtData(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemPGPData
     *
     * <p>
     *  方法itemPGPData
     * 
     * 
     * @param i
     * @return the asked PGPData element, null if the index is too big
     * @throws XMLSecurityException
     */
    public PGPData itemPGPData(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_PGPDATA, i);

        if (e != null) {
            return new PGPData(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemRetrievalMethod
     *
     * <p>
     *  方法itemRetrievalMethod
     * 
     * 
     * @param i
     *@return the asked RetrievalMethod element, null if the index is too big
     * @throws XMLSecurityException
     */
    public RetrievalMethod itemRetrievalMethod(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_RETRIEVALMETHOD, i);

        if (e != null) {
            return new RetrievalMethod(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemSPKIData
     *
     * <p>
     *  方法项SPKIData
     * 
     * 
     * @param i
     * @return the asked SPKIData element, null if the index is too big
     * @throws XMLSecurityException
     */
    public SPKIData itemSPKIData(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_SPKIDATA, i);

        if (e != null) {
            return new SPKIData(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemX509Data
     *
     * <p>
     *  方法项X509Data
     * 
     * 
     * @param i
     * @return the asked X509Data element, null if the index is too big
     * @throws XMLSecurityException
     */
    public X509Data itemX509Data(int i) throws XMLSecurityException {
        if (x509Datas != null) {
            return x509Datas.get(i);
        }
        Element e =
            XMLUtils.selectDsNode(
                this.constructionElement.getFirstChild(), Constants._TAG_X509DATA, i);

        if (e != null) {
            return new X509Data(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemEncryptedKey
     *
     * <p>
     *  方法itemEncryptedKey
     * 
     * 
     * @param i
     * @return the asked EncryptedKey element, null if the index is too big
     * @throws XMLSecurityException
     */
    public EncryptedKey itemEncryptedKey(int i) throws XMLSecurityException {
        if (encryptedKeys != null) {
            return encryptedKeys.get(i);
        }
        Element e =
            XMLUtils.selectXencNode(
                this.constructionElement.getFirstChild(), EncryptionConstants._TAG_ENCRYPTEDKEY, i);

        if (e != null) {
            XMLCipher cipher = XMLCipher.getInstance();
            cipher.init(XMLCipher.UNWRAP_MODE, null);
            return cipher.loadEncryptedKey(e);
        }
        return null;
    }

    /**
     * Method itemDEREncodedKeyValue
     *
     * <p>
     *  方法itemDEREncodedKeyValue
     * 
     * 
     * @param i
     * @return the asked DEREncodedKeyValue element, null if the index is too big
     * @throws XMLSecurityException
     */
    public DEREncodedKeyValue itemDEREncodedKeyValue(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDs11Node(
                this.constructionElement.getFirstChild(), Constants._TAG_DERENCODEDKEYVALUE, i);

        if (e != null) {
            return new DEREncodedKeyValue(e, this.baseURI);
        }
        return null;
    }

    /**
     * Method itemKeyInfoReference
     *
     * <p>
     *  方法itemKeyInfoReference
     * 
     * 
     * @param i
     * @return the asked KeyInfoReference element, null if the index is too big
     * @throws XMLSecurityException
     */
    public KeyInfoReference itemKeyInfoReference(int i) throws XMLSecurityException {
        Element e =
            XMLUtils.selectDs11Node(
                this.constructionElement.getFirstChild(), Constants._TAG_KEYINFOREFERENCE, i);

        if (e != null) {
            return new KeyInfoReference(e, this.baseURI);
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
     * @param i index
     * @return the element number of the unknown elements
     */
    public Element itemUnknownElement(int i) {
        NodeList nl = this.constructionElement.getChildNodes();
        int res = 0;

        for (int j = 0; j < nl.getLength(); j++) {
            Node current = nl.item(j);

            /**
             * $todo$ using this method, we don't see unknown Elements
             *  from Signature NS; revisit
             * <p>
             *  $ todo $使用这个方法,我们没有看到来自Signature NS的未知元素;重访
             * 
             */
            if ((current.getNodeType() == Node.ELEMENT_NODE)
                && current.getNamespaceURI().equals(Constants.SignatureSpecNS)) {
                res++;

                if (res == i) {
                    return (Element) current;
                }
            }
        }

        return null;
    }

    /**
     * Method isEmpty
     *
     * <p>
     *  方法isEmpty
     * 
     * 
     * @return true if the element has no descendants.
     */
    public boolean isEmpty() {
        return this.constructionElement.getFirstChild() == null;
    }

    /**
     * Method containsKeyName
     *
     * <p>
     *  方法containsKeyName
     * 
     * 
     * @return If the KeyInfo contains a KeyName node
     */
    public boolean containsKeyName() {
        return this.lengthKeyName() > 0;
    }

    /**
     * Method containsKeyValue
     *
     * <p>
     *  方法containsKeyValue
     * 
     * 
     * @return If the KeyInfo contains a KeyValue node
     */
    public boolean containsKeyValue() {
        return this.lengthKeyValue() > 0;
    }

    /**
     * Method containsMgmtData
     *
     * <p>
     *  方法containsMgmtData
     * 
     * 
     * @return If the KeyInfo contains a MgmtData node
     */
    public boolean containsMgmtData() {
        return this.lengthMgmtData() > 0;
    }

    /**
     * Method containsPGPData
     *
     * <p>
     *  方法包含PGPData
     * 
     * 
     * @return If the KeyInfo contains a PGPData node
     */
    public boolean containsPGPData() {
        return this.lengthPGPData() > 0;
    }

    /**
     * Method containsRetrievalMethod
     *
     * <p>
     *  方法containsRetrievalMethod
     * 
     * 
     * @return If the KeyInfo contains a RetrievalMethod node
     */
    public boolean containsRetrievalMethod() {
        return this.lengthRetrievalMethod() > 0;
    }

    /**
     * Method containsSPKIData
     *
     * <p>
     *  方法包含SPKIData
     * 
     * 
     * @return If the KeyInfo contains a SPKIData node
     */
    public boolean containsSPKIData() {
        return this.lengthSPKIData() > 0;
    }

    /**
     * Method containsUnknownElement
     *
     * <p>
     *  方法containsUnknownElement
     * 
     * 
     * @return If the KeyInfo contains a UnknownElement node
     */
    public boolean containsUnknownElement() {
        return this.lengthUnknownElement() > 0;
    }

    /**
     * Method containsX509Data
     *
     * <p>
     *  方法containsX509Data
     * 
     * 
     * @return If the KeyInfo contains a X509Data node
     */
    public boolean containsX509Data() {
        return this.lengthX509Data() > 0;
    }

    /**
     * Method containsDEREncodedKeyValue
     *
     * <p>
     *  方法containsDEREncodedKeyValue
     * 
     * 
     * @return If the KeyInfo contains a DEREncodedKeyValue node
     */
    public boolean containsDEREncodedKeyValue() {
        return this.lengthDEREncodedKeyValue() > 0;
    }

    /**
     * Method containsKeyInfoReference
     *
     * <p>
     *  方法containsKeyInfoReference
     * 
     * 
     * @return If the KeyInfo contains a KeyInfoReference node
     */
    public boolean containsKeyInfoReference() {
        return this.lengthKeyInfoReference() > 0;
    }

    /**
     * This method returns the public key.
     *
     * <p>
     *  此方法返回公钥。
     * 
     * 
     * @return If the KeyInfo contains a PublicKey node
     * @throws KeyResolverException
     */
    public PublicKey getPublicKey() throws KeyResolverException {
        PublicKey pk = this.getPublicKeyFromInternalResolvers();

        if (pk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a key using the per-KeyInfo key resolvers");
            }

            return pk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a key using the per-KeyInfo key resolvers");
        }

        pk = this.getPublicKeyFromStaticResolvers();

        if (pk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a key using the system-wide key resolvers");
            }

            return pk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a key using the system-wide key resolvers");
        }

        return null;
    }

    /**
     * Searches the library wide KeyResolvers for public keys
     *
     * <p>
     *  搜索库的宽KeyResolvers的公钥
     * 
     * 
     * @return The public key contained in this Node.
     * @throws KeyResolverException
     */
    PublicKey getPublicKeyFromStaticResolvers() throws KeyResolverException {
        Iterator<KeyResolverSpi> it = KeyResolver.iterator();
        while (it.hasNext()) {
            KeyResolverSpi keyResolver = it.next();
            keyResolver.setSecureValidation(secureValidation);
            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null) {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    for (StorageResolver storage : storageResolvers) {
                        PublicKey pk =
                            keyResolver.engineLookupAndResolvePublicKey(
                                (Element) currentChild, uri, storage
                            );

                        if (pk != null) {
                            return pk;
                        }
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }
        return null;
    }

    /**
     * Searches the per-KeyInfo KeyResolvers for public keys
     *
     * <p>
     *  搜索公钥的per-KeyInfo KeyResolvers
     * 
     * 
     * @return The public key contained in this Node.
     * @throws KeyResolverException
     */
    PublicKey getPublicKeyFromInternalResolvers() throws KeyResolverException {
        for (KeyResolverSpi keyResolver : internalKeyResolvers) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Try " + keyResolver.getClass().getName());
            }
            keyResolver.setSecureValidation(secureValidation);
            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null)      {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    for (StorageResolver storage : storageResolvers) {
                        PublicKey pk =
                            keyResolver.engineLookupAndResolvePublicKey(
                                (Element) currentChild, uri, storage
                            );

                        if (pk != null) {
                            return pk;
                        }
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }

        return null;
    }

    /**
     * Method getX509Certificate
     *
     * <p>
     *  方法getX509Certificate
     * 
     * 
     * @return The certificate contained in this KeyInfo
     * @throws KeyResolverException
     */
    public X509Certificate getX509Certificate() throws KeyResolverException {
        // First search using the individual resolvers from the user
        X509Certificate cert = this.getX509CertificateFromInternalResolvers();

        if (cert != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a X509Certificate using the per-KeyInfo key resolvers");
            }

            return cert;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a X509Certificate using the per-KeyInfo key resolvers");
        }

        // Then use the system-wide Resolvers
        cert = this.getX509CertificateFromStaticResolvers();

        if (cert != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a X509Certificate using the system-wide key resolvers");
            }

            return cert;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a X509Certificate using the system-wide key resolvers");
        }

        return null;
    }

    /**
     * This method uses each System-wide {@link KeyResolver} to search the
     * child elements. Each combination of {@link KeyResolver} and child element
     * is checked against all {@link StorageResolver}s.
     *
     * <p>
     *  此方法使用每个系统范围的{@link KeyResolver}搜索子元素。对所有{@link StorageResolver}检查{@link KeyResolver}和子元素的每个组合。
     * 
     * 
     * @return The certificate contained in this KeyInfo
     * @throws KeyResolverException
     */
    X509Certificate getX509CertificateFromStaticResolvers()
        throws KeyResolverException {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE,
                "Start getX509CertificateFromStaticResolvers() with " + KeyResolver.length()
                + " resolvers"
            );
        }
        String uri = this.getBaseURI();
        Iterator<KeyResolverSpi> it = KeyResolver.iterator();
        while (it.hasNext()) {
            KeyResolverSpi keyResolver = it.next();
            keyResolver.setSecureValidation(secureValidation);
            X509Certificate cert = applyCurrentResolver(uri, keyResolver);
            if (cert != null) {
                return cert;
            }
        }
        return null;
    }

    private X509Certificate applyCurrentResolver(
        String uri, KeyResolverSpi keyResolver
    ) throws KeyResolverException {
        Node currentChild = this.constructionElement.getFirstChild();
        while (currentChild != null)      {
            if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                for (StorageResolver storage : storageResolvers) {
                    X509Certificate cert =
                        keyResolver.engineLookupResolveX509Certificate(
                            (Element) currentChild, uri, storage
                        );

                    if (cert != null) {
                        return cert;
                    }
                }
            }
            currentChild = currentChild.getNextSibling();
        }
        return null;
    }

    /**
     * Method getX509CertificateFromInternalResolvers
     *
     * <p>
     *  方法getX509CertificateFromInternalResolvers
     * 
     * 
     * @return The certificate contained in this KeyInfo
     * @throws KeyResolverException
     */
    X509Certificate getX509CertificateFromInternalResolvers()
        throws KeyResolverException {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE,
                "Start getX509CertificateFromInternalResolvers() with "
                + this.lengthInternalKeyResolver() + " resolvers"
            );
        }
        String uri = this.getBaseURI();
        for (KeyResolverSpi keyResolver : internalKeyResolvers) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Try " + keyResolver.getClass().getName());
            }
            keyResolver.setSecureValidation(secureValidation);
            X509Certificate cert = applyCurrentResolver(uri, keyResolver);
            if (cert != null) {
                return cert;
            }
        }

        return null;
    }

    /**
     * This method returns a secret (symmetric) key. This is for XML Encryption.
     * <p>
     *  此方法返回秘密(对称)密钥。这是用于XML加密。
     * 
     * 
     * @return the secret key contained in this KeyInfo
     * @throws KeyResolverException
     */
    public SecretKey getSecretKey() throws KeyResolverException {
        SecretKey sk = this.getSecretKeyFromInternalResolvers();

        if (sk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a secret key using the per-KeyInfo key resolvers");
            }

            return sk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a secret key using the per-KeyInfo key resolvers");
        }

        sk = this.getSecretKeyFromStaticResolvers();

        if (sk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a secret key using the system-wide key resolvers");
            }

            return sk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a secret key using the system-wide key resolvers");
        }

        return null;
    }

    /**
     * Searches the library wide KeyResolvers for Secret keys
     *
     * <p>
     *  搜索库密钥KeyResolvers的密钥
     * 
     * 
     * @return the secret key contained in this KeyInfo
     * @throws KeyResolverException
     */
    SecretKey getSecretKeyFromStaticResolvers() throws KeyResolverException {
        Iterator<KeyResolverSpi> it = KeyResolver.iterator();
        while (it.hasNext()) {
            KeyResolverSpi keyResolver = it.next();
            keyResolver.setSecureValidation(secureValidation);

            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null)      {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    for (StorageResolver storage : storageResolvers) {
                        SecretKey sk =
                            keyResolver.engineLookupAndResolveSecretKey(
                                (Element) currentChild, uri, storage
                            );

                        if (sk != null) {
                            return sk;
                        }
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }
        return null;
    }

    /**
     * Searches the per-KeyInfo KeyResolvers for secret keys
     *
     * <p>
     * 搜索密钥的per-KeyInfo KeyResolvers
     * 
     * 
     * @return the secret key contained in this KeyInfo
     * @throws KeyResolverException
     */

    SecretKey getSecretKeyFromInternalResolvers() throws KeyResolverException {
        for (KeyResolverSpi keyResolver : internalKeyResolvers) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Try " + keyResolver.getClass().getName());
            }
            keyResolver.setSecureValidation(secureValidation);
            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null)      {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    for (StorageResolver storage : storageResolvers) {
                        SecretKey sk =
                            keyResolver.engineLookupAndResolveSecretKey(
                                (Element) currentChild, uri, storage
                            );

                        if (sk != null) {
                            return sk;
                        }
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }

        return null;
    }

    /**
     * This method returns a private key. This is for Key Transport in XML Encryption.
     * <p>
     *  此方法返回一个私钥。这是XML加密中的密钥传输。
     * 
     * 
     * @return the private key contained in this KeyInfo
     * @throws KeyResolverException
     */
    public PrivateKey getPrivateKey() throws KeyResolverException {
        PrivateKey pk = this.getPrivateKeyFromInternalResolvers();

        if (pk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a private key using the per-KeyInfo key resolvers");
            }
            return pk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a secret key using the per-KeyInfo key resolvers");
        }

        pk = this.getPrivateKeyFromStaticResolvers();
        if (pk != null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "I could find a private key using the system-wide key resolvers");
            }
            return pk;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "I couldn't find a private key using the system-wide key resolvers");
        }

        return null;
    }

    /**
     * Searches the library wide KeyResolvers for Private keys
     *
     * <p>
     *  搜索库宽KeyResolvers的私钥
     * 
     * 
     * @return the private key contained in this KeyInfo
     * @throws KeyResolverException
     */
    PrivateKey getPrivateKeyFromStaticResolvers() throws KeyResolverException {
        Iterator<KeyResolverSpi> it = KeyResolver.iterator();
        while (it.hasNext()) {
            KeyResolverSpi keyResolver = it.next();
            keyResolver.setSecureValidation(secureValidation);

            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null)      {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    // not using StorageResolvers at the moment
                    // since they cannot return private keys
                    PrivateKey pk =
                        keyResolver.engineLookupAndResolvePrivateKey(
                            (Element) currentChild, uri, null
                        );

                    if (pk != null) {
                        return pk;
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }
        return null;
    }

    /**
     * Searches the per-KeyInfo KeyResolvers for private keys
     *
     * <p>
     *  搜索per-KeyInfo KeyResolvers的私钥
     * 
     * 
     * @return the private key contained in this KeyInfo
     * @throws KeyResolverException
     */
    PrivateKey getPrivateKeyFromInternalResolvers() throws KeyResolverException {
        for (KeyResolverSpi keyResolver : internalKeyResolvers) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Try " + keyResolver.getClass().getName());
            }
            keyResolver.setSecureValidation(secureValidation);
            Node currentChild = this.constructionElement.getFirstChild();
            String uri = this.getBaseURI();
            while (currentChild != null) {
                if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
                    // not using StorageResolvers at the moment
                    // since they cannot return private keys
                    PrivateKey pk =
                        keyResolver.engineLookupAndResolvePrivateKey(
                            (Element) currentChild, uri, null
                        );

                    if (pk != null) {
                        return pk;
                    }
                }
                currentChild = currentChild.getNextSibling();
            }
        }

        return null;
    }

    /**
     * This method is used to add a custom {@link KeyResolverSpi} to a KeyInfo
     * object.
     *
     * <p>
     *  此方法用于向KeyInfo对象添加自定义{@link KeyResolverSpi}。
     * 
     * 
     * @param realKeyResolver
     */
    public void registerInternalKeyResolver(KeyResolverSpi realKeyResolver) {
        this.internalKeyResolvers.add(realKeyResolver);
    }

    /**
     * Method lengthInternalKeyResolver
     * <p>
     *  方法lengthInternalKeyResolver
     * 
     * 
     * @return the length of the key
     */
    int lengthInternalKeyResolver() {
        return this.internalKeyResolvers.size();
    }

    /**
     * Method itemInternalKeyResolver
     *
     * <p>
     *  方法itemInternalKeyResolver
     * 
     * 
     * @param i the index
     * @return the KeyResolverSpi for the index.
     */
    KeyResolverSpi itemInternalKeyResolver(int i) {
        return this.internalKeyResolvers.get(i);
    }

    /**
     * Method addStorageResolver
     *
     * <p>
     *  方法addStorageResolver
     * 
     * @param storageResolver
     */
    public void addStorageResolver(StorageResolver storageResolver) {
        if (storageResolvers == nullList) {
            // Replace the default null StorageResolver
            storageResolvers = new ArrayList<StorageResolver>();
        }
        this.storageResolvers.add(storageResolver);
    }


    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_KEYINFO;
    }
}
