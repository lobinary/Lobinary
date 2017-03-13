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
 * $Id: Reference.java,v 1.9 2005/05/10 16:03:46 mullan Exp $
 * <p>
 *  $ Id：Referencejava,v 19 2005/05/10 16:03:46 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.Data;
import javax.xml.crypto.URIReference;
import javax.xml.crypto.XMLStructure;
import java.io.InputStream;
import java.util.List;

/**
 * A representation of the <code>Reference</code> element as defined in the
 * <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * The XML schema is defined as:
 * <code><pre>
 * &lt;element name="Reference" type="ds:ReferenceType"/&gt;
 * &lt;complexType name="ReferenceType"&gt;
 *   &lt;sequence&gt;
 *     &lt;element ref="ds:Transforms" minOccurs="0"/&gt;
 *     &lt;element ref="ds:DigestMethod"/&gt;
 *     &lt;element ref="ds:DigestValue"/&gt;
 *   &lt;/sequence&gt;
 *   &lt;attribute name="Id" type="ID" use="optional"/&gt;
 *   &lt;attribute name="URI" type="anyURI" use="optional"/&gt;
 *   &lt;attribute name="Type" type="anyURI" use="optional"/&gt;
 * &lt;/complexType&gt;
 *
 * &lt;element name="DigestValue" type="ds:DigestValueType"/&gt;
 * &lt;simpleType name="DigestValueType"&gt;
 *   &lt;restriction base="base64Binary"/&gt;
 * &lt;/simpleType&gt;
 * </pre></code>
 *
 * <p>A <code>Reference</code> instance may be created by invoking one of the
 * {@link XMLSignatureFactory#newReference newReference} methods of the
 * {@link XMLSignatureFactory} class; for example:
 *
 * <pre>
 *   XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
 *   Reference ref = factory.newReference
 *     ("http://www.ietf.org/rfc/rfc3275.txt",
 *      factory.newDigestMethod(DigestMethod.SHA1, null));
 * </pre>
 *
 * <p>
 *  <code>引用</code>元素的表示形式
 * <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C对XML签名语法和处理的建议</a> XML模式定义为：<code> <pre>&lt; element name ="Reference"type ="ds：ReferenceType"/&gt
 * ; &lt; complexType name ="ReferenceType"&gt; &lt; sequence&gt; &lt; element ref ="ds：Transforms"minOc
 * curs ="0"/&gt; &lt; element ref ="ds：DigestMethod"/&gt; &lt; element ref ="ds：DigestValue"/&gt; &lt; 
 * / sequence&gt; &lt; attribute name ="Id"type ="ID"use ="optional"/&gt; &lt; attribute name ="URI"type
 *  ="anyURI"use ="optional"/&gt; &lt; attribute name ="Type"type ="anyURI"use ="optional"/&gt; &lt; / c
 * omplexType&gt;。
 * 
 *  &lt; element name ="DigestValue"type ="ds：DigestValueType"/&gt; &lt; simpleType name ="DigestValueTy
 * pe"&gt; &lt; restriction base ="base64Binary"/&gt; &lt; / simpleType&gt; </pre> </code>。
 * 
 * <p>可以通过调用{@link XMLSignatureFactory}类的{@link XMLSignatureFactory#newReference newReference}方法之一创建<code>
 * 引用</code>实例;例如：。
 * 
 * <pre>
 *  XMLSignatureFactory factory = XMLSignatureFactorygetInstance("DOM");参考ref = factorynewReference("htt
 * p：// wwwietforg / rfc / rfc3275txt",factoryNewDigestMethod(DigestMethodSHA1,null));。
 * </pre>
 * 
 * 
 * @author Sean Mullan
 * @author Erwin van der Koogh
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#newReference(String, DigestMethod)
 * @see XMLSignatureFactory#newReference(String, DigestMethod, List, String, String)
 */
public interface Reference extends URIReference, XMLStructure {

