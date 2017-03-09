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
 * $Id: PGPData.java,v 1.4 2005/05/10 16:35:35 mullan Exp $
 * <p>
 *  $ Id：PGPData.java,v 1.4 2005/05/10 16:35:35 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.keyinfo;

import java.util.Collections;
import java.util.List;
import javax.xml.crypto.XMLStructure;

/**
 * A representation of the XML <code>PGPData</code> element as defined in
 * the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>. A
 * <code>PGPData</code> object is used to convey information related to
 * PGP public key pairs and signatures on such keys. The XML Schema Definition
 * is defined as:
 *
 * <pre>
 *    &lt;element name="PGPData" type="ds:PGPDataType"/&gt;
 *    &lt;complexType name="PGPDataType"&gt;
 *      &lt;choice&gt;
 *        &lt;sequence&gt;
 *          &lt;element name="PGPKeyID" type="base64Binary"/&gt;
 *          &lt;element name="PGPKeyPacket" type="base64Binary" minOccurs="0"/&gt;
 *          &lt;any namespace="##other" processContents="lax" minOccurs="0"
 *           maxOccurs="unbounded"/&gt;
 *        &lt;/sequence&gt;
 *        &lt;sequence&gt;
 *          &lt;element name="PGPKeyPacket" type="base64Binary"/&gt;
 *          &lt;any namespace="##other" processContents="lax" minOccurs="0"
 *           maxOccurs="unbounded"/&gt;
 *        &lt;/sequence&gt;
 *      &lt;/choice&gt;
 *    &lt;/complexType&gt;
 * </pre>
 *
 * A <code>PGPData</code> instance may be created by invoking one of the
 * {@link KeyInfoFactory#newPGPData newPGPData} methods of the {@link
 * KeyInfoFactory} class, and passing it
 * <code>byte</code> arrays representing the contents of the PGP public key
 * identifier and/or PGP key material packet, and an optional list of
 * elements from an external namespace.
 *
 * <p>
 *  在<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> PGPData </code>元素的表示形
 * 式</a>。
 *  <code> PGPData </code>对象用于传达与这些密钥上的PGP公钥对和签名相关的信息。 XML模式定义定义为：。
 * 
 * <pre>
 *  &lt; element name ="PGPData"type ="ds：PGPDataType"/&gt; &lt; complexType name ="PGPDataType"&gt; &lt
 * ; choice&gt; &lt; sequence&gt; &lt; element name ="PGPKeyID"type ="base64Binary"/&gt; &lt; element na
 * me ="PGPKeyPacket"type ="base64Binary"minOccurs ="0"/&gt; &lt; any namespace ="## other"processConten
 * ts ="lax"minOccurs ="0"maxOccurs ="unbounded"/&gt; &lt; / sequence&gt; &lt; sequence&gt; &lt; element
 *  name ="PGPKeyPacket"type ="base64Binary"/&gt; &lt; any namespace ="## other"processContents ="lax"mi
 * nOccurs ="0"maxOccurs ="unbounded"/&gt; &lt; / sequence&gt; &lt; / choice&gt; &lt; / complexType&gt;。
 * </pre>
 * 
 *  可以通过调用{@link KeyInfoFactory}类的{@link KeyInfoFactory#newPGPData newPGPData}方法中的一个来创建<code> PGPData </code>
 * 实例,并且传递<code> byte </code> PGP公钥标识符和/或PGP密钥材料分组的内容,以及来自外部命名空间的元素的可选列表。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see KeyInfoFactory#newPGPData(byte[])
 * @see KeyInfoFactory#newPGPData(byte[], byte[], List)
 * @see KeyInfoFactory#newPGPData(byte[], List)
 */
public interface PGPData extends XMLStructure {

    /**
     * URI identifying the PGPData KeyInfo type:
     * http://www.w3.org/2000/09/xmldsig#PGPData. This can be specified as the
     * value of the <code>type</code> parameter of the {@link RetrievalMethod}
     * class to describe a remote <code>PGPData</code> structure.
     * <p>
     */
    final static String TYPE = "http://www.w3.org/2000/09/xmldsig#PGPData";

    /**
     * Returns the PGP public key identifier of this <code>PGPData</code> as
     * defined in <a href="http://www.ietf.org/rfc/rfc2440.txt">RFC 2440</a>,
     * section 11.2.
     *
     * <p>
     * 标识PGPData KeyInfo类型的URI：http://www.w3.org/2000/09/xmldsig#PGPData。
     * 这可以被指定为{@link RetrievalMethod}类的<code> type </code>参数的值,以描述远程<code> PGPData </code>结构。
     * 
     * 
     * @return the PGP public key identifier (may be <code>null</code> if
     *    not specified). Each invocation of this method returns a new clone
     *    to protect against subsequent modification.
     */
    byte[] getKeyId();

    /**
     * Returns the PGP key material packet of this <code>PGPData</code> as
     * defined in <a href="http://www.ietf.org/rfc/rfc2440.txt">RFC 2440</a>,
     * section 5.5.
     *
     * <p>
     *  返回<a href="http://www.ietf.org/rfc/rfc2440.txt"> RFC 2440 </a>,第11.2节中定义的<code> PGPData </code>的PGP公
     * 钥标识符。
     * 
     * 
     * @return the PGP key material packet (may be <code>null</code> if not
     *    specified). Each invocation of this method returns a new clone to
     *    protect against subsequent modification.
     */
    byte[] getKeyPacket();

    /**
     * Returns an {@link Collections#unmodifiableList unmodifiable list}
     * of {@link XMLStructure}s representing elements from an external
     * namespace.
     *
     * <p>
     *  返回<a href="http://www.ietf.org/rfc/rfc2440.txt"> RFC 2440 </a>,第5.5节中定义的<code> PGPData </code>的PGP密钥
     * 素材数据包。
     * 
     * 
     * @return an unmodifiable list of <code>XMLStructure</code>s (may be
     *    empty, but never <code>null</code>)
     */
    @SuppressWarnings("rawtypes")
    List getExternalElements();
}
