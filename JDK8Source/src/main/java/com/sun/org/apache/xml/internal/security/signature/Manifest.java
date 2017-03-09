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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Handles <code>&lt;ds:Manifest&gt;</code> elements.
 * <p> This element holds the <code>Reference</code> elements</p>
 * <p>
 *  处理<code>&lt; ds：Manifest&gt; </code>元素。 <p>此元素包含<code>参考</code>元素</p>
 * 
 */
public class Manifest extends SignatureElementProxy {

    /**
     * The maximum number of references per Manifest, if secure validation is enabled.
     * <p>
     *  如果启用安全验证,则每个清单的最大引用数。
     * 
     */
    public static final int MAXIMUM_REFERENCE_COUNT = 30;

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(Manifest.class.getName());

    /** Field references */
    private List<Reference> references;
    private Element[] referencesEl;

    /** Field verificationResults[] */
    private boolean verificationResults[] = null;

    /** Field resolverProperties */
    private Map<String, String> resolverProperties = null;

    /** Field perManifestResolvers */
    private List<ResourceResolver> perManifestResolvers = null;

    private boolean secureValidation;

    /**
     * Constructs {@link Manifest}
     *
     * <p>
     *  构造{@link Manifest}
     * 
     * 
     * @param doc the {@link Document} in which <code>XMLsignature</code> is placed
     */
    public Manifest(Document doc) {
        super(doc);

        XMLUtils.addReturnToElement(this.constructionElement);

        this.references = new ArrayList<Reference>();
    }

    /**
     * Constructor Manifest
     *
     * <p>
     *  构造函数清单
     * 
     * 
     * @param element
     * @param baseURI
     * @throws XMLSecurityException
     */
    public Manifest(Element element, String baseURI) throws XMLSecurityException {
        this(element, baseURI, false);

    }
    /**
     * Constructor Manifest
     *
     * <p>
     *  构造函数清单
     * 
     * 
     * @param element
     * @param baseURI
     * @param secureValidation
     * @throws XMLSecurityException
     */
    public Manifest(
        Element element, String baseURI, boolean secureValidation
    ) throws XMLSecurityException {
        super(element, baseURI);

        Attr attr = element.getAttributeNodeNS(null, "Id");
        if (attr != null) {
            element.setIdAttributeNode(attr, true);
        }
        this.secureValidation = secureValidation;

        // check out Reference children
        this.referencesEl =
            XMLUtils.selectDsNodes(
                this.constructionElement.getFirstChild(), Constants._TAG_REFERENCE
            );
        int le = this.referencesEl.length;
        if (le == 0) {
            // At least one Reference must be present. Bad.
            Object exArgs[] = { Constants._TAG_REFERENCE, Constants._TAG_MANIFEST };

            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                                   I18n.translate("xml.WrongContent", exArgs));
        }

        if (secureValidation && le > MAXIMUM_REFERENCE_COUNT) {
            Object exArgs[] = { le, MAXIMUM_REFERENCE_COUNT };

            throw new XMLSecurityException("signature.tooManyReferences", exArgs);
        }

        // create List
        this.references = new ArrayList<Reference>(le);