    /**
     * Returns an {@link java.util.Collections#unmodifiableList unmodifiable
     * list} of {@link Transform}s that are contained in this
     * <code>Reference</code>.
     *
     * <p>
     *  返回包含在此<code>参考</code>中的{@link Transform}的{@link javautilCollections#unmodifiableList unmodifiable list}
     * 。
     * 
     * 
     * @return an unmodifiable list of <code>Transform</code>s
     *    (may be empty but never <code>null</code>)
     */
    @SuppressWarnings("rawtypes")
    List getTransforms();

    /**
     * Returns the digest method of this <code>Reference</code>.
     *
     * <p>
     *  返回此<code>引用</code>的摘要方法
     * 
     * 
     * @return the digest method
     */
    DigestMethod getDigestMethod();

    /**
     * Returns the optional <code>Id</code> attribute of this
     * <code>Reference</code>, which permits this reference to be
     * referenced from elsewhere.
     *
     * <p>
     *  返回此<code>引用</code>的可选<code> Id </code>属性,允许此引用从其他地方引用
     * 
     * 
     * @return the <code>Id</code> attribute (may be <code>null</code> if not
     *    specified)
     */
    String getId();

    /**
     * Returns the digest value of this <code>Reference</code>.
     *
     * <p>
     *  返回此<code>引用</code>的摘要值
     * 
     * 
     * @return the raw digest value, or <code>null</code> if this reference has
     *    not been digested yet. Each invocation of this method returns a new
     *    clone to protect against subsequent modification.
     */
    byte[] getDigestValue();

    /**
     * Returns the calculated digest value of this <code>Reference</code>
     * after a validation operation. This method is useful for debugging if
     * the reference fails to validate.
     *
     * <p>
     * 返回验证操作之后此<code>引用</code>的计算摘要值如果引用无法验证,此方法对于调试非常有用
     * 
     * 
     * @return the calculated digest value, or <code>null</code> if this
     *    reference has not been validated yet. Each invocation of this method
     *    returns a new clone to protect against subsequent modification.
     */
    byte[] getCalculatedDigestValue();

    /**
     * Validates this reference. This method verifies the digest of this
     * reference.
     *
     * <p>This method only validates the reference the first time it is
     * invoked. On subsequent invocations, it returns a cached result.
     *
     * <p>
     *  验证此引用此方法验证此引用的摘要
     * 
     *  <p>此方法仅在第一次调用引用时验证引用在后续调用中,它返回缓存的结果
     * 
     * 
     * @return <code>true</code> if this reference was validated successfully;
     *    <code>false</code> otherwise
     * @param validateContext the validating context
     * @throws NullPointerException if <code>validateContext</code> is
     *    <code>null</code>
     * @throws XMLSignatureException if an unexpected exception occurs while
     *    validating the reference
     */
    boolean validate(XMLValidateContext validateContext)
        throws XMLSignatureException;

    /**
     * Returns the dereferenced data, if
     * <a href="XMLSignContext.html#Supported Properties">reference caching</a>
     * is enabled. This is the result of dereferencing the URI of this
     * reference during a validation or generation operation.
     *
     * <p>
     *  如果启用了<a href=\"XMLSignContexthtml#Supported Properties\">引用高速缓存</a>,则返回取消引用的数据这是在验证或生成操作期间取消引用此引用的UR
     * I的结果。
     * 
     * 
     * @return the dereferenced data, or <code>null</code> if reference
     *    caching is not enabled or this reference has not been generated or
     *    validated
     */
    Data getDereferencedData();

    /**
     * Returns the pre-digested input stream, if
     * <a href="XMLSignContext.html#Supported Properties">reference caching</a>
     * is enabled. This is the input to the digest operation during a
     * validation or signing operation.
     *
     * <p>
     * 
     * @return an input stream containing the pre-digested input, or
     *    <code>null</code> if reference caching is not enabled or this
     *    reference has not been generated or validated
     */
    InputStream getDigestInputStream();
}
