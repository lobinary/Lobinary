/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
/*
 * $Id: SignedInfo.java,v 1.7 2005/05/10 16:03:47 mullan Exp $
 * <p>
 *  $ Id：SignedInfo.java,v 1.7 2005/05/10 16:03:47 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.XMLStructure;
import java.io.InputStream;
import java.util.List;

/**
 * An representation of the XML <code>SignedInfo</code> element as
 * defined in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 * <pre><code>
 * &lt;element name="SignedInfo" type="ds:SignedInfoType"/&gt;
 * &lt;complexType name="SignedInfoType"&gt;
 *   &lt;sequence&gt;
 *     &lt;element ref="ds:CanonicalizationMethod"/&gt;
 *     &lt;element ref="ds:SignatureMethod"/&gt;
 *     &lt;element ref="ds:Reference" maxOccurs="unbounded"/&gt;
 *   &lt;/sequence&gt;
 *   &lt;attribute name="Id" type="ID" use="optional"/&gt;
 * &lt;/complexType&gt;
 * </code></pre>
 *
 * A <code>SignedInfo</code> instance may be created by invoking one of the
 * {@link XMLSignatureFactory#newSignedInfo newSignedInfo} methods of the
 * {@link XMLSignatureFactory} class.
 *
 * <p>
 *  在<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> SignedInfo </code>元素的
 * 表示形式</a>。
 *  XML模式定义被定义为：<pre> <code>&lt; element name ="SignedInfo"type ="ds：SignedInfoType"/&gt; &lt; complexTy
 * pe name ="SignedInfoType"&gt; &lt; sequence&gt; &lt; element ref ="ds：CanonicalizationMethod"/&gt; &l
 * t; element ref ="ds：SignatureMethod"/&gt; &lt; element ref ="ds：Reference"maxOccurs ="unbounded"/&gt;
 *  &lt; / sequence&gt; &lt; attribute name ="Id"type ="ID"use ="optional"/&gt; &lt; / complexType&gt; </code>
 *  </pre>。
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newSignedInfo newSignedInfo}方法之一来创建<code>
 *  SignedInfo </code>实例。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newSignedInfo(CanonicalizationMethod, SignatureMethod, List)
 * @see XMLSignatureFactory#newSignedInfo(CanonicalizationMethod, SignatureMethod, List, String)
 */
public interface SignedInfo extends XMLStructure {

    /**
     * Returns the canonicalization method of this <code>SignedInfo</code>.
     *
     * <p>
     *  返回此<code> SignedInfo </code>的规范化方法。
     * 
     * 
     * @return the canonicalization method
     */
    CanonicalizationMethod getCanonicalizationMethod();

    /**
     * Returns the signature method of this <code>SignedInfo</code>.
     *
     * <p>
     *  返回此<code> SignedInfo </code>的签名方法。
     * 
     * 
     * @return the signature method
     */
    SignatureMethod getSignatureMethod();

    /**
     * Returns an {@link java.util.Collections#unmodifiableList
     * unmodifiable list} of one or more {@link Reference}s.
     *
     * <p>
     *  返回一个或多个{@link参考}的{@link java.util.Collections#unmodifiableList unmodifiable list}。
     * 
     * 
     * @return an unmodifiable list of one or more {@link Reference}s
     */
    @SuppressWarnings("rawtypes")
    List getReferences();

    /**
     * Returns the optional <code>Id</code> attribute of this
     * <code>SignedInfo</code>.
     *
     * <p>
     *  返回此<code> SignedInfo </code>的可选<code> Id </code>属性。
     * 
     * 
     * @return the id (may be <code>null</code> if not specified)
     */
    String getId();

    /**
     * Returns the canonicalized signed info bytes after a signing or
     * validation operation. This method is useful for debugging.
     *
     * <p>
     *  在签名或验证操作后返回规范化的签名信息字节。此方法对于调试非常有用。
     * 
     * @return an <code>InputStream</code> containing the canonicalized bytes,
     *    or <code>null</code> if this <code>SignedInfo</code> has not been
     *    signed or validated yet
     */
    InputStream getCanonicalizedData();
}
