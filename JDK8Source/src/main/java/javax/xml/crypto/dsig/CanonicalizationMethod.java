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
 * $Id: CanonicalizationMethod.java,v 1.6 2005/05/10 16:03:45 mullan Exp $
 * <p>
 *  $ Id：CanonicalizationMethod.java,v 1.6 2005/05/10 16:03:45 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

/**
 * A representation of the XML <code>CanonicalizationMethod</code>
 * element as defined in the
 * <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>. The XML
 * Schema Definition is defined as:
 * <p>
 * <pre>
 *   &lt;element name="CanonicalizationMethod" type="ds:CanonicalizationMethodType"/&gt;
 *     &lt;complexType name="CanonicalizationMethodType" mixed="true"&gt;
 *       &lt;sequence&gt;
 *         &lt;any namespace="##any" minOccurs="0" maxOccurs="unbounded"/&gt;
 *           &lt;!-- (0,unbounded) elements from (1,1) namespace --&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="Algorithm" type="anyURI" use="required"/&gt;
 *     &lt;/complexType&gt;
 * </pre>
 *
 * A <code>CanonicalizationMethod</code> instance may be created by invoking
 * the {@link XMLSignatureFactory#newCanonicalizationMethod
 * newCanonicalizationMethod} method of the {@link XMLSignatureFactory} class.
 *
 * <p>
 *  XML <code> CanonicalizationMethod </code>元素的表示形式
 * <a href="http://www.w3.org/TR/xmldsig-core/">
 *  W3C对XML签名语法和处理的建议</a>。 XML模式定义定义为：
 * <p>
 * <pre>
 *  &lt; element name ="CanonicalizationMethod"type ="ds：CanonicalizationMethodType"/&gt; &lt; complexTy
 * pe name ="CanonicalizationMethodType"mixed ="true"&gt; &lt; sequence&gt; &lt; any namespace ="## any"
 * minOccurs ="0"maxOccurs ="unbounded"/&gt; &lt;！ - (0,unbounded)elements from(1,1)namespace  - &gt; &l
 * t; / sequence&gt; &lt; attribute name ="Algorithm"type ="anyURI"use ="required"/&gt; &lt; / complexTy
 * pe&gt;。
 * </pre>
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newCanonicalizationMethod newCanonicalizationMethod}
 * 方法来创建<code> CanonicalizationMethod </code>实例。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newCanonicalizationMethod(String, C14NMethodParameterSpec)
 */
public interface CanonicalizationMethod extends Transform {

    /**
     * The <a href="http://www.w3.org/TR/2001/REC-xml-c14n-20010315">Canonical
     * XML (without comments)</a> canonicalization method algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/TR/2001/REC-xml-c14n-20010315">规范XML(无注释)</a>规范化方法算法URI。
     * 
     */
    final static String INCLUSIVE =
        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

    /**
     * The
     * <a href="http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments">
     * Canonical XML with comments</a> canonicalization method algorithm URI.
     * <p>
     *  的
     * <a href="http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments">
     *  规范XML与注释</a>规范化方法算法URI。
     * 
     */
    final static String INCLUSIVE_WITH_COMMENTS =
        "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";

    /**
     * The <a href="http://www.w3.org/2001/10/xml-exc-c14n#">Exclusive
     * Canonical XML (without comments)</a> canonicalization method algorithm
     * URI.
     * <p>
     *  <a href="http://www.w3.org/2001/10/xml-exc-c14n#">独有的规范XML(无注释)</a>规范化方法算法URI。
     * 
     */
    final static String EXCLUSIVE =
        "http://www.w3.org/2001/10/xml-exc-c14n#";

    /**
     * The <a href="http://www.w3.org/2001/10/xml-exc-c14n#WithComments">
     * Exclusive Canonical XML with comments</a> canonicalization method
     * algorithm URI.
     * <p>
     * <a href="http://www.w3.org/2001/10/xml-exc-c14n#WithComments">独家规范XML与注释</a>规范化方法算法URI。
     * 
     */
    final static String EXCLUSIVE_WITH_COMMENTS =
        "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";

    /**
     * Returns the algorithm-specific input parameters associated with this
     * <code>CanonicalizationMethod</code>.
     *
     * <p>The returned parameters can be typecast to a
     * {@link C14NMethodParameterSpec} object.
     *
     * <p>
     *  返回与此<code> CanonicalizationMethod </code>相关联的特定于算法的输入参数。
     * 
     * 
     * @return the algorithm-specific input parameters (may be
     *    <code>null</code> if not specified)
     */
    AlgorithmParameterSpec getParameterSpec();
}
