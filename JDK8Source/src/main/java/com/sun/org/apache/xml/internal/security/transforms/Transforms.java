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
package com.sun.org.apache.xml.internal.security.transforms;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.Canonicalizer;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Holder of the {@link com.sun.org.apache.xml.internal.security.transforms.Transform} steps to
 * be performed on the data.
 * The input to the first Transform is the result of dereferencing the
 * <code>URI</code> attribute of the <code>Reference</code> element.
 * The output from the last Transform is the input for the
 * <code>DigestMethod algorithm</code>
 *
 * <p>
 *  对数据执行的{@link com.sun.org.apache.xml.internal.security.transforms.Transform}步骤的持有人。
 * 第一个Transform的输入是取消引用<code> Reference </code>元素的<code> URI </code>属性的结果。
 * 最后一个变换的输出是<code> DigestMethod算法</code>的输入。
 * 
 * 
 * @author Christian Geuer-Pollmann
 * @see Transform
 * @see com.sun.org.apache.xml.internal.security.signature.Reference
 */
public class Transforms extends SignatureElementProxy {

    /** Canonicalization - Required Canonical XML (omits comments) */
    public static final String TRANSFORM_C14N_OMIT_COMMENTS
        = Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS;

    /** Canonicalization - Recommended Canonical XML with Comments */
    public static final String TRANSFORM_C14N_WITH_COMMENTS
        = Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS;

    /** Canonicalization - Required Canonical XML 1.1 (omits comments) */
    public static final String TRANSFORM_C14N11_OMIT_COMMENTS
        = Canonicalizer.ALGO_ID_C14N11_OMIT_COMMENTS;

    /** Canonicalization - Recommended Canonical XML 1.1 with Comments */
    public static final String TRANSFORM_C14N11_WITH_COMMENTS
        = Canonicalizer.ALGO_ID_C14N11_WITH_COMMENTS;

    /** Canonicalization - Required Exclusive Canonicalization (omits comments) */
    public static final String TRANSFORM_C14N_EXCL_OMIT_COMMENTS
        = Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS;

    /** Canonicalization - Recommended Exclusive Canonicalization with Comments */
    public static final String TRANSFORM_C14N_EXCL_WITH_COMMENTS
        = Canonicalizer.ALGO_ID_C14N_EXCL_WITH_COMMENTS;

    /** Transform - Optional XSLT */
    public static final String TRANSFORM_XSLT
        = "http://www.w3.org/TR/1999/REC-xslt-19991116";

    /** Transform - Required base64 decoding */
    public static final String TRANSFORM_BASE64_DECODE
        = Constants.SignatureSpecNS + "base64";

    /** Transform - Recommended XPath */
    public static final String TRANSFORM_XPATH
        = "http://www.w3.org/TR/1999/REC-xpath-19991116";

    /** Transform - Required Enveloped Signature */
    public static final String TRANSFORM_ENVELOPED_SIGNATURE
        = Constants.SignatureSpecNS + "enveloped-signature";

    /** Transform - XPointer */
    public static final String TRANSFORM_XPOINTER
        = "http://www.w3.org/TR/2001/WD-xptr-20010108";

    /** Transform - XPath Filter */
    public static final String TRANSFORM_XPATH2FILTER
        = "http://www.w3.org/2002/06/xmldsig-filter2";

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(Transforms.class.getName());

    private Element[] transforms;

    protected Transforms() { };

    private boolean secureValidation;

    /**
     * Constructs {@link Transforms}.
     *
     * <p>
     *  构造{@link Transforms}。
     * 
     * 
     * @param doc the {@link Document} in which <code>XMLSignature</code> will
     * be placed
     */
    public Transforms(Document doc) {
        super(doc);
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Constructs {@link Transforms} from {@link Element} which is
     * <code>Transforms</code> Element
     *
     * <p>
     *  构造来自{@link Element}的{@link Transforms},它是<code> Transforms </code>元素
     * 
     * 
     * @param element  is <code>Transforms</code> element
     * @param BaseURI the URI where the XML instance was stored
     * @throws DOMException
     * @throws InvalidTransformException
     * @throws TransformationException
     * @throws XMLSecurityException
     * @throws XMLSignatureException
     */
    public Transforms(Element element, String BaseURI)
        throws DOMException, XMLSignatureException, InvalidTransformException,
            TransformationException, XMLSecurityException {
        super(element, BaseURI);

        int numberOfTransformElems = this.getLength();

        if (numberOfTransformElems == 0) {
            // At least one Transform element must be present. Bad.
            Object exArgs[] = { Constants._TAG_TRANSFORM, Constants._TAG_TRANSFORMS };

            throw new TransformationException("xml.WrongContent", exArgs);
        }
    }

    /**
     * Set whether secure validation is enabled or not. The default is false.
     * <p>
     *  设置是否启用安全验证。默认值为false。
     * 
     */
    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
    }

