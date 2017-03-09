/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: SignatureMethod.java,v 1.5 2005/05/10 16:03:46 mullan Exp $
 * <p>
 *  $ Id：SignatureMethod.java,v 1.5 2005/05/10 16:03:46 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * A representation of the XML <code>SignatureMethod</code> element
 * as defined in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 * <p>
 * <pre>
 *   &lt;element name="SignatureMethod" type="ds:SignatureMethodType"/&gt;
 *     &lt;complexType name="SignatureMethodType" mixed="true"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="HMACOutputLength" minOccurs="0" type="ds:HMACOutputLengthType"/&gt;
 *         &lt;any namespace="##any" minOccurs="0" maxOccurs="unbounded"/&gt;
 *           &lt;!-- (0,unbounded) elements from (1,1) namespace --&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Algorithm" type="anyURI" use="required"/&gt;
 *     &lt;/complexType&gt;
 * </pre>
 *
 * A <code>SignatureMethod</code> instance may be created by invoking the
 * {@link XMLSignatureFactory#newSignatureMethod newSignatureMethod} method
 * of the {@link XMLSignatureFactory} class.
 *
 * <p>
 *  在<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> SignatureMethod </code>
 * 元素的表示形式</a>。
 *  XML模式定义定义为：。
 * <p>
 * <pre>
 *  &lt; element name ="SignatureMethod"type ="ds：SignatureMethodType"/&gt; &lt; complexType name ="Sign
 * atureMethodType"mixed ="true"&gt; &lt; sequence&gt; &lt; element name ="HMACOutputLength"minOccurs ="
 * 0"type ="ds：HMACOutputLengthType"/&gt; &lt; any namespace ="## any"minOccurs ="0"maxOccurs ="unbounde
 * d"/&gt; &lt;！ - (0,unbounded)elements from(1,1)namespace  - &gt; &lt; / sequence&gt; &lt; attribute n
 * ame ="Algorithm"type ="anyURI"use ="required"/&gt; &lt; / complexType&gt;。
 * </pre>
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newSignatureMethod newSignatureMethod}
 * 方法来创建<code> SignatureMethod </code>实例。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newSignatureMethod(String, SignatureMethodParameterSpec)
 */
public interface SignatureMethod extends XMLStructure, AlgorithmMethod {

    /**
     * The <a href="http://www.w3.org/2000/09/xmldsig#dsa-sha1">DSAwithSHA1</a>
     * (DSS) signature method algorithm URI.
     * <p>
     */
    static final String DSA_SHA1 =
        "http://www.w3.org/2000/09/xmldsig#dsa-sha1";

    /**
     * The <a href="http://www.w3.org/2000/09/xmldsig#rsa-sha1">RSAwithSHA1</a>
     * (PKCS #1) signature method algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/2000/09/xmldsig#dsa-sha1"> DSAwithSHA1 </a>(DSS)签名方法算法URI。
     * 
     */
    static final String RSA_SHA1 =
        "http://www.w3.org/2000/09/xmldsig#rsa-sha1";

    /**
     * The <a href="http://www.w3.org/2000/09/xmldsig#hmac-sha1">HMAC-SHA1</a>
     * MAC signature method algorithm URI
     * <p>
     *  签名方法算法URI的<a href="http://www.w3.org/2000/09/xmldsig#rsa-sha1"> RSAwithSHA1 </a>(PKCS#1)。
     * 
     */
    static final String HMAC_SHA1 =
        "http://www.w3.org/2000/09/xmldsig#hmac-sha1";

    /**
     * Returns the algorithm-specific input parameters of this
     * <code>SignatureMethod</code>.
     *
     * <p>The returned parameters can be typecast to a {@link
     * SignatureMethodParameterSpec} object.
     *
     * <p>
     *  <a href="http://www.w3.org/2000/09/xmldsig#hmac-sha1"> HMAC-SHA1 </a> MAC签名方法算法URI
     * 
     * 
     * @return the algorithm-specific input parameters of this
     *    <code>SignatureMethod</code> (may be <code>null</code> if not
     *    specified)
     */
    AlgorithmParameterSpec getParameterSpec();
}
