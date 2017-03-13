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
 * ===========================================================================
 *
 * (C) Copyright IBM Corp. 2003 All Rights Reserved.
 *
 * ===========================================================================
 * <p>
 *  ================================================== =======================
 * 
 *  (C)版权所有IBM Corp 2003保留所有权利
 * 
 *  ================================================== =======================
 * 
 */
/*
 * $Id: XMLObject.java,v 1.5 2005/05/10 16:03:48 mullan Exp $
 * <p>
 *  $ Id：XMLObjectjava,v 15 2005/05/10 16:03:48 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import java.util.List;
import javax.xml.crypto.XMLStructure;

/**
 * A representation of the XML <code>Object</code> element as defined in
 * the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * An <code>XMLObject</code> may contain any data and may include optional
 * MIME type, ID, and encoding attributes. The XML Schema Definition is
 * defined as:
 *
 * <pre><code>
 * &lt;element name="Object" type="ds:ObjectType"/&gt;
 * &lt;complexType name="ObjectType" mixed="true"&gt;
 *   &lt;sequence minOccurs="0" maxOccurs="unbounded"&gt;
 *     &lt;any namespace="##any" processContents="lax"/&gt;
 *   &lt;/sequence&gt;
 *   &lt;attribute name="Id" type="ID" use="optional"/&gt;
 *   &lt;attribute name="MimeType" type="string" use="optional"/&gt;
 *   &lt;attribute name="Encoding" type="anyURI" use="optional"/&gt;
 * &lt;/complexType&gt;
 * </code></pre>
 *
 * A <code>XMLObject</code> instance may be created by invoking the
 * {@link XMLSignatureFactory#newXMLObject newXMLObject} method of the
 * {@link XMLSignatureFactory} class; for example:
 *
 * <pre>
 *   XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
 *   List content = Collections.singletonList(fac.newManifest(references)));
 *   XMLObject object = factory.newXMLObject(content, "object-1", null, null);
 * </pre>
 *
 * <p>Note that this class is named <code>XMLObject</code> rather than
 * <code>Object</code> to avoid naming clashes with the existing
 * {@link java.lang.Object java.lang.Object} class.
 *
 * <p>
 *  在<a href=\"http://wwww3org/TR/xmldsig-core/\"> XML签名语法和处理的W3C建议</a>中定义的XML <code> Object </code>元素的表
 * 示</a> <code> XMLObject </code>可以包含任何数据,并且可以包括可选的MIME类型,ID和编码属性。
 * XML模式定义定义为：。
 * 
 * <pre> <code>&lt; element name ="Object"type ="ds：ObjectType"/&gt; &lt; complexType name ="ObjectType"
 * mixed ="true"&gt; &lt; sequence minOccurs ="0"maxOccurs ="unbounded"&gt; &lt; any namespace ="## any"
 * processContents ="lax"/&gt; &lt; / sequence&gt; &lt; attribute name ="Id"type ="ID"use ="optional"/&g
 * t; &lt; attribute name ="MimeType"type ="string"use ="optional"/&gt; &lt; attribute name ="Encoding"t
 * ype ="anyURI"use ="optional"/&gt; &lt; / complexType&gt; </code> </pre>。
 * 
 *  可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newXMLObject newXMLObject}方法来创建<code> 
 * XMLObject </code>实例;例如：。
 * 
 * <pre>
 * XMLSignatureFactory fac = XMLSignatureFactorygetInstance("DOM"); List content = CollectionssingletonL
 * ist(facnewManifest(references))); XMLObject object = factorynewXMLObject(content,"object-1",null,null
 * );。
 * </pre>
 * 
 *  <p>请注意,此类名为<code> XMLObject </code>而不是<code> Object </code>,以避免与现有{@link javalangObject javalangObject}
 * 类。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @author Joyce L. Leung
 * @since 1.6
 * @see XMLSignatureFactory#newXMLObject(List, String, String, String)
 */
public interface XMLObject extends XMLStructure {

    /**
     * URI that identifies the <code>Object</code> element (this can be
     * specified as the value of the <code>type</code> parameter of the
     * {@link Reference} class to identify the referent's type).
     * <p>
     * 
     */
    final static String TYPE = "http://www.w3.org/2000/09/xmldsig#Object";

    /**
     * Returns an {@link java.util.Collections#unmodifiableList unmodifiable
     * list} of {@link XMLStructure}s contained in this <code>XMLObject</code>,
     * which represent elements from any namespace.
     *
     *<p>If there is a public subclass representing the type of
     * <code>XMLStructure</code>, it is returned as an instance of that class
     * (ex: a <code>SignatureProperties</code> element would be returned
     * as an instance of {@link javax.xml.crypto.dsig.SignatureProperties}).
     *
     * <p>
     *  标识<code> Object </code>元素的URI(可以指定为{@link Reference}类的<code> type </code>参数的值,以标识引用对象的类型)
     * 
     * 
     * @return an unmodifiable list of <code>XMLStructure</code>s (may be empty
     *    but never <code>null</code>)
     */
    @SuppressWarnings("rawtypes")
    List getContent();

    /**
     * Returns the Id of this <code>XMLObject</code>.
     *
     * <p>
     *  返回包含在此<code> XMLObject </code>中的{@link XMLStructure}的{@link javautilCollections#unmodifiableList unmodifiable list}
     * ,它们表示任何命名空间中的元素。
     * 
     * p>如果有一个代表<code> XMLStructure </code>类型的公共子类,它将作为该类的实例返回(例如：<code> SignatureProperties </code>元素将作为实例返
     * 回) {@link javaxxmlcryptodsigSignatureProperties})。
     * 
     * 
     * @return the Id (or <code>null</code> if not specified)
     */
    String getId();

    /**
     * Returns the mime type of this <code>XMLObject</code>. The
     * mime type is an optional attribute which describes the data within this
     * <code>XMLObject</code> (independent of its encoding).
     *
     * <p>
     *  返回此<code> XMLObject </code>的ID
     * 
     * 
     * @return the mime type (or <code>null</code> if not specified)
     */
    String getMimeType();

    /**
     * Returns the encoding URI of this <code>XMLObject</code>. The encoding
     * URI identifies the method by which the object is encoded.
     *
     * <p>
     *  返回此<code> XMLObject </code>的MIME类型。mime类型是一个可选属性,描述此<code> XMLObject </code>(独立于其编码)
     * 
     * 
     * @return the encoding URI (or <code>null</code> if not specified)
     */
    String getEncoding();
}
