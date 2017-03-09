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
 * $Id: KeyName.java,v 1.4 2005/05/10 16:35:35 mullan Exp $
 * <p>
 *  $ Id：KeyName.java,v 1.4 2005/05/10 16:35:35 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.keyinfo;

import javax.xml.crypto.XMLStructure;

/**
 * A representation of the XML <code>KeyName</code> element as
 * defined in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>.
 * A <code>KeyName</code> object contains a string value which may be used
 * by the signer to communicate a key identifier to the recipient. The
 * XML Schema Definition is defined as:
 *
 * <pre>
 * &lt;element name="KeyName" type="string"/&gt;
 * </pre>
 *
 * A <code>KeyName</code> instance may be created by invoking the
 * {@link KeyInfoFactory#newKeyName newKeyName} method of the
 * {@link KeyInfoFactory} class, and passing it a <code>String</code>
 * representing the name of the key; for example:
 * <pre>
 * KeyInfoFactory factory = KeyInfoFactory.getInstance("DOM");
 * KeyName keyName = factory.newKeyName("Alice");
 * </pre>
 *
 * <p>
 *  在<a href="http://www.w3.org/TR/xmldsig-core/"> XML签名语法和处理的W3C建议书中定义的XML <code> KeyName </code>元素的表示形
 * 式</a>。
 *  <code> KeyName </code>对象包含字符串值,签名者可以使用该值来将密钥标识符传达给接收方。 XML模式定义定义为：。
 * 
 * <pre>
 *  &lt; element name ="KeyName"type ="string"/&gt;
 * </pre>
 * 
 *  可以通过调用{@link KeyInfoFactory}类的{@link KeyInfoFactory#newKeyName newKeyName}方法并传递<code> String </code>
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see KeyInfoFactory#newKeyName(String)
 */
public interface KeyName extends XMLStructure {

    /**
     * Returns the name of this <code>KeyName</code>.
     *
     * <p>
     * 实例来创建<code> KeyName </code>实例钥匙;例如：。
     * <pre>
     *  KeyInfoFactory factory = KeyInfoFactory.getInstance("DOM"); KeyName keyName = factory.newKeyName("Al
     * ice");。
     * </pre>
     * 
     * @return the name of this <code>KeyName</code> (never
     *    <code>null</code>)
     */
    String getName();
}
