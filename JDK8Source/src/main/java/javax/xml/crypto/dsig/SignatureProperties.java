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
 * $Id: SignatureProperties.java,v 1.4 2005/05/10 16:03:46 mullan Exp $
 * <p>
 *  $ Id：SignatureProperties.java,v 1.4 2005/05/10 16:03:46 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.XMLStructure;
import java.util.List;

/**
 * A representation of the XML <code>SignatureProperties</code> element as
 * defined in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML Schema Definition is defined as:
 * <pre><code>
 *&lt;element name="SignatureProperties" type="ds:SignaturePropertiesType"/&gt;
 *   &lt;complexType name="SignaturePropertiesType"&gt;
 *     &lt;sequence&gt;
 *       &lt;element ref="ds:SignatureProperty" maxOccurs="unbounded"/&gt;
 *     &lt;/sequence&gt;
 *     &lt;attribute name="Id" type="ID" use="optional"/&gt;
 *   &lt;/complexType&gt;
 * </code></pre>
 *
 * A <code>SignatureProperties</code> instance may be created by invoking the
 * {@link XMLSignatureFactory#newSignatureProperties newSignatureProperties}
 * method of the {@link XMLSignatureFactory} class; for example:
 *
 * <pre>
 *   XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
 *   SignatureProperties properties =
 *      factory.newSignatureProperties(props, "signature-properties-1");
 * </pre>
 *
 * <p>
 *  在<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> SignatureProperties </code>
 * 元素的表示形式</a>。
 *  XML模式定义定义为：<pre> <code> lt; element name ="SignatureProperties"type ="ds：SignaturePropertiesType"/&g
 * t; &lt; complexType name ="SignaturePropertiesType"&gt; &lt; sequence&gt; &lt; element ref ="ds：Signa
 * tureProperty"maxOccurs ="unbounded"/&gt; &lt; / sequence&gt; &lt; attribute name ="Id"type ="ID"use =
 * "optional"/&gt; &lt; / complexType&gt; </code> </pre>。
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newSignatureProperties newSignatureProperties}
 * 方法来创建<code> SignatureProperties </code>实例;例如：。
 * 
 * <pre>
 *  XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM"); SignatureProperties properties
 *  = factory.newSignatureProperties(props,"signature-properties-1");。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newSignatureProperties(List, String)
 * @see SignatureProperty
 */
public interface SignatureProperties extends XMLStructure {

    /**
     * URI that identifies the <code>SignatureProperties</code> element (this
     * can be specified as the value of the <code>type</code> parameter of the
     * {@link Reference} class to identify the referent's type).
     * <p>
     * </pre>
     * 
     */
    final static String TYPE =
        "http://www.w3.org/2000/09/xmldsig#SignatureProperties";

    /**
     * Returns the Id of this <code>SignatureProperties</code>.
     *
     * <p>
     *  标识<code> SignatureProperties </code>元素的URI(可以将其指定为{@link Reference}类的<code> type </code>参数的值,以标识引用对象
     * 的类型)。
     * 
     * 
     * @return the Id of this <code>SignatureProperties</code> (or
     *    <code>null</code> if not specified)
     */
    String getId();

    /**
     * Returns an {@link java.util.Collections#unmodifiableList unmodifiable
     * list} of one or more {@link SignatureProperty}s that are contained in
     * this <code>SignatureProperties</code>.
     *
     * <p>
     *  返回此<code> SignatureProperties </code>的ID。
     * 
     * 
     * @return an unmodifiable list of one or more
     *    <code>SignatureProperty</code>s
     */
    @SuppressWarnings("rawtypes")
    List getProperties();
}