    /**
     * Adds the <code>Transform</code> with the specified <code>Transform
     * algorithm URI</code>
     *
     * <p>
     * 使用指定的<code> Transform algorithm URI </code>添加<code> Transform </code>
     * 
     * 
     * @param transformURI the URI form of transform that indicates which
     * transformation is applied to data
     * @throws TransformationException
     */
    public void addTransform(String transformURI) throws TransformationException {
        try {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Transforms.addTransform(" + transformURI + ")");
            }

            Transform transform = new Transform(this.doc, transformURI);

            this.addTransform(transform);
        } catch (InvalidTransformException ex) {
            throw new TransformationException("empty", ex);
        }
    }

    /**
     * Adds the <code>Transform</code> with the specified <code>Transform
     * algorithm URI</code>
     *
     * <p>
     *  使用指定的<code> Transform algorithm URI </code>添加<code> Transform </code>
     * 
     * 
     * @param transformURI the URI form of transform that indicates which
     * transformation is applied to data
     * @param contextElement
     * @throws TransformationException
     */
    public void addTransform(String transformURI, Element contextElement)
       throws TransformationException {
        try {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Transforms.addTransform(" + transformURI + ")");
            }

            Transform transform = new Transform(this.doc, transformURI, contextElement);

            this.addTransform(transform);
        } catch (InvalidTransformException ex) {
            throw new TransformationException("empty", ex);
        }
    }

    /**
     * Adds the <code>Transform</code> with the specified <code>Transform
     * algorithm URI</code>.
     *
     * <p>
     *  使用指定的<code> Transform algorithm URI </code>添加<code> Transform </code>。
     * 
     * 
     * @param transformURI the URI form of transform that indicates which
     * transformation is applied to data
     * @param contextNodes
     * @throws TransformationException
     */
    public void addTransform(String transformURI, NodeList contextNodes)
       throws TransformationException {

        try {
            Transform transform = new Transform(this.doc, transformURI, contextNodes);
            this.addTransform(transform);
        } catch (InvalidTransformException ex) {
            throw new TransformationException("empty", ex);
        }
    }

    /**
     * Adds a user-provided Transform step.
     *
     * <p>
     *  添加用户提供的变换步骤。
     * 
     * 
     * @param transform {@link Transform} object
     */
    private void addTransform(Transform transform) {
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "Transforms.addTransform(" + transform.getURI() + ")");
        }

        Element transformElement = transform.getElement();

        this.constructionElement.appendChild(transformElement);
        XMLUtils.addReturnToElement(this.constructionElement);
    }

    /**
     * Applies all included <code>Transform</code>s to xmlSignatureInput and
     * returns the result of these transformations.
     *
     * <p>
     *  将所有包含的<code> Transform </code>应用于xmlSignatureInput并返回这些转换的结果。
     * 
     * 
     * @param xmlSignatureInput the input for the <code>Transform</code>s
     * @return the result of the <code>Transforms</code>
     * @throws TransformationException
     */
    public XMLSignatureInput performTransforms(
        XMLSignatureInput xmlSignatureInput
    ) throws TransformationException {
        return performTransforms(xmlSignatureInput, null);
    }

    /**
     * Applies all included <code>Transform</code>s to xmlSignatureInput and
     * returns the result of these transformations.
     *
     * <p>
     *  将所有包含的<code> Transform </code>应用于xmlSignatureInput并返回这些转换的结果。
     * 
     * 
     * @param xmlSignatureInput the input for the <code>Transform</code>s
     * @param os where to output the last transformation.
     * @return the result of the <code>Transforms</code>
     * @throws TransformationException
     */
    public XMLSignatureInput performTransforms(
        XMLSignatureInput xmlSignatureInput, OutputStream os
    ) throws TransformationException {
        try {
            int last = this.getLength() - 1;
            for (int i = 0; i < last; i++) {
                Transform t = this.item(i);
                String uri = t.getURI();
                if (log.isLoggable(java.util.logging.Level.FINE)) {
                    log.log(java.util.logging.Level.FINE, "Perform the (" + i + ")th " + uri + " transform");
                }
                checkSecureValidation(t);
                xmlSignatureInput = t.performTransform(xmlSignatureInput);
            }
            if (last >= 0) {
                Transform t = this.item(last);
                checkSecureValidation(t);
                xmlSignatureInput = t.performTransform(xmlSignatureInput, os);
            }

            return xmlSignatureInput;
        } catch (IOException ex) {
            throw new TransformationException("empty", ex);
        } catch (CanonicalizationException ex) {
            throw new TransformationException("empty", ex);
        } catch (InvalidCanonicalizerException ex) {
            throw new TransformationException("empty", ex);
        }
    }

    private void checkSecureValidation(Transform transform) throws TransformationException {
        String uri = transform.getURI();
        if (secureValidation && Transforms.TRANSFORM_XSLT.equals(uri)) {
            Object exArgs[] = { uri };

            throw new TransformationException(
                "signature.Transform.ForbiddenTransform", exArgs
            );
        }
    }

    /**
     * Return the nonnegative number of transformations.
     *
     * <p>
     *  返回非负的转换数。
     * 
     * 
     * @return the number of transformations
     */
    public int getLength() {
        if (transforms == null) {
            transforms =
                XMLUtils.selectDsNodes(this.constructionElement.getFirstChild(), "Transform");
        }
        return transforms.length;
    }

    /**
     * Return the <it>i</it><sup>th</sup> <code>{@link Transform}</code>.
     * Valid <code>i</code> values are 0 to <code>{@link #getLength}-1</code>.
     *
     * <p>
     *  返回<it> i </it> <sup> th </sup> <code> {@ link Transform} </code>。
     * 有效的<code> i </code>值为0到<code> {@ link #getLength} -1 </code>。
     * 
     * @param i index of {@link Transform} to return
     * @return the <it>i</it><sup>th</sup> Transform
     * @throws TransformationException
     */
    public Transform item(int i) throws TransformationException {
        try {
            if (transforms == null) {
                transforms =
                    XMLUtils.selectDsNodes(this.constructionElement.getFirstChild(), "Transform");
            }
            return new Transform(transforms[i], this.baseURI);
        } catch (XMLSecurityException ex) {
            throw new TransformationException("empty", ex);
        }
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_TRANSFORMS;
    }
}
