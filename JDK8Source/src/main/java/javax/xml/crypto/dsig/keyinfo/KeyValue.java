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
 * $Id: KeyValue.java,v 1.4 2005/05/10 16:35:35 mullan Exp $
 * <p>
 *  $ Id：KeyValuejava,v 14 2005/05/10 16:35:35 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.keyinfo;

import java.security.KeyException;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import javax.xml.crypto.XMLStructure;

/**
 * A representation of the XML <code>KeyValue</code> element as defined
 * in the <a href="http://www.w3.org/TR/xmldsig-core/">
 * W3C Recommendation for XML-Signature Syntax and Processing</a>. A
 * <code>KeyValue</code> object contains a single public key that may be
 * useful in validating the signature. The XML schema definition is defined as:
 *
 * <pre>
 *    &lt;element name="KeyValue" type="ds:KeyValueType"/&gt;
 *    &lt;complexType name="KeyValueType" mixed="true"&gt;
 *      &lt;choice&gt;
 *        &lt;element ref="ds:DSAKeyValue"/&gt;
 *        &lt;element ref="ds:RSAKeyValue"/&gt;
 *        &lt;any namespace="##other" processContents="lax"/&gt;
 *      &lt;/choice&gt;
 *    &lt;/complexType&gt;
 *
 *    &lt;element name="DSAKeyValue" type="ds:DSAKeyValueType"/&gt;
 *    &lt;complexType name="DSAKeyValueType"&gt;
 *      &lt;sequence&gt;
 *        &lt;sequence minOccurs="0"&gt;
 *          &lt;element name="P" type="ds:CryptoBinary"/&gt;
 *          &lt;element name="Q" type="ds:CryptoBinary"/&gt;
 *        &lt;/sequence&gt;
 *        &lt;element name="G" type="ds:CryptoBinary" minOccurs="0"/&gt;
 *        &lt;element name="Y" type="ds:CryptoBinary"/&gt;
 *        &lt;element name="J" type="ds:CryptoBinary" minOccurs="0"/&gt;
 *        &lt;sequence minOccurs="0"&gt;
 *          &lt;element name="Seed" type="ds:CryptoBinary"/&gt;
 *          &lt;element name="PgenCounter" type="ds:CryptoBinary"/&gt;
 *        &lt;/sequence&gt;
 *      &lt;/sequence&gt;
 *    &lt;/complexType&gt;
 *
 *    &lt;element name="RSAKeyValue" type="ds:RSAKeyValueType"/&gt;
 *    &lt;complexType name="RSAKeyValueType"&gt;
 *      &lt;sequence&gt;
 *        &lt;element name="Modulus" type="ds:CryptoBinary"/&gt;
 *        &lt;element name="Exponent" type="ds:CryptoBinary"/&gt;
 *      &lt;/sequence&gt;
 *    &lt;/complexType&gt;
 * </pre>
 * A <code>KeyValue</code> instance may be created by invoking the
 * {@link KeyInfoFactory#newKeyValue newKeyValue} method of the
 * {@link KeyInfoFactory} class, and passing it a {@link
 * java.security.PublicKey} representing the value of the public key. Here is
 * an example of creating a <code>KeyValue</code> from a {@link DSAPublicKey}
 * of a {@link java.security.cert.Certificate} stored in a
 * {@link java.security.KeyStore}:
 * <pre>
 * KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
 * PublicKey dsaPublicKey = keyStore.getCertificate("myDSASigningCert").getPublicKey();
 * KeyInfoFactory factory = KeyInfoFactory.getInstance("DOM");
 * KeyValue keyValue = factory.newKeyValue(dsaPublicKey);
 * </pre>
 *
 * This class returns the <code>DSAKeyValue</code> and
 * <code>RSAKeyValue</code> elements as objects of type
 * {@link DSAPublicKey} and {@link RSAPublicKey}, respectively. Note that not
 * all of the fields in the schema are accessible as parameters of these
 * types.
 *
 * <p>
 *  在<a href=\"http://wwww3org/TR/xmldsig-core/\"> W3C推荐的XML签名语法和处理</a>中定义的XML <code> KeyValue </code>元素
 * 的表示</a> <code> KeyValue </code>对象包含可用于验证签名的单个公钥XML模式定义定义为：。
 * 
 * <pre>
 * &lt; element name ="KeyValue"type ="ds：KeyValueType"/&gt; &lt; complexType name ="KeyValueType"mixed 
 * ="true"&gt; &lt; choice&gt; &lt; element ref ="ds：DSAKeyValue"/&gt; &lt; element ref ="ds：RSAKeyValue
 * "/&gt; &lt; any namespace ="## other"processContents ="lax"/&gt; &lt; / choice&gt; &lt; / complexType
 * &gt;。
 * 
 * &lt; element name ="DSAKeyValue"type ="ds：DSAKeyValueType"/&gt; &lt; complexType name ="DSAKeyValueTy
 * pe"&gt; &lt; sequence&gt; &lt; sequence minOccurs ="0"&gt; &lt; element name ="P"type ="ds：CryptoBina
 * ry"/&gt; &lt; element name ="Q"type ="ds：CryptoBinary"/&gt; &lt; / sequence&gt; &lt; element name ="G
 * "type ="ds：CryptoBinary"minOccurs ="0"/&gt; &lt; element name ="Y"type ="ds：CryptoBinary"/&gt; &lt; e
 * lement name ="J"type ="ds：CryptoBinary"minOccurs ="0"/&gt; &lt; sequence minOccurs ="0"&gt; &lt; elem
 * ent name ="Seed"type ="ds：CryptoBinary"/&gt; &lt; element name ="PgenCounter"type ="ds：CryptoBinary"/
 * &gt; &lt; / sequence&gt; &lt; / sequence&gt; &lt; / complexType&gt;。
 * 
 * &lt; element name ="RSAKeyValue"type ="ds：RSAKeyValueType"/&gt; &lt; complexType name ="RSAKeyValueTy
 * pe"&gt; &lt; sequence&gt; &lt; element name ="Modulus"type ="ds：CryptoBinary"/&gt; &lt; element name 
 * ="Exponent"type ="ds：CryptoBinary"/&gt; &lt; / sequence&gt; &lt; / complexType&gt;。
 * </pre>
 *  可以通过调用{@link KeyInfoFactory}类的{@link KeyInfoFactory#newKeyValue newKeyValue}方法并传递表示公钥值的{@link javasecurityPublicKey}
 * 来创建<code> KeyValue </code>实例以下是从{@link javasecurityKeyStore}中存储的{@link javasecuritycertCertificate}的{@link DSAPublicKey}
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see KeyInfoFactory#newKeyValue(PublicKey)
 */