        for (int i = 0; i < le; i++) {
            Element refElem = referencesEl[i];
            Attr refAttr = refElem.getAttributeNodeNS(null, "Id");
            if (refAttr != null) {
                refElem.setIdAttributeNode(refAttr, true);
            }
            this.references.add(null);
        }
    }

    /**
     * This <code>addDocument</code> method is used to add a new resource to the
     * signed info. A {@link com.sun.org.apache.xml.internal.security.signature.Reference} is built
     * from the supplied values.
     *
     * <p>
     *  此<code> addDocument </code>方法用于向签名信息添加新资源。
     *  {@link com.sun.org.apache.xml.internal.security.signature.Reference}是从提供的值构建的。
     * 
     * 
     * @param baseURI the URI of the resource where the XML instance was stored
     * @param referenceURI <code>URI</code> attribute in <code>Reference</code> for specifying
     * where data is
     * @param transforms com.sun.org.apache.xml.internal.security.signature.Transforms object with an ordered
     * list of transformations to be performed.
     * @param digestURI The digest algorithm URI to be used.
     * @param referenceId
     * @param referenceType
     * @throws XMLSignatureException
     */
    public void addDocument(
        String baseURI, String referenceURI, Transforms transforms,
        String digestURI, String referenceId, String referenceType
    ) throws XMLSignatureException {
        // the this.doc is handed implicitly by the this.getOwnerDocument()
        Reference ref =
            new Reference(this.doc, baseURI, referenceURI, this, transforms, digestURI);

        if (referenceId != null) {
            ref.setId(referenceId);
        }

        if (referenceType != null) {
            ref.setType(referenceType);
        }

        // add Reference object to our cache vector
        this.references.add(ref);

        // add the Element of the Reference object to the Manifest/SignedInfo
        this.constructionElement.appendChild(ref.getElement());
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * The calculation of the DigestValues in the References must be after the
     * References are already added to the document and during the signing
     * process. This ensures that all necessary data is in place.
     *
     * <p>
     * 引用中的DigestValues的计算必须在引用已经添加到文档和签名过程之后。这确保所有必要的数据到位。
     * 
     * 
     * @throws ReferenceNotInitializedException
     * @throws XMLSignatureException
     */
    public void generateDigestValues()
        throws XMLSignatureException, ReferenceNotInitializedException {
        for (int i = 0; i < this.getLength(); i++) {
            // update the cached Reference object, the Element content is automatically updated
            Reference currentRef = this.references.get(i);
            currentRef.generateDigestValue();
        }
    }

    /**
     * Return the nonnegative number of added references.
     *
     * <p>
     *  返回添加引用的非负数。
     * 
     * 
     * @return the number of references
     */
    public int getLength() {
        return this.references.size();
    }

    /**
     * Return the <it>i</it><sup>th</sup> reference. Valid <code>i</code>
     * values are 0 to <code>{link@ getSize}-1</code>.
     *
     * <p>
     *  返回<it> i </it> <sup> th </sup>引用。有效的<code> i </code>值为0到<code> {link @ getSize} -1 </code>。
     * 
     * 
     * @param i Index of the requested {@link Reference}
     * @return the <it>i</it><sup>th</sup> reference
     * @throws XMLSecurityException
     */
    public Reference item(int i) throws XMLSecurityException {
        if (this.references.get(i) == null) {
            // not yet constructed, so _we_ have to
            Reference ref =
                new Reference(referencesEl[i], this.baseURI, this, secureValidation);

            this.references.set(i, ref);
        }

        return this.references.get(i);
    }

    /**
     * Sets the <code>Id</code> attribute
     *
     * <p>
     *  设置<code> Id </code>属性
     * 
     * 
     * @param Id the <code>Id</code> attribute in <code>ds:Manifest</code>
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
     *  返回<code> Id </code>属性
     * 
     * 
     * @return the <code>Id</code> attribute in <code>ds:Manifest</code>
     */
    public String getId() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ID);
    }

    /**
     * Used to do a <A HREF="http://www.w3.org/TR/xmldsig-core/#def-ValidationReference">reference
     * validation</A> of all enclosed references using the {@link Reference#verify} method.
     *
     * <p>This step loops through all {@link Reference}s and does verify the hash
     * values. If one or more verifications fail, the method returns
     * <code>false</code>. If <i>all</i> verifications are successful,
     * it returns <code>true</code>. The results of the individual reference
     * validations are available by using the {@link #getVerificationResult(int)} method
     *
     * <p>
     *  用于使用{@link Reference#verify}对所有引用的参考文献执行<A HREF="http://www.w3.org/TR/xmldsig-core/#def-ValidationReference">
     * 参考验证</A>方法。
     * 
     *  <p>此步骤循环遍历所有{@link Reference}并验证哈希值。如果一个或多个验证失败,则方法返回<code> false </code>。
     * 如果<i>所有</i>验证成功,则返回<code> true </code>。通过使用{@link #getVerificationResult(int)}方法可以获得各个引用验证的结果。
     * 
     * 
     * @return true if all References verify, false if one or more do not verify.
     * @throws MissingResourceFailureException if a {@link Reference} does not verify
     * (throws a {@link com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException}
     * because of an uninitialized {@link XMLSignatureInput}
     * @see com.sun.org.apache.xml.internal.security.signature.Reference#verify
     * @see com.sun.org.apache.xml.internal.security.signature.SignedInfo#verify()
     * @see com.sun.org.apache.xml.internal.security.signature.MissingResourceFailureException
     * @throws XMLSecurityException
     */
    public boolean verifyReferences()
        throws MissingResourceFailureException, XMLSecurityException {
        return this.verifyReferences(false);
    }

    /**
     * Used to do a <A HREF="http://www.w3.org/TR/xmldsig-core/#def-ValidationReference">reference
     * validation</A> of all enclosed references using the {@link Reference#verify} method.
     *
     * <p>This step loops through all {@link Reference}s and does verify the hash
     * values. If one or more verifications fail, the method returns
     * <code>false</code>. If <i>all</i> verifications are successful,
     * it returns <code>true</code>. The results of the individual reference
     * validations are available by using the {@link #getVerificationResult(int)} method
     *
     * <p>
     *  用于使用{@link Reference#verify}对所有引用的参考文献执行<A HREF="http://www.w3.org/TR/xmldsig-core/#def-ValidationReference">
     * 参考验证</A>方法。
     * 
     * <p>此步骤循环遍历所有{@link Reference}并验证哈希值。如果一个或多个验证失败,则方法返回<code> false </code>。
     * 如果<i>所有</i>验证成功,则返回<code> true </code>。通过使用{@link #getVerificationResult(int)}方法可以获得各个引用验证的结果。
     * 
     * 
     * @param followManifests
     * @return true if all References verify, false if one or more do not verify.
     * @throws MissingResourceFailureException if a {@link Reference} does not verify
     * (throws a {@link com.sun.org.apache.xml.internal.security.signature.ReferenceNotInitializedException}
     * because of an uninitialized {@link XMLSignatureInput}
     * @see com.sun.org.apache.xml.internal.security.signature.Reference#verify
     * @see com.sun.org.apache.xml.internal.security.signature.SignedInfo#verify(boolean)
     * @see com.sun.org.apache.xml.internal.security.signature.MissingResourceFailureException
     * @throws XMLSecurityException
     */
    public boolean verifyReferences(boolean followManifests)
        throws MissingResourceFailureException, XMLSecurityException {
        if (referencesEl == null) {
            this.referencesEl =
                XMLUtils.selectDsNodes(
                    this.constructionElement.getFirstChild(), Constants._TAG_REFERENCE
                );
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "verify " + referencesEl.length + " References");
            log.log(java.util.logging.Level.FINE, "I am " + (followManifests
                ? "" : "not") + " requested to follow nested Manifests");
        }
        if (referencesEl.length == 0) {
            throw new XMLSecurityException("empty");
        }
        if (secureValidation && referencesEl.length > MAXIMUM_REFERENCE_COUNT) {
            Object exArgs[] = { referencesEl.length, MAXIMUM_REFERENCE_COUNT };

            throw new XMLSecurityException("signature.tooManyReferences", exArgs);
        }

        this.verificationResults = new boolean[referencesEl.length];
        boolean verify = true;
        for (int i = 0; i < this.referencesEl.length; i++) {
            Reference currentRef =
                new Reference(referencesEl[i], this.baseURI, this, secureValidation);

            this.references.set(i, currentRef);

            // if only one item does not verify, the whole verification fails
            try {
                boolean currentRefVerified = currentRef.verify();

                this.setVerificationResult(i, currentRefVerified);

                if (!currentRefVerified) {
                    verify = false;
                }
                if (log.isLoggable(java.util.logging.Level.FINE)) {
                    log.log(java.util.logging.Level.FINE, "The Reference has Type " + currentRef.getType());
                }

                // was verification successful till now and do we want to verify the Manifest?
                if (verify && followManifests && currentRef.typeIsReferenceToManifest()) {
                    if (log.isLoggable(java.util.logging.Level.FINE)) {
                        log.log(java.util.logging.Level.FINE, "We have to follow a nested Manifest");
                    }

                    try {
                        XMLSignatureInput signedManifestNodes =
                            currentRef.dereferenceURIandPerformTransforms(null);
                        Set<Node> nl = signedManifestNodes.getNodeSet();
                        Manifest referencedManifest = null;
                        Iterator<Node> nlIterator = nl.iterator();

                        findManifest: while (nlIterator.hasNext()) {
                            Node n = nlIterator.next();

                            if ((n.getNodeType() == Node.ELEMENT_NODE)
                                && ((Element) n).getNamespaceURI().equals(Constants.SignatureSpecNS)
                                && ((Element) n).getLocalName().equals(Constants._TAG_MANIFEST)
                            ) {
                                try {
                                    referencedManifest =
                                        new Manifest(
                                             (Element)n, signedManifestNodes.getSourceURI(), secureValidation
                                        );
                                    break findManifest;
                                } catch (XMLSecurityException ex) {
                                    if (log.isLoggable(java.util.logging.Level.FINE)) {
                                        log.log(java.util.logging.Level.FINE, ex.getMessage(), ex);
                                    }
                                    // Hm, seems not to be a ds:Manifest
                                }
                            }
                        }

                        if (referencedManifest == null) {
                            // The Reference stated that it points to a ds:Manifest
                            // but we did not find a ds:Manifest in the signed area
                            throw new MissingResourceFailureException("empty", currentRef);
                        }

                        referencedManifest.perManifestResolvers = this.perManifestResolvers;
                        referencedManifest.resolverProperties = this.resolverProperties;

                        boolean referencedManifestValid =
                            referencedManifest.verifyReferences(followManifests);

                        if (!referencedManifestValid) {
                            verify = false;

                            log.log(java.util.logging.Level.WARNING, "The nested Manifest was invalid (bad)");
                        } else {
                            if (log.isLoggable(java.util.logging.Level.FINE)) {
                                log.log(java.util.logging.Level.FINE, "The nested Manifest was valid (good)");
                            }
                        }
                    } catch (IOException ex) {
                        throw new ReferenceNotInitializedException("empty", ex);
                    } catch (ParserConfigurationException ex) {
                        throw new ReferenceNotInitializedException("empty", ex);
                    } catch (SAXException ex) {
                        throw new ReferenceNotInitializedException("empty", ex);
                    }
                }
            } catch (ReferenceNotInitializedException ex) {
                Object exArgs[] = { currentRef.getURI() };

                throw new MissingResourceFailureException(
                    "signature.Verification.Reference.NoInput", exArgs, ex, currentRef
                );
            }
        }

        return verify;
    }

    /**
     * Method setVerificationResult
     *
     * <p>
     *  方法setVerificationResult
     * 
     * 
     * @param index
     * @param verify
     */
    private void setVerificationResult(int index, boolean verify) {
        if (this.verificationResults == null) {
            this.verificationResults = new boolean[this.getLength()];
        }

        this.verificationResults[index] = verify;
    }

    /**
     * After verifying a {@link Manifest} or a {@link SignedInfo} using the
     * {@link Manifest#verifyReferences()} or {@link SignedInfo#verify()} methods,
     * the individual results can be retrieved with this method.
     *
     * <p>
     *  在使用{@link Manifest#verifyReferences()}或{@link SignedInfo#verify()}方法验证{@link Manifest}或{@link SignedInfo}
     * 后,可以使用此方法检索单个结果。
     * 
     * 
     * @param index an index of into a {@link Manifest} or a {@link SignedInfo}
     * @return the results of reference validation at the specified index
     * @throws XMLSecurityException
     */
    public boolean getVerificationResult(int index) throws XMLSecurityException {
        if ((index < 0) || (index > this.getLength() - 1)) {
            Object exArgs[] = { Integer.toString(index), Integer.toString(this.getLength()) };
            Exception e =
                new IndexOutOfBoundsException(
                    I18n.translate("signature.Verification.IndexOutOfBounds", exArgs)
                );

            throw new XMLSecurityException("generic.EmptyMessage", e);
        }

        if (this.verificationResults == null) {
            try {
                this.verifyReferences();
            } catch (Exception ex) {
                throw new XMLSecurityException("generic.EmptyMessage", ex);
            }
        }

        return this.verificationResults[index];
    }

    /**
     * Adds Resource Resolver for retrieving resources at specified <code>URI</code> attribute
     * in <code>reference</code> element
     *
     * <p>
     *  添加资源解析器以在<code>引用</code>元素中的指定<code> URI </code>属性检索资源
     * 
     * 
     * @param resolver {@link ResourceResolver} can provide the implemenatin subclass of
     * {@link ResourceResolverSpi} for retrieving resource.
     */
    public void addResourceResolver(ResourceResolver resolver) {
        if (resolver == null) {
            return;
        }
        if (perManifestResolvers == null) {
            perManifestResolvers = new ArrayList<ResourceResolver>();
        }
        this.perManifestResolvers.add(resolver);
    }

    /**
     * Adds Resource Resolver for retrieving resources at specified <code>URI</code> attribute
     * in <code>reference</code> element
     *
     * <p>
     *  添加资源解析器以在<code>引用</code>元素中的指定<code> URI </code>属性检索资源
     * 
     * 
     * @param resolverSpi the implementation subclass of {@link ResourceResolverSpi} for
     * retrieving the resource.
     */
    public void addResourceResolver(ResourceResolverSpi resolverSpi) {
        if (resolverSpi == null) {
            return;
        }
        if (perManifestResolvers == null) {
            perManifestResolvers = new ArrayList<ResourceResolver>();
        }
        perManifestResolvers.add(new ResourceResolver(resolverSpi));
    }

    /**
     * Get the Per-Manifest Resolver List
     * <p>
     *  获取每个清单解析器列表
     * 
     * 
     * @return the per-manifest Resolver List
     */
    public List<ResourceResolver> getPerManifestResolvers() {
        return perManifestResolvers;
    }

    /**
     * Get the resolver property map
     * <p>
     *  获取解析器属性映射
     * 
     * 
     * @return the resolver property map
     */
    public Map<String, String> getResolverProperties() {
        return resolverProperties;
    }

    /**
     * Used to pass parameters like proxy servers etc to the ResourceResolver
     * implementation.
     *
     * <p>
     *  用于将参数(如代理服务器等)传递给ResourceResolver实现。
     * 
     * 
     * @param key the key
     * @param value the value
     */
    public void setResolverProperty(String key, String value) {
        if (resolverProperties == null) {
            resolverProperties = new HashMap<String, String>(10);
        }
        this.resolverProperties.put(key, value);
    }

    /**
     * Returns the value at specified key
     *
     * <p>
     *  返回指定键的值
     * 
     * 
     * @param key the key
     * @return the value
     */
    public String getResolverProperty(String key) {
        return this.resolverProperties.get(key);
    }

    /**
     * Method getSignedContentItem
     *
     * <p>
     *  方法getSignedContentItem
     * 
     * 
     * @param i
     * @return The signed content of the i reference.
     *
     * @throws XMLSignatureException
     */
    public byte[] getSignedContentItem(int i) throws XMLSignatureException {
        try {
            return this.getReferencedContentAfterTransformsItem(i).getBytes();
        } catch (IOException ex) {
            throw new XMLSignatureException("empty", ex);
        } catch (CanonicalizationException ex) {
            throw new XMLSignatureException("empty", ex);
        } catch (InvalidCanonicalizerException ex) {
            throw new XMLSignatureException("empty", ex);
        } catch (XMLSecurityException ex) {
            throw new XMLSignatureException("empty", ex);
        }
    }

    /**
     * Method getReferencedContentPriorTransformsItem
     *
     * <p>
     *  方法getReferencedContentPriorTransformsItem
     * 
     * 
     * @param i
     * @return The contents before transformation of the reference i.
     * @throws XMLSecurityException
     */
    public XMLSignatureInput getReferencedContentBeforeTransformsItem(int i)
        throws XMLSecurityException {
        return this.item(i).getContentsBeforeTransformation();
    }

    /**
     * Method getReferencedContentAfterTransformsItem
     *
     * <p>
     *  方法getReferencedContentAfterTransformsItem
     * 
     * 
     * @param i
     * @return The contents after transformation of the reference i.
     * @throws XMLSecurityException
     */
    public XMLSignatureInput getReferencedContentAfterTransformsItem(int i)
        throws XMLSecurityException {
        return this.item(i).getContentsAfterTransformation();
    }

    /**
     * Method getSignedContentLength
     *
     * <p>
     *  方法getSignedContentLength
     * 
     * 
     * @return The number of references contained in this reference.
     */
    public int getSignedContentLength() {
        return this.getLength();
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
        return Constants._TAG_MANIFEST;
    }
}
