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
 * $Id: Transform.java,v 1.5 2005/05/10 16:03:48 mullan Exp $
 * <p>
 *  $ Id：Transform.java,v 1.5 2005/05/10 16:03:48 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.Data;
import javax.xml.crypto.OctetStreamData;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

/**
 * A representation of the XML <code>Transform</code> element as
 * defined in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 *
 * <pre>
 * &lt;element name="Transform" type="ds:TransformType"/&gt;
 *   &lt;complexType name="TransformType" mixed="true"&gt;
 *     &lt;choice minOccurs="0" maxOccurs="unbounded"&gt;
 *       &lt;any namespace="##other" processContents="lax"/&gt;
 *       &lt;!-- (1,1) elements from (0,unbounded) namespaces --&gt;
 *       &lt;element name="XPath" type="string"/&gt;
 *     &lt;/choice&gt;
 *     &lt;attribute name="Algorithm" type="anyURI" use="required"/&gt;
 *   &lt;/complexType&gt;
 * </pre>
 *
 * A <code>Transform</code> instance may be created by invoking the
 * {@link XMLSignatureFactory#newTransform newTransform} method
 * of the {@link XMLSignatureFactory} class.
 *
 * <p>
 *  <a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> Transform </code>元素的表示
 * 形式</a>。
 *  XML模式定义定义为：。
 * 
 * <pre>
 *  &lt; element name ="Transform"type ="ds：TransformType"/&gt; &lt; complexType name ="TransformType"mi
 * xed ="true"&gt; &lt; choice minOccurs ="0"maxOccurs ="unbounded"&gt; &lt; any namespace ="## other"pr
 * ocessContents ="lax"/&gt;来自(0,无限)命名空间的！ - (1,1)个元素 - &gt; &lt; element name ="XPath"type ="string"/&g
 * t; &lt; / choice&gt; &lt; attribute name ="Algorithm"type ="anyURI"use ="required"/&gt; &lt; / comple
 * xType&gt;。
 * </pre>
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newTransform newTransform}方法来创建<code> 
 * Transform </code>实例。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newTransform(String, TransformParameterSpec)
 */
public interface Transform extends XMLStructure, AlgorithmMethod {

    /**
     * The <a href="http://www.w3.org/2000/09/xmldsig#base64">Base64</a>
     * transform algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/2000/09/xmldsig#base64"> Base64 </a>变换算法URI。
     * 
     */
    final static String BASE64 = "http://www.w3.org/2000/09/xmldsig#base64";

    /**
     * The <a href="http://www.w3.org/2000/09/xmldsig#enveloped-signature">
     * Enveloped Signature</a> transform algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/2000/09/xmldsig#enveloped-signature"> Enveloped Signature </a>变换算法URI。
     * 
     */
    final static String ENVELOPED =
        "http://www.w3.org/2000/09/xmldsig#enveloped-signature";

    /**
     * The <a href="http://www.w3.org/TR/1999/REC-xpath-19991116">XPath</a>
     * transform algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/TR/1999/REC-xpath-19991116"> XPath </a>变换算法URI。
     * 
     */
    final static String XPATH = "http://www.w3.org/TR/1999/REC-xpath-19991116";

    /**
     * The <a href="http://www.w3.org/2002/06/xmldsig-filter2">
     * XPath Filter 2</a> transform algorithm URI.
     * <p>
     *  <a href="http://www.w3.org/2002/06/xmldsig-filter2"> XPath过滤器2 </a>变换算法URI。
     * 
     */
    final static String XPATH2 = "http://www.w3.org/2002/06/xmldsig-filter2";

    /**
     * The <a href="http://www.w3.org/TR/1999/REC-xslt-19991116">XSLT</a>
     * transform algorithm URI.
     * <p>
     * <a href="http://www.w3.org/TR/1999/REC-xslt-19991116"> XSLT </a>变换算法URI。
     * 
     */
    final static String XSLT = "http://www.w3.org/TR/1999/REC-xslt-19991116";

    /**
     * Returns the algorithm-specific input parameters associated with this
     * <code>Transform</code>.
     * <p>
     * The returned parameters can be typecast to a
     * {@link TransformParameterSpec} object.
     *
     * <p>
     *  返回与此<code> Transform </code>关联的特定于算法的输入参数。
     * <p>
     *  返回的参数可以类型转换为{@link TransformParameterSpec}对象。
     * 
     * 
     * @return the algorithm-specific input parameters (may be <code>null</code>
     *    if not specified)
     */
    AlgorithmParameterSpec getParameterSpec();

    /**
     * Transforms the specified data using the underlying transform algorithm.
     *
     * <p>
     *  使用基础变换算法转换指定的数据。
     * 
     * 
     * @param data the data to be transformed
     * @param context the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @return the transformed data
     * @throws NullPointerException if <code>data</code> is <code>null</code>
     * @throws TransformException if an error occurs while executing the
     *    transform
     */
    public abstract Data transform(Data data, XMLCryptoContext context)
        throws TransformException;

    /**
     * Transforms the specified data using the underlying transform algorithm.
     * If the output of this transform is an <code>OctetStreamData</code>, then
     * this method returns <code>null</code> and the bytes are written to the
     * specified <code>OutputStream</code>. Otherwise, the
     * <code>OutputStream</code> is ignored and the method behaves as if
     * {@link #transform(Data, XMLCryptoContext)} were invoked.
     *
     * <p>
     *  使用基础变换算法转换指定的数据。
     * 如果这个变换的输出是一个<code> OctetStreamData </code>,那么这个方法返回<code> null </code>,字节写入指定的<code> OutputStream </code>
     * 。
     * 
     * @param data the data to be transformed
     * @param context the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @param os the <code>OutputStream</code> that should be used to write
     *    the transformed data to
     * @return the transformed data (or <code>null</code> if the data was
     *    written to the <code>OutputStream</code> parameter)
     * @throws NullPointerException if <code>data</code> or <code>os</code>
     *    is <code>null</code>
     * @throws TransformException if an error occurs while executing the
     *    transform
     */
    public abstract Data transform
        (Data data, XMLCryptoContext context, OutputStream os)
        throws TransformException;
}