public interface KeyValue extends XMLStructure {

    /**
     * URI identifying the DSA KeyValue KeyInfo type:
     * http://www.w3.org/2000/09/xmldsig#DSAKeyValue. This can be specified as
     * the value of the <code>type</code> parameter of the
     * {@link RetrievalMethod} class to describe a remote
     * <code>DSAKeyValue</code> structure.
     * <p>
     * 中创建<code> KeyValue </code>的示例：。
     * <pre>
     * KeyStore keyStore = KeyStoregetInstance(KeyStoregetDefaultType()); publicKey dsaPublicKey = keyStoreg
     * etCertificate("myDSASigningCert")getPublicKey(); KeyInfoFactory factory = KeyInfoFactorygetInstance("
     * DOM"); KeyValue keyValue = factorynewKeyValue(dsaPublicKey);。
     * </pre>
     * 
     *  此类别分别返回类型为{@link DSAPublicKey}和{@link RSAPublicKey}的对象的<code> DSAKeyValue </code>和<code> RSAKeyValue
     *  </code>元素。
     * 请注意,并非模式中的所有字段作为这些类型的参数可访问。
     * 
     */
    final static String DSA_TYPE =
        "http://www.w3.org/2000/09/xmldsig#DSAKeyValue";

    /**
     * URI identifying the RSA KeyValue KeyInfo type:
     * http://www.w3.org/2000/09/xmldsig#RSAKeyValue. This can be specified as
     * the value of the <code>type</code> parameter of the
     * {@link RetrievalMethod} class to describe a remote
     * <code>RSAKeyValue</code> structure.
     * <p>
     */
    final static String RSA_TYPE =
        "http://www.w3.org/2000/09/xmldsig#RSAKeyValue";

    /**
     * Returns the public key of this <code>KeyValue</code>.
     *
     * <p>
     *  标识DSA KeyValue KeyInfo类型的URI：http：// wwww3org / 2000/09 / xmldsig#DSAKeyValue这可以指定为{@link RetrievalMethod}
     * 类的<code> type </code>参数的值, remote <code> DSAKeyValue </code>结构。
     * 
     * 
     * @return the public key of this <code>KeyValue</code>
     * @throws KeyException if this <code>KeyValue</code> cannot be converted
     *    to a <code>PublicKey</code>
     */
    PublicKey getPublicKey() throws KeyException;
}
